var objDatable;

$(document).ready(function() {

//	$("#registroFechaNacimiento").datepicker({
//		showOn : 'both',
//		dateFormat : 'dd/mm/yy',
//		changeMonth : true,
//		changeYear : true,
//		yearRange : '-112:+0'
//	});
//	$("#registroFechaNacimiento").datepicker($.datepicker.regional['es']);
//
//	$("#busquedaFechaNacimiento").datepicker({
//		showOn : 'both',
//		dateFormat : 'dd/mm/yy',
//		changeMonth : true,
//		changeYear : true,
//		yearRange : '-112:+0'
//	});
//	$("#busquedaFechaNacimiento").datepicker($.datepicker.regional['es']);
//
//	$("#registroFechaCreacion").datepicker({
//		showOn : 'both',
//		dateFormat : 'dd/mm/yy',
//		changeMonth : true,
//		changeYear : true,
//		yearRange : '-112:+0'
//	});
//	$("#registroFechaCreacion").datepicker($.datepicker.regional['es']);
//
//	$("#busquedaFechaCreacion").datepicker({
//		showOn : 'both',
//		dateFormat : 'dd/mm/yy',
//		changeMonth : true,
//		changeYear : true,
//		yearRange : '-112:+0'
//	});
//	$("#busquedaFechaCreacion").datepicker($.datepicker.regional['es']);
//
//	$('#regresar').click(function() {
//		window.history.back();
//	});
//
//	$('#aceptarAltaPersonaBtn').click(function() {
//		window.close();
//	});

	// Funcion que hace un trim al valor de cualquier campo de texto
	$('input:text').blur(function() {
		$(this).val($.trim($(this).val()));
	});
	
//	// En laspantallas de busqueda de personas, se pone la busqueda exacta por default
//	$('#busquedaExacta').attr('checked', 'checked');

});
