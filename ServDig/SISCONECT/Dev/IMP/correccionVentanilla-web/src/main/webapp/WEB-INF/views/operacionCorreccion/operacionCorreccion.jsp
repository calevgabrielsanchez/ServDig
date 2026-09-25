<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@page import="mx.gob.imss.ctirss.correccion.session.UserSession"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/tabControler.js"></script>
<link rel="stylesheet" type="text/css" 	href="<%=request.getContextPath()%>/resources/estilos/styleTabs.css">
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/resources/estilos/cedulasStyle.css">
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/resources/estilos/estilo.css">


<script type="text/javascript">
$(document).ready(function() {
	
	inicializandoCampos();
	deslbloquearFormas();
	desbloquearTabs();
	limpiarFormularios();
	$("#tabSeguimientoOperacion").hide();
	
});


function dateParser(idPicker,boton){
	  $('#'+idPicker).datepicker("option", "onSelect", function(){
	        $("#"+boton).show();
	  });
	  
	  $("#"+boton).click(function(){  
	     $('#'+idPicker).val("");
	     $("#"+boton).hide();
	  });
}


//fecha en formato yyyy-mm-dd convierte a dd-mm-yyyy
function formateaFecha(fecha){
	var data=fecha.split("-");	
	return data[2]+"-"+data[1]+"-"+data[0]
}



function validaCapturaDerivaSubdelegacion(){
	
	if(domicilioGuardado==undefined){
		alert("Favor de capturar el domiclio");
		return false;
	}
	if($("#fechaDerivacion").val()=="" || $("#folioDerivacion").val()=="" || $("#comboDelegacionDDS").val()==-1 || $("#comboSubDelegacionDDS").val()==-1){
		alert("Favor de capturar campos requeridos");
		return false;
	}
	return true;
	
}

function validaCapturaFiscalizacion(){
	
	if($("#numFolioOficio").val()=="" || $("#fecDerivFisSegCorr").val()=="" ){
		alert("Favor de capturar campos requeridos");
		return false;
	}
	return true;
}

function validaCapturaReactivar(){
	if($("#fechaSolReactiva").val()=="" || $("#fechaEnvioSol").val()=="" ||
	   $("#fechaReactiva").val()=="" || $("#numOficioEnvio").val()==""	||
	   $("#numOficioReactiva").val()=="" || $("#txObservacionesReactivaSegCorr").val()==""
	){
		alert("Favor de capturar campos requeridos");
		return false;
	}
	return true;
	
	
}


function validaCapturaDictamen(){
	if($("#fecAutDicSegCorr").val()=="" || $("#numFolioOficioOperacion").val()=="" || $("#comboAnios").val()==-1){
		alert("Favor de capturar campos requeridos");
		return false;
	}
	return true;
}

function validaCapturaCancelacion(){
	if($("#refCancelacion").val()=="" || $("#fecCancelacionSegCorr").val()=="" || $("#cboMotivos").val()==-1){
		alert("Favor de capturar campos requeridos");
		return false;
	}
	return true;
}


function bloquearFormas(){
	
	bloquearForma('devSubDelegacionSeguimientoCorreccionForm');	
	bloquearForma('devFiscalizacionSeguimientoCorreccionForm');	
	bloquearForma('devDictamenSeguimientoCorreccionForm');	
	bloquearForma('cancelacionSeguimientoCorreccionForm');	

	
	
	
	$("#spnFechaDerivarSubdel").hide();
	$("#spnfecDerivFisSegCorr").hide();
	$("#spnFechaDerivarDict").hide();
	$("#spnfechaSolReactiva").hide();
	$("#spnfechaEnvioSol").hide();
	$("#spnfechaReactiva").hide();
}

