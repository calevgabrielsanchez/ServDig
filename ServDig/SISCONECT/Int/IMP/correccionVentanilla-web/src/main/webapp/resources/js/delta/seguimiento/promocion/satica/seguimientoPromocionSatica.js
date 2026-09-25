/**
 * indica el tab seleccionado
 */
activeTab = '#saticaSeguimientoTab';

/**
 * Indica la forma seleccionada
 */
FORMA_ACTUAL ='saticaMainForm';

/**Variables indicativas de las secciones
 *disponibles. 
 */
var SEGUIMIENTO=1;
var CANCELACION=2;
var DERIVA_SUBDELEGACION=3;
var DERIVA_FISCALIZACION=4;
var REGULARIZAR_OBRA=5;

var JEFE_OF_CORRECCION = 4;
var JEFE_OF_CORR_Y_DIC = 6;
var JEFE_DEP_AUD_PAT = 7;

//jefe de oficina de corrección y dictamen , jefe de departamento de auditoría a patrones y Jefe de oficina de corrección 

var idSaticaMain            = "#dgPromocionSeguimientoSaticaMain";
var oDgSaticaMain;

var idSaticaValidaDomicilios       = "#dgDialogSaticaValidaDom";
var oDgSaticaValidaDomicilios;
var obraValida = false;

var jsFechaNotificacionOfSatica = "form#saticASeguimientoTabForm #fechaNotificacionSaticaSeg";
var jsSpanFNotificacionSatica = "form#saticASeguimientoTabForm #spnFechaNotificacionSaticaSeg";
var jsFechaAtencionOficioSatica = "form#saticASeguimientoTabForm #fechaAtnOficioSaticaSeg";
var jsSpanFAtencionOficioSatica = "form#saticASeguimientoTabForm #spnFechaAtencionSaticaSeg";

var jsNumRegistroObraSatica = "form#saticASeguimientoTabForm #numRegistroObraSaticaSeg";
var jsCheckRegularizaObra = "form#saticASeguimientoTabForm #regularizaObraSaticaSeg";
var jsBotonValidaObraSatica = "form#saticASeguimientoTabForm #btnValidaRegistroObraSaticaSeg";
var jsClaveFkPatronSaticaMain= "form#saticaMainForm #cveFkPatronSatica";
var jsClaveFkPatronSaticaSeg = "form#saticASeguimientoTabForm #cveFkPatronSaticaSeg";
var jsRegistroPatronalSaticaMain= "form#saticaMainForm #regPatronSatica";
var jsRegistroPatronalTabPagos= "form#regularizarObraGenericoTABForm #registroPatronalPagosDt";
var jsBotonValidaRegPatronSaticaMain= "form#saticaMainForm #btnValidaRegPatronSaticaMain";
var jsRazonSocialSaticaMain = "form#saticaMainForm #razonSocialSatica";
var jsObservacionesSaticaSeg= "form#saticASeguimientoTabForm #observacionesSaticaSeg";
var jsfechaInicioPCSaticaSeg= "form#saticASeguimientoTabForm #fechaInicioPCSaticaSeg";
var jsfechaFinPCSaticaSeg= "form#saticASeguimientoTabForm #fechaFinPCSaticaSeg";

// etiquetas para mensajes (label)
var jsLabelNumRegObraSaticaSeg= "form#saticASeguimientoTabForm #labelNumRegistroObraSaticaSeg";
var jsRegPatronSaticaLabel = "form#saticaMainForm #regPatronSaticaLabel"; 
var jsRegistroPatronalSaticaMainDet= "form#saticaMainForm #registroPatronalDeteccion";
var jsRazonSocialSaticaMainDet= "form#saticaMainForm #razonSocialDeteccion";

var jsFechaNotifSaticaCapturable = true;
var jsFechaAtencionSaticaCapturable = true;
var jsNumRegistroObraSaticaCapturable = true;
var jsRPSaticaMainValido=false;

// Variables con nombres de Tabs Satica
var jsTabSaticaSeg = "saticaSeguimientoTab";
var jsTabCancelarGen = "cancelacionGenericoTab_satica";
var jsTabDerivarSubDelGen = "derivarSubdelegacionGenericoTab_satica";
var jsTabDerivarFiscalizacionGen = "derivarFiscalizacionTAB_satica";
var jsTabRegularizarObraGen = "regularizarObraGenericoTAB_satica";

// variables utilizadas para validacion de fechas en los tabs genericos
var jsFechaEmisionSaticaMain="form#saticaMainForm #fechaOficioPromocionSatica";
// fechas cancelacion
var jsFechaEmisionCancelacion="form#cancelacionGenericoTabForm #fechaEmisionOficioGenerico";
var jsFechaNotifCancelacion="form#cancelacionGenericoTabForm #fechaNotificacionOficioGenerico";
// fechas subdelegacion
var jsFechaNotifDerivarSubDel="form#derivarSubdelegacionGenericoTabForm #fechaNotificacionOficioGenerico";
var rolUsrSaticaSeg="";
var ROL_JEFE = false;

var jsFechaNotifFiscalGen="form#derivarFiscalizacionTABForm #fechaNotificacionOficioFiscalizacion";
var jsEstatusPromocionSatica="";
var jsEstatusPromocionSaticaSeg="form#saticASeguimientoTabForm #estatusPromocionSaticaSeg";

/**
 * Controla el valor del hidden el cual indica
 * en que sección se encuentra el usuario.
 * Esta variable es utilizada por JAVA.
 */
function setToFormSeccionActual(){
	
	}

/**
 * Funcion que asigna el rol de jefe
 * para controlar los permisos de las pestañas.
 * @author Gerardo Salazar Vega
 * @version 1.0.0 
 */
function asignaRolSatica (rolUsuario){
	$("form#saticaMainForm #rolUsuarioSaticaSeg").val(rolUsuario);
	rolUsrSaticaSeg=$("form#saticaMainForm #rolUsuarioSaticaSeg").val();	
	if (rolUsrSaticaSeg==JEFE_OF_CORRECCION || rolUsrSaticaSeg==JEFE_OF_CORR_Y_DIC || rolUsrSaticaSeg==JEFE_DEP_AUD_PAT){		
		ROL_JEFE = true;
	} else {
		ROL_JEFE = false;
	} 
}	

