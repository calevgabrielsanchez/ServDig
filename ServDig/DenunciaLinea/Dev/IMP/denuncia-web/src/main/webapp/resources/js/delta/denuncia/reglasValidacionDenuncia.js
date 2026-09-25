/*
 * JS que contiene las funciones y reglas de validacion
 * usadas para la captura de campos
 * de los tabs de denuncia en linea
 * 
 */
var validaGuardar;
var validaPatronesDenunciados;
var isExplorer=false;

var validaCURP = {
	maxlength : 18,
	minlength : 18,
	alphanumeric : true,
	regexp : /^[a-zA-Z]{1}[aeiouxAEIOUX]{1}[a-zA-Z]{2}\d{2}([0][1-9]|[1][0-2])([0][1-9]|[1][0-9]|[2][0-9]|[3][0-1])[hmHM]{1}(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE|  as|bc|bs|cc|cs|ch|cl|cm|df|dg|gt|gr|hg|jc|mc|mn|ms|nt|nl|oc|pl|qt|qr|sp|sp|sr|tc|ts|tl|vz|yn|zs|ne)[^aeiouAEIOU]{3}(\d|[a-zA-Z])\d$/
};

var validaTelefono = {
	digits : true,
	maxlength : 12,
	minlength : 12
};

var validaNombre = {
	maxlength : 50,
	alphanumeric : true
};

var validaReqBeneficiario = {
		required : function(element) {
			return $("#cmbCveTipoDenuncianteDT").val() == TD_BENEFICIARIO;
		}		
}

var validaReqRepLegal = {
		required : function(element) {
			return $("#cmbCveTipoDenuncianteDT").val() == TD_REPLEGAL;
		}		
}

var validaTipoDenu={
	regexp : /^([1]|[2]|[3])$/
};




