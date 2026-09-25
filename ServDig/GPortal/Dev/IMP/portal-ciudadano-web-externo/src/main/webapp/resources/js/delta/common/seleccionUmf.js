/**
 * 
 */
turnoBloqueado = false;

$(document).ready(function() {
	
	$("#validarInfo").on('click',validacionesUmfDomicilio);
	$("#cancelarTramite").on('click',mostrarMensajeCancelar);
	$(".salir").on('click',mostrarMensajeCancelar);
	$("#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF").change(eventoCambioIdClinica);
	$("#medicoEnTurno\\.turno\\.idTurno").change(modificacionTurnoSeleccionado);
	
	blockBackButton();
	setEventosChecarError("datosDomicilioUmfForm",verificarErrores);
});

/**
 * Funcion para cambiar de paso
 * @param mostrar
 */
function mostrarUmfPaso(mostrar) {
	var registroHijos= $("#cveTipoTramite").val() == 48,
	paso = registroHijos ? 4: 3;
	
	if(mostrar) {
		$("#li"+paso).addClass("completed");
		$("#infoMensajeClinica").show();
		$("#infoClinica").show();
		$("#divbotones").show();
	} else {
		$("#li"+paso).removeClass("completed");
		$("#infoMensajeClinica").hide();
		$("#infoClinica").hide();
		$("#divbotones").hide();
	}
	
	
}

var muestraPaso3 = function() {
	var codigoPostal = $("#domicilio\\.codigoPostal\\.codigoPostal").val();
	$("#codigoPostalSeleccionado").val(codigoPostal);
	mostrarUmfPaso(true);
}
/**
 * funcion para ubicar las umfs en base a la colonica y no al CP
 */
var eventoCambioColonia = function(asentamiento) {
	
	limpiarDatosUmf("medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF", "medicoEnTurno\\.turno\\.idTurno", "medicoEnTurno\\.consultorio\\.idConsultorio", false);
	limpiarInfoUmf();
	ubicarUmfsByAsentamiento(asentamiento);
}

var mostrarMensajeSinUmf = function () {
	mostrarMensajeError("No se localiz\u00F3 ninguna cl\u00EDnica con el c\u00F3digo postal capturado, por favor pres\u00E9ntese a su cl\u00EDnica m\u00E1s cercana para realizar el tr\u00E1mite.","Error");
}
var mostrarMensajeDomicilio = function() {
	mostrarMensajeError("No se localiz\u00F3 informaci\u00F3n con el c\u00F3digo postal ingresado.", "Error");
	mostrarUmfPaso(false);
}

var ubicarUmfs = function() {
	var codigoPostal = $("#domicilio\\.codigoPostal\\.codigoPostal").val(),
	opciones = {
		codigoPostal : codigoPostal,
		idSelect: "medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF",
		funcionSinDatos: mostrarMensajeSinUmf
	};
	
	findUmfsByCodigoPostal(opciones);
}

var ubicarUmfsByAsentamiento = function(asentamiento) {
	
	if(asentamiento.clave != "-1") {
		var opcionesBusqueda = {
			asentamiento: asentamiento, 
			idSelect: "medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF", 
			funcionSinDatos: mostrarMensajeSinUmf
		};
		
		findUmfsByAsentamiento(opcionesBusqueda);
	}
}

var eventoCambioIdClinica = function() {
	desbloquearTurno()
	
	turnoBloqueado = false;
	var idUmf = $("#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF").val();

	if(idUmf == "-1") {
		limpiarInfoUmf();
		limpiarInfoTurnos();
		limpiarInfoConsultorio();
		$("#medicoEnTurno\\.turno\\.idTurno").removeAttr("disabled");
	} else {
		var tipoTramite = $("#tipoTramite").val();
		if(tipoTramite == 'cambioClinica' || tipoTramite == 'cambioClinicaD' || $("#cveTipoTramite").val() == 48) {
			var idAsignacionNSS = $("#idAsignacionHidde").val();
			var opcionesBusqueda = {
				idAsignacionNss:idAsignacionNSS,
				idParentesco: 5,
				idUmfBusqueda: idUmf,
				funcionSeter: setMedicoActivo,
				funcionNoEncontrado: modificacionUmfSeleccionada
			};
			
			getMedicoActivo(opcionesBusqueda);
		} else {
			desbloquearTurno()
			modificacionUmfSeleccionada();
		}
	}
}

