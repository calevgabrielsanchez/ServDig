var idDtInvitaciones = "#dtListaInvitaciones";
var idGenDetalleSegInvitacion = "#dgGenDetalleSegInvitacion";

var oDtInvitaciones;
var oGenDetalleSegInvitacion;

var JEFE_OF_CORRECCION = 4;
var JEFE_OF_CORR_Y_DIC = 6;
var JEFE_DEP_AUD_PAT = 7;
var AUDITOR = 2;
var SUPERVISOR_OF_CORRECCION= 10;
var rolActivoSITAB = 0;

/**
 * Iniciamos la configuracion de los componentes visuales de jQuery.
 */
$(document).ready(function() {
	 	 	
	llenaSelectTipoInv();
	
	$("form#formSegInvitacionMain #inFechaEmisionIniInv").datepicker( { dateFormat: 'dd-mm-yy' });
	$("form#formSegInvitacionMain #inFechaEmisionFinInv").datepicker( { dateFormat: 'dd-mm-yy' });	
	
	//Fecha notificacion del oficio
	$("form#formSeguimientoInvitacionTAB #inFecNotificaOfiSITAB").datepicker( { dateFormat: 'dd-mm-yy' });
	
	//Fecha de cancelacion
	$("form#formCancelaSITAB #inFecCancelacionSITAB").datepicker( { dateFormat: 'dd-mm-yy' });	
	
	//Fecha der fiscalizacion
	$("form#formDerFiscalSITAB #fechaDerFiscalSITAB").datepicker( { dateFormat: 'dd-mm-yy' });
	
	//Fechas de aut del aviso de dictamen
	$("form#formAudAviDicSITAB #fechaAudAviDicSITAB").datepicker( {
		dateFormat: 'dd-mm-yy',
		onSelect: function(dateText, inst) { 
			$("form#formAudAviDicSITAB #fechaIniAudAviDicSITAB").val("");
			$("form#formAudAviDicSITAB #fechaFinAudAviDicSITAB").val("");
			
			$("form#formAudAviDicSITAB #fechaIniAudAviDicSITAB").datepicker('option', 'maxDate', $("form#formAudAviDicSITAB #fechaAudAviDicSITAB").val());
			$("form#formAudAviDicSITAB #fechaFinAudAviDicSITAB").datepicker('option', 'maxDate', $("form#formAudAviDicSITAB #fechaAudAviDicSITAB").val());
		}	
	});
	$("form#formAudAviDicSITAB #fechaIniAudAviDicSITAB").datepicker( { dateFormat: 'dd-mm-yy' });
	$("form#formAudAviDicSITAB #fechaFinAudAviDicSITAB").datepicker( { dateFormat: 'dd-mm-yy' });
	
	//Fecha de reapertura de folio 
	$("form#formReabrirFolioSITAB #fechaReaFolioSITAB").datepicker( { dateFormat: 'dd-mm-yy' });
	
	//Fecha derivar subdelegacion
	$("form#formDerSubdelegacionSITAB #fechaDerSubdelegacionSITAB").datepicker( { dateFormat: 'dd-mm-yy' });	
	
	//Ocultar boton seguimiento
	$("#divBtnSegInvi").css("display", "none");
	
	$("form#formSegInvitacionMain #inFechaEmisionIniInv").datepicker( { dateFormat: 'dd-mm-yy' });
	$("form#formSegInvitacionMain #inFechaEmisionFinInv").datepicker( { dateFormat: 'dd-mm-yy' });	
	
	deshabilitaCamposSITAB();

		$("form#formSegInvitacionMain #inFechaEmisionIniInv, " +
				"form#formSegInvitacionMain #inFechaEmisionFinInv, " +
				"form#formSeguimientoInvitacionTAB #inFecNotificaOfiSITAB, " +
				"form#formCancelaSITAB #inFecCancelacionSITAB, " +
				"form#formDerFiscalSITAB #fechaDerFiscalSITAB, " +
				"form#formAudAviDicSITAB #fechaAudAviDicSITAB, " +
				"form#formAudAviDicSITAB #fechaIniAudAviDicSITAB, " +
				"form#formAudAviDicSITAB #fechaFinAudAviDicSITAB, " +
				"form#formReabrirFolioSITAB #fechaReaFolioSITAB, " +
				"form#formDerSubdelegacionSITAB #fechaDerSubdelegacionSITAB").datepicker('option', 'maxDate',getFechaServidor());
		


		$("form#formCancelaSITAB #inFecCancelacionSITAB, " +
				"form#formDerFiscalSITAB #fechaDerFiscalSITAB, " +
				"form#formSeguimientoInvitacionTAB #inFecNotificaOfiSITAB, " +
				"form#formAudAviDicSITAB #fechaAudAviDicSITAB, " +
				"form#formDerSubdelegacionSITAB #fechaDerSubdelegacionSITAB").datepicker('option', 'minDate', getFechaServidorMenos45Dias());

	
	oGenDetalleSegInvitacion = $(idGenDetalleSegInvitacion).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 1030,
		closeOnEscape: false,
		open:function(event, ui){
			
		},
		close:function(event,ui){
			consultaInvitaciones();
		},
		buttons: {
			"Salir": function() {									
				salirDetalleSeguimientoInvitacion();
			}
		}
	});
});

