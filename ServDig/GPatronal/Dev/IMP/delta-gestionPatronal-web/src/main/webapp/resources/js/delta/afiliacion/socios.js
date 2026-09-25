var dtSocio;
var dtSocioForSession;
var dtSocioFisicoDatosContactoDetalle;
var sIdNameFormPaginarSocio = "#SocioFormPaginar";
var sIdDialogErrorSinSeleccionSocio = "#dgErrorSinSeleccionSocio";
var sIdDialogEliminarSocio = "#dgEliminarSocio";
var sIdDialogEliminarSocioForSession = "#dgEliminarSocioTramite";
var sIdDialogModificarSocio = "#dgModificarSocio";
var sIdDialogNuevoSocio = "#divGralAgregarSocio";
var sIdDialogDeshacerEliminarSocioForSession = "#dgDeshacerEliminarSocioTramite";
var sIdDialogErrorDuplicadoSocio = "#dgErrorDuplicadoSocio";
var sIdDialogModificaSocioFisico = "#idDialogModificaSocioFisico";
var sIdDialogModificaSocioMoral = "#idDialogModificaSocioMoral";
var sIdDialogModificaSocioFideicomiso = "#idDialogModificaSocioFideicomiso";
var sIdDialogErrorSinSeleccionPersonaParaSocio = "#dgErrorSinSeleccionPersonaParaSocio";

var oDialogDetalleEnTramiteSocioFisico;
var oDialogDetalleEnTramiteSocioMoral;
var oDialogDetalleEnTramiteSocioFideicomiso;

var oDialogDetalleEnTramiteSocioFisicoEliminar;
var oDialogDetalleEnTramiteSocioMoralEliminar;
var oDialogDetalleEnTramiteSocioFideicomisoEliminar;

var sIdDialogDetalleEnTramiteSocioFisico = "#divDetalleEnTramiteSocioFisico";
var sIdDialogDetalleEnTramiteSocioMoral = "#divDetalleEnTramiteSocioMoral";
var sIdDialogDetalleEnTramiteSocioFideicomiso = "#divDetalleEnTramiteSocioFideicomiso";

var dtSocioFisicoDatosContactoDetalle;
var dtSocioMoralDatosContactoDetalle;
var dtSocioFideicomisoDatosContactoDetalle;

var dtSocioFisicoDatosContactoDetalleEliminar;
var dtSocioMoralDatosContactoDetalleEliminar;
var dtSocioFideicomisoDatosContactoDetalleEliminar;

var sIdDialgoAgregarDatoContactoDetalleSocio = "#dgNuevoDatoContactoDetalleSocio";
var sIdDialgoModificarDatoContactoDetalleSocio = "#dgModificarDatoContactoDetalleSocio";
var sIdDialogEliminarDatoContactoDetalleSocio = "#dgEliminarDatoContactoDetalleSocio";

var oDialogAgregarDatoContactoDetalleSocio;
var oDialogModificarDatoContactoDetalleSocio;
var oDialogEliminarDatoContactoDetalleSocio;

var arrayDatosSocio = new Array();
var oDialogEliminarSocio;
var oDialogNuevoSocio;
var oDialogDeshacerEliminarSocio;
var oDialogSocioConsultar;
var oDialogErrorDuplicadoSocio;
var oDialogModificaSocioFisico;
var oDialogModificaSocioMoral;
var oDialogModificaSocioFideicomiso;
var mcSocioModFisico;
var mcSocioModMoral;
var mcSocioModFideicomiso;

// datos de socio para ser enviados al proceso del grid de datos de contacto
var socioIdSocioForMediosContactoDetalleTramite;
var socioIdPersonaForMediosContactoDetalleTramite;
var socioEsDomicilioNacionalForMediosContactoDetalleTramite;
var socioEsNacionalForMediosContactoDetalleTramite;
var socioTipoSocioIdTipoPersonaForMediosContactoDetalleTramite;
var socioNombresForMediosContactoDetalleTramite;
var socioPrimerApellidoForMediosContactoDetalleTramite;
var socioSegundoApellidoForMediosContactoDetalleTramite;
var socioNombreRazonSocialForMediosContactoDetalleTramite;

var socioTipoSocioIdTipoPersonaForMediosContactoGeneral;

var accionSocioSolicitada;

$(function() {
	construirGridSocios();
	construirGridTramiteSocios();
	construirDialogosEliminarSocios();
	construirDialogosModificarSocio();
	construirDialogosDeshacerEliminarSocio();
	inicializaEstilosSocios();
	inicializaEstilosSociosDatoContactoDetalle();
	evaluarBotonesSocio();
	ocultarDialogosModificarSocios();
	configurarDatePickerModificarFechaExpedicionContratoSocioFideicomiso();
	construirDialogoDetalleEnTramiteSocios();
	construirDialogosAgregarDatoContactoDetalleSocio();
	construirDialogosModificarDatoContactoDetalleSocio();
	construirGridTramiteSociosDatosContactoDetalle();
});

MedioContacto.prototype.extendValidation = function() {
	var mc = this;

	var _selectTipoFn = function() {
		return mc.jqSelectTipo;
	};
	var fnValidarDatos = mc.validarDatos;
	var alterFnValidarDatos = function(arg1, arg2) {
		this.validarDatos = fnValidarDatos;
		var _option = $([ _selectTipoFn(), ' > ', 'option:selected' ].join(''));
		if (/correo e|facebook|twitter/i.test(_option.text())) {
			var _tmptxt = $(mc.jqTxtFldDesc).val().replace(/^\s+|\s+$/g, '');
			$(mc.jqTxtFldDesc).val(_tmptxt);
			arg2 = _tmptxt
		}
		var retval = this.validarDatos(arg1, arg2);
		this.validarDatos = alterFnValidarDatos;
		return retval;
	};

	$.extend(mc, {
		validarDatos : alterFnValidarDatos
	});
}

var columnasSocio = [ {
	"mDataProp" : "idPersona",
	"bVisible" : false
}, {
	"mDataProp" : "idSocio",
	"bVisible" : false
}, {
	"sTitle" : "RFC",
	"mDataProp" : "rfc"
}, {
	"sTitle" : "CURP",
	"mDataProp" : "curp"
}, {
	"mDataProp" : "tipoSocio.idTipoPersona",
	"bVisible" : false
}, {
	"sTitle" : "Tipo de Socio",
	"mDataProp" : "tipoSocio.descripcion"
}, {
	"sTitle" : "Nombre / Raz\u00F3n Social",
	"mDataProp" : fnRenderNombreRazonSocial
}, {
	"sTitle" : "Nacionalidad",
	"mDataProp" : "esNacional",
	"fnRender" : function(oObj) {
		return parseIndicadorNacionalidad(oObj.aData.esNacional);
	}
}, {
	"sTitle" : "Residencia",
	"mDataProp" : "esDomicilioNacional",
	"fnRender" : function(oObj) {
		return parseIndicadorResidencia(oObj.aData.esDomicilioNacional);
	}

}, {
	"sTitle" : "",
	"fnRender" : function(oObj) {
		index = oObj.aData.idPersona;
		arrayDatosSocio[index] = oObj.aData;
		return construyeLigaSocio(index);
	}
}, {
	"mDataProp" : "primerApellido",
	"bVisible" : false
}, {
	"mDataProp" : "segundoApellido",
	"bVisible" : false
}, {
	"mDataProp" : "nombres",
	"bVisible" : false
},

];

var columnasTramiteSocios = [ {
	"mDataProp" : "idPersona",
	"bVisible" : false
}, {
	"mDataProp" : "idSocio",
	"bVisible" : false
}, {
	"sTitle" : "RFC",
	"mDataProp" : "rfc",
	sWidth : "100px"
}, {
	"sTitle" : "CURP",
	"mDataProp" : "curp",
	sWidth : "150px"
}, {
	"mDataProp" : "tipoSocio.idTipoPersona",
	"bVisible" : false
}, {
	"sTitle" : "Tipo de Socio",
	"mDataProp" : "tipoSocio.descripcion",
	sWidth : "70px"
}, {
	"sTitle" : "Nombre / Raz\u00F3n Social",
	"mDataProp" : fnRenderNombreRazonSocial,
	sWidth : "300px"
}, {
	"sTitle" : "Nacionalidad",
	"mDataProp" : "esNacional",
	"fnRender" : function(oObj) {
		return parseIndicadorNacionalidad(oObj.aData.esNacional);
	}
}, {
	"sTitle" : "Residencia",
	"mDataProp" : "esDomicilioNacional",
	"fnRender" : function(oObj) {
		return parseIndicadorResidencia(oObj.aData.esDomicilioNacional);
	}

}, {
	"sTitle" : "Acci&oacute;n a Realizar",
	"fnRender" : function(oObj) {
		index = oObj.aData.accion;
		return showAfectacion(index);
	}

}, {
	"sTitle" : "",
	"fnRender" : function(oObj) {
		index = oObj.aData;
		return deshacerSocio(index);
	}

}, {
	"sTitle" : "",
	"fnRender" : function(oObj) {
		valor = oObj.aData;
		return mostrarDetalleEnTramiteSocios(valor);
	}

} ];

var columnasSocioFisicoDatosContactoDetalle = [ {
	"mDataProp" : "idVista",
	"bVisible" : false
}, {
	"mDataProp" : "errorFormGeneral",
	"bVisible" : false
}, {
	"sTitle" : "Medio de Contacto",
	"mDataProp" : "tipoMedioContacto.descripcion"
}, {
	"sTitle" : "Descripci&oacute;n",
	"mDataProp" : "desFormaContacto"
} ];

var columnasSocioMoralDatosContactoDetalle = [ {
	"mDataProp" : "idVista",
	"bVisible" : false
}, {
	"mDataProp" : "errorFormGeneral",
	"bVisible" : false
}, {
	"sTitle" : "Medio de Contacto",
	"mDataProp" : "tipoMedioContacto.descripcion"
}, {
	"sTitle" : "Descripci&oacute;n",
	"mDataProp" : "desFormaContacto"
} ];

var columnasSocioFideicomisoDatosContactoDetalle = [ {
	"mDataProp" : "idVista",
	"bVisible" : false
}, {
	"mDataProp" : "errorFormGeneral",
	"bVisible" : false
}, {
	"sTitle" : "Medio de Contacto",
	"mDataProp" : "tipoMedioContacto.descripcion"
}, {
	"sTitle" : "Descripci&oacute;n",
	"mDataProp" : "desFormaContacto"
} ];

function fnRenderNombreRazonSocial(oObj) {
	var nomRznSocial; 
	if (oObj.nombres != null && oObj.nombres != undefined) {
		nomRznSocial = oObj.nombres + " " + oObj.primerApellido + " "
		+ oObj.segundoApellido;
		return nomRznSocial.toUpperCase();
	} else {
		nomRznSocial = oObj.nombreRazonSocial;
		return nomRznSocial.toUpperCase();
	}
}

function construirDialogosDeshacerEliminarSocio() {
	oDialogDeshacerEliminarSocio = $(sIdDialogDeshacerEliminarSocioForSession)
			.dialog({
				autoOpen : false,
				resizable : false,
				height : 200,
				modal : true,
				buttons : {
					"Eliminar" : deshacerAccionSobreSocio,
					'Cancelar' : cancelarTramiteSocios
				}
			});

	oDialogErrorDuplicadoSocio = $(sIdDialogErrorDuplicadoSocio).dialog({
		autoOpen : false,
		resizable : false,
		height : 200,
		modal : true,
		buttons : {
			"Aceptar" : function() {
				$(this).dialog("close");
			}
		}
	});
}

function construirDialogosModificarSocio() {

	oDialogModificarSocio = $(sIdDialogModificarSocio).dialog({
		autoOpen : false,
		resizable : false,
		width : 800,
		height : 400,
		modal : true,
		buttons : {
			"Guardar" : function() {
				modificarTramiteSocio();
			},
			'Cancelar' : function() {
				$(this).dialog("close");
			}
		}
	});
}

function construirDialogosAgregarDatoContactoDetalleSocio() {

	/* Configuracion del dialogo de agregar nuevo elemento */
	oDialogAgregarDatoContactoDetalleSocio = $(
			sIdDialgoAgregarDatoContactoDetalleSocio).dialog({
		autoOpen : false,
		resizable : false,
		height : 370,
		width : 600,
		modal : true,
		buttons : {
			"Guardar" : function() {
				agregarDatoContactoDetalleSocio();
			},
			'Cancelar' : function() {
				$(this).dialog("close");
			}
		}

	});
}

function construirDialogosModificarDatoContactoDetalleSocio() {

	/* Configuracion del dialogo de modificar nuevo elemento */
	oDialogModificarDatoContactoDetalleSocio = $(
			sIdDialgoModificarDatoContactoDetalleSocio).dialog({
		autoOpen : false,
		resizable : false,
		height : 370,
		width : 600,
		modal : true,
		buttons : {
			"Guardar" : function() {
				modificarDatoContactoDetalleSocio();
			},
			'Cancelar' : function() {
				$(this).dialog("close");
			}
		}

	});
}

function construirGridSocios() {

	if (dtSocio != undefined) {
		dtSocio.fnDestroy();
	}
	dtSocio = $('#tbSocio').dataTable({
		"bJQueryUI" : false,
		"bPaginate" : true,
		"bLengthChange" : false,
		"iDisplayLength" : 5,
		"sPaginationType" : "full_numbers",
		"bFilter" : false,
		"bSort" : false,
		"bInfo" : false,
		"bAutoWidth" : false,
		"bServerSide" : false,
		"aoColumns" : columnasSocio,
		"bProcessing" : true,
		"sAjaxSource" : '/delta-gestionPatronal-web/socios/fb/paginarSocios',
		"fnServerData" : enviarTramite
	});

}

function construirDialogoDetalleEnTramiteSocios() {
	oDialogDetalleEnTramiteSocioFisico = $(sIdDialogDetalleEnTramiteSocioFisico)
			.dialog({
				autoOpen : false,
				resizable : false,
				modal : true,
				height : 650,
				width : 800,
				buttons : {
					"Aceptar" : function() {
						validaCambiosDetalleEnTramiteSocio();
					},
					"Cancelar" : function() {
						cancelarEdicionMediosContactoSocioEnDetalle();
						$(this).dialog("close");
					}
				}
			});

	oDialogDetalleEnTramiteSocioFisicoEliminar = $(
			"#divDetalleEnTramiteSocioFisicoEliminar").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : 550,
		width : 750,
		buttons : {
			"Cerrar" : function() {
				$(this).dialog("close");
			}
		}
	});

	oDialogDetalleEnTramiteSocioMoral = $(sIdDialogDetalleEnTramiteSocioMoral)
			.dialog({
				autoOpen : false,
				resizable : false,
				modal : true,
				height : 550,
				width : 750,
				buttons : {
					"Aceptar" : function() {
						validaCambiosDetalleEnTramiteSocioMoral();
					},
					"Cancelar" : function() {
						cancelarEdicionMediosContactoSocioEnDetalle();
						$(this).dialog("close");
					}
				}
			});

	oDialogDetalleEnTramiteSocioMoralEliminar = $(
			"#divDetalleEnTramiteSocioMoralEliminar").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : 600,
		width : 850,
		buttons : {
			"Cerrar" : function() {
				$(this).dialog("close");
			}
		}
	});

	oDialogDetalleEnTramiteSocioFideicomiso = $(
			sIdDialogDetalleEnTramiteSocioFideicomiso).dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : 550,
		width : 750,
		buttons : {
			"Aceptar" : function() {
				validaCambiosDetalleEnTramiteSocioFideicomiso();
			},
			"Cancelar" : function() {
				cancelarEdicionMediosContactoSocioEnDetalle();
				$(this).dialog("close");
			}
		}
	});

	oDialogDetalleEnTramiteSocioFideicomisoEliminar = $(
			"#divDetalleEnTramiteSocioFideicomisoEliminar").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : 550,
		width : 750,
		buttons : {
			"Cerrar" : function() {
				$(this).dialog("close");
			}
		}
	});
}

function inicializaEstilosSocios() {
	$("#tbSocio tbody").hover(function() {
		$(this).css('cursor', 'pointer');
	});

	/* Add a click handler to the rows - this could be used as a callback */
	$("#tbSocio tbody").click(function(event) {
		var seleccionar = !$(event.target.parentNode).hasClass('row_selected');

		$(dtSocio.fnSettings().aoData).each(function() {
			$(this.nTr).removeClass('row_selected');
		});
		if (seleccionar) {
			$(event.target.parentNode).addClass('row_selected');
		}
	});
}

function inicializaEstilosSociosDatoContactoDetalle() {
	$("#tbSocioFisicoDatosContactoDetalle tbody").hover(function() {
		$(this).css('cursor', 'pointer');
	});

	$("#tbSocioMoralDatosContactoDetalle tbody").hover(function() {
		$(this).css('cursor', 'pointer');
	});

	$("#tbSocioFideicomisoDatosContactoDetalle tbody").hover(function() {
		$(this).css('cursor', 'pointer');
	});

	/* Add a click handler to the rows - this could be used as a callback */
	$("#tbSocioFisicoDatosContactoDetalle tbody").click(
			function(event) {
				var seleccionar = !$(event.target.parentNode).hasClass(
						'row_selected');

				$(dtSocioFisicoDatosContactoDetalle.fnSettings().aoData).each(
						function() {
							$(this.nTr).removeClass('row_selected');
						});
				if (seleccionar) {
					$(event.target.parentNode).addClass('row_selected');
				}
			});

	/* Add a click handler to the rows - this could be used as a callback */
	$("#tbSocioMoralDatosContactoDetalle tbody").click(
			function(event) {
				var seleccionar = !$(event.target.parentNode).hasClass(
						'row_selected');

				$(dtSocioMoralDatosContactoDetalle.fnSettings().aoData).each(
						function() {
							$(this.nTr).removeClass('row_selected');
						});
				if (seleccionar) {
					$(event.target.parentNode).addClass('row_selected');
				}
			});

	/* Add a click handler to the rows - this could be used as a callback */
	$("#tbSocioFideicomisoDatosContactoDetalle tbody").click(
			function(event) {
				var seleccionar = !$(event.target.parentNode).hasClass(
						'row_selected');

				$(dtSocioFideicomisoDatosContactoDetalle.fnSettings().aoData)
						.each(function() {
							$(this.nTr).removeClass('row_selected');
						});
				if (seleccionar) {
					$(event.target.parentNode).addClass('row_selected');
				}
			});

}

function enviarTramite(sSource, aoData, fnCallback) {
	var wrapper = new Object();
	wrapper.aoData = aoData;
	var oForm = $(sIdNameFormPaginarSocio).serializeObject(true);
	wrapper.oForm = oForm;

	$.postJSON(sSource, wrapper, function(data) {
		fnCallback(data);
	});

}

function parseIndicador(o) {
	if (o == '1' || o == 1) {
		return 'si';

	} else {
		return 'no';
	}
}

function construyeLigaSocio(object) {

	return "<a style='cursor:pointer;' onclick='showDetalleSocio(" + object
			+ ")'>Mostrar Detalle</a>";
}

function construirGridTramiteSocios() {
	/*
	 * Configuracion del data table de Representante Legal para el objeto de
	 * sesion
	 */
	dtSocioForSession = $('#tbSocioForSession')
			.dataTable(
					{
						bJQueryUI : false,
						bFilter : false,
						bInfo : false,
						bSort : false,
						"bPaginate" : true,
						"bAutoWidth" : false,
						"bServerSide" : true,
						"sPaginationType" : "full_numbers",
						"aoColumns" : columnasTramiteSocios,
						"bProcessing" : true,
						"sAjaxSource" : '/delta-gestionPatronal-web/socios/fb/paginarForSession',
						"fnServerData" : enviarMovimientoSocios
					});

}

