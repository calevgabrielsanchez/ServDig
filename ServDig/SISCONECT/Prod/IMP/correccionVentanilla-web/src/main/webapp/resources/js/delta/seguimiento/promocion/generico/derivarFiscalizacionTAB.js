
// Dialog Confirmar Generico para Seguimiento SATIC B
var idDgConfirmarSATICBFizTab = "#dgConfirmarSaticB";
var oDgConfirmarSATICBFizTab;
var jsFuncionarioRegistraHidDerFisca ="form#derivarFiscalizacionTABForm #funcionarioDerFiscaSaticb";


/**
 * Funcion donde se inicializa todo el comportamiento y/o funcionalidad que se deberia de cargar en el Ready de Jquery , 
 * se separo del Ready principal para facilitar la carga en diferentes momentos y no sea tan p�sada al iniciar la pantalla
 * principale de consulta de promociones. 
 * funcionalidad inicial para la pesta�a de Derivar a fiscalizacion.
 * @author Oscar Beltran
 * @version 1.0.1
 */
function initTabDerivarFiscalizacion(){
	
	$( "form#derivarFiscalizacionTABForm #fecDerivacionGenerica").datepicker( fechaSeguimiento());

	//	/*seccion para definir la fecha maxima del servidor y establecerle un limite maximo a las fechas
	//	 * maximas de los calendarios para las fechas siguientes , con el formato dd-MM-yyyy  */ 
	//	$.postJSON(getAppContextParaJS() + "/promocion/seguimiento/generico/obtenerFechaServidor.do", null,function(data) {
	//		}).error(function(data){
	//			validarSesionExpirada(data);
	//		}).complete(function(data){
	//			$("form#derivarFiscalizacionTABForm #fecDerivacionGenerica").datepicker('option', 'maxDate', data.responseText);
	//	});
	//
	//	/*seccion para definir la fecha minima del servidor y establecerle un limite minima a las fechas
	//	 * maximas de los calendarios para las fechas siguientes , con formato dd-MM-yyyy  */ 
	//	$.postJSON(getAppContextParaJS() + "/promocion/seguimiento/generico/obtenerFechaServidorMinima.do", null,function(data) {
	//	}).error(function(data){
	//		validarSesionExpirada(data);
	//	}).complete(function(data){
	//		//alert(JSON.stringify(data, null, 4));
	//		$("form#derivarFiscalizacionTABForm #fecDerivacionGenerica").datepicker('option', 'minDate', data.responseText);
	//	});
	
	//ocultar boton de hecha
	$('#spnFecDerFisc').hide();
	
	//estilos 
	$("form#derivarFiscalizacionTABForm #fecDerivacionGenerica").addClass("red");
	$("form#derivarFiscalizacionTABForm #referenciaFiscalizacionGen").addClass("red");
	$("form#derivarFiscalizacionTABForm #fecDerivacionGenerica").val("");
	$("form#derivarFiscalizacionTABForm #referenciaFiscalizacionGen").val("");
	
	$("form#derivarFiscalizacionTABForm #fecDerivacionGenerica").removeAttr('disabled');
	$("form#derivarFiscalizacionTABForm #referenciaFiscalizacionGen").removeAttr('disabled');
	$("form#derivarFiscalizacionTABForm #btnConfirmarDerivar").removeAttr('disabled');
	
	
	//alert("val: " + $(jsFuncionarioRegistraHidDerFisca).val());
	$("form#derivarFiscalizacionTABForm #lblFuncionarioDerFiscaSaticb").html($(jsFuncionarioRegistraHidDerFisca).val());
	
}

/**
 * Funci�n que limpia la fecha de derivacion ingresada
 * @author Oscar German Beltr�n Ortega
 */
function limpiaFechaDerivacion(){
	
	$("form#derivarFiscalizacionTABForm #fecDerivacionGenerica").val("");
	$('#spnFecDerFisc').hide();
	
}

/**
 * Funci�n de validacion de datos requeridos y datos correctos para la derivacion de 
 * fiscalizacion
 * @author Oscar German Beltr�n Ortega
 */
