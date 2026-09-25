

var oDgConfirmaCCRG;
var idConfirmaCCRG   = "#dgConfirmarCCRG";

/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion que inicializa el dialogo confirmar cierre por cotizar
 */
function inicializaDialogConfirmarCCRG(){
	oDgConfirmaCCRG = $(idConfirmaCCRG).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 700,
		closeOnEscape: false,
		buttons: {
			   "Si": function() { 
				   bloquear();
				   var promocion = $("#cierrePorCotizarRGenericoTabForm").serializeObject(true);
				   $.postJSON(getAppContextParaJS() + "/promocion/seguimiento/generico/GuardarCCRG.do", promocion, function(data) {
						alert('Promoci\u00f3n Concluida, Cotiz\u00f3 Razonable');
						eval($("form#cierrePorCotizarRGenericoTabForm #functionAuxCCRG").val());
						oDgConfirmaCCRG.dialog('close');
					}).error(function(datas){ 
						validarSesionExpirada(datas);
					}).complete(function(){
						desbloquear();
					});
							
			}, "No": function(){
				$(this).dialog("close"); 
			} 
		}
	});
	
}

/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion inicializa el datepicker de la pantalla cierre por cotizar razonablemente
 */
function inicializaFormaCierrePorCotR(){
	
	$("form#cierrePorCotizarRGenericoTabForm #fecCotizRazCCRG").datepicker( { 
		dateFormat: 'dd-mm-yy',
		onSelect: function(dateText, inst) { 
			jsValidaFechaCierreCotizarR();
	    }
	});
	
		$( "form#cierrePorCotizarRGenericoTabForm #fecCotizRazCCRG").datepicker('option', 'maxDate', jsFechaMaxSeguimiento);
		$( "form#cierrePorCotizarRGenericoTabForm #fecCotizRazCCRG").datepicker('option', 'minDate', jsFechaMinSeguimiento);
		$("form#cierrePorCotizarRGenericoTabForm #fecCotizRazCCRG").addClass("red");
	
}

/**
 * @author Enrique Duran Jimenez
 * @since 26/06/2012
 * Funcion js que manda llamar el metodo guardar generico para el modulo Cierre por Cotizar Razonablemente
 */
function jsGuardarCCRG(){
	tipoSeguimiento = $("form#cierrePorCotizarRGenericoTabForm #functionAuxCCRG").val();
	$('form#cierrePorCotizarRGenericoTabForm #labelFecCotizarRaz').html('');
	if($("form#cierrePorCotizarRGenericoTabForm #fecCotizRazCCRG").val() != '' && $("form#cierrePorCotizarRGenericoTabForm #fecCotizRazCCRG").val() != undefined){
		inicializaDialogConfirmarCCRG();
		oDgConfirmaCCRG.dialog('open');
	}else{		
		$("form#cierrePorCotizarRGenericoTabForm #labelFecCotizarRaz").html('<label class="etiquetaError">Campo Requerido</label>');
	}	
	
}

/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion valida la fecha cierrre por cotizar vs la fecha de atencion del seguimiento
 */
function jsValidaFechaCierreCotizarR(){
	$("form#cierrePorCotizarRGenericoTabForm #labelFecCotizarRaz").html('');
	
	 var fecIni =   $("form#cierrePorCotizarRGenericoTabForm #fecCotizRazCCRG").val();
	 var fecFinal = $("form#cierrePorCotizarRGenericoTabForm #fechaAtencionGenericaCCRG").val();
	 if(fecIni != '' && fecFinal != ''){
		 if(jsValidaFechas(fecFinal,fecIni)){
			 $("form#cierrePorCotizarRGenericoTabForm #fecCotizRazCCRG").val(fecIni);
			 $("form#cierrePorCotizarRGenericoTabForm #labelFecCotizarRaz").html('');
		 }else{
			 $("form#cierrePorCotizarRGenericoTabForm #fecCotizRazCCRG").val('');
			 $("form#cierrePorCotizarRGenericoTabForm #labelFecCotizarRaz").html('<label class="etiquetaError">La fecha Cierre por Cot. Raz. no puede ser menor a la fecha de atenci&oacute;n</label>');
		 }
	 }	
}

/**
 * @author Enrique Duran Jimenez
 * @since 05/06/2012
 * Funcion limpia la fecha capturada en pantalla
 */
function jsLimpiarCCRG(){
	$("form#cierrePorCotizarRGenericoTabForm #fecCotizRazCCRG").val("");
	$("form#cierrePorCotizarRGenericoTabForm #labelFecCotizarRaz").html("");
	
}