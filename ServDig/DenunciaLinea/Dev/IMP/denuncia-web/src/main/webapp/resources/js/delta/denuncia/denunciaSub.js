var patronesCmpl = [];
var i;
var patronPrincipal = 1;
var validaEnviar;
var validaGuardar;
var validaCapturaTrabajo;
var denunciadoAntes;
var checkboxValues = [];
var oDgPatronesDenunciados;	
var dtPatronesDenunciados;


$(document).ready(function() {
		
	oDgPatronesDenunciados = $("#dgAgregaPatrones").dialog({
	 	autoOpen: false,
	 	modal:true,
	 	resizable:true,
	 	height: 600,
	 	width: 1000
	 });
	
	 jQuery.validator.addMethod('regexp', function(value, element, param) {
	     return this.optional(element) || value.match(param);
	  },'El valor no coincide con la estructura requerida.');

	
     llenaComboPeriodoPago();
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
	$('#md1fechaInicio').prop('disabled','disabled');
    $('#md1fechaFin').prop('disabled','disabled');
    $('#md2fechaInicio').prop('disabled','disabled');
	$('#md2fechaFin').prop('disabled','disabled');
	$('#md3ImporteImss').prop('disabled','disabled');
	$('#md3ImporteReal').prop('disabled','disabled');
    $('#md4fechaInicio').prop('disabled','disabled');		  
	  
	i = 0;
	

	
//	$("#btnLogueo").click(function(e){
//		if(validaLogueo.form()){
//			var CURP = $('input#CURP').val();
//			var pass = $('input#pass').val();
//			var sUsuario = '{' +
//			'"refPassword": "'+pass+'",'+
//			'"nomUsuarioSistema" : "'+CURP+'"}';
//			var usuario = jQuery.parseJSON(sUsuario);
//			 $.postJSON("validaUsuarioInternet.do", usuario, function(data) {				
//					alert("registro exitoso");					  
//				}).error(function(data){ 
//					
//				}).complete(function(){
//					//Instrucciones para el 'complete'
//				}); 	
//			
//		}
//	});
//	
  
//    $("#btnEnviar").click(function(e){
//    	var folio = $('#txtFolioDenunciaDT').val();
//	    var sNumFolioDenuncia = '{' +'"folioDenuncia": "'+ folio + '"}';
//		agregaReglasDenunciaForm();
//		var resultado =validaGuardar.form();
//    	if(resultado){    	
//    	    quitaReglasDenunciaForm();
//      	    var promocion = jQuery.parseJSON(sNumFolioDenuncia);
//	    	jQuery.ajax({
//	    		async: false,
//	    	    type: 'POST',
//	    	    url:  getAppContextParaJS() + '/denuncia/obtenerFechaServidor.do',
//	    	    data: sNumFolioDenuncia, // or JSON.stringify ({name: 'jonas'}),
//	    	    success: function(data) { 	    	    	  
//	    		    	    	
//	    	    	if(folio == ''){
//	    	    		var desObservaciones = $('#txtObs').val();	
//	    	    		var desAclaracion = $('#txtAclDP').val();	
//	    	    		var cveTipodenunciante= $('#cmbCveTipoDenuncianteDT').val();
//	    	    		var cveFoliodenuncia =  $('#hdIdDenuncia').val();
//	    	    		
//	    	    		var scveFoliodenuncia = '"cveFoliodenuncia":'+'"' + cveFoliodenuncia + '"';
//	    	    		var scveTipodenuncianteD = '"cveTipodenunciante":'+'"' + cveTipodenunciante + '"';
//	    	    		var sdesObservaciones =  '"desObservaciones":'+'"' + jQuery.trim(desObservaciones) + '"';
//	    	    		var sdesAclaracion = '"desAclaracion":'+'"' + jQuery.trim(desAclaracion) + '"';
//	    	    		
//	    	    			    	    		
//	    	    		var sDenuncia = '{'+ scveFoliodenuncia +','+ scveTipodenuncianteD +','+ sdesObservaciones +','+ sdesAclaracion +'}';
//
//	    	    		var jDenuncia = jQuery.parseJSON(sDenuncia);
//
//		    	    		$.postJSON( getAppContextParaJS() + "/denuncia/generaDenuncia.do", jDenuncia, function(denuncia) {
//		    					  if(denuncia != null){	    						  
//		    						  $('#hdIdDenuncia').prop('value', denuncia.cveFoliodenuncia);
//		    						  $('#txtFolioDenunciaDT').prop('value', denuncia.numFoliodenuncia);
//		    						  folio = $('#txtFolioDenunciaDT').val();
//		    					  }
//		    				}).error(function(denuncia){ 
//		    					//alert("error generando denuncia" + denuncia);
//		    				}).complete(function(){
//		    					//alert('El registro se ha guardado con exito');
//		    					folio = $('#txtFolioDenunciaDT').val();	    	
//		    					if(folio != '' && folio != null){	    	    	    		
//		    						var cveFoliodenuncia = $('#hdIdDenuncia').val();
//		    	    	    		//guardarDatosPersonas
//		    	    	    		llenaObjetoDatosTrabajador();
//		    	    	    		llenaBeneficiario();
//		    	    	    		llenaRepresentanteLegal();
//		    	    	    		//patron
//		    	    	    		llenaObjetosPatron();
//		    	    	    		//trabajo
//		    	    	    		llenaCentroTrabajo();
//		    	    	    		llenaFormaPago();
//		    	    	    		llenaMotivosDenuncia();
//		    	    	    		
//		    	    	    		$.postJSON(getAppContextParaJS() + "/denuncia/enviar/"+ cveFoliodenuncia+".do",null, function(denuncia) {
//		  	  	    	  			     //alert("regresa de enviar");
//				    	    		}).error(function(denuncia){ 
//				    	    			//alert("error enviando denuncia" + denuncia);
//				    	    		}).complete(function(){
//				    	    				 $('#frmEnviar').attr('action', getAppContextParaJS() + "/denuncia/enviaRatificacion.do");			    	    				 
//				    	    				 $('#frmEnviar').submit();
//				    	    		});
//		    	    	    	}
//		    						    					
//		    				});
//	    	    	    }
//	    	    	
//	    	    	if(folio != ''){
//	    	    		var cveFoliodenuncia = $('#hdIdDenuncia').val();
//	    	    		//guardarDatosPersonas
//	    	    		llenaObjetoDatosTrabajador();
//	    	    		llenaBeneficiario();
//	    	    		llenaRepresentanteLegal();
//	    	    		//patron
//	    	    		llenaObjetosPatron();
//	    	    		//trabajo
//	    	    		llenaCentroTrabajo();
//	    	    		llenaFormaPago();
//	    	    		llenaMotivosDenuncia();
//	    	    		
//	    	    		$.postJSON(getAppContextParaJS() + "/denuncia/enviar/"+ cveFoliodenuncia+".do",null, function(denuncia) {
//	  	    	  			     //alert("regresa de enviar");
//	    	    		}).error(function(denuncia){ 
//	    	    			//alert("error enviando denuncia" + denuncia);
//	    	    		}).complete(function(){
//	    	    				 $('#frmEnviar').attr('action', getAppContextParaJS() + "/denuncia/enviaRatificacion.do");			    	    				 
//	    	    				 $('#frmEnviar').submit();
//	    	    		});
//	    	    	}
//
//	    		},
//	    	    contentType: "application/json"
//		    }).error(function(data){ 
//		    	validarSesionExpirada(data);
//		    }).complete(function(){
//		    	//Instrucciones para el 'complete'
//		    });
//    	 }//validaForm    	
//    });	
    	

	
    

//    $("#btnGuardar").click(function(e){
//    	
//    	if(validaCaptura.form()){
//    	var folio = $('#txtFolioDenunciaDT').val();
//	    var sNumFolioDenuncia = '{' +'"folioDenuncia": "'+ folio + '"}';
//   	    var promocion = jQuery.parseJSON(sNumFolioDenuncia);
//	    jQuery.ajax({
//	    		async: false,
//	    	    type: 'POST',
//	    	    url:   getAppContextParaJS() + '/denuncia/obtenerFechaServidor.do',
//	    	    data:  sNumFolioDenuncia, // or JSON.stringify ({name: 'jonas'}),
//	    	    success: function(data) { 	  
//	    	    		//Denuncia
//	    	    		var folio = $("#txtFolioDenunciaDT").val();
//	    	    		//var observaciones = $("#txtFolioDenunciaDT").val();
//	    	    		//Persona
//	    	    		var denunciante = $('select#cmbCveTipoDenuncianteDT').val();
//	    	    		var nomTrab = $('input#txtDesNombreDT').val();
//	    	    		var apPatTrab = $('input#txtDesPaternoDT').val();
//	    	    		var apMatTrab = $('input#txtDesMaternoDT').val();
//	    	    		var nssTrab = $('input#txtCveNssDT').val();
//	    	    		var curpTrab = $('input#txtCveCurpDT').val();
//	    	    		var rfcTrab = $('input#txtCveRfcDT').val();
//	    	    		var dirTrab = '';
//	    	    		var correoTrab = $('input#txtDesEmailDT').val();
//	    	    		var telTrab = $('input#txtNumTelefonoDT').val();
//	    	    		var celTrab = $('input#numCelular').val();
//	    	    		var tipoDocTrab = $('select#cmbCveTipodocumentoDT').val();
//	    	    		var numDocTrab = $('input#txtRefDocDT').val();
//	    	    		//Motivo de la denuncia
//	    	    		//alert($('checkbox#md1').val());
//	    	    		var motivoDenuncia1 = '';
//	    	    		var md1Fecha1 = '';
//	    	    		var md1Fecha2 = '';
//	    	    		
//	    	    		var motivoDenuncia2 = '';
//	    	    		var md2Fecha1 = '';
//	    	    		var md2Fecha2 = '';
//	    	    		
//	    	    		var motivoDenuncia3 = '';
//	    	    		var salarioReg = '';
//	    	    		var salarioReal = '';
//	    	    		
//	    	    		var motivoDenuncia4 = '';
//	    	    		var md4Fecha1 = '';
//	    	    		
//	    	    		
//	    	    		var observaciones = '';
//	    	    		
//	    	    		//Datos del beneficiario
//	    	    		var nomBen = '';
//	    	    		var apPatBen = '';
//	    	    		var apMatBen = '';
//	    	    		var curpBen = '';
//	    	    		var correoTrabBen = '';
//	    	    		var dirBen = '';
//	    	    		var telBen = '';
//	    	    		var celBen = '';
//	    	    		var tipoDocBen = '';
//	    	    		var numDocBen = '';
//	    	    		
//	    	    		//Datos del Representante Legal
//	    	    		var nomRep = '';
//	    	    		var apPatRep = '';
//	    	    		var apMatRep = '';
//	    	    		var curpRep = '';
//	    	    		var dirRep = '';
//	    	    		var telRep = '';
//	    	    		var celRep = '';
//	    	    		var tipoDocRep = '';
//	    	    		var numDocRep = '';
//	    	    		
//	    	    		//Patron
//	    	    		var razonSocialPat = '';
//	    	    		var domPat = '';
//	    	    		var nomRepLeg = '';
//	    	    		var giro = '';
//	    	    		var sector = '';
//	    	    		var rfcPat = '';
//	    	    		var regPat = '';
//	    	    		var numTrabs = '';
//	    	    		var domFiscal = '';
//	    	    		var telPat = '';
//	    	    		
//	    	    		
//	    	    		
//	    	    		//Centro de Trabajo
//	    	    		var periodoInicio = '';
//	    	    		var periodoFin = '';
//	    	    		var actividad = '';
//	    	    		var nombreJefe = '';
//	    	    		var horarioLab = '';
//	    	    		var salarioCT = '';
//	    	    		var vacacionesCT = '';
//	    	    		var periodoPago = '';
//	    	    		var especiPerPago = '';
//	    	    		var diasVacaciones = '';
//	    	    		var aguinaldoAnual = '';
//	    	    		var diasAguinaldo = '';
//	    	    		var gratificacion = '';
//	    	    		var comisiones = '';
//	    	    		var baseOtorga = '';
//	    	    		var tipoComprobante = '';
//	    	    		var efectivo = '';
//	    	    		var transferencia = '';
//	    	    		var cheque = '';
//	    	    		var otros = '';
//	    	    		var deposito = '';
//	    	    		var fecRiesgo = '';
//	    	    		var observaciones = '';
//	    	    		
//	    	    		//Primero se genera la denuncia
//	    	    		var sDenuncia = '{' +
//	    	    			'"numFoliodenuncia":"'+folio+'",'+
//	    	    			'"cveOrigendenuncia":"2", '+
//	    	    			'"idStatus":"1", '+
//	    	    			'"cveTipodenunciante":"'+denunciante+'", '+
//	    	    			'"motivosDenuncia":[{"cveMotivodenuncia": "'+motivoDenuncia1+'"}] }';
//	    	    		var denuncia = jQuery.parseJSON(sDenuncia);
//	    	    		  $.postJSON("generaDenuncia.do",denuncia , function(data) {
//	    	    			//Se guardan los datos del trabajador
//	  	    	    		$("#txtFolioDenunciaDT").prop("value", data.numFoliodenuncia);
//	  	    	    		//Se guardan los datos del patron
//	  	    	    		var sTrabajador = '{' +
//	  	    	    			'"dlcTipodenunciante":{"cveTipodenunciante":"1"},'+
//	  	    	    			'"cveFoliodenuncia":"'+data.cveFoliodenuncia+'",'+
//	  	    	    			'"desNombre":"'+nomTrab+'",'+
//	  	    	    			'"desPaterno":"'+apPatTrab+'",'+
//	  	    	    			'"desMaterno":"'+apMatTrab+'",'+
//	  	    	    			'"cveNss":"'+nssTrab+'",'+
//	  	    	    			'"cveCurp":"'+curpTrab+'",'+
//	  	    	    			'"cveRfc":"'+rfcTrab+'",'+
//	  	    	    			'"desEmail":"'+correoTrab+'",'+
//	  	    	    			'"numTelefono":"'+telTrab+'",'+
//	  	    	    			'"numCelular":"'+celTrab+'"'+
//	  	    	    		'}';
//	  	    	    		var trabajador = jQuery.parseJSON(sTrabajador);
//	  	    	    		$.postJSON("guardaDatosTrabajador.do", trabajador , function(dataTrabajador) {
//		    	    			
//		    				  }).error(function(dataTrabajador){ 
//		    						alert("error" + dataTrabajador);
//		    					}).complete(function(){
//		    						//Instrucciones para el 'complete'
//		    					});
//	  	    	    		
//	  	    	    		
//	  	    	    		
//	  	    	    		//Se guardan los datos Motivos de Denuncia
//	  	    	    		var sMotivos = '[' +
//  	    	    			'{"cveFoliodenuncia":"'+data.cveFoliodenuncia+'", "cveMotivodenuncia": "1", "fecLabalDejolab" : "'+md1Fecha1+'" , "fecLabdelIngreso": "'+md1Fecha2+'"},'+
//  	    	    			'{"cveFoliodenuncia":"'+data.cveFoliodenuncia+'", "cveMotivodenuncia": "2", "fecLabalDejolab" : "'+md2Fecha1+'" , "fecLabdelIngreso": "'+md2Fecha2+'"},'+
//  	    	    			'{"cveFoliodenuncia":"'+data.cveFoliodenuncia+'", "cveMotivodenuncia": "3", "impSalarioReg" : "'+salarioReg+'" , "impSalarioReal": "'+salarioReal+'"},'+
//  	    	    			'{"cveFoliodenuncia":"'+data.cveFoliodenuncia+'", "cveMotivodenuncia": "4", "fecLabalDejolab" : "'+md1Fecha1+'" }'+
//  	    	    		']';
//  	    	    		var motivos = jQuery.parseJSON(sMotivos);
//  	    	    		$.postJSON("guardaMotivosDenuncia.do", motivos , function(dataMotivos) {
//	    	    			
//	    				  }).error(function(dataMotivos){ 
//	    						alert("error" + dataMotivos);
//	    					}).complete(function(){
//	    						//Instrucciones para el 'complete'
//	    					});
//	  	    	    	
//  	    	    		var sPersona = '';
//  	    	    		if(denunciante == '2' || denunciante == '3'){
//	    	    		if(denunciante == '2'){
//  	    	    		
//  	    	    		 sPersona = '{' +
//	    	    			'"cveTipodenunciante":"'+denunciante+'",'+
//	    	    			'"cveFoliodenuncia":"'+data.cveFoliodenuncia+'",'+
//	    	    			'"desNombre":"'+nomBen+'",'+
//	    	    			'"desPaterno":"'+apPatBen+'",'+
//	    	    			'"desMaterno":"'+apMatBen+'",'+
//	    	    			'"cveCurp":"'+curpBen+'",'+
//	    	    			'"desEmail":"'+correoTrabBen+'",'+
//	    	    			'"numTelefono":"'+telBen+'",'+
//	    	    			'"numCelular":"'+celBen+'",'+
//	    	    			'"refDocumento":"'+numDocBen+'",'+
//	    	    			'"numDocumento":"'+tipoDocBen+'"'+
//	    	    		'}';
//  	    	    		
//	    	    		}else if(denunciante == '3'){
//	    	    			sPersona = '{' +
//	    	    			'"cveTipodenunciante":"'+denunciante+'",'+
//	    	    			'"cveFoliodenuncia":"'+data.cveFoliodenuncia+'",'+
//	    	    			'"desNombre":"'+nomRep+'",'+
//	    	    			'"desPaterno":"'+apPatRep+'",'+
//	    	    			'"desMaterno":"'+apMatRep+'",'+
//	    	    			'"cveCurp":"'+curpRep+'",'+
//	    	    			'"desEmail":"'+correoTrabRep+'",'+
//	    	    			'"numTelefono":"'+telRep+'",'+
//	    	    			'"numCelular":"'+celRep+'",'+
//	    	    			'"refDocumento":"'+numDocRep+'",'+
//	    	    			'"numDocumento":"'+tipoDocRep+'"'+
//	    	    		'}';
//	    	    		}
//  	    	    		
//	    	    		var persona = jQuery.parseJSON(sPersona);
//	    	    		$.postJSON("guardaDatosTrabajador.do", persona , function(dataPersona) {
//    	    			
//    				  }).error(function(dataPersona){ 
//    						alert("error" + dataPersona);
//    					}).complete(function(){
//    						//Instrucciones para el 'complete'
//    					});
//	    	    		
//  	    	    		
//  	    	    		}
//	    					  
//	    					  
//	    				  }).error(function(data){ 
//	    						alert("error" + data);
//	    					}).complete(function(){
//	    						//Instrucciones para el 'complete'
//	    					});
//	    	    		
//	    	    		
//	    	    	 	    	 
//	    		},
//	    	    contentType: "application/json"
//		    }).error(function(data){ 
//		    	validarSesionExpirada(data);
//		    }).complete(function(){
//		    	//Instrucciones para el 'complete'
//		    //	alert("Los datos fueron guardados exitosamente.");
//		    });
//    	}
//	});
    
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
			//alert(sMotivoDenunciaPk);
			
    		$.postJSON(getAppContextParaJS() +"/denuncia/guardaMotivo/"+ cveFoliodenuncia +".do", jMotivoDenunciaPK, function(motivoDenuncia) {				  
				  if(motivoDenuncia != null){	    						  
					  //$('#txtFolioDenunciaDT').prop('value', motivoDenuncia.dltDenuncia.numFoliodenuncia);
				  }
			}).error(function(denuncia){ 
				//alert("error generando denuncia" + denuncia);
			}).complete(function(){
				//alert('El registro se ha guardado con exito');
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
			//alert(sMotivoDenunciaPk);
			
			$.postJSON(getAppContextParaJS() +"/denuncia/guardaMotivo/"+ cveFoliodenuncia +".do", jMotivoDenunciaPK, function(motivoDenuncia) {				  
				  if(motivoDenuncia != null){	    						  
					  //$('#txtFolioDenunciaDT').prop('value', motivoDenuncia.dltDenuncia.numFoliodenuncia);
				  }
			}).error(function(motivoDenuncia){ 
				//alert("error generando denuncia" + denuncia);
			}).complete(function(){
				//alert('El registro se ha guardado con exito');
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
			//alert(sMotivoDenunciaPk);
			
			$.postJSON(getAppContextParaJS() +"/denuncia/guardaMotivo/"+ cveFoliodenuncia +".do", jMotivoDenunciaPK, function(motivoDenuncia) {					  
				  if(motivoDenuncia != null){	    						  
					  //$('#txtFolioDenunciaDT').prop('value', motivoDenuncia.dltDenuncia.numFoliodenuncia);
				  }
			}).error(function(motivoDenuncia){ 
				//alert("error generando denuncia" + denuncia);
			}).complete(function(){
				//alert('El registro se ha guardado con exito');
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
			//alert(sMotivoDenunciaPk);
			
			$.postJSON(getAppContextParaJS() +"/denuncia/guardaMotivo/"+ cveFoliodenuncia +".do", jMotivoDenunciaPK, function(motivoDenuncia) {					  
				  if(motivoDenuncia != null){	    						  
					  //$('#txtFolioDenunciaDT').prop('value', motivoDenuncia.dltDenuncia.numFoliodenuncia);
				  }
			}).error(function(motivoDenuncia){ 
				//alert("error generando denuncia" + denuncia);
			}).complete(function(){
				//alert('El registro se ha guardado con exito');
				folio = $('#txtFolioDenunciaDT').val();	    						    						    				
			});	
		}
			
    }
    
    
    function llenaObjetosPatron(){
    	var idPatronPrincipal = $('#hdIdPatronPrincipal').val();
    	var desNomrazonsocial = $('#txtDesNomrazonsocialDP').val();	
		var domicilioTrabajo = $('#txtDomicilioTrabajoDP').val();	
		var desNomreplegal = $('#txtDesNomreplegalDP').val();	
		var selGiroActividad = $('#cbxSelGiroActividadDP').val();
		var selSector = $('#cbxSelSectorDP').val();
		var desRfc = $('#txtRfcPatronDP').val();
		var cveRegpat = $('#txtCveRegpatDP').val();
		var numTrabajadores = $('#txtNumTrabajadoresDP').val();
		var domicilioId = $('#txtDomFiscalPtrIdDP').val();
		var numTelefono = $('#txtNumTelefonoPatronDP').val();
		
		var sidPatronPrincipal = '"cveDatospatron":'+'"'+ idPatronPrincipal +'"';
		var sDenunciadoAntes= '"indPatrondenunciado":'+'"'+denunciadoAntes+'"';
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
		
		var sPatronPrincipal = '"idPatronprincipal":'+'"' + idPatronPrincipal + '"';
		var idOrdenPatron = ++i;
		var sIdOrdenPatron = '"idOrdenPatron":'+'"'+idOrdenPatron+'"';
		
		
		var strPatron = '{'+ sidPatronPrincipal +','+ sDenunciadoAntes +','+ sDesNomrazonsocial +','+ sDomicilioTrabajo +','+ sDesNomreplegal  
								+','+ sSelGiroActividad  +','+ sSelSector +','+ sDesRfc +','+ sCveRegpat +','+ sNumTrabajadores 
								+','+  sDomicilioId  +','+ sNumTelefono  +',' + sPatronPrincipal +',' + sIdOrdenPatron +'}';

		var jPatron = jQuery.parseJSON(strPatron);
		
		var idDenuncia = $('#hdIdDenuncia').val();
		
		if(idDenuncia == ''){
			alert("Guarde la denuncia antes de agregar patrones");
		}else{
			//alert("patron principal: " + sidPatronPrincipal);
			$.postJSON(getAppContextParaJS() +"/denuncia/agregaPatron/"+ idDenuncia +".do", jPatron, function(patron) {
				if(patron != null){	    						  
					  $('#hdIdPatronPrincipal').prop('value', patron.cveDatospatron);						  
				  }
			}).error(function(data){ 
				//alert("error" + data);
			}).complete(function(patron){
				$('#hdIdPatronPrincipal').prop('value', patron.cveDatospatron);
				//alert($('#hdIdPatronPrincipal').val());
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
    		var scveCURP= '"cveCURP":'+'"'+cveCURP+'"';
    		var scveRfc= '"cveRfc":'+'"'+cveRFC+'"';
    		var scveNss= '"cveNss":'+'"'+cveNSS+'"';
    		var sdesEmail= '"desEmail":'+'"'+desEmail+'"';
    		var snumTelefono= '"numTelefono":'+'"'+numTelefono+'"';
    		var snumCelular= '"numCelular":'+'"'+numCelular+'"';
    		var scveTipoDocumento= '"cveTipoDocumento":'+'"'+cveTipoDocumento+'"';
    		var srefDocumento= '"refDocumento":'+'"'+refDocumento+'"';
    		var snumDocumento= '"numDocumento":'+'"'+numDocumento+'"';
    		
    		var idDenuncia = $('#hdIdDenuncia').val();
    		
    		var sPersona = '{' + sIdPersona +','+ scveTipoDenunciante +','+ sdesNombre +','+ sdesPaterno +','+ sdesMaterno  
			+','+ scveCURP  +','+ scveRfc +','+ scveNss +','+ sdesEmail +','+ snumTelefono 
			+','+  snumCelular  +','+ scveTipoDocumento  +',' + srefDocumento +',' + snumDocumento +'}';

    		
    		var jpersona = jQuery.parseJSON(sPersona);
    		
    		
    		$.postJSON( getAppContextParaJS() +"/denuncia/guardaPersona/"+ idDenuncia +".do", jpersona, function(persona) {
				  //alert(sIdPersona);
				  if(persona != null){	    						  
					  $('#hdIdTrabajador').prop('value', persona.cvePersona);					
				  }
				  
			}).error(function(data){ 
				//alert("error" + data);
			}).complete(function(){
				//alert('El registro se ha guardado con exito');
				//llenar los campos de la persona con la data q se regresa
			});
    		    		
    }
    
	
	function llenaBeneficiario(){
		if($('#cmbCveTipoDenuncianteDT').val() == '2'){
 
			 var idPersona = $('#hdIdBeneficiario').val();
			 var cveTipoDenunciante = '2';
	    	 var desNombre = $('#txtDesNombreBDT').val();
	    	 var desPaterno = $('#txtDesPaternoBDT').val();
	         var desMaterno = $('#txtDesMaternoBDT').val();
	    	 var cveCURP =  $('#txtCveCurpBDT').val();	    		    	 
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
	    	 var scveCURP= '"cveCURP":'+'"'+cveCURP+'"';
	    	 var snumTelefono= '"numTelefono":'+'"'+numTelefono+'"';
	    	 var snumCelular= '"numCelular":'+'"'+numCelular+'"';
	    	 var scveTipoDocumento= '"cveTipoDocumento":'+'"'+cveTipoDocumento+'"';
	    	 var srefDocumento= '"refDocumento":'+'"'+refDocumento+'"';
	    	 var snumDocumento= '"numDocumento":'+'"'+numDocumento+'"';
	    	 
	    	 var sPersonaB = '{' + sIdPersona +','+ scveTipoDenunciante +','+ sdesNombre +','+ sdesPaterno +','+ sdesMaterno  
				+','+ scveCURP  +','+ snumTelefono +','+  snumCelular  +','+ scveTipoDocumento  +',' + srefDocumento +',' + snumDocumento +'}';
	    		

	    		var jpersonaB = jQuery.parseJSON(sPersonaB);
	    		
	    		
	    	 $.postJSON( getAppContextParaJS() +"/denuncia/guardaPersona/"+ idDenuncia +".do", jpersonaB, function(persona) {
	    		 if(persona != null){	    						  
					  $('#hdIdBeneficiario').prop('value', persona.cvePersona);					
				  }				  			
				}).error(function(data){ 
					//alert("error" + data);
				}).complete(function(){
					//alert('El registro se ha guardado con exito');
					//llenar los campos de la persona con la data q se regresa
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
	    	 var cveCURP =  $('#txtCveCurpRLDT').val();	    		    	 
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
	    	 var scveCURP= '"cveCURP":'+'"'+cveCURP+'"';
	    	 var snumTelefono= '"numTelefono":'+'"'+numTelefono+'"';
	    	 var snumCelular= '"numCelular":'+'"'+numCelular+'"';
	    	 var scveTipoDocumento= '"cveTipoDocumento":'+'"'+cveTipoDocumento+'"';
	    	 var srefDocumento= '"refDocumento":'+'"'+refDocumento+'"';
	    	 var snumDocumento= '"numDocumento":'+'"'+numDocumento+'"';
	    	 
	    	 var sPersonaRL = '{' + sIdPersona +',' + scveTipoDenunciante +','+ sdesNombre +','+ sdesPaterno +','+ sdesMaterno  
				+','+ scveCURP  +','+ snumTelefono +','+  snumCelular  +','+ scveTipoDocumento  +',' + srefDocumento +',' + snumDocumento +'}';

	    		
	    	var jpersonaRL = jQuery.parseJSON(sPersonaRL);
	    		
	    		
	    	 $.postJSON( getAppContextParaJS() +"/denuncia/guardaPersona/"+ idDenuncia +".do", jpersonaRL, function(persona) {
	    		 if(persona != null){	    						  
					  $('#hdIdRepLegal').prop('value', persona.cvePersona);					
				  }		  			
				}).error(function(data){ 
					//alert("error" + data);
				}).complete(function(){
					//alert('El registro se ha guardado con exito');
					//llenar los campos de la persona con la data q se regresa
				});
		}		
	}
	

	function llenaCentroTrabajo(){
		
		var fechaInicio = $('#txtFechaInicioTrabajoIT').val();
		var fechaFin = $('#txtFechaFinTrabajoIT').val();	
		var desLaboresdesemp = $('#txtDesLaboresDesempIT').val();	
		var desNumcontrato = $("[name='rdContratoIT']:checked").val();
		var desNomjefeinmediato = $('#txtDesNomJefeInmediatoIT').val();
		var desHorariolabores = $('#txtDesHorariolaboresIT').val();
		var impSalariopercibido = $('#txtImpSalarioPercibidoIT').val();
		var impVacaciones = $('#txtImpVacacionesIT').val();
		var numDiasvacaciones = $('#txtNumDiasVacacionesIT').val();
		var impAguinaldo = $('#txtImpAguinaldoIT').val();
		var diasAguinaldo = $('#txtDiasAguinaldoIT').val();
		var gratificacion = $('#txtImpGratificcionIT').val();	
		var desBaseComisionOtros = $('#txtDesBaseComisionOtrosIT').val();
		//var baseOtorgamiento= $('#txtBaseOtorgamientoIT').val();
		var riesgoTrabajo = $('#rdRiesgo').val();
		var fecFechariesgotrab = $('#txtFecFechaRiesgoTrabIT').val();
		var desObservaciones = $('#desObservacionesIT').val();
				
		var sFechaInicio= '"fechaInicio":'+'"'+fechaInicio+'"';
		var sFechaFin= '"fechaFin":'+'"'+fechaFin+'"';
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
		var sBaseOtorgamiento= '"baseOtorgamiento":'+'"'+baseOtorgamiento+'"';
		var sRiesgoTrabajo= '"riesgoTrabajo":'+'"'+riesgoTrabajo+'"';
		var sFecFechariesgotrab= '"fecFechariesgotrab":'+'"'+fecFechariesgotrab+'"';
		var sDesObservaciones= '"desObservaciones":'+'"'+desObservaciones+'"';
		
		var idDenuncia = $('#hdIdDenuncia').val(); 
		
		var strTrabajo = '{'+ sDesLaboresdesemp +','+ sDesNumcontrato +','+ sDesNomjefeinmediato +','+ sDesHorariolabores  
		                   +','+ sImpSalariopercibido  +','+ sImpVacaciones +','+ sNumDiasvacaciones +','+ sImpAguinaldo +','+ sDesBaseComisionOtros 
		                   +','+  sFecFechariesgotrab  +','+ sDesObservaciones + '}';
		
		var trabajo = jQuery.parseJSON(strTrabajo);
		
		$.postJSON(getAppContextParaJS() + "/denuncia/datosTrabajoMain/guardaCentroTrabajo/" + idDenuncia +".do", trabajo, function(data) {
			  //alert('se guardo');
		}).error(function(data){ 
			//alert("error" + data);
		}).complete(function(){
			//alert('El registro se ha guardado con exito');
		});	
	}
	
	function llenaFormaPago(){
		
	
		var periodoPago = $('#sltCvePeriodoPagoIT').val();				//
		var desEspecifiquePP = $('#txtDesEspecifiquePPIT').val();
		
		var comprobantePago = $('#sltCveComprobantePagoIT').val();			//
		var desEspecifiqueCP = $('#txtDesEspecifiqueCPIT').val();
		checkBoxSeleccionados(); //asignado al arreglo checkboxValues
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
			  //alert('se guardo');
		}).error(function(data){ 
			//alert("error" + data);
		}).complete(function(){
			//alert('El registro se ha guardado con exito');
		});	
		
	}
	
	
	$.postJSON( getAppContextParaJS() +"/denuncia/obtenerFechaServidor.do", null,null).error(function(data){
			//alert("error sacando fecha servidor");
		}).complete(function(data){
				$('#hdFechaServidor').attr('value',data.responseText);
				$('#fechaLabelID').html(data.responseText );					
				
				//$("#md1fechaInicio,#md1fechaFin, #md2fechaInicio,#md2fechaFin,#md4fechaInicio,#txtFechaInicioTrabajoIT,#txtFechaFinTrabajoIT,#txtFecFechaRiesgoTrabIT,#txtFechaNacimientoDT").datepicker( { dateFormat: 'dd-mm-yy',
					$("#md1fechaInicio,#md1fechaFin, #md2fechaInicio,#md2fechaFin,#md4fechaInicio,#txtFechaInicioTrabajoIT,#txtFechaFinTrabajoIT,#txtFecFechaRiesgoTrabIT").datepicker( { dateFormat: 'dd-mm-yy',
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
	 	 
	  
	
	 
	 
	 var validaCaptura = $("#denunciaSubForm").validate({
	  	  rules: {
	  		txtDesNombreDT: {
	  			alphanumeric: true,
	  			maxlength : 50
	  			 
	  	    },
	  	  txtDesPaternoDT: {
	  		alphanumeric: true,
  			maxlength : 50
	  	  },
	  	txtDesMaternoDT: {
	  		alphanumeric: true,
  			maxlength : 50
	  	  },
	  	txtCveNssDT : {
	  		digits: true,
//	  		required: function(element) {
//	  				return (document.getElementById('txtCveNssDT').value != '');
//	  		 },
	  		minlength : 11,
  			maxlength : 11
	  	},
	  	txtCveCurpDT : {
	  		alphanumeric: true,
	  		required: function(element) {
  				return (document.getElementById('txtCveCurpDT').length > 0);
	  		},
	  		minlength : 18,
  			maxlength : 18
	  	}, 
	  	txtCveRfcDT : {
	  		alphanumeric: true,
	  		required: function(element) {
  				return (document.getElementById('txtCveRfcDT').length > 0);
	  		},
	  		minlength : 12,
  			maxlength : 13
	  	},
	  	txtDesEmailDT : {
	  		email : true,
	  		maxlength : 50
	  	}, txtNumTelefonoDT : {
	  		digits: true,
	  		required: function(element) {
  				return (document.getElementById('txtNumTelefonoDT').length > 0);
	  		},
	  		minlength : 12,
  			maxlength : 12
	  	},numCelular :{
	  		digits: true,
	  		required: function(element) {
  				return (document.getElementById('numCelular').length > 0);
	  		},
	  		minlength : 10,
  			maxlength : 10
	  	},
	  	txtRefDocDT: {
	  		alphanumeric: true,
  			maxlength : 80
	  	},
	  	md1fechaInicio : {
	  		required: function(element) {
  				return (document.getElementById('md1').checked == true);
	  		}
	  	}
	  	
	  		
	  	}
	  	 	
	  }); 
	 
	
	 
	  cargaDenuncia();

	  var oTable = $(dtPatronesDenunciados).dataTable();
	  oTable.fnDraw();
	
}); 

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
function irRegistro(){
	$("#registroForm").submit(); 
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
        if( jsIsDiaHabil(fechaVigenciaDenuncia) && !jsIsDiaFestivo(fechaVigenciaDenuncia)){ //IsDiaHabil
           i = i+1;
        }
    }
    var fechaVigencia = new Date(fechaVigenciaDenuncia);
    //alert(fechaVigencia);
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


/*function consultaDenuncia(statusDenuncia){	
	alert("La denuncia se encuentra en estatus: " + statusDenuncia);
}*/

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



function agregaCentroTrabajo(){
	    
	    var cveInfoTrabajo = $('#hdIdDatosTrabajo').val();		
	    var fechaInicio = $('#txtFechaInicioTrabajoIT').val();
		var fechaFin = $('#txtFechaFinTrabajoIT').val();	
		var actividadDesempenaba = $('#txtDesLaboresDesempIT').val();	
		//var cuentaContrato = $('#rdContratoIT').val();
		var cuentaContrato = $("[name='rdContratoIT'] option:selected").html();
		var nomJefeInmediato = $('#txtDesNomJefeInmediatoIT').val();
		var horarioLabores = $('#txtDesHorariolaboresIT').val();
		var salario = $('#txtImpSalarioPercibidoIT').val();
		var vacaciones = $('#txtImpVacacionesIT').val();
		var periodoPago = $('#sltCvePeriodoPagoIT').val();
		var especPeriodoPago = $('#txtDesEspecifiquePPIT').val();
		var diasVacaciones = $('#txtNumDiasVacacionesIT').val();
		var aguinaldo = $('#txtImpAguinaldoIT').val();
		var diasAguinaldo = $('#txtDiasAguinaldoIT').val();	
		var gratificacion = $('#txtImpGratificcionIT').val();	
		var comisiones = $('#txtDesBaseComisionOtrosIT').val();	
		//var baseOtorgamiento= $('#txtBaseOtorgamientoIT').val();
		var comprobantePago = $('#sltCveComprobantePagoIT').val();
		var especComprobantePago = $('#txtDesEspecifiqueCPIT').val();
		var formaPago = $('#cbxEfectivo').val();		//TODO:check how getting checkbox selected
		var especFormaPago = $('#txtDesEspecifiqueFPIT').val();
		var riesgoTrabajo = $('#rdRiesgo').val();
		var fechaRiesgo = $('#txtFecFechaRiesgoTrabIT').val();
		var observaciones = $('#desObservacionesIT').val();

		var scveInfoTrabajo = '"cveInfoTrabajo":'+'"'+ cveInfoTrabajo +'"';
		var sFechaInicio= '"fechaInicio":'+'"'+fechaInicio+'"';
		var sFechaFin= '"fechaFin":'+'"'+fechaFin+'"';
		var sActividadDesempenaba= '"actividadDesempenaba":'+'"'+actividadDesempenaba+'"';
		var sCuentaContrato= '"cuentaContrato":'+'"'+cuentaContrato+'"';
		var sNomJefeInmediato= '"nomJefeInmediato":'+'"'+nomJefeInmediato+'"';
		var sHorarioLabores= '"horarioLabores":'+'"'+horarioLabores+'"';
		var sSalario= '"salario":'+'"'+salario+'"';
		var sVacaciones= '"vacaciones":'+'"'+vacaciones+'"';
		var sPeriodoPago= '"periodoPago":'+'"'+periodoPago+'"';
		var sEspecPeriodoPago= '"especPeriodoPago":'+'"'+especPeriodoPago+'"';
		var sDiasVacaciones= '"diasVacaciones":'+'"'+diasVacaciones+'"';
		var sAguinaldo= '"aguinaldo":'+'"'+aguinaldo+'"';
		var sDiasAguinaldo= '"diasAguinaldo":'+'"'+diasAguinaldo+'"';
		var sGratificacion= '"gratificacion":'+'"'+gratificacion+'"';
		var sComisiones= '"comisiones":'+'"'+comisiones+'"';
		var sBaseOtorgamiento= '"baseOtorgamiento":'+'"'+baseOtorgamiento+'"';
		var sComprobantePago= '"comprobantePago":'+'"'+comprobantePago+'"';
		var sEspecComprobantePago= '"especComprobantePago":'+'"'+especComprobantePago+'"';
		var sFormaPago= '"formaPago":'+'"'+formaPago+'"';
		var sEspecFormaPago= '"especFormaPago":'+'"'+especFormaPago+'"';
		var sRiesgoTrabajo= '"riesgoTrabajo":'+'"'+riesgoTrabajo+'"';
		var sFechaRiesgo= '"fechaRiesgo":'+'"'+fechaRiesgo+'"';
		var sObservaciones= '"observaciones":'+'"'+observaciones+'"';
		
		var strTrabajo = '{'+ scveInfoTrabajo +','+ sFechaInicio +','+ sFechaFin +','+ sActividadDesempenaba +','+ sCuentaContrato  
		                   +','+ sNomJefeInmediato  +','+ sHorarioLabores +','+ sSalario +','+ sVacaciones +','+ sPeriodoPago 
		                   +','+  sEspecPeriodoPago  +','+ sDiasVacaciones  +',' + sAguinaldo + sDiasAguinaldo +','+ sDiasAguinaldo 
		                   +','+ sGratificacion +','+ sComisiones + sBaseOtorgamiento +','+ sComprobantePago +','+ sEspecComprobantePago 
		                   +','+ sFormaPago + sEspecFormaPago +','+ sRiesgoTrabajo +','+ sFechaRiesgo +','+ sObservaciones +'}';
		
		var trabajo = jQuery.parseJSON(strTrabajo);
		
		$.postJSON(getAppContextParaJS() + "/denuncia/datosTrabajoMain/guardaCentroTrabajo.do", trabajo, function(data) {
			  //alert('se guardo');
              $('#hdIdDatosTrabajo').prop('value', data.cveInfotrabajo);
		}).error(function(data){ 
			//alert("error" + data);
		}).complete(function(){
			//alert('El registro se ha guardado con exito');
		});
	

}

/*
function limpiaTabPatron(){
	$('#txtDesNomrazonsocialDP').prop('value', '');
	$('#txtDesNomrazonsocialDP').prop('value', '');	
	$('#txtDomicilioTrabajoDP').prop('value', '');	
	$('#txtDesNomreplegalDP').prop('value', '');
	$('#cbxSelGiroActividadDP').prop('value', '-1');
	$('#cbxSelSectorDP').prop('value', '-1');
	$('#txtRfcPatronDP').prop('value', '');
	$('#txtCveRegpatDP').prop('value', '');
	$('#txtNumTrabajadoresDP').prop('value', '');
	$('#txtDomFiscalPtrIdDP').prop('value', '');
	$('#txtNumTelefonoPatronDP').prop('value', '');
	
	$("input:radio[name=rdPatronPrincipalDP]").prop('value', '');

    $("input:radio[name=rdDenunciadoAntesDP]").prop('value', '');
}*/

//function opcionOtro(field){	
//	var id = field.id;	
//	if(id == "sltCvePeriodoPago"){
//		var otro = $('#' + id + ' option:selected').html();
//		var valor = field.value;
//		if(otro != "OTROS"){
//			$('.opcionalP').hide()
//		} else{
//			$('.opcionalP').show()
//		}
//	
//	} else if(id == "sltCveComprobantePago"){
//		var otro = $('#' + id + ' option:selected').html();
//		var valor = field.value;
//		if(otro != "OTROS"){
//			$('.opcionalC').hide()
//		} else{
//			$('.opcionalC').show()
//		}
//	} else if(id == "") {
//		
//	}
//}

function llenaComboPeriodoPago(){
	$.postJSON(getAppContextParaJS() + "/denuncia/datosTrabajoMain/consultaPeriodoPago.do", '', function(datas) {
		 var options = "<option value='-1' >--Por favor seleccione--</option>";
		 if(datas!=null)
		   for (var i = 0; i < datas.length; i++) {
	         options += "<option value='"+ datas[i].cveFormapago +"'>"+ datas[i].descFormapago +"</option>";		     
	       }
		 $('select#sltCvePeriodoPagoIT').html(options);
		//Agrega las opciones al control
	}).error(function(datas){ 
		(datas);
	}).complete(function(){
		//Instrucciones para el 'complete'
	});

	
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

//Dialog de Agregar Patrones		
function openDgAgregarPatronesDenunciados(){
	oDgPatronesDenunciados.dialog('open');
}

function agregaReglasDenunciaForm(){
	
		//alert('pasa por aca');
		$("#cmbCveTipoDenuncianteDT").rules("add", "required");
		$("#txtDesNombreDT").rules("add", "required");
		$("#txtDesPaternoDT").rules("add", "required");
		$("#txtDesMaternoDT").rules("add", "required");
		///$("#txtCveNssDT").rules("add", "required");
		$("#txtCveCurpDT").rules("add", "required");
		$("#txtCveRfcDT").rules("add", "required");
		$("#txtNumTelefonoDT").rules("add", "required");
		$("#txtDesEmailDT").rules("add", "required");
		$("#cmbCveTipodocumentoDT").rules("add", "required");
		$("#rdDenunciadoAntesDP").rules("add", "required"); //		
		$("#txtDesNomrazonsocialDP").rules("add", "required");
		//$("#cbxSelGiroActividadDP").rules("add", "required");
		$("#cbxSelSectorDP").rules("add", "required");
		$("#txtNumTrabajadoresDP").rules("add", "required");	
  }
  
 function quitaReglasDenunciaForm(){
	  $("#cmbCveTipoDenuncianteDT").rules("remove", "required");
	  $("#txtDesNombreDT").rules("remove", "required");
	  $("#txtDesPaternoDT").rules("remove", "required");
	  //$("#txtDesMaternoDT").rules("remove", "required");
	  $("#txtCveCurpDT").rules("remove", "required");
      $("#txtCveRfcDT").rules("remove", "required");
	  $("#txtNumTelefonoDT").rules("remove", "required");
	  $("#txtDesEmailDT").rules("remove", "required");
	  $("#cmbCveTipodocumentoDT").rules("remove", "required");
	  $("#rdDenunciadoAntesDP").rules("remove", "required"); //	
	  $("#txtDesNomrazonsocialDP").rules("remove", "required");
	  //$("#cbxSelGiroActividadDP").rules("remove", "required");
	  //$("#cbxSelSectorDP").rules("remove", "required");
	  $("#txtNumTrabajadoresDP").rules("remove", "required");
	  $("#txtCveNssDT").rules("remove", "required");
	  
  }  

function cargaDenuncia(){
	var idDenunciaRdSelect = $('#hdIdDenunciaCarga').val();
	var scveFoliodenuncia = '"cveFoliodenuncia":'+'"' + idDenunciaRdSelect + '"';	
	var sDenuncia = '{'+ scveFoliodenuncia +'}';
	var jDenuncia = jQuery.parseJSON(sDenuncia);
	
	//jQuery.ajaxSetup({async:false});
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
    					//alert("error generando denuncia" + denuncia);
    				}).complete(function(){ 
    					//alert("complete cargando denuncia" + denuncia);
    				});
    				
    				$.postJSON(getAppContextParaJS() + "/denuncia/cargaPersonas.do", jDenuncia, function(personas) {
						  if(personas != null){	    						  			  
							  for(i=0; personas.length; i++){
								  var tipoDenunciante =personas[i].cveTipodenunciante; 
								  //alert( personas[i].cveTipodenunciante);
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
							    		$('#txtCveCurpBDT').prop('value', personas[i].cveCurp);
							    		$('#txtNumTelefonoBDT').prop('value', personas[i].cveRfc);
							    		$('#txtNumTelefonoMblBDT').prop('value', personas[i].cveNss);
							    		$('#cmbCveTipodocumentoBDT').prop('value', personas[i].cveTipodocumento);
							    		$('#txtNumTelefonoDT').prop('value', personas[i].numTelefono);
							    		if(personas[i].cveTipodocumento != '' && personas[i].cveTipodocumento != null){
							    			$("#cmbCveTipodocumentoBDT").prop('value',personas[i].cveTipodocumento);   
							    		}
							    		$('#txtNumDocumentoBDT').prop('value', personas[i].numDocumento);			    									    		
								   }
								   if(tipoDenunciante == '3'){
									    $('#hdIdRepLegal').prop('value', personas[i].cvePersona);					    		   
							    		$('#txtDesNombreBDT').prop('value', personas[i].desNombre);
							    		$('#txtDesPaternoBDT').prop('value', personas[i].desPaterno);
							    		$('#txtDesMaternoBDT').prop('value', personas[i].desMaterno);
							    		$('#txtCveCurpBDT').prop('value', personas[i].cveCurp);
							    		$('#txtNumTelefonoBDT').prop('value', personas[i].cveRfc);
							    		$('#txtNumTelefonoMblBDT').prop('value', personas[i].cveNss);
							    		$('#cmbCveTipodocumentoBDT').prop('value', personas[i].cveTipodocumento);
							    		$('#txtNumTelefonoDT').prop('value', personas[i].numTelefono);
							    		if(personas[i].cveTipodocumento != '' && personas[i].cveTipodocumento != null){
							    			$("#cmbCveTipodocumentoRLDT").prop('value',personas[i].cveTipodocumento);   
							    		}
							    		$('#txtNumDocumentoBDT').prop('value', personas[i].numDocumento);	
								   }
								  
						  		}
					  		}
					}).error(function(){ 
						//alert("error cargando personas");
					}).complete(function(){ 
						//alert("complete cargando denuncia" + personas);
					});
    				
    				
    				$.postJSON(getAppContextParaJS() + "/denuncia/cargaPatronPrincipal.do", jDenuncia, function(patron) {
    					if(patron != null){	    
    						$('#hdIdPatronPrincipal').prop('value', patron.cveDatospatron);
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
    						$('#txtRfcPatronDP').prop('value', patron.desRfc);
    						$('#txtCveRegpatDP').prop('value', patron.cveRegpat);
    						$('#txtNumTrabajadoresDP').prop('value', patron.numTrabajadores);
    						$('#txtNumTelefonoPatronDP').prop('value', patron.numTelefono);
    						$('input:radio[name="rdPatronPrincipalDP"]').filter('[value="1"]').attr('checked', true);
    					}
					}).error(function(jqXHR, textStatus, errorThrown) { 
//					        console.log("error " + textStatus); 
//					        console.log("incoming Text " + jqXHR.responseText); 
					   }) .complete(function(){ 
						//alert("complete cargando denuncia" + personas);
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
						//alert("error cargando personas");
					}).complete(function(){ 
						//alert("complete cargando denuncia" + personas);
					});
    				
    				
    				$.postJSON(getAppContextParaJS() + "/denuncia/cargaDatosTrabajo.do", jDenuncia, function(trabajo) {
    					if(trabajo != null){	    
    						$('#hdIdDatosTrabajo').prop('value', trabajo.cveInfotrabajo);
    						//$('#txtFechaInicioTrabajoIT').prop('value',trabajo.txtFechaInicioTrabajoIT);
    						//$('#txtFechaFinTrabajoIT').prop('value', trabajo.txtFechaFinTrabajoIT);
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
    						//$('#txtImpGratificcionIT').prop('value',trabajo.);
    						$('#txtDesBaseComisionOtrosIT').prop('value',trabajo.impComisionOtros);
    						//$('#txtBaseOtorgamientoIT').prop('value',trabajo.desBaseComisionOtros);
    						$('#txtFecFechaRiesgoTrabIT').prop('value',trabajo.fecFechariesgotrab);
    						
    					}
					}).error(function(){ 
						//alert("error cargando personas");
					}).complete(function(){ 
						//alert("complete cargando denuncia" + personas);
						var idInfoTrabajo = $('#hdIdDatosTrabajo').val();
						var scveInfotrabajo = '"cveInfotrabajo":'+'"' + idInfoTrabajo + '"';	
						var sInfotrabajo = '{'+ scveInfotrabajo +'}';
						//alert(sInfotrabajo);
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
	    							
	    							if(formasPago[i].id.cveConcepto == '2'){ // llena comprobantes pago	    							
	    								$('#sltCveComprobantePagoIT').prop('value', formasPago[i].id.cveFormapago);
	    								if(formasPago[i].id.cveFormapago == '11'){	    									
	    									$('#txtDesEspecifiqueCPIT').prop('value', formasPago[i].desEspecifique);
	    								}
	    							}	
	    							if(formasPago[i].id.cveConcepto == '3'){
	    								var idCheck = "[value='" + formasPago[i].id.cveFormapago + "']";
	    								//$(idCheck).prop('checked', true);
	    								 $('input:checkbox[name="cbxFormaPago"]').filter(idCheck).attr('checked', true);
	    								
	    								if(formasPago[i].id.cveFormapago == '16'){	    									
	    									$('#txtDesEspecifiqueFPIT').prop('value', formasPago[i].desEspecifique);
	    								}
	    							} // llena formas pago	    									    						
	    						}	    						
	    					}
						}).error(function(){ 
							//alert("error cargando personas");
						}).complete(function(){ 
							//alert("complete cargando denuncia" + personas);
						});		
					});
    				    				
		},
	    contentType: "application/json"
    }).error(function(data){ 
    	validarSesionExpirada(data);
    }).complete(function(){
    	//Instrucciones para el 'complete'
    });
   
   }
	
}

function validaFechaFinDeNoAfiliacion() {
  	var mensaje_errorFechaFinal="<label class='etiquetaError'>La fecha final no puede ser menor a la fecha inicial</label>";	  	
  	var fechaInicio=$("form#denunciaForm #md1fechaInicio").val();	
  	var fechaFin=$("form#denunciaForm #md1fechaFin").val();	  	
  	$("form#denunciaForm #labelfechaFinNoAfil").html("");
  	if (fechaInicio==""){
  		$("form#denunciaForm #md1fechaFin").val("");
  		alert ("Verifique que exista la fecha inicial");
  	} else if (fechaFin!="" && !comparaFechas(fechaInicio, fechaFin, '-')){
  		$("form#denunciaForm #labelfechaFinNoAfil").html(mensaje_errorFechaFinal);
  		$("form#denunciaForm #md1fechaFin").val("");
  	}  else if(fechaFin!=''){
  	}
  }
  
  function validaFecFinAfiliacionPost() {
	  	var mensaje_errorFechaFinal="<label class='etiquetaError'>La fecha final no puede ser menor a la fecha inicial</label>";		  	
	  	var fechaInicio=$("form#denunciaForm #md2fechaInicio").val();	
	  	var fechaFin=$("form#denunciaForm #md2fechaFin").val();	
	  	
	  	$("form#denunciaForm #labelfechaFinAfilPost").html("");
	  	if (fechaInicio==""){
	  		$("form#denunciaForm #md2fechaFin").val("");
	  		alert ("Verifique que exista la fecha inicial");
	  	} else if (fechaFin!="" && !comparaFechas(fechaInicio, fechaFin, '-')){
	  		$("form#denunciaForm #labelfechaFinAfilPost").html(mensaje_errorFechaFinal);
	  		$("form#denunciaForm #md2fechaFin").val("");
	  	}
  }
  
  function validaFecFinTrabajo() {
	  	var mensaje_errorFechaFinal="<label class='etiquetaError'>La fecha final no puede ser menor a la fecha inicial</label>";		  	
	  	var fechaInicio=$("form#denunciaForm #txtFechaInicioTrabajoIT").val();	
	  	var fechaFin=$("form#denunciaForm #txtFechaFinTrabajoIT").val();	
	  	
	  	$("form#denunciaForm #labelfechaFinTrabajo").html("");
	  	if (fechaInicio==""){
	  		$("form#denunciaForm #txtFechaFinTrabajoIT").val("");
	  		alert ("Verifique que exista la fecha inicial");
	  	} else if (fechaFin!="" && !comparaFechas(fechaInicio, fechaFin, '-')){
	  		$("form#denunciaForm #labelfechaFinTrabajo").html(mensaje_errorFechaFinal);
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
		    }else{
		    	$.postJSON(getAppContextParaJS() +"/denuncia/agregaPatronComplemento/"+ idDenuncia +".do", jPatron, function(patron) {
	  				alert("Los datos fueron guardados exitosamente.");
			  }).error(function(data){ 
			  		//alert("error" + data);
			  }).complete(function(patron){		  		
			  		//alert($('#hdIdPatronPrincipal').val());
			  });
		    }
	   }	  	
    }

  function showNss(){
	  $("#tblNss").show();
  }
  
  function hideNss(){
	  $("#tblNss").hide();
  }
  
  function habilitaParams(value){
	  if(value == '1'){		  
		  var chec = $('#md1').val();
		  $('#md1fechaInicio').prop('disabled','');
		  $('#md1fechaFin').prop('disabled','');
		  
	  }
	  if(value == '2'){
		  $('#md2fechaInicio').prop('disabled','');
		  $('#md2fechaFin').prop('disabled','');
	  }
	  if(value == '3'){
		  $('#md3ImporteImss').prop('disabled','');
		  $('#md3ImporteReal').prop('disabled','');
	  }
	  if(value == '4'){
		  $('#md4fechaInicio').prop('disabled','');		  
	  }

  }