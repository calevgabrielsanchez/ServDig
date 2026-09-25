//Variables globales para la revision de la cedula
var numFolio;
var regPatronal;
var claveSolCorr;
var claveAnexoSolCorr;
var rubros;
var dataTableRubros;
var intTotalRegRubro;
var dataPrincipal;
var cvePresentaCorre;
var observ;
var presuntivo;
var rolUsuarioCedValidaSuper;
var consolidadoCedulaValidacion;
//Recuperamos la lista de ejercicios,rp y totales de RP asocioados a la presnetacion y solCorr
function incializaCedulaRevisionSupv(data){
	dataPrincipal=null;
	numFolio=data.nuFolio;
	rolUsuarioCedValidaSuper=data.user.cveRol;	
	regPatronal=data.regPatronal;
	claveSolCorr=data.cveSolCorr;
	cvePresentaCorre=data.cvePresentaCorr;
	var sVarSeg = '{"cveSolCorr":"'+claveSolCorr+'","regPatronal":"'+regPatronal+'","nuFolio":"'+numFolio+'","cvePresentaCorr":"'+cvePresentaCorre+'"}';
	var clase = jQuery.parseJSON(sVarSeg);
	
	$('#formCedRevSupvCorrSeguimiento').each (function(){
		this.reset();
	});
	
	$.postJSON_Sync("correccion/cedulaRevisionAud.do", clase, function(data) {		
		
		limpiaFormulario();
		generaCombosSupv(data.cedulaRevisionAudVO);		
		generaTablaSupv(data.cedulaRevisionAudVO.rubros);	
		generaTablaSupvTotalBaseDifSV(data.cedulaRevisionAudVO.totalRpEjer);
		consolidadoCedulaValidacion=data.cedulaRevisionAudVO.totalRpEjer;
		generaResumenRevisionCed(data);
		observ=data.cedulaRevisionAudVO.observaciones;
		if(data.cedulaRevisionAudVO.solicitudConstruccion){
			$("form#formCedRevSupvCorrSeguimiento #wrapperCheckPresuntivo").css("display", "block");
		}else{
			$("form#formCedRevSupvCorrSeguimiento #wrapperCheckPresuntivo").css("display", "none");
		}
		
		
		if(data.cedulaRevisionAudVO.presuntivo){
			$('form#formCedRevSupvCorrSeguimiento #checkPresuntivo').attr('checked', true);
		}else{
			$('form#formCedRevSupvCorrSeguimiento #checkPresuntivo').attr('checked', false);
		}
		
		$('form#formCedRevSupvCorrSeguimiento #fechaAplicaCedula').val(data.cedulaRevisionAudVO.fechaAplicacionCedula);
		
		
		$('form#formCedRevSupvCorrSeguimiento #checkPresuntivo').change(function(){
			if($('form#formCedRevSupvCorrSeguimiento #checkPresuntivo').is(":checked")){
				$("form#formResumenSeguimiento #lbValMetodoCalc").text("PRESUNTIVO");
				presuntivo=true;
			}else{
				$("form#formResumenSeguimiento #lbValMetodoCalc").text("");
				presuntivo=false;
			}
		});
		
		$("form#formCedRevSupvCorrSeguimiento #valDifDet").text("");
	}).error(function(data){
		jAlert('No se puede obtener la informaci\u00f3n general de la revisi\u00f3n de c\u00e9dula, favor de reintentar ','Alerta');
	});
	

	generaListenersSupv();
	
	
	
	$('#btnAutorizaCedularRevision').attr('disabled', 'disabled');
	$('#btnRechazaCedularRevision').removeAttr('disabled');
	
}

//Inicializa los combos de ejercicio y RP
function generaCombosSupv(data){
	
	var options = "<option value='-1' >--Por favor seleccione--</option>";
	var optionsRegPat="<option value='-1' >--Por favor seleccione--</option>";

	for(var s=0;s<data.ejercicios.length;s++){
		options += "<option value='"+ data.ejercicios[s] +"'>"+ data.ejercicios[s]+"</option>";		
	}
//	for(var s=0;s<data.registrosPatronales.length;s++){
//		optionsRegPat += "<option value='"+ data.registrosPatronales[s] +"'>"+ data.registrosPatronales[s]+"</option>";		
//	}
	$("form#formCedRevSupvCorrSeguimiento #numEjercicio").html(options);
	//$("form#formCedRevSupvCorrSeguimiento #regPatronal").html(optionsRegPat);
}

