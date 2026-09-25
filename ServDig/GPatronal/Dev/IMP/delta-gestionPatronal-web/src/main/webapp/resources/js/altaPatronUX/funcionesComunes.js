/**
 * 
 */

/**
 * Opciones por default de los validators para modificar los estilos ya que la clase
 * errorDocs cuando se le pone a un campo lo marca en rojo, e internamente, el validate
 * pone esa clase tanto a los span como a los campos
 */
DEFAULTS_VALIDATE = {
	errorClass: "errorDocs",
	errorElement: "span"
}

MENSAJE_ERROR_FORM = "<strong>Error en el formulario!</strong> no ha llenado todos los campos requeridos. Por favor verifique";
/**
 * Funcion para marcar los asteriscos en rojo
 */
var marcarAsteriscos = function($formulario,selectorError,selectorPadre) {
	$($formulario).find(selectorError).each(function() {
		var existeError = $(this).is(":visible") || ($(this).attr("type") == "hidden");
		var cssSpan = existeError ? "red" : "black";
		
		var $padre = $(this).parent(selectorPadre);
		
		$padre.find(":text").each(function() {
			setColorAsteriscoLabel(this,cssSpan);
		});
		
		$padre.find("select").each(function() {
			setColorAsteriscoLabel(this,cssSpan);
		});
		
		$padre.find("textArea").each(function() {
			setColorAsteriscoLabel(this,cssSpan);
		})

        $padre.find("input[type='hidden']").each(function() {
            setColorAsteriscoLabel(this,cssSpan);
        })
	});
}

/**
 * Metodo para limpiar solo los campos de texto de un formulario
 */
var limpiarForm = function(idFormulario) {
	$("#"+idFormulario).find(":text").each(function() {
		limpiarElemento(this);
	});
	
	$("#"+idFormulario).find("select").each(function() {
		limpiarElemento(this);
	});
	
	$("#"+idFormulario).find("textarea").each(function() {
		limpiarElemento(this);
	});
	
	$("#"+idFormulario).find(".alert-danger").each(function() {
		$(this).hide();
	});
	
	//verificamos si el formulario tiene el plugin de validacion
	var validator = $("#"+idFormulario).data('validator');
	//si existe el validador reseteamos el formulario
	if(validator) {
		validator.resetForm();
	}
};

/**
 * Metodo para validar si existe un elemento
 */
var existeElemento = function(elemento) {
	if(elemento != undefined && elemento != null) {
		return true;
	} 
	return false;
}


/**
 * Metodo para validar si una cadena es vacia
 */
var isEmpty = function(valor) {
	return $.trim(valor).length == 0;
}

/**
*Funcion para marcar los pasos, de acuero al parametro se marcan todos los pasos anteriores
* y el actual, y se desmarcan los demas
**/
var pasoActualTramite = function(paso) {
	//le restamos uno para que empaque con los indices
	var pasoActual = paso - 1;
	//recorremos los li y ponemos complete a los menores al numero que pasamos
	$("#pasosTramite li").each(function(index) {
		if(index <= pasoActual) {
			$(this).addClass("completed");
		} else {
			$(this).removeClass("completed");
		}
	});
}

var crearDialogo = function(mensaje, buttons, titulo, width, hight) {
	
	var width1 = width ? width : "300px";
	var hight1 = hight ? hight : "auto";
	
	var $divMensajes = $("<div></div>");
	$divMensajes.dialog({
		resizable: false,
		width: width1,
		height:hight1,
		modal: true,
		title: titulo,
		autoOpen: false,
		closeOnEscape: false,
		buttons: buttons
	});

	$divMensajes.html(mensaje);
	$divMensajes.dialog('open');
}


var limpiarElemento = function(elementJavaScript) {
	//convertimos el objeto javascript en un objeto de JQUEry
	$elemento = $(elementJavaScript);
	//obtenemos el tipo de elemento sobre el que se esta realizando el limpiarl
	var tipoElemento = $elemento.prop("tagName").toLowerCase();
	//Verificamos el tipo de elemento para ponerle el default
	if(tipoElemento == "select") {
		$elemento.val("-1");
	} else {
		$elemento.val("");
	}
	//quitamos el color del asterisco
	setColorAsteriscoLabel(elementJavaScript,"black")
	//quitamos la clase que marca en rojo el campo
	$elemento.removeClass("errorDocs");
}

var setColorAsteriscoLabel = function(elementJS, color) {
	if($('label[for="'+elementJS.name+ '"]')){
		$('label[for="'+elementJS.name+ '"]').find("span").css("color",color);
	} else {
		$('label[for="'+elementJS.id+ '"]').find("span").css("color",color);
	}
}

var setColorAsteriscoPage = function(existError) {
	var cssSpanPage = existError?"red":"black";

    $('.errorPage:visible').each(function(){
        $(this).find("span").css("color",cssSpanPage);
    });
}

/**
 * funcion que muestra el mensaje (param mensaje) dentro del div error
 * que se encuentra en el formulario con id -idFormulario- en caso de no existir el div
 * lo creara dentro del form y lo pondra como primer elemento
 */
var mostrarMensajeErrorGenerico = function(idFormulario, mostrar, mensaje) {
	var existeDivError = false;
	$formulario = $("#"+ idFormulario);
	//buscamos el div de error dentro del formulario
	$formulario.find(".alert-danger").each(function() {
		//en caso de que se encuentre
		var $mensajeError = $(this);
		//lo mostramos
		if(mostrar) {
			$mensajeError.html(mensaje);
			$mensajeError.show()
		} else {
			$mensajeError.html("");
			$mensajeError.hide()
		}
		//marcamos la bandera en true indicando que el formulario si cuenta con el div de error
		existeDivError = true;
	});
	//en caso de que no exista el div de error y se requiera mostrar el mensaje
	if(!existeDivError && mostrar) {
		//creamos un div con el mensaje de error
		var $divErrorNuevo = $("<div class=\"alert alert-danger\">"+mensaje+"</div>");
		//lo ponemos como primer elemento dentro del formulario
		$formulario.prepend($divErrorNuevo);
	}
}