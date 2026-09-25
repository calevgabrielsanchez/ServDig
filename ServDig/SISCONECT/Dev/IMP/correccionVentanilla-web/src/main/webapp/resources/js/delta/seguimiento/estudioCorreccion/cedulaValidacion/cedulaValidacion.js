//Variables globales para la cedula de validacion
var cv_numFolio;
var cv_regPatronal;
var cv_claveSolCorr;
var cv_claveAnexoSolCorr;
var cv_cvePresentaCorr;
var respuesta ;
var dtConsoImportesCedula;
//alamcena el numero de pagos en la modal de pagos
var numeroPagosPorCorreccionCedVal ;
var percepcionesCedulaVal;
var cveSolcorr;
var idRolUsuario;
var auditor='2';
var supervisor='10';

var JEFE_OF_CORRECCION = 4;
var JEFE_OF_CORRECCION_Y_DICTAMEN = 6;
var JEFE_DEP_AUD_PAT = 7;
var SUPERVISOR_OF_CORRECCION= 10;
var NOTIFICACION_OFICIO_RESULTADOS=14;
var nivelAutorizacion;
var detalleData;

var indAutoPrimera;
var indAutoSegunda;
var totalDifePrimer=0.0;
function getConsolidadoCedulaValidacion(data) {
	determinaRol();	
	getConsolidadoCedulaValidacionPrincipal(data);
}


function determinaRol(){
	$.postJSON("correccion/consultaRolUsuario.do", null, function(rol) {
		idRolUsuario=rol;
		if(rol==JEFE_OF_CORRECCION || rol==JEFE_DEP_AUD_PAT ||
				rol==SUPERVISOR_OF_CORRECCION || rol==JEFE_OF_CORRECCION_Y_DICTAMEN){
			//caso de super
			bloqueaConsolidacionSupervisor();
			$("#btnAutorizarCedVali").show("fast");
			$("#btnRechazaCedVali").show("fast");
			$("#btnFinalizaCedulaValidacion").hide("fast");
		}else{
			$("#btnAutorizarCedVali").hide("fast");
			$("#btnRechazaCedVali").hide("fast");
			$("#btnFinalizaCedulaValidacion").show("fast");
		}
		
		
	});
}





function getConsolidadoCedulaValidacionPrincipal(data) {
	//alert("getConsolidadoCedulaValidacionPrincipal");
	limpiarFormularioCedulaValidacion();
	cv_cvePresentaCorr = $("#cvePresentaCorreccionHdnSeg").val();
	cveSolcorr=data.cveSolCorr;
	var sVarSeg = '{"cvePresentaCorr":"'+cv_cvePresentaCorr+'","cveSolCorr":"'+data.cveSolCorr+'"}';
	var clase = jQuery.parseJSON(sVarSeg);
	
	$.postJSON_Sync("correccion/consultaCedValidacion.do", clase, function(data) {		
		validAutorizacion();
		indAutoPrimera=data.indValPrimera;
		indAutoSegunda=data.indValSegunda;
		generaTablaConsolidadoValidacion(data.listaConsolidados);
		generaCombosCedValid(data);
		generaListenersCedValid();
		llenaConsolidacionImportesExistente(data);
		generaResumenValidacionCedula(data);
		recalculaTotal();
		$("form#formCedValidacionSeguimientoCorr #cveConsolidaImporteCedValHdn").val(data.cveRecepcion);

		var fechaNotOR = $('form#ofSeguimientoCorreccionForm #fecNotORSegCorr').val(); 
		if(indAutoPrimera == 2 && indAutoSegunda == 2 && ( fechaNotOR != null && fechaNotOR != undefined && fechaNotOR != '' )){
			activaSeccionConsolidaImportes();
		}
	});
	
}


function generaResumenValidacionCedula(data){
	 $("form#formResumenSeguimiento #lbValNumTrabajOfRes").text(moneyMaskDT(data.numTrabRegularizados));

	 drawData("#lbCopBaseValCedVal", data.consolidaImporteVo.suertePpalDetCOP);
	 drawData("#lbCopSPValCedVal", data.consolidaImporteVo.suertePrincipalCOP);
	 drawData("#lbCopActValCedVal", data.consolidaImporteVo.actualizacionCOP);
	 drawData("#lbCopRecValCedVal", data.consolidaImporteVo.recargosCOP);
	 drawData("#lbCopMultaValCedVal", data.consolidaImporteVo.multasCOP);
	 drawData("#lbCopTotaValCedVal", data.consolidaImporteVo.totalPagadoCOP);
	 
	 	 
	 drawData("#lbRcvBaseValCedVal", data.consolidaImporteVo.suertePpalDetRCV);
	 drawData("#lbRcvSPValCedVal", data.consolidaImporteVo.suertePrincipalRCV);
	 drawData("#lbRcvActValCedVal",data.consolidaImporteVo.actualizacionRCV);
	 drawData("#lbRcvRecValCedVal", data.consolidaImporteVo.recargosRCV);
	 drawData("#lbRcvMultaValCedVal", data.consolidaImporteVo.multasRCV);
	 drawData("#lbRcvTotaValCedVal", data.consolidaImporteVo.totalPagadoRCV);
	 
	
	var totalBase=parseFloat($("form#formResumenSeguimiento #lbCopBaseValCedVal").text())+parseFloat($("form#formResumenSeguimiento #lbRcvBaseValCedVal").text());
	var totalSuerte=parseFloat($("form#formResumenSeguimiento #lbCopSPValCedVal").text())+parseFloat($("form#formResumenSeguimiento #lbRcvSPValCedVal").text());
	var totalActualizac=parseFloat($("form#formResumenSeguimiento #lbCopActValCedVal").text())+parseFloat($("form#formResumenSeguimiento #lbRcvActValCedVal").text());
	var totalRecarg=parseFloat($("form#formResumenSeguimiento #lbCopRecValCedVal").text())+parseFloat($("form#formResumenSeguimiento #lbRcvRecValCedVal").text());
	var totalMulta=parseFloat(data.consolidaImporteVo.multasCOP)+parseFloat(data.consolidaImporteVo.multasRCV);
	var totalTotal=parseFloat($("form#formResumenSeguimiento #lbCopTotaValCedVal").text())+parseFloat($("form#formResumenSeguimiento #lbRcvTotaValCedVal").text());
	
	
	
	
	drawData("#lbTotalBaseValCedVal", totalBase);
	drawData("#lbTotalSPValCedVal", totalSuerte);
	drawData("#lbTotalActValCedVal", totalActualizac);
	drawData("#lbTotalRecValCedVal", totalRecarg);
	drawData("#lbTotalTotaValCedVal", totalTotal);
	drawData("#lbTotalMultaValCedVal", totalMulta);
	drawData("#lbnumTraRegu", data.consolidaImporteVo.trabRegularizados);
}

function drawData(id,valor){
	if(valor=='' || valor==null || valor==undefined || valor=='null'){
		valor="0.0";
	}
	 $("form#formResumenSeguimiento "+id).text(moneyMaskDT(valor));
}
//Recuperamos la lista de ejercicios,rp y totales de RP asociados a la presentacion y solCorr
function inicializaCedulaValidacion(data){
	inicializaSeccionConsolidaImportes();
	getConsolidadoCedulaValidacion(data);
	
}

//Inicializa los combos de ejercicio y RP
function generaCombosCedValid(data){	
	var options = "<option value='-1' >--Por favor seleccione--</option>";
	for(var s=0;s<data.ejercicios.length;s++){
		options += "<option value='"+ data.ejercicios[s] +"'>"+ data.ejercicios[s]+"</option>";		
	}
	$("form#formCedValidacionSeguimientoCorr #numEjercicio").html(options);
}


function llenaConsolidacionImportesExistente(data){
	$("form#formCedValidacionSeguimientoCorr #porcentajeAvanceSegCorr_cedval").val(data.consolidaImporteVo.porcAvance);
	$("form#formCedValidacionSeguimientoCorr #porcentajeRegularizadoSegCorr_cedval").val(data.consolidaImporteVo.porcRegularizado);
	$("form#formCedValidacionSeguimientoCorr #trabRevisadosSegCorr_cedval").val(data.consolidaImporteVo.trabRevisados);
	$("form#formCedValidacionSeguimientoCorr #trabOmisosUniSegCorr_cedval").val(data.consolidaImporteVo.trabOmisos);
	$("form#formCedValidacionSeguimientoCorr #trabSubdeclaUniSegCorr_cedval").val(data.consolidaImporteVo.trabSubdeclarados);
	$("form#formCedValidacionSeguimientoCorr #trabRegularizadosRegObraSegCorr_cedval").val(data.consolidaImporteVo.trabRegularizados);
	$("form#formCedValidacionSeguimientoCorr #suertePpalDetCopSegCorr_cedval").val(data.consolidaImporteVo.suertePpalDetCOP);
	$("form#formCedValidacionSeguimientoCorr #suertePpalDetRcvSegCorr_cedval").val(data.consolidaImporteVo.suertePpalDetRCV);
	//alert("data.consolidaImporteVo.fechaRegistroTxt : " + data.consolidaImporteVo.fechaRegistroTxt);
	$("form#formCedValidacionSeguimientoCorr #fecCaptura_cedval").val(data.consolidaImporteVo.fechaRegistroTxt);
	
	if(data.consolidaImporteVo.numParcialidades == null || data.consolidaImporteVo.numParcialidades == 'null'){
		$("form#formCedValidacionSeguimientoCorr #numeroParcialidadesSegCorr_cedval").val('');
	}else{
		$("form#formCedValidacionSeguimientoCorr #numeroParcialidadesSegCorr_cedval").val(data.consolidaImporteVo.numParcialidades);
	}
	
	if(data.comprobanteConvenio == null || data.comprobanteConvenio == false){
		$("form#formCedValidacionSeguimientoCorr #cbxComprobConvenio_cedval").attr('checked', false);
	}else{
		$("form#formCedValidacionSeguimientoCorr #cbxComprobConvenio_cedval").attr('checked', true);
	}
}

