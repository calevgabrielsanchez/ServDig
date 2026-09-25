
var idSeguimiento = "#dgSeguimientoPromocion";
var idConsultaRegistros		= "#dtConsultaRegistros";
//var idDgCancelacion	= "#dgPromocionCancelacio";
var idDgInvitacion	= "#dgInvitacionGuardar";
var idDgRegulariza = "#dgRegularizaPromocion";
var idDgPromocionConsultaDet = "#dgPromocionConsultaDet";
var idDatosSatic			= "#dgPromocionDatosSaticB";
var idDatosSBC              = "#dgPromocionDatosSBC";

var oDgSeguimeinto;
var oDgConsultaRegistros;
var oDgCancelacion;
var oDgInvitacion;
var oDgRgulariza;
var validaCaptura;
var oDgPromocionConsultaDet;
var oDgDatosSatic;
var oDgDatosSBC;
var valdaDetAct;
var limpiarForm = true;

$(document).ready(function(){

	$( "form#promocionConsultaForm #fechaIncial, form#promocionConsultaForm #fechaFinal, form#CrtRegulapagosdetForm #fecEmision,form#promocionFormConsultaDetAct #fechaAtencion,form#promocionFormConsultaDetAct #fechaNotificacion,form#promocionFormConsultaDetAct #fechaEstimIncio,form#promocionFormConsultaDetAct #fechaEstTerm, form#promocionCancelacionForm #fecCancelacion" ).datepicker( { dateFormat: 'dd-mm-yy' });
	$( "#fecAtencion, #fecNotificacion" ).datepicker( { dateFormat: 'dd-mm-yy' });
	// Para el modal de SEGUIMIENTO
	$( "#fecNotificacionOficio, #fecAtencionOficio, #fecEmision, #fecCancelacion, #fechaAvisoDictamen, #fechaInicio, #fechaFin " ).datepicker( { dateFormat: 'dd-mm-yy' });
	
	$(".tab_content").hide();
	$("ul.tabs li:first").addClass("active").show();
	$(".tab_content:first").show();
	
	$("ul.tabs li").click(function()
		       {
				$("ul.tabs li").removeClass("active");
				$(this).addClass("active");
				$(".tab_content").hide();

				var activeTab = $(this).find("a").attr("href");
				$(activeTab).fadeIn();
				return false;
			});
	
	$("#tdFolioDet").hide();
	$("#tdInputFolioDet").hide();
	$("#tdInputFolioDet2").hide();
	$("#btnGuardar").hide();

	oDgConsultaRegistros = $(idConsultaRegistros).dataTable({
		"bJQueryUI" : true,
		bFilter : false,
		bInfo:true,
		bSort: true,
		"bPaginate": true,
		"bAutoWidth" : true,
		"bServerSide" :	true,
		"aoColumns" : [ {
			fnRender :function(oObj){
				var retVal = '<input type="radio" value="' +
				oObj.aData['cvePromocion'] +'" id="radioTable" class="radioDeteccion" name="radio" onclick="muestraDiv()"/> ';
				return retVal;
			}, 
			aTargets: [0]
		},{
			"sTitle" : "Folio de Promoci\u00F3n",
			"mDataProp" : "nuFoliopromocion",
			"sClass": "dtCenterClassColumn"
		}, {
			"sTitle" : "Elemento de Selecci\u00F3n",
			"mDataProp" : "descCriterioseleccion",
			"sClass": "dtCenterClassColumn"
		}, {
			"sTitle" : "Registro Patronal",
			"mDataProp" : "regPatron",
			"sClass": "dtCenterClassColumn"
		},{
			"sTitle" : "Raz\u00F3n Social",
			"mDataProp" : "razonSocial",
			"sClass": "dtCenterClassColumn"
		},{
			"sTitle" : "Numero de Oficio de la promoci\u00F3n",
			"mDataProp" : "nuOficiopro",
			"sClass": "dtCenterClassColumn"
		}, {
			"sTitle" : "Fecha de del oficio de promoci\u00F3n",
			"mDataProp" : "fecFechaemisionpro",
			"sClass":"dtCenterClassColumn"
		}
		],"bProcessing" : true,
		"sAjaxSource" : 'consulta/paginar.do',
		"fnServerData" : function(sSource, aoData, fnCallback) {				

			var wrapper = new Object();
			wrapper.aoData = aoData;								

			var oForm = $("#promocionConsultaForm").toObject({mode:'first'});
			var fechaInicial = $("form#promocionConsultaForm #fechaIncial").val();
			var fechaFinal = $("form#promocionConsultaForm #fechaFinal").val();
			wrapper.oForm = oForm;

			$.postJSON(sSource, wrapper, function(data) {
				fnCallback(data);
				if(data.aaData!=null){					
				 	if(data.aaData.length<=0 && fechaInicial!="" && fechaFinal!=""){
				 		$('#labelError').html('<label style="color: red;">No se encontraron resultados de su b&uacute;squeda, por favor realice nueva b&uacute;squeda</label>');
				 	}else{
				 		$('#labelError').html('');
				 	}
				 }				 
			}).complete(function(){
				desbloquear();
			});
		}
	});
	/*
	oDgCancelacion = $(idDgCancelacion).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 930,
		beforeClose: function(event,ui){
			limpiarFormulario("#promocionCancelacionForm");
		},
		buttons: {
			"Confirmar": function() {				
				if($("form#promocionCancelacionForm #nuVolanteCancela").val()==""){
					$('#nuVolanteCancelaError').html('<label style="color: red;">Este campo es requerido</label>');
					return false;
				}else{
					$('#nuVolanteCancelaError').html('');
				}
				if($("form#promocionCancelacionForm #fechaCancelacion").val()==""){
					$('#fechaCancelacionError').html('<label style="color: red;">Este campo es requerido</label>');
					return false;
				}else{
					$('#fechaCancelacionError').html('');
				}
				if(!checaCero($("#idMotivoCancelacion").val(),"Motivo de Cancelacion")) return false;
				var crtPromocion = $("#promocionCancelacionForm").toObject({mode:'first'});
				$.postJSON("consulta/cancelar.do", crtPromocion, function(data) {																						
					oDgConsultaRegistros.fnDraw();							
					oDgCancelacion.dialog("close");	
					$("#buttons").hide();
					alert("La obra promocionada a sido cancelada con "+\u00e9+"xito");
				}).error(function(data){ 
					validarSesionExpirada(data);
				}).complete(function(){
					//Instrucciones para el complete													
				});
					$(this).dialog("close");							
			}, 
			"Salir": function() { 
				$(this).dialog("close"); 
			} 
		}
	});*/
	
	oDgPromocionConsultaDet = $(idDgPromocionConsultaDet).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 930,
		beforeClose :function(event,ui){	
			if(limpiarForm){
				$("#divNumReporte").hide();
				$("#divDatosCenso").hide();
				$("#divActividad").hide();
				$("#divDatosObra").hide();
				$("#divCombosObra").hide();
				limpiarFormulario("#dgPromocionConsultaDetAct");
			}
		},
		buttons: {
			"Regularizar": function() {
				if(valdaDetAct.form()){
					var deteccion = $("#promocionFormConsultaDetAct").serializeObject(true);
					$.postJSON("consulta/actualiza.do", deteccion, function(data) {
						limpiarForm = true;
						oDgPromocionConsultaDet.dialog('close');
						regularizacion('promocionFormConsultaDetAct');						
					}).error(function(data){ 
						desbloquear();
						validarSesionExpirada(data);
					}).complete(function(){
						//Instrucciones para el 'complete'
					});					
				}															
			}, 
			"Regresar": function() { 
				$(this).dialog("close");
				limpiarForm = true;				
			} 
		}
	});
	
	oDgInvitacion = $(idDgInvitacion).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 720,
		buttons: {}
	});
	
	oDgDatosSatic = $(idDatosSatic).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 930,
		open:function(event, ui)
        {					
			var deteccion = $("#promocionFormDatosSaticB").serializeObject(true);
			$.postJSON("consulta/periodos.do", deteccion, function(datas) {
				  	 var options;
				  	 var options2;
					 if(datas!=null)
					   for (var i = 0; i < datas.length; i++) {
						   if(datas[i].substring(6,7)==0) 
							   options += "<option value='"+ datas[i].substring(0,6) +"'>"+ datas[i].substring(0,6) +"</option>";
						   else
							   options2 += "<option value='"+ datas[i].substring(0,6) +"'>"+ datas[i].substring(0,6) +"</option>";
				       }
					 $('select#periodosfaltantes').html(options);
					 $('select#periodosPresentados').html(options2);
					//Agrega las opciones al control
				}).error(function(datas){ 
					validarSesionExpirada(datas);
				}).complete(function(){
					//Instrucciones para el 'complete'
				});
        },
		buttons: {
			"Regularizar": function(){
				oDgDatosSatic.dialog('close');
				regularizacion('promocionFormDatosSaticB');
			},
			"Regresar": function() { 
				$(this).dialog("close"); 								
			} 
		}
	});
 
	oDgDatosSBC = $(idDatosSBC).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 930,
		buttons: {
			"Regularizar": function(){
				$("#labelFecAte").html('');
				$("#labelFecNot").html('');
				if($("#fecAtencion").val() != '' && $("#fecNotificacion").val() != '' && $("#razonSocialSBC").val() != ''){
					var cvePromocion = $("#cvePromocion").val();
					var fecAtencion  = $("#fecAtencion").val();
					var fecNotificacion  = $("#fecNotificacion").val();
					var registroPatronal  = $("#registroPatronalSBC").val();
					var cveFKPatron  = $("#cveFKPatron").val();
					var variable = '{' +
					   '"cvePromocion":"'+cvePromocion+'",'+
					   '"fechaAtencion":"'+fecAtencion+'",'+
					   '"fechaNotificacion":"'+fecNotificacion+'",'+
					   '"cveFkPatron":"'+cveFKPatron+'",'+
					   '"regPatron":"'+registroPatronal+'"}';
					var variableJson = jQuery.parseJSON(variable);
					$.postJSON("consulta/actualizaPromocionSBC.do", variableJson, function(data) {
						oDgDatosSBC.dialog('close');
						regularizacionSBC();						
					}).error(function(data){ 
						desbloquear();
						validarSesionExpirada(data);
					}).complete(function(){
						oDgDatosSBC.dialog('close');
					});		
				}else{
					if($("#fecAtencion").val() == ''){
						 $("#labelFecAte").html('<label class="etiquetaError">Campo requerido</label>');
					}
					if($("#fecNotificacion").val() == ''){
						$("#labelFecNot").html('<label class="etiquetaError">Campo requerido</label>');
					}
					if($("#razonSocialSBC").val() == ''){
						$("#labelRegistroPatronalSBC").html('<label class="etiquetaError">Campo requerido</label>');
					}
				}
				
			},
			"Regresar": function() { 
				$(this).dialog("close"); 								
			} 
		}
	});
	
	/**
	 * Modal para Seguimeinto
	 * @author EDJ
	 * 06/03/2012
	 */
	
	oDgSeguimeinto = $(idSeguimiento).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 930,
		//close: iniciaPantallaUISeguimiento(),
		buttons: {
			"Salir": function() { 
				
				$(this).dialog("close"); 	
				iniciaPantallaUISeguimiento();
			} 
		}
	});
	

	
	valdaDetAct = $("#promocionFormConsultaDetAct").validate({
		 rules: {
			 fechaAtencion:{
				 required: true
			 },
			 fechaNotificacion:{
				 required: true
			 },
			 fechaDeteccion: {
				 required: true
			 },
			 nomRazonsocial: {
				 required: true
			 },
			 regPatron: {
				 required: true				 
			 },
			 fechaEstimIncio: {
				 required: true
			 }			 
		 }
	});
	
	oDgRgulariza = $(idDgRegulariza).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 720,
		beforeClose: function(event,ui){
			$("form#promocionRegularizaForm #fechaAtencionPro").prop('disabled','');
			$("form#promocionRegularizaForm #fechaPAI").prop('disabled','');
			$("form#promocionRegularizaForm #porRegularizado").prop('disabled','disabled');
			$("form#promocionRegularizaForm #porAvance").prop('disabled','disabled');
			$("form#promocionRegularizaForm #fecPerIni").prop('disabled','disabled');
			$("form#promocionRegularizaForm #fecPerFin").prop('disabled','disabled');
			$("form#promocionRegularizaForm #numTrabrevisados").prop('disabled','disabled');
			$("form#promocionRegularizaForm #numTrabomisos").prop('disabled','disabled');
			$("form#promocionRegularizaForm #numTrabsubdclara").prop('disabled','disabled');
		},
		open:function(event, ui)
		{
			$('form#promocionRegularizaForm #txCopSp').val("");
			$('form#promocionRegularizaForm #txCopAct').val("");
			$('form#promocionRegularizaForm #txCopRec').val("");
			$('form#promocionRegularizaForm #txCopTp').val("");
			$('form#promocionRegularizaForm #txRcvSp').val("");
			$('form#promocionRegularizaForm #txRcvAct').val("");
			$('form#promocionRegularizaForm #txRcvRec').val("");
			$('form#promocionRegularizaForm #txRcvTp').val("");			
			resumenPagos();
			if($("form#promocionRegularizaForm #tipoProm").val()=='EX'){
				$("#convenio").show();
				$("#parcialidades").show();
			}
		}
	});
	
	$("#btnValidar").click(function(){
		bloquear();
		var patron = $("form#invitacionFormRegistro #registroPatronal").val();
		$.postJSON("consulta/validaPatron.do",patron,function(data) { 
			if(data == null){
				alert("El registro Patronal es invalido");
				$("form#invitacionFormRegistro #razonSocial").val('');
			}
			else if(data != null && data.razonSocial != null){
				alert("El registro Patronal es valido");
				$("form#invitacionFormRegistro #razonSocial").val(data.razonSocial);							
			}
			desbloquear();
		}).error(function(data){ 
			desbloquear();
			validarSesionExpirada(data);			
		}).complete(function(){						
			desbloquear();												
		});	

	});
	
	$("form#promocionFormConsultaDetAct #cvePkTipObra").change(function(){
		if($("form#promocionFormConsultaDetAct #cvePkTipObra").val()!=-1){
			$("form#promocionFormConsultaDetAct #cvePkFaseConst").prop('disabled','disabled');			
		}else{
			$("form#promocionFormConsultaDetAct #cvePkFaseConst").prop('disabled','');
		}
	});
	
	$("form#promocionFormConsultaDetAct #cvePkFaseConst").change(function(){
		if($("form#promocionFormConsultaDetAct #cvePkFaseConst").val()!=-1){
			$("form#promocionFormConsultaDetAct #cvePkTipObra").prop('disabled','disabled');			
		}else{
			$("form#promocionFormConsultaDetAct #cvePkTipObra").prop('disabled','');
		}
	});
	
	 $("#btnValidarSBC").click(function(event){
		 event.preventDefault();
		 $("#labelRegistroPatronalSBC").html('');
		 $("#razonSocialSBC").val('');
		
		
		 var patron = $("#registroPatronalSBC").val();
		 patron = patron.substring(0,patron.length - 1);
		 if(patron != '' && patron.length >= 10){							 
			 bloquear();
			 oDgDatosSBC.dialog('close');	
			$.postJSON("consulta/validaPatron.do",patron,function(data) { 
				if(data == null){
					$("#labelRegistroPatronalSBC").html('<label class="etiquetaError">El registro patronal no es valido</label>');
					$("#razonSocialSBC").val('');
					oDgDatosSBC.dialog('open');	
					desbloquear();	
				}
				else if(data != null && data.razonSocial != null){
					
					$("#razonSocialSBC").val(data.razonSocial);
					$("#cveFKPatron").val(data.cvePK);
					oDgDatosSBC.dialog('open');	
					desbloquear();	
				}
			}).error(function(data){ 
				desbloquear();	
			}).complete(function(){
				desbloquear();	
			});
	 }else{
		 $("#labelRegistroPatronalSBC").html('<label class="etiquetaError">Capturar un registro patronal valido</label>');
		 $("#razonSocialSBC").html('');
	 }
	 });
	
	 llenarCombo();
	 
	 
	 $("#btnGuardarInv").click(
				function() {
					if(validaInvitacion()){
						var crtInvitacion = $("#invitacionFormRegistro").toObject({mode:'first'});
						$.postJSON("consulta/agregaInvitacion.do", crtInvitacion, function(data) {
							$("form#seguimientoForm #fecOfiInvitacion").val($("form#invitacionFormRegistro #fecEmision").val());
							oDgConsultaRegistros.fnDraw();							
							oDgInvitacion.dialog("close");
						}).error(function(data){ 
							validarSesionExpirada(data);
						}).complete(function(){
							limpiarFormulario("#invitacionFormRegistro");
							//Instrucciones para el complete													
						});											
					}		
				});
	 
	 
	/**seccion para definir la fecha maxima del servidor y establecerle un limite maximo a las fechas
	 * maximas de los calendarios para las fechas siguientes
	 */  
	$.postJSON(getAppContextParaJS() + "/promocion/consulta/obtenerFechaServidor.do", null,function(data) {
		}).error(function(data){
	validarSesionExpirada(data);
	}).complete(function(data){

		$("form#seguimientoForm #fecNotificacionOficio").datepicker('option', 'maxDate', data.responseText);
		$("form#seguimientoForm #fecAtencionOficio").datepicker('option', 'maxDate', data.responseText);
		$("form#promocionCancelacionForm #fecCancelacion").datepicker('option', 'maxDate', data.responseText);
		//$("form#promocionCancelacionForm #fecCancelacion").datepicker('option', 'maxDate', data.responseText);
		$("form#autAviDictamenSegForm #fechaAvisoDictamen").datepicker('option', 'maxDate', data.responseText);
	
	});
	
	/**seccion para definir la fecha minima del servidor y establecerle un limite minima a las fechas
	 * maximas de los calendarios para las fechas siguientes
	 */ 
	$.postJSON(getAppContextParaJS() + "/promocion/consulta/obtenerFechaServidorMinima.do", null,function(data) {
	}).error(function(data){
		validarSesionExpirada(data);
	}).complete(function(data){
		//alert(JSON.stringify(data, null, 4));
		$("form#seguimientoForm #fecNotificacionOficio").datepicker('option', 'minDate', data.responseText);
		$("form#seguimientoForm #fecAtencionOficio").datepicker('option', 'minDate', data.responseText);
		$("form#promocionCancelacionForm #fecCancelacion").datepicker('option', 'minDate', data.responseText);
		$("form#autAviDictamenSegForm #fechaAvisoDictamen").datepicker('option', 'minDate', data.responseText);
		
		
	});
	 
	//parte de los tabs del seguimiento de promocion
	 $('#tab_container_href').tabs({
		 cache: false,
	     spinner: 'Cargando...',
		 ajaxOptions: {
			cache: false,
			type:'POST',
			error: function( xhr, status, index, anchor ) {
				$( anchor.hash ).html(
					"No se pudo obtener la información..." );
				}
			}
		});
	 $("#tab_container_href").tabs({ disabled: [2] });
	 $('#cancelacionTAB').show('slow');
	 
	 
	 desabilitaForma();

	 
	 $(idSeguimiento).bind( "dialogclose", function(event, ui) {
		  iniciaPantallaUISeguimiento();
		});
});