// asigna las funciones para cada componente
function generaListenersSupv(){
	$("form#formCedRevSupvCorrSeguimiento #numEjercicio,form#formCedRevSupvCorrSeguimiento #regPatronal").unbind();
	$('form#formCedRevSupvCorrSeguimiento #btnAutorizaCedularRevision').unbind();


	$("form#formCedRevSupvCorrSeguimiento #regPatronal").change(function(){
		if($("form#formCedRevSupvCorrSeguimiento #numEjercicio").val()!='-1' && $("form#formCedRevSupvCorrSeguimiento #regPatronal").val()!='-1'){
		getBaseCotPagadaSupv();
		
	}
		
	});
	
	
	
	$("form#formCedRevSupvCorrSeguimiento #numEjercicio").change(function(){		
//		if($("form#formCedRevSupvCorrSeguimiento #numEjercicio").val()!='-1' && $("form#formCedRevSupvCorrSeguimiento #regPatronal").val()!='-1'){
//			getBaseCotPagadaSupv();
//			
//		}
		
		var sVarSeg = '{"cveSolCorr":"'+claveSolCorr+'","cveEjercicio":"'+$("form#formCedRevSupvCorrSeguimiento #numEjercicio").val()+'"}';
		var clase = jQuery.parseJSON(sVarSeg);
		
		$.postJSON("correccion/consultaRegistrosPatronales.do", clase, function(data) {
			limpiaFormulario();
			if(dataTableRubros!=undefined && rubros!=undefined){
				dataTableRubros.fnClearTable();
				rubros.splice(0);
			}
			
			
			
			var optionsRegPat="<option value='-1' >--Por favor seleccione--</option>";
			for(var s=0;s<data.length;s++){
				optionsRegPat += "<option value='"+ data[s][1] +"'>"+ data[s][0]+"</option>";		
			}
			$("form#formCedRevSupvCorrSeguimiento #regPatronal").html(optionsRegPat);
		}).error(function(data){
			jAlert('No se puede recuperar la lista de registros patronales, favor de reintentar ','Alerta');
		});
	});


//	$('form#formCedRevSupvCorrSeguimiento #btnReporteRevision').click(function(){
//		generaReporte();
//	});
//	
	
	
	$('form#formCedRevSupvCorrSeguimiento #btnAutorizaCedularRevision').click(function(){
		autorizarRevisionSupv();
		ejecutaReglasValidacion(1);
	});
		
	$('form#formCedRevSupvCorrSeguimiento #btnRechazaCedularRevision').unbind();
	$('form#formCedRevSupvCorrSeguimiento #btnRechazaCedularRevision').click(function(){
		rechazaRevisionSupv();
		ejecutaReglasValidacion();
	});
		
	
	
	$('form#formCedRevSupvCorrSeguimiento #autorizabaseCot,form#formCedRevSupvCorrSeguimiento #autorizaDifeBaseCoti').change(function(){
		compruebaAutorizacion();
	});	
	
	
	$('form#formCedRevSupvCorrSeguimiento #checkPresuntivo').change(function(){
		if($('form#formCedRevSupvCorrSeguimiento #checkPresuntivo').is(":checked")){
			presuntivo=true;
		}else{
			presuntivo=false;
		}
	});
	
}



