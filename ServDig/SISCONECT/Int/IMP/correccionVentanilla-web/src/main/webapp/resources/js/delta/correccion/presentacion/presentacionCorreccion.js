//Referencia para el objeto data table
/**
 * Datos unicode
 * \u00e1 -> �
 * \u00e9 -> �
 * \u00ed -> �
 * \u00f3 -> �
 * \u00fa -> �
 * \u00c1 -> �
 * \u00c9 -> �
 * \u00cd -> �
 * \u00d3 -> �
 * \u00da -> �
 * \u00f1 -> �
 * \u00d1 -> �
 * 
 * Para confirmar que es el bueno.
 */
var objDataTableSolFoliosCorreccion;
var objDataTableCopPagadas;
var oDtPatronesInscritos;
var errorDialog;
var reglasValidacionInicial;
var selloDigitalImssPresentaCorr;
var ROL_USUARIO_INTERNET=5;

$(document).ready(function() {
	//$("#fechaAutorizacionCorreccionEspontanea, #fechaAceptacionInvitacionCorreccion, #fechaEjercicioInicial, #fechaEjercicioFinal, #fechaProrroga, #fechaFirma").datepicker( { dateFormat: 'dd/mm/yy' });
	//$("#fechaAutorizacionCorreccionEspontanea").datepicker( { dateFormat: 'dd/mm/yy' });
	$("#menuPresentacionCorreccionID").hide();
	$("#presentacionMainDialog").hide();
	$("#wrapperDataTableFoliosSolicitudCorreccion").hide();
	
	
	/**
	 * Inicializacion del data table
	 */
	oDtPatronesInscritos = $("#dtPatronesInscritos").dataTable({
		bJQueryUI : true,
		bFilter : false,
		bInfo:true,
		bSort: false,
		"bPaginate": true,
		"bAutoWidth" : false,
		"bServerSide" :	true,
		"iDeferLoading": 0,
		"aoColumns" : [
		    {
				"sTitle" : "Registro Patronal",
				"mDataProp" : "registroPatronal",
				"sClass": "dtCenterClassColumn",
				"fnRender":function(o,val){
					return o.aData['registroPatronal'].substring(0,10);
				}
			}, {
				"sTitle" : "Cuota IMSS",
				"mDataProp" : "cuotaIMSS",
				"sClass":"dtCenterClassColumn"
			}, {
				"sTitle" : "Actualizaci\u00f3n IMSS",
				"mDataProp" : "actualizacionIMSS",
				"sClass": "dtCenterClassColumn"
			}, {
				"sTitle" : "Recargos IMSS",
				"mDataProp" : "recargosIMSS",
				"sClass": "dtCenterClassColumn"
			}, {
				"sTitle" : "Total IMSS",
				"mDataProp" : "totalIMSS",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "RCV",
				"mDataProp" : "cuotaRCV",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Actualizaci\u00f3n RCV",
				"mDataProp" : "actualizacionRCV",
				"sClass": "dtCenterClassColumn"
			}, {
				"sTitle" : "Recargos RCV",
				"mDataProp" : "recargosRCV",
				"sClass": "dtCenterClassColumn"
			}, {
				"sTitle" : "Total RCV",
				"mDataProp" : "totalRCV",
				"sClass": "dtCenterClassColumn"
			}
			],"bProcessing" : true,
			"sAjaxSource" : 'presentacion/paginar.do',
			"fnServerData" : function(sSource, aoData, fnCallback) {
				
				
				var wrapper = new Object();
				wrapper.aoData = aoData;
				
				var oForm = $("form#presentacionCorreccionModel").toObject({mode:'first'});
				wrapper.oForm = oForm;
			
				$.postJSON(sSource, wrapper, function(data) {
			
					fnCallback(data);
			
				 }).error(function(datas){ 
						validarSesionExpirada(datas);				 
				 });
			}
		});
	
	objDataTableSolFoliosCorreccion = $('#tableFoliosSolicitudCorreccion').dataTable({
		bJQueryUI : true,
		bFilter : false,
		bInfo:true,
		bSort: false,
		"bPaginate": true,
		"bAutoWidth" : false,
		"bServerSide" : true,
		"iDeferLoading": 0,
		"sAjaxSource" : "presentacion/buscarFoliosSolicitudCorreccion.do",
		"aoColumns" : [ {
							"sTitle": "Folio solicitud de correcci\u00f3n",
							"sClass": "dtCenterClassColumn",
							"fnRender" :function(oObj){
								var idEstatus = oObj.aData['idEstatus'];
								var retVal;
								if(idEstatus == 2){
									retVal = '<a href=javascript:buscarSolicitud("' + oObj.aData['idSolicitudCorreccion'] + '") style="color:#1122CC">' + oObj.aData['nuFolioSolCorreccion'] + '</a>';
								}else{
									retVal = '<label>' + oObj.aData['nuFolioSolCorreccion'] + '</label>';
								}
								return retVal;
							}, 
							aTargets: [0]
						 }, {
							"sTitle" : "Fecha l&iacute;mite de solicitud",
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
							"sTitle" : "Tipo de correcci\u00f3n",
							"mDataProp" : "tipoCorreccion",
							"sClass": "dtCenterClassColumn"
						 }
					],
		"fnServerData" : function(sSource, aoData, fnCallback) {
			var wrapper = new Object();
			wrapper.aoData = aoData;								
			bloquear();
			var oForm = $("#presentacionCorreccionModel").toObject({mode:'first'});
			wrapper.oForm = oForm;
			
			$.postJSON(sSource, wrapper, function(data) {										
				fnCallback(data);
				desbloquear();
			 }).error(function(datas){ 
					validarSesionExpirada(datas);
					desbloquear();
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
			 chkDocumentacionCorreccion: {
				 required:true
			 }
		 }
	});	
	
	triggerPatronInternet('','folioSoicitudCorreccionFiltro','btnBuscaSolicitudes');
});

/**
 * Esta funcion es para buscar todos los folios de solicitud de correccion bajo las siguientes premisas
 * 	1. Si se requiere una busqueda por registro patronal nos traeremos todas solicitudes de correcci�n NO RECHAZADAS, que esten ACEPTADAS y la fecha limite
 * 		no este vencida en caso de existir una prorroga se daran 10 dias m�s.
 * 	2. Por Numero de folio de solicitud de correcci�n.
 */
function buscarSolicitudesDeCorreccion(){
	$('#CopPagadasAnexoDiv').html('');
	resetDisplayStart(objDataTableSolFoliosCorreccion);
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
 * Esta funcion es cuando le den click a alg�n link con el folio de solicitud de correccion en el datatable
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
			
			// 
			
			$("form#presentacionCorreccionModel #fechaAutorizacionCorreccionEspontanea").val(data.fechaAutorizacionCorreccionEspontanea);
			$("form#presentacionCorreccionModel #tipoDeCorreccionHidden").val(data.tipoDeCorreccion);
			$("form#presentacionCorreccionModel #fechaCadenaOriginal").val(data.fechaCadenaOriginal);
			$("form#presentacionCorreccionModel #procedencia").val(data.procedencia);
			$("form#presentacionCorreccionModel #idSubDelegacion").val(data.idSubDelegacion);
			$("form#presentacionCorreccionModel #cveDelegacion").val(data.cveDelegacion);
			$("form#presentacionCorreccionModel #cveSubDelegacion").val(data.cveSubDelegacion);	
			$("form#presentacionCorreccionModel #idTipoDeSolicitud").val(data.idTipoDeSolicitud);	
			$("form#presentacionCorreccionModel #cveNroRegObra").val(data.cveNroRegObra);	
			
			//
			$('#folioSolicitudSeleccionadoLabelID').html('<b>' + data.folioCorreccion + '</b>');
			$("form#presentacionCorreccionModel #folioCorreccionInputID").val(data.folioCorreccion);
			$("form#presentacionCorreccionModel #razonSocialID").val(data.razonSocial);
			$("form#presentacionCorreccionModel #numeroRegistroPatronalInputID").val(data.numeroRegistroPatronal.substring(0,10));
			$("form#presentacionCorreccionModel #digitoVerificadorInputID").val(data.digitoVerificador);
			$("form#presentacionCorreccionModel #curpInputID").val(data.curp);
			$("form#presentacionCorreccionModel #rfcInputID").val(data.rfc);
	
			//Domicilio fiscal
			
			$("form#presentacionCorreccionModel #registroPatronalFiscalInputID").val(data.registroPatronalFiscal.substring(0,10));
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
			
			//Domicilio Centro de Trabajo        
			$("form#presentacionCorreccionModel #registroPatronalCentroTrabajoInputID").val(data.registroPatronalCentroTrabajo.substring(0,10));
			$("form#presentacionCorreccionModel #calleCentroTrabajoInputID").val(data.calleCentroTrabajo);
			$("form#presentacionCorreccionModel #numExteriorCentroTrabajoInputID").val(data.numExteriorCentroTrabajo);
			$("form#presentacionCorreccionModel #numInteriorCentroTrabajoInputID").val(data.numInteriorCentroTrabajo);
			$("form#presentacionCorreccionModel #coloniaCentroTrabajoInputID").val(data.coloniaCentroTrabajo);
			$("form#presentacionCorreccionModel #municipioCentroTrabajoInputID").val(data.municipioCentroTrabajo);
			$("form#presentacionCorreccionModel #localidadCentroTrabajoInputID").val(data.localidadCentroTrabajo);
			$("form#presentacionCorreccionModel #entidadFederativaCentroTrabajoInputID").val(data.entidadFederativaCentroTrabajo);
			$("form#presentacionCorreccionModel #codigoPostalCentroTrabajoInputID").val(data.codigoPostalCentroTrabajo);
			
			$("form#presentacionCorreccionModel #actividadCentroTrabajoInputID").val(data.actividadCentroTrabajo);
			$("form#presentacionCorreccionModel #claseCentroTrabajoInputID").val(data.claseCentroTrabajo);
			$("form#presentacionCorreccionModel #fraccionCentroTrabajoInputID").val(data.fraccionCentroTrabajo);
			$("form#presentacionCorreccionModel #primaCentroTrabajoInputID").val(data.primaCentroTrabajo);
			
			
			//Domicilio Obra        
			$("form#presentacionCorreccionModel #registroPatronalObraInputID").val(data.registroPatronalObra);
			$("form#presentacionCorreccionModel #calleObraInputID").val(data.calleObra);
			$("form#presentacionCorreccionModel #numExteriorObraInputID").val(data.numExteriorObra);
			$("form#presentacionCorreccionModel #numInteriorObraInputID").val(data.numInteriorObra);
			$("form#presentacionCorreccionModel #coloniaObraInputID").val(data.coloniaObra);
			$("form#presentacionCorreccionModel #municipioObraInputID").val(data.municipioObra);
			$("form#presentacionCorreccionModel #localidadObraInputID").val(data.localidadObra);
			$("form#presentacionCorreccionModel #entidadFederativaObraInputID").val(data.entidadFederativaObra);
			$("form#presentacionCorreccionModel #codigoPostalObraInputID").val(data.codigoPostalObra);
			
			//ID SOLICITUD
			
			
			$("form#presentacionCorreccionModel #cveSolicitudCorreccion").val(data.cveSolicitudCorreccion);
			
			
			
			
			resetDisplayStart(oDtPatronesInscritos);
			oDtPatronesInscritos.fnDraw();
			
			//Fechas de la correccion
			if(data.tipoDeCorreccion == 1){
				$('input:radio[name=tipoDeCorreccion]:nth(0)').attr('checked',true);
				$("#fechaAutorizacionCorreccionEspontanea").datepicker("enable");
				//$('#fechaAutorizacionCorreccionEspontanea').attr('readonly', false);
			}else if(data.tipoDeCorreccion == 2){
				$('input:radio[name=tipoDeCorreccion]:nth(1)').attr('checked',true);
				$("form#presentacionCorreccionModel #fechaAceptacionInvitacionCorreccion").val(data.fechaAceptacionInvitacionCorreccion);
			}
				
			$("form#presentacionCorreccionModel #fechaEjercicioInicial").val(data.fechaEjercicioInicial);
			$("form#presentacionCorreccionModel #fechaEjercicioFinal").val(data.fechaEjercicioFinal);
			$("form#presentacionCorreccionModel #fechaProrroga").val(data.fechaProrroga);
			$("form#presentacionCorreccionModel #numeroTrabajadores").val(data.numeroTrabajadores);
		
			$("form#presentacionCorreccionModel #observaciones").val(data.observaciones);
		
			
			$('#CopPagadasAnexoDiv').html(data.htmlCopPagadas);
			$("#menuPresentacionCorreccionID").show();
			$("#presentacionMainDialog").show();
			$("#wrapperDataTableFoliosSolicitudCorreccion").hide();
			$("#CopPagadasAnexoDiv").show();
			
			$("#domicilioCentroTrabajoDiv").hide()
			$("#CopPagadasAnexoDiv").hide()
			$("#registrosPatronalesInscritos").hide()
			$("#domicilioObralDiv").hide()

		}
	}).error(function(data){
		validarSesionExpirada(data);
	}).complete(function(){
		desbloquear(); 
		
		$("#domicilioCentroTrabajoDiv").hide()
		$("#CopPagadasAnexoDiv").hide()
		$("#registrosPatronalesInscritos").hide()
		$("#domicilioObralDiv").hide()

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

	var cveDelegacion = $('#cveDelegacion').val();	
	cveDelegacion =  $.trim(cveDelegacion);	
	if(cveDelegacion.length=1){
		cveDelegacion = '0' + cveDelegacion;
	}
	
	var cveSubDelegacion = $('#cveSubDelegacion').val();
	cveSubDelegacion =  $.trim(cveSubDelegacion);	
	if(cveSubDelegacion.length=1){
		cveSubDelegacion = '0' + cveSubDelegacion;
	}
	

	var idSubDelegacion = $('#idSubDelegacion').val();
	idSubDelegacion =  $.trim(idSubDelegacion);	

	var regPatronCorregir = $('#numeroRegistroPatronalInputID').val();
	regPatronCorregir =  $.trim(regPatronCorregir);
	
	var regPatronFiscal = $('#registroPatronalFiscalInputID').val();
	regPatronFiscal =  $.trim(regPatronFiscal);

	var unoVariosRp = '';

	var idTipoSolicitud = $('#idTipoDeSolicitud').val();
	idTipoSolicitud =  $.trim(idTipoSolicitud);

	var fechaAntecedente = '';  // checar
	
	var antecedente = ''; 	
	if($('#radioEspontanea').checked=true){
		antecedente='ESPONTANEA';
	}else if($('#radioInvitacion').checked=true){
		antecedente='INVITACION';
		fechaAntecedente= $('#fechaAceptacionInvitacionCorreccion').val();
	}
	
	var fecIni = $('#fechaEjercicioInicial').val();
	fecIni =  $.trim(fecIni);
	
	var fecFin = $('#fechaEjercicioFinal').val();
	fecFin =  $.trim(fecFin);
	

	var obraSatic = $('#cveNroRegObra').val();
	obraSatic =  $.trim(obraSatic);	

	var actividadRegPatInput = $('#actividadCentroTrabajoInputID').val();
	actividadRegPatInput =  $.trim(actividadRegPatInput);
	
	var claseRegPatInput = $('#claseCentroTrabajoInputID').val();
	claseRegPatInput =  $.trim(claseRegPatInput);
	var fraccionRegPatInput = $('#fraccionCentroTrabajoInputID').val();
	fraccionRegPatInput =  $.trim(fraccionRegPatInput);
	var primaRegPatInput = $('#primaCentroTrabajoInputID').val();
	primaRegPatInput =  $.trim(primaRegPatInput);

	var txRepLegalInput = $('#nombreYFirmaPatron').val();
	txRepLegalInput =  $.trim(txRepLegalInput);
	
	var trabajadoresDom = $('#numeroTrabajadores').val();
	trabajadoresDom =  $.trim(trabajadoresDom);
	
	var fechaHora = $('#fechaCadenaOriginal').val();
	fechaHora =  $.trim(fechaHora);
	
	var tipoDocumento= 'CORP-002'
	var procedencia= $('#procedencia').val();
	procedencia =  $.trim(procedencia);
	
	var resultado = cveDelegacion + cveSubDelegacion + '|' + idSubDelegacion + '|' + regPatronCorregir + '|'+regPatronFiscal +'|';
	resultado = resultado + unoVariosRp + '|' + idTipoSolicitud + '|'+antecedente +'|';	
	resultado = resultado + fecIni + '|' + fecFin + '|' + fechaAntecedente +'|';	
	resultado = resultado + obraSatic + '|' + actividadRegPatInput + '|' + claseRegPatInput +'|';	
	resultado = resultado + fraccionRegPatInput+'|'+ primaRegPatInput +'|'+ txRepLegalInput +'|'+ fechaHora +'|';
	resultado = resultado + tipoDocumento+'|'+ procedencia;
	
	return resultado;
}

function registrarFirmado(){
	bloquear();
	var contexto = $('#idContexto').val();
	var urlPDF = contexto + "/servlet/EnviaArchivoServlet";
//	 $("#firmaElectronica").val(firmaDigitalResp);
//	 $("#cadenaOriginal").val(getCadenaOriginal());
	if($('#radioEspontanea').is(':checked')) { 
		$('#fechaAutorizacionCorreccionEspontanea').rules('add',{required:true});
		//Correccion Espontanea 1
		$("form#presentacionCorreccionModel #tipoDeCorreccionHidden").val('1');
	}else if($('#radioInvitacion').is(':checked')){
		$("form#presentacionCorreccionModel #tipoDeCorreccionHidden").val('2');
	}
	
	if(reglasValidacionInicial.form()){
		//presentarCorr();
		
		if((recuperarRolUsuario()!=ROL_USUARIO_INTERNET)){
			selloDigitalImssPresentaCorr=recuperaSelloImss();
			if(selloDigitalImssPresentaCorr==null){
				alert("No se puede recuperar el sello,favor de reintentar");
				return;
			}
			recuperaMensajeTramitePresenta(TRAMITE_PRESENTACION);
			objMensajeFirma.cveMensaje=null;
			
			presentarCorr(selloDigitalImssPresentaCorr.tramite,selloDigitalImssPresentaCorr.sello,generaCadenaPrincipalPresentacion());							
		}else{
			if(confirm(recuperaMensajeTramitePresenta(TRAMITE_PRESENTACION))){
				var param=generaParametrosFirmaPresentacion();
				if(param!=null){
					firmarDocumentoSISCONET('contenedorFirmaPresentacion',callBackFirmaPresenta,param);
				}else{
					alert("No se puede generar el documento,favor de reintentar");
				}
					
			}
		}
		
		
		
		
	}
	desbloquear();
}


function presentarCorr(sello, selloIMSS, cadenaOriginal,urlAcuse){
	
	$('#CopPagadasAnexoDiv').html('');
	var contexto = $('#idContexto').val();
	var urlPDF = contexto + "/servlet/EnviaArchivoServlet";
	var correccion = $("form#presentacionCorreccionModel").toObject({mode:'first'});
	correccion["firmaElectronica"] = sello;
	correccion["selloIMSS"] = selloIMSS;
	correccion["cadenaOriginal"] = cadenaOriginal;
	correccion["mensaje"]=objMensajeFirma;
	
	if(urlAcuse!=undefined){
		correccion["urlAcuseFirma"]=urlAcuse;
	}
	
	
	correccion.observaciones=$("#observacionesPresentacion").val();
	$.postJSON_Sync("presentacion/presentarCorreccion.do", correccion, function(data) {
		
		if(data.folioDesdeInternet==true){
			desbloquear();
			alert("El folio no puede ser presentado debido a que fue registrado desde internet");
		}else{
			desbloquear();
			errorDialog.html('<font size="2px">El folio de correcci&oacute;n ha sido presentado con &eacute;xito</font>');
			errorDialog.dialog("open");
			limpiarCampos();
			window.open(urlPDF,"PresentacionCorrecion","menubar=1,resizable=1,width=500,height=500");
			
		}
		startEncuestaHC(2000,"IMSS-02-073");
	}).error(function(data){ 
		alert("error" + data);
	}).complete(function(){
		desbloquear(); 
		$("#menuPresentacionCorreccionID").hide();
		$("#presentacionMainDialog").hide();
		$("#wrapperDataTableFoliosSolicitudCorreccion").hide();
		
		
		$("#domicilioCentroTrabajoDiv").hide();
		$("#CopPagadasAnexoDiv").hide();
		$("#registrosPatronalesInscritos").hide();
		$("#domicilioObralDiv").hide();
		
		
	});

}



///////////////////////  Ejecucion de la firma digital ////////////////////



var resultadoPresentaFirma;
function callBackFirmaPresenta(){	
	resultadoPresentaFirma=firma.getDatosSalida();
	if(resultadoPresentaFirma!=null){
		if(resultadoPresentaFirma.Resultado==0){	
			window.open(resultadoPresentaFirma.acuse,'', "scrollbars=1,height=500,width=700");
			var sello=recuperaSelloImss(resultadoPresentaFirma.folio);
			if(sello==null){
				alert("No se puede recuperar el sello, favor de reintentar");
				return;
			}
			presentarCorr(resultadoPresentaFirma.folio,sello.sello, generaCadenaPrincipalPresentacion(),resultadoPresentaFirma.acuse);
		}else{
			errorDialog.html('<font size="2px">No se ha podido firmar el documento,favor de reintentar</font>');
			errorDialog.dialog("open");
		}		
	}

}
//firmarDocumentoPresentacion
function generaParametrosFirmaPresentacion(){
//	selloDigitalImssPresentaCorr=recuperaSelloImss();
//	if(selloDigitalImssPresentaCorr=="-1"){
//		return null;
//	}
	
	var parmVals = {			
			acuse:"AcuseV1.0",
			aplicacion:"portalimssdigital",
			operacion:"firmaCMS",
			//origen:"http://dnassd.imss.gob.mx",
			origen:"http://correcciondigital.imss.gob.mx",
			salida:"resultado,descripcion,folio,acuse,qr,rfc,curp,serie_cert,archivos,firmas,contenedores,vigencias",
			tipo_archivos:"",
			forma_firma_archivos:"0",
			firma_archivo:true,
			//val_rfc:true,
			max_archivos:5,
			min_archivos:1,
			//curp:"DUSL821218HDFRLC09",
			curp:$("#curpInputID").val().trim(),
			rfc:$("#rfcInputID").val().trim(),
			//rfc:"DUSL821218LN8",			
			nombreCompleto:$("#razonSocialID").val(),
			registroPatronal:$("#numeroRegistroPatronalInputID").val()+recuperaDigitoVerificador($("#numeroRegistroPatronalInputID").val()),
			idTipoSolicitud:"6",								
			cad_original:quitaAcentos(generaCadenaPrincipalPresentacion().substring(0,generaCadenaPrincipalPresentacion().length-2)+"||"),		
			descripcionTipoSolicitud:"CORP-02 PRESENTACI�N DE LA CORRECCI�N",
			fechaElectronica:getFechaServidor()		
			};	
		return parmVals;
	}

function generaCadenaPrincipalPresentacion(){
	var campos = new Object();
	var cadenaPrincipal="||";

	campos['folioCorreccionInputID'] = 'Folio de Correccion';
	campos['razonSocialID'] = 'Nombre, denomicacion o razon social: ';
	campos['numeroRegistroPatronalInputID'] = 'Numero de Registro Patronal: ';
	campos['digitoVerificadorInputID'] = 'Digito Verificador';
	campos['curpInputID'] = 'Clave unica de registro de poblacion';
	campos['rfcInputID'] = 'Registro Federal de Contribuyentes';
	campos['registroPatronalFiscalInputID'] = 'Registro Patronal';
	campos['calleInputID'] = 'Calle';
	campos['numExteriorInputID'] = 'Numero Exterior';
	campos['numInteriorInputID'] = 'Numero interior';
	campos['coloniaInputID'] = 'Colonia';
	campos['municipioInputID'] = 'Municipio / Delegacion';
	campos['localidadInputID'] = 'Localidad';
	campos['entidadFederativaInputID'] = 'Entidad Federativa';
	campos['codigoPostalInputID'] = 'CodigoPostal';
	campos['telefonoInputID'] = 'Telefono';
	campos['emailInputID'] = 'Correo Electronico';
	campos['registroPatronalCentroTrabajoInputID'] = 'Registro Patronal';
	campos['calleCentroTrabajoInputID'] = 'Calle Centro Trabajo';
	campos['numExteriorCentroTrabajoInputID'] = 'Numero Exterior Centro Trabajo';
	campos['numInteriorCentroTrabajoInputID'] = 'Numero Interior Centro Trabajo';
	campos['coloniaCentroTrabajoInputID'] = 'Colonia Centro Trabajo';
	campos['municipioCentroTrabajoInputID'] = 'Municipio / Delegacion Centro Trabajo';
	campos['localidadCentroTrabajoInputID'] = 'Localidad Centro Trabajo';
	campos['entidadFederativaCentroTrabajoInputID'] = 'Entidad Federativa Centro Trabajo';
	campos['codigoPostalCentroTrabajoInputID'] = 'Codigo Postal Centro Trabajo';
	campos['actividadCentroTrabajoInputID'] = 'Actividad Centro Trabao';
	campos['claseCentroTrabajoInputID'] = 'Clase Centro Trabajo';
	campos['fraccionCentroTrabajoInputID'] = 'Fraccion Centro Trabajo';
	campos['primaCentroTrabajoInputID'] = 'Prima Centro Trabajo';
	campos['calleObraInputID'] = 'Calle Obra';
	campos['numExteriorObraInputID'] = 'Numero Exterior Obra';
	campos['numInteriorObraInputID'] = 'Numero Interior Obra';
	campos['coloniaObraInputID'] = 'Colonia Obra';
	campos['municipioObraInputID'] = 'Municipio/Delegacion Obra';
	campos['localidadObraInputID'] = 'Localidad Obra';
	campos['entidadFederativaObraInputID'] = 'Entidad Federativa Obra';
	campos['codigoPostalObraInputID'] = 'Codigo Postal Obra';
	campos['fechaAutorizacionCorreccionEspontanea'] = 'Fecha Autorizacion';
	campos['fechaAceptacionInvitacionCorreccion'] = 'Correccion por Invitacion';
	campos['fechaEjercicioInicial'] = 'Ejercicio / periodo regularizado';
	campos['fechaEjercicioFinal'] = 'Ejercicio / periodo regularizado Al';
	campos['fechaProrroga'] = 'Prorroga';
	campos['numeroTrabajadores'] = 'Numero de trabajadores regularizados';
	campos['nombreYFirmaPatron'] = 'Nombre y firma del patron o representante legal';
	campos['lugar'] = 'Lugar';
	campos['fechaFirma'] = 'FechaFirma';

	var total=0;
	for (var k in campos) {
		total++;
	}
	
	var s=0;
	for (var k in campos) {		
	    if (campos.hasOwnProperty(k)) {
	    	 	cadenaPrincipal=cadenaPrincipal+campos[k]+"|"+$("#"+k).val();	
	    	 	if(s<total-1){
	    	 		cadenaPrincipal+="|";
	    	 	 }
	    		s++;
	      }	   
	}	
	
	cadenaPrincipal+="||";
	return cadenaPrincipal;	
	
}


function recuperaMensajeTramitePresenta(cveTramite){

	var msj;
	var obje='{"cveTramite":"'+cveTramite+'"}';
	var valor = jQuery.parseJSON(obje);
	$.postJSON_Sync("../solicitud/correcion/obtenerMesajeTramite.do", valor, function(data) {
		msj=data.desMensajes;
		objMensajeFirma=data;
	}).error(function(data){
		desbloquear();		
		validarSesionExpirada(data);
		alert("error" + data);
	}).complete(function(data){		
				
	});	
	return msj;
}


function recuperaSelloImss(idTramite){
	
	var datos='{"selloIMSS":""}';
	var crtSolicitudCorr = jQuery.parseJSON(datos);
	crtSolicitudCorr.selloIMSS=generaCadenaPrincipalPresentacion();
	if(idTramite!=undefined){
		crtSolicitudCorr.idTramite=idTramite;
	}
	
	var sello;
	$.postJSON_Sync("../solicitud/correcion/recuperaSelloDigital.do", crtSolicitudCorr, function(data) {
		sello=data.respuestaObjetoFirmadoSimple;
				
	});
	 
	return sello;
}


function recuperarRolUsuario(){
	
	var cveRol;
	$.postJSON_Sync("../solicitud/correcion/consultarRolUsuario.do", null, function(data) {
		cveRol=data.cveRol;
		desbloquear();
		
	});
	return cveRol;
}



function recuperaDigitoVerificador(rp){
	
	var digitoVerificador;
	
	var valor='{"registroPatronal":"'+rp+'"}';
	var crtSolicitudCorr = jQuery.parseJSON(valor);
	$.postJSON_Sync("../solicitud/correcion/generaDigitoVerificador.do", crtSolicitudCorr, function(data) {
		digitoVerificador=data;
		desbloquear();		
	});
	return digitoVerificador;
}