function construirGridTramiteSociosDatosContactoDetalle() {
	dtSocioFisicoDatosContactoDetalle = $(
			'#divDetalleEnTramiteSocioFisico #tbSocioFisicoDatosContactoDetalle')
			.dataTable(
					{
						bJQueryUI : false,
						bFilter : false,
						bInfo : false,
						bSort : false,
						iDisplayLength : 4,
						bPaginate : true,
						sPaginationType : "full_numbers",
						bAutoWidth : false,
						bServerSide : true,
						aoColumns : columnasSocioFisicoDatosContactoDetalle,
						bProcessing : true,
						sAjaxSource : '/delta-gestionPatronal-web/socios/fb/obtenerDetalleEnTramiteSociosDatosContacto',
						fnServerData : enviarDatosDeSocioParaGridMediosContacto
					});

	dtSocioFisicoDatosContactoDetalleEliminar = $(
			'#divDetalleEnTramiteSocioFisicoEliminar #tbSocioFisicoDatosContactoDetalleEliminar')
			.dataTable(
					{
						bJQueryUI : false,
						bFilter : false,
						bInfo : false,
						bSort : false,
						iDisplayLength : 4,
						bPaginate : true,
						sPaginationType : "full_numbers",
						bAutoWidth : false,
						bServerSide : true,
						aoColumns : columnasSocioFisicoDatosContactoDetalle,
						bProcessing : true,
						sAjaxSource : '/delta-gestionPatronal-web/socios/fb/obtenerDetalleEnTramiteSociosDatosContacto',
						fnServerData : enviarDatosDeSocioParaGridMediosContacto
					});

	dtSocioMoralDatosContactoDetalle = $(
			'#divDetalleEnTramiteSocioMoral #tbSocioMoralDatosContactoDetalle')
			.dataTable(
					{
						bJQueryUI : false,
						bFilter : false,
						bInfo : false,
						bSort : false,
						iDisplayLength : 4,
						bPaginate : true,
						sPaginationType : "full_numbers",
						bAutoWidth : false,
						bServerSide : true,
						aoColumns : columnasSocioMoralDatosContactoDetalle,
						bProcessing : true,
						sAjaxSource : '/delta-gestionPatronal-web/socios/fb/obtenerDetalleEnTramiteSociosDatosContacto',
						fnServerData : enviarDatosDeSocioParaGridMediosContacto
					});

	dtSocioMoralDatosContactoDetalleEliminar = $(
			'#tbSocioMoralDatosContactoDetalleEliminar')
			.dataTable(
					{
						bJQueryUI : false,
						bFilter : false,
						bInfo : false,
						bSort : false,
						iDisplayLength : 4,
						sEcho : 4,
						bPaginate : true,
						sPaginationType : "full_numbers",
						bAutoWidth : false,
						bServerSide : true,
						aoColumns : columnasSocioMoralDatosContactoDetalle,
						bProcessing : true,
						sAjaxSource : '/delta-gestionPatronal-web/socios/fb/obtenerDetalleEnTramiteSociosDatosContacto',
						fnServerData : enviarDatosDeSocioParaGridMediosContacto
					});

	dtSocioFideicomisoDatosContactoDetalle = $(
			'#divDetalleEnTramiteSocioFideicomiso #tbSocioFideicomisoDatosContactoDetalle')
			.dataTable(
					{
						bJQueryUI : false,
						bFilter : false,
						bInfo : false,
						bSort : false,
						iDisplayLength : 4,
						bPaginate : true,
						sPaginationType : "full_numbers",
						bAutoWidth : false,
						bServerSide : true,
						aoColumns : columnasSocioFideicomisoDatosContactoDetalle,
						bProcessing : true,
						sAjaxSource : '/delta-gestionPatronal-web/socios/fb/obtenerDetalleEnTramiteSociosDatosContacto',
						fnServerData : enviarDatosDeSocioParaGridMediosContacto
					});

	dtSocioFideicomisoDatosContactoDetalleEliminar = $(
			'#divDetalleEnTramiteSocioFideicomisoEliminar #tbSocioFideicomisoDatosContactoDetalleEliminar')
			.dataTable(
					{
						bJQueryUI : false,
						bFilter : false,
						bInfo : false,
						bSort : false,
						iDisplayLength : 4,
						bPaginate : true,
						bAutoWidth : false,
						sPaginationType : "full_numbers",
						bServerSide : true,
						aoColumns : columnasSocioFideicomisoDatosContactoDetalle,
						bProcessing : true,
						sAjaxSource : '/delta-gestionPatronal-web/socios/fb/obtenerDetalleEnTramiteSociosDatosContacto',
						fnServerData : enviarDatosDeSocioParaGridMediosContacto
					});
}

function enviarDatosDeSocioParaGridMediosContacto(sSource, aoData, fnCallback) {

	aoData.push({
		"name" : "sSearch",
		"value" : ''
	});

	var wrapperMovimiento = new Object();
	var oForm = new Object();
	var socio = new Object();
	var tipoSocio = new Object();

	wrapperMovimiento.aoData = aoData;
	socio.tipoSocio = tipoSocio;
	wrapperMovimiento.oForm = oForm;
	wrapperMovimiento.socio = socio;

	// asignamos valores
	wrapperMovimiento.socio.idSocio = socioIdSocioForMediosContactoDetalleTramite;
	wrapperMovimiento.socio.idPersona = socioIdPersonaForMediosContactoDetalleTramite;
	wrapperMovimiento.socio.esDomicilioNacional = socioEsDomicilioNacionalForMediosContactoDetalleTramite;
	wrapperMovimiento.socio.esNacional = socioEsNacionalForMediosContactoDetalleTramite;
	wrapperMovimiento.socio.tipoSocio.idTipoPersona = socioTipoSocioIdTipoPersonaForMediosContactoDetalleTramite;
	wrapperMovimiento.socio.nombres = socioNombresForMediosContactoDetalleTramite;
	wrapperMovimiento.socio.primerApellido = socioPrimerApellidoForMediosContactoDetalleTramite;
	wrapperMovimiento.socio.segundoApellido = socioSegundoApellidoForMediosContactoDetalleTramite;
	wrapperMovimiento.socio.nombreRazonSocial = socioNombreRazonSocialForMediosContactoDetalleTramite;

	$.postJSON(sSource, wrapperMovimiento, function(data) {
		fnCallback(data);
	});

}

function enviarMovimientoSocios(sSource, aoData, fnCallback) {
	aoData.push({
		"name" : "sSearch",
		"value" : ''
	});

	var wrapperMovimiento = new Object();
	wrapperMovimiento.aoData = aoData;
	var oForm = $(sIdNameFormPaginarSocio).serializeObject(true);
	// ver las otras dos pinchas clasificaciones de tipo de socio: fideicomiso y
	// extranjerillo, ah! y klas pinches relaciones cn persona y sujetoobligao
	if (tipoPersonaFiscal == "FISICA") {
		// oForm.tipoPersonaRepresentada.idTipoPersona = 1;
		oForm.idPersona = $("#fisica\\.idPersona").val();
	} else {
		// oForm.tipoPersonaRepresentada.idTipoPersona = 2;
		oForm.idPersona = $("#moral\\.idPersona").val();
	}
	wrapperMovimiento.oForm = oForm;
	$.postJSON(sSource + "?idSolicitud=" + idSolicitud, wrapperMovimiento,
			function(data) {
				fnCallback(data);
			});
}

function construirDialogosEliminarSocios() {

	oDialogEliminarSocio = $(sIdDialogEliminarSocio).dialog({
		autoOpen : false,
		resizable : false,
		height : 200,
		modal : true,
		buttons : {
			"Eliminar" : eliminarTramiteSocios,
			'Cancelar' : cancelarSocios
		}
	});

	oDialogEliminarSocioForSession = $(sIdDialogEliminarSocioForSession)
			.dialog({
				autoOpen : false,
				resizable : false,
				height : 200,
				modal : true,
				buttons : {
					"Eliminar" : eliminarTramiteSocios,
					'Cancelar' : cancelarTramiteSocios
				}
			});

	oDialogEliminarDatoContactoDetalleSocio = $(
			sIdDialogEliminarDatoContactoDetalleSocio).dialog({
		autoOpen : false,
		resizable : false,
		height : 200,
		modal : true,
		buttons : {
			"Eliminar" : eliminarDatoContactoDetalleSocio,
			'Cancelar' : function() {
				$(this).dialog("close");
			}
		}
	});
}

function eliminarSocios(data) {

	fnHideErrores(sIdDialogEliminarSocio);
	/* Obtenemos el radio seleccionado */
	var obRowSelected = fnGetRowSelected(dtSocio);
	var idSocio = obRowSelected.idPersona;

	// var cveIdPatronSujetoObligado = $('#socioFormPaginar:hidden
	// #cveIdPatronSujetoObligado').val();

	var socioObj = new Object();

	socioObj.cveIdPatronSujetoObligado = $("#cveIdSujetoObligado").val();
	socioObj.tipoPersonaRepresentada = new Object();
	socioObj.idPersona = obRowSelected.idPersona;

	if (tipoPersonaFiscal == "FISICA") { // esto nel!!!!!!!!
		socioObj.tipoPersonaRepresentada.idTipoPersona = 1;
	} else {
		socioObj.tipoPersonaRepresentada.idTipoPersona = 2;
	}

	socioObj.personaFisica = new Object();
	socioObj.personaFisica = obRowSelected.personaFisica;

	var sSource = '/socios/fb/eliminarSocio';
	var callback;
	alert("a eliminar...., este no es de tramite");
	sendToServer(sSource, socioObj, procesarRespuestaParaSocios, false);
}

function modificarTramiteSocio() {

	var oForm;
	var sSource = '/socios/fb/modificarSocio';
	document.getElementById("responseSocioHidden").value = 3;
	var tipoSocioModificacionVar = document
			.getElementById("tipoSocioModificacion").value;
	var evaluaMediosContacto=true;
	if (tipoSocioModificacionVar == 1) {
		oForm = $("form#formModificaSocioFisico").toObject(true);
		if(oForm.esDomicilioNacional =="false" && oForm.esNacional=="false"){
			evaluaMediosContacto=false;
			oForm.domicilioFiscal=undefined;
		}else{
			oForm.mediosContacto = mcSocioModFisico.obtenerListaMediosContacto();
		}
	} else if (tipoSocioModificacionVar == 2) {
		oForm = $("form#formModificaSocioMoral").toObject(true);
		
		if(oForm.esDomicilioNacional =="false" && oForm.esNacional=="false"){
			evaluaMediosContacto=false;
			oForm.domicilioFiscal=undefined;
		}else{
			oForm.mediosContacto = mcSocioModMoral.obtenerListaMediosContacto();
		}
			
			
	} else if (tipoSocioModificacionVar == 3) {
		oForm = $("form#formModificaSocioFideicomiso").toObject(true);
		f = $("form#formModificaSocioFideicomiso #msFechaExpedicionContrato")
				.val();
		oForm.fechaExpedicionContrato = f;
		oForm.mediosContacto = mcSocioModFideicomiso
				.obtenerListaMediosContacto();
	}
	
	if(evaluaMediosContacto){
		if (!validaMediosContactoRequeridos(oForm.mediosContacto)) {
			var oDialogo;
			construirDialogoGenerico(
					"#dialogoMensajes",
					oDialogo,
					"Error",
					"Debe capturar al menos un Tel\u00E9fono fijo o m\u00F3vil y/o Correo Electr\u00F3nico v\u00E1lidos.",
					true);
			return false;
		}
	}
	oForm.tipoPersonaFiscalPatron = tipoPersonaFiscal;

	sendToServer(sSource, oForm, procesarRespuestaParaSocios);
}

function fnOpenDialogModificarSocio() {
	if (fnValidaRegistroSeleccionado(dtSocio)) {
		var obRowSelected = fnGetRowSelected(dtSocio);
		var idSocio = obRowSelected.idSocio;

		var socio = new Object();
		socio.idSocio = idSocio;
		accionSocioSolicitada = 'MODIFICAR';
		sendToServer('/socios/fb/validaMovimientoPrevio', socio,
				fnCallbackOpenDialogModificarSocio, false);

	} else {
		// Mostramos mensaje de error
		fnDialogErrorSinSeleccionSocio();
	}

	/*
	 * else if (fnValidaRegistroSeleccionado(dtSocioForSession)){
	 * //.dialog('open'); }
	 */
}

