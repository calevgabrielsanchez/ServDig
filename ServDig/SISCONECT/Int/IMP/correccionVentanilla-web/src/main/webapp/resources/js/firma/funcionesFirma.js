var idDgFirma = "#dgFirma";

var oDgFirma;
var firmaDigitalResp = ";" // variable donde se almacena pkcs7

$(document).ready(function() {

	// Dialog Firma			
	oDgFirma = $(idDgFirma).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 930,
		beforeClose :function(event,ui){
		},
		buttons: {
			"Guardar": function() {
				firmaDigitalResp = firmar();
				if (firmaDigitalResp!= "") {
					registrarFirmado(); //funcion implementada por el programador
					$(this).dialog("close");
				}	
			}, 
			"Cancelar": function() { 
				$(this).dialog("close"); 
			} 
		}
	});
 

});//$(document).ready(function()

function abrirDialogoFirma(){
	oDgFirma.dialog('open');
}

function firmar() {
	var cert = document.forms.formaFirma.certificado.value;
	var pwd = document.forms.formaFirma.contrasena.value;
	var co = getCadenaOriginal(); // funcion implementada por el programador
//	alert(co);
	var tipoCert = document.forms.formaFirma.tipoCert.value;
	if (cert=="") {
		alert('Por favor ingrese el Certificado');
		return "";
	}
	if (pwd=="") {
		alert('Por favor ingrese la Contrase\u00F1a');
		return "";
	}
	var fd = "";
	fd = SignSat(co, cert, pwd);
	/*
	if ((tipoCert == "" || tipoCert == "SAT") && cert.indexOf(".cer") != -1 ) {
		fd = SignSat(co, cert, pwd);
	} else {
		fd = SignIMSS(co, cert, pwd);
	}
	*/
	
	$("#certificadoMsg").html('');
	if (fd != ""){
		if (!validarCertificadoPatron(fd)) {
	//		alert('Debe usar el certificado con el que inici\u00F3 sesi\u00F3n');
			alert('Debe usar el certificado del SAT');
			return "";
		}
	}
	document.forms.formaFirma.certificado.value = '';
	//$("#certificado").val('');
	document.forms.formaFirma.contrasena.value = '';
	document.forms.formaFirma.llave.value = '';
	return fd;
}

function validarCertificadoPatron(pkcs7In){
	var noCertPat = document.forms.formaFirma.noCert.value;
	var resp = true;
	var errorOcurrido = "";
	if (noCertPat != "") {
		//invocar ajax
		$.ajax({
	        url: getAppContextParaJS()+"/firma/validaSerial.do",
	        async:false,
	        contentType: "application/json",
	        data: "pkcs7="+pkcs7In,
	        error: function(objeto, quepaso, otroobj){
	        	errorOcurrido = otroobj;
	        	resp = false;
	        },
	        success: function(datos){
	        	resp = datos;
	        },
	        type: "GET"
	});
	}
	if (errorOcurrido=="Forbidden")  {
		alert('Su sesi\u00F3n ha expirado.');
		window.location.reload(true);
	}
	return resp;
}

// Funciones proporcionadas por equipo de Yolanda Ovalle
function SignSat(contenido, pkcs12, passwd) {
    var pkcs7 = "";
    var keyPath = document.forms.formaFirma.llave.value;
    if (contenido != ""){
    	if (keyPath=="") {
    		alert('Por favor ingrese la Llave SAT');
    		return "";
    	}
        pkcs7 = document.applet_sat.firma(keyPath, pkcs12, passwd, contenido, "SHA1withRSA", 2);
        if(pkcs7.indexOf("Error")==-1){
            if( document.applet_sat.getIsEmptyMessage() == true ){
                window.alert( "El mensaje no tiene contenido" );
            } else {
                return pkcs7;
            }
        } else{
           // habilitaBoton(true);
            procesaExcepcionSAT(pkcs7);
            return "";
        }
    } else {
        alert("Error al contactar al servidor");
        //habilitaBoton(true);           
        return "";
    }
    return pkcs7;
}

function procesaExcepcionSAT(exc){
    var msg = exc;
    
    /** ERROR EN EL ARCHIVO *.key */
    if ( exc.indexOf( "java.io.IOException: DER input, Integer tag error" ) != -1 ) {
        if(( document.firmaForm.llave.value.indexOf(".key") != -1 ) ) {
            msg = "El archivo de llave privada est\u00E1 corrupto o no es procesable";
        } else {
            msg = "El archivo seleccionado para la llave privada no corresponde al formato";
        }
    } 
    
    /** MENSAJE DE ERROR EN EL ARCHIVO *.cer */    
    if ( exc.indexOf(     "java.security.cert.CertificateException: Unable to initialize, "+
                        "java.io.IOException: DerInputStream.getLength(): lengthTag=127, too big.") != -1 ) {
        if( ( document.firmaForm.certificado.value.indexOf(".cer") != -1 ) ) {
            msg = "El archivo del certificado est\u00E1 corrupto o no es procesable";
        } else {
            msg = "El archivo seleccionado para el certificado no corresponde al formato";
        }
    } 
    
    /** MENSAJE DE ERROR EN EL ARCHIVO *.cer */
    if ( exc.indexOf( "java.io.IOException: DER input, Integer tag error" ) != -1 ) {
        if( ( document.firmaForm.certificado.value.indexOf(".cer") != -1 ) ) {
            msg = "El archivo del certificado est\u00E1 corrupto o no es procesable";
        } else {
            msg = "Por favor seleccione un archivo .cer";
        }
    } 
    
    /** Mensaje de contrase\u00F1a incorrecta */
    if ( exc.indexOf( "java.io.IOException: failed to decrypt safe contents entry" ) != -1 ){
        msg = " La contrase\u00F1a no es correcta, intente de nuevo ";
    }
    
    /** Mensaje de ruta de archivo inv‡lida */
    if (exc.indexOf("java.io.FileNotFoundException") != -1 ) {
        msg = " La ruta " + exc.substr(55, exc.length-99) + " no es v\u00E1lida ";
    }
    alert( msg );
}

function SignIMSS(contenido, pkcs12, passwd){
    var pkcs7 = "";
    if (contenido != ""){
        pkcs7 = document.applet_imss.firma(pkcs12, passwd, contenido, "SHA1withRSA", 2);
        if(pkcs7.indexOf("Se produjo una excepcion")==-1){
            return pkcs7;
        } else{
            procesa_excepcion(pkcs7);
            //habilitaBoton(true);           
            return "";
        }
    } else{
        alert("Error al contactar al servidor");
        //habilitaBoton(true);           
    }
    return pkcs7;
}
    
function procesa_excepcion(exc){
    var msg = exc;
    if (exc.indexOf("java.io.IOException: DER input, Integer tag error") != -1 ){
        if(( document.forms.formaFirma.certificado.value.indexOf(".pfx") != -1 ) || (document.forms.formaFirma.certificado.value.indexOf(".p12") != -1) ){
            msg = "El archivo PKCS12 est\u00E1 corrupto";
        }
        else{
            msg = "Por favor seleccione un archivo .p12 o .pfx";
        }
    } 

    if (exc.indexOf("java.io.IOException: failed to decrypt safe contents entry") != -1){
        msg = " La contrase\u00F1a no es correcta, intente de nuevo ";
    }
    if (exc.indexOf("java.io.FileNotFoundException") != -1 ){
        msg = " El archivo no existe o se modific\u00F3 la ruta del archivo. Por favor verifique y actualice la informaci\u00F3n ";
    }
    alert(msg);
}
