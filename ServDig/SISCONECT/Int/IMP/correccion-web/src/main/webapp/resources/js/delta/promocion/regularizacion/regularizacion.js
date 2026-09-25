
var idDgPromRegularizacion = "#dgPromocionRegularizacion";
var idDataTable 		   = "#dtRegularizacion";

var oDtRegPagos;
var oDgPromRegularizacion;
var validaRegPagos;
var consulta = 0;

$(document).ready(function(){
	
	$( "form#promocionRegularizaForm #fecPerIni, form#promocionRegularizaForm #fecPerFin, form#CrtRegulapagosdetForm #fechaPago, form#promocionRegularizaForm #fechaAtencionPro, form#promocionRegularizaForm #fechaPAI" ).datepicker( { dateFormat: 'dd-mm-yy' });
	
	oDtRegPagos = $(idDataTable).dataTable({
		"bJQueryUI" : true,
		"bPaginate": true,
		"bAutoWidth" : true,
		"bServerSide" :	true,
		"iDeferLoading": 0,
		"aoColumnDefs": 
			[
			 {  fnRender :function(oObj){
				 var retVal = '<input type="radio" value="' +
				 oObj.aData['cveRegulapagosdet'] +'" id="radioTable" class="radioClase" name="radioPago" onclick="mostrar()"/> ';
				 return retVal;
			 }, "aTargets": [0], "mDataProp" :"cveRegulapagosdet" },
			 {  "aTargets": [1], "mDataProp" :"regpat" },
			 {  "aTargets": [2], "mDataProp" :"numFoliosua" },
			 {  "aTargets": [3], "mDataProp" :"numOrdeningreso" },
			 {  "aTargets": [4], "mDataProp" :"numCredito" },
			 {  "aTargets": [5], "mDataProp" :"fechaPago" },
			 {  "aTargets": [6], "mDataProp" :"numPeriodoCop" },
			 {  "aTargets": [7], "mDataProp" :"impCopsp" },
			 {  "aTargets": [8], "mDataProp" :"impCopact" },
			 {  "aTargets": [9], "mDataProp" :"impCoprec" },
			 {fnRender :function(oObj){
				 var retVal = oObj.aData['impCopact'] + oObj.aData['impCopsp'] + oObj.aData['impCoprec'] 
				 return retVal; }, "aTargets": [10] 
			 },
			 {  "aTargets": [11], "mDataProp" :"impMultasCop" },
			 {  "aTargets": [12], "mDataProp" :"numPeriodoRcv" },
			 {  "aTargets": [13], "mDataProp" :"impRcvsp" },
			 {  "aTargets": [14], "mDataProp" :"impRcvact" },
			 {  "aTargets": [15], "mDataProp" :"impRcvrec" },
			 {fnRender :function(oObj){
				 var retVal = oObj.aData['impRcvact'] + oObj.aData['impRcvsp'] + oObj.aData['impRcvrec'] 
				 return retVal; }, "aTargets": [16] 
			 },
			 {  "aTargets": [17], "mDataProp" :"impMultasRcv" }


			 ],
		"sAjaxSource" : jsContextoPromocion+'consulta/paginarPagoDet.do',
		"fnServerData" : function(sSource, aoData, fnCallback) {
			var wrapper = new Object();
			wrapper.aoData = aoData;								
			
			var oForm = $("#CrtRegulapagosdetForm").toObject({mode:'first'});
			wrapper.oForm = oForm;

			$.postJSON(sSource, wrapper, function(data) {
				fnCallback(data);
			});
		}
	});

	oDgPromRegularizacion = $(idDgPromRegularizacion).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,		
		width: 1000,
		beforeClose :function(event,ui){
		    limpiarFormulario("#CrtRegulapagosdetForm");
		    $('form#CrtRegulapagosdetForm #bandera').val(false);
		    resumenPagos();
		},
		open:function(event, ui)
		{
			var reg = $('form#promocionRegularizaForm #regPatron').val();
			var options = "<option value='' >--Por favor seleccione--</option>";					 					 
			options += "<option value='"+ reg +"'>"+ reg +"</option>";		     
			$('select#regpat').html(options);
			
			if($("form#promocionRegularizaForm #tipoProm").val()=='EX'){
				$("form#CrtRegulapagosdetForm #numOrdeningreso").prop('disabled','');				
			}
		}
	});
	
	validaRegPagos = $("#promocionRegularizaForm").validate({
		rules: {
			porRegularizado: {
				required: true
			},
			porAvance: {
				required: true
			},
			fecPerIni: {
				required: true
			},
			fecPerFin: {
				required: true
			},
			numTrabomisos: {
				required: true
			},
			numTrabsubdclara: {
				required: true
			},
			numTrabrevisados: {
				required: true
			}			
		}			
	});
	
	$("form#CrtRegulapagosdetForm #idConcepto").change(function(){
		var concepto = $("form#CrtRegulapagosdetForm #idConcepto").val();
		if(concepto ==  ""){
			desactivaPagoCOP();
			desactivaPagoRCV();
		}else if(concepto == 0){
			desactivaPagoRCV();
			activaPagoCOP();			
			calculaPeriodosCOP();
		}else if(concepto == 1){
			desactivaPagoCOP();
			activaPagoRCV();			
			calculaPeriodosRCV();
		}else if(concepto == 2){
			activaPagoCOP();
			activaPagoRCV();
			calculaPeriodosCOP();
			calculaPeriodosRCV();
		}			
	});
	
	$("form#CrtRegulapagosdetForm #numFoliosua").change(function(){
		if($("form#promocionRegularizaForm #tipoProm").val()=='EX'){
			if($("form#CrtRegulapagosdetForm #numFoliosua").val()!=""){
				$("form#CrtRegulapagosdetForm #numOrdeningreso").prop('disabled','disabled');
			}else{
				$("form#CrtRegulapagosdetForm #numOrdeningreso").prop('disabled','');
			}
		}		
	});
	
	$("form#CrtRegulapagosdetForm #numOrdeningreso").change(function(){
		if($("form#promocionRegularizaForm #tipoProm").val()=='EX'){
			if($("form#CrtRegulapagosdetForm #numOrdeningreso").val()!=""){
				$("form#CrtRegulapagosdetForm #numFoliosua").prop('disabled','disabled');
				$("form#CrtRegulapagosdetForm #numCredito").prop('disabled','');
			}else{
				$("form#CrtRegulapagosdetForm #numFoliosua").prop('disabled','');
				$("form#CrtRegulapagosdetForm #numCredito").prop('disabled','disabled');
			}
		}		
	});

});

