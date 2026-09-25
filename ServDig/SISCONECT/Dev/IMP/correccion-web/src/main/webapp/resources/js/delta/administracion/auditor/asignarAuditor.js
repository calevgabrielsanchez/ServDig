var tableResult          = "#dtAsignarAuditor";
var oDtTableResult;
var tableResultAuditores = "#dtAuditoresDisponibles";
var oDtTableResultAuditores;
var tableResultCarga     = "#dtCargaTrabajo";
var oDtTableResultCarga;
var idDgDatosFolio = "#dgDatosFolio";
var oDgDatosFolio;

var idDgConfirmar = "#dgConfirmaAuditor";
var oDgConfirmar;



$(document).ready(function() {
	
	jsLlenaOrigen();
	
	$("form#asignarAuditorForm #fechaIncial,form#asignarAuditorForm #fechaFinal").datepicker( { 
		dateFormat: 'dd/mm/yy',
		onSelect: function(dateText, inst) { 
			jsValidaFechasLimite();
			jsValidaFecFinal();
		    }		
	});
	
	$.postJSON("asignaAuditor/obtenerFechaServidor.do", null,function(data) {
	}).error(function(data){
		validarSesionExpirada(data);
	}).complete(function(data){
//		alert(JSON.stringify(data, null, 4));
		$("form#asignarAuditorForm #fechaIncial,form#asignarAuditorForm #fechaFinal").datepicker('option', 'maxDate', data.responseText);
	});
	
	// Dialog de Datos del folio seleccionado
	oDgDatosFolio = $(idDgDatosFolio).dialog({
			autoOpen: false,
			modal:true,
			resizable:false,
			width: 930,
			closeOnEscape: false
	 });
	
	// Dialog para confirmar
	oDgConfirmar = $(idDgConfirmar).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		height: 150,
		width: 700,
		buttons: {
			"Si": function() { 
				asignar();				
				$(this).dialog("close"); 
			}, 
			"No": function() { 
				$(this).dialog("close"); 
			} 
		}
	});
	
			
	
		
	
});

/**
 * Metodo que llena el combo Origen con una lista de crc_tipocorr
 * Enrique Duran Jimenez
 * 24/05/2012
 */
function jsLlenaOrigen(){
	
	var variable = '{"idOrigen":"1"}';		
	var variableJson = jQuery.parseJSON(variable);
	$.postJSON("asignaAuditor/cboOrigen.do", variableJson, function(data) {
		
		var myselect=document.getElementById("cboOrigen");
		myselect.options.length = 1;
		
		for(var i = 0 ; i < data.length ; i++){
			myselect.add(new Option(data[i][2], data[i][0]));
		}
		
	});
}

/**
 * Metodo para validar si en el campo Folio se ingresa un numero de folio borre los demas campos
 * Enrique Duran Jimenez
 * 24/05/2012
 */
function jsValidaOficio(valor){
	
	if(valor.length > 16){
		$("form#asignarAuditorForm #fechaIncial").val("");
		$("form#asignarAuditorForm #fechaFinal").val("");
		$("form#asignarAuditorForm #cboOrigen").val("");
		$("form#asignarAuditorForm #labelFolio").html('');
	}
	
	return;
}

/**
 * Metodo manda al controlador para busqueda
 * Enrique Duran Jimenez
 * 24/05/2012
 */
