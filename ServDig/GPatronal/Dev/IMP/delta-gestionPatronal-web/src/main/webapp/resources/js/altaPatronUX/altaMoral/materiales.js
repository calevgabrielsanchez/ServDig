var idTblMateriasPrimas = "#idTblMateriasPrimas";
var idTblMaquinariaEquipo = "#idTblMaquinariaEquipo";
var idTblEquipoTransporte = "#idTblEquipoTransporte";

var $formMateriasPrimas;
var $formMaquinariaEquipo;
var $formEquipoTransporte;
var $formTablaMateriasPrimas;
var $formTablaMaquinariaEquipo;
var $formTablaEquipoTransporte;
var $formChkEquipoTransportePropio;
var $formChkEquipoTransporteAjeno;

var contadorRegistrosMateriasPrimas = 0;
var contadorRegistrosMaquinariaEquipo = 0;
var contadorRegistrosEquipoTransporte = 0;
var contadorUnidadesEquipoTransporte = 0;

var tabla = {
		materiaPrima : null,
		maquinariaEquipo : null,
		equipoTransporte : null,
	};

var aaDataMateriaPrima = [];
var aaDataMaquinariaEquipo = [];
var aaDataEquipoTransporte = [];

var columnasTblMateriaPrima = [ {
	mDataProp : "id",
	sTitle : "",
	bVisible : false
}, {
	mDataProp : "descripcion",
	sTitle : "Descripci\u00F3n",
	bVisible : true,
	sWidth: "1000px",
	componente : 'textarea',
	type : "texto",
	size:"300"
},{
	sTitle : "Eliminar",
	bSortable : false,
	size:"5",
	sClass : "dt-center",
	fnRender : function ( objeto, val) {
					return generaBotonEliminarMateriales(objeto.aData.id, 'idTblMateriasPrimas');
				}
} ];

var columnasTblMqEquipos = [ {
	mDataProp : "id",
	sTitle : "",
	bVisible : false
}, {
	mDataProp : "desNombre",
	sTitle : "Nombre",
	bVisible : true,
	sWidth: "250px",
	size:"30"
}, {
	mDataProp : "desCapacidadPotencia",
	sTitle : "Capacidad/Potencia",
	bVisible : true,
	sWidth: "200px",
	size:"25"
}, {
	mDataProp : "tipo.id",
	sTitle : "",
	bVisible : false
}, {
	mDataProp : "tipo.descripcion",
	sTitle : "Tipo Maquinaria",
	bVisible : true,
	sWidth: "200px"
}, {
	mDataProp : "desUso",
	sTitle : "Uso",
	bVisible : true,
	sWidth: "300px",
	size:"40"
}, {
	mDataProp : "numUnidades",
	sTitle : "Unidades",
	bVisible : true,
	sWidth: "75px",
	size:"5"
},{
	sTitle : "Eliminar",
	bSortable : false,
	size:"5",
	sClass : "dt-center",
	fnRender : function ( objeto, val) {
					return generaBotonEliminarMateriales(objeto.aData.id, 'idTblMaquinariaEquipo');
				}
} ];

var columnasTblTransport = [ {
	mDataProp : "id",
	sTitle : "",
	bVisible : false
}, {
	mDataProp : "desNombre",
	sTitle : "Nombre",
	bVisible : true,
	sWidth: "250px",
	size:"30"
}, {
	mDataProp : "desCapacidadPotencia",
	sTitle : "Capacidad/Potencia",
	bVisible : true,
	sWidth: "200px",
	size:"25"
}, {
	mDataProp : "tipoCombustible.clave",
	sTitle : "",
	bVisible : false
}, {
	mDataProp : "tipoCombustible.desTipoCombustible",
	sTitle : "Tipo de Transporte",
	bVisible : true
}, {
	mDataProp : "desUso",
	sTitle : "Uso",
	bVisible : true,
	sWidth: "300px",
	size:"40"
}, {
	mDataProp : "numUnidades",
	sTitle : "Unidades",
	bVisible : true,
	sWidth: "75px",
	size:"5"
},{
	sTitle : "Eliminar",
	bSortable : false,
	size:"5",
	sClass : "dt-center",
	fnRender : function ( objeto, val) {
					return generaBotonEliminarMateriales(objeto.aData.id, 'idTblEquipoTransporte');
				}
} ];