function generaListenersCedValid(){
	
	$("form#formCedValidacionSeguimientoCorr #numEjercicio").unbind();
	$("form#formCedValidacionSeguimientoCorr #regPatronal").unbind();
	$("form#formCedValidacionSeguimientoCorr #numEjercicio").change(function(){		
		
		if(percepcionesCedulaVal!=undefined){
			percepcionesCedulaVal.splice(0);	
		}		
		generaTablaVacia();
		recalculaTotal();
		
		var sVarSeg = '{"cveSolCorr":"'+claveSolCorr+'","cveEjercicio":"'+$("form#formCedValidacionSeguimientoCorr #numEjercicio").val()+'"}';
		var clase = jQuery.parseJSON(sVarSeg);	
		$.postJSON("correccion/consultaRegistrosPatronales.do", clase, function(data) {			
			var optionsRegPat="<option value='-1' >--Por favor seleccione--</option>";
			for(var s=0;s<data.length;s++){
				optionsRegPat += "<option value='"+ data[s][1] +"'>"+ data[s][0]+"</option>";		
			}
			$("form#formCedValidacionSeguimientoCorr #regPatronal").html(optionsRegPat);
		}).error(function(data){
			jAlert('No se puede recuperar la lista de registros patronales, favor de reintentar ','Alert Dialog');
		});
	});
	
	
	$("form#formCedValidacionSeguimientoCorr #regPatronal").change(function(){
		if($("form#formCedValidacionSeguimientoCorr #numEjercicio").val()!='-1' && $("form#formCedValidacionSeguimientoCorr #regPatronal").val()!='-1'){
			determinaRol();
			getDetalleValidacion();
			validAutorizacion();
		}
	});
	
}


//Recupera la informacion asociada a un ejercicio y un registro patronal
function getDetalleValidacion(){	

	var ejercicio=$("form#formCedValidacionSeguimientoCorr #numEjercicio").val();
	var regPatro=$("form#formCedValidacionSeguimientoCorr #regPatronal option:selected").text();
	var folio=numFolio;	
	var claveAnexoSolCorr=$("form#formCedValidacionSeguimientoCorr #regPatronal").val();
	var sVarSeg = '{"cveSolCorr":"'+claveSolCorr+'","cveEjercicio":"'+ejercicio+'","regPatronal":"'+regPatro+'","nuFolio":"'+numFolio+'","cvePresentaCorr":"'+cvePresentaCorre+'","cveAnexoSolCorr":"'+claveAnexoSolCorr+'"}';
	var clase = jQuery.parseJSON(sVarSeg);
	
	$.postJSON_Sync("correccion/consultaDetalleCedValidacion.do", clase, function(data) {
		detalleData=data;
		generaTablaConsolidadoValidacionImportes(data.listaPercepciones);
		validAutorizacion();
		bloquearSecciones(detalleData);
		indAutoPrimera=data.indValPrimera;
		indAutoSegunda=data.indValSegunda;
		
		//en caso de que haya fecha Notificacion y los ckecks esten seleccionados todos habilita seccion de pagos
		 var fechaNotOR = $('form#ofSeguimientoCorreccionForm #fecNotORSegCorr').val();  
		 if( (fechaNotOR != null && fechaNotOR != undefined && fechaNotOR != '') && (indAutoSegunda == 2 || indAutoSegunda == 0)){
				 activaSeccionConsolidaImportes();
		 }
		 validAutorizacion();
	});
	
	validAutorizacion();
}


function bloquearSecciones(data){
	$("#btnAutorizarCedVali").removeAttr("disabled");	
	$("#btnRechazaCedVali").removeAttr("disabled");	
	$("#btnGuardarValidacion").removeAttr("disabled");	

	if(idRolUsuario==auditor){
		if((data.indValPrimera==null && data.indValSegunda==null)||(data.indValPrimera=='0' && data.indValSegunda==null )){
			bloquearSegundoNivel();
			desbloquearPrimerNivel();
			nivelAutorizacion=1;
		}else if(recuperaRecepcion() && ((data.indValPrimera=='2'  && data.indValSegunda==null) || (data.indValPrimera=='2'  && data.indValSegunda=='0'))){
			bloquearPrimerNivel();
			desbloquearSegundoNivel();
			nivelAutorizacion=2;
		}else{
			bloquearPrimerNivel();
			bloquearSegundoNivel();
			nivelAutorizacion=-1;
		}
		deshabilitaCamposGridCV();
	}else if(idRolUsuario==JEFE_OF_CORRECCION || idRolUsuario==JEFE_DEP_AUD_PAT ||
			idRolUsuario==SUPERVISOR_OF_CORRECCION || idRolUsuario==JEFE_OF_CORRECCION_Y_DICTAMEN){
		bloquearPrimerNivel();
		bloquearSegundoNivel();
		if((data.indValPrimera=='1' && data.indValSegunda==null)
				//||(data.indValPrimera=='0' && data.indValSegunda==null )
				){
			accesoPrimerNivelAutorizacion(true);
			accesoSegundoNivelAutorizacion(false);
			nivelAutorizacion=1;
		}else if((data.indValPrimera=='2'  && data.indValSegunda=='1') 
//				|| (data.indValPrimera=='2'  && data.indValSegunda=='0')
				){
			nivelAutorizacion=2;
			accesoPrimerNivelAutorizacion(false);
			accesoSegundoNivelAutorizacion(true);
		}else{
			accesoPrimerNivelAutorizacion(false);
			accesoSegundoNivelAutorizacion(false);
			nivelAutorizacion=-1;
			$("#btnAutorizarCedVali").prop('disabled','disabled');
			$("#btnRechazaCedVali").prop('disabled','disabled');
			$("#btnGuardarValidacion").prop('disabled','disabled');
			
			
			
		}
	}
	
	
}

function accesoPrimerNivelAutorizacion(flag){	
	if(!flag){
		for(var s=0;s<percepcionesCedulaVal.length;s++){
			$('#autorizaAclarado'+percepcionesCedulaVal[s].idRow).attr('disabled', 'disabled');		
		}
	}else{
		for(var s=0;s<percepcionesCedulaVal.length;s++){
			$('#autorizaAclarado'+percepcionesCedulaVal[s].idRow).removeAttr('disabled');		
		}		
	}	
}


function accesoSegundoNivelAutorizacion(flag){	
	if(!flag){
		for(var s=0;s<percepcionesCedulaVal.length;s++){
			$('#autorizaAclaradoOficioRes'+percepcionesCedulaVal[s].idRow).attr('disabled', 'disabled');		
			$('#autorizaTotalPagado'+percepcionesCedulaVal[s].idRow).attr('disabled', 'disabled');	
		}
	}else{
		for(var s=0;s<percepcionesCedulaVal.length;s++){
			$('#autorizaAclaradoOficioRes'+percepcionesCedulaVal[s].idRow).removeAttr('disabled');	
			$('#autorizaTotalPagado'+percepcionesCedulaVal[s].idRow).removeAttr('disabled');	
		}		
	}	
}

function bloquearPrimerNivel(){	
	for(var s=0;s<percepcionesCedulaVal.length;s++){
		$('#aclaradoCV'+percepcionesCedulaVal[s].idRow).attr('disabled', 'disabled');		
	}
}

function bloquearSegundoNivel(){
	
	for(var s=0;s<percepcionesCedulaVal.length;s++){
		$('#aclaradoOficioResultados'+percepcionesCedulaVal[s].idRow).attr('disabled', 'disabled');
		$('#totalPagado'+percepcionesCedulaVal[s].idRow).attr('disabled', 'disabled');
	}
}


function desbloquearPrimerNivel(){	
	for(var s=0;s<percepcionesCedulaVal.length;s++){
		$('#aclaradoCV'+percepcionesCedulaVal[s].idRow).removeAttr('disabled');	
	}
}

function desbloquearSegundoNivel(){
	
	for(var s=0;s<percepcionesCedulaVal.length;s++){
		$('#aclaradoOficioResultados'+percepcionesCedulaVal[s].idRow).removeAttr('disabled');
		$('#totalPagado'+percepcionesCedulaVal[s].idRow).removeAttr('disabled');
	}
}

function recuperaRecepcion(){
	var sVarSeg = '{"cvePresentaCorr":"'+cvePresentaCorre+'"}';
	var clase = jQuery.parseJSON(sVarSeg);
	var flag;
	$.postJSON_Sync("correccion/getRecepcion.do", clase, function(data) {
		if(data.cveStatus==NOTIFICACION_OFICIO_RESULTADOS){			
			flag= true;
		}else{			
			flag= false;
		}		
	});
	return flag;
}


function deshabilitaCamposGridCV(){
	
	for(var s=0;s<percepcionesCedulaVal.length;s++){
		if(percepcionesCedulaVal[s].autorizaAclarado){
			$('#aclaradoCV'+percepcionesCedulaVal[s].idRow).attr('disabled', 'disabled');
		}
		if(percepcionesCedulaVal[s].autorizaAclaradoOficioRes){
			$('#aclaradoOficioResultados'+percepcionesCedulaVal[s].idRow).attr('disabled', 'disabled');
		}
		if(percepcionesCedulaVal[s].autorizaTotalPagado){
			$('#totalPagado'+percepcionesCedulaVal[s].idRow).attr('disabled', 'disabled');
		}
	}
	
}




function validaAutorizacionCedulaValid(idRow){
	
	for(var s=0;s<percepcionesCedulaVal.length;s++){
		if(percepcionesCedulaVal[s].idRow==idRow){
				percepcionesCedulaVal[s].autorizaAclarado=$("#autorizaAclarado"+percepcionesCedulaVal[s].idRow).is(":checked");
				percepcionesCedulaVal[s].autorizaAclaradoOficioRes=$("#autorizaAclaradoOficioRes"+percepcionesCedulaVal[s].idRow).is(":checked");
				percepcionesCedulaVal[s].autorizaTotalPagado=$("#autorizaTotalPagado"+percepcionesCedulaVal[s].idRow).is(":checked");			
				break;
		}
	}
	validAutorizacion();
//	var flagAutoriza=true;
//	if(nivelAutorizacion==1){	
//		for(var s=0;s<percepcionesCedulaVal.length;s++){
//			if(!percepcionesCedulaVal[s].autorizaAclarado){
//				flagAutoriza=false;				
//			}
//		}		
//	}else if(nivelAutorizacion==2){
//		for(var s=0;s<percepcionesCedulaVal.length;s++){
//			if((!percepcionesCedulaVal[s].autorizaAclaradoOficioRes) || (!percepcionesCedulaVal[s].autorizaTotalPagado)){
//				flagAutoriza=false;				
//			}
//		}		
//	}
//
//	if(flagAutoriza){
//		$('#btnAutorizarCedVali').removeAttr('disabled');	
//		$('#btnRechazaCedVali').attr('disabled', 'disabled');
//		
//		
//	}else{
//		$('#btnRechazaCedVali').removeAttr('disabled');	
//		$('#btnAutorizarCedVali').attr('disabled', 'disabled');
//	}
	
}


