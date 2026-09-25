// Variable paa determinar la validacion de los campos del tab
// derivar a otra subdelegacion
var validaSdGen = true;
var jsFuncionarioRegistraHidDSubGT ="form#derivarSubdelegacionGenericoTabForm #hidFuncionarioRegistra";

var jsCveFkPatronFiscalDSubDGT = "form#derivarSubdelegacionGenericoTabForm #cveFkPatronFis";
var jsRegPatronFiscalDSubDGT = "form#derivarSubdelegacionGenericoTabForm #regPatronDerivarSubdelFis";
var jsLabelRegPatronFiscalDSubGT= "form#derivarSubdelegacionGenericoTabForm #labelRegPatronDerivarSubdelFis";

var jsCveFkPatronObraDSubDGT = "form#derivarSubdelegacionGenericoTabForm #cveFkPatronObra";
var jsRegPatronObralDSubDGT = "form#derivarSubdelegacionGenericoTabForm #regPatronDerivarSubdelObra";
var jsLabelRegPatronObraDSubGT = "form#derivarSubdelegacionGenericoTabForm #labelRegPatronDerivarSubdelObra";


/**
 * Funcion donde se inicializa todo el comportamiento y/o funcionalidad que se deberia de cargar en el Ready de Jquery , 
 * se separo del Ready principal para facilitar la carga en diferentes momentos y no sea tan pesada al iniciar la pantalla
 * principal de consulta de promociones. 
 * funcionalidad inicial para el tab de derivar a otra subdelegacion.
 * @author Gerardo Salazar Vega
 * @version 1.0.1
 */
function initTabDerivarSubdelegacionGenerico(){
	limpiarFormulario("#derivarSubdelegacionGenericoTabForm");
	var jsFechaDerivarSubdelGenerico= "form#derivarSubdelegacionGenericoTabForm #fechaDerivarSubdel";	
	
	$(jsFechaDerivarSubdelGenerico).datepicker(fechaSeguimiento());
//	$(jsFechaDerivarSubdelGenerico).datepicker('option', 'maxDate', jsFechaMaxSeguimiento);
//	$(jsFechaDerivarSubdelGenerico).datepicker('option', 'minDate', jsFechaMinSeguimiento);
	limpiarFormulario("#derivarSubdelegacionGenericoTabForm");
	$("form#derivarSubdelegacionGenericoTabForm label").html("");
	$("form#derivarSubdelegacionGenericoTabForm #funcionarioRegistraDerivarSubdel").html($(jsFuncionarioRegistraHidDSubGT).val());
	//ocultar boton de borrar fecha
	$('#spnFechaDerivarSubdel').hide();	
	// Habilita campos de captura 
	$('form#derivarSubdelegacionGenericoTabForm input[type=text]').removeAttr("disabled");
	$('form#derivarSubdelegacionGenericoTabForm input[type=button]').removeAttr("disabled");
	
	//estilos 
	ponerEstiloCapturableSubd(true);
}

function ponerEstiloCapturableSubd(asignar){
	
	if (asignar){		
		$("form#derivarSubdelegacionGenericoTabForm #fechaDerivarSubdel").addClass("red");
		$("form#derivarSubdelegacionGenericoTabForm #regPatronDerivarSubdelFis").addClass("red");
		$("form#derivarSubdelegacionGenericoTabForm #regPatronDerivarSubdelObra").addClass("red");
		//$("form#derivarSubdelegacionGenericoTabForm #descSubdelDestDerivarSubdel").addClass("red");
	} else {
		$("form#derivarSubdelegacionGenericoTabForm #fechaDerivarSubdel").removeClass("red");
		$("form#derivarSubdelegacionGenericoTabForm #regPatronDerivarSubdelFis").removeClass("red");
		$("form#derivarSubdelegacionGenericoTabForm #regPatronDerivarSubdelObra").removeClass("red");
		//$("form#derivarSubdelegacionGenericoTabForm #descSubdelDestDerivarSubdel").removeClass("red");
	}
}

/**
 * Funcion donde se borra la fecha capturada en el campo 
 * fecha de la derivacion a otra subdelegacion.
 *  
 * @author Gerardo Salazar Vega
 * @version 1.0.0
 */
function limpiaFechaDerivacionSubdelGenerico(){	
	$("form#derivarSubdelegacionGenericoTabForm #fechaDerivarSubdel").val("");
	$("form#derivarSubdelegacionGenericoTabForm #labelFechaDerivarSubdel").html("");
	$('#spnFechaDerivarSubdel').hide();	
}

