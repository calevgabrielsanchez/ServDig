	/**
	 * indica el tab seleccionado
	 */
	activeTab = '#seguimientoSBCTAB_SBC';
	
	/**
	 * Indica la forma seleccionada
	 */
	FORMA_ACTUAL ='seguimientoSBCTABForm';

/**Variables indicativas de las secciones
 *disponibles. 
 */
var SEGUIMIENTO=1;
var CANCELACION=2;
var DERIVA_SUBDELEGACION=3;
var DERIVA_FISCALIZACION=4;
var REGULARIZAR_OBRA=5;


var flagPermiso=false;

var oDgDatosSBC;
var idDatosSBC   = "#dgPromocionDatosSBC";

var idDgConfirmarRP = "#dgConfirmarRegularizaObra";
var oDgConfirmarRP;

var idDgConfirmarSBCTab = "#dgConfirmarRegularizaObra";
var oDgConfirmarSBCTab;

var URL = getAppContextParaJS() + "/promocion/consulta";


var JEFE_OF_CORRECCION = 4;
var JEFE_OF_CORR_Y_DIC = 6;
var JEFE_DEP_AUD_PAT = 7;
/**
 * Controla el valor del hidden el cual indica
 * en que sección se encuentra el usuario.
 * Esta variable es utilizada por JAVA.
 */
function setToFormSeccionActual(){
	
	}

/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion que inicializa el dialogo principal para el seguimiento SBC
 */
function inicializaDialogSBC(){
	
	
	oDgDatosSBC = $(idDatosSBC).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 930,
		closeOnEscape: false,
		beforeClose :function(event,ui){
			//Limpiamos todos los jsp
			jsLimpiarFormaSeguimientoSBC();
			oDgConsultaRegistros.fnDraw();
		},
		buttons: {
			"Regresar": function() { 
				$(this).dialog("close"); 								
			} 
		}
	});
	
	mostrarSBC();
}


/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion consulta con la cve_promocion seleccionada y llena la informacion en la pantalla
 */
