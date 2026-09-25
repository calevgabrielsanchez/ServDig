var AUDITOR= 2;
var cvePresentacion;
var folioCorreccion;


/**
 * Constantes para arreglo de fechas 
 * del seguimiento de la correccion
 * 
 */
F_ELABORAPRE = 0;
F_RECEPCION = 1;
F_APLICA_REVISION = 2;
F_REQ_DOC_EMI = 3;
F_REQ_DOC_NOT = 4;
F_REQ_DOC_ATN = 5;
F_APLICA_VALIDACION = 6;
F_OF_RESULT_EMI = 7;
F_OF_RESULT_NOT = 8;
F_OF_RESULT_ATN = 9;
F_DERIVA_SUBDEL = 10;
F_DERIVA_FIS = 11;
F_DERIVA_FIS_REAC = 12;
F_DERIVA_FIS_ENVIO = 13;
F_REACTIVACION = 14;
F_DICTAMEN_EMI = 15;
F_CANCELA_EMI = 16;
F_CONCLUSION_EMI = 17;
F_CONCLUSION_NOT = 18;
F_PRORROGA_AUT = 19;
F_PRORROGA_SOL = 20;
F_SOL_AUT = 21;
F_SOL_CORR = 22;

/**
 * Arreglo de fechas del seguimiento de la correccion
 * columna 1: ubicacion de la fecha en html
 * columna 2: descripcion de la fecha
 * @author Gerardo Salazar Vega
 */
var jsFechasCorr = [
	["form#seguimientoCorreccionForm #labelFechaPresentacionCorr", "fecha de presentaci&oacute;n"],
	["form#formRecepcionSeguimiento #fecPresentCorrecConsol", "fecha de recepci&oacute;n"],
	["form#formCedRevAudCorrSeguimiento #fechaAplicaCedula", "fecha de aplicaci&oacute;n cedula de revisi&oacute;n"],
	["form#reqDocumentacionSeguimientoCorreccionForm #fecEmisionReqDocSegCorr", "fecha de emisi&oacute;n del requerimiento de documentaci&oacute;n"],
	["form#reqDocumentacionSeguimientoCorreccionForm #fecNotifReqDocSegCorr", "fecha de notificaci&oacute;n del requerimiento de documentaci&oacute;n"],
	["form#reqDocumentacionSeguimientoCorreccionForm #fecAtenReqDocSegCorr", "fecha de atenci&oacute;n del requerimiento de documentaci&oacute;n"],
	["form#formCedValidacionSeguimientoCorr #fecCaptura_cedval", "fecha de aplicaci&oacute;n de cedula de validaci&oacute;n"],
	["form#ofSeguimientoCorreccionForm #fecEmiORSegCorr", "fecha de emisi&oacute;n de oficio de resultados"],
	["form#ofSeguimientoCorreccionForm #fecNotORSegCorr", "fecha de notificaci&oacute;n de oficio de resultados"],
	["form#ofSeguimientoCorreccionForm #fecAteORSegCorr", "fecha de atenci&oacute;n de oficio de resultados"],
	["form#devSubDelegacionSeguimientoCorreccionForm #fechaDerivacion", "fecha de derivaci&oacute;n a subdelegacion"],
	["form#devFiscalizacionSeguimientoCorreccionForm #fecDerivFisSegCorr", "fecha de derivaci&oacute;n a fiscalizacion"],
	["form#reactivacionSeguimientoCorreccionForm #fechaSolReactiva", "fecha de solicitud de reactivaci&oacute;n"],
	["form#reactivacionSeguimientoCorreccionForm #fechaEnvioSol", "fecha de env&iacute;o de solicitud de reactivaci&oacute;n"],
	["form#reactivacionSeguimientoCorreccionForm #fechaReactiva", "fecha de reactivaci&oacute;n"],
	["form#devDictamenSeguimientoCorreccionForm #fecAutDicSegCorr", "fecha de autorizaci&oacute;n env&iacute;o a dictamen"],
	["form#cancelacionSeguimientoCorreccionForm #fecCancelacionSegCorr", "fecha de cancelaci&oacute;n"],
	["form#conclusionSeguimientoCorreccionForm #fecEmiConclusion", "fecha de emisi&oacute;n del oficio de conclusi&oacute;n"],
	["form#conclusionSeguimientoCorreccionForm #fecNotConclusion", "fecha de notificaci&oacute;n del oficio de conclusion"],
	["form#formResumenSeguimiento #lbFechaAutoProrrVal", "fecha de autorizaci&oacute;n de pr&oacute;rroga"],
	["form#formResumenSeguimiento #lbFechaSolProrrVal", "fecha de solicitud de pr&oacute;rroga"],
	["form#formResumenSeguimiento #lbFechaAutSolCorrVal", "fecha de autorizaci&oacute;n de solicitud de correcci&oacute;n"],
	["form#formResumenSeguimiento #lbFechaPreSolCorrVal", "fecha de solicitud de correcci&oacute;n"]
]