function buscar(){
	
	$("#btnVisualizar").hide('fast');
	$("form#asignarAuditorForm #labelOrigen").html('');
	$("form#asignarAuditorForm #labelPeriodo").html('');
	$("form#asignarAuditorForm #labelFolio").html('');
	
	var tipoFolio = 'vacio';
	if(jsValidaBuscar() == true){ // selecciono del combo
		if($("form#asignarAuditorForm #cboOrigen").val() >= 3 && $("form#asignarAuditorForm #cboOrigen").val() <= 7){  // selecciono del combo una promocion
			tipoFolio = 'promocion';
		}
		if($("form#asignarAuditorForm #cboOrigen").val() >= 10 && $("form#asignarAuditorForm #cboOrigen").val() <= 11){  // selecciono del combo una invitacion
			tipoFolio = 'invitacion';
		}
		if(  $("form#asignarAuditorForm #cboOrigen").val() == 1 || $("form#asignarAuditorForm #cboOrigen").val() == 2 || $("form#asignarAuditorForm #cboOrigen").val() == 12 ){  // selecciono del combo una solicitud de correccion
			tipoFolio = 'solicitud';
		}
	}else{ // capturo un folio
		$("form#asignarAuditorForm #fechaIncial").val('');
		$("form#asignarAuditorForm #fechaFinal").val('');
		$("form#asignarAuditorForm #cboOrigen").val('');
		if($("form#asignarAuditorForm #folioTemp").val() != '' && $("form#asignarAuditorForm #folioTemp").val().length > 16){
			tipoFolio = jsValidaFolio();
		}else{
			$("form#asignarAuditorForm #labelFolio").html('<label class="etiquetaError">Capturar un folio valido</label>');
		}
	}

	if(tipoFolio == 'promocion'){
		promocionPagina();
	}
	if(tipoFolio == 'invitacion'){
		
		if(oDtTableResult != undefined){
			  oDtTableResult.fnDestroy();			  
		  }		
		$(tableResult).html('');
		oDtTableResult = $(tableResult).dataTable(
				{
					bJQueryUI : true,
					bFilter : false,
					bInfo : true,
					bSort : false,
					
					"bPaginate" : true,
					"bAutoWidth" : false,
					"bServerSide" : false,
					"aoColumns" : [
							{
								fnRender : function(
										oObj) {
									var retVal = '<input type="radio" value="' + oObj.aData['cveInvitacion']
											+ '" id="radioTable" class="radioDeteccion" name="radio" onclick="javascript:muestraDiv();"/> ';
									return retVal;
								},
								aTargets : [ 0 ]
							},
							{
								"sWidth": "15%",
								"sTitle" : "Folio",
								"mDataProp" : "nuFolioInvitacion",
								"sClass" : "dtCenterClassColumn"
							},{
								"sWidth": "20%",
								"sTitle" : "Registro Patronal",
								"mDataProp" : "regPatronal",
								"sClass" : "dtCenterClassColumn"
							},
							{
								"sWidth": "20%",
								"sTitle" : "Raz&oacute;n Social",
								"mDataProp" : "razonSocial",
								"sClass" : "dtCenterClassColumn"
							}
							,{
								"sWidth": "15%",
								"sTitle" : "Periodo Inicial",
								"mDataProp" : "fechaIncial",
								"sClass" : "dtCenterClassColumn"
							},{
								"sWidth": "15%",
								"sTitle" : "Periodo Final",
								"mDataProp" : "fechaFinal",
								"sClass" : "dtCenterClassColumn"
							},{
								"sWidth": "15%",
								"sTitle" : "Fecha Emisi&oacute;n",
								"mDataProp" : "fechaEmision",
								"sClass" : "dtCenterClassColumn"
							}
							 ],
					"bProcessing" : true,
					"sAjaxSource" : 'asignaAuditor/paginarInvitacion.do',
					"fnServerData" : function(sSource,aoData,fnCallback) {
						
						bloquear();
						
						var wrapper = new Object();
						wrapper.aoData = aoData;
	
						var oForm = $("#asignarAuditorForm").toObject({mode : 'first'});
						wrapper.oForm = oForm;
	
						$.postJSON(sSource,wrapper,function(data) { 
							
							fnCallback(data);
							desbloquear();
						 }).error(function(datas){ 
								validarSesionExpirada(datas);				 
						});
					}
				});
	}
	if(tipoFolio == 'solicitud'){
		
		if(oDtTableResult != undefined){
			  oDtTableResult.fnDestroy();			  
		  }		
		$(tableResult).html('');
		oDtTableResult = $(tableResult).dataTable(
				{
					bJQueryUI : true,
					bFilter : false,
					bInfo : true,
					bSort : false,
					
					"bPaginate" : true,
					"bAutoWidth" : false,
					"bServerSide" : false,
					"aoColumns" : [
							{
								fnRender : function(
										oObj) {
									var retVal = '<input type="radio" value="' + oObj.aData['cveSolicitudCorr']
											+ '" id="radioTable" class="radioDeteccion" name="radio" onclick="javascript:muestraDiv();"/> ';
									return retVal;
								},
								aTargets : [ 0 ]
							},
							{
								"sWidth": "15%",
								"sTitle" : "Folio",
								"mDataProp" : "nuFolio",
								"sClass" : "dtCenterClassColumn"
							},{
								"sWidth": "20%",
								"sTitle" : "Registro Patronal",
								"mDataProp" : "patron",
								"sClass" : "dtCenterClassColumn"
							},
							{
								"sWidth": "20%",
								"sTitle" : "Raz&oacute;n Social",
								"mDataProp" : "razonSocialPatronCorregir",
								"sClass" : "dtCenterClassColumn"
							}
							,{
								"sWidth": "15%",
								"sTitle" : "Periodo Inicial",
								"mDataProp" : "fechaInicial",
								"sClass" : "dtCenterClassColumn"
							},{
								"sWidth": "15%",
								"sTitle" : "Periodo Final",
								"mDataProp" : "fechaFinal",
								"sClass" : "dtCenterClassColumn"
							},{
								"sWidth": "15%",
								"sTitle" : "Fecha Emisi&oacute;n",
								"mDataProp" : "fechaPresentacion",
								"sClass" : "dtCenterClassColumn"
							}
							 ],
					"bProcessing" : true,
					"sAjaxSource" : 'asignaAuditor/paginarSolicitud.do',
					"fnServerData" : function(sSource,aoData,fnCallback) {
						
						bloquear();
						
						var wrapper = new Object();
						wrapper.aoData = aoData;
	
						var oForm = $("#asignarAuditorForm").toObject({mode : 'first'});
						wrapper.oForm = oForm;
	
						$.postJSON(sSource,wrapper,function(data) { 
							
							fnCallback(data);
							desbloquear();
						 }).error(function(datas){ 
								validarSesionExpirada(datas);				 
						});
					}
				});
	}if(tipoFolio == 'vacio'){
		$("form#asignarAuditorForm #labelFolio").html('<label class="etiquetaError">Capturar un folio valido</label>');
	}
	
}

