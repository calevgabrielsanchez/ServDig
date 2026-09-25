var index=-1;
var dialogoConfirmarCancelar;
var dialogoConfirmar;

$(document).ready(function(){
	$("#selectable").selectable({
		selected : function(event, ui) {
			$(ui.selected).siblings().removeClass("ui-selected");
		},
		stop : function() {
			$(".ui-selected", this).each(function() {
				index = $("#selectable li").index(this);
			});
		}
	});

	$('#btnInciaTramite').click(function(){
		inicarTramite();
	});
	
	$('#btnRetomarTramite').click(function(){
		retomarTramite();
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
				parent.WizardModificacionPatronClasificacionCtrl.limpiarElementosSesion();
				parent.WizardModificacionPatronClasificacionCtrl.abrir();
		 	}
		 }
	 });
	
	$('[data-toggle="tooltip"]').tooltip();

});

function inicarTramite() {
	if (index != -1) {
		var numeroRegistroPatronal = parent.WizardModificacionPatronClasificacionCtrl.config.numeroRegistroPatronal;
		var idTipoTramite = $("#selectable li")[index].id;
		
		if (idTipoTramite==176){
			var msj = 'Para realizar este tr\u00E1mite usted deber\u00E1 contar con el registro patronal del domicilio anterior y registro patronal del nuevo domicilio.<br>' +
			'Es su responsabilidad asignar la baja del Registro Patronal del domicilio anterior.';
			construirDialogoCambioMunicipio(msj, tramiteCambio, callbackCancelar, 250, 400);	
		} else{
			var urlAction = '/delta-gestionPatronal-web/wizard/tramite/modificar/patron/clasificacion/generarSolicitud/' + idTipoTramite;
			$("#modificacionClasificacionForm #hdnClasifNumeroRegistroPatronal").val(numeroRegistroPatronal);
			$.blockUI();

			document.getElementById('modificacionClasificacionForm').action = urlAction;
			document.getElementById('modificacionClasificacionForm').submit();	
		}
		
	} else {
		var oDialogoGenerico = undefined;
		construirDialogoGenerico("#dialogoMensajes", oDialogoGenerico, "Aviso",
				"Debe seleccionar el tr\u00E1mite que desea realizar.", true,
				undefined, undefined, 200, 450);
	}
}

function callbackCancelar(){
	console.log("::: En callbackCancelar, se cierra el dialogo ");
}

function tramiteCambio(){
	var numeroRegistroPatronal = parent.WizardModificacionPatronClasificacionCtrl.config.numeroRegistroPatronal;
		var idTipoTramite = $("#selectable li")[index].id;
		var urlAction = '/delta-gestionPatronal-web/wizard/tramite/modificar/patron/clasificacion/generarSolicitud/' + idTipoTramite;
		$("#modificacionClasificacionForm #hdnClasifNumeroRegistroPatronal").val(numeroRegistroPatronal);
		$.blockUI();

		document.getElementById('modificacionClasificacionForm').action = urlAction;
		document.getElementById('modificacionClasificacionForm').submit();	
}

function construirDialogoCambioMunicipio(mensaje, callbackAceptar, callbackCancelar, height, width){
	if (height==undefined)
		height=150
	if (width==undefined)
		width=400

	$("#textoMensaje").html(mensaje);
	var dialogo = $("#dialogoMensajes").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : height,
		width : width,
		title : "Confirmaci&oacute;n",
		buttons : {
			"Cancelar" : function() {
				if(callbackCancelar!=undefined)
					callbackCancelar();
				$(this).dialog("close");
			},
			"Aceptar" : function() {
				if(callbackAceptar!=undefined)
					callbackAceptar();
				$(this).dialog("close");
			}
		}
	});
	dialogo.dialog('open');
}

function retomarTramite() {
	
	var numeroRegistroPatronal = parent.WizardModificacionPatronClasificacionCtrl.config.numeroRegistroPatronal;
	var idTipoTramite = $('#hdnIdTipoTramite').val();
	var idSolicitudPendiente = $('#hdnIdSolicitud').val();

	$("#modificacionClasificacionForm #hdnClasifNumeroRegistroPatronal").val(numeroRegistroPatronal);
	var urlAction = '/delta-gestionPatronal-web/wizard/tramite/modificar/patron/clasificacion/retomar/solicitud/'
			+ idTipoTramite + '/' + idSolicitudPendiente;

	$.blockUI();
	
	document.getElementById('modificacionClasificacionForm').action = urlAction;
	document.getElementById('modificacionClasificacionForm').submit();
}

function cancelarTramite() {
	var idSolicitudPendiente = $('#hdnIdSolicitud').val();
	var url = '/delta-gestionPatronal-web/wizard/tramite/modificar/patron/clasificacion/cancelar/solicitud/'
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
	parent.WizardModificacionPatronClasificacionCtrl.cerrar();
}