function deslbloquearFormas(){
	habilitaCamposXForma('devSubDelegacionSeguimientoCorreccionForm');	
	habilitaCamposXForma('devFiscalizacionSeguimientoCorreccionForm');	
	habilitaCamposXForma('devDictamenSeguimientoCorreccionForm');	
	habilitaCamposXForma('cancelacionSeguimientoCorreccionForm');	
	habilitaCamposXForma('reactivacionSeguimientoCorreccionForm');	
	
	$("#spnFechaDerivarSubdel").show();
	$("#spnfecDerivFisSegCorr").show();
	$("#spnFechaDerivarDict").show();
	$("#spnfechaSolReactiva").show();
	$("#spnfechaEnvioSol").show();
	$("#spnfechaReactiva").show();
	
	  $('#botonAgregarDomicilio').show();
	
}
function limpiarFormularios(){
		$("#calleDerivarSubdel").val("");
		$("#coloniaDerivarSubdel").val("");
		$("#numExtDerivarSubdel").val("");
		$("#numIntDerivarSubdel").val("");
		$("#municipioDerivarSubdel").val("");
		$("#estadoDerivarSubdel").val("");
		$("#codigoPostalDerivarSubdel").val("");
		$("#fechaDerivacion").val("");
		$("#comboDelegacionDDS").val(-1);
		$("#folioDerivacion").val("");
		$("#comboSubDelegacionDDS").val("");
		$("#nombreFuncionarioSubdele").val("");		
		$("#fecAutDicSegCorr").val("");
		$("#numFolioOficioOperacion").val("");
		$("#comboAnios").val(-1);
		$("#refCancelacion").val("");
		$("#fecCancelacionSegCorr").val("");
		$("#cboMotivos").val("");	  
		$("#numFolioOficio").val("");
		$("#fecDerivFisSegCorr").val("");
		$("#nombreFuncionario").val("");	
}

function validarAlfaNumericoDevSub(e) { 
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla==8) return true;
    patron = /[1234567890abcdefghijklmn\U00F1opqrstuvwxyzABCDEFGHIJKLMN\U00D1OPQRSTUVWXYZ/]/;
    te = String.fromCharCode(tecla);
    return patron.test(te);
} 


function validarAlfaNumerico(e) { 
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla==8) return true;
    patron = /[1234567890abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ/]/;
    te = String.fromCharCode(tecla);
    return patron.test(te);
} 


function limpiafecDerivFisSegCorr(){	
	$("form#devFiscalizacionSeguimientoCorreccionForm #fecDerivFisSegCorr").val("");
	$("form#devFiscalizacionSeguimientoCorreccionForm #labelfecDerivFisSegCorr").html("");
	$("form#devFiscalizacionSeguimientoCorreccionForm #spnfecDerivFisSegCorr").hide();	
}

function limpiaFechaDerivacionSubdel(){	
	$("form#devSubDelegacionSeguimientoCorreccionForm #fechaDerivacion").val("");
	$("form#devSubDelegacionSeguimientoCorreccionForm #labelFechaDerivarSubdel").html("");
	$('#spnFechaDerivarSubdel').hide();	
}




function inicializandoCampos(){
	
	$("#fechaDerivacion").datepicker({dateFormat: 'dd-mm-yy',beforeShowDay: $.datepicker.noWeekends,maxDate: getFechaServidor(),minDate: getFechaServidorMenos45Dias()});
	$("#fecDerivFisSegCorr").datepicker({dateFormat: 'dd-mm-yy',beforeShowDay: $.datepicker.noWeekends,maxDate: getFechaServidor(),minDate: getFechaServidorMenos45Dias()});
	$("#fechaSolReactiva").datepicker({dateFormat: 'dd-mm-yy',beforeShowDay: $.datepicker.noWeekends,maxDate: getFechaServidor(),minDate: getFechaServidorMenos45Dias()});
	$("#fechaEnvioSol").datepicker({dateFormat: 'dd-mm-yy',beforeShowDay: $.datepicker.noWeekends,maxDate: getFechaServidor(),minDate: getFechaServidorMenos45Dias()});
	$("#fechaReactiva").datepicker({dateFormat: 'dd-mm-yy',beforeShowDay: $.datepicker.noWeekends,maxDate: getFechaServidor(),minDate: getFechaServidorMenos45Dias()});
	$("#fecAutDicSegCorr").datepicker({dateFormat: 'dd-mm-yy',beforeShowDay: $.datepicker.noWeekends,maxDate: getFechaServidor(),minDate: getFechaServidorMenos45Dias()});
	$("#fecCancelacionSegCorr").datepicker({dateFormat: 'dd-mm-yy',beforeShowDay: $.datepicker.noWeekends,maxDate: getFechaServidor(),minDate: getFechaServidorMenos45Dias()});
	$("#forbidenTabs").val();
	$("#accessTabs").val("seguimientoCorreccionDerivarSub-seguimientoCorreccionDerivarFis-seguimientoCorreccionReactivar-seguimientoCorreccionDerivarDic-seguimientoCorreccionCancelacion");
	
	
	dateParser("fechaDerivacion","spnFechaDerivarSubdel");
	dateParser("fecDerivFisSegCorr","spnfecDerivFisSegCorr");
	dateParser("fecAutDicSegCorr","spnFechaDerivarDict");
	dateParser("fechaSolReactiva","spnfechaSolReactiva");
	dateParser("fechaEnvioSol","spnfechaEnvioSol");
	dateParser("fechaReactiva","spnfechaReactiva");
	
}