function mostrarSBC(){
	var idPromocion = $('#:checked').val();
	if(idPromocion != null){
		var sPromocion = '{"cvePromocion":'+idPromocion+'}';
		var promocion = jQuery.parseJSON(sPromocion);
		// Buscamos el elemento
		$.postJSON(URL + "/muestraSBC.do", promocion, function(data) {
			if(data != null){				
				// LLena Datos Informativos de otras pestañas
				$("form#segimientoSBCForm #seguimientoSBC").val("SBC");
				// se oculta el boton de generar invitacion
				$("form#seguimientoSBCTABForm #butonSBCInv").hide();
				// datos de SBC
				$("form#segimientoSBCForm #labelCriterioSeleccion").html('<label >' + data.descCriterioseleccion + '</label>');
				$("form#segimientoSBCForm #labelFolio").html('<label >' + data.nuFoliopromocion + '</label>');
				$("form#segimientoSBCForm #labelFechaOficio").html('<label >' + data.fechaOficio + '</label>');
				$("form#seguimientoSBCTABForm #fechaOficioSBC").val(data.fechaOficio);				
				$("form#segimientoSBCForm #labelNumeroOficio").html('<label >' + data.nuOficiopro + '</label>');
				$("form#segimientoSBCForm #labelRegistroPatronal").html('<label >' + data.regPatron + '</label>');
				$("form#segimientoSBCForm #regPatronalSbsHdn").val(data.regPatron);
				
				$("form#segimientoSBCForm #labelRazonSocial").html('<label >' + data.razonSocial + '</label>');
				$("form#segimientoSBCForm #labelCalle").html('<label >' + data.domCalle + '</label>');
				$("form#segimientoSBCForm #labelColonia").html('<label >' + data.refColonia + '</label>');
				if(data.numNroext != null){
					$("form#segimientoSBCForm #labelNumExt").html('<label >' + data.numNroext + '</label>');
				}
				if(data.numNroint != null){
					$("form#segimientoSBCForm #labelNumInt").html('<label >' + data.numNroint + '</label>');
				}							
				$("form#segimientoSBCForm #labelCP").html('<label >' + data.numCodigopostal + '</label>');
							
				/**
				 * Inicializamos TABS
				 */
				$("#accessTabs").val("seguimientoSBCTAB_SBC-cancelacionGenericoTab_SBC-autAviDictamenGenericoTab_SBC-cierrePorCotizarRGenericoTab_SBC-regularizarObraGenericoTAB_SBC");
				if(data.rolUsuario == JEFE_OF_CORRECCION || data.rolUsuario == JEFE_OF_CORR_Y_DIC || data.rolUsuario == JEFE_DEP_AUD_PAT){
					$("#accessTabs").val("seguimientoSBCTAB_SBC-cancelacionGenericoTab_SBC-autAviDictamenGenericoTab_SBC-cierrePorCotizarRGenericoTab_SBC-regularizarObraGenericoTAB_SBC");
					flagPermiso=true;
				}else{
					$("form#seguimientoSBCTABForm #rolSBCSeguimiento").val("NP");
					flagPermiso=false;
					
				}
				if(data.cveEstatus == '28'){
					jsBloqueaStatus28();
					if(data.fechaNotificacion != null){
						$("form#seguimientoSBCTABForm #fechaNotificacionSBCSeg").val(data.fechaNotificacion);
						$("form#seguimientoSBCTABForm #labelAlert").html('');
						$('form#seguimientoSBCTABForm #fechaNotificacionSBCSeg').removeClass("red");
						$("form#seguimientoSBCTABForm #fechaAtencionSBCSeg").attr("disabled",false);
						$('form#seguimientoSBCTABForm #fechaAtencionSBCSeg').addClass("red");
						$("form#seguimientoSBCTABForm #butonSBCInv").attr("disabled",false);
						 // Manda fecha a la cancelacion
						$("form#cancelacionGenericoTabForm #fechaNotificacionOficioGenerico").val(data.fechaNotificacion);
						 // Manda fecha a la Invitacion
						$("form#invitacionAntecedenteForm #fechaNotificacionInv").val(data.fechaNotificacion);
						// Mnada fecha aviso dictamen
						$("form#autAviDictamenGenericoTabForm #fechaNotificacionOficioDictamenGenerico").val(data.fechaNotificacion);
						//fecha notificacion para pagos
						$("form#regularizarObraGenericoTABForm #fechaNotificacionHdn").val(data.fechaNotificacion); 
						
						$('form#seguimientoSBCTABForm #btnLimpiarSBCTAB').prop('disabled',false);
						$('form#seguimientoSBCTABForm #btnGuardarSBCTAB').prop('disabled',false);
						$('form#seguimientoSBCTABForm #btnLimpiaFechaNotif').hide();
						$("form#seguimientoSBCTABForm #fechaNotificacionSBCSeg").prop('disabled',true);	
						
						$("form#seguimientoSBCTABForm #butonSBCInv").attr("disabled",false);
					}
					if($("form#seguimientoSBCTABForm #rolSBCSeguimiento").val() != "NP"){						
						setTabHabilitado('cancelacionGenericoTab_SBC');	
						setTabHabilitado('autAviDictamenGenericoTab_SBC');	
					}else{
						setTabDesHabilitado('cancelacionGenericoTab_SBC');	
					}
					if(data.fechaNotificacion != null && data.fechaAtencion != null){
						$('form#seguimientoSBCTABForm #fechaAtencionSBCSeg').removeClass("red");
						$("form#seguimientoSBCTABForm #fechaAtencionSBCSeg").val(data.fechaAtencion);
						$("form#seguimientoSBCTABForm #labelAlert").html('');
						//se agrega para verificar si se cuenta con el permiso 
						if($("form#seguimientoSBCTABForm #rolSBCSeguimiento").val() != "NP"){
							 setTabHabilitado('autAviDictamenGenericoTab_SBC');
							 setTabHabilitado('cierrePorCotizarRGenericoTab_SBC');
						}
						 $("form#seguimientoSBCTABForm #butonSBCInv").attr("disabled",true);
						 $("form#seguimientoSBCTABForm #banderaSBC").attr("disabled",false);
						 $("form#cierrePorCotizarRGenericoTabForm #fechaAtencionGenericaCCRG").val(data.fechaAtencion);
						 $('form#seguimientoSBCTABForm #btnLimpiarSBCTAB').prop('disabled',false);
						 $('form#seguimientoSBCTABForm #btnGuardarSBCTAB').prop('disabled',false);
						 $('form#seguimientoSBCTABForm #btnLimpiaFechaAtencion').hide();
						 $("form#seguimientoSBCTABForm #fechaAtencionSBCSeg").prop('disabled',true);
						 setTabDesHabilitado('cancelacionGenericoTab_SBC');	
					}
					changeTab('seguimientoSBCTAB_SBC');
				}else if(data.cveEstatus == '32'){
					jsBloqueaStatus32();
					if(data.fechaNotificacion != null){
						$("form#seguimientoSBCTABForm #fechaNotificacionSBCSeg").val(data.fechaNotificacion);
						//fecha notificacion para pagos
						$("form#regularizarObraGenericoTABForm #fechaNotificacionHdn").val(data.fechaNotificacion);
						$("form#seguimientoSBCTABForm #labelAlert").html('');
						$('form#seguimientoSBCTABForm #fechaNotificacionSBCSeg').removeClass("red");
						$("form#seguimientoSBCTABForm #butonSBCInv").attr("disabled",false);
					}
					if(data.fechaNotificacion != null && data.fechaAtencion != null){
						$('form#seguimientoSBCTABForm #fechaAtencionSBCSeg').removeClass("red");
						$("form#seguimientoSBCTABForm #fechaAtencionSBCSeg").val(data.fechaAtencion);
						$("form#seguimientoSBCTABForm #labelAlert").html('');
					}
					if(data.fechaInicio != null){
						$("form#seguimientoSBCTABForm #labelFechaDelSegSBC").val(data.fechaInicio);
					}
					if(data.fechaFin != null){
						$("form#seguimientoSBCTABForm #labelFechaAlSegSBC").val(data.fechaFin);
					}
					
					jsBloquearSeguimientoSBCTAB();
					
					
				}
			
				
				$("form#seguimientoSBCTABForm #txObservacionesSBCTAB").val(data.txObservaciones);
				$("form#seguimientoSBCTABForm #txObservacionesSBCTAB").addClass("red");
				$("form#seguimientoSBCTABForm #txObservacionesSBCTAB").prop('disabled',false);
				$("form#seguimientoSBCTABForm #btnGuardarSBCTAB").prop('disabled',false);
				changeTab('seguimientoSBCTAB_SBC');
				oDgDatosSBC.dialog("open");	
				
				// Pestaña Cierre Por Cotizar Razonablemente
				$("form#cierrePorCotizarRGenericoTabForm #labelFecCotizarRaz").html('');
				$("form#cierrePorCotizarRGenericoTabForm #labelFuncionarioReg").html('<label >' + data.auditor + '</label>');
				$("form#cierrePorCotizarRGenericoTabForm #cvePromocionCCRG").val(data.cvePromocion);
				$("form#cierrePorCotizarRGenericoTabForm #cveUsuarioCCRG").val(data.cveUsuario);
				$("form#cierrePorCotizarRGenericoTabForm #functionAuxCCRG").val("seguimientoSBCCierrePorCotizarR()");
				
				// Pestaña Invitacion
				$("form#invitacionAntecedenteForm #cvePromocion").val(data.cvePromocion);
				$("form#invitacionAntecedenteForm #funcionSeguimientoInv").val("seguimientoSBCInvitacion()");
				
				
				// Datos a regularizar promocion
				$("form#regularizarObraGenericoTABForm #cvePromocion").val(data.cvePromocion);
				$("form#regularizarObraGenericoTABForm #registroPatronalPagosDt").val(data.regPatron);
				$("form#regularizarObraGenericoTABForm #functionAuxRegularizaObra").val("seguimientoSBCRagulariza()");
				
				// Pestaña Seguimiento
				$("form#seguimientoSBCTABForm #cvePromocionSBCTAB").val(data.cvePromocion);
				
				//Pestaña cancelacion
				initTabCancelacionGenerico();
				$("form#cancelacionGenericoTabForm #functionAuxCancelacion").val("seguimientoSBCCancelacion()");
				$("form#cancelacionGenericoTabForm #cvePromocion").val(data.cvePromocion);
				$("form#cancelacionGenericoTabForm #fechaEmisionOficioGenerico").val(data.fechaOficio);
				
				
				// Pestaña Aut Avi Dict	
				initTabAvisoDictamenGenerico();
				$("form#autAviDictamenGenericoTabForm #fechaEmisionOficioDictamenGenerico").val(data.fechaOficio);
				$("form#autAviDictamenGenericoTabForm #functionAuxAutAvisoDict").val("seguimientoSBCAutAviDict()");
				$("form#autAviDictamenGenericoTabForm #cvePromocion").val(data.cvePromocion);
				//$("form#autAviDictamenGenericoTabForm #funcionarioRegGenericoTab").val(data.auditor);
					
				inicializaFormaSBC();				
				initTabRegularizarObra();				
				inicializaFormaCierrePorCotR();
								
			}							
		}).error(function(data){ 
			validarSesionExpirada(data);
		}).complete(function(){
			
			desbloquear();
		});
	}
}


