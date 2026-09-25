/**
 * 
 */
STATIC_NOMBRE_RECIEN_NACIDO = 'RECIEN NACIDO';
STATIC_INSTRUCCIONES_PERSONA_NORMAL = 'Para ubicar a la persona de clic en el bot&oacute;n <strong>Buscar Persona</strong>.';
STATIC_INSTRUCCIONES_RECIEN_NACIDO = 'Introduzca los datos personales del reci&eacute;n nacido.';
STATIC_INSTRUCCIONES_PERSONA_ASEGURADO = 'A continuaci&oacute;n se muestran los datos personales del asegurado o pensionado.';

STATIC_URL_VALIDACIONES = "/${mvn.web.app.root}/tramite/registro/validaciones/datosPersonales";
//Variables de constrol
IS_RECIEN_NACIDO = false;
//asegurado sin domicilio
ASEGURADO_CON_DOMICILIO = false;
//variable para saber si se permite o no la eleccion del estado civil
PEMITIR_ELECCION_ESTADO_CIVIL = false;
//Variable para saber si es o no patron IMSS
IS_PATRON_IMSS = false;
//Objetos que seran usados como combos
C_PARENTESCO = null;
//Combo de estado civil
C_ESTADO_CIVIL = null;
//combo de razon de registro
C_RAZON_REGISTRO = null;
//Objetos de los fieldset
F_DATOS_PERSONALES = null;
//objeto que contendra el fieldset de los datos del domicilio
F_DATOS_DOMICILIO = null;
//formulario del registro
FORM_REGISTRO = null;
//Campo del formulario que contiene el nombre
CAMPO_NOMBRE = null;
//Objetos que representan a lso memnsahes
S_MENSAJES_PERSONA = null;

$(document).ready(function() {
	//Iniciamos el componente de domicilio recortado
	iniciarComponenteDomicilio();
	
	$.blockUI();
	//Seteamos la mascara para la fecha de nacimiento
	$('#fisica\\.fechaNacimiento').mask("99/99/9999");
	 
	//establecemos las mascaras para los campos que solo aceptan numeros y letras
	validateForm.allowOnlyRegularExpression( $('.entero_10'),regularExpression.entero_10);
	validateForm.allowOnlyRegularExpression( $('.alfanumerico_espacios'),regularExpression.alfanumerico_espacios);
	validateForm.allowOnlyRegularExpression( $('.alfabetico_espacios'),regularExpression.alfabetico_espacios);
	 
	//Hacemos los combos objetos
	llenarObjetosCombos();
	
	//llenamos la variables globales
	llenarVariablesGlobales();
	//habiliatar opciones de recien nacido
	var retomarRecienNacido = functionRetomarRecienNacido();
	retomarCamposHijosProcreados();
	//llenamos los parentescos
	llenarComboParentescos();
	//Deshabiliatamos los datos del domicilio
	habilitaDomicilio(false);
	//-------------- Seteo de enventos a botones y combos -------------------------------------
	//Se sete al evento cuando se modifica el combo de parntesco
	C_PARENTESCO.change(validaCambioSelectParentesco);

	if($("#localizaPersona").length > 0) {
		//Se agrega evento de click en el boton de localizar persona
		$("#localizaPersona").click(buscarPersona);
	}
	//evento para check de recien nacido
	$("input[name='varianteRegistro']").on("change", function () {
	    cambioVarianteRegistro(this.value);
	});
	
	$("#hijosProcreadosCheck").change(setearValorHijosProcreados);
	//metodo para enviar el formulario
	if($("#irAConfirmacion").length > 0) { //verificamos si existe el boton en el formulario
		$("#irAConfirmacion").click(validacionesFormulario);
	} else {
		$("#registro").attr("action","");
	}
	//se asigna el evento para regresar al grupo familiar
	$("#regresaraGrupo").click(mostrarMensajeRegresarAGrupo);
	//boton de la guia de tramite
	$("#guia").click(muestraGuia);
	
	if(retomarRecienNacido){
		C_RAZON_REGISTRO.attr("disabled","disabled");
	}
	 
	$.unblockUI();
});

var cambioVarianteRegistro = function(variante) {
	var valorActualVariante = $("#valorActualVariante").val();
	if(variante == 1) {
		habilitarRecienNacido();
	} else {
		if ($('#recienNacidoCheck').css('display') != 'none' && valorActualVariante == 1) {
			deshabilitarRecienNacido();
		}
	}
	
	$("#valorActualVariante").val(variante);
}

var retomarCamposHijosProcreados = function() {
	verOpcionesConcubina();
}

var setearValorHijosProcreados = function() {
	if($('#hijosProcreadosCheck').is(":checked")){
		$("#indHijosProcreados").val(1);
	}else{
		$("#indHijosProcreados").val(0);
	}
}

function functionRetomarRecienNacido() {
	var retomarRecienNacido = CAMPO_NOMBRE.val() == STATIC_NOMBRE_RECIEN_NACIDO;
	verOpcionRecienNacido();
	//verificamos si estamos retomando el registro de un recien nacido
	if(retomarRecienNacido){
		IS_RECIEN_NACIDO = true; 
		habilitarCamposDatosBasicos(true,false);
		$("#recienNacidoCheck").attr("checked",true);
	 }else{
		 habilitarCamposDatosBasicos(false,false);
	 }
	
	return retomarRecienNacido;
}

