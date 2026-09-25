var appletFirma;

$(document).ready(function() {
	
	appletFirma = document.getElementById("seguriDataApplet");
	
	$('#btnFirmar').live('click', function() {
		firmarIMSS();
	});
});

function selectTheFile(obj, title, filter, ext, read) {

	appletFirma.getFilePath(title, filter, ext, read);

	if (appletFirma.isSuccess()) {
		var thePath = appletFirma.getSelectedFileAndPath();
		if (thePath != "") {
			obj.value = thePath;
		}
	} else {
		procesa_excepcion(appletFirma.getErrorMessage());
		return;
	}
}

var firmarIMSS = function() {
	
	$('#msgErrorDiv').hide;
	
	// 1 = archivo, 2 = cadena de texto
	var opcion = 2;
	if($('#firmarArchivo').val() == 'true') { 
		opcion = 1;
	}
	
	
	var pkcs7 = "";
	var contenido = null;
	var pkcs12 = $('#cerFile').val();
	var passwd = $('#pwdInput').val();

	if (pkcs12 == "") {
		procesa_excepcion('certEmpty');
		return;
	} else if (passwd == "") {
		procesa_excepcion('pwdEmpty');
		return;
	}
	
	// Se valida el tipo de contenido
	if (opcion == 1) {
		contenido = $('#FILE_NAME');
		if (contenido.val() == "") {
			procesa_excepcion("fileToSignEmpty");
			contenido.focus();
			return;
		}
	} else if (opcion == 2) {
		current = $('#cadenaOriginal');
		if (current.val() == "") {
			procesa_excepcion('cadenaOriginalEmpty');
			return false;
		}
	}

	// Generacion del PKCS7
	pkcs7 = document.applets.applet_ssign.firma(pkcs12, passwd, contenido,'SHA1withRSA', opcion);

	if (pkcs7.indexOf("Error") == -1 && pkcs7.indexOf("Se produjo una excepci\u00f3n") == -1
			&& pkcs7.indexOf("Se produjo una excepcion") == -1) {

		// Se llama a los servicios de firma digital IMSS
		$('#sPKCS7').val(pkcs7);
		
		if(opcion == 1){
			var filename = contenido.val().replace(/^.*[\\\/]/, '');
			$('#fileNameToSign').val(filename);
		}
		
		$('#procesaFirmaForm').submit();
	} else {
		procesa_excepcion(pkcs7);
	}
};

var procesa_excepcion = function(exc) {
	var msg = '';

	if (exc.indexOf("java.io.IOException: DER input, Integer tag error") != -1) {
		if (($('#cerFile').val().indexOf(".pfx") != -1)
				|| ($('#cerFile').val().indexOf(".p12") != -1)
				|| ($('#cerFile').val().indexOf(".cer") != -1)) {
			msg = "El archivo PKCS12 est\u00e1 corrupto";
		} else {
			msg = "Por favor seleccione un archivo .p12 o .pfx";
		}
	} else if (exc.indexOf("java.io.IOException: failed to decrypt safe contents entry") != -1) {
		msg = " La contrase\u00f1a no es correcta, intente de nuevo ";
	} else if (exc.indexOf("Wrong password") != -1) {
		msg = " La contrase\u00f1a no es correcta, intente de nuevo ";
	} else if (exc.indexOf("java.io.FileNotFoundException") != -1) {
		msg = " La ruta " + exc.substr(55, exc.length - 99) + " no es v\u00e1lida ";
	} else if (exc.indexOf("certEmpty") != -1) {
		msg = "Por favor seleccione un archivo .p12 o .pfx o .cer";
	} else if (exc.indexOf("pwdEmpty") != -1) {
		msg = "Es necesario introducir la contrase\u00f1a";
	} else if (exc.indexOf("cadenaOriginalEmpty") != -1) {
		msg = "El mensaje a firmar es requerido";
	} else if (exc.indexOf("fileToSignEmpty") != -1) {
		msg = "Por favor seleccione el archivo que desea firmar";
	}

	$('#msgError').text(msg);
	$('#msgErrorDiv').show();
};