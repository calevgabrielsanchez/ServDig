var asentamientosUbicados=null;
window.query_cache = {};
var request = null;
var busquedaPersonaCtrl = null;

$(document).ready(function() {
	window.query_cache = {};
	
	$("#componenteDomicilio").domicilioRecortado({
		funcionOk: muestraPaso3,
		funcionLimpiar:limpiarTodo,
		funcionCambioColonia: eventoCambioColonia,
		mostrarMesajeRequeridos: false,
		mostrarMensajeCaptura: false,
		habilitarTooltips: false,
		mostrarTitulosDialogs: true
	});
	
	if($("#buscarPersona").length > 0) {
		
		busquedaPersonaCtrl = new PantallaPersonaCtrl();
		
		$("#buscarPersona").on("click", busquedaPersonaCtrl.buscar);
		$("#limpiarPersona").on("click",busquedaPersonaCtrl.limpiar);
		$("#personaCapturada").on("click", busquedaPersonaCtrl.continuar);
		$('#curp').on('keypress', function(e) {
		    if (e.which == 13) {
		    	e.preventDefault();
		    	busquedaPersonaCtrl.buscar();
		    	return false;
		    }
		});
		setEventosChecarError("formBusquedaPersona",function() {
			marcarCamposConErrores("formBusquedaPersona",".error","div");
		});
	}
});

PantallaPersonaCtrl = function() {
	
	var urlBusqueda = context_path + '/derechohabientes/tramite/registro/busquedaPersona',
	_persona = null,
	_self = this;
	
	this.buscar = function() {
		
		var $divErrores = $("#divErrorCampos"), $divErrorPersona = $("#divErrorBusquedaPersona"),
		busqueda = $("#formBusquedaPersona").toObject();
		
		$divErrores.hide();
		$divErrores.html("");
		$divErrorPersona.hide();
		$divErrorPersona.html("");
		
		fnHideErrores("form#formBusquedaPersona");
		
		$.blockUI();
		$.postJSON(urlBusqueda, busqueda, function(result) {
			if(result.error) {
				_persona = null;
				mostrarMensajeError(result.mensaje, "Error");
			} else {
				procesar(result.fisica);
			}

			$.unblockUI();
		}).error(function(data){
			$.unblockUI();
			//mostrarMensajeError("Existen errores en la informaci&oacute;n capturada","Error");
			fnProcesarErrores(data, "#formBusquedaPersona");
			marcarCamposConErrores("formBusquedaPersona",".error","div");
			$("#divErrorCampos").get(0).scrollIntoView();
		});
	};
	
	this.limpiar = function() {
		var $divErrores = $("#divErrorCampos"), $formBusqueda = $("#formBusquedaPersona"),
		$divInfo = $("#infoPersona"), $campoCurp = $formBusqueda.find("#curp");
		//limpiamos cada uno de los campos de resultado
		$divInfo.find(".limpiable").each(function() {
			$(this).text("");
			$(this).val("");
		});
		//ocultamos los datos
		$divInfo.hide();
		//ocultamos toda el area del domicilio
		$("#capturaDomicilio").hide();
		//limpiamos el componente de domicilio recortado
		//$("#componenteDomicilio").domicilioRecortado("clean");
		$campoCurp.val("");
		$campoCurp.focus();
		//mostramos el formulario de busqueda
		$formBusqueda.show();
		$formBusqueda.get(0).scrollIntoView();
		//quitamos el paso completado
		$("#li3").removeClass("completed");
		$("#correo").removeAttr("disabled");
		$("#telefono").removeAttr("disabled");
		$("#personaCapturada").show();
	
	};
	
	this.getPersona = function() {
		return _persona;
	};
	
	this.continuar = function() {
		
		
		var $divCaptura = $("#capturaDomicilio"),
		urlValMedios = context_path + '/derechohabientes/tramite/registro/validacionesMedios',
		medios = $("#formMediosDeContacto").toObject();
		fnHideErrores("form#formMediosDeContacto");
		marcarCamposConErrores("formMediosDeContacto",".error","div");
		
		$.blockUI();
		$.postJSON(urlValMedios, medios, function(result) {
			//una vez que ubicamos a la persona mostramos los datos del domicilio
			$("#correo").attr("disabled","disabled");
			$("#telefono").attr("disabled","disabled");
			$("#personaCapturada").hide();
			$divCaptura.show();
			$divCaptura.get(0).scrollIntoView();
			$("#li3").addClass("completed");
			$.unblockUI();
		}).error(function(data){
			$.unblockUI();
			//mostrarMensajeError("Existen errores en la informaci&oacute;n capturada","Error");
			fnProcesarErrores(data, "#formMediosDeContacto");
			marcarCamposConErrores("formMediosDeContacto",".error","div");
			$("#divErrorCampos").get(0).scrollIntoView();
		});
		
		
	}
	
	this.regresar = function() {
		var $divCaptura = $("#capturaDomicilio"), $divPersona = $("#infoPersona");
		//una vez que ubicamos a la persona mostramos los datos del domicilio
		$divPersona.show();
		$divCaptura.hide();
		$divPersona.get(0).scrollIntoView();
		$("#li3").removeClass("completed");
	}
	
	//Funcion privada para procesar a la persona encontrada
	var procesar = function(persona) {
		
		_persona = persona;
		
		var $divInfo = $("#infoPersona");
		
		$divInfo.find("#infoCurp").text(_persona.curp);
		$divInfo.find("#infoNombre").text(_persona.nombre);
		$divInfo.find("#infoPApellido").text(_persona.primerApellido);
		$divInfo.find("#infoSApellido").text(_persona.segundoApellido);
		$divInfo.find("#infoLNacimiento").text(_persona.lugarNacimiento.nombre);
		$divInfo.find("#infoFNacimiento").text(_persona.fechaNacimientoFormateada);
		$divInfo.find("#infoSexo").text(_persona.sexo.descripcion);
		$("#formBusquedaPersona").hide();
		
		//_self.continuar();
		$divInfo.show();
	}
	
}