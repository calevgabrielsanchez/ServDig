
$(document).ready(function() {

	deshabilitarCamposDomicilio();
	deshabilitarCamposMediosDeContacto();
	
	// se iniciliza el componente del acordeon con la opcion 'autoHeight: false' para que cada DIV colapsable tenga la altura de acuerdo a su contenido
	$('#acordeon').accordion({autoHeight: false});
		
	// Incluimos el JS de Domicilios.
	$.getScript("/gestionDomicilios-web/static/resources/js/delta/domicilios/Domicilio.js", function(){
		DomicilioCtrl.init('domicilioRegistrar');
		DomicilioCtrl.setOnCloseCallback(fnOnCloseDomicilio);	
	});
	
	$('#domicilio').click(function(){
		DomicilioCtrl.localizar();
	});
	
	
	// Incluimos el JS de Medios de contacto
	$.getScript("/gestionMediosContacto-web/static/resources/js/delta/mediosContacto/MedioContacto.js", function(){
		MedioContactoCtrl.init('mediosContactoRegistrar');
		MedioContactoCtrl.setOnCloseCallback(fnOnCloseMediosContacto);		
	});
	
	$('#mediosContacto').click(function(){
		MedioContactoCtrl.registrar();
	});
	
});

function deshabilitarCamposDomicilio(){
	
	// Ponemos los campos del domicilio como disabled
	$('#codigoPostal').fadeTo('slow', 0.5);
	$('#codigoPostal').attr("disabled", "disabled");

	$('#entidadFederativa').fadeTo('slow', 0.5);
	$('#entidadFederativa').attr("disabled", "disabled");
	
	$('#municipio').fadeTo('slow', 0.5);
	$('#municipio').attr("disabled", "disabled");
	
	$('#localidad').fadeTo('slow', 0.5);
	$('#localidad').attr("disabled", "disabled");
	
	$('#tipoAsentamiento').fadeTo('slow', 0.5);
	$('#tipoAsentamiento').attr("disabled", "disabled");
	
	$('#asentamiento').fadeTo('slow', 0.5);
	$('#asentamiento').attr("disabled", "disabled");
	
	$('#tipoVialidadPrimaria').fadeTo('slow', 0.5);
	$('#tipoVialidadPrimaria').attr("disabled", "disabled");
	
	$('#vialidadPrimaria').fadeTo('slow', 0.5);
	$('#vialidadPrimaria').attr("disabled", "disabled");
	
	$('#numeroExteriorPrincipal').fadeTo('slow', 0.5);
	$('#numeroExteriorPrincipal').attr("disabled", "disabled");
	
	$('#numeroExteriorAlfanumerico').fadeTo('slow', 0.5);
	$('#numeroExteriorAlfanumerico').attr("disabled", "disabled");
	
	$('#numeroExteriorSecundario').fadeTo('slow', 0.5);
	$('#numeroExteriorSecundario').attr("disabled", "disabled");
	
	$('#numeroInterior').fadeTo('slow', 0.5);
	$('#numeroInterior').attr("disabled", "disabled");
	
	$('#numeroInteriorAlfanumerico').fadeTo('slow', 0.5);
	$('#numeroInteriorAlfanumerico').attr("disabled", "disabled");
	
	$('#tipoVialidadReferenciaPrimaria').fadeTo('slow', 0.5);
	$('#tipoVialidadReferenciaPrimaria').attr("disabled", "disabled");
	
	$('#vialidadReferenciaPrimaria').fadeTo('slow', 0.5);
	$('#vialidadReferenciaPrimaria').attr("disabled", "disabled");
	
	$('#tipoVialidadReferenciaSecundaria').fadeTo('slow', 0.5);
	$('#tipoVialidadReferenciaSecundaria').attr("disabled", "disabled");
	
	$('#vialidadReferenciaSecundaria').fadeTo('slow', 0.5);
	$('#vialidadReferenciaSecundaria').attr("disabled", "disabled");
	
	$('#tipoVialidadReferenciaPosterior').fadeTo('slow', 0.5);
	$('#tipoVialidadReferenciaPosterior').attr("disabled", "disabled");
	
	$('#vialidadReferenciaPosterior').fadeTo('slow', 0.5);
	$('#vialidadReferenciaPosterior').attr("disabled", "disabled");
	
	$('#descripcion').fadeTo('slow', 0.5);
	$('#descripcion').attr("disabled", "disabled");
	
}

function deshabilitarCamposMediosDeContacto(){
	
	// Los campos de Medios de Contacto tambien los ponemos como disabled 
 	$('#numeroTelefonicoParticular').fadeTo('slow', 0.5);
 	$('#numeroTelefonicoParticular').attr("disabled", "disabled");
 	
 	$('#claveLada').fadeTo('slow', 0.5);
	$('#claveLada').attr("disabled", "disabled");
	
	$('#extension').fadeTo('slow', 0.5);
	$('#extension').attr("disabled", "disabled");
	
	$('#numeroTelefonicoMovil').fadeTo('slow', 0.5);
	$('#numeroTelefonicoMovil').attr("disabled", "disabled");
	
	$('#correoElectronico').fadeTo('slow', 0.5);
	$('#correoElectronico').attr("disabled", "disabled");
	
}

