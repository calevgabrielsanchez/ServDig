/**
 * 
 */

IS_ASEGURADO = false;
PEMITIR_ELECCION_ESTADO_CIVIL = false;
IS_PATRON_IMSS = false;
IS_RECIEN_NACIDO = false;
NOMBRE_RECIEN_NACIDO = 'RECIEN NACIDO';
CONTEXT_PATH_APLICACION = '/${mvn.web.app.root}';

$(document).ready(
	function () {
		
		IS_PATRON_IMSS = $("#isPatronImss").val() == 1 ? true: false;
		var idParentescoDer = $("#parentescoDelRegistro").val();
		
		IS_ASEGURADO = idParentescoDer == PARENTESCO_ENUM.ASEGURADO || idParentescoDer == PARENTESCO_ENUM.PENSIONADO;
		PEMITIR_ELECCION_ESTADO_CIVIL = IS_ASEGURADO || idParentescoDer == PARENTESCO_ENUM.PADRES;
		
		razon_registro = $("#razonDelRegistro").val();
		
		habilitarDesabilitarCamposDatosBasicos(false,false);
		
		if(IS_ASEGURADO) {
			$("#wrapperCurp").hide();
		}
		
		if(razon_registro == RAZON_REGISTRO_ENUM.RECIEN_NACIDO) {
			 habilitarCamposRecienNacido(true, false, false);
		} else {
			 habilitarCamposRecienNacido(false, false, false);
		}
		
		$("#buscarXCurp").click(function() {
			fnHideErrores("#tablaDatosPersonales");
			verificarErrores();
			parent.BusquedaPersonaPorCurpCtrl.init("buscarPersonaPorCurpDiv",2);
			parent.BusquedaPersonaPorCurpCtrl.setOnCloseCallback(callBackDatosPersona); 
			parent.BusquedaPersonaPorCurpCtrl.abrir();
		});
		
		$("#continuarADomicilio").click(function() {
			validarDatosPersonales();
		});
		
		$("#fisica\\.fechaNacimiento").datepicker({
			showOn: 'focus',
			dateFormat: 'dd/mm/yy',
			changeYear: true,
			changeMonth: true,
			maxDate: new Date(),
			yearRange : '-112:+0',
			regional:'es'
			}
		);	
		
		$("#hijosProcreadosCheck").change(setearValorHijosProcreados);
		
		verificarErrores();
		setEventosChecarError("formRegistro",verificarErrores);
	}
);

function habilitarCamposRecienNacido(indicador, indLimpiarForm, llenarDatos) {
	var razonRegistro = $("#razonDelRegistro").val();
	fnHideErrores("form#formRegistro");
	verificarErrores()
	IS_RECIEN_NACIDO = indicador;
	
	if(indLimpiarForm) {
		limpiarFormulario();
		limpiarMedios();
	}
	
	if(indicador) {
		$("#indCurpObligatoria").hide();
		$("#rnSi").addClass('active');
		$("#rnNo").removeClass('active');
		$("#wrapperCurp").hide();
		
		$("#fisica\\.fechaNacimiento").datepicker( "option", "showOn", "both" );
		razonRegistro = RAZON_REGISTRO_ENUM.RECIEN_NACIDO;
		habilitarDesabilitarCamposDatosBasicos(true, false);
		
		if(IS_RECIEN_NACIDO) {
			$('#fisica\\.nombre').attr("disabled","disabled");
		}
		
		if(llenarDatos) {
			$('#fisica\\.nombre').val(NOMBRE_RECIEN_NACIDO);
			
			var apellidoAsegurado = $("#datosAsegurado\\.primerApellido").val();
			var idSexoAsegurado = $("#datosAsegurado\\.sexo\\.idSexo").val();
			
			if(idSexoAsegurado == SEXO_ENUM.MUJER) {
				$("#fisica\\.segundoApellido").val(apellidoAsegurado);
			} else {
				$("#fisica\\.primerApellido").val(apellidoAsegurado);
			}
		}
	} else {
		$("#fisica\\.fechaNacimiento").datepicker( "option", "showOn", "focus" );
		$("#indCurpObligatoria").show();
		habilitarDesabilitarCamposDatosBasicos(false, false);
		$("#rnNo").addClass('active');
		$("#rnSi").removeClass('active');
		if(!IS_ASEGURADO)
			$("#wrapperCurp").show();
	}
	
	$('#razonRegistro\\.idRazonRegistro').val(razonRegistro);
}