function recuperaEjerciciosDictaminar(){
	var context = getAppContextParaJS();
	var param={};
	param.nuFolio=$("#nuFolioInput").val();
	 $.postJSON_Sync(context+"/seguimiento/correccion/recuperaEjerciciosDictaminar.do", param, function(objData) {			  
	     var options = '<option value="-1" >--Por favor seleccione--</option>';
			for (var i = 0; i < objData.length; i++) {
		        options += '<option value="' + objData[i] + '">' + objData[i] + '</option>';
		      }
		 $("#comboAnios").html(options);
	});		
	
}

function cargarDatos(data){
	
	$("#fechaDerivacion").datepicker("option", "minDate",formateaFecha(data.solicitud.fecFechaElacoracionCorreccion));
	$("#fecDerivFisSegCorr").datepicker("option", "minDate",formateaFecha(data.solicitud.fecFechaElacoracionCorreccion));
	$("#fechaSolReactiva").datepicker("option", "minDate",formateaFecha(data.solicitud.fecFechaElacoracionCorreccion));
	//$("#fechaEnvioSol").datepicker("option", "maxDate",formateaFecha(data.solicitud.fecFechaElacoracionCorreccion));
	//$("#fechaReactiva").datepicker("option", "maxDate",formateaFecha(data.solicitud.fecFechaElacoracionCorreccion));
	$("#fecAutDicSegCorr").datepicker("option", "minDate",formateaFecha(data.solicitud.fecFechaElacoracionCorreccion));
	$("#fecCancelacionSegCorr").datepicker("option", "minDate",formateaFecha(data.solicitud.fecFechaElacoracionCorreccion));


	dateParser("fechaDerivacion","spnFechaDerivarSubdel");
	dateParser("fecDerivFisSegCorr","spnfecDerivFisSegCorr");
	dateParser("fecAutDicSegCorr","spnFechaDerivarDict");
	dateParser("fechaSolReactiva","spnfechaSolReactiva");
	dateParser("fechaEnvioSol","spnfechaEnvioSol");
	dateParser("fechaReactiva","spnfechaReactiva");
	dateParser("fecCancelacionSegCorr","spnfecDeCancelacion");
	
	$("#fechaSolReactiva").datepicker("option", "onSelect", function(){
		$("#fechaEnvioSol").datepicker("option", "minDate",$("#fechaSolReactiva").val());		
		$("#fechaEnvioSol").val("");
		$("#fechaReactiva").val("");
		$("#spnfechaSolReactiva").show();
	  });
	
	$("#fechaEnvioSol").datepicker("option", "onSelect", function(){
		$("#fechaReactiva").datepicker("option", "minDate",$("#fechaEnvioSol").val());
		$("#spnfechaEnvioSol").show();
		
	  });
	
	
	
	
	
	
	$("#nombreFuncionarioDictamen").val(data.usuario);
	$("#nombreFuncionarioFisca").val(data.usuario);
	$("#nombreFuncionarioSubdele").val(data.usuario);
	$("#nombreFuncionarioReactiva").val(data.usuario);
	$("#labelFuncionarioReg").text(data.usuario);
	$("#labelFuncionarioAut").text(data.usuario);
	
	if(data.derivaSubdelegacion!=null && data.derivaSubdelegacion!=undefined){
		$("#calleDerivarSubdel").val(data.domicilioSubdelegacion.calle);
		$("#coloniaDerivarSubdel").val(data.domicilioSubdelegacion.asentamiento.nombre);
		$("#numExtDerivarSubdel").val(data.domicilioSubdelegacion.numExterior1);
		$("#numIntDerivarSubdel").val(data.domicilioSubdelegacion.numInteriorAlf);
		$("#municipioDerivarSubdel").val(data.domicilioSubdelegacion.asentamiento.localidad.municipio.nombre);
		
		$("#estadoDerivarSubdel").val(data.domicilioSubdelegacion.asentamiento.localidad.municipio.entidadFederativa.nombre);
		$("#codigoPostalDerivarSubdel").val(data.domicilioSubdelegacion.asentamiento.codigoPostal.codigoPostal);
	
		$("#fechaDerivacion").val(formateaFecha(data.derivaSubdelegacion.fechaDerivacion));
		$("#comboDelegacionDDS").val(data.derivaSubdelegacion.subdelegDestino.sacDelegacion.cvePk);
		$("#comboDelegacionDDS").trigger("change");
		$("#folioDerivacion").val(data.derivaSubdelegacion.numFolio);
		$("#comboSubDelegacionDDS").val(data.derivaSubdelegacion.subdelegDestino.cvePk);

		
		bloquearForma('devSubDelegacionSeguimientoCorreccionForm');					
		bloquarTabs();
		//setTabHabilitado("seguimientoCorreccionDerivarSub");
		alert("Folio Derivado a Subdelegacion");
		$("#seguimientoCorreccionDerivarSubLI").trigger("click");
		$('#botonAgregarDomicilio').hide();
	
	}else if(data.derivacionDicatmen!=null && data.derivacionDicatmen!=undefined){		  
		  	$("#fecAutDicSegCorr").val(formateaFecha(data.derivacionDicatmen.fecFechaEmiOf));
			$("#numFolioOficioOperacion").val(data.derivacionDicatmen.numFolioOficio);
			$("#comboAnios").val(data.derivacionDicatmen.ejercicio);
			bloquearForma('devDictamenSeguimientoCorreccionForm');					
			bloquarTabs();
			setTabHabilitado("seguimientoCorreccionDerivarDic");
			alert("Folio Derivado a Dictamen");
			$("#seguimientoCorreccionDerivarDicLI").trigger("click");
			
	}else if(data.cancelacion!=null && data.cancelacion!=undefined){
			$("#refCancelacion").val(data.cancelacion.numFolioOficio);
			$("#fecCancelacionSegCorr").val(formateaFecha(data.cancelacion.fecFechaEmiOf));
			$("#cboMotivos").val(data.cancelacion.idMotivoCancelacion);
		  
		  bloquearForma('cancelacionSeguimientoCorreccionForm');					
		  bloquarTabs();
		  setTabHabilitado("seguimientoCorreccionCancelacion");
		  alert("Folio Cancelado");
		  $("#seguimientoCorreccionCancelacionLI").trigger("click");
	}else if(data.derivaFiscalizacion!=null && data.derivaFiscalizacion!=undefined ){
			//Verificar si se puede reactivar
// 				$("#numFolioOficio").val(data.derivaFiscalizacion.numFolioOficio);
// 				$("#fecDerivFisSegCorr").val(formateaFecha(data.derivaFiscalizacion.fechaDeriva));
// 				//$("#nombreFuncionario").val(data.derivaFiscalizacion.nomUsuarioDeriva);
// 				bloquarTabs();		
// 				bloquearForma('devFiscalizacionSeguimientoCorreccionForm');	
// 				//Revisar reactivacion
// 				if(data.derivaFiscalizacion.fechaSolReactiva!="" && data.derivaFiscalizacion.fechaSolReactiva!=null){					
// 					 bloquarTabs();
// 					 setTabHabilitado("seguimientoCorreccionDerivarFis");
// 					 $("#seguimientoCorreccionDerivarFisLI").trigger("click");
// 					$("#fechaSolReactiva").val(formateaFecha(data.derivaFiscalizacion.fechaSolReactiva));
// 					$("#fechaEnvioSol").val(formateaFecha(data.derivaFiscalizacion.fechaEnvioSol));
// 					$("#numOficioEnvio").val(data.derivaFiscalizacion.numOficioEnvio);
// 					$("#fechaReactiva").val(formateaFecha(data.derivaFiscalizacion.fechaReactiva));
// 					$("#numOficioReactiva").val(data.derivaFiscalizacion.numOficioReactiva);
// 					//$("#nombreFuncionario").val(data.derivaFiscalizacion.nomUserReactiva);
// 					$("#txObservacionesReactivaSegCorr").val(data.derivaFiscalizacion.txObservaciones);					
// 				}else{
// 					 bloquarTabs();
// 					 setTabHabilitado("seguimientoCorreccionReactivar");
// 					 $("#seguimientoCorreccionReactivarLI").trigger("click");
// 				}

				if(data.puedeReactivar==true){
					
					bloquarTabs();	
					setTabHabilitado("seguimientoCorreccionReactivar");
					$("#seguimientoCorreccionReactivarLI").trigger("click");
				}else{
					
					desbloquearTabs();
					setTabDesHabilitado("seguimientoCorreccionReactivar");
				}
				
				if(data.finalizaProceso==true){
					bloquarTabs();	
					alert("Derivacion fiscalizacion terminada");
					bloquearFormas();
				}
		}else{
			setTabDesHabilitado("seguimientoCorreccionReactivar");
		}


	
}


