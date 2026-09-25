var sIdDialogNuevoSocioFiscal = "#idDialogNuevoSocioFiscal";
var sIdDialogNuevoSocioMoral = "#idDialogNuevoSocioMoral";
var sIdDialogNuevoSocioFideicomiso = "#idDialogNuevoSocioFideicomiso";

var dtContctosNS;
var oDialogFisicaNacional;
var oDialogMoralNacional;
var oDialogNuevoSocioFideicomiso;
var checkSocioExtranjero;
var valEsNacional;
var valEsDomiclioNacional;
var divGralAgregarSocioDialog;
var mcSocioFisico;
var mcSocioMoral;
var mcSocioFideicomiso;

MedioContacto.prototype.extendValidation = function () {
    var mc = this;
    var _selectTipoFn = function() { return mc.jqSelectTipo; };
    var fnValidarDatos = mc.validarDatos;
    var alterFnValidarDatos = function(arg1, arg2) {
        this.validarDatos = fnValidarDatos;
        var _option = $([_selectTipoFn(), ' > ', 'option:selected'].join(''));
        if (/correo e|facebook|twitter/i.test(_option.text())) {
            var _tmptxt = $(mc.jqTxtFldDesc).val().replace(/^\s+|\s+$/g, '');
            $(mc.jqTxtFldDesc).val(_tmptxt);
            arg2 = _tmptxt
        }
        var retval = this.validarDatos(arg1, arg2);
        this.validarDatos = alterFnValidarDatos;
        return retval;
    };

    $.extend(mc, {validarDatos: alterFnValidarDatos});
}
	
$(document).ready(function() {					
	inicializaValoresNuevoSocio();
	//configurarBusquedaPersonaFisicaNS();
	configurarBusquedaPersonaMoralNS();
	configurarDatePickerExpedicionContratoSocioFideicomiso();
	inializaValoresNuevoSocioMoral();
	inializaValoresNuevoSocioFideicomiso();
	$("#divGralAgregarSocio").css("display", "none");
	
	divGralAgregarSocioDialog = $("#divGralAgregarSocio").dialog({
		autoOpen: false,
		modal:true,
		resizable:false
	});
	
	oDialogFisicaNacional = $(sIdDialogNuevoSocioFiscal).dialog ({
		autoOpen: false,
		modal:true,
		title: "Agregar Socio Fisico",
		resizable:false,
		width: 1030,
		closeOnEscape: false,
		open:function(event, ui){
			
		},
		close:function(event,ui){
			inicializaValoresNuevoSocio();
		},
		buttons: {
			
		}
	});
	
	oDialogMoralNacional = $(sIdDialogNuevoSocioMoral).dialog ({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 1030,
		title: "Agregar Socio Moral",
		closeOnEscape: false,
		open:function(event, ui){
			
		},
		close:function(event,ui){
			inializaValoresNuevoSocioMoral();
		},
		buttons: {
			
		}
	});
	
	oDialogNuevoSocioFideicomiso = $(sIdDialogNuevoSocioFideicomiso).dialog ({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 1030,
		closeOnEscape: false,
		title: "Agregar Socio Fideicomiso",
		open:function(event, ui){
			
		},
		close:function(event,ui){
			inializaValoresNuevoSocioFideicomiso();
		},
		buttons: {
			
		}
	});
	
	
	/**
		Apartado para datos o medios de contecto
	*/
	
	/*mcSocio = new MedioContacto("mediosContactoContenedorSocioPersonaFisica", 2, tpPropietarioSocioPersonaFisica, idPropietarioSocioPersonaFisica, idSolicitud, idSujetoOblgiado);
	mcSocio.init();*/
	
	
})

function inicializaValoresNuevoSocio() {	
	$("form:#formNuevoSocio #esSocioExtranjero").attr('checked', false);
	$("form:#formNuevoSocio #radioDomExtranjero").attr('disabled', true);
	$("form:#formNuevoSocio #radioDomNacional").attr('disabled', true);
	
	//nuevoSocioPersonaFisica.jsp
	//$("form:#formNuevoSocioPersonaFisica #btnGuardarNuevoSocio").attr('disabled', true);
	
	$("form:#formNuevoSocioPersonaFisica #filaNSRFC").show();
	$("form:#formNuevoSocioPersonaFisica #filaNSCURP").show();
	$("form:#formNuevoSocioPersonaFisica #divNSFisicaDomicilio").show();	
	$("form:#formNuevoSocioPersonaFisica #divNSCargarDatos").show();	
	$("form:#formNuevoSocioPersonaFisica #nsFisicaPrimerAp").attr('disabled', true);
	$("form:#formNuevoSocioPersonaFisica #nsFisicaSegundoAp").attr('disabled', true);
	$("form:#formNuevoSocioPersonaFisica #nsFisicaNombre").attr('disabled', true);
	$("form:#formNuevoSocioPersonaFisica #nsFisicaPrimerAp").attr('readonly', true);
	$("form:#formNuevoSocioPersonaFisica #nsFisicaSegundoAp").attr('readonly', true);
	$("form:#formNuevoSocioPersonaFisica #nsFisicaNombre").attr('readonly', true);
	
	$("form:#formNuevoSocioPersonaFisica #nsFisicaRFC").val('');
	$("form:#formNuevoSocioPersonaFisica #nsFisicaCURP").val('');
	$("form:#formNuevoSocioPersonaFisica #nsFisicaPrimerAp").val('');
	$("form:#formNuevoSocioPersonaFisica #nsFisicaSegundoAp").val('');
	$("form:#formNuevoSocioPersonaFisica #nsFisicaNombre").val('');
} 

function inializaValoresNuevoSocioMoral() {
	$("form:#formNuevoSocioPersonaMoral #nsMoralRFC").val("");
	$("form:#formNuevoSocioPersonaMoral #nsMoralRazonSocial").val("");		
	$("form:#formNuevoSocioPersonaMoral #nsMoralTipoSociedad").val("");
	
	$("form:#formNuevoSocioPersonaMoral #nsMoralIdPersona").val("");
	//$("form:#formNuevoSocioPersonaMoral #tipoPersonaMoral").val("");
	$("form:#formNuevoSocioPersonaMoral #nsMoralNacional").val("");
	$("form:#formNuevoSocioPersonaMoral #nsMoralDomicilioNacional").val("");
}

