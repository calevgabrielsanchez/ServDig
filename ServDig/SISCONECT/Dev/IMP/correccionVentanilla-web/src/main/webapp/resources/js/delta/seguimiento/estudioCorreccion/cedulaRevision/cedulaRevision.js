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
var presuntivo;
var observ;
var fechaAplicaCedula;
var flagRef;
var objetoForma;
var consolidadoCedulaValidacion;
var rolCeduRevisionAud;
var flagUpdate=true;
var razonAcceso;

//Recuperamos la lista de ejercicios,rp y totales de RP asocioados a la presnetacion y solCorr
function incializaCedulaRevision(data){
	consolidadoCedulaValidacion=null;
	rolCeduRevisionAud=data.user.cveRol;
	dataPrincipal=null;
	numFolio=data.nuFolio;
	regPatronal=data.regPatronal;
	claveSolCorr=data.cveSolCorr;
	cvePresentaCorre=data.cvePresentaCorr;
	var sVarSeg = '{"cveSolCorr":"'+claveSolCorr+'","regPatronal":"'+regPatronal+'","nuFolio":"'+numFolio+'","cvePresentaCorr":"'+cvePresentaCorre+'"}';
	var clase = jQuery.parseJSON(sVarSeg);
	$("form#formResumenSeguimiento #lbValMetodoCalc").text("");
	$.postJSON_Sync("correccion/cedulaRevisionAud.do", clase, function(data) {
		$("#btnAgregarListadoRubros").prop('disabled','disabled');
		$("form#formCedRevAudCorrSeguimiento #checkPresuntivo").removeAttr('disabled');
		generaCombos(data.cedulaRevisionAudVO);		
		generaTabla(data.cedulaRevisionAudVO.rubros);	
		generatablaTotalBaseDif(data.cedulaRevisionAudVO.totalRpEjer);
		consolidadoCedulaValidacion=data.cedulaRevisionAudVO.totalRpEjer;
		generaResumenRevisionCed(data);
		observ=data.cedulaRevisionAudVO.observaciones;
		if(data.cedulaRevisionAudVO.solicitudConstruccion){
			$("form#formCedRevAudCorrSeguimiento #wrapperCheckPresuntivo").css("display", "block");
		}else{
			$("form#formCedRevAudCorrSeguimiento #wrapperCheckPresuntivo").css("display", "none");
		}
		
		
		if(data.cedulaRevisionAudVO.presuntivo){
			$('form#formCedRevAudCorrSeguimiento #checkPresuntivo').attr('checked', true);
		}else{
			$('form#formCedRevAudCorrSeguimiento #checkPresuntivo').attr('checked', false);
		}
		
		$("form#formCedRevAudCorrSeguimiento #baseCotImss").removeAttr('disabled');		
		$("form#formCedRevAudCorrSeguimiento #difeBaseCotiIMSS").removeAttr('disabled');	
		
		$('form#formCedRevAudCorrSeguimiento #fechaAplicaCedula').val(data.cedulaRevisionAudVO.fechaAplicacionCedula);
		fechaAplicaCedula=data.cedulaRevisionAudVO.fechaAplicacionCedula;
		
		
		$('form#formCedRevAudCorrSeguimiento #checkPresuntivo').change(function(){
			if($('form#formCedRevAudCorrSeguimiento #checkPresuntivo').is(":checked")){
				$("form#formResumenSeguimiento #lbValMetodoCalc").text("PRESUNTIVO");
				presuntivo=true;
			}else{
				$("form#formResumenSeguimiento #lbValMetodoCalc").text("");
				presuntivo=false;
			}
		});
		
		$("#valDifDet").val('');
	}).error(function(data){
		jAlert('No se puede consultar la informaci\u00f3n principal de la revisi\u00f3n de c\u00e9dula, favor de reintentar ','Alert Dialog');
	});
	
	//limpia formulario
	$('#formCedRevAudCorrSeguimiento').each (function(){
		this.reset();
	});
	
	//Listeners para los componentes del form
	generaListeners();
	
}

//Inicializa los combos de ejercicio y RP
function generaCombos(data){
	
	var options = "<option value='-1' >--Por favor seleccione--</option>";
	var optionsRegPat="<option value='-1' >--Por favor seleccione--</option>";
	for(var s=0;s<data.ejercicios.length;s++){
		options += "<option value='"+ data.ejercicios[s] +"'>"+ data.ejercicios[s]+"</option>";				
	}

	$("form#formCedRevAudCorrSeguimiento #numEjercicio").html(options);
}

