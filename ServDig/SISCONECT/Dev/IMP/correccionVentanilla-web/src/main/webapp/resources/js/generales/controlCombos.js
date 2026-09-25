function tranfiereInfoComb1aCom2(nameLst1,nameLst2) {

	obj=document.getElementById(nameLst1);
	obj2=document.getElementById(nameLst2);
	
	if (obj.value==-1 && obj2.length>1){

		while (true) {
				obj2=document.getElementById(nameLst2);
				cantidadOpts = obj2.length;
				
				if(cantidadOpts<=1) break;
				obj2.options[cantidadOpts-1]=null;
				
	   }
	   
		return;
	}else if(obj.value!=-1 && obj2.length<=1){
		cantidadOpts = obj.length;

		for (i = 1; i < cantidadOpts; i++) {
				valor=obj.options[i].value;
				txt=obj.options[i].text;
				obj2=document.getElementById(nameLst2);
				opc = new Option(txt,valor);
				eval(obj2.options[obj2.options.length]=opc);	 
	    }
	
	}
}

function getSendComboDescToHidden(obj,hiddenObj) {
	var hid = document.getElementById(hiddenObj);
	
	if(obj.options[obj.selectedIndex].text!="--Por favor seleccione--")
		hid.value = obj.options[obj.selectedIndex].text;
	else
		hid.value = "";
}

//function getSendComboDescToHiddenJSON(idObj,hiddenObj) {
//	alert("idObj="+idObj);
//	alert("hiddenObj="+hiddenObj);
//	//var obj = document.getElementById(idObj);
//	//var hid = document.getElementById(hiddenObj);
//	
//	alert("idObj options="+idObj.options);
//	alert("hiddenObj.value="+hiddenObj.value);
//	hiddenObj.value = idObj.options[idObj.selectedIndex].text;
//	
//}