function fnCallbackOpenDialogModificarSocio(response) {

	if (!response.existeMovimientoPrevio) {
		switch (accionSocioSolicitada) {
		case 'MODIFICAR':
			var obRowSelected = fnGetRowSelected(dtSocio);
			var idSocio = obRowSelected.idSocio;
			var idPersona = obRowSelected.idPersona;
			var idPersonaMoralPatron = obRowSelected.idPersonaMoralPatron;
			var tipoSocioMod = obRowSelected.tipoSocio.idTipoPersona;
			var nacionalidadMod = obRowSelected.esNacional;
			var nacionalidadDomMod = obRowSelected.esDomicilioNacional;
			var esPersonaFisica = obRowSelected.esPersonaFisica;
			var socioExtranjero = false;
			var rfc = obRowSelected.rfc;
			var curp = obRowSelected.curp;
			var nombreRazonSocial = obRowSelected.nombreRazonSocial!=undefined ? obRowSelected.nombreRazonSocial.toUpperCase() : "";
			var primerApellido = obRowSelected.primerApellido;
			var segundoApellido = obRowSelected.segundoApellido;
			var nombres = obRowSelected.nombres;
			var tipoSociedad = obRowSelected.tipoSociedad;

			if (nacionalidadDomMod == "Extranjera") {
				socioExtranjero = true;
			}

			$("#dtModificaContactosSocioFisico").html("");
			$("#dtModificaContactosSocioMoral").html("");
			$("#dtModificaContactosSocioFideicomiso").html("");

			if (tipoSocioMod == 1) {

				$("#tipoSocioModificacion").val(tipoSocioMod);

				$("form#formModificaSocioFisico #idSocio").val(idSocio);
				$("form#formModificaSocioFisico #msFisicoIdPersona").val(
						idPersona);
				$("form#formModificaSocioFisico #tipoSocio\\.idTipoPersona")
						.val(tipoSocioMod);
				$("form#formModificaSocioFisico #idPersonaMoralPatron").val(
						idPersonaMoralPatron);

				$("form#formModificaSocioFisico #esPersonaFisica").val(
						esPersonaFisica);
				$("form#formModificaSocioFisico #msFisicoRFC").val(rfc);
				$("form#formModificaSocioFisico #msFisicoCURP").val(curp);
				$("form#formModificaSocioFisico #msFisicoPrimerAp").val(
						primerApellido);
				$("form#formModificaSocioFisico #msFisicoSegundoAp").val(
						segundoApellido);
				$("form#formModificaSocioFisico #msFisicoNombre").val(nombres);

				$(
						"form#formModificaSocioFisico #msMoralDenominacionRazonSocial")
						.val(nombreRazonSocial);

				// secc. dom. fiscal
				if (obRowSelected.domicilioFiscal != null) {
					$("form#formModificaSocioFisico #msFisicoCalle").val(
							obRowSelected.domicilioFiscal.calle);
					$("form#formModificaSocioFisico #msFiscicoNumExt").val(
							obRowSelected.domicilioFiscal.numExteriorAlf);
					$("form#formModificaSocioFisico #msFiscioNumInt").val(
							obRowSelected.domicilioFiscal.numInteriorAlf);

					if (obRowSelected.domicilioFiscal.vialidadReferenciaPrimaria != null) {
						$("form#formModificaSocioFisico #msFiscicoReferUno")
								.val(
										obRowSelected.domicilioFiscal.vialidadReferenciaPrimaria.nombre);
					}

					if (obRowSelected.domicilioFiscal.vialidadReferenciaSecundaria != null) {
						$("form#formModificaSocioFisico #msFiscicoReferDos")
								.val(
										obRowSelected.domicilioFiscal.vialidadReferenciaSecundaria.nombre);
					}

					if (obRowSelected.domicilioFiscal.vialidadReferenciaPosterior != null) {
						$("form#formModificaSocioFisico #msFiscicoReferPost")
								.val(
										obRowSelected.domicilioFiscal.vialidadReferenciaPosterior.nombre);
					}

					if (obRowSelected.domicilioFiscal.asentamiento != null) {
						$("form#formModificaSocioFisico #msFiscicoColonia")
								.val(
										obRowSelected.domicilioFiscal.asentamiento.nombre);

						if (obRowSelected.domicilioFiscal.asentamiento.localidad != null) {
							$(
									"form#formModificaSocioFisico #msFiscicoLocalidad")
									.val(
											obRowSelected.domicilioFiscal.asentamiento.localidad.nombre);

							if (obRowSelected.domicilioFiscal.asentamiento.localidad.municipio != null) {
								$(
										"form#formModificaSocioFisico #msFiscicoDeleg")
										.val(
												obRowSelected.domicilioFiscal.asentamiento.localidad.municipio.nombre);

								if (obRowSelected.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa != null) {
									$(
											"form#formModificaSocioFisico #msFiscicoEntidad")
											.val(
													obRowSelected.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre);
								}
							}
						}
					}

					$("form#formModificaSocioFisico #msFiscicoCP").val(
							obRowSelected.domicilioFiscal.codigoPostal.codigoPostal);
				}

				if (nacionalidadMod == "Extranjero") {
					$("form#formModificaSocioFisico #msFisicoNacional").val(
							false);
				} else {
					$("form#formModificaSocioFisico #msFisicoNacional").val(
							true);
				}

				if (nacionalidadDomMod == "Extranjera") {
					$("form#formModificaSocioFisico #msFisicoDomicilioNacional")
							.val(false);
				} else {
					$("form#formModificaSocioFisico #msFisicoDomicilioNacional")
							.val(true);
				}

				if (nacionalidadMod == "Extranjero"
						&& nacionalidadDomMod == "Extranjera") {
					$("form#formModificaSocioFisico #divMSFisicoDomicilio")
							.css("display", "none");
					$("form#formModificaSocioFisico #divMSCargarDatos").css(
							"display", "none");
					$("form#formModificaSocioFisico #filaMSFisicoRFC").hide();
					$("form#formModificaSocioFisico #filaMSFisicoCURP").hide();
				} else { // nacionales
					mcSocioModFisico = new MedioContacto(
							"dtModificaContactosSocioFisico", 2,
							tpPropietarioSocioPersonaFisica, idSocio,
							idSolicitud, null);
					mcSocioModFisico.init();
					mcSocioModFisico.extendValidation();
					$.extend(mc, {
						validarDatos : alterFnValidarDatos
					});
					$("form#formModificaSocioFisico #divMSFisicoDomicilio")
							.css("display", "block");
					$("form#formModificaSocioFisico #divMSCargarDatos").css(
							"display", "block");
					$("form#formModificaSocioFisico #filaMSFisicoRFC").show();
					$("form#formModificaSocioFisico #filaMSFisicoCURP").show();

				}

				fnOpenDialogModificaSocioFisico();

			} else if (tipoSocioMod == 2) {

				$("#tipoSocioModificacion").val(tipoSocioMod);

				$("form#formModificaSocioMoral #idSocio").val(idSocio);
				$("form#formModificaSocioMoral #msMoralIdPersona").val(
						idPersona);
				$("form#formModificaSocioMoral #tipoSocio\\.idTipoPersona")
						.val(tipoSocioMod);
				$("form#formModificaSocioMoral #idPersonaMoralPatron").val(
						idPersonaMoralPatron);
				$("form#formModificaSocioMoral #msTipoSociedad").val(
						tipoSociedad);

				$("form#formModificaSocioMoral #esPersonaFisica").val(
						esPersonaFisica);
				$("form#formModificaSocioMoral #msMoralRFC").val(rfc);
				$("form#formModificaSocioMoral #msMoralDenominacionRazonSocial")
						.val(nombreRazonSocial.toUpperCase());

				// secc. escritura consittutiva
				if (obRowSelected.escrituraConstitutiva != null) {
					$("form#formModificaSocioMoral #msMoralFolioMercantil")
							.val(
									obRowSelected.escrituraConstitutiva.folioMercantil);
					$("form#formModificaSocioMoral #msMoralSeccion").val(
							obRowSelected.escrituraConstitutiva.seccion);
					$("form#formModificaSocioMoral #msMoralPartida").val(
							obRowSelected.escrituraConstitutiva.partida);
					$("form#formModificaSocioMoral #msMoralVolumen").val(
							obRowSelected.escrituraConstitutiva.volumen);
					$("form#formModificaSocioMoral #msMoralFoja").val(
							obRowSelected.escrituraConstitutiva.foja);
				}

				// secc. dom. fiscal
				if (obRowSelected.domicilioFiscal != null) {
					$("form#formModificaSocioMoral #msMoralCalle").val(
							obRowSelected.domicilioFiscal.calle);
					$("form#formModificaSocioMoral #msMoralNumExt").val(
							obRowSelected.domicilioFiscal.numExteriorAlf);
					$("form#formModificaSocioMoral #msMoralNumInt").val(
							obRowSelected.domicilioFiscal.numInteriorAlf);

					if (obRowSelected.domicilioFiscal.vialidadReferenciaPrimaria != null) {
						$("form#formModificaSocioMoral #msMoralReferUno")
								.val(
										obRowSelected.domicilioFiscal.vialidadReferenciaPrimaria.nombre);
					}

					if (obRowSelected.domicilioFiscal.vialidadReferenciaSecundaria != null) {
						$("form#formModificaSocioMoral #msMoralReferDos")
								.val(
										obRowSelected.domicilioFiscal.vialidadReferenciaSecundaria.nombre);
					}

					if (obRowSelected.domicilioFiscal.vialidadReferenciaPosterior != null) {
						$("form#formModificaSocioMoral #msMoralReferPost")
								.val(
										obRowSelected.domicilioFiscal.vialidadReferenciaPosterior.nombre);
					}

					if (obRowSelected.domicilioFiscal.asentamiento != null) {
						$("form#formModificaSocioMoral #msMoralColonia")
								.val(
										obRowSelected.domicilioFiscal.asentamiento.nombre);

						if (obRowSelected.domicilioFiscal.asentamiento.localidad != null) {
							$("form#formModificaSocioMoral #msMoralLocalidad")
									.val(
											obRowSelected.domicilioFiscal.asentamiento.localidad.nombre);

							if (obRowSelected.domicilioFiscal.asentamiento.localidad.municipio != null) {
								$("form#formModificaSocioMoral #msMoralDeleg")
										.val(
												obRowSelected.domicilioFiscal.asentamiento.localidad.municipio.nombre);

								if (obRowSelected.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa != null) {
									$(
											"form#formModificaSocioMoral #msMoralEntidad")
											.val(
													obRowSelected.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre);
								}
							}
						}
					}

					$("form#formModificaSocioMoral #msMoralCP").val(
							obRowSelected.domicilioFiscal.codigoPostal.codigoPostal);
				}

				if (nacionalidadMod == "Extranjero") {
					$("form#formModificaSocioMoral #msMoralNacional")
							.val(false);
				} else {
					$("form#formModificaSocioMoral #msMoralNacional").val(true);
				}

				if (nacionalidadDomMod == "Extranjera") {
					$("form#formModificaSocioMoral #msMoralDomicilioNacional")
							.val(false);
				} else {
					$("form#formModificaSocioMoral #msMoralDomicilioNacional")
							.val(true);
				}

				if (nacionalidadMod == "Extranjero"
						&& nacionalidadDomMod == "Extranjera") {
					$("form#formModificaSocioMoral #msMoralDenominacionRazonSocial")
							.attr('disabled', false);
					$("form#formModificaSocioMoral #msMoralDenominacionRazonSocial")
					.attr('readonly', false);
					$("form#formModificaSocioMoral #filaMSRFCMoral").hide();
					$("form#formModificaSocioMoral #filaTipoSociedad").hide();
					$(
							"form#formModificaSocioMoral #divMSMoralEscrituraConstitutiva")
							.css("display", "none");
					$("form#formModificaSocioMoral #divMSMoralDomicilio").css(
							"display", "none");
					$(
							"form#formModificaSocioMoral #divModificaContactosSocioMoral")
							.css("display", "none");
				} else {
					mcSocioModMoral = new MedioContacto(
							"dtModificaContactosSocioMoral", 2,
							tpPropietarioSocioPersonaFisica, idSocio,
							idSolicitud, null);
					mcSocioModMoral.init();
					mcSocioModMoral.extendValidation();

					$(
							"form#formModificaSocioMoral #msMoralDenominacionRazonSocial")
							.attr('disabled', true);
					$("form#formModificaSocioMoral #filaMSRFCMoral").show();
					$("form#formModificaSocioMoral #filaTipoSociedad").show();
					$(
							"form#formModificaSocioMoral #divMSMoralEscrituraConstitutiva")
							.css("display", "block");
					$("form#formModificaSocioMoral #divMSMoralDomicilio").css(
							"display", "block");
					$(
							"form#formModificaSocioMoral #divModificaContactosSocioMoral")
							.css("display", "block");
				}

				fnOpenDialogModificaSocioMoral();

			} else if (tipoSocioMod == 3) {

				$("#tipoSocioModificacion").val(tipoSocioMod);

				$("form#formModificaSocioFideicomiso #idSocio").val(idSocio);
				$("form#formModificaSocioFideicomiso #msFideicomisoIdPersona")
						.val(idPersona);
				$(
						"form#formModificaSocioFideicomiso #tipoSocio\\.idTipoPersona")
						.val(tipoSocioMod);
				$("form#formModificaSocioFideicomiso #idPersonaMoralPatron")
						.val(idPersonaMoralPatron);

				$("form#formModificaSocioFideicomiso #esPersonaFisica").val(
						esPersonaFisica);
				$("form#formModificaSocioFideicomiso #msFideicomisoRFC").val(
						rfc);
				$("form#formModificaSocioFideicomiso #msFideicomisoNombre")
						.val(nombreRazonSocial);

				// secc. contrato
				$("form#formModificaSocioFideicomiso #msFidecomisoProtoolo")
						.val(obRowSelected.numeroInstrumetoProtocolizacion);
				$("form#formModificaSocioFideicomiso #msFideicomisoNotaria")
						.val(obRowSelected.notariaCorreduria);
				$("form#formModificaSocioFideicomiso #estado\\.clave").val(
						obRowSelected.estado.clave);

				var f = obRowSelected.fechaExpedicionContrato;

				if (f.indexOf("-") != -1) {
					/*
					 * var f1 = new Date(f); var curr_date = f1.getDate() + 1;
					 * var curr_month = f1.getMonth() + 1; var curr_year =
					 * f1.getFullYear();
					 */

					var array_f = f.split("-");

					var curr_date_str = array_f[2];
					var curr_month_str = array_f[1];
					var curr_year_str = array_f[0];

					$(
							"form#formModificaSocioFideicomiso #msFechaExpedicionContrato")
							.val(
									curr_date_str + '/' + curr_month_str + '/'
											+ curr_year_str);

				} else {
					$(
							"form#formModificaSocioFideicomiso #msFechaExpedicionContrato")
							.val(f);
				}

				// secc. dom. fiscal
				if (obRowSelected.domicilioFiscal != null) {
					$("form#formModificaSocioFideicomiso #msFideicomisoCalle")
							.val(obRowSelected.domicilioFiscal.calle);
					$("form#formModificaSocioFideicomiso #msFideicomisoNumExt")
							.val(obRowSelected.domicilioFiscal.numExteriorAlf);
					$("form#formModificaSocioFideicomiso #msFideicomisoNumInt")
							.val(obRowSelected.domicilioFiscal.numInteriorAlf);

					if (obRowSelected.domicilioFiscal.vialidadReferenciaPrimaria != null) {
						$(
								"form#formModificaSocioFideicomiso #msFideicomisoReferUno")
								.val(
										obRowSelected.domicilioFiscal.vialidadReferenciaPrimaria.nombre);
					}

					if (obRowSelected.domicilioFiscal.vialidadReferenciaSecundaria != null) {
						$(
								"form#formModificaSocioFideicomiso #msFideicomisoReferDos")
								.val(
										obRowSelected.domicilioFiscal.vialidadReferenciaSecundaria.nombre);
					}

					if (obRowSelected.domicilioFiscal.vialidadReferenciaPosterior != null) {
						$(
								"form#formModificaSocioFideicomiso #msFideicomisoReferPost")
								.val(
										obRowSelected.domicilioFiscal.vialidadReferenciaPosterior.nombre);
					}

					if (obRowSelected.domicilioFiscal.asentamiento != null) {
						$(
								"form#formModificaSocioFideicomiso #msFideicomisoColonia")
								.val(
										obRowSelected.domicilioFiscal.asentamiento.nombre);

						if (obRowSelected.domicilioFiscal.asentamiento.localidad != null) {
							$(
									"form#formModificaSocioFideicomiso #msFideicomisoLocalidad")
									.val(
											obRowSelected.domicilioFiscal.asentamiento.localidad.nombre);

							if (obRowSelected.domicilioFiscal.asentamiento.localidad.municipio != null) {
								$(
										"form#formModificaSocioFideicomiso #msFideicomisoDeleg")
										.val(
												obRowSelected.domicilioFiscal.asentamiento.localidad.municipio.nombre);

								if (obRowSelected.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa != null) {
									$(
											"form#formModificaSocioFideicomiso #msFideicomisoEntidad")
											.val(
													obRowSelected.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre);
								}
							}
						}
					}

					$("form#formModificaSocioFideicomiso #msFideicomisoCP")
							.val(obRowSelected.domicilioFiscal.codigoPostal.codigoPostal);
				}

				if (nacionalidadMod == "Extranjero") {
					$(
							"form#formModificaSocioFideicomiso #msFideicomisoNacional")
							.val(false);
				} else {
					$(
							"form#formModificaSocioFideicomiso #msFideicomisoNacional")
							.val(true);
				}

				if (nacionalidadDomMod == "Extranjera") {
					$(
							"form#formModificaSocioFideicomiso #msFideicomisoDomicilioNacional")
							.val(false);
				} else {
					$(
							"form#formModificaSocioFideicomiso #msFideicomisoDomicilioNacional")
							.val(true);
				}

				mcSocioModFideicomiso = new MedioContacto(
						"dtModificaContactosSocioFideicomiso", 2,
						tpPropietarioSocioPersonaFisica, idSocio, idSolicitud,
						null);
				mcSocioModFideicomiso.init();
				mcSocioModFideicomiso.extendValidation();
				fnOpenDialogModificaSocioFideicomiso();
			}
			break;
		case 'ELIMINAR':
			oDialogEliminarSocio.dialog('open');
			break;
		}
	} else {
		var oDialogo;
		construirDialogoGenerico(
				"#dialogoMensajes",
				oDialogo,
				"Error",
				"Ya se tiene un movimiento previo registrado para este socio.<br>"
						+ "Por favor seleccione la opci\u00F3n: <b>'Detalle'</b> en la secci\u00F3n: <b>'Informaci\u00F3n a modificar'</b> para editar la informaci\u00F3n del socio.<br>"
						+ "Recuerde que \u00FAnicamente podr\u00E1 modificar la informaci\u00F3n del socio si usted seleccion\u00F3 previamente un movimieno de <b>modificaci\u00F3n</b>.<br>"
						+ "Si seleccion\u00F3 la opci\u00F3n: <b>eliminar</b>, el <b>'Detalle'</b> solo mostrar\u00E1 la informaci\u00F3n correspondiente sin posibilidad de edici\u00F3n.",
				true, undefined, undefined, 250, 700);
	}
}

function cancelarSocios() {
	fnHideErrores(sIdDialogEliminarSocio);
	$(this).dialog("close");
}

function eliminarTramiteSocios(data) {
	fnHideErrores(sIdDialogEliminarSocioForSession);
	/* Obtenemos el radio seleccionado */
	var obRowSelected = fnGetRowSelected(dtSocio);
	var idPersona = obRowSelected.idPersona;
	var esNacional = obRowSelected.esNacional;
	var esDomicilioNacional = obRowSelected.esDomicilioNacional;
	var nombres = obRowSelected.nombres;
	var primerApellido = obRowSelected.primerApellido;
	var segundoApellido = obRowSelected.segundoApellido;
	var nombreRazonSocial = obRowSelected.nombreRazonSocial;
	var sSource = '/socios/fb/eliminarSocio';

	var socioObj = new Object();
	socioObj.tipoSocio = new Object();
	socioObj.mediosContacto = new Object();

	socioObj.idPersona = obRowSelected.idPersona;
	// socioObj.tipoSocio.descripcion =
	// $('#tipoPersonaFiscalHiddenSocio').val();
	socioObj.tipoSocio.idTipoPersona = obRowSelected.tipoSocio.idTipoPersona;
	socioObj.tipoSocio.descripcion = obRowSelected.tipoSocio.descripcion;
	socioObj.tipoPersonaFiscalPatron = $('#tipoPersonaFiscalHiddenSocio').val();
	if (obRowSelected.esNacional == "Nacional") {
		socioObj.esNacional = true;
	} else {
		socioObj.esNacional = false;
	}

	if (obRowSelected.esDomicilioNacional == "Nacional") {
		socioObj.esDomicilioNacional = true;
	} else {
		socioObj.esDomicilioNacional = false;
	}

	socioObj.nombres = obRowSelected.nombres;
	socioObj.primerApellido = obRowSelected.primerApellido;
	socioObj.segundoApellido = obRowSelected.segundoApellido;
	socioObj.nombreRazonSocial = obRowSelected.nombreRazonSocial;

	// var idSocio = obRowSelected.idSocio;

	if (obRowSelected.tipoSocio.idTipoPersona == 1) {
		mcSocioModFisico = new MedioContacto("dtModificaContactosSocioFisico",
				2, tpPropietarioSocioPersonaFisica, obRowSelected.idSocio,
				idSolicitud, null);
		mcSocioModFisico.init();
		mcSocioModFisico.extendValidation();
		socioObj.mediosContacto = mcSocioModFisico.obtenerListaMediosContacto();
	} else if (obRowSelected.tipoSocio.idTipoPersona == 2) {
		mcSocioModMoral = new MedioContacto("dtModificaContactosSocioMoral", 2,
				tpPropietarioSocioPersonaFisica, obRowSelected.idSocio,
				idSolicitud, null);
		mcSocioModMoral.init();
		mcSocioModMoral.extendValidation();
		socioObj.mediosContacto = mcSocioModMoral.obtenerListaMediosContacto();
	} else if (obRowSelected.tipoSocio.idTipoPersona == 3) {
		mcSocioModFideicomiso = new MedioContacto(
				"dtModificaContactosSocioFideicomiso", 2,
				tpPropietarioSocioPersonaFisica, obRowSelected.idSocio,
				idSolicitud, null);
		mcSocioModFideicomiso.init();
		mcSocioModFideicomiso.extendValidation();
		socioObj.mediosContacto = mcSocioModFideicomiso
				.obtenerListaMediosContacto();
	}

	document.getElementById("responseSocioHidden").value = 2;

	sendToServer(sSource, socioObj, procesarRespuestaParaSocios, false);
}

function cancelarTramiteSocios() {
	fnHideErrores(sIdDialogEliminarSocioForSession);
	$(this).dialog("close");
}

function fnOpenDialogEliminarSocio() {

	// Validamos que exista un elemento seleccionado.
	if (fnValidaRegistroSeleccionado(dtSocio)) {
		var obRowSelected = fnGetRowSelected(dtSocio);
		var idSocio = obRowSelected.idSocio;

		var socio = new Object();
		socio.idSocio = idSocio;
		accionSocioSolicitada = 'ELIMINAR';
		sendToServer('/socios/fb/validaMovimientoPrevio', socio,
				fnCallbackOpenDialogModificarSocio, false);

	} else if (fnValidaRegistroSeleccionado(dtSocioForSession)) {

		oDialogEliminarSocioForSession.dialog('open');
	} else {
		// Mostramos mensaje de error
		fnDialogErrorSinSeleccionSocio();
	}
}

function fnDialogErrorSinSeleccionSocio() {
	oDialogErrorSinSeleccionSocio = $(sIdDialogErrorSinSeleccionSocio).dialog({
		autoOpen : false,
		resizable : false,
		height : 140,
		modal : true,
		buttons : {
			'Aceptar' : function() {
				$(this).dialog("close");
			}
		}
	});

	oDialogErrorSinSeleccionSocio.dialog('open');
}

function fnOpenDialogNuevoSocio() {
	oDialogNuevoSocio = $(sIdDialogNuevoSocio).dialog({
		autoOpen : false,
		modal : true,
		resizable : false,
		width : 1030,
		closeOnEscape : false,
		open : function(event, ui) {

		},
		close : function(event, ui) {

		},
		buttons : {

		}
	});
	oDialogNuevoSocio.dialog("open");
}

function deshacerAccionSobreSocio() {

	var sSource = '/socios/fb/deshacerAccionSobreSocio';
	var socioObj = new Object();
	socioObj.tipoSocio = new Object();
	var esNacionalStrDeshacerSocio = document
			.getElementById("esNacionalStrDeshacerSocio").value;
	var esDomicilioNacionalStrDeshacerSocio = document
			.getElementById("esDomicilioNacionalStrDeshacerSocio").value;

	var idPersonaDeshacerSocio = document
			.getElementById("idPersonaDeshacerSocio").value;

	socioObj.idPersona = idPersonaDeshacerSocio != "null" ? idPersonaDeshacerSocio
			: ""; // IE no interpreta bien null, no es como FF, chales!!

	if (esNacionalStrDeshacerSocio == "Nacional") {
		socioObj.esNacional = true;
	} else {
		socioObj.esNacional = false;
	}

	if (esDomicilioNacionalStrDeshacerSocio == "Nacional") {
		socioObj.esDomicilioNacional = true;
	} else {
		socioObj.esDomicilioNacional = false;
	}

	socioObj.primerApellido = document
			.getElementById("primerApellidoDeshacerSocio").value;
	socioObj.segundoApellido = document
			.getElementById("segundoApellidoDeshacerSocio").value;
	socioObj.nombres = document.getElementById("nombresDeshacerSocio").value;
	socioObj.nombreRazonSocial = document
			.getElementById("nombreRazonSocialDeshacerSocio").value;
	socioObj.tipoSocio.idTipoPersona = document
			.getElementById("idTipoPersonaDeshacerSocio").value;
	socioObj.tipoSocio.descripcion = document
			.getElementById("tipoPersonaDescripcionDeshacerSocio").value;

	socioObj.tipoPersonaFiscalPatron = $('#tipoPersonaFiscalHiddenSocio').val();

	/*
	 * var obRowSelected = fnGetRowSelected(dtSocio); var idPersona =
	 * obRowSelected.idPersona; var esNacional = obRowSelected.esNacional; var
	 * esDomicilioNacional = obRowSelected.esDomicilioNacional; var nombres =
	 * obRowSelected.nombres; var primerApellido = obRowSelected.primerApellido;
	 * var segundoApellido = obRowSelected.segundoApellido; var
	 * nombreRazonSocial = obRowSelected.nombreRazonSocial; var sSource =
	 * '/socios/fb/deshacerAccionSobreSocio';
	 * 
	 * var socioObj = new Object(); socioObj.tipoSocio = new Object();
	 * 
	 * socioObj.idPersona = obRowSelected.idPersona;
	 * //socioObj.tipoSocio.descripcion =
	 * $('#tipoPersonaFiscalHiddenSocio').val();
	 * socioObj.tipoSocio.idTipoPersona = obRowSelected.tipoSocio.idTipoPersona;
	 * socioObj.tipoSocio.descripcion = obRowSelected.tipoSocio.descripcion;
	 * socioObj.tipoPersonaFiscalPatron =
	 * $('#tipoPersonaFiscalHiddenSocio').val(); if (obRowSelected.esNacional ==
	 * "Nacional"){ socioObj.esNacional = true; } else { socioObj.esNacional =
	 * false; }
	 * 
	 * if (obRowSelected.esDomicilioNacional == "Nacional"){
	 * socioObj.esDomicilioNacional = true; } else {
	 * socioObj.esDomicilioNacional = false; }
	 * 
	 * socioObj.nombres = obRowSelected.nombres; socioObj.primerApellido =
	 * obRowSelected.primerApellido; socioObj.segundoApellido =
	 * obRowSelected.segundoApellido; socioObj.nombreRazonSocial =
	 * obRowSelected.nombreRazonSocial;
	 */

	/*
	 * var idPersona = document.getElementById("idPersonaDeshacerSocio").value;
	 * var sSource =
	 * '/delta-gestionPatronal-web/socios/fb/deshacerEliminarSocio';
	 * 
	 * var datosRequest={ idPersona:
	 * idPersona,tipoPersonaFiscal:tipoPersonaFiscal};
	 */

	document.getElementById("responseSocioHidden").value = 4;
	// $.getJSON(sSource, datosRequest,procesarRespuestaParaSocios);
	sendToServer(sSource, socioObj, procesarRespuestaParaSocios);

}

