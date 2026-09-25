// Declaracion de constantes utilizadas
var OTRO_PERIODO_PAGO = "6";
var OTRO_COMPROBANTE_PAGO = "11";
var FP_EFECTIVO = "13";
var FP_CHEQUE   = "14";
var FP_DEPOSITO = "15";
var FP_TRANSFER = "16";
var FP_OTRO     = "17";
var MOTIVO_NOAFILIADO   = "1";
var MOTIVO_FECPOSTERIOR = "2";
var MOTIVO_SALINFERIOR  = "3";
var MOTIVO_NOAVISOBAJA  = "4";
var totalRegPatroReg = 0;
// Tipos de Denunciante
var TD_TRABAJADOR = "1";
var TD_BENEFICIARIO = "2";
var TD_REPLEGAL = "3";

//Declaracion de variables globales
var objDenuncia;
var ubicaDomicilio;
var bandera = true;

if (typeof console == "undefined" || typeof console.log == "undefined") var console = { log: function() {} }; 

$(document).ready(function() {
	desbloquear();
	if (jsConsultaDenuncia!=null){
		
		objDenuncia = jsConsultaDenuncia;
		
		txtDenuncia = null;
		jsConsultaDenuncia = null;
		
		if(objDenuncia.cveEstatus==2 && objDenuncia.tipoUsuario==1){
			$("#btnCancelar").click();
		}
		$('#md1fechaInicio').prop('disabled','disabled');
	    $('#md1fechaFin').prop('disabled','disabled');
	    $('#md2fechaInicio').prop('disabled','disabled');
		$('#md2fechaFin').prop('disabled','disabled');
		$('#md3ImporteImss').prop('disabled','disabled');
		$('#md3ImporteReal').prop('disabled','disabled');
	    $('#md4fechaInicio').prop('disabled','disabled');
	    
	    
	    cargaDatosDenuncia();
	} else {
		//console.log("creando denuncia");
		creaDenuncia();
	}
	
	$("#btnGuardar").unbind();
	$("#btnRatificar").unbind();
	$("#btnRatificar").click(function(){
		
		if(objDenuncia.cveEstatus==4){
			alert("La denuncia ya se encuentra ratificada");
			return;
		}
		objDenuncia.aclaraciones=$("#txtAclara").val();
		$.postJSON( getAppContextParaJS() + "/denuncia/ratificaDenuncia.do", objDenuncia, function(respuesta) {
			objDenuncia = respuesta;
		
			
		}).error(function(denuncia){ 
			alert("error generando ratificando denuncia");
		}).complete(function(){			
			ratificarDenuncia();
			alert ("Denuncia ratificada exitosa");
		});
		
		
		
	});
	
	
	$("#btnGuardar").click(function(e){
		//alert("pasa por 1");
		$.postJSON_Sync = function(url, data, callback) {
		    return jQuery.ajax({
		        'type': 'POST',
		        'url': url,
		        'async': false,
		        'contentType': 'application/json',
		        'data': JSON.stringify(data),
		        'dataType': 'json',
		        'success': callback
		    });
		}
		
		//alert("pasa por 2");
		objDenuncia.finalizado = false;
		llenaDenuncia();
		var resultado;
		if(isExplorer){
			//alert("pasa por 3");
			resultado =validarFormaGuardar();
		}else{			
			//alert("pasa por 4");
			quitaReglasDenunciaForm();
			resultado =validaGuardar.form();
		}
		
		//alert("pasa por 5");
		if (resultado) {
			//alert("pasa por 6");
			bloquear();
			$.postJSON_Sync( getAppContextParaJS() + "/denuncia/guardarDenuncia.do", objDenuncia, function(respuesta) {
				objDenuncia = respuesta;
				//console.log("clave denuncia="+objDenuncia.cveDenuncia);	
				
				//alert("pasa por 7");
			}).error(function(denuncia){ 
				alert("error generando denuncia");
				//alert("pasa por 8");
			}).complete(function(){
				//alert ("Denuncia guardada exitosa");
				desbloquear();
			});
		} else {
			alert ("Tiene campos con error, favor de verificar");
		}
	});
	
	//alert("pasa por 9");
	oDgPatronesDenunciados = $("#dgAgregaPatrones").dialog({
	 	autoOpen: false,
	 	modal:true,
	 	resizable:true,
	 	height: 600,
	 	width: 1000,
	 	closeOnEscape: false,
	 	buttons: {
			"Guardar": function() {
				if(validaPatronesDenunciados.form()){
					if(bandera){
						agregaPatronSec();
						$(this).dialog("close");
					}else{
						modificaPatronSec();
						$(this).dialog("close");
					}
				}
			}
	 	}
	 });
	
});

function creaDenuncia() {
	$.postJSON(getAppContextParaJS() + "/denuncia/creaDenuncia.do", null, function(data) {
		  if(data != null){	    
			  objDenuncia=data;
			  generaTablaPatronesSecundarios();			  
			  $('input[name=rdCveNssDT]:eq(1)').attr('checked', 'checked');
			  $('input[name=rdDenunciadoAntesDP]:eq(1)').attr('checked', 'checked');
			  $('input[name=rdPatronPrincipalDP]:eq(1)').attr('checked', 'checked');
			  $('input[name=rdContratoIT]:eq(1)').attr('checked', 'checked');
			  $('input[name=rdRiesgo]:eq(1)').attr('checked', 'checked');
			  
				  $("#btnRatificar").hide();
				  
				  if(objDenuncia.tipoUsuario==2){
					  $("#btnGuardar").hide();
					  $("#btnCancelar").click(function(){
						  	var input = $("<input>").attr("type", "hidden").attr("name", "folio").val(objDenuncia.folioDenuncia);
							$('#pagConsultaForma').append($(input));					
							var inputFol = $("<input>").attr("type", "hidden").attr("name", "cveFolioDenuncia").val(objDenuncia.cveDenuncia);
							$('#pagConsultaForma').append($(inputFol));		
//							$('#pagConsultaForma').attr("action", "/denunciaenlinea/login/seleccionarPeril.do");
//							$('#pagConsultaForma').submit();
							cancelar('pagConsultaForma');
					  });
				  }else{
					  
					  $("#btnCancelar").click(function(){
							var input = $("<input>").attr("type", "hidden").attr("name", "folio").val(objDenuncia.folioDenuncia);
							$('#pagConsultaForma').append($(input));					
							var inputFol = $("<input>").attr("type", "hidden").attr("name", "cveFolioDenuncia").val(objDenuncia.cveDenuncia);
						
							$('#pagConsultaForma').attr("action", getAppContextParaJS() + "/denuncia/redireccionConsulta.do");
							$('#pagConsultaForma').append($(inputFol));		
							$('#pagConsultaForma').submit();
						 
					 });
				  }

				  habilitaBotonesCargar();

					
				  $("#sltCvePeriodoPagoIT").trigger('onchange');
				  $("#sltCveComprobantePagoIT").trigger('onchange');

				  
				  	$('#cbxChequeDT').change();
					$('#cbxDepositoDT').change();
					$('#cbxTransferenciaBancariaDT').change();
					$('#cbxOtrosDT').change();
			  
		  } else {
			  alert ("No se creo la denuncia, favor de verificar");
		  }
	}).error(function(denuncia){ 
		//alert("error generando denuncia" + denuncia);
	}).complete(function(){ 
		//alert("complete cargando denuncia" + denuncia);
		if(objDenuncia.tipoUsuario==2 && !objDenuncia.funcionario){
			$("#divAclaraciones").show();
		}else{
			$("#divAclaraciones").hide();
			$("#btnRatificar").hide();
		}
		
	});
}

