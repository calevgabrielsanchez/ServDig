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
            else if (tag == 'select'){
                    this.selectedIndex = 0;
            }
    });
};

function limpiarFormulario(idForm){
    
    $(idForm).clearForm();
};

//Quita el atributo "disabled" de los elementos de un formulario
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