//antes : muestraDialogoSaticAMain
function inicializaDialogSaticA(){
	try {
		
	var idPromocion = $('#:checked').val();
	if(idPromocion != null) {
				var sPromocion = '{"cvePromocion":'+idPromocion+'}';
				//alert ("promocion:"+sPromocion);
				var promocion = jQuery.parseJSON(sPromocion);
				$.postJSON_Sync(jsContextoPromocion+"seguimiento/satica/mostrarSatica.do", promocion, function(data) {
					limpiarFormulario("#saticaMainForm");
					$("form#saticaMainForm label").prop("value","");					
					$("form#saticaMainForm #cvePromocionSatica").prop("value", idPromocion);
					$("form#saticaMainForm #sCriterioSelSaticA").html(data.descripcionCriterioseleccion);					
					$("form#saticaMainForm #folioPromocionSatica").html(data.folioPromocion);
					$(jsFechaEmisionSaticaMain).html(data.fechaOficioPromocion);
					$("form#saticaMainForm #numeroOficioPromocionSatica").html(data.numeroOficioPromocion);
					$("form#saticaMainForm #calleSatica").html(data.calle);
					$("form#saticaMainForm #coloniaSatica").html(data.colonia);
					$("form#saticaMainForm #numExtSatica").html(data.numExterior);
					$("form#saticaMainForm #numIntSatica").html(data.numInterior);
					$("form#saticaMainForm #codigoPostalSatica").html(data.codigoPostal);
					$("form#saticaMainForm #estadoSatica").html(data.estado);
					$("form#saticaMainForm #municipioSatica").html(data.municipio);
					$("form#saticaMainForm #regPatronSaticaLabel").text("");
					// se agrega registro patronal de deteccion
					$(jsRegistroPatronalSaticaMainDet).html(data.registroPatronalDeteccion);
					$(jsRazonSocialSaticaMainDet).html(data.razonSocialDeteccion);					
					$(jsRegistroPatronalSaticaMain).val(data.registroPatronal);

				 
					$(jsRazonSocialSaticaMain).html(data.razonSocial);
					$(jsClaveFkPatronSaticaMain).prop("value", data.cveFkPatron);					
					$("form#saticaMainForm #estatusPromocionSatica").prop("value", data.estatusPromocion);
					$(jsEstatusPromocionSaticaSeg).prop("value", data.estatusPromocion);					
					jsEstatusPromocionSatica = data.estatusPromocion;
					asignaRolSatica(data.rolUsuario);
					limpiarFormulario("#saticASeguimientoTabForm");
					$("form#saticASeguimientoTabForm label").html("");
					// Datos del seguimiento
					verificaCamposCapturablesSaticaSeg(data);
					if(data.saticaSeguimientoTabVO!=null && data.saticaSeguimientoTabVO.observaciones!=null){
						$(jsObservacionesSaticaSeg).prop("value", data.saticaSeguimientoTabVO.observaciones);
					}else{
						$(jsObservacionesSaticaSeg).prop("value", "");
					}
					//se envia Registro patronal a Regularizar obra
					$(jsRegistroPatronalTabPagos).prop("value", data.registroPatronal);
					
					//fecha notificacion para pantalla de pagos
					$("form#regularizarObraGenericoTABForm #fechaNotificacionHdn").prop("value", data.saticaSeguimientoTabVO.fechaNotificacion); 

					
				}).error(function(data){ 
					validarSesionExpirada(data);
				}).complete(function(){
					//Instrucciones para el 'complete'
					$(idSaticaMain).dialog("destroy");
					//$('.ui-dialog').empty().remove();
					//$(".ui-dialog:has('#dgPromocionSeguimientoSaticaMain')").remove();
					oDgSaticaMain = $(idSaticaMain).dialog({
						autoOpen: false,
						modal:true,
						resizable:false,
						width: 1100,
						closeOnEscape: false,
						beforeClose :function(event,ui){
							// elimina el domicilio de la sesion
							$.postJSON_Sync(jsContextoPromocion+"seguimiento/generico/removerDomicilioSession.do", null, function(data) {					
							}).error(function(data){ 					
								validarSesionExpirada(data);
							}).complete(function(){
								//Instrucciones para el complete													
							});							
							oDgConsultaRegistros.fnDraw();
						},						
						buttons: {			
							"Regresar": function() { 
								$(this).dialog("close");
								limpiarForm = true;				
							} 
						}
					});
					
					activeTab = '#saticaSeguimientoTab';
					changeTab(jsTabSaticaSeg);
					oDgSaticaMain.dialog('open');
					deshabilitarTabsSaticaSeg();
					habilitarElementosSatica();					
					
					//alert ("estatus="+$("form#saticaMainForm #estatusPromocionSatica").val());
					// habilita la seccion de datos del patron en derivar a otra subdelegacion
					initTabSeguimientoSaticA();
					initTabCancelacionGenerico();
					//initTabAvisoDictamenGenerico();
					initTabDerivarFiscalizacion();
					initTabRegularizarObra();
					initTabDerivarSubdelegacionGenerico();
					$('#datosPatronObraDerSubGT').show('fast');
					$("form#derivarSubdelegacionGenericoTabForm #tieneDatosPatronObraDerSubGT").val("true");
					
					// actualizacion de variables para tabs genericos
					var idPromocionG = $("form#saticaMainForm #cvePromocionSatica").val();
					$("form#saticASeguimientoTabForm #cvePromocionSaticaSeg").val(idPromocionG);
					$("form#autAviDictamenGenericoTabForm #cvePromocion").val(idPromocionG);
					$("form#cancelacionGenericoTabForm #cvePromocion").val(idPromocionG);
					$(jsFechaEmisionCancelacion).val($(jsFechaEmisionSaticaMain).html());
					$("form#regularizarObraGenericoTABForm #cvePromocion").val(idPromocionG);
					$("form#derivarSubdelegacionGenericoTabForm #cvePromocion").val(idPromocionG);
					//console.log("init Satica subdel="+$("form#derivarSubdelegacionGenericoTabForm #cvePromocion").val());
					$("form#derivarFiscalizacionTABForm #cvePromocion").val(idPromocionG);
					//console.log("init Satica="+$("form#derivarFiscalizacionTABForm #cvePromocion").val());
					$("form#derivarSubdelegacionGenericoTabForm #fechaEmisionOficioGenerico").val($(jsFechaEmisionSaticaMain).html());
					$("form#cancelacionGenericoTabForm #functionAuxCancelacion").val("finalizaCancelacionSatica()");
					$("form#derivarSubdelegacionGenericoTabForm #functionAuxDerSubdelegacion").val("finalizaDerSubDelSatica()");
					$("form#derivarFiscalizacionTABForm #functionAuxFiscalizacion").val("finalizaDerFiscalizSatica()");	
					$("form#regularizarObraGenericoTABForm #functionAuxRegularizaObra").val("finalizaRegObraSatica()");					
					
					desbloquear();
				});
			} else {
				alert ("No se obtuvo la promoci\u00f3n"); // el valor es null
			}
	reglasSaticA();
	} catch (error) {
		alert ( "Se gener\u00f3 un error : " + error);
		}
	
}

function verificaCamposCapturablesSaticaSeg(paramData) {
	var fecNotificacionOficioResp=paramData.saticaSeguimientoTabVO.fecNotificacionOficio;
	var fecAtencionOficioResp=paramData.saticaSeguimientoTabVO.fecAtencionOficio;
	var numRegistroObraResp=paramData.saticaSeguimientoTabVO.numRegistroObra;
	var fechaInicioPeriodoResp=paramData.saticaSeguimientoTabVO.fecIniPeriodo;
	var fechaFinPeriodoResp=paramData.saticaSeguimientoTabVO.fecFinPeriodo;
	
	if (fecNotificacionOficioResp!= null && fecNotificacionOficioResp!=""){
		$(jsFechaNotificacionOfSatica).val(fecNotificacionOficioResp);
		jsFechaNotifSaticaCapturable = false;
	} else {
		jsFechaNotifSaticaCapturable = true;
	}
	
	if (fecAtencionOficioResp!= null && fecAtencionOficioResp != ""){
		$(jsFechaAtencionOficioSatica).val(fecAtencionOficioResp);
		jsFechaAtencionSaticaCapturable = false;
	} else {
		jsFechaAtencionSaticaCapturable = true;
	}					

	if (numRegistroObraResp != null && numRegistroObraResp!=""){
		$(jsNumRegistroObraSatica).removeAttr("disabled");
		$(jsNumRegistroObraSatica).val(numRegistroObraResp);
		jsNumRegistroObraSaticaCapturable = false;
	} else {
		jsNumRegistroObraSaticaCapturable = true;
	}					

	if (fechaInicioPeriodoResp!= null && fechaInicioPeriodoResp != ""){
		$(jsfechaInicioPCSaticaSeg).val(fechaInicioPeriodoResp);
	}
	
	if (fechaFinPeriodoResp!= null && fechaFinPeriodoResp != ""){
		$(jsfechaFinPCSaticaSeg).val(fechaFinPeriodoResp);
	}
	$(jsObservacionesSaticaSeg).removeAttr("disabled");
}

