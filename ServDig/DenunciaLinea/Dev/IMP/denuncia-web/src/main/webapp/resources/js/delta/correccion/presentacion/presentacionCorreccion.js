//Referencia para el objeto data table
/**
 * Datos unicode
 * \u00e1 -> á
 * \u00e9 -> é
 * \u00ed -> í
 * \u00f3 -> ó
 * \u00fa -> ú
 * \u00c1 -> Á
 * \u00c9 -> É
 * \u00cd -> Í
 * \u00d3 -> Ó
 * \u00da -> Ú
 * \u00f1 -> ñ
 * \u00d1 -> Ñ
 */
var objDataTableSolFoliosCorreccion;
var objDataTableCopPagadas;
var errorDialog;
var reglasValidacionInicial;



$(document).ready(function() {
	//$("#fechaAutorizacionCorreccionEspontanea, #fechaAceptacionInvitacionCorreccion, #fechaEjercicioInicial, #fechaEjercicioFinal, #fechaProrroga, #fechaFirma").datepicker( { dateFormat: 'dd/mm/yy' });
	$("#fechaAutorizacionCorreccionEspontanea").datepicker( { dateFormat: 'dd/mm/yy' });
	$("#menuPresentacionCorreccionID").hide();
	$("#presentacionMainDialog").hide();
	$("#wrapperDataTableFoliosSolicitudCorreccion").hide();
	objDataTableSolFoliosCorreccion = $('#tableFoliosSolicitudCorreccion').dataTable({
		bJQueryUI : true,
		bFilter : false,
		bInfo:true,
		bSort: false,
		"bPaginate": true,
		"bAutoWidth" : false,
		"bServerSide" : true,
		"sAjaxSource" : "presentacion/buscarFoliosSolicitudCorreccion.do",
		"aoColumns" : [ {
							"sTitle": "Folio Solicitud Correccion",
							"sClass": "dtCenterClassColumn",
							"fnRender" :function(oObj){
								var idEstatus = oObj.aData['idEstatus'];
								var retVal;
								if(idEstatus == 2){
									retVal = '<a href=javascript:buscarSolicitud("' + oObj.aData['idSolicitudCorreccion'] + '")>' + oObj.aData['nuFolioSolCorreccion'] + '</a>';
								}else{
									retVal = '<label>' + oObj.aData['nuFolioSolCorreccion'] + '</label>';
								}
								return retVal;
							}, 
							aTargets: [0]
						 }, {
							"sTitle" : "Fecha L&iacute;mite Solicitud",
							"mDataProp" : "fdLimiteSolicitud",
							"sClass": "dtCenterClassColumn"
						 }, {
							"sTitle" : "Estatus",
							"sClass":"dtCenterClassColumn",
							"fnRender" :function(oObj){
								var idEstatus = oObj.aData['idEstatus'];
								var retVal;
								if(idEstatus == 3){
									var motivo = oObj.aData['motivoRechazo'];
									retVal = '<label>' + oObj.aData['estatus'] + '-' + motivo + '</label>';
								}else{
									retVal = '<label>' + oObj.aData['estatus'] + '</label>';
								}
								return retVal;
							}
						 }, {
							"sTitle" : "Tipo de Correccion",
							"mDataProp" : "tipoCorreccion",
							"sClass": "dtCenterClassColumn"
						 }
					],
		"fnServerData" : function(sSource, aoData, fnCallback) {
			var wrapper = new Object();
			wrapper.aoData = aoData;								
	
			var oForm = $("#presentacionCorreccionModel").toObject({mode:'first'});
			wrapper.oForm = oForm;
			
			$.postJSON(sSource, wrapper, function(data) {										
				fnCallback(data);					
			});	
		}
	});
	
	//Dialogo para mostrar errores
	errorDialog = $("#dialog-error").dialog({
		autoOpen: false,
		modal: true,
		resizable: true,
		width: 930,
		buttons: {
			Ok: function() {
				$(this).dialog("close");
			}
		}
	});
	
	reglasValidacionInicial = $("#presentacionCorreccionModel").validate({
		 rules: {
			 lugar: {
				 required:true,
				 alphanumeric: true
			 },
			 nombreYFirmaPatron: {
				 required:true,
				 alphanumeric: true
			 },
			 folioSoicitudCorreccionFiltro: {
				 required:true,
				 alphanumeric: true
			 },
			 fechaFirma: {
				 date: true
			 },
			 chkDocumentacionCorreccion: {
				 required:true
			 }
		 }
	});
	
});