function llenaDenuncia() {

	objDenuncia.tipoDenunciante = $('#cmbCveTipoDenuncianteDT').val();
	
	//	Datos del trabajador
	objDenuncia.datosTrabajadorVO.observaciones = $('#txtObs').val();
	objDenuncia.datosTrabajadorVO.trabajador.nombre = $('#txtDesNombreDT').val();
	objDenuncia.datosTrabajadorVO.trabajador.apellidoPaterno = $('#txtDesPaternoDT').val();
	objDenuncia.datosTrabajadorVO.trabajador.apellidoMaterno = $('#txtDesMaternoDT').val();
	objDenuncia.datosTrabajadorVO.trabajador.tieneNss = $("#rdCveNssDT").is(':checked')
	objDenuncia.datosTrabajadorVO.trabajador.nss = $('#txtCveNssDT').val();	
	objDenuncia.datosTrabajadorVO.trabajador.curp = $('#txtCveCurpDT').val();
	objDenuncia.datosTrabajadorVO.trabajador.rfc = $('#txtCveRfcDT').val();
	objDenuncia.datosTrabajadorVO.trabajador.email = $('#txtDesEmailDT').val();
	objDenuncia.datosTrabajadorVO.trabajador.telefonoContacto = $('#txtNumTelefonoDT').val();
	objDenuncia.datosTrabajadorVO.trabajador.telefonoCelular = $('#numCelular').val();
	objDenuncia.datosTrabajadorVO.trabajador.numeroDocumento = $('#numDocumento').val();
	objDenuncia.datosTrabajadorVO.trabajador.docOficial= $('#cmbCveTipodocumentoDT').val();
	objDenuncia.datosTrabajadorVO.trabajador.sexoTrabajador= $("#rdCveSexo:checked").val();
	
	// beneficiario
	objDenuncia.datosTrabajadorVO.beneficiario.nombre = $('#txtDesNombreBDT').val();
	objDenuncia.datosTrabajadorVO.beneficiario.apellidoPaterno = $('#txtDesPaternoBDT').val();
	objDenuncia.datosTrabajadorVO.beneficiario.apellidoMaterno = $('#txtDesMaternoBDT').val();
	objDenuncia.datosTrabajadorVO.beneficiario.curp = $('#txtCveCurpBDT').val();
	objDenuncia.datosTrabajadorVO.beneficiario.telefonoContacto = $('#txtNumTelefonoBDT').val();
	objDenuncia.datosTrabajadorVO.beneficiario.telefonoCelular = $('#txtNumTelefonoMblBDT').val();
	objDenuncia.datosTrabajadorVO.beneficiario.numeroDocumento = $('#txtNumDocumentoBDT').val();
	objDenuncia.datosTrabajadorVO.beneficiario.docOficial= $('#cmbCveTipodocumentoBDT').val();
	
	// representante legal
	objDenuncia.datosTrabajadorVO.representanteLegal.nombre = $('#txtDesNombreRLDT').val();
	objDenuncia.datosTrabajadorVO.representanteLegal.apellidoPaterno = $('#txtDesPaternoRLDT').val();
	objDenuncia.datosTrabajadorVO.representanteLegal.apellidoMaterno = $('#txtDesMaternoRLDT').val();
	objDenuncia.datosTrabajadorVO.representanteLegal.curp = $('#txtCveCurpRLDT').val();
	objDenuncia.datosTrabajadorVO.representanteLegal.telefonoContacto = $('#txtNumTelefonoRLDT').val();
	objDenuncia.datosTrabajadorVO.representanteLegal.telefonoCelular = $('#txtNumTelefonoMblRLDT').val();
	objDenuncia.datosTrabajadorVO.representanteLegal.numeroDocumento = $('#txtNumDocumentoRLDT').val();
	objDenuncia.datosTrabajadorVO.representanteLegal.docOficial=$('#cmbCveTipodocumentoRLDT').val();
	objDenuncia.datosTrabajadorVO.motivosDenuncia= getMotivosDenuncia();
	
	if(objDenuncia.tipoUsuario==2){
		objDenuncia.aclaraciones=$('#txtAclara').val();
	}
	
	getDatosPatron();
	getDatosCentroTrabajo();
}

function getDatosPatron() {
	// Datos del patron
	objDenuncia.datosPatronVO.denunciaExistente = $("#rdDenunciadoAntesDP:checked").val();
	objDenuncia.datosPatronVO.razonSocial = $('#txtDesNomrazonsocialDP').val();
	// $('#domicilioTrabajo').val();
	// $('#domicilioId').val();
	objDenuncia.datosPatronVO.nombreRepresentanteLegal = $('#txtDesNomreplegalDP').val();
	objDenuncia.datosPatronVO.giroPatron = $('#cbxSelGiroActividadDP').val();
	objDenuncia.datosPatronVO.rfc = $('#txtRfcPatronDP').val();
	objDenuncia.datosPatronVO.regPat = $('#txtCveRegpatDP').val();
	objDenuncia.datosPatronVO.numTrabajadores = $('#txtNumTrabajadoresDP').val();
	objDenuncia.datosPatronVO.telefonoEmpresa = $('#txtNumTelefonoPatronDP').val();
	objDenuncia.datosPatronVO.recibeTotalSueldoDeUnPatron = $("#rdPatronPrincipalDP:checked").val();
	objDenuncia.datosPatronVO.observaciones = $('#txtObsDP').val();
	
	
	
} 

function getDatosCentroTrabajo() {
	var cvePeriodoPago;
	var cveComprobantePago;
	
	objDenuncia.datosCentroTrabajoVO.fechaInicio = $('#txtFechaInicioTrabajoIT').val();
	objDenuncia.datosCentroTrabajoVO.fechaTermino = $('#txtFechaFinTrabajoIT').val();
	objDenuncia.datosCentroTrabajoVO.actividadTrabajador = $('#txtDesLaboresDesempIT').val();
	objDenuncia.datosCentroTrabajoVO.tieneContrato = $("#rdContratoIT:checked").val();
	objDenuncia.datosCentroTrabajoVO.numeroContrato = $("#txtContratoIT").val();
	
	objDenuncia.datosCentroTrabajoVO.nombreJefeInm = $('#txtDesNomJefeInmediatoIT').val();
	objDenuncia.datosCentroTrabajoVO.horarioLabores = $('#txtDesHorariolaboresIT').val();
	objDenuncia.datosCentroTrabajoVO.salario = $('#txtImpSalarioPercibidoIT').asNumber();
	objDenuncia.datosCentroTrabajoVO.vacaciones = $('#txtImpVacacionesIT').asNumber();
	objDenuncia.datosCentroTrabajoVO.diasVacaciones = $('#txtNumDiasVacacionesIT').val();
	cvePeriodoPago = $('#sltCvePeriodoPagoIT').val();
	objDenuncia.datosCentroTrabajoVO.pagoPeriodoSal.cveFormaPago = cvePeriodoPago;
	if (cvePeriodoPago == OTRO_PERIODO_PAGO) {
		objDenuncia.datosCentroTrabajoVO.pagoPeriodoSal.descripcion = $('#txtDesEspecifiquePPIT').val();
	}	
	objDenuncia.datosCentroTrabajoVO.aguinaldoAnual = $('#txtImpAguinaldoIT').asNumber();
	objDenuncia.datosCentroTrabajoVO.diasAguinaldo = $('#txtDiasAguinaldoIT').val();
	objDenuncia.datosCentroTrabajoVO.gratificacion = $('#txtImpGratificcionIT').asNumber();
	objDenuncia.datosCentroTrabajoVO.comisiones = $('#txtDesBaseComisionOtrosIT').val();
	//objDenuncia.datosCentroTrabajoVO.baseComision = $('#txtBaseOtorgamientoIT').val();
	cveComprobantePago = $('#sltCveComprobantePagoIT').val();
	objDenuncia.datosCentroTrabajoVO.pagoComprobantePago.cveFormaPago = cveComprobantePago;
	if (cveComprobantePago == OTRO_COMPROBANTE_PAGO) {
		objDenuncia.datosCentroTrabajoVO.pagoComprobantePago.descripcion = $('#txtDesEspecifiqueCPIT').val();
	}
	objDenuncia.datosCentroTrabajoVO.formasPago = getFormasPago(); // array de formas de pago
	objDenuncia.datosCentroTrabajoVO.tuvoRiesgoTrabajo = $("#rdRiesgo:checked").val();
	if (isRadioButtonYes("rdRiesgo")) {
		objDenuncia.datosCentroTrabajoVO.fechaRiesgoTrabajo = $('#txtFecFechaRiesgoTrabIT').val();
	}
	objDenuncia.datosCentroTrabajoVO.observaciones = $('#desObservacionesIT').val();	
	
	if($('#sltSubdelegacionIT').val()!=-1){
		objDenuncia.cveSubdelegacion=$('#sltSubdelegacionIT').val();			
	}
		
}