function deshabilitarTabsSaticaSeg(){
	
	$("#accessTabs").val(jsTabSaticaSeg +"-"+ jsTabCancelarGen +"-"+ jsTabDerivarSubDelGen +"-"+ jsTabDerivarFiscalizacionGen +"-"+ jsTabRegularizarObraGen);
	$("#forbidenTabs").val();
//	setTabDesHabilitado(jsTabCancelarGen);
//	setTabDesHabilitado(jsTabDerivarSubDelGen);	
//	setTabDesHabilitado(jsTabDerivarFiscalizacionGen);
	//setTabDesHabilitado(jsTabRegularizarObraGen);
}

function habilitarElementosSatica (){
	//var estatus=$("form#saticaMainForm #estatusPromocionSatica").val();
	
	if (jsEstatusPromocionSatica==28){
		habilitaProcesoNotificacion();
	} else if (jsEstatusPromocionSatica==32){
		habilitaPromocionRegularizada();
	}
}

/**
 * Funcion donde se habilitan los elementos para el estatus 28 
 * proceso de notificación del oficio de promoción.
 *  
 * @author Gerardo Salazar Vega
 * @version 1.0.0
 */
function habilitaProcesoNotificacion () {
	var valorRPSaticaMain=$(jsRegistroPatronalSaticaMain).val();
	
	if (valorRPSaticaMain !=null && valorRPSaticaMain.length > 0){
		registroPatronalActivar(jsFechaAtencionSaticaCapturable);
		jsRPSaticaMainValido = false;
	} else {
		registroPatronalActivar(true);
		jsRPSaticaMainValido = false;
	}
	
	if (ROL_JEFE){		
//		setTabHabilitado(jsTabDerivarSubDelGen);
//		setTabHabilitado(jsTabCancelarGen);
	}
	
	if (ROL_JEFE && !jsFechaNotifSaticaCapturable){		
//		setTabHabilitado(jsTabDerivarFiscalizacionGen);		
	}		
	
}

/**
 * Funcion donde se habilitan los elementos para el estatus 32 
 * Promoción Regularizada.
 *  
 * @author Gerardo Salazar Vega
 * @version 1.1.0
 */
function habilitaPromocionRegularizada () {
	/*Tab de seguimiento pero solo el campo de observaciones y el botón de guardar.
	El tab de Regularizar Obra con todos sus campo y botones, excepto el periodo a regularizar (del –al).
	Los demás campos de los demás tabs quedaran bloqueados.*/
	$(jsFechaNotificacionOfSatica).prop('disabled','disabled');	
	$(jsRegistroPatronalSaticaMain).prop('disabled','disabled');
	$("#btnValidaRegPatronSaticaMain").prop('disabled','disabled');
	jsRPSaticaMainValido = true;
	$(jsCheckRegularizaObra).prop('disabled','disabled');
	$(jsCheckRegularizaObra).attr('checked', true);
	
//	setTabHabilitado(jsTabRegularizarObraGen);
	$("form#regularizarObraGenericoTABForm #perRegularizaDel").prop('disabled','disabled');
	$("form#regularizarObraGenericoTABForm #perRegularizaAl").prop('disabled','disabled');
//	setTabDesHabilitado(jsTabCancelarGen);
//	setTabDesHabilitado(jsTabDerivarSubDelGen);
//	setTabDesHabilitado(jsTabDerivarFiscalizacionGen);
}

function initTabSeguimientoSaticA(){
	activeTab = '#saticaSeguimientoTab';

	habilitaCamposXForma("saticASeguimientoTabForm");
	$( jsFechaNotificacionOfSatica ).datepicker(fechaSeguimiento());
//	$( jsFechaNotificacionOfSatica ).datepicker('option', 'maxDate', jsFechaMaxSeguimiento);
//	$( jsFechaNotificacionOfSatica ).datepicker('option', 'minDate', jsFechaMinSeguimiento);
	
	$( jsFechaAtencionOficioSatica ).datepicker( fechaSeguimiento());
//	$( jsFechaAtencionOficioSatica ).datepicker('option', 'maxDate', jsFechaMaxSeguimiento);
//	$( jsFechaAtencionOficioSatica ).datepicker('option', 'minDate', jsFechaMinSeguimiento);
	$(jsFechaAtencionOficioSatica).prop('disabled','disabled');
	$(jsFechaAtencionOficioSatica).removeClass("red");
	
	// deshabilita el campo de numero de obra	
	numRegObraActivar(jsNumRegistroObraSaticaCapturable);
	//$(jsNumRegistroObraSatica).prop("disabled","disabled");
	//$(jsNumRegistroObraSatica).mask("999999999999");
	$(jsBotonValidaObraSatica).prop("disabled","disabled");
	$(jsCheckRegularizaObra).prop("disabled","disabled");
	//$("#spnBtnValidarNumRegObraSaticaSeg").hide();
	habilitarFecNotificacion();
	
	ponerEstiloCapturableSegTabSatica(true);
	
	var jsNumRObra=	$(jsNumRegistroObraSatica).val();
	if (jsNumRObra!=null && jsNumRObra.length > 0 && jsEstatusPromocionSatica==28) {
		$(jsCheckRegularizaObra).removeAttr("disabled");
		$(jsCheckRegularizaObra).addClass("red");
	}

}

function ponerEstiloCapturableSegTabSatica(asignar){
	var estatus=$("form#saticaMainForm #estatusPromocionSatica").val();
	if (asignar) {
		if (estatus!=32) {
			//habilitarFecNotificacion();
			//$(jsFechaNotificacionOfSatica).addClass("red");
			//$(jsFechaAtencionOficioSatica).addClass("red");
		}
		$(jsObservacionesSaticaSeg).addClass("red");
		
	} else {
		$(jsFechaNotificacionOfSatica).removeClass("red");
		$(jsFechaAtencionOficioSatica).removeClass("red");
		$(jsObservacionesSaticaSeg).removeClass("red");
	}
}

