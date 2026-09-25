/**
 * 
 */
var solicitudPrincipal;
var checarSolicitud = null;
var solicitudCreada = null;
var personaMoralAP = null;
var requiereActaSindicato = false;
var requiereSocios = false;
var isSAS = false;
var reintentosFinalizar = 0;

$(document).ready(function(){

	$.postJSON(getContext()+ '/alta/patron/recuperaEstructuraAltaPatronal.do', null, function(data) {
		solicitudPrincipal=data;
	}).error(function(data){
		solicitudPrincipal=undefined;
	});	

});


function finalizarSolicitud(){
	$.blockUI();
	$("#divProcesandoSolicitud").show();
	var ajax_request = new XMLHttpRequest();
	ajax_request.open( "POST", context_path + "/alta/finalizarAltaPatronal", true );
	ajax_request.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
	ajax_request.onreadystatechange = function() {
		if (ajax_request.readyState == 4 && ajax_request.status == 200) {
			solicitudCreada = (JSON.parse(ajax_request.responseText)).solicitud;
			$("#folioProcesando").html(solicitudCreada.noFolioSolicitud);
			verificarFinalizacionSolicitud();
			checarSolicitud = setInterval(verificarFinalizacionSolicitud,15000);
		}
	}
	ajax_request.send( JSON.stringify(solicitudPrincipal));
}

function verificarFinalizacionSolicitud(){
	var ajax_request = new XMLHttpRequest();
	ajax_request.open( "POST", context_path + "/alta/verificaEstadoSolicitud", true );
	ajax_request.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
	ajax_request.onreadystatechange = function() {
		if (ajax_request.readyState == 4 && ajax_request.status == 200) {
			var data=JSON.parse(ajax_request.responseText);
			solicitudCreada = data.solicitud;
			var estadoSolicitud = data.solicitud.estadoSolicitud.idEstadoSolicitud;
			if(estadoSolicitud == 2) {
				clearInterval(checarSolicitud);    			
				var tramite = data.tramiteSO;
				$("#idSolicitud").val(solicitudCreada.solicitudId);
				$("#idTramite").val(tramite.tramiteId);
				var fechSolicitudAltaPatronal = convertirFechasSol(solicitudCreada.fechaConclusion);
				$("#fechaSolARP").html(fechSolicitudAltaPatronal);
				$("#fechaSolTIP").html(fechSolicitudAltaPatronal);
				$("#folioSolARP").html(solicitudCreada.noFolioSolicitud);
				$("#folioSolTIP").html(solicitudCreada.noFolioSolicitud);
				$("#registroPatronalCreado").html(tramite.sujetoObligado.numeroRegistroPatronal); 
				$("#divProcesandoSolicitud").dialog('close');
				limpiarDOM();			
				$("#resumenRegistro").show();
				$.unblockUI();
			} else if(estadoSolicitud == 1) {
				clearInterval(checarSolicitud);
				errorFinalizadoSolicitud(solicitudCreada.noFolioSolicitud);
			}
		}
	}
	ajax_request.send( JSON.stringify(solicitudCreada));
}

function convertirFechasSol(fecha){

	if(fecha != null) {
		try{

			var fechaConGuion = fecha.split("T");
			var fechas = fechaConGuion[0].split("-");
			return fechas[2]+"/"+fechas[1]+"/"+fechas[0];

		} catch(err){}
	}

	return "";
}

function limpiarDOM(){	
	var componentes=$(".seccionTramite")
	for(var s=0;s<componentes.length;s++){
		if(componentes[s].id!="resumenRegistro"){      
			$("#"+componentes[s].id).remove();
		}	    
	}
}

function errorFinalizadoSolicitud(folioSol){
	var $divMensajes = $("<div></div>");
	$divMensajes.dialog({
		resizable: false,
		modal:true,
		heigth: 'auto',
		width: '400px',
		title: "Error",
		autoOpen: false,
		closeOnEscape: false,
		buttons: {
			"Aceptar": function() {
				$(this).dialog("close");
				location.href="http://www.imss.gob.mx/servicios-digitales";
			}
		},
		create:function () {
			$(this).closest(".ui-dialog")
			.find("button:first") // the first button
			.addClass("btn btn-sm btn-primary");
		}
	})

	$divMensajes.html("<div class=\"alert alert-danger\">Ocurri&oacute; un error al finalizar su solicitud con folio <strong>"+folioSol+"</strong>. Por favor intentelo mas tarde .</div>");
	$divMensajes.dialog('open');
}

function procesandoSolicitud() {
	var $divMensajes = $("#divProcesandoSolicitud");
	$divMensajes.dialog({
		resizable: false,
		width: "850px",
		height: "auto",
		modal: true,
		title: "Procesando solicitud",
		autoOpen: false,
		closeOnEscape: false,
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$divMensajes.dialog('open');
	
}