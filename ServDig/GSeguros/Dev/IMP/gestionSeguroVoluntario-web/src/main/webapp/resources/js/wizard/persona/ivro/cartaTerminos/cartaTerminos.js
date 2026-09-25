
$(document).ready(function() {
	
	$('#aceptaCartaTC').hide();
	
	$('#aceptaCartaTC').click(function(e) {
		e.preventDefault();
		//Cerrar dialogo carta terminos y condiciones
		$('#mdmDatosEntradaRenovacion').submit();
//		parent.CartaTerminosCtrl.abrirWizardPrincipal();
	});
	
	$('#chkCartaTC').change(function() {
		if($(this).prop('checked'))
			$('#aceptaCartaTC').show();
		else
			$('#aceptaCartaTC').hide();
	});

	$('#cancelarCartaTC').click(function() {		
		if (!$.isEmptyObject(parent.WizardSeguroIvroIndivCtrl.dialogo)) {
			parent.WizardSeguroIvroIndivCtrl.dialogo.dialog('close');
		} else {
			$('#ivroPersonalFrame').parent().dialog('close');
		}
	});
	
});