$(document)
		.ready(
				function() {
					jQuery.validator.addMethod('regexp', function(value,
							element, param) {
						return this.optional(element) || value.match(param);
					}, 'El valor no coincide con la estructura requerida.');
					$.validator.addMethod("motivoRechazoReqerido", function(value, elem, param) {
					    if($(":checkbox[id^='md']:checked").length > 0){
					       return true;
					   }else {
					       return false;
					   }
					},"Debe seleccionar al menos un motivo de denuncia");
					
					$.validator.addMethod("comboRequerido", function(value, elem, param) {
						if (value!=-1) {
							return true;
						} else {
							return false;
						}
//						console.log("value="+value);
//						console.log("elem="+elem);
//						console.log("elem.val="+elem.value);
//						console.log("param="+param);
					       return false;
					},"Este campo es obligatorio");
					

					var validaCaptura = $("#formRegistro").validate({
						rules : {
							j_captcha_response : {
								required : true,
								minlength : 3,
								alphanumeric : true
							},
							email : {
								required : true
							},
							confirmaEmail : {
								required : true,
								equalTo : "#email"
							},
							password : {
								required : true,
								alphanumeric : true,
								maxlength : 8,
								minlength : 8
							},
							confirm_pass : {
								required : true,
								alphanumeric : true,
								maxlength : 8,
								minlength : 8
							},
							pregunta : {
								required : true,
								alphanumeric : true
							},
							respuesta : {
								required : true,
								alphanumeric : true
							}
						}

					});	
					
					validaGuardar = $("#denunciaForm").validate(
									{
										rules : {
											txtFechaFinTrabajoIT: {
												maxlength : 10
												
											},
											txtFechaInicioTrabajoIT: {
												maxlength : 10
												
											},
											txtDesNombreDT : {
												maxlength : 50,
												alphanumeric : true
											},
											cmbCveTipoDenuncianteDT:validaTipoDenu,
											txtDesPaternoDT : {
												maxlength : 50,
												alphanumeric : true
											},
											txtDesMaternoDT : {
												maxlength : 50,
												alphanumeric : true
											},
											txtCveNssDT : {
												digits : true,
//												required : function(element) {
//													return $("[name='rdCveNssDT']:checked").val() == '1';
//												}												
												maxlength : 11
											},
											txtCveCurpDT : validaCURP,
											txtCveRfcDT : {
												alphanumeric : true,
												maxlength : 13,
												minlength : 13,
												regexp : /^[a-zA-Z]{4}(\d{6})((\D|\d){3})?$/											
											},
											txtNumTelefonoDT : validaTelefono,
											numCelular : validaTelefono,
											txtDesEmailDT : {
												email : true
											},
											// Datos del beneficiario
									
											// Datos del patron											
											txtDesNomrazonsocialDP : {
												maxlength : 100,
												alphanumeric : true
											},
											txtDesNomreplegalDP : {
												maxlength : 100,
												alphanumeric : true
											},
																			 
											txtNumTrabajadoresDP : {
												maxlength : 4,
												digits : true
											},
											txtRfcPatronDP : {
												maxlength : 13,
												minlength : 12,
												//regexp : /^(([A-Z]|[a-z]|\s){1})(([A-Z]|[a-z]){3})([0-9]{6})((([A-Z]|[a-z]|[0-9]){2,3}))$/
												regexp : /^[a-zA-Z]{3,4}(\d{6})((\D|\d){3})?$/	
											},
											txtRfcPatronCmpDP : {
												maxlength : 13,
												minlength : 12,
												//regexp : /^(([A-Z]|[a-z]|\s){1})(([A-Z]|[a-z]){3})([0-9]{6})((([A-Z]|[a-z]|[0-9]){2,3}))$/
												regexp : /^[a-zA-Z]{3,4}(\d{6})((\D|\d){3})?$/	
											},											
											txtCveRegpatDP : {
												maxlength : 11,
												regexp : /^(([A-Z]|[a-z]){1}\d{2}|\d{3})(\d{7,8})$/
											},
											 txtDomFiscalPtrIdDP: { 
												 alphanumeric: true 
											},
											 
											txtNumTelefonoPatronDP : {
												maxlength : 12,
												minlength : 12,
												alphanumeric : true
											},

											// txtNumDocumentoBDT numero del documento de identificacion
											/*
											 * txtFechaInicioTrabajoIT: {
											 * date:true },
											 * txtFechaFinTrabajoIT: { date:true },
											 */
											txtDesNomJefeInmediatoIT : {
												maxlength : 100,
												//minlength : 20,
												alphanumeric : true
											},
											txtDesLaboresDesempIT : {
												maxlength : 30,
												//minlength : 7,
												alphanumeric : true
											},
											txtImpSalarioPercibidoIT : {
												maxlength : 20
												//number: true
											},
											txtImpVacacionesIT : {
												maxlength : 20
												//number: true
											},
											/*
											 * sltCvePeriodoPagoIT: { digits:
											 * true },
											 */
											txtDesEspecifiquePPIT : {
												maxlength : 50,
												//minlength : 5,
												alphanumeric : true
											},
											txtNumDiasVacacionesIT : {
												number: true,
												maxlength : 4
											},
											txtImpAguinaldoIT : {
												//number: true
											},
											txtDiasAguinaldoIT : {
												digits : true,
												maxlength : 4
											},
											txtImpGratificcionIT : {
												//number: true
											},
											txtDesBaseComisionOtrosIT : {
												maxlength : 50,
												//minlength : 20,
												alphanumeric : true
											},
											txtImpGratificcionIT : {
												//number : true
											},
											txtBaseOtorgamientoIT : {
												alphanumeric : true
											},
											/*
											 * sltCveComprobantePagoIT: {
											 * alphanumeric: true },
											 */
											txtDesEspecifiqueCPIT : {
												maxlength : 50,
												//minlength : 5,
												alphanumeric : true
											},
											txtDesEspecifiqueFPIT : {
												maxlength : 50,
												//minlength : 5,
												alphanumeric : true,
												required : function(element) {
													return $("#cbxOtrosDT").is(':checked');
												}
												
											},
											txtContratoIT : {
												maxlength : 10,
//												required : function(element) {
//													return $("[name='rdContratoIT']:checked").val() == '1';
//												}
											},											
											txtFecFechaRiesgoTrabIT : {
												required : function(element) {
													return $("[name='rdRiesgo']:checked").val() == '1';
												}
											}											
										},
									messages: {
										validaCURP: {
										regexp: "El formato del CURP no es correcto"
										},
										cmbCveTipoDenuncianteDT:{
										regexp: "Favor de seleccionar un tipo de denunciante"	
										}
									}
									});
					validaPatronesDenunciados = $("#patronesDenunciadosForm")
					.validate(
							{
								rules : {
									txtDesNomrazonsocialCmpDP : {
										required : true
									},txtRfcPatronCmpDP : {
										maxlength : 13,
										minlength : 12,
										//regexp : /^(([A-Z]|[a-z]|\s){1})(([A-Z]|[a-z]){3})([0-9]{6})((([A-Z]|[a-z]|[0-9]){2,3}))$/
										regexp : /^[a-zA-Z]{3,4}(\d{6})((\D|\d){3})?$/	
									}
									
								}
							});
					

				});



