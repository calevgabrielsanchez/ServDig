ID_DIV_DOM_NUEVO = "domicilioDerechohabienteDiv";
//id_plugin domicilio
ID_PLUGIN_DOM = "plugin_domicilioRecortado";



$(document).ready(
	function () {
		initDomiciliosRegistro($("#nuevoDomicilio").val()==1);
	}
);

/**
 * Funcion para iniciar dos componente de domicilio en el tramite de cambio de clinica
 * uno para mostrar el domicilio actual de la persona que siempre estara deshabilitado y otro
 * para que se capture el domicilio al que se cambiara la persona
 */
function initDomiciliosRegistro(esNuevoDomicilio) {
	
	//modificamos la ruta de busqueda de codigos postales
	$.fn.domicilioRecortado.URL_BUSQUEDA_CP = '/gestionDomicilios-web/domicilio/nacional/ubicar/asentamiento/get/codigoPostal'
	//convertimos el formulario en objeto para obtenerl el domicilio actual y el nuevo
	var tramiteDomicilio = $("#formComplemento").toObject();
	//las opciones que ambos componentes compartiran
	var opcionesComunes = {
		mostrarMensajeCaptura: true,
		mostrarFormulario: true,
		setDomicilioDefaultOnInit: true,
		mostrarTitulosDialogs: true,
		
	};	
	
	if(!esNuevoDomicilio){
		opcionesComunes.excepcionesBloqueo=["calle","numExt","numInt"];
		//inicializamos el componente de domicilio recortado
		$("#"+ID_DIV_DOM_NUEVO).domicilioRecortado($.extend({},opcionesComunes,{
			domicilioDefault: tramiteDomicilio,
			formularioDeshabilitado: true
		}));
	}else{
	//inicializamos el componente de domicilio recortado
		$("#"+ID_DIV_DOM_NUEVO).domicilioRecortado($.extend({},opcionesComunes,{
			domicilioDefault: null,
			formularioDeshabilitado: false
		}));
	}

}

/**
 * Validamos que el domicilio capturado en el componente sea correcto
 */
var validarDomicilioCapturadoComponente = function(funcion) {
	//obtenemos la referencia al plugin
	var componenteDomicilio = $("#"+ID_DIV_DOM_NUEVO).data(ID_PLUGIN_DOM);
	//obtenemos el domicilio capturado
	var domicilioCapturado = componenteDomicilio.getDomicilioCapturado();
	//verificamos si el domicilio capturado es diferente de null,
	//en caso de ser nulo quiere decir que existen errores de captura
	if(domicilioCapturado != null) {
		//seteamos domicilio en el form oculto
		setDomicilio(domicilioCapturado, "formComplemento");
		return true;
	} else {
		if( $.isFunction(funcion)) {
			funcion();
		}
		return false;
	}
	
}

