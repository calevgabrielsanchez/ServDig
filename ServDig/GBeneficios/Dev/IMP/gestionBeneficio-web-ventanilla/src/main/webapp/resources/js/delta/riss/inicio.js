$(function() {
	$('input[type=radio].opc-RISS').click(function(e) {
		if ($(this).val() == 'rissRfc') {
			$('div#rissByNrp').show();
			$('div#rissByNss').hide();
			$('input#rfc').focus();

		} else {
			$('div#rissByNrp').hide();
			$('div#rissByNss').show();
			$('input#nss').focus();
		}

		if (e.originalEvent !== undefined) {
			$('span.error').hide();
		}

	});

	$('input[type=radio].opc-RISS').each(function() {
		if ($(this).is(':checked')) {
			$(this).trigger('click');
		}
	});
});