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
	
	$("#btnAsignar").click(function(event) {
		event.preventDefault();
		var idUsuario = $('form#reasignarAuditorForm #:checked').val();
		if(idUsuario != undefined){
			oDgConfirmar.dialog('open');
		}else{
			alert('Seleccionar un auditor de la lista');
		}});
	
});


/**
 * Funcion js manda llamar el metodo que llena la lista de auditores
 * @author Enrique Duran Jimenez
 * @since 04/06/2012
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
//								var retVal = '<input type="radio" value="' + oObj.aData['cveIdUsuario']
								var retVal = '<input type="radio" value="' + oObj.aData['desUsrCurp']
										+ '" id="radioTable" class="radioDeteccion" name="radio" onclick="javascript:detalle();"/> ';
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
				"sAjaxSource" : 'reAsignaAuditor/llenaAuditores.do',
				"fnServerData" : function(sSource,aoData,fnCallback) {
					
					bloquear();
					
					var wrapper = new Object();
					wrapper.aoData = aoData;
					var cveAuditorAsignado = $("form#reasignarAuditorForm #cveUsuarioAsignado").val();
					if(cveAuditorAsignado == undefined){
						cveAuditorAsignado = null;
					}
					var forma = '{"desUsrCurp":"'+cveAuditorAsignado+'"}';
					
					var formJson = jQuery.parseJSON(forma);
					var oForm = formJson
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
 * Funcion js manda llamar el metodo que llena la carga de auditores
 * @author Enrique Duran Jimenez
 * @since 04/06/2012
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
			"sAjaxSource" : 'reAsignaAuditor/muestraCarga.do',
			"fnServerData" : function(sSource,aoData,fnCallback) {
				
				var wrapper = new Object();
				wrapper.aoData = aoData;
				var idUsuario = $('form#reasignarAuditorForm #:checked').val();
				
				if(idUsuario == undefined){
					idUsuario = null;
				}
//				var forma = '{"cveAuditor":'+idUsuario+'}';
				var forma = '{"cveAuditorUsuarioAsignado":"'+idUsuario+'"}';
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
 * Funcion js manda llamar el metodo que inserta en la tabla crtAuditorAsignado
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 */
function asignar(){
	
//	$("form#reasignarAuditorForm #cveAuditor").val($('form#reasignarAuditorForm #:checked').val());
	$("form#reasignarAuditorForm #cveAuditorUsuarioAsignado").val($('form#reasignarAuditorForm #:checked').val());
	
	var crtAuditorAsignado = $("form#reasignarAuditorForm").serializeObject(true);
	bloquear();
	$.postJSON("reAsignaAuditor/asignar.do", crtAuditorAsignado,function(data) {
		alert('El auditor ha sido asignado correctamente');
		//refrescaFolio(data.folio);
		//detalle();
		limpiar();
	}).error(function(data){ 
		validarSesionExpirada(data);
		desbloquear();	
	}).complete(function(){
		desbloquear();				
	});
	
}


function limpiar(){
	
	$("form#reasignarAuditorForm #folioTemp").val("");
	$("form#reasignarAuditorForm #cvePromocion").val("");
	$("form#reasignarAuditorForm #cveInvitacion").val("");
	$("form#reasignarAuditorForm #cveSolicitudCorr").val("");
	$("form#reasignarAuditorForm #cveAuditor").val("");
	$("form#reasignarAuditorForm #cveUsuarioAsignado").val("");
	$("form#reasignarAuditorForm #labelFolioConsulta").html('');
	$("form#reasignarAuditorForm #labelFolio").html('');
	$("form#reasignarAuditorForm #labelPeriodoIni").html('');
	$("form#reasignarAuditorForm #labelPeriodoFin").html('');
	$("form#reasignarAuditorForm #labelFechaEmision").html('');
	$("form#reasignarAuditorForm #labelRegPatronal").html('');
	$("form#reasignarAuditorForm #labelRazonSocial").html('');
	$("form#reasignarAuditorForm #labelAuditor").html('');
	$("#contenido").hide();
	
}

/**
 * Funcion js manda llamar el dialog confirmar
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 */

