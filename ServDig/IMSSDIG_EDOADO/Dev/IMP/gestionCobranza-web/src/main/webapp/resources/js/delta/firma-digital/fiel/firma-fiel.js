var appletFirma;

$(document).ready(function(){
	
	appletFirma = document.getElementById("seguriDataApplet");
	
	$('#btnFirmar').live('click', function() {
		firma();
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
	
function firma() {
	
	$('#msgErrorDiv').hide;
	
	var p7var = "";
	var current = null;
	
	// 1 = archivo, 2 = cadena de texto
	var opcion = 2;
	if($('#firmarArchivo').val() == 'true') { 
		opcion = 1;
	}
	
	try {
		if ($('#keyFile').val() == "") {
			procesa_excepcion('keyEmpty');
			return false;
		}

		if ($('#cerFile').val() == "") {
			procesa_excepcion('certEmpty');
			return false;
		}
		if ($('#keyPwd').val() == "") {
			procesa_excepcion('pwdEmpty');
			return false;
		}
		
		if (opcion == 1) {
			current = $('#FILE_NAME');
			if (current.val() != "") {
				appletFirma.setInputTextDataIs("FILE");
			} else {
				procesa_excepcion("fileToSignEmpty");
				current.focus();
				return;
			}
		} else if (opcion == 2) {
			current = $('#cadenaOriginal');
			if (current.val() != "") {
				appletFirma.setInputTextDataIs("TEXT");
			} else {
				procesa_excepcion('cadenaOriginalEmpty');
				return false;
			}
		}
		
		//appletFirma.setDoDetached(document.getElementById('doDetached').checked);
		
		// Generacion del PKCS12
		appletFirma.sign($('#cerFile').val(),$('#keyFile').val(), $('#keyPwd').val(), current.val());
		
		if (appletFirma.isSuccess()) {
			p7var = appletFirma.getSignedMessage();
		} else {
			p7var = appletFirma.getErrorMessage();
		}
		
		if (p7var.indexOf("Exception") != -1 || p7var.indexOf("Error") != -1 || p7var.indexOf("Invalid") != -1 || 
				p7var.indexOf("no puede encontrar") != -1 || p7var.indexOf("Key must not be null") != -1) {
			procesa_excepcion(p7var);
		} else {
			$('#sPKCS7').val(p7var);
			
			if(opcion == 1){
				var filename = current.val().replace(/^.*[\\\/]/, '');
				$('#fileNameToSign').val(filename);
			}
			
			$('#procesaFirmaForm').submit();
		}
	} catch (err) {
		procesa_excepcion(err.description == null ? (err.message == null ? err : err.message)
				: err.description);
	}
}

function procesa_excepcion(exc) {
	msg = exc;
	
	if (exc.indexOf("java.io.IOException: DER input, Integer tag error") != -1
			|| exc.indexOf("java.io.IOException: toDerInputStream") != -1) {
		msg = "El archivo PKCS12 está corrupto o no está codificado bajo dicho formato";
	} else if (exc.indexOf("java.io.IOException: failed to decrypt safe contents entry") != -1
			|| exc.indexOf("Given final block not properly padded") != -1) {
		msg = "La contrase\u00f1a del PKCS#12 no es correcta, intente de nuevo";
	} else if (exc.indexOf("java.security.NoSuchAlgorithmException") != -1) {
		msg = "El PKCS#12 se encuentra cifrado con algún algoritmo no soportado por los proveedores existentes";
	} else if (exc.indexOf("CKR_PIN_INCORRECT") != -1
			|| exc.indexOf("CKR_PIN_LEN_RANGE") != -1) {
		msg = "No fue posible tener acceso al dispositivo debido a un NIP incorrecto";
	} else if (exc.indexOf("CKR_FUNCTION_CANCELED") != -1) {
		msg = "La operación fue cancelada por el usuario";
	} else if (exc.indexOf("javax.crypto.BadPaddingException: Given final block not properly padded") != -1) {
		msg = "La contrase\u00f1a del PKCS#12 no es correcta, intente de nuevo";
	} else if (exc.indexOf("by zero") != -1) {
		msg = "La contrase\u00f1a del PKCS#12 no es correcta, intente de nuevo";
	} else if (exc.indexOf("java.security.cert.CertificateException") != -1
			|| exc.indexOf("java.io.IOException: DerInputStream") != -1) {
		msg = "El certificado digital parametrizado es incorrecto";
	} else if (exc.indexOf("file does not exist") != -1) {
		msg = "La ruta de archivo recibida no es correcta";
	} else if (exc.indexOf("java.io.EOFException") != -1) {
		msg = "Los parámetros recibidos no son correctos";
	} else if (exc.indexOf("PKCS11 not found") != -1) {
		msg = "La firma digital no fue bien integrada a esta aplicación o FF no se encuentra instalado";
	} else if (exc.indexOf("Invalid password") != -1) {
		msg = "La contrase\u00f1a no es correcta, intente de nuevo";
	} else if (exc.indexOf("no puede encontrar el archivo especificado") != -1) {
		msg = "El archivo " + exc.substr(0, exc.length - 56) + " no ha sido encontrado, intente de nuevo";
	} else if (exc.indexOf("no puede encontrar la ruta especificada") != -1) {
		msg = "La ruta del archivo " + exc.substr(0, exc.length - 53) + " no ha sido encontrada, intente de nuevo";
	} else if (exc.indexOf("certEmpty") != -1) {
		msg = "Es necesario seleccionar el archivo .cer";
	} else if (exc.indexOf("keyEmpty") != -1) {
		msg = "Es necesario seleccionar el archivo .key";
	} else if (exc.indexOf("pwdEmpty") != -1) {
		msg = "Es necesario introducir la contrase\u00f1a";
	} else if (exc.indexOf("cadenaOriginalEmpty") != -1) {
		msg = "El mensaje a firmar es requerido";
	} else if (exc.indexOf("Text to sign cannot be an empty string") != -1) {
		msg = "El mensaje a firmar es requerido";
	} else if (exc.indexOf("Key must not be null") != -1) {
		msg = "El archivo de la llave primaria no es v\u00e1lido";
	}  else if (exc.indexOf("fileToSignEmpty") != -1) {
		msg = "Por favor seleccione el archivo que desea firmar";
	}
	
	$('#msgError').text(msg);
	$('#msgErrorDiv').show();
}