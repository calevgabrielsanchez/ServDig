var dialogoConfirmarCancelar;
var dialogoConfirmar;
var campoToFocusOn;

$(document).ready(function() {
	
	// Se incializa el blockUI para las peticiones AJAX
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
	
	//enableOtroPoder($('input[name="tipoPoder"]:checked').val());
	$(".uniqueCheckbox").click( function() {
	    var n = $(this).attr('name');
	    $("[name=" + n + ']').prop("checked", false);
	    $(this).prop("checked", true);
	});
	
	$('#nombreComercial').keypress(function(e) {
		var code = e.keyCode || e.which; 
		if (code  == 13) {               
			e.preventDefault();
			return false;
		}
	});
	
	obtenerMunicipiosImss();
	$('#pasoPrevio').click(function() {
		pasoPrevio();
	});
	
	$('#mostrarRLVentanilla').click(function() {
		var errors = evaluarComplementoClasificacion();

		if (errors == "") {
			validarSolicitudAltaPatronal();				
		} else {
			construirDialogoErrores(errors);
		}
	});

	$('#finalizarTramite').click(function() {
		var errors = evaluarComplementoClasificacion();
 
		if (errors == "") {
				
			errors= evaluaSubdelegacion();
			console.log("Errors: "+ errors)
			;
			if (errors == ""){
				console.log("No se tiene error llendo a: validarSolicitudAltaPatronal ")
				validarSolicitudAltaPatronal();	
				
			}else{
				construirDialogoErrores(errors);
			}
		} else {
			
			construirDialogoErrores(errors);
		}
		console.log("Termine en finalizartramite.click")
		
	});

	$('#guardarTramite').click(function() {
		guardarTramite();
	});

	$('#guardarCerrarTramite').click(function() {
		guardarCerrarTramite();
	});

	$('#cancelarTramite').click(function() {
		dialogoConfirmarCancelar.dialog( "open" );
	});

	$('#cerrarWizard').click(function() {
		cerrarWizard();
	});
	
	
	dialogoConfirmarCancelar = $( "#dialog-confirm-cancelar" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		buttons: {
		 	"Cancelar": function() {
		 		$( this ).dialog( "close" );
		 	},
			"Aceptar": function() {
				cancelarTramite();
		 	}
		 }
	});
	
	
	dialogoConfirmar = $( "#dialog-confirm" ).dialog({
		resizable: false,
		height:160,
		modal: true,
		autoOpen: false,
		buttons: {
			"Aceptar": function() {
				cerrarWizard();
		 	}
		 }
	});
		
	if(parent.WizardAltaPatronalCtrl.config.idOrigen == idOrigenPortalInternet){
		//Si es INTERNET
		parent.FirmaDigitalCtrl.setOnCloseCallback(function() {
			dialogoConfirmar.dialog("option", "buttons", [ {
				text : 'Aceptar',
				click : function() {
					$(this).dialog('close');
				}
			}]);
			
			if(parent.FirmaDigitalCtrl.datosSalida == null) {
				$('#mensajeDialogo').text("La validaci\u00f3n de la firma no pudo ser realizada");
				dialogoConfirmar.dialog('open');
			} else {
				if(parent.FirmaDigitalCtrl.datosSalida.Resultado == 0) {
					var firmaResponse = {
						cadenaOriginal               : parent.FirmaDigitalCtrl.datosSalida.contenedores[0].cadori,
						recibo                       : parent.FirmaDigitalCtrl.datosSalida.contenedores[0].cms,
						reciboNotarial               : parent.FirmaDigitalCtrl.datosSalida.folio,
						urlAcuseFirma                : parent.FirmaDigitalCtrl.datosSalida.acuse,
						serialCertificado            : parent.FirmaDigitalCtrl.datosSalida.serie_cert,
						strIniciaVigenciaCertificado : parent.FirmaDigitalCtrl.datosSalida.vigIni,
						strFinVigenciaCertificado    : parent.FirmaDigitalCtrl.datosSalida.vigFin
					};

					firmarTramite(firmaResponse);
				} else {
					$('#mensajeDialogo').text("La validaci\u00f3n de la firma no pudo ser realizada");
					dialogoConfirmar.dialog('open');
				}
			}
		});
	}
	
	setEventosChecarError("clasificacionForm",evaluarComplementoClasificacion);
});

function enableOtroPoder(tipoPoder){
	if(tipoPoder==undefined)
		return;
	if(tipoPoder=='Especial'){
		 $('#otroPoder').show();
	}else{
		$('#otroPoder').hide();
		$('#otroPoder').val('');
	}
}

function firmarTramite(firmaResponse) {
	var url = context_path + '/wizard/tramite/registro/patronal/procesarDatosFirma';
	prepararRequest(url, firmaResponse, false, finalizarSolicitudAltaPatronal);
}