function validarRegistroPatronalSatica(){
	var rpSatica = $(jsRegistroPatronalSaticaMain).val();
	var jsMensajeError="El registro Patronal es inv&aacute;lido";
	
	$(jsRegPatronSaticaLabel).html("");
	if (rpSatica==""){
		alert ("Proporcione el registro patronal");
		return;
	}
	
	if(!longitudMandatoria($(jsRegistroPatronalSaticaMain).val(),10,"Registro Patronal")) return false;
	
	bloquear();

	$.postJSON(jsContextoPromocion+"seguimiento/generico/validaPatron.do", rpSatica, function(data) {
		if(data == null || data.cveRespuestaWS == JSERROR_WS) {
			if (data!=null && data.descRespuestaWS.length > 0) jsMensajeError = data.descRespuestaWS; 
			$(jsRegPatronSaticaLabel).html(jsMensajeError);
			$(jsRegistroPatronalSaticaMain).data('valAntes', null);
			eliminaDatosRPMain();
		} else if(data != null && data.razonSocial != null) {
			// asigna la clave del registro patronal al tab de seguimiento para guardarlo
			jsRPSaticaMainValido = true;
			$(jsRegistroPatronalSaticaMain).data('valAntes', data.registroPatronal.substring(0, 10));
			$(jsClaveFkPatronSaticaMain).val(data.cvePK)
			$(jsClaveFkPatronSaticaSeg).val($(jsClaveFkPatronSaticaMain).val());
			$(jsRazonSocialSaticaMain).html(data.razonSocial);
			$(jsRegistroPatronalTabPagos).val(data.registroPatronal);
		}
		desbloquear();
	}).error(function(data){ 
		desbloquear();
		validarSesionExpirada(data);			
	}).complete(function(){		
		desbloquear();												
	});
}

/**
 * Funcion donde se eliminan los datos referentes al registro patronal. 
 *  
 * @author Gerardo Salazar Vega
 * @version 1.0.0
 */
function eliminaDatosRPMain(){
	$(jsClaveFkPatronSaticaMain).val("");
	$(jsClaveFkPatronSaticaSeg).val("");
	$(jsRegistroPatronalTabPagos).val("");
	$(jsRazonSocialSaticaMain).html("");
	$(jsNumRegistroObraSatica).prop("value", "");
	//longitudMandatoria($(jsRegistroPatronalSaticaMain).val(),10,"Registro Patronal")
	jsRPSaticaMainValido = false;	
}

function reseteaValObra(){
	obraValida=false;
	$(jsCheckRegularizaObra).prop("disabled", "disabled");
	$(jsCheckRegularizaObra).removeClass("red");
	$(jsfechaInicioPCSaticaSeg).prop("value", "");
	$(jsfechaFinPCSaticaSeg).prop("value", "");
	
	

	
}

function modificaRPMain() {

		eliminaDatosRPMain();
		$('#regPatronSaticaLabel').html('Se requiere validar el registro patronal 0');	
}

function habilitarFecNotificacion(){	
	var valorFechaNotif=$(jsFechaNotificacionOfSatica).val();
	
	$("#fechaCancelacionCGT").val('');
	$("#fechaDerivarSubdel").val('');
	$("#fecDerivacionGenerica").val('');
	
	
	


	if (valorFechaNotif !=null && valorFechaNotif.length > 0){
		//paso de fecha de notificacion a pantalla de pagos
		$("form#regularizarObraGenericoTABForm #fechaNotificacionHdn").val(valorFechaNotif); 
		// habilitar tab de derivar a fiscalizacion
		// habilitar fecha atencion oficio
		if (jsEstatusPromocionSatica == 28 && ROL_JEFE && !jsFechaNotifSaticaCapturable) {
			//setTabHabilitado(jsTabDerivarFiscalizacionGen);
		}
		//setTabDesHabilitado(jsTabDerivarSubDelGen);
		if (jsFechaNotifSaticaCapturable) {
			$(jsFechaNotificacionOfSatica).removeAttr("disabled");
			$(jsFechaNotificacionOfSatica).addClass("red");
			$(jsSpanFNotificacionSatica).show("fast");
		} else {
			$(jsFechaNotificacionOfSatica).prop("disabled","disabled");
			$(jsFechaNotificacionOfSatica).removeClass("red");			
			$(jsSpanFNotificacionSatica).hide();			
		}
		if (jsFechaAtencionSaticaCapturable) {
			$(jsFechaAtencionOficioSatica).removeAttr("disabled");
			$(jsFechaAtencionOficioSatica).addClass("red");
			// $(jsSpanFAtencionOficioSatica).show("fast");
		} else {
			$(jsFechaAtencionOficioSatica).prop("disabled","disabled");
			$(jsFechaAtencionOficioSatica).removeClass("red");
			$(jsSpanFAtencionOficioSatica).hide();			
		}
		habilitarFecAtencion();
		$(jsFechaNotifCancelacion).val(valorFechaNotif);
		// Utilizado para validar la fecha notificacion en el tab de derivar a otra subdelegacion		
		$(jsFechaNotifDerivarSubDel).val(valorFechaNotif);
		$(jsFechaNotifFiscalGen).val(valorFechaNotif);		
		
	} else {
	//	setTabDesHabilitado(jsTabDerivarFiscalizacionGen);
		if (jsEstatusPromocionSatica == 28 && ROL_JEFE && !jsFechaNotifSaticaCapturable) {
		//	setTabHabilitado(jsTabDerivarSubDelGen);
		}
		
		if (jsFechaNotifSaticaCapturable) {
			$(jsFechaNotificacionOfSatica).removeAttr("disabled");
			$(jsFechaNotificacionOfSatica).addClass("red");
		}
		$(jsSpanFNotificacionSatica).hide();
		if (jsFechaAtencionSaticaCapturable) {
			$(jsFechaAtencionOficioSatica).prop("disabled","disabled");
			$(jsFechaAtencionOficioSatica).removeClass("red");
			$(jsFechaAtencionOficioSatica).val("");
			$(jsSpanFAtencionOficioSatica).hide();
		}
		habilitarFecAtencion();
		$(jsFechaNotifDerivarSubDel).val("");
		$(jsFechaNotifCancelacion).val("");
		$(jsFechaNotifFiscalGen).val("");
	}
}


function habilitarFecAtencion() {
	var valorFechaAtencion=$(jsFechaAtencionOficioSatica).val();
	if (valorFechaAtencion!=null && valorFechaAtencion.length > 0){
		if (jsFechaAtencionSaticaCapturable){
			$(jsSpanFAtencionOficioSatica).show('fast');
		}		
		numRegObraActivar(jsNumRegistroObraSaticaCapturable);
		// deshabilitar los tabs de cancelacion, derivar a otra subdelegación. y derivar a fiscalización
		// Si hay fecha de atención el número de registro de obra es obligatorio pero el registro patronal no.		
//		setTabDesHabilitado(jsTabCancelarGen);
//		setTabDesHabilitado(jsTabDerivarSubDelGen);
//		setTabDesHabilitado(jsTabDerivarFiscalizacionGen);
	} else {
		$(jsSpanFAtencionOficioSatica).hide();
		$(jsNumRegistroObraSatica).prop("disabled","disabled");
		$(jsNumRegistroObraSatica).removeClass("red");
		$(jsNumRegistroObraSatica).val('');
		validaNumRObraSaticaSeg();		
		
		if (ROL_JEFE && !jsFechaNotifSaticaCapturable){
//			setTabHabilitado(jsTabCancelarGen);
//			setTabHabilitado(jsTabDerivarSubDelGen);
//			setTabHabilitado(jsTabDerivarFiscalizacionGen);
		}	
	}
}