/**
 * Metodo manda al controlador para busqueda por promocion
 * Enrique Duran Jimenez
 * 24/05/2012
 */
function promocionPagina(){

	
	if(oDtTableResult != undefined){
		  oDtTableResult.fnDestroy();			  
	  }		
	$(tableResult).html('');
	oDtTableResult = $(tableResult).dataTable(
			{
				bJQueryUI : true,
				bFilter : false,
				bInfo : true,
				bSort : false,
				
				"bPaginate" : true,
				"bAutoWidth" : false,
				"bServerSide" : false,
				"aoColumns" : [
						{
							fnRender : function(
									oObj) {
								var retVal = '<input type="radio" value="' + oObj.aData['cvePromocion']
										+ '" id="radioTable" class="radioDeteccion" name="radio" onclick="javascript:muestraDiv();"/> ';
								return retVal;
							},
							aTargets : [ 0 ]
						},
						{
							"sWidth": "15%",
							"sTitle" : "Folio",
							"mDataProp" : "nuFoliopromocion",
							"sClass" : "dtCenterClassColumn"
						},{
							"sWidth": "20%",
							"sTitle" : "Registro Patronal",
							"mDataProp" : "regPatron",
							"sClass" : "dtCenterClassColumn"
						},
						{
							"sWidth": "20%",
							"sTitle" : "Raz&oacute;n Social",
							"mDataProp" : "razonSocial",
							"sClass" : "dtCenterClassColumn"
						}
						,{
							"sWidth": "15%",
							"sTitle" : "Periodo Inicial",
							"mDataProp" : "fechaInicio",
							"sClass" : "dtCenterClassColumn"
						},{
							"sWidth": "15%",
							"sTitle" : "Periodo Final",
							"mDataProp" : "fechaFin",
							"sClass" : "dtCenterClassColumn"
						},{
							"sWidth": "15%",
							"sTitle" : "Fecha Emisi&oacute;n",
							"mDataProp" : "fechaAtencion",
							"sClass" : "dtCenterClassColumn"
						}
						 ],
				"bProcessing" : true,
				"sAjaxSource" : 'asignaAuditor/paginarPromocion.do',
				"fnServerData" : function(sSource,aoData,fnCallback) {
					
					bloquear();
					
					var wrapper = new Object();
					wrapper.aoData = aoData;

					var oForm = $("#asignarAuditorForm").toObject({mode : 'first'});
					wrapper.oForm = oForm;

					$.postJSON(sSource,wrapper,function(data) { 
						
						fnCallback(data);
						desbloquear();
					 }).error(function(datas){ 
							validarSesionExpirada(datas);				 
							desbloquear();
					});
				}
			});

}