function isRadioButtonYes(paramRadioButton) {
	var valorRadio = $('input:radio[name='+paramRadioButton+']:checked').val();
	if (valorRadio=="1") {
		return true;
	} else {
		return false;
	}
}

function getFormasPago() {
	var arrayFormasPago = [];
	getValorFormaPago("cbxEfectivoDT", arrayFormasPago);
	getValorFormaPago("cbxTransferenciaBancariaDT", arrayFormasPago);
	getValorFormaPago("cbxChequeDT", arrayFormasPago);
	getValorFormaPago("cbxDepositoDT", arrayFormasPago);
	getValorFormaPago("cbxOtrosDT", arrayFormasPago);
	return arrayFormasPago;
}

function getValorFormaPago(paramCheckFPago, paramArrayFP) {
	var descripcionFP = null;
	
	if (paramCheckFPago == "cbxOtrosDT") {
		//console.log("cbxOtrosDT.check=" + $('#'+paramCheckFPago).is(':checked'));
		descripcionFP = $('#txtDesEspecifiqueFPIT').val();
	}
	
	if ($('#'+paramCheckFPago).is(':checked')) {
		paramArrayFP.push(createFormaPago($('#'+paramCheckFPago).val(), descripcionFP));		
	}	
}

function createFormaPago(paramCveFormaPago, paramDescripcion) {
	var formaPago = new Object();
	formaPago.cveFormaPago = paramCveFormaPago;
	formaPago.descripcion = paramDescripcion;
	return formaPago;
}

function getMotivosDenuncia() {
	var arrayMotivoDen = [];
	
	if ($('#md1').is(':checked')) {
		arrayMotivoDen.push(createMotivoDenuncia($('#md1').val(), $('#md1fechaInicio').val(), $('#md1fechaFin').val()));
	}

	if ($('#md2').is(':checked')) {
		arrayMotivoDen.push(createMotivoDenuncia($('#md2').val(), $('#md2fechaInicio').val(), $('#md2fechaFin').val()));
	}

	if ($('#md3').is(':checked')) {
		arrayMotivoDen.push(createMotivoDenuncia($('#md3').val(), null, null, $('#md3ImporteImss').asNumber(), $('#md3ImporteReal').asNumber()));
	}

	if ($('#md4').is(':checked')) {
		arrayMotivoDen.push(createMotivoDenuncia($('#md4').val(), null, $('#md4fechaInicio').val()));
	}
	return arrayMotivoDen;
}

function createMotivoDenuncia(paramCveMotivo, paramFIni, paramFFin, paramSalReg, paramSalReal) {
	var motivoDenuncia = new Object();
	motivoDenuncia.cveMotivoDenuncia = paramCveMotivo;
	motivoDenuncia.fechaLabDelIngreso = paramFIni;
	motivoDenuncia.fechaLabDejoLab = paramFFin;
	motivoDenuncia.impoSalarioReg = paramSalReg;
	motivoDenuncia.impoSalarioReal =paramSalReal;

	return motivoDenuncia;
}

function cargaDatosDenuncia() {
	
	 $('#md1fechaInicio').prop('disabled','disabled');
	 $('#md1fechaFin').prop('disabled','disabled');
	 $('#md2fechaInicio').prop('disabled','disabled');
	 $('#md2fechaFin').prop('disabled','disabled');
	 $('#md3ImporteImss').prop('disabled','disabled');
	 $('#md3ImporteReal').prop('disabled','disabled');
	 $('#md4fechaInicio').prop('disabled','disabled');
	 
	cargaDatosTrabajador();
	cargaDatosPatron();
	cargaDatosCentroTrabajo();
	if(objDenuncia.tipoUsuario==2){
		
		 $("#txtAclara").removeAttr("disabled");	
		 $("#btnGuardar").removeAttr("disabled");	
		 $("#btnCancelar").removeAttr("disabled");	
		 $("#btnRatificar").removeAttr("disabled");	
		 
		 habilitaBotonesCargar();
		 
		 $("#divAclaraciones").show();		 
		 $("#btnRatificar").show();
		
		 $("#btnGuardar").hide();
		 $("#btnEnviar").hide();
		 
		 $("#btnCancelar").unbind();
		 
		 $("#btnCancelar").click(function(){
				var input = $("<input>").attr("type", "hidden").attr("name", "folio").val(objDenuncia.folioDenuncia);
				$('#pagConsultaForma').append($(input));					
				var inputFol = $("<input>").attr("type", "hidden").attr("name", "cveFolioDenuncia").val(objDenuncia.cveDenuncia);
//				$('#pagConsultaForma').append($(inputFol));		
//				$('#pagConsultaForma').submit();
				cancelar('pagConsultaForma');
		 });
		 $(":input").prop('disabled','disabled');	
		 habilitaDenuncianteSubdelegado();

	}else{
		$(":input").removeAttr("disabled");	
		$("#divAclaraciones").hide();
		$("#btnRatificar").hide();
		habilitaMotivosDenuncia(document.getElementById("md1"));
	    habilitaMotivosDenuncia(document.getElementById("md2"));		
	    habilitaMotivosDenuncia(document.getElementById("md3"));		
	    habilitaMotivosDenuncia(document.getElementById("md4"));	
		
		habilitaBotonesCargar();
		 
		 $("#btnCancelar").unbind();
		 
		 $("#btnCancelar").click(function(){
				var input = $("<input>").attr("type", "hidden").attr("name", "folio").val(objDenuncia.folioDenuncia);
				$('#pagConsultaForma').append($(input));					
				var inputFol = $("<input>").attr("type", "hidden").attr("name", "cveFolioDenuncia").val(objDenuncia.cveDenuncia);
			
				$('#pagConsultaForma').attr("action", getAppContextParaJS() + "/denuncia/redireccionConsulta.do");
				$('#pagConsultaForma').append($(inputFol));		
				$('#pagConsultaForma').submit();
			 
			 
		 });
		 habilitaDenunciante();
	}
	
}