// asigna las funciones para cada componente
function generaListeners(){
	$("form#formCedRevAudCorrSeguimiento #numEjercicio,form#formCedRevAudCorrSeguimiento #regPatronal").unbind();
	$('form#formCedRevAudCorrSeguimiento #btnGuardarRevision').unbind();
	$('form#formCedRevAudCorrSeguimiento #btnAgregarListadoRubros').unbind();
	$('form#formCedRevAudCorrSeguimiento #btnFinalizaCedularRevision').unbind();
	
	$("form#formCedRevAudCorrSeguimiento #regPatronal").change(function(){
		$("#btnAgregarListadoRubros").removeAttr("disabled");
		if($("form#formCedRevAudCorrSeguimiento #numEjercicio").val()!='-1' && $("form#formCedRevAudCorrSeguimiento #regPatronal").val()!='-1'){
			getBaseCotPagada();
		}else{
			limpiarForma();
		}
	});

	
	
	$("form#formCedRevAudCorrSeguimiento #numEjercicio").change(function(){	
		
		$("#btnAgregarListadoRubros").removeAttr("disabled");
		if($("form#formCedRevAudCorrSeguimiento #numEjercicio").val()=='-1'){
			limpiarForma();
			$('form#formCedRevAudCorrSeguimiento #fechaAplicaCedula').val(fechaAplicaCedula);
		}else{
			
			var val=$("form#formCedRevAudCorrSeguimiento #numEjercicio").val();
			limpiarForma();
			$("form#formCedRevAudCorrSeguimiento #numEjercicio").val(val);
			$('form#formCedRevAudCorrSeguimiento #fechaAplicaCedula').val(fechaAplicaCedula);
		}
		var sVarSeg = '{"cveSolCorr":"'+claveSolCorr+'","cveEjercicio":"'+$("form#formCedRevAudCorrSeguimiento #numEjercicio").val()+'"}';
		var clase = jQuery.parseJSON(sVarSeg);
		
		$.postJSON("correccion/consultaRegistrosPatronales.do", clase, function(data) {
			//limpiarForma();
			if(dataTableRubros!=undefined && rubros!=undefined){
				dataTableRubros.fnClearTable();
				rubros.splice(0);
				recalcularSumaTotal();
			}
			
			var optionsRegPat="<option value='-1' >--Por favor seleccione--</option>";
			for(var s=0;s<data.length;s++){
				optionsRegPat += "<option value='"+ data[s][1] +"'>"+ data[s][0]+"</option>";		
			}
			$("form#formCedRevAudCorrSeguimiento #regPatronal").html(optionsRegPat);
		}).error(function(data){
			jAlert('No se puede recuperar la lista de registros patronales, favor de reintentar ','Alert Dialog');
		});
	});

	
	
	$('form#formCedRevAudCorrSeguimiento #baseCotImss,form#formCedRevAudCorrSeguimiento #difeBaseCotiIMSS,form#formCedRevAudCorrSeguimiento #baseCotPatron').change(function(){				
		recalculaConceptos($(this));
	});
	
	$('form#formCedRevAudCorrSeguimiento #difeBaseCotiIMSS').change(function(){
	     recalculaConceptos($(this));
	     recalcularSumaTotal();
	});	
	
	$('form#formCedRevAudCorrSeguimiento #btnAgregarListadoRubros').click(function(){
		agregarRubro();
	});
	
	$('form#formCedRevAudCorrSeguimiento #btnGuardarRevision').click(function(){
		guardarRevision();
	});
		
	
	$('form#formCedRevAudCorrSeguimiento #btnFinalizaCedularRevision').click(function(){
		finalizaCedulaRevision();
		$("form#formCedRevAudCorrSeguimiento #checkPresuntivo").attr('disabled', 'disabled');	
	});	
	
	
	$("form#formCedRevAudCorrSeguimiento #percepcionesList").change(function(){
	    $("#percepcionNueva").val('');
	});	
	
	$('form#formCedRevAudCorrSeguimiento #checkPresuntivo').change(function(){
		if($('form#formCedRevAudCorrSeguimiento #checkPresuntivo').is(":checked")){
			$("form#formResumenSeguimiento #lbValMetodoCalc").text("PRESUNTIVO");
			presuntivo=true;
		}else{
			$("form#formResumenSeguimiento #lbValMetodoCalc").text("");
			presuntivo=false;
		}
	});	
	
}