var muestraGuia = function(){
	var perfil = 1;
	var parentesco = C_PARENTESCO.val();
	
	if(parentesco != "-1") {
		if(parentesco == PARENTESCO_ENUM.PADRES || parentesco == PARENTESCO_ENUM.CONCUBINARIO){
			showGuiaTramite('46',perfil);
		}else if(parentesco == PARENTESCO_ENUM.ASEGURADO || parentesco == PARENTESCO_ENUM.PENSIONADO){
			showGuiaTramite('44',perfil);
		} else {
			showGuiaTramite('47',perfil);
		}
	}
};

/**
 * Metodo que valida si existen errores de captura en los campos del formulario
 * @returns
 */
var validacionesFormulario = function() {
	//se setan las descripciones de los combos
	setearDescripciones();
	//se quitan los espacios innecesarios de los campos
	quitarEspacios();
	//Se esconden los errores de captura
	fnHideErrores("form#registro");
	
	var idParentesco = C_PARENTESCO.val();
	//Validacion para mostrar a padres
	if(((idParentesco == PARENTESCO_ENUM.PADRES && !IS_PATRON_IMSS) ||
			idParentesco == PARENTESCO_ENUM.CONCUBINARIO )&& !ASEGURADO_CON_DOMICILIO ) {
		mostrarBotonConfirmacion(false);
		mostrarMensajeErrorDomicilio();
		muestraUbicarDomicilio(false);
		return;
	}else {
		
		//console.log("el id del parentesco seleccionado en el combo es: " + idParentesco);
		if(validarFechaNacimiento()) {
			FORM_REGISTRO = null;
			FORM_REGISTRO = $("form#registro");
			//Se habilita el contenido del formulario para podder enviar el formulario
			FORM_REGISTRO.habilitarContenido(false);
			//Se transforma el formulario en un objeto
			var tramiteRegistro = FORM_REGISTRO.toObject();
			
			//forzamos el seteo del id del parentesco ya que por alguna razon no se setea el seleccionado en el combo
			tramiteRegistro.parentesco.idParentesco = idParentesco;
			
			//se realiza la llamada al metodo de validaciones
			$.postJSON(STATIC_URL_VALIDACIONES, tramiteRegistro, function(data2) {
				//Se envia el formulario a la pantalla de confirmacion
				var correcto = validarDomicilioCapturado(errorCamposFormulario);
				
				if(correcto) {
					$.blockUI();
					FORM_REGISTRO.attr("action","/${mvn.web.app.root}/tramite/registro/confirmacion");
					FORM_REGISTRO.submit();
				}
			}).error(function(data){
				errorCamposFormulario(data);
			});
		}
	}
};

var errorCamposFormulario = function(data) {
	//habiliatamos los datos basicos
	habilitarCamposDatosBasicos(false,false);
	//Deshabiliatamos los datos del domicilio
	habilitaDomicilio(false);
	
	var idParentesco = C_PARENTESCO.val();
	if( idParentesco == PARENTESCO_ENUM.ASEGURADO || idParentesco == PARENTESCO_ENUM.PENSIONADO){
		activarFechaNacAsegurado();
		C_RAZON_REGISTRO.attr("disabled","disabled");
		C_PARENTESCO.attr("disabled","disabled");
	}
	$.unblockUI();
	mostrarMensajeErrorCaptura();
	
	if(data != undefined && data != null) {
		fnProcesarErrores(data, "form#registro");
	}
}

function validarFechaNacimiento() {
	var fechaString = $('#fisica\\.fechaNacimiento').val();
	//console.log("la fecha de nacimiento es: " + fechaString);
	if($.trim(fechaString) != "") {
		var arreglo = fechaString.split("/");
		var dia = parseInt(arreglo[0],10);
		var mes = parseInt(arreglo[1],10);
		var anio = arreglo[2].substring(0,2);
		
		
		//se modifica fecha
		//se agrega arreglo
		var fechaInvalida = dia == 0 || mes == 0 || dia> dias(""+mes, ""+ arreglo[2]) || mes > 12 || anio == "00";
		
		if(!fechaInvalida) {
			var fechaNac = new Date(arreglo[1]+"/"+arreglo[0]+"/"+arreglo[2]);
			var fechaHoy = new Date();
			
			if(fechaNac.getTime() > fechaHoy.getTime()) {
				fnShowError("#fisica\\.fechaNacimientoError" , "La fecha de nacimiento no pueder ser mayor al dia de hoy"  );
				mostrarMensajeErrorCaptura();
				return false;
			}
		} else {
			fnShowError("#fisica\\.fechaNacimientoError" , "Fecha invalida"  );
			mostrarMensajeErrorCaptura();
			return false;
		}
	} 
	
	return true;
}

function setearDescripciones() {

	setDescripcionCombo("parentesco\\.idParentesco","parentesco\\.descripcion");
	setDescripcionCombo("fisica\\.lugarNacimiento\\.clave","fisica\\.lugarNacimiento\\.nombre");
	setDescripcionCombo("fisica\\.estadoCivil\\.idEstadoCivil","fisica\\.estadoCivil\\.descripcion");
	setDescripcionCombo("fisica\\.sexo\\.idSexo","fisica\\.sexo\\.descripcion");
	setDescripcionCombo("razonRegistro\\.idRazonRegistro","razonRegistro\\.descripcion");
}


function quitarEspacios() {
	$("#fisica\\.nombre").val($.trim($("#fisica\\.nombre").val()));
	$("#fisica\\.primerApellido").val($.trim($("#fisica\\.primerApellido").val()));
	$("#fisica\\.segundoApellido").val($.trim($("#fisica\\.segundoApellido").val()));
}

