/**
 * indica el tab seleccionado
 */
var activeTab = '#seguimientoSaticbTAB_saticb';

var EN_PROCESO_NOTIFICACION_OFICIO_PROMOCION = 28;
var PROMOCION_REGULARIZADA = 32;
var ROL_JEFE_SATICB = false;
var jsBTieneFecAtencion = false;				

/**
 * Indica la forma seleccionada
 */
var FORMA_ACTUAL ='seguimientoSaticBForm';

var SEGUIMIENTO=1;
var CANCELACION=2;
var DERIVAR_SUBDELEGACION=3;
var DERIVAR_FISCALIZACION=4;
var AUT_AVISO_DICT=5;
var ESTATUS_OBRA=6;
var REGULARIZAR_OBRA=7;

//inicializacion
var idDatosSeguimientoSaticB = "#dgSeguimientoPromocionSaticB"; //id Div de jsp dgSeguimientoPromocionSaticB 
var oDgDatosSeguimientoSaticB;

// Variables para los roles
var JEFE_OF_CORRECCION = 4;
var JEFE_OF_CORR_Y_DIC = 6;
var JEFE_DEP_AUD_PAT = 7;

/**
 * Controla el valor del hidden el cual indica
 * en que sección se encuentra el usuario.
 * Esta variable es utilizada por JAVA.
 * @author Oscar German Beltrán Ortega
 */
function setToFormSeccionActual(){
	/*NO APLICA SE REQUIERE MENCIONAR ESTA FUNCION 
	 * NO BORRAR
	 **/
}

function asignaRolJefe(paramRol) {	
	if (paramRol == JEFE_OF_CORRECCION ||
		paramRol == JEFE_OF_CORR_Y_DIC || 
		paramRol == JEFE_DEP_AUD_PAT) {
		ROL_JEFE_SATICB = true;
	} else {
		ROL_JEFE_SATICB = false;
	}
} 
/**
 * Función que se ejecuta al seleccionar una promocion desde la pantalla de consulta
 * de los seguimientos, ejecuta la consulta para mostrar la informacion de la promocion
 * seleccionada
 * @author Oscar German Beltrán Ortega
 */
