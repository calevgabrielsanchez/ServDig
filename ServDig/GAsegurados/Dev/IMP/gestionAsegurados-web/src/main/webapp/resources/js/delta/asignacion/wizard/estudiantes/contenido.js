var dialogoConfirmarCancelar;
var dialogoConfirmarCommon;

$(document).ready(function() {	
	
	$('#finalizarTramite').click(function(e) {
		
		fnHideErrores('#uploadFileForm');
		
		var file = $("#fileData").val();
		
		if (file == '') {    
            $("span#fileDataError").text("Campo requerido");
            fnShowElement("span#fileDataError");
            e.preventDefault();
        }
        else {
            var ext = file.split('.').pop().toLowerCase();
            if ($.inArray(ext, ['xml']) == -1) {
                $("span#fileDataError").text("Archivo inválido");
                fnShowElement("span#fileDataError");
                e.preventDefault();
            }
            else {
            	var firmante = construirFirmante();
        		
        		var componenteFirma = {
        			idTipoSolicitud : codigoTipoSolicitud,
        			descripcionTipoSolicitud : descripcionTipoSolicitud,
        			idTipoTramite : arrayCodigoTipoTramite,
        			folioSolicitud : $('#folioSolicitud').val(),
        			curp : firmante.curp,
        			rfc : firmante.rfc,
        			validarRFC : true,
        			registroPatronal : "",
        			nombreCompleto : firmante.nombreRazonSocial,
        			fechaElectronica : datosEntradaFirma.fechaElectronica,
        			cad_original : contenidoFirmar,
        			tipo_operacion : 'firmaCMS',
        			firma_archivo : false,
        			min_archivos : 0,
        			max_archivos : 0,
        			afectado: [construirAfectado()],
        			tipoAcuse: '1',
        			acuse: 'AP'
        		};
        		
        		parent.iniciarFirmaDigital(componenteFirma);
            }
        }
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
			"ACEPTAR": function() {
				cancelarTramite();
		 	},
		 	"CANCELAR": function() {
		 		$( this ).dialog( "close" );
		 	}
		 }
	 });
	
	dialogoConfirmarCommon = $( "#dialog-confirm-common" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false
	 });
	
	/* 
	 * Al cerrar el diálogo de la firma digital,
	 * se ejecuta la función para finalizar el trámite
	 */
	parent.FirmaDigitalCtrl.setOnCloseCallback(function() {
		dialogoConfirmarCommon.dialog("option", "buttons", [ {
			text : 'ACEPTAR',
			click : function() {
				$(this).dialog('close');
			}
		}]);
		
		if(parent.FirmaDigitalCtrl.datosSalida == null) {
			mostrarDialogo("La validaci\u00f3n de la firma no pudo ser realizada");
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
				mostrarDialogo("La validaci\u00f3n de la firma no pudo ser realizada");
			}
		}
	});

});

function construirFirmante() {
	var firmante = new Object();
	firmante.nombreRazonSocial = parent.AtributosPersonaCtrl.personaFirmada.nombreCompleto;
	firmante.rfc = parent.AtributosPersonaCtrl.personaFirmada.rfc;
	firmante.curp = parent.AtributosPersonaCtrl.personaFirmada.curp;
	
	return firmante;
}

function construirAfectado() {
	var personaAfectada = new Object();
	personaAfectada.nombreRazonSocial = datosEntradaFirma.nombreCompleto;
	personaAfectada.rfc = datosEntradaFirma.rfc;
	personaAfectada.curp = datosEntradaFirma.curp;
	personaAfectada.registroPatronal = datosEntradaFirma.registroPatronal;
	
	return personaAfectada;
}

function firmarTramite(firmaResponse) {
	var url = '/gestionAsegurados-web/asignacion/portal/procesarDatosFirma';

	$.postJSON(url, firmaResponse, function(data) {
		finalizarTramite();
	}).error(function(data){
		mostrarDialogo(data.mensaje);
	});
}

function finalizarTramite() {
	$('form#uploadFileForm').submit();
}

function cancelarTramite() {
	cerrarWizard();
}

function cerrarWizard() {	
	parent.WizardAsignacionNSSEstudiantesCtrl.cerrar();
}

function mostrarDialogo (mensaje) {
	$('#mensajeDialogo').text(mensaje);
	dialogoConfirmarCommon.dialog('open');
}