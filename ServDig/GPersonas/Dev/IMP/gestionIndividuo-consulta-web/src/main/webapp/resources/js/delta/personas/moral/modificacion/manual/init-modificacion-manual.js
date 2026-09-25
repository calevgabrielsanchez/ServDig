var objCtrl = parent.ModificacionManualDatosMoralCtrl;

$(document).ready(function() {

	$('#busquedaIdPersona').val(objCtrl.datosEntrada.idPersona);
	
	$('#indCapturaRFC').val(objCtrl.datosEntrada.indCapturaRFC);
	$('#indCapturaDomicilioFiscal').val(objCtrl.datosEntrada.indCapturaDomicilioFiscal);
	$('#indCapturaMediosContactoFiscales').val(objCtrl.datosEntrada.indCapturaMediosContactoFiscales);
	$('#indCapturaRazonSocial').val(objCtrl.datosEntrada.indCapturaRazonSocial);
	$('#indCapturaFechaConstitucion').val(objCtrl.datosEntrada.indCapturaFechaConstitucion);
	$('#indCapturaTipoSociedad').val(objCtrl.datosEntrada.indCapturaTipoSociedad);
	
	$('#indCapturaActaConstitutiva').val(objCtrl.datosEntrada.indCapturaActaConstitutiva);
	$('#indCapturaRegistroSindicato').val(objCtrl.datosEntrada.indCapturaRegistroSindicato);
	
	$('#indAutorizacion').val(objCtrl.datosEntrada.indAutorizacion);	
	
	fnMDMPantalla();
});

// Ir al servicio de MDM
var fnMDMPantalla = function() {
	$('#forma').submit();
};
