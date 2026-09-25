var personasEncontradas = null;
var personaUnica = false;
var sinServicio = false;

$(document).ready(
	function() {
		habilitarDatosBasicos(false,true);
		
		$("#fechaNacimiento").datepicker({
			showOn: 'focus',
			dateFormat: 'dd/mm/yy',
			changeMonth: true,
			changeYear: true,
			maxDate: new Date(),
			regional:'es',
			yearRange: '-112:+0'
		});
		
		$("#buscar").click(
			function() {
				ocultarMensajeError();
				ocultarMensajePersonaFallecida();
				personasEncontradas = null;
				personaUnica = false;
				var curp = $("#curp").val();
				habilitarDatosBasicos(false,true);
				$("#curp").val(curp);
				//console.debug("se presiono boton de busqueda");
				quitarEspaciosSeguidos("busquedaPersonaFisica");
				validacionFormulario();
			}	
		);
		
		$("#limpiar").click(
			function() {
				if(!sinServicio) {
					ocultarMensajeError();
					ocultarMensajePersonaFallecida();
					personasEncontradas = null;
					personaUnica = false;
					$("#aceptar").hide();
					//console.debug("se presiono boton de limpiar");
					limpiarContenidoTabla();
					habilitarDatosBasicos(false,true);
				} else {
					capturaManual();
				}
				setSizeWithinIframe(document);
				fnHideErrores("form#busquedaPersonaFisica");
				verificarErrores();
			}
		);
		
		$("#aceptar").click(
			function() {
				if(personaUnica) {
					seleccionarPersona(0);
				} else {
					validacionesCapturaManual();
				}
			}
			
		);
		
		$("#habilitaCurp").click(function() {
			habilitarConsultaPorCurp(true);
		});
		
		setEventosChecarError("busquedaPersonaFisica",verificarErrores);
	}
);

function habilitarConsultaPorCurp(habilitar) {
	fnHideErrores("form#busquedaPersonaFisica");
	if(habilitar) {
		personasEncontradas = null;
		personaUnica = false;
		sinServicio = false;
		habilitarDatosBasicos(false,true);
		
		ocultarMensajeError();
		ocultarMensajePersonaFallecida();
		$("#habilitaCurp").hide();
		$("#buscar").show();
	} else {
		$("#habilitaCurp").show();
		$("#buscar").hide();
		habilitarDatosBasicos(true,true);
	}
}

function validacionesCapturaManual() {
	$.blockUI();
	$('div#personasWrapper').habilitarContenido();
	var fisica = $("#busquedaPersonaFisica").toObject();
	habilitarDatosBasicos(true, false);
	
	var url = '/gestionAsegurados-web-externo/wizard/busqueda/persona/validacionesBasicos';
	fnHideErrores("form#busquedaPersonaFisica");
	
	$.postJSON(url, fisica, function(data2) {
		verificarErrores();
		seleccionarPersonaCapturada();
	}).error(function(data){
		fnProcesarErrores(data, "form#busquedaPersonaFisica");
		verificarErrores();
		$.unblockUI();
	});
}

function seleccionarPersonaCapturada() {
	
	$('div#personasWrapper').habilitarContenido();
	var fisica = $("#busquedaPersonaFisica").toObject();
	fisica.fechaNacimientoFormateada = $("#fechaNacimiento").val();
	personasEncontradas = new Array();
	personasEncontradas.push(fisica);
	
	seleccionarPersona(0);
}

function buscarPersona(fisica) {
	var url = '/gestionAsegurados-web-externo/wizard/busqueda/persona/buscar';
	
	$.blockUI();
	$.postJSON(url, fisica, function(result) {
		if(result.error != undefined && result.error != null) {
			procesarErroresEnServicioBusqueda(result);
			$.unblockUI();
		} else {
			procesarPersonasEncontradas(result);
			$.unblockUI();
		}
	});
}

function limpiarContenidoTabla() {
	var tabla = $("#personasFisicasFoundIMSSTable");
	$(tabla).find('tbody').html('');
	mostrarListado(false);
}

