// Dialog Confirmar Generico para Seguimiento SATIC B
var idDgConfirmarSATICBTab = "#dgConfirmarSaticB";
var oDgConfirmarSATICBTab;
var jsSaticBCheckEstatusObra = "form#seguimientoSaticbTABForm #cbxEstatusObraSaticB"; 
/**
 * Funcion donde se inicializa todo el comportamiento y/o funcionalidad que se deberia de cargar en el Ready de Jquery , 
 * se separo del Ready principal para facilitar la carga en diferentes momentos y no sea tan
 * pésada al iniciar la pantalla principal de consulta de promociones. 
 * funcionalidad inicial para la pestaña de Seguimiento SATIC B
 * @author Oscar Beltran
 * @version 1.0.1
 */
function initTabSeguimientoSaticB(habilitaBtnInvitacion){
	//alert("initTabSeguimientoSaticB: " + habilitaBtnInvitacion);
	//asigancion de calendario fecha Notificacion
	$( "form#seguimientoSaticbTABForm #fechaNotificaSaticb").datepicker({
		dateFormat: 'dd-mm-yy'
	});
	
	/*seccion para definir la fecha maxima del servidor y establecerle un limite maximo a las fechas
	 * maximas de los calendarios para las fechas siguientes , con el formato dd-MM-yyyy  */ 
	$.postJSON(getAppContextParaJS() + "/promocion/seguimiento/generico/obtenerFechaServidor.do", null,function(data) {
		}).error(function(data){
			validarSesionExpirada(data);
		}).complete(function(data){
			$("form#seguimientoSaticbTABForm #fechaNotificaSaticb").datepicker('option', 'maxDate', data.responseText);
		});

	
	/*seccion para definir la fecha minima del servidor y establecerle un limite minima a las fechas
	 * maximas de los calendarios para las fechas siguientes , con formato dd-MM-yyyy */ 
	$.postJSON(getAppContextParaJS() + "/promocion/seguimiento/generico/obtenerFechaServidorMinima.do", null,function(data) {
	}).error(function(data){
		validarSesionExpirada(data);
	}).complete(function(data){
		$("form#seguimientoSaticbTABForm #fechaNotificaSaticb").datepicker('option', 'minDate', data.responseText);
	});
	
	
	//Estilos
	$( "form#seguimientoSaticbTABForm #fechaNotificaSaticb").addClass("red");
	$( "form#seguimientoSaticbTABForm #observacionesSegSaticb").addClass("red");
	//fecha notificacion
	$( "form#seguimientoSaticbTABForm #fechaNotificaSaticb").removeAttr('disabled');
	//$( "form#seguimientoSaticbTABForm #fechaNotificaSaticb").val('');
	
	//checkbox
//	$( "form#seguimientoSaticbTABForm #cbxEstatusObraSaticB").removeAttr('disabled');
//	$("form#seguimientoSaticbTABForm #cbxEstatusObraSaticB").attr('checked', false);
//	$("form#seguimientoSaticbTABForm #cbxEstatusObraSaticB").removeClass("red");
	
	//btn fecha
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
	
	//$( "form#seguimientoSaticbTABForm #cbxEstatusObraSaticB").val('');
	

	$( "form#seguimientoSaticbTABForm #fecSolCorrSaticb").val('');
	$( "form#seguimientoSaticbTABForm #fecOficioInvSaticb").val('');
	$( "form#seguimientoSaticbTABForm #fecSolCorrIniSaticb").val('');
	$( "form#seguimientoSaticbTABForm #fecSolCorrFinSaticb").val('');
	$( "form#seguimientoSaticbTABForm #btnGuardarSegSaticb").prop('disabled',false);
	$("form#seguimientoSaticbTABForm #observacionesSegSaticb").prop('disabled',false);
	$("form#seguimientoSaticbTABForm #observacionesSegSaticb").addClass("red");
	
	 
 }

/**
 * Función que valida la fecha de notificacion 
 * @author Oscar German Beltrán Ortega
 */