function showDetalleSocio(indice) {

	if (arrayDatosSocio[indice].tipoSocio.idTipoPersona == 1) { // configuramos
																// socio fisico
		inicializarDatosDetalleDomFiscalFisico();
		oDialogSocioConsultar = $('#divDetallesocioFisico').dialog({
			autoOpen : false,
			resizable : false,
			title : "Socio F\u00EDsico Detalle",
			height : 650,
			width : 900,
			modal : true,
			close : function() {
				$("#divDetalleSocioFisicoDatosContacto").html("");
			},
			buttons : {
				"Cerrar" : function() {
					$(this).dialog("close");
				}
			}
		});

		$("#divDetallesocioFisico").css("display", "block");

		// seccion persona
		$("#divDetallesocioFisico #lbFisicaRFC").text(
				arrayDatosSocio[indice].rfc);
		$("#divDetallesocioFisico #lbFisicaCURP").text(
				arrayDatosSocio[indice].curp);
		$("#divDetallesocioFisico #lbFisicaPrimerAp").text(
				arrayDatosSocio[indice].primerApellido);
		$("#divDetallesocioFisico #lbFisicaSegundoAp").text(
				arrayDatosSocio[indice].segundoApellido);
		$("#divDetallesocioFisico #lbFisicaNombre").text(
				arrayDatosSocio[indice].nombres);

		// seccion dom. fiscal (solo visible para usuario oparetivos, pendiente)
		// y si tiene dom nacional
		if (arrayDatosSocio[indice].domicilioFiscal != null) {
			$("#divDetallesocioFisico #lbFisicaCalle").text(
					arrayDatosSocio[indice].domicilioFiscal.calle);
			$("#divDetallesocioFisico #lbFisicaNumExt").text(
					arrayDatosSocio[indice].domicilioFiscal.numExterior1);
			$("#divDetallesocioFisico #lbFisicaNumInt").text(
					arrayDatosSocio[indice].domicilioFiscal.numInterior);
			if (arrayDatosSocio[indice].domicilioFiscal.vialidadReferenciaPrimaria != null) {
				$("#divDetallesocioFisico #lbFisicaReferUno")
						.text(
								arrayDatosSocio[indice].domicilioFiscal.vialidadReferenciaPrimaria.nombre);
			}
			if (arrayDatosSocio[indice].domicilioFiscal.vialidadReferenciaSecundaria != null) {
				$("#divDetallesocioFisico #lbFisicaReferDos")
						.text(
								arrayDatosSocio[indice].domicilioFiscal.vialidadReferenciaSecundaria.nombre);
			}
			if (arrayDatosSocio[indice].domicilioFiscal.vialidadReferenciaPosterior != null) {
				$("#divDetallesocioFisico #lbFisicaReferPost")
						.text(
								arrayDatosSocio[indice].domicilioFiscal.vialidadReferenciaPosterior.nombre);
			}
			if (arrayDatosSocio[indice].domicilioFiscal.asentamiento != null) {
				$("#divDetallesocioFisico #lbFisicaReferColonia").text(
						arrayDatosSocio[indice].domicilioFiscal.colonia);

				if (arrayDatosSocio[indice].domicilioFiscal.asentamiento.localidad != null) {
					$("#divDetallesocioFisico #lbFisicaReferLocalidad")
							.text(
									arrayDatosSocio[indice].domicilioFiscal.asentamiento.localidad.nombre);

					if (arrayDatosSocio[indice].domicilioFiscal.asentamiento.localidad.municipio != null) {
						$("#divDetallesocioFisico #lbFisicaReferDeleg")
								.text(
										arrayDatosSocio[indice].domicilioFiscal.asentamiento.localidad.municipio.nombre);

						if (arrayDatosSocio[indice].domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa != null) {
							$("#divDetallesocioFisico #lbFisicaReferEntidad")
									.text(
											arrayDatosSocio[indice].domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre);
						}
					}
				}

			}

			$("#divDetallesocioFisico #lbFisicaReferCP")
					.text(
							arrayDatosSocio[indice].domicilioFiscal.codigoPostal.codigoPostal);

			$("#divDetalleSocioFisicoDomicilio").css("display", "block");
			// $("#divDetalleSocioFisicoSinDomicilio").hide();

		} else {
			$("#divDetalleSocioFisicoSinDomicilio").css("display", "block");
			// $("#divDetalleSocioFisicoDomicilio").hide();
		}

		// detalle para socio fisico extranjero con residencia extranjera
		if (arrayDatosSocio[indice].esDomicilioNacional == "Extranjera"
				&& arrayDatosSocio[indice].esNacional == "Extranjero") {

			$("#divDetallesocioFisico #filaNSRFC").hide();
			$("#divDetallesocioFisico #filaNSCURP").hide();
			$("#divDetalleSocioFisicoDatosContacto").hide();
			$("#divEncabezadoDetalleDatosContactoSocioFisico").hide();
			$("#divDetalleSocioFisicoDomicilio").css("display", "none");
		} else {
			$("#divDetallesocioFisico #filaNSRFC").show();
			$("#divDetallesocioFisico #filaNSCURP").show();
			if (arrayDatosSocio[indice].domicilioFiscal != null) {
				$("#divDetalleSocioFisicoDomicilio").css("display", "block");
			}
			$("#divDetalleSocioFisicoDatosContacto").show();
			$("#divEncabezadoDetalleDatosContactoSocioFisico").show();
		}

		var mcSocioDetalle = new MedioContacto(
				"divDetalleSocioFisicoDatosContacto", 1,
				tpPropietarioSocioPersonaFisica,
				arrayDatosSocio[indice].idSocio, idSolicitud, idSujetoObligado,
				false);
		mcSocioDetalle.init();
		mcSocioDetalle.extendValidation();

	} else if (arrayDatosSocio[indice].tipoSocio.idTipoPersona == 2) { // configuramos
																		// socio
																		// moral

		oDialogSocioConsultar = $('#divDetallesocioMoral').dialog({
			autoOpen : false,
			resizable : false,
			title : "Socio Moral Detalle",
			height : 800,
			width : 900,
			modal : true,
			close : function() {
				$("#divDetalleSocioMoralDatosContacto").html("");
			},
			buttons : {
				"Cerrar" : function() {
					$(this).dialog("close");
				}
			}
		});

		$("#divDetallesocioMoral").css("display", "block");

		// seccion persona
		$("#divDetallesocioMoral #lbMoralRFC")
				.text(arrayDatosSocio[indice].rfc);
		$("#divDetallesocioMoral #lbMoralDenomRazonSocial").text(
				arrayDatosSocio[indice].nombreRazonSocial);
		$("#divDetallesocioMoral #lbMoralTipoSociedad").text(
				arrayDatosSocio[indice].tipoSociedad);

		// seccion acta contitutiva

		$("#divDetallesocioMoral #lbMoralNumEscritura")
				.text(
						arrayDatosSocio[indice].escrituraConstitutiva != null ? arrayDatosSocio[indice].escrituraConstitutiva.numEscritura
								: "Sin Informaci\u00F3n");
		$("#divDetallesocioMoral #lbMoralNumNotaria")
				.text(
						arrayDatosSocio[indice].escrituraConstitutiva != null ? arrayDatosSocio[indice].escrituraConstitutiva.numNotaria
								: "Sin Informaci\u00F3n");
		$("#divDetallesocioMoral #lbMoralEntidadFed")
				.text(
						arrayDatosSocio[indice].escrituraConstitutiva != null ? arrayDatosSocio[indice].escrituraConstitutiva.lugarExpedicion.entidadFederativa.nombre
								: "Sin Informaci\u00F3n");
		$("#divDetallesocioMoral #lbMoralMunicipio")
				.text(
						arrayDatosSocio[indice].escrituraConstitutiva != null ? arrayDatosSocio[indice].escrituraConstitutiva.lugarExpedicion.nombre
								: "Sin Informaci\u00F3n");
		$("#divDetallesocioMoral #lbMoralFecConstitucion")
				.text(
						arrayDatosSocio[indice].escrituraConstitutiva != null ? arrayDatosSocio[indice].escrituraConstitutiva.fechaExpedicion
								: "Sin Informaci\u00F3n");

		$("#divDetallesocioMoral #lbMoralFolioMercantil")
				.text(
						arrayDatosSocio[indice].escrituraConstitutiva != null
								&& arrayDatosSocio[indice].escrituraConstitutiva.folioMercantil != undefined ? arrayDatosSocio[indice].escrituraConstitutiva.folioMercantil
								: "Sin Informaci\u00F3n");
		$("#divDetallesocioMoral #lbMoralSeccion")
				.text(
						arrayDatosSocio[indice].escrituraConstitutiva != null
								&& arrayDatosSocio[indice].escrituraConstitutiva.seccion != undefined ? arrayDatosSocio[indice].escrituraConstitutiva.seccion
								: "Sin Informaci\u00F3n");
		$("#divDetallesocioMoral #lbMoralPartida")
				.text(
						arrayDatosSocio[indice].escrituraConstitutiva != null
								&& arrayDatosSocio[indice].escrituraConstitutiva.partida != undefined ? arrayDatosSocio[indice].escrituraConstitutiva.partida
								: "Sin Informaci\u00F3n");
		$("#divDetallesocioMoral #lbMoralVolumen")
				.text(
						arrayDatosSocio[indice].escrituraConstitutiva != null
								&& arrayDatosSocio[indice].escrituraConstitutiva.volumen != undefined ? arrayDatosSocio[indice].escrituraConstitutiva.volumen
								: "Sin Informaci\u00F3n");
		$("#divDetallesocioMoral #lbMoralFoja")
				.text(
						arrayDatosSocio[indice].escrituraConstitutiva != null
								&& arrayDatosSocio[indice].escrituraConstitutiva.foja != undefined ? arrayDatosSocio[indice].escrituraConstitutiva.foja
								: "Sin Informaci\u00F3n");

		// seccion dom. fiscal (solo visible para usuario oparetivos, pendiente)
		// y si tiene dom nacional
		if (arrayDatosSocio[indice].domicilioFiscal != null) {
			$("#divDetallesocioMoral #lbMoralCalle").text(
					arrayDatosSocio[indice].domicilioFiscal.calle);
			$("#divDetallesocioMoral #lbMoralNumExt").text(
					arrayDatosSocio[indice].domicilioFiscal.numExteriorAlf);
			$("#divDetallesocioMoral #lbMoralNumInt").text(
					arrayDatosSocio[indice].domicilioFiscal.numInteriorAlf);
			if (arrayDatosSocio[indice].domicilioFiscal.vialidadReferenciaPrimaria != null) {
				$("#divDetallesocioMoral #lbMoralReferUno")
						.text(
								arrayDatosSocio[indice].domicilioFiscal.vialidadReferenciaPrimaria.nombre);
			}
			if (arrayDatosSocio[indice].domicilioFiscal.vialidadReferenciaSecundaria != null) {
				$("#divDetallesocioMoral #lbMoralReferDos")
						.text(
								arrayDatosSocio[indice].domicilioFiscal.vialidadReferenciaSecundaria.nombre);
			}
			if (arrayDatosSocio[indice].domicilioFiscal.vialidadReferenciaPosterior != null) {
				$("#divDetallesocioMoral #lbMoralReferPost")
						.text(
								arrayDatosSocio[indice].domicilioFiscal.vialidadReferenciaPosterior.nombre);
			}
			if (arrayDatosSocio[indice].domicilioFiscal.asentamiento != null) {
				$("#divDetallesocioMoral #msFideicomisoReferColonia")
						.text(
								arrayDatosSocio[indice].domicilioFiscal.asentamiento.nombre);

				if (arrayDatosSocio[indice].domicilioFiscal.asentamiento.localidad != null) {
					$("#divDetallesocioMoral #lbMoralReferLocalidad")
							.text(
									arrayDatosSocio[indice].domicilioFiscal.asentamiento.localidad.nombre);

					if (arrayDatosSocio[indice].domicilioFiscal.asentamiento.localidad.municipio != null) {
						$("#divDetallesocioMoral #lbMoralReferDeleg")
								.text(
										arrayDatosSocio[indice].domicilioFiscal.asentamiento.localidad.municipio.nombre);

						if (arrayDatosSocio[indice].domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa != null) {
							$("#divDetallesocioMoral #lbMoralReferEntidad")
									.text(
											arrayDatosSocio[indice].domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre);
						}
					}
				}

			}

			$("#divDetallesocioMoral #lbMoralReferCP").text(
					arrayDatosSocio[indice].domicilioFiscal.codigoPostal.codigoPostal);

			$("#divDetalleSocioMoralDomicilio").css("display", "block");

		} else {
			$("#divDetalleSocioMoralSinDomicilio").css("display", "block");
		}

		// detalle para socio moral extranjero con residencia extranjera
		if (arrayDatosSocio[indice].esDomicilioNacional == "Extranjera"
				&& arrayDatosSocio[indice].esNacional == "Extranjero") {

			$("#divDetallesocioMoral #filaRFCMoral").hide();
			$("#divDetallesocioMoral #filaTipoSociedadMoral").hide();
			$(
					"#divDetallesocioMoral #divDetalleEscrituraConstitutivaSocioMoral")
					.css("display", "none");
			$("#divDetalleSocioMoralDomicilio").css("display", "none");

		} else {
			$("#divDetallesocioMoral #filaRFCMoral").show();
			$("#divDetallesocioMoral #filaTipoSociedadMoral").show();
			$(
					"#divDetallesocioMoral #divDetalleEscrituraConstitutivaSocioMoral")
					.css("display", "block");
			if (arrayDatosSocio[indice].domicilioFiscal != null) {
				$("#divDetallesocioMoral #divDetalleSocioMoralDomicilio").css(
						"display", "block");
			}

		}

		var mcSocioDetalle = new MedioContacto(
				"divDetalleSocioMoralDatosContacto", 1,
				tpPropietarioSocioPersonaFisica,
				arrayDatosSocio[indice].idSocio, idSolicitud, idSujetoObligado,
				false);
		mcSocioDetalle.init();
		mcSocioDetalle.extendValidation();

	} else if (arrayDatosSocio[indice].tipoSocio.idTipoPersona == 3) { // configuramos
																		// socio
																		// fideicomiso

		oDialogSocioConsultar = $('#divDetallesocioFideicomiso').dialog({
			autoOpen : false,
			resizable : false,
			title : "Socio Fideicomiso Detalle",
			height : 700,
			width : 750,
			modal : true,
			close : function() {
				$("#divDetalleSocioFideicomisoDatosContacto").html("");
			},
			buttons : {
				"Cerrar" : function() {
					$(this).dialog("close");
				}
			}
		});

		$("#divDetallesocioFideicomiso").css("display", "block");

		// seccion datos del fideicomiso
		$("#divDetallesocioFideicomiso #lbFideicomisoNombre").text(
				arrayDatosSocio[indice].nombreRazonSocial);
		$("#divDetallesocioFideicomiso #lbFideicomisoRFC").text(
				arrayDatosSocio[indice].rfc);

		// seccion contrato
		$("#divDetallesocioFideicomiso #lbFideicomisoNumInstProt").text(
				arrayDatosSocio[indice].numeroInstrumetoProtocolizacion);
		$("#divDetallesocioFideicomiso #lbFideicomisoNotariaCorreduria").text(
				arrayDatosSocio[indice].notariaCorreduria);
		$("#divDetallesocioFideicomiso #lbFideicomisoEstado")
				.text(
						arrayDatosSocio[indice].estado != null ? arrayDatosSocio[indice].estado.nombre
								: "");
		$("#divDetallesocioFideicomiso #lbFideicomisoFechaExpedicion").text(
				arrayDatosSocio[indice].fechaExpedicionContrato);

		// seccion dom. fiscal (solo visible para usuario oparetivos, pendiente)
		if (arrayDatosSocio[indice].domicilioFiscal != null) {
			$("#divDetallesocioFideicomiso #lbFideicomisoCalle").text(
					arrayDatosSocio[indice].domicilioFiscal.calle);
			$("#divDetallesocioFideicomiso #lbFideicomisoNumExt").text(
					arrayDatosSocio[indice].domicilioFiscal.numExteriorAlf);
			$("#divDetallesocioFideicomiso #lbFideicomisoNumInt").text(
					arrayDatosSocio[indice].domicilioFiscal.numInteriorAlf);
			if (arrayDatosSocio[indice].domicilioFiscal.vialidadReferenciaPrimaria != null) {
				$("#divDetallesocioFideicomiso #lbFideicomisoReferUno")
						.text(
								arrayDatosSocio[indice].domicilioFiscal.vialidadReferenciaPrimaria.nombre);
			}
			if (arrayDatosSocio[indice].domicilioFiscal.vialidadReferenciaSecundaria != null) {
				$("#divDetallesocioFideicomiso #lbFideicomisoReferDos")
						.text(
								arrayDatosSocio[indice].domicilioFiscal.vialidadReferenciaSecundaria.nombre);
			}
			if (arrayDatosSocio[indice].domicilioFiscal.vialidadReferenciaPosterior != null) {
				$("#divDetallesocioFideicomiso #lbFideicomisoReferPost")
						.text(
								arrayDatosSocio[indice].domicilioFiscal.vialidadReferenciaPosterior.nombre);
			}
			if (arrayDatosSocio[indice].domicilioFiscal.asentamiento != null) {
				$("#divDetallesocioFideicomiso #lbFideicomisoReferColonia")
						.text(
								arrayDatosSocio[indice].domicilioFiscal.asentamiento.nombre);

				if (arrayDatosSocio[indice].domicilioFiscal.asentamiento.localidad != null) {
					$(
							"#divDetallesocioFideicomiso #lbFideicomisoReferLocalidad")
							.text(
									arrayDatosSocio[indice].domicilioFiscal.asentamiento.localidad.nombre);

					if (arrayDatosSocio[indice].domicilioFiscal.asentamiento.localidad.municipio != null) {
						$(
								"#divDetallesocioFideicomiso #lbFideicomisoReferDeleg")
								.text(
										arrayDatosSocio[indice].domicilioFiscal.asentamiento.localidad.municipio.nombre);

						if (arrayDatosSocio[indice].domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa != null) {
							$(
									"#divDetallesocioFideicomiso #lbFideicomisoReferEntidad")
									.text(
											arrayDatosSocio[indice].domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre);
						}
					}
				}

			}

			$("#divDetallesocioFideicomiso #lbFideicomisoReferCP").text(
					arrayDatosSocio[indice].domicilioFiscal.codigoPostal.codigoPostal);

			$("#divDetalleSocioFideicomisoDomicilio").css("display", "block");

		} else {
			$("#divDetalleSocioFideicomisoSinDomicilio")
					.css("display", "block");
		}

		var mcSocioDetalle = new MedioContacto(
				"divDetalleSocioFideicomisoDatosContacto", 1,
				tpPropietarioSocioPersonaFisica,
				arrayDatosSocio[indice].idSocio, idSolicitud, idSujetoObligado,
				false);
		mcSocioDetalle.init();
		mcSocioDetalle.extendValidation();
	}

	oDialogSocioConsultar.dialog('open');
	$("#listaForSessionSocio").focus();
}

