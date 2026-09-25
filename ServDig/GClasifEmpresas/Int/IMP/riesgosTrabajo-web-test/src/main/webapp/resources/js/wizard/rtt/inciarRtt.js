var busquedaRTTCtrl = {
	tipoBusqueda: 1,
	tipoRespaldo: 1,
	periodoBusqueda: 0,
	anioActual: 0,
	context: '/${mvn.web.app.root}',
	optionDefault: '<option value=\"0\">--Selecciona por favor--</option>',
	init: function() {
		busquedaRTTCtrl.cargarPeriodos();
		$(".panelRtt").on("click",busquedaRTTCtrl.procesaTipoBusqueda);
		$("#buscarHistorial").on("click", busquedaRTTCtrl.busqueda);
		$("#periodoBusqueda").on("change",{attr:"periodoBusqueda"},busquedaRTTCtrl.setValorCombo);
		$("#abrirVisor").on("click", busquedaRTTCtrl.abrirVisor);
		//inicializamos los combos
		combosComunesCtrl.init();
	},
	periodoBusquedaChange: function() {
		if(busquedaRTTCtrl.periodoBusqueda == busquedaRTTCtrl.anioActual) {
			$("#infoPeriodoActual").show();
		} else {
			$("#infoPeriodoActual").hide();
		}
	},
	procesaTipoBusqueda: function() {
		busquedaRTTCtrl.tipoRespaldo = busquedaRTTCtrl.tipoBusqueda = this.id.replace('rtt','');
		busquedaRTTCtrl.limpiarTipoBusqueda();
		var datosBusqueda = busquedaRTTCtrl.datosBusqueda[busquedaRTTCtrl.tipoBusqueda-1];
		$("#etiquetaBusqueda").html(datosBusqueda.etiqueta);
		$("#"+datosBusqueda.campo).attr("maxlength",datosBusqueda.longitud);
		$("#opcionesNombre")[datosBusqueda.showNom]();
		combosComunesCtrl.limpiarCombos();
		busquedaRTTCtrl.resetPeriodo();
		$("#contenedorRiegosTrabajoHistorial").html('');
	},
	setValorCombo: function(event) {
		var atributo = event.data.attr;
		busquedaRTTCtrl[atributo] = this.value;
		$("#contenedorRiegosTrabajoHistorial").html('');
		if(busquedaRTTCtrl[atributo+"Change"])busquedaRTTCtrl[atributo+"Change"]();
	},
	resetPeriodo: function() {
		$("#periodoBusqueda").val(busquedaRTTCtrl.periodoBusqueda = busquedaRTTCtrl.anioActual);
		busquedaRTTCtrl.periodoBusquedaChange();
	},
	mostrarMensajeError: function(mensaje) {
		$("#error").html(mensaje).show();
		$("#contenedorRiegosTrabajoHistorial").html('');
	},
	ocultarError: function() {
		$("#error").html("").hide();
	},
	errores: {
		datos: "Debe ingresar alguno de los datos para la b\u00FAsqueda.",
		rfc: "R.F.C. debe ser de 12 o 13 caracteres.",
		nombre: "Debe ingresar al menos 3 caracteres para la b\u00FAsqueda."
	},
	limpiarTipoBusqueda: function() {
		$('#parametroBusqueda').val('')
		busquedaRTTCtrl.ocultarError();
	},
	busqueda: function() {
		$('div#contenedorRiegosTrabajoHistorial').html('');
		busquedaRTTCtrl.ocultarError();
		if(busquedaRTTCtrl.validaDatos()){
			busquedaRTTCtrl.ejecutarConsulta();
		}
	}, 
	datoInvalido: function(dato) {
		return dato === "" || dato === undefined || dato === null;
	},
	cargarPeriodos: function() {
		combosComunesCtrl.cargarCombo("/getPeriodos",function(data) {
			busquedaRTTCtrl.periodoBusqueda = busquedaRTTCtrl.anioActual = data.actual;
			combosComunesCtrl.setCombos("periodoBusqueda",busquedaRTTCtrl.periodoBusqueda,data.periodos, null, null);
		});
	},
	validaDatos: function() {
		var tipoBusqueda = busquedaRTTCtrl.tipoBusqueda,
		datosBusqueda= busquedaRTTCtrl.datosBusqueda[busquedaRTTCtrl.tipoBusqueda-1],
		valorCampo=$("#"+datosBusqueda.campo).val();
		
		if(busquedaRTTCtrl.datoInvalido(valorCampo)) {
			busquedaRTTCtrl.mostrarMensajeError(busquedaRTTCtrl.errores['datos']);
			return false;
		}
			
		return datosBusqueda.valida(valorCampo);
	},
	datosBusqueda: [{
		etiqueta: 'N&uacute;mero Registro Patronal',
		longitud: "10",
		url: '/historialRiesgoTrabajo/consultar/',
		sinDato: 'sinNRP',
		campo: 'parametroBusqueda',
		showNom: 'hide',
		error: 'Ocurri\u00F3 un error inesperado al cargar los riesgos de trabajo',
		valida: function(dato) {
			return true;
		}
	},{
		etiqueta: 'Nombre o Raz&oacute;n Social',
		longitud: "",
		url: '/historialRiesgoTrabajo/obtenerNombres/',
		sinDato: 'sinNRS',
		showNom: 'show',
		campo: 'parametroBusqueda',
		error: 'Ocurri\u00F3 un error inesperado al consultar por Nombre o Raz\u00F3n Social',
		valida: function(dato) {
			if (dato.length < 3) {
				busquedaRTTCtrl.mostrarMensajeError(busquedaRTTCtrl.errores['nombre']);
				return false;
			}
			return true;
		}
	},{
		etiqueta: 'R.F.C',
		longitud: "13",
		url: '/historialRiesgoTrabajo/obtenerRFCs/',
		sinDato: 'sinRFC',
		showNom: 'hide',
		campo: 'parametroBusqueda',
		error: 'Ocurri\u00F3 un error inesperado al consultar por RFC',
		valida: function(dato) {
			if (dato.length != 12 && dato.length != 13) {
				busquedaRTTCtrl.mostrarMensajeError(busquedaRTTCtrl.errores['rfc']);
				return false;
			}
			return true;
		}
	}],
	buscarRiesgosPorRP: function(rp) {
		busquedaRTTCtrl.tipoRespaldo = busquedaRTTCtrl.tipoBusqueda;
		busquedaRTTCtrl.tipoBusqueda = 1;
		busquedaRTTCtrl.ejecutarConsulta(rp);
	},
	ejecutarConsulta: function(valorCampo) {
		var busqueda = busquedaRTTCtrl.datosBusqueda[busquedaRTTCtrl.tipoBusqueda-1],
		valorBusqueda = valorCampo != null ? valorCampo : $("#"+busqueda.campo).val(),
		url = busquedaRTTCtrl.context + busqueda.url + busquedaRTTCtrl.getSinDato(valorBusqueda,busqueda.sinDato);
		if(busquedaRTTCtrl.tipoBusqueda==1) {
			url+= "/"+busquedaRTTCtrl.periodoBusqueda;
		} else if(busquedaRTTCtrl.tipoBusqueda == 2) {
			url += "/" + $('input[type=radio][name=busquedaNRS]:checked').val();
		}
		busquedaRTTCtrl.consultaAjaxHistorialRTT(url, busqueda.error);
	},
	consultaAjaxHistorialRTT: function(url, msjError) {
		
		$.ajax({
			url : url,
			data : {
				delegacion: combosComunesCtrl.delegacion,
				subdelegacion: combosComunesCtrl.subdelegacion,
				periodo: busquedaRTTCtrl.periodoBusqueda
			},
			cache: false,
			type: 'POST',
			beforeSend : function() {
				$('div#mensajes > div').show();
				$.blockUI();
			},
			success : function(data) {
				busquedaRTTCtrl.tipoBusqueda = busquedaRTTCtrl.tipoRespaldo;
				var $riesgosTrabajo = $('div#contenedorRiegosTrabajoHistorial');
				$riesgosTrabajo.html(data);
				$riesgosTrabajo.get(0).scrollIntoView();
				$('div#mensajes > div').hide();
				$.unblockUI();
			},
			error : function(data) {
				$('#error').html(msjError);
				$('div#mensajes > div').hide();
				$.unblockUI();
			}
		});
	},
	generarExcelRiesgosTrabajoHistorial: function() {
		busquedaRTTCtrl.abrirModal(busquedaRTTCtrl.context + '/historialRiesgoTrabajo/generarExcel');
	},
	abrirModal: function(url) {
		window.open( url, '_blank');
	},
	abrirVisor: function(){
		busquedaRTTCtrl.abrirModal("/gestionSolicitud-visor-web/portal");
	},
	getSinDato: function(dato, sinDato) {
		if(busquedaRTTCtrl.datoInvalido(dato)){
			return sinDato;
		}
		return dato;
	}
}