/**
 * Function para habilitar los campos de recien nacido
 * @returns
 */
var habilitarRecienNacido = function() {
	//obtenemos el nombre actual
	var nombreActual = CAMPO_NOMBRE.val();
	
	var varianteRegistro = $('input:radio[name=varianteRegistro]:checked').val()
	//limpiarMediosDeContacto();
	//Checamos si en check de recien nacido esta habilitado
	if(varianteRegistro == 1){
		//seteamos la razon de registro a recien nacido
		$("#idRazonregistroSeleccionada").val(RAZON_REGISTRO_ENUM.RECIEN_NACIDO);
		//ponemos la variable en true
		IS_RECIEN_NACIDO = true;
		//Establecemos la razon de registro a recien nacido
		C_RAZON_REGISTRO.val(RAZON_REGISTRO_ENUM.RECIEN_NACIDO);
		//desabilitamos el campo nombre
		CAMPO_NOMBRE.attr("disabled","disabled");
		//verificamos si el nombre actual es diferenre a recien nacido
		if(nombreActual != STATIC_NOMBRE_RECIEN_NACIDO) {
			//Si es diferente limpiaremos los campos
			limpiarDatosPersonales();	
			//seteamos el nombre de recien nacido
			CAMPO_NOMBRE.val(STATIC_NOMBRE_RECIEN_NACIDO);
			//Verificamos el sexo del asegurado
			var sexoAsegurado = $("#idSexoAsegurado").val();
			//obtenemos el apellido del asegurado
			var apellidoAsegurado = $("#apellidoAsegurado").val();
			//ponemos el apellido al recien nacido
			if(sexoAsegurado == SEXO_ENUM.HOMBRE) {
				//Si el asegurado es hombre ponemos el apellido en paterno
				$("#fisica\\.primerApellido").val(apellidoAsegurado);
			} else {
				//Si el asegurado es mujer ponemos el apellido en materno
				$("#fisica\\.segundoApellido").val(apellidoAsegurado);
			}
		}
		habilitarCamposDatosBasicos(true,false);//habilitamos los campos de datos personales
		muestraUbicarDomicilio(true);//habiliatamos el boton de mostrar domicilio
		$("#localizaPersona").hide();//escondemos el botn de busqueda por curp
		S_MENSAJES_PERSONA.html(STATIC_INSTRUCCIONES_RECIEN_NACIDO);//ponemos el mensaje en pantalla
		validaEdad();//Validamos la edad para saber la razon de registro
		C_RAZON_REGISTRO.attr("disabled","disabled");
		$('#fisica\\.fechaNacimiento').datepicker( "destroy" );
		$('#fisica\\.fechaNacimiento').datepicker(
			{
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : new Date(),
			yearRange : '-112:+0'
		});
	}else{
		deshabilitarRecienNacido();
	}
	
	
};

var deshabilitarRecienNacido = function() {
	//obtenemos el nombre actual
	var nombreActual = CAMPO_NOMBRE.val();
	var parentescoAReg = $("#idParentescoSeleccionado").val();
	
	if(parentescoAReg == PARENTESCO_ENUM.ASEGURADO || parentescoAReg == PARENTESCO_ENUM.PENSIONADO){
		S_MENSAJES_PERSONA.html(STATIC_INSTRUCCIONES_PERSONA_ASEGURADO);	
	} else {
		S_MENSAJES_PERSONA.html(STATIC_INSTRUCCIONES_PERSONA_NORMAL);//Seteamos instrucciones de busqueda por curp
	}
	
	//si el nombre es recien na
	if(nombreActual == STATIC_NOMBRE_RECIEN_NACIDO) {
		limpiarDatosPersonales();
	}
	//ponemos la bandera de recien nacido en false
	IS_RECIEN_NACIDO = false;
	habilitarCamposDatosBasicos(false,false);//desabilitamos los campos
	//mostramos el boton de busqueda de personas por curp
	$("#localizaPersona").show();
	validaEdad();//validamos la edad
	C_RAZON_REGISTRO.removeAttr("disabled");
}

//funcion que pone los combos en objetos
var llenarObjetosCombos = function() {
	
	C_PARENTESCO = $("#parentesco\\.idParentesco");
	C_ESTADO_CIVIL = $("#fisica\\.estadoCivil\\.idEstadoCivil");
	C_RAZON_REGISTRO = $("#razonRegistro\\.idRazonRegistro");
	
	F_DATOS_PERSONALES = $("#datosPersonalesDerechohabiente");
	F_DATOS_DOMICILIO = $("#datosDomicilioDerechohabiente");
	
	S_MENSAJES_PERSONA= $("#mensajesDatosPersona");
	
	FORM_REGISTRO = $("form#registro");
	
	CAMPO_NOMBRE = $("#fisica\\.nombre");
};

/**
 * funcion para habilitar deshabilitar el con
 */
var habilitaDomicilio = function(habilitar) {
	/*
	var componenteDom = $(ID_COMPONENTE_DOM).data(ID_PLUGIN_DOM);
	if(habilitar) {
		F_DATOS_DOMICILIO.habilitarContenido();
	} else {
		F_DATOS_DOMICILIO.deshabilitarContenido();
	}*/
};