/**
 * Funcion que valida los campos capturados regresa un boolean
 * Enrique Duran Jimenez
 * 25/05/2012
 */
function jsValidaBuscar(){
	
	var resp = false;
	
	if($("form#asignarAuditorForm #folioTemp").val() == ''){
		if($("form#asignarAuditorForm #cboOrigen").val() == ''){
			$("form#asignarAuditorForm #labelOrigen").html('<label class="etiquetaError">Campo Requerido</label>');
		}
		if($("form#asignarAuditorForm #fechaIncial").val() == '' || $("form#asignarAuditorForm #fechaFinal").val() == ''){
			$("form#asignarAuditorForm #labelPeriodo").html('<label class="etiquetaError">Campo Requerido</label>');
		}
		if($("form#asignarAuditorForm #cboOrigen").val() != '' && $("form#asignarAuditorForm #fechaIncial").val() != '' && $("form#asignarAuditorForm #fechaFinal").val() != ''){
			resp = true;
		}
	}
	
	return resp;
}

/**
 * Funcion que valida el tipo de folio capturado para paginar
 * Enrique Duran Jimenez
 * 25/05/2012
 */
function jsValidaFolio(){
	
	var folio = $("form#asignarAuditorForm #folioTemp").val();
	var array_folio = folio.split("/");
	if(array_folio != undefined && array_folio.length > 1){
		var tipo = array_folio[1];
		
		var regresa;
		if(tipo != ''){
			if(tipo == 'EXO'){
				regresa = 'promocion';
			}
			if(tipo == 'EX'){
				regresa = 'promocion';
			}
			if(tipo == 'SBC'){
				regresa = 'promocion';
			}
			if(tipo == 'SATICB'){
				regresa = 'promocion';
			}
			if(tipo == 'SATICA'){
				regresa = 'promocion';
			}	
			if(tipo == 'CI'){
				regresa = 'invitacion';
			}	
			if(tipo == 'CCI'){
				regresa = 'invitacion';
			}	
			if(tipo == 'CE'){
				regresa = 'solicitud';
			}	
			if(tipo == 'CCE'){
				regresa = 'solicitud';
			}	
		}else{
			regresa = 'vacio';
		}
	}else{
		regresa = 'vacio';
	}
	return regresa;

}

/**
 * Funcion que valida el tipo de folio capturado para mostrar los datos del folio
 * Enrique Duran Jimenez
 * 04/06/2012
 */
function jsValidaFolioVer(){
	
	var folio = $("form#asignarAuditorForm #folioTemp").val();
	var array_folio = folio.split("/");
	if(array_folio != undefined && array_folio.length > 1){
		var tipo = array_folio[1];
		
		var regresa;
		if(tipo != ''){
			if(tipo == 'EXO'){
				regresa = '6';
			}
			if(tipo == 'EX'){
				regresa = '5';
			}
			if(tipo == 'SBC'){
				regresa = '7';
			}
			if(tipo == 'SATICB'){
				regresa = '4';
			}
			if(tipo == 'SATICA'){
				regresa = '3';
			}	
			if(tipo == 'CI'){
				regresa = '11';
			}	
			if(tipo == 'CCI'){
				regresa = '10';
			}	
			if(tipo == 'CE'){
				regresa = '1';
			}	
			if(tipo == 'CCE'){
				regresa = '2';
			}	
		}else{
			regresa = 'vacio';
		}
	}else{
		regresa = 'vacio';
	}
	
	return regresa;

}

/**
 * Funcion que muestra el boton ASIGNAR REVISAR
 * @author Enrique Duran Jimenez
 * @since 28/05/2012
 */
function muestraDiv(){
	$("#btnVisualizar").show('fast');
}