var ARRAY_FECHAS_SEGCORR = [ F_OF_RESULT_ATN, F_OF_RESULT_NOT, F_OF_RESULT_EMI,
	                        F_APLICA_VALIDACION,
	                        F_REQ_DOC_ATN, F_REQ_DOC_NOT, F_REQ_DOC_EMI,
	                        F_APLICA_REVISION,
	                        F_RECEPCION,
	                        F_ELABORAPRE,
	                        F_PRORROGA_AUT,
	                        F_PRORROGA_SOL,
	                        F_SOL_AUT,
	                        F_SOL_CORR
                        ];
/**
 * Funcion para determinar la primera fecha valida 
 * de una lista de fechas
 * @param listaFechas lista de indices para el arreglo jsFechasCorr
 * @author Gerardo Salazar Vega
 */
function selectFechaValida(listaFechas) {
	   for (var i = 0; i < listaFechas.length; i++) {
		   var idFecha=listaFechas[i];
		   var valFecha;
		   
		   valFecha = $(jsFechasCorr[idFecha][0]).val();
		   if (valFecha==null || valFecha=="") {
			   valFecha = $(jsFechasCorr[idFecha][0]).text();			   
		   }
		   if (valFecha!=null && valFecha!="") {
			   return ([valFecha, jsFechasCorr[idFecha][1]]);
		   }
      }
	   return null;
}

/**
 * Funcion para determinar validez de una fecha 
 * contra una lista de fechas
 * @param fecMayor fecha a validar
 * @param listaFechas lista de indices para el arreglo jsFechasCorr
 * @author Gerardo Salazar Vega
 */
function validaListaFechas(fecMayor, listaFechas) {
	var fechaSelect;
	var fvalida=false;
	
	fechaSelect = selectFechaValida(listaFechas);	
	fvalida=comparaFechas(fechaSelect[0], fecMayor, "-");
	return ([fvalida, fechaSelect[1]] );
}

//Validaciones para la pestaña de resumen 
function valSeguimientoCorreccionResumen1(){
	//Siempre se muestra
	return true;
}

//validaciones para Recepcion
function valSeguimientoCorreccionRecepcion2(){
	//siempre Se muestra
	return true;
}

//Validacion Cedula Revision Regla 1
function valSeguimientoCorreccionCedRevision1(){
//	if($('form#formRecepcionSeguimiento #fecPresentCorrecConsol').val()!=''){
//		return true;
//	}else{
//		return false;
//	}
	return false;
}

//Validacion Cedula Revision Regla 2
function valSeguimientoCorreccionCedRevision2(){
	var valSuertePpalCOP=parseFloat($("form#formRecepcionSeguimiento #suertePpalPenPagoCopSegCorr").val());
	var valSuertePpalRCV=parseFloat($("form#formRecepcionSeguimiento #suertePpalPenPagoRcvSegCorr").val());
	
	//if(valSuertePpalCOP<=0 && valSuertePpalRCV<=0 && (cveEstatusRecepcion == 7 || cveEstatusRecepcion == 8 || cveEstatusRecepcion == 9)){
	if((cveEstatusRecepcion == 7 || cveEstatusRecepcion == 8 || cveEstatusRecepcion == 9 || cveEstatusRecepcion >= 10) && (cveEstatusRecepcion!=21 || cveEstatusRecepcion!=22 || cveEstatusRecepcion!=24 || cveEstatusRecepcion!=25)){
		//habilitaCamposXForma('formCedRevSupvCorrSeguimiento');
		return true;
	}else{
		return false;
	}
}

