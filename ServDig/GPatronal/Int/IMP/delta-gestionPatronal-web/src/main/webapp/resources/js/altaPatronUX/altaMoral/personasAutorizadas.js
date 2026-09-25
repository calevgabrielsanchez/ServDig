PERSONAS_AUTORIZADAS = [];
PERSONA_ENCONTRADA = null;
	
$(document).ready(function() {
	//seteamos el evento para buscar a la persona con el rfc y curp
	$("#buscarPersona").on("click", buscaPersonaAutorizada);
	//se setea el evento al boton que crea la tabla de persona autorizada una vez que se confirma la bsuqueda
	$("#guardaPersonaAutorizada").on("click",anadirPersonaAutorizada);
	
	$("#guardarPersonasAutorizadas").on("click", guardarPersonasAutorizadas)
	//cancelar domicilio
	$("#cancelarPersonasAutorizadas").on("click",function(){paginaPrevia()});
	
	$("#form-busqueda #rfcPA").on("keypress", function() {
		limpiarDatosBusqueda();
	});
	//Validaciones para el formulario de busqueda
	initValidatorBusqueda();
	//iniciamos el validador del resultado
	initValidatorResultado();
});

var guardarPersonasAutorizadas = function() {
	
	solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.personasAutorizadas = new Array();
	$.each(PERSONAS_AUTORIZADAS, function(index, value){
		var personaAutorizada = new Object();
		value.documentosProbatorios = null;
		value.actaNacimiento=null;
		personaAutorizada.fisica = value;
		solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.personasAutorizadas.push(personaAutorizada);
		
	});
	paginaSiguiente();
	
};

/**
 * Metodo para iniciarlizar el validador del formulario de busqueda de persona autorizada
 */
var initValidatorBusqueda = function() {
	var $formBusqueda = $("#form-busqueda");
	$formBusqueda.validate($.extend({},DEFAULTS_VALIDATE,{
		verifyErrors: function(existError) {
			mostrarMensajeError(existError, "<strong>Error en el formulario!</strong> no ha llenado todos los campos requeridos. Por favor verifique");
			marcarAsteriscos($formBusqueda,".errorDocs","col-sm-4");
		},
		rules: {
			curp: {
				required: true,
				minlength: 18,
				maxlength: 18,
				curp: true
			},
			rfc: {
				required: true,
				minlength: 13,
				maxlength: 13,
				rfcFisica: true
			}
		}
	}));
}


/**
 * Metodo par inicializar el validador del formulario de resultado
 */
var initValidatorResultado = function() {
	$("#form-resultado").validate($.extend({},DEFAULTS_VALIDATE,{
		verifyErrors: function(existError) {
			mostrarMensajeError(existError,"<strong>Error en el formulario!</strong> no ha llenado todos los campos requeridos. Por favor verifique");
		},
		rules: {
			telefonoFijo: {
				maxlength: 15,
				number: true
			},
			telefonoMovil: {
				maxlength: 10,
				number: true
			},
			extension: {
				maxlength: 4,
				number: true
			},
			correo: {
				email: true
			}
		}
	}));
}

var limpiarDatosBusqueda = function() {
	var $formResultado = $("#form-resultado");
	var $formBusqPerAut = $("#form-busqueda");
	$formBusqPerAut.find("#curpPA").val("");
	$formResultado.hide();
	limpiarFormulario();
	mostrarMensajeError(false);
	PERSONA_ENCONTRADA = null;
}

/**
 * MEtodo que busca a la persona en base al curp y el rfc proporcionados
 */
var buscaPersonaAutorizada = function() {
	PERSONA_ENCONTRADA = null;
	var $formResultado = $("#form-resultado");
	var $formBusqPerAut = $("#form-busqueda");
	$formResultado.hide();
	var validator = $formResultado.validate();
	validator.resetForm();
	limpiarFormulario();
	mostrarMensajeError(false);
	
	if($formBusqPerAut.valid()) {
		var personaBusqueda = $formBusqPerAut.toObject();
		$formBusqPerAut.find("#rfc").val(personaBusqueda.rfc.toUpperCase());
		personaBusqueda.rfc = personaBusqueda.rfc.toUpperCase();
		if(!personaExistenteEnLista(personaBusqueda)) {
			var urlConsulta = context_path + "/alta/get/personaAutorizada";
			$.blockUI();
			$.postJSON(urlConsulta,personaBusqueda, function(data) {
				if(data.mensajeError == undefined || data.mensajeError == null) {
					PERSONA_ENCONTRADA = data.personaEncontrada;
					setPersonaLocalizada(PERSONA_ENCONTRADA);
				} else {
					mostrarMensajeError(true,data.mensajeError);
				}
				
				$.unblockUI();
			});
		} else {
			mostrarMensajeError(true,"La persona autorizada, ya se encuentra registrada.");
		}
	}
}

