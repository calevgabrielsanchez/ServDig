/**
 * JS para el soporte del catalogo de clase.
 */


var idDataTable 	= "#dtFoliosPromocion";
var idDataTablaAPatrones = "#dtAgregaPatrones";
var idDataTrabajadoresA = "#dtAgregaTrabajadoresA";
var idDataTrabajadoresR = "#dtAgregaTrabajadoresR";
var idDataConceptos = "#dtAgregaConceptos";
var idDataConceptosC = "#dtAgregaConceptosC";
var idDataPagos = "#dtAnexoPagos";
var idDataPagosR = "#dtAnexoPagosR";
var idDgNuevo 		= "#dgCorreccionNuevo";
var idDgModificar 	= "#dgCorreccionModificar";
var idDgBorrar 		= "#dgCorreccionBorrar";
var idDgAyuda		= "#dgCorreccionAyuda";
var idDgAnexoPagos 	= "#dgCorreccionAnexoPagos";
var idDgAnexoPagosR	= "#dgCorreccionAnexoPagosR";
var idDgBuscar		= "#dgCorreccionBuscar";
var idDgFoliosPromocion = "#dgFoliosPromocion";
var idDgAgregaPatrones = "#dgAgregaPatrones";
var idDgAgregaTrabajadoresA = "#dgAgregaTrabajadoresA";
var idDgAgregaTrabajadoresR = "#dgAgregaTrabajadoresR";
var idDgAgregaConceptos = "#dgAgregaConceptos";	
var idDgAgregaConceptosC = "#dgAgregaConceptosC";
var idDgAgregaPagos = "#dgAnexoPagos";
var idDgAgregaPagosR = "#dgAnexoPagosR";
var idFolio = "#txtFolio"; 
var idBuscaTable    = "#dtBuscarCorrecciones";
var oDtBuscaCorrreccion;

// Objeto del DataTable
var dtFoliosPromocion;
var dtAgregaPatrones;
var dtAgregaTrabajadoresA;
var dtAgregaTrabajadoresR;
var dtAgregaPagos;
var dtAgregaPagosR;
var dtAgregaConceptos;
var dtAgregaConceptosC;
// Dialogos
var oDgNuevo;
var oDgBorrar;
var oDgModificar;
var oDgAyuda;
var oDtBuscaCorreccion;
var oDgAnexoPagos;
var oDgAnexoPagosR;
var oDgBuscar;
var oDgFoliosPromocion;
var oDgAgregaPatrones;
var oDgAgregaTrabajadoresA;
var oDgAgregaTrabajadoresR;
var oDgAgregaConceptos;
var oDgAgregaConceptosC;
var oDgAgregaPagos;
var oDgAgregaPagosR;
var fnGuardarCorreccion;
var fnGeneraFolioFinal;
var oTxtFolio;

var idDgBuscar		= "#dgCorreccionBuscar";
var oDgBuscar;

/**
 * Iniciamos la configuracion de los componentes visuales de jQuery.
 */



