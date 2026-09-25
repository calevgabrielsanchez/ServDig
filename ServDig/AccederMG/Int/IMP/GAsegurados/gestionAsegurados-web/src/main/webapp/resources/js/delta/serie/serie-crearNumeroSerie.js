var oDialogoConfirmarCreacion;
var oDialogoOperacionExitosa;

$(document).ready(function() {

	$('#crearSerie').click(function() {
		oDialogoConfirmarCreacion.dialog('open');
	});

	oDialogoConfirmarCreacion = $("#dgConfirmCrearSerie").dialog({
		autoOpen : false,
		resizable : false,
		height : 180,
		modal : true,
		buttons : {
			"Aceptar" : function(data) {
				$(this).dialog("close");
				crearSerie();
			},
			'Cancelar' : function() {
				$(this).dialog("close");
			}
		}
	});

	oDialogoOperacionExitosa = $("#operacionExitosa").dialog({
		autoOpen : false,
		resizable : false,
		height : 200,
		modal : true,
		buttons : {
			"Aceptar" : function(data) {
				$(this).dialog('close');
				parent.CreacionSerieCtrl.cerrar();
			}
		}
	});
});

function crearSerie() {

	fnHideErrores("form#crearSerieForm");
	
	var oForm = $("form#crearSerieForm").toObject();
	var url = $("form#crearSerieForm").attr('action');

	$.blockUI();

	$.postJSON(url, oForm, function(data) {
		$.unblockUI();
		$('label#msgExito').text(data.MENSAJE_EXITO);
		oDialogoOperacionExitosa.dialog('open');
	}).error(function(data) {
		fnProcesarErrores(data, "form#crearSerieForm");
		setSizeWithinIframe(document);
		$.unblockUI();
	});
}