function inicializarDatosDetalleDomFiscalMoral() {
	$("#divDetallesocioMoral #lbMoralCalle").text("");
	$("#divDetallesocioMoral #lbMoralNumExt").text("");
	$("#divDetallesocioMoral #lbMoralNumInt").text("");
	$("#divDetallesocioMoral #lbMoralReferUno").text("");
	$("#divDetallesocioMoral #lbMoralReferDos").text("");
	$("#divDetallesocioMoral #lbMoralReferPost").text("");
	$("#divDetallesocioMoral #msFideicomisoReferColonia").text("");
	$("#divDetallesocioMoral #lbMoralReferLocalidad").text("");
	$("#divDetallesocioMoral #lbMoralReferDeleg").text("");
	$("#divDetallesocioMoral #lbMoralReferEntidad").text("");
	$("#divDetallesocioMoral #lbMoralReferCP").text("");
}

function inicializarDatosDetalleDomFiscalFisico() {
	$("#divDetallesocioFisico #lbFisicaCalle").text("");
	$("#divDetallesocioFisico #lbFisicaNumExt").text("");
	$("#divDetallesocioFisico #lbFisicaNumInt").text("");
	$("#divDetallesocioFisico #lbFisicaReferUno").text("");
	$("#divDetallesocioFisico #lbFisicaReferDos").text("");
	$("#divDetallesocioFisico #lbFisicaReferPost").text("");
	$("#divDetallesocioFisico #lbFisicaReferColonia").text("");
	$("#divDetallesocioFisico #lbFisicaReferLocalidad").text("");
	$("#divDetallesocioFisico #lbFisicaReferDeleg").text("");
	$("#divDetallesocioFisico #lbFisicaReferEntidad").text("");
	$("#divDetallesocioFisico #lbFisicaReferCP").text("");
}

function parseIndicadorTipoSocio(o) {
	if (o == 1) {
		return 'P .Fisica';
	} else if (o == 2) {
		return 'P. Moral';
	} else {
		return 'Fideicomiso';
	}
}

function parseIndicadorNacionalidad(o) {
	if (o == false) {
		return 'Extranjero';
	} else {
		return 'Nacional';
	}
}

function parseIndicadorResidencia(r) {
	if (r == false) {
		return 'Extranjera';
	} else {
		return 'Nacional';
	}
}

function showAfectacionSocio(accion) {
	return accion;
}

function deshacerSocio(index) {
	return "<a style='cursor:pointer;' onclick='deshacerAccionSocio("
			+ index.idPersona + ", \"" + index.esNacional + "\", \""
			+ index.esDomicilioNacional + "\", \"" + index.primerApellido
			+ "\", \"" + index.segundoApellido + "\", \"" + index.nombres
			+ "\", \"" + index.nombreRazonSocial + "\", "
			+ index.tipoSocio.idTipoPersona + ", \""
			+ index.tipoSocio.descripcion + "\")'>Deshacer Acci&oacute;n</a>";
}

function mostrarDetalleEnTramiteSocios(valor) {
	return "<a style='cursor:pointer;' onclick='mostrarDialogoDeDetalleEnTramiteSocios("
			+ valor.idPersona
			+ ", \""
			+ valor.esNacional
			+ "\", \""
			+ valor.esDomicilioNacional
			+ "\", \""
			+ valor.primerApellido
			+ "\", \""
			+ valor.segundoApellido
			+ "\", \""
			+ valor.nombres
			+ "\", \""
			+ valor.nombreRazonSocial
			+ "\", "
			+ valor.tipoSocio.idTipoPersona
			+ ", \""
			+ valor.tipoSocio.descripcion + "\")'>Mostrar Detalle</a>";
}

function deshacerAccionSocio(idPersonaDeshacerSocio1,
		esNacionalStrDeshacerSocio1, esDomicilioNacionalStrDeshacerSocio1,
		primerApellidoDeshacerSocio1, segundoApellidoDeshacerSocio1,
		nombresDeshacerSocio1, nombreRazonSocialDeshacerSocio1,
		idTipoPersonaDeshacerSocio1, tipoPersonaDescripcionDeshacerSocio1) {

	document.getElementById("idPersonaDeshacerSocio").value = idPersonaDeshacerSocio1;
	document.getElementById("esNacionalStrDeshacerSocio").value = esNacionalStrDeshacerSocio1;
	document.getElementById("esDomicilioNacionalStrDeshacerSocio").value = esDomicilioNacionalStrDeshacerSocio1;
	document.getElementById("primerApellidoDeshacerSocio").value = primerApellidoDeshacerSocio1;
	document.getElementById("segundoApellidoDeshacerSocio").value = segundoApellidoDeshacerSocio1;
	document.getElementById("nombresDeshacerSocio").value = nombresDeshacerSocio1;
	document.getElementById("nombreRazonSocialDeshacerSocio").value = nombreRazonSocialDeshacerSocio1;
	document.getElementById("idTipoPersonaDeshacerSocio").value = idTipoPersonaDeshacerSocio1;
	document.getElementById("tipoPersonaDescripcionDeshacerSocio").value = tipoPersonaDescripcionDeshacerSocio1;

	$("#listaForSessionSocio").focus();

	oDialogDeshacerEliminarSocio.dialog('open');
}

function mostrarDialogoDeDetalleEnTramiteSocios(idPersonaDetalleSocio1,
		esNacionalStrDetalleSocio1, esDomicilioNacionalStrDetalleSocio1,
		primerApellidoDetalleSocio1, segundoApellidoDetalleSocio1,
		nombresDetalleSocio1, nombreRazonSocialDetalleSocio1,
		idTipoPersonaDetalleSocio1, tipoPersonaDescripcionDetalleSocio1) {

	var socio = new Object();
	var tipoSocio = new Object();

	socio.tipoSocio = tipoSocio;

	socio.esDomicilioNacional = esDomicilioNacionalStrDetalleSocio1 == "Nacional" ? true
			: false;
	socio.esNacional = esNacionalStrDetalleSocio1 == "Nacional" ? true : false;
	socio.tipoSocio.idTipoPersona = idTipoPersonaDetalleSocio1;
	socio.primerApellido = primerApellidoDetalleSocio1;
	socio.segundoApellido = segundoApellidoDetalleSocio1;
	socio.nombres = nombresDetalleSocio1;
	socio.nombreRazonSocial = nombreRazonSocialDetalleSocio1;
	socio.idPersona = idPersonaDetalleSocio1;

	sendToServer('/socios/fb/obtenerDetalleEnTramiteSocios', socio,
			procesaRespuestaParaDetalleEnTramiteSocios, false);
}

function procesarRespuestaParaSocios(data) {
	dato = document.getElementById("responseSocioHidden").value;
	var oDialog;

	switch (dato) {
	case "1":
		oDialog = oDialogAgregarSocio;
		break;
	case "2":
		// oDialog=oDialogEliminarSocioForSession;
		oDialog = oDialogEliminarSocio;
		break;
	case "3":
		var tipoSocioModificacionVar = document
				.getElementById("tipoSocioModificacion").value;

		if (tipoSocioModificacionVar == 1) {
			oDialog = oDialogModificaSocioFisico;
		} else if (tipoSocioModificacionVar == 2) {
			oDialog = oDialogModificaSocioMoral;
		} else if (tipoSocioModificacionVar == 3) {
			oDialog = oDialogModificaSocioFideicomiso;
		}
		break;
	case "4":
		oDialog = oDialogDeshacerEliminarSocio;
		break;
	}

	if (data == false) {
		oDialogErrorDuplicadoSocio.dialog("open");
	}
	// cleanForm();
	dtSocioForSession.fnDraw();
	oDialog.dialog('close');
}

