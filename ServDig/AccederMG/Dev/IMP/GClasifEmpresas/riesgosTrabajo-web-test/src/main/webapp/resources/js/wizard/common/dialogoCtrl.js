var dialogosCtrl= {
	close: function() {
		$(this).dialog('close');
	},
	abrirDialogo: function(properties) {
		var $dialogMensajes = $( "#dialogMensajesCtrl" );
		$dialogMensajes.html(properties.mensaje);
		$dialogMensajes.dialog('option','title',properties.titulo);
		$dialogMensajes.dialog('option','buttons',properties.buttons);
		$dialogMensajes.dialog("open");
	},
	init: function() {
		$( "#dialogMensajesCtrl" ).dialog({
			resizable: false,
			height:'auto',
			modal: true,
			autoOpen: false
		});
	}
};

$(document).ready(dialogosCtrl.init);