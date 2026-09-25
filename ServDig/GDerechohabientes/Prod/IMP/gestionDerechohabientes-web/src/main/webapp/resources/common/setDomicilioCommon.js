/**
 * Script que se genera para setear el domicilio dentro de un formulario 
 * tomando en cuenta que el formulario cuenta con la estructura del objeto domicilio
 */

function setDomicilioCommon(domicilio, contenedor) {
	
	var idContenedor = (contenedor!=undefined && contenedor != null) ? ("#"+contenedor+" ") : "";
	//-------------------------------
	//Verificamos si el domicilio viene y no es nulo
	//---------------------------------
	if(domicilio != undefined && domicilio != null && !jQuery.isEmptyObject(domicilio)) {
		//Se setea la clave en caso de existir
		$(idContenedor + '#domicilio\\.clave').val(domicilio.clave);
		//Se setea el codigo postal
		$(idContenedor + '#domicilio\\.codigoPostal\\.codigoPostal').val(domicilio.codigoPostal.codigoPostal);
		
		//--------------------------------------------------
		//Se setea toda la informacion relacionada con el asentamiento
		//---------------------------------------------------
		$(idContenedor + '#domicilio\\.asentamiento\\.clave').val(domicilio.asentamiento.clave);
		$(idContenedor + '#domicilio\\.asentamiento\\.nombre').val(domicilio.asentamiento.nombre);
		$(idContenedor + '#domicilio\\.asentamiento\\.localidad\\.clave').val(domicilio.asentamiento.localidad.clave);
		$(idContenedor + '#domicilio\\.asentamiento\\.localidad\\.nombre').val(domicilio.asentamiento.localidad.nombre);
		$(idContenedor + '#domicilio\\.asentamiento\\.localidad\\.municipio\\.clave').val(domicilio.asentamiento.localidad.municipio.clave);
		$(idContenedor + '#domicilio\\.asentamiento\\.localidad\\.municipio\\.nombre').val(domicilio.asentamiento.localidad.municipio.nombre);
		$(idContenedor + '#domicilio\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave').val(domicilio.asentamiento.localidad.municipio.entidadFederativa.clave);
		$(idContenedor + '#domicilio\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.nombre').val(domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre);
		
		/*
		 * Se setea toda la informacion relacionada con los numetos
		 */
		$(idContenedor + '#domicilio\\.numExterior1').val(domicilio.numExterior1);
		$(idContenedor + '#domicilio\\.numExteriorAlf').val(domicilio.numExteriorAlf);
		$(idContenedor + '#domicilio\\.numInterior').val(domicilio.numInterior);
		$(idContenedor + '#domicilio\\.numInteriorAlf').val(domicilio.numInteriorAlf);
		$(idContenedor + '#domicilio\\.numExterior2').val(domicilio.numExterior2);
		
		/*
		 * Se verifican las vialidades
		 */
		if(domicilio.vialidadPrimaria != null && domicilio.vialidadPrimaria != undefined) {
			$(idContenedor + '#domicilio\\.vialidadPrimaria\\.clave').val(domicilio.vialidadPrimaria.clave);
			$(idContenedor + '#domicilio\\.vialidadPrimaria\\.nombre').val(domicilio.vialidadPrimaria.nombre);
			
			if(domicilio.vialidadPrimaria.tipoVialidad != undefined && domicilio.vialidadPrimaria.tipoVialidad != null) {
				$(idContenedor + '#domicilio\\.vialidadPrimaria\\.tipoVialidad\\.clave').val(domicilio.vialidadPrimaria.tipoVialidad.clave);
				$(idContenedor + '#domicilio\\.vialidadPrimaria\\.tipoVialidad\\.descripcion').val(domicilio.vialidadPrimaria.tipoVialidad.descripcion);
			}
		} else {
			$(idContenedor + '#domicilio\\.vialidadPrimaria\\.clave').val("");
			$(idContenedor + '#domicilio\\.vialidadPrimaria\\.nombre').val("");
			$(idContenedor + '#domicilio\\.vialidadPrimaria\\.tipoVialidad\\.clave').val("");
			$(idContenedor + '#domicilio\\.vialidadPrimaria\\.tipoVialidad\\.descripcion').val("");
		}
		
		if(domicilio.vialidadReferenciaPrimaria != null && domicilio.vialidadReferenciaPrimaria != undefined) {
			$(idContenedor + '#domicilio\\.vialidadReferenciaPrimaria\\.clave').val(domicilio.vialidadReferenciaPrimaria.clave);
			$(idContenedor + '#domicilio\\.vialidadReferenciaPrimaria\\.nombre').val(domicilio.vialidadReferenciaPrimaria.nombre);
			// Si el tipo de vialidad vienen nulo no ponemos esos campos
			if(domicilio.vialidadReferenciaPrimaria.tipoVialidad != undefined && domicilio.vialidadReferenciaPrimaria.tipoVialidad != null) {
				$(idContenedor + '#domicilio\\.vialidadReferenciaPrimaria\\.tipoVialidad\\.clave').val(domicilio.vialidadReferenciaPrimaria.tipoVialidad.clave);
				$(idContenedor + '#domicilio\\.vialidadReferenciaPrimaria\\.tipoVialidad\\.descripcion').val(domicilio.vialidadReferenciaPrimaria.tipoVialidad.descripcion);
			}
		} else {
			$(idContenedor + '#domicilio\\.vialidadReferenciaPrimaria\\.clave').val("");
			$(idContenedor + '#domicilio\\.vialidadReferenciaPrimaria\\.nombre').val("");
			$(idContenedor + '#domicilio\\.vialidadReferenciaPrimaria\\.tipoVialidad\\.clave').val("");
			$(idContenedor + '#domicilio\\.vialidadReferenciaPrimaria\\.tipoVialidad\\.descripcion').val("");
		}
		
		if(domicilio.vialidadReferenciaSecundaria != null && domicilio.vialidadReferenciaSecundaria != undefined) {
			$(idContenedor + '#domicilio\\.vialidadReferenciaSecundaria\\.clave').val(domicilio.vialidadReferenciaSecundaria.clave);
			$(idContenedor + '#domicilio\\.vialidadReferenciaSecundaria\\.nombre').val(domicilio.vialidadReferenciaSecundaria.nombre);
			// Si el tipo de vialidad vienen nulo no ponemos esos campos
			if(idContenedor + domicilio.vialidadReferenciaSecundaria.tipoVialidad != undefined && domicilio.vialidadReferenciaSecundaria.tipoVialidad != null) {
				$('#domicilio\\.vialidadReferenciaSecundaria\\.tipoVialidad\\.clave').val(domicilio.vialidadReferenciaSecundaria.tipoVialidad.clave);
				$('#domicilio\\.vialidadReferenciaSecundaria\\.tipoVialidad\\.descripcion').val(domicilio.vialidadReferenciaSecundaria.tipoVialidad.descripcion);
			}
		} else {
			$(idContenedor + '#domicilio\\.vialidadReferenciaSecundaria\\.clave').val("");
			$(idContenedor + '#domicilio\\.vialidadReferenciaSecundaria\\.nombre').val("");
			$(idContenedor + '#domicilio\\.vialidadReferenciaSecundaria\\.tipoVialidad\\.clave').val("");
			$(idContenedor + '#domicilio\\.vialidadReferenciaSecundaria\\.tipoVialidad\\.descripcion').val("");
		}
		
		if(domicilio.vialidadReferenciaPosterior != null && domicilio.vialidadReferenciaPosterior != undefined) {
			$(idContenedor + '#domicilio\\.vialidadReferenciaPosterior\\.clave').val(domicilio.vialidadReferenciaPosterior.clave);
			$(idContenedor + '#domicilio\\.vialidadReferenciaPosterior\\.nombre').val(domicilio.vialidadReferenciaPosterior.nombre);
			// Si el tipo de vialidad vienen nulo no ponemos esos campos
			if(domicilio.vialidadReferenciaPosterior.tipoVialidad != undefined && domicilio.vialidadReferenciaPosterior.tipoVialidad != null) {
				$(idContenedor + '#domicilio\\.vialidadReferenciaPosterior\\.tipoVialidad\\.clave').val(domicilio.vialidadReferenciaPosterior.tipoVialidad.clave);
				$(idContenedor + '#domicilio\\.vialidadReferenciaPosterior\\.tipoVialidad\\.descripcion').val(domicilio.vialidadReferenciaPosterior.tipoVialidad.descripcion);
			} 
		} else {
			$(idContenedor + '#domicilio\\.vialidadReferenciaPosterior\\.clave').val("");
			$(idContenedor + '#domicilio\\.vialidadReferenciaPosterior\\.nombre').val("");
			$(idContenedor + '#domicilio\\.vialidadReferenciaPosterior\\.tipoVialidad\\.clave').val("");
			$(idContenedor + '#domicilio\\.vialidadReferenciaPosterior\\.tipoVialidad\\.descripcion').val("");
		}
		
		$(idContenedor + "#domicilio\\.calle").val(domicilio.calle);
		$(idContenedor + "#domicilio\\.tipoBusquedaVialidad").val(domicilio.tipoBusquedaVialidad);
		
		if(domicilio.domicilioCarretera != undefined) {
			$(idContenedor + "#domicilio\\.domicilioCarretera\\.terminoGeneral\\.descripcion").val(domicilio.domicilioCarretera.terminoGeneral.descripcion);
			$(idContenedor + "#domicilio\\.domicilioCarretera\\.terminoGeneral\\.clave").val(domicilio.domicilioCarretera.terminoGeneral.clave);
			$(idContenedor + "#domicilio\\.domicilioCarretera\\.derechoTransito\\.descripcion").val(domicilio.domicilioCarretera.derechoTransito.descripcion);
			$(idContenedor + "#domicilio\\.domicilioCarretera\\.derechoTransito\\.clave").val(domicilio.domicilioCarretera.derechoTransito.clave);
			$(idContenedor + "#domicilio\\.domicilioCarretera\\.origen").val(domicilio.domicilioCarretera.origen);
			$(idContenedor + "#domicilio\\.domicilioCarretera\\.destino").val(domicilio.domicilioCarretera.destino);
			$(idContenedor + "#domicilio\\.domicilioCarretera\\.administracion\\.descripcion").val(domicilio.domicilioCarretera.administracion.descripcion);
			$(idContenedor + "#domicilio\\.domicilioCarretera\\.administracion\\.clave").val(domicilio.domicilioCarretera.administracion.clave);
			$(idContenedor + "#domicilio\\.domicilioCarretera\\.cadenamiento").val(domicilio.domicilioCarretera.cadenamiento);
			$(idContenedor + "#domicilio\\.domicilioCarretera\\.codigoCarretera").val(domicilio.domicilioCarretera.codigoCarretera);
		} else{
			$(idContenedor + "#domicilio\\.domicilioCarretera\\.terminoGeneral\\.descripcion").val('');
			$(idContenedor + "#domicilio\\.domicilioCarretera\\.terminoGeneral\\.clave").val('');
			$(idContenedor + "#domicilio\\.domicilioCarretera\\.derechoTransito\\.descripcion").val('');
			$(idContenedor + "#domicilio\\.domicilioCarretera\\.derechoTransito\\.clave").val('');
			$(idContenedor + "#domicilio\\.domicilioCarretera\\.origen").val('');
			$(idContenedor + "#domicilio\\.domicilioCarretera\\.destino").val('');
			$(idContenedor + "#domicilio\\.domicilioCarretera\\.administracion\\.descripcion").val('');
			$(idContenedor + "#domicilio\\.domicilioCarretera\\.administracion\\.clave").val('');
			$(idContenedor + "#domicilio\\.domicilioCarretera\\.cadenamiento").val('');
			$(idContenedor + "#domicilio\\.domicilioCarretera\\.codigoCarretera").val('');
		}
		
		if(domicilio.domicilioCamino != undefined) {
			$(idContenedor + "#domicilio\\.domicilioCamino\\.terminoGeneral\\.descripcion").val(domicilio.domicilioCamino.terminoGeneral.descripcion);
			$(idContenedor + "#domicilio\\.domicilioCamino\\.terminoGeneral\\.clave").val(domicilio.domicilioCamino.terminoGeneral.clave);
			$(idContenedor + "#domicilio\\.domicilioCamino\\.margen\\.descripcion").val(domicilio.domicilioCamino.margen.descripcion);
			$(idContenedor + "#domicilio\\.domicilioCamino\\.margen\\.clave").val(domicilio.domicilioCamino.margen.clave);
			$(idContenedor + "#domicilio\\.domicilioCamino\\.origen").val(domicilio.domicilioCamino.origen);
			$(idContenedor + "#domicilio\\.domicilioCamino\\.destino").val(domicilio.domicilioCamino.destino);
			$(idContenedor + "#domicilio\\.domicilioCamino\\.cadenamiento").val(domicilio.domicilioCamino.cadenamiento);
		} else {
			$(idContenedor + "#domicilio\\.domicilioCamino\\.terminoGeneral\\.descripcion").val('');
			$(idContenedor + "#domicilio\\.domicilioCamino\\.terminoGeneral\\.clave").val('');
			$(idContenedor + "#domicilio\\.domicilioCamino\\.margen\\.descripcion").val('');
			$(idContenedor + "#domicilio\\.domicilioCamino\\.margen\\.clave").val('');
			$(idContenedor + "#domicilio\\.domicilioCamino\\.origen").val('');
			$(idContenedor + "#domicilio\\.domicilioCamino\\.destino").val('');
			$(idContenedor + "#domicilio\\.domicilioCamino\\.cadenamiento").val('');
		}
	}
}

