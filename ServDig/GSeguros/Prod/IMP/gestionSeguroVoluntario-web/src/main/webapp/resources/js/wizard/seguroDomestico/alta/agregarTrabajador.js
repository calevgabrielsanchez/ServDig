$.getScript('/${mvn.web.app.root}/static/resources/js/wizard/seguroDomestico/comunes/common.js');

$(document).ready(function() {
	$('#buscarCotizar').click(function(e) {
		$('#nextStepForm').submit();
	});
	$('#buscarCotizar').on('keyup', function(e) {
	    if (e.which == 13) {
	    	$('#nextStepForm').submit();
	    }
	});
	$('#cancelarCotizacion').click(function(e) {
		$('#cancelarForm').submit();
	});
	$('#sueldoDiarioTrabajador').numeric({maxDecimalPlaces : 2, allowThouSep: false, allowMinus: false});
});