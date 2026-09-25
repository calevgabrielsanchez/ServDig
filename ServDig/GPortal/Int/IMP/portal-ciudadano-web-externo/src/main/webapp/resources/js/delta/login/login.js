$(document).ready(function() {

	$('input#correoConfirmacionInput').bind("cut copy paste", function(e) {
		$('div#errorWrapperAux').show();
		fnShowError('span#mailConfirmacionErrors' , 'No se permite copiar-pegar para este campo, es necesario que se capture');		
		e.preventDefault();
	});
	
	/*
	 * Funcion que convierte en MAYUSCULAS el valor 
	 * de campo CURP al perder el foco 
	 */
	$('input[type="text"]#registroCurp').blur(function() {
		$(this).val($(this).val().toUpperCase());
	});
	
	$('#refreshCaptcha').bind('click', function(){
		loadCaptcha();
	});
	
	loadCaptcha();

	$("#continuar").click(function(){
		submitFormulario();
	});
	
	verificarErrores();
	//se agregan eventos para quitar errores
	setEventosChecarError2("registroAseguradoDatosBasicosForm",verificarErrores,".errors");
});

function loadCaptcha() {
	var date = new Date();
	$("#captchaImg").attr("src", context_path + "/servlet/CaptchaServlet?" + date.getTime());
}

function submitFormulario(){
	$("#registroAseguradoDatosBasicosForm").submit();
}

function verificarErrores() {
	marcarCamposConErrores("registroAseguradoDatosBasicosForm",".error","div");
}

function setEventosChecarError2(idFormulario,functionPintarErrores,cadenaConcatenar) {
	$("#"+idFormulario+" :text").change(function() {
		verificarPersistenciaDeError2(this, false,functionPintarErrores,cadenaConcatenar);
	});
	
	$("#"+idFormulario+" select").change(function() {
		verificarPersistenciaDeError2(this, true,functionPintarErrores,cadenaConcatenar);
	});
	
	$("#"+idFormulario+" textarea").change(function() {
		verificarPersistenciaDeError2(this, false,functionPintarErrores,cadenaConcatenar);
	});
}

function verificarPersistenciaDeError2(element, isSelect, functionErrores,cadenaConcatenar) {
	var elementId = element.id;
    var elementName = element.name;
    var errorId = cadenaConcatenar != undefined && cadenaConcatenar != null  ? cadenaConcatenar : "Error";
    if(elementName == "captcha") {
    	elementName="errorFormGeneral";
    }
    
    var idElementError= elementName+""+errorId;
    var elementoError = document.getElementById(idElementError);
    //verificamos si existe el mensaje de error
    if(elementoError != undefined) {
    	//obtenemos la referencia al elemento del error
    	var $elementoError = $(elementoError);
    	//verificamos si persiste el error
    	var errorSolucionado = isSelect ? element.selectedIndex > 0 : $.trim(element.value).length > 0;
    	//si el elemento es visible y ya no hay error ocultamos el mensaje, y corremos la validacion de errores
    	if($elementoError.is(":visible") && errorSolucionado) {
    		$elementoError.html("");
    		$elementoError.removeClass("showElement").addClass("hiddenElement");
    		//verificamos si ejecutamos la funcion
    		if(functionErrores != undefined && functionErrores != null && $.isFunction(functionErrores)) {
    			functionErrores();
			}
    	}
    }
}