$(document).ready(function(){
	$("#fechaNacimiento").datepicker({
		showOn : 'both',
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : '-112:+0'
	});
	$("#fechaNacimiento").datepicker($.datepicker.regional['es']);
	
	/*
	 * Colocamos el atributo disabled a los campos
	 * que no pueden ser modificados.
	 */
	$('input.disabled').each(function() {
		$(this).fadeTo('slow', 0.5);
		$(this).attr('readonly', 'readony');
		$(this).attr('disabled', 'disabled');
	});
});