/**
 * JS para el soporte del catalogo de deteccion.
 */

var idConfirmarDet 	= "#dgDeteccionConfirmar";
var idDataTable 	= "#dtDeteccion";
var idDgRegistro	= "#dgDeteccionRegistro";
var idDgValida 		= "#dgDeteccionValida";
var idDgAyuda		= "#dgDeteccionAyuda";
var idDataTableValida = "#dtTableValida";
var idDataTableValidaSatic = "#dtTableValidaSatic";
var idDgValidacion 	= "#dgDeteccionTablesValidacion"
var idDgCancelacion	= "#dgDeteccionCancelacio"
var idDgConfirmaRegistro	= "#dgConfirmaRegistro"
var idDgConfirmaPromocion	= "#dgConfirmaPromocion";
var idDgdeteccionLayout		= "#dgDeteccionRegistroLayout";
var idDgSaticValidacion     = "#dgDeteccionTablesSatic";
	
// Objeto del DataTable
var oDtDeteccion;
// Dialogos
var oDgRegistro;
var oDgReporte;
var oDgValida;
var oDgValidacion;
var oDgValidaTable;
var oDgValidaSaticTable;
var ODgTablesValidacion;
var oDgRegistroConfirma;
var oDgAyuda;
var oDgCancelacion;
var oDgConfirmaPromocion;
var validaCaptura;
var validaCapturaVal;
var validaCapturaReg;
var validaCapturaLayout;
var oDgdeteccionLayout;
var oDgConfirmarDet;
var oDgSaticResults;
// Constantes
var limpiaDomicilio = true;
var sinDomicilio = true;
var validaFlag = false;
var estatusGral = false;

/**
 * Iniciamos la configuracion de los componentes visuales de jQuery.
 */
$(document).ready(function() {
	 // Fecha de Inicio y Termino
	 $( "form#deteccionFormReporte #fechaIncial,form#deteccionFormReporte #fechaFinal,form#deteccionFormRegistro #fechaDeteccion,form#deteccionFormReporte #fechaEstimIncio,form#deteccionFormReporte #fechaEstTerm,form#deteccionFormValida #fecFechainicioEst,form#deteccionFormValida #fecFechaterminoEst ,form#deteccionFormRegistro #fechaEstimIncio2,form#deteccionFormRegistro #fechaEstTerm2 " ).datepicker( { dateFormat: 'dd-mm-yy' });	 	
	 

		
		$.postJSON(getAppContextParaJS() + "/deteccion/alta/obtenerFechaServidor.do", null,function(data) {
		}).error(function(data){
			validarSesionExpirada(data);
		}).complete(function(data){
			//alert(JSON.stringify(data, null, 4));
			$('#fechaDeteccion').datepicker('option', 'maxDate', data.responseText);
		});
		
		$.postJSON(getAppContextParaJS() + "/deteccion/alta/obtenerFechaServidorMinima.do", null,function(data) {
		}).error(function(data){
			validarSesionExpirada(data);
		}).complete(function(data){
			//alert(JSON.stringify(data, null, 4));
			$('#fechaDeteccion').datepicker('option', 'minDate', data.responseText);
		});

	 
	 if($("#estatus").val() != undefined){
		 jsLlenaEstatus();
	 }
	 
	/**
	 * Inicializacion del data table
	 */
	oDtDeteccion = $(idDataTable).dataTable({
		bJQueryUI : true,
		bFilter : false,
		bInfo:true,
		bSort: false,
		"bPaginate": true,
		"bAutoWidth" : true,
		"bServerSide" :	true,
		"aoColumns" : [ {
			fnRender :function(oObj){
				var retVal = '<input type="radio" value="' +
				oObj.aData['cveDeteccion'] +'" id="radioTable" class="radioDeteccion" name="radio" onclick="ocultaDiv();"/> ';
				return retVal;
			}, 
			aTargets: [0]
			},{
				"sTitle" : "Folio Detecci&oacute;n ",
				"mDataProp" : "nuFoliodeteccion",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Reporte de Obra",
				"mDataProp" : "nuReportectrlobra",
				"sClass": "dtCenterClassColumn"
			}, {
				"sTitle" : "Fecha de Detecci&oacute;n",
				"mDataProp" : "fechaDet",
				"sClass": "dtCenterClassColumn"
			}, {
				"sTitle" : "Raz&oacute;n Social",
				"mDataProp" : "nomRazonsocial",
				"sClass":"dtCenterClassColumn"
			}, {
				"sTitle" : "Fecha de Inicio",
				"mDataProp" : "fechaIncial",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Fecha de Termino",
				"mDataProp" : "fechaFinal",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Calle",
				"mDataProp" : "domCalle",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Colonia",
				"mDataProp" : "refColonia",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Num Interior",
				"mDataProp" : "numNroint",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Num Exterior",
				"mDataProp" : "numNroext",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Avance (%)",
				"mDataProp" : "porAvanceobraEst",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Estatus",
				"mDataProp" : "estatus",
				"sClass": "dtCenterClassColumn"
			}
			],"bProcessing" : true,
			"sAjaxSource" : 'deteccion/paginar.do',
			"fnServerData" : function(sSource, aoData, fnCallback) {				
				
				var wrapper = new Object();
				wrapper.aoData = aoData;								
		
				var oForm = $("#deteccionFormReporte").toObject({mode:'first'});
				wrapper.oForm = oForm;
				bloquear();
				$.postJSON(sSource, wrapper, function(data) {
					fnCallback(data);					
					if(data.aaData!=null)
					 	if(data.aaData.length<=0)
					 		$('#labelError').html('<label style="color: red;">No se encontraron resultados de su b&uacute;squeda, por favor realice nueva b&uacute;squeda</label>');
						else
							$('#labelError').html('');
					desbloquear();
				}).error(function(data){
					validarSesionExpirada(data);
				});
			}
		});
	
	
	// Dialog de Elemento Nuevo			
	 oDgRegistro = $(idDgRegistro).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 930,
		open:function(event, ui)
		{					
			var deteccion = $("#deteccionFormRegistro").serializeObject(true);
			  $.postJSON("alta/censores.do", deteccion, function(datas) {
					 var options = "<option value='' >--Por favor seleccione--</option>";
					 if(datas!=null)
					   for (var i = 0; i < datas.length; i++) {
				         options += "<option value='"+ datas[i][0] +"'>"+ datas[i][1] +"</option>";		     
				       }
					 $('select#cveCensor').html(options);
					//Agrega las opciones al control
				}).error(function(datas){ 
					validarSesionExpirada(datas);
				}).complete(function(){
					
				});
        },
		beforeClose :function(event,ui){		    
		    if(limpiaDomicilio){
		    	limpiarFormulario("#deteccionFormRegistro");
		    	limpiarDomicilioAlta();
		    	validaFlag = false;
		    	var crtDeteccion = $("#deteccionFormRegistro").toObject({mode:'first'});
				$.postJSON("alta/removerDomicilioSession.do", crtDeteccion, function(data) {					
				}).error(function(data){ 					
					validarSesionExpirada(data);
				}).complete(function(){
					//Instrucciones para el complete													
				});
		    }else{
		    	limpiaDomicilio = true;
		    }
		},
		buttons: {
			"Guardar": function() {
				$("#labelPromovido").html('');
				$("#labelcveTipocorr").html('');
				$("#labelfechaDeteccion").html('');
				$("#labeltipClaseobra").html('');
				$("#labelcveCensor").html('');
				$("#labelPromovido").html('');
						if($("#deteccionFormRegistro #:checked").val() != undefined){					
							if($("#deteccionFormRegistro #fechaDeteccion").val() != ''){
								if($("#deteccionFormRegistro #tipClaseobra").val() != ''){
									if($("#deteccionFormRegistro #idPromovido").val() != ''){	
										if($("form#deteccionFormRegistro #:checked").val()==8){
											bloquear();
											limpiaDomicilio = false;
											grabarAlta();
											validaFlag = false;
											limpiarFormulario("#deteccionFormRegistro");
											$("#domicilioIdV").val("");
											desbloquear();
											$(this).dialog("close");
											
										}else{
											if($("#deteccionFormRegistro #cveCensor").val() != ''){
												if($("#nuReportectrlobra").val() != ''){
													
														var result = jsValidaNUmReporte();
														if(result == true){
															bloquear();
															limpiaDomicilio = false;
															grabarAlta();
															limpiarFormulario("#deteccionFormRegistro");
															$("#labelnuReportectrlobra").html('');
															limpiarDomicilioAlta();
															$("#domicilioIdV").val("");
															validaFlag = false;
															desbloquear();
															$(this).dialog("close");
														}else{
															$("#nuReportectrlobra").val("");
															$("#labelnuReportectrlobra").html('<label style="color: red;">El numero de reporte ya existe</label>');
														}
													
												}else{
													$("#labelnuReportectrlobra").html('<label style="color: red;">Campo Requerido</label>');
												}
											}else{
												
												$("#labelcveCensor").html('<label style="color: red;">Campo Requerido</label>');
											}										
										}									
		
									}else{
										
										$("#labelPromovido").html('<label style="color: red;">Campo Requerido</label>');
									}
								}else{
									
									$("#labeltipClaseobra").html('<label style="color: red;">Campo Requerido</label>');
								}
							}else{
								
								$("#labelfechaDeteccion").html('<label style="color: red;">Campo Requerido</label>');
							}							
						}else{
							
							$("#labelcveTipocorr").html('<label style="color: red;">Campo Requerido</label>');
						}
			}, 
			"Cancelar": function() { 
				$("#divNumReporte").hide();
				$("#divDatosCenso").hide();
				$("#divActividad").hide();
				limpiarFormulario("#deteccionFormRegistro");
				var crtDeteccion = $("#deteccionFormRegistro").toObject({mode:'first'});
				bloquear();
				$.postJSON("alta/removerDomicilioSession.do", crtDeteccion, function(data) {
					limpiarDomicilioAlta();
					validaFlag = false;
					oDgRegistro.dialog("close");
				}).error(function(data){ 
					desbloquear();
					validarSesionExpirada(data);
				}).complete(function(){
					desbloquear();													
				}); 				
			} 
		}
	});
	 
	oDgdeteccionLayout = $(idDgdeteccionLayout).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 930,		
		beforeClose :function(event,ui){		    
		    if(limpiaDomicilio){
		    	limpiarFormulario("#deteccionFormRegistro");
		    	var crtDeteccion = $("#deteccionFormRegistro").toObject({mode:'first'});
				$.postJSON("alta/removerDomicilioSession.do", crtDeteccion, function(data) {					
				}).error(function(data){ 
					validarSesionExpirada(data);
				}).complete(function(){
					//Instrucciones para el complete													
				});
		    }else{
		    	limpiaDomicilio = true;
		    }
		    sinDomicilio = true;
		    $('form#deteccionLayoutForm #domInegiButtons').show();
			$('form#deteccionLayoutForm #cvePkTipObra').prop('disabled','');
			$('form#deteccionLayoutForm #cvePkFaseConst').prop('disabled','');
			$('form#deteccionLayoutForm #tipClaseobra').prop('disabled','');
			$('form#deteccionLayoutForm #idObligado').prop('disabled','');
			$('form#deteccionLayoutForm #cveFkZona').prop('disabled','');
		},
		buttons: {
			"Salir": function() { 
				$("#divNumReporte").hide();
				$("#divDatosCenso").hide();
				$("#divActividad").hide();
				limpiarFormulario("#deteccionLayoutForm");
				var crtDeteccion = $("#deteccionLayoutForm").toObject({mode:'first'});
				$.postJSON("deteccion/removerDomicilioSession.do", crtDeteccion, function(data) {
					oDgdeteccionLayout.dialog("close");
				}).error(function(data){ 
					validarSesionExpirada(data);
				}).complete(function(){					
					//Instrucciones para el complete
					$('form#deteccionLayoutForm #domInegiButtons').show();
					$('form#deteccionLayoutForm #cvePkTipObra').prop('disabled','');
					$('form#deteccionLayoutForm #cvePkFaseConst').prop('disabled','');
					$('form#deteccionLayoutForm #tipClaseobra').prop('disabled','');
					$('form#deteccionLayoutForm #idObligado').prop('disabled','');
					$('form#deteccionLayoutForm #cveFkZona').prop('disabled','');
				});				
			} 
		}
	});

	// Dialog de Elemento a Validacion			
