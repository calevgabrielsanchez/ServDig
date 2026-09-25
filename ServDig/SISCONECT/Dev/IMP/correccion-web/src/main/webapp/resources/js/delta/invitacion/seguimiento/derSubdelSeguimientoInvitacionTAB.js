function validarRegistroPatronalFiscalSITAB(){	
	var regPatronalDerSubSITAB = $("form#formDerSubdelegacionSITAB #regPatronDerSubdelegacionSITAB").val();
	
	if (regPatronalDerSubSITAB=='') {
		$('form#formDerSubdelegacionSITAB #reqRegPatronDerSubdelSITAB').css("display", "block");
	} else {
//		$("#dgGenDetalleSegInvitacion").hide();
		bloquear();
		$('form#formDerSubdelegacionSITAB #btnValidaRegistroPatronSITAB').attr("disabled", "disabled");
		$('form#formDerSubdelegacionSITAB #reqRegPatronDerSubdelSITAB').css("display", "none");
		$('form#formDerSubdelegacionSITAB #errorIgualDerSubdelSITAB').css("display", "none");
//		bloquear();
		$.postJSON("seginvitacion/validaPatronSITAB.do", regPatronalDerSubSITAB, function(data) { 
			if(data != null && data.razonSocial != null){
				limpiaDerSubdelSITAB();
				var subdelegacionActual =  $("form#formDerSubdelegacionSITAB #cveSubdDelegOriginalSITAB").val();
				var subdelegacionDestino = data.ubicacion.municipio.sacSubdelegacion.cvePk;
					
				if (subdelegacionActual==subdelegacionDestino) {
					$('form#formDerSubdelegacionSITAB #errorIgualDerSubdelSITAB').css("display", "block");
				} else {
					$("form#formDerSubdelegacionSITAB #regPatronDerSubdelegacionSITAB").val(data.registroPatronal);
					$("form#formDerSubdelegacionSITAB #razonSocialDerSubdelegacionSITAB").val(data.razonSocial);
					$("form#formDerSubdelegacionSITAB #cvePatronDerSubdelegSITAB").val(data.cvePK);
					$("form#formDerSubdelegacionSITAB #destinoSubdelDerSubdelegacionSITAB").val(data.ubicacion.municipio.sacSubdelegacion.nomNombre);
					$("form#formDerSubdelegacionSITAB #cveSubdDelegSITAB").val(data.ubicacion.municipio.sacSubdelegacion.cvePk);		
					
					$('form#formDerSubdelegacionSITAB #divBtnServiceDomicilio').css("display", "block");					
					eliminaDomicilioSesion();	
					$("#dgGenDetalleSegInvitacion").show();
				}	

				//se tuvo que regresar de nuevo a como estaba
//				$("form#formDerSubdelegacionSITAB #regPatronDerSubdelegacionSITAB").val(data.registroPatronal);
//				$("form#formDerSubdelegacionSITAB #razonSocialDerSubdelegacionSITAB").val(data.razonSocial);
//				$("form#formDerSubdelegacionSITAB #cvePatronDerSubdelegSITAB").val(data.cvePK);
//				$("form#formDerSubdelegacionSITAB #destinoSubdelDerSubdelegacionSITAB").val(data.ubicacion.municipio.sacSubdelegacion.nomNombre);
//				$("form#formDerSubdelegacionSITAB #cveSubdDelegSITAB").val(data.ubicacion.municipio.sacSubdelegacion.cvePk);		
//				
//				$('form#formDerSubdelegacionSITAB #divBtnServiceDomicilio').css("display", "block");					
//				eliminaDomicilioSesion();	
//				$("#dgGenDetalleSegInvitacion").show();
			
			}
			else {
				limpiaDerSubdelSITAB();
				deshabilitaComponentesDerSubdelSITAB();
//				$("#dgGenDetalleSegInvitacion").show();
				abrirAvisoGenericoSITAB("El registro Patronal no existe o esta dado de baja");									
			}
			$('form#formDerSubdelegacionSITAB #btnValidaRegistroPatronSITAB').attr("disabled", false);
		}).error(function(data){ 
//			$("#dgGenDetalleSegInvitacion").show();
			abrirAvisoGenericoSITAB("Ocurrio un error al validar el Registro Patronal. Intentelo una vez mas");
			$('form#formDerSubdelegacionSITAB #btnValidaRegistroPatronSITAB').attr("disabled", false);
			limpiaDerSubdelSITAB();
			validarSesionExpirada(data);			
		}).complete(function(){						
//			$("#dgGenDetalleSegInvitacion").show();	
			desbloquear();										
		});
	}
	
}