function agregaReglasDenunciaForm() {
	//console.log()
	//console.log("1");
	$("#cmbCveTipoDenuncianteDT").rules("add", "comboRequerido");
	$("#cmbCveTipodocumentoDT").rules("add", "comboRequerido");
	//$("#sltSubdelegacionIT").rules("add", "comboRequerido");
	$("#txtFechaInicioTrabajoIT").rules("add", "required");
	//$("#txtFechaFinTrabajoIT").rules("add", "required");
	//console.log("2");
	$("#txtDesNombreDT").rules("add", "required");
	$("#txtDesPaternoDT").rules("add", "required");
	//console.log("3");
	
	//$("#txtDesMaternoDT").rules("add", "required");
	// /$("#txtCveNssDT").rules("add", "required");
	//$("#txtCveCurpDT").rules("add", "required");
	//$("#txtCveRfcDT").rules("add", "required");
	//$("#txtNumTelefonoDT").rules("add", "required");
	//$("#txtDesEmailDT").rules("add", "required");
	$("#cmbCveTipodocumentoDT").rules("add", "required");
	//$("#txtDomicilio").rules("add", "required");
	
	$("#txtDesUpload").rules("add", "required");
	//$("#numDocumento").rules("add", "required");
	//$("#txtFechaNacimientoDT").rules("add", "required");
	//$("#txtLugarNacimientoDT").rules("add", "required");
	$("#rdCveSexo").rules("add", "required");
	
	//console.log("4");
	if($("#cmbCveTipoDenuncianteDT").val() == TD_BENEFICIARIO){
		$("#txtDesPaternoBDT").rules("add", "required");
		//$("#txtDesMaternoBDT").rules("add", "required");
		$("#txtDesNombreBDT").rules("add", "required");
		$("#cmbCveTipodocumentoBDT").rules("add", "required");
		//$("#txtNumDocumentoBDT").rules("add", "required");
		//$("#direccionBeneficiario").rules("add", "required");
		$("#txtDesUploadBeneficiario").rules("add", "required");
		//$("#txtNumDocumentoBDT").rules("add", "required");
		
		$("#cmbCveTipodocumentoBDT").rules("add", "comboRequerido");
		//console.log("5");
	}else if($("#cmbCveTipoDenuncianteDT").val() == TD_REPLEGAL){
		$("#txtDesPaternoRLDT").rules("add", "required");
		//$("#txtDesMaternoRLDT").rules("add", "required");
		$("#txtDesNombreRLDT").rules("add", "required");
		$("#cmbCveTipodocumentoRLDT").rules("add", "required");
		//$("#txtNumDocumentoRLDT").rules("add", "required");
		//$("#direccionRepLegalDT").rules("add", "required");
		$("#txtDesUploadRP").rules("add", "required");	
		//$("#txtNumDocumentoRLDT").rules("add", "required");
		$("#cmbCveTipodocumentoRLDT").rules("add", "comboRequerido");
		
		//console.log("6");
	}
	
	
	
	
	//console.log("7");
	$("#rdDenunciadoAntesDP").rules("add", "required"); //		
	$("#txtDesNomrazonsocialDP").rules("add", "required");
	//$("#txtNumTrabajadoresDP").rules("add", "required");	
	//$("#txtDomicilioTrabajoDP").rules("add", "required");
	
	
	//console.log("8");
	$("#md1").rules("add", "motivoRechazoReqerido");
	$("#md2").rules("add", "motivoRechazoReqerido");
	$("#md3").rules("add", "motivoRechazoReqerido");
	$("#md4").rules("add", "motivoRechazoReqerido");
	
	agregaDependientesRequeridosChk("#md1", "#md1fechaInicio");
	agregaDependientesRequeridosChk("#md2", "#md2fechaInicio");
	agregaDependientesRequeridosChk("#md3", "#md3ImporteImss");
	agregaDependientesRequeridosChk("#md4", "#md4fechaInicio");

	//console.log("9");
	//$("#cbxSelGiroActividadDP").rules("add", "comboRequerido");
	$("#sltCvePeriodoPagoIT").rules("add", "comboRequerido");
	$("#txtDesLaboresDesempIT").rules("add", "required");
	$("#txtImpSalarioPercibidoIT").rules("add", "required");
	$("#sltCveComprobantePagoIT").rules("add", "comboRequerido");
	//console.log("10");
}

