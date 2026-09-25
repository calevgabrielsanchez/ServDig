/**
 * Mario Teran Blanco
 * IMSS (Instituto Mexicano del Seguro Social)
 * 10/04/2012
 */

/**
 * Metodo para ver el detalle de la solicitud en un dialog recibiendo los siguientes parametros
 * @param idSolicitud que es el id de la solicitud a mostrar el detalle
 */

function detalleSolicitud(idsolicitud) {
	
	$detalleSolicitud = $('<div id="solicitud'+idsolicitud+'"></div');
	
	$detalleSolicitud.dialog({
		autoOpen : false,
		title: 'Detalle de solicitud',
		modal: true,
		width: 950,
		height: 500,
		resizable: false,
		buttons: {
			"Cerrar": function() {
				$(this).dialog('close');
				$(this).dialog('destroy');
				$(this).html('');
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	var ajaxSource = context_path + '/solicitud/detalle/';
	var solicitud = {'idSolicitud': idsolicitud};
	
	$detalleSolicitud.html('Cargando Detalle de la solicitud...');
	$detalleSolicitud.load(ajaxSource,solicitud);
	
	$detalleSolicitud.dialog('open');
}

/**
 * Metodo para cancelar una solicitud y sus tramites recibe los siguientes parametros
 * @param solitud el id d ela solicitud a cancelar
 * @param folio el folio de la solicitud a cancelar
 * @param dataTable la tabla que sera actualizada despues de cancelar una solicitud
 */
function cancelarSolicitud( solicitud , dataTable) {
	$cancelarSolicitud = $('<div align="center"></div');
	
	$cancelarSolicitud.dialog({
		autoOpen : false,
		title: 'Confirmaci\u00F3n Requerida',
		modal: true,
		width: 400,
		height: 200,
		resizable: false,
		buttons: {
			"Aceptar": function() {
				procesarCancelacionSolicitud(solicitud,$(this),dataTable);
			},
			"Cancelar": function() {
				$(this).dialog('close');
				$(this).dialog('destroy');
				$(this).html('');
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$cancelarSolicitud.html('\u00BF Est\u00E1 seguro que desea cancelar la solicitud?');
	$cancelarSolicitud.dialog('open');
}

/**
 * Proceso que hace la llamada al controlador para cancelar la solicitud
 * recibiendo los siguientes parametros
 * @param solicitud
 */
function procesarCancelacionSolicitud(solicitud,$vsolicitud, dataTable) {
	
	$vsolicitud.html('Esperando respuesta...');
	$vsolicitud.dialog(parametrosEspera());
	
	var ajaxSource = context_path + "/solicitud/cancelar";
	var solicitud = {"solicitudId" : solicitud,"razonCancelacion" : {"idRazonCancelacion": 5},"observacion" : "A peticion del derechohabiente"};
	$.postJSON(ajaxSource,solicitud,
			function(result) {
				$vsolicitud.html(result.modelo);
				$vsolicitud.dialog(parametrosRespuesta(dataTable));
			}
	);
	
}

function parametrosEspera() {
	var parametrosEsp = {
		autoOpen : false,
		title: 'Resultado',
		resizable: false,
		modal: true,
		width: 400,
		height: 200
	}

	return parametrosEspera;
}

function parametrosRespuesta (datatable) {
	var parametrosResp = {
			autoOpen : false,
			title: 'Resultado',
			resizable: false,
			modal: true,
			width: 400,
			height: 200,
			buttons: {
				"Aceptar": function() {
					if(datatable!=null && datatable!= undefined)
					{
						datatable.fnDraw();
					}
					$(this).dialog('close');
					$(this).dialog('destroy');
					$(this).html('');
				}
			}
	};
	
	return parametrosResp;
}

function htmlCorrecto (mensajea) {
	var mensaje = '<div id="wrapperIntsAnterior" class="ui-widget"';
	mensaje += 'style="width: 450px !important;" align="center">';
	mensaje += '<div class="ui-state-highlight ui-corner-all"';
	mensaje += 'style="margin-top: 20px; padding: 0 .5em;">';
	mensaje += '<p><span class="ui-icon ui-icon-info"style="float: left; margin-right: .3em;"></span>'+mensajea+'</p></div>';
	mensaje += '</div>';
	return mensaje;
}

function htmlError (mensaje) {
	var error = '<div class="ui-widget-content ui-corner-all">';
	error += '<div class="ui-state-error ui-corner-all" align="center">';
	error += '<p class="ui-helper-reset ui-state-error-text"><div class="ui-icon ui-icon-alert">'+mensaje+'</p>';
	error += '</div>';
	error += '</div>';
	
	return error;
}