function agregarPago(){
	if(validaRegPagos.form()){
		if($("form#promocionRegularizaForm #cveRegulapagos").val()==null || $("form#promocionRegularizaForm #cveRegulapagos").val()==""){
			$("#labelCveRegulaPagos").html('<label style="color: red;">Es necesario Guardar el Registro para agregar Pagos</label>');
		}else{
			$('form#CrtRegulapagosdetForm #cveRegulapagos').val($('form#promocionRegularizaForm #cveRegulapagos').val());
			$('form#CrtRegulapagosdetForm #bandera').val(true);			
			oDgPromRegularizacion.dialog("open");
			oDtRegPagos.fnDraw();
		}
	}	
}

function sumaTrabajadores(){	
	if($("form#promocionRegularizaForm #numTrabomisos").val()!=""){
		if($("form#promocionRegularizaForm #numTrabsubdclara").val()==""){
			$("form#promocionRegularizaForm #numTrabsubdclara").val(0)
		}			
	}else{
		$("form#promocionRegularizaForm #numTrabomisos").val(0);
	}
	if($("form#promocionRegularizaForm #numTrabsubdclara").val()!=""){
		if($("form#promocionRegularizaForm #numTrabomisos").val()==""){
			$("form#promocionRegularizaForm #numTrabomisos").val(0)
		}			
	}else{
		$("form#promocionRegularizaForm #numTrabsubdclara").val(0);
	}
	sum = parseFloat($("form#promocionRegularizaForm #numTrabomisos").val()) + parseFloat($("form#promocionRegularizaForm #numTrabsubdclara").val());		
	if($("form#promocionRegularizaForm #numTrabrevisados").val()>=sum){
		$("form#promocionRegularizaForm #numTrabReg").val(sum);
		$("#labelCveRegulaPagos").html('');
	}else{
		$("#labelCveRegulaPagos").html('<label style="color: red;">La suma de los trabajadores omisos + subdeclarados no puede ser mayor a los trabajadores revisados</label>');
	}	
}