/**
 * Metodo que llena el cambo  de tipos de Promocion 
 */
function llenarCombo() {
	$.postJSON("consulta/llenarTiposCorreccion.do",'null',function(data) { 
		if(data != null){
			var myselect=document.getElementById("tipoPromocion");
			myselect.options.length = 1;
			for(var i = 0 ; i < data.length ; i++){
			var elOptNew = document.createElement('option');
			elOptNew.text = data[i].txDescripcion;
			elOptNew.value = data[i].cveTipocorr;
				myselect.add(elOptNew);
			}	
		}
		
	});
	
}

function buscar(){
	
	if(jsValidaBuscar()){
		$("#promocionDisponiblesPromocion").show();
		$("#buttons").hide();
		oDgConsultaRegistros.fnDraw();	
		bloquear();	
	}
}

function muestraDiv(){
	$("#buttons").show();
}

function Cancelar(){		
	
	var radios = document.getElementsByName("radio");
	for (i=0;i<radios.length;i++)
	 {
		if(radios[i].checked)
		{
			var idPromocion = radios[i].value;
			var sPromocion = '{"cvePromocion":'+idPromocion+'}';
			var promocion = jQuery.parseJSON(sPromocion);
			$.postJSON("consulta/consultaPorClaveUser.do", promocion, function(data) {				
				if(data.fecFechaAtencion!=null){					
					alert('la promoci'+'\u00f3'+'n no se puede cancelar por que contiene pagos');
				}else{
					$('#wrapperDialogCancelacion,#promocionCancelacionForm,#cvePromocion').val(promocion.cvePromocion);			
					$('#promocionCancelacionForm #labelFuncionario').val(data.razonSocial);	
					oDgCancelacion.dialog('open');
				}									
			}).error(function(data){ 
				validarSesionExpirada(data);
			}).complete(function(){
				//Instrucciones para el complete													
			});			
		}
	 }
}

