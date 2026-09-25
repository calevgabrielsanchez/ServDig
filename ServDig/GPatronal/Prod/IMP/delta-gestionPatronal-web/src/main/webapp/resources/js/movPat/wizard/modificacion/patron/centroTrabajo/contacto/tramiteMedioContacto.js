var campoToFocusOn;
var dialogoConfirmar;
var dialogoConfirmarCancelar;
var urlContacto 			= '/delta-gestionPatronal-web/wizard/tramite/centroTrabajo/medios';

$(function() {
	parseTelefonos();
	removeToSpanErrorForma();
	
	$('#finalizarTramiteDatosContacto').click(function() {
		removeToSpanErrorForma();
		evaluarFinalizarTramite();
	});
	
	$('#cerrarWizard').click(function() {
		removeToSpanErrorForma();
		cerrarWizard();
	});
	
	$('#cancelarTramite').click(function() {
		removeToSpanErrorForma();
		dialogoConfirmarCancelar.dialog( "open" );
	});
	
	dialogoConfirmar = $( "#dialog-confirm" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		dialogClass: "no-close",
	    closeOnEscape: false
	 });
	
	dialogoConfirmarCancelar = $( "#dialog-confirm-cancelar" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		buttons: {
			"ACEPTAR": function() {
				cancelarTramite();
		 	},
		 	"CANCELAR": function() {
		 		$( this ).dialog( "close" );
		 	}
		 }
	 });
	
});

function cerrarWizard() {	
	parent.WizardModificacionContactoCentroTrabajoCtrl.cerrar();
}

function cancelarTramite() {	
	var idSolicitudPendiente = $('#hdnIdSolicitud').val();	
	var url = urlContacto + '/cancelar/solicitud/' + idSolicitudPendiente;
	
	$.blockUI();	
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
			cerrarWizard();
		}
	}]);
	
	$.postJSON(url, null, function(data) {
		mostrarDialogo(data.mensaje);
	}).error(function(data){
		mostrarDialogo(data.mensaje);
	}).done(function(data){
		$.unblockUI();
	});
}

function mostrarDialogo (mensaje) {
	$('#mensajeDialogo').text(mensaje);
	dialogoConfirmar.dialog('open');
}

function evaluarFinalizarTramite() {
	var errors = evaluarCentroTrabajo();
	if (errors == "") {
		//No hay errores, concluir tramite.		
		parent.FirmaDigitalCtrl.setOnCloseCallback(function() {
			if(parent.FirmaDigitalCtrl.datosSalida == null) {
				mostrarMensaje("La validaci&oacute;n de la firma no pudo ser realizada");
			}else {
				if(parent.FirmaDigitalCtrl.datosSalida.Resultado == 0) {
					var firmaResponse = {
						cadenaOriginal               : parent.FirmaDigitalCtrl.datosSalida.contenedores[0].cadori,
						recibo                       : parent.FirmaDigitalCtrl.datosSalida.firmas[0],
						reciboNotarial               : parent.FirmaDigitalCtrl.datosSalida.folio,
						urlAcuseFirma                : parent.FirmaDigitalCtrl.datosSalida.acuse,
						serialCertificado            : parent.FirmaDigitalCtrl.datosSalida.serie_cert,
						strIniciaVigenciaCertificado : parent.FirmaDigitalCtrl.datosSalida.vigIni,
						strFinVigenciaCertificado    : parent.FirmaDigitalCtrl.datosSalida.vigFin
					};

					firmarTramite(firmaResponse);
				} else {
					mostrarMensaje("La validaci&oacute;n de la firma no pudo ser realizada");
				}
			}
		});
		
		var componenteFirma = {
				idTipoSolicitud : codigoTipoSolicitud,
				descripcionTipoSolicitud : descripcionTipoSolicitud,
				idTipoTramite : arrayCodigoTipoTramite,
				folioSolicitud : $('#folioSolicitud').val(),
				curp : parent.FirmanteCtrl.curp,
				rfc : parent.FirmanteCtrl.rfc,
				validarRFC : true,
				registroPatronal : parent.AtributosPersonaCtrl.personaPortal.registroPatronal,
				nombreCompleto : parent.FirmanteCtrl.nombreRazonSocial,
				fechaElectronica : datosEntradaFirma.fechaElectronica,
				cad_original : $('#contenidoFirmar').val(),
				tipo_operacion : 'firmaCMS',
				firma_archivo : false,
				min_archivos : 0,
				max_archivos : 0,
				afectado: [parent.AtributosPersonaCtrl.personaPortal],
				mediosContacto :construirMedios(),
				tipoAcuse: '1',
				acuse: 'MCCT'
		};		
		parent.iniciarFirmaDigital(componenteFirma);		
	}
}

