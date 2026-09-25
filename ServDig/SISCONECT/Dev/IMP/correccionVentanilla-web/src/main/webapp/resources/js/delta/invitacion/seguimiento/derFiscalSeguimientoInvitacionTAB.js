function deshabilitaComponentesDerFiscalSITAB() {	
	$("form#formDerFiscalSITAB #reqfecDerFiscalSITAB").css("display", "none");
	$("form#formDerFiscalSITAB #errorfecDerFiscalSITAB").css("display", "none");
	$("form#formDerFiscalSITAB #reqReferenciaDerFiscalSITAB").css("display", "none");	
	$("form#formDerFiscalSITAB #fechaDerFiscalSITAB").addClass('red');
	$("form#formDerFiscalSITAB #referenciaDerFiscalSITAB").addClass('red');
	$("form#formDerFiscalSITAB #errorfecNotificaDerFiscalSITAB").css("display", "none");
	$("#btnLimpiaFechaDerFiscalSITAB").hide();
}

function validaFechaDerFiscalSITAB() {
	var fecIni = $("form#formDetalleSegInvitacion #hidFechOfiDet").val();
	var fecIni2 = $("form#formSeguimientoInvitacionTAB #inFecNotificaOfiSITAB").val();
	var fecFin = $("form#formDerFiscalSITAB #fechaDerFiscalSITAB").val();
	
	if (fecIni2!='') {
		retorno = jsValidaFechas(fecIni2, fecFin);
	} else {
		retorno = jsValidaFechas(fecIni, fecFin);
	}	
	
	if(retorno){	
		$("form#formDerFiscalSITAB #fechaDerFiscalSITAB").val(fecFin);
		$("form#formDerFiscalSITAB #errorfecDerFiscalSITAB").css("display", "none");
		$("form#formDerFiscalSITAB #errorfecNotificaDerFiscalSITAB").css("display", "none");
		$("#btnLimpiaFechaDerFiscalSITAB").show();
	}else{			 
		$("form#formDerFiscalSITAB #fechaDerFiscalSITAB").val('');
		$("#btnLimpiaFechaDerFiscalSITAB").hide();
		if (fecIni2!='') {
			$("form#formDerFiscalSITAB #errorfecNotificaDerFiscalSITAB").css("display", "block");
		} else{
			$("form#formDerFiscalSITAB #errorfecDerFiscalSITAB").css("display", "block");
		}		
	}
}

function validaGuardaderFiscalSITAB() {	
	var fechaDerFiscalReq = $("form#formDerFiscalSITAB #fechaDerFiscalSITAB").val();
	var refReq = $("form#formDerFiscalSITAB #referenciaDerFiscalSITAB").val();
	var contadorValida = 0; 
	
	if (fechaDerFiscalReq=='' || fechaDerFiscalReq==null) {
		$("form#formDerFiscalSITAB #reqfecDerFiscalSITAB").css("display", "block");
		contadorValida++;
	} 
	if (refReq=='' || refReq==null) {
		$("form#formDerFiscalSITAB #reqReferenciaDerFiscalSITAB").css("display", "block");
		contadorValida++;
	} 
	if (contadorValida==0) {
		abrirConfirmacionGenerica(" \u00BF Est\u00e1 seguro que desea guardar los datos capturados?", guardaDerFiscalSITAB);		
		deshabilitaComponentesDerFiscalSITAB();
	}
}

function guardaDerFiscalSITAB() {
	
	var oForm = $("#formDerFiscalSITAB").toObject({mode : 'first'});
	oForm.fechaNotOficioTx=$("form#formSeguimientoInvitacionTAB #inFecNotificaOfiSITAB").val();
	oForm.txObservaciones=$("form#formSeguimientoInvitacionTAB #taObservacionesSITAB").val()
		
	$.postJSON("seginvitacion/derivaFiscalSegInvitacion.do",oForm,function(data) { 
		if (data!=null) {
			$("form#formSeguimientoInvitacionTAB #inFecDerFiscaSITAB").val($("form#formDerFiscalSITAB #fechaDerFiscalSITAB").val());
				
			setTabDesHabilitado('derivaSubSeguimientoInvitacionTAB_segInvi');
			setTabDesHabilitado('autAviSeguimientoInvitacionTAB_segInvi');
			setTabDesHabilitado('cancelaSeguimientoInvitacionTAB_segInvi');
				
			$("form#formDerFiscalSITAB #fechaDerFiscalSITAB").attr("disabled", "disabled");
			$("form#formDerFiscalSITAB #referenciaDerFiscalSITAB").attr("disabled", "disabled");
			$("form#formDerFiscalSITAB #btnConfirmarDerFiscalSITABr").attr("disabled", "disabled");			
			
			deshabilitaComponentesDerFiscalSITAB();
			bloqueaPantallaSegInvitacionTAB();
			
			$('form#formDerFiscalSITAB input[type=text]').removeClass("red");

			oDgExitoGenericoSITAB.dialog("open");
		}					
	 }).error(function(datas){ 
		validarSesionExpirada(datas);			
	});
	
}

function habilitaCamposReqDerFiscalSITAB() {
	$("form#formDerFiscalSITAB #fechaDerFiscalSITAB").attr("disabled", false);
	$("form#formDerFiscalSITAB #referenciaDerFiscalSITAB").attr("disabled", false);
	$("form#formDerFiscalSITAB #btnConfirmarDerFiscalSITABr").attr("disabled", false);
}

function limpiaFechaDerFiscalSITAB() {
	$("form#formDerFiscalSITAB #fechaDerFiscalSITAB").val('');
//	$("form#formDerFiscalSITAB #fechaDerFiscalSITAB").addClass('red');
	$("#btnLimpiaFechaDerFiscalSITAB").hide();
	
}