function Guardar(){		
	if($("form#promocionRegularizaForm #fechaAtencionPro").val()!=""){
		if(validaRegPagos.form()){
			sum = parseFloat($("form#promocionRegularizaForm #numTrabomisos").val()) + parseFloat($("form#promocionRegularizaForm #numTrabsubdclara").val());		
			if($("form#promocionRegularizaForm #numTrabrevisados").val()>=sum){
				var crtRegularizacion = $("#promocionRegularizaForm").toObject({mode:'first'});									
				$.postJSON("consulta/guardarPago.do", crtRegularizacion, function(data) {
					$("#labelCveRegulaPagos").html('');
					alert('El registro se Guardo Correctamente');
					$('form#promocionRegularizaForm #cveRegulapagos').val(data.cveRegulapagos);						
				}).error(function(data){ 
					validarSesionExpirada(data);
				}).complete(function(){
					//Instrucciones para el complete													
				});
			}else{
				$("#labelCveRegulaPagos").html('<label style="color: red;">La suma de los trabajadores omisos + subdeclarados no puede ser mayor a los trabajadores revisados</label>');
			}
		}	
	}else if($("form#promocionRegularizaForm #fechaPAI").val()!=""){
		var crtRegularizacion = $("#promocionRegularizaForm").toObject({mode:'first'});									
		$.postJSON("consulta/actualizaPromocion.do", crtRegularizacion, function(data) {
			$("#labelCveRegulaPagos").html('');
			alert('El registro se Guardo Correctamente');
			$('form#promocionRegularizaForm #cveRegulapagos').val(data.cveRegulapagos);
			oDgRgulariza.dialog('close');
		}).error(function(data){ 
			validarSesionExpirada(data);
		}).complete(function(){
			//Instrucciones para el complete			
		});
	}else{
		$("#labelCveRegulaPagos").html('<label style="color: red;">Es necesario seleccionar una fecha</label>');
	}
}

function guardaPago(){
	if(validaFormPagos()){
		var crtRegularPago = $('#CrtRegulapagosdetForm').toObject({mode:'first'});
		$.postJSON("consulta/guardarPagoDet.do", crtRegularPago, function(data) {		
			alert('El registro se Guardo Correctamente');
			limpiarFormulario("#CrtRegulapagosdetForm");
			$('form#CrtRegulapagosdetForm #cveRegulapagosdet').val(0);
			$('form#CrtRegulapagosdetForm #idPagoCaratula').val('');
			if($("form#promocionRegularizaForm #tipoProm").val()=='EX'){
				$("form#CrtRegulapagosdetForm #numFoliosua").prop('disabled','');
				$("form#CrtRegulapagosdetForm #numCredito").prop('disabled','disabled');				
			}
			desactivaPagoCOP();
			desactivaPagoRCV();
			oDtRegPagos.fnDraw();
		}).error(function(data){ 
			validarSesionExpirada(data);
		}).complete(function(){
			//Instrucciones para el complete													
		});			
	}	
}

function cancelar(){
	limpiarFormulario("#CrtRegulapagosdetForm");
	$('form#CrtRegulapagosdetForm #cveRegulapagosdet').val(0);
	desactivaPagoCOP();
	desactivaPagoRCV();
	$("#regpatError").html('');
	$("#idConceptoError").html('');
	$("#numOrdeningresoError").html('');
	$("#numFoliosuaError").html('');
	$("#numCreditoError").html('');
	$("#fechaPagoError").html('');
	$("#numPeriodoCopError").html('');
	$("#impCopspError").html('');
	$("#impCopactError").html('');
	$("#impCoprecError").html('');
	$("#numPeriodoRcvError").html('');
	$("#impRcvspError").html('');
	$("#impRcvactError").html('');
	$("#impRcvrecError").html('');
	$('form#CrtRegulapagosdetForm #idPagoCaratula').val('');
	var radios = document.getElementsByName("radioPago");
	for (i=0;i<radios.length;i++)
	 {
		if(radios[i].checked)
		{
			radios[i].checked = false;
		}
	 }
}

