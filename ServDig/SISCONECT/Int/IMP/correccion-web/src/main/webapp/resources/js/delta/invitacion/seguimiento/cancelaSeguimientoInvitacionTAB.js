function guardarCanelacionSITAB() {
	var oForm = $("#formCancelaSITAB").toObject({mode : 'first'});
	oForm.fechaNotOficioTx=$("form#formSeguimientoInvitacionTAB #inFecNotificaOfiSITAB").val();
	oForm.txObservaciones=$("form#formSeguimientoInvitacionTAB #taObservacionesSITAB").val();
		
	$.postJSON("seginvitacion/cancelaSeguimientoInvitacion.do",oForm,function(data) { 
		if (data!=null) {
			$("form#formSeguimientoInvitacionTAB #inFecCancelaOfiSITAB").val($("form#formCancelaSITAB #inFecCancelacionSITAB").val());								
			setTabDesHabilitado('derivaSubSeguimientoInvitacionTAB_segInvi');
			setTabDesHabilitado('autAviSeguimientoInvitacionTAB_segInvi');
			setTabDesHabilitado('derivaFisSeguimientoInvitacionTAB_segInvi');
				
			$("form#formCancelaSITAB #inRefCancelacionSITAB").attr("disabled","disabled");
			$("form#formCancelaSITAB #idMotivoCancelacion").attr("disabled","disabled"); 
			$("form#formCancelaSITAB #inFecCancelacionSITAB").attr("disabled","disabled");
			$("form#formCancelaSITAB #selFuncionarioAutCancelacionSITAB").attr("disabled","disabled");
			$("form#formCancelaSITAB #btnGuardaCancelaSITAB").attr("disabled","disabled");
			deshabilitaCamposCancelaSITAB();
			bloqueaPantallaSegInvitacionTAB();
			
			$('form#formCancelaSITAB input[type=text]').removeClass("red");
			oDgExitoGenericoSITAB.dialog("open");
		}					
	 }).error(function(datas){ 
		validarSesionExpirada(datas);				 
	});
}

function habilitaCamposReqCancelaSITAB() {
	$("form#formCancelaSITAB #inRefCancelacionSITAB").attr("disabled",false);
	$("form#formCancelaSITAB #idMotivoCancelacion").attr("disabled",false); 
	$("form#formCancelaSITAB #inFecCancelacionSITAB").attr("disabled",false);
	$("form#formCancelaSITAB #selFuncionarioAutCancelacionSITAB").attr("disabled",false);
	$("form#formCancelaSITAB #btnGuardaCancelaSITAB").attr("disabled",false);
}

function validaFechaCancelacionSITAB() {
	var fecIni = $("form#formSeguimientoInvitacionTAB #inFecNotificaOfiSITAB").val();
	var fecIni2 = $("form#formDetalleSegInvitacion #hidFechOfiDet").val();
	var fecFinal = $("form#formCancelaSITAB #inFecCancelacionSITAB").val();	 
	
	$("form#formCancelaSITAB #errorFechaUnoCancelacionSITAB").css("display", "none");
	$("form#formCancelaSITAB #errorFechaCancelacionSITAB").css("display", "none");
	
	var retorno = true;
	
	if (fecIni!='') {
		retorno = jsValidaFechas(fecIni, fecFinal);
		if (!retorno) {
			$("form#formCancelaSITAB #inFecCancelacionSITAB").val('');
			$("form#formCancelaSITAB #errorFechaCancelacionSITAB").css("display", "block");
		} else {
			$("#btnLimpiaFechaCancelaSITAB").show();
		}
	}  else {
		retorno = jsValidaFechas(fecIni2, fecFinal);
		if (!retorno) {					
			$("form#formCancelaSITAB #errorFechaUnoCancelacionSITAB").val('');
			$("form#formCancelaSITAB #inFecCancelacionSITAB").val('');
			$("form#formCancelaSITAB #errorFechaUnoCancelacionSITAB").css("display", "block");
		} else {
//			$("form#formCancelaSITAB #inFecCancelacionSITAB").removeClass('red');
			$("#btnLimpiaFechaCancelaSITAB").show();
		}
	}	
}

