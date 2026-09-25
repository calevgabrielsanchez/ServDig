$(document).ready(function(){
	/*
	 * Colocamos el atributo disabled a los campos
	 * que no pueden ser modificados.
	 */
	$('input').each(function() {
		$(this).fadeTo('slow', 0.5);
		$(this).attr('disabled', 'disabled');
		$(this).attr('readonly', 'readony');
	});
	
	$('select').each(function() {
		$(this).fadeTo('slow', 0.5);
		$(this).attr('disabled', 'disabled');
		$(this).attr('readonly', 'readony');
	});	
});