/**
 * Funcion donde se valida los campos requeridos para el tab generico 
 * derivar a otra subdelegacion.
 *  
 * @author Gerardo Salazar Vega
 * @version 1.0.0
 */
function validaCamposDerivacionSub(){
	validaSdGen = true;
	var nForma="derivarSubdelegacionGenericoTabForm";
	var fechaDerivacionSubValidar=$("form#derivarSubdelegacionGenericoTabForm #fechaDerivarSubdel").val();
	var seccionDatosPatObra = $("form#derivarSubdelegacionGenericoTabForm #tieneDatosPatronObraDerSubGT").val();
	var rpValido = false;
	
	// se inicializan los label
	$("form#derivarSubdelegacionGenericoTabForm #labelFechaDerivarSubdel").html('');
	$(jsLabelRegPatronFiscalDSubGT).html('');	
	$("form#derivarSubdelegacionGenericoTabForm #labelRegPatronDerivarSubdelObra").html('');	
	$("form#derivarSubdelegacionGenericoTabForm #labelDescSubdelDestDerivarSubdel").html('');
	$("form#derivarSubdelegacionGenericoTabForm #labelCalleDerivarSubdel").html('');
	$("form#derivarSubdelegacionGenericoTabForm #labelColoniaDerivarSubdel").html('');
	$("form#derivarSubdelegacionGenericoTabForm #labelNumExtDerivarSubdel").html('');
	$("form#derivarSubdelegacionGenericoTabForm #labelCodigoPostalDerivarSubdel").html('');

	validaCampoRequerido(nForma, "regPatronDerivarSubdelFis", "labelRegPatronDerivarSubdelFis");
	if (seccionDatosPatObra=="true") {
		validaCampoRequerido(nForma, "regPatronDerivarSubdelObra", "labelRegPatronDerivarSubdelObra");
	}
	validaCampoRequerido(nForma, "fechaDerivarSubdel", "labelFechaDerivarSubdel");
	//validaCampoRequerido(nForma, "descSubdelDestDerivarSubdel", "labelDescSubdelDestDerivarSubdel");
	validaCampoRequerido(nForma, "calleDerivarSubdel", "labelCalleDerivarSubdel");
	validaCampoRequerido(nForma, "coloniaDerivarSubdel", "labelColoniaDerivarSubdel");
	validaCampoRequerido(nForma, "numExtDerivarSubdel", "labelNumExtDerivarSubdel");
	validaCampoRequerido(nForma, "codigoPostalDerivarSubdel", "labelCodigoPostalDerivarSubdel");
	
	rpValido = registroPatronalValidadoEnSindo (jsCveFkPatronFiscalDSubDGT, jsRegPatronFiscalDSubDGT, jsLabelRegPatronFiscalDSubGT );
	if (!rpValido) { validaSdGen = false; } 
	rpValido = registroPatronalValidadoEnSindo (jsCveFkPatronObraDSubDGT, jsRegPatronObralDSubDGT, jsLabelRegPatronObraDSubGT );	
	if (!rpValido) { validaSdGen = false; }
	
	if (fechaDerivacionSubValidar.length > 0){
		validaFechaEmisionDerSubDGT();
	}
	return validaSdGen;
}

function validaCampoRequerido(forma, campo, etiqueta) {
	var mensaje_requerido = "<label class='etiquetaError'>Campo Requerido</label>";
	var rutaForm = "form#" + forma + " #";
	var v_campo = rutaForm + campo;
	var v_label = rutaForm + etiqueta;

	if ($(v_campo).val() == '') {
		$(v_label).html(mensaje_requerido);
		validaSdGen = false;
	}
}

