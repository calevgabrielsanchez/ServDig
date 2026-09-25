var patronesCmpl = [];
var i;
var patronPrincipal = 1;
var validaEnviar;
var validaGuardar;

var denunciadoAntes;
var checkboxValues = [];
var oDgPatronesDenunciados;	
var dtPatronesDenunciados;

var urlDomicilios = "/gestionDomicilios-web/static/resources/js/delta/domicilios/Domicilio.js";
var ventanaValidacion=false;
var inicioValidacion=false;

$(document).ready(function() {
		// llenaComboPreguntas();
		$.getScript(urlDomicilios)
		.done(function(script, textStatus) {
		  DomicilioCtrl.init('domicilioUbicar');
		  DomicilioCtrl.setOnCloseCallback(cambiarDomicilio);
		// Configuración del boton disparador de domiiclios
		$('#ubicar').click(function() {
			// var idDelegacion = 39;
			
			if(objDenuncia!=null && objDenuncia.cveDenuncia != null){
				DomicilioCtrl.localizar();
			}else{
				if(confirm("Es necesario guardar la denuncia antes de agregar el domicilio,\u00BFDesea guardar la denuncia?")){
					if(validaForma()){
						$('#btnGuardar').click();
						DomicilioCtrl.localizar();
					}else{
						alert("Tiene campos con error, favor de verificar")
					}
				}
			}
			
			
		});
		
		})
		.fail(
		function(jqxhr, settings, exception) { 
		alert('Error al cargar el script'); 
	}); 
		
		$.getScript(urlDomicilios)
		.done(function(script, textStatus) {
		  DomicilioCtrl.init('domicilioUbicar1');
		  DomicilioCtrl.setOnCloseCallback(cambiarDomicilio1);
		// Configuración del boton disparador de domiiclios
		$('#ubicar1').click(function() {
			// var idDelegacion = 39;
			
			if(objDenuncia!=null && objDenuncia.cveDenuncia != null){
				DomicilioCtrl.localizar();
			}else{
				if(confirm("Es necesario guardar la denuncia antes de agregar el domicilio,\u00BFDesea guardar la denuncia?")){
					if(validaForma()){
						$('#btnGuardar').click();
						DomicilioCtrl.localizar();
					}else{
						alert("Tiene campos con error, favor de verificar")
					}
				}
			}
			
			
		});
		
		})
		.fail(
		function(jqxhr, settings, exception) { 
		alert('Error al cargar el script'); 
	}); 
		$.getScript(urlDomicilios)
		.done(function(script, textStatus) {
		  DomicilioCtrl.init('domicilioUbicar2');
		  DomicilioCtrl.setOnCloseCallback(cambiarDomicilio2);
		// Configuración del boton disparador de domiiclios
		$('#ubicar2').click(function() {
			// var idDelegacion = 39;
			
			if(objDenuncia!=null && objDenuncia.cveDenuncia != null){
				DomicilioCtrl.localizar();
			}else{
				if(confirm("Es necesario guardar la denuncia antes de agregar el domicilio,\u00BFDesea guardar la denuncia?")){
					if(validaForma()){
						$('#btnGuardar').click();
						DomicilioCtrl.localizar();
					}else{
						alert("Tiene campos con error, favor de verificar")
					}
				}
			}
			
			
		});
		
		})
		.fail(
		function(jqxhr, settings, exception) { 
		alert('Error al cargar el script'); 
	}); 
		$.getScript(urlDomicilios)
		.done(function(script, textStatus) {
		  DomicilioCtrl.init('domicilioUbicar3');
		  DomicilioCtrl.setOnCloseCallback(cambiarDomicilio3);
		// Configuración del boton disparador de domiiclios
		$('#ubicar3').click(function() {
			// var idDelegacion = 39;
			
			if(objDenuncia!=null && objDenuncia.cveDenuncia != null){
				DomicilioCtrl.localizar();
			}else{
				if(confirm("Es necesario guardar la denuncia antes de agregar el domicilio,\u00BFDesea guardar la denuncia?")){
					if(validaForma()){
						$('#btnGuardar').click();
						DomicilioCtrl.localizar();
					}else{
						alert("Tiene campos con error, favor de verificar")
					}
				}
			}
			
			
		});
		
		})
		.fail(
		function(jqxhr, settings, exception) { 
		alert('Error al cargar el script'); 
	}); 
		
		$.getScript(urlDomicilios)
		.done(function(script, textStatus) {
		  DomicilioCtrl.init('domicilioUbicar4');
		  DomicilioCtrl.setOnCloseCallback(cambiarDomicilio4);
		// Configuración del boton disparador de domiiclios
		$('#ubicar4').click(function() {
			// var idDelegacion = 39;
			
			if(objDenuncia!=null && objDenuncia.cveDenuncia != null){
				DomicilioCtrl.localizar();
			}else{
				if(confirm("Es necesario guardar la denuncia antes de agregar el domicilio,\u00BFDesea guardar la denuncia?")){
					if(validaForma()){
						$('#btnGuardar').click();
						DomicilioCtrl.localizar();
					}else{
						alert("Tiene campos con error, favor de verificar")
					}
				}
			}
			
			
		});
		
		})
		.fail(
		function(jqxhr, settings, exception) { 
		alert('Error al cargar el script'); 
	}); 
		
		$.getScript(urlDomicilios)
		.done(function(script, textStatus) {
		  DomicilioCtrl.init('domicilioUbicar5');
		  DomicilioCtrl.setOnCloseCallback(cambiarDomicilio5);
		// Configuración del boton disparador de domiiclios
		$('#ubicar5').click(function() {
			// var idDelegacion = 39;
			
			if(objDenuncia!=null && objDenuncia.cveDenuncia != null){
				DomicilioCtrl.localizar();
			}else{
				if(confirm("Es necesario guardar la denuncia antes de agregar el domicilio,\u00BFDesea guardar la denuncia?")){
					if(validaForma()){
						$('#btnGuardar').click();
						DomicilioCtrl.localizar();
					}else{
						alert("Tiene campos con error, favor de verificar")
					}
				}
			}
			
			
		});
		
		})
		.fail(
		function(jqxhr, settings, exception) { 
		alert('Error al cargar el script'); 
	}); 
		var cambiarDomicilio = function() {
			
			var objDomicilio =  this;
			
			if(objDomicilio != undefined && objDomicilio != null) {
				docimicilioUbicado=true;
				try {
					var domicilio =jQuery.trim(objDomicilio.vialidadPrimaria.nombre) + ' ' + jQuery.trim(objDomicilio.numExterior1) + ', ' +jQuery.trim(objDomicilio.numextalf) + ', ' +
					jQuery.trim(objDomicilio.numintnum) + ', ' +jQuery.trim(objDomicilio.numintalf) +', '+
					jQuery.trim(objDomicilio.asentamiento.nombre) + ', ' +
					jQuery.trim(objDomicilio.asentamiento.codigoPostal.codigoPostal) + ' '+jQuery.trim(objDomicilio.asentamiento.localidad.municipio.nombre) + ', '+ jQuery.trim(objDomicilio.asentamiento.localidad.municipio.entidadFederativa.nombre);  
					 if(ubicaDomicilio==1){
						 $("#txtDomicilio").prop("value", domicilio);
						 guardaDomicilio(objDomicilio, objDenuncia);
					 }else if(ubicaDomicilio==2){
						 $("#direccionBeneficiario").prop("value", domicilio);
						 guardaDomicilioBen(objDomicilio, objDenuncia);
					 }else if(ubicaDomicilio==3){
						 $("#direccionRepLegalDT").prop("value", domicilio);	
						 guardaDomicilioRep(objDomicilio, objDenuncia);
					 }
					 
					
					 // SE DEBE ACTUALIZAR LA DENUNCIA CON EL ID DOMICILIO
						// GENERADO
				
				
				} catch(e) {}
			}
		}
	
		
		var cambiarDomicilio1 = function() {
			
			var objDomicilio =  this;
			
			if(objDomicilio != undefined && objDomicilio != null) {
				docimicilioUbicado=true;
				try {
					
					
					var domicilio =jQuery.trim(objDomicilio.vialidadPrimaria.nombre) + ' ' + jQuery.trim(objDomicilio.numExterior1) + ', ' +jQuery.trim(objDomicilio.numExteriorAlf) + ', ' +
					jQuery.trim(objDomicilio.numInterior) + ', ' +jQuery.trim(objDomicilio.numInteriorAlf) +', '+
					jQuery.trim(objDomicilio.asentamiento.nombre) + ', ' +
					jQuery.trim(objDomicilio.asentamiento.codigoPostal.codigoPostal) + ' '+jQuery.trim(objDomicilio.asentamiento.localidad.municipio.nombre) + ', '+ jQuery.trim(objDomicilio.asentamiento.localidad.municipio.entidadFederativa.nombre); // SE DEBE GUARDAR EL DOMICILIO
					 if(ubicaDomicilio==1){
						 $("#txtDomicilio").prop("value", domicilio);
						 guardaDomicilio(objDomicilio, objDenuncia);
					 }else if(ubicaDomicilio==2){
						 $("#direccionBeneficiario").prop("value", domicilio);
						 guardaDomicilioBen(objDomicilio, objDenuncia);
					 }else if(ubicaDomicilio==3){
						 $("#direccionRepLegalDT").prop("value", domicilio);	
						 guardaDomicilioRep(objDomicilio, objDenuncia);
					 }
					 
					 // SE DEBE GUARDAR EL DOMICILIO
					 
					 
					
					 
					
					 // SE DEBE ACTUALIZAR LA DENUNCIA CON EL ID DOMICILIO
						// GENERADO
				
				
				} catch(e) {}
			}
		}
		
var cambiarDomicilio2 = function() {
			
			var objDomicilio =  this;
			
			if(objDomicilio != undefined && objDomicilio != null) {
				docimicilioUbicado=true;
				try {
					
					
					var domicilio =jQuery.trim(objDomicilio.vialidadPrimaria.nombre) + ' ' + jQuery.trim(objDomicilio.numExterior1) + ', ' +jQuery.trim(objDomicilio.numExteriorAlf) + ', ' +
					jQuery.trim(objDomicilio.numInterior) + ', ' +jQuery.trim(objDomicilio.numInteriorAlf) +', '+
					jQuery.trim(objDomicilio.asentamiento.nombre) + ', ' +
					jQuery.trim(objDomicilio.asentamiento.codigoPostal.codigoPostal) + ' '+jQuery.trim(objDomicilio.asentamiento.localidad.municipio.nombre) + ', '+ jQuery.trim(objDomicilio.asentamiento.localidad.municipio.entidadFederativa.nombre); // SE DEBE GUARDAR EL DOMICILIO
						 // SE DEBE GUARDAR EL DOMICILIO
					 if(ubicaDomicilio==1){
						 $("#txtDomicilio").prop("value", domicilio);
						 guardaDomicilio(objDomicilio, objDenuncia);
					 }else if(ubicaDomicilio==2){
						 $("#direccionBeneficiario").prop("value", domicilio);
						 guardaDomicilioBen(objDomicilio, objDenuncia);
					 }else if(ubicaDomicilio==3){
						 $("#direccionRepLegalDT").prop("value", domicilio);	
						 guardaDomicilioRep(objDomicilio, objDenuncia);
					 }
					 
					
					 
					
					 // SE DEBE ACTUALIZAR LA DENUNCIA CON EL ID DOMICILIO
						// GENERADO
				
				
				} catch(e) {}
			}
		}

var cambiarDomicilio3 = function() {
	
	var objDomicilio =  this;
	
	if(objDomicilio != undefined && objDomicilio != null) {
		docimicilioUbicado=true;
		try {
			
			var domicilio =jQuery.trim(objDomicilio.vialidadPrimaria.nombre) + ' ' + jQuery.trim(objDomicilio.numExterior1) + ', ' +jQuery.trim(objDomicilio.numExteriorAlf) + ', ' +
			jQuery.trim(objDomicilio.numInterior) + ', ' +jQuery.trim(objDomicilio.numInteriorAlf) +', '+
			jQuery.trim(objDomicilio.asentamiento.nombre) + ', ' +
			jQuery.trim(objDomicilio.asentamiento.codigoPostal.codigoPostal) + ' '+jQuery.trim(objDomicilio.asentamiento.localidad.municipio.nombre) + ', '+ jQuery.trim(objDomicilio.asentamiento.localidad.municipio.entidadFederativa.nombre); // SE DEBE GUARDAR EL DOMICILIO
			 if(ubicaDomicilio==1){
				 $("#txtDomicilio").prop("value", domicilio);
				 guardaDomicilio(objDomicilio, objDenuncia);
			 }else if(ubicaDomicilio==2){
				 $("#direccionBeneficiario").prop("value", domicilio);
				 guardaDomicilioBen(objDomicilio, objDenuncia);
			 }else if(ubicaDomicilio==3){
				 $("#direccionRepLegalDT").prop("value", domicilio);	
				 guardaDomicilioRep(objDomicilio, objDenuncia);
			 }else if(ubicaDomicilio==4){
				 $("#txtDomicilioTrabajoDP").prop("value", domicilio);	
				 guardaDomicilioPat(objDomicilio, objDenuncia);
			 }
			 else if(ubicaDomicilio==5){
				 $("#txtDomFiscalPtrIdDP").prop("value", domicilio);	
				
			 }
			 
			
			 
			
			 // SE DEBE ACTUALIZAR LA DENUNCIA CON EL ID DOMICILIO GENERADO
		
		
		} catch(e) {}
	}
}

var cambiarDomicilio4 = function() {
	
	var objDomicilio =  this;
	
	if(objDomicilio != undefined && objDomicilio != null) {
		docimicilioUbicado=true;
		try {
			
			
			var domicilio =jQuery.trim(objDomicilio.vialidadPrimaria.nombre) + ' ' + jQuery.trim(objDomicilio.numExterior1) + ', ' +jQuery.trim(objDomicilio.numExteriorAlf) + ', ' +
			jQuery.trim(objDomicilio.numInterior) + ', ' +jQuery.trim(objDomicilio.numInteriorAlf) +', '+
			jQuery.trim(objDomicilio.asentamiento.nombre) + ', ' +
			jQuery.trim(objDomicilio.asentamiento.codigoPostal.codigoPostal) + ' '+jQuery.trim(objDomicilio.asentamiento.localidad.municipio.nombre) + ', '+ jQuery.trim(objDomicilio.asentamiento.localidad.municipio.entidadFederativa.nombre); // SE DEBE GUARDAR EL DOMICILIO
			 if(ubicaDomicilio==1){
				 $("#txtDomicilio").prop("value", domicilio);
				 guardaDomicilio(objDomicilio, objDenuncia);
			 }else if(ubicaDomicilio==2){
				 $("#direccionBeneficiario").prop("value", domicilio);
				 guardaDomicilioBen(objDomicilio, objDenuncia);
			 }else if(ubicaDomicilio==3){
				 $("#direccionRepLegalDT").prop("value", domicilio);	
				 guardaDomicilioRep(objDomicilio, objDenuncia);
			 }else if(ubicaDomicilio==4){
				 $("#txtDomicilioTrabajoDP").prop("value", domicilio);	
				 guardaDomicilioCenTrab(objDomicilio, objDenuncia);
			 }
			 else if(ubicaDomicilio==5){
				 $("#txtDomFiscalPtrIdDP").prop("value", domicilio);	
				 guardaDomicilioPat(objDomicilio, objDenuncia);
			 }
			 // SE DEBE ACTUALIZAR LA DENUNCIA CON EL ID DOMICILIO GENERADO
		} catch(e) {}
	}
}

var cambiarDomicilio5 = function() {
	
	var objDomicilio =  this;
	
	if(objDomicilio != undefined && objDomicilio != null) {
		docimicilioUbicado=true;
		try {
			var domicilio = '';
			
			if(objDomicilio.vialidadPrimaria!=undefined){
				domicilio = domicilio + jQuery.trim(objDomicilio.vialidadPrimaria.nombre) + ', ';
			}
			
			
			domicilio = domicilio + 'Num Ext ' + jQuery.trim(objDomicilio.numExterior1)+ ', ';
			if(jQuery.trim(objDomicilio.numExteriorAlf)!=''){
				domicilio = domicilio + '-' + jQuery.trim(objDomicilio.numExteriorAlf);
			}
			if(jQuery.trim(objDomicilio.numInterior)!='' ){
				domicilio = domicilio +'Num Int ' + jQuery.trim(objDomicilio.numInterior)+ ', ';
			}
			if(jQuery.trim(objDomicilio.numInteriorAlf)!= ''){
				domicilio = domicilio + '-'+jQuery.trim(objDomicilio.numInteriorAlf) + ', ';
			}
			if(objDomicilio.asentamiento.clave!='-1'){
				domicilio = domicilio +  jQuery.trim(objDomicilio.asentamiento.nombre) + ', ';
			}
			if(objDomicilio.asentamiento.codigoPostal!=undefined){
				domicilio = domicilio  +jQuery.trim(objDomicilio.asentamiento.codigoPostal.codigoPostal) + ', ';
			}
			
			domicilio = domicilio +
			jQuery.trim(objDomicilio.asentamiento.localidad.nombre) + ', '+
			jQuery.trim(objDomicilio.asentamiento.localidad.municipio.nombre) + ', '+ 
			jQuery.trim(objDomicilio.asentamiento.localidad.municipio.entidadFederativa.nombre);
			
			if(objDomicilio.descripcion!=undefined){
			  domicilio = domicilio +' (' + objDomicilio.descripcion; + ')'; // SE DEBE GUARDAR EL DOMICILIO
			}
			 // SE DEBE GUARDAR EL DOMICILIO
			 if(ubicaDomicilio==1){
				 $("#txtDomicilio").prop("value", domicilio);
				 guardaDomicilio(objDomicilio, objDenuncia);
				 objDomicilio = null;
			 }else if(ubicaDomicilio==2){
				 $("#direccionBeneficiario").prop("value", domicilio);
				 guardaDomicilioBen(objDomicilio, objDenuncia);
				 objDomicilio = null;
			 }else if(ubicaDomicilio==3){
				 $("#direccionRepLegalDT").prop("value", domicilio);	
				 guardaDomicilioRep(objDomicilio, objDenuncia);
				 objDomicilio = null;
			 }else if(ubicaDomicilio==4){
				 $("#txtDomicilioTrabajoDP").prop("value", domicilio);	
				 guardaDomicilioCenTrab(objDomicilio, objDenuncia);
				 objDomicilio = null;
			 }
			 else if(ubicaDomicilio==5){
				 $("#txtDomFiscalPtrIdDP").prop("value", domicilio);
				 guardaDomicilioPat(objDomicilio, objDenuncia);
				 objDomicilio = null;
			 }
			 else if(ubicaDomicilio==6){
				 $("#txtDomFiscalPtrIdCmpDP").prop("value", domicilio);
				 guardaDomicilioPatAdicional(objDomicilio, objDenuncia);
				 objDomicilio = null;
			 }
			 // SE DEBE ACTUALIZAR LA DENUNCIA CON EL ID DOMICILIO GENERADO
		} catch(e) {}
	}
}
	oDgPatronesDenunciados = $("#dgAgregaPatrones").dialog({
	 	autoOpen: false,
	 	modal:true,
	 	resizable:true,
	 	height: 600,
	 	width: 1000,
	 	closeOnEscape: false,
	 	buttons: {
			"Guardar": function() { 
				llenaObjetoPatronComplemento();				
				$(this).dialog("close"); 
			}
	 	}
	 });
	
	dtPatronesDenunciados = $("#dtPatronesDenunciados").dataTable();

	
	llenaComboPeriodoPago();
	llenaComboSector();
	llenaComboSubdelegaciones();
	llenaComboTipoDocumento();
	
	formatoMoneda('#txtImpSalarioPercibidoIT');
	formatoMoneda('#txtImpVacacionesIT');
	formatoMoneda('#txtImpAguinaldoIT');
	formatoMoneda('#txtImpGratificcionIT');
	formatoMoneda('#md3ImporteImss');
	formatoMoneda('#md3ImporteReal');
	
	
	$("#cbxSelSectordDP").change(function(){
		llenaComboActividad();
	});
	
	
	
	
	 $(".tab_content").hide();
	 $("ul.tabs li:first").addClass("active").show();
	 $(".tab_content:first").show();

	 $("ul.tabs li").click(function(){
			$("ul.tabs li").removeClass("active");
			$(this).addClass("active");
			$(".tab_content").hide();

			var activeTab = $(this).find("a").attr("href");
			$(activeTab).fadeIn();
			return false;
		});

	
	$("#btnCargaPatCmplDP").hide();
	$("#tblPatronCmplDP").hide();
	$("#tblNss").hide();
	$("#tdRiesgo").hide();
	$("#txtFecFechaRiesgoTrabIT").hide();
	
		
	$('#txtDesNombreRLDT').prop('disabled','disabled');
	$('#txtDesPaternoRLDT').prop('disabled','disabled');
	$('#txtDesMaternoRLDT').prop('disabled','disabled');
	$('#txtCveCurpRLDT').prop('disabled','disabled');
	$('#direccionRepLegalDT').prop('disabled','disabled');
	$('#txtNumTelefonoRLDT').prop('disabled','disabled');
	$('#txtNumTelefonoMblRLDT').prop('disabled','disabled');
	$('#cmbCveTipodocumentoRLDT').prop('disabled','disabled');
	$('#btnAdjuntarDocRL').prop('disabled','disabled');
	$('#btnEliminarDocRL').prop('disabled','disabled');
	$('#txtRefDocRLDT').prop('disabled','disabled');
	$('#txtNumDocumentoRLDT').prop('disabled','disabled');	
	$('#txtDesNombreBDT').prop('disabled','disabled');
	$('#txtDesPaternoBDT').prop('disabled','disabled');
	$('#txtDesMaternoBDT').prop('disabled','disabled');
	$('#txtCveCurpBDT').prop('disabled','disabled');
	$('#direccionBeneficiario').prop('disabled','disabled');
	$('#txtNumTelefonoBDT').prop('disabled','disabled');
	$('#txtNumTelefonoMblBDT').prop('disabled','disabled');
	$('#cmbCveTipodocumentoBDT').prop('disabled','disabled');
	$('#buttonAdjuntarDatosTrabajador').prop('disabled','disabled');
	$('#buttonEliminarDatosTrabajador').prop('disabled','disabled');
	$('#buttonAdjuntarDatosB').prop('disabled','disabled');
	$('#buttonEliminarDatosB').prop('disabled','disabled');		
	$('#txtNumDocumentoBDT').prop('disabled','disabled');	
	$('#md1fechaInicio').prop('disabled','disabled');
    $('#md1fechaFin').prop('disabled','disabled');
    $('#md2fechaInicio').prop('disabled','disabled');
	$('#md2fechaFin').prop('disabled','disabled');
	$('#md3ImporteImss').prop('disabled','disabled');
	$('#md3ImporteReal').prop('disabled','disabled');
    $('#md4fechaInicio').prop('disabled','disabled');
    $('#txtDesEspecifiqueFPIT').prop('disabled','disabled');
    
	i = 0;
	

	
	
	
	$("#btnRegistrar").click(function(e){
		
		if($('#password').val()!=$('#confirm_pass').val()){			
			alert("El password es inv\u00e1lido, favor de verificar");
		}else if(validaCaptura.form()){
			var desPassword = $('input#password').val();
			var email = $('input#email').val();
			var captcha = $('input#j_captcha_response').val();
			var pregunta = $('select#pregunta').val();
			var respuesta = $('input#respuesta').val();
			
			var sUsuario = '{' +
			'"captcha": "'+captcha+'",'+
			'"desEmail": "'+email+'",'+
			'"cvePregunta": "'+pregunta+'",'+
			'"desRespuesta": "'+respuesta+'",'+
			'"desPassword" : "'+desPassword+'"}';
			var usuario = jQuery.parseJSON(sUsuario);
			 $.postJSON("registraUsuario.do", usuario, function(data) {
				 	if(data==3){
				 		alert('Su registro fue exitoso, en breve recibir\u00e1 la confirmaci\u00f3n en el correo electr\u00f3nico proporcionado.');
				 		$("#irLoginForm").submit(); 
				 	}else if(data==2){
				 		alert('El correo electr\u00f3nico capturado ya est\u00e1 registrado, no procede su registro.');				 	
				 	}else if(data==1){
				 		alert('Los caracteres de la imagen no coinciden con los capturados.');
				 		nuevaImagen();
				 	//	location.reload(); 
				 		// $('input#imgCaptcha').html('<img
						// src="<%=request.getContextPath()%>/captchaController/captcha.htm"
						// id="imgCaptcha" name="imgCaptcha"/>');
				 	}
				 });					
		   }
	});
// $("#btnEnviar").click(function(e){
// var folio = $('#txtFolioDenunciaDT').val();
// var sNumFolioDenuncia = '{' +'"folioDenuncia": "'+ folio + '"}';
// agregaReglasDenunciaForm();
// var resultado =validaGuardar.form();
// if(resultado){
// quitaReglasDenunciaForm();
// var promocion = jQuery.parseJSON(sNumFolioDenuncia);
// jQuery.ajax({
// async: false,
// type: 'POST',
// url: getAppContextParaJS() + '/denuncia/obtenerFechaServidor.do',
// data: sNumFolioDenuncia, // or JSON.stringify ({name: 'jonas'}),
// success: function(data) {
//	    		    	    	
// if(folio == ''){
// var desObservaciones = $('#txtObs').val();
// var desAclaracion = $('#txtAclDP').val();
// var cveTipodenunciante= $('#cmbCveTipoDenuncianteDT').val();
// var cveFoliodenuncia = $('#hdIdDenuncia').val();
//	    	    		
// var scveFoliodenuncia = '"cveFoliodenuncia":'+'"' + cveFoliodenuncia + '"';
// var scveTipodenuncianteD = '"cveTipodenunciante":'+'"' + cveTipodenunciante +
// '"';
// var sdesObservaciones = '"desObservaciones":'+'"' +
// jQuery.trim(desObservaciones) + '"';
// var sdesAclaracion = '"desAclaracion":'+'"' + jQuery.trim(desAclaracion) +
// '"';
//	    	    		
//	    	    			    	    		
// var sDenuncia = '{'+ scveFoliodenuncia +','+ scveTipodenuncianteD +','+
// sdesObservaciones +','+ sdesAclaracion +'}';
//
// var jDenuncia = jQuery.parseJSON(sDenuncia);
//
// $.postJSON( getAppContextParaJS() + "/denuncia/generaDenuncia.do", jDenuncia,
// function(denuncia) {
// if(denuncia != null){
// $('#hdIdDenuncia').prop('value', denuncia.cveFoliodenuncia);
// $('#txtFolioDenunciaDT').prop('value', denuncia.numFoliodenuncia);
// folio = $('#txtFolioDenunciaDT').val();
// }
// }).error(function(denuncia){
// //alert("error generando denuncia" + denuncia);
// }).complete(function(){
// //alert('El registro se ha guardado con exito');
// folio = $('#txtFolioDenunciaDT').val();
// if(folio != '' && folio != null){
// var cveFoliodenuncia = $('#hdIdDenuncia').val();
// //guardarDatosPersonas
// llenaObjetoDatosTrabajador();
// llenaBeneficiario();
// llenaRepresentanteLegal();
// //patron
// llenaObjetosPatron();
// //trabajo
// llenaCentroTrabajo();
// llenaFormaPago();
// llenaMotivosDenuncia();
//		    	    	    		
// $.postJSON(getAppContextParaJS() + "/denuncia/enviar/"+
// cveFoliodenuncia+".do",null, function(denuncia) {
// //alert("regresa de enviar");
// }).error(function(denuncia){
// //alert("error enviando denuncia" + denuncia);
// }).complete(function(){
// $('#frmEnviar').attr('action', getAppContextParaJS() +
// "/denuncia/enviaRatificacion.do");
// $('#frmEnviar').submit();
// });
// }
//		    						    					
// });
// }
//	    	    	
// if(folio != ''){
// var cveFoliodenuncia = $('#hdIdDenuncia').val();
// llenaObjetoDatosTrabajador();
// llenaBeneficiario();
// llenaRepresentanteLegal();
// llenaObjetosPatron();
// llenaCentroTrabajo();
// llenaFormaPago();
// llenaMotivosDenuncia();
//	    	    		
// $.postJSON(getAppContextParaJS() + "/denuncia/enviar/"+
// cveFoliodenuncia+".do",null, function(denuncia) {
// //alert("regresa de enviar");
// }).error(function(denuncia){
// //alert("error enviando denuncia" + denuncia);
// }).complete(function(){
// $('#frmEnviar').attr('action', getAppContextParaJS() +
// "/denuncia/enviaRatificacion.do");
// $('#frmEnviar').submit();
// });
// }
//
// },
// contentType: "application/json"
// }).error(function(data){
// validarSesionExpirada(data);
// }).complete(function(){
// //Instrucciones para el 'complete'
// });
// }//validaForm
// });
    	
  
	 
	 
	 
    var validaRecupera = $("#formRecupera").validate({
	  	  rules: {
	  		rj_captcha_response: {
	  	      required: true,
	  	      minlength: 3,
	  	      alphanumeric: true
	  	   },
	  	 desMail:{
		   	 required: true
		  	 }
	  	 
	  	  }	 
		 
	  });
		
    var validaCaptura = $("#formRegistro").validate({
	  	  rules: {
	  	   j_captcha_response: {
	  	      required: true,
	  	      minlength: 3,
	  	      alphanumeric: true
	  	   },
	  	   email:{
		   	 required: true
		  	 },
		  	confirmEmail:{
	  		   	 required: true,
	  		   	 equalTo: "#email"
	  	   },
	  	 password: {
	  		required: true,
	  		alphanumeric: true,
	  		maxlength : 8,
	  		minlength : 8
	  	   },
	  	 confirm_pass: {
		  		required: true,
		  		alphanumeric: true,
		  		maxlength : 8,
		  		minlength : 8
		  	   },
	  	   pregunta: {
	  		 required: true,
	  		alphanumeric: true
	  	   },
	  	   respuesta: {
	  		 required: true,
	  		alphanumeric: true
	  	   }
	  	  }	 
		 
	  });


   		$("#btnRecuperar").click(function(e){
		    	if(validaRecupera.form()){
		    		var email = $('input#desMail').val();
					var captcha = $('input#rj_captcha_response').val();
					var respuesta = $('input#respuesta').val();
					
					var sUsuario = '{' +
					'"captcha" : "'+captcha+'",'+
					'"desRespuesta" : "'+respuesta+'",'+
					'"desEmail": "'+email+'"}';
					
					var usuario = jQuery.parseJSON(sUsuario);
					
					$.postJSON("validaCaptcha.do", usuario, function(data) {				
						if(data==2){
							$.postJSON("verificaCorreoRec.do", usuario, function(ver) {
								if(ver==1){
									$("#irRecuperaConfForm").submit(); 
								}else if(ver == 2){
									actucap();
									alert('El correo electr\u00f3nico capturado no se encuentra registrado');
								}
							
							}).error(function(data){ 
								
							}).complete(function(){
								// Instrucciones para el 'complete'
							}); 	
					}else{
							actucap();
							alert('Los caracteres de la imagen no coinciden con los capturados');
						}	
					}).error(function(data){ 
						
					}).complete(function(){
						// Instrucciones para el 'complete'
					}); 	
		    	}
	});
    
			
			
			
	
		
    
// $("#btnGuardar").click(function(e){
// var folio = $('#txtFolioDenunciaDT').val();
// var sNumFolioDenuncia = '{' +'"folioDenuncia": "'+ folio + '"}';
// if(validaGuardar.form() ){
// var promocion = jQuery.parseJSON(sNumFolioDenuncia);
// jQuery.ajax({
// async: false,
// type: 'POST',
// url: getAppContextParaJS() + '/denuncia/obtenerFechaServidor.do',
// data: sNumFolioDenuncia, // or JSON.stringify ({name: 'jonas'}),
// success: function(data) {
// if(folio == ''){
// var desObservaciones = $('#txtObs').val();
// var desAclaracion = $('#txtAclDP').val();
// var cveTipodenunciante= $('#cmbCveTipoDenuncianteDT').val();
// var cveFoliodenuncia = $('#hdIdDenuncia').val();
//	    	    		
// var scveFoliodenuncia = '"cveFoliodenuncia":'+'"' + cveFoliodenuncia + '"';
// var scveTipodenuncianteD = '"cveTipodenunciante":'+'"' + cveTipodenunciante +
// '"';
// var sdesObservaciones = '"desObservaciones":'+'"' +
// jQuery.trim(desObservaciones) + '"';
// var sdesAclaracion = '"desAclaracion":'+'"' + jQuery.trim(desAclaracion) +
// '"';
//	    	    		
//	    	    			    	    		
// var sDenuncia = '{'+ scveFoliodenuncia +','+ scveTipodenuncianteD +','+
// sdesObservaciones +','+ sdesAclaracion +'}';
//
//
// var jDenuncia = jQuery.parseJSON(sDenuncia);
//	    	    		
//	    	    		
// $.postJSON( getAppContextParaJS() + "/denuncia/generaDenuncia.do", jDenuncia,
// function(denuncia) {
//
// if(denuncia != null){
// $('#hdIdDenuncia').prop('value', denuncia.cveFoliodenuncia);
// $('#txtFolioDenunciaDT').prop('value', denuncia.numFoliodenuncia);
// folio = $('#txtFolioDenunciaDT').val();
// }
// }).error(function(denuncia){
// //alert("error generando denuncia" + denuncia);
// }).complete(function(){
//
// folio = $('#txtFolioDenunciaDT').val();
// if(folio != '' && folio != null){
// //guardarDatosPersonas
// llenaObjetoDatosTrabajador();
// llenaBeneficiario();
// llenaRepresentanteLegal();
// //patron
// llenaObjetosPatron();
// //trabajo
// llenaCentroTrabajo();
// llenaFormaPago();
// llenaMotivosDenuncia();
// }
// });
//	    	    		
// }
// if(folio != ''){
// llenaObjetoDatosTrabajador();
// llenaBeneficiario();
// llenaRepresentanteLegal();
// llenaObjetosPatron();
// llenaCentroTrabajo();
// llenaFormaPago();
// llenaMotivosDenuncia();
//	    	    		
// }
// },
// contentType: "application/json"
// }).error(function(data){
// validarSesionExpirada(data);
// }).complete(function(){
// //Instrucciones para el 'complete'
// alert("Los datos fueron guardados exitosamente.");
// });
// }else{
// alert("Formulario Invalido");
// }
// });
    
    function llenaMotivosDenuncia(){
    	
    	var cveFoliodenuncia = $('#hdIdDenuncia').val();
    	
    	if($("#md1").attr("checked")){

			var cveMotivodenuncia = '1';
			var valorInicial = formateaFecha($('#md1fechaInicio').val());
			var valorFinal = formateaFecha($('#md1fechaFin').val());
			
			var scveFoliodenuncia = '"cveFoliodenuncia":'+'"' + cveFoliodenuncia + '"';
			var scveMotivodenuncia = '"cveMotivodenuncia":'+'"' + cveMotivodenuncia + '"';
			var svalorInicial = '"valorInicial":'+'"' + valorInicial + '"';
			var svalorFinal = '"valorFinal":'+'"' + valorFinal + '"';
				
			var sMotivoDenunciaPk = '{'+ scveFoliodenuncia +','+ scveMotivodenuncia +','+ svalorInicial  +','+ svalorFinal+'}';
			var jMotivoDenunciaPK =  jQuery.parseJSON(sMotivoDenunciaPk);
			
			if(valorInicial == '' && valorFinal == '' ){
				alert("Introduzca los valores del motivo de denuncia");
				return;
			}

			
    		$.postJSON(getAppContextParaJS() +"/denuncia/guardaMotivo/"+ cveFoliodenuncia +".do", jMotivoDenunciaPK, function(motivoDenuncia) {				  
				  if(motivoDenuncia != null){	    						  
					  // $('#txtFolioDenunciaDT').prop('value',
						// motivoDenuncia.dltDenuncia.numFoliodenuncia);
				  }
			}).error(function(denuncia){ 
				// alert("error generando denuncia" + denuncia);
			}).complete(function(){
				// alert('El registro se ha guardado con exito');
				folio = $('#txtFolioDenunciaDT').val();	    						    						    				
			});	    						
		}
		if($("#md2").attr("checked")){
			var cveMotivodenuncia = '2';
			var valorInicial= $('#md2fechaInicio').val();
			var valorFinal =	 $('#md2fechaFin').val();	

			var scveFoliodenuncia = '"cveFoliodenuncia":'+'"' + cveFoliodenuncia + '"';
			var scveMotivodenuncia = '"cveMotivodenuncia":'+'"' + cveMotivodenuncia + '"';									
			var svalorInicial = '"valorInicial":'+'"' + valorInicial + '"';
			var svalorFinal = '"valorFinal":'+'"' + valorFinal + '"';
			
			var sMotivoDenunciaPk = '{'+ scveFoliodenuncia +','+ scveMotivodenuncia +'}';
			var jMotivoDenunciaPK =  jQuery.parseJSON(sMotivoDenunciaPk);
			
			if(valorInicial == '' && valorFinal == '' ){
				alert("Introduzca los valores del motivo de denuncia");
				return;
			}
			
			var sMotivoDenunciaPk = '{'+ scveFoliodenuncia +','+ scveMotivodenuncia +','+ svalorInicial  +','+ svalorFinal+'}';
			var jMotivoDenunciaPK =  jQuery.parseJSON(sMotivoDenunciaPk);

			if(valorInicial == '' && valorFinal == '' ){
				alert("Introduzca los valores del motivo de denuncia");
				return;
			}
			// alert(sMotivoDenunciaPk);
			
			$.postJSON(getAppContextParaJS() +"/denuncia/guardaMotivo/"+ cveFoliodenuncia +".do", jMotivoDenunciaPK, function(motivoDenuncia) {				  
				  if(motivoDenuncia != null){	    						  
					  // $('#txtFolioDenunciaDT').prop('value',
						// motivoDenuncia.dltDenuncia.numFoliodenuncia);
				  }
			}).error(function(motivoDenuncia){ 
				// alert("error generando denuncia" + denuncia);
			}).complete(function(){
				// alert('El registro se ha guardado con exito');
				folio = $('#txtFolioDenunciaDT').val();	    						    						    				
			});	
			
		} 
		if($("#md3").attr("checked")){
			var cveMotivodenuncia = '3';								
			var valorInicial= $('#md3ImporteReal').val();
			var valorFinal = $('#md3ImporteImss').val();
			
			var scveFoliodenuncia = '"cveFoliodenuncia":'+'"' + cveFoliodenuncia + '"';
			var scveMotivodenuncia = '"cveMotivodenuncia":'+'"' + cveMotivodenuncia + '"';
			var svalorInicial = '"valorInicial":'+'"' + valorInicial + '"';
			var svalorFinal = '"valorFinal":'+'"' + valorFinal + '"';

			
			if(valorInicial == '' && valorFinal == '' ){
				alert("Introduzca los valores del motivo de denuncia");
				return;
			}
			
			var sMotivoDenunciaPk = '{'+ scveFoliodenuncia +','+ scveMotivodenuncia +','+ svalorInicial  +','+ svalorFinal+'}';
			var jMotivoDenunciaPK =  jQuery.parseJSON(sMotivoDenunciaPk);
			// alert(sMotivoDenunciaPk);
			
			$.postJSON(getAppContextParaJS() +"/denuncia/guardaMotivo/"+ cveFoliodenuncia +".do", jMotivoDenunciaPK, function(motivoDenuncia) {					  
				  if(motivoDenuncia != null){	    						  
					  // $('#txtFolioDenunciaDT').prop('value',
						// motivoDenuncia.dltDenuncia.numFoliodenuncia);
				  }
			}).error(function(motivoDenuncia){ 
				// alert("error generando denuncia" + denuncia);
			}).complete(function(){
				// alert('El registro se ha guardado con exito');
				folio = $('#txtFolioDenunciaDT').val();	    						    						    				
			});	
		}
		if($("#md4").attr("checked")){			
			var cveMotivodenuncia = '4';
			var valorInicial = $('#md4fechaInicio').val();
			var valorFinal  = $('#md4fechaFin').val();
			
			var scveFoliodenuncia = '"cveFoliodenuncia":'+'"' + cveFoliodenuncia + '"';
			var scveMotivodenuncia = '"cveMotivodenuncia":'+'"' + cveMotivodenuncia + '"';
			var svalorInicial = '"valorInicial":'+'"' + valorInicial + '"';
			var svalorFinal = '"valorFinal":'+'"' + valorFinal + '"';

			
			if(valorInicial == '' && valorFinal == '' ){
				alert("Introduzca los valores del motivo de denuncia");
				return;
			}
			
			var sMotivoDenunciaPk = '{'+ scveFoliodenuncia +','+ scveMotivodenuncia +','+ svalorInicial  +','+ svalorFinal+'}';
			var jMotivoDenunciaPK =  jQuery.parseJSON(sMotivoDenunciaPk);
			// alert(sMotivoDenunciaPk);
			
			$.postJSON(getAppContextParaJS() +"/denuncia/guardaMotivo/"+ cveFoliodenuncia +".do", jMotivoDenunciaPK, function(motivoDenuncia) {					  
				  if(motivoDenuncia != null){	    						  
					  // $('#txtFolioDenunciaDT').prop('value',
						// motivoDenuncia.dltDenuncia.numFoliodenuncia);
				  }
			}).error(function(motivoDenuncia){ 
				// alert("error generando denuncia" + denuncia);
			}).complete(function(){
				// alert('El registro se ha guardado con exito');
				folio = $('#txtFolioDenunciaDT').val();	    						    						    				
			});	
		}
			
    }
    
    
    function llenaObjetosPatron(){
    	var idPatronPrincipal = $('#hdIdPatronPrincipal').val();    	
    	// alert(idPatronPrincipal);
    	var desNomrazonsocial = $('#txtDesNomrazonsocialDP').val();	
		var domicilioTrabajo = $('#txtDomicilioTrabajoDP').val();	
		var desNomreplegal = $('#txtDesNomreplegalDP').val();	
		var selGiroActividad = $('#cbxSelGiroActividadDP').val();
		var selSector = $('#cbxSelSectorDP').val();
		var desRfc = $('#txtRfcPatronDP').val();
		var cveRegpat = $('#txtCveRegpatDP').val();
		var numTrabajadores = $('#txtNumTrabajadoresDP').val();
		var domicilioId = '';
		if(objDenuncia!=null){
			domicilioId =objDenuncia.datosPatronVO.idDomicilio;
		}
		
		var numTelefono = $('#txtNumTelefonoPatronDP').val();
		var indPatronprincipal = '1';
		var indDenunciadoAntes =  $("input:radio[name=rdDenunciadoAntesDP]").val();
		
		
		var sidPatronPrincipal = '"cveDatospatron":'+'"'+ idPatronPrincipal +'"';
		var sDenunciadoAntes= '"indPatrondenunciado":'+'"'+indDenunciadoAntes+'"';
		var sDesNomrazonsocial= '"desNomrazonsocial":'+'"'+desNomrazonsocial+'"';
		var sDomicilioTrabajo= '"domicilioTrabajo":'+'"'+domicilioTrabajo+'"';
		var sDesNomreplegal= '"desNomreplegal":'+'"'+desNomreplegal+'"';
		var sSelGiroActividad= '"selGiroActividad":'+'"'+selGiroActividad+'"';
		var sSelSector= '"selSector":'+'"'+selSector+'"';
		var sDesRfc= '"desRfc":'+'"'+desRfc+'"';
		var sCveRegpat= '"cveRegpat":'+'"'+cveRegpat+'"';
		var sNumTrabajadores= '"numTrabajadores":'+'"'+numTrabajadores+'"';
		var sDomicilioId= '"domicilioId":'+'"'+domicilioId+'"';
		var sNumTelefono= '"numTelefono":'+'"'+numTelefono+'"';
		
		var sindPatronprincipal = '"idPatronprincipal":'+'"'+indPatronprincipal+'"';
		
		
		var strPatron = '{'+ sidPatronPrincipal +','+ sDenunciadoAntes +','+ sDesNomrazonsocial +','+ sDomicilioTrabajo +','+ sDesNomreplegal  
								+','+ sSelGiroActividad  +','+ sSelSector +','+ sDesRfc +','+ sCveRegpat +','+ sNumTrabajadores 
								+','+  sDomicilioId  +','+ sNumTelefono  +',' + sindPatronprincipal +'}';

		var jPatron = jQuery.parseJSON(strPatron);
		
		var idDenuncia = $('#hdIdDenuncia').val();
		
		if(idDenuncia == ''){
			alert("Guarde la denuncia antes de agregar patrones");
		}else{

			$.postJSON(getAppContextParaJS() +"/denuncia/agregaPatron/"+ idDenuncia +".do", jPatron, function(patron) {
				if(patron != null){	    						  
					  $('#hdIdPatronPrincipal').prop('value', patron.cveDatospatron);		
					  // alert($('#hdIdPatronPrincipal').val());
				  }
			}).error(function(data){ 
				// alert("error" + data);
			}).complete(function(patron){
				// $('#hdIdPatronPrincipal').prop('value',
				// patron.cveDatospatron);
				// alert($('#hdIdPatronPrincipal').val());
			});
		}	    
    }
    

	function llenaObjetoDatosTrabajador(){
    		
		    var idPersona = $('#hdIdTrabajador').val();
		    var cveTipoDenunciante = '1';		    
    		var desNombre = $('#txtDesNombreDT').val();
    		var desPaterno = $('#txtDesPaternoDT').val();
    		var desMaterno = $('#txtDesMaternoDT').val();
    		var cveCURP =  $('#txtCveCurpDT').val();

    		var cveRFC =  $('#txtCveRfcDT').val();
    		var cveNSS =  $('#txtCveNssDT').val();
    		var desEmail =  $('#txtDesEmailDT').val();
    		var numTelefono =  $('#txtNumTelefonoDT').val();
    		var numCelular =  $('#numCelular').val();
    		var domicilioID =  $('#direccionTrabajador').val();
    		var cveTipoDocumento =  $('#cmbCveTipodocumentoDT').val();
    		var refDocumento =  $('#txtRefDocDT').val();
    		var numDocumento =  $('#numDocumento').val();
    		
    		var sIdPersona = '"cvePersona":'+'"'+ idPersona +'"';
    		var scveTipoDenunciante= '"cveTipodenunciante":'+'"'+cveTipoDenunciante+'"';
    		var sdesNombre= '"desNombre":'+'"'+desNombre+'"';    		
    		var sdesPaterno= '"desPaterno":'+'"'+desPaterno+'"';
    		var sdesMaterno= '"desMaterno":'+'"'+desMaterno+'"';
    		var scveCURP= '"cveCurp":'+'"'+cveCURP+'"';
    		var scveRfc= '"cveRfc":'+'"'+cveRFC+'"';
    		var scveNss= '"cveNss":'+'"'+cveNSS+'"';
    		var sdesEmail= '"desEmail":'+'"'+desEmail+'"';
    		var snumTelefono= '"numTelefono":'+'"'+numTelefono+'"';
    		var snumCelular= '"numCelular":'+'"'+numCelular+'"';
    		var scveTipoDocumento= '"cveTipodocumento":'+'"'+cveTipoDocumento+'"';
    		var srefDocumento= '"refDocumento":'+'"'+refDocumento+'"';
    		var snumDocumento= '"numDocumento":'+'"'+numDocumento+'"';
    		
    		var idDenuncia = $('#hdIdDenuncia').val();
    		
    		var sPersona = '{' + sIdPersona +','+ scveTipoDenunciante +','+ sdesNombre +','+ sdesPaterno +','+ sdesMaterno  
			+','+ scveCURP  +','+ scveRfc +','+ scveNss +','+ sdesEmail +','+ snumTelefono 
			+','+  snumCelular  +','+ scveTipoDocumento  +',' + srefDocumento +',' + snumDocumento +'}';

    		
    		var jpersona = jQuery.parseJSON(sPersona);
    		
    		
    		$.postJSON( getAppContextParaJS() +"/denuncia/guardaPersona/"+ idDenuncia +".do", jpersona, function(persona) {
				  // alert(sIdPersona);
				  if(persona != null){	    						  
					  $('#hdIdTrabajador').prop('value', persona.cvePersona);					
				  }
				  
			}).error(function(data){ 
				// alert("error" + data);
			}).complete(function(){
				// alert('El registro se ha guardado con exito');
				// llenar los campos de la persona con la data q se regresa
			});
    		    		
    }
    
	
	function llenaBeneficiario(){
		if($('#cmbCveTipoDenuncianteDT').val() == '2'){
 
			 var idPersona = $('#hdIdBeneficiario').val();
			 var cveTipoDenunciante = '2';
	    	 var desNombre = $('#txtDesNombreBDT').val();
	    	 var desPaterno = $('#txtDesPaternoBDT').val();
	         var desMaterno = $('#txtDesMaternoBDT').val();
	    	// var cveCURP = $('#txtCveCurpBDT').val();
	    	 var numTelefono =  $('#txtNumTelefonoBDT').val();
	    	 var numCelular =  $('#txtNumTelefonoMblBDT').val();
	    	 var domicilioID =  $('#direccionBeneficiario').val();
	    	 var cveTipoDocumento =  $('#cmbCveTipodocumentoBDT').val();
	    	 var refDocumento =  $('#txtRefDocBDT').val();
	    	 var numDocumento =  $('#txtNumDocumentoBDT').val();
	    	 
	    	 var idDenuncia = $('#hdIdDenuncia').val();
	    	 
	    	 var sIdPersona = '"cvePersona":'+'"'+ idPersona +'"';
	    	 var scveTipoDenunciante= '"cveTipodenunciante":'+'"'+cveTipoDenunciante+'"';
	    	 var sdesNombre= '"desNombre":'+'"'+desNombre+'"';    			    		
	    	 var sdesPaterno= '"desPaterno":'+'"'+desPaterno+'"';
	    	 var sdesMaterno= '"desMaterno":'+'"'+desMaterno+'"';
	    	 // var scveCURP= '"cveCURP":'+'"'+cveCURP+'"';
	    	 var snumTelefono= '"numTelefono":'+'"'+numTelefono+'"';
	    	 var snumCelular= '"numCelular":'+'"'+numCelular+'"';
	    	 var scveTipoDocumento= '"cveTipodocumento":'+'"'+cveTipoDocumento+'"';
	    	 var srefDocumento= '"refDocumento":'+'"'+refDocumento+'"';
	    	 var snumDocumento= '"numDocumento":'+'"'+numDocumento+'"';
	    	 
	    	 var sPersonaB = '{' + sIdPersona +','+ scveTipoDenunciante +','+ sdesNombre +','+ sdesPaterno +','+ sdesMaterno  
				+',' + snumTelefono +','+  snumCelular  +','+ scveTipoDocumento  +',' + srefDocumento +',' + snumDocumento +'}';
	    		

	    		var jpersonaB = jQuery.parseJSON(sPersonaB);
	    		
	    		
	    	 $.postJSON( getAppContextParaJS() +"/denuncia/guardaPersona/"+ idDenuncia +".do", jpersonaB, function(persona) {
	    		 if(persona != null){	    						  
					  $('#hdIdBeneficiario').prop('value', persona.cvePersona);					
				  }				  			
				}).error(function(data){ 
					// alert("error" + data);
				}).complete(function(){
					// alert('El registro se ha guardado con exito');
					// llenar los campos de la persona con la data q se regresa
				});
		}				
	}
	
	function llenaRepresentanteLegal(){
		if($('#cmbCveTipoDenuncianteDT').val() == '3'){
			
			 var idPersona = $('#hdIdRepLegal').val();
			 var cveTipoDenunciante = '3';
	    	 var desNombre = $('#txtDesNombreRLDT').val();
	    	 var desPaterno = $('#txtDesPaternoRLDT').val();
	         var desMaterno = $('#txtDesMaternoRLDT').val();
	    	 // var cveCURP = $('#txtCveCurpRLDT').val();
	    	 var numTelefono =  $('#txtNumTelefonoRLDT').val();
	    	 var numCelular =  $('#txtNumTelefonoMblRLDT').val();
	    	 var domicilioID =  $('#direccionRepLegalDT').val();
	    	 var cveTipoDocumento =  $('#cmbCveTipodocumentoRLDT').val();
	    	 var refDocumento =  $('#txtRefDocRLDT').val();
	    	 var numDocumento =  $('#txtNumDocumentoRLDT').val();
	    	 
	    	 var idDenuncia = $('#hdIdDenuncia').val();
	    	 
	    	 var sIdPersona = '"cvePersona":'+'"'+ idPersona +'"';
	    	 var scveTipoDenunciante= '"cveTipodenunciante":'+'"'+cveTipoDenunciante+'"';
	    	 var sdesNombre= '"desNombre":'+'"'+desNombre+'"';    			    		
	    	 var sdesPaterno= '"desPaterno":'+'"'+desPaterno+'"';
	    	 var sdesMaterno= '"desMaterno":'+'"'+desMaterno+'"';
	    	 // var scveCURP= '"cveCURP":'+'"'+cveCURP+'"';
	    	 var snumTelefono= '"numTelefono":'+'"'+numTelefono+'"';
	    	 var snumCelular= '"numCelular":'+'"'+numCelular+'"';
	    	 var scveTipoDocumento= '"cveTipodocumento":'+'"'+cveTipoDocumento+'"';
	    	 var srefDocumento= '"refDocumento":'+'"'+refDocumento+'"';
	    	 var snumDocumento= '"numDocumento":'+'"'+numDocumento+'"';
	    	 
	    	 var sPersonaRL = '{' + sIdPersona +',' + scveTipoDenunciante +','+ sdesNombre +','+ sdesPaterno +','+ sdesMaterno  
				+',' + snumTelefono +','+  snumCelular  +','+ scveTipoDocumento  +',' + srefDocumento +',' + snumDocumento +'}';

	    		
	    	var jpersonaRL = jQuery.parseJSON(sPersonaRL);
	    		
	    		
	    	 $.postJSON( getAppContextParaJS() +"/denuncia/guardaPersona/"+ idDenuncia +".do", jpersonaRL, function(persona) {
	    		 if(persona != null){	    						  
					  $('#hdIdRepLegal').prop('value', persona.cvePersona);					
				  }		  			
				}).error(function(data){ 
					// alert("error" + data);
				}).complete(function(){
					// alert('El registro se ha guardado con exito');
					// llenar los campos de la persona con la data q se regresa
				});
		}		
	}
	

	function llenaCentroTrabajo(){
		var idCentroTrabajo = $('#hdIdDatosTrabajo').val();
		
		var fechaInicio = $('#txtFechaInicioTrabajoIT').val();
		var fechaFin = $('#txtFechaFinTrabajoIT').val();	
		var desLaboresdesemp = $('#txtDesLaboresDesempIT').val();	
		var desNumcontrato = $("[name='rdContratoIT']:checked").val();
		var desNomjefeinmediato = $('#txtDesNomJefeInmediatoIT').val();
		var desHorariolabores = $('#txtDesHorariolaboresIT').val();
		var impSalariopercibido = $('#txtImpSalarioPercibidoIT').val();
		var impVacaciones = $('#txtImpVacacionesIT').asNumber();
		var numDiasvacaciones = $('#txtNumDiasVacacionesIT').val();
		var impAguinaldo = $('#txtImpAguinaldoIT').asNumber();
		var diasAguinaldo = $('#txtDiasAguinaldoIT').val();
		var gratificacion = $('#txtImpGratificcionIT').asNumber();	
		var desBaseComisionOtros = $('#txtDesBaseComisionOtrosIT').val();
		//var baseOtorgamiento= $('#txtBaseOtorgamientoIT').val();		
		var riesgoTrabajo =  $("input:radio[name=rdRiesgo]").val();
		var fecFechariesgotrab = $('#txtFecFechaRiesgoTrabIT').val();
		var desObservaciones = $('#desObservacionesIT').val();
		var domicilioId = '';
		if(objDenuncia!=null){
			domicilioId =objDenuncia.datosPatronVO.idDomicilio;
		}
		var sidCentroTrabajo = '"cveInfotrabajo":'+'"'+idCentroTrabajo+'"';
		var sFechaInicio= '"fecFechaInicio":'+'"'+fechaInicio+'"';
		var sFechaFin= '"fecFechaFin":'+'"'+fechaFin+'"';
		var sDesLaboresdesemp= '"desLaboresdesemp":'+'"'+desLaboresdesemp+'"';
		var sDesNumcontrato= '"desNumcontrato":'+'"'+desNumcontrato+'"';
		var sDesNomjefeinmediato= '"desNomjefeinmediato":'+'"'+desNomjefeinmediato+'"';
		var sDesHorariolabores= '"desHorariolabores":'+'"'+desHorariolabores+'"';
		var sImpSalariopercibido= '"impSalariopercibido":'+'"'+impSalariopercibido+'"';
		var sImpVacaciones= '"impVacaciones":'+'"'+impVacaciones+'"';
		var sNumDiasvacaciones= '"numDiasvacaciones":'+'"'+numDiasvacaciones+'"';
		var sImpAguinaldo= '"impAguinaldo":'+'"'+impAguinaldo+'"';
		var sDiasAguinaldo= '"diasAguinaldo":'+'"'+impAguinaldo+'"';
		var sGratificacion= '"gratificacion":'+'"'+gratificacion+'"';
		var sDesBaseComisionOtros= '"desBaseComisionOtros":'+'"'+desBaseComisionOtros+'"';
		var sBaseOtorgamiento= '"desBaseOtorgamiento":'+'"'+baseOtorgamiento+'"';
		var sRiesgoTrabajo= '"riesgoTrabajo":'+'"'+riesgoTrabajo+'"';
		var sFecFechariesgotrab= '"fecFechariesgotrab":'+'"'+fecFechariesgotrab+'"';
		var sDesObservaciones= '"desObservaciones":'+'"'+desObservaciones+'"';
		var sIdDomicilio= '"idDomicilio":'+'"'+domicilioId+'"';
		
		var idDenuncia = $('#hdIdDenuncia').val(); 
		
		var strTrabajo = '{' + sidCentroTrabajo  + ','+ sDesLaboresdesemp +','+ sDesNumcontrato +','+ sDesNomjefeinmediato +','+ sDesHorariolabores  
		                   +','+ sImpSalariopercibido  +','+ sImpVacaciones +','+ sNumDiasvacaciones +','+ sImpAguinaldo +','+ sDesBaseComisionOtros 
		                   +','+  sFecFechariesgotrab  +','+ sDesObservaciones  +','+  sFechaInicio +','+  sFechaFin +','+  sRiesgoTrabajo +','+ sBaseOtorgamiento +','+ sIdDomicilio + '}';
		
		var trabajo = jQuery.parseJSON(strTrabajo);
		
		$.postJSON(getAppContextParaJS() + "/denuncia/datosTrabajoMain/guardaCentroTrabajo/" + idDenuncia +".do", trabajo, function(data) {
			  // alert('se guardo');
			  if(data != null){	    						  
				  $('#hdIdDatosTrabajo').prop('value', data.cveInfotrabajo);					
			  }				
		}).error(function(data){ 
			// alert("error" + data);
		}).complete(function(){
			// alert('El registro se ha guardado con exito');
		});	
	}
	
	function llenaFormaPago(){
		
	
		var periodoPago = $('#sltCvePeriodoPagoIT').val();				//
		var desEspecifiquePP = $('#txtDesEspecifiquePPIT').val();
		
		var comprobantePago = $('#sltCveComprobantePagoIT').val();			//
		var desEspecifiqueCP = $('#txtDesEspecifiqueCPIT').val();
		checkBoxSeleccionados(); // asignado al arreglo checkboxValues
		var desEspecifiqueFP = $('#txtDesEspecifiqueFPIT').val();
		
				
		var sPeriodoPago= '"periodoPago":'+'"'+periodoPago+'"';
		var sDesEspecifiquePP= '"desEspecifiquePP":'+'"'+desEspecifiquePP+'"';
		var sComprobantePago= '"comprobantePago":'+'"'+comprobantePago+'"';
		var sDesEspecifiqueCP= '"desEspecifiqueCP":'+'"'+desEspecifiqueCP+'"';
		var sFormaPago= '"formaPago":'+'['+checkboxValues+']';
		var sDesEspecifiqueFP= '"desEspecifiqueFP":'+'"'+desEspecifiqueFP+'"';
		
		
		var idDenuncia = $('#hdIdDenuncia').val(); 
		
		var strTrabajo = '{'+ sPeriodoPago+','+ sComprobantePago+','+ sFormaPago 
		                    +','+ sDesEspecifiquePP+','+ sDesEspecifiqueCP+','+ sDesEspecifiqueFP + '}';
		
		var trabajo = jQuery.parseJSON(strTrabajo);
		
		$.postJSON(getAppContextParaJS() + "/denuncia/datosTrabajoMain/guardaFormaPago/" + idDenuncia +".do", trabajo, function(data) {
			  // alert('se guardo');
		}).error(function(data){ 
			// alert("error" + data);
		}).complete(function(){
			// alert('El registro se ha guardado con exito');
		});	
		
	}
	
	
	$.postJSON( getAppContextParaJS() +"/denuncia/obtenerFechaServidor.do", null,null).error(function(data){
			// alert("error sacando fecha servidor");
		}).complete(function(data){
				$('#hdFechaServidor').attr('value',data.responseText);
				$('#fechaLabelID').html(data.responseText );					
				
				//$("#md1fechaInicio,#md1fechaFin, #md2fechaInicio,#md2fechaFin,#md4fechaInicio,#txtFechaInicioTrabajoIT,#txtFechaFinTrabajoIT,#txtFecFechaRiesgoTrabIT,#txtFechaNacimientoDT").datepicker( { dateFormat: 'dd/mm/yy',
					$("#md1fechaInicio,#md1fechaFin, #md2fechaInicio,#md2fechaFin,#md4fechaInicio,#txtFechaInicioTrabajoIT,#txtFechaFinTrabajoIT,#txtFecFechaRiesgoTrabIT").datepicker( { dateFormat: 'dd/mm/yy',
					changeMonth : true,
					changeYear : true,
					maxDate: data.responseText,
					yearRange : '-112:+0' });
		});
		
		
	 $("input:radio[name=rdPatronPrincipalDP]").click(function() { 
			patronPrincipal = $(this).val(); 	    
		}); 	
	
	 $("input:radio[name=rdDenunciadoAntesDP]").click(function() { 
		   denunciadoAntes = $(this).val(); 
	 });

	  var oTable = $(dtPatronesDenunciados).dataTable();
	  oTable.fnDraw();
	
}); 