function procesaRespuestaParaDetalleEnTramiteSocios(data) {

	llenarDatosDeSocioParaGridMediosContacto(data);

	if (data.tipoSocio.idTipoPersona == 1) { // socio fisico
		if (data.accion == 'ELIMINAR') {
			$(
					"#divDetalleEnTramiteSocioFisicoEliminar #detalleEnTramiteSocioFisicoRFCEliminar")
					.text(data.rfc);
			$(
					"#divDetalleEnTramiteSocioFisicoEliminar #detalleEnTramiteSocioFisicoCURPEliminar")
					.text(data.curp);
			$(
					"#divDetalleEnTramiteSocioFisicoEliminar #detalleEnTramiteSocioFisicoPrimerApellidoEliminar")
					.text(data.primerApellido);
			$(
					"#divDetalleEnTramiteSocioFisicoEliminar #detalleEnTramiteSocioFisicoSegundoApellidoEliminar")
					.text(data.segundoApellido);
			$(
					"#divDetalleEnTramiteSocioFisicoEliminar #detalleEnTramiteSocioFisicoNombresEliminar")
					.text(data.nombres);

			if (data.domicilioFiscal != null) {

				$(
						"#divDetalleEnTramiteSocioFisicoEliminarDomicilio #detalleTramiteEliminarFiscicoNumExt")
						.val(data.domicilioFiscal.numExteriorAlf);
				$(
						"#divDetalleEnTramiteSocioFisicoEliminarDomicilio #detalleTramiteEliminarFiscioNumInt")
						.val(data.domicilioFiscal.numInteriorAlf);

				if (data.domicilioFiscal.vialidadReferenciaPrimaria != null) {
					$(
							"#divDetalleEnTramiteSocioFisicoEliminarDomicilio #detalleTramiteEliminarFiscicoReferUno")
							.val(
									data.domicilioFiscal.vialidadReferenciaPrimaria.nombre);
				}

				if (data.domicilioFiscal.asentamiento != null) {
					$(
							"#divDetalleEnTramiteSocioFisicoEliminarDomicilio #detalleTramiteEliminarFiscicoColonia")
							.val(data.domicilioFiscal.asentamiento.nombre);

					if (data.domicilioFiscal.asentamiento.localidad != null) {

						if (data.domicilioFiscal.asentamiento.localidad.municipio != null) {
							$(
									"#divDetalleEnTramiteSocioFisicoEliminarDomicilio #detalleTramiteEliminarFiscicoDeleg")
									.val(
											data.domicilioFiscal.asentamiento.localidad.municipio.nombre);

							if (data.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa != null) {
								$(
										"#divDetalleEnTramiteSocioFisicoEliminarDomicilio #detalleTramiteEliminarFiscicoEntidad")
										.val(
												data.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre);
							}
						}
					}
				}

				$(
						"#divDetalleEnTramiteSocioFisicoEliminarDomicilio #detalleTramiteEliminarFiscicoCP")
						.val(data.domicilioFiscal.codigoPostal.codigoPostal);

				$("#divDetalleEnTramiteSocioFisicoEliminarDomicilio").css(
						"display", "block");
			} else {
				$("#divDetalleEnTramiteSocioFisicoEliminarSinDomicilio").css(
						"display", "block");
			}

			oDialogDetalleEnTramiteSocioFisicoEliminar.dialog('open');
			dtSocioFisicoDatosContactoDetalleEliminar.fnDraw();

		} else {
			socioTipoSocioIdTipoPersonaForMediosContactoGeneral = 1;
						
			$("#divDetalleEnTramiteSocioFisico #idPersona").val(data.idPersona);
			$("#divDetalleEnTramiteSocioFisico #esNacional").val(
					data.esNacional);
			$("#divDetalleEnTramiteSocioFisico #esDomicilioNacional").val(
					data.esDomicilioNacional);

			$("#divDetalleEnTramiteSocioFisico #detalleEnTramiteSocioFisicoRFC")
					.text(data.rfc);
			$(
					"#divDetalleEnTramiteSocioFisico #detalleEnTramiteSocioFisicoCURP")
					.text(data.curp);
			$(
					"#divDetalleEnTramiteSocioFisico #detalleEnTramiteSocioFisicoPrimerApellido")
					.text(data.primerApellido);
			$(
					"#divDetalleEnTramiteSocioFisico #detalleEnTramiteSocioFisicoSegundoApellido")
					.text(data.segundoApellido);
			$(
					"#divDetalleEnTramiteSocioFisico #detalleEnTramiteSocioFisicoNombres")
					.text(data.nombres);

			if (data.domicilioFiscal != null) {

				$(
						"#divDetalleEnTramiteSocioFisicoDomicilio #detalleTramiteFiscicoNumExt")
						.val(data.domicilioFiscal.numExteriorAlf);
				$(
						"#divDetalleEnTramiteSocioFisicoDomicilio #detalleTramiteFiscioNumInt")
						.val(data.domicilioFiscal.numInteriorAlf);

				if (data.domicilioFiscal.vialidadReferenciaPrimaria != null) {
					$(
							"#divDetalleEnTramiteSocioFisicoDomicilio #detalleTramiteFiscicoReferUno")
							.val(
									data.domicilioFiscal.vialidadReferenciaPrimaria.nombre);
				}

				if (data.domicilioFiscal.asentamiento != null) {
					$(
							"#divDetalleEnTramiteSocioFisicoDomicilio #detalleTramiteFiscicoColonia")
							.val(data.domicilioFiscal.asentamiento.nombre);

					if (data.domicilioFiscal.asentamiento.localidad != null) {

						if (data.domicilioFiscal.asentamiento.localidad.municipio != null) {
							$(
									"#divDetalleEnTramiteSocioFisicoDomicilio #detalleTramiteFiscicoDeleg")
									.val(
											data.domicilioFiscal.asentamiento.localidad.municipio.nombre);

							if (data.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa != null) {
								$(
										"#divDetalleEnTramiteSocioFisicoDomicilio #detalleTramiteFiscicoEntidad")
										.val(
												data.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre);
							}
						}
					}
				}

				$(
						"#divDetalleEnTramiteSocioFisicoDomicilio #detalleTramiteFiscicoCP")
						.val(data.domicilioFiscal.codigoPostal.codigoPostal);

				$("#divDetalleEnTramiteSocioFisicoDomicilio").css("display",
						"block");
			} else {
				$("#divDetalleEnTramiteSocioFisicoSinDomicilio").css("display",
						"block");
			}

			oDialogDetalleEnTramiteSocioFisico.dialog('open');
			dtSocioFisicoDatosContactoDetalle.fnDraw();

		}

	} else if (data.tipoSocio.idTipoPersona == 2) { // socio moral
		if (data.accion == 'ELIMINAR') {
			$(
					"#divDetalleEnTramiteSocioMoralEliminar #detalleEnTramiteSocioMoralRFCEliminar")
					.text(data.rfc);
			$(
					"#divDetalleEnTramiteSocioMoralEliminar #detalleEnTramiteSocioMoralDenomRazonSocialEliminar")
					.text(data.nombreRazonSocial);
			$(
					"#divDetalleEnTramiteSocioMoralEliminar #detalleEnTramiteSocioMoralTipoSociedadEliminar")
					.text(data.tipoSociedad);
			if (data.escrituraConstitutiva != null) {

				var txtNumEscritura = data.escrituraConstitutiva != undefined ? data.escrituraConstitutiva.numEscritura
						: "Sin Informaci\u00F3n"
				var txtNumNotaria = data.escrituraConstitutiva != undefined ? data.escrituraConstitutiva.numNotaria
						: "Sin Informaci\u00F3n"
				var txtEntidad = data.escrituraConstitutiva != undefined ? data.escrituraConstitutiva.lugarExpedicion.entidadFederativa.nombre
						: "Sin Informaci\u00F3n"
				var txtMunicipio = data.escrituraConstitutiva != undefined ? data.escrituraConstitutiva.lugarExpedicion.nombre
						: "Sin Informaci\u00F3n"
				var txtFechaConstitucion = data.escrituraConstitutiva != undefined ? data.escrituraConstitutiva.fechaExpedicion
						: "Sin Informaci\u00F3n"

				var txtFolio = data.escrituraConstitutiva.folioMercantil != undefined ? data.escrituraConstitutiva.folioMercantil
						: "Sin Informaci\u00F3n"
				var txtSeccion = data.escrituraConstitutiva.seccion != undefined ? data.escrituraConstitutiva.seccion
						: "Sin Informaci\u00F3n"
				var txtPartida = data.escrituraConstitutiva.partida != undefined ? data.escrituraConstitutiva.partida
						: "Sin Informaci\u00F3n"
				var txtVolumen = data.escrituraConstitutiva.volumen != undefined ? data.escrituraConstitutiva.volumen
						: "Sin Informaci\u00F3n"
				var txtFoja = data.escrituraConstitutiva.foja != undefined ? data.escrituraConstitutiva.foja
						: "Sin Informaci\u00F3n"

				$(
						"#divDetalleEnTramiteSocioMoralEliminar #detalleEnTramiteSocioMoralNumEscrituraEliminar")
						.text(txtNumEscritura);
				$(
						"#divDetalleEnTramiteSocioMoralEliminar #detalleEnTramiteSocioMoralNumNotariaEliminar")
						.text(txtNumNotaria);
				$(
						"#divDetalleEnTramiteSocioMoralEliminar #detalleEnTramiteSocioMoralEntidadFedEliminar")
						.text(txtEntidad);
				$(
						"#divDetalleEnTramiteSocioMoralEliminar #detalleEnTramiteSocioMoralMunicipioEliminar")
						.text(txtMunicipio);
				$(
						"#divDetalleEnTramiteSocioMoralEliminar #detalleEnTramiteSocioMoralFecConstitucionEliminar")
						.text(txtFechaConstitucion);

				$(
						"#divDetalleEnTramiteSocioMoralEliminar #detalleEnTramiteSocioMoralFolioMercantilEliminar")
						.text(txtFolio);
				$(
						"#divDetalleEnTramiteSocioMoralEliminar #detalleEnTramiteSocioMoralSeccionEliminar")
						.text(txtSeccion);
				$(
						"#divDetalleEnTramiteSocioMoralEliminar #detalleEnTramiteSocioMoralPartidaEliminar")
						.text(txtPartida);
				$(
						"#divDetalleEnTramiteSocioMoralEliminar #detalleEnTramiteSocioMoralVolumenEliminar")
						.text(txtVolumen);
				$(
						"#divDetalleEnTramiteSocioMoralEliminar #detalleEnTramiteSocioMoralFojaEliminar")
						.text(txtFoja);
			}

			if (data.domicilioFiscal != null) {

				$(
						"#divDetalleEnTramiteSocioMoralEliminarDomicilio #detalleTramiteEliminarMoralNumExt")
						.val(data.domicilioFiscal.numExteriorAlf);
				$(
						"#divDetalleEnTramiteSocioMoralEliminarDomicilio #detalleTramiteEliminarMoralNumInt")
						.val(data.domicilioFiscal.numInteriorAlf);

				if (data.domicilioFiscal.vialidadReferenciaPrimaria != null) {
					$(
							"#divDetalleEnTramiteSocioMoralEliminarDomicilio #detalleTramiteEliminarMoralReferUno")
							.val(
									data.domicilioFiscal.vialidadReferenciaPrimaria.nombre);
				}

				if (data.domicilioFiscal.asentamiento != null) {
					$(
							"#divDetalleEnTramiteSocioMoralEliminarDomicilio #detalleTramiteEliminarMoralColonia")
							.val(data.domicilioFiscal.asentamiento.nombre);

					if (data.domicilioFiscal.asentamiento.localidad != null) {

						if (data.domicilioFiscal.asentamiento.localidad.municipio != null) {
							$(
									"#divDetalleEnTramiteSocioMoralEliminarDomicilio #detalleTramiteEliminarMoralDeleg")
									.val(
											data.domicilioFiscal.asentamiento.localidad.municipio.nombre);

							if (data.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa != null) {
								$(
										"#divDetalleEnTramiteSocioMoralEliminarDomicilio #detalleTramiteEliminarMoralEntidad")
										.val(
												data.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre);
							}
						}
					}
				}

				$(
						"#divDetalleEnTramiteSocioMoralEliminarDomicilio #detalleTramiteEliminarMoralCP")
						.val(data.domicilioFiscal.codigoPostal.codigoPostal);

				$("#divDetalleEnTramiteSocioMoralEliminarDomicilio").css(
						"display", "block");
			} else {
				$("#divDetalleEnTramiteSocioMoralEliminarSinDomicilio").css(
						"display", "block");
			}

			oDialogDetalleEnTramiteSocioMoralEliminar.dialog('open');
			// dtSocioMoralDatosContactoDetalleEliminar.fnDraw();

		} else {

			if (!data.esDomicilioNacional && !data.esNacional) {
				mostrarInformacionMoralExtranjero();

				$("#divDetalleEnTramiteSocioMoral #idPersona").val(
						data.idPersona);
				$("#divDetalleEnTramiteSocioMoral #esNacional").val(
						data.esNacional);
				$("#divDetalleEnTramiteSocioMoral #esDomicilioNacional").val(
						data.esDomicilioNacional);

				$(
						"#divDetalleEnTramiteSocioMoral #detalleEnTramiteSocioMoralRFC")
						.text(data.rfc);
				$(
						"#divDetalleEnTramiteSocioMoral #detalleEnTramiteSocioMoralDenomRazonSocial")
						.text(data.nombreRazonSocial);
				$(
						"#divDetalleEnTramiteSocioMoral #detalleEnTramiteSocioMoralTipoSociedad")
						.text(data.tipoSociedad);

			} else {
				mostrarInformacionMoralNacional();
				socioTipoSocioIdTipoPersonaForMediosContactoGeneral = 2;

				$("#divDetalleEnTramiteSocioMoral #idPersona").val(
						data.idPersona);
				$("#divDetalleEnTramiteSocioMoral #esNacional").val(
						data.esNacional);
				$("#divDetalleEnTramiteSocioMoral #esDomicilioNacional").val(
						data.esDomicilioNacional);

				$(
						"#divDetalleEnTramiteSocioMoral #detalleEnTramiteSocioMoralRFC")
						.text(data.rfc);
				$(
						"#divDetalleEnTramiteSocioMoral #detalleEnTramiteSocioMoralDenomRazonSocial")
						.text(data.nombreRazonSocial);
				$(
						"#divDetalleEnTramiteSocioMoral #detalleEnTramiteSocioMoralTipoSociedad")
						.text(data.tipoSociedad);
				if (data.escrituraConstitutiva != null) {
					
					var numEscritura= data.escrituraConstitutiva.numEscritura;
					var numNotaria	= data.escrituraConstitutiva.numNotaria;
					var entidad		= data.escrituraConstitutiva.lugarExpedicion!=undefined 
										&& data.escrituraConstitutiva.lugarExpedicion.entidadFederativa!=undefined 
										? data.escrituraConstitutiva.lugarExpedicion.entidadFederativa.nombre 
										: "Sin Informaci\u00F3n";
					var delegacion 	= data.escrituraConstitutiva.lugarExpedicion!= undefined ? data.escrituraConstitutiva.lugarExpedicion.nombre : "Sin Informaci\u00F3n";
					var fechaExpedicion	= data.escrituraConstitutiva.fechaExpedicion;
					
					var txtFolio = data.escrituraConstitutiva.folioMercantil != undefined ? data.escrituraConstitutiva.folioMercantil
							: "Sin Informaci\u00F3n"
					var txtSeccion = data.escrituraConstitutiva.seccion != undefined ? data.escrituraConstitutiva.seccion
							: "Sin Informaci\u00F3n"
					var txtPartida = data.escrituraConstitutiva.partida != undefined ? data.escrituraConstitutiva.partida
							: "Sin Informaci\u00F3n"
					var txtVolumen = data.escrituraConstitutiva.volumen != undefined ? data.escrituraConstitutiva.volumen
							: "Sin Informaci\u00F3n"
					var txtFoja = data.escrituraConstitutiva.foja != undefined ? data.escrituraConstitutiva.foja
							: "Sin Informaci\u00F3n"
								
					$("#divDetalleEnTramiteSocioMoral #detalleEnTramiteSocioMoralNumEscritura").text(numEscritura);
					$("#divDetalleEnTramiteSocioMoral #detalleEnTramiteSocioMoralNumNotaria").text(numNotaria);
					$("#divDetalleEnTramiteSocioMoral #detalleEnTramiteSocioMoralEntidadFed").text(entidad);
					$("#divDetalleEnTramiteSocioMoral #detalleEnTramiteSocioMoralMunicipio").text(delegacion);
					$("#divDetalleEnTramiteSocioMoral #detalleEnTramiteSocioMoralFecConstitucion").text(fechaExpedicion);
								
					$(
							"#divDetalleEnTramiteSocioMoral #detalleEnTramiteSocioMoralFolioMercantil")
							.text(txtFolio);
					$(
							"#divDetalleEnTramiteSocioMoral #detalleEnTramiteSocioMoralSeccion")
							.text(txtSeccion);
					$(
							"#divDetalleEnTramiteSocioMoral #detalleEnTramiteSocioMoralPartida")
							.text(txtPartida);
					$(
							"#divDetalleEnTramiteSocioMoral #detalleEnTramiteSocioMoralVolumen")
							.text(txtVolumen);
					$(
							"#divDetalleEnTramiteSocioMoral #detalleEnTramiteSocioMoralFoja")
							.text(txtFoja);
				}

				if (data.domicilioFiscal != null) {

					$(
							"#divDetalleEnTramiteSocioMoralDomicilio #detalleTramiteMoralNumExt")
							.text(data.domicilioFiscal.numExterior1);
					$(
							"#divDetalleEnTramiteSocioMoralDomicilio #detalleTramiteMoralNumInt")
							.text(data.domicilioFiscal.numInterior);

					if (data.domicilioFiscal.vialidadPrimaria != null) {
						$(
								"#divDetalleEnTramiteSocioMoralDomicilio #detalleTramiteMoralReferUno")
								.text(
										data.domicilioFiscal.vialidadPrimaria.nombre);
					}

					if (data.domicilioFiscal.asentamiento != null) {
						$(
								"#divDetalleEnTramiteSocioMoralDomicilio #detalleTramiteMoralColonia")
								.text(data.domicilioFiscal.asentamiento.nombre);

						if (data.domicilioFiscal.asentamiento.localidad != null) {

							if (data.domicilioFiscal.asentamiento.localidad.municipio != null) {
								$(
										"#divDetalleEnTramiteSocioMoralDomicilio #detalleTramiteMoralDeleg")
										.text(
												data.domicilioFiscal.asentamiento.localidad.municipio.nombre);

								if (data.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa != null) {
									$(
											"#divDetalleEnTramiteSocioMoralDomicilio #detalleTramiteMoralEntidad")
											.text(
													data.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre);
								}
							}
						}
					}

					$(
							"#divDetalleEnTramiteSocioMoralDomicilio #detalleTramiteMoralCP")
							.text(
									data.domicilioFiscal.codigoPostal.codigoPostal);

					$("#divDetalleEnTramiteSocioMoralDomicilio").css("display",
							"block");
				} else {
					$("#divDetalleEnTramiteSocioMoralSinDomicilio").css(
							"display", "block");
				}
				dtSocioMoralDatosContactoDetalle.fnDraw();
			}
			oDialogDetalleEnTramiteSocioMoral.dialog('open');
		}
	} else if (data.tipoSocio.idTipoPersona == 3) { // socio fideicomiso
		if (data.accion == 'ELIMINAR') {
			$(
					"#divDetalleEnTramiteSocioFideicomisoEliminar #detalleEnTramiteSocioFideicomisoDenominacionRazonSocialEliminar")
					.text(data.nombreRazonSocial);
			$(
					"#divDetalleEnTramiteSocioFideicomisoEliminar #detalleEnTramiteSocioFideicomisoRFCEliminar")
					.text(data.rfc);
			$(
					"#divDetalleEnTramiteSocioFideicomisoEliminar #detalleEnTramiteSocioFideicomisoNumeroInstrumetoProtocolizacionEliminar")
					.text(data.numeroInstrumetoProtocolizacion);
			$(
					"#divDetalleEnTramiteSocioFideicomisoEliminar #detalleEnTramiteSocioFideicomisoNotariaCorreduriaEliminar")
					.text(data.notariaCorreduria);
			$(
					"#divDetalleEnTramiteSocioFideicomisoEliminar #detalleEnTramiteSocioFideicomisoEstadoEliminar")
					.text(data.estado.nombre);
			$(
					"#divDetalleEnTramiteSocioFideicomisoEliminar #detalleEnTramiteSocioFideicomisoFechaExpedicionContratoEliminar")
					.text(data.fechaExpedicionContrato);

			if (data.domicilioFiscal != null) {

				$(
						"#divDetalleEnTramiteSocioFideicomisoEliminarDomicilio #detalleTramiteEliminarFideicomisoNumExt")
						.val(data.domicilioFiscal.numExteriorAlf);
				$(
						"#divDetalleEnTramiteSocioFideicomisoEliminarDomicilio #detalleTramiteEliminarFideicomisoNumInt")
						.val(data.domicilioFiscal.numInteriorAlf);

				if (data.domicilioFiscal.vialidadReferenciaPrimaria != null) {
					$(
							"#divDetalleEnTramiteSocioFideicomisoEliminarDomicilio #detalleTramiteEliminarFideicomisoReferUno")
							.val(
									data.domicilioFiscal.vialidadReferenciaPrimaria.nombre);
				}

				if (data.domicilioFiscal.asentamiento != null) {
					$(
							"#divDetalleEnTramiteSocioFideicomisoEliminarDomicilio #detalleTramiteEliminarFideicomisoColonia")
							.val(data.domicilioFiscal.asentamiento.nombre);

					if (data.domicilioFiscal.asentamiento.localidad != null) {

						if (data.domicilioFiscal.asentamiento.localidad.municipio != null) {
							$(
									"#divDetalleEnTramiteSocioFideicomisoEliminarDomicilio #detalleTramiteEliminarFideicomisoDeleg")
									.val(
											data.domicilioFiscal.asentamiento.localidad.municipio.nombre);

							if (data.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa != null) {
								$(
										"#divDetalleEnTramiteSocioFideicomisoEliminarDomicilio #detalleTramiteEliminarFideicomisoEntidad")
										.val(
												data.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre);
							}
						}
					}
				}

				$(
						"#divDetalleEnTramiteSocioFideicomisoEliminarDomicilio #detalleTramiteEliminarFideicomisoCP")
						.val(data.domicilioFiscal.codigoPostal.codigoPostal);

				$("#divDetalleEnTramiteSocioFideicomisoEliminarDomicilio").css(
						"display", "block");
			} else {
				$("#divDetalleEnTramiteSocioFideicomisoEliminarSinDomicilio")
						.css("display", "block");
			}

			oDialogDetalleEnTramiteSocioFideicomisoEliminar.dialog('open');
			dtSocioFideicomisoDatosContactoDetalleEliminar.fnDraw();

		} else {
			socioTipoSocioIdTipoPersonaForMediosContactoGeneral = 3;

			$("#divDetalleEnTramiteSocioFideicomiso #idPersona").val(
					data.idPersona);
			$("#divDetalleEnTramiteSocioFideicomiso #esNacional").val(
					data.esNacional);
			$("#divDetalleEnTramiteSocioFideicomiso #esDomicilioNacional").val(
					data.esDomicilioNacional);

			$(
					"#divDetalleEnTramiteSocioFideicomiso #detalleEnTramiteSocioFideicomisoDenominacionRazonSocial")
					.text(data.nombreRazonSocial);
			$(
					"#divDetalleEnTramiteSocioFideicomiso #detalleEnTramiteSocioFideicomisoRFC")
					.text(data.rfc);
			$(
					"#divDetalleEnTramiteSocioFideicomiso #detalleEnTramiteSocioFideicomisoNumeroInstrumetoProtocolizacion")
					.text(data.numeroInstrumetoProtocolizacion);
			$(
					"#divDetalleEnTramiteSocioFideicomiso #detalleEnTramiteSocioFideicomisoNotariaCorreduria")
					.text(data.notariaCorreduria);
			$(
					"#divDetalleEnTramiteSocioFideicomiso #detalleEnTramiteSocioFideicomisoEstado")
					.text(data.estado.nombre);
			$(
					"#divDetalleEnTramiteSocioFideicomiso #detalleEnTramiteSocioFideicomisoFechaExpedicionContrato")
					.text(data.fechaExpedicionContrato);

			if (data.domicilioFiscal != null) {

				$(
						"#divDetalleEnTramiteSocioFideicomisoDomicilio #detalleTramiteFideicomisoNumExt")
						.val(data.domicilioFiscal.numExteriorAlf);
				$(
						"#divDetalleEnTramiteSocioFideicomisoDomicilio #detalleTramiteFideicomisoNumInt")
						.val(data.domicilioFiscal.numInteriorAlf);

				if (data.domicilioFiscal.vialidadReferenciaPrimaria != null) {
					$(
							"#divDetalleEnTramiteSocioFideicomisoDomicilio #detalleTramiteFideicomisoReferUno")
							.val(
									data.domicilioFiscal.vialidadReferenciaPrimaria.nombre);
				}

				if (data.domicilioFiscal.asentamiento != null) {
					$(
							"#divDetalleEnTramiteSocioFideicomisoDomicilio #detalleTramiteFideicomisoColonia")
							.val(data.domicilioFiscal.asentamiento.nombre);

					if (data.domicilioFiscal.asentamiento.localidad != null) {

						if (data.domicilioFiscal.asentamiento.localidad.municipio != null) {
							$(
									"#divDetalleEnTramiteSocioFideicomisoDomicilio #detalleTramiteFideicomisoDeleg")
									.val(
											data.domicilioFiscal.asentamiento.localidad.municipio.nombre);

							if (data.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa != null) {
								$(
										"#divDetalleEnTramiteSocioFideicomisoDomicilio #detalleTramiteFideicomisoEntidad")
										.val(
												data.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre);
							}
						}
					}
				}

				$(
						"#divDetalleEnTramiteSocioFideicomisoDomicilio #detalleTramiteFideicomisoCP")
						.val(data.domicilioFiscal.codigoPostal.codigoPostal);

				$("#divDetalleEnTramiteSocioFideicomisoDomicilio").css(
						"display", "block");
			} else {
				$("#divDetalleEnTramiteSocioFideicomisoSinDomicilio").css(
						"display", "block");
			}

			oDialogDetalleEnTramiteSocioFideicomiso.dialog('open');
			dtSocioFideicomisoDatosContactoDetalle.fnDraw();

		}

	}
}

function mostrarInformacionMoralExtranjero() {

	$("#divDetalleEnTramiteSocioMoral #celdaTituloRFC").hide();
	$("#divDetalleEnTramiteSocioMoral #celdaRFCMoral").hide();
	$("#divDetalleEnTramiteSocioMoral #celdaTituloTipoSociedad").hide();
	$("#divDetalleEnTramiteSocioMoral #celdaTipoSociedad").hide();
	$("#divDetalleEnTramiteSocioMoral #tbSocioMoralDatosContactoDetalle")
			.hide();
	$("#divDetalleEnTramiteSocioMoral #detalleTramiteSocioMoralDPLegenda")
			.hide();
	$("#divDetalleEnTramiteSocioMoral #detalleTramiteSocioMoralECLegenda")
			.hide();
	$("#divDetalleEnTramiteSocioMoral #tblDetalleSocioMoralEC").hide();
	$("#divDetalleEnTramiteSocioMoral #tblDetalleSocioMoralFM").hide();
	$("#divDetalleEnTramiteSocioMoral #tblDetalleSocioMoralSPVF").hide();
	$("#divDetalleEnTramiteSocioMoral #divDetalleEnTramiteSocioMoralDomicilio")
			.hide();
	$("#divDetalleEnTramiteSocioMoral #detalleSocioMoralDatosContacto").hide();

}

function mostrarInformacionMoralNacional() {
	$("#divDetalleEnTramiteSocioMoral #celdaTituloRFC").show();
	$("#divDetalleEnTramiteSocioMoral #celdaRFCMoral").show();
	$("#divDetalleEnTramiteSocioMoral #celdaTituloTipoSociedad").show();
	$("#divDetalleEnTramiteSocioMoral #celdaTipoSociedad").show();
	$("#divDetalleEnTramiteSocioMoral #tbSocioMoralDatosContactoDetalle")
			.show();
	$("#divDetalleEnTramiteSocioMoral #detalleTramiteSocioMoralDPLegenda")
			.show();
	$("#divDetalleEnTramiteSocioMoral #detalleTramiteSocioMoralECLegenda")
			.show();
	$("#divDetalleEnTramiteSocioMoral #tblDetalleSocioMoralEC").show();
	$("#divDetalleEnTramiteSocioMoral #tblDetalleSocioMoralFM").show();
	$("#divDetalleEnTramiteSocioMoral #tblDetalleSocioMoralSPVF").show();
	$("#divDetalleEnTramiteSocioMoral #divDetalleEnTramiteSocioMoralDomicilio")
			.show();
	$("#divDetalleEnTramiteSocioMoral #detalleSocioMoralDatosContacto").show();

}

function llenarDatosDeSocioParaGridMediosContacto(data) {
	socioIdSocioForMediosContactoDetalleTramite = data.idSocio;
	socioIdPersonaForMediosContactoDetalleTramite = data.idPersona;
	socioEsDomicilioNacionalForMediosContactoDetalleTramite = data.esDomicilioNacional;
	socioEsNacionalForMediosContactoDetalleTramite = data.esNacional;
	socioTipoSocioIdTipoPersonaForMediosContactoDetalleTramite = data.tipoSocio.idTipoPersona;
	socioNombresForMediosContactoDetalleTramite = data.nombres;
	socioPrimerApellidoForMediosContactoDetalleTramite = data.primerApellido;
	socioSegundoApellidoForMediosContactoDetalleTramite = data.segundoApellido;
	socioNombreRazonSocialForMediosContactoDetalleTramite = data.nombreRazonSocial;

}

function callbackActualizacionSocioTramite(response) {
	if (isOpRatificacion) {
		checkedObject = $('#chkRatificaSocio');// Se asigna a la variable
												// global checkedObject el
												// objeto check que se
												// deseleccionara en caso de
												// presionar cancelar en la
												// pantalla de confirmación
		callbackValidacionTramiteActivo(response, ratificaTramiteSocio,
				deseleccionarCheck);
		isOpRatificacion = false;
	} else {
		callbackValidacionTramiteActivo(response, guardarTramiteSocio);
	}
}

function guardarSocio() {
	validarSocios('/socios/fb/realizarValidaciones?tipoPersonaFiscal='
			+ tipoPersonaFiscal + '&operacion=' + codigoOperacionGuardarSocio,
			callbackValidarSocios);
}

function validarSocios(url, callbackAEjecutar) {
	sendToServer(url, null, callbackAEjecutar, true);
}

