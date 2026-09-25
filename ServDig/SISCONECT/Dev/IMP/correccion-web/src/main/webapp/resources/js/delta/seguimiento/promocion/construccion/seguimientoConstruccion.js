	/**
	 * indica el tab seleccionado
	 */
	activeTab = '#seguimientoTAB_Construccion';
	
	/**
	 * Indica la forma seleccionada
	 */
	FORMA_ACTUAL ='seguimientoConstruccionTABForm';

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

var oDgDatosConstruccion;
var idDatosConstruccion   = "#dgPromocionDatosCostruccion";

var idDgConfirmarRP = "#dgConfirmarRegularizaObra";
var oDgConfirmarRP;

var idDgConfirmarTAB = "#dgConfirmarSaticB";
var oDgConfirmarTAB;

var jsRolSeguimientoExCon="form#seguimientoConstruccionTABForm #rolSeguimientoEX";
var URL = getAppContextParaJS() + "/promocion/consulta";


var JEFE_OF_CORRECCION = 4;
var JEFE_OF_CORR_Y_DIC = 6;
var JEFE_DEP_AUD_PAT = 7;
/**
 * Controla el valor del hidden el cual indica
 * en que secci�n se encuentra el usuario.
 * Esta variable es utilizada por JAVA.
 */
function setToFormSeccionActual(){
	
	}

/**
 * @author Enrique Duran Jimenez
 * @since 10/07/2012
 * Funcion que inicializa el dialogo principal para el seguimiento de Construccion
 */
function inicializaDialogConstruccion(){
	
	oDgDatosConstruccion = $(idDatosConstruccion).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 980,
		closeOnEscape: false,	
		beforeClose :function(event,ui){
			jsLimpiarFormaSeguimientoEX();
			oDgConsultaRegistros.fnDraw();
			// elimina el domicilio de la sesion
			$.postJSON(jsContextoPromocion+"seguimiento/generico/removerDomicilioSession.do", null, function(data) {					
			}).error(function(data){ 					
				validarSesionExpirada(data);
			}).complete(function(){
				//Instrucciones para el complete													
			});		
			
		},
		buttons: {
			"Regresar": function() { 
				$(this).dialog("close"); 								
			} 
		}
	});
	
	mostrarConstruccion();
}

/**
 * @author Enrique Duran Jimenez
 * @since 10/07/2012
 * Funcion que inicializa el dialogo consfirmar
 */
function inicializaDialogConfirmarTAB(){
	if($("form#segimientoConstruccionForm #regPatronalSegConstruccion").val() != '' && $("form#segimientoConstruccionForm #regPatronalValidar").val() == ''){
		alert('Favor de validar el registro patronal ingresado');
	}else{
		oDgConfirmarTAB = $(idDgConfirmarTAB).dialog({
			autoOpen: false,
			modal:true,
			resizable:false,
			width: 980,
			closeOnEscape: false,	
			buttons: {
				"Aceptar": function(){
					guardarSeguimientoEX();
					$("form#segimientoConstruccionForm #btnLimpiaRPEX").hide();	
					if($("form#seguimientoConstruccionTABForm #fechaNotificacionConstruccionSeg").val() != ''){
						jsFechaNotificacionEXData();
						oDgConfirmarTAB.dialog("close");
					}
					if($("form#seguimientoConstruccionTABForm #fechaNotificacionConstruccionSeg").val() != '' &&
					   $("form#seguimientoConstruccionTABForm #fechaAtencionConstruccion").val() != ''){
						jsFechaNotificacionEXData();
						jsFechaAtencionEXData();
					}
					if($("form#seguimientoConstruccionTABForm #afil15Construccion").val() != ''){
						jsAfil15EXData();
					}
				},
				"Regresar": function() { 
					$(this).dialog("close"); 								
				} 
			}
		});
		
		oDgConfirmarTAB.dialog("open");
	}	
}

/**
 * @author Enrique Duran Jimenez
 * @since 10/07/2012
 * Funcion consulta con la cve_promocion seleccionada y llena la informacion en la pantalla
 */