//Recupera la informacion asociada a un ejercicio y un registro patronal
function getBaseCotPagada(){
	
	var ejercicio=$("form#formCedRevAudCorrSeguimiento #numEjercicio").val();
	var regPatro=$("form#formCedRevAudCorrSeguimiento #regPatronal option:selected").text();
	
	
	
	var folio=numFolio;	
	claveAnexoSolCorr=$("form#formCedRevAudCorrSeguimiento #regPatronal").val();
	var sVarSeg = '{"cveSolCorr":"'+claveSolCorr+'","cveEjercicio":"'+ejercicio+'","regPatronal":"'+regPatro+'","nuFolio":"'+numFolio+'","cvePresentaCorr":"'+cvePresentaCorre+'","cveAnexoSolCorr":"'+claveAnexoSolCorr+'"}';
	var clase = jQuery.parseJSON(sVarSeg);
	
	$.postJSON("correccion/getBaseCotPagadaImss.do", clase, function(data) {
		
		if(flagRef && !data.cedulaRevisionAudVO.accesoDetalle){
			flagRef=false;
			return;
		}
		
		if(!data.cedulaRevisionAudVO.accesoDetalle){
			flagUpdate=false;
			razonAcceso=data.cedulaRevisionAudVO.razonAcceso;
			jAlert(data.cedulaRevisionAudVO.razonAcceso,"Informaci\u00f3n");
			$("form#formCedRevAudCorrSeguimiento #checkPresuntivo").attr('disabled', 'disabled');	
			//return;
		}else{
			flagUpdate=true;
		}
		
		
		generatablaTotalBaseDif(data.cedulaRevisionAudVO.totalRpEjer);
		intTotalRegRubro=data.cedulaRevisionAudVO.rubros.length;
		dataPrincipal=data;
		dataPrincipal.cedulaRevisionAudVO.cvePresentaCorr=cvePresentaCorre;
		
		
		
		$("form#formCedRevAudCorrSeguimiento #baseCotPatron").val(moneyMaskDT(data.cedulaRevisionAudVO.baseCotPagaImsPatron,2));
		$("form#formCedRevAudCorrSeguimiento #baseCotImss").val(moneyMaskDT(data.cedulaRevisionAudVO.baseCotPagaImsIMSS,2));
		$("form#formCedRevAudCorrSeguimiento #difeBaseCotiPatron").val(moneyMaskDT(data.cedulaRevisionAudVO.difBaseCotiPatron,2));
		$("form#formCedRevAudCorrSeguimiento #difeBaseCotiIMSS").val(moneyMaskDT(data.cedulaRevisionAudVO.difBaseCotiIMSS,2));			
		$("form#formCedRevAudCorrSeguimiento #txAreaObserva").val(observ);	
		
		
		if(data.cedulaRevisionAudVO.autorizaBaseCotPaga){
			$("form#formCedRevAudCorrSeguimiento #baseCotImss").attr('disabled', 'disabled');		
		}
		if(data.cedulaRevisionAudVO.autorizaDifBaseCot){
			$("form#formCedRevAudCorrSeguimiento #difeBaseCotiIMSS").attr('disabled', 'disabled');
		}
		
		
		
		recalculaConceptos($("form#formCedRevAudCorrSeguimiento #baseCotPatron"));
		generaTabla(data.cedulaRevisionAudVO.rubros);			
		
		
		var options = "<option value='-1' >--Por favor seleccione--</option>";
		var flag=false;
		
		for(var s=0;s<data.cedulaRevisionAudVO.percepciones.length;s++){
			flag=false;
			for(var t=0;t<data.cedulaRevisionAudVO.rubros.length;t++){
				if(data.cedulaRevisionAudVO.percepciones[s].cvePercepcion==data.cedulaRevisionAudVO.rubros[t].cvePercepcion){
					flag=true;
				}
			}
			if(!flag){
				options += "<option value='"+ data.cedulaRevisionAudVO.percepciones[s].cvePercepcion +"'>"+ data.cedulaRevisionAudVO.percepciones[s].txRemuneracion+"</option>";
			}
		}
		
		
		
		
		$("form#formCedRevAudCorrSeguimiento #percepcionesList").html(options);
		
	}).error(function(data){
		jAlert('No se puede ejectuar la consulta, favor de reintentar ','Alert Dialog');
	});
	
}
//Recalcula las cifras de conceptos
function recalculaConceptos(componente){
		
	if(componente.val()=='' || isNaN(parseFloat(quitaFormato(componente.val())))){
		componente.val('0.0');
	}
	var flBaseCotPatron=parseFloat(quitaFormato($("#baseCotPatron").val()));
	var flDifBaseCotPatron=parseFloat(quitaFormato($("#difeBaseCotiPatron").val()));
	var flTotalPatron=flBaseCotPatron+flDifBaseCotPatron;	
	var flBaseCotImss=parseFloat(quitaFormato($("#baseCotImss").val()));
	var flDifBaseCotImss=parseFloat(quitaFormato($("#difeBaseCotiIMSS").val()));
	var flTotalImss=flBaseCotImss+flDifBaseCotImss;	
	$("#totalConceptoPatron").val(moneyMaskDT(flTotalPatron,2));
	$("#totalConceptoImss").val(moneyMaskDT(flTotalImss,2));
	
}
//Metodo que genera la tabla de rubros
function generaTabla(data){
	
	rubros=data;	
	dataTableRubros= $("#dtRubrosAB").dataTable( {
			"aaData": rubros,
			"bAutoWidth" : true,
			bFilter : false,
			bJQueryUI : true,
			"bDestroy": true,
			bSort: false,
			"fnInfoCallback": function( oSettings, iStart, iEnd, iMax, iTotal, sPre ) {			   
				recalculaSumaVertical();
				recalcularSumaTotal();
			  },
			"aoColumns" : [{				
				"sTitle" : "Eliminar",
				"mDataProp" : "idRow",
				"sClass": "dtCenterClassColumn",
				"bVisible": false
			},{				
				"sTitle" : "Eliminar",
				"sClass": "dtCenterClassColumn",
				"fnRender":function(o,val){
					return '<input type="button" value="X" id="botonElim'+o.aData['idRow']+'" onclick="borrarRegistro('+o.aData['idRow']+')" >';
					//return '<IMG SRC="../resources/images/delete.png" id="botonElim'+o.aData['idRow']+'" onclick="borrarRegistro('+o.aData['idRow']+')">';
					//return '<button><IMG SRC="../resources/images/delete.png" id="botonElim'+o.aData['idRow']+'" onclick="borrarRegistro('+o.aData['idRow']+')"></button>';					
				}
			},{
					
				"sTitle" : "Concepto",
				"mDataProp" : "concepto",
				"sClass": "dtCenterClassColumn",
				"sWidth":"120px"
			},{
					
				"sTitle" : "Autodeterminaci\u00f3n patr\u00f3n",
				"mDataProp" : "autoDeterminacion",
				"sClass": "dtCenterClassColumn"
			},{
					
				"sTitle" : "Importe Aclarado",
				"mDataProp" : "importeAclarado",
				"sClass": "dtCenterClassColumn",
				"fnRender": function ( o, val ) {
					var valor=MoneyToNumber(o.aData['importeAclarado']);
					var val='<label for="importeAclaradoRow'+o.aData['idRow']+'">$:</label><input size="18"  value="'+valor+'" id="importeAclaradoRow'+o.aData['idRow']+'"  onclick="edit(this);" maxlength="16" onchange="changeImporteAclarado('+o.aData['idRow']+')"  onblur="moneyMask(this,2);validaPresicionImportesTabla(this,'+o.aData['idRow']+');" class="inputMoney" />';
					return val;
			        }
			},{
					
				"sTitle" : "Importe Por aclarar",
				"mDataProp" : "importePorAclarar",
				"sClass": "dtCenterClassColumn",
				"fnRender": function ( o, val ) {
					var valor=MoneyToNumber(o.aData['importePorAclarar']);
					var val='<label for="importePorAclararRow'+o.aData['idRow']+'">$:</label><input size="18"  value="'+valor+'" id="importePorAclararRow'+o.aData['idRow']+'"  onclick="edit(this);" maxlength="16" onchange="changeImportePorAclarar('+o.aData['idRow']+')"  onblur="moneyMask(this,2);validaPresicionImportesTabla(this,'+o.aData['idRow']+');" class="inputMoney" />';
					return val;
			        }
			},{
					
				"sTitle" : "Total<br>",					
				"sClass": "dtCenterClassColumn",
				"fnRender": function ( o, val ) {
					var val='<label for="totalHorizontal'+o.aData['idRow']+'">$:</label><input size="18"  value="0.0"  id="totalHorizontal'+o.aData['idRow']+'" readonly="readonly" class="inputMoney" />	'
			        return val;
			         }
			},{
				
				"sTitle" : "Dato Correcto",					
				"sClass": "dtCenterClassColumn",
				"mDataProp" : "datoCorrecto",
				"fnRender":function(o,val){
						if(o.aData['datoCorrecto']){
							return '<input type="checkbox" id="datoCorrecto'+o.aData['idRow']+'" disabled  checked="'+o.aData['datoCorrecto']+'">';
						}else{
							return '<input type="checkbox" id="datoCorrecto'+o.aData['idRow']+'" disabled >';
						}					
					}
				}
				]
	    } );  
//	recalculaSumaVertical();
//	recalcularSumaTotal();	
	deshabilitaCamposGrid(data);
}

