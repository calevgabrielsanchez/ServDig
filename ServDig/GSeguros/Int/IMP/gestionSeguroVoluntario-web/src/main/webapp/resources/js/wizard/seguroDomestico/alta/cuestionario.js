$.getScript('/${mvn.web.app.root}/static/resources/js/wizard/seguroDomestico/comunes/common.js');
$.getScript('/${mvn.web.app.root}/static/resources/js/wizard/comunes/TCCuestionarioControl.js');

$(document).ready(function() {
	var _cuestionario = $('#cuestionarioContainer').cuestionario({
		idCuestionario: 1,
		formId: 'nextStepForm',
		onSuccess: function () {
		}
	});
	
	$('#siguientePaso').hide();
	
	$('#siguientePaso').click(function(e) {
		e.preventDefault();
		_cuestionario.cuestionario('validar');
	});
	$('#chkTCCuestionario').change(function() {
		if($(this).prop('checked'))
			$('#siguientePaso').show();
		else
			$('#siguientePaso').hide();
	});
	$('#linkTCCuestionario').click(function() {
		dialogIVROTCCuestionario.dialog('open');
	});
	$('#cancelarProceso').click(function() {
		$('#cancelarProcesoForm').submit();
	});
});