function validarSolicitudAltaPatronal() {
	$.blockUI();
	var idSolicitud = $('#hdnIdSolicitud').val();
	var url = context_path + '/wizard/tramite/registro/patronal/validar/solicitud/' + idSolicitud;
	var clasificacionForm = $("form#clasificacionForm").toObject();
	clasificacionForm.personasAutorizadas=construirListaPersonasAutorizadas();
	if(parent.WizardAltaPatronalCtrl.config.idOrigen == idOrigenPortalInternet){
		//Procesar petición INTERNET (Firma)
		prepararRequest(url, clasificacionForm, true, evaluarRIF);
	}else if(parent.WizardAltaPatronalCtrl.config.idOrigen == idOrigenPortalVentanilla){
		//Procesar petición VENTANILLA (Representantes Legales)
		prepararRequest(url, clasificacionForm, true, procesarSolicitudVentanilla);
	}else{
		//Procesar petición PORTAL CIUDADANO....
		prepararRequest(url, clasificacionForm, true, procesarSolicitud);
	}
}


function procesarSolicitudVentanilla(response) {
	if (response.mensajeExito != undefined && response.mensajeExito != null) {
		var idSolicitud = $('#hdnIdSolicitud').val();
		var url = context_path + '/wizard/tramite/registro/patronal/finalizar/solicitud/' + idSolicitud;
		var clasificacionForm = $("form#clasificacionForm").toObject();
		clasificacionForm.personasAutorizadas=construirListaPersonasAutorizadas();
		
		//Alta Patronal PF y PM mostraran Representantes a seleccionar, solo PF no sera requerido.
		prepararRequest(url, clasificacionForm, true, mostrarRepresentantesLegales);
		
	}else if (response.mensajeError != undefined
			&& response.mensajeError != null) {
		var mensaje = "";
		var error = true;
		if (response.mensajeError == "") {
			mensaje = "Ocurrio un error con el servidor.";
		} else {
			mensaje = response.mensajeError;
		}		
		construirDialogoMensajes("Existe un inconveniente", mensaje, error, undefined, 200, 600);
	}
}

function procesarVentanilla(response){
	if (response.mensajeExito != undefined && response.mensajeExito != null) {
		//Si finalizar solicitud es exitoso, mostrar pantalla de "solicitud en proceso"...
		var folioSolicitud = $('#hdnFolioSolicitud').val();		
		parent.ProcesandoSolicitudCtrl.abrir(folioSolicitud);
		cerrarWizard();
	}else if (response.mensajeError != undefined
			&& response.mensajeError != null) {
		var mensaje = "";
		var error = true;
		if (response.mensajeError == "") {
			mensaje = "Ocurrio un error con el servidor.";
		} else {
			mensaje = response.mensajeError;
		}		
		construirDialogoMensajes("Existe un inconveniente", mensaje, error, undefined, 200, 600);
	}
}

function procesarSolicitud(response){
	if (response.mensajeExito != undefined && response.mensajeExito != null) {
		
		finalizarSolicitudAltaPatronal();
		
	}else if (response.mensajeError != undefined
			&& response.mensajeError != null) {
		var mensaje = "";
		var error = true;
		if (response.mensajeError == "") {
			mensaje = "Ocurrio un error con el servidor.";
		} else {
			mensaje = response.mensajeError;
		}		
		construirDialogoMensajes("Existe un inconveniente", mensaje, error, undefined, 200, 600);
	}
}

function mostrarRepresentantesLegales(response){
	if (response.mensajeExito != undefined && response.mensajeExito != null) {		
		setTimeout(function(){
			var idSolicitud = $('#hdnIdSolicitud').val();
			var urlAction = context_path + '/wizard/tramite/registro/patronal/representantesLegales/'+ idSolicitud;
			$.blockUI();
			$('form#concluirTramiteForm').attr('action', urlAction);
			$('form#concluirTramiteForm').submit();
		}, 200);
		
	}else if (response.mensajeError != undefined
			&& response.mensajeError != null) {
		var mensaje = "";
		var error = true;
		if (response.mensajeError == "") {
			mensaje = "Ocurrio un error con el servidor.";
		} else {
			mensaje = response.mensajeError;
		}		
		construirDialogoMensajes("Existe un inconveniente", mensaje, error, undefined, 200, 600);
	}
	
}

function evaluarRIF(response){
	if (response.mensajeExito != undefined && response.mensajeExito != null) {
		
		iniciarFirmaDigital(response);
		
	}else if (response.mensajeError != undefined
			&& response.mensajeError != null) {
		var mensaje = "";
		var error = true;
		if (response.mensajeError == "") {
			mensaje = "Ocurrio un error con el servidor.";
		} else {
			mensaje = response.mensajeError;
		}		
		construirDialogoMensajes("Existe un inconveniente", mensaje, error, undefined, 200, 600);
	}
}