function invitacion(){		
	var radios = document.getElementsByName("radio");
	$('#cancelacion').attr('href','');
	for (i=0;i<radios.length;i++)
	 {
		if(radios[i].checked)
		{
			var idPromocion = radios[i].value;
			var sPromocion = '{"cvePromocion":'+idPromocion+'}';
			var crtPromocion = jQuery.parseJSON(sPromocion);									
			$.postJSON("consulta/consultaInvitacion.do", crtPromocion, function(data) {	
				if(data.cvePromocion != null){
					$('form#invitacionFormRegistro #cvePromocion').val(data.cvePromocion);
				}
				if(data.cveDeteccion != null){
					$('form#invitacionFormRegistro #cveDeteccion').val(data.cveDeteccion);
				}
				$('form#invitacionFormRegistro #patron').val(data.cveFkPatron);
				$('form#invitacionFormRegistro #folioPromocion').val(data.folioPromocion);
				if(data.nuOficioinv != null){
					$('form#invitacionFormRegistro #oficio').val(data.nuOficioinv);
					$('form#invitacionFormRegistro #oficio').prop('disabled','disabled');
				}
				if(data.fechaEmision != null){
					$('form#invitacionFormRegistro #fecEmision').val(data.fechaEmision);
					$('form#invitacionFormRegistro #fecEmision').prop('disabled','disabled');
					$('form#invitacionFormRegistro #btnGuardarInv').prop('disabled','disabled');
				}
				if(data.RegPatronal != null){
					$('form#invitacionFormRegistro #registroPatronal').val(data.regPatronal);
					$('form#invitacionFormRegistro #registroPatronal').prop('disabled','disabled');
					$('form#invitacionFormRegistro #btnValidar').prop('disabled','disabled');				}
				
				$('form#invitacionFormRegistro #razonSocial').val(data.patron);
								
			}).error(function(data){ 
				validarSesionExpirada(data);
			}).complete(function(){
				//Instrucciones para el complete													
			});
			
		}
	 }
}