function validaFechaEmisionDerSubDGT () {
	var mensaje_errorFechaEmision="<label class='etiquetaError'>La fecha de derivaci&oacute;n no puede ser menor a la fecha de oficio de promoci&oacute;n</label>";	
	var fechaEmision=$("form#derivarSubdelegacionGenericoTabForm #fechaEmisionOficioGenerico").val();
	var fechaNotificacionOf=$("form#derivarSubdelegacionGenericoTabForm #fechaNotificacionOficioGenerico").val();
	var fechaDerivacionSub=$("form#derivarSubdelegacionGenericoTabForm #fechaDerivarSubdel").val();	
	
	$("form#derivarSubdelegacionGenericoTabForm #labelFechaDerivarSubdel").html("");
	if (fechaNotificacionOf.length > 0){
		if (validaFechaNotificacionDerSubDGT()) {
			$('#spnFechaDerivarSubdel').show('fast');
		} else {
			$('#spnFechaDerivarSubdel').hide();
		}
	} else if (fechaDerivacionSub!="" && !comparaFechas(fechaEmision, fechaDerivacionSub, '-')){
		$("form#derivarSubdelegacionGenericoTabForm #labelFechaDerivarSubdel").html(mensaje_errorFechaEmision);
		$("form#derivarSubdelegacionGenericoTabForm #fechaDerivarSubdel").val("");
		$('#spnFechaDerivarSubdel').hide();	
		validaSdGen = false;
	} else {
		$('#spnFechaDerivarSubdel').show('fast');	
	}
}

function validaFechaNotificacionDerSubDGT () {
	var mensaje_errorFecha="<label class='etiquetaError'>La fecha de derivaci&oacute;n no puede ser menor a la fecha de notificaci&oacute;n</label>";	
	var fechaNotificacionOf=$("form#derivarSubdelegacionGenericoTabForm #fechaNotificacionOficioGenerico").val();
	var fechaDerivacionSub=$("form#derivarSubdelegacionGenericoTabForm #fechaDerivarSubdel").val();
	var jsBanderaFecValida=true;
	
	if (fechaNotificacionOf=="") {
		jsBanderaFecValida=true;
	} else if (fechaNotificacionOf!="" && fechaDerivacionSub!="" && !comparaFechas(fechaNotificacionOf, fechaDerivacionSub, '-')){
		$("form#derivarSubdelegacionGenericoTabForm #labelFechaDerivarSubdel").html(mensaje_errorFecha);
		$("form#derivarSubdelegacionGenericoTabForm #fechaDerivarSubdel").val("");
		validaSdGen = false;
		jsBanderaFecValida=false;		
	}	
	return jsBanderaFecValida;
}

function validarRegistroPatronalGenerico(paramRegPatron){
	bloquear();
	//var patron = $("form#invitacionFormRegistro #registroPatronal").val();
	$.postJSON(jsContextoPromocion+"seguimiento/generico/validaPatron.do", paramRegPatron, function(data) { 
		if(data == null){
			alert("El registro Patronal es invalido");
			//$("form#invitacionFormRegistro #razonSocial").val('');
		}
		else if(data != null && data.razonSocial != null){
			//alert("El registro Patronal es valido");
			//console.log(JSON.stringify(obj)); falla en IE
			//$("form#invitacionFormRegistro #razonSocial").val(data.razonSocial);							
		}
		desbloquear();
	}).error(function(data){ 
		desbloquear();
		validarSesionExpirada(data);			
	}).complete(function(){						
		desbloquear();												
	});
}

function validarRegistroPatronalFiscal(paramRegPatron){
	if (paramRegPatron==""){
		alert ("Proporcione el registro patronal del domicilio fiscal");
		return;
	}
	bloquear();
	//var patron = $("form#invitacionFormRegistro #registroPatronal").val();
	$.postJSON(jsContextoPromocion+"seguimiento/generico/validaPatron.do", paramRegPatron, function(data) { 
		if(data == null){
			alert("El registro Patronal es invalido");
			$("form#derivarSubdelegacionGenericoTabForm #razonSocialDerivarSubdelFis").html('');
			//$("form#derivarSubdelegacionGenericoTabForm #razonSocialDerivarSubdelFis").focus();
		} else if(data.cveRespuestaWS == JSERROR_WS){
			
			alert("El registro Patronal es invalido");
			$("form#derivarSubdelegacionGenericoTabForm #razonSocialDerivarSubdelFis").html('');
			//$("form#derivarSubdelegacionGenericoTabForm #razonSocialDerivarSubdelFis").focus();
		} else if(data != null && data.razonSocial != null){
			//alert("El registro Patronal es valido");
			//console.log(JSON.stringify(data));
			var subDOrigen=$("form#derivarSubdelegacionGenericoTabForm #subdelegacionFuncionarioReg").val();
			var subDDest=data.ubicacion.municipio.sacSubdelegacion.cvePk;			
			if (subDOrigen==subDDest){
				alert ("No se puede derivar a la misma subdelegaci\u00f3n");
				$("form#derivarSubdelegacionGenericoTabForm #cveSubdelegacionDestino").val("");
				$("form#derivarSubdelegacionGenericoTabForm #descSubdelDestDerivarSubdel").val("");
				$(jsCveFkPatronFiscalDSubDGT).val("");
				
			} else { 
				$(jsCveFkPatronFiscalDSubDGT).val(data.cvePK);
				$("form#derivarSubdelegacionGenericoTabForm #razonSocialDerivarSubdelFis").html(data.razonSocial);	
				$(jsLabelRegPatronFiscalDSubGT).html("");				
				$("form#derivarSubdelegacionGenericoTabForm #cveSubdelegacionDestino").val(data.ubicacion.municipio.sacSubdelegacion.cvePk);
				$("form#derivarSubdelegacionGenericoTabForm #descSubdelDestDerivarSubdel").val(data.ubicacion.municipio.sacSubdelegacion.nomNombre);
			}
		}
		desbloquear();
	}).error(function(data){ 
		desbloquear();
		validarSesionExpirada(data);			
	}).complete(function(){						
		desbloquear();												
	});
}