function validaForma(){
	   var flag;
		if(isExplorer){
			flag= validarFormaGuardar();
		}else{
			flag= validaGuardar.form();
		}
		return flag;
}

function validaFecha(form,fec,fec2,leyendafec,leyendafec2){
	fec = "form#"+form+" #"+fec+"";
	fec2 = "form#"+form+" #"+fec2+"";
	if($(fec2).val()!="" && $(fec).val()!=""){
		if(!validaFechas($(fec2).val(),$(fec).val())){
			alert("La "+leyendafec+" es menor a la "+leyendafec2);
			$(fec2).val(""); 
			$("#fechaFinal").val("");
			return;
		}
	}	
}

function jsValidaPeriodoFinal(fecha){
	var fechaIncial = $("#fechaIncial").val();
	if(fechaIncial != '' && fecha != ''){
		var anioI = fechaIncial.substring(6,10);
		var anioF = fecha.substring(6,10);
		
		if(anioI == anioF){
			$("#fechaFinal").val(fecha);
			return true;
		}else{
			alert('No se puede elegir mas de un ejercicio');
			$("#fechaFinal").val("");
			$("#fechaIncial").val("")
			return false;
		}
	}	
}


function jsValidaDiaHabil(fecha){
	var evalFecha = $(fecha).val();
	if(evalFecha != ''){
	   var elemFecha = evalFecha.split("/"); 
	   var hFecha = new Date(elemFecha[2],elemFecha[1]-1, elemFecha[0]);
	   var diaFecha = hFecha.getDay();
	   alert(diaFecha);
	   if(diaFecha == 6 || diaFecha == 0){ 
		   return(false);
	   }else{
		   return(true);
	   }
	}
}