function validaCamposDerivacion(){

	var regresa = false;

	$("form#derivarFiscalizacionTABForm #labelreferenciaFiscalizaGen").html('');
	$("form#derivarFiscalizacionTABForm #labelfecDerivacionGenrico").html('');

	if($("form#derivarFiscalizacionTABForm #fecDerivacionGenerica").val() == ''){
		$("form#derivarFiscalizacionTABForm #labelfecDerivacionGenrico").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	if($("form#derivarFiscalizacionTABForm #referenciaFiscalizacionGen").val() == ''){
		$("form#derivarFiscalizacionTABForm #labelreferenciaFiscalizaGen").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	if($("form#derivarFiscalizacionTABForm #fecDerivacionGenerica").val() != '' && $("form#derivarFiscalizacionTABForm #referenciaFiscalizacionGen").val() !=''){
		regresa = true;
	}
	
	return regresa;
}


/**
 * Funcion que valida que la fecha de derivacion no sea menor 
 * que la fecca de notificacion
 * @param fecIni
 * @author Oscar Beltran Ortega
 */
function jsValidaFecDerivacion(fecDeri){
	$("#labelfecDerivacionGenrico").html('');
	if(jsValidaFecha(fecDeri)){
		$("form#derivarFiscalizacionTABForm #fecDerivacionGenerica").val(fecDeri);
		 $("#labelfecDerivacionGenrico").html('');
		 
		 if($("form#saticASeguimientoTabForm #fechaAtnOficioSaticaSeg").val()!="" && $("form#saticASeguimientoTabForm #fechaAtencionConstruccion").val()!="" && $("form#seguimientoConstruccionTABForm #fechaAtencionConstruccion").val()!="" &&
				 $("form#saticASeguimientoTabForm #fechaAtnOficioSaticaSeg").val()!=undefined && $("form#saticASeguimientoTabForm #fechaAtencionConstruccion").val()!=undefined && $("form#seguimientoConstruccionTABForm #fechaAtencionConstruccion").val()!=undefined){
			 var fecha;
			 if($("form#saticASeguimientoTabForm #fechaAtnOficioSaticaSeg").val()!="" && $("form#saticASeguimientoTabForm #fechaAtnOficioSaticaSeg").val()!=undefined){
				 fecha=$("form#saticASeguimientoTabForm #fechaAtnOficioSaticaSeg").val();	 
			 }else if($("form#saticASeguimientoTabForm #fechaAtencionConstruccion").val()!="" && $("form#saticASeguimientoTabForm #fechaAtencionConstruccion").val()!=undefined){
				 fecha=$("form#saticASeguimientoTabForm #fechaAtencionConstruccion").val();
			 }else if($("form#seguimientoConstruccionTABForm #fechaAtencionConstruccion").val()!="" && $("form#seguimientoConstruccionTABForm #fechaAtencionConstruccion").val()!=undefined){
				 fecha=$("form#seguimientoConstruccionTABForm #fechaAtencionConstruccion").val();
			 }
			 
			 
			
			 
			 if(jsValidaVsfecAtencionFisGen(fecDeri,fecha)){
				 $("form#derivarFiscalizacionTABForm #fecDerivacionGenerica").val(fecDeri);
				 $("#labelfecDerivacionGenrico").html('');
				 $('#spnFecDerFisc').show("fast");
			 }else{
				 $("form#derivarFiscalizacionTABForm #fecDerivacionGenerica").val("");
				 $("#labelfecDerivacionGenrico").html('<label class="etiquetaError" >La fecha de la derivaci&oacute;n no puede ser menor a la fecha Atenc&oacute;n del oficio de promoci&oacute;n</label>');
				 return;
			 } 
		 }
		 
		 
		 
		 
		 
		 
		 
		 if(jsValidaVsfecNotificacionFisGen(fecDeri)){
			 $("form#derivarFiscalizacionTABForm #fecDerivacionGenerica").val(fecDeri);
			 $("#labelfecDerivacionGenrico").html('');
			 $('#spnFecDerFisc').show("fast");
		 }else{
			 $("form#derivarFiscalizacionTABForm #fecDerivacionGenerica").val("");
			 $("#labelfecDerivacionGenrico").html('<label class="etiquetaError" >La fecha de la derivaci&oacute;n no puede ser menor a la fecha Notificaci&oacute;n del oficio de promoci&oacute;n</label>');
		 }
	}else{
		$("form#derivarFiscalizacionTABForm #fecDerivacionGenerica").val("");
		 $("#labelfecDerivacionGenrico").html('<label class="etiquetaError" >La fecha de derivaci&oacute;n no puede ser mayor al dia actual</label>');
	}
}



/**
 * Funcion que valida la fecha que recibe como parametro contra la fecha 
 * de notificacion
 * @param fecEvaluar , fecha a Evaluar
 * @returns {Boolean}
 * @author Oscar German Beltran Ortega
 */
function jsValidaVsfecNotificacionFisGen(fecEvaluar){
	var fecNotificacioOficio = $("form#derivarFiscalizacionTABForm #fechaNotificacionOficioFiscalizacion").val();
	var resp = false;
	if(fecEvaluar != '' && fecNotificacioOficio != ''){
		if(jsValidaFechas(fecNotificacioOficio,fecEvaluar)){
			 resp = true;
		 }
	}
	return resp;
}	


function jsValidaVsfecAtencionFisGen(fecEvaluar,fecAtencion){
	
	var resp = false;
	if(fecEvaluar != '' && fecAtencion != ''){
		if(jsValidaFechas(fecAtencion,fecEvaluar)){
			 resp = true;
		 }
	}
	return resp;
}


/**
 * Funci�n que ejecuta la peticion de guardar del tab de derivar a fiscalizacion 
 * @author Oscar German Beltr�n Ortega
 */
function procesaFormularioFiscalizacion(){
	oDgConfirmarSATICBFizTab = $(idDgConfirmarSATICBFizTab).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 700,
		closeOnEscape: false,
		buttons: {
			   "Si": function() {
				   if(validaCamposDerivacion()){
					   guardarSeguimientoFizcalizacion();
					   $('form#derivarFiscalizacionTABForm :input').prop("disabled", "disabled");
					   $('form#derivarFiscalizacionTABForm :input').removeClass("red");
					   try {
							//recupera el nombre de la funcion generica
							eval($("form#derivarFiscalizacionTABForm #functionAuxFiscalizacion").val());	
							oDgConfirmarSATICBFizTab.dialog("close");
						} catch (e) {
				
						}
				   }
				   aplicaReglasSaticB();
				   reglasSaticA();
				   
				   oDgConfirmarSATICBFizTab.dialog("close");
				   $("#spnFecDerFisc").hide();
				   
				   $("#regPatronSatica").prop("disabled", "disabled");
				   $("#btnValidaRegPatronSaticaMain").prop("disabled", "disabled");
				   $('form#segimientoConstruccionForm #regPatronalSegConstruccion').prop('disabled','disabled');
				   $('form#segimientoConstruccionForm #btnValidarRegPatronal').prop('disabled','disabled');
				   $("#spnFechaNotificacionSaticaSeg").hide();
			   }, "No": function(){
				$(this).dialog("close"); 
			} 
		},
		beforeClose :function(event,ui){			
			$("#regPatronSatica").removeAttr('disabled');
			$("#btnValidaRegPatronSaticaMain").removeAttr('disabled');			
		}
	});
	
	oDgConfirmarSATICBFizTab.dialog("open");
}

function jsBorraLabelFiscalizacion(){
	$('form#derivarFiscalizacionTABForm #labelreferenciaFiscalizaGen').html('');
}



/**
 * @author Enrique Duran Jimenez
 * @since 09/06/2012
 * Funcion que manda ejecutar el metodo de Guardar seguimiento SATICB
 */
function guardarSeguimientoFizcalizacion(){
	 
	var oForm = $("#derivarFiscalizacionTABForm").toObject(true);
	oForm.cvePromocion = $("form#derivarFiscalizacionTABForm #cvePromocion").val();
	   bloquear();
	   $.postJSON(jsContextoPromocion + "seguimiento/generico/fiscalizacion.do", oForm, function(data) {	
		}).error(function(datas){ 
			validarSesionExpirada(datas);
		}).complete(function(){				
			desbloquear();
			alert('La derivaci\u00F3n a fiscalizaci\u00F3nn se ha guardado exitosamente');
		});	
}