function inializaValoresNuevoSocioFideicomiso() {

	$("form:#formNuevoSocioFideicomiso #nsFideicomisoNombre").val("");
	$("form:#formNuevoSocioFideicomiso #nsFideicomisoRFC").val("");
	$("form:#formNuevoSocioFideicomiso #nsFidecomisoProtoolo").val("");
	$("form:#formNuevoSocioFideicomiso #nsFideicomisoNotaria").val("");
	$("form:#formNuevoSocioFideicomiso #nsFideicmnioPartia").val("");
	$("form:#formNuevoSocioFideicomiso #nsFideicomisoCalle").val("");
	$("form:#formNuevoSocioFideicomiso #nsFideicomisolNumExt").val("");
	$("form:#formNuevoSocioFideicomiso #nsFideicomisoNumInt").val("");
	$("form:#formNuevoSocioFideicomiso #nsFideicomisoReferUno").val("");
	$("form:#formNuevoSocioFideicomiso #nsFideicomisoReferDos").val("");
	$("form:#formNuevoSocioFideicomiso #nsFideicomisoReferPost").val("");
	$("form:#formNuevoSocioFideicomiso #nsFideicomisoReferColonia").val("");
	$("form:#formNuevoSocioFideicomiso #nsFideicomisoReferLocalidad").val("");
	$("form:#formNuevoSocioFideicomiso #nsFideicomisoReferDeleg").val("");
	$("form:#formNuevoSocioFideicomiso #nsFideicomisoReferEntidad").val("");
	$("form:#formNuevoSocioFideicomiso #nsFideicomisoReferCP").val("");
	
}

function validaSocioExtranjero() {	
	var oForm = $("form:#formNuevoSocio").toObject({mode : 'first'});
	checkSocioExtranjero = oForm.esNacional;	
	
	if  (checkSocioExtranjero!=undefined) {
		$("form:#formNuevoSocio #radioDomExtranjero").attr('disabled', false);
		$("form:#formNuevoSocio #radioDomNacional").attr('disabled', false);
	} else {
		$("form:#formNuevoSocio #radioDomExtranjero").attr('disabled', true);
		$("form:#formNuevoSocio #radioDomNacional").attr('disabled', true);
	}	
}

function validaDatosNuevoSocio() {	
	var oForm = $("form:#formNuevoSocio").toObject({mode : 'first'});
	var contadorValidaNSFisica = 0;
	var buscarPersonaNSCounter = 0;
	var valorTipoPersona;
	
	if (oForm.tipoSocio != undefined){
		valorTipoPersona = oForm.tipoSocio.idTipoPersona;
		
		if(valorTipoPersona==undefined) {
			var oDialogo;
			construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error", "Seleccione un tipo de persona.", true);
			contadorValidaNSFisica++;
		}
	
	} else {
		var oDialogo;
		construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error", "Seleccione un tipo de persona.", true);
		contadorValidaNSFisica++;
	}
	var valorDomicilio = oForm.esDomicilioNacional;
	var valorSocioExtranjero = oForm.esNacional;
			
	
	if (valorTipoPersona=="1" && checkSocioExtranjero && valorDomicilio=="0") {
		$("form:#formNuevoSocioPersonaFisica #a1").hide();
		$("form:#formNuevoSocioPersonaFisica #a2").hide();
		$("form:#formNuevoSocioPersonaFisica #b1").hide();
		$("form:#formNuevoSocioPersonaFisica #b2").hide();
		//$("form:#formNuevoSocioPersonaFisica #filaNSRFC").css("display", "none");
		//$("form:#formNuevoSocioPersonaFisica #filaNSCURP").hide();
		
		$("form:#formNuevoSocioPersonaFisica #divNSFisicaDomicilio").hide();	
		$("form:#formNuevoSocioPersonaFisica #divNSCargarDatos").hide();	
		$("form:#formNuevoSocioPersonaFisica #nsFisicaPrimerAp").attr('disabled', false);
		$("form:#formNuevoSocioPersonaFisica #nsFisicaSegundoAp").attr('disabled', false);
		$("form:#formNuevoSocioPersonaFisica #nsFisicaNombre").attr('disabled', false);
		$("form:#formNuevoSocioPersonaFisica #nsFisicaPrimerAp").attr('readonly', false);
		$("form:#formNuevoSocioPersonaFisica #nsFisicaSegundoAp").attr('readonly', false);
		$("form:#formNuevoSocioPersonaFisica #nsFisicaNombre").attr('readonly', false);		
	} else if (valorTipoPersona=="2" && checkSocioExtranjero && valorDomicilio=="0") {					
		$("form:#formNuevoSocioPersonaMoral #divNSMoralDomicilio").css("display", "none");
		//$("form:#formNuevoSocioPersonaMoral #filaRFCMoral").hide();
		$("form:#formNuevoSocioPersonaMoral #moralA1").hide();
		$("form:#formNuevoSocioPersonaMoral #moralA2").hide();
		//$("form:#formNuevoSocioPersonaMoral #filaTipoSociedad").hide();
		$("form:#formNuevoSocioPersonaMoral #moralB1").hide();
		$("form:#formNuevoSocioPersonaMoral #moralB2").hide();
		$("form:#formNuevoSocioPersonaMoral #divNSMoralCargarDatos").hide();		
		$("form:#formNuevoSocioPersonaMoral #nsMoralRazonSocial").attr('disabled', false);
		$("form:#formNuevoSocioPersonaMoral #nsMoralRazonSocial").attr('readonly', false);	
		$("#divMediosContactoNSPersonaMoral").hide();
	}
	 
	if (valorDomicilio=="0") {
		valEsDomiclioNacional=false;
		buscarPersonaNSCounter++;
	}	else  {
		valEsDomiclioNacional=true;
	}	 	 
	if (checkSocioExtranjero) {
		valEsNacional=false;
		buscarPersonaNSCounter++;
	} else {
		valEsNacional=true;
	}
 
	if (contadorValidaNSFisica==0 && valorTipoPersona=="1") {
		$("form:#formNuevoSocioPersonaFisica #idTipoPersonaSocioPersonaFisica").val(valorTipoPersona);
		$("form:#formNuevoSocioPersonaFisica #nsFisicaNacional").val(valEsNacional);
		$("form:#formNuevoSocioPersonaFisica #nsFisicaDomicilioNacional").val(valEsDomiclioNacional);
		oDialogFisicaNacional.dialog("open");
		
		if (buscarPersonaNSCounter < 2){ // el 2 indica que se cumplen las dos condiciones (dom. extranjero y nac. extranjera)
			buscarPersonaFisicaNS();
		}
		
	} else if (contadorValidaNSFisica==0 && valorTipoPersona=="2") {
		$("form:#formNuevoSocioPersonaMoral #idTipoPersonaSocioPersonaMoral").val(valorTipoPersona);
		$("form:#formNuevoSocioPersonaMoral #nsMoralNacional").val(valEsNacional);
		$("form:#formNuevoSocioPersonaMoral #nsMoralDomicilioNacional").val(valEsDomiclioNacional);
		oDialogMoralNacional.dialog("open");
		
		if (buscarPersonaNSCounter < 2){ // el 2 indica que se cumplen las dos condiciones (dom. extranjero y nac. extranjera)
			$("form:#formNuevoSocioPersonaMoral #moralA1").show();
			$("form:#formNuevoSocioPersonaMoral #moralA2").show();
			$("form:#formNuevoSocioPersonaMoral #moralB1").show();
			$("form:#formNuevoSocioPersonaMoral #moralB2").show();
			$("form:#formNuevoSocioPersonaMoral #divNSMoralCargarDatos").show();
			buscarPersonaMoralNS();
		}
		
	} else if (contadorValidaNSFisica==0 && valorTipoPersona=="3") {
		$("form:#formNuevoSocioFideicomiso #idTipoPersonaSocioFideicomiso").val(valorTipoPersona);
		$("form:#formNuevoSocioFideicomiso #nsFideicomisoNacional").val(valEsNacional);
		$("form:#formNuevoSocioFideicomiso #nsFideicomisoDomicilioNacional").val(valEsDomiclioNacional);
		oDialogNuevoSocioFideicomiso.dialog("open");
		
		if (buscarPersonaNSCounter < 2){ // el 2 indica que se cumplen las dos condiciones (dom. extranjero y nac. extranjera)
			buscarPersonaFideicomisoNS();
		}
	}		
}

