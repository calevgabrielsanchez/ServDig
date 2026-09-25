/**
 * Controla todas las peticiones JSON de la 
 * pantalla de pagos seguimiento.
 * 
 */

/**Tabla donde se muestran todos los pagos realizados*/
var idDataTable 	= "#dtPagosDetalle";

/**Tabla donde se muestran todos los pagos realizados agrupados por RP*/
var idDataTablePorRP 	= "#dtPagosDetallePorRP";

/**Pantalla Modal de confirmación al momento de eliminar un pago*/
var idDialogBorrar = "#dgPagosBorrar";

/**Indica el total globalizado trabajadores regularizados*/
var totalLabel_TRAB_REG = 0;

/**Indica el total globalizado Suerte principal COP*/
var totalLabel_SPCOP = 0;

/**Indica el total globalizado Suerte Principal RCV*/
var totalLabel_SPRCV = 0;

/**
 * Inicializamos funciones básicas de la pantalla
 */
$(document).ready(function() {

	$("form#pagosVOForm,#fechaPago").datepicker( { dateFormat: 'dd-mm-yy' });
	$("form#pagosVOForm,#fechaPago").datepicker('option', 'maxDate', getFechaServidor());
	
	
	divControl('hide','tituloSumarizados');
	divControl('hide','pagosDetallePorRPData');
	
	generaDataTablePagosDetalle();
	
});

/**
 * Permite generar la tabala de pagos realizados asi
 * como la tabla del totalizado global.
 */
