var urlWizardRegRep= context_path + '/wizard/tramite/representado/registro/',
dialogoConfirmarCancelar,
dialogoConfirmar,
nombreRS = "",
curpPat = null;

$(document).ready(function() {
	// Se incializa el blockUI para las peticiones AJAX
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
	
	var $formBusquedaPersona = $("#busquedaPersona");
	
	if($formBusquedaPersona.length) {
		$formBusquedaPersona.find("#rfc").live(
			'blur', function() {
					quitarEspaciosRfc();
		});
		
		$("#busquedaPersona input").keypress(function(event) {
		    if (event.which == 13) {
		        event.preventDefault();
		        quitarEspaciosRfc();
		        buscarRfc();
		    }
		});
		
		jQuery.validator.addMethod("RFC", function(value, element) {
			let erFisica = /^([a-zA-Z\u00E1\u00E9\u00ED\u00F3\u00FA\u00C1\u00C9\u00CD\u00D3\u00DA\u00E4\u00EB\u00EF\u00F6\u00FC\u00C4\u00CB\u00CF\u00D6\u00DC\u00D1\u00F1]{4})\d{6}([a-zA-Z\w]{3})$/,
			erMoral = /^([a-zA-Z\u00E1\u00E9\u00ED\u00F3\u00FA\u00C1\u00C9\u00CD\u00D3\u00DA\u00E4\u00EB\u00EF\u00F6\u00FC\u00C4\u00CB\u00CF\u00D6\u00DC\u0026\u00D1\u00F1\u005F]{3})\d{6}([\w]{3})$/,
			longitudFisica = 13,
			longitudMoral = 12,
			fisica = false,
			expresionRegular = null;
			
			fisica = $("#tipoPersona\\.idTipoPersona").val() == 1;
			expresionRegular = fisica ? erFisica : erMoral;
			return this.optional( element ) || expresionRegular.test( value );
		}, 'El RFC no tiene el formato correcto');
		
		let funcionValidarLongitudRFC = function() {
			return ($("#tipoPersona\\.idTipoPersona").val() == 1 ? 13 : 12);
		},
		mensajeRFC = "Es necesario que captures las {0} posiciones del RFC";
		
		$formBusquedaPersona.validate({
			errorClass: "errorDocs",
			errorElement: "span",
			rules: { 
				"rfc": {
					required:true,
					RFC: true,
					minlength: funcionValidarLongitudRFC,
					maxlength: funcionValidarLongitudRFC
				}, 
				"tipoPersona.idTipoPersona": {
					min: 1
				}
			}, 
			messages: {
				"rfc": {minlength : mensajeRFC, maxlength: mensajeRFC},
				"tipoPersona.idTipoPersona": {min: LABEL_CAMPO_OBLIGATORIO}
			}
		});
		
		
	}
	
	
	
	$('#finalizarTramite').click(function() {
		firmarRepresentante();
	});
	
	//Siguiente - Seleccion de RL
	$('#mostrarRLVentanilla').click(function() {
		firmarRepresentante();
	});
	
	$('#siguienteFirRepresentado').on('click',buscarRfc);

	$('#guardarTramite').on('click',guardarTramite);

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
		dialogClass: "no-close",
	    closeOnEscape: false,
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
		height:'auto',
		modal: true,
		dialogClass: "no-close",
	    closeOnEscape: false,
		autoOpen: false
	 });
	
	 establecerRepresentado();
	 $("#idTipoPoderError").hide();//Se inicializa mensaje de error de segunda panatalla a oculto
	 $('[data-toggle="tooltip"]').tooltip();
});

/**
 * funcion para recuperar los datos de la empresa a representar
 */