function validAutorizacion(){
	
	var flagAutoriza=true;
	if(nivelAutorizacion==1){	
		for(var s=0;s<percepcionesCedulaVal.length;s++){
			if(!percepcionesCedulaVal[s].autorizaAclarado){
				flagAutoriza=false;				
			}
		}		
	}else if(nivelAutorizacion==2){
		for(var s=0;s<percepcionesCedulaVal.length;s++){
			if((!percepcionesCedulaVal[s].autorizaAclaradoOficioRes) || (!percepcionesCedulaVal[s].autorizaTotalPagado)){
				flagAutoriza=false;				
			}
		}		
	}else{
		$('#btnRechazaCedVali').attr('disabled', 'disabled');
		$('#btnAutorizarCedVali').attr('disabled', 'disabled');		
		return;
		
	}
	
	
	if(percepcionesCedulaVal.length==0){
		flagAutoriza=false;
	}

	if(flagAutoriza){
		$('#btnAutorizarCedVali').removeAttr('disabled');	
		$('#btnRechazaCedVali').attr('disabled', 'disabled');		
	}else{
		$('#btnRechazaCedVali').removeAttr('disabled');	
		$('#btnAutorizarCedVali').attr('disabled', 'disabled');
	}
	
}

function recalculaTotal(){
	
	recalculaPrimerNivelTotal();
	recalculaSegundoNivelTotal();
	recalculaTercerNivelNivelTotal();	
}

function recalculaPrimerNivelTotal(){
	var importePorAclarar;
	var importeAclarado;
	var diferencia;	
	var importePorAclararACM=0;
	var importeAclaradoACM=0;
	var diferenciaACM=0;
	
	for(var s=0;s<percepcionesCedulaVal.length;s++){
			diferencia=0;
			importeAclarado=0;
			importePorAclarar=0;		
			importePorAclarar=parseFloat(percepcionesCedulaVal[s].importexAclarar);
			importeAclarado=parseFloat(percepcionesCedulaVal[s].aclarado);
			diferencia=importePorAclarar-importeAclarado;
			percepcionesCedulaVal[s].diferenciaAclarado=diferencia;		
			importePorAclararACM+=importePorAclarar;
			importeAclaradoACM+=importeAclarado;
			diferenciaACM+=diferencia;
			$("#diferenciaAclarado"+s).text('$'+moneyMaskDT(diferencia,2));			
		}

	
	$("#footcedValCV1").text('Total');
	$("#footcedValCV2").text('$'+moneyMaskDT(importePorAclararACM,2));
	$("#footcedValCV3").text('$'+moneyMaskDT(importeAclaradoACM,2));
	$("#footcedValCV4").text('$'+moneyMaskDT(diferenciaACM,2));
	
}


function recalculaSegundoNivelTotal(){
	var diferencia;
	var aclaradOfRes;
	var totalPagar;
	var aclaradOfResACM=0;
	var totalPagarACM=0;
	for(var s=0;s<percepcionesCedulaVal.length;s++){		
		diferencia=0;
		aclaradOfRes=0;
		totalPagar=0;		
		diferencia=parseFloat(percepcionesCedulaVal[s].diferenciaAclarado);
		aclaradOfRes=parseFloat(percepcionesCedulaVal[s].aclaradoOficioResultados);
		totalPagar=diferencia-aclaradOfRes;
		aclaradOfResACM+=aclaradOfRes;
		totalPagarACM+=totalPagar;
		percepcionesCedulaVal[s].totalAPagar=totalPagar;
		$("#totalAPagar"+s).text('$'+moneyMaskDT(totalPagar,2));				
	}

	
	$("#footcedValCV5").text('$'+moneyMaskDT(aclaradOfResACM,2));
	$("#footcedValCV6").text('$'+moneyMaskDT(totalPagarACM,2));
}


function recalculaTercerNivelNivelTotal(){
	var diferencia;
	var total_A_Pagar;
	var totalPagado;
	var diferenciaACM=0;
	var totalPagadoACM=0;
	
	for(var s=0;s<percepcionesCedulaVal.length;s++){		
		diferencia=0;
		total_A_Pagar=0;
		totalPagado=0;				
		total_A_Pagar=parseFloat(percepcionesCedulaVal[s].totalAPagar);
		totalPagado=parseFloat(percepcionesCedulaVal[s].totalPagado);
		diferencia=total_A_Pagar-totalPagado;		
		diferenciaACM+=diferencia;
		totalPagadoACM+=totalPagado;
		percepcionesCedulaVal[s].diferenciaPagado=diferencia;
		$("#diferenciaPagado"+s).text('$'+moneyMaskDT(diferencia,2));				
	}
	
	$("#footcedValCV7").text('$'+moneyMaskDT(totalPagadoACM,2));
	$("#footcedValCV8").text('$'+moneyMaskDT(diferenciaACM,2));
}


function changeImporteAclaradoCV(idRow){
	var posicion=recuperaPosicion(idRow);
	
	$("#aclaradoCV"+idRow).val(($("#aclaradoCV"+idRow).val()).replace(/[^0-9.,]*/gi,"")); 
	
	if($("#aclaradoCV"+idRow).val()==''){
		$("#aclaradoCV"+idRow).val('0');
	}
	if(percepcionesCedulaVal[posicion].importexAclarar<parseFloat($("#aclaradoCV"+idRow).val())){
		jAlert("El valor aclarado no puede ser mayor al importe por aclarar","Info");
		$("#aclaradoCV"+idRow).val('0');
	}
	
	$("#aclaradoOficioResultados"+idRow).val('0');
	$("#totalPagado"+idRow).val('0');
	
	percepcionesCedulaVal[posicion].aclaradoOficioResultados=0;
	percepcionesCedulaVal[posicion].totalPagado=0;
	
	percepcionesCedulaVal[posicion].aclarado=quitaFormato($("#aclaradoCV"+idRow).val());
	recalculaTotal();
}


function changeAclaradoOfiResu(idRow){
	
	var posicion=recuperaPosicion(idRow);
	
	$("#aclaradoOficioResultados"+idRow).val(($("#aclaradoOficioResultados"+idRow).val()).replace(/[^0-9.,]*/gi,"")); 
	if($("#aclaradoOficioResultados"+idRow).val()==''){
		$("#aclaradoOficioResultados"+idRow).val('0');
	}
	var val=parseFloat(quitaFormato($("#diferenciaAclarado"+idRow).text().replace(/[^0-9.,]*/gi,"")));	
	if(val<parseFloat($("#aclaradoOficioResultados"+idRow).val())){
		jAlert("El valor de oficio de res no puede ser mayor a la diferencia","Info");
		$("#aclaradoOficioResultados"+idRow).val('0');
	}
	percepcionesCedulaVal[posicion].aclaradoOficioResultados=quitaFormato($("#aclaradoOficioResultados"+idRow).val());
	$("#totalPagado"+idRow).val('0');
	percepcionesCedulaVal[posicion].totalPagado=0;
	recalculaTotal();
}


function changeTotalPagado(idRow){
	
	var posicion=recuperaPosicion(idRow);	
	$("#totalPagado"+idRow).val(($("#totalPagado"+idRow).val()).replace(/[^0-9.,]*/gi,"")); 
	if($("#totalPagado"+idRow).val()==''){
		$("#totalPagado"+idRow).val('0');
	}
	
	var val=parseFloat(quitaFormato($("#totalAPagar"+idRow).text().replace(/[^0-9.,]*/gi,"")));	
	if(val<parseFloat($("#totalPagado"+idRow).val())){
		jAlert("El valor Total Pagado  no puede ser mayor a la diferencia","Info");
		$("#totalPagado"+idRow).val('0');
	}
	percepcionesCedulaVal[posicion].totalPagado=quitaFormato($("#totalPagado"+idRow).val());
	recalculaTotal();
	
}


function recuperaPosicion(idRow){
	var posicion=0;	
	for(var s=0;s<percepcionesCedulaVal.length;s++){		
		if(percepcionesCedulaVal[s].idRow==idRow){
			posicion=s;
			break;
		}	
	}
	return posicion;
}

/**
 * Función gue genera la tabla de consolidacion por cedula 
 * @author Jorge Hernandez Almazan
 */