function muestraSaticB(){
	
	//elimina el domicilio de session
	$.postJSON(jsContextoPromocion+"seguimiento/generico/removerDomicilioSession.do", null, function(data) {					
	}).error(function(data){ 					
		validarSesionExpirada(data);
	}).complete(function(){
		//Instrucciones para el complete													
	});	
	
	var estatusPromocion = 0;
	var fechaNotificacion = null;
	var esNuevoEstatusObra = true;
	var habilitaBtnInvitacion = false;
	var idPromocion = $('#:checked').val();
		if(idPromocion != null) {
			
			var sPromocion = '{"cvePromocion":'+idPromocion+'}';
			var promocion = jQuery.parseJSON(sPromocion);
			// Buscamos el elemento
			$.postJSON_Sync(jsContextoPromocion + "seguimiento/saticb/muestraSaticB.do", promocion, function(data) {
				//se limpia campo de pagos
				$("form#regularizarObraGenericoTABForm #cveRegulaPagos").val("");
				// se oculta el boton de generar invitacion
				$("form#seguimientoSaticbTABForm #btnGenInvitaSaticB").hide();
				//para llenar el hidden cvePromocion de cada TAB
				$("form#derivarFiscalizacionTABForm #cvePromocion").val(data.cvePromocion);
				$("form#regularizarObraGenericoTABForm #cvePromocion").val(data.cvePromocion);
				$("form#seguimientoSaticbTABForm #cvePromocion").val(data.cvePromocion);
				$("form#cancelacionGenericoTabForm #cvePromocion").val(data.cvePromocion);
				$("form#autAviDictamenGenericoTabForm #cvePromocion").val(data.cvePromocion);
				$("form#derivarSubdelegacionGenericoTabForm #cvePromocion").val(data.cvePromocion);
				$("form#derivarSubdelegacionGenericoTabForm #cvePromocion").val(data.cvePromocion);
				$("form#derivarSubdelegacionGenericoTabForm #fechaEmisionOficioGenerico").val(data.fechaOficioPromocion);
				$("form#estatusObraTABForm #cvePromocion").val(data.cvePromocion);
				$("form#estatusObraTABForm #numeroDeRegistroDeObra").val(data.registroObra);
				$("form#autAviDictamenGenericoTabForm #fechaEmisionOficioDictamenGenerico").val(data.fechaOficioPromocion);
				
				// ROL
				$("form#seguimientoSaticBForm #rolGenericoSaticB").val(data.rolUsuario);
				asignaRolJefe(data.rolUsuario);
				
				$("form#seguimientoSaticBForm #desCriterioSeleccion").html(data.desCriterioSeleccion);
				$("form#seguimientoSaticBForm #numFolioPromocion").html(data.numFolioPromocion);
				$("form#seguimientoSaticBForm #fecOficioSaticb").html(data.fechaOficioPromocion);
				$("form#seguimientoSaticBForm #numOficioPromocion").html(data.numOficioPromocion);
				
				//datos de la obra
				$("form#seguimientoSaticBForm #saticb").html(data.txtSaticb);
				$("form#seguimientoSaticBForm #registroObra").html(data.registroObra);
				$("form#seguimientoSaticBForm #calleObra").html(data.calleObra);
				$("form#seguimientoSaticBForm #coloniaObra").html(data.coloniaObra);
				$("form#seguimientoSaticBForm #numeroExteriorObra").html(data.numExteriorObra);
				$("form#seguimientoSaticBForm #numeroInteriorObra").html(data.numInteriorObra);
				$("form#seguimientoSaticBForm #codigoPostalObra").html(data.codigoPostalObra);
				
				//registro patronal para regularizar obra
				$("form#regularizarObraGenericoTABForm #registroPatronalPagosDt").val(data.registroPatronal);
				
				//para el tab de cancelacion
				$("form#cancelacionGenericoTabForm #fechaEmisionOficioGenerico").val(data.fechaOficioPromocion);				
				estatusPromocion = data.cveEstatus;				
				activeTab = "seguimientoSaticbTAB_saticb";				
				
				if(data.segSaticTabVo != null && data.segSaticTabVo.fechaNotificacion!= null && data.segSaticTabVo.fechaNotificacion != ''){
					fechaNotificacion = data.segSaticTabVo.fechaNotificacion;
					// Verifica si existe la fecha de atencion
					if (data.segEstatusObraVo!=null && data.segEstatusObraVo.fechaAtencionOficio!=null && data.segEstatusObraVo.fechaAtencionOficio.length > 0) {
						jsBTieneFecAtencion = true;
					} else {
						jsBTieneFecAtencion = false;
					}
					$("form#seguimientoSaticbTABForm #fechaNotificaSaticb").val(data.segSaticTabVo.fechaNotificacion);
					$( "form#derivarFiscalizacionTABForm #fechaNotificacionOficioFiscalizacion").val(data.segSaticTabVo.fechaNotificacion);
					//para el tab de cancelacion
					$("form#cancelacionGenericoTabForm #fechaNotificacionOficioGenerico").val(data.segSaticTabVo.fechaNotificacion);
					//fecha de notificacion para pagos
					$("form#regularizarObraGenericoTABForm #fechaNotificacionHdn").val(data.segSaticTabVo.fechaNotificacion);
					//fecha de notificacion para derivar a subdelegacion
					$("form#derivarSubdelegacionGenericoTabForm #fechaNotificacionOficioGenerico").val(data.segSaticTabVo.fechaNotificacion);
					//fecha de notificacion para dictamen
					$("form#autAviDictamenGenericoTabForm #fechaNotificacionOficioDictamenGenerico").val(data.segSaticTabVo.fechaNotificacion);
					$("form#seguimientoSaticbTABForm #observacionesSegSaticb").val(data.segSaticTabVo.observaciones);
					
				}
				
				//seccion guardado parcial estatus obra
				//if(data.segEstatusObraVo != null && data.segEstatusObraVo.regularizarObra != null && data.segEstatusObraVo.regularizarObra){
				if(data.segEstatusObraVo != null && jsBTieneFecAtencion){
					$("form#estatusObraTABForm #fecAtencionOficioEstObraGenerico").val(data.segEstatusObraVo.fechaAtencionOficio);					
					$("form#estatusObraTABForm #cbxRegulaObraEstObraGen").attr('checked', data.segEstatusObraVo.regularizarObra);
					$("form#estatusObraTABForm #fecAtencionOficioEstObraGenerico").prop('disabled','disabled');
					$("form#estatusObraTABForm #cbxRegulaObraEstObraGen").prop('disabled','disabled');
					$("form#estatusObraTABForm #btnGuardarEstObraGen").prop('disabled','disabled');					
					//fecha atn del tab de seguimiento
					$("form#seguimientoSaticbTABForm #fecAtenOficioSaticb").val(data.segEstatusObraVo.fechaAtencionOficio);
					$("#spnFecAtnEstObra").hide();
					esNuevoEstatusObra = false;
				}else{
					esNuevoEstatusObra = true;
					$( "form#seguimientoSaticbTABForm #fecAtenOficioSaticb").val('');
				}
				
				//muestra domicilio Geografico si existe
				if( data.domicilioGeografico != null ){
					$("form#seguimientoSaticBForm #registroPatronal").html(data.registroPatronal);
					$("form#seguimientoSaticBForm #razonSocial").html(data.nomRazonSocialPatron);

					$("form#seguimientoSaticBForm #numeroExterior").html(data.domicilioGeografico.numextnum);
					$("form#seguimientoSaticBForm #calle").html(data.domicilioGeografico.dgVialidadByCveViaPrin.nomVia);
					$("form#seguimientoSaticBForm #codigoPostal").html(data.domicilioGeografico.dgCodigosPostales.id.codigo);
					$("form#seguimientoSaticBForm #colonia").html(data.domicilioGeografico.dgAsentamiento.nomAsen);

					if(data.domicilioGeografico.numintnum != null && data.domicilioGeografico.numintnum != undefined){
						$("form#seguimientoSaticBForm #numeroInterior").html(data.domicilioGeografico.numintnum);
					}
					
					
				}else{
					//si no existe registro en DOmGeo se saca la info de SAtPatron y sat_ubicacion
					//Datos del Patrón Responsable de la Obra
					$("form#seguimientoSaticBForm #registroPatronal").html(data.registroPatronal);
					$("form#seguimientoSaticBForm #razonSocial").html(data.nomRazonSocialPatron);
					$("form#seguimientoSaticBForm #calle").html(data.callePatron);
					$("form#seguimientoSaticBForm #colonia").html(data.coloniaPatron);
					$("form#seguimientoSaticBForm #numeroExterior").html(data.numExteriorPatron);
					$("form#seguimientoSaticBForm #numeroInterior").html(data.numInteriorPatron);
					$("form#seguimientoSaticBForm #codigoPostal").html(data.codigoPostalPatron);
					$("form#seguimientoSaticBForm #cvePatronObraSaticb").val(data.cvePatron);

				}

				oDgDatosSeguimientoSaticB.dialog("open");
				desHabilitaCampo("form#seguimientoSaticbTABForm #cbxEstatusObraSaticB");
				$('#cbxEstatusObraSaticB').attr('checked', false);

			}).error(function(data){ 
				validarSesionExpirada(data);
			}).complete(function(){
				//Instrucciones que inicializan los tabs, un tab un metodo de inicializacion desde aqui
				
				initTabDerivarFiscalizacion();
				
				
				//initTabSeguimientoSaticB(false);
				initTabSeguimientoSaticBInicio();
				initTabRegularizarObra();
				initTabCancelacionGenerico();
				//false si es guardado parcial , true si es nuevo
				initTabEstatusObraGenerico(esNuevoEstatusObra);
				initTabAvisoDictamenGenerico();
				initTabDerivarSubdelegacionGenerico();
				
				asignaEstadoInicialPantallaSeg(estatusPromocion,fechaNotificacion);
				//alert('pausa');
				aplicaReglasSaticB();
				desbloquear();
				
				
				//iniciaRestricciones();
				
			});
	
		}
	}


