var dialogoConfirmarCancelar;
var dialogoConfirmar;

$(document).ready(function() {
	
	// Se incializa el blockUI para las peticiones AJAX
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
	
	$('#cancelarTramite').click(function() {
		dialogoConfirmarCancelar.dialog( "open" );
	});
	
	$('#btnConcluirRL').click(function() {
		var rlActivo = "";
		var idSolicitud =  $("#idSolicitud").val();
		//rlActivo = $('input:radio[name=grupoRL]:checked').val();
		rlActivo = $("#hndRepresentanteLegalSelected").val();
		
		if(rlRequerido){
			//Es requerido el RL cuando es AP PM
			if(rlActivo == "" || rlActivo == undefined) {
				construirDialogoErrores("* Es necesario seleccionar el Representante Legal para finalizar el tr&aacute;mite. <br/>");			
			}else{
				var url = context_path + '/wizard/tramite/registro/patronal/finalizar/solicitud/' 
					+ idSolicitud + "/" + rlActivo;			
				prepararRequest(url, null, true, procesarVentanillaRL);
			}
		}else{
			//Es opcional el RL cuando es AP PF.
			if(rlActivo == "" || rlActivo == undefined) {
				rlActivo=0;
			}			
			var url = context_path + '/wizard/tramite/registro/patronal/finalizar/solicitud/' 
				+ idSolicitud + "/" + rlActivo;			
			prepararRequest(url, null, true, procesarVentanillaRL);
		}
		
	});	
	
	$('#pasoPrevioRL').click(function() {
		pasoPrevioSubmit();
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
				cancelarTramite();
		 	},
		 	"CANCELAR": function() {
		 		$( this ).dialog( "close" );
		 	}
		 }
	});
	
	dialogoConfirmar = $( "#dialog-confirm" ).dialog({
		resizable: false,
		height:160,
		modal: true,
		autoOpen: false,
		buttons: {
			"ACEPTAR": function() {
				cerrarWizard();
		 	}
		 }
	});
	
});

function pasoPrevioSubmit() {
	var idSolicitud =  $("#idSolicitud").val();
	setTimeout(function(){
		var urlAction = context_path + '/wizard/tramite/registro/patronal/pasoPrevio/solicitud/' + idSolicitud;
		$.blockUI();	
		document.getElementById('concluirForm').action = urlAction;
		document.getElementById('concluirForm').submit();
	}, 200);
}

function procesarVentanillaRL(response){
	if (response.mensajeExito != undefined && response.mensajeExito != null) {
		var folioSolicitud = $('#hdnFolioSolicitud').val();
		parent.ProcesandoSolicitudCtrl.abrir(folioSolicitud);
		cerrarWizard();	
	}else if (response.mensajeError != undefined
			&& response.mensajeError != null) {
		titulo = "Existe un inconveniente";
		error = true;

		if (response.mensajeError == "") {
			mensaje = "Ocurrio un error con el servidor.";
		} else {
			mensaje = response.mensajeError;
		}		
		construirDialogoMensajes(titulo, mensaje, error, undefined, 200, 600);
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
				campoToFocusOn.focus();
				campoToFocusOn = undefined;

			}
		}
	});
	dialogo.dialog('open');
}

function construirDialogoMensajes(titulo, mensaje, error, callback, height, width) {
	if(height==undefined)
		height='auto';
	if(width==undefined)
		width=400;
		
	$("#textoMensaje").html(mensaje);
	$("#textoMensaje").removeAttr("style");
	if (error) {
		$("#textoMensaje").attr("style", "color: red;");
	} else {
		$("#textoMensaje").attr("style", "color: black;");
	}
	var dialogo = $("#dialogoMensajes").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : height,
		width : width,
		title : titulo,
		buttons : {
			"Aceptar" : function() {
				if (jQuery.isFunction(callback)) {
					callback();
				}
				$(this).dialog("close");
			}
		}
	});
	dialogo.dialog('open');
}

function cancelarTramite() {
	var idSolicitud =  $("#idSolicitud").val();
	var url = context_path + '/wizard/tramite/registro/patronal/cancelar/solicitud/'
		+ idSolicitud;

	$.postJSON(url, null, function(data) {
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog( "open" );
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.open();
	});
}

function cerrarWizard() {	
	parent.WizardAltaPatronalCtrl.cerrar();
}
