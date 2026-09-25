var index=-1;
var dialogoListaTramitesClasificacion;

function muestraOpciones() {
	
	dialogoListaTramitesClasificacion = $("#listaTramitesClasificacion").dialog({
		autoOpen : false,
		resizable : false,
		height : 550,
		width : 400,
		modal : true,
		buttons : {
			'Continuar' : validaSeleccionDeTramiteClasificacion,
			'Cancelar' : cerrarVentana
		}
	});
	
	$("#selectable").selectable({
		selected : function(event, ui) {
			$(ui.selected).siblings().removeClass("ui-selected");
		},
		stop : function() {
			$(".ui-selected", this).each(function() {
				index = $("#selectable li").index(this);
			});
		}
	});
	
	dialogoListaTramitesClasificacion.dialog('open');
}

function cerrarVentana(){
	dialogoListaTramitesClasificacion.dialog("close");
	parent.ModSRTCtrl.cerrar();
}

function validaSeleccionDeTramiteClasificacion() {

	if (index != -1) {
		$("#idSolicitud").val("");
		$("#idTramite").val($("#selectable li")[index].id);

		dialogoListaTramitesClasificacion.dialog('close');
		$.blockUI();
		
//		if (context.indexOf("clasificacion") < 0) {
//			context += "/cmp/clasificacion/msrt/generarSolicitud/";
//		} else {
//			context += "/";
//		}

		navegarTo('/cmp/clasificacion/generarSolicitud?idTipoTramite='+$("#selectable li")[index].id, 'clasificacionInvokerForm');

	} else {
		var oDialogoGenerico = undefined;
		construirDialogoGenerico("#dialogoMensajes", oDialogoGenerico, "Aviso",
				"Debe seleccionar el tr\u00E1mite que desea realizar.", true,
				undefined, undefined, 150, 400);
	}
}


$(function(){
	muestraOpciones();
});