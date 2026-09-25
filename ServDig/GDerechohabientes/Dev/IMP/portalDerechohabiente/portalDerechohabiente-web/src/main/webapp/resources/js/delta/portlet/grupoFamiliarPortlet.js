
$.getScript("/${mvn.web.app.root}/static/resources/js/delta/wizard/detalleDerechohabiente/DetalleDerechohabienteWizard.js");
$.getScript("/${mvn.web.app.root}/static/resources/js/delta/wizard/listadoCandidatos/ListadoCandidatosWizard.js");
$.getScript("/${mvn.web.app.root}/static/resources/js/delta/wizard/bajaDerechohabiente/BajaDerechohabienteWizard.js");
$.getScript("/${mvn.web.app.root}/static/resources/js/delta/wizard/prorrogaDerechohabiente/ProrrogaDerechohabienteWizard.js");
$.getScript("/${mvn.web.app.root}/static/resources/js/delta/wizard/registroDerechohabiente/RegistroDerechohabienteWizard.js");
$.getScript("/${mvn.web.app.root}/static/resources/js/delta/wizard/correccionDerechohabiente/CorreccionDatosDerechohabienteWizard.js");
$.getScript("/gestionIndividuo-consulta-web/static/resources/js/wizard/common/actualizacion-datos/actualizacionDatosPersonaWizard.js");
$.getScript("/gestionDomicilios-web/static/resources/js/delta/domicilios/wizard/general/WizardDomicilioGeneral.js");