function generaTablaConsolidadoValidacionImportes(data){
	percepcionesCedulaVal=data;

	dtConsoImportesCedula=$("#dtConsolidacionImportes").dataTable( {
		aaData: percepcionesCedulaVal,
		bAutoWidth : false,
		bFilter : false,
		bJQueryUI : true,
		bDestroy: true,
		bSort: false,
		"sScrollX": "100%",
		"sScrollXInner": "140%",
		"fnInfoCallback": function( oSettings, iStart, iEnd, iMax, iTotal, sPre ) {			   
			recalculaTotal();
			deshabilitaCamposGridCV();
			bloquearSecciones(detalleData);
		  },
		"aoColumns" : [{				
			"sTitle" : "Concepto",
			"mDataProp" : "concepto",
			"sClass": "dtCenterClassColumn",
		    "sWidth":"200px"
		},{
				
			"sTitle" : "Importe por Aclarar",
			"mDataProp" : "importexAclarar",
			"sClass": "dtCenterClassColumn",
			"sWidth":"150px",
			"fnRender": function ( o, val ) {
				return '<div align="right"><label id="importexAclarar'+o.aData['idRow']+'">$'+ moneyMaskDT(o.aData['importexAclarar'],2)+'</label></div>';
		    }
		},{
				
			"sTitle" : "Aclarado",
			"mDataProp" : "aclarado",
			"sClass": "dtCenterClassColumn",
			 "sWidth":"150px",
			"fnRender": function ( o, val ) {
				var valor=MoneyToNumber(o.aData['aclarado']);
				var val='<div align="right"><label>$:</label><input size="18"  value="'+valor+'" id="aclaradoCV'+o.aData['idRow']+'"  onclick="edit(this);" maxlength="16"  onchange="changeImporteAclaradoCV('+o.aData['idRow']+');" onblur="moneyMask(this,2);" class="inputMoney" /></div>';
				return val;
	        }			
		},{
				
			"sTitle" : "Diferencia ",
			"mDataProp" : "diferenciaAclarado",
			"sClass": "dtCenterClassColumn",
			"sWidth":"150px",
			"fnRender": function ( o, val ) {
				var componente='<div align="right"><label id="diferenciaAclarado'+o.aData['idRow']+'">'+"$ " + moneyMaskDT(o.aData['diferenciaAclarado'],2)+'</label></div>';
				return componente;
	        }			
		},{
			
			"sTitle" : "Autorizaci\u00f3n",					
			"sClass": "dtCenterClassColumn",
			"mDataProp" : "autorizaAclarado",
			"fnRender":function(o,val){
				
					var habilitado;
					if(idRolUsuario==auditor){
						habilitado='disabled';
					}
				
					if(o.aData['autorizaAclarado']){
						return '<input type="checkbox" id="autorizaAclarado'+o.aData['idRow']+'" '+habilitado+'  onchange="validaAutorizacionCedulaValid('+o.aData['idRow']+')" checked="'+o.aData['autorizaAclarado']+'">';
					}else{
						return '<input type="checkbox" id="autorizaAclarado'+o.aData['idRow']+'" '+habilitado+'  onchange="validaAutorizacionCedulaValid('+o.aData['idRow']+')" >';
					}					
				}
			},{
				
			"sTitle" : "Aclarado Oficio<br> Resultados",
			"mDataProp" : "aclaradoOficioResultados",
			"sClass": "dtCenterClassColumn",
			 "sWidth":"150px",
			"fnRender": function ( o, val ) {
				var valor=MoneyToNumber(o.aData['aclaradoOficioResultados']);
				var val='<div align="right"><label>$:</label><input size="18"  value="'+valor+'" id="aclaradoOficioResultados'+o.aData['idRow']+'" onclick="edit(this);"  maxlength="16" onchange="changeAclaradoOfiResu('+o.aData['idRow']+');"  onblur="moneyMask(this,2);" class="inputMoney" /></div>';
				return val;
	        }						
		},{
				
			"sTitle" : "Total a Pagar",
			"mDataProp" : "totalAPagar",
			"sClass": "dtCenterClassColumn",
			"sWidth":"150px",
			"fnRender": function ( o, val ) {
				var componente='<div align="right"><label id="totalAPagar'+o.aData['idRow']+'">'+"$ " + moneyMaskDT(o.aData['totalAPagar'],2)+'</label></div>';	
				return componente;
	        }			
		},{
			
			"sTitle" : "Autorizaci\u00f3n",					
			"sClass": "dtCenterClassColumn",
			"mDataProp" : "autorizaAclaradoOficioRes",
			"fnRender":function(o,val){
				
					var habilitado;
					if(idRolUsuario==auditor){
						habilitado='disabled';
					}
				
					if(o.aData['autorizaAclaradoOficioRes']){
						return '<input type="checkbox" id="autorizaAclaradoOficioRes'+o.aData['idRow']+'" '+habilitado+' onchange="validaAutorizacionCedulaValid('+o.aData['idRow']+')" checked="'+o.aData['autorizaAclaradoOficioRes']+'">';
					}else{
						return '<input type="checkbox" id="autorizaAclaradoOficioRes'+o.aData['idRow']+'" '+habilitado+' onchange="validaAutorizacionCedulaValid('+o.aData['idRow']+')" >';
					}					
				}
			},{
				
			"sTitle" : "Total Pagado",
			"mDataProp" : "totalPagado",
			"sClass": "dtCenterClassColumn",
			 "sWidth":"150px",
			"fnRender": function ( o, val ) {
				var valor=MoneyToNumber(o.aData['totalPagado']);
				var val='<div align="right"><label>$:</label><input size="18"  value="'+valor+'" id="totalPagado'+o.aData['idRow']+'" onclick="edit(this);"  maxlength="16"  onchange="changeTotalPagado('+o.aData['idRow']+');" onblur="moneyMask(this,2);" class="inputMoney" /></div>';
				return val;
	        }				
		},{
				
			"sTitle" : "Diferencia",
			"mDataProp" : "diferenciaPagado",
			"sClass": "dtCenterClassColumn",
			"sWidth":"150px",
			"fnRender": function ( o, val ) {
				var componente='<div align="right"><label id="diferenciaPagado'+o.aData['idRow']+'">'+"$ " + moneyMaskDT(o.aData['diferenciaPagado'],2)+'</label></div>';
				return componente;
	        }			
		},{
			
			"sTitle" : "Autorizaci\u00f3n",					
			"sClass": "dtCenterClassColumn",
			"mDataProp" : "autorizaTotalPagado",
			"fnRender":function(o,val){
				
					var habilitado;
					if(idRolUsuario==auditor){
						habilitado='disabled';
					}
				
					if(o.aData['autorizaTotalPagado']){
						return '<input type="checkbox" id="autorizaTotalPagado'+o.aData['idRow']+'" '+habilitado+' onchange="validaAutorizacionCedulaValid('+o.aData['idRow']+')" checked="'+o.aData['autorizaTotalPagado']+'">';
					}else{
						return '<input type="checkbox" id="autorizaTotalPagado'+o.aData['idRow']+'" '+habilitado+'  onchange="validaAutorizacionCedulaValid('+o.aData['idRow']+')">';
					}					
				}
			}]
    } );  

}


/**
 * Función gue genera la tabla de consolidacion 
 * @author Jorge Hernandez Almazan
 */


function generaTablaConsolidadoValidacion(data){
	respuesta=data;
	$("#dtConsolidadoValidacion").dataTable( {
		aaData: respuesta,
		bAutoWidth : false,
		bFilter : false,
		bJQueryUI : true,
		bDestroy: true,
		bSort: false,
		"aoColumns" : [{				
			"sTitle" : "Concepto",
			"mDataProp" : "concepto",
			"sClass": "dtCenterClassColumn"
			
		},{
				
			"sTitle" : "Importe por Aclarar",
			"mDataProp" : "importexAclarar",
			"sClass": "dtCenterClassColumn",
			"fnRender": function ( o, val ) {
				return "$ " + moneyMaskDT(o.aData['importexAclarar'],2);
		    }
		},{
				
			"sTitle" : "Aclarado",
			"mDataProp" : "aclarado",
			"sClass": "dtCenterClassColumn",
			"fnRender": function ( o, val ) {
				return "$ " + moneyMaskDT(o.aData['aclarado'],2);
	        }			
		},{
				
			"sTitle" : "Diferencia ",
			"mDataProp" : "diferenciaAclarado",
			"sClass": "dtCenterClassColumn",
			"fnRender": function ( o, val ) {
				return "$ " + moneyMaskDT(o.aData['diferenciaAclarado'],2);
	        }			
		},{
				
			"sTitle" : "Aclarado Oficio Resultados",
			"mDataProp" : "aclaradoOficioResultados",
			"sClass": "dtCenterClassColumn",
			"fnRender": function ( o, val ) {
				return "$ " + moneyMaskDT(o.aData['aclaradoOficioResultados'],2);
	        }						
		},{
				
			"sTitle" : "Total a Pagar",
			"mDataProp" : "totalAPagar",
			"sClass": "dtCenterClassColumn",
			"fnRender": function ( o, val ) {
				return "$ " + moneyMaskDT(o.aData['totalAPagar'],2);
	        }			
		},{
				
			"sTitle" : "Total Pagado",
			"mDataProp" : "totalPagado",
			"sClass": "dtCenterClassColumn",
			"fnRender": function ( o, val ) {
				return "$ " + moneyMaskDT(o.aData['totalPagado'],2);
	        }				
		},{
				
			"sTitle" : "Diferencia",
			"mDataProp" : "diferenciaPagado",
			"sClass": "dtCenterClassColumn",
			"fnRender": function ( o, val ) {
				return "$ " + moneyMaskDT(o.aData['diferenciaPagado'],2);
	        }			
		}],
		"fnFooterCallback": function( nFoot, aData, iStart, iEnd, aiDisplay ) {
			calculaTotalConsolidado();
		}
    } );  


}

function calculaTotalConsolidado(){
	

	var totImportexAclarar = 0;
	var totAclarado=0;
	var totDiferencia=0;
	var totAclaOficRes=0;
	var totAPagar=0;
	var totTotalPagado=0;
	var totalDiferencia=0;

	for(var s=0;s<respuesta.length;s++){
		totImportexAclarar+=parseFloat(respuesta[s].importexAclarar);
		totAclarado+=parseFloat(respuesta[s].aclarado);
		totDiferencia+=parseFloat(respuesta[s].diferenciaAclarado);
		totAclaOficRes+=parseFloat(respuesta[s].aclaradoOficioResultados);
		totAPagar+=parseFloat(respuesta[s].totalAPagar);
		totTotalPagado+=parseFloat(respuesta[s].totalPagado);
		totalDiferencia+=parseFloat(respuesta[s].diferenciaPagado);
	}
		
	
	$("#footcedValT1").html("Total");
	$("#footcedValT2").html("$ "+moneyMaskDT(totImportexAclarar));
	$("#footcedValT3").html("$ "+moneyMaskDT(totAclarado));
	$("#footcedValT4").html("$ "+moneyMaskDT(totDiferencia));
	$("#footcedValT5").html("$ "+moneyMaskDT(totAclaOficRes));
	$("#footcedValT6").html("$ "+moneyMaskDT(totAPagar));
	$("#footcedValT7").html("$ "+moneyMaskDT(totTotalPagado));
	$("#footcedValT8").html("$ "+moneyMaskDT(totalDiferencia));	
	$("form#formCedValidacionSeguimientoCorr #baseDeterminadaSegCorr_cedval").val("$ "+moneyMaskDT(totAPagar));
	totalDifePrimer=totDiferencia;
}

function quitaFormato(valor){
	var currentValue = valor.replace(/[\,]+/gi,"");
	return myNumber = Number(currentValue);	
}