function recuperaMotivosCancelacion(){
	
	 $.postJSON_Sync(getAppContextParaJS()+"/seguimiento/correccion/motivosCancelaccion.do", null, function(objData) {			  
	     var options = '<option value="-1" >--Por favor seleccione--</option>';
			for (var i = 0; i < objData.length; i++) {
		        options += '<option value="' + objData[i].idMotivocancelacion + '">' + objData[i].motivocancelacion + '</option>';
		      }
		 $("#cboMotivos").html(options);
	});
	
}

var  objGestionOperacionCorr;
function consultarFolio(){
	
	deslbloquearFormas();
	desbloquearTabs();
	limpiarFormularios();
	var param={};
	param.numeroFolio=$("#nuFolioInput").val();
	if($("#nuFolioInput").val()==""){
		alert("Favor de ingresar numero de folio");
		return;
	}
	 $.postJSON_Sync(getAppContextParaJS()+"/seguimiento/correccion/gestionOperacion.do", param, function(objData) {			  
		 
		 
		 if(objData.mensaje!=""){
			alert(objData.mensaje); 		
			if(objData.existe==false){
				$("#tabSeguimientoOperacion").hide(); 
				return;
			}
		 }else{
			 desbloquearTabs();
			 $("#tabSeguimientoOperacion").show();
			 $("#tabSegOperacion").show();
			 objGestionOperacionCorr=objData;	
			 recuperaEjerciciosDictaminar();
			 recuperaMotivosCancelacion();
			 cargarDatos(objData);
		
		 }
		 
		 if(objData.presentada==true){
			 $("#tabSeguimientoOperacion").hide(); 
		 }else{
			 $("#tabSeguimientoOperacion").show(); 
		 }
		
	});		
	
}