function cargaDomicilioDerSubSITAB(){
	var resultado = openWindowregistraDomicilioInegi(getAppContextParaJS(),'/seguimiento/seginvitacion/solicitudDomGeograficoSITAB.do',"DOM_DERIVAR_SUBD_SITAB","actualizaDomicilios()");
	var registroPatronal=$("form#formDerSubdelegacionSITAB #cvePatronDerSubdelegSITAB").val();
	var sPatron = '{"patron":'+'"DOM_DERIVAR_SUBD_SITAB"}';
	//colocar una llave o recuperar registro patronal
	//var sPatron = '{"patron":'+'"'+registroPatronal+'"}';
	var crcPatron = jQuery.parseJSON(sPatron);
	bloquear();
	$.postJSON("seginvitacion/actualizaDomGeograficoSITAB.do", crcPatron, function(data) {	
		if (data!=null) {			
			$('form#formDerSubdelegacionSITAB,#calleDerSubdelegacionSITAB').val(data.dgVialidadByCveViaPrin.nomVia);
			$('form#formDerSubdelegacionSITAB,#numExtDerSubdelegacionSITAB').val(data.numextnum);
			$('form#formDerSubdelegacionSITAB,#numIntDerSubdelegacionSITAB').val(data.numintnum);
			$('form#formDerSubdelegacionSITAB,#coloniaDerSubdelegacionSITAB').val(data.dgAsentamiento.nomAsen);		
			$('form#formDerSubdelegacionSITAB,#codigoPostalDerSubdelegacionSITAB').val(data.dgCodigosPostales.id.codigo);
		} 	
		desbloquear();		
	}).error(function(data){ 
		desbloquear();
		alert("Error: Conexión no disponible, intente de nuevo");
	}).complete(function(){
		
	});	
}

function actualizaDomicilios(){
	var registroPatronal=$("form#formDerSubdelegacionSITAB #cvePatronDerSubdelegSITAB").val();
	var sPatron = '{"patron":'+'"DOM_DERIVAR_SUBD_SITAB"}';
	//colocar una llave o recuperar registro patronal
	//var sPatron = '{"patron":'+'"'+registroPatronal+'"}';
	var crcPatron = jQuery.parseJSON(sPatron);
	bloquear();
	$.postJSON("seginvitacion/actualizaDomGeograficoSITAB.do", crcPatron, function(data) {	
		if (data!=null) {			
			$('form#formDerSubdelegacionSITAB,#calleDerSubdelegacionSITAB').val(data.dgVialidadByCveViaPrin.nomVia);
			$('form#formDerSubdelegacionSITAB,#numExtDerSubdelegacionSITAB').val(data.numextnum);
			$('form#formDerSubdelegacionSITAB,#numIntDerSubdelegacionSITAB').val(data.numintnum);
			$('form#formDerSubdelegacionSITAB,#coloniaDerSubdelegacionSITAB').val(data.dgAsentamiento.nomAsen);		
			$('form#formDerSubdelegacionSITAB,#codigoPostalDerSubdelegacionSITAB').val(data.dgCodigosPostales.id.codigo);
		} 	
		desbloquear();		
	}).error(function(data){ 
		desbloquear();
		alert("Error: Conexión no disponible, intente de nuevo");
	}).complete(function(){
		
	});	
}


function eliminaDomicilioSesion() {
	var crtInvitacion = $("form#formDerSubdelegacionSITAB").toObject({mode:'first'});
	$.postJSON("seginvitacion/eliminaDomicilioSessionSITAB.do", crtInvitacion, function(data) {					
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
		//Instrucciones para el complete													
	});
}

