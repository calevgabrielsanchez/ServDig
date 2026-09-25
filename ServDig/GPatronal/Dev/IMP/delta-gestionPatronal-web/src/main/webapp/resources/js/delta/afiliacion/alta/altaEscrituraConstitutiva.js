var campoToFocusOn;

function solicitarConfirmacionDeFolioMecantil(){
	if ($("#altaPatronalForm #moral\\.escrituraConstitutiva\\.seccion").val() != ""
		|| $("#altaPatronalForm #moral\\.escrituraConstitutiva\\.partida").val() != ""
		|| $("#altaPatronalForm #moral\\.escrituraConstitutiva\\.volumen").val() != ""
		|| $("#altaPatronalForm #moral\\.escrituraConstitutiva\\.foja").val() != ""){
		
		var mensaje = "Al dar clic en campo Folio Mercantil indica	que intenta capturar informaci&oacute;n, se descartar&aacute; "+
		"la informaci&oacute;n en los campos Secci&oacute;n, Partida, Volumen y Foja, &iquest;Est&aacute; " +
		"seguro que desea proceder?"
		var objDialogo;
		construirDialogoGenericoDeConfirmacion("#dialogoMensajes", objDialogo, "Solicitud de confirmaci\u00F3n", mensaje, 
				false, callbackConfirmacionFolioMercantil, undefined, 200, 550)
		
		
	}
}

function callbackConfirmacionFolioMercantil(){
	$("#altaPatronalForm #moral\\.escrituraConstitutiva\\.seccion").val("");
	$("#altaPatronalForm #moral\\.escrituraConstitutiva\\.partida").val("");
	$("#altaPatronalForm #moral\\.escrituraConstitutiva\\.volumen").val("");
	$("#altaPatronalForm #moral\\.escrituraConstitutiva\\.foja").val("");
	$("#altaPatronalForm #moral\\.escrituraConstitutiva\\.folioMercantil").focus();
}

function solicitarConfirmacionSeccionPartidaVolumenFoja(campoToFocus){
	if ($("#altaPatronalForm #moral\\.escrituraConstitutiva\\.folioMercantil").val() != "") {
		var mensaje = "Al dar clic en los campos Secci&oacute;n, Partida, Volumen o Foja se intenta capturar informaci&oacute;n en ellos lo cual indica"+
		"que se descartar&aacute; la informaci&oacute;n en el campo Folio Mercantil, &iquest;Est&aacute; seguro que desea proceder?"
		campoToFocusOn = campoToFocus;
		var objDialogo;
		construirDialogoGenericoDeConfirmacion("#dialogoMensajes", objDialogo, "Solicitud de confirmaci\u00F3n", mensaje, 
				false, callbackConfirmacionSPVF, undefined, 200, 550);
	}
}

function callbackConfirmacionSPVF(){
	$("#altaPatronalForm #moral\\.escrituraConstitutiva\\.folioMercantil").val("");
	$("#altaPatronalForm #moral\\.escrituraConstitutiva\\.seccion").val("");
	campoToFocusOn.focus();
}

function inicializaFechasEscritura(){
	inicializaFecha("#txtFechaRegistroEdicionEC", "+60D");
}

function validateEntidadOptionSelection(){
	if($("#altaPatronalForm #moral\\.escrituraConstitutiva\\.lugarExpedicion\\.entidadFederativa\\.clave").val()==-1)
		return "* Seleccione la entidad federativa";
}

function validateMunicipioOptionSelection(){
	if($("#altaPatronalForm #moral\\.escrituraConstitutiva\\.lugarExpedicion\\.clave").val()==-1)
		return "* Seleccione el municipio o delegaci\u00F3n";
}

function validate_FolioMercantil_y_Archivo(){
	if($("#altaPatronalForm #moral\\.escrituraConstitutiva\\.folioMercantil").val()=="" 
		&& $("#altaPatronalForm #moral\\.escrituraConstitutiva\\.seccion").val()==""
		&& $("#altaPatronalForm #moral\\.escrituraConstitutiva\\.partida").val()==""
		&& $("#altaPatronalForm #moral\\.escrituraConstitutiva\\.volumen").val()==""
		&& $("#altaPatronalForm #moral\\.escrituraConstitutiva\\.foja").val()=="")
		return "* Proporcione el Folio Mercantil o bien la Secci\u00F3n, Partida, Volumen y Foja";
}

function validate_Archivo(field, rules, i, options){
	var existeAlgunElementoDeFolioDeArchivo=false;
	if($("#altaPatronalForm #moral\\.escrituraConstitutiva\\.seccion").val()!=""
	|| $("#altaPatronalForm #moral\\.escrituraConstitutiva\\.partida").val()!=""
	|| $("#altaPatronalForm #moral\\.escrituraConstitutiva\\.volumen").val()!=""
	|| $("#altaPatronalForm #moral\\.escrituraConstitutiva\\.foja").val()!="")
		existeAlgunElementoDeFolioDeArchivo=true;
	
	if(existeAlgunElementoDeFolioDeArchivo && field.value=="")
		return "* Este campo es obligatorio"
}

function attachEscrituraConstitutiva(sujetoTramite){
	//nos aseguramos que solo se agregue la info de escritura cuando
	//se seleccionó escritura constitutiva
	if(tipoActa!=1 && sujetoTramite.moral!=undefined)
		sujetoTramite.moral.escrituraConstitutiva = undefined;
	else if(tipoActa!=2 && sujetoTramite.moral!=undefined)
		sujetoTramite.moral.registrRoSindicato = undefined;
	
	return sujetoTramite;
}

$(function() {	
	inicializaFechasEscritura();
});