/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion que inicializa los datepicker de la pestaña seguimiento
 */
function inicializaFormaSBC(){
	
	$( "form#seguimientoSBCTABForm #fechaAtencionSBCSeg").datepicker( { dateFormat: 'dd-mm-yy' ,
		onSelect: function(dateText, inst) { 
			$("#fecCotizRazCCRG").val("");
			jsValidaFecAtencion();
		    }
	});
	
	$( "form#seguimientoSBCTABForm #fechaNotificacionSBCSeg").datepicker( { dateFormat: 'dd-mm-yy' ,
		onSelect: function(dateText, inst) { 
			jsValidaFecNotificacion();
		    }
	});
		$("form#seguimientoSBCTABForm #fechaNotificacionSBCSeg, form#seguimientoSBCTABForm #fechaAtencionSBCSeg").datepicker('option', 'maxDate', jsFechaMaxSeguimiento);
		$("form#seguimientoSBCTABForm #fechaNotificacionSBCSeg, form#seguimientoSBCTABForm #fechaAtencionSBCSeg").datepicker('option', 'minDate', jsFechaMinSeguimiento);
	
	
}

/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion que inicializa los datepicker de la pantalla invitacion
 */
function inicializaInvitacionSBC(){
	
	//var id = $("form#invitacionAntecedenteForm #cvePromocion").val();
	//jsMuestraInvitacionSBC(id);
	var regPatronalSbc = $("form#segimientoSBCForm #regPatronalSbsHdn").val();
	//alert("regPatronalSbc : " + regPatronalSbc);
	validarRegPatronalSBS(regPatronalSbc);
}


/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion que consulta con la cve_promocion las datos de la invitacion y llena la pantalla con los datos adquiridos
 */
function jsMuestraInvitacionSBC(obj){
	var id = obj;
	
	var sPromocion = '{' +
	   '"cveTemp":"'+id+'",'+
	   '"tipoPrograma":"promocion"}';		
	var promocion = jQuery.parseJSON(sPromocion);

	var url = getAppContextParaJS()+"/catalogo/invitacion/invitacionAntecedente.do";
	$.postJSON(url,promocion,function(data) {
		jsLimpiarGuardar();
		$("form#invitacionAntecedenteForm #labelFolioAntecedente").html('<label>' + data.folioAntecedente + '</label>');
		$("form#invitacionAntecedenteForm #labelRegPatronal").html('<label>' + data.regPatronal + '</label>');
		$("form#invitacionAntecedenteForm #labelNomRazonSocial").html('<label>' + data.razonSocial + '</label>');
		$("form#invitacionAntecedenteForm #cveDeteccion").val(data.cveDeteccion);
		$("form#invitacionAntecedenteForm #cvePromocion").val(data.cvePromocion);
		$("form#invitacionAntecedenteForm #tipoPrograma").val(data.tipoPrograma);
		$("form#invitacionAntecedenteForm #fechaIncialinv").val(data.fechaIncial);
		$("form#invitacionAntecedenteForm #fechaFinalInv").val(data.fechaFinal);	
		
		oDgInvitacionAntecedente.dialog('open');
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
		desbloquear();
	});			
}


/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion que Limpi los campos de la pantalla invitacion
 */
function jsLimpiarGuardar(){
	
	$("form#invitacionAntecedenteForm #cveDeteccion").val();
	$("form#invitacionAntecedenteForm #cvePromocion").val();
	$("form#invitacionAntecedenteForm #nuOficioinv").val();
	$("form#invitacionAntecedenteForm #fechaEmisionFec").val();
	$("form#invitacionAntecedenteForm #fechaIncialinv").val();
	$("form#invitacionAntecedenteForm #fechaFinalInv").val();
	
}

/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion que realiza las funcionalidades del seguimiento SBC cuando termina de ejecutarce el metodo de cancelacion
 */
function seguimientoSBCCancelacion(){	
	$("form#seguimientoSBCTABForm #labelFechaCancelacion").val($("form#cancelacionGenericoTabForm #fechaCancelacionCGT").val());
	setTabDesHabilitado('autAviDictamenGenericoTab_SBC');
	setTabDesHabilitado('cierrePorCotizarRGenericoTab_SBC');	
	setTabDesHabilitado('regularizarObraGenericoTAB_SBC');
	guardarSeguimientoGenerico();
	jsBloquearSeguimientoSBCTAB();
	jsBloquearSeguimientoCancelar();
	changeTab('seguimientoSBCTAB_SBC');
}

/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion que realiza las funcionalidades del seguimiento SBC cuando termina de ejecutarce el metodo de invitacion
 */