function establecerRepresentado() {
	var personaFisica = typeof $("#sujetoObligado\\.fisica\\.rfc").val() !== 'undefined',
	personaMoral = typeof $("#sujetoObligado\\.moral\\.rfc").val() !== 'undefined',
	representado = new Object();
	
	if(personaFisica && !personaMoral) {
		representado.curp=$("#sujetoObligado\\.fisica\\.curp").val();
		representado.nombreRazonSocial=$("#sujetoObligado\\.fisica\\.nombre").val() + 
		" " + $("#sujetoObligado\\.fisica\\.primerApellido").val() + " " + $("#sujetoObligado\\.fisica\\.segundoApellido").val();
		representado.rfc=$("#sujetoObligado\\.fisica\\.rfc").val();
	} else if (!personaFisica && personaMoral){
		representado.nombreRazonSocial=$("#sujetoObligado\\.moral\\.razonSocial").val();
		representado.rfc=$("#sujetoObligado\\.moral\\.rfc").val();
	}
	
	if(personaFisica || personaMoral) {
		parent.WizardRegistroRepresentadoLegalCtrl.setRepresentado(representado);
	}
}

function quitarEspaciosRfc() {
	var valRfc = $.trim($("#rfc").val().toUpperCase());
	$("#rfc").val(valRfc);
}


function buscarRfc() {
	$("#errorNegocio").html("");
	var $formPersona = $("#busquedaPersona"),
	oForm = $("#busquedaPersona").toObject(),
	url = urlWizardRegRep + 'validaciones',
	representado = new Object();
	
	fnHideErrores("form#busquedaPersona");
	
	if($formPersona.valid()) {
		
		$.postJSON(url, oForm, function(data2) {
			if(data2.negocio.errorFormGeneral == null){
				
				if($("#tipoPersona\\.idTipoPersona").val() == "1") {
					curpPat = data2.negocio.curp;
					nombreRS = data2.negocio.nombre;				
					representado.curp=curpPat;
					representado.nombreRazonSocial=nombreRS;
					representado.rfc=data2.negocio.rfc;				
				} else {
					nombreRS = data2.negocio.razonSocial;				
					if(data2.negocio.tipoSociedad != null && data2.negocio.tipoSociedad.descripcionAbreviada != null) {
						nombreRS += ' ' + data2.negocio.tipoSociedad.descripcionAbreviada;
					}				
					representado.nombreRazonSocial=nombreRS;
					representado.rfc=data2.negocio.rfc;
				}			
				parent.WizardRegistroRepresentadoLegalCtrl.setRepresentado(representado);
				
				if(parent.WizardRegistroRepresentadoLegalCtrl.config.idOrigen == 2){
					//INTERNET: Solicitar firma PF/PM a representar 
					muestraMensajeFirma();	
				}else{
					//VENTANILLA: No mostrar firma y procesar busqueda.
					submitBusquedaPersona();
				}
			} else {
				muestraErrorNegocio(data2.negocio.errorFormGeneral);
			}
		}).error(function(data){
			fnProcesarErrores(data, "form#busquedaPersona");
		});
	}
}

function submitBusquedaPersona() {
	setTimeout(function(){		
		$.blockUI();
		$("#busquedaPersona").submit();
	}, 200);
}


function muestraErrorNegocio(mensaje) {
	
	var errorG = '<div class="alert alert-danger">' +
	'<button type="button" class="close" data-dismiss="alert">x</button>' +
	'<strong>Error: </strong>' + mensaje + '</div>';
	
	$("#errorNegocio").html(errorG);
}

function muestraMensajeFirma() {
	$('#mensajeDialogo').html('A continuaci&oacute;n se te pedir&aacute; la informaci&oacute;n de la FIEL de la empresa a representar');
	dialogoConfirmar.dialog("option", "buttons", [{
		text : 'Cancelar',
		click : function() {
			$(this).dialog('close');
		}
	}, {
		text : 'Aceptar',
		click : function() {
			$(this).dialog('close');
			firmarRepresentado();
		}
	}]);
	
	dialogoConfirmar.dialog('open');
}

function firmarRepresentante() {
	if(!tipoPoderSeleccionado())
		return;
	
	if(parent.WizardRegistroRepresentadoLegalCtrl.config.idOrigen == 2){
		//Internet
		procesarFirmaInternet();
	}else{
		//Ventanilla
		procesarSolicitudVentanilla();
	}
}