//Funcion de callback para el llenado de la informacion del domicilio
var fnOnCloseDomicilio = function(){
	var objetoDomicilio = this;
	var contextDomicilio = $('div#domicilioDiv');
	
	try{
		
		$('#entidadFederativa', contextDomicilio).attr('value', objetoDomicilio.asentamiento.localidad.municipio.entidadFederativa.nombre);
		$('#municipio', contextDomicilio).attr('value', objetoDomicilio.asentamiento.localidad.municipio.nombre);
		$('#localidad', contextDomicilio).attr('value', objetoDomicilio.asentamiento.localidad.nombre);
		$('#asentamiento', contextDomicilio).attr('value', objetoDomicilio.asentamiento.nombre);
		$('#tipoAsentamiento', contextDomicilio).attr('value', objetoDomicilio.asentamiento.tipoAsentamiento.descripcion);
		
		$('#numeroExteriorPrincipal', contextDomicilio).attr('value', objetoDomicilio.numExterior1);
		$('#numeroExteriorAlfanumerico', contextDomicilio).attr('value', objetoDomicilio.numExteriorAlf);
		$('#numeroExteriorSecundario', contextDomicilio).attr('value', objetoDomicilio.numExterior2);
		$('#numeroInterior', contextDomicilio).attr('value', objetoDomicilio.numInterior);
		$('#numeroInteriorAlfanumerico', contextDomicilio).attr('value', objetoDomicilio.numInteriorAlf);
		
		$('#claveEntidadFederativa').attr('value', objetoDomicilio.asentamiento.localidad.municipio.entidadFederativa.clave);
		$('#claveMunicipio').attr('value', objetoDomicilio.asentamiento.localidad.municipio.clave);
		$('#claveLocalidad').attr('value', objetoDomicilio.asentamiento.localidad.clave);
		$('#claveAsentamiento').attr('value', objetoDomicilio.asentamiento.clave);
		
		
		//La descripcion no es requerida por lo tanto si no se captura
		// al realizar el UpperCase falla.
		try{
			//Descripcion del domicilio
			$('#descripcion').val(objetoDomicilio.descripcion.toUpperCase());
		}catch (e) {
			// TODO: handle exception
		}
		/*
		 * Procesamiento de las vialidades
		 */
		try{
			
			try{
				
				//Nombres
				$('#vialidadPrimaria', contextDomicilio).attr('value', objetoDomicilio.vialidadPrimaria.nombre);
				$('#vialidadReferenciaPrimaria', contextDomicilio).attr('value', objetoDomicilio.vialidadReferenciaPrimaria.nombre);
				$('#vialidadReferenciaSecundaria', contextDomicilio).attr('value', objetoDomicilio.vialidadReferenciaSecundaria.nombre);
				$('#vialidadReferenciaPosterior', contextDomicilio).attr('value', objetoDomicilio.vialidadReferenciaPosterior.nombre);
				
			}catch (e) {
				// TODO: handle exception
			}
			
			try{
				
				//Claves
				$('#vialidadPrimaria\\.clave', contextDomicilio).attr('value', objetoDomicilio.vialidadPrimaria.clave);
				$('#vialidadReferenciaPrimaria\\.clave', contextDomicilio).attr('value', objetoDomicilio.vialidadReferenciaPrimaria.clave);
				$('#vialidadReferenciaSecundaria\\.clave', contextDomicilio).attr('value', objetoDomicilio.vialidadReferenciaSecundaria.clave);
				$('#vialidadReferenciaPosterior\\.clave', contextDomicilio).attr('value', objetoDomicilio.vialidadReferenciaPosterior.clave);
				
			}catch (e) {
				// TODO: handle exception
			}
			
			try{
				//Tipos de vialidad
				$('#tipoVialidadPrimaria', contextDomicilio).attr('value', objetoDomicilio.vialidadPrimaria.tipoVialidad.descripcion);
				$('#tipoVialidadReferenciaPrimaria', contextDomicilio).attr('value', objetoDomicilio.vialidadReferenciaPrimaria.tipoVialidad.descripcion);
				$('#tipoVialidadReferenciaSecundaria', contextDomicilio).attr('value', objetoDomicilio.vialidadReferenciaSecundaria.tipoVialidad.descripcion);
				$('#tipoVialidadReferenciaPosterior', contextDomicilio).attr('value', objetoDomicilio.vialidadReferenciaPosterior.tipoVialidad.descripcion);
				
				
			}catch (e) {
				// TODO: handle exception
			}
			
		}catch (e) {
			// TODO: handle exception
		}
		
		// Se coloco el codigo postal al final por que en caso de que el domicilio ubicado no tenga codigo
		//no truene.
		$('#codigoPostal' , contextDomicilio).attr('value', objetoDomicilio.codigoPostal.codigoPostal);
	}catch(err){
	}
}

// Funcion de callback para el llenado de la informacion de los medios de contacto
var fnOnCloseMediosContacto = function(){
	var objetoMediosContacto = this;
	var contextMedios = $('div#mediosConactoDiv');
	try{
		//Telefono particular
		$('#numeroTelefonicoParticular' , contextMedios).attr('value', objetoMediosContacto.telefonoFijo.numero);
		$('#claveLada', contextMedios).attr('value', objetoMediosContacto.telefonoFijo.claveLada);
		$('#extension', contextMedios).attr('value', objetoMediosContacto.telefonoFijo.extension);
	}catch(err){
	}

	try{
		//Telefono movil
		$('#numeroTelefonicoMovil', contextMedios).attr('value', objetoMediosContacto.telefonoMovil.numero);
	}catch(err){
	}

	try{
		//Correo electronico
		$('#correoElectronico', contextMedios).attr('value', objetoMediosContacto.correoElectronico.correo);
	}catch(err){
	}
}