$(document).ready(function() {
	
	$("#btnGuardarEle").click(function(e){
		if(validaPagos.form()){
		var folio = document.getElementById("txtFolio").value;
		var cve_Patron = document.getElementById("regpat").value;
		var FolioSUA = document.getElementById("txtFolioSUA").value;
		var FolioOrdenIngreso = document.getElementById("txtOrdenIngreso").value;
		var NoCredito =  document.getElementById("txtNoCredito").value;
		var fechaPago =  formateaFecha(document.getElementById("fechaPagoAnexoPagos").value);
		var COPPeriodo=  document.getElementById("periodoCOP").value;
		var COPSP =  $('form#formPagos input#txtCOPSP').asNumber();
		var COPAct =$('form#formPagos input#txtCOPAct').asNumber(); 
		var COPRec =$('form#formPagos input#txtCOPRec').asNumber(); 
		var COPMultas =$('form#formPagos input#txtCOPMultas').asNumber();  
		var RCVPeriodo = document.getElementById("periodoRCV").value;
		var RCVSP =$('form#formPagos input#txtRCVSP').asNumber(); 
		var RCVAct =$('form#formPagos input#txtRCVAct').asNumber(); 
		var RCVRec =$('form#formPagos input#txtRCVRec').asNumber(); 
		var RCVMultas =$('form#formPagos input#txtRCVMultas').asNumber(); 
		var sAnexoPagos = '{' +
		 '"folio":"'+folio+'",'+
		 '"cvePatron":"'+cve_Patron+'",'+
		 '"foliosua":"'+FolioSUA+'",'+
		 '"folioordeningreso":"'+FolioOrdenIngreso+'",'+
		 '"nocredito":"'+NoCredito+'",'+
		 '"fechapago":"'+fechaPago+'",'+
		 '"copperiodo":"'+COPPeriodo+'",'+
		 '"copsp":"'+COPSP+'",'+
		 '"copact":"'+COPAct+'",'+
		 '"coprec":"'+COPRec+'",'+
		 '"copmultas":"'+COPMultas+'",'+
		 '"rcvperiodo":"'+RCVPeriodo+'",'+
		 '"rcvsp":"'+RCVSP+'",'+
		 '"rcvact":"'+RCVAct+'",'+
		 '"rcvrec":"'+RCVRec+'",'+
		 '"idProceso":"1",'+
		 '"rcvmultas":"'+RCVMultas+'"}';
		  var anexoPagos = jQuery.parseJSON(sAnexoPagos);
		  
		  //alert (reglaNegocio.id.nombrecontrol);
		  var sumaCOP = COPSP + COPAct + COPRec + COPMultas;
		  var sumaRCV = RCVSP + RCVAct + RCVRec + RCVMultas;	
		
		 // TIPO_ADEUDO = validaSolicitudPago(folio, sumaCOP, sumaRCV, 'autodeterminacion');
			  
			 
//		  if(validaTipoPago(TIPO_ADEUDO)){
			  $.postJSON("correccion/guardaAnexoPagos.do", anexoPagos, function(data) {
				  
				  var oTable = $(idDataPagos).dataTable(); 
				  
				  oTable.fnDraw();
				  $('form#formPagos select#regpat').prop('value', '');
				  $('form#formPagos select#concepto').prop('value','' );
				  $('form#formPagos input#txtFolioSUA').prop('value','' );
				  $('form#formPagos input#txtOrdenIngreso').prop('value','' );
				  $('form#formPagos input#txtNoCredito').prop('value', '');
				  $('form#formPagos input#fechaPagoAnexoPagos').prop('value', '');
				  $('form#formPagos select#periodoCOP').prop('value', '');
				  $('form#formPagos input#txtCOPSP').prop('value', '');
				  $('form#formPagos input#txtCOPAct').prop('value', '');
				  $('form#formPagos input#txtCOPRec').prop('value', '');
				  $('form#formPagos input#txtCOPTotal').prop('value', '');
				  $('form#formPagos input#txtCOPMultas').prop('value', '');
				  $('form#formPagos select#periodoRCV').prop('value', '');
				  $('form#formPagos input#txtRCVSP').prop('value', '');
				  $('form#formPagos input#txtRCVAct').prop('value', '');
				  $('form#formPagos input#txtRCVRec').prop('value', '');
				  $('form#formPagos input#txtRCVTotal').prop('value', '');
				  $('form#formPagos input#txtRCVMultas').prop('value', '');
				  
			  }).error(function(data){ 
					alert("error" + data);
				}).complete(function(){
					//Instrucciones para el 'complete'
				});
			

		 // }
	}
	});
	
	$("#btnGuardarEleR").click(function(e){
		if(validaPagosR.form()){
		var folio = document.getElementById("txtFolio").value;
		var cve_Patron = document.getElementById("regpatR").value;
		var FolioSUA = document.getElementById("txtFolioSUAR").value;
		var FolioOrdenIngreso = document.getElementById("txtOrdenIngresoR").value;
		var NoCredito =  document.getElementById("txtNoCreditoR").value;
		var fechaPago =  formateaFecha(document.getElementById("fechaPagoAnexoPagosR").value);
		var COPPeriodo=  document.getElementById("periodoCOPR").value;
		var COPSP =  $('form#formPagosR input#txtCOPSPR').asNumber();
		var COPAct =$('form#formPagosR input#txtCOPActR').asNumber(); 
		var COPRec =$('form#formPagosR input#txtCOPRecR').asNumber(); 
		var COPMultas =$('form#formPagosR input#txtCOPMultasR').asNumber();  
		var RCVPeriodo = document.getElementById("periodoRCVR").value;
		var RCVSP =$('form#formPagosR input#txtRCVSPR').asNumber(); 
		var RCVAct =$('form#formPagosR input#txtRCVActR').asNumber(); 
		var RCVRec =$('form#formPagosR input#txtRCVRecR').asNumber(); 
		var RCVMultas =$('form#formPagosR input#txtRCVMultasR').asNumber(); 
		var sAnexoPagos = '{' +
		 '"folio":"'+folio+'",'+
		 '"cvePatron":"'+cve_Patron+'",'+
		 '"foliosua":"'+FolioSUA+'",'+
		 '"folioordeningreso":"'+FolioOrdenIngreso+'",'+
		 '"nocredito":"'+NoCredito+'",'+
		 '"fechapago":"'+fechaPago+'",'+
		 '"copperiodo":"'+COPPeriodo+'",'+
		 '"copsp":"'+COPSP+'",'+
		 '"copact":"'+COPAct+'",'+
		 '"coprec":"'+COPRec+'",'+
		 '"copmultas":"'+COPMultas+'",'+
		 '"rcvperiodo":"'+RCVPeriodo+'",'+
		 '"rcvsp":"'+RCVSP+'",'+
		 '"rcvact":"'+RCVAct+'",'+
		 '"rcvrec":"'+RCVRec+'",'+
		 '"idProceso":"2",'+
		 '"rcvmultas":"'+RCVMultas+'"}';
		  var anexoPagos = jQuery.parseJSON(sAnexoPagos);
		  
		  //alert (reglaNegocio.id.nombrecontrol);
		  var sumaCOP = COPSP + COPAct + COPRec + COPMultas;
		  var sumaRCV = RCVSP + RCVAct + RCVRec + RCVMultas;	
		
		 // TIPO_ADEUDO = validaSolicitudPago(folio, sumaCOP, sumaRCV, 'autodeterminacion');
			  
			 
	//	  if(validaTipoPago(TIPO_ADEUDO)){
			  $.postJSON("correccion/guardaAnexoPagos.do", anexoPagos, function(data) {
				  
				  var oTable = $(idDataPagosR).dataTable(); 
				  
				  oTable.fnDraw();
				  $('form#formPagosR select#regpatR').prop('value', '');
				  $('form#formPagosR select#conceptoR').prop('value','' );
				  $('form#formPagosR input#txtFolioSUAR').prop('value','' );
				  $('form#formPagosR input#txtOrdenIngresoR').prop('value','' );
				  $('form#formPagosR input#txtNoCreditoR').prop('value', '');
				  $('form#formPagosR input#fechaPagoAnexoPagosR').prop('value', '');
				  $('form#formPagosR select#periodoCOPR').prop('value', '');
				  $('form#formPagosR input#txtCOPSPR').prop('value', '');
				  $('form#formPagosR input#txtCOPActR').prop('value', '');
				  $('form#formPagosR input#txtCOPRecR').prop('value', '');
				  $('form#formPagosR input#txtCOPTotalR').prop('value', '');
				  $('form#formPagosR input#txtCOPMultasR').prop('value', '');
				  $('form#formPagosR select#periodoRCVR').prop('value', '');
				  $('form#formPagosR input#txtRCVSPR').prop('value', '');
				  $('form#formPagosR input#txtRCVActR').prop('value', '');
				  $('form#formPagosR input#txtRCVRecR').prop('value', '');
				  $('form#formPagosR input#txtRCVTotalR').prop('value', '');
				  $('form#formPagosR input#txtRCVMultasR').prop('value', '');
				  
			  }).error(function(data){ 
					alert("error" + data);
				}).complete(function(){
					//Instrucciones para el 'complete'
				});
			

		//  }
		}	  	
	});
	
	fnGeneraFolioFinal = function(){
		var numFolio = $('input#txtFolio').val();
		var numero = 0;
		var subdelegacion = '01';
		var delegacion = '03'; 
		var fecha = new Date();
		var anio = fecha.getFullYear();
		var tokens = numFolio.split("/");
	  	if(tokens.length==4){
	  		if(tokens[3]==''){
	  		 var sFolio = '{"cveDelegacion": "'+delegacion+'", "cveSubdelegacion": "'+subdelegacion+'", "numAnio" : "'+anio+'", "crcTipoCorr":{"cveTipocorr": "6" }}';
	  		  var folio = jQuery.parseJSON(sFolio);
	  		  //alert (reglaNegocio.id.nombrecontrol);
	  		bloquear();
	  		  $.postJSON("correccion/obtenerFolio.do", folio, function(data) {
	  			numFolio = numFolio + data[0].numNumero;
	  			guardarCorreccion(numFolio);
	  			  $('input#txtFolio').prop("value", numFolio);
	  			return data[0].numNumero;
	  			}).error(function(data){ 
	  				alert("error" + data);
	  			}).complete(function(){
	  				desbloquear();
	  			});
	  	}
	  		}
		
		
	}

	 /**
	 * Inicializacion del data table
	 */
 oDtBuscaCorrreccion = $(idBuscaTable).dataTable({
			"bJQueryUI" : true,
			"bPaginate": true,
			"bAutoWidth" : true,
			"bServerSide" :	true,
			"aoColumnDefs": [
			              {  fnRender :function(oObj){
			  				var retVal = '<input type="radio" value="' +
							oObj.aData['folio'] +'" id="radioTable" class="radioClase" name="radioCorr" onclick=""/> ';
							return retVal;
			              	}, "aTargets": [0], "mDataProp" :"folioa" },
				              {  "aTargets": [1], "mDataProp" :"cgcCatTipo.descripcion" },
				              {  "aTargets": [2], "mDataProp" :"cgcCatOrigen.descOrigen" },
				              {  "aTargets": [3], "mDataProp" :"folio" },
				              {  "aTargets": [4], "mDataProp" :"cvePatron" },
				              {  "aTargets": [5], "mDataProp" :"nombre" },
				              {  "aTargets": [6], "mDataProp" :"del" },
				              {  "aTargets": [7], "mDataProp" :"al" }
			          ],
			 "sAjaxSource" : 'correccion/paginarCorrecciones.do',
			 "fnServerData" : function(sSource, aoData, fnCallback) {
				 var sSearch = $('form#formCorre select#cgcCatTipo\\.idTipo').val() + '|';
				 if($("form#formCorreccionBuscar select#findClasificacion").val()!=null && $("form#formCorreccionBuscar select#findClasificacion").val()!= '-1'){
					 sSearch =sSearch + 'findClasificacion:' + $('form#formCorreccionBuscar select#findClasificacion').val() + '|';
				 }
				 if($("form#formCorreccionBuscar select#findFuente").val()!=null && $("form#formCorreccionBuscar select#findFuente").val()!= '-1'){
					 sSearch =sSearch + 'findFuente:' +  $('form#formCorreccionBuscar select#findFuente').val() + '|';
				 }
				 if($("form#formCorreccionBuscar input#findFolio").val()!=null && $("form#formCorreccionBuscar input#findFolio").val()!= ''){
					 sSearch =sSearch + 'findFolio:' + $('form#formCorreccionBuscar input#findFolio').val() + '|';
				 }
				 if($("form#formCorreccionBuscar input#findRP").val()!=null && $("form#formCorreccionBuscar input#findRP").val()!= ''){
					 sSearch =sSearch + 'findRP:' + $('form#formCorreccionBuscar input#findRP').val() + '|';
				 }
				 if($("form#formCorreccionBuscar input#findNombre").val()!=null && $("form#formCorreccionBuscar input#findNombre").val()!= ''){
					 sSearch =sSearch + 'findNombre:' + $('form#formCorreccionBuscar input#findNombre').val() + '|';
				 }
				 if($("form#formCorreccionBuscar input#findAfil").val()!=null && $("form#formCorreccionBuscar input#findAfil").val()!= ''){
					 sSearch =sSearch + 'findAfil:' + $('form#formCorreccionBuscar input#findAfil').val() ;
				 }
				 aoData.push({
					
						"name" : "sSearch",
						"value" : sSearch
					});
					var wrapper = new Object();
					wrapper.aoData = aoData;
					$.postJSON(sSource, wrapper, function(data) {
						fnCallback
					});
				}
			});

	
	fnGuardarCorreccion = function(){
		if(validaCapturaC.form()){
		var rp =$('input#txtGRP').val();
		var idTipo = $('select#cgcCatTipo\\.idTipo').val(); 
		var idOrigen =$('select#cbxGOrigen').val(); 
		var idCriterioSel = $('select#cbxGCriterioSeleccion').val();
		var cvePatron = $('input#txtGRP').val();
		var nombre = $('input#txtGNombre').val();
		var afil15 = $('input#txtAFIL15').val();
		var periododel = formateaFecha($('input#dtpGPeriodoDel').val())
		var periodoal = formateaFecha($('input#dtpGPeriodoAl').val());
		var aoi = formateaFecha($('input#dtpAOI').val());
		var anoOficionOI = $('input#txtANoOficioOI').val();
		var aoin = formateaFecha($('input#dtpAOIN').val());
		var asp = formateaFecha($('input#dtpASP').val());
		var asr = formateaFecha($('input#dtpASR').val());
		var asa = formateaFecha($('input#dtpASA').val());
		var ap =  formateaFecha($('input#dtpAP').val());
		var aasr =  formateaFecha($('input#dtpAASR').val());
		var aacp = formateaFecha($('input#dtpAACP').val());
		var idMotivoRechazo = $('select#CgcCatMotivoRechazo\\.idMotivoRechazo').val();
		var aapp = formateaFecha($('input#dtpAAPP').val());
		var anoConvenio = $('input#txtANoConvenio').val();
		var anoParcialidades = $('input#txtANoParcialidades').val();
		var acopconvsp = $('input#txtACOPConvSP').asNumber();
		var arcvconvsp = $('input#txtARCVConvSP').asNumber();
		var rcr = formateaFecha($('input#dtpRCR').val());
		var rporccr = $('input#txtRPorcCR').val();
		var rccpr = formateaFecha($('input#dtpRCCPR').val());
		var rrde = formateaFecha($('input#dtpRRDE').val());
		var rnooficiorde = $('input#txtRNoOficioRDE').val();
		var rrdn = formateaFecha($('input#dtpRRDN').val());
		var rod = formateaFecha($('input#dtpROD').val());
		var rnooficiood = $('input#txtRNoOficioOD').val();
		var rnod = formateaFecha($('input#dtpRNOD').val());
		var rvda = formateaFecha($('input#dtpRVDA').val());
		var rvpp= formateaFecha($('input#dtpRVPP').val());
		var rvtc = formateaFecha($('input#dtpRVTC').val());
		var rdc = formateaFecha($('input#dtpRDC').val());
		var rnoconvenio = $('input#txtRNoConvenio').val();
		var rnoparcialidades = $('input#txtRNoParcialidades').val();
		var rcopconvsp = $('input#txtRCOPConvSP').asNumber();
		var rrcvconvsp = $('input#txtRRCVConvSP').asNumber();
		var csd = '';
		var cpai = '';
		var cds = '';
		var cc = '';
		var idMoticocancelacion = $('select#CgcCatMotivoCancelacion\\.idMotivocancelacion').val();
		var cpresentopagos = '';
		var cnooficio = '';
		var idStatus = '';
		var idAuditor = '';
		var folio =  $('input#txtFolio').val();
		var sCorreccion = '{' +
					'"folio": "'+folio+'",'+
					 '"cgcCatTipo": {"idTipo": "'+idTipo+'"},'+
					 '"cgcCatOrigen" : {"idOrigen":"'+idOrigen+'"},'+
					 '"cgtCatCriterioSeleccion": {"idCriterioseleccion":"'+idCriterioSel+'"},'+
					'"cvePatron" : "'+cvePatron+'",'+
					'"nombre" : "'+nombre+'",'+
					'"afil15" : "'+afil15+'",'+
					'"periododel" : "'+periododel+'",'+
					'"periodoal" : "'+periodoal+'",'+
					'"afil15" : "'+afil15+'",'+
					'"aoi" : "'+aoi+'",'+
					'"anooficiooi" : "'+anoOficionOI+'",'+
					'"aoin" : "'+aoin+'",'+
					'"asp" : "'+asp+'",'+
					'"asr" : "'+asr+'",'+
					'"asa" : "'+asa+'",'+
					'"ap" : "'+ap+'",'+
					'"aasr" : "'+aasr+'",'+
					'"aacp" : "'+aacp+'",'+
					'"aapp" : "'+aapp+'",'+
					'"anoconvenio" : "'+anoConvenio+'",'+
					'"anoparcialidades" : "'+anoParcialidades+'",'+
					'"acopconvsp" : "'+acopconvsp+'",'+
					'"arcvconvsp" : "'+arcvconvsp+'",'+
					'"rcr" : "'+rcr+'",'+
					'"rporccr" : "'+rporccr+'",'+
					'"rccpr" : "'+rccpr+'",'+
					'"rrde" : "'+rrde+'",'+
					'"rnooficiorde" : "'+rnooficiorde+'",'+
					'"rrdn" : "'+rrdn+'",'+
					'"rod" : "'+rod+'",'+
					'"rnooficiood" : "'+rnooficiood+'",'+
					'"rnod" : "'+rnod+'",'+
					'"rvda" : "'+rvda+'",'+
					'"rvpp" : "'+rvpp+'",'+
					'"rvtc" : "'+rvtc+'",'+
					'"rdc" : "'+rdc+'",'+
					'"rnoconvenio" : "'+rnoconvenio+'",'+
					'"rnoparcialidades" : "'+rnoparcialidades+'",'+
					'"rcopconvsp" : "'+rcopconvsp+'",'+
					'"rrcvconvsp" : "'+rrcvconvsp+'",'+
					'"csd" : "'+csd+'",'+
					'"cpai" : "'+cpai+'",'+
					'"cds" : "'+cds+'",'+
					'"cc" : "'+cc+'"}';
				
		var correccion = jQuery.parseJSON(sCorreccion);
		  $.postJSON("correccion/guardaCorreccion.do", correccion, function(data) {
				  document.getElementById('guardadoHidden').value = 1;
				  validaGuardado();
				  var sRP = ""
					   document.getElementById('txtFolio').value = data.folio;;
					  var rp  = document.getElementById('registroPatronal').value;
					  if(rp=='')
					  	rp = document.getElementById('txtGRP').value;
					  
				  var sRP = '{"id": {"folio" : "'+data.folio+'" , "rp" : "'+rp+'"}}';
				  var RegPat = jQuery.parseJSON(sRP);
				  $.postJSON("correccion/guardaRP.do", RegPat, function(dataa) {
					  document.getElementById('txtGNoRPA').value = 1;
					
					  
				}).error(function(dataa){ 
					alert("error" + dataa);
				}).complete(function(){
					
				});
			}).error(function(data){ 
				alert("error" + data);
			}).complete(function(){
				alert('El registro se ha guardado con exito');
			});
		  
		  $.postJSON("correccion/consultaAuditores.do", '', function(auditores) {
			  var options = "";
				 if(auditores!=null)
				   for (var i = 0; i < auditores.length; i++) {
					 options += "<option value='"+ auditores[i][0] +"'>"+ auditores[i][1] +"</option>";		     
			       }
				 $('select#cbxAuditor').html(options);
				 $('select#cbxAuditor').prop("disabled", false);
				 $('select#cbxAuditor').addClass("red");
		}).error(function(auditores){ 
			alert("error" + auditores);
		}).complete(function(){
			
		});
		  
		  
		  
		}
	}
	
	$("#cmdGuardar").click(function(e){
		if(validaCapturaC.form()){
			var folio =  $('input#txtFolio').val();
			if (document.getElementById('txtACOPConvSP').disabled == false && document.getElementById('txtARCVConvSP').disabled == false){
				if(document.getElementById('txtACOPConvSP').value == ''){
					alert('Debe introducir el monto de la SP Determinada de COP');
					return false;
					
				}else if(document.getElementById('txtARCVConvSP').value == ''){
					alert('Debe introducir el monto de la SP Determinada de RCV');
					return false;
				}
				else if((document.getElementById('txtACOPPagTotal').value == '' || document.getElementById('txtACOPPagTotal').value == '$0.00' ) && (document.getElementById('txtARCVPagTotal').value == '' || document.getElementById('txtARCVPagTotal').value == '$0.00')){
					alert('Debe Registrar los Pagos de la Fase de Autodeterminacion');
					return false;
				}else{
					var idSituacionCO =  1;
					var sConceptoOmitido = '{"id": {"idProceso" : "2" , "cgcCatSituacionCO" : {"idSituacionco" :"'+idSituacionCO+'"}, "cgcCatConceptoOmitido" : {"idConceptoomitido" : "1"} , "folio": "'+folio+'"}}'; 
					var conceptoOmitido = jQuery.parseJSON(sConceptoOmitido);
					  $.postJSON("correccion/consultaConceptosFolio.do", conceptoOmitido, function(dataC) {
						  		if(dataC==null){
						  			alert('Debe Registrar los Conceptos Pagados/Omitidos');
						  			return false;
						  		}
						  			
						  		
					  }).error(function(dataa){ 
							(dataa);
						}).complete(function(){
							
						});
					
				}
			
			}	
			
			
			var rp =$('input#txtGRP').val();
			var idTipo = $('select#cgcCatTipo\\.idTipo').val(); 
			var idOrigen =$('select#cbxGOrigen').val(); 
			var idCriterioSel = $('select#cbxGCriterioSeleccion').val();
			var cvePatron = $('input#txtGRP').val();
			var nombre = $('input#txtGNombre').val();
			var afil15 = $('input#txtAFIL15').val();
			var periododel = formateaFecha($('input#dtpGPeriodoDel').val());
			var periodoal = formateaFecha($('input#dtpGPeriodoAl').val());
			var aoi = formateaFecha($('input#dtpAOI').val());
			var anoOficionOI = $('input#txtANoOficioOI').val();
			var aoin = formateaFecha($('input#dtpAOIN').val());
			var asp = formateaFecha($('input#dtpASP').val());
			var ap =  formateaFecha($('input#dtpAP').val());
			var asa = formateaFecha($('input#dtpASA').val());
			var asr = formateaFecha($('input#dtpASR').val());
			var idMotivoRechazo = $('select#CgcCatMotivoRechazo\\.idMotivoRechazo').val();
			var aapp = formateaFecha($('input#dtpAAPP').val());
			var aacp = formateaFecha($('input#dtpAACP').val());
			var aasr =  formateaFecha($('input#dtpAASR').val());
			var anoConvenio = $('input#txtANoConvenio').val();
			var anoParcialidades = $('input#txtANoParcialidades').val();
			var acopconvsp = $('input#txtACOPConvSP').asNumber();
			var arcvconvsp = $('input#txtARCVConvSP').asNumber();
			var rcr = formateaFecha($('input#dtpRCR').val());
			var rporccr = $('input#txtRPorcCR').val();
			var rccpr = formateaFecha($('input#dtpRCCPR').val());
			var rrde = formateaFecha($('input#dtpRRDE').val());
			var rnooficiorde = $('input#txtRNoOficioRDE').val();
			var rrdn = formateaFecha($('input#dtpRRDN').val());
			var rod = formateaFecha($('input#dtpROD').val());
			var rnooficiood = $('input#txtRNoOficioOD').val();
			var rnod = formateaFecha($('input#dtpRNOD').val());
			var rvda = formateaFecha($('input#dtpRVDA').val());
			var rvpp= formateaFecha($('input#dtpRVPP').val());
			var rvtc = formateaFecha($('input#dtpRVTC').val());
			var rdc = formateaFecha($('input#dtpRDC').val());
			var rnoconvenio = $('input#txtRNoConvenio').val();
			var rnoparcialidades = $('input#txtRNoParcialidades').val();
			var rcopconvsp = $('input#txtRCOPConvSP').asNumber();
			var rrcvconvsp = $('input#txtRRCVConvSP').asNumber();
			var csd = '';
			var cpai = '';
			var cds = '';
			var cc = '';
			var idMoticocancelacion = $('select#CgcCatMotivoCancelacion\\.idMotivocancelacion').val();
			var cpresentopagos = '';
			var cnooficio = '';
			var idStatus = '';
			var idAuditor = '';
			
			var sCorreccion = '{' +
						'"folio": "'+folio+'",'+
						 '"cgcCatTipo": {"idTipo": "'+idTipo+'"},'+
						 '"cgcCatOrigen" : {"idOrigen":"'+idOrigen+'"},'+
						 '"cgtCatCriterioSeleccion": {"idCriterioseleccion":"'+idCriterioSel+'"},'+
						'"cvePatron" : "'+cvePatron+'",'+
						'"nombre" : "'+nombre+'",'+
						'"afil15" : "'+afil15+'",'+
						'"periododel" : "'+periododel+'",'+
						'"periodoal" : "'+periodoal+'",'+
						'"afil15" : "'+afil15+'",'+
						'"aoi" : "'+aoi+'",'+
						'"anooficiooi" : "'+anoOficionOI+'",'+
						'"aoin" : "'+aoin+'",'+
						'"asp" : "'+asp+'",'+
						'"ap" : "'+ap+'",'+
						'"asa" : "'+asa+'",'+
						'"asr" : "'+asr+'",'+
						'"aasr" : "'+aasr+'",'+
						'"aacp" : "'+aacp+'",'+
						'"aapp" : "'+aapp+'",'+
						'"anoconvenio" : "'+anoConvenio+'",'+
						'"anoparcialidades" : "'+anoParcialidades+'",'+
						'"acopconvsp" : "'+acopconvsp+'",'+
						'"arcvconvsp" : "'+arcvconvsp+'",'+
						'"rcr" : "'+rcr+'",'+
						'"rporccr" : "'+rporccr+'",'+
						'"rccpr" : "'+rccpr+'",'+
						'"rrde" : "'+rrde+'",'+
						'"rnooficiorde" : "'+rnooficiorde+'",'+
						'"rrdn" : "'+rrdn+'",'+
						'"rod" : "'+rod+'",'+
						'"rnooficiood" : "'+rnooficiood+'",'+
						'"rnod" : "'+rnod+'",'+
						'"rvda" : "'+rvda+'",'+
						'"rvpp" : "'+rvpp+'",'+
						'"rvtc" : "'+rvtc+'",'+
						'"rdc" : "'+rdc+'",'+
						'"rnoconvenio" : "'+rnoconvenio+'",'+
						'"rnoparcialidades" : "'+rnoparcialidades+'",'+
						'"rcopconvsp" : "'+rcopconvsp+'",'+
						'"rrcvconvsp" : "'+rrcvconvsp+'",'+
						'"csd" : "'+csd+'",'+
						'"cpai" : "'+cpai+'",'+
						'"cds" : "'+cds+'",'+
						'"cc" : "'+cc+'"}';
					
			var correccion = jQuery.parseJSON(sCorreccion);
			  $.postJSON("correccion/guardaCorreccion.do", correccion, function(data) {
					  document.getElementById('guardadoHidden').value = 1;
					  validaGuardado();
					  var sRP = ""
						   document.getElementById('txtFolio').value = data.folio;;
						  var rp  = document.getElementById('registroPatronal').value;
						  if(rp=='')
						  	rp = document.getElementById('txtGRP').value;
						  
					  var sRP = '{"id": {"folio" : "'+data.folio+'" , "rp" : "'+rp+'"}}';
					  var RegPat = jQuery.parseJSON(sRP);
					  $.postJSON("correccion/guardaRP.do", RegPat, function(dataa) {
						  document.getElementById('txtGNoRPA').value = 1;
						
						  
					}).error(function(dataa){ 
						(dataa);
					}).complete(function(){
						
					});
				}).error(function(data){ 
					
				}).complete(function(){
					alert('El registro se ha guardado con \u00E9xito');
				});
			}
	});
	dtFoliosPromocion = $(idDataTable).dataTable({
			"bJQueryUI" : true,
			"bPaginate": true,
			"bAutoWidth" : true,
			"bServerSide" :	true,
			"aoColumnDefs": [
			              {  fnRender :function(oObj){
			  				var retVal = '<input type="radio" value="' +
							oObj.aData['folio'] +'" id="radioTable" class="radioClase" name="radioTable" onclick=""/> ';
							return retVal;
			              	}, "aTargets": [0], "mDataProp" :"folioa" },
			              	{  "aTargets": [1], "mDataProp" :"cgcCatTipo.descripcion" },
			              {  "aTargets": [2], "mDataProp" :"cgcCatOrigen.descOrigen" },
			              {  "aTargets": [3], "mDataProp" :"folio" },
			              {  "aTargets": [4], "mDataProp" :"cvePatron" },
			              {  "aTargets": [5], "mDataProp" :"nombre" },
			              {  "aTargets": [6], "mDataProp" :"afil15" },
			              {  "aTargets": [7], "mDataProp" :"sp" },
			              {  "aTargets": [8], "mDataProp" :"oi" },
			              {  "aTargets": [9], "mDataProp" :"pr" }
			          ],
			 "sAjaxSource" : 'correccion/paginar.do',
			 "fnServerData" : function(sSource, aoData, fnCallback) {
				 var sSearch = $('form#formCorre select#cgcCatTipo\\.idTipo').val() + '|';
				 if($("form#formCorrePromo select#findClasificacion").val()!=null && $("form#formCorrePromo select#findClasificacion").val()!= '-1'){
					 sSearch =sSearch + 'findClasificacion:' + $('form#formCorrePromo select#findClasificacion').val() + '|';
				 }
				 if($("form#formCorrePromo select#findFuente").val()!=null && $("form#formCorrePromo select#findFuente").val()!= '-1'){
					 sSearch =sSearch + 'findFuente:' +  $('form#formCorrePromo select#findFuente').val() + '|';
				 }
				 if($("form#formCorrePromo input#findFolio").val()!=null && $("form#formCorrePromo input#findFolio").val()!= ''){
					 sSearch =sSearch + 'findFolio:' + $('form#formCorrePromo input#findFolio').val() + '|';
				 }
				 if($("form#formCorrePromo input#findRP").val()!=null && $("form#formCorrePromo input#findRP").val()!= ''){
					 sSearch =sSearch + 'findRP:' + $('form#formCorrePromo input#findRP').val() + '|';
				 }
				 if($("form#formCorrePromo input#findNombre").val()!=null && $("form#formCorrePromo input#findNombre").val()!= ''){
					 sSearch =sSearch + 'findNombre:' + $('form#formCorrePromo input#findNombre').val() + '|';
				 }
				 if($("form#formCorrePromo input#findAfil").val()!=null && $("form#formCorrePromo input#findAfil").val()!= ''){
					 sSearch =sSearch + 'findAfil:' + $('form#formCorrePromo input#findAfil').val() ;
				 }
				 aoData.push({
					
						"name" : "sSearch",
						"value" : sSearch
					});
					var wrapper = new Object();
					wrapper.aoData = aoData;
					$.postJSON(sSource, wrapper, function(data) {
						fnCallback
					});
				}
			});
	
	dtAgregaPatrones = $(idDataTablaAPatrones).dataTable({
		"bJQueryUI" : true,
		"bPaginate": true,
		"bAutoWidth" : true,
		"bServerSide" :	true,
		"aoColumnDefs": [
		              {  fnRender :function(oObj){
		  				var retVal = '<input type="radio" value="' +
						oObj.aData['id.folio'] +'" id="radioTable" class="radioClase" name="radio" onclick=""/> ';
						return retVal;
		              	}, "aTargets": [0], "mDataProp" :"id.folio" },
		               {  "aTargets": [1], "mDataProp" :"id.rp" }
		             
		              
		              
		          ],
		 "sAjaxSource" : 'correccion/paginarPatrones.do',
		 "fnServerData" : function(sSource, aoData, fnCallback) {
			 aoData.push({
					"name" : "sSearch",
					"value" : $('#txtFolio').val()
				});
				var wrapper = new Object();
				wrapper.aoData = aoData;
				$.postJSON(sSource, wrapper, function(data) {
					fnCallback
				});
			}
			
		});

	
	dtAgregaTrabajadoresA = $(idDataTrabajadoresA).dataTable({
		"bJQueryUI" : true,
		"bPaginate": true,
		"bAutoWidth" : true,
		"bServerSide" :	true,
		 "fnServerData" : function(sSource, aoData, fnCallback) {
			 aoData.push({
					"name" : "sSearch",
					"value" : $('#txtFolio').val()
				});
				var wrapper = new Object();
				wrapper.aoData = aoData;
				$.postJSON(sSource, wrapper, function(data) {
					fnCallback
				});
			},
		"aoColumnDefs": [
			              {  fnRender :function(oObj){
			            	var retVal = '<input type="radio" value="' +
			  				oObj.aData['regPats'] +'" id="radioTable" class="radioClase" name="radio" /> ';
							return retVal;
			              	}, "aTargets":[0], "mDataProp" :"id.rp" },
			               {  "aTargets": [1], "mDataProp" :"regPats" },
			               {  "aTargets": [2], "mDataProp" :"atrabrevisados" },
			               {  "aTargets": [3], "mDataProp" :"atrabomisos" },
			               {  "aTargets": [4], "mDataProp" :"atrabsubdeclarados" },
			               {fnRender :function(oObj){
				  				var retVal = oObj.aData['atrabomisos'] + oObj.aData['atrabsubdeclarados'] 
								return retVal; }, "aTargets": [5] }
			             
			              
			              
			          ],
		 "sAjaxSource" : 'correccion/paginarPatrones.do'
	
			
		});

	dtAgregaTrabajadoresR = $(idDataTrabajadoresR).dataTable({
		"bJQueryUI" : true,
		"bPaginate": true,
		"bAutoWidth" : true,
		"bServerSide" :	true,
		 "fnServerData" : function(sSource, aoData, fnCallback) {
			 aoData.push({
					"name" : "sSearch",
					"value" : $('#txtFolio').val()
				});
				var wrapper = new Object();
				wrapper.aoData = aoData;
				$.postJSON(sSource, wrapper, function(data) {
					fnCallback
				});
			},
		"aoColumnDefs": [
			              {  fnRender :function(oObj){
			            	var retVal = '<input type="radio" value="' +
			  				oObj.aData['regPats'] +'" id="radioTable" class="radioClase" name="radio" /> ';
							return retVal;
			              	}, "aTargets":[0], "mDataProp" :"id.rp" },
			               {  "aTargets": [1], "mDataProp" :"regPats" },
			               {  "aTargets": [2], "mDataProp" :"rtrabrevisados" },
			               {  "aTargets": [3], "mDataProp" :"rtrabomisos" },
			               {  "aTargets": [4], "mDataProp" :"rtrabsubdeclarados" },
			               {fnRender :function(oObj){
				  				var retVal = oObj.aData['rtrabrevisados'] + oObj.aData['rtrabomisos'] + oObj.aData['rtrabsubdeclarados'] 
								return retVal; }, "aTargets": [5] }
			             
			              
			              
			          ],
		 "sAjaxSource" : 'correccion/paginarPatrones.do'
	
			
		});
	
	
	
	dtAgregaConceptos = $(idDataConceptos).dataTable({
		"bJQueryUI" : true,
		"bPaginate": true,
		"bAutoWidth" : true,
		"bServerSide" :	true,
		"aoColumnDefs": [
		              {  fnRender :function(oObj){
		  				var retVal = '<input type="radio" value="' +
						oObj.aData['id.folio'] +'" id="radioTable" class="radioClase" name="radio" onclick=""/> ';
						return retVal;
		              	}, "aTargets": [0], "mDataProp" :"id.folio" },
		               {  "aTargets": [1], "mDataProp" :"id.cgcCatConceptoOmitido.descConceptoomitido" },
		               {
		            	   fnRender :function(oObj){
				  				var retVal = '<input type="checkbox" checked="checked" disabled="disabled"/> ';
								return retVal;
				              	}, "aTargets": [2], "mDataProp" :"id.cgcCatConceptoOmitido.idConceptoomitido" }
		             
		              
		              
		          ],
		 "sAjaxSource" : 'correccion/paginarConceptos.do',
		 "fnServerData" : function(sSource, aoData, fnCallback) {
			 aoData.push({
					"name" : "sSearch",
					"value" : $('#txtFolio').val()
				});
				var wrapper = new Object();
				wrapper.aoData = aoData;
				$.postJSON(sSource, wrapper, function(data) {
					fnCallback
				});
			}
		});
	
	dtAgregaConceptosC = $(idDataConceptosC).dataTable({
		"bJQueryUI" : true,
		"bPaginate": true,
		"bAutoWidth" : true,
		"bServerSide" :	true,
		"bUseRendered": false,
		"aoColumnDefs": [
		              {  fnRender :function(oObj){
		  				var retVal = '<input type="radio" value="' +
						oObj.aData['folio'] +'" id="radioTable" class="radioClase" name="radio" onclick=""/> ';
						return retVal;
		              	}, "aTargets": [0], "mDataProp" :"folio" },
		               {  "aTargets": [1], "mDataProp" :"cgcCatConceptoOmitido.descConceptoomitido" },
		               {
		            	  fnRender :function(oObj){
		            		  var retVal = ''	
		            		  if(oObj.aData.pagoRecibido!= null && oObj.aData.pagoRecibido.idSituacionco == 1){
		            		  		retVal = '<input type="checkbox" checked="checked" disabled="disabled"/> '; 
		            		  	}
		            		  else{
		            			  retVal = '<input type="checkbox" disabled="disabled"/> '; 
		            		  }
				  				 
								return retVal;
				       	}, "aTargets": [2], "mDataProp" :"folioa" }
				       
		               ,
		               {
		            	  fnRender :function(oObj){
		            		  var retVal = ''	
			            		  if(oObj.aData.aclarado.idSituacionco == 2){
			            		  		retVal = '<input type="checkbox" checked="checked" disabled="disabled"/> '; 
			            		  	}else if(oObj.aData.aclarado.idSituacionco == null || oObj.aData.aclarado.idSituacionco == 3){
			            		  		retVal = '<input type="checkbox" disabled="disabled"/> '; 
			            		  	}
		            		  return retVal;	
			            		  				       	
		            		  }
		               , "aTargets": [3], "mDataProp" :"foliob" }
		             
		              
		              
		          ],
		 "sAjaxSource" : 'correccion/paginarConceptosC.do',
		 "fnServerData" : function(sSource, aoData, fnCallback) {
			 aoData.push({
					"name" : "sSearch",
					"value" : $('#txtFolio').val()
				});
				var wrapper = new Object();
				wrapper.aoData = aoData;
				$.postJSON(sSource, wrapper, function(data) {
					fnCallback
				});
			}
		});
	
	dtAgregaPagos = $(idDataPagos).dataTable({
		"bJQueryUI" : true,
		"bPaginate": true,
		"bAutoWidth" : true,
		"bServerSide" :	true,
		"aoColumnDefs": [
		              {  fnRender :function(oObj){
		  				var retVal = '<input type="radio" value="' +
						oObj.aData['idPago'] +'" id="radioTable" class="radioClase" name="radio" onclick=""/> ';
						return retVal;
		              	}, "aTargets": [0], "mDataProp" :"idPago" },
		              {  "aTargets": [1], "mDataProp" :"cvePatron" },
		              {  "aTargets": [2], "mDataProp" :"foliosua" },
		              {  "aTargets": [3], "mDataProp" :"folioordeningreso" },
		              {  "aTargets": [4], "mDataProp" :"nocredito" },
		              {  "aTargets": [5], "mDataProp" :"fechapago" },
		              {  "aTargets": [6], "mDataProp" :"copperiodo" },
		              {  "aTargets": [7], "mDataProp" :"copsp" },
		              {  "aTargets": [8], "mDataProp" :"copact" },
		              {  "aTargets": [9], "mDataProp" :"coprec" },
		              {fnRender :function(oObj){
			  				var retVal = oObj.aData['copact'] + oObj.aData['copsp'] + oObj.aData['coprec'] 
							return retVal; }, "aTargets": [10] },
		              {  "aTargets": [11], "mDataProp" :"copmultas" },
		              {  "aTargets": [12], "mDataProp" :"rcvperiodo" },
		              {  "aTargets": [13], "mDataProp" :"rcvsp" },
		              {  "aTargets": [14], "mDataProp" :"rcvact" },
		              {  "aTargets": [15], "mDataProp" :"rcvrec" },
		              {fnRender :function(oObj){
			  				var retVal = oObj.aData['rcvact'] + oObj.aData['rcvsp'] + oObj.aData['rcvrec'] 
							return retVal; }, "aTargets": [16] },
		              {  "aTargets": [17], "mDataProp" :"rcvmultas" }
		              
		              
		          ],
		 "sAjaxSource" : 'correccion/paginarAnexoPagosA.do',
		 "fnServerData" : function(sSource, aoData, fnCallback) {
			 aoData.push({
					"name" : "sSearch",
					"value" : $('#txtFolio').val() 
				});
				var wrapper = new Object();
				wrapper.aoData = aoData;
				$.postJSON(sSource, wrapper, function(data) {
					fnCallback
				});
			}
		});

	dtAgregaPagosR = $(idDataPagosR).dataTable({
		"bJQueryUI" : true,
		"bPaginate": true,
		"bAutoWidth" : true,
		"bServerSide" :	true,
		"aoColumnDefs": [
		              {  fnRender :function(oObj){
		  				var retVal = '<input type="radio" value="' +
						oObj.aData['idPago'] +'" id="radioTable" class="radioClase" name="radioR" onclick=""/> ';
						return retVal;
		              	}, "aTargets": [0], "mDataProp" :"idPago" },
		              {  "aTargets": [1], "mDataProp" :"cvePatron" },
		              {  "aTargets": [2], "mDataProp" :"foliosua" },
		              {  "aTargets": [3], "mDataProp" :"folioordeningreso" },
		              {  "aTargets": [4], "mDataProp" :"nocredito" },
		              {  "aTargets": [5], "mDataProp" :"fechapago" },
		              {  "aTargets": [6], "mDataProp" :"copperiodo" },
		              {  "aTargets": [7], "mDataProp" :"copsp" },
		              {  "aTargets": [8], "mDataProp" :"copact" },
		              {  "aTargets": [9], "mDataProp" :"coprec" },
		              {fnRender :function(oObj){
			  				var retVal = oObj.aData['copact'] + oObj.aData['copsp'] + oObj.aData['coprec'] 
							return retVal; }, "aTargets": [10] },
		              {  "aTargets": [11], "mDataProp" :"copmultas" },
		              {  "aTargets": [12], "mDataProp" :"rcvperiodo" },
		              {  "aTargets": [13], "mDataProp" :"rcvsp" },
		              {  "aTargets": [14], "mDataProp" :"rcvact" },
		              {  "aTargets": [15], "mDataProp" :"rcvrec" },
		              {fnRender :function(oObj){
			  				var retVal = oObj.aData['rcvact'] + oObj.aData['rcvsp'] + oObj.aData['rcvrec'] 
							return retVal; }, "aTargets": [16] },
		              {  "aTargets": [17], "mDataProp" :"rcvmultas" }
		              
		              
		          ],
		 "sAjaxSource" : 'correccion/paginarAnexoPagosR.do',
		 "fnServerData" : function(sSource, aoData, fnCallback) {
			 aoData.push({
					"name" : "sSearch",
					"value" : $('#txtFolio').val()
				});
				var wrapper = new Object();
				wrapper.aoData = aoData;
				$.postJSON(sSource, wrapper, function(data) {
					fnCallback
				});
			}
		});

	 // Fecha de Inicio y Termino
	 $( "#dtpAOI, #dtpAOIN, #dtpASP, #dtpAP, #dtpASA, #dtpASR , #dtpAAPP, #dtpAACP, #dtpAASR, #dtpGPeriodoDel , #dtpGPeriodoAl" ).datepicker( { dateFormat: 'dd/mm/yy' });
	 $( "#dtpCSD1, #dtpCDS1, #dtpCPAI1, #dtpCC, #dtpRCR, #dtpRCCPR, #dtpRRDE, #dtpRRDN, #dtpROD, #dtpRNOD, #dtpRDC, #dtpRVPP, #dtpRVTC, #dtpRVDA, #fechaPagoAnexoPagos, #fechaPagoAnexoPagosR " ).datepicker( { dateFormat: 'dd/mm/yy' });
	
	$(".tab_content").hide();
	$("ul.tabs li:first").addClass("active").show();
	$(".tab_content:first").show();

	$("ul.tabs li").click(function()
       {
		$("ul.tabs li").removeClass("active");
		$(this).addClass("active");
		$(".tab_content").hide();

		var activeTab = $(this).find("a").attr("href");
		$(activeTab).fadeIn();
		return false;
	});
	$('input#buscaOrigenPromocion').hide(10);	
	
	
	
	// Dialog de Anexos Pagos			
	 oDgFoliosPromocion = $(idDgFoliosPromocion).dialog({
		autoOpen: false,
		modal:true,
		resizable:true,
		height: 600,
		width: 1100,
		open:function(event, ui)
        {
			var oTable = $(idDataTable).dataTable(); 
			  
			  oTable.fnDraw();
        }
	        
	 });
	 
	// Dialog de Anexos Pagos			
	 oDgAgregaPatrones = $(idDgAgregaPatrones).dialog({
		autoOpen: false,
		modal:true,
		resizable:true,
		height: 200,
		width: 500,
		open:function(event, ui)
        {
			var oTable = $(idDataTablaAPatrones).dataTable(); 
			  
			  oTable.fnDraw();
        },
        close: function(event, ui)
        {
		 var folio = document.getElementById('txtFolio').value;
		 var sRP = '{"id": {"folio" : "'+folio+'"}}';
		  var RegPat = jQuery.parseJSON(sRP);
		 $.postJSON("correccion/conteoPatrones.do", RegPat, function(data) {
			 document.getElementById('txtGNoRPA').value = data;
			
		}).error(function(data){ 
			
		}).complete(function(){
			//Instrucciones para el 'complete'
		});
        }
		
	 
		
	});
	 
	 
	// Dialog de Anexos Pagos			
	 oDgAgregaTrabajadoresA = $(idDgAgregaTrabajadoresA).dialog({
		autoOpen: false,
		modal:true,
		resizable:true,
		height: 400,
		width: 600,
		open:function(event, ui)
        {
			var oTable = $(idDataTrabajadoresA).dataTable(); 
			  oTable.fnDraw();
        },
        close: function(event, ui)
        {
        	var folio = document.getElementById('txtFolio').value;
        	var sRP = '{"id": {"folio" : "'+folio+'"}}';
        	var RegPat = jQuery.parseJSON(sRP);
        	$.postJSON("correccion/consultaAnexoPatrones.do", RegPat, function(data) {
        		var revisados = 0;
        		var omisos = 0;
        		var subdeclarados = 0;
        		var regularizados = 0;
        		for(var i=0; i< data.length ; i++){
        			revisados += data[i].atrabrevisados;
        			omisos += data[i].atrabomisos;
        			subdeclarados += data[i].atrabsubdeclarados;
        		}
        		regularizados = subdeclarados + omisos;
        		$('#txtATrabRevisados').prop("value", revisados);
        		$('#txtATrabOmisos').prop("value", omisos);
        		$('#txtATrabSubdeclarados').prop("value", subdeclarados);
        		$('#txtATrabRegularizados').prop("value", regularizados);
        		
        		
        	}).error(function(data){ 
        		
        	}).complete(function(){
			//Instrucciones para el 'complete'
        	});
        }
		
	 
		
	});
	 
	 
	 
		// Dialog de Anexos Pagos			
	 oDgAgregaTrabajadoresR = $(idDgAgregaTrabajadoresR).dialog({
		autoOpen: false,
		modal:true,
		resizable:true,
		height: 400,
		width: 600,
		open:function(event, ui)
        {
			var oTable = $(idDataTrabajadoresR).dataTable(); 
			  oTable.fnDraw();
        },
        close: function(event, ui)
        {
        	var folio = document.getElementById('txtFolio').value;
        	var sRP = '{"id": {"folio" : "'+folio+'"}}';
        	var RegPat = jQuery.parseJSON(sRP);
        	$.postJSON("correccion/consultaAnexoPatrones.do", RegPat, function(data) {
        		var revisados = 0;
        		var omisos = 0;
        		var subdeclarados = 0;
        		var regularizados = 0;
        		for(var i=0; i< data.length ; i++){
        			revisados += data[i].rtrabrevisados;
        			omisos += data[i].rtrabomisos;
        			subdeclarados += data[i].rtrabsubdeclarados;
        		}
        		regularizados = subdeclarados + omisos;
        		$('#txtRTrabRevisados').prop("value", revisados);
        		$('#txtRTrabOmisos').prop("value", omisos);
        		$('#txtRTrabSubdeclarados').prop("value", subdeclarados);
        		$('#txtRTrabRegularizados').prop("value", regularizados);
        		
        		
        	}).error(function(data){ 
        		
        	}).complete(function(){
			//Instrucciones para el 'complete'
        	});
        }
		
	 
		
	});
	 
	 
	 
	// Dialog de Conceptos Omitidos
	 oDgAgregaConceptos = $(idDgAgregaConceptos).dialog({
		autoOpen: false,
		modal:true,
		resizable:true,
		height: 400,
		width: 600,
		open:function(event, ui)
        {
			var oTable = $(idDataConceptos).dataTable(); 
			  oTable.fnDraw();
			$.postJSON("correccion/consultaConceptos.do", '', function(datas) {
					 var options = "<option value='' >--Por favor seleccione--</option>";
					 if(datas!=null)
					   for (var i = 0; i < datas.length; i++) {
				         options += "<option value='"+ datas[i].idConceptoomitido +"'>"+ datas[i].descConceptoomitido +"</option>";		     
				       }
					 $('select#cbxConceptoOmitido').html(options);
					//Agrega las opciones al control
				}).error(function(datas){ 
					(datas);
				}).complete(function(){
					//Instrucciones para el 'complete'
				});
        }
		
	 
		
	});
	 
		// Dialog de Conceptos Omitidos
	 oDgAgregaConceptosC = $(idDgAgregaConceptosC).dialog({
			autoOpen: false,
			modal:true,
			resizable:true,
			height: 400,
			width: 600,
			open:function(event, ui)
	        {
				var oTable = $(idDataConceptosC).dataTable(); 
				  oTable.fnDraw();
				$.postJSON("correccion/consultaConceptos.do", '', function(datas) {
						 var options = "<option value='' >--Por favor seleccione--</option>";
						 if(datas!=null)
						   for (var i = 0; i < datas.length; i++) {
					         options += "<option value='"+ datas[i].idConceptoomitido +"'>"+ datas[i].descConceptoomitido +"</option>";		     
					       }
						 $('select#cbxConceptoOmitidoC').html(options);
						//Agrega las opciones al control
					}).error(function(datas){ 
						(datas);
					}).complete(function(){
						//Instrucciones para el 'complete'
					});
	        }
			
		 
			
		});
	 
	 oDgAgregaPagos = $(idDgAgregaPagos).dialog({
		autoOpen: false,
		modal:true,
		resizable:true,
		height: 600,
		width: 1100,
		open:function(event, ui)
        {
			var oTable = $("#dtAnexoPagos").dataTable(); 
			oTable.fnDraw();
			var numTrabs = $('form#formCorre input#txtATrabRegularizados').val();
			if(numTrabs != null && numTrabs != '' && numTrabs > 0){
				$('form#formPagos input#txtOrdenIngreso').prop("disabled", true);
				$('form#formPagos input#txtOrdenIngreso').removeClass("red");
				
			}else{
				$('form#formPagos input#txtOrdenIngreso').prop("disabled", false);
				$('form#formPagos input#txtOrdenIngreso').addClass("red");
				
			}
			
			var f =  $('input#txtFolio').val(); 
			var sFolio = '{"folio": "'+f+'" }';
			  var folio = jQuery.parseJSON(sFolio);
			  $.postJSON("correccion/consultaPatronesAP.do", folio, function(datas) {
					 var options = "<option value='' >--Por favor seleccione--</option>";
					 if(datas!=null)
					   for (var i = 0; i < datas.length; i++) {
				         options += "<option value='"+ datas[i].id.rp +"'>"+ datas[i].id.rp +"</option>";		     
				       }
					 $('select#regpat').html(options);
					//Agrega las opciones al control
				}).error(function(datas){ 
					(datas);
				}).complete(function(){
					//Instrucciones para el 'complete'
				});
			  
			 
        }
	         ,
		 close: function(event, ui)
	        {
			 var numFolio =  document.getElementById('txtFolio').value;
			 var sFolio = '{"folio": "'+numFolio+'", "idProceso" : "1" }';
			  var folio = jQuery.parseJSON(sFolio);
			  var totalcopsp = 0;
			  var totalrcvsp = 0;
			  var totalcopact = 0;
			  var totalrcvact = 0;
			  var totalcoprec = 0;
			  var totalrcvrec = 0;
			  var totalMultasCOP = 0;
			  var totalMultasRCV = 0;
			 $.postJSON("correccion/consultaAnexoPagosAR.do", folio, function(data) {
				 var options = "<option value='-1' >--Por favor seleccione--</option>";
				 if(data!=null){
				   for (var i = 0; i < data.length; i++) {
					 totalcopsp = totalcopsp + data[i].copsp;
					 totalrcvsp = totalrcvsp + data[i].rcvsp;
					 totalcopact = totalcopact + data[i].copact;
					 totalrcvact =totalrcvact + data[i].rcvact;
					 totalcoprec = totalcoprec + data[i].coprec;
					 totalrcvrec = totalrcvrec + data[i].rcvrec;
					 totalMultasCOP = totalMultasCOP + data[i].copmultas;
					 totalMultasRCV = totalMultasRCV + data[i].rcvmultas;
			       }
				 }
				 var totalCOP = 0;
				 totalCOP = totalcopsp + totalcopact + totalcoprec + totalMultasCOP;
				 var totalRCV = 0;
				 totalRCV = totalrcvsp + totalrcvrec+ totalrcvact + totalMultasRCV;
				 sumariza("txtACOPPagSP", totalcopsp);
				 $('form#formCorre input#txtACOPPagSP').formatCurrency();
				 sumariza("txtARCVPagSP", totalrcvsp);
				 $('form#formCorre input#txtARCVPagSP').formatCurrency();
				 sumariza("txtACOPPagAct", totalcopact);
				 $('form#formCorre input#txtACOPPagAct').formatCurrency();
				 sumariza("txtARCVPagAct", totalrcvact);
				 $('form#formCorre input#txtARCVPagAct').formatCurrency();
				 sumariza("txtACOPPagRec", totalcoprec);
				 $('form#formCorre input#txtACOPPagRec').formatCurrency();
				 sumariza("txtARCVPagRec", totalrcvrec);
				 $('form#formCorre input#txtARCVPagRec').formatCurrency();
				 sumariza("txtACOPPagMul", totalMultasCOP);
				 $('form#formCorre input#txtACOPPagMul').formatCurrency();
				 sumariza("txtARCVPagMul", totalMultasRCV);
				 $('form#formCorre input#txtARCVPagMul').formatCurrency();
				 
				 
				 
				 
				 $('form#formCorre input#txtACOPPagTotal').prop("value", totalCOP);
				 $('form#formCorre input#txtACOPPagTotal').prop('readonly', true);
				 $('form#formCorre input#txtACOPPagTotal').formatCurrency();
				 
				 $('form#formCorre input#txtARCVPagTotal').prop("value", totalRCV);
				 $('form#formCorre input#txtARCVPagTotal').prop('readonly', true);
				 $('form#formCorre input#txtARCVPagTotal').formatCurrency();
				 
				 
				 var difCOP = parseFloat( $('input#txtACOPConvSP').asNumber()) - parseFloat(totalCOP);
				 var difRCV = parseFloat( $('input#txtARCVConvSP').asNumber()) - parseFloat(totalRCV);
				 						 
				 $('form#formCorre input#txtACOPxPagSP').prop('value', difCOP);
				 $('form#formCorre input#txtACOPxPagSP').prop('readonly', true);
				 $('form#formCorre input#txtACOPxPagSP').formatCurrency();
				 $('form#formCorre input#txtARCVxPagSP').prop('value', difRCV);
				 $('form#formCorre input#txtARCVxPagSP').prop('readonly', true);
				 $('form#formCorre input#txtARCVxPagSP').formatCurrency();
				//Agrega las opciones al control
			}).error(function(data){ 
				
			}).complete(function(){
				//Instrucciones para el 'complete'
			});
	        }
	 
		
	});
	 

	 oDgAgregaPagosR = $(idDgAgregaPagosR).dialog({
			autoOpen: false,
			modal:true,
			resizable:true,
			height: 600,
			width: 1100,
			open:function(event, ui)
	        {
				var oTable = $("#dtAnexoPagosR").dataTable(); 
				oTable.fnDraw();
				var numTrabs = $('form#formCorre input#txtRTrabRegularizados').val();
				if(numTrabs != null && numTrabs != '' && numTrabs > 0){
					$('form#formPagos input#txtOrdenIngresoR').prop("disabled", true);
					$('form#formPagos input#txtOrdenIngresoR').removeClass("red");
					
				}else{
					$('form#formPagos input#txtOrdenIngresoR').prop("disabled", false);
					$('form#formPagos input#txtOrdenIngresoR').addClass("red");
					
				}
				
				var f =  $('input#txtFolio').val(); 
				var sFolio = '{"folio": "'+f+'" }';
				  var folio = jQuery.parseJSON(sFolio);
				  $.postJSON("correccion/consultaPatronesAP.do", folio, function(datas) {
						 var options = "<option value='' >--Por favor seleccione--</option>";
						 if(datas!=null)
						   for (var i = 0; i < datas.length; i++) {
					         options += "<option value='"+ datas[i].id.rp +"'>"+ datas[i].id.rp +"</option>";		     
					       }
						 $('select#regpatR').html(options);
						//Agrega las opciones al control
					}).error(function(datas){ 
						(datas);
					}).complete(function(){
						//Instrucciones para el 'complete'
					});
	        }
		         ,
			 close: function(event, ui)
		        {
				 var numFolio =  document.getElementById('txtFolio').value;
				 var sFolio = '{"folio": "'+numFolio+'", "idProceso" : "2" }';
				  var folio = jQuery.parseJSON(sFolio);
				  var totalcopsp = 0;
				  var totalrcvsp = 0;
				  var totalcopact = 0;
				  var totalrcvact = 0;
				  var totalcoprec = 0;
				  var totalrcvrec = 0;
				  var totalMultasCOP = 0;
				  var totalMultasRCV = 0;
				 $.postJSON("correccion/consultaAnexoPagosAR.do", folio, function(data) {
					 var options = "<option value='-1' >--Por favor seleccione--</option>";
					 if(data!=null){
					   for (var i = 0; i < data.length; i++) {
						 totalcopsp = totalcopsp + data[i].copsp;
						 totalrcvsp = totalrcvsp + data[i].rcvsp;
						 totalcopact = totalcopact + data[i].copact;
						 totalrcvact =totalrcvact + data[i].rcvact;
						 totalcoprec = totalcoprec + data[i].coprec;
						 totalrcvrec = totalrcvrec + data[i].rcvrec;
						 totalMultasCOP = totalMultasCOP + data[i].copmultas;
						 totalMultasRCV = totalMultasRCV + data[i].rcvmultas;
				       }
					 }
					 var totalCOP = 0;
					 totalCOP = totalcopsp + totalcopact + totalcoprec + totalMultasCOP;
					 var totalRCV = 0;
					 totalRCV = totalrcvsp + totalrcvrec+ totalrcvact + totalMultasRCV;
					 sumariza("txtRCOPPagSP", totalcopsp);
					 $('form#formCorre input#txtRCOPPagSP').formatCurrency();
					 sumariza("txtRRCVPagSP", totalrcvsp);
					 $('form#formCorre input#txtRRCVPagSP').formatCurrency();
					 sumariza("txtRCOPPagAct", totalcopact);
					 $('form#formCorre input#txtRCOPPagAct').formatCurrency();
					 sumariza("txtRRCVPagAct", totalrcvact);
					 $('form#formCorre input#txtRRCVPagAct').formatCurrency();
					 sumariza("txtRCOPPagRec", totalcoprec);
					 $('form#formCorre input#txtRCOPPagRec').formatCurrency();
					 sumariza("txtRRCVPagRec", totalrcvrec);
					 $('form#formCorre input#txtRRCVPagRec').formatCurrency();
					 sumariza("txtRCOPPagMul", totalMultasCOP);
					 $('form#formCorre input#txtRCOPPagMul').formatCurrency();
					 sumariza("txtRRCVPagMul", totalMultasRCV);
					 $('form#formCorre input#txtRRCVPagMul').formatCurrency();
					 
					 
					 
					 
					 $('form#formCorre input#txtRCOPPagTotal').prop("value", totalCOP);
					 $('form#formCorre input#txtRCOPPagTotal').prop('readonly', true);
					 $('form#formCorre input#txtRCOPPagTotal').formatCurrency();
					 
					 $('form#formCorre input#txtRRCVPagTotal').prop("value", totalRCV);
					 $('form#formCorre input#txtRRCVPagTotal').prop('readonly', true);
					 $('form#formCorre input#txtRRCVPagTotal').formatCurrency();
					 
					 
					 var difCOP = parseFloat( $('input#txtRCOPConvSP').asNumber()) - parseFloat(totalCOP);
					 var difRCV = parseFloat( $('input#txtRRCVConvSP').asNumber()) - parseFloat(totalRCV);
					 						 
					 $('form#formCorre input#txtRCOPxPagSP').prop('value', difCOP);
					 $('form#formCorre input#txtRCOPxPagSP').prop('readonly', true);
					 $('form#formCorre input#txtRCOPxPagSP').formatCurrency();
					 $('form#formCorre input#txtRRCVxPagSP').prop('value', difRCV);
					 $('form#formCorre input#txtRRCVxPagSP').prop('readonly', true);
					 $('form#formCorre input#txtRRCVxPagSP').formatCurrency();
					//Agrega las opciones al control
				}).error(function(data){ 
					
				}).complete(function(){
					//Instrucciones para el 'complete'
				});
		        }
		 
			
		});
	 
	 var validaCapturaC = $("#formCorre").validate({
	  	  rules: {
	  		txtGRP: {
	  	      required: true,
	  	      maxlength: 10,
	  	      minlength: 10,
	  	      alphanumeric: true
	  	   },
	  	  txtGNombre: {
		   	  required: true,
		   	  maxlength: 100,
		   	 alphanumeric: true
		   },
		   txtAFLI15:{
			  maxlength: 12,
			  digits: true
			},
			txtANoConvenio:{
				maxlength: 20,
				alphanumeric: true
			},
			txtANoParcialidades:{
				maxlength : 2,
		  		digits: true,
		  		min : 2,
		  		max : 48
			},
			txtANoOficioOI:{
				maxlength : 11,
		  		digits: true
		  	},
		  	txtRPorcCR:{
		  		maxlength : 3,
		  		digits: true,
		  		min : 1,
		  		max : 100
		  	},
		  	txtRNoOficioRDE:{
		  		maxlength : 11,
		  		digits: true
		  	},
		  	txtRNoOficioOD:{
		  		maxlength : 11,
		  		digits: true
		  	},
		  	txtRNoConvenio:{
		  		maxlength: 20,
				alphanumeric: true
		  	},
		  	txtRNoParcialidades:{
		  		maxlength : 2,
		  		digits: true,
		  		min : 2,
		  		max : 48
		  	}
	  	  }
	  });
	 

	 var validaPagos = $("#formPagos").validate({
	  	  rules: {
	  		  regpat: {
	  			 required: true
	  	    },
	  	    concepto:{
	  	    	 required: true
	  	    }, 
	  	  txtFolioSUA: {
	  		required: function(element) {
	  				return document.getElementById('txtOrdenIngreso').value == '';
	  	         }
	  	       },
	  	  txtOrdenIngreso: {
	  		required: function(element){
	  			  return document.getElementById('txtFolioSUA').value == '';
	  		  }
	  	  },
	  	fechaPagoAnexoPagos: {
	  		required: true
	  	},
	  	txtNoCredito: {
	  		required: function(element){
	  			  return document.getElementById('txtOrdenIngreso').value != '';
	  		  }
	  	},
	  	periodoCOP: {
	  		required:function(element){
	  			  return (document.getElementById('concepto').value == 'COP' || document.getElementById('concepto').value == 'COPRCV');
	  		  }
	  	}, 
	  	periodoRCV: {
	  		required:function(element){
	  			  return (document.getElementById('concepto').value == 'RCV' || document.getElementById('concepto').value == 'COPRCV');
	  		  }
	  	},
	  	txtCOPSP:{
	  		maxlength: 14 
	  	},
	  	txtCOPAct:{
	  		maxlength: 14
	  	},
	  	txtCOPRec:{
	  		maxlength: 14
	  	},
	  	txtCOPTotal: {
	  		required:function(element){
	  			  return (document.getElementById('concepto').value == 'COP' || document.getElementById('concepto').value == 'COPRCV');
	  		  }
	  		
	  	},
	  	txtRCVSP: {
	  		maxlength: 14
	  		
	  	},
	  	txtRCVAct: {
	  		maxlength: 14
	  		
	  	},
	  	txtRCVRec: {
	  		maxlength: 14
	  		
	  	},
	  	txtRCVTotal:{
	  		required:function(element){
	  			  return (document.getElementById('concepto').value == 'RCV' || document.getElementById('concepto').value == 'COPRCV');
	  		  }
	  		
	  		
		  
	  	}
	  	  
	  	  },
	  	 messages: {
	  		regpat: "Seleccione un registro patronal",
	  		concepto: "Seleccione un concepto",
	  		txtFolioSUA : "Debe Ingresar Folio SUA / Orden Cr&eacute;dito",
	  		txtOrdenIngreso: "Debe Ingresar Folio SUA / Orden Cr&eacute;dito",
	  		fechaPagoAnexoPagos : "Debe seleccionar la fecha de pago",
	  		txtNoCredito : "Debe ingresar el n&uacute;mero de No. Cr&eacute;dito",
	  		periodoCOP	: "Debe seleccionar un Per&iacute;odo de COP",
	  		periodoRCV: "Debe seleccionar un Per&iacute;odo de RCV",
	  		txtCOPTotal: "No ha registrado el Monto COP Pagado",
	  		txtRCVTotal: "No ha registrado el Monto RCV Pagado"
	  	 }
	 	
	  }); 
	 
	 var validaPagosR = $("#formPagosR").validate({
	  	  rules: {
	  		  regpatR: {
	  			 required: true
	  	    },
	  	    conceptoR:{
	  	    	 required: true
	  	    }, 
	  	  txtFolioSUAR: {
	  		required: function(element) {
	  				return document.getElementById('txtOrdenIngresoR').value == '';
	  	         }
	  	       },
	  	  txtOrdenIngresoR: {
	  		required: function(element){
	  			  return document.getElementById('txtFolioSUAR').value == '';
	  		  }
	  	  },
	  	fechaPagoAnexoPagosR: {
	  		required: true
	  	},
	  	txtNoCredito: {
	  		required: function(element){
	  			  return document.getElementById('txtOrdenIngresoR').value != '';
	  		  }
	  	},
	  	periodoCOPR: {
	  		required:function(element){
	  			  return (document.getElementById('conceptoR').value == 'COP' || document.getElementById('conceptoR').value == 'COPRCV');
	  		  }
	  	}, 
	  	periodoRCVR: {
	  		required:function(element){
	  			  return (document.getElementById('conceptoR').value == 'RCV' || document.getElementById('conceptoR').value == 'COPRCV');
	  		  }
	  	},
	  	txtCOPSPR:{
	  		maxlength: 14 
	  	},
	  	txtCOPActR:{
	  		maxlength: 14
	  	},
	  	txtCOPRecR:{
	  		maxlength: 14
	  	},
	  	txtCOPTotalR: {
	  		required:function(element){
	  			  return (document.getElementById('conceptoR').value == 'COP' || document.getElementById('conceptoR').value == 'COPRCV');
	  		  }
	  		
	  	},
	  	txtRCVSPR: {
	  		maxlength: 14
	  		
	  	},
	  	txtRCVActR: {
	  		maxlength: 14
	  		
	  	},
	  	txtRCVRecR: {
	  		maxlength: 14
	  		
	  	},
	  	txtRCVTotalR:{
	  		required:function(element){
	  			  return (document.getElementById('conceptoR').value == 'RCV' || document.getElementById('conceptoR').value == 'COPRCV');
	  		  }
	  		
	  		
		  
	  	}
	  	  
	  	  },
	  	 messages: {
	  		regpatR: "Seleccione un registro patronal",
	  		conceptoR: "Seleccione un concepto",
	  		txtFolioSUAR : "Debe Ingresar Folio SUA / Orden Cr&eacute;dito",
	  		txtOrdenIngresoR: "Debe Ingresar Folio SUA / Orden Cr&eacute;dito",
	  		fechaPagoAnexoPagosR : "Debe seleccionar la fecha de pago",
	  		txtNoCreditoR : "Debe ingresar el n&uacute;mero de No. Cr&eacute;dito",
	  		periodoCOPR	: "Debe seleccionar un Per&iacute;odo de COP",
	  		periodoRCVR: "Debe seleccionar un Per&iacute;odo de RCV",
	  		txtCOPTotalR: "No ha registrado el Monto COP Pagado",
	  		txtRCVTotalR: "No ha registrado el Monto RCV Pagado"
	  	 }
	 	
	  }); 
	 
	 $('select#cbxAMotivoRechazo').prop('disabled', true);
	 $('select#cbxAuditor').prop('disabled', true);
	 $('select#cbxCMotivoCancelacion').prop('disabled', true);
	 $('select#CgcCatMotivoCancelacion\\.idMotivocancelacion').prop('disabled', true);

	 
	// Dialog de busca correccion			
	 oDgBuscar = $(idDgBuscar).dialog({
	 	autoOpen: false,
	 	modal:true,
	 	resizable:true,
	 	height: 600,
	 	width: 1000
	 	
	 });

		 $('#txtCOPSP').blur(function()
	             {
	                 $('#txtCOPSP').formatCurrency();
	             });
	 $('#txtCOPAct').blur(function()
             {
                 $('#txtCOPAct').formatCurrency();
             });
	 $('#txtCOPRec').blur(function()
             {
                 $('#txtCOPRec').formatCurrency();
             });
	 $('#txtCOPTotal').blur(function()
             {
                 $('#txtCOPTotal').formatCurrency();
             });
	 
	 $('#txtRCVSP').blur(function()
             {
                 $('#txtRCVSP').formatCurrency();
             });
	 $('#txtRCVAct').blur(function()
             {
                 $('#txtRCVAct').formatCurrency();
             });
	 $('#txtRCVRec').blur(function()
             {
                 $('#txtRCVRec').formatCurrency();
             });
	 $('#txtRCVTotal').blur(function()
             {
                 $('#txtRCVTotal').formatCurrency();
             });
	 $('#txtACOPPagSP').blur(function()
             {
                 $('#txtACOPPagSP').formatCurrency();
             });
	 $('#txtARCVPagSP').blur(function()
             {
                 $('#txtARCVPagSP').formatCurrency();
             });
	 $('#txtACOPPagAct').blur(function()
             {
                 $('#txtACOPPagAct').formatCurrency();
             });
	 $('#txtARCVPagAct').blur(function()
             {
                 $('#txtARCVPagAct').formatCurrency();
             });
	 $('#txtACOPPagRec').blur(function()
             {
                 $('#txtACOPPagRec').formatCurrency();
             });
	 
	 $('#txtACOPPagMul').blur(function()
             {
                 $('#txtACOPPagMul').formatCurrency();
             });
	 $('#txtARCVPagMul').blur(function()
             {
                 $('#txtARCVPagMul').formatCurrency();
             });
	 
	 $('#txtACOPPagTotal').blur(function()
             {
                 $('#txtACOPPagTotal').formatCurrency();
             });
	 $('#txtARCVPagTotal').blur(function()
             {
                 $('#txtARCVPagTotal').formatCurrency();
             });
	 
	 $('#txtACOPConvSP').blur(function()
             {
                 $('#txtACOPConvSP').formatCurrency();
             });
	 $('#txtARCVConvSP').blur(function()
             {
                 $('#txtARCVConvSP').formatCurrency();
             });
	 
	 $('#txtACOPxPagSP').blur(function()
             {
                 $('#txtACOPxPagSP').formatCurrency();
             });
	 $('#txtARCVxPagSP').blur(function()
             {
                 $('#txtARCVxPagSP').formatCurrency();
             });
	 
	 $('#txtRCOPPagSP').blur(function()
             {
                 $('#txtRCOPPagSP').formatCurrency();
             });
	 $('#txtRRCVPagSP').blur(function()
             {
                 $('#txtRRCVPagSP').formatCurrency();
             });
	 
	 
	 
	 $('#txtRCOPPagAct').blur(function()
             {
                 $('#txtRCOPPagAct').formatCurrency();
             });
	 $('#txtRRCVPagAct').blur(function()
             {
                 $('#txtRRCVPagAct').formatCurrency();
             });
	 
	 $('#txtRCOPPagRec').blur(function()
             {
                 $('#txtRCOPPagRec').formatCurrency();
             });
	 $('#txtRRCVPagRec').blur(function()
             {
                 $('#txtRRCVPagRec').formatCurrency();
             });
	 
	 $('#txtRCOPPagMul').blur(function()
             {
                 $('#txtRCOPPagMul').formatCurrency();
             });
	 $('#txtRRCVPagMul').blur(function()
             {
                 $('#txtRRCVPagMul').formatCurrency();
             });
	 
	 $('#txtRCOPPagTotal').blur(function()
             {
                 $('#txtRCOPPagTotal').formatCurrency();
             });
	 $('#txtRRCVPagTotal').blur(function()
             {
                 $('#txtRRCVPagTotal').formatCurrency();
             });
	 
	 $('#txtRCOPConvSP').blur(function()
             {
                 $('#txtRCOPConvSP').formatCurrency();
             });
	 $('#txtRRCVConvSP').blur(function()
             {
                 $('#txtRRCVConvSP').formatCurrency();
             });
	 
	 $('#txtRCOPxPagSP').blur(function()
             {
                 $('#txtRCOPxPagSP').formatCurrency();
             });
	 $('#txtRRCVxPagSP').blur(function()
             {
                 $('#txtRRCVxPagSP').formatCurrency();
             });
	 
	 $('#txtRCVMultas').blur(function()
             {
                 $('#txtRCVMultas').formatCurrency();
             });
	 
	 
	 $('#txtCOPMultas').blur(function()
             {
                 $('#txtCOPMultas').formatCurrency();
             });
	 
	 
});//$(document).ready(function()

