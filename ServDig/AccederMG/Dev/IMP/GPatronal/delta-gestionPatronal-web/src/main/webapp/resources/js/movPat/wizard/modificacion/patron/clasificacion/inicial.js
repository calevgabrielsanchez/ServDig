var index=-1;
var dialogoConfirmarCancelar;
var dialogoConfirmar;
var pathMovPat = '/movPat';
var origenInternet = false;

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
		var numeroRegistroPatronal = parent.WizardModificacionPatronClasificacionCtrl.config.numeroRegistroPatronal;
		dialogoConfirmarCancelar.dialog( "open" );
		$('#rpMessage').html(numeroRegistroPatronal);
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
	
	console.log('inicial.js idOrigenSolicitud', idOrigenSolicitud);
	console.log('pathMovPat', pathMovPat);
	
	origenInternet = idOrigenSolicitud === idOrigenINTERNET;
	
	if (origenInternet) {
		pathMovPat = '/movPat/internet'
	}
	

});

function inicarTramite() {
	if (index != -1) {
		var numeroRegistroPatronal = parent.WizardModificacionPatronClasificacionCtrl.config.numeroRegistroPatronal;
		var idTipoTramite = $("#selectable li")[index].id;
		
		if (idTipoTramite==176){
			var msj = 'Para realizar este tr\u00E1mite usted deber\u00E1 contar con el registro patronal del domicilio anterior y registro patronal del nuevo domicilio.<br>' +
			'Es su responsabilidad realizar el tr\u00E1mite de baja del Registro Patronal del domicilio anterior.';
			construirDialogoCambioMunicipio(msj, tramiteCambio, callbackCancelar, 250, 400);	
		} else{
			var urlAction = context_path + pathMovPat + '/wizard/tramite/clasificacion/generarSolicitud/' + idTipoTramite;
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
		var urlAction = context_path + pathMovPat + '/wizard/tramite/clasificacion/generarSolicitud/' + idTipoTramite;
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
	var urlAction = context_path + pathMovPat + '/wizard/tramite/clasificacion/retomar/solicitud/'
			+ idTipoTramite + '/' + idSolicitudPendiente;

	$.blockUI();
	
	document.getElementById('modificacionClasificacionForm').action = urlAction;
	document.getElementById('modificacionClasificacionForm').submit();
}

function cancelarTramite() {
	var idSolicitudPendiente = $('#hdnIdSolicitud').val();
	var url = context_path + pathMovPat + '/wizard/tramite/clasificacion/cancelar/solicitud/'
				+ idSolicitudPendiente;
	
	$.blockUI();
	
	$.postJSON(url, null, function(data) {
		//console.log('**** CANCELAR TRAMITE*****', JSON.stringify(data));
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog( "open" );
		
		if (origenInternet) {
			downloadPDF(data.acuseCancelacion, data.nombreArchivo);	
		}
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


function downloadPDF(pdf, nombreArchivo) {
	const linkSource = `data:application/pdf;base64,${pdf}`;
	const downloadLink = document.createElement("a");
	const fileName = `${nombreArchivo}`;
	downloadLink.href = linkSource;
	downloadLink.download = fileName;
	downloadLink.click();
	
	var d = $("#pdfcontainer").html("<iframe width='100%' height='100%' src='data:application/pdf;base64, " +
    encodeURI(pdf) + "'></iframe>");
	
	objReporte = d.dialog({

		title : 'Acuse de Cancelacion',
		autoOpen : false,
		width : "100%",
		height : 900,
		modal : true,
		resizable : false,
		autoResize : true,
		overlay : {
			opacity : 0.5,
			background : "black"
		},
		close : function(event, ui) {
			cancelarInicioTramite();
		}
	}).height(900);
	objReporte.dialog('open');

}


function presentarAcuseCancelacion(){
	//$("#formaAcuse").submit(); TODO:
	var url = context + "presentarAcuse";
	$('#reporteFrame').html('<iframe id="reporteClasificacionFrame" src="' + url 
			+ '" width="100%" height="900px" '
			+ 'onload="set_size(\'reporteClasificacionFrame\'); $.unblockUI();" frameborder="0"/>');
			
	$.blockUI();
	var dialogo = $("#dialogoAcuseCancelacion").dialog({
		autoOpen : false,
		resizable : true,
		modal : true,
		//height : '900px',
		//maxHeight : '95%',
		width : '95%',
		title : "Revisar",
		overlay : {
			opacity : 0.5,
			background : "black"
		}

	});
	dialogo.dialog('open');		
}
