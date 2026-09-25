/**
 * victor 
 * IMSS (Instituto Mexicano del Seguro Social)
 * 10/04/2012
 */

/**
 * Metodo para ver el detalle de la solicitud en un dialog recibiendo los siguientes parametros
 * 
 * @param idSolicitud que es el id de la solicitud a mostrar el detalle
 */
function detalleSolicitudReg(idsolicitud) {
	
	$detalleSolicitudReg= $('<div></div');
	
	$detalleSolicitudReg.dialog({
		autoOpen : false,
		title: 'Detalle de solicitud',
		modal: true,
		width: 500,
		height: 200,
		buttons: {
			"Cerrar": function() {
				$detalleSolicitudReg.html('');
				$(this).dialog('close');
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	var ajaxSource = context_path + '/solicitud/detalleReg/';
	var solicitud = {'idSolicitud': idsolicitud};
	
	$detalleSolicitudReg.load(ajaxSource,solicitud);
	
	$detalleSolicitudReg.dialog('open');
}

