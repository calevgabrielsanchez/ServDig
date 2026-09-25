/**
 * 
 */
var medicoEnTurnoActivo;
var MISMA_UMF_ASEGURADO = false;
var MENSAJE_INTEGRANTE_REGISTRADO = "Se encontr&oacute; al menos un miembro del grupo familiar en esta UMF, por lo tanto los datos de adscripci&oacute;n ser&aacute;n los mismos.";

$(document).ready(
	function() {
		
		$("form#formRegistro").deshabilitarContenido(false);
		$("form#formUMF").deshabilitarContenido(false);
		
		getUmfsDisponibles();
		
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF").change(function() {
			fnHideErrores("form#formUMF");
			var deferredFinal = $.Deferred();
			validarUmf(deferredFinal);
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
			var deferredFinal = $.Deferred();
			validarUmf(deferredFinal);
			limpiarConsultorio();
			if($.trim(idUmf).length > 0)
				setConsultorios(idUmf,idTurno);
			
			
		});
		
		$("#medicoEnTurno\\.consultorio\\.idConsultorio").change(function() {
			
			fnHideErrores("form#formUMF");
			var deferredFinal = $.Deferred();
			validarUmf(deferredFinal);
			
			var idUmf = $('#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF').val();
			var idTurno = $('#medicoEnTurno\\.turno\\.idTurno').val();
			var idConsultorio = $('#medicoEnTurno\\.consultorio\\.idConsultorio').val();
			
			setMedico(idUmf,idTurno,idConsultorio);
			
		});
		
		setEventosChecarErrorCampo("formUMF");
		
	}
);



