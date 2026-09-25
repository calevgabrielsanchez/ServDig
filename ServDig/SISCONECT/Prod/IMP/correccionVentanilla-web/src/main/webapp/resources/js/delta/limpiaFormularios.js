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