/*
function configurarBusquedaPersonaFisicaNS(){
	
	$.getScript("/gestionIndividuo-web/static/resources/js/delta/personas/fisica/PersonaFisica.js", function(){		
		
	});
}*/

function configurarBusquedaPersonaMoralNS(){
	/*
	 * configuracion para buscar a la persona fisica
	 */
	$.getScript("/gestionIndividuo-web/static/resources/js/delta/personas/moral/PersonaMoral.js", function(){
		
	});
}

/**
 * Funcion para invocar al proceso de buscar persona fisica
 */
function buscarPersonaFisicaNS() {
	PersonaFisicaCtrl.init('personaFisicaNS', PersonaFisicaCtrl.tipoServicio.COMPLETO, PersonaFisicaCtrl.tipoContexto.INTERNO, PersonaFisicaCtrl.tipoBusqueda.SAT);
	if (PersonaFisicaCtrl.persona != null){
		PersonaFisicaCtrl.persona = null;
		PersonaFisicaCtrl.personaEncontrada = false;
	}
	PersonaFisicaCtrl.setOnCloseCallback(fnOnPersonaRetorno); 
	PersonaFisicaCtrl.buscar();
}

function buscarPersonaMoralNS() {
	PersonaMoralCtrl.init('personaMoralNS');
	if (PersonaMoralCtrl.persona != null){
		PersonaMoralCtrl.persona = null;
		PersonaMoralCtrl.personaEncontrada = false;
	}
	PersonaMoralCtrl.setOnCloseCallback(fnOnPersonaMoralRetorno);
	PersonaMoralCtrl.buscar();
}

function buscarPersonaFideicomisoNS() {
	PersonaMoralCtrl.init('personaFideicomisoNS');
	if (PersonaMoralCtrl.persona != null){
		PersonaMoralCtrl.persona = null;
		PersonaMoralCtrl.personaEncontrada = false;
	}
	PersonaMoralCtrl.setOnCloseCallback(fnOnPersonaFideicomisoRetorno); 
	PersonaMoralCtrl.buscar();
}

/* 
 * Callback de la busqueda de personas fisica. 
 */ 
var fnOnPersonaRetorno = function(){
	
	var objTramiteFisica = this;
	var p = null;	
	
	if(objTramiteFisica.fisica != null) {
		p = objTramiteFisica.fisica;
	} else if (objTramiteFisica.datosICA != null) {
		p = objTramiteFisica.datosICA.personaFisicaIMSS;
	}
	
	if (p != null && p.nombre != null) {
		
		if (!cuentaRegistroConIdPersona(p, oDialogFisicaNacional)){
			p1 = p;
			return;
		}
		
		var idPersona = p.idPersona;
		var rfc = p.rfc;
		var curp = p.curp;
		var nombre = p.nombre;
		var primerApellido = p.primerApellido;
		var segundoApellido = p.segundoApellido;
						
		$("form:#formNuevoSocioPersonaFisica #nsFisicaRFC").val(rfc);
		$("form:#formNuevoSocioPersonaFisica #nsFisicaCURP").val(curp);
		$("form:#formNuevoSocioPersonaFisica #nsFisicaPrimerAp").val(primerApellido);
		$("form:#formNuevoSocioPersonaFisica #nsFisicaSegundoAp").val(segundoApellido);
		$("form:#formNuevoSocioPersonaFisica #nsFisicaNombre").val(nombre); 
		
		$("form:#formNuevoSocioPersonaFisica #nsFisicaIdPersona").val(idPersona != undefined ? idPersona : 0);
		$("form:#formNuevoSocioPersonaFisica #tipoPersonaFisica").val(true);	
		$("form:#formNuevoSocioPersonaFisica #esNacional").val(valEsNacional);
		$("form:#formNuevoSocioPersonaFisica #nsMoralDomicilioNacional").val(valEsDomiclioNacional);	
		 
		$("form:#formNuevoSocioPersonaFisica #btnGuardarNuevoSocio").attr('disabled', false);		
		 
		 
		$("#divMediosContactoNSPersonaFisica").html("");
		mcSocioFisico = new MedioContacto("divMediosContactoNSPersonaFisica", 2, tpPropietarioSocioPersonaFisica, null, idSolicitud, null);			 			
		mcSocioFisico.init();
        mcSocioFisico.extendValidation();
		
		if (idPersona!=undefined) {
			cargarDomicilioFiscalNS(idPersona, "fisica");
		}
		
	} else {
		oDialogFisicaNacional.dialog('close');
	}
}