//Recupera la informacion asociada a un ejercicio y un registro patronal
function getBaseCotPagadaSupv(){		
	var ejercicio=$("form#formCedRevSupvCorrSeguimiento #numEjercicio").val();
	var regPatro=$("form#formCedRevSupvCorrSeguimiento #regPatronal option:selected").text();
	var folio=numFolio;	
	claveAnexoSolCorr=$("form#formCedRevSupvCorrSeguimiento #regPatronal").val();
	var sVarSeg = '{"cveSolCorr":"'+claveSolCorr+'","cveEjercicio":"'+ejercicio+'","regPatronal":"'+regPatro+'","nuFolio":"'+numFolio+'","cvePresentaCorr":"'+cvePresentaCorre+'","cveAnexoSolCorr":"'+claveAnexoSolCorr+'"}';
	var clase = jQuery.parseJSON(sVarSeg);
	

	
	$.postJSON_Sync("correccion/getBaseCotPagadaImss.do", clase, function(data) {
		
		if(!data.cedulaRevisionAudVO.accesoDetalle){
			jAlert(data.cedulaRevisionAudVO.razonAcceso,"Informaci\u00f3n");
			return;
		}
		consolidadoCedulaValidacion=data.cedulaRevisionAudVO.totalRpEjer;
		generaTablaSupvTotalBaseDifSV(data.cedulaRevisionAudVO.totalRpEjer);
		intTotalRegRubro=data.cedulaRevisionAudVO.rubros.length;
		dataPrincipal=data;
		dataPrincipal.cedulaRevisionAudVO.cvePresentaCorr=cvePresentaCorre;
		$("form#formCedRevSupvCorrSeguimiento #baseCotPatron").text(moneyMaskDT(data.cedulaRevisionAudVO.baseCotPagaImsPatron));
		$("form#formCedRevSupvCorrSeguimiento #baseCotImss").text(moneyMaskDT(data.cedulaRevisionAudVO.baseCotPagaImsIMSS));
		$("form#formCedRevSupvCorrSeguimiento #difeBaseCotiPatron").text(moneyMaskDT(data.cedulaRevisionAudVO.difBaseCotiPatron));
		$("form#formCedRevSupvCorrSeguimiento #difeBaseCotiIMSS").text(moneyMaskDT(data.cedulaRevisionAudVO.difBaseCotiIMSS));		
		$("form#formCedRevSupvCorrSeguimiento #txAreaObservaSuperv").val(observ);	
		
	
			
		$("form#formCedRevSupvCorrSeguimiento #autorizabaseCot").attr('checked',data.cedulaRevisionAudVO.autorizaBaseCotPaga);
		$("form#formCedRevSupvCorrSeguimiento #autorizaDifeBaseCoti").attr('checked',data.cedulaRevisionAudVO.autorizaDifBaseCot);
		
		
		
		
		recalculaConceptosSupv($("form#formCedRevSupvCorrSeguimiento #baseCotPatron"));
		generaTablaSupv(data.cedulaRevisionAudVO.rubros);	
		recalcularSumaTotalSupv();
		compruebaAutorizacion();
		
		if(data.cedulaRevisionAudVO.indAutorizaRevision==1){
			deshabilitaCamposXForma('formCedRevSupvCorrSeguimiento');
			habilitaCampo("form#formCedRevSupvCorrSeguimiento #numEjercicio")
			habilitaCampo("form#formCedRevSupvCorrSeguimiento #regPatronal")
		}
		
		
	}).error(function(data){
		jAlert('No se puede obtener el detalle de la c\u00e9dula, favor de reintentar ','Alerta');
	});
	
}
//Recalcula las cifras de conceptos
function recalculaConceptosSupv(componente){
	
	var flBaseCotPatron=parseFloat(quitaFormato($("form#formCedRevSupvCorrSeguimiento #baseCotPatron").text()));
	var flDifBaseCotPatron=parseFloat(quitaFormato($("form#formCedRevSupvCorrSeguimiento #difeBaseCotiPatron").text()));
	var flTotalPatron=flBaseCotPatron+flDifBaseCotPatron;	
	var flBaseCotImss=parseFloat(quitaFormato($("form#formCedRevSupvCorrSeguimiento #baseCotImss").text()));
	var flDifBaseCotImss=parseFloat(quitaFormato($("form#formCedRevSupvCorrSeguimiento #difeBaseCotiIMSS").text()));
	var flTotalImss=flBaseCotImss+flDifBaseCotImss;	
	$("form#formCedRevSupvCorrSeguimiento #totalConceptoPatron").text(moneyMaskDT(flTotalPatron,2));
	$("form#formCedRevSupvCorrSeguimiento #totalConceptoImss").text(moneyMaskDT(flTotalImss,2));
	
}
//Metodo que genera la tabla de rubros
function generaTablaSupv(data){
	
	rubros=data;	
	dataTableRubros= $("form#formCedRevSupvCorrSeguimiento #dtRubrosAB").dataTable( {
			"aaData": rubros,
			"bAutoWidth" : true,
			bFilter : false,
			bJQueryUI : true,
			"bDestroy": true,
			bSort: false,
			"aoColumns" : [{
					
				"sTitle" : "Concepto",
				"mDataProp" : "concepto",
				"sClass": "dtCenterClassColumn"
			},{
					
				"sTitle" : "Autodeterminacion patron",
				"mDataProp" : "autoDeterminacion",
				"sClass": "dtCenterClassColumn",
				"sWidth":"120px",
				"fnRender": function ( o, val ) {
					return '<div align="right">'+(o.aData['autoDeterminacion'])+"</div>";
				}
			},{
					
				"sTitle" : "Importe Aclarado",
				"mDataProp" : "importeAclarado",
				"sClass": "dtCenterClassColumn",
				"fnRender": function ( o, val ) {
					return '<div align="right">$'+moneyMaskDT(o.aData['importeAclarado'])+"</div>";
				}
			},{
					
				"sTitle" : "Importe Por aclarar",
				"mDataProp" : "importePorAclarar",
				"sClass": "dtCenterClassColumn",
				"fnRender": function ( o, val ) {
					return '<div align="right">$'+moneyMaskDT(o.aData['importePorAclarar'])+"</div>";
				}
			},{
					
				"sTitle" : "Total",					
				"sClass": "dtCenterClassColumn",
				"mDataProp" : "total",
				"fnRender": function ( o, val ) {
					return '<div align="right">$'+moneyMaskDT(o.aData['total'])+"</div>";
					}
				}
				,{
					
					"sTitle" : "Dato Correcto",					
					"sClass": "dtCenterClassColumn",
					"mDataProp" : "datoCorrecto",
					"sWidth":"60px",
					"fnRender":function(o,val){
						if(o.aData['datoCorrecto']){
							return '<input type="checkbox" id="datoCorrecto'+o.aData['idRow']+'" onchange="changeDatoCorrectoSupv('+o.aData['idRow']+')" checked="'+o.aData['datoCorrecto']+'"> ';
						}else{
							return '<input type="checkbox" id="datoCorrecto'+o.aData['idRow']+'" onchange="changeDatoCorrectoSupv('+o.aData['idRow']+')">';
							}	
						}
					}
				]
	    } );  
}