function seguimientoSBCInvitacion(){	
	$("form#seguimientoSBCTABForm #labelFechaOficioInv").val($("form#invitacionAntecedenteForm #fechaEmisionFec").val());
	$("form#seguimientoSBCTABForm #labelFechaDelSegSBC").val($("form#invitacionAntecedenteForm #fechaIncialinv").val());
	$("form#seguimientoSBCTABForm #labelFechaAlSegSBC").val($("form#invitacionAntecedenteForm #fechaFinalInv").val());		
	setTabDesHabilitado("cancelacionGenericoTab_SBC");	
	setTabDesHabilitado('autAviDictamenGenericoTab_SBC');
	setTabDesHabilitado('cierrePorCotizarRGenericoTab_SBC');	
	setTabDesHabilitado('regularizarObraGenericoTAB_SBC');
	guardarSeguimientoGenerico();
	jsBloquearSeguimientoSBCTAB();
	changeTab('seguimientoSBCTAB_SBC');
}

/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion que realiza las funcionalidades del seguimiento SBC cuando termina de ejecutarce el metodo de CierrePorCotizarR
 */
function seguimientoSBCCierrePorCotizarR(){
	
	$("form#seguimientoSBCTABForm #labelFechaCierreCotRaz").val($("form#cierrePorCotizarRGenericoTabForm #fecCotizRazCCRG").val());
	setTabDesHabilitado("cancelacionGenericoTab_SBC");	
	setTabDesHabilitado('autAviDictamenGenericoTab_SBC');
	setTabDesHabilitado('regularizarObraGenericoTAB_SBC');
	guardarSeguimientoGenerico();
	jsBloquearSeguimientoSBCTAB();
	jsBloquearSeguimientoCierreCotizarR();
	changeTab('seguimientoSBCTAB_SBC');
}

/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion que realiza las funcionalidades del seguimiento SBC cuando termina de ejecutarce el metodo de AutAviDict
 */
function seguimientoSBCAutAviDict(){
	$("form#seguimientoSBCTABForm #labelFechaAutAviso").val($("form#autAviDictamenGenericoTabForm #fecAvisoDictGenericoTab").val());
	$("form#seguimientoSBCTABForm #labelFechaDelSegSBC").val($("form#autAviDictamenGenericoTabForm #fecIniPeriodoDictGenericoTab").val());
	$("form#seguimientoSBCTABForm #labelFechaAlSegSBC").val($("form#autAviDictamenGenericoTabForm #fecFinPeriodoDictGenericoTab").val());	
	setTabDesHabilitado("cancelacionGenericoTab_SBC");	
	setTabDesHabilitado('cierrePorCotizarRGenericoTab_SBC');	
	setTabDesHabilitado('regularizarObraGenericoTAB_SBC');
	guardarSeguimientoGenerico();
	jsBloquearSeguimientoSBCTAB();
	jsBloquearSeguimientoAutAviDict();
	changeTab('seguimientoSBCTAB_SBC');
}

/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion que realiza las funcionalidades del seguimiento SBC cuando termina de ejecutarce el metodo de Regulariza pagos
 */
function seguimientoSBCRagulariza(){
	$("form#seguimientoSBCTABForm #labelFechaDelSegSBC").val($("form#regularizarObraGenericoTABForm #perRegularizaDel").val());
	$("form#seguimientoSBCTABForm #labelFechaAlSegSBC").val($("form#regularizarObraGenericoTABForm #perRegularizaAl").val());
	setTabDesHabilitado("cancelacionGenericoTab_SBC");	
	setTabDesHabilitado('autAviDictamenGenericoTab_SBC');
	setTabDesHabilitado('cierrePorCotizarRGenericoTab_SBC');		
	guardarSeguimientoGenerico();
	jsBloquearSeguimientoRegular();
	changeTab('seguimientoSBCTAB_SBC');
	jsBloquearSeguimientoSBCTAB();
}


/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion que realiza validaciones dentro de la pantalla SEGUIMIENTO SBC para la fecha de notificacion
 */
function jsValidaFecNotificacion(){
	
	$("form#seguimientoSBCTABForm #labelAlert").html('');
	 var fecIni = $("form#seguimientoSBCTABForm #fechaOficioSBC").val();
	 var fecFinal = $("form#seguimientoSBCTABForm #fechaNotificacionSBCSeg").val();
	 if(fecIni != '' && fecFinal != ''){
		 if(jsValidaFechas(fecIni,fecFinal)){
			 $("form#seguimientoSBCTABForm #fechaNotificacionSBCSeg").val(fecFinal);
			 $('form#seguimientoSBCTABForm #btnLimpiaFechaNotif').show();
			 $("form#seguimientoSBCTABForm #labelAlert").html('');
			 $('form#seguimientoSBCTABForm #fechaNotificacionSBCSeg').removeClass("red");
			 $("form#seguimientoSBCTABForm #fechaAtencionSBCSeg").attr("disabled",false);
			 $('form#seguimientoSBCTABForm #fechaAtencionSBCSeg').addClass("red");
			
			 //No se habilita hasta que se haya guardado la promocion
			 //$("form#seguimientoSBCTABForm #butonSBCInv").attr("disabled",false);
			 
			 $("form#regularizarObraGenericoTABForm #fechaNotificacionHdn").val(fecFinal); 
			 // Manda fecha a la cancelacion
			 $("form#cancelacionGenericoTabForm #fechaNotificacionOficioGenerico").val(fecFinal);
			 $("form#invitacionAntecedenteForm #fechaOfInvitacionTxInv").val(fecFinal);
			 // manda la fecha a aviso dictamen
			 $("form#autAviDictamenGenericoTabForm #fechaNotificacionOficioDictamenGenerico").val(fecFinal);
			 $('form#seguimientoSBCTABForm #btnLimpiarSBCTAB').prop('disabled',false);
			 $('form#seguimientoSBCTABForm #btnGuardarSBCTAB').prop('disabled',false);
			 // manda a la invitacion
			 $("form#invitacionAntecedenteForm #fechaNotificacionInv").val(fecFinal);
		 }else{ 
			 $('form#seguimientoSBCTABForm #btnLimpiaFechaNotif').hide();
			 $("form#seguimientoSBCTABForm #fechaAtencionSBCSeg").val("");
			 $('form#seguimientoSBCTABForm #fechaAtencionSBCSeg').removeClass("red");
			 $("form#seguimientoSBCTABForm #fechaAtencionSBCSeg").attr("disabled",true);
			 $('form#seguimientoSBCTABForm #btnLimpiaFechaAtencion').hide();
			 $("form#seguimientoSBCTABForm #butonSBCInv").attr("disabled",true);
			 $("form#seguimientoSBCTABForm #fechaNotificacionSBCSeg").val('');
			 $('form#seguimientoSBCTABForm #fechaNotificacionSBCSeg').addClass("red");
			 $("form#autAviDictamenGenericoTabForm #fechaNotificacionOficioDictamenGenerico").val('');
			 $("form#seguimientoSBCTABForm #labelAlert").html('<label class="etiquetaError">La fecha de notificaci&oacute;n no puede ser menor a la fecha del Oficio Promoci&oacute;n </label>');
			 // manda a la invitacion
			 $("form#invitacionAntecedenteForm #fechaNotificacionInv").val('');
		 }
	 }
	
}