function cargarDomicilioFiscalNSFisica(data) {
//	alert("CARGAR DOM PERSONA FISICA");
	 if (data!=null){
		 
		 $("form:#formNuevoSocioPersonaFisica #nsFisicaCalle").text(data.domicilioFiscal.vialidadPrimaria.nombre);
		 $("form:#formNuevoSocioPersonaFisica #nsFisicaNumExt").text(data.domicilioFiscal.numExterior1);
		 $("form:#formNuevoSocioPersonaFisica #nsFisicaNumInt").text(data.domicilioFiscal.numInterior);
//		 $("form:#formNuevoSocioPersonaFisica #nsFiscicaReferUno").val(data.referenciaPrimaria);
//		 $("form:#formNuevoSocioPersonaFisica #nsFiscicaReferDos").val(data.referenciaSecundaria);
//		 $("form:#formNuevoSocioPersonaFisica #nsFiscicaReferPost").val(data.referenciaPosterior);
		 $("form:#formNuevoSocioPersonaFisica #nsFisicaColonia").text(data.domicilioFiscal.asentamiento.nombre);	
		 $("form:#formNuevoSocioPersonaFisica #nsFisicaLocalidad").text(data.domicilioFiscal.asentamiento.localidad.nombre);
		 $("form:#formNuevoSocioPersonaFisica #nsFisicaDeleg").text(data.domicilioFiscal.asentamiento.localidad.municipio.nombre);			 
		 $("form:#formNuevoSocioPersonaFisica #nsFisicaEntidad").text(data.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre);
		 $("form:#formNuevoSocioPersonaFisica #nsFisicaCP").text(data.domicilioFiscal.codigoPostal.codigoPostal);
		 
	 }
}

/* 
 * Callback de la busqueda de personas moral. 
 */ 
var fnOnPersonaMoralRetorno = function(){ 
	var p = this;
	
	if (p != null && p.razonSocial != null) {
		var escrituraPM = p.escrituraConstitutiva;
		if(escrituraPM==undefined)
			escrituraPM = new Object();
		
		if (!cuentaRegistroConIdPersona(p, oDialogMoralNacional)){
			return;
		}
		
		var idPersonaMoral = p.idPersona;
		var rfcMoral = p.rfc;
		var razonSocial = p.razonSocial;	
		if (p.tipoSociedad != null){
			var tipoSOciedad = p.tipoSociedad.descripcion;
		}
		var idActaConstitutiva = p.idActaConstitutiva;
		var idNSocioMoral = p.idSocio;
		
		
		var numEscritura = escrituraPM!= undefined ? escrituraPM.numEscritura : "Sin Informaci\u00F3n"
		var numNotaria = escrituraPM!= undefined ? escrituraPM.numNotaria : "Sin Informaci\u00F3n"
		var entidad = escrituraPM!= undefined ? escrituraPM.lugarExpedicion.entidadFederativa.nombre : "Sin Informaci\u00F3n"
		var municipio = escrituraPM!= undefined ? escrituraPM.lugarExpedicion.nombre : "Sin Informaci\u00F3n"
		var fechaConstitucion;
		
		if(escrituraPM!= undefined){
			fechaConstitucion = escrituraPM.fechaExpedicion;
			var fechaConstitucionDate = $.datepicker.parseDate("yy-mm-dd",  fechaConstitucion);
			fechaConstitucion = $.datepicker.formatDate( "dd/mm/yy", fechaConstitucionDate);
		}else{
			fechaConstitucion = "Sin Informaci\u00F3n";
		}
		var folioMercantil = escrituraPM.folioMercantil != undefined ? escrituraPM.folioMercantil : "Sin informaci\u00F3n";
		var seccion = escrituraPM.seccion != undefined ? escrituraPM.seccion : "Sin informaci\u00F3n";
		var partida = escrituraPM.partida != undefined ? escrituraPM.partida : "Sin informaci\u00F3n";
		var volumen = escrituraPM.volumen != undefined ? escrituraPM.volumen : "Sin informaci\u00F3n";
		var foja = escrituraPM.foja != undefined ? escrituraPM.foja : "Sin informaci\u00F3n";
		
		
		
		
		$("form:#formNuevoSocioPersonaMoral #nsMoralRFC").val(rfcMoral);
		$("form:#formNuevoSocioPersonaMoral #nsMoralRazonSocial").val(razonSocial);		
		$("form:#formNuevoSocioPersonaMoral #nsMoralTipoSociedad").val(tipoSOciedad);
		$("form:#formNuevoSocioPersonaMoral #nsMoralIdActaConstitutiva").val(idActaConstitutiva);
		
		$("form:#formNuevoSocioPersonaMoral #idPersonaMoral").val(idPersonaMoral);
		$("form:#formNuevoSocioPersonaMoral #nsMoralIdPersona").val(idPersonaMoral);
		$("form:#formNuevoSocioPersonaMoral #tipoPersonaMoral").val(false);
		$("form:#formNuevoSocioPersonaMoral #nsMoralNacional").val(valEsNacional);
		$("form:#formNuevoSocioPersonaMoral #nsMoralDomicilioNacional").val(valEsDomiclioNacional);
		
		$("form:#formNuevoSocioPersonaMoral #nsMoralNumEscritura").val(numEscritura);
		$("form:#formNuevoSocioPersonaMoral #nsMoralNumNotaria").val(numNotaria);
		$("form:#formNuevoSocioPersonaMoral #nsMoralEntidadFed").val(entidad);
		$("form:#formNuevoSocioPersonaMoral #nsMoralMunicipio").val(municipio);
		$("form:#formNuevoSocioPersonaMoral #nsMoralFecConstitucion").val(fechaConstitucion);
		
		
		$("form:#formNuevoSocioPersonaMoral #nsMoralIdActaConstitutiva").val(folioMercantil);
		$("form:#formNuevoSocioPersonaMoral #nsMoralSeccion").val(seccion);
		$("form:#formNuevoSocioPersonaMoral #nsMoralPartia").val(partida);
		$("form:#formNuevoSocioPersonaMoral #nsMoralVolumen").val(volumen);
		$("form:#formNuevoSocioPersonaMoral #nsMoralFoja").val(foja);
		//escrituraPM.numEscritura escrituraPM.numNotaria
		
		 if (idPersonaMoral!=undefined) {
			 $("#divMediosContactoNSPersonaMoral").html("");
			 mcSocioMoral = new MedioContacto("divMediosContactoNSPersonaMoral", 2, tpPropietarioSocioPersonaFisica, null, idSolicitud, null);			 			
			 mcSocioMoral.init();
             mcSocioMoral.extendValidation();
			 
			cargarDomicilioFiscalNS(idPersonaMoral, "moral");
		 }	
		 
	} else {
		alert('persona fisica viene null desde el servicio de personas');
	}
}