function generaDataTablePagosDetalle(){
	
	totalLabel_SPCOP = 0;
	var totalLabel_RECCOP = 0;
	var totalLabel_ACTCOP = 0;
	var totalLabel_MULTASCOP = 0;
	var totalLabel_TOTALCOP = 0;
	
	totalLabel_SPRCV = 0;
	var totalLabel_RECRCV = 0;
	var totalLabel_ACTRCV = 0;
	var totalLabel_MULTASRCV = 0;
	var totalLabel_TOTALRCV = 0;
	
	 totalLabel_TRAB_REG = 0;
	var totalLabel_TRAB_ALTAS = 0;
	var totalLabel_TRAB_BAJAS = 0;
	var totalLabel_TRAB_MOD_SAL = 0;

	$('#label_SP_COP').html('$'+moneyMaskDT(totalLabel_SPCOP,2));
	$('#label_ACT_COP').html('$'+moneyMaskDT(totalLabel_RECCOP,2));
	$('#label_REC_COP').html('$'+moneyMaskDT(totalLabel_ACTCOP,2));
	$('#label_MULTAS_COP').html('$'+moneyMaskDT(totalLabel_MULTASCOP,2));
	$('#label_TOTAL_COP').html('$'+moneyMaskDT(totalLabel_TOTALCOP,2));
	
	$('#label_SP_RCV').html('$'+moneyMaskDT(totalLabel_SPRCV,2));
	$('#label_ACT_RCV').html('$'+moneyMaskDT(totalLabel_RECRCV,2));
	$('#label_REC_RCV').html('$'+moneyMaskDT(totalLabel_ACTRCV,2));
	$('#label_MULTAS_RCV').html('$'+moneyMaskDT(totalLabel_MULTASRCV,2));
	$('#label_TOTAL_RCV').html('$'+moneyMaskDT(totalLabel_TOTALRCV,2));
	
	$('#label_TRAB_REG').html('$'+moneyMaskDT(totalLabel_TRAB_REG,2));
	$('#label_TRAB_ALTAS').html('$'+moneyMaskDT(totalLabel_TRAB_ALTAS,2));
	$('#label_TRAB_BAJAS').html('$'+moneyMaskDT(totalLabel_TRAB_BAJAS,2));
	$('#label_TRAB_MOD_SAL').html('$'+moneyMaskDT(totalLabel_TRAB_MOD_SAL,2));
	
	$(idDataTable).dataTable({
		bJQueryUI : true,
		bFilter : false,
		bInfo:true,
		bSort: false,
		bDestroy: true,
		"bPaginate": true,
		"bAutoWidth" : true,
		"bServerSide" :	true,
		"sScrollX": "100%", 
		"sScrollXInner": "200%",
		"aoColumns" : [ {
			"sTitle" : "Seleccione",
			fnRender :function(oObj){
				var retVal = '<input type="radio" value="' +
				oObj.aData['cveRevpagos'] +'" id="cveRevpagosSelecconado" onclick="consultarPago()" class="radioDeteccion" name="radio"/> ';
				return retVal;
			}, 
			aTargets: [0]
			},{
				"sTitle" : "Registro Patronal ",
				"mDataProp" : "registroPatronal",
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Folio SUA ",
				"mDataProp" : "numFoliosua",
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Orden Ingreso",
				"mDataProp" : "numOrdeningreso",
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Num. Cr\u00e9dito",
				"mDataProp" : "numCredito",
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Fecha Pago",
				"mDataProp" : "fechaPagoStr",
				"sClass": "dtCenterClassColumn",
				"sWidth": "150px"
			},{
				"sTitle" : "Tipo Doct",
				"mDataProp" : "idTipodocto",
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Ejercicio ",
				"mDataProp" : "cveEjercicio",
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Periodo ",
				"mDataProp" : "numPeriodo",
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Sp COP ",
				fnRender :function(oObj){
					totalLabel_SPCOP+= Number(oObj.aData['impCopsp']);
					$('#label_SP_COP').html('$'+moneyMaskDT(totalLabel_SPCOP,2));
					var retVal = '$'+moneyMaskDT(oObj.aData['impCopsp'],2) ;
					return retVal;
				}, 
				aTargets: [9],
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Act COP ",
				fnRender :function(oObj){
					totalLabel_ACTCOP+= Number(oObj.aData['impCopact']);
					$('#label_ACT_COP').html('$'+moneyMaskDT(totalLabel_ACTCOP,2));
					var retVal = '$'+moneyMaskDT(oObj.aData['impCopact'],2) ;
					return retVal;
				}, 
				aTargets: [10],
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Rec COP ",
				fnRender :function(oObj){
					totalLabel_RECCOP+= Number(oObj.aData['impCoprec']);
					$('#label_REC_COP').html('$'+moneyMaskDT(totalLabel_RECCOP,2));
					var retVal = '$'+moneyMaskDT(oObj.aData['impCoprec'],2) ;
					return retVal;
				}, 
				aTargets: [11],
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Multas COP ",
				fnRender :function(oObj){
					totalLabel_MULTASCOP+= Number(oObj.aData['impCopmulta']);
					$('#label_MULTAS_COP').html('$'+moneyMaskDT(totalLabel_MULTASCOP,2));
					var retVal = '$'+moneyMaskDT(oObj.aData['impCopmulta'],2) ;
					return retVal;
				}, 
				aTargets: [12],
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Total COP ",
				fnRender :function(oObj){
					totalLabel_TOTALCOP+= Number(oObj.aData['impCoptot']);
					$('#label_TOTAL_COP').html('$'+moneyMaskDT(totalLabel_TOTALCOP,2));
					var retVal = '$'+moneyMaskDT(oObj.aData['impCoptot'],2) ;
					return retVal;
				}, 
				aTargets: [13],
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Periodo RCV",
				"mDataProp" : "numPeriodoRCV",
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Sp RCV ",
				fnRender :function(oObj){
					totalLabel_SPRCV+= Number(oObj.aData['impRcvsp']);
					$('#label_SP_RCV').html('$'+moneyMaskDT(totalLabel_SPRCV,2));
					var retVal = '$'+moneyMaskDT(oObj.aData['impRcvsp'],2) ;
					return retVal;
				}, 
				aTargets: [14],
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Act RCV ",
				fnRender :function(oObj){
					totalLabel_ACTRCV+= Number(oObj.aData['impRcvact']);
					$('#label_ACT_RCV').html('$'+moneyMaskDT(totalLabel_ACTRCV,2));
					var retVal = '$'+moneyMaskDT(oObj.aData['impRcvact'],2) ;
					return retVal;
				}, 
				aTargets: [15],
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Rec RCV ",
				fnRender :function(oObj){
					totalLabel_RECRCV+= Number(oObj.aData['impRcvrec']);
					$('#label_REC_RCV').html('$'+moneyMaskDT(totalLabel_RECRCV,2));
					var retVal = '$'+moneyMaskDT(oObj.aData['impRcvrec'],2) ;
					return retVal;
				}, 
				aTargets: [16],
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Multas RCV ",
				fnRender :function(oObj){
					totalLabel_MULTASRCV+= Number(oObj.aData['impRcvmulta']);
					$('#label_MULTAS_RCV').html('$'+moneyMaskDT(totalLabel_MULTASRCV,2));
					var retVal = '$'+moneyMaskDT(oObj.aData['impRcvmulta'],2) ;
					return retVal;
				}, 
				aTargets: [17],
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Total RCV ",
				fnRender :function(oObj){
					totalLabel_TOTALRCV+= Number(oObj.aData['impRcvtot']);
					$('#label_TOTAL_RCV').html('$'+moneyMaskDT(totalLabel_TOTALRCV,2));
					var retVal = '$'+moneyMaskDT(oObj.aData['impRcvtot'],2) ;
					return retVal;
				}, 
				aTargets: [18],
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Traba. Reg ",
				fnRender :function(oObj){
					var retVal = Number(oObj.aData['numTrabregula']);
					totalLabel_TRAB_REG+= Number(retVal);
					$('#label_TRAB_REG').html(totalLabel_TRAB_REG);
					
					return retVal;
				},
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Trab. Altas",
				fnRender :function(oObj){
					var retVal = Number(oObj.aData['numAltas']);
					totalLabel_TRAB_ALTAS+= Number(retVal);
					$('#label_TRAB_ALTAS').html(totalLabel_TRAB_ALTAS);
					
					return retVal;
				},
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Trab. Bajas",
				fnRender :function(oObj){
					var retVal = Number(oObj.aData['numBajas']);
					totalLabel_TRAB_BAJAS+= Number(retVal);
					$('#label_TRAB_BAJAS').html(totalLabel_TRAB_BAJAS);
					
					return retVal;
				},
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Trab. Mod. Sal",
				fnRender :function(oObj){
					var retVal = Number(oObj.aData['numModifsalario']);
					totalLabel_TRAB_MOD_SAL+= Number(retVal);
					$('#label_TRAB_MOD_SAL').html(totalLabel_TRAB_MOD_SAL);
					
					return retVal;
				},
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Detalle Pagos",
				fnRender :function(oObj){
					var retVal = oObj.aData['flagCobPagos'];
					if(retVal=="S"){
							var fecPag=oObj.aData['fechaPagoStr']+'';						
							return '<button onclick="recuperaDetallePagoId('+oObj.aData['numFoliosua']+','+oObj.aData['idTipodocto']+','+oObj.aData['numPeriodo']+',\''+fecPag+'\')" type="button">Ver</button>';
						}else{
							return "";
						}
					}
				}
			],"bProcessing" : true,
			"sAjaxSource" : 'pagos/listarPagosRealizados.do',
			"fnServerData" : function(sSource, aoData, fnCallback) {				
				
				var wrapper = new Object();
			
				var sCvePresentacion = '"cvePresentacorr":'+'"'+document.getElementById("cvePresentacorr").value+'"';
				var sTipoPago = '"indTipopago":'+'"'+document.getElementById("indTipopago").value+'"';
				var sCveRegulaPagos = '"cveRegulaPagos":'+'"'+document.getElementById("cveRegulapagos").value+'"';
				var sCrtRevPagos = '{'+sCvePresentacion+','+sTipoPago+','+sCveRegulaPagos+'}';
				var oCrtRevPagos = jQuery.parseJSON(sCrtRevPagos);
				
				wrapper.aoData = aoData;				
				wrapper.oForm = oCrtRevPagos;
				
				$.postJSON(sSource, wrapper, function(data) {			
					
					fnCallback(data);
				 }).error(function(datas){ 
						validarSesionExpirada(datas);
				});
			}
		});

	
}

