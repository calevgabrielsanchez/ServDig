var asentamientosUbicados;
C_TIPO_CREDENCIAL = null; //objeto que contendra el combo de tipo de credencial
D_DATOS_ANIO = null;
D_DATOS_IFE = null; 
D_ERROR_IFE = null;
L_CODIGO = null;

MENSAJE_ERROR_OBLIGARORIO = "Obligatorio";
MENSAJE_ERROR_9_CARACTERES = "Debe ser de 9 d\u00edgitos";
MENSAJE_ERROR_13_CARACTERES = "Debe ser de 13 d\u00edgitos";
MENSAJE_ERROR_18_CARACTERES = "Debe ser de 18 d\u00edgitos";
MENSAJE_ERROR_NUMERICO = "Debe se n\u00famerico";
MENSAJE_ERROR_ALFANUM = "Debe ser alfanum\u00e9rico";
MENSAJE_ERROR_4_CARACTERES = "Debe ser de 4 d\u00edgitos";
MENSAJE_ERROR_2_CARACTERES = "Debe ser de 2 d\u00edgitos";
MENSAJE_ERROR_CREDENCIAL = "De acuerdo a su tipo de credencial y al a&ntilde;o de expedici&oacute;n, su identificaci&oacute;n ya no es valida.";
MENSAJE_ERROR_ANIO_EXPEDICION = "El a&ntilde;o de expedici&oacute;n no concuerda con el tipo de credencial.";
MENSAJE_ERROR_CVE_ELECTOR = "La clave de elector no coincide con los datos de la persona";
MENSAJE_LONGITUD_ANIO = "El a&ntilde;o debe ser de 4 posiciones";
MENSAJE_ERROR_ANIO_ACTUAL = "El a&ntilde;o de expedici&oacute;n no puede ser mayor al a&ntilde;o en curso";

ETIQUETA_CODIGO_TIPO_ABC = "N\u00famero (OCR):";
ETIQUETA_CODIGO_TIPO_DE = "C&oacute;digo de identificaci&oacute;n de credencial:";
	
$(document).ready(function() {  
	//llenamos los objetos iniciales
	llenarObjetosFormularios();
	//Establecemos el evento del combo de credencial
	C_TIPO_CREDENCIAL.change(eventoCambioTipoCredencial);
	
	//Todos los datepicker se validan cuando se cierran
	$.datepicker.setDefaults({
		onClose: function(){
			$(this).valid();
		}
	});
	
	//agregamos los validadores adicionales
	agregarValidadoresAdicionales();
	
	//Establecemos el validador por default
	setValidadorDefault();
	
	validateForm.allowOnlyRegularExpression( $('.entero_15'),regularExpression.entero_15);
	validateForm.allowOnlyRegularExpression( $('.entero_4'),regularExpression.entero_4);

}); 

function agregarValidadoresAdicionales() {
	//agregamos el validador alfanumerico
	$.validator.addMethod("alphanumeric", function(value, element) { 
		return this.optional(element) || /^[a-z0-9\-]+$/i.test(value); 
	}, MENSAJE_ERROR_ALFANUM); 

	//agregamos el validador de vigencia de acuerdo al tipo de credencial y su anio de expedicion
	$.validator.addMethod("validaVigenciaCredencial", function(value, element) { 
		return validadorVigenciaTipoCredencial(value); 
	}, MENSAJE_ERROR_CREDENCIAL); 
	
	//agregamos el validador de anio de expedicion
	$.validator.addMethod("validaAnioExpedicion", function(value, element) { 
		return validarAnioMinimo(value); 
	}, MENSAJE_ERROR_ANIO_EXPEDICION); 
	
	//agregamos el validador de vigencia de acuerdo al tipo de credencial y su anio de expedicion
	$.validator.addMethod("validaClaveElector", function(value, element) { 
		return validarClaveElector(value); 
	}, MENSAJE_ERROR_CVE_ELECTOR);
	
	//agregamos el validador de vigencia de acuerdo al tipo de credencial y su anio de expedicion
	$.validator.addMethod("validaAnioActual", function(value, element) { 
		return validarAnioMaximo(value); 
	}, MENSAJE_ERROR_ANIO_ACTUAL);
}

