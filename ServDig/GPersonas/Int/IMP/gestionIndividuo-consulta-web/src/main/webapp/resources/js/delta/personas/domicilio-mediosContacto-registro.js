var isFiscal;

$(document).ready(function() {
	
	// se iniciliza el componente del acordeon con la opcion 'autoHeight: false' para que cada DIV colapsable tenga la altura de acuerdo a su contenido
	$('#acordeon').accordion({autoHeight: false});
		
	// Incluimos el JS de Domicilios.
	$.getScript("/gestionDomicilios-web/static/resources/js/delta/domicilios/Domicilio.js", function(){
		DomicilioCtrl.init('domicilioRegistrar');
		DomicilioCtrl.setOnCloseCallback(fnOnCloseDomicilio);	
	});
	
	$('#domicilio').click(function(){
		isFiscal = false;
		DomicilioCtrl.localizar();
	});
	
	$('#btnDomFiscal').click(function(){
		isFiscal = true;
		
		DomicilioCtrl.init('domicilioRegistrar');
		DomicilioCtrl.setOnCloseCallback(fnOnCloseDomicilio);
		
		DomicilioCtrl.localizar();
	});
		
});

//Funcion de callback para el llenado de la informacion del domicilio
var fnOnCloseDomicilio = function(){
	var objetoDomicilio = this;
	var contextDomicilio = $('div#domicilioDiv');
	
	var tipo = isFiscal ? "Fiscal" : "";
	
	limpiarFormaOcultaDomicilio(tipo, contextDomicilio);
	
	try{
		
		$('#entidadFederativa' + tipo, contextDomicilio).attr('value', validaContenidoElemento(objetoDomicilio.asentamiento.localidad.municipio.entidadFederativa.nombre));
		$('#municipio' + tipo, contextDomicilio).attr('value', validaContenidoElemento(objetoDomicilio.asentamiento.localidad.municipio.nombre));
		$('#localidad' + tipo, contextDomicilio).attr('value', validaContenidoElemento(objetoDomicilio.asentamiento.localidad.nombre));
		$('#asentamiento' + tipo, contextDomicilio).attr('value', validaContenidoElemento(objetoDomicilio.asentamiento.nombre));
		$('#tipoAsentamiento' + tipo, contextDomicilio).attr('value', validaContenidoElemento(objetoDomicilio.asentamiento.tipoAsentamiento.descripcion));
		
		$('#numeroExteriorPrincipal' + tipo, contextDomicilio).attr('value', validaContenidoElemento(objetoDomicilio.numExterior1));
		$('#numeroExteriorAlfanumerico' + tipo, contextDomicilio).attr('value', validaContenidoElemento(objetoDomicilio.numExteriorAlf));
		$('#numeroExteriorSecundario' + tipo, contextDomicilio).attr('value', validaContenidoElemento(objetoDomicilio.numExterior2));
		$('#numeroInterior' + tipo, contextDomicilio).attr('value', validaContenidoElemento(objetoDomicilio.numInterior));
		$('#numeroInteriorAlfanumerico' + tipo, contextDomicilio).attr('value', validaContenidoElemento(objetoDomicilio.numInteriorAlf));
		
		$('#claveEntidadFederativa' + tipo).attr('value', validaContenidoElemento(objetoDomicilio.asentamiento.localidad.municipio.entidadFederativa.clave));
		$('#claveMunicipio' + tipo).attr('value', validaContenidoElemento(objetoDomicilio.asentamiento.localidad.municipio.clave));
		$('#claveLocalidad' + tipo).attr('value', validaContenidoElemento(objetoDomicilio.asentamiento.localidad.clave));
		$('#claveAsentamiento' + tipo).attr('value', validaContenidoElemento(objetoDomicilio.asentamiento.clave));
		
		//La descripcion no es requerida por lo tanto si no se captura
		// al realizar el UpperCase falla.
		try{
			//Descripcion del domicilio
			$('#descripcion' + tipo).val(validaContenidoElemento(objetoDomicilio.descripcion.toUpperCase()));
		}catch (e) {
		}
		/*
		 * Procesamiento de las vialidades
		 */
		try{
			
			try{
				
				//Nombres
				$('#vialidadPrimaria' + tipo, contextDomicilio).attr('value', validaContenidoElemento(objetoDomicilio.vialidadPrimaria.nombre));
				$('#vialidadReferenciaPrimaria' + tipo, contextDomicilio).attr('value', validaContenidoElemento(objetoDomicilio.vialidadReferenciaPrimaria.nombre));
				$('#vialidadReferenciaSecundaria' + tipo, contextDomicilio).attr('value', validaContenidoElemento(objetoDomicilio.vialidadReferenciaSecundaria.nombre));
				$('#vialidadReferenciaPosterior' + tipo, contextDomicilio).attr('value', validaContenidoElemento(objetoDomicilio.vialidadReferenciaPosterior.nombre));
				
			}catch (e) {
			}
			
			try{
				
				//Claves
				$('#vialidadPrimaria' + tipo + '\\.clave', contextDomicilio).attr('value', validaContenidoElemento(objetoDomicilio.vialidadPrimaria.clave));
				$('#vialidadReferenciaPrimaria' + tipo + '\\.clave', contextDomicilio).attr('value', validaContenidoElemento(objetoDomicilio.vialidadReferenciaPrimaria.clave));
				$('#vialidadReferenciaSecundaria' + tipo + '\\.clave', contextDomicilio).attr('value', validaContenidoElemento(objetoDomicilio.vialidadReferenciaSecundaria.clave));
				$('#vialidadReferenciaPosterior' + tipo + '\\.clave', contextDomicilio).attr('value', validaContenidoElemento(objetoDomicilio.vialidadReferenciaPosterior.clave));
				
			}catch (e) {
				// TODO: handle exception
			}
			
			try{
				//Tipos de vialidad
				$('#tipoVialidadPrimaria' + tipo, contextDomicilio).attr('value', validaContenidoElemento(objetoDomicilio.vialidadPrimaria.tipoVialidad.descripcion));
				$('#tipoVialidadReferenciaPrimaria' + tipo, contextDomicilio).attr('value', validaContenidoElemento(objetoDomicilio.vialidadReferenciaPrimaria.tipoVialidad.descripcion));
				$('#tipoVialidadReferenciaSecundaria' + tipo, contextDomicilio).attr('value', validaContenidoElemento(objetoDomicilio.vialidadReferenciaSecundaria.tipoVialidad.descripcion));
				$('#tipoVialidadReferenciaPosterior' + tipo, contextDomicilio).attr('value', validaContenidoElemento(objetoDomicilio.vialidadReferenciaPosterior.tipoVialidad.descripcion));
				
				
			}catch (e) {
			}
			
		}catch (e) {
		}
		
		// Se coloco el codigo postal al final por que en caso de que el domicilio ubicado no tenga codigo
		//no truene.
		$('#codigoPostal' + tipo , contextDomicilio).attr('value', validaContenidoElemento(objetoDomicilio.codigoPostal.codigoPostal));
		
		if (isFiscal) {
			if (validaContenidoElemento(objetoDomicilio.vialidadPrimaria) == '') {
				$('span#nombreVialidadPrimaria', contextDomicilio).text('');
			} else {
				$('span#nombreVialidadPrimaria', contextDomicilio).text(validaContenidoElemento(objetoDomicilio.vialidadPrimaria.nombre));
			}
			$('span#numExteriorAlfa', contextDomicilio).text(validaContenidoElemento(objetoDomicilio.numExteriorAlf));
			$('span#numExterior', contextDomicilio).text(validaContenidoElemento(objetoDomicilio.numExterior1));
			$('span#numInteriorAlfa', contextDomicilio).text(validaContenidoElemento(objetoDomicilio.numInteriorAlf));
			$('span#numInterior', contextDomicilio).text(validaContenidoElemento(objetoDomicilio.numInterior));
			$('span#nombreAsentamiento', contextDomicilio).text(validaContenidoElemento(objetoDomicilio.asentamiento.nombre));
			$('span#nombreMunicipio', contextDomicilio).text(validaContenidoElemento(objetoDomicilio.asentamiento.localidad.municipio.nombre));
			$('span#nombreEstado', contextDomicilio).text(validaContenidoElemento(objetoDomicilio.asentamiento.localidad.municipio.entidadFederativa.nombre));
			$('span#codigoPostal', contextDomicilio).text(validaContenidoElemento(objetoDomicilio.asentamiento.codigoPostal.codigoPostal));
			
			$('#addressParticular').removeClass('hidden');
			$('#addressParticular').addClass('showElement');
		}
		
	}catch(err){
	}
	
};