function validaInvitacion(){
	if($("form#invitacionFormRegistro #oficio").val()=="" && $("form#invitacionFormRegistro #fecEmision").val()==""){		
		$("#labelOficio").html('<label style="color: red;">Campo requerido</label>');
		$("#labelFecEmision").html('<label style="color: red;">Campo requerido</label>');
		return false;
	}else if($("form#invitacionFormRegistro #oficio").val()=="" && $("form#invitacionFormRegistro #fecEmision").val()!=""){
		$("#labelOficio").html('<label style="color: red;">Campo requerido</label>');
		return false;
	}else if($("form#invitacionFormRegistro #oficio").val()!="" && $("form#invitacionFormRegistro #fecEmision").val()==""){
		$("#labelFecEmision").html('<label style="color: red;">Campo requerido</label>');
		return false;
	}else return true;
}

function regularizacion(form){
	var idPromocion = $('form#'+form+' #cvePromocion').val();
	var sPromocion = '{"cvePromocion":'+idPromocion+'}';
	var crtPromocion = jQuery.parseJSON(sPromocion);									
	$.postJSON("consulta/consultaRegularizacion.do", crtPromocion, function(data) {
		if(data.cveRegulapagos==0){
			$('form#promocionRegularizaForm #cveRegulapagos').val('');
		}else{
			$('form#promocionRegularizaForm #cveRegulapagos').val(data.cveRegulapagos);
		}
		if(data.fechaAtencionPro!=null && data.fechaAtencionPro!=""){
			$("form#promocionRegularizaForm #fechaPAI").prop('disabled','disabled');
			$("form#promocionRegularizaForm #porRegularizado").prop('disabled','');
			$("form#promocionRegularizaForm #porAvance").prop('disabled','');
			$("form#promocionRegularizaForm #fecPerIni").prop('disabled','');
			$("form#promocionRegularizaForm #fecPerFin").prop('disabled','');
			$("form#promocionRegularizaForm #numTrabrevisados").prop('disabled','');
			$("form#promocionRegularizaForm #numTrabomisos").prop('disabled','');
			$("form#promocionRegularizaForm #numTrabsubdclara").prop('disabled','');			
		}
		$('form#promocionRegularizaForm #fechaAtencionPro').val(data.fechaAtencionPro);
		$('form#promocionRegularizaForm #fechaPAI').val(data.fechaPAI);
		$('form#promocionRegularizaForm #tipoProm').val(data.tipoProm);
		$('form#promocionRegularizaForm #cvePromocion').val(data.cvePromocion);
		$('form#promocionRegularizaForm #fecPeriodo').val(data.fechaInicio);
		$('form#promocionRegularizaForm #regPatron').val(data.regPatron);
		$('form#promocionRegularizaForm #porRegularizado').val(data.porRegularizado);
		$('form#promocionRegularizaForm #porAvance').val(data.porAvance);
		$('form#promocionRegularizaForm #fecPerIni').val(data.fecPerIni);
		$('form#promocionRegularizaForm #fecPerFin').val(data.fecPerFin);
		$('form#promocionRegularizaForm #numTrabrevisados').val(data.numTrabrevisados);
		$('form#promocionRegularizaForm #numTrabomisos').val(data.numTrabomisos);
		$('form#promocionRegularizaForm #numTrabsubdclara').val(data.numTrabsubdclara);
		$('form#promocionRegularizaForm #numTrabReg').val(data.numTrabomisos+data.numTrabsubdclara);
		oDgRgulariza.dialog("open");
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
		//Instrucciones para el complete													
	});		
}

function validaFecha(form,fec,fec2,leyendafec,leyendafec2){
	fec = "form#"+form+" #"+fec+"";
	fec2 = "form#"+form+" #"+fec2+"";
	if($(fec2).val()!="") {
		if(!validaFechas($(fec2).val(),$(fec).val())) {
			alert("La "+leyendafec+" es menor a la "+leyendafec2);
			$(fec2).val("");
		}
		if (!jsValidaFechasEjercicio($(fec2).val(),$(fec).val())) {
			alert("La fechas deben corresponder al mismo ejercicio");
			$(fec2).val("");
		}
	}	
}

function validafechaSistema(form,fec,leyendafec){
	fec = "form#"+form+" #"+fec+"";
	var fechaSis = new Date();
	var diaS = fechaSis.getDate();
	var mesS = fechaSis.getMonth() + 1;
	var anioS = fechaSis.getFullYear()+"";
	if(diaS<10){
		diaS = "0"+diaS;
	}
	if(mesS<10){
		mesS = "0"+mesS;
	}
	var fec2 = diaS+"-"+mesS+"-"+anioS;
	if($(fec).val()!=""){
		if(!validaFechas(fec2,$(fec).val())){
			alert("La "+leyendafec+" debe ser menor a la Fecha del sistema");
			$(fec).val("");
		}
	}
}

function valorMaxim(campo,leyenda,form){
	campo = "form#"+form+" #"+campo;
	if(!valorMaximo($(campo).val(),leyenda,100)){		
		$(campo).val("");		
	}
}

/** 
 * Funcion que ejecuta la peticion para el boton de "Seguimiento" en la pantalla de
 * seguimiento de promocion de acuerdo al criterio de seleccion Seleccionado:
 * SATIC A - 3
 * SATIC B - 4
 * EXHORTO DE CONSTRUCCION - 5
 * EXHORTO DE LO ORDINARIO - 6
 * SALARIO BASE DE COTIZACIÓN - 7
 * */
