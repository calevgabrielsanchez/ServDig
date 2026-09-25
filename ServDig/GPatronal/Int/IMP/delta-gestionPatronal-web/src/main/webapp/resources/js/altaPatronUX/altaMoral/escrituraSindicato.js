INDEX_SELECCIONADO = -1;
SOCIOS = [];
SOCIO_ENCONTRADO = null;
SINDICATO = null;
ESCRITURA = null;

$(document).ready(function() {
	$("#selectable").selectable({
		selected : function(event, ui) {
			$(ui.selected).siblings().removeClass("ui-selected");
		},
		stop : function() {
			$(".ui-selected", this).each(function() {
				var indexSeleccionado = $("#selectable li").index(this);
				habilitarSeccionElegida(indexSeleccionado);
				mostrarMensajeErrorGenerico("contenedorSelects",false);
			});
		}
	});
	
	initValidateSindicato();
	initValidateEscritura();
	initValidateBusquedaSocio();
	
	$("#guardarEscritura").on("click", guardarDatosEscritura);
	$("#cancelarEscritura").on("click",function(){paginaPrevia()});
	$("#buscarSocio").on("click",buscarSocio);
	$("#anadirSocioFisica").on("click",aceptarSocio);
	$("#anadirSocioMoral").on("click",aceptarSocio);
	
	$("#formEscritura #fechaExpedicion").datepicker({
		changeMonth : true,
		changeYear : true,
		maxDate : 0,
		onClose : function(dateText, inst) {
			$("#formEscritura").validate().element("#fechaExpedicion");
		}
	});
	
	$("#formSindicato #fechaRegistro").datepicker({
		changeMonth : true,
		changeYear : true,
		maxDate : 0,
		onClose : function(dateText, inst) {
			$("#formSindicato").validate().element("#fechaRegistro");
		}
	});
});

/**
*
**/
var habilitarSeccionElegida = function(indexSeleccionado) {
	INDEX_SELECCIONADO = indexSeleccionado;
	if(indexSeleccionado == 0){
		$("#escritura").show();
		$("#sindicato").hide();
		limpiarForm("formSindicato");
	} else if(indexSeleccionado == 1){
		$("#sindicato").show();
		$("#escritura").hide();
        limpiarEscritura();
	} else {
		$("#sindicato").hide();
		$("#escritura").hide();
		limpiarForm("formSindicato");
        limpiarEscritura();
	}
}

var limpiarEscritura = function () {
    limpiarForm("formEscritura");
    if(SOCIOS.length > 0){
        SOCIOS = [];
        SOCIO_ENCONTRADO = null;
        pintarListadoSocios();
    }

    limpiarFormularioBusquedaSocios();
    limpiarFormulariosSocios();
}

/**
**/
var initValidateSindicato = function() {
	$("#formSindicato").validate($.extend({},DEFAULTS_VALIDATE, {
		verifyErrors: function(existError) {
			marcarAsteriscos($("#formSindicato"),".errorDocs","div");
			mostrarMensajeErrorGenerico("formSindicato",existError,MENSAJE_ERROR_FORM);
			
		},
		rules : {
			numReferenciadocRegistro: {required:true,maxlength: 20},
			fechaRegistro: {required:true},
			autoridadLaboral: {required:true,maxlength: 100}
		}
	}));
}

var initValidateEscritura = function() {
	$("#formEscritura").validate($.extend({},DEFAULTS_VALIDATE, {
		verifyErrors: function(existError) {
			marcarAsteriscos($("#formEscritura"),".errorDocs","div");
			mostrarMensajeErrorGenerico("formEscritura",existError,MENSAJE_ERROR_FORM);
			
		},
		rules : {
			numEscritura: {required:true,maxlength: 12},
			numNotaria: {required:true,maxlength: 15},
			"lugarExpedicion.entidadFederativa.clave": {min:0},
			"lugarExpedicion.clave": {min: 0},
			"fechaExpedicion":{required:true},
			folioMercantil: {maxlength: 13},
			seccion: {maxlength: 18},
			partida:{maxlength: 15},
			volumen:{maxlength: 15},
			foja:{maxlength: 15},
		},
		messages : {
			"lugarExpedicion.entidadFederativa.clave": {min : "Seleccione un estado v&aacute;lido."},
			"lugarExpedicion.clave": {min: "Seleccione un municipio v&aacute;lido."}
		}
	}));
}

