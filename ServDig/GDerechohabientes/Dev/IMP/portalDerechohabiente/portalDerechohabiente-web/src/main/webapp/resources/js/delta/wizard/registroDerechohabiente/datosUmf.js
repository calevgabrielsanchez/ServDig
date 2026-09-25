/**
 * 
 */
var medicoEnTurnoActivo;
var MISMA_UMF_ASEGURADO = false;
var MENSAJE_INTEGRANTE_REGISTRADO = "Se encontr&oacute; al menos un miembro del grupo familiar en esta UMF, por lo tanto los datos de adscripci&oacute;n ser&aacute;n los mismos.";

CONTEXT_PATH_APLICACION = '/${mvn.web.app.root}';

$(document).ready(
	function() {
		//console.log("La homoclave del tramite es: " +$("#homoclaveTramite").val());
		MISMA_UMF_ASEGURADO = $("#mismaUmf").val() == 1;
		
		$("#formRegistro").deshabilitarContenido(false);
		
		getUmfsDisponibles();
		
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF").change(function() {
			setDatosUmf();
		});
		
		$("#medicoEnTurno\\.turno\\.idTurno").change(function() {
			var idUmf = $('#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF').val();
			var idTurno = $('#medicoEnTurno\\.turno\\.idTurno').val();
			var idTurnoAnterior = $("#turnoSeleccionado").val();
			
			if(idTurno != idTurnoAnterior) {
				$("#consultorioSeleccionado").val("");
				//console.debug("Se limpiar consultorio");
			}
			
			limpiarConsultorio();
			if($.trim(idUmf).length > 0)
				setConsultorios(idUmf,idTurno);
		});
		
		$("#medicoEnTurno\\.consultorio\\.idConsultorio").change(function() {
			
			var idUmf = $('#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF').val();
			var idTurno = $('#medicoEnTurno\\.turno\\.idTurno').val();
			var idConsultorio = $('#medicoEnTurno\\.consultorio\\.idConsultorio').val();
			
			setMedico(idUmf,idTurno,idConsultorio);
			
		});
		
		$('#capturarDocumentos').click(function() {
			var tipoTramite = $("#tipoTramite\\.idTipoTramite").val();
			var idTramite = $("#tramiteId").val();
			var curpDocumento = $("#fisica\\.curp").val();
			var registroConyuge = $("#hdnRegistroConyuge").val() == "1" ? true : false;
			var mismoSexo = $("#hdnMismoSexo").val() == "1" ? true : false;
			var documentosANoMostrar = "0";
			var hijosProcreados = $("#indHijosProcreados").val() == "1" ? true : false;
			
			if(registroConyuge) {
				documentosANoMostrar = mismoSexo ? "2" : "65";
			}else if(!hijosProcreados && tipoTramite == "46"  ){
				documentosANoMostrar ="1_3_4";
			}
			
			
			if(!parent.WizardCapturaDocumentosProbatoriosCtrl.isPrimeraCaptura()) {
				mostrarMensajeDocumentosExixtentes();
			} else {
				var curpCap = curpDocumento == "" ? null : curpDocumento;
				var datosComplementarios = {'curp' : curpCap, 'documentosNoMostrados': documentosANoMostrar};
				parent.WizardCapturaDocumentosProbatoriosCtrl.init("divCapturaDocs",tipoTramite,idTramite,datosComplementarios);
				parent.WizardCapturaDocumentosProbatoriosCtrl.abrir();
			}
		});
		
		$("#finalizarTramite").click(function() {
			validarUmf();
		});
		
		setEventosChecarError("formRegistro",verificarErrores);
	}
);

function validarUmf() {
	
	var requiereDocs = $("#requiereDocs").val() == 1;
	$("form#formRegistro").habilitarContenido(false);
	var oForm = $("form#formRegistro").toObject();
	habilitarDesabilitarCamposDatosBasicos(false,false);
	var url = CONTEXT_PATH_APLICACION + '/wizard/registro/validaciones';
	fnHideErrores("form#formRegistro");
	$.blockUI();
	$.postJSON(url, oForm, function(data2) {
		
		$.unblockUI();
		
		if(requiereDocs && !parent.WizardCapturaDocumentosProbatoriosCtrl.isCapturaFinalizada()) {
			mostrarMensajeError("Debe completar la documentaci&oacute;n para finalizar el tr&aacute;mite");
		} else {
			invocarFirmaDigital();
		}
	}).error(function(data){
		$.unblockUI();
		fnProcesarErrores(data, "form#formRegistro");
		verificarErrores()
	});
}