function jsValidaFechas(fecIni, fecFin){
	
	var array_fechaIni = fecIni.split("-"); 
	var array_fechaFin = fecFin.split("-"); 
	
	var anioIni = parseInt(array_fechaIni[2],10);
	var anioFin = parseInt(array_fechaFin[2],10);
	
	var mesIni = parseInt(array_fechaIni[1],10);
	var mesFin = parseInt(array_fechaFin[1],10);
	
	var diaIni = parseInt(array_fechaIni[0],10);
	var diaFin = parseInt(array_fechaFin[0],10);
	
	
	if(anioIni > anioFin){
		return false;
	}else {
		if(anioFin == anioIni){
			if(mesIni > mesFin){
				return false;
			}else{
				if(mesIni == mesFin){
					
					if(diaIni > diaFin){
						
						return false;
					}else{
						if(diaIni <= diaFin){
							
							return true;
						}
					}
				}else{
					if(mesIni < mesFin){
						return true;
					}
			  }
			}
		}else{
			if(anioIni < anioFin){
				return true;
			}
		}
	}
	
}

function validaCamposReqCancelaSITAB() {
	
//	alert($("form#formCancelaSITAB #idMotivoCancelacion").val());
	var motCance = $("form#formCancelaSITAB #idMotivoCancelacion").val(); 
	var fechaReq = $("form#formCancelaSITAB #inFecCancelacionSITAB").val();
	var funcionarioReq = $("form#formCancelaSITAB #selFuncionarioAutCancelacionSITAB").val();
	var refCancelacionReq = $("form#formCancelaSITAB #inRefCancelacionSITAB").val();
	var contadorCancelaSITAB = 0;
	
	$("form#formCancelaSITAB #reqFechaCancelacionSITAB").css("display", "none");
	$("form#formCancelaSITAB #reqMotivoCancelacionSITAB").css("display", "none");
	$("form#formCancelaSITAB #reqFuncionarioCancelacionSITAB").css("display", "none");
	$("form#formCancelaSITAB #reqReferenciaCancelacionSITAB").css("display", "none");
	
	
	if (fechaReq=='') {
		$("form#formCancelaSITAB #reqFechaCancelacionSITAB").css("display", "block");
		contadorCancelaSITAB++;
	} 
	if (funcionarioReq=='-1') {
		$("form#formCancelaSITAB #reqFuncionarioCancelacionSITAB").css("display", "block");
		contadorCancelaSITAB++;
	} 
	if (motCance=="-1") {
		$("form#formCancelaSITAB #reqMotivoCancelacionSITAB").css("display", "block");
		contadorCancelaSITAB++;
	} 
	if (refCancelacionReq=='') {
		$("form#formCancelaSITAB #reqReferenciaCancelacionSITAB").css("display", "block");
		contadorCancelaSITAB++;
	} 
	if (contadorCancelaSITAB==0) {
		abrirConfirmacionGenerica(" \u00BF Est\u00e1 seguro que desea guardar los datos capturados?", guardarCanelacionSITAB);	
	}
}

function deshabilitaCamposCancelaSITAB() {
	$("form#formCancelaSITAB #errorFechaCancelacionSITAB").css("display", "none");
	$("form#formCancelaSITAB #errorFechaUnoCancelacionSITAB").css("display", "none");
	$("form#formCancelaSITAB #reqFechaCancelacionSITAB").css("display", "none");
	$("form#formCancelaSITAB #reqMotivoCancelacionSITAB").css("display", "none");
	$("form#formCancelaSITAB #reqFuncionarioCancelacionSITAB").css("display", "none");		
	$("form#formCancelaSITAB #reqReferenciaCancelacionSITAB").css("display", "none");
	$("#btnLimpiaFechaCancelaSITAB").hide();
	$("form#formCancelaSITAB #inFecCancelacionSITAB").addClass('red');
}

function limpiaFechaCancelaSITAB(){
	$("form#formCancelaSITAB #inFecCancelacionSITAB").val('');
//	$("form#formCancelaSITAB #inFecCancelacionSITAB").addClass('red');
	$("#btnLimpiaFechaCancelaSITAB").hide();
}