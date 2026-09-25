


var objDialog;

/*Seccion de codigo a ejecutar en cuanto el
 * DOM envie la se�ar de que esta listo para procesar 
 * de modificaciones al DOM
 */
$(document).ready(function(){
	
	
	
	/*
	 * Funcion de submit de Iniciar solicitud
	 */
	
	$('form#formConfirm').submit(function(){
		
		//Presentamos el dialogo de confirmacion
		
		objDialog.dialog('open');
		return false;
	});
	
	
	
	/*
	 * Configuracion del dialogo
	 */
	objDialog = $( "#dgInicioSolicitud" ).dialog({
		resizable: false,
		modal: true,
		width: '80%',
		autoOpen:false,
		buttons:{
			"Cancelar": function() {
				$( this ).dialog( "close" );
			},
			"Aceptar": function() {
				$('form#formIniciar').submit();
				$( this ).dialog( "close" );
			}
		}
	});
	
});