var llenarVariablesGlobales = function() {
	IS_RECIEN_NACIDO = C_RAZON_REGISTRO.val() == RAZON_REGISTRO_ENUM.RECIEN_NACIDO;
	IS_PATRON_IMSS = $("#patronIMSS").val() == "true";
	ASEGURADO_CON_DOMICILIO = $("#aseguradoConDomicilio").val() == "1";
};

/**
 * Funcion que manda a llamar al componente de personas
 * @returns
 */
var buscarPersona = function() {
	BusquedaPersonaPorCurpCtrl.init("divComponenteBusquedaPersona",1);
	BusquedaPersonaPorCurpCtrl.setOnCloseCallback(funcionRetornoPersona); 
	BusquedaPersonaPorCurpCtrl.abrir();
};

/**
 * Funcion para setear a la persona en el formulario
 * @returns
 */
var funcionRetornoPersona = function() {
	//obtenemos a la persona retornada por el componente
	var personaLocalizada = this;
	//Seteamos los datos en el formulario y obtenemos el id de la persona
	var idPersona = setPersonaCommon(personaLocalizada);	
	
		//Verificamos si la persona ya existia en el Instituto
		if(idPersona !=null) {
			buscarMediosContacto(idPersona);
		} else {//Si la persona no trae id
			//limpiamos los medios de contacto
			limpiarMediosDeContacto();
			//seteamos el domicilio del asegurado
			setDomicilioAsegurado();
		}
		
		if(C_PARENTESCO.val() == PARENTESCO_ENUM.HIJOS) {
			validaEdad();
		}
};

var mostrarBotonConfirmacion = function(mostrar) {
	
	if(mostrar) {
		$("#irAConfirmacion").show();
	} else {
		$("#irAConfirmacion").hide();
	}
};


/**
 * function que se ejecuta cuando el combo de parentesco cambia
 * @returns
 */
var validaCambioSelectParentesco = function () {
	var idParentesco = C_PARENTESCO.val();
	$("#idRazonregistroSeleccionada").val("-1");
	if(idParentesco != "-1") {
		$("#guia").show();
	} else {
		$("#guia").hide();
	}
	
	$("#idParentescoSeleccionado").val(idParentesco);
	$("input[name=varianteRegistro][value=" + 0 + "]").attr('checked', 'checked');
	verOpcionesConcubina();
	verOpcionRecienNacido();
	
	mostrarBotonConfirmacion(true);
	
	$('#fisica\\.fechaNacimiento').datepicker( "destroy" );
	
	
	if($('#conFechaNacimiento').val() == 0){
		
		if(idParentesco == PARENTESCO_ENUM.ASEGURADO || idParentesco == PARENTESCO_ENUM.PENSIONADO){
			activarFechaNacAsegurado();
			C_RAZON_REGISTRO.attr("disabled","disabled");	  
			C_RAZON_REGISTRO.val(RAZON_REGISTRO_ENUM.NORMAL);
			$("#idRazonregistroSeleccionada").val("1");
		}
		
	}
	
	establecerRazonRegistro(idParentesco);
	establecerEstadoCivil(idParentesco);
	validarDomicilioPersonaParentesco($("#fisica\\.idPersona").val());
	
	//Validacion para mostrar a padres
	if(((idParentesco == PARENTESCO_ENUM.PADRES && !IS_PATRON_IMSS) ||
			idParentesco == PARENTESCO_ENUM.CONCUBINARIO )&& !ASEGURADO_CON_DOMICILIO ) {
		limpiarDomicilio();
		mostrarBotonConfirmacion(false);
		mostrarMensajeErrorDomicilio();
		muestraUbicarDomicilio(false);
		return;
	}
};

/**
 * function para llenar los parentescos permitidos para el combo
 * @returns
 */
var llenarComboParentescos = function() {
	var idParentescoSeleccionado = $("#idParentescoSeleccionado").val();
	getParentescosValidos("parentesco\\.idParentesco", idParentescoSeleccionado,funcionOkParentesco, idParentescoSeleccionado);
};

/**
 * function para llenar los parentescos permitidos para el combo
 * @returns
 */
var llenarComboRazonRegistro= function() {
	var idParentescoSeleccionado = $("#idParentescoSeleccionado").val();
	var idRazonSeleccionada = $("#idRazonregistroSeleccionada").val();
	
	getRazonesRegistroPorParentesco("razonRegistro\\.idRazonRegistro", idParentescoSeleccionado,idRazonSeleccionada,funcionOkRazon, null);
};

var funcionOkRazon = function() {
//Se comenta ya que el seteo de la descripcion se hace cuando se envia el formulario
	//setDescripcionCombo("razonRegistro\\.idRazonRegistro","razonRegistro\\.descripcion");
};

var funcionOkParentesco = function(idParentesco) {
	
	if(idParentesco != "" && idParentesco != "-1") {
		
		$("#guia").show();
		establecerEstadoCivil(idParentesco);
		establecerRazonRegistro(idParentesco);
		habilitarDomicilio(idParentesco);
		
		if(isParentescoAsegurado(idParentesco)) {
			$("#localizaPersona").hide();
		}
	} else {
		//llenar raon registro
		llenarComboRazonRegistro();
	}
	
	if($("#conFechaNacimiento").val() == 0){
		if(C_PARENTESCO.val() == PARENTESCO_ENUM.ASEGURADO || C_PARENTESCO.val() == PARENTESCO_ENUM.PENSIONADO){
			activarFechaNacAsegurado();
		}
	}
	
	if(idParentesco == "5" || idParentesco == "6"){
		$('#parentesco\\.idParentesco').attr("disabled","disabled");
	}
};