function firmarTramite(firmaResponse) {
	var url = urlContacto + '/procesarDatosFirma';
		
	$.postJSON(url, firmaResponse, function(data) {
		finalizarTramite();
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
}

function finalizarTramite(){
	
	$.blockUI();
	cntroTrabajo=new Object();
	cntroTrabajo.cveIdPatronSujetoObligado=$("#cveIdSujetoObligado").val();
	cntroTrabajo.mediosContacto=crearArrayMediosCT();

	var folioSolicitudPendiente = $("#folioSolicitud").val();
	var url = urlContacto + '/finalizarSolicitud';
	
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
			cerrarWizard();
		}
	}]);
	
	$.postJSON(url, cntroTrabajo , function(data) {	
		$.unblockUI();
		parent.ProcesandoSolicitudCtrl.abrir(folioSolicitudPendiente);
		cerrarWizard();
	}).error(function(data){
		$.unblockUI();
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
}

function evaluarCentroTrabajo() {
	var temp = "";	
	temp = $("#ctCorreoElectronico").val();
	var telFijoA = $("#ctTelefonoFijo").val();
	var ladaB = $("#ctLada2").val();
	var telFijoB = $("#ctTelefonoFijo2").val();
	var extB = $("#ctExtension2").val();
	removeToSpanErrorForma();
	var errors = "";
	//Valida Telefono (Principal)
	if (isEmpty(telFijoA)) {
		errors += "ERROR";
		$('#telPrincipalError').append('<span id="telPrincipalTmp" class="error customError">Tel&eacute;fono fijo (Principal) es requerido</span>');
		setCampoToFocusOn("#ctTelefonoFijo");
	}	
	//Valida Telefono (Secundario)
	if(!isEmpty(ladaB) && isEmpty(telFijoB)){
		errors += "ERROR";
		$('#telSecuendarioError').append('<span id="telSecuendarioTmp" class="error customError">Debe proporcionar un tel&eacute;fono fijo (Secundario) v&aacute;lido</span>');
		setCampoToFocusOn("#ctTelefonoFijo2");
	}else if(!isEmpty(extB) && isEmpty(telFijoB)){
		errors += "ERROR";
		$('#telSecuendarioError').append('<span id="telSecuendarioTmp" class="error customError">Debe proporcionar un tel&eacute;fono fijo (Secundario) v&aacute;lido</span>');
		setCampoToFocusOn("#ctTelefonoFijo2");
	}
	//Valida Correo Electronico	
	if (isEmpty(temp)) {
		errors += "ERROR";
		$('#correoElectronicoError').append('<span id="correoElectronicoTmp" class="error customError">Correo electr&oacute;nico es requerido</span>');
		setCampoToFocusOn("#ctCorreoElectronico");		
	}else if(!fnValidaCorreo(temp)){
		errors += "ERROR";
		$('#correoElectronicoError').append('<span id="correoElectronicoTmp" class="error customError">Debe proporcionar un correo electr&oacute;nico v&aacute;lido</span>');
		setCampoToFocusOn("#ctCorreoElectronico");
	}
	return errors;
}

function isEmpty(temp) {
	return (temp == undefined || temp == "");
}

function crearArrayMediosCT(){
	var telefonoFijo = new Object();
	telefonoFijo.idVista = 1;
	telefonoFijo.tipoMedioContacto= new Object();
	telefonoFijo.tipoMedioContacto.idTipoMedioContacto=tipoContactoTelefonoFijo;
		
	var lada = $("#ctLada").val()!=undefined ? $("#ctLada").val() :"";
	var tel = $("#ctTelefonoFijo").val()!=undefined ? $("#ctTelefonoFijo").val() : "";
	var ext = $("#ctExtension").val()!=undefined ? $("#ctExtension").val() : "";
	var descripcionTelefono = 	lada+ "|"+tel+"|"+ext;	
	telefonoFijo.desFormaContacto=descripcionTelefono;
	
	var telefonoFijo2 = new Object();
	telefonoFijo2.idVista = 2;
	telefonoFijo2.tipoMedioContacto= new Object();
	telefonoFijo2.tipoMedioContacto.idTipoMedioContacto=tipoContactoTelefonoFijo;
	
	var ladaB = $("#ctLada2").val()!=undefined ? $("#ctLada2").val() :"";
	var telB = $("#ctTelefonoFijo2").val()!=undefined ? $("#ctTelefonoFijo2").val() : "";
	var extB = $("#ctExtension2").val()!=undefined ? $("#ctExtension2").val() : "";
	var descripcionTelefonoB = 	ladaB+ "|"+telB+"|"+extB;
	telefonoFijo2.desFormaContacto=descripcionTelefonoB;
	
	var correo = new Object();
	correo.idVista = 3;
	correo.tipoMedioContacto= new Object();
	correo.tipoMedioContacto.idTipoMedioContacto=tipoContactoCorreoElectronico;
	correo.desFormaContacto=$("#ctCorreoElectronico").val()!=undefined ? $("#ctCorreoElectronico").val() : "";	
	var listMedios=[telefonoFijo, telefonoFijo2, correo];
	
	return listMedios;
}

function parseTelefonos(){
	var arregloTelefonoPrincipal = telefonoPrincipalCompleto.split("|");	
	if(arregloTelefonoPrincipal.length==3){
		var lada = telefonoPrincipalCompleto.split("|")[0];
		var numeroTelefono = telefonoPrincipalCompleto.split("|")[1];
		var extension = telefonoPrincipalCompleto.split("|")[2];
		$("#ctLada").val(lada);
		$("#ctTelefonoFijo").val(numeroTelefono);
		$("#ctExtension").val(extension);		
	}else{		
		$("#ctLada").val('');
		$("#ctTelefonoFijo").val('');
		$("#ctExtension").val('');
	}
	
	var arregloTelefonoSecundario = telefonoSecundarioCompleto.split("|");
	if(arregloTelefonoSecundario.length==3){
		var lada2 = telefonoSecundarioCompleto.split("|")[0];
		var numeroTelefono2 = telefonoSecundarioCompleto.split("|")[1];
		var extension2 = telefonoSecundarioCompleto.split("|")[2];
		$("#ctLada2").val(lada2);
		$("#ctTelefonoFijo2").val(numeroTelefono2);
		$("#ctExtension2").val(extension2);
	}else{
		$("#ctLada2").val('');
		$("#ctTelefonoFijo2").val('');
		$("#ctExtension2").val('');
	}
}

function removeToSpanErrorForma() {
	$('span[id^="telPrincipalTmp"]').remove();
	$('span[id^="telSecuendarioTmp"]').remove();
	$('span[id^="correoElectronicoTmp"]').remove();
}

function setCampoToFocusOn(fieldName) {
	if (campoToFocusOn == undefined)
		campoToFocusOn = $(fieldName);
}

function construirMedios () {	
	var mediosCon = new Array();	
	var correo = {
		"desFormaContacto":$("#ctCorreoElectronico").val(),
		"tipoMedioContacto":{
			"descripcion":"Correo Electronico",
			"idTipoMedioContacto":1
		}
	};	
	mediosCon.push(correo);	
	
	var telefonoFijo = {
		"desFormaContacto": $("#ctLada").val() + ' ' + $("#ctTelefonoFijo").val() + ' ' + $("#ctExtension").val(),
		"tipoMedioContacto":{
			"descripcion":"Telefono Fijo",
			"idTipoMedioContacto":2
		}
	};		
	telefonoFijo.desFormaContacto = $.trim(telefonoFijo.desFormaContacto);	
	mediosCon.push(telefonoFijo);
	
	var telefonoFijo2 = {
		"desFormaContacto": $("#ctLada2").val() + ' ' + $("#ctTelefonoFijo2").val() + ' ' + $("#ctExtension2").val(),
		"tipoMedioContacto":{
			"descripcion":"Telefono Fijo",
			"idTipoMedioContacto":2
		}
	};	
	telefonoFijo2.desFormaContacto = $.trim(telefonoFijo2.desFormaContacto);	
	if(telefonoFijo2.desFormaContacto.length > 0) {
		mediosCon.push(telefonoFijo2);
	}	
	return mediosCon;	
}