function inicializaSeccionConsolidaImportes(){
	//alert("inicializaSeccionConsolidaImportes");
	
	var nuFolio = $('form#formRecepcionSeguimiento #nuFolioHdn').val();
	var arrayNumFolio = nuFolio.split("/");
	var tipoFolio = arrayNumFolio[1];

	
	//estado inicial de la pantalla
	
	$("form#formCedValidacionSeguimientoCorr #numeroParcialidadesSegCorr_cedval").removeClass("red");
	$("form#formCedValidacionSeguimientoCorr #cbxComprobConvenio_cedval").removeClass("red");	
	$("form#formCedValidacionSeguimientoCorr #trabRevisadosSegCorr_cedval").removeClass("red");
	$("form#formCedValidacionSeguimientoCorr #trabOmisosUniSegCorr_cedval").removeClass("red");
	$("form#formCedValidacionSeguimientoCorr #trabSubdeclaUniSegCorr_cedval").removeClass("red");	
	$("form#formCedValidacionSeguimientoCorr #suertePpalDetCopSegCorr_cedval").removeClass("red");
	$("form#formCedValidacionSeguimientoCorr #suertePpalDetRcvSegCorr_cedval").removeClass("red");
	$("form#formCedValidacionSeguimientoCorr #numeroParcialidadesSegCorr_cedval").removeClass("red");
	$("form#formCedValidacionSeguimientoCorr #cbxComprobConvenio_cedval").attr('checked', false);
	
	$("form#formCedValidacionSeguimientoCorr #numeroParcialidadesSegCorr_cedval").prop('disabled','disabled');
	$("form#formCedValidacionSeguimientoCorr #cbxComprobConvenio_cedval").prop('disabled','disabled');	
	$("form#formCedValidacionSeguimientoCorr #trabRevisadosSegCorr_cedval").prop('disabled','disabled');
	$("form#formCedValidacionSeguimientoCorr #trabOmisosUniSegCorr_cedval").prop('disabled','disabled');
	$("form#formCedValidacionSeguimientoCorr #trabSubdeclaUniSegCorr_cedval").prop('disabled','disabled');	
	$("form#formCedValidacionSeguimientoCorr #suertePpalDetCopSegCorr_cedval").prop('disabled','disabled');
	$("form#formCedValidacionSeguimientoCorr #suertePpalDetRcvSegCorr_cedval").prop('disabled','disabled');
	$("form#formCedValidacionSeguimientoCorr #numeroParcialidadesSegCorr_cedval").prop('disabled','disabled');
	$("form#formCedValidacionSeguimientoCorr #cbxComprobConvenio_cedval").prop('disabled','disabled');
	$("form#formCedValidacionSeguimientoCorr #btnDatosRegularizacionSegCorr_cedval").prop('disabled','disabled');
	
	
	$("form#formCedValidacionSeguimientoCorr #porcentajeAvanceSegCorr_cedval").prop('disabled','disabled');
	$("form#formCedValidacionSeguimientoCorr #porcentajeRegularizadoSegCorr_cedval").prop('disabled','disabled');
	$("form#formCedValidacionSeguimientoCorr #baseDeterminadaSegCorr_cedval").prop('disabled','disabled');
	
}

/**
 * FUncion que se ejecuta cuando existe ya guardada una fecha de notificacion y cuando ya se checaron todos los cheks de la 
 * pantalla de validacion
 */
function activaSeccionConsolidaImportes(){
	//alert("activaSeccionConsolidaImportes");
	var nuFolio = $('form#formRecepcionSeguimiento #nuFolioHdn').val();
	var arrayNumFolio = nuFolio.split("/");
	var tipoFolio = arrayNumFolio[1];
	
	if(tipoFolio =='CCI' || tipoFolio == 'CCE'){
		$("form#formCedValidacionSeguimientoCorr #porcentajeAvanceSegCorr_cedval").addClass("red");
		$("form#formCedValidacionSeguimientoCorr #porcentajeRegularizadoSegCorr_cedval").addClass("red");
		$("form#formCedValidacionSeguimientoCorr #porcentajeAvanceSegCorr_cedval").removeAttr('disabled');
		$("form#formCedValidacionSeguimientoCorr #porcentajeRegularizadoSegCorr_cedval").removeAttr('disabled');
	}else{
		$("form#formCedValidacionSeguimientoCorr #porcentajeAvanceSegCorr_cedval").removeClass("red");
		$("form#formCedValidacionSeguimientoCorr #porcentajeRegularizadoSegCorr_cedval").removeClass("red");
		$("form#formCedValidacionSeguimientoCorr #porcentajeAvanceSegCorr_cedval").prop('disabled','disabled');
		$("form#formCedValidacionSeguimientoCorr #porcentajeRegularizadoSegCorr_cedval").prop('disabled','disabled');
		$("form#formCedValidacionSeguimientoCorr #porcentajeAvanceSegCorr_cedval").val('');
		$("form#formCedValidacionSeguimientoCorr #porcentajeRegularizadoSegCorr_cedval").val('');
		
		
	}

	//estado inicial de la pantalla
	$("form#formCedValidacionSeguimientoCorr #numeroParcialidadesSegCorr_cedval").addClass("red");
	$("form#formCedValidacionSeguimientoCorr #cbxComprobConvenio_cedval").addClass("red");	
	$("form#formCedValidacionSeguimientoCorr #trabRevisadosSegCorr_cedval").addClass("red");
	$("form#formCedValidacionSeguimientoCorr #trabOmisosUniSegCorr_cedval").addClass("red");
	$("form#formCedValidacionSeguimientoCorr #trabSubdeclaUniSegCorr_cedval").addClass("red");	
	$("form#formCedValidacionSeguimientoCorr #suertePpalDetCopSegCorr_cedval").addClass("red");
	$("form#formCedValidacionSeguimientoCorr #suertePpalDetRcvSegCorr_cedval").addClass("red");
	//$("form#formCedValidacionSeguimientoCorr #cbxComprobConvenio_cedval").attr('checked', false);
	
	$("form#formCedValidacionSeguimientoCorr #numeroParcialidadesSegCorr_cedval").removeAttr('disabled');
	$("form#formCedValidacionSeguimientoCorr #cbxComprobConvenio_cedval").removeAttr('disabled');	
	$("form#formCedValidacionSeguimientoCorr #trabRevisadosSegCorr_cedval").removeAttr('disabled');
	$("form#formCedValidacionSeguimientoCorr #trabOmisosUniSegCorr_cedval").removeAttr('disabled');
	$("form#formCedValidacionSeguimientoCorr #trabSubdeclaUniSegCorr_cedval").removeAttr('disabled');	
	$("form#formCedValidacionSeguimientoCorr #suertePpalDetCopSegCorr_cedval").removeAttr('disabled');
	$("form#formCedValidacionSeguimientoCorr #suertePpalDetRcvSegCorr_cedval").removeAttr('disabled');
	$("form#formCedValidacionSeguimientoCorr #cbxComprobConvenio_cedval").removeAttr('disabled');
	
	//$("form#formCedValidacionSeguimientoCorr #numeroParcialidadesSegCorr_cedval").val('');
	
	$("form#formCedValidacionSeguimientoCorr #btnDatosRegularizacionSegCorr_cedval").removeAttr('disabled');
	$("form#formCedValidacionSeguimientoCorr #btnGuardarValidacion").removeAttr('disabled');
}


/**
 * Función que valida que los datos capturados en las text de tipo porcentaje , sean mayor a cero, y menores o iguales a 100
 * @author Oscar German Beltrán Ortega
 */
function validaPorcentajeConsolImpteCedVal(campo,labelMsg){
	var respuesta= true;
	if(idRolUsuario==auditor){
		
		var valorValidar = $("form#formCedValidacionSeguimientoCorr #" +campo).val();
		$("form#formCedValidacionSeguimientoCorr #" + labelMsg).html('');

		if(valorValidar != ''){
			if(parseInt(valorValidar,10) >0 && parseInt(valorValidar,10)<= 100){
				respuesta = true;
			}else{
			//caso erroneo
				$("form#formCedValidacionSeguimientoCorr #" + labelMsg).html('<label class="etiquetaError">El valor debe ser entre 1 y 100 </label>');
				respuesta = false;
			
			}
		}
	}
		return respuesta;
}

function validaParcialidadesSegCorrCedVal(){
	var respuesta = false;
	if($("form#formCedValidacionSeguimientoCorr #numeroParcialidadesSegCorr_cedval").val() != '' ){
		var valorNumParcialid = parseInt($("form#formCedValidacionSeguimientoCorr #numeroParcialidadesSegCorr_cedval").val());
		
		if(valorNumParcialid>0 && valorNumParcialid <= 48){
			respuesta = true;
			$("form#formCedValidacionSeguimientoCorr #labelNumParcialidiSegCorr_cedval").html('');
		}else{
			//mensaje de validacion
			respuesta = false
			
			$("form#formCedValidacionSeguimientoCorr #labelNumParcialidiSegCorr_cedval").html('<label class="etiquetaError">Valor de 1 a 48</label>');
		}
		
	}
	if($("form#formCedValidacionSeguimientoCorr #numeroParcialidadesSegCorr_cedval").val() == null ){
		$("form#formCedValidacionSeguimientoCorr #numeroParcialidadesSegCorr_cedval").val('')
		respuesta = true;
	}else if($("form#formCedValidacionSeguimientoCorr #numeroParcialidadesSegCorr_cedval").val()==''){
		respuesta = true;
	}
	
	return respuesta;
}


/**
 * Función que realiza los calculos para obtener el total de Trabajadores
 * regularizados
 * @author Oscar German Beltrán Ortega
 */
function calcTotalTrabRegularizadoConsolImpteCedVal(){
	
	var valorTotalReg = 0;
	var valorTrabSub = 0;
	var valorOmisosUni = 0; 
	

	if(document.getElementById("trabSubdeclaUniSegCorr_cedval").value != ''){
		valorTrabSub = parseIntComas(document.getElementById("trabSubdeclaUniSegCorr_cedval").value,10);
	}
	if(document.getElementById("trabOmisosUniSegCorr_cedval").value !=''){
		valorOmisosUni = parseIntComas(document.getElementById("trabOmisosUniSegCorr_cedval").value,10);
	}
	
	
	valorTotalReg = valorTrabSub +  valorOmisosUni;
	
	var myNumber = Number(valorTotalReg);
	$("form#formCedValidacionSeguimientoCorr #trabRegularizadosRegObraSegCorr_cedval").val(myNumber.formatMoney(0, '.', ','));
	validaTrabRegularizadosConsolImpteVal();
}


/**
 * Función  de validacion de los datos requeridos y datos validos para el tab de regularizar obra
 * @author Oscar German Beltrán Ortega
 */