function deshabilitaCamposGrid(data){
	
	for(var s=0;s<rubros.length;s++){
		if(rubros[s].datoCorrecto){
			$('#importeAclaradoRow'+rubros[s].idRow).attr('disabled', 'disabled');
			$('#importePorAclararRow'+rubros[s].idRow).attr('disabled', 'disabled');
			$('#totalHorizontal'+rubros[s].idRow).attr('disabled', 'disabled');
			$('#botonElim'+rubros[s].idRow).attr('disabled', 'disabled');
		}
	}
	
}

//listeners para los componentes de la tabla de rubros
function changeImporteAclarado(idRow){
	$("#importeAclaradoRow"+idRow).val(($("#importeAclaradoRow"+idRow).val()).replace(/[^0-9.,]*/gi,"")); 
	
	if($("#importeAclaradoRow"+idRow).val()=='' || isNaN(parseFloat($("#importeAclaradoRow"+idRow).val()))){
		$("#importeAclaradoRow"+idRow).val('0');
	}
	var posicion;
    for(var s=0;s<rubros.length;s++){
         if(rubros[s].idRow==idRow){
         	posicion=s;
         }
     }
	rubros[posicion].importeAclarado=quitaFormato($("#importeAclaradoRow"+idRow).val());
	recalculaSumaLineal(idRow,posicion);
	recalcularSumaTotal();
}
//Listener para la tabla de rubros
function changeImportePorAclarar(idRow){
	$("#importePorAclararRow"+idRow).val(($("#importePorAclararRow"+idRow).val()).replace(/[^0-9.,]*/gi,"")); 
	if($("#importePorAclararRow"+idRow).val()=='' || isNaN(parseFloat($("#importePorAclararRow"+idRow).val()))){
		$("#importePorAclararRow"+idRow).val('0');
	}
	var posicion;
    for(var s=0;s<rubros.length;s++){
         if(rubros[s].idRow==idRow){
         	posicion=s;
         }
     }
	rubros[posicion].importePorAclarar=quitaFormato($("#importePorAclararRow"+idRow).val());
	recalculaSumaLineal(idRow,posicion);
	recalcularSumaTotal();
}

