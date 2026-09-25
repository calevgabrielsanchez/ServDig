var tipoOperacion;
var cancelable;
var ventanilla;

$(document).ready(function() {
//	funcionalidadVentanilla();
});

function funcionalidadVentanilla() {
	
	if(ventanilla) {
		var url = parent.WizardIVROVentanillaSeguroDomesticoCtrl.config.url;
		var idPersona = null;
		var rfc = null;
		var nssCifrado = null;
		
		if(parent.WizardIVROVentanillaSeguroDomesticoCtrl.config.idPersona !== undefined
				&& parent.WizardIVROVentanillaSeguroDomesticoCtrl.config.idPersona !== null){			
			idPersona = parent.WizardIVROVentanillaSeguroDomesticoCtrl.config.idPersona;
			rfc = parent.WizardIVROVentanillaSeguroDomesticoCtrl.config.rfc;
			nssCifrado = parent.WizardIVROVentanillaSeguroDomesticoCtrl.config.nssCifrado;	
			
		}else if(parent.WizardIVROAltaSeguroDomesticoCtrl.config.idPersona !== undefined
				&& parent.WizardIVROAltaSeguroDomesticoCtrl.config.idPersona !== null){
			idPersona = parent.WizardIVROAltaSeguroDomesticoCtrl.config.idPersona;
			rfc = parent.WizardIVROAltaSeguroDomesticoCtrl.config.rfc;
			nssCifrado = parent.WizardIVROAltaSeguroDomesticoCtrl.config.nssCifrado;
			
		}else if(parent.WizardIVRORenovacionSeguroDomesticoCtrl.config.idPersona !== undefined
				&& parent.WizardIVRORenovacionSeguroDomesticoCtrl.config.idPersona !== null){
			
			idPersona = parent.WizardIVRORenovacionSeguroDomesticoCtrl.config.idPersona;
			rfc = parent.WizardIVRORenovacionSeguroDomesticoCtrl.config.rfc;
			nssCifrado = parent.WizardIVRORenovacionSeguroDomesticoCtrl.config.nssCifrado;
		}
			
		if(idPersona != null){
			$('<form/>', {
				'id': 'formBackToVentanillaMain',
				'action': url +  
					'/' + idPersona + 
					'/' + rfc + 
					'/' + nssCifrado,
				'method': 'get',
				'submit': function() {
					$.blockUI();
				}
			}).insertAfter($('form').last());	
		}
		
	}
}

function closeWizard() {
	
	parent.WizardAltaSeguroFamiliarCtrl.close();
	
	/*
	if(ventanilla) {
		
		if(parent.WizardAltaSeguroFamiliarCtrl !== undefined 
			&& parent.WizardAltaSeguroFamiliarCtrl.config.idPersona !== undefined
			&& parent.WizardAltaSeguroFamiliarCtrl.config.idPersona !== null){
			
			parent.WizardAltaSeguroFamiliarCtrl.close();
			parent.WizardAltaSeguroFamiliarCtrl.dialogo = { };
			
		}else{
			parent.WizardIVROVentanillaSeguroDomesticoCtrl.close();
			parent.WizardIVROVentanillaSeguroDomesticoCtrl.dialogo = { };
						
		}
		
	} else if(tipoOperacion !== undefined && tipoOperacion != '') {
		parent.WizardIVRORenovacionSeguroDomesticoCtrl.close();
		parent.WizardIVRORenovacionSeguroDomesticoCtrl.dialogo = { };
		
	} else {
		parent.WizardIVROAltaSeguroDomesticoCtrl.close();
		parent.WizardIVROAltaSeguroDomesticoCtrl.dialogo = { };
		
	}
	
	if((typeof $("#wizardAltaSeguroVoluntario")) !== "undefined"){
		$("#wizardAltaSeguroVoluntario").dialog('close');
	}else{
		if((typeof parent.$("#wizardAltaSeguroVoluntario")) !== "undefined"){
			parent.$("#wizardAltaSeguroVoluntario").dialog('close');
		}
	}
	*/
}
