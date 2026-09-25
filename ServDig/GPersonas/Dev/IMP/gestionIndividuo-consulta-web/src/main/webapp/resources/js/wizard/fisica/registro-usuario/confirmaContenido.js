var dialogoConfirmarCancelar;
var dialogoConfirmar;
var dialogoConfirmarConMsg;
var dialogoAcceso;
var curpLocal;
var serialCertificado;
$(document).ready(function() {
	// Se incializa el blockUI para las peticiones AJAX
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
	

	$('#guardaTramite').click(function() {
		
		dialogoConfirmarConMsg.dialog( "open" );
		
		
	});

	
	$('#cancelarTramite').click(function() {
		dialogoConfirmarCancelar.dialog( "open" );
	});

	
	dialogoConfirmarCancelar = $( "#dialog-confirm-cancelar" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		buttons: {
			"ACEPTAR": function() {
				cerrarWizard();
		 	},
		 	"CANCELAR": function() {
		 		$( this ).dialog( "close" );
		 	}
		 }
	 });
	
	dialogoConfirmar = $( "#dialog-confirm" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false
	 });
	
	dialogoAcceso = $( "#dialog-confirm" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		dialogClass: "no-close",
		close:function () {
			$(this).dialog('close');
			parent.WizardRegistroUsuarioCtrl.acceso(curpLocal, serialCertificado);
			cerrarWizard();
		}
	 });
	
	dialogoConfirmarConMsg = $( "#dialog-confirm-msg" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		buttons: {
			"ACEPTAR": function() {
				
				guardarTramite();
				$(this).dialog('close');
		 	},
		 	"CANCELAR": function() {
		 		cerrarWizard();
		 	}
		 	
		 }
	 });
	
	

});

function construirAfectado() {
	var personaAfectada = new Object();
	personaAfectada.nombreRazonSocial = datosEntradaFirma.nombreCompleto;
	personaAfectada.rfc = datosEntradaFirma.rfc;
	personaAfectada.curp = datosEntradaFirma.curp;
	
	var arrayAfectados = [personaAfectada];
	return arrayAfectados;
}

function guardarTramite() {
	//var idSolicitudPendiente = $('#idSolicitud').val();

	
	
	parent.FirmaDigitalCtrl.setOnCloseCallback(function() {
		if(parent.FirmaDigitalCtrl.datosSalida == null) {
			mostrarMensaje("La validaci&oacute;n de la firma no pudo ser realizada");
		}else {
			if(parent.FirmaDigitalCtrl.datosSalida.Resultado == 0) {
				var rfcFirma = parent.FirmaDigitalCtrl.datosSalida.rfc;
				var curpFirma = parent.FirmaDigitalCtrl.datosSalida.curp;
				var rfcLocal = $('#rfc').val();
				 curpLocal = $('#curp').val();
				 serialCertificado  = parent.FirmaDigitalCtrl.datosSalida.serie_cert;
				 /*
				if(rfcLocal.toUpperCase() != rfcFirma.toUpperCase() || curpLocal.toUpperCase() != curpFirma.toUpperCase()){
					mostrarMensaje("No coinciden los datos estadisticos con los del certificado");
				}else{
					
				}*/
				
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
		
		//salvaForma();
		
	});

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
		cad_original :  $('#contenidoFirmar').val(),
		tipo_operacion : 'firmaCMS',
		firma_archivo : false,
		min_archivos : 0,
		max_archivos : 0,
		mostrarCartaTerminos : true,
		afectado: construirAfectado(),
		acuse: 'RUCD',
		tipoAcuse: 1
	};

	parent.iniciarFirmaDigital(componenteFirma);
	
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
			cerrarWizard();
		}
	}]);
	
	dialogoAcceso.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
			parent.WizardRegistroUsuarioCtrl.acceso(curpLocal, serialCertificado);
			cerrarWizard();
		}
	}]);
	
		
}

function firmarTramite(firmaResponse) {
	var url = '/gestionIndividuo-consulta-web/wizard/tramite/registro/usuario/procesarDatosFirma';

	$.postJSON(url, firmaResponse, function(data) {
		salvaForma();
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
}

function salvaForma(){
	var url = '/gestionIndividuo-consulta-web/wizard/tramite/registro/usuario/guardar/solicitud';
	var datosSalida;
	var oForm = $("form#solicitudForm").toObject();
	
	$.postJSON(url, oForm, function(data) {
		/*
		$('#mensajeDialogo').text(data.mensaje);
		dialogoAcceso.dialog('open');*/
		if(!data.error) {
			datosSalida = {
				'mensaje': data.mensaje,
				'usuario': {
					'curp': curpLocal,
					'serial': serialCertificado
				}
			};
		} else {
			datosSalida = {
				'mensaje': data.mensaje,
				'usuario': null
			} ;
		}
		
		parent.WizardRegistroUsuarioCtrl.setDatosSalida(datosSalida);
		parent.WizardRegistroUsuarioCtrl.cerrar();
	}).error(function(data){
		/*
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
		*/
		datosSalida = {
			'mensaje': data.mensaje,
			'usuario': null
		} ;
		parent.WizardRegistroUsuarioCtrl.setDatosSalida(datosSalida);
		parent.WizardRegistroUsuarioCtrl.cerrar();
	});

	
}

function mostrarMensaje(mensaje) {
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
		}
	}]);
	
	$('#mensajeDialogo').html(mensaje);
	dialogoConfirmar.dialog('open');
}


function cerrarWizard() {	
	parent.WizardRegistroUsuarioCtrl.cerrar();
}