function recalculaSumaLineal(idRow,posicion){
	var val=parseFloat(quitaFormato($("#importeAclaradoRow"+idRow).val()))+parseFloat(quitaFormato($("#importePorAclararRow"+idRow).val()));
	$("#totalHorizontal"+idRow).val(MoneyToNumber(val));	
	rubros[posicion].total=val;

}

function changeDatoCorrecto(idRow){	
	if($('#datoCorrecto'+idRow).is(":checked")){
		rubros[idRow].datoCorrecto=1;
	}else{
		rubros[idRow].datoCorrecto=0;		
	}	
}

function borrarRegistro(idRow){
	
	jConfirm('¿Esta seguro que desea borrar el concepto ?', 'Confirmaci\u00f3n', function(r) {	    
	    if(r){
	    	var posicion;
	        for(var s=0;s<rubros.length;s++){
	             if(rubros[s].idRow==idRow){
	             	posicion=s;
	             }
	         }
	    $('#percepcionesList').append("<option value='"+ rubros[posicion].cvePercepcion +"'>"+ rubros[posicion].concepto+"</option>");
	 	dataTableRubros.fnDeleteRow(posicion);	
	 	rubros.splice(posicion,1);
	 	recalcularSumaTotal();
	    }
	});
    
}

function recalculaSumaVertical(){
	
	
	var aclarado;
	var porAclarar;
	for(var s=0;s<rubros.length;s++){

		
		
		
	//	$("#totalHorizontal"+rubros[s].idRow).val(parseFloat($("#importeAclaradoRow"+rubros[s].idRow).val())+parseFloat($("#importePorAclararRow"+rubros[s].idRow).val()));
		$("#totalHorizontal"+rubros[s].idRow).val(MoneyToNumber(parseNumber(rubros[s].importeAclarado)+parseNumber(rubros[s].importePorAclarar)));
		//$("#totalHorizontal"+rubros[s].idRow).val(aclarado+porAclarar);
	}	
}