function construirAfectado() {
	var personaAfectada = new Object();
	personaAfectada.nombreRazonSocial = datosEntradaFirma.nombreCompleto;
	personaAfectada.rfc = datosEntradaFirma.rfc;
	personaAfectada.curp = datosEntradaFirma.curp;
	
	var afectados = [personaAfectada];
	
	return afectados;
}

function iniciarFirmaDigital(response){

		var componenteFirma = {
			idTipoSolicitud : codigoTipoSolicitud,
			descripcionTipoSolicitud : descripcionTipoSolicitud,
			idTipoTramite : arrayCodigoTipoTramite,
			folioSolicitud : $('#hdnFolioSolicitud').val(),
			curp : datosEntradaFirma.curp,
			rfc : datosEntradaFirma.rfc,
			validarRFC : true,
			registroPatronal : datosEntradaFirma.registroPatronal,
			nombreCompleto : datosEntradaFirma.nombreCompleto,
			fechaElectronica : datosEntradaFirma.fechaElectronica,
			cad_original : $('#contenidoFirmar').val().replace(/"/g, '\\"'),
			tipo_operacion : 'firmaCMS',
			firma_archivo : true,
			min_archivos : 1,
			max_archivos : 6,
			afectado: [parent.AtributosPersonaCtrl.personaPortal],
			acuse: 'AP',
			tipoAcuse: 1
		};

        //Se quita caracter de escape en caso de que venga ya en el nombre
        parent.AtributosPersonaCtrl.personaPortal.nombreRazonSocial = parent.AtributosPersonaCtrl.personaPortal.nombreRazonSocial
            .replace(/\\/g, '');

		// Se escapan las comillas dobles
		componenteFirma.afectado[0].nombreRazonSocial = parent.AtributosPersonaCtrl.personaPortal.nombreRazonSocial
			.replace(/\"/g, '\\"');
		
		parent.iniciarFirmaDigital(componenteFirma);
		
}

function finalizarSolicitudAltaPatronal() {
	var idSolicitud = $('#hdnIdSolicitud').val();
	var url = context_path + '/wizard/tramite/registro/patronal/finalizar/solicitud/'
			+ idSolicitud;
	var clasificacionForm = $("form#clasificacionForm").toObject();
	clasificacionForm.personasAutorizadas=construirListaPersonasAutorizadas();
	prepararRequest(url, clasificacionForm, true, confirmarFinalizacion);
}

function confirmarFinalizacion(response) {
	$.blockUI();
	if (response.mensajeExito != undefined && response.mensajeExito != null) {
		var folioSolicitud = $('#hdnFolioSolicitud').val();
		var idSolicitud = $('#hdnIdSolicitud').val();
		parent.ProcesandoSolicitudCtrl.abrir(folioSolicitud, idSolicitud, tipoSolicitud);
		cerrarWizard();
	} else if (response.mensajeError != undefined
			&& response.mensajeError != null) {
		var error = true;
		if (response.mensajeError == "") {
			construirDialogoMensajes("Operaci&oacute;n Erronea", "Ocurrio un error con el servidor.", error);
		} else {
			construirDialogoMensajes("Actividad inv&aacute;lida", response.mensajeError, true, 200, 600);
		}
	}
}

function pasoPrevio() {
	$.blockUI();	
	var idSolicitud = $('#hdnIdSolicitud').val();
	var clasificacionForm = $("form#clasificacionForm").toObject();
	clasificacionForm.personasAutorizadas=construirListaPersonasAutorizadas();
	var url = context_path + '/wizard/tramite/registro/patronal/guardar/solicitud/complementoClasif/' + idSolicitud;
	var request = $.ajax({
		url : url,
		async : true,
		type : "POST",
		data : clasificacionForm ? JSON.stringify(clasificacionForm) : null,
		dataType : "json",
		contentType : "application/json; charset=utf-8"
	});
	request.done(pasoPrevioSubmit);
		
}

function pasoPrevioSubmit() {
	setTimeout(function(){
		var idSolicitud = $('#hdnIdSolicitud').val();
		var urlAction = context_path + '/wizard/tramite/registro/patronal/pasoPrevio/solicitud/' + idSolicitud;
		$.blockUI();	
		document.getElementById('clasificacionForm').action = urlAction;
		document.getElementById('clasificacionForm').submit();
		}, 200);	
}


function evaluarComplementoClasificacion() {
	var temp = "";
	var errors = "";
	var $elementoValidando = null;
	var errorCampoObligarorio = "Campo obligatorio.";
	var existeError = false;
	if (typeof indPrestaServicioPersonal !== "undefined" && indPrestaServicioPersonal == 1) {
		$elementoValidando = $("#clasificacion\\.numCentrosTraba");
		temp = $("#clasificacion\\.numCentrosTraba").val();
		if (isEmpty(temp)) {
			existeError = true;
			errors += "* El n&uacute;mero de centros de trabajo es requerido. <br/>";
			setCampoToFocusOn("#clasificacion\\.numCentrosTraba");
			
		}
		
		mostrarMensajeErrorMovPat($elementoValidando, existeError , errorCampoObligarorio);
	}
	
	existeError = false;
	$elementoValidando = $('#municipioIMSS\\.subdelegacion\\.id');
	temp = $elementoValidando.val();
	if (isEmpty(temp) || temp == '-1') {
		existeError = true;
		errors += "* El campo Subdelegaci&oacute;n es obligatorio. <br/>";
		setCampoToFocusOn('#municipioIMSS\\.subdelegacion\\.id');
	}
	
	mostrarMensajeErrorMovPat($elementoValidando, existeError , errorCampoObligarorio);
	existeError = false;
	$elementoValidando = $('#tipoPoderA');
	var atLeastOneIsChecked = $('input[name="tipoPoder"]:checked').length > 0;
	if(typeof validaTipoPoder !== "undefined" && validaTipoPoder && !atLeastOneIsChecked){
		errors += "* Debe seleccionar el tipo de poder que se le ha otorgado. <br/>";
		existeError = true;
		setCampoToFocusOn('#tipoPoder');
	}
	//solo si existe el campo se quitara o se pondra el error
	if($('#tipoPoderA').length) {
		mostrarMensajeErrorMovPat($elementoValidando, existeError , errorCampoObligarorio);
	}
	
	pintarErrorGeneral(errors != "")
	return errors;
}

function mostrarMensajeErrorMovPat($campo, error , mensaje) {
	var cssDisplay = error ? "block" : "none";
	var cssBorder = error ? "1px solid red" : "1px solid #ccc";
	var cssColor = error ? "red" : "black";
	
	if($campo != undefined) {
		var idCampo = $campo.attr("id");
		
		
		var tipoElemento = $campo.prop("tagName");
		var idSpanRequired = $campo.attr("spanRequired");
		var idSpanError = $campo.attr("spanError");
		//console.log("el id del campo es: " + idCampo + " y es un " + tipoElemento + " su spanRequerido es: " + idSpanRequired + " su spanError es: " + idSpanError);
		var valorCampo = $campo.val();
		
		if(tipoElemento != "table") {
			//ponemos el campo en rojo
			$campo.css("border",cssBorder);
		}
		//se obtiene asi para evitar escapar 
		var spanError = document.getElementById(idCampo+"Error");
		var campoRequired = document.getElementById(idCampo + "Req");
		
		if(spanError != undefined && spanError != null) {
			//console.log("se encontro el span de error y su id es: " + spanError.id );
			spanError.style.display = cssDisplay;
			
			if((mensaje != undefined || mensaje != null) && error) {
				spanError.innerHTML = mensaje;
			} else {
				spanError.innerHTML = "";
			}
		}
		
		if(campoRequired != undefined && campoRequired != null) {
			//console.log("se encontro el campo required y su id es: " + campoRequired.id);
			campoRequired.style.color = cssColor;
		}
		
	}
}

function evaluaSubdelegacion(){
	var existeError = false;
	var errors = "";
	var errorCampoObligarorio = "Campo obligatorio.";
	//recorrer los radiobotton
	var valorRadioButton = $('input[name=idMunicipioImssRadio]:radio').each(function(){
	  //  alert($(this).val());
	});
	
	
	//para ver que no tenga elementos activos
	let elementoActivo = document.querySelector('input[name="idMunicipioImssRadio"]:checked');
	//var elementoActivo = document.querySelector('input[name="idMunicipioImssRadio"]:checked').length > 0;
	
	if(!elementoActivo) {
		console.log("No se selecciono la subdelegación");
		
//		alert('Se requiere agregar la subdelegación para seguir con el trámite.');
	
//	existeError = true;
	errors += "* Se requiere agregar la subdelegaci&oacute;n para seguir con el tr&aacute;mite. <br/>";
//	setCampoToFocusOn('input[name=idMunicipioImssRadio]:radio');
	
//	mostrarMensajeError(valorRadioButton, existeError , errorCampoObligarorio);
//	pintarErrorGeneral(errors != "")
	}
		
	return errors;
	
}

function isEmpty(temp) {
	return (temp == undefined || temp == "");
}

function setCampoToFocusOn(fieldName) {
	if (campoToFocusOn == undefined)
		campoToFocusOn = $(fieldName);
}

function guardarTramite() {
	construirDialogoConfirmarComun('\u00BFEst\u00E1 seguro que desea Guardar la solicitud?', guardarSolicitudTramite);
}

function guardarSolicitudTramite() {
	var idSolicitud = $('#hdnIdSolicitud').val();
	var url = context_path + '/wizard/tramite/registro/patronal/guardar/solicitud/complementoClasif/'
		+ idSolicitud;

	var clasificacionForm = $("form#clasificacionForm").toObject();
	clasificacionForm.personasAutorizadas=construirListaPersonasAutorizadas();
	prepararRequest(url, clasificacionForm, true, procesarRespuestaServidor);
}

function guardarCerrarTramite() {
	construirDialogoConfirmarComun('\u00BFEst\u00E1 seguro que desea Guardar antes de cerrar la solicitud?', guardarCerrarSolicitud);
}

function guardarCerrarSolicitud(){
	var idSolicitud = $('#hdnIdSolicitud').val();
	var url = context_path + '/wizard/tramite/registro/patronal/guardar/solicitud/complementoClasif/'
		+ idSolicitud;

	var clasificacionForm = $("form#clasificacionForm").toObject();
	clasificacionForm.personasAutorizadas=construirListaPersonasAutorizadas();
	prepararRequest(url, clasificacionForm, true, callbackGuardarCerrarSolicitud);
}

function callbackGuardarCerrarSolicitud(response) {
	procesarRespuestaServidor(response, cerrarWizard);
}

function cancelarTramite() {
	var idSolicitud = $('#hdnIdSolicitud').val();
	var url = context_path + '/wizard/tramite/registro/patronal/cancelar/solicitud/'
		+ idSolicitud;

	$.postJSON(url, null, function(data) {
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog( "open" );
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.open();
	});
}

function cerrarWizard() {	
	parent.WizardAltaPatronalCtrl.cerrar();
}

function construirDialogoErrores(errores) {
	$("#textoMensaje").html(errores);
	$("#textoMensaje").removeAttr("style");
	$("#textoMensaje").attr("style", "color: red;");
	var dialogo = $("#dialogoMensajes").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : 275,
		width : 500,
		title : "Existen faltantes en su captura.",
		buttons : {
			"Aceptar" : function() {
				$(this).dialog("close");
				campoToFocusOn.focus();
				campoToFocusOn = undefined;

			}
		}
	});
	dialogo.dialog('open');
}