/**
 * Metodo para setear el estado civil a partir del parentesco
 * @param idParentesco
 * @returns
 */
var establecerEstadoCivil = function(idParentesco) {
	
	var idEstadocivil = C_ESTADO_CIVIL.val();
	
	if(isParentescoAsegurado(idParentesco)) {
		PEMITIR_ELECCION_ESTADO_CIVIL = true;
	} else if(isParentescoPadres(idParentesco)) {//padres
		PEMITIR_ELECCION_ESTADO_CIVIL = true;
	} else if(isParentescoConyuge(idParentesco)) {//cnyuge
		idEstadocivil = ESTADO_CIVIL_ENUM.CASADO;
		PEMITIR_ELECCION_ESTADO_CIVIL = false;
	} else if(isParentescoConcubina(idParentesco)) {//concubina_rio
		idEstadocivil = ESTADO_CIVIL_ENUM.CONCUBINATO;
		PEMITIR_ELECCION_ESTADO_CIVIL = false;
	} else if(isParentescoHijos(idParentesco)) {//hijos
		idEstadocivil = ESTADO_CIVIL_ENUM.SOLTERO;
		PEMITIR_ELECCION_ESTADO_CIVIL = false;
	} 
	
	if(!PEMITIR_ELECCION_ESTADO_CIVIL) {
		C_ESTADO_CIVIL.attr("disabled","disabled");
	} else {
		C_ESTADO_CIVIL.removeAttr("disabled");
	}
	
	C_ESTADO_CIVIL.val(idEstadocivil);
};

var verOpcionesConcubina = function() {
	var ver = $("#idParentescoSeleccionado").val() == PARENTESCO_ENUM.CONCUBINARIO;
	
	if(ver) {
		$("#labelHijosProcreados").show();
		$("#checkHijosProcreados").show();
	} else {
		$("#labelHijosProcreados").hide();
		$("#checkHijosProcreados").hide();
		$("#indHijosProcreados").val(0);
	}
}

var verOpcionesDeRegistro = function(ver,padre) {
	/*if(padre)
	$("input[name=varianteRegistro][value=" + 0 + "]").attr('checked', 'checked');*/
	if(ver) {
		$("#radiosRecienNacido").show();
	} else {
		$("#radiosRecienNacido").hide();
	}
	
	if(padre) {
		$("#spanRecienNacido").hide();
	} else {
		$("#spanRecienNacido").show();
	}
}

var verOpcionRecienNacido = function() {
	var isHijo = $("#idParentescoSeleccionado").val() == PARENTESCO_ENUM.HIJOS;
	var isPadre = $("#idParentescoSeleccionado").val() == PARENTESCO_ENUM.PADRES;
	
	if(isHijo || isPadre) {
		verOpcionesDeRegistro(isHijo || isPadre,isPadre);
	} else {
		verOpcionesDeRegistro(false,false);
	}
	habilitarRecienNacido();
};

/**
 * Function que establece la razon de regidtro
 * @param idParentesco
 * @returns
 */
var establecerRazonRegistro = function(idParentesco) {
	
	var verRecienNacido = false;
	var habiliatarComboRazonRegistro = true;
	var razonRegistroActual = $('#idRazonregistroSeleccionada').val();
	
	C_RAZON_REGISTRO.attr("disabled","disabled");
	
	if(razonRegistroActual == "" || razonRegistroActual == "-1") {
		$("#idRazonregistroSeleccionada").val("1");
	}
	
	if(isParentescoAsegurado(idParentesco)) {
		llenarComboRazonRegistro();
	} else if(isParentescoHijos(idParentesco)){
		$("#idRazonregistroSeleccionada").val(razonRegistroActual);
		
		if(IS_RECIEN_NACIDO){
			C_RAZON_REGISTRO.attr("disabled","disabled");	
		}else{
			C_RAZON_REGISTRO.removeAttr("disabled");
		}
		llenarComboRazonRegistro();
	} else {
		C_RAZON_REGISTRO.removeAttr("disabled");
		llenarComboRazonRegistro();
	} 
	
};

var habilitarCamposDatosBasicos = function(habilitar,envio) {
	
	if(habilitar) {
		F_DATOS_PERSONALES.habilitarContenido();
		
		if(IS_RECIEN_NACIDO) {
			$('#fisica\\.curp').attr("disabled","disabled");
			$('#fisica\\.nombre').attr("disabled","disabled");
		}		
		
		if(envio) {
			C_PARENTESCO.removeAttr("disabled");
			C_ESTADO_CIVIL.removeAttr("disabled");
			C_RAZON_REGISTRO.removeAttr("disabled");
		}
	} else {
		
		if(!IS_RECIEN_NACIDO){
			F_DATOS_PERSONALES.deshabilitarContenido();
//			$('#fisica\\.fechaNacimiento').datepicker( "option", "showOn", "focus" );
		} else {
			$('#fisica\\.curp').attr("disabled","disabled");
			$('#fisica\\.nombre').attr("disabled","disabled");
		}
		
		$('#fisica\\.curp').attr("disabled","disabled");
		
		//$('#parentesco\\.idParentesco').attr("disabled","disabled");
		if(!PEMITIR_ELECCION_ESTADO_CIVIL) {
			C_ESTADO_CIVIL.attr("disabled","disabled");
		}
		
		//C_RAZON_REGISTRO.attr("disabled","disabled");
	}
	
};