function initTabSeguimientoSaticBInicio(){

	$( "form#seguimientoSaticbTABForm #fechaNotificaSaticb").datepicker(fechaSeguimiento());
	
	//	$.postJSON(getAppContextParaJS() + "/promocion/seguimiento/generico/obtenerFechaServidor.do", null,function(data) {
	//		}).error(function(data){
	//			validarSesionExpirada(data);
	//		}).complete(function(data){
	//			$("form#seguimientoSaticbTABForm #fechaNotificaSaticb").datepicker('option', 'maxDate', data.responseText);
	//		});
	//
	//	$.postJSON(getAppContextParaJS() + "/promocion/seguimiento/generico/obtenerFechaServidorMinima.do", null,function(data) {
	//	}).error(function(data){
	//		validarSesionExpirada(data);
	//	}).complete(function(data){
	//		$("form#seguimientoSaticbTABForm #fechaNotificaSaticb").datepicker('option', 'minDate', data.responseText);
	//	});
	//	
	
	//Estilos
	$( "form#seguimientoSaticbTABForm #fechaNotificaSaticb").addClass("red");
	$( "form#seguimientoSaticbTABForm #observacionesSegSaticb").addClass("red");
	$( "form#seguimientoSaticbTABForm #fechaNotificaSaticb").removeAttr('disabled');

	$('#spnFecNotSaticb').hide();
	
	//deshabilita fechas
	$( "form#seguimientoSaticbTABForm #fechaCancelaSaticb").prop('disabled','disabled');
	$( "form#seguimientoSaticbTABForm #fechaDerSubdelSaticb").prop('disabled','disabled');
	$( "form#seguimientoSaticbTABForm #fecAviDictSaticb").prop('disabled','disabled');
	$( "form#seguimientoSaticbTABForm #fechaDerFiscaSaticb").prop('disabled','disabled');
	$( "form#seguimientoSaticbTABForm #fecAtenOficioSaticb").prop('disabled','disabled');
	$( "form#seguimientoSaticbTABForm #cbxEstatusObraSaticB").prop('disabled','disabled');
	$( "form#seguimientoSaticbTABForm #fecSolCorrSaticb").prop('disabled','disabled');
	$( "form#seguimientoSaticbTABForm #fecOficioInvSaticb").prop('disabled','disabled');
	$( "form#seguimientoSaticbTABForm #fecSolCorrIniSaticb").prop('disabled','disabled');
	$( "form#seguimientoSaticbTABForm #fecSolCorrFinSaticb").prop('disabled','disabled');
	
	//limpia fechas
	$( "form#seguimientoSaticbTABForm #fechaCancelaSaticb").val('');
	$( "form#seguimientoSaticbTABForm #fechaDerSubdelSaticb").val('');
	$( "form#seguimientoSaticbTABForm #fecAviDictSaticb").val('');
	$( "form#seguimientoSaticbTABForm #fechaDerFiscaSaticb").val('');
	$( "form#seguimientoSaticbTABForm #fecSolCorrSaticb").val('');
	$( "form#seguimientoSaticbTABForm #fecOficioInvSaticb").val('');
	$( "form#seguimientoSaticbTABForm #fecSolCorrIniSaticb").val('');
	$( "form#seguimientoSaticbTABForm #fecSolCorrFinSaticb").val('');
	$( "form#seguimientoSaticbTABForm #btnGuardarSegSaticb").prop('disabled',false);
	$("form#seguimientoSaticbTABForm #observacionesSegSaticb").prop('disabled',false);
	$("form#seguimientoSaticbTABForm #observacionesSegSaticb").addClass("red");	 
 }


