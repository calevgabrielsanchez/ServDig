/**
 * Mario Teran Blanco
 * IMSS (Instituto Mexicano del Seguro Social)
 * 16/04/2012
 * Script relacionado con las operaciones comunes de los tramites de baja
 */

$(document).ready(function() {

	//checamos que haya elementos para poder dar de baja de lo contrario
	//escondemos el boton de aceptar
	if($("#candidato").val() == undefined) {
		$('#aceptar').hide();
	}
	
	//ponemos el estilo del grid a nuestra tabla
	$('#candidatos').dataTable( {
		bJQueryUI : true,
        bFilter : false,
        bInfo:true,
        bSort: false,
        "bPaginate": true,
        "bAutoWidth" : true,
        "iDeferLoading" : 0
        });
	
	//agregamos el evento del boton cancelar
	$("#cancelar").click(function() {
		cancelarBaja();
	});
});

function esperePorFavor() {
	$decision = $('<div></div');

	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 140,
		title : '',
		modal : true
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text('Espere un momento por favor');
	$decision.dialog('open');
}

function errorTramite(tipoTramite) {
	var mensajeError = '<div class="ui-widget">' +
	'<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">'+
	'<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>' +
	'<strong>Esta persona ya cuenta con un tr&aacute;mite de' + tipoTramite+', para realizar cualquier tr&aacute;mite finalizar el tr&aacute;mite abierto</strong></p></div></div>';

	$razonRechazo = $('<div></div');
	$razonRechazo.html(mensajeError);
	$razonRechazo.dialog({
		autoOpen : false,
		title: '',
		show: "blind",
		hide: "explode",
		resizable: false,
		modal: true,
		width: 500,
		buttons: {
			"Cerrar": function() {
				cierraDialogo($(this));
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$razonRechazo.dialog('open')
}

function cancelarBaja() {
	$.blockUI();
	location.href = "" + context_path + "/inicio/grupoFamiliar";
}

function errorNoSeleccionado() {
	$noSeleccionado = $('<div></div');

	$noSeleccionado.dialog({
		autoOpen : false,
		resizable : false,
		height : 140,
		title : 'Error',
		modal : true,
		buttons : {
			"Aceptar" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$noSeleccionado.text('Debe seleccionar un derechohabiente');
	$noSeleccionado.dialog('open');
}

function mostrarConprobanteSolcitudInternet(idSolicitud,titulo){
	var direccion=context_path + "/documentos/comprobanteSolicitud?idSolicitud="+idSolicitud+"&titulo="+titulo;
	var page=context_path + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable: yes,dialogwidth: 1000,dialogheight:1000,scroll:on");	
}



function parametrosRespuesta($dialogo){
	
	opciones = {
		autoOpen : false,
		resizable: false,
		height: 140,
		title: 'Resultado',
		modal: true,
		buttons: {
			"Aceptar" : function() {
				cierraDialogo($dialogo);
			}
		}
	};
	
	return opciones;
}

function parametrosEspera(){
	
	opciones = {
		autoOpen : false,
		resizable: false,
		height: 140,
		title: 'Esperando...',
		modal: true,
		buttons: {
			"" : function() {}
		}
	};
	
	return opciones;
}