function generaOperacion(tipoOperacion){
	
	
	switch(tipoOperacion){
		case 1://Deriva Subdelegacion
			if(validaCapturaDerivaSubdelegacion()){
				derivaSubdelegacion();	
			}			
			break;
		case 2://Deriva fiscalizacion
			if(validaCapturaFiscalizacion()){
				derivaFiscalizacion();
			}			
			break;
		case 3://Reactivacion
			if(validaCapturaReactivar()){
				reactivar();
			}			
			break;
		case 4://Derivar Dictamen
			if(validaCapturaDictamen()){
				derivarDictamen();
			}		
			break;
		case 5://Cancelacion
			if(validaCapturaCancelacion()){
				cancelacionFolioCorreccion();
			}			
			break;
	
	}
	
}

function reactivar(){
	
	objGestionOperacionCorr.fechaSolicitudReactivacion=$("#fechaSolReactiva").val();
	objGestionOperacionCorr.fechaEnvioSolicitudNormativo=$("#fechaEnvioSol").val();
	objGestionOperacionCorr.fechaReactivacion=$("#fechaReactiva").val();;
	objGestionOperacionCorr.numeroOficio=$("#numOficioEnvio").val();
	
	objGestionOperacionCorr.numeroOficioReactivacion=$("#numOficioReactiva").val();
	//objGestionOperacionCorr.funcionarioReactivacion=$("#nombreFuncionario").val();;

	if($("#txObservacionesReactivaSegCorr").val().length>200){
		objGestionOperacionCorr.observaciones=$("#txObservacionesReactivaSegCorr").val().substring(0,200);
	}else{		
		objGestionOperacionCorr.observaciones=$("#txObservacionesReactivaSegCorr").val();
	}
	
	
	 $.postJSON_Sync(getAppContextParaJS()+"/seguimiento/correccion/reactivar.do", objGestionOperacionCorr, function(objData) {			  
		 alert(objData.mensaje); 
		 desbloquearTabs();
		 setTabDesHabilitado("seguimientoCorreccionReactivar");
		 $("#seguimientoCorreccionDerivarFisLink").trigger("click");
		 limpiarFormularios();
		 habilitaCamposXForma('devFiscalizacionSeguimientoCorreccionForm');			
		 deslbloquearFormas();
	 });	
	

}

