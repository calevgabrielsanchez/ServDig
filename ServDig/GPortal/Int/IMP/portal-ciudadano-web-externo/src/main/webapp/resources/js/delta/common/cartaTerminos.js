
$(document).ready(function() {
	
	$('#aceptaCartaTC').hide();
	
	$('#aceptaCartaTC').click(function(e) {
		e.preventDefault();
		//Cerrar dialogo carta terminos y condiciones
		parent.CartaTerminosCtrl.abrirWizardPrincipal();
	});
	
	$('#chkCartaTC').change(function() {
		if($(this).prop('checked'))
			$('#aceptaCartaTC').show();
		else
			$('#aceptaCartaTC').hide();
	});

	$('#cancelarCartaTC').click(function() {		
		parent.CartaTerminosCtrl.cancelarCarta();		
	});
	
});