function validarAnioMaximo(value) {
	if(value.length == 4) {
		var fechaActual = new Date();
		var anioActual = fechaActual.getFullYear();
		
		if(parseInt(value) > anioActual) {
			return false;
		}
		
		return true;
	}
}

function validarClaveElector(value) {
	var validacion = 0;
	var fisica = parent.WizardCapturaDocumentosProbatoriosCtrl.getPersona();
	
	if(fisica != null) {
		if(value != null && value != "" && value.length == 18) {
			var posicion_1 = value.charAt(0);
			var posicion_2 = value.charAt(1);
			var posicion_3 = value.charAt(2);
			var posicion_4 = value.charAt(3);
			var posicion_5 = value.charAt(4);
			var posicion_6 = value.charAt(5);
			var posicion_7Y8 = value.charAt(6) + "" + value.charAt(7);
			var posicion_9Y10 = value.charAt(8) + "" + value.charAt(9);
			var posicion_11Y12 = value.charAt(10) + "" + value.charAt(11);
			var posicion_13Y14 = value.charAt(12) + "" + value.charAt(14);
			var posicion_15 = value.charAt(14);
			
			
			if(posicion_1 != getLetra(fisica.primerApellido,1)) {
				return false;
			}
			
			if(posicion_2 != getLetra(fisica.primerApellido, 2)) {
				return false;
			}
			
			if(posicion_3 != getLetra(fisica.segundoApellido, 1)) {
				return false;
			}
			
			if(posicion_4 != getLetra(fisica.segundoApellido, 2)) {
				return false;
			}
			
			if(posicion_5 != getLetra(fisica.nombre, 1)) {
				return false;
			}
			
			if(posicion_6 != getLetra(fisica.nombre, 2)) {
				return false;
			}
			
			var fechaSeparado = fisica.fechaNacimiento.split("/");
			
			if(posicion_7Y8 != fechaSeparado[2].subString(2,3)) {
				return false;
			}
			
			if(posicion_9Y10 != fechaSeparado[1]) {
				return false;
			}
			
			if(posicion_11Y12 != fechaSeparado[0]) {
				return false;
			}
			
			if(posicion_15.toUpperCase() != fisica.sexo.descripcion.toUpperCase().charAt(1) ) {
				return false;
			}
			
			
			return true;
		}
	} 
	
	return false;
}

function getLetra(cadena,tipo) {
	
	if(cadena != null && cadena != "") {
		//quiere decir que es la primera letra
		if(tipo == 1) {
			return cadena.charAt(0).toUpperCase();
		} else {
			
			if(cadena.length > 1) {
				for(var i=1; i < cadena.length ; i++) {
					var l = cadena.charAt(i).toUpperCase();
					if(l != "A" && l != "E" && l != "I" && l != "O" && l != "U") {
						return l;
					}
				}
				
				return cadena.charAt(1);
			}
		}
	}
	
}