function llenaSelectTipoInv(){
	
	$.postJSON("seginvitacion/selecTiposCorr.do", null, function(data) {		
		var myselect=document.getElementById("tiposCorr");
		myselect.options.length = 1;
		
		for(var i = 0 ; i < data.length ; i++){			
			myselect.add(new Option(data[i][2], data[i][2]));
		}		
	});
}

function consultaInvitaciones() {
	if(oDtInvitaciones != undefined){
		oDtInvitaciones.fnDestroy();
		  
	  }
	
	oDtInvitaciones = $(idDtInvitaciones).dataTable(
	{
		bJQueryUI : true,
		bFilter : false,
		bInfo : true,
		bSort : false,			
		"bPaginate" : true,
		"bAutoWidth" : false,
		"bServerSide" : false,
		"aoColumns" : [ {
			fnRender :function(oObj){
				var retVal = '<input type="radio" value="' +
				oObj.aData['cveInvitacion'] +'" id="radioTable" class="radioClase" name="radio" /> ';
				return retVal;
			}, 
			aTargets: [0]
			},{
				"sWidth": "20%",
				"sTitle" : "Folio de invitaci&oacute;n",
				"mDataProp" : "nuFolioInvitacion",
				"sClass": "dtCenterClassColumn"
			},{
				"sWidth": "15%",
				"sTitle" : "Antecedente",
				"mDataProp" : "folioAntecedente",
				"sClass": "dtCenterClassColumn"
			},{
				"sWidth": "15%",
				"sTitle" : "Registro Patronal ",				
				"sClass": "dtCenterClassColumn",
				//"mDataProp" : "satPatron.registroPatronal"
				mDataProp:function ( source, type, val ) {
						if(source.satPatron==null){
							return "";
						}else {
							return source.satPatron.registroPatronal;	
						}
				 }
			},{
				"sWidth": "20%",
				"sTitle" : "Raz&oacute;n Social ",				
				"sClass": "dtCenterClassColumn",
				//"mDataProp" : "satPatron.razonSocial"
				mDataProp:function ( source, type, val ) {
				   if(source.satPatron==null){
						return "";
					}else {
						return source.satPatron.razonSocial;	
					}
				 }
			},{
				"sWidth": "10%",
				"sTitle" : "N&uacute;mero Oficio ",
				"mDataProp" : "nuOficioinv",
				"sClass": "dtCenterClassColumn"
			},{
				"sWidth": "15%",
				"sTitle" : "Fecha Emisi&oacute;n ",
				"mDataProp" : "fechaEmision",
				"sClass": "dtCenterClassColumn"
			}],
				"bProcessing" : true,
				"sAjaxSource" : 'seginvitacion/consultaInvicaciones.do',
				"fnServerData" : function(sSource,aoData,fnCallback) {
					
					bloquear();
					
					var wrapper = new Object();
					wrapper.aoData = aoData;

					var oForm = $("#formSegInvitacionMain").toObject({mode : 'first'});
					wrapper.oForm = oForm;
					$.postJSON(sSource,wrapper,function(data) {
						fnCallback(data);
						desbloquear();
						$("#divBtnSegInvi").css("display", "block");
					 }).error(function(datas){ 
						validarSesionExpirada(datas);				 
					});
				}
			});
}