/**
 * Permite generar la tabala del global pagado
 * agrupado por registro patronal.
 */

function generaDataTablePagosDetallePorRP(data){
	
	var index = 0;
	
	$(idDataTablePorRP).dataTable({
		"aaData": data,
		bFilter : false,
		bJQueryUI : true,
		bInfo:true,
		"bAutoWidth" : true,
		"sScrollX": "100%", 
		"sScrollXInner": "200%",
		"bDestroy": true,
		bSort: false,
		//crollY: "200px",
		"aoColumns" : [{
				"sTitle" : "Registro Patronal ",
				"mDataProp" : "registroPatronal",
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Sp COP ",
				fnRender :function(oObj){
					var retVal = '$'+moneyMaskDT(data[index].impCopsp,2) ;
					return retVal;
				}, 
				aTargets: [1],
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Act COP ",
				fnRender :function(oObj){
				  	var retVal = '$'+moneyMaskDT(data[index].impCopact,2) ;
					return retVal;
				}, 
				aTargets: [2],
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Rec COP ",
				fnRender :function(oObj){
					var retVal = '$'+moneyMaskDT(data[index].impCoprec,2) ;
					return retVal;
				}, 
				aTargets: [3],
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Multas COP ",
				fnRender :function(oObj){
					var retVal = '$'+moneyMaskDT(data[index].impCopmulta,2) ;
					return retVal;
				}, 
				aTargets: [4],
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Total COP ",
				fnRender :function(oObj){
					var retVal = '$'+moneyMaskDT(data[index].impCoptot,2) ;
					return retVal;
				}, 
				aTargets: [5],
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Sp RCV ",
				fnRender :function(oObj){
					var retVal = '$'+moneyMaskDT(data[index].impRcvsp,2) ;
					return retVal;
				}, 
				aTargets: [6],
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Act RCV ",
				fnRender :function(oObj){
					var retVal = '$'+moneyMaskDT(data[index].impRcvact,2) ;
					return retVal;
				}, 
				aTargets: [7],
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Rec RCV ",
				fnRender :function(oObj){
					var retVal = '$'+moneyMaskDT(data[index].impRcvrec,2) ;
					return retVal;
				}, 
				aTargets: [8],
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Multas RCV ",
				fnRender :function(oObj){
					var retVal = '$'+moneyMaskDT(data[index].impRcvmulta,2) ;
					return retVal;
				}, 
				aTargets: [9],
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Total RCV ",
				fnRender :function(oObj){
					var retVal = '$'+moneyMaskDT(data[index++].impRcvtot,2) ;
					return retVal;
				}, 
				aTargets: [10],
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Traba. Reg ",
				"mDataProp" : "numTrabregula",
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Trab. Altas",
				"mDataProp" : "numAltas",
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Trab. Bajas",
				"mDataProp" : "numBajas",
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			},{
				"sTitle" : "Trab. Mod. Sal",
				"mDataProp" : "numModifsalario",
				"sClass": "dtCenterClassColumn",
				"sWidth": "100px"
			}
			]
		});

	
}

