var idTblRecursosHumanos = "#idTblRecursosHumanos";

var $formRecursosHumanos;
var $formTablaRecursosHumanos;

var contadorRegistrosRecursosHumanos = 0;

var tabla = {
		recursosHumanos : null,
	};

var aaDataRecursosHumanos = [];

var columnasTblRecursosHumanos = [ {
	mDataProp : "clave",
	sTitle : "",
	bVisible : false
}, {
	mDataProp : "numTrabajadores",
	sTitle : "No. Trabajadores",
	bVisible : true,
        sWidth: "125px",
	size : "5"
}, {
	mDataProp : "oficioOcupacion",
	sTitle : "Oficio u ocupaci&oacute;n",
	bVisible : true,
        sWidth: "870px",
	size : "50"
},{
	sTitle : "Eliminar",
	bSortable : false,
	size:"5",
	sClass : "dt-center",
	fnRender : function ( objeto, val) {
					return generaBotonEliminarRecursosHumanos(objeto.aData.clave, 'idTblRecursosHumanos');
				}
} ];

$(document).ready(function() {
	
	$formRecursosHumanos = $("#idFrmAgregarRecursosHumanos");
	$formTablaRecursosHumanos = $("#idFrmTablaRecursosHumanos");
	
	$("#idBtnAgregarRecursosHumanos").click(function(event) {
		agregarColumnasTablaRecursosHumanos(event.currentTarget.id);
	});
	
	$("#idLinkAgregarRecursosHumanos").click(function(event) {
		if ($("#idPanelRecursosHumanos").hasClass('in')) {
			limpiarForm("idFrmAgregarRecursosHumanos");
			$("#idDivTxtAreaRecursosHumanos").find("span").each(function() {
				$(this).remove();
			});
		} else {
			if (tabla['recursosHumanos'].fnGetData().length >= 15) {
				$("#idBtnAgregarRecursosHumanos").prop('disabled', true);
			} else {
				$("#idBtnAgregarRecursosHumanos").prop('disabled', false);
			}
		}
	});
	
	$("#idBtnSiguienteRecursosHumanos").click(function(event) {
		if ($formTablaRecursosHumanos.valid()) {
			setCamposObjetoRecursosHumanos();
			paginaSiguiente();
		}
	});
	
	$("#idBtnAtrasRecursosHumanos").click(function(event) {
		paginaPrevia();
	});
	
	intReglaValidacionTablaRecursosHumanos();
	inicializaAltaPatronalUxRecursosHumanos();
	initValidatorRecursosHumanos();
	intValidacionTablaRecursosHumanos();
});

/**
 * Metodo para iniciarlizar el validador del formulario de materias primas
 */
var initValidatorRecursosHumanos = function() {
	$formRecursosHumanos.validate($.extend({}, DEFAULTS_VALIDATE, {
		verifyErrors: function(existError) {
			mostrarMensajeErrorGenerico($formRecursosHumanos.attr("id"), existError, MENSAJE_ERROR_FORM);
			marcarAsteriscos($formRecursosHumanos, ".errorDocs", ".col-sm-9");
		},
		rules: {
			txtNumTrabajadores: {
				required: true,
				number : true,
				maxlength: 5,
				min: 1
			},
			txtOficioOcupacion: {
				required: true,
				maxlength: 50
			}
		}
	}));
}

function intValidacionTablaRecursosHumanos() {
	$formTablaRecursosHumanos.validate($.extend({}, DEFAULTS_VALIDATE, {
		verifyErrors: function(existError) {
			mostrarMensajeErrorRecursosHumanos($formTablaRecursosHumanos.attr("id"), existError, "<strong>Error en el formulario!</strong> no ha llenado todos los campos requeridos. Por favor verifique");
			marcarAsteriscos($formTablaRecursosHumanos, ".errorDocs", "form");
		},
		rules: {
			hdnValidaTablaRecursosHumanos: {
				validaTablaRecursosHumanos : true
			}
		},
		ignore: "",//esta propiedad se sobre escribe para que puedaas usar campos hidden
		errorElement: "span"
	}));
	
}

function intReglaValidacionTablaRecursosHumanos() {
	$.validator.addMethod("validaTablaRecursosHumanos", function(value, elem, param) {
		if (tabla['recursosHumanos'].fnGetData( ).length > 0) {
			return true;
		} else {
			return false;
		}
	},"Recursos Humanos requeridos.");
}

var mostrarMensajeErrorRecursosHumanos = function(idForm, mostrar, mensaje) {
	if (mostrar) {
		if (idForm == "idFrmTablaRecursosHumanos") {
			$("#idErrorFormRecursosHumanos").html(mensaje).show();
		}
	} else {
		if (idForm == "idFrmTablaRecursosHumanos") {
			$("#idErrorFormRecursosHumanos").html("").hide();
		}
	}
}

function inicializaAltaPatronalUxRecursosHumanos() {
	tabla['recursosHumanos'] = crearGrid(idTblRecursosHumanos, columnasTblRecursosHumanos, aaDataRecursosHumanos);
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

function fnClickRemoveRowRecursosHumanos(idRow, idGrid) {
	var dataTabla;
	
	switch(idGrid) {
		case "#idTblRecursosHumanos" :
			dataTabla= tabla['recursosHumanos'].fnGetData( );
			for(var t = 0; t < dataTabla.length; t++) {    
				if (dataTabla[t].clave == idRow) {
					tabla['recursosHumanos'].fnDeleteRow(t);
					if (tabla['recursosHumanos'].fnGetData().length >= 15) {
						$("#idBtnAgregarRecursosHumanos").prop('disabled', true);
					} else {
						$("#idBtnAgregarRecursosHumanos").prop('disabled', false);
					}
			    }
			}
			break;
	}
}

function agregarColumnasTablaRecursosHumanos(idEvento) {
	var objeto;
	switch(idEvento) {
		case "idBtnAgregarRecursosHumanos":
			if ($formRecursosHumanos.valid()) {
				objeto = {
						"clave" : contadorRegistrosRecursosHumanos++,
						"numTrabajadores" : $("#idTxtNumTrabajadores").val(),
						"oficioOcupacion" : $("#idTxtOficioOcupacion").val().toUpperCase(),
					};
					tabla['recursosHumanos'].fnAddData(objeto);
					limpiarForm("idFrmAgregarRecursosHumanos");
					$("#idPanelRecursosHumanos").removeClass('in');
					$formTablaRecursosHumanos.valid();
			}
			break;
	}
}

function generaBotonEliminarRecursosHumanos(idRow, idGrid) {
	var botonHTML='';
	
	botonHTML += ''
		+'<span class="glyphicon glyphicon-trash" onmouseover="" style="cursor: pointer;" aria-hidden="true" onclick="fnClickRemoveRowRecursosHumanos('+idRow+','+idGrid+')"></span>';
	
	return botonHTML;
}

function setCamposObjetoRecursosHumanos() {
	var datosRecursosHumanos = tabla['recursosHumanos'].fnGetData();
	
	solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.personal = new Array();
	
	var listPersonal = solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.personal;
	var personal;
	
	if (datosRecursosHumanos != null && datosRecursosHumanos.length > 0) {
		$.each(datosRecursosHumanos, function(index, value) {
			personal = new Object();
			
			personal.idVista = index;
			personal.sujetoObligado = null;
			personal.numTrabajadores = value.numTrabajadores;
			personal.oficioOcupacion = value.oficioOcupacion;
			
			listPersonal.push(personal);
		});
	}
	
}