function mostrar(){
	var radios = document.getElementsByName("radioPago");
	for (i=0;i<radios.length;i++)
	 {
		if(radios[i].checked)
		{
			var idPago = radios[i].value;
			var sPago = '{"cveRegulapagosdet":'+idPago+'}';
			var crtRegulapagosDet = jQuery.parseJSON(sPago);									
			$.postJSON("consulta/consultaPago.do", crtRegulapagosDet, function(data) {
				if(data.idConcepto==0){
					desactivaPagoRCV()
					activaPagoCOP();
					calculaPeriodosCOP();
				}else if(data.idConcepto==1){
					desactivaPagoCOP()
					activaPagoRCV();
					calculaPeriodosRCV();
				}else if(data.idConcepto==2){
					activaPagoCOP();
					activaPagoRCV();
					calculaPeriodosCOP();
					calculaPeriodosRCV();
				}				
				$('form#CrtRegulapagosdetForm #cveRegulapagosdet').val(data.cveRegulapagosdet);
				$('form#CrtRegulapagosdetForm #idPagoCaratula').val(data.idPagoCaratula);
				$('form#CrtRegulapagosdetForm #regpat').val($('form#promocionRegularizaForm #regPatron').val());
				$('form#CrtRegulapagosdetForm #idConcepto').val(data.idConcepto);
				$('form#CrtRegulapagosdetForm #numFoliosua').val(data.numFoliosua);
				$('form#CrtRegulapagosdetForm #numOrdeningreso').val(data.numOrdeningreso);
				$('form#CrtRegulapagosdetForm #numCredito').val(data.numCredito);
				$('form#CrtRegulapagosdetForm #fechaPago').val(data.fechaPago);				
				$('form#CrtRegulapagosdetForm #numPeriodoCop').val(data.numPeriodoCop);
				$('form#CrtRegulapagosdetForm #impCopsp').val(data.impCopsp);
				$('form#CrtRegulapagosdetForm #impCopact').val(data.impCopact);
				$('form#CrtRegulapagosdetForm #impCoprec').val(data.impCoprec);
				$('form#CrtRegulapagosdetForm #txtCOPTotal').val(data.impCopsp+data.impCopact+data.impCoprec);
				$('form#CrtRegulapagosdetForm #impMultasCop').val(data.impMultasCop);
				$('form#CrtRegulapagosdetForm #numPeriodoRcv').val(data.numPeriodoRcv);
				$('form#CrtRegulapagosdetForm #impRcvsp').val(data.impRcvsp);
				$('form#CrtRegulapagosdetForm #impRcvact').val(data.impRcvact);
				$('form#CrtRegulapagosdetForm #impRcvrec').val(data.impRcvrec);
				$('form#CrtRegulapagosdetForm #txtRCVTotal').val(data.impRcvsp+data.impRcvact+data.impRcvrec);
				$('form#CrtRegulapagosdetForm #impMultasRcv').val(data.impMultasRcv);						
			}).error(function(data){ 
				validarSesionExpirada(data);
			}).complete(function(){
				//Instrucciones para el complete													
			});
			
		}
	 }
}

function eliminar(){
	var radios = document.getElementsByName("radioPago");
	for (i=0;i<radios.length;i++)
	 {
		if(radios[i].checked)
		{
			var idPago = radios[i].value;
			var sPago = '{"cveRegulapagosdet":'+idPago+'}';
			var crtRegulapagosDet = jQuery.parseJSON(sPago);									
			$.postJSON("consulta/eliminaPago.do", crtRegulapagosDet, function(data) {
				limpiarFormulario("#CrtRegulapagosdetForm");
				$('form#CrtRegulapagosdetForm #cveRegulapagosdet').val(0);
				desactivaPagoCOP();
				desactivaPagoRCV();
				oDtRegPagos.fnDraw();				
			}).error(function(data){ 
				validarSesionExpirada(data);
			}).complete(function(){
				//Instrucciones para el complete													
			});
			
		}
	 }
}

