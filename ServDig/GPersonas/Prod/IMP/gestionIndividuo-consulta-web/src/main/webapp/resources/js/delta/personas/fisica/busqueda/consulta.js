

$(document).ready(function(){
	
	
	
	// Configuracion del boton de cancelar...
	
	
	$("#cancelar").click(function( event ){
		var ctrl = parent.PersonaFisicaCtrl;
		if(ctrl){
			ctrl.cerrar();
		}else{
			window.close();
		}
	});
	
	//Configuracion del boton de limpiar ..
	$("#limpiar").click(function(event){
		
		limpiarFormulario("#forma");
		
	});
	
	/*Configuracion de la fecha*/
	$("#busquedaFechaNacimiento").datepicker({
		showOn : 'both',
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : '-112:+0'
	});
	$("#registroFechaNacimiento").datepicker($.datepicker.regional['es']);
	
	
});