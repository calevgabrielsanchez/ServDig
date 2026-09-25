var oDialogoFirma;

$(function() {
	$("#divBotonesFirma").hide();
	$("#divContrasenia").hide();
	$("#divUser").hide();
	
	$('#firmaElectronicaModel #certificado').live('change', function(){
		var rutaCert = $('#firmaElectronicaModel #certificado').val();
	});
	
	$('#firmaElectronicaModel #btnFirmar').live('click', function(){
		firmar();
	});
	
	oDialogoFirma = $('#dialogoFirma').dialog({
		autoOpen:false,
		resizable: false,
		modal: true,
		width: 500,
		buttons: {
			 
			"Ok":function(){
				$( this ).dialog( "close" );
			}
		}
		
		
	});
});

var firmar = function(){
	var tipoFirma=$("#tipoFirma").val();
	var pkcs7="";
	var usuario = $('#usuario').val();
  	var contenido = $('#cadenaOriginal').val();
    var pkcs12 = $('#certificado').val();
    var passwd= $('#password').val();           
		
	if (tipoFirma==1){
		if(contenido == "" || passwd == "" || usuario == "") {
			$('#dialogoFirma').text("Por favor llene los datos requeridos");
			if (contenido == "")
				$('#dialogoFirma').text("No se pudo generar la cadena original. Intente más tarde");
			oDialogoFirma.dialog('open');
			return;
		}
		else		
			firmarNPIE(contenido,passwd,usuario,pkcs7,pkcs12);
	}	
	
	if (tipoFirma==2){
		$("#divUser").hide();
		firmarFIEL();	
	}	
};

function firmarNPIE(contenido,passwd,usuario,pkcs7,pkcs12){
	//alert("El pkcs12 es:"+pkcs12);
	//var contenido_origen = document.applet_secuencia.obtenSecuencial(document.firmaElectronicaModel.siteId.name, document.firmaElectronicaModel.siteId.value, document.location);	
	
	if (contenido != "") {	 
		pkcs7="DASHDKJHDKJCRELJFREJFLERKJFLERTRETERVBBVKJFLERKJFLKREJFLKREFERFREFREGVFDSFWERFDSFERWFSDJFKLERJFLKREJFLKREJFLKREJFKLFJKL";
		//pkcs7 = document.applet_ssign.firma(pkcs12, passwd, contenido, "SHA1withRSA", 2);				        
        $('#dialogoFirma').text('El pkcs7 es: [' + pkcs7 + ']');
		oDialogoFirma.dialog('open');
        	
        if(pkcs7.indexOf("Se produjo una excep")==-1){	
        	$('#sPKCS7').val(pkcs7);
			document.firmaElectronicaModel.action=document.firmaElectronicaModel.action+"procesoFirmaDigitalNPIE";
        	$('#firmaElectronicaModel').submit();
        	
        } else {
            procesa_excepcion(pkcs7);
        }
    } else { 
    		$('#dialogoFirma').text("Seleccione el archivo");
    		oDialogoFirma.dialog('open');
    }
}

function firmarFIEL(){

}

var procesa_excepcion = function(exc){
	var msg;
    if (exc.indexOf("java.io.IOException: DER input, Integer tag error") != -1 ){
        if( ( $('#certificado').val().indexOf(".pfx") != -1 ) || 
            ( $('#certificado').val().indexOf(".p12") != -1 )  || 
            ( $('#certificado').val().value.indexOf(".cer") != -1) ) {
            msg = "El archivo PKCS12 est\u00e1 corrupto";
        } else {
            msg = "Por favor seleccione un archivo .p12 o .pfx";
        }
    }
    if (exc.indexOf("java.io.IOException: failed to decrypt safe contents entry") != -1 ) {
        msg = " La contrase\u00f1a no es correcta, intente de nuevo ";
    }
    if (exc.indexOf("java.io.FileNotFoundException") != -1 ) {
        msg = " La ruta " + exc.substr(55,exc.length-99) + " no es v\u00e1lida ";
    }    
    $('#dialogoFirma').text(msg);
	oDialogoFirma.dialog('open');	
};

var seleccionaCert = function (certificado) {
	   var certificadoParse;
};


/*
 * funciones para navegacion
 */
function fnFirmaGoBack(){
	$("#form-firma-back").submit();
}

function getTipoCertificado(){
	$("#divBotonesFirma").hide();
	$("#divContrasenia").hide();
	$("#divUser").hide();
	var tipoCertificado=validarTipoCertificado($('#certificado').val());
	if (tipoCertificado==0){
		$('#dialogoFirma').text("Por favor seleccione un archivo! .p12 o .pfx o .cer");
		$("#tituloFirma").html("");
		$('#certificado').val("");
		oDialogoFirma.dialog('open');
        return;
	}
	if (tipoCertificado==1){		
		$("#tipoFirma").val("1");
		$("#tituloFirma").html("Firma NPIE");
		$("#divUser").show();
	}
	if (tipoCertificado==2){
		$("#tipoFirma").val("2");
		$("#tituloFirma").html("Firma FIEL");		
	}
	$("#divContrasenia").show();	
	$("#divBotonesFirma").show();	
}

function validarTipoCertificado(file){
	var extArray = new Array(".pfx", ".cer", ".p12"); 
	var certificadoValido = 0; 
	if (!file) return; 
	while (file.indexOf("\\") != -1) 
		file = file.slice(file.indexOf("\\") + 1); 
	ext = file.slice(file.indexOf(".")).toLowerCase(); 
	for (var i = 0; i < extArray.length; i++) { 
		if (extArray[i] == ext) {				
			certificadoValido = i+1; 				
			break; 
		} 
	} 
	return certificadoValido;	
}