var initValidateBusquedaSocio = function() {
	$("#formBusquedaSocio").validate($.extend({},DEFAULTS_VALIDATE, {
		verifyErrors: function(existError) {
			marcarAsteriscos($("#formBusquedaSocio"),".errorDocs","div");
			mostrarMensajeErrorGenerico("formBusquedaSocio",existError,MENSAJE_ERROR_FORM);
			
		},
		rules : {
			rfc: {required:true, minlength: 12, maxlength: 13, rfcFisOMoral: true},
			curp: {
				required:{
		        	depends: function(element) {
		          		return $("#formBusquedaSocio #rfc").val().length == 13;
		        	}
		      	}
			}
		}
	}));
}

/**
* Metodo que verifica que opcion esta seleccionada y que se tiene que validar
**/
var guardarDatosEscritura = function() {
	var valido = false;
	var $formEscritura = $("#formEscritura");
	var escritura  = null;
	var sindicato = null;
	
	var opcionSeleccionada = $("#selectable").find(".ui-selected").length != 0;
	
	if(!opcionSeleccionada) {
		mostrarMensajeErrorGenerico("contenedorSelects",true,"<strong>Debe seleccionar una opci&oacute;n</strong>. Debe seleccionar una opci&oacute;n");
		return;
	}
	
	if(INDEX_SELECCIONADO == 0) {
		valido = $("#formEscritura").valid();
		if($("#formBusquedaSocio").is(":visible")) {
			valido = valido && $("#formBusquedaSocio").valid();
		}

		if(valido){
			if(SOCIOS.length > 0){
				valido = true;
			}else{
				valido = false;
			}
			mostrarMensajeErrorGenerico("formBusquedaSocio",!valido,"Debe registrar al menos un socio");
		}
		
		escritura = $formEscritura.toObject();
		var nombreEstado = $formEscritura.find("#lugarExpedicion\\.entidadFederativa\\.clave option:selected").html()
		escritura.lugarExpedicion.nombre = nombreEstado;
		sindicato = null;
	} else if(INDEX_SELECCIONADO == 1) {
		valido = $("#formSindicato").valid();
		sindicato = $("#formSindicato").toObject();
		escritura = null;
	} else {
		valido = true;
	}
	
	if(valido) {
		if(escritura != null) {
			solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.registroSindicato = null;
			solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.escrituraConstitutiva = escritura;
			solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.socios = SOCIOS;
		} else if(sindicato != null) {
			solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.registroSindicato = sindicato;
			solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.escrituraConstitutiva = null;
		} else {
			solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.registroSindicato = null;
			solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.escrituraConstitutiva = null;
		}
		paginaSiguiente()
		visualizaPrevio();
	}
}

var buscarSocio = function() {
	var $formSocio = $("#formBusquedaSocio");
	var formValido = $formSocio.valid();
	
	var urlConsulta = context_path + "/wizard/tramite/socios/getSocio";
	
	if(formValido) {
		var personaMoral = personaMoralAP;
		var datosBusqueda = $formSocio.toObject();
		var isFisica = $formSocio.find("#rfc").val().length == 13;
		datosBusqueda.tipoSocio = new Object();
		datosBusqueda.tipoSocio.idTipoPersona = isFisica ? 1 : 2;
		datosBusqueda.idPersonaMoralPatron = personaMoral.idPersona;
		datosBusqueda.rfcPersonaMoralPatron= personaMoral.rfc;
		
		if(!socioEnLista(datosBusqueda)) {
			
			$.postJSON(urlConsulta, datosBusqueda, function(data) {
				if(data.error) {
					mostrarMensajeErrorGenerico("formBusquedaSocio",true,data.error);
				} else {
					mostrarMensajeErrorSocios(false);
					SOCIO_ENCONTRADO = data.socio;
					setSocioLocalizado(data.socio);
				}
			})
			
		} else {
			mostrarMensajeErrorGenerico("formBusquedaSocio",true,"El socio ya se encuentra registrado.");
		}
	}
}