//Validacion Cedula Revision Regla 3
function valSeguimientoCorreccionCedRevision3(){
	var valSuertePpalCOP=parseFloat($("form#formRecepcionSeguimiento #suertePpalPenPagoCopSegCorr").val());

	//if($("form#formRecepcionSeguimiento #cbxComprobConvenio").is(":checked") && valSuertePpalCOP>0 && (cveEstatusRecepcion == 7 || cveEstatusRecepcion == 8 || cveEstatusRecepcion == 9)){
	if((cveEstatusRecepcion == 7 || cveEstatusRecepcion == 8 || cveEstatusRecepcion == 9 || cveEstatusRecepcion >= 10)&& (cveEstatusRecepcion!=21 || cveEstatusRecepcion!=22 || cveEstatusRecepcion!=24 || cveEstatusRecepcion!=25)){
	//	habilitaCamposXForma('formCedRevSupvCorrSeguimiento');
		return true;
	}else{
		return false;
	}
}

//Validacion Cedula Revision Regla 4
function valSeguimientoCorreccionCedRevision4(){
	var valSuertePpalRCV=parseFloat($("form#formRecepcionSeguimiento #suertePpalPenPagoRcvSegCorr").val());
	
	//if($("form#formRecepcionSeguimiento #cbxComprobConvenio").is(":checked") && valSuertePpalRCV>0 && (cveEstatusRecepcion == 7 || cveEstatusRecepcion == 8 || cveEstatusRecepcion == 9)){
	if((cveEstatusRecepcion == 7 || cveEstatusRecepcion == 8 || cveEstatusRecepcion == 9 || cveEstatusRecepcion >= 10)&& (cveEstatusRecepcion!=21 || cveEstatusRecepcion!=22 || cveEstatusRecepcion!=24 || cveEstatusRecepcion!=25)){
		//habilitaCamposXForma('formCedRevSupvCorrSeguimiento');
		return true;
	}else{
		return false;
	}
}

//Validacion Req Documentacion Regla 1
function valSeguimientoCorreccionReqDocumentacion1(){	
	var flag=false;
	var flagAuto=true;
	if(consolidadoCedulaValidacion!=undefined){
		for(var s=0;s<consolidadoCedulaValidacion.length;s++){
			if(consolidadoCedulaValidacion[s].razonable=='NO'){
				flag=true;
			}
		}
		
		for(var s=0;s<consolidadoCedulaValidacion.length;s++){
			if(consolidadoCedulaValidacion[s].estatus!='AUTORIZADO'){
				flagAuto=false;
			}
		}
	}
	return (flag && flagAuto);
}

//Validacion Cedu Validacion Regla 1
function valSeguimientoCorreccionCedValidacion1(){
	var flag=false;
	var flagAuto=true;
	if(consolidadoCedulaValidacion!=undefined){
		for(var s=0;s<consolidadoCedulaValidacion.length;s++){
			if(consolidadoCedulaValidacion[s].razonable=='NO'){
				flag=true;
			}
		}
	
	
	for(var s=0;s<consolidadoCedulaValidacion.length;s++){
			if(consolidadoCedulaValidacion[s].estatus!='AUTORIZADO'){
				flagAuto=false;
			}
		}
	}
	return (flag && flagAuto);
}

//Validacion Oficio Resultados Regla 1  //pendiente
function valSeguimientoCorreccionOfiResultados1(){
	if(indAutoPrimera=='2' && totalDifePrimer>0){
		return true;
	}else{
		return false;
	}
}

