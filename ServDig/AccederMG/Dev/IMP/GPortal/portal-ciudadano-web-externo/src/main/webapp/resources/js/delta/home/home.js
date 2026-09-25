$(document).ready(function() {

	$('#limpiar').click(function(event) {
		limpiarFormulario();
		verificarErrores();
	});

	$('input#correoConfirmacionInput').bind("cut copy paste", function(e) {
		$('div#errorWrapperAux').show();
		$("#correoConfirmacionInput").css("border","1px solid red");
		fnShowError('span#mailConfirmacionErrors' , 'No se permite COPIAR-PEGAR para este campo, es necesario que se capture');		
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

	$("#buscar").click(function(){
		validarTerminos();
	});
	
	verificarErrores();
	
	setEventosChecarError("registroAseguradoDatosBasicosForm",verificarErrores,"Error");
});

function limpiarFormulario(){
	fnHideErrores('form#registroAseguradoDatosBasicosForm');
	$('form#registroAseguradoDatosBasicosForm').clearForm();
	$('div#errorWrapperAux').hide();
}

function loadCaptcha() {
	var date = new Date();
	$("#captchaImg").attr("src", context_path + "/servlet/CaptchaServlet?" + date.getTime());
}


function validarTerminos(){
	$.blockUI();
	var url = contextpath +"/home/terminos";
	var curp = $("#registroCurp").val();
	var correo = $("#correoInput").val();
	var correoConfirmacion = $("#correoConfirmacionInput").val();
	var strCaptcha = $("#strCaptcha").val();

	$.post(url , {"curp":curp, "correo":correo, "correoConfirmacion":correoConfirmacion, "captcha":strCaptcha}, function(data){
			$.unblockUI();
			if($.parseJSON(data)){
				CartaTerminosCtrl.init('divCartaTerminos', tramiteModifDatosGrales);
				CartaTerminosCtrl.setOnAceptCallback(aceptarCartaTerminos);
				CartaTerminosCtrl.setOnCancelCallback(rechazarCartaTerminos);
				CartaTerminosCtrl.abrir();
			}else{
				submitFormulario(true);
			}
	});
}

function aceptarCartaTerminos() {
	submitFormulario(true);
}

function rechazarCartaTerminos() {
	submitFormulario(false);
}

function submitFormulario(terminos){
	$("#terminos").val(terminos);
	$("#registroAseguradoDatosBasicosForm").submit();
}

function verificarErrores() {
	marcarCamposConErrores("registroAseguradoDatosBasicosForm",".error",".col-sm-6");
}