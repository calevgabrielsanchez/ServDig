var dialogoConfirmarCancelar;
var dialogoConfirmar;
var textoMensaje = 'textoMensajeAP';
var dialogoMensajes = 'dialogoMensajesAP';


$(document).ready(function(){
	
	$('#btnInciaTramite').click(function(){
		$.blockUI();
		var idTipoPersona = ''+$('#hdnTipoPersona').val();
		if(idTipoPersona == idTipoPersonaFisicaEnum){
			//Personas Fisicas
			validaInicarTramite();
		}else{
			//Personas Morales
			inicarTramite();
		}
	});
	
	$('#btnRetomarTramite').click(function(){
		$.blockUI();
		var idTipoPersona = ''+$('#hdnTipoPersona').val();
		if(idTipoPersona == idTipoPersonaFisicaEnum){
			//Personas Fisicas
			validaRetomarTramite();
		}else{
			//Personas Morales
			retomarTramite();
		}		
	});
	
	$('#btnCancelarTramite').click(function(){
		dialogoConfirmarCancelar.dialog( "open" );
	});
	
	$('#btnInicioCancelarTramite').click(function(){
		cancelarInicioTramite();
	});

	dialogoConfirmarCancelar = $( "#dialog-confirm-cancelar" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		buttons: {
			"No": function() {
		 		$( this ).dialog( "close" );
		 	},
			"Si": function() {
				$( this ).dialog( "close" );
				cancelarTramite();
		 	}
		 }
	 });
	
	dialogoConfirmar = $( "#dialog-confirm" ).dialog({
		resizable: false,
		height:160,
		modal: true,
		autoOpen: false,
		buttons: {
			"Aceptar": function() {
				parent.WizardAltaPatronalCtrl.cerrar();
		 	}
		 }
	 });
});


function validaInicarTramite() {
	var idPersona = $('#hdnIdPersona').val();
	var urlAction = context_path +'/wizard/tramite/registro/patronal/validarIniciarRetomarAltaFisica/'+idPersona;	
	
	$.getJSON(urlAction, function(response) {
		if (response.mensajeExito != undefined && response.mensajeExito != null) {
			inicarTramite();
		}else{
			$.unblockUI();
			construirDialogoMensajesValidacion("Tr&aacute;mite improcedente", response.mensajeError, true, null);
		}
    });
}

function validaRetomarTramite() {
	var idPersona = $('#hdnIdPersona').val();
	var urlAction = context_path +'/wizard/tramite/registro/patronal/validarIniciarRetomarAltaFisica/'+idPersona;	
	
	$.getJSON(urlAction, function(response) {
		if (response.mensajeExito != undefined && response.mensajeExito != null) {
			retomarTramite();
		}else{
			$.unblockUI();
			construirDialogoMensajesValidacion("Tr&aacute;mite improcedente", response.mensajeError, true, null);
		}
    });
}

function inicarTramite() {
	var urlAction = context_path +'/wizard/tramite/registro/patronal/crear/solicitud';


	document.getElementById('personaForm').action = urlAction;
	document.getElementById('personaForm').submit();
}

function retomarTramite() {
	var idSolicitudPendiente = $('#hdnIdSolicitud').val();
	var urlAction = context_path +'/wizard/tramite/registro/patronal/retomar/solicitud/'
			+ idSolicitudPendiente;

	document.getElementById('personaForm').action = urlAction;
	document.getElementById('personaForm').submit();

}

function cancelarTramite() {
	var idSolicitudPendiente = $('#hdnIdSolicitud').val();
	var url = context_path +'/wizard/tramite/registro/patronal/cancelar/solicitud/'
				+ idSolicitudPendiente;
	
	$.blockUI();
	
	$.postJSON(url, null, function(data) {
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog( "open" );
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.open();
	}).done(function(data){
		$.unblockUI();
	});
}

function cancelarInicioTramite() {
	parent.WizardAltaPatronalCtrl.cerrar();
}

function construirDialogoMensajesValidacion(titulo, mensaje, error, callback) {
		
	if(isEmpty(mensaje)){
		mensaje="Ocurrio un error con el servidor.";		
	}
	
	$("#"+textoMensaje).html(mensaje);
	$("#"+textoMensaje).removeAttr("style");
	if (error) {
		$("#"+textoMensaje).attr("style", "color: red;");
	} else {
		$("#"+textoMensaje).attr("style", "color: black;");
	}
	var dialogo = $("#"+dialogoMensajes).dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : 'auto',
		width : 400,
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

function isEmpty(temp) {
	return (temp == undefined || temp == null || temp == "");
}