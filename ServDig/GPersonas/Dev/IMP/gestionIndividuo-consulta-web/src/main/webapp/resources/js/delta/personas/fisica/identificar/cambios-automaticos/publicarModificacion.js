var widgetCometConn;
var oDialogoCerrarSesion;

$(document).ready(function() {
	oDialogoCerrarSesion = $('#dgExito').dialog({
		autoOpen : false,
		closeOnEscape: false,
		resizable : false,
		height : 150,
		modal : true,
		open : function(event, ui) {
			$(this).parent().children().children(".ui-dialog-titlebar-close").hide();
		},
		buttons : {
			"Aceptar" : function(data) {
				$(this).dialog("close");
			}
		}
	});

	var urlWidget = "/delta-comet-web/static/resources/js/delta/comet/WidgetServiceComet.js";
	var idPersonaFisica = 25129441;

	$.getScript(urlWidget).done(function(script, textStatus) {
		widgetCometConn = new WidgetServiceCtrl();
		widgetCometConn.suscribirWidget('actualizacionPersonaFisica', personaFisicaWidgetCallback, idPersonaFisica);
		widgetCometConn.init();
	})
	.fail(function(jqxhr, settings, exception) {
		alert('Error al cargar el script ' + urlWidget);
	});
});

personaFisicaWidgetCallback = function(message) {
	var aviso = message.data.idPersona;
	$('#dgMensajeSistema').text('Id Fisica: ' + aviso);
	oDialogoCerrarSesion.dialog('open');
};
