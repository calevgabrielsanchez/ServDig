var widgetCometConnMor;
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
	var idPersonaMoral = 153475;

	$.getScript(urlWidget).done(function(script, textStatus) {
		widgetCometConnMor = new WidgetServiceCtrl();
		widgetCometConnMor.suscribirWidget('actualizacionPersonaMoral', personaMoralWidgetCallback, idPersonaMoral);
		widgetCometConnMor.init();
	})
	.fail(function(jqxhr, settings, exception) {
		alert('Error al cargar el script ' + urlWidget);
	});
});

personaMoralWidgetCallback = function(message) {
	var aviso = message.data.idPersona;
	$('#dgMensajeSistema').text('Cve Moral: ' + aviso);
	oDialogoCerrarSesion.dialog('open');
};