function validarDomVsSatic(){
	if ($(jsRegistroPatronalSaticaMain).val()==""){
		alert ("Proporcione el registro patronal");
		return;
	}
	if (!jsRPSaticaMainValido){
		alert ("Se requiere validar el registro patronal");
		return;
	}
	// Buscamos el elemento
	var idObra = $(jsNumRegistroObraSatica).val();
	if(idObra.length>0)
	{
		var sObra = '{"numeroObra":'+'"'+idObra+'"}';
		var satPatron = jQuery.parseJSON(sObra);
		bloquear();
		asignaDatosDomDeteccion();
		$(jsLabelNumRegObraSaticaSeg).html("");
		
		$.postJSON(jsContextoPromocion+"seguimiento/satica/validarObraSatica.do", satPatron, function(data) {
			desbloquear();
			if(data==null)
			{
				alert('El n\u00famero de la obra no existe o es inv\u00e1lido');
				$(jsNumRegistroObraSatica).val("");
				$('form#saticaValidaDomForm #calleSaticaSatObra').val("");
				$('form#saticaValidaDomForm #numExtSaticaSatObra').val("");
				$('form#saticaValidaDomForm #numIntSaticaSatObra').val("");
				$('form#saticaValidaDomForm #coloniaSaticaSatObra').val("");
				$('form#saticaValidaDomForm #codigoPostalSaticaSatObra').val("");
				// deshabilita el check de regularizar obra
				$(jsCheckRegularizaObra).prop('disabled','disabled');
				obraValida = false;
			}
			else
			{	
				if (data.error == null && validaObraVsRegPat(data.ubicacion.patron.registroPatronalSD)) {
					$('form#saticaValidaDomForm #calleSaticaSatObra').val(data.ubicacion.calle);
					$('form#saticaValidaDomForm #numExtSaticaSatObra').val(data.ubicacion.numeroExterior);
					$('form#saticaValidaDomForm #numIntSaticaSatObra').val(data.ubicacion.numeroInterior);
					$('form#saticaValidaDomForm #coloniaSaticaSatObra').val(data.ubicacion.colonia);
					$('form#saticaValidaDomForm #codigoPostalSaticaSatObra').val(data.ubicacion.codigoPostal);
	
					$('form#saticaValidaDomForm #cveFkPatronValidaDom').val(data.cveFkPatron);
					$('form#saticaValidaDomForm #regPatronValidaDom').val(data.ubicacion.patron.registroPatronal);
					$('form#saticaValidaDomForm #razonSocialValidaDom').val(data.ubicacion.patron.razonSocial);	
					$('form#saticaValidaDomForm #fecInicialSatObra').val(data.fechaInicial);
					$('form#saticaValidaDomForm #fecFinalSatObra').val(data.fechaFinal);
					$('form#saticaValidaDomForm #fecRegistroSatObra').val(data.fechaRegistro);	
		
					obraValida = true;
					// despues de validar la obra y el registro patronal se bloquea la captura de los 2 campos
				//	$(jsRegistroPatronalSaticaMain).prop('readonly', true).removeClass("red");
				//	$(jsBotonValidaRegPatronSaticaMain).prop('disabled','disabled');
				//	$(jsNumRegistroObraSatica).prop('readonly', true).removeClass("red");					
				//	$(jsBotonValidaObraSatica).prop('disabled','disabled');
				} else{
					obraValida = false;
					if (data.error != null) {alert (data.error)};
				}
			}
		}).error(function(data){ 
			desbloquear();
			validarSesionExpirada(data);
			alert("Error: La conexi\u00f3n no est\u00e1 disponible, intente de nuevo");
		}).complete(function(){
			//Instrucciones para el 'complete'
			if (obraValida) {
				muestraDialogoValidaDom();
			}
		});
	}
	else
	{
		$(jsLabelNumRegObraSaticaSeg).html("Se requiere el n&uacute;mero de registro de obra");
		//alert('Se requiere el n\u00famero de registro de obra');
	}	
	
}

function validaObraVsRegPat(regPatronObra){
	var jsRpValidar;			
	
//	if ($(jsClaveFkPatronSaticaSeg).val()!='') {
//		jsRpValidar = $(jsClaveFkPatronSaticaSeg).val()
//	} else {jsClaveFkPatronSaticaMain
//		jsRpValidar = $(jsClaveFkPatronSaticaMain).val()
//	}
	jsRpValidar = $(jsRegistroPatronalSaticaMain).val();	
	//console.log(", rp1"+jsRpValidar + ", rpObra="+ regPatronObra);
	if (regPatronObra!= jsRpValidar){
		alert ("La obra "+ $(jsNumRegistroObraSatica).val() + " no pertenece al registro patronal "+ $(jsRegistroPatronalSaticaMain).val());
		$(jsNumRegistroObraSatica).val("");
		obraValida = false;
		return false;
	} else{
		return true;
	}
}

function muestraDialogoValidaDom() {
	oDgSaticaValidaDomicilios = $(idSaticaValidaDomicilios).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 930,
		closeOnEscape: false,
		beforeClose :function(event,ui){
			asignaDatosDomObra();
		},		
		buttons: {
			"Continuar": function() { 
				asignaDatosDomObra();
				$(this).dialog("close");
				limpiarForm = true;				
			}
//			, 
//			"Salir": function() { 
//				$(this).dialog("close");
//				limpiarForm = true;				
//			} 
		}
	});

	oDgSaticaValidaDomicilios.dialog('open');
}

/**
 * Funcion donde asignan los datos del domicilio de la deteccion 
 * en el dialogo Validar Domicilio vs Satic.
 *  
 * @author Gerardo Salazar Vega
 * @version 1.0.0
 */
function asignaDatosDomDeteccion(){
	$("form#saticaValidaDomForm #calleSaticaDet").val($("form#saticaMainForm #calleSatica").html());
	$("form#saticaValidaDomForm #coloniaSaticaDet").val($("form#saticaMainForm #coloniaSatica").html());
	$("form#saticaValidaDomForm #numExtSaticaDet").val($("form#saticaMainForm #numExtSatica").html());
	$("form#saticaValidaDomForm #numIntSaticaDet").val($("form#saticaMainForm #numIntSatica").html());
	$("form#saticaValidaDomForm #codigoPostalSaticaDet").val($("form#saticaMainForm #codigoPostalSatica").html());
}

/**
 * Funcion que asigna los valores del dialogo Validar Domicilio vs Satic hacia  
 * el tab de seguimiento Satic A y la pantalla principal de Satic A.
 *  
 * @author Gerardo Salazar Vega
 * @version 1.0.0
 */
function asignaDatosDomObra () {
	// Se asignan las fechas de la obra en periodo inicial y final del tab de seguimiento
	$(jsfechaInicioPCSaticaSeg).val($('form#saticaValidaDomForm #fecInicialSatObra').val());
	$(jsfechaFinPCSaticaSeg).val($('form#saticaValidaDomForm #fecFinalSatObra').val());
	//habilita el check de regularizar obra
	validaNumRObraSaticaSeg();
}

/**
 * Funcion donde se borra la fecha capturada en el campo 
 * fecha de Notificacion del Oficio del tab de seguimiento Satic A.
 *  
 * @author Gerardo Salazar Vega
 * @version 1.0.0
 */
function limpiaFechaNotificacionSaticaSeg(){	
	$(jsFechaNotificacionOfSatica).val("");
	$(jsFechaAtencionOficioSatica).val("");
	habilitarFecNotificacion();	
	reglasSaticA();
}