function callbackValidarSocios(response) {
	if (response != undefined || response != null) {
		if (response == 'OK') {
			var rfcEnviarValidacion;
			if (tipoPersonaFiscal == "FISICA") {
				rfcEnviarValidacion = $("#fisica\\.rfc").val();
			} else {
				rfcEnviarValidacion = $("#moral\\.rfc").val();
			}
			validaSolicitudTramiteActivo(
					'/afiliacion/validarTramiteActivo?tipoTramite=' + socio
							+ '&idSolicitud=' + idSolicitud + '&rfc='
							+ rfcEnviarValidacion,
					callbackActualizacionSocioTramite);
		} else {
			var oDialogo;
			construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error",
					response, true, undefined, undefined, 150, 500);
		}
	} else {
		var oDialogo;
		construirDialogoGenerico(
				"#dialogoMensajes",
				oDialogo,
				"Error",
				"Hay un problema al guardar el tr\u00E1mite, contacte al administrador del sistema.",
				true, undefined, undefined, 150, 500);
	}

}

function guardarTramiteSocio() {

	construirSujetoSocio();
	sendToServer('/afiliacion/actualizarTramiteSocio?idSolicitud='
			+ idSolicitud, sujetoObigadoTramite, callbackGuardarTramiteSocio,
			false);
}

function callbackGuardarTramiteSocio(response) {
	callbackEnviarTramite(response, 150, 750);
	evaluarBotonesSolicitud();
}

function construirSujetoSocio() {
	if (sujetoObigadoTramite == undefined || sujetoObigadoTramite == null) {
		sujetoObigadoTramite = new Object();
	}
	sujetoObigadoTramite.cveIdSujetoObligado = $("#cveIdSujetoObligado").val();
	sujetoObigadoTramite.tipoPersonaFiscal = $("#tipoPersonaFiscal").val();
	if (tipoPersonaFiscal == "FISICA") {
		sujetoObigadoTramite.fisica = new Object();
		sujetoObigadoTramite.fisica.idPersona = $("#fisica\\.idPersona").val();
	} else {
		sujetoObigadoTramite.moral = new Object();
		sujetoObigadoTramite.moral.idPersona = $("#moral\\.idPersona").val();
	}
}

function ratificaSocio() {
	var esRatificado = $("#chkRatificaSocio").attr("checked");
	if (esRatificado == undefined) {// Esto significa que el elemento no esta
									// checado
		tramiteSocioRatificado = false;
		evaluarBotonesSocio();
	} else {// Si esta checado
		validarSocios('/socios/fb/realizarValidaciones?tipoPersonaFiscal='
				+ tipoPersonaFiscal + '&operacion='
				+ codigoOperacionRatificarSocio,
				callbackValidarSociosAlRatificar);
	}
}

function callbackValidarSociosAlRatificar(response) {
	if (response != undefined || response != null) {
		if (response == 'OK') {
			var rfcEnviarValidacion;
			if (tipoPersonaFiscal == "FISICA") {
				rfcEnviarValidacion = $("#fisica\\.rfc").val();
			} else {
				rfcEnviarValidacion = $("#moral\\.rfc").val();
			}
			validaSolicitudTramiteActivo(
					'/afiliacion/validarTramiteActivo?tipoTramite=' + socio
							+ '&idSolicitud=' + idSolicitud + '&rfc='
							+ rfcEnviarValidacion,
					callbackActualizacionSocioTramite);
			isOpRatificacion = true;
		} else {
			$("#chkRatificaSocio").removeAttr("checked", false);
			var oDialogo;
			construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error",
					response, true, undefined, undefined, 150, 500);
		}
	} else {
		var oDialogo;
		construirDialogoGenerico(
				"#dialogoMensajes",
				oDialogo,
				"Error",
				"Hay un problema al guardar el tr\u00E1amite, contacte al administrador del sistema.",
				true, undefined, undefined, 150, 500);
	}

}

function ratificaTramiteSocio() {
	construirSujetoSocio();
	sendToServer('/afiliacion/ratificarTramite?tipoTramite=' + tipoTramiteSocio
			+ '&idSolicitud=' + idSolicitud, sujetoObigadoTramite,
			callbackRatificarSocio, false);
}

function callbackRatificarSocio(response) {
	$.unblockUI();
	callbackEnviarTramite(response, 200, 550);
	if (response.mensajeError != undefined && response.mensajeError != null) {
		deseleccionarCheck();
		tramiteSocioRatificado = false;
	} else {
		$("#mensajeSocioRatificacion").show();
		$("#listaForSessionSocio").hide();
		$("#btnGridSocio").hide();
		tramiteSocioRatificado = true;
		tramiteSocioActivo = true;
		evaluarBotonesSolicitud();
	}
	evaluarBotonesSocio();

}

function evaluarBotonesSocio() {
	$("#mensajeSocioRatificacion").hide();
	$("#dgErrorSinSeleccionSocio").hide();
	$("#btnGridSocio").show();
	evaluarBotonesTramiteSocioActivo();
	evaluarBotonesTramiteSocioRatificado();

	// if(!esNuevaSolicitud){
	// if(!isOperadorIMSS){
	// $("#listaForSessionSocio").hide();
	// $("#btnGridSocio").hide();
	// $("#grupoRatificarSocio").hide();
	// $("#btnGuardarSocios").hide();
	// if(tramiteSocioRatificado){
	// $("#mensajeSocioRatificacion").show();
	// }else if(!tramiteSocioRatificado){
	// $("#mensajeSocioRatificacion").hide();
	// }
	// }else if(isOperadorIMSS){
	// evaluarBotonesTramiteSocioActivo();
	// evaluarBotonesTramiteSocioRatificado();
	// }
	// }
}

function evaluarBotonesTramiteSocioActivo() {
	if (tramiteSocioActivo) {
		$("#listaForSessionSocio").show();
	} else if (!tramiteSocioActivo) {
		$("#listaForSessionSocio").show();
	}
}

function evaluarBotonesTramiteSocioRatificado() {
	if (tramiteSocioRatificado) {
		$("#listaForSessionSocio").hide();
		$("#btnGridSocio").hide();
		$("#mensajeSocioRatificacion").show();
		$("#chkRatificaSocio").attr("checked", "checked");
	}
}

function fnOpenDialogModificaSocioFisico() {
	oDialogModificaSocioFisico = $(sIdDialogModificaSocioFisico).dialog({
		autoOpen : false,
		modal : true,
		resizable : false,
		title : "Modificar Socio (Persona F\u00EDsica)",
		width : 950,
		closeOnEscape : false,
		open : function(event, ui) {

		},
		close : function(event, ui) {

		},
		buttons : {
			"Guardar" : function() {
				modificarTramiteSocio();
			},
			'Cancelar' : function() {
				$(this).dialog("close");
			}
		}
	});
	oDialogModificaSocioFisico.dialog("open");
}

function fnOpenDialogModificaSocioMoral() {
	oDialogModificaSocioMoral = $(sIdDialogModificaSocioMoral).dialog({
		autoOpen : false,
		modal : true,
		resizable : false,
		title : "Modificar Socio (Persona Moral)",
		width : 1030,
		closeOnEscape : false,
		open : function(event, ui) {

		},
		close : function(event, ui) {

		},
		buttons : {
			"Guardar" : function() {
				modificarTramiteSocio();
			},
			'Cancelar' : function() {
				$(this).dialog("close");
			}
		}
	});
	oDialogModificaSocioMoral.dialog("open");
}

function fnOpenDialogModificaSocioFideicomiso() {
	oDialogModificaSocioFideicomiso = $(sIdDialogModificaSocioFideicomiso)
			.dialog({
				autoOpen : false,
				modal : true,
				resizable : false,
				title : "Modificar Socio (Fideicomiso)",
				width : 1030,
				closeOnEscape : false,
				open : function(event, ui) {

				},
				close : function(event, ui) {

				},
				buttons : {
					"Guardar" : function() {
						modificarTramiteSocio();
					},
					'Cancelar' : function() {
						$(this).dialog("close");
					}
				}
			});
	oDialogModificaSocioFideicomiso.dialog("open");
}

function fnDialogErrorSinSeleccionPersonaParaSocio() {
	oDialogErrorSinSeleccionPersonaParaSocio = $(
			sIdDialogErrorSinSeleccionPersonaParaSocio).dialog({
		autoOpen : false,
		resizable : false,
		height : 180,
		modal : true,
		buttons : {
			'Aceptar' : function() {
				$(this).dialog("close");
				alert('Pendiente mostrar pantalla de seleccion de persona');
				// fnOpenBuscarPersonaFisica();
			}
		}
	});

	oDialogErrorSinSeleccionPersonaParaSocio.dialog('open');
}

function fnOpenDialogNuevoDatoContactoDetalleSocio() {

	$(
			"#dgNuevoDatoContactoDetalleSocio #nuevoDatoContactoDetalleSocioDescripcion")
			.val('');
	$("#dgNuevoDatoContactoDetalleSocio #tipoContactoSocio").val('');

	oDialogAgregarDatoContactoDetalleSocio.dialog('open');
}

function fnOpenDialogEliminarDatoContactoDetalleSocio() {

	if (fnValidaRegistroSeleccionado(dtSocioFisicoDatosContactoDetalle)
			|| fnValidaRegistroSeleccionado(dtSocioMoralDatosContactoDetalle)
			|| fnValidaRegistroSeleccionado(dtSocioFideicomisoDatosContactoDetalle)) {
		oDialogEliminarDatoContactoDetalleSocio.dialog('open');
	} else {
		fnDialogErrorSinSeleccionSocio();
	}

}

function fnOpenDialogModificarDatoContactoDetalleSocio() {
	if (socioTipoSocioIdTipoPersonaForMediosContactoGeneral == 1) { // s. fisico
		if (fnValidaRegistroSeleccionado(dtSocioFisicoDatosContactoDetalle)) {

			var obRowSelected = fnGetRowSelected(dtSocioFisicoDatosContactoDetalle);

			$("#dgModificarDatoContactoDetalleSocio #idVistaModificarSocio")
					.val(obRowSelected.idVista);
			$(
					"#dgModificarDatoContactoDetalleSocio #errorFormGeneralModificarSocio")
					.val(obRowSelected.errorFormGeneral);
			$(
					"#dgModificarDatoContactoDetalleSocio #tipoContactoModificarSocio")
					.val(obRowSelected.tipoMedioContacto.idTipoMedioContacto);
			$(
					"#dgModificarDatoContactoDetalleSocio #tipoContactoModificarSocio")
					.attr("disabled", "true");
			$(
					"#dgModificarDatoContactoDetalleSocio #modificarDatoContactoDetalleSocioDescripcion")
					.val(obRowSelected.desFormaContacto);

			oDialogModificarDatoContactoDetalleSocio.dialog("open");
		} else {
			fnDialogErrorSinSeleccionSocio();
		}
	}
	if (socioTipoSocioIdTipoPersonaForMediosContactoGeneral == 2) { // socio
																	// moral
		if (fnValidaRegistroSeleccionado(dtSocioMoralDatosContactoDetalle)) {

			var obRowSelected = fnGetRowSelected(dtSocioMoralDatosContactoDetalle);

			$("#dgModificarDatoContactoDetalleSocio #idVistaModificarSocio")
					.val(obRowSelected.idVista);
			$(
					"#dgModificarDatoContactoDetalleSocio #errorFormGeneralModificarSocio")
					.val(obRowSelected.errorFormGeneral);
			$(
					"#dgModificarDatoContactoDetalleSocio #tipoContactoModificarSocio")
					.val(obRowSelected.tipoMedioContacto.idTipoMedioContacto);
			$(
					"#dgModificarDatoContactoDetalleSocio #tipoContactoModificarSocio")
					.attr("disabled", "true");
			$(
					"#dgModificarDatoContactoDetalleSocio #modificarDatoContactoDetalleSocioDescripcion")
					.val(obRowSelected.desFormaContacto);

			oDialogModificarDatoContactoDetalleSocio.dialog("open");
		} else {
			fnDialogErrorSinSeleccionSocio();
		}
	}
	if (socioTipoSocioIdTipoPersonaForMediosContactoGeneral == 3) { // s.
																	// fideicomiso
		if (fnValidaRegistroSeleccionado(dtSocioFideicomisoDatosContactoDetalle)) {

			var obRowSelected = fnGetRowSelected(dtSocioFideicomisoDatosContactoDetalle);

			$("#dgModificarDatoContactoDetalleSocio #idVistaModificarSocio")
					.val(obRowSelected.idVista);
			$(
					"#dgModificarDatoContactoDetalleSocio #errorFormGeneralModificarSocio")
					.val(obRowSelected.errorFormGeneral);
			$(
					"#dgModificarDatoContactoDetalleSocio #tipoContactoModificarSocio")
					.val(obRowSelected.tipoMedioContacto.idTipoMedioContacto);
			$(
					"#dgModificarDatoContactoDetalleSocio #tipoContactoModificarSocio")
					.attr("disabled", "true");
			$(
					"#dgModificarDatoContactoDetalleSocio #modificarDatoContactoDetalleSocioDescripcion")
					.val(obRowSelected.desFormaContacto);

			oDialogModificarDatoContactoDetalleSocio.dialog("open");
		} else {
			fnDialogErrorSinSeleccionSocio();
		}
	}
}

function ocultarDialogosModificarSocios() {
	$("#idDialogModificaSocioFisico").css("display", "none");
	$("#idDialogModificaSocioMoral").css("display", "none");
	$("#idDialogModificaSocioFideicomiso").css("display", "none");
}

function configurarDatePickerModificarFechaExpedicionContratoSocioFideicomiso() {
	/* Fecha de regsitro de sindicato */
	$("#msFechaExpedicionContrato").datepicker({
		maxDate : "+0D",
		showOn : "button",
		buttonImage : context_path + "/static/resources/imagenes/calendar.gif",
		buttonImageOnly : true,
		dateFormat : "dd/mm/yy",
		changeMonth : true,
		changeYear : true,
		yearRange : '-112:+0'
	});
}

function capturaFolioMercantilMod() {
	if ($("form:#formModificaSocioMoral #msMoralFolioMercantil").val() != "") {
		$("form:#formModificaSocioMoral #msMoralSeccion")
				.attr("disabled", true);
		$("form:#formModificaSocioMoral #msMoralPartida")
				.attr("disabled", true);
		$("form:#formModificaSocioMoral #msMoralVolumen")
				.attr("disabled", true);
		$("form:#formModificaSocioMoral #msMoralFoja").attr("disabled", true);
	} else {
		$("form:#formModificaSocioMoral #msMoralSeccion").attr("disabled",
				false);
		$("form:#formModificaSocioMoral #msMoralPartida").attr("disabled",
				false);
		$("form:#formModificaSocioMoral #msMoralVolumen").attr("disabled",
				false);
		$("form:#formModificaSocioMoral #msMoralFoja").attr("disabled", false);
	}
}

function deshabilitarFolioMercantilMod() {
	if ($("form:#formModificaSocioMoral #msMoralSeccion").val() != ""
			|| $("form:#formModificaSocioMoral #msMoralPartida").val() != ""
			|| $("form:#formModificaSocioMoral #msMoralVolumen").val() != ""
			|| $("form:#formModificaSocioMoral #msMoralFoja").val() != "") {
		$("form:#formModificaSocioMoral #msMoralFolioMercantil").attr(
				"disabled", true);
	} else {
		$("form:#formModificaSocioMoral #msMoralFolioMercantil").attr(
				"disabled", false);
	}
}

function agregarDatoContactoDetalleSocio() {

	var sSource = '/delta-gestionPatronal-web/socios/fb/agregarDatoContactoDetalleSocio';
	var wrapperMovimiento = new Object();
	var oForm = new Object();
	var socio = new Object();
	var tipoSocio = new Object();
	var tipoMedioContacto = new Object();

	if (!validarDatosContacto(
			$("#dgNuevoDatoContactoDetalleSocio #tipoContactoSocio"),
			$("#dgNuevoDatoContactoDetalleSocio #nuevoDatoContactoDetalleSocioDescripcion"),
			$("#divErrorTipoContactoSocio"),
			$("#divErrorNuevoDatoContactoDetalleSocioDescripcion"))) {
		return false;
	}

	// wrapperMovimiento.aoData = aoData;
	oForm.tipoMedioContacto = tipoMedioContacto;
	socio.tipoSocio = tipoSocio;
	wrapperMovimiento.oForm = oForm;
	wrapperMovimiento.socio = socio;

	oForm.desFormaContacto = $(
			"#dgNuevoDatoContactoDetalleSocio #nuevoDatoContactoDetalleSocioDescripcion")
			.val();
	oForm.tipoMedioContacto.idTipoMedioContacto = $(
			"#dgNuevoDatoContactoDetalleSocio #tipoContactoSocio").val();
	oForm.tipoMedioContacto.descripcion = jQuery(
			'#dgNuevoDatoContactoDetalleSocio #tipoContactoSocio option:selected')
			.text();

	if (socioTipoSocioIdTipoPersonaForMediosContactoGeneral == 1) {

		// asignamos valores
		wrapperMovimiento.socio.idPersona = $(
				"#divDetalleEnTramiteSocioFisico #idPersona").val();
		wrapperMovimiento.socio.esNacional = $(
				"#divDetalleEnTramiteSocioFisico #esNacional").val();
		wrapperMovimiento.socio.esDomicilioNacional = $(
				"#divDetalleEnTramiteSocioFisico #esDomicilioNacional").val();

		wrapperMovimiento.socio.primerApellido = $(
				"#divDetalleEnTramiteSocioFisico #detalleEnTramiteSocioFisicoPrimerApellido")
				.val();
		wrapperMovimiento.socio.segundoApellido = $(
				"#divDetalleEnTramiteSocioFisico #detalleEnTramiteSocioFisicoSegundoApellido")
				.val();
		wrapperMovimiento.socio.nombres = $(
				"#divDetalleEnTramiteSocioFisico #detalleEnTramiteSocioFisicoNombres")
				.val();

		wrapperMovimiento.socio.tipoSocio.idTipoPersona = socioTipoSocioIdTipoPersonaForMediosContactoGeneral;

		$.postJSON(sSource, wrapperMovimiento, function(data) {
			dtSocioFisicoDatosContactoDetalle.fnDraw();
			oDialogAgregarDatoContactoDetalleSocio.dialog("close");
		});

	} else if (socioTipoSocioIdTipoPersonaForMediosContactoGeneral == 2) {

		// asignamos valores
		wrapperMovimiento.socio.idPersona = $(
				"#divDetalleEnTramiteSocioMoral #idPersona").val();
		wrapperMovimiento.socio.esNacional = $(
				"#divDetalleEnTramiteSocioMoral #esNacional").val();
		wrapperMovimiento.socio.esDomicilioNacional = $(
				"#divDetalleEnTramiteSocioMoral #esDomicilioNacional").val();

		wrapperMovimiento.socio.nombreRazonSocial = $(
				"#divDetalleEnTramiteSocioMoral #detalleEnTramiteSocioMoralDenomRazonSocial")
				.val();

		wrapperMovimiento.socio.tipoSocio.idTipoPersona = socioTipoSocioIdTipoPersonaForMediosContactoGeneral;

		$.postJSON(sSource, wrapperMovimiento, function(data) {
			dtSocioMoralDatosContactoDetalle.fnDraw();
			oDialogAgregarDatoContactoDetalleSocio.dialog("close");
		});

	} else if (socioTipoSocioIdTipoPersonaForMediosContactoGeneral == 3) {

		// asignamos valores
		wrapperMovimiento.socio.idPersona = $(
				"#divDetalleEnTramiteSocioFideicomiso #idPersona").val();
		wrapperMovimiento.socio.esNacional = $(
				"#divDetalleEnTramiteSocioFideicomiso #esNacional").val();
		wrapperMovimiento.socio.esDomicilioNacional = $(
				"#divDetalleEnTramiteSocioFideicomiso #esDomicilioNacional")
				.val();

		wrapperMovimiento.socio.nombreRazonSocial = $(
				"#divDetalleEnTramiteSocioFideicomiso #detalleEnTramiteSocioFideicomisoDenominacionRazonSocial")
				.val();

		wrapperMovimiento.socio.tipoSocio.idTipoPersona = socioTipoSocioIdTipoPersonaForMediosContactoGeneral;

		$.postJSON(sSource, wrapperMovimiento, function(data) {
			dtSocioFideicomisoDatosContactoDetalle.fnDraw();
			oDialogAgregarDatoContactoDetalleSocio.dialog("close");
		});

	}
}

