/*!
 * Javascript con la configuracion externa necesaria para 
 * que funcione la gestion de un domicilio.
 * 

 * Author: Lucio Duran Silva.
 * Date: Martes 5 de Junio de 2012
 */

/**
 * Inicializacion de las variables
 */

var _strDialogoRegistro = "#dgRegistroDomicilio";
var _objDialogoRegistro;

var _strIdBotonRegistro = "#btnRegistroDomicilio";

$(document).ready(
		function() {
				
			
			//initGestionDomicilio();
			
			
			
});


var initGestionDomicilio = function(){
	
	/*
	 * Configuracion del boton de registro de nuevo 
	 * domicilio
	 */
	$(_strIdBotonRegistro).click(function(event){
		_objDialogoRegistro.dialog('open');
		
	});
	
	/*
	 * Configuracion del dialogo
	 */
	_objDialogoRegistro = $( _strDialogoRegistro ).dialog({
			height: 600,
			width: 1000,
			modal: true,
			autoOpen: false
		});	
		
	
}