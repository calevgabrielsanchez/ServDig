var sIdDialogActualizarNombreComercial = "#numFolioSolicitud"
var oDialogActualizarNombreComercial;

/** Seccion de codigo a ejectuar cuando el DOM este listo * */
$(function() {
	/* Configuracion del dialogo de actualizacion exitosa */
	oDialogActualizarNombreComercial = $(
			sIdDialogActualizarNombreComercial).dialog( {
		autoOpen : false,
		resizable : true,
		modal : true,
		height : 150,
		width : 350,
		buttons : {
			"Aceptar" : function() {
				$(this).dialog('close');
				$("#razonSocialForm").submit();
			}
		}
	});
});

function actualizarNombreComercial(accion) {
	fnHideErrores('#patronForm');	
	/*recuperamos los valores del form*/
    var objForm = $("#razonSocialForm").toObject(true);
    sSource = context_path + "/sujetoObligado/actualizarRazonDenominacionSocial/"+$("#idSolicitud").val();
    $.postJSON(sSource, objForm, function(data) {
    	switch(accion){
    		case 'activar':
    						$("#formaAcuse").submit();
							dialogoTramiteRazonSocial.dialog("close");
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
    						oDialogActualizarNombreComercial.dialog("open");
    						break;
    	}
		
	}).error(function(data) {
		fnProcesarErrores(data, '#divActualizarNombreComercial');
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
		height: 250, width: 350,
		title : titulo,
		buttons: {"Aceptar" : function(){
			$( this ).dialog( "close" );
			history.back();}}
	});
	dialogo.dialog('open');
}