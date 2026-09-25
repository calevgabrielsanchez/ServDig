$(function() {
	
	$('#firmaElectronicaModel #certificado').live('change', function(){
		var rutaCert = $('#firmaElectronicaModel #certificado').val();
	});
	
	$('#firmaElectronicaModel #btnFirmar').live('click', function(){
		firmar();
	});
});


/*Funcion para agregar el elemento nuevo*/
var firmar = function(){

	var pkcs7="";
  	var contenido = $('#cadenaOriginal').val();
    var pkcs12 = $('#certificado').val();
    var passwd= $('#password').val();
    $('#password').val('');
	if(pkcs12 == "") {
        alert("Por favor seleccione un archivo .p12 o .pfx o .cer");
        return;
    }
	if(contenido == "" || passwd == "") {
        alert("Error al contactar al servidor");
        return;
    }
	
	if (contenido != "") {
        pkcs7 = document.applet_firma.firma(pkcs12,passwd,contenido,'SHA1withRSA',2);
        alert('El pkcs7 es: [' + pkcs7 + ']');
         
        if(pkcs7.indexOf("Se produjo una excepcion")==-1){
        	$('#sPKCS7').val(pkcs7);
        	$('#firmaElectronicaModel').submit();
        	
        } else {
            procesa_excepcion(pkcs7);
        }
    } else {
        alert("Seleccione el archivo");
    }
};

var procesa_excepcion = function(exc){
	var msg;
    if (exc.indexOf("java.io.IOException: DER input, Integer tag error") != -1 ){
        if( ( $('#certificado').val().indexOf(".pfx") != -1 ) || 
            ( $('#certificado').val().indexOf(".p12") != -1)  || 
            ( $('#certificado').val().value.indexOf(".cer") != -1)) {
            msg = "El archivo PKCS12 está corrupto";
        } else {
            msg = "Por favor seleccione un archivo .p12 o .pfx";
        }
    }
    if (exc.indexOf("java.io.IOException: failed to decrypt safe contents entry") != -1 ) {
        msg = " La contraseña no es correcta, intente de nuevo ";
    }
    if (exc.indexOf("java.io.FileNotFoundException") != -1 ) {
        msg = " La ruta " + exc.substr(55,exc.length-99) + " no es válida ";
    }
    alert(msg);
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
