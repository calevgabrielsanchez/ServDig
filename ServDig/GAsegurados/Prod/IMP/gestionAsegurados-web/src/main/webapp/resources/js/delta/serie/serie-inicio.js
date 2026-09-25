var cleanDatable = false;

$(document).ready(function() {

	CreacionSerieCtrl.setOnCloseCallback(function() { 
		$('form#refreshSeriesForm').submit();
	});
	
	$('#tableSeriesActivas').dataTable({
		"sPaginationType": "bootstrap",
		"aoColumnDefs" : [ {
			"bVisible" : false,
			"aTargets" : [ 0 ]
		} ] 
	});

	$('#crearSerie').click(function() {

		CreacionSerieCtrl.init('creacionSerieDiv');
		CreacionSerieCtrl.iniciarCreacionSerie();
	});
	
	$('#btnLimpiarFormulario').click(function() {
		limpiarFormulario('#consultarSerieForm');
		
	});
});

var objDialogoCtrl = {
	dialogo : {}
};