function buscaElemento(elemento) {
	var idTipo = $('form#formCorre select#cgcCatTipo\\.idTipo').val();
	if(idTipo == -1){
  		
  			desactivaCombos('cbxGOrigen');
  		
		 
			desactivaCombos('cbxGCriterioSeleccion');
			desabilitaForma();
  	}
	var idOrigen = $('form#formCorre select#cbxGOrigen').val();
	if(idOrigen == '-1' || idOrigen == ''){
		desactivaCombos('cbxGCriterioSeleccion');
		desabilitaForma();
		generaFolioInicial();
	}
	var idCritS = $('form#formCorre select#cbxGCriterioSeleccion').val();
	if(idCritS == '-1' || idCritS == ''){
		//desabilitaForma();
		desactivaDirecto('txtGRP');
		desactivaDirecto('txtGNombre');
		
	}
	var sReglaNegocio = '{"nombrecontrol":"'+elemento+'", "cgcCatTipo":{"idTipo":"'+idTipo+'"}}';
	  var reglaNegocio = jQuery.parseJSON(sReglaNegocio);
	  //alert (reglaNegocio.id.nombrecontrol);
	  $.postJSON("correccion/consultaRegla.do", reglaNegocio, function(data) {
		  var hijos = data.hijos;
		   if(hijos!=null){
			  var tokens = hijos.split(",");
			for(var i=0; i< tokens.length; i++){
		  	
		  		if(tokens[i]=='cbxGOrigen'){
		  			activaCombos('cbxGOrigen');
		  			activa(tokens[i]);
		  		}
	  		 else	if(tokens[i]=='cbxGCriterioSeleccion'){
		  			activaCombos('cbxGCriterioSeleccion');
		  			activa(tokens[i]);
		  		}
	  		else	if(tokens[i]=='cbxAMotivoRechazo'){
	  			activaCombos('cbxAMotivoRechazo');
	  			activa(tokens[i]);
	  		}
	  		 else{
			  		activa(tokens[i]);
		  		}
		  	if(elemento =='txtRPorcCR'){
		  			var razonabilidad = document.getElementById('txtRPorcCR').value;
		  			
		  			if(razonabilidad != '' && razonabilidad <= 9){
		  				activa('dtpRCCPR');
		  			}else{
		  				desactiva('dtpRCCPR');
		  			}
		  		}
		  	
		  		
			}
		  }
		  var hijosIndependientes = data.hijosindependientes;
		  
		  if(hijosIndependientes!=null){
			  var itokens = hijosIndependientes.split(",");
			  for(var i=0; i< itokens.length; i++){
				  activa(itokens[i]);
			  }
		  }
		}).error(function(data){ 
			
		}).complete(function(){
			//Instrucciones para el 'complete'
		});
	  
	
}