//Validacion Conclusion Regla 1
function valSeguimientoCorreccionConclusion1(){
	var flag=true;
	var flagAutoriza=true;
	if(consolidadoCedulaValidacion!=undefined && cveEstatusRecepcion!=undefined && (cveEstatusRecepcion == 12 || cveEstatusRecepcion == 16 || cveEstatusRecepcion == 17 || cveEstatusRecepcion == 18) ){		
		for(var s=0;s<consolidadoCedulaValidacion.length;s++){
			if(consolidadoCedulaValidacion[s].razonable=='NO'){
				flag=false;
			}
		}
		
		for(var s=0;s<consolidadoCedulaValidacion.length;s++){
			if(consolidadoCedulaValidacion[s].estatus!='AUTORIZADO'){
				flagAutoriza=false;
			}
		}
	}else{
		flag=false;
	}
	return  (flag && flagAutoriza);
}

//Validacion Conclusion Regla 2
function valSeguimientoCorreccionConclusion2(){
	
	if((cveEstatusRecepcion == 12 || cveEstatusRecepcion == 16 || cveEstatusRecepcion == 17 || cveEstatusRecepcion == 18)){
		return true;
	}else{
		return false;
	}
}

//Validacion Conclusion Regla 3
function valSeguimientoCorreccionConclusion3(){
	if((cveEstatusRecepcion == 12 || cveEstatusRecepcion == 16 || cveEstatusRecepcion == 17 || cveEstatusRecepcion == 18)){
		return true;
	}else{
		return false;
	}
}

//Validacion Conclusion Regla 4
function valSeguimientoCorreccionConclusion4(){
	if((cveEstatusRecepcion == 12 || cveEstatusRecepcion == 16 || cveEstatusRecepcion == 17 || cveEstatusRecepcion == 18)){
		return true;
	}else{
		return false;
	}
}



function valSeguimientoCorreccionDerivaSubdelegacion(){
	return true;
}


function valSeguimientoCorreccionDerivaFiscalizacion(){
	return true;
}

function valSeguimientoCorreccionReactivacion(){
	if($("#fecDerivFisSegCorr").val()!=''){
		return true;
	}else{
		return false;
	}
	
}


function valSeguimientoCorreccionDerivaDictamen(){
	if($("#fecNotORSegCorr").val()==""){		
		return true;
	}else{
		return false;
	}
	
	
}

function valSeguimientoCorreccionCancelacion(){
	if($("#fecNotORSegCorr").val()==""){		
		return true;
	}else{
		return false;
	}
	
}




function ejecutaReglasValidacion(rolUsuario){
	
	if(rolIsJefe){
		rolUsuario=1;
	}else{
		rolUsuario=2;
	}
	if(rolUsuario==AUDITOR){
		bloquearTodo();
		reglasAuditor();
	}else{
		bloquearTodo();
		reglasJefeTabs();
		reglasJefeOficinaCampos();
	}
	
	
	   if($("form#conclusionSeguimientoCorreccionForm #folioConclusion").is(':disabled') ){
		   bloquearTodo();
		   setTabHabilitado('seguimientoCorreccionResumen');
		  
	   }
	   
	
	
}


function reglasAuditor(){
	if(cveEstatusRecepcion==undefined){
		cveEstatusRecepcion=-1;
	}
	var reglas = new Object();
	reglas['valSeguimientoCorreccionResumen1()'] = 'seguimientoCorreccionResumen';
	reglas['valSeguimientoCorreccionRecepcion2()'] = 'seguimientoCorreccionRecepcion';
	
	reglas['valSeguimientoCorreccionCedRevision1()'] ='seguimientoCorreccionCedRevision' ;
	reglas['valSeguimientoCorreccionCedRevision2()'] = 'seguimientoCorreccionCedRevision';
	reglas['valSeguimientoCorreccionCedRevision3()'] = 'seguimientoCorreccionCedRevision';
	reglas['valSeguimientoCorreccionCedRevision4()'] ='seguimientoCorreccionCedRevision' ;
	
	
	reglas['valSeguimientoCorreccionReqDocumentacion1()'] ='seguimientoCorreccionReqDocumentacion' ;
	reglas['valSeguimientoCorreccionCedValidacion1()'] ='seguimientoCorreccionCedValidacion' ;
	
	
	reglas['valSeguimientoCorreccionOfiResultados1()'] ='seguimientoCorreccionOfiResultados' ;
	
	reglas['valSeguimientoCorreccionConclusion1()'] ='seguimientoCorreccionConclusion' ;
	reglas['valSeguimientoCorreccionConclusion2()'] ='seguimientoCorreccionConclusion' ;
	reglas['valSeguimientoCorreccionConclusion3()'] ='seguimientoCorreccionConclusion' ;
	reglas['valSeguimientoCorreccionConclusion4()'] ='seguimientoCorreccionConclusion' ;
	
	for (var k in reglas) {
	    if (reglas.hasOwnProperty(k)) {
	    	var flag=eval(k);
	    	//console.log("Ejecutando Validacion "+k+" = "+flag);
	    	if(flag){
	    		setTabHabilitado(reglas[k]);
	    	}
	    }
	}	
	
}