function changeDatoCorrectoSupv(idRow){	
	if($('form#formCedRevSupvCorrSeguimiento #datoCorrecto'+idRow).is(":checked")){		
		rubros[idRow].datoCorrecto=true;
	}else{		
		rubros[idRow].datoCorrecto=false;		
	}	
	
	compruebaAutorizacion();
	
}


function compruebaAutorizacion(){
	var flag=true;
	var flagBase=false;
	if(rubros.length==0){
		return;
	}
	for(var s=0;s<rubros.length;s++){
		if(rubros[s].datoCorrecto=='0'){			
			flag=false;
		}
	}
	
	
	if($('form#formCedRevSupvCorrSeguimiento #autorizabaseCot').is(":checked") && $('form#formCedRevSupvCorrSeguimiento #autorizaDifeBaseCoti').is(":checked")){
		flagBase=true;
	}
	
	if(flag && flagBase){
		$('#btnAutorizaCedularRevision').removeAttr('disabled');
		$('#btnRechazaCedularRevision').attr('disabled', 'disabled');
	}else{
		$('#btnAutorizaCedularRevision').attr('disabled', 'disabled');
		$('#btnRechazaCedularRevision').removeAttr('disabled');
	}
}

function recalcularSumaTotalSupv(){
	 var totalImporteAclarado=0.0;
	 var totalImportePorAclarar=0.0;
	 var totalImportes=0.0;
	 for(var s=0;s<rubros.length;s++){
			totalImporteAclarado+=parseFloat(rubros[s].importeAclarado);
			totalImportePorAclarar+=parseFloat(rubros[s].importePorAclarar);
	 }
	 totalImportes=totalImporteAclarado+totalImportePorAclarar;
	$("form#formCedRevSupvCorrSeguimiento #valTotImporteAclarado").text(moneyMaskDT(totalImporteAclarado));
	$("form#formCedRevSupvCorrSeguimiento #valTotImportePorAclarar").text(moneyMaskDT(totalImportePorAclarar));
	$("form#formCedRevSupvCorrSeguimiento #valTotImportAcla").text(moneyMaskDT(totalImportePorAclarar));
	$("form#formCedRevSupvCorrSeguimiento #valTotImporte").text(moneyMaskDT(totalImportes));	
	
	
	var va=parseFloat(quitaFormato($("form#formCedRevSupvCorrSeguimiento #difeBaseCotiIMSS").text()));	
	if(va!=0){		
		var re=(totalImportePorAclarar/va)*100;
		var res=re+'';
		$("form#formCedRevSupvCorrSeguimiento #valPorcRaz").text(res.substring(0,5)+'%');
		if(re<=9.0 && re>0){
			$("form#formCedRevSupvCorrSeguimiento #valDifDet").text('RAZONABLE');			
		}else{
			$("form#formCedRevSupvCorrSeguimiento #valDifDet").text('NO RAZONABLE');
		}
	}else{
		$("form#formCedRevSupvCorrSeguimiento #valDifDet").text('NO RAZONABLE');
	}
}