function cargarDomicilioFiscalNSMoral(data) {
//	alert("CARGAR DOM PERSONA MORAL");
	 if (data!=null){
		 $("form:#formNuevoSocioPersonaMoral #nsMoralCalle").text(data.domicilioFiscal.vialidadPrimaria.nombre);
		 $("form:#formNuevoSocioPersonaMoral #nsMoralNumExt").text(data.domicilioFiscal.numExterior1);
		 $("form:#formNuevoSocioPersonaMoral #nsMoralNumInt").text(data.domicilioFiscal.numInterior);
//		 $("form:#formNuevoSocioPersonaMoral #nsMoralReferUno").val(data.referenciaPrimaria);
//		 $("form:#formNuevoSocioPersonaMoral #nsMoralReferDos").val(data.referenciaSecundaria);
//		 $("form:#formNuevoSocioPersonaMoral #nsMoralReferPost").val(data.referenciaPosterior);
		 $("form:#formNuevoSocioPersonaMoral #nsMoralReferColonia").text(data.domicilioFiscal.asentamiento.nombre);	
		 $("form:#formNuevoSocioPersonaMoral #nsMoralReferLocalidad").text(data.domicilioFiscal.asentamiento.localidad.nombre);
		 $("form:#formNuevoSocioPersonaMoral #nsMoralReferDeleg").text(data.domicilioFiscal.asentamiento.localidad.municipio.nombre);			 
		 $("form:#formNuevoSocioPersonaMoral #nsMoralReferEntidad").text(data.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre);
		 $("form:#formNuevoSocioPersonaMoral #nsMoralReferCP").text(data.domicilioFiscal.codigoPostal.codigoPostal);		 
	 }
}


/* 
 * Callback de la busqueda de personas fisica. 
 */ 
var fnOnPersonaFideicomisoRetorno = function(){ 
	var p = this;
	
	if (p != null && p.razonSocial != null) {
		
		if (!cuentaRegistroConIdPersona(p, oDialogNuevoSocioFideicomiso)){
			return;
		}
		
		
		var idPersonaFideicomiso = p.idPersona;
		var rfcFideicomiso = p.rfc;
		var razonSocialFideicomiso = p.razonSocial;
		
		$("form:#formNuevoSocioFideicomiso #nsFideicomisoRFC").val(rfcFideicomiso);
		$("form:#formNuevoSocioFideicomiso #nsFideicomisoNombre").val(razonSocialFideicomiso);
		$("form:#formNuevoSocioFideicomiso #idPersonaFideicomiso").val(idPersonaFideicomiso);		
		
		 if (idPersonaFideicomiso!=undefined) {
			 $("#divMediosContactoNSPersonaFideicomiso").html("");
			 mcSocioFideicomiso = new MedioContacto("divMediosContactoNSPersonaFideicomiso", 2, tpPropietarioSocioPersonaFisica, null, idSolicitud, null);			 			
			 mcSocioFideicomiso.init();
             mcSocioFideicomiso.extendValidation();
			 
			 cargarDomicilioFiscalNS(idPersonaFideicomiso, "fideicomiso");
		 }
	} else {
		alert('persona fisica viene null desde el servicio de personas');
	}
}

function cargarDomicilioFiscalNSFideicomiso(data) {
//	alert("CARGAR DOM PERSONA MORAL");
	 if (data!=null){
		 $("form:#formNuevoSocioPersonaMoral #nsMoralCalle").val(data.calle);
		 $("form:#formNuevoSocioPersonaMoral #nsMoralNumExt").val(data.numeroLetraExterior);
		 $("form:#formNuevoSocioPersonaMoral #nsMoralNumInt").val(data.numeroLetraInterior);
		 $("form:#formNuevoSocioPersonaMoral #nsMoralReferUno").val(data.referenciaPrimaria);
		 $("form:#formNuevoSocioPersonaMoral #nsMoralReferDos").val(data.referenciaSecundaria);
		 $("form:#formNuevoSocioPersonaMoral #nsMoralReferPost").val(data.referenciaPosterior);
		 $("form:#formNuevoSocioPersonaMoral #nsMoralReferColonia").val(data.coloniaPoblacion);	
		 $("form:#formNuevoSocioPersonaMoral #nsMoralReferLocalidad").val(data.localidad);
		 $("form:#formNuevoSocioPersonaMoral #nsMoralReferDeleg").val(data.delegacionMunicipio);			 
		 $("form:#formNuevoSocioPersonaMoral #nsMoralReferEntidad").val(data.entidadFederativa);
		 $("form:#formNuevoSocioPersonaMoral #nsMoralReferCP").val(data.codigoPostal);			 
	 }
}

function creaTablaContactosNuevoSocio() {
	dtContctosNS = $('#dtContactosNuevoSocio').dataTable({
		"bJQueryUI": true,
		"bPaginate": false,
		"bLengthChange": false,
		"iDisplayLength": 5,
		"bFilter": false,
		"bSort": false,
		"bInfo": false,
		"bAutoWidth": false,
		"bServerSide" : true,			
		"aoColumns" : [ {
				"sWidth": "50%",
				"sTitle" : "Tipo de Dato de contacto",
				"mDataProp" : "tipoContacto",
				"sClass": "odd"
			},{
				"sWidth": "50%",
				"sTitle" : "Descripción",
				"mDataProp" : "descripcion",
				"sClass": "odd"
			}],
		"bProcessing" : true,
		"sAjaxSource" : '/delta-gestionPatronal-web/socio/fb/paginar',
		"fnServerData" : inicializaValoresNuevoSocio
	});	
}