var setMedicoActivo = function(medico) {
	llenarDatosUmf(medico.unidadMedicaFamiliar);
	medico.consultorio.idUmfConsultorioTurnoMedico = medico.idMedicoContultorioTurno;
	$("#medicoEnTurno\\.turno\\.idTurnoError").removeClass("showElement").addClass("hiddenElement");
	$("#medicoEnTurno\\.consultorio\\.idConsultorioError").removeClass("showElement").addClass("hiddenElement");
	verificarErrores();
	var opciones = {
			idUmf: medico.unidadMedicaFamiliar.idUMF,
			idSelect: "medicoEnTurno\\.turno\\.idTurno",
			idTurnoSeleccionado: medico.turno.idTurno,
			funcionOk: setDatosConsultorioDefault,
			parametrosOk: medico.consultorio
	};
	
	findTurnosByUmf(opciones);
}

var setDatosConsultorioDefault = function(consultorio) {
	turnoBloqueado = true;
	setearDatosConsultorios(consultorio);
	$("#medicoEnTurno\\.turno\\.idTurno").attr("disabled","disabled");
	mostrarMensajeError("Existe por lo menos un integrante de su grupo registrado en la UMF seleccionada, los datos de turno y consultorio ser&aacute;n los mismos.","Aviso");
}

/**
 * Metodo que se llama cuando se modifica la umf
 */
var modificacionUmfSeleccionada = function() {
	var idUmf = $("#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF").val();

	limpiarInfoConsultorio();
	if(UMFS_DISPONIBLES != null) {
		var umf = null;
		for(var i= 0 ; i < UMFS_DISPONIBLES.length; i++) {
			if(UMFS_DISPONIBLES[i].idUMF == idUmf) {
				umf = UMFS_DISPONIBLES[i];
				break;
			}
		}
		
		llenarDatosUmf(umf);
		
	}

	var opciones = {
			idUmf: idUmf,
			idSelect: "medicoEnTurno\\.turno\\.idTurno",
			funcionOk: setInfoTurnos
	};
	findTurnosByUmf(opciones);
}

var llenarDatosUmf = function(umf) {
	if(umf != null) {
		$("#nom_corto").html("<strong>"+umf.descripcion+"</strong>");
		if(umf.desDireccion!= null) {
			$("#direccionUmf").html(umf.desDireccion);
		} else {
			$("#direccionUmf").html("No Disponible");
		}
		
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.longitud").val(umf.longitud);
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.latitud").val(umf.latitud);
		
		if(umf.subdelegacion != null) {
			if(umf.subdelegacion.delegacion != null ) {
				$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.delegacion\\.id").val(umf.subdelegacion.delegacion.id);
				$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.delegacion\\.descripcion").val(umf.subdelegacion.delegacion.descripcion);
			} else {
				$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.delegacion\\.id").val("");
				$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.delegacion\\.descripcion").val("");
			}
			
			$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.id").val(umf.subdelegacion.id);
			$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.descripcion").val(umf.subdelegacion.descripcion);
		} else {
			$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.delegacion\\.id").val("");
			$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.delegacion\\.descripcion").val("");
			$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.id").val("");
			$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.descripcion").val("");
		}
	}
}