var limpiarDatosDomicilio = function() {
	//Se setea la clave en caso de existir
	$('#domicilio\\.clave').val("");
	//Se setea el codigo postal
	$('#domicilio\\.codigoPostal\\.codigoPostal').val("");
	//--------------------------------------------------
	//Se setea toda la informacion relacionada con el asentamiento
	//---------------------------------------------------
	$('#domicilio\\.asentamiento\\.clave').val("");
	$('#domicilio\\.asentamiento\\.nombre').val("");
	$('#domicilio\\.asentamiento\\.localidad\\.clave').val("");
	$('#domicilio\\.asentamiento\\.localidad\\.nombre').val("");
	$('#domicilio\\.asentamiento\\.localidad\\.municipio\\.clave').val("");
	$('#domicilio\\.asentamiento\\.localidad\\.municipio\\.nombre').val("");
	$('#domicilio\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave').val("");
	$('#domicilio\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.nombre').val("");
	
	/*
	 * Se setea toda la informacion relacionada con los numetos
	 */
	$('#domicilio\\.numExterior1').val("");
	$('#domicilio\\.numExteriorAlf').val("");
	$('#domicilio\\.numInterior').val("");
	$('#domicilio\\.numInteriorAlf').val("");
	$('#domicilio\\.numExterior2').val("");
	
	$('#domicilio\\.vialidadPrimaria\\.clave').val("");
	$('#domicilio\\.vialidadPrimaria\\.nombre').val("");
	$('#domicilio\\.vialidadPrimaria\\.tipoVialidad\\.clave').val("");
	$('#domicilio\\.vialidadPrimaria\\.tipoVialidad\\.descripcion').val("");
	
	$('#domicilio\\.vialidadReferenciaPrimaria\\.clave').val("");
	$('#domicilio\\.vialidadReferenciaPrimaria\\.nombre').val("");
	$('#domicilio\\.vialidadReferenciaPrimaria\\.tipoVialidad\\.clave').val("");
	$('#domicilio\\.vialidadReferenciaPrimaria\\.tipoVialidad\\.descripcion').val("");
	
	$('#domicilio\\.vialidadReferenciaSecundaria\\.clave').val("");
	$('#domicilio\\.vialidadReferenciaSecundaria\\.nombre').val("");
	$('#domicilio\\.vialidadReferenciaSecundaria\\.tipoVialidad\\.clave').val("");
	$('#domicilio\\.vialidadReferenciaSecundaria\\.tipoVialidad\\.descripcion').val("");
	
	$('#domicilio\\.vialidadReferenciaPosterior\\.clave').val("");
	$('#domicilio\\.vialidadReferenciaPosterior\\.nombre').val("");
	$('#domicilio\\.vialidadReferenciaPosterior\\.tipoVialidad\\.clave').val("");
	$('#domicilio\\.vialidadReferenciaPosterior\\.tipoVialidad\\.descripcion').val("");

	$("#domicilio\\.calle").val("");
	$("#domicilio\\.tipoBusquedaVialidad").val("");

	$("#domicilio\\.domicilioCarretera\\.terminoGeneral\\.descripcion").val('');
	$("#domicilio\\.domicilioCarretera\\.terminoGeneral\\.clave").val('');
	$("#domicilio\\.domicilioCarretera\\.derechoTransito\\.descripcion").val('');
	$("#domicilio\\.domicilioCarretera\\.derechoTransito\\.clave").val('');
	$("#domicilio\\.domicilioCarretera\\.origen").val('');
	$("#domicilio\\.domicilioCarretera\\.destino").val('');
	$("#domicilio\\.domicilioCarretera\\.administracion\\.descripcion").val('');
	$("#domicilio\\.domicilioCarretera\\.administracion\\.clave").val('');
	$("#domicilio\\.domicilioCarretera\\.cadenamiento").val('');
	$("#domicilio\\.domicilioCarretera\\.codigoCarretera").val('');

	$("#domicilio\\.domicilioCamino\\.terminoGeneral\\.descripcion").val('');
	$("#domicilio\\.domicilioCamino\\.terminoGeneral\\.clave").val('');
	$("#domicilio\\.domicilioCamino\\.margen\\.descripcion").val('');
	$("#domicilio\\.domicilioCamino\\.margen\\.clave").val('');
	$("#domicilio\\.domicilioCamino\\.origen").val('');
	$("#domicilio\\.domicilioCamino\\.destino").val('');
	$("#domicilio\\.domicilioCamino\\.cadenamiento").val('');
};