function habilitaBotonesCargar(){
	
	ocultaCamposAdjunto('cbxTransferenciaBancariaDT','txtDesUploadFormaPagoTranBanc','uploaderFormaPagoTranBanc','btnCargar16');
	ocultaCamposAdjunto('cbxChequeDT','txtDesUploadFormaPagoCheque','uploaderFormaPagoCheque','btnCargar14');
	ocultaCamposAdjunto('cbxDepositoDT','txtDesUploadFormaPagoDepoCuenta','uploaderFormaPagoDepoCuenta','btnCargar15');
	ocultaCamposAdjunto('cbxOtrosDT','txtDesUploadFormaPagoOtro','uploaderFormaPagoOtro','btnCargar17');
	
	$('#btnCargar1').removeAttr("disabled");
	 if($('#txtDesUpload').val()==""){
		 $('#btnCargar1').hide();
	 }
	 
	 $('#btnCargar2').removeAttr("disabled");
	 if($('#txtDesUploadBeneficiario').val() == ""){ 
		 $('#btnCargar2').hide();
	 }
	 
	 $('#btnCargar3').removeAttr("disabled");
	 if($('#txtDesUploadRP').val() == ""){
		 $('#btnCargar3').hide();
	 }
	 
	 $('#btnCargar14').removeAttr("disabled");
	 if($('#txtDesUploadFormaPagoCheque').val()==""){
		 $('#btnCargar14').hide();
	 }
	 
	 $('#btnCargar15').removeAttr("disabled");
	 if($('#txtDesUploadFormaPagoDepoCuenta').val()==""){
		 $('#btnCargar15').hide();
	 }
	 
	 $('#btnCargar16').removeAttr("disabled");
	 if($('#txtDesUploadFormaPagoTranBanc').val()==""){
		 $('#btnCargar16').hide();
	 }
	 
	 $('#btnCargar17').removeAttr("disabled");
	 if($('#txtDesUploadFormaPagoOtro').val()==""){
		 $('#btnCargar17').hide();
	 }
	 
	 $('#btnCargar11').removeAttr("disabled");
	 if($('#txtDesUploadComprobantePago').val()==""){
		 $('#btnCargar11').hide();
	 }
}

function cargaDatosTrabajador() {
	generaTablaPatronesSecundarios();
	$("#txtFolioDenunciaDT").val(objDenuncia.folioDenuncia);
	$('#cmbCveTipoDenuncianteDT').val(objDenuncia.tipoDenunciante); 

	
	$('#txtObs').val(objDenuncia.datosTrabajadorVO.observaciones);
	$('#txtDesNombreDT').val(objDenuncia.datosTrabajadorVO.trabajador.nombre); 
	$('#txtDesPaternoDT').val(objDenuncia.datosTrabajadorVO.trabajador.apellidoPaterno); 
	$('#txtDesMaternoDT').val(objDenuncia.datosTrabajadorVO.trabajador.apellidoMaterno);
	 // actualiza radiobutton
	assignRadioValueFromField(objDenuncia.datosTrabajadorVO.trabajador.sexoTrabajador, "rdCveSexo");
	assignRadioValueFromField(objDenuncia.datosTrabajadorVO.trabajador.nss, "rdCveNssDT", "txtCveNssDT");
	$('#txtDomicilio').val(objDenuncia.datosTrabajadorVO.trabajador.desDomicilio);
	 $('#txtCveCurpDT').val(objDenuncia.datosTrabajadorVO.trabajador.curp); 
	 $('#txtCveRfcDT').val(objDenuncia.datosTrabajadorVO.trabajador.rfc);
	 $('#txtDesEmailDT').val(objDenuncia.datosTrabajadorVO.trabajador.email); 
	 $('#txtNumTelefonoDT').val(objDenuncia.datosTrabajadorVO.trabajador.telefonoContacto); 
	 $('#numCelular').val(objDenuncia.datosTrabajadorVO.trabajador.telefonoCelular);
	 $('#numDocumento').val(objDenuncia.datosTrabajadorVO.trabajador.numeroDocumento); 
	 $('#cmbCveTipodocumentoDT').val(objDenuncia.datosTrabajadorVO.trabajador.docOficial); 
	 $('#txtDesUpload').val(objDenuncia.datosTrabajadorVO.trabajador.nombreDocumento); 
	 // beneficiario
	 $('#txtDesNombreBDT').val(objDenuncia.datosTrabajadorVO.beneficiario.nombre);
	 $('#txtDesPaternoBDT').val(objDenuncia.datosTrabajadorVO.beneficiario.apellidoPaterno); 
	 $('#txtDesMaternoBDT').val(objDenuncia.datosTrabajadorVO.beneficiario.apellidoMaterno);
	 $('#txtCveCurpBDT').val(objDenuncia.datosTrabajadorVO.beneficiario.curp);
	 $('#direccionBeneficiario').val(objDenuncia.datosTrabajadorVO.beneficiario.desDomicilio);
	 $('#txtNumTelefonoBDT').val(objDenuncia.datosTrabajadorVO.beneficiario.telefonoContacto); 
	 $('#txtNumTelefonoMblBDT').val(objDenuncia.datosTrabajadorVO.beneficiario.telefonoCelular); 
	 $('#txtNumDocumentoBDT').val(objDenuncia.datosTrabajadorVO.beneficiario.numeroDocumento);
	 $('#cmbCveTipodocumentoBDT').val(objDenuncia.datosTrabajadorVO.beneficiario.docOficial); 
	 $('#txtDesUploadBeneficiario').val(objDenuncia.datosTrabajadorVO.beneficiario.nombreDocumento); 
	 // representante legal
	 $('#txtDesNombreRLDT').val(objDenuncia.datosTrabajadorVO.representanteLegal.nombre);
	 $('#txtDesPaternoRLDT').val(objDenuncia.datosTrabajadorVO.representanteLegal.apellidoPaterno); 
	 $('#txtDesMaternoRLDT').val(objDenuncia.datosTrabajadorVO.representanteLegal.apellidoMaterno);
	 $('#txtCveCurpRLDT').val(objDenuncia.datosTrabajadorVO.representanteLegal.curp);
	 $('#direccionRepLegalDT').val(objDenuncia.datosTrabajadorVO.representanteLegal.desDomicilio);
	 $('#txtNumTelefonoRLDT').val(objDenuncia.datosTrabajadorVO.representanteLegal.telefonoContacto); 
	 $('#txtNumTelefonoMblRLDT').val(objDenuncia.datosTrabajadorVO.representanteLegal.telefonoCelular); 
	 $('#txtNumDocumentoRLDT').val(objDenuncia.datosTrabajadorVO.representanteLegal.numeroDocumento);
	 $('#cmbCveTipodocumentoRLDT').val(objDenuncia.datosTrabajadorVO.representanteLegal.docOficial); 
	 $('#txtDesUploadRP').val(objDenuncia.datosTrabajadorVO.representanteLegal.nombreDocumento); 
	 cargaMotivosDenuncia();
	 habilitaDenunciante();
	 
	 habilitaMotivosDenuncia(document.getElementById("md1"));
	 habilitaMotivosDenuncia(document.getElementById("md2"));
	 habilitaMotivosDenuncia(document.getElementById("md3"));
	 habilitaMotivosDenuncia(document.getElementById("md4"));
}

