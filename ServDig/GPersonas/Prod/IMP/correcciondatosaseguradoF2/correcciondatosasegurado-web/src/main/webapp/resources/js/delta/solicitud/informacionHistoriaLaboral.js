$(document).ready(
function() {	
	var indiceHistoriaLaboral = ($('#datosHistoriaLaboralGrid tr').length - 1);

	$().dateSelectBoxes({
		monthElement : $('#mesFechaInscripcion'),
		dayElement : $('#diaFechaInscripcion'),
		yearElement : $('#anioFechaInscripcion'),
		monthLabel : "Mes",
		yearLabel : "A\u00F1o",
		dayLabel : "D\u00EDa",
		generateOptions : true,
		keepLabels : true,
		Default: false
	});

	$().dateSelectBoxes({
		monthElement : $('#mesFechaBaja'),
		dayElement : $('#diaFechaBaja'),
		yearElement : $('#anioFechaBaja'),
		monthLabel : "Mes",
		yearLabel : "A\u00F1o",
		dayLabel : "D\u00EDa",
		generateOptions : true,
		keepLabels : true
	});

	$('#agregarHistoriaLaboral').click(
			function() {				
				var registroNombrePatron = $("#registroNombrePatron").val().toUpperCase();
				var registroEntidadFederativa = $("#registroEntidadFederativa").val() === '-1' ? '': $("#registroEntidadFederativa option:selected").text();
                                var entidadFederativaId = $("#registroEntidadFederativa").val() === '-1' ? '': $("#registroEntidadFederativa option:selected").val();
				var registroFechaInscripcion = obtenerFecha("FechaInscripcion");
				
				if($("#registroNumeroRegistroPatronal").val()==="")
					{
						$("#registroNumeroRegistroPatronal").val("N/D");
					}
        	
				
                                if($('#idVigente').is(':checked')) {
                                    var registroFechaBaja ="Vigente a la fecha";
                                }else{
                                    var registroFechaBaja = obtenerFecha("FechaBaja");
                                }
				var registroNumeroRegistroPatronal = $("#registroNumeroRegistroPatronal").val().toUpperCase();
				var registroActividadEmpresa = $("#registroActividadEmpresa").val().toUpperCase();
				var registroDomicilioEmpresa = $("#registroDomicilioEmpresa").val().toUpperCase();

				fnHideErrores("form#informacionHistoriaLaboralForm");
				fnHideErroresInput("form#informacionHistoriaLaboralForm");
			
				$.blockUI();
				$.ajax({
						url : contextPath
							+ '/wizard/correccionDatosAsegurado/validar/historiaLaboral',
						type : 'post',
						async : false,
						dataType : 'json',
						contentType : "application/json; charset=utf-8",
						data : JSON.stringify({
									nombrePatron : registroNombrePatron,
									entidadFederativa : registroEntidadFederativa,
									fechaInscripcion : registroFechaInscripcion,
									fechaBaja : registroFechaBaja,
									numeroRegistroPatronal : registroNumeroRegistroPatronal,
									actividadEmpresa : registroActividadEmpresa,
									domicilioEmpresa : registroDomicilioEmpresa,
                                                                        claveEntidad: entidadFederativaId
								}),
						success : function(response) {
							var arrayCampos = [
									registroNombrePatron,
									registroEntidadFederativa,
									registroFechaInscripcion,
									registroFechaBaja,
									registroNumeroRegistroPatronal,
									registroActividadEmpresa,
									registroDomicilioEmpresa];
							fnCrearRow(arrayCampos,++indiceHistoriaLaboral,'datosHistoriaLaboralGrid',entidadFederativaId);
							limpiarCamposAgregados(['registroNombrePatron','registroEntidadFederativa','registroNumeroRegistroPatronal','registroActividadEmpresa','registroDomicilioEmpresa']);
                                                        $("#idVigente").removeAttr('checked');
							limpiarSelectsFechas(['FechaInscripcion','FechaBaja' ]);
                                                        setDisableFecha(false);
							$.unblockUI();
						},
						error : function(error) {
							fnProcesarErrores(error,"form#informacionHistoriaLaboralForm");
							$.unblockUI();
						}
					});
				$.unblockUI();
			});
	$('#continuarInformacionHistoriaLaboral').click(function() {		
		fnHideErrores("form#informacionHistoriaLaboralForm");
		fnHideErroresInput("form#informacionHistoriaLaboralForm");
		var gridHistoriaLaboral = fnCrearList('datosHistoriaLaboralGrid', 'informacionHistoriaLaboralForm', 'historiaLaboralGrid', 7);
		var datosHistoriaLaboralVO= {}
		datosHistoriaLaboralVO["historiaLaboralGrid"] = gridHistoriaLaboral;
		limpiarCamposAgregados(['registroNombrePatron','registroEntidadFederativa','registroNumeroRegistroPatronal','registroActividadEmpresa','registroDomicilioEmpresa']);
		limpiarSelectsFechas(['FechaInscripcion', 'FechaBaja']);
		$.blockUI();
		$.ajax({
				url : contextPath
					+ '/wizard/correccionDatosAsegurado/validar/infHistoriaLaboral',
				type : 'post',
				async : false,
				dataType : 'json',
				contentType : "application/json; charset=utf-8",
				data : JSON.stringify(datosHistoriaLaboralVO),
				success : function(response) {
					fnCrearCamposHidden('datosHistoriaLaboralGrid', 'informacionHistoriaLaboralForm', 'historiaLaboralGrid', 7);	
					document.charset = 'ISO-8859-1';
					$('#informacionHistoriaLaboralForm').submit();					
					$.unblockUI();
				},
				error : function(error) {
					fnProcesarErrores(error,"form#informacionHistoriaLaboralForm");
					$.unblockUI();
				}
			});
		$.unblockUI();
	});
        
    

    $('#idVigente').change(function() {
        setDisableFecha(this.checked);
    });
});