function procesarFirmaInternet() {
	
	var rfcRepresentante= parent.WizardRegistroRepresentadoLegalCtrl.config.rfcPersona;
	var representado = parent.WizardRegistroRepresentadoLegalCtrl.getRepresentado();
	
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
	
	
	
	
	var representantesNuevo = [{
			"nombreRazonSocial" : datosEntradaFirma.nombreCompleto,
			'rfc' : datosEntradaFirma.rfc,
			'curp' : datosEntradaFirma.curp
	}];
	
	var componenteFirma = {
		idTipoSolicitud : codigoTipoSolicitud,
		descripcionTipoSolicitud : descripcionTipoSolicitud,
		idTipoTramite : arrayCodigoTipoTramite,
		folioSolicitud : $('#folioSolicitud').val(),
		curp : datosEntradaFirma.curp,
		rfc : datosEntradaFirma.rfc,
		validarRFC : true,
		registroPatronal : datosEntradaFirma.registroPatronal,
		nombreCompleto : datosEntradaFirma.nombreCompleto,
		fechaElectronica : datosEntradaFirma.fechaElectronica,
		cad_original : $('#contenidoFirmar').val(),
		tipo_operacion : 'firmaCMS',
		firma_archivo : false,
		min_archivos : 0,
		max_archivos : 0,
		mostrarCartaTerminos : true,
		afectado: [representado],
		representados: representantesNuevo,
		tipoAcuse: '1',
		acuse: 'ARL'
	};

	parent.iniciarFirmaDigital(componenteFirma);
}

function firmarTramite(firmaResponse) {
	var url = urlWizardRegRep + 'procesarDatosFirma';

	$.postJSON(url, firmaResponse, function(data) {
		finalizarTramite();
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
}

function finalizarTramite() {	
	var idTipoPoder = $("#idTipoPoder").val();
	var idSolicitudPendiente = $('#idSolicitud').val();
	var folioSolicitudPendiente = $("#folioSolicitud").val();
	
	var url = urlWizardRegRep + 'finalizar/solicitud/'+idTipoPoder+'/'+idSolicitudPendiente;
		
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'Aceptar',
		click : function() {
			$(this).dialog('close');
			cerrarWizard();
		}
	}]);
	
	$.postJSON(url, null, function(data) {		
		parent.ProcesandoSolicitudCtrl.abrir(folioSolicitudPendiente);
		cerrarWizard();
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
}

//Funciones para mostrar seleccion de Representantes Legales
function procesarSolicitudVentanilla() {
	var idTipoPoder = $("#idTipoPoder").val();
	var idSolicitudPendiente = $('#idSolicitud').val();
	var folioSolicitudPendiente = $("#folioSolicitud").val();
	
	var url = urlWizardRegRep + 'finalizar/solicitud/'+idTipoPoder+'/'+idSolicitudPendiente;
		
	prepararRequest(url, null, true, mostrarRepresentantesLegales);
	
}

function mostrarRepresentantesLegales(response){
	if (response.mensaje != undefined && response.mensaje != null) {
		var idSolicitudPendiente = $('#idSolicitud').val();
		setTimeout(function(){
			var urlAction = parent.representantesLegalesCtrl.config.urlRLVentanilla + idSolicitudPendiente+"/"+idTipoTramite;
			$.blockUI();
			$('form#soForm').attr('action', urlAction);
			$('form#soForm').submit();
		}, 90);
	}else{
		var mensaje = "Ocurrio un error al procesar la solicitud.";
		if (response.error == "") {			
		} else {
			mensaje = response.error;
		}
		mostrarDialogoH(mensaje);
	}	
}

function tipoPoderSeleccionado(){
	var tipoPoder = $("#idTipoPoder").val();
	if(tipoPoder==undefined
		||(tipoPoder!=undefined && tipoPoder<=0)){
		$("#idTipoPoderError").show();
		return false;
	}else{
		$("#idTipoPoderError").hide();
		return true;
	}
}

function guardarTramite() {	
	$('#mensajeDialogo').html("Se ha guardado correctamente el tr&aacute;mite");

	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'Aceptar',
		click : function() {
			$(this).dialog('close');
		}
	}]);
	
	dialogoConfirmar.dialog('open');	
}

