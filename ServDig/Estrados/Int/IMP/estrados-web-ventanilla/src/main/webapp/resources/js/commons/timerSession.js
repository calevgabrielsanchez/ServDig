var flagInactividad = false;
var tiempoMAXventana = 30000;

$(document).ready(function() {
//	setInterval(verificaTiempoSesion, 20000);
});

function verificaTiempoSesion() {
	if(flagInactividad){
		//esperando respuesta del dialogo
		return;
	}
	
	$.postJSON_Sync(getAppContextParaJS()+"/estrados/recuperaTiempos.do", null,function(data) {
		if(data.maxsesion == "false") {
			cerrarSession("Lo sentimos el tiempo m\u00e1ximo de sesi\u00f3n ha expirado, favor de ingresar nuevamente.");
		}
		if(data.inactividad == "false") {
			verificarInactividad();
		}
	}).error(function(data) {
		cerrarSession("Lo sentimos la sesi\u00f3n se ha perdi\u00f3, favor de ingresar nuevamente.");
	}).complete(function(data) {
		
	});
}

function cerrarSession(textMensaje) {
	var dialogo = $('#divMensajeSession').dialog({
      title: "Alerta",
      autoOpen: false,
      width : 400,
      height : 180,
      modal: true,
      resizable: false,
      overlay: {
          opacity: 0.5,
          background: "black"
      },
      buttons: [{
      		text:"Aceptar",
      		click:function() {
      			$(this).dialog("destroy");
      			logOut();
      		}
      }]
	});
	dialogo.html(textMensaje);
	dialogo.dialog("open");
}

function verificarInactividad(){
	var dialogo=$('#divMensajeSession').dialog({
      title: "Alerta",
      autoOpen: false,
      width : 400,
      height : 180,
      modal: true,
      resizable: false,
      overlay: {
          opacity: 0.5,
          background: "black"
      },
      buttons: [{
      		text:"SI",
      		click:function(){
      			$(this).dialog("destroy");
      			reactivaTiempo();
      		}
      },{
    		text:"NO",
    		click:function(){
    			$(this).dialog("destroy");
    			logOut();
    	}
    }]
	});
	dialogo.html("La sesi\u00f3n est\u00e1 a punto de expirar,\u00bfDesea continuar con la sesi\u00f3n actual?");
	dialogo.dialog("open");
	flagInactividad=true;	 
	setTimeout(verificabandera, tiempoMAXventana);
}

function reactivaTiempo(){
	$.postJSON_Sync(getAppContextParaJS()+"/estrados/reactivaTiempo.do", null,function(data) {	
		flagInactividad=false;	
	}).error(function(data){		
	}).complete(function(data){		
	});	
}

function logOut(){
	document.location.href =getAppContextParaJS()+'/j_spring_security_logout';
}

function verificabandera(){	
	if(flagInactividad){
		logOut();
	}
}

function bloquear() {
	$.blockUI({ message:  '<h2>Procesando...</h2>', css: {             
		border: 'none',
		padding: '15px',
		opacity: .5
	} });
}

function desbloquear() {
	$.unblockUI();
}