/**
 * JS para la vista de la captura de datos complementarios + serie de la persona para solicitudes pendientes
 */

var objTipoSerie;

$(document).ready(function() {
	
	// Verificamos los campos a readonly
//	$('div#datosBasicosDiv').makeFormReadOnly();
	$('div#datosBasicosDiv').deshabilitarContenido(false);
    // los combos hechos con el componente maravilloso no los desabilitan los metodos anteriores, por eso hay que hacerlo de forma manual	
	$('#fisica\\.lugarNacimiento\\.clave').attr('disabled', 'disabled');
	$('#fisica\\.sexo\\.idSexo').attr('disabled', 'disabled');
	
	// Creamos los campos como readonly del curp
//	var curpDiv = $('div#datosCurp');
//	$('input', curpDiv).each(function() {
//		$(this).fadeTo('slow', 0.5);
//	});

	// Configuracion del calendario
	$("#registroFechaNacimientoC").datepicker({
		showOn : 'both',
		// buttonImage: '../../imagenes/pie.jpg',
		// buttonImage: '../../../resources/imagenes/pie.jpg',
		// buttonText: 'Ingresar fecha',
		// buttonImageOnly: false,
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : '-112:+0'
	});

	// Inicializacion del dialogo de cancelacion de solicitud pendiente
	objDialog = $('#dgCancelarSolicitudPendiente').dialog({
	        autoOpen:false,
	        resizable: false,
	        height:150,
	        width:400,
	        modal: true,
	        buttons: {
	            "Aceptar": function(data) {
	        		var url = context_path + "/tramite/pendiente/cancelar";
	        		var form = $('form#paso3Form').attr('action', url);
	        		form.attr('method', 'post');
	        		form.submit();
	            },
	            "Cancelar": function(data) {
	            	$( this ).dialog( "close" );
					return false;
	            }
	        }
	 });
	
	$('#cancelarSolicitud').click(function() {
		objDialog.dialog('open');
	});

	$('#registrar').click(function() {
		
		$('div#datosBasicosDiv').habilitarContenido(false);
		// los combos con el componente maravilloso no los desabilitan los metodos anteriores, por eso hay que hacerlo de forma manual		
		$('#fisica\\.lugarNacimiento\\.clave').removeAttr('disabled');
		$('#fisica\\.sexo\\.idSexo').removeAttr('disabled');
		
		$('form#paso3Form').submit();
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

	/**
	 * Incluimos el JS de Medios de contacto
	 */
	var urlMedios = "/gestionMediosContacto-web/static/resources/js/delta/mediosContacto/MedioContacto.js";
	$.getScript(urlMedios).done(function(script, textStatus) {
		MedioContactoCtrl.init('mediosContactoCaptura');
		MedioContactoCtrl.setOnCloseCallback(fnOnCloseMediosContacto);
		$('#btnRegistrarMedios').click(function() {
			MedioContactoCtrl.registrar();
		});
	}).fail(
	function(jqxhr, settings, exception) {
		alert('Error al cargar el script /gestionMediosContacto-web/static/resources/js/delta/mediosContacto/MedioContacto.js');
	});

	// Volvemos de solo escritura los campos de las secciones de
	// medios y domicilio
	var domiciliosDiv = $('div#domicilio');
	$('input[type=\'text\'],input[type=\'checkbox\'] ',	domiciliosDiv).each(function() {
		$(this).fadeTo('slow', 0.5);
		$(this).attr('readonly', 'readony');
	});

	var mediosDiv = $('div#mediosContactoDatos');
	$('input[type=\'text\'],input[type=\'checkbox\'] ',	mediosDiv).each(function() {
		$(this).fadeTo('slow', 0.5);
		$(this).attr('readonly', 'readony');
	});

	var serieDiv = $('div#datosSerie');
	$('input[type=\'text\'],input[type=\'checkbox\'] ',	serieDiv).each(function() {
		$(this).fadeTo('slow', 0.5);
		$(this).attr('readonly', 'readony');
	});
	
	//Combo series y años
	iniciarComboAnios();

	$('#asignacionSerieNss\\.serie\\.tipoSerie\\.idTipoSerie').change(function(){
		var text = $('#asignacionSerieNss\\.serie\\.tipoSerie\\.idTipoSerie option:selected').text();
		$('#asignacionSerieNss\\.serie\\.tipoSerie\\.descripcion').val(text);

		if($('#asignacionSerieNss\\.serie\\.tipoSerie\\.idTipoSerie').val() == 3){
			$('#selectSerie').removeClass('hiddenElement');
			$('#selectSerie').addClass('showElement');
		}else{
			$('#selectSerie').removeClass('showElement');
			$('#selectSerie').addClass('hiddenElement');
		}
	});
	
	// este codigo tiene sentido despues de una recarga de la pagina, cuando hubo errores de valicacion en el controller. entonces, hay que refrescar los combos 
	// si es que aplica
	if($('#tipoSerie\\.idTipoSerie').val() == 3){
		$('#selectSerie').removeClass('hiddenElement');
		$('#selectSerie').addClass('showElement');
	}else{
		$('#selectSerie').removeClass('showElement');
		$('#selectSerie').addClass('hiddenElement');
	}
	
	if ($('form#asentamientoForUmfForm').length > 0) {
		obtenerUMFs();
	} else {
		$('div#umfContenedor').html('No se cuenta con la información necesaria para obtener las Unidades Médico Familiar');
	}
	
});


//metodo para iniciar el combo de años
function iniciarComboAnios(){
	var options = "<option value='-1'> --Por favor seleccione-- </option>";
	for(var i = 0; i < 100; i ++){
		var anio = i + "";
		if(anio.length == 1){
			anio = "0" + anio;
		}
		options += "<option value='" + i + "'>" + anio + "</option>";
	}
	
	$('#asignacionSerieNss\\.serie\\.anioRegistro').html(options);
}

//este metodo hace una consulta para obtener los datos de una serie en base a un idSerie y si el idTipoSerie es 3 entonces ocultara el combo de los años; si se 
//diferente a 3 entonces lo mostrara
function getSerie(){
	
	var sDivSerie = 'div#selectSerie';
	var objDivSerie = $(sDivSerie);
	
 var url = context_path + "/serie/get/serie";
 var idSerie = $('#serie\\.idSerie').val();
	   
 $.getJSON(url, {'idSerie': idSerie}, function(data){
 	
 	objTipoSerie = data.serie.tipoSerie.idTipoSerie;
		if(objTipoSerie == 3){
			objDivSerie.removeClass('hiddenElement');
			objDivSerie.addClass('showElement');
		}else{
			objDivSerie.removeClass('showElement');
			objDivSerie.addClass('hiddenElement');
		}
		
		$('#tipoSerie\\.idTipoSerie').val(objTipoSerie);
		
 }).error(function(data){
//		fnProcesarErrores(data, 'form#formCodigoPostal')
 });
}


 
/*
 * Funcion de callback para el llenado de la informacion de los medios de
 * contacto
 */
var fnOnCloseMediosContacto = function() {
	var objetoMediosContacto = this;
	var hasPropMediosContacto = false;

	if (objetoMediosContacto != null) {
		for ( var listProp in objetoMediosContacto) {
			hasPropMediosContacto = true;
			break;
		}
	}

	if (objetoMediosContacto != null && hasPropMediosContacto) {

		var contextMedios = $('div#mediosContactoDatos');
		try {
			var tf = objetoMediosContacto.telefonoFijo;
			if (tf != null) {
				try {
					$('#numeroTelefonicoParticular', contextMedios).attr(
							'value', objetoMediosContacto.telefonoFijo.numero);
					$('#claveLada', contextMedios).attr('value',
							objetoMediosContacto.telefonoFijo.claveLada);
					$('#extension', contextMedios).attr('value',
							objetoMediosContacto.telefonoFijo.extension);
				} catch (e) {
					// TODO: handle exception
				}

			}

			var tm = objetoMediosContacto.telefonoMovil;
			if (tm != null) {
				try {
					$('#numeroTelefonicoMovil', contextMedios).attr('value',
							objetoMediosContacto.telefonoMovil.numero);
				} catch (e) {
					// TODO: handle exception
				}

			}
			var ce = objetoMediosContacto.correoElectronico;
			if (ce != null) {
				try {
					$('#correoElectronico', contextMedios).attr('value',
							objetoMediosContacto.correoElectronico.correo);
				} catch (e) {
					// TODO: handle exception
				}

			}

		} catch (e) {

		}
		
		var urlAddMedios = context_path + "/tramite/mediosContacto/agregar"
		// Agregamos los domicilios a la solicitud ...
		$.postJSON(urlAddMedios ,objetoMediosContacto, function(data){
				
		} );
		
		
	}

}

/*
 * Funcion de callback para el llenado de la informacion del domicilio
 */
var fnOnCloseDomicilio = function() {
	var objetoDomicilio = this;
	var contextDomicilio = $('div#domicilio div#datos');
	var hasPropDomicilio = false;

	if (objetoDomicilio != null) {
		for ( var listProp in objetoDomicilio) {
			hasPropDomicilio = true;
			break;
		}
	}

	if (objetoDomicilio != null && hasPropDomicilio) {
		try {

			
			$('#entidadFederativa', contextDomicilio)
					.attr(
							'value',
							objetoDomicilio.asentamiento.localidad.municipio.entidadFederativa.nombre);
			$('#municipio', contextDomicilio).attr('value',
					objetoDomicilio.asentamiento.localidad.municipio.nombre);
			$('#localidad', contextDomicilio).attr('value',
					objetoDomicilio.asentamiento.localidad.nombre);
			$('#asentamiento', contextDomicilio).attr('value',
					objetoDomicilio.asentamiento.nombre);

			$('#entidadFederativaClave', contextDomicilio)
					.attr(
							'value',
							objetoDomicilio.asentamiento.localidad.municipio.entidadFederativa.clave);
			$('#municipioClave', contextDomicilio).attr('value',
					objetoDomicilio.asentamiento.localidad.municipio.clave);
			$('#localidadClave', contextDomicilio).attr('value',
					objetoDomicilio.asentamiento.localidad.clave);
			$('#asentamientoClave', contextDomicilio).attr('value',
					objetoDomicilio.asentamiento.clave);

			
			$('#numeroExteriorPrincipal', contextDomicilio).attr('value',
					objetoDomicilio.numExterior1);
			$('#numeroExteriorAlfanumerico', contextDomicilio).attr('value',
					objetoDomicilio.numExteriorAlf);
			$('#numeroExteriorSecundario', contextDomicilio).attr('value',
					objetoDomicilio.numExterior2);
			$('#numeroInterior', contextDomicilio).attr('value',
					objetoDomicilio.numInterior);
			$('#numeroInteriorAlfanumerico', contextDomicilio).attr('value',
					objetoDomicilio.numInteriorAlf);
			
			
			
			$('#vialidadPrimaria', contextDomicilio).attr('value',
					objetoDomicilio.vialidadPrimaria.nombre);
			$('#vialidadReferenciaPrimaria', contextDomicilio).attr('value',
					objetoDomicilio.vialidadReferenciaPrimaria.nombre);
			$('#vialidadReferenciaSecundaria', contextDomicilio).attr('value',
					objetoDomicilio.vialidadReferenciaSecundaria.nombre);
			$('#vialidadReferenciaPosterior', contextDomicilio).attr('value',
					objetoDomicilio.vialidadReferenciaPosterior.nombre);
			
			$('#vialidadPrimaria\\.clave', contextDomicilio).attr('value',
					objetoDomicilio.vialidadPrimaria.clave);
			
			$('#vialidadReferenciaPrimaria\\.clave', contextDomicilio).attr('value',
					objetoDomicilio.vialidadReferenciaPrimaria.clave);
			
			$('#vialidadReferenciaSecundaria\\.clave', contextDomicilio).attr('value',
					objetoDomicilio.vialidadReferenciaSecundaria.clave);
			
			$('#vialidadReferenciaPosterior\\.clave', contextDomicilio).attr('value',
					objetoDomicilio.vialidadReferenciaPosterior.clave);

			
			
			
			
			
			$('#claveEntidadFederativa', contextDomicilio)
					.attr(
							'value',
							objetoDomicilio.asentamiento.localidad.municipio.entidadFederativa.clave);
			$('#claveMunicipio', contextDomicilio).attr('value',
					objetoDomicilio.asentamiento.localidad.municipio.clave);
			$('#claveLocalidad', contextDomicilio).attr('value',
					objetoDomicilio.asentamiento.localidad.clave);
			$('#claveAsentamiento', contextDomicilio).attr('value',
					objetoDomicilio.asentamiento.clave);
			
			$('#codigoPostal', contextDomicilio).attr('value',
					objetoDomicilio.asentamiento.codigoPostal.codigoPostal);

		} catch (e) {
			// TODO: handle exception
		}
		
		// Se ejecuta el componeten para localizar las UMFs
		$('input#cveAsentamientoAux').val(objetoDomicilio.asentamiento.clave);
		$('input#nombreAsentamientoAux').val(objetoDomicilio.asentamiento.nombre);
		$('input#localidadCveAsentamientoAux').val(objetoDomicilio.asentamiento.localidad.clave);
		$('input#municipioCveAsentamientoAux').val(objetoDomicilio.asentamiento.localidad.municipio.clave);
		$('input#estadoCveAsentamientoAux').val(objetoDomicilio.asentamiento.localidad.municipio.entidadFederativa.clave);
		$('input#cpAsentamientoAux').val(objetoDomicilio.asentamiento.codigoPostal.codigoPostal);
		$('input#tipoAsentamientoAux').val(objetoDomicilio.asentamiento.tipoAsentamiento.clave);
		
		obtenerUMFs();
		
		var urlAddDomicilio = context_path + "/tramite/domicilio/agregar";
		// Agregamos los domicilios a la solicitud ...
		$.postJSON(urlAddDomicilio ,objetoDomicilio, function(data){
				
		} );

	}

};