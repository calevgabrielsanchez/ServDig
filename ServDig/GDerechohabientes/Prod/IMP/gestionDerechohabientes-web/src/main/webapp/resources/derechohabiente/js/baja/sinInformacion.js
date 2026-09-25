/**
 * 
 */

$(document).ready(function() {
	$("#aceptar").click(function() {
		esperePorF();
		location.href = "" + context_path + "/inicio/grupoFamiliar";
	});
	
	function esperePorF() {
		$decision = $('<div></div');

		$decision.dialog({
			autoOpen : false,
			resizable : false,
			height : 140,
			title : '',
			modal : true
		}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

		$decision.text('Espere un momento por favor');
		$decision.dialog('open');
	}

});