$(document).ready(busquedaRTTCtrl.init);

function validarSolicitud() {

	var url = busquedaRTTCtrl.context + '/wizard/riesgosTrabajo/validarSolicitud';

	$.ajax({
				url : url,
				data : null,
				cache: false,
				success : function(data) {
					if (data.error) {
						$('#crearPDF').attr('disabled', true);
						construirDialogo("#divMensaje", "Mensaje de Sistema",
								data.msg);
					} else {
						generarPDFRiesgosTrabajo();
					}
				},
				error : function(data) {
					construirDialogo("#divMensaje", "Mensaje de Sistema",
							"A ocurrido un error inesparado");
				}
	});
}

function generarPDFRiesgosTrabajo() {
	var url = busquedaRTTCtrl.context + '/wizard/riesgosTrabajo/generarPDF';
	window.open(url, '_blank');
	$('#crearPDF').attr('disabled', true);
}

function generarExcelRiesgosTrabajo() {
	var url = busquedaRTTCtrl.context + '/wizard/riesgosTrabajo/generarExcel';
	window.open(url, '_blank');
}

function cerrar() {
	parent.WizardRttCtrl.cerrar();
}

function construirDialogo(divId, titulo, mensaje) {
	$("#textoMensaje").html(mensaje);
	$("#textoMensaje").removeAttr("style");
	$("#textoMensaje").attr("style", "color: red;");
	height = 210;
	width = 430;

	var objDialogo = $(divId).dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : height,
		width : width,
		title : titulo,
		buttons : {
			"Aceptar" : function() {
				$(this).dialog("close");
			}
		}
	});
	objDialogo.dialog('open');
}