$(document).ready(function() {
	
	$formMateriasPrimas = $("#idFrmAgregarMateriasPrimas");
	$formMaquinariaEquipo = $("#idFrmAgregarMaquinariaEquipo");
	$formEquipoTransporte = $("#idFrmAgregarEquipoTransporte");
	$formTablaMateriasPrimas = $("#idFrmTablaMateriasPrimas");
	$formTablaMaquinariaEquipo = $("#idFrmTablaMaquinariaEquipo");
	$formTablaEquipoTransporte = $("#idFrmTablaEquipoTransporte");
	$formChkEquipoTransportePropio = $("#idFrmChkEquipoTransportePropio");
	$formChkEquipoTransporteAjeno = $("#idFrmChkEquipoTransporteAjeno");
	
	$("#idBtnAgregarMateriaPrima").click(function(event) {
		agregarColumnasTablaMateriales(event.currentTarget.id);
	});
	
	$("#idBtnAgregarMaquinariaEquipo").click(function(event) {
		agregarColumnasTablaMateriales(event.currentTarget.id);
	});
	
	$("#idBtnAgregarEquipoTransporte").click(function(event) {
		agregarColumnasTablaMateriales(event.currentTarget.id);
	});
	
	$("#idLinkAgregarMateriaPrima").click(function(event) {
		if ($("#idPanelMateriasPrimas").hasClass('in')) {
			limpiarForm("idFrmAgregarMateriasPrimas");
			$("#idDivTxtAreaMateriasPrimas").find("span").each(function() {
				$(this).remove();
			});
		} else {
			if (tabla['materiaPrima'].fnGetData().length >= 10) {
				$("#idBtnAgregarMateriaPrima").prop('disabled', true);
			} else {
				$("#idBtnAgregarMateriaPrima").prop('disabled', false);
			}
		}
	});
	
	$("#idLinkAgregarMaquinariaEquipo").click(function(event) {
		if ($("#idPanelMaquinariaEquipo").hasClass('in')) {
			limpiarForm("idFrmAgregarMaquinariaEquipo");
		} else {
			if (tabla['maquinariaEquipo'].fnGetData().length >= 10) {
				$("#idBtnAgregarMaquinariaEquipo").prop('disabled', true);
			} else {
				$("#idBtnAgregarMaquinariaEquipo").prop('disabled', false);
			}
		}
	});
	
	$("#idLinkAgregarEquipoTransporte").click(function(event) {
		if ($("#idPanelEquipoTransporte").hasClass('in')) {
			limpiarForm("idFrmAgregarEquipoTransporte");
		} else {
			if (tabla['equipoTransporte'].fnGetData().length >= 10) {
				$("#idBtnAgregarEquipoTransporte").prop('disabled', true);
			} else {
				$("#idBtnAgregarEquipoTransporte").prop('disabled', false);
			}
		}
	});
	
	$("#idBtnSiguienteMateriales").click(function(event) {
		if ($formTablaMateriasPrimas.valid() & 
				$formTablaMaquinariaEquipo.valid() & 
				( !($("#siCuetaConTransporte").is(':checked')) ? true : $formTablaEquipoTransporte.valid()) & 
				( !($("#siCuetaConTransporte").is(':checked')) ? true : $formChkEquipoTransportePropio.valid()) & 
				( !($("#siCuetaConTransporte").is(':checked')) ? true : $formChkEquipoTransporteAjeno.valid())) {
			setCamposObjetoMateriales();
			if ($("#idPanelMateriasPrimas").hasClass('in')) {
				limpiarForm("idFrmAgregarMateriasPrimas");
				$("#idDivTxtAreaMateriasPrimas").find("span").each(function() {
					$(this).remove();
				});
				$("#idPanelMateriasPrimas").removeClass('in');
			}
			if ($("#idPanelMaquinariaEquipo").hasClass('in')) {
				limpiarForm("idFrmAgregarMaquinariaEquipo");
				$("#idPanelMaquinariaEquipo").removeClass('in');
			}
			if ($("#idPanelEquipoTransporte").hasClass('in')) {
				limpiarForm("idFrmAgregarEquipoTransporte");
				$("#idPanelEquipoTransporte").removeClass('in');
			}
			paginaSiguiente();
		}
	});
	
	$("#idBtnAtrasMateriales").click(function(event) {
		paginaPrevia();
	});
	
	$("#idTxtDesMateriaPrima").keyup(function(event) {
		validaSize(this, 300, event);
	});
	
	$("#idTxtDesMateriaPrima").keypress(function(event) {
		validaSize(this, 300, event);
	});
	
	intReglaValidacionTablaMateriasPrimas();
	intReglaValidacionTablaMaquinariaEquipo();
	intReglaValidacionTablaEquipoTransporte();
	intReglaValidacionCuentaConTransPropio();
	intReglaValidacionCuentaConTransAjeno();
	inicializaAltaPatronalUxMateriales();
	initValidatorMateriasPrimas();
	initValidatorMaquinariaEquipo();
	initValidatorEquipoTransporte();
	intValidacionTablaMateria();
	intValidacionTablaMaquinariaEquipo();
	intValidacionTablaEquipoTransporte();
	intValidacionChkCuentaConTransPropio();
	intValidacionChkCuentaConTransAjeno();
});

