/*
 * JS de control del Widget de Domicilios Particulares.
 */

$.getScript("/gestionDomicilios-web/static/resources/js/delta/domicilios/wizard/modificacion/particular/modificacionDomicilioParticularWizard.js");
$.getScript("/gestionDomicilios-web/static/resources/js/delta/domicilios/wizard/registrar/particular/registrarDomicilioParticularWizard.js");

var domicilioParticularWidget = {
		editar : function() {
			
			var idPersona = $('#idPersonaWidgetCtrl').val();
			var idDomicilioParticular = $('#idDomiclioPartWidget').val();
			
			/* Se valida que la persona ya tenga un domicilio,
			 * si ya lo tiene se llama al wizard de modificación
			 * en caso contrario se llama al wizard de creación
			 */
			if($('#idDomiclioPartWidget').length > 0) {
				WizardModificacionDomicilioParticularCtrl.init('wizardDatosActualizacion', 1, idPersona, idDomicilioParticular);
				WizardModificacionDomicilioParticularCtrl.abrir();
			} else {
				//Cuando se lanza el wizard desde la validación del domicilio del asegurado, se debe enviar otro parametro
				//WizardRegistrarDomicilioParticularCtrl.init que indica que se trata de un origen distinto
				WizardRegistrarDomicilioParticularCtrl.init('wizardDatosActualizacion', 1, idPersona);
				WizardRegistrarDomicilioParticularCtrl.abrir();
			}
			
			
		}
	};

	$("#editarDomParticular").live('click', function() {
		domicilioParticularWidget.editar();
	});