var mostrarMensajeErrorSocios = function(mostrar,mensaje) {
	if(mostrar) {
		$("#errorFormBusquedaSocio").html(mensaje).show();
	} else {
		$("#errorFormBusquedaSocio").html("").hide();
	}
}

/**
 * Metodo para validar si el rfc o curp ya se encuentra dentro de
 */
var socioEnLista  =function(socio) {
	var existePersona = false;
	//si la lista no es vacia busqcamos a la persona en el arrat
	if(SOCIOS.length > 0) {
		$.each(SOCIOS, function(index, value) {
			if(value.rfc.toUpperCase() == socio.rfc.toUpperCase()) {
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
var aceptarSocio = function() {
	
	var isFisica = $("#formBusquedaSocio #rfc").val().length == 13;
	var idForm = isFisica ? "formResultadoFisica" : "formResultadoMoral";
	//cacheamos el formulario
	var $formResultado = $("#"+idForm);
	
	if(SOCIO_ENCONTRADO.personaFisica != null) {
		SOCIO_ENCONTRADO.personaFisica.documentosProbatorios = null;
		SOCIO_ENCONTRADO.personaFisica.actaNacimiento=null;
	} else {
		SOCIO_ENCONTRADO.personaMoral.fechaCreacion= null;
		SOCIO_ENCONTRADO.personaMoral.fechaRegistro= null;
		SOCIO_ENCONTRADO.personaMoral.fechaBaja=null;
		SOCIO_ENCONTRADO.personaMoral.fechaModificacion=null;
		SOCIO_ENCONTRADO.personaMoral.escrituraConstitutiva=null;
		SOCIO_ENCONTRADO.personaMoral.registroSindicato=null;
		SOCIO_ENCONTRADO.personaMoral.datosPersonaSAT=null;
	}
	limpiarFormularioBusquedaSocios();
	limpiarFormulariosSocios();
	addSocioLista(SOCIO_ENCONTRADO);
	SOCIO_ENCONTRADO = null;
	$formResultado.hide();
}


/**
*Metodo para agregar a la persona autorizada a la lista de persona
**/
var addSocioLista = function(socio) {
	//agregamos a la persona en el array
	SOCIOS.push(socio);
	//pintamos a las personas
	pintarListadoSocios();
}

/**
 * Metodo para crear las tablas de personas autorizadas dependiendo del contenido del 
 * array de personas
 */
var pintarListadoSocios = function() {
	var $br = $("<br>");
	var $divListadoSocios = $("#listadoSocios");
	$divListadoSocios.html("");
	//por cada persona autorizada en el array
	$.each(SOCIOS, function(index, value){
		//ponemos el titulo de la persona que es
		var $tituloPA = $("<h4>Socio "+(index+1)+"</h4>");
		//creamos la tabla
		var $registro = crearTablaPersonaSocio(value,index);
		//agregamos el contenido que acabamos de crear en el div
		$divListadoSocios.append($tituloPA).append($registro).append($br);			
	});
	//ponemos en el titulo de persona autorizada el siguiente numero
	$("#numeroSocioActual").html(""+(SOCIOS.length+1));
	//si el numero de personas ya es tres ocultamos el formulario de busqueda
	if(SOCIOS.length == 1) {
		$("#formBusquedaSocio").hide();
	} else {
		//Si el numero de personas es menor a 3 mostramos e formulario de busqueda de persona
		$("#formBusquedaSocio").show();
	}
};

/**
 * Metodo para crear la tabla con la informacion de la persona, regresa un objeto de tabla de Jquery
 * que la tenemos que concatenar al div donde se pinten las personas autorizadas
 */
var crearTablaPersonaSocio = function(socio,index) {
	var $tablaPersona = $("<table class=\"table table-striped table-bordered\"></table>");

	if(socio.tipoSocio.idTipoPersona == 1) {
		var $filaTitulos1 = $("<tr>").append($("<th>Nombre(s)</th>")).append($("<th>Primer apellido</th>")).append($("<th>Segundo apellido</th>")).append($("<th>Eliminar socio</th>"))
		//anadimos la primera fila que contiene los titulos a la tabla
		$tablaPersona.append($filaTitulos1);
		//Creamos la fila donde se mostraran los datos de la persona
		var $fila1=$("<tr>");
		var $celdaNombre=$("<td>"+socio.personaFisica.nombre+"</td>");
		var $celdaPA=$("<td>"+socio.personaFisica.primerApellido+"</td>");
		var $celdaSA=$("<td>"+socio.personaFisica.segundoApellido+"</td>");
		var $celdaElim=$("<td><button type=\"button\" class=\"btn btn-sm btn-danger\"  onClick=\"javaScript:quitarSocio("+index+")\"><span class=\"glyphicon glyphicon-trash\" aria-hidden=\"true\"></span> Eliminar</button></td>");
		$fila1.append($celdaNombre).append($celdaPA).append($celdaSA).append($celdaElim);
		$tablaPersona.append($fila1);

	} else {
		var $filaTitulos1 = $("<tr>").append($("<th>Nombre o raz&oacute;n social</th>")).append($("<th>Tipo sociedad</th>")).append($("<th>Eliminar socio</th>"));
		//anadimos la primera fila que contiene los titulos a la tabla
		$tablaPersona.append($filaTitulos1);
		//ponemos el contenido de la segunda fila
		var $fila2 = $("<tr>");
		var $celdaTF=$("<td>"+(socio.personaMoral.razonSocial?socio.personaMoral.razonSocial:"")+"</td>");
		var $celdaEx=$("<td>"+(socio.personaMoral.tipoSociedad.descripcion?socio.personaMoral.tipoSociedad.descripcion:"")+"</td>");
		var $celdaElim=$("<td><button type=\"button\" class=\"btn btn-sm btn-danger\"  onClick=\"javaScript:quitarSocio("+index+")\"><span class=\"glyphicon glyphicon-trash\" aria-hidden=\"true\"></span> Eliminar</button></td>");
		$fila2.append($celdaTF).append($celdaEx).append($celdaElim);
		$tablaPersona.append($fila2);
	}
	//Retornamos la tabla de la persona
	return $tablaPersona;
}

var quitarSocio = function(index) {
	//Quitamos a la persona del array
	SOCIOS.splice(index,1);
	//pintamos las tablas de nuevo
	pintarListadoSocios();
}

/**
 * Pintamos a la persona autorizada en el formulario para que se capturen sus datos de contacto
 */
var setSocioLocalizado = function(socioActual) {
	//limpiamos el formulario
	limpiarFormulariosSocios();
	//verificamos el tipo de persona
	var isFisica = $("#formBusquedaSocio #rfc").val().length == 13;
	//verificamos el formulario que usaremos
	var idFormMostrar = isFisica ? "formResultadoFisica" : "formResultadoMoral";
	//
	var idFormOcultar = isFisica ? "formResultadoMoral" : "formResultadoFisica";
	//cacheamos el formulario para solo buscar los elementos dentro de el
	var $formResutaldoBusqueda = $("#"+idFormMostrar);
	//
	var personaLocalizada = isFisica  ? socioActual.personaFisica : socioActual.personaMoral;
	if(isFisica) {
		//seteamos los datos de la persona
		$formResutaldoBusqueda.find("#nombre").val(personaLocalizada.nombre);
		$formResutaldoBusqueda.find("#primerApellido").val(personaLocalizada.primerApellido);
		$formResutaldoBusqueda.find("#segundoApellido").val(personaLocalizada.segundoApellido);
	} else {
		$formResutaldoBusqueda.find("#razonSocial").val(personaLocalizada.razonSocial);
		$formResutaldoBusqueda.find("#tipoSociedad\\.descripcion").val(personaLocalizada.tipoSociedad.descripcion);
	}
	//mostramos el formulario en pantalla
	$formResutaldoBusqueda.show();
	$("#"+idFormOcultar).hide();
};

var limpiarFormulariosSocios = function() {
	limpiarForm("formResultadoFisica");
	limpiarForm("formResultadoMoral");

}

var limpiarFormularioBusquedaSocios = function(){
	limpiarForm("formBusquedaSocio");
}

var pedirSoloSocios = function(){
	$('#selectEscrituraSindicato').hide();
	$('#escritura').show();
	$('#divFormEscritura').hide();
	$('#sindicato').hide();
}