//	 oDgValida = $(idDgValida).dialog({
//		autoOpen: false,
//		modal:true,
//		resizable:false,
//		width: 930,
//		beforeClose :function(event,ui){
//		    limpiarFormulario("#deteccionFormValida");
//		    if(limpiaDomicilio){
//		    	var crtDeteccion = $("#deteccionFormRegistro").toObject({mode:'first'});
//				$.postJSON("deteccion/removerDomicilioSession.do", crtDeteccion, function(data) {					
//				}).error(function(data){ 
//					validarSesionExpirada(data);
//				}).complete(function(){
//					//Instrucciones para el complete													
//				});
//		    }else{
//		    	limpiaDomicilio = true;
//		    }
//		},
//		buttons: {
//			"Validar": function() {																
//				if(validaCapturaVal.form()){
//					var crtDeteccion = $("#deteccionFormValida").toObject({mode:'first'});
//					$.postJSON("deteccion/consultar.do", crtDeteccion, function(data) {						
//						$("form#deteccionFormRegistro #estado").val($("form#deteccionFormValida #estado").val());
//						$("form#deteccionFormRegistro #municipio").val($("form#deteccionFormValida #municipio").val());
//						$("form#deteccionFormRegistro #domCalle").val($("form#deteccionFormValida #domCalle").val());
//						$("form#deteccionFormRegistro #refColonia").val($("form#deteccionFormValida #refColonia").val());
//						$("form#deteccionFormRegistro #numNroint").val($("form#deteccionFormValida #numNroint").val());
//						$("form#deteccionFormRegistro #numNroext").val($("form#deteccionFormValida #numNroext").val());
//						$("form#deteccionFormRegistro #numCodigopostal").val($("form#deteccionFormValida #numCodigopostal").val());
//						if(data!=null){
//							limpiaDomicilio = false;
//							oDgValidaTable.fnDraw();
//							oDgValidaSaticTable.fnDraw();
//							oDgValida.dialog("close");
//							oDgValidacion.dialog('open');
//							$("form#deteccionFormValida #cvePkFaseConst").prop('disabled','');
//							$("form#deteccionFormValida #cvePkTipObra").prop('disabled','');
//						}else{						
//							oDgRegistroConfirma.dialog("open");
//						}
//					}).error(function(data){ 
//						validarSesionExpirada(data);
//					}).complete(function(){
//						//Instrucciones para el complete													
//					});
//				}				 
//			}, 
//			"Cancelar": function() {
//				var crtDeteccion = $("#deteccionFormRegistro").toObject({mode:'first'});
//				$.postJSON("deteccion/removerDomicilioSession.do", crtDeteccion, function(data) {
//					oDgValida.dialog("close");
//				}).error(function(data){ 
//					validarSesionExpirada(data);
//				}).complete(function(){
//					//Instrucciones para el complete													
//				});
//			} 
//		}
//	});
	 
	// Dialog de Elemento a Tabla de Resultado de Validacion
	 oDgValidacion = $(idDgValidacion).dialog({
			autoOpen: false,
			modal:true,
			resizable:false,
			width: 930,
			buttons: {
				"Continuar": function() {
					if($("#numCodigopostal").val() != '' || $("#numCodigopostal").val() != null){
						oDgValidaSaticTable.fnDraw();
						$(this).dialog("close");
					}
				}, 
				"Salir": function() {
					validaFlag = false;
					$(this).dialog("close");  
				} 
			}
		});
	 
	// Dialog de Elemento para cancelar la obra
	 oDgCancelacion = $(idDgCancelacion).dialog({
			autoOpen: false,
			modal:true,
			resizable:false,
			width: 930,
			buttons: {
				"Confirmar": function() {
					if(!checaCero($("#idMotivocancelacion").val(),"Motivo de Cancelación")) return false;
					var crtDeteccion = $("#deteccionCancelacionForm").toObject({mode:'first'});
					$.postJSON("deteccion/cancelar.do", crtDeteccion, function(data) {																						
						oDtDeteccion.fnDraw();							
						oDgCancelacion.dialog("close");	
						$("#actionButtons").hide();
						$("#deteccionButtons").show();
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
		});
	 
	 
	// Dialog de Elemento Ayuda		
	oDgAyuda = $(idDgAyuda).dialog({
		modal:		true,
		buttons: {
			"Aceptar": function() {
				$(this).dialog("close"); 
			}
		}
	});
	
	oDgRegistroConfirma = $(idDgConfirmaRegistro).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,		
		width: 300,
		buttons: {
			"Si": function() {
				var crtDeteccion = $("#deteccionFormValida").toObject({mode:'first'});
				bloquear();
				$.postJSON("deteccion/censor.do", crtDeteccion, function(data) {
					$('#wrapperDialogRegistro,#deteccionFormRegistro,#nombreCensor').val(data.nombreCensor);
					limpiaDomicilio = false;
					oDgRegistroConfirma.dialog("close");
					oDgValida.dialog("close");
					oDgRegistro.dialog('open');
				}).error(function(data){ 
					desbloquear();
					validarSesionExpirada(data);
				}).complete(function(){
					desbloquear();													
				});											
			}, 
			"No": function() { 
				$(this).dialog("close"); 
			} 
		}
	});
	
	oDgConfirmaPromocion = $(idDgConfirmaPromocion).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,		
		width: 300,
		buttons: {
			"Si": function() {
				var crtDeteccion = $("#deteccionPromocionForm").toObject({mode:'first'}); 
				$.postJSON("deteccion/promocion.do", crtDeteccion, function(data) {
					$('#wrapperDialogPromocion,#deteccionPromocionForm,#cveDeteccion').val(data.cveDeteccion);				
					oDgConfirmaPromocion.dialog('close');
					oDtDeteccion.fnDraw();
				}).error(function(data){ 
					validarSesionExpirada(data);
				}).complete(function(){
					//Instrucciones para el 'complete'
				});				
			}, 
			"No": function() { 
				$(this).dialog("close"); 
			} 
		}
	});

	oDgValidaTable = $(idDataTableValida).dataTable({

		bJQueryUI : true,
		bFilter : false,
		bInfo : true,
		bSort : false,
		
		"bPaginate" : true,
		"bAutoWidth" : false,
		"bServerSide" : true,
		"aoColumns" : [ {
			"sWidth": "13%",
			"sTitle" : "Folio Detecci&oacute;n ",
			"mDataProp" : "nuFoliodeteccion",
			"sClass": "dtCenterClassColumn"
		},{
			"sWidth": "10%",
			"sTitle" : "Fecha de Detecci&oacute;n",
			"mDataProp" : "fecFechadeteccionFc",
			"sClass": "dtCenterClassColumn"
		}, {
			"sWidth": "18%",
			"sTitle" : "Raz&oacute;n Social",
			"mDataProp" : "nomRazonsocial",
			"sClass":"dtCenterClassColumn"
		},{
			"sWidth": "15%",
			"sTitle" : "Calle",
			"mDataProp" : "domCalle",
			"sClass": "dtCenterClassColumn"
		},{
			"sWidth": "15%",
			"sTitle" : "Colonia",
			"mDataProp" : "refColonia",
			"sClass": "dtCenterClassColumn"
		},{
			"sWidth": "5%",
			"sTitle" : "Num Exterior",
			"mDataProp" : "numNroext",
			"sClass": "dtCenterClassColumn"
		},{
			"sWidth": "14%",
			"sTitle" : "Fecha de  Inicio  ",
			"mDataProp" : "fecFechainicioEst",
			"sClass": "dtCenterClassColumn"
		},{
			"sWidth": "10%",
			"sTitle" : "Fecha de Termino",
			"mDataProp" : "fecFechaterminoEst",
			"sClass": "dtCenterClassColumn"
		}
		],"bProcessing" : true,
		"sAjaxSource" : 'alta/validar.do',
		"fnServerData" : function(sSource, aoData, fnCallback) {					

			var wrapper = new Object();
			wrapper.aoData = aoData;								
		
			
			var colonia = $("#refColonia").val();
			var calle = $("#domCalle").val();
			var numExt = $("#numNroext").val();
			var cp = $("#numCodigopostal").val();
			
			var variable = '{' +
			   '"refColonia":"'+colonia+'",'+
			   '"domCalle":"'+calle+'",'+
			   '"numNroext":"'+numExt+'",'+
			   '"numCodigopostal":"'+cp+'"}';					
			var variableJson = jQuery.parseJSON(variable);
			wrapper.oForm = variableJson;
			if(cp != ""){
				bloquear();
				$.postJSON(sSource, wrapper, function(data) {				
					if(data != null){
						fnCallback(data);		
						oDgValidacion.dialog('open');
						desbloquear();
					}
				});
				desbloquear();
			}
		}

	});
	
	validaCaptura = $("#deteccionFormReporte").validate({
		 rules: {
			 domCalle: {				 
				 maxlength: 40,
				 alphanumeric: true
			 },
			 numNroint: {				 
				 maxlength: 10,
				 alphanumeric: true
			 },
			 numNroext: {
				 maxlength: 10,
				 alphanumeric: true
			 },
			 numCodigopostal: {
				 maxlength: 5,
				 alphanumeric: true
			 },
			 nuFoliodeteccion: {
		  	     maxlength: 18,
		  	     alphanumeric: true
			 },			 
			 fechaIncial: {
				 required: true
			 },
			 fechaFinal: {
				 required: true
			 },
			 estatus:{
				 required: true
			 }
		 }
	});
	
	validaCapturaVal = $("#deteccionFormValida").validate({
		 rules: {	
			 estado:{
				 required: true
			 },
			 municipio:{
				 required: true
			 },
			 domCalle: {
				 required: true
			 },
			 refColonia: {
				 required: true
			 },			 
			 numNroext: {
				 required: true
			 },
			 numCodigopostal: {
				 required: true
			 }
		 }
	});
	
	validaCapturaReg = $("#deteccionFormRegistro").validate({
		 rules: {
			 cveTipocorr:{
				 required: true
			 },			 
			 fechaDeteccion:{
				 required: true
			 },
			 idObligado:{
				 required: true
			 },
			 estado:{
				 required: true
			 },
			 municipio:{
				 required: true
			 },
			 domCalle: {
				 required: true				 				 
			 },
			 refColonia: {
				 required: true				 
			 },			 
			 numNroext: {
				 required: true
			 },
			 numCodigopostal: {
				 required: true
			 },
			 tipClaseobra:{
				 required: true
			 },			 
			 cveCensor: {
				 required: true
			 },
			 nuReportectrlobra:{
				 required: true
			 }
		 }
	});
	
	validaCapturaLayout = $("#deteccionLayoutForm").validate({
		 rules: {			
			 idObligado:{
				 required: true
			 },
			 estado:{
				 required: true
			 },
			 municipio:{
				 required: true
			 },
			 domCalle: {
				 required: true				 				 
			 },
			 refColonia: {
				 required: true				 
			 },			 
			 numNroext: {
				 required: true
			 },
			 numCodigopostal: {
				 required: true
			 }
		 }
	});
	
	oDgValidaSaticTable = $(idDataTableValidaSatic).dataTable({

		bJQueryUI : true,
		bFilter : true,
		bInfo:true,
		bSort: false,
		"bPaginate": true,
		"bAutoWidth" : true,
		"bServerSide" :	true,
		"aoColumns" : [ 
		               
		 {
			"sWidth": "30%",
			"sTitle" : "Calle",
			"mDataProp" : "domCalle",
			"sClass": "dtCenterClassColumn"
		}, {
			"sWidth": "5%",
			"sTitle" : "Num. Exterior",
			"mDataProp" : "numNroext",
			"sClass": "dtCenterClassColumn"
		},{
			"sWidth": "5%",
			"sTitle" : "CP",
			"mDataProp" : "numCodigopostal",
			"sClass": "dtCenterClassColumn"
		},{
			"sWidth": "15%",
			"sTitle" : "Numero de Obra",
			"mDataProp" : "numRegObra",
			"sClass": "dtCenterClassColumn"
		},{
			"sWidth": "15%",
			"sTitle" : "Registro Patronal",
			"mDataProp" : "regPatron",
			"sClass": "dtCenterClassColumn"
		}, {
			"sWidth": "30%",
			"sTitle" : "Razon Social",
			"mDataProp" : "nomRazonsocial",
			"sClass":"dtCenterClassColumn"
		}
		],"bProcessing" : true,
		"sAjaxSource" : 'alta/validarSatic.do',
		"fnServerData" : function(sSource, aoData, fnCallback) {					

			var wrapper = new Object();
			wrapper.aoData = aoData;								
	
			var cp = $("#numCodigopostal").val();
			var variable = '{"numCodigopostal":'+'"'+cp+'"}';
			var oForm = jQuery.parseJSON(variable);
			wrapper.oForm = oForm;
			if(cp != ""){
				bloquear();
				$.postJSON(sSource, wrapper, function(data) {
					if(data != null){
						fnCallback(data);
						
						oDgSaticResults.dialog('open');
						desbloquear();
					}else{
						
						if(cp != ""){
							alert('No se encontro ninguna obra con los datos proporcionados');
							// pasamos domicilio
							validaIdDeteccion();
							$('form#deteccionFormRegistro #estado').val($("#estado").val());
							$('form#deteccionFormRegistro #municipio').val($("#municipio").val());
							$('form#deteccionFormRegistro #domCalle').val($("#domCalle").val());
							$('form#deteccionFormRegistro #refColonia').val($("#refColonia").val());
							$('form#deteccionFormRegistro #numNroint').val($("#numNroint").val());
							$('form#deteccionFormRegistro #numNroext').val($("#numNroext").val());
							$('form#deteccionFormRegistro #numCodigopostal').val($("#numCodigopostal").val());
							
							oDgRegistro.dialog('open');
							desbloquear();
						}else{
							oDgSaticResults.dialog('open');
							desbloquear();
						}
					}
				});
				
			}
		}

	});
	
	$("#btnValidaObra").click(function(){
		
		validaFlag = false;
		var resp = jsValidaDomObra();
		if(resp == true){
			var variable =  '{"estatus":'+'"'+ 1 +'"}';
			var crtDeteccion = jQuery.parseJSON(variable);
			alert('Se validara el domicilio dentro de la detecci\u00f3n');
			bloquear();
			$.postJSON("alta/consultar.do", crtDeteccion, function(data) {
				if(data != null){
					if(data[0].estatus == 'diferente'){
						alert('El estado seleccionado no corresponde al usuario en session');
					}else{
						oDgValidaTable.fnDraw();
					}						
				}else{
					oDgConfirmarDet.dialog('open');	
				}
			}).error(function(data){ 
				desbloquear();
				validarSesionExpirada(data);
			}).complete(function(){
				desbloquear();												
			});
		}
		
	});
	
	
	$("#btnRegistraObra").click(function(){
		if(validaFlag == true){
			// pasamos domicilio
			$('form#deteccionFormRegistro #estado').val($("#estado").val());
			$('form#deteccionFormRegistro #municipio').val($("#municipio").val());
			$('form#deteccionFormRegistro #domCalle').val($("#domCalle").val());
			$('form#deteccionFormRegistro #refColonia').val($("#refColonia").val());
			$('form#deteccionFormRegistro #numNroint').val($("#numNroint").val());
			$('form#deteccionFormRegistro #numNroext').val($("#numNroext").val());
			$('form#deteccionFormRegistro #numCodigopostal').val($("#numCodigopostal").val());
			if($("#domicilioIdV").val() != undefined){
				$('form#deteccionFormRegistro #domicilioId').val($("#domicilioIdV").val());
			}
			oDgRegistro.dialog('open');
		}else{
			alert('Es necesario validar el domicilio');
		}
		
	});
	
	// Dialog Confirmar si cintinua a satic
	oDgConfirmarDet = $(idConfirmarDet).dialog({
			autoOpen: false,
			modal:true,
			resizable:false,
			width: 930,
			buttons: {
				"Continuar": function() {
					// continuamos la validacion de SATIC
					
					if($("#numCodigopostal").val() != '' || $("#numCodigopostal").val() != null){
						
						oDgValidaSaticTable.fnDraw();
						$(this).dialog("close");
					}
				}, 
				"Salir": function() { 
					validaFlag = false;
					$(this).dialog("close"); 
				} 
			}
		});
	
	// Dialog SATIC Table
	oDgSaticResults = $(idDgSaticValidacion).dialog({
			autoOpen: false,
			modal:true,
			resizable:false,
			width: 930,
			buttons: {
				"Continuar": function() {
					
					$(this).dialog("close");
					
					// pasamos domicilio
					validaIdDeteccion();
					$('form#deteccionFormRegistro #estado').val($("#estado").val());
					$('form#deteccionFormRegistro #municipio').val($("#municipio").val());
					$('form#deteccionFormRegistro #domCalle').val($("#domCalle").val());
					$('form#deteccionFormRegistro #refColonia').val($("#refColonia").val());
					$('form#deteccionFormRegistro #numNroint').val($("#numNroint").val());
					$('form#deteccionFormRegistro #numNroext').val($("#numNroext").val());
					$('form#deteccionFormRegistro #numCodigopostal').val($("#numCodigopostal").val());
					
					oDgRegistro.dialog('open');
				}, 
				"Salir": function() { 
					validaFlag = false;
					$(this).dialog("close"); 
				} 
			}
		});
	
	
	$("#btnLimpiar").click(function(){
		bloquear();
		limpiarDomicilioAlta();
		var crtDeteccion = $("#deteccionFormValida").toObject({mode:'first'});
		$.postJSON("alta/removerDomicilioSession.do", crtDeteccion, function(data) {
			desbloquear();
		});	
	});
	