/**
 * Permite generar la tabala del sumarizado por registro patronal.
 * Esta funcion es un puente el cual ejecuta 
 * generaDataTablePagosDetallePorRP(data);
 */
function mostrarVentanaSumarizadoPorRP(){
	
	if(document.getElementById("cveRegulapagos").value!=""){
		
		alert("Esta funci\u00f3n no est\u00e1 habilitada para pagos de promoci\u00f3n");
		return false;
		
	}
	
	var sCvePresentacion = '"cvePresentacorr":'+'"'+document.getElementById("cvePresentacorr").value+'"';
	var sTipoPago = '"indTipopago":'+'"'+document.getElementById("indTipopago").value+'"';
	var sCrtRevPagos = '{'+sCvePresentacion+','+sTipoPago+'}';
	var oCrtRevPagos = jQuery.parseJSON(sCrtRevPagos);
	
	bloquear();
	
	$.postJSON("pagos/listarPagosRealizadosPorRP.do", oCrtRevPagos, function(data) {			
	
		generaDataTablePagosDetallePorRP(data);
		
		desbloquear();
		
	 }).error(function(datas){ 
			validarSesionExpirada(datas);
			desbloquear();
	});

	divControl('show','tituloSumarizados');
	divControl('show','pagosDetallePorRPData');
	
	$('form#pagosVOForm #sumarizado').val("Actualizar Sumarizado");
	
}

/**
 *Permite guardar un pago.
 *Reinicia la posicion de los campos y valores
 *de la pantalla. (Bloquea todos los campos) 
 */