function aplicaReglasSaticB(){
	//$("#accessTabs").val("seguimientoSaticbTAB_saticb-cancelacionGenericoTab_saticb-derivarSubdelegacionGenericoTab_saticb-autAviDictamenGenericoTab_saticb-estatusObraTAB_saticb-regularizarObraGenericoTAB_saticb-derivarFiscalizacionTAB_saticb");
	setTabDesHabilitado("cancelacionGenericoTab_saticb");
	setTabDesHabilitado("derivarSubdelegacionGenericoTab_saticb");
	setTabDesHabilitado("derivarFiscalizacionTAB_saticb");
	setTabDesHabilitado("autAviDictamenGenericoTab_saticb");
	setTabDesHabilitado("estatusObraTAB_saticb");
	setTabDesHabilitado("regularizarObraGenericoTAB_saticb");
	
	if( ROL_JEFE_SATICB){
		//Rglas para seguimiento
		if(!validaFechaNotOfi()){
			setTabHabilitado("seguimientoSaticbTAB_saticb");
			setTabHabilitado("cancelacionGenericoTab_saticb");
			//setTabHabilitado("derivarSubdelegacionGenericoTab_saticb");
//			setTabHabilitado("derivarFiscalizacionTAB_saticb");
			setTabHabilitado("autAviDictamenGenericoTab_saticb");
			setTabDesHabilitado("estatusObraTAB_saticb");
			setTabDesHabilitado("regularizarObraGenericoTAB_saticb");
			
			desHabilitaCampo("#cbxEstatusObraSaticB");
			$('#cbxEstatusObraSaticB').attr('checked', false);
		}
		if(!validaFechaNotOfi() && validaFechaCancelacion()){
			setTabHabilitado("seguimientoSaticbTAB_saticb");
			setTabHabilitado("cancelacionGenericoTab_saticb");
			setTabDesHabilitado("derivarSubdelegacionGenericoTab_saticb");
			setTabDesHabilitado("autAviDictamenGenericoTab_saticb");
			setTabDesHabilitado("derivarFiscalizacionTAB_saticb");
			setTabDesHabilitado("estatusObraTAB_saticb");
		}
		if(validaFechaNotOfi()){
			setTabHabilitado("seguimientoSaticbTAB_saticb");
			setTabHabilitado("cancelacionGenericoTab_saticb");
			setTabHabilitado("derivarSubdelegacionGenericoTab_saticb");
			setTabHabilitado("autAviDictamenGenericoTab_saticb");
			setTabHabilitado("derivarFiscalizacionTAB_saticb");
			setTabDesHabilitado("estatusObraTAB_saticb");
			//desHabilitaCampo('#cbxEstatusObraSaticB');
			
		}
		if(validaFechaNotOfi() && validaEstatusObra()){
			setTabHabilitado("seguimientoSaticbTAB_saticb");
			setTabDesHabilitado("cancelacionGenericoTab_saticb");
			setTabDesHabilitado("derivarSubdelegacionGenericoTab_saticb");
			setTabDesHabilitado("autAviDictamenGenericoTab_saticb");
			setTabDesHabilitado("derivarFiscalizacionTAB_saticb");
			setTabHabilitado("estatusObraTAB_saticb");
		}
		if(validaFechaNotOfi() && validaFechaCancelacion()){
			setTabHabilitado("seguimientoSaticbTAB_saticb");
			setTabHabilitado("cancelacionGenericoTab_saticb");
			setTabDesHabilitado("derivarSubdelegacionGenericoTab_saticb");
			setTabDesHabilitado("autAviDictamenGenericoTab_saticb");
			setTabDesHabilitado("derivarFiscalizacionTAB_saticb");
			setTabDesHabilitado("estatusObraTAB_saticb");
		}
		if(validaFechaNotOfi() && validaFechaDerSub()){
			setTabHabilitado("seguimientoSaticbTAB_saticb");
			setTabDesHabilitado("cancelacionGenericoTab_saticb");
			setTabHabilitado("derivarSubdelegacionGenericoTab_saticb");
			setTabDesHabilitado("autAviDictamenGenericoTab_saticb");
			setTabDesHabilitado("derivarFiscalizacionTAB_saticb");
			setTabDesHabilitado("estatusObraTAB_saticb");
		}
		if(!validaFechaNotOfi() && validaFechaDerSub()){
			setTabHabilitado("seguimientoSaticbTAB_saticb");
			setTabDesHabilitado("cancelacionGenericoTab_saticb");
			setTabHabilitado("derivarSubdelegacionGenericoTab_saticb");
			setTabDesHabilitado("autAviDictamenGenericoTab_saticb");
			setTabDesHabilitado("derivarFiscalizacionTAB_saticb");
			setTabDesHabilitado("estatusObraTAB_saticb");
		}
		if(validaFechaNotOfi() && validaFechaDerFisca()){
			setTabHabilitado("seguimientoSaticbTAB_saticb");
			setTabDesHabilitado("cancelacionGenericoTab_saticb");
			setTabDesHabilitado("derivarSubdelegacionGenericoTab_saticb");
			setTabDesHabilitado("autAviDictamenGenericoTab_saticb");
			setTabHabilitado("derivarFiscalizacionTAB_saticb");
			setTabDesHabilitado("estatusObraTAB_saticb");
		}
		if(validaFechaNotOfi() && validaFechaAviDic()){
			setTabHabilitado("seguimientoSaticbTAB_saticb");
			setTabDesHabilitado("cancelacionGenericoTab_saticb");
			setTabDesHabilitado("derivarSubdelegacionGenericoTab_saticb");
			setTabHabilitado("autAviDictamenGenericoTab_saticb");
			setTabDesHabilitado("derivarFiscalizacionTAB_saticb");
			setTabDesHabilitado("estatusObraTAB_saticb");
		}
		if(!validaFechaNotOfi() && validaFechaDerFisca()){
			setTabHabilitado("seguimientoSaticbTAB_saticb");
			setTabDesHabilitado("cancelacionGenericoTab_saticb");
			setTabDesHabilitado("derivarSubdelegacionGenericoTab_saticb");
			setTabDesHabilitado("autAviDictamenGenericoTab_saticb");
			setTabHabilitado("derivarFiscalizacionTAB_saticb");
			setTabDesHabilitado("estatusObraTAB_saticb");
		}
		if(!validaFechaNotOfi() && validaFechaAviDic()){
			setTabHabilitado("seguimientoSaticbTAB_saticb");
			setTabDesHabilitado("cancelacionGenericoTab_saticb");
			setTabDesHabilitado("derivarSubdelegacionGenericoTab_saticb");
			setTabHabilitado("autAviDictamenGenericoTab_saticb");
			setTabDesHabilitado("derivarFiscalizacionTAB_saticb");
			setTabDesHabilitado("estatusObraTAB_saticb");
		}
		
		
		if(validaFechaNotOfi() && validaFechaAtencion()){
			setTabHabilitado("seguimientoSaticbTAB_saticb");
			setTabDesHabilitado("cancelacionGenericoTab_saticb");
			setTabDesHabilitado("derivarSubdelegacionGenericoTab_saticb");
			setTabDesHabilitado("derivarFiscalizacionTAB_saticb");
			setTabDesHabilitado("autAviDictamenGenericoTab_saticb");
			setTabHabilitado("estatusObraTAB_saticb");
			setTabDesHabilitado("regularizarObraGenericoTAB_saticb");			
			changeTab("seguimientoSaticbTAB_saticb");
			
		}
		if(validaFechaNotOfi() && validaFechaAtencion() && validaRegularizaObra()){
			setTabHabilitado("seguimientoSaticbTAB_saticb");
			setTabDesHabilitado("cancelacionGenericoTab_saticb");
			setTabDesHabilitado("derivarSubdelegacionGenericoTab_saticb");
			setTabDesHabilitado("derivarFiscalizacionTAB_saticb");
			setTabDesHabilitado("autAviDictamenGenericoTab_saticb");
			setTabHabilitado("estatusObraTAB_saticb");
			setTabHabilitado("regularizarObraGenericoTAB_saticb");			
			changeTab("seguimientoSaticbTAB_saticb");
			
		}
		if(validaFechaNotOfi() && validaFechaAtencion() && !validaRegularizaObra()){
			setTabHabilitado("seguimientoSaticbTAB_saticb");
			setTabDesHabilitado("cancelacionGenericoTab_saticb");
			setTabDesHabilitado("derivarSubdelegacionGenericoTab_saticb");
			setTabDesHabilitado("derivarFiscalizacionTAB_saticb");
			setTabDesHabilitado("autAviDictamenGenericoTab_saticb");
			setTabHabilitado("estatusObraTAB_saticb");
			setTabDesHabilitado("regularizarObraGenericoTAB_saticb");			
			changeTab("seguimientoSaticbTAB_saticb");
			
		}
		
	}else{
		setTabHabilitado("seguimientoSaticbTAB_saticb");
		setTabDesHabilitado("cancelacionGenericoTab_saticb");
		setTabDesHabilitado("derivarSubdelegacionGenericoTab_saticb");
		setTabDesHabilitado("derivarFiscalizacionTAB_saticb");
		setTabDesHabilitado("autAviDictamenGenericoTab_saticb");
		setTabDesHabilitado("estatusObraTAB_saticb");
		setTabDesHabilitado("regularizarObraGenericoTAB_saticb");
		
	}
		
}

function validaFechaNotOfi(){
	if( $("#fechaNotificaSaticb").val() != '' && $('#fechaNotificaSaticb').is('[disabled]')){
		return true;
	}else{
		return false
	}
}

function validaFechaAtencion(){
	if( $("#fecAtenOficioSaticb").val() != ''){
		return true;
	}else{
		return false
	}
}

function validaFechaDerSub(){
	if( $("#fechaDerivarSubdel").val() != ''){
		return true;
	}else{
		return false
	}
}
function validaFechaDerFisca(){
	if( $("#fecDerivacionGenerica").val() != ''){
		return true;
	}else{
		return false
	}
}

function validaFechaAviDic(){
	if( $("#fecAvisoDictGenericoTab").val() != ''){
		return true;
	}else{
		return false
	}
}

function validaFechaCancelacion(){
	if( $("#fechaCancelacionCGT").val() != ''){
		return true;
	}else{
		return false
	}
}

function validaEstatusObra(){
	var cbxEstatusObra = $("form#seguimientoSaticbTABForm #cbxEstatusObraSaticB").is(":checked");
	if(cbxEstatusObra == true && jsBTieneFecAtencion){
		return true;
	}else{
		return false;
	}
		
}
function validaRegularizaObra(){
	var cbxEstatusObra = $("form#estatusObraTABForm #cbxRegulaObraEstObraGen").is(":checked");
	if(cbxEstatusObra == true && jsBTieneFecAtencion){
		return true;
	}else{
		return false;
	}
		
}