function muestra(){
	bloquear();
	var pantalla;
	
	if($("form#promocionConsultaForm #tipoPromocion").val()=="4"){
		var radios = document.getElementsByName("radio");
		for (i=0;i<radios.length;i++)
		 {
			if(radios[i].checked)
			{
		
				var idPromocion = radios[i].value;
				var sPromocion = '{"cvePromocion":'+idPromocion+'}';
				var promocion = jQuery.parseJSON(sPromocion);
				// Buscamos el elemento
				$.postJSON("consulta/muestraSatic.do", promocion, function(data) {
					$("form#promocionFormDatosSaticB #sdelegOrig").val(data.sdelegOrig);
					$("form#promocionFormDatosSaticB #cveTipocorr").val(data.cveTipocorr);
					$("form#promocionFormDatosSaticB #cveFkPatron").val(data.cveFkPatron);
					$("form#promocionFormDatosSaticB #cvePromocion").val(data.cvePromocion);
					$("form#promocionFormDatosSaticB #nomRazonsocial").val(data.nomRazonsocial);
					$("form#promocionFormDatosSaticB #regPatron").val(data.regPatron);				
					$("form#promocionFormDatosSaticB #txRfcpatron").val(data.txRfcpatron);
					$("form#promocionFormDatosSaticB #txCurppatron").val(data.txCurppatron);
					$("form#promocionFormDatosSaticB #numRegObra").val(data.numRegObra);
					$("form#promocionFormDatosSaticB #incidencia").val(data.incidencia);
					$("form#promocionFormDatosSaticB #fechaIncial").val(data.fechaIncial);
					$("form#promocionFormDatosSaticB #fechaFinal").val(data.fechaFinal);
					$("form#promocionFormDatosSaticB #refColonia").val(data.refColonia);
					$("form#promocionFormDatosSaticB #numNroext").val(data.numNroext);
					$("form#promocionFormDatosSaticB #domCalle").val(data.domCalle);
					$("form#promocionFormDatosSaticB #numCodigopostal").val(data.numCodigopostal);
					oDgDatosSatic.dialog("open");					
				}).error(function(data){ 
					validarSesionExpirada(data);
				}).complete(function(){
					//Instrucciones para el 'complete'
					desbloquear();
				});
			}
		 }
	}if($("form#promocionConsultaForm #tipoPromocion").val()=="7"){
		var radios = document.getElementsByName("radio");
		for (i=0;i<radios.length;i++)
		 {
			if(radios[i].checked)
			{
		
				var idPromocion = radios[i].value;
				var sPromocion = '{"cvePromocion":'+idPromocion+'}';
				var promocion = jQuery.parseJSON(sPromocion);
				// Buscamos el elemento
				$.postJSON("consulta/muestraSBC.do", promocion, function(data) {
					
					$("#cvePromocion").val(data.cvePromocion);
					$("#descCriterioseleccion").val(data.descCriterioseleccion);
					$("#nuOficiopro").val(data.nuOficiopro);
					$("#fecOficioPromocion").val(data.fechaOficio);
					$("#observaciones").val(data.txObservaciones);
					$("#calle").val(data.domCalle);
					$("#colonia").val(data.refColonia);
					$("#numExt").val(data.numNroext);
					$("#numInt").val(data.numNroint);
					$("#cp").val(data.numCodigopostal);
					$("#registroPatronalSBC").val(data.regPatron);
					$("#razonSocialSBC").val(data.razonSocial);
					$("#cveFKPatron").val(data.cveFkPatron);
					
					$("#fecAtencion").val("");
					$("#fecNotificacion").val("");
					
					
					
					oDgDatosSBC.dialog("open");					
				}).error(function(data){ 
					validarSesionExpirada(data);
				}).complete(function(){
					//Instrucciones para el 'complete'
					desbloquear();
				});
			}
		 }
	}if($("form#promocionConsultaForm #tipoPromocion").val()=="6"){
		var radios = document.getElementsByName("radio");
		for (i=0;i<radios.length;i++)
		 {
			if(radios[i].checked)
			{
		
				var idPromocion = radios[i].value;
				var sPromocion = '{"cvePromocion":'+idPromocion+'}';
				var promocion = jQuery.parseJSON(sPromocion);
				// Buscamos el elemento
				$.postJSON("consulta/muestraEXO.do", promocion, function(data) {
					// llenar JSP
					$("form#seguimientoForm #criterioSeleccion").html('<label>' + data.descCriterioseleccion + '</label>');
					$("form#seguimientoForm #numFolioPromocion").html('<label>' + data.nuFoliopromocion + '</label>');
					$("form#seguimientoForm #fecOficio").html('<label>' + data.fechaOficio + '</label>');
					$("form#seguimientoForm #numOficioPromocion").html('<label>' + data.nuOficiopro + '</label>');
					$("form#seguimientoForm #registroPatronal").html('<label>' + data.regPatron + '</label>');
					$("form#seguimientoForm #razonSocial").html('<label>' + data.razonSocial + '</label>');
					$("form#seguimientoForm #domCalle").html('<label>' + data.domCalle + '</label>');
					$("form#seguimientoForm #colonia").html('<label>' + data.refColonia + '</label>');
					if(data.numNroext != null){
						$("form#seguimientoForm #numExt").html('<label>' + data.numNroext + '</label>');
					}
					if(data.numNroint != null){
						$("form#seguimientoForm #numInt").html('<label>' + data.numNroint + '</label>');
					}
					$("form#seguimientoForm #cp").html('<label>' + data.numCodigopostal + '</label>');
					$("form#seguimientoForm #cvePromocion").val(data.cvePromocion);
					if(data.fecSolCorr != ''){
						$("form#seguimientoForm #fecSol").val(data.fecSolCorr);
					}
					if(data.fechaIncial != ''){
						$("form#seguimientoForm #fecSolCorrIni").val(data.fechaIncial);
					}
					if(data.fechaFinal != ''){
						$("form#seguimientoForm #fecSolCorrFin").val(data.fechaFinal);
					}
					if(data.fechaNotificacion != '' && data.fechaNotificacion != null){
						//$("form#seguimientoForm #fecNotificacionOficio").val(data.fechaNotificacion);
						//$("form#seguimientoForm #fecNotificacionOficio").attr('disabled','disabled');
					}
					if(data.fechaAtencion != '' && data.fechaAtencion != null){
						//$("form#seguimientoForm #fecAtencionOficio").val(data.fechaAtencion);
						//$("form#seguimientoForm #fecAtencionOficio").attr('disabled','disabled');
					}
					if(data.fecOficioInvitacion != ''){
						$("form#seguimientoForm #fecOfiInvitacion").val(data.fecOficioInvitacion);
					}
					$("form#seguimientoForm #observaciones").val(data.txObservaciones);
					
					oDgSeguimeinto.dialog("open");	
				}).error(function(data){ 
					validarSesionExpirada(data);
				}).complete(function(){
					//Instrucciones para el 'complete'
					desbloquear();
				});
			}
		 }
	}else{
		var radios = document.getElementsByName("radio");
		for (i=0;i<radios.length;i++)
		 {
			if(radios[i].checked)
			{
		
				var idPromocion = radios[i].value;
				var sPromocion = '{"cvePromocion":'+idPromocion+'}';
				var promocion = jQuery.parseJSON(sPromocion);
				// Buscamos el elemento
				$.postJSON("consulta/mostrar.do", promocion, function(data) {
					$('form#promocionFormConsultaDetAct #cvePromocion').val(idPromocion);
					$('form#promocionFormConsultaDetAct #cveDeteccion').val(data.cveDeteccion);		
					$('form#promocionFormConsultaDetAct #sdelegOrig').val(data.sdelegOrig);
					$('form#promocionFormConsultaDetAct #cveTipocorr').val(data.cveTipocorr);
					$('form#promocionFormConsultaDetAct #cveFkPatron').val(data.cveFkPatron);
					$('form#promocionFormConsultaDetAct #domicilioId').val(data.domicilioId);
					$('form#promocionFormConsultaDetAct #idObligado').val(data.idObligado);
					$('form#promocionFormConsultaDetAct #idPromovido').val(data.idPromovido);
					$('form#promocionFormConsultaDetAct #fechaRegistro').val(data.fechaRegistro);				
					$('form#promocionFormConsultaDetAct #nuFoliodeteccion').val(data.nuFoliodeteccion);
					$('form#promocionFormConsultaDetAct #fechaDeteccion').val(data.fechaDeteccion);
					$('form#promocionFormConsultaDetAct #fechaEmision').val(data.fechaEmision);
					$('form#promocionFormConsultaDetAct #fechaNotificacion').val(data.fechaNotificacion);
					$('form#promocionFormConsultaDetAct #fechaAtencion').val(data.fechaAtencion);
					if(data.cveTipocorr==8){			
						$('form#promocionFormConsultaDetAct #divActividad').show('fast');			
						$('form#promocionFormConsultaDetAct #nomRazonsocial').val(data.nomRazonsocial);			
						$('form#promocionFormConsultaDetAct #regPatron').val(data.regPatron);
						$('form#promocionFormConsultaDetAct #desDepcontratante').val(data.desDepcontratante);
						$('form#promocionFormConsultaDetAct #desDependenciapub').val(data.desDependenciapub);
						$('form#promocionFormConsultaDetAct #numTrabajdores').val(data.numTrabajdores);
					}else{	
						$("#divDatosCenso").show("fast");
						$("#divNumReporte").show("fast");
						$('form#promocionFormConsultaDetAct #nuReportectrlobra').val(data.nuReportectrlobra);
						$('form#promocionFormConsultaDetAct #txRfcpatron').val(data.txRfcpatron);
						$('form#promocionFormConsultaDetAct #nomRazonsocial').val(data.nomRazonsocial);			
						$('form#promocionFormConsultaDetAct #regPatron').val(data.regPatron);
						$('form#promocionFormConsultaDetAct #txCurppatron').val(data.txCurppatron);
						$('form#promocionFormConsultaDetAct #txEmail').val(data.txEmail);
						$('form#promocionFormConsultaDetAct #txTelefono').val(data.txTelefono);													
						$('form#promocionFormConsultaDetAct #impCostoobra').val(data.impCostoobra);
						$('form#promocionFormConsultaDetAct #porAvanceobraEst').val(data.porAvanceobraEst);
						$('form#promocionFormConsultaDetAct #canSuperficie').val(data.canSuperficie);
						$('form#promocionFormConsultaDetAct #desDepcontratante').val(data.desDepcontratante);
						$('form#promocionFormConsultaDetAct #desDependenciapub').val(data.desDependenciapub);
						$('form#promocionFormConsultaDetAct #numTrabajdores').val(data.numTrabajdores);
//						if((data.tipClaseobra!=null && data.tipClaseobra!="") && ((data.tipoObra!=null && data.tipoObra!="") || (data.faseObra!=null && data.faseObra!=""))){
//							$("#divDatosObra").show("fast");						
//							$('form#promocionFormConsultaDetAct #tipoObra').val(data.tipoObra);
//							$('form#promocionFormConsultaDetAct #faseObra').val(data.faseObra);
//						}else{
						$("#divCombosObra").show("fast");
						$('form#promocionFormConsultaDetAct #cvePkTipObra').val(data.cvePkTipObra);
						$('form#promocionFormConsultaDetAct #cvePkFaseConst').val(data.cvePkFaseConst);
//						}
					}
					if(data.cveFkZona!=null)
						$('form#promocionFormConsultaDetAct #cveFkZona').val(data.cveFkZona);
					if(data.fecFechaterminoEst!=null)
						$('form#promocionFormConsultaDetAct #fecFechaterminoEst').val(data.fechaFinal);
					$('form#promocionFormConsultaDetAct #fechaEstimIncio').val(data.fechaIncial);
					$('form#promocionFormConsultaDetAct #tipClaseobra').val(data.tipClaseobra);				
					$('form#promocionFormConsultaDetAct #estado').val(data.estado);
					$('form#promocionFormConsultaDetAct #municipio').val(data.municipio);
					$('form#promocionFormConsultaDetAct #domCalle').val(data.domCalle);
					$('form#promocionFormConsultaDetAct #refColonia').val(data.refColonia);
					$('form#promocionFormConsultaDetAct #numNroint').val(data.numNroint);
					$('form#promocionFormConsultaDetAct #numNroext').val(data.numNroext);
					$('form#promocionFormConsultaDetAct #numCodigopostal').val(data.numCodigopostal);		
					oDgPromocionConsultaDet.dialog('open');
				}).error(function(data){ 
					validarSesionExpirada(data);
				}).complete(function(){
					//Instrucciones para el 'complete'
					desbloquear();
				});
			}
		 }
	}
	
}

