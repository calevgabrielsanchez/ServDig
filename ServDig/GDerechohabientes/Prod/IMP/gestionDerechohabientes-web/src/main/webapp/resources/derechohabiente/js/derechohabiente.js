/**
 * Mario Teran Blanco
 * IMSS (Instituto Mexicano del Seguro Social)
 * 10/04/2012
 */

function detalleDerechohabiente(idDerechohabiente) {
	
	$detalleDerechohabiente = $('<div></div');
	$detalleDerechohabiente.html('Cargando detalle del Derechohabiente...')
	$detalleDerechohabiente.dialog({
		autoOpen : false,
		title: 'Detalle Derechohabiente',
		show: "blind",
		hide: "explode",
		resizable: false,
		modal: true,
		height: 700,
		width: 1000,
		buttons: {
			"Cerrar": function() {
				$(this).dialog('close');
				$(this).dialog('destroy');
				$(this).html('');
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	var ajaxResource = context_path + '/derechohabiente/detalle/';
	var derechohabiente = {"idDerechohabiente" : idDerechohabiente != undefined && idDerechohabiente != null ? idDerechohabiente : 0};
	$detalleDerechohabiente.load(ajaxResource, derechohabiente);
	$detalleDerechohabiente.dialog('open');
}