/*
 * JS de control del Widget de Persona Moral
 */

$.getScript("/gestionIndividuo-consulta-web/static/resources/js/wizard/common/actualizacion-datos/actualizacionDatosPersonaWizard.js");

var personaMoralDatosBasicosWidget = {
	editar : function() {
		
		var idPersonaSesion = $('#idPersonaSesionFM').val();
		var cveMoral = $('#cveMoral').val();
		var rfcPersona = $('#rfcPersona').val();
		var curpPersona = 'SIN_CURP';
		
		if(isEmpty(idPersonaSesion))
			idPersonaSesion=0;
		
		// Se valida que se tenga el curp y rfc
		if (rfcPersona == null || rfcPersona == '') {
			rfcPersona = 'SIN_RFC';
		}
		
		WizardActualizacionDatosCtrl.init('wizardDatosActualizacion', idPersonaSesion, 2, cveMoral,
				curpPersona, rfcPersona);
		WizardActualizacionDatosCtrl.abrir();
	}
};

$("#editarPersonaMoral").live('click', function() {
	personaMoralDatosBasicosWidget.editar();
});

function isEmpty(valor) {
	return (valor == undefined || valor == "");
}