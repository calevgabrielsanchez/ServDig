/*!
 * general.js
 * 
 * Archivo javascript que debera contener las funciones que son
 * generales a los largo de todo el sistema o modulo.
 * 
 */

var idDialogoCerrarSesion = "#dgCerrarSesion";
var idDialogoBuzonTributario = "#dgBuzonTributario";
var oDialogoCerrarSesion;
var oDialogoBuzonTributario;

/*Seccion de codigo a ejecutar en cuanto el
 * DOM envie la senial de que esta listo para procesar 
 * de modificaciones al DOM
 */
$(document).ready(function() {

	/*Configuracion de la funcion de submit de la forma del
	 * registro de nuevo usuario*/

	$('#formRegistroUsuarioNuevo').submit(function() {
		fnOpenRegistroNuevoUsuario();
		return false;
	});

	/*Inicializacion del dialogo
	 * de confirmacion de cerrar sesion*/
	oDialogoCerrarSesion = $(idDialogoCerrarSesion).dialog({
		autoOpen : false,
		resizable : false,
		height : 'auto',
		modal : true,
		buttons : {
			'Cancelar' : function() {
				$(this).dialog("close");
			},
			"Aceptar" : function(data) {
				$("#formCerrarSesion").submit();
			}
		}
	});

    /*Inicializacion del dialogo
     * de buzon tributario*/
    oDialogoBuzonTributario = $(idDialogoBuzonTributario).dialog({
        autoOpen : false,
        resizable : false,
        height : 'auto',
        modal : true,
        buttons : {
            'Cancelar' : function() {
                $(this).dialog("close");
            },
            "Aceptar" : function(data) {
                if(($("#rfcPersonaCtrl").val())!=null){
                    var rfcPersona = $("#rfcPersonaCtrl").val();
                }else if(($("#hdnRfcPersonaRep").val())!=null){
                    var rfcPersona = $("#hdnRfcPersonaRep").val();
                } else{
                    var rfcPersona = $("#rfcWS").val();
                }

                var razonSocial = $("#razonSocial").val();
                openInNewTab('${mvn.url.buzon.tributario}?rfc='+rfcPersona+'&razonSocial='+razonSocial+'&appOrigen=IMSSDigital');
                $(this).dialog("close");
            }
        }
    });

	inicializarValidacionesCaracteresEspeciales();
	
	if ($('form#registroAseguradoDatosBasicosForm').length > 0 
			&& $('form#registroAseguradoDatosBasicosForm').closest('div.page_holder_custom').find('div.ui-widget:first').length > 0) {
		$("form#registroAseguradoDatosBasicosForm :input").change(function(event) {
			if (typeof event.originalEvent !== 'undefined') {
		    	$('form#registroAseguradoDatosBasicosForm').closest('div.page_holder_custom').find('div.ui-widget:first p').html('<strong>Los resultados de la b&uacute;squeda han sido eliminados, ya que la informaci&oacute;n para realizar la b&uacute;squeda ha cambiado</strong>');
		    }
			
		});
	}

	//Programacion del boton de ayuda rapida
	$('#ayuda-rapida').bind('click', function(){
		$('#tutorial_contenedor').toggle();
		$('#img-tutorial').toggle();
		$('#close-ayuda-bar').toggle();
		
		$('.widget-resize').each(function() {
			var icon = $('i', this);
			var css_present = $(icon).attr('class');
			var iconresizesmall = "icono-cerrar";
			
			if (css_present == iconresizesmall) {
				$(this).trigger('click');
			}
		});
	});
	
	
	//Programacion del boton para cerrar la ayuda rapida
	
	$('#close-ayuda').bind('click', function(){
		$('#tutorial_contenedor').toggle();
		$('#img-tutorial').toggle();
		$('#close-ayuda-bar').toggle();
	});

	
	/*
	 * Codigo de Google analytics para pagina de IMSS externa.
	 */
	
	  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){

		  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),

		  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)

		  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');



		  ga('create', 'UA-47165435-1', 'imss.gob.mx');

		  ga('send', 'pageview');

});

/*Funcion para abrir el dialogo cerrar sesion*/
var fnAbrirDialogoCerrarSesion = function() {
	oDialogoCerrarSesion.dialog('open');
};

/*Funcion para abrir el dialogo buzon tributario*/
var fnAbrirDialogoBuzonTributario = function(msjBuzon, razonSocial, rfc) {
    $("#msjBuzon").html(msjBuzon);
    $("#razonSocial").val(razonSocial);
    $("#rfcWS").val(rfc);
    oDialogoBuzonTributario.dialog('open');
};

/*Funcion que abre la ventana alterna
 * para el registro de usuario*/