var setDisable = function (select, bandera) {
    console.log(bandera)
    if (bandera) {
        $("#"+select).attr("disabled", true);
    } else {
        $("#"+select).removeAttr("disabled");
    }
};


function setDisableFecha(bandera){
    if (bandera) {
            $('#diaFechaBaja').val(0);
            $('#mesFechaBaja').val(0);
            $('#anioFechaBaja').val(0);
            setDisable("diaFechaBaja",true);
            setDisable("mesFechaBaja",true);
            setDisable("anioFechaBaja",true);
        } else {
            setDisable("diaFechaBaja",false);
            setDisable("mesFechaBaja",false);
            setDisable("anioFechaBaja",false);
        }
}

var fnEliminarRow = function(idRow) {
    console.log(idRow);
    var registroNombrePatron; 
    var registroEntidadFederativa;
    var registroFechaInscripcion;
    var registroFechaBaja;
    var registroNumeroRegistroPatronal;
    var registroActividadEmpresa;
    var registroDomicilioEmpresa;
        $('#'+ idRow +" td").each(function (index) {
            
                    switch(index){
                        case 0:
                            registroNombrePatron=$(this).text();
                        break;
                        case 1:
                            registroEntidadFederativa=$(this).text();
                        break;
                        case 2:
                            registroFechaInscripcion=$(this).text();
                        break;
                        case 3:
                            registroFechaBaja=$(this).text();
                        break;
                        case 4:
                            registroNumeroRegistroPatronal=$(this).text();
                        break;
                        case 5:
                            registroActividadEmpresa=$(this).text();
                        break;
                        case 6:
                            registroDomicilioEmpresa=$(this).text();
                        break;
                    }
                console.log($(this).text());
        });
	
	console.log(idRow);
	$.blockUI();
	$.ajax({
                url : contextPath
                        + '/wizard/correccionDatosAsegurado/actualizarLista',
                type : 'post',
                async : false,
                dataType : 'json',
                contentType : "application/json; charset=utf-8",
                data : JSON.stringify({
                        nombrePatron : registroNombrePatron,
                        entidadFederativa : registroEntidadFederativa,
                        fechaInscripcion : registroFechaInscripcion,
                        fechaBaja : registroFechaBaja,
                        numeroRegistroPatronal : registroNumeroRegistroPatronal,
                        actividadEmpresa : registroActividadEmpresa,
                        domicilioEmpresa : registroDomicilioEmpresa}),
            success : function(response) {
                $('#'+ idRow).remove();
                indexarListaHistorial();
        },
            error : function(error) {
                    fnProcesarErrores(error,"form#informacionHistoriaLaboralForm");
                    $.unblockUI();
            }
        });
	$.unblockUI(); 
};

function indexarListaHistorial(){
	$('#datosHistoriaLaboralGrid tr').each(function(index){
            var indicetotal=index;
                $(this).attr('id','datosHistoriaLaboralGrid'+indicetotal);
                var element=$(this).children('td:last');
                element.children("a").attr('onclick','return fnEliminarRow(\'' + 'datosHistoriaLaboralGrid'+indicetotal
                            + '\')');
	});
}