var setInfoTurnos = function() {
	if(TURNOS_DISPONIBLES != null) {
		
		for(var i =0 ; i < TURNOS_DISPONIBLES.length; i++) {
			var turno = TURNOS_DISPONIBLES[i];
			var horainicio =turno.horaInicioTurno != null ? turno.horaInicioTurno : "N/D";
			var horafin =turno.horaFinTurno != null ? turno.horaFinTurno : "N/D"
			if(turno.idTurno == 1) {
				$("#horarioMatutino").html(horainicio + " a " + horafin);
			} else {
				$("#horarioVespertirno").html(horainicio + " a " + horafin);
			}
		}
		
	} else {
		$("#horarioMatutino").html("No disponible");
		$("#horarioVespertirno").html("No disponible");
	}
}

var limpiarTodo = function() {
	//$("#datosDomicilioUmfForm").reset();
	limpiarUmfs();
	limpiarInfoUmf();
	limpiarInfoTurnos();
	limpiarInfoConsultorio();
	mostrarUmfPaso(false);
	$("#domicilio\\.calle").val("");
	$("#domicilio\\.codigoPostal\\.codigoPostal").val("");
	//se ocultan los errores
	fnHideErrores("form#datosDomicilioUmfForm");
	verificarErrores();
	
}


function limpiarUmfs() {
	UMFS_DISPONIBLES = null;
	$("#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF").html("<option value='-1'>--Seleccione por favor--</option>");
}

function limpiarInfoUmf() {
	$("#nom_corto").html("");
	$("#direccionUmf").html("");
	$("#horarioMatutino").html("N/A");
	$("#horarioVespertirno").html("N/A");
}

function limpiarInfoTurnos() {
	TURNOS_DISPONIBLES = null;
	$("#medicoEnTurno\\.turno\\.idTurno").html("<option value='-1'>--Seleccione por favor--</option>");
}

function limpiarInfoConsultorio() {
	$("#medicoEnTurno\\.consultorio\\.idConsultorio").val("");
	$("#medicoEnTurno\\.idMedicoContultorioTurno").val("");
}
/**
 * Metodo que se llama cuando se cambia o elige el turno
 */
var modificacionTurnoSeleccionado = function() {
	
	var opcionesBusquedaConsultorio = {
		idUmf: $("#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF").val(),
		idTurno: $("#medicoEnTurno\\.turno\\.idTurno").val(),
		funcionSeter: setearDatosConsultorios,
		funcionsindatos: functionSinDatos
	};
	
	//Se busca el consultorio con menor poblacion
	getConsultorioMenorPoblacion(opcionesBusquedaConsultorio);
}


var functionSinDatos = function() {
	$('#medicoEnTurno\\.consultorio\\.idConsultorio').attr("disabled","disabled");
	mostrarMensajeError('Para brindarle una mejor atenci&oacute;n, el tr&aacute;mite de adscripci&oacute;n o cambio de cl&iacute;nica lo podr&aacute; realizar de forma presencial, acudiendo a la Unidad de Medicina Familiar (UMF) m&aacute;s cercana a su domicilio. Puede consultar la ubicaci&oacute;n de su UMF y requisitos en <a href="http://www.imss.gob.mx" target="_blank" rel="noopener noreferrer">www.imss.gob.mx</a> o en la app IMSS Digital.', 'Aviso');
};

/**
 * funcion para setear los datos del consultorio
 */
var setearDatosConsultorios = function(consultorio) {
	//si el consultorio es nulo se seteran los datos
	if(consultorio != null) {
		$("#medicoEnTurno\\.consultorio\\.idConsultorio").val(consultorio.idConsultorio);
		$("#medicoEnTurno\\.idMedicoContultorioTurno").val(consultorio.idUmfConsultorioTurnoMedico)
	}
}