/**
 * Metodo para iniciarlizar el validador del formulario de materias primas
 */
var initValidatorMateriasPrimas = function() {
	$formMateriasPrimas.validate($.extend({}, DEFAULTS_VALIDATE, {
		verifyErrors: function(existError) {
			mostrarMensajeErrorGenerico($formMateriasPrimas.attr("id"), existError, MENSAJE_ERROR_FORM);
			marcarAsteriscos($formMateriasPrimas, ".errorDocs", ".col-sm-9");
		},
		rules: {
			txtDesMateriaPrima: {
				required: true,
				maxlength: 300
			}
		}
	}));
}

/**
 * Metodo para iniciarlizar el validador del formulario de Maquinaria y Equipo
 */
var initValidatorMaquinariaEquipo = function() {
	$formMaquinariaEquipo.validate($.extend({}, DEFAULTS_VALIDATE, {
		verifyErrors: function(existError) {
			mostrarMensajeErrorGenerico($formMaquinariaEquipo.attr("id"), existError, MENSAJE_ERROR_FORM);
			marcarAsteriscos($formMaquinariaEquipo, ".errorDocs", ".col-sm-9");
		},
		rules: {
			txtDesMaquinariaEquipo: {
				required: true,
				maxlength: 30
			},
			txtCapacidadMaquinariaEquipo: {
				maxlength: 25
			},
			sltTipoMaquinaria: {
				min : 0
			},
			txtUsoMaquinariaEquipo: {
				required: true,
				maxlength: 100
			},
			txtUnidadesMaquinariaEquipo: {
				required: true,
				number: true,
				maxlength: 5,
				min: 1
			}
		},
		messages : {
			sltTipoMaquinaria : {
				min : "Seleccione un tipo de maquinaria v\u00E1lido."
			}
		}
	}));
}

/**
 * Metodo para iniciarlizar el validador del formulario de Maquinaria y Equipo
 */
var initValidatorEquipoTransporte = function() {
	$formEquipoTransporte.validate($.extend({}, DEFAULTS_VALIDATE, {
		verifyErrors: function(existError) {
			mostrarMensajeErrorGenerico($formEquipoTransporte.attr("id"), existError, MENSAJE_ERROR_FORM);
			marcarAsteriscos($formEquipoTransporte, ".errorDocs", ".col-sm-9");
		},
		rules: {
			txtDesEquipoTransporte: {
				required: true,
				maxlength: 30
			},
			txtCapacidadEquipoTransporte: {
				required: true,
				maxlength: 25
			},
			sltTipoCombustibleEquipoTransporte: {
				min : 0
			},
			txtUsoEquipoTransporte: {
				required: true,
				maxlength: 40
			},
			txtUnidadesEquipoTransporte: {
				required: true,
				number: true,
				maxlength: 5,
				min: 1
			}
		},
		messages : {
			sltTipoCombustibleEquipoTransporte : {
				min : "Seleccione un tipo de combustible v\u00E1lido."
			}
		}
	}));
}