function inhabilitar(e, elemento) {
	
	  if(document.getElementById(elemento)!= null && document.getElementById(elemento).value.length == 0){
		  var idTipo = $('form#formCorre select#cgcCatTipo\\.idTipo').val(); 
		  var sReglaNegocio = '{"nombrecontrol":"'+elemento+'", "cgcCatTipo":{"idTipo":"'+idTipo+'"}}';
		  var reglaNegocio = jQuery.parseJSON(sReglaNegocio);
		  //alert (reglaNegocio.id.nombrecontrol);
		  $.postJSON("correccion/consultaRegla.do", reglaNegocio, function(data) {
			  var hijos = data.hijos;
			  if(hijos != null){
				  var tokens = hijos.split(",");
			  	var nivel = data.nivel;
			 // alert(data.hijosindependientes);
				  for(var i=0; i< tokens.length; i++){
						  if(tokens[i]=='cbxAMotivoRechazo'){
							  	eliminaOpcionesSelect('cbxAMotivoRechazo');
								desactivaCombos('cbxAMotivoRechazo');
								buscaElementoDes(tokens[i]);
					  		}
						  else if(tokens[i]=='cbxATipoObra'){
							  eliminaOpcionesSelect('CgcCatMotivoCancelacion\\.idMotivocancelacion');
							 	desactivaCombos('CgcCatTipoObra\\.idTipoobra');
							 	buscaElementoDes(tokens[i]);
					  		}else if(tokens[i]=='chkCPresentoPagos'){
								 $('#chkCPresentoPagos').prop("checked", false)
								desactiva(tokens[i]);
						  		inhabilitar(e, tokens[i]);
						  		validarPresentoPagos();
						  	}else{
					  			desactiva(tokens[i]);
					  			inhabilitar(e, tokens[i]);
					  		}
				  }
			  }
			  var hijosIndependientes = data.hijosindependientes;
			  
			  if(hijosIndependientes!=null){
				  var itokens = hijosIndependientes.split(",");
				  for(var i=0; i< itokens.length; i++){
						  if(itokens[i]=='cbxAMotivoCancelacion'){
							  
							  desactivaCombos('CgcCatMotivoCancelacion\\.idMotivocancelacion');
							  inhabilitar(e, itokens[i]);
					  		}
						  else if(itokens[i]=='cbxGTipoObra'){
							  
							  desactivaCombos('CgcCatTipoObra\\.idTipoobra');
							  inhabilitar(e, itokens[i]);
					  		}else{
					  			desactiva(itokens[i]);
					  			inhabilitar(e, itokens[i]);
					  		}
					 
				  }
			  }
			 
			
			  
			}).error(function(data){ 
				
			}).complete(function(){
				//Instrucciones para el 'complete'
			});
		  
		  
	  }
	  }

function setMensaje(elemento) {
	var idTipo = $('form#formCorre select#cgcCatTipo\\.idTipo').val(); 
	var sReglaNegocio = '{"nombrecontrol":"'+elemento+'", "cgcCatTipo":{"idTipo":"'+idTipo+'"}}';
	  var reglaNegocio = jQuery.parseJSON(sReglaNegocio);
	  var nivel = '';
	  //alert (reglaNegocio.id.nombrecontrol);
	  $.postJSON("correccion/consultaRegla.do", reglaNegocio, function(data) {
		  var hijos = data.hijos;
		  $('#txtGMensajeAyuda').prop("value", data.mensaje);
		  if(data.cgcCatStatus != null){
			  document.getElementById("txtStatus").value= data.cgcCatStatus.descStatus;
			  document.getElementById("idStatusHidden").value= data.cgcCatStatus.idStatus;
		  }
		  	}).error(function(data){ 
		  		
		}).complete(function(){
			//Instrucciones para el 'complete'
		});
}
	

function activa(elemento){
	//--Por favor seleccione--

	var idSeleccion = $('form#formCorre select#cbxGCriterioSeleccion').val();
	if(idSeleccion != -1 && idSeleccion!=0){
		 $('form#formCorre input#'+elemento ).prop('disabled', false);
		 $('form#formCorre input#'+elemento ).addClass("red");
	}
}

function guardarCorreccion(foliog){
var rp =$('input#txtGRP').val();
var idTipo = $('select#cgcCatTipo\\.idTipo').val(); 
var idOrigen =$('select#cbxGOrigen').val(); 
var idCriterioSel = $('select#cbxGCriterioSeleccion').val();
var cvePatron = $('input#txtGRP').val();
var nombre = $('input#txtGNombre').val();
var afil15 = $('input#txtAFIL15').val();
var periododel = formateaFecha($('input#dtpGPeriodoDel').val());
var periodoal = formateaFecha($('input#dtpGPeriodoAl').val());
var aoi = formateaFecha($('input#dtpAOI').val());
var anoOficionOI = $('input#txtANoOficioOI').val();
var aoin = formateaFecha($('input#dtpAOIN').val());
var asp = formateaFecha($('input#dtpASP').val());
var asr = formateaFecha($('input#dtpASR').val());
var asa = formateaFecha($('input#dtpASA').val());
var ap =  formateaFecha($('input#dtpAP').val());
var aasr =  formateaFecha($('input#dtpAASR').val());
var aacp = formateaFecha($('input#dtpAACP').val());
var idMotivoRechazo = $('select#CgcCatMotivoRechazo\\.idMotivoRechazo').val();
var aapp = formateaFecha($('input#dtpAAPP').val());
var anoConvenio = $('input#txtANoConvenio').val();
var anoParcialidades = $('input#txtANoParcialidades').val();
var acopconvsp = $('input#txtACOPConvSP').val();
var arcvconvsp = $('input#txtARCVConvSP').val();
var rcr = formateaFecha($('input#dtpRCR').val());
var rporccr = $('input#txtRPorcCR').val();
var rccpr = formateaFecha($('input#dtpRCCPR').val());
var rrde = formateaFecha($('input#dtpRRDE').val());
var rnooficiorde = $('input#txtRNoOficioRDE').val();
var rrdn = formateaFecha($('input#dtpRRDN').val());
var rod = formateaFecha($('input#dtpROD').val());
var rnooficiood = $('input#txtRNoOficioOD').val();
var rnod = formateaFecha($('input#dtpRNOD').val());
var rvda = formateaFecha($('input#dtpRVDA').val());
var rvpp= formateaFecha($('input#dtpRVPP').val());
var rvtc = formateaFecha($('input#dtpRVTC').val());
var rdc = formateaFecha($('input#dtpRDC').val());
var rnoconvenio = $('input#txtRNoConvenio').val();
var rnoparcialidades = $('input#txtRNoParcialidades').val();
var rcopconvsp = $('input#txtRCOPConvSP').val();
var rrcvconvsp = $('input#txtRRCVConvSP').val();
var csd = '';
var cpai = '';
var cds = '';
var cc = '';
var idMoticocancelacion = $('select#CgcCatMotivoCancelacion\\.idMotivocancelacion').val();
var cpresentopagos = '';
var cnooficio = '';
var idStatus = '';
var idAuditor = '';

var folio = foliog;
var sCorreccion = '{' +
			'"folio": "'+folio+'",'+
			'"cgcCatOrigen" : {"idOrigen": "'+idOrigen+'"},'+
			'"cgcCatTipo" : {"idTipo": "'+idTipo+'"},'+
			'"cgtCatCriterioSeleccion" : {"idCriterioseleccion": "'+idCriterioSel+'"},'+
			'"cvePatron" : "'+cvePatron+'",'+
			'"nombre" : "'+nombre+'",'+
			'"afil15" : "'+afil15+'",'+
			'"periododel" : "'+periododel+'",'+
			'"periodoal" : "'+periodoal+'",'+
			'"afil15" : "'+afil15+'",'+
			'"aoi" : "'+aoi+'",'+
			'"anooficiooi" : "'+anoOficionOI+'",'+
			'"aoin" : "'+aoin+'",'+
			'"asp" : "'+asp+'",'+
			'"asr" : "'+asr+'",'+
			'"asa" : "'+asa+'",'+
			'"ap" : "'+ap+'",'+
			'"aasr" : "'+aasr+'",'+
			'"aacp" : "'+aacp+'",'+
			'"aapp" : "'+aapp+'",'+
			'"anoconvenio" : "'+anoConvenio+'",'+
			'"anoparcialidades" : "'+anoParcialidades+'",'+
			'"acopconvsp" : "'+acopconvsp+'",'+
			'"arcvconvsp" : "'+arcvconvsp+'",'+
			'"rcr" : "'+rcr+'",'+
			'"rporccr" : "'+rporccr+'",'+
			'"rccpr" : "'+rccpr+'",'+
			'"rrde" : "'+rrde+'",'+
			'"rnooficiorde" : "'+rnooficiorde+'",'+
			'"rrdn" : "'+rrdn+'",'+
			'"rod" : "'+rod+'",'+
			'"rnooficiood" : "'+rnooficiood+'",'+
			'"rnod" : "'+rnod+'",'+
			'"rvda" : "'+rvda+'",'+
			'"rvpp" : "'+rvpp+'",'+
			'"rvtc" : "'+rvtc+'",'+
			'"rdc" : "'+rdc+'",'+
			'"rnoconvenio" : "'+rnoconvenio+'",'+
			'"rnoparcialidades" : "'+rnoparcialidades+'",'+
			'"rcopconvsp" : "'+rcopconvsp+'",'+
			'"rrcvconvsp" : "'+rrcvconvsp+'",'+
			'"csd" : "'+csd+'",'+
			'"cpai" : "'+cpai+'",'+
			'"cds" : "'+cds+'",'+
			'"cc" : "'+cc+'"}';
			
var correccion = jQuery.parseJSON(sCorreccion);
  $.postJSON("correccion/guardaCorreccion.do", correccion, function(data) {
		  document.getElementById('guardadoHidden').value = 1;
		  validaGuardado();
		  var sRP = ""
			  var folio = document.getElementById('txtFolio').value;
			  var rp  = document.getElementById('registroPatronal').value;
			  if(rp=='')
			  	rp = document.getElementById('txtGRP').value;
			  
		  var sRP = '{"id": {"folio" : "'+folio+'" , "rp" : "'+rp+'"}}';
		  var RegPat = jQuery.parseJSON(sRP);
		  $.postJSON("correccion/guardaRP.do", RegPat, function(dataa) {
			  document.getElementById('txtGNoRPA').value = 1;
			  var oTables = $(idDataTablaAPatrones).dataTable(); 
			  oTables.fnDraw();
			  
		}).error(function(dataa){ 
			(dataa);
		}).complete(function(){
			//Instrucciones para el 'complete'
		});
	}).error(function(data){ 
		
	}).complete(function(){
		//Instrucciones para el 'complete'
	});

}


function guardarCorreccionFromPromo(data){
	var periododel = '';
	var periodoal = '';
var rp =data[0].cvePatron;
var idTipo = data[0].cgcCatTipo.idTipo;
var idOrigen = data[0].cgcCatOrigen.idOrigen; 
var idCriterioSel = data[0].cgtCatCriterioSeleccion.idCriterioseleccion;
var cvePatron = data[0].cvePatron;
var nombre = data[0].nombre;
var afil15 = data[0].afil15;
if(data[0].periododel!=null)
	periododel = data[0].periododel;
if(data[0].periodoal!= null)
	periodoal = data[0].periodoal;
var folioPromo = data[0].folio;
var folioCorre = $('#txtFolio').val();

	var sCorreccion = '{' +
			'"folio" : "' + folioCorre + '",'+
			'"cgcCatOrigen" : {"idOrigen": "'+idOrigen+'"},'+
			'"cgcCatTipo" : {"idTipo": "'+idTipo+'"},'+
			'"cgtCatCriterioSeleccion" : {"idCriterioseleccion": "'+idCriterioSel+'"},'+
			'"cvePatron" : "'+cvePatron+'",'+
			'"nombre" : "'+nombre+'",'+
			'"afil15" : "'+afil15+'",'+
			'"periododel" : "'+periododel+'",'+
			'"periodoal" : "'+periodoal+'",'+
			'"afil15" : "'+afil15+'"}';
			
  var correccion = jQuery.parseJSON(sCorreccion);
  $.postJSON("correccion/guardaCorreccion.do", correccion, function(corre) {
		  document.getElementById('guardadoHidden').value = 1;
		  $('#txtFolio').prop("value", corre.folio);
		  validaGuardado();
		  
		  var sRP = ""
		  var sRP = '{"id": {"folio" : "'+corre.folio+'" , "rp" : "'+rp+'"}}';
		  var RegPat = jQuery.parseJSON(sRP);
		  $.postJSON("correccion/guardaRP.do", RegPat, function(dataa) {
			  document.getElementById('txtGNoRPA').value = 1;
			
			  
		}).error(function(dataa){ 
			(dataa);
		}).complete(function(){
			//Instrucciones para el 'complete'
		}); 
		  var sParam =  '["'+data[0].folio+'","'+corre.folio+'"]';
		  var param = jQuery.parseJSON(sParam);
		  $.postJSON("correccion/finalizaPromocion.do", param, function(data) {
				
			
				 
			}).error(function(data){ 
				
			}).complete(function(){
				//Instrucciones para el 'complete'
			});
	}).error(function(data){ 
		
	}).complete(function(){
		//Instrucciones para el 'complete'
	});
  
 

}



function formateaFecha(sFecha){
	var fecha = '';
	var fec = null;
	var fe = null;
	if(sFecha != ''){
		var anio = sFecha.substring(6,10);
		var mes = sFecha.substring(3,5) ;
		var dia = sFecha.substring(0,2);
		var fec = new Date(anio, mes-1, dia);
		fe = new Date( parseInt(fec.getTime() + ( 1 * 86400000 ) ) );
		fecha = fe.getFullYear() + '-' + (fe.getMonth()+1) + '-' + fe.getDate();
	}
	
	
	return fecha;
	
}

function desactivaCombos(combo){
	//--Por favor seleccione--
	$('form#formCorre select#'+ combo).html('');
	$('form#formCorre select#'+ combo).html("<option value='-1'>--Por favor seleccione--</option>");
	 $('form#formCorre select#'+combo ).removeClass("red");
	$('form#formCorre select#'+ combo).prop("disabled", true);
	
	
}


