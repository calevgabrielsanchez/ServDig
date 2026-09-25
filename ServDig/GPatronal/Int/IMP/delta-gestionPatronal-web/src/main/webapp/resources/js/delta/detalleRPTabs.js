var dialogoListaTramitesClasificacion;
var arrayRepresentantes = new Array();
var arrayDatosSocio = new Array();
var index = -1;
var indexSocio = -1;
var indexRepresentante = -1;
var oDialogoDetalleRegistroPatronal;
var patronSeleccionado = -1;
var listaPatrones = new Array();
var oTableTramites;
var idTramites = "#tramites";
var indicadorTramiteClasifExistente;
var dialogoError;
var idDialogoError = "#dgErrorSinSeleccion";
var idDialogoTramiteExistente = "#tramitesClasificacionExistentes";
var dialogoNoProcedeTramite;
var idDialogoNoProcedeTramite = "#dgDialogoNoProcedeTramite";
var idFiltroDef = "#gridSolicitudes_filter";
var gridRegistrosPatronales;
var dialogoTramiteDatosFiscalesPrecargados;
var dialogoSolicitarAsignacion;
var dialogoSolicitudRestringida;
var listaTramitesClasificacion = "#listaTramitesClasificacion";
var existeTramiteCentroTrabajoEnCurso;
var columnasSolicitud = [ {
	mDataProp : "solicitudId",
	bVisible : false
}, {
	mDataProp : "tipoSolicitud.idTipoSolicitud",
	bVisible : false
}, {
	mDataProp : "tramites",
	bVisible : false
}, {
	mDataProp : "noFolioSolicitud",
	sTitle : "Folio"
}, {
	mDataProp : "fechaSolicitudParse",
	sTitle : "Fecha de Creaci\u00F3n"
}, {
	mDataProp : "tipoSolicitud.descripcion",
	sTitle : "Tipo"
}, {
	sTitle : "Propietario (RP/RFC)",
	"fnRender" : renderOwner
}, {
	sTitle : "Informaci\u00F3n a Modificar",
	"fnRender" : renderTramites
}, {
	mDataProp : "estadoSolicitud.descripcion",
	sTitle : "Estado"
} ];

MedioContacto.prototype.extendValidation = function () {
    var mc = this;

    var _selectTipoFn = function() { return mc.jqSelectTipo; };
    var fnValidarDatos = mc.validarDatos;
    var alterFnValidarDatos = function(arg1, arg2) {
        this.validarDatos = fnValidarDatos;
        var _option = $([_selectTipoFn(), ' > ', 'option:selected'].join(''));
        if (/correo e|facebook|twitter/i.test(_option.text())) {
            var _tmptxt = $(mc.jqTxtFldDesc).val().replace(/^\s+|\s+$/g, '');
            $(mc.jqTxtFldDesc).val(_tmptxt);
            arg2 = _tmptxt
        }
        var retval = this.validarDatos(arg1, arg2);
        this.validarDatos = alterFnValidarDatos;
        return retval;
    };

    $.extend(mc, {validarDatos: alterFnValidarDatos});
}

var columnasRegistroPatronal = [
		{
			mDataProp : "cveIdSujetoObligado",
			bVisible : false
		}, {
					// mDataProp : "numeroRegistroPatronal",
					sTitle : "Registro Patronal",
					fnRender : function(oObj) {
						var row = oObj.aData;
						return row.numeroRegistroPatronal + row.modalidad.numModalidad
								+ row.digVerificador;
					}
		},{
			// mDataProp : "numeroRegistroPatronal",
			sTitle : "Subdelegaci\u00F3n",
			fnRender : function(oObj) {
							var row = oObj.aData;
							if(row.subdelegacion!=undefined){
								return row.subdelegacion.descripcion;
							}else{
								return "";
							}
						}
		}, {
			sTitle : "",
			fnRender : function(oObj) {
				patronSeleccionado = oObj.aData.cveIdSujetoObligado;
				listaPatrones[patronSeleccionado] = oObj.aData;
				return mostrarDetalleRegistro(patronSeleccionado);
			}
		} ];