function validaRegPatron(){
	limpiarForm = false;
	if(!longitudMandatoria($("form#promocionFormConsultaDetAct #regPatron").val(),10,"Registro Patronal")) return false;
	oDgPromocionConsultaDet.dialog('close');
	bloquear();	
	var deteccion = $("#promocionFormConsultaDetAct").serializeObject(true);	
	$.postJSON("consulta/validaRegPatron.do", deteccion, function(data) {
		if(data.cveFkPatron==null){
			alert("El registro Patronal es invalido");
			$("form#promocionFormConsultaDetAct #nomRazonsocial").val("");
			$("form#promocionFormConsultaDetAct #txRfcpatron").val("");
			$("form#promocionFormConsultaDetAct #txCurppatron").val("");
			$("form#promocionFormConsultaDetAct #cveFkPatron").val("");
			$("form#promocionFormConsultaDetAct #actividad").val("");
		}else{
			alert("El registro Patronal es valido");
			$("form#promocionFormConsultaDetAct #nomRazonsocial").val(data.nomRazonsocial);
			$("form#promocionFormConsultaDetAct #txRfcpatron").val(data.txRfcpatron);
			$("form#promocionFormConsultaDetAct #txCurppatron").val(data.txCurppatron);
			$("form#promocionFormConsultaDetAct #cveFkPatron").val(data.cveFkPatron);
			$("form#promocionFormConsultaDetAct #actividad").val(data.actividad);
		}						
		oDgPromocionConsultaDet.dialog('open');
		desbloquear();	
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
		//Instrucciones para el 'complete'		
	});
}

	function jsValidaFecAte(fecAte){
		$("#labelFecAte").html('');
		if(jsValidaFecha(fecAte)){
			 $("#fecAtencion").val(fecAte);
			 $("#labelFecAte").html('');
		}else{
			 $("#fecAtencion").val("");
			 $("#labelFecAte").html('<label class="etiquetaError" >La fecha atencion no puede ser mayor al dia actual</label>');
		}
	}
	
	function jsValidaFecNot(fecNot){
		$("#labelFecNot").html('');
		if(jsValidaFecha(fecNot)){
			 $("#fecNotificacion").val(fecNot);
			 $("#labelFecNot").html('');
		}else{
			 $("#fecNotificacion").val("");
			 $("#labelFecNot").html('<label class="etiquetaError" >La fecha notificacion no puede ser mayor al dia actual</label>');
		}
	}
	
	function jsValidaFecha(fecha){
		
		var resp = true;
		var fechaSis = new Date();
		var diaS = fechaSis.getDate();
		var mesS = fechaSis.getMonth() + 1;
		var anioS = fechaSis.getFullYear();
		var diaP = fecha.substring(0,2);
		var mesP = fecha.substring(3,5);
		var anioP = fecha.substring(6,10);
		
		
		if(anioP > anioS){
			resp = false;
		}else {
			if(anioP == anioS){
				if(mesP > mesS){
					resp = false;
				}else{
					if(mesP == mesS){
						if(diaP > diaS){
							resp = false;
						}else{
							if(diaP <= diaS){
								resp = true;
							}
						}
					}else if(mesP < mesS){
						resp = true;
					}
				}
			}else{
				if(anioP < anioS){
					resp = true;
				}
			}
		}
		
		return resp;		
		
	}
	
	function jsValidaNotificacion(){
		var fecIni = $("#fecAtencion").val();
		 var fecFinal =  $("#fecNotificacion").val();
		 if(fecIni != '' && fecFinal != ''){
			 if(jsValidaFechas(fecFinal,fecIni)){
				 $("#fecNotificacion").val(fecFinal);
				 $("#labelFecNot").html('');
			 }else{
				 $("#fecNotificacion").val('');
				 $("#labelFecNot").html('<label class="etiquetaError">La fecha notificacion no puede ser mayor a la fecha atencion</label>');
			 }
		 }
	}
	
	function jsValidaFechas(fecIni, fecFin){
		
		var array_fechaIni = fecIni.split("-"); 
		var array_fechaFin = fecFin.split("-"); 
		
		var anioIni = parseInt(array_fechaIni[2],10);
		var anioFin = parseInt(array_fechaFin[2],10);
		
		var mesIni = parseInt(array_fechaIni[1],10);
		var mesFin = parseInt(array_fechaFin[1],10);
		
		var diaIni = parseInt(array_fechaIni[0],10);
		var diaFin = parseInt(array_fechaFin[0],10);
		
		
		if(anioIni > anioFin){
			return false;
		}else {
			if(anioFin == anioIni){
				if(mesIni > mesFin){
					return false;
				}else{
					if(mesIni == mesFin){
						
						if(diaIni > diaFin){
							
							return false;
						}else{
							if(diaIni <= diaFin){
								
								return true;
							}
						}
					}else{
						if(mesIni < mesFin){
							return true;
						}
				  }
				}
			}else{
				if(anioIni < anioFin){
					return true;
				}
			}
		}
		
	}
	