/**
 * Esta funcion es para buscar todos los folios de solicitud de correccion bajo las siguientes premisas
 * 	1. Si se requiere una busqueda por registro patronal nos traeremos todas solicitudes de corrección NO RECHAZADAS, que esten ACEPTADAS y la fecha limite
 * 		no este vencida en caso de existir una prorroga se daran 10 dias más.
 * 	2. Por Numero de folio de solicitud de corrección.
 */
function buscarSolicitudesDeCorreccion(){
	$('#CopPagadasAnexoDiv').html('');
	objDataTableSolFoliosCorreccion.fnDraw();
	$("#wrapperDataTableFoliosSolicitudCorreccion").show();
	$("#menuPresentacionCorreccionID").hide();
	$("#presentacionMainDialog").hide();
}

function mostrarSolicitudesDeCorreccion(){
	$('#CopPagadasAnexoDiv').html('');
	$("#wrapperDataTableFoliosSolicitudCorreccion").show();
	$("#menuPresentacionCorreccionID").hide();
	$("#presentacionMainDialog").hide();
}

function mostrarAnexoSolicitudCorreccion(){
	$("#CopPagadasAnexoDiv").toggle();
}

/**
 * Esta funcion es cuando le den click a algún link con el folio de solicitud de correccion en el datatable
 */
function buscarSolicitud(folio){
	limpiarCampos();
	$('#CopPagadasAnexoDiv').html('');
	var correccion = $("#presentacionCorreccionModel").serializeObject(true);
	correccion.folioSoicitudCorreccionFiltro = folio;
	bloquear();
	$.postJSON("presentacion/buscarPorFolio.do", correccion, function(data) {
		
		if(data.mensajeError != null){
			errorDialog.html('<font size="2px">' + data.mensajeError + '</font>');
			errorDialog.dialog("open");
		}else{
			$('#folioSolicitudSeleccionadoLabelID').html('<b>' + data.folioCorreccion + '</b>');
			$("form#presentacionCorreccionModel #folioCorreccionInputID").val(data.folioCorreccion);
			$("form#presentacionCorreccionModel #razonSocialID").val(data.razonSocial);
			$("form#presentacionCorreccionModel #numeroRegistroPatronalInputID").val(data.numeroRegistroPatronal);
			$("form#presentacionCorreccionModel #digitoVerificadorInputID").val(data.digitoVerificador);
			$("form#presentacionCorreccionModel #curpInputID").val(data.curp);
			$("form#presentacionCorreccionModel #rfcInputID").val(data.rfc);
	
			//Domicilio fiscal
			$("form#presentacionCorreccionModel #calleInputID").val(data.calle);
			$("form#presentacionCorreccionModel #numExteriorInputID").val(data.numExterior);
			$("form#presentacionCorreccionModel #numInteriorInputID").val(data.numInterior);
			$("form#presentacionCorreccionModel #coloniaInputID").val(data.colonia);
			$("form#presentacionCorreccionModel #municipioInputID").val(data.municipio);
			$("form#presentacionCorreccionModel #localidadInputID").val(data.localidad);
			$("form#presentacionCorreccionModel #entidadFederativaInputID").val(data.entidadFederativa);
			$("form#presentacionCorreccionModel #codigoPostalInputID").val(data.codigoPostal);
			$("form#presentacionCorreccionModel #telefonoInputID").val(data.telefono);
			$("form#presentacionCorreccionModel #emailInputID").val(data.email);
				
			//Fechas de la correccion
			if(data.tipoDeCorreccion == 1){
				$('input:radio[name=tipoDeCorreccion]:nth(0)').attr('checked',true);
				$("#fechaAutorizacionCorreccionEspontanea").datepicker("enable");
				$('#fechaAutorizacionCorreccionEspontanea').attr('readonly', false);
			}else if(data.tipoDeCorreccion == 2){
				$('input:radio[name=tipoDeCorreccion]:nth(1)').attr('checked',true);
				$("form#presentacionCorreccionModel #fechaAceptacionInvitacionCorreccion").val(data.fechaAceptacionInvitacionCorreccion);
			}
				
			$("form#presentacionCorreccionModel #fechaEjercicioInicial").val(data.fechaEjercicioInicial);
			$("form#presentacionCorreccionModel #fechaEjercicioFinal").val(data.fechaEjercicioFinal);
			$("form#presentacionCorreccionModel #fechaProrroga").val(data.fechaProrroga);
			$("form#presentacionCorreccionModel #numeroTrabajadores").val(data.numeroTrabajadores);
				
			//$("form#presentacionCorreccionModel #observaciones").val(JSON.stringify(data, null, 4));
			$("form#presentacionCorreccionModel #observaciones").val(data.observaciones);
				
			//jQuery.each(data.anexoSolCorrPat, function() { 
				//alert(this.codigoPostal + " : " + this.cvePatron);
			//});
			
			$('#CopPagadasAnexoDiv').html(data.htmlCopPagadas);
			$("#menuPresentacionCorreccionID").show();
			$("#presentacionMainDialog").show();
			$("#wrapperDataTableFoliosSolicitudCorreccion").hide();
			$("#CopPagadasAnexoDiv").hide();
		}
	}).error(function(data){
		validarSesionExpirada(data);
	}).complete(function(){
		desbloquear(); 
	});
}