function validaFechaDerSubdelSITAB() {
	$('form#formDerSubdelegacionSITAB #reqFechaDerSubdelSITAB').css("display", "none");
	var fecIni = $("form#formDetalleSegInvitacion #hidFechOfiDet").val();
	var fecIni2 = $("form#formSeguimientoInvitacionTAB #inFecNotificaOfiSITAB").val();
	var fecFin = $("form#formDerSubdelegacionSITAB #fechaDerSubdelegacionSITAB").val();
		
	if (fecIni2!='') {
		retorno = jsValidaFechas(fecIni2, fecFin);
	} else {
		retorno = jsValidaFechas(fecIni, fecFin);
	}	
	
	if(retorno){	
		$("form#formDerSubdelegacionSITAB #fechaDerSubdelegacionSITAB").val(fecFin);
		$('form#formDerSubdelegacionSITAB #errorFechaDerSubdelSITAB').css("display", "none");
		$("form#formDerSubdelegacionSITAB #errorFechaNotificaDerSubdelSITAB").css("display", "none");
//		$("form#formDerSubdelegacionSITAB #fechaDerSubdelegacionSITAB").removeClass('red');
		$('#btnLimpiaFechaSubdelegSITAB').show();
	}else{
		$("form#formDerSubdelegacionSITAB #fechaDerSubdelegacionSITAB").val('');
		if (fecIni2!='') {
			$("form#formDerSubdelegacionSITAB #errorFechaNotificaDerSubdelSITAB").css("display", "block");
		} else {
			$('form#formDerSubdelegacionSITAB #errorFechaDerSubdelSITAB').css("display", "block");
		}				
	}
}

function validaFormDerSubdelegSITAB() {
	$('form#formDerSubdelegacionSITAB #reqFechaDerSubdelSITAB').css("display", "none");
	$('form#formDerSubdelegacionSITAB #reqDestinoDerSubdelSITAB').css("display", "none");
	$('form#formDerSubdelegacionSITAB #reqReferenciaDerSubdelSITAB').css("display", "none");	
	$('form#formDerSubdelegacionSITAB #reqCalleDerSubdelSITAB').css("display", "none");
	$('form#formDerSubdelegacionSITAB #reqColoniaDerSubdelSITAB').css("display", "none");
	$('form#formDerSubdelegacionSITAB #reqNumExtDerSubdelSITAB').css("display", "none");
	$('form#formDerSubdelegacionSITAB #reqCodPosDerSubdelSITAB').css("display", "none");
	
	var fechaDerSubdelReq = $('form#formDerSubdelegacionSITAB #fechaDerSubdelegacionSITAB').val();
	var destinoDerSubdelReq = $('form#formDerSubdelegacionSITAB #destinoSubdelDerSubdelegacionSITAB').val();
	var referDerSubdelReq = $('form#formDerSubdelegacionSITAB #referenciaDerSubdelegacionSITAB').val();
	var calleDomicilioReq = $('form#formDerSubdelegacionSITAB,#calleDerSubdelegacionSITAB').val();
	var contadorDerSubdel = 0;
	
	if (fechaDerSubdelReq=='') {
		$('form#formDerSubdelegacionSITAB #reqFechaDerSubdelSITAB').css("display", "block");
		contadorDerSubdel++;
	} 
	if (destinoDerSubdelReq=='') {
		$('form#formDerSubdelegacionSITAB #reqDestinoDerSubdelSITAB').css("display", "block");
		contadorDerSubdel++;
	} 
	if (referDerSubdelReq=='') {
		$('form#formDerSubdelegacionSITAB #reqReferenciaDerSubdelSITAB').css("display", "block");
		contadorDerSubdel++;
	} 
	if (calleDomicilioReq=='') {
		$('form#formDerSubdelegacionSITAB #reqCalleDerSubdelSITAB').css("display", "block");
		$('form#formDerSubdelegacionSITAB #reqColoniaDerSubdelSITAB').css("display", "block");
		$('form#formDerSubdelegacionSITAB #reqNumExtDerSubdelSITAB').css("display", "block");
		$('form#formDerSubdelegacionSITAB #reqCodPosDerSubdelSITAB').css("display", "block");
		contadorDerSubdel++;
	}
	
	if($('form#formDerSubdelegacionSITAB #regPatronDerSubdelegacionSITAB').val()!="" && $('form#formDerSubdelegacionSITAB #razonSocialDerSubdelegacionSITAB').val()==""){
		alert("Favor de validar el registro patronal nuevamente");
		contadorDerSubdel++;
	}
	
	
	
	if (contadorDerSubdel==0) {
		abrirConfirmacionGenerica(" \u00BF Est\u00e1 seguro que desea guardar los datos capturados?", guardarDerSubdelSITAB);		
	}
}