function intValidacionTablaMateria() {
	$formTablaMateriasPrimas.validate($.extend({}, DEFAULTS_VALIDATE, {
		verifyErrors: function(existError) {
			mostrarMensajeErrorMateriales($formTablaMateriasPrimas.attr("id"), existError, "<strong>Error en el formulario!</strong> no ha llenado todos los campos requeridos. Por favor verifique");
			marcarAsteriscos($formTablaMateriasPrimas, ".errorDocs", "form");
		},
		rules: {
			hdnValidaTablaMateriasPrimas: {
				validaTablaMateriasPrimas : true
			}
		},
		ignore: "",//esta propiedad se sobre escribe para que puedaas usar campos hidden
		errorElement: "span"
	}));
	
}

function intValidacionTablaMaquinariaEquipo() {
	$formTablaMaquinariaEquipo.validate($.extend({}, DEFAULTS_VALIDATE, {
		verifyErrors: function(existError) {
			mostrarMensajeErrorMateriales($formTablaMaquinariaEquipo.attr("id"), existError, "<strong>Error en el formulario!</strong> no ha llenado todos los campos requeridos. Por favor verifique");
			marcarAsteriscos($formTablaMaquinariaEquipo, ".errorDocs", "form");
		},
		rules: {
			hdnValidaTablaMaquinariaEquipo: {
				validaTablaMaquinariaEquipo : true
			}
		},
		ignore: "",
		errorElement: "span"
	}));
	
}

function intValidacionTablaEquipoTransporte() {
	$formTablaEquipoTransporte.validate($.extend({}, DEFAULTS_VALIDATE, {
		verifyErrors: function(existError) {
			mostrarMensajeErrorMateriales($formTablaEquipoTransporte.attr("id"), existError, "<strong>Error en el formulario!</strong> no ha llenado todos los campos requeridos. Por favor verifique");
			marcarAsteriscos($formTablaEquipoTransporte, ".errorDocs", "form");
		},
		rules: {
			hdnValidaTablaEquipoTransporte: {
				validaTablaEquipoTransporte : true
			}
		},
		ignore: "",
		errorElement: "span"
	}));
	
}

function intValidacionChkCuentaConTransPropio() {
	$formChkEquipoTransportePropio.validate($.extend({}, DEFAULTS_VALIDATE, {
		verifyErrors: function(existError) {
			mostrarMensajeErrorMateriales($formChkEquipoTransportePropio.attr("id"), existError, "<strong>Error en el formulario!</strong> no ha llenado todos los campos requeridos. Por favor verifique");
			marcarAsteriscos($formChkEquipoTransportePropio, ".errorDocs", "form");
		},
		rules: {
			hdnValidaCuentaConTransPropio: {
				validaCuentaConTransPropio : true
			}
		},
		ignore: "",
		errorElement: "span"
	}));
}

function intValidacionChkCuentaConTransAjeno() {
	$formChkEquipoTransporteAjeno.validate($.extend({}, DEFAULTS_VALIDATE, {
		verifyErrors: function(existError) {
			mostrarMensajeErrorMateriales($formChkEquipoTransporteAjeno.attr("id"), existError, "<strong>Error en el formulario!</strong> no ha llenado todos los campos requeridos. Por favor verifique");
		},
		rules: {
			hdnValidaCuentaConTransAjeno: {
				validaCuentaConTransAjeno : true
			}
		},
		ignore: "",
		errorElement: "span"
	}));
}

function intReglaValidacionTablaMateriasPrimas() {
	$.validator.addMethod("validaTablaMateriasPrimas", function(value, elem, param) {
		if (tabla['materiaPrima'].fnGetData( ).length > 0) {
			return true;
		} else {
			return false;
		}
	},"Materias primas requeridas.");
}

function intReglaValidacionTablaMaquinariaEquipo() {
	$.validator.addMethod("validaTablaMaquinariaEquipo", function(value, elem, param) {
		if (tabla['maquinariaEquipo'].fnGetData( ).length > 0) {
			return true;
		} else {
			return false;
		}
	},"Maquinaria y equipo requeridos.");
}

