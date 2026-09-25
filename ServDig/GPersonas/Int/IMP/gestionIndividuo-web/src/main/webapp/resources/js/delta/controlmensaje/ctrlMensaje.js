/**
 *  JS para el control de mensajes por medio de dialogos de confirmacion.
 * 
 **/


/*==========================*/
/* Variables de ids de html */
/*==========================*/

var idDialogoExito ='#dialogoMensajeOperacionExitosa';
var idTextMnsjExito ='#dialogoMensajeOperacionExitosa span#mensaje';


/*==========================*/
/* objetos de los digalogs*/
/*==========================*/

// variable para el control del dialogo de exito
var oDialogoExito;
//variable para el mensaje de exito.
var oMensajeExito;


$(function() {
	/*configuracion inicial*/
	fnConfigInitMensajes();
});




/* 
 * Funcion de configuracion inicial de los mensajes
 * 
 */
function fnConfigInitMensajes(){
	/*inicializamos el dialogo de exito*/
	fnConfigDialogoExito();
}


/*
 * Funcion para inicializar la configuracion del dialogo de 
 * mensaje de exito. 
 */
function fnConfigDialogoExito(){
	oDialogoExito = $(idDialogoExito).dialog({
		autoOpen:false,
		resizable: false,
		modal: true,	
		buttons: {
			Aceptar: function() {
				$( this ).dialog( "close" );
			}
		}
	});
}


function fnOpenDialogoExito(){
	oDialogoExito.dialog('open');
}