var columnasSocio = [ {
	"mDataProp" : "idPersona",
	"bVisible" : false
}, {
	"mDataProp" : "idSocio",
	"bVisible" : false
}, {
	"sTitle" : "Nombre / Denominaci\u00F3n o Raz\u00F3n Social",
	"mDataProp" : fnRenderNombreRazonSocial
}, {
	"sTitle" : "RFC",
	"mDataProp" : "rfc"
}, {
	"sTitle" : "CURP",
	"mDataProp" : fnRenderCurp
}, {
	"mDataProp" : "tipoSocio.idTipoPersona",
	"bVisible" : false
}, {
	"sTitle" : "Tipo de Socio",
	"mDataProp" : "tipoSocio.descripcion"
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
		indexSocio = oObj.aData.idPersona;
		arrayDatosSocio[indexSocio] = oObj.aData;
		return construyeLigaSocio(indexSocio);
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

function fnRenderCurp(oObj){
	if(oObj.curp!=undefined)
		return oObj.curp;
	
	return "";
}

function renderOwner(oObj) {
	if (oObj.aData.sujetoObligado != null
			&& oObj.aData.sujetoObligado != undefined) {
		if (oObj.aData.sujetoObligado.numeroRegistroPatronal != undefined)
			return oObj.aData.sujetoObligado.numeroRegistroPatronal
					+ oObj.aData.sujetoObligado.modalidad.numModalidad
					+ oObj.aData.sujetoObligado.digVerificador;
		else if (oObj.aData.sujetoObligado.fisica != undefined)
			return oObj.aData.sujetoObligado.fisica.rfc;
		else if (oObj.aData.sujetoObligado.moral != undefined)
			return oObj.aData.sujetoObligado.moral.rfc;
	} else {
		return "";
	}
}

function renderTramites(oObj) {
	var arrayTramites = oObj.aData.tramites;
	var maxValues = oObj.aData.tramites.length;
	var descripcionTramites = '';
	var i = 0;
	for (i = 0; i < maxValues; i++) {
		descripcionTramites += arrayTramites[i].tipoTramite.descripcion
				+ ".<br>";
	}
	return descripcionTramites;
}

function renderTramitePorDefecto(oObj) {
	var arrayTramites = oObj.aData.tramites;
	var maxValues = oObj.aData.tramites.length;
	var idTramitePorDefecto = 0;
	var i = 0;
	for (i = 0; i < maxValues; i++) {
		idTramitePorDefecto = arrayTramites[i].tipoTramite.idTipoTramite;
	}
	return idTramitePorDefecto;
}

function rederFecha(registro) {
	var sFecha = new String(registro.aData.fechaSolicitud);
	var valores = sFecha.split('T');
	var horaCompleta = valores[1].split('.');
	var horaCorta = horaCompleta[0];
	var valores = valores[0].split('-');
	return valores[2] + "/" + valores[1] + "/" + valores[0] + " " + horaCorta;
}

var columnasRepresentante = [ {
	"mDataProp" : "cveIdPersona",
	"bVisible" : false
}, {
	"mDataProp" : "personaFisica.idPersona",
	"bVisible" : false
}, {
	"mDataProp" : "tipoPersonaRepresentada.idTipoPersona",
	"bVisible" : false
}, {
	"mDataProp" : "cveIdRepresentanteLegal",
	"bVisible" : false
}, {
	"sTitle" : "Nombre",
	"mDataProp" : renderNombre
}, {
	"sTitle" : "RFC",
	"mDataProp" : "personaFisica.rfc"
}, {
	"sTitle" : "CURP",
	"mDataProp" : "personaFisica.curp",
	"fnRender" : function(oObj) {
					if(oObj.aData.personaFisica.curp!=undefined){
						return oObj.aData.personaFisica.curp;
					}else{
						return "";
					}
				}
}, {
	"sTitle" : "Actos de Administraci&oacute;n o dominio",
	"mDataProp" : "indActAdmonDominio",
	"fnRender" : function(oObj) {
		return parseIndicador(oObj.aData.indActAdmonDominio);
	}
}, {
	"sTitle" : "",
	"fnRender" : function(oObj) {
		indexRepresentante = oObj.aData.personaFisica.idPersona;
		arrayRepresentantes[indexRepresentante] = oObj.aData;
		return construyeLiga(indexRepresentante);
	}
} ];

$(document).ready(
		function() {

			$("#gridRegistrosPatronales tbody").click(function(event) {
				$("#registrosPatronalesForm #numeroRegistroPatronal").val("")
			});

			construirGridRepresentateLegalActual()
			inicializarGridDeRegistrosPatronales();
			inicializarGridDeSolicitudesEnProceso();
			inicializaFiltroRegistrosPatronales();
			mc = new MedioContacto("mediosContactoContenedor", 1,
					tpPropietario, idPropietario, undefined, idSujetoObligado,false);
			mc.init();
            mc.extendValidation();
			construirGridSociosActuales();

			if (tipoPersonaFiscal == 'FISICA')
				$('tbSocio').hide();

			if (!isOperadorIMSS && existeSolicitudActiva) {
				$("#btnGenerarSolicitud").hide();
			}
			if (isOperadorIMSS || isRL) {
				$("#btnRegresarConsultaRFC").show();
			} else {
				$("#btnRegresarConsultaRFC").hide();
			}

			$(idFiltroDef).dialog({
				autoOpen : false
			});

			dialogoError = $(idDialogoError).dialog({
				autoOpen : false,
				resizable : false,
				height : 175,
				width : 300,
				modal : true,
				buttons : {
					'Aceptar' : function() {
						$(this).dialog("close");
					}
				}
			});

			dialogoNoProcedeTramite = $(idDialogoNoProcedeTramite).dialog({
				autoOpen : false,
				resizable : false,
				height : 300,
				width : 300,
				modal : true,
				buttons : {
					'Aceptar' : function() {
						$(this).dialog("close");
					}
				}
			});

			dialogoTramites = $(idTramites).dialog({
				autoOpen : false,
				resizable : false,
				height : 550,
				width : 400,
				modal : true,
				buttons : {
					'Continuar' : validaSeleccion
				}
			});

			dialogoTramiteClasifExistente = $(idDialogoTramiteExistente)
					.dialog({
						autoOpen : false,
						resizable : false,
						height : 175,
						width : 400,
						modal : true,
						buttons : {
							'Aceptar' : function() {
								$(this).dialog("close");
							}
						}
					});

			dialogoTramiteDatosFiscalesPrecargados = $(
					'#dialogoMensajesTramitePrecargado').dialog({
				autoOpen : false,
				resizable : false,
				height : 175,
				width : 300,
				modal : true,
				buttons : {
					'Aceptar' : function() {
						$(this).dialog("close");
					}
				}
			});

			dialogoListaTramitesClasificacion = $(listaTramitesClasificacion)
					.dialog({
						autoOpen : false,
						resizable : false,
						height : 550,
						width : 400,
						modal : true,
						buttons : {
							'Continuar' : validaSeleccionDeTramiteClasificacion
						}
					});

			$("#selectable").selectable({
				selected : function(event, ui) {
					$(ui.selected).siblings().removeClass("ui-selected");
				},
				stop : function() {
					$(".ui-selected", this).each(function() {
						index = $("#selectable li").index(this);
					});
				}
			});
			
			setUpAccordionComponent();
		});

function construirGridRepresentateLegalActual() {
	dtRepresentanteLegal = $('#tbRepresentantesLegales')
			.dataTable(
					{
						"bJQueryUI" : false,
						"bPaginate" : true,
						"bLengthChange" : false,
						"iDisplayLength" : 5,
						"bFilter" : false,
						"bSort" : false,
						"bInfo" : false,
						"bAutoWidth" : false,
						"bServerSide" : true,
						"aoColumns" : columnasRepresentante,
						"sPaginationType" : "full_numbers",
						"bProcessing" : true,
						"sAjaxSource" : '/delta-gestionPatronal-web/representanteLegal/fb/paginar',
						"fnServerData" : enviarParametrosRepresentante
					});
}

function inicializarGridDeSolicitudesEnProceso() {

	oTableTramites = $('#gridSolicitudesProceso').dataTable({
		"bJQueryUI" : false,
		"bPaginate" : true,
		"bLengthChange" : false,
		"iDisplayLength" : 10,
		"bServerSide" : false,
		"bProcessing" : true,
		"sPaginationType" : "full_numbers",
		"bFilter" : true,
		"bSort" : true,
		"bInfo" : false,
		"bAutoWidth" : true,
		"aoColumns" : columnasSolicitud,
		"sAjaxSource" : context_path + "/afiliacion/cargaSolicitudesEnProceso",
		"fnServerData" : cargarGridSolicitudesProceso
	});

	inicializaEstiloGrid($("#gridSolicitudesProceso tbody"), oTableTramites);

	$("#txtBuscar").keyup(function() {
		oTableTramites.fnFilter($("#txtBuscar").val());
	});
}

function inicializarGridDeRegistrosPatronales() {
	gridRegistrosPatronales = $('#gridRegistrosPatronales').dataTable({
		"bJQueryUI" : false,
		"bPaginate" : true,
		"bLengthChange" : false,
		"bServerSide" : false,
		"iDisplayLength" : 5,
		"sPaginationType" : "full_numbers",
		"bFilter" : true,
		"bSort" : false,
		"bInfo" : false,
		"bAutoWidth" : true,
		"aoColumns" : columnasRegistroPatronal,
		"sAjaxSource" : context_path + "/afiliacion/cargaRegistrosPatronales",
		"fnServerData" : cargarGridRegistrosPatronales
	});

	inicializaEstiloGrid($("#gridRegistrosPatronales tbody"),
			gridRegistrosPatronales);

}

function abrirTramites() {
	validaTramiteExiste();
	if (indicadorTramiteClasifExistente == 'true'
			|| indicadorTramiteClasifExistente == true) {
		dialogoTramiteClasifExistente.dialog('open');
	} else {
		dialogoTramites.dialog('open');
	}
}

function validaSeleccion() {
	if (index != -1) {
		$("#idSolicitud").val("");
		$("#idTramite").val($("#selectable li")[index].id);
		dialogoTramites.dialog('close');
		navegar(context_path + '/clasificacion/');

	}
}

function navegar(url, tipoTramiteCodigo) {

	var proceder = true;

	if (tipoTramiteCodigo != undefined) {
		$(oTableTramites.fnSettings().aoData)
				.each(
						function() {
							// alert("codigo: " + tipoTramiteCodigo);
							if (this._aData.tipoTramite.idTipoTramite == tipoTramiteCodigo) {
								// alert("id de tramite para actualizar rep
								// legal: " +
								// this._aData.tipoTramite.idTipoTramite + ", no
								// se puede proceder ya que hay un tamite en
								// curso");
								$('#dgDialogoNoProcedeTramite').attr('tittle',
										this._aData.tipoTramite.descripcion);
								proceder = false;
								return;
							}
						});
	}

	if (proceder) {

		document.getElementById('patronForm').action = url;
		document.getElementById('patronForm').submit();
	} else {
		dialogoNoProcedeTramite.dialog('open');
	}

}

function cargarGridSolicitudesProceso(sSource, aoData, fnCallback) {
	var wrapper = new Object();
	wrapper.oForm = new Object();
	wrapper.oForm.sujetoObligado = new Object();
	wrapper.oForm.sujetoObligado.cveIdSujetoObligado = $("#cveIdSujetoObligado")
			.val();
	wrapper.oForm.sujetoObligado.tipoPersonaFiscal = $("#tipoPersonaFiscal")
			.val();
	if (tipoPersonaFiscal == "FISICA") {
		wrapper.oForm.sujetoObligado.fisica = new Object();
		wrapper.oForm.sujetoObligado.fisica.nombreComercial = $(
				"#fisica\\.nombreComercial").val();
		wrapper.oForm.sujetoObligado.fisica.idPersona = $("#fisica\\.idPersona")
				.val();
		wrapper.oForm.sujetoObligado.fisica.rfc = $("#fisica\\.rfc").val();
	} else {
		wrapper.oForm.sujetoObligado.moral = new Object();
		wrapper.oForm.sujetoObligado.moral.nombreComercial = $(
				"#moral\\.nombreComercial").val();
		wrapper.oForm.sujetoObligado.moral.idPersona = $("#moral\\.idPersona")
				.val();
		wrapper.oForm.sujetoObligado.moral.rfc = $("#moral\\.rfc").val();
	}

	wrapper.aoData = aoData;
	$.postJSON(sSource, wrapper, function(data) {
		gridCustomCallback(data, fnCallback);
	});
}

function gridCustomCallback(data, fnCallback) {
	fnCallback(data);
	$("#oTableTramites");
}

function cargarGridRegistrosPatronales(sSource, aoData, fnCallback) {
	var wrapper = new Object();
	wrapper.oForm = new Object();
	wrapper.oForm = new Object();
	wrapper.oForm.cveIdSujetoObligado = $("#cveIdSujetoObligado").val();
	wrapper.oForm.tipoPersonaFiscal = $("#tipoPersonaFiscal").val();
	if (tipoPersonaFiscal == "FISICA") {
		wrapper.oForm.fisica = new Object();
		wrapper.oForm.fisica.nombreComercial = $("#fisica\\.nombreComercial")
				.val();
		wrapper.oForm.fisica.idPersona = $("#fisica\\.idPersona").val();
		wrapper.oForm.fisica.rfc = $("#fisica\\.rfc").val();
	} else {
		wrapper.oForm.moral = new Object();
		wrapper.oForm.moral.nombreComercial = $("#moral\\.nombreComercial")
				.val();
		wrapper.oForm.moral.idPersona = $("#moral\\.idPersona").val();
		wrapper.oForm.moral.rfc = $("#moral\\.rfc").val();
	}

	wrapper.aoData = aoData;
	$.postJSON(sSource, wrapper, function(data) {
		fnCallback(data);
	});
}

function validaSeleccionSol() {
	$("#idSolicitud").val("");
	$("#idTramite").val("");

	var obRowSelected = fnGetRowSelected(oTableTramites);
	if (obRowSelected == undefined) {
		dialogoError.dialog('open');
	} else {
		var url = "/afiliacion/evaluarSeleccionDeSolicitud";
		// var solicitudId=obRowSelected.solicitudId;
		var solicitudObj = new Object();
		solicitudObj.solicitudId = obRowSelected.solicitudId;
		solicitudObj.sujetoObligado = new Object();
		// solicitudObj.sujetoObligado.tipoPersonaFiscal = new Object();
		solicitudObj.sujetoObligado.tipoPersonaFiscal = $("#tipoPersonaFiscal")
				.val();
		if (obRowSelected.sujetoObligado != null
				|| obRowSelected.sujetoObligado != undefined){
			solicitudObj.sujetoObligado.numeroRegistroPatronal = 
				obRowSelected.sujetoObligado.numeroRegistroPatronal;
			
			if(obRowSelected.sujetoObligado.modalidad!=undefined && 
					obRowSelected.sujetoObligado.modalidad!=null && 
					obRowSelected.sujetoObligado.modalidad.numModalidad!= null){
				solicitudObj.sujetoObligado.numeroRegistroPatronal+=obRowSelected.sujetoObligado.modalidad.numModalidad;
				if(obRowSelected.sujetoObligado.digVerificador!=undefined && obRowSelected.sujetoObligado.digVerificador!=null)
					solicitudObj.sujetoObligado.numeroRegistroPatronal+=obRowSelected.sujetoObligado.digVerificador;
			}
		}
		
		// sendToServer(url, solicitudId, callbackEjecutarOperacionEvaluada,
		// false);
		sendToServer(url, solicitudObj, callbackEjecutarOperacionEvaluada,
				false);
	}
}

function callbackEjecutarOperacionEvaluada(response) {
	var obRowSelected = fnGetRowSelected(oTableTramites);
	$("#idSolicitud").val(obRowSelected.solicitudId);
	$("#idTramite").val(obRowSelected.tramites[0].tipoTramite.idTipoTramite);
	var identificadorSolicitud = obRowSelected.solicitudId;
	var identificadorTramite = obRowSelected.tramites[0].tipoTramite.idTipoTramite;

	switch (response.tipoOperacion) {
	case MOSTRAR_DETALLE_MODIFICACION_CENTRO_TRABAJO:
		document.getElementById('detalleRPForm').method = 'POST';
		document.getElementById('detalleRPForm').action = context_path
				+ '/clasificacion?idTramite=' + identificadorTramite;
		document.getElementById('detalleRPForm').submit();
//		document.getElementById('numeroRegistroPatronal').value = obRowSelected.numeroRegistroPatronal;
//		$.blockUI();
//		navegarDetalleCT(identificadorSolicitud);
		break;
	case MOSTRAR_DETALLE_MODIFICACION_SRT:
		document.getElementById('detalleRPForm').method = 'POST';
		document.getElementById('detalleRPForm').action = context_path
				+ '/clasificacion?idTramite=' + identificadorTramite;
		document.getElementById('detalleRPForm').submit();
		break;
	case MOSTRAR_ACUSE_DE_MODIFICACION_SRT:
		mostrarMensajeRPenProceso();
		break;
	case MOSTRAR_AVISO_DE_MODIFICACION_SRT:
		document.getElementById('detalleRPForm').method = 'POST';
		document.getElementById('detalleRPForm').target = '_blank';
		document.getElementById('detalleRPForm').action = context_path
				+ '/clasificacion/presentarAcuse';
		document.getElementById('detalleRPForm').submit();
		break;
	case MOSTRAR_ACUSE_DATOS_PATRONALES:
		document.getElementById('detalleRPForm').method = 'POST';
		document.getElementById('detalleRPForm').target = '_blank';
		document.getElementById('detalleRPForm').action = context_path
				+ '/clasificacion/presentarAcusePatronal?idSolicitud='
				+ identificadorSolicitud;
		document.getElementById('detalleRPForm').submit();
		break;
	
	case MOSTRAR_ACUSE_CENTRO_TRABAJO:
		document.getElementById('detalleRPForm').method = 'POST';
		document.getElementById('detalleRPForm').target = '_blank';
		document.getElementById('detalleRPForm').action = context_path
				+ '/clasificacion/presentarAcusePatronal?idSolicitud='
				+ identificadorSolicitud;
		document.getElementById('detalleRPForm').submit();
		break;
	
		
	case MOSTRAR_DETALLE_MODIFICACION_DATOS_PATRONALES:
		document.getElementById('solicitudForm').method = 'POST';
		document.getElementById('solicitudForm').action = context_path
				+ '/afiliacion/mostrarTramites?idSolicitud='
				+ identificadorSolicitud;
		document.getElementById('solicitudForm').submit();

		// dialogoTramiteDatosFiscalesPrecargados.dialog('open');
		break;
	case SOLICITAR_ASIGNACION:
		construirDialogoGenerico(
				"#dialogoMensajes",
				dialogoSolicitarAsignacion,
				"Aviso",
				"Por favor asigne la solicitud para su atenci\u00F3n en backoffice o ventanilla",
				false, callbackCloseDialogoAsignacion);
		break;
	case ACCESO_RESTRINGIDO:
		construirDialogoGenerico(
				"#dialogoMensajes",
				dialogoSolicitudRestringida,
				"Aviso",
				"\u00A1Permiso denegado!.<br>La solicitud seleccionada no pertenece a su subdelegaci\u00F3n",
				false, callbackCloseDialogoAsignacion, undefined, 200, 500);
		break;
	default:
		document.getElementById('detalleRPForm').action = context_path
				+ '/clasificacion?idTramite=' + identificadorTramite;
		break;
	}

}

function callbackCloseDialogoAsignacion() {
}

function inicializaSujetoTramiteGeneral() {
	if (sujetoObigadoTramite == undefined || sujetoObigadoTramite == null) {
		sujetoObigadoTramite = new Object();
	}
	sujetoObigadoTramite.cveIdSujetoObligado = $("#cveIdSujetoObligado").val();
	sujetoObigadoTramite.tipoPersonaFiscal = $("#tipoPersonaFiscal").val();
	if (tipoPersonaFiscal == "FISICA") {
		sujetoObigadoTramite.fisica = new Object();
		sujetoObigadoTramite.fisica.idPersona = $("#fisica\\.idPersona").val();
		sujetoObigadoTramite.fisica.rfc = $("#fisica\\.rfc").val();
	} else {
		sujetoObigadoTramite.moral = new Object();
		sujetoObigadoTramite.moral.idPersona = $("#moral\\.idPersona").val();
		sujetoObigadoTramite.moral.rfc = $("#moral\\.rfc").val();
	}
}

function validaTramiteExiste() {
	var sSource = context_path
			+ '/sujetoObligado/validaTramiteClasificacionExistente';

	// TODO
	var obRowSelected = fnGetRowSelected(gridRegistrosPatronales);
	if (obRowSelected == undefined || obRowSelected == null) {
		dialogoError.dialog('open');
		return false;
	} else {
		var idPatronSeleccionado = obRowSelected.cveIdSujetoObligado;
		var request = $.ajax({
			url : sSource,
			async : false,
			type : "POST",
			data : idPatronSeleccionado ? JSON.stringify(idPatronSeleccionado)
					: null,
			dataType : "json",
			contentType : "application/json; charset=utf-8"
		});
		request
				.done(function(response) {
					if (response.errors != undefined) {
						alert(response.errors);
					} else {
						indicadorTramiteClasifExistente = response.existeTramiteClasificacion;
					}
				});
		return true;
	}
}

function enviarSolicitud(funcionEjecturar) {
	$("#textoConfirmacion")
			.html(
					"Una vez enviada la solicitud al Instituto usted no podr\u00E1 realizar modificaciones.<br>"
							+ "Favor de confirmar la Finalizaci\u00F3n de la Captura/Edici\u00F3n de la Solicitud.<br> ");
	var dialogo = $("#dialogoConfirmacion").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : 150,
		width : 400,
		title : "Confirmaci&oacute;n",
		buttons : {
			"Aceptar" : function() {
				funcionEjecturar();
				$(this).dialog("close");
			},
			"Cancelar" : function() {
				$(this).dialog("close");
			}
		}
	});
	dialogo.dialog('open');

}