function mostrarConstruccion(){
	var idPromocion = $('#:checked').val();
	if(idPromocion != null){
		var sPromocion = '{"cvePromocion":'+idPromocion+'}';
		var promocion = jQuery.parseJSON(sPromocion);
		// Buscamos el elemento
		$.postJSON(URL + "/muestraConstruccion.do", promocion, function(data) {
			if(data != null){
				$("#regPatronalSegConstruccion").removeAttr("disabled");
				$('#btnValidarRegPatronal').removeAttr("disabled");
				$("#accessTabs").val("seguimientoTAB_Construccion-cancelacionGenericoTab_Construccion-derivarSubdelegacionGenericoTab_Construccion-derivarFiscalizacionGenericoTab_Construccion-autAviDictamenGenericoTab_Construccion-cierrePorCotizarRGenericoTab_Construccion-regularizarObraGenericoTAB_Construccion");
				//setTabDesHabilitado("autAviDictamenGenericoTab_Construccion");
				setTabDesHabilitado('derivarFiscalizacionGenericoTab_Construccion');
				setTabDesHabilitado('derivarSubdelegacionGenericoTab_Construccion');
				// se oculta el boton de generar invitacion
				$('form#seguimientoConstruccionTABForm #butonConstruccionInv').hide();
				//data.rolUsuario=4; // *** borrar ***
				if(data.rolUsuario == JEFE_OF_CORRECCION || data.rolUsuario == JEFE_OF_CORR_Y_DIC || data.rolUsuario == JEFE_DEP_AUD_PAT){
					$(jsRolSeguimientoExCon).val("rol");
				}								
				$("form#segimientoConstruccionForm #cvePromocion").val(data.cvePromocion);
				$("form#segimientoConstruccionForm #labelCriterioSeleccion").html('<label >' + data.descCriterioseleccion + '</label>');
				$("form#segimientoConstruccionForm #labelFolio").html('<label >' + data.nuFoliopromocion + '</label>');
				$("form#segimientoConstruccionForm #labelFechaOficio").html('<label >' + data.fechaOficio + '</label>');
				$("form#segimientoConstruccionForm #fechaOficioConstruccion").val(data.fechaOficio);				
				$("form#segimientoConstruccionForm #labelNumeroOficio").html('<label >' + data.nuOficiopro + '</label>');
				$("form#regularizarObraGenericoTABForm #fechaNotificacionHdn").val(data.fechaNotificacion); 
				if(data.regPatron != null){
					var registroPatronal = data.regPatron;
					registroPatronal = registroPatronal.substring(0,10);
					$("form#segimientoConstruccionForm #regPatronalSegConstruccion").val(registroPatronal);
					$("form#seguimientoConstruccionTABForm #regPatronConstruccion").val(data.cveFkPatron);
					$("form#segimientoConstruccionForm #regPatronalValidar").val(registroPatronal);
					if(data.fechaAtencion != null) {
						$('form#segimientoConstruccionForm #regPatronalSegConstruccion').prop('disabled','disabled');
						$('form#segimientoConstruccionForm #btnValidarRegPatronal').prop('disabled','disabled');
						$('form#segimientoConstruccionForm #btnLimpiaRPEX').hide();
					}
					if ($(jsRolSeguimientoExCon).val()=="rol") {
						if(data.fechaNotificacion!=null){
							setTabHabilitado('autAviDictamenGenericoTab_Construccion');
							setTabHabilitado('derivarFiscalizacionGenericoTab_Construccion');
						}						
						setTabHabilitado('derivarSubdelegacionGenericoTab_Construccion');						
					}
				}else{
					
					$("form#segimientoConstruccionForm #regPatronalSegConstruccion").val('');
					$("form#seguimientoConstruccionTABForm #regPatronConstruccion").val('');
					$("form#segimientoConstruccionForm #regPatronalValidar").val('');
					setTabDesHabilitado('autAviDictamenGenericoTab_Construccion');
				}
				if( data.razonSocial != null){
					$("form#segimientoConstruccionForm #labelRazonSocial").html('<label >' + data.razonSocial + '</label>');
				}	
				
				$("form#segimientoConstruccionForm #labelCalle").html('<label >' + data.domCalle + '</label>');
				$("form#segimientoConstruccionForm #labelColonia").html('<label >' + data.refColonia + '</label>');
				if(data.numNroext != null){
					$("form#segimientoConstruccionForm #labelNumExt").html('<label >' + data.numNroext + '</label>');
				}
				if(data.numNroint != null){
					$("form#segimientoConstruccionForm #labelNumInt").html('<label >' + data.numNroint + '</label>');
				}							
				$("form#segimientoConstruccionForm #labelCP").html('<label >' + data.numCodigopostal + '</label>');
				
				
				// Datos a regularizar promocion
				$("form#regularizarObraGenericoTABForm #cvePromocion").val(data.cvePromocion);
				$("form#regularizarObraGenericoTABForm #registroPatronalPagosDt").val(data.regPatron);
				$("form#regularizarObraGenericoTABForm #functionAuxRegularizaObra").val("seguimientoEXRegulariza()");
				initTabRegularizarObra();		
				
				// Status 28
				$("form#seguimientoConstruccionTABForm #labelAlert").html('');
				if(data.cveEstatus == '28'){
					jsBloquearStatus28();
					if(data.fechaNotificacion != null){
						 $("form#seguimientoConstruccionTABForm #fechaNotificacionConstruccionSeg").prop('disabled','disabled');
						 $("form#seguimientoConstruccionTABForm #fechaNotificacionConstruccionSeg").val(data.fechaNotificacion);
						 $('form#seguimientoConstruccionTABForm #fechaNotificacionConstruccionSeg').removeClass("red");
						 $('form#seguimientoConstruccionTABForm #btnLimpiaFechaNotifConstruccion').hide();
						 $("form#seguimientoConstruccionTABForm #labelAlert").html('');						 
//						 if($(jsRolSeguimientoExCon).val() == "rol"){
//								setTabHabilitado('cancelacionGenericoTab_Construccion');
//								setTabHabilitado('derivarFiscalizacionGenericoTab_Construccion');
//								setTabHabilitado('derivarSubdelegacionGenericoTab_Construccion');
//							}else{
//								setTabDesHabilitado('cancelacionGenericoTab_Construccion');
//								setTabDesHabilitado('derivarFiscalizacionGenericoTab_Construccion');
//								setTabDesHabilitado('derivarSubdelegacionGenericoTab_Construccion');
//							}
						 
						 $('form#seguimientoConstruccionTABForm #fechaAtencionConstruccion').prop('disabled',false);
						 $('form#seguimientoConstruccionTABForm #fechaAtencionConstruccion').addClass("red");
						 
						 $('form#seguimientoConstruccionTABForm #butonConstruccionInv').prop('disabled',false);
						 // Enviar a las demas pantallas
						 // Manda fecha a la cancelacion
						 $("form#cancelacionGenericoTabForm #fechaNotificacionOficioGenerico").val(data.fechaNotificacion);
						 // Manda a la invitacion
						 $("form#invitacionAntecedenteForm #fechaNotificacionInv").val(data.fechaNotificacion);
						 // manda la fecha a aviso dictamen
						 $("form#autAviDictamenGenericoTabForm #fechaNotificacionOficioDictamenGenerico").val(data.fechaNotificacion);
						 // fiscalizacion
						 $("form#derivarFiscalizacionTABForm #fechaNotificacionOficioFiscalizacion").val(data.fechaNotificacion);
						 // SUBDELEGACION
						 $("form#derivarSubdelegacionGenericoTabForm #fechaNotificacionOficioGenerico").val(data.fechaNotificacion);
					}if(data.fechaNotificacion != null && data.fechaAtencion != null){
						 $("form#seguimientoConstruccionTABForm #fechaAtencionConstruccion").prop('disabled','disabled');
						 $("form#seguimientoConstruccionTABForm #fechaAtencionConstruccion").val(data.fechaAtencion);
						 $('form#seguimientoConstruccionTABForm #fechaAtencionConstruccion').removeClass("red");
						 $('form#seguimientoConstruccionTABForm #fechaNotificacionConstruccionSeg').removeClass("red");
						 $('form#seguimientoConstruccionTABForm #btnLimpiaFechaAtencionConstruccion').hide();			 
						 $("form#seguimientoConstruccionTABForm #labelAlert").html('');
						 // Activa
						 $('form#seguimientoConstruccionTABForm #afil15Construccion').prop('disabled',false);
						 $('form#seguimientoConstruccionTABForm #afil15Construccion').addClass("red");
						 $('form#seguimientoConstruccionTABForm #banderaConstruccion').prop('disabled',false);
						 $('form#seguimientoConstruccionTABForm #banderaConstruccion').addClass("red");
						 $('form#seguimientoConstruccionTABForm #butonConstruccionInv').prop('disabled','disabled');
						 setTabHabilitado('cierrePorCotizarRGenericoTab_Construccion');
						 // manda a cierre por cotizar
						 $("form#cierrePorCotizarRGenericoTabForm #fechaAtencionGenericaCCRG").val(data.fechaAtencion);
					}
				}else if(data.cveEstatus == '32'){     // estatus 32
					jsBloquearStatus32EX();
					//desHabilitaCapturaFechasPeriodoRegulaObra();
					setTabDesHabilitado('cierrePorCotizarRGenericoTab_Construccion');
					if(data.fechaNotificacion != null){
						 $("form#seguimientoConstruccionTABForm #fechaNotificacionConstruccionSeg").prop('disabled','disabled');
						 $("form#seguimientoConstruccionTABForm #fechaNotificacionConstruccionSeg").val(data.fechaNotificacion);
						 $('form#seguimientoConstruccionTABForm #btnLimpiaFechaNotifConstruccion').hide();
						 $("form#seguimientoConstruccionTABForm #labelAlert").html('');
					}if(data.fechaNotificacion != null && data.fechaAtencion != null){
						 $("form#seguimientoConstruccionTABForm #fechaAtencionConstruccion").prop('disabled','disabled');
						 $("form#seguimientoConstruccionTABForm #fechaAtencionConstruccion").val(data.fechaAtencion);
						 $('form#seguimientoConstruccionTABForm #fechaNotificacionConstruccionSeg').removeClass("red");
						 $('form#seguimientoConstruccionTABForm #btnLimpiaFechaAtencionConstruccion').hide();			 
						 $("form#seguimientoConstruccionTABForm #labelAlert").html('');
					}
					if(data.fechaInicio != null){
						$("form#seguimientoConstruccionTABForm #labelFechaDelSegConstruccion").val(data.fechaInicio);
					}
					if(data.fechaFin != null){
						$("form#seguimientoConstruccionTABForm #labelFechaAlSegConstruccion").val(data.fechaFin);
					}
					
				}
				
				if(data.cveNroregobraSatic != null){
					$("form#seguimientoConstruccionTABForm #afil15Construccion").val(data.cveNroregobraSatic);
					$("form#seguimientoConstruccionTABForm #afil15Construccion").prop('disabled','disabled');
					$('form#seguimientoConstruccionTABForm #afil15Construccion').removeClass("red");
				}
				
				// Pesta�a Cierre Por Cotizar Razonablemente
				$("form#cierrePorCotizarRGenericoTabForm #labelFecCotizarRaz").html('');
				$("form#cierrePorCotizarRGenericoTabForm #labelFuncionarioReg").html('<label >' + data.auditor + '</label>');
				$("form#cierrePorCotizarRGenericoTabForm #cvePromocionCCRG").val(data.cvePromocion);
				$("form#cierrePorCotizarRGenericoTabForm #cveUsuarioCCRG").val(data.cveUsuario);
				$("form#cierrePorCotizarRGenericoTabForm #functionAuxCCRG").val("seguimientoEXCierrePorCotizarR()");
				
				// Pesta�a Invitacion
				$("form#invitacionAntecedenteForm #cvePromocion").val(data.cvePromocion);
				$("form#invitacionAntecedenteForm #funcionSeguimientoInv").val("seguimientoEXInvitacion()");
				
				// Pesta�a Seguimiento Construccion
				$("form#seguimientoConstruccionTABForm #cvePromocionConstruccionTAB").val(data.cvePromocion);
				$("form#seguimientoConstruccionTABForm #fechaOficioConstruccion").val(data.fechaOficio);
				$("form#seguimientoConstruccionTABForm #lableTituloPeriodo").html('<label>Periodo a Regularizar</label>');
				$("form#seguimientoConstruccionTABForm #labelFechaSolCorr").val(data.fecSolCorr);
				
				//Pesta�a cancelacion
				initTabCancelacionGenerico();
				$("form#cancelacionGenericoTabForm #functionAuxCancelacion").val("seguimientoEXCancelacion()");
				$("form#cancelacionGenericoTabForm #fechaEmisionOficioGenerico").val(data.fechaOficio);
				$("form#cancelacionGenericoTabForm #cvePromocion").val(data.cvePromocion);
								
				// Pesta�a Aut Avi Dict	
				initTabAvisoDictamenGenerico();
				$("form#autAviDictamenGenericoTabForm #cvePromocion").val(data.cvePromocion);
				$("form#autAviDictamenGenericoTabForm #functionAuxAutAvisoDict").val("seguimientoEXAutAviDict()");
				$("form#autAviDictamenGenericoTabForm #fechaEmisionOficioDictamenGenerico").val(data.fechaOficio);
				
				// Pesta�a derivar a subdelegacion
				$("form#derivarSubdelegacionGenericoTabForm #cvePromocion").val(data.cvePromocion);
				$("form#derivarSubdelegacionGenericoTabForm #fechaEmisionOficioGenerico").val(data.fechaOficio);
				$("form#derivarSubdelegacionGenericoTabForm #functionAuxDerSubdelegacion").val("seguimientoEXDerivarSubdeleg()");
								
				// Pesta�a derivar a fiscalizacion
				$("form#derivarFiscalizacionTABForm #cvePromocion").val(data.cvePromocion);
				$("form#derivarFiscalizacionTABForm #functionAuxFiscalizacion").val("seguimientoEXDerivarfiscalizacion()");
				
				inicializaFormaConstruccion();
				inicializaFormaCierrePorCotR();				
				initTabDerivarFiscalizacion();
				initTabDerivarSubdelegacionGenerico();				
				
				if(data.cveEstatus == '32'){
					setTimeout ('retraso()', 1000); 
				}				
				oDgDatosConstruccion.dialog("open");
				
			}							
		}).error(function(data){ 
			validarSesionExpirada(data);
		}).complete(function(){
			
			desbloquear();
		});
	}
}

