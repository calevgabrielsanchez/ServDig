
$(document).ready(function() {

	// Se incializa el blockUI para las peticiones AJAX
	//$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
	
	$("#btnIngresar").on("click",function(){validaLoginForm() == true? fnOnLoginInternetReturn() : "" });
	
});

var regexCURP = /^[A-Za-z]{4}\d{6}((H|M)|(h|m))[A-Za-z]{2}[A-Za-z]{3}\w{1}\d{1}$/;

var regexEmail = /^[^@]+@[^@]+\.[a-zA-Z]{2,}$/;


function validaLoginForm() {
	console.log("::: En validaLoginForm()");
	$.blockUI();
	
	var curp = $("#curp").val();
	var email = $("#email").val();
	var confirmemail = $("#confirmemail").val();
	var regPat = $("#registroPatronal").val();
	var captcha = grecaptcha.getResponse()
	
	
	if( $("#registroPatronal").val() == '' || $("#confirmemail").val() == '' || $("#email").val() == '' || $("#curp").val() == '' || captcha == '' ){
		construirDialogoMensajes("Error", "Campo requerido", true,  undefined, undefined, undefined);
		if( $("#registroPatronal").val() == ''){
			$("#registroPatronal").attr("style","border-color: red !important;");
		} else{
			$("#registroPatronal").attr("style","border-color: black !important;");
		}
		if( $("#confirmemail").val() == ''){
			$("#confirmemail").attr("style","border-color: red !important;");
		} else{
			$("#confirmemail").attr("style","border-color: black !important;");
		}
		if( $("#email").val() == ''){
			$("#email").attr("style","border-color: red !important;");
		} else{
			$("#email").attr("style","border-color: black !important;");
		}
		if( $("#curp").val() == ''){
			$("#curp").attr("style","border-color: red !important;");
		} else{
			$("#curp").attr("style","border-color: black !important;");
		}
		if(captcha == '' ){
			$("#captchaRequired").attr("style","color: red !important;");
		}else{
			$("#captchaRequired").attr("style","color: black !important;");
		}
		return false;
	} 
	
	if( !regexCURP.test(curp)){
		$("#curp").attr("style","border-color: red !important;");
		construirDialogoMensajes("Error", "El formato del campo CURP no es v&aacute;lido", true,  undefined, undefined, undefined);
		return false;
	}else{
		$("#curp").attr("style","border-color: black !important;");
	}

	if( !regexEmail.test(email)){
		$("#email").attr("style","border-color: red !important;");
		construirDialogoMensajes("Error", "El correo ingresado no cumple con la estructura general, nombreusuario@dominio", true,  undefined, undefined, undefined);
		return false;
	}else{
		$("#email").attr("style","border-color: black !important;");
	}

	if( !regexEmail.test(confirmemail) || $("#email").val() != $("#confirmemail").val()){
		$("#confirmemail").attr("style","border-color: red !important;");
		construirDialogoMensajes("Error", "El correo de confirmaci&iacute;n no es v&aacute;lido", true,  undefined, undefined, undefined);
		return false;
	}else{
		$("#confirmemail").attr("style","border-color: black !important;");
	}

	if( !(regPat.length==10 || regPat.length==11)){
		$("#registroPatronal").attr("style","border-color: red !important;");
		construirDialogoMensajes("Error", "El formato del campo no es v&aacute;lido", true,  undefined, undefined, undefined);
		return false;
	}else{
		$("#registroPatronal").attr("style","border-color: black !important;");
	}	

	if(captcha == '' ){
		construirDialogoMensajes("Error", "Campo requerido", true,  undefined, undefined, undefined);
		$("#captchaRequired").attr("style","color: red !important;");
		$("#rc-anchor-container").attr("style","border-color: red !important;");
		return false;
	}else{
		$("#captchaRequired").attr("style","color: black !important;");
	}
	
	return true;
}




function construirDialogoMensajes(titulo, mensaje, error, callback, pheight, pwidth) {
	if(pheight==undefined)
		pheight='auto';
	if(pwidth==undefined)
		pwidth=400;

	$("#textoMensaje").html(mensaje);
	$("#textoMensaje").removeAttr("style");
	if (error) {
		$("#textoMensaje").attr("style", "color: red;");
	} else {
		$("#textoMensaje").attr("style", "color: black;");
	}
	var dialogo = $("#dialogoMensajes").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : pheight,
		width : pwidth,
		title : titulo,
		buttons : {
			"Aceptar" : function() {
				if (jQuery.isFunction(callback)) {
					callback();
				}
				$(this).dialog("close");
			}
		}
	});
	$.unblockUI();
	dialogo.dialog('open');
}


var fnOnLoginInternetReturn = function(){

	var curp = $("#curp").val();
	var email = $("#email").val();
	var regPat = $("#registroPatronal").val();
	$.blockUI();
	var sSource = context_path + '/movPat/internet/validarAcceso?curp='+curp+'&email='+email+'&registroPatronal='+regPat;
	setTimeout(function(){
			prepararRequest(sSource, null, false, callbackValidaAccesoInternet);
	}, 500);

}

function callbackValidaAccesoInternet(response){
	if (response.mensajeError != undefined
				&& response.mensajeError != null) {
			titulo = "Alerta";
			error = true;
			mensaje = response.mensajeError;
			construirDialogoMensajes(titulo, mensaje, error, undefined, undefined, undefined);
			//$.unblockUI();
	}else{
			var sSource = context_path + '/movPat/internet/home';
			window.location.href = sSource;
	}

}

function prepararRequest(sSource, data, async, callback) {
	var request = $.ajax({
		url : sSource,
		async : async,
		type : "POST",
		data : data ? JSON.stringify(data) : null,
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		success: function() {
                $.unblockUI();
            }
	});
	request.done(callback);
	request.fail(callback);
}

function salirLogin() {
	location.href = "http://www.imss.gob.mx/";
}