function validarEnvioSolicitud() {
	inicializaSujetoTramiteGeneral();
	sendToServer('/afiliacion/validarSolicitudCompletes', sujetoObigadoTramite,
			callbackvalidarEnvioSolicitud, false);
}

function callbackvalidarEnvioSolicitud(response) {
	$.unblockUI();
	if (response.mensajeError == "valido") {
		ejecutarEnvioDeSolicitud();
	} else {
		procesarRespuestaServer(response,
				callbackConfirmarvalidarEnvioSolicitud, 500, 600);
	}

}

function callbackConfirmarvalidarEnvioSolicitud(response) {

}

function ejecutarEnvioDeSolicitud() {
	// Se muestra la ventana para que el usuario pueda firmar digitalmente
	dialogFirma = $("#dialogoConcluirSolicitudFirma").dialog({
		autoOpen : false,
		resizable : false,
		height : 550,
		width : 400,
		modal : true
	});
	$("#dialogoConcluirSolicitudFirma").css("display", "block");
	dialogFirma.dialog('open');
}

function ejecutarEnvioDeSolicitudSinFirma() {
	dialogFirma.dialog('close');
	$.blockUI();
	inicializaSujetoTramiteGeneral();
	sendToServer('/afiliacion/enviarSolicitud', sujetoObigadoTramite,
			callbackEnvioSolicitud, false);

}