function guardarPago(){
	
	transformDataToModel([DIV_NAME_COP,DIV_NAME_RCV,DIV_NAME_MA],true);
	
	var crtRevPagos = $("#pagosVOForm").toObject({mode:'first'});
	
	if(validateCompleteForm()){
		
		bloquear();
		
		$.postJSON("pagos/agregarModificar.do", crtRevPagos, function(data) {

			if(data!=null && data.msg!=null && data.msg!=undefined && data.msg!=""){
				alert(data.msg);
			}
				
			desbloquear();
			
			$("#pagosVOForm #saveData").attr("disabled","disabled");
			$("#pagosVOForm #modifyData").attr("disabled","disabled");
			$("#pagosVOForm #deleteData").attr("disabled","disabled");
			
			
			handleDivFields([DIV_NAME_GENERAL_DATA,DIV_NAME_COP,DIV_NAME_RCV,DIV_NAME_MA],true,true);
						
			generaDataTablePagosDetalle();
			
		}).error(function(data){
			transformDataToModel([DIV_NAME_COP,DIV_NAME_RCV,DIV_NAME_MA],false);
			validarSesionExpirada(data);
			alert("error" + data);
			desbloquear();
		});
	}
	
}

/**
 * Permite Eliminar un pago.
 *Reinicia la posicion de los campos y valores
 *de la pantalla. (Bloquea todos los campos) 
 */
function eliminarPago(){

	transformDataToModel([DIV_NAME_COP,DIV_NAME_RCV,DIV_NAME_MA],true);
	
	
	$(idDialogBorrar).dialog({
		autoOpen: true,
		modal:true,
		resizable:false,
		height: 140,
		buttons: {
			"Aceptar": function() { 

				var crtRevPagos = $("#pagosVOForm").toObject({mode:'first'});
				
				$.postJSON("pagos/eliminarPago.do", crtRevPagos, function(data) {
					
					if(data!=null && data.msg!=null && data.msg!=undefined && data.msg!=""){
						alert(data.msg);
					}
										
					$("#pagosVOForm #saveData").attr("disabled","disabled");
					$("#pagosVOForm #modifyData").attr("disabled","disabled");
					$("#pagosVOForm #deleteData").attr("disabled","disabled");
					$('#:checked').attr("checked",false);
					$("form#pagosVOForm #cveRevpagos").val(0);

					handleDivFields([DIV_NAME_GENERAL_DATA,DIV_NAME_COP,DIV_NAME_RCV,DIV_NAME_MA],true,true);
								
					generaDataTablePagosDetalle();
					
				}).error(function(data){
					transformDataToModel([DIV_NAME_COP,DIV_NAME_RCV,DIV_NAME_MA],false);
					validarSesionExpirada(data);
					alert("error" + data);
				});
				
				
				
				$(this).dialog("close"); 
			}, 
			"Cancelar": function() {
				$(this).dialog("close");
			} 
		}
	});	

}

/**
 * Permite consultar un pago y este es sujeto
 * a eliminar o modificar.
 */