function quitaReglasDenunciaForm() {
	//alert("pasa por la funcion");
	$("#cmbCveTipoDenuncianteDT").rules("remove", "required");
	$("#txtFechaInicioTrabajoIT").rules("remove", "required");
	//$("#txtFechaFinTrabajoIT").rules("remove", "required");
	$("#txtDesNombreDT").rules("remove", "required");
	$("#txtDesPaternoDT").rules("remove", "required");
	//$("#txtDesMaternoDT").rules("remove", "required");
	//$("#txtCveCurpDT").rules("remove", "required");
	$("#txtCveRfcDT").rules("remove", "required");
	$("#txtNumTelefonoDT").rules("remove", "required");
	$("#txtDesEmailDT").rules("remove", "required");
	$("#cmbCveTipodocumentoDT").rules("remove", "comboRequerido");
	//$("#sltSubdelegacionIT").rules("remove", "comboRequerido");
	$("#rdDenunciadoAntesDP").rules("remove", "required"); //	
	$("#txtDesNomrazonsocialDP").rules("remove", "required");
	//$("#cbxSelGiroActividadDP").rules("remove", "required");
	$("#txtNumTrabajadoresDP").rules("remove", "required");
	//$("#numDocumento").rules("remove", "required");
	
	//alert("pasa por la funcion 1");
	//$("#txtFechaNacimientoDT").rules("remove", "required");
	//$("#txtLugarNacimientoDT").rules("remove", "required");
	$("#rdCveSexo").rules("remove", "required");
	
	//alert("pasa por la funcion 2");
	$("#txtDesPaternoBDT").rules("remove", "required");
	//$("#txtDesMaternoBDT").rules("remove", "required");
	$("#txtDesNombreBDT").rules("remove", "required");
	$("#cmbCveTipodocumentoBDT").rules("remove", "required");
	//$("#txtNumDocumentoBDT").rules("remove", "required");	
	$("#txtDesPaternoRLDT").rules("remove", "required");
	//$("#txtDesMaternoRLDT").rules("remove", "required");
	$("#txtDesNombreRLDT").rules("remove", "required");
	$("#cmbCveTipodocumentoRLDT").rules("remove", "required");
	//$("#txtNumDocumentoRLDT").rules("remove", "required");	
	
	//alert("pasa por la funcion 2.1");
	
	$("#txtDesUpload").rules("remove", "required");
	$("#txtDesUploadBeneficiario").rules("remove", "required");
	$("#txtDesUploadRP").rules("remove", "required");
	$("#txtDomicilio").rules("remove", "required");
	$("#txtDomicilioTrabajoDP").rules("remove", "required");
	
	
	//alert("pasa por la funcion 3");
	//$("#txtNumDocumentoRLDT").rules("remove", "required");
	//$("#txtNumDocumentoBDT").rules("remove", "required");
	
	$("#md1").rules("remove", "motivoRechazoReqerido");
	$("#md2").rules("remove", "motivoRechazoReqerido");
	$("#md3").rules("remove", "motivoRechazoReqerido");
	$("#md4").rules("remove", "motivoRechazoReqerido");
	
	//alert("pasa por la funcion 4");
	//$("#cbxSelGiroActividadDP").rules("remove", "comboRequerido");
	$("#sltCvePeriodoPagoIT").rules("remove", "comboRequerido");
	$("#txtDesLaboresDesempIT").rules("remove", "required");
	$("#txtImpSalarioPercibidoIT").rules("remove", "required");
	$("#sltCveComprobantePagoIT").rules("remove", "comboRequerido");
	
	//alert("pasa por la funcion 5");
	$("#txtDomicilio").rules("remove", "required");
	$("#direccionRepLegalDT").rules("remove", "required");		
	$("#direccionBeneficiario").rules("remove", "required");
	$("#txtDomicilioTrabajoDP").rules("remove", "required");
	
	$("#cmbCveTipodocumentoDT").rules("remove", "comboRequerido");
	$("#cmbCveTipodocumentoBDT").rules("remove", "comboRequerido");
	$("#cmbCveTipodocumentoRLDT").rules("remove", "comboRequerido");

	$("#txtCveNssDT").rules("remove", "required");
}

function agregaDependientesRequeridosChk(paramChk, paramDependientes) {
	if ($(paramChk).is(':checked')){
		$(paramDependientes).each(function(){
		    $(this).rules("add", "required");
		});		
	}
}