function validaTrabRegularizadosConsolImpteVal(){
	var jsTrabRevisadosRegularObraConsolImpteVal =  "form#formCedValidacionSeguimientoCorr #trabRevisadosSegCorr_cedval";
	$("form#formCedValidacionSeguimientoCorr #labelTrabRegularizadosRegObraSegCorr_cedval").html('');
	var trabajosRegularizados = parseIntComas($("form#formCedValidacionSeguimientoCorr #trabRegularizadosRegObraSegCorr_cedval").val() != ''?$("form#formCedValidacionSeguimientoCorr #trabRegularizadosRegObraSegCorr_cedval").val():"0",10);
	var trabajosRevisados = parseIntComas($(jsTrabRevisadosRegularObraConsolImpteVal).val() != '' ? $(jsTrabRevisadosRegularObraConsolImpteVal).val():"0",10);
	var respuesta = true;

	//COmentado pq no aplica aqui la regla
	if(trabajosRegularizados <= trabajosRevisados){
		respuesta = true
		
	}else{
		
		$("form#formCedValidacionSeguimientoCorr #labelTrabRegularizadosRegObraSegCorr_cedval").html('<label class="etiquetaError"  align="rigth">La suma de los trab. Omisos + trab. Subdeclarados No puede ser MAYOR que los Trabajadores Revisados</label>');
		respuesta = false;
	}
	
	return respuesta;
}



function guardaCedulaValidacion(){
	//if(validaDatosConsolidacionCedVal()){
	
	jConfirm("\u00BFEsta seguro que desea aplicar los cambios?","Confirmar",guardar);

	
	function guardar(valor){
		if(!valor){
			return;
		}
		
		var oForm = $("#formCedValidacionSeguimientoCorr").toObject(true);
		oForm.cveAnexoSolCorrPat=$("form#formCedValidacionSeguimientoCorr #regPatronal").val();
		oForm.numFolio=$("#labelFolioCorr").text();
		oForm.listaPercepciones=percepcionesCedulaVal;
		oForm.cveEjercicio=$("form#formCedValidacionSeguimientoCorr #numEjercicio").val();
		oForm.cvePresentaCorr=$("#cvePresentaCorreccionHdnSeg").val();
		bloquear();
		$.postJSON_Sync("correccion/guardarCedulaValidacionSeg.do", oForm, function(data) {	
			cv_cvePresentaCorr = $("#cvePresentaCorreccionHdnSeg").val();
			var sVarSeg = '{"cvePresentaCorr":"'+cv_cvePresentaCorr+'","cveSolCorr":"'+cveSolcorr+'"}';
			var clase = jQuery.parseJSON(sVarSeg);	
			jAlert(data.resultado,"Info");
			$.postJSON("correccion/consultaCedValidacion.do", clase, function(data) {				
				generaTablaConsolidadoValidacion(data.listaConsolidados);
				llenaConsolidacionImportesExistente(data);
				activaSeccionConsolidaImportes();
				//$("form#formCedValidacionSeguimientoCorr #btnDatosRegularizacionSegCorr_cedval").removeAttr('disabled');
			});
			ejecutaReglasValidacion(idRolUsuario);
		}).error(function(data){ 					
			validarSesionExpirada(data);
		}).complete(function(){
			desbloquear();		
			ejecutaReglasValidacion();
		});
		
	}
	//}
	
}


function finalizaCedulaValidacion(){
	//guardaCedulaValidacion();
	
	
	jConfirm("Una vez finalizado ya no se podr\u00e1n modificar ninguna c\u00e9dula , \u00BFDesea Continuar?","Confirmar",finaliza);
	function finaliza(valor){
			if(!valor){
				return;
			}
			if(nivelAutorizacion==undefined){
				jAlert("No se puede finalizar ,verifique que el proceso este completo","Informaci\u00f3n");
				return;
			}
			
			var oForm = $("#formCedValidacionSeguimientoCorr").toObject(true);
			
			oForm.cveAnexoSolCorrPat=$("form#formCedValidacionSeguimientoCorr #regPatronal").val();
			oForm.cveEjercicio=$("form#formCedValidacionSeguimientoCorr #numEjercicio").val();	
			oForm.listaPercepciones=percepcionesCedulaVal;
			oForm.cvePresentaCorr=$("#cvePresentaCorreccionHdnSeg").val();
			oForm.seccionAutoriza=nivelAutorizacion;
			bloquear();
			$.postJSON("correccion/finalizaCedulaValidacion.do", oForm, function(data) {
				jAlert(data.resultado,"Info");
				getDetalleValidacion();
				ejecutaReglasValidacion(idRolUsuario);
			}).error(function(data){ 					
				validarSesionExpirada(data);
			}).complete(function(){
				desbloquear();
				ejecutaReglasValidacion(idRolUsuario);										
			});	
	}
}


function autorizaCedulaValida(){
	
	jConfirm("\u00BFEsta seguro de autorizar las c\u00e9dulas?","Confirmar",function (valor){
		if(!valor){
			return;
		}
		var oForm = $("#formCedValidacionSeguimientoCorr").toObject(true);
		oForm.cveAnexoSolCorrPat=$("form#formCedValidacionSeguimientoCorr #regPatronal").val();
		oForm.listaPercepciones=percepcionesCedulaVal;
		oForm.cveEjercicio=$("form#formCedValidacionSeguimientoCorr #numEjercicio").val();
		oForm.cvePresentaCorr=$("#cvePresentaCorreccionHdnSeg").val();
		oForm.seccionAutoriza=nivelAutorizacion;
		oForm.numFolio=folioSeguimientoCorreccion;
		bloquear();
		$.postJSON_Sync("correccion/autorizaCedulaValidacion.do", oForm, function(data) {
			jAlert(data.resultado,"Autorizacion");	
			getDetalleValidacion();
			ejecutaReglasValidacion(idRolUsuario);
		}).error(function(data){ 					
			validarSesionExpirada(data);
		}).complete(function(){
			desbloquear();		
			 ejecutaReglasValidacion(idRolUsuario);
		});	
	});
	
	
	
}

function rechazaCedulaValida(){
	
	jConfirm("\u00BFEsta seguro de rechazar las c\u00e9dulas?","Confirmar",rechazaCed);
	
	function rechazaCed(valor){
			if(!valor){
				return;
			}
			
			if($("form#formCedValidacionSeguimientoCorr #regPatronal").val()=='-1' || $("form#formCedValidacionSeguimientoCorr #numEjercicio").val()=='-1'){
				jAlert("Seleccione una c\u00e9dula","Alerta");
				return;
			}
			var oForm = $("#formCedValidacionSeguimientoCorr").toObject(true);
			oForm.cveAnexoSolCorrPat=$("form#formCedValidacionSeguimientoCorr #regPatronal").val();
			oForm.listaPercepciones=percepcionesCedulaVal;
			oForm.cveEjercicio=$("form#formCedValidacionSeguimientoCorr #numEjercicio").val();
			oForm.cvePresentaCorr=$("#cvePresentaCorreccionHdnSeg").val();
			oForm.seccionAutoriza=nivelAutorizacion;
			
			
			bloquear();
			$.postJSON("correccion/rechazaCedulaValidacion.do", oForm, function(data) {			
				jAlert(data.resultado,"Rechazo");	
				getDetalleValidacion();
				ejecutaReglasValidacion(idRolUsuario);
			}).error(function(data){ 					
				validarSesionExpirada(data);
			}).complete(function(){
				desbloquear();			
				ejecutaReglasValidacion(idRolUsuario);
			});	
	}
}
function openDialogoPagosCedula_Valida(){
	var fechaOficioRe =  $("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").val();
	if(validaFechaOficioResultados() && validaSuertesPrincipal()){
		if(confirm("Este proceso puede tardar varios minutos, desea continuar?")){
			
			bloquear();
			
			var periodoInicialPag = $("form#seguimientoCorreccionForm #fecFechaPeriodoIniSegHdn").val();
			var periodoFinalPag = $("form#seguimientoCorreccionForm #fecFechaPeriodoFinSegHdn").val();
			var folioCorreccionPag= $("form#seguimientoCorreccionForm #nuFolioSegCorrHdn").val();
			var cvePresentacionCorr = $("form#seguimientoCorreccionForm #cvePresentaCorreccionHdnSeg").val();
			var indTipoPagoCorr = "2";
			
			
			
			var accion = "seguimiento/ec/pagos.do?periodoInicial=" + periodoInicialPag
			     +"&periodoFinal="+periodoFinalPag+"&folioCorreccion="+folioCorreccionPag
			     +"&idPresentacion="+cvePresentacionCorr+"&indTipoPago="+indTipoPagoCorr+"&fechaMinDateCalendar="+fechaOficioRe;

	
			//regresa mayor a 0 en caso de que se haya guardado un pago en la pantalla de pagos
			var respuesta = openWindowPagosSeguimientoFII(getAppContextParaJS(),accion);
	
			//variable para almacenar el numero de pagos
			numeroPagosPorCorreccionCedVal = respuesta;
			
			
			//salirPagosTemporalCedVal();
			if(numeroPagosPorCorreccionCedVal != 0){
				
				continuaFlujoPagosCedVal();
				obtenerTotalesPagosCedVal("flujoNormal");
				
				//si es auditor se habilitan validaciones para desbloquear conclusion
				if(!(idRolUsuario==JEFE_OF_CORRECCION || idRolUsuario==JEFE_DEP_AUD_PAT ||
						idRolUsuario==SUPERVISOR_OF_CORRECCION || idRolUsuario==JEFE_OF_CORRECCION_Y_DICTAMEN)){
					reglasAuditor();
				}
				
				
			}
			desbloquear();
		}else{
			alert("Cancelo.");
		}
	}
	
}





function continuaFlujoPagosCedVal(){

	if($("form#formCedValidacionSeguimientoCorr #cveConsolidaImporteCedValHdn").val() == ''  ){

		//habilitar la seccion de trabajadores
		$("form#formCedValidacionSeguimientoCorr #trabRevisadosSegCorr_cedval").removeAttr('disabled');
		$("form#formCedValidacionSeguimientoCorr #trabRevisadosSegCorr_cedval").addClass("red");
		
		$("form#formCedValidacionSeguimientoCorr #trabOmisosUniSegCorr_cedval").removeAttr('disabled');
		$("form#formCedValidacionSeguimientoCorr #trabOmisosUniSegCorr_cedval").addClass("red");
		
		$("form#formCedValidacionSeguimientoCorr #trabSubdeclaUniSegCorr_cedval").removeAttr('disabled');
		$("form#formCedValidacionSeguimientoCorr #trabSubdeclaUniSegCorr_cedval").addClass("red");
		
		
	}
	
}



