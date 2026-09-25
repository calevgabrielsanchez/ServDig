var dialogoConfirmarErrorInesperado;
var dialogoConfirmarError;
var urlRissPublico = '/altaPublica/riss/';
	
$(function() {
	
	dialogoConfirmarErrorInesperado = $("#dialog-confirm-Error").dialog({
		resizable : false,
		height : 160,
		modal : true,
		autoOpen : false,
		closeOnEscape: false,
		open: function(event, ui) { $('#dialog-confirm-Error').parent().find('a.ui-dialog-titlebar-close').hide();},
		buttons : {
			"ACEPTAR" : function() {				
				window.location = context_path + urlRissPublico + 'iniciar'; 
			}
		}
	});
	
	dialogoConfirmarError = $("#dialog-confirm-Error").dialog({
		resizable : false,
		height : 160,
		modal : true,
		autoOpen : false,
		closeOnEscape: false,
		open: function(event, ui) { $('#dialog-confirm-Error').parent().find('a.ui-dialog-titlebar-close').hide();},
		buttons : {
			"ACEPTAR" : function() {				
				$( this ).dialog( "close" );
			}
		}
	});
	
	$('button#btnValidarPatron').click(function(e) {
		e.preventDefault();
		$('form#validarRissForm').submit();		
	});
	
	$('button#btnValidarFisicas').click(function(e) {
		e.preventDefault();
		removeToSpanErrorRFC();
		var url	 			 = context_path + urlRissPublico + 'validar/fisica';
		var datosAdicionales = context_path + urlRissPublico + 'datosAdicionales/fisica';
		var rfc = "";
		var validaRfc="true";
		
		//Obtener rfc de la persona fisica o sino tiene, el que se captura
		if($('#rfcObligatorio').val() == undefined){
			rfc=$('#rfcFisica').val()+"";
			validaRfc="false";
		}else{
			rfc=$('#rfc').val()+"";
		}
		if(rfc.trim() != ""){
			$.blockUI();
			//Validar si el RFC tiene registros patronales asociados
			var sSource = context_path + urlRissPublico + "validarNRPsAsociados/";
			$.ajax({
				type: "GET",
				url : sSource + rfc + '/' + validaRfc,		
				success: function(result) {
					$.unblockUI();
					if (undefined != result.error &&
						result.error == "") {				
						if ((result.tienePatrones==true)) {
							url = datosAdicionales;
						}
						$('form#validarRissForm').attr('action',url);
						$('form#validarRissForm').submit();					
					}else{
						$('#mensajeDialogoError').text("Error: " +result.error);
						dialogoConfirmarError.dialog("open");						
					}
				},
				error: function(errorThrown){
					$('#mensajeDialogoError').text("Ocurrió un error inesperado, favor de intentar nuevamente.");
					dialogoConfirmarErrorInesperado.dialog("open");
					$.unblockUI();
				}
			});
		}else{
			$('#controlError').append('<span id="rfcErrorsTmp" class="error customError">Campo requerido</span>');	
		}
	});

	$('button#bntCancelar').click(function(e) {
		e.preventDefault();
		$('form#formCancelar').submit();
	});

	if ($('input#rfc').length > 0) {
		$('input#rfc').focus();
	}
});


function removeToSpanErrorRFC() {
	$('span[id^="rfcErrorsTmp"]').remove();
	$('span[id^="rfc.errors"]').remove();
}