var grupoFamiliarPortletCtrl = {
	
	divContenedor : 'listadoCandidatos',
	divContenedorRegistro : 'wizardRegistroDerechohabiente',
	registroDerechohabiente : function() {
		var idAsignacion = $("#hdnIdAsignacionNss").val();
		var nss = $("#hdnNss").val();
		
		WizardRegistroDerechohabienteCtrl.setOnCloseCallback(function() {
			if(WizardRegistroDerechohabienteCtrl.datosSalida.registroCorrecto) {
				
				if(WizardRegistroDerechohabienteCtrl.datosSalida.mostrarDocumentos) {
					ejecutarConsultaSolicitudPorFolio(WizardRegistroDerechohabienteCtrl.datosSalida.folioSolicitud);
				}
			}
		});
		
		WizardRegistroDerechohabienteCtrl.init(this.divContenedorRegistro, idAsignacion, nss);
		WizardRegistroDerechohabienteCtrl.abrir();
	},
	bajaDefuncion : function() {
		var idAsignacion = $("#hdnIdAsignacionNss").val();
		var nss = $("#hdnNss").val();
		var tipoBaja = BAJA_DEFUNCION;
		
		WizardListadoCandidatosCtrl.init(this.divContenedor,idAsignacion,nss,tipoBaja);
		WizardListadoCandidatosCtrl.setOnCloseCallback(iniciarRegistroDeBaja);
		WizardListadoCandidatosCtrl.abrir();
		
	},
	correccionDatosDHabiente : function() {
		var idAsignacion = $("#hdnIdAsignacionNss").val();
		var nss = $("#hdnNss").val();
		var correccionDatosDHabiente = CORRECCION_DATOS_DERECHOHABIENTE;
		
		WizardListadoCandidatosCtrl.init(this.divContenedor,idAsignacion,nss,correccionDatosDHabiente);
		WizardListadoCandidatosCtrl.setOnCloseCallback(iniciarCorreccionDatosDHabiente);
		WizardListadoCandidatosCtrl.abrir();
		
	},
	bajaDivorcio : function () {
		var idAsignacion = $("#hdnIdAsignacionNss").val();
		var nss = $("#hdnNss").val();
		var tipoBaja = BAJA_DIVORCIO;
		
		WizardListadoCandidatosCtrl.init(this.divContenedor,idAsignacion,nss,tipoBaja);
		WizardListadoCandidatosCtrl.setOnCloseCallback(iniciarRegistroDeBaja);
		WizardListadoCandidatosCtrl.abrir();
	}, 
	bajaConcubinato : function() {
		var idAsignacion = $("#hdnIdAsignacionNss").val();
		var nss = $("#hdnNss").val();
		var tipoBaja = BAJA_CONCUBINATO;
		
		WizardListadoCandidatosCtrl.init(this.divContenedor,idAsignacion,nss,tipoBaja);
		WizardListadoCandidatosCtrl.setOnCloseCallback(iniciarRegistroDeBaja);
		WizardListadoCandidatosCtrl.abrir();
	},
	bajaPersonaEnUnionCivil : function() {
		var idAsignacion = $("#hdnIdAsignacionNss").val();
		var nss = $("#hdnNss").val();
		var tipoBaja = BAJA_DE_DERECHOHABIENTE_POR_TERMINO_DE_UNION_CIVIL;
		
		WizardListadoCandidatosCtrl.init(this.divContenedor,idAsignacion,nss,tipoBaja);
		WizardListadoCandidatosCtrl.setOnCloseCallback(iniciarRegistroDeBaja);
		WizardListadoCandidatosCtrl.abrir();
	},
	bajaDependencia : function() {
		var idAsignacion = $("#hdnIdAsignacionNss").val();
		var nss = $("#hdnNss").val();
		var tipoBaja = BAJA_DEPENDENCIA;
		
		WizardListadoCandidatosCtrl.init(this.divContenedor,idAsignacion,nss,tipoBaja);
		WizardListadoCandidatosCtrl.setOnCloseCallback(iniciarRegistroDeBaja);
		WizardListadoCandidatosCtrl.abrir();
	},
	
	//Seccion encargada de manejar la funcionalidad de prorrogas
	prorrogaController : function(_tipoProrroga) {
		var idAsignacion = $("#hdnIdAsignacionNss").val();
		var nss = $("#hdnNss").val();
		var tipoProrrga = _tipoProrroga;
		
		WizardListadoCandidatosCtrl.init(this.divContenedor,idAsignacion,nss,tipoProrrga);
		WizardListadoCandidatosCtrl.setOnCloseCallback(iniciarRegistroDeProrroga);
		WizardListadoCandidatosCtrl.abrir();
	},
	
	cambioDomicilio : function() {
		var idAsignacion = $("#hdnIdAsignacionNss").val();
		var nss = $("#hdnNss").val();
		var tipoTramite = ACTUALIZACION_DOMICILIO_PARTICULAR;
		console.log( "Tipo de tramite: "  + tipoTramite);
		WizardListadoCandidatosCtrl.init(this.divContenedor,idAsignacion,nss,tipoTramite);
		WizardListadoCandidatosCtrl.setOnCloseCallback(iniciarCambioDomicilio);
		WizardListadoCandidatosCtrl.abrir();
	}, 
	
	cambioClinica : function() {
		var idAsignacion = $("#hdnIdAsignacionNss").val();
		var nss = $("#hdnNss").val();
		var tipoTramite = CAMBIO_UMF;
		WizardListadoCandidatosCtrl.init(this.divContenedor,idAsignacion,nss,tipoTramite);
		WizardListadoCandidatosCtrl.setOnCloseCallback(iniciarCambioClinica);
		WizardListadoCandidatosCtrl.abrir();
	}
};

var iniciarRegistroDeBaja = function() {
	var integrante =  this;
	
	if(!jQuery.isEmptyObject(integrante)) {
		
		WizardBajaDerechohabienteCtrl.init("wizardBajaDerechohabiente", integrante.idAsignacionNss, integrante.nss, integrante.idTipoTramite, integrante.idIntegranteSeleccionado);
		WizardBajaDerechohabienteCtrl.abrir();
		
	}
};

var iniciarCorreccionDatosDHabiente = function() {
	var integrante =  this;
	
	if(!jQuery.isEmptyObject(integrante)) {
		WizardCorreccionDerechohabienteCtrl.init("wizardCorreccionDerechohabiente", integrante.idAsignacionNss, integrante.nss, integrante.idTipoTramite, integrante.idIntegranteSeleccionado,4, integrante.curpFromListOfCandidates);
		WizardCorreccionDerechohabienteCtrl.abrir();
		
	}
};

var iniciarRegistroDeProrroga = function() {
	var integrante =  this;
	
	if(!jQuery.isEmptyObject(integrante)) {
		
		WizardProrrogaDerechohabienteCtrl.init("wizardProrrogaDerechohabiente", integrante.idAsignacionNss, integrante.nss, integrante.idTipoTramite, integrante.idIntegranteSeleccionado);
		WizardProrrogaDerechohabienteCtrl.abrir();
		
	}
};