/**
 * 
 *  Funcion que realiza validaciones dentro de la pantalla SEGUIMIENTO SBC para la fecha de Atencion
 */
function jsValidaFecAtencion(){
	
	$("form#seguimientoSBCTABForm #labelAlert").html('');
	
	 var fecIni = $("form#seguimientoSBCTABForm #fechaNotificacionSBCSeg").val();
	 var fecFinal = $("form#seguimientoSBCTABForm #fechaAtencionSBCSeg").val();
	 if(fecIni != '' && fecFinal != ''){
		 if(jsValidaFechas(fecIni,fecFinal)){
			 $('form#seguimientoSBCTABForm #fechaAtencionSBCSeg').removeClass("red");
			 $("form#seguimientoSBCTABForm #fechaAtencionSBCSeg").val(fecFinal);
			 $('form#seguimientoSBCTABForm #btnLimpiaFechaAtencion').show();			 
			 $("form#seguimientoSBCTABForm #labelAlert").html('');
			if(flagPermiso){
				 setTabHabilitado('autAviDictamenGenericoTab_SBC');
				// setTabHabilitado('cierrePorCotizarRGenericoTab_SBC');
				 setTabDesHabilitado('cancelacionGenericoTab_SBC');	
			}
			 //No se habilite hasta que se haya guardado la promocion
			// $("form#seguimientoSBCTABForm #butonSBCInv").attr("disabled",true);
			
			 
			 $("form#seguimientoSBCTABForm #banderaSBC").attr("disabled",false);
			 $("form#seguimientoSBCTABForm #banderaSBC").addClass("red");
			 $("form#cierrePorCotizarRGenericoTabForm #fechaAtencionGenericaCCRG").val(fecFinal);
			 $('form#seguimientoSBCTABForm #btnLimpiarSBCTAB').prop('disabled',false);
			 $('form#seguimientoSBCTABForm #btnGuardarSBCTAB').prop('disabled',false);
		 }else{
			 $('form#seguimientoSBCTABForm #btnLimpiaFechaAtencion').hide();
			 $("form#seguimientoSBCTABForm #fechaAtencionSBCSeg").val('');
			 setTabDesHabilitado('autAviDictamenGenericoTab_SBC');
			 setTabDesHabilitado('cierrePorCotizarRGenericoTab_SBC');
			 	if($("form#seguimientoSBCTABForm #rolSBCSeguimiento").val() != "NP"){
					setTabHabilitado('cancelacionGenericoTab_SBC');	
				}else{
					setTabDesHabilitado('cancelacionGenericoTab_SBC');	
				}
			 	//No se habilite hasta que se haya guardado la promocion
			// $("form#seguimientoSBCTABForm #butonSBCInv").attr("disabled",true);
			 $("form#seguimientoSBCTABForm #labelAlert").html('<label class="etiquetaError">La fecha de Atenci&oacute;n no puede ser mayor a la fecha de notificaci&oacute;n</label>');
			 $("form#cierrePorCotizarRGenericoTabForm #fechaAtencionGenericaCCRG").val('');
		 }
	 }
}


/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion que valida si el check de RP esta habilitado
 */
function jsValidaRPSBC(valor){
	if(valor == 'rp'){
		$("form#seguimientoSBCTABForm #banderaSBC").attr('checked', 'checked');
		   inicializaDialogConfirmarRP();	
		}
}

/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion que inicializa el dialogo de RP
 */
function inicializaDialogConfirmarRP(){
	oDgConfirmarRP = $(idDgConfirmarRP).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 700,
		closeOnEscape: false,
		buttons: {
			   "Si": function() { 
					jsFuncionRPPestana();
					setTabHabilitado('regularizarObraGenericoTAB_SBC');
					guardaSeguimientoSBCRegulariza();
					jsBloquearSeguimientoSBCTAB();
					changeTab('regularizarObraGenericoTAB_SBC');
					$("form#seguimientoSBCTABForm #banderaSBC").attr('checked', 'checked');
					$("form#seguimientoSBCTABForm #banderaSBC").removeClass("red");
					$("form#seguimientoSBCTABForm #banderaSBC").attr('disabled', 'disabled');
					$(this).dialog("close"); 
					return true;
							
			}, "No": function(){
				$("form#seguimientoSBCTABForm #banderaSBC").removeAttr('checked');
				$("form#seguimientoSBCTABForm #banderaSBC").addClass("red");
				$(this).dialog("close"); 
				return false;
			} 
		}
	});
	oDgConfirmarRP.dialog('open');
	return true;
//	$("form#seguimientoSBCTABForm #banderaSBC").attr('checked', true);
}

/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion que manda ejecutar el metodo de Guardar seguimiento SBC
 */
function guardaSeguimientoSBC(){
	
	oDgConfirmarSBCTab = $(idDgConfirmarSBCTab).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 700,
		closeOnEscape: false,
		buttons: {
			   "Si": function() { 
				   guardarSeguimientoGenerico();
				   if($("form#seguimientoSBCTABForm #fechaNotificacionSBCSeg").val() != ''){
					   jsFechaNotificacionData();
					   oDgConfirmarSBCTab.dialog("close");
				   }
				   if($("form#seguimientoSBCTABForm #fechaNotificacionSBCSeg").val() != ''&&
					  $("form#seguimientoSBCTABForm #fechaAtencionSBCSeg").val() != ''){
					   jsfechaAtencionData();
				   }
				   
							
			}, "No": function(){
				$(this).dialog("close"); 
			} 
		}
	});
	
	oDgConfirmarSBCTab.dialog("open");
	
	
}