function jsValidaFecNotifSaticb(fecIni){
	
	 $("#fecAvisoDictGenericoTab").val("");
	 $("#fechaDerivarSubdel").val("");
	 $("#fechaCancelacionCGT").val("");
	
	if(jsValidaFecha(fecIni)){
		 $( "form#seguimientoSaticbTABForm #fechaNotificaSaticb").val(fecIni);
		 $("#labelFechaNotificaSaticb").html('');
		 if(jsValidaVsfecOficioSaticB(fecIni)){
			 $( "form#seguimientoSaticbTABForm #fechaNotificaSaticb").val(fecIni);
			 $("#labelFechaNotificaSaticb").html('');
			 $("#spnFecNotSaticb").show();
			 ejecutaPasoFechaNotificacion();
		 }else{
			 $( "form#seguimientoSaticbTABForm #fechaNotificaSaticb").val("");
			 $("#labelFechaNotificaSaticb").html('<label class="etiquetaError" >La fecha de notificaci&oacute;n del oficio no puede ser menor a la fecha oficio de promoci&oacute;n</label>');
		 }
	}else{
		 $( "form#seguimientoSaticbTABForm #fechaNotificaSaticb").val("");
		 $("#labelFechaNotificaSaticb").html('<label class="etiquetaError" >La fecha de notificaci&oacute;n del oficio no puede ser mayor al dia actual</label>');
	}
}

/**
 * Funcion que valida la fecha que recibe como parametro contra la fecha del
 * oficio de la promocion
 * @param fecIni , fecha a Evaluar
 * @returns {Boolean}
 * @author Oscar German Beltran Ortega
 */
function jsValidaVsfecOficioSaticB(fecIni){
	var fecOficioPromo = $("form#seguimientoSaticBForm #fecOficioSaticb").text();
	
	var resp = false;
	if(fecIni != '' && fecOficioPromo != ''){
		if(jsValidaFechas(fecOficioPromo,fecIni)){
			 resp = true;
		 }
	}
	return resp;
}

/**
 * Función que ejecuta los pasos cuando se ingresa la fecha de notificacion
 * @author Oscar German Beltrán Ortega
 */
function ejecutaPasoFechaNotificacion(){
	
	$(".tab_content:first").show();
	//aqui se setea el hidden de fecha de Notificacion del tab de derivar fiscalizacion p validacion
	var fechaNotificacionSaticb = $( "form#seguimientoSaticbTABForm #fechaNotificaSaticb").val();
	
	
	
	$( "form#derivarFiscalizacionTABForm #fechaNotificacionOficioFiscalizacion").val(fechaNotificacionSaticb);
	
	//para el tab de cancelacion
	$("form#cancelacionGenericoTabForm #fechaNotificacionOficioGenerico").val(fechaNotificacionSaticb);
	//para el tab de aut avis dict
	$("form#autAviDictamenGenericoTabForm #fechaNotificacionOficioDictamenGenerico").val(fechaNotificacionSaticb);
	//para la pantalla de invitacion
	$("form#invitacionAntecedenteForm #fechaNotificacionInv").val(fechaNotificacionSaticb);
	//alert("fecha notb : " + $("form#invitacionAntecedenteForm #fechaNotificacionInv").val());
	//pasamos la fecha de notificacion para la pantalla de pagos 
	$("form#regularizarObraGenericoTABForm #fechaNotificacionHdn").val(fechaNotificacionSaticb); 
	//pasamos la fecha de notificacion a derivar fiscalizacion
	$("form#derivarSubdelegacionGenericoTabForm #fechaNotificacionOficioGenerico").val(fechaNotificacionSaticb);
	
	
}
		 
/**
 * Función que limpia la fecha de notificacion y regresa al estatus original el tab de seguimiento, 
 * antes de capturar la fecha de notificacion
 * @author Oscar German Beltrán Ortega
 */
