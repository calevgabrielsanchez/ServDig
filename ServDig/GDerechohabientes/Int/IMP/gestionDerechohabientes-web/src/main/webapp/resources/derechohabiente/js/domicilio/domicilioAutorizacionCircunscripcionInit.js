ID_DIV_DOM_ACTUAL = "domicilioAnteriorDiv";
ID_DIV_DOM_NUEVO = "domicilioActualDiv";
//id_plugin domicilio
ID_PLUGIN_DOM = "plugin_domicilioRecortado";

/**
 * Funcion para iniciar dos componente de domicilio en el tramite de cambio de clinica
 * uno para mostrar el domicilio actual de la persona que siempre estara deshabilitado y otro
 * para que se capture el domicilio al que se cambiara la persona
 */
function initDomicilios(validacion) {
	//modificamos la ruta de busqueda de codigos postales
	$.fn.domicilioRecortado.URL_BUSQUEDA_CP = '/gestionDomicilios-web/domicilio/nacional/ubicar/asentamiento/get/codigoPostal'
	//convertimos el formulario en objeto para obtenerl el domicilio actual y el nuevo
	var tramiteCorreccion = $("#correccionDatos").toObject();
	isValidacion =validacion;
	//las opciones que ambos componentes compartiran
	var opcionesComunes = {
		funcionError: null,
		mostrarMensajeCaptura: true,
		mostrarFormulario: true,
		setDomicilioDefaultOnInit: true,
		bootstrapHabilitado: false,
		mostrarTitulosDialogs: true,
		classInputs: 'form-control',//cssClass que tendran los select, cajas de texto
		classTabla: 'page_holder_no_height', //cssClas que tendra la tabla
		classButtonAceptar: 'mboton', //cssClass del boton aceptar que iniciara la busqueda por CP
		classButtonLimpiar: 'mboton' //cssClass del boton limpiar del formulario de domicilio
	};	
	
	//inicializamos el componente de domicilio recortado
	$("#"+ID_DIV_DOM_ACTUAL).domicilioRecortado($.extend({},opcionesComunes,{
		domicilioDefault: tramiteCorreccion.domicilioAnterior,
		formularioDeshabilitado:true //deshabillitamos el formulario de inicio
	}));
	
	if(validacion){
	
	//inicializamos el componente de domicilio recortado
		$("#"+ID_DIV_DOM_NUEVO).domicilioRecortado($.extend({},opcionesComunes,{
			domicilioDefault: tramiteCorreccion.domicilio,
			formularioDeshabilitado: validacion //si estamos en la validacion bloqueamos tambien este domicilio
		}));
	}else{
	
		$("#"+ID_DIV_DOM_NUEVO).domicilioRecortado($.extend({},opcionesComunes,{
			
			formularioDeshabilitado: validacion //si estamos en la validacion bloqueamos tambien este domicilio
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
		setDomicilioCommon(domicilioCapturado,"correccionDatos");
		return true;
	} else {
		if( $.isFunction(funcion)) {
			funcion();
		}
		return false;
	}
	
}

/**INICIALIZACION PARA LOS DOMICILIOS DE LA SUSPENSION DE LA AUTORIZACION**/
function initDomiciliosSuspension() {
	//modificamos la ruta de busqueda de codigos postales
	$.fn.domicilioRecortado.URL_BUSQUEDA_CP = '/gestionDomicilios-web/domicilio/nacional/ubicar/asentamiento/get/codigoPostal'
	//convertimos el formulario en objeto para obtenerl el domicilio actual y el nuevo
	var tramiteCorreccion = $("#correccionDatos").toObject();
	
	//las opciones que ambos componentes compartiran
	var opcionesComunes = {
		funcionError: null,
		mostrarMensajeCaptura: true,
		mostrarFormulario: true,
		setDomicilioDefaultOnInit: true,
		bootstrapHabilitado: false,
		mostrarTitulosDialogs: true,
		classInputs: 'form-control',//cssClass que tendran los select, cajas de texto
		classTabla: 'page_holder_no_height', //cssClas que tendra la tabla
		classButtonAceptar: 'mboton', //cssClass del boton aceptar que iniciara la busqueda por CP
		classButtonLimpiar: 'mboton' //cssClass del boton limpiar del formulario de domicilio
	};	
	
	//inicializamos el componente de domicilio recortado
	$("#"+ID_DIV_DOM_ACTUAL).domicilioRecortado($.extend({},opcionesComunes,{
		domicilioDefault: tramiteCorreccion.domicilioOrigen,
		formularioDeshabilitado:true //deshabillitamos el formulario de inicio
	}));
	//inicializamos el componente de domicilio recortado
		$("#"+ID_DIV_DOM_NUEVO).domicilioRecortado($.extend({},opcionesComunes,{
			domicilioDefault: tramiteCorreccion.domicilioDestino,
			formularioDeshabilitado: true //si estamos en la validacion bloqueamos tambien este domicilio
		}));
	
		
}