function setDomicilio(domicilio, contenedor) {
	
	var idContenedor = (contenedor!=undefined && contenedor != null) ? ("#"+contenedor+" ") : "";
	//-------------------------------
	//Verificamos si el domicilio viene y no es nulo
	//---------------------------------
	if(domicilio != undefined && domicilio != null && !jQuery.isEmptyObject(domicilio)) {
		
		//Se setea la clave en caso de existir
		$(idContenedor + ' #clave').val(domicilio.clave);
		//Se setea el codigo postal
		$(idContenedor + ' #codigoPostal\\.codigoPostal').val(domicilio.codigoPostal.codigoPostal);
		
		//--------------------------------------------------
		//Se setea toda la informacion relacionada con el asentamiento
		//---------------------------------------------------
		$(idContenedor + ' #asentamiento\\.clave').val(domicilio.asentamiento.clave);
		
		$(idContenedor + ' #asentamiento\\.codigoPostal\\.codigoPostal').val(domicilio.codigoPostal.codigoPostal);
		$(idContenedor + ' #asentamiento\\.nombre').val(domicilio.asentamiento.nombre);
		$(idContenedor + ' #asentamiento\\.localidad\\.clave').val(domicilio.asentamiento.localidad.clave);
		$(idContenedor + ' #asentamiento\\.localidad\\.nombre').val(domicilio.asentamiento.localidad.nombre);
		$(idContenedor + ' #asentamiento\\.localidad\\.municipio\\.clave').val(domicilio.asentamiento.localidad.municipio.clave);
		$(idContenedor + ' #asentamiento\\.localidad\\.municipio\\.nombre').val(domicilio.asentamiento.localidad.municipio.nombre);
		$(idContenedor + ' #asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave').val(domicilio.asentamiento.localidad.municipio.entidadFederativa.clave);
		$(idContenedor + ' #asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.nombre').val(domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre);
		
		/*
		 * Se setea toda la informacion relacionada con los numetos
		 */
		$(idContenedor + ' #numExterior1').val(domicilio.numExterior1);
		$(idContenedor + ' #numExteriorAlf').val(domicilio.numExteriorAlf);
		$(idContenedor + ' #numInterior').val(domicilio.numInterior);
		$(idContenedor + ' #numInteriorAlf').val(domicilio.numInteriorAlf);
		$(idContenedor + ' #numExterior2').val(domicilio.numExterior2);
		
		/*
		 * Se verifican las vialidades
		 */
		if(domicilio.vialidadPrimaria != null && domicilio.vialidadPrimaria != undefined) {
			$(idContenedor + ' #vialidadPrimaria\\.clave').val(domicilio.vialidadPrimaria.clave);
			$(idContenedor + ' #vialidadPrimaria\\.nombre').val(domicilio.vialidadPrimaria.nombre);
			
			if(domicilio.vialidadPrimaria.tipoVialidad != undefined && domicilio.vialidadPrimaria.tipoVialidad != null) {
				$(idContenedor + ' #vialidadPrimaria\\.tipoVialidad\\.clave').val(domicilio.vialidadPrimaria.tipoVialidad.clave);
				$(idContenedor + ' #vialidadPrimaria\\.tipoVialidad\\.descripcion').val(domicilio.vialidadPrimaria.tipoVialidad.descripcion);
			}
		} else {
			$(idContenedor + ' #vialidadPrimaria\\.clave').val("");
			$(idContenedor + ' #vialidadPrimaria\\.nombre').val("");
			$(idContenedor + ' #vialidadPrimaria\\.tipoVialidad\\.clave').val("");
			$(idContenedor + ' #vialidadPrimaria\\.tipoVialidad\\.descripcion').val("");
		}
		
		if(domicilio.vialidadReferenciaPrimaria != null && domicilio.vialidadReferenciaPrimaria != undefined) {
			$(idContenedor + ' #vialidadReferenciaPrimaria\\.clave').val(domicilio.vialidadReferenciaPrimaria.clave);
			$(idContenedor + ' #vialidadReferenciaPrimaria\\.nombre').val(domicilio.vialidadReferenciaPrimaria.nombre);
			// Si el tipo de vialidad vienen nulo no ponemos esos campos
			if(domicilio.vialidadReferenciaPrimaria.tipoVialidad != undefined && domicilio.vialidadReferenciaPrimaria.tipoVialidad != null) {
				$(idContenedor + ' #vialidadReferenciaPrimaria\\.tipoVialidad\\.clave').val(domicilio.vialidadReferenciaPrimaria.tipoVialidad.clave);
				$(idContenedor + ' #vialidadReferenciaPrimaria\\.tipoVialidad\\.descripcion').val(domicilio.vialidadReferenciaPrimaria.tipoVialidad.descripcion);
			}
		} else {
			$(idContenedor + ' #vialidadReferenciaPrimaria\\.clave').val("");
			$(idContenedor + ' #vialidadReferenciaPrimaria\\.nombre').val("");
			$(idContenedor + ' #vialidadReferenciaPrimaria\\.tipoVialidad\\.clave').val("");
			$(idContenedor + ' #vialidadReferenciaPrimaria\\.tipoVialidad\\.descripcion').val("");
		}
		
		if(domicilio.vialidadReferenciaSecundaria != null && domicilio.vialidadReferenciaSecundaria != undefined) {
			$(idContenedor + ' #vialidadReferenciaSecundaria\\.clave').val(domicilio.vialidadReferenciaSecundaria.clave);
			$(idContenedor + ' #vialidadReferenciaSecundaria\\.nombre').val(domicilio.vialidadReferenciaSecundaria.nombre);
			// Si el tipo de vialidad vienen nulo no ponemos esos campos
			if(idContenedor + domicilio.vialidadReferenciaSecundaria.tipoVialidad != undefined && domicilio.vialidadReferenciaSecundaria.tipoVialidad != null) {
				$(' #vialidadReferenciaSecundaria\\.tipoVialidad\\.clave').val(domicilio.vialidadReferenciaSecundaria.tipoVialidad.clave);
				$(' #vialidadReferenciaSecundaria\\.tipoVialidad\\.descripcion').val(domicilio.vialidadReferenciaSecundaria.tipoVialidad.descripcion);
			}
		} else {
			$(idContenedor + ' #vialidadReferenciaSecundaria\\.clave').val("");
			$(idContenedor + ' #vialidadReferenciaSecundaria\\.nombre').val("");
			$(idContenedor + ' #vialidadReferenciaSecundaria\\.tipoVialidad\\.clave').val("");
			$(idContenedor + ' #vialidadReferenciaSecundaria\\.tipoVialidad\\.descripcion').val("");
		}
		
		if(domicilio.vialidadReferenciaPosterior != null && domicilio.vialidadReferenciaPosterior != undefined) {
			$(idContenedor + ' #vialidadReferenciaPosterior\\.clave').val(domicilio.vialidadReferenciaPosterior.clave);
			$(idContenedor + ' #vialidadReferenciaPosterior\\.nombre').val(domicilio.vialidadReferenciaPosterior.nombre);
			// Si el tipo de vialidad vienen nulo no ponemos esos campos
			if(domicilio.vialidadReferenciaPosterior.tipoVialidad != undefined && domicilio.vialidadReferenciaPosterior.tipoVialidad != null) {
				$(idContenedor + ' #vialidadReferenciaPosterior\\.tipoVialidad\\.clave').val(domicilio.vialidadReferenciaPosterior.tipoVialidad.clave);
				$(idContenedor + ' #vialidadReferenciaPosterior\\.tipoVialidad\\.descripcion').val(domicilio.vialidadReferenciaPosterior.tipoVialidad.descripcion);
			} 
		} else {
			$(idContenedor + ' #vialidadReferenciaPosterior\\.clave').val("");
			$(idContenedor + ' #vialidadReferenciaPosterior\\.nombre').val("");
			$(idContenedor + ' #vialidadReferenciaPosterior\\.tipoVialidad\\.clave').val("");
			$(idContenedor + ' #vialidadReferenciaPosterior\\.tipoVialidad\\.descripcion').val("");
		}
		
		$(idContenedor + " #calle").val(domicilio.calle);
		$(idContenedor + " #tipoBusquedaVialidad").val(domicilio.tipoBusquedaVialidad);
		
		if(domicilio.domicilioCarretera != undefined) {
			$(idContenedor + " #domicilioCarretera\\.terminoGeneral\\.descripcion").val(domicilio.domicilioCarretera.terminoGeneral.descripcion);
			$(idContenedor + " #domicilioCarretera\\.terminoGeneral\\.clave").val(domicilio.domicilioCarretera.terminoGeneral.clave);
			$(idContenedor + " #domicilioCarretera\\.derechoTransito\\.descripcion").val(domicilio.domicilioCarretera.derechoTransito.descripcion);
			$(idContenedor + " #domicilioCarretera\\.derechoTransito\\.clave").val(domicilio.domicilioCarretera.derechoTransito.clave);
			$(idContenedor + " #domicilioCarretera\\.origen").val(domicilio.domicilioCarretera.origen);
			$(idContenedor + " #domicilioCarretera\\.destino").val(domicilio.domicilioCarretera.destino);
			$(idContenedor + " #domicilioCarretera\\.administracion\\.descripcion").val(domicilio.domicilioCarretera.administracion.descripcion);
			$(idContenedor + " #domicilioCarretera\\.administracion\\.clave").val(domicilio.domicilioCarretera.administracion.clave);
			$(idContenedor + " #domicilioCarretera\\.cadenamiento").val(domicilio.domicilioCarretera.cadenamiento);
			$(idContenedor + " #domicilioCarretera\\.codigoCarretera").val(domicilio.domicilioCarretera.codigoCarretera);
		} else{
			$(idContenedor + " #domicilioCarretera\\.terminoGeneral\\.descripcion").val('');
			$(idContenedor + " #domicilioCarretera\\.terminoGeneral\\.clave").val('');
			$(idContenedor + " #domicilioCarretera\\.derechoTransito\\.descripcion").val('');
			$(idContenedor + " #domicilioCarretera\\.derechoTransito\\.clave").val('');
			$(idContenedor + " #domicilioCarretera\\.origen").val('');
			$(idContenedor + " #domicilioCarretera\\.destino").val('');
			$(idContenedor + " #domicilioCarretera\\.administracion\\.descripcion").val('');
			$(idContenedor + " #domicilioCarretera\\.administracion\\.clave").val('');
			$(idContenedor + " #domicilioCarretera\\.cadenamiento").val('');
			$(idContenedor + " #domicilioCarretera\\.codigoCarretera").val('');
		}
		
		if(domicilio.domicilioCamino != undefined) {
			$(idContenedor + " #domicilioCamino\\.terminoGeneral\\.descripcion").val(domicilio.domicilioCamino.terminoGeneral.descripcion);
			$(idContenedor + " #domicilioCamino\\.terminoGeneral\\.clave").val(domicilio.domicilioCamino.terminoGeneral.clave);
			$(idContenedor + " #domicilioCamino\\.margen\\.descripcion").val(domicilio.domicilioCamino.margen.descripcion);
			$(idContenedor + " #domicilioCamino\\.margen\\.clave").val(domicilio.domicilioCamino.margen.clave);
			$(idContenedor + " #domicilioCamino\\.origen").val(domicilio.domicilioCamino.origen);
			$(idContenedor + " #domicilioCamino\\.destino").val(domicilio.domicilioCamino.destino);
			$(idContenedor + " #domicilioCamino\\.cadenamiento").val(domicilio.domicilioCamino.cadenamiento);
		} else {
			$(idContenedor + " #domicilioCamino\\.terminoGeneral\\.descripcion").val('');
			$(idContenedor + " #domicilioCamino\\.terminoGeneral\\.clave").val('');
			$(idContenedor + " #domicilioCamino\\.margen\\.descripcion").val('');
			$(idContenedor + " #domicilioCamino\\.margen\\.clave").val('');
			$(idContenedor + " #domicilioCamino\\.origen").val('');
			$(idContenedor + " #domicilioCamino\\.destino").val('');
			$(idContenedor + " #domicilioCamino\\.cadenamiento").val('');
		}
	}
	
}