function limpiaFechaNotificacionSaticb(){
	//pestañas
	//setTabDesHabilitado("derivarFiscalizacionTAB_saticb");
	//setTabDesHabilitado("regularizarObraGenericoTAB_saticb");
	//setTabDesHabilitado("estatusObraTAB_saticb");
	
	
	
	if($("form#seguimientoSaticBForm #RolUsuario").val() == JEFE_OF_CORRECCION ||
			   $("form#seguimientoSaticBForm #rolGenericoSaticB").val() == JEFE_OF_CORR_Y_DIC || 
			   $("form#seguimientoSaticBForm #rolGenericoSaticB").val() == JEFE_DEP_AUD_PAT){
		//setTabHabilitado("cancelacionGenericoTab_saticb");
		//setTabHabilitado("autAviDictamenGenericoTab_saticb");
		//setTabHabilitado("derivarSubdelegacionGenericoTab_saticb");
	}else{
		//setTabDesHabilitado("cancelacionGenericoTab_saticb");
		//setTabDesHabilitado("autAviDictamenGenericoTab_saticb");
		//setTabDesHabilitado("derivarSubdelegacionGenericoTab_saticb");
	}
	
	
	$( "form#seguimientoSaticbTABForm #cbxEstatusObraSaticB").prop('disabled','disabled');
	$( "form#seguimientoSaticbTABForm #cbxEstatusObraSaticB").removeClass("red");
	$( "form#seguimientoSaticbTABForm #fechaNotificaSaticb").val("");
	
	ejecutaPasoFechaNotificacion();
	$("#spnFecNotSaticb").hide();
	//chebox
	$("form#seguimientoSaticbTABForm #cbxEstatusObraSaticB").attr('checked', false);
	
}
		 
	
/**
 * Función para ejecutar lo peticion de guardado del tab de seguimiento
 * @author Oscar German Beltrán Ortega
 */
function procesaFormularioSegSaticb(){
		oDgConfirmarSATICBTab = $(idDgConfirmarSATICBTab).dialog({
			autoOpen: false,
			modal:true,
			resizable:false,
			width: 700,
			closeOnEscape: false,
			buttons: {
				   "Si": function() { 
					   if($("form#seguimientoSaticbTABForm #fechaNotificaSaticb").val() != ''){
						   $("#labelFechaNotificaSaticb").html('');
						   //guardarSeguimientoGenerico();
						   procesaFormularioTabSegSaticb("validaDatosSaticbTab()");
						}else{
							$("#labelFechaNotificaSaticb").html('<label class="etiquetaError" >Campo Requerido</label>');
						}
					   habilitaCampo("form#seguimientoSaticbTABForm #cbxEstatusObraSaticB");
					   $('#cbxEstatusObraSaticB').attr('checked', false);
						 
					   $("#spnFecNotSaticb").hide();					   
					   $("#observacionesSegSaticb").prop('disabled','disabled');					   
					   $("#observacionesSegSaticb").removeClass("red");
					   
					   oDgConfirmarSATICBTab.dialog("close");
								
				}, "No": function(){
					$(this).dialog("close"); 
				} 
			}
		});
		
		oDgConfirmarSATICBTab.dialog("open");
		
	}
	