function procesarRespuestaServidor(response, callback) {
	var mensaje = "";
	var titulo = "";
	var error = false;
	
	if (response.mensajeExito != undefined && response.mensajeExito != null) {
		titulo = "Operaci&oacute;n Exitosa";
		error = false;
		if (response.mensajeExito == "") {
			mensaje = "Operaci&oacute;n realizada con &eacute;xito.";
		} else {
			mensaje = response.mensajeExito;
			construirDialogoMensajes(titulo, mensaje, error, callback);
		}

		return true;
	} else if (response.mensajeError != undefined
			&& response.mensajeError != null) {
		titulo = "Operaci&oacute;n Erronea";
		error = true;
		if (response.mensajeError == "") {
			mensaje = "Ocurrio un error con el servidor.";
		} else {
			mensaje = response.mensajeError;
		}
		construirDialogoMensajes(titulo, mensaje, error, callback);
	}
	return false;
}

function construirDialogoMensajes(titulo, mensaje, error, callback, height, width) {
	if(height==undefined)
		height='auto';
	if(width==undefined)
		width=400;
		
	$("#textoMensaje").html(mensaje);
	$("#textoMensaje").removeAttr("style");
	if (error) {
		$("#textoMensaje").attr("style", "color: red;");
	} else {
		$("#textoMensaje").attr("style", "color: black;");
	}
	var dialogo = $("#dialogoMensajes").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : height,
		width : width,
		title : titulo,
		buttons : {
			"Aceptar" : function() {
				if (jQuery.isFunction(callback)) {
					callback();
				}
				$(this).dialog("close");
			}
		}
	});
	dialogo.dialog('open');
}