function cargaDatosPatron() {
	// Datos del patron
	assignRadioValue(objDenuncia.datosPatronVO.denunciaExistente, "rdDenunciadoAntesDP");
	$('#txtDesNomrazonsocialDP').val(objDenuncia.datosPatronVO.razonSocial);
	$('#txtDomicilioTrabajoDP').val(objDenuncia.datosPatronVO.desDomicilio);
	$('#txtDesNomreplegalDP').val(objDenuncia.datosPatronVO.nombreRepresentanteLegal);
	$('#txtRfcPatronDP').val(objDenuncia.datosPatronVO.rfc);
	$('#txtCveRegpatDP').val(objDenuncia.datosPatronVO.regPat);
	$('#txtNumTrabajadoresDP').val(objDenuncia.datosPatronVO.numTrabajadores);
	$('#txtNumTelefonoPatronDP').val(objDenuncia.datosPatronVO.telefonoEmpresa);
	assignRadioValue(objDenuncia.datosPatronVO.recibeTotalSueldoDeUnPatron, "rdPatronPrincipalDP")
	$('#txtObsDP').val(objDenuncia.datosPatronVO.observaciones);
	$('#txtDomFiscalPtrIdDP').val(objDenuncia.datosPatronVO.desDomicilio);
	
	totalRegPatroReg=objDenuncia.datosPatronVO.patronesSecundarios.length;
} 

function cargaDatosCentroTrabajo() {
	var cvePeriodoPago;
	var cveComprobantePago;
	
	$('#txtDomicilioTrabajoDP').val(objDenuncia.datosCentroTrabajoVO.desDomicilio);
	$('#txtFechaInicioTrabajoIT').val(objDenuncia.datosCentroTrabajoVO.fechaInicio);
	$('#txtFechaFinTrabajoIT').val(objDenuncia.datosCentroTrabajoVO.fechaTermino);	
	$('#txtDesLaboresDesempIT').val(objDenuncia.datosCentroTrabajoVO.actividadTrabajador);
	assignRadioValueFromField(objDenuncia.datosCentroTrabajoVO.numeroContrato, "rdContratoIT", "txtContratoIT");
	$('#txtDesNomJefeInmediatoIT').val(objDenuncia.datosCentroTrabajoVO.nombreJefeInm);
	$('#txtDesHorariolaboresIT').val(objDenuncia.datosCentroTrabajoVO.horarioLabores);
	$('#txtImpSalarioPercibidoIT').val(objDenuncia.datosCentroTrabajoVO.salario);
	$('#txtImpVacacionesIT').val(objDenuncia.datosCentroTrabajoVO.vacaciones);
	$('#txtNumDiasVacacionesIT').val(objDenuncia.datosCentroTrabajoVO.diasVacaciones);
	//console.log("periodo pago="+objDenuncia.datosCentroTrabajoVO.pagoPeriodoSal.cveFormaPago);
	$('#sltCvePeriodoPagoIT').val(objDenuncia.datosCentroTrabajoVO.pagoPeriodoSal.cveFormaPago);
	$('#txtDesEspecifiquePPIT').val(objDenuncia.datosCentroTrabajoVO.pagoPeriodoSal.descripcion);
	$('#txtImpAguinaldoIT').val(objDenuncia.datosCentroTrabajoVO.aguinaldoAnual);
	$('#txtDiasAguinaldoIT').val(objDenuncia.datosCentroTrabajoVO.diasAguinaldo);
	$('#txtImpGratificcionIT').val(objDenuncia.datosCentroTrabajoVO.gratificacion);
	$('#txtDesBaseComisionOtrosIT').val(objDenuncia.datosCentroTrabajoVO.comisiones);
	//$('#txtBaseOtorgamientoIT').val(objDenuncia.datosCentroTrabajoVO.baseComision);	
	if(objDenuncia.datosCentroTrabajoVO.pagoComprobantePago.cveFormaPago==0){
		$('#sltCveComprobantePagoIT').val('-1');
	}else{
		$('#sltCveComprobantePagoIT').val(objDenuncia.datosCentroTrabajoVO.pagoComprobantePago.cveFormaPago);
		 $('select#sltCveComprobantePagoIT').change();
	}
	
	$('#txtDesUploadComprobantePago').val(objDenuncia.datosCentroTrabajoVO.pagoComprobantePago.nombreArchivo);
	$('#txtDesEspecifiqueCPIT').val(objDenuncia.datosCentroTrabajoVO.pagoComprobantePago.descripcion);
	//console.log("tuvo riesgo="+objDenuncia.datosCentroTrabajoVO.tuvoRiesgoTrabajo);
	assignRadioValueFromField(objDenuncia.datosCentroTrabajoVO.fechaRiesgoTrabajo, "rdRiesgo", "txtFecFechaRiesgoTrabIT");	
	//$('#txtFecFechaRiesgoTrabIT').val(objDenuncia.datosCentroTrabajoVO.fechaRiesgoTrabajo);
	$('#desObservacionesIT').val(objDenuncia.datosCentroTrabajoVO.observaciones);	
	
	$('#txtImpSalarioPercibidoIT').formatCurrency();
	$('#txtImpVacacionesIT').formatCurrency();
	$('#txtImpAguinaldoIT').formatCurrency();
	$('#txtImpGratificcionIT').formatCurrency();
	
	$('#md3ImporteImss').formatCurrency();
	$('#md3ImporteReal').formatCurrency();

	$("#sltCvePeriodoPagoIT").trigger('onchange');
	$("#sltCveComprobantePagoIT").trigger('onchange');
	cargaFormasPago();
}

function assignRadioValue(paramBan, paramName) {
	var objRadioBtn;
	
	if (paramBan=="1" || paramBan) {
		$('input:radio[name='+ paramName + ']')[0].checked = true;
		objRadioBtn = $('input:radio[name='+ paramName + ']')[0];
	} else {
		$('input:radio[name='+ paramName + ']')[1].checked = true;
		objRadioBtn = $('input:radio[name='+ paramName + ']')[1];
	}
	return objRadioBtn;
}

function assignRadioValueFromField(paramField, paramNameRadio, paramNameField) {
	var hasValField = hasValue(paramField);
	var radioSelection;
	
	radioSelection = assignRadioValue(hasValField, paramNameRadio);
	radioSelection.click();
	//$('#' + paramNameRadio).trigger("click");
	if (hasValField) {		
		$('#'+paramNameField).val(paramField);
	}
}

function hasValue(paramVar) {
	if (paramVar==null || paramVar ==undefined || paramVar==""){
		return false;
	} else {
		return true;
	}
}

