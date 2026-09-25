
function cargarRazonRechazo() {
	
	$razonRechazo = $('<div></div');
	$razonRechazo.html('Cargando las razones de rechazo...')
	$razonRechazo.dialog({
		autoOpen : false,
		title: 'Raz\u00F3n del rechazo',
		show: "blind",
		hide: "explode",
		resizable: false,
		modal: true,
		width: 500,
		buttons: {
			"Si": function() {
				var razon = $('#idRazonRechazo').val();
				var observaciones = $('#observacionesRechazo').val();
				rechazarSolicitud(razon,observaciones,$(this));
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

function rechazarSolicitud(razonRechazo, observaciones, $dialogo) {
	$("#rechazo\\.observaciones").val(observaciones);
	$("#rechazo\\.idRazonRechazo").val(razonRechazo);
	$("#frmRechazo").attr('action',context_path + "/tramite/rechazarProrrogas");
	$("#frmRechazo").submit();
}


function cierraDialogo($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
}

function parametrosRespuestaRechazo($dialogo,solicitud,tramite) {
	
	opciones = {
		autoOpen : false,
		resizable: false,
		width: 480,
		title: 'Resultado',
		modal: true,
		buttons: {
			"Aceptar" : function() {
				cierraDialogo($dialogo);
				if(solicitud != null) {
					showComprobanteRechazo();
				}
				location.href = "" + context_path + "/welcome/uno/busqueda";
			}
		}
	};

	return opciones;
}

function showComprobanteRechazo(){
	var direccion=context_path + "/documentos/rechazoSolicitud?titulo=RECHAZO DE PRÓRROGA&idTipoTramite=";
	var page=context_path + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable: yes,dialogwidth: 1000,dialogheight:1000,scroll:on");	
}
