$.getScript('/${mvn.web.app.root}/static/resources/js/wizard/seguroDomestico/comunes/common.js');

$(document).ready(function() {
	$('#imprimirComprobante').click(function() {
		$('#imprimirComprobanteForm').submit();
		$.unblockUI();
	});
//	$('#imprimirCuestionario').click(function() {
//		$('#imprimirCuestionarioForm').submit();
//		$.unblockUI();
//	});
	$('#cerrarWizard').click(function() {
		if(ventanilla)
			$('#formBackToVentanillaMain').submit();
		else
			closeWizard();
	});
});	