var fnOpenRegistroNuevoUsuario = function() {
	var oSendData = new Object();
	var sUrl = "/gestionIndividuo-web/persona/fisica/registro/1/";
	window.showModalDialog(sUrl,oSendData,"dialogWidth:1000px;dialogHeight:900px;status=yes,toolbar=no,menubar=no,location=no");
};

/**
 * Funcion para limpiar los campos de busqueda
 */
$.fn.clearForm = function() {
	return this.each(function() {
		$('input,select,textarea', this).clearFields();
	});
};

/**
 * Clears the selected form elements.
 */
$.fn.clearFields = $.fn.clearInputs = function() {
	return this.each(function() {
		if ($(this).attr('disabled') != 'disabled') {
			var t = this.type, tag = this.tagName.toLowerCase();
			if (t == 'text' || t == 'password' || tag == 'textarea')
				this.value = '';
			else if (t == 'checkbox' || t == 'radio')
				this.checked = false;
			else if (tag == 'select') {
				this.selectedIndex = 0;
			}
		}
	});
};

function limpiarFormulario(idForm) {

	$(idForm).clearForm();
};

function set_size(elemento, maxSize) {

	var element = $("#" + elemento);

	setSizeCommon(element, maxSize);
}

function setSizeWithinIframe(document, maxSize, minSize) {
	var w = document.defaultView || document.parentWindow;
	var frames = w.parent.document.getElementsByTagName('iframe');

	for ( var i = frames.length; i-- > 0;) {
		var frame = frames[i];
		try {
			var d = frame.contentDocument || frame.contentWindow.document;
			if (d === document) {
				setSizeCommon(frame, maxSize, minSize);
				break;
			}
		} catch (e) {
			//alert(e);
		}
	}
}

/* Funcion para ajustar en automatico el tamanio del iframe
 * de acuerdo a su contenido recibe el id del iframe.
 */
function setSizeCommon(elemento, maxSize, minSize) {
	var rootElement;
	var maxHeight = 0;
	var incrementoHeight = 0;
	var maxSizeDefault = 900;
	var maxSizeToApply = 0;

	if ($.browser.msie) {
		rootElement = "body";
		incrementoHeight = 60;
	} else {
		rootElement = "html";
		incrementoHeight = 20;
	}

	if ($(elemento).contents().find("html").height() != 0) {

		maxHeight = $(elemento).contents().find(rootElement).height();

		maxHeight += incrementoHeight;

		if (maxSize != undefined) {
			if (maxSize > maxSizeDefault) {
				maxSizeToApply = maxSizeDefault;
			} else {
				maxSizeToApply = maxSize;
			}
		} else {
			maxSizeToApply = maxSizeDefault;
		}

		if (maxHeight > maxSizeToApply) {
			maxHeight = maxSizeToApply;
		}
		
		if (minSize != 'undefined' && minSize > maxHeight) {
			maxHeight = minSize;
		}

		$(elemento).css('height', maxHeight + 'px');
	}
}

function isTextSelected(input) {
	var startPos = input.selectionStart;
	var endPos = input.selectionEnd;
	var doc = document.selection;

	if (doc && doc.createRange().text.length != 0) {
		return true;
	} else if (!doc && input.value.substring(startPos, endPos).length != 0) {
		return true;
	}
	return false;
}

