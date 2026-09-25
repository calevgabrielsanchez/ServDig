var busquedaRTTCtrl = {
	tipoBusqueda: 1,
	tipoRespaldo: 1,
	periodoBusqueda: 0,
	anioActual: 0,
	context: '/${mvn.web.app.root}',
	connVent: '/historialRiesgoTrabajo',
	connPort: '/wizard/riesgosTrabajo',
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
        nss: "NSS debe ser de 11 caracteres.",
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
	}, {
        etiqueta: 'NSS',
        longitud: "11",
        url: '/historialRiesgoTrabajo/consultaNSS/',
        sinDato: 'sinRFC',
        showNom: 'hide',
        campo: 'parametroBusqueda',
        error: 'Ocurri\u00F3 un error inesperado al consultar por NSS',
        valida: function (dato) {
            if (dato.length != 11) {
                busquedaRTTCtrl.mostrarMensajeError(busquedaRTTCtrl.errores['nss']);
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
		if (busquedaRTTCtrl.tipoBusqueda == 1 || busquedaRTTCtrl.tipoBusqueda == 5) {
			url += "/" + busquedaRTTCtrl.periodoBusqueda;
		} else if (busquedaRTTCtrl.tipoBusqueda == 2) {
			url += "/" + $('input[type=radio][name=busquedaNRS]:checked').val();
		}

		if (await conn(busquedaRTTCtrl.connVent))
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
		if(await conn(busquedaRTTCtrl.connVent))
			busquedaRTTCtrl.abrirModal(busquedaRTTCtrl.context + '/historialRiesgoTrabajo/generarExcel');
	},
	descargaExcelRfc: async function(opt) {
		if(await conn(busquedaRTTCtrl.connVent))
			busquedaRTTCtrl.abrirModal(busquedaRTTCtrl.context + '/historialRiesgoTrabajo/descargaExcelRfc/'+$("#obtainRfc").val()+'/'+opt);
	},
	abrirModal: function(url) {
		window.open( url, '_blank');
	},
	abrirVisor: async function(){
		if(await conn(busquedaRTTCtrl.connVent))
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

async function validarSolicitud() {
	var url = busquedaRTTCtrl.context + '/wizard/riesgosTrabajo/validarSolicitud';

	if(await conn(busquedaRTTCtrl.connPort)){
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
}

async function validarSolicitudRfc() {
	var url = busquedaRTTCtrl.context + '/wizard/riesgosTrabajo/validarSolicitudRfc';

	if(await conn(busquedaRTTCtrl.connPort)){
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
	}
};

function generarPDFRiesgosTrabajo() {
	var url = busquedaRTTCtrl.context + '/wizard/riesgosTrabajo/generarPDF';
	window.open(url, '_blank');
	$('#crearPDF').attr('disabled', true);
}

async function generarExcelRiesgosTrabajo() {
	if(await conn(busquedaRTTCtrl.connPort)){
		var url = busquedaRTTCtrl.context + '/wizard/riesgosTrabajo/generarExcel';
		window.open(url, '_blank');
	}
}

function cerrar() {
	parent.WizardRttCtrl.cerrar();
}

function generarPDFRttxRfc() {
	var url = busquedaRTTCtrl.context + '/wizard/riesgosTrabajo/descargaPdfRfc/'+$("#obtainRfc").val()+'/0';
	window.open(url, '_blank');
	$('#crearPDFRfc').attr('disabled', true);
}

async function descargaExcelRttRfc(opt) {
	if(await conn(busquedaRTTCtrl.connPort))
		busquedaRTTCtrl.abrirModal(busquedaRTTCtrl.context + '/wizard/riesgosTrabajo/descargaExcelRttRfc/'+$("#obtainRfc").val()+'/'+opt);
}

async function conn(origen){
	var url = busquedaRTTCtrl.context + origen + '/connection';
	var respuesta = false;
	var opciones = {
		titulo: 'Sesi\u00F3n',
		mensaje:"Intermitencia en la comunicaci\u00F3n con la Base de Datos. Favor de ingresar nuevamente."
	};

	const connectResponse = await fetch(url).then(function (response) {
		if (response.ok) {
			return respuesta = response.ok;
		} else {
			console.log("No se estableció la conexión con Base de datos:" + error.message);
			return respuesta;
		}
	}).catch(function (error) {
		console.log("No se estableció la conexión con Base de datos:" + error.message);
		return respuesta;
	});

	if (!connectResponse)
		dialogosCtrl.abrirDialogo(opciones);

	return respuesta;
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