function autorizarRevisionSupv(){
	
	if(dataPrincipal==undefined){
		jAlert("No se han presentado cambios","Informaci\u00f3n ");
		return;
	}
	dataPrincipal.cedulaRevisionAudVO.autorizaBaseCotPaga=$('form#formCedRevSupvCorrSeguimiento #autorizabaseCot').is(":checked");
	dataPrincipal.cedulaRevisionAudVO.autorizaDifBaseCot=$('form#formCedRevSupvCorrSeguimiento #autorizaDifeBaseCoti').is(":checked");
	dataPrincipal.cedulaRevisionAudVO.observaciones=$("form#formCedRevSupvCorrSeguimiento #txAreaObservaSuperv").val();
	dataPrincipal.cedulaRevisionAudVO.presuntivo=presuntivo;
	var sVo=JSON.stringify(dataPrincipal.cedulaRevisionAudVO);
	claveAnexoSolCorr=$("form#formCedRevSupvCorrSeguimiento #regPatronal").val();
	var sVarSeg = '{"cveSolCorr":"'+claveSolCorr+'","cedulaRevisionAudVO":'+sVo+',"nuFolio":"'+numFolio+'","cveEjercicio":"'+$("form#formCedRevSupvCorrSeguimiento #numEjercicio").val()+'","cvePresentaCorr":"'+dataPrincipal.cedulaRevisionAudVO.cvePresentaCorr+'","regPatronal":"'+$("form#formCedRevSupvCorrSeguimiento #regPatronal option:selected").text()+'" ,"cveAnexoSolCorr":"'+claveAnexoSolCorr+'"}';

	var clase = jQuery.parseJSON(sVarSeg);	
	$.postJSON_Sync("correccion/autorizaCedulaRevision.do", clase, function(data) {
		observ=data.cedulaRevisionAudVO.observaciones;
		getBaseCotPagadaSupv();
		jAlert(data.exito,"Informaci\u00f3n");
		

		if(data.cedulaRevisionAudVO.autorizacionCompleta){
			//Se pasa la clave al modulo de Validacion y se activaria la pestania
			$("form#formCedValidacionSeguimientoCorr #cveConsolidaImporteCedValHdn").val(data.cveRecepcion);	
			deshabilitaCamposXForma('formCedRevSupvCorrSeguimiento');
			habilitaCampo("form#formCedRevSupvCorrSeguimiento #numEjercicio");
			habilitaCampo("form#formCedRevSupvCorrSeguimiento #regPatronal");
		}
		getDetalleValidacion();
		ejecutaReglasValidacion(rolUsuarioCedValidaSuper);

		
	
		
	}).error(function(data){
		jAlert('No se puede autorizar la c\u00e9dula, favor de reintentar ','Alerta');
	});
}


