/**
 * 
 */
STATIC_NOMBRE_RECIEN_NACIDO = 'RECIEN NACIDO';
STATIC_INSTRUCCIONES_PERSONA_NORMAL = 'Para ubicar a la persona de clic en el bot&oacute;n <strong>Buscar Persona</strong>.';
STATIC_INSTRUCCIONES_RECIEN_NACIDO = 'Introduzca los datos personales del reci&eacute;n nacido.';

//Objetos de los fieldset
F_DATOS_GRUPO = null;
F_DATOS_PERSONALES = null;
F_DATOS_DOMICILIO = null;
F_MEDIOS_CONTACTO = null;
//Objetos que representan a lso memnsahes
S_MENSAJES_PERSONA = null;

$(document).ready(function() {
	$.blockUI();
	
	$("#regresaraInicioTramite").click(regresarARegistro);
	
	if($("#irAConfirmacion").length > 0) {
		$("#irAConfirmacion").click(function() {
			$("#registro").on("submit",function(){$.blockUI();});
			$("#registro").submit();
		});
	}
	
	$.unblockUI();
});

/**
 * 
 * @returns
 */
var regresarARegistro = function(){
	
	$("#registro").on("submit",function(){$.blockUI();});
	$("#registro").attr("action","/${mvn.web.app.root}/tramite/registro/iniciarTramite");			          
	$("#registro").submit();
};