/**
 * Función que inicializael dialogo principal de Seguimiento SaticB
 * @author Oscar German Beltrán Ortega
 */
function inicializaDialogSaticb(){
	
	var idPromocion = $('#:checked').val();
		//dialogo principal de SaticB
	oDgDatosSeguimientoSaticB = $(idDatosSeguimientoSaticB).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 1130,
		closeOnEscape: false,
		open:function(event, ui){

		},
		close:function(event,ui){
			
			//destruimos el DT de pagos para que se reinicie..
			//if(oDtDatosPagosGenerico != undefined){
				//oDtDatosPagosGenerico.fnDestroy();
				
				//$(dtAnexoPagosGenDetalle).html('');

				//borramos los hidden de los jsps
				$("form#regularizarObraGenericoTABForm #cvePromocion").val('')
				$("form#regularizarObraGenericoTABForm #cveRegulaPagos").val('')
				//$("form#anexoPagosGenericoForm #cvePromocion").val('')
				/$("form#anexoPagosGenericoForm #cveRegulaPagosGral").val('')
				//limpia los campos de regularizar obra
				$('form#regularizarObraGenericoTABForm input[type=text]').val("");
				$('form#regularizarObraGenericoTABForm input[type=hidden]').val("");
				
				$.postJSON(jsContextoPromocion+"seguimiento/generico/removerDomicilioSession.do", null, function(data) {					
				}).error(function(data){ 					
					validarSesionExpirada(data);
				}).complete(function(){
					//Instrucciones para el complete													
				});	
				
				oDgConsultaRegistros.fnDraw();	
			//}
			
		},
		buttons: {
			"Regresar": function() { 
				$(this).dialog("close"); 								
			} 
		}
	});
	
	//ejecucion
	muestraSaticB();
	desbloquear();

}


/**
 * Función utilizada para completar el flujo de Cancelacion una vez que ha sido guardada la 
 * cancelacion
 * @author Oscar German Beltrán Ortega
 */
function completaCancelacionAuxiliar(){
	
	$("form#seguimientoSaticbTABForm #fechaCancelaSaticb").val($("form#cancelacionGenericoTabForm #fechaCancelacionCGT").val());
	
	//fiscalizacion
	//setTabDesHabilitado("derivarFiscalizacionTAB_saticb");
	
	//subdelegacion
	//setTabDesHabilitado("derivarSubdelegacionGenericoTab_saticb");
	
	//aviso dictamen
	//setTabDesHabilitado("autAviDictamenGenericoTab_saticb");
	
	//cancelacion
	$('form#cancelacionGenericoTabForm input[type=text]').prop('disabled','disabled');
	$('form#cancelacionGenericoTabForm input[type=text]').removeClass("red");
	$('form#cancelacionGenericoTabForm input[type=button]').prop('disabled','disabled');
	$('form#cancelacionGenericoTabForm input[type=select]').prop('disabled','disabled');
	$('form#cancelacionGenericoTabForm input[type=select]').removeClass("red");
	$('#spnFechaCancelacionCGT').hide();
	$('#spnFecNotSaticb').hide();
	
	//seguimiento tab
	$('form#seguimientoSaticbTABForm input[type=text]').prop('disabled','disabled');
	$('form#seguimientoSaticbTABForm input[type=text]').removeClass("red");
	$('form#seguimientoSaticbTABForm input[type=button]').prop('disabled','disabled');
	$('form#seguimientoSaticbTABForm input[type=checkbox]').prop('disabled','disabled');
	$('form#seguimientoSaticbTABForm input[type=checkbox]').removeClass("red");
	
	//btn de domicilio
	$("#btnAgreModificDomSaticB").prop('disabled','disabled');
	
	changeTab('seguimientoSaticbTAB_saticb');
	guardarSeguimientoGenerico();
	
}

/**
 * Función utilizada para completar el flujo de Derivar a fiscalizacion una vez que ha sido guardada la 
 * Derivaciocion
 * @author Oscar German Beltrán Ortega
 */
function completaDerivFiscalizacionAuxiliar(){

	$("form#seguimientoSaticbTABForm #fechaDerFiscaSaticb").val($("form#derivarFiscalizacionTABForm #fecDerivacionGenerica").val());
	

	
	//subdelegacion
	//setTabDesHabilitado("derivarSubdelegacionGenericoTab_saticb");
	
	//aviso dictamen
	//setTabDesHabilitado("autAviDictamenGenericoTab_saticb");
	
	//cancelacion
	//setTabDesHabilitado("cancelacionGenericoTab_saticb");

	//seguimiento tab
	$('form#seguimientoSaticbTABForm input[type=text]').prop('disabled','disabled');
	$('form#seguimientoSaticbTABForm input[type=text]').removeClass("red");
	$('form#seguimientoSaticbTABForm input[type=button]').prop('disabled','disabled');
	$('form#seguimientoSaticbTABForm input[type=checkbox]').prop('disabled','disabled');
	$('form#seguimientoSaticbTABForm input[type=checkbox]').removeClass("red");
	changeTab('seguimientoSaticbTAB_saticb');
	
	//btn de domicilio
	$("#btnAgreModificDomSaticB").prop('disabled','disabled');
	
	guardarSeguimientoGenerico();
}

/**
 * Función utilizada para completar el flujo de Derivar a Subdelegacion una vez que ha sido guardada la 
 * Derivacion
 * @author Oscar German Beltrán Ortega
 */
function completaDerivSubdelegacion(){
	$("form#seguimientoSaticbTABForm #fechaDerSubdelSaticb").val($("form#derivarSubdelegacionGenericoTabForm #fechaDerivarSubdel").val());
	
	//fiscalizacion
	//setTabDesHabilitado("derivarFiscalizacionTAB_saticb");
	
	//subdelegacion
	$('form#derivarSubdelegacionGenericoTabForm input[type=text]').prop('disabled','disabled');
	$('form#derivarSubdelegacionGenericoTabForm input[type=text]').removeClass("red");
	$('form#derivarSubdelegacionGenericoTabForm input[type=button]').prop('disabled','disabled');
	
	
	//aviso dictamen
	//setTabDesHabilitado("autAviDictamenGenericoTab_saticb");
	
	//cancelacion
//	setTabDesHabilitado("cancelacionGenericoTab_saticb");
	
	//seguimiento tab
	$('form#seguimientoSaticbTABForm input[type=text]').prop('disabled','disabled');
	$('form#seguimientoSaticbTABForm input[type=text]').removeClass("red");
	$('form#seguimientoSaticbTABForm input[type=button]').prop('disabled','disabled');
	$('form#seguimientoSaticbTABForm input[type=checkbox]').prop('disabled','disabled');
	$('form#seguimientoSaticbTABForm input[type=checkbox]').removeClass("red");
	changeTab('seguimientoSaticbTAB_saticb');
	
	//btn de domicilio
	$("#btnAgreModificDomSaticB").prop('disabled','disabled');
	
	guardarSeguimientoGenerico();
}


