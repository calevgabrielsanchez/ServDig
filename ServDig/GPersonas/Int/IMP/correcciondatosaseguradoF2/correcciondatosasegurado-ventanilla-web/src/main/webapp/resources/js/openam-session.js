/**
 * Variables para el cntrol de la sesion
 */

var oDialogoCerrarSesion;
var idTimer;
var validaAviso;
var dialogoConfirmarMsg;

$(document).ready(function(){
	
	validaAviso = $("#validaAviso").val();
	idTimer = setInterval( validaSessionSSO, $('#intervaloValidacionSession').val());
	
	dialogoConfirmarMsg = $('<div class="modal fade" id="modalConfirmarSesion" tabindex="-1" role="dialog">  <div class="modal-dialog ">    <div class="modal-content">      <div class="modal-header">        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>        <h4 class="modal-title">Mensaje de sistema</h4>      </div><div class="modal-body"><div><div class=""><div class="row"><div class="col-md-12"><p class="container-fluid"><span>Te informamos que tu Sesi\u00f3n est\u00e1 por expirar, \u00BFDeseas continuar con tu sesi\u00F3n activa?</span></p></div></div></div><div class=""><div class="row"><div class="col-md-12"><div class="pull-right"><button onclick="cerrarDialogoSesion()" type="button" class="btn btn-default "> <span class="glyphicon glyphicon-undefined"> </span> No </button> <span> </span><button onclick="refrescarSesion()" type="button" class="btn btn-primary "> <span class="glyphicon glyphicon-undefined"> </span> Si </button><span> </span></div></div></div></div></div></div></div></div></div>');
	oDialogoCerrarSesion = $('<div class="modal fade" id="modalSalirSesion" tabindex="-1" role="dialog">  <div class="modal-dialog ">    <div class="modal-content">      <div class="modal-header">        <h4 class="modal-title">Mensaje de sistema</h4>      </div><div class="modal-body"><div><div class=""><div class="row"><div class="col-md-12"><p class="container-fluid"><span>Te informamos que tu sesi\u00f3n expir\u00f3, deber\u00e1s ingresar tu usuario y contrase\u00f1a nuevamente.</span></p></div></div></div><div class=""><div class="row"><div class="col-md-12"><div class="pull-right"><button onclick="salirSesion()" type="button" class="btn btn-primary "><span class="glyphicon glyphicon-undefined"></span>Aceptar</button><span> </span></div></div></div></div></div></div></div></div></div>');
	
	
});

function validaSessionSSO() {
	var fechaFinSession = new Date($("#fechaFinSession").val());
	var fechaAvisoSession =  new Date($("#fechaAvisoSession").val());
	
	if(fechaAvisoSession<= new Date() && validaAviso == "true"){
		 $.post(context_path + "/session/validar/session", null ,function(data) {
		 }, 'json').error(
    		function(data) {
    		}
		 );
		 //modal
		 dialogoConfirmarMsg.modal();
		 validaAviso = "false";
		return;
	}
	if(fechaFinSession <= new Date() && validaAviso == "false"){
		oDialogoCerrarSesion.modal({backdrop: "static"});
	}
	
}

function refrescarSesion(){
	//peticion ajax
	tokenid = getCookie("iPlanetDirectoryPro");
	refreshToken(tokenid);
	//cerrar dialogo al regresar
	dialogoConfirmarMsg.modal('hide');
}

function cerrarDialogoSesion(){
	dialogoConfirmarMsg.modal('hide');
}

function salirSesion(){
	document.forms["aux"].action = context_path + "/j_spring_security_logout";
	document.forms["aux"].submit();
}

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
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

function refreshToken(tokenid){
    
	url = "http://${mvn.url.openam}/openam_10.0.0/json/sessions?tokenId=" + tokenid + "&_action=validate";
	refresh = true;
	params = {'tokenid':tokenid , 'refresh':refresh};
	tSesion = 40;
	tNotificacion = 10;
	
	if (url !== null) {
	    $.ajax({		    
		    type : 'POST',
		    url :  url,
		    contentType: 'application/json; charset=utf-8',
		    crossDomain: true,
		    dataType: 'jsonp'
	    });
    }
	fechaActual = new Date();
	fechaAvisoSesion = new Date(fechaActual.getTime() + (tSesion - tNotificacion)*60000);
	fechaFinSesion = new Date(fechaActual.getTime() + tSesion*60000);
	$("#fechaAvisoSession").val(fechaAvisoSesion);
	$("#fechaFinSession").val(fechaFinSesion);
	validaAviso = "true";
}