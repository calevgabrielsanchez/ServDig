/**
 * 
 */

function callbackICA() {
	
	
	var datosSalidaICA = parent.identificarCambiosAutomaticosPersonaFisicaCtrl.getDatosSalida();
	
	
	if(datosSalidaICA != null) {
		if(existenErroresIca(datosSalidaICA.traza)) {
			validadoRenapo = false;
			$("#calificacion\\.idCalificacion").val("");
			procesarErroresIca(datosSalidaICA.traza);
		} else {
			if(datosSalidaICA.personaFisicaIMSS != null && datosSalidaICA.personaFisicaIMSS != undefined) {
				var personaIMSS = datosSalidaICA.personaFisicaIMSS;
				
				
				
				if(verificarCambiosDatosBasicosEnIca(datosSalidaICA.cambios)) {
					
					if(existeA) {
						calificadoRenapo = false;
						$("#calificacion\\.idCalificacion").val("");
						mostrarMensajeDiferenciasEnDatosAsegurado(datosSalidaICA.cambios);
					}  else {
						calificadoRenapo = true;
						$("#calificacion\\.idCalificacion").val(1);
						setDatosPersona(personaIMSS);
					}
				} else {
					if(existeA) {
						var mensajeVal = validarCoincidenciaFecha(personaIMSS.fechaNacimientoFormateada);
						
						if(mensajeVal != null) {
							calificadoRenapo = false;
							$("#calificacion\\.idCalificacion").val("");
							errorGenerico(mensajeVal);
						} else {
							calificadoRenapo = true;
							$("#calificacion\\.idCalificacion").val(1);
							setDatosPersona(personaIMSS);
						}
					}
				}
			}
		}
	} else {
		$("#curpCap").val($.trim($("#curpActual").val()));
		calificadoRenapo = false;
		$("#calificacion\\.idCalificacion").val("");
	}
}



function validarCoincidenciaFecha(fecha) {
	var anioActual = $("#anioRegistroNac").val();
	var mesActual = $("#mesRegistroNac").val();
	
	if($("#fechaNacimiento").val() != "") {
		if($("#fechaNacimiento").val() != fecha) {
			return "La fecha de nacimiento no coincide, para poder actualizar la informaci&oacute;n acuda a ventanilla.";
		}
	} else {
		if(anioActual != "" && mesActual != "") {
			var fechaArr = fecha.split('/');
			var anioRenapoCompleto= fechaArr[2];
			var anioRenapo = anioRenapoCompleto.charAt(2) + '' + anioRenapoCompleto.charAt(3);
			var mesRenapo = parseInt(fechaArr[1]);
			
			if(anioActual != anioRenapo || mesActual != mesRenapo) {
				return "La fecha de nacimiento no coincide con el mes y año de nacimiento registrados"; 
			} 
		}
	}
	
	return null;
}

function mostrarMensajeDiferenciasEnDatosAsegurado(cambios) {
	var datosModificados = listarAtributosModificadosXIca(cambios);
	var mensaje = "Se han encontrado las siguientes diferencias a parte de la fecha de nacimiento: <br>" + datosModificados + "<br>";
	mensaje += "Es necesario acudir a la ventanilla de afiliaci&oacute;n para poder realizar la modificaci&oacute;n";
	
	errorGenerico(mensaje);
	
	if($.trim($("#curpActual").val()) == "") {
		$("#curpCap").val("");
	}
}

function existenErroresIca(traza) {
	if((traza.ERROR_CONSULTA_RENAPO != undefined && traza.ERROR_CONSULTA_RENAPO != null)
		|| (traza.CURP_NO_LOCALIZADO != undefined && traza.CURP_NO_LOCALIZADO != null)
	) {
		return true;
	}
	
	return false;
}

function procesarErroresIca(traza) {
	alert("procesarErroresIca invoked at integracionICACorreccion.");
	if(traza.ERROR_CONSULTA_RENAPO != null && traza.ERROR_CONSULTA_RENAPO != null) {
		
		
		if(existeA) {
			if($("#fechaNacimiento").val() == "") {
				$("#curpCap").attr("disabled","disabled");
				$("#curpCap").val("");
				errorGenerico(traza.ERROR_CONSULTA_RENAPO + ". Los datos capturados seran calificados por el IMSS");
				habilitarFecha();
				$("#llamarIca").hide();
			} else {
				$("#curpCap").val("");
			}
		} else {
			$("#curpCap").attr("disabled","disabled");
			$("#curpCap").val("");
			errorGenerico(traza.ERROR_CONSULTA_RENAPO + ". Los datos capturados seran calificados por el IMSS");
			habilitarBeneficiario();
		}
	}
	else if (traza.CURP_NO_LOCALIZADO != undefined && traza.CURP_NO_LOCALIZADO != null) {
		errorGenerico(traza.CURP_NO_LOCALIZADO);
		$("#curpCap").val($.trim($("#curpActual").val()));
	}
}