function ejecutarConsulta(numPersonaAturorizada){
	var rfcVar='#rfcPA'+numPersonaAturorizada;
	var curpVar='#curpPA'+numPersonaAturorizada;
	var persona = new Object();
	persona.rfc=$(rfcVar).val();
	persona.curp=$(curpVar).val();
	if(persona.rfc!=undefined && persona.curp!=undefined && persona.rfc!="" && persona.curp!=""){
		obtenerPersonaAutorizada(numPersonaAturorizada);
	}
	
}

function obtenerPersonaAutorizada(numPersonaAturorizada) {
	$.blockUI();
	var url = context_path + '/wizard/tramite/registro/patronal/consultar/personaAutorizada/'+numPersonaAturorizada;
	var rfcVar='#rfcPA'+numPersonaAturorizada;
	var curpVar='#curpPA'+numPersonaAturorizada;
	fnHideErrores("");
	var persona = new Object();
	persona.rfc=$(rfcVar).val();
	persona.curp=$(curpVar).val();
	if(persona.rfc!=undefined){
		persona.rfc=persona.rfc.toUpperCase();
		persona.curp=persona.curp.toUpperCase();
	}
	
	var listaPersonas = construirListaPersonasAutorizadas();
	var i;
	for (i=0; i<listaPersonas.length; i++){
		var pa = listaPersonas[i];
		if(persona.rfc!=undefined && persona.rfc!='' && persona.rfc==pa.fisica.rfc
		 && persona.curp!=undefined && persona.curp!='' && persona.curp==pa.fisica.curp){
		 	construirDialogoMensajes("Error", "La persona autorizada, ya se encuentra registrada.", true);
		 	$.unblockUI();
		 	return;
		 }
		
		
	}

	
	var callback=undefined;
	if(numPersonaAturorizada=='1')
		callback=desplegarInfoPA;//prepararRequest(url, persona, true, desplegarInfoPA);
	else if(numPersonaAturorizada=='2')
		callback=desplegarInfoPA2;//prepararRequest(url, persona, true, desplegarInfoPA2);
	if(numPersonaAturorizada=='3')
		callback=desplegarInfoPA3;//prepararRequest(url, persona, true, desplegarInfoPA3);
	
	$.postJSON(url, persona, function(data) {
		callback(data);
	}).error(function(data){
		construirDialogoMensajes("Error", "La informaci\u00f3n proporcionada presenta errores, favor de verificarla", true);
		fnProcesarErroresDeCaptura(data, "");
	});
	
}

