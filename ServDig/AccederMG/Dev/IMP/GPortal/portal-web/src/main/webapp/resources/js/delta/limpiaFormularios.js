$.fn.clearForm = function(clearDisabledReadOnly) {
	return this.each(function() {
		$('input,select,textarea', this).clearFields(clearDisabledReadOnly);
	});
};

/**
 * Clears the selected form elements.
 */
$.fn.clearFields = $.fn.clearInputs = function(clearDisabledReadOnly) {
	return this.each(function() {
		var isReadOnlyDisabled = false;
		
		if($(this).attr("readonly") == true || $(this).attr("readonly") == 'readonly') {
			isReadOnlyDisabled = true;
		} else if($(this).attr("disabled") == true || $(this).attr("disabled") == 'disabled') {
			isReadOnlyDisabled = true;
		}

		if (isReadOnlyDisabled) {
			if (typeof clearDisabledReadOnly != 'undefined'
					&& clearDisabledReadOnly == true) {
				clearElement(this);
			}
		} else {
			clearElement(this);
		}
	});
};

function clearElement(element) {
	var t = element.type, tag = element.tagName.toLowerCase();

	if (t == 'text' || t == 'password' || tag == 'textarea')
		element.value = '';
	else if (t == 'checkbox' || t == 'radio')
		element.checked = false;
	else if (tag == 'select') {
		element.selectedIndex = 0;
	}
};

/**
 * Función que limpia el formulario que se recibe (id). Por default no limpia
 * los elementos disabled/readonly, en caso de que se necesite que los limpia se
 * debe mandar true como segundo parámetro
 */
function limpiarFormulario(idForm, clearDisabledReadOnly) {
	$(idForm).clearForm(clearDisabledReadOnly);
};

// Aplica el atributo "readonly" a los elementos de un formulario
$.fn.makeFormReadOnly = function(){
	return this.each(function(){
		$('input,select', this).makeReadOnly();
	});
};

$.fn.makeReadOnly= function(){
	return this.each(function(){
		var t = this.type, tag = this.tagName.toLowerCase();
        if (t == 'text' || t == 'password' || tag == 'textarea'){
        	if( this.value != ''){
        		$(this).attr('readonly' , 'readony');
        		$(this).fadeTo('slow', 0.5);
        	}
        }
        else if (tag == 'select'){
                if( this.selectedIndex != 0){
                	$(this).attr('readonly' , 'readony');
                	$(this).fadeTo('slow', 0.5);
                }
        }
	});
};

/* 
 * Aplica el atributo "disabled" a los elementos de un formulario,
 * recibe bandera para indicar si se quiere aplicar el disabled
 * sólo a los elementos que tengan algún valor
 */
$.fn.deshabilitarContenido = function(soloConDato){
	return this.each(function(){
		$('input, select, textarea', this).deshabilitarElemento(soloConDato);
	});
};

$.fn.deshabilitarElemento = function(soloConDato){
	return this.each(function(){
		var type = this.type;
		var tag = this.tagName.toLowerCase();
		
		if(type == 'text' || type == 'password' || tag == 'textarea'){
			if (soloConDato) {
				if(this.value != ''){
					$(this).attr('disabled', 'disabled');
				}
			} else {
				$(this).attr('disabled', 'disabled');
			}
		} else if(tag == 'select'){
			$(this).attr('disabled', 'disabled');
		}
		
	});
};


// Quita el atributo "disabled" de los elementos de un formulario
$.fn.habilitarContenido = function(soloConDato){
	return this.each(function(){
		$('input, select, textarea', this).habilitarElemento(soloConDato);
	});
};

$.fn.habilitarElemento = function(soloConDato){
	return this.each(function(){
		var type = this.type;
		var tag = this.tagName.toLowerCase();
		
		if(type == 'text' || type == 'password' || tag == 'textarea'){
			if (soloConDato) {
				if(this.value != ''){
					$(this).removeAttr('disabled');
				}
			} else {
				$(this).removeAttr('disabled');
			}
		} else if(tag == 'select'){
			$(this).removeAttr('disabled');
		}
		
	});
};