function obtenerTotalesPagosCedVal(bandera){

	var nuFolioCorreccion =	$('form#seguimientoCorreccionForm #nuFolioSegCorrHdn').val();

	var sNuFolioCorr = '{"nuFolio":"'+nuFolioCorreccion+'",'+
	'"banderaTipoPago":"2"}';
	var corrSeguimientoVO = jQuery.parseJSON(sNuFolioCorr);
	var respuesta = true;
	
	$.postJSON_Sync("correccion/obtenerTotalesPagosSolCorr.do", corrSeguimientoVO, function(data) {
		
		if(data != null){
			
			
			var myNumber = Number(data.impCopsp);
			$('form#formCedValidacionSeguimientoCorr #suertePrincipalCopSegCorr_cedval').val(myNumber.formatMoney(2, '.', ','));
			myNumber = Number(data.impRcvsp);
			$('form#formCedValidacionSeguimientoCorr #suertePrincipalRcvSegCorr_cedval').val(myNumber.formatMoney(2, '.', ','));
			
			myNumber = Number(data.impCopact);
			$('form#formCedValidacionSeguimientoCorr #actualizacionCopSegCorr_cedval').val(myNumber.formatMoney(2, '.', ','));
			myNumber = Number(data.impRcvact);
			$('form#formCedValidacionSeguimientoCorr #actualizacionRcvSegCorr_cedval').val(myNumber.formatMoney(2, '.', ','));
			
			myNumber = Number(data.impCoprec);
			$('form#formCedValidacionSeguimientoCorr #recargosCopSegCorr_cedval').val(myNumber.formatMoney(2, '.', ','));
			myNumber = Number(data.impRcvrec);
			$('form#formCedValidacionSeguimientoCorr #recargosRcvSegCorr_cedval').val(myNumber.formatMoney(2, '.', ','));
		
			/*myNumber = Number(data.impCopmulta);
			$('form#formCedValidacionSeguimientoCorr #multasCopSegCorr').val(myNumber.formatMoney(2, '.', ','));
			myNumber = Number(data.impRcvmulta);
			$('form#formCedValidacionSeguimientoCorr #multasRcvSegCorr').val(myNumber.formatMoney(2, '.', ','));*/
			
			myNumber = Number(data.impCoptot);
			$('form#formCedValidacionSeguimientoCorr #totalPagadoCopSegCorr_cedval').val(myNumber.formatMoney(2, '.', ','));
			myNumber = Number(data.impRcvtot);
			$('form#formCedValidacionSeguimientoCorr #totalPagadoRcvSegCorr_cedval').val(myNumber.formatMoney(2, '.', ','));
		
			calculaSuertePpalPentPagoCedVal(data);
			
			//RN08
			var selCbxPagos = $("form#formCedValidacionSeguimientoCorr #cbRecPagoTra").attr('checked');
			var suertPpalAutDetCop =$('form#formCedValidacionSeguimientoCorr #suertePpalDetCopSegCorr_cedval').val();
			var suertePpalPendPagCop = $('form#formCedValidacionSeguimientoCorr #suertePpalPenPagoCopSegCorr_cedval').val();
			
			var suertPpalAutDetRcv =$('form#formCedValidacionSeguimientoCorr #suertePpalDetRcvSegCorr_cedval').val();
			var suertePpalPendPagRcv = $('form#formCedValidacionSeguimientoCorr #suertePpalPenPagoRcvSegCorr_cedval').val();
			
			var cvePresnetaCorr =  $('form#seguimientoCorreccionForm #cvePresentaCorreccionHdnSeg').val();
			/*if(selCbxPagos == 'checked' && ((parseIntComas(suertPpalAutDetCop)>0 && parseIntComas(suertePpalPendPagCop)==0 ) 
					&& (parseIntComas(suertPpalAutDetRcv)>0 && parseIntComas(suertePpalPendPagRcv)==0 ))  ){
				//alert("RN08");
				$('form#formRecepcionSeguimiento #estatusPresentaCorrecConsolida').val("Correccion Pagada");
			}*/	
			
			if(suertPpalAutDetCop == ''){
				suertPpalAutDetCop = 0.00;
			}
			if(suertPpalAutDetRcv == ''){
				suertPpalAutDetRcv = 0.00;
			}
			
			if(bandera == "flujoNormal" && ( parseFloat(suertPpalAutDetCop)>0 && parseFloat(suertPpalAutDetRcv) && ( parseFloat(suertePpalPendPagCop)<=0 )&&  parseFloat(suertePpalPendPagRcv)<=0 )){
				//alert("paso la validacion de los pagos");
				guardaEstatusCrtRevRecepcionCedVal(cvePresnetaCorr, "16", "1");	
			}
			
			//  
		}

	}).error(function(data) {
		validarSesionExpirada(data);
		alert("error"+data);
	}).complete(function(){				
		desbloquear();
		
	});	
}

function edit(obj){	
	obj.value=quitaFormato(obj.value);
}

function calculaSuertePpalPentPagoCedVal(data){
	
	//calculo de Suerte ppal pent pago
	//Suerte Ppal Aut - Suerte ppal pagada
	var suertPpalAutDetCop =$('form#formCedValidacionSeguimientoCorr #suertePpalDetCopSegCorr_cedval').val() != '' ?$('form#formCedValidacionSeguimientoCorr #suertePpalDetCopSegCorr_cedval').val():"0";
	var suertPpalAutDetRcv =$('form#formCedValidacionSeguimientoCorr #suertePpalDetRcvSegCorr_cedval').val() != '' ? $('form#formCedValidacionSeguimientoCorr #suertePpalDetRcvSegCorr_cedval').val():"0";
	var suertePpalPentPagoCop = null;
	var suertePpalPentPagoRcv = null;
	if(data == null){	
	 suertePpalPentPagoCop = parseIntComas(suertPpalAutDetCop) - parseInt($('form#formCedValidacionSeguimientoCorr #suertePrincipalCopSegCorr_cedval').val());
	 suertePpalPentPagoRcv = parseIntComas(suertPpalAutDetRcv) - parseInt($('form#formCedValidacionSeguimientoCorr #suertePrincipalRcvSegCorr_cedval').val());
	} else{
		suertePpalPentPagoCop = parseIntComas(suertPpalAutDetCop) - parseIntComas(data.impCopsp);
		 suertePpalPentPagoRcv = parseIntComas(suertPpalAutDetRcv) - parseIntComas(data.impRcvsp);
	}
	var myNumber = Number(suertePpalPentPagoCop);
	$('form#formCedValidacionSeguimientoCorr #suertePpalPenPagoCopSegCorr_cedval').val(myNumber.formatMoney(2, '.', ',')); 
	myNumber = Number(suertePpalPentPagoRcv);
	$('form#formCedValidacionSeguimientoCorr #suertePpalPenPagoRcvSegCorr_cedval').val(myNumber.formatMoney(2, '.', ',')); 
}

function limpiarFormularioCedulaValidacion(){
	$('#formCedValidacionSeguimientoCorr').each (function(){
		this.reset();		
	});
	var options = "<option value='-1' >--Por favor seleccione--</option>";
	$("form#formCedValidacionSeguimientoCorr #regPatronal").html(options);
	

	if(percepcionesCedulaVal!=undefined)
	percepcionesCedulaVal.splice(0);
	generaTablaVacia();
	
}

function generaTablaVacia(){
	percepcionesCedulaVal=new Array();

	dtConsoImportesCedula=$("#dtConsolidacionImportes").dataTable( {
		aaData: percepcionesCedulaVal,
		bAutoWidth : false,
		bFilter : false,
		bJQueryUI : true,
		bDestroy: true,
		bSort: false,
		"aoColumns" : [{				
			"sTitle" : "Concepto",
			"mDataProp" : "concepto",
			"sClass": "dtCenterClassColumn",
		    "sWidth":"200px"
		},{
				
			"sTitle" : "Importe por Aclarar",
			"mDataProp" : "importexAclarar",
			"sClass": "dtCenterClassColumn",
			"sWidth":"150px"
		},{
				
			"sTitle" : "Aclarado",
			"mDataProp" : "aclarado",
			"sClass": "dtCenterClassColumn",
			 "sWidth":"150px"			
		},{
				
			"sTitle" : "Diferencia ",
			"mDataProp" : "diferenciaAclarado",
			"sClass": "dtCenterClassColumn",
			"sWidth":"150px"		
		},{
			
			"sTitle" : "Autorizaci\u00f3n",					
			"sClass": "dtCenterClassColumn",
			"mDataProp" : "autorizaAclarado"
			},{
				
			"sTitle" : "Aclarado Oficio<br> Resultados",
			"mDataProp" : "aclaradoOficioResultados",
			"sClass": "dtCenterClassColumn",
			 "sWidth":"150px"						
		},{
				
			"sTitle" : "Total a Pagar",
			"mDataProp" : "totalAPagar",
			"sClass": "dtCenterClassColumn",
			"sWidth":"150px"			
		},{
			
			"sTitle" : "Autorizaci\u00f3n",					
			"sClass": "dtCenterClassColumn",
			"mDataProp" : "autorizaAclaradoOficioRes"
			},{
				
			"sTitle" : "Total Pagado",
			"mDataProp" : "totalPagado",
			"sClass": "dtCenterClassColumn",
			 "sWidth":"150px"				
		},{
				
			"sTitle" : "Diferencia",
			"mDataProp" : "diferenciaPagado",
			"sClass": "dtCenterClassColumn",
			"sWidth":"150px"			
		},{
			
			"sTitle" : "Autorizaci\u00f3n",					
			"sClass": "dtCenterClassColumn",
			"mDataProp" : "autorizaTotalPagado"
			}]
    } );  

}


