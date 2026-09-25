function validaGuardaFormSITAB() {	
	abrirConfirmacionGenerica("\u00BF Est\u00e1 seguro que desea guardar los datos capturados ?", guardaFormSITAB);	
}

function guardaFormSITAB() {	 
	var oForm = $("#formSeguimientoInvitacionTAB").toObject({mode : 'first'});
	
	oForm.fechaNotOficioTx=$("form#formSeguimientoInvitacionTAB #inFecNotificaOfiSITAB").val();
	
	$.postJSON("seginvitacion/guardaSeguimientoInvitacion.do",oForm,function(data) { 
		if (data!=null) {			
			oDgExitoGenericoSITAB.dialog("open");			
			bloqueaPantallaSegInvitacionTAB();
		}					
	}).error(function(datas){ 
		validarSesionExpirada(datas);				 
	}); 	
 }

function bloqueaPantallaSegInvitacionTAB() {
	$("form#formSeguimientoInvitacionTAB #taObservacionesSITAB").removeClass('red');
	$("form#formSeguimientoInvitacionTAB #inFecNotificaOfiSITAB").removeClass('red');
	$("form#formSeguimientoInvitacionTAB #inFecNotificaOfiSITAB").attr("disabled", "disabled");
	$("form#formSeguimientoInvitacionTAB #taObservacionesSITAB").attr("disabled", "disabled");
	$("form#formSeguimientoInvitacionTAB #btnGuardarSITAB").attr("disabled", "disabled");
	$("#btnLimpiaFechaNotificacionSITAB").hide();
}

function validaFechaNotOficioSITAB() {	
	$("form#formSeguimientoInvitacionTAB #errorValidaFechaNot").html('');
	$("#btnLimpiaFechaNotificacionSITAB").hide();
	
	var validaRetorno;
	var validaRetornoTAB;
	
	var fechaEmisionInvi = $("form#formDetalleSegInvitacion #hidFechOfiDet").val();
	var fechaNotOficio = $("form#formSeguimientoInvitacionTAB #inFecNotificaOfiSITAB").val();
	var fechaCancelaInvi = $("form#formCancelaSITAB #inFecCancelacionSITAB").val();
	var fechaDerSubdelegacion = $("form#formDerSubdelegacionSITAB #fechaDerSubdelegacionSITAB").val();
	var fechaDerFiscalizacion = $("form#formDerFiscalSITAB #fechaDerFiscalSITAB").val();
	var fechaAutAvisoDictamen =	$("form#formAudAviDicSITAB #fechaAudAviDicSITAB").val();
	var validaFechaNotificacion = 0;
	
	if(fechaNotOficio!=""){
		setTabHabilitado('derivaFisSeguimientoInvitacionTAB_segInvi');
	}else{
		setTabDesHabilitado('derivaFisSeguimientoInvitacionTAB_segInvi');
	}
	
	
	if (fechaEmisionInvi!='') {
		validaRetorno = jsValidaFechas(fechaEmisionInvi, fechaNotOficio);
		if (!validaRetorno) {
			$("form#formSeguimientoInvitacionTAB #errorValidaFechaNot").html('<label> La fecha no puede ser menor a la fecha de emisi&oacute;n </label>');
			$("form#formSeguimientoInvitacionTAB #inFecNotificaOfiSITAB").val('');
			$("form#formCancelaSITAB #inFecCancelacionSITAB").val('');
			setTabDesHabilitado('derivaFisSeguimientoInvitacionTAB_segInvi');
			validaFechaNotificacion++;
		} 		
	} 
	
	if (fechaCancelaInvi!='') {		
		validaRetornoTAB = jsValidaFechas(fechaCancelaInvi, fechaNotOficio);
		if (validaRetornoTAB) {
			validaFechaNotificacion++;
			$("form#formCancelaSITAB #inFecCancelacionSITAB").val('');			
			deshabilitaCamposCancelaSITAB();	
			$("#btnLimpiaFechaNotificacionSITAB").show();
			$("form#formCancelaSITAB #errorFechaCancelacionSITAB").css("display", "block");
		}
	} else if (fechaDerSubdelegacion!='') {
		validaRetornoTAB = jsValidaFechas(fechaDerSubdelegacion, fechaNotOficio);
		if (validaRetornoTAB) {
			validaFechaNotificacion++;
			$("form#formDerSubdelegacionSITAB #fechaDerSubdelegacionSITAB").val('');			
			deshabilitaComponentesDerSubdelSITAB();		
			$("#btnLimpiaFechaNotificacionSITAB").show();
			$("form#formDerSubdelegacionSITAB #errorFechaNotificaDerSubdelSITAB").css("display", "block");
		}
	} else if (fechaDerFiscalizacion!='') {	
		validaRetornoTAB = jsValidaFechas(fechaDerFiscalizacion, fechaNotOficio);
		if (validaRetornoTAB) {
			validaFechaNotificacion++;
			$("form#formDerFiscalSITAB #fechaDerFiscalSITAB").val('');			
			deshabilitaComponentesDerFiscalSITAB();
			$("#btnLimpiaFechaNotificacionSITAB").show();
			$("form#formDerFiscalSITAB #errorfecNotificaDerFiscalSITAB").css("display", "block");
		}
	} else if (fechaAutAvisoDictamen!='') {		
		validaRetornoTAB = jsValidaFechas(fechaAutAvisoDictamen, fechaNotOficio);
		if (validaRetornoTAB) {
			validaFechaNotificacion++;
			$("form#formAudAviDicSITAB #fechaAudAviDicSITAB").val('');			
			deshabilitaCamposAutAviSITAB();
			$("#btnLimpiaFechaNotificacionSITAB").show();
			$("form#formAudAviDicSITAB #errorFechaNotificaAviDicSITAB").css("display", "block");
		}
	} 	
	
	if (validaFechaNotificacion==0) {
		if (rolActivoSITAB==JEFE_OF_CORR_Y_DIC || rolActivoSITAB==JEFE_DEP_AUD_PAT) {
			setTabHabilitado('derivaSubSeguimientoInvitacionTAB_segInvi');
			setTabHabilitado('derivaFisSeguimientoInvitacionTAB_segInvi');
			setTabHabilitado('autAviSeguimientoInvitacionTAB_segInvi');					
		}		
		$("#btnLimpiaFechaNotificacionSITAB").show();
//		$("form#formSeguimientoInvitacionTAB #inFecNotificaOfiSITAB").removeClass('red');
	}
}

function limpiaFechaNotificacionSITAB() {
	setTabDesHabilitado('derivaSubSeguimientoInvitacionTAB_segInvi');
	setTabDesHabilitado('derivaFisSeguimientoInvitacionTAB_segInvi');
	setTabDesHabilitado('autAviSeguimientoInvitacionTAB_segInvi');
	
	
	$("form#formSeguimientoInvitacionTAB #inFecNotificaOfiSITAB").val('');
//	$("form#formSeguimientoInvitacionTAB #inFecNotificaOfiSITAB").addClass('red');
	$("#btnLimpiaFechaNotificacionSITAB").hide();
	
}
 