function reglasJefeTabs(){
	if(cveEstatusRecepcion==undefined){
		cveEstatusRecepcion=-1;
	}
	var reglas = new Object();
	reglas['valSeguimientoCorreccionResumen1()'] = 'seguimientoCorreccionResumen';
	reglas['valSeguimientoCorreccionRecepcion2()'] = 'seguimientoCorreccionRecepcion';
	
	reglas['valSeguimientoCorreccionCedRevision1()'] ='seguimientoCorreccionCedRevision' ;
	reglas['valSeguimientoCorreccionCedRevision2()'] = 'seguimientoCorreccionCedRevision';
	reglas['valSeguimientoCorreccionCedRevision3()'] = 'seguimientoCorreccionCedRevision';
	reglas['valSeguimientoCorreccionCedRevision4()'] ='seguimientoCorreccionCedRevision' ;
	
	
	reglas['valSeguimientoCorreccionReqDocumentacion1()'] ='seguimientoCorreccionReqDocumentacion' ;
	reglas['valSeguimientoCorreccionCedValidacion1()'] ='seguimientoCorreccionCedValidacion' ;
	
	
	reglas['valSeguimientoCorreccionOfiResultados1()'] ='seguimientoCorreccionOfiResultados' ;
	
	reglas['valSeguimientoCorreccionConclusion1()'] ='seguimientoCorreccionConclusion' ;
	reglas['valSeguimientoCorreccionConclusion2()'] ='seguimientoCorreccionConclusion' ;
	reglas['valSeguimientoCorreccionConclusion3()'] ='seguimientoCorreccionConclusion' ;
	reglas['valSeguimientoCorreccionConclusion4()'] ='seguimientoCorreccionConclusion' ;

	
	reglas['valSeguimientoCorreccionDerivaSubdelegacion()'] ='seguimientoCorreccionDerivarSub' ;
	reglas['valSeguimientoCorreccionDerivaFiscalizacion()'] ='seguimientoCorreccionDerivarFis' ;
	reglas['valSeguimientoCorreccionReactivacion()'] ='seguimientoCorreccionReactivar' ;
	reglas['valSeguimientoCorreccionDerivaDictamen()'] ='seguimientoCorreccionDerivarDic' ;
	reglas['valSeguimientoCorreccionCancelacion()'] ='seguimientoCorreccionCancelacion' ;
	
	
	
	for (var k in reglas) {
	    if (reglas.hasOwnProperty(k)) {
	    	var flag=eval(k);
	    	//console.log("Ejecutando Validacion "+k+" = "+flag);
	    	if(flag){
	    		setTabHabilitado(reglas[k]);
	    	}
	    }
	}	
	
}


