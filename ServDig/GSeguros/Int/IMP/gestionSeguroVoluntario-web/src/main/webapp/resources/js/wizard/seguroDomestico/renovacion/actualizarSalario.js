$.getScript('/${mvn.web.app.root}/static/resources/js/wizard/seguroDomestico/comunes/common.js');

$(document).ready(function() {
	$('#actualizarycotizar').click(function(e) {
		$('#nextStepForm').submit();
	});
	$('#cancelarCotizacion').click(function(e) {
		$('#cancelarForm').submit();
	});
});