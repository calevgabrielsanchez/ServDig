$(document).ready(function() {
 /*
	$('input.numerico').keypress(function(event) {
    	
		var charAt = String.fromCharCode(event.which);
		var characterReg = /\d{1}/;
        if(!characterReg.test(charAt)){
            return event.preventDefault();
        }
        
    });
	
	$('input:not(.numerico)').keypress(function(event) {
    	
		var charAt = String.fromCharCode(event.which);
		var characterReg = /[\w\u00d1\u00f1,\s\/]{1}/;
        if(!characterReg.test(charAt)){
            return event.preventDefault();
        }
        
    });
	
	$('input.rfc_moral').keypress(function(event) {
    	
		var charAt = String.fromCharCode(event.which);
		var characterReg = /[\w\&]{1}/;
        if(!characterReg.test(charAt)){
            return event.preventDefault();
        }
        
        // solucionar el puto & para no tener que hacer esto
        if(event.which == 38){
        	if($(this).val().length < 12){
        		$(this).val($(this).val() + "&");
        	}
        }
        
    });
	*/
	
	$('input').bind("cut copy paste", function(e) {
	      e.preventDefault();
	});
		
	/**
	 *  Funcion que convierte en MAYUSCULAS el valor de cualquier campo de texto al perder el foco 
	 */
	$('input[type="text"]').blur(function() {
		$(this).val($(this).val().toUpperCase());
	});
	
});


var pDialogHeigthPersonas = 550;
var pDialogWidthPersonas = 600;
var dataEmpty = {"sEcho":"undefined","iTotalRecords":0,"iTotalDisplayRecords":0,"sColumns":null,"aaData":[]};

function fnSetValues() {
	var iHeight = '800';
	var iWidth = '900';
	var sFeatures = "dialogwidth: " + iHeight + "px;";
	return sFeatures;
}

//function fnSetValues() {
//	var iHeight = '800';
//	var iWidth = '900';
//	var sFeatures = "dialogwidth: " + iHeight + "px;";
//	return sFeatures;
//	var fnValidaExisteSolicitudesEnProceso = function() {
//	};
//}

/**
 * FUncion para el procesamiento de errores cuando la peticion es asincrona
 * 
 * @param data
 * @param contenedor
 */
function fnProcesarErrores(data, contenedor) {

	switch (data.status) {
	case 403:
		// La sesion expiro
		window.location.reload(true);
		break;
	case 412:
		// Existen errores de captura
		fnProcesarErroresDeCaptura(data, contenedor);
		break;
	case 500:
		// Existen errores de negocio
		fnProcesarErrorNegocio(data, contenedor);
		break;

	}
}

/**
 * Funcion para mostrar los errores de captura (campos invalidos o vacios)
 * cuando es una invocacion asincrona y response con JSON.
 * 
 * @param data
 * @param contenedor
 */
function fnProcesarErroresDeCaptura(data, contenedor) {
	var objErrores = jQuery.parseJSON(data.responseText);

	for (var index = 0; index < objErrores.erroresCaptura.length; index++) {
		var campo = objErrores.erroresCaptura[index].campo;
		
		//Si el nombre del campo contiene puntos debemos de escaparlos
		campo = campo.replace(/\./g, "\\.");
		
		var mensaje = objErrores.erroresCaptura[index].mensaje;
		
		var filtroCampoError = contenedor + ' #' + campo + 'Error';
		
		fnShowError(filtroCampoError, mensaje);
	}

}

/**
 * 
 * @param data
 * @param contenedor
 * @param campo
 */
function fnProcesarErrorNegocio(data, contenedor) {

	var campo = 'errorNegocio';

	var objError = jQuery.parseJSON(data.responseText);
	var mensaje = objError.erroresNegocio;
	var filtroCampoError = contenedor + ' #' + campo + 'Label';

	fnShowError(filtroCampoError, mensaje);

}

/**
 * 
 * @param mensaje
 * @param contenedor
 */
function fnSetErrorNegocio(mensaje, contenedor) {
	var campo = 'errorNegocio';
	var filtroCampoError = contenedor + ' #' + campo + 'Label';
	fnShowError(filtroCampoError, mensaje);

}

var nClassShow = 'showElement';
var nClassHidden = 'hiddenElement';