var validacionesUmfDomicilio = function() {
	
	setearDescripciones();
	
	var tipoTramite = $("#tipoTramite").val(),
	oForm = $("#datosDomicilioUmfForm").toObject(),
	persona = busquedaPersonaCtrl != null ? busquedaPersonaCtrl.getPersona() : null,
	domicilio = $("#componenteDomicilio").domicilioRecortado("get"),
	url = context_path + '/derechohabientes/tramite/'+tipoTramite+'/validaciones',
	correoElectronico = $.trim($("#correo").val()),
	telefono = $.trim($("#telefono").val());
	
	
	fnHideErrores("form#datosDomicilioUmfForm");
	verificarErrores();
	
	if(domicilio != null) {
		oForm.domicilio = new Object();
		oForm.domicilio = domicilio;
	}
	
	if(persona != null) {
		var fisica = new Object(),
		correoCapturado = correoElectronico.length > 0,
		telefonoCapturado = telefono.length > 0;
		
		if(correoCapturado) {
			fisica.correoElectronico = {
				correo: correoElectronico
			}
		}
		
		if(telefonoCapturado) {
			fisica.telefonoFijo =  {
				claveLada: telefono
			}
		}
		
		if(correoCapturado || telefonoCapturado) {
			oForm.fisica = fisica;
		}
	}
	
	if(turnoBloqueado) {
		oForm.medicoEnTurno.turno.idTurno=$("#medicoEnTurno\\.turno\\.idTurno").val();
	}
	
	if(validarCodigoPostal()) {
		$.blockUI();
		$.postJSON(url, oForm, function(data2) {
			$.unblockUI();
			if(!data2.correcto)  {
				mostrarMensajeError(data2.mensaje,"Error");
				bloquearTurno();
			} else {
				mensajeConfirmacion(oForm, tipoTramite);
			}
		}).error(function(data){
			$.unblockUI();
			//mostrarMensajeError("Existen errores en la informaci&oacute;n capturada","Error");
			fnProcesarErrores(data, "form#datosDomicilioUmfForm");
			bloquearTurno();
			verificarErrores();
			
			if(domicilio == null) {
				pintarErrorGeneral(true);
			}
			$("#divErrorCampos").get(0).scrollIntoView();
		});
	}
}

var bloquearTurno = function() {
	if(turnoBloqueado) {
		$("#medicoEnTurno\\.turno\\.idTurno").attr("disabled","disabled");
	}
}

var desbloquearTurno = function() {
	if(turnoBloqueado) {
		$("#medicoEnTurno\\.turno\\.idTurno").removeAttr("disabled");
	}
}


var mensajeConfirmacion = function(formulario, tipoTramite) {
	
	var umf = formulario.medicoEnTurno.unidadMedicaFamiliar.descripcion,
	turno = formulario.medicoEnTurno.turno.descripcion,
	consultorio =formulario.medicoEnTurno.consultorio.idConsultorio,
	persona = busquedaPersonaCtrl != null ? busquedaPersonaCtrl.getPersona() : null,
	mensaje = "<strong>Los datos ingresados son los siguientes: </strong><br>";
	
	if(persona != null){
		mensaje +="<strong>Beneficiario:</strong>"+persona.nombreCompleto+"<br>";
	}
	mensaje += "<strong>UMF:</strong> "+ umf + "<br>";
	mensaje += "<strong>Turno:</strong> "+ turno + "<br>";
	mensaje += "<strong>Consultorio :</strong> "+ consultorio + "<br>";
	
	var $mensajeConfirmacion =  $( "#dialog-confirm" );
	$mensajeConfirmacion.html(mensaje);
	$mensajeConfirmacion.dialog({
		autoOpen : false,
		title: 'Confirmaci\u00F3n requerida',
		resizable: false,
		closeOnEscape: false,
		modal: true,
		heigth: 'auto',
		width: 'auto',
		buttons: {
			"Cancelar" : function() {
	        	$(this).dialog("close");
	        	bloquearTurno();
	        },
			"Aceptar" : function() {
				finalizar(formulario,tipoTramite);
	          $(this).dialog("close");
	        }
	        
		}
	}
	);
	
	$mensajeConfirmacion.dialog('open');
}

