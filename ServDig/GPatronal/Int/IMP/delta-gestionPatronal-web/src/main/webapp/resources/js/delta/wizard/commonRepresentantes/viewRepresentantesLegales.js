var dialogoConfirmarCancelar;
var dialogoConfirmar;

$(document).ready(function() {
		
	$('#cancelarTramite').click(function() {
		dialogoConfirmarCancelar.dialog( "open" );
	});
	
	$('#btnConcluirRL').click(function() {
		
		var rlActivo = "";
		//rlActivo = $('input:radio[name=grupoRL]:checked').val();
		rlActivo = $("#hndRepresentanteLegalSelected").val();
		
		if(rlRequerido) {			
			//RL requerido
			if(isEmpty(rlActivo)) {
				construirDialogoErrores("* Es necesario seleccionar el Representante Legal para finalizar el tr&aacute;mite. <br/>");
			}else{			
				finalizarTramite(rlActivo);
			}
		}else{			
			//RL opcional
			if(isEmpty(rlActivo)) {
				rlActivo=0;
			}
			finalizarTramite(rlActivo);
		}
		
	});	
	
	$('#cerrarWizard').click(function() {
		cerrarWizard();
	});
	
	$('#agregarRepresentanteLegal').click(function() {
		parent.WizardAgregarRLCtrl.init('procesandoSolicitudComponent',null);
		parent.WizardAgregarRLCtrl.setDataTable(_dataTableRLs);
		parent.WizardAgregarRLCtrl.abrir();		
	});
	
	dialogoConfirmarCancelar = $( "#dialog-confirm-cancelar" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		buttons: {
			"ACEPTAR": function() {
				$( this ).dialog( "close" );
				cancelarTramite();
		 	},
		 	"CANCELAR": function() {
		 		$( this ).dialog( "close" );
		 	}
		 }
	});
	
	dialogoConfirmar = $( "#dialog-confirm" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		buttons: {
			"ACEPTAR": function() {
				cerrarWizard();
		 	}
		 }
	});
	
});

function isEmpty(temporal) {
	return (temporal == undefined || temporal == "");
}

function finalizarTramite(rlActivo){
	var idSolicitud =  $("#idSolicitud").val();
	var tipoTramite = parent.representantesLegalesCtrl.config.tipoTramite;
	var url = parent.representantesLegalesCtrl.config.urlFinalizarTramite 
		+ idSolicitud + "/" + rlActivo + "/" + tipoTramite;
	
	prepararRequest(url, null, true, procesarVentanillaRL);
}

function procesarVentanillaRL(response){
	
	$.unblockUI();
	
	if (response.mensaje != undefined && response.mensaje != null) {
		
		var folioSolicitud = $('#folioSolicitud').val();
		var idSolicitud = $('#idSolicitud').val();			
		parent.ProcesandoSolicitudCtrl.abrir(folioSolicitud);
		
		cerrarWizard();	
	}else{
		var mensaje = "Ocurrio un error al finalizar la solicitud.";
		if (response.error == "") {			
		} else {
			mensaje = response.error;
		}
		construirDialogoErrores(mensaje);
	}	
}

function construirDialogoErrores(errores) {
	$("#textoMensaje").html(errores);
	$("#textoMensaje").removeAttr("style");
	$("#textoMensaje").attr("style", "color: red;");
	var dialogo = $("#dialogoMensajes").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : 275,
		width : 500,
		title : "Existen faltantes en su captura.",
		buttons : {
			"Aceptar" : function() {
				$(this).dialog("close");
			}
		}
	});
	dialogo.dialog('open');
}

function prepararRequest(sSource, data, async, callback) {
	
	$.blockUI();
	
	var request = $.ajax({
		url : sSource,
		async : async,
		type : "POST",
		data : data ? JSON.stringify(data) : null,
		dataType : "json",
		contentType : "application/json; charset=utf-8"
	});
	request.done(callback);
	request.fail(callback);
}

function cancelarTramite() {
	var idSolicitud =  $("#idSolicitud").val();
	var url = parent.representantesLegalesCtrl.config.urlCancelarTramite + idSolicitud;
	
	$.blockUI();
	
	$.postJSON(url, null, function(data) {
		$.unblockUI();
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog( "open" );
	}).error(function(data){
		$.unblockUI();
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.open();
	});
}


function cerrarWizard(){
	parent.representantesLegalesCtrl.cerrarWizardPrincipal();		
}
