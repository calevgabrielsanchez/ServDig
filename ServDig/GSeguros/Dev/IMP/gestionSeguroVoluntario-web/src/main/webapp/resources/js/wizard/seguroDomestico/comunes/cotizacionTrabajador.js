$.getScript('/${mvn.web.app.root}/static/resources/js/wizard/seguroDomestico/comunes/common.js');

$(document).ready(function() {
	$('#cancelarTramite').click(function() {
		$('#cancelarCotizacion').submit();
	});

	$('#siguientePaso').click(function(e) {
		$('#nextStepForm').submit();
	});
	
	$("#amortizacionesSeguro").dataTable({
		'bFilter': false,
		'bDestroy': true,
		'bLengthChange': false,
		'bAutoWidth': false,
		'bPaginate' : false,
		'bInfo': false,
		'aoColumns' : [{'sWidth': '25%'},
	                   {'sWidth': '25%'},
	                   {'sWidth': '25%'},
	                   {'sWidth': '25%'}]
	});
});