function callbackEnvioSolicitud(response) {
	$.unblockUI();
	var respuesta = procesarRespuestaServer(response,
			callbackConfirmarEnvioSolicitud);

	if (respuesta) {
		document.getElementById('formReporteModificacionPatronal').method = 'POST';
		document.getElementById('formReporteModificacionPatronal').target = '_blank';
		document.getElementById('formReporteModificacionPatronal').action = context_path
				+ '/afiliacion/procesarInformacionAcuseAfiliacion?origen=ACUSE';
		document.getElementById('formReporteModificacionPatronal').submit();
	}
}

function callbackConfirmarEnvioSolicitud(response) {
	if (!error) {
		$("#btnEnviarSolicitud").hide();
		if (isOperadorIMSS) {
			$("#btnConcluirSolicitud").show();
		}

		var rfcParam;
		if (tipoPersonaFiscal == "FISICA") {
			rfcParam = $("#fisica\\.rfc").val();
		} else {
			rfcParam = $("#moral\\.rfc").val();
		}

		navegarTo('/sujetoObligado/recargarTramites?rfcParam=' + rfcParam,
				'formSupport');
	}
}

function concluirSolicitudDatosFiscales() {
	$.blockUI();
	inicializaSujetoTramiteGeneral();
	sendToServer('/afiliacion/concluirSolicitud?idSolicitud='
			+ idSolicitudActiva, sujetoObigadoTramite,
			callbackConcluirSolicitudDatosFiscales, true);
}

function callbackConcluirSolicitudDatosFiscales(response) {
	$.unblockUI();
	var respuestaProcesar = procesarRespuestaServer(response,
			callbackConfirmarConclusionSolicitud, 150, 450);
	if (respuestaProcesar) {
		document.getElementById('formReporteModificacionPatronal').method = 'POST';
		document.getElementById('formReporteModificacionPatronal').target = '_blank';
		document.getElementById('formReporteModificacionPatronal').action = context_path
				+ '/afiliacion/procesarInformacionAcuseAfiliacion?origen=COMPROBANTE';
		document.getElementById('formReporteModificacionPatronal').submit();
	}
}

function callbackConfirmarConclusionSolicitud(response) {
	if (!error) {
		$("#btnConcluirSolicitud").hide();
		var rfcParam;
		if (tipoPersonaFiscal == "FISICA") {
			rfcParam = $("#fisica\\.rfc").val();
		} else {
			rfcParam = $("#moral\\.rfc").val();
		}

		navegarTo('/sujetoObligado/recargarTramites?rfc=' + rfcParam,
				'formSupport');
	}
}