var fnCrearRow = function(arrayCampos, rowCount, tableName,idEntidadFed) {
	var idTr = tableName + rowCount;
	var trHtml = '<tr id=\'' + idTr + '\'>';
	for (var i = 0; i < arrayCampos.length; i++) {
		trHtml += '<td style="word-wrap: break-word">' + arrayCampos[i] + '</td>';
	}
	trHtml += '<td> <a href="#" onclick="return fnEliminarRow(\'' + idTr
			+ '\');" >Eliminar</a> <input id="idEntidadFederativa" type="hidden" value="'+idEntidadFed+'"></td></tr>';

	$('#' + tableName + ' tbody').append(trHtml);
};

var fnCrearRowHidden = function(arrayCampos, arrayCamposHidden, rowCount,
		tableName, tipoDocto) {
	var idTr = tableName + rowCount;
	var trHtml = '<tr id=\'' + idTr + '\'>';
	for (var i = 0; i < arrayCampos.length; i++) {
		trHtml += '<td>' + arrayCampos[i] + '</td>';
	}
	for (var i = 0; i < arrayCamposHidden.length; i++) {
		trHtml += '<td hidden="hidden">' + arrayCamposHidden[i] + '</td>';
	}
	trHtml += '<td hidden="hidden">' +tipoDocto+ '</td>';
	trHtml += '<td> <a href="#" onclick="return fnEliminarRow(\'' + idTr
			+ '\','+tipoDocto+');" >Eliminar</a> </td></tr>';

	$('#' + tableName + ' tbody').append(trHtml);
	//indexarListaDocumento();
};


var fnCrearList = function(idTable, idFrom, parameterList, numeroCampos) {
	var arrayCampos = [];
	var arrayList = [];
	$("#" + idTable + " tbody tr th").each(function(index) {
		if (arrayCampos.length < numeroCampos) {
			arrayCampos.push($(this).attr("name"));
		}
	});
	$("#" + idTable + " tbody tr").each(function(index) {
		$(this).children("td").each(
			function(index2) {
				var entry = {};
				if (index2 < numeroCampos) {
					entry[arrayCampos[index2]] = $(this).text();
				}
				arrayList.push(entry);
			});
	});
	return arrayList;
};


var fnCrearCamposHidden = function(idTable, idFrom, parameterList, numeroCampos) {
	var arrayCampos = [];
	$("#" + idTable + " tbody tr th").each(function(index) {
		if (arrayCampos.length <= numeroCampos) {
			var nombre=$(this).attr("name");
                        if("acciones"===nombre){
                            nombre="claveEntidad";
                            console.log("entro");
                            
                        }
                        arrayCampos.push(nombre);
		}
	});

	$("#" + idTable + " tbody tr").each(
			function(index) {
				$(this).children("td").each(
						function(index2) {
							if (index2 < numeroCampos) {
								$('#' + idFrom).append(
										'<input type="hidden" name="'
												+ parameterList + '['
												+ (index - 1) + '].'
												+ arrayCampos[index2]
												+ '" value="' + $(this).text()
												+ '"/>');
							}else if(index2==numeroCampos){
                                                            var input=$(this).find("input").val();
                                                            $('#' + idFrom).append('<input type="hidden" name="'
												+ parameterList + '['
												+ (index - 1) + '].'
												+ arrayCampos[index2]
												+ '" value="' + input
												+ '"/>');
                                                        }
						});
			});
};

var limpiarCamposAgregados = function(arrayCamposLimpiar) {
	jQuery.each(arrayCamposLimpiar, function(i, val) {
		$("#" + val).val("");
	});
};

var limpiarSelectsFechas = function(arrayCamposLimpiar) {
	jQuery.each(arrayCamposLimpiar, function(i, val) {
		$("#dia" + val).val("0");
		$("#mes" + val).val("0");
		$("#mes" + val).change();
		$("#anio" + val).val("0");
	});
};

var obtenerFecha = function(fecha) {
	var banderaDia = false;
	var formatoFecha = "";

	if ($("#dia" + fecha).val() === '0') {
		formatoFecha = "";
	} else {
		formatoFecha = ($("#dia" + fecha).val().length === 1 ? '0'+ $("#dia" + fecha).val() + "/" : $("#dia" + fecha).val() + "/");
		banderaDia = true;
	}

	if (banderaDia) {
		formatoFecha += $("#mes" + fecha).val().length === 1 ? '0'+ $("#mes" + fecha).val() + "/" : $("#mes" + fecha).val() + "/";

	} else {
		formatoFecha += $("#mes" + fecha).val() === '0' ? '': ($("#mes" + fecha).val().length === 1 ? '0'
						+ $("#mes" + fecha).val() + "/" : $("#mes" + fecha).val()+ "/");
	}
	formatoFecha += $("#anio" + fecha).val() === "0" ? $("#anio" + fecha).val(): $("#anio" + fecha + " option:selected").text();
	return formatoFecha;
};