function derivaFiscalizacion(){
	objGestionOperacionCorr.numeroFolio=$("#nuFolioInput").val();
	objGestionOperacionCorr.folioOficioFiscalizacion=$("#numFolioOficio").val();
	objGestionOperacionCorr.fechaDerivacionFiscaliozacion=$("#fecDerivFisSegCorr").val();;


	 $.postJSON_Sync(getAppContextParaJS()+"/seguimiento/correccion/derivarFiscalizacion.do", objGestionOperacionCorr, function(objData) {			  
		 alert(objData.mensaje); 
		 bloquarTabs();
		 bloquearFormas();
		 if(objData.puedeReactivar==true){
			 setTabHabilitado("seguimientoCorreccionReactivar");
		 }else{
			 desbloquearTabs();
			 setTabDesHabilitado("seguimientoCorreccionReactivar");
		 }
		 
		 if(objData.finalizaProceso==true){
			 alert("Derivacion a fiscalizacion terminada");
			 bloquarTabs();
		 }
		 
	});	
}


function cancelacionFolioCorreccion(){
	
	objGestionOperacionCorr.referenciaCancelacion=$("#refCancelacion").val();
	objGestionOperacionCorr.fechaCancelacion=$("#fecCancelacionSegCorr").val();
	objGestionOperacionCorr.motivoCancelacion=$("#cboMotivos").val();
	
	 $.postJSON_Sync(getAppContextParaJS()+"/seguimiento/correccion/cancelacionFolio.do", objGestionOperacionCorr, function(objData) {			  
		 alert(objData.mensaje); 
		 bloquearFormas();
		 bloquarTabs();
		});	
	
}
function derivarDictamen(){
	

	objGestionOperacionCorr.numeroFolio=$("#nuFolioInput").val();
	objGestionOperacionCorr.fechaAutDeAvisoDictamen=$("#fecAutDicSegCorr").val();
	objGestionOperacionCorr.ejercicioDictaminar=$("#comboAnios").val();;
	objGestionOperacionCorr.numerAviso=$("#numFolioOficioOperacion").val();

	
	  var context = getAppContextParaJS();	
	  $.postJSON_Sync(context+"/seguimiento/correccion/derivarDictamen.do", objGestionOperacionCorr, function(objData) {			  
		  alert(objData.mensaje);
		  bloquearFormas();
		  bloquarTabs();
		});	
	
	
}