function modificarDatoContactoDetalleSocio() {
	var sSource = '/delta-gestionPatronal-web/socios/fb/modificarDatoContactoDetalleSocio';

	var wrapperMovimiento = new Object();
	var oForm = new Object();
	var socio = new Object();
	var tipoSocio = new Object();
	var tipoMedioContacto = new Object();

	if (!validarDatosContacto(
			$("#dgModificarDatoContactoDetalleSocio #tipoContactoModificarSocio"),
			$("#dgModificarDatoContactoDetalleSocio #modificarDatoContactoDetalleSocioDescripcion"),
			$("#divErrorTipoContactoModificarSocio"),
			$("#divErrorModificarDatoContactoDetalleSocioDescripcion"))) {
		return false;
	}

	oForm.tipoMedioContacto = tipoMedioContacto;
	socio.tipoSocio = tipoSocio;
	wrapperMovimiento.oForm = oForm;
	wrapperMovimiento.socio = socio;

	oForm.desFormaContacto = $(
			"#dgModificarDatoContactoDetalleSocio #modificarDatoContactoDetalleSocioDescripcion")
			.val();
	oForm.tipoMedioContacto.idTipoMedioContacto = $(
			"#dgModificarDatoContactoDetalleSocio #tipoContactoModificarSocio")
			.val();
	oForm.tipoMedioContacto.descripcion = jQuery(
			'#dgModificarDatoContactoDetalleSocio #tipoContactoModificarSocio option:selected')
			.text();
	oForm.idVista = $(
			"#dgModificarDatoContactoDetalleSocio #idVistaModificarSocio")
			.val();
	// utilizamos errorFormGeneral para mandar id persona
	oForm.errorFormGeneral = $(
			"#dgModificarDatoContactoDetalleSocio #errorFormGeneralModificarSocio")
			.val();

	if (socioTipoSocioIdTipoPersonaForMediosContactoGeneral == 1) {

		// asignamos valores
		wrapperMovimiento.socio.idPersona = $(
				"#divDetalleEnTramiteSocioFisico #idPersona").val();
		wrapperMovimiento.socio.esNacional = $(
				"#divDetalleEnTramiteSocioFisico #esNacional").val();
		wrapperMovimiento.socio.esDomicilioNacional = $(
				"#divDetalleEnTramiteSocioFisico #esDomicilioNacional").val();

		wrapperMovimiento.socio.primerApellido = $(
				"#divDetalleEnTramiteSocioFisico #detalleEnTramiteSocioFisicoPrimerApellido")
				.val();
		wrapperMovimiento.socio.segundoApellido = $(
				"#divDetalleEnTramiteSocioFisico #detalleEnTramiteSocioFisicoSegundoApellido")
				.val();
		wrapperMovimiento.socio.nombres = $(
				"#divDetalleEnTramiteSocioFisico #detalleEnTramiteSocioFisicoNombres")
				.val();

		wrapperMovimiento.socio.tipoSocio.idTipoPersona = socioTipoSocioIdTipoPersonaForMediosContactoGeneral;

		$.postJSON(sSource, wrapperMovimiento, function(data) {
			dtSocioFisicoDatosContactoDetalle.fnDraw();
			oDialogModificarDatoContactoDetalleSocio.dialog("close");
		});

	} else if (socioTipoSocioIdTipoPersonaForMediosContactoGeneral == 2) {

		// asignamos valores
		wrapperMovimiento.socio.idPersona = $(
				"#divDetalleEnTramiteSocioMoral #idPersona").val();
		wrapperMovimiento.socio.esNacional = $(
				"#divDetalleEnTramiteSocioMoral #esNacional").val();
		wrapperMovimiento.socio.esDomicilioNacional = $(
				"#divDetalleEnTramiteSocioMoral #esDomicilioNacional").val();

		wrapperMovimiento.socio.nombreRazonSocial = $(
				"#divDetalleEnTramiteSocioMoral #detalleEnTramiteSocioMoralDenomRazonSocial")
				.val();

		wrapperMovimiento.socio.tipoSocio.idTipoPersona = socioTipoSocioIdTipoPersonaForMediosContactoGeneral;

		$.postJSON(sSource, wrapperMovimiento, function(data) {
			dtSocioMoralDatosContactoDetalle.fnDraw();
			oDialogModificarDatoContactoDetalleSocio.dialog("close");
		});

	} else if (socioTipoSocioIdTipoPersonaForMediosContactoGeneral == 3) {

		// asignamos valores
		wrapperMovimiento.socio.idPersona = $(
				"#divDetalleEnTramiteSocioFideicomiso #idPersona").val();
		wrapperMovimiento.socio.esNacional = $(
				"#divDetalleEnTramiteSocioFideicomiso #esNacional").val();
		wrapperMovimiento.socio.esDomicilioNacional = $(
				"#divDetalleEnTramiteSocioFideicomiso #esDomicilioNacional")
				.val();

		wrapperMovimiento.socio.nombreRazonSocial = $(
				"#divDetalleEnTramiteSocioFideicomiso #detalleEnTramiteSocioFideicomisoDenominacionRazonSocial")
				.val();

		wrapperMovimiento.socio.tipoSocio.idTipoPersona = socioTipoSocioIdTipoPersonaForMediosContactoGeneral;

		$.postJSON(sSource, wrapperMovimiento, function(data) {
			dtSocioFideicomisoDatosContactoDetalle.fnDraw();
			oDialogModificarDatoContactoDetalleSocio.dialog("close");
		});
	}
}

/**
 * esta funcion en realidad no guarda cambios, solo valida los datos de contacto
 * 
 * @returns {Boolean}
 */
function guardarCambiosDetalleEnTramiteSocio(response) {
	// verificamos datos de contacto, al menos debe ser capturado telefono
	var contadorDatosContacto = 0;
	var dtSocioDatosContactoDetalle;
	var result = true;

	if (!response.datosContactoValidos) {
		var oDialogo;
		construirDialogoGenerico(
				"#dialogoMensajes",
				oDialogo,
				"Error",
				"Debe capturar al menos un dato de contacto.\nLos datos requeridos son: Tel\u00E9fono fijo o m\u00F3vil o Correo Electr\u00F3nico.",
				true, undefined, undefined, 150, 500);
		return false;
	}

	if (socioTipoSocioIdTipoPersonaForMediosContactoGeneral == 1) {
		// dtSocioDatosContactoDetalle = dtSocioFisicoDatosContactoDetalle;
		oDialogDetalleEnTramiteSocioFisico.dialog("close");
	} else if (socioTipoSocioIdTipoPersonaForMediosContactoGeneral == 2) {
		// dtSocioDatosContactoDetalle = dtSocioMoralDatosContactoDetalle;
		oDialogDetalleEnTramiteSocioMoral.dialog("close");
	} else if (socioTipoSocioIdTipoPersonaForMediosContactoGeneral == 3) {
		// dtSocioDatosContactoDetalle = dtSocioFideicomisoDatosContactoDetalle;
		oDialogDetalleEnTramiteSocioFideicomiso.dialog("close");
	}
	//	
	// $(dtSocioDatosContactoDetalle.fnSettings().aoData).each(function(){
	// contadorDatosContacto++;
	// }
	// );
	//	
	// if (contadorDatosContacto > 0){
	// var contadorDatosContactoRequeridos = 0;
	//		
	// $(dtSocioDatosContactoDetalle.fnSettings().aoData).each(function(){
	// if ((this._aData.tipoMedioContacto.idTipoMedioContacto == 1 &&
	// this._aData.desFormaContacto != "")
	// || (this._aData.tipoMedioContacto.idTipoMedioContacto == 2 &&
	// this._aData.desFormaContacto != "")
	// || (this._aData.tipoMedioContacto.idTipoMedioContacto == 3 &&
	// this._aData.desFormaContacto != "")){
	// contadorDatosContactoRequeridos++;
	// }
	// });
	//		
	// if (contadorDatosContactoRequeridos == 0){
	// var oDialogo;
	// construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error", "Debe
	// capturar al menos un dato de contacto.\nLos datos requeridos pueden ser
	// Tel\u00E9fono fijo o m\u00F3vil y/o Correo Electr\u00F3nico.", true,
	// undefined, undefined, 150,500);
	// result = false;
	// }
	// } else {
	// var oDialogo;
	// construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error", "Debe
	// capturar al menos un dato de contacto.\nLos datos requeridos pueden ser
	// Tel\u00E9fono fijo o m\u00F3vil y/o Correo Electr\u00F3nico.", true,
	// undefined, undefined, 150, 500);
	// result = false;
	// }

	return result;
}

function eliminarDatoContactoDetalleSocio() {

	var wrapperMovimiento = new Object();
	var oForm = new Object();
	var socio = new Object();
	var tipoSocio = new Object();
	var tipoMedioContacto = new Object();

	oForm.tipoMedioContacto = tipoMedioContacto;
	socio.tipoSocio = tipoSocio;
	wrapperMovimiento.oForm = oForm;
	wrapperMovimiento.socio = socio;

	var sSource = '/delta-gestionPatronal-web/socios/fb/eliminarDatoContactoDetalleSocio';

	if (socioTipoSocioIdTipoPersonaForMediosContactoGeneral == 1) {

		var obRowSelected = fnGetRowSelected(dtSocioFisicoDatosContactoDetalle);

		// asignamos valores
		wrapperMovimiento.socio.idPersona = obRowSelected.errorFormGeneral; // utilizamos
																			// este
																			// atrib.
																			// para
																			// idPersona
		// obtenemos estos valores del div correspondiente (ke chaafa!)
		wrapperMovimiento.socio.esNacional = $(
				"#divDetalleEnTramiteSocioFisico #esNacional").val();
		wrapperMovimiento.socio.esDomicilioNacional = $(
				"#divDetalleEnTramiteSocioFisico #esDomicilioNacional").val();
		wrapperMovimiento.socio.primerApellido = $(
				"#divDetalleEnTramiteSocioFisico #detalleEnTramiteSocioFisicoPrimerApellido")
				.text();
		wrapperMovimiento.socio.segundoApellido = $(
				"#divDetalleEnTramiteSocioFisico #detalleEnTramiteSocioFisicoSegundoApellido")
				.text();
		wrapperMovimiento.socio.nombres = $(
				"#divDetalleEnTramiteSocioFisico #detalleEnTramiteSocioFisicoNombres")
				.text();

		wrapperMovimiento.oForm.idVista = obRowSelected.idVista;
		wrapperMovimiento.socio.tipoSocio.idTipoPersona = socioTipoSocioIdTipoPersonaForMediosContactoGeneral;

		$.postJSON(sSource, wrapperMovimiento, function(data) {
			dtSocioFisicoDatosContactoDetalle.fnDraw();
			oDialogEliminarDatoContactoDetalleSocio.dialog("close");
		});

	} else if (socioTipoSocioIdTipoPersonaForMediosContactoGeneral == 2) {
		var obRowSelected = fnGetRowSelected(dtSocioMoralDatosContactoDetalle);

		// asignamos valores
		wrapperMovimiento.socio.idPersona = obRowSelected.errorFormGeneral; // utilizamos
																			// este
																			// atrib.
																			// para
																			// idPersona
		// obtenemos estos valores del div correspondiente (ke chaafa!)
		wrapperMovimiento.socio.esNacional = $(
				"#divDetalleEnTramiteSocioMoral #esNacional").val();
		wrapperMovimiento.socio.esDomicilioNacional = $(
				"#divDetalleEnTramiteSocioMoral #esDomicilioNacional").val();
		wrapperMovimiento.socio.nombreRazonSocial = $(
				"#divDetalleEnTramiteSocioMoral #detalleEnTramiteSocioMoralDenomRazonSocial")
				.text();

		wrapperMovimiento.oForm.idVista = obRowSelected.idVista;
		wrapperMovimiento.socio.tipoSocio.idTipoPersona = socioTipoSocioIdTipoPersonaForMediosContactoGeneral;

		$.postJSON(sSource, wrapperMovimiento, function(data) {
			dtSocioMoralDatosContactoDetalle.fnDraw();
			oDialogEliminarDatoContactoDetalleSocio.dialog("close");
		});
	} else if (socioTipoSocioIdTipoPersonaForMediosContactoGeneral == 3) {
		var obRowSelected = fnGetRowSelected(dtSocioFideicomisoDatosContactoDetalle);

		// asignamos valores
		wrapperMovimiento.socio.idPersona = obRowSelected.errorFormGeneral; // utilizamos
																			// este
																			// atrib.
																			// para
																			// idPersona
		// obtenemos estos valores del div correspondiente (ke chaafa!)
		wrapperMovimiento.socio.esNacional = $(
				"#divDetalleEnTramiteSocioFideicomiso #esNacional").val();
		wrapperMovimiento.socio.esDomicilioNacional = $(
				"#divDetalleEnTramiteSocioFideicomiso #esDomicilioNacional")
				.val();
		wrapperMovimiento.socio.nombreRazonSocial = $(
				"#divDetalleEnTramiteSocioFideicomiso #detalleEnTramiteSocioFideicomisoDenominacionRazonSocial")
				.text();

		wrapperMovimiento.oForm.idVista = obRowSelected.idVista;
		wrapperMovimiento.socio.tipoSocio.idTipoPersona = socioTipoSocioIdTipoPersonaForMediosContactoGeneral;

		$.postJSON(sSource, wrapperMovimiento, function(data) {
			dtSocioFideicomisoDatosContactoDetalle.fnDraw();
			oDialogEliminarDatoContactoDetalleSocio.dialog("close");
		});
	}

}

function validaCambiosDetalleEnTramiteSocio() {
	validaMediosContactoRequeridosDeSocio(guardarCambiosDetalleEnTramiteSocio);
}

function validaCambiosDetalleEnTramiteSocioMoral() {
	validaMediosContactoRequeridosDeSocioMoral(guardarCambiosDetalleEnTramiteSocio);
}

function validaCambiosDetalleEnTramiteSocioFideicomiso() {
	validaMediosContactoRequeridosDeSocioFideicomiso(guardarCambiosDetalleEnTramiteSocio);
}

function validaMediosContactoRequeridosDeSocio(callback) {
	var socio = new Object();
	var sSource = "/socios/validaMediosContacto";
	socio.idPersona = $("#divDetalleEnTramiteSocioFisico #idPersona").val();
	sendToServer(sSource, socio, callback, false);
}

function validaMediosContactoRequeridosDeSocioMoral(callback) {
	var socio = new Object();
	var sSource = "/socios/validaMediosContacto";
	socio.idPersona = $("#divDetalleEnTramiteSocioMoral #idPersona").val();
	socio.esNacional=$("#divDetalleEnTramiteSocioMoral #esNacional").val();
	socio.esDomicilioNacional=$("#divDetalleEnTramiteSocioMoral #esDomicilioNacional").val();
	sendToServer(sSource, socio, callback, false);
}

function validaMediosContactoRequeridosDeSocioFideicomiso(callback) {
	var socio = new Object();
	var sSource = "/socios/validaMediosContacto";
	socio.idPersona = $("#divDetalleEnTramiteSocioFideicomiso #idPersona")
			.val();
	sendToServer(sSource, socio, callback, false);
}

function cancelarEdicionMediosContactoSocioEnDetalle() {

	var socio = new Object();
	var tipoSocio = new Object();

	socio.tipoSocio = tipoSocio;

	var sSource = '/socios/fb/cancelarEdicionMediosContactoSocioEnDetalle';

	if (socioTipoSocioIdTipoPersonaForMediosContactoGeneral == 1) {

		// asignamos valores
		socio.idPersona = $("#divDetalleEnTramiteSocioFisico #idPersona").val();
		socio.esNacional = $("#divDetalleEnTramiteSocioFisico #esNacional")
				.val();
		socio.esDomicilioNacional = $(
				"#divDetalleEnTramiteSocioFisico #esDomicilioNacional").val();

		socio.primerApellido = $(
				"#divDetalleEnTramiteSocioFisico #detalleEnTramiteSocioFisicoPrimerApellido")
				.val();
		socio.segundoApellido = $(
				"#divDetalleEnTramiteSocioFisico #detalleEnTramiteSocioFisicoSegundoApellido")
				.val();
		socio.nombres = $(
				"#divDetalleEnTramiteSocioFisico #detalleEnTramiteSocioFisicoNombres")
				.val();

		socio.tipoSocio.idTipoPersona = socioTipoSocioIdTipoPersonaForMediosContactoGeneral;

		sendToServer(sSource, socio,
				cancelarEdicionMediosContactoSocioEnDetalleCallback, false);

	} else if (socioTipoSocioIdTipoPersonaForMediosContactoGeneral == 2) {

		// asignamos valores
		socio.idPersona = $("#divDetalleEnTramiteSocioMoral #idPersona").val();
		socio.esNacional = $("#divDetalleEnTramiteSocioMoral #esNacional")
				.val();
		socio.esDomicilioNacional = $(
				"#divDetalleEnTramiteSocioMoral #esDomicilioNacional").val();

		socio.nombreRazonSocial = $(
				"#divDetalleEnTramiteSocioMoral #detalleEnTramiteSocioMoralDenomRazonSocial")
				.val();

		socio.tipoSocio.idTipoPersona = socioTipoSocioIdTipoPersonaForMediosContactoGeneral;

		sendToServer(sSource, socio,
				cancelarEdicionMediosContactoSocioEnDetalleCallback, false);

	} else if (socioTipoSocioIdTipoPersonaForMediosContactoGeneral == 3) {

		// asignamos valores
		socio.idPersona = $("#divDetalleEnTramiteSocioFideicomiso #idPersona")
				.val();
		socio.esNacional = $("#divDetalleEnTramiteSocioFideicomiso #esNacional")
				.val();
		socio.esDomicilioNacional = $(
				"#divDetalleEnTramiteSocioFideicomiso #esDomicilioNacional")
				.val();

		socio.nombreRazonSocial = $(
				"#divDetalleEnTramiteSocioFideicomiso #detalleEnTramiteSocioFideicomisoDenominacionRazonSocial")
				.val();

		socio.tipoSocio.idTipoPersona = socioTipoSocioIdTipoPersonaForMediosContactoGeneral;

		sendToServer(sSource, socio,
				cancelarEdicionMediosContactoSocioEnDetalleCallback, false);

	}
}

function cancelarEdicionMediosContactoSocioEnDetalleCallback(response) {
	// haber que chingados hacemos con el response, pinche madre!!!
	// alert("cancelarEdicionMediosContactoSocioEnDetalleCallback's response: "
	// + response);

	if (socioTipoSocioIdTipoPersonaForMediosContactoGeneral == 1) {
		dtSocioFisicoDatosContactoDetalle.fnDraw();
	} else if (socioTipoSocioIdTipoPersonaForMediosContactoGeneral == 2) {
		dtSocioMoralDatosContactoDetalle.fnDraw();
	} else if (socioTipoSocioIdTipoPersonaForMediosContactoGeneral == 3) {
		dtSocioFideicomisoDatosContactoDetalle.fnDraw();
	}
}
