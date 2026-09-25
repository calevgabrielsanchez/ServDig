

/*Seccion de codigo a ejecutar en cuanto el
 * DOM envie la se�ar de que esta listo para procesar 
 * de modificaciones al DOM
 */



var idDialogoCerrarSesion = "#dgCerrarSesion";
var oDialogoCerrarSesion;
var idTimer;
var validaAviso;
var dialogoConfirmarMsg;


$(document).ready(function(){
	
	
	  validaAviso =$("#validaAviso").val();
	  idTimer = setInterval( validaSessionSSO, 10000);
	
	  dialogoConfirmarMsg = $( "#dialog-Aviso-Session" ).dialog({
			resizable: false,
			height:200,
			modal: true,
			autoOpen: false,
			buttons: {
				"ACEPTAR": function() {
					//parent.registroUsuarioDatosBasicosWizard.cerrar();
			 	}
			 }
		 });
});

/*
function validaSessionSSO() {
	var fechaFinSession = new Date($("#fechaFinSession").val());
	var fechaAvisoSession =  new Date($("#fechaAvisoSession").val());
	var intervaloValidacionSession = $("#intervaloValidacionSession").val();
	
	//alert("llamada" + validaAviso);	
	if(fechaAvisoSession<= new Date() && validaAviso == "true"){
		//alert("Importante Concluira su session en 5 minutos ");
		 $.postJSON(context_path + "/busqueda/validar/session", null ,function(data) {
    	}).error(
    		function(data) {
    		}
    	);
		//mostrarMensaje("Estimado usuario la sesi\u00f3n  concluir\u00e1 en 5 minutos, trasncurrido este tiempo el sistema lo redireccionar\u00e1 a la p\u00e1gina de acceso");
		 mostrarMensaje("Te informamos que tu sesi\u00f3n est\u00e1 por expirar, y deber\u00e1s ingresar tu usuario y contrase\u00f1a nuevamente");
		 validaAviso = "false";
		return;
	}
	if(fechaFinSession <= new Date() && validaAviso == "false"){
		//clearTimeout(idTimer);//alert("Estimado usuario la sesión expirado el sistema lo redireccionara a la pagina de acceso ");
		mostrarMensajeFinSession("Te informamos que tu sesi\u00f3n expir\u00f3, deber\u00e1s ingresar tu usuario y contrase\u00f1a nuevamente");
		
		
	}
	
}
*/

function validaSessionSSO() {
	var fechaFinSession = new Date($("#fechaFinSession").val());
	var fechaAvisoSession =  new Date($("#fechaAvisoSession").val());
	var intervaloValidacionSession = $("#intervaloValidacionSession").val();
	
	//alert("llamada" + validaAviso);	
	if(new Date()>=  fechaFinSession){
		//clearTimeout(idTimer);//alert("Estimado usuario la sesión expirado el sistema lo redireccionara a la pagina de acceso ");
		mostrarMensajeFinSession("Te informamos que tu sesi\u00f3n expir\u00f3, deber\u00e1s ingresar tu usuario y contrase\u00f1a nuevamente");
		 validaAviso = "false";
		return;
	}
	
	if(new Date() >= fechaAvisoSession  && validaAviso == "true"){
		//mostrarMensaje("Estimado usuario la sesi\u00f3n  concluir\u00e1 en 5 minutos, trasncurrido este tiempo el sistema lo redireccionar\u00e1 a la p\u00e1gina de acceso");
		 mostrarMensaje("Te informamos que tu sesi\u00f3n est\u00e1 por expirar, y deber\u00e1s ingresar tu usuario y contrase\u00f1a nuevamente");
		return;
	}
}

	function marcaAvisoSession(){
		 $.postJSON(context_path + "/busqueda/validar/session", null ,function(data) {
	 	}).error(
	 		function(data) {
	 		}
	 	);
		 validaAviso = "false";
		
	}
	
	