/**
 * Funcion para mostrar el error
 * 
 * @param idCampoError
 * @param mensajeError
 */
function fnShowError(idCampoError, mensajeError) {
	$(idCampoError).removeClass(nClassHidden);
	$(idCampoError).addClass(nClassShow);
	$(idCampoError).text(mensajeError);
}

/**
 * Funcion para ocultar los errores de un contenedor. span con clase error
 * 
 * @param contenedor
 */
function fnHideErrores(contenedor) {

	var filtroErrores;
	if (contenedor == "") {
		filtroErrores = 'span.error';
	} else {
		filtroErrores = contenedor + ' span.error';
	}

	$(filtroErrores).each(function(index) {

		$(this).removeClass(nClassShow);
		$(this).addClass(nClassHidden);
		$(this).text();
	});
}

/**
 * Funcion para mostrar el error
 * 
 * @param idCampoError
 * @param mensajeError
 */
function fnShowElement(element) {

	$(element).removeClass(nClassHidden);
	$(element).addClass(nClassShow);
}

/**
 * Funcion para ocultar los errores de un contenedor. span con clase error
 * 
 * @param contenedor
 */
function fnHideElement(element) {

	$(element).removeClass(nClassShow);
	$(element).addClass(nClassHidden);
}

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
		var t = this.type, tag = this.tagName.toLowerCase();
		if (t == 'text' || t == 'password' || tag == 'textarea')
			this.value = '';
		else if (t == 'checkbox' || t == 'radio')
			this.checked = false;
		else if (tag == 'select') {
			this.selectedIndex = 0;
		}
	});
};

function limpiarFormulario(idForm) {

	
	//Borramos los errores
	fnHideErrores(idForm);
	
	$(idForm).clearForm();
};

/* Get the rows which are currently selected */
function fnGetRowSelected(oTableLocal) {
	var obRowSelected = null;

	$(oTableLocal.fnSettings().aoData).each(function() {
		if ($(this.nTr).hasClass('row_selected')) {
			obRowSelected = this._aData;
		}
	});

	return obRowSelected;

};

/* FUncion que valida que se encuentre seleccionado al menos un radio */

var fnValidaRegistroSeleccionado = function(oTableLocal) {

	var obRowSelected = fnGetRowSelected(oTableLocal);

	if (obRowSelected == null) {
		return false;
	} else {
		return true;
	}

};

function fnShowErrorBusiness() {
	fnShowElement('#errorBusiness');
}



function resetDisplayStart(oDatable){
	
    /*Se debe de reiniciar el contador del DIsplaySTart a 0*/
    var oSettings = oDatable.fnSettings();
    oSettings._iDisplayStart = 0;
    
}

function checkDate(fecha) {
	var arrFecha = fecha.split("/");

	if (arrFecha.length == 3) {
		var day = parseInt(arrFecha[0]);
		if (arrFecha[0].charAt(0) == '0') {
			day = parseInt(arrFecha[0].substr(1,1));
		}

		var month = parseInt(arrFecha[1]) - 1;
		if (arrFecha[1].charAt(0) == '0') {
			month = parseInt(arrFecha[1].substr(1,1)) - 1;
		}

		var year = parseInt(arrFecha[2]);
		var d = new Date(year, month, day);
		
		var dateDay = d.getDate();
		var dateMonth = d.getMonth();
		var dateYear = d.getFullYear();

		return dateDay === day && dateMonth === month && dateYear === year;
	} else {
		return false;
	}
}

//Función para ajustar en automático el tamaño del iframe
function set_size(elemento) {
	var rootElement;
	var maxHeight = 0;
	var incrementoHeight = 0;
	
	if ($.browser.msie) {
		rootElement = "div.site_position_center";
		
		if ($(rootElement).length <= 0) {
			rootElement = "div.site_position_center_fixed";
		}
		
		incrementoHeight = 60;
	} else {
		rootElement = "html";
		incrementoHeight = 20;
	}

	if ($("#" + elemento).contents().find("html").height() != 0) {
		
		maxHeight = $("#" + elemento).contents().find(rootElement).height();
		
		maxHeight += incrementoHeight;
		
		$("#" + elemento).css('height', maxHeight + 'px');
	}

}