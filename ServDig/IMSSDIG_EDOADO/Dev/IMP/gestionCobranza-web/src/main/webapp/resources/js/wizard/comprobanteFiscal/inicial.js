
$(document).ready(function() {
	$('#btnInicioCancelarTramite').click(function(){
		cancelarInicioTramite();
	});

	$('#btnInciaTramite').click(function() {
		$.blockUI();
		var periodo = $('#anioPeriodo').val() + $('#mesPeriodo').val();
		$('#periodo').val(periodo);
		document.getElementById('busquedaComprobanteFiscal').submit();
	});
});

function cancelarInicioTramite() {
	parent.WizardComprobanteFiscalCtrl.cerrar(); 
}