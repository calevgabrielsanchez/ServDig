/**
 * JS de manejo del componente de domicilio que se usara en el tramite de registro de derechohabientes
 */

//div compoentne domicilio
ID_COMPONENTE_DOM = "#contenedorDomicilioRecortado";
//id_plugin domicilio
ID_PLUGIN_DOM = "plugin_domicilioRecortado";

/**
 * function para iniciar el componente de domicilios
 */
var iniciarComponenteDomicilio = function() {
	
	//Modificamos la ruta de busqueda de codigos postales
	$.fn.domicilioRecortado.URL_BUSQUEDA_CP = '/gestionDomicilios-web/domicilio/nacional/ubicar/byUmf/asentamiento/get/codigoPostalYUmf'
	//hacemos esto para obtener el domicilio del tramite
	var tramiteJSON = $("#registro").toObject();
	
	//inicializamos el componente de domicilio recortado
	$(ID_COMPONENTE_DOM).domicilioRecortado({
		funcionError: null,
		mostrarMensajeCaptura: false,
		mostrarFormulario: true,
		formularioDeshabilitado:true,
		domicilioDefault: tramiteJSON.domicilio,
		setDomicilioDefaultOnInit: true,
		bootstrapHabilitado: false,
		mostrarTitulosDialogs: true,
		classInputs: 'form-control',//cssClass que tendran los select, cajas de texto
		classTabla: 'page_holder_no_height', //cssClas que tendra la tabla
		classButtonAceptar: 'mboton', //cssClass del boton aceptar que iniciara la busqueda por CP
		classButtonLimpiar: 'mboton', //cssClass del boton limpiar del formulario de domicilio
		parametrosBusqueda:{idUmf : $("#idUmfUsuario").val()}//la url en la que buscamos los codigos postales requiere este parametro
	});
}

/**
 * Funcion para setear el domicilio que se pase tanto en el componente de domicilio
 * como en el formulario oculto
 */
var setearDomicilio = function(domicilio,limpiarIfNullUndefined) {
	if(domicilio != undefined && domicilio != null ) {
		//seteamos domicilio en el form oculto
		setDomicilioCommon(domicilio);
		//seteamos el domicilio en el componente de domicilio recortado
		$(ID_COMPONENTE_DOM).domicilioRecortado("set",domicilio);
	} else if(limpiarIfNullUndefined) {
		limpiarDomicilio();
	}
}

/**
 * Function para mostrar o no el boton de ubicar domicilio (funcionamiento anterior)
 * habilita o deshabilita el componente de domicilio recortado
 */
var muestraUbicarDomicilio = function(mostrar){
	//limpiamos el componente de domicilio
	$(ID_COMPONENTE_DOM).domicilioRecortado("block",!mostrar);
};

/**
 * Validamos que el domicilio capturado en el componente sea correcto
 */
var validarDomicilioCapturado = function(funcion) {
	//obtenemos el domicilio capturado
	var domicilioCapturado = $(ID_COMPONENTE_DOM).domicilioRecortado("get");
	//verificamos si el domicilio capturado es diferente de null,
	//en caso de ser nulo quiere decir que existen errores de captura
	if(domicilioCapturado != null) {
		//seteamos domicilio en el form oculto
		setDomicilioCommon(domicilioCapturado);
		return true;
	} else {
		if( $.isFunction(funcion)) {
			funcion();
		}
		return false;
	}
	
	
}

var limpiarDomicilio = function() {
	//seteamos domicilio en el form oculto
	limpiarDatosDomicilio();
	//limpiamos el componente de domicilio
	$(ID_COMPONENTE_DOM).domicilioRecortado("clean");
}