function consultarPago(){

	var cveCrtRevPago = $('#:checked').val();
	
	if(cveCrtRevPago!=undefined){
		bloquear();
		handleDivFields([DIV_NAME_GENERAL_DATA,DIV_NAME_COP,DIV_NAME_RCV,DIV_NAME_MA],true,true);
		
		$("form#pagosVOForm #cveRevpagos").val(cveCrtRevPago);
		
		var crtRevPagos = $("#pagosVOForm").toObject({mode:'first'});
		
		
		$.postJSON("pagos/consultarPago.do", crtRevPagos, function(data) {
		
			if(data!=null && data.msg!=null && data.msg!=undefined && data.msg!=""){
					alert(data.msg);
			}else{
				
				
				handleDivFields([DIV_NAME_GENERAL_DATA,DIV_NAME_COP,DIV_NAME_RCV,DIV_NAME_MA],true,true);
				handleDivFields([DIV_NAME_GENERAL_DATA],false,false);
				MV_SELECTED = 0;

				
				/*Datos Generales*/
				$("form#pagosVOForm #regitroPatronal").val(data.pagoModel.cveAnexosolcorrpat);
				$("form#pagosVOForm #sua").val(data.pagoModel.numFoliosua);
				$("form#pagosVOForm #oIngreso").val(data.pagoModel.numOrdeningreso);
				$("form#pagosVOForm #numCredito").val(data.pagoModel.numCredito);
				$("form#pagosVOForm #fechaPago").val(data.pagoModel.fechaPagoStr);
				$("form#pagosVOForm #tpDocto").val(data.pagoModel.idTipodocto);
				
				/*COP*/
				$("form#pagosVOForm #SP_COP").val(moneyMaskDT(data.pagoModel.impCopsp,2));
				$("form#pagosVOForm #ACT_COP").val(moneyMaskDT(data.pagoModel.impCopact,2));
				$("form#pagosVOForm #REC_COP").val(moneyMaskDT(data.pagoModel.impCoprec,2));
				$("form#pagosVOForm #MULTA_COP").val(moneyMaskDT(data.pagoModel.impCopmulta,2));
				$("form#pagosVOForm #totalCOP").val(moneyMaskDT(data.pagoModel.impCoptot,2));
				
				/*RCV*/
				$("form#pagosVOForm #SP_RCV").val(moneyMaskDT(data.pagoModel.impRcvsp,2));
				$("form#pagosVOForm #ACT_RCV").val(moneyMaskDT(data.pagoModel.impRcvact,2));
				$("form#pagosVOForm #REC_RCV").val(moneyMaskDT(data.pagoModel.impRcvrec,2));
				$("form#pagosVOForm #MULTA_RCV").val(moneyMaskDT(data.pagoModel.impRcvmulta,2));
				$("form#pagosVOForm #totalRCV").val(moneyMaskDT(data.pagoModel.impRcvtot,2));
				
				/*MA*/
				$("form#pagosVOForm #trabReg").val(data.pagoModel.numTrabregula);
				$("form#pagosVOForm #trabAltas").val(data.pagoModel.numAltas);
				$("form#pagosVOForm #trabBajas").val(data.pagoModel.numBajas);
				$("form#pagosVOForm #modSalario").val(data.pagoModel.numModifsalario);
				
				/*Globales*/
				
				try{
					
					if(data.pagoModel.impCopsp!=null && data.pagoModel.impCopsp!=undefined && data.pagoModel.impCopsp>0){
						handleDivFields([DIV_NAME_COP],false,false);
						$("form#pagosVOForm #copSelect").val(data.pagoModel.numPeriodo);
						MV_SELECTED = 1;
					}
					
					if(data.pagoModel.impRcvsp!=null && data.pagoModel.impRcvsp!=undefined && data.pagoModel.impRcvsp>0){
						handleDivFields([DIV_NAME_RCV],false,false);
						$("form#pagosVOForm #rcvSelect").val(data.pagoModel.numPeriodo);
						
						if(MV_SELECTED<=0){
							MV_SELECTED = 2;
						}else if(MV_SELECTED==1){
							MV_SELECTED = 4;
						}
							
					}
					
					if((data.pagoModel.numTrabregula!=undefined && data.pagoModel.numTrabregula!=null && data.pagoModel.numTrabregula>0)
						|| data.pagoModel.numAltas!=undefined && data.pagoModel.numAltas!=null && data.pagoModel.numAltas>0
						|| data.pagoModel.numBajas!=undefined && data.pagoModel.numBajas!=null && data.pagoModel.numBajas>0
						|| data.pagoModel.numModifsalario!=undefined && data.pagoModel.numModifsalario!=null && data.pagoModel.numModifsalario>0
					
					){
						handleDivFields([DIV_NAME_MA],false,false);
						$("form#pagosVOForm #maSelect").val(data.pagoModel.numPeriodo);
						
						if(MV_SELECTED<=0){
							MV_SELECTED = 3;
						}else{
							
							if(MV_SELECTED==1){
								MV_SELECTED = 5;
							}else if(MV_SELECTED==4){
								MV_SELECTED = 6;
							}
						}
					}
					
					$("form#pagosVOForm #tpMovimiento").val(MV_SELECTED);
					
					/*Hiddens*/
					$("form#pagosVOForm #cvePresentacorr").val(data.pagoModel.cvePresentacorr);
					$("form#pagosVOForm #indTipopago").val(data.pagoModel.indTipopago);
					$("form#pagosVOForm #cveRevpagos").val(data.pagoModel.cveRevpagos);
					
				}catch(error){
					alert("::"+error+"::");
				}
				
				$("#pagosVOForm #saveData").attr("disabled","disabled");
				$("#pagosVOForm #modifyData").attr("disabled",null);
				$("#pagosVOForm #deleteData").attr("disabled",null);
				$('#:checked').attr("checked",false);
				
			}
			
			desbloquear();
		}).error(function(data){ 
			validarSesionExpirada(data);
			alert("error" + data);
			desbloquear();
		});
		
	}else{alert("Seleccione un elemento de la lista");}	
}