function setDatosUmf() {
	var index = $("#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF")[0].selectedIndex;
	if(index >0) {
		var umfSel = UMFS_DISPONIBLES[index-1];
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.delegacion\\.id").val(umfSel.subdelegacion.delegacion.id);
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.delegacion\\.descripcion").val(umfSel.subdelegacion.delegacion.descripcion);
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.id").val(umfSel.subdelegacion.id);
		$("#medicoEnTurno\\.unidadMedicaFamiliar\\.subdelegacion\\.descripcion").val(umfSel.subdelegacion.descripcion);
		
		//Verificamos si existe algun integrante en esta umf
		var url = "/portalDerechohabiente-web/umf/getMedicoEnTurnoActivo";
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
					
					$('#medicoEnTurno\\.turno\\.idTurno')
					
					quitaErrorCampo('medicoEnTurno.turno.idTurno');
					quitaErrorCampo('medicoEnTurno.consultorio.idConsultorio');
					fnHideErrores("form#formUMF");
					pintarErrorGeneral(false);
					
				} else {
					$("#fechaCambioMedico").val("");
					$("#turnoSeleccionado").val("");
					$("#consultorioSeleccionado").val("");
					$("#mensajeUmfIntegrante").html("");
					$("#mensajeIntegranteUmf").hide();
					$("#indSeleccionMedico").val(1);
					var deferredFinal = $.Deferred();
					validarUmf(deferredFinal);
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
		var deferredFinal = $.Deferred();
		validarUmf(deferredFinal);
		
	}
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
		parametrosOk: parametrosfuncion
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
	
	var parametrosFuncionOk = [umfSeleccionada,turnoSeleccionado];
	
	var opciones =  {
		codigoPostal : cp,
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
			
			//combosUmfMedicoConsultorio.js
			
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
	
	var opcionesBusqueda = {
		idUmf: idUmf,
		idTurno : idTurno,
		idSelect: 'medicoEnTurno\\.consultorio\\.idConsultorio',
		idConsultorioSeleccionado: idConsultorio,
		funcionOk: functionOkConsultorio,
		parametrosOk: parametrosfuncion
	};
	
	findConsultoriosByUmfTurno(opcionesBusqueda);
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
	$("#medicoEnTurno\\.medicoFamiliar\\.idMedicoFamiliar").val(medicoEnTurnoActivo.medicoFamiliar.idMedicoFamiliar);
	$("#medicoEnTurno\\.medicoFamiliar\\.noMatricula").val(medicoEnTurnoActivo.medicoFamiliar.noMatricula);
	$("#medicoEnTurno\\.medicoFamiliar\\.nombre").val(medicoEnTurnoActivo.medicoFamiliar.nombre+" "+medicoEnTurnoActivo.medicoFamiliar.primerApellido+" "+medicoEnTurnoActivo.medicoFamiliar.segundoApellido);
	$("#medicoEnTurno\\.medicoEspecialidad\\.idMedicoEspacialidad").val(medicoEnTurnoActivo.medicoEspecialidad.idMedicoEspacialidad);
	$("#medicoEnTurno\\.medicoEspecialidad\\.descripcion").val(medicoEnTurnoActivo.medicoEspecialidad.descripcion);
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
	$("#medicoEnTurno\\.medicoFamiliar\\.idMedicoFamiliar").val(medico.medicoFamiliar.idMedicoFamiliar);
	$("#medicoEnTurno\\.medicoFamiliar\\.noMatricula").val(medico.medicoFamiliar.noMatricula);
	$("#medicoEnTurno\\.medicoFamiliar\\.nombre").val(medico.medicoFamiliar.nombre+" "+medico.medicoFamiliar.primerApellido+" "+medico.medicoFamiliar.segundoApellido);
	$("#medicoEnTurno\\.medicoEspecialidad\\.idMedicoEspacialidad").val(medico.medicoEspecialidad.idMedicoEspacialidad);
	$("#medicoEnTurno\\.medicoEspecialidad\\.descripcion").val(medico.medicoEspecialidad.descripcion);
};



function validarUmf(deferredFinal) {
	
	var $formaUMF = $("form#formUMF");
	
	// ---------------------------------------
	// No se muestran los combos de UMF
	// ---------------------------------------
	if( $formaUMF.length == 0 ){
		deferredFinal.resolve();
		
	}else{
	
		// ---------------------------------------------
		// Existe la forma con los datos de la clinica
		// ---------------------------------------------
		var oForm = wizardGeneralDomicilios.formToObject("formUMF",true);
		var url = '/${mvn.web.app.root}'+'/wizard/domicilio/validaciones';
		fnHideErrores("form#formUMF");
		
		$.blockUI();
		$.postJSON(url, oForm, function(data2) {
			deferredFinal.resolve(data2);
			pintarErrorGeneral(false);
		}).error(function(data){
			fnProcesarErrores(data, "form#formUMF");
			fnProcesarErroresCampos(data, "form#formUMF");
			deferredFinal.reject(data);
		}).always(function(){
			$.unblockUI();
		});
		
	}
}


function fnProcesarErroresCampos(data, contenedor){
	  var objErrores = jQuery.parseJSON(data.responseText);
	  var form = $(contenedor);
	 
	  for( index = 0 ; index < objErrores.erroresCaptura.length ; index ++){
		  var campo = objErrores.erroresCaptura[index].campo;
			var cssBorder = "1px solid red"
			var cssColor = "red"
			
			//si existe error se buscaran los campos y el requerido
			var idSpanRequired = campo+'Req'
			var idCampoError = campo

			//se obtiene asi para evitar escapar lso caracteres
			var requiredRelacionado = document.getElementById(idSpanRequired);
			var campoRelacionado = document.getElementById(idCampoError);

			//si el campo existe se marcara en rojo
			if(campoRelacionado != undefined && campoRelacionado != null) {
				campoRelacionado.style.border = cssBorder;
			}

			if(requiredRelacionado != undefined && requiredRelacionado != null) {
				requiredRelacionado.style.color = cssColor;
			}
			tieneError = true
	  }
	  pintarErrorGeneral(tieneError);
}


function quitaErrorCampo(element) {
	
	var campo = element;
	
	var idSpanRequired = campo+"Req";
	var idCampoError = campo;

	//se obtiene asi para evitar escapar lso caracteres
	var requiredRelacionado = document.getElementById(idSpanRequired);
	var campoRelacionado = document.getElementById(idCampoError);

	//si el campo existe se marcara en rojo
	if(campoRelacionado != undefined && campoRelacionado != null) {
		campoRelacionado.style.border = "1px solid #ccc";
	}

	if(requiredRelacionado != undefined && requiredRelacionado != null) {
		requiredRelacionado.style.color = "black";
	}
}