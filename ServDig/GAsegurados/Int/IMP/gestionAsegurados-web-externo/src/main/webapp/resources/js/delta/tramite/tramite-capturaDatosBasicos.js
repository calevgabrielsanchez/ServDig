$(document).ready(function() {

	//Boton de limpiar formulario
	$('#limpiar').click(function(event) {
		fnHideErrores('form#registroAseguradoDatosBasicosForm');
		$('form#registroAseguradoDatosBasicosForm').clearForm();
		$('div#errorWrapperAux').hide();
		verificarErrores();
	});

	$('input#correoConfirmacionInput').bind("cut copy paste", function(e) {
		$('div#errorWrapperAux').show();
		$("#correoConfirmacionInput").css("border","1px solid red")
		fnShowError('span#mailConfirmacionErrors' , 'No se permite copiar-pegar para este campo, es necesario que se capture.');		
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
	verificarErrores();
});

function loadCaptcha() {
	var date = new Date();
	$("#captchaImg").attr("src", context_path + "/servlet/CaptchaServlet?" + date.getTime());
}

function verificarErrores() {
	marcarCamposConErrores("registroAseguradoDatosBasicosForm",".error",".col-sm-6");
}