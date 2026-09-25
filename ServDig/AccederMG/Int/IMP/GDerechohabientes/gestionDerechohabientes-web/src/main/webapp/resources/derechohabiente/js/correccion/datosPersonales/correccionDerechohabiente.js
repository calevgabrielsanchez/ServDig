/**
 * Mario Teran Blanco 

 * IMSS (Instituto Mexicano del Seguro Social) 
 * 20/04/2012
 * 
 * 
 */

var asentamientosUbicados;
var validacion;
var listaVialidades;
var guardarValidacion=false;
var existeA = false;
var calificadoRenapo = false;
var calificadoImss = false;
var permiteCambioDomicilio = true;
$(document).ready(function() {
	
	

	
	$.ajaxSetup({ cache: false }); 
	setValidadorFormulario();

	existeAsegurado($("#asegurado").val(),$("#idParentescoActual").val());
	setValidacion($("#validacion").val());
	//$('#fechaNacimiento').mask("99/99/9999");
	
	var idParentescoActual = parseInt($("#idParentescoActual").val());
	var umfs = {
			'umfUsuario' : $("#idUmfDom").val(),
			'umfPersona' : $("#idUmfDom").val()
	};
	
	 
	if( idParentescoActual != ASEGURADO && idParentescoActual != PENSIONADO ){
		preSeleccionarOpciones();
	}
	
	
	$("#llamarIca").click(function() {
		
		var $curpCap = $("#curpCap");
		
		if( $.validator.methods["curp"].call($.validator.prototype,$curpCap.val(),$curpCap[0]) ){
			llamarICA();
		}
	});
	
	
	if((idParentescoActual==CONCUBINARIO) || ((idParentescoActual==PADRES || idParentescoActual==MADRES) && ($("#patronImss").val() == 0 && $("#tieneAcuerdo").val() == 0
			&& $("#aseguradoFallecido").val()!=1))) {
		permiteCambioDomicilio  = false;
	}
	
	//leasignamos la accion de guardar al presionar el boton aceptar
	initDomicilios($("#validacion").val()==1 || !permiteCambioDomicilio);
	
	$("#aceptar").click(eventoGuardarCorreccion);
	
	
	$("#regresar").click(function() {
		botonRegresar();
	});
	
	
	$("#regresarGrupoFamiliar").click(function() {
		salirCorreccion();
	});
	
	$("#cancelar").hide();
	$("#regresar").hide();

	
	// ------------------------------------------------
	// Limita el n�mero de caracteres en la text area
	// ------------------------------------------------
	if( $("#observaciones").length > 0 ){
		var offsetLeft = $("#observaciones").position().left - parseInt($("#observaciones").css("padding-left"));
		asignartextAreaLimites("observaciones",{styles: {marginLeft:offsetLeft}});
	}
});

function setearDescripciones() {

	if($("#parentesco\\.idParentesco").val() != 5 && $("#parentesco\\.idParentesco").val() != 6) {
		setDescripcionCombo("parentesco\\.idParentesco","parentesco\\.descripcion");
	}
	setDescripcionCombo("lugarNacimiento\\.clave","lugarNacimiento\\.nombre");
	setDescripcionCombo("estadoCivil\\.idEstadoCivil","estadoCivil\\.descripcion");
	setDescripcionCombo("sexo\\.idSexo","sexo\\.descripcion");
}

/**
 * Metodo que setea el texto seleccionado de un combo a un campo
 * @param idCombo - El id del select
 * @param idDescripcion -  el id del elemento que contendra la descripcion
 */
function setDescripcionCombo(idCombo,idDescripcion) {
	var texto = $("select#"+idCombo+" option:selected").html();
	$("#"+idDescripcion).val(texto);
}

/**
 * Funcion para validar si es posible o no guardar la solicitud de correccion
 */
function eventoGuardarCorreccion() {

	var verificarRequisitosMinimos = false;
	//Si hubo cambio de parentesco, no puede ser de un recien nacido
	if(verificarCambioParentesco() && ($("form#registro input#indicadorRN").val() == '1') ) {
		//mostramos error
		errorCambioParentescoRecienNacido();
	} else {
		//Verificamos si hubo algun cambio en la informacion del asegurado
		//llamamos al seteo del domicilio
		validarDomicilioCapturadoComponente();
		if(verificarCambioDatos()) {
			var existeCambiosDatosPersonales = verificarCambioDatosPersonales();
			//Si hubo cambio de datos verificamos si fue de datos personales o parentesco, solo en esos casos se validan los requisitos minimos
			if( existeCambiosDatosPersonales || verificarCambioParentesco()) {
				//Verificamos si hay inconsistencia con la curp con la que se hizo el ICA
				if( errorCambioCurp() ){
					//si hay error mandamos error
					errorGenerico("La curp capturada no corresponde a la curp con la que se identificaron los cambios");
					return;
				}else {
					//verificarRequisitosMinimos = true;
				}
			}
			
			if(validarDomicilioCapturado()) {
				//validamos que el formulario este completo y que los campos tengan lo necesario
				if( $('form#registro').valid() && validarCombosRegistro()){
					
					//Verificamos si existen cambio en los datos de la persona
					if(existeCambiosDatosPersonales && !calificadoRenapo) {
						$("#calificacion\\.idCalificacion").val(3);
					}
					
					// ---------------------------------------------------------------------------
					// La fecha de nacimiento capturada cuando se registro el recien nacido
					// de coincidir con la obtenida por ICA
					// ---------------------------------------------------------------------------
					if( esCorrectaFechaNacimientoRecienNacido() ){
						if(verificarRequisitosMinimos) {
							verificarRequisitos();
						} else {
							salvarCorreccion();
						}
					} else {
						return;
					}
						
				}
			}
		} else {
			// Si no ha modificado ningun dato dentro del formulario mandamos un error a pantalla
			errorNoCambioDatos();
			return;
		}
	}
}

function validarDomicilioCapturado() {
	
	if($.trim($("#domicilio\\.clave").val()).length == 0 &&
			$.trim($("#domicilio\\.codigoPostal\\.codigoPostal").val()).length == 0	) {
		mensajeError("Por favor capture su domicilio");
		return false;
	}
	return true;
}

/**
 * Metodo para verificar que los requisitos minimos se cumplan
 */
function verificarRequisitos() {
	
	$.blockUI();
	
	var url = context_path + "/derechohabiente/correccion/datosPersonales/requisitos";
	var fechan = null;
	var curp = null;
	var indRecienNacido = null;
	
	
	try{
		if( $.trim($("#fechaNacimiento").val()).length > 0 ){
			fechan = $("#fechaNacimiento").val();
		}
		
		if( $("form#registro input#indicadorRN") )
			indRecienNacido = $("form#registro input#indicadorRN").val();
		
	}catch(e){
		
	}
		
	
	// ----------------------------------------------
	// Cambi� la CURP
	// ----------------------------------------------
	if($.trim($("#curpActual").val()) != $.trim($("#curpCap").val()))
		curp = $.trim($("#curpCap").val());
	
	var parametros = {
		"derechohabiente": {
			"idPersona":$("#idPersona").val(),
			"sexo": {
				"idSexo": $('#sexo\\.idSexo').val()
			},
			"fechaNacimiento": fechan,
			"curp" : curp
		},
		"parentesco": {
			"idParentesco": $('#parentesco\\.idParentesco').val()
		},
		"domicilio" : {
			"clave" : $("#domicilio\\.clave").val(),
			"codigoPostal" : {
				"codigoPostal" : $("#domicilio\\.codigoPostal\\.codigoPostal").val()
			}
		},
		"indRecienNacido":indRecienNacido
	};
	
	$.postJSON(url, parametros, function(result) {
		
		if(result.modelo.aprobado == 1){
			$("#regresar").show();
			$('#ubicar').hide();
			$('#aceptar').hide();
			$("#regresarGrupoFamiliar").hide();
			salvarCorreccion();
		}
		else{
			$.unblockUI();
			mensajeError(result.modelo.motivo);
		}
			
	});
}