function activaCombos(combo){
	//CgcCatTipoObra\\.idTipoobra
	$('form#formCorre select#'+ combo).prop('disabled', false);
	$('form#formCorre select#'+combo ).addClass("red");
}


function validarRegPat(e, regPat, digVer) {
	//F7028494100
	  tecla = (document.all) ? e.keyCode : e.which;
	  if (tecla==13 ) {
		  if(document.getElementById(regPat).value.length > 0  && document.getElementById(regPat).value.length <=11 ){
			  if(document.getElementById(regPat).value != '99999999999'){
				  var sPatron = '{"registroPatronal":"'+document.getElementById(regPat).value+'"}';
				  var patron = jQuery.parseJSON(sPatron);
				  //	alert (reglaNegocio.id.nombrecontrol);
				  $.postJSON("correccion/validaRegPat.do", patron, function(data) {
					  var folio = document.getElementById('txtFolio').value;
 					  var rp  = document.getElementById('registroPatronal').value;
					  var sRP = '{"id": {"folio" : "'+folio+'" , "rp" : "'+rp+'"}}';
					  var RegPat = jQuery.parseJSON(sRP);
					  $.postJSON("correccion/guardaRP.do", RegPat, function(dataa) {
						  var oTables = $(idDataTablaAPatrones).dataTable(); 
						  oTables.fnDraw();
					  
					}).error(function(dataa){ 
						(dataa);
					}).complete(function(){
						//Instrucciones para el 'complete'
					});
				  
				  
				}).error(function(data){ 
					
				}).complete(function(){
					//Instrucciones para el 'complete'
				});
			  
		  }  
		  }
	  }
	}

function agregarTrabajadores(proceso){
	var revisados = 0;
	var omisos = 0;
	var subdeclarados = 0;
	var regularizados = 0;
	
		
		var oTables = null;
		if( proceso == 'A'){
			if(document.getElementById('revisados').value.length!=''){
				revisados = document.getElementById('revisados').value;
			}
			if(document.getElementById('omisos').value.length!=''){
				omisos = document.getElementById('omisos').value;
			}
			if(document.getElementById('subdeclarados').value.length!=''){
				subdeclarados = document.getElementById('subdeclarados').value;
			}
			if(parseInt(revisados) < (parseInt(omisos) + parseInt(subdeclarados))){
				alert('La suma de Trabajadores Omisos y Trabajadores Subdeclarados no puede superar el total de Trabajadores Revisados');
				return false;
			}
			oTables = $(idDataTrabajadoresA).dataTable();
		}
		else if(proceso == 'R'){
			if(document.getElementById('revisadosR').value.length!=''){
				revisados = document.getElementById('revisadosR').value;
			}
			if(document.getElementById('omisosR').value.length!=''){
				omisos = document.getElementById('omisosR').value;
			}
			if(document.getElementById('subdeclaradosR').value.length!=''){
				subdeclarados = document.getElementById('subdeclaradosR').value;
			}
			if(parseInt(revisados) < (parseInt(omisos) + parseInt(subdeclarados))){
				alert('La suma de Trabajadores Omisos y Trabajadores Subdeclarados no puede superar el total de Trabajadores Revisados');
				return false;
			}
			oTables = $(idDataTrabajadoresR).dataTable();
		}
		regularizados = omisos + subdeclarados;
		 var folio = document.getElementById('txtFolio').value;
		 var radios = document.getElementsByName("radio");
		 var seleccionado = 0;
			for (i=0;i<radios.length;i++)
			 {
				if(radios[i].checked)
				{
					seleccionado = 1;
					var idPatron = radios[i].value;
					var sRP = '';
					if (proceso == 'A'){
						sRP = '{"id": {"folio" : "'+folio+'" , "rp" : "'+idPatron+'"}, "atrabomisos" : "'+omisos+'" , "atrabrevisados": "'+revisados+'", "atrabsubdeclarados" : "'+subdeclarados+'"}';
					}else if(proceso == 'R'){
						sRP = '{"id": {"folio" : "'+folio+'" , "rp" : "'+idPatron+'"}, "rtrabomisos" : "'+omisos+'" , "rtrabrevisados": "'+revisados+'", "rtrabsubdeclarados" : "'+subdeclarados+'"}';
					}
					  var RegPat = jQuery.parseJSON(sRP);
					  $.postJSON("correccion/guardaRP.do", RegPat, function(dataa) {
						  oTables.fnDraw();
					}).error(function(dataa){ 
						(dataa);
					}).complete(function(){
						//Instrucciones para el 'complete'
					});
				}
			 }
			if(seleccionado == 0){
				alert('Es necesario seleccionar un Registro Patronal');
				return false;
			}
		
			
}


function agregarConceptoOmitido(proceso){
	var concepto = $('#cbxConceptoOmitido').val();
	var idProceso = 0;
	if(proceso == 'A'){
		idProceso = 1;
	}else if(proceso = 'B'){
		idProceso = 2;
	}
	
	var idSituacionCO =  3;
	var	idConceptoOmitido =$('select#cbxConceptoOmitido').val();
	if(idConceptoOmitido == ''){
		alert('Debe seleccionar un concepto');
		return false;
	}else{
	var	folio = document.getElementById('txtFolio').value;
	var sConceptoOmitido = '{"id": {"idProceso" : "'+idProceso+'" , "cgcCatSituacionCO" : {"idSituacionco" :"'+idSituacionCO+'"}, "cgcCatConceptoOmitido" : {"idConceptoomitido" : "'+idConceptoOmitido+'"} , "folio": "'+folio+'"}}'; 
	var conceptoOmitido = jQuery.parseJSON(sConceptoOmitido);
	  $.postJSON("correccion/guardaConceptoOmitido.do", conceptoOmitido, function(dataa) {
		  var oTable = $(idDataConceptos).dataTable(); 
		  oTable.fnDraw();
	}).error(function(dataa){ 
		(dataa);
	}).complete(function(){
		//Instrucciones para el 'complete'
	});
	}
	
}


function agregarConceptoOmitidoC(proceso){
	var concepto = $('#cbxConceptoOmitido').val();
	var idProceso = 0;
	if(proceso == 'A'){
		idProceso = 1;
	}else if(proceso = 'B'){
		idProceso = 2;
	}
	var	idConceptoOmitido =$('select#cbxConceptoOmitidoC').val();
	var	folio = document.getElementById('txtFolio').value;
	var banderaExiste = 0;
	
	var idSituacionCO =  1;
	var sConceptoOmitido = '{"id": {"idProceso" : "'+idProceso+'" , "cgcCatSituacionCO" : {"idSituacionco" :"'+idSituacionCO+'"}, "cgcCatConceptoOmitido" : {"idConceptoomitido" : "'+idConceptoOmitido+'"} , "folio": "'+folio+'"}}'; 
	var conceptoOmitido = jQuery.parseJSON(sConceptoOmitido);
	  $.postJSON("correccion/consultaConceptosFolio.do", conceptoOmitido, function(dataC) {
		if(dataC!=null){
			banderaExiste = 1;
		}
		if(banderaExiste == 0){
			var chkPagado = $('#pagado').is(":checked");
			if(chkPagado == true){
				var idSituacionCO =  1;
				var sConceptoOmitido = '{"id": {"idProceso" : "'+idProceso+'" , "cgcCatSituacionCO" : {"idSituacionco" :"'+idSituacionCO+'"}, "cgcCatConceptoOmitido" : {"idConceptoomitido" : "'+idConceptoOmitido+'"} , "folio": "'+folio+'"}}'; 
				var conceptoOmitido = jQuery.parseJSON(sConceptoOmitido);
				  $.postJSON("correccion/guardaConceptoOmitido.do", conceptoOmitido, function(dataa) {
					  var oTable = $(idDataConceptosC).dataTable(); 
					  oTable.fnDraw();
				}).error(function(dataa){ 
					(dataa);
				}).complete(function(){
					//Instrucciones para el 'complete'
				});
			}
			var chkNoAclarado = $('#noAclarado').is(":checked");
			var idSituacionCO =  3;
			if(chkNoAclarado == true){
				idSituacionCO =  2;
			}else{
				idSituacionCO =  3;
			}
			
			
			var sConceptoOmitido = '{"id": {"idProceso" : "'+idProceso+'" , "cgcCatSituacionCO" : {"idSituacionco" :"'+idSituacionCO+'"}, "cgcCatConceptoOmitido" : {"idConceptoomitido" : "'+idConceptoOmitido+'"} , "folio": "'+folio+'"}}'; 
			var conceptoOmitido = jQuery.parseJSON(sConceptoOmitido);
			  $.postJSON("correccion/guardaConceptoOmitido.do", conceptoOmitido, function(dataa) {
				  var oTable = $(idDataConceptosC).dataTable(); 
				  oTable.fnDraw();
			}).error(function(dataa){ 
				(dataa);
			}).complete(function(){
				//Instrucciones para el 'complete'
			});
			  
			  var oTable = $(idDataConceptosC).dataTable(); 
			  oTable.fnDraw();
		}else{
			 var oTable = $(idDataConceptosC).dataTable(); 
			  oTable.fnDraw();
			alert('El concepto ya se encuentra en la lista');
			return false;
		}
		
		
	}).error(function(dataa){ 
		(dataa);
	}).complete(function(){
		//Instrucciones para el 'complete'
	});
	  
	
}

function validar(e, elemento) {
	
	
	  tecla = (document.all) ? e.keyCode : e.which;
	  if (tecla==13 || elemento.indexOf("dtp") != -1 ) {
		  if(document.getElementById(elemento).value.length > 0){
			  var idTipo = $('form#formCorre select#cgcCatTipo\\.idTipo').val(); 
			  var sReglaNegocio = '{"nombrecontrol":"'+elemento+'", "cgcCatTipo":{"idTipo":"'+idTipo+'"}}';
			  var reglaNegocio = jQuery.parseJSON(sReglaNegocio);
			  $.postJSON("correccion/consultaRegla.do", reglaNegocio, function(data) {
			  var hijos = data.hijos;
			  var dependencia = data.cveUsuario;
			  if(document.getElementById(dependencia) != null && document.getElementById(dependencia).value != ''){
				  hijos = data.nombreCampo
			  }
			  if(hijos != null){
				  var tokens = hijos.split(",");
			  	for(var i=0; i< tokens.length; i++){
			  		if(tokens[i]=='cbxAMotivoRechazo'){
			  			activaCombos('cbxAMotivoRechazo');
			  		}
			  		else if(elemento =='txtRPorcCR'){
			  			var razonabilidad = document.getElementById('txtRPorcCR').value;
			  				if(razonabilidad != '' && razonabilidad <= 9){
				  				activa('dtpRCCPR');
				  			}else{
				  				desactiva('dtpRCCPR');
				  			}
			  			
			  				
			  		}
			  		else{
			  			activa(tokens[i]);
			  		}
			  	}
			  }
			  var hijosIndependientes = data.hijosindependientes;
			  
			  if(hijosIndependientes!=null){
				  var itokens = hijosIndependientes.split(",");
				  for(var i=0; i< itokens.length; i++){
					  if(itokens[i]=='cbxAMotivoRechazo'){
				  			activaCombos('cbxAMotivoRechazo');
				  		}
				  		if(itokens[i]=='cbxGTipoObra'){
				  			activaCombos('CgcCatTipoObra\\.idTipoobra');
				  		}else{
					  		activa(itokens[i]);
				  		}
				  }
			  }
			}).error(function(data){ 
				
			}).complete(function(){
				//Instrucciones para el 'complete'
			});
		  
		  
	  }
	  }
	}

function validaFechasCGCorre(campoFecha, limiteInferior, limiteSuperior){
	var sFecha =  document.getElementById(campoFecha).value;
	if(sFecha!= ''){
		var sFechaMinima = '';
		var sFechaMaxima = '';
		if(limiteInferior != 'dtpFechaSistema' && limiteInferior != 'dtpPFechaMin'){
			var tokens = limiteInferior.split(',');
			if(tokens.length>1){
				if(document.getElementById(tokens[1]).value != ''){
					sFechaMinima = document.getElementById(tokens[1]).value;
				}else if(document.getElementById(tokens[0]).value != ''){
					sFechaMinima = document.getElementById(tokens[0]).value;
				}
			}else{
				sFechaMinima = document.getElementById(limiteInferior).value;
			}
		}
		if(limiteSuperior != 'dtpFechaSistema' && limiteSuperior != 'dtpPFechaMax'){
			sFechaMaxima = document.getElementById(limiteSuperior).value;
		}
		
		var hoy = new Date();
		var fecha = new Date();	
		var fechaMaxima = new Date();
		var fechaMinima = new Date();
		
		var anio = sFecha.substring(6,10);
		var mes = sFecha.substring(3,5) ;
		mes = mes - 1; 
		var dia = sFecha.substring(0,2);
		fecha.setFullYear(anio, mes, dia);
		
		if(sFechaMinima != ''){
			anio = sFechaMinima.substring(6,10);
			mes = sFechaMinima.substring(3,5);
			mes = mes - 1 ;
			dia = sFechaMinima.substring(0,2);
			fechaMinima.setFullYear(anio, mes, dia);
		}
		if(sFechaMaxima != ''){
			anio = sFechaMaxima.substring(6,10);
			mes = sFechaMaxima.substring(3,5);
			mes = mes - 1 ;
			dia = sFechaMaxima.substring(0,2);
			fechaMaxima.setFullYear(anio, mes, dia);
		}
		
		if(limiteSuperior == 'dtpPFechaMax'){
			if(!(fecha > fechaMinima || (fecha -0 == fechaMinima -0))){
				var d = sFechaMinima.substring(0,2);
				var m = sFechaMinima.substring(3,5);
			
				var a = sFechaMinima.substring(6,10);
				alert('La fecha no puede ser menor a ' + d +'/'+m+'/'+ a);
				$('input#'+campoFecha).prop("value", "");
				buscaElementoDes(campoFecha);
				return false;
			}
		}else if(limiteInferior == 'dtpPFechaMin'){
			var d = sFechaMaxima.substring(0,2);
			var m = sFechaMaxima.substring(3,5);
		
			var a = sFechaMaxima.substring(6,10);
			if(!(fecha < fechaMaxima || (fecha -0 == fechaMaxima -0))){
				alert('La fecha no puede ser menor a ' + d +'/'+m+'/'+ a);
				$('input#'+campoFecha).prop("value", "");
				buscaElementoDes(campoFecha);
				return false;
			}
		}
		
		else if( !((fecha > fechaMinima || (fecha -0 == fechaMinima -0)) && (fecha < fechaMaxima || (fecha - 0 == fechaMaxima -0))) ){
			if(fecha != ''){
				var dM = sFechaMaxima.substring(0,2);
				var mM = sFechaMaxima.substring(3,5);
			
				var aM = sFechaMaxima.substring(6,10);
				var d = sFechaMinima.substring(0,2);
				var m = sFechaMinima.substring(3,5);
			
				var a = sFechaMinima.substring(6,10);
				alert('La fecha no puede ser menor a ' + d +'/'+m+'/'+ a + ' o mayor a ' +  dM +'/'+mM+'/'+ aM);
				
			}
				
			$('input#'+campoFecha).prop("value", "");
			
			return false;
		}
	}
	return true;
}


function buscaElementoDes(elemento) {
	var idTipo = $('form#formCorre select#cgcCatTipo\\.idTipo').val(); 
	var sReglaNegocio = '{"nombrecontrol":"'+elemento+'", "cgcCatTipo":{"idTipo":"'+idTipo+'"}}';
	  var reglaNegocio = jQuery.parseJSON(sReglaNegocio);
	  //alert (reglaNegocio.id.nombrecontrol);
	  $.postJSON("consultaRegla.do", reglaNegocio, function(data) {
		  var hijos = data.hijos;
		  if(hijos!=null){
			  var tokens = hijos.split(",");
		  	for(var i=0; i< tokens.length; i++){
		  		 if(tokens[i]=='cbxGOrigen'){
		  			desactivaCombos('cbxGOrigen');
			  		}
		  		 else	if(tokens[i]=='cbxMotivoCancelacion'){
		  			desactivaCombos('cbxMotivoCancelacion');
			  		}
		  		 else	if(tokens[i]=='cbxAMotivoRechazo'){
		  			desactivaCombos('cbxAMotivoRechazo');
			  		}
		  		 else{
			  			desactiva(tokens[i]);
			  		}
		  		
		  	}
		  }
		  var hijosIndependientes = data.hijosindependientes;
		  
		  if(hijosIndependientes!=null){
			  var itokens = hijosIndependientes.split(",");
			  for(var i=0; i< itokens.length; i++){
				  desactiva(itokens[i]);
			  }
		  }
		}).error(function(data){ 
			
		}).complete(function(){
			//Instrucciones para el 'complete'
		});
	  
	  

}





function generaFolioInicial(){
	
	var subdelegacion = '';
	var delegacion = '';
	$.postJSON("correccion/obtenerDelegacion.do", '', function(data) {
		delegacion = data.cveCodigoDelegacion;
		subdelegacion = data.cveCodigoSubDelegacion;
		if(delegacion.length == 1){
			delegacion = '0' + delegacion;
		}
		if(subdelegacion.length == 1){
			subdelegacion = '0' + subdelegacion;
		}
	
	var idTipo = $('form#formCorre select#cgcCatTipo\\.idTipo').val(); 
	var idOrigen =$('form#formCorre select#cbxGOrigen').val(); 
	if(idOrigen != -1 && idOrigen != 0){
	var numFolio = delegacion +''+ subdelegacion + '/';
	if(idTipo == 1)
		numFolio = numFolio + 'CCE/';
	else if(idTipo == 2)
		numFolio = numFolio + 'CCI/';
	else if(idTipo == 3)
		numFolio = numFolio + 'CE/';
	else if(idTipo == 4)
		numFolio = numFolio + 'CI/';
	
	var fecha = new Date();
	var anio = fecha.getFullYear();
	numFolio = numFolio + anio + '/';
	document.getElementById('txtFolio').value = numFolio;
	buscaElemento('cbxGOrigen');
	
	
	
//	alert(folio);
	}	
	}).error(function(datas){ 
		(datas);
	}).complete(function(){
		//Instrucciones para el 'complete'
	});
	
}



function validaGuardadoAl(){
	if(confirm("Antes de Continuar es necesario GUARDAR el Registro Una vez Guardado no podr\u00e1 modificar los datos capturados hasta el momento. Desea Guardar el Registro?")){
		fnGuardarCorreccion();
	}else
		return false;
}



function generaFolioFinal(){
	var numFolio = $('input#txtFolio').val();
	var numero = 0;
	var subdelegacion = '01';
	var delegacion = '03'; 
	var fecha = new Date();
	var anio = fecha.getFullYear();
	var tokens = numFolio.split("/");
  	if(tokens.length==4){
  		if(tokens[3]==''){
  		 var sFolio = '{"cveDelegacion": "'+delegacion+'", "cveSubdelegacion": "'+subdelegacion+'", "numAnio" : "'+anio+'", "crcTipoCorr":{"cveTipocorr": "6" }}';
  		  var folio = jQuery.parseJSON(sFolio);
  		  //alert (reglaNegocio.id.nombrecontrol);
  		  $.postJSON("correccion/obtenerFolio.do", folio, function(data) {
  			  numFolio = numFolio + data[0].numNumero;
  			
  			  $('input#txtFolio').prop("value", numFolio);
  			  
  			  return  numFolio;
  			  
  			}).error(function(data){ 
  				
  			}).complete(function(){
  				//Instrucciones para el 'complete'
  			});
  	}
  		}
}








function validaGuardado(){
	 var guardado = document.getElementById('guardadoHidden').value; 
	 if(guardado == '1'){
		 $('select#cgcCatTipo\\.idTipo').prop("disabled", true);
		 $('select#cbxGOrigen').prop("disabled", true);
		 $('select#cbxOrigen').removeClass("red");
		 $('select#cbxGCriterioSeleccion').prop("disabled", true);
		 $('input#btnRPA').prop("disabled", false);
		 $('select#cbxGCriterioSeleccion').removeClass("red");
	 }else if(guardado == '0'){
		 $('select#cgcCatTipo\\.idTipo').prop("disabled", false);
		 $('select#cbxGOrigen').prop("disabled", false);
		 $('select#cbxGCriterioSeleccion').prop("disabled", false);
		 $('input#btnRPA').prop("disabled", true);
		 $('input#btnRPA').removeClass("red");
	 }
}

function validaOrigenPromocion(){
	if($('select#cbxGOrigen').val()==3)
		$('input#buscaOrigenPromocion').show("slow");
	else
		$('input#buscaOrigenPromocion').hide(100);
		
	
}

function muestraBotonAgrPatrones(){
	if($('select#cbxGOrigen').val()==3)
		$('input#buscaOrigenPromocion').show("slow");
	else
		$('input#buscaOrigenPromocion').hide(100);
}

function buscarPromociones(){
	alert('Buscar Promociones');
}

function abreFoliosPagos(){
	oDgFoliosPromocion.dialog('open');
}

function openDgAgregarRP(){
	oDgAgregaPatrones.dialog('open');
}

function openDgTrabajadores(opcion){
	if(opcion == 'A'){
		oDgAgregaTrabajadoresA.dialog('open');
	}	
	else if(opcion == 'R'){
		oDgAgregaTrabajadoresR.dialog('open');
	}
}

function openDgAgregarConceptos(){
	var pp = $('#chkCPresentoPagos').is(':checked');
	if(pp == false){
		oDgAgregaConceptos.dialog('open');	
	}else{
		oDgAgregaConceptosC.dialog('open');
	}
	
}


function openDgAgregarPagos(){
	if (document.getElementById('txtACOPConvSP').disabled == false && document.getElementById('txtARCVConvSP').disabled == false){
		if(document.getElementById('txtACOPConvSP').value == ''){
			alert('Debe introducir el monto de la SP Determinada de COP')
			return false;
		}
		if(document.getElementById('txtARCVConvSP').value == ''){
			alert('Debe introducir el monto de la SP Determinada de RCV')
			return false;
		}
	}
	oDgAgregaPagos.dialog('open');
}


function openDgAgregarPagosR(){
	if (document.getElementById('txtRCOPConvSP').disabled == false && document.getElementById('txtRRCVConvSP').disabled == false){
		if(document.getElementById('txtRCOPConvSP').value == ''){
			alert('Debe introducir el monto de la SP Determinada de COP')
			return false;
		}
		if(document.getElementById('txtRRCVConvSP').value == ''){
			alert('Debe introducir el monto de la SP Determinada de RCV')
			return false;
		}
	}
	oDgAgregaPagosR.dialog('open');
}

