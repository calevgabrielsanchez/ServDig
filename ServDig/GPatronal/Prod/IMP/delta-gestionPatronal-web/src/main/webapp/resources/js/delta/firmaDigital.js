var oDialogoFirma;

$(function() {
	
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


/*Funcion para agregar el elemento nuevo*/
var firmar = function(){

	var pkcs7="";
  	var contenido = $('#cadenaOriginal').val();
    var pkcs12 = $('#certificado').val();
    var passwd= $('#password').val();           
	if(pkcs12 == "") {
        //alert("Por favor seleccione un archivo .p12 o .pfx o .cer");
		$('#dialogoFirma').text("Por favor seleccione un archivo! .p12 o .pfx o .cer");
		oDialogoFirma.dialog('open');
        return;
    }
	if(contenido == "" || passwd == "") {
        //alert("Error al contactar al servidor");
		$('#dialogoFirma').text("Error al contactar al servidor");
		oDialogoFirma.dialog('open');
        return;
    }
	//alert("El pkcs12 es:"+pkcs12);
	if (contenido != "") {
		pkcs7="DASHDKJHDKJCRELJFREJFLERKJFLERKJFLERKJFLKREJFLKREFERFREFREGVFDSFWERFDSFERWFSDJFKLERJFLKREJFLKREJFLKREJFKLFJKL";
        //pkcs7 = document.applets.applet_firma.firma(pkcs12,passwd,contenido,'SHA1withRSA',2);                        
        $('#dialogoFirma').text('El pkcs7 es: [' + pkcs7 + ']');
		oDialogoFirma.dialog('open');         
        if(pkcs7.indexOf("Se produjo una excepci\u00f3n")==-1){
        	$('#sPKCS7').val(pkcs7);        	
        	$('#firmaElectronicaModel').submit();        	
        } else {
            procesa_excepcion(pkcs7);
        }
    } else { 
        //alert("Seleccione el archivo");
    	$('#dialogoFirma').text("Seleccione el archivo");
 		oDialogoFirma.dialog('open');
    }
};

var procesa_excepcion = function(exc){
	var msg;
    if (exc.indexOf("java.io.IOException: DER input, Integer tag error") != -1 ){
        if( ( $('#certificado').val().indexOf(".pfx") != -1 ) || 
            ( $('#certificado').val().indexOf(".p12") != -1 )  || 
            ( $('#certificado').val().value.indexOf(".cer") != -1)) {
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
//    alert(msg);
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