function fnConcluirConFirma() {
	$("#formConcluirConFirma #idSujetoObligado").val(idSujetoObligado);
	$("#formConcluirConFirma #numRegPatronal").val(
			$('#numeroRegistroPatronal').val());
	$("#formConcluirConFirma #idSolicitudActiva").val(idSolicitudActiva);
	document.forms.formConcluirConFirma.action = contextPath
			+ '../afiliacion/iniciaProcesoFirmaDigital';
	document.forms.formConcluirConFirma.submit();
}// btnConcluirConFirma

function presentarSolicitudConfirmacionCancelacion() {
	var dialogoSolicitarConfirmacion;
	construirDialogoGenericoDeConfirmacion("#dialogoMensajes",
			dialogoSolicitarConfirmacion, "Confirmaci\u00F3n",
			"\u00BFEst\u00E1 seguro que desea cancelar la solicitud?", false,
			cancelarSolicitudDatosFiscales);
}

function cancelarSolicitudDatosFiscales(response) {
	$.blockUI();
	inicializaSujetoTramiteGeneral();
	var solicitudObj = new Object();
	solicitudObj.solicitudId = idSolicitudActiva;
	solicitudObj.sujetoObligado = new Object();
	solicitudObj.sujetoObligado = sujetoObigadoTramite;
	sendToServer('/afiliacion/cancelarSolicitud', solicitudObj,
			callbackCancelarSolicitudDatosFiscales, true);
}

function callbackCancelarSolicitudDatosFiscales(response) {
	procesarRespuestaServer(response, callbackConfirmarCancelacionSolicitud);
	$.unblockUI();
}

function callbackConfirmarCancelacionSolicitud(response) {
	if (!error) {
		$("#btnConcluirSolicitud").hide();
		var rfcParam;
		if (tipoPersonaFiscal == "FISICA") {
			rfcParam = $("#fisica\\.rfc").val();
		} else {
			rfcParam = $("#moral\\.rfc").val();
		}

		navegarTo('/sujetoObligado/recargarTramites?rfc=' + rfcParam,
				'formSupport');
	}
}

function validaRPSeleccionado() {
	var obRowSelected = fnGetRowSelected(gridRegistrosPatronales);
	var valorCampoRegistroPatronal = $("#numeroRegistroPatronal").val();
	if(valorCampoRegistroPatronal!=undefined){
		valorCampoRegistroPatronal = valorCampoRegistroPatronal.toUpperCase();
		$("#numeroRegistroPatronal").val(valorCampoRegistroPatronal);
	}
	if (obRowSelected == undefined && valorCampoRegistroPatronal == '') {
		dialogoError.dialog('open');
	} else {
		inicializaSujetoTramiteGeneral();
		var obRowSelected = fnGetRowSelected(gridRegistrosPatronales);
		if (obRowSelected != undefined) {
			sujetoObigadoTramite.numeroRegistroPatronal = obRowSelected.numeroRegistroPatronal
					+ obRowSelected.modalidad.numModalidad;
			document.getElementById('numeroRegistroPatronal').value = obRowSelected.numeroRegistroPatronal
					+ obRowSelected.modalidad.numModalidad;
		} else if (valorCampoRegistroPatronal != '') {
			if (valorCampoRegistroPatronal.length == 10
					|| valorCampoRegistroPatronal.length == 11) {
				sujetoObigadoTramite.numeroRegistroPatronal = valorCampoRegistroPatronal;
			} else {
				var oDialogoGenerico;
				construirDialogoGenerico(
						"#dialogoMensajes",
						oDialogoGenerico,
						"Aviso",
						"El registro patronal proporcionado es inv\u00E1lido. <br>Proporcione un registro v\u00E1lido a 10 u 11 posiciones.",
						true, undefined, undefined, 150, 400);
				return false;
			}
		}

		sendToServer('/afiliacion/validaRegistroPatronalPermitido',
				sujetoObigadoTramite, callbackValidaRPSeleccionado, false);
	}
}

function callbackValidaRPSeleccionado(response) {
	if (response.mensajeError != undefined && response.mensajeError != null) {
		titulo = "Operaci&oacute;n Erronea";
		error = true;
		if (response.mensajeError == "") {
			mensaje = "Ocurrio un error con el servidor.";
		} else {
			mensaje = response.mensajeError;
		}
		var oDialogoGenerico;
		construirDialogoGenerico("#dialogoMensajes", oDialogoGenerico, titulo,
				mensaje, error, undefined, undefined, 150, 400);
	} else {
		navegarTo('/afiliacion/mostrarDetalleRegistroPatronal',
				'registrosPatronalesForm');
	}
}

function navegarDetalle() {
	var obRowSelected = fnGetRowSelected(gridRegistrosPatronales);
	if (obRowSelected == undefined) {
		dialogoError.dialog('open');
	} else {
		document.getElementById('numeroRegistroPatronal').value = obRowSelected.numeroRegistroPatronal;
		$.blockUI();
		navegarTo('/afiliacion/mostrarDetalleRegistroPatronal',
				'registrosPatronalesForm');
	}
}

function navegarDetalleCT(idSolicitud) {
	var obRowSelected = fnGetRowSelected(oTableTramites);
	if (obRowSelected == undefined) {
		dialogoError.dialog('open');
	} else {
		$("#idSolicitud").val(idSolicitud);
		$("#idTramite").val(centroTrabajoNombreTramite);
		navegar(context_path + '/clasificacion/');
	}
//		$('#centroTrabajoInvokerForm #numeroRegistroPatronal').val(''+obRowSelected.sujetoObligado.numeroRegistroPatronal 
//				+ obRowSelected.sujetoObligado.modalidad.numModalidad);
//		$.blockUI();
//		navegarTo('/afiliacion/cargarTramiteCentroTrabajo?idSolicitud='
//				+ idSolicitud, 'centroTrabajoInvokerForm');
//	}
}

function navegarAConsultaSolicitudes() {
	navegarTo('/solicitud/consultaAvanzadaDeSolicitudes', 'detalleRPForm');
}

function enviarParametrosRepresentante(sSource, aoData, fnCallback) {
	var wrapper = new Object();
	wrapper.aoData = aoData;
	var oForm = new Object();
	oForm.cveIdPatronSujetoObligado = $("#cveIdSujetoObligado").val();
	wrapper.oForm = oForm;

	$.postJSON(sSource, wrapper, function(data) {
		fnCallback(data);
	});
}

function renderNombre(oObj) {
	
	var nombre = "";
	if(oObj.personaFisica.nombre!=undefined)
		nombre +=oObj.personaFisica.nombre;
	if(oObj.personaFisica.primerApellido)
		nombre +=" " + oObj.personaFisica.primerApellido;
	if(oObj.personaFisica.segundoApellido)
		nombre +=" " + oObj.personaFisica.segundoApellido;
	
	return nombre;
}

function parseIndicador(o) {
	if (o == '1' || o == 1) {
		return '<center>SI</center>';

	} else {
		return '<center>NO</center>';
	}
}

function construyeLiga(object) {

	return "<a href='#' onclick='showRepLegal(" + object
			+ ")'>Mostrar Detalle</a>";
}