function resumenPagos(){
	var sumaCopsp = 0;
	var sumaCopact = 0;
	var sumaCoprec = 0;
	var sumaRCVsp = 0;
	var sumaRCVact = 0;
	var sumaRCVrec = 0;
	if($('form#promocionRegularizaForm #cveRegulapagos').val()!=""){
		var cveRegulaPagos = $('form#promocionRegularizaForm #cveRegulapagos').val();
		var sCve = '{"cveBusqueda":'+cveRegulaPagos+'}';
		var crtRegularPagoDet = jQuery.parseJSON(sCve);		
		$.postJSON("consulta/consultar.do", crtRegularPagoDet, function(datas) {		
			if(datas!=null){
				for (var i = 0; i < datas.length; i++) {
					sumaCopsp  += datas[i].impCopsp;
					sumaCopact += datas[i].impCopact;
					sumaCoprec += datas[i].impCoprec;
					sumaRCVsp  += datas[i].impRcvsp;
					sumaRCVact += datas[i].impRcvact;
					sumaRCVrec += datas[i].impRcvrec;
				}
				$('form#promocionRegularizaForm #txCopSp').val(sumaCopsp);
				$('form#promocionRegularizaForm #txCopAct').val(sumaCopact);
				$('form#promocionRegularizaForm #txCopRec').val(sumaCoprec);
				$('form#promocionRegularizaForm #txCopTp').val(sumaCopsp+sumaCopact+sumaCoprec);
				$('form#promocionRegularizaForm #txRcvSp').val(sumaRCVsp);
				$('form#promocionRegularizaForm #txRcvAct').val(sumaRCVact);
				$('form#promocionRegularizaForm #txRcvRec').val(sumaRCVrec);
				$('form#promocionRegularizaForm #txRcvTp').val(sumaRCVsp+sumaRCVact+sumaRCVrec);
			}
		}).error(function(data){ 
			validarSesionExpirada(data);
		}).complete(function(){
			//Instrucciones para el complete													
		});			
	}	
}

function calculaPeriodosCOP(){
	var fecIni = $('form#promocionRegularizaForm #fecPerIni').val();
	var fecFin = $('form#promocionRegularizaForm #fecPerFin').val();	
	var anios = fecFin.substring(6,10) - fecIni.substring(6,10);	
	var meses;
	var options = "<option value='' >--Por favor seleccione--</option>";
	for(var i = 0;i <= anios;i++){		
		if(i>0 && i<anios){			
			meses = 11;
		}else{
			if(i==0){
				meses = fecIni.substring(3,5);				
			}else if(i==anios){
				meses = parseFloat(fecFin.substring(3,5))-1;
			}
		}
		if(i==0){
			for(var m = meses; m <= 12;m++){
				if(m <= 9){					
					ms = parseFloat(m);
					ms = "0"+ms;
				}else{
					ms = parseFloat(m);
				}				
				periodo = (parseFloat(fecIni.substring(6,10)) + parseFloat(i)) + (ms+"");
				options += "<option value='"+ periodo +"'>"+ periodo +"</option>";
			}
		}else{
			for(var m = 0; m <= meses;m++){
				if(m < 9){
					ms = parseFloat(m)+1;
					ms = "0"+(ms);
				}else{
					ms = parseFloat(m)+1;
				}				
				periodo = (parseFloat(fecIni.substring(6,10)) + parseFloat(i)) + (ms+"");
				options += "<option value='"+ periodo +"'>"+ periodo +"</option>";
			}
		}		
	}
	$('select#numPeriodoCop').html(options);
}

function calculaPeriodosRCV(){
	var fecIni = $('form#promocionRegularizaForm #fecPerIni').val();
	var fecFin = $('form#promocionRegularizaForm #fecPerFin').val();	
	var anios = fecFin.substring(6,10) - fecIni.substring(6,10);	
	var meses;
	var options = "<option value='' >--Por favor seleccione--</option>";
	for(var i = 0;i <= anios;i++){		
		if(i>0 && i<anios){			
			meses = 11;
		}else{
			if(i==0){
				meses = fecIni.substring(3,5);				
			}else if(i==anios){
				meses = parseFloat(fecFin.substring(3,5));
			}
		}
		if(i==0){
			for(var m = meses; m <= 12;m++){			
				if(m < 9){					
					ms = parseFloat(m);
					ms = "0"+ms;									
				}else{
					ms = parseFloat(m);
				}	
				if((m%2)==0){
					periodo = (parseFloat(fecIni.substring(6,10)) + parseFloat(i)) + (ms+"");				
					options += "<option value='"+ periodo +"'>"+ periodo +"</option>";
				}
			}
		}else{
			for(var m = 1; m <= meses;m++){			
				if(m < 9){
					m = parseFloat(m)+1;
					m = "0"+m;
				}else{
					m = parseFloat(m)+1;
				}
				periodo = (parseFloat(fecIni.substring(6,10)) + parseFloat(i)) + (m+"");				
				options += "<option value='"+ periodo +"'>"+ periodo +"</option>";
			}
		}					
	}
	$('select#numPeriodoRcv').html(options);
}