function buscar(){
	
	if($("form#reasignarAuditorForm #folioTemp").val() != '' && $("form#reasignarAuditorForm #folioTemp").val().length > 16){
		$("form#reasignarAuditorForm #labelFolioConsulta").html('');
		var folioTemp = $("form#reasignarAuditorForm #folioTemp").val();
		var bandera = jsValidaFolio();
		if(bandera != 'vacio' && bandera != undefined){
			var crtPromocion = '{"folioTemp":'+ '"' + folioTemp +'","bandera":'+ '"' + bandera +'"}';
			var variableJson = jQuery.parseJSON(crtPromocion);
			bloquear();
			$.postJSON("reAsignaAuditor/buscar.do", variableJson,function(data) {
				
				if(data != null){
					
					$("form#reasignarAuditorForm #folioTemp").val("");
					$("form#reasignarAuditorForm #cvePromocion").val("");
					$("form#reasignarAuditorForm #cveInvitacion").val("");
					$("form#reasignarAuditorForm #cveSolicitudCorr").val("");
					$("form#reasignarAuditorForm #cveAuditor").val("");
					$("form#reasignarAuditorForm #cveUsuarioAsignado").val("");
					$("form#reasignarAuditorForm #labelFolioConsulta").html('');
					$("form#reasignarAuditorForm #labelFolio").html('');
					$("form#reasignarAuditorForm #labelPeriodoIni").html('');
					$("form#reasignarAuditorForm #labelPeriodoFin").html('');
					$("form#reasignarAuditorForm #labelFechaEmision").html('');
					$("form#reasignarAuditorForm #labelRegPatronal").html('');
					$("form#reasignarAuditorForm #labelRazonSocial").html('');
					$("form#reasignarAuditorForm #labelAuditor").html('');
					
					
					if(data.cvePromocion != null){
						$("form#reasignarAuditorForm #cvePromocion").val(data.cvePromocion);
					}
					if(data.cveInvitacion != null){
						$("form#reasignarAuditorForm #cveInvitacion").val(data.cveInvitacion);
					}
					if(data.cveSolicitudCorr != null){
						$("form#reasignarAuditorForm #cveSolicitudCorr").val(data.cveSolicitudCorr);
					}
					$("form#reasignarAuditorForm #cveUsuarioAsignado").val(data.cveAuditorAsignado);
					
					if(data.folioTemp != null){
						$("form#reasignarAuditorForm #labelFolio").html('<label>' + data.folioTemp + '</label>');
					}
					if(data.fechaInicio != null){
						$("form#reasignarAuditorForm #labelPeriodoIni").html('<label>' + data.fechaInicio + '</label>');
					}
					if(data.fechaFin != null){
						$("form#reasignarAuditorForm #labelPeriodoFin").html('<label>' + data.fechaFin + '</label>');
					}
					if(data.fechaAtencion != null){
						$("form#reasignarAuditorForm #labelFechaEmision").html('<label>' + data.fechaAtencion + '</label>');
					}
					if(data.regPatron != null){
						$("form#reasignarAuditorForm #labelRegPatronal").html('<label>' + data.regPatron + '</label>');
					}
					if(data.razonSocial != null){
						$("form#reasignarAuditorForm #labelRazonSocial").html('<label>' + data.razonSocial + '</label>');
					}
					if(data.auditor != null){
						$("form#reasignarAuditorForm #labelAuditor").html('<label>' + data.auditor + '</label>');
					}
					llenaAuditores();
					llenaCarga();
					$("#contenido").show();
				}else{
					limpiar();
					$("form#reasignarAuditorForm #labelFolioConsulta").html('<label class="etiquetaError">El folio no esta asignado</label>');
				}		
			}).error(function(data){
				validarSesionExpirada(data);
			}).complete(function(){
				desbloquear();
			});
		}else{
			limpiar();
			$("form#reasignarAuditorForm #labelFolioConsulta").html('<label class="etiquetaError">Capturar un folio valido</label>');
		}
	}else{
		limpiar();
		$("form#reasignarAuditorForm #labelFolioConsulta").html('<label class="etiquetaError">Capturar un folio valido</label>');
	}
}


/**
 * Funcion que valida el tipo de folio capturado para mostrar los datos del folio
 * Enrique Duran Jimenez
 * 05/06/2012
 */
