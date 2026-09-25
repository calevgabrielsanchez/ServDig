var dialogoConfirmarCancelar;
var dialogoConfirmar;
var urlRissVentanilla = '/alta/riss/';

$(function() {
	$('#btnCancelarSolic').click(function() {
		dialogoConfirmarCancelar.dialog("open");
	});

	$('#btnProcesarSolic').click(function(e) {
		e.preventDefault();
		$('form#concluirSolicForm').submit();
	});

	dialogoConfirmarCancelar = $("#dialog-confirm-cancelar").dialog({
		resizable : false,
		height : 'auto',
		modal : true,
		autoOpen : false,
		buttons : {
			"ACEPTAR" : function() {
				$(this).dialog("close");
				cancelarTramite();
			},
			"CANCELAR" : function() {
				$(this).dialog("close");
			}
		}
	});

	dialogoConfirmar = $("#dialog-confirm").dialog({
		resizable : false,
		height : 160,
		modal : true,
		autoOpen : false,
		buttons : {
			"ACEPTAR" : function() {
				window.location = context_path + urlRissVentanilla + 'iniciar';
			}
		}
	});
});

function cancelarTramite() {
	var url = context_path + urlRissVentanilla + 'cancelar';

	$.blockUI();

	$.ajax({
		url : url,
		type : "POST",
		dataType : "json",
		data : {
			idSolicitud : $('#idSolic').val()
		}
	}).success(function(data) {
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog("open");
	}).error(function(data) {
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.open();
	}).done(function(data) {
		$.unblockUI();
	});
}