//	$("form#deteccionFormReporte #cvePkTipObra").change(function(){
//		if($("form#deteccionFormReporte #cvePkTipObra").val()!=-1){
//			$("form#deteccionFormReporte #cvePkFaseConst").prop('disabled','disabled');
//		}else{
//			$("form#deteccionFormReporte #cvePkFaseConst").prop('disabled','');
//		}
//	});
//	
//	$("form#deteccionFormReporte #cvePkFaseConst").change(function(){
//		if($("form#deteccionFormReporte #cvePkFaseConst").val()!=-1){
//			$("form#deteccionFormReporte #cvePkTipObra").prop('disabled','disabled');
//		}else{
//			$("form#deteccionFormReporte #cvePkTipObra").prop('disabled','');
//		}
//	});
//	
//	$("form#deteccionFormValida #cvePkTipObra").change(function(){
//		if($("form#deteccionFormValida #cvePkTipObra").val()!=-1){
//			$("form#deteccionFormValida #cvePkFaseConst").prop('disabled','disabled');
//		}else{
//			$("form#deteccionFormValida #cvePkFaseConst").prop('disabled','');
//		}
//	});
//	
//	$("form#deteccionFormValida #cvePkFaseConst").change(function(){
//		if($("form#deteccionFormValida #cvePkFaseConst").val()!=-1){
//			$("form#deteccionFormValida #cvePkTipObra").prop('disabled','disabled');
//		}else{
//			$("form#deteccionFormValida #cvePkTipObra").prop('disabled','');
//		}
//	});	
//	
//	$("form#deteccionFormRegistro #cvePkTipObra").change(function(){
//		if($("form#deteccionFormRegistro #cvePkTipObra").val()!=-1){
//			$("form#deteccionFormRegistro #cvePkFaseConst").prop('disabled','disabled');
//		}else{
//			$("form#deteccionFormRegistro #cvePkFaseConst").prop('disabled','');
//		}
//	});
//	
//	$("form#deteccionFormRegistro #cvePkFaseConst").change(function(){
//		if($("form#deteccionFormRegistro #cvePkFaseConst").val()!=-1){
//			$("form#deteccionFormRegistro #cvePkTipObra").prop('disabled','disabled');
//		}else{
//			$("form#deteccionFormRegistro #cvePkTipObra").prop('disabled','');
//		}
//	});
	
});//$(document).ready(function()

