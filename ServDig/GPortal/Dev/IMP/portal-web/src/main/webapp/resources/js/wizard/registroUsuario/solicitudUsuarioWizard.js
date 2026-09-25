/*
 * JS de control del Widget de Persona Fisica.
 */

//$.getScript("/gestionIndividuo-consulta-web/static/resources/js/wizard/fisica/registro-usuario/registroUsuarioWizard.js");

var dialogMensajes;
var dialogResultadoRegistro;
var dialogMensajesOpinion;

$(document).ready(function(){
	dialogMensajes = $( "div#dialog-mensajes" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		buttons: {
		 	"ACEPTAR": function() {
		 		$( this ).dialog( "close" );
		 	}
		 }
	 });
	
	dialogResultadoRegistro = $( "div#dialog-mensajesInicio" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false
	 });
	
	dialogResultadoRegistroBienvenido = $( "div#dialog-mensajesInicioBienvenido" ).dialog({
		resizable: false,
		height:'auto',
		width: '750px',
		modal: true,
		autoOpen: false
	 });
	
	 dialogMensajesOpinion = $( "div#dialog-mensajes-opinion" ).dialog({
	 		resizable: false,
	 		height:'auto',
			width: '550px',
	 		modal: true,
	 		autoOpen: false,
	 		buttons: {
	 		 	"ACEPTAR": function() {
	 		 		$( this ).dialog( "close" );
	 		 	}
	 		 }
	 	 });

	
});


var registroUsuarioDatosBasicosWizard = {
		registrar : function() {
			
			var idPersona = '';
			var rfcPersona ='';
			var curpPersona = '';
			WizardRegistroUsuarioCtrl.init('wizardRegistroUsuario', 1, idPersona,curpPersona, rfcPersona);
			WizardRegistroUsuarioCtrl.setOnCloseCallback(mostrarResultadoRegistro);
			WizardRegistroUsuarioCtrl.abrir();
		}
	};

$("#registrarUsuario").live('click', function() {
	registroUsuarioDatosBasicosWizard.registrar();
});


function mostrarResultadoRegistro(){
	var datosSalida = WizardRegistroUsuarioCtrl.datosSalida;
	if(datosSalida != null){
	if(datosSalida.usuario == null) {
		//En caso de error
		$("#mensajeDialogoInicio").html(datosSalida.mensaje);
		dialogResultadoRegistro.dialog("option", "buttons", [ {
			text : 'ACEPTAR',
			click : function() {
				$(this).dialog('close');
			}
		}]);
		
		dialogResultadoRegistro.dialog('open');
	} else {
		//En caso de exito
		dialogResultadoRegistroBienvenido.dialog("option", "buttons", [ {
			text : 'ACEPTAR',
			click : function() {
//				parent.AuthenticateSSO.authenticate(datosSalida.usuario.curp,datosSalida.usuario.serial);
//				parent.AuthenticateSSO.setOnCloseCallback(function(){
//					$("#formlogin").submit();
//				});
				$(this).dialog('close');
			}
		}]);
		dialogResultadoRegistroBienvenido.dialog('open');
	}
	}
}

function mostrarMsgOpinion(){
	dialogMensajesOpinion.dialog('open');
}
	