function reglasJefeOficinaCampos(){
	if(cveEstatusRecepcion==undefined){
		cveEstatusRecepcion=-1;
	}
	//alert("esattus "+cveEstatusRecepcion )
	var reglas = new Object();
	var allForm='formRecepcionSeguimiento,formCedRevAudCorrSeguimiento,reqDocumentacionSeguimientoCorreccionForm,formCedValidacionSeguimientoCorr,ofSeguimientoCorreccionForm,devSubDelegacionSeguimientoCorreccionForm,devFiscalizacionSeguimientoCorreccionForm,reactivacionSeguimientoCorreccionForm,devDictamenSeguimientoCorreccionForm,cancelacionSeguimientoCorreccionForm,conclusionSeguimientoCorreccionForm,formCedRevSupvCorrSeguimiento';
	reglas['valSeguimientoCorreccionCancelacionJefe()'] = '';
	reglas['valSeguimientoCorreccionDerivarDictamenJefe()'] = 'formRecepcionSeguimiento,devDictamenSeguimientoCorreccionForm,ofSeguimientoCorreccionForm';
	reglas['valSeguimientoCorreccionDerivarFizcalizacionJefe()'] = 'devFiscalizacionSeguimientoCorreccionForm';
	reglas['valSeguimientoCorreccionReactivacionJefe()'] = '';
	reglas['valSeguimientoCorreccionDerivarSubdelegacionJefe()'] = '';
	reglas['valSeguimientoCorreccionConclusionJefe()'] = '';
	reglas['valSeguimientoCorreccionOficioResultadosJefe()'] = '';
	reglas['valSeguimientoCorreccionCedulaValidacionJefe()'] = '';
	reglas['valSeguimientoCorreccionReqDocumentacionJefe()'] = '';
	reglas['valSeguimientoCorreccionCedulaRevisionJefe()'] = '';
	
	for (var k in reglas) {
	    if (reglas.hasOwnProperty(k)) {
	    	var flag=eval(k);
	    	if(flag && reglas[k]!=''){
	    		bloquearFormas(reglas[k]);
	    	}
	    }
	}
}


function valSeguimientoCorreccionCancelacionJefe(){	
	if(cveEstatusRecepcion==22){
		bloquearTodo();
		setTabHabilitado("seguimientoCorreccionResumen");
		//setTabHabilitado("seguimientoCorreccionRecepcion");
		setTabHabilitado("seguimientoCorreccionCancelacion");
		return true;
	}else{
		return false;
	}
}

function valSeguimientoCorreccionDerivarDictamenJefe(){	
	if(cveEstatusRecepcion==25){
		bloquearTodo();
		setTabHabilitado("seguimientoCorreccionResumen");
		setTabHabilitado("seguimientoCorreccionRecepcion");
		setTabHabilitado("seguimientoCorreccionDerivarDic");
		//setTabHabilitado("seguimientoCorreccionOfiResultados");
		return true;
	}else{
		return false;
	}
}

function valSeguimientoCorreccionDerivarFizcalizacionJefe(){	
	if(cveEstatusRecepcion==24){
		bloquearTodo();
		setTabHabilitado("seguimientoCorreccionResumen");
		setTabHabilitado("seguimientoCorreccionRecepcion");
		setTabHabilitado("seguimientoCorreccionDerivarFis");
		setTabHabilitado("seguimientoCorreccionReactivar");
		//habilitaCamposXForma("reactivacionSeguimientoCorreccionForm");
		return true;
	}else{
		return false;
	}
}

function valSeguimientoCorreccionReactivacionJefe(){	
	if($("#fechaSolReactiva").val()!=''){
		bloquearTodo();
		setTabHabilitado("seguimientoCorreccionResumen");
		setTabHabilitado("seguimientoCorreccionRecepcion");
		setTabHabilitado("seguimientoCorreccionCedRevision");
		setTabHabilitado("seguimientoCorreccionCedValidacion");
		setTabHabilitado("seguimientoCorreccionReqDocumentacion");
		setTabHabilitado("seguimientoCorreccionOfiResultados");
		setTabHabilitado("seguimientoCorreccionReactivar");
		setTabHabilitado("seguimientoCorreccionDerivarFis");
		 $("form#reactivacionSeguimientoCorreccionForm #botonGuardar").prop("disabled", "disabled");
		 $("form#reactivacionSeguimientoCorreccionForm #botonReactivar").prop("disabled", "disabled");	
		
	}else{
		return false;
	}
	false;
}