function agregarSocioSesion() {	
	var oForm = $("#formNuevoSocioPersonaFisica").toObject({mode : "first"});	
	oForm.rfc =  $("form:#formNuevoSocioPersonaFisica #nsFisicaRFC").val();
	oForm.curp = $("form:#formNuevoSocioPersonaFisica #nsFisicaCURP").val();
	oForm.primerApellido =  $("form:#formNuevoSocioPersonaFisica #nsFisicaPrimerAp").val();
	oForm.segundoApellido = $("form:#formNuevoSocioPersonaFisica #nsFisicaSegundoAp").val();
	oForm.nombres = $("form:#formNuevoSocioPersonaFisica #nsFisicaNombre").val();
	
	if(oForm.primerApellido!=undefined)
		oForm.primerApellido=oForm.primerApellido.toUpperCase();
	if(oForm.segundoApellido!=undefined)
		oForm.segundoApellido=oForm.segundoApellido.toUpperCase();
	if(oForm.nombres!=undefined)
		oForm.nombres=oForm.nombres.toUpperCase();
	if(oForm.nombreRazonSocial!=undefined)
		oForm.nombreRazonSocial=oForm.nombreRazonSocial.toUpperCase();
	
	//Se agrega el objeto TramiteFisica al socio
	var tramiteFisica = PersonaFisicaCtrl.getPersona();
	oForm.tramiteFisica = new Object();
	oForm.tramiteFisica = tramiteFisica;
	
	if (mcSocioFisico != undefined){ // esto indica que la persona es extranjera con residencia extranjera si viene undefined
		// verificamos datos de contacto, al menos debe ser capturado telefono (fijo o movil) o correo electronico
		var mediosContactoListPFisica = mcSocioFisico.obtenerListaMediosContacto();
		var contadorDatosContactoRequeridosPFisica = 0;
		
		if (mediosContactoListPFisica.length > 0){
			for (i=0 ; i < mediosContactoListPFisica.length ; i++){
				if ((mediosContactoListPFisica[i].tipoMedioContacto.idTipoMedioContacto == 1 && mediosContactoListPFisica[i].desFormaContacto != "")
					|| (mediosContactoListPFisica[i].tipoMedioContacto.idTipoMedioContacto == 2 && mediosContactoListPFisica[i].desFormaContacto != "")
					|| (mediosContactoListPFisica[i].tipoMedioContacto.idTipoMedioContacto == 3 && mediosContactoListPFisica[i].desFormaContacto != "")){ // implementar exp reg para correo-e
					contadorDatosContactoRequeridosPFisica++;
				}
			}
			
			if (contadorDatosContactoRequeridosPFisica == 0){
				var oDialogo;
				construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error", "Debe capturar al menos un Tel\u00E9fono fijo o m\u00F3vil y/o Correo Electr\u00F3nico v\u00E1lidos.", true,
						undefined, undefined, 150,500);
			} else {
				// asignamos datos de contacto
				oForm.mediosContacto = mcSocioFisico.obtenerListaMediosContacto();
					
				$.postJSON("/delta-gestionPatronal-web/socios/fb/agregarSocioSesion.do",oForm,function(data) {
					if (data == "OK") {				
						oDialogFisicaNacional.dialog("close");
						oDialogNuevoSocio.dialog("close");
						dtSocioForSession.fnDraw();
					} else if (data == "DUPLICATED"){
						oDialogErrorDuplicadoSocio.dialog("open");
					} else {
						alert(data);
					}
				 }).error(function(datas){ 
					 alert('Error al enviar la peticion');			
				});
			}
			
		} else {
			var oDialogo;
			construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error", "Debe capturar al menos un dato de contacto.\nLos datos requeridos son: Tel\u00E9fono fijo o m\u00F3vil y/o Correo Electr\u00F3nico.", true,
					undefined, undefined, 150,500);
		}
	} else { // se mandan los datos del Socio extranjero con residencia extranjera
		$.postJSON("/delta-gestionPatronal-web/socios/fb/agregarSocioSesion.do",oForm,function(data) {
					if (data == "OK") {				
						oDialogFisicaNacional.dialog("close");
						oDialogNuevoSocio.dialog("close");
						dtSocioForSession.fnDraw();
					} else if (data == "DUPLICATED"){
						oDialogErrorDuplicadoSocio.dialog("open");
					} else {
						alert(data);
					}
				 }).error(function(datas){ 
					 alert('Error al enviar la peticion');			
				});
	}
	
}

function canclelarAgregarSocioSesion() {
	// turnamos nulo idPersona en caso de proceder a registrar a un socio extranejro con residencia extranjera
	$("#formNuevoSocioPersonaFisica #nsFisicaIdPersona").val(undefined);
	$("#formNuevoSocioPersonaMoral #nsMoralIdPersona").val(undefined);
	$("#formNuevoSocioFideicomiso #idPersonaFideicomiso").val(undefined);
	
	oDialogFisicaNacional.dialog("close");
}