function retraso(){
	$("form#seguimientoConstruccionTABForm #labelFechaDelSegConstruccion").val($("form#regularizarObraGenericoTABForm #perRegularizaDel").val());
	$("form#seguimientoConstruccionTABForm #labelFechaAlSegConstruccion").val($("form#regularizarObraGenericoTABForm #perRegularizaAl").val());
}

/**
 * @author Enrique Duran Jimenez
 * @since 10/07/2012
 * Funcion que inicializa los datepicker de la pesta�a seguimientoconstruccion
 */
function inicializaFormaConstruccion(){
	$( "form#seguimientoConstruccionTABForm #fechaNotificacionConstruccionSeg").datepicker( { dateFormat: 'dd-mm-yy' ,
		onSelect: function(dateText, inst) { 
			jsValidaFecNotificacionConstruccion();
		    }
	});
	
	$( "form#seguimientoConstruccionTABForm #fechaAtencionConstruccion").datepicker( { dateFormat: 'dd-mm-yy' ,
		onSelect: function(dateText, inst) { 
			jsValidaFecAtencionConstruccion();
		    }
	});
		$("form#seguimientoConstruccionTABForm #fechaNotificacionConstruccionSeg, form#seguimientoConstruccionTABForm #fechaAtencionConstruccion").datepicker('option', 'maxDate', jsFechaMaxSeguimiento);
		$("form#seguimientoConstruccionTABForm #fechaNotificacionConstruccionSeg, form#seguimientoConstruccionTABForm #fechaAtencionConstruccion").datepicker('option', 'minDate', jsFechaMinSeguimiento);
	
	
}


function jsValidaRegPatronal(){
	$("form#segimientoConstruccionForm #labelRPError").html('');
	if($("form#segimientoConstruccionForm #regPatronalSegConstruccion ").val() != '' &&
	   $("form#segimientoConstruccionForm #regPatronalSegConstruccion ").val().length == 10){							 
		 bloquear();
		 var patron = $("form#segimientoConstruccionForm #regPatronalSegConstruccion").val();
		 $.postJSON(URL + "/validaPatron.do",patron,function(data) { 
			if(data == null){
				$("form#segimientoConstruccionForm #labelRPError").html('<label class="etiquetaError">El registro patronal no es valido</label>');
				$("form#segimientoConstruccionForm #labelRazonSocial").html('');
				$("form#segimientoConstruccionForm #regPatronConstruccion").val('');
				$("form#seguimientoConstruccionTABForm #regPatronConstruccion").val('');
				$("form#segimientoConstruccionForm #regPatronalValidar").val('');
				$("form#invitacionAntecedenteForm #cveFkPatronInv").val('');
				setTabDesHabilitado('autAviDictamenGenericoTab_Construccion');
				
			}
			else if(data != null && data.razonSocial != null){				
				$("form#segimientoConstruccionForm #labelRazonSocial").html('<label>' + data.razonSocial + '</label>');
				$("form#segimientoConstruccionForm #regPatronalValidar").val(data.cvePK);
				$("form#invitacionAntecedenteForm #cveFkPatronInv").val(data.cvePK);
				$("form#seguimientoConstruccionTABForm #regPatronConstruccion").val(data.cvePK);
				if($(jsRolSeguimientoExCon).val() == "rol"){
					setTabHabilitado('autAviDictamenGenericoTab_Construccion');
				}
				
				$("form#regularizarObraGenericoTABForm #registroPatronalPagosDt").val(data.registroPatronal);
				// Deshabilita campo RP
				 $('form#segimientoConstruccionForm #regPatronalSegConstruccion').prop('disabled','disabled');
				 $('form#segimientoConstruccionForm #btnValidarRegPatronal').prop('disabled','disabled');
			}
		}).error(function(data){
			validarSesionExpirada(data);
		}).complete(function(){
			desbloquear();
			$("form#segimientoConstruccionForm #btnLimpiaRPEX").show();
		});
 }else{
	 	$("form#segimientoConstruccionForm #labelRazonSocial").html('');
		$("form#segimientoConstruccionForm #regPatronConstruccion").val('');
		$("form#seguimientoConstruccionTABForm #regPatronConstruccion").val('');	
		$("form#segimientoConstruccionForm #regPatronalValidar").val('');
		 $("form#segimientoConstruccionForm #labelRPError").html('<label class="etiquetaError">Capturar un Registro Patronal valido</label>');
		
	}
}

/**
 * @author Enrique Duran Jimenez
 * @since 24/09/2012
 * Funcion que activa y limpia el campo de Registro Patronal
 */
function jsLimpiaRPEX(){
	 $('form#segimientoConstruccionForm #regPatronalSegConstruccion').prop('disabled',false);
	 $('form#segimientoConstruccionForm #btnValidarRegPatronal').prop('disabled',false);
	 $('form#segimientoConstruccionForm #regPatronalSegConstruccion').val('');
	 $("form#segimientoConstruccionForm #labelRazonSocial").html('');
	 $("form#segimientoConstruccionForm #regPatronalValidar").val('');
	 $("form#invitacionAntecedenteForm #cveFkPatronInv").val('');
	 setTabDesHabilitado('autAviDictamenGenericoTab_Construccion');
	 setTabDesHabilitado('derivarFiscalizacionGenericoTab_Construccion');
	 setTabDesHabilitado('derivarSubdelegacionGenericoTab_Construccion');	 
}

/**
 * @author Enrique Duran Jimenez
 * @since 11/07/2012
 * Funcion que realiza validaciones dentro de la pantalla SEGUIMIENTO Construccion para la fecha de notificacion
 */