function validacionFormulario() {
	var oForm = $("form#busquedaPersonaFisica").toObject();
	var url = '/gestionAsegurados-web-externo/wizard/busqueda/persona/validaciones';
	fnHideErrores("form#busquedaPersonaFisica");
	
	$.postJSON(url, oForm, function(data2) {
		verificarErrores();
		buscarPersona(oForm);
	}).error(function(data){
		fnProcesarErrores(data, "form#busquedaPersonaFisica");
		verificarErrores();
		setSizeWithinIframe(document);
	});
}

function procesarErroresEnServicioBusqueda(datosRespuesta) {
	var origenInternet = $("#origenPeticion").val() == 2;
	//verificamos si el servicio de resnapo estuvo disponible
	if(datosRespuesta.servicioNoDisponible) {
		sinServicio = true;
		var mensajeErrorA = datosRespuesta.error;
		if(!origenInternet) {
			capturaManual();
			mensajeErrorA += ". Capture los datos basicos de la persona para registrarla manualmente.";
		} else {
			mensajeErrorA += ". No es posible ubicar a la persona sin utilizar el servicio de RENAPO";
		}
		
		mostrarMensajeError(mensajeErrorA);
	} else if(datosRespuesta.curpNoLocalizada) {
		mostrarMensajeError(datosRespuesta.error);
	}
	
}

function capturaManual() {
	$("#buscar").hide();
	$("#habilitaCurp").show();
	fnHideErrores("form#busquedaPersonaFisica");
	habilitarDatosBasicos(true, true);
}

function mostrarMensajeError(mensaje) {
	$("#divError").show();
	$("#mensajeError").html(mensaje);
}

function mostrarMensajePersonaFallecida(mensaje) {
	$("#divWarningDefuncion").show();
	$("#mensajeDivWarnigDefuncion").html(mensaje);
}

function ocultarMensajePersonaFallecida() {
	$("#divError").hide();
	$("#mensajeDivWarnigDefuncion").html("");
}

function ocultarMensajeError() {
	$("#divWarningDefuncion").hide();
	$("#mensajeError").html("");
}

function procesarPersonasEncontradas(datosRespuesta) {
	personasEncontradas = datosRespuesta.lista;
	
	/*if(personasEncontradas.length == 1) {
	 	mostrarListado(false);
	 	personaUnica = true;
	 	procesarSoloUno(personasEncontradas[0]);
	} else {*/
		mostrarListado(true);
		pintarFilas(personasEncontradas);
		setSizeWithinIframe(document);
	//}
}

function pintarFilas(lista) {
	var tabla = $("#personasFisicasFoundIMSSTable");
	$(tabla).find('tbody').html('');
	if(lista != null && lista != undefined) {
		for(var i=0; i < lista.length ; i++ ) {
			var fila = "<tr>";
			
			fila += "<td>" + mostrarId(lista[i])+ "</td>";
			fila += "<td>" + getTexto(lista[i].rfc)+ "</td>";
			fila += "<td>" + getTexto(lista[i].curp)+ "</td>";
			fila += "<td>" + getTexto(lista[i].nss) + "</td>";
			fila += "<td>" + getTexto(lista[i].nombre) + "</td>";
			fila += "<td>" + getTexto(lista[i].primerApellido) + "</td>";
			fila += "<td>" + getTexto(lista[i].segundoApellido) + "</td>";
			fila += "<td>" + getTexto(lista[i].sexo.descripcion) + "</td>";
			fila += "<td>" + getTexto(lista[i].fechaNacimientoFormateada) + "</td>";
			fila += "<td>" + getTexto(lista[i].lugarNacimiento.nombre) + "</td>";
			fila += "<td>" + getTexto(lista[i].subEstadosFormateados) + "</td>";
			fila += "<td> <button type='button' class='btn btn-primary' onclick='seleccionarPersona("+i+")'> Elegir </button></td>";

			fila += "</tr>";
			$(tabla).find('tbody').append(fila);
		}
		
	}
}

