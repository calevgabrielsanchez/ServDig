/**
 * Paso de la consulta de los candidatos
 */

//Datos del formulario
var forma;

//Mensaje de la operacion
var messageProcess;

// Ventana modal de informacion del procesos
var modal;

//JSON de la forma
var oForm;

var sIdForma = "form#forma";

var formaSiguiente;

var sSource = "/servicios/internos/persona/fisica/paso1";

var errorEnPasos = false;

/*
 * Inicializamos los componentes necesarios.
 */
$(document).ready(function() {

	forma = $("form#forma");
	formaSiguiente = $("form#formaSiguiente");

	messageProcess = $("#messageProcesoConsulta");

	$(forma).submit(function(event) {

		consultar();
		return false;
	});

	modal = $('#modalInfoProcess');

	$(modal).modal();

	$(modal).modal('toggle');

});

function consultar() {

	fnHideErrores("form#forma");
	block();

	// 1. Validamos los datos

	var isValido = validar();

	if (isValido) {

		flujo.pasos[0].procesar();

		flujo.pasos[1].procesar();

		flujo.pasos[2].procesar();

		/*
		 *  2. Una vez ejecutados los pasos anteriores se debe de
		 *  verificar el siguiente pantalla cual debe de ser.
		 */

		var bseguir = flujo.transiciones[0].validar();

		if (bseguir) {

			flujo.transiciones[0].seguir();

		} else {

			// Debemos de consultar la persona en las diferentes entidades externas.

			// Iniciamos el flujo alterno...

			flujoAlterno.pasos[0].procesar();

			flujoAlterno.pasos[1].procesar();

		}
	}

	unblock();
}

/**
 * 
 */
function block() {
	$(modal).modal('show');
	return true;
}

/**
 * 
 */
function unblock() {
	$(modal).modal('hide');
	return true;
}

/**
 * 
 * @param message
 */
function updateState(message) {
	$(messageProcess).text(message);
}

/**
 * Validamos los campos antes de iniciar la consulta
 * de los 3 pasos.
 */
function validar() {

	$.ajaxSetup({
		async : false
	});

	updateState("Iniciando la validacion");

	var url = context_path + sSource + "/consultar/validar/datos";

	oForm = $(forma).toObject();

	var bValidar = false;

	$.postJSON(url, oForm, function(data) {
		bValidar = true;
	}).error(function(data) {
		fnProcesarErrores(data, "form#forma");
		bValidar = false;
	});

	return bValidar;
}

/**
 * Consulta por CURP en el IMSS
 */
var paso1 = function() {

	$.ajaxSetup({
		async : false
	});

	updateState(this.mensaje);

	var errorEnPaso = false;

	$.postJSON(this.url, oForm, function(data) {

	}).error(function(data) {
		errorEnPaso = true;

	});

	if (errorEnPaso) {
		this.hasError = true;
	}

}

/**
 * Consulta por RFC en el IMSS
 */
var paso2 = function() {

	$.ajaxSetup({
		async : false
	});

	updateState(this.mensaje);

	var errorEnPaso = false;

	$.postJSON(this.url, oForm, function(data) {

	}).error(function(data) {
		errorEnPaso = true;
	});

	if (errorEnPaso) {
		this.hasError = true;
	}
}

/**
 * Consulta por Datos Basicos en el IMSS
 */
var paso3 = function() {
	$.ajaxSetup({
		async : false
	});

	updateState(this.mensaje);

	var errorEnPaso = false;

	$.postJSON(this.url, oForm, function(data) {

	}).error(function(data) {
		errorEnPaso = true;
	});

	if (errorEnPaso) {
		this.hasError = true;
	}
}

/**
 * 
 */
var transicionSiguienteValidar = function() {

	var bSeguir = false;

	$.ajaxSetup({
		async : false
	});

	updateState(this.mensaje);

	var url = context_path + sSource + "/validar/transicion/siguiente";
	$.postJSON(url, oForm, function(data) {
		bSeguir = true;

	}).error(function(data) {
		errorEnPasos = true;
		bSeguir = false;
	});

	return bSeguir;
}

/**
 * 
 */
var transicionSeguir = function() {

	$.ajaxSetup({
		async : false
	});

	updateState(this.mensaje);

	$(formaSiguiente).submit();

}



/**
 * Consulta la persona en el RENAPO a traves del CURP.
 */
var fnPasoConsultarEnRenapo = function() {

	$.ajaxSetup({
		async : false
	});

	updateState(this.mensaje);

	var errorEnPaso = false;

	$.postJSON(this.url, oForm, function(data) {

	}).error(function(data) {
		errorEnPaso = true;
	});

	if (errorEnPaso) {
		this.hasError = true;
	}
}

/**
 * Consulta de la persona en el SAT
 */
var fnPasoConsultarEnSat = function() {

	$.ajaxSetup({
		async : false
	});

	updateState(this.mensaje);

	var errorEnPaso = false;

	$.postJSON(this.url, oForm, function(data) {

	}).error(function(data) {
		errorEnPaso = true;
	});

	if (errorEnPaso) {
		this.hasError = true;
	}

}

var flujo = {
	pasos : [ {
		nombre : "Consulta de persona fisica por CURP en IMSS",
		mensaje : " Consultando personas por CURP...",
		procesar : paso1,
		url : context_path + sSource + "/consultar/imss/curp",
		hasError : false
	},

	{
		nombre : "Consulta de persona fisica por RFC en IMSS",
		mensaje : " Consultando personas por RFC...",
		procesar : paso2,
		url : context_path + sSource + "/consultar/imss/rfc",
		hasError : false
	}, {
		nombre : "Consulta de persona fisica por Datos Basicos en IMSS",
		mensaje : " Consultando personas por Datos basicos....",
		procesar : paso3,
		url : context_path + sSource + "/consultar/imss/datosbasicos",
		hasError : false
	}

	],
	transiciones : [ {
		nombre : "Transicion al paso 2 cuando se encontraron candidats en el IMSS",
		mensaje : "",
		validar : transicionSiguienteValidar,
		seguir : transicionSeguir
	} ]
};

var flujoAlterno = {
	pasos : [

	{
		nombre : "Consulta de la persona fisica en la entidad RENAPO",
		mensaje : " Consultando la persona fisica en RENAPO....",
		procesar : fnPasoConsultarEnRenapo,
		url : context_path + sSource + "/consultar/renapo/curp",
		hasError : false
	}, {
		nombre : "Consulta de la persona fisica en la entidad SAT",
		mensaje : " Consultando la persona fisica en SAT....",
		procesar : fnPasoConsultarEnSat,
		url : context_path + sSource + "/consultar/sat/rfc",
		hasError : false
	} ]
};

