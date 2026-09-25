
var TIPO_DENUNCIANTE_TRABAJADOR=1;
// Se modifico la estructura del plugin upclick.js para poder recibir una funcion validadora como parametro para q se ejecute antes del submit 

var TIPO_DENUNCIANTE_BENEFICIARIO=2;
var TIPO_DENUNCIANTE_REPLEGAL=3;
var URL_ARCHIVO_PERSONAS="/denuncia/cargaArchivo.do";
var URL_ARCHIVO_FORMA_PAGO="/denuncia/cargaArchivoFormaPago.do";
	
$(document).ready(function(){
	//Datos Trabajador
	var validaCamposTrabajador=getFuncionValida('cmbCveTipodocumentoDT',TIPO_DENUNCIANTE_TRABAJADOR);
	var validaCamposBeneficiario=getFuncionValida('cmbCveTipodocumentoBDT',TIPO_DENUNCIANTE_BENEFICIARIO);
	var validaCamposRepLegal=getFuncionValida('cmbCveTipodocumentoRLDT',TIPO_DENUNCIANTE_REPLEGAL);

	//Datos Forma Pago
	var validaFormaPagoEfectivo=getFuncionValidaFormaPago('#cbxEfectivoDT');
	var validaFormaPagoTransBanc=getFuncionValidaFormaPago('#cbxTransferenciaBancariaDT');
	var validaFormaPagoCheque=getFuncionValidaFormaPago('#cbxChequeDT');
	var validaFormaPagoDepCuentaBanc=getFuncionValidaFormaPago('#cbxDepositoDT');
	var validaFormaPagoOtros=getFuncionValidaFormaPago('#cbxOtrosDT');	
	var validaComprobantePago=getFuncionValidaComprobantePago();
	
	//Se pasa la estructura al plugin para el envio, 
	upclick(generaObjetoArchivo('uploaderTrabajador',validaCamposTrabajador,URL_ARCHIVO_PERSONAS,'txtDesUpload'));
	upclick(generaObjetoArchivo('uploaderBeneficiario',validaCamposBeneficiario,URL_ARCHIVO_PERSONAS,'txtDesUploadBeneficiario'));
	upclick(generaObjetoArchivo('uploaderRepLegal',validaCamposRepLegal,URL_ARCHIVO_PERSONAS,'txtDesUploadRP'));
//	upclick(generaObjetoArchivo('uploaderFormaPagoEfec',validaFormaPagoEfectivo,URL_ARCHIVO_FORMA_PAGO,'txtDesUploadFormaPagoEfec'));
	upclick(generaObjetoArchivo('uploaderFormaPagoTranBanc',validaFormaPagoTransBanc,URL_ARCHIVO_FORMA_PAGO,'txtDesUploadFormaPagoTranBanc'));
	upclick(generaObjetoArchivo('uploaderFormaPagoCheque',validaFormaPagoCheque,URL_ARCHIVO_FORMA_PAGO,'txtDesUploadFormaPagoCheque'));
	upclick(generaObjetoArchivo('uploaderFormaPagoDepoCuenta',validaFormaPagoDepCuentaBanc,URL_ARCHIVO_FORMA_PAGO,'txtDesUploadFormaPagoDepoCuenta'));
	upclick(generaObjetoArchivo('uploaderFormaPagoOtro',validaFormaPagoOtros,URL_ARCHIVO_FORMA_PAGO,'txtDesUploadFormaPagoOtro'));
	upclick(generaObjetoArchivo('uploaderComprobantePago',validaComprobantePago,URL_ARCHIVO_FORMA_PAGO,'txtDesUploadComprobantePago'));
	
	
	/////////////////////////////////////////////////////////////////////////////////////
	///Funciones genericas

		function validaFormulario(){
			   var flag;
				if(isExplorer){
					flag= validarFormaGuardar();
				}else{
					flag= validaGuardar.form();
				}
				return flag;
		}
		   
	 //funcion generica que genera la estructura del objeto para el envio del archivo, recibe el elemento(boton) y la funcion que valida el envio del archivo
	  function generaObjetoArchivo(elemento,funcionValida,url,cajaTexto){
			var archivo={
				      element: document.getElementById(elemento),
					  action: getAppContextParaJS() + url, 
				      valida:funcionValida,
				      onstart:
				        function(filename){	 		    	  
				        },
				      oncomplete:
				        function(response_data){
				    	  if((response_data+'').indexOf("Error")>=0 && (response_data+'').length>50){
				    		   alert("No se pudo adjuntar el documento,favor de reintentar");
				    	  }else if((response_data+'').indexOf("ERROR")>=0){
				    		  alert(response_data);
				    	  }else{
				    		 alert("Archivo "+response_data+" adjuntado exitosamente  ");	 
				    		 var va= document.getElementById(cajaTexto).value=response_data;
				    		 btnMostrarImagen(elemento);
				    		 
				    		 if(elemento=='uploaderTrabajador'){
				    			 objDenuncia.datosTrabajadorVO.trabajador.nombreDocumento=response_data;
				    		 }else if(elemento=='uploaderBeneficiario'){
				    			 objDenuncia.datosTrabajadorVO.beneficiario.nombreDocumento=response_data;
				    		 }else if(elemento=='uploaderRepLegal'){
				    			 objDenuncia.datosTrabajadorVO.representanteLegal.nombreDocumento=response_data;
				    		 }
				    		 
				    	  }
				        }
				     };
			return archivo;
	   }
	   
	  // funcion generica que construye una funcion para validar los componentes requeridos de un denunciante
	   function getFuncionValida(combo,tipoDenunciante){
		   var func=function(form){
				if(document.getElementById(combo).value<0){
					alert("Favor de seleccionar primero el tipo de documento del trabajador");
					return;
				}else{
					if(confirm("Es necesario guardar la denuncia antes de adjuntar archivos,\u00BFDesea guardar la denuncia?")){
						if(validaFormulario()){
							$("#btnGuardar").click();
							var n=(form.action).split("?");
							form.action=n[0];
							form.action=form.action+"?cveDenuncia="+objDenuncia.cveDenuncia+"&cveTipoDocumento="+document.getElementById(combo).value+"&cveTipoDenunciante="+tipoDenunciante
							form.submit();
						}else{
							alert("Tiene campos con error, favor de verificar")
						}
					}
					return;
				}
			}
		   return func;
	   }
	   
	   
	   
	   
	   function getFuncionValidaFormaPago(id){
		   var func=function(form){
			   if($(id).is(':checked')){
				   if(confirm("Es necesario guardar la denuncia antes de adjuntar archivos,\u00BFDesea guardar la denuncia?")){
					   if(validaFormulario()){
						   $("#btnGuardar").click();
						     var n=(form.action).split("?");
							 form.action=n[0];
							 form.action=form.action+"?cveFormaPago="+$(id).val()+"&cveDenuncia="+objDenuncia.cveDenuncia;
							 form.submit();
						}else{
							alert("Tiene campos con error, favor de verificar")
						}					    
				   }	             
			   }else{
				   alert("Seleccione el tipo de documento");
			   } 	
		   }
		   return func;
	   }
	   
	   function getFuncionValidaComprobantePago(){
		   return function(form){
			   if($("#sltCveComprobantePagoIT").val()==-1){
				   alert("Seleccione el tipo de comprobante de pago");
			   }else{ 
				   //if(objDenuncia.cveDenuncia==null || objDenuncia.datosCentroTrabajoVO.pagoComprobantePago.cveFormaPago<=0){
				   if(confirm("Es necesario guardar la denuncia antes de adjuntar archivos,\u00BFDesea guardar la denuncia?")){
					   if(validaFormulario()){
						     $("#btnGuardar").click();
						     var n=(form.action).split("?");
							 form.action=n[0];
							 form.action=form.action+"?cveFormaPago="+$("#sltCveComprobantePagoIT").val()+"&cveDenuncia="+objDenuncia.cveDenuncia;
							 form.submit();	
						}else{
							alert("Tiene campos con error, favor de verificar")
						}
				   }
			   }
//			   else if($("#sltCveComprobantePagoIT").val()!=-1){
//		             var n=(form.action).split("?");
//					 form.action=n[0];
//					 form.action=form.action+"?cveFormaPago="+$("#sltCveComprobantePagoIT").val()+"&cveDenuncia="+objDenuncia.cveDenuncia;
//					 form.submit();			             	
//			   }
		   }
	   }
	   
	   
	   
	   function btnMostrarImagen(elemento){
		   if(elemento == 'uploaderTrabajador'){
			   $("#btnCargar1").show();  
		   }else 
		   if(elemento == 'uploaderBeneficiario'){
			   $("#btnCargar2").show();  
		   }else
		   if(elemento == 'uploaderRepLegal'){
			   $("#btnCargar3").show();  
		   }else
			   if(elemento == 'uploaderComprobantePago'){
				   $("#btnCargar11").show();  
		   }else
			   if(elemento == 'uploaderFormaPagoTranBanc'){
				   $("#btnCargar16").show();  
		   }else
			   if(elemento == 'uploaderFormaPagoCheque'){
				   $("#btnCargar14").show();  
		   }else
			   if(elemento == 'uploaderFormaPagoDepoCuenta'){
				   $("#btnCargar15").show();  
		   }else
			   if(elemento == 'uploaderFormaPagoOtro'){
				   $("#btnCargar17").show();  
			   }
	   
	   }
	   
	  
});