//Inicializa el paginador
function inicializaPosicionPaginador(){
	oDtDeteccion.fnDisplayStart(0);
}


function registra(){
	oDgRegistro.dialog('open');
}

function valida(){
	limpiarFormulario("#deteccionFormValida");
	oDgValida.dialog('open');
}

function consultar(){		
	
	var deteccion = $("#deteccionFormMain").serializeObject(true);				
	$.postJSON("deteccion/consultar.do", deteccion, function(data) {
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
		alert("La obra no se encontro");						
	});
}

function modificar(){
	var idDeteccion = $('#:checked').val();
	var sDeteccion = '{"cveDeteccion":'+idDeteccion+'}';
	var deteccion = jQuery.parseJSON(sDeteccion);
	// Buscamos el elemento
	$.postJSON("deteccion/consultaPorClave.do", deteccion, function(data) {
		$('#wrapperDialogModif,#deteccionFormModificar,#cveDeteccion').val(data.cveDeteccion);				
		oDgReporte.dialog('open');
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
		//Instrucciones para el 'complete'
	});
}

function borrar(){
	var idDeteccion = $('#:checked').val();
	var sDeteccion = '{"cveDeteccion":'+idDeteccion+'}';
	var deteccion = jQuery.parseJSON(sDeteccion);
	
	// Buscamos el elemento
	$.postJSON("deteccion/consultaPorClave.do", deteccion, function(data) {
		$('#wrapperDialogBorrar,#deteccionFormBorrar,#cveDeteccion').val(data.cveDeteccion);
		oDgBorrar.dialog('open');
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
		//Instrucciones para el 'complete'
	});
}