function jsValidaFolio(){
	
	var folio = $("form#reasignarAuditorForm #folioTemp").val();
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
				regresa = 'solicitud';
			}	
			if(tipo == 'CCI'){
				regresa = 'solicitud';
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
 * Funcion js manda llamar el metodo que muestra las operaciones asigndas al usuario seleccionado
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * @param cve_usuario
 */
function detalle(){
	var idUsuario = $('form#reasignarAuditorForm #:checked').val();
	if(idUsuario != undefined){
		if(oDtTableResultCarga != undefined){
			llenaCarga();	
		}
	}else{
		alert('Seleccionar un auditor de la lista');
	}
}

/**
 * Funcion js manda llamar el dialog confirmar
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 */

function refrescaFolio(folio){
	
		
		$("form#reasignarAuditorForm #folioTemp").val(folio);
		var bandera = jsValidaFolio();
		$("form#reasignarAuditorForm #folioTemp").val("");
		var crtPromocion = '{"folioTemp":'+ '"' + folio +'","bandera":'+ '"' + bandera +'"}';
		var variableJson = jQuery.parseJSON(crtPromocion);
		bloquear();
		$.postJSON("reAsignaAuditor/buscar.do", variableJson,function(data) {
			if(data != null){
				
				$("form#reasignarAuditorForm #folioTemp").val("");
				$("form#reasignarAuditorForm #cvePromocion").val("");
				$("form#reasignarAuditorForm #cveInvitacion").val("");
				$("form#reasignarAuditorForm #cveSolicitudCorr").val("");
				$("form#reasignarAuditorForm #cveAuditor").val("");
				$("form#reasignarAuditorForm #cveUsuarioAsignado").val("");
				$("form#reasignarAuditorForm #labelFolioConsulta").html('');
				$("form#reasignarAuditorForm #labelFolio").html('');
				$("form#reasignarAuditorForm #labelPeriodoIni").html('');
				$("form#reasignarAuditorForm #labelPeriodoFin").html('');
				$("form#reasignarAuditorForm #labelFechaEmision").html('');
				$("form#reasignarAuditorForm #labelRegPatronal").html('');
				$("form#reasignarAuditorForm #labelRazonSocial").html('');
				$("form#reasignarAuditorForm #labelAuditor").html('');
				
				
				if(data.cvePromocion != null){
					$("form#reasignarAuditorForm #cvePromocion").val(data.cvePromocion);
				}
				if(data.cveInvitacion != null){
					$("form#reasignarAuditorForm #cveInvitacion").val(data.cveInvitacion);
				}
				if(data.cveSolicitudCorr != null){
					$("form#reasignarAuditorForm #cveSolicitudCorr").val(data.cveSolicitudCorr);
				}
				$("form#reasignarAuditorForm #cveUsuarioAsignado").val(data.cveAuditorAsignado);				
				if(data.folioTemp != null){
					$("form#reasignarAuditorForm #labelFolio").html('<label>' + data.folioTemp + '</label>');
				}
				if(data.fechaInicio != null){
					$("form#reasignarAuditorForm #labelPeriodoIni").html('<label>' + data.fechaInicio + '</label>');
				}
				if(data.fechaFin != null){
					$("form#reasignarAuditorForm #labelPeriodoFin").html('<label>' + data.fechaFin + '</label>');
				}
				if(data.fechaAtencion != null){
					$("form#reasignarAuditorForm #labelFechaEmision").html('<label>' + data.fechaAtencion + '</label>');
				}
				if(data.regPatron != null){
					$("form#reasignarAuditorForm #labelRegPatronal").html('<label>' + data.regPatron + '</label>');
				}
				if(data.razonSocial != null){
					$("form#reasignarAuditorForm #labelRazonSocial").html('<label>' + data.razonSocial + '</label>');
				}
				if(data.auditor != null){
					$("form#reasignarAuditorForm #labelAuditor").html('<label>' + data.auditor + '</label>');
				}
				alert($("form#reasignarAuditorForm #cveUsuarioAsignado").val());
				llenaAuditores();
			}	
		}).error(function(data){
			validarSesionExpirada(data);
		}).complete(function(){
			desbloquear();
		});
	
}

function jsvalidarcaracteres(e) { 
	e = e.toUpperCase();
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla==8) return true;
    patron = /[1234567890ABCDEFGHIJKLMN�OPQRSTUVWXYZ/]/;
    te = String.fromCharCode(tecla);
    
    return patron.test(te);
} 