function deshabilitaCamposSITAB() {
	$("form:#formSegInvitacionMain #reqPeriodoFechasSITAB").css("display", "none");
	$("form:#formSegInvitacionMain #reqTipoCorrSITAB").css("display", "none");
	$("form:#formSegInvitacionMain #errorPeriodoFechasSITAB").css("display", "none");
	$("form:#formSegInvitacionMain #errorPeriodoFechasAnioSITAB").css("display", "none");
}

function validaSegInvitacion() {	
	
	$("form:#formSegInvitacionMain #reqPeriodoFechasSITAB").css("display", "none");
	$("form:#formSegInvitacionMain #reqTipoCorrSITAB").css("display", "none");	
	
	if ($("#tiposCorr").val()!='0') {
		if ($("#inFolioInv").val()!='') {
			abrirAvisoGenericoSITAB("Combinaci&oacute;n de filtros no permitida");
			$("#inFolioInv").val("");					
		} else if ($("#inFechaEmisionIniInv").val()=='' || $("#inFechaEmisionFinInv").val()=='') {
			$("form:#formSegInvitacionMain #reqPeriodoFechasSITAB").css("display", "block");			
		} else {
			consultaInvitaciones();
		}
	} else if ($("#inFolioInv").val()!='') {
		if ($("#inFechaEmisionIniInv").val()!='') {
			abrirAvisoGenericoSITAB("Combinaci&oacute;n de filtros no permitida");
			$("#inFechaEmisionIniInv").val("");
			$("#inFechaEmisionFinInv").val("");
		} else if ($("#inFechaEmisionFinInv").val()!='') {
			abrirAvisoGenericoSITAB("Combinaci&oacute;n de filtros no permitida");
			$("#inFechaEmisionFinInv").val("");
			$("#inFechaEmisionIniInv").val("");
		} else {
			consultaInvitaciones();
		}				
	} else if ($("#inFechaEmisionIniInv").val()!='' || $("#inFechaEmisionFinInv").val()!='') {
		if ($("#inFechaEmisionIniInv").val()=='') {
			$("form:#formSegInvitacionMain #reqPeriodoFechasSITAB").css("display", "block");
		} else if ($("#inFechaEmisionFinInv").val()=='') {
			$("form:#formSegInvitacionMain #reqPeriodoFechasSITAB").css("display", "block");
		} else if ($("#tiposCorr").val()=='0') {
			$("form:#formSegInvitacionMain #reqTipoCorrSITAB").css("display", "block");
		} else {
			consultaInvitaciones();
		} 
	} else if ($("#inFolioInv").val()=='' && $("#tiposCorr").val()=="0" &&  $("#inFechaEmisionIniInv").val()=='' &&  $("#inFechaEmisionFinInv").val()=='') {
		abrirAvisoGenericoSITAB("Se requiere seleccione uno de los filtros");		
	} 
}