function parseNumber(obj){
	
	var s=''+obj;
	var val=s.replace(/[\,]+/gi,"");
	return parseFloat(val);
}
function recalcularSumaTotal(){
	 var totalImporteAclarado=0.0;
	 var totalImportePorAclarar=0.0;
	 var totalImportes=0.0;
	 for(var s=0;s<rubros.length;s++){
			totalImporteAclarado+=parseFloat(rubros[s].importeAclarado);
			totalImportePorAclarar+=parseFloat(rubros[s].importePorAclarar);
	 }
	 totalImportes=totalImporteAclarado+totalImportePorAclarar;
	$("#valTotImporteAclarado").val(MoneyToNumber(totalImporteAclarado));
	$("#valTotImportePorAclarar").val(MoneyToNumber(totalImportePorAclarar));
	$("#valTotImporte").val(MoneyToNumber(totalImportes));	
	$("#valTotImportAcla").val(MoneyToNumber(totalImportePorAclarar));
	
	var va=parseFloat(quitaFormato($("#difeBaseCotiIMSS").val()));	
	if(va!=0){		
		var re=(totalImportePorAclarar/va)*100;
		var res=re+'';
		$("#valPorcRaz").val(res.substring(0,5)+'%');
		if(re<=9.0 && re>0){
			$("#valDifDet").val('RAZONABLE');			
		}else{
			$("#valDifDet").val('NO RAZONABLE');
		}
	}else{
		$("#valDifDet").val('NO RAZONABLE');
	}
}

function agregarRubro(){

	if(!flagUpdate){
		return;
	}
	
	var flag=false;
	if($("#percepcionesList").val()=='-1' && $("#percepcionNueva").val()==''){
		jAlert("Por favor seleccione una percepci\u00f3n","Informaci\u00f3n");
	}else if($("#percepcionesList").val()=='-1' && $("#percepcionNueva").val()!=''){
			for(var s=0;s<rubros.length;s++){			
			if(rubros[s].concepto==$("#percepcionNueva").val()){
				flag=true;
			}
		}
		

		$("#percepcionesList option").each(function(){
			if($("#percepcionNueva").val()==$(this).text()){
				flag=true;		
			}
		});
		
		//se agrega a la BD
		if(!flag){
			var sVarSeg = '{"txRemuneracion":"'+ $("#percepcionNueva").val()+'","folioCorreccion":"'+numFolio+'","validaPercepcion":"true"}';
			var clase = jQuery.parseJSON(sVarSeg);	
				
			$.postJSON("../catalogo/percepciones/agregar.do",clase, function(datos) {
				
				
				if(datos.error!=''){
					jAlert(datatos.error,"Informaci\u00f3n");
				}else{
					var cvePercepcion=datos.cvePercepcion;				
					intTotalRegRubro+=1;						
						var ob={
						"total":null,
						"cvePercepcion":cvePercepcion,
						"idRow":intTotalRegRubro,
						"idRubro":'-1',
						"concepto":$("#percepcionNueva").val(),
						"autoDeterminacion":"$0.0",
						"importeAclarado":"0",
						"importePorAclarar":"0",
						"datoCorrecto":false
						};						
						rubros.push(ob);
						dataTableRubros.fnAddData(ob);
			
					
				}
			}).error(function(data){
				jAlert('Error al intentar agregar la percepci\u00f3n, favor de reintentar ','Alerta');
			});
		}else{			
			jAlert("Ya existe una percepci\u00f3n de este tipo","Informaci\u00f3n");			
		}		
	}else{		
		for(var s=0;s<rubros.length;s++){
			if(rubros[s].cvePercepcion==$("#percepcionesList").val()){
				flag=true;
			}
		}
		
		if(flag){
			jAlert("Ya existe una percepci\u00f3n de este tipo","Informaci\u00f3n");
		}else{			
			intTotalRegRubro+=1;
			var ob={
				"total":null,
				"cvePercepcion":$("#percepcionesList").val(),
				"idRow":intTotalRegRubro,
				"idRubro":'-1',
				"concepto":$("#percepcionesList option:selected").text(),
				"autoDeterminacion":"$0.0",
				"importeAclarado":"0",
				"importePorAclarar":"0",
				"datoCorrecto":false
			};	
			
			$("#percepcionesList option[value='"+$("#percepcionesList").val()+"']").remove();
			rubros.push(ob);
			dataTableRubros.fnAddData(ob);			
		}				
	}	
}