function finalizar(formulario,tipoTramite) {
	var url = context_path+ '/derechohabientes/tramite/'+tipoTramite+'/finalizar';
	
	$.blockUI();
	$.postJSON(url, formulario, function(data2) {
		$.unblockUI();
		if(!data2.correcto)  {
			bloquearTurno();
			mostrarMensajeError(data2.mensaje,"Error");
		} else {
			$.blockUI();
			location.href = context_path+"/solicitud/finalizada";
		}
	}).error(function(data){
		$.unblockUI();
		//mostrarMensajeError("Existen errores en la informacion capturada","Error");
		fnProcesarErrores(data, "#datosDomicilioUmfForm");
		verificarErrores();
	});
}

function validarCodigoPostal() {
	var codigoPostalSeleccionado = $("#codigoPostalSeleccionado").val(),
	codigoCaptura = $("#domicilio\\.codigoPostal\\.codigoPostal").val();
	
	if(codigoCaptura != codigoPostalSeleccionado) {
		mostrarMensajeError("El c&oacute;digo postal no es el mismo que con el que se hizo la b&uacute;squeda","Error");
		return false;
	}
	
	return true;
}

function mostrarMensajeCancelar() {
	var tipoTramite = $("#tipoTramite").val(),
	$mensajeError =  $( "#dialog-error" ),
	cveTipoTramite = $("#cveTipoTramite").val();
	tipoTramite = cveTipoTramite == 48 ? "registroHijos" : tipoTramite;
	$mensajeError.html("Toda la informaci&oacute;n capturada hasta el momento se perder&aacute;, &iquest;Est&aacute; seguro que desea cancelar?");
	$mensajeError.dialog({
		autoOpen : false,
		resizable: false,
		closeOnEscape: false,
		modal: true,
		heigth: 'auto',
		width: 'auto',
		title:'Advertencia',
		buttons: {
	        "No" : function() {
		          $(this).dialog("close");
		     },
		 	"Si" : function() {
		          $(this).dialog("close");
		          $.blockUI();
		          location.href = context_path+'/derechohabientes/tramite/'+tipoTramite;
		        }
		}
	}
	);

	$mensajeError.dialog('open');
}

function mostrarMensajeError(mensaje, titulo) {
	var $mensajeError =  $( "#dialog-error" );
	$mensajeError.html(mensaje);
	$mensajeError.dialog({
		autoOpen : false,
		title: titulo,
		resizable: false,
		closeOnEscape: false,
		modal: true,
		heigth: 'auto',
		width: 'auto',
		buttons: {
			"Aceptar" : function() {
	          $(this).dialog("close");
	        }
		}
	}
	);

	$mensajeError.dialog('open');
}

function setearDescripciones() {
	if(turnoBloqueado) {
		$("#medicoEnTurno\\.turno\\.idTurno").removeAttr("disabled");
	}
	
	$("#medicoEnTurno\\.consultorio\\.descripcion").val($("#medicoEnTurno\\.consultorio\\.idConsultorio").val());
	setDescripcionCombo("medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF","medicoEnTurno\\.unidadMedicaFamiliar\\.descripcion");
	setDescripcionCombo("medicoEnTurno\\.turno\\.idTurno","medicoEnTurno\\.turno\\.descripcion");
}


/**
 * Metodo que setea el texto seleccionado de un combo a un campo
 * @param idCombo - El id del select
 * @param idDescripcion -  el id del elemento que contendra la descripcion
 */
function setDescripcionCombo(idCombo,idDescripcion) {
	var texto = $("select#"+idCombo+" option:selected").html().toUpperCase();
	//console.log("campo en mayusculas " + texto);
	$("#"+idDescripcion).val(texto);
}

var verificarErrores = function() {
	marcarCamposConErrores("datosDomicilioUmfForm",".error","div");
}