/**
 * Indica la fecha máxima retroactiva del calendario 
 * de pagos. 
 */
function setMinCalendarDay(){
	 var fechaMinDate = document.getElementById("fechaMinDateCalendar").value;
	 
	 var fechaArray = fechaMinDate.split("/");
	 
	 $(function () { 
		 $("form#pagosVOForm,#fechaPago").datepicker('option', 'minDate', new Date(Number(fechaArray[2]), Number(fechaArray[1]) - 1, Number(fechaArray[0]))); 
		}); 
	
}

var dataPagosDetalle;
function recuperaDetallePagoId(folioSua,tipoDocumento,periodo,fechaPagoDat){
	

		var sCrtRevPagos = '{}';
		var oCrtRevPagos = jQuery.parseJSON(sCrtRevPagos);
		
		oCrtRevPagos.periodo=periodo;
		oCrtRevPagos.folioSua=folioSua;
		//oCrtRevPagos.tipoDocumento=tipoDocumento;
		oCrtRevPagos.fecPago=fechaPagoDat;
		bloquear();
		
		$.postJSON("pagos/recuperaDetalleCobranzaPago.do", oCrtRevPagos, function(data) {			
			
			dataPagosDetalle=data;
			generaTablaDetallePagos(data);
			desbloquear();
		 }).error(function(datas){ 
				validarSesionExpirada(datas);
				desbloquear();
		});


	
}