function firmarRepresentado() {
	var rfcRepre= $('#rfc').val();
	
	parent.FirmaDigitalCtrl.setOnCloseCallback(function() {
		
		if(parent.FirmaDigitalCtrl.datosSalida == null) {
			mostrarMensaje("La validaci&oacute;n de la autenticaci&oacute;n no pudo ser realizada");
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
								
				var url = urlWizardRegRep + 'procesarDatosFirmaRepresentado';
						 
				var request = $.ajax({
					url : urlWizardRegRep + 'procesarDatosFirmaRepresentado',
					async : false,
					type : "POST",
					data : JSON.stringify(firmaResponse),
					dataType : "json",
					contentType : "application/json; charset=utf-8"
				});
				
				submitBusquedaPersona();
				
			} else {
				mostrarMensaje("La validaci&oacute;n de la autenticaci&oacute;n no pudo ser realizada");
			}
		}
	});
	
	var fechaSistema = new Date();
	var rfcRepresentanteL = parent.WizardRegistroRepresentadoLegalCtrl.config.rfcPersona;
	var curpRepresentanteL = parent.WizardRegistroRepresentadoLegalCtrl.config.curpPersona;
	var cadenaOriginalRepresentado = getCadenaOriginalPatron(rfcRepre);
	var afectado = parent.WizardRegistroRepresentadoLegalCtrl.getRepresentado();
	
	var representantesNuevo = [{
		'nombreRazonSocial' : parent.FirmanteCtrl.nombreRazonSocial,
		'rfc' : parent.FirmanteCtrl.rfc,
		'curp' : parent.FirmanteCtrl.curp
	}];
	
	var componenteFirma = {
		idTipoSolicitud : codigoTipoSolicitud,
		descripcionTipoSolicitud : descripcionTipoSolicitud,
		folioSolicitud : '',
		idTipoTramite : arrayCodigoTipoTramite,
		curp : afectado.curp,
		rfc : afectado.rfc,
		validarRFC : true,
		registroPatronal : '',
		nombreCompleto : afectado.nombreRazonSocial,
		fechaElectronica : dateFormat(fechaSistema, "dd/mm/yy"),
		cad_original : cadenaOriginalRepresentado,
		tipo_operacion : 'firmaCMS',
		firma_archivo : true,
		min_archivos : 1,
		max_archivos : 6,
		afectado: [afectado],
		representados: representantesNuevo,
		tipoAcuse: '1',
		acuse: 'ARL'
	};

	parent.iniciarFirmaDigital(componenteFirma);
}

function cancelarTramite() {
	
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'Aceptar',
		click : function() {
			$(this).dialog('close');
			cerrarWizard();
		}
	}]);
	
	var idSolicitudPendiente = $('#idSolicitud').val();
	var url = urlWizardRegRep + 'cancelar/solicitud/'+idSolicitudPendiente;
	
	$.postJSON(url, null, function(data) {
		$('#mensajeDialogo').html(data.mensaje);
		dialogoConfirmar.dialog( "open" );
	}).error(function(data){
		$('#mensajeDialogo').html(data.mensaje);
		dialogoConfirmar.open();
	});
}

function mostrarMensaje(mensaje) {
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'Aceptar',
		click : function() {
			$(this).dialog('close');
		}
	}]);
	
	$('#mensajeDialogo').html(mensaje);
	dialogoConfirmar.dialog('open');
}

function cerrarWizard() {	
	parent.WizardRegistroRepresentadoLegalCtrl.cerrar();
}

function getCadenaOriginalPatron(rfcPatron) {
	var cadenaOriginalPatron = "||";
	
	cadenaOriginalPatron += "Invocante:portalimssdigital|";
	cadenaOriginalPatron += "Tramite:"+descripcionTipoSolicitud+"|";
	cadenaOriginalPatron += "Fecha:"+dateFormat(new Date(),"dd 'de' mmmm yyyy, HH:MM:ss")+"|";
	cadenaOriginalPatron += "RFC:"+rfcPatron+"|Nombre o Razon Social:"+nombreRS+"";
	if(curpPat!= null) {
		cadenaOriginalPatron += "|CURP:"+curpPat;
	}
	cadenaOriginalPatron += "||";
	
	return cadenaOriginalPatron;
}