function ayuda(){
	alert("ayuda");
	oDgAyuda.dialog('open');
}

function buscar(){
	if(jsValidarConsulta()){		
		$("#actionButtons").hide();
		$("#deteccionButtons").show();
		$("#deteccionData").show();
		bloquear();
		oDtDeteccion.fnDraw();
	}	
}

function ocultaDiv(){

		var estatus = $("#estatus option:selected").text();
	
		$("#tdValidacion").hide();
		
		if(estatus == 'SIN DOMICILIO'){
			$("#tdMostrar").hide();			
			$("#tdValidacion").show();
			$("#tdCancela").show();
		}if(estatus == 'CANCELADAS'){
			$("#tdCancela").hide();
			$("#tdValidacion").hide();
			$("#tdMostrar").show();
		}if(estatus == 'PROMOVER A SATICA'){
			$("#tdMostrar").show();
			$("#tdCancela").show();
			$("#tdValidacion").hide();
		}if(estatus == 'PROMOVER A CONSTRUCCION'){
			$("#tdMostrar").show();
			$("#tdCancela").show();
			$("#tdValidacion").hide();
		}
		
		$("#actionButtons").show("fast");
		$("#deteccionButtons").hide();
}

function mostrar(){
	
	var id = $('#:checked').val();
	var estatus = $("#estatus option:selected").text();
	if(estatus == 'SIN DOMICILIO'){
		
		var variable = '{"cveDeteccion": '+ id +'}';
		var variableJson = jQuery.parseJSON(variable);
		$.postJSON("deteccion/sinDomicilio.do",variableJson, function(data) {
			if(data != null){
				var context = getAppContextParaJS();
				var resultado = openWindowregistraDomicilioInegi(context,'catalogo/deteccion/altaModal.do');	
				refrescarAltaModal('deteccionFormValida');
			}
		});
		setTimeout ("refrescaBusqueda();", 1000);
		
	}else{
		
		jsLlenaTipoObra();
		var radios = document.getElementsByName("radio");
		for (i=0;i<radios.length;i++)
		 {
			if(radios[i].checked)
			{
		
				var idDeteccion = radios[i].value;
				var sDeteccion = '{"cveDeteccion":'+idDeteccion+'}';
				var deteccion = jQuery.parseJSON(sDeteccion);
				
				// Buscamos el elemento
				$.postJSON("deteccion/mostrar.do", deteccion, function(data) {		
					$('form#deteccionLayoutForm #cveDeteccion').val(data.cveDeteccion);		
					$('form#deteccionLayoutForm #sdelegOrig').val(data.sdelegOrig);
					$('form#deteccionLayoutForm #cveTipocorr').val(data.cveTipocorr);
					$('form#deteccionLayoutForm #cveFkPatron').val(data.cveFkPatron);
					$('form#deteccionLayoutForm #domicilioId').val(data.domicilioId);
					$('form#deteccionLayoutForm #idObligado').val(data.idObligado);
					$('form#deteccionLayoutForm #idPromovido').val(data.idPromovido);
					$('form#deteccionLayoutForm #fechaRegistro').val(data.fechaRegistro);				
					$('form#deteccionLayoutForm #nuFoliodeteccion').val(data.nuFoliodeteccion);
					$('form#deteccionLayoutForm #fechaDeteccion').val(data.fechaDeteccion);				
					if(data.cveTipocorr==8){			
						$('form#deteccionLayoutForm #divActividad').show();	
						$("form#deteccionLayoutForm #divDatosCenso").hide();
						$("form#deteccionLayoutForm #divNumReporte").hide();
						$('form#deteccionLayoutForm #nomRazonsocial').val(data.nomRazonsocial);			
						$('form#deteccionLayoutForm #regPatron').val(data.regPatron);
						$('form#deteccionLayoutForm #desDepcontratante').val(data.desDepcontratante);
						$('form#deteccionLayoutForm #desDependenciapub').val(data.desDependenciapub);
						$('form#deteccionLayoutForm #numTrabajdores').val(data.numTrabajdores);
					}else{	
						$('form#deteccionLayoutForm #divActividad').hide();
						$("form#deteccionLayoutForm #divDatosCenso").show();
						$("form#deteccionLayoutForm #divNumReporte").show();
						$('form#deteccionLayoutForm #nuReportectrlobra').val(data.nuReportectrlobra);
						$('form#deteccionLayoutForm #txRfcpatron').val(data.txRfcpatron);
						$('form#deteccionLayoutForm #nomRazonsocial').val(data.nomRazonsocial);			
						$('form#deteccionLayoutForm #regPatron').val(data.regPatron);
						$('form#deteccionLayoutForm #txCurppatron').val(data.txCurppatron);
						$('form#deteccionLayoutForm #txEmail').val(data.txEmail);
						$('form#deteccionLayoutForm #txTelefono').val(data.txTelefono);													
						$('form#deteccionLayoutForm #impCostoobra').val(data.impCostoobra);
						$('form#deteccionLayoutForm #porAvanceobraEst').val(data.porAvanceobraEst);
						$('form#deteccionLayoutForm #canSuperficie').val(data.canSuperficie);
						$('form#deteccionLayoutForm #desDepcontratante').val(data.desDepcontratante);
						$('form#deteccionLayoutForm #desDependenciapub').val(data.desDependenciapub);
						$('form#deteccionLayoutForm #numTrabajdores').val(data.numTrabajdores);
						if((data.tipClaseobra!=null && data.tipClaseobra!="") && ((data.tipoObra!=null && data.tipoObra!="") || (data.faseObra!=null && data.faseObra!=""))){
							$("form#deteccionLayoutForm #divDatosObra").show();						
							$('form#deteccionLayoutForm #selectTipoObra').val(data.cvePkTipObra);
							$('form#deteccionLayoutForm #cvePkFaseConst').val(data.cvePkFaseConst);
							$('form#deteccionLayoutForm #selectTipoObra').prop('disabled','disabled');
						}else{
							$("form#deteccionLayoutForm #divCombosObra").show();
						}
					}
					if(data.cveFkZona!=null)
						$('form#deteccionLayoutForm #cveFkZona').val(data.cveFkZona);
				
					$('form#deteccionLayoutForm #fechaEstTerm2').val(data.fechaEstTerm2);
					$('form#deteccionLayoutForm #fechaEstimIncio2').val(data.fechaEstimIncio2);
					$('form#deteccionLayoutForm #tipClaseobra').val(data.tipClaseobra);				
					$('form#deteccionLayoutForm #estado').val(data.estado);
					$('form#deteccionLayoutForm #municipio').val(data.municipio);
					$('form#deteccionLayoutForm #domCalle').val(data.domCalle);
					$('form#deteccionLayoutForm #refColonia').val(data.refColonia);
					$('form#deteccionLayoutForm #numNroint').val(data.numNroint);
					$('form#deteccionLayoutForm #numNroext').val(data.numNroext);
					$('form#deteccionLayoutForm #numCodigopostal').val(data.numCodigopostal);
					if(data.domicilioId!=null){
						$('form#deteccionLayoutForm #domInegiButtons').hide();
						$('form#deteccionLayoutForm #cvePkTipObra').prop('disabled','disabled');
						$('form#deteccionLayoutForm #cvePkFaseConst').prop('disabled','disabled');
						$('form#deteccionLayoutForm #tipClaseobra').prop('disabled','disabled');
						$('form#deteccionLayoutForm #idPromovido').prop('disabled','disabled');
						$('form#deteccionLayoutForm #cveFkZona').prop('disabled','disabled');
						sinDomicilio = false;
					}
					oDgdeteccionLayout.dialog('open');
				}).error(function(data){ 
					validarSesionExpirada(data);
				}).complete(function(){
					//Instrucciones para el 'complete'
				});
			}
		 }
	}
}

