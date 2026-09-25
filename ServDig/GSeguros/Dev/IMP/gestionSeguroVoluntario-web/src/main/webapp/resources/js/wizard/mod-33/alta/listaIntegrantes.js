$.getScript('/${mvn.web.app.root}/static/resources/js/wizard/mod-33/comunes/common.js');

$(document).ready(function() {
			
	$('.error').hide();
	
	$('#cerrar').click(function() {
		closeWizard();
	});
	
	$('#siguientePaso').click(function() {
		
		if ($('input#soloSolicitanteChk').length > 0) {
			$('input#soloSolicitante', 'form#nextStepForm').val(
					$('input#soloSolicitanteChk').is(':checked'));
		} else {
			$('input#soloSolicitante', 'form#nextStepForm').val(false);
		}
		
		$('#nextStepForm').submit();
	});
	
	$('button#btnAgregarIntegrante').on('click', function(event) {
		event.preventDefault();
		
		$('.error', 'form#agregarIntegranteForm').hide();
		
		var curpRegex = /^([a-zA-Z]{4})\d{6}([a-zA-Z]{6}[a-zA-Z0-9]{2})$/;
		var nssRegex = new RegExp("\\d{11}");
		var _form = $('form#agregarIntegranteForm');
		
		var curp = $('input#curpFamiliar', _form).val().toUpperCase();		
		var nss = $('input#nssFamiliar', _form).val();
		
		var error = false;
		
		if (nss == '') {
			$('#nssFamiliarError', _form).text('Campo requerido');
			$('#nssFamiliarError', _form).show();
			error = true;
		} else if (!nssRegex.test(nss)) {
			$('#nssFamiliarError', _form).text('Formato inv\u00e1lido');
			$('#nssFamiliarError', _form).show();
			error = true;
		}
		
		if (curp == '') {
			$('#curpFamiliarError', _form).text('Campo requerido');
			$('#curpFamiliarError', _form).show();
			error = true;
		} else if (!curpRegex.test(curp)) {
			$('#curpFamiliarError', _form).text('Formato inv\u00e1lido');
			$('#curpFamiliarError', _form).show();
			error = true;
		}
		
		if($('select#idParentescoFamiliar option:selected').val() == -1) {
			$('#idParentescoFamiliarError', _form).text('Campo requerido');
			$('#idParentescoFamiliarError', _form).show();
			error = true;
		}
				
		if (!error) {
			$('input#curpFamiliar', _form).val(curp);
			_form.submit();
		} else {
			setSizeWithinIframe(document);
		}
	});
	
	$('button#btnLimpiarIntegrante').on('click', function(event){
		event.preventDefault();
		
		$('.error', 'form#agregarIntegranteForm').hide();
		document.getElementById('agregarIntegranteForm').reset();
		
	});
	
	$('.eliminarIntegrante').live('click', function(e){
		e.preventDefault();
		
		$('form#quitarIntegranteForm input#inputIndex').val($(this).attr('idx'));
		
		$('form#quitarIntegranteForm').submit();
	});
	
	$('#tblIntegrantes').dataTable({
		'bFilter': true,
		'bDestroy': true,
		'bLengthChange': false,
		'bAutoWidth': false,
		'bPaginate' : true,
		'bInfo': true,
		'bSort': false,
		'sPaginationType': 'bootstrap',
		'aoColumns' : [{'sWidth': '25%'},
	                   {'sWidth': '20%'},
	                   {'sWidth': '20%'},
	                   {'sWidth': '20%'},
	                   {'sWidth': '15%'}]
		}	
	);
	
	$('.linkQuitar').click(function(event) {
		$('#inputIndex').val(event.currentTarget.id);
		$('#quitarTrabajadorForm').submit();
	});
	
	$('input#soloSolicitanteChk').on('change', function(event) {
		event.preventDefault();
				
		if($(this).is(':checked')) {

			var _form = $('form#agregarIntegranteForm');
			
			$('input#curpFamiliar', _form).val('');
			$('input#nssFamiliar', _form).val('');
			$('select#idParentescoFamiliar').val('-1');
			
			$('.error', _form).hide();
			
			$('input, button, select', 'form#agregarIntegranteForm')
			.not('#soloSolicitanteChk').prop('disabled', true);
			
			$('input, button, select', 'div#tblIntegrantes_wrapper')
			.not('#soloSolicitanteChk').prop('disabled', true);
			
		} else {
			
			$('input, button, select', 'form#agregarIntegranteForm')
			.not('#soloSolicitanteChk').prop('disabled', false);
			
			$('input, button, select', 'div#tblIntegrantes_wrapper')
				.not('#soloSolicitanteChk').prop('disabled', false);
		}
	});
	
	$('input#soloSolicitanteChk').trigger("change");
});