function intReglaValidacionTablaEquipoTransporte() {
	$.validator.addMethod("validaTablaEquipoTransporte", function(value, elem, param) {
		if (tabla['equipoTransporte'].fnGetData( ).length > 0) {
			return true;
		} else {
			return false;
		}
	},"Equipo de transporte requeridos.");
}

function intReglaValidacionCuentaConTransPropio() {
	$.validator.addMethod("validaCuentaConTransPropio", function(value, elem, param) {
		if (contadorUnidadesEquipoTransporte > 0 & ($("#idChkConTransportePropio").prop('checked') || $("#idChkConTransporteAjeno").prop('checked'))) {
			return true;
		} else {
			return false;
		}
	},"Selecciona un tipo de transporte.");
}

function intReglaValidacionCuentaConTransAjeno() {
	$.validator.addMethod("validaCuentaConTransAjeno", function(value, elem, param) {
		if (contadorUnidadesEquipoTransporte > 0 & ($("#idChkConTransportePropio").prop('checked') || $("#idChkConTransporteAjeno").prop('checked'))) {
			return true;
		} else {
			return false;
		}
	},"Selecciona un tipo de transporte.");
}

var mostrarMensajeErrorMateriales = function(idForm, mostrar, mensaje) {
	if (mostrar) {
		if (idForm == "idFrmTablaMateriasPrimas" || idForm == "idFrmTablaMaquinariaEquipo" || idForm == "idFrmTablaEquipoTransporte" || 
			idForm == "idFrmChkEquipoTransportePropio" || idForm == "idFrmChkEquipoTransporteAjeno") {
			$("#idErrorFormMateriales").html(mensaje).show();
		}
	} else {
		if (idForm == "idFrmTablaMateriasPrimas" || idForm == "idFrmTablaMaquinariaEquipo" || idForm == "idFrmTablaEquipoTransporte" || 
			idForm == "idFrmChkEquipoTransportePropio" || idForm == "idFrmChkEquipoTransporteAjeno") {
			$("#idErrorFormMateriales").html("").hide();
		}
	}
}

function inicializaAltaPatronalUxMateriales() {
	
	if ($("#noCuetaConTransporte").is(':checked')) {
		$("#idLinkAgregarEquipoTransporte").prop('disabled', true);
		$("#idPanelEquipoTransporte").removeClass('in');
		$("#idChkConTransportePropio").prop("checked", "");
		$("#idChkConTransportePropio").prop('disabled', true);
		$("#idChkConTransporteAjeno").prop('disabled', true);
		$("#idChkConTransporteAjeno").prop("checked", "");
		$("#idChkNoDistribuye").prop('disabled', false);
		$("#idChkNoDistribuye").prop("checked", "checked");
	}
	
	$("#siCuetaConTransporte").change(function() {
		if ($("#siCuetaConTransporte").is(':checked')) {
			$("#idDivHiddenPanelEquipoTransporte").show();
			$("#idSpanRequiredTablaTransporte").show();
			$("#idSpanRequiredChkTRansporte").show();
			$("#idLinkAgregarEquipoTransporte").prop('disabled', false);
			$("#idChkConTransportePropio").prop('disabled', false);
			$("#idChkConTransporteAjeno").prop('disabled', false);
			$("#idChkNoDistribuye").prop('disabled', true);
			$("#idChkNoDistribuye").prop("checked", "");
		} else {
			$("#idLinkAgregarEquipoTransporte").prop('disabled', true);
		}
	});
	
	$("#noCuetaConTransporte").change(function() {
		limpiarForm("idFrmAgregarEquipoTransporte");
		limpiarForm("idFrmTablaEquipoTransporte");
		limpiarForm("idFrmChkEquipoTransportePropio");
		limpiarForm("idFrmChkEquipoTransporteAjeno");
		if ($("#noCuetaConTransporte").is(':checked')) {
			$("#idDivHiddenPanelEquipoTransporte").hide();
			$("#idSpanRequiredTablaTransporte").hide();
			$("#idSpanRequiredChkTRansporte").hide();
			$("#idPanelEquipoTransporte").removeClass('in');
			$("#idLinkAgregarEquipoTransporte").prop('disabled', true);
			$("#idChkConTransportePropio").prop("checked", "");
			$("#idChkConTransportePropio").prop('disabled', true);
			$("#idChkConTransporteAjeno").prop('disabled', true);
			$("#idChkConTransporteAjeno").prop("checked", "");
			$("#idChkNoDistribuye").prop('disabled', false);
			$("#idChkNoDistribuye").prop("checked", "checked");
			if (tabla['equipoTransporte'].fnGetData().length > 0) {
				tabla['equipoTransporte'].fnClearTable();
				contadorRegistrosEquipoTransporte = 0;
				contadorUnidadesEquipoTransporte = 0;
			}
		} else {
			$("#idLinkAgregarEquipoTransporte").prop('disabled', false);
		}
	});
	
	$("#idChkConTransportePropio").change(function() {
		$formChkEquipoTransportePropio.valid();
		$formChkEquipoTransporteAjeno.valid();
		if ($("#idChkConTransportePropio").prop('checked')) {
			if (contadorUnidadesEquipoTransporte < 2) {
				$("#idChkConTransporteAjeno").prop("checked", "");
			}
		}
	});
	
	$("#idChkConTransporteAjeno").change(function() {
		$formChkEquipoTransportePropio.valid();
		$formChkEquipoTransporteAjeno.valid();
		if ($("#idChkConTransporteAjeno").prop('checked')) {
			if (contadorUnidadesEquipoTransporte < 2) {
				$("#idChkConTransportePropio").prop("checked", "");
			}
		}
	});
	
	tabla['materiaPrima'] = crearGrid(idTblMateriasPrimas, columnasTblMateriaPrima, aaDataMateriaPrima);
	tabla['maquinariaEquipo'] = crearGrid(idTblMaquinariaEquipo, columnasTblMqEquipos, aaDataMaquinariaEquipo);
	tabla['equipoTransporte'] = crearGrid(idTblEquipoTransporte, columnasTblTransport, aaDataEquipoTransporte);
}

