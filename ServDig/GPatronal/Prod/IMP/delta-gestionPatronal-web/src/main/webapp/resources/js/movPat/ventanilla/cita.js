$(document).ready(function() {
	// Se incializa el blockUI para las peticiones AJAX
    $(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
	// Ocultamos divs
	$("#divContenidoCita").hide();
    $("#divCita").hide();
    $("#divRp").hide();
    //inicializamos las validaciones
    initValidacionesFormContacto();
    //seteamos el evento de los botones
    $("#btnAvisoModificacionSRT").on("click", avisoModificacion);  
    $("#btnConCita").on("click", showFolio);
    $("#btnSinCita").on("click", showRegistroPatronal);
    $("#btnBuscarFolio").on("click", buscarDetalleDeFolio);
    $("#btnCancelarFolio").on("click", hideFolio);
    $("#btnCancelarRp").on("click", hideRegistroPatronal)
    
});

/**
 * Metodo para inicializar el validador de medios de contacto
 */
var initValidacionesFormContacto = function() {
    $("#formBuscarFolio").validate($.extend({},DEFAULTS_VALIDATE,{
        verifyErrors: function(existError) {
            marcarAsteriscos($("#formBuscarFolio"),".errorDocs","div");
        },
        rules: {
            folioCita: {
				required: true, 
				number:true, 
				maxlength: 25},
        },
        messages: {
	        folioCita: {
				required: "Este campo es obligatorio.",
				maxlength: "El formato del campo no es v&aacute;lido.",
				number: "El formato del campo no es v&aacute;lido."
			},	        
    	}
    }));
}

var avisoModificacion = function() {
	$("#divHome").hide();
    $("#divContenidoCita").show();
}


var showFolio = function() {
    $("#divCita").show();
    $("#divRp").hide();
    window.scrollTo(0, 250);
}

var hideFolio = function() {
	$("#divHome").show();
    $("#divCita").hide();
    $("#divContenidoCita").hide();
}

var showRegistroPatronal = function() {
	//$("#divContenidoCita").hide();
	$("#divCita").hide();
    $("#divRp").show();
    window.scrollTo(0, 250);
}

var hideRegistroPatronal = function() {
	$("#divRp").hide();	
	$("#divHome").show();
    $("#divContenidoCita").hide();
}


var buscarFolio = function() {
    var formBuscarFolio = $("#formBuscarFolio");
    var valido = formBuscarFolio.valid();
    var folio =  $("#formBuscarFolio #folioCita").val();
    console.log('Validacion', valido);
    if (valido) {
        $.blockUI();
        console.log('Folio', folio);
        var urlAction = context_path + '/movPat/ventanilla/tramite/folio';

        document.getElementById('formBuscarFolio').action = urlAction;
        document.getElementById('formBuscarFolio').submit();
    }
}


var buscarDetalleDeFolio = function() {
	 var formBuscarFolio = $("#formBuscarFolio");
    var valido = formBuscarFolio.valid();
    var folio =  $("#formBuscarFolio #folioCita").val();
	
	if(valido) {
		 $.blockUI();
        console.log('Folio', folio);
        
		sendToServer('/movPat/ventanilla/tramite/folio', 
				folio, callbackValidaFolioDetalle, false);
		
	}
}

function callbackValidaFolioDetalle(response) {
	$.unblockUI();
	if (response.mensajeError != undefined && response.mensajeError != null) {
		titulo = "Operaci&oacute;n Err&oacute;nea";
		error = true;
		var height=200;
		var width=400;
		if (response.mensajeError == "") {
			mensaje = "Ocurrio un error con el servidor.";
		} else {
			mensaje = response.mensajeError;
		}
		
		if(response.mensajeError.length > 100){
			height=200;
			width=550;
		}
		
		var oDialogoGenerico;
		construirDialogoGenerico("#dialogoMensajes", oDialogoGenerico, titulo,
				mensaje, error, undefined, undefined, height, width);
	} else {
		openPatronCalificacion.open(response.registroPatronal, response.idSolicitud, response.idTramite);
		$("#formBuscarFolio #folioCita").val("")
	}
}

var openPatronCalificacion = {
	open : function(_registroPatronal, _idSolicitud, _idTramite) {
		var tipoTramite = 11;
		console.log("Registro Patronal", _registroPatronal);

		WizardModificacionPatronClasificacionCtrl.init('wizardModificacionClasificacionVentanilla', _registroPatronal,
				tipoTramite, "Modificaciones en el Seguro de Riesgo de Trabajo", context);
		WizardModificacionPatronClasificacionCtrl.abrirRetomarTramiteFolio(_idSolicitud, _idTramite);
	},
	solicitarRIF: function () {
		var rfc 		= AtributosPersonaCtrl.personaPortal.rfc;
		var idPersona 	= AtributosPersonaCtrl.personaPortal.idPersona;
		
		WizardSolicitarRifCtrl.init('wizardModificacionClasificacionVentanilla', rfc, idPersona);
		WizardSolicitarRifCtrl.abrir();
	}
};