/**
 * Metodo para validar si el rfc o curp ya se encuentra dentro de
 */
var personaExistenteEnLista  =function(personaBusqueda) {
	var existePersona = false;
	//si la lista no es vacia busqcamos a la persona en el arrat
	if(PERSONAS_AUTORIZADAS.length > 0) {
		$.each(PERSONAS_AUTORIZADAS, function(index, value) {
			if(value.rfc.toUpperCase() == personaBusqueda.rfc.toUpperCase()) {
				existePersona =  true;
				//al usar each esto quiere decir que cortamos el ciclo, no que la funcion personaExistenteEnLista retornara false
				return false;
			}
		});
	}
	
	return existePersona;
};

/**
 * Si se presiona el boton de aceptar compoementaremos a la persona obtenida con los numeros de telefono
 * y el correo
 */
var anadirPersonaAutorizada = function() {
	
	//cacheamos el formulario
	$formBusqueda = $("#form-resultado");
	//si no existen errores de captura anadimos a la persona autorizada a las tablas
	if($formBusqueda.valid()) {
		var correo = $formBusqueda.find("#correo").val(); 
		if(!isEmpty(correo)){ 
			PERSONA_ENCONTRADA.correoElectronico=new Object();
			PERSONA_ENCONTRADA.correoElectronico.correo=correo;
		}
		//agregamos el relefono fijo al objeto persona que buscamos
		var telefonoFijo = $formBusqueda.find("#telefonoFijo").val();
		if(!isEmpty(telefonoFijo)){
			PERSONA_ENCONTRADA.telefonoFijo=new Object();
			PERSONA_ENCONTRADA.telefonoFijo.numero=telefonoFijo;
			PERSONA_ENCONTRADA.telefonoFijo.extension=$formBusqueda.find("#extension").val();
		}	
		
		var telefonoMovil = $formBusqueda.find("#telefonoMovil").val();
		if(!isEmpty(telefonoMovil)){
			PERSONA_ENCONTRADA.telefonoMovil=new Object();
			PERSONA_ENCONTRADA.telefonoMovil.numero=telefonoMovil;
		}
		
		addPersonasAutorizada(PERSONA_ENCONTRADA);
		PERSONA_ENCONTRADA = null;
		limpiarFormularioBusqueda();
		limpiarFormulario();
		$("#form-resultado").hide();
	}
}

/**
 * Pintamos a la persona autorizada en el formulario para que se capturen sus datos de contacto
 */
var setPersonaLocalizada = function(personaLocalizada) {
	//limpiamos el formulario
	limpiarFormulario();
	//cacheamos el formulario para solo buscar los elementos dentro de el
	var $formPersonaAut = $("#form-resultado");
	//seteamos los datos de la persona
	$formPersonaAut.find("#nombre").val(personaLocalizada.nombre);
	$formPersonaAut.find("#primerApellido").val(personaLocalizada.primerApellido);
	$formPersonaAut.find("#segundoApellido").val(personaLocalizada.segundoApellido);
	//verificamos si existe la informacion del telefono fiscal
	if(existeElemento(personaLocalizada.telefonoFijoFiscal)) {
		$formPersonaAut.find("#telefonoFijo").val(personaLocalizada.telefonoFijoFiscal.numero);
	} else {
		$formPersonaAut.find("#telefonoFijo").val("");
	}
	//verificamos si existe la informacion de telefono movil
	if(existeElemento(personaLocalizada.telefonoMovilFiscal)) {
		$formPersonaAut.find("#telefonoMovil").val(personaLocalizada.telefonoMovilFiscal.numero);
	} else {
		$formPersonaAut.find("#telefonoMovil").val("");
	}
	//Verificamos si existe el correo para mostrarlo en el formulario
	if(existeElemento(personaLocalizada.correoElectronicoFiscal)) {
		$formPersonaAut.find("#correo").val(personaLocalizada.correoElectronicoFiscal.correo);
	} else {
		$formPersonaAut.find("#correo").val("");
	}
	//mostramos el formulario en pantalla
	$formPersonaAut.show();
};

/**
 * funcion para limpiar el formulario de resultados de la persona
 */
var limpiarFormulario = function() {
	limpiarForm("form-resultado");
}

/**
 * Metodo para limpiar el formulario donde se captura el curp y el rfc de la persona autorizada
 */
var limpiarFormularioBusqueda = function() {
	limpiarForm("form-busqueda");
}

/**
*Metodo para agregar a la persona autorizada a la lista de persona
**/
var addPersonasAutorizada = function(personaAutorizada) {
	//agregamos a la persona en el array
	PERSONAS_AUTORIZADAS.push(personaAutorizada);
	//pintamos a las personas
	pintarPersonasAutorizadas();

}

