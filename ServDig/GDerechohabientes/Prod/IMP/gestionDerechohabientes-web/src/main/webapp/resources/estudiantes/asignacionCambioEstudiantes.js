var BOTON_UBICAR_DOMICILIO =  null;
var FIELDSET_DOMICILIO = null;
var FIELDSET_DATOS_ADSCRIPCION = null;
var DIV_DATOS_ADSCRIPCION = null;
var UMFS_BUSQUEDA = null;
var ID_UMF_ACTUAL = null;
var ID_COMBO_UMFS = "medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF";
var ID_COMBO_TURNOS = "medicoEnTurno\\.turno\\.idTurno";
var ID_COMBO_CONSULTORIOS = "medicoEnTurno\\.consultorio\\.idConsultorio";
var COMBO_TURNO = null;
var COMBO_CONSULTORIO = null;
var BOTON_ACEPTAR = null;
var BOTON_REGRESAR = null;

$(document).ready(
	function() {
		
		//obtenemos las umfs
		setUmfBusqueda();
		//llenamos los objetos de la vista
		llenarObjetosVista();
		//iniciamos el wizard de domicilios-umf
		iniciarComponenteDomicilio();
		//Seteamos el evento del boton de ubicar domicilio
		BOTON_UBICAR_DOMICILIO.click(ubicarDomicilio);
		//se setea evento combo turnos
		COMBO_TURNO.change(functionCambioTurno);
		//se setea evento de cambio de consultorio
		COMBO_CONSULTORIO.change(functionCambioConsultorio);
		//se agrega evento boton aceptar
		BOTON_ACEPTAR.click(functionAceptar);
		//Se asignac evento boton regrear
		BOTON_REGRESAR.click(salirCorreccion);
	}
);