function registroPatronalValidadoEnSindo (pCveFkPatron, pRegPatron, pLabel) {
	var mensaje_validar_reg_patron = "<label class='etiquetaError'>Se requiere validar el registro patronal</label>";
	var resultValidar = true;
	if ($(pRegPatron).val().length > 0 && $(pCveFkPatron).val() == "") {
		$(pLabel).html(mensaje_validar_reg_patron);
		resultValidar = false;
	}	
	return resultValidar;
}


function validarRegistroPatronalObra(paramRegPatron){
	$("form#derivarSubdelegacionGenericoTabForm #labelRegPatronDerivarSubdelObra").html('');
	$("form#derivarSubdelegacionGenericoTabForm #labelDescSubdelDestDerivarSubdel").html('');
	
	if (paramRegPatron==""){
		alert ("Proporcione el registro patronal del responsable de la obra");
		return;
	}
	
	bloquear();
	$.postJSON(jsContextoPromocion+"seguimiento/generico/validaPatron.do", paramRegPatron, function(data) { 
		if(data == null){
			alert("El registro Patronal es invalido");
			$("form#derivarSubdelegacionGenericoTabForm #regPatronDerivarSubdelObra").val('');
		} else if(data.cveRespuestaWS == -1){
			alert("El registro Patronal es invalido");
			$("form#derivarSubdelegacionGenericoTabForm #razonSocialDerivarSubdelObra").html('');
			//$("form#derivarSubdelegacionGenericoTabForm #razonSocialDerivarSubdelFis").focus();
		} else if(data != null && data.razonSocial != null){
			//alert("El registro Patronal es valido");
			//console.log(JSON.stringify(data));
		
			$("form#derivarSubdelegacionGenericoTabForm #cveFkPatronObra").val(data.cvePK);
			$("form#derivarSubdelegacionGenericoTabForm #razonSocialDerivarSubdelObra").html(data.razonSocial);			
			}
		desbloquear();
	}).error(function(data){ 
		desbloquear();
		validarSesionExpirada(data);			
	}).complete(function(){						
		desbloquear();												
	});
}