function guardarSeguimientoGenerico(){
	 var promocion = $("#seguimientoSBCTABForm").serializeObject(true);
	   bloquear();
	   $.postJSON(getAppContextParaJS()+ "/promocion/seguimiento/generico/GuardarSeguimientoSBC.do", promocion, function(data) {
		   if($("form#seguimientoSBCTABForm #fechaAtencionSBCSeg").val()==""){
			   $("form#seguimientoSBCTABForm #butonSBCInv").attr("disabled",false);
		   }
		}).error(function(datas){ 
			validarSesionExpirada(datas);
		}).complete(function(){
			
			desbloquear();
		});	
}

/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion que manda ejecutar el metodo de Guardar seguimiento SBC
 */
function guardaSeguimientoSBCRegulariza(){
	
	 var promocion = $("#seguimientoSBCTABForm").serializeObject(true);
	   bloquear();
	   $.postJSON(getAppContextParaJS()+ "/promocion/seguimiento/generico/GuardarSeguimientoSBCRegulariza.do", promocion, function(data) {	
		}).error(function(datas){ 
			validarSesionExpirada(datas);
		}).complete(function(){
			desbloquear();
		});	
}

/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion que manda a la pestaña de Pagos y deshabilita las demas pestañas
 */
function jsFuncionRPPestana(){
	setTabDesHabilitado('cancelacionGenericoTab_SBC');
	setTabDesHabilitado('autAviDictamenGenericoTab_SBC');
	setTabDesHabilitado('cierrePorCotizarRGenericoTab_SBC');
	guardarSeguimientoGenerico();
	setTabHabilitado('regularizarObraGenericoTAB_SBC');
	//jsBloquearSeguimientoSBCTAB();
	oDgConfirmarRP.dialog("close");
	changeTab('regularizarObraGenericoTAB_SBC');
	
}

/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion que bloquea todos los campos de la pantalla Seguimiento SBC
 */
function jsBloquearSeguimientoSBCTAB(){
	
	$('form#seguimientoSBCTABForm input[type=text]').prop('disabled','disabled');
	$('form#seguimientoSBCTABForm input[type=button]').prop('disabled','disabled');
	$('form#seguimientoSBCTABForm input[type=checkbox]').prop('disabled','disabled');
	$('form#seguimientoSBCTABForm #txObservacionesSBCTAB').prop('disabled',true);
	$("form#seguimientoSBCTABForm #txObservacionesSBCTAB").removeClass("red");
	$("form#seguimientoSBCTABForm #fechaNotificacionSBCSeg").removeClass("red");
	$("form#seguimientoSBCTABForm #fechaAtencionSBCSeg").removeClass("red");
	$("form#seguimientoSBCTABForm #btnLimpiaFechaNotif").hide();
	$("form#seguimientoSBCTABForm #btnLimpiaFechaAtencion").hide();
	$("form#seguimientoSBCTABForm #labelFechaDelSegSBC").prop('disabled',false);
	$("form#seguimientoSBCTABForm #labelFechaAlSegSBC").prop('disabled',false);
	$("form#seguimientoSBCTABForm #labelFechaDelSegSBC").prop('readonly',true);
	$("form#seguimientoSBCTABForm #labelFechaAlSegSBC").prop('readonly',true);
	
}

/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion que desbloquea todos los campos de la pantalla Seguimiento SBC
 */
function jsDesBloquearSeguimientoSBCTAB(){
	
	$('form#seguimientoSBCTABForm input[type=text]').prop('disabled',false);
	$('form#seguimientoSBCTABForm input[type=button]').prop('disabled',false);
	$('form#seguimientoSBCTABForm input[type=checkbox]').prop('disabled',false);
	$('form#seguimientoSBCTABForm #txObservacionesSBCTAB').prop('disabled',false);
	$('form#seguimientoSBCTABForm #fechaAtencionSBCSeg').prop('disabled',true);
	$("form#seguimientoSBCTABForm #txObservacionesSBCTAB").addClass("red");
	
}

/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion que bloquea todos los campos de la pantalla Seguimiento SBC - cabcelar
 */
function jsBloquearSeguimientoCancelar(){
	$('form#cancelacionGenericoTabForm input[type=text]').prop('disabled','disabled');
	$('form#cancelacionGenericoTabForm input[type=button]').prop('disabled','disabled');
	$('form#cancelacionGenericoTabForm input[type=select]').prop('disabled','disabled');
	$('form#cancelacionGenericoTabForm input[type=text]').removeClass("red");
	$('form#cancelacionGenericoTabForm input[type=select]').removeClass("red");
}

/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion que desbloquea todos los campos de la pantalla Seguimiento SBC - cancelar
 */
function jsDesBloquearSeguimientoCancelar(){
	$('form#cancelacionGenericoTabForm input[type=text]').prop('disabled',false);
	$('form#cancelacionGenericoTabForm input[type=button]').prop('disabled',false);
	$('form#cancelacionGenericoTabForm input[type=select]').prop('disabled',false);
	$('form#cancelacionGenericoTabForm #funcionarioAutorizaCGT').prop('disabled',false);
	$('form#cancelacionGenericoTabForm #funcionarioAutorizaCGT').find('option').each(function() {$(this).prop("disabled", "disabled") });
	$('form#cancelacionGenericoTabForm #cancelacionGenericoTabVO\\.cveMotivoCancelacion').find('option').each(function() {$(this).prop("disabled", "disabled") });	
}

/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion que bloquea todos los campos de la pantalla Seguimiento SBC - AviDict
 */
function jsBloquearSeguimientoAutAviDict(){
	$('form#autAviDictamenGenericoTabForm input[type=text]').prop('disabled','disabled');
	$('form#autAviDictamenGenericoTabForm input[type=button]').prop('disabled','disabled');
	habilitaCombosCancelacionCGT();
}

/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion que desbloquea todos los campos de la pantalla Seguimiento SBC - AviDict
 */