function desplegarInfoPA(response){
	if(response.mensajeError!=undefined){
		construirDialogoMensajes("Error", response.mensajeError, true);
	}else{
		$("#rfcPA1").val(response.personaEncontrada.rfc);
		$("#rfcPA1").attr("readonly","readonly");
		$("#curpPA1").val(response.personaEncontrada.curp);
		$("#curpPA1").attr("readonly","readonly");
		
		$("#nombrePA1").val(response.personaEncontrada.nombre);
		$("#apellidoPatPA1").val(response.personaEncontrada.primerApellido);
		$("#apellidoMatPA1").val(response.personaEncontrada.segundoApellido);
		$("#idPersonaPA1").val(response.personaEncontrada.idPersona);
		$("#cveFisicaPA1").val(response.personaEncontrada.cveFisica);
		$("#numPA1").removeAttr("readonly");
		$("#extPA1").removeAttr("readonly");
		$("#numMovPA1").removeAttr("readonly");
		$("#correoPA1").removeAttr("readonly");
		
		if(response.personaEncontrada.telefonoFijoFiscal!=null 
				&& response.personaEncontrada.telefonoFijoFiscal!=undefined)
			$("#numPA1").val(response.personaEncontrada.telefonoFijoFiscal.numero);
		
		if(response.personaEncontrada.telefonoMovilFiscal!=null
				&& response.personaEncontrada.telefonoMovilFiscal!=undefined)
		$("#numMovPA1").val(response.personaEncontrada.telefonoMovilFiscal.numero);
		
		if(response.personaEncontrada.correoElectronicoFiscal!=null
				&& response.personaEncontrada.correoElectronicoFiscal!=undefined)
		$("#correoPA1").val(response.personaEncontrada.correoElectronicoFiscal.correo);
	}
	$.unblockUI();
}

function desplegarInfoPA2(response){
	if(response.mensajeError!=undefined){
		construirDialogoMensajes("Error", response.mensajeError, true);
	}else{
		$("#rfcPA2").val(response.personaEncontrada.rfc);
		$("#rfcPA2").attr("readonly","readonly");
		$("#curpPA2").val(response.personaEncontrada.curp);
		$("#curpPA2").attr("readonly","readonly");

		$("#nombrePA2").val(response.personaEncontrada.nombre);
		$("#apellidoPatPA2").val(response.personaEncontrada.primerApellido);
		$("#apellidoMatPA2").val(response.personaEncontrada.segundoApellido);
		$("#idPersonaPA2").val(response.personaEncontrada.idPersona);
		$("#cveFisicaPA2").val(response.personaEncontrada.cveFisica);
		$("#numPA2").removeAttr("readonly");
		$("#extPA2").removeAttr("readonly");
		$("#numMovPA2").removeAttr("readonly");
		$("#correoPA2").removeAttr("readonly");
		
		if(response.personaEncontrada.telefonoFijoFiscal!=null 
				&& response.personaEncontrada.telefonoFijoFiscal!=undefined)
		$("#numPA2").val(response.personaEncontrada.telefonoFijoFiscal.numero);
		
		if(response.personaEncontrada.telefonoMovilFiscal!=null
				&& response.personaEncontrada.telefonoMovilFiscal!=undefined)
		$("#numMovPA2").val(response.personaEncontrada.telefonoMovilFiscal.numero);
		
		if(response.personaEncontrada.correoElectronicoFiscal!=null
				&& response.personaEncontrada.correoElectronicoFiscal!=undefined)
		$("#correoPA2").val(response.personaEncontrada.correoElectronicoFiscal.correo);
	}
	$.unblockUI();
}