function siguiente(){
	var actionO = $("#frmMain").attr("action");	
	$("#frmMain").attr("action",getAppContextParaJS() + actionO );				
	$("#frmMain").submit(); 	 	 
}

function irInicio(){
	$("#wlForm").submit(); 
}
function irLogin(){
	$("#irLoginForm").submit(); 
}
function irRecuperar(){
	$("#irRecuperarForm").submit(); 
}
function irRecuperarConf(){
	$("#irRecuperaConfForm").submit(); 
}
function irRegistro(){
	$("#formRecuperaConfirma").submit(); 
}

function loguear(){
	$("#registroForm").submit(); 
}

function jsCalculaVigenciaDenuncia(){ 
    var evalFecha = new Date();
    var fechaInicial = new Date();
    
    var valorFecha = fechaInicial.valueOf(); 
    var valorFechaTerminoNatural = valorFecha +  ( 10 * 24 * 60 * 60 * 1000 ); 
    fechaTerminoNatural = new Date(valorFechaTerminoNatural); 
    
    var i=0;
    var fechaVigenciaDenuncia = valorFecha;
    while(i <10){
        fechaVigenciaDenuncia = fechaVigenciaDenuncia + (24 * 60 * 60 * 1000 );
        if( jsIsDiaHabil(fechaVigenciaDenuncia) && !jsIsDiaFestivo(fechaVigenciaDenuncia)){ // IsDiaHabil
           i = i+1;
        }
    }
    var fechaVigencia = new Date(fechaVigenciaDenuncia);
    // alert(fechaVigencia);
}

