$.getScript("/riesgosTrabajo-web/static/resources/js/wizard/rtt/wizardRttCtrl.js");
$.getScript("/escritoDesacuerdo-web/static/resources/js/wizard/desacuerdo/wizardEscritoCtrl.js");

var riesgoTrabajoWidget = {
		editar : function() {
			var _this=parent;
		    var datos = {contenedor: 'wizardRiesgoTrabajoDiv',
            		rp: _this.AtributosPersonaCtrl.personaPortal.registroPatronal,
					razonSocial: _this.AtributosPersonaCtrl.personaPortal.nombreRazonSocial,
				    id: _this.AtributosPersonaCtrl.personaPortal.idPersona};
		    WizardRttCtrl.init(datos);
			WizardRttCtrl.abrir();  
			
		},
		editarRfc : function() {
			var _this=parent;
		    var datos = {contenedor: 'wizardRiesgoTrabajoDiv',
            		rfc: _this.AtributosPersonaCtrl.personaPortal.rfc,
					razonSocial: _this.AtributosPersonaCtrl.personaPortal.nombreRazonSocial,
				    id: _this.AtributosPersonaCtrl.personaPortal.idPersona};
		    WizardRttCtrl.init(datos);
			WizardRttCtrl.abrir();  
			
		},
		escrito : function() {
			var _this=parent;
			 var datos = {contenedor: 'wizardRiesgoTrabajoDiv',
		        		rp: _this.AtributosPersonaCtrl.personaPortal.registroPatronal};
			    WizardEscritoCtrl.init(datos);
				WizardEscritoCtrl.abrir();  	
		}
	};

$("#entrarRiesgoTrabajo").live('click', function() {
	riesgoTrabajoWidget.editar();
});

$("#entrarRiesgoTrabajoRfc").live('click', function() {
	riesgoTrabajoWidget.editarRfc();
});

$("#registrarEscritoDesacuerdo").live('click', function() {
	riesgoTrabajoWidget.escrito();
});
