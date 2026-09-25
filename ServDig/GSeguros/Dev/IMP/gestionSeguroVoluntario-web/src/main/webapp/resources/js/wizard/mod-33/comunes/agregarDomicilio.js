$.getScript('/${mvn.web.app.root}/static/resources/js/wizard/mod-33/comunes/common.js');

var _scriptDomRecortado = '/${mvn.web.app.rootDomicilios}/static/resources/js/delta/domicilios/recortado/DomicilioRecortadoCtrl.js';
var _domRecotadoCtrl = null;
$.getScript(_scriptDomRecortado, function() {
	_domRecotadoCtrl = DomicilioRecortadoCtrl;
	
	_domRecotadoCtrl.init({
		'contenedor' : 'domicilioRecortadoContainer',
		'divMensajes' : 'domicilioRecortadoErrorContainer',
		'mostrarFormulario' : true,
		'onReady' : function() {
			setSizeWithinIframe(document);
		}
	});
});

$(document).ready(function() {
        document.charset = 'ISO-8859-1';
	$('#cerrar').click(function(event) {
		event.preventDefault();
		closeWizard();
	});
	
	$('#regresar').click(function(event){
		event.preventDefault();
		$('form#previousStep').submit();
	});
	
	$('#siguientePaso').click(function(event) {
		event.preventDefault();
		agregarDomicilio(_domRecotadoCtrl.getDomicilio());
		
	});
});

function agregarDomicilio(objetoDomicilio) {
	
	var domForm = $('form#agregarDomicilioForm');

	if (objetoDomicilio != null) {
		try {

			$('#codigoPostal', domForm).attr('value',validaAtributoVacio(objetoDomicilio.codigoPostal.codigoPostal));
			$('#codigoPostalAux', domForm).attr('value',validaAtributoVacio(objetoDomicilio.codigoPostal.codigoPostal));
			
			$('#entidadFederativaClave', domForm).attr('value',validaAtributoVacio(objetoDomicilio.asentamiento.localidad.municipio.entidadFederativa.clave));
			$('#entidadFederativa', domForm).attr('value',validaAtributoVacio(objetoDomicilio.asentamiento.localidad.municipio.entidadFederativa.nombre));
			
			$('#municipioClave', domForm).attr('value',validaAtributoVacio(objetoDomicilio.asentamiento.localidad.municipio.clave));
			$('#municipio', domForm).attr('value',validaAtributoVacio(objetoDomicilio.asentamiento.localidad.municipio.nombre));
			
			$('#localidadClave', domForm).attr('value',validaAtributoVacio(objetoDomicilio.asentamiento.localidad.clave));
			$('#localidad', domForm).attr('value',validaAtributoVacio(objetoDomicilio.asentamiento.localidad.nombre));
			
			$('#asentamientoClave', domForm).attr('value',validaAtributoVacio(objetoDomicilio.asentamiento.clave));
			$('#asentamiento', domForm).attr('value',validaAtributoVacio(objetoDomicilio.asentamiento.nombre));
			
			$('#numeroExteriorAlfanumerico', domForm).attr('value',validaAtributoVacio(objetoDomicilio.numExteriorAlf));
			$('#numeroInteriorAlfanumerico', domForm).attr('value',validaAtributoVacio(objetoDomicilio.numInteriorAlf));
			
			$('#calle', domForm).attr('value',validaAtributoVacio(objetoDomicilio.calle));
			
			if (validaAtributoVacio(objetoDomicilio.vialidadPrimaria) == '') {
				$('#vialidadPrimaria', domForm).attr('value','');
				$('#vialidadPrimaria\\.clave', domForm).attr('value','');
			} else {
				$('#vialidadPrimaria', domForm).attr('value',validaAtributoVacio(objetoDomicilio.vialidadPrimaria.nombre));
				$('#vialidadPrimaria\\.clave', domForm).attr('value',validaAtributoVacio(objetoDomicilio.vialidadPrimaria.clave));
			}
			
			domForm.submit();
						
		} catch (e) {
			$.error(e);
		}		
	}
};

function validaAtributoVacio (atributo) {
	if (atributo == null || typeof atributo === 'undefined') {
		return "";
	} else {
		return atributo;
	}
}