/*En Desuso para probar la firma electronica*/
function presentarCorreccion(){
	var contexto = $('#idContexto').val();
	var urlPDF = contexto + "/servlet/EnviaArchivoServlet";
	if($('#radioEspontanea').is(':checked')) { 
		$('#fechaAutorizacionCorreccionEspontanea').rules('add',{required:true});
		//Correccion Espontanea 1
		$("form#presentacionCorreccionModel #tipoDeCorreccionHidden").val('1');
	}else if($('#radioInvitacion').is(':checked')){
		$("form#presentacionCorreccionModel #tipoDeCorreccionHidden").val('2');
	}
	
	if(reglasValidacionInicial.form()){
		$('#CopPagadasAnexoDiv').html('');
		var correccion = $("#presentacionCorreccionModel").serializeObject(true);
		bloquear();
		$.postJSON("presentacion/presentarCorreccion.do", correccion, function(data) {
			errorDialog.html('<font size="2px">El folio de correcci&oacute;n ha sido presentado con &eacute;xito</font>');
			errorDialog.dialog("open");
			limpiarCampos();
			window.open(urlPDF,"PresentacionCorrecion","menubar=1,resizable=1,width=500,height=500");
		}).error(function(data){ 
			alert("error" + data);
		}).complete(function(){
			desbloquear(); 
			$("#menuPresentacionCorreccionID").hide();
			$("#presentacionMainDialog").hide();
			$("#wrapperDataTableFoliosSolicitudCorreccion").hide();
		});
	}
}