var salirCorreccion = function() {

	$decision = $('<div></div');

	$decision.dialog({
		autoOpen : false,
		resizable : false,
		width : 500,
		title : 'Seleccione una opci\u00F3n',
		modal : true,
		buttons : {
			"Si" : function() {
				var cambio = $("#cambioclinica").val() == "1";
				if(!cambio) {
					location.href = context_path +"/welcome/uno/busqueda";
				} else {
					location.href = context_path +"/inicio/grupoFamiliar";
				}
				$.blockUI();
				cierraDialogo($(this));
			},
			"No" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text('\u00BF Est\u00E1 Seguro que desea salir del tr\u00E1mite de Correcci\u00F3n? Se perderan todos los datos no guardados');
	$decision.dialog('open');
};

var functionAceptar = function() {
	var validaciones = validaErroresCaptura();
	
	if(validaciones) {
		muestraConfirmacion();
	}
};

var muestraConfirmacion = function() {
	var mensajeError = 'El domicilio y/o UMF del grupo familiar ser&aacute; actualizado.' +
	' De clic an Aceptar para continuar con el proceso.';

	$razonRechazo = $('<div></div');
	$razonRechazo.html(mensajeError);
	$razonRechazo.dialog({
		autoOpen : false,
		title: 'Corfimaci&oacute;n',
		resizable: false,
		modal: true,
		width: 500,
		buttons: {
			"Aceptar" : function() {
				
				cierraDialogo($(this));
				$("#tramiteCorreccion").on("submit",function(){$.blockUI();});
				$("#tramiteCorreccion").habilitarContenido();
				$("#tramiteCorreccion").submit();
			},
			"Cerrar": function() {
				cierraDialogo($(this));
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$razonRechazo.dialog('open');
}

var setUmfBusqueda = function() {
	ID_UMF_ACTUAL = $("#idUmfOrigen").val();
	
	UMFS_BUSQUEDA = {
		'umfUsuario' : ID_UMF_ACTUAL,
		'umfPersona' : ID_UMF_ACTUAL
	};
};

var iniciarComponenteDomicilio = function() {
	DomicilioCtrl.init('componentDomicilio',44,UMFS_BUSQUEDA);
	DomicilioCtrl.setOnCloseCallback(cambiarDomicilio);
};

var ubicarDomicilio = function() {
	DomicilioCtrl.localizar();
};

var llenarObjetosVista = function() {
	BOTON_UBICAR_DOMICILIO = $("#buscaDomicilio");
	FIELDSET_DOMICILIO = $("#datosDomicilioDerechohabiente");
	FIELDSET_DATOS_ADSCRIPCION = $("#datosAdscripcion");
	DIV_DATOS_ADSCRIPCION = $("#divDatosAdscripcion");
	COMBO_TURNO = $("#"+ID_COMBO_TURNOS);
	COMBO_CONSULTORIO = $("#"+ID_COMBO_CONSULTORIOS);
	BOTON_ACEPTAR = $("#aceptar");
	BOTON_REGRESAR = $("#regresarGrupoFamiliar");
};

var cambiarDomicilio = function() {
	var domicilioRetornado =  this;
	
	if(!jQuery.isEmptyObject(domicilioRetornado)) {
		setDomicilioCommon(domicilioRetornado);
		seleccionDeUmf();
	}
}; 

var seleccionDeUmf  = function() {
	DIV_DATOS_ADSCRIPCION.show();
	
	var codigoPostal = $("#domicilio\\.codigoPostal\\.codigoPostal").val();
	var parametrosBusquedaTurnos= {idUmfbusqueda : ID_UMF_ACTUAL};
	findUmfsByCodigoPostal(codigoPostal, ID_COMBO_UMFS, ID_UMF_ACTUAL, functionBusquedaTurnos, parametrosBusquedaTurnos, null,null);
};

var functionBusquedaTurnos = function(parametrosBusquedaTurno) {
	var idUmfBusquedaTurnos = parametrosBusquedaTurno.idUmfbusqueda;
	
	var index = $("#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF")[0].selectedIndex;
	
	if(index >0) {
		var umfSel = UMFS_DISPONIBLES[index-1];
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.delegacion\\.id").val(umfSel.subdelegacion.delegacion.id);
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.delegacion\\.descripcion").val(umfSel.subdelegacion.delegacion.descripcion);
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.id").val(umfSel.subdelegacion.id);
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.descripcion").val(umfSel.subdelegacion.descripcion);
	}
	
	findTurnosByUmf(idUmfBusquedaTurnos, ID_COMBO_TURNOS, null, null, null, null,null);
};

var functionCambioTurno = function() {
	
	var idTurno = $('#medicoEnTurno\\.turno\\.idTurno').val();
	
	if(idTurno != "" && idTurno !="-1") {
		
		limpiarConsultorio();
		setConsultorios(idTurno);
	}
};

function setConsultorios(idTurno) {
	
	limpiarMedico();
	findConsultoriosByUmfTurno(ID_UMF_ACTUAL,idTurno,ID_COMBO_CONSULTORIOS,null, null, null, null,null);
};

function limpiarConsultorio() {
	//console.debug("Se limpiar el consultorio");
	$("#medicoEnTurno\\.idMedicoContultorioTurno").val("");
	$("#medicoEnTurno\\.consultorio\\.idConsultorio").html("<option value='-1' selected='selected'> -- Por favor seleccione -- </option>");
};

function limpiarMedico() {
	$("#medicoEnTurno\\.idMedicoContultorioTurno").val("");
	$("#medicoEnTurno\\.medicoFamiliar\\.idMedicoFamiliar").val("");
	$("#medicoEnTurno\\.medicoFamiliar\\.noMatricula").val("");
	$("#medicoEnTurno\\.medicoFamiliar\\.nombre").val("");
	$("#medicoEnTurno\\.medicoEspecialidad\\.idMedicoEspecialidad").val("");
	$("#medicoEnTurno\\.medicoEspecialidad\\.descripcion").val("");
};

function functionCambioConsultorio() {
	
	var idTurno = $("#"+ID_COMBO_TURNOS).val();
	var idConsultorio = $("#"+ID_COMBO_CONSULTORIOS).val();
	if(idConsultorio == "" || idConsultorio == "-1") {
		limpiarMedico();
	} else {
		getMedicobyUmfTurnoConsultorio(ID_UMF_ACTUAL, idTurno, idConsultorio, setDatosMedico, null,null);
	}
	
}

var setDatosMedico = function(medico) {
	$("#medicoEnTurno\\.idMedicoContultorioTurno").val(medico.idMedicoContultorioTurno);
	if(medico.medicoFamiliar != null && medico.medicoFamiliar != undefined) {
		$("#medicoEnTurno\\.medicoFamiliar\\.idMedicoFamiliar").val(medico.medicoFamiliar.idMedicoFamiliar);
		$("#medicoEnTurno\\.medicoFamiliar\\.noMatricula").val(medico.medicoFamiliar.noMatricula);
		$("#medicoEnTurno\\.medicoFamiliar\\.nombre").val(medico.medicoFamiliar.nombre+" "+medico.medicoFamiliar.primerApellido+" "+medico.medicoFamiliar.segundoApellido);
	} else {
		$("#medicoEnTurno\\.medicoFamiliar\\.idMedicoFamiliar").val("");
		$("#medicoEnTurno\\.medicoFamiliar\\.noMatricula").val("-");
		$("#medicoEnTurno\\.medicoFamiliar\\.nombre").val("-");
	
	}
	if(medico.medicoEspecialidad != null && medico.medicoEspecialidad != undefined ) {
		$("#medicoEnTurno\\.medicoEspecialidad\\.idMedicoEspacialidad").val(medico.medicoEspecialidad.idMedicoEspacialidad);
		$("#medicoEnTurno\\.medicoEspecialidad\\.descripcion").val(medico.medicoEspecialidad.descripcion);
	} else {
		$("#medicoEnTurno\\.medicoEspecialidad\\.idMedicoEspacialidad").val("");
		$("#medicoEnTurno\\.medicoEspecialidad\\.descripcion").val("-");
	}
};

var validaErroresCaptura = function() {
	var mensaje = "";
	var validacion = true;
	
	if(document.getElementById('domicilio.asentamiento.nombre').value == ""){
		mensaje = mensaje + "Es necesario capturar el domicilio";
		validacion = false;
	}
	
	if(mensaje=="") {
			var turnoSeleccionado = $("#medicoEnTurno\\.turno\\.idTurno").val();
			var consultorioSeleccionado = $("#medicoEnTurno\\.consultorio\\.idConsultorio").val();
			
			if(turnoSeleccionado == "-1" || consultorioSeleccionado=="-1") {
				mensaje = " Es necesario elegir el consultorio y turno";
				validacion = false;
			}
	}
	
	if(!validacion) {
		mostrarMensajeErrorAsignacion(mensaje);
	}
	
	return validacion;
};

function mostrarMensajeErrorAsignacion(mensaje) {
	var mensajeError = '<div class="ui-widget">' +
	'<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">'+
	'<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>' +
	'<strong>' + mensaje +'</strong></p></div></div>';

	$razonRechazo = $('<div></div');
	$razonRechazo.html(mensajeError);
	$razonRechazo.dialog({
		autoOpen : false,
		title: 'Error de captura',
		resizable: false,
		modal: true,
		width: 500,
		buttons: {
			"Cerrar": function() {
				cierraDialogo($(this));
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$razonRechazo.dialog('open');
}

/**
 * MEtodo para cerrar un dialogo y borrar su contenido
 * @param $dialogo
 */
function cierraDialogo($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
}