function rechazaRevisionSupv(){
	
		if(dataPrincipal==undefined){
			jAlert("No se han presentado cambios","Informaci\u00f3n");
			return;
		}
		dataPrincipal.cedulaRevisionAudVO.autorizaBaseCotPaga=$('form#formCedRevSupvCorrSeguimiento #autorizabaseCot').is(":checked");
		dataPrincipal.cedulaRevisionAudVO.autorizaDifBaseCot=$('form#formCedRevSupvCorrSeguimiento #autorizaDifeBaseCoti').is(":checked");
		dataPrincipal.cedulaRevisionAudVO.observaciones=$("form#formCedRevSupvCorrSeguimiento #txAreaObservaSuperv").val();
		dataPrincipal.cedulaRevisionAudVO.presuntivo=presuntivo;
		var sVo=JSON.stringify(dataPrincipal.cedulaRevisionAudVO);
		var sVarSeg = '{"cveSolCorr":"'+claveSolCorr+'","cedulaRevisionAudVO":'+sVo+',"nuFolio":"'+numFolio+'","cveEjercicio":"'+$("form#formCedRevSupvCorrSeguimiento #numEjercicio").val()+'","cvePresentaCorr":"'+dataPrincipal.cedulaRevisionAudVO.cvePresentaCorr+'","regPatronal":"'+$("form#formCedRevSupvCorrSeguimiento #regPatronal option:selected").text()+'","cveAnexoSolCorr":"'+claveAnexoSolCorr+'"}';

		var clase = jQuery.parseJSON(sVarSeg);	
		$.postJSON_Sync("correccion/rechazaCedulaRevision.do", clase, function(data) {	
			observ=data.cedulaRevisionAudVO.observaciones;
			getBaseCotPagadaSupv();
			ejecutaReglasValidacion(rolUsuarioCedValidaSuper);
			jAlert(data.exito,"Informaci\u00f3n");
		}).error(function(data){
			jAlert('No se puede rechazar la c\u00e9dula, favor de reintentar ','Alerta');
		});
	}


//function guardarRevisionSuperv(){
//	if(dataPrincipal==undefined){
//		jAlert("No se han presentado cambios","Info");
//		return;
//	}
//	
//	
//	dataPrincipal.cedulaRevisionAudVO.autorizaBaseCotPaga=$('form#formCedRevSupvCorrSeguimiento #autorizabaseCot').is(":checked");
//	dataPrincipal.cedulaRevisionAudVO.autorizaDifBaseCot=$('form#formCedRevSupvCorrSeguimiento #autorizaDifeBaseCoti').is(":checked");
//
//	var sVo=JSON.stringify(dataPrincipal.cedulaRevisionAudVO);
//	var sVarSeg = '{"cveSolCorr":"'+claveSolCorr+'","cedulaRevisionAudVO":'+sVo+',"nuFolio":"'+numFolio+'","cveEjercicio":"'+$("form#formCedRevSupvCorrSeguimiento #numEjercicio").val()+'","cvePresentaCorr":"'+dataPrincipal.cedulaRevisionAudVO.cvePresentaCorr+'","regPatronal":"'+$("form#formCedRevSupvCorrSeguimiento #regPatronal").val()+'"}';
//	var clase = jQuery.parseJSON(sVarSeg);	
//	$.postJSON("correccion/guardaRevisionSupervisor.do", clase, function(data) {		
//		jAlert(data.exito,"Info");
//	});
//}



function generaTablaSupvTotalBaseDifSV(data){
	dataTableRubros= $("form#formCedRevSupvCorrSeguimiento #dtTotalBdDif").dataTable( {
		"aaData": data,
		"bAutoWidth" : true,
		bFilter : false,
		bJQueryUI : true,
		"bDestroy": true,
		bSort: false,
		"aoColumns" : [{				
			"sTitle" : "RP",
			"mDataProp" : "registroPatronal",
			"sClass": "dtCenterClassColumn"
			
		},{
				
			"sTitle" : "Periodo",
			"mDataProp" : "periodo",
			"sClass": "dtCenterClassColumn"
		},{
				
			"sTitle" : "Base Cot. Pagada",
			"mDataProp" : "baseCotPagada",
			"sClass": "dtRightClassColumn",
			"fnRender": function ( o, val ) {
				return '<div align="right">$'+moneyMaskDT(o.aData['baseCotPagada'])+"</div>";
			}
		},{
				
			"sTitle" : " Dif. BCP",
			"mDataProp" : "difBaseCotPagada",
			"sClass": "dtRightClassColumn",
			"fnRender": function ( o, val ) {
				return '<div align="right">$'+moneyMaskDT(o.aData['difBaseCotPagada'])+"</div>";
			}
		},{
				
			"sTitle" : "Total (Suma BCP + BSP)",
			"mDataProp" : "totalBase",
			"sClass": "dtRightClassColumn",
			"fnRender": function ( o, val ) {
				return '<div align="right">$'+moneyMaskDT(o.aData['totalBase'])+"</div>";
			}
		},{
				
			"sTitle" : "Total por Aclarar",
			"mDataProp" : "totalPorAclarar",
			"sClass": "dtRightClassColumn",
			"fnRender": function ( o, val ) {
				return '<div align="right">$'+moneyMaskDT(o.aData['totalPorAclarar'])+"</div>";
			}
		},{
				
			"sTitle" : "Razonable",
			"mDataProp" : "razonable",
			"sClass": "dtCenterClassColumn"
		},{
				
			"sTitle" : "Estatus",
			"mDataProp" : "estatus",
			"sClass": "dtCenterClassColumn"
		}]
    } );  
	
}