function jsDesBloquearSeguimientoAutAviDict(){
	$('form#autAviDictamenGenericoTabForm input[type=text]').prop('disabled',false);
	$('form#autAviDictamenGenericoTabForm input[type=button]').prop('disabled',false);
}

/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion que bloquea todos los campos de la pantalla Seguimiento SBC - CierreCotizarR
 */
function jsBloquearSeguimientoCierreCotizarR(){
	$('form#cierrePorCotizarRGenericoTabForm input[type=text]').prop('disabled','disabled');
	$('form#cierrePorCotizarRGenericoTabForm input[type=button]').prop('disabled','disabled');
}

/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion que desbloquea todos los campos de la pantalla Seguimiento SBC - CierreCotizarR
 */
function jsDesBloquearSeguimientoCierreCotizarR(){
	$('form#cierrePorCotizarRGenericoTabForm input[type=text]').prop('disabled',false);
	$('form#cierrePorCotizarRGenericoTabForm input[type=button]').prop('disabled',false);
}

/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion que bloquea todos los campos de la pantalla Seguimiento SBC - Regula Pagos
 */
function jsBloquearSeguimientoRegular(){
	$('form#seguimientoSBCTABForm input[type=text]').prop('disabled','disabled');
	$('form#seguimientoSBCTABForm input[type=text]').removeClass("red");
	$('form#seguimientoSBCTABForm input[type=button]').prop('disabled','disabled');
}

/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion que desbloquea todos los campos de la pantalla Seguimiento SBC - Regula Pagos
 */
function jsDesBloquearSeguimientoRegular(){
	$('form#seguimientoSBCTABForm input[type=text]').prop('disabled',false);
	$('form#seguimientoSBCTABForm input[type=button]').prop('disabled',false);
}

/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion que limpia los campos del seguimiento lismpiando  y desbloqueando todos los campos de todas las pestañas
 */
function jsLimpiarFormaSeguimientoSBC(){
	limpiarFormulario("#seguimientoSBCTABForm");
	limpiarFormulario("#cancelacionGenericoTabForm");
	limpiarFormulario("#autAviDictamenGenericoTabForm");
	limpiarFormulario("#seguimientoSBCTABForm");
	limpiarFormulario("#seguimientoSBCTABForm");
	$('seguimientoSBCTAB_SBC,form#seguimientoSBCTABForm,#labelFechaDelSegSBC').val('');
	$('seguimientoSBCTAB_SBC,form#seguimientoSBCTABForm,#labelFechaAlSegSBC').val('');
	jsDesBloquearSeguimientoSBCTAB();
	jsDesBloquearSeguimientoCancelar();
	jsDesBloquearSeguimientoAutAviDict();
	jsDesBloquearSeguimientoCierreCotizarR();
	jsDesBloquearSeguimientoRegular();
}

/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion que limpia los campos capturados en pantalla
 */
function limpiarSeguimientoTABSBC(){
	limpiarFormulario("#seguimientoSBCTABForm");
	$("form#seguimientoSBCTABForm #labelAlert").html('');
	$('form#seguimientoSBCTABForm #fechaAtencionSBCSeg').removeClass("red");
	$('form#seguimientoSBCTABForm #fechaNotificacionSBCSeg').addClass("red");
	jsBloqueaStatus28();
}


function jsBloqueaStatus28(){
	limpiarFormulario("#seguimientoSBCTABForm");
	limpiarFormulario("#cancelacionGenericoTabForm");
	limpiarFormulario("#autAviDictamenGenericoTabForm");
	limpiarFormulario("#cancelacionGenericoTabForm");
	limpiarFormulario("#regularizarObraGenericoTABForm");
	jsDesBloquearSeguimientoSBCTAB();
	jsDesBloquearSeguimientoCancelar();
	$('form#seguimientoSBCTABForm #fechaNotificacionSBCSeg').addClass("red");
	$('form#seguimientoSBCTABForm #fechaAtencionSBCSeg').removeClass("red");
	$("form#seguimientoSBCTABForm #fechaAtencionSBCSeg").attr("disabled",true);
	$('form#seguimientoSBCTABForm #btnLimpiaFechaAtencion').hide();
	$("form#seguimientoSBCTABForm #butonSBCInv").attr("disabled",true);
	$("form#seguimientoSBCTABForm #banderaSBC").attr("disabled",'disabled');
	$('form#seguimientoSBCTABForm #btnLimpiarSBCTAB').prop('disabled',true);
	$('form#seguimientoSBCTABForm #btnGuardarSBCTAB').prop('disabled',false);
	$('form#seguimientoSBCTABForm #btnLimpiaFechaNotif').hide();
	setTabDesHabilitado("autAviDictamenGenericoTab_SBC");	
	setTabDesHabilitado('cierrePorCotizarRGenericoTab_SBC');
	setTabDesHabilitado('regularizarObraGenericoTAB_SBC');	
	
}

function jsBloqueaStatus32(){
	limpiarFormulario("#seguimientoSBCTABForm");
	limpiarFormulario("#cancelacionGenericoTabForm");
	limpiarFormulario("#autAviDictamenGenericoTabForm");
	limpiarFormulario("#cancelacionGenericoTabForm");
	limpiarFormulario("#regularizarObraGenericoTABForm");
	jsDesBloquearSeguimientoSBCTAB();
	jsDesBloquearSeguimientoCancelar();
	$("form#seguimientoSBCTABForm #fechaNotificacionSBC").attr("disabled",true);
	$("form#seguimientoSBCTABForm #fechaAtencionSBCSeg").attr("disabled",true);
	$('form#seguimientoSBCTABForm #btnLimpiaFechaAtencion').hide();
	$("form#seguimientoSBCTABForm #butonSBCInv").attr("disabled",true);
	$("form#seguimientoSBCTABForm #banderaSBC").attr("disabled",false);	
	$('form#seguimientoSBCTABForm #btnLimpiarSBCTAB').prop('disabled',true);
	$('form#seguimientoSBCTABForm #btnGuardarSBCTAB').prop('disabled',false);
	$('form#seguimientoSBCTABForm #btnLimpiaFechaNotif').show();	
	setTabDesHabilitado('autAviDictamenGenericoTab_SBC');
	setTabDesHabilitado('cierrePorCotizarRGenericoTab_SBC');	
	$("form#seguimientoSBCTABForm #banderaSBC").attr('checked', true);
	setTabDesHabilitado('cancelacionGenericoTab_SBC');
	
	changeTab('seguimientoSBCTAB_SBC');
}