function guardarRevision(){
	
	jConfirm("\u00BFEsta seguro que desea aplicar los cambios?","Confirmar",guardarRev);
		
	function guardarRev(valor){
		
		if(!valor){
			return;
		}
		
		if(!flagUpdate){
			jAlert(razonAcceso,"Informaci\u00f3n");
			return;
		}
		
		if(dataPrincipal==undefined || $("#numEjercicio").val()=="-1" || $("#regPatronal").val()=="-1"){
			jAlert("No se han presentado cambios","Informaci\u00f3n");
			return;
		}
		
		recalculaSumaVertical();
		recalcularSumaTotal();
		dataPrincipal.cedulaRevisionAudVO.baseCotPagaImsPatron=quitaFormato($("#baseCotPatron").val());
		dataPrincipal.cedulaRevisionAudVO.baseCotPagaImsIMSS=quitaFormato($("#baseCotImss").val());
		dataPrincipal.cedulaRevisionAudVO.difBaseCotiPatron=quitaFormato($("#difeBaseCotiPatron").val());
		dataPrincipal.cedulaRevisionAudVO.difBaseCotiIMSS=quitaFormato($("#difeBaseCotiIMSS").val());			
		dataPrincipal.cedulaRevisionAudVO.presuntivo=presuntivo;
		dataPrincipal.cedulaRevisionAudVO.observaciones=$("#txAreaObserva").val();	
		
		
		
		if($("#valDifDet").val()=='RAZONABLE'){
			dataPrincipal.cedulaRevisionAudVO.razonable=1;
		}else{
			dataPrincipal.cedulaRevisionAudVO.razonable=0;
		}
		
		var sVo=JSON.stringify(dataPrincipal.cedulaRevisionAudVO);
		var sVarSeg = '{"cveSolCorr":"'+claveSolCorr+'","cedulaRevisionAudVO":'+sVo+',"nuFolio":"'+numFolio+'","cveEjercicio":"'+$("#numEjercicio").val()+'","cvePresentaCorr":"'+dataPrincipal.cedulaRevisionAudVO.cvePresentaCorr+'","regPatronal":"'+$("form#formCedRevAudCorrSeguimiento #regPatronal option:selected").text()+'","cveAnexoSolCorr":"'+claveAnexoSolCorr+'"}';
		var clase = jQuery.parseJSON(sVarSeg);	
		//bloqueaSegCorr();
		$.postJSON("correccion/guardaRevision.do", clase, function(data) {	
			observ=data.cedulaRevisionAudVO.observaciones;
			if(!flagRef){
				getBaseCotPagada();		
				jAlert(data.exito,"Informaci\u00f3n");
				flagRef=false;
			}
			//desbloqueaSegCorr();
			ejecutaReglasValidacion(rolCeduRevisionAud);
		}).error(function(data){
			jAlert('No se pudo guardar los cambios , favor de reintentar','Alerta');
		});
	}
}




