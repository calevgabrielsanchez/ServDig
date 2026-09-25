var dialogoConfirmarCancelar;
var dialogoConfirmar;
var dialogoConfirmarSalida;
var index=-1;
var idOrigenSolicitud = '${mvn.web.app.origin.id}';

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
		iniciarTramite();
	});
	
	$('#btnRetomarTramite').click(function(){
		retomar();
	});
	
	$('#btnCancelarTramite').click(function(){
		dialogoConfirmarCancelar.dialog( "open" );
	});
	
	$('#btnInicioCancelarTramite').click(function(){
		cancelar();
	});
	
	inicializarDialogos();
});

function inicializarDialogos() {
	if($("#dialog-confirm-cancelar").length > 0) {
		dialogoConfirmarCancelar = $( "#dialog-confirm-cancelar" ).dialog({
			resizable: false,
			height:'auto',
			modal: true,
			autoOpen: false,
			buttons: {
				"Cancelar": function() {
					$( this ).dialog( "close" );
				},
				"Aceptar": function() {
					$( this ).dialog( "close" );
					cancelarSolicitud();
				}
			}
		});

		$('#btnIniciocancelarYContinuar').click(function(){
			dialogoConfirmarCancelar.dialog( "open" );
		});	
	}
	
	if($("#dialog-confirm-salir").length > 0) {
		dialogoConfirmarSalida = $( "#dialog-confirm-salir" ).dialog({
			resizable: false,
			height:'auto',
			modal: true,
			autoOpen: false,
			buttons: {
				"Continuar": function() {
					$( this ).dialog( "close" );
					parent.WizardRegistroDerechohabienteCtrl.cerrar();
				}
			}
		});
	}
	

	
	dialogoConfirmar = $( "#dialog-confirm" ).dialog({
		resizable: false,
		height:160,
		modal: true,
		autoOpen: false,
		buttons: {
			"Aceptar": function() {
				parent.WizardBajaDerechohabienteCtrl.cerrar();
		 	}
		 }
	 });
}

function iniciarTramite() {
	
	if(idOrigenSolicitud == 2) {
		$('#formIniciaTramite').submit();	
	} else if(idOrigenSolicitud == 6){
		inicioCiudadano();
	}
	
}

function inicioCiudadano() {
	if(index != -1) {
		var idTipoBaja = $("#selectable li")[index].id;
		$("#tipoTramite\\.idTipoTramite").val(idTipoBaja);
	}
	
	FORMULARIO_BAJA = $("#bajaDerechohabiente");
	var oForm = FORMULARIO_BAJA.toObject();
	var url = '/${mvn.web.app.root}'+'/wizard/baja/validaInicioCiudadano';
	fnHideErrores("form#bajaDerechohabiente");
	
	$.blockUI();
	$.postJSON(url, oForm, function(data2) {
		FORMULARIO_BAJA.attr("action",'/${mvn.web.app.root}/wizard/baja/iniciarTramiteCiudadano');
		FORMULARIO_BAJA.submit();
	}).error(function(data){
		$.unblockUI();
		fnProcesarErrores(data, "form#bajaDerechohabiente");
	});
}


function retomar() {
	var url = '/${mvn.web.app.root}'+'/wizard/baja/retomar';
	
	$('#solicitudForm').attr('action',url);
	$('#solicitudForm').submit();
}


function cancelarSolicitud() {
	var idSolicitudPendiente = $('#idSolicitudPendiente').val();
	var url = '/${mvn.web.app.root}'+'/wizard/baja/solicitud/cancelar';
	
	$.blockUI();
	
	$.postJSON(url, {solicitudId : idSolicitudPendiente}, function(data) {
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog( "open" );
	}).error(function(data){
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.open();
	}).done(function(data){
		$.unblockUI();
	});

}

function cancelar() {
	parent.WizardBajaDerechohabienteCtrl.cerrar();
}