var sIdFormActualizarEscrituraConstitutiva = '#formActualizarEscrituraConstitutiva';
var sIdDialogActualizarEscrituraConstitutiva = "#dgActualizarEscrituraConstitutiva"
var oDialogActualizarEscrituraConstitutiva;


/** Seccion de codigo a ejectuar cuando el DOM este listo * */
$(function() {
	/* Configuracion del dialogo de actualizacion exitosa */
	oDialogActualizarEscrituraConstitutiva = $(
			sIdDialogActualizarEscrituraConstitutiva).dialog( {
		autoOpen : false,
		resizable : true,
		modal : true,
		height : 350,
		width : 350,
		buttons : {
			"Aceptar" : function() {
				$(this).dialog('close');
				history.back();
			}
		}
	});
	
	$("#numEscritura").keydown(soloNumeros);

	$("#formActualizarEscrituraConstitutiva #folioMercantil").keydown(soloNumeros);
	$("#formActualizarEscrituraConstitutiva #seccion").keydown(soloNumeros);
	$("#formActualizarEscrituraConstitutiva #partida").keydown(soloNumeros);
	$("#formActualizarEscrituraConstitutiva #volumen").keydown(soloNumeros);
	$("#formActualizarEscrituraConstitutiva #foja").keydown(soloNumeros);

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

function actualizarEscrituraConstitutiva(accion) {
	fnHideErrores('#formActualizarEscrituraConstitutiva');
	/* recuperamos los valores del form */
	var objForm = $("#formActualizarEscrituraConstitutiva").toObject(true);
	sSource = context_path + "/sujetoObligado/actualizarEscrituraConstitutiva/"+$("#idSolicitud").val();
	$.postJSON(sSource, objForm, function(data) {
		/* Actualizamos datos */
		
		switch(accion){
    		case 'activar':
    						$("#formaAcuse").submit();
    						dialogoEscrituraConstitutiva.dialog("close");
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
    						oDialogActualizarEscrituraConstitutiva.dialog("open");
    						break;
    	}
			
	}).error(function(data) {
		fnProcesarErrores(data, '#divActualizarEscrituraConstitutiva');
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

/**
 * Al ser invocado inhabilita y  limpia los campos seccion,	partida, volumen y foja
 */
function inhabilitarCapura1(){
	//alert('inhabilitarCapura1 invoked!');
	
	// habilitamos folioMercantil
	//$("#folioMercantil").attr("readonly", "false");
	
	// limpamos
	$("#formActualizarEscrituraConstitutiva #seccion").val("");
	$("#formActualizarEscrituraConstitutiva #partida").val("");
	$("#formActualizarEscrituraConstitutiva #volumen").val("");
	$("#formActualizarEscrituraConstitutiva #foja").val("");
	
	// inhabilitamos
	/*$("#formActualizarEscrituraConstitutiva #seccion").attr("readonly", "true");
	$("#formActualizarEscrituraConstitutiva #partida").attr("readonly", "true");
	$("#formActualizarEscrituraConstitutiva #volumen").attr("readonly", "true")
	$("#formActualizarEscrituraConstitutiva #foja").attr("readonly", "true");*/
	
}

/**
 * Al ser invocado inhabilita y  limpia el campo folioMercantil
 */
function inhabilitarCapura2(){
	//alert('inhabilitarCapura2 invoked!');
	
	// habilitamos para edición
	/*$("#formActualizarEscrituraConstitutiva #seccion").attr("readonly", "false");
	$("#formActualizarEscrituraConstitutiva #partida").attr("readonly", "false");
	$("#formActualizarEscrituraConstitutiva #volumen").attr("readonly", "false");
	$("#formActualizarEscrituraConstitutiva #foja").attr("readonly", "false");*/
	
	// limpiamos folioMercantil
	$("#formActualizarEscrituraConstitutiva #folioMercantil").val("");
	// inhabilitamos folioMercantil
	//$("#formActualizarEscrituraConstitutiva #folioMercantil").attr("readonly", "true");
}