function jsValidaFechasEjercicio(fecIni, fecFin){
		
		var array_fechaIni = fecIni.split("-"); 
		var array_fechaFin = fecFin.split("-"); 
		
		var anioIni = parseInt(array_fechaIni[2],10);
		var anioFin = parseInt(array_fechaFin[2],10);
		
		if(anioIni != anioFin){
			return false;
		}
		return true;
	}
		
	
	function jsvalidarAlfaNumerico(e) { 
		
	    tecla = (document.all) ? e.keyCode : e.which;
	    if (tecla==8) return true;
	    patron = /[1234567890abcdefghijklmn–opqrstuvwxyzABCDEFGHIJKLMN„OPQRSTUVWXYZ]/;
	    te = String.fromCharCode(tecla);
	    
	    return patron.test(te);
	} 
	
	function regularizacionSBC(){
		var idPromocion = $('#cvePromocion').val();
		var sPromocion = '{"cvePromocion":'+idPromocion+'}';
		var crtPromocion = jQuery.parseJSON(sPromocion);									
		$.postJSON("consulta/consultaRegularizacion.do", crtPromocion, function(data) {
			if(data.cveRegulapagos==0){
				$('form#promocionRegularizaForm #cveRegulapagos').val('');
			}else{
				$('form#promocionRegularizaForm #cveRegulapagos').val(data.cveRegulapagos);
			}
			if(data.fechaAtencionPro!=null && data.fechaAtencionPro!=""){
				$("form#promocionRegularizaForm #fechaPAI").prop('disabled','disabled');
				$("form#promocionRegularizaForm #porRegularizado").prop('disabled','');
				$("form#promocionRegularizaForm #porAvance").prop('disabled','');
				$("form#promocionRegularizaForm #fecPerIni").prop('disabled','');
				$("form#promocionRegularizaForm #fecPerFin").prop('disabled','');
				$("form#promocionRegularizaForm #numTrabrevisados").prop('disabled','');
				$("form#promocionRegularizaForm #numTrabomisos").prop('disabled','');
				$("form#promocionRegularizaForm #numTrabsubdclara").prop('disabled','');			
			}
			$('form#promocionRegularizaForm #fechaAtencionPro').val(data.fechaAtencionPro);
			$('form#promocionRegularizaForm #fechaPAI').val(data.fechaPAI);
			$('form#promocionRegularizaForm #tipoProm').val(data.tipoProm);
			$('form#promocionRegularizaForm #cvePromocion').val(data.cvePromocion);
			$('form#promocionRegularizaForm #fecPeriodo').val(data.fechaInicio);
			$('form#promocionRegularizaForm #regPatron').val(data.regPatron);
			$('form#promocionRegularizaForm #porRegularizado').val(data.porRegularizado);
			$('form#promocionRegularizaForm #porAvance').val(data.porAvance);
			$('form#promocionRegularizaForm #fecPerIni').val(data.fecPerIni);
			$('form#promocionRegularizaForm #fecPerFin').val(data.fecPerFin);
			$('form#promocionRegularizaForm #numTrabrevisados').val(data.numTrabrevisados);
			$('form#promocionRegularizaForm #numTrabomisos').val(data.numTrabomisos);
			$('form#promocionRegularizaForm #numTrabsubdclara').val(data.numTrabsubdclara);
			$('form#promocionRegularizaForm #numTrabReg').val(data.numTrabomisos+data.numTrabsubdclara);
			oDgRgulariza.dialog("open");
		}).error(function(data){ 
			validarSesionExpirada(data);
		}).complete(function(){
			//Instrucciones para el complete													
		});		
	}
	
	function jsValidaFecNotif(fecIni){
		$("#labelFecAtencionOficio").html('');
		
		if(jsValidaFecha(fecIni)){
			 $("#fecNotificacionOficio").val(fecIni);
			 $("#labelFecNotif").html('');
			 if(jsValidaVsfecOficio(fecIni)){
				 $("#fecNotificacionOficio").val(fecIni);
				 $("#labelFecNotif").html('');
			 }else{
				 $("#fecNotificacionOficio").val("");
				 $("#labelFecNotif").html('<label class="etiquetaError" >La fecha de notificaci&oacute;n del oficio no puede ser menor a la fecha oficio de promoci&oacute;n</label>');
			 }
		}else{
			 $("#fecNotificacionOficio").val("");
			 $("#labelFecNotif").html('<label class="etiquetaError" >La fecha de notificaci&oacute;n del oficio no puede ser mayor al dia actual</label>');
		}
	}
	
	
	function jsValidaFechaAtencionOficio(fecIni){
		
		if(jsValidaFecha(fecIni)){
			 $("#fecAtencionOficio").val(fecIni);
			 $("#labelFecAtencionOficio").html('');
			 if(jsValidaVsfecNotif(fecIni)){
				 $("#fecAtencionOficio").val(fecIni);
				 $("#labelFecAtencionOficio").html('');
			 }else{
				 $("#fecAtencionOficio").val("");
				 $("#labelFecAtencionOficio").html('<label class="etiquetaError" >La fecha de atenci&oacute;n del oficio no puede ser menor a la fecha de notificaci&oacute;n del oficio</label>');
			 }
		}else{
			 $("#fecAtencionOficio").val("");
			 $("#labelFecAtencionOficio").html('<label class="etiquetaError" >La fecha de atenci&oacute;n del oficio no puede ser mayor al dia actual</label>');
		}
	}
	
	
	/**
	 * Metodo que valida que la fecha de cancelacion no sea menor a la fecha
	 * del oficio de la promocion
	 * @param fecIni
	 * 
	 */
	function jsValidaFecCancelacion(fecIni){
		
		if(jsValidaFecha(fecIni)){
			 $("#fechaCancelacion").val(fecIni);
			 $("#labelFecCancelacion").html('');
			 if(jsValidaVsfecOficio(fecIni)){
				 $("#fechaCancelacion").val(fecIni);
				 $("#labelFecCancelacion").html('');
			 }else{
				 $("#fechaCancelacion").val("");
				 $("#labelFecCancelacion").html('<label class="etiquetaError" >La fecha de cancelaci&oacute;n no puede ser menor a la fecha oficio de promoci&oacute;n</label>');
			 }
		}else{
			 $("#fechaCancelacion").val("");
			 $("#labelFecCancelacion").html('<label class="etiquetaError" >La fecha de cancelaci&oacute;n no puede ser mayor al dia actual</label>');
		}
	}
