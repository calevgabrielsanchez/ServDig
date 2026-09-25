console.log("Ejecuto codigo D"); 
var avance = "Firma en proceso";
var enProceso = "Generaci&oacute;n de documentos en proceso.";
var msjFinal = "Se realiz&oacute; con exito la firma y generaci&oacute;n de ";
var myVar = "";
var myVar2 = "";
console.log("Termino codigo D");

function respuestaCHFECyN(respuestaEvento) {
	console.log(":::: En respuestaCHFECyN");
	var data = respuestaEvento.data;
	var resultadoJSON = $.parseJSON(data);
	var porcentaje1 = 100 / splittedListCadena.length;
	var porcentaje2 = Math.round(porcentaje1*1, -2);

	console.log("Data: " + data);
	console.log("Porcentaje : " + porcentaje1);
	console.log("porcentaje con el decimal: " + porcentaje2);	
    console.log("resultado: " + resultadoJSON.resultado + ", texto: " + resultadoJSON.texto);
	
	if(resultadoJSON.resultado == 0){ // si la respuesta es de exito

		myStopFunction();
		myStopFunction2();

		$('#bar').css('width', porcentaje2 + '%');
		document.getElementById("firmando").innerHTML = avance;
		document.getElementById("bar").style.backgroundColor = '#4D92DF';
		$('#modalBarra').modal({
			backdrop : 'static',
			keyboard : false
		});

		var sizeDocs = Object.keys(resultadoJSON.firmas).length;
		procesarResultadoFirmaMasivaCMS(resultadoJSON); 
		
		myVar = setTimeout(function(){ 
			$('#msgModalFirmaError').modal('hide');
			document.getElementById("firmando").innerHTML = msjFinal + sizeDocs + " documentos.";
			document.getElementById("bar").className = 'progress-bar';
			document.getElementById("bar").style.backgroundColor = '#32CD32';
			document.getElementById("btnBar").style.display = 'block';
		}, 7000);
	
	}else{ // en caso de error en el resultado de la firma
	    console.log("::: ERROR: resultado: " + resultadoJSON.resultado + ", texto: " + resultadoJSON.texto);
		$('#modalBarra').modal('hide');
		$('#msgModalFirmaError').modal({
			backdrop : 'static',
			keyboard : false
		});
	}
}
//verificando si el objeto window tiene disponible el método addEventListener.
if (window.addEventListener) {
	console.log("window.addEventListener 1");	
	addEventListener("message", respuestaCHFECyN, false);	//Usa el estándar moderno de JavaScript
} else {
	console.log("window.addEventListener 2");	
	attachEvent("onmessage", respuestaCHFECyN);	//Asume que está ejecutándose en un navegador antiguo
}

function procesarResultadoFirmaMasivaCMS(resultadoJSON) {
    console.log("---- En procesarResultadoFirmaMasivaCMS");
	var rfcFirmaCMS = resultadoJSON.rfc ? resultadoJSON.rfc : "";
	var firmasObj = resultadoJSON.firmas;
	var firmas = JSON.stringify(resultadoJSON.firmas) ? JSON.stringify(resultadoJSON.firmas) : "";
	var size = Object.keys(resultadoJSON).length;
	console.log("--- rfc a enviar: " + rfcFirmaCMS);
	console.log("--- firmas por enviar: " + firmas);
	console.log("--- tamaño JSON: " + size);
	
    //AQUI ENVIAR SOLICITUD AJAX A MACII PARA GUARDAR DATOS DE RESPUESTA DE LA FIRMA
	procesarRespuestaFirmaDigital(firmasObj);
	
	myVar2 = setTimeout(function(){ 
		document.getElementById("firmando").innerHTML = enProceso;
		document.getElementById("bar").style.backgroundColor = '#DDDDDD';//Gris  
	}, 3000);

	console.log("Saliendo de procesarResultadoFirmaMasivaCMS");
}

function myStopFunction() {
  clearTimeout(myVar);
}
function myStopFunction2() {
  clearTimeout(myVar2);
}