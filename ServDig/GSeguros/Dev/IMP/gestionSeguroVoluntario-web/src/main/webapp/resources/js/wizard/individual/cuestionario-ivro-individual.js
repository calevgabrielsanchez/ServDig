var dialogIVROTCCuestionario;

$(document).ready(function() {
	
	dialogIVROTCCuestionario = $('#divTCCuestionario').dialog({
		title: 'T&eacute;rminos y Condiciones',
		appendTo: parent.document,
		resizable: false,
		height: 400,
		width: 'auto',
		modal: true,
		autoOpen: false,
	    closeOnEscape: false
	 });
	
	$('#cerrarTCCuestionario').click(function() {
		dialogIVROTCCuestionario.dialog('close');
	});
	
	var _cuestionario = $('#cuestionarioContainer').cuestionario({
		idCuestionario: 1,
		formId: 'formCuestionarioIvro',
		onSuccess: function () {
		}
	});	
	
    $('#chkTCCuestionario').change(function() {
    	if($(this).prop('checked'))
    		$('#siguienteCuestionario').show();
    	else
    		$('#siguienteCuestionario').hide();
    });
    $('#linkTCCuestionario').click(function() {
    	dialogIVROTCCuestionario.dialog('open');
    });
    
    $("#siguienteCuestionario").live('click', function(event) {
      event.preventDefault();
      _cuestionario.cuestionario('validar');
    });
    
    $("#cerrarWizard, #cerrarWizardError").live('click', function(event) {
      event.preventDefault();
      parent.WizardSeguroIvroIndivCtrl.cerrar();
    });

});