function llenaDatosPromocion(){
	
	var folio = $('input#radioTable').val();
	var sPromocion = '{' +
	'"folio": "'+folio+'"}';
	var promocion = jQuery.parseJSON(sPromocion);
	jQuery.ajax({
		async: false,
	    type: 'POST',
	    url: 'correccion/consultaPromocion.do',
	    data: sPromocion, // or JSON.stringify ({name: 'jonas'}),
	    success: function(data) { 

			var sCriterioSeleccion = '{"idCriterioseleccion": "'+data[0].cgtCatCriterioSeleccion.idCriterioseleccion+'"}';
			var criterioSeleccion = jQuery.parseJSON(sCriterioSeleccion);
			$.postJSON("correccion/consultaCriterios.do", criterioSeleccion, function(dataCrit) {
				 var options = "";
				 if(dataCrit!=null)
				   for (var i = 0; i < dataCrit.length; i++) {
			         options += "<option value='"+ dataCrit[i].idCriterioseleccion +"'>"+ dataCrit[i].descCriterioseleccion +"</option>";		     
			       }
				 $('select#cbxGCriterioSeleccion').html(options);
				 $('select#cbxGCriterioSeleccion').prop("disabled", true);
				 $('select#cbxGCriterioSeleccion').removeClass("red");
			}).error(function(dataCrit){ 
				(dataCrit);
			}).complete(function(){
				//Instrucciones para el 'complete'
			});
			
			
			if(confirm("Antes de Continuar es necesario GUARDAR el Registro Una vez Guardado no podr\u00e1 modificar los datos capturados hasta el momento. Desea Guardar el Registro?")){
				oDgFoliosPromocion.dialog('close');
				guardarCorreccionFromPromo
				desbloquear();
				var folioC =  $('input#txtFolio').val();
				document.getElementById('guardadoHidden').value = 1;
				validaGuardado();
				$('#txtGNoRPA').prop('value','1');
				$('#txtGRP').prop('value',data[0].cvePatron);
				$('#txtGRP').prop('disabled',false);
				$('#txtGRP').addClass("red");
				$('#txtGNombre').prop("value", data[0].nombre);
				$('#txtGNombre').prop('disabled',false);
				$('#txtGNombre').addClass("red");
				//buscaElemento('txtGNombre');
				if(data[0].afil15!=null){
					$('#txtAFIL15').prop("value", data[0].afil15);
					$('#txtAFIL15').prop('disabled',false);
					$('#txtAFIL15').addClass("red");
				
				}
				if(data[0].periododel!=null){
					$('#dtpGPeriodoDel').prop("value", desformateaFecha(data[0].periododel));
					$('#dtpGPeriodoDel').prop('disabled',false);
					$('#dtpGPeriodoDel').addClass("red");
					buscaElemento('dtpGPeriodoDel');
				}
				$('input#buscaOrigenPromocion').hide(100);
				if (data[0].periodoal!=null){
					$('#dtpGPeriodoAl').prop("value", desformateaFecha(data[0].periodoal));
					$('#dtpGPeriodoAl').prop('disabled',false);
					$('#dtpGPeriodoAl').addClass("red");
					buscaElemento('dtpGPeriodoAl');
				}
				if( data[0].oi!=null){
					$('#dtpAOI').prop("value", desformateaFecha(data[0].oi));
					$('#dtpAOI').prop('disabled',false);
					$('#dtpAOI').addClass("red");
					buscaElemento('dtpAOI');
				}
				if (data[0].sp!=null){
					$('#dtpASP').prop("value", desformateaFecha(data[0].sp));
					$('#dtpASP').prop('disabled',false);
					$('#dtpASP').addClass("red");
					buscaElemento('dtpASP');
				}
				cancelar();
		    	
				//agregaAnexoRP(folioC, data[0].cvePatron);
				alert('Se guardaron los datos correctamente');
				
			}else
				return false;
			
			
	    },
	    contentType: "application/json"
}).error(function(data){ 
	
}).complete(function(){
	//Instrucciones para el 'complete'
});
	
	
}

function cancelar(){
	oDgFoliosPromocion.dialog('close');
}

function agregaAnexoRP(folio , rp){
	var sAnexoRP = '{"id": {"folio" : "'+folio+'" , "rp" : "'+rp+'"}';
	var anexoRP = jQuery.parseJSON(sAnexoRP);
	$.postJSON("correccion/guardaAnexoRP.do", anexoRP, function(data) {
		 
	}).error(function(data){ 
		
	}).complete(function(){
		//Instrucciones para el 'complete'
	});
}


function validaConcepto(){
	var concepto = document.getElementById('concepto').value; 
	if(concepto == 'COP'){
		llenaComboPeriodoCOP();
		eliminaOpcionesSelect('form#formPagos select#periodoRCV');
		habilitaCOP(concepto, 'on');
		habilitaRCV(concepto, 'off');
		habilitaMultasCOP('MultaRCV', 'off');
		habilitaMultasRCV(concepto, 'off');
	}else if(concepto == 'RCV'){
		llenaComboPeriodoRCV();
		eliminaOpcionesSelect('form#formPagos select#periodoCOP');
		habilitaRCV(concepto, 'on');	
		habilitaCOP(concepto, 'off');
		habilitaMultasCOP('MultaRCV', 'off');
		habilitaMultasRCV(concepto, 'off');
	}else if(concepto == 'COPRCV'){
		llenaComboPeriodoCOP();
		llenaComboPeriodoRCV();
		habilitaCOP(concepto, 'on');
		habilitaRCV(concepto, 'on');
		habilitaMultasCOP('MultaRCV', 'off');
		habilitaMultasRCV(concepto, 'off');
	}
	else if(concepto == 'MultaCOP'){
		habilitaMultasCOP(concepto, 'on');
		habilitaMultasRCV(concepto, 'off');
		habilitaCOP(concepto, 'off');
		habilitaRCV(concepto, 'off');
	}
	else if(concepto == 'MultaRCV'){
		habilitaMultasCOP('MultaRCV', 'off');
		habilitaMultasRCV(concepto, 'on');
		habilitaCOP(concepto, 'off');
		habilitaRCV(concepto, 'off');
	}
	else if(concepto == 'MultaCOPRCV'){
		habilitaMultasCOP('MultaCOPRCV', 'on');
		habilitaMultasRCV('MultaCOPRCV', 'on');
		habilitaCOP(concepto, 'off');
		habilitaRCV(concepto, 'off');
	}else{
		eliminaOpcionesSelect('form#formPagos select#periodoCOP');
		eliminaOpcionesSelect('form#formPagos select#periodoRCV');
		habilitaCOP(concepto, 'off');
		habilitaRCV(concepto, 'off');
		habilitaMultasCOP(concepto, 'off');
		habilitaMultasRCV(concepto, 'off');
	}
	
}


function validaConceptoR(){
	var concepto = document.getElementById('conceptoR').value; 
	if(concepto == 'COP'){
		llenaComboPeriodoCOPR();
		eliminaOpcionesSelect('form#formPagosR select#periodoRCVR');
		habilitaCOPR(concepto, 'on');
		habilitaRCVR(concepto, 'off');
		habilitaMultasCOPR('MultaRCV', 'off');
		habilitaMultasRCVR(concepto, 'off');
	}else if(concepto == 'RCV'){
		llenaComboPeriodoRCVR();
		eliminaOpcionesSelect('form#formPagosR select#periodoCOPR');
		habilitaRCVR(concepto, 'on');	
		habilitaCOPR(concepto, 'off');
		habilitaMultasCOPR('MultaRCV', 'off');
		habilitaMultasRCVR(concepto, 'off');
	}else if(concepto == 'COPRCV'){
		llenaComboPeriodoCOPR();
		llenaComboPeriodoRCVR();
		habilitaCOPR(concepto, 'on');
		habilitaRCVR(concepto, 'on');
		habilitaMultasCOPR('MultaRCV', 'off');
		habilitaMultasRCVR(concepto, 'off');
	}
	else if(concepto == 'MultaCOPR'){
		habilitaMultasCOPR(concepto, 'on');
		habilitaMultasRCVR(concepto, 'off');
		habilitaCOPR(concepto, 'off');
		habilitaRCVR(concepto, 'off');
	}
	else if(concepto == 'MultaRCVR'){
		habilitaMultasCOPR('MultaRCVR', 'off');
		habilitaMultasRCVR(concepto, 'on');
		habilitaCOPR(concepto, 'off');
		habilitaRCVR(concepto, 'off');
	}
	else if(concepto == 'MultaCOPRCV'){
		habilitaMultasCOPR('MultaCOPRCV', 'on');
		habilitaMultasRCVR('MultaCOPRCV', 'on');
		habilitaCOPR(concepto, 'off');
		habilitaRCVR(concepto, 'off');
	}else{
		eliminaOpcionesSelect('form#formPagosR select#periodoCOPR');
		eliminaOpcionesSelect('form#formPagosR select#periodoRCVR');
		habilitaCOPR(concepto, 'off');
		habilitaRCVR(concepto, 'off');
		habilitaMultasCOPR(concepto, 'off');
		habilitaMultasRCVR(concepto, 'off');
	}
	
}

function habilitaCOP(concepto, opcion){
	if(opcion == 'on'){
		$('form#formPagos input#txtCOPSP' ).prop("disabled", false);
		$('form#formPagos input#txtCOPAct' ).prop("disabled", false);
		$('form#formPagos input#txtCOPRec' ).prop("disabled", false);
		$('form#formPagos input#txtCOPSP' ).addClass("red");
		$('form#formPagos input#txtCOPAct' ).addClass("red");
		$('form#formPagos input#txtCOPRec' ).addClass("red");
	}else{
		$('form#formPagos input#txtCOPSP' ).prop("disabled", true);
		$('form#formPagos input#txtCOPAct' ).prop("disabled", true);
		$('form#formPagos input#txtCOPRec' ).prop("disabled", true);
		$('form#formPagos input#txtCOPSP' ).removeClass("red");
		$('form#formPagos input#txtCOPAct' ).removeClass("red");
		$('form#formPagos input#txtCOPRec' ).removeClass("red");
	}
	
}

function habilitaCOPR(concepto, opcion){
	if(opcion == 'on'){
		$('form#formPagosR input#txtCOPSPR' ).prop("disabled", false);
		$('form#formPagosR input#txtCOPActR' ).prop("disabled", false);
		$('form#formPagosR input#txtCOPRecR' ).prop("disabled", false);
		$('form#formPagosR input#txtCOPSPR' ).addClass("red");
		$('form#formPagosR input#txtCOPActR' ).addClass("red");
		$('form#formPagosR input#txtCOPRecR' ).addClass("red");
	}else{
		$('form#formPagosR input#txtCOPSPR' ).prop("disabled", true);
		$('form#formPagosR input#txtCOPActR' ).prop("disabled", true);
		$('form#formPagosR input#txtCOPRecR' ).prop("disabled", true);
		$('form#formPagosR input#txtCOPSPR' ).removeClass("red");
		$('form#formPagosR input#txtCOPActR' ).removeClass("red");
		$('form#formPagosR input#txtCOPRecR' ).removeClass("red");
	}
	
}

function habilitaRCV(concepto, opcion){
	if(opcion == 'on'){
		$('form#formPagos input#txtRCVSP' ).prop("disabled", false);
		$('form#formPagos input#txtRCVAct' ).prop("disabled", false);
		$('form#formPagos input#txtRCVRec' ).prop("disabled", false);
		$('form#formPagos input#txtRCVSP' ).addClass("red");
		$('form#formPagos input#txtRCVAct' ).addClass("red");
		$('form#formPagos input#txtRCVRec' ).addClass("red");
	}else{
		$('form#formPagos input#txtRCVSP' ).prop("disabled", true);
		$('form#formPagos input#txtRCVAct' ).prop("disabled", true);
		$('form#formPagos input#txtRCVRec' ).prop("disabled", true);
		$('form#formPagos input#txtRCVSP' ).removeClass("red");
		$('form#formPagos input#txtRCVAct' ).removeClass("red");
		$('form#formPagos input#txtRCVRec' ).removeClass("red");
		
	}
	
}

function habilitaRCVR(concepto, opcion){
	if(opcion == 'on'){
		$('form#formPagosR input#txtRCVSPR' ).prop("disabled", false);
		$('form#formPagosR input#txtRCVActR' ).prop("disabled", false);
		$('form#formPagosR input#txtRCVRecR' ).prop("disabled", false);
		$('form#formPagosR input#txtRCVSPR' ).addClass("red");
		$('form#formPagosR input#txtRCVActR' ).addClass("red");
		$('form#formPagosR input#txtRCVRecR' ).addClass("red");
	}else{
		$('form#formPagosR input#txtRCVSPR' ).prop("disabled", true);
		$('form#formPagosR input#txtRCVActR' ).prop("disabled", true);
		$('form#formPagosR input#txtRCVRecR' ).prop("disabled", true);
		$('form#formPagosR input#txtRCVSPR' ).removeClass("red");
		$('form#formPagosR input#txtRCVActR' ).removeClass("red");
		$('form#formPagosR input#txtRCVRecR' ).removeClass("red");
		
	}
	
}

function habilitaMultasCOP(concepto, opcion){
		if(opcion == 'on'){
			$('form#formPagos input#txtCOPMultas' ).prop("disabled", false);
			$('form#formPagos input#txtCOPMultas' ).addClass("red");
		}
		else if(opcion == 'off'){
			$('form#formPagos input#txtCOPMultas' ).prop("disabled", true);
			$('form#formPagos input#txtCOPMultas' ).removeClass("red");
		}
	}

function habilitaMultasCOPR(concepto, opcion){
	if(opcion == 'on'){
		$('form#formPagosR input#txtCOPMultasR' ).prop("disabled", false);
		$('form#formPagosR input#txtCOPMultasR' ).addClass("red");
	}
	else if(opcion == 'off'){
		$('form#formPagosR input#txtCOPMultasR' ).prop("disabled", true);
		$('form#formPagosR input#txtCOPMultasR' ).removeClass("red");
	}
}

function habilitaMultasRCV(concepto, opcion){
	if(opcion == 'on'){
		$('form#formPagos input#txtRCVMultas' ).prop("disabled", false);
		$('form#formPagos input#txtRCVMultas' ).addClass("red");
	}
	else if(opcion == 'off'){
		$('form#formPagos input#txtRCVMultas' ).prop("disabled", true);
		$('form#formPagos input#txtRCVMultas' ).removeClass("red");
	}
}


function habilitaMultasRCVR(concepto, opcion){
	if(opcion == 'on'){
		$('form#formPagosR input#txtRCVMultasR' ).prop("disabled", false);
		$('form#formPagosR input#txtRCVMultasR' ).addClass("red");
	}
	else if(opcion == 'off'){
		$('form#formPagosR input#txtRCVMultasR' ).prop("disabled", true);
		$('form#formPagosR input#txtRCVMultasR' ).removeClass("red");
	}
}

function llenaComboPeriodoCOP(){
	var fechaInicio =  $('input#dtpGPeriodoDel').val();
	var fechaFin = $('input#dtpGPeriodoAl').val(); 
	var sParam =  '["'+fechaInicio+'","'+fechaFin+'"]';
	 var param = jQuery.parseJSON(sParam);
	$.postJSON("correccion/armaPeriodos.do", param, function(dataC) {
		 var options = "<option value='' >--Por favor seleccione--</option>";
		 if(dataC!=null)
		   for (var i = 0; i < dataC.length; i++) {
	         options += "<option value='"+ dataC[i] +"'>"+ dataC[i] +"</option>";		     
	       }
		 $('form#formPagos select#periodoCOP').html(options);
		 $('form#formPagos select#periodoCOP').addClass("red");
	  }).error(function(dataC){ 
		  (dataC);
	  }).complete(function(){
		//Instrucciones para el 'complete'
	  });
}

function llenaComboPeriodoCOPR(){
	var fechaInicio =  $('input#dtpGPeriodoDel').val();
	var fechaFin = $('input#dtpGPeriodoAl').val(); 
	var sParam =  '["'+fechaInicio+'","'+fechaFin+'"]';
	 var param = jQuery.parseJSON(sParam);
	$.postJSON("correccion/armaPeriodos.do", param, function(dataC) {
		 var options = "<option value='' >--Por favor seleccione--</option>";
		 if(dataC!=null)
		   for (var i = 0; i < dataC.length; i++) {
	         options += "<option value='"+ dataC[i] +"'>"+ dataC[i] +"</option>";		     
	       }
		 $('form#formPagosR select#periodoCOPR').html(options);
		 $('form#formPagosR select#periodoCOPR').addClass("red");
	  }).error(function(dataC){ 
		  (dataC);
	  }).complete(function(){
		//Instrucciones para el 'complete'
	  });
}

function llenaComboPeriodoRCV(){
	var fechaInicio =  $('input#dtpGPeriodoDel').val();
	var fechaFin = $('input#dtpGPeriodoAl').val(); 
	var sParam =  '["'+fechaInicio+'","'+fechaFin+'"]';
	 var param = jQuery.parseJSON(sParam);
	$.postJSON("correccion/armaBimestres.do", param, function(dataC) {
		 var options = "<option value='' >--Por favor seleccione--</option>";
		 if(dataC!=null)
		   for (var i = 0; i < dataC.length; i++) {
	         options += "<option value='"+ dataC[i] +"'>"+ dataC[i] +"</option>";		     
	       }
		 $('form#formPagos select#periodoRCV').html(options);
		 $('form#formPagos select#periodoRCV').addClass("red");
	  }).error(function(dataC){ 
		  (dataC);
	  }).complete(function(){
		//Instrucciones para el 'complete'
	  });
	
	
}


function llenaComboPeriodoRCVR(){
	var fechaInicio =  $('input#dtpGPeriodoDel').val();
	var fechaFin = $('input#dtpGPeriodoAl').val(); 
	var sParam =  '["'+fechaInicio+'","'+fechaFin+'"]';
	 var param = jQuery.parseJSON(sParam);
	$.postJSON("correccion/armaBimestres.do", param, function(dataC) {
		 var options = "<option value='' >--Por favor seleccione--</option>";
		 if(dataC!=null)
		   for (var i = 0; i < dataC.length; i++) {
	         options += "<option value='"+ dataC[i] +"'>"+ dataC[i] +"</option>";		     
	       }
		 $('form#formPagosR select#periodoRCVR').html(options);
		 $('form#formPagosR select#periodoRCVR').addClass("red");
	  }).error(function(dataC){ 
		  (dataC);
	  }).complete(function(){
		//Instrucciones para el 'complete'
	  });
	
	
}
function llenaComboPatrones(numFolio){
	 var sFolio = '{"folio": "'+numFolio+'" }}';
	  var folio = jQuery.parseJSON(sFolio);
	 $.postJSON("correccion/consultaAnexoPagos.do", folio, function(data) {
		 var options = "<option value='' >--Por favor seleccione--</option>";
		 if(data!=null)
		   for (var i = 0; i < data.length; i++) {
	         options += "<option value='"+ data[i].idPago +"'>"+ data[i].cvePatron +"</option>";		     
	       }
		 $('form#formCorre select#regpat').html(options);
		//Agrega las opciones al control
	}).error(function(data){ 
		
	}).complete(function(){
		//Instrucciones para el 'complete'
	});

}




function desactiva(elemento){
	//--Por favor seleccione--
	var idSeleccion = $('form#formCorre select#cbxGCriterioSeleccion').val(); 
	if(idSeleccion != -1 && idSeleccion!=0){
		if( elemento.indexOf("cmd") == -1){
			$('form#formCorre input#'+elemento ).prop("value", "");
		}
		 
		$('form#formCorre input#'+elemento ).prop("disabled", true);
		$('form#formCorre input#'+elemento ).removeClass("red");
		 
		
	}
}




function verificaNivel(elemento) {
	var idTipo = $('select#cgcCatTipo\\.idTipo').val(); 
	var sReglaNegocio = '{"nombrecontrol":"'+elemento+'", "cgcCatTipo":{"idTipo":"'+idTipo+'"}}';
	var reglaNegocio = jQuery.parseJSON(sReglaNegocio);
	$.postJSON("correccion/consultaRegla.do", reglaNegocio, function(data) {
		var nivel = '';  
		if(data!=null && data.nivel != null && data.nivel != ''){
				nivel = data.nivel;
		  }
		   if(nivel != null && nivel != ''){
			  var sReglaNegocioNivel = '{"nivel":"'+nivel +'" , "nombrecontrol":"'+elemento+'", "cgcCatTipo":{"idTipo":"'+idTipo+'"}}';
			  var reglaNegocioNivel = jQuery.parseJSON(sReglaNegocioNivel);
			  $.postJSON("correccion/consultaReglaNivel.do", reglaNegocioNivel, function(dataNivel) {
				  for(i=0;i<dataNivel.length; i++){
					  if(document.getElementById(elemento).value=='' )
						  activa(dataNivel[i].nombrecontrol);
					  else
						  desactiva(dataNivel[i].nombrecontrol);
				  }
				  }).error(function(dataNivel){ 
					  (dataNivel);
				}).complete(function(){
					//Instrucciones para el 'complete'
				});
			 }
		  	}).error(function(data){ 
		  		
		}).complete(function(){
			//Instrucciones para el 'complete'
		});
}


function verificaNivelAdicional(elemento, campo) {
	var idTipo = $('select#cgcCatTipo\\.idTipo').val(); 
	var sReglaNegocio = '{"nombrecontrol":"'+elemento+'", "cgcCatTipo":{"idTipo":"'+idTipo+'"}}';
	var reglaNegocio = jQuery.parseJSON(sReglaNegocio);
	$.postJSON("correccion/consultaRegla.do", reglaNegocio, function(data) {
		var nivel = '';  
		if(data!=null && data.nivel != null && data.nivel != ''){
				nivel = data.nivel;
		  }
		   if(nivel != null && nivel != ''){
			  var sReglaNegocioNivel = '{"nivel":"'+nivel +'" , "nombrecontrol":"'+elemento+'", "cgcCatTipo":{"idTipo":"'+idTipo+'"}}';
			  var reglaNegocioNivel = jQuery.parseJSON(sReglaNegocioNivel);
			  $.postJSON("correccion/consultaReglaNivel.do", reglaNegocioNivel, function(dataNivel) {
				  for(i=0;i<dataNivel.length; i++){
					  if(document.getElementById(elemento).value=='' )
						  activa(dataNivel[i].nombrecontrol);
					  else {
						  if(document.getElementById(dataNivel[i].nombrecontrol).value=='')
							  desactiva(dataNivel[i].nombrecontrol);
					  }
						  
				  }
				  
				  if(document.getElementById(elemento).value=='' )
					  activa(campo);
				  else{
					  if(document.getElementById(elemento).value!='' )
						  if(document.getElementById(campo).value=='')
							  desactiva(campo);
				  }
					  
					  
				  }).error(function(dataNivel){ 
					  (dataNivel);
				}).complete(function(){
					//Instrucciones para el 'complete'
				});
			 }
		  	}).error(function(data){ 
		  		
		}).complete(function(){
			//Instrucciones para el 'complete'
		});
}

function verificaNivelExenta(elemento, campos) {
	var idTipo = $('select#cgcCatTipo\\.idTipo').val(); 
	var sReglaNegocio = '{"nombrecontrol":"'+elemento+'", "cgcCatTipo":{"idTipo":"'+idTipo+'"}}';
	var reglaNegocio = jQuery.parseJSON(sReglaNegocio);
	var token = campos.split(",");
	$.postJSON("correccion/consultaRegla.do", reglaNegocio, function(data) {
		var nivel = '';  
		if(data!=null && data.nivel != null && data.nivel != ''){
				nivel = data.nivel;
		  }
		   if(nivel != null && nivel != ''){
			  var sReglaNegocioNivel = '{"nivel":"'+nivel +'" , "nombrecontrol":"'+elemento+'", "cgcCatTipo":{"idTipo":"'+idTipo+'"}}';
			  var reglaNegocioNivel = jQuery.parseJSON(sReglaNegocioNivel);
			  $.postJSON("correccion/consultaReglaNivel.do", reglaNegocioNivel, function(dataNivel) {
				  for(i=0;i<dataNivel.length; i++){
					  
					  
						  
						  if(document.getElementById(elemento).value=='' ){
							  var bandera = 0;
							  for(j=0; j<token.length; j++){
								  if(token[j]==dataNivel[i].nombrecontrol){
									  	bandera = 1;
								  } 
  
							  }

							  if(bandera == 0){
								  activa(dataNivel[i].nombrecontrol);
	  							}				  }
						  
						  else{
							  
							  if(document.getElementById(elemento).value!=''){
								  var bandera = 0;
								  for(j=0; j<token.length; j++){
									  if(token[j]==dataNivel[i].nombrecontrol){
				  							bandera = 1;
			  					 	}
			  							
								  }
								  if(bandera == 0){
		  								desactiva(dataNivel[i].nombrecontrol);
		  							}
							  }
						  }
					  
						  
				  }
				  }).error(function(dataNivel){ 
					  (dataNivel);
				}).complete(function(){
					//Instrucciones para el 'complete'
				});
			 }
		  	}).error(function(data){ 
		  		
		}).complete(function(){
			//Instrucciones para el 'complete'
		});
	 
}

