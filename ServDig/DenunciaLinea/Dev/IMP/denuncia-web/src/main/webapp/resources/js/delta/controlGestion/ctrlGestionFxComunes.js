
/**
 * Variable que controlan el flujo de los pagos
 */

var PARCIALIDADES_COMPLETAS = "PARCIALIDADES_COMPLETAS";
var PAGOS_COMPLETOS = "PAGOS_COMPLETOS";
var ADEUDO_COP = "ADEUDO_COP";
var ADEUDO_RCV = "ADEUDO_RCV";
var ADEUDO = "ADEUDO";
var TIPO_ADEUDO = "";



function validaSolicitudPago(numFolio, newCOP, newRCV, modulo){
	 var numFolio = document.getElementById("txtFolio").value;
	 var sFolio = '{"folio": "'+numFolio+'" }';
	 var parcialidades =  '';
	 var suertePrincipalCOP = '';
	 var suertePrincipalRCV = '';
	 if(modulo == 'promocion'){
		 parcialidades = document.getElementById('txtNoParcialidades').value;
		 suertePrincipalCOP = document.getElementById('txtCOPConvSP').value;
		 suertePrincipalRCV = document.getElementById('txtRCVConvSP').value;
	 }else if(modulo == 'autodeterminacion'){
		 parcialidades = document.getElementById('txtANoParcialidades').value;
		 suertePrincipalCOP = document.getElementById('txtACOPConvSP').value;
		 suertePrincipalRCV = document.getElementById('txtARCVConvSP').value;
	 }else if(modulo == 'revision'){
		 parcialidades = document.getElementById('txtRNoParcialidades').value;
		 suertePrincipalCOP = document.getElementById('txtRCOPConvSP').value;
		 suertePrincipalRCV = document.getElementById('txtRRCVConvSP').value;
	 }
	 
	 var  resultado = ADEUDO;
	 var errorOcurrido;
	 	 
	 $.ajax({
	        url: getAppContextParaJS()+"/controlGestion/promocion/consultaAnexoPagos.do",
	        async:false,
	        contentType: "application/json",
	        data: sFolio,
	        error: function(objeto, quepaso, otroobj){
	        	errorOcurrido = otroobj;
	        	resultado = "ERROR EN LA OPERACION";
	        	
	       
	        	//VALIDAR SI ES FORBIDEN o hacer el return para avisar
	        	
	        },
	        success: function(data){
				 
				 if(data!=null && data!=""){	
					 if(parcialidades!=0 && parcialidades!="" && parcialidades!=undefined && data.length==parcialidades){
						 alert("No puede registrar m\u00E1s parcialidades");
						 resultado=  PARCIALIDADES_COMPLETAS;
					 }else{
						 var totalcopsp=0;
						 var totalcoprec=0;
						 var totalcopact=0;
						 
						 var totalrcvsp=0;
						 var totalrcvrec=0;
						 var totalrcvact=0;
						 
						 var sumCOP=0;
						 var sumRCV=0;
						 
						 var totalMultasCOP = 0;
						 var totalMultasRCV = 0;
						 for (var i = 0; i < data.length; i++) {
							 
							 totalcopsp = totalcopsp + data[i].copsp;
							 totalcoprec = totalcoprec + data[i].coprec;
							 totalcopact = totalcopact + data[i].copact;
							 
							 totalrcvsp = totalrcvsp + data[i].rcvsp;
							 totalrcvrec = totalrcvrec + data[i].rcvrec;
							 totalrcvact =totalrcvact + data[i].rcvact;
							 
							 totalMultasCOP = totalMultasCOP + data[i].copmultas;
							 totalMultasRCV = totalMultasRCV + data[i].rcvmultas;
							 							
						   }
						 
						 sumCOP = totalcopsp +  totalcoprec + totalcopact + totalMultasCOP + newCOP;
						 sumRCV = totalrcvsp +  totalrcvrec + totalrcvact + totalMultasRCV + newRCV;
						 
						 sumCOP1 = totalcopsp +  totalcoprec + totalcopact + totalMultasCOP;
						 sumRCV1 = totalrcvsp +  totalrcvrec + totalrcvact + totalMultasRCV;
						 
						 suertePrincipalCOP = replaceAll(suertePrincipalCOP,",","");
						 suertePrincipalCOP = replaceAll(suertePrincipalCOP,"$","");
						 
						 suertePrincipalRCV = replaceAll(suertePrincipalRCV,",","");
						 suertePrincipalRCV = replaceAll(suertePrincipalRCV,"$","");
					 	 
						 if(Number(sumCOP)<Number(suertePrincipalCOP) && Number(sumRCV)<Number(suertePrincipalRCV)){
							 resultado = ADEUDO;
							 return false;
						 }if(Number(sumCOP)>Number(suertePrincipalCOP) || Number(sumRCV)>Number(suertePrincipalRCV)){
							 alert("No puede registrar un pago adicional dado que la SPD de alguno de los conceptos (COP o RCV) excede su monto");
							 resultado = PAGOS_COMPLETOS;
							 return false;
						 }
					 }
				 }else {
					 suertePrincipalCOP = replaceAll(suertePrincipalCOP,",","");
					 suertePrincipalCOP = replaceAll(suertePrincipalCOP,"$","");
					 
					 suertePrincipalRCV = replaceAll(suertePrincipalRCV,",","");
					 suertePrincipalRCV = replaceAll(suertePrincipalRCV,"$","");
					 if(Number(newCOP) >  Number(suertePrincipalCOP) || Number(newRCV) > Number(suertePrincipalRCV)){
						 alert("No puede registrar el pago dado que la SPD de alguno de los conceptos (COP o RCV) es inferior al monto de su pago");
						 resultado = PAGOS_COMPLETOS;
						 return false;
					 }else{
						 resultado = ADEUDO;
					 }
					 
				 }
				 
	        },
	        type: "POST"
	});
	
	if (errorOcurrido=="Forbidden")  {
		alert('Su sesi\u00F3n ha expirado.');
		window.location.reload(true);
	}
	
	return resultado;
	
}

function validaTipoPago(tipoPago){
	
  if(tipoPago==ADEUDO)return true;
  else if(tipoPago==ADEUDO_RCV){
	  
	  $('form#formPagos input#txtCOPSP').prop('value', '');
	  $('form#formPagos input#txtCOPAct').prop('value', '');
	  $('form#formPagos input#txtCOPRec').prop('value', '');
	  $('form#formPagos input#txtCOPTotal').prop('value', '');
	  $('form#formPagos input#txtCOPMultas').prop('value', '');
	  
	  var totalRCV = $('form#formPagos input#txtRCVTotal').prop('value');
	  
	  if(totalRCV!=undefined && totalRCV!=null && totalRCV!="" && totalRCV>0) return true;
	  else{
		 alert("Su adeudo es en RCV por favor ingresar el mismo");
		 return false;
	  }
	  
	  
	  
  }else if(tipoPago==ADEUDO_COP){
	  $('form#formPagos input#txtRCVSP').prop('value', '');
	  $('form#formPagos input#txtRCVAct').prop('value', '');
	  $('form#formPagos input#txtRCVRec').prop('value', '');
	  $('form#formPagos input#txtRCVTotal').prop('value', '');
	  $('form#formPagos input#txtRCVMultas').prop('value', '');
	  
 	  var totalCOP = $('form#formPagos input#txtCOPTotal').prop('value');
	  
	  if(totalCOP!=undefined && totalCOP!=null && totalCOP!="" && totalCOP>0) return true;
	  else{
		 alert("Su adeudo es en COP por favor ingresar el mismo");
		 return false;
	  }
  }
  
  return false;
}