function habilitarBeneficiario() {
	$("#nombre").removeAttr("disabled");
	$("#primerApellido").removeAttr("disabled");
	$("#segundoApellido").removeAttr("disabled");
	$("#sexo\\.idSexo").removeAttr("disabled");
	$("#lugarNacimiento\\.clave").removeAttr("disabled");
	$("#fechaNacimiento").removeAttr("disabled");
	$("#fechaNacimiento").attr("readOnly","readOnly");
	$("#parentesco\\.descripcion").removeAttr("disabled");
	
	$("#llamarIca").hide();
}

function habilitarFecha() {
	
	var mesNacimiento = parseInt($("#mesRegistroNac").val())-1;
	var anioNacimiento = parseInt('19'+$("#anioRegistroNac").val());
	var ultimoDia = dias(mesNacimiento, anioNacimiento);
	var ultimo = new Date();
	var minimo = new Date();
	minimo.setFullYear(anioNacimiento,mesNacimiento,1);
	ultimo.setFullYear(anioNacimiento,mesNacimiento,ultimoDia);
	
	$("#fechaNacimiento").removeAttr("disabled");
	$("#fechaNacimiento").datepicker( "option", "showOn", "both");
	$("#fechaNacimiento").datepicker( "option", "minDate", minimo);
	$("#fechaNacimiento").datepicker( "option", "maxDate", ultimo);
	
}

function listarAtributosModificadosXIca(cambiosICA) {
	var cambio = "CAMBIO";
	var cambios="<ul>"
	
	if(cambiosICA.nombre == cambio) {
		cambios += "<li>Nombre(s)</li>"
	} 
	
	if(cambiosICA.primerApellido == cambio) {
		cambios += "<li>Primer Apellido</li>"
	}
	
	if(cambiosICA.segundoApellido == cambio) {
		cambios += "<li>Segundo Apellido</li>"
	}
	
	if(cambiosICA.sexo == cambio) {
		cambios += "<li>Sexo</li>"
	}
	
	if(cambiosICA.lugarNacimiento == cambio) {
		cambios += "<li>Lugar de nacimiento</li>"
	}
	
	cambios += "</ul>";
	
	return cambios;
}

function verificarCambiosDatosBasicosEnIca(cambios) {
	var cambio = "CAMBIO";
	
	if(cambios.nombre == cambio) {
		return true;
	} 
	
	if(cambios.primerApellido == cambio) {
		return true;
	}
	
	if(cambios.segundoApellido == cambio) {
		return true;
	}
	
	if(cambios.sexo == cambio) {
		return true;
	}
	
	if(cambios.lugarNacimiento == cambio) {
		return true;
	}
	
	return false;
}

function errorGenerico(mensaje) {
	 var mensajeError = '<div class="ui-widget">' +
		'<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">'+
		'<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>' +
		'<strong>'+mensaje+'</strong></p></div></div>';

		$mensajeE = $('<div></div');
		$mensajeE.html(mensajeError);
		$mensajeE.dialog({
			autoOpen : false,
			title: 'Error',
			show: "blind",
			hide: "explode",
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
		
		$mensajeE.dialog('open');
}


function callbackICAVerificacion() {
	alert("callbackICAVerificacion invocado, existeA: " + existeA);
	
	var datosSalidaICA = parent.identificarCambiosAutomaticosPersonaFisicaCtrl.getDatosSalida();
	
	setTimeout(function() {
		
	    // Do something after 5 seconds
		if(datosSalidaICA != null) {
			if(existenErroresIca(datosSalidaICA.traza)) {
				validadoRenapo = false;
				$("#calificacion\\.idCalificacion").val("");
				procesarErroresIca(datosSalidaICA.traza);
			} else {
				if(datosSalidaICA.personaFisicaIMSS != null && datosSalidaICA.personaFisicaIMSS != undefined) {
					parent.WizardListadoCandidatosCtrl.cerrar();
				} else {
					errorGenerico("No se encontró la persona con el CURP indicado, favor de intentar nuevamente.");
				}
			}
		} else {
			errorGenerico("No se obtuvieron datos desde las entidades externas, favor de intentar mas tarde.");
		}
	}, 5000);
	
	//alert("datosSalidaICA : " + datosSalidaICA + ", datosSalidaICA.personaFisicaIMSS: " + datosSalidaICA.personaFisicaIMSS);
	
	
	
	
}
