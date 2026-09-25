/* SRG 080312 */
$.fn.clearForm = function() {
	
  return this.each(function() {
	  
    var type = this.type, tag = this.tagName.toLowerCase();
    if (tag == 'form')
      return $(':input',this).clearForm();
    if (type == 'text' || type == 'password' || tag == 'textarea')
      this.value = '';
    else if (type == 'checkbox' || type == 'radio')
      this.checked = false;
    else if (tag == 'select')
      this.selectedIndex = 0;
    
    //Establecer por default la busqueda exacta al resetear el formulario
//    if(this.id == 'busquedaExacta'){
    	$('#busquedaExacta').attr('checked', 'checked');
//    }
    
  });
  
};