function jsValidaFecFinalInv(){	
	 $("form:#formSegInvitacionMain #errorPeriodoFechasSITAB").css("display", "none");
	 var retorno = true;
	 
	 var fecIni = $("form#formSegInvitacionMain #inFechaEmisionIniInv").val();
	 var fecFinal = $("form#formSegInvitacionMain #inFechaEmisionFinInv").val();	 
	 
	 if (fecIni!='' && fecFinal!='') {
		 retorno = jsValidaFechas(fecIni, fecFinal);
	 }
	
	 if(retorno){	
		 $("form#formSegInvitacionMain #inFechaEmisionFinInv").val(fecFinal);
	 }else{			 
		 $("form#formSegInvitacionMain #inFechaEmisionFinInv").val('');
		 $("form:#formSegInvitacionMain #errorPeriodoFechasSITAB").css("display", "block");
	 }
}

function jsValidaFechasLimiteInv(){	
	$("form:#formSegInvitacionMain #errorPeriodoFechasAnioSITAB").css("display", "none");	 
	
	var ini = $("form#formSegInvitacionMain #inFechaEmisionIniInv").val();
	var fin = $("form#formSegInvitacionMain #inFechaEmisionFinInv").val();
	var resp = true;
	if(ini != '' && fin != ''){		
		var array_fechaIni = ini.split("-"); 
		var array_fechaFin = fin.split("-"); 
		
		var anioIni = parseInt(array_fechaIni[2]);
		var anioFin = parseInt(array_fechaFin[2]);		
		
		if(anioIni < anioFin){
			resp = false;
		}else if(anioIni == anioFin){
			resp = true;
		}
		$("#inFolioInv").val('');
	}
	if(resp == false){
		$("form#formSegInvitacionMain #inFechaEmisionIniInv").val("");
		$("form#formSegInvitacionMain #inFechaEmisionFinInv").val("");
		$("form:#formSegInvitacionMain #errorPeriodoFechasAnioSITAB").css("display", "block");	 
	}
}

function jsLimpiaFiltrosRequeridosSITAB() {
	$("form#formSegInvitacionMain #inFechaEmisionIniInv").val('');
	$("form#formSegInvitacionMain #inFechaEmisionFinInv").val('');
	$("#tiposCorr").val('0');
}