function jsIsDiaHabil(evalDia){
	var evalDate = new Date(evalDia);	
	if(evalDate.getDay() == 6 ||evalDate.getDay() == 0){
		return false;
	}else{return true;} 
}

function toUpperCase(elemento){
	$('input#'+elemento).prop('value', $('input#'+elemento).val().toUpperCase() )
}

function jsIsDiaFestivo(evalDia){
	var evalDate = new Date(evalDia);
	var calendario = new Array(12);
	
	for (i = 0; i < calendario.length; i++){
	 calendario[i] = new Array();
	}
	 calendario[0][0] = "A&ntilde;o nuevo";
	 calendario[0][5] = "Reyes Magos";
	 calendario[11][24] = "Navidad";
	 calendario[11][30] = "Fin de a&ntilde;o";
	 	 
	 if(calendario[evalDate.getMonth()][evalDate.getDate() - 1] != null){
		 alert (calendario[evalDate.getMonth()][evalDate.getDate() - 1]);
		 return true;	 
	 }else{
		 return false;
	 }

}

function conMayusculas(field) {
     field.value = field.value.toUpperCase();
}

function activaFS(select){
	var tD = $('#cveTipodenunciante').val();
	if(tD == '1'){
		$('#fsDatosBeneficiario').hide();
		$('#fsDatosRepresentanteLegal').hide();
	}
	if(tD == '2'){
		$('#fsDatosBeneficiario').show();	
		$('#fsDatosRepresentanteLegal').hide();
	}
	if(tD == '3'){
		$('#fsDatosRepresentanteLegal').show();
		$('#fsDatosBeneficiario').hide();
	}
	
}

function enviar(){
	$("#hdEnviar").val("1");
	$("#frmConsulta").attr("action", getAppContextParaJS() +'/denuncia/enviar/'+idDenuncia +'.do');
}

function agregaDenuncia() {		
	$("#ndForm").submit(); 
}


function modificaDenuncia(idDenuncia){		
	$("#frmConsulta").attr("action", getAppContextParaJS() +'/denuncia/modificaDenuncia/'+idDenuncia +'.do');
	$("#frmConsulta").submit();
}



function muestraAgregaPC(){
	$("#btnCargaPatCmplDP").show();
}

function ocultaAgregaPC(){
	if(patronesCmpl.length > 1){		
		alert("Elimine los patrones complemento");
	}else{
		 $("#btnCargaPatCmplDP").hide();
		 $("#tblPatronCmplDP").hide();
		 $("#hdrTblPatronCmplDP").hide();
		 patronPrincipal = 1;
	}	
}


function opcionOtro(field){	
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
	} else if(id == "") {
	
	}
}