function cargaMotivosDenuncia() {
	var motivos = objDenuncia.datosTrabajadorVO.motivosDenuncia
	//console.log("cargaMotivosDenuncia, len="+motivos.length);
	//console.log(motivos);
	 habilitaMotivosDenuncia(document.getElementById("md1"));
	 habilitaMotivosDenuncia(document.getElementById("md2"));
	 habilitaMotivosDenuncia(document.getElementById("md3"));
	 habilitaMotivosDenuncia(document.getElementById("md4"));
    
	if(motivos != null){	       						
		 for(var i=0; i<motivos.length; i++){
			 var regMotivo = motivos[i];
			 var cveMotivo = regMotivo.cveMotivoDenuncia;   
			 //console.log("car.cveMotivo="+cveMotivo);
			 if(cveMotivo == MOTIVO_NOAFILIADO){
				 $("#md1").prop('checked', true);
				 habilitaMotivosDenuncia(document.getElementById("md1"));
				 $('#md1fechaInicio').val(regMotivo.fechaLabDelIngreso);
				 $('#md1fechaFin').val(regMotivo.fechaLabDejoLab);    								 
			 }    							 
			 if(cveMotivo == MOTIVO_FECPOSTERIOR){
				 $("#md2").prop('checked', true);
				 habilitaMotivosDenuncia(document.getElementById("md2"));
				 $('#md2fechaInicio').val(regMotivo.fechaLabDelIngreso);
				 $('#md2fechaFin').val(regMotivo.fechaLabDejoLab);
			 }    							 
			 if(cveMotivo == MOTIVO_SALINFERIOR){
				 $("#md3").prop('checked', true);
				 habilitaMotivosDenuncia(document.getElementById("md3"));
				 $('#md3ImporteImss').val(regMotivo.impoSalarioReg);
				 $('#md3ImporteReal').val(regMotivo.impoSalarioReal);
			 }			 
			 if(cveMotivo == MOTIVO_NOAVISOBAJA){    
				 $("#md4").prop('checked', true);
				 habilitaMotivosDenuncia(document.getElementById("md4"));
				 $('#md4fechaInicio').val(regMotivo.fechaLabDejoLab);    								 
			 }	
		}
		
	}
	
	
}

function cargaFormasPago() {
	var arrayFormasPago = objDenuncia.datosCentroTrabajoVO.formasPago
	if (arrayFormasPago != null) {
		for (i = 0; i<arrayFormasPago.length; i++) {
			var formaPago = arrayFormasPago[i];
			//console.log("fpago Obj="+arrayFormasPago[i]);
			//console.log("fpago="+formaPago.cveFormaPago);
			if (formaPago.cveFormaPago == FP_EFECTIVO) {
				$('#cbxEfectivoDT').attr('checked', true);
				$("#txtDesUploadFormaPagoEfec").val(formaPago.nombreArchivo)
			}
			if (formaPago.cveFormaPago == FP_CHEQUE) {
				$('#cbxChequeDT').attr('checked', true);
				$("#txtDesUploadFormaPagoCheque").val(formaPago.nombreArchivo)
			}
			if (formaPago.cveFormaPago == FP_DEPOSITO) {
				$('#cbxDepositoDT').attr('checked', true);
				$("#txtDesUploadFormaPagoDepoCuenta").val(formaPago.nombreArchivo)
			}
			if (formaPago.cveFormaPago == FP_TRANSFER) {
				$('#cbxTransferenciaBancariaDT').attr('checked', true);
				$("#txtDesUploadFormaPagoTranBanc").val(formaPago.nombreArchivo)
			}
			if (formaPago.cveFormaPago == FP_OTRO) {
				$('#cbxOtrosDT').attr('checked', true);
				$('#txtDesEspecifiqueFPIT').val(formaPago.descripcion).removeAttr('disabled');
				$("#txtDesUploadFormaPagoOtro").val(formaPago.nombreArchivo)
				
			}
		}
		
		$('#cbxChequeDT').change();
		$('#cbxDepositoDT').change();
		$('#cbxTransferenciaBancariaDT').change();
		$('#cbxOtrosDT').change();
	}
}

function generaTablaPatronesSecundarios(){
	  //objDenuncia.datosPatronVO.patronesSecundarios
	  dataTablePatrones= $("#dtPatronesDenunciados").dataTable({
			"aaData": objDenuncia.datosPatronVO.patronesSecundarios,
			"bAutoWidth" : true,
			bFilter : false,
			bJQueryUI : true,
			"bDestroy": true,
			bSort: false,
			"fnInfoCallback": function( oSettings, iStart, iEnd, iMax, iTotal, sPre ) {			   

			  },
			"aoColumns" : [{				
				"sTitle" : "",
				"mDataProp" : "idRow",
				"sClass": "dtCenterClassColumn",
				"sWidth":"80px",
				"fnRender": function ( o, val ) {					
					var val='<input type="radio" name="rdPatronSecundario" value="'+o.aData['idRow']+'">';
					return val;
			        }
			},{
					
				"sTitle" : "Razon Social",
				"mDataProp" : "nombreRazonSocial",
				"sClass": "dtCenterClassColumn",
				"sWidth":"120px"
			}]
	    } );  
}

function openDgAgregarPatronesSecundarios(){
	bandera = true;
	$("#txtRfcPatronCmpDP").val("");
	$("#txtDesNomrazonsocialCmpDP").val("");
	$("#txtDomFiscalPtrIdCmpDP").val("");
	$("#cveDom").val("");
	oDgPatronesDenunciados.dialog('open');
}

function openDgModificarPatronesSecundarios(){
	bandera = false;
	var seleccionado= $("input[name=rdPatronSecundario]:checked").val();
	if(seleccionado == null){
		alert("Selecciona un patron");
	}else{
		var listaPatronesSec = objDenuncia.datosPatronVO.patronesSecundarios;
		for(var i = 0; i<listaPatronesSec.length; i++){
			if(listaPatronesSec[i].idRow == seleccionado){
				$("#txtRfcPatronCmpDP").val(listaPatronesSec[i].rfc);
				$("#txtDesNomrazonsocialCmpDP").val(listaPatronesSec[i].nombreRazonSocial);
				$("#txtDomFiscalPtrIdCmpDP").val(listaPatronesSec[i].descDomicilio);
			}
		}
		oDgPatronesDenunciados.dialog('open');
	}
}


function agregaPatronSec(){
	var ob={
			"cveDenuncia":"-1",
			"rfc":$("#txtRfcPatronCmpDP").val(),
			"nombreRazonSocial":$("#txtDesNomrazonsocialCmpDP").val().toUpperCase(),
			"cveDomicilio":$("#cveDom").val(),
			"idRow":totalRegPatroReg++,
			"descDomicilio":$("#txtDomFiscalPtrIdCmpDP").val()
			};						
			objDenuncia.datosPatronVO.patronesSecundarios.push(ob);
			dataTablePatrones.fnAddData(ob);
			$("#txtRfcPatronCmpDP").val("");
			$("#txtDesNomrazonsocialCmpDP").val("");
			$("#txtDomFiscalPtrIdCmpDP").val("");
			$("#cveDom").val("");
			alert("Los datos fueron agregados exitosamente");
			
}

function modificaPatronSec(){
	bandera = false;
		var idRow = $("input[name=rdPatronSecundario]:checked").val();
		if($("#cveDom").val()!=''){
			objDenuncia.datosPatronVO.patronesSecundarios[idRow].cveDomicilio = $("#cveDom").val();
		}
		objDenuncia.datosPatronVO.patronesSecundarios[idRow].idRow = idRow; 
		objDenuncia.datosPatronVO.patronesSecundarios[idRow].nombreRazonSocial = $("#txtDesNomrazonsocialCmpDP").val().toUpperCase();
		objDenuncia.datosPatronVO.patronesSecundarios[idRow].rfc = $("#txtRfcPatronCmpDP").val();
//		objDenuncia.datosPatronVO.patronesSecundarios[idRow].cveDenuncia = '-1';
		objDenuncia.datosPatronVO.patronesSecundarios[idRow].descDomicilio = $("#txtDomFiscalPtrIdCmpDP").val(); 
		
		dataTablePatrones.fnUpdate($("#txtDesNomrazonsocialCmpDP").val().toUpperCase(),idRow,1);
		$("#txtRfcPatronCmpDP").val("");
		$("#txtDesNomrazonsocialCmpDP").val("");
		$("#txtDomFiscalPtrIdCmpDP").val("");
		$("#cveDom").val("");
		alert("Los datos fueron modificados exitosamente");
}