/**
 * Metodo para enviar los datos del formulario
 */
function salvarCorreccion() {
	
	try{
		$("#registro input:text").each(
			function(index) {
				$(this).removeAttr("disabled");
				$(this).removeClass("disabled");
				$(this).attr("readonly","readonly");
			}
		);
		
		$("#registro select").each(
			function(index) {
				$(this).removeAttr("disabled");
				$(this).removeClass("disabled");
				$(this).attr("readonly","readonly");
			}
		);
		
		$("#fechaNacimiento").removeAttr("disabled");
		$("#fechaNacimiento").attr("readonly", "readonly");
		
		if( $('form#registro').valid() ) {
			setearDescripciones()
			$("#registro").on("submit",function(){$.blockUI();});
			$('#registro').submit();
		} else
			$.unblockUI();
	
	}catch(e){
		//console.log(e);
	}
}


/**
 * Asigna los valores para estado civil en base al parentesco
 * 
 * Deshabilita el campo de sexo
 * Oculta el bot�n para capturar el domicilio en base al patron
 * 
 * @param validarDomicilioParentesco Si se valida el domicilio para el parentesco 
 *        asignado en la vista
 */
function comboSexo(validarDomicilioParentesco){
	
	validarDomicilioParentesco = ( typeof(validarDomicilioParentesco) === 'undefined' )? false: validarDomicilioParentesco;
	
	var parentesco = $('#parentesco\\.idParentesco').val();
	var $estadoCivil = $('#estadoCivil\\.idEstadoCivil').attr("disabled","disabled");
	var idPersonaAfectada = $("#idPersona").val();
	
	$('#sexo\\.idSexo').attr("disabled","disabled");
	
	if(parentesco == CONYUGE){		
		
		// -------------------------------------------------
		// Puede tener cualquier sexo
		// -------------------------------------------------
		$estadoCivil.val(CASADO);
	}
	
	if(parentesco == CONCUBINARIO){			
		$estadoCivil.val(CONCUBINATO);
	}
	
	if(parentesco == HIJOS){	
		$estadoCivil.val(SOLTERO);
	}
	
	
	if($("#validacion").val()=="1") {
		
		// --------------------------------------------------------
		// Si es la pantalla de validaci�n de datos no puede 
		// cambiar el domicilio
		// --------------------------------------------------------
		$('#ubicar').hide();
		
	}else{
		
		if( (parentesco  == PADRES) || (parentesco == MADRES) ){
			$estadoCivil.removeAttr("disabled");
		}
		
		if($.trim(parentesco).length > 0) {
			if((parentesco==CONCUBINARIO) || ((parentesco==PADRES || parentesco==MADRES) && ($("#patronImss").val() == 0 && $("#tieneAcuerdo").val() == 0
					&& $("#aseguradoFallecido").val()!=1))) {
				// ---------------------------------------------------------------------------------
				// Si el patron no es el IMSS, deben tener el mismo domicilio que le asegurado
				// ---------------------------------------------------------------------------------
				$('#ubicar').hide();
			} else {
				$('#ubicar').show();
			}
		}
	}
	
	
	// --------------------------------------------------------------------------
	// Se valida si el domicilio que se asigna es el del asegurado o el
	// de la persona
	// --------------------------------------------------------------------------
	if( validarDomicilioParentesco ){
		
		$.postJSON(context_path + "/derechohabiente/correccion/validarDomicilioParentesco",{
			"derechohabiente": {"persona": {"idPersona":idPersonaAfectada}},
			"parentesco": {"idParentesco": parentesco}
		},function(result) {
			try{
				
				if(!result.modelo.error){
				
					var domicilio = result.modelo.domicilioAsegurado;
					
					if( !result.modelo.setDomicilioAsegurado ){
						if( result.modelo.personaDomicilio ){
							domicilio = result.modelo.personaDomicilio.domicilio;
						}
					}
					
					// ----------------------------------------------------------
					// Asignamos nuevo domicilio para la persona afectada
					// ----------------------------------------------------------
					cambiarDomicilio.call(domicilio);
					
				}else{
					mensajeError(result.modelo.mensajeError);
				}
			
			}catch(e){
				
			}
				
		});
		
		
		
	}
	
}




function confirmacion(){
	var mensaje= '<div class="ui-widget">' +
	'<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> ' +
	'<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>' +
	'Por favor verifique la informaci\u00F3n proporcionada y de clic en Aceptar para continuar con el registro. '+
	'Si requiere corregir datos de clic en Regresar.</p></div></div>';
	mensajeConfirmacion(mensaje);
	
	habilitarCampos(false);
	$("#mensajeConfirmacion").html(mensaje);
	$("#regresarGrupoFamiliar").hide();
	$("#regresar").show();
	
}



function botonRegresar(){
	$("#mensajeConfirmacion").text('');
	habilitarCampos(true);
	$('#ubicar').show();
	$("#regresar").hide();
	$("#cancelar").hide();
	$("#regresarGrupoFamiliar").show();
	$("#aceptar").show();
	$("#domicilio\\.codigoPostal\\.codigoPostal").attr("disabled","disabled");
	guardarValidacion=false;
}



/**
 * Metodo para editar o deshabilitar campos
 * 
 */
function habilitarCampos(estado){
	
	$("#fechaNacimiento").attr("disabled", "disabled");
		
	$("#registro select").each(function(index) {
		$(this).removeAttr("disabled");
		if(estado==false)
			$(this).attr("disabled","disabled");
	});
	
	$("#mediosActuales input:text").each(function(index) {
		$(this).removeAttr("disabled");
		if(estado==false)
			$(this).attr("disabled","disabled");
	});
	
	
	// ------------------------------------------------------
	// No es ASEGURADO ni PENSIONADO
	// ------------------------------------------------------
	if($("#parentesco\\.idParentesco").val()!=5 && $("#parentesco\\.idParentesco").val()!=6){
		
		$("#registro input:text").each(function(index) {
			$(this).removeAttr("disabled");
			if(estado==false)
				$(this).attr("disabled","disabled");
		});
		
	} else {
		
		// -----------------------------------
		// Es ASEGURADO o PENSIONADO
		// -----------------------------------
		deshabilitarCampos($("#parentesco\\.idParentesco").val());
	}
		

	
}

/**
 * MEtodo para establecer la accion dependiendo si es la validacion o no
 * @param validacion
 */
function setValidacion(validacion) {
	
	if(validacion != 1) {
		
		// ------------------------------------------------
		// Correcci�n de datos
		// ------------------------------------------------
		$("#registro").attr("action",""+context_path+"/derechohabiente/correccion/datosPersonales/guardar");
	} else {
		
		// -------------------------------------------------------------
		// Se validaron los datos. 
		// Se valido que el curp no este reptido en el grupo familiar
		// Se piden los documentos probatorios y observaciones
		// -------------------------------------------------------------
		$("#registro").attr("action",""+context_path+"/derechohabiente/correccion/datosPersonales/validacion/guardar");
		
		$('#rechazar').click(function() {
			cargarRazonRechazo();
		});
		
		$('#regresar').click(function(){
			cancelarValidacion();
		});
		
		$('#ubicar').hide();
		
		habilitarCampos(false);
	}
	
	$("#domicilioNuevo input:text").each(function(index) {
		$(this).attr("disabled","disabled");
	});	
	
}