function valSeguimientoCorreccionDerivarSubdelegacionJefe(){
	if(cveEstatusRecepcion==21){
		bloquearTodo();
		setTabHabilitado("seguimientoCorreccionResumen");
		setTabHabilitado("seguimientoCorreccionRecepcion");
		setTabHabilitado("seguimientoCorreccionDerivarSub");
		return true;
	}else{
		return false;
	}
}

function valSeguimientoCorreccionConclusionJefe(){
	if(cveEstatusRecepcion==12 || cveEstatusRecepcion==16 || cveEstatusRecepcion==17 || cveEstatusRecepcion==18){
		return true;
	}else{
		return false;
	}
}

function valSeguimientoCorreccionOficioResultadosJefe(){
	if(cveEstatusRecepcion==14){
		setTabHabilitado("seguimientoCorreccionOfiResultados");
		return true;
	}else{
		return false;
	}
}

function valSeguimientoCorreccionCedulaValidacionJefe(){
	return false;
}

function valSeguimientoCorreccionReqDocumentacionJefe(){
	return false;
}

function valSeguimientoCorreccionCedulaRevisionJefe(){
	return false;
}


function bloquearFormas(formas){
	
	var lista=formas.split(",");
	for(var s=0;s<lista.length;s++){
		//console.log("Bloquendo Forma "+lista[s]);
		deshabilitaCamposXForma(lista[s]);
	}
}

function desbloquearFormas(){
	var formas='formRecepcionSeguimiento,formCedRevAudCorrSeguimiento,reqDocumentacionSeguimientoCorreccionForm,formCedValidacionSeguimientoCorr,ofSeguimientoCorreccionForm,devSubDelegacionSeguimientoCorreccionForm,devFiscalizacionSeguimientoCorreccionForm,reactivacionSeguimientoCorreccionForm,devDictamenSeguimientoCorreccionForm,cancelacionSeguimientoCorreccionForm,conclusionSeguimientoCorreccionForm,formCedRevSupvCorrSeguimiento';
	var lista=formas.split(",");
	for(var s=0;s<lista.length;s++){
		//console.log("Desbloquendo Forma "+lista[s]);
		habilitaCamposXForma(lista[s]);
	}
}

function bloquearTodo(){
	setTabDesHabilitado('seguimientoCorreccionResumen');
	setTabDesHabilitado('seguimientoCorreccionRecepcion');
	setTabDesHabilitado('seguimientoCorreccionCedRevision');
	setTabDesHabilitado('seguimientoCorreccionReqDocumentacion');
	setTabDesHabilitado('seguimientoCorreccionCedValidacion');
	setTabDesHabilitado('seguimientoCorreccionOfiResultados');
	setTabDesHabilitado('seguimientoCorreccionDerivarSub');
	setTabDesHabilitado('seguimientoCorreccionDerivarFis');
	setTabDesHabilitado('seguimientoCorreccionReactivar');
	setTabDesHabilitado('seguimientoCorreccionDerivarDic');					
	setTabDesHabilitado('seguimientoCorreccionCancelacion');					
	setTabDesHabilitado('seguimientoCorreccionConclusion');
}


function desbloquearTodo(){
	setTabHabilitado('seguimientoCorreccionResumen');
	setTabHabilitado('seguimientoCorreccionRecepcion');
	setTabHabilitado('seguimientoCorreccionCedRevision');
	setTabHabilitado('seguimientoCorreccionReqDocumentacion');
	setTabHabilitado('seguimientoCorreccionCedValidacion');
	setTabHabilitado('seguimientoCorreccionOfiResultados');
	setTabHabilitado('seguimientoCorreccionDerivarSub');
	setTabHabilitado('seguimientoCorreccionDerivarFis');
	setTabHabilitado('seguimientoCorreccionReactivar');
	setTabHabilitado('seguimientoCorreccionDerivarDic');					
	setTabHabilitado('seguimientoCorreccionCancelacion');					
	setTabHabilitado('seguimientoCorreccionConclusion');
}


function consultaEstatus(){
	
	var clase = jQuery.parseJSON(folioCorreccion);
	$.postJSON_Sync("correccion/consultaEstatusCorreccion.do", clase, function(data) {
		
		alert("se consulto "+data);
	});
}