var iniciarCambioDomicilio = function() {
	var integrante =  this;
	
	if(!jQuery.isEmptyObject(integrante)) {
		//console.log("Entrando al wizard de domicilio con ID: " + integrante.idTipoTramite);
		var opciones = {
			idPersona	: integrante.idIntegranteSeleccionado,
			idPersonaInteresada: AtributosPersonaCtrl.personaFirmada.idPersona
		};
		
		WizardDomicilioGeneralCtrl.init(opciones).abrir();
	}
};

var iniciarCambioClinica = function() {
	var integrante =  this;
	
	if(!jQuery.isEmptyObject(integrante)) {
		console.log("Entrando al wizard de domicilio con ID: " + integrante.idTipoTramite);
		WizardDomicilioGeneralCtrl.init("wizardDatosActualizacion", integrante.idAsignacionNss, integrante.nss, integrante.idTipoTramite, integrante.idIntegranteSeleccionado);
		WizardDomicilioGeneralCtrl.abrir();
	}
};

/**
 * Se pone el evento para el boton de registro de derechohabiente
 */
$("#iniciarRegistroBeneficiario").live('click', function() {
	grupoFamiliarPortletCtrl.registroDerechohabiente();
});

$("#iniciarBajaDefuncion").live('click', function() {
	grupoFamiliarPortletCtrl.bajaDefuncion();
});

$("#iniciarCorreccionDatosDHabiente").live('click', function() {
	grupoFamiliarPortletCtrl.correccionDatosDHabiente();
});

$("#iniciarBajaDivorcio").live('click', function() {
	grupoFamiliarPortletCtrl.bajaDivorcio();
});

$("#iniciarBajaConcubinato").live("click", function() {
	grupoFamiliarPortletCtrl.bajaConcubinato();
});

$("#iniciarBajaUnionCivil").live("click", function() {
	grupoFamiliarPortletCtrl.bajaPersonaEnUnionCivil();
});

$("#iniciarBajaDependencia").live('click', function () {
	grupoFamiliarPortletCtrl.bajaDependencia();
});

$("#iniciarProrrogaPermanente").live('click', function () {
	grupoFamiliarPortletCtrl.prorrogaController(PRORROGA_PERMANENTE);
});

$("#iniciarProrrogaAcuerdo").live('click', function () {
	grupoFamiliarPortletCtrl.prorrogaController(PRORROGA_ACUERDOS);
});

$("#iniciarProrrogaObstetrica").live('click', function () {
	grupoFamiliarPortletCtrl.prorrogaController(PRORROGA_OBSTETRICOS);
});

$("#iniciarProrrogaFisica").live('click', function () {
	grupoFamiliarPortletCtrl.prorrogaController(PRORROGA_ENFERMEDAD);
});

$("#iniciarProrrogaEstudios").live('click', function () {
	grupoFamiliarPortletCtrl.prorrogaController(PRORROGA_ESTUDIOS);
});

$("#iniciarProrrogaLaudo").live('click', function () {
	grupoFamiliarPortletCtrl.prorrogaController(PRORROGA_LAUDO);
});

$("#iniciarProrrogaTemporal").live('click', function () {
	grupoFamiliarPortletCtrl.prorrogaController(PRORROGA_TEMPORAL);
});

$("#iniciarProrrogaTemporal").live('click', function () {
	grupoFamiliarPortletCtrl.prorrogaController(PRORROGA_TEMPORAL);
});

$("#iniciarCambioDomicilio").live('click', function () {
	grupoFamiliarPortletCtrl.cambioDomicilio();
});

$("#iniciarCambioClinica").live('click', function () {
	grupoFamiliarPortletCtrl.cambioClinica();
});

$(document).ready( function() {
		
		var mostrarOpciones = $("#mostrarOpciones").val() == 1;
		if(mostrarOpciones) {
			var idParentAse = $("#hdnIdParentesco").val();
			var idEstadoAs = $("#hdnIdEstadoDerechohabiente").val();
			$.post("/portal-web/utility/menu/opciones/portlet/grupo/"+idParentAse+"/"+idEstadoAs,null,function(data) {
				$("#opcionesPortletGrupoFamiliar").html(data);
			});
		}
	}
);