function limpiaFormulario(){
	$("form#formCedRevSupvCorrSeguimiento #baseCotImss").text('0');
	$("form#formCedRevSupvCorrSeguimiento #difeBaseCotiIMSS").text('0');
	$("form#formCedRevSupvCorrSeguimiento #totalConceptoImss").text('0');
	$("form#formCedRevSupvCorrSeguimiento #valTotImporteAclarado").text('0');
	$("form#formCedRevSupvCorrSeguimiento #valTotImportePorAclarar").text('0');
	$("form#formCedRevSupvCorrSeguimiento #valTotImporte").text('0');
	$("form#formCedRevSupvCorrSeguimiento #valTotImportAcla").text('0');
	$("form#formCedRevSupvCorrSeguimiento #valPorcRaz").text('0');
	$("form#formCedRevSupvCorrSeguimiento #valDifDet").text('');
	$("#autorizabaseCot").removeAttr("checked");
	$("#autorizaDifeBaseCoti").removeAttr("checked");
	
}

function generaReporte(){
	
	$.postJSON("correccion/generaReporte.do", null, function(data) {	
		var contexto = $('#idContexto').val();
		var urlPDF = contexto + "/servlet/EnviaArchivoServlet";
		window.open(urlPDF,"PresentacionCorrecion","menubar=1,resizable=1,width=500,height=500");
	});
}

function quitaFormato(valor){
	var currentValue = valor.replace(/[\,]+/gi,"");
	return myNumber = Number(currentValue);	
}

function generaResumenRevisionCed(data){
	
	 $("form#formResumenSeguimiento #lbValObserva").text(data.cedulaRevisionAudVO.observaciones);
	 $("form#formResumenSeguimiento #lbValFechaCedulaRev").text(data.cedulaRevisionAudVO.fechaAplicacionCedula);
	 if(data.cedulaRevisionAudVO.presuntivo){
		 $("form#formResumenSeguimiento #lbValMetodoCalc").text("Presuntivo");
	 }
	
	 var tabla="<TABLE align='rigth' class='tablaverde2' WIDTH=350  border=1 cellspacing=0>";
	 tabla+="<thead>";
	 tabla+="<tr>";
	 tabla+="<td align='center'>RP</td>";
	 tabla+="<td align='center'>Ejercicio</td>";
	 tabla+="<td align='center'>Porcentaje</td>";
	 tabla+="</tr>";
	 tabla+="</thead>";
	 for(var s=0;s<consolidadoCedulaValidacion.length;s++){
		 tabla+="<tr>";
		 tabla+="<td>"+consolidadoCedulaValidacion[s].registroPatronal+"</td>";
		 tabla+="<td align='right'>"+consolidadoCedulaValidacion[s].periodo+"</td>";
	
		 var difBase=parseFloat(consolidadoCedulaValidacion[s].difBaseCotPagada);
		 var totalImportePorAclarar=parseFloat(consolidadoCedulaValidacion[s].totalPorAclarar);
		
		 if(difBase!=0){
			 var re=(totalImportePorAclarar/difBase)*100;
			 tabla+="<td align='right'>"+re+"%</td>";
		 }else{
			 tabla+="<td align='right'>---</td>";
		 }
		
		 tabla+="</tr>";		
		 
	 }
	 tabla+="</TABLE>";	
	 
	 $("form#formResumenSeguimiento #tablaPorce").html(tabla);
}