/*
function mostrarMensaje(mensaje) {
	dialogoConfirmarMsg.dialog("option", "buttons", [ {
		text : 'Continuar',
		click : function() {
			$(this).dialog('close');
		}
	}]);
	
	$("#mensajeDialogoSession").html(mensaje);
	dialogoConfirmarMsg.dialog('open');
}

*/
	
	function mostrarMensaje(mensaje) {
		dialogoConfirmarMsg.dialog("option", "buttons", [ 
			{
			text : 'Revalidar Sesi\u00f3n',
			click : function() {
				
				refreshToken();
				$(this).dialog('close');
				}
			},
			{
				text : 'Aceptar',
				click : function() {
					
					marcaAvisoSession();
					$(this).dialog('close');
					}
			}
		]);
		
		$("#mensajeDialogoSession").html(mensaje);
		dialogoConfirmarMsg.dialog('open');
	}

	/*
function mostrarMensajeFinSession(mensaje) {
	dialogoConfirmarMsg.dialog("option", "buttons", [ {
		text : 'Continuar',
		click : function() {
			$.postJSON(context_path + "/busqueda/limpiarSesion",{},function(data) {
	    		$("#formCerrarSesion").submit();
	    	}).error(
	    		function(data) {
	    			$("#formCerrarSesion").submit();
	    		}
	    	);
			$(this).dialog('close');
		}
	}]);
	
	$("#mensajeDialogoSession").html(mensaje);
	dialogoConfirmarMsg.dialog('open');
}
*/
	
	function mostrarMensajeFinSession(mensaje) {
		dialogoConfirmarMsg.dialog("option", "buttons", [ {
			text : 'Continuar',
			click : function() {
				$.postJSON(context_path + "/busqueda/limpiarSesion",{},function(data) {
		    		$("#formCerrarSesion").submit();
		    	}).error(
		    		function(data) {
		    			$("#formCerrarSesion").submit();
		    		}
		    	);
				$(this).dialog('close');
			}
		}]);
		
		$("#mensajeDialogoSession").html(mensaje);
		dialogoConfirmarMsg.dialog('open');
	}
	

	function refreshToken(){
		
		tokenid = getCookie("iPlanetDirectoryPro");
		url = '/openam_10.0.0/identity/isTokenValid';
		refresh = true;
		params = {'tokenid':tokenid , 'refresh':refresh};
		tSesion = 3;
		tNotificacion = 1;
		
		if (url !== null) {
		    $.ajax({
			    data : params,
			    type : 'GET',
			    url : 'http://ssoimssdigd.imss.gob.mx:8001' + url,
			    contentType: 'application/json; charset=utf-8',
			    crossDomain: true,
			    dataType: 'jsonp'
		    });
	    }
		
		var fechaFinSession = new Date($("#fechaFinSession").val());
		var fechaAvisoSession =  new Date($("#fechaAvisoSession").val());
		var intervaloValidacionSession = $("#intervaloValidacionSession").val();
		
		fechaActual = new Date();
		fechaAvisoSesion = new Date(fechaActual.getTime() + (tSesion - tNotificacion)*60000);
		
		fechaFinSesion = new Date(fechaActual.getTime() + tSesion*60000);
		validaAviso = "true";
		$("#fechaAvisoSession").val(fechaAvisoSesion);
		$("#fechaFinSession").val(fechaFinSesion);
		$("#validaAviso").val(validaAviso);
		 $.postJSON(context_path + "/busqueda/revalidar/session", null ,function(data) {
		 	}).error(
		 		function(data) {
		 		}
		 	);
		
		//alert("sali de la llamada");
	}


	function getCookie(cname) {
	    var name = cname + "=";
	    var decodedCookie = decodeURIComponent(document.cookie);
	    var ca = decodedCookie.split(';');
	    for(var i = 0; i <ca.length; i++) {
	        var c = ca[i];
	        while (c.charAt(0) == ' ') {
	            c = c.substring(1);
	        }
	        if (c.indexOf(name) == 0) {
	            return c.substring(name.length, c.length);
	        }
	    }
	    return "";
	}