function eliminaPatronSecundario(){	
	if(confirm("\u00BFEsta seguro que desea eliminar el patron secundario?")){
		var idRow=$('input:radio[name=rdPatronSecundario]:checked').val();
		for(var t=0;t<objDenuncia.datosPatronVO.patronesSecundarios.length;t++){
			if(objDenuncia.datosPatronVO.patronesSecundarios[t].idRow==idRow){
				dataTablePatrones.fnDeleteRow(t);
				objDenuncia.datosPatronVO.patronesSecundarios.splice(t,1);
				alert("Los datos fueron eliminados exitosamente");
			}
		}
	}

}

function cargaDatosPrueba() {
	var fecHoy= new Date();
	var fechaCaptura;	
	var hora=  fecHoy.getHours() + ":" + fecHoy.getMinutes()
	fechaCaptura= $.datepicker.formatDate('M dd yy',fecHoy) + " " + hora;
	$('#cmbCveTipoDenuncianteDT').val("2");
	$('#cmbCveTipoDenuncianteDT').trigger("change");
	$('#txtDesNombreDT').val("RAFAEL");
	$('#txtDesPaternoDT').val("TAMARIZ");
	$('#txtDesMaternoDT').val("ALCANTARA");
	$('input:radio[name=rdCveNssDT]')[1].checked = true;
	$('input:radio[name=rdCveNssDT]').trigger("click");
	$('#txtCveNssDT').val("10000000131");	
	$('#txtCveCurpDT').val("TAAR000809HDFMLF05");
	$('#txtCveRfcDT').val("TALR810505AB1");
	$('#txtDesEmailDT').val("TAAR000809HDFMLFA5@mail.com");
	$('#txtNumTelefonoDT').val("0151248833");
	$('#numCelular').val("5511111111");
	$('#numDocumento').val("5511112222");
	$('#txtObs').val("observaciones " + fechaCaptura);

	$('#txtDesNombreBDT').val("MARICARMEN");
	$('#txtDesPaternoBDT').val("VILA");
	$('#txtDesMaternoBDT').val("RAMIREZ");
	$('#txtCveCurpBDT').val("VIAM000610MDFLMR01");
	$('#txtNumTelefonoBDT').val("0166237799");
	$('#txtNumTelefonoMblBDT').val("2222221111");
	$('#txtNumDocumentoBDT').val("5566586858899");

	$('#txtDesNombreRLDT').val("JOSE EDUARDO");
	$('#txtDesPaternoRLDT').val("LOPEZ");
	$('#txtDesMaternoRLDT').val("ROCHA");
	$('#txtCveCurpRLDT').val("LORE000711HDFPCDA9");
	$('#txtNumTelefonoRLDT').val("5544216677");
	$('#txtNumTelefonoMblRLDT').val("5522223333");
	$('#txtNumDocumentoRLDT').val("5577456712300");

	$('#md1').attr('checked', true);
	$('#md1fechaInicio').val("2012-01-01");
	$('#md1fechaFin').val("2012-12-31");
	$('#md2').attr('checked', true);
	$('#md2fechaInicio').val("2012-02-02");
	$('#md2fechaFin').val("2012-03-03");
	$('#md3').attr('checked', true);
	$('#md3ImporteImss').val("3500");
	$('#md3ImporteReal').val("4500");
	$('#md4').attr('checked', true);
	$('#md4fechaInicio').val("2012-05-05");
			
	$('#txtDesNomrazonsocialDP').val("INGENIERIA Y DESARROLLO INMOBILIARIO DE MEXICO, S.A. DE C.V.");
	$('#txtDesNomreplegalDP').val("URIEL HERNANDEZ ABARCA");
	$('input:radio[name=rdDenunciadoAntesDP]')[1].checked = true;
	$('#cbxSelGiroActividadDP').val("268");
	$('#txtRfcPatronDP').val("IDI9802234X8");
	$('#txtCveRegpatDP').val("E2924858103");
	$('#txtNumTrabajadoresDP').val("150");
	$('#txtNumTelefonoPatronDP').val("5555666677");
	$('input:radio[name=rdPatronPrincipalDP]')[0].checked = true;
	$('#txtObsDP').val("Observaciones patron " + fechaCaptura);

	$('#txtFechaInicioTrabajoIT').val("2011-01-01");
	$('#txtFechaFinTrabajoIT').val("2011-12-31");
	$('#txtDesLaboresDesempIT').val("Analisis de riesgo");
	$('input:radio[name=rdContratoIT]')[1].checked = true;
	$('#txtDesNomJefeInmediatoIT').val("MARIO SANTIAGO REQUENA ARANDA");
	$('#txtDesHorariolaboresIT').val("Lunes a viernes 9:00 a 18:00");
	$('#txtImpSalarioPercibidoIT').val("3500");
	$('#txtImpVacacionesIT').val("120");
	$('#sltCvePeriodoPagoIT').val("2");
	$('#txtDesEspecifiquePPIT').val("bimensual");
	$('#txtNumDiasVacacionesIT').val("7");
	$('#txtImpAguinaldoIT').val("300");
	$('#txtDiasAguinaldoIT').val("7");
	$('#txtImpGratificcionIT').val("250");
	$('#txtDesBaseComisionOtrosIT').val("bono de productividad");
	//$('#txtBaseOtorgamientoIT').val("porcentaje de productividad");
	$('#sltCveComprobantePagoIT').val("7");
	$('#txtDesEspecifiqueCPIT').val("otro comprobante");
	$('#cbxEfectivoDT').attr('checked', true);
	$('#cbxTransferenciaBancariaDT').attr('checked', true);
	$('#cbxChequeDT').attr('checked', true);
	$('#cbxDepositoDT').attr('checked', true);
	$('#cbxOtrosDT').attr('checked', true);
	$('#txtDesEspecifiqueFPIT').val("Otra forma de pago");
	$('input:radio[name=rdRiesgo]')[1].checked = true;
	$('#txtFecFechaRiesgoTrabIT').val("2012-09-09");
	$('#desObservacionesIT').val("observaciones referentes al centro de trabajo " + fechaCaptura);
	}

function showContrato(paramRadioValue) {
	if (paramRadioValue==1) {		
		$("#datosNumContrato").show();
	} else {
		$("#datosNumContrato").hide();
		$("#txtContratoIT").val(null);
	}
}

function habilitaMotivosDenuncia(paramRadioMD){
	  var arrayCampos = null;

	  if(paramRadioMD.value == '1'){
		  arrayCampos = ['#md1fechaInicio', '#md1fechaFin'];		  
		  //$("#fechaval").show();
		  etiqueta=document.getElementById("fechaval");
		  //document.getElementById("fechaval").innerHTML="<font color='#FF0000'>*</font>";
	  }
	  if(paramRadioMD.value == '2'){
		  arrayCampos = ['#md2fechaInicio', '#md2fechaFin'];
		  etiqueta=document.getElementById("fechaval2");
		  //document.getElementById("fechaval2").innerHTML="<font color='#FF0000'>*</font>";
	  }
	  if(paramRadioMD.value == '3'){
		  arrayCampos = ['#md3ImporteImss', '#md3ImporteReal'];
		  etiqueta=document.getElementById("salarios");
		  //document.getElementById("salarios").innerHTML="<font color='#FF0000'>*</font>";
	  }
	  if(paramRadioMD.value == '4'){
		  arrayCampos = ['#md4fechaInicio'];
		  etiqueta=document.getElementById("fechaval3");
	  }
	  habilitaCampo(arrayCampos, paramRadioMD.checked, etiqueta);
}