function jsValidaFecNotificacionConstruccion(){
	$("form#seguimientoConstruccionTABForm #labelAlert").html('');
	 var fecIni = $("form#seguimientoConstruccionTABForm #fechaOficioConstruccion").val();
	 var fecFinal = $("form#seguimientoConstruccionTABForm #fechaNotificacionConstruccionSeg").val();
	 if(fecIni != '' && fecFinal != ''){
		 if(jsValidaFechas(fecIni,fecFinal)){
			 $("form#seguimientoConstruccionTABForm #fechaNotificacionConstruccionSeg").val(fecFinal);
			 $('form#seguimientoConstruccionTABForm #fechaNotificacionConstruccionSeg').removeClass("red");
			 $('form#seguimientoConstruccionTABForm #btnLimpiaFechaNotifConstruccion').show();
			 $("form#seguimientoConstruccionTABForm #labelAlert").html('');
			 $("form#regularizarObraGenericoTABForm #fechaNotificacionHdn").val(fecFinal); 
//			 if($(jsRolSeguimientoExCon).val() == "rol"){
//					setTabHabilitado('cancelacionGenericoTab_Construccion');
//					setTabHabilitado('derivarSubdelegacionGenericoTab_Construccion');
//					setTabHabilitado('derivarFiscalizacionGenericoTab_Construccion');
//				}else{
//					setTabDesHabilitado('cancelacionGenericoTab_Construccion');
//					setTabDesHabilitado('derivarSubdelegacionGenericoTab_Construccion');
//					setTabDesHabilitado('derivarFiscalizacionGenericoTab_Construccion');
//				}
			 
			 $('form#seguimientoConstruccionTABForm #fechaAtencionConstruccion').prop('disabled',false);
			 $('form#seguimientoConstruccionTABForm #fechaAtencionConstruccion').addClass("red");
			
			 //No se habilita hasta que se guarde
			// $('form#seguimientoConstruccionTABForm #butonConstruccionInv').prop('disabled',false);
			 // Enviar a las demas pantallas
			 // Manda fecha a la cancelacion
			 $("form#cancelacionGenericoTabForm #fechaNotificacionOficioGenerico").val(fecFinal);
			// Manda a la invitacion
			 $("form#invitacionAntecedenteForm #fechaNotificacionInv").val(fecFinal);
			 // manda la fecha a aviso dictamen
			 $("form#autAviDictamenGenericoTabForm #fechaNotificacionOficioDictamenGenerico").val(fecFinal);
			 // fiscalizacion
			 $("form#derivarFiscalizacionTABForm #fechaNotificacionOficioFiscalizacion").val(fecFinal);
			// SUBDELEGACION
			 $("form#derivarSubdelegacionGenericoTabForm #fechaNotificacionOficioGenerico").val(fecFinal);
		 }else{ 
			 $('form#seguimientoConstruccionTABForm #btnLimpiaFechaNotifConstruccion').hide();
			 $('form#seguimientoConstruccionTABForm #fechaNotificacionConstruccionSeg').addClass("red");
			 $("form#seguimientoConstruccionTABForm #fechaNotificacionConstruccionSeg").val('');
			 $("form#seguimientoConstruccionTABForm #labelAlert").html('<label class="etiquetaError">La fecha de notificaci&oacute;n no puede ser menor a la fecha del Oficio Promoci&oacute;n </label>');
			 setTabDesHabilitado('derivarFiscalizacionGenericoTab_Construccion');
			 //setTabDesHabilitado('cancelacionGenericoTab_Construccion');
			 $('form#seguimientoConstruccionTABForm #fechaAtencionConstruccion').prop('disabled','disabled');
			 $('form#seguimientoConstruccionTABForm #fechaAtencionConstruccion').removeClass("red");
			 $('form#seguimientoConstruccionTABForm #butonConstruccionInv').prop('disabled','disabled');
			// Enviar a las demas pantallas
			 // Manda fecha a la cancelacion
			 $("form#cancelacionGenericoTabForm #fechaNotificacionOficioGenerico").val('');
			 // Manda a la invitacion
			 $("form#invitacionAntecedenteForm #fechaOfInvitacionTxInv").val('');
			 // manda la fecha a aviso dictamen
			 $("form#autAviDictamenGenericoTabForm #fechaNotificacionOficioDictamenGenerico").val('');
			 // fiscalizacion
			 $("form#derivarFiscalizacionTABForm #fechaNotificacionOficioFiscalizacion").val('');
			// SUBDELEGACION
			 $("form#derivarSubdelegacionGenericoTabForm #fechaNotificacionOficioGenerico").val('');
		 }
	 }
	
}


/**
 * @author Enrique Duran Jimenez
 * @since 11/07/2012
 * Funcion que realiza validaciones dentro de la pantalla SEGUIMIENTO Construccion para la fecha de Atencion
 */
function jsValidaFecAtencionConstruccion(){
	
	$("form#seguimientoConstruccionTABForm #labelAlert").html('');
	
	 var fecIni = $("form#seguimientoConstruccionTABForm #fechaNotificacionConstruccionSeg").val();
	 var fecFinal = $("form#seguimientoConstruccionTABForm #fechaAtencionConstruccion").val();
	 if(fecIni != '' && fecFinal != ''){
		 if(jsValidaFechas(fecIni,fecFinal)){
			 $("form#seguimientoConstruccionTABForm #fechaAtencionConstruccion").val(fecFinal);
			 $('form#seguimientoConstruccionTABForm #fechaNotificacionConstruccionSeg').removeClass("red");
			 $('form#seguimientoConstruccionTABForm #btnLimpiaFechaAtencionConstruccion').show();			 
			 $("form#seguimientoConstruccionTABForm #labelAlert").html('');
			 // Activa
			 $('form#seguimientoConstruccionTABForm #afil15Construccion').prop('disabled',false);
			 $('form#seguimientoConstruccionTABForm #afil15Construccion').addClass("red");
			 $('form#seguimientoConstruccionTABForm #banderaConstruccion').prop('disabled',false);
			 $('form#seguimientoConstruccionTABForm #banderaConstruccion').addClass("red");
			 $('form#seguimientoConstruccionTABForm #butonConstruccionInv').prop('disabled','disabled');
//			 setTabHabilitado('cierrePorCotizarRGenericoTab_Construccion');
			 setTabDesHabilitado('cancelacionGenericoTab_Construccion');
			 // manda a cierre por cotizar
			 $("form#cierrePorCotizarRGenericoTabForm #fechaAtencionGenericaCCRG").val(fecFinal);
		 }else{
			 $('form#seguimientoConstruccionTABForm #btnLimpiaFechaAtencionConstruccion').hide();
			 $("form#seguimientoConstruccionTABForm #fechaAtencionConstruccion").val('');
			 $("form#seguimientoConstruccionTABForm #labelAlert").html('<label class="etiquetaError">La fecha de Atenci&oacute;n no puede ser mayor a la fecha de notificaci&oacute;n</label>');
			 //$('form#seguimientoConstruccionTABForm #butonConstruccionInv').prop('disabled',false);
			 //desactiva
			 $('form#seguimientoConstruccionTABForm #afil15Construccion').prop('disabled','disabled');
			 $('form#seguimientoConstruccionTABForm #afil15Construccion').removeClass("red");
			 $('form#seguimientoConstruccionTABForm #banderaConstruccion').prop('disabled','disabled');
			 $('form#seguimientoConstruccionTABForm #banderaConstruccion').removeClass("red");
//			 setTabDesHabilitado('cierrePorCotizarRGenericoTab_Construccion');
			 if($(jsRolSeguimientoExCon).val() == "rol"){
					setTabHabilitado('cancelacionGenericoTab_Construccion');
				}else{
					setTabDesHabilitado('cancelacionGenericoTab_Construccion');
				}
			 // manda a cierre por cotizar
			 $("form#cierrePorCotizarRGenericoTabForm #fechaAtencionGenericaCCRG").val('');
		 }
	 }
}

/**
 * @author Enrique Duran Jimenez
 * @since 11/07/2012
 * Funcion que realiza funcionalidad en el boton [X] de la fecha de atencion
 */
