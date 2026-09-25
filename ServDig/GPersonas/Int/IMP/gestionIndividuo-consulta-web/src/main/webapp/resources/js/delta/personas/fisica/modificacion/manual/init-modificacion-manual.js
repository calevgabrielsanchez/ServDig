var objCtrl = parent.ModificacionManualDatosFisicaCtrl;

$(document).ready(function() {

	$('#busquedaIdPersona').val(objCtrl.datosEntrada.idPersona);
	
	$('#indCapturaNombre').val(objCtrl.datosEntrada.indCapturaNombre);
	$('#indCapturaCURP').val(objCtrl.datosEntrada.indCapturaCURP);
	$('#indCapturaSexo').val(objCtrl.datosEntrada.indCapturaSexo);
	$('#indCapturaFechaNacimiento').val(objCtrl.datosEntrada.indCapturaFechaNacimiento);
	$('#indCapturaLugarNacimiento').val(objCtrl.datosEntrada.indCapturaLugarNacimiento);
	$('#indCapturaDocumentoProbatorio').val(objCtrl.datosEntrada.indCapturaDocumentoProbatorio);
	
	$('#indCapturaRFC').val(objCtrl.datosEntrada.indCapturaRFC);
	$('#indCapturaDomicilioFiscal').val(objCtrl.datosEntrada.indCapturaDomicilioFiscal);
	$('#indCapturaMediosContactoFiscales').val(objCtrl.datosEntrada.indCapturaMediosContactoFiscales);
	
	$('#indCapturaDomicilioParticular').val(objCtrl.datosEntrada.indCapturaDomicilioParticular);
	$('#indCapturaMediosContactoParticular').val(objCtrl.datosEntrada.indCapturaMediosContactoParticular);
		
	$('#indAutorizacion').val(objCtrl.datosEntrada.indAutorizacion);	
	
	fnMDMPantalla();
});

// Ir al servicio de MDM
var fnMDMPantalla = function() {
	$('#forma').submit();
};