/**
 * Funcion que muestra el jsp datosFolio.jsp
 * @author Enrique Duran Jimenez
 * @since 28/05/2012
 */
function mostrar(){
	var id = $('#:checked').val();
	var idOrigen = $("form#asignarAuditorForm #cboOrigen").val();
	if(idOrigen == ''){
		idOrigen = jsValidaFolioVer();
	}
	var crtPromocion = '{"cveTemp":'+ id +',"bandera":'+ idOrigen +'}';
	var variableJson = jQuery.parseJSON(crtPromocion);
	bloquear();
	$.postJSON("asignaAuditor/llenaDatosFolio.do", variableJson,function(data) {
		if(data != null){
			
			jsLimpiaForma();
			
			$("form#datosFolioForm #labelFolio").html('<label>' + data.folioTemp + '</label>');
			if(data.fechaInicio != null){
				$("form#datosFolioForm #labelPeriodoIni").html('<label>' + data.fechaInicio + '</label>');
			}
			if(data.fechaFin != null){
				$("form#datosFolioForm #labelPeriodoFin").html('<label>' + data.fechaFin + '</label>');
			}
			if(data.fechaAtencion != null){
				$("form#datosFolioForm #labelFechaEmision").html('<label>' + data.fechaAtencion + '</label>');
			}
			if(data.regPatron != null){
				$("form#datosFolioForm #labelRegPatronal").html('<label>' + data.regPatron + '</label>');
			}
			if(data.razonSocial != null){
				$("form#datosFolioForm #labelRazonSocial").html('<label>' + data.razonSocial + '</label>');
			}
			if(data.cvePromocion != null){
				$("form#datosFolioForm #cvePromocion").val(data.cvePromocion);
			}
			if(data.cveInvitacion != null){
				$("form#datosFolioForm #cveInvitacion").val(data.cveInvitacion);
			}
			if(data.cveSolicitudCorr != null){
				$("form#datosFolioForm #cveSolicitudCorr").val(data.cveSolicitudCorr);
			}
			llenaAuditores();
			llenaCarga();
			oDgDatosFolio.dialog('open');
		}		
	}).error(function(data){
		validarSesionExpirada(data);
	}).complete(function(){
		desbloquear();
	});
}

/**
 * Funcion js manda llamar el metodo que llena la lista de auditores
 * @author Enrique Duran Jimenez
 * @since 28/05/2012
 */
function llenaAuditores(){
	
	if(oDtTableResultAuditores != undefined){
		oDtTableResultAuditores.fnDestroy();
		  
	  }
	oDtTableResultAuditores = $(tableResultAuditores).dataTable(
			{
				bJQueryUI : true,
				bFilter : false,
				bInfo : true,
				bSort : false,
				
				"bPaginate" : true,
				"bAutoWidth" : false,
				"bServerSide" : false,
				"aoColumns" : [
						{
							fnRender : function(
									oObj) {
								var retVal = '<input type="radio" value="' + oObj.aData['cveIdUsuario']
										+ '" id="radioAuditor" class="radioDeteccion" name="radioAuditor" onclick="javascript:detalle();"/> ';
								return retVal;
							},
							aTargets : [ 0 ]
						},
						{
							"sTitle" : "Nombre del Auditor",
							"mDataProp" : "nombreCompleto",
							"sClass" : "dtCenterClassColumn"
						},{
							"sTitle" : "Casos Asignados",
							"mDataProp" : "total",
							"sClass" : "dtCenterClassColumn"
						}						
						 ],
				"bProcessing" : true,
				"sAjaxSource" : 'asignaAuditor/llenaAuditores.do',
				"fnServerData" : function(sSource,aoData,fnCallback) {
					
					bloquear();
					
					var wrapper = new Object();
					wrapper.aoData = aoData;

					$.postJSON(sSource,wrapper,function(data) { 
						
						fnCallback(data);
						desbloquear();
					 }).error(function(datas){ 
							validarSesionExpirada(datas);
							desbloquear();
					});
				}
			});
	
	
	
	
}

/**
 * Funcion js manda llamar el metodo que llena la carga de auditores
 * @author Enrique Duran Jimenez
 * @since 31/05/2012
 */