var isParentescoHijos = function(idParentesco) {
	var isHijo = idParentesco == PARENTESCO_ENUM.HIJOS;
	
	return isHijo;
};

var isParentescoConyuge = function(idParentesco) {
	var isConyuge = idParentesco == PARENTESCO_ENUM.CONYUGE;
	
	return isConyuge;
};

var isParentescoAsegurado = function(idParentesco) {
	var isAsegurado = idParentesco == PARENTESCO_ENUM.ASEGURADO || idParentesco == PARENTESCO_ENUM.PENSIONADO;
	
	return isAsegurado;
};

var isParentescoPadres = function (idParentesco) {
	var isPadre = idParentesco == PARENTESCO_ENUM.PADRES || idParentesco == PARENTESCO_ENUM.MADRES;
	
	return isPadre;
};

var isParentescoConcubina  = function(idParentesco) {
	var isConcubina = idParentesco == PARENTESCO_ENUM.CONCUBINARIO || idParentesco == PARENTESCO_ENUM.CONCUBINA;
	
	return isConcubina;
};

/**
 * 
 * @returns
 */
var getParentescoSeleccionadoEnCombo = function() {
	var idParentesco = $('#parentesco\\.idParentesco').val();	
	
	return idParentesco;
};

/**
 * Metodo para mostrar o no el boton de ubicar domicilio y un mensaje
 * @param mostrar
 * @param mostrarMensajeDomicilio
 * @param mensaje
 * @returns
 */
var muestraDomicilioYMensaje = function(mostrar,mostrarMensajeDomicilio,mensaje) {
	
	//mostramos o no el boton de ubicar domicilio
	muestraUbicarDomicilio(mostrar);
	//Checamos si requerimos mostrar mensaje de domicilio
	if(mostrarMensajeDomicilio) {
		muestraMensajeEnDiv("mensajesDomicilio",mensaje);
	}
};

/**
 * Metodo para mostrar mensaje en div
 * @param div
 * @param mensaje
 * @returns
 */
var muestraMensajeEnDiv = function(div,mensaje) {
	$("#"+div).html(mensaje);
};

var limpiarDatosPersonales = function(){	
	
	$('#fisica\\.idPersona').val('');		
	$('#fisica\\.nombre').val('');		
	$('#fisica\\.primerApellido').val('');		
	$('#fisica\\.segundoApellido').val('');		
	$('#fisica\\.curp').val('');		
	$('#fisica\\.lugarNacimiento\\.clave')[0].selectedIndex=0; 		
	$('#fisica\\.sexo\\.idSexo')[0].selectedIndex=0;		
	$('#fisica\\.fechaNacimiento').val('');				
		
};

/**
 * Function para limpiar los medios de contacto
 */
var limpiarMediosDeContacto = function() {
	//limpiamos el telefono fijo
	$("#fisica\\.telefonoFijo\\.clave").val('');
	$("#fisica\\.telefonoFijo\\.claveLada").val('');
	//limpiamos el correo electrono
	$("#fisica\\.correoElectronico\\.clave").val('');
	$("#fisica\\.correoElectronico\\.correo").val('');
};

function validaEdad() {
	
	var fecha = $("#fisica\\.fechaNacimiento").val();
	var razonRegistro = "-1";
	var razonRegistroActual = $('#idRazonregistroSeleccionada').val();
	var ponerRazonRegistro = true;
	
	if(razonRegistroActual != "" && razonRegistroActual != "-1") {
		ponerRazonRegistro = false;
	}
	
	var habilitaRazon = false;
	var idParentesco = $("#parentesco\\.idParentesco").val();
	
	if($.trim(fecha) != "") {
		
		
		var edad = calcularEdad(fecha);
		
		if(isParentescoAsegurado(idParentesco)) {
			habilitaRazon = false;
		}else if(isParentescoHijos(idParentesco)) {
			if(edad <= 1){
				if($('#recienNacidoCheck').is(":checked")){
					razonRegistro = RAZON_REGISTRO_ENUM.RECIEN_NACIDO;
				}else{
					razonRegistro = RAZON_REGISTRO_ENUM.HASTA_16;
				}
			}
		
			if(edad > 1 && edad <= 16){		
				razonRegistro = RAZON_REGISTRO_ENUM.HASTA_16;
			}
		
			if(edad > 16 && edad <= 25){
				var sexoHijo = $("#fisica\\.sexo\\.idSexo").val();
				if(IS_PATRON_IMSS) {
					if(sexoHijo == SEXO_ENUM.MUJER) {
						razonRegistro = RAZON_REGISTRO_ENUM.NORMAL;
					} else {
						razonRegistro = edad < 18 ? RAZON_REGISTRO_ENUM.NORMAL : RAZON_REGISTRO_ENUM.HASTA_25;
						
						if(razonRegistro == RAZON_REGISTRO_ENUM.HASTA_25) {
							habilitaRazon = true;
						}
					}
				} else {
					razonRegistro = RAZON_REGISTRO_ENUM.HASTA_25;
					habilitaRazon = true;
				} 
			}
		
			if(edad > 25){			
				razonRegistro = RAZON_REGISTRO_ENUM.MAYOR_25;
				habilitaRazon = true;
			}
		} else {
			if($('#recienNacidoCheck').is(":checked")){
				razonRegistro = RAZON_REGISTRO_ENUM.RECIEN_NACIDO;
			} else {
				razonRegistro = 1;
			}
		}
		
		if(habilitaRazon) {
			C_RAZON_REGISTRO.removeAttr("disabled");
		} else {
			C_RAZON_REGISTRO.attr("disabled","disabled");
		}
		
	} else {
		if(isParentescoHijos(idParentesco)) {
			if($('#recienNacidoCheck').is(":checked")){
				razonRegistro = RAZON_REGISTRO_ENUM.RECIEN_NACIDO;
				ponerRazonRegistro = true;
			}else{
				razonRegistro = -1;
				ponerRazonRegistro = true;
			}
		} else {
			razonRegistro = RAZON_REGISTRO_ENUM.NORMAL;
			ponerRazonRegistro = true;
		}
		
	}
	
	if(ponerRazonRegistro) {
		$('#idRazonregistroSeleccionada').val(razonRegistro);
	}
	
	llenarComboRazonRegistro();
	
	
}