function invocarFirmaDigital() {
	
	var numeroArchivos = 0;
	var requiereDoctos= false;
	
	/*
	var requiereDocs = $("#requiereDocs").val() == 1;
	
	
	if(requiereDocs) {
		if(parent.WizardCapturaDocumentosProbatoriosCtrl.getNumeroDocumentosRequeridos() > 0) {
			requiereDoctos = true;
			numeroArchivos = parent.WizardCapturaDocumentosProbatoriosCtrl.getNumeroDocumentosRequeridos();
		}
	}*/
	
	parent.FirmaDigitalCtrl.setOnCloseCallback(function() {
		
		if(parent.FirmaDigitalCtrl.datosSalida == null) {
			mostrarMensaje("La validaci&oacute;n de la firma no pudo ser realizada");
		}else {
			if(parent.FirmaDigitalCtrl.datosSalida.Resultado == 0) {
				var firmaResponse = {
					cadenaOriginal               : parent.FirmaDigitalCtrl.datosSalida.contenedores[0].cadori,
					recibo                       : parent.FirmaDigitalCtrl.datosSalida.firmas[0],
					reciboNotarial               : parent.FirmaDigitalCtrl.datosSalida.folio,
					urlAcuseFirma                : parent.FirmaDigitalCtrl.datosSalida.acuse,
					serialCertificado            : parent.FirmaDigitalCtrl.datosSalida.serie_cert,
					strIniciaVigenciaCertificado : parent.FirmaDigitalCtrl.datosSalida.vigIni,
					strFinVigenciaCertificado    : parent.FirmaDigitalCtrl.datosSalida.vigFin
				};

				firmarTramite(firmaResponse);
			} else {
				mostrarMensaje("La validaci&oacute;n de la firma no pudo ser realizada");
			}
		}
	});
	
	var componenteFirma = {
		tipo_operacion :'firmaCMS',
		acuse:'AcuseV1.0',
		rfc: parent.FirmanteCtrl.rfc,
		validarRFC :true,
		curp: parent.FirmanteCtrl.curp,
		firma_archivo : requiereDoctos,
		min_archivos : numeroArchivos,
		max_archivos : numeroArchivos,
		fechaElectronica : datosEntradaFirma.fechaElectronica,
		cad_original:$('#contenidoFirmar').val(),
		registroPatronal : "",
		nombreCompleto : parent.FirmanteCtrl.nombreRazonSocial,
		idTipoSolicitud : codigoTipoSolicitud,
		descripcionTipoSolicitud : descripcionTipoSolicitud,
		folioSolicitud : $('#hdnFolioSolicitud').val(),
		idTipoTramite : arrayCodigoTipoTramite
	};
	
	parent.iniciarFirmaDigital(componenteFirma);
}