function llenaCarga(){
	/*
	 * INICIA GRID CARGA
	 */
	
	if(oDtTableResultCarga != undefined){
		oDtTableResultCarga.fnDestroy();
		  
	  }
		
	oDtTableResultCarga = $(tableResultCarga).dataTable(
		{
			
			bJQueryUI : true,
			bFilter : false,
			bInfo : true,
			bSort : false,
			
			"bPaginate" : true,
			"bAutoWidth" : true,
			"bServerSide" : true,
			"aoColumns" : [
					{
						"sTitle" : "Folio",
						"mDataProp" : "folio",
						"sClass" : "dtCenterClassColumn"
					},{
						"sTitle" : "Registro Patronal",
						"mDataProp" : "regPatronal",
						"sClass" : "dtCenterClassColumn"
					},{
						"sTitle" : "Raz&oacute;n Social",
						"mDataProp" : "razonSocial",
						"sClass" : "dtCenterClassColumn"
					},{
						"sTitle" : "Periodo del",
						"mDataProp" : "fechaIni",
						"sClass" : "dtCenterClassColumn"
					},{
						"sTitle" : "Periodo al",
						"mDataProp" : "fechaFin",
						"sClass" : "dtCenterClassColumn"
					},{
						"sTitle" : "Fecha Asignaci&oacute;n",
						"mDataProp" : "fechaAsignacion",
						"sClass" : "dtCenterClassColumn"
					},{
						"sTitle" : "D\u00edas Transcurridos",
						"mDataProp" : "total",
						"sClass" : "dtCenterClassColumn"
					}							
					 ],
			"bProcessing" : true,
			"sAjaxSource" : 'asignaAuditor/muestraCarga.do',
			"fnServerData" : function(sSource,aoData,fnCallback) {
				
				var wrapper = new Object();
				wrapper.aoData = aoData;
				var idUsuario = $('form#datosFolioForm #:checked').val();
				
				if(idUsuario == undefined){
					idUsuario = null;
				}
				var forma = '{"cveAuditor":'+idUsuario+'}';
				var formJson = jQuery.parseJSON(forma);
				var oForm = formJson
				wrapper.oForm = oForm;
				bloquear();
				$.postJSON(sSource,wrapper,function(data) { 
					
					fnCallback(data);
					desbloquear();
				 }).error(function(datas){ 
						validarSesionExpirada(datas);
						desbloquear();
				});
			}
		});
}

/**	
 * Funcion js manda llamar el metodo que muestra las operaciones asigndas al usuario seleccionado
 * @author Enrique Duran Jimenez
 * @since 28/05/2012
 * @param cve_usuario
 */
function detalle(){
	var idUsuario = $('form#datosFolioForm #:checked').val();
	if(idUsuario != undefined){
		if(oDtTableResultCarga != undefined){
			llenaCarga();	
		}
	}else{
		alert('Seleccionar un auditor de la lista');
	}
}

/**
 * Funcion js para cerrar el modal
 * @author Enrique Duran Jimenez
 * @since 30/05/2012
 */
function salir(){
	oDgDatosFolio.dialog("close"); 
}

/**
 * Funcion js manda llamar el metodo que inserta en la tabla crtAuditorAsignado
 * @author Enrique Duran Jimenez
 * @since 30/05/2012
 */
function asignar(){
	var auditorId = $('form#datosFolioForm #:checked').val();
	$("form#datosFolioForm #cveAuditor").val(auditorId);
	
	var crtAuditorAsignado = $("form#datosFolioForm").serializeObject(true);
	bloquear();
	oDgDatosFolio.dialog("close");
	$.postJSON("asignaAuditor/asignar.do", crtAuditorAsignado,function(data) {
		if(data != null){
			alert('El auditor ha sido asignado correctamente');
			detalle();
			llenaAuditores();
			buscar();
			oDgDatosFolio.dialog("open");
			$('form#datosFolioForm #:checked').val(auditorId);
			$(":radio[name='radioAuditor'][value='" + auditorId + "']").attr('checked', true);
		}else{
			oDgDatosFolio.dialog("open");
			alert('El folio ya esta asignado');
		}
	}).error(function(data){ 
		validarSesionExpirada(data);
		desbloquear();	
	}).complete(function(){
		desbloquear();				
	});
	
}