function desplegarInfoPA3(response){
	if(response.mensajeError!=undefined){
		construirDialogoMensajes("Error", response.mensajeError, true);
	}else{
		$("#rfcPA3").val(response.personaEncontrada.rfc);
		$("#rfcPA3").attr("readonly","readonly");
		$("#curpPA3").val(response.personaEncontrada.curp);
		$("#curpPA3").attr("readonly","readonly");
				
		$("#nombrePA3").val(response.personaEncontrada.nombre);
		$("#apellidoPatPA3").val(response.personaEncontrada.primerApellido);
		$("#apellidoMatPA3").val(response.personaEncontrada.segundoApellido);
		$("#idPersonaPA3").val(response.personaEncontrada.idPersona);
		$("#cveFisicaPA3").val(response.personaEncontrada.cveFisica);
		$("#numPA3").removeAttr("readonly");
		$("#extPA3").removeAttr("readonly");
		$("#numMovPA3").removeAttr("readonly");
		$("#correoPA3").removeAttr("readonly");
		
		if(response.personaEncontrada.telefonoFijoFiscal!=null 
				&& response.personaEncontrada.telefonoFijoFiscal!=undefined)
		$("#numPA3").val(response.personaEncontrada.telefonoFijoFiscal.numero);
		
		if(response.personaEncontrada.telefonoMovilFiscal!=null
				&& response.personaEncontrada.telefonoMovilFiscal!=undefined)
		$("#numMovPA3").val(response.personaEncontrada.telefonoMovilFiscal.numero);
		
		if(response.personaEncontrada.correoElectronicoFiscal!=null
				&& response.personaEncontrada.correoElectronicoFiscal!=undefined)
		$("#correoPA3").val(response.personaEncontrada.correoElectronicoFiscal.correo);
	}
	$.unblockUI();
}

function construirListaPersonasAutorizadas(){
	var pa1=undefined;
	var pa2=undefined;
	var pa3=undefined;
	
	//Se mantiene inhabilitado el envio de PA	
	
	if(!isEmpty($("#nombrePA1").val())){
		pa1=new Object();
		pa1.fisica=new Object();
		pa1.fisica.tipoPersona=new Object();
		pa1.fisica.tipoPersona.idTipoPersona=1;
		pa1.fisica.rfc=$("#rfcPA1").val();
		pa1.fisica.idPersona=$("#idPersonaPA1").val();
		pa1.fisica.cveFisica=$("#cveFisicaPA1").val();
		pa1.fisica.curp=$("#curpPA1").val();
		pa1.fisica.nombre=$("#nombrePA1").val();
		pa1.fisica.primerApellido=$("#apellidoPatPA1").val();
		pa1.fisica.segundoApellido=$("#apellidoMatPA1").val();
		
		if($("#correoPA1").val()!=""){ 
			pa1.fisica.correoElectronico=new Object();
			pa1.fisica.correoElectronico.correo=$("#correoPA1").val();
		}
		if($("#numPA1").val()!=""){
			pa1.fisica.telefonoFijo=new Object();
			pa1.fisica.telefonoFijo.numero=$("#numPA1").val();
			pa1.fisica.telefonoFijo.extension=$("#extPA1").val();
		}	
		if($("#numMovPA1").val()!=""){
			pa1.fisica.telefonoMovil=new Object();
			pa1.fisica.telefonoMovil.numero=$("#numMovPA1").val();
			
		}
	}
	
	if(!isEmpty($("#nombrePA2").val())){
		pa2=new Object();
		pa2.fisica=new Object();
		pa2.fisica.tipoPersona=new Object();
		pa2.fisica.tipoPersona.idTipoPersona=1;
		pa2.fisica.rfc=$("#rfcPA2").val();
		pa2.fisica.idPersona=$("#idPersonaPA2").val();
		pa2.fisica.cveFisica=$("#cveFisicaPA2").val();
		pa2.fisica.curp=$("#curpPA2").val();
		pa2.fisica.nombre=$("#nombrePA2").val();
		pa2.fisica.primerApellido=$("#apellidoPatPA2").val();
		pa2.fisica.segundoApellido=$("#apellidoMatPA2").val();
		if($("#correoPA2").val()!=""){ 
			pa2.fisica.correoElectronico=new Object();
			pa2.fisica.correoElectronico.correo=$("#correoPA2").val();
		}
		if($("#numPA2").val()!=""){
			pa2.fisica.telefonoFijo=new Object();
			pa2.fisica.telefonoFijo.numero=$("#numPA2").val();
			pa2.fisica.telefonoFijo.extension=$("#extPA2").val();
		}		
		if($("#numMovPA2").val()!=""){
			pa2.fisica.telefonoMovil=new Object();
			pa2.fisica.telefonoMovil.numero=$("#numMovPA2").val();
			
		}
	}	
	if(!isEmpty($("#nombrePA3").val())){
		pa3=new Object();
		pa3.fisica=new Object();
		pa3.fisica.tipoPersona=new Object();
		pa3.fisica.tipoPersona.idTipoPersona=1;
		pa3.fisica.rfc=$("#rfcPA3").val();
		pa3.fisica.idPersona=$("#idPersonaPA3").val();
		pa3.fisica.cveFisica=$("#cveFisicaPA3").val();
		pa3.fisica.curp=$("#curpPA3").val();
		pa3.fisica.nombre=$("#nombrePA3").val();
		pa3.fisica.primerApellido=$("#apellidoPatPA3").val();
		pa3.fisica.segundoApellido=$("#apellidoMatPA3").val();		
		if($("#correoPA3").val()!=""){
			pa3.fisica.correoElectronico=new Object();
			pa3.fisica.correoElectronico.correo=$("#correoPA3").val();		
		}
		if($("#numPA3").val()!=""){
			pa3.fisica.telefonoFijo=new Object();
			pa3.fisica.telefonoFijo.numero=$("#numPA3").val();
			pa3.fisica.telefonoFijo.extension=$("#extPA3").val();
		}
		
		if($("#numMovPA3").val()!=""){
			pa3.fisica.telefonoMovil=new Object();
			pa3.fisica.telefonoMovil.numero=$("#numMovPA3").val();
		}		
	}
	
	
	var personasAutorizadas=[];
	if(pa1!=undefined  ){
		personasAutorizadas.push(pa1);
	}
	if(pa2!=undefined){
		personasAutorizadas.push(pa2);
	}
	if(pa3!=undefined){
		personasAutorizadas.push(pa3);
	}
	
	return personasAutorizadas;
	
}