function copTotal(){
	if($("form#CrtRegulapagosdetForm #impCopsp").val()!=""){
		if($("form#CrtRegulapagosdetForm #impCopact").val()==""){
			$("form#CrtRegulapagosdetForm #impCopact").val(0)
		}
		if($("form#CrtRegulapagosdetForm #impCoprec").val()==""){
			$("form#CrtRegulapagosdetForm #impCoprec").val(0)
		}			
	}else{
		$("form#CrtRegulapagosdetForm #impCopsp").val(0);
	}
	if($("form#CrtRegulapagosdetForm #impCopact").val()!=""){
		if($("form#CrtRegulapagosdetForm #impCopsp").val()==""){
			$("form#CrtRegulapagosdetForm #impCopsp").val(0)
		}
		if($("form#CrtRegulapagosdetForm #impCoprec").val()==""){
			$("form#CrtRegulapagosdetForm #impCoprec").val(0)
		}			
	}else{
		$("form#CrtRegulapagosdetForm #impCopact").val(0);
	}
	if($("form#CrtRegulapagosdetForm #impCoprec").val()!=""){
		if($("form#CrtRegulapagosdetForm #impCopact").val()==""){
			$("form#CrtRegulapagosdetForm #impCopact").val(0)
		}
		if($("form#CrtRegulapagosdetForm #impCopsp").val()==""){
			$("form#CrtRegulapagosdetForm #impCopsp").val(0)
		}
	}else{
		$("form#CrtRegulapagosdetForm #impCoprec").val(0);
	}
	sum = parseFloat($("form#CrtRegulapagosdetForm #impCopsp").val()) + parseFloat($("form#CrtRegulapagosdetForm #impCopact").val()) + parseFloat($("form#CrtRegulapagosdetForm #impCoprec").val());		
	if($("form#CrtRegulapagosdetForm #impCoprec").val()==0){
		$("form#CrtRegulapagosdetForm #impCoprec").val("")
	}
	if($("form#CrtRegulapagosdetForm #impCopact").val()==0){
		$("form#CrtRegulapagosdetForm #impCopact").val("")
	}
	if($("form#CrtRegulapagosdetForm #impCopsp").val()==0){
		$("form#CrtRegulapagosdetForm #impCopsp").val("")
	}
	
	$("form#CrtRegulapagosdetForm #txtCOPTotal").val(sum);
	$("#labelCveRegulaPagos").html('');	
}

function rcvTotal(){
	if($("form#CrtRegulapagosdetForm #impRcvsp").val()!=""){
		if($("form#CrtRegulapagosdetForm #impRcvact").val()==""){
			$("form#CrtRegulapagosdetForm #impRcvact").val(0)
		}
		if($("form#CrtRegulapagosdetForm #impRcvrec").val()==""){
			$("form#CrtRegulapagosdetForm #impRcvrec").val(0)
		}			
	}else{
		$("form#CrtRegulapagosdetForm #impRcvsp").val(0);
	}
	if($("form#CrtRegulapagosdetForm #impRcvact").val()!=""){
		if($("form#CrtRegulapagosdetForm #impRcvsp").val()==""){
			$("form#CrtRegulapagosdetForm #impRcvsp").val(0)
		}
		if($("form#CrtRegulapagosdetForm #impRcvrec").val()==""){
			$("form#CrtRegulapagosdetForm #impRcvrec").val(0)
		}			
	}else{
		$("form#CrtRegulapagosdetForm #impRcvact").val(0);
	}
	if($("form#CrtRegulapagosdetForm #impRcvrec").val()!=""){
		if($("form#CrtRegulapagosdetForm #impRcvact").val()==""){
			$("form#CrtRegulapagosdetForm #impRcvact").val(0)
		}
		if($("form#CrtRegulapagosdetForm #impRcvsp").val()==""){
			$("form#CrtRegulapagosdetForm #impRcvsp").val(0)
		}
	}else{
		$("form#CrtRegulapagosdetForm #impRcvrec").val(0);
	}
	sum = parseFloat($("form#CrtRegulapagosdetForm #impRcvsp").val()) + parseFloat($("form#CrtRegulapagosdetForm #impRcvact").val()) + parseFloat($("form#CrtRegulapagosdetForm #impRcvrec").val());		
	if($("form#CrtRegulapagosdetForm #impRcvrec").val()==0){
		$("form#CrtRegulapagosdetForm #impRcvrec").val("")
	}
	if($("form#CrtRegulapagosdetForm #impRcvact").val()==0){
		$("form#CrtRegulapagosdetForm #impRcvact").val("")
	}
	if($("form#CrtRegulapagosdetForm #impRcvsp").val()==0){
		$("form#CrtRegulapagosdetForm #impRcvsp").val("")
	}
	$("form#CrtRegulapagosdetForm #txtRCVTotal").val(sum);
	$("#labelCveRegulaPagos").html('');	
}

