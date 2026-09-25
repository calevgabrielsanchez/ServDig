$.getScript('/${mvn.web.app.root}/static/resources/js/wizard/seguroDomestico/comunes/common.js');

$(document).ready(function() {
	$('#cancelarSolicitudRenovacion').click(function() {
		if(ventanilla)
            parent.WizardIVROVentanillaSeguroDomesticoCtrl.abrir();
		else
			closeWizard();
	});
	
	$('#siguientePaso').click(function() {
		$('#nextStepForm').submit();
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
	$('.linkActualizarSalario').click(function(event) {
		$('#inputIndex').val($(event.currentTarget).attr('index'));
		$('#actualizarSalarioForm').submit();
	});
});