CLINICA_ENCONTRADA = false;

$(document).ready(function() {
	
	//inicializamos el componente de domicilio recortado
	$("#componenteDomicilio").domicilioRecortado({
		funcionError: null,
		mostrarMensajeCaptura: false,
		mostrarFormulario: true,
		mostrarTitulosDialogs: true,
		mostrarMesajeRequeridos: false, 
		funcionOk: validacionesCP
	});
	
	$("#continuar").click(continuar);
	
	$("#datosIncorrectos").click(cancelar);
	
	$(".salir").click(function() {
		cancelar(null);
	});
	
	$(".item-breadcrumb").click(function() {
		cancelar($(this).attr("url"));
	});
});

var validacionesCP = function() {
	var codigoPostal = $("#domicilio\\.codigoPostal\\.codigoPostal").val();
	console.log("se busca umf para el codigo postal " + codigoPostal);
	$.ajax({
		url: "/gestionAsegurados-web-externo/asignacionNSS/validaDomicilio",
		type: 'post',
		beforeSend : $.blockUI,
        contentType: 'application/json',
        dataType: 'JSON',
        data: JSON.stringify({"codigoPostal": {"codigoPostal": codigoPostal}}),
        complete: $.unblockUI,
        success : function (result) {
        	var $divError = $("#mensajeError"),
        	$btnContinuar = $("#continuar");
        	if(!result.resultado) {
        		$divError.show();
        		$divError.html(result.mensaje);
        		$btnContinuar.hide();
        	} else {
        		$divError.hide();
        		$divError.html("");
        		$btnContinuar.show();
        	}
        }
	})
}

var continuar = function() {
	var domicilioCapturado = $("#componenteDomicilio").domicilioRecortado("get");
	
	if(domicilioCapturado != null) {
		setearDomicilio(domicilioCapturado);
		$("#formularioDomicilioNSS").attr("action","finalizar");
		//$(this).dialog("close");
		$("#formularioDomicilioNSS").submit();
	}
}

var mostrarMensajeConfirmacion = function() {
	var buttons = {
		"Cancelar" : function() {$(this).dialog("close");},
		"Aceptar" : function() {
			$("#formularioDomicilioNSS").attr("action","finalizar");
			$(this).dialog("close");
			$("#formularioDomicilioNSS").submit();
		}
	}
	
	//mostramos el mensaje
	crearDialogo("A continuaci&oacute;n se asignar&aacute; su NSS, &iquest; Est&acute; seguro que desea continuar?", "Confirmaci&oacute;n requerida", buttons);
}

var cancelar = function(action) {
	
	var buttons = {
		"No" : function() {$(this).dialog("close");},
		"Si" : function() {
			$(this).dialog("close");
			if(action){
				$("#formSalir").attr("action",action);
			}
			$("#formSalir").submit();
		}
	}
	//mostramos el mensaje
	crearDialogo("&iquest; Est&aacute;s seguro que deseas cancelar?", "Atenci\u00F3n", buttons);
}

var crearDialogo = function(mensaje, titulo, buttons) {
	
	$divMensajes = $( "#mensajes" );
	$divMensajes.dialog({
		resizable: false,
		height:'auto',
		modal: true,
		title: titulo,
		autoOpen: false,
	    closeOnEscape: false,
		buttons: buttons
	 });
	
	$divMensajes.html(mensaje);
	$divMensajes.dialog('open');
}

