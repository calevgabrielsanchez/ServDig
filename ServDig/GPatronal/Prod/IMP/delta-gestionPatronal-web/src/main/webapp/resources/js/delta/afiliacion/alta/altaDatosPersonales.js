function validaDatosPersonalesRequeridos(){
	var datosAfilCapturados = $("form#altaPatronalForm").toObject(true);
	
	if(tipoPersona==0){
		showErrorDialog("Debe proporcionar la informaci\u00F3n de la persona que desea dar de alta.");
		return false;	
	}else if(tipoPersona==tipoPersonaFisica){
		if(datosAfilCapturados.fisica.primerApellido=="" || datosAfilCapturados.fisica.segundoApellido ==""
				|| datosAfilCapturados.fisica.nombre =="" || datosAfilCapturados.fisica.curp ==""
				|| datosAfilCapturados.fisica.curp =="" || datosAfilCapturados.fisica.nombreComercial ==""){
			showErrorDialog("Por favor complete la informaci\u00F3n requerida.");
			return false;
		}
		
	}else if(tipoPersona==tipoPersonaMoral){
		if(datosAfilCapturados.moral.razonSocial=="" || datosAfilCapturados.moral.tipoSociedad.descripcion=="" 
			|| datosAfilCapturados.moral.rfc =="" || datosAfilCapturados.moral.nombreComercial ==""){
			showErrorDialog("Por favor complete la informaci\u00F3n requerida.");
			return false;
		}
	
	}
	
	return true;
}
