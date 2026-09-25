var urlWizardTramite = '/${mvn.web.app.root}/wizard/actualizaRfc/';
var actionProcesarActualizacion = 'actualizar/';
var idformaAuxiliarActualizaRfc = 'formaAuxiliarActualizaRfc';
var idFormaActualizarRfc = 'fisicaForm';
var spanError = 'span.error';
var rfcInputTmp = 'rfc';
var rfcInputTmpError = 'rfcError';
var nClassShow = 'showElement';
var nClassHidden = 'hiddenElement';
var dialogoMensajeOper = 'dialogoMensajeOper';
var dialogoConfirmarOper = 'dialogoConfirmarOper';
var rfcFinal = "";
var rfcSinValor = "SIN_RFC";
var titulo = "Error en la actualizaci&oacute;n del RFC.";
var msgErrorDefault = 'Ocurri&oacute; un error al intentar actualizar el RFC.';
var erRfcFisica = '^(([A-Z]|[a-z]|\s){1})(([A-Z]|[a-z]){3})([0-9]{6})((([A-Z]|[a-z]|[0-9]){3}))';
var msgCampoRequeridoRFC = 'El RFC es requerido.';
var msgLongitudIncorrectaRFC = 'Es requerido un RFC de 13 posiciones.';
var msgFormatoIncorrectaRFC = 'El RFC no contiene la estructura correcta.';

$(document).ready(function(){
	
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
	
	$('#aceptaActualizarRfc').click(function(){
		iniciarWizardActualizarRfc();
	});
	
	$('#cancelarActualizarRfc').click(function(){
		cerrarWizardTramites();
	});
	
	rfcFinal = "";
});

function iniciarWizardActualizarRfc() {
	
	var rfcInput = $('#'+rfcInputTmp).val().trim();
	var validarRfc = rfcInput.length > 0 ? true : false;
	var contieneRfc = rfcInput.length > 0 ? true : false;
	var esRequerido = ''+$('#rfcRequeridoHidden').val() == "false" ? false : true;
	
	fnHideErrores('#'+idformaAuxiliarActualizaRfc);
		
	//console.log("rfcInput: " + rfcInput);
	//console.log("validarRfc: " + validarRfc);
	//console.log("contieneRfc: " + contieneRfc);
	//console.log("esRequerido: " + esRequerido);
	
	//NO requerido y NO capturado 
	if((esRequerido==false) && (contieneRfc==false)){
		//No se valida requerido, longitud ni formato.
		rfcFinal = rfcSinValor;
		procesarActualizacion(rfcFinal);				
	}else{
		//Validar RFC
		if(validarRfc){
			var huboError = false;		
			rfcFinal = rfcInput;
			
			if(rfcFinal.length < 13){
				huboError = true;
				fnShowError('#'+rfcInputTmpError , msgLongitudIncorrectaRFC);
			}else{
				var validRfc=new RegExp(erRfcFisica);
				var matchArray=rfcFinal.match(validRfc);
				if (matchArray==null) {
					huboError = true;
					fnShowError('#'+rfcInputTmpError , msgFormatoIncorrectaRFC);
				}
			}
			if (!huboError) {
				rfcFinal = rfcFinal.toUpperCase();
				procesarActualizacion(rfcFinal);
			}
		}else{
			huboError = true;
			fnShowError('#'+rfcInputTmpError , msgCampoRequeridoRFC);
		}
	}
	
}

function procesarActualizacion(rfc) {
	//console.log("procesarActualizacion RFC=" + rfc);
	var idPersonaHidden = $('#idPersonaHidden').val();
	var url = urlWizardTramite + actionProcesarActualizacion + idPersonaHidden + '/' + rfc;
	prepararRequest(url, null, true, concluirActualizacionRfc);	
}

function concluirActualizacionRfc(response) {	
	
	if (response.mensaje != undefined && response.mensaje != null) {
		if( rfcFinal == rfcSinValor){
			rfcFinal=null;			
		}
		parent.WizardActualizaRfcCtrl.setRfc(rfcFinal);
		parent.WizardActualizaRfcCtrl.concluirActualizarRfc();
	}else if (response.error != undefined
			&& response.error != null) {
		parent.WizardActualizaRfcCtrl.setRfc(null);
		var mensajeError = "";
		if (response.error == "") {
			mensajeError = msgErrorDefault;
		} else {
			mensajeError = response.error;
		}
		construirDialogoMensajes(titulo, mensajeError, true, undefined, 200, 600);
	}else{
		parent.WizardActualizaRfcCtrl.setRfc(null);
		construirDialogoMensajes(titulo, msgErrorDefault, true, undefined, 200, 600);
	}
	

}

function cerrarWizardTramites(){
	//Cierra el dialogo para actualizar RFC, y el wizard del tramite
	parent.WizardActualizaRfcCtrl.cancelarDialogo();
}

function cerrarWizard(){
	//Cierra el dialogo para actualizar RFC
	parent.WizardActualizaRfcCtrl.cerrar();
}

function fnHideErrores(contenedor) {

	var filtroErrores;
	if (contenedor == "") {
		filtroErrores = spanError;
	} else {
		filtroErrores = contenedor + spanError;
	}

	$(filtroErrores).each(function(index) {

		$(this).removeClass(nClassShow);
		$(this).addClass(nClassHidden);
		$(this).text();
	});
}

function fnShowError(idCampoError, mensajeError) {
	$(idCampoError).removeClass(nClassHidden);
	$(idCampoError).addClass(nClassShow);
	$(idCampoError).text(mensajeError);
}

function prepararRequest(sSource, data, async, callback) {
	var request = $.ajax({
		url : sSource,
		async : async,
		type : "POST",
		data : data ? JSON.stringify(data) : null,
		dataType : "json",
		contentType : "application/json; charset=utf-8"
	});
	request.done(callback);
	request.fail(callback);
}

function construirDialogoMensajes(titulo, mensaje, error, callback, height, width) {
	if(height==undefined)
		height='auto';
	if(width==undefined)
		width=400;
		
	$('#'+dialogoMensajeOper).html(mensaje);
	$('#'+dialogoMensajeOper).removeAttr("style");
	if (error) {
		$('#'+dialogoMensajeOper).attr("style", "color: red;");
	} else {
		$('#'+dialogoMensajeOper).attr("style", "color: black;");
	}
	var dialogo = $('#'+dialogoConfirmarOper).dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : height,
		width : width,
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
	dialogo.dialog('open');
}