function buscarMediosContacto(idPersona) {
	var oForm = {
		'idPersona' : idPersona
	};
	
	var url = '/${mvn.web.app.root}/derechohabientes/getMediosContacto';
	
	$.blockUI();
	$.postJSON(url, oForm, function(fisica) {
		
		if(fisica.telefonoFijo != undefined && fisica.telefonoFijo != null) {
			$("#fisica\\.telefonoFijo\\.clave").val(fisica.telefonoFijo.clave);
			$("#fisica\\.telefonoFijo\\.claveLada").val(fisica.telefonoFijo.claveLada);
		} else {
			$("#fisica\\.telefonoFijo\\.clave").val('');
			$("#fisica\\.telefonoFijo\\.claveLada").val('');
		}

		if(fisica.correoElectronico != undefined && fisica.correoElectronico != null) {
			$("#fisica\\.correoElectronico\\.clave").val(fisica.correoElectronico.clave);
			$("#fisica\\.correoElectronico\\.correo").val(fisica.correoElectronico.correo);
		} else {
			$("#fisica\\.correoElectronico\\.clave").val('');
			$("#fisica\\.correoElectronico\\.correo").val('');
		}
		
		$.unblockUI();
		validarDomicilioPersonaParentesco(idPersona);
	}).error(function(data){
		
	});
}

function habilitarDomicilio(idParentesco) {
	var habilitar = false;
	
	if((!isParentescoConcubina(idParentesco) && !isParentescoPadres(idParentesco)) || (isParentescoPadres(idParentesco) && IS_PATRON_IMSS)){
		habilitar = true;
	}
	
	muestraUbicarDomicilio(habilitar);
}
function validarDomicilioPersonaParentesco(idPersona) {
	//console.log("entro a validar dom");
	if(idPersona == "") {
		setDomicilioAsegurado();
	} else {
		var parentesco = C_PARENTESCO.val();
		var isAsegurado = isParentescoAsegurado(parentesco);
		
		var oForm = {
			'fisica': {'idPersona' : idPersona},
			'parentesco' : {'idParentesco' : parentesco}
		};
	
		var url = '/${mvn.web.app.root}/tramite/registro/validacionesDomicilio';
	
		//console.log("entro a validar el domicilio")
		$.blockUI();
		$.postJSON(url, oForm, function(valDomicilio) {
			//Verificamos si existe error
			var error = valDomicilio.error;
			
			//seteamos el domicilio, indicamos que si el domicilio es nulo que lo limpie
			setearDomicilio(valDomicilio.personaDomicilio.domicilio,true)
			
			//Validamos si no hubo error en las validaciones de domicilio
			if(error) {
				if(valDomicilio.mensajeError != "0") {
					//Si ocurrio un error ocultamos el boton de enviar
					$("#irAConfirmacion").hide();
					//mostramos el error
					$("#mensajesDomicilio").html(valDomicilio.mensajeError);
				} else {
					//Si ocurrio un error ocultamos el boton de enviar
					$("#irAConfirmacion").hide();
					//mostramos el error
					$("#mensajesDomicilio").html("Ocurri&oacute; un error al consultar el domicilio de la persona");
				}
			} else {
				$("#mensajeErrorDomicilio").hide();
				//Validamos si permite ubicar domicilio de la persona
				var permiteUbicarDomicilio = valDomicilio.permiteUbicarDomcilio;

				//Si si permite ubicarlo mostramos el boton
				if(permiteUbicarDomicilio) {
					
					mensajeDom = "De clic en el bot&oacute;n ubicar domicilio en caso de que desee modificarlo. <strong>Nota:</strong> El domicilio" +
					" deber&aacute; estar dentro de la circunscripci&oacute;n de la Unidad Medica Familiar del usuario firmado.";

					$("#mensajesDomicilio").html(mensajeDom);
					muestraUbicarDomicilio(true);
				} else {
					muestraUbicarDomicilio(false);
					$("#mensajeRegi").show();
					$("#mensajesDomicilio").html(valDomicilio.mensajeDomicilio);
				}
				
				$("#irAConfirmacion").show();
			}
			
			$.unblockUI();
		});
	}
}

/**
 * Funcion para establecer el domicili del asegurado o pensionado
 */
