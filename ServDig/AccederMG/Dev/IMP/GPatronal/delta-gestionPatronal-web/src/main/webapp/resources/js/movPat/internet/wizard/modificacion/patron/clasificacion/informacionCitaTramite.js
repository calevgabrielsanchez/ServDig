var index=-1;
var dialogoConfirmarCancelar;
var dialogoConfirmar;
var pathMovPat = '/movPat/internet';
var origenInternet = false;
var fechaEfectoDatePicker;

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

	
	$('#btnCancelarTramite').click(function(){
		dialogoConfirmarCancelar.dialog( "open" );
	});
	
	$('#btnCerrar').click(function(){
		cerrarPantalla();
	});
	
	$('#btnModificarCita').click(function(){
		modificarCita();
	});
	
	$('#btnCancelarCambiarFecha').click(function(){
		cancelarCambiarCita();
	});
	
	$('#btnCambiarFechaCita').click(function(){
		dialogoConfirmarCambioCita.dialog( "open" );
	});
	
	$('#btnImprimirComprobanteCita').click(function(){
		imprimirComprobanteCita();	
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
		resizable: true,
		height:'auto',
		modal: true,
		autoOpen: false,
		buttons: {
			"Aceptar": function() {
				$(this).dialog('close');
		 	}
		 }
	 });
	 
	 dialogoConfirmarCambioCita = $( "#dialog-confirm-cita" ).dialog({
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
				cambiarCita();
		 	}
		 }
	 });
	
	$('[data-toggle="tooltip"]').tooltip();
	
	console.log('inicial.js idOrigenSolicitud', idOrigenSolicitud);
	console.log('pathMovPat', pathMovPat);
	
	$('#divAcciones').show();
	$('#divModificaCita').hide();	
	
	fechaEfectoDatePicker = $("#fechaEfecto").datepicker({
		showOn: "button",
		buttonImage: context_path + "/static/resources/imagenes/calendar.gif",
		buttonImageOnly: true,
		changeMonth : true,
		changeYear : true,
		dateFormat : 'dd/mm/yy',
		maxDate: 30,
		minDate : 0,
		beforeShowDay: $.datepicker.noWeekends,
		onClose : function(dateText, inst) {
			if (dateText != "") {
				runEffectHideFechaEfectoInvalidaMsg();
				$("#fechaEfecto").attr("style", "width: 100px;");
			}
		}
	
	});

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
		downloadPDFCita(data.acuseCancelacion, data.nombreArchivo);	
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.open();
	}).done(function(data){
		$.unblockUI();
	});
}

function cerrarPantalla() {
	console.log('Cerrando pantalla');
	parent.WizardModificacionPatronClasificacionCtrl.cerrar();
}