function cancela(){						
	var radios = document.getElementsByName("radio");
	for (i=0;i<radios.length;i++)
	 {
		if(radios[i].checked)
		{
			var idDeteccion = radios[i].value;
			var sDeteccion = '{"cveDeteccion":'+idDeteccion+'}';
			var deteccion = jQuery.parseJSON(sDeteccion);			
			$('#wrapperDialogCancelacion,#deteccionCancelacionForm,#cveDeteccion').val(deteccion.cveDeteccion);				
			oDgCancelacion.dialog('open');
		}
	 }
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
	if(!validaFechas(fec2,$(fec).val())){
		alert("La "+leyendafec+" debe ser menor a la Fecha del sistema");
		$(fec).val("");
		return false;
	}
}

function validaNuReportectrlobra(form,inputName){
	var numero="form#"+form+" #"+inputName+"";
	var fechaDeteccion="form#"+form+" #fechaDeteccion";
	
	var deteccion = $("#"+form).toObject({mode:'first'});
	//var deteccion = $("#deteccionFormRegistro").serializeObject(true);
	;
	$.postJSON("alta/validaNuReporteControlObra.do", deteccion, function(data) {
	//	alert('data.responseText: ' + data.responseText);
		
		
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(data){
		//alert('complete' + data.responseText);
		//alert(JSON.stringify(data, null, 4));
		if(data.responseText=='false'){
			//alert('El numero control de obra :' + numero +' ya esta registrado para el año '+ fechaDeteccion);
		}
		
	});
	
}

function activaDiv(){
	if($("form#deteccionFormRegistro #:checked").val()==9){
		$("#divNumReporte").show("fast");
		$("#divDatosCenso").show("fast");
		$("#nmCensor").show();
		$("#dtCensor").show();
		$("#divActividad").hide();
	}else if($("form#deteccionFormRegistro #:checked").val()==8){
		$("#divNumReporte").hide();
		$("#divDatosCenso").hide();
		$("#nmCensor").hide();
		$("#dtCensor").hide();
		$("#divActividad").show("fast");
	}	
}

function activaDiv2(){
	if($("form#deteccionFormReporte #:checked").val()==9){
		$("#divDatosDet").show("fast");
	}else if($("form#deteccionFormReporte #:checked").val()==8){
		$("#divDatosDet").hide();
	}	
}

function deshabilita(){
	if($("form#deteccionFormReporte #:checked").val()!=undefined){
		 $("#:checked").attr('checked', false);
		 $("#divDatosDet").hide();
	}
}

function promocion(){
	
	var radios = document.getElementsByName("radio");
	for (i=0;i<radios.length;i++)
	 {
		if(radios[i].checked)
		{
			var idDeteccion = radios[i].value;
			var sDeteccion = '{"cveDeteccion":'+idDeteccion+'}';
			var deteccion = jQuery.parseJSON(sDeteccion);			
			$('#wrapperDialogPromocion,#deteccionPromocionForm,#cveDeteccion').val(deteccion.cveDeteccion);				
			oDgConfirmaPromocion.dialog('open');
		}
	 }	
}

function validaRegPatron(){
	if(!longitudMandatoria($("form#deteccionFormRegistro #regPatron").val(),10,"Registro Patronal")) return false;
	var deteccion = $("#deteccionFormRegistro").serializeObject(true);
	limpiaDomicilio = false;
	oDgRegistro.dialog('close');
	bloquear();
	$.postJSON("promocionDet/validaRegPatron.do", deteccion, function(data) {
		if(data.cveFkPatron==null){
			alert("El registro Patronal es invalido");
			$("form#deteccionFormRegistro #nomRazonsocial").val("");
			$("form#deteccionFormRegistro #txRfcpatron").val("");
			$("form#deteccionFormRegistro #txCurppatron").val("");
			$("form#deteccionFormRegistro #cveFkPatron").val("");
			$("form#deteccionFormRegistro #actividad").val("");
		}else{
			alert("El registro Patronal es valido");
			$("form#deteccionFormRegistro #nomRazonsocial").val(data.nomRazonsocial);
			$("form#deteccionFormRegistro #txRfcpatron").val(data.txRfcpatron);
			$("form#deteccionFormRegistro #txCurppatron").val(data.txCurppatron);
			$("form#deteccionFormRegistro #cveFkPatron").val(data.cveFkPatron);
			$("form#deteccionFormRegistro #actividad").val(data.actividad);
		}						
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
		oDgRegistro.dialog('open');
		limpiaDomicilio = true;
		//Instrucciones para el 'complete'
		desbloquear();
	});
}

function validaRegPatronAlta(){
	if(!longitudMandatoria($("form#deteccionFormRegistro #regPatron").val(),10,"Registro Patronal")) return false;
	var deteccion = $("#deteccionFormRegistro").serializeObject(true);
	limpiaDomicilio = false;
	oDgRegistro.dialog('close');
	bloquear();
	$.postJSON("alta/validaRegPatron.do", deteccion, function(data) {
		if(data.cveFkPatron==null){
			alert("El registro Patronal es invalido");
			$("form#deteccionFormRegistro #nomRazonsocial").val("");
			$("form#deteccionFormRegistro #txRfcpatron").val("");
			$("form#deteccionFormRegistro #txCurppatron").val("");
			$("form#deteccionFormRegistro #cveFkPatron").val("");
			$("form#deteccionFormRegistro #actividad").val("");
		}else{
			alert("El registro Patronal es valido");
			$("form#deteccionFormRegistro #nomRazonsocial").val(data.nomRazonsocial);
			$("form#deteccionFormRegistro #txRfcpatron").val(data.txRfcpatron);
			$("form#deteccionFormRegistro #txCurppatron").val(data.txCurppatron);
			$("form#deteccionFormRegistro #cveFkPatron").val(data.cveFkPatron);
			$("form#deteccionFormRegistro #actividad").val(data.actividad);
		}						
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
		oDgRegistro.dialog('open');
		limpiaDomicilio = true;
		//Instrucciones para el 'complete'
		desbloquear();
	});
}

function grabar(){
	var crtDeteccion = $("#deteccionFormRegistro").serializeObject(true);				
	$.postJSON("deteccion/agregar.do", crtDeteccion, function(data) {
		alert("La obra ha sido agregada con el Folio: "+data.nuFoliodeteccion);
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
		$("#divNumReporte").hide();
		$("#divDatosCenso").hide();
		$("#divActividad").hide();
		$("form#deteccionFormRegistro #cvePkFaseConst").prop('disabled','');
		$("form#deteccionFormRegistro #cvePkTipObra").prop('disabled','');
		inicializaPosicionPaginador();
		limpiaDomicilio = true;
	});	
}

function grabarAlta(){
	var estatus = $("#bandera").val();
	var crtDeteccion = $("#deteccionFormRegistro").serializeObject(true);				
	$.postJSON("alta/agregar.do", crtDeteccion, function(data) {
		alert("La obra ha sido agregada con el Folio: "+data.nuFoliodeteccion);
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
		$("form#deteccionFormRegistro #cvePkFaseConst").prop('disabled','');
		$("form#deteccionFormRegistro #cvePkTipObra").prop('disabled','');
		limpiarDomicilioAlta();
		
		if(estatus == 'consulta'){
			window.close();
		}
	});	
}

function actualizar(){
	var crtDeteccion = $("#deteccionLayoutForm").serializeObject(true);				
	$.postJSON("alta/actualizar.do", crtDeteccion, function(data) {
		alert("La obra con Folio: "+data.nuFoliodeteccion+" se actualizo correctamente");
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
		$("#divNumReporte").hide();
		$("#divDatosCenso").hide();
		$("#divActividad").hide();
		$("form#deteccionFormRegistro #cvePkFaseConst").prop('disabled','');
		$("form#deteccionFormRegistro #cvePkTipObra").prop('disabled','');
		inicializaPosicionPaginador();
		limpiaDomicilio = true;
	});	
}

function mostarDomGeo(context,fuente){
	var resultado = openWindowregistraDomicilioInegi(context,'catalogo/deteccion/deteccionDomGeografico.do');
	if(fuente == 'registro'){
		refrescar('deteccionFormRegistro');
	}if(fuente == 'buscar'){
		refrescar('deteccionFormReporte');
	}if(fuente == 'validar'){
		refrescar('deteccionFormReporte');
	}if(fuente == 'layout'){
		refrescar('deteccionLayoutForm');
	}
}

function mostarDomGeoAlta(context,fuente){
	var resultado = openWindowregistraDomicilioInegi(context,'deteccion/alta/deteccionDomGeografico.do');
	if(fuente == 'registro'){
		refrescar('deteccionFormRegistro');
	}if(fuente == 'buscar'){
		refrescar('deteccionFormReporte');
	}if(fuente == 'validar'){
		refrescarAlta('deteccionFormValida');
	}if(fuente == 'layout'){
		refrescar('deteccionLayoutForm');
	}
}

function refrescar(form){
	var crtDeteccion = $("#"+form).serializeObject(true);
	$.postJSON("deteccion/obtenerDomicilioSession.do", crtDeteccion,function(data) {
		$("form#"+form+" #estado").val(data.estado);
		$("form#"+form+" #municipio").val(data.municipio);
		$("form#"+form+" #domCalle").val(data.domCalle);
		$("form#"+form+" #refColonia").val(data.refColonia);
		$("form#"+form+" #numNroint").val(data.numNroint);
		$("form#"+form+" #numNroext").val(data.numNroext);
		$("form#"+form+" #numCodigopostal").val(data.numCodigopostal);
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
							
	});
}

function refrescarAlta(form){
	var crtDeteccion = $("#"+form).toObject({mode:'first'});
	$.postJSON("alta/obtenerDomicilioSession.do", crtDeteccion,function(data) {
		$("form#"+form+" #estado").val(data.estado);
		$("form#"+form+" #municipio").val(data.municipio);
		$("form#"+form+" #domCalle").val(data.domCalle);
		$("form#"+form+" #refColonia").val(data.refColonia);
		if(data.numNroint!=null){
			$("form#"+form+" #numNroint").val(data.numNroint);	
		}
		$("form#"+form+" #numNroext").val(data.numNroext);
		$("form#"+form+" #numCodigopostal").val(data.numCodigopostal);
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
							
	});
}


function refrescarAltaModal(form){
	var crtDeteccion = $("#"+form).serializeObject(true);
	$.postJSON("obtenerDomicilioSession.do", crtDeteccion,function(data) {
		$("form#"+form+" #estado").val(data.estado);
		$("form#"+form+" #municipio").val(data.municipio);
		$("form#"+form+" #domCalle").val(data.domCalle);
		$("form#"+form+" #refColonia").val(data.refColonia);
		$("form#"+form+" #numNroint").val(data.numNroint);
		$("form#"+form+" #numNroext").val(data.numNroext);
		$("form#"+form+" #numCodigopostal").val(data.numCodigopostal);
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
							
	});
}

function obtieneIdRegistro(form,campo){
	if(!longitudMandatoria($("form#"+form+" #"+campo).val(),10,"Registro Patronal")) return false;
	if($("form#"+form+" #"+campo).val()==""){
		return false;
	}
	var deteccion = $("#"+form).serializeObject(true);	
	$.postJSON("deteccion/verficaPatron.do", deteccion, function(data) {
		if(data==0){
			alert("El registro Patronal es Incorrecto");
			$("form#"+form+" #cveFkPatron").val("");
			$("form#"+form+" #"+campo).val("");
		}else{
			$("form#"+form+" #cveFkPatron").val(data);
		}						
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
	});
}

function valorMaxim(campo,leyenda,form){
	campo = "form#"+form+" #"+campo;
	if(!valorMaximo($(campo).val(),leyenda,100)){		
		$(campo).val("");		
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

function jsValidaPeriodoInicial(fecha){
	var fechaFinal = $("#fechaFinal").val();
	if(fechaFinal != '' && fecha != ''){
		var anioI = fechaFinal.substring(6,10);
		var anioF = fecha.substring(6,10);
		
		if(anioI == anioF){
			$("#fechaIncial").val(fecha);
			return true;
		}else if(anioI < anioF){
			alert('La fecha inicial no puede ser mayor a la fecha final');
			$("#fechaIncial").val("");
			$("#fechaFinal").val("");
			return;
		}else{
			alert('No se puede elegir mas de un ejercicio');
			$("#fechaIncial").val("");
			$("#fechaFinal").val("");
			return false;
		}
	}	
}

function jsValidaDomObra(){
	
	limpiaEtiquetas();
	
	var resp = true;
	
	if($("#estado").val() == ''){
		$("#labelEstado").html('<label class="etiquetaError">Campo Requerido</label>');
		resp = false;
	}
	if($("#municipio").val() == ''){
		$("#labelMunicipio").html('<label class="etiquetaError">Campo Requerido</label>');
		resp = false;
	}
	if($("#domCalle").val() == ''){
		$("#labelDomCalle").html('<label class="etiquetaError">Campo Requerido</label>');
		resp = false;
	}
	if($("#refColonia").val() == ''){
		$("#labelRefColonia").html('<label class="etiquetaError">Campo Requerido</label>');
		resp = false;
	}
	if($("#numNroext").val() == ''){
		$("#labelNumNroext").html('<label class="etiquetaError">Campo Requerido</label>');
		resp = false;
	}
	if($("#numCodigopostal").val() == ''){
		$("#labelNumCodigopostal").html('<label class="etiquetaError">Campo Requerido</label>');
		resp = false;
	}
	
	
	return resp;
}

function limpiaEtiquetas(){
	
	$("#labelEstado").html('');
	$("#labelMunicipio").html('');
	$("#labelDomCalle").html('');
	$("#labelRefColonia").html('');
	$("#labelNumNroint").html('');
	$("#labelNumNroext").html('');
	$("#labelNumCodigopostal").html('');
}

function limpiarDomicilioAlta(){
	$("#estado").val("");
	$("#municipio").val("");
	$("#domCalle").val("");
	$("#refColonia").val("");
	$("#numNroint").val("");
	$("#numNroext").val("");
	$("#numCodigopostal").val("");
}

function confirmar(){
	
	var id = $('#:checked').val();
	if(id!=undefined){
		$("#domicilioIdV").val(id);
	}
	
}



function jsValidaNUmReporte(){
	
	var valor = $("#nuReportectrlobra").val();
	var fecha = $("#fechaDeteccion").val();
	var bandera = $('form#deteccionFormRegistro #bandera').val(); 
	$("#labelnuReportectrlobra").html('');
	
	var resp = true;
	var errorOcurrido = "";
	if(bandera != 'consulta'){
		//invocar ajax
		$.ajax({
	        url: getAppContextParaJS()+"/deteccion/alta/validaNuReporte.do",
	        async:false,
	        contentType: "application/json",
	        data: "nuReportectrlobra="+valor+"&fechaDeteccion="+fecha,
	        error: function(objeto, quepaso, otroobj){
	        	errorOcurrido = otroobj;
	        	resp = false;
	        },
	        success: function(datos){
	        	resp = datos;
	        },
	        type: "GET"
		});
	}
	
	
	return resp;
}

function jsValidarConsulta(){
	
	$("#labelfechaIncial").html('');		
	$("#labelfechaFinal").html('');
	$("#labelestatus").html('');		
	
	var resp = false;
	var folio = $("#nuFoliodeteccion").val();
	if(folio != ''){		
		$("#fechaIncial").val("");		
		$("#fechaFinal").val("");
		$("#estatus").val("");
		$("#labelfechaIncial").html('');		
		$("#labelfechaFinal").html('');
		$("#labelestatus").html('');	
		return true;
	}if($("#fechaIncial").val() == ''){		
		$("#labelfechaIncial").html('<label style="color: red;"> Campo Requerido </label>');
	}if($("#fechaFinal").val() == ''){
		$("#labelfechaIncial").html('<label style="color: red;"> Campo Requerido </label>');
	}if( $("#estatus").val() == ''){
		$("#labelestatus").html('<label style="color: red;"> Campo Requerido </label>');	
	}if($("#fechaIncial").val() != '' && $("#fechaFinal").val() != '' && $("#estatus").val() != '' ){
		resp = true;
	}
	
	return resp;
}

function jsLlenaEstatus(){
	var variable = '{"estatus":"1"}';
	var variableJson = jQuery.parseJSON(variable);
	bloquear();
	$.postJSON("deteccion/llenaEstatus.do",variableJson,function(data) { 
			var myselect=document.getElementById("estatus");
			myselect.options.length = 1;
			for(var i = 0 ; i < data.length ; i++){
				myselect.add(new Option(data[i][1], data[i][0]));
			}	
			desbloquear();
		
	});
}

function validaIdDeteccion(){
	var variable = '{"estatus":"1"}';
	var variableJson = jQuery.parseJSON(variable);
	bloquear();
	$.postJSON("alta/validaIdDeteccion.do",variableJson,function(data) {
		if(data != null){
			if(data.cveTipocorr != null){
				if(data.cveTipocorr == 8){
					$('input:radio[name=cveTipocorr]')[0].checked = true;
				}else if(data.cveTipocorr == 9){
					$('input:radio[name=cveTipocorr]')[1].checked = true;
				}
				activaDiv();
			}
			
			
			$('form#deteccionFormRegistro #fechaDeteccion').val(data.fechaDeteccion);
			$('form#deteccionFormRegistro #nuReportectrlobra').val(data.nuReportectrlobra);			
			$('form#deteccionFormRegistro #nomRazonsocial').val(data.nomRazonsocial);
			$('form#deteccionFormRegistro #fechaEstimIncio2').val(data.fechaEstimIncio2);
			$('form#deteccionFormRegistro #fechaEstTerm2').val(data.fechaEstTerm2);
			$('form#deteccionFormRegistro #tipClaseobra').val(data.tipClaseobra);
			
			try{
				$('form#deteccionFormRegistro #tipClaseobra').val(data.tipClaseobra);
				
				
				$('form#deteccionFormRegistro #tipClaseobra').trigger('change');
				setTimeout(function() {
					$('form#deteccionFormRegistro #cvePkTipObra').val(data.cvePkTipObra);
					$('form#deteccionFormRegistro #cvePkTipObra').trigger('change');
				},2000);
				
				if(data.cvePkFaseConst!=null){
					setTimeout(function() {$('form#deteccionFormRegistro #cvePkFaseConst').val(data.cvePkFaseConst);},2000);
				}
			
			}catch(err){}
			
			$('form#deteccionFormRegistro #idPromovido').val(data.idPromovido);
			$('form#deteccionFormRegistro #canSuperficie').val(data.canSuperficie);
			$('form#deteccionFormRegistro #txRfcpatron').val(data.txRfcpatron);
			$('form#deteccionFormRegistro #txCurppatron').val(data.txCurppatron);
			
			
			$('form#deteccionFormRegistro #impCostoobra').val(data.impCostoobra);
			$('form#deteccionFormRegistro #porAvanceobraEst').val(data.porAvanceobraEst);
			$('form#deteccionFormRegistro #txEmail').val(data.txEmail);
			$('form#deteccionFormRegistro #txTelefono').val(data.txTelefono);
			$('form#deteccionFormRegistro #desDependenciapub').val(data.desDependenciapub);
			$('form#deteccionFormRegistro #desDepcontratante').val(data.desDepcontratante);
			$('form#deteccionFormRegistro #cveFkZona').val(data.cveFkZona);
			$('form#deteccionFormRegistro #numTrabajdores').val(data.numTrabajdores);
			$('form#deteccionFormRegistro #cveDeteccion').val(data.cveDeteccion);
			$('form#deteccionFormRegistro #actividad').val(data.txActividad);
			$('form#deteccionFormRegistro #bandera').val(data.bandera); 
			
			desbloquear();
		}
	});
	desbloquear();
}

function mandaURL(){ 

	document.getElementById("altaForm").submit();
	
}

function cambiaCombo(){
	buscar();
	$("#actionButtons").hide();
	$("#deteccionButtons").hide();
}

function refrescaBusqueda(){
	oDtDeteccion.fnDraw();
}

function doNothing(){}

function jsLlenaTipoObra(){
	
	var variable = '{"estatus":"1"}';
	var variableJson = jQuery.parseJSON(variable);
	$.postJSON("deteccion/cboTipoObra.do", variableJson, function(data) {
		
		var myselect=document.getElementById("selectTipoObra");
		myselect.options.length = 1;
		
		for(var i = 0 ; i < data.length ; i++){
			myselect.add(new Option(data[i][1], data[i][0]));
		}
		
		
	}).error(function(data){ 
			alert("error" + data);
		}).complete(function(){
			//Instrucciones para el 'complete'
		});
}