function bloqueaConsolidacionSupervisor(){
		
	$( "form#formCedValidacionSeguimientoCorr #porcentajeAvanceSegCorr_cedval").prop('readonly', true);
	$( "form#formCedValidacionSeguimientoCorr #porcentajeAvanceSegCorr_cedval").removeClass("red");
	
	$("form#formCedValidacionSeguimientoCorr #porcentajeRegularizadoSegCorr_cedval").prop('readonly', true);
	$("form#formCedValidacionSeguimientoCorr #porcentajeRegularizadoSegCorr_cedval").removeClass("red");
	
	$("form#formCedValidacionSeguimientoCorr #numeroParcialidadesSegCorr_cedval").prop('readonly', true);
	$("form#formCedValidacionSeguimientoCorr #numeroParcialidadesSegCorr_cedval").removeClass("red");
	
	$("form#formCedValidacionSeguimientoCorr #trabRevisadosSegCorr_cedval").prop('readonly', true);
	$("form#formCedValidacionSeguimientoCorr #trabRevisadosSegCorr_cedval").removeClass("red");
	
	$("form#formCedValidacionSeguimientoCorr #trabOmisosUniSegCorr_cedval").prop('readonly', true);
	$("form#formCedValidacionSeguimientoCorr #trabOmisosUniSegCorr_cedval").removeClass("red");
	
	$("form#formCedValidacionSeguimientoCorr #trabSubdeclaUniSegCorr_cedval").prop('readonly', true);
	$("form#formCedValidacionSeguimientoCorr #trabSubdeclaUniSegCorr_cedval").removeClass("red");
	

	
}

function validaFechaOficioResultados(){
	var resultado = false;
	var fechaOficioRe =  $("form#ofSeguimientoCorreccionForm #fecEmiORSegCorr").val();

	if(fechaOficioRe == null || fechaOficioRe == undefined  || fechaOficioRe == ""){
		resultado = false;
		alert("No se ha ingresado Fecha de Emisi\u00F3n del Oficio de Resultados");
	}else{
		resultado = true
	}
	
	return resultado;
	
}


function validaSuertesPrincipal(){
	var resultado=false;
	if($("#suertePpalDetCopSegCorr_cedval").val()=='' || $("#suertePpalDetRcvSegCorr_cedval").val()==''){
		alert(" Antes de continuar favor de capturar Suerte Principal Determinada ");
		resultado=false;
	}else{
		resultado=true;
	}
	return resultado;
}

/**
 * Valida el monto de 
 * La Suerte Principal de la C.O.P, DEBE ser MENOR a la BASE DETERMINADA y NO puede ser IGUAL a CERO
 * @author Oscar Beltran
 */
function validaMontoSuertePpalBaseDeterminadaCedVal(){
	
	var resultado = false;
	var suertePpalDetCop =  parseFloatComas($("form#formCedValidacionSeguimientoCorr #suertePpalDetCopSegCorr_cedval").val());
	var baseDeterminada =  parseFloatComas(replaceAll($("form#formCedValidacionSeguimientoCorr #baseDeterminadaSegCorr_cedval").val(),"$",""));
	$("form#formCedValidacionSeguimientoCorr #labelSuertePpalDetCopSegCorr_cedval").html('');
	
	if((suertePpalDetCop != null && suertePpalDetCop != "") &&  (baseDeterminada != null && baseDeterminada != "")){
		if(parseFloatComas(suertePpalDetCop) < parseFloatComas(baseDeterminada)){
			if(parseFloatComas(suertePpalDetCop)<= 0){
				$("form#formCedValidacionSeguimientoCorr #labelSuertePpalDetCopSegCorr_cedval").html('<label class="etiquetaError">Suerte Ppal COP no puede ser igual a 0</label>');
				resultado = false;
			}else{
				resultado = true;
			}
			
		}else {
			$("form#formCedValidacionSeguimientoCorr #labelSuertePpalDetCopSegCorr_cedval").html('<label class="etiquetaError">Debe ser menor a Base Determinada</label>');
			resultado = false;
		}
	}else {
		resultado = true;
	}
	return 	resultado;
}


/**
 * Valida el monto de 
 * La Suerte Principal del R.C.V, DEBE ser MENOR a la BASE DETERMINADA y SI puede ser IGUAL a CERO.
 * @author Oscar Beltran
 */
function validaMontoSuertePpalRCVBaseDeterminadaCedVal(){
	
	var resultado = false;
	var suertePpalDetRCV =  parseFloatComas($("form#formCedValidacionSeguimientoCorr #suertePpalDetRcvSegCorr_cedval").val());
	var baseDeterminada =  parseFloatComas(replaceAll($("form#formCedValidacionSeguimientoCorr #baseDeterminadaSegCorr_cedval").val(),"$",""));

	$("form#formCedValidacionSeguimientoCorr #labelSuertePpalDetRcvSegCorr_cedval").html('');
	
	if((suertePpalDetRCV != null && suertePpalDetRCV != "") &&  (baseDeterminada != null && baseDeterminada != "")){
		if(parseFloatComas(suertePpalDetRCV) < parseFloatComas(baseDeterminada)){
			//alert("correcto");
			/*if(parseFloatComas(suertePpalDetRCV)<= 0){
				$("form#regularizarObraGenericoTABForm #labelSuertePpalDetRcv").html('<label class="etiquetaError">Suerte Ppal COP no puede ser igual a 0</label>');
				resultado = false;
			}else{
				resultado = true;
			}*/
			resultado = true;
		}else {
			$("form#formCedValidacionSeguimientoCorr #labelSuertePpalDetRcvSegCorr_cedval").html('<label class="etiquetaError">Debe ser menor a Base Determinada</label>');
			//alert("debe ser menor");
			resultado = false;
		}
	}else{
		resultado = true;
	}
	
	return 	resultado;
}



/**
 * Función de validacion de los datos requeridos y de datos ingresados validos
 * @author Oscar German Beltrán Ortega
 */
function validaDatosConsolidacionCedVal(){
	
	var regresa = false;
	
	 
	 var fechaNotOR = $('form#ofSeguimientoCorreccionForm #fecNotORSegCorr').val(); 	
	if(indAutoPrimera == 2 && indAutoSegunda == 2 && ( fechaNotOR != null && fechaNotOR != undefined && fechaNotOR != '' )){
		$("form#formCedValidacionSeguimientoCorr #labelTrabRevisadosSegCorr_cedval").html('');
		$("form#formCedValidacionSeguimientoCorr #labelTrabOmisosUniSegCorr_cedval").html('');
		$("form#formCedValidacionSeguimientoCorr #labelTrabSubdeclaUniSegCorr_cedval").html('');
		$("form#formCedValidacionSeguimientoCorr #labelSuertePpalDetCopSegCorr_cedval").html('');
		$("form#formCedValidacionSeguimientoCorr #labelSuertePpalDetRcvSegCorr_cedval").html('');
		
		
		if($("form#formCedValidacionSeguimientoCorr #trabRevisadosSegCorr_cedval").val() == ''){
			$("form#formCedValidacionSeguimientoCorr #labelTrabRevisadosSegCorr_cedval").html('<label class="etiquetaError">Campo Requerido</label>');
		}
		if($("form#formCedValidacionSeguimientoCorr #trabOmisosUniSegCorr_cedval").val() == ''){
			$("form#formCedValidacionSeguimientoCorr #labelTrabOmisosUniSegCorr_cedval").html('<label class="etiquetaError">Campo Requerido</label>');
		}
		if($("form#formCedValidacionSeguimientoCorr #trabSubdeclaUniSegCorr_cedval").val() == ''){
			$("form#formCedValidacionSeguimientoCorr #labelTrabSubdeclaUniSegCorr_cedval").html('<label class="etiquetaError">Campo Requerido</label>');
		}
		
		if(($("form#formCedValidacionSeguimientoCorr #suertePpalDetCopSegCorr_cedval").val()=='' || parseInt($("form#formCedValidacionSeguimientoCorr #suertePpalDetCopSegCorr_cedval").val(),10)<=0 ) && 
				( $("form#formCedValidacionSeguimientoCorr #suertePpalDetRcvSegCorr_cedval").val()== '' || parseInt($("form#formCedValidacionSeguimientoCorr #suertePpalDetRcvSegCorr_cedval").val(),10)<=0 )){
			$("form#formCedValidacionSeguimientoCorr #labelSuertePpalDetCopSegCorr_cedval").html('<label class="etiquetaError"  align="rigth">Ingrese COP</label>');
			$("form#formCedValidacionSeguimientoCorr #labelSuertePpalDetRcvSegCorr_cedval").html('<label class="etiquetaError" align="left"> o RCV </label>');
			
		}

		
		if( $("form#formCedValidacionSeguimientoCorr #trabRevisadosSegCorr_cedval").val() != '' && $("form#formCedValidacionSeguimientoCorr #trabOmisosUniSegCorr_cedval").val() !='' 
			&& $("form#formCedValidacionSeguimientoCorr #trabSubdeclaUniSegCorr_cedval").val() != '' &&  
				 ( $("form#formCedValidacionSeguimientoCorr #suertePpalDetCopSegCorr_cedval").val() != '' && $("form#formCedValidacionSeguimientoCorr #suertePpalDetCopSegCorr_cedval").val() != "0" )
						&&  ($("form#formCedValidacionSeguimientoCorr #suertePpalDetRcvSegCorr_cedval").val() != '' || $("form#formCedValidacionSeguimientoCorr #suertePpalDetRcvSegCorr_cedval").val() != "0")   
				  	&& validaTrabRegularizadosConsolImpteVal() && validaParcialidadesSegCorrCedVal() && 
				  	validaMontoSuertePpalBaseDeterminadaCedVal() && validaMontoSuertePpalRCVBaseDeterminadaCedVal()){
			
			regresa = true;
			
		}

	}else{
		//solo cuando apliquen las condiciones deberia validar la seccion de consolidacion... si no , regresar true
		regresa = true;
	}
		
	
	return regresa;
}

function guardaEstatusCrtRevRecepcionCedVal(cvePresentaCorr, estatusRecepcion,tipoPago){
	
	
	var variable = '{' +
	   '"cvePresentacorr":"'+cvePresentaCorr+'",'+
	   '"indTipoPago":"'+tipoPago+'",'+
	   '"cveStatus":"'+estatusRecepcion+'"}';	
	
	var recepcion = jQuery.parseJSON(variable);
	//alert("variable :" + variable);
	
	bloquear();
	$.postJSON("correccion/actualizaEstatusRecepcionCedVal.do", recepcion, function(data) {
		//alert("dentro de variable");

	}).error(function(data) {
		validarSesionExpirada(data);
		alert("error"+data);
	}).complete(function(data){	
		desbloquear();
		//completaFlujoComprobConvenio();
		//completaRecepcionSolCorr();
	});	


}