function verificaNivelEspecial(elemento, nivel1, nivel2) {
	var idTipo = $('select#cgcCatTipo\\.idTipo').val(); 
	var sReglaNegocio = '{"nombrecontrol":"'+elemento+'", "cgcCatTipo":{"idTipo":"'+idTipo+'"}}';
	var reglaNegocio = jQuery.parseJSON(sReglaNegocio);
	$.postJSON("correccion/consultaRegla.do", reglaNegocio, function(data) {
		var nivel = '';  
		if(data!=null && data.nivel != null && data.nivel != ''){
				nivel = data.nivel;
		  }
		   if(nivel != null && nivel != ''){
			  var sReglaNegocioNivel = '{"nivel":"'+nivel +'" , "nombrecontrol":"'+elemento+'", "cgcCatTipo":{"idTipo":"'+idTipo+'"}}';
			  var reglaNegocioNivel = jQuery.parseJSON(sReglaNegocioNivel);
			  $.postJSON("correccion/consultaReglaNivel.do", reglaNegocioNivel, function(dataNivel) {
				  for(i=0;i<dataNivel.length; i++){
					  if(document.getElementById(elemento).value=='' )
						  if(document.getElementById(nivel1).value==''){
							  activa(dataNivel[i].nombrecontrol);
						  }
					  else{
						  if(document.getElementById(elemento).value==''){
							  if(document.getElementById(nivel1).value==''){
								  desactiva(dataNivel[i].nombrecontrol);  
							  }
							  
						  }
							  
					  }
						  
				  }
				  }).error(function(dataeNivel){ 
					  (dataNivel);
				}).complete(function(){
					//Instrucciones para el 'complete'
				});
			 }
		  	}).error(function(data){ 
		  		
		}).complete(function(){
			//Instrucciones para el 'complete'
		});
	 
}

function validarFolioSUA(e, hab, inhab) {
	var numTrabs = $('form#formCorre input#txtATrabRegularizados').val();
	 tecla = (document.all) ? e.keyCode : e.which;
	  if (tecla==13 ) {
		  if(document.getElementById(hab).value == '' ){
			  if(inhab == 'txtOrdenIngreso'){
				  if((document.getElementById(hab).value == '' || document.getElementById(hab).value == 0) && (numTrabs == null || numTrabs == '' || numTrabs == 0)){
					  $('form#formPagos input#'+inhab ).prop("disabled", false);
					  $('form#formPagos input#'+inhab ).addClass("red");
				  }
			  }else{
				  $('form#formPagos input#'+inhab ).prop("disabled", false);
				  $('form#formPagos input#'+inhab ).addClass("red");
			  }
			  
			  
		  }else{
			  $('form#formPagos input#'+inhab ).prop("disabled", true);
			  $('form#formPagos input#'+inhab ).removeClass("red");
		  }
		   

	  }  
	}

function validaFolioSUA(hab, inhab) {
	//F7028494100
		  var numTrabs = $('form#formCorre input#txtATrabRegularizados').val();
		  if(document.getElementById(hab).value == ''){
			  if(inhab == 'txtOrdenIngreso'){
				  if((document.getElementById(hab).value == '' || document.getElementById(hab).value == 0) && (numTrabs == null || numTrabs == '' || numTrabs == 0)){
					  $('form#formPagos input#'+inhab ).prop("disabled", false);
					  $('form#formPagos input#'+inhab ).addClass("red");
				  }
			  }else{
				  $('form#formPagos input#'+inhab ).prop("disabled", false);
				  $('form#formPagos input#'+inhab ).addClass("red");
			  }
			  
			  
		  }else{
			  $('form#formPagos input#'+inhab ).prop("disabled", true);
			  $('form#formPagos input#'+inhab ).removeClass("red");
		  }
		  

	    
	}


function validarFolioSUAR(e, hab, inhab) {
	var numTrabs = $('form#formCorre input#txtRTrabRegularizados').val();
	 tecla = (document.all) ? e.keyCode : e.which;
	  if (tecla==13 ) {
		  if(document.getElementById(hab).value == '' ){
			  if(inhab == 'txtOrdenIngresoR'){
				  if((document.getElementById(hab).value == '' || document.getElementById(hab).value == 0) && (numTrabs == null || numTrabs == '' || numTrabs == 0)){
					  $('form#formPagosR input#'+inhab ).prop("disabled", false);
					  $('form#formPagosR input#'+inhab ).addClass("red");
				  }
			  }else{
				  $('form#formPagosR input#'+inhab ).prop("disabled", false);
				  $('form#formPagosR input#'+inhab ).addClass("red");
			  }
			  
			  
		  }else{
			  $('form#formPagosR input#'+inhab ).prop("disabled", true);
			  $('form#formPagosR input#'+inhab ).removeClass("red");
		  }
		   

	  }  
	}

function validaFolioSUAR(hab, inhab) {
	//F7028494100
		  var numTrabs = $('form#formCorre input#txtRTrabRegularizados').val();
		  if(document.getElementById(hab).value == ''){
			  if(inhab == 'txtOrdenIngresoR'){
				  if((document.getElementById(hab).value == '' || document.getElementById(hab).value == 0) && (numTrabs == null && numTrabs == '' && numTrabs == 0)){
					  $('form#formPagosR input#'+inhab ).prop("disabled", false);
					  $('form#formPagosR input#'+inhab ).addClass("red");
				  }
			  }else{
				  $('form#formPagosR input#'+inhab ).prop("disabled", false);
				  $('form#formPagosR input#'+inhab ).addClass("red");
			  }
			  
			  
		  }else{
			  $('form#formPagosR input#'+inhab ).prop("disabled", true);
			  $('form#formPagosR input#'+inhab ).removeClass("red");
		  }
		  

	    
	}
function suma(e, concepto) {
	//F7028494100
	  tecla = (document.all) ? e.keyCode : e.which;
	  if (tecla==13 ) {
		  var SP = 0.00;
		  var Act = 0.00;
		  var Rec = 0.00;
		  var total = 0.00;
		  if($("#txt"+ concepto + "SP").asNumber() == '' || isNaN($("#txt"+ concepto + "SP").asNumber())){
			  SP = 0.00;
			  document.getElementById("txt"+ concepto + "SP").value = '0.00';
		  }else{
		 	  SP =$("#txt"+ concepto + "SP").asNumber();
		  }
		  if($("#txt"+ concepto + "Rec").asNumber() == '' || isNaN($("#txt"+ concepto + "Rec").asNumber())){
			  Rec = 0.00;
			  document.getElementById("txt"+ concepto + "Rec").value = '0.00';
		  }else{
			  Rec = $("#txt"+ concepto + "Rec").asNumber()
		  }
		  if($("#txt"+ concepto + "Act").asNumber() == '' || isNaN($("#txt"+ concepto + "Act").asNumber())){
			Act = 0.00;
			document.getElementById("txt"+ concepto + "Act").value = '0.00';
		  }else{
			  Act = $("#txt"+ concepto + "Act").asNumber()
		  }
					
			
					
			
			  
			  total = parseFloat(SP) + parseFloat(Act) + parseFloat(Rec);
			  document.getElementById("txt"+ concepto + "Total").value = total;
			  $('form#formPagos input#txt'+ concepto + 'Total').formatCurrency();
	  }  
	}

function sumaR(e, concepto) {
	//F7028494100
	  tecla = (document.all) ? e.keyCode : e.which;
	  if (tecla==13 ) {
		  var SP = 0.00;
		  var Act = 0.00;
		  var Rec = 0.00;
		  var total = 0.00;
		  if($("#txt"+ concepto + "SPR").asNumber() == '' || isNaN($("#txt"+ concepto + "SPR").asNumber())){
			  SP = 0.00;
			  document.getElementById("txt"+ concepto + "SPR").value = '0.00';
		  }else{
		 	  SP =$("#txt"+ concepto + "SPR").asNumber();
		  }
		  if($("#txt"+ concepto + "RecR").asNumber() == '' || isNaN($("#txt"+ concepto + "RecR").asNumber())){
			  Rec = 0.00;
			  document.getElementById("txt"+ concepto + "RecR").value = '0.00';
		  }else{
			  Rec = $("#txt"+ concepto + "RecR").asNumber()
		  }
		  if($("#txt"+ concepto + "ActR").asNumber() == '' || isNaN($("#txt"+ concepto + "ActR").asNumber())){
			Act = 0.00;
			document.getElementById("txt"+ concepto + "ActR").value = '0.00';
		  }else{
			  Act = $("#txt"+ concepto + "ActR").asNumber()
		  }
					
			
					
			
			  
			  total = parseFloat(SP) + parseFloat(Act) + parseFloat(Rec);
			  document.getElementById("txt"+ concepto + "TotalR").value = total;
			  $('form#formPagosR input#txt'+ concepto + 'TotalR').formatCurrency();
	  }  
	}


function sumar(concepto) {
	//F7028494100
	  
	    var SP = 0.00;
		  var Act = 0.00;
		  var Rec = 0.00;
		  var total = 0.00;
		  if($("#txt"+ concepto + "SP").asNumber() == '' || isNaN($("#txt"+ concepto + "SP").asNumber())){
			  SP = 0.00;
			  document.getElementById("txt"+ concepto + "SP").value = '0.00';
		  }else{
		 	  SP =$("#txt"+ concepto + "SP").asNumber();
		  }
		  if($("#txt"+ concepto + "Rec").asNumber() == '' || isNaN($("#txt"+ concepto + "Rec").asNumber())){
			  Rec = 0.00; 
			  document.getElementById("txt"+ concepto + "Rec").value = '0.00';
		  }else{
			  Rec = $("#txt"+ concepto + "Rec").asNumber()
		  }
		  if($("#txt"+ concepto + "Act").asNumber() == '' || isNaN($("#txt"+ concepto + "Act").asNumber())){
			Act = 0.00;
			document.getElementById("txt"+ concepto + "Act").value = '0.00';
		  }else{
			  Act = $("#txt"+ concepto + "Act").asNumber()
		  }
					
			
			  
			  total = parseFloat(SP) + parseFloat(Act) + parseFloat(Rec);
			  document.getElementById("txt"+ concepto + "Total").value = total;
			  $('form#formPagos input#txt'+ concepto + 'Total').formatCurrency();
	    
	}

function sumarR(concepto) {
	//F7028494100
	  
	    var SP = 0.00;
		  var Act = 0.00;
		  var Rec = 0.00;
		  var total = 0.00;
		  if($("#txt"+ concepto + "SPR").asNumber() == '' || isNaN($("#txt"+ concepto + "SPR").asNumber())){
			  SP = 0.00;
			  document.getElementById("txt"+ concepto + "SPR").value = '0.00';
		  }else{
		 	  SP =$("#txt"+ concepto + "SPR").asNumber();
		  }
		  if($("#txt"+ concepto + "RecR").asNumber() == '' || isNaN($("#txt"+ concepto + "RecR").asNumber())){
			  Rec = 0.00; 
			  document.getElementById("txt"+ concepto + "RecR").value = '0.00';
		  }else{
			  Rec = $("#txt"+ concepto + "RecR").asNumber()
		  }
		  if($("#txt"+ concepto + "ActR").asNumber() == '' || isNaN($("#txt"+ concepto + "ActR").asNumber())){
			Act = 0.00;
			document.getElementById("txt"+ concepto + "ActR").value = '0.00';
		  }else{
			  Act = $("#txt"+ concepto + "ActR").asNumber()
		  }
					
			
			  
			  total = parseFloat(SP) + parseFloat(Act) + parseFloat(Rec);
			  document.getElementById("txt"+ concepto + "TotalR").value = total;
			  $('form#formPagos input#txt'+ concepto + 'TotalR').formatCurrency();
	    
	}

function validarCamposOn(e, hab, inhab) {
	//F7028494100
	  tecla = (document.all) ? e.keyCode : e.which;
	  if (tecla==13 ) {
		  if(document.getElementById(hab).value == ''){
			  $('input#'+inhab ).prop("disabled", true);
			  $('input#'+inhab ).addClass("red");
		  }else{
			  $('input#'+inhab ).prop("disabled", false);
			  $('input#'+inhab ).removeClass("red");
			  
		  }
		  

	  }  
	}


function validarCamposOns(hab, inhab) {
	//F7028494100
	  
		  if(document.getElementById(hab).value == ''){
			  $('input#'+inhab ).prop("disabled", true);
			  $('input#'+inhab ).removeClass("red");
		  }else{
			  $('input#'+inhab ).prop("disabled", false);
			  $('input#'+inhab ).addClass("red");
		  }
		  

	    
	}
function guardaAnexoPagos(){
	
	
	
	
}

function sumariza(elemento, valor){
	$('form#formCorre input#'+elemento).prop('disabled', false);
	 $('form#formCorre input#'+elemento).prop('value', valor);
	 $('form#formCorre input#'+elemento).prop('readonly', true);
}


function llenaComboOrigen(){
	var idTipo = $('select#cgcCatTipo\\.idTipo').val();
	if(idTipo!= -1){
	 $.postJSON("correccion/consultaOrigen.do", idTipo, function(data) {
		 var options = "<option value='' >--Por favor seleccione--</option>";
		 if(data!=null)
		   for (var i = 0; i < data.length; i++) {
	         options += "<option value='"+ data[i].cgcCatOrigen.idOrigen +"'>"+ data[i].cgcCatOrigen.descOrigen +"</option>";		     
	       }
		 $('select#cbxGOrigen').prop("disabled", false);
		 $('select#cbxGOrigen').html(options);
		//Agrega las opciones al control
	}).error(function(data){ 
		
	}).complete(function(){
		//Instrucciones para el 'complete'
	});
	}else{
		$('select#cbxGOrigen').html('');
		$('select#cbxGOrigen').html("<option value='-1'>--Por favor seleccione--</option>");
		 $('select#cbxGOrigen').prop("disabled", false);
	}

}

function llenaCriterioSeleccion(){
	var idTipo = $('select#cgcCatTipo\\.idTipo').val();
	var idOrigen = $('select#cbxGOrigen').val();
	var sParam =  '["'+idTipo+'","'+idOrigen+'"]';
	 var param = jQuery.parseJSON(sParam);
	if(idTipo!= -1 && idOrigen != ''){
	 $.postJSON("correccion/consultaComboCriterios.do", param, function(data) {
		 var options = "<option value='' >--Por favor seleccione--</option>";
		 if(data!=null)
		   for (var i = 0; i < data.length; i++) {
	         options += "<option value='"+ data[i].idCriterioseleccion +"'>"+ data[i].descCriterioseleccion +"</option>";		     
	       }
		 $('select#cbxGCriterioSeleccion').prop("disabled", false);
		 $('select#cbxGCriterioSeleccion').html(options);
		//Agrega las opciones al control
	}).error(function(data){ 
		
	}).complete(function(){
		//Instrucciones para el 'complete'
	});
	}else{
		$('select#cbxGCriterioSeleccion').html('');
		$('select#cbxGCriterioSeleccion').html("<option value='-1'>--Por favor seleccione--</option>");
		 $('select#cbxGCriterioSeleccion').prop("disabled", true);
	}

}


function desabilitaForma(){
	$('#formCorre :input:text').prop("value", "");
	$('#formCorre :input:text').prop("disabled", true);
	 $('#formCorre :input:text').removeClass("red");
	 $('#formCorre input#cmdGuardar').removeClass("red");
	 $('#formCorre input#cmdGuardar').prop("disabled", true);
	
	
}

function desactivaDirecto(elemento){
	//--Por favor seleccione--
	if( elemento.indexOf("cmd") == -1){
		$('form#formCorre input#'+elemento ).prop("value", "");
	}
		 $('form#formCorre input#'+elemento ).prop("disabled", true);
		 $('form#formCorre input#'+elemento ).removeClass("red");
}



function llenaMotivosRechazos(elemento){
	
	var sr = $('input#dtpASR').val();
	if(sr!= '' ){
	 $.postJSON("correccion/consultaMotivoRechazo.do", '1', function(data) {
		 var options = "<option value='' >--Por favor seleccione--</option>";
		 if(data!=null)
		   for (var i = 0; i < data.length; i++) {
	         options += "<option value='"+ data[i].idMotivorechazo +"'>"+ data[i].motivorechazo +"</option>";		     
	       }
		 $('select#'+elemento).prop("disabled", false);
		 $('select#'+elemento).html(options);
		 $('select#'+elemento).addClass("red");
		 
		//Agrega las opciones al control
	}).error(function(data){ 
		
	}).complete(function(){
		//Instrucciones para el 'complete'
	});
	}else{
		$('select#'+elemento).html('');
		$('select#'+elemento).html("<option value='-1'>--Por favor seleccione--</option>");
		 $('select#'+elemento).prop("disabled", true);
		 $('select#'+elemento).removeClass("red");
		 
			

	}
}

function habilitaCancelar(origen, destino){
	if(document.getElementById(origen).disabled == false){
		$('#formCorre #'+ destino).prop("value", "");
		$('#formCorre #'+ destino).prop("disabled", true);
		 $('#formCorre #' +destino).removeClass("red");	
	}else if(document.getElementById(origen).disabled == true){
		$('#formCorre #'+destino).prop("value", "");
		$('#formCorre #'+destino).prop("disabled", false);
		 $('#formCorre #'+destino).addClass("red");
	}
	
}

function verificaRRDN(origen, destino){
	if(document.getElementById(origen).disabled == false){
		$('#formCorre #'+ destino).prop("value", "");
		$('#formCorre #'+ destino).prop("disabled", true);
		 $('#formCorre #' +destino).removeClass("red");	
	}else if(document.getElementById(origen).disabled == true){
		$('#formCorre #'+destino).prop("value", "");
		$('#formCorre #'+destino).prop("disabled", false);
		 $('#formCorre #'+destino).addClass("red");
	}
	
}


function nuevaCorreccion(){
	location.reload();
}

function listarCorrecciones(){
	oDgBuscar.dialog('open');
}

function desformateaFecha(sFecha){
	var fecha = '';
	if(sFecha != null && sFecha != '' ){
		var anio = sFecha.substring(0,4);
		var mes = sFecha.substring(5,7) ;
		var dia = sFecha.substring(8,10);
		fecha = fecha +dia + '/' + mes + '/' +anio ;
	}
	return fecha;
	
}



function enviaAPaginar(){
	var oTable = $(idBuscaTable).dataTable(); 
	  
	  oTable.fnDraw();
}

function enviaAPaginarFolios(){
	var oTable = $(idDataTable).dataTable(); 
	  
	  oTable.fnDraw();
}


function paginaPagosA(){
	var oTable = $(idDataPagos).dataTable(); 
	  
	  oTable.fnDraw();
}