/**
 * Funcion que setea la persona en un formulario
 * @param persona
 * @returns
 */
function setPersonaCommon(persona) {
	var idPersona = null;
	
	if(!jQuery.isEmptyObject(persona)){
		if(persona.idPersona == undefined || persona.idPersona == null){ // Persona RENAPO
			$('#fisica\\.idPersona').val("");
		}else if(persona.idPersona != undefined ){ // Persona en IMSS con calificacion
			$('#fisica\\.idPersona').val(persona.idPersona);	
			idPersona = persona.idPersona;
		}
			
		if(persona.nombre != undefined){
			$('#fisica\\.nombre').val(persona.nombre);
		}
		if(persona.curp != undefined){
			$('#fisica\\.curp').val(persona.curp);
		}
		
		if(persona.primerApellido != undefined){        
			$('#miPrimerApellido').val(persona.primerApellido);
			$('#fisica\\.primerApellido').val(persona.primerApellido);
		}
		
		if(persona.segundoApellido != undefined){
			$('#miSegundoApellido').val(persona.segundoApellido);
			$('#fisica\\.segundoApellido').val(persona.segundoApellido);
		}
	    
	    if(persona.fechaNacimientoFormateada != undefined){
	    	$('#fisica\\.fechaNacimiento').val(persona.fechaNacimientoFormateada);
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
	    
	    if(persona.mesRegistroNac != undefined) {
	    	if($('#fisica\\.mesRegistroNac').length > 0) {
	    		$('#fisica\\.mesRegistroNac').val(persona.mesRegistroNac);
	    	}
	    }
	    
	    if(persona.anioRegistroNac != undefined) {
	    	if($('#fisica\\.anioRegistroNac').length > 0) {
	    		$('#fisica\\.anioRegistroNac').val(persona.anioRegistroNac);
	    	}
	    }
	    
	    if(persona.actaNacimiento != undefined && persona.actaNacimiento != null && $("#fisica\\.actaNacimiento\\.noActa").length > 0) {
	    	var actaNacimiento = persona.actaNacimiento;
	    	
	    	if(actaNacimiento.noActa != undefined && actaNacimiento.noActa != null) {
	    		$("#fisica\\.actaNacimiento\\.noActa").val(actaNacimiento.noActa);
	    	}
	    	
	    	if(actaNacimiento.noFoja != undefined && actaNacimiento.noFoja != null) {
	    		$("#fisica\\.actaNacimiento\\.noFoja").val(actaNacimiento.noFoja);
	    	}
	    	
	    	if(actaNacimiento.noLibro != undefined && actaNacimiento.noLibro != null) {
	    		$("#fisica\\.actaNacimiento\\.noLibro").val(actaNacimiento.noLibro);
	    	}
	    	
	    	if(actaNacimiento.municipio != undefined && actaNacimiento.municipio != null) {
	    		$("#fisica\\.actaNacimiento\\.municipio\\.clave").val(actaNacimiento.municipio.clave);
	    		$("#fisica\\.actaNacimiento\\.municipio\\.nombre").val(actaNacimiento.municipio.nombre);
	    		
	    		if(actaNacimiento.municipio.entidadFederativa != undefined && actaNacimiento.municipio.entidadFederativa != null) {
	    			$("#fisica\\.actaNacimiento\\.municipio\\.entidadFederativa\\.clave").val(actaNacimiento.municipio.entidadFederativa.clave);
	    			$("#fisica\\.actaNacimiento\\.municipio\\.entidadFederativa\\.nombre").val(actaNacimiento.municipio.entidadFederativa.nombre);
	    		}
	    	}
	    	
	    	if(actaNacimiento.documentoPorTipo != undefined && actaNacimiento.documentoPorTipo != null) {
	    		$("#fisica\\.actaNacimiento\\.documentoPorTipo\\.idDocumentoPorTipo").val(actaNacimiento.documentoPorTipo.idDocumentoPorTipo);
	    		if(actaNacimiento.documentoPorTipo.documento != undefined && actaNacimiento.documentoPorTipo.documento != null) {
	    			$("#fisica\\.actaNacimiento\\.documentoPorTipo\\.documento\\.cveIdDocumento").val(actaNacimiento.documentoPorTipo.documento.cveIdDocumento);
	    			$("#fisica\\.actaNacimiento\\.documentoPorTipo\\.documento\\.desDocumento").val(actaNacimiento.documentoPorTipo.documento.desDocumento);
	    		}
	    	} else {
	    		$("#fisica\\.actaNacimiento\\.documentoPorTipo\\.idDocumentoPorTipo").val(17);
	    		$("#fisica\\.actaNacimiento\\.documentoPorTipo\\.documento\\.cveIdDocumento").val(1);
	    		$("#fisica\\.actaNacimiento\\.documentoPorTipo\\.documento\\.desDocumento").val('ACTA DE NACIMIENTO');
	    	}
	    	
	    	if(actaNacimiento.idEntidadFederativa != undefined && actaNacimiento.idEntidadFederativa != null) {
	    		$("#fisica\\.actaNacimiento\\.noLibro").val(actaNacimiento.idEntidadFederativa);
	    	}
	    	
	    	if(actaNacimiento.idMunicipio != undefined && actaNacimiento.idMunicipio != null) {
	    		$("#fisica\\.actaNacimiento\\.idMunicipio").val(actaNacimiento.idMunicipio);
	    	}
	    	
	    	$("#fisica\\.actaNacimiento\\.fechaSuceso").val(persona.fechaNacimientoFormateada);
	    	
	    	if(actaNacimiento.anio != undefined && actaNacimiento.anio != null) {
	    		$("#fisica\\.actaNacimiento\\.anio").val(actaNacimiento.anio);
	    	}
	    	
	    	if(actaNacimiento.tomo != undefined && actaNacimiento.tomo != null) {
	    		$("#fisica\\.actaNacimiento\\.tomo").val(actaNacimiento.tomo);
	    	}
	    	
	    	if(actaNacimiento.crip != undefined && actaNacimiento.crip != null) {
	    		$("#fisica\\.actaNacimiento\\.crip").val(actaNacimiento.crip);
	    	}
	    	
	    	if(actaNacimiento.noJuzgado != undefined && actaNacimiento.noJuzgado != null) {
	    		$("#fisica\\.actaNacimiento\\.noJuzgado").val(actaNacimiento.noJuzgado);
	    	}
	    }
	}
	
	return idPersona;
}

function  valdaDatosMinimosDomicilioAnteriorActualDiferentes (contenedor){
	
	var idContenedor = (contenedor!=undefined && contenedor != null) ? ("#"+contenedor+" ") : "";
	if(
	$.trim($(idContenedor + '#domicilio\\.codigoPostal\\.codigoPostal').val()) != $.trim($(idContenedor + '#domicilioAnterior\\.codigoPostal\\.codigoPostal').val()) ||
	$.trim($(idContenedor + "#domicilio\\.calle").val()) != $.trim($(idContenedor + "#domicilioAnterior\\.calle").val()) ||
	$.trim($(idContenedor + '#domicilio\\.asentamiento\\.clave').val()) != $.trim($(idContenedor + '#domicilioAnterior\\.asentamiento\\.clave').val()) ||
	$.trim($(idContenedor + '#domicilio\\.asentamiento\\.localidad\\.municipio\\.clave').val()) !=
		$.trim($(idContenedor + '#domicilioAnterior\\.asentamiento\\.localidad\\.municipio\\.clave').val()) ||
	$.trim($(idContenedor + '#domicilio\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave').val()) !=
		$.trim($(idContenedor + '#domicilioAnterior\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave').val()) ||
	$.trim($(idContenedor + '#domicilio\\.numExteriorAlf').val()) != $.trim($(idContenedor + '#domicilioAnterior\\.numExteriorAlf').val()) ||
	$.trim($(idContenedor + '#domicilio\\.numInteriorAlf').val()) != $.trim($(idContenedor + '#domicilioAnterior\\.numInteriorAlf').val())
	){
		return true;
	}else{
		return false;
	}	
}