function jsLimpiaFechaAtencionEX(){
	$('form#seguimientoConstruccionTABForm #fechaAtencionConstruccion').val('');
	$('form#seguimientoConstruccionTABForm #fechaAtencionConstruccion').addClass("red");
	$('form#seguimientoConstruccionTABForm #btnLimpiaFechaAtencionConstruccion').hide();
	//$('form#seguimientoConstruccionTABForm #butonConstruccionInv').prop('disabled',false);
	//desactiva
	 $('form#seguimientoConstruccionTABForm #afil15Construccion').prop('disabled','disabled');
	 $('form#seguimientoConstruccionTABForm #afil15Construccion').removeClass("red");
	 $('form#seguimientoConstruccionTABForm #banderaConstruccion').prop('disabled','disabled');
	 $('form#seguimientoConstruccionTABForm #banderaConstruccion').removeClass("red");
	 setTabDesHabilitado('cierrePorCotizarRGenericoTab_Construccion');
	 if($(jsRolSeguimientoExCon).val() == "rol"){
			setTabHabilitado('cancelacionGenericoTab_Construccion');
		}else{
			setTabDesHabilitado('cancelacionGenericoTab_Construccion');
		}
}

/**
 * @author Enrique Duran Jimenez
 * @since 11/07/2012
 * Funcion que realiza funcionalidad en el boton [X] de la fecha de notificacion
 */
function jsLimpiaFechaNotificacionEX(){
	jsLimpiaFechaAtencionEX();
	$('form#seguimientoConstruccionTABForm #fechaNotificacionConstruccionSeg').val('');
	$('form#seguimientoConstruccionTABForm #fechaNotificacionConstruccionSeg').addClass("red");
	$('form#seguimientoConstruccionTABForm #btnLimpiaFechaNotifConstruccion').hide();
	 setTabDesHabilitado('derivarFiscalizacionGenericoTab_Construccion');
	 $('form#seguimientoConstruccionTABForm #fechaAtencionConstruccion').prop('disabled','disabled');
	 $('form#seguimientoConstruccionTABForm #fechaAtencionConstruccion').removeClass("red");
	 $('form#seguimientoConstruccionTABForm #butonConstruccionInv').prop('disabled','disabled');
}

/**
 * @author Enrique Duran Jimenez
 * @since 11/07/2012
 * Funcion que inicializa los datepicker de la pantalla invitacion
 */
function inicializaInvitacionConstruccion(){
	var rpSegCon=$("form#segimientoConstruccionForm #regPatronalSegConstruccion").val();
	if (rpSegCon==null || rpSegCon=='') {
		alert ("Se requiere el registro patronal");
		return;
	}

	validarRegPatronalConst(rpSegCon);
	/*var id = $("form#invitacionAntecedenteForm #cvePromocion").val();
	$("form#invitacionAntecedenteForm #fechaIncialinv").datepicker('option', 'beforeShowDay', null);
	$("form#invitacionAntecedenteForm #fechaFinalInv").datepicker('option', 'beforeShowDay', null);
	jsMuestraInvitacionConstruccion(id); */
}


/**
 * @author Enrique Duran Jimenez
 * @since 11/07/2012
 * Funcion que consulta con la cve_promocion las datos de la invitacion y llena la pantalla con los datos adquiridos
 */