function agregarSocioMoralSesion() {	
	var oForm = $("#formNuevoSocioPersonaMoral").toObject({mode : "first"});	
	oForm.rfc =  $("form:#formNuevoSocioPersonaMoral #nsMoralRFC").val();
	oForm.nombreRazonSocial = $("form:#formNuevoSocioPersonaMoral #nsMoralRazonSocial").val();
	oForm.tipoSociedad =  $("form:#formNuevoSocioPersonaMoral #nsMoralTipoSociedad").val();	
	oForm.tipoPersonaFiscalPatron = tipoPersonaFiscal;
	oForm.domicilioFiscal=new Object();
	oForm.domicilioFiscal.vialidadPrimaria=new Object();
	oForm.domicilioFiscal.asentamiento=new Object();
	oForm.domicilioFiscal.asentamiento.localidad=new Object();
	oForm.domicilioFiscal.asentamiento.localidad.municipio=new Object();
	oForm.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa=new Object();
	oForm.domicilioFiscal.codigoPostal= new Object();
	
	oForm.domicilioFiscal.vialidadPrimaria.nombre = $("form:#formNuevoSocioPersonaMoral #nsMoralCalle").text();
	oForm.domicilioFiscal.numExterior1 = $("form:#formNuevoSocioPersonaMoral #nsMoralNumExt").text();
	oForm.domicilioFiscal.numExteriorAlf =$("form:#formNuevoSocioPersonaMoral #nsMoralNumExtAlf").text();
	oForm.domicilioFiscal.numInterior =$("form:#formNuevoSocioPersonaMoral #nsMoralNumInt").text();
	oForm.domicilioFiscal.numInteriorAlf =$("form:#formNuevoSocioPersonaMoral #nsMoralNumIntAlf").text();
	oForm.domicilioFiscal.asentamiento.nombre = $("form:#formNuevoSocioPersonaMoral #nsMoralReferColonia").text();
	oForm.domicilioFiscal.asentamiento.localidad.municipio.nombre = $("form:#formNuevoSocioPersonaMoral #nsMoralReferDeleg").text();
	oForm.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre = $("form:#formNuevoSocioPersonaMoral #nsMoralReferEntidad").text();
	oForm.domicilioFiscal.codigoPostal.codigoPostal = $("form:#formNuevoSocioPersonaMoral #nsMoralReferCP").text();
	// sec. escritura constitutiva, pendiente para ver de odonde se trae
	/*oForm.escrituraConstitutiva.folioMercantil = $("form:#formNuevoSocioPersonaMoral #nsMoralIdActaConstitutiva").val();
	oForm.escrituraConstitutiva.seccion = $("form:#formNuevoSocioPersonaMoral #nsMoralSeccion").val();
	oForm.escrituraConstitutiva.partida = $("form:#formNuevoSocioPersonaMoral #nsMoralPartia").val();
	oForm.escrituraConstitutiva.volumen = $("form:#formNuevoSocioPersonaMoral #nsMoralVolumen").val();
	oForm.escrituraConstitutiva.foja = $("form:#formNuevoSocioPersonaMoral #nsMoralFoja").val();*/
	
	if (mcSocioMoral != undefined){ // esto indica que la persona es extranjera con residencia extranjera si viene undefined
		// verificamos datos de contacto, al menos debe ser capturado telefono (fijo o movil) o correo electronico
		var mediosContactoListPMoral = mcSocioMoral.obtenerListaMediosContacto();
		var contadorDatosContactoRequeridosPMoral = 0;
		
		if (mediosContactoListPMoral.length > 0){
			for (i=0 ; i < mediosContactoListPMoral.length ; i++){
				if ((mediosContactoListPMoral[i].tipoMedioContacto.idTipoMedioContacto == 1 && mediosContactoListPMoral[i].desFormaContacto != "")
					|| (mediosContactoListPMoral[i].tipoMedioContacto.idTipoMedioContacto == 2 && mediosContactoListPMoral[i].desFormaContacto != "")
					|| (mediosContactoListPMoral[i].tipoMedioContacto.idTipoMedioContacto == 3 && mediosContactoListPMoral[i].desFormaContacto != "")){ // implementar exp reg para correo-e
					contadorDatosContactoRequeridosPMoral++;
				}
			}
			
			if (contadorDatosContactoRequeridosPMoral == 0){
				var oDialogo;
				construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error", "Debe capturar al menos un Tel\u00E9fono fijo o m\u00F3vil y/o Correo Electr\u00F3nico v\u00E1lidos.", true,
						undefined, undefined, 150,500);
			} else {
				// asignamos datos de contacto
				oForm.mediosContacto = mcSocioMoral.obtenerListaMediosContacto();
													 
				$.postJSON("/delta-gestionPatronal-web/socios/fb/agregarSocioSesion.do",oForm,function(data) { 
								
					if (data == "OK") {				
						oDialogMoralNacional.dialog("close");
						oDialogNuevoSocio.dialog("close");
						dtSocioForSession.fnDraw();
					} else if (data == "DUPLICATED"){
						oDialogErrorDuplicadoSocio.dialog("open");
					} else {
						alert(data);
					}
					
				 }).error(function(datas){ 
					 alert('Error al enviar la peticion');			
				});
			}
			
		} else {
			var oDialogo;
			construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error", "Debe capturar al menos un dato de contacto.\nLos datos requeridos son: Tel\u00E9fono fijo o m\u00F3vil y/o Correo Electr\u00F3nico.", true,
					undefined, undefined, 150,500);
		}
	} else { // se mandan los datos del Socio extranjero con residencia extranjera
		$.postJSON("/delta-gestionPatronal-web/socios/fb/agregarSocioSesion.do",oForm,function(data) { 
								
					if (data == "OK") {				
						oDialogMoralNacional.dialog("close");
						oDialogNuevoSocio.dialog("close");
						dtSocioForSession.fnDraw();
					} else if (data == "DUPLICATED"){
						oDialogErrorDuplicadoSocio.dialog("open");
					} else {
						alert(data);
					}
					
				 }).error(function(datas){ 
					 alert('Error al enviar la peticion');			
				});
	}
	
}

function canclelarAgregarSocioMoralSesion() {
	oDialogMoralNacional.dialog("close");
}

function agregarSocioFideicomisoSesion() {	
	var oForm = $("#formNuevoSocioFideicomiso").toObject({mode : "first"});	
	
	if (oForm.estado.clave == undefined || oForm.estado.clave == "-1"){
		var oDialogo;
		construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error", "Seleccione un estado.", true);

		$("form:#formNuevoSocioFideicomiso #estado\\.clave").focus();
		return;
	}
	
	oForm.rfc =  $("form:#formNuevoSocioFideicomiso #nsFideicomisoRFC").val();
	oForm.nombreRazonSocial = $("form:#formNuevoSocioFideicomiso #nsFideicomisoNombre").val();
	
	if (mcSocioFideicomiso != undefined){ // esto indica que la persona es extranjera con residencia extranjera si viene undefined
		// verificamos datos de contacto, al menos debe ser capturado telefono (fijo o movil) o correo electronico
		var mediosContactoListFideicomiso = mcSocioFideicomiso.obtenerListaMediosContacto();
		var contadorDatosContactoRequeridosFideicomiso = 0;
		
		
		if (mediosContactoListFideicomiso.length > 0){
			for (i=0 ; i < mediosContactoListFideicomiso.length ; i++){
				if ((mediosContactoListFideicomiso[i].tipoMedioContacto.idTipoMedioContacto == 1 && mediosContactoListFideicomiso[i].desFormaContacto != "")
					|| (mediosContactoListFideicomiso[i].tipoMedioContacto.idTipoMedioContacto == 2 && mediosContactoListFideicomiso[i].desFormaContacto != "")
					|| (mediosContactoListFideicomiso[i].tipoMedioContacto.idTipoMedioContacto == 3 && mediosContactoListFideicomiso[i].desFormaContacto != "")){ // implementar exp reg para correo-e
					contadorDatosContactoRequeridosFideicomiso++;
				}
			}
			
			if (contadorDatosContactoRequeridosFideicomiso == 0){
				var oDialogo;
				construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error", "Debe capturar al menos un Tel\u00E9fono fijo o m\u00F3vil y/o Correo Electr\u00F3nico v\u00E1lidos.", true,
						undefined, undefined, 150,500);
			} else {
				// asignamos datos de contacto
				oForm.mediosContacto = mcSocioFideicomiso.obtenerListaMediosContacto();
				
				// datos de contrato (el estado para contrato ya viene precargado desde el select)
				if ($("form:#formNuevoSocioFideicomiso #nsFidecomisoProtoolo").val() != undefined && $("form:#formNuevoSocioFideicomiso #nsFidecomisoProtoolo").val() != ""){
					oForm.numeroInstrumetoProtocolizacion = $("form:#formNuevoSocioFideicomiso #nsFidecomisoProtoolo").val();
				} else {
					oForm.numeroInstrumetoProtocolizacion = 0;
				}
				 
				oForm.notariaCorreduria = $("form:#formNuevoSocioFideicomiso #nsFideicomisoNotaria").val(); 
				oForm.fechaExpedicionContrato = $("form:#formNuevoSocioFideicomiso #nsFideicmnioPartia").val(); 
													 
				$.postJSON("/delta-gestionPatronal-web/socios/fb/agregarSocioSesion.do",oForm,function(data) { 
								
					if (data == "OK") {				
						oDialogNuevoSocioFideicomiso.dialog("close");
						oDialogNuevoSocio.dialog("close");
						dtSocioForSession.fnDraw();
					} else if (data == "DUPLICATED"){
						oDialogErrorDuplicadoSocio.dialog("open");
					} else {
						alert(data);
					}
										
				 }).error(function(datas){ 
					 alert('Error al enviar la peticion');			
				});
			}
			
		} else {
			var oDialogo;
			construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error", "Debe capturar al menos un dato de contacto.\nLos datos requeridos son: Tel\u00E9fono fijo o m\u00F3vil y/o Correo Electr\u00F3nico.", true,
					undefined, undefined, 150,500);
		}
	} else {
		// datos de contrato (el estado para contrato ya viene precargado desde el select)
				if ($("form:#formNuevoSocioFideicomiso #nsFidecomisoProtoolo").val() != undefined && $("form:#formNuevoSocioFideicomiso #nsFidecomisoProtoolo").val() != ""){
					oForm.numeroInstrumetoProtocolizacion = $("form:#formNuevoSocioFideicomiso #nsFidecomisoProtoolo").val();
				} else {
					oForm.numeroInstrumetoProtocolizacion = 0;
				}
				 
				oForm.notariaCorreduria = $("form:#formNuevoSocioFideicomiso #nsFideicomisoNotaria").val(); 
				oForm.fechaExpedicionContrato = $("form:#formNuevoSocioFideicomiso #nsFideicmnioPartia").val(); 
													 
				$.postJSON("/delta-gestionPatronal-web/socios/fb/agregarSocioSesion.do",oForm,function(data) { 
								
					if (data == "OK") {				
						oDialogNuevoSocioFideicomiso.dialog("close");
						oDialogNuevoSocio.dialog("close");
						dtSocioForSession.fnDraw();
					} else if (data == "DUPLICATED"){
						oDialogErrorDuplicadoSocio.dialog("open");
					} else {
						alert(data);
					}
										
				 }).error(function(datas){ 
					 alert('Error al enviar la peticion');			
				});
	}
	
	
}

