var sIdDialogActualizarRegistroSindicato = "#dgActualizarRegistroSindicato"
var oDialogActualizarRegistroSindicato;

/** Seccion de codigo a ejectuar cuando el DOM este listo * */
$(function() {
	/*Fecha de regsitro de sindicato*/
	$("#txtFechaRegistroEdicion").datepicker({
		maxDate: "+0D",
		showOn: "button",
		buttonImage: context_path + "/static/resources/imagenes/calendar.gif",
		buttonImageOnly: true,
		dateFormat: "dd/mm/yy",
		changeMonth: true,
		changeYear: true,
		yearRange: '-112:+0'
	});
	/* Configuracion del dialogo de actualizacion exitosa */
	oDialogActualizarRegistroSindicato = $(
			sIdDialogActualizarRegistroSindicato).dialog( {
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
	
	$("#numReferenciadocRegistro").keydown(soloNumeros);
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

function actualizarRegistroSindicato(accion) {
	fnHideErrores('#formActualizarRegistroSindicato');	
	$("#fechaRegistro:hidden").val($("#txtFechaRegistroEdicion").val());
	/*recuperamos los valores del form*/
    var objForm = $("#formActualizarRegistroSindicato").serializeObject(true);
    sSource = context_path + "/sujetoObligado/actualizarRegistroSindicato/"+$("#idSolicitud").val();
    $.postJSON(sSource, objForm, function(data) {
		/*Actualizamos datos*/
    	//oDialogActualizarRegistroSindicato.dialog("open");
    	    	
    	switch(accion){
    		case 'activar':
    						$("#formaAcuse").submit();
    						dialogoTramiteSiondicato.dialog("close");
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
    						oDialogActualizarRegistroSindicato.dialog("open");
    						break;
    	}
    	
	}).error(function(data) {
		fnProcesarErrores(data, '#divActualizarRegistroSindicato');
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