//Validaciones para explorer 8
var expRgCURP=/^[a-zA-Z]{1}[aeiouxAEIOUX]{1}[a-zA-Z]{2}\d{2}([0][1-9]|[1][0-2])([0][1-9]|[1][0-9]|[2][0-9]|[3][0-1])[hmHM]{1}(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE|  as|bc|bs|cc|cs|ch|cl|cm|df|dg|gt|gr|hg|jc|mc|mn|ms|nt|nl|oc|pl|qt|qr|sp|sp|sr|tc|ts|tl|vz|yn|zs|ne)[^aeiouAEIOU]{3}(\d|[a-zA-Z])\d$/;
var expRgRFC=/^[a-zA-Z]{3,4}(\d{6})((\D|\d){3})?$/;
var expRgEmail=/^[0-9a-z_\-\.]+@[0-9a-z\-\.]+\.[a-z]{2,4}$/i;
var expRgTele=/^[0-9]{8,12}$/;
var expRgNumTrab=/^[0-9]{1,4}$/;
var expRgNumVaca=/^[0-9]{1,4}$/;
var expRgRP= /^(([A-Z]|[a-z]){1}\d{2}|\d{3})(\d{7,8})$/

function validaCampo(isRequired,isCombo,expresionRegular,id){

	var flag=true;
	if(isRequired && !isCombo && $(''+id).val()=='' ){
		flag=false;
	}else if(isRequired && isCombo && $(''+id).val()==-1){
		flag=false;
	}else if(isRequired && !isCombo && $(''+id).val()!=null && $(''+id).val()!='' && expresionRegular!='' && $(''+id).val().search(expresionRegular)==-1){
		flag=false;				
	}else if(!isRequired && !isCombo && $(''+id).val()!=null && $(''+id).val()!='' && expresionRegular!='' && $(''+id).val().search(expresionRegular)==-1){
		flag=false;
	}
	
	
	if(flag){
		$("label[for='"+id.substring(1)+"']").text("");
	}
	return flag;
}

function validaSeguroSocial(){
//	if($("[name='rdCveNssDT']:checked").val()==true && $("#txtCveNssDT").val()==""){
//		return false;
//	}else{
//		$("label[for='txtCveNssDT']").text("");
//		return true;
//	}
	return true;
}

function validaContrato(){
//	if($("[name='rdContratoIT']:checked").val()==true && $("#txtContratoIT").val()==""){
//		return false;
//	}else{
//		$("label[for='txtContratoIT']").text("");
//		return true;
//	}
	return true;
}

function validaAccidenteTrabajo(){
	if($("[name='rdRiesgo']:checked").val()==true && $("#txtFecFechaRiesgoTrabIT").val()==""){
		return false;
	}else{
		$("label[for='txtFecFechaRiesgoTrabIT']").text("");
		return true;
	}
}

function validaMotivosDenunciaEnvia(){
	
	var flag=true;
	$("label[for='md1fechaInicio']").text("");	
	$("label[for='md2fechaInicio']").text("");	
	$("label[for='md3ImporteImss']").text("");	
	$("label[for='md4fechaInicio']").text("");		
	$("label[for='md']").text("");
	
	
	if(!$("#md1").is(':checked') && !$("#md2").is(':checked') && !$("#md3").is(':checked') && !$("#md4").is(':checked')){
		$("label[for='md']").addClass("error");
		$("label[for='md']").text("Debe seleccionar al menos un motivo de denuncia");	
		flag=false;
		return flag;
	}
	
	if($("#md1").is(':checked') && $("#md1fechaInicio").val()==''){
		$("label[for='md1fechaInicio']").addClass("error");
		$("label[for='md1fechaInicio']").text("Este campo es obligatorio.");	
		flag=false;
	}
	if($("#md2").is(':checked') && $("#md2fechaInicio").val()==''){
		$("label[for='md2fechaInicio']").addClass("error");
		$("label[for='md2fechaInicio']").text("Este campo es obligatorio.");	
		flag=false;
	}
	if($("#md3").is(':checked') && $("#md3ImporteImss").val()==''){
		$("label[for='md3ImporteImss']").addClass("error");
		$("label[for='md3ImporteImss']").text("Este campo es obligatorio.");	
		flag=false;
	}
	if($("#md4").is(':checked') && $("#md4fechaInicio").val()==''){
		$("label[for='md4fechaInicio']").addClass("error");
		$("label[for='md4fechaInicio']").text("Este campo es obligatorio.");	
		flag=false;
	}
	
	return flag;
}


