/**
 * 
 */


var dialogoConfirmarCancelar;
var dialogoConfirmar;
var dialogoError;

$(document).ready(
	function() {
		
		$("#regresar").click(function() {
			$.blockUI();
			$("#formRegistro").habilitarContenido(false);
			$("#formRegistro").attr("action","/portal-ciudadano-web-externo/wizard/registro/regresar");
			$("#formRegistro").submit();
		});
		
		$('#guardarCerrarTramite').click(function() {
			guardarTramite(true);
		});
		
		$('#guardarTramite').click(function() {
			guardarTramite(false);
		});

		$('#cancelarTramite').click(function() {
			dialogoConfirmarCancelar.dialog( "open" );
		});

		$('#cerrarWizard').click(function() {
			cerrarWizard();
		});
		
		dialogoConfirmarCancelar = $( "#dialog-confirm-cancelar" ).dialog({
			resizable: false,
			height:'auto',
			modal: true,
			autoOpen: false,
			dialogClass: "no-close",
		    closeOnEscape: false,
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
			dialogClass: "no-close",
		    closeOnEscape: false
		 });
		
		dialogoError = $( "#dialog-error" ).dialog({
			resizable: false,
			height:'auto',
			modal: true,
			autoOpen: false,
			dialogClass: "no-close",
		    closeOnEscape: false,
		    buttons: {
			 	"ACEPTAR": function() {
			 		$( this ).dialog( "close" );
			 	}
			 }
		 });
	}
);

function guardarTramite(cerrar) {

	$.blockUI();
	fnHideErrores("form#formRegistro");
	var url = '/portal-ciudadano-web-externo/wizard/registro/guardar';
	habilitarDesabilitarCamposDatosBasicos(true,true);
	var tramiteRegistro = $("form#formRegistro").toObject();
	habilitarDesabilitarCamposDatosBasicos(false,false);
	$.postJSON(url, tramiteRegistro , function(data) {
		if(data.error != undefined && data.error != null && data.error != "") {
			mostrarMensajeError(data.error);
		} else {
			mostrarMensaje(data.mensaje,cerrar);
		}
		$.unblockUI();
	}).error(function(data){
		$.unblockUI()();
		mostrarMensajeError(data.mensaje);
	});
}


function mostrarMensajeError(mensaje) {
	$('#mensajeError').html(mensaje);
	dialogoError.dialog('open');
}

function mostrarMensaje(mensaje, cerrar) {
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			if(cerrar) {
				cerrarWizard();
			}
			$(this).dialog('close');
		}
	}]);
	
	$('#mensajeDialogo').html(mensaje);
	dialogoConfirmar.dialog('open');
}

function cancelarTramite() {
	
	dialogoConfirmar.dialog("option", "buttons", [ {
		text : 'ACEPTAR',
		click : function() {
			$(this).dialog('close');
			cerrarWizard();
		}
	}]);
	
	var idSolicitudPendiente = $('#idSolicitud').val();
	var url = '/portalDerechohabiente-web/wizard/registro/solicitud/cancelar';
	$.blockUI();
	$.postJSON(url, {solicitudId : idSolicitudPendiente}, function(data) {
		$.unblockUI();
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
}

function calcularEdad(fechaNac) {
	
	var fecha = new Date(fechaNac);
	//console.debug("fecha de nacimiento al aplicarle el parser ISO8601: %s", fecha);
	var hoy = new Date();
	//console.debug("fecha de nacimiento del integrante: %s, fecha de hoy: %s",fechaNac,hoy);
	var ed = datediff(hoy, fecha);
	//console.debug("diferencias entre edad %s" , ed[0]);
	return ed[0];
}

function datediff(date1, date2) {

	var y1 = date1.getFullYear(), m1 = date1.getMonth(), d1 = date1.getDate(),
	y2 = date2.getFullYear(), m2 = date2.getMonth(), d2 = date2.getDate();

	//console.debug("Fechas 1: %s,%s,%d,%s,%s,%s",y1,m1,d1,y2,m2,d2);

	if (d1 < d2) {
		m1--;
		d1 += DaysInMonth(y2, m2);
	}
	if (m1 < m2) {
		y1--;
		m1 += 12;
	}

	return [y1 - y2, m1 - m2, d1 - d2];
}


function DaysInMonth(Y, M) {
	with (new Date(Y, M, 1, 12)) {
		setDate(0);
		return getDate();
	}
}
	
function cerrarWizard() {	
	parent.WizardCapturaDocumentosProbatoriosCtrl.limpiarIndicadores();
	parent.WizardRegistroDerechohabienteCtrl.cerrar();

}

/**
 * Metodo que setea el texto seleccionado de un combo a un campo
 * @param idCombo - El id del select
 * @param idDescripcion -  el id del elemento que contendra la descripcion
 */
function setDescripcionCombo(idCombo,idDescripcion) {
	var texto = $("select#"+idCombo+" option:selected").html();
	$("#"+idDescripcion).val(texto);
}
