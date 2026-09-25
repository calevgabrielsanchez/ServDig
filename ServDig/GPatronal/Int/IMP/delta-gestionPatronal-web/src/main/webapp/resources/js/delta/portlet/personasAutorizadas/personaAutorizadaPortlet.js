/**
 * Script para controlar el portlet de persona autorizada
 */

$.getScript("/delta-gestionPatronal-web/static/resources/js/delta/wizard/personaAutorizada/registro/registroPersonaAutorizadaWizard.js");
$.getScript("/delta-gestionPatronal-web/static/resources/js/delta/wizard/personaAutorizada/baja/bajaPersonaAutorizadaWizard.js");

var personaAutorizadaPortlet = {
		registrar : function() {
			var idPersona = $("#hdnIdPersonaRepresentada").val();
			var rfcPersona = $("#rfcPersonaPatronAut").val();
			var idTipoPersona = $("#tipoPersonaRepresentado").val();
									
			WizardRegistroPersonaAutorizadaCtrl.init("wizardRegistroPersonaAutorizada",
				idPersona,idTipoPersona,rfcPersona);
			WizardRegistroPersonaAutorizadaCtrl.abrir();
		},
		baja: function() {
			var idPersona = $("#hdnIdPersonaRepresentada").val();
			var rfcPersona = $("#rfcPersonaPatronAut").val();
			var idTipoPersona = $("#tipoPersonaRepresentado").val();
						
			WizardBajaPersonaAutorizadaCtrl.init("wizardRegistroPersonaAutorizada",
				idPersona,idTipoPersona,rfcPersona);
			WizardBajaPersonaAutorizadaCtrl.abrir();
		}
}

$("#registrarPersonaAutorizada").live('click', function() {
	personaAutorizadaPortlet.registrar();
});

$("#eliminarPersonaAutorizada").live('click', function() {
	personaAutorizadaPortlet.baja();
});

$(document).ready(function() {
	$.post("/portal-web/utility/menu/opciones/1/8",null,function(data) {
			$("#opcionesPersonasAutorizadas").html(data);
	});
});

$(window).load(function() {
	var listaPA = $('#sizeListaPersonasAut').val();
	if(listaPA <= 0 || listaPA == '0'){
		$('#eliminarPersonaAutorizada').remove();
	}
});