$(document).ready(function() {
	var mensaje = 'Actualmente ya tiene una solicitud de Correcci\u00f3n de Datos registrada e iniciada.';
	var url = contextPath + "/wizard/correccionDatosAsegurado/validar/noExisteSolicitud";
	
	$popup = $('<div></div>');
	$popup.append(mensaje);

	$popup.dialog({
		autoOpen : false,
		title : 'Mensaje',
		show : "blind",
		modal : true,
		height : 250,
		width : 450,
		closeOnEscape: true,
		open: function(event, ui) {
			$(".ui-dialog-titlebar-close", ui.dialog | ui).hide();
		},
		buttons : {
			"Aceptar" : function() {
				 var url = context_path
			      + '/wizard/atencionResponsable';
				 $('form#consultaRenapoForm').attr('action', url);
				 $('form#consultaRenapoForm').submit();
			}
		}
	});
	
	$('#continuarCarpturarDomicilio').click(function() {
		var curp = $('#curp').val();
		var isFromReturn = $('#isFromReturn').val();
		var request = {
			type : "POST",
			url : url,
			dataType : 'json',
			data : { 'curp': curp, 'isFromReturn': isFromReturn},
			success: function(response) {
				if (response.noExisteSolicitud === true) {
					$('#consultaRenapoForm').submit();
				} else {
					$popup.dialog('open');
				}
			},
			error: function(errorResponse) {
				// console.error('ERROR: ', errorResponse);
			}
		};
		$.ajax(request);
	});

});