function llenaComboPeriodoPago(){
	$.postJSON(getAppContextParaJS() + "/denuncia/datosTrabajoMain/consultaPeriodoPago.do", '', function(datas) {
		 var options = "<option value='-1' >--Por favor seleccione--</option>";
		 if(datas!=null)
		   for (var i = 0; i < datas.length; i++) {
	         options += "<option value='"+ datas[i].cveFormapago +"'>"+ datas[i].descFormapago +"</option>";		     
	       }
			// Agrega las opciones al control
		 $('select#sltCvePeriodoPagoIT').html(options);
		 // Si es una consulta, selecciona el valor de la lista
		 if (objDenuncia!=null && hasValue(objDenuncia.datosCentroTrabajoVO.pagoPeriodoSal.cveFormaPago)) {
			 $('#sltCvePeriodoPagoIT').val(objDenuncia.datosCentroTrabajoVO.pagoPeriodoSal.cveFormaPago);
			 $("#sltCvePeriodoPagoIT").trigger('onchange');
		 }
	}).error(function(datas){ 
		(datas);
	}).complete(function(){
		// Instrucciones para el 'complete'
	});

	
}


function llenaComboActividad(){
	
	$.postJSON(getAppContextParaJS() + "/denuncia/datosTrabajoMain/consultaActividades.do?sector="+$("#cbxSelSectordDP").val(), '', function(datas) {
		 var options = "<option value='-1' >--Por favor seleccione--</option>";
		 if(datas!=null)
		   for (var i = 0; i < datas.length; i++) {
	         options += "<option style='width: 250px' value='"+ datas[i].cveActEconomica +"'>"+ datas[i].txActividad +"</option>";		     
	       }
			// Agrega las opciones al control
		$('select#cbxSelGiroActividadDP').html(options);
		generaToolTipCombo("cbxSelGiroActividadDP");
	
		 // Si es una consulta, selecciona el valor de la lista
		 if (objDenuncia!=null && hasValue(objDenuncia.datosPatronVO.giroPatron)) {
			 $('#cbxSelGiroActividadDP').val(objDenuncia.datosPatronVO.giroPatron);
			 $("#cbxSelGiroActividadDP").change();
		 }
	}).error(function(datas){ 
		(datas);
	}).complete(function(){
		// Instrucciones para el 'complete'
	});
}


function llenaComboSector(){
	
	$.postJSON(getAppContextParaJS() + "/denuncia/datosTrabajoMain/consultaGrupos.do", '', function(datas) {
		 var options = "<option value='-1' >--Por favor seleccione--</option>";
		 if(datas!=null)
		   for (var i = 0; i < datas.length; i++) {
	         options += "<option  style='width: 250px' value='"+ datas[i].cveGrupo +"'>"+ datas[i].txGrupo +"</option>";		     
	       }
			// Agrega las opciones al control
		$('select#cbxSelSectordDP').html(options);
		generaToolTipCombo("cbxSelSectordDP");
	
		 // Si es una consulta, selecciona el valor de la lista
		 if (objDenuncia!=null && hasValue(objDenuncia.datosPatronVO.sector)) {
			 $('#cbxSelSectordDP').val(objDenuncia.datosPatronVO.sector);
			 $('#cbxSelSectordDP').change();
		 }
	}).error(function(datas){ 
		(datas);
	}).complete(function(){
		// Instrucciones para el 'complete'
	});
}


function llenaComboTipoDocumento(){
	
	$.postJSON(getAppContextParaJS() + "/denuncia/datosTrabajoMain/consultaTipoDocumento.do", '', function(datas) {
		 var options = "<option value='-1' >--Por favor seleccione--</option>";
		 if(datas!=null)
		   for (var i = 0; i < datas.length; i++) {
	         options += "<option  style='width: 250px' value='"+ datas[i].cveTipodocumento +"'>"+ datas[i].desDocumento +"</option>";		     
	       }
			// Agrega las opciones al control
		$('select#cmbCveTipodocumentoDT').html(options);
		$('select#cmbCveTipodocumentoBDT').html(options);
		$('select#cmbCveTipodocumentoRLDT').html(options);
		generaToolTipCombo("cmbCveTipodocumentoDT");
		generaToolTipCombo("cmbCveTipodocumentoBDT");
		generaToolTipCombo("cmbCveTipodocumentoRLDT");
	
		$('select#cmbCveTipodocumentoDT').unbind();
		$('select#cmbCveTipodocumentoDT').change(function(){
		if($('select#cmbCveTipodocumentoDT').val()==-1){
				$("#txtDesUpload").hide();
				$("#txtDesUploadLabel").hide();				
				$("#uploaderTrabajador").hide();
			}else{
				$("#txtDesUpload").show();
				$("#txtDesUploadLabel").show();				
				$("#uploaderTrabajador").show();
			}
		});
		
		
		$('select#cmbCveTipodocumentoBDT').unbind();
		$('select#cmbCveTipodocumentoBDT').change(function(){
		if($('select#cmbCveTipodocumentoBDT').val()==-1){
				$("#txtDesUploadBeneficiarioLabel").hide();
				$("#txtDesUploadBeneficiario").hide();				
				$("#uploaderBeneficiario").hide();
			}else{
				$("#txtDesUploadBeneficiarioLabel").show();
				$("#txtDesUploadBeneficiario").show();				
				$("#uploaderBeneficiario").show();
			}
		});
		
		
		$('select#cmbCveTipodocumentoRLDT').unbind();
		$('select#cmbCveTipodocumentoRLDT').change(function(){
		if($('select#cmbCveTipodocumentoRLDT').val()==-1){
				$("#txtDesUploadRPLabel").hide();
				$("#txtDesUploadRP").hide();				
				$("#uploaderRepLegal").hide();
			}else{
				$("#txtDesUploadRPLabel").show();
				$("#txtDesUploadRP").show();				
				$("#uploaderRepLegal").show();
			}
		});
		
		
		$('select#sltCveComprobantePagoIT').unbind();
		$('select#sltCveComprobantePagoIT').change(function(){
		if($('select#sltCveComprobantePagoIT').val()==-1 || $('select#sltCveComprobantePagoIT').val()==10){
				$("#txtDesUploadComprobantePago").hide();
				$("#uploaderComprobantePago").hide();
				
			}else{
				$("#txtDesUploadComprobantePago").show();
				$("#uploaderComprobantePago").show();
			}
		});
		
		
		 // Si es una consulta, selecciona el valor de la lista
		 if (objDenuncia!=null && hasValue(objDenuncia.datosTrabajadorVO.trabajador.docOficial)) {
			 $('#cmbCveTipodocumentoDT').val(objDenuncia.datosTrabajadorVO.trabajador.docOficial);			 
		 }
		 
		 
		 if (objDenuncia!=null && hasValue(objDenuncia.datosTrabajadorVO.representanteLegal.docOficial)) {
			 $('#cmbCveTipodocumentoRLDT').val(objDenuncia.datosTrabajadorVO.representanteLegal.docOficial);			 
		 }
		 
		 
		 if (objDenuncia!=null && hasValue(objDenuncia.datosTrabajadorVO.beneficiario.docOficial)) {
			 $('#cmbCveTipodocumentoBDT').val(objDenuncia.datosTrabajadorVO.beneficiario.docOficial);			 
		 }
		 
		 $('#cmbCveTipodocumentoDT').change();
		 $('#cmbCveTipodocumentoRLDT').change();
		 $('#cmbCveTipodocumentoBDT').change();
		 $('select#sltCveComprobantePagoIT').change();
		 
	}).error(function(datas){ 
		(datas);
	}).complete(function(){
		// Instrucciones para el 'complete'
	});
}

function ocultaCamposAdjunto(id,caja,boton,botonCargar){		
	if(!$("#"+id).is(':checked')){
		$("#"+caja).hide();
		$("#"+boton).hide(); 
		$("#"+botonCargar).hide();		
	}else{
		$("#"+caja).show();
		$("#"+boton).show();
		if($("#"+caja).val() != ""){
			$("#"+botonCargar).show();
			
		}
	}
}
function llenaComboSubdelegaciones(){
	
	$.postJSON(getAppContextParaJS() + "/denuncia/datosTrabajoMain/consultaSubdelegaciones.do", '', function(datas) {
		 var options = "<option value='-1' >--Por favor seleccione--</option>";
		 if(datas!=null)
		   for (var i = 0; i < datas.length; i++) {
	         options += "<option  style='width: 250px' value='"+ datas[i].cveSubdelegacion +"'>"+ datas[i].nomNombre +"</option>";		     
	       }
			// Agrega las opciones al control
		$('select#sltSubdelegacionIT').html(options);
		//generaToolTipCombo("sltSubdelegacionIT");
	
		 // Si es una consulta, selecciona el valor de la lista
		 if (objDenuncia!=null && hasValue(objDenuncia.cveSubdelegacion)) {
			 $('#sltSubdelegacionIT').val(objDenuncia.cveSubdelegacion);
		 }
	}).error(function(datas){ 
		(datas);
	}).complete(function(){
		// Instrucciones para el 'complete'
	});
}