function validarAnioMinimo(value) {
	if(value != null && value != "") {
		//pasamos a entero el anio
		var anioExpedicion = parseInt(value);
		//Validamos que existe el campo en el formulario
		if($("#tipoCredencial").length > 0) {
			var tipoCredencial  = $("#tipoCredencial").val();
			
			if(tipoCredencial == "") {
				return true;
			} else if(tipoCredencial == "A" ) {
				return false;
			} else if(tipoCredencial == "B") {
				if(anioExpedicion >= 2002 && anioExpedicion <= 2008) {
					return true;
				} else {
					return false;
				}
			} else if(tipoCredencial == "C"){
				if(anioExpedicion >= 2008 && anioExpedicion <= 2013) {
					return true;
				} else {
					return false;
				}
			} else if(tipoCredencial == "D"){
				if(anioExpedicion >= 2013) {
					return true;
				} else {
					return false;
				}
			} else if(tipoCredencial == "E") {
				if(anioExpedicion >= 2013) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			return true;
		}
	}
}
/**
 * Function para validar si de acuerdo al tipo de credencial y el anio de expedicion la credencial aun esta vigente
 * @param value
 * @returns {Boolean}
 */
function validadorVigenciaTipoCredencial(value) {
	if(value != null && value != "") {
		//pasamos a entero el anio
		var anioExpedicion = parseInt(value);
		 var date = new Date();
		 var anioActual = date.getFullYear(); // se obtiene el valor del anio actual
		//Validamos que existe el campo en el formulario
		if($("#tipoCredencial").length > 0) {
			var tipoCredencial  = $("#tipoCredencial").val();
			
			if(tipoCredencial == "") {
				return true;
			} else if(tipoCredencial == "A" ) {
				return false;
			} else if(tipoCredencial == "B") {
				if(anioExpedicion < 2003) {
					return false;
				} else {
					if(anioActual > 2018) {
						return false;
					} else {
						return true;
					}
				}
			} else if(tipoCredencial == "C" || tipoCredencial == "D" || tipoCredencial == "E") {
				
				 var anioFinVigencia = anioExpedicion + 10; //se le suman 10 anios a la fecha de registro ya que esa es la vigencia
				
				 //si la fecha de fin de vigencia es mayor al actual, la fecha es valida
				 if(anioFinVigencia > anioActual) {
					 return true;
				 } else {
					 return false;
				 }
				 
			}
		} else {
			return true;
		}
	}
}

function eventoCambioTipoCredencial() {
	var tipoCredencial = C_TIPO_CREDENCIAL.val();
	var mostrarDatosAnio = false;
	var mostrarDatosComplementarios = false;
	limpiarDatosFormulario();
	
	if(tipoCredencial == "") {
		$('#formdocument').removeData('validator');
		D_DATOS_ANIO.hide();
		D_DATOS_IFE.hide();
		setValidadorDefault();
	} else {
		mostrarDatosAnio = true;
		mostrarDatosComplementarios = true;
		
		if(tipoCredencial == "A"){
			
			$('#formdocument').removeData('validator');
			setValidadorDefault();
			mostrarErrorIfe(true,"<strong>El tipo de credencial A no es v&aacute;lida como identificaci&oacute;n.</strong>");
			C_TIPO_CREDENCIAL.val("");
			mostrarDatosAnio = false;
			mostrarDatosComplementarios = false;
			
		}else if(tipoCredencial == "B" || tipoCredencial == "C" ) {
			
			mostrarErrorIfe(false,null);
			L_CODIGO.html(ETIQUETA_CODIGO_TIPO_ABC);
			setValidadorTipoABC();
			
		} else {
			
			mostrarErrorIfe(false,null);
			L_CODIGO.html(ETIQUETA_CODIGO_TIPO_DE);
			mostrarDatosComplementarios = false;
			setValidatosTipoDE();
			
		}
	}
	
	if(mostrarDatosAnio) {
		D_DATOS_ANIO.show();
	} else {
		D_DATOS_ANIO.hide();
	}
	
	if(mostrarDatosComplementarios) {
		D_DATOS_IFE.show();
	} else {
		D_DATOS_IFE.hide();
	}
}

function mostrarErrorIfe(mostrar, mensaje) {
	if(mostrar) {
		$("#mensajeErrorIfe").html(mensaje);
		D_ERROR_IFE.show();
	} else {
		$("#mensajeErrorIfe").html("");
		D_ERROR_IFE.hide();
	}
}

function setValidadorDefault() {
	//$("#formdocument").rules("remove",false);
	$("#codigoSeguridad").removeAttr('maxlength');
	$("#formdocument").validate({ 
		rules: { 
			tipoCredencial: {
				required: true
			}
		}, 
		errorLabelContainer: "#warning", 
		messages: { 
			tipoCredencial: {
				required: MENSAJE_ERROR_OBLIGARORIO
			}
		} 
	}); 
}

/**
 * funcion para setear el validador para los tipos de credenciales a, b y c
 */
function setValidadorTipoABC() {
	$('#formdocument').removeData('validator');
	$("#codigoSeguridad").removeAttr('maxlength');
	$("#codigoSeguridad").attr("maxlength",13);
	$("#formdocument").validate({ 
		rules: { 
			tipoCredencial: {
				required: true
			},
			anioRegistro: {
				required:true,
				number:true,
				minlength : 4,
				maxlength : 4,
				validaAnioExpedicion: true,
				validaVigenciaCredencial: true
			}, 
			claveElector: {
				required:true,
				maxlength:18,
				alphanumeric : true
			},
			emision: {
				required:true,
				number:true,
				minlength : 2,
				maxlength : 2
			},			
			codigoSeguridad: {
				required:true,
				number : true,
				minlength : 13,
				maxlength:13	
			}
		}, 
		errorLabelContainer: "#warning", 
		messages: { 
			tipoCredencial: {
				required: MENSAJE_ERROR_OBLIGARORIO
			},
			anioRegistro: {
				number:MENSAJE_ERROR_NUMERICO,
				required:MENSAJE_ERROR_OBLIGARORIO, 
				minlength : MENSAJE_LONGITUD_ANIO,
				maxlength : MENSAJE_LONGITUD_ANIO
			},
			claveElector: {
				required:MENSAJE_ERROR_OBLIGARORIO, 
				maxlength:MENSAJE_ERROR_18_CARACTERES, 
				alphanumeric : MENSAJE_ERROR_ALFANUM
			},
			emision: {
				required: MENSAJE_ERROR_OBLIGARORIO, 
				number: MENSAJE_ERROR_NUMERICO, 
				minlength : MENSAJE_ERROR_2_CARACTERES,
				maxlength: MENSAJE_ERROR_2_CARACTERES
			},
			codigoSeguridad: {
				number: MENSAJE_ERROR_NUMERICO,
				required: MENSAJE_ERROR_OBLIGARORIO, 
				minlength: MENSAJE_ERROR_13_CARACTERES, 
				maxlength: MENSAJE_ERROR_13_CARACTERES
			}
		} 
	}); 
	
	$("#formdocument").valid();
}

function limpiarDatosFormulario() {
	$("#anioRegistro").val("");
	$("#codigoSeguridad").val("");
	$("#emision").val("");
	$("#claveElector").val("");
}

/**
 * Funcion para establecer el validador de formulario par alos tipos de credenciales D y E
 */
function setValidatosTipoDE() {
	$('#formdocument').removeData('validator');
	$("#codigoSeguridad").removeAttr('maxlength');
	$("#codigoSeguridad").attr("maxlength",9);
	$("#formdocument").validate({ 
		rules: { 
			tipoCredencial: {
				required: true
			},
			anioRegistro: {
				required:true,
				number:true,
				minlength : 4,
				maxlength : 4,
				validaAnioActual: true,
				validaAnioExpedicion: true,
				validaVigenciaCredencial: true
			}, 
			codigoSeguridad: {
				required:true,
				number : true,
				minlength: 9,
				maxlength:9
			}
		}, 
		errorLabelContainer: "#warning", 
		messages: { 
			tipoCredencial: {
				required: MENSAJE_ERROR_OBLIGARORIO
			},
			anioRegistro: {
				required:MENSAJE_ERROR_OBLIGARORIO, 
				number:MENSAJE_ERROR_NUMERICO,
				minlength : MENSAJE_LONGITUD_ANIO,
				maxlength : MENSAJE_LONGITUD_ANIO
			},
			codigoSeguridad: {
				required:MENSAJE_ERROR_OBLIGARORIO, 
				minlength:MENSAJE_ERROR_9_CARACTERES, 
				maxlength:MENSAJE_ERROR_9_CARACTERES, 
				number:MENSAJE_ERROR_NUMERICO
			}
		} 
	}); 
	
	$("#formdocument").valid();
}
/**
 * Metodo para llenar los objetos que componen el formulario (combos, inputs ...)
 */
function llenarObjetosFormularios() {
	
	L_CODIGO = $("#labelCodigo");
	C_TIPO_CREDENCIAL = $("#tipoCredencial");
	D_ERROR_IFE = $("#divErrorIfe");
	D_DATOS_ANIO = $("#camposAnioNumero");
	D_DATOS_IFE = $("#camposComplementarios");
}