function actaualizaDomDerSubDgt(){
	var sPatron = '{"patron":'+'"DOM_DERIVAR_SUBD"}';
	var crcPatron = jQuery.parseJSON(sPatron);
	bloquear();
	$.postJSON(jsContextoPromocion+"seguimiento/generico/actualizaDomObra.do", crcPatron, function(data) {
		if (data != null) {
			$('form#derivarSubdelegacionGenericoTabForm,#calleDerivarSubdel').val(data.dgVialidadByCveViaPrin.nomVia);
			$('form#derivarSubdelegacionGenericoTabForm #labelCalleDerivarSubdel').html('');
			$('form#derivarSubdelegacionGenericoTabForm,#numExtDerivarSubdel').val(data.numextnum);
			$('form#derivarSubdelegacionGenericoTabForm #labelNumExtDerivarSubdel').html('');
			$('form#derivarSubdelegacionGenericoTabForm,#numIntDerivarSubdel').val(data.numintnum);
			
			$('form#derivarSubdelegacionGenericoTabForm,#coloniaDerivarSubdel').val(data.dgAsentamiento.nomAsen);	
			$('form#derivarSubdelegacionGenericoTabForm #labelColoniaDerivarSubdel').html('');
			$('form#derivarSubdelegacionGenericoTabForm,#codigoPostalDerivarSubdel').val(data.dgCodigosPostales.id.codigo);
			$('form#derivarSubdelegacionGenericoTabForm #labelCodigoPostalDerivarSubdel').html('');
		}
		desbloquear();
		
	}).error(function(data){ 
		desbloquear();
		alert("Error: Conexi�n no disponible, intente de nuevo");
	}).complete(function(){
		
	});	
}
function cargaDomicilioDerSubDGT(){
	var resultado = openWindowregistraDomicilioInegi(getAppContextParaJS(),'/promocion/seguimiento/generico/solicitudDomGeograficoObra.do',"DOM_DERIVAR_SUBD","actaualizaDomDerSubDgt()");
	var sPatron = '{"patron":'+'"DOM_DERIVAR_SUBD"}';
	var crcPatron = jQuery.parseJSON(sPatron);
	bloquear();
	$.postJSON(jsContextoPromocion+"seguimiento/generico/actualizaDomObra.do", crcPatron, function(data) {
		if (data != null) {
			$('form#derivarSubdelegacionGenericoTabForm,#calleDerivarSubdel').val(data.dgVialidadByCveViaPrin.nomVia);
			$('form#derivarSubdelegacionGenericoTabForm #labelCalleDerivarSubdel').html('');
			$('form#derivarSubdelegacionGenericoTabForm,#numExtDerivarSubdel').val(data.numextnum);
			$('form#derivarSubdelegacionGenericoTabForm #labelNumExtDerivarSubdel').html('');
			$('form#derivarSubdelegacionGenericoTabForm,#numIntDerivarSubdel').val(data.numintnum);
			
			$('form#derivarSubdelegacionGenericoTabForm,#coloniaDerivarSubdel').val(data.dgAsentamiento.nomAsen);	
			$('form#derivarSubdelegacionGenericoTabForm #labelColoniaDerivarSubdel').html('');
			$('form#derivarSubdelegacionGenericoTabForm,#codigoPostalDerivarSubdel').val(data.dgCodigosPostales.id.codigo);
			$('form#derivarSubdelegacionGenericoTabForm #labelCodigoPostalDerivarSubdel').html('');
		}
		desbloquear();
		
	}).error(function(data){ 
		desbloquear();
		alert("Error: Conexi�n no disponible, intente de nuevo");
	}).complete(function(){
		
	});	
}

function borraDatosRPFiscalSGT(){
	$("form#derivarSubdelegacionGenericoTabForm #labelRegPatronDerivarSubdelFis").html('');
	$(jsCveFkPatronFiscalDSubDGT).val("");
	$(jsLabelRegPatronFiscalDSubGT).html("");
}

function procesaFormularioDerivacionSub(funcionValidacion) {
	FORMA_ACTUAL = "derivarSubdelegacionGenericoTabForm" ;
	
	var cve_promocion = $("form#derivarSubdelegacionGenericoTabForm #cvePromocion").val();
	if (cve_promocion.length == 0){
		alert ("No se se encontro la clave de promocion");
		return false;
	}
	document.forms["derivarSubdelegacionGenericoTabForm"].action = jsContextoPromocion+"seguimiento/generico/derivarSubdelegacion.do";
	
		if (procesaFormulario(funcionValidacion)) {
			$('form#derivarSubdelegacionGenericoTabForm input[type=text]').prop("disabled", "disabled");
			$('form#derivarSubdelegacionGenericoTabForm input[type=button]').prop("disabled", "disabled");			
			ponerEstiloCapturableSubd(false);
			$('#spnFechaDerivarSubdel').hide();
			alert("La informaci\u00f3n ha sido guardada");
			$('#regPatronSatica').prop("disabled", "disabled");
			$('#regPatronSatica').removeClass("red");
			$('#btnValidaRegPatronSaticaMain').prop("disabled", "disabled");
			try {
				//recupera el nombre de la funcion generica
				eval($("form#derivarSubdelegacionGenericoTabForm #functionAuxDerSubdelegacion").val());	
			} catch (e) {

			}
			aplicaReglasSaticB();
			reglasSaticA();
			
			//Caso Folio Construccion
			setTabDesHabilitado("autAviDictamenGenericoTab_Construccion");
		}
	
}

