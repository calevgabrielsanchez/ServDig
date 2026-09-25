function KeyPressed(psCampo,psEvento,psTipoDato, pAlert, pUpperCase, pBlanks,pNotEnter)
{
	var lsValor="", lsTecla="", lsTeclachar="", lsCaracteresPermitidos="";
	
	if (window.event)
		lsTecla = window.event.keyCode;
	else if (psEvento)
		lsTecla = psEvento.which;
	else
		return true;

	psTipoDato = psTipoDato.toLowerCase();

	if (psTipoDato=='alfabetico') //Alphabetic values
		lsCaracteresPermitidos = "kgjwopbvcñkgjfaábcdeéfghiíjklmnñoópqrstuúüvwxyz ";
	else if (psTipoDato=='fecha') //Date Values
			lsCaracteresPermitidos = "1234567890/";
	else if (psTipoDato=='hora') //Time Values
			lsCaracteresPermitidos = "1234567890:";
	else if (psTipoDato=='moneda'){ //Currency Values
			lsValor = psCampo.value;
			if ( lsValor.indexOf(".") > -1) 
				lsCaracteresPermitidos = "1234567890";
			else 
				lsCaracteresPermitidos = "1234567890.";
		}
	else if (psTipoDato=='entero'){ //int values
			lsCaracteresPermitidos = "1234567890";
		}
	else if (psTipoDato=='validchar'){ //Every Valid Character.
			lsCaracteresPermitidos =  " !#$%&()*+,-./0123456789:;<=>?@[\]^_`abcdefghijklmnopqrstuvwxyz{|}~''¡¢£¤¥¦§¨(c)ª«¬­(r)¯°±²³´µ¶·¸¹º»1/41/23/4¿àáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ";
			lsCaracteresPermitidos =  lsCaracteresPermitidos + '"';
		}
	else if (psTipoDato=='phone'){ //int values plus space
			lsCaracteresPermitidos = "1234567890+()- ";
	}	
	else if (psTipoDato=='folio'){ //int values plus comma
			lsCaracteresPermitidos = "1234567890,";
	}
        else if (psTipoDato=='email'){ //mail Values
			lsValor = psCampo.value;
			if ( lsValor.indexOf("@") > -1) 
				lsCaracteresPermitidos = "1234567890_-,.abcdefghijklmnopqrstuvwxyz";
			else 
				lsCaracteresPermitidos = "1234567890_-,.abcdefghijklmnopqrstuvwxyz@";
		}
	

	else if (psTipoDato=='alphanumeric') //alphanumeric values
		lsCaracteresPermitidos = "aábcdeéfghiíjklmnñoópqrstuúüvwxyz1234567890 ";

	else if (psTipoDato=='alphanumericnotspecial') //
		lsCaracteresPermitidos = "abcdefghijklmnopqrstuvwxyz0123456789 "; 
	else if ( psTipoDato=='account') // For Bank Acount 
		lsCaracteresPermitidos = "1234567890 -"; 
	else if ( psTipoDato=='user') // For User Id
		lsCaracteresPermitidos = "1234567890 -_.abcdefghijklmnopqrstuvwxyz"; 
		
	lsTeclaChar = String.fromCharCode(lsTecla);

	if (pAlert == null) {
		pAlert = "";
	}

	if (pUpperCase != null) {
		if (pUpperCase.toLowerCase() == 'uppercase=yes') {
		   lsTeclaChar = lsTeclaChar.toUpperCase();
		   window.event.keyCode = lsTeclaChar.charCodeAt(0);
		}
	}
	if (pBlanks != null) {
		if (pBlanks.toLowerCase() == 'blanks=yes') {
			lsCaracteresPermitidos += " ";
		}
	}

	if ((lsCaracteresPermitidos).indexOf(lsTeclaChar.toLowerCase())>-1 || lsTecla==8 || lsTecla==13 || lsTecla==0) {	
	   if (lsTecla==13 && pNotEnter != null && pNotEnter.toLowerCase()=='yes')
		return false;
	   else
		return true;
	}
	else {
	  if (pAlert.toLowerCase() == 'alert=yes') {
	  	alert('Sólo se permiten estos caracteres ' + lsCaracteresPermitidos);
	  }
	  return false;
	}
}

function convertirMayusculas(psCampo,psEvento,psTipoDato){
	retorno = KeyPressed(psCampo,psEvento,psTipoDato)
	if(retorno){
		if (window.event.keyCode>=97 && window.event.keyCode<=122){
			alert(window.event.keyCode);
			alert(chr(window.event.keyCode));
		}
	}
	return retorno;
}

