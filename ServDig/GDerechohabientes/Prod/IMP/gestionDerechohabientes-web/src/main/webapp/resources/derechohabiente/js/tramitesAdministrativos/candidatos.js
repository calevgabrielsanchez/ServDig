/**
 * 
 */
$(document).ready(function(){
	
	$("#aceptar").on('click', function() {
		validarSeleccionDerechohabiente();
	});
	
	$("#regresar").on('click', function() {
		regresarGrupoFamiliar();
	});
});


/**
 * 
 */

var validarSeleccionDerechohabiente = function() {
	var derechohabiente = $("input:radio[name=candidato]:checked").val();
	
	if(derechohabiente != undefined || derechohabiente != null) {
		var url = context_path + "/tramite/tramiteAbierto";
		//primero validaremos los tramites abiertos
		var parametros = {
			'idPersona' : derechohabiente
		}
		
		$.postJSON( url, parametros, 
			function(result) {
				if(result.modelo != null) {
					tramiteAbierto(result.modelo.tipoTramite.descripcion);
				} else {
					procesarSeleccion(derechohabiente);
				}
			}	
		);
	} else {
		derechohabienteNoSeleccionado();
	}
}

var procesarSeleccion = function(idDerechohabiente) {
	$.blockUI();
	$("#integrante").attr("action",context_path + "/tramites/admin/crear");
	$("#derechohabiente\\.idPersona").val(idDerechohabiente);
	$("#integrante").submit();
}


var regresarGrupoFamiliar = function() {
	$.blockUI();
	location.href = "" + context_path + "/inicio/grupoFamiliar";
}

var derechohabienteNoSeleccionado = function() {
	var mensaje = "Debe seleccionar un derechohabiente";
	var buttons = {"Aceptar": function() {cerrarDialogo($(this));}}
	crearDialogoMensaje(mensaje, "Error", buttons);
};

var tramiteAbierto = function(tipoTramite) {
	
	var mensaje = '<div class="ui-widget">' +
	'<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">'+
	'<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>' +
	'<strong>Esta persona ya cuenta con un tr&aacute;mite de ' + tipoTramite+ ', para realizar cualquier tr&aacute;mite finalizar el tr&aacute;mite abierto</strong></p></div></div>';
	var buttons = {"Cerrar": function() {cerrarDialogo($(this));}}
	crearDialogoMensaje(mensaje, "Error", buttons);
}

var crearDialogoMensaje = function(mensaje, titulo, buttons) {

	var $dialogo = $('<div></div');
	$dialogo.html(mensaje);
	$dialogo.dialog({
		autoOpen : false,
		title: titulo,
		show: "blind",
		hide: "explode",
		resizable: false,
		modal: true,
		width: 500,
		buttons: buttons
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$dialogo.dialog('open');

}

var cerrarDialogo = function($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
}