/**
 * Función utilizada para completar el flujo de Autorizar aviso a Dictamen una vez que ha sido guardada la 
 * Autorizacion
 * @author Oscar German Beltrán Ortega
 */
function completaAutAviDicAux(){
	$("form#seguimientoSaticbTABForm #fecAviDictSaticb").val($("form#autAviDictamenGenericoTabForm #fecAvisoDictGenericoTab").val());
	$("form#seguimientoSaticbTABForm #fecSolCorrIniSaticb").val($("form#autAviDictamenGenericoTabForm #fecIniPeriodoDictGenericoTab").val());
	$("form#seguimientoSaticbTABForm #fecSolCorrFinSaticb").val($("form#autAviDictamenGenericoTabForm #fecFinPeriodoDictGenericoTab").val());
	//fiscalizacion
	//setTabDesHabilitado("derivarFiscalizacionTAB_saticb");
	
	//subdelegacion
	//setTabDesHabilitado("derivarSubdelegacionGenericoTab_saticb");
	
	//aviso dictamen
	$('form#autAviDictamenGenericoTabForm input[type=button]').prop('disabled','disabled');
	
	//cancelacion
	//setTabDesHabilitado("cancelacionGenericoTab_saticb");
	
	//seguimiento tab
	$('form#seguimientoSaticbTABForm input[type=text]').prop('disabled','disabled');
	$('form#seguimientoSaticbTABForm input[type=text]').removeClass("red");
	$('form#seguimientoSaticbTABForm input[type=button]').prop('disabled','disabled');
	$('form#seguimientoSaticbTABForm input[type=checkbox]').prop('disabled','disabled');
	$('form#seguimientoSaticbTABForm input[type=checkbox]').removeClass("red");
	$("form#seguimientoSaticbTABForm #observacionesSegSaticb").prop('disabled',true);
	$("form#seguimientoSaticbTABForm #observacionesSegSaticb").removeClass("red");
	changeTab('seguimientoSaticbTAB_saticb');
	
	//btn de domicilio
	$("#btnAgreModificDomSaticB").prop('disabled','disabled');
	
	guardarSeguimientoGenerico();
	
	//estatus Obra
	$('form#estatusObraTABForm input[type=text]').prop('disabled','disabled');
	$('form#estatusObraTABForm input[type=text]').removeClass("red");
	$('form#estatusObraTABForm input[type=button]').prop('disabled','disabled');
	$('form#estatusObraTABForm input[type=checkbox]').prop('disabled','disabled');
	$('form#estatusObraTABForm input[type=checkbox]').removeClass("red");
	

}

function actualizaDomiciliSaticB(){
	var registroPatronal=$("form#seguimientoSaticBForm #registroPatronal").text();
	var cvePatronPant = $("form#seguimientoSaticBForm #cvePatronObraSaticb").val();
	
	var sPatron = '{"patron":'+'"DOM_SATICB_PATRON_OBRA",'+
	'"cvePatron":"'+cvePatronPant+'"}';	
	
	var crcPatron = jQuery.parseJSON(sPatron);

	//bloquear();
	$.postJSON(jsContextoPromocion + "seguimiento/saticb/actualizaDomPatronObra.do", crcPatron, function(data) {	
		if(data != null){
			if(data.dgVialidadByCveViaPrin.nomVia != undefined && data.dgVialidadByCveViaPrin.nomVia != null){
				$('form#seguimientoSaticBForm #calle').html(data.dgVialidadByCveViaPrin.nomVia);
			}
			if(data.numextnum != undefined && data.numextnum != null){
				$('form#seguimientoSaticBForm #numeroExterior').html(data.numextnum);
			}
			
			if(data.numintnum != undefined && data.numintnum != null){
				$('form#seguimientoSaticBForm #numeroInterior').html(data.numintnum);
			}
			if(data.dgAsentamiento.nomAsen != undefined && data.dgAsentamiento.nomAsen != null){
				$('form#seguimientoSaticBForm #colonia').html(data.dgAsentamiento.nomAsen);	
			}
			if(data.dgCodigosPostales != undefined && data.dgCodigosPostales != null && data.dgCodigosPostales.id != null){
				$("form#seguimientoSaticBForm #codigoPostal").html(data.dgCodigosPostales.id.codigo);
			}
			
		}

	}).error(function(data){ 
		//desbloquear();
		alert("Error: Conexión no disponible, intente de nuevo");
	}).complete(function(){
		//desbloquear();
	});	
}


/**
 * Función que ejeucta la funcionalidad de Domicilios Genericos
 * @author Jorge Ventura Hernandez Almazan
 */
function cargaDomicilioSaticb(){
	var resultado = openWindowregistraDomicilioInegi(getAppContextParaJS(),'/promocion/seguimiento/saticb/solicitudDomGeograficoPatronObra.do',null,"actualizaDomiciliSaticB()");
	var registroPatronal=$("form#seguimientoSaticBForm #registroPatronal").text();
	var cvePatronPant = $("form#seguimientoSaticBForm #cvePatronObraSaticb").val();
	
	var sPatron = '{"patron":'+'"DOM_SATICB_PATRON_OBRA",'+
	'"cvePatron":"'+cvePatronPant+'"}';	
	
	var crcPatron = jQuery.parseJSON(sPatron);

	//bloquear();
	$.postJSON(jsContextoPromocion + "seguimiento/saticb/actualizaDomPatronObra.do", crcPatron, function(data) {	
		if(data != null){
			if(data.dgVialidadByCveViaPrin.nomVia != undefined && data.dgVialidadByCveViaPrin.nomVia != null){
				$('form#seguimientoSaticBForm #calle').html(data.dgVialidadByCveViaPrin.nomVia);
			}
			if(data.numextnum != undefined && data.numextnum != null){
				$('form#seguimientoSaticBForm #numeroExterior').html(data.numextnum);
			}
			
			if(data.numintnum != undefined && data.numintnum != null){
				$('form#seguimientoSaticBForm #numeroInterior').html(data.numintnum);
			}
			if(data.dgAsentamiento.nomAsen != undefined && data.dgAsentamiento.nomAsen != null){
				$('form#seguimientoSaticBForm #colonia').html(data.dgAsentamiento.nomAsen);	
			}
			if(data.dgCodigosPostales != undefined && data.dgCodigosPostales != null && data.dgCodigosPostales.id != null){
				$("form#seguimientoSaticBForm #codigoPostal").html(data.dgCodigosPostales.id.codigo);
			}
			
		}

	}).error(function(data){ 
		//desbloquear();
		alert("Error: Conexión no disponible, intente de nuevo");
	}).complete(function(){
		//desbloquear();
	});	

}