function generatablaTotalBaseDif(data){
	dataTableRubros= $("#dtTotalBdDif").dataTable( {
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
				return '<div align="right">$'+moneyMaskDT(o.aData['baseCotPagada'],2)+"</div>";
			}
		},{
				
			"sTitle" : " Dif. BCP",
			"mDataProp" : "difBaseCotPagada",
			"sClass": "dtRightClassColumn",
			"fnRender": function ( o, val ) {
				return '<div align="right">$'+moneyMaskDT(o.aData['difBaseCotPagada'],2)+"</div>";
			}
		},{
				
			"sTitle" : "Total (Suma BCP + BSP)",
			"mDataProp" : "totalBase",
			"sClass": "dtRightClassColumn",
			"fnRender": function ( o, val ) {
				return '<div align="right">$'+moneyMaskDT(o.aData['totalBase'],2)+"</div>";
			}
		},{
				
			"sTitle" : "Total por Aclarar",
			"mDataProp" : "totalPorAclarar",
			"sClass": "dtRightClassColumn",
			"fnRender": function ( o, val ) {
				return '<div align="right">$'+moneyMaskDT(o.aData['totalPorAclarar'],2)+"</div>";
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

function finalizaCedulaRevision(){
	
	
	jConfirm("Una ves finalizado ya no podr\u00e1 efectuar cambios en las c\u00e9dulas \u00BFEsta seguro que desea finalizar el proceso?","Confirmar",finalizaCedRev);
	
	function finalizaCedRev(valor){
		
		if(!valor){
			return;
		}
	
		if(!flagUpdate){
			jAlert(razonAcceso,"Informaci\u00f3n");
			return;
		}
		
		
		var sVarSeg = '{"cveSolCorr":"'+claveSolCorr+'","nuFolio":"'+numFolio+'","cveEjercicio":"'+$("#numEjercicio").val()+'","cvePresentaCorr":"'+cvePresentaCorre+'","regPatronal":"'+$("form#formCedRevAudCorrSeguimiento #regPatronal option:selected").text()+'"}';
		var clase = jQuery.parseJSON(sVarSeg);	
		bloqueaSegCorr();
		$.postJSON("correccion/finalizaCedulaRevision.do", clase, function(data) {
			flagRef=true;		
			getBaseCotPagada();
			jAlert(data.exito,"Informaci\u00f3n");
			setTimeout(limpiarForma, 1000);
			ejecutaReglasValidacion(rolCeduRevisionAud);
			desbloqueaSegCorr();
		}).error(function(data){
			jAlert('No se puede finalizar la c\u00e9dula, favor de reintentar ','Alert Dialog');
		});
	}
}

function limpiarForma(){
	$('#formCedRevAudCorrSeguimiento').each (function(){
		this.reset();
		$("#btnAgregarListadoRubros").prop('disabled','disabled');
	});
	
	dataTableRubros.fnClearTable();
	rubros.splice(0);
	recalcularSumaTotal();
	$('#valDifDet').val("");
}
function esSupervisor(){
	
	return true;
}

//moneyMaskDT

function MoneyToNumber(val){
	return moneyMaskDT(val,2)	
}

function quitaFormato(valor){
	var currentValue = valor.replace(/[\,]+/gi,"");
	return myNumber = Number(currentValue);	
}


function validaPresicionImportes(componente){
	if(!validaPresicionNum(componente.value,13,2)){
		jAlert("Presici\u00f3n numerica excedida","Informaci\u00f3n");
		componente.value="0.0";
		changeImporteAclarado(idRow);
		changeImportePorAclarar(idRow);
		recalculaSumaVertical();
		recalcularSumaTotal();
		componente.focus();
	}
}


function validaPresicionImportesTabla(componente,idRow){
	if(!validaPresicionNum(componente.value,13,2)){
		jAlert("Presici\u00f3n numerica excedida","Info");
		componente.value="0.0";
		changeImporteAclarado(idRow);
		changeImportePorAclarar(idRow);
		recalculaSumaVertical();
		recalcularSumaTotal();
		componente.focus();
	}
}
function edit(obj){
	
	obj.value=quitaFormato(obj.value);
}

function limitText(limitField, limitNum) {
    if (limitField.value.length > limitNum) {
        limitField.value = limitField.value.substring(0, limitNum);
    } 
}

function limpiaCombo(){
	$('form#formCedRevAudCorrSeguimiento #percepcionesList').get(0).selectedIndex = 0;	
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