function validarDatosPersonales() {
	
	$("form#formRegistro").habilitarContenido(false);
	var oForm = $("form#formRegistro").toObject();
	var url = CONTEXT_PATH_APLICACION + '/wizard/registro/validaciones';
	fnHideErrores("form#formRegistro");
	verificarErrores()
	$.blockUI();
	$.postJSON(url, oForm, function(data2) {
		
		if(!data2.correcto) {
			$.unblockUI();
			habilitarDesabilitarCamposDatosBasicos(false,false);
			habilitarCamposRecienNacido(IS_RECIEN_NACIDO, false, false);
			mostrarMensajeError(data2.mensaje);
		} else {
			$.blockUI();
			
			$("#formRegistro").attr("action",CONTEXT_PATH_APLICACION+ "/wizard/registro/siguiente");
			$("#formRegistro").submit();
		}
	}).error(function(data){
		habilitarDesabilitarCamposDatosBasicos(false,false);
		habilitarCamposRecienNacido(IS_RECIEN_NACIDO, false, false);
		
		$.unblockUI();
		fnProcesarErrores(data, "form#formRegistro");
		verificarErrores();
	});
}

var callBackDatosPersona = function(){
    
	var p = this;
	
	if(!jQuery.isEmptyObject(p)){
		setPersonaCommon(p);
		p=null;		
	}			
}

function setPersonaCommon(persona) {
	
	if(persona.personaCalificaciones != null && persona.personaCalificaciones.length > 0 || persona.idPersona != undefined){
		limpiarFormulario();
	}
	
	if(persona.idPersona == undefined){ // Persona RENAPO
		$('#fisica\\.idPersona').val("");
	}else if(persona.idPersona != undefined ){ // Persona en IMSS con calificacion
		$('#fisica\\.idPersona').val(persona.idPersona);			
	}
		
	if(persona.nombre != undefined){
		$('#fisica\\.nombre').val(persona.nombre);
	}
	if(persona.curp != undefined){
		$('#fisica\\.curp').val(persona.curp);
	}
	
	if(persona.primerApellido != undefined){        
		$('#fisica\\.primerApellido').val(persona.primerApellido);
	}
	
	if(persona.segundoApellido != undefined){
		$('#fisica\\.segundoApellido').val(persona.segundoApellido);
	} else {
		$('#fisica\\.segundoApellido').val('');
	}	
    
    if(persona.fechaNacimientoFormateada != undefined){
    	$('#fisica\\.fechaNacimiento').val(persona.fechaNacimientoFormateada);
    	//console.debug("fecha de nacimiento: %s, fecha de nacimiento formateada: %s",persona.fechaNacimiento,persona.fechaNacimientoFormateada);
    	validaEdad(persona.fechaNacimientoFormateada);
    }
    
    if(persona.sexo != undefined){
    	if(persona.sexo.idSexo != null){  
	    	$('#fisica\\.sexo\\.idSexo').val(persona.sexo.idSexo);
    	}
    }
    if(persona.lugarNacimiento != undefined){
    	if(persona.lugarNacimiento.clave != null){
	    	$('#fisica\\.lugarNacimiento\\.clave').val(persona.lugarNacimiento.clave);
    	}
    }		                	   
	
    if(persona.idPersona != undefined && persona.idPersona != null){ // Persona en IMSS con calificacion
    	buscarMediosContacto(persona.idPersona);			
	} else {
		limpiarMedios();
	}
}