function validaFormPagos(){
	var Error = "";
	if($("form#CrtRegulapagosdetForm #regpat").val()==""){
		$("#regpatError").html('<label style="color: red;">El Campo es Requerido</label>');
		Error = "Error";
	}else
		$("#regpatError").html('');
	if($("form#CrtRegulapagosdetForm #idConcepto").val()==""){
		$("#idConceptoError").html('<label style="color: red;">El Campo es Requerido</label>');
		Error = "Error";
	}else
		$("#idConceptoError").html('');
	if($("form#CrtRegulapagosdetForm #numFoliosua").val()=="" && $("form#CrtRegulapagosdetForm #numOrdeningreso").val()==""){
		$("#numFoliosuaError").html('<label style="color: red;">El Campo es Requerido</label>');
		Error = "Error";
	}else
		$("#numFoliosuaError").html('');
	if( $("form#CrtRegulapagosdetForm #numFoliosua").val()=="" && $("form#CrtRegulapagosdetForm #numOrdeningreso").val()==""){
		//$("#numOrdeningresoError").html('<label style="color: red;">El Campo es Requerido</label>');
		Error = "Error";
	}else
		//$("#numOrdeningresoError").html('');
	if($("form#CrtRegulapagosdetForm #numOrdeningreso").val()!="" && $("form#CrtRegulapagosdetForm #numCredito").val()==""){
		$("#numCreditoError").html('<label style="color: red;">El Campo es Requerido</label>');
		Error = "Error";
	}else
		$("#numCreditoError").html('');
	if($("form#CrtRegulapagosdetForm #fechaPago").val()==""){
		$("#fechaPagoError").html('<label style="color: red;">El Campo es Requerido</label>');
		Error = "Error";
	}else
		$("#fechaPagoError").html('');
	if($("form#CrtRegulapagosdetForm #idConcepto").val()!="" && ($("form#CrtRegulapagosdetForm #idConcepto").val()==0 || $("form#CrtRegulapagosdetForm #idConcepto").val()==2)){
		if($("form#CrtRegulapagosdetForm #idConcepto").val()==0){
			$("#numPeriodoRcvError").html('');
			$("#impRcvspError").html('');
			$("#impRcvactError").html('');
			$("#impRcvrecError").html('');
		}
		if($("form#CrtRegulapagosdetForm #numPeriodoCop").val()==""){
			$("#numPeriodoCopError").html('<label style="color: red;">El Campo es Requerido</label>');
			Error = "Error";
		}else
			$("#numPeriodoCopError").html('');
		if($("form#CrtRegulapagosdetForm #impCopsp").val()==""){
			$("#impCopspError").html('<label style="color: red;">El Campo es Requerido</label>');
			Error = "Error";
		}else
			$("#impCopspError").html('');
		if($("form#CrtRegulapagosdetForm #impCopact").val()==""){
			$("#impCopactError").html('<label style="color: red;">El Campo es Requerido</label>');
			Error = "Error";
		}else
			$("#impCopactError").html('');
		if($("form#CrtRegulapagosdetForm #impCoprec").val()==""){
			$("#impCoprecError").html('<label style="color: red;">El Campo es Requerido</label>');
			Error = "Error";
		}else
			$("#impCoprecError").html('');		
	}
	if($("form#CrtRegulapagosdetForm #idConcepto").val()!="" && ($("form#CrtRegulapagosdetForm #idConcepto").val()==1 || $("form#CrtRegulapagosdetForm #idConcepto").val()==2)){
		if($("form#CrtRegulapagosdetForm #idConcepto").val()==1){
			$("#numPeriodoCopError").html('');
			$("#impCopspError").html('');
			$("#impCopactError").html('');
			$("#impCoprecError").html('');
		}		
		if($("form#CrtRegulapagosdetForm #numPeriodoRcv").val()==""){
			$("#numPeriodoRcvError").html('<label style="color: red;">El Campo es Requerido</label>');
			Error = "Error";
		}else
			$("#numPeriodoRcvError").html('');
		if($("form#CrtRegulapagosdetForm #impRcvsp").val()==""){
			$("#impRcvspError").html('<label style="color: red;">El Campo es Requerido</label>');
			Error = "Error";
		}else
			$("#impRcvspError").html('');
		if($("form#CrtRegulapagosdetForm #impRcvact").val()==""){
			$("#impRcvactError").html('<label style="color: red;">El Campo es Requerido</label>');
			Error = "Error";
		}else
			$("#impRcvactError").html('');
		if($("form#CrtRegulapagosdetForm #impRcvrec").val()==""){
			$("#impRcvrecError").html('<label style="color: red;">El Campo es Requerido</label>');
			Error = "Error";
		}else
			$("#impRcvrecError").html('');
	}
	if(Error!=""){		
		return false;
	}else{
		return true;
	}
}