var setearDomicilio = function(objetoDomicilio) {
	$formularioDomicilio = $("#formularioDomicilioNSS");
	
	if (objetoDomicilio != null) {
		try {

			$formularioDomicilio.find('#entidadFederativa' ).val(validaAtributoVacio(objetoDomicilio.asentamiento.localidad.municipio.entidadFederativa.nombre));
			$formularioDomicilio.find('#municipio' ).val(validaAtributoVacio(objetoDomicilio.asentamiento.localidad.municipio.nombre));
			$formularioDomicilio.find('#localidad' ).val(validaAtributoVacio(objetoDomicilio.asentamiento.localidad.nombre));
			$formularioDomicilio.find('#asentamiento' ).val(validaAtributoVacio(objetoDomicilio.asentamiento.nombre));

			$formularioDomicilio.find('#entidadFederativaClave' ).val(validaAtributoVacio(objetoDomicilio.asentamiento.localidad.municipio.entidadFederativa.clave));
			$formularioDomicilio.find('#municipioClave' ).val(validaAtributoVacio(objetoDomicilio.asentamiento.localidad.municipio.clave));
			$formularioDomicilio.find('#localidadClave' ).val(validaAtributoVacio(objetoDomicilio.asentamiento.localidad.clave));
			$formularioDomicilio.find('#asentamientoClave' ).val(validaAtributoVacio(objetoDomicilio.asentamiento.clave));
			
			$formularioDomicilio.find('#numeroExteriorPrincipal' ).val(validaAtributoVacio(objetoDomicilio.numExterior1));
			$formularioDomicilio.find('#numeroExteriorAlfanumerico' ).val(validaAtributoVacio(objetoDomicilio.numExteriorAlf));
			$formularioDomicilio.find('#numeroExteriorSecundario' ).val(validaAtributoVacio(objetoDomicilio.numExterior2));
			$formularioDomicilio.find('#numeroInterior' ).val(validaAtributoVacio(objetoDomicilio.numInterior));
			$formularioDomicilio.find('#numeroInteriorAlfanumerico' ).val(validaAtributoVacio(objetoDomicilio.numInteriorAlf));
			
			$formularioDomicilio.find('#calle' ).val(validaAtributoVacio(objetoDomicilio.calle));
			
			if (validaAtributoVacio(objetoDomicilio.vialidadPrimaria) == '') {
				$formularioDomicilio.find('#vialidadPrimaria' ).val('');
				$formularioDomicilio.find('#vialidadPrimaria\\.clave' ).val('');
			} else {
				$formularioDomicilio.find('#vialidadPrimaria' ).val(validaAtributoVacio(objetoDomicilio.vialidadPrimaria.nombre));
				$formularioDomicilio.find('#vialidadPrimaria\\.clave' ).val(validaAtributoVacio(objetoDomicilio.vialidadPrimaria.clave));
			}
			
			if(validaAtributoVacio(objetoDomicilio.domicilioCarretera) == '') {
				$formularioDomicilio.find('#domCar\\.ter\\.des' ).val('');
				$formularioDomicilio.find('#domCar\\.ter\\.cve' ).val('');
				$formularioDomicilio.find('#domCar\\.der\\.des' ).val('');
				$formularioDomicilio.find('#domCar\\.der\\.cve' ).val('');
				$formularioDomicilio.find('#domCar\\.or' ).val('');
				$formularioDomicilio.find('#domCar\\.des' ).val('');
				$formularioDomicilio.find('#domCar\\.adm\\.des' ).val('');
				$formularioDomicilio.find('#domCar\\.adm\\.cve' ).val('');
				$formularioDomicilio.find('#domCar\\.cad' ).val('');
				$formularioDomicilio.find('#domCar\\.cod' ).val('');
			} else {
				$formularioDomicilio.find('#domCar\\.ter\\.des' ).val(validaAtributoVacio(objetoDomicilio.domicilioCarretera.terminoGeneral.descripcion));
				$formularioDomicilio.find('#domCar\\.ter\\.cve' ).val(validaAtributoVacio(objetoDomicilio.domicilioCarretera.terminoGeneral.clave));
				$formularioDomicilio.find('#domCar\\.der\\.des' ).val(validaAtributoVacio(objetoDomicilio.domicilioCarretera.derechoTransito.descripcion));
				$formularioDomicilio.find('#domCar\\.der\\.cve' ).val(validaAtributoVacio(objetoDomicilio.domicilioCarretera.derechoTransito.clave));
				$formularioDomicilio.find('#domCar\\.or' ).val(validaAtributoVacio(objetoDomicilio.domicilioCarretera.origen));
				$formularioDomicilio.find('#domCar\\.des' ).val(validaAtributoVacio(objetoDomicilio.domicilioCarretera.destino));
				$formularioDomicilio.find('#domCar\\.adm\\.des' ).val(validaAtributoVacio(objetoDomicilio.domicilioCarretera.administracion.descripcion));
				$formularioDomicilio.find('#domCar\\.adm\\.cve' ).val(validaAtributoVacio(objetoDomicilio.domicilioCarretera.administracion.clave));
				$formularioDomicilio.find('#domCar\\.cad' ).val(validaAtributoVacio(objetoDomicilio.domicilioCarretera.cadenamiento));
				$formularioDomicilio.find('#domCar\\.cod' ).val(validaAtributoVacio(objetoDomicilio.domicilioCarretera.codigoCarretera));
			}
			
			if(validaAtributoVacio(objetoDomicilio.domicilioCamino) == '') {
				$formularioDomicilio.find('#domCam\\.ter\\.des' ).val('');
				$formularioDomicilio.find('#domCam\\.ter\\.cve' ).val('');
				$formularioDomicilio.find('#domCam\\.mar\\.des' ).val('');
				$formularioDomicilio.find('#domCam\\.mar\\.cve' ).val('');
				$formularioDomicilio.find('#domCam\\.or' ).val('');
				$formularioDomicilio.find('#domCam\\.des' ).val('');
				$formularioDomicilio.find('#domCam\\.cad' ).val('');
			} else {
				$formularioDomicilio.find('#domCam\\.ter\\.des' ).val(validaAtributoVacio(objetoDomicilio.domicilioCamino.terminoGeneral.descripcion));
				$formularioDomicilio.find('#domCam\\.ter\\.cve' ).val(validaAtributoVacio(objetoDomicilio.domicilioCamino.terminoGeneral.clave));
				$formularioDomicilio.find('#domCam\\.mar\\.des' ).val(validaAtributoVacio(objetoDomicilio.domicilioCamino.margen.descripcion));
				$formularioDomicilio.find('#domCam\\.mar\\.cve' ).val(validaAtributoVacio(objetoDomicilio.domicilioCamino.margen.clave));
				$formularioDomicilio.find('#domCam\\.or' ).val(validaAtributoVacio(objetoDomicilio.domicilioCamino.origen));
				$formularioDomicilio.find('#domCam\\.des' ).val(validaAtributoVacio(objetoDomicilio.domicilioCamino.destino));
				$formularioDomicilio.find('#domCam\\.cad' ).val(validaAtributoVacio(objetoDomicilio.domicilioCamino.cadenamiento));
			}
			
			if (validaAtributoVacio(objetoDomicilio.vialidadReferenciaPrimaria) == '') {
				$formularioDomicilio.find('#vialidadReferenciaPrimaria' ).val('');
				$formularioDomicilio.find('#vialidadReferenciaPrimaria\\.clave' ).val('');
			} else {
				$formularioDomicilio.find('#vialidadReferenciaPrimaria' ).val(validaAtributoVacio(objetoDomicilio.vialidadReferenciaPrimaria.nombre));
				$formularioDomicilio.find('#vialidadReferenciaPrimaria\\.clave' ).val(validaAtributoVacio(objetoDomicilio.vialidadReferenciaPrimaria.clave));
			}
			
			if (validaAtributoVacio(objetoDomicilio.vialidadReferenciaSecundaria) == '') {
				$formularioDomicilio.find('#vialidadReferenciaSecundaria' ).val('');
				$formularioDomicilio.find('#vialidadReferenciaSecundaria\\.clave' ).val('');
			} else {
				$formularioDomicilio.find('#vialidadReferenciaSecundaria' ).val(validaAtributoVacio(objetoDomicilio.vialidadReferenciaSecundaria.nombre));
				$formularioDomicilio.find('#vialidadReferenciaSecundaria\\.clave' ).val(validaAtributoVacio(objetoDomicilio.vialidadReferenciaSecundaria.clave));
			}
			
			if (validaAtributoVacio(objetoDomicilio.vialidadReferenciaPosterior) == '') {
				$formularioDomicilio.find('#vialidadReferenciaPosterior' ).val('');
				$formularioDomicilio.find('#vialidadReferenciaPosterior\\.clave' ).val('');
			} else {
				$formularioDomicilio.find('#vialidadReferenciaPosterior' ).val(validaAtributoVacio(objetoDomicilio.vialidadReferenciaPosterior.nombre));
				$formularioDomicilio.find('#vialidadReferenciaPosterior\\.clave' ).val(validaAtributoVacio(objetoDomicilio.vialidadReferenciaPosterior.clave));
			}
			
			$formularioDomicilio.find('#claveEntidadFederativa' ).val(validaAtributoVacio(objetoDomicilio.asentamiento.localidad.municipio.entidadFederativa.clave));
			$formularioDomicilio.find('#claveMunicipio' ).val(validaAtributoVacio(objetoDomicilio.asentamiento.localidad.municipio.clave));
			$formularioDomicilio.find('#claveLocalidad' ).val(validaAtributoVacio(objetoDomicilio.asentamiento.localidad.clave));
			$formularioDomicilio.find('#claveAsentamiento' ).val(validaAtributoVacio(objetoDomicilio.asentamiento.clave));
			
			$formularioDomicilio.find('#codigoPostal' ).val(validaAtributoVacio(objetoDomicilio.codigoPostal.codigoPostal));
			$formularioDomicilio.find('#asentamientoCodigoPostal' ).val(validaAtributoVacio(objetoDomicilio.codigoPostal.codigoPostal));

			$formularioDomicilio.find('#latitud' ).val(validaAtributoVacio(objetoDomicilio.latitud));
			$formularioDomicilio.find('#longitud' ).val(validaAtributoVacio(objetoDomicilio.longitud));
			
		} catch (e) {}		
	}

};

function validaAtributoVacio (atributo) {
	if (atributo == null || typeof atributo === 'undefined') {
		return "";
	} else {
		return atributo;
	}
}