function derivaSubdelegacion(){
	
	objGestionOperacionCorr.numeroFolio=$("#nuFolioInput").val();
	objGestionOperacionCorr.tipoOperacion="1";
	objGestionOperacionCorr.cveDomicilio=domicilioGuardado.domicilioId;
	objGestionOperacionCorr.fechaDerivacionSubdelegacion=$("#fechaDerivacion").val();
	objGestionOperacionCorr.cveDelegacionDestino=$("#comboDelegacionDDS").val();
	objGestionOperacionCorr.cveSubdelegacionDestino=$("#comboSubDelegacionDDS").val();
	objGestionOperacionCorr.folioOficioDerivacion=$("#folioDerivacion").val();
	//objGestionOperacionCorr.funcionarioRegistraDerSub=$("#nombreFuncionario").val();

	
	
	var context = getAppContextParaJS();	
	  $.postJSON_Sync(context+"/seguimiento/correccion/procesaOperacion.do", objGestionOperacionCorr, function(objData) {			  
		  alert(objData.mensaje); 
		  bloquearFormas();
		  bloquarTabs();
		  $('#botonAgregarDomicilio').hide();
		});		
}



function bloquarTabs(){
	
	$("#accessTabs").val("seguimientoCorreccionReactivar-seguimientoCorreccionDerivarFis-seguimientoCorreccionDerivarSub-seguimientoCorreccionDerivarDic-seguimientoCorreccionCancelacion");
	$("#forbidenTabs").val();
	setTabDesHabilitado("seguimientoCorreccionReactivar");
	setTabDesHabilitado("seguimientoCorreccionDerivarFis");
	setTabDesHabilitado("seguimientoCorreccionDerivarSub");
	setTabDesHabilitado("seguimientoCorreccionDerivarDic");
	setTabDesHabilitado("seguimientoCorreccionCancelacion");
	
	

}


function desbloquearTabs(){
	
	$("#accessTabs").val("seguimientoCorreccionReactivar-seguimientoCorreccionDerivarFis-seguimientoCorreccionDerivarSub-seguimientoCorreccionDerivarDic-seguimientoCorreccionCancelacion");
	$("#forbidenTabs").val();
	setTabHabilitado("seguimientoCorreccionReactivar");
	setTabHabilitado("seguimientoCorreccionDerivarFis");
	setTabHabilitado("seguimientoCorreccionDerivarSub");
	setTabHabilitado("seguimientoCorreccionDerivarDic");
	setTabHabilitado("seguimientoCorreccionCancelacion");
	
	
	$("#spnFechaDerivarSubdel").show();
	$("#spnfecDerivFisSegCorr").show();
	$("#spnFechaDerivarDict").show();
	$("#spnfechaSolReactiva").show();
	$("#spnfechaEnvioSol").show();
	$("#spnfechaReactiva").show();
}


function bloquearForma(formaBloquear){
	$('form#'+formaBloquear +' :input').prop('disabled','disabled');
	$('form#'+formaBloquear +' :input').removeClass("red");
}

function reseteaFecha(idFecha){
	$("#"+idFecha).val("");
}

function validarAlfaNumericoReactivacion(e) { 
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla==8) return true;
    patron = /[1234567890abcdefghijklmn\U00F1opqrstuvwxyzABCDEFGHIJKLMN\U00D1OPQRSTUVWXYZ/]/;
    te = String.fromCharCode(tecla);
    return patron.test(te);
} 
</script>
<html lang="sp">

	<input type="hidden" name="accessTabs" id="accessTabs" value="">
	<input type="hidden" name="forbidenTabs" id="forbidenTabs" value="">
	<div id="cuerpo">
			
			
			
			
			<div class="menu_principal" style="height: 2em !important;"> 
		      <!--inicia menu principal-->
		      <div align="center">
		        <div class="centrado">
		          <ul style="height: 1em !important;">
		            <li><a>Seguimiento a solicitudes no presentadas</a></li>
		          </ul>
		        </div>
		        <!--fin centrado--> 
		      </div>
		    </div>
		    
			<br>	
			
			
			<div style="background-color: #f2fff2;" id="wrapperDialogSolCorr">