function limpiarMedios() {
	$("#fisica\\.telefonoFijo\\.clave").val('');
	$("#fisica\\.telefonoFijo\\.claveLada").val('');
	
	$("#fisica\\.telefonoMovil\\.clave").val('');
	$("#fisica\\.telefonoMovil\\.numero").val('');
	
	$("#fisica\\.correoElectronico\\.clave").val('');
	$("#fisica\\.correoElectronico\\.correo").val('');
	
	$("#fisica\\.facebook\\.clave").val('');
	$("#fisica\\.facebook\\.cuenta").val('');
	
	$("#fisica\\.twitter\\.clave").val('');
	$("#fisica\\.twitter\\.cuenta").val('');
}

function buscarMediosContacto(idPersona) {
	var oForm = {
		'idPersona' : idPersona
	};
	
	var url = CONTEXT_PATH_APLICACION + '/wizard/registro/getMediosContacto';
	
	$.blockUI();
	$.postJSON(url, oForm, function(fisica) {
		
		if(fisica.telefonoFijo != undefined && fisica.telefonoFijo != null) {
			$("#fisica\\.telefonoFijo\\.clave").val(fisica.telefonoFijo.clave);
			$("#fisica\\.telefonoFijo\\.claveLada").val(fisica.telefonoFijo.claveLada);
		} else {
			$("#fisica\\.telefonoFijo\\.clave").val('');
			$("#fisica\\.telefonoFijo\\.claveLada").val('');
		}
		
		if(fisica.telefonoMovil != undefined && fisica.telefonoMovil != null) {
			$("#fisica\\.telefonoMovil\\.clave").val(fisica.telefonoMovil.clave);
			$("#fisica\\.telefonoMovil\\.numero").val(fisica.telefonoMovil.numero);
		} else {
			$("#fisica\\.telefonoMovil\\.clave").val('');
			$("#fisica\\.telefonoMovil\\.numero").val('');
		}
		
		if(fisica.correoElectronico != undefined && fisica.correoElectronico != null) {
			$("#fisica\\.correoElectronico\\.clave").val(fisica.correoElectronico.clave);
			$("#fisica\\.correoElectronico\\.correo").val(fisica.correoElectronico.correo);
		} else {
			$("#fisica\\.correoElectronico\\.clave").val('');
			$("#fisica\\.correoElectronico\\.correo").val('');
		}
		
		if(fisica.facebook != undefined && fisica.facebook != null) {
			$("#fisica\\.facebook\\.clave").val(fisica.facebook.clave);
			$("#fisica\\.facebook\\.cuenta").val(fisica.facebook.cuenta);
		} else {
			$("#fisica\\.facebook\\.clave").val('');
			$("#fisica\\.facebook\\.cuenta").val('');
		}
		
		if(fisica.twitter != undefined && fisica.twitter != null) {
			$("#fisica\\.twitter\\.clave").val(fisica.twitter.clave);
			$("#fisica\\.twitter\\.cuenta").val(fisica.twitter.cuenta);
		} else {
			$("#fisica\\.twitter\\.clave").val('');
			$("#fisica\\.twitter\\.cuenta").val('');
		}
		
		$.unblockUI();
	}).error(function(data){
		
	});
}

