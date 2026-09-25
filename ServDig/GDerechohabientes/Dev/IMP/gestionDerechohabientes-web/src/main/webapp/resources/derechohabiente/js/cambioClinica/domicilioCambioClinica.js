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
	limpiarDatosMedico();
	$.fn.domicilioRecortado.URL_BUSQUEDA_CP = '/gestionDomicilios-web/domicilio/nacional/ubicar/delegacion/asentamiento/get/conParametros'
	//convertimos el formulario en objeto para obtenerl el domicilio actual y el nuevo
	var tramiteCorreccion = $("#correccionDatos").toObject();
	//el id de la delegacion
	var idDelegacion = 0;
	//si no es el asegurado se pasa la delegacion  de lo contrario se pasara delegacion 0
	if($("#esAsegurado").val() != "true" && $("#tieneAcuerdo").val()  <= 0){
		idDelegacion = $("#idDelegacion").val();
	}
	//otros parametros de busqueda
	var otrosParametros = {
		'idDelegacion' : idDelegacion,
		'idUmfOrigen':0,
		'idUmfDestino':$("#idUMFUsuario").val()
	};
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
		classButtonLimpiar: 'mboton', //cssClass del boton limpiar del formulario de domicilio
		parametrosBusqueda: otrosParametros
	};	
	
	//inicializamos el componente de domicilio recortado
	$("#"+ID_DIV_DOM_ACTUAL).domicilioRecortado($.extend({},opcionesComunes,{
		domicilioDefault: tramiteCorreccion.domicilioAnterior,
		formularioDeshabilitado:true //deshabillitamos el formulario de inicio
	}));
	
	//inicializamos el componente de domicilio recortado
	$("#"+ID_DIV_DOM_NUEVO).domicilioRecortado($.extend({},opcionesComunes,{
		domicilioDefault: tramiteCorreccion.domicilio,
		funcionOk: funcionAsentamientosEncontrados,
		funcionLimpiar: funcionLimpiarDatosMedico,
		formularioDeshabilitado: validacion //si estamos en la validacion bloqueamos tambien este domicilio
	}));	
}

/**
 * Validamos que el domicilio capturado en el componente sea correcto
 */
var validarDomicilioCapturado = function(funcion) {
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

var funcionLimpiarDatosMedico = function() {
	//escondemos los datos del medico
	$("#medicoTurno").hide();
	//oculatamos el boton aceptar
	$("#aceptar").hide();
	//limpiamos los datos del medico
	limpiarDatosMedico();
}