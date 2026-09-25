var urlWizardRegRep				= context_path + '/alta/';
var formBusquedaRFC;
var estructuraFirmaEmpresa;
var estructuraFirmaRepresentante;

$(document).ready(function() {
	
	formBusquedaRFC = $("#busquedaRFCForm");
	// Se incializa el blockUI para las peticiones AJAX
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
	
	initValidatorBusquedaRFC();
	
	$('#buscarPersonaRepLegal').click(function() {
		if(formBusquedaRFC.valid()){
			buscaRFC();
		}
	});
	
	$("#rfcAltaRep").on("keypress", function() {
		$("#datosEmpresa").hide();
		$("#botonesDiv").hide();
	});
	
	$.postJSON(urlWizardRegRep+ 'recuperaRfcSesion.do', null, function(data) {
		if(data != null && data != ""){
			$("#rfcAltaRep").val(data);
			  $('#buscarPersonaRepLegal').click();

		}
	})
});

function initValidatorBusquedaRFC() {
	 formBusquedaRFC.validate($.extend({},DEFAULTS_VALIDATE,{
		verifyErrors: function(existError) {
			mostrarMensajeErrorRepLegal(existError, "<strong>Error en el formulario!</strong> no ha llenado todos los campos requeridos. Por favor verifique");
			marcarAsteriscos(formBusquedaRFC,".errorDocs",".col-sm-4");
		},
		rules: {
			rfcAltaRep: {
				required: true,
				minlength: 12,
				maxlength: 12,
				rfcMoral: true
			}
		}
	}));
}


function buscaRFC() {
	var oForm = $("form#busquedaRFCForm").toObject();
	var url = urlWizardRegRep + 'buscaRFC';
	var representado = new Object();
	
	representado.rfc = oForm.rfcAltaRep;
	representado.tipoPoder = new Object();
	representado.tipoPoder.idTipoPoder = $('input[name=idTipoPoder]:checked', '#busquedaRFCForm').val();
	
	fnHideErrores("form#busquedaRFCForm");
	
	$.postJSON(url, representado, function(data2) {
		if(data2.negocio.errorFormGeneral == null){
			$("#razonSocialId").val(data2.negocio.razonSocial);
			
			if(data2.negocio.tipoSociedad != null && data2.negocio.tipoSociedad.descripcionAbreviada != null) {
				$("#tipoSociedadId").val(data2.negocio.tipoSociedad.descripcionAbreviada);
			}				
			//se setea la persona por si no trae id 
			personaMoralAP = data2.negocio;
			solicitudPrincipal.tramiteRepresentanteLegal.sujetoObligado.moral = data2.negocio;
			estructuraFirmaEmpresa = data2.estructuraFirmaEmpresa;
			estructuraFirmaRepresentante = data2.estructuraFirmaRepresentante;
			solicitudPrincipal.busquedaPersona = data2.oForm;
			//validamos las reglas para ver si es posible realizar el alta patronal
			validacionesICA(data2.negocio.idPersona, false);
		} else if(data2.negocio.errorFormGeneral == "existeRelacion"){
			//validamos si es posible realizar el alta patronal
			validacionesICA(data2.negocio.idPersona, true);
		} else{
			mostrarMensajeErrorRepLegal(true,data2.negocio.errorFormGeneral);
		}
	}).error(function(data){
		fnProcesarErrores(data, "form#busquedaRFCForm");
	});
}

/**
 * Metodo para validar si es posible realizar el alta patronal
 */
var validacionesICA = function(idPersonaMoral, saltarPasos) {
	var url=context_path+ "/alta/validarPersonaMoral";
	if(idPersonaMoral != null) {
		$.postJSON(url, {}, function(data){
			//se obtiene el codigo de respuesta
			var codigoRespuestaVal = data.codigoRespuesta;
			//si el codigo de respuesta es 0  quiere decir que no es posible realizar el tramite
			if(codigoRespuestaVal == 0) {
				//quitamos la persona moral capturada al principio ya que no es posible realizar el tramite
				solicitudPrincipal.tramiteRepresentanteLegal.sujetoObligado.moral = null;
				//mostramos el mensaje de error
				mostrarMensajeErrorRepLegal(true,data.mensaje);
			} else {
				//verificamos si es necesario pedir escritura, sindicato o socios
				/*
				if(codigoRespuestaVal == 2) {
					requiereSocios = false;
					requiereActaSindicato = true;
				} else if(codigoRespuestaVal == 3) {
					requiereSocios = true;
					requiereActaSindicato = false;
				}*/
				//asociamos la persona moral mas completa
				personaMoralAP = data.personaMoral;
				//seteamos los datos ica
				solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.datosICA = data.datosICA;
				mostrarInfoPersona(saltarPasos);
			}
			
		});
	} else {
		//si el id de la persona es nula quiere decir que no esta registrada y solo mostramos la informacion
		mostrarInfoPersona(false);
	}
	
}

var mostrarInfoPersona = function(saltarPasos) {
	$("#datosEmpresa").show();
	$("#botonesDiv").show();
	//seteamos los datos del patron
	setDatosPatron();
	//verificamos si nos tenemos que saltar los pasos de firma
	if(saltarPasos) {
		//simulamos que ya estamos en la pagina de las firmas
		paginaActual = 1;
		//y avanzamos a la confirmacion
		paginaSiguiente();
	}
}

var mostrarMensajeErrorRepLegal = function(mostrar,mensaje) {
	if(mostrar) {
		$("#errorFormBusqueda").html(mensaje).show();
		$("#botonesDiv").hide();
	} else {
		$("#errorFormBusqueda").html("").hide();
	}
}


function firmar(){
	inicializaFirmaEmpresa();
	paginaSiguiente();
}