/*	function jsValidaRequeridosSeguimiento(){
		
		var fecNotif  = $("#fecNotificacionOficio").val();
		var fecAtenOf = $("#fecAtencionOficio").val();
		var result = false;
		
		if(fecNotif != '' && fecAtenOf != ''){
			result = true;
		}
		
		return result;
		
	}*/
	
	/**
	 * Metodo que valida la fecha que recibe como parametro contra la fecha del
	 * oficio de la promocion
	 * @param fecIni , fecha a Evaluar
	 * @returns {Boolean}
	 */
	function jsValidaVsfecOficio(fecIni){
		var fecOficioPromo = $("#fecOficio").text();
		var resp = false;
		if(fecIni != '' && fecOficioPromo != ''){
			if(jsValidaFechas(fecOficioPromo,fecIni)){
				 resp = true;
			 }
		}
		
		return resp;
	}
	
	
	function jsValidaVsfecNotif(fecIni){
		var fecNotificacion = $("#fecNotificacionOficio").val();
		var resp = false;
		
		if(fecIni != '' && fecNotificacion != ''){
			if(jsValidaFechas(fecNotificacion,fecIni)){
				 resp = true;
			 }
		}
		
		return resp;
	}
	
	
	function jsValidaFechasCorr(fecIni){
		var fecSolCorrIni = $("#fecSolCorrIni").val();
		
		if(fecIni != '' && fecSolCorrIni != ''){
			if(jsValidaFechas(fecSolCorrIni,fecIni)){
				$("#fecSolCorrFin").val(fecIni);
				$("#labelFecCorr").html('');
			 }else{
				 $("#fecSolCorrFin").val("");
				 $("#labelFecCorr").html('<label class="etiquetaError" >La fecha final no puede ser menor a la fecha inicial</label>');
			 }
		}
	}
	
	function jsvalidarNumerico(e) { 
		
	    tecla = (document.all) ? e.keyCode : e.which;
	    if (tecla==8) return true;
	    patron = /[1234567890]/;
	    te = String.fromCharCode(tecla);
	    
	    return patron.test(te);
	} 
	
	function jsValidaFecEmision(fecEmision){
		
		if(jsValidaFecha(fecEmision)){
			 $("#fecEmision").val(fecEmision);
			 $("#labelOficio").html('');
		}else{
			 $("#fecEmision").val("");
			 $("#labelOficio").html('<label class="etiquetaError">La fecha emision no puede ser mayor al dia actual</label>');
		}
	}
	
	function desabilitaForma(){
		$('form#seguimientoForm :input#fecNotificacionOficio').addClass("red");
		$('form#seguimientoForm :input#fecAtencionOficio').prop("value", "");
		$('form#seguimientoForm :input#fecAtencionOficio').prop("disabled", true);
		$('form#seguimientoForm :input#fecAtencionOficio').removeClass("red");
		$('form#seguimientoForm :input#btnGenInvita').prop("disabled", true);
		
	}
		
		 
	
	
	function complementaPantalla(elementoActual,elementoSiguiente){
		
		
		if(elementoActual=='fecNotificacionOficio'){
			
			var valFechaNotOfi = $('form#seguimientoForm input#fecNotificacionOficio').val();
			if(valFechaNotOfi != null && valFechaNotOfi != ''){
				$('form#seguimientoForm :input:text').prop("value", "");
				activa(elementoSiguiente);
				$('form#seguimientoForm :input#btnGenInvita').prop("disabled", false);
			
			}	 
			
		}else if(elementoActual=='fecAtencionOficio'){
			var valFecAtnOficio = $('form#seguimientoForm input#fecAtencionOficio').val();
			
			//activa(elementoSiguiente);
			if(valFecAtnOficio!=null && valFecAtnOficio != ''){
				$("#tab_container_href").tabs( "enable", 2);
				$('#autDictamenTAB').show('slow'); 
				$("#tab_container_href").tabs("disable", 1);
				$('#cancelacionTAB').hide('slow');
				$('form#seguimientoForm :input#btnGenInvita').prop("disabled", true);
			}
		}

		
	}
	
	/* $("#cancelacionTAB").click( function() {
		 alert("cancelacionTAB");
			$("#tab_container_href").tabs("select", 1);
			
		});*/
	
	/**
	 * Funcion que activa un elemento HTML y lo pone disponible para captura o escritura
	 * , de acuerdo al ID que se le pase (elemento)
	 * @param elemento ID del elemento HTML
	 */
	function activa(elemento){
		if(elemento != null && elemento != ''){
			$('form#seguimientoForm input#'+elemento ).prop('disabled', false);
			$('form#seguimientoForm input#'+elemento ).addClass("red");
		}
	}
		
	function muestraInvitacion(){
		bloquear();
	}
		
	
	function jsValidaBuscar(){
		
		$("#labelGeneral").html('');
		$("#labelFechaIncial").html('');
		$("#labelFechaFinal").html('');
		var resp = false;
		
		if($("form#promocionConsultaForm #tipoPromocion").val() != '' && $("form#promocionConsultaForm #fechaIncial").val() != '' 
			&& $("form#promocionConsultaForm #fechaFinal").val()!='' ){
			//borrar los demas campos
			resp = true;
		}else if($("form#promocionConsultaForm #nuFoliopromocion").val()!= ''){
			//borrar los demas campos
			
			resp = true;
		}else if($("form#promocionConsultaForm #tipoPromocion").val() != '' ){
			if($("form#promocionConsultaForm #fechaIncial").val() == ''){
				$("#labelFechaIncial").html('<label style="color: red;">Campo obligatorio</label>');
			}
			if($("form#promocionConsultaForm #fechaFinal").val()== ''){
				$("#labelFechaFinal").html('<label style="color: red;">Campo obligatorio</label>');
			}
			
		}else{
			$("#labelGeneral").html('<label style="color: red;">Debe ingresar algun criterio de busqueda</label>');
		}
		
		return resp;
		
	}
	
	function limpiaCampos(){
		
		 $("form#promocionConsultaForm #fechaFinal").val("");
		 $("form#promocionConsultaForm #fechaIncial").val("");
		 $("form#promocionConsultaForm #tipoPromocion").val("");
	}
	
	function iniciaPantallaUISeguimiento(){
		
		//tabs estado inicial
		 $("#tab_container_href").tabs({ disabled: [2] });
		 $("#tab_container_href").tabs( "enable", 0);
		 $("#tab_container_href").tabs( "enable", 1);
		 $('#seguimientoTAB').show('slow');
		 $('#cancelacionTAB').show('slow');
		 $('#autDictamenTAB').hide('slow');

		 //Datos estado inicial
		 $("form#seguimientoForm #fecNotificacionOficio").val("");
		 $("form#seguimientoForm #fecCancelaOficio").val("");
		 $("form#seguimientoForm #fecAtencionOficio").val("");
		 $("form#seguimientoForm #fecAutDict").val("");
		 $("form#seguimientoForm #fecSol").val("");
		 $("form#seguimientoForm #fecOfiInvitacion").val("");
		 $("form#seguimientoForm #fecSolCorrIni").val("");
		 $("form#seguimientoForm #fecSolCorrFin").val("");
		 $("form#seguimientoForm #observaciones").val("");
		 
		 $("form#promocionCancelacionForm #nuVolanteCancela").val("");
		 $("form#promocionCancelacionForm #funcionarioReg").val("");
		 $("form#promocionCancelacionForm #fecCancelacion").val("");
		 $("form#promocionCancelacionForm #funcionarioAut").val("");
		 $("form#promocionCancelacionForm #idMotivoCancelacion").val("-1");
		 
		 $("form#autAviDictamenSegForm #fechaAvisoDictamen").val("");
		 $("form#autAviDictamenSegForm #numAvisoDictamen").val("");
		 $("form#autAviDictamenSegForm #funcionarioReg").val("");
		 $("form#autAviDictamenSegForm #fechaInicio").val("");
		 $("form#autAviDictamenSegForm #fechaFin").val("");
		 
		 
		 //botones estado inicial
		 $('form#seguimientoForm :input#btnGenInvita').prop("disabled", false);
		 $('form#seguimientoForm :input#btnGuardarSeg').prop("disabled", false);
		 $('form#autAviDictamenSegForm :input#btnGuardarAut').prop("disabled", true);
		 $('form#promocionCancelacionForm :input#btnConfirmarCancel').prop("disabled", false);
		 
		 //estilos iniciales
		 $('form#seguimientoForm :input#fecNotificacionOficio').addClass("red");
 		 $('form#seguimientoForm :input#fecAtencionOficio').removeClass("red");
		 
		 
		
	}
	
	
	