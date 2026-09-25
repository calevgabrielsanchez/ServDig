var dialogoMensajes;

$(function() {
	
	dialogoMensajes = $( "#dialog-mensajes" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		buttons: {
			"ACEPTAR": function() {
				$( this ).dialog( "close" );
		 	}
		 }
	 });
	
	$('#confirmarSolicitud').click(function() {
		var umf = $("input[name='idUmfRadio']:checked").val();
				
		if (umf != null && typeof umf  !== 'undefined' && umf != '') {

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
				max_archivos : 0
			};

			parent.iniciarFirmaDigital(componenteFirma);
		} else {
			$('#mensajeDialogo').text('La UMF es requerida');
			dialogoMensajes.dialog('open');
		}
	});

	$('#cerrarWizard').click(function() {
		cerrarWizard();
	});	
	
	parent.FirmaDigitalCtrl.setOnCloseCallback(function() {
		dialogoMensajes.dialog("option", "buttons", [ {
			text : 'ACEPTAR',
			click : function() {
				$(this).dialog('close');
			}
		}]);
		
		
		if(parent.FirmaDigitalCtrl.datosSalida == null) {
			$('#mensajeDialogo').text("La validaci\u00f3n de la firma no pudo ser realizada");
			dialogoMensajes.dialog('open');
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
				$('#mensajeDialogo').text("La validaci\u00f3n de la firma no pudo ser realizada");
				dialogoMensajes.dialog('open');
			}
		}
	});
	
	if ($('form#asentamientoForUmfForm').length > 0) {
		obtenerUMFs();
	} else {
		$('div#umfContenedor').html('No se cuenta con la información necesaria para obtener las Unidades Médico Familiar');
	}
});

function firmarTramite(firmaResponse) {
	var url = '/gestionAsegurados-web-externo/wizard/nss/procesarDatosFirma';

	$.postJSON(url, firmaResponse, function(data) {
		confirmarSolicitud();
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
}

function confirmarSolicitud() {
	$('#finalizarNSSForm').submit();
}

function cerrarWizard() {	
	parent.WizardAsignacionNSSCtrl.cerrar();
}

function obtenerUMFs() {
	var oForm = $('#asentamientoForUmfForm').serialize();
	
	var url = '/gestionDomicilios-web/widget/domicilio/utility/consulta/umf/asentamiento';
	
	$.post(url, oForm, function(data){
		$('#umfContenedor').html(data);
		
		// Se settea la función de callback para las UMF
		setFnCallback(fnSeleccionarUMF);
	}).error(function (data){
		$('#umfContenedor').html("<span>Existió un error al cargar las UMF's</span>");
	});
}

var fnSeleccionarUMF = function () {

	//idUMF|noEconomico|idDelegacion|cveDelegacion|idSubdelegacion|cveSubdelegacion|cveCiz;
	var umfArray = umfSeleccionada.split('|');
	
	$('#idUmfAsegurado').val(umfArray[0]);
	$('#noEconomicoUmfAsegurado').val(umfArray[1]);
	$('#idDelegacionAsegurado').val(umfArray[2]);
	$('#cveDelegacionAsegurado').val(umfArray[3]);
	$('#idSubdelegacionAsegurado').val(umfArray[4]);
	$('#cveSubdelegacionAsegurado').val(umfArray[5]);
	$('#cveCizAsegurado').val(umfArray[6]);
	
};