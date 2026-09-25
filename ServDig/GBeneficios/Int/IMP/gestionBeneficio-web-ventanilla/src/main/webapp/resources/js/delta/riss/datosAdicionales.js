$(function() {
	$('button#btnValidar').click(function(e) {
		e.preventDefault();
		$('form#validarRissForm').submit();
	});

	$('button#bntCancelar').click(function(e) {
		e.preventDefault();
		$('form#formCancelar').submit();
	});

	if ($('input#rfc').length > 0) {
		$('input#rfc').focus();
	}
});