function limpiaCamposDeriva(){
	
	$('form#formDerSubdelegacionSITAB #razonSocialDerSubdelegacionSITAB').val("");
//	$('form#formDerSubdelegacionSITAB #calleDerSubdelegacionSITAB').val("");
//	$('form#formDerSubdelegacionSITAB #numExtDerSubdelegacionSITAB').val("");
//	$('form#formDerSubdelegacionSITAB #codigoPostalDerSubdelegacionSITAB').val("");
//	$('form#formDerSubdelegacionSITAB #coloniaDerSubdelegacionSITAB').val("");



	
}

function deshabilitaComponentesDerSubdelSITAB() {
	$('form#formDerSubdelegacionSITAB #reqFechaDerSubdelSITAB').css("display", "none");
	$('form#formDerSubdelegacionSITAB #errorFechaDerSubdelSITAB').css("display", "none");
	$('form#formDerSubdelegacionSITAB #reqDestinoDerSubdelSITAB').css("display", "none");
	$('form#formDerSubdelegacionSITAB #reqReferenciaDerSubdelSITAB').css("display", "none");
	$('form#formDerSubdelegacionSITAB #reqRegPatronDerSubdelSITAB').css("display", "none");
	$('form#formDerSubdelegacionSITAB #divBtnServiceDomicilio').css("display", "none");		
	$('form#formDerSubdelegacionSITAB #errorIgualDerSubdelSITAB').css("display", "none");
	$('form#formDerSubdelegacionSITAB #reqCalleDerSubdelSITAB').css("display", "none");
	$('form#formDerSubdelegacionSITAB #reqColoniaDerSubdelSITAB').css("display", "none");
	$('form#formDerSubdelegacionSITAB #reqNumExtDerSubdelSITAB').css("display", "none");
	$('form#formDerSubdelegacionSITAB #reqCodPosDerSubdelSITAB').css("display", "none");
	$("form#formDerSubdelegacionSITAB #errorFechaNotificaDerSubdelSITAB").css("display", "none");
	$("form#formDerSubdelegacionSITAB #fechaDerSubdelegacionSITAB").addClass('red');
	$('#btnLimpiaFechaSubdelegSITAB').hide();	
	$("form#formDerSubdelegacionSITAB #regPatronDerSubdelegacionSITAB").addClass('red');
	$('form#formDerSubdelegacionSITAB #referenciaDerSubdelegacionSITAB').addClass('red');
}