function showRepLegal(object) {

	oDialogDetalleRepLegal = $('#divDetalleRepLegal').dialog({
		autoOpen : false,
		resizable : false,
		height : 700,
		width : 1000,
		modal : true,
		close : function() {
			$("#divDetalleMediosContactoRepLegal").html("");
		},
		buttons : {
			"Cerrar" : function() {
				$(this).dialog("close");
			}
		}
	});

	$("#divDetalleRepLegal").css("display", "block");

	$("#divDetalleRepLegal #detalleRepLegalRFC").text(
			arrayRepresentantes[object].personaFisica.rfc);
	$("#divDetalleRepLegal #detalleRepLegalCURP").text(
			arrayRepresentantes[object].personaFisica.curp);
	$("#divDetalleRepLegal #detalleRepLegalPrimerApellido").text(
			arrayRepresentantes[object].personaFisica.primerApellido);
	$("#divDetalleRepLegal #detalleRepLegalSegundoApellido").text(
			arrayRepresentantes[object].personaFisica.segundoApellido);
	$("#divDetalleRepLegal #detalleRepLegalNombre").text(
			arrayRepresentantes[object].personaFisica.nombre);

	if (arrayRepresentantes[object].indActAdmonDominio.indexOf("NO") != -1
			|| arrayRepresentantes[object].indActAdmonDominio.indexOf("no") != -1) {
		$("#divDetalleRepLegal #indActAdmonDominioDetalle").text("NO");
	} else {
		$("#divDetalleRepLegal #indActAdmonDominioDetalle").text("SI");
	}

	var mcRepLegalDetalle = new MedioContacto(
			"divDetalleMediosContactoRepLegal", 1, tpPropietarioRepLegal,
			arrayRepresentantes[object].cveIdRepresentanteLegal, undefined,
			idSujetoObligado, false);
	mcRepLegalDetalle.init();
    mcRepLegalDetalle.extendValidation();
	oDialogDetalleRepLegal.dialog('open');

}

function construirGridSociosActuales() {

	dtSocio = $('#tbSocio').dataTable({
		"bJQueryUI" : false,
		"bPaginate" : true,
		"bLengthChange" : false,
		"iDisplayLength" : 5,
		"bFilter" : false,
		"bSort" : false,
		"bInfo" : false,
		"bAutoWidth" : false,
		"sPaginationType" : "full_numbers",
		"bServerSide" : false,
		"aoColumns" : columnasSocio,
		"bProcessing" : true,
		"sAjaxSource" : '/delta-gestionPatronal-web/socios/fb/paginarSocios',
		"fnServerData" : enviarParametrosSocios
	});
}

function enviarParametrosSocios(sSource, aoData, fnCallback) {
	var wrapper = new Object();
	wrapper.aoData = aoData;
	wrapper.oForm = oForm;

	var wrapper = new Object();
	wrapper.aoData = aoData;
	var oForm = new Object();
	oForm.cveIdPatronSujetoObligado = $("#cveIdSujetoObligado").val();
	wrapper.oForm = oForm;

	$.postJSON(sSource, wrapper, function(data) {
		fnCallback(data);
	});

}