/**
 * Funcion donde se borra la fecha capturada en el campo 
 * fecha de Atencion del Oficio del tab de seguimiento Satic A.
 *  
 * @author Gerardo Salazar Vega
 * @version 1.0.0
 */
function limpiaFechaAtencionSaticaSeg(){	
	$(jsFechaAtencionOficioSatica).val("");
	habilitarFecAtencion();	
	reglasSaticA();
}

/**
 * Funcion donde se valida la fecha capturada en el campo 
 * fecha de Notificacion del Oficio del tab de seguimiento Satic A.
 *  
 *  
 * @version 1.0.0
 */
function validaFechaNotificacionSaticaSeg () {
	var mensaje_errorFechaNotSeg="<label class='etiquetaError'>La fecha de notificaci&oacute;n no puede ser menor a la fecha de Fecha de Oficio de Promoci&oacute;n</label>";	
	var fechaNotificacionOf=$(jsFechaNotificacionOfSatica).val();
	var fechaEmision=$(jsFechaEmisionSaticaMain).html();	
	//alert ("fechaemi="+fechaEmision+", "+$(jsFechaEmisionSaticaMain).html())
	if (fechaNotificacionOf==""){
		alert ("Verifique que exista la fecha de notificacion del oficio");
		//validaSdGen = false;
	} else if (fechaEmision!="" && !comparaFechas(fechaEmision, fechaNotificacionOf, '-')){
		$("form#saticASeguimientoTabForm #labelFechaNotificacionSaticaSeg").html(mensaje_errorFechaNotSeg);
		limpiaFechaNotificacionSaticaSeg();
		//validaSdGen = false;
	} else {
		$("form#saticASeguimientoTabForm #labelFechaNotificacionSaticaSeg").html("");
	}
	habilitarFecNotificacion();	
	$("form#saticASeguimientoTabForm #fechaAtnOficioSaticaSeg").val("");
	
}

/**
 * Funcion donde se valida la fecha capturada en el campo 
 * fecha de Atencion del Oficio del tab de seguimiento Satic A.
 *  
 * @author Gerardo Salazar Vega
 * @version 1.0.0
 */
function validaFechaAtencionSaticaSeg () {
	var mensaje_errorFecha="<label class='etiquetaError'>La fecha de atenci&oacute;n no puede ser menor a la fecha de notificaci&oacute;n</label>";	
	var fechaNotificacionOf=$(jsFechaNotificacionOfSatica).val();
	var fechaAtencion=$(jsFechaAtencionOficioSatica).val();	
	
	if (fechaNotificacionOf==""){
		alert ("Verifique que exista la fecha de notificacion del oficio");
		//validaSdGen = false;
	} else if (!comparaFechas(fechaNotificacionOf, fechaAtencion, '-')){
		$("form#saticASeguimientoTabForm #labelFechaAtencionSaticaSeg").html(mensaje_errorFecha);
		limpiaFechaAtencionSaticaSeg();
	} else {
		$("form#saticASeguimientoTabForm #labelFechaAtencionSaticaSeg").html("");
	}	
	habilitarFecAtencion();
	reglasSaticA();
}

// habilita el check de regularizar obra
function validaNumRObraSaticaSeg(){
	var numRObra=$(jsNumRegistroObraSatica).val();
	
	if (numRObra!= null && numRObra.length > 0){
		$(jsCheckRegularizaObra).removeAttr("disabled");
		$(jsCheckRegularizaObra).addClass("red");
	} else {
	//	$(jsBotonValidaObraSatica).prop('disabled','disabled');
		$(jsCheckRegularizaObra).attr('checked', false);
		$(jsCheckRegularizaObra).prop('disabled','disabled');				
		$(jsfechaInicioPCSaticaSeg).val("");
		$(jsfechaFinPCSaticaSeg).val("");		
	}	
}

// falta validar con el rp del tab
function validaCamposSaticaSeg() {
	var fechaNotificacionOf = $(jsFechaNotificacionOfSatica).val();
	var fechaAtencion = $(jsFechaAtencionOficioSatica).val();
	var observacionesSatica = $(jsObservacionesSaticaSeg).val();
	var mensaje_error_reg_obra="<label class='etiquetaError'>Se requiere un n&uacute;mero de registro de obra v&aacute;lido</label>";
	var valorRPSaticaMain=$(jsRegistroPatronalSaticaMain).val();
	var datosValidosSaticA=true;

	if (fechaNotificacionOf.length == 0 && fechaAtencion.length == 0
			&& valorRPSaticaMain.length == 0 && observacionesSatica.length == 0) {
			alert("No hay informaci\u00f3n por guardar");
			return false;
	}
	
	
	$(jsLabelNumRegObraSaticaSeg).html("");
	//Si la fecha de atención es ingresada el número de registro de obra es obligatorio pero el registro patronal no
	if (fechaAtencion.length > 0) {
		if (jsNumRegistroObraSaticaCapturable && !obraValida) {
			$(jsLabelNumRegObraSaticaSeg).html(mensaje_error_reg_obra);
			datosValidosSaticA=false;
		}
		if ($(jsRegistroPatronalSaticaMain).val().length == 0){
			$(jsRegPatronSaticaLabel).html("Campo requerido");
			datosValidosSaticA=false;
		}
	}
	return datosValidosSaticA;
}

function checkRegularizarObraSaticaSeg (){
//	Check box Regularizar Obra
//	Al dar clic en regularizar obra el tab de regularizar obra se habilitara, pero antes se tiene que enviar el siguiente mensaje 
//	“Antes de continuar debe guardar su información”, y se guardar la información en la tabla crt_promocion 
//	y se deshabilitaran los campos de los demás tabs (Cancelación, derivación a otra subdelegación, derivar a fiscalización ).
	var checkRegObraSaticaS=$(jsCheckRegularizaObra).is(":checked");
	// alert (checkRegObraSaticaS);
	if (checkRegObraSaticaS){
		alert ("Antes de continuar debe guardar su informaci\u00f3n");
		$(jsEstatusPromocionSaticaSeg).val("32");
			if (procesaFormularioSaticaSeg("validaCamposSaticaSeg()") ){
				setTabHabilitado(jsTabRegularizarObraGen);
				$(jsCheckRegularizaObra).prop('disabled','disabled');
				deshabilitaCamposXForma("cancelacionGenericoTabForm");
				deshabilitaCamposXForma("derivarSubdelegacionGenericoTabForm");
//				deshabilitaCamposXForma("derivarFiscalizacionTABForm");				
			} else {
				$(jsCheckRegularizaObra).attr('checked', false);
			}
		} else {
			// colocar estado original del estatus de la promocion, traerlo del hidden de satica main
			//$(jsEstatusPromocionSaticaSeg).val("28");			
			setTabDesHabilitado(jsTabRegularizarObraGen);
		} 
}

