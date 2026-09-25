var contadorBloquesCO = 1;
var firmaTarda = "El componente est&aacute; tardando m&aacute;s de lo normal.";
var firmaFalla = "El componente se encuentra fuera de servicio, favor de intentarlo nuevamente.";
var firmadoCorrecto = "Se realiz&oacute; correctamente el firmado de # documentos.";
var myVar = "";
var myVar2 = "";
var myVar3 = "";

if (window.addEventListener) {
	console.log("window.addEventListener 1");	
	addEventListener("message", resultadoCHFECyN, false);
} else {
	console.log("window.addEventListener 2");	
	attachEvent("onmessage", resultadoCHFECyN);
}
		
function firmadoDig(){
	validaRfc();
	////////Firmado
	var rfc = document.getElementById("txtRFC").value;
	var cadenaOri = document.getElementById("cadenaOri").value;

	var cadenaOperacion = '{"operacion": "firmaCMS", "aplicacion": "portalimssdigital","rfc": "'
			+ cambiarUni(rfc) + '", "acuse": "AcuseV1.0", "cad_original":"'
			+ cambiarUni(cadenaOri) +'",'
			+ '"salida": "rfc, cert, acuse, serie_cert, curp, contenedores, rfc_rl, curp_rl"}';

	document.getElementById("params").value = cadenaOperacion;
	console.log('cadenaOperacion: ' + document.getElementById("params").value);
	document.getElementById("formWidget").submit();
}			
	
	
function resultadoCHFECyN(respuestaEvento) {
	console.log("Estoy en resultadoCHFECyN");
	var data = respuestaEvento.data;
	var resultadoJSON = $.parseJSON(data);
	console.log("Data: " + data);
	console.log("resultadoJSON: " + resultadoJSON);
	if(resultadoJSON.resultado == 0){
		console.log("La respuesta se proceso con exito");
	}else{
		console.log("Ocurrio un error en la respuesta, resultadoJSON.resultado: " + resultadoJSON.resultado);
	}
}
	
function validaRfc() {
	console.log("Validando RFC");
	var rfcStr = document.getElementById("txtRFC").value;
	var strCorrecta;
	strCorrecta = rfcStr;
	if (rfcStr.length == 12) {
		var valid = '^(([A-Z]|[a-z]){3})([0-9]{6})((([A-Z]|[a-z]|[0-9]){3}))';
	} else {
		var valid = '^(([A-Z]|[a-z]|\s){1})(([A-Z]|[a-z]){3})([0-9]{6})((([A-Z]|[a-z]|[0-9]){3}))';
	}
	var validRfc = new RegExp(valid);
	var matchArray = strCorrecta.match(validRfc);
	if (matchArray == null) {
		return false;
	} else {
		return true;
	}
}

function cambiarUni(cadena) {
	var result = "";
	for ( var i = 0; i < cadena.length; i++) {
		// Assumption: all characters are < 0xffff
		if (cadena[i] == "á" || cadena[i] == "é" || cadena[i] == "í"
				|| cadena[i] == "ó" || cadena[i] == "ú"
				|| cadena[i] == "Á" || cadena[i] == "É"
				|| cadena[i] == "Í" || cadena[i] == "Ó"
				|| cadena[i] == "Ú" || cadena[i] == "ñ"
				|| cadena[i] == "Ñ" || cadena[i] == "&" 
				|| cadena[i] == "'" || cadena[i] == "\"") {
			result += "\\u"
					+ ("000" + cadena[i].charCodeAt(0).toString(16))
							.substr(-4);
		} else {
			result += cadena[i];
		}
	}
	return result;
}	

function muestraIframe(){

	myStopFunction();
	myStopFunction2();
	myStopFunction3();

 	var porcentaje1 = 100 / 5; //splittedListCadena.length;
 	console.log("Resultado Porcentaje1 : " + porcentaje1);

	var porcentaje2 = Math.round(porcentaje1*contadorBloquesCO, -2);
	console.log("porcentaje con el decimal: " + porcentaje2);

	$('#bar').css('width', porcentaje2 + '%');
	var avance = "Firmando documentos:  " + porcentaje2 + "%";

	document.getElementById("firmando").innerHTML = avance;
	document.getElementById("bar").style.backgroundColor = '#4D92DF';

	$('#modalBarra').modal({
		backdrop : 'static',
		keyboard : false
	});
		
	myFunction();
	myFunction2();
	myFunction3();
}

/*
		$('#msgModalFirma').modal('hide');
		$('#msgModalFirmaError').modal('hide');
		$('#modalBarra').modal('show');
*/

function myFunction() {
	console.log("En myFunction");
	  myVar = setTimeout(function(){ 
		  $('#msgModalFirmaError').modal('hide');
		  document.getElementById("firmando").innerHTML = firmaTarda;
		  document.getElementById("docsFirmados").innerHTML = "Documentos firmados: # de # al momento.";
		  document.getElementById("bar").style.backgroundColor = '#DDDDDD';//Gris tardando 
	  }, 2000);
	}

function myFunction2() {
console.log("En myFunction2");
	  myVar2 = setTimeout(function(){ 
		  $('#msgModalFirmaError').modal('hide');
		  document.getElementById("firmando").innerHTML = firmaFalla;
		  document.getElementById("docsFirmados").innerHTML = "Se firmaron: # de # documentos.";
		  document.getElementById("bar").className = 'progress-bar';
		  document.getElementById("bar").style.backgroundColor = '#D0021B';//Rojo falló 
		  document.getElementById("btnBar").style.display = 'block';
		  document.getElementById("btnBar2").innerHTML = "Aceptar";
	  }, 3000);	 
	}

function myFunction3() {
console.log("En myFunction3");
	  myVar3 = setTimeout(function(){ 
	    //$('#modalBarra').modal('hide');
		$('#msgModalFirmaError').modal('hide');
		document.getElementById("firmando").innerHTML = firmadoCorrecto;
		document.getElementById("bar").className = 'progress-bar';
		document.getElementById("bar").style.backgroundColor = '#32CD32';
		document.getElementById("btnBar").style.display = 'block';	
		document.getElementById("btnBar2").innerHTML = "Exito";		
	  }, 4000);
	}
	
function myStopFunction() {
  clearTimeout(myVar);
}

function myStopFunction2() {
  clearTimeout(myVar2);
}

function myStopFunction3() {
  clearTimeout(myVar3);
}
