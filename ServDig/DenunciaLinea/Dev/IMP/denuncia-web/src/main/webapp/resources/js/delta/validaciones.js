function tieneDato(campo,leyenda){
	if((campo == null) || (campo =="")){
		alert("El campo "+leyenda+" no debe estar vacio");
		return false;
	}else
		return true;
}

function tieneDatos(campo,leyenda){
	if((campo == null) || (campo =="")){
		alert(leyenda);
		return false;
	}else
		return true;
}
function checaCero(campo,leyendaCampo){
    if (campo == -1 || campo == -2 || campo == 0){
           alert("Debe seleccionar "+leyendaCampo);           
           return false;
        }
    else return true;
}

function longitudMandatoria(campo,longitud,leyendaCampo){
	campo = campo +"";	
	if(campo.length<longitud){
		alert("La longitud del campo "+leyendaCampo+" debe ser de "+longitud);
		return false;
	}else return true;
}

function validaFechas(fecha, fecha2){
	fecha = fecha+"";
	fecha2 = fecha2+"";
	if(fecha != "" && fecha2 != ""){
		if (fecha.substring(6,10) > fecha2.substring(6,10))  
		{  
			return(true);
		}  
		else  
		{  
			if (fecha.substring(6,10) == fecha2.substring(6,10))  
			{   
				if (fecha.substring(3,5) > fecha2.substring(3,5))  
				{  
					return(true);  
				}  
				else  
				{   
					if (fecha.substring(3,5) == fecha2.substring(3,5))  
					{  
						if (fecha.substring(0,2) >= fecha2.substring(0,2))  
							return(true);  
						else{							
							return(false);
						}							  
					}  
					else{						
						return(false);
					}						  
				}  
			}  
			else{				
				return(false);
			}
		}
	}		  
}

function validaFechasPorPeriodo(fecha, fecha2){
	fecha = fecha+"";
	fecha2 = fecha2+"";
	if(fecha != "" && fecha2 != ""){
		if (fecha.substring(6,10) == fecha2.substring(6,10))  
		{  
			return(true);
		}  
		else  
		{
			if(fecha.substring(6,10)-(fecha2.substring(6,10))>1){
				return false;
			}else{
				if (fecha.substring(6,10) > fecha2.substring(6,10))  
				{   
					if (fecha.substring(3,5) < fecha2.substring(3,5))  
					{  
						return(true);  
					}  
					else  
					{   
						if (fecha.substring(3,5) == fecha2.substring(3,5))  
						{  
							if (fecha.substring(0,2) <= fecha2.substring(0,2))  
								return(true);  
							else{							
								return(false);
							}							  
						}  
						else{						
							return(false);
						}						  
					}  
				}  
				else{				
					return(false);
				}
			}						
		}
	}		  
}

function checaMail(campo,leyendaCampo){
	
    var checkOK = "@."; 
    var checkStr = campo; 
    var allValid = true; 
    var decPoints = 0; 
	var elementFound = 0;
    var allNum = "";    
    checkStr+="";   
	if(checkStr.length>0){		
		for (i = 0; i < checkStr.length; i++) 
		{ 
			ch = checkStr.charAt(i);
			for (j = 0; j < checkOK.length; j++) 
				if (ch == checkOK.charAt(j)){					
					elementFound = elementFound+1;
					break; 
				}
				
				allNum += ch; 
			}
			if (elementFound<2) { 
				alert("El campo \""+leyendaCampo+"\" no cuenta con un formato correcto.Ej: mail@dominio.com"); 
			return false; 
		}
	}
    return true;
}


function valorMaximo(campo,leyendaCampo,maximo){	
	if(campo>maximo){
		alert("El valor de "+leyendaCampo+" es mayor de "+maximo);
		return false;
	}else return true;
}

function PermiteSoloNumeros(id) {
    id = id + "";
    var valida_numeros = id.replace(/[^0-9()]*/gi,"");
    return valida_numeros;
}

function noCaracteresEspeciales(id) {
	id = id + "";
    var valida_numeros = id.replace(/[\'\´\#\$\(\)\"\!\%\%\=\+\@\+\,\;\:\*\&\?\¡\¿\{\}\[\]\°\¬\^\|\`\~\\\\]+/gi,"");
    return valida_numeros.toUpperCase();
}

function PermiteSoloNumerosYPunto(id) {
    var id = id + "";
    var valida_numeros = id.replace(/[^0-9.]*/gi,"");    
    return valida_numeros;
}

function validaCampo(accion,id,form){
	id = "form#"+form+" #"+id+"";
	if(accion == 'noCaracteresEspeciales'){
		$(id).val(noCaracteresEspeciales($(id).val()));
	}if(accion == 'PermiteSoloNumeros'){
		$(id).val(PermiteSoloNumeros($(id).val()));
	}if(accion == 'PermiteSoloNumerosYPunto'){
		$(id).val(PermiteSoloNumerosYPunto($(id).val()));
	}		
}

function bloquear(){
//	$.blockUI({ message:  '<h1>Procesando...</h1>', css: {             
//		border: 'none',             
//		padding: '15px',                          
//		opacity: .5             
//	} });
}


function desbloquear(){
	//$.unblockUI();
}

function validaChkBoxLst(chkObj,leyenda){
	var currentChk;
	var myCheckBoxList = chkObj.split(",");
	var cont = 0;
	try{
		for(var i=0;i<myCheckBoxList.length;i++){	
			currentChk = $("form#trabajadoresFormRegistro #"+myCheckBoxList[i]).attr('checked');
			
			if (currentChk){ 
				cont = cont + 1;
				
				};
			
		}
		if(cont > 0)return true
	}catch(error){

		return false;
	}

	alert("Debe de seleccionar al menos un elemento en:"+leyenda);
	
	return false;
}

function validaPeriodo(form,fec,fec2){
	fec = "form#"+form+" #"+fec+"";
	fec2 = "form#"+form+" #"+fec2+"";
	if($(fec2).val()!=""){
		if(!validaFechasPorPeriodo($(fec2).val(),$(fec).val())){
			alert("La b"+'\u00fa'+"squeda maxima es de un periodo");
			$(fec2).val("");
		}
	}	
}

function validaMail(form,campo,leyenda){
	mail = "form#"+form+" #"+campo+"";
	if($(mail).val()!=""){
		if(!checaMail($(mail).val(),leyenda)){
			$(mail).focus();
		}
		
	}
}