function downloadPDFCita(pdf, nombreArchivo) {
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
			cerrarPantalla();
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

function modificarCita() {
	$('#divAcciones').hide();
	$('#divModificaCita').show();
}

function cancelarCambiarCita() {
	$('#divAcciones').show();
	$('#divModificaCita').hide();
}

function showCalendar() {
	fechaEfectoDatePicker.datepicker('show');
}

function runEffectHideFechaEfectoInvalidaMsg() {
	$("#fechaEfectoInvalidaMsg:visible").fadeOut(1000);
}

function evaluarOnblur(tf, event) {

	if (tf.value.length > 0) {
		if (tf.value.length > 0 && tf.value.length < 10) {
			tf.setAttribute("style",
					"width: 100px; border-color: red !important;");
			fechaEfectoDatePicker.datepicker('show');
			runEffectFechaEfectoInvalidaMsg();
		} else {
			var date = tf.value;
			var year = date.substr(6, 4);
			var day = date.substr(0, 2);
			var month = date.substr(3, 2);

			if (year < 2000) {
				tf.value = day + '/' + month + '/2000';
				date = tf.value;
			}

			var check = false;
			var re = /^\d{1,2}\/\d{1,2}\/\d{4}$/;
			if (re.test(date)) {
				var adata = date.split('/');
				var dd = parseInt(adata[0], 10);
				var mm = parseInt(adata[1], 10);
				var yyyy = parseInt(adata[2], 10);
				var xdata = new Date(yyyy, mm - 1, dd);
				if ((xdata.getFullYear() == yyyy)
						&& (xdata.getMonth() == mm - 1)
						&& (xdata.getDate() == dd))
					check = true;
				else
					check = false;
			} else
				check = false;

			if (!check) {
				runEffectFechaEfectoInvalidaMsg();
				fechaEfectoDatePicker.datepicker('show');
			} else {
				runEffectHideFechaEfectoInvalidaMsg();
				tf.setAttribute("style",
						"width: 100px; border-color: black !important;");
			}
		}
	} else {
		runEffectHideFechaEfectoInvalidaMsg();
		tf.setAttribute("style", "width: 100px;");
	}
}

function cambiarCita() {
	$.blockUI();
	var idSolicitud = $('#hdnIdSolicitud').val();
	var date = $("#fechaEfecto").val();
	
	console.log('idSOlciitud', idSolicitud);
	console.log('fechaEfecto', date);

	var sSource = context_path + pathMovPat + "/wizard/tramite/clasificacion/modificaCita/" + idSolicitud;	
	
	var form_data2 = new FormData();
	form_data2.append("fechaNuevaCita", date);
	
	var request = $.ajax({
		url : sSource,
		type : "POST",
		data : form_data2,
		dataType : "json",
		processData: false,
		cache: false,
		contentType: false,
	});
	request.done(responseCambiarCita);
	request.fail(responseCambiarCita);
}

function responseCambiarCita(data) {
	$.unblockUI();
	console.log('Entrando a callbakc', data);
	$('#mensajeDialogo').text(data.mensaje);
	
	$("#mensajeDialogo").removeAttr("style");
	
	if (data.exito) {
		$("#diaCita").html(data.fechaHora);
		$("#mensajeDialogo").attr("style", "color: black;");
	} else {
		$("#mensajeDialogo").attr("style", "color: red;");
	}
	
	cancelarCambiarCita();
	dialogoConfirmar.dialog( "open" );
}

function imprimirComprobanteCita() {
	var idSolicitud = $('#hdnIdSolicitud').val();
	$.blockUI();
		
	console.log('idSOlciitud', idSolicitud);

	var sSource = context_path + "/movPat/internet/clasificacion/generarCitaPdf/" + idSolicitud;	
	
	var request = $.ajax({
		url : sSource,
		type : "POST",
		//data : form_data2,
		dataType : "json",
		processData: false,
		cache: false,
		contentType: false,
	});
	request.done(responseImprimeCita);
	request.fail(responseImprimeCita);
}	

function responseImprimeCita(data) {
	$.unblockUI();
	console.log('Entrando a callbakc imprimir cita', data);
	
	if(data.pdfCita != null){
		downloadPDF(data.pdfCita, data.idSolicitud, "cita_", closePdf);
	} else {
		construirDialogoMensajesMovPat("Error", data.mensaje, true, undefined, undefined, undefined);
	}
			
}

function closePdf() {
	
}

function downloadPDF(pdf, identificador, nombrePDF, action) {
	const linkSource = `data:application/pdf;base64,${pdf}`;
	const downloadLink = document.createElement("a");
	const fileName = nombrePDF + identificador + ".pdf";
	downloadLink.href = linkSource;
	downloadLink.download = fileName;
	downloadLink.click();
	
	var d = $("#pdfcontainer").html("<iframe width='100%' height='100%' src='data:application/pdf;base64, " +
    encodeURI(pdf) + "' onload='$.unblockUI();'></iframe>");
	
	objReporte = d.dialog({

		title : 'Comprobante de tr\u00E1mite',
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
		}
	}).height(900);
	objReporte.dialog('open');

}