/**
 * Función que asigna el comportamiento inicial o el de guardado parcial de todo el flujo  de Seguimiento
 * de SAticB
 * @author Oscar German Beltrán Ortega
 */
function asignaEstadoInicialPantallaSeg(cveEstatus,fechaNotificacion){
	
	//asigna nombre funciones Auxiliares
	$("form#cancelacionGenericoTabForm #functionAuxCancelacion").val("completaCancelacionAuxiliar()");
	$("form#derivarFiscalizacionTABForm #functionAuxFiscalizacion").val("completaDerivFiscalizacionAuxiliar()");
	$("form#derivarSubdelegacionGenericoTabForm #functionAuxDerSubdelegacion").val("completaDerivSubdelegacion()");
	$("form#autAviDictamenGenericoTabForm #functionAuxAutAvisoDict").val("completaAutAviDicAux()");
	$("form#regularizarObraGenericoTABForm #functionAuxRegularizaObra").val("completaRegulaObraAuxB()");
	$("form#estatusObraTABForm #functionAuxEstatusObra").val("completaEstatusObraAuxB()");
	
	//funcion axuliliar para invitacion
	$("form#invitacionAntecedenteForm #funcionSeguimientoInv").val("completaFlujoInvSegSaticb()");
	
	$("#accessTabs").val("seguimientoSaticbTAB_saticb-cancelacionGenericoTab_saticb-derivarSubdelegacionGenericoTab_saticb-autAviDictamenGenericoTab_saticb-estatusObraTAB_saticb-regularizarObraGenericoTAB_saticb-derivarFiscalizacionTAB_saticb");
	
	
	
	if(cveEstatus == EN_PROCESO_NOTIFICACION_OFICIO_PROMOCION ){
		
		//setTabHabilitado("seguimientoSaticbTAB_saticb");
		$('form#seguimientoSaticBForm input[type=text]').val('');
		
		if(ROL_JEFE_SATICB && !jsBTieneFecAtencion) {
			//setTabHabilitado("cancelacionGenericoTab_saticb");
			//fiscalizacion
			if(fechaNotificacion == null){
				//setTabDesHabilitado("derivarFiscalizacionTAB_saticb");
				//setTabDesHabilitado("autAviDictamenGenericoTab_saticb");
			}else{
				//setTabHabilitado("derivarFiscalizacionTAB_saticb");
				//setTabHabilitado("autAviDictamenGenericoTab_saticb");
			}
			//setTabHabilitado("derivarSubdelegacionGenericoTab_saticb");
		} else {
		//auditor
			//setTabDesHabilitado("cancelacionGenericoTab_saticb");
			//setTabDesHabilitado("derivarFiscalizacionTAB_saticb");
			//setTabDesHabilitado("derivarSubdelegacionGenericoTab_saticb");
			//setTabDesHabilitado("autAviDictamenGenericoTab_saticb");
		}
		
		//bloquea tabs estado inicial
		setTabDesHabilitado("regularizarObraGenericoTAB_saticb");
		changeTab('seguimientoSaticbTAB_saticb');
		
		if(!jsBTieneFecAtencion){
			$( "form#seguimientoSaticbTABForm #cbxEstatusObraSaticB").removeAttr('disabled');
			$( "form#seguimientoSaticbTABForm #cbxEstatusObraSaticB").addClass("red");
			$("form#seguimientoSaticbTABForm #cbxEstatusObraSaticB").attr('checked', false);
		} else {
			$( "form#seguimientoSaticbTABForm #cbxEstatusObraSaticB").attr('checked', true);
			$( "form#seguimientoSaticbTABForm #cbxEstatusObraSaticB").prop('disabled','disabled');
			$("form#seguimientoSaticbTABForm #cbxEstatusObraSaticB").removeClass("red");
			//setTabHabilitado("estatusObraTAB_saticb");
		}		
		//para ambos casos , puede estar guardada la fecha notificacion (guardado parcial)
		if(fechaNotificacion==null) {
			$( "form#seguimientoSaticbTABForm #fechaNotificaSaticb").val('');
			$("form#seguimientoSaticbTABForm #observacionesSegSaticb").val('');
		} else {
			$( "form#seguimientoSaticbTABForm #fechaNotificaSaticb").prop('disabled','disabled');
			$( "form#seguimientoSaticbTABForm #fechaNotificaSaticb").val(fechaNotificacion);
			$( "form#seguimientoSaticbTABForm #fechaNotificaSaticb").removeClass("red");
		}
	
		//btn de domicilio
		$("#btnAgreModificDomSaticB").removeAttr('disabled');
		
	}else if(cveEstatus == PROMOCION_REGULARIZADA){
		//setTabHabilitado("seguimientoSaticbTAB_saticb");
		//setTabDesHabilitado("cancelacionGenericoTab_saticb");
		//setTabDesHabilitado("derivarFiscalizacionTAB_saticb");
		//setTabDesHabilitado("derivarSubdelegacionGenericoTab_saticb");//deshabilitado x que ya tiene fecha de notif
		//setTabDesHabilitado("autAviDictamenGenericoTab_saticb");
		//setTabHabilitado("estatusObraTAB_saticb");
		//setTabHabilitado("regularizarObraGenericoTAB_saticb");
		
		//validar con rol
		if($("form#seguimientoSaticBForm #RolUsuario").val() == JEFE_OF_CORRECCION ||
				   $("form#seguimientoSaticBForm #rolGenericoSaticB").val() == JEFE_OF_CORR_Y_DIC || 
				   $("form#seguimientoSaticBForm #rolGenericoSaticB").val() == JEFE_DEP_AUD_PAT){
			//setTabHabilitado("cancelacionGenericoTab_saticb");
			//setTabHabilitado("derivarFiscalizacionTAB_saticb");
		}else{
			//setTabDesHabilitado("cancelacionGenericoTab_saticb");
			//setTabDesHabilitado("derivarFiscalizacionTAB_saticb");
		}
		
		
		//ponemos fecha de notificacion
		$("form#seguimientoSaticbTABForm #fechaNotificaSaticb").val(fechaNotificacion);
		$("form#seguimientoSaticbTABForm #fechaNotificaSaticb").prop('disabled','disabled');
		$("form#seguimientoSaticbTABForm #cbxEstatusObraSaticB").attr('checked', true);
		
		$('form#seguimientoSaticbTABForm input[type=text]').prop('disabled','disabled');
		$('form#seguimientoSaticbTABForm input[type=text]').removeClass("red");
		$('form#seguimientoSaticbTABForm input[type=button]').prop('disabled','disabled');
		$('form#seguimientoSaticbTABForm input[type=checkbox]').prop('disabled','disabled');
		$('form#seguimientoSaticbTABForm input[type=checkbox]').removeClass("red");
		
		$('form#estatusObraTABForm input[type=text]').prop('disabled','disabled');
		$('form#estatusObraTABForm input[type=text]').removeClass("red");
		$('form#estatusObraTABForm input[type=button]').prop('disabled','disabled');
		$('form#estatusObraTABForm input[type=checkbox]').prop('disabled','disabled');
		$('form#estatusObraTABForm input[type=checkbox]').removeClass("red");
		

		changeTab('regularizarObraGenericoTAB_saticb');	
		
		//para ambos casos , puede estar guardada la fecha notificacion (guardado parcial)
		if(fechaNotificacion==null){
			$( "form#seguimientoSaticbTABForm #fechaNotificaSaticb").val('');
			$("form#seguimientoSaticbTABForm #observacionesSegSaticb").val('');
		}else{
			$( "form#seguimientoSaticbTABForm #fechaNotificaSaticb").datepicker('destroy');
			$( "form#seguimientoSaticbTABForm #fechaNotificaSaticb").val(fechaNotificacion);
		}
		
		//btn de domicilio
		$("#btnAgreModificDomSaticB").prop('disabled','disabled');
		
		
		//deshabilitar periodo
		desHabilitaCapturaFechasPeriodoRegulaObra();
		
	}
	
	
	
}