function inicializarValidacionesCaracteresEspeciales() {

	// Vocales acentuadas y con di�resis, n�meros y enie (may�sculas y min�sculas)
	var commonCharWhiteList = '\u00E1\u00E9\u00ED\u00F3\u00FA\u00C1\u00C9\u00CD\u00D3\u00DA\u00E4\u00EB\u00EF\u00F6\u00FC\u00C4\u00CB' 
		+ '\u00CF\u00D6\u00DC\u00F1\u00D10123456789';
	var noUnicodeCharsWhitelist = '!@#$%&*()+=\';,/{}":?.-_';
	var noUnicodeCharsWhitelistAfiliacion = commonCharWhiteList + '&,.\'()-';
	var noUnicodeCharsWhitelistClasificacion = commonCharWhiteList + '&,.\'()-';
	var noUnicodeCharsSemiEstricto = '&';

	$("input.numerico").numeric();

	$("input.numericoSinPunto").numeric({
		allowMinus : false,
		allowThouSep : false,
		allowDecSep : false
	});
	
	$("input.numericoPositivo2Decimales").numeric({
		allowMinus : false,
		allowThouSep : false,
		maxDecimalPlaces: 2
	});
	

	$("input.alfanumerico").alphanum({
		allow : noUnicodeCharsWhitelist
	});
	
	// S�lo permite letras (sin acentos, ni di�resis) y &
	$("input.alfanumericoNSS").alphanum({
		allow : '&\'-.',
		allowCaseless : false,
		allowOtherCharSets : false,
		allowLatin : true,
		allowSpace : true
	});

	//solo permite letras, numeros (caracteres /.-)
	$("input.caracterEsp").alphanum({
		allow : '/-.',
		allowSpace : true,
		allowOtherCharSets : false
	});
	

	//solo permite letras, numeros (caracteres @-._)
	$("input.correoElectronico").alphanum({
		allow : '@-._',
		allowSpace : false,
		allowLower: true,
		allowUpper: true,
		allowOtherCharSets : false
		
	});
	
	//solo permite letras, numeros (caracteres &,./'())
	$("input.caracterNotaria").alphanum({
		allow : '&,./()\' - ',
		allowSpace : true,
		allowOtherCharSets : false
	});
	
	//solo permite letras numeros (Caracteres &,./()\' - ' �,�)
	
	$("input.nombreComercial").alphanum({
		allow : '\u00C1\u00E1\u00E9\u00C9\u00ED\u00CD\u00D3\u00F3\u00DA\u00FA &,./()\' - ',
		allowSpace : true,
		allowOtherCharSets : false
	});


	//solo permite numeros (caracter *)
	$("input.asterisco").alphanum({
		allow : '\u002A 0-9',
		allowCaseless : true,
		allowOtherCharSets : false,
		allowLatin : false,
		allowSpace : false,
	});
	

	//solo permite letra �
	$("input.autoridad").alpha({
		allow : '\u00F1\u00D1',
		allowSpace : true,
		allowOtherCharSets : false
	});

	$("textarea.alfanumerico").alphanum({
		allow : noUnicodeCharsWhitelist
	});

	$("input.textAfiliacion").alphanum({
		allow : noUnicodeCharsWhitelistAfiliacion,
		allowCaseless : false,
		allowOtherCharSets : false
	});

	$("textarea.textAfiliacion").alphanum({
		allow : noUnicodeCharsWhitelistAfiliacion,
		allowCaseless : false,
		allowOtherCharSets : false
	});

	$("input.textClasificacion").alphanum({
		allow : noUnicodeCharsWhitelistClasificacion,
		allowCaseless : false,
		allowOtherCharSets : false
	});

	$("textarea.textClasificacion").alphanum({
		allow : noUnicodeCharsWhitelistClasificacion,
		allowCaseless : false,
		allowOtherCharSets : false
	});

	/*
	 * S�lo acepta n�meros y letras, no acpeta: acentos, espacios, carateres
	 * especiales, como los casos de CURP y RFC (Persona Fisica)
	 */
	$("input.alfanumericoEstricto").alphanum({
		allowSpace : false,
		allowOtherCharSets : false
	});

	/*
	 * Solo acepta numeros, letras y algunos caracteres especiales permitidos como &
	 * , no acpeta: acentos, espacios, carateres
	 * especiales, como los casos de RFC (Persona Moral)
	 */
	$("input.alfanumericoSemiEstricto").alphanum({
		allowSpace : false,
		allow : noUnicodeCharsSemiEstricto,
		allowOtherCharSets : false
	});

	$(".sinAcentos").alpha({
		allowOtherCharSets : false
	});
}

function marcarCamposConErrores(form,selectorError,selectorPadre) {
	var tieneError = false;
	
	$("#"+form).find(""+selectorError).each(function() {
		var existeError = $(this).is(":visible");
		tieneError = tieneError || existeError;
		var cssBor = existeError ? "1px solid red" : "1px solid #ccc";
		var cssSpan = existeError ? "red" : "black";
		
		var $padre = $(this).parent(""+selectorPadre);
		
		$padre.find(":text").each(function() {
			$(this).css("border",cssBor);
			$('label[for="'+this.name+ '"]').find("span").css("color",cssSpan);
			$('label[for="'+this.id+ '"]').find("span").css("color",cssSpan);
		})
		
		$padre.find("select").each(function() {
			$(this).css("border",cssBor);
			$('label[for="'+this.name+ '"]').find("span").css("color",cssSpan);
			$('label[for="'+this.id+ '"]').find("span").css("color",cssSpan);
		})
		
		$padre.find("textarea").each(function() {
			$(this).css("border",cssBor);
			$('label[for="'+this.name+ '"]').find("span").css("color",cssSpan);
			$('label[for="'+this.id+ '"]').find("span").css("color",cssSpan);
		})
	});
	
	pintarErrorGeneral(tieneError);
}

