
function showGuiaTramite(tipoTramite,rol){
	var direccion=contextPath + "/guiaTramite/muestraGuiaTramite?idTipoTramite="+tipoTramite+"&idRol="+rol;
	var page=contextPath + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");	
}


function showGuiaTramite2(tipoTramite,rol) {
	
	$divPdfGuiaTramite = $('<div></div');
	var ajaxSource = context_path + '/guiaTramite/muestraGuiaTramite';
	var solicitud = {'idTipoTramite': tipoTramite,'idRol':rol};
	var url = context_path + '/guiaTramite/muestraGuiaTramite?idTipoTramite='+tipoTramite+'&idRol='+rol;
	$iframe = $('<iframe   src="'+ url +'"  width="100%" height="100%"/>');
	$iframe.dialog({
		autoOpen : false,
		title: 'Guia Tramite',
		modal: false,
		width: 1900,
		height: 1200,
		buttons: {
			"Cerrar": function() {
				$(this).dialog('close');
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	
	$iframe.dialog('open');
}