function validaSize(textarea, size, event) {
	var input = document.getElementById(event.target.id);
	
    if (isTextSelected(input)) {
        return ;
    }

	if (textarea.value.length >= size) {
		textarea.value = textarea.value.substr(0, size);
	}
}

function isTextSelected(input) {
	   var startPos = input.selectionStart;
	   var endPos = input.selectionEnd;
	   var doc = document.selection;

	   if(doc && doc.createRange().text.length != 0){
	      return true;
	   }else if (!doc && input.value.substring(startPos,endPos).length != 0){
	      return true;
	   }
	   return false;
	}

function crearGrid(idGrid, columModel, data) {
	var grid = $(idGrid).dataTable({
		aaData : data,
		bJQueryUI : false,
		bFilter : false,
		bInfo : false,
		bSort : false,
		bPaginate : false,
		bAutoWidth : false,
		bServerSide : false,
		bProcessing : false,
		oLanguage: {"sZeroRecords": "Dar clic en la opci&oacute;n 'Agregar' para capturar la informaci&oacute;n"},
		aoColumns : columModel
	});
	return grid;
}

function fnClickRemoveRowMateriales(idRow, idGrid) {
	var dataTabla;
	
	switch(idGrid) {
		case "#idTblMateriasPrimas" :
			dataTabla= tabla['materiaPrima'].fnGetData( );
			for(var t = 0; t < dataTabla.length; t++) {    
				if (dataTabla[t].id == idRow) {
					tabla['materiaPrima'].fnDeleteRow(t);
					if (tabla['materiaPrima'].fnGetData().length >= 10) {
						$("#idBtnAgregarMateriaPrima").prop('disabled', true);
					} else {
						$("#idBtnAgregarMateriaPrima").prop('disabled', false);
					}
			    }
			}
			break;
		case "#idTblMaquinariaEquipo" :
			dataTabla= tabla['maquinariaEquipo'].fnGetData( );
			for(var t = 0; t < dataTabla.length; t++) {    
				if (dataTabla[t].id == idRow) {
					tabla['maquinariaEquipo'].fnDeleteRow(t);
					if (tabla['maquinariaEquipo'].fnGetData().length >= 10) {
						$("#idBtnAgregarMaquinariaEquipo").prop('disabled', true);
					} else {
						$("#idBtnAgregarMaquinariaEquipo").prop('disabled', false);
					}
			    }
			}
			break;
		case "#idTblEquipoTransporte" :
			dataTabla= tabla['equipoTransporte'].fnGetData( );
			for(var t = 0; t < dataTabla.length; t++) {    
				if (dataTabla[t].id == idRow) {
					contadorUnidadesEquipoTransporte -= parseInt(tabla['equipoTransporte'].fnGetData()[t].numUnidades);
					tabla['equipoTransporte'].fnDeleteRow(t);
					
					if (contadorUnidadesEquipoTransporte <= 1) {
						$("#idChkConTransportePropio").prop("checked", "");
						$("#idChkConTransporteAjeno").prop("checked", "");
					}
					
					if (tabla['equipoTransporte'].fnGetData().length >= 10) {
						$("#idBtnAgregarEquipoTransporte").prop('disabled', true);
					} else {
						$("#idBtnAgregarEquipoTransporte").prop('disabled', false);
					}
			    }
			}
			break;
	}
}