function jsLimpiaFechaNotif(){
	$('form#seguimientoSBCTABForm #fechaNotificacionSBCSeg').val('');
	$("form#autAviDictamenGenericoTabForm #fechaNotificacionOficioDictamenGenerico").val('');
	$('form#seguimientoSBCTABForm #btnLimpiaFechaNotif').hide();	
	$('form#seguimientoSBCTABForm #fechaAtencionSBCSeg').prop('disabled',true);	
	$('form#seguimientoSBCTABForm #fechaAtencionSBCSeg').val('');
	$('form#seguimientoSBCTABForm #btnLimpiaFechaAtencion').hide();
	$("form#seguimientoSBCTABForm #butonSBCInv").attr("disabled",true);
	 $("form#cancelacionGenericoTabForm #fechaNotificacionOficioGenerico").val("");
	 $('form#seguimientoSBCTABForm #fechaNotificacionSBCSeg').addClass("red");
	 $('form#seguimientoSBCTABForm #fechaAtencionSBCSeg').removeClass("red");
	setTabDesHabilitado('autAviDictamenGenericoTab_SBC');
	setTabDesHabilitado('cierrePorCotizarRGenericoTab_SBC');
	setTabDesHabilitado('regularizarObraGenericoTAB_SBC');
	if($("form#seguimientoSBCTABForm #rolSBCSeguimiento").val() != "NP"){
		setTabHabilitado('cancelacionGenericoTab_SBC');	
	}else{
		setTabDesHabilitado('cancelacionGenericoTab_SBC');	
	}
}


function jsLimpiaFechaAtencion(){
	$('form#seguimientoSBCTABForm #fechaAtencionSBCSeg').val('');
	$('form#seguimientoSBCTABForm #btnLimpiaFechaAtencion').hide();
	$("form#seguimientoSBCTABForm #butonSBCInv").attr("disabled",false);
	$('form#seguimientoSBCTABForm #fechaAtencionSBCSeg').addClass("red");
	$("form#seguimientoSBCTABForm #banderaSBC").attr("disabled",'disabled');
	$("form#seguimientoSBCTABForm #banderaSBC").removeClass("red");
	setTabDesHabilitado('autAviDictamenGenericoTab_SBC');
	setTabDesHabilitado('cierrePorCotizarRGenericoTab_SBC');
	setTabDesHabilitado('regularizarObraGenericoTAB_SBC');	
	if($("form#seguimientoSBCTABForm #rolSBCSeguimiento").val() != "NP"){
		setTabHabilitado('cancelacionGenericoTab_SBC');	
	}else{
		setTabDesHabilitado('cancelacionGenericoTab_SBC');	
	}
}

function jsFechaNotificacionData(){
	
	$("form#seguimientoSBCTABForm #labelAlert").html('');
	$('form#seguimientoSBCTABForm #fechaNotificacionSBCSeg').removeClass("red");
	$("form#seguimientoSBCTABForm #fechaAtencionSBCSeg").attr("disabled",false);
	$('form#seguimientoSBCTABForm #fechaAtencionSBCSeg').addClass("red");
	$("form#seguimientoSBCTABForm #butonSBCInv").attr("disabled",false);	
	$('form#seguimientoSBCTABForm #btnLimpiarSBCTAB').prop('disabled',false);
	$('form#seguimientoSBCTABForm #btnGuardarSBCTAB').prop('disabled',false);
	$('form#seguimientoSBCTABForm #btnLimpiaFechaNotif').hide();
	$("form#seguimientoSBCTABForm #fechaNotificacionSBCSeg").prop('disabled',true);						

}

function jsfechaAtencionData(){
	$('form#seguimientoSBCTABForm #fechaAtencionSBCSeg').removeClass("red");
	$("form#seguimientoSBCTABForm #labelAlert").html('');
	 setTabHabilitado('autAviDictamenGenericoTab_SBC');
	 setTabHabilitado('cierrePorCotizarRGenericoTab_SBC');
	 $("form#seguimientoSBCTABForm #butonSBCInv").attr("disabled",true);
	 $("form#seguimientoSBCTABForm #banderaSBC").attr("disabled",false);
	 $('form#seguimientoSBCTABForm #btnLimpiarSBCTAB').prop('disabled',false);
	 $('form#seguimientoSBCTABForm #btnGuardarSBCTAB').prop('disabled',false);
	 $('form#seguimientoSBCTABForm #btnLimpiaFechaAtencion').hide();
	 $("form#seguimientoSBCTABForm #fechaAtencionSBCSeg").prop('disabled',true);
	 setTabDesHabilitado('cancelacionGenericoTab_SBC');	
}

function validarRegPatronalSBS(registroPatronal) {
	var respuesta = false;
	registroPatronal = registroPatronal.substring(0,10);
	if(registroPatronal != '' && registroPatronal.length == 10){							 
		 bloquear();
			$.postJSON(jsContextoPromocion+"seguimiento/generico/validaPatron.do",registroPatronal,function(data){ 
				
				if(data == null){

					alert("El registro patronal no es v&aacute;lido");
					respuesta = false;
				}
				else if(data != null && data.cveRespuestaWS <= JSERROR_WS){
						alert(data.descRespuestaWS );
						respuesta = false;
					 } else if(data.razonSocial != null){
						 nombre = data.razonSocial;
						 respuesta=true;
						 var id = $("form#invitacionAntecedenteForm #cvePromocion").val();
						 jsMuestraInvitacionSBC(id);
						 
					 }					

			}).error(function(data){ 
				alert('Ocurri\u00F3 un error al consultar al patr\u00F3n, intentelo nuevamente por favor');
				validarSesionExpirada(data);
				//alert("respuesta1:: " + respuesta);
				return respuesta;
			}).complete(function(){
				//alert("respuesta2= " + respuesta);
				desbloquear();	
				return respuesta;
			});
	 }
	
	
	
}