function firmarTramite(firmaResponse) {
	var url = CONTEXT_PATH_APLICACION + '/wizard/registro/procesarDatosFirma';

	$.blockUI();
	$.postJSON(url, firmaResponse, function(data) {
		$.unblockUI();
		finalizarTramite();
	}).error(function(data){
		$.unblockUI();
		$('#mensajeDialogo').text(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
}

function finalizarTramite() {
	
	var existeError = false;
	$("form#formRegistro").habilitarContenido(false);
	var registro = $("form#formRegistro").toObject();
	habilitarDesabilitarCamposDatosBasicos(false,false);
	var url = CONTEXT_PATH_APLICACION + '/wizard/registro/finalizar';
	
	var buttons = [{
		text : 'Aceptar',
		click : function() {
			parent.WizardRegistroDerechohabienteCtrl.datosSalida.salir = true;
			$(this).dialog('close');
			cerrarWizard();
		}
	}         
	];
	
	$.blockUI();
	$.postJSON(url, registro, function(data) {
		$.unblockUI();
		if(data.error) {
			existeError=true;
			var datosSalidaR = {
				registroCorrecto : false,
				folioSolicitud : "",
				mostrarDocumentos: false,
				redireccionar: false
			};
			parent.WizardRegistroDerechohabienteCtrl.setDatosSalida(datosSalidaR);
			dialogoConfirmar.dialog("option", "buttons",buttons);
			
			
		} else {
			
			parent.WizardRegistroDerechohabienteCtrl.datosSalida.registroCorrecto = true;
			parent.WizardRegistroDerechohabienteCtrl.datosSalida.folioSolicitud = $("#hdnFolioSolicitud").val();
			
			buttons.push({
				text : 'Ver documentos',
				click : function() {
					parent.WizardRegistroDerechohabienteCtrl.datosSalida.mostrarDocumentos= true;
					parent.WizardRegistroDerechohabienteCtrl.datosSalida.redireccionar= false;
					cerrarWizard();
				}
			});
			
			dialogoConfirmar.dialog("option", "width","400px");
			var parentesco = $("#parentesco\\.idParentesco").val();
			
			if(parentesco == 5 || parentesco == 6) {
				buttons.push({
					text : 'Ir al portal asegurado',
					click : function() {
						parent.WizardRegistroDerechohabienteCtrl.datosSalida.mostrarDocumentos= false;
						parent.WizardRegistroDerechohabienteCtrl.datosSalida.redireccionar= true;
						cerrarWizard();
					}
				});
				dialogoConfirmar.dialog("option", "width","500px");
			}
			dialogoConfirmar.dialog("option", "buttons",buttons);
			
		}
		
		$('#mensajeDialogo').html(data.mensaje);
		dialogoConfirmar.dialog('open');
		
		if(!existeError) {
			var homoclave = $("#homoclaveTramite").val();
			
			if($.trim(homoclave).length != 0 && parent.startEncuestaHC) {
				parent.startEncuestaHC(500,homoclave);
			}
		}
	}).error(function(data){
		$.unblockUI();
		$('#mensajeDialogo').html(data.mensaje);
		dialogoConfirmar.dialog('open');
	});
}

function habilitarDesabilitarCamposDatosBasicos(habilitar,envio) {
	if(habilitar) {
		$("#formRegistro").habilitarContenido(false);
	} else {
		$("#formRegistro").deshabilitarContenido(false);
		deshabilitarDatosDeUmf();
	}
}

function deshabilitarDatosDeUmf() {
	
	if(!MISMA_UMF_ASEGURADO) {
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF").removeAttr('disabled');
		var umfSeleccionada = $("#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF").val();
		
		if(umfSeleccionada != "-1" ) {
			var turnoSeleccionado = $("#medicoEnTurno\\.turno\\.idTurno").val();
			if($("#indSeleccionMedico").val() == 1) {
			 $("#medicoEnTurno\\.turno\\.idTurno").removeAttr("disabled");
			}
			
			if(turnoSeleccionado != "" && turnoSeleccionado != "-1") {
				if($("#indSeleccionMedico").val() == 1) {
					$("#medicoEnTurno\\.consultorio\\.idConsultorio").removeAttr("disabled");
				}
			}
		}
	}
}

function getUmfsDisponibles() {
	
	if(!MISMA_UMF_ASEGURADO) {
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF").removeAttr("disabled");
		
		if($("#indSeleccionMedico").val() == 0) {
			$("#mensajeUmfIntegrante").html(MENSAJE_INTEGRANTE_REGISTRADO);
			$("#mensajeIntegranteUmf").show();
		}
	}
	
	var umfSeleccionada = $("#umfSeleccionada").val();
	var turnoSeleccionado = $("#turnoSeleccionado").val();
	var parametrosFuncionOk = [umfSeleccionada,turnoSeleccionado];
	
	console.log("se cambio el parametro de la funcion de busqueda de umf");
	var opciones = {
		codigoPostal :  $.trim($("#cpBusquedaUmf").val()),
		idSelect: "medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF",
		umfSeleccionada: umfSeleccionada,
		funcionOk: funcionOkUmf,
		parametrosOk: parametrosFuncionOk,
		funcionError: functionErrorUmf
	};
	
	findUmfsByCodigoPostal(opciones);
	
}

var funcionOkUmf = function(parametrosfuncion) {
	
	if(parametrosfuncion != null) {
		var umfSeleccionada = parametrosfuncion[0];
		var turnoSeleccionado = parametrosfuncion[1];
		
		if(umfSeleccionada != "" && umfSeleccionada != "-1" && turnoSeleccionado != "" && turnoSeleccionado != "-1") {
			if(!MISMA_UMF_ASEGURADO) {
				if($("#indSeleccionMedico").val() == 1) {
					$('#medicoEnTurno\\.turno\\.idTurno').removeAttr('disabled');
				}
			}
			
			var opciones = {
				idUmf: umfSeleccionada,
				idSelect: "medicoEnTurno\\.turno\\.idTurno",
				idTurnoSeleccionado: turnoSeleccionado,
				funcionOk: funcionOkTurno,
				parametrosOk: parametrosfuncion,
				funcionError: functionErrorTurnos
			};
			
			findTurnosByUmf(opciones);
		}
	}
};

function setTurnos(umfSeleccionada) {
	if($("#indSeleccionMedico").val() == 1) {
		$('#medicoEnTurno\\.turno\\.idTurno').removeAttr('disabled');
	}
	var turnoSeleccionado = $("#turnoSeleccionado").val();
	
	var parametrosfuncion = [umfSeleccionada,turnoSeleccionado];
	
	var opciones = {
		idUmf: umfSeleccionada,
		idSelect: "medicoEnTurno\\.turno\\.idTurno",
		idTurnoSeleccionado: turnoSeleccionado,
		funcionOk: funcionOkTurno,
		parametrosOk: parametrosfuncion,
		funcionSinDatos: functionSinTurno,
		funcionError: functionErrorTurnos
	};
	
	findTurnosByUmf(opciones);

}

var functionErrorUmf = function() {
	mostrarMensajeError("Ocurri&oacute; un error al consultar las UMFs relacionadas con el codigo postal");
};

var functionErrorTurnos = function() {
	mostrarMensajeError("Ocurri&oacute; un error al consultar los turnos relacionados con la UMF");
};

var functionErrorConsultorios = function() {
	mostrarMensajeError("Ocurri&oacute; un error al consultar los consultorios");
};

var functionSinTurno = function() {
	$('#medicoEnTurno\\.turno\\.idTurno').attr("disabled","disabled");
	mostrarMensajeError("No se encontraron turnos relacionados a la Unidad M&eacute;dica Familiar seleccionada.");
};

var functionSinConsultorios = function() {
	mostrarMensajeError("No se encontraron consultorios relacionados al turno seleccionado.");
};

var funcionOkTurno = function(parametrosfuncion) {
	
	if(parametrosfuncion != null) {
		
		var umfSeleccionada = parametrosfuncion[0];
		var turnoSeleccionado = parametrosfuncion[1];
		
		if(umfSeleccionada != "" && umfSeleccionada != "-1" && turnoSeleccionado != "" && turnoSeleccionado != "-1") {
			setConsultorios(umfSeleccionada, turnoSeleccionado);
		}
	}
};

var functionOkConsultorio = function(parametrosfuncion) {
	
	if(parametrosfuncion != null) {
		
		var umfSeleccionada = parametrosfuncion[0];
		var turnoSeleccionado = parametrosfuncion[1];
		var consultorioSeleccionado = parametrosfuncion[2];
		
		if(consultorioSeleccionado != "" && consultorioSeleccionado != "-1") {
			setMedico(umfSeleccionada,turnoSeleccionado,consultorioSeleccionado);
		}
		
	}
};

function setDatosUmf() {
	var index = $("#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF")[0].selectedIndex;
	if(index >0) {
		var umfSel = UMFS_DISPONIBLES[index-1];
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.delegacion\\.id").val(umfSel.subdelegacion.delegacion.id);
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.delegacion\\.descripcion").val(umfSel.subdelegacion.delegacion.descripcion);
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.id").val(umfSel.subdelegacion.id);
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.descripcion").val(umfSel.subdelegacion.descripcion);
		
		//Verificamos si existe algun integrante en esta umf
		var url = CONTEXT_PATH_APLICACION + "/umf/getMedicoEnTurnoActivo";
		var umf = {
				"asignacionNSS" : {
					"idAsignacionNSS": $("#idAsignacionNSSSessio").val()
				},
				"parentesco" : {
					"idParentesco" : $("#parentesco\\.idParentesco").val()
				},
				"medicoEnTurno": {
					"unidadMedicaFamiliar": {
						"idUMF" : umfSel.idUMF
					}
				}
		};
		$.blockUI();
		
		$.postJSON(url,umf,
			function(result) {
			
			$.unblockUI();
			
			if(result.error) {
				mostrarMensajeError(result.mensaje);
			} else{
				
				if(result.encontrado) {
					medicoEnTurnoActivo = result.medico;
					$("#medicoEnTurno\\.turno\\.idTurnoError").removeClass("showElement").addClass("hiddenElement");
					$("#medicoEnTurno\\.consultorio\\.idConsultorioError").removeClass("showElement").addClass("hiddenElement");
					verificarErrores();
					$("#indSeleccionMedico").val(result.cambioPosible ? 1 : 0);
					
					if(result.fechaEncontrada)
						$("#fechaCambioMedico").val(result.fechaCambio);
					
					$('#medicoEnTurno\\.turno\\.idTurno').attr('disabled','disabled');
					$('#medicoEnTurno\\.consultorio\\.idConsultorio').attr('disabled','disabled');
					
					$("#turnoSeleccionado").val(medicoEnTurnoActivo.turno.idTurno);
					$("#consultorioSeleccionado").val(medicoEnTurnoActivo.consultorio.idConsultorio);
					$("#mensajeUmfIntegrante").html(MENSAJE_INTEGRANTE_REGISTRADO);
					$("#mensajeIntegranteUmf").show();
					setTurnos(umfSel.idUMF);
				} else {
					$("#fechaCambioMedico").val("");
					$("#turnoSeleccionado").val("");
					$("#consultorioSeleccionado").val("");
					$("#mensajeUmfIntegrante").html("");
					$("#mensajeIntegranteUmf").hide();
					$("#indSeleccionMedico").val(1);
					setTurnos(umfSel.idUMF);
					limpiarMedico();
					limpiarConsultorio();
				}
			}
				
			}	
		);
	}else{
		$("#mensajeUmfIntegrante").html("");
		$("#mensajeIntegranteUmf").hide();
		$("#indSeleccionMedico").val(1);
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.delegacion\\.id").val('');
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.delegacion\\.descripcion").val('');
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.id").val('');
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.descripcion").val('');
		$("#medicoEnTurno\\.turno\\.idTurno").attr("disabled","disabled");
		$("#medicoEnTurno\\.turno\\.idTurno")[0].selectedIndex = 0;
		$("#medicoEnTurno\\.consultorio\\.idConsultorio").attr('disabled','disabled');
		$("#medicoEnTurno\\.consultorio\\.idConsultorio")[0].selectedIndex = 0;
		$("#turnoSeleccionado").val('');
		$("#umfSeleccionada").val('');
		limpiarMedico();
		limpiarConsultorio();
		
	}
}

function setDatosMedicoTurnoActivo() {
	var options = "<option value='" + medicoEnTurnoActivo.turno.idTurno + "' selected='selected'>" + medicoEnTurnoActivo.turno.descripcion + "</option>";
	$("#medicoEnTurno\\.turno\\.idTurno").html(options);
	$("#medicoEnTurno\\.turno\\.idTurno").attr("disabled","disabled");
	options = "<option value='" + medicoEnTurnoActivo.consultorio.idConsultorio + "' selected='selected'>" + medicoEnTurnoActivo.consultorio.descripcion + "</option>";
	$("#medicoEnTurno\\.consultorio\\.idConsultorio").html(options);
	$("#medicoEnTurno\\.consultorio\\.idConsultorio").attr("disabled","disabled");
	$("#medicoEnTurno\\.idMedicoContultorioTurno").val(medicoEnTurnoActivo.idMedicoContultorioTurno);
	if(medicoEnTurnoActivo.medicoFamiliar != null && medicoEnTurnoActivo.medicoFamiliar != undefined) {
		$("#medicoEnTurno\\.medicoFamiliar\\.idMedicoFamiliar").val(medicoEnTurnoActivo.medicoFamiliar.idMedicoFamiliar);
		$("#medicoEnTurno\\.medicoFamiliar\\.noMatricula").val(medicoEnTurnoActivo.medicoFamiliar.noMatricula);
		$("#medicoEnTurno\\.medicoFamiliar\\.nombre").val(medicoEnTurnoActivo.medicoFamiliar.nombre+" "+medicoEnTurnoActivo.medicoFamiliar.primerApellido+" "+medicoEnTurnoActivo.medicoFamiliar.segundoApellido);
	}else {
		$("#medicoEnTurno\\.medicoFamiliar\\.idMedicoFamiliar").val("");
		$("#medicoEnTurno\\.medicoFamiliar\\.noMatricula").val("-");
		$("#medicoEnTurno\\.medicoFamiliar\\.nombre").val("-");
	
	}
	
	if(medicoEnTurnoActivo.medicoEspecialidad != null && medicoEnTurnoActivo.medicoEspecialidad != undefined ) {
		$("#medicoEnTurno\\.medicoEspecialidad\\.idMedicoEspacialidad").val(medicoEnTurnoActivo.medicoEspecialidad.idMedicoEspacialidad);
		$("#medicoEnTurno\\.medicoEspecialidad\\.descripcion").val(medicoEnTurnoActivo.medicoEspecialidad.descripcion);
	} else {
		$("#medicoEnTurno\\.medicoEspecialidad\\.idMedicoEspacialidad").val("");
		$("#medicoEnTurno\\.medicoEspecialidad\\.descripcion").val("-");
	}
}

function limpiarConsultorio() {
	//console.debug("Se limpiar el consultorio");
	$("#medicoEnTurno\\.idMedicoContultorioTurno").val("");
	$("#medicoEnTurno\\.consultorio\\.idConsultorio").html("<option value='-1' selected='selected'> -- Por favor seleccione -- </option>");
}

function limpiarMedico() {
	$("#medicoEnTurno\\.idMedicoContultorioTurno").val("");
	$("#medicoEnTurno\\.medicoFamiliar\\.idMedicoFamiliar").val("");
	$("#medicoEnTurno\\.medicoFamiliar\\.noMatricula").val("");
	$("#medicoEnTurno\\.medicoFamiliar\\.nombre").val("");
	$("#medicoEnTurno\\.medicoEspecialidad\\.idMedicoEspecialidad").val("");
	$("#medicoEnTurno\\.medicoEspecialidad\\.descripcion").val("");
}

function setConsultorios(idUmf , idTurno) {
	
	if($("#indSeleccionMedico").val() == 1) {
		$('#medicoEnTurno\\.consultorio\\.idConsultorio').removeAttr('disabled');
	}
	
	$("#medicoEnTurno\\.consultorio\\.idConsultorio")[0].selectedIndex = 0;

	var idConsultorio = $("#consultorioSeleccionado").val();
	limpiarMedico();
	
	var parametrosfuncion = [idUmf,idTurno,idConsultorio];
	
	var opciones = {
		idUmf: idUmf,
		idTurno : idTurno,
		idSelect: 'medicoEnTurno\\.consultorio\\.idConsultorio',
		idConsultorioSeleccionado: idConsultorio,
		mostrarVirtuales: 0,
		funcionOk: functionOkConsultorio,
		parametrosOk: parametrosfuncion,
		funcionSinDatos: functionSinConsultorios,
		funcionError: functionErrorConsultorios
	};
	
	findConsultoriosByUmfTurno(opciones);
}

function setMedico(idUmf,idTurno,idConsultorio) {
	var opcionesBusqueda = {
		idUmf: idUmf, 
		idTurno: idTurno,
		idConsultorio: idConsultorio, 
		funcionSeter: setDatosMedico
	};
	
	getMedicobyUmfTurnoConsultorio(opcionesBusqueda);
	
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

function mostrarMensajeDocumentosExixtentes() {
	dialogoConfirmar.dialog("option", "buttons", [{
		text : 'Cancelar',
		click : function() {
			$(this).dialog('close');
		}
	},{
		text : 'Continuar',
		click : function() {
			var tipoTramite = $("#tipoTramite\\.idTipoTramite").val();
			var idTramite = $("#tramiteId").val();
			parent.WizardCapturaDocumentosProbatoriosCtrl.init("divCapturaDocs",tipoTramite,idTramite);
			parent.WizardCapturaDocumentosProbatoriosCtrl.abrir();
			$(this).dialog('close');
		}
	}]);
	
	$('#mensajeDialogo').html("Se eliminaran los datos de los documentos que ya se han capturado");
	dialogoConfirmar.dialog('open');
}

function verificarErrores() {
	marcarCamposConErrores("datosAdscripcionTable",".error","td");
}