function limpiarForm(numPersonaAturorizada){
	//limpiarFormulario(personaform,true);
	$('#personaAutorizada' + numPersonaAturorizada +' :input').removeAttr("readonly");
	$('#personaAutorizada' + numPersonaAturorizada +' :input').val('');
	$('#personaAutorizada' + numPersonaAturorizada +' :input').attr("readonly","readonly");
	
	var rfc='#rfcPA'+numPersonaAturorizada;
	var curp='#curpPA'+numPersonaAturorizada;
		
	$(rfc).removeAttr("readonly");
	$(curp).removeAttr("readonly");	;
}

function validaCurpRfc() {
	var response = true;
	var rfcPA1 = $('#rfcPA1').val();
	var curpPA1 = $('#curpPA1').val();
	var rfcPA2 = $('#rfcPA2').val();
	var curpPA2 = $('#curpPA2').val();
	var rfcPA3 = $('#rfcPA3').val();
	var curpPA3 =$('#curpPA3').val();
	
	if ((rfcPA1 == rfcPA2 && curpPA1==curpPA2 && rfcPA1 != "" ) || 
			(rfcPA1 == rfcPA3 && curpPA1==curpPA3 && rfcPA3 != "") || 
			(rfcPA3 == rfcPA2 && curpPA3==curpPA2 && rfcPA2 != "")) {
		construirDialogoMensajes("Actividad inv&aacute;lida", "Los datos RFC y CURP deben ser diferentes para cada persona autorizada.", true, 200, 300);
		response = false;
	}
	return response;
}


/* setCampoToFocusOn("#correo"+mail);*/
 
function validarEmail(mail) {
	var correo = $('input#correo' + mail).val();
	expr = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if (correo.length != 0) {
		if (!expr.test(correo)) {
			$('#dialogoMsgError').html(
					'<span style="color: red;">La direcci&oacute;n de email no es v&aacute;lida: '
							+ correo + '</span>');
			$('#dialogoMsgError').dialog({
				title : 'Error',
				modal : true,
				resizable : false,
				buttons : {
					Aceptar : function() {
						$(this).dialog("close");
						$('#dialogoMsgSeleccion').html('');
						$('input#correo' + mail).focus();/*DEJA EL CURSOR NUEVAMENTE EN EL CAMPO PARA QUE INGRESE EL CORREO CORRECTAMENTE*/
					}
				}
			});

		}

	}

}

function setEventosChecarError(idFormulario,functionPintarErrores) {
	
	$('input:radio').change(function() {
		functionPintarErrores();
	});
	
	$("#"+idFormulario+" :checkbox").change(function() {
		functionPintarErrores();
	});
}