function generaTablaDetallePagos(datos){
	
	
	
	
	$("#contenedorDetalleCobranzaPagos").dialog({
		autoOpen: false,
		modal:true,
		resizable:true,
		draggable: true,
		width: 700,
		closeOnEscape: false,
		title:"Detalle Cobranza Pagos",
		close:function(event,ui){
			
		},
		buttons: {
			"Salir": function() {									
				$("#contenedorDetalleCobranzaPagos").dialog("close");
			}
		}
	});
	
	
	
	$("#contenedorDetalleCobranzaPagos").dialog('open');
	
	$("#dtDetalleCobranzaPago").dataTable( {
		"aaData": dataPagosDetalle,
		"bAutoWidth" : false,
		bFilter : false,
		bJQueryUI : true,
		"bDestroy": true,
		bSort: false,
		"sScrollX": "100%", 
		"sScrollXInner": "400%",
		"fnInfoCallback": function( oSettings, iStart, iEnd, iMax, iTotal, sPre ) {			   
			
		  },
		"aoColumns" : [{				
			"sTitle" : "Folio Sua",
			"mDataProp" : "folioSua",
			"sClass": "dtCenterClassColumn",
            "sWidth":"120px",
			aTargets: [0]
		},{				
			"sTitle" : "NSS",
			"mDataProp" : "nss",
			"sClass": "dtCenterClassColumn",
			"sWidth":"150px"
		},{				
			"sTitle" : "Periodo",
			"mDataProp" : "periodo",
			"sClass": "dtCenterClassColumn",
			"sWidth":"150px"
		},{				
			"sTitle" : "Fecha Pago",
			"mDataProp" : "fecPago",
			"sClass": "dtCenterClassColumn",
			"sWidth":"150px"
		},{				
			"sTitle" : "Fecha Carga",
			"mDataProp" : "fecCarga",
			"sClass": "dtCenterClassColumn",
			"sWidth":"150px"
		},{				
			"sTitle" : "DV",
			"mDataProp" : "dv",
			"sClass": "dtCenterClassColumn",
			"sWidth":"150px"
		},{				
			"sTitle" : "Reg Patronal",
			"mDataProp" : "rp",
			"sClass": "dtCenterClassColumn",
			"sWidth":"150px"
		},{				
			"sTitle" : "Mod",
			"mDataProp" : "mod",
			"sClass": "dtCenterClassColumn",
			"sWidth":"150px"
		},{				
			"sTitle" : "Tipo Documento",
			"mDataProp" : "tipoDocumento",
			"sClass": "dtCenterClassColumn",
			"sWidth":"150px"
		},{				
			"sTitle" : "Tipo Documento COP",
			"mDataProp" : "tipoDocCOP",
			"sClass": "dtCenterClassColumn",
			"sWidth":"150px"
		},{				
			"sTitle" : "Tipo Documento RCV",
			"mDataProp" : "tipoDocRCV",
			"sClass": "dtCenterClassColumn",
			"sWidth":"150px"
		},{				
			"sTitle" : "Imp Cuota Fija",
			"mDataProp" : "impCuotaFija",
			"sClass": "dtCenterClassColumn",
			"sWidth":"200px"
		},{				
			"sTitle" : "Imp Exedente 3SMGDF",
			"mDataProp" : "tipoDocumento",
			"sClass": "dtCenterClassColumn",
			"sWidth":"200px"
		},{				
			"sTitle" : "Imp Presta Diner",
			"mDataProp" : "impPrestaDinero",
			"sClass": "dtCenterClassColumn",
			"sWidth":"250px"
		},{				
			"sTitle" : "Imp Gasto Med Pens",
			"mDataProp" : "tipoDocumento",
			"sClass": "dtCenterClassColumn",
			"sWidth":"250px"
		},{				
			"sTitle" : "Imp Riesgos Trabajo",
			"mDataProp" : "impRiesgosTrabajo",
			"sClass": "dtCenterClassColumn",
			"sWidth":"250px"
		},{				
			"sTitle" : "Imp Validez Vida",
			"mDataProp" : "impValidezVida",
			"sClass": "dtCenterClassColumn",
			"sWidth":"250px"
		},{				
			"sTitle" : "Imp Guarderias Presco",
			"mDataProp" : "tipoDocumento",
			"sClass": "dtCenterClassColumn",
			"sWidth":"250px"
		},{				
			"sTitle" : "Imp Subtotal COP",
			"mDataProp" : "impSubtotalCOP",
			"sClass": "dtCenterClassColumn",
			"sWidth":"200px"
		},{				
			"sTitle" : "Imp Actualiza COP",
			"mDataProp" : "impActualizaCOP",
			"sClass": "dtCenterClassColumn",
			"sWidth":"200px"
		},{				
			"sTitle" : "Imp Recargos COP",
			"mDataProp" : "impRecargosCOP",
			"sClass": "dtCenterClassColumn",
			"sWidth":"200px"
		},{				
			"sTitle" : "Imp Total COP",
			"mDataProp" : "impTotalCOP",
			"sClass": "dtCenterClassColumn",
			"sWidth":"150px"
		},{				
			"sTitle" : "Imp Retiro RCV",
			"mDataProp" : "impRetiroRCV",
			"sClass": "dtCenterClassColumn",
			"sWidth":"200px"
		},{				
			"sTitle" : "Imp Ces Antiguedad",
			"mDataProp" : "impCesAntiedad",
			"sClass": "dtCenterClassColumn",
			"sWidth":"200px"
		},{				
			"sTitle" : "Imp Subtotal RCV",
			"mDataProp" : "impSubtotalRCV",
			"sClass": "dtCenterClassColumn",
			"sWidth":"200px"
		},{				
			"sTitle" : "Imp Actualiza RCV",
			"mDataProp" : "impActualizaRCV",
			"sClass": "dtCenterClassColumn",
			"sWidth":"200px"
		},{				
			"sTitle" : "Imp Recargos RCV",
			"mDataProp" : "impRegarcosRCV",
			"sClass": "dtCenterClassColumn",
			"sWidth":"200px"
		},{				
			"sTitle" : "Imp Total RCV",
			"mDataProp" : "impTotalRCV",
			"sClass": "dtCenterClassColumn",
			"sWidth":"200px"
		}]});
	
	
}