function canclelarAgregarSocioFideicomisoSesion() {
	oDialogNuevoSocioFideicomiso.dialog("close");
}


function cargarDomicilioFiscalNS(idPersonaNS, tipoPersonaNS) {
	
//	var datosRequest={idPersona: idPersonaNS, tipoPersonaFiscal:tipoPersonaNS} ;
	var oForm = new  Object();
	oForm.idPersona = idPersonaNS;
	oForm.denominacionRazonSocial = tipoPersonaNS;
	
	$.postJSON("/delta-gestionPatronal-web/socios/fb/cargarDomicilioNuevoSocio.do",oForm,function(data) { 
		if (data!=null) {			
//			alert("Operacion exitosa");	
			if (tipoPersonaNS=="fisica") {
				cargarDomicilioFiscalNSFisica(data);
			} else if (tipoPersonaNS=="moral") {
				cargarDomicilioFiscalNSMoral(data);
			} else if (tipoPersonaNS=="fideicomiso") {
				cargarDomicilioFiscalNSFideicomiso(data);
			}
		}					
	 }).error(function(datas){ 
		 alert('Error al enviar la peticion');			
	});
	
}

function configurarDatePickerExpedicionContratoSocioFideicomiso(){
	/*Fecha de regsitro de sindicato*/
	$("#nsFideicmnioPartia").datepicker({
		maxDate: "+0D",
		showOn: "button",
		buttonImage: context_path + "/static/resources/imagenes/calendar.gif",
		buttonImageOnly: true,
		dateFormat: "dd/mm/yy",
		changeMonth: true,
		changeYear: true,
		yearRange: '-112:+0'
	});
}

function capturaFolioMercantil() {		
	if ($("form:#formNuevoSocioPersonaMoral #nsMoralIdActaConstitutiva").val()!=""){
		$("form:#formNuevoSocioPersonaMoral #nsMoralSeccion").attr("disabled", true);
		$("form:#formNuevoSocioPersonaMoral #nsMoralPartia").attr("disabled", true);
		$("form:#formNuevoSocioPersonaMoral #nsMoralVolumen").attr("disabled", true);
		$("form:#formNuevoSocioPersonaMoral #nsMoralFoja").attr("disabled", true);
	} else {
		$("form:#formNuevoSocioPersonaMoral #nsMoralSeccion").attr("disabled", false);
		$("form:#formNuevoSocioPersonaMoral #nsMoralPartia").attr("disabled", false);
		$("form:#formNuevoSocioPersonaMoral #nsMoralVolumen").attr("disabled", false);
		$("form:#formNuevoSocioPersonaMoral #nsMoralFoja").attr("disabled", false);
	}
}

function deshabilitarFolioMercantil() {
	if ($("form:#formNuevoSocioPersonaMoral #nsMoralSeccion").val()!="" 
		|| $("form:#formNuevoSocioPersonaMoral #nsMoralPartia").val()!="" 
		|| $("form:#formNuevoSocioPersonaMoral #nsMoralVolumen").val()!=""
		|| $("form:#formNuevoSocioPersonaMoral #nsMoralFoja").val()!="") {
			$("form:#formNuevoSocioPersonaMoral #nsMoralIdActaConstitutiva").attr("disabled", true);
	} else {
		$("form:#formNuevoSocioPersonaMoral #nsMoralIdActaConstitutiva").attr("disabled", false);
	}
}

function cerrarDialogoSeleccionNuevoSocio(){
	divGralAgregarSocioDialog.dialog('close');
}

function cuentaRegistroConIdPersona(p, oDialog){
	if(p.idPersona == undefined){
		oDialog.dialog("close");
		var oDialogo;
		construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error", 
				"Se ha encontrado que la persona " + p.nombre + " " + p.primerApellido + " " + p.segundoApellido + " esta registrada ante " +
				"el SAT (Sistema de Administraci&oacute;n Tributaria) y/o RENAPO, " +
				"sin embargo no se ha encontrado registro de la misma dentro del Instituto Mexicano del Seguro Social.<br><br>" +
				"Recuerde que usted puede registrar como socio solo aquellas personas que previamente han sido registradas ante el instituto, " +
				"por favor aseg&uacute;rese que la persona que desea registrar como socio ha sido acreditada previamente " +
				"ante el Instituto Mexicano del Seguro Social.", true, undefined,undefined,250,800);
		return false;
	} else {
		return true;
	}
}