function validaContenidoElemento(contenido){
	if(typeof contenido === 'undefined'){
		return "";
	}else {
		return contenido;
	}
}

function limpiarFormaOcultaDomicilio(tipo, contextDomicilio) {
	$('#entidadFederativa' + tipo, contextDomicilio).attr('value', null);
	$('#municipio' + tipo, contextDomicilio).attr('value', null);
	$('#localidad' + tipo, contextDomicilio).attr('value', null);
	$('#asentamiento' + tipo, contextDomicilio).attr('value', null);
	$('#tipoAsentamiento' + tipo, contextDomicilio).attr('value', null);
	
	$('#numeroExteriorPrincipal' + tipo, contextDomicilio).attr('value', null);
	$('#numeroExteriorAlfanumerico' + tipo, contextDomicilio).attr('value', null);
	$('#numeroExteriorSecundario' + tipo, contextDomicilio).attr('value', null);
	$('#numeroInterior' + tipo, contextDomicilio).attr('value', null);
	$('#numeroInteriorAlfanumerico' + tipo, contextDomicilio).attr('value', null);
	
	$('#claveEntidadFederativa' + tipo).attr('value', null);
	$('#claveMunicipio' + tipo).attr('value', null);
	$('#claveLocalidad' + tipo).attr('value', null);
	$('#claveAsentamiento' + tipo).attr('value', null);
		
	$('#descripcion' + tipo).val(null);
		
	$('#vialidadPrimaria' + tipo, contextDomicilio).attr('value', null);
	$('#vialidadReferenciaPrimaria' + tipo, contextDomicilio).attr('value', null);
	$('#vialidadReferenciaSecundaria' + tipo, contextDomicilio).attr('value', null);
	$('#vialidadReferenciaPosterior' + tipo, contextDomicilio).attr('value', null);

	$('#vialidadPrimaria' + tipo + '\\.clave', contextDomicilio).attr('value', null);
	$('#vialidadReferenciaPrimaria' + tipo + '\\.clave', contextDomicilio).attr('value', null);
	$('#vialidadReferenciaSecundaria' + tipo + '\\.clave', contextDomicilio).attr('value', null);
	$('#vialidadReferenciaPosterior' + tipo + '\\.clave', contextDomicilio).attr('value', null);
	
	$('#tipoVialidadPrimaria' + tipo, contextDomicilio).attr('value', null);
	$('#tipoVialidadReferenciaPrimaria' + tipo, contextDomicilio).attr('value', null);
	$('#tipoVialidadReferenciaSecundaria' + tipo, contextDomicilio).attr('value', null);
	$('#tipoVialidadReferenciaPosterior' + tipo, contextDomicilio).attr('value', null);
		
	$('#codigoPostal' + tipo , contextDomicilio).attr('value', null);
}