function validaMotivosDenunciaGuardar(){
	
	var flag=true;
	$("label[for='md1fechaInicio']").text("");	
	$("label[for='md2fechaInicio']").text("");	
	$("label[for='md3ImporteImss']").text("");	
	$("label[for='md4fechaInicio']").text("");		
	$("label[for='md']").text("");
	

	if($("#md1").is(':checked') && $("#md1fechaInicio").val()==''){
		$("label[for='md1fechaInicio']").addClass("error");
		$("label[for='md1fechaInicio']").text("Este campo es obligatorio.");	
		flag=false;
	}
	if($("#md2").is(':checked') && $("#md2fechaInicio").val()==''){
		$("label[for='md2fechaInicio']").addClass("error");
		$("label[for='md2fechaInicio']").text("Este campo es obligatorio.");	
		flag=false;
	}
	if($("#md3").is(':checked') && $("#md3ImporteImss").val()==''){
		$("label[for='md3ImporteImss']").addClass("error");
		$("label[for='md3ImporteImss']").text("Este campo es obligatorio.");	
		flag=false;
	}
	if($("#md4").is(':checked') && $("#md4fechaInicio").val()==''){
		$("label[for='md4fechaInicio']").addClass("error");
		$("label[for='md4fechaInicio']").text("Este campo es obligatorio.");	
		flag=false;
	}
	
	return flag;
}




function validarFormaEnvia(){	
		
		//alert('pasa por aca');
		var flagValido=true;
		var reglas = new Object();
		
		
		reglas['validaCampo(true,true,"","#cmbCveTipoDenuncianteDT")'] = 'cmbCveTipoDenuncianteDT';	
		
		
		reglas['validaCampo(true,false,"","#txtDesPaternoDT")'] = 'txtDesPaternoDT';	
		//reglas['validaCampo(true,false,"","#txtDesMaternoDT")'] = 'txtDesMaternoDT';	
		reglas['validaCampo(true,false,"","#txtDesNombreDT")'] = 'txtDesNombreDT';	
		reglas['validaSeguroSocial()'] = 'txtCveNssDT';
		reglas['validaCampo(false,false,expRgCURP,"#txtCveCurpDT")'] = 'txtCveCurpDT';	
		reglas['validaCampo(false,false,expRgRFC,"#txtCveRfcDT")'] = 'txtCveRfcDT';	
		reglas['validaCampo(false,false,"","#txtDomicilio")'] = 'txtDomicilio';	
		reglas['validaCampo(false,false,expRgEmail,"#txtDesEmailDT")'] = 'txtDesEmailDT';	
		reglas['validaCampo(false,false,expRgTele,"#txtNumTelefonoDT")'] = 'txtNumTelefonoDT';	
		reglas['validaCampo(false,false,expRgTele,"#numCelular")'] = 'numCelular';			
		reglas['validaCampo(true,true,"","#cmbCveTipodocumentoDT")'] = 'cmbCveTipodocumentoDT';	
		reglas['validaCampo(true,true,"","#sltSubdelegacionIT")'] = 'sltSubdelegacionIT';	
		reglas['validaCampo(false,false,"","#numDocumento")'] = 'numDocumento';			
		reglas['validaCampo(true,false,"","#txtDesNomrazonsocialDP")'] = 'txtDesNomrazonsocialDP';	
		reglas['validaCampo(false,false,"","#txtDomicilioTrabajoDP")'] = 'txtDomicilioTrabajoDP';	
		//reglas['validaCampo(true,true,"","#cbxSelSectordDP")'] = 'cbxSelSectordDP';
		//reglas['validaCampo(true,true,"","#cbxSelGiroActividadDP")'] = 'cbxSelGiroActividadDP';		
		reglas['validaCampo(false,false,expRgRFC,"#txtRfcPatronDP")'] = 'txtRfcPatronDP';
		reglas['validaCampo(false,false,expRgRP,"#txtCveRegpatDP")'] = 'txtCveRegpatDP';		
		reglas['validaCampo(false,false,expRgNumTrab,"#txtNumTrabajadoresDP")'] = 'txtNumTrabajadoresDP';
		reglas['validaCampo(false,false,"","#txtDomFiscalPtrIdDP")'] = 'txtDomFiscalPtrIdDP';		
		reglas['validaCampo(false,false,expRgTele,"#txtNumTelefonoPatronDP")'] = 'txtNumTelefonoPatronDP';
		reglas['validaCampo(true,false,"","#txtFechaInicioTrabajoIT")'] = 'txtFechaInicioTrabajoIT';
		reglas['validaContrato()'] = 'txtContratoIT';		
		reglas['validaCampo(true,false,"","#txtDesLaboresDesempIT")'] = 'txtDesLaboresDesempIT';
		reglas['validaCampo(true,false,"","#txtImpSalarioPercibidoIT")'] = 'txtImpSalarioPercibidoIT';
		reglas['validaCampo(true,true,"","#sltCvePeriodoPagoIT")'] = 'sltCvePeriodoPagoIT';
		reglas['validaCampo(false,false,expRgNumVaca,"#txtNumDiasVacacionesIT")'] = 'txtNumDiasVacacionesIT';
		reglas['validaCampo(false,false,expRgNumVaca,"#txtDiasAguinaldoIT")'] = 'txtDiasAguinaldoIT';
		reglas['validaCampo(true,true,"","#sltCveComprobantePagoIT")'] = 'sltCveComprobantePagoIT';
		reglas['validaAccidenteTrabajo()'] = 'txtFecFechaRiesgoTrabIT';		
		reglas['validaMotivosDenunciaEnvia()'] = '';
		
		
		//Si esta seleccionado beneficiario
		if($("#cmbCveTipoDenuncianteDT").val()=='2'){
			reglas['validaCampo(true,false,"","#txtDesPaternoBDT")'] = 'txtDesPaternoBDT';	
			//reglas['validaCampo(true,false,"","#txtDesMaternoBDT")'] = 'txtDesMaternoBDT';	
			reglas['validaCampo(true,false,"","#txtDesNombreBDT")'] = 'txtDesNombreBDT';	
			reglas['validaCampo(false,false,"","#direccionBeneficiario")'] = 'direccionBeneficiario';	
			reglas['validaCampo(true,true,"","#cmbCveTipodocumentoBDT")'] = 'cmbCveTipodocumentoBDT';		
			reglas['validaCampo(false,false,"","#txtNumDocumentoBDT")'] = 'txtNumDocumentoBDT';		
		}
		//si esta seleccionado representante legal
		if($("#cmbCveTipoDenuncianteDT").val()=='3'){
			reglas['validaCampo(true,false,"","#txtDesPaternoRLDT")'] = 'txtDesPaternoRLDT';	
			//reglas['validaCampo(true,false,"","#txtDesMaternoRLDT")'] = 'txtDesMaternoRLDT';	
			reglas['validaCampo(true,false,"","#txtDesNombreRLDT")'] = 'txtDesNombreRLDT';	
			reglas['validaCampo(false,false,"","#direccionRepLegalDT")'] = 'direccionRepLegalDT';	
			reglas['validaCampo(true,true,"","#cmbCveTipodocumentoRLDT")'] = 'cmbCveTipodocumentoRLDT';	
			reglas['validaCampo(false,false,"","#txtNumDocumentoRLDT")'] = 'txtNumDocumentoRLDT';		
		}
		
	//	validaGuardar.resetForm();
		for (var k in reglas) {
		    if (reglas.hasOwnProperty(k)) {
		    	var flag=eval(k);
		    	if(!flag){		
		    		flagValido=false;
		    		$("label[for='"+reglas[k]+"']").text("Campo inv\u00e1lido o Requerido");
		    		$("label[for='"+reglas[k]+"']").addClass("error");
		    		
		    	}
		    }
		}	
		return flagValido;
}