function guardarDerSubdelSITAB() {
	
		var oForm = $("#formDerSubdelegacionSITAB").toObject({mode : 'first'});
		oForm.fechaNotOficioTx=$("form#formSeguimientoInvitacionTAB #inFecNotificaOfiSITAB").val();
		oForm.txObservaciones=$("form#formSeguimientoInvitacionTAB #taObservacionesSITAB").val()
		
		$.postJSON("seginvitacion/derivarSubdelegacionSegInvitacion.do",oForm,function(data) { 
			if (data!=null) {
				$("form#formSeguimientoInvitacionTAB #inFecDerSubdelSITAB").val($('form#formDerSubdelegacionSITAB #fechaDerSubdelegacionSITAB').val());
				
				setTabDesHabilitado('derivaFisSeguimientoInvitacionTAB_segInvi');
				setTabDesHabilitado('autAviSeguimientoInvitacionTAB_segInvi');
				setTabDesHabilitado('cancelaSeguimientoInvitacionTAB_segInvi');
				
				$("form#formDerSubdelegacionSITAB #fechaDerSubdelegacionSITAB").attr("disabled", "disabled");
				
				$("form#formDerSubdelegacionSITAB #regPatronDerSubdelegacionSITAB").attr("disabled", "disabled");
				$("form#formDerSubdelegacionSITAB #destinoSubdelDerSubdelegacionSITAB").attr("disabled", "disabled");
				$("form#formDerSubdelegacionSITAB #referenciaDerSubdelegacionSITAB").attr("disabled", "disabled");
				$("form#formDerSubdelegacionSITAB #btnConfirmaDerSubdelSITAB").attr("disabled", "disabled");
				$('form#formDerSubdelegacionSITAB #btnValidaRegistroPatronSITAB').attr("disabled", "disabled");
				deshabilitaComponentesDerSubdelSITAB();	
				bloqueaPantallaSegInvitacionTAB();
				
				var imputTextPato = document.getElementById("regPatronDerSubdelegacionSITAB");
				imputTextPato.disabled = true;
				
				$("form#formDerSubdelegacionSITAB #regPatronDerSubdelegacionSITAB").removeClass('red')
				$("form#formDerSubdelegacionSITAB #fechaDerSubdelegacionSITAB").removeClass('red');				
				$("form#formDerSubdelegacionSITAB #regPatronDerSubdelegacionSITAB").removeClass('red');
				$('form#formDerSubdelegacionSITAB #referenciaDerSubdelegacionSITAB').removeClass('red');
				oDgExitoGenericoSITAB.dialog("open");
				
				
				
			}					
		 }).error(function(datas){ 
			validarSesionExpirada(datas);			
		});
}

function limpiaDerSubdelSITAB() {
	$('form#formDerSubdelegacionSITAB #fechaDerSubdelegacionSITAB').val("");
	$('form#formDerSubdelegacionSITAB #destinoSubdelDerSubdelegacionSITAB').val("");
	$('form#formDerSubdelegacionSITAB #referenciaDerSubdelegacionSITAB').val("");
	$('form#formDerSubdelegacionSITAB,#calleDerSubdelegacionSITAB').val("");
	$('form#formDerSubdelegacionSITAB,#numExtDerSubdelegacionSITAB').val("");
	$('form#formDerSubdelegacionSITAB,#numIntDerSubdelegacionSITAB').val("");
	$('form#formDerSubdelegacionSITAB,#coloniaDerSubdelegacionSITAB').val("");		
	$('form#formDerSubdelegacionSITAB,#codigoPostalDerSubdelegacionSITAB').val("");
	$("form#formDerSubdelegacionSITAB #razonSocialDerSubdelegacionSITAB").val("");
	$("form#formDerSubdelegacionSITAB #cvePatronDerSubdelegSITAB").val("");	
	$("form#formDerSubdelegacionSITAB #cveSubdDelegSITAB").val("");
	$("form#formDerSubdelegacionSITAB #regPatronDerSubdelegacionSITAB").val('');
}

function limpiaFechaDerSubdelegSITAB() {
	$("form#formDerSubdelegacionSITAB #fechaDerSubdelegacionSITAB").val('');
//	$("form#formDerSubdelegacionSITAB #fechaDerSubdelegacionSITAB").addClass('red');
	$('#btnLimpiaFechaSubdelegSITAB').hide();	
}

function habilitaCamposReqDerSubdelegSITAB() {
	$("form#formDerSubdelegacionSITAB #fechaDerSubdelegacionSITAB").attr("disabled", false);
	$("form#formDerSubdelegacionSITAB #destinoSubdelDerSubdelegacionSITAB").attr("disabled", false);
	$("form#formDerSubdelegacionSITAB #referenciaDerSubdelegacionSITAB").attr("disabled", false);
	$("form#formDerSubdelegacionSITAB #btnConfirmaDerSubdelSITAB").attr("disabled", false);
	$('form#formDerSubdelegacionSITAB #btnValidaRegistroPatronSITAB').attr("disabled", false);
}