function seguimientoInvitacion() {
	var idInvitacion = $('#:checked').val();
	
	if (idInvitacion!=undefined) {
		setTabDesHabilitado('derivaFisSeguimientoInvitacionTAB_segInvi');
		var sVarSeg = '{"cveInvitacion":'+idInvitacion+'}';
		var clase = jQuery.parseJSON(sVarSeg);
		$.postJSON_Sync("seginvitacion/detalleInvitacion.do", clase, function(data) {
			
			$("form#formDetalleSegInvitacion #idDetalleInvitacion").val(data.cveInvitacion);
			$("form#formDetalleSegInvitacion #hidFechOfiDet").val(data.fechaOfInvitacionTx);
			
			$("form#formDetalleSegInvitacion #lbFolInviDet").html('<label>' + data.nuFolioInvitacion + '</label>');
			$("form#formDetalleSegInvitacion #lbFechOfiDet").html('<label>' + data.fechaOfInvitacionTx + '</label>');
			$("form#formDetalleSegInvitacion #ldNumOfiInviDet").html('<label>' + data.nuOficioinv + '</label>');
			$("form#formDetalleSegInvitacion #lbPeriodoInviIni").html('<label>' + data.fechaIncial + '</label>');
			$("form#formDetalleSegInvitacion #lbPeriodoInviFin").html('<label>' + data.fechaFinal + '</label>');
			
			$("form#formDetalleSegInvitacion #lbRegPatInviDet").html('<label>' + data.satPatron.registroPatronal + '</label>');
			$("form#formDetalleSegInvitacion #lbRazSocialInviDet").html('<label>' + data.satPatron.razonSocial + '</label>');
			$("form#formDetalleSegInvitacion #lbDomInviDet").html('<label>' + data.domicilio + '</label>');
			$("form#formDetalleSegInvitacion #lbDomObra").html('<label>' + data.domicilioObra + '</label>');
			
			//TAB seguimientoInvitacionTAb.jsp			
			$("form#formSeguimientoInvitacionTAB #inCveInviSITAB").val(data.cveInvitacion);		
			$("form#formSeguimientoInvitacionTAB #inFecNotificaOfiSITAB").val(data.fechaNotOficioTx);
			
			if (data.fechaNotOficioTx!=null) {
				$("form#formSeguimientoInvitacionTAB #inFecNotificaOfiSITAB").attr("disabled", "disabled");
				setTabHabilitado('derivaFisSeguimientoInvitacionTAB_segInvi');
				
			} else {
				$("form#formSeguimientoInvitacionTAB #inFecNotificaOfiSITAB").attr("disabled", false);
				$("form#formSeguimientoInvitacionTAB #inFecNotificaOfiSITAB").addClass('red');
				setTabDesHabilitado('derivaFisSeguimientoInvitacionTAB_segInvi');
				
			}
			$("form#formSeguimientoInvitacionTAB #taObservacionesSITAB").attr("disabled", false);
			$("form#formSeguimientoInvitacionTAB #btnGuardarSITAB").attr("disabled", false);
			
			$("form#formSeguimientoInvitacionTAB #inFecCancelaOfiSITAB").val(data.fechaCancelacionTx);
			$("form#formSeguimientoInvitacionTAB #inFecDerSubdelSITAB").val(data.fechaDerSubdelegacionTx);
			$("form#formSeguimientoInvitacionTAB #inFecAviDictSITAB").val(data.fechaAisoDictamenTx);
			$("form#formSeguimientoInvitacionTAB #inFecDerFiscaSITAB").val(data.fechaPaiTx); 
			$("form#formSeguimientoInvitacionTAB #inFecSolCorrSITAB").val(data.fechaCorreccionTx);	
			$("form#formSeguimientoInvitacionTAB #inFecSolCorrIniSITAB").val(data.fechaInicioCorreccionTx);
			$("form#formSeguimientoInvitacionTAB #inFecSolCorrFinSITAB").val(data.fechaFinCorreccionTx);
			$("form#formSeguimientoInvitacionTAB #taObservacionesSITAB").val(data.txObservaciones);
			$("#btnLimpiaFechaNotificacionSITAB").hide();			
			$("form#formSeguimientoInvitacionTAB #taObservacionesSITAB").addClass('red');
			$("form#formCancelaSITAB #inRefCancelacionSITAB").addClass('red');
			
			//TAB cancelaSeguimientoInvitacionTAB.jsp
			$("form#formCancelaSITAB #hidCveInvitacionCancelacionSITAB").val(data.cveInvitacion);
			$("form#formCancelaSITAB #inFuncionarioRegCancelacionSITAB").html('<label>' + data.cveUsuario + '</label>');
			$("form#formCancelaSITAB #inFecCancelacionSITAB").val(data.fechaCancelacionTx);
			$("form#formCancelaSITAB #selFuncionarioAutCancelacionSITAB").val(data.cveUsuarioAutoriza);
			$("form#formCancelaSITAB #idMotivoCancelacion").val("-1");			
			$("form#formCancelaSITAB #inRefCancelacionSITAB").val(data.numOficioCancelacion);					
								
			//TAB derFiscalSeguimientoInvitacionTAB.jsp
			$("form#formDerFiscalSITAB #funcionarioDerFiscalSITAB").html('<label>' + data.cveUsuario + '</label>');
			$("form#formDerFiscalSITAB #hidCveInvitacionSITAB").val(data.cveInvitacion);			
			$("form#formDerFiscalSITAB #referenciaDerFiscalSITAB").val(data.txRfrpai);
			$("form#formDerFiscalSITAB #fechaDerFiscalSITAB").val(data.fechaPaiTx);
			
			//TAB autAviSeguimientoInvitacionTAB
			$("form#formAudAviDicSITAB #funcionarioRegAudAviDicSITAB").html('<label>' + data.cveUsuario + '</label>');
			$("form#formAudAviDicSITAB #cveInvitacionAudAviDictSITAB").val(data.cveInvitacion);			
			$("form#formAudAviDicSITAB #fechaAudAviDicSITAB").val(data.fechaAisoDictamenTx);
			$("form#formAudAviDicSITAB #fechaIniAudAviDicSITAB").val(data.fechaPeriodoIniDicTx);
			$("form#formAudAviDicSITAB #fechaFinAudAviDicSITAB").val(data.fechaPeriodoFinDicTx);
			$("form#formAudAviDicSITAB #numeroAudAviDicSITAB").val(data.numAvisoDictamen);
			
			//TAB derSubdelSeguimientoInvitacionTAB			
			$("form#formDerSubdelegacionSITAB #cveSubdDelegOriginalSITAB").val(data.cveFkSubdelegacion);
			$("form#formDerSubdelegacionSITAB #funRegDerSubdelegacionSITAB").html('<label>' + data.cveUsuario + '</label>');
			$("form#formDerSubdelegacionSITAB #cveInvitacionDerSubdelegSITAB").val(data.cveInvitacion);					
			
			if (data.idVariosRPS=="1") {
				var tituloFscal="Domicilio Fiscal";
				$("#divTablaListaDomicilios").css("display", "block");
				$("form#formDetalleSegInvitacion #domInviVariable").html('<label>' + tituloFscal  + '</label>');
				jsCargaUbicacionesRPS(data.cveInvitacion);
			} else {
				if(oDtListaDomicilios != undefined){					
//					oDtListaDomicilios.fnDestroy();
					$("#divTablaListaDomicilios").css("display", "none");
				}
				var tituloLaboral="Domicilio del Centro de Trabajo";
				$("form#formDetalleSegInvitacion #domInviVariable").html('<label>' + tituloLaboral + '</label>');
			}
			
			cargaFuncionarioAutorizaCanSITAB();
			deshabilitaCamposCancelaSITAB();
			deshabilitaComponentesDerFiscalSITAB();
			deshabilitaCamposAutAviSITAB();
			deshabilitaComponentesDerSubdelSITAB();
			limpiaDerSubdelSITAB();
			
			habilitaCamposReqDerSubdelegSITAB();
			habilitaCamposReqCancelaSITAB();
			habilitaCamposReqDerFiscalSITAB();
			habilitaCamposReqAviDictSITAB();
			
			/**
			 * Inicializamos TABS
			 */
			$("#accessTabs").val("seguimientoInvitacionTAB_segInvi-cancelaSeguimientoInvitacionTAB_segInvi-derivaSubSeguimientoInvitacionTAB_segInvi-derivaFisSeguimientoInvitacionTAB_segInvi-autAviSeguimientoInvitacionTAB_segInvi");
			changeTab('seguimientoInvitacionTAB_segInvi');
			
			if (data.fechaCorreccionTx!=null) {
				setTabDesHabilitado('seguimientoInvitacionTAB_segInvi');
				setTabDesHabilitado('cancelaSeguimientoInvitacionTAB_segInvi');
				setTabDesHabilitado('derivaSubSeguimientoInvitacionTAB_segInvi');
				setTabDesHabilitado('derivaFisSeguimientoInvitacionTAB_segInvi');
				setTabDesHabilitado('autAviSeguimientoInvitacionTAB_segInvi');
				bloqueaPantallaSegInvitacionTAB();
			} else {
				setTabHabilitado('seguimientoInvitacionTAB_segInvi');
				setTabHabilitado('cancelaSeguimientoInvitacionTAB_segInvi');
				setTabHabilitado('derivaSubSeguimientoInvitacionTAB_segInvi');
				setTabHabilitado('autAviSeguimientoInvitacionTAB_segInvi');
				
			
				//Habilitar tabs de acuerdo al rol del usuario
//				console.log("va "+data.fechaNotOficioTx);
//				if (data.fechaNotOficioTx==null) {
//					console.log(data.fechaNotOficioTx);
//					setTabDesHabilitado('derivaFisSeguimientoInvitacionTAB_segInvi');								
//				} else {
//					setTabHabilitado('derivaFisSeguimientoInvitacionTAB_segInvi');
//				}
				
				rolActivoSITAB = data.rolUsuario;
				/*if (!(rolActivoSITAB==JEFE_OF_CORR_Y_DIC || rolActivoSITAB==JEFE_DEP_AUD_PAT)) {
					setTabDesHabilitado('cancelaSeguimientoInvitacionTAB_segInvi');
					setTabDesHabilitado('derivaFisSeguimientoInvitacionTAB_segInvi');
//					$("#cancelaSeguimientoInvitacionTAB_segInviLI").css("display", "block");
//					$("#derivaFisSeguimientoInvitacionTAB_segInviLI").css("display", "block");
				} else if (!rolActivoSITAB==JEFE_OF_CORRECCION) {
					setTabDesHabilitado('cancelaSeguimientoInvitacionTAB_segInvi');
//					$("#cancelaSeguimientoInvitacionTAB_segInviLI").css("display", "block");
				}*/
				
				if (rolActivoSITAB==AUDITOR) {
					setTabDesHabilitado('cancelaSeguimientoInvitacionTAB_segInvi');
					setTabDesHabilitado('derivaFisSeguimientoInvitacionTAB_segInvi');
					setTabDesHabilitado('derivaSubSeguimientoInvitacionTAB_segInvi');
					setTabDesHabilitado('autAviSeguimientoInvitacionTAB_segInvi');
					
				} else if (rolActivoSITAB==JEFE_OF_CORR_Y_DIC || rolActivoSITAB==JEFE_DEP_AUD_PAT) {
					setTabHabilitado('seguimientoInvitacionTAB_segInvi');
					setTabHabilitado('cancelaSeguimientoInvitacionTAB_segInvi');
//					setTabHabilitado('derivaFisSeguimientoInvitacionTAB_segInvi');
					setTabHabilitado('derivaSubSeguimientoInvitacionTAB_segInvi');
					setTabHabilitado('autAviSeguimientoInvitacionTAB_segInvi');
				}else{
					setTabDesHabilitado('seguimientoInvitacionTAB_segInvi');
					setTabDesHabilitado('cancelaSeguimientoInvitacionTAB_segInvi');
					setTabDesHabilitado('derivaFisSeguimientoInvitacionTAB_segInvi');
					setTabDesHabilitado('derivaSubSeguimientoInvitacionTAB_segInvi');
					setTabDesHabilitado('autAviSeguimientoInvitacionTAB_segInvi');
				}
				
				
				//DEl documento REPORTEGESTION 2808 incdencia 31
//				if (data.fechaNotOficioTx==null) {
//					setTabDesHabilitado('derivaSubSeguimientoInvitacionTAB_segInvi');
//					setTabDesHabilitado('derivaFisSeguimientoInvitacionTAB_segInvi');	
//					setTabDesHabilitado('autAviSeguimientoInvitacionTAB_segInvi');	
//				} else {
//					setTabHabilitado('derivaSubSeguimientoInvitacionTAB_segInvi');
//					setTabHabilitado('derivaFisSeguimientoInvitacionTAB_segInvi');
//					setTabHabilitado('autAviSeguimientoInvitacionTAB_segInvi');
//					
//				}
				
			}												
			oGenDetalleSegInvitacion.dialog("open");

		}).error(function(data){ 
			validarSesionExpirada(data);
			alert("error" + data);
		});			
	} else {
		alert ("seleccione una invitacion")
	} 	
	
	if($("#inFecNotificaOfiSITAB").val()==""){
		setTabDesHabilitado('derivaFisSeguimientoInvitacionTAB_segInvi');		
	}
	
}