function fnRenderNombreRazonSocial(oObj) {
	if (oObj.nombres != null && oObj.nombres != undefined) {
		
		var razonSocialFisico = oObj.nombres;
		if(oObj.primerApellido!=undefined)
			razonSocialFisico += " "+oObj.primerApellido;
		if(oObj.segundoApellido!=undefined)
			razonSocialFisico += " "+oObj.segundoApellido;
		return razonSocialFisico.toUpperCase();
	} else {
		return oObj.nombreRazonSocial.toUpperCase();
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

function construyeLigaSocio(object) {
	return "<a href='#' onclick='showDetalleSocio(" + object
			+ ")'>Mostrar Detalle</a>";
}

function showDetalleSocio(indice) {
	$("#divDetalleSocioFisicoDatosContacto").html("");
	$("#divDetalleSocioMoralDatosContacto").html("");
	$("#divDetalleSocioFideicomisoDatosContacto").html("");

	if (arrayDatosSocio[indice].tipoSocio.idTipoPersona == 1) { // configuramos
																// socio fisico

		oDialogSocioConsultar = $('#divDetallesocioFisico').dialog({
			autoOpen : false,
			resizable : false,
			title : "Socio F\u00EDsico Detalle",
			height : 650,
			width : 900,
			modal : true,
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
				$("#divDetallesocioFisico #lbFisicaReferColonia")
						.text(
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

			$("#divDetallesocioFisico #lbFisicaReferCP").text(
					arrayDatosSocio[indice].domicilioFiscal.codigoPostal.codigoPostal);

		}

		// detalle para socio fisico extranjero con residencia extranjera
		if (arrayDatosSocio[indice].esDomicilioNacional == "Extranjera"
				&& arrayDatosSocio[indice].esNacional == "Extranjero") {

			$("#divDetallesocioFisico #filaNSRFC").hide();
			$("#divDetallesocioFisico #filaNSCURP").hide();
			$("#divDetallesocioFisico #divDetalleSocioFisicoDomicilio").css(
					"display", "none");
		} else {
			$("#divDetallesocioFisico #filaNSRFC").show();
			$("#divDetallesocioFisico #filaNSCURP").show();
			$("#divDetallesocioFisico #divDetalleSocioFisicoDomicilio").css(
					"display", "block");
		}

		var mcSocioDetalle = new MedioContacto(
				"divDetalleSocioFisicoDatosContacto", 1,
				tpPropietarioSocioPersonaFisica,
				arrayDatosSocio[indice].idSocio, undefined, idSujetoObligado, false);
		mcSocioDetalle.init();
        mcSocioDetalle.extendValidation();

	} else if (arrayDatosSocio[indice].tipoSocio.idTipoPersona == 2) { // configuramos
																		// socio
																		// moral

		oDialogSocioConsultar = $('#divDetallesocioMoral').dialog({
			autoOpen : false,
			resizable : false,
			title : "Socio Moral Detalle",
			height : 720,
			width : 1000,
			modal : true,
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
		
		var numEscritura = "Sin informaci\u00F3n";
		var numNotaria = "Sin informaci\u00F3n";
		var entidad = "Sin informaci\u00F3n";
		var municipio = "Sin informaci\u00F3n";
		var fechaConstitucion = "Sin informaci\u00F3n";
		
		if(arrayDatosSocio[indice].escrituraConstitutiva != null){
			numEscritura = arrayDatosSocio[indice].escrituraConstitutiva.numEscritura;
			numNotaria = arrayDatosSocio[indice].escrituraConstitutiva.numNotaria;
			entidad = arrayDatosSocio[indice].escrituraConstitutiva.lugarExpedicion.entidadFederativa.nombre;
			municipio = arrayDatosSocio[indice].escrituraConstitutiva.lugarExpedicion.nombre;
			fechaConstitucion = arrayDatosSocio[indice].escrituraConstitutiva.fechaExpedicion;
		}
		
		
		$("#divDetallesocioMoral #lbMoralNumEscritura").text(numEscritura);
		$("#divDetallesocioMoral #lbMoralNumNotaria").text(numNotaria);
		$("#divDetallesocioMoral #lbMoralEntidadFed").text(entidad);
		$("#divDetallesocioMoral #lbMoralMunicipio").text(municipio);
		$("#divDetallesocioMoral #lbMoralFecConstitucion").text(fechaConstitucion);
		
		
		// seccion acta contitutiva
		$("#divDetallesocioMoral #lbMoralFolioMercantil")
				.text(
						arrayDatosSocio[indice].escrituraConstitutiva != null && arrayDatosSocio[indice].escrituraConstitutiva.folioMercantil!=null
						? arrayDatosSocio[indice].escrituraConstitutiva.folioMercantil : "Sin informaci\u00F3n");
		$("#divDetallesocioMoral #lbMoralSeccion")
				.text(
						arrayDatosSocio[indice].escrituraConstitutiva != null && arrayDatosSocio[indice].escrituraConstitutiva.seccion!=null
						? arrayDatosSocio[indice].escrituraConstitutiva.seccion
								: "Sin Informaci\u00F3n");
		$("#divDetallesocioMoral #lbMoralPartida")
				.text(
						arrayDatosSocio[indice].escrituraConstitutiva != null && arrayDatosSocio[indice].escrituraConstitutiva.partida!=null
						? arrayDatosSocio[indice].escrituraConstitutiva.partida
								: "Sin Informaci\u00F3n");
		$("#divDetallesocioMoral #lbMoralVolumen")
				.text(
						arrayDatosSocio[indice].escrituraConstitutiva != null && arrayDatosSocio[indice].escrituraConstitutiva.volumen!=null 
						? arrayDatosSocio[indice].escrituraConstitutiva.volumen
								: "Sin Informaci\u00F3n");
		$("#divDetallesocioMoral #lbMoralFoja")
				.text(
						arrayDatosSocio[indice].escrituraConstitutiva != null && arrayDatosSocio[indice].escrituraConstitutiva.foja!=null
						? arrayDatosSocio[indice].escrituraConstitutiva.foja
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
					arrayDatosSocio[indice].domicilioFiscal.codigoPostal);
			
			
		}else{
			
			inicializarDatosDomFiscalMoral();
		}

		// detalle para socio moral extranjero con residencia extranjera
		if (arrayDatosSocio[indice].esDomicilioNacional == "Extranjera"
				&& arrayDatosSocio[indice].esNacional == "Extranjero") {

			$("#divDetallesocioMoral #filaRFCMoral").hide();
			$("#divDetallesocioMoral #filaTipoSociedadMoral").hide();
			$(
					"#divDetallesocioMoral #divDetalleEscrituraConstitutivaSocioMoral")
					.css("display", "none");
			$("#divDetallesocioMoral #divDetalleSocioMoralDomicilio").css(
					"display", "none");
		} else {
			$("#divDetallesocioMoral #filaRFCMoral").show();
			$("#divDetallesocioMoral #filaTipoSociedadMoral").show();
			$(
					"#divDetallesocioMoral #divDetalleEscrituraConstitutivaSocioMoral")
					.css("display", "block");
			$("#divDetallesocioMoral #divDetalleSocioMoralDomicilio").css(
					"display", "block");
		}

		var mcSocioDetalle = new MedioContacto(
				"divDetalleSocioMoralDatosContacto", 1,
				tpPropietarioSocioPersonaFisica,
				arrayDatosSocio[indice].idSocio, undefined, idSujetoObligado, false);
		mcSocioDetalle.init();
        mcSocioDetalle.extendValidation();

	} else if (arrayDatosSocio[indice].tipoSocio.idTipoPersona == 3) { // configuramos
																		// socio
																		// fideicomiso

		oDialogSocioConsultar = $('#divDetallesocioFideicomiso').dialog({
			autoOpen : false,
			resizable : false,
			title : "Socio Fideicomiso Detalle",
			height : 720,
			width : 1000,
			modal : true,
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
					arrayDatosSocio[indice].domicilioFiscal.codigoPostal);

		}

		var mcSocioDetalle = new MedioContacto(
				"divDetalleSocioFideicomisoDatosContacto", 1,
				tpPropietarioSocioPersonaFisica,
				arrayDatosSocio[indice].idSocio, undefined, idSujetoObligado, false);
		mcSocioDetalle.init();
        mcSocioDetalle.extendValidation();

	}

	oDialogSocioConsultar.dialog('open');

}

function inicializarDatosDomFiscalMoral(){
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

function mostrarDetalleRegistro(idPatron) {
	return "<a href='#' onclick='showRegistroPatronal(" + idPatron
			+ ")'>Detalle</a>";
}

function showRegistroPatronal(idPatron) {

	oDialogoDetalleRegistroPatronal = $('#dgDetalleRegistroPatronal').dialog({
		autoOpen : false,
		resizable : false,
		title : "Detalle de Registro Patronal",
		// height: 350,
		height : 'auto',
		width : 1000,
		modal : true,
		buttons : {
			"Cerrar" : function() {
				$(this).dialog("close");
			}
		}
	});

	$("#dgDetalleRegistroPatronal").css("display", "block");
	
	$("#textoDomicilioCentroTrabajo").text("");//Se inicializa la direccion de centro de trabajo
	
	var patron = listaPatrones[idPatron];
	var clasificacion = patron.clasificacion;
	
	if(clasificacion!=undefined){
		var fraccionCompleta = ''
				+ listaPatrones[idPatron].clasificacion.fraccion.grupo.division.numDivision
				+ listaPatrones[idPatron].clasificacion.fraccion.grupo.numGrupo
				+ listaPatrones[idPatron].clasificacion.fraccion.numFraccion;
		$("#dgDetalleRegistroPatronal #desGiro").text(clasificacion.giro);
		$("#dgDetalleRegistroPatronal #idFraccionAct").text(fraccionCompleta);
		$("#dgDetalleRegistroPatronal #cvedivisionAct")
				.text(
						listaPatrones[idPatron].clasificacion.fraccion.grupo.division.descripcion);
		$("#dgDetalleRegistroPatronal #cvegrupoAct").text(
				listaPatrones[idPatron].clasificacion.fraccion.grupo.descripcion);
		$("#dgDetalleRegistroPatronal #cvefraccionAct").text(
				listaPatrones[idPatron].clasificacion.fraccion.descripcion);
	}
	var cTrabajo = patron.cntroTrabajo;
	
	if(cTrabajo!=undefined && cTrabajo!=null){
		$("#textoDomicilioCentroTrabajo").text(cTrabajo.descripcion);
	}
	oDialogoDetalleRegistroPatronal.dialog("open");
	oDialogoDetalleRegistroPatronal.focus();
}

function muestraOpciones() {
	if (validaTramiteExiste()) {
		if (indicadorTramiteClasifExistente == 'true'
				|| indicadorTramiteClasifExistente == true) {
			dialogoTramiteClasifExistente.dialog('open');
		} else {
			index = -1;
			dialogoListaTramitesClasificacion.dialog('open');
		}
	}
}

function validaSeleccionDeTramiteClasificacion() {

	if (index != -1) {
		$("#idSolicitud").val("");
		$("#idTramite").val($("#selectable li")[index].id);

		dialogoListaTramitesClasificacion.dialog('close');
		$.blockUI();
		if (context.indexOf("clasificacion") < 0) {
			context += "/clasificacion/";
		} else {
			context += "/";
		}
		var obRowSelected = fnGetRowSelected(gridRegistrosPatronales);
		$('#clasificacionInvokerForm #cveIdSujetoObligado').val(
				obRowSelected.cveIdSujetoObligado);
		$('#clasificacionInvokerForm #numeroRegistroPatronal').val(
				''+ obRowSelected.numeroRegistroPatronal
				+ obRowSelected.modalidad.numModalidad
				+ obRowSelected.digVerificador);

		navegarTo('?idTramite=' + $("#selectable li")[index].id
				+ '&idSolicitud=', 'clasificacionInvokerForm');

	} else {
		var oDialogoGenerico;
		construirDialogoGenerico("#dialogoMensajes", oDialogoGenerico, "Aviso",
				"Debe seleccionar el tr\u00E1mite que desea realizar.", true,
				undefined, undefined, 150, 400);
	}
}

function desplegarCentroTrabajo() {
	var obRowSelected = fnGetRowSelected(gridRegistrosPatronales);
	if (obRowSelected == undefined || obRowSelected == null) {
		dialogoError.dialog('open');
		return false;
	} else {
		validaTramiteCentroTrabajoExiste(obRowSelected);
		if (!existeTramiteCentroTrabajoEnCurso) {
			if (context.indexOf("clasificacion") < 0) {
				context += "/clasificacion/";
			} else {
				context += "/";
			}
			$('#clasificacionInvokerForm #cveIdSujetoObligado').val(
					obRowSelected.cveIdSujetoObligado);
			$('#clasificacionInvokerForm #numeroRegistroPatronal').val(
					''+ obRowSelected.numeroRegistroPatronal
					+ obRowSelected.modalidad.numModalidad
					+ obRowSelected.digVerificador);
			$("#idTramite").val(centroTrabajoNombreTramite);
			navegarTo('?idTramite=' + centroTrabajoNombreTramite
					+ '&idSolicitud=', 'clasificacionInvokerForm');
//			$('#centroTrabajoInvokerForm #cveIdSujetoObligado').val(
//					obRowSelected.cveIdSujetoObligado);
//			$('#centroTrabajoInvokerForm #numeroRegistroPatronal').val(
//					obRowSelected.numeroRegistroPatronal);
//
//			navegarTo('/afiliacion/mostrarCentroTrabajo',
//					'centroTrabajoInvokerForm');
		} else {
			var oDialogoGenerico;
			construirDialogoGenerico(
					"#dialogoMensajes",
					oDialogoGenerico,
					"Error",
					"Actualmente existe un tr\u00E1mite en curso por favor verifique la informaci\u00F3n del tr\u00E1mite y concluya la captura del mismo.",
					true, undefined, undefined, 150, 600);
		}
	}
}

function validaTramiteCentroTrabajoExiste(obRowSelected) {
	var sSource = '/afiliacion/validaTramiteCentroTrabajoExistente';
	var idPatronSeleccionado = obRowSelected.cveIdSujetoObligado;
	sendToServer(sSource, idPatronSeleccionado,
			callbackValidaTramiteCentroTrabajoExistente, false);
}

function callbackValidaTramiteCentroTrabajoExistente(response) {
	existeTramiteCentroTrabajoEnCurso = response.tramiteEnCurso;
}

function navegarABusquedaRFC() {
	navegarTo("/sujetoObligado", "busquedaRFCForm");
}

function inicializaFiltroRegistrosPatronales() {
	$("#registrosPatronalesForm #numeroRegistroPatronal").keyup(
			function() {
				gridRegistrosPatronales.fnFilter($(
						"#registrosPatronalesForm #numeroRegistroPatronal")
						.val());
			});

	$("#gridRegistrosPatronales_filter").dialog({
		autoOpen : false
	});
}

function validaPermisosDeEjecucion(callbackPermisos) {
	if (tipoPersonaFiscal == "FISICA") {
		rfcEnviarValidacion = $("#fisica\\.rfc").val();
	} else {
		rfcEnviarValidacion = $("#moral\\.rfc").val();
	}
	sendToServer("/afiliacion/validaPermisos", rfcEnviarValidacion,
			callbackPermisos, false);
}

function callbackEvaluacionPermisosGenerarSolicitud(response) {
	if (response.mensajeError == undefined || response.mensajeError == "") {
		$('#formSupport').submit();
	} else {
		procesarRespuestaServer(response, navegarABusquedaRFC, 150, 500);
	}
}

function callbackEvaluacionPermisosConsultarSolicitudes(response) {
	if (response.mensajeError == undefined || response.mensajeError == "") {
		navegarAConsultaSolicitudes();
	} else {
		procesarRespuestaServer(response, navegarABusquedaRFC, 150, 500);
	}
}

function callbackEvaluacionPermisosModificarSRT(response) {
	if (response.mensajeError == undefined || response.mensajeError == "") {
		if(isOperadorIMSS){
			if(evaluarPermisosPorSubdelegacion())
				muestraOpciones();
			else
				return false;
		}else{
			muestraOpciones();
		}
		
	} else {
		procesarRespuestaServer(response, navegarABusquedaRFC, 150, 500);
	}
}

function evaluarPermisosPorSubdelegacion(){
	var obRowSelected = fnGetRowSelected(gridRegistrosPatronales);
	var idSubdelegacionRP = 0;
	
	if(obRowSelected.subdelegacion!=undefined)
		idSubdelegacionRP = obRowSelected.subdelegacion.id;
	
	if(idSubdelegacionRP!=0 && idSubdelegacionRP!=idSubdelegacionOperador){
		var oDialogoGenerico;
		construirDialogoGenerico(
				"#dialogoMensajes",
				oDialogoGenerico,
				"Error",
				"\u00A1Permiso denegado!.<br>El registro patronal seleccionado no pertenece a su subdelegaci\u00F3n",
				true, undefined, undefined, 200, 600);
		return false;
	}
	return true;
}

function callbackEvaluacionPermisosModificarCentroTrabajo(response) {
	if (response.mensajeError == undefined || response.mensajeError == "") {
		if(isOperadorIMSS){
			if(evaluarPermisosPorSubdelegacion())
				desplegarCentroTrabajo();
			else
				return false;
		}else{
			desplegarCentroTrabajo();
		}
	} else {
		procesarRespuestaServer(response, navegarABusquedaRFC, 500, 600);
	}
}

function fnOnClosePresentarAcuse() {

}

function mostrarMensajeRPenProceso() {
	var textoMensajeRPenProceso = "La solicitud ha sido enviada con \u00E9xito al Instituto Mexicano del Seguro Social, "
			+ "ahora debe presentarse en la Delegaci\u00F3n y Subdelegaci\u00F3n correspondiente para concluir su tr\u00E1mite."
			+ "\n Recuerde que debe presentar una copia de su acuse de recibo.";

	construirDialogoAceptarCancelar(textoMensajeRPenProceso, callbackMensajeRPenProcesoAceptar, callbackMensajeRPenProcesoCancelar, 200, 600);
}

function callbackMensajeRPenProcesoAceptar() {
	$("#formaRegresoDetalle").submit();
}

function callbackMensajeRPenProcesoCancelar() {
}

function construirDialogoAceptarCancelar(mensaje, callbackAceptar, callbackCancelar, height, width) {
	if (height == undefined) {
		height = 150;
	}
	if (width == undefined) {
		width = 400;
	}

	$("#textoMensaje").html(mensaje);
	var dialogo = $("#dialogoMensajes").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : height,
		width : width,
		title : "Solicitudes en Proceso",
		buttons : {
			"Aceptar" : function() {
				if(callbackCancelar!=undefined)
					callbackCancelar();
				$(this).dialog("close");
			}, "Generar acuse" : function() {
				if(callbackAceptar!=undefined)
					callbackAceptar();
				$(this).dialog("close");
			}
		}
	});

	dialogo.dialog('open');
}

function testcdrs(){
	var DRSCtrl;
	$.getScript("/delta-gestionPatronal-web/static/resources/js/delta/portal/afiliacion/cdrs.js", function(){
		DRSCtrl =  tramiteDRSCtrl;
		 // Div para crear el diálogo
		DRSCtrl.init('cdrsContainer');
		DRSCtrl.datosEntrada.idPersona=25129441;
		DRSCtrl.datosEntrada.idTipoPersona=1;
		 // Función de callback
		DRSCtrl.setOnCloseCallback(mainCallback);
		DRSCtrl.desplegar();
	});
	
}

function mainCallback(){
	alert('regrese');
}


function testmsrt(){
	var MSRT;
	$.getScript("/delta-gestionPatronal-web/static/resources/js/delta/portal/afiliacion/msrt.js", function(){
		MSRT =  ModSRTCtrl;
		 // Div para crear el diálogo
		MSRT.init('msrtContainer');
		MSRT.datosEntrada.numeroRegistroPatronal="Y5447651101";
		 // Función de callback
		MSRT.setOnCloseCallback(mainCallback);
		MSRT.desplegar();
	});
	
}

