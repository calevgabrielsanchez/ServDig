/**
 * 
 */
var medicoEnTurnoActivo;
var MISMA_UMF_ASEGURADO = false;
var MENSAJE_INTEGRANTE_REGISTRADO = "Se encontr&oacute; al menos un miembro del grupo familiar en esta UMF, por lo tanto los datos de adscripci&oacute;n ser&aacute;n los mismos.";

function setDatosUmf() {
	var index = $("#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF")[0].selectedIndex;
	if(index >0) {
		var umfSel = UMFS_DISPONIBLES[index-1];
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.delegacion\\.id").val(umfSel.subdelegacion.delegacion.id);
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.delegacion\\.descripcion").val(umfSel.subdelegacion.delegacion.descripcion);
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.id").val(umfSel.subdelegacion.id);
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.descripcion").val(umfSel.subdelegacion.descripcion);
		
		//Verificamos si existe algun integrante en esta umf
		var url = context_path+"/umf/getMedicoEnTurnoActivo";
		var umf = {
				"parentesco" : {
					"idParentesco" : $("#parentesco\\.idParentesco").val()
				},
				"medicoEnTurno": {
					"unidadMedicaFamiliar": {
						"idUMF" : umfSel.idUMF
					}
				},
				"asignacionNSS" : {
					"idAsignacionNSS" : $("#idAsignacionNss").val()
				}
		};
		$.blockUI();
		
		$.postJSON(url,umf,
			function(result) {
			
			$.unblockUI();
			
			if(result.error) {
				wizardGeneralDomicilios.mostrarMensajeError(result.mensaje);
			} else{
				
				if(result.encontrado) {
					medicoEnTurnoActivo = result.medico;
					
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
		limpiarDatosDeAdscripcion();
	}
}

function limpiarDatosDeAdscripcion() {
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
	$("#consultorioSeleccionado").val("");
	limpiarMedico();
	limpiarConsultorio();
}

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
		};
	
	findTurnosByUmf(opciones);

}


function getUmfsDisponibles() {
	
	// ------------------------------------------------------------------
	// Esta funcion se ejecuta automaticamente al cargar la pagina.
	// Validamos que la seccion de clinica exista
	// ------------------------------------------------------------------
	var $medicoTurno = $("#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF");
	if( $medicoTurno.length == 0 )
		return;
	
	if(!MISMA_UMF_ASEGURADO) {
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF").removeAttr("disabled");
		
		if($("#indSeleccionMedico").val() == 0) {
			$("#mensajeUmfIntegrante").html(MENSAJE_INTEGRANTE_REGISTRADO);
			$("#mensajeIntegranteUmf").show();
		}
	}
	
	
	var cp = $.trim($("#cpBusquedaUmf").val());
	var umfSeleccionada = $("#umfSeleccionada").val();
	var turnoSeleccionado = $("#turnoSeleccionado").val();
	
	console.log("se modifican parametros")
	
	var parametrosFuncionOk = [umfSeleccionada,turnoSeleccionado];
	
	var opciones = {
			codigoPostal :  $.trim($("#cpBusquedaUmf").val()),
			idSelect: "medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF",
			umfSeleccionada: umfSeleccionada,
			funcionOk: funcionOkUmf,
			parametrosOk: parametrosFuncionOk
		};
	
	findUmfsByCodigoPostal(opciones);
	
}


var funcionOkUmf = function(parametrosfuncion) {
	
	var index = $("#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF")[0].selectedIndex;
	if(index >0) {
		var umfSel = UMFS_DISPONIBLES[index-1];
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.delegacion\\.id").val(umfSel.subdelegacion.delegacion.id);
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.delegacion\\.descripcion").val(umfSel.subdelegacion.delegacion.descripcion);
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.id").val(umfSel.subdelegacion.id);
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.descripcion").val(umfSel.subdelegacion.descripcion);
	}
	
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
					parametrosOk: parametrosfuncion
			};
			
			findTurnosByUmf(opciones);
		}
	}
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
			funcionOk: functionOkConsultorio,
			parametrosOk: parametrosfuncion
	};
	
	findConsultoriosByUmfTurno(opciones);
}




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



function setMedico(idUmf,idTurno,idConsultorio) {
	
	var opcionesBusqueda = {
		idUmf: idUmf, 
		idTurno: idTurno,
		idConsultorio: idConsultorio, 
		funcionSeter: setDatosMedico
	};

	getMedicobyUmfTurnoConsultorio(opcionesBusqueda);
	
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
	}
	
	if(medicoEnTurnoActivo.medicoEspecialidad != null && medicoEnTurnoActivo.medicoEspecialidad != undefined) {
		$("#medicoEnTurno\\.medicoEspecialidad\\.idMedicoEspacialidad").val(medicoEnTurnoActivo.medicoEspecialidad.idMedicoEspacialidad);
		$("#medicoEnTurno\\.medicoEspecialidad\\.descripcion").val(medicoEnTurnoActivo.medicoEspecialidad.descripcion);
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


var setDatosMedico = function(medico) {
	
	$("#medicoEnTurno\\.idMedicoContultorioTurno").val(medico.idMedicoContultorioTurno);
	
	if(medico.medicoFamiliar != null && medico.medicoFamiliar != undefined) {
		$("#medicoEnTurno\\.medicoFamiliar\\.idMedicoFamiliar").val(medico.medicoFamiliar.idMedicoFamiliar);
		$("#medicoEnTurno\\.medicoFamiliar\\.noMatricula").val(medico.medicoFamiliar.noMatricula);
		$("#medicoEnTurno\\.medicoFamiliar\\.nombre").val(medico.medicoFamiliar.nombre+" "+medico.medicoFamiliar.primerApellido+" "+medico.medicoFamiliar.segundoApellido);
	}
	if(medico.medicoEspecialidad != null && medico.medicoEspecialidad != undefined) {
		$("#medicoEnTurno\\.medicoEspecialidad\\.idMedicoEspacialidad").val(medico.medicoEspecialidad.idMedicoEspacialidad);
		$("#medicoEnTurno\\.medicoEspecialidad\\.descripcion").val(medico.medicoEspecialidad.descripcion);
	}
};




$(document).ready(
	function() {
		
		$("form#formUMF").deshabilitarContenido(false);
		
		if($("#mismaUmf").length > 0) {
			MISMA_UMF_ASEGURADO = $("#mismaUmf").val() == 1;
		}
		
		getUmfsDisponibles();
		
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF").change(function() {
			fnHideErrores("form#formUMF");
			setDatosUmf();
		});
		
		
		$("#medicoEnTurno\\.turno\\.idTurno").change(function() {
			
			var idUmf = $('#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF').val();
			var idTurno = $('#medicoEnTurno\\.turno\\.idTurno').val();
			var idTurnoAnterior = $("#turnoSeleccionado").val();
			
			if(idTurno != idTurnoAnterior) {
				$("#consultorioSeleccionado").val("");
			}
			
			
			fnHideErrores("form#formUMF");
			limpiarConsultorio();
			if($.trim(idUmf).length > 0)
				setConsultorios(idUmf,idTurno);
		});
		
		$("#medicoEnTurno\\.consultorio\\.idConsultorio").change(function() {
			
			fnHideErrores("form#formUMF");
			
			var idUmf = $('#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF').val();
			var idTurno = $('#medicoEnTurno\\.turno\\.idTurno').val();
			var idConsultorio = $('#medicoEnTurno\\.consultorio\\.idConsultorio').val();
			
			setMedico(idUmf,idTurno,idConsultorio);
			
		});
		
	
		
	}
);