function activaPagoCOP(){
	$("form#CrtRegulapagosdetForm #numPeriodoCop").prop('disabled','');
	$("form#CrtRegulapagosdetForm #impCopsp").prop('disabled','');
	$("form#CrtRegulapagosdetForm #impCopact").prop('disabled','');
	$("form#CrtRegulapagosdetForm #impCoprec").prop('disabled','');
}

function desactivaPagoCOP(){
	$("form#CrtRegulapagosdetForm #numPeriodoCop").prop('disabled','disabled');
	$("form#CrtRegulapagosdetForm #impCopsp").prop('disabled','disabled');
	$("form#CrtRegulapagosdetForm #impCopact").prop('disabled','disabled');
	$("form#CrtRegulapagosdetForm #impCoprec").prop('disabled','disabled');
}

function activaPagoRCV(){
	$("form#CrtRegulapagosdetForm #numPeriodoRcv").prop('disabled','');
	$("form#CrtRegulapagosdetForm #impRcvsp").prop('disabled','');
	$("form#CrtRegulapagosdetForm #impRcvact").prop('disabled','');
	$("form#CrtRegulapagosdetForm #impRcvrec").prop('disabled','');
}

function desactivaPagoRCV(){
	$("form#CrtRegulapagosdetForm #numPeriodoRcv").prop('disabled','disabled');
	$("form#CrtRegulapagosdetForm #impRcvsp").prop('disabled','disabled');
	$("form#CrtRegulapagosdetForm #impRcvact").prop('disabled','disabled');
	$("form#CrtRegulapagosdetForm #impRcvrec").prop('disabled','disabled');
}

function activaPagos(){
	if($("form#promocionRegularizaForm #fechaAtencionPro").val()!=""){
		$("form#promocionRegularizaForm #fechaPAI").prop('disabled','disabled');
		$("form#promocionRegularizaForm #porRegularizado").prop('disabled','');
		$("form#promocionRegularizaForm #porAvance").prop('disabled','');
		$("form#promocionRegularizaForm #fecPerIni").prop('disabled','');
		$("form#promocionRegularizaForm #fecPerFin").prop('disabled','');
		$("form#promocionRegularizaForm #numTrabrevisados").prop('disabled','');
		$("form#promocionRegularizaForm #numTrabomisos").prop('disabled','');
		$("form#promocionRegularizaForm #numTrabsubdclara").prop('disabled','');
		$("#labelCveRegulaPagos").html('');
	}else if($("form#promocionRegularizaForm #fechaAtencionPro").val()==""){
		$("form#promocionRegularizaForm #fechaPAI").prop('disabled','');
		$("form#promocionRegularizaForm #porRegularizado").prop('disabled','disabled');
		$("form#promocionRegularizaForm #porAvance").prop('disabled','disabled');
		$("form#promocionRegularizaForm #fecPerIni").prop('disabled','disabled');
		$("form#promocionRegularizaForm #fecPerFin").prop('disabled','disabled');
		$("form#promocionRegularizaForm #numTrabrevisados").prop('disabled','disabled');
		$("form#promocionRegularizaForm #numTrabomisos").prop('disabled','disabled');
		$("form#promocionRegularizaForm #numTrabsubdclara").prop('disabled','disabled');
	}
	if($("form#promocionRegularizaForm #fechaPAI").val()!=""){			
		$("form#promocionRegularizaForm #fechaAtencionPro").prop('disabled','disabled');
		$("#labelCveRegulaPagos").html('');
	}else if($("form#promocionRegularizaForm #fechaPAI").val()==""){
		$("form#promocionRegularizaForm #fechaAtencionPro").prop('disabled','');
	}
}