function cargaDetalleCorreccion(){
	var radios = document.getElementsByName("radioCorr");
	 var seleccionado = 0;
		for (i=0;i<radios.length;i++)
		 {
			if(radios[i].checked)
			{
				seleccionado = 1;
				var folio = radios[i].value;
				var sCorreccion = '{"folio" : "'+folio+'"}';
				var correccion = jQuery.parseJSON(sCorreccion);
				jQuery.ajaxSetup({async:false});
				jQuery.ajax({
						async: false,
					    type: 'POST',
					    url: 'correccion/consultaCorreccion.do',
					    data: sCorreccion, // or JSON.stringify ({name: 'jonas'}),
					    success: function(data) { 
							  $('#txtFolio').prop("value", data.folio , data.dv);
							  buscaElementoCargaDetalle('txtFolio', data.cgcCatTipo.idTipo);
							 activaDirecto('dtpGPeriodoDel', desformateaFecha(data.periododel), data.dv);
							 activaDirecto('dtpGPeriodoAl', desformateaFecha(data.periodoal), data.dv);
							 activaDirecto('txtGRP', data.cvePatron, data.dv)
							 activaDirecto('txtGNombre', data.nombre, data.dv)
							
							 
							 
							  $('select#cgcCatTipo\\.idTipo').html('');
							  $('select#cgcCatTipo\\.idTipo').html("<option value='"+data.cgcCatTipo.idTipo+"'>"+data.cgcCatTipo.descripcion+"</option>");
							  $('select#cgcCatTipo\\.idTipo').prop('disabled', true);
							 
							  $('select#cbxGOrigen').html('');
							  $('select#cbxGOrigen').html("<option value='"+data.cgcCatOrigen.idOrigen+"'>"+data.cgcCatOrigen.descOrigen+"</option>");
							  $('select#cbxGOrigen').prop('disabled', true);
							 
							  
							  $('select#cbxGCriterioSeleccion').html('');
							  $('select#cbxGCriterioSeleccion').html("<option value='"+data.cgtCatCriterioSeleccion.idCriterioseleccion+"'>"+data.cgtCatCriterioSeleccion.descCriterioseleccion+"</option>");
							  $('select#cbxGCriterioSeleccion').prop('disabled', true);
							 
						  //Buscar Registros patronales asociados al folio de correccion
							 
							 var sRP = '{"id": {"folio" : "'+folio+'"}}';
							 var RegPat = jQuery.parseJSON(sRP);
							 $.postJSON("correccion/consultaAnexoPatrones.do", RegPat, function(listaAnexoPatrones) {
								 $('#txtGNoRPA').prop("value", listaAnexoPatrones.length);
								 var tAtrabRevisados = 0;
								 var tAtrabOmisos = 0;
								 var tAtrabSubdeclarados = 0;
								 var tRtrabRevisados = 0;
								 var tRtrabOmisos = 0;
								 var tRtrabSubdeclarados = 0;
								 for(i=1; i<listaAnexoPatrones.length; i++){
									 tAtrabRevisados = tAtrabRevisados + listaAnexosPatrones[i].atrabrevisados;
									 tAtrabOmisos = tAtrabOmisos + listaAnexoPatrones[i].atrabomisos;
									 tAtrabSubdeclarados = tAtrabSubdeclarados + listaAnexoPatrones[i].atrabsubdeclarados;
									 tRtrabRevisados = tRtrabRevisados + listaAnexoPatrones[i].rtrabrevisados;
									 tRtrabOmisos = tRtrabOmisos + listaAnexoPatrones[i].rtrabomisos;
									 tRtrabSubdeclarados = tRtrabSubdeclarados + listaAnexoPatrones[i].rtrabsubdeclarados;
								 }
								//Trabajadores Regularizados
								 $('#txtATrabRevisados').prop("value", tAtrabRevisados);
								 $('#txtATrabOmisos').prop("value", tAtrabOmisos);
								 $('#txtATrabSubdeclarados').prop("value", tAtrabSubdeclarados);
								 $('#txtATrabRegularizados').prop("value", tAtrabRevisados + tAtrabOmisos + tAtrabSubdeclarados);
								 
								 
								 $('#txtRTrabRevisados').prop("value", tRtrabRevisados);
								 $('#txtRTrabOmisos').prop("value", tRtrabOmisos);
								 $('#txtRTrabSubdeclarados').prop("value", tRtrabSubdeclarados);
								 $('#txtRTrabRegularizados').prop("value", tRtrabSubdeclarados + tRtrabOmisos + tRtrabRevisados);
								 
							}).error(function(listaAnexosPatrones){ 
								(listaAnexosPatrones);
					        }).complete(function(){
								//Instrucciones para el 'complete'
					        });
							
							 
							 if(data.aoi!=null && data.aoi!=''){
								 activaDirecto('dtpAOI', desformateaFecha(data.aoi), data.dv);
								 buscaElementoCargaDetalle('dtpAOI', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							 }
							 
							 if(data.anooficiooi !=null && data.anooficiooi!=''){
								 activaDirecto('txtANoOficioOI', data.anooficiooi, data.dv);
								 buscaElementoCargaDetalle('txtANoOficioOI', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							 }
							 
							 if(data.aoin != null && data.aoin!=''){
								 activaDirecto('dtpAOIN', desformateaFecha(data.aoin), data.dv);
								 buscaElementoCargaDetalle('dtpAOIN', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							 }

							 //AUTODETERMINACION
							 if(data.asp != null && data.asp != ''){
								 activaDirecto('dtpASP', desformateaFecha(data.asp), data.dv);	 
								 buscaElementoCargaDetalle('dtpASP', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							 }
							 
							 if(data.ap!=null && data.ap != ''){
								 activaDirecto('dtpAP', desformateaFecha(data.ap), data.dv);
								 buscaElementoCargaDetalle('dtpAP', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							 }
							 
							 if(data.asa!=null && data.asa != ''){
								 activaDirecto('dtpASA', desformateaFecha(data.asa), data.dv);
								 buscaElementoCargaDetalle('dtpASA', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							 }
							 
							 if(data.aapp != null && data.aapp != ''){
								 activaDirecto('dtpAAPP', desformateaFecha(data.aapp), data.dv);
								 buscaElementoCargaDetalle('dtpAAPP', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							 }
							 
							if(data.aacp != null && data.aacp != ''){
								activaDirecto('dtpAACP', desformateaFecha(data.aacp), data.dv);
								buscaElementoCargaDetalle('dtpAACP', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							 }
							
							if(data.aasr != null && data.aasr != ''){
								activaDirecto('dtpAASR', desformateaFecha(data.aasr), data.dv);
								buscaElementoCargaDetalle('dtpAASR', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							 }
							
							
							if(data.asr != null && data.asr != ''){
								activaDirecto('dtpASR', desformateaFecha(data.asr), data.dv);
								buscaElementoCargaDetalle('dtpASR', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							 }
							
							if(data.anoconvenio != null && data.anoconvenio != ''){
								activaDirecto('txtANoConvenio', data.anoconvenio, data.dv);
								buscaElementoCargaDetalle('txtANoConvenio', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							 }	
							 
							if(data.anoparcialidades != null && data.anoparcialidades != ''){
								activaDirecto('txtANoParcialidades', data.anoparcialidades, data.dv);
								buscaElementoCargaDetalle('txtANoParcialidades', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							 }
							
							//REVISION INTERNA
							 
							if(data.rcr != null && data.rcr != ''){
								activaDirecto('dtpRCR', desformateaFecha(data.rcr), data.dv);
								buscaElementoCargaDetalle('dtpRCR', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							 }
							if(data.rporccr != null && data.rporccr != ''){
								 activaDirecto('txtRPorcCR', data.rporccr, data.dv);
								buscaElementoCargaDetalle('txtRPorcCR', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							 }
							if(data.rccpr != null && data.rccpr != ''){
								activaDirecto('dtpRCCPR', desformateaFecha(data.rccpr), data.dv);
								buscaElementoCargaDetalle('dtpRCCPR', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							 }
							
							if(data.rrde != null && data.rrde != ''){
								activaDirecto('dtpRRDE', desformateaFecha(data.rrde), data.dv);
								buscaElementoCargaDetalle('dtpRRDE', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							 }
							if(data.rrdn != null && data.rrdn != ''){
								activaDirecto('dtpRRDN', desformateaFecha(data.rrdn), data.dv);
								buscaElementoCargaDetalle('dtpRRDN', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							 }
							if(data.rod != null && data.rod != ''){
								activaDirecto('dtpROD', desformateaFecha(data.rod), data.dv);
								buscaElementoCargaDetalle('dtpROD', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							 }
							if(data.rnod != null && data.rnod != ''){
								activaDirecto('dtpRNOD', desformateaFecha(data.rnod), data.dv);
								buscaElementoCargaDetalle('dtpRNOD', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							 }
							if(data.rdc != null && data.rdc != ''){
								activaDirecto('dtpRDC', desformateaFecha(data.rdc), data.dv);
								buscaElementoCargaDetalle('dtpRDC', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							 }
							
							
							
							if(data.rvpp != null && data.rvpp != ''){
								 activaDirecto('dtpRVPP', desformateaFecha(data.rvpp), data.dv);
								buscaElementoCargaDetalle('dtpRVPP', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							 }
							if(data.rvtc != null && data.rvtc != ''){
								 activaDirecto('dtpRVTC', desformateaFecha(data.rvtc), data.dv);
								buscaElementoCargaDetalle('dtpRVTC', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							 }
							if(data.rvda != null && data.rvda != ''){
								 activaDirecto('dtpRVDA',  desformateaFecha(data.rvda), data.dv);
								buscaElementoCargaDetalle('dtpRVDA', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							 }
							if(data.rnooficiorde != null && data.rnooficiorde != ''){
								 activaDirecto('txtRNoOficioRDE',  data.rnooficiorde, data.dv);
								buscaElementoCargaDetalle('txtRNoOficioRDE', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							 }
							
							if(data.rnooficiood != null && data.rnooficiood != ''){
								 activaDirecto('txtRNoOficioOD',  data.rnooficiood, data.dv);
								buscaElementoCargaDetalle('txtRNoOficioOD', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							 }
							if(data.rnoconvenio != null && data.rnoconvenio != ''){
								 activaDirecto('txtRNoConvenio',  data.rnoconvenio, data.dv);
								buscaElementoCargaDetalle('txtRNoConvenio', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							 }
							if(data.rnoparcialidades != null && data.rnoparcialidades != ''){
								activaDirecto('txtRNoParcialidades',  data.rnoparcialidades, data.dv);
								buscaElementoCargaDetalle('txtRNoParcialidades', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							 }
							if(data.rnoparcialidades != null && data.rnoparcialidades != ''){
								activaDirecto('txtRNoParcialidades',  data.rnoparcialidades, data.dv);
								buscaElementoCargaDetalle('txtRNoParcialidades', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
							 }
							if(data.arcvconvsp != null && data.arcvconvsp != ''){
								  activaDirecto('txtARCVConvSP', data.arcvconvsp, data.dv);
								  $('form#formCorre input#txtARCVConvSP').formatCurrency();
								  buscaElementoCargaDetalle('txtARCVConvSP', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
								  
							  }
							
							if(data.rrcvconvsp != null && data.rrcvconvsp != ''){
								  activaDirecto('txtRRCVConvSP', data.rrcvconvsp, data.dv);
								  $('form#formCorre input#txtRRCVConvSP').formatCurrency();
								  buscaElementoCargaDetalle('txtRRCVConvSP', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
								  
							  }
							if(data.acopconvsp != null && data.acopconvsp != ''){
								  activaDirecto('txtACOPConvSP', data.acopconvsp, data.dv);
								  $('form#formCorre input#txtACOPConvSP').formatCurrency();
								  buscaElementoCargaDetalle('txtACOPConvSP', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
								  
							  }
							  if(data.rcopconvsp != null && data.rcopconvsp != ''){
								  activaDirecto('txtRCOPConvSP', data.rcopconvsp, data.dv);
								  $('form#formCorre input#txtRCOPConvSP').formatCurrency();
								  buscaElementoCargaDetalle('txtRCOPConvSP', data.cgcCatTipo.idTipo, data.cgcCatOrigen.idOrigen, data.cgtCatCriterioSeleccion.idCriterioseleccion);
								  
							  }
														
							
							
							 //Consultar Anexo Pagos
							 var numFolio = data.folio;
							 cargaDetallePagos(numFolio, data.cgcCatTipo.idTipo);
							
							 
							
							  
							  $('input#cmdGuardar').prop('disabled', false);
							  
							  
							  oDgBuscar.dialog('close');

					    },
					    contentType: "application/json"
				}).error(function(data){ 
					
				}).complete(function(){
					//Instrucciones para el 'complete'
				});
				 break;
			}
		 }
}

function activaDirecto(elemento, value, digver){
	//--Por favor seleccione--
	if(value != null){
		if(digver == 2){
			$('form#formCorre input#'+elemento ).prop("disabled", true);
			$('form#formCorre input#'+elemento ).prop("value", value);
			$('form#formCorre input#'+elemento ).removeClass("red");
		}else{
			$('form#formCorre input#'+elemento ).prop("disabled", false);
			$('form#formCorre input#'+elemento ).prop("value", value);
			$('form#formCorre input#'+elemento ).addClass("red");
		}
		
	}
}

function validarRegPatronal(regPat) {
	//F7028494100
	 	  if(document.getElementById(regPat).value.length > 0  && document.getElementById(regPat).value.length <=11 ){
			  if(document.getElementById(regPat).value != '99999999999'){
				  var sPatron = '{"registroPatronal":"'+document.getElementById(regPat).value+'"}';
				  var patron = jQuery.parseJSON(sPatron);
				  //	alert (reglaNegocio.id.nombrecontrol);
				  bloquear();
				  $.postJSON("correccion/validaRegPat.do", patron, function(data) {
					  if(data==null){
						  desbloquear();
						  alert('El patr\u00F3n no existe o no esta activo');
						  document.getElementById(regPat).value = '';
					  }
					  var nombre = '';
					  var idPat = '';
					  if(data != null && data.razonSocial != null){
						  nombre = data.razonSocial;
						  document.getElementById('txtGNombre').value = nombre;
						  buscaElemento('txtGRP');
						  buscaElemento('txtGNombre');
					 }
		             if(data != null && data.razonSocial != null){
		            	 idPat = data.cvePK;
		            	 document.getElementById('regPatHidden').value = idPat;
					 }
					 
					}).error(function(data){ 
						alert('Ocurrio un error al consultar al patr\u00F3n, intentelo nuevamente por favor');
						document.getElementById(regPat).value = '';
						desbloquear();
						
					}).complete(function(data){
						desbloquear();
					});
		 
				 }
		  }  
		  else{
			  alert('El registro patronal es invalido');
			  return false;
		  }
	  
	}


function validarPresentoPagos(){
	var pp = $('#chkCPresentoPagos').is(':checked');
	if(pp == true){
		$('#cmdAConceptos').addClass("red");
		$('#cmdAConceptos').prop("disabled", false);
		$('#cmdAPagos').addClass("red");
		$('#cmdAPagos').prop("disabled", false);
		$('#txtACOPConvSP').prop("disabled", false);
		$('#txtARCVConvSP').prop("disabled", false);
		
		
		
	}else{
		$('#cmdAConceptos').removeClass("red");
		$('#cmdAConceptos').prop("disabled", true);
		$('#cmdAPagos').removeClass("red");
		$('#cmdAPagos').prop("disabled", true);
		$('#txtACOPConvSP').prop("disabled", true);
		$('#txtARCVConvSP').prop("disabled", true);
	}
}

function validaFecha(fec,fec2){
	var fecha;
	var fecha2;
	if(fec!=''){		
		var tokens = fec.split(',');
		if(tokens.length>1){
			if(document.getElementById(tokens[1]).value != ''){
				fecha = document.getElementById(tokens[1]).value;
			}else if(document.getElementById(tokens[0]).value != ''){
				fecha = document.getElementById(tokens[0]).value;
			}
		}else
			fecha = document.getElementById(fec).value;
		while (fecha.toString().indexOf("/") != -1)
			fecha = fecha.replace("/","-");	    
	}
	if(fec2!=''){
		fecha2 = document.getElementById(fec2).value;
		while (fecha2.toString().indexOf("/") != -1)
			fecha2 = fecha2.replace("/","-");	    
	}
	if(fecha2!=""){
		if(!validaFechas(fecha2,fecha)){
			alert("La fecha no puede ser menor a "+fecha);
			$('input#'+fec2).prop("value", "");
			return false;
		}else{
			return true;
		}
	}	
}

function validafechaSistema(fec){		
	var fecha = document.getElementById(fec).value;		
	while (fecha.toString().indexOf("/") != -1)
		fecha = fecha.replace("/","-");	    
	var fechaSis = new Date();
	var diaS = fechaSis.getDate();
	var mesS = fechaSis.getMonth() + 1;
	var anioS = fechaSis.getFullYear()+"";
	if(diaS<10){
		diaS = "0"+diaS;
	}
	if(mesS<10){
		mesS = "0"+mesS;
	}
	var fecha2 = diaS+"-"+mesS+"-"+anioS;
	if(fecha!=""){
		if(!validaFechas(fecha2,fecha)){
			alert("La fecha debe ser menor o igual a "+fecha2);
			$('input#'+fec).prop("value", "");
			return false;
		}else{
			return true;
		}
	}
}

function eliminaOpcionesSelect(selector){
	$(selector).html('');
	$(selector).html("<option value='-1'>--Por favor seleccione--</option>");
	$(selector).removeClass("red");
}


function sumaTrabajadoresAutodeterminacion(e){
	

	var trabRevi =  $('input#txtATrabRevisados').val();
	var trabOmi =  $('input#txtATrabOmisos').val(); 
	var trabSub =  $('input#txtATrabSubdeclarados').val(); 
	var trabReg = $('input#txtATrabRegularizados').val(); 

	tecla = (document.all) ? e.keyCode : e.which;
	if (tecla==13  ) {
		if(trabRevi == ''){
			alert('La suma de los Trabajadores Omisos + T. Subdeclarados NO puede ser MAYOR que los Trabajadores Revisados');
			return false;
		}
		if(trabOmi == '' &&  trabSub==''){
			return false;
		}
		if(trabOmi == ''){
			trabOmi = 0;
		}
		if(trabSub == ''){
			trabSub = 0;
		}
			
		
		trabReg = parseInt(trabOmi) + parseInt(trabSub);
		if(trabReg > trabRevi){
			alert('La suma de los Trabajadores Omisos + T. Subdeclarados NO puede ser MAYOR que los Trabajadores Revisados');
		}else{
			document.getElementById('txtTrabRegularizados').value = trabReg;
		}
	}
		
	}

function sumaTrabajadoresRevision(e){
	

	var trabRevi =  $('input#txtRTrabRevisados').val();
	var trabOmi =  $('input#txtRTrabOmisos').val(); 
	var trabSub =  $('input#txtRTrabSubdeclarados').val(); 
	var trabReg = $('input#txtRTrabRegularizados').val(); 

	tecla = (document.all) ? e.keyCode : e.which;
	if (tecla==13  ) {
		if(trabRevi == ''){
			alert('La suma de los Trabajadores Omisos + T. Subdeclarados NO puede ser MAYOR que los Trabajadores Revisados');
			return false;
		}
		if(trabOmi == '' &&  trabSub==''){
			return false;
		}
		if(trabOmi == ''){
			trabOmi = 0;
		}
		if(trabSub == ''){
			trabSub = 0;
		}
			
		
		trabReg = parseInt(trabOmi) + parseInt(trabSub);
		if(trabReg > trabRevi){
			alert('La suma de los Trabajadores Omisos + T. Subdeclarados NO puede ser MAYOR que los Trabajadores Revisados');
		}else{
			document.getElementById('txtTrabRegularizados').value = trabReg;
		}
	}
		
	}

function sumarTrabajadoresAuto(){
	

	var trabRevi =  $('input#txtATrabRevisados').val();
	var trabOmi =  $('input#txtATrabOmisos').val(); 
	var trabSub =  $('input#txtATrabSubdeclarados').val(); 
	var trabReg = $('input#txtATrabRegularizados').val(); 

	
	
		if(trabRevi == ''){
			alert('La suma de los Trabajadores Omisos + T. Subdeclarados NO puede ser MAYOR que los Trabajadores Revisados');
			return false;
		}
		if(trabOmi == ''){
			trabOmi = 0;
		}
		if(trabSub == ''){
			trabSub = 0;
		}
			
		
		trabReg = parseInt(trabOmi) + parseInt(trabSub);
		if(trabReg > trabRevi){
			alert('La suma de los Trabajadores Omisos + T. Subdeclarados NO puede ser MAYOR que los Trabajadores Revisados');
		}else{
			document.getElementById('txtTrabRegularizados').value = trabReg;
		}
	
		
	}


function sumarTrabajadoresRevision(){
	

	var trabRevi =  $('input#txtRTrabRevisados').val();
	var trabOmi =  $('input#txtRTrabOmisos').val(); 
	var trabSub =  $('input#txtRTrabSubdeclarados').val(); 
	var trabReg = $('input#txtRTrabRegularizados').val(); 

	
	
		if(trabRevi == ''){
			alert('La suma de los Trabajadores Omisos + T. Subdeclarados NO puede ser MAYOR que los Trabajadores Revisados');
			return false;
		}
		if(trabOmi == ''){
			trabOmi = 0;
		}
		if(trabSub == ''){
			trabSub = 0;
		}
			
		
		trabReg = parseInt(trabOmi) + parseInt(trabSub);
		if(trabReg > trabRevi){
			alert('La suma de los Trabajadores Omisos + T. Subdeclarados NO puede ser MAYOR que los Trabajadores Revisados');
		}else{
			document.getElementById('txtTrabRegularizados').value = trabReg;
		}
	
		
	}


function buscaElementoCargaDetalle(elemento, idTipo, idOrigen, idCritS) {

	  
	  
	  if(document.getElementById(elemento).value.length > 0){
		  var sReglaNegocio = '{"nombrecontrol":"'+elemento+'", "cgcCatTipo":{"idTipo":"'+idTipo+'"}}';
		  var reglaNegocio = jQuery.parseJSON(sReglaNegocio);
		  $.postJSON("correccion/consultaRegla.do", reglaNegocio, function(data) {
		  var hijos = data.hijos;
		  var dependencia = data.cveUsuario;
		  if(document.getElementById(dependencia) != null && document.getElementById(dependencia).value != ''){
			  hijos = data.nombreCampo
		  }
		  if(hijos != null){
			  var tokens = hijos.split(",");
		  	for(var i=0; i< tokens.length; i++){
		  		if(tokens[i]=='cbxMotivoCancelacion'){
		  			activaCombos('cbxMotivoCancelacion');
		  		}
		  		if(tokens[i]=='cbxTipoObra'){
		  			activaCombos('cbxTipoObra');
		  		}else{
		  			activa(tokens[i]);
		  		}
		  	}
		  }
		  var hijosIndependientes = data.hijosindependientes;
		  
		  if(hijosIndependientes!=null){
			  var itokens = hijosIndependientes.split(",");
			  for(var i=0; i< itokens.length; i++){
				  if(itokens[i]=='cbxMotivoCancelacion'){
			  			activaCombos('cbxMotivoCancelacion');
			  		}
			  		if(itokens[i]=='cbxTipoObra'){
			  			activaCombos('cbxTipoObra');
			  		}else{
				  		activa(itokens[i]);
			  		}
			  }
		  }
		}).error(function(data){ 
			
		}).complete(function(){
			//Instrucciones para el 'complete'
		});
	  
	  
}

}

function cargaDetallePagos(numFolio, idTipo){
	 
	 var numFolio =  document.getElementById('txtFolio').value;
	 var sFolio = '{"folio": "'+numFolio+'" }';
	  var folio = jQuery.parseJSON(sFolio);
	 //Autodeterminacion
	  var atotalcopsp = 0;
	  var atotalrcvsp = 0;
	  var atotalcopact = 0;
	  var atotalrcvact = 0;
	  var atotalcoprec = 0;
	  var atotalrcvrec = 0;
	  //Revision
	  var rtotalcopsp = 0;
	  var rtotalrcvsp = 0;
	  var rtotalcopact = 0;
	  var rtotalrcvact = 0;
	  var rtotalcoprec = 0;
	  var rtotalrcvrec = 0;
	 $.postJSON("correccion/consultaAnexoPagos.do", folio, function(data) {
		 var options = "<option value='-1' >--Por favor seleccione--</option>";
		 if(data!=null){
		   for (var i = 0; i < data.length; i++) {
			   if(data[i].idProceso == 1){
				   	 atotalcopsp = atotalcopsp + data[i].copsp;
					 atotalrcvsp = atotalrcvsp + data[i].rcvsp;
					 atotalcopact = atotalcopact + data[i].copact;
					 atotalrcvact =atotalrcvact + data[i].rcvact;
					 atotalcoprec = atotalcoprec + data[i].coprec;
					 atotalrcvrec = atotalrcvrec + data[i].rcvrec;
			   }else if(data[i].idProceso == 2){
				   	 rtotalcopsp = rtotalcopsp + data[i].copsp;
					 rtotalrcvsp = rtotalrcvsp + data[i].rcvsp;
					 rtotalcopact = rtotalcopact + data[i].copact;
					 rtotalrcvact =rtotalrcvact + data[i].rcvact;
					 rtotalcoprec = rtotalcoprec + data[i].coprec;
					 rtotalrcvrec = rtotalrcvrec + data[i].rcvrec;
			   }
			 
		   }
		   
		 }
		 //Autodeterminacion
		 var atotalCOP = 0;
		 atotalCOP = atotalcopsp + atotalcopact + atotalcoprec;
		 var atotalRCV = 0;
		 atotalRCV = atotalrcvsp + atotalrcvrec+ atotalrcvact;
		 sumariza("txtACOPPagSP", atotalcopsp);
		 $('form#formCorre input#txtACOPPagSP').formatCurrency();
		 sumariza("txtARCVPagSP", atotalrcvsp);
		 $('form#formCorre input#txtARCVPagSP').formatCurrency();
		 sumariza("txtACOPPagAct", atotalcopact);
		 $('form#formCorre input#txtACOPPagAct').formatCurrency();
		 sumariza("txtARCVPagAct", atotalrcvact);
		 $('form#formCorre input#txtARCVPagAct').formatCurrency();
		 sumariza("txtACOPPagRec", atotalcoprec);
		 $('form#formCorre input#txtACOPPagRec').formatCurrency();
		 sumariza("txtARCVPagRec", atotalrcvrec);
		 $('form#formCorre input#txtARCVPagRec').formatCurrency();
		 
		 
		 
		 
		 //Revision
		 var rtotalCOP = 0;
		 rtotalCOP = rtotalcopsp + rtotalcopact + rtotalcoprec;
		 var rtotalRCV = 0;
		 rtotalRCV = rtotalrcvsp + rtotalrcvrec+ rtotalrcvact;
		 sumariza("txtRCOPPagSP", rtotalcopsp);
		 $('form#formCorre input#txtRCOPPagSP').formatCurrency();
		 sumariza("txtRRCVPagSP", rtotalrcvsp);
		 $('form#formCorre input#txtRRCVPagSP').formatCurrency();
		 sumariza("txtRCOPPagAct", rtotalcopact);
		 $('form#formCorre input#txtRCOPPagAct').formatCurrency();
		 sumariza("txtRRCVPagAct", rtotalrcvact);
		 $('form#formCorre input#txtRRCVPagAct').formatCurrency();
		 sumariza("txtRCOPPagRec", rtotalcoprec);
		 $('form#formCorre input#txtRCOPPagRec').formatCurrency();
		 sumariza("txtRRCVPagRec", rtotalrcvrec);
		 $('form#formCorre input#txtRRCVPagRec').formatCurrency();
		 
		 
		 sumariza("txtACOPPagTotal", atotalCOP);
		 $('form#formCorre input#txtACOPPagTotal').formatCurrency();
		 
		 sumariza("txtARCVPagTotal", atotalRCV);
		 $('form#formCorre input#txtARCVPagTotal').formatCurrency();
		
		 sumariza("txtRCOPPagTotal", rtotalCOP);
		 $('form#formCorre input#txtRCOPPagTotal').formatCurrency();
		
		 
		 sumariza("txtRRCVPagTotal", rtotalRCV);
		 $('form#formCorre input#txtRRCVPagTotal').formatCurrency();
		 
		
		
		
		 
		 
		//Agrega las opciones al control
	}).error(function(data){ 
		
	}).complete(function(){
		//Instrucciones para el 'complete'
	});
   }

function salirAnexoPagos(){
	
	oDgAgregaPagos.dialog('close');
}


function eliminaAnexoPagos(){
	var idPago = $('#:checked[name*="radio"]').val();
	var sPago = '{"idPago":"'+idPago+'"}';
	var pago = jQuery.parseJSON(sPago);
	
	// Buscamos el elemento
	$.postJSON("correccion/eliminaAnexoPagos.do", pago, function(data) {
		 var oTable = $(idDataPagos).dataTable(); 
		  oTable.fnDraw();
	}).error(function(data){ 
		
	}).complete(function(){
		//Instrucciones para el 'complete'
	});
	
}

function salirAnexoPagosR(){
	
	oDgAgregaPagosR.dialog('close');
}


function eliminaAnexoPagosR(){
	var idPago = $('#:checked[name*="radioR"]').val();
	var sPago = '{"idPago":"'+idPago+'"}';
	var pago = jQuery.parseJSON(sPago);
	
	// Buscamos el elemento
	$.postJSON("correccion/eliminaAnexoPagos.do", pago, function(data) {
		 var oTable = $(idDataPagosR).dataTable(); 
		  oTable.fnDraw();
	}).error(function(data){ 
		
	}).complete(function(){
		//Instrucciones para el 'complete'
	});
	
}