function marcarCamposConErroresFromSpanErrors(form,selectorError) {
	
	var tieneError = false;
	
	$("#"+form).find(""+selectorError).each(function() {
		var $spanError = $(this);
		var existeError = $spanError.is(":visible");
		tieneError = existeError || tieneError;
		var cssBorder = existeError ? "1px solid red" : "1px solid #ccc";
		var cssColor = existeError ? "red" : "black";
		
		//si existe error se buscaran los campos y el requerido
		var idSpanRequired = $spanError.attr("spanRequired");
		var idCampoError = $spanError.attr("campoRelacionado");

		//se obtiene asi para evitar escapar lso caracteres
		var requiredRelacionado = document.getElementById(idSpanRequired);
		var campoRelacionado = document.getElementById(idCampoError);

		//si el campo existe se marcara en rojo
		if(campoRelacionado != undefined && campoRelacionado != null) {
			campoRelacionado.style.border = cssBorder;
		}

		if(requiredRelacionado != undefined && requiredRelacionado != null) {
			requiredRelacionado.style.color = cssColor;
		}
	});
	
	pintarErrorGeneral(tieneError);
}

function mostrarMensajeError($campo, error , mensaje) {
	var cssDisplay = error ? "block" : "none";
	var cssBorder = error ? "1px solid red" : "1px solid #ccc";
	var cssColor = error ? "red" : "black";
	
	if($campo != undefined) {
		var idCampo = $campo.attr("id");
		
		
		var tipoElemento = $campo.prop("tagName").toLowerCase();
		var idSpanRequired = $campo.attr("spanRequired");
		var idSpanError = $campo.attr("spanError");
		//console.log("el id del campo es: " + idCampo + " y es un " + tipoElemento + " su spanRequerido es: " + idSpanRequired + " su spanError es: " + idSpanError);
		var valorCampo = $campo.val();
		
		if(tipoElemento != "table") {
			//ponemos el campo en rojo
			$campo.css("border",cssBorder);
		}
		//se obtiene asi para evitar escapar 
		var spanError = document.getElementById(idCampo+"Error");
		var campoRequired = document.getElementById(idCampo + "Req");
		
		if(spanError != undefined && spanError != null) {
			//console.log("se encontro el span de error y su id es: " + spanError.id );
			spanError.style.display = cssDisplay;
			
			if((mensaje != undefined || mensaje != null) && error) {
				spanError.innerHTML = mensaje;
			} else {
				spanError.innerHTML = "";
			}
		}
		
		if(campoRequired != undefined && campoRequired != null) {
			//console.log("se encontro el campo required y su id es: " + campoRequired.id);
			campoRequired.style.color = cssColor;
		}
		
	}
}

function pintarErrorGeneral(tieneError){
	var cssColor = tieneError ? "red" : "black";
	var cssDisplay = tieneError ? "block":"none";
	var labelGeneral = document.getElementById("labelCamposObligatoriosGeneral");
	var divErrores = document.getElementById("divErrorCampos");
	
	if(labelGeneral != undefined && labelGeneral != null) {
		//labelGeneral.style.color = cssColor;
	}
	
	if(divErrores != undefined && divErrores != null) {
		divErrores.style.display = cssDisplay;
		divErrores.innerHTML = "<strong>&iexcl;Error en el formulario!</strong> No has llenado todos los campos requeridos. Por favor verifica."
	}
}

function setEventosChecarError(idFormulario,functionPintarErrores,cadenaConcatenar) {
	$("#"+idFormulario+" :text").change(function() {
		verificarPersistenciaDeError(this, false,functionPintarErrores,cadenaConcatenar);
	});
	
	$("#"+idFormulario+" select").change(function() {
		verificarPersistenciaDeError(this, true,functionPintarErrores,cadenaConcatenar);
	});
	
	$("#"+idFormulario+" textarea").change(function() {
		verificarPersistenciaDeError(this, false,functionPintarErrores,cadenaConcatenar);
	});
}

function verificarPersistenciaDeError(element, isSelect, functionErrores,cadenaConcatenar) {
	var elementId = element.id;
    var elementName = element.name;
    var errorId = cadenaConcatenar != undefined && cadenaConcatenar != null  ? cadenaConcatenar : "Error";
    var idElementError= elementName+""+errorId;
    var elementoError = document.getElementById(idElementError);
    //verificamos si existe el mensaje de error
    if(elementoError != undefined) {
    	//obtenemos la referencia al elemento del error
    	var $elementoError = $(elementoError);
    	//verificamos si persiste el error
    	var errorSolucionado = isSelect ? element.selectedIndex > 0 : $.trim(element.value).length > 0;
    	//si el elemento es visible y ya no hay error ocultamos el mensaje, y corremos la validacion de errores
    	if($elementoError.is(":visible") && errorSolucionado) {
    		$elementoError.html("");
    		$elementoError.removeClass("showElement").addClass("hiddenElement");
    		//verificamos si ejecutamos la funcion
    		if(functionErrores != undefined && functionErrores != null && $.isFunction(functionErrores)) {
    			functionErrores();
			}
    	}
    }
}

function openInNewTab(url) {
    var win = window.open(url, '_blank');
    win.focus();
}