function procesaFormularioSaticaSeg(funcionValidacion) {
	var resultGuarda=false;
	FORMA_ACTUAL ='saticASeguimientoTabForm';
	document.forms["saticASeguimientoTabForm"].action = jsContextoPromocion+"seguimiento/satica/guardarSaticaSeguimiento.do";
	//document.saticASeguimientoTabForm.action = jsContextoPromocion+"seguimiento/satica/guardarSaticaSeguimiento.do";
	if ($('#fechaNotificacionSaticaSeg').val() == ''){
		var mensaje_errorFechaNotSeg="<label class='etiquetaError'>Debe de seleccionar una Fecha de Notificaci&oacute;n de Oficio</label>";
		$("form#saticASeguimientoTabForm #labelFechaNotificacionSaticaSeg").html(mensaje_errorFechaNotSeg);
		
	}
	//$("form#saticASeguimientoTabForm #regularizaObraSaticaSeg").prop("disabled","disabled");
	if ($('#fechaNotificacionSaticaSeg').val() != '' && confirm("Est\u00e1 seguro que desea guardar sus datos ?")) {
		
		if (procesaFormulario(funcionValidacion)) {
			deshabilitaCamposSaticA();
			finalizaGuardadoSaticaSeg();
			alert("La informaci\u00f3n ha sido guardada");
			resultGuarda = true;
			reglasSaticA();
		}
	}
	return resultGuarda;
}

function guardaSaticaSegTab() {
	FORMA_ACTUAL = 'saticASeguimientoTabForm';
	document.forms["saticASeguimientoTabForm"].action = jsContextoPromocion	+ "seguimiento/satica/guardarSaticaSeguimiento.do";
	// validacion para que no modifique el estatus cuando guarda desde otro tab
	$(jsEstatusPromocionSaticaSeg).val("");
	if (procesaFormulario('true')) {
		deshabilitaCamposXForma("saticASeguimientoTabForm");
	}
	return true;
}

function finalizaCancelacionSatica(){	
	$("form#saticASeguimientoTabForm #fechaCancelacionOfSaticaSeg").val($("form#cancelacionGenericoTabForm #fechaCancelacionCGT").val());
	
	deshabilitaCamposXForma("saticASeguimientoTabForm");
	deshabilitaCamposXForma("derivarFiscalizacionTABForm");
	deshabilitaCamposXForma("derivarSubdelegacionGenericoTabForm");
	deshabilitaCamposXForma("regularizarObraGenericoTABForm");
//	setTabDesHabilitado(jsTabDerivarSubDelGen);
//	setTabDesHabilitado(jsTabDerivarFiscalizacionGen);
	//setTabDesHabilitado(jsTabRegularizarObraGen);
//	guardaSaticaSegTab();
	//procesaFormularioSaticaSeg('validaCamposSaticaSeg()');
}

function finalizaDerSubDelSatica(){	
	$("form#saticASeguimientoTabForm #fechaDerivacionSubDelSaticaSeg").val($("form#derivarSubdelegacionGenericoTabForm #fechaDerivarSubdel").val());	
	deshabilitaCamposXForma("saticASeguimientoTabForm");
	deshabilitaCamposXForma("derivarFiscalizacionTABForm");
	deshabilitaCamposXForma("derivarSubdelegacionGenericoTabForm");
	deshabilitaCamposXForma("regularizarObraGenericoTABForm");
	//guardaSaticaSegTab();
//	setTabDesHabilitado(jsTabCancelarGen);
//	setTabDesHabilitado(jsTabDerivarFiscalizacionGen);
//	setTabDesHabilitado(jsTabRegularizarObraGen);
}

function finalizaDerFiscalizSatica(){	
	$("form#saticASeguimientoTabForm #fechaDerivacionFisSaticaSeg").val($("form#derivarFiscalizacionTABForm #fecDerivacionGenerica").val());
	
	deshabilitaCamposXForma("saticASeguimientoTabForm");
	deshabilitaCamposXForma("derivarFiscalizacionTABForm");
	deshabilitaCamposXForma("derivarSubdelegacionGenericoTabForm");
	deshabilitaCamposXForma("regularizarObraGenericoTABForm");
//	setTabDesHabilitado(jsTabDerivarSubDelGen);
//	setTabDesHabilitado(jsTabCancelarGen);
//	setTabDesHabilitado(jsTabRegularizarObraGen);
	//guardaSaticaSegTab();
}

function finalizaRegObraSatica(){	
	$(jsfechaInicioPCSaticaSeg).val($("form#regularizarObraGenericoTABForm #perRegularizaDel").val());
	$(jsfechaFinPCSaticaSeg).val($("form#regularizarObraGenericoTABForm #perRegularizaAl").val());
//	setTabDesHabilitado(jsTabDerivarSubDelGen);
//	setTabDesHabilitado(jsTabCancelarGen);
//	setTabDesHabilitado(jsTabDerivarFiscalizacionGen);	
	deshabilitaCamposXForma("saticASeguimientoTabForm");
	deshabilitaCamposXForma("derivarFiscalizacionTABForm");
	deshabilitaCamposXForma("derivarSubdelegacionGenericoTabForm");
	deshabilitaCamposXForma("regularizarObraGenericoTABForm");
	$("form#regularizarObraGenericoTABForm #trabRevisados").prop("readonly", "readonly");
	 
	$("form#regularizarObraGenericoTABForm #trabOmisosUni").prop("readonly", "readonly");
	 
	$("form#regularizarObraGenericoTABForm #trabSubdeclaUni").prop("readonly", "readonly");
	 
	$("form#regularizarObraGenericoTABForm #baseDeterminada").prop("readonly", "readonly");
	 
	$("form#regularizarObraGenericoTABForm #suertePpalDetCop").prop("readonly", "readonly");
	
	$("form#regularizarObraGenericoTABForm #suertePpalDetRcv").prop("readonly", "readonly");
	$("form#regularizarObraGenericoTABForm #porcentajeAvance").prop("readonly", "readonly");
	$("form#regularizarObraGenericoTABForm #porcentajeRegularizado").prop("readonly", "readonly");
	$("form#regularizarObraGenericoTABForm #numeroParcialidades").prop("readonly", "readonly");

	
	$("form#regularizarObraGenericoTABForm #trabRevisados").removeClass("red");
	 
	$("form#regularizarObraGenericoTABForm #trabOmisosUni").removeClass("red");
	 
	$("form#regularizarObraGenericoTABForm #trabSubdeclaUni").removeClass("red");
	$("form#regularizarObraGenericoTABForm #baseDeterminada").removeClass("red");
	 
	$("form#regularizarObraGenericoTABForm #suertePpalDetCop").removeClass("red");
	$("form#regularizarObraGenericoTABForm #porcentajeAvance").removeClass("red");
	$("form#regularizarObraGenericoTABForm #porcentajeRegularizado").removeClass("red");
	$("form#regularizarObraGenericoTABForm #numeroParcialidades").removeClass("red");
	$("form#regularizarObraGenericoTABForm #suertePpalDetRcv").removeClass("red");
	$("form#regularizarObraGenericoTABForm #btnGuardarRegularizaObra").prop("disabled", "disabled");
	
	$("#spnPeriodoRegObra").hide();
	$("#btnDatosRegularizacion").removeAttr("disabled");

}

/**
 * Funcion para bloquear elementos input (txt, button)
 * contenidos en el tab de seguimiento satic A y satic A main 
 * y quita el estilo capturable.  
 *  
 * @author Gerardo Salazar Vega
 * @version 1.0.0
 */
