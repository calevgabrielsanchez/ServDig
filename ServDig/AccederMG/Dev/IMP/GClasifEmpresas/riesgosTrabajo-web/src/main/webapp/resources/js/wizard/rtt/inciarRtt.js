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

		busquedaRTTCtrl.showHideVisor((busquedaRTTCtrl.tipoBusqueda-1));
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
		nombre: "Debe ingresar al menos 3 caracteres para la b\u00FAsqueda.",
		nrp: "N.R.P. debe ser de 10 caracteres.",
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
			if (dato.length != 10) {
				busquedaRTTCtrl.mostrarMensajeError(busquedaRTTCtrl.errores['nrp']);
				return false;
			}
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
	},{
		etiqueta: 'R.F.C. Total',
		longitud: "13",
		url: '/historialRiesgoTrabajo/consultaRfc/',
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
	ejecutarConsulta: async function (valorCampo) {
		var busqueda = busquedaRTTCtrl.datosBusqueda[busquedaRTTCtrl.tipoBusqueda - 1],
			valorBusqueda = valorCampo != null ? valorCampo : $("#" + busqueda.campo).val(),
			url = busquedaRTTCtrl.context + busqueda.url + busquedaRTTCtrl.getSinDato(valorBusqueda, busqueda.sinDato);
		if (busquedaRTTCtrl.tipoBusqueda == 1) {
			url += "/" + busquedaRTTCtrl.periodoBusqueda;
		} else if (busquedaRTTCtrl.tipoBusqueda == 2) {
			url += "/" + $('input[type=radio][name=busquedaNRS]:checked').val();
		}

		if (await connection())
			busquedaRTTCtrl.consultaAjaxHistorialRTT(url, busqueda.error, valorBusqueda);

	},
	consultaAjaxHistorialRTT: function(url, msjError, valorRfc) {
		
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

				if (busquedaRTTCtrl.tipoRespaldo == 4) {
					 var url = '/historialRiesgoTrabajo/generaXlsRfc/';
					 var urlSend = busquedaRTTCtrl.context + url + valorRfc;

						$.ajax({
							url : urlSend,
							data : {
								delegacion: combosComunesCtrl.delegacion,
								subdelegacion: combosComunesCtrl.subdelegacion,
								periodo: busquedaRTTCtrl.periodoBusqueda
							},
							cache: false,
							type: 'POST'
						});
				}
			},
			error : function(data) {
				$('#error').html(msjError);
				$('div#mensajes > div').hide();
				$.unblockUI();
			}
		});
	},
	generarExcelRiesgosTrabajoHistorial: async function() {
		if(await connection())
			busquedaRTTCtrl.abrirModal(busquedaRTTCtrl.context + '/historialRiesgoTrabajo/generarExcel');
	},
	descargaExcelRfc: async function(opt) {
		if(await connection())
			busquedaRTTCtrl.abrirModal(busquedaRTTCtrl.context + '/historialRiesgoTrabajo/descargaExcelRfc/'+$("#obtainRfc").val()+'/'+opt);
	},
	abrirModal: function(url) {
		window.open( url, '_blank');
	},
	abrirVisor: async function(){
		if(await connection())
			busquedaRTTCtrl.abrirModal("/gestionSolicitud-visor-web/portal");
	},
	getSinDato: function(dato, sinDato) {
		if(busquedaRTTCtrl.datoInvalido(dato)){
			return sinDato;
		}
		return dato;
	},
	showHideVisor: function(valor) {
		if(busquedaRTTCtrl.tipoBusqueda==4){
			$("#abrirVisor").hide();
		}else{
			$("#abrirVisor").show();
		}
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
						var titulo = "Aviso";
						armarDlgModal(titulo, data.msg, 400, 220);
					} else {
						generarPDFRiesgosTrabajo();
					}
				},
				error : function(data) {
					var titulo = "Advertencia";
					var mensaje = "A ocurrido un error inesperado.";
					armarDlgModal(titulo, mensaje, 400, 200);
				}
	});
}

function validarSolicitudRfc() {
	var url = busquedaRTTCtrl.context + '/wizard/riesgosTrabajo/validarSolicitudRfc';

	$.ajax({
		url : url,
		data : null,
		cache: false,
		success : function(data) {
			if (data.error) {
				$('#crearPDF').attr('disabled', true);
				var titulo = "Aviso";
				armarDlgModal(titulo, data.msg, 400, 220);
			} else {
				generarPDFRttxRfc(+$("#obtainRfc").val());
			}
		},
		error : function(data) {
			var titulo = "Advertencia";
			var mensaje = "A ocurrido un error inesperado.";
			armarDlgModal(titulo, mensaje, 400, 200);
		}
	});
};

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

function generarPDFRttxRfc( ) {
	var url = busquedaRTTCtrl.context + '/wizard/riesgosTrabajo/descargaPdfRfc/'+$("#obtainRfc").val()+'/0';
	window.open(url, '_blank');
	$('#crearPDFRfc').attr('disabled', true);
}

function descargaExcelRttRfc(opt) {
	busquedaRTTCtrl.abrirModal(busquedaRTTCtrl.context + '/wizard/riesgosTrabajo/descargaExcelRttRfc/'+$("#obtainRfc").val()+'/'+opt);
}

function armarDlgModal(titulo, mensaje, dlgWidth, dlgHeight) {
	var newdiv = document.createElement('div');
	newdiv.setAttribute('id', 'divMsjDlgModal');
	newdiv.innerHTML = mensaje;
	var divv = document.getElementsByTagName('div')[0];
	divv.appendChild(newdiv);

	var objDialogo = $("#divMsjDlgModal").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		cache : false,
		height : dlgHeight,
		width : dlgWidth,
		title : titulo,
		buttons : {
			"Aceptar" : function() {
				//parent.WizardRttCtrl.cerrar();
				$(this).dialog("close");
			}
		}
	});
	objDialogo.dialog('open');
}