/**
 * Metodo para crear las tablas de personas autorizadas dependiendo del contenido del 
 * array de personas
 */
var pintarPersonasAutorizadas = function() {
	var $br = $("<br>");
	$divPersonasAutorizadas = $("#divPersonasAutorizadas");
	$divPersonasAutorizadas.html("");
	//por cada persona autorizada en el array
	$.each(PERSONAS_AUTORIZADAS, function(index, value){
		//ponemos el titulo de la persona que es
		var $tituloPA = $("<h4>Persona autorizada "+(index+1)+"</h4>");
		//creamos la tabla
		var $registro = crearTablaPersonaAutorizada(value,index);
		//agregamos el contenido que acabamos de crear en el div
		$divPersonasAutorizadas.append($tituloPA).append($registro).append($br);			
	});
	//ponemos en el titulo de persona autorizada el siguiente numero
	$("#numeroPersonaAut").html(""+(PERSONAS_AUTORIZADAS.length+1));
	//si el numero de personas ya es tres ocultamos el formulario de busqueda
	if(PERSONAS_AUTORIZADAS.length == 3) {
		$("#form-busqueda").hide();
	} else {
		//Si el numero de personas es menor a 3 mostramos e formulario de busqueda de persona
		$("#form-busqueda").show();
	}
};

/**
 * Metodo para crear la tabla con la informacion de la persona, regresa un objeto de tabla de Jquery
 * que la tenemos que concatenar al div donde se pinten las personas autorizadas
 */
var crearTablaPersonaAutorizada = function(personaAutorizada,index) {
	var $tablaPersona = $("<table class=\"table table-striped table-bordered\"></table>");
	var $filaTitulos1 = $("<tr>").append($("<th>Nombre(s)</th>")).append($("<th>Primer apellido</th>")).append($("<th>Segundo apellido</th>")).append($("<th>Tel&eacute;fono m&oacute;vil</th>"))
	//anadimos la primera fila que contiene los titulos a la tabla
	$tablaPersona.append($filaTitulos1);
	//Creamos la fila donde se mostraran los datos de la persona
	var $fila1=$("<tr>");
	var $celdaNombre=$("<td>"+personaAutorizada.nombre+"</td>");
	var $celdaPA=$("<td>"+personaAutorizada.primerApellido+"</td>");
	var $celdaSA=$("<td>"+personaAutorizada.segundoApellido+"</td>");
	var movil = personaAutorizada.telefonoMovil
	var $celdaTM=$("<td>"+(movil != null ? movil.numero : "" )+"</td>");
	$fila1.append($celdaNombre).append($celdaPA).append($celdaSA).append($celdaTM);
	$tablaPersona.append($fila1);
	
	var $filaTitulos2 = $("<tr>").append($("<th>Tel&eacute;fono fijo</th>")).append($("<th>Extension</th>")).append($("<th>Correo electr&oacute;nico</th>")).append($("<th>Eliminar persona autorizada</th>"))
	$tablaPersona.append($filaTitulos2);
	//ponemos el contenido de la segunda fila
	var $fila2 = $("<tr>");
	var fijo = personaAutorizada.telefonoFijo;
	var $celdaTF=$("<td>"+(fijo != null ? fijo.numero: "")+"</td>");
	var $celdaEx=$("<td>"+(fijo != null ? fijo.extension: "")+"</td>");
	var correoE = personaAutorizada.correoElectronico;
	var $celdaCorreo=$("<td>"+(correoE != null ? correoE.correo: "")+"</td>");
	var $celdaElim=$("<td><button type=\"button\" class=\"btn btn-sm btn-danger\"  onClick=\"javaScript:quitarPersonaAutorizada("+index+")\"><span class=\"glyphicon glyphicon-trash\" aria-hidden=\"true\"></span> Eliminar</button></td>");
	$fila2.append($celdaTF).append($celdaEx).append($celdaCorreo).append($celdaElim);
	$tablaPersona.append($fila2);
	//Retornamos la tabla de la persona
	return $tablaPersona;
}

/**
 * Metodo para quitar a una persona autorizada de pantall
 */
var quitarPersonaAutorizada = function(index) {
	//Quitamos a la persona del array
	PERSONAS_AUTORIZADAS.splice(index,1);
	//pintamos las tablas de nuevo
	pintarPersonasAutorizadas();
}

var mostrarMensajeError = function(mostrar,mensaje) {
	if(mostrar) {
		$("#errorFormBusquedaPA").html(mensaje).show();
	} else {
		$("#errorFormBusquedaPA").html("").hide();
	}
}