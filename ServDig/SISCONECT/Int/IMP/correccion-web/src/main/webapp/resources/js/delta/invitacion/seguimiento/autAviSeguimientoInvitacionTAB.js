function deshabilitaCamposAutAviSITAB() {
	$("form#formAudAviDicSITAB #reqFechaAviDicSITAB").css("display", "none");
	$("form#formAudAviDicSITAB #errorFechaAviDicSITAB").css("display", "none");
	$("form#formAudAviDicSITAB #reqNumeroAudAviDicSITAB").css("display", "none");
	$("form#formAudAviDicSITAB #errorPeriodoFecAvisoDictSITAB").css("display", "none");
	$("form#formAudAviDicSITAB #reqPeriodoFecAvisoDictSITAB").css("display", "none");
	$("form#formAudAviDicSITAB #errorFechaNotificaAviDicSITAB").css("display", "none");
	$("form#formAudAviDicSITAB #fechaAudAviDicSITAB").addClass('red');
	$("#btnLimpiaFechaAviDictSITAB").hide();
	$("form#formAudAviDicSITAB #fechaIniAudAviDicSITAB").addClass('red');
	$("form#formAudAviDicSITAB #fechaFinAudAviDicSITAB").addClass('red');
	$("form#formAudAviDicSITAB #numeroAudAviDicSITAB").addClass('red');
}

function validaFechaAutAviDictSITAB() {
	var fecIni = $("form#formDetalleSegInvitacion #hidFechOfiDet").val();
	var fecIni2 = $("form#formSeguimientoInvitacionTAB #inFecNotificaOfiSITAB").val();
	var fecFin = $("form#formAudAviDicSITAB #fechaAudAviDicSITAB").val();
	
	if (fecIni2!='') {
		retorno = jsValidaFechas(fecIni2, fecFin);
	} else {
		retorno = jsValidaFechas(fecIni, fecFin);
	}		
	
	if(retorno){	
		$("form#formAudAviDicSITAB #fechaAudAviDicSITAB").val(fecFin);
		$("form#formAudAviDicSITAB #errorFechaAviDicSITAB").css("display", "none");
		$("form#formAudAviDicSITAB #errorFechaNotificaAviDicSITAB").css("display", "none");
//		$("form#formAudAviDicSITAB #fechaAudAviDicSITAB").removeClass('red');
		$("#btnLimpiaFechaAviDictSITAB").show();
	}else{			 
		$("form#formAudAviDicSITAB #fechaAudAviDicSITAB").val('');
		if (fecIni2!='') {
			$("form#formAudAviDicSITAB #errorFechaNotificaAviDicSITAB").css("display", "block");
		} else {
			$("form#formAudAviDicSITAB #errorFechaAviDicSITAB").css("display", "block");
		}		
	}
}

function validaPerFechasAutAviDicSITAB() {
	
	var fecPerIni = $("form#formAudAviDicSITAB #fechaIniAudAviDicSITAB").val();
	var fecPerFin = $("form#formAudAviDicSITAB #fechaFinAudAviDicSITAB").val();
	
	var retornoValPer = true;
	
	if (fecPerIni!='' && fecPerFin!='') {
		retornoValPer = jsValidaFechas(fecPerIni, fecPerFin);
	}		
	
	if(retornoValPer){	
		$("form#formAudAviDicSITAB #fechaFinAudAviDicSITAB").val(fecPerFin);
		$("form#formAudAviDicSITAB #errorPeriodoFecAvisoDictSITAB").css("display", "none");
	}else{			 
		$("form#formAudAviDicSITAB #fechaFinAudAviDicSITAB").val('');
		$("form#formAudAviDicSITAB #errorPeriodoFecAvisoDictSITAB").css("display", "block");
	}
}

function validaGuardarAutAviSITAB() {
	
	$("form#formAudAviDicSITAB #reqFechaAviDicSITAB").css("display", "none");
	$("form#formAudAviDicSITAB #reqNumeroAudAviDicSITAB").css("display", "none");
	$("form#formAudAviDicSITAB #reqPeriodoFecAvisoDictSITAB").css("display", "none");
	
	var fecAudReq = $("form#formAudAviDicSITAB #fechaAudAviDicSITAB").val();
	var fecIniReq = $("form#formAudAviDicSITAB #fechaIniAudAviDicSITAB").val();
	var fecFinReq = $("form#formAudAviDicSITAB #fechaFinAudAviDicSITAB").val();
	var fecNumAud = $("form#formAudAviDicSITAB #numeroAudAviDicSITAB").val();	
	var contadorAvisDict = 0;
	
	if (fecAudReq=='') {
		$("form#formAudAviDicSITAB #reqFechaAviDicSITAB").css("display", "block");
		contadorAvisDict++;
	} 
	if (fecIniReq=='' || fecFinReq=='') {
		$("form#formAudAviDicSITAB #reqPeriodoFecAvisoDictSITAB").css("display", "block");
		contadorAvisDict++;
	} 
	if (fecNumAud=='') {
		$("form#formAudAviDicSITAB #reqNumeroAudAviDicSITAB").css("display", "block");
		contadorAvisDict++;
	} 
	if (contadorAvisDict==0){
		abrirConfirmacionGenerica("\u00BF Est\u00e1 seguro que desea guardar los datos capturados?", guardaFormAutAviSITAB);		
	}
}