function setDomicilioAsegurado() {
	
	var url = '/${mvn.web.app.root}/tramite/registro/getDomicilioAsegurado';
	var mostrarBoton = false;
	$.blockUI();//obtenemod el parentesco
	var idParentesco = C_PARENTESCO.val();	
	
	$.postJSON(url, null, function(domicilio) {
		//seteamos el domicilio, indicamos que si el domicilio es nulo que lo limpie
		setearDomicilio(domicilio,true);
		
		
		if(idParentesco != "" && idParentesco != "-1") {
			var isAsegurado = isParentescoAsegurado(idParentesco);
			var isPadre = isParentescoPadres(idParentesco);
			var isConcubina = isParentescoConcubina(idParentesco);
			
			var mensaje = "";
			
			if((!isPadre && !isConcubina) || (isPadre && IS_PATRON_IMSS) || isAsegurado) {
				mensaje = "De clic en el bot&oacute;n ubicar domicilio en caso de que desee modificarlo. <strong>Nota:</strong> El domicilio" +
				" debera estar dentro de la circunscripci&oacute;n de la Unidad Medica Familiar del usuario firmado.";
				
				mostrarBoton = true;
			} else {
				mensaje = "El parentesco a registrar no permite que la persona tenga un domicilio distinto al del asegurado / pensionado." +
						" De clic en <strong>Aceptar</strong> para continuar.";	
				
				mostrarBoton = false;
			}

			muestraDomicilioYMensaje(mostrarBoton,true,mensaje);
		}
		
		$.unblockUI();
	});
};

function convertirAFechaMesAnio(mes, anio) {
	var date = new Date();
	  date.setMonth(mes-1); //en javascript los meses van de 0 a 11
	  date.setDate(1);
	  date.setYear(anio);
	  
	  return date;
}

function dias(mes, anno) {
	anno = parseInt(anno,10);
	mes = parseInt(mes,10); 
    switch (mes) {
	    case 1 : case 3 : case 5 : case 7 : case 8 : case 10 : case 12 : return 31;
		case 2 : return (anno % 4 == 0) ? 29 : 28;
	}
    
	return 30;
 }

function activarFechaNacAsegurado(){
	var mesNacAs = $('#fisica\\.mesRegistroNac').val();
	var aniosNacAs = $('#fisica\\.anioRegistroNac').val();
	var fechaNacimiento = convertirAFechaMesAnio(mesNacAs,aniosNacAs);
	
	var ultimoDia = dias(""+mesNacAs, ""+aniosNacAs);
	var ultimo = new Date();
	var minimo = new Date();
	
	minimo.setFullYear(fechaNacimiento.getFullYear(),fechaNacimiento.getMonth(),1);
	ultimo.setFullYear(fechaNacimiento.getFullYear(),fechaNacimiento.getMonth(),ultimoDia);
	
	$('#fisica\\.fechaNacimiento').datepicker(
				{
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true,
				minDate:minimo,
	            maxDate:ultimo
	});
	$('#fisica\\.fechaNacimiento').removeAttr("disabled");
}

var mostrarMensajeRegresarAGrupo = function() {
	
	var botones = {
		"Si" : function() {	  
			$(this).dialog("close");
			$.blockUI();
        	location.href = "/${mvn.web.app.root}/inicio/grupoFamiliar";
        },
        "No" : function() {$(this).dialog("close");}
	};
	
	crearDialogoPantalla('Confirmaci&oacute;n', '\u00BFEsta seguro que desea salir? Se perder\u00E1 la informaci\u00F3n ingresada.', botones);
};

var mostrarMensajeErrorDomicilio = function(){
	var parentescos = "concubina(rio)";
	parentescos += (!IS_PATRON_IMSS ? ", padre o madre" : "");
	var mensajeAviso ='Para poder registrar beneficiarios con parentesco <strong>'+parentescos+'</strong> es necesario que' +
	' el asegurado o pensionado cuente con domicilio, para poder actualizar el domicilio es necesario que en el men&uacute;' +
	' de tr&aacute;mites se elija la opci&oacute;n de <strong>"Correcci&oacute;n de datos"</strong> y se asigne un domicilio' + 
	' al asegurado o pensionado, una vez que se tenga el domicilio podr&aacute; registrar a los beneficiarios con los parentescos mencionados';
	
	mostrarMensajeInfoRegistro(mensajeAviso)
}

var mostrarMensajeErrorCaptura = function(){
	mostrarMensajeErrorRegistro("Existen errores en el formulario, verifique sus datos.");
}

var mostrarMensajeInfoRegistro = function(mensaje) {
	var mensajeAviso = '<div class="ui-widget">' +
	'<div class="ui-state-highlight ui-corner-all" style="padding: 0 .7em;">'+
	'<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>' + mensaje+'</p></div></div>';
	
	var botones= {"Cerrar": function() {
			cierraDialogo($(this));
		}
	}
	
	crearDialogoPantalla("Aviso", mensajeAviso, botones)
}

var mostrarMensajeErrorRegistro = function(mensaje) {
	
	var mensajeErrorReg = '<div class="ui-widget">' +
	'<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">'+
	'<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>' +
	'<strong>'+mensaje+'</strong></p></div></div>';
	
	var botones = {"Cerrar": function() {
		cierraDialogo($(this));
	}};
	
	crearDialogoPantalla("Error", mensajeErrorReg, botones)
	
}

//metodo para crear un dialogo
var crearDialogoPantalla = function(titulo, mensaje, botones) {
	$decision = $('<div></div');
	$decision.dialog({
		autoOpen : false,
		resizable : false,
		width: 500,
		height : 'auto',
		title : titulo,
		modal : true,
		buttons : botones
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.html(mensaje);
	$decision.dialog('open')
}

var cierraDialogo = function($dialogo){
	$dialogo.dialog('close'); 
	$dialogo.dialog('destroy');
	$dialogo.html('');
}