function limpiarCampos(){
	$('#folioSolicitudSeleccionadoLabelID').html('');
	$("form#presentacionCorreccionModel #folioCorreccionInputID").val('');
	$("form#presentacionCorreccionModel #razonSocialID").val('');
	$("form#presentacionCorreccionModel #numeroRegistroPatronalInputID").val('');
	$("form#presentacionCorreccionModel #digitoVerificadorInputID").val('');
	$("form#presentacionCorreccionModel #curpInputID").val('');
	$("form#presentacionCorreccionModel #rfcInputID").val('');

	//Domicilio fiscal
	$("form#presentacionCorreccionModel #calleInputID").val('');
	$("form#presentacionCorreccionModel #numExteriorInputID").val('');
	$("form#presentacionCorreccionModel #numInteriorInputID").val('');
	$("form#presentacionCorreccionModel #coloniaInputID").val('');
	$("form#presentacionCorreccionModel #municipioInputID").val('');
	$("form#presentacionCorreccionModel #localidadInputID").val('');
	$("form#presentacionCorreccionModel #entidadFederativaInputID").val('');
	$("form#presentacionCorreccionModel #codigoPostalInputID").val('');
	$("form#presentacionCorreccionModel #telefonoInputID").val('');
	$("form#presentacionCorreccionModel #emailInputID").val('');
		
	$('input:radio[name=tipoDeCorreccion]:nth(0)').attr('checked',false);
	$('input:radio[name=tipoDeCorreccion]:nth(1)').attr('checked',false);
	
	$('input[name=chkCombtPago]').attr('checked', false);
	$('input[name=chkCombtAvisosAfil]').attr('checked', false);
	$('input[name=chkDocumentacionCorreccion]').attr('checked', false);
		
	$("form#presentacionCorreccionModel #fechaEjercicioInicial").val('');
	$("form#presentacionCorreccionModel #fechaEjercicioFinal").val('');
	$("form#presentacionCorreccionModel #fechaProrroga").val('');
	$("form#presentacionCorreccionModel #numeroTrabajadores").val('');
	
	$("form#presentacionCorreccionModel #fechaAutorizacionCorreccionEspontanea").val('');
	$("form#presentacionCorreccionModel #fechaAceptacionInvitacionCorreccion").val('');
		
	$("form#presentacionCorreccionModel #observaciones").val('');
	$("#lugar").val('');
	$("#nombreYFirmaPatron").val('');
	reglasValidacionInicial.resetForm(); 
	$("#fechaAutorizacionCorreccionEspontanea").rules("remove");
	$("#fechaAutorizacionCorreccionEspontanea").datepicker("disable");
	$("form#presentacionCorreccionModel #tipoDeCorreccionHidden").val('');
}

function getCadenaOriginal(){
	return 'vladimiraguirrepiedragil|60321975|AUPV8102095Q4';
}

function registrarFirmado(){
	var contexto = $('#idContexto').val();
	var urlPDF = contexto + "/servlet/EnviaArchivoServlet";
	if($('#radioEspontanea').is(':checked')) { 
		$('#fechaAutorizacionCorreccionEspontanea').rules('add',{required:true});
		//Correccion Espontanea 1
		$("form#presentacionCorreccionModel #tipoDeCorreccionHidden").val('1');
	}else if($('#radioInvitacion').is(':checked')){
		$("form#presentacionCorreccionModel #tipoDeCorreccionHidden").val('2');
	}
	
	if(reglasValidacionInicial.form()){
		$('#CopPagadasAnexoDiv').html('');
		var correccion = $("#presentacionCorreccionModel").serializeObject(true);
		bloquear();
		$.postJSON("presentacion/presentarCorreccion.do", correccion, function(data) {
			errorDialog.html('<font size="2px">El folio de correcci&oacute;n ha sido presentado con &eacute;xito</font>');
			errorDialog.dialog("open");
			limpiarCampos();
			window.open(urlPDF,"PresentacionCorrecion","menubar=1,resizable=1,width=500,height=500");
		}).error(function(data){ 
			alert("error" + data);
		}).complete(function(){
			desbloquear(); 
			$("#menuPresentacionCorreccionID").hide();
			$("#presentacionMainDialog").hide();
			$("#wrapperDataTableFoliosSolicitudCorreccion").hide();
		});
	}
}