/**
 * Función utilizada para completar el flujo de Regularizar Obra una vez que ha sido guardada la 
 * Regulacion
 * @author Oscar German Beltrán Ortega
 */
function completaRegulaObraAuxB(){

	$("form#seguimientoSaticbTABForm #fecSolCorrIniSaticb").val($("form#regularizarObraGenericoTABForm #perRegularizaDel").val());
	$("form#seguimientoSaticbTABForm #fecSolCorrFinSaticb").val($("form#regularizarObraGenericoTABForm #perRegularizaAl").val());
	
	//fiscalizacion
	setTabDesHabilitado("derivarFiscalizacionTAB_saticb");
	
	//subdelegacion
	setTabDesHabilitado("derivarSubdelegacionGenericoTab_saticb");
	
	//aviso dictamen
	setTabDesHabilitado("autAviDictamenGenericoTab_saticb");
	
	//cancelacion
	setTabDesHabilitado("cancelacionGenericoTab_saticb");
		
	//seguimiento tab
	$('form#seguimientoSaticbTABForm input[type=text]').prop('disabled','disabled');
	$('form#seguimientoSaticbTABForm input[type=text]').removeClass("red");
	$('form#seguimientoSaticbTABForm input[type=button]').prop('disabled','disabled');
	$('form#seguimientoSaticbTABForm input[type=checkbox]').prop('disabled','disabled');
	$('form#seguimientoSaticbTABForm input[type=checkbox]').removeClass("red");
	
	//estatus Obra
	$('form#estatusObraTABForm input[type=text]').prop('disabled','disabled');
	$('form#estatusObraTABForm input[type=text]').removeClass("red");
	$('form#estatusObraTABForm input[type=button]').prop('disabled','disabled');
	$('form#estatusObraTABForm input[type=checkbox]').prop('disabled','disabled');
	$('form#estatusObraTABForm input[type=checkbox]').removeClass("red");
	
	//deshabilita el periodo
	$("form#regularizarObraGenericoTABForm #perRegularizaDel").removeClass("red");
	$("form#regularizarObraGenericoTABForm #perRegularizaAl").removeClass("red");
	$("form#regularizarObraGenericoTABForm #perRegularizaDel").datepicker('destroy');
	$("form#regularizarObraGenericoTABForm #perRegularizaAl").datepicker('destroy');
	$('#spnPeriodoRegObra').hide();
	
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
	
	//btn de domicilio
	$("#btnAgreModificDomSaticB").prop('disabled','disabled');
	
	guardarSeguimientoGenerico();
	guardarPeriodosPromocionSeguimientoSaticB($("form#regularizarObraGenericoTABForm #perRegularizaDel").val(),$("form#regularizarObraGenericoTABForm #perRegularizaAl").val());
}



/**
 * Función utilizada para completar el flujo de Estatus Obra una vez que ha sido guardada la 
 * Estatus Obra
 * @author Oscar German Beltrán Ortega
 */
function completaEstatusObraAuxB(){
	//alert("completaEstatusObraAuxB");

	$("form#seguimientoSaticbTABForm #fecAtenOficioSaticb").val($("form#estatusObraTABForm #fecAtencionOficioEstObraGenerico").val());
	
	$('form#estatusObraTABForm input[type=text]').prop('disabled','disabled');
	$('form#estatusObraTABForm input[type=text]').removeClass("red");
	$('form#estatusObraTABForm input[type=button]').prop('disabled','disabled');
	$('form#estatusObraTABForm input[type=checkbox]').prop('disabled','disabled');
	$('form#estatusObraTABForm input[type=checkbox]').removeClass("red");
	
	//seguimiento tab
	$('form#seguimientoSaticbTABForm input[type=text]').prop('disabled','disabled');
	$('form#seguimientoSaticbTABForm input[type=text]').removeClass("red");
	$('form#seguimientoSaticbTABForm input[type=checkbox]').prop('disabled','disabled');
	$('form#seguimientoSaticbTABForm input[type=checkbox]').removeClass("red");
	$("#spnFecAtnEstObra").hide();
	$("#spnFecNotSaticb").hide();
	
	//btn de domicilio
	$("#btnAgreModificDomSaticB").prop('disabled','disabled');
	
	guardarSeguimientoGenerico();
}



function validarRegPatronalSaticB(registroPatronal) {
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
						 jsMuestraInvitacionSaticB(id);
						 
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

function iniciaRestricciones(){
	//Derivacion a subdelegacion no debe permitir fecha anterior a la notificacion
	if($("form#seguimientoSaticbTABForm #fechaNotificaSaticb").val()!=''){
		$("form#derivarSubdelegacionGenericoTabForm #fechaDerivarSubdel").datepicker('option', 'minDate', $( "form#seguimientoSaticbTABForm #fechaNotificaSaticb").val());
	}	
}