function agregarColumnasTablaMateriales(idEvento) {
	var objeto;
	switch(idEvento) {
		case "idBtnAgregarMateriaPrima":
			if ($formMateriasPrimas.valid()) {
				objeto = {
						"id" : contadorRegistrosMateriasPrimas++,
						"descripcion" : $("#idTxtDesMateriaPrima").val().toUpperCase()
					};
					tabla['materiaPrima'].fnAddData(objeto);
					limpiarForm("idFrmAgregarMateriasPrimas");
					$("#idPanelMateriasPrimas").removeClass('in');
					$formTablaMateriasPrimas.valid();
					$("#idDivTxtAreaMateriasPrimas").find("span").each(function() {
						$(this).remove();
					});
			}
			break;
		case "idBtnAgregarMaquinariaEquipo":
			if ($formMaquinariaEquipo.valid()) {
				objeto = {
						"id" : contadorRegistrosMaquinariaEquipo++,
						"desNombre" : $("#idTxtDesMaquinariaEquipo").val().toUpperCase(),
						"desCapacidadPotencia" : $("#idTxtCapacidadMaquinariaEquipo").val().toUpperCase(),
						"tipo" : {
							"id" : $("#sltTipoMaquinaria").val(),
							"descripcion" : $("#sltTipoMaquinaria option:selected").html()
						},
						"desUso" : $("#idTxtUsoMaquinariaEquipo").val().toUpperCase(),
						"numUnidades" : $("#idTxtUnidadesMaquinariaEquipo").val(),
					};
					tabla['maquinariaEquipo'].fnAddData(objeto);
					limpiarForm("idFrmAgregarMaquinariaEquipo");
					$("#idPanelMaquinariaEquipo").removeClass('in');
					$formTablaMaquinariaEquipo.valid();
			}
			break;
		case "idBtnAgregarEquipoTransporte":
			if ($formEquipoTransporte.valid()) {
				objeto = {
					"id" : contadorRegistrosEquipoTransporte++,
					"desNombre" : $("#idTxtDesEquipoTransporte").val().toUpperCase(),
					"desCapacidadPotencia" : $("#idTxtCapacidadEquipoTransporte").val().toUpperCase(),
					"tipoCombustible" : {
						"clave" : $("#sltTipoCombustibleEquipoTransporte").val(),
						"desTipoCombustible" : $("#sltTipoCombustibleEquipoTransporte option:selected").html()
					},
					"desUso" : $("#idTxtUsoEquipoTransporte").val().toUpperCase(),
					"numUnidades" : $("#idTxtUnidadesEquipoTransporte").val(),
				};
				tabla['equipoTransporte'].fnAddData(objeto);
				contadorUnidadesEquipoTransporte += parseInt($("#idTxtUnidadesEquipoTransporte").val());
				limpiarForm("idFrmAgregarEquipoTransporte");
				$("#idPanelEquipoTransporte").removeClass('in');
				$formTablaEquipoTransporte.valid();
			}
			break;
	}
}

