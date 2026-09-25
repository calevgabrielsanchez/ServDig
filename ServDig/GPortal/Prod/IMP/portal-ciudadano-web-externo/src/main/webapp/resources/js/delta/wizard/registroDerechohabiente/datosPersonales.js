/**
 * 
 */

IS_ASEGURADO = false;
PEMITIR_ELECCION_ESTADO_CIVIL = false;
IS_PATRON_IMSS = false;
IS_RECIEN_NACIDO = false;
NOMBRE_RECIEN_NACIDO = 'RECIEN NACIDO';

$(document).ready(
	function () {
		
		IS_PATRON_IMSS = $("#isPatronImss").val() == 1 ? true: false;
		var idParentescoDer = $("#parentescoDelRegistro").val();
		
		IS_ASEGURADO = idParentescoDer == PARENTESCO_ENUM.ASEGURADO || idParentescoDer == PARENTESCO_ENUM.PENSIONADO;
		PEMITIR_ELECCION_ESTADO_CIVIL = IS_ASEGURADO || idParentescoDer == PARENTESCO_ENUM.PADRES;
		
		razon_registro = $("#razonDelRegistro").val();
		
		habilitarDesabilitarCamposDatosBasicos(false,false);
		
		$("#continuarADomicilio").click(function() {
			validarDatosPersonales();
		});
	}
);


function validarDatosPersonales() {
	
	$("form#formRegistro").habilitarContenido(false);
	var oForm = $("form#formRegistro").toObject();
	var url = '/portal-ciudadano-web-externo/wizard/registro/validaciones';
	fnHideErrores("form#formRegistro");
	
	$.blockUI();
	$.postJSON(url, oForm, function(data2) {
		
		if(!data2.correcto) {
			$.unblockUI();
			habilitarDesabilitarCamposDatosBasicos(false,false);
			mostrarMensajeError(data2.mensaje);
		} else {
			$.blockUI();
			setearDescripciones();
			$("#formRegistro").attr("action","/portal-ciudadano-web-externo/wizard/registro/siguiente");
			$("#formRegistro").submit();
		}
	}).error(function(data){
		habilitarDesabilitarCamposDatosBasicos(false,false);
		$.unblockUI();
		fnProcesarErrores(data, "form#formRegistro");
	});
}

function setearDescripciones() {

	setDescripcionCombo("parentesco\\.idParentesco","parentesco\\.descripcion");
	setDescripcionCombo("fisica\\.lugarNacimiento\\.clave","fisica\\.lugarNacimiento\\.nombre");
	setDescripcionCombo("fisica\\.estadoCivil\\.idEstadoCivil","fisica\\.estadoCivil\\.descripcion");
	setDescripcionCombo("fisica\\.sexo\\.idSexo","fisica\\.sexo\\.descripcion");
	setDescripcionCombo("razonRegistro\\.idRazonRegistro","razonRegistro\\.descripcion");
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
		
}

function habilitarDesabilitarCamposDatosBasicos(habilitar,envio) {
	
	if(habilitar) {
		$('#fisica\\.curp').removeAttr("disabled");
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
		
			$('#fisica\\.nombre').attr("disabled","disabled");
			$('#fisica\\.primerApellido').attr("disabled","disabled");
			$('#fisica\\.lugarNacimiento\\.clave').attr("disabled","disabled");
			$('#fisica\\.segundoApellido').attr("disabled","disabled");
			$('#fisica\\.sexo\\.idSexo').attr("disabled","disabled");
			$('#fisica\\.fechaNacimiento').attr("disabled","disabled");
			$('#fisica\\.fechaNacimiento').datepicker( "option", "showOn", "focus" );
		
		$('#parentesco\\.idParentesco').attr("disabled","disabled");
		if(!PEMITIR_ELECCION_ESTADO_CIVIL) {
			$('#fisica\\.estadoCivil\\.idEstadoCivil').attr("disabled","disabled");
		}
		$('#razonRegistro\\.idRazonRegistro').attr("disabled","disabled");
	}
	
}