function validaEdad(fecha) {
	
	var idParentesco = $("#parentesco\\.idParentesco").val();
	var fechas = fecha.split("/");
	//Se transforma la fecha ya que debe ir dd/MM/yyyy
	fecha = "" + fechas[1] + "/" + fechas[0] + "/" + fechas[2];
	
	var edad = calcularEdad(fecha);
	var razonRegistro = "-1";
	
	if(idParentesco == PARENTESCO_ENUM.HIJOS) {
		if(edad <= 1){
			if($('#recienNacido').is(":checked")){
				razonRegistro = RAZON_REGISTRO_ENUM.RECIEN_NACIDO;
			}else{
				razonRegistro = RAZON_REGISTRO_ENUM.HASTA_16;
			}
		} else {
			if($('#miNombre').val() == 'RECIEN NACIDO' ){
				$('#miNombre').val('');
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
				}
			} else {
				razonRegistro = RAZON_REGISTRO_ENUM.HASTA_25;
			} 
		}

		if(edad > 25){			
			razonRegistro = RAZON_REGISTRO_ENUM.MAYOR_25;
		}
	} else {
		razonRegistro = RAZON_REGISTRO_ENUM.NORMAL;
	}
	
	$('#razonRegistro\\.idRazonRegistro').val(razonRegistro);
	$("#razonDelRegistro").val(razonRegistro);
}

function limpiarFormulario(){	
	
	$('#fisica\\.idPersona').val('');		
	$('#fisica\\.nombre').val('');		
	$('#fisica\\.primerApellido').val('');		
	$('#fisica\\.segundoApellido').val('');		
	$('#fisica\\.curp').val('');		
	$('#fisica\\.lugarNacimiento\\.clave')[0].selectedIndex=0; 		
	$('#fisica\\.sexo\\.idSexo')[0].selectedIndex=0;		
	$('#fisica\\.fechaNacimiento').val('');		
	
	verificarErrores()
		
}

function habilitarDesabilitarCamposDatosBasicos(habilitar,envio) {
	
	if(habilitar) {
		if(!IS_RECIEN_NACIDO) {
			$('#fisica\\.curp').removeAttr("disabled");
		}
		$('#fisica\\.nombre').removeAttr("disabled");
		$('#fisica\\.primerApellido').removeAttr("disabled");
		$('#fisica\\.lugarNacimiento\\.clave').removeAttr("disabled");
		$('#fisica\\.segundoApellido').removeAttr("disabled");
		$('#fisica\\.sexo\\.idSexo').removeAttr("disabled");
		$('#fisica\\.fechaNacimiento').removeAttr("disabled");
		$('#fisica\\.fechaNacimiento').datepicker( "option", "showOn", "both" );
		
		if(envio) {
			$('#parentesco\\.idParentesco').removeAttr("disabled");
			$('#fisica\\.estadoCivil\\.idEstadoCivil').removeAttr("disabled");
			$('#razonRegistro\\.idRazonRegistro').removeAttr("disabled");
		}
	} else {
		$('#fisica\\.curp').attr("disabled","disabled");
		if(!IS_RECIEN_NACIDO){
			$('#fisica\\.nombre').attr("disabled","disabled");
			$('#fisica\\.primerApellido').attr("disabled","disabled");
			$('#fisica\\.lugarNacimiento\\.clave').attr("disabled","disabled");
			$('#fisica\\.segundoApellido').attr("disabled","disabled");
			$('#fisica\\.sexo\\.idSexo').attr("disabled","disabled");
			$('#fisica\\.fechaNacimiento').attr("disabled","disabled");
			$('#fisica\\.fechaNacimiento').datepicker( "option", "showOn", "focus" );
		}
		
		$('#parentesco\\.idParentesco').attr("disabled","disabled");
		if(!PEMITIR_ELECCION_ESTADO_CIVIL) {
			$('#fisica\\.estadoCivil\\.idEstadoCivil').attr("disabled","disabled");
		}
		$('#razonRegistro\\.idRazonRegistro').attr("disabled","disabled");
	}
	
}

var setearValorHijosProcreados = function() {
	if($('#hijosProcreadosCheck').is(":checked")){
		$("#indHijosProcreados").val(1);
	}else{
		$("#indHijosProcreados").val(0);
	}
}


var verificarErrores = function() {
	//buscamos los erroresen el formulario de registro
	marcarCamposConErrores("formRegistro",".error","td");
}