function generaBotonEliminarMateriales(idRow, idGrid) {
	var botonHTML='';
	
	botonHTML += ''
		+'<span class="glyphicon glyphicon-trash" onmouseover="" style="cursor: pointer;" aria-hidden="true" onclick="fnClickRemoveRowMateriales('+idRow+','+idGrid+')"></span>';
	
	return botonHTML;
}

function setCamposObjetoMateriales() {
	var datosMateriaPrima = tabla['materiaPrima'].fnGetData();
	var datosMaquinariaEquipo = tabla['maquinariaEquipo'].fnGetData();
	var datosEquipoTransporte = tabla['equipoTransporte'].fnGetData();
	
	solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.materiaPrimaMateriales = new Array();
	solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.equipos = new Array();
	solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.equiposTransporte = new Array();
	
	var listMateriales = solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.materiaPrimaMateriales;
	var listMaquinaria = solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.equipos;
	var listTransporte = solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.equiposTransporte;
	
	var materiaPrima;
	var maquinariaEquipo;
	var equipoTransporte;
	
	if (datosMateriaPrima != null && datosMateriaPrima.length > 0) {
		$.each(datosMateriaPrima, function(index, value) {
			materiaPrima = new Object();
			
			materiaPrima.idVista = index;
			materiaPrima.sujetoObligado = null;
			materiaPrima.id = null;
			materiaPrima.descripcion = value.descripcion;
			listMateriales.push(materiaPrima);
		});
	}
	
	if (datosMaquinariaEquipo != null && datosMaquinariaEquipo.length > 0) {
		$.each(datosMaquinariaEquipo, function(index, value) {
			maquinariaEquipo = new Object();
			
			maquinariaEquipo.idVista = index;
			maquinariaEquipo.sujetoObligado = null;
			maquinariaEquipo.id = null;
			maquinariaEquipo.desCapacidadPotencia = value.desCapacidadPotencia;
			maquinariaEquipo.desNombre = value.desNombre;
			maquinariaEquipo.desUso = value.desUso;
			maquinariaEquipo.numUnidades = value.numUnidades;
			
			var tipo = new Object();
			tipo.id = value.tipo.id;
			tipo.descripcion = value.tipo.descripcion;
			
			maquinariaEquipo.tipo = tipo;
			listMaquinaria.push(maquinariaEquipo);
		});
	}
	
	if (datosEquipoTransporte != null && datosEquipoTransporte.length > 0) {
		$.each(datosEquipoTransporte, function(index, value) {
			equipoTransporte = new Object();
			
			equipoTransporte.idVista = index;
			equipoTransporte.sujetoObligado = null;
			equipoTransporte.id = null;
			equipoTransporte.desCapacidadPotencia = value.desCapacidadPotencia;
			equipoTransporte.desNombre = value.desNombre;
			equipoTransporte.desUso = value.desUso;
			equipoTransporte.numUnidades = value.numUnidades;
			
			var tipoCombustible = new Object();
			tipoCombustible.clave = value.tipoCombustible.clave;
			tipoCombustible.desTipoCombustible = value.tipoCombustible.desTipoCombustible;
			
			equipoTransporte.tipoCombustible = tipoCombustible;
			
			listTransporte.push(equipoTransporte);
		});
	}
	
	solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.clasificacion.indDistribuyeEntrega = !$("#idChkNoDistribuye").prop('checked') ? 0 : 1;
	solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.clasificacion.indServiciosATerceros = !$("#idChkServInstalacion").prop('checked') ? 0 : 1;
	solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.clasificacion.indTransporteAjeno = !$("#idChkConTransporteAjeno").prop('checked') ? 0 : 1;
	solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.clasificacion.indTransportePropio = !$("#idChkConTransportePropio").prop('checked') ? 0 : 1;
	
	solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.clasificacion.indDistribuyeEntrega = !$("#idChkNoDistribuye").prop('checked') ? 0 : 1;
	solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.clasificacion.indServiciosATerceros = !$("#idChkServInstalacion").prop('checked') ? 0 : 1;
	solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.clasificacion.indTransporteAjeno = !$("#idChkConTransporteAjeno").prop('checked') ? 0 : 1;
	solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.clasificacion.indTransportePropio = !$("#idChkConTransportePropio").prop('checked') ? 0 : 1;
	
}