function validarFormaGuardar(){	
		var flagValido=true;
		var reglas = new Object();										  
		reglas['validaCampo(true,true,"","#cmbCveTipoDenuncianteDT")'] = 'cmbCveTipoDenuncianteDT';	
		reglas['validaCampo(false,false,"","#txtDesPaternoDT")'] = 'txtDesPaternoDT';	
		reglas['validaCampo(false,false,"","#txtDesMaternoDT")'] = 'txtDesMaternoDT';	
		reglas['validaCampo(false,false,"","#txtDesNombreDT")'] = 'txtDesNombreDT';	
		reglas['validaSeguroSocial()'] = 'txtCveNssDT';
		reglas['validaCampo(false,false,expRgCURP,"#txtCveCurpDT")'] = 'txtCveCurpDT';	
		reglas['validaCampo(false,false,expRgRFC,"#txtCveRfcDT")'] = 'txtCveRfcDT';	
		reglas['validaCampo(false,false,"","#txtDomicilio")'] = 'txtDomicilio';	
		reglas['validaCampo(false,false,expRgEmail,"#txtDesEmailDT")'] = 'txtDesEmailDT';
		reglas['validaCampo(false,true,"","#sltSubdelegacionIT")'] = 'sltSubdelegacionIT';	
		reglas['validaCampo(false,false,expRgTele,"#txtNumTelefonoDT")'] = 'txtNumTelefonoDT';	
		reglas['validaCampo(false,false,expRgTele,"#numCelular")'] = 'numCelular';			
		reglas['validaCampo(false,true,"","#cmbCveTipodocumentoDT")'] = 'cmbCveTipodocumentoDT';	
		reglas['validaCampo(false,false,"","#numDocumento")'] = 'numDocumento';			
		reglas['validaCampo(false,false,"","#txtDesNomrazonsocialDP")'] = 'txtDesNomrazonsocialDP';	
		reglas['validaCampo(false,false,"","#txtDomicilioTrabajoDP")'] = 'txtDomicilioTrabajoDP';	
		//reglas['validaCampo(false,true,"","#cbxSelSectordDP")'] = 'cbxSelSectordDP';
		//reglas['validaCampo(false,true,"","#cbxSelGiroActividadDP")'] = 'cbxSelGiroActividadDP';		
		reglas['validaCampo(false,false,expRgRFC,"#txtRfcPatronDP")'] = 'txtRfcPatronDP';
		reglas['validaCampo(false,false,expRgRP,"#txtCveRegpatDP")'] = 'txtCveRegpatDP';		
		reglas['validaCampo(false,false,expRgNumTrab,"#txtNumTrabajadoresDP")'] = 'txtNumTrabajadoresDP';
		reglas['validaCampo(false,false,"","#txtDomFiscalPtrIdDP")'] = 'txtDomFiscalPtrIdDP';		
		reglas['validaCampo(false,false,expRgTele,"#txtNumTelefonoPatronDP")'] = 'txtNumTelefonoPatronDP';
		reglas['validaCampo(false,false,"","#txtFechaInicioTrabajoIT")'] = 'txtFechaInicioTrabajoIT';
		reglas['validaContrato()'] = 'txtContratoIT';		
		reglas['validaCampo(false,false,"","#txtDesLaboresDesempIT")'] = 'txtDesLaboresDesempIT';
		reglas['validaCampo(false,false,"","#txtImpSalarioPercibidoIT")'] = 'txtImpSalarioPercibidoIT';
		reglas['validaCampo(false,true,"","#sltCvePeriodoPagoIT")'] = 'sltCvePeriodoPagoIT';
		reglas['validaCampo(false,false,expRgNumVaca,"#txtNumDiasVacacionesIT")'] = 'txtNumDiasVacacionesIT';
		reglas['validaCampo(false,false,expRgNumVaca,"#txtDiasAguinaldoIT")'] = 'txtDiasAguinaldoIT';
		reglas['validaCampo(false,true,"","#sltCveComprobantePagoIT")'] = 'sltCveComprobantePagoIT';
		reglas['validaAccidenteTrabajo()'] = 'txtFecFechaRiesgoTrabIT';		
		reglas['validaMotivosDenunciaGuardar()'] = '';
		
		//Si esta seleccionado beneficiario
		if($("#cmbCveTipoDenuncianteDT").val()=='2'){
			reglas['validaCampo(false,false,"","#txtDesPaternoBDT")'] = 'txtDesPaternoBDT';	
			reglas['validaCampo(false,false,"","#txtDesMaternoBDT")'] = 'txtDesMaternoBDT';	
			reglas['validaCampo(false,false,"","#txtDesNombreBDT")'] = 'txtDesNombreBDT';	
			reglas['validaCampo(false,false,"","#direccionBeneficiario")'] = 'direccionBeneficiario';	
			reglas['validaCampo(false,true,"","#cmbCveTipodocumentoBDT")'] = 'cmbCveTipodocumentoBDT';		
			reglas['validaCampo(false,false,"","#txtNumDocumentoBDT")'] = 'txtNumDocumentoBDT';		
		}
		//si esta seleccionado representante legal
		if($("#cmbCveTipoDenuncianteDT").val()=='3'){
			reglas['validaCampo(false,false,"","#txtDesPaternoRLDT")'] = 'txtDesPaternoRLDT';	
			reglas['validaCampo(false,false,"","#txtDesMaternoRLDT")'] = 'txtDesMaternoRLDT';	
			reglas['validaCampo(false,false,"","#txtDesNombreRLDT")'] = 'txtDesNombreRLDT';	
			reglas['validaCampo(false,false,"","#direccionRepLegalDT")'] = 'direccionRepLegalDT';	
			reglas['validaCampo(false,true,"","#cmbCveTipodocumentoRLDT")'] = 'cmbCveTipodocumentoRLDT';	
			reglas['validaCampo(false,false,"","#txtNumDocumentoRLDT")'] = 'txtNumDocumentoRLDT';		
		}
		
		//validaGuardar.resetForm();
		for (var k in reglas) {
		    if (reglas.hasOwnProperty(k)) {
		    	var flag=eval(k);
		    	if(!flag){		
		    		flagValido=false;
		    		$("label[for='"+reglas[k]+"']").text("Campo inv\u00e1lido o Requerido");
		    		$("label[for='"+reglas[k]+"']").addClass("error");		    		
		    	}
		    }
		}	
		return flagValido;
}

