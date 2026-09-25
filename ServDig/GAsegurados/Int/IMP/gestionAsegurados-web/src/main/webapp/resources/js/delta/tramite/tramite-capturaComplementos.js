/**
 * JS para la vista de la caputa libre de la persona
 */

var objTipoSerie;

$(document).ready(function() {

	// Verificamos los campos a readonly
	$('div#datosBasicosDiv').deshabilitarContenido(false);

	// los combos hechos con el componente maravilloso no los desabilitan los metodos anteriores, por eso hay que hacerlo de forma manual	
	$('#fisica\\.lugarNacimiento\\.clave').attr('disabled', 'disabled');
	$('#fisica\\.sexo\\.idSexo').attr('disabled', 'disabled');

	// Se deshabilita debido a que se puede capturar una persona sin Curp
	$('#registroCurp').attr('disabled', 'disabled');
	// Se deshabilita debido a que se puede capturar una persona sin apellido Paterno
	$('#registroSegundoApellido').attr('disabled', 'disabled');

	// Configuracion del calendario
	var fechaNac = $('#registroFechaNacimientoC').val();

	if (!(fechaNac && fechaNac != '')) {
		$("#registroFechaNacimientoC").datepicker({
			showOn : 'both',
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			yearRange : '-112:+0'
		});
	}

	$('#regresar').click(function() {
		var url = context_path + "/tramite/iniciar";
		var form = $('form#registroPersonaFisicaForm').attr('action', url);
		form.attr('method', 'get');
		form.submit();
	});

	$('#registrar').click(function() {
		
		$('div#datosBasicosDiv').habilitarContenido(false);
		// los combos con el componente maravilloso no los desabilitan los metodos anteriores, por eso hay que hacerlo de forma manual		
		$('#fisica\\.lugarNacimiento\\.clave').removeAttr('disabled');
		$('#fisica\\.sexo\\.idSexo').removeAttr('disabled');
		
		$('form#registroPersonaFisicaForm').submit();
	});

	/**
	 * Incluimos el JS de Domicilios.
	 */
	var urlDomicilios = "/gestionDomicilios-web/static/resources/js/delta/domicilios/Domicilio.js";
	$.getScript(urlDomicilios).done(function(script, textStatus) {
		DomicilioCtrl.init('domicilioLocaliza');
		DomicilioCtrl.setOnCloseCallback(fnOnCloseDomicilio);
		$('#btnUbicarDomicilio').click(function() {
			DomicilioCtrl.localizar();
		});
	}).fail(function(jqxhr, settings, exception) {
		alert('Error al cargar el script /gestionDomicilios-web/static/resources/js/delta/domicilios/Domicilio.js');
	});

	var mediosDiv = $('div#mediosContactoDatos');
	var cveCorreo = $('input[type=\'hidden\']#cveCorreo').val();
	// Sólo se hacen readonly los campos de medios cuando no se tenga uno previamente capturado
	if ( validaAtributoVacio(cveCorreo) != '') {
		$('input[type=\'text\'],input[type=\'checkbox\'] ',	mediosDiv).each(function() {
			$(this).fadeTo('slow', 0.5);
			$(this).attr('readonly', 'readony');
		});
	}

	var serieDiv = $('div#datosSerie');
	$('input[type=\'text\'],input[type=\'checkbox\'] ',	serieDiv).each(function() {
		$(this).fadeTo('slow', 0.5);
		$(this).attr('readonly', 'readony');
	});
	
	if ($('form#asentamientoForUmfForm').length > 0) {
		obtenerUMFs();
	} else if ($('input#fromSIME').length > 0) {
		if ($('input#codigoPostalExtranjero').val() != ''
			&& $('input#codigoPostalExtranjero').val() != undefined) {
			obtenerUMFsByCodigoPostal($('input#codigoPostalExtranjero').val());
		} else {
			$('div#umfContenedor').html('No se cuenta con la información necesaria para obtener las Unidades Médico Familiar');
		}
	} else {
		$('div#umfContenedor').html('No se cuenta con la información necesaria para obtener las Unidades Médico Familiar');
	}
	
	$('button#ignoreCurrentRecord').click(function(){
		$('form#extranjeroSIMEIgnoreForm').submit();
	});
	
});



/*
 * Funcion de callback para el llenado de la informacion del domicilio
 */