/**
 * Funcion js manda llamar el dialog confirmar
 * @author Enrique Duran Jimenez
 * @since 31/05/2012
 */
function confirmar(){
	var idUsuario = $('form#datosFolioForm #:checked').val();
	if(idUsuario != undefined){
		oDgConfirmar.dialog('open');
	}else{
		alert('Seleccionar un auditor de la lista');
	}
}

function limpiar(){
	$("form#asignarAuditorForm #labelPeriodo").html('');
	$("form#asignarAuditorForm #labelOrigen").html('');
	$("form#asignarAuditorForm #labelFolio").html('');
	$("form#asignarAuditorForm #fechaIncial").val('');
	$("form#asignarAuditorForm #fechaFinal").val('');
	$("form#asignarAuditorForm #cboOrigen").val('');
	$("form#asignarAuditorForm #folioTemp").val('');
	promocionPagina();
	
}


/**
 * Funcion js valida el periodo de fechas
 * @author Enrique Duran Jimenez
 * @since 31/05/2012
 */
function jsValidaFechasLimite(){
	$("form#asignarAuditorForm #labelPeriodo").html('');
	var ini = $("form#asignarAuditorForm #fechaIncial").val();
	var fin = $("form#asignarAuditorForm #fechaFinal").val();
	var resp = true;
	if(ini != '' && fin != ''){
		var array_fechaIni = ini.split("/"); 
		var array_fechaFin = fin.split("/"); 
		
		var anioIni = parseInt(array_fechaIni[2]);
		var anioFin = parseInt(array_fechaFin[2]);		
		
		if(anioIni < anioFin){
			resp = false;
		}else if(anioIni == anioFin){
			resp = true;
		}
	}
	if(resp == false){
		$("form#asignarAuditorForm #fechaIncial").val("");
		$("form#asignarAuditorForm #fechaFinal").val("");
		$("form#asignarAuditorForm #labelPeriodo").html('<label class="etiquetaError" >El rango de fechas permitido es maximo un a\u00f1o</label>');
	}
}

/**
 * Funcion js valida el periodo de no sea menos a AL
 * @author Enrique Duran Jimenez
 * @since 31/05/2012
 */
function jsValidaFecFinal(){
	
	
	 var fecIni = $("form#asignarAuditorForm #fechaIncial").val();
	 var fecFinal = $("form#asignarAuditorForm #fechaFinal").val();
	 if(fecIni != '' && fecFinal != ''){
		 if(jsValidaFechas(fecIni,fecFinal)){
			 $("form#asignarAuditorForm #fechaFinal").val(fecFinal);
			 $("form#asignarAuditorForm #labelPeriodo").html('');
		 }else{
			 $("form#asignarAuditorForm #fechaFinal").val('');
			 $("form#asignarAuditorForm #labelPeriodo").html('<label class="etiquetaError">La fecha Al no puede ser menor a la fecha Del</label>');
		 }
	 }
}



function jsValidaFechas(fecIni, fecFin){

var array_fechaIni = fecIni.split("/"); 
var array_fechaFin = fecFin.split("/"); 

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

function jsvalidarcaracteres(e) { 
	
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla==8) return true;
    patron = /[1234567890ABCDEFGHIJKLMNÑOPQRSTUVWXYZ/]/;
    te = String.fromCharCode(tecla);
    
    return patron.test(te);
} 

function jsLimpiaForma(){
	
	$("form#datosFolioForm #cvePromocion").val("");
	$("form#datosFolioForm #cveInvitacion").val("");
	$("form#datosFolioForm #cveSolicitudCorr").val("");	
	$("form#datosFolioForm #labelFolio").html('');
	$("form#datosFolioForm #labelPeriodoIni").html('');
	$("form#datosFolioForm #labelPeriodoFin").html('');
	$("form#datosFolioForm #labelFechaEmision").html('');
	$("form#datosFolioForm #labelRegPatronal").html('');
	$("form#datosFolioForm #labelRazonSocial").html('');
	$("form#datosFolioForm #cvePromocion").val("");
	$("form#datosFolioForm #cveInvitacion").val("");
	$("form#datosFolioForm #cveSolicitudCorr").val("");
	
}