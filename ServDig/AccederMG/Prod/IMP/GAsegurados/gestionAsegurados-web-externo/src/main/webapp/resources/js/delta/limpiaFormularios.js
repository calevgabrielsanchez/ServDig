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



//Funcion para volver readOnly los campos de texto de una forma (form)


$.fn.makeFormReadOnly = function(){
	return this.each(function(){
		$('input,select', this).makeReadOnly();
	})
}

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
}

// 191807 041212
// Estas funciones hacen algo similar a las de arriba, solo que aqui aplica el atributo "disabled" (porque el readonly no aplica para selects)
$.fn.deshabilitarContenido = function(){
	return this.each(function(){
		$('input, select, textarea', this).deshabilitarElemento();
	});
}

$.fn.deshabilitarElemento = function(){
	return this.each(function(){
		var type = this.type;
		var tag = this.tagName.toLowerCase();
		
		if(type == 'text' || type == 'password' || tag == 'textarea'){
			if(this.value != ''){
				$(this).attr('disabled', 'disabled');
			}else if(tag == 'select'){
				$(this).attr('disabled', 'disabled');
			}
		}
		
	});
}

// 191807 041212
// Estas funciones hacen lo opuesto a las de arriba, es decir, quitan el atributo "disabled" de los elementos justo antes de hacer un submit para que
// puedan viajar hacia el controller
$.fn.habilitarContenido = function(){
	return this.each(function(){
		$('input, select, textarea', this).habilitarElemento();
	});
}

$.fn.habilitarElemento = function(){
	return this.each(function(){
		var type = this.type;
		var tag = this.tagName.toLowerCase();
		
		if(type == 'text' || type == 'password' || tag == 'textarea'){
			if(this.value != ''){
				$(this).removeAttr('disabled');
			}else if(tag == 'select'){
				$(this).removeAttr('disabled');
			}
		}
		
	});
}