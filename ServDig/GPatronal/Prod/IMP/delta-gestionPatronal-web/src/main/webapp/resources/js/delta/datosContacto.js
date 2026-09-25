var sIdFormActualizarDatosContacto = '#datosContactoForm';
var sIdDialogActualizarDatosContacto = "#dgActualizarDatosContacto"
var oDialogActualizarDatosContacto;

/** Seccion de codigo a ejectuar cuando el DOM este listo * */
$(function() {
	/* Configuracion del dialogo de actualizacion exitosa */
	oDialogActualizarDatosContacto = $(
			sIdDialogActualizarDatosContacto).dialog( {
		autoOpen : false,
		resizable : true,
		modal : true,
		height : 200,
		width : 350,
		buttons : {
			"Aceptar" : function() {
				$(this).dialog('close');
				history.back();
			}
		}
	});
	
	$("#telFijo").keydown(soloNumeros);
	
	$("#extencion").keydown(soloNumeros);
	
	$("#telMovil").keydown(soloNumeros);
});

var soloNumeros = function(event) {
	// Allow Only: keyboard 0-9, numpad 0-9, backspace, tab, left arrow, right arrow, delete, shift, ini, fin
	if ((event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode >= 96 && event.keyCode <= 105)
			|| event.keyCode == 8 || event.keyCode == 9 || (event.keyCode > 34 && event.keyCode < 38)
			|| event.keyCode == 39 || event.keyCode == 46 || event.keyCode == 16) {
		// Allow normal operation
	} else {
		event.preventDefault();
	}
}

function actualizarDatosContacto(accion) {
	fnHideErrores('#datosContactoForm');
	/* recuperamos los valores del form */
	var objForm = $("#datosContactoForm").toObject(true);
	sSource = context_path + "/sujetoObligado/actualizarDatosContacto/"+$("#idSolicitud").val();
	$.postJSON(sSource, objForm, function(data) {
		/* Actualizamos datos */
		//oDialogActualizarDatosContacto.dialog("open");
		
		switch(accion){
    		case 'activar':
				$("#formaAcuse").submit();
				dialogoTramiteDatosContacto.dialog("close");
				if(data.mensajeExito != undefined && data.mensajeExito != null){
					titulo = "Operaci&oacute;n Exitosa";
					error = false;
					if(data.mensajeExito == ""){
						mensaje = "Operaci&oacute;n realizada con &eacute;xito."
					}else{
						mensaje = data.mensajeExito;
					}
					construirDialogoMensajes(titulo, mensaje, error);
					return true;
				}
				break;
    		case 'finalizar':
				oDialogActualizarDatosContacto.dialog("open");
				break;
    	}
		
	}).error(function(data) {
		// hasError = true;
		fnProcesarErrores(data, '#divActualizarDatosContacto');
	});
}

function construirDialogoMensajes(titulo, mensaje, error){
	$("#textoMensaje").html(mensaje);
	$("#textoMensaje").removeAttr("style");
	if(error){
		$("#textoMensaje").attr("style", "color: red;");
	}else{
		$("#textoMensaje").attr("style", "color: blue;");
	}
	var dialogo = $("#dialogoMensajes").dialog({
		autoOpen:false, resizable: false, modal: true,
		height: 300, width: 450,
		title : titulo,
		buttons: {"Aceptar" : function(){$( this ).dialog( "close" );
		history.back();}}
	});
	dialogo.dialog('open');
}