function habilitaCampo(paramFields, paramHabilitar, etiquetas) {	
	for(i = 0; i < paramFields.length; i++) {
		if (paramHabilitar) {
			$(paramFields[i]).prop('disabled','');
			etiquetas.innerHTML="<font color='#FF0000'>*</font>";
		} else {
			$(paramFields[i]).prop('disabled','disabled');
			$(paramFields[i]).val("");
			etiquetas.innerHTML="";
			//$("#fechaval").hide();
		}
	} 
}

function enviarDenuncia() {
	//console.log("enviando denuncia");
	var resultado;
	objDenuncia.finalizado = true;
	llenaDenuncia();
	//console.log("1");
	if(isExplorer){
		resultado=validarFormaEnvia();
	}else{
		//console.log("2");
		agregaReglasDenunciaForm();
		//console.log("3");
		resultado =validaGuardar.form();
		//console.log("4");
	}

	
	
	if (resultado) {
		bloquear();
		$.postJSON( getAppContextParaJS() + "/denuncia/guardarDenuncia.do", objDenuncia, function(respuesta) {
			objDenuncia = respuesta;
			//console.log("clave denuncia Guardada="+objDenuncia.cveDenuncia);	
			
		}).error(function(denuncia){ 
			alert("Error enviando denuncia");
		}).complete(function(){
			alert ("Denuncia enviada exitosamente");
			desbloquear();
			//muestraPDF();
			//$('#pagConsultaForma').submit();
				
			
			alert("Denuncia Finalizada");
			$("#denunciaForm").attr("action",getAppContextParaJS() + "/denuncia/muestraPDF.do" );		
			$("#denunciaForm").submit();
			muestraPDF();
			
		});
	} else {
		alert ("Tiene campos con error, favor de verificar");
	}	
}

function ratificarDenuncia() {
			$("#denunciaForm").attr("action",getAppContextParaJS() + "/denuncia/muestraRatPDF.do" );		
			$("#denunciaForm").submit();
			muestraRatPDF();
			
		
}

function muestraPDF(){
	
	  $("#denunciaForm").attr("action",getAppContextParaJS() + "/denuncia/muestraPDF.do" );		
	  $("#denunciaForm").submit();
}


function muestraRatPDF(){
	
	  $("#denunciaForm").attr("action",getAppContextParaJS() + "/denuncia/muestraRatPDF.do" );		
	  $("#denunciaForm").submit();
}

function habilitaDenunciante(){
	if($('#cmbCveTipoDenuncianteDT').val() == '2' && objDenuncia.cveEstatus!=4){
		//beneficiario
		$('[name$="BDT"]').removeAttr("disabled");
		$('#ubicar1').removeAttr("disabled");
		$('#uploaderBeneficiario').removeAttr("disabled");		
		$('[name$="RLDT"]').prop('disabled','disabled').val("");
		$('#ubicar2').prop('disabled','disabled');
		$('#uploaderRepLegal').prop('disabled','disabled');	
		
		$("#divDatosRepLegal").hide();
		$("#divDatosBeneficiario").show();
	}
	if($('#cmbCveTipoDenuncianteDT').val() == '3' && objDenuncia.cveEstatus!=4){	
		//Representante legal
		$('[name$="RLDT"]').removeAttr("disabled");
		$('#ubicar2').removeAttr("disabled");
		$('#uploaderRepLegal').removeAttr("disabled");
		$('[name$="BDT"]').prop('disabled','disabled').val("");
		$('#ubicar1').prop('disabled','disabled');
		$('#uploaderBeneficiario').prop('disabled','disabled');
		
		$("#divDatosBeneficiario").hide();
		$("#divDatosRepLegal").show();
	}
	if($('#cmbCveTipoDenuncianteDT').val() == '1' ){		
		//trabajador
		$('[name$="RLDT"]').prop('disabled','disabled').val("");
		$("label.error[for$='RLDT']").hide();
		$('[name$="BDT"]').prop('disabled','disabled').val("");
		$("label.error[for$='BDT']").hide();
		$('#ubicar1').prop('disabled','disabled');
		$('#ubicar2').prop('disabled','disabled');
		$('#uploaderBeneficiario').prop('disabled','disabled');
		$('#uploaderRepLegal').prop('disabled','disabled');
		
		$("#divDatosBeneficiario").hide();
		$("#divDatosRepLegal").hide();
		
	}
}


function habilitaDenuncianteSubdelegado(){
	if($('#cmbCveTipoDenuncianteDT').val() == '2' && objDenuncia.cveEstatus!=4){
		//beneficiario
		$("#divDatosRepLegal").hide();
		$("#divDatosBeneficiario").show();
	}
	if($('#cmbCveTipoDenuncianteDT').val() == '3' && objDenuncia.cveEstatus!=4){	
		//Representante legal
		$("#divDatosBeneficiario").hide();
		$("#divDatosRepLegal").show();
	}
	if($('#cmbCveTipoDenuncianteDT').val() == '1' ){		
		//trabajador
		$("#divDatosBeneficiario").hide();
		$("#divDatosRepLegal").hide();		
	}
	
	$('#txtAclara').removeAttr("disabled");
	$('#btnRatificar').removeAttr("disabled");
	$('#btnCancelar').removeAttr("disabled");
	
	$("#btnCargar1").removeAttr("disabled"); 
	$("#btnCargar2").removeAttr("disabled");
	$("#btnCargar3").removeAttr("disabled"); 
	$("#btnCargar11").removeAttr("disabled");  
	$("#btnCargar16").removeAttr("disabled");  
	$("#btnCargar14").removeAttr("disabled");
	$("#btnCargar15").removeAttr("disabled");  
	$("#btnCargar17").removeAttr("disabled");  
}
function toUpperCase(field){
    field.value = field.value.toUpperCase();
}

function toLowerCase(field){
    field.value = field.value.toLowerCase();
}


function opcionOtroCP(field){
	
	var id = field.id;	
	if(id == "sltCvePeriodoPago"){
		var otro = $('#' + id + ' option:selected').html();
		var valor = field.value;
		if(otro != "6"){
			$('.txtDesEspecifiquePPIT').hide()
		} else{
			$('.txtDesEspecifiquePPIT').show()
		}
	
	} else if(id == "sltCveComprobantePagoIT"){
		
		var otro = $('#' + id + ' option:selected').html();
		var valor = field.value;
		if(otro != "OTROS"){
			$('#txtDesEspecifiqueCPIT').hide();
			$('#txtDesEspecifiqueCPITLab').hide();
		} else{
			$('#txtDesEspecifiqueCPIT').show();
			$('#txtDesEspecifiqueCPITLab').show();
		}
	} else if(id == "sltCvePeriodoPagoIT") {
		var otro = $('#' + id + ' option:selected').html();
		var valor = field.value;
		if(otro != "OTRO"){
			$('#txtDesEspecifiquePPIT').hide();
			$('#txtDesEspecifiquePPITLabel').hide();
		} else{
			$('#txtDesEspecifiquePPIT').show();
			$('#txtDesEspecifiquePPITLabel').show();
		}
	}

}


function bloquear(){
	$.blockUI({ message:  '<h1>Procesando...</h1>', css: {             
		border: 'none',             
		padding: '15px',                          
		opacity: .5             
	} });
}


function desbloquear(){
	$.unblockUI();
}