function generaToolTipCombo(idCombo){
	function tooltipselect(){
		drop = document.getElementById(idCombo);
		drop.title = drop.options[drop.selectedIndex].text;
	};

	items = document.getElementById(idCombo).getElementsByTagName("option");
	for(i=0; i<items.length; i++){
	       items[i].title=items[i].text;				
	}
	drop = document.getElementById(idCombo);
	drop.onchange = tooltipselect;
	tooltipselect();
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


function desformateaFecha(sFecha) {
	var fecha = '';
	if (sFecha != null && sFecha != '') {
		var anio = sFecha.substring(0, 4);
		var mes = sFecha.substring(5, 7);
		var dia = sFecha.substring(8, 10);
		fecha = fecha + dia + '/' + mes + '/' + anio;
	}
	return fecha;
}

function checkBoxSeleccionados(){
	checkboxValues = $('[name="cbxFormaPago"]:checked').map(
			function(){ return $(this).val(); }
			).toArray();
}


function desactivaFS(){
	
	if($('#cmbCveTipoDenuncianteDT').val() == '2'){
		
		$('#txtDesNombreBDT').prop('disabled','');
		$('#txtDesPaternoBDT').prop('disabled','');
		$('#txtDesMaternoBDT').prop('disabled','');
		$('#txtCveCurpBDT').prop('disabled','');
		$('#direccionBeneficiario').prop('disabled','');
		$('#txtNumTelefonoBDT').prop('disabled','');
		$('#txtNumTelefonoMblBDT').prop('disabled','');
		$('#cmbCveTipodocumentoBDT').prop('disabled','');
		$('#buttonAdjuntarDatosTrabajador').prop('disabled','');
		$('#buttonEliminarDatosTrabajador').prop('disabled','');
		$('#txtNumDocumentoBDT').prop('disabled','');		
		
		$('#txtDesNombreRLDT').prop('disabled','disabled');
		$('#txtDesPaternoRLDT').prop('disabled','disabled');
		$('#txtDesMaternoRLDT').prop('disabled','disabled');
		$('#txtCveCurpRLDT').prop('disabled','disabled');
		$('#direccionRepLegalDT').prop('disabled','disabled');
		$('#txtNumTelefonoRLDT').prop('disabled','disabled');
		$('#txtNumTelefonoMblRLDT').prop('disabled','disabled');
		$('#cmbCveTipodocumentoRLDT').prop('disabled','disabled');
		$('#btnAdjuntarDocRL').prop('disabled','disabled');
		$('#btnEliminarDocRL').prop('disabled','disabled');
		$('#txtRefDocRLDT').prop('disabled','disabled');
		$('#txtNumDocumentoRLDT').prop('disabled','disabled');
		
	}
	if($('#cmbCveTipoDenuncianteDT').val() == '3'){
	
		$('#txtDesNombreRLDT').prop('disabled','');
		$('#txtDesPaternoRLDT').prop('disabled','');
		$('#txtDesMaternoRLDT').prop('disabled','');
		$('#txtCveCurpRLDT').prop('disabled','');
		$('#direccionRepLegalDT').prop('disabled','');
		$('#txtNumTelefonoRLDT').prop('disabled','');
		$('#txtNumTelefonoMblRLDT').prop('disabled','');
		$('#cmbCveTipodocumentoRLDT').prop('disabled','');
		$('#btnAdjuntarDocRL').prop('disabled','');
		$('#btnEliminarDocRL').prop('disabled','');
		$('#txtRefDocRLDT').prop('disabled','');
		$('#txtNumDocumentoRLDT').prop('disabled','');
		
		$('#txtDesNombreBDT').prop('disabled','disabled');
		$('#txtDesPaternoBDT').prop('disabled','disabled');
		$('#txtDesMaternoBDT').prop('disabled','disabled');
		$('#txtCveCurpBDT').prop('disabled','disabled');
		$('#direccionBeneficiario').prop('disabled','disabled');
		$('#txtNumTelefonoBDT').prop('disabled','disabled');
		$('#txtNumTelefonoMblBDT').prop('disabled','disabled');
		$('#cmbCveTipodocumentoBDT').prop('disabled','disabled');
		$('#buttonAdjuntarDatosTrabajador').prop('disabled','disabled');
		$('#buttonEliminarDatosTrabajador').prop('disabled','disabled');
		$('#txtNumDocumentoBDT').prop('disabled','disabled');	
	}
	
	if($('#cmbCveTipoDenuncianteDT').val() == '1'){
		
		$('#txtDesNombreRLDT').prop('disabled','disabled');
		$('#txtDesPaternoRLDT').prop('disabled','disabled');
		$('#txtDesMaternoRLDT').prop('disabled','disabled');
		$('#txtCveCurpRLDT').prop('disabled','disabled');
		$('#direccionRepLegalDT').prop('disabled','disabled');
		$('#txtNumTelefonoRLDT').prop('disabled','disabled');
		$('#txtNumTelefonoMblRLDT').prop('disabled','disabled');
		$('#cmbCveTipodocumentoRLDT').prop('disabled','disabled');
		$('#btnAdjuntarDocRL').prop('disabled','disabled');
		$('#btnEliminarDocRL').prop('disabled','disabled');
		$('#txtRefDocRLDT').prop('disabled','disabled');
		$('#txtNumDocumentoRLDT').prop('disabled','disabled');
		
		$('#txtDesNombreBDT').prop('disabled','disabled');
		$('#txtDesPaternoBDT').prop('disabled','disabled');
		$('#txtDesMaternoBDT').prop('disabled','disabled');
		$('#txtCveCurpBDT').prop('disabled','disabled');
		$('#direccionBeneficiario').prop('disabled','disabled');
		$('#txtNumTelefonoBDT').prop('disabled','disabled');
		$('#txtNumTelefonoMblBDT').prop('disabled','disabled');
		$('#cmbCveTipodocumentoBDT').prop('disabled','disabled');
		$('#buttonAdjuntarDatosTrabajador').prop('disabled','disabled');
		$('#buttonEliminarDatosTrabajador').prop('disabled','disabled');
		$('#txtNumDocumentoBDT').prop('disabled','disabled');	
	}
}

function imprimeAcuse(){		
	var actionO = $("#frmEnviar2").attr("action");			
	$("#frmEnviar2").attr("action",getAppContextParaJS() + actionO );	
	$("#frmEnviar2").submit(); 	 	 
}

// Dialog de Agregar Patrones
function openDgAgregarPatronesDenunciados(){
	oDgPatronesDenunciados.dialog('open');
}

function cargaDenuncia(){
	var idDenunciaRdSelect = $('#hdIdDenunciaCarga').val();
	var scveFoliodenuncia = '"cveFoliodenuncia":'+'"' + idDenunciaRdSelect + '"';	
	var sDenuncia = '{'+ scveFoliodenuncia +'}';
	var jDenuncia = jQuery.parseJSON(sDenuncia);
	

			 
	
	
	
	
	// jQuery.ajaxSetup({async:false});
	if(idDenunciaRdSelect != null & idDenunciaRdSelect != ''){
	
	jQuery.ajax({
		async: false,
	    type: 'POST',
	    url:  getAppContextParaJS() + '/denuncia/obtenerFechaServidor.do',
	    data: sDenuncia, 
	    success: function(data) { 	    	    	  
	    			$.postJSON(getAppContextParaJS() + "/denuncia/cargaDenuncia.do", jDenuncia, function(denuncia) {
    					  if(denuncia != null){	    						  
    					      $('#hdIdDenuncia').prop('value', denuncia.cveFoliodenuncia);
    						  $('#hdIdDenunciaCarga').prop('value', denuncia.cveFoliodenuncia);
    						  $('#txtFolioDenunciaDT').prop('value', denuncia.numFoliodenuncia);
    						  $("#cmbCveTipoDenuncianteDT").prop('value',denuncia.cveTipodenunciante);    						 
    					  }
    				}).error(function(denuncia){ 
    					// alert("error generando denuncia" + denuncia);
    				}).complete(function(){ 
    					// alert("complete cargando denuncia" + denuncia);
    				});
    				
    				$.postJSON(getAppContextParaJS() + "/denuncia/cargaPersonas.do", jDenuncia, function(personas) {
						  if(personas != null){	    						  			  
							  for(i=0; personas.length; i++){
								  var tipoDenunciante =personas[i].cveTipodenunciante; 
								  // alert( personas[i].cveTipodenunciante);
								  if( tipoDenunciante == '1'){
									    $('#hdIdTrabajador').prop('value', personas[i].cvePersona);					    		   
							    		$('#txtDesNombreDT').prop('value', personas[i].desNombre);
							    		$('#txtDesPaternoDT').prop('value', personas[i].desPaterno);
							    		$('#txtDesMaternoDT').prop('value', personas[i].desMaterno);
							    		if(personas[i].cveNss != '' && personas[i].cveNss != null){
							    			$('input:radio[name="rdCveNssDT"]').filter('[value="1"]').attr('checked', true); 
							    			$("#tblNss").show();
							    		}
							    		$('#txtCveCurpDT').prop('value', personas[i].cveCurp);
							    		$('#txtCveRfcDT').prop('value', personas[i].cveRfc);
							    		$('#txtCveNssDT').prop('value', personas[i].cveNss);
							    		$('#txtDesEmailDT').prop('value', personas[i].desEmail);
							    		$('#txtNumTelefonoDT').prop('value', personas[i].numTelefono);
							    		$('#numCelular').prop('value', personas[i].numCelular);		
							    		if(personas[i].cveTipodocumento != '' && personas[i].cveTipodocumento != null){
							    			$("#cmbCveTipodocumentoDT").prop('value',personas[i].cveTipodocumento);   
							    		}
							    		$('#numDocumento').prop('value', personas[i].numDocumento);
										    					   
							  	   }
								   if( tipoDenunciante == '2'){
									    $('#hdIdBeneficiario').prop('value', personas[i].cvePersona);					    		   
							    		$('#txtDesNombreBDT').prop('value', personas[i].desNombre);
							    		$('#txtDesPaternoBDT').prop('value', personas[i].desPaterno);
							    		$('#txtDesMaternoBDT').prop('value', personas[i].desMaterno);							    		
							    		$('#txtNumTelefonoBDT').prop('value', personas[i].numTelefono);
							    		$('#txtNumTelefonoMblBDT').prop('value', personas[i].numCelular);							    									    		
							    		if(personas[i].cveTipodocumento != '' && personas[i].cveTipodocumento != null){
							    			$("#cmbCveTipodocumentoBDT").prop('value',personas[i].cveTipodocumento);   
							    		}
							    		$('#txtNumDocumentoBDT').prop('value', personas[i].numDocumento);		
							    		
							    		$('#txtDesNombreBDT').prop('disabled','');
							    		$('#txtDesPaternoBDT').prop('disabled','');
							    		$('#txtDesMaternoBDT').prop('disabled','');							    		
							    		$('#direccionBeneficiario').prop('disabled','');
							    		$('#txtNumTelefonoBDT').prop('disabled','');
							    		$('#txtNumTelefonoMblBDT').prop('disabled','');
							    		$('#cmbCveTipodocumentoBDT').prop('disabled','');
							    		$('#buttonAdjuntarDatosTrabajador').prop('disabled','');
							    		$('#buttonEliminarDatosTrabajador').prop('disabled','');
							    		$('#txtNumDocumentoBDT').prop('disabled','');
								   }
								   if(tipoDenunciante == '3'){
									    $('#hdIdRepLegal').prop('value', personas[i].cvePersona);					    		   
							    		$('#txtDesNombreRLDT').prop('value', personas[i].desNombre);
							    		$('#txtDesPaternoRLDT').prop('value', personas[i].desPaterno);
							    		$('#txtDesMaternoRLDT').prop('value', personas[i].desMaterno);
							    		
							    		$('#txtNumTelefonoRLDT').prop('value', personas[i].numTelefono);
							    		$('#txtNumTelefonoMblRLDT').prop('value', personas[i].numCelular);							    									    		
							    		if(personas[i].cveTipodocumento != '' && personas[i].cveTipodocumento != null){
							    			$("#cmbCveTipodocumentoRLDT").prop('value',personas[i].cveTipodocumento);   
							    		}
							    		$('#txtNumDocumentoBDT').prop('value', personas[i].numDocumento);	
							    		
							    		$('#txtDesNombreRLDT').prop('disabled','');
							    		$('#txtDesPaternoRLDT').prop('disabled','');
							    		$('#txtDesMaternoRLDT').prop('disabled','');
							    		$('#txtNumTelefonoRLDT').prop('disabled','');
							    		$('#txtNumTelefonoMblRLDT').prop('disabled','');
							    		$('#cmbCveTipodocumentoRLDT').prop('disabled','');
							    		
							    		$('#direccionRepLegalDT').prop('disabled','');
							    		
							    		
							    		
							    		$('#btnAdjuntarDocRL').prop('disabled','');
							    		$('#btnEliminarDocRL').prop('disabled','');
							    		$('#txtRefDocRLDT').prop('disabled','');
							    		$('#txtNumDocumentoRLDT').prop('disabled','');
								   }
								  
						  		}
					  		}
					}).error(function(){ 
						// alert("error cargando personas");
					}).complete(function(){ 
						// alert("complete cargando denuncia" + personas);
					});
    				
    				
    				$.postJSON(getAppContextParaJS() + "/denuncia/cargaPatronPrincipal.do", jDenuncia, function(patron) {
    					if(patron != null){	    
    						$('#hdIdPatronPrincipal').prop('value', patron.cveDatospatron);
    						// alert($('#hdIdPatronPrincipal').val());
    						if(patron.indPatrondenunciado != '' && patron.indPatrondenunciado != null){
				    			if(patron.indPatrondenunciado == '1'){
    							  $('input:radio[name="rdDenunciadoAntesDP"]').filter('[value="1"]').attr('checked', true);
    							} 
				    			if(patron.indPatrondenunciado == '0'){
				    			  $('input:radio[name="rdDenunciadoAntesDP"]').filter('[value="0"]').attr('checked', true);	
				    			}
				    		}
    						$('#txtDesNomrazonsocialDP').prop('value', patron.desNomrazonsocial);
    						$('#txtDesNomreplegalDP').prop('value', patron.desNomreplegal);
    						// alert( patron.desRfc);
    						$('#txtRfcPatronDP').prop('value', patron.desRfc);
    						$('#txtCveRegpatDP').prop('value', patron.cveRegpat);
    						$('#txtNumTrabajadoresDP').prop('value', patron.numTrabajadores);
    						$('#txtNumTelefonoPatronDP').prop('value', patron.numTelefono);
    						$('input:radio[name="rdPatronPrincipalDP"]').filter('[value="1"]').attr('checked', true);
    					}
					}).error(function(jqXHR, textStatus, errorThrown) { 
//					     console.log   ("error " + textStatus); 
//					        console.log("incoming Text " + jqXHR.responseText); 
					   }).complete(function(){ 
						// alert("complete cargando denuncia" + personas);
					});
    				
    				
    				$.postJSON(getAppContextParaJS() + "/denuncia/cargaMotivosDenuncia.do", jDenuncia, function(motivos) {
    					if(motivos != null){	       						
    						 for(i=0; motivos.length; i++){
    							 var cveMotivo = motivos[i].id.cveMotivodenuncia;    							 
    							 if(cveMotivo == '1'){
    								 $('#hdIdm1').prop('value', motivos[i].id.cveMotivodenuncia);
    								 $("#md1").prop('checked', true);
    								 $('#md1fechaInicio').prop('value', motivos[i].fecLabdelIngreso);
    								 $('#md1fechaFin').prop('value',motivos[i].fecLabalDejolab);    								 
    							 }    							 
    							 if(cveMotivo == '2'){
    								 $('#hdIdm2').prop('value', motivos[i].id.cveMotivodenuncia);
    								 $("#md2").prop('checked', true);
    								 $('#md2fechaInicio').prop('value', motivos[i].fecLabdelIngreso);
    								 $('#md2fechaFin').prop('value',motivos[i].fecLabalDejolab);
    							 }    							 
    							 if(cveMotivo == '3'){
    								 $('#hdIdm3').prop('value', motivos[i].id.cveMotivodenuncia);
    								 $("#md3").prop('checked', true);
    								 $('#md3ImporteImss').prop('value', motivos[i].impSalarioReg);
    								 $('#md3ImporteReal').prop('value',motivos[i].impSalarioReal);
    							 }
    							 
    							 if(cveMotivo == '4'){    								 
    								 $('#hdIdm4').prop('value', motivos[i].id.cveMotivodenuncia);
    								 $("#md4").prop('checked', true);
    								 $('#md4fechaInicio').prop('value', motivos[i].fecLabdelIngreso);    								 
    							 }

    						}
    					}
					}).error(function(){ 
						// alert("error cargando personas");
					}).complete(function(){ 
						// alert("complete cargando denuncia" + personas);
					});
    				
    				
    				$.postJSON(getAppContextParaJS() + "/denuncia/cargaDatosTrabajo.do", jDenuncia, function(trabajo) {
    					if(trabajo != null){	    
    						$('#hdIdDatosTrabajo').prop('value', trabajo.cveInfotrabajo);
    						$('#txtFechaInicioTrabajoIT').prop('value',trabajo.fecFechaInicio);
    						$('#txtFechaFinTrabajoIT').prop('value', trabajo.fecFechaFin);
    						$('#txtDesLaboresDesempIT').prop('value', trabajo.desLaboresdesemp);

    						if(trabajo.desNumcontrato == 'No'){
				    			  $('input:radio[name="rdContratoIT"]').filter('[value="No"]').attr('checked', true);	
				    		}
    						if(trabajo.desNumcontrato== 'Si'){
				    			  $('input:radio[name="rdContratoIT"]').filter('[value="Si"]').attr('checked', true);	
				    		}
    						
    						$('#txtDesNomJefeInmediatoIT').prop('value', trabajo.desNomjefeinmediato);
    						
    						$('#txtDesHorariolaboresIT').prop('value', trabajo.desHorariolabores);
    						$('#txtImpSalarioPercibidoIT').prop('value', trabajo.impSalariopercibido);
    						$('#txtImpVacacionesIT').prop('value',trabajo.impVacaciones);
    						$('#txtNumDiasVacacionesIT').prop('value', trabajo.numDiasvacaciones);
    						$('#txtImpAguinaldoIT').prop('value',trabajo.impAguinaldo);
    						// $('#txtImpGratificcionIT').prop('value',trabajo.);
    						$('#txtDesBaseComisionOtrosIT').prop('value',trabajo.desBaseComisionOtros);
    						//$('#txtBaseOtorgamientoIT').prop('value',trabajo.desBaseOtorgamiento);
    						if(trabajo.fecFechariesgotrab != ''){
    							$('input:radio[name="rdRiesgo"]').filter('[value="Si"]').attr('checked', true);
    							$('#txtFecFechaRiesgoTrabIT').show();
    						}
    						$('#txtFecFechaRiesgoTrabIT').prop('value',trabajo.fecFechariesgotrab);
    						
    					}
					}).error(function(){ 
						// alert("error cargando personas");
					}).complete(function(){ 
						// alert("complete cargando denuncia" + personas);
						var idInfoTrabajo = $('#hdIdDatosTrabajo').val();
						var scveInfotrabajo = '"cveInfotrabajo":'+'"' + idInfoTrabajo + '"';	
						var sInfotrabajo = '{'+ scveInfotrabajo +'}';
						// alert(sInfotrabajo);
						var jInfoTrabajo = jQuery.parseJSON(sInfotrabajo);
						$.postJSON(getAppContextParaJS() + "/denuncia/cargaFormasPago.do", jInfoTrabajo, function(formasPago) {
	    					if(formasPago != null){	    
	    						for(i=0; formasPago.length; i++){	    							
	    							if(formasPago[i].id.cveConcepto == '1'){
	    								$('#sltCvePeriodoPagoIT').prop('value', formasPago[i].id.cveFormapago);	    								
	    								if(formasPago[i].id.cveFormapago == '11'){	    									
	    									$('#txtDesEspecifiquePPIT').prop('value', formasPago[i].desEspecifique);
	    								}
	    							} // llena periodo pago de salario
	    							
	    							if(formasPago[i].id.cveConcepto == '2'){ // llena
																				// comprobantes
																				// pago
	    								$('#sltCveComprobantePagoIT').prop('value', formasPago[i].id.cveFormapago);
	    								if(formasPago[i].id.cveFormapago == '11'){	    									
	    									$('#txtDesEspecifiqueCPIT').prop('value', formasPago[i].desEspecifique);
	    								}
	    							}	
	    							if(formasPago[i].id.cveConcepto == '3'){
	    								var idCheck = "[value='" + formasPago[i].id.cveFormapago + "']";
	    								// $(idCheck).prop('checked', true);
	    								 $('input:checkbox[name="cbxFormaPago"]').filter(idCheck).attr('checked', true);
	    								
	    								if(formasPago[i].id.cveFormapago == '16'){	    									
	    									$('#txtDesEspecifiqueFPIT').prop('value', formasPago[i].desEspecifique);
	    								}
	    							} // llena formas pago
	    						}	    						
	    					}
						}).error(function(){ 
							// alert("error cargando personas");
						}).complete(function(){ 
							// alert("complete cargando denuncia" + personas);
						});		
					});
    				    				
		},
	    contentType: "application/json"
    }).error(function(data){ 
    	validarSesionExpirada(data);
    }).complete(function(){
    	// Instrucciones para el 'complete'
    });
   
   }
	
}


function limpiaCampo(objeto){

	if(objeto.value=='0'){
		objeto.value="";
	}
}

function validaFechaFinDeNoAfiliacion() {
  	var mensaje_errorFechaFinal="<label class='etiquetaError'>La fecha final no puede ser menor o igual a la fecha inicial</label>";	  	
  	var fechaInicio=$("form#denunciaForm #md1fechaInicio").val();	
  	var fechaFin=$("form#denunciaForm #md1fechaFin").val();	  	
  	$("form#denunciaForm #labelfechaFinNoAfil").html("");
  	if (fechaInicio==""){
  		$("form#denunciaForm #md1fechaFin").val("");
  		alert ("Verifique que exista la fecha inicial");
  	} else if (fechaFin!="" && !comparaFechas(fechaInicio, fechaFin, '/') || fechaInicio==fechaFin){
  		$("form#denunciaForm #labelfechaFinNoAfil").html(mensaje_errorFechaFinal);
  		$("form#denunciaForm #md1fechaFin").val("");
  	}  else if(fechaFin!=''){
  	}
  }
  

function validaFechaNacimiento() {
  	var mensaje_errorFechaFinal="<label class='etiquetaError'>Su edad no puede ser menor de 18 años</label>";	  	
  	var fechanacimiento=$("form#denunciaForm #txtFechaNacimientoDT").val();	  	  	
  	$("form#denunciaForm #txtFechaNacimientoDT").html("");
  	if (fechanacimient==""){
  		$("form#denunciaForm #txtFechaNacimientoDT").val("");
  		alert ("Debe colocar su fecha de nacimiento");
  	}
  }





function validaFecFinAfiliacionPost() {
	  	var mensaje_errorFechaFinal="<label class='etiquetaError'>La fecha final no puede ser menor o igual a la fecha inicial</label>";		  	
	  	var fechaInicio=$("form#denunciaForm #md2fechaInicio").val();	
	  	var fechaFin=$("form#denunciaForm #md2fechaFin").val();	
	  	
	  	$("form#denunciaForm #labelfechaFinAfilPost").html("");
	  	if (fechaInicio==""){
	  		$("form#denunciaForm #md2fechaFin").val("");
	  		alert ("Verifique que exista la fecha inicial");
	  	} else if (fechaFin!="" && !comparaFechas(fechaInicio, fechaFin, '/') || fechaInicio==fechaFin){
	  		$("form#denunciaForm #labelfechaFinAfilPost").html(mensaje_errorFechaFinal);
	  		$("form#denunciaForm #md2fechaFin").val("");
	  	}
  }
  
  function validaFecFinTrabajo() {
	  	var mensaje_errorFechaFinal="<span class='etiquetaError'>La fecha final no puede ser menor a la fecha inicial</span>";		  	
	  	var fechaInicio=$("form#denunciaForm #txtFechaInicioTrabajoIT").val();	
	  	var fechaFin=$("form#denunciaForm #txtFechaFinTrabajoIT").val();	
	  	
	  	$("form#denunciaForm #labelfechaFinTrabajo").html("");
	  	if (fechaInicio==""){
	  		$("form#denunciaForm #txtFechaFinTrabajoIT").val("");
	  		alert ("Verifique que exista la fecha inicial");
	  	} else if (fechaFin!="" && !comparaFechas(fechaInicio, fechaFin, '/') || fechaInicio==fechaFin){
	  	
	  		$("form#denunciaForm #labelfechaFinTrabajo").html('<span class="required">La fecha final no puede ser menor o igual a la fecha inicial </span>');
	  		$("form#denunciaForm #txtFechaFinTrabajoIT").val("");
	  	}
   }

  function llenaObjetoPatronComplemento(){
	  var idPatronPrincipal = $('#hdIdPatronPrincipal').val();
	  
	  var desNomrazonsocial = $('#txtDesNomrazonsocialCmpDP').val();	
	  var desRfc = $('#txtRfcPatronCmpDP').val();

	  var sDesNomrazonsocial= '"desNomrazonsocial":'+'"'+desNomrazonsocial+'"';
	  var sDesRfc= '"desRfc":'+'"'+desRfc+'"';

	  var strPatronCmp = '{'+ sDesNomrazonsocial +','+ sDesRfc +'}';
	  var idDenuncia = $('#hdIdDenuncia').val();
	  var jPatron = jQuery.parseJSON(strPatronCmp);

	  if(idDenuncia == '' ){
			alert("Guarde la denuncia antes de agregar patrones");
	  }else{
			if( idPatronPrincipal == ''){
				alert("Registre patron principal antes de registrar patrones complemento");
			}else if(txtDesNomrazonsocialCmpDP != ''){
		    	$.postJSON(getAppContextParaJS() +"/denuncia/agregaPatronComplemento/"+ idDenuncia +".do", jPatron, function(patron) {
	  				// alert("Los datos fueron guardados exitosamente.");
				  }).error(function(data){ 
				  		// alert("error" + data);
				  }).complete(function(patron){		  		
				  		// alert($('#hdIdPatronPrincipal').val());
				  });
		    } else{
		    	alert("Ingrese al menos la Razón Social de patrón complemento");
		    }
	   }	  	
    }

  function showNss(){
	  $("#tblNss").show();
  }
  
  function hideNss(){
	  $("#tblNss").hide();
	  $("#txtCveNssDT").val(null);
  }
  
  
  function showRiesgo(){
	  $("#tdRiesgo").show();
	  $("#txtFecFechaRiesgoTrabIT").show();
  }
  
  function hideRiesgo(){
	  $("#tdRiesgo").hide();
	  $("#txtFecFechaRiesgoTrabIT").hide();
	  $("#txtFecFechaRiesgoTrabIT").val(null);
  }
  
  function verificaFP(){	  
	
	  if($("#cbxOtrosDT").is(':checked')){
		  $('#txtDesEspecifiqueFPIT').removeAttr("disabled");
	  }else{
		  $('#txtDesEspecifiqueFPIT').prop('disabled','disabled');
	  }
  }
  
  function modificaPatronComplemento(){
	  var radiosP = document.getElementsByName("rdPatronCmpl");
		var seleccionado = 0;
		var idPCmpl;
		
		for (i=0;i<radiosP.length;i++){
			if(radiosP[i].checked){
				idPCmpl = radiosP[i].value;
			}
		}	  
	  alert(idPCmpl);
  }
  
  
  function guardaDomicilio(objDomicilio, objDenuncia){
	  // Enviamos al controller y validamos ahi
	  var numExt = "0";
		 if(objDomicilio.numExterior1!= undefined){
			 numExt =objDomicilio.numExterior1; 
		 }
	  var numExteriorAlf = "";
	 if(objDomicilio.numExteriorAlf!= undefined && objDomicilio.numExteriorAlf!=null){
		 numExteriorAlf =objDomicilio.numExteriorAlf; 
	 }
	 var numExterior2 = "0";
	 if(objDomicilio.numExterior2!= undefined && objDomicilio.numExterior2!=null){
		 numExterior2 =objDomicilio.numExterior2; 
	 }
	 var numInterior = "0";
	 if(objDomicilio.numInterior!= undefined && objDomicilio.numInterior!=null){
		 numInterior =objDomicilio.numInterior; 
	 }
	 var numInteriorAlf = "";
	 if(objDomicilio.numInteriorAlf!= undefined && objDomicilio.numInteriorAlf!=null){
		 numInteriorAlf =objDomicilio.numInteriorAlf; 
	 }
	 var vialidadPosterior = "";
	 if(objDomicilio.vialidadReferenciaPosterior!= undefined && objDomicilio.vialidadReferenciaPosterior!=null){
		 vialidadPosterior =objDomicilio.vialidadReferenciaPosterior.clave; 
	 }
	 var codigo = "";
	 if(objDomicilio.codigoPostal!= undefined){
		 codigo =  objDomicilio.codigoPostal.codigoPostal;
	 }
	 var nomVialidadPrimaria = "";
	 var cveVialidadPrimiaria = "";
	 if(objDomicilio.vialidadPrimaria!= undefined){
		 nomVialidadPrimaria =  objDomicilio.vialidadPrimaria.nombre;
		 cveVialidadPrimiaria = objDomicilio.vialidadPrimaria.clave;
	 }
	 var refPrimaria = "";
	 if(objDomicilio.vialidadReferenciaPrimaria!= undefined){
		 refPrimaria =  objDomicilio.vialidadReferenciaPrimaria.clave;
	 }
	 var refSecundaria = "";
	 if(objDomicilio.vialidadReferenciaPrimaria!= undefined){
		 refSecundaria =  objDomicilio.vialidadReferenciaSecundaria.clave;
	 }
	 var descripcion = "";
	 if(objDomicilio.descripcion!= undefined){
		 descripcion =  objDomicilio.descripcion;
	 }
	
	  var sDomicilioVO = '{' +
		 '"codigo":"'+codigo+'",'+
		 '"cveTipoDom":"1",'+
		 '"cveEnt":"'+objDomicilio.asentamiento.localidad.municipio.entidadFederativa.clave+'",'+
		 '"cveMun":"'+objDomicilio.asentamiento.localidad.municipio.clave+'",'+
		 '"cveLoc":"'+objDomicilio.asentamiento.localidad.clave+'",'+
		 '"cvePeriodo":"1",'+
		 '"nomVial":"'+nomVialidadPrimaria+'",'+
		 '"numExtNum":"'+numExt+'",'+
		 '"numExtAlf":"'+numExteriorAlf+'",'+
		 '"numExtAnt":"'+numExterior2+'",'+
		 '"numIntNum":"'+numInterior+'",'+
		 '"numIntAlf":"'+numInteriorAlf+'",'+
		 '"cveAsen":"'+objDomicilio.asentamiento.clave+'",'+
		 '"cveViaPrin":"'+cveVialidadPrimiaria+'",'+
		 '"cveViaRef1":"'+refPrimaria+'",'+
		 '"cveViaRef2":"'+refSecundaria+'",'+
		 '"cveViaRef3":"'+vialidadPosterior+'",'+
		 '"descripc":"'+descripcion+'",'+
		 '"domGeog":"1"}';
	   
	var domicilio = 	  jQuery.parseJSON(sDomicilioVO);
	  
	  $.postJSON(getAppContextParaJS() +"/denuncia/guardaDomicilio.do", domicilio, function(data) {
		  	objDenuncia.datosTrabajadorVO.trabajador.idDomicilio = data.domicilioId;
		 
		  }).error(function(data){ 
		  		// alert("error" + data);
		  }).complete(function(data){		  		
			  $("#btnGuardar").click();
		  });
	  
  }
  
  
  function guardaDomicilioBen(objDomicilio, objDenuncia){
	  // Enviamos al controller y validamos ahi
	  var numExt = "0";
		 if(objDomicilio.numExterior1!= undefined){
			 numExt =objDomicilio.numExterior1; 
		 }
	  var numExteriorAlf = "";
	 if(objDomicilio.numExteriorAlf!= undefined && objDomicilio.numExteriorAlf!=null){
		 numExteriorAlf =objDomicilio.numExteriorAlf; 
	 }
	 var numExterior2 = "0";
	 if(objDomicilio.numExterior2!= undefined && objDomicilio.numExterior2!=null){
		 numExterior2 =objDomicilio.numExterior2; 
	 }
	 var numInterior = "0";
	 if(objDomicilio.numInterior!= undefined && objDomicilio.numInterior!=null){
		 numInterior =objDomicilio.numInterior; 
	 }
	 var numInteriorAlf = "";
	 if(objDomicilio.numInteriorAlf!= undefined && objDomicilio.numInteriorAlf!=null){
		 numInteriorAlf =objDomicilio.numInteriorAlf; 
	 }
	 var vialidadPosterior = "";
	 if(objDomicilio.vialidadReferenciaPosterior!= undefined && objDomicilio.vialidadReferenciaPosterior!=null){
		 vialidadPosterior =objDomicilio.vialidadReferenciaPosterior.clave; 
	 }
	 var codigo = "";
	 if(objDomicilio.codigoPostal!= undefined){
		 codigo =  objDomicilio.codigoPostal.codigoPostal;
	 }
	 var nomVialidadPrimaria = "";
	 var cveVialidadPrimiaria = "";
	 if(objDomicilio.vialidadPrimaria!= undefined){
		 nomVialidadPrimaria =  objDomicilio.vialidadPrimaria.nombre;
		 cveVialidadPrimiaria = objDomicilio.vialidadPrimaria.clave;
	 }
	 var refPrimaria = "";
	 if(objDomicilio.vialidadReferenciaPrimaria!= undefined){
		 refPrimaria =  objDomicilio.vialidadReferenciaPrimaria.clave;
	 }
	 var refSecundaria = "";
	 if(objDomicilio.vialidadReferenciaPrimaria!= undefined){
		 refSecundaria =  objDomicilio.vialidadReferenciaSecundaria.clave;
	 }
	 var descripcion = "";
	 if(objDomicilio.descripcion!= undefined){
		 descripcion =  objDomicilio.descripcion;
	 }
	
	  var sDomicilioVO = '{' +
		 '"codigo":"'+codigo+'",'+
		 '"cveTipoDom":"1",'+
		 '"cveEnt":"'+objDomicilio.asentamiento.localidad.municipio.entidadFederativa.clave+'",'+
		 '"cveMun":"'+objDomicilio.asentamiento.localidad.municipio.clave+'",'+
		 '"cveLoc":"'+objDomicilio.asentamiento.localidad.clave+'",'+
		 '"cvePeriodo":"1",'+
		 '"nomVial":"'+nomVialidadPrimaria+'",'+
		 '"numExtNum":"'+numExt+'",'+
		 '"numExtAlf":"'+numExteriorAlf+'",'+
		 '"numExtAnt":"'+numExterior2+'",'+
		 '"numIntNum":"'+numInterior+'",'+
		 '"numIntAlf":"'+numInteriorAlf+'",'+
		 '"cveAsen":"'+objDomicilio.asentamiento.clave+'",'+
		 '"cveViaPrin":"'+cveVialidadPrimiaria+'",'+
		 '"cveViaRef1":"'+refPrimaria+'",'+
		 '"cveViaRef2":"'+refSecundaria+'",'+
		 '"cveViaRef3":"'+vialidadPosterior+'",'+
		 '"descripc":"'+descripcion+'",'+
		 '"domGeog":"1"}';
	   
	var domicilio = 	  jQuery.parseJSON(sDomicilioVO);
	  
	  $.postJSON(getAppContextParaJS() +"/denuncia/guardaDomicilio.do", domicilio, function(data) {
		  	objDenuncia.datosTrabajadorVO.beneficiario.idDomicilio = data.domicilioId;
		 
		  }).error(function(data){ 
		  		// alert("error" + data);
		  }).complete(function(data){		  		
			  $("#btnGuardar").click();
		  });
	  
  }
  
  
  function guardaDomicilioRep(objDomicilio, objDenuncia){
	  // Enviamos al controller y validamos ahi
	  var numExt = "0";
		 if(objDomicilio.numExterior1!= undefined){
			 numExt =objDomicilio.numExterior1; 
		 }
	  var numExteriorAlf = "";
	 if(objDomicilio.numExteriorAlf!= undefined && objDomicilio.numExteriorAlf!=null){
		 numExteriorAlf =objDomicilio.numExteriorAlf; 
	 }
	 var numExterior2 = "0";
	 if(objDomicilio.numExterior2!= undefined && objDomicilio.numExterior2!=null){
		 numExterior2 =objDomicilio.numExterior2; 
	 }
	 var numInterior = "0";
	 if(objDomicilio.numInterior!= undefined && objDomicilio.numInterior!=null){
		 numInterior =objDomicilio.numInterior; 
	 }
	 var numInteriorAlf = "";
	 if(objDomicilio.numInteriorAlf!= undefined && objDomicilio.numInteriorAlf!=null){
		 numInteriorAlf =objDomicilio.numInteriorAlf; 
	 }
	 var vialidadPosterior = "";
	 if(objDomicilio.vialidadReferenciaPosterior!= undefined && objDomicilio.vialidadReferenciaPosterior!=null){
		 vialidadPosterior =objDomicilio.vialidadReferenciaPosterior.clave; 
	 }
	 var codigo = "";
	 if(objDomicilio.codigoPostal!= undefined){
		 codigo =  objDomicilio.codigoPostal.codigoPostal;
	 }
	 var nomVialidadPrimaria = "";
	 var cveVialidadPrimiaria = "";
	 if(objDomicilio.vialidadPrimaria!= undefined){
		 nomVialidadPrimaria =  objDomicilio.vialidadPrimaria.nombre;
		 cveVialidadPrimiaria = objDomicilio.vialidadPrimaria.clave;
	 }
	 var refPrimaria = "";
	 if(objDomicilio.vialidadReferenciaPrimaria!= undefined){
		 refPrimaria =  objDomicilio.vialidadReferenciaPrimaria.clave;
	 }
	 var refSecundaria = "";
	 if(objDomicilio.vialidadReferenciaPrimaria!= undefined){
		 refSecundaria =  objDomicilio.vialidadReferenciaSecundaria.clave;
	 }
	 var descripcion = "";
	 if(objDomicilio.descripcion!= undefined){
		 descripcion =  objDomicilio.descripcion;
	 }
	
	  var sDomicilioVO = '{' +
		 '"codigo":"'+codigo+'",'+
		 '"cveTipoDom":"1",'+
		 '"cveEnt":"'+objDomicilio.asentamiento.localidad.municipio.entidadFederativa.clave+'",'+
		 '"cveMun":"'+objDomicilio.asentamiento.localidad.municipio.clave+'",'+
		 '"cveLoc":"'+objDomicilio.asentamiento.localidad.clave+'",'+
		 '"cvePeriodo":"1",'+
		 '"nomVial":"'+nomVialidadPrimaria+'",'+
		 '"numExtNum":"'+numExt+'",'+
		 '"numExtAlf":"'+numExteriorAlf+'",'+
		 '"numExtAnt":"'+numExterior2+'",'+
		 '"numIntNum":"'+numInterior+'",'+
		 '"numIntAlf":"'+numInteriorAlf+'",'+
		 '"cveAsen":"'+objDomicilio.asentamiento.clave+'",'+
		 '"cveViaPrin":"'+cveVialidadPrimiaria+'",'+
		 '"cveViaRef1":"'+refPrimaria+'",'+
		 '"cveViaRef2":"'+refSecundaria+'",'+
		 '"cveViaRef3":"'+vialidadPosterior+'",'+
		 '"descripc":"'+descripcion+'",'+
		 '"domGeog":"1"}';
	   
	var domicilio = 	  jQuery.parseJSON(sDomicilioVO);
	  
	  $.postJSON(getAppContextParaJS() +"/denuncia/guardaDomicilio.do", domicilio, function(data) {
		  	objDenuncia.datosTrabajadorVO.representanteLegal.idDomicilio = data.domicilioId;
		 
		  }).error(function(data){ 
		  		// alert("error" + data);
		  }).complete(function(data){		  		
			  $("#btnGuardar").click();
		  });
	  
  }
  
  function guardaDomicilioPat(objDomicilio, objDenuncia){
	  // Enviamos al controller y validamos ahi
	  // Enviamos al controller y validamos ahi
	  var numExt = "0";
		 if(objDomicilio.numExterior1!= undefined){
			 numExt =objDomicilio.numExterior1; 
		 }
	  var numExteriorAlf = "";
	 if(objDomicilio.numExteriorAlf!= undefined && objDomicilio.numExteriorAlf!=null){
		 numExteriorAlf =objDomicilio.numExteriorAlf; 
	 }
	 var numExterior2 = "0";
	 if(objDomicilio.numExterior2!= undefined && objDomicilio.numExterior2!=null){
		 numExterior2 =objDomicilio.numExterior2; 
	 }
	 var numInterior = "0";
	 if(objDomicilio.numInterior!= undefined && objDomicilio.numInterior!=null){
		 numInterior =objDomicilio.numInterior; 
	 }
	 var numInteriorAlf = "";
	 if(objDomicilio.numInteriorAlf!= undefined && objDomicilio.numInteriorAlf!=null){
		 numInteriorAlf =objDomicilio.numInteriorAlf; 
	 }
	 var vialidadPosterior = "";
	 if(objDomicilio.vialidadReferenciaPosterior!= undefined && objDomicilio.vialidadReferenciaPosterior!=null){
		 vialidadPosterior =objDomicilio.vialidadReferenciaPosterior.clave; 
	 }
	 var codigo = "";
	 if(objDomicilio.codigoPostal!= undefined){
		 codigo =  objDomicilio.codigoPostal.codigoPostal;
	 }
	 var nomVialidadPrimaria = "";
	 var cveVialidadPrimiaria = "";
	 if(objDomicilio.vialidadPrimaria!= undefined){
		 nomVialidadPrimaria =  objDomicilio.vialidadPrimaria.nombre;
		 cveVialidadPrimiaria = objDomicilio.vialidadPrimaria.clave;
	 }
	 var refPrimaria = "";
	 if(objDomicilio.vialidadReferenciaPrimaria!= undefined){
		 refPrimaria =  objDomicilio.vialidadReferenciaPrimaria.clave;
	 }
	 var refSecundaria = "";
	 if(objDomicilio.vialidadReferenciaPrimaria!= undefined){
		 refSecundaria =  objDomicilio.vialidadReferenciaSecundaria.clave;
	 }
	 var descripcion = "";
	 if(objDomicilio.descripcion!= undefined){
		 descripcion =  objDomicilio.descripcion;
	 }
	
	  var sDomicilioVO = '{' +
		 '"codigo":"'+codigo+'",'+
		 '"cveTipoDom":"1",'+
		 '"cveEnt":"'+objDomicilio.asentamiento.localidad.municipio.entidadFederativa.clave+'",'+
		 '"cveMun":"'+objDomicilio.asentamiento.localidad.municipio.clave+'",'+
		 '"cveLoc":"'+objDomicilio.asentamiento.localidad.clave+'",'+
		 '"cvePeriodo":"1",'+
		 '"nomVial":"'+nomVialidadPrimaria+'",'+
		 '"numExtNum":"'+numExt+'",'+
		 '"numExtAlf":"'+numExteriorAlf+'",'+
		 '"numExtAnt":"'+numExterior2+'",'+
		 '"numIntNum":"'+numInterior+'",'+
		 '"numIntAlf":"'+numInteriorAlf+'",'+
		 '"cveAsen":"'+objDomicilio.asentamiento.clave+'",'+
		 '"cveViaPrin":"'+cveVialidadPrimiaria+'",'+
		 '"cveViaRef1":"'+refPrimaria+'",'+
		 '"cveViaRef2":"'+refSecundaria+'",'+
		 '"cveViaRef3":"'+vialidadPosterior+'",'+
		 '"descripc":"'+descripcion+'",'+
		 '"domGeog":"1"}';
	   
	var domicilio = 	  jQuery.parseJSON(sDomicilioVO);
	  
	  $.postJSON(getAppContextParaJS() +"/denuncia/guardaDomicilio.do", domicilio, function(data) {
		  	objDenuncia.datosPatronVO.idDomicilio = data.domicilioId;
		 
		  }).error(function(data){ 
		  		// alert("error" + data);
		  }).complete(function(data){		  		
			  $("#btnGuardar").click();
		  });
	  
  }
  
  function guardaDomicilioPatAdicional(objDomicilio, objDenuncia){
	  // Enviamos al controller y validamos ahi
	  // Enviamos al controller y validamos ahi
	  var numExt = "0";
		 if(objDomicilio.numExterior1!= undefined){
			 numExt =objDomicilio.numExterior1; 
		 }
	  var numExteriorAlf = "";
	 if(objDomicilio.numExteriorAlf!= undefined && objDomicilio.numExteriorAlf!=null){
		 numExteriorAlf =objDomicilio.numExteriorAlf; 
	 }
	 var numExterior2 = "0";
	 if(objDomicilio.numExterior2!= undefined && objDomicilio.numExterior2!=null){
		 numExterior2 =objDomicilio.numExterior2; 
	 }
	 var numInterior = "0";
	 if(objDomicilio.numInterior!= undefined && objDomicilio.numInterior!=null){
		 numInterior =objDomicilio.numInterior; 
	 }
	 var numInteriorAlf = "";
	 if(objDomicilio.numInteriorAlf!= undefined && objDomicilio.numInteriorAlf!=null){
		 numInteriorAlf =objDomicilio.numInteriorAlf; 
	 }
	 var vialidadPosterior = "";
	 if(objDomicilio.vialidadReferenciaPosterior!= undefined && objDomicilio.vialidadReferenciaPosterior!=null){
		 vialidadPosterior =objDomicilio.vialidadReferenciaPosterior.clave; 
	 }
	 var codigo = "";
	 if(objDomicilio.codigoPostal!= undefined){
		 codigo =  objDomicilio.codigoPostal.codigoPostal;
	 }
	 var nomVialidadPrimaria = "";
	 var cveVialidadPrimiaria = "";
	 if(objDomicilio.vialidadPrimaria!= undefined){
		 nomVialidadPrimaria =  objDomicilio.vialidadPrimaria.nombre;
		 cveVialidadPrimiaria = objDomicilio.vialidadPrimaria.clave;
	 }
	 var refPrimaria = "";
	 if(objDomicilio.vialidadReferenciaPrimaria!= undefined){
		 refPrimaria =  objDomicilio.vialidadReferenciaPrimaria.clave;
	 }
	 var refSecundaria = "";
	 if(objDomicilio.vialidadReferenciaPrimaria!= undefined){
		 refSecundaria =  objDomicilio.vialidadReferenciaSecundaria.clave;
	 }
	 var descripcion = "";
	 if(objDomicilio.descripcion!= undefined){
		 descripcion =  objDomicilio.descripcion;
	 }
	
	  var sDomicilioVO = '{' +
		 '"codigo":"'+codigo+'",'+
		 '"cveTipoDom":"1",'+
		 '"cveEnt":"'+objDomicilio.asentamiento.localidad.municipio.entidadFederativa.clave+'",'+
		 '"cveMun":"'+objDomicilio.asentamiento.localidad.municipio.clave+'",'+
		 '"cveLoc":"'+objDomicilio.asentamiento.localidad.clave+'",'+
		 '"cvePeriodo":"1",'+
		 '"nomVial":"'+nomVialidadPrimaria+'",'+
		 '"numExtNum":"'+numExt+'",'+
		 '"numExtAlf":"'+numExteriorAlf+'",'+
		 '"numExtAnt":"'+numExterior2+'",'+
		 '"numIntNum":"'+numInterior+'",'+
		 '"numIntAlf":"'+numInteriorAlf+'",'+
		 '"cveAsen":"'+objDomicilio.asentamiento.clave+'",'+
		 '"cveViaPrin":"'+cveVialidadPrimiaria+'",'+
		 '"cveViaRef1":"'+refPrimaria+'",'+
		 '"cveViaRef2":"'+refSecundaria+'",'+
		 '"cveViaRef3":"'+vialidadPosterior+'",'+
		 '"descripc":"'+descripcion+'",'+
		 '"domGeog":"1"}';
	   
	var domicilio = 	  jQuery.parseJSON(sDomicilioVO);
	  
	  $.postJSON(getAppContextParaJS() +"/denuncia/guardaDomicilio.do", domicilio, function(data) {
		  		$("#cveDom").prop("value", data.domicilioId);
		  }).error(function(data){ 
		  		// alert("error" + data);
		  }).complete(function(data){		  		
//			  $("#btnGuardar").click(); 
		  });
	  
  }
  
  function guardaDomicilioCenTrab(objDomicilio, objDenuncia){
	  // Enviamos al controller y validamos ahi
	  var numExt = "0";
		 if(objDomicilio.numExterior1!= undefined){
			 numExt =objDomicilio.numExterior1; 
		 }
	  var numExteriorAlf = "";
	 if(objDomicilio.numExteriorAlf!= undefined && objDomicilio.numExteriorAlf!=null){
		 numExteriorAlf =objDomicilio.numExteriorAlf; 
	 }
	 var numExterior2 = "0";
	 if(objDomicilio.numExterior2!= undefined && objDomicilio.numExterior2!=null){
		 numExterior2 =objDomicilio.numExterior2; 
	 }
	 var numInterior = "0";
	 if(objDomicilio.numInterior!= undefined && objDomicilio.numInterior!=null){
		 numInterior =objDomicilio.numInterior; 
	 }
	 var numInteriorAlf = "";
	 if(objDomicilio.numInteriorAlf!= undefined && objDomicilio.numInteriorAlf!=null){
		 numInteriorAlf =objDomicilio.numInteriorAlf; 
	 }
	 var vialidadPosterior = "";
	 if(objDomicilio.vialidadReferenciaPosterior!= undefined && objDomicilio.vialidadReferenciaPosterior!=null){
		 vialidadPosterior =objDomicilio.vialidadReferenciaPosterior.clave; 
	 }
	 var codigo = "";
	 if(objDomicilio.codigoPostal!= undefined){
		 codigo =  objDomicilio.codigoPostal.codigoPostal;
	 }
	 var nomVialidadPrimaria = "";
	 var cveVialidadPrimiaria = "";
	 if(objDomicilio.vialidadPrimaria!= undefined){
		 nomVialidadPrimaria =  objDomicilio.vialidadPrimaria.nombre;
		 cveVialidadPrimiaria = objDomicilio.vialidadPrimaria.clave;
	 }
	 var refPrimaria = "";
	 if(objDomicilio.vialidadReferenciaPrimaria!= undefined){
		 refPrimaria =  objDomicilio.vialidadReferenciaPrimaria.clave;
	 }
	 var refSecundaria = "";
	 if(objDomicilio.vialidadReferenciaPrimaria!= undefined){
		 refSecundaria =  objDomicilio.vialidadReferenciaSecundaria.clave;
	 }
	 var descripcion = "";
	 if(objDomicilio.descripcion!= undefined){
		 descripcion =  objDomicilio.descripcion;
	 }
	
	  var sDomicilioVO = '{' +
		 '"codigo":"'+codigo+'",'+
		 '"cveTipoDom":"1",'+
		 '"cveEnt":"'+objDomicilio.asentamiento.localidad.municipio.entidadFederativa.clave+'",'+
		 '"cveMun":"'+objDomicilio.asentamiento.localidad.municipio.clave+'",'+
		 '"cveLoc":"'+objDomicilio.asentamiento.localidad.clave+'",'+
		 '"cvePeriodo":"1",'+
		 '"nomVial":"'+nomVialidadPrimaria+'",'+
		 '"numExtNum":"'+numExt+'",'+
		 '"numExtAlf":"'+numExteriorAlf+'",'+
		 '"numExtAnt":"'+numExterior2+'",'+
		 '"numIntNum":"'+numInterior+'",'+
		 '"numIntAlf":"'+numInteriorAlf+'",'+
		 '"cveAsen":"'+objDomicilio.asentamiento.clave+'",'+
		 '"cveViaPrin":"'+cveVialidadPrimiaria+'",'+
		 '"cveViaRef1":"'+refPrimaria+'",'+
		 '"cveViaRef2":"'+refSecundaria+'",'+
		 '"cveViaRef3":"'+vialidadPosterior+'",'+
		 '"descripc":"'+descripcion+'",'+
		 '"domGeog":"1"}';
	   
	var domicilio = 	  jQuery.parseJSON(sDomicilioVO);
	  
	  $.postJSON(getAppContextParaJS() +"/denuncia/guardaDomicilio.do", domicilio, function(data) {
		  	objDenuncia.datosCentroTrabajoVO.idDomicilio = data.domicilioId;
		 
		  }).error(function(data){ 
		  		// alert("error" + data);
		  }).complete(function(data){		  		
			  $("#btnGuardar").click();
		  });
	  
  }
  
  function llenaComboPreguntas(){
		$.postJSON("obtenerPreguntas.do", '', function(dataC) {
			 var options = "<option value='' >--Por favor seleccione--</option>";
			 if(dataC!=null)
			   for (var i = 0; i < dataC.length; i++) {
		         options += "<option value='"+ dataC[i].cvePregunta +"'>"+ dataC[i].desPregunta +"</option>";		     
		       }
			 $('select#pregunta').html(options);
			 
		  }).error(function(dataC){ 
			  (dataC);
		  }).complete(function(){
			// Instrucciones para el 'complete'
		  });
	}
  
  function recuperaPregunta(){
	  var sMail = $("#desMail").val();
		var sUsuario = '{' +
		'"desEmail" : "'+sMail+'"}';
	  var mail = jQuery.parseJSON(sUsuario);
	  $.postJSON(getAppContextParaJS() +"/denuncia/consultaUsuario.do", mail, function(dataC) {
			 if(dataC!=null && dataC.cvePregunta>0){
				 var idPregunta = dataC.cvePregunta;
				 $.postJSON(getAppContextParaJS() +"/denuncia/obtenerPreguntasPorID.do",  idPregunta, function(dataP) {
					 if(dataP!=null){
						 $('input#pregunta').prop("value", dataP[0].desPregunta);
					 }else{
						 $('input#pregunta').prop("value", "");
					 }
				 }).error(function(dataP){ 
				  
				 }).complete(function(){
					 // Instrucciones para el 'complete'
				 });
			 }else{
				 alert("El correo electr\u00f3nico no se encuentra registrado");
			 }
		  }).error(function(dataC){ 
			  
		  }).complete(function(){
			// Instrucciones para el 'complete'
		  });
  }
  
  
  function setPersona(persona){
	  ubicaDomicilio = persona;
  }
  
  function borraDomicilio(caso){

	  switch (caso){
	  		case 1:
	  			if(eliminaDom(objDenuncia.datosTrabajadorVO.trabajador.idDomicilio)){
	  				$("#txtDomicilio").val("");
	  				objDenuncia.datosTrabajadorVO.trabajador.idDomicilio=null;
	  			}
	  			break;
	  		case 2:
	  			if(eliminaDom(objDenuncia.datosTrabajadorVO.beneficiario.idDomicilio)){
	  				$("#direccionBeneficiario").val("");
	  				objDenuncia.datosTrabajadorVO.beneficiario.idDomicilio=null;
	  			}
	  			break;
	  		case 3:
	  			if(eliminaDom(objDenuncia.datosTrabajadorVO.representanteLegal.idDomicilio)){
	  				$("#direccionRepLegalDT").val("");
	  				objDenuncia.datosTrabajadorVO.representanteLegal.idDomicilio=null;
	  			}
	  			break;  	
	  		case 4:
	  			if(eliminaDom(objDenuncia.datosCentroTrabajoVO.idDomicilio)){
	  				$("#txtDomicilioTrabajoDP").val("");
	  				objDenuncia.datosCentroTrabajoVO.idDomicilio=null;
	  			}
	  			break;  
	  		case 5:
	  			if(eliminaDom(objDenuncia.datosPatronVO.idDomicilio)){
	  				$("#txtDomFiscalPtrIdDP").val("");
	  				objDenuncia.datosPatronVO.idDomicilio=null;
	  			}
	  			break; 
	  			
	  }
	  	
	
  }
  
  
  function eliminaDom(idDomicilio){	  
	  if(confirm("\u00BFEsta seguro que desea borrar el domicilio completamente?")){
		  var dom = '{' +'"domicilioId" : "'+idDomicilio+'"}';
		  var domicilioId = jQuery.parseJSON(dom);
		  bloquear();
		  $.postJSON(getAppContextParaJS() +"/denuncia/borrarDomicilio.do", domicilioId, function(dataC) {
			  
			  
			  
		  	}).error(function(dataC){ 
				  
			  }).complete(function(){
				// Instrucciones para el 'complete'
				  alert("Domicilio eliminado exitosamente");
				  desbloquear();
				  return true;
			  });
	  }	  
	  return false;
  }
  function actucap(){
		var obj=document.getElementById("imgCaptchaRec");
	    if (!obj) obj=window.document.all.cap;
	    if (obj){
	    	//obj.src='http://localhost:7001/denuncia-web/captchaController/captcha.htm?'+Math.random();
	     // obj.src='http://172.24.116.52:11000/denuncia-web/captchaController/captcha.htm?'+Math.random();
	    	obj.src='/denunciaenlinea/captchaController/captcha.htm?'+Math.random();
	    }
	  }
  
  
  function formatoMoneda(idCampo){
	   

	 $(idCampo).unbind();	
	 $(idCampo).blur(function(){
	    
		 if($(idCampo).val()!='0'){
			 $(idCampo).formatCurrency();
		 }else{
			 $(idCampo).val('');
		 }
	        
	 });	
	 
			 $(idCampo).click(function(){
				 if($(idCampo).val()!='' && $(idCampo).val()!='$0.00' && $(idCampo).val()!='0'){
					 $(idCampo).val($(idCampo).asNumber());	
					 $(idCampo).focus();
				 }else{
					 $(idCampo).val('');
				 }			 
			 });
		}
  
  function cargarDocVista(tipoDenunciante){
	  var urlImagen = getAppContextParaJS() + "/servlet/MostrarImagenServlet";
	  var sDenunciaVO = '{' +
	  '"cveDenuncia":"' + objDenuncia.cveDenuncia + '",' +
	  '"tipoDenunciante":"' + tipoDenunciante + '"}';

	  var denuncia = jQuery.parseJSON(sDenunciaVO);
	  $.postJSON(getAppContextParaJS() +"/denuncia/mostrarImagen.do", denuncia, function(data) {
	  }).error(function(data){ 
	  alert("error" + data);
	  }).complete(function(data){ 
		  window.open(urlImagen,"PresentacionCorrecion","menubar=1,resizable=1,width=500,height=500");
	  });
	  return;

  }

  function cargarDocPago(tipoPago,tipoComprobante){
	  var tipo = tipoPago;
	  if(tipoComprobante != null){
		  tipo = tipoComprobante.value;
	  }
	  var urlImagen = getAppContextParaJS() + "/servlet/MostrarImagenServlet";
	  var sDenunciaDTO = '{' +
	  '"dltDenuncia":{"cveFoliodenuncia":"' + objDenuncia.cveDenuncia + '"},' +
	  '"formaPagoVO":{"cveFormaPago":"' + tipo + '"}}';

	  var denuncia = jQuery.parseJSON(sDenunciaDTO);
	  $.postJSON(getAppContextParaJS() +"/denuncia/mostrarImagenPago.do", denuncia, function(data) {
	  }).error(function(data){ 
		  alert("error" + data);
	  }).complete(function(data){ 
		  window.open(urlImagen,"PresentacionCorrecion","menubar=1,resizable=1,width=500,height=500");
	  });
	  return;

}