function procesaFormularioTabSegSaticb(funcionValidacion){
	FORMA_ACTUAL="seguimientoSaticbTABForm";
	document.forms["seguimientoSaticbTABForm"].action = jsContextoPromocion + "seguimiento/saticb/actualizaPromSaticb.do";
	
		if(procesaFormulario(funcionValidacion)){			
			alert("Los datos se ingresaron correctamente.");
			completaFlujoSegTab();
			$("#fechaNotificaSaticb").prop('disabled','disabled');
			aplicaReglasSaticB();
		}else{
			//alert("no paso la validacion");
		}
		
	
}

	/**
	 * @author Enrique Duran Jimenez
	 * @since 09/06/2012
	 * Funcion que manda ejecutar el metodo de Guardar seguimiento SATICB
	 */
	function guardarSeguimientoGenerico(){
	
		 var cvePromocion           = $("form#seguimientoSaticbTABForm #cvePromocion").val();
		 var fechaNotificaSaticb    = $("form#seguimientoSaticbTABForm #fechaNotificaSaticb").val();
		 var observacionesSegSaticb = $("form#seguimientoSaticbTABForm #observacionesSegSaticb").val();
		
		 
		 var variable = '{' +
		   '"cvePromocion":"'+cvePromocion+'",'+
		   '"fechaNotificacion":"'+fechaNotificaSaticb+'",'+
		   '"txObservaciones":"'+observacionesSegSaticb+'"}';	
		
		var promocion = jQuery.parseJSON(variable);
		   bloquear();
		   $.postJSON(getAppContextParaJS()+ "/promocion/seguimiento/generico/GuardarSeguimientoSaticB.do", promocion, function(data) {	
			}).error(function(datas){ 
				validarSesionExpirada(datas);
			}).complete(function(){	
				eliminaDomicilioSession();
				desbloquear();
			});	
		   
		   
		   
		
	}
	
	function eliminaDomicilioSession(){
		
		 //elimina el domicilio de session
		$.postJSON(jsContextoPromocion+"seguimiento/generico/removerDomicilioSession.do", null, function(data) {					
		}).error(function(data){ 					
			validarSesionExpirada(data);
		}).complete(function(){
			//Instrucciones para el complete													
		});	
		
	}
	
	
	/**
	 * Función de validacion de datos requeridos para el tab de seguimiento
	 * @author Oscar German Beltrán Ortega
	 */
	function validaDatosSaticbTab(){
		var regresa = false;

		$("form#seguimientoSaticbTABForm #labelFechaNotificaSaticb").html('');
		
		if($("form#seguimientoSaticbTABForm #fechaNotificaSaticb").val() == ''){
			$("form#seguimientoSaticbTABForm #labelFechaNotificaSaticb").html('<label class="etiquetaError">Campo Requerido</label>');
		}else
	
		if($("form#seguimientoSaticbTABForm #fechaNotificaSaticb").val() != '' ){
			regresa = true;
		}
		
		
		return regresa;
		
	}

	/**
	 * Función que ejecuta el comportamiento al seleccionar el chekBox de estatus de obra
	 * del tab de seguimiento
	 * @author Oscar German Beltrán Ortega
	 */
	function seleccionaEstatusObra(){

		var cbxEstatusObra = $("form#seguimientoSaticbTABForm #cbxEstatusObraSaticB").attr('checked');
		if(cbxEstatusObra == 'checked'){
			setTabHabilitado("estatusObraTAB_saticb");	

			setTabDesHabilitado("derivarFiscalizacionTAB_saticb");
			setTabDesHabilitado("derivarSubdelegacionGenericoTab_saticb");
			setTabDesHabilitado("autAviDictamenGenericoTab_saticb");
			setTabDesHabilitado("cancelacionGenericoTab_saticb");
		}else{
			if($("form#seguimientoSaticBForm #RolUsuario").val() == JEFE_OF_CORRECCION ||
					   $("form#seguimientoSaticBForm #rolGenericoSaticB").val() == JEFE_OF_CORR_Y_DIC || 
					   $("form#seguimientoSaticBForm #rolGenericoSaticB").val() == JEFE_DEP_AUD_PAT){
				setTabHabilitado("cancelacionGenericoTab_saticb");
				setTabHabilitado("derivarFiscalizacionTAB_saticb");
				setTabHabilitado("derivarSubdelegacionGenericoTab_saticb");
				setTabHabilitado("autAviDictamenGenericoTab_saticb");
			}else{
				setTabDesHabilitado("cancelacionGenericoTab_saticb");
				setTabDesHabilitado("derivarFiscalizacionTAB_saticb");
				setTabDesHabilitado("derivarSubdelegacionGenericoTab_saticb");
				setTabDesHabilitado("autAviDictamenGenericoTab_saticb");
			}
			
			//estos aplica para ambos
			setTabDesHabilitado("estatusObraTAB_saticb");
			setTabDesHabilitado("regularizarObraGenericoTAB_saticb");
		}
		
	}
	
	/**
	 * Función que invoca al Dialogo generico de invitacion
	 * @author Oscar German Beltrán Ortega
	 */
	function muestraInvitacionSaticb(){
		$("#labelFechaEmisionGuardar").html('');
		var id = $("form#seguimientoSaticbTABForm #cvePromocion").val();
		var registroPatronal = $("form#regularizarObraGenericoTABForm #registroPatronalPagosDt").val();
		//alert("registroPatronal : " + registroPatronal);
		validarRegPatronalSaticB(registroPatronal);//valida y aqui abre el dialogo de Invivtacion
			
		
	
	}
	
	/**
	 * @author Enrique Duran Jimenez
	 * @since 11/07/2012
	 * Funcion que consulta con la cve_promocion las datos de la invitacion y llena la pantalla con los datos adquiridos
	 */
	function jsMuestraInvitacionSaticB(obj){
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
			//console.log("saticb inicial:  " + replaceAll(data.fechaIncial,"/","-"));
			//console.log("saticb final: " +   replaceAll(data.fechaFinal,"/","-"));
			$("form#invitacionAntecedenteForm #fechaIncialinv").val(replaceAll(data.fechaIncial,"/","-"));
			$("form#invitacionAntecedenteForm #fechaFinalInv").val(replaceAll(data.fechaFinal,"/","-"));	
			$("form#invitacionAntecedenteForm #fechaOfInvitacionTxInv").val(data.fechaOfInvitacionTx);
			oDgInvitacionAntecedente.dialog('open');
		}).error(function(data){ 
			validarSesionExpirada(data);
		}).complete(function(){
			desbloquear();
		});			
	}
	
	function completaFlujoSegTab(){
		$( "form#seguimientoSaticbTABForm #fechaNotificaSaticb").datepicker('destroy');
		$( "form#seguimientoSaticbTABForm #fechaNotificaSaticb").removeClass("red");
		//$( "form#seguimientoSaticbTABForm #fechaNotificaSaticb").val("");
		$("#spnFecNotSaticb").hide();
		if ($(jsSaticBCheckEstatusObra).is(":checked")) {
			$(jsSaticBCheckEstatusObra).prop('disabled','disabled').removeClass("red");
		} else {
			habilitaElementosFecNotifica();
		}
	} 
	
	function guardarPeriodosPromocionSeguimientoSaticB(periodoInicial,periodoFinal){
		 var cvePromocion  = $("form#seguimientoSaticbTABForm #cvePromocion").val();
		 var variable = '{' +
		   '"cvePromocion":"'+cvePromocion+'",'+
		   '"fechaIncial":"'+periodoInicial+'",'+
		   '"fechaFinal":"'+periodoFinal+'"}';	
		 var promocion = jQuery.parseJSON(variable);
		   bloquear();
		   $.postJSON(getAppContextParaJS()+ "/promocion/seguimiento/saticb/guardarPeriodosSaticB.do", promocion, function(data) {	
			   
			}).error(function(datas){
				
				validarSesionExpirada(datas);
			}).complete(function(){
				
				desbloquear();
			});	
	}
	
/*
 * Funcion para habilitar los elementos:
 * tab de derivar a fiscalizacion
 * check box estatus obra
 * dependen de que se guarde la fecha de notificacion
 */	
function habilitaElementosFecNotifica() {
	// habilita el tab de derivar a fiscalizacion y dictamen
	if(ROL_JEFE_SATICB){
		//setTabHabilitado("derivarFiscalizacionTAB_saticb");
		//setTabHabilitado("autAviDictamenGenericoTab_saticb");
	} else {
		//setTabDesHabilitado("derivarFiscalizacionTAB_saticb");
		setTabDesHabilitado("autAviDictamenGenericoTab_saticb");
	}	
	// habilita el check de estatus obra
	$(jsSaticBCheckEstatusObra).removeAttr('disabled').addClass("red");	
}