function deshabilitaCamposSaticA(){
//	$(jsFechaNotificacionOfSatica + ", not(:empty)").prop('disabled','disabled').removeClass("red");
//	$(jsFechaAtencionOficioSatica + ", not(:empty)").prop('disabled','disabled').removeClass("red");	
//	$(jsNumRegistroObraSatica + ", not(:empty)").prop('disabled','disabled').removeClass("red");	
//	$(jsRegistroPatronalSaticaMain + ", not(:empty)").prop('disabled','disabled').removeClass("red");
	
	if ($(jsFechaNotificacionOfSatica).val().length>0){
		$(jsFechaNotificacionOfSatica).prop('disabled','disabled').removeClass("red");
		$(jsSpanFNotificacionSatica).hide();
	}

	if ($(jsFechaAtencionOficioSatica).val().length>0){
		$(jsFechaAtencionOficioSatica).prop('disabled','disabled').removeClass("red");
		$(jsSpanFAtencionOficioSatica).hide();
	}

	if ($(jsNumRegistroObraSatica).val().length>0){
		$(jsNumRegistroObraSatica).prop('disabled','disabled').removeClass("red");
		$(jsBotonValidaObraSatica).prop('disabled','disabled');
	}
	
	if ($(jsNumRegistroObraSatica).val().length>0 && $(jsFechaAtencionOficioSatica).val().length>0){
		$(jsRegistroPatronalSaticaMain).prop('disabled','disabled');
		$(jsBotonValidaRegPatronSaticaMain).prop('disabled','disabled');
	}	
}

function fecNotificacionSaticaActivar(jsActivar) {
	if (jsActivar) {
		$(jsFechaNotificacionOfSatica).removeAttr("disabled");
		$(jsFechaNotificacionOfSatica).addClass("red");
		$(jsSpanFNotificacionSatica).show("fast");
	} else  {
		$(jsFechaNotificacionOfSatica).prop("disabled","disabled");
		$(jsFechaNotificacionOfSatica).removeClass("red");			
		$(jsSpanFNotificacionSatica).hide();			
	}
}

function fecAtencionSaticaActivar(jsActivar) {
	if (jsActivar) {
		$(jsFechaAtencionOficioSatica).removeAttr("disabled");
		$(jsFechaAtencionOficioSatica).addClass("red");
		$(jsSpanFAtencionOficioSatica).show("fast");
	} else {
		$(jsFechaAtencionOficioSatica).prop("disabled","disabled");
		$(jsFechaAtencionOficioSatica).removeClass("red");
		$(jsSpanFAtencionOficioSatica).hide();
	}
}

function numRegObraActivar(jsActivar) {
	if (jsActivar) {
		$(jsNumRegistroObraSatica).removeAttr("disabled");
		$(jsNumRegistroObraSatica).addClass("red");
		$(jsNumRegistroObraSatica).prop('readonly', false);
		$(jsBotonValidaObraSatica).removeAttr("disabled");		
	} else {
		$(jsNumRegistroObraSatica).prop("disabled","disabled");
		$(jsNumRegistroObraSatica).removeClass("red");
		$(jsBotonValidaObraSatica).prop("disabled","disabled");
	}	
}
function checkRegularizaObraActivar(jsActivar) {
	if (jsActivar) {
		$(jsCheckRegularizaObra).removeAttr("disabled");
		$(jsNumRegistroObraSatica).addClass("red");
	} else {
		$(jsCheckRegularizaObra).prop('disabled','disabled');
		$(jsNumRegistroObraSatica).removeClass("red");
	}
}

function registroPatronalActivar(jsActivar) {
	if (jsActivar) {
		$(jsRegistroPatronalSaticaMain).removeAttr("disabled");
		$(jsRegistroPatronalSaticaMain).addClass("red");
		$("#btnValidaRegPatronSaticaMain").removeAttr("disabled");
		$(jsRegistroPatronalSaticaMain).prop('readonly', false)
	} else {
		$(jsRegistroPatronalSaticaMain).prop('disabled','disabled');
		$(jsRegistroPatronalSaticaMain).removeClass("red");
		$("#btnValidaRegPatronSaticaMain").prop('disabled','disabled');		
	}	
}
/**
 * Funcion para habilitar elementos
 * al finalizar el guardado del seguimiento
 * del modulo Satic A
 * @author Gerardo Salazar Vega 
 */
function finalizaGuardadoSaticaSeg() {
	var jsValFechaNotificacion= $(jsFechaNotificacionOfSatica).val();
	
	if (ROL_JEFE && jsValFechaNotificacion.length > 0){
		setTabHabilitado(jsTabDerivarFiscalizacionGen);
	}
}



//////////////////////////////////////////////////////////////
//Validaciones para la habilitacion de pestanias


function valSeguimientoSaticASeguimiento(){	
	//siempre esta activa
	return true;
}


function valSeguimientoSaticACancelacion(){
	var flag=false;
	if(ROL_JEFE){
		if (($("#fechaNotificacionSaticaSeg").val() == ""  && $("#fechaDerivarSubdel").val() == "")
				||( ($("#fechaNotificacionSaticaSeg").val() != "" && $("#fechaAtnOficioSaticaSeg").val() == "")
				&& ($("#fechaDerivarSubdel").val() == "")
				&& ($("#fecDerivacionGenerica").val() == ""))) {
			flag = true;
		}		
	}
	return flag;
}

function valSeguimientoSaticADerivaSubdelegacion(){
	var flag=false;
	if(ROL_JEFE){
		if(($("#fechaNotificacionSaticaSeg").val()=="" && $("#fechaCancelacionCGT").val()=="") ||(($("#fechaNotificacionSaticaSeg").val() != "" && $('#fechaNotificacionSaticaSeg').is('[disabled]') && $("#fechaAtnOficioSaticaSeg").val() == "")
			&& ($("#fechaCancelacionCGT").val() == "")	
			&& ($("#fecDerivacionGenerica").val() == ""))){
			flag = true;
		}		
	}
	return flag;
}

function valSeguimientoSaticADerivaFizca(){
	var flag=false;
	if(ROL_JEFE){
		//if(($("#fechaNotificacionSaticaSeg").val() != "" && $("#fechaAtnOficioSaticaSeg").val() != "" && $('#fechaAtnOficioSaticaSeg').is('[disabled]')) 
		//10-Junio-2014 Marible modifico regla
		if(($("#fechaNotificacionSaticaSeg").val() != "" && $("#fechaAtnOficioSaticaSeg").val() == "") 
			&&($("#fechaCancelacionCGT").val() == "")
			&&($("#fechaDerivarSubdel").val() == "")
			){
			flag = true;
		}		
	}
	return flag;
}

function valSeguimientoSaticARegularizaObra(){
	if($(jsCheckRegularizaObra).is(":checked")){
		return true;
	}else{
		return false;
	}	
}



function reglasSaticA(){
	
	bloquearTabs("saticaSeguimientoTab,cancelacionGenericoTab_satica,derivarSubdelegacionGenericoTab_satica,derivarFiscalizacionTAB_satica,regularizarObraGenericoTAB_satica");	
	var reglas = new Object();
	reglas['valSeguimientoSaticASeguimiento()'] = 'saticaSeguimientoTab';
	reglas['valSeguimientoSaticACancelacion()'] = 'cancelacionGenericoTab_satica';
	reglas['valSeguimientoSaticADerivaSubdelegacion()'] = 'derivarSubdelegacionGenericoTab_satica';
	reglas['valSeguimientoSaticADerivaFizca()'] = 'derivarFiscalizacionTAB_satica';
	reglas['valSeguimientoSaticARegularizaObra()'] = 'regularizarObraGenericoTAB_satica';
	evaluaReglasTab(reglas);	
	
}



