/**
 * @author Hugo Armando Martinez
 */
var modInterfaz;

$(function() {
	evaluarTramiteMediosContactoActivo();
	evaluarTramiteMediosContactoRatificado();
	mc = new MedioContacto("mediosContactoContenedor", modInterfaz, tpPropietario, idPropietario, idSolicitud, idSujetoOblgiado);
    var _selectTipoFn = function() { return mc.jqSelectTipo; };
    var fnValidarDatos = mc.validarDatos;
    var alterFnValidarDatos = function(arg1, arg2) {
        this.validarDatos = fnValidarDatos;
        //Option correspondiente al tipo de medio de contacto seleccionado
        var _option = $([_selectTipoFn(), ' > ', 'option:selected'].join(''));
        if (/correo e|facebook|twitter/i.test(_option.text())) {
            var _tmptxt = $(mc.jqTxtFldDesc).val().replace(/^\s+|\s+$/g, '');
            $(mc.jqTxtFldDesc).val(_tmptxt);
            arg2 = _tmptxt
        }
        var retval = this.validarDatos(arg1, arg2);
        this.validarDatos = alterFnValidarDatos;
        return retval;
    };

    $.extend(mc, {validarDatos: alterFnValidarDatos});
	mc.init();
		
});


function evaluarTramiteMediosContactoActivo(){
	$("#mesajeRatificacionDatosContacto").hide();
	if(tramiteDatosContactoActivo){
		modInterfaz = 3; 
		$("#btnGuardarDatosContacto").show();
		$("#btnModificarDatosContacto").hide();
	}else if(!tramiteDatosContactoActivo) {
		modInterfaz = 1; 
		$("#btnModificarDatosContacto").show();
		$("#grupoRatificarDC").show();
		$("#btnGuardarDatosContacto").hide();
	}
}

function evaluarTramiteMediosContactoRatificado(){
	$("#mesajeRatificacionDatosContacto").hide();
	if(tramiteDatosContactoRatificado){
		modInterfaz = 1;
		$("#mesajeRatificacionDatosContacto").show();
		$("#btnGuardarDatosContacto").hide();
		$("#btnModificarDatosContacto").hide();
		$("#chkRatificaDC").attr("checked","checked");
	}
}

function callbackActualizacionContactoTramite(response){
	if(isOpRatificacion){
		checkedObject = $('#chkRatificaDC');//Se asigna a la variable global checkedObject el objeto check que se deseleccionara en caso de presionar cancelar en la pantalla de confirmación		
		callbackValidacionTramiteActivo(response, ratificaTramiteDatosContacto, deseleccionarCheck);
		isOpRatificacion=false;
	}else{
		callbackValidacionTramiteActivo(response, guardarDatosContactoPersona);
	}
}

function guardarDC(){
	construirDialogoConfirmar('Guardar',guardarDatosContactoPersona);
}

function guardarDatosContactoPersona(){
	
	var listaDeMediosContactoPersona=mc.obtenerListaMediosContacto();
	//Inicializa el objeto sujetoObligadoTramite
	inicializarObjetoTramite();
	
	if (tipoPersonaFiscal == "FISICA") {
		sujetoObigadoTramite.fisica.mediosContacto=listaDeMediosContactoPersona;
	} else {
		sujetoObigadoTramite.moral.mediosContacto=listaDeMediosContactoPersona;
	}
	
	sendToServer('/afiliacion/actualizarTramite?tipoTramite='+datosContacto+'&idSolicitud='+idSolicitud,sujetoObigadoTramite,
			callbackDatosContactoTramite, false);
}

function callbackDatosContactoTramite(response){
	callbackEnviarTramite(response, 150, 750);
	evaluarBotonesSolicitud();
	//	oTableTramites.fnDraw();
}

function ratificaDT(){
	var esRatificado = $("#chkRatificaDC").attr("checked");
	if(esRatificado == undefined){//Esto significa que el elemento no esta checado
		tramiteDatosContactoRatificado=false;
		evaluarTramiteMediosContactoActivo();
		mc.modoDespliegue(modInterfaz);
	}else{//Si esta checado
		isOpRatificacion=true;
		var rfcEnviarValidacion;
		if (tipoPersonaFiscal == "FISICA") {
			rfcEnviarValidacion=$("#fisica\\.rfc").val();
		}else{
			rfcEnviarValidacion=$("#moral\\.rfc").val();
		}
		validaSolicitudTramiteActivo('/afiliacion/validarTramiteActivo?tipoTramite='+datosContacto+'&idSolicitud='+idSolicitud+'&rfc='+rfcEnviarValidacion,callbackActualizacionContactoTramite);
	}
}

function ratificaTramiteDatosContacto(){
	construirSujetoObligadoDatoContacto();
	sendToServer('/afiliacion/ratificarTramite?tipoTramite='+tipoTramiteDatosContacto+'&idSolicitud='+idSolicitud,sujetoObigadoTramite,
			callbackRatificarDatosContactoTramite, false);
}

function construirSujetoObligadoDatoContacto(){
	//Inicializa el objeto sujetoObligadoTramite [Definición en el archivo datosGenerales.js]
	var listaDeMediosContactoPersona=mc.obtenerListaMediosContacto();
	//Inicializa el objeto sujetoObligadoTramite
	inicializarObjetoTramite();
	
	if (tipoPersonaFiscal == "FISICA") {
		sujetoObigadoTramite.fisica.mediosContacto=listaDeMediosContactoPersona;
	} else {
		sujetoObigadoTramite.moral.mediosContacto=listaDeMediosContactoPersona;
	}
}

function callbackRatificarDatosContactoTramite(response){
	$.unblockUI();
	callbackEnviarTramite(response, 200, 550);
	if(response.mensajeError!=undefined && response.mensajeError!=null){
		deseleccionarCheck();
		tramiteDatosContactoRatificado=false;
	}else{
		$("#btnGuardarDatosContacto").hide();
		$("#btnRatificarDatosContacto").hide();
		$("#mesajeRatificacionDatosContacto").show();
		mc.modoDespliegue(1);
		tramiteDatosContactoRatificado=true;
		tramiteDatosContactoActivo=true;
		evaluarBotonesSolicitud();
	}
	
	evaluarTramiteMediosContactoActivo();
	evaluarTramiteMediosContactoRatificado();
//	oTableTramites.fnDraw();
}

function modificarDatosContacto(){
	$("#btnRatificarDatosContacto").hide();
	$("#btnModificarDatosContacto").hide();
	$("#btnGuardarDatosContacto").show();
	mc.modoDespliegue(3);
}

