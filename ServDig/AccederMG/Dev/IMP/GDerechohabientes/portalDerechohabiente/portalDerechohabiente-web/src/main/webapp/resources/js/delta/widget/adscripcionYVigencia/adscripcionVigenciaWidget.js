$.getScript("/${mvn.web.app.root}/static/resources/js/delta/wizard/bajaDerechohabiente/BajaDerechohabienteWizard.js");
$.getScript("/${mvn.web.app.root}/static/resources/js/delta/wizard/prorrogaDerechohabiente/ProrrogaDerechohabienteWizard.js");
$.getScript("/gestionIndividuo-consulta-web/static/resources/js/wizard/common/actualizacion-datos/actualizacionDatosPersonaWizard.js");
$.getScript("/${mvn.web.app.root}/static/resources/js/delta/wizard/correccionDerechohabiente/CorreccionDatosDerechohabienteWizard.js");
//$.getScript("/gestionDomicilios-web/static/resources/js/delta/domicilios/wizard/registrar/particular/registrarDomicilioParticularWizard.js");
$.getScript("/gestionDomicilios-web/static/resources/js/delta/domicilios/wizard/general/WizardDomicilioGeneral.js");

var adscripcionVigenciaWidgetCtrl = {
	
	divContenedor : 'listadoCandidatos',
	bajaDefuncion : function() {
		
		var portalContext = $("#portalContext").val();
		
		if(portalContext != 4) {
			var idAsignacion = $("#hdnIdAsignacionNss").val();
			var nss = $("#hdnNss").val();
			var idDerechohabiente = $("#hdnIdPersona").val();
			var tipoBaja = BAJA_DEFUNCION;
			
			WizardBajaDerechohabienteCtrl.init("wizardBajaDerechohabiente", idAsignacion, nss, tipoBaja, idDerechohabiente);
			WizardBajaDerechohabienteCtrl.abrir();
		}
		
	},
	correccionDatosDHabiente : function() {
		var portalContext = $("#portalContext").val();
		
		if(portalContext != 4) {
			var idAsignacion = $("#hdnIdAsignacionNss").val();
			var nss = $("#hdnNss").val();
			var idDerechohabiente = $("#hdnIdPersona").val();
			var curpPersonaDerechohabiente = $("#hdnCurpPersonaDerechohabiente").val();
			var correccionDatosDHabiente = CORRECCION_DATOS_DERECHOHABIENTE;
			
			WizardCorreccionDerechohabienteCtrl.init("wizardCorreccionDerechohabiente", idAsignacion, nss, correccionDatosDHabiente, idDerechohabiente, portalContext, curpPersonaDerechohabiente);
			WizardCorreccionDerechohabienteCtrl.abrir();
		}
		
	},
	bajaDivorcio : function () {
		
		var portalContext = $("#portalContext").val();
		
		if(portalContext != 4) {
			var idAsignacion = $("#hdnIdAsignacionNss").val();
			var nss = $("#hdnNss").val();
			var tipoBaja = BAJA_DIVORCIO;
			var idDerechohabiente = $("#hdnIdPersona").val();
			
			WizardBajaDerechohabienteCtrl.init("wizardBajaDerechohabiente", idAsignacion, nss, tipoBaja, idDerechohabiente);
			WizardBajaDerechohabienteCtrl.abrir();
		}
	}, 
	bajaConcubinato : function() {
		var portalContext = $("#portalContext").val();
		
		if(portalContext != 4) {
			var idAsignacion = $("#hdnIdAsignacionNss").val();
			var nss = $("#hdnNss").val();
			var tipoBaja = BAJA_CONCUBINATO;
			var idDerechohabiente = $("#hdnIdPersona").val();
			
			WizardBajaDerechohabienteCtrl.init("wizardBajaDerechohabiente", idAsignacion, nss, tipoBaja, idDerechohabiente);
			WizardBajaDerechohabienteCtrl.abrir();
		}
	}, 
	bajaDependencia : function() {
		var portalContext = $("#portalContext").val();
		
		if(portalContext != 4) {
			var idAsignacion = $("#hdnIdAsignacionNss").val();
			var nss = $("#hdnNss").val();
			var tipoBaja = BAJA_DEPENDENCIA;
			var idDerechohabiente = $("#hdnIdPersona").val();
			
			WizardBajaDerechohabienteCtrl.init("wizardBajaDerechohabiente", idAsignacion, nss, tipoBaja, idDerechohabiente);
			WizardBajaDerechohabienteCtrl.abrir();
		}
	},
	
	//Seccion encargada de manejar la funcionalidad de prorrogas
	prorrogaController : function(tipoProrroga) {
		var portalContext = $("#portalContext").val();
		
		if(portalContext != 4) {
			var idAsignacion = $("#hdnIdAsignacionNss").val();
			var nss = $("#hdnNss").val();
			var idDerechohabiente = $("#hdnIdPersona").val();
			
			WizardProrrogaDerechohabienteCtrl.init("wizardProrrogaDerechohabiente", idAsignacion, nss, tipoProrroga, idDerechohabiente);
			WizardProrrogaDerechohabienteCtrl.abrir();
		}
	},
	
	cambioDomicilio : function() {
		var portalContext = $("#portalContext").val();
		
		if(portalContext != 4) {
			var idAsignacion = $("#hdnIdAsignacionNss").val();
			var nss = $("#hdnNss").val();
			var idDerechohabiente = $("#hdnIdPersona").val();
			
			var tipoTramite = ACTUALIZACION_DOMICILIO_PARTICULAR;
			console.log( "Tipo de tramite: "  + tipoTramite);
			
			var opciones = {
				idPersona	: idDerechohabiente,
				idPersonaInteresada: AtributosPersonaCtrl.personaFirmada.idPersona
			};
			
			WizardDomicilioGeneralCtrl.init(opciones).abrir();
		}
	}
};

$("#iniciarBajaDefuncion").live('click', function() {
	adscripcionVigenciaWidgetCtrl.bajaDefuncion();
});

$("#iniciarCorreccionDatosDHabiente").live('click', function() {
	adscripcionVigenciaWidgetCtrl.correccionDatosDHabiente();
});

$("#iniciarBajaDivorcio").live('click', function() {
	adscripcionVigenciaWidgetCtrl.bajaDivorcio();
});

$("#iniciarBajaConcubinato").live("click", function() {
	adscripcionVigenciaWidgetCtrl.bajaConcubinato();
});

$("#iniciarBajaDependencia").live('click', function () {
	adscripcionVigenciaWidgetCtrl.bajaDependencia();
});


$("#iniciarProrrogaObstetrica").live('click', function () {
	adscripcionVigenciaWidgetCtrl.prorrogaController(PRORROGA_OBSTETRICOS);
});

$("#iniciarProrrogaFisica").live('click', function () {
	adscripcionVigenciaWidgetCtrl.prorrogaController(PRORROGA_ENFERMEDAD);
});

$("#iniciarProrrogaEstudios").live('click', function () {
	adscripcionVigenciaWidgetCtrl.prorrogaController(PRORROGA_ESTUDIOS);
});

$("#iniciarCambioDomicilio").live('click', function () {
	adscripcionVigenciaWidgetCtrl.cambioDomicilio();
});

$(document).ready(
	function() {
		var idParentesco = $("#hdnIdParentesco").val();
		var idEstado = $("#hdnIdEstadoDerechohabiente").val();
		
		$.post("/portal-web/utility/menu/opciones/adscripcion/"+idParentesco+"/" + idEstado,null,function(data) {
				$("#accionesWidgetAdscripcionVigencia").html(data);
		});
	}
);