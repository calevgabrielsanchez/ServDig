$(document).ready(function(){
	
	
	
	
	
	
	/*
	 * Colocamos el atributo disabled a los campos
	 * que no pueden ser modificados.
	 */
	$('input.disabled').each(function() {
		
		$(this).fadeTo('slow', 0.5);
		$(this).attr('readonly', 'readony');
		$(this).attr('disabled', 'disabled');
	});
	
	$('select').each(function() {
		
		$(this).fadeTo('slow', 0.5);
		$(this).attr('readonly', 'readony');
		$(this).attr('disabled', 'disabled');
	});
	
	
	
// Configuracion del boton de cancelar...
	
	
	$("#cancelar").click(function( event ){
		var ctrl = parent.PersonaFisicaCtrl;
		if(ctrl){
			ctrl.cerrar();
		}else{
			window.close();
		}
	});
	
	$("#regresar").click(function( event ){
		
		window.location = context_path+"/persona/fisica/ubicar/regresar";
	});
	
	
	
});