function salirCorreccion() {

	$decision = $('<div></div');

	$decision.dialog({
		autoOpen : false,
		resizable : false,
		width : 500,
		title : 'Seleccione una opci\u00F3n',
		modal : true,
		buttons : {
			"Si" : function() {
				location.href = "" + context_path + "/inicio/grupoFamiliar";
			},
			"No" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text('\u00BF Est\u00E1 seguro que desea salir del tr\u00E1mite de Correcci\u00F3n? Se perder\u00E1n todos los datos no guardados');
	$decision.dialog('open');
}

function cancelarValidacion() {
	salirCorreccion();
}



// -------------------------------------------
// Rechazar solicitud
//-------------------------------------------

function parametrosRespuestaRechazo($dialogo,solicitud) {
	
	opciones = {
		autoOpen : false,
		resizable: false,
		width: 480,
		title: 'Resultado', 
		modal: true,
		buttons: {
			"Aceptar" : function() {
				cierraDialogo($dialogo);
				if(solicitud != null) {
					showComprobanteRechazo();
				}
				location.href= context_path + "/inicio/grupoFamiliar";
			}
		}
	};

	return opciones;
}


function cargarRazonRechazo() {
	
	$razonRechazo = $('<div></div');
	$razonRechazo.html('Cargando Razones de rechazo...');
	$razonRechazo.dialog({
		autoOpen : false,
		title: 'Raz\u00F3n rechazo',
		show: "blind",
		resizable: false,
		modal: true,
		width: 480,
		buttons: {
			"Si": function() {
				var razon = $('#idRazonRechazo').val();
				rechazarSolicitud(razon,$(this));
			},
			"No": function() {
				cierraDialogo($(this));
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	var ajaxResource = context_path + '/solicitud/cargarRazonRechazo';
	$razonRechazo.load(ajaxResource);
	$razonRechazo.dialog('open');
}

function rechazarSolicitud(razonRechazo, $dialogo) {
	
	var solicitud = {
		'idSolicitud': $('#solicitud\\.idSolicitud').val(),
		'idPersona': $('#idPersona').val(),
		'idTipoTramite': $('#tipoTramite\\.idTipoTramite').val(),
		'idRazonRechazo': razonRechazo,
		'idTramite' : $('#idTramite').val()
	};
	
	var ajax_source = context_path + "/derechohabiente/baja/rechazar";
	
	$.postJSON(ajax_source, solicitud, function(result) {
		
		if(result.errores == null) {
			$dialogo.html("<center>La solicitud a sido rechazada satisfactoriamente por la siguiente raz\u00F3n: <br>" + result.modelo.razonResultado.descripcion + "</center>" );
			$dialogo.dialog(parametrosRespuestaRechazo($dialogo,solicitud));
		} else {
			
		}
	});
}

function showComprobanteRechazo(){
	var direccion=contextPath + "/documentos/rechazoSolicitud?titulo=Correccion Derechohabiente";
	var page=contextPath + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");	
}




function cancelarCorreccion() {

	$decision = $('<div></div');

	$decision.dialog({
		autoOpen : false,
		resizable : false,
		width : 140,
		title : 'Selecciona una opci\u00F3n',
		modal : true,
		buttons : {
			"Si" : function() {
				location.href = "" + context_path + "/inicio/grupoFamiliar";
			},
			"No" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text('\u00BF Est\u00E1 seguro que desea cancelar el tr\u00E1mite de correcci\u00F3n de datos?');
	$decision.dialog('open');
}


// -------------------------------------------------
// Deshabilitar campos en base al parentesco
//-------------------------------------------------

function existeAsegurado(existe,idParentesco){
	existeA = existe == 'true';
	if(existeA){
		camposAsegurado();
	} else {
		camposOtros(idParentesco);
	}
}


function deshabilitarCampos(idParentesco){
	
	if(idParentesco==5 || idParentesco==6){
		camposAsegurado();
	}	else {
		camposOtros(idParentesco);
	}
}

function camposAsegurado(){
	
	$("#nombre").attr("disabled","disabled");
	$("#primerApellido").attr("disabled","disabled");
	$("#segundoApellido").attr("disabled","disabled");
	$("#sexo\\.idSexo").attr("disabled","disabled");
	$("#curpCap").attr("disabled","disabled");
	$("#lugarNacimiento\\.clave").attr("disabled","disabled");
	$("#parentesco\\.descripcion").attr("disabled","disabled");
	$("#curpCap").attr("disabled","disabled");
	
	$("#fechaNacimiento").attr("disabled","disabled");
	$("#fechaNacimiento").attr("readonly","readonly");
	
	
	// -------------------------------------------------------------------------------------
	// Si el asegurado no tiene fecha de nacimiento, podr� capturar el d�a ya que el
	// mes y a�o se obtienen de la base
	// -------------------------------------------------------------------------------------
	if($.trim($('#fechaNacimiento').val()) == ""){
		activarFechaNacAsegurado();
	}
	
	icaController.ocultar();
	
}


function camposOtros(idParentesco){

	$("#nombre").attr("disabled","disabled");
	$("#primerApellido").attr("disabled","disabled");
	$("#segundoApellido").attr("disabled","disabled");
	$("#sexo\\.idSexo").attr("disabled","disabled");
	$("#lugarNacimiento\\.clave").attr("disabled","disabled");
	$("#fechaNacimiento").attr("disabled","disabled");
	$("#parentesco\\.descripcion").attr("disabled","disabled");
	$('#estadoCivil\\.idEstadoCivil').attr("disabled","disabled");
	
	// --------------------------------------------------
	// Deben tener el mismo domicilio que el asegurado
	// --------------------------------------------------
	if((idParentesco==CONCUBINARIO) || ((idParentesco==PADRES || idParentesco==MADRES) && ($("#patronImss").val() == 0 && $("#tieneAcuerdo").val() == 0
			&& $("#aseguradoFallecido").val()!=1))) {
		$('#ubicar').hide();
	}
	
	if( idParentesco == PADRES ){
		$('#estadoCivil\\.idEstadoCivil').removeAttr("disabled");
	}
	
	if($("#validacion").val()=="1") {
		icaController.ocultar();
		$("#curpCap").attr("disabled","disabled");
	} else {
		
		var mensajeOtros = "Para actualizar su informaci&oacute;n es necesario introducir su CURP y dar clic en el boton \"Identificar Cambios\" para consultarla con la entidad externa RENAPO";
		icaController.mostrar(mensajeOtros);
	}
}


// ---------------------------------------------------
// Actualizaci�n de domiicilio
//---------------------------------------------------

var cambiarDomicilio = function() {
	
	var objDomicilio =  this;
	
	if(objDomicilio != undefined && objDomicilio != null) {
		docimicilioUbicado=true;
		try {
			
			$('#domicilio\\.clave').val(objDomicilio.clave);
			$('#domicilio\\.codigoPostal\\.codigoPostal').val(objDomicilio.codigoPostal.codigoPostal);
			$('#domicilio\\.asentamiento\\.clave').val(objDomicilio.asentamiento.clave);
			$('#domicilio\\.asentamiento\\.nombre').val(objDomicilio.asentamiento.nombre);
			$('#domicilio\\.asentamiento\\.localidad\\.clave').val(objDomicilio.asentamiento.localidad.clave);
			$('#domicilio\\.asentamiento\\.localidad\\.nombre').val(objDomicilio.asentamiento.localidad.nombre);
			$('#domicilio\\.asentamiento\\.localidad\\.municipio\\.clave').val(objDomicilio.asentamiento.localidad.municipio.clave);
			$('#domicilio\\.asentamiento\\.localidad\\.municipio\\.nombre').val(objDomicilio.asentamiento.localidad.municipio.nombre);
			$('#domicilio\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave').val(objDomicilio.asentamiento.localidad.municipio.entidadFederativa.clave);
			$('#domicilio\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.nombre').val(objDomicilio.asentamiento.localidad.municipio.entidadFederativa.nombre);
			
			$('#domicilio\\.numExterior1').val(objDomicilio.numExterior1);
			$('#domicilio\\.numExteriorAlf').val(objDomicilio.numExteriorAlf);
			$('#domicilio\\.numInterior').val(objDomicilio.numInterior);
			$('#domicilio\\.numInteriorAlf').val(objDomicilio.numInteriorAlf);
			$('#domicilio\\.numExterior2').val(objDomicilio.numExterior2);
			// Si el tipo de vialidad para la vialidad primaria vienen nulo no
			// ponemos esos campos
			
			if(objDomicilio.vialidadPrimaria != null && objDomicilio.vialidadPrimaria != undefined) {
				$('#domicilio\\.vialidadPrimaria\\.clave').val(objDomicilio.vialidadPrimaria.clave);
				$('#domicilio\\.vialidadPrimaria\\.nombre').val(objDomicilio.vialidadPrimaria.nombre);
				
				if(objDomicilio.vialidadPrimaria.tipoVialidad != undefined && objDomicilio.vialidadPrimaria.tipoVialidad != null) {
					$('#domicilio\\.vialidadPrimaria\\.tipoVialidad\\.clave').val(objDomicilio.vialidadPrimaria.tipoVialidad.clave);
					$('#domicilio\\.vialidadPrimaria\\.tipoVialidad\\.descripcion').val(objDomicilio.vialidadPrimaria.tipoVialidad.descripcion);
				}
			} else {
				$('#domicilio\\.vialidadPrimaria\\.clave').val("");
				$('#domicilio\\.vialidadPrimaria\\.nombre').val("");
				$('#domicilio\\.vialidadPrimaria\\.tipoVialidad\\.clave').val("");
				$('#domicilio\\.vialidadPrimaria\\.tipoVialidad\\.descripcion').val("");
			}
			
			if(objDomicilio.vialidadReferenciaPrimaria != null && objDomicilio.vialidadReferenciaPrimaria != undefined) {
				$('#domicilio\\.vialidadReferenciaPrimaria\\.clave').val(objDomicilio.vialidadReferenciaPrimaria.clave);
				$('#domicilio\\.vialidadReferenciaPrimaria\\.nombre').val(objDomicilio.vialidadReferenciaPrimaria.nombre);
				// Si el tipo de vialidad vienen nulo no ponemos esos campos
				if(objDomicilio.vialidadReferenciaPrimaria.tipoVialidad != undefined && objDomicilio.vialidadReferenciaPrimaria.tipoVialidad != null) {
					$('#domicilio\\.vialidadReferenciaPrimaria\\.tipoVialidad\\.clave').val(objDomicilio.vialidadReferenciaPrimaria.tipoVialidad.clave);
					$('#domicilio\\.vialidadReferenciaPrimaria\\.tipoVialidad\\.descripcion').val(objDomicilio.vialidadReferenciaPrimaria.tipoVialidad.descripcion);
				}
			} else {
				$('#domicilio\\.vialidadReferenciaPrimaria\\.clave').val("");
				$('#domicilio\\.vialidadReferenciaPrimaria\\.nombre').val("");
				$('#domicilio\\.vialidadReferenciaPrimaria\\.tipoVialidad\\.clave').val("");
				$('#domicilio\\.vialidadReferenciaPrimaria\\.tipoVialidad\\.descripcion').val("");
			}
			
			if(objDomicilio.vialidadReferenciaSecundaria != null && objDomicilio.vialidadReferenciaSecundaria != undefined) {
				$('#domicilio\\.vialidadReferenciaSecundaria\\.clave').val(objDomicilio.vialidadReferenciaSecundaria.clave);
				$('#domicilio\\.vialidadReferenciaSecundaria\\.nombre').val(objDomicilio.vialidadReferenciaSecundaria.nombre);
				// Si el tipo de vialidad vienen nulo no ponemos esos campos
				if(objDomicilio.vialidadReferenciaSecundaria.tipoVialidad != undefined && objDomicilio.vialidadReferenciaSecundaria.tipoVialidad != null) {
					$('#domicilio\\.vialidadReferenciaSecundaria\\.tipoVialidad\\.clave').val(objDomicilio.vialidadReferenciaSecundaria.tipoVialidad.clave);
					$('#domicilio\\.vialidadReferenciaSecundaria\\.tipoVialidad\\.descripcion').val(objDomicilio.vialidadReferenciaSecundaria.tipoVialidad.descripcion);
				}
			} else {
				$('#domicilio\\.vialidadReferenciaSecundaria\\.clave').val("");
				$('#domicilio\\.vialidadReferenciaSecundaria\\.nombre').val("");
				$('#domicilio\\.vialidadReferenciaSecundaria\\.tipoVialidad\\.clave').val("");
				$('#domicilio\\.vialidadReferenciaSecundaria\\.tipoVialidad\\.descripcion').val("");
			}
			
			if(objDomicilio.vialidadReferenciaPosterior != null && objDomicilio.vialidadReferenciaPosterior != undefined) {
				$('#domicilio\\.vialidadReferenciaPosterior\\.clave').val(objDomicilio.vialidadReferenciaPosterior.clave);
				$('#domicilio\\.vialidadReferenciaPosterior\\.nombre').val(objDomicilio.vialidadReferenciaPosterior.nombre);
				// Si el tipo de vialidad vienen nulo no ponemos esos campos
				if(objDomicilio.vialidadReferenciaPosterior.tipoVialidad != undefined && objDomicilio.vialidadReferenciaPosterior.tipoVialidad != null) {
					$('#domicilio\\.vialidadReferenciaPosterior\\.tipoVialidad\\.clave').val(objDomicilio.vialidadReferenciaPosterior.tipoVialidad.clave);
					$('#domicilio\\.vialidadReferenciaPosterior\\.tipoVialidad\\.descripcion').val(objDomicilio.vialidadReferenciaPosterior.tipoVialidad.descripcion);
				} 
			} else {
				$('#domicilio\\.vialidadReferenciaPosterior\\.clave').val("");
				$('#domicilio\\.vialidadReferenciaPosterior\\.nombre').val("");
				$('#domicilio\\.vialidadReferenciaPosterior\\.tipoVialidad\\.clave').val("");
				$('#domicilio\\.vialidadReferenciaPosterior\\.tipoVialidad\\.descripcion').val("");
			}
			
			$("#domicilio\\.calle").val(objDomicilio.calle);
			$("#domicilio\\.tipoBusquedaVialidad").val(objDomicilio.tipoBusquedaVialidad);
			
			if(objDomicilio.domicilioCarretera != undefined) {
				$("#domicilio\\.domicilioCarretera\\.terminoGeneral\\.descripcion").val(objDomicilio.domicilioCarretera.terminoGeneral.descripcion);
				$("#domicilio\\.domicilioCarretera\\.terminoGeneral\\.clave").val(objDomicilio.domicilioCarretera.terminoGeneral.clave);
				$("#domicilio\\.domicilioCarretera\\.derechoTransito\\.descripcion").val(objDomicilio.domicilioCarretera.derechoTransito.descripcion);
				$("#domicilio\\.domicilioCarretera\\.derechoTransito\\.clave").val(objDomicilio.domicilioCarretera.derechoTransito.clave);
				$("#domicilio\\.domicilioCarretera\\.origen").val(objDomicilio.domicilioCarretera.origen);
				$("#domicilio\\.domicilioCarretera\\.destino").val(objDomicilio.domicilioCarretera.destino);
				$("#domicilio\\.domicilioCarretera\\.administracion\\.descripcion").val(objDomicilio.domicilioCarretera.administracion.descripcion);
				$("#domicilio\\.domicilioCarretera\\.administracion\\.clave").val(objDomicilio.domicilioCarretera.administracion.clave);
				$("#domicilio\\.domicilioCarretera\\.cadenamiento").val(objDomicilio.domicilioCarretera.cadenamiento);
				$("#domicilio\\.domicilioCarretera\\.codigoCarretera").val(objDomicilio.domicilioCarretera.codigoCarretera);
			} else{
				$("#domicilio\\.domicilioCarretera\\.terminoGeneral\\.descripcion").val('');
				$("#domicilio\\.domicilioCarretera\\.terminoGeneral\\.clave").val('');
				$("#domicilio\\.domicilioCarretera\\.derechoTransito\\.descripcion").val('');
				$("#domicilio\\.domicilioCarretera\\.derechoTransito\\.clave").val('');
				$("#domicilio\\.domicilioCarretera\\.origen").val('');
				$("#domicilio\\.domicilioCarretera\\.destino").val('');
				$("#domicilio\\.domicilioCarretera\\.administracion\\.descripcion").val('');
				$("#domicilio\\.domicilioCarretera\\.administracion\\.clave").val('');
				$("#domicilio\\.domicilioCarretera\\.cadenamiento").val('');
				$("#domicilio\\.domicilioCarretera\\.codigoCarretera").val('');
			}
			
			if(objDomicilio.domicilioCamino != undefined) {
				$("#domicilio\\.domicilioCamino\\.terminoGeneral\\.descripcion").val(objDomicilio.domicilioCamino.terminoGeneral.descripcion);
				$("#domicilio\\.domicilioCamino\\.terminoGeneral\\.clave").val(objDomicilio.domicilioCamino.terminoGeneral.clave);
				$("#domicilio\\.domicilioCamino\\.margen\\.descripcion").val(objDomicilio.domicilioCamino.margen.descripcion);
				$("#domicilio\\.domicilioCamino\\.margen\\.clave").val(objDomicilio.domicilioCamino.margen.clave);
				$("#domicilio\\.domicilioCamino\\.origen").val(objDomicilio.domicilioCamino.origen);
				$("#domicilio\\.domicilioCamino\\.destino").val(objDomicilio.domicilioCamino.destino);
				$("#domicilio\\.domicilioCamino\\.cadenamiento").val(objDomicilio.domicilioCamino.cadenamiento);
			} else {
				$("#domicilio\\.domicilioCamino\\.terminoGeneral\\.descripcion").val('');
				$("#domicilio\\.domicilioCamino\\.terminoGeneral\\.clave").val('');
				$("#domicilio\\.domicilioCamino\\.margen\\.descripcion").val('');
				$("#domicilio\\.domicilioCamino\\.margen\\.clave").val('');
				$("#domicilio\\.domicilioCamino\\.origen").val('');
				$("#domicilio\\.domicilioCamino\\.destino").val('');
				$("#domicilio\\.domicilioCamino\\.cadenamiento").val('');
			}
		} catch(e) {}
	}
};






// -------------------------------------------------
// Dialogos de error
//-------------------------------------------------

/**
 * MEtodo para cerrar un dialogo y borrar su contenido
 * @param $dialogo
 */
function cierraDialogo($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
}


function mensajeConfirmacion(mensaje){
	$ventana = $('<div></div');
	
	$ventana.html(mensaje);	
	$ventana.dialog({
		autoOpen : false,
		title: 'Mensaje',
		show: "blind",
		modal: true,
		width: 500,
		buttons: {
			"Cerrar": function() {
				cierraDialogo($(this));
			}
		}
	
		}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	
	$ventana.dialog('open');
}


function errorTramite() {
		var mensajeError = '<div class="ui-widget">' +
		'<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">'+
		'<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>' +
		'<strong>Los datos personales no coinciden con la CURP establecida</strong></p></div></div>';

		$razonRechazo = $('<div></div');
		$razonRechazo.html(mensajeError);
		$razonRechazo.dialog({
			autoOpen : false,
			title: '',
			show: "blind",
			resizable: false,
			modal: true,
			width: 500,
			buttons: {
				"Cerrar": function() {
					cierraDialogo($(this));
				}
			}
		}
		).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
		
		$razonRechazo.dialog('open');
	}
 
 
 
 function errorSinRespuestaRenapo() {
		var mensajeError = '<div class="ui-widget">' +
		'<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">'+
		'<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>' +
		'<strong>No fue posible validar a la persona en RENAPO por lo cual sera calificado por el IMSS</strong></p></div></div>';

		$razonRechazo = $('<div></div');
		$razonRechazo.html(mensajeError);
		$razonRechazo.dialog({
			autoOpen : false,
			title: '',
			show: "blind",
			resizable: false,
			modal: true,
			width: 500,
			buttons: {
				"Cerrar": function() {
					cierraDialogo($(this));
					verificarRequisitos();
				}
			}
		}
		).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
		
		$razonRechazo.dialog('open');
	}

 

 function errorCambioParentescoRecienNacido() {
		var mensajeError = '<div class="ui-widget">' +
		'<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">'+
		'<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>' +
		'<strong>No es posible modificar el parentesco para un reci&eacute;n nacido</strong></p></div></div>';

		$razonRechazo = $('<div></div');
		$razonRechazo.html(mensajeError);
		$razonRechazo.dialog({
			autoOpen : false,
			title: 'Error',
			show: "blind",
			resizable: false,
			modal: true,
			width: 500,
			buttons: {
				"Cerrar": function() {
					cierraDialogo($(this));
				}
			}
		}
		).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
		
		$razonRechazo.dialog('open');
}
 
 function errorNoCambioDatos() {
		var mensajeError = '<div class="ui-widget">' +
		'<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">'+
		'<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>' +
		'<strong>No se ha modificado ning&uacute;n campo</strong></p></div></div>';

		$razonRechazo = $('<div></div');
		$razonRechazo.html(mensajeError);
		$razonRechazo.dialog({
			autoOpen : false,
			title: '',
			show: "blind",
			resizable: false,
			modal: true,
			width: 500,
			buttons: {
				"Cerrar": function() {
					cierraDialogo($(this));
				}
			}
		}
		).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
		
		$razonRechazo.dialog('open');
}
 
 
 
	
// ---------------------------------------
// Validaciones
// ---------------------------------------
 
/**
 * Valida que los datos de la curp capturada coincidan con los datos en pantalla
 */
function errorCambioCurp(){
	
	// -------------------------------------------------------------------
	// Se hace una transformaci�n a nivel CSS a UpperCase, pero el 
	// javascript obtienen el valor en minusculas
	// -------------------------------------------------------------------
	var curpActual = $("#curpActual").val().toUpperCase(); 
	var curpCap = $("#curpCap").val().toUpperCase();
	var curpValidada = $("#curpValidada").val().toUpperCase();
	
	if ((curpActual != curpCap) && (curpCap != curpValidada) ){
		return true;
	}

	
	if(curpValidada.length > 0){
		if( curpCap != curpValidada )
			return true;
	}

	
	return false;
} 
 

function validarCombosRegistro(){
	var resultado =true;
     
	$("#errorLugarNacimiento").html('');
	$("#errorSexo").html('');
	$("#errorEstadoCivil").html('');
	$("#errorParentesco").html('');
	
	
	if($("#sexo\\.idSexo")[0].selectedIndex==0){
		$("#errorSexo").html("Obligatorio");
		resultado=false;
	}
	
	return resultado;
}
	
	
	
 function verificarCambioDatos() {
	 
	 //var cambioDomicilio = $.trim($("#domicilio\\.clave").val()).length == 0;
	 var cambioDomicilio = valdaDatosMinimosDomicilioAnteriorActualDiferentes("registro");
	 var cambioDatos = verificarCambioDatosPersonales();
	 var cambioMedios = verificarCambioMediosDeContacto();
	 var cambioParentesco = verificarCambioParentesco();
	 var cambioEstadoCivil = verificarCambioEstadoCivil();
	 
	 return cambioDomicilio || cambioDatos || cambioMedios || cambioParentesco || cambioEstadoCivil;
	 
 }
 
 function verificarCambioParentesco() {
	 if($.trim($("#idParentescoActual").val()) != $.trim($("#parentesco\\.idParentesco").val()))
		 return true;
 }
 
 function verificarCambioEstadoCivil () {
	 if( ($.trim($("#estadoCivil\\.idEstadoCivil").val()) != "-1") && ($.trim($("#idEdoCivilActual").val()) != $.trim($("#estadoCivil\\.idEstadoCivil").val())) )
		 return true;
	 
	 return false;
 }
 
 
 function verificarLugarDeNacimiento() {
	 var idLugarActual = $.trim($("#idLugarNacActual").val());
	 var idLugarNuevo = $.trim($("#lugarNacimiento\\.clave").val());
	 
	 if(idLugarActual == "") {
		 if(idLugarNuevo != "" && idLugarNuevo != "0" && idLugarNuevo != "-1") {
			 return true;
		 }
	 } else {
		 if(idLugarActual != idLugarNuevo && (idLugarNuevo != "" && idLugarNuevo != "0" && idLugarNuevo != "-1")) {
			 return true;
		 }
	 }
	 
	 return false;
 }
 
 function verificarCambioDatosPersonales() {
	 
	 if($.trim($("#nombreActual").val()) != $.trim($("#nombre").val()))
		 return true;
	 if($.trim($("#pApellidoActual").val()) != $.trim($("#primerApellido").val()))
		 return true;
	 if($.trim($("#sApellidoActual").val()) != $.trim($("#segundoApellido").val()))
		 return true;
	 if($.trim($("#fechaNacActual").val()) != $.trim($("#fechaNacimiento").val())){
		 return true;
	 }
	 
	 if(verificarLugarDeNacimiento())
		 return true;
	 if($.trim($("#curpActual").val()) != $.trim($("#curpCap").val()))
		 return true;
	 if($.trim($("#idSexoActual").val()) != $.trim($("#sexo\\.idSexo").val()))
		 return true;
	 
	 return false;
 }
 
 function verificarCambioMediosDeContacto() {
	 if($.trim($("#correoEActual").val()) != $.trim($("#correoElectronico\\.correo").val()))
		 return true;
	 if($.trim($("#telefonoActual").val()) != $.trim($("#telefonoFijo\\.claveLada").val()))
		 return true;
	 if($.trim($("#celularActual").val()) != $.trim($("#telefonoMovil\\.numero").val()))
		 return true;
	 if($.trim($("#facebookActual").val()) != $.trim($("#facebook\\.cuenta").val()))
		 return true;
	 if($.trim($("#twitterActual").val()) != $.trim($("#twitter\\.cuenta").val()))
		 return true;
	 
	 return false;
 }
 
 
 
 // ---------------------------------------------------------
 // Muestra y oculta los mensajes de Identificar cambios
//---------------------------------------------------------
 icaController = {
	mensaje  : "Para actualizar su fecha de nacimiento es necesario que de clic en el boton \"Identificar Cambios\" para consultarla con la entidad externa RENAPO", 
	mensajeCurpInvalida : "La CURP proporcionada es incorrecta",
	
	ocultar : function(){
		$("#llamarIca").hide();
		$("#mensajeCurp").hide();
	},
	
	mostrar : function(_mensaje){
		
		var _mensajeAux = this.mensaje;
		
		$("#llamarIca").show();
		$("#mensajeCurp").show();
		
		if( _mensaje )
			_mensajeAux = _mensaje;
			
		$("#mensajeBusquedaPorCurp").html(_mensajeAux);
	 },
	 
	 identificaCambios : function(){
		 var curp = $('#curpCap').val();
		 try{
			 if( (curp.length > 0) && ( curp.length == 18 )  ){
				//inicializamos el objeto del ica
				identificarCambiosAutomaticosPersonaFisicaCtrl.init('dialogICA');
				
				//parametros para iniciar la pantalla de ica
				var datosEntradaICA = {
					indMostrarPantalla : true,
					idPersona : $('#idPersona').val(),
					curp : $('#curpCap').val(),
					indBusquedaRENAPO : true,
					indUsuarioExterno : false,
					rfc: '',
					nombrePersona : '',
					primerApellido :'',
					segundoApellido : '',
					indBusquedaSAT : false
				};
				
				//Establecemos los parametros de entrada
				identificarCambiosAutomaticosPersonaFisicaCtrl.datosEntrada = datosEntradaICA;
				
				//establecemos el callback
				identificarCambiosAutomaticosPersonaFisicaCtrl.setOnCloseCallback(callbackICA); 
			
				//abrimos la pantalla del ica
				identificarCambiosAutomaticosPersonaFisicaCtrl.identificarCambios();
			 
			 }else{
				 mensajeConfirmacion(this.mensajeCurpInvalida);
			 }
				 
		 }catch(e){
		 }
		 
		 
	 }
 };
 
 
 function llamarICA() {
	icaController.identificaCambios();
}
 
 
 
// ------------------------------------------------
// Funciones de fecha
//------------------------------------------------
 function dias(mes, anno) {
	    mes = parseInt(mes);
	    mes+=1;
		anno = parseInt(anno);
	    switch (mes) {
		    case 1 : case 3 : case 5 : case 7 : case 8 : case 10 : case 12 : return 31;
			case 2 : return (anno % 4 == 0) ? 29 : 28;
		}
	    
		return 30;
	 }
	 
	 function ultimodia(elemento) {
	    var arreglo = elemento.split("/");
		  var dia = arreglo[1];
		  var mes = arreglo[0];
		  var anno = arreglo[2];
		  
	    dia = dias(mes, anno);
		  
		$("#fecha2").val(mes+"/"+dia+"/"+anno);
	 }

	 
	 function convertirAFecha(string) {
		  var date = new Date();
		  
		  var fechaArr = string.split('/');
			 var aho = fechaArr[2];
			 var mes = fechaArr[1];
			 var dia = fechaArr[0];
		  
		  date.setMonth(mes-1); //en javascript los meses van de 0 a 11
		  date.setDate(dia);
		  date.setYear(aho);
		  return date;
	}

	 
	 
	 
// -------------------------------------------------------------------
// Funciones de Wizard Domicilio
// -------------------------------------------------------------------
	 
 function setAsentamientosUmf( idUMF , idAsentamiento) {
		
	var umf = {'idUMF': idUMF};
	var ajax_source = context_path + "/umf/getAsentamientos";

	$.postJSON(ajax_source, umf, function(result){
		asentamientosUbicados = result;
		var options = "<option value=''> -- Por favor seleccione -- </option>";
		for(var i = 0 ; i < asentamientosUbicados.length ; i++){
			if(idAsentamiento == asentamientosUbicados[i].clave)
				options += "<option value='" + asentamientosUbicados[i].clave + "' selected='selected'>" + asentamientosUbicados[i].nombre + "</option>";
			else
				options += "<option value='" + asentamientosUbicados[i].clave + "'>" + asentamientosUbicados[i].nombre + "</option>";
		}
		
		$("#domicilio\\.asentamiento\\.clave").html(options);
	});
	
	
}

/**
 * Metodo paracambiar localidad asi como codigo postal 
 * cuando seleccionemos un nuevo asentamiento
 */
function setLocalidad(){
	var index = $("select#domicilio\\.asentamiento\\.clave")[0].selectedIndex;
	if((asentamientosUbicados!=null)&&(asentamientosUbicados[index-1]!=null)){
		var asent=asentamientosUbicados[index-1];
	
		$("#domicilio\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave").val(asent.localidad.municipio.entidadFederativa.clave);
		$("#domicilio\\.asentamiento\\.localidad\\.municipio\\.clave").val(asent.localidad.municipio.clave);
		$("#domicilio\\.asentamiento\\.localidad\\.clave").val(asent.localidad.clave);
		$("#domicilio\\.asentamiento\\.localidad\\.nombre").val(""+asent.localidad.nombre);
		
		if(asent.codigoPostal!=null&&asent.codigoPostal.codigoPostal!=null){
			$("#domicilio\\.codigoPostal\\.codigoPostal").val(""+asent.codigoPostal.codigoPostal);
		}
	}
}



function setVialidades(vialidades,select,numeroCombo){
	listaVialidades = vialidades;
	var clave = undefined;
	var tipoVialidad = undefined; 
	var options = "<option value='-1'> -- Por favor seleccione -- </option>";
	
		
	if(numeroCombo==1){
		clave=	$("#vialidadPrimaria").val();
		tipoVialidad=$("#tipoVialidad");
	}
	else
	if(numeroCombo==2){
		clave=	$("#vialidadRefPrimaria").val();
		tipoVialidad=$("#tipoVialidadRefPrimaria");
	}
	else
	if(numeroCombo==3){
		clave=	$("#vialidadRefSecundaria").val();
		tipoVialidad=$("#tipoVialidadRefSecundaria");
	}else
	if(numeroCombo==4){
		clave=	$("#vialidadRefposterior").val();
		tipoVialidad=$("#tipoVialidadRefPosterior");
	}
	
    for(var i = 0 ; i < vialidades.length ; i++){
    	
    		if(clave == vialidades[i].clave){
				options += "<option value='" + vialidades[i].clave + "' selected='selected'>" + vialidades[i].nombre + "</option>";
				colocarTipoVialidad(tipoVialidad,i+1);
			}else{
    			options += "<option value='" + vialidades[i].clave + "'>" + vialidades[i].nombre + "</option>";
    		}
    } 
    $(""+select).html(options); 

	
}

function colocarTipoVialidad(tipoVialidad,index){
	if(index!=0){
		tipoVialidad.val(listaVialidades[index-1].tipoVialidad.descripcion);
	}else{
		tipoVialidad.val('');
		
	}
	
}

function findVialidades(cveEnt,cveMun,cveLoc){
	var url =' /gestionDomicilios-web/domicilio/nacional/ubicar/get/vialidades';
	
	$.getJSON( url , {'cveEnt' : cveEnt, 'cveMun':cveMun , 'cveLoc':cveLoc }, function(data){ 
            //Crea un combo de vialidades 
		    setVialidades(data.vialidades , 'select#domicilio\\.vialidadPrimaria\\.clave',1); 
            setVialidades(data.vialidades , 'select#domicilio\\.vialidadReferenciaPrimaria\\.clave',2);
		    setVialidades(data.vialidades , 'select#domicilio\\.vialidadReferenciaSecundaria\\.clave',3);
		    setVialidades(data.vialidades , 'select#domicilio\\.vialidadReferenciaPosterior\\.clave',4);
    	}).error(function(data){ 
    }); 
}

 
 	 
// -----------------------------------------------------------------
// -----------------------------------------------------------------
 /**
  * Metodo con el que deshabilitamos campos dependiendo del parentesco
  * @param idParentesco
  */
function deshabilitaParentesco(idParentesco) {
 	
 	//deshabilitamos los campos principales
	$("#nss").attr("disabled","disabled");
	//$("#parentesco\\.idParentesco").attr("disabled","disabled");
	$("#calidad").attr("disabled","disabled");
	
	//Deshabilitamos los campos necesarios para asegurado o pensionado
	if(idParentesco == ASEGURADO || idParentesco == PENSIONADO) {
		$("#parentesco\\.descripcion").attr("disabled","disabled");
		$("#curp").attr("disabled","disabled");
		$("#nombre").attr("disabled","disabled");
		$("#sexo\\.idSexo").attr("disabled","disabled");
		$("#primerApellido").attr("disabled","disabled");
		$("#segundoApellido").attr("disabled","disabled");
		$("#curpCap").attr("disabled","disabled");
		$("#parentesco\\.descripcion").attr("disabled","disabled");
	}
	
	//Si el parentesco es padres o concubinarios deshabilitamos el boton de cambio de domicilio
	//ademas de ocultar el area de domicilio ya que para estos parentescos no se puede cambiar el actual domicilio
	if((idParentesco==CONCUBINARIO) || ((idParentesco==PADRES || idParentesco==MADRES) && ($("#patronImss").val() == 0 && $("#tieneAcuerdo").val() == 0
			&& $("#aseguradoFallecido").val()!=1))) {
		$('#ubicar').hide();
	}

	//deshabiliatamos el domicilio para cualquier tipo de parentesco ya que solo
	//puede modificarse al presionar el boton de cambio de domicilio
	$("#domicilioNuevo input:text").each(function(index) {
		$(this).removeAttr("readonly");
		$(this).attr("disabled","disabled");
	});	
}

	 
function setDatosPersona(persona) {
	$("#curpCap").val(persona.curp);
	$("#curpValidada").val(persona.curp);
	$("#sexo\\.idSexo").val(persona.sexo.idSexo);
	$("#nombre").val(persona.nombre);
	$("#primerApellido").val(persona.primerApellido);
	$("#segundoApellido").val(persona.segundoApellido);
	$("#fechaNacimiento").val(persona.fechaNacimientoFormateada);
	$("#lugarNacimiento\\.clave").val(persona.lugarNacimiento.clave);
	
	
	// --------------------------------------------------------------------------------
	// Si la fecha de nacimiento no era la correcta se ocult� el boton de aceptar
	// En caso de que capture una nueva CURP con los datos correctos, se debe
	// habilitar el bot�n nuevamente
	// --------------------------------------------------------------------------------
	$('#aceptar').show();
	
	
	// -----------------------------------------------------------------------------
	// Si no tenia acta cuando se consulto la CURP se asigna la bandera a cero, por
	// default esta a 1
	// -----------------------------------------------------------------------------
	 if(identificarCambiosAutomaticosPersonaFisicaCtrl.getDatosSalida().personaFisicaIMSS.actaNacimiento == null)
		 $("#indICATeniaActa").val(0);
	 else
		 $("#indICATeniaActa").val(1);
	
}



function botonesConfirmacion($div){
	$div.html('');
	var guardar ='<input type="button" id="aceptar"  value="Aceptar" onclick="confirmacion()" class="mboton" />';
	var cancelar='<input type="button" id="cancelar"  value="Cancelar" class="mboton" onclick="cancelarCorreccion()"/>';
	$div.append(guardar);
	$div.append(cancelar);
}

function botonesGuardado($div){
	$div.html('');
	var guardar ='<input type="button"  id="aceptar"  value="Aceptar" class="mboton" onclick="salvarCorreccion()"/>';
	var regresar ='<input type="button" id="regresar" value="Regresar" onclick="botonRegresar()" class="mboton" />';
	var cancelar='<input type="button" value="Cancelar" id="cancelar" class="mboton" onclick="cancelarCorreccion()"/>';
	$div.append(guardar);
	$div.append(regresar);
	$div.append(cancelar);
}


///inicio/getParentescos
function findParentescos(idParentesco){
	if(idParentesco != 5 && idParentesco != 6) {
		var url = context_path + "/inicio/getParentescos";
		$.postJSON(url, {}, function(result) {
			var options = "<option value=''> -- Por favor seleccione -- </option>";
			
			for(var i = 0 ; i < result.length ; i++){
				
				if(result[i].idParentesco!=5 && result[i].idParentesco!=6){
					if(idParentesco == result[i].idParentesco)
							options += "<option value='" + result[i].idParentesco + "' selected='selected'>" + result[i].descripcion + "</option>";
						else
							options += "<option value='" + result[i].idParentesco + "'>" + result[i].descripcion + "</option>";
				}
			}
				
			$("#parentesco\\.idParentesco").html(options);
		});
	}
}


/**
 * Asigna valores a los campos deshabilitados en base al parentesco
 * 
 * En ocaciones la llamda Ajax tarda en regresar los datos y los select no tienen valores asignados
 */
function preSeleccionarOpciones(){
	
	// ---------------------------------------------------------------------
	// Verificara que se pueden asignar los valores cada segundo hasta que
	// toda la informaci�n este cargada
	// ---------------------------------------------------------------------
		
	setTimeout(function(){
		
		var $sexoAseg = $('#sexo\\.idSexo');
		var $parentesco = $('#parentesco\\.idParentesco');
		var $estadoCivil = $('#estadoCivil\\.idEstadoCivil');

		if( $sexoAseg && $parentesco.val() && $estadoCivil ){
			
			var parentescoChildrenSize = 0;
			if( $parentesco.is("input") ){
				parentescoChildrenSize = 1;
			}else{
				parentescoChildrenSize = $parentesco.children("option").size();
			} 
			
			if(    ( $sexoAseg.children("option").size() > 0 )
				&& ( parentescoChildrenSize > 0 )
				&& ( $estadoCivil.children("option").size() > 0  )
			){
				
				$("#parentesco\\.idParentesco").change(function() {
					comboSexo(true);
				});
				
				comboSexo();
				return true;
			}
		}
		
		preSeleccionarOpciones();
		
	},500);
	

	
}

function limpiarDocumentos(data) {
	var cambioDomicilio = $.trim($("#domicilio\\.clave").val()).length == 0;
	var cambioDerechohabiente = verificarCambioParentesco() || verificarCambioEstadoCivil();
	var cambioDatos = verificarCambioDatosPersonales();
	
	
	var documentos = data.tipoDocumentoProbatorioList;
	
	if (!cambioDomicilio) {
		documentos = quitarElemento(documentos, 3);
	}
	if (!cambioDatos && !cambioDerechohabiente) {
		documentos = quitarElemento(documentos, 2);
	}
	data.tipoDocumentoProbatorioList = documentos;
	return data;
}

function quitarElemento(arreglo, idElemento){
	$.each(arreglo, function(i) {
		if(arreglo[i].idTipoDocumentoProbatorio == idElemento){
			arreglo.splice(i,1);
	        return false;
		}
	});
	return arreglo;
}


function activarFechaNacAsegurado(){
	var mesNacAs = $('#mesRegistroNac').val();
	var aniosNacAs = $('#anioRegistroNac').val();
	var fechaNacimiento = convertirAFechaMesAnio(mesNacAs,aniosNacAs);
	
	var ultimoDia = dias2(mesNacAs, aniosNacAs);
	var ultimo = new Date();
	var minimo = new Date();
	
	minimo.setFullYear(fechaNacimiento.getFullYear(),fechaNacimiento.getMonth(),1);
	ultimo.setFullYear(fechaNacimiento.getFullYear(),fechaNacimiento.getMonth(),ultimoDia);
	
	$('#fechaNacimiento').datepicker( "destroy" );
	$('#fechaNacimiento').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : false,
		changeYear : false,
		minDate:minimo,
	    maxDate:ultimo
	});
	$('#fechaNacimiento').removeAttr("disabled");
	$("#fechaNacimiento").css("cssText","background-color:#ffffff !important;");
	$("#fechaNacimiento").css({cursor:'pointer',width:'50%'});
	
}


function convertirAFechaMesAnio(mes, anio) {
	var date = new Date();
	  date.setMonth(mes-1); //en javascript los meses van de 0 a 11
	  date.setDate(1);
	  date.setYear(anio);
	  
	  return date;
}

function dias2(mes, anno) {
	anno = parseInt(anno);
	mes = parseInt(mes); 
    switch (mes) {
	    case 1 : case 3 : case 5 : case 7 : case 8 : case 10 : case 12 : return 31;
		case 2 : return (anno % 4 == 0) ? 29 : 28;
	}
    
	return 30;
 }


function esCorrectaFechaNacimientoRecienNacido(){
	 var esRecienNacido = ($("form#registro input#indicadorRN").val() == '1');
	
	 if($.trim($("#fechaNacActual").val()) != $.trim($("#fechaNacimiento").val())){
		 
		 if( esRecienNacido ){
			 errorFechaNacimientoRecienNacido();
			 return false;
		 }
		 
	 }
	 
	 return true;
	
}

function errorFechaNacimientoRecienNacido() {
	var mensajeError = '<div class="ui-widget">' +
	'<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">'+
	'<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>' +
	'<strong>La fecha de nacimiento, para el recien nacido, no coincide con la capturada cuando se registro en el sistema.<br />'+
	'No es posible actualizar los datos del derechohabiente.</strong></p></div></div>';

	var $razonRechazo = $('<div></div');
	$razonRechazo.html(mensajeError);
	$razonRechazo.dialog({
		autoOpen : false,
		title: 'Error',
		show: "blind",
		resizable: false,
		modal: true,
		width: 500,
		buttons: {
			"Cerrar": function() {
				cierraDialogo($(this));
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$razonRechazo.dialog('open');
}

function setValidadorFormulario() {
	validateForm.allowOnlyRegularExpression( $('.alfanumerico_espacios'),regularExpression.alfanumerico_espacios);
	validateForm.allowOnlyRegularExpression( $('.alfanumerico'),regularExpression.alfanumerico);
	validateForm.allowOnlyRegularExpression( $('.entero_10'),regularExpression.entero_10);
	//deshabilitarCampos('${derechohabiente.parentesco.idParentesco}');
	
	//findParentescos(${derechohabiente.parentesco.idParentesco});
	
	$.validator.addMethod("alphanumeric", function(value, element) { 
        return this.optional(element) || /^[a-z0-9\- ]+$/i.test(value); 
    }, "Username must contain only letters, numbers, or dashes."); 

	
	
	$("#registro").validate({
		 rules:{ 
	    		nombre:{
	    			required:true,
	    			maxlength:50
	    		}
	    		,primerApellido:{
	       			required:true,
	    			maxlength:50
	    		}
	    		,segundoApellido:{
	       			required:false,
	    			maxlength:50
	    		}
	    		,numExteriorAlf:{
					required:true
				}
				,curpCap: {
					equalToCurp: "#curpActual",
					minlength: 18,
					maxlength: 18,
				}
			},
	 		messages: { 
				nombre:{
						required:"Obligatorio",
						 maxlength:"Debe ser de 50 caracteres como m\u00e1ximo",
						 alphanumeric:"Debe ser alfan\u00famerico"
					},
				primerApellido:{
					required:"Obligatorio",
					 maxlength:"Debe ser de 50 caracteres como m\u00e1ximo",
					 alphanumeric:"Debe ser alfan\u00famerico"
				},
				segundoApellido:{
					required:"Obligatorio",
					 maxlength:"Debe ser de 50 caracteres como m\u00e1ximo",
					 alphanumeric:"Debe ser alfan\u00famerico"
				 },
				 numExteriorAlf:{
						required:"Obligatorio"
					}
				}
				,curpCap: {
					minlength: "Debe ser de 18 caracteres",
					maxlength: "Debe ser de 18 caracteres",
					equalToCurp: "Formato incorrecto"
				}
	    });
}