function jsMuestraInvitacionConstruccion(obj){
	var id = obj;
	
	var sPromocion = '{' +
	   '"cveTemp":"'+id+'",'+
	   '"tipoPrograma":"promocion"}';	
	var promocion = jQuery.parseJSON(sPromocion);

	var url = getAppContextParaJS()+"/catalogo/invitacion/invitacionAntecedente.do";
	$.postJSON(url,promocion,function(data) {
		jsLimpiarGuardar();
		$("form#invitacionAntecedenteForm #labelFolioAntecedente").html('<label>' + data.folioAntecedente + '</label>');
		if(data.regPatronal != null){
			$("form#invitacionAntecedenteForm #labelRegPatronal").html('<label>' + data.regPatronal + '</label>');
		}
		if(data.razonSocial){
			$("form#invitacionAntecedenteForm #labelNomRazonSocial").html('<label>' + data.razonSocial + '</label>');
		}		
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
 * @since 11/07/2012
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
 * @since 11/07/2012
 * Funcion que guarda losdatos del seguimiento de construccion
 */
function guardarSeguimientoEX(){
	 var promocion = $("#seguimientoConstruccionTABForm").serializeObject(true);
	   bloquear();
	   $.postJSON(getAppContextParaJS()+ "/promocion/seguimiento/generico/GuardarSeguimientoEX.do", promocion, function(data) {	
		   	
		   setTabDesHabilitado("cancelacionGenericoTab_Construccion");
		   setTabDesHabilitado("derivarSubdelegacionGenericoTab_Construccion");
		   setTabDesHabilitado("autAviDictamenGenericoTab_Construccion");	
		  if($("#fechaNotificacionConstruccionSeg").val()!='' && $("#fechaAtencionConstruccion").val()==''){
			$('form#seguimientoConstruccionTABForm #butonConstruccionInv').prop('disabled',false);
		}
	   }).error(function(datas){ 
			validarSesionExpirada(datas);
		}).complete(function(){			
			alert('Los datos se guardaron correctamente');
			desbloquear();
		});	
}

/**
 * @author Enrique Duran Jimenez
 * @since 11/07/2012
 * Funcion que realiza las funcionalidades del seguimiento construccion cuando termina de ejecutarce el metodo de invitacion
 */
function seguimientoEXInvitacion(){	
	$("form#seguimientoConstruccionTABForm #labelFechaInvitacion").val($("form#invitacionAntecedenteForm #fechaEmisionFec").val());
	$("form#seguimientoConstruccionTABForm #labelFechaDelSegConstruccion").val($("form#invitacionAntecedenteForm #fechaIncialinv").val());
	$("form#seguimientoConstruccionTABForm #labelFechaAlSegConstruccion").val($("form#invitacionAntecedenteForm #fechaFinalInv").val());	
	$("form#seguimientoConstruccionTABForm #lableTituloPeriodo").html('<label>Periodo de Correcci&oacute;n</label>');
	setTabDesHabilitado('cancelacionGenericoTab_Construccion');
	setTabDesHabilitado('derivarSubdelegacionGenericoTab_Construccion');
	setTabDesHabilitado('derivarFiscalizacionGenericoTab_Construccion');
	setTabDesHabilitado('autAviDictamenGenericoTab_Construccion');
	setTabDesHabilitado('cierrePorCotizarRGenericoTab_Construccion');
	setTabDesHabilitado('regularizarObraGenericoTAB_Construccion');	
	guardarSeguimientoEX();
	jsBloquearSeguimientoEXTAB();
	changeTab('seguimientoTAB_Construccion');
}


/**
 * @author Enrique Duran Jimenez
 * @since 11/07/2012
 * Funcion que realiza las funcionalidades del seguimiento EX cuando termina de ejecutarce el metodo de cancelacion
 */
function seguimientoEXCancelacion(){	
	$("form#seguimientoConstruccionTABForm #labelFechaCancelacion").val($("form#cancelacionGenericoTabForm #fechaCancelacionCGT").val());
	setTabDesHabilitado('derivarSubdelegacionGenericoTab_Construccion');
	setTabDesHabilitado('derivarFiscalizacionGenericoTab_Construccion');
	setTabDesHabilitado('autAviDictamenGenericoTab_Construccion');
	setTabDesHabilitado('cierrePorCotizarRGenericoTab_Construccion');
	setTabDesHabilitado('regularizarObraGenericoTAB_Construccion');	
	guardarSeguimientoEX();
	jsBloquearSeguimientoEXTAB();
	jsBloquearSeguimientoCancelar();
	changeTab('seguimientoTAB_Construccion');
}

/**
 * @author Enrique Duran Jimenez
 * @since 11/07/2012
 * Funcion que realiza las funcionalidades del seguimiento EX cuando termina de ejecutarce el metodo de derivar a otra subdelegacion
 */
function seguimientoEXDerivarSubdeleg(){	
	$("form#seguimientoConstruccionTABForm #lableFechaDerivSubdelegacion").val($("form#derivarSubdelegacionGenericoTabForm #fechaDerivarSubdel").val());
	setTabDesHabilitado('cancelacionGenericoTab_Construccion');
	setTabDesHabilitado('derivarFiscalizacionGenericoTab_Construccion');
	setTabDesHabilitado('autAviDictamenGenericoTab_Construccion');
	setTabDesHabilitado('cierrePorCotizarRGenericoTab_Construccion');
	setTabDesHabilitado('regularizarObraGenericoTAB_Construccion');	
	guardarSeguimientoEX();
	jsBloquearSeguimientoEXTAB();
	jsBloquearSeguimientoDerivarSubDel();
	changeTab('seguimientoTAB_Construccion');
}

/**
 * @author Enrique Duran Jimenez
 * @since 11/07/2012
 * Funcion que realiza las funcionalidades del seguimiento EX cuando termina de ejecutarce el metodo de derivar a fiscalizacion
 */
function seguimientoEXDerivarfiscalizacion(){	
	$("form#seguimientoConstruccionTABForm #labelFechaFiscalizacion").val($("form#derivarFiscalizacionTABForm #fecDerivacionGenerica").val());
	setTabDesHabilitado('cancelacionGenericoTab_Construccion');
	setTabDesHabilitado('derivarSubdelegacionGenericoTab_Construccion');
	setTabDesHabilitado('autAviDictamenGenericoTab_Construccion');
	setTabDesHabilitado('cierrePorCotizarRGenericoTab_Construccion');
	setTabDesHabilitado('regularizarObraGenericoTAB_Construccion');	
	guardarSeguimientoEX();
	jsBloquearSeguimientoEXTAB();
	jsBloquearSeguimientoDerivarFiscalizacion();
	changeTab('seguimientoTAB_Construccion');
}

/**
 * 
 * @since 11/07/2012
 * Funcion que realiza las funcionalidades del seguimiento EX cuando termina de ejecutarce el metodo de AUT. AVi DIC
 */
function seguimientoEXAutAviDict(){	
	$("form#seguimientoConstruccionTABForm #labelFechaAutAviso").val($("form#autAviDictamenGenericoTabForm #fecAvisoDictGenericoTab").val());
	$("form#seguimientoConstruccionTABForm #labelFechaDelSegConstruccion").val($("form#autAviDictamenGenericoTabForm #fecIniPeriodoDictGenericoTab").val());
	$("form#seguimientoConstruccionTABForm #labelFechaAlSegConstruccion").val($("form#autAviDictamenGenericoTabForm #fecFinPeriodoDictGenericoTab").val());	
	setTabDesHabilitado('cancelacionGenericoTab_Construccion');
	setTabDesHabilitado('derivarSubdelegacionGenericoTab_Construccion');
	setTabDesHabilitado('derivarFiscalizacionGenericoTab_Construccion');
	setTabDesHabilitado('cierrePorCotizarRGenericoTab_Construccion');
	setTabDesHabilitado('regularizarObraGenericoTAB_Construccion');	
	guardarSeguimientoEX();
	jsBloquearSeguimientoEXTAB();
	jsBloquearSeguimientoAutAviDict();
	changeTab('seguimientoTAB_Construccion');
}

/**
 * @author Enrique Duran Jimenez
 * @since 11/07/2012
 * Funcion que realiza las funcionalidades del seguimiento EX cuando termina de ejecutarce el metodo de CierrePorCotizarR
 */
function seguimientoEXCierrePorCotizarR(){
	
	$("form#seguimientoConstruccionTABForm #labelFechaCierreCotR").val($("form#cierrePorCotizarRGenericoTabForm #fecCotizRazCCRG").val());
	setTabDesHabilitado('cancelacionGenericoTab_Construccion');
	setTabDesHabilitado('derivarSubdelegacionGenericoTab_Construccion');
	setTabDesHabilitado('derivarFiscalizacionGenericoTab_Construccion');
	setTabDesHabilitado('autAviDictamenGenericoTab_Construccion');
	setTabDesHabilitado('regularizarObraGenericoTAB_Construccion');	
	guardarSeguimientoEX();
	jsBloquearSeguimientoEXTAB();
	jsBloquearSeguimientoCierreCotizarR();
	changeTab('seguimientoTAB_Construccion');
}

/**
 * @author Enrique Duran Jimenez
 * @since 11/07/2012
 * Funcion que realiza las funcionalidades del seguimiento EX cuando termina de ejecutarce el metodo de Regulariza pagos
 */
function seguimientoEXRegulariza(){
	$("form#seguimientoConstruccionTABForm #labelFechaDelSegConstruccion").val($("form#regularizarObraGenericoTABForm #perRegularizaDel").val());
	$("form#seguimientoConstruccionTABForm #labelFechaAlSegConstruccion").val($("form#regularizarObraGenericoTABForm #perRegularizaAl").val());
	setTabDesHabilitado('cancelacionGenericoTab_Construccion');
	setTabDesHabilitado('derivarSubdelegacionGenericoTab_Construccion');
	setTabDesHabilitado('derivarFiscalizacionGenericoTab_Construccion');
	setTabDesHabilitado('autAviDictamenGenericoTab_Construccion');
	setTabDesHabilitado('cierrePorCotizarRGenericoTab_Construccion');
	guardarSeguimientoEX();
	jsBloquearSeguimientoEXTAB();
	jsBloquearSeguimientoRegular();
	changeTab('seguimientoTAB_Construccion');
}


/**
 * @author Enrique Duran Jimenez
 * @since 11/07/2012
 * Funcion que bloquea todos los campos de la pantalla Seguimiento EX
 */
function jsBloquearSeguimientoEXTAB(){
	
	$('form#seguimientoConstruccionTABForm input[type=text]').prop('disabled','disabled');
	$('form#seguimientoConstruccionTABForm input[type=button]').prop('disabled','disabled');
	$('form#seguimientoConstruccionTABForm input[type=checkbox]').prop('disabled','disabled');
	$('form#seguimientoConstruccionTABForm #txObservacionesConstruccionTAB').prop('disabled',true);
	$("form#seguimientoConstruccionTABForm #txObservacionesConstruccionTAB").removeClass("red");
	$("form#seguimientoConstruccionTABForm #fechaNotificacionConstruccionSeg").removeClass("red");
	$("form#seguimientoConstruccionTABForm #banderaConstruccion").removeClass("red");
	$("form#seguimientoConstruccionTABForm #fechaAtencionConstruccion").removeClass("red");
	$("form#seguimientoConstruccionTABForm #afil15Construccion").removeClass("red");
	$("form#seguimientoConstruccionTABForm #btnLimpiaFechaAtencionConstruccion").hide();
	$("form#seguimientoConstruccionTABForm #btnLimpiaFechaNotifConstruccion").hide();
	$("form#segimientoConstruccionForm #btnLimpiaRPEX").hide();	
	$("form#seguimientoConstruccionTABForm #labelFechaDelSegConstruccion").prop('disabled',false);
	$("form#seguimientoConstruccionTABForm #labelFechaAlSegConstruccion").prop('disabled',false);
	$("form#seguimientoConstruccionTABForm #labelFechaDelSegConstruccion").prop('readonly',true);
	$("form#seguimientoConstruccionTABForm #labelFechaAlSegConstruccion").prop('readonly',true);
	
}


/**
 * @author Enrique Duran Jimenez
 * @since 11/07/2012
 * Funcion que desbloquea todos los campos de la pantalla Seguimiento EX
 */
function jsDesBloquearSeguimientoEXTAB(){
	
	$('form#seguimientoConstruccionTABForm input[type=text]').prop('disabled',false);
	$('form#seguimientoConstruccionTABForm input[type=button]').prop('disabled',false);
	$('form#seguimientoConstruccionTABForm input[type=checkbox]').prop('disabled',false);
	$('form#seguimientoConstruccionTABForm #txObservacionesConstruccionTAB').prop('disabled',false);
	$("form#seguimientoConstruccionTABForm #txObservacionesConstruccionTAB").addClass("red");
	$("form#seguimientoConstruccionTABForm #fechaNotificacionConstruccionSeg").addClass("red");
	$("form#seguimientoConstruccionTABForm #fechaAtencionConstruccion").addClass("red");
	$("form#seguimientoConstruccionTABForm #btnLimpiaFechaAtencionConstruccion").show();
	$("form#seguimientoConstruccionTABForm #btnLimpiaFechaNotifConstruccion").show();
	
}

/**
 * @author Enrique Duran Jimenez
 * @since 11/07/2012
 * Funcion que bloquea todos los campos de la pantalla Seguimiento EX - cabcelar
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
 * @since 11/07/2012
 * Funcion que desbloquea todos los campos de la pantalla Seguimiento EX - cancelar
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
 * @since 11/07/2012
 * Funcion que bloquea todos los campos de la pantalla Seguimiento EX - AviDict
 */
function jsBloquearSeguimientoAutAviDict(){
	$('form#autAviDictamenGenericoTabForm input[type=text]').prop('disabled','disabled');
	$('form#autAviDictamenGenericoTabForm input[type=button]').prop('disabled','disabled');
	habilitaCombosCancelacionCGT();
}

/**
 * @author Enrique Duran Jimenez
 * @since 11/07/2012
 * Funcion que desbloquea todos los campos de la pantalla Seguimiento EX - AviDict
 */
function jsDesBloquearSeguimientoAutAviDict(){
	$('form#autAviDictamenGenericoTabForm input[type=text]').prop('disabled',false);
	$('form#autAviDictamenGenericoTabForm input[type=button]').prop('disabled',false);
}

/**
 * @author Enrique Duran Jimenez
 * @since 11/07/2012
 * Funcion que bloquea todos los campos de la pantalla Seguimiento EX - CierreCotizarR
 */
function jsBloquearSeguimientoCierreCotizarR(){
	$('form#cierrePorCotizarRGenericoTabForm input[type=text]').prop('disabled','disabled');
	$('form#cierrePorCotizarRGenericoTabForm input[type=button]').prop('disabled','disabled');
}

/**
 * @author Enrique Duran Jimenez
 * @since 11/07/2012
 * Funcion que desbloquea todos los campos de la pantalla Seguimiento EX - CierreCotizarR
 */
function jsDesBloquearSeguimientoCierreCotizarR(){
	$('form#cierrePorCotizarRGenericoTabForm input[type=text]').prop('disabled',false);
	$('form#cierrePorCotizarRGenericoTabForm input[type=button]').prop('disabled',false);
}

/**
 * @author Enrique Duran Jimenez
 * @since 11/07/2012
 * Funcion que bloquea todos los campos de la pantalla Seguimiento EX - Regula Pagos
 */
function jsBloquearSeguimientoRegular(){
	$('form#seguimientoSBCTABForm input[type=text]').prop('disabled','disabled');
	$('form#seguimientoSBCTABForm input[type=text]').removeClass("red");
	$('form#seguimientoSBCTABForm input[type=button]').prop('disabled','disabled');
}

/**
 * @author Enrique Duran Jimenez
 * @since 11/07/2012
 * Funcion que desbloquea todos los campos de la pantalla Seguimiento EX - Regula Pagos
 */
function jsDesBloquearSeguimientoRegular(){
	$('form#seguimientoSBCTABForm input[type=text]').prop('disabled',false);
	$('form#seguimientoSBCTABForm input[type=button]').prop('disabled',false);
}

/**
 * @author Enrique Duran Jimenez
 * @since 11/07/2012
 * Funcion que bloquea todos los campos de la pantalla Seguimiento EX - Derivar a subdelegacion
 */
function jsBloquearSeguimientoDerivarSubDel(){
	$('form#derivarSubdelegacionGenericoTabForm input[type=text]').prop('disabled','disabled');
	$('form#derivarSubdelegacionGenericoTabForm input[type=text]').removeClass("red");
	$('form#derivarSubdelegacionGenericoTabForm input[type=button]').prop('disabled','disabled');
}

/**
 * @author Enrique Duran Jimenez
 * @since 11/07/2012
 * Funcion que bloquea todos los campos de la pantalla Seguimiento EX - Derivar a subdelegacion
 */
function jsDesBloquearSeguimientoDerivarSubDel(){
	$('form#derivarSubdelegacionGenericoTabForm input[type=text]').prop('disabled',false);
	$('form#derivarSubdelegacionGenericoTabForm input[type=text]').addClass("red");
	$('form#derivarSubdelegacionGenericoTabForm input[type=button]').prop('disabled',false);
}

/**
 * @author Enrique Duran Jimenez
 * @since 11/07/2012
 * Funcion que bloquea todos los campos de la pantalla Seguimiento EX - Derivar a fiscalizacion
 */
function jsBloquearSeguimientoDerivarFiscalizacion(){
	$('form#derivarFiscalizacionTABForm input[type=text]').prop('disabled','disabled');
	$('form#derivarFiscalizacionTABForm input[type=text]').removeClass("red");
	$('form#derivarFiscalizacionTABForm input[type=button]').prop('disabled','disabled');
}

/**
 * @author Enrique Duran Jimenez
 * @since 11/07/2012
 * Funcion que desbloquea todos los campos de la pantalla Seguimiento EX - Derivar a fiscalizacion
 */
function jsDesBloquearSeguimientoDerivarFiscalizacion(){
	$('form#derivarFiscalizacionTABForm input[type=text]').prop('disabled',false);
	$('form#derivarFiscalizacionTABForm input[type=text]').addClass("red");
	$('form#derivarFiscalizacionTABForm input[type=button]').prop('disabled',false);
}

function jsBloquearStatus28(){
	//habilita
	$('form#seguimientoConstruccionTABForm #fechaNotificacionConstruccionSeg').addClass("red");	
	if($(jsRolSeguimientoExCon).val() == "rol"){
		setTabHabilitado('cancelacionGenericoTab_Construccion');
		setTabHabilitado('derivarSubdelegacionGenericoTab_Construccion');
	}else{
		setTabDesHabilitado('cancelacionGenericoTab_Construccion');
		setTabDesHabilitado('derivarSubdelegacionGenericoTab_Construccion');
	}	
	
	// Deshabilita
	setTabDesHabilitado('regularizarObraGenericoTAB_Construccion');	
	setTabDesHabilitado('cierrePorCotizarRGenericoTab_Construccion');
	//setTabDesHabilitado('derivarFiscalizacionGenericoTab_Construccion');	
	$('form#seguimientoConstruccionTABForm #banderaConstruccion').prop('disabled','disabled');
	$('form#seguimientoConstruccionTABForm #banderaConstruccion').removeClass("red");
	$('form#seguimientoConstruccionTABForm #afil15Construccion').prop('disabled','disabled');
	$('form#seguimientoConstruccionTABForm #afil15Construccion').removeClass("red");
	$('form#seguimientoConstruccionTABForm #fechaAtencionConstruccion').prop('disabled','disabled');
	$('form#seguimientoConstruccionTABForm #fechaAtencionConstruccion').removeClass("red");
	$('form#seguimientoConstruccionTABForm #butonConstruccionInv').prop('disabled','disabled');
	$('form#seguimientoConstruccionTABForm #btnLimpiaFechaAtencionConstruccion').hide();
	$('form#seguimientoConstruccionTABForm #btnLimpiaFechaNotifConstruccion').hide();
	changeTab('seguimientoTAB_Construccion');
}

function jsBloquearStatus32EX(){
	//habilita
	setTabHabilitado('seguimientoTAB_Construccion');
	setTabHabilitado('regularizarObraGenericoTAB_Construccion');
	// Deshabilita
	setTabDesHabilitado('cancelacionGenericoTab_Construccion');	
	setTabDesHabilitado('derivarSubdelegacionGenericoTab_Construccion');
	setTabDesHabilitado('derivarFiscalizacionGenericoTab_Construccion');
	setTabDesHabilitado('autAviDictamenGenericoTab_Construccion');
	setTabDesHabilitado('cierrePorCotizarRGenericoTab_Construccion');	
	$('form#seguimientoConstruccionTABForm #fechaNotificacionConstruccionSeg').prop('disabled','disabled');
	$('form#seguimientoConstruccionTABForm #fechaNotificacionConstruccionSeg').removeClass("red");
	$('form#seguimientoConstruccionTABForm #fechaAtencionConstruccion').prop('disabled','disabled');
	$('form#seguimientoConstruccionTABForm #fechaAtencionConstruccion').removeClass("red");
	$('form#seguimientoConstruccionTABForm #afil15Construccion').prop('disabled','disabled');
	$('form#seguimientoConstruccionTABForm #afil15Construccion').removeClass("red");
	$("form#seguimientoConstruccionTABForm #banderaConstruccion").attr('checked', 'checked');
	$('form#seguimientoConstruccionTABForm #banderaConstruccion').prop('disabled','disabled');
	$('form#seguimientoConstruccionTABForm #banderaConstruccion').removeClass("red");
	$('form#seguimientoConstruccionTABForm #butonConstruccionInv').prop('disabled','disabled');
	$('form#seguimientoConstruccionTABForm #btnGuardarConstruccionTAB').prop('disabled',false);
	$('form#seguimientoConstruccionTABForm #btnLimpiaFechaAtencionConstruccion').hide();
	$('form#seguimientoConstruccionTABForm #btnLimpiaFechaNotifConstruccion').hide();
	changeTab('regularizarObraGenericoTAB_Construccion');

}

/**
 * @author Enrique Duran Jimenez
 * @since 11/07/2012
 * Funcion que valida si el check de RP esta habilitado
 */
function jsValidaRP(valor){
	if(valor == 'rp'){
		if($("form#seguimientoConstruccionTABForm #banderaConstruccion").attr('checked') != null){
			$("form#seguimientoConstruccionTABForm #banderaConstruccion").attr('checked', 'checked');
		   inicializaDialogConfirmarRPEX();	
		}
	}
}

/**
 * @author Enrique Duran Jimenez
 * @since 11/07/2012
 * Funcion que inicializa el dialogo de RP
 */
function inicializaDialogConfirmarRPEX(){
	oDgConfirmarRP = $(idDgConfirmarRP).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 700,
		closeOnEscape: false,
		buttons: {
			   "Si": function() { 
				   guardaSeguimientoEXRegulariza();
				   jsFuncionRPPestanaEX();					
				   jsBloquearSeguimientoEXTAB();
				   changeTab('regularizarObraGenericoTAB_Construccion');
					$("form#seguimientoConstruccionTABForm #banderaConstruccion").attr('checked', 'checked');
					$(this).dialog("close"); 
					return true;
							
			}, "No": function(){
				$("form#seguimientoConstruccionTABForm #banderaConstruccion").removeAttr('checked');
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
 * @since 11/07/2012
 * Funcion que manda a la pesta�a de Pagos y deshabilita las demas pesta�as
 */
function jsFuncionRPPestanaEX(){
	setTabDesHabilitado('cancelacionGenericoTab_Construccion');
	setTabDesHabilitado('derivarSubdelegacionGenericoTab_Construccion');
	setTabDesHabilitado('derivarFiscalizacionGenericoTab_Construccion');
	setTabDesHabilitado('autAviDictamenGenericoTab_Construccion');
	setTabDesHabilitado('cierrePorCotizarRGenericoTab_Construccion');
	setTabHabilitado('regularizarObraGenericoTAB_Construccion');
	oDgConfirmarRP.dialog("close");
	changeTab('regularizarObraGenericoTAB_Construccion');
	
}

/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion que manda ejecutar el metodo de Guardar seguimiento EX
 */
function guardaSeguimientoEXRegulariza(){
	
	if( $("form#seguimientoConstruccionTABForm #regPatronalSegConstruccion").val()==''){
		alert("El registro patronal es necesario");
		return;
	}
	 var promocion = $("#seguimientoConstruccionTABForm").serializeObject(true);
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
 * @since 11/07/2012
 * Funcion que limpia los campos del seguimiento lismpiando  y desbloqueando todos los campos de todas las pesta�as
 */
function jsLimpiarFormaSeguimientoEX(){
	limpiarFormulario("#seguimientoConstruccionTABForm");
	limpiarFormulario("#cancelacionGenericoTabForm");
	limpiarFormulario("#autAviDictamenGenericoTabForm");
	limpiarFormulario("#derivarSubdelegacionGenericoTabForm");
	limpiarFormulario("#derivarFiscalizacionTABForm");
	limpiarFormulario("#segimientoConstruccionForm");
	$("form#segimientoConstruccionForm #labelRazonSocial").html('');
	jsDesBloquearSeguimientoEXTAB();
	jsDesBloquearSeguimientoCancelar();
	jsDesBloquearSeguimientoAutAviDict();
	jsDesBloquearSeguimientoCierreCotizarR();
	jsDesBloquearSeguimientoRegular();
	jsDesBloquearSeguimientoDerivarSubDel();
	jsDesBloquearSeguimientoDerivarFiscalizacion();
}

function jsFechaNotificacionEXData(){
	 $("form#seguimientoConstruccionTABForm #fechaNotificacionConstruccionSeg").prop('disabled','disabled');
	 $('form#seguimientoConstruccionTABForm #fechaNotificacionConstruccionSeg').removeClass("red");
	 $('form#seguimientoConstruccionTABForm #btnLimpiaFechaNotifConstruccion').hide();
	 $("form#seguimientoConstruccionTABForm #labelAlert").html('');
	 
	 if($(jsRolSeguimientoExCon).val() == "rol"){
			setTabHabilitado('cancelacionGenericoTab_Construccion');
			setTabHabilitado('derivarSubdelegacionGenericoTab_Construccion');
			setTabHabilitado('derivarFiscalizacionGenericoTab_Construccion');
			setTabHabilitado('autAviDictamenGenericoTab_Construccion');
		}else{
			setTabDesHabilitado('cancelacionGenericoTab_Construccion');
			setTabDesHabilitado('derivarSubdelegacionGenericoTab_Construccion');
			setTabDesHabilitado('derivarFiscalizacionGenericoTab_Construccion');
		}
	 
	 $('form#seguimientoConstruccionTABForm #fechaAtencionConstruccion').prop('disabled',false);
	 $('form#seguimientoConstruccionTABForm #fechaAtencionConstruccion').addClass("red");
	 $('form#seguimientoConstruccionTABForm #butonConstruccionInv').prop('disabled',false);
}

function jsFechaAtencionEXData(){
	 $("form#seguimientoConstruccionTABForm #fechaAtencionConstruccion").prop('disabled','disabled');
	 $('form#seguimientoConstruccionTABForm #fechaAtencionConstruccion').removeClass("red");
	 $('form#seguimientoConstruccionTABForm #fechaNotificacionConstruccionSeg').removeClass("red");
	 $('form#seguimientoConstruccionTABForm #btnLimpiaFechaAtencionConstruccion').hide();			 
	 $("form#seguimientoConstruccionTABForm #labelAlert").html('');
     $('form#segimientoConstruccionForm #regPatronalSegConstruccion').prop('disabled','disabled');
	 $('form#segimientoConstruccionForm #btnValidarRegPatronal').prop('disabled','disabled');						

	 // Activa
	 $('form#seguimientoConstruccionTABForm #afil15Construccion').prop('disabled',false);
	 $('form#seguimientoConstruccionTABForm #afil15Construccion').addClass("red");
	 $('form#seguimientoConstruccionTABForm #banderaConstruccion').prop('disabled',false);
	 $('form#seguimientoConstruccionTABForm #banderaConstruccion').addClass("red");
	 $('form#seguimientoConstruccionTABForm #butonConstruccionInv').prop('disabled','disabled');
	 setTabHabilitado('cierrePorCotizarRGenericoTab_Construccion');
}

function jsAfil15EXData(){
	$("form#seguimientoConstruccionTABForm #afil15Construccion").prop('disabled','disabled');
	$('form#seguimientoConstruccionTABForm #afil15Construccion').removeClass("red");
}

function jsvalidarNumerico(e) { 
	
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla==8) return true;
    patron = /[1234567890]/;
    te = String.fromCharCode(tecla);
    
    return patron.test(te);
} 


function validarRegPatronalConst(registroPatronal) {
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
						 var id = $("form#seguimientoSaticbTABForm #cvePromocion").val();
						 /*lo de invitacion*/
						 var id = $("form#invitacionAntecedenteForm #cvePromocion").val();
						 $("form#invitacionAntecedenteForm #fechaIncialinv").datepicker('option', 'beforeShowDay', null);
						 $("form#invitacionAntecedenteForm #fechaFinalInv").datepicker('option', 'beforeShowDay', null);
						 jsMuestraInvitacionConstruccion(id); 
						 
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
