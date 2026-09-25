/*
 * JS de control del Widget de Persona Fisica.
 */

var isReadOnly = $('div#idPersonaIdentidadWidget').attr('is-readOnly'); 

if (isReadOnly != 'true') {
	$.getScript("/gestionIndividuo-consulta-web/static/resources/js/wizard/fisica/modificacion/medios/particulares/modificacionMediosParticularesWizard.js");
	$.getScript("/gestionIndividuo-consulta-web/static/resources/js/wizard/common/actualizacion-datos/actualizacionDatosPersonaWizard.js");
	$.getScript("/gestionDomicilios-web/static/resources/js/delta/domicilios/wizard/general/WizardDomicilioGeneral.js");
}

var mediosContactoParticularesWidget = {
	editar : function() {
		
		var idPersona = AtributosPersonaCtrl.personaPortal.idPersona;
		
		WizardModificacionMediosParticularesCtrl.init('wizardDatosActualizacion', 1, idPersona);
		WizardModificacionMediosParticularesCtrl.abrir();
	}
};

var domicilioParticularWidget = {
	editar : function() {
		WizardDomicilioGeneralCtrl.init({idPersona	: AtributosPersonaCtrl.personaPortal.idPersona}).abrir();
	}

};

var personaMoralDatosBasicosWidget = {
	editar : function() {
		
		var idPersonaSesion = AtributosPersonaCtrl.personaFirmada.idPersona;
		var cveMoral = AtributosPersonaCtrl.personaPortal.idFiscalPersona;
		var rfcPersona = AtributosPersonaCtrl.personaPortal.rfc;
		var curpPersona = 'SIN_CURP';
				
		if(isEmpty(idPersonaSesion))
			idPersonaSesion=0;
		
		// Se valida que se tenga el curp y rfc
		if (rfcPersona == null || rfcPersona == '') {
			rfcPersona = 'SIN_RFC';
		}
		
		var params = {
			idPersonaSesion : idPersonaSesion,
			idTipoPersona : 2,
			idPersona : cveMoral,
			curp : curpPersona,
			rfc : rfcPersona,
			consultaRenapo : false,
			consultaSat : true
		};
		
		WizardActualizacionDatosCtrl.init('wizardDatosActualizacion', params);
		WizardActualizacionDatosCtrl.abrir();
	}
};

var personaFisicaDatosBasicosWidget = {
	editar : function() {
				
		var idPersonaSesion = AtributosPersonaCtrl.personaFirmada.idPersona;
		var idPersona = AtributosPersonaCtrl.personaPortal.idPersona;
		var rfcPersona = AtributosPersonaCtrl.personaPortal.rfc;
		var curpPersona = AtributosPersonaCtrl.personaPortal.curp;
		
		if(isEmpty(idPersonaSesion))
			idPersonaSesion=0;
		
		// Se valida que se tenga el curp y rfc
		if (rfcPersona == null || rfcPersona == '') {
			rfcPersona = 'SIN_RFC';
		}
		
		if (curpPersona == null || curpPersona == '') {
			curpPersona = 'SIN_CURP';
		}
		
		var params = {
			idPersonaSesion : idPersonaSesion,
			idTipoPersona : 1,
			idPersona : idPersona,
			curp : curpPersona,
			rfc : rfcPersona,
			consultaRenapo : true,
			consultaSat : true
		};
		
		WizardActualizacionDatosCtrl.init('wizardDatosActualizacion', params);
		WizardActualizacionDatosCtrl.abrir();
	}
};

var reporteHLDAWidget = {
	mostrar : function() {
		var nssCifrado = AtributosPersonaCtrl.personaFirmada.nssCifrado;

		var urlFrame = '/gestionAsegurados-web-externo/hlda/reporte?nss='
				+ nssCifrado;

		var div = $('#reporteFrame');

		div.dialog({
			title : 'Reporte de Semanas Cotizadas',
			closeOnEscape : false,
			autoOpen : false,
			width : 900,
			height : 900,
			modal : true,
			resizable : false,
			overlay : {
				opacity : 0.5,
				background : "black"
			},
			close : function(event, ui) {
				// Se destruye el dialogo
				$(this).dialog('destroy').empty();
			}
		});

		div.dialog('open');

		div.html('<iframe id="reporteHldaFrame" src="' + urlFrame
				+ '" width="100%" height="100%" frameborder="0"/>');
	}
};

$("#editarPersonaFisica").live('click', function() {
	personaFisicaDatosBasicosWidget.editar();
});

$("#editarPersonaMoral").live('click', function() {
	personaMoralDatosBasicosWidget.editar();
});

$("#editarDomicilio").live('click', function() {
	domicilioParticularWidget.editar();
});

$("#editarMedios").live('click', function() {
	mediosContactoParticularesWidget.editar();
});

$("#reporteHLDA").live('click', function(event) {
	event.preventDefault();
	reporteHLDAWidget.mostrar();
});

function isEmpty(valor) {
	return (valor == undefined || valor == "");
}