function guardaFormAutAviSITAB () {
		var oForm = $("#formAudAviDicSITAB").toObject({mode : 'first'});
		oForm.fechaNotOficioTx=$("form#formSeguimientoInvitacionTAB #inFecNotificaOfiSITAB").val();
		oForm.txObservaciones=$("form#formSeguimientoInvitacionTAB #taObservacionesSITAB").val()
		
		$.postJSON("seginvitacion/autAviDictamenSegInvitacion.do",oForm,function(data) { 
			if (data!=null) {
				$("form#formSeguimientoInvitacionTAB #inFecAviDictSITAB").val($("form#formAudAviDicSITAB #fechaAudAviDicSITAB").val());
//				$("form#formSeguimientoInvitacionTAB #inFecSolCorrIniSITAB").val(fechaIniAutAviDict);
//				$("form#formSeguimientoInvitacionTAB #inFecSolCorrFinSITAB").val(fechaFinAutAviDict);
				
				setTabDesHabilitado('derivaSubSeguimientoInvitacionTAB_segInvi');
				setTabDesHabilitado('cancelaSeguimientoInvitacionTAB_segInvi');
				setTabDesHabilitado('derivaFisSeguimientoInvitacionTAB_segInvi');
				
				$("form#formAudAviDicSITAB #fechaAudAviDicSITAB").attr("disabled", "disabled");
				$("form#formAudAviDicSITAB #fechaIniAudAviDicSITAB").attr("disabled", "disabled");
				$("form#formAudAviDicSITAB #fechaFinAudAviDicSITAB").attr("disabled", "disabled");
				$("form#formAudAviDicSITAB #numeroAudAviDicSITAB").attr("disabled", "disabled");	
				$("form#formAudAviDicSITAB #btnGuardarAudAviDicSITAB").attr("disabled", "disabled");
				deshabilitaCamposAutAviSITAB();
				bloqueaPantallaSegInvitacionTAB();
				
				$("form#formAudAviDicSITAB #fechaAudAviDicSITAB").removeClass('red');				
				$("form#formAudAviDicSITAB #fechaIniAudAviDicSITAB").removeClass('red');
				$("form#formAudAviDicSITAB #fechaFinAudAviDicSITAB").removeClass('red');
				$("form#formAudAviDicSITAB #numeroAudAviDicSITAB").removeClass('red');
				
				
				$("form#formSeguimientoInvitacionTAB #inFecSolCorrIniSITAB").val($("form#formAudAviDicSITAB #fechaIniAudAviDicSITAB").val());
				$("form#formSeguimientoInvitacionTAB #inFecSolCorrFinSITAB").val($("form#formAudAviDicSITAB #fechaFinAudAviDicSITAB").val());
								
				oDgExitoGenericoSITAB.dialog("open");
			}					
		 }).error(function(datas){ 
			validarSesionExpirada(datas);			
		});
}

function habilitaCamposReqAviDictSITAB() {
	$("form#formAudAviDicSITAB #fechaAudAviDicSITAB").attr("disabled", false);
	$("form#formAudAviDicSITAB #fechaIniAudAviDicSITAB").attr("disabled", false);
	$("form#formAudAviDicSITAB #fechaFinAudAviDicSITAB").attr("disabled", false);
	$("form#formAudAviDicSITAB #numeroAudAviDicSITAB").attr("disabled", false);	
	$("form#formAudAviDicSITAB #btnGuardarAudAviDicSITAB").attr("disabled", false);
}

function limpiaFechaAviDictSITAB() {
	$("form#formAudAviDicSITAB #fechaAudAviDicSITAB").val('');
//	$("form#formAudAviDicSITAB #fechaAudAviDicSITAB").addClass('red');
	$("#btnLimpiaFechaAviDictSITAB").hide();
}