function mostrarId(persona) {
	
	return persona.idPersona != null && persona.idPersona != undefined ? (""+persona.idPersona) : ""; 
}

function procesarSoloUno(persona) {
	
	$("#curp").val(persona.curp);
	$("#nombre").val(persona.nombre);
	$("#primerApellido").val(persona.primerApellido);
	$("#segundoApellido").val(persona.segundoApellido);
	$("#sexo\\.idSexo").val(persona.sexo.idSexo);
	$("#fechaNacimiento").val(persona.fechaNacimientoFormateada);
	$("#lugarNacimiento\\.clave").val(persona.lugarNacimiento.clave);
	
	$("#aceptar").show();
}

function mostrarListado(mostrar) {
	if(mostrar) {
		$("#listadoPersonas").show();
	} else {
		$("#listadoPersonas").hide();
	}
	
}

function getTexto(propiedad) {
	return propiedad == null || propiedad == undefined? '': propiedad;
}

function habilitarDatosBasicos(habilitar,limpiar) {
	
	if(habilitar) {
		$('div#personasWrapper').habilitarContenido();
		$('#fechaNacimiento').datepicker( "option", "showOn", "both" );
		$('#curp').attr("disabled","disabled");
		mostrarRequiredCurp(false);
		$('#aceptar').show();
	} else {
		mostrarRequiredCurp(true);
		$('div#personasWrapper').deshabilitarContenido();
		$('#fechaNacimiento').datepicker( "option", "showOn", "focus" );
		$('#curp').removeAttr("readOnly");
		$('#curp').removeAttr("disabled");
		$('#aceptar').hide();
	}
	
	if(limpiar) {
		$('div#personasWrapper').habilitarContenido();
		$('#busquedaPersonaFisica').clearForm();
		this.habilitarDatosBasicos(habilitar,false);
	} 
}

function mostrarRequiredCurp(mostrar) {
	if(mostrar) {
		$("#requiredCurp").show();
		$("#requiredNombre").hide();
		$("#requiredPrimerApellido").hide();
		$("#requiredSegundoApellido").hide();
		$("#requiredSexo\\.idSexo").hide();
		$("#requiredFechaNacimiento").hide();
		$("#requiredLugarNacimiento\\.clave").hide();
	} else {
		$("#requiredCurp").hide();
		$("#requiredNombre").show();
		$("#requiredPrimerApellido").show();
		$("#requiredSegundoApellido").show();
		$("#requiredSexo\\.idSexo").show();
		$("#requiredFechaNacimiento").show();
		$("#requiredLugarNacimiento\\.clave").show();
	}
}

function seleccionarPersona(indice) {
	var fechaDefuncion = personasEncontradas[indice] != undefined ? personasEncontradas[indice].fechaDefuncion : null;
	if(fechaDefuncion != null && fechaDefuncion != "3000-01-01") {
		mostrarMensajePersonaFallecida("No es posible seleccionar a la persona para realizar el tr&aacute;mite, ya que cuenta con una fecha de defunci&oacute;n: <strong>" + fechaDefuncion + "</strong>.");
	} else {
		parent.BusquedaPersonaPorCurpCtrl.setPersona(personasEncontradas[indice]);
		parent.BusquedaPersonaPorCurpCtrl.cerrar();
	}
}

function quitarEspaciosSeguidos(formulario) {
	$('form#'+formulario+' input[type=text]').each(function(){
		$(this).val($.trim($(this).val()));
		$(this).val($(this).val().replace(/\s+/gi,' '));
		$(this).val($(this).val().replace(/-+/gi,'-'));
		$(this).val($(this).val().replace(/\'+/gi,'\''));
		$(this).val($(this).val().replace(/\.+/gi,'\.'));
	});
}

var verificarErrores = function() {
	marcarCamposConErrores("busquedaPersonaFisica",".error","div");
}