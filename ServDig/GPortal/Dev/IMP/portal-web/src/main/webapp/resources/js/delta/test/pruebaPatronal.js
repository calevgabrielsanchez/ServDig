

$(document).ready(function() {
	$('#formDetalleSujetoObligado').submit(function() {
		var rfc = $('#rfcpatron').val();
		var request = $.ajax({
			data: rfc? JSON.stringify(rfc) : null,
			async : false,
			url : context_path + '/portal/patronal/obtenerDetallePatron',
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			type: 'post'
		});

		request.done(function(response) {
			alert("response: " + response);
		});
		request.fail(function(response) {
			if(response.responseText){
				alert("Error: " + response.responseText);
			} else {
				alert("Error en el servicio");
			}
		});
		return false;
	});
});