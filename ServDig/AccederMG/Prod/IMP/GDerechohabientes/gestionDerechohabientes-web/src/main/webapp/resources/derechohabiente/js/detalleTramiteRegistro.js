/**
 * 
 */
var mensajeAprobado = '<div class="ui-widget">' +
'<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> ' +
'<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>' +
'<strong>Aprobado</strong></p></div></div>';

var mensajeRechazo = '<div class="ui-widget">' +
'<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">'+
'<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>' +
'<strong>No aprobado</strong></p></div></div>';

var rechazarSolicitud = function() {
	
	$razonRechazo = $('<div></div');
	$razonRechazo.html('Cargando Razones de rechazo...')
	$razonRechazo.dialog({
		autoOpen : false,
		title: 'Razon rechazo',
		show: "blind",
		hide: "explode",
		resizable: false,
		modal: true,
		width: 500,
		buttons: {
			"Si": function() {
				var razon = $('#idRazonRechazo').val();
				var observaciones = $('#observacionesRechazo').val();
				guardarRechazoSolicitud(razon,observaciones,$(this));
			},
			"No": function() {
				cierraDialogo($(this));
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	var ajaxResource = context_path + '/solicitud/cargarRazonRechazo';
	$razonRechazo.load(ajaxResource);
	$razonRechazo.dialog('open');
}

function guardarRechazoSolicitud(razonRechazo, observaciones, $dialogo) {
	
	var solicitud = {
		'idSolicitud': $('#idSolicitud').val(),
		'idPersona': $('#idPersona').val(),
		'idTipoTramite': $('#idTipoTramite').val(),
		'idRazonRechazo': razonRechazo,
		'idTramite' : $('#idTramite').val(),
		'observaciones': observaciones
	};
	
	var ajax_source = context_path + "/derechohabiente/baja/rechazar";
	
	$.postJSON(ajax_source, solicitud, function(result) {
		
		if(result.errores == null) {
			$dialogo.html("<center>La solicitud a sido rechazada satisfactoriamente por la siguiente razon: <br>" + result.modelo.razonResultado.descripcion + "</center>" );
			$dialogo.dialog(parametrosRespuestaRechazo($dialogo,true));
		} else {
			$dialogo.html(result.errores[0]);
			$dialogo.dialog(parametrosRespuestaRechazo($dialogo,false));
		}
	});
}

function parametrosRespuestaRechazo($dialogo,rechazado) {
	
	opciones = {
		autoOpen : false,
		resizable: false,
		width: 480,
		title: 'Resultado',
		modal: true,
		buttons: {
			"Aceptar" : function() {
				cierraDialogo($dialogo);
				if(rechazado) {
					showComprobanteRechazo();
				}
				$detallesolicitud = $('#solicitud'+$('#idSolicitud').val()+'');
				cierraDialogo($detallesolicitud);
			}
		}
	};

	return opciones;
}

function mensajeInformativo(mensaje) {
	var mensajeA = '<div class="ui-widget">' +
	'<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> ' +
	'<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>' +
	'<strong>' + mensaje + '</strong></p></div></div>';
	
	return mensajeA;
}

function mensajeError(mensaje) {
	
	var mensajeR = '<div class="ui-widget">' +
	'<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">'+
	'<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>' +
	'<strong>' + mensaje + '</strong></p></div></div>';
	
	return mensajeR;
}