<table style="width: 900px" class="tablaverde2">
									<tbody>
										<tr valign="top" class="impar">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>
										<tr valign="top" class="par">   												
											<td width="120px" align="left" class="etiqueta2">
											    	<label id="nuFolioLabel" for="nuFolio">Folio de la Solicitud de Correcci&oacute;n:</label>	    		
											   </td>
											   <td width="80px" align="left">
											   		<input type="text" maxlength="18" size="20" value="" onblur="this.value=(this.value).toUpperCase();" onkeyup="validaCampo('noCaracteresEspeciales','nuFolioInput');mayusculasTextField(this);"  onchange="limpiarFormularios()" name="nuFolio" id="nuFolioInput">
											   		
											   </td>
										
											
										</tr>
										<tr valign="top" class="par">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>	
										<tr align="center">
										  <td align="center" colspan="4">
										  	<a onclick="javascript:consultarFolio()" href="#" id="btnBuscarProrroga"><span class="boton">Buscar</span></a>
										  </td>
										</tr>	
										<tr valign="top" class="impar">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>										
									</tbody>
								</table>
			
			</div> 
			
				<div  id="tabSeguimientoOperacion" style="width: auto; overflow: auto; ">
		<table  style="width: 900px; overflow: auto;" align="center" >
			<tr	>
				<td colspan="6" align="center">			
					<ul class="tabs">
					    <li class="etiqueta2" id="seguimientoCorreccionDerivarSubLI">		<a href="#seguimientoCorreccionDerivarSub" id="seguimientoCorreccionDerivarSubLink">				Derivar a Otra Subdelegaci&oacute;n</a></li>
						<li class="etiqueta2" id="seguimientoCorreccionDerivarFisLI">		<a href="#seguimientoCorreccionDerivarFis" id="seguimientoCorreccionDerivarFisLink">				Derivar a Fiscalizaci&oacute;n</a></li>
						<li class="etiqueta2" id="seguimientoCorreccionReactivarLI">		<a href="#seguimientoCorreccionReactivar" id="seguimientoCorreccionReactivarLink">				Reactivaci&oacute;n</a></li>
						<li class="etiqueta2" id="seguimientoCorreccionDerivarDicLI">		<a href="#seguimientoCorreccionDerivarDic" id="seguimientoCorreccionDerivarDicLink">				Derivar a Dictamen</a></li>
						<li class="etiqueta2" id="seguimientoCorreccionCancelacionLI">		<a href="#seguimientoCorreccionCancelacion" id="seguimientoCorreccionCancelacionLink">			Cancelaci&oacute;n</a></li>									
					
					</ul>		
				</td>
			</tr>
			<tr>
				<td>
					<div id="seguimientoCorreccionDerivarSub" 		 class="tab_content">	<jsp:include page="/WEB-INF/views/operacionCorreccion/devSubDelegacion/devSubDelegacionSeguimientoCorreccion.jsp" /></div>
					<div id="seguimientoCorreccionDerivarFis" 		 class="tab_content">	<jsp:include page="/WEB-INF/views/operacionCorreccion/derivacionFiscalizacion/devFiscalizacionSeguimientoCorreccion.jsp" /></div>
					<div id="seguimientoCorreccionReactivar" 		 class="tab_content">	<jsp:include page="/WEB-INF/views/operacionCorreccion/reactivacion/reactivacionSeguimientoCorreccion.jsp" /></div>
					<div id="seguimientoCorreccionDerivarDic" 		 class="tab_content">	<jsp:include page="/WEB-INF/views/operacionCorreccion/derivacionDictamen/devDictamenSeguimientoCorreccion.jsp" /></div>
					<div id="seguimientoCorreccionCancelacion" 	 	 class="tab_content">	<jsp:include page="/WEB-INF/views/operacionCorreccion/cancelacion/cancelacionSeguimientoCorreccion.jsp" /></div>
					
					
<!-- 					<div id="seguimientoCorreccionDerivarSub" 		 class="tab_content">	</div> -->
<!-- 					<div id="seguimientoCorreccionDerivarFis" 		 class="tab_content">	</div> -->
<!-- 					<div id="seguimientoCorreccionReactivar" 		 class="tab_content">	</div> -->
<!-- 					<div id="seguimientoCorreccionDerivarDic" 		 class="tab_content">	</div> -->
<!-- 					<div id="seguimientoCorreccionCancelacion" 	 	 class="tab_content">	</div> -->
				</td>
			</tr>
		</table>	
	</div>	    
</div>	    
		 
					
	