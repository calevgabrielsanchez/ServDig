$.getScript('/${mvn.web.app.root}/static/resources/js/wizard/seguroDomestico/comunes/common.js');

$(document).ready(function() {
	$('#cancelarSolicitudAlta').click(function() {
		if(ventanilla && tieneSeguros)
			$('#formBackToVentanillaMain').submit();
		else
			closeWizard();
	});
	
	$('#siguientePaso').click(function() {
		$('#nextStepForm').submit();
	});
	
	$('#agregarTrabajador').click(function(event) {
		event.preventDefault();
		$('#agregarTrabajadorForm').submit();
	});
	
	$('#tblTrabajadores').dataTable({
		'bFilter': false,
		'bDestroy': true,
		'bLengthChange': false,
		'bAutoWidth': false,
		'bPaginate' : false,
		'bInfo': false,
		/*'sPaginationType': 'none',*/
		'aoColumnDefs': [{'sSortDataType': 'html', 'sType': 'html', 'aTargets': [0]},
		                 {'bSortable': false, 'aTargets': [4]}],
		'aoColumns' : [{'sWidth': '20%'},
	                   {'sWidth': '35%'},
	                   {'sWidth': '15%'},
	                   {'sWidth': '15%'},
	                   {'sWidth': '15%'}]
		}	
	);
	$('.linkQuitar').click(function(event) {
		$('#inputIndex').val(event.currentTarget.id);
		$('#quitarTrabajadorForm').submit();
	});
});