var fnOnCloseDomicilio = function() {
	var objetoDomicilio = this;
	var contextDomicilio = $('div#domicilio div#datos');

	if (objetoDomicilio != null) {
		try {			
			$('#entidadFederativa', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.asentamiento.localidad.municipio.entidadFederativa.nombre));
			$('#municipio', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.asentamiento.localidad.municipio.nombre));
			$('#localidad', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.asentamiento.localidad.nombre));
			$('#asentamiento', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.asentamiento.nombre));

			$('#entidadFederativaClave', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.asentamiento.localidad.municipio.entidadFederativa.clave));
			$('#municipioClave', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.asentamiento.localidad.municipio.clave));
			$('#localidadClave', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.asentamiento.localidad.clave));
			$('#asentamientoClave', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.asentamiento.clave));
			
			$('#numeroExteriorPrincipal', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.numExterior1));
			$('#numeroExteriorAlfanumerico', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.numExteriorAlf));
			$('#numeroExteriorSecundario', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.numExterior2));
			$('#numeroInterior', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.numInterior));
			$('#numeroInteriorAlfanumerico', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.numInteriorAlf));
			
			$('#calle', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.calle));
			
			if (validaAtributoVacio(objetoDomicilio.vialidadPrimaria) == '') {
				$('#vialidadPrimaria', contextDomicilio).attr('value','');
				$('#vialidadPrimaria\\.clave', contextDomicilio).attr('value','');
			} else {
				$('#vialidadPrimaria', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.vialidadPrimaria.nombre));
				$('#vialidadPrimaria\\.clave', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.vialidadPrimaria.clave));
			}
			
			
			if(validaAtributoVacio(objetoDomicilio.domicilioCarretera) == '') {
				$('#domCar\\.ter\\.des', contextDomicilio).attr('value','');
				$('#domCar\\.ter\\.cve', contextDomicilio).attr('value','');
				$('#domCar\\.der\\.des', contextDomicilio).attr('value','');
				$('#domCar\\.der\\.cve', contextDomicilio).attr('value','');
				$('#domCar\\.or', contextDomicilio).attr('value','');
				$('#domCar\\.des', contextDomicilio).attr('value','');
				$('#domCar\\.adm\\.des', contextDomicilio).attr('value','');
				$('#domCar\\.adm\\.cve', contextDomicilio).attr('value','');
				$('#domCar\\.cad', contextDomicilio).attr('value','');
				$('#domCar\\.cod', contextDomicilio).attr('value','');
			} else {
				$('#domCar\\.ter\\.des', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.domicilioCarretera.terminoGeneral.descripcion));
				$('#domCar\\.ter\\.cve', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.domicilioCarretera.terminoGeneral.clave));
				$('#domCar\\.der\\.des', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.domicilioCarretera.derechoTransito.descripcion));
				$('#domCar\\.der\\.cve', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.domicilioCarretera.derechoTransito.clave));
				$('#domCar\\.or', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.domicilioCarretera.origen));
				$('#domCar\\.des', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.domicilioCarretera.destino));
				$('#domCar\\.adm\\.des', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.domicilioCarretera.administracion.descripcion));
				$('#domCar\\.adm\\.cve', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.domicilioCarretera.administracion.clave));
				$('#domCar\\.cad', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.domicilioCarretera.cadenamiento));
				$('#domCar\\.cod', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.domicilioCarretera.codigoCarretera));
			}
			
			if(validaAtributoVacio(objetoDomicilio.domicilioCamino) == '') {
				$('#domCam\\.ter\\.des', contextDomicilio).attr('value','');
				$('#domCam\\.ter\\.cve', contextDomicilio).attr('value','');
				$('#domCam\\.mar\\.des', contextDomicilio).attr('value','');
				$('#domCam\\.mar\\.cve', contextDomicilio).attr('value','');
				$('#domCam\\.or', contextDomicilio).attr('value','');
				$('#domCam\\.des', contextDomicilio).attr('value','');
				$('#domCam\\.cad', contextDomicilio).attr('value','');
			} else {
				$('#domCam\\.ter\\.des', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.domicilioCamino.terminoGeneral.descripcion));
				$('#domCam\\.ter\\.cve', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.domicilioCamino.terminoGeneral.clave));
				$('#domCam\\.mar\\.des', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.domicilioCamino.margen.descripcion));
				$('#domCam\\.mar\\.cve', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.domicilioCamino.margen.clave));
				$('#domCam\\.or', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.domicilioCamino.origen));
				$('#domCam\\.des', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.domicilioCamino.destino));
				$('#domCam\\.cad', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.domicilioCamino.cadenamiento));
			}
			
			
			
			if (validaAtributoVacio(objetoDomicilio.vialidadReferenciaPrimaria) == '') {
				$('#vialidadReferenciaPrimaria', contextDomicilio).attr('value','');
				$('#vialidadReferenciaPrimaria\\.clave', contextDomicilio).attr('value','');
			} else {
				$('#vialidadReferenciaPrimaria', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.vialidadReferenciaPrimaria.nombre));
				$('#vialidadReferenciaPrimaria\\.clave', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.vialidadReferenciaPrimaria.clave));
			}
			
			if (validaAtributoVacio(objetoDomicilio.vialidadReferenciaSecundaria) == '') {
				$('#vialidadReferenciaSecundaria', contextDomicilio).attr('value','');
				$('#vialidadReferenciaSecundaria\\.clave', contextDomicilio).attr('value','');
			} else {
				$('#vialidadReferenciaSecundaria', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.vialidadReferenciaSecundaria.nombre));
				$('#vialidadReferenciaSecundaria\\.clave', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.vialidadReferenciaSecundaria.clave));
			}
			
			if (validaAtributoVacio(objetoDomicilio.vialidadReferenciaPosterior) == '') {
				$('#vialidadReferenciaPosterior', contextDomicilio).attr('value','');
				$('#vialidadReferenciaPosterior\\.clave', contextDomicilio).attr('value','');
			} else {
				$('#vialidadReferenciaPosterior', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.vialidadReferenciaPosterior.nombre));
				$('#vialidadReferenciaPosterior\\.clave', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.vialidadReferenciaPosterior.clave));
			}
			
			$('#claveEntidadFederativa', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.asentamiento.localidad.municipio.entidadFederativa.clave));
			$('#claveMunicipio', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.asentamiento.localidad.municipio.clave));
			$('#claveLocalidad', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.asentamiento.localidad.clave));
			$('#claveAsentamiento', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.asentamiento.clave));
			
			$('#codigoPostal', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.asentamiento.codigoPostal.codigoPostal));
			
			$('#descripcion', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.descripcion));
			
			$('#latitud', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.latitud));
			$('#longitud', contextDomicilio).attr('value',validaAtributoVacio(objetoDomicilio.longitud));
			
			$('div#msgSinDomicilio').hide();
			
			if (validaAtributoVacio(objetoDomicilio.vialidadPrimaria) == '') {
				$('span#nombreVialidadPrimaria', contextDomicilio).text('');
			} else {
				$('span#nombreVialidadPrimaria', contextDomicilio).text(validaAtributoVacio(objetoDomicilio.vialidadPrimaria.nombre));
			}
			$('span#numExteriorAlfa', contextDomicilio).text(validaAtributoVacio(objetoDomicilio.numExteriorAlf));
			$('span#numExterior', contextDomicilio).text(validaAtributoVacio(objetoDomicilio.numExterior1));
			$('span#numInteriorAlfa', contextDomicilio).text(validaAtributoVacio(objetoDomicilio.numInteriorAlf));
			$('span#numInterior', contextDomicilio).text(validaAtributoVacio(objetoDomicilio.numInterior));
			$('span#nombreAsentamiento', contextDomicilio).text(validaAtributoVacio(objetoDomicilio.asentamiento.nombre));
			$('span#nombreMunicipio', contextDomicilio).text(validaAtributoVacio(objetoDomicilio.asentamiento.localidad.municipio.nombre));
			$('span#nombreEstado', contextDomicilio).text(validaAtributoVacio(objetoDomicilio.asentamiento.localidad.municipio.entidadFederativa.nombre));
			$('span#codigoPostal', contextDomicilio).text(validaAtributoVacio(objetoDomicilio.asentamiento.codigoPostal.codigoPostal));
			
			$('#addressParticular').removeClass('hidden');
			$('#addressParticular').addClass('showElement');

		} catch (e) {}
		
		// Se ejecuta el componeten para localizar las UMFs
		$('input#cveAsentamientoAux').val(objetoDomicilio.asentamiento.clave);
		$('input#nombreAsentamientoAux').val(objetoDomicilio.asentamiento.nombre);
		$('input#localidadCveAsentamientoAux').val(objetoDomicilio.asentamiento.localidad.clave);
		$('input#municipioCveAsentamientoAux').val(objetoDomicilio.asentamiento.localidad.municipio.clave);
		$('input#estadoCveAsentamientoAux').val(objetoDomicilio.asentamiento.localidad.municipio.entidadFederativa.clave);
		$('input#cpAsentamientoAux').val(objetoDomicilio.asentamiento.codigoPostal.codigoPostal);
		$('input#tipoAsentamientoAux').val(objetoDomicilio.asentamiento.tipoAsentamiento.clave);
				
		obtenerUMFs();
		
		// Se oculta el error de campo requerido en domicilio particular y UMF
		$('span#fisica\\.domicilios\\.errors').hide();
		$('span#fisica\\.umf\\.idUMF\\.errors').hide();
	}

};

function validaAtributoVacio (atributo) {
	if (atributo == null || typeof atributo === 'undefined') {
		return "";
	} else {
		return atributo;
	}
}