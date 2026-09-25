$(document)
        .ready(
                function () {
                    document.charset = 'utf-8';
                    var indiceNSS = ($('#listadoNSSInvolucradosGrid tr').length - 1);

                    desactivarSelectDocumentoAdjunto();
                    var indiceDocumentoProbatorio = ($('#listadoDocumentosGrid tr').length - 1);
                    $('#agregarNSS')
                            .click(
                                    function () {
                                        var registroNSS = $("#registroNSS")
                                                .val();
                                        fnHideErrores("form#datosHistoriaLaboralForm");
                                        var propiedad = "font-size";
                                        var valor = $("#registroNSS").css(propiedad);
                                        fnHideErroresInput("form#datosHistoriaLaboralForm");
                                        $("#registroNSS").css(propiedad, valor);
                                        $.blockUI();
                                        $
                                                .ajax({
                                                    url: contextPath
                                                            + '/wizard/correccionDatosAsegurado/validar/NSS/'
                                                            + ($('#listadoNSSInvolucradosGrid tr').length - 1),
                                                    type: 'post',
                                                    async: false,
                                                    dataType: 'json',
                                                    contentType: "application/json; charset=utf-8",
                                                    data: JSON.stringify({
                                                        nss: registroNSS
                                                    }),
                                                    success: function (response) {
                                                        var table = document.getElementById('listadoNSSInvolucradosGrid');
                                                        var rowCount = table.rows.length;
                                                        var arrayCampos = [
                                                            rowCount,
                                                            registroNSS];
                                                        fnCrearRow(arrayCampos,
                                                                rowCount,
                                                                'listadoNSSInvolucradosGrid');
                                                        limpiarCamposAgregados(["registroNSS"]);
                                                        var indiceNSS = ($('#listadoNSSInvolucradosGrid tr').length - 1);
                                                        desactivarOptgroupSelectDocumento(claveDocumentoNSS, indiceNSS * 10);
                                                        $.unblockUI();
                                                    },
                                                    error: function (error) {
                                                        fnProcesarErrores(
                                                                error,
                                                                "form#datosHistoriaLaboralForm");
                                                        $.unblockUI();
                                                    }
                                                });
                                        $.unblockUI();
                                    });

                    $('#agregarDocumento')
                            .click(
                                    function () {
                                        if ($("#registroDocumentoProbatorio").val() == '-1') {
                                            mensageConfirmacion('Tipo de archivo no v\u00e1lido, seleccione un documento v\u00e1lido');
                                            return;
                                        }
                                        var registroDocumentoProbatorio = $(
                                                "#registroDocumentoProbatorio option:selected")
                                                .text();
                                        var tipoDocumento = $(
                                                "#registroDocumentoProbatorio :selected").parent().attr("value");

                                        var idDocumentoPorTipo = $(
                                                "#registroDocumentoProbatorio :selected").attr("docportipo");

                                        var desDocumento = $(
                                                "#registroDocumentoProbatorio")
                                                .val();
                                        var ext = $("#fileData").val().split(
                                                "\\");
                                        var nombreArchivo = ext[ext.length - 1];
                                        nombreArchivo = (idDocumentoPorTipo + "_").concat(nombreArchivo);
                                        var arrayCampos = [
                                            ++indiceDocumentoProbatorio,
                                            registroDocumentoProbatorio];
                                        var arrayCamposHidden = [
                                            nombreArchivo, desDocumento, idDocumentoPorTipo];
                                        var ext = $("#fileData").val();
                                        var n = ext.split("\\");
                                        var nombreExtension = n[n.length - 1];
                                        n = nombreExtension.split(".");
                                        nombreExtension = n[n.length - 1];
                                        var urlDocumento = contextPath
                                                + "/wizard/correccionDatosAsegurado/uploadify";
                                        if (nombreExtension == 'gif'
                                                || nombreExtension == 'tif'
                                                || nombreExtension == 'jpg'
                                                || nombreExtension == 'png'
                                                || nombreExtension == 'pdf') {
                                            nombreExtension = "";

                                            valida_docto = {'idDocPorTipo': idDocumentoPorTipo};
                                            $.blockUI();
                                            $
                                                    .ajaxFileUpload({
                                                        url: urlDocumento,
                                                        secureuri: false,
                                                        fileElementId: 'fileData',
                                                        data: valida_docto,
                                                        mensajeSuccess: 'Documento adjuntado exitosamente <br>BP-5001 - Operaci\u00f3n realizada con \u00e9xito',
                                                        mensajeError: 'Error al adjuntar Documento',
                                                        error: function (data, status) {
                                                            $.unblockUI();
                                                    $("#registroDocumentoProbatorio").val(-1);
                                            limpiarCamposAgregados(["fileData"]);
                                                        },
                                                        success: function (data, status) {
                                                            $.unblockUI();
                                                            var idDocBoveda = JSON.parse(data.responseText)['idDocBoveda'];
                                                            arrayCamposHidden.push(idDocBoveda);
                                                            fnCrearRowHidden(
                                                                    arrayCampos,
                                                                    arrayCamposHidden,
                                                                    indiceDocumentoProbatorio,
                                                                    'listadoDocumentosGrid', tipoDocumento);
                                                            if (tipoDocumento === claveDocumentoNSS) {
                                                                //Agregar validacion aqui si se requiere verificar num de Doctos probatorios por NSS
                                                                desactivarSelectDocumento();
                                                            } else {
                                                                desactivarSelectDocumento();
                                                            }
                                                            $(
                                                                    "#registroDocumentoProbatorio")
                                                                    .val(-1);
                                                            limpiarCamposAgregados(["fileData"]);

                                                        }

                                                    });
                                        } else {
                                            mensageConfirmacion('El archivo no cumple con el tipo de formato permitido [.pdf, .jpg, .jpeg, .png, .gif, .tif, u otro tipo de imagen con algoritmo de compresi\u00f3n]. No es posible adjuntar el archivo.');

                                        }
                                    });

                    $('#continuarDatosHistoriaLaboral').click(
                            function () {

                                fnCrearCamposHiddenNotTH(
                                        'listadoNSSInvolucradosGrid',
                                        'datosHistoriaLaboralForm', 'NSSList',
                                        'NSS', 1);
                                fnCrearCamposHiddenNotTHDocumentos(
                                        'listadoDocumentosGrid',
                                        'datosHistoriaLaboralForm',
                                        'documentoProbatorioList', [
                                            'desDocumento', 'nombre',
                                            'cveIdDocumento', 'idDocumentoPorTipo', 'idDocBoveda', 'tipoDocumento']);
                                document.charset = 'ISO-8859-1';
                                $('#datosHistoriaLaboralForm').submit();

                            });
                });

var fnEliminarRowNss = function (idRow, tipoDocto) {

    var item = $('#' + idRow);
    idRow = $("#listadoNSSInvolucradosGrid tr").index(item);
    idRow = idRow - 1;

    $.blockUI();
    $.ajax({
        url: contextPath
                + '/wizard/correccionDatosAsegurado/actualizarLista',
        type: 'post',
        async: false,
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({'idRow': '' + idRow, 'view': 'datosHistoriaLaboral', 'type': 'listadoNSSInvolucradosGrid'}),
        success: function (response) {
            $(item).remove();
            if (tipoDocto != undefined && tipoDocto != claveDocumentoNSS) {
                activarSelectDocumento(tipoDocto);
            } else {
                var indiceNSS = ($('#listadoNSSInvolucradosGrid tr').length - 1);
                desactivarOptgroupSelectDocumento(claveDocumentoNSS, indiceNSS * 10);
            }
            indexarListaDocumento();
        },
        error: function (error) {
            fnProcesarErrores(error, "form#datosHistoriaLaboralForm");
            $.unblockUI();
        }
    });
    $.unblockUI();
    return false;
};

var fnEliminarRow = function (idRow, tipoDocto, idDocBoveda) {

    var item = $('#' + idRow);
    idRow = $("#listadoDocumentosGrid tr").index(item);
    idRow = idRow - 1;

    $.blockUI();
    $.ajax({

        url: contextPath + '/wizard/correccionDatosAsegurado/eliminarDocumentoBoveda',
        type: 'post',
        async: true,
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({'idDocBoveda': idDocBoveda, 'idRow': '' + idRow, 'view': 'datosHistoriaLaboral', 'type': 'listadoDocumentosGrid'}),
        success: function (response) {
            if (response.success) {
                $(item).remove();
                if (tipoDocto != undefined && tipoDocto != claveDocumentoNSS) {
                    activarSelectDocumento(tipoDocto);
                } else {
                    var indiceNSS = ($('#listadoNSSInvolucradosGrid tr').length - 1);
                    desactivarOptgroupSelectDocumento(claveDocumentoNSS, indiceNSS * 10);
                }
                indexarListaDocumento();
                $.unblockUI();
                mensageConfirmacion(response.success);
            } else {
                $.unblockUI();
                mensageConfirmacion(response.error);
            }
        },
        error: function (error) {
            $.unblockUI();
            if (error.status === 500) {
                mensageConfirmacion("EX-500 - Error al eliminar el Documento.");
            }
        }

    });
    return false;
};

function indexarListaDocumento() {
    $('#listadoDocumentosGrid tr').each(
            function (index) {
                $(this).children(' td:first').html('<p style="font-size: 1.5em;">' + index + "</p>");
            });
}

var fnCrearRow = function (arrayCampos, rowCount, tableName) {
    var idTr = tableName + rowCount;
    var trHtml = '<tr id=\'' + idTr + '\'>';
    for (var i = 0; i < arrayCampos.length; i++) {
        trHtml += '<td width="5%" nowrap><p style="font-size: 1.5em;">' + arrayCampos[i] + '</p></td>';
    }
    trHtml += '<td align="right" width="90%" height="55"> <a href="#" onclick="return fnEliminarRowNss(\'' + idTr
            + '\');" ><p style="font-size: 1.5em;">Eliminar</p></a> </td></tr>';

    $('#' + tableName + ' tbody').append(trHtml);
};

var fnCrearRowHidden = function (arrayCampos, arrayCamposHidden, rowCount,
        tableName, tipoDocto) {
    var idDocBoveda = arrayCamposHidden[arrayCamposHidden.length - 1];
    var idTr = tableName + rowCount;
    var trHtml = '<tr id=\'' + idTr + '\'>';
    for (var i = 0; i < arrayCampos.length; i++) {
        trHtml += '<td width="5%" nowrap><p style="font-size: 1.5em;">' + arrayCampos[i] + '</p></td>';
    }
    for (var i = 0; i < arrayCamposHidden.length; i++) {
        trHtml += '<td hidden="hidden">' + arrayCamposHidden[i] + '</td>';
    }
    trHtml += '<td hidden="hidden">' + tipoDocto + '</td>';
    trHtml += '<td align="right" width="90%" height="55"> <a href="#" onclick="return fnEliminarRow(\'' + idTr
            + '\',' + tipoDocto + ',\'' + idDocBoveda + '\');" ><p style="font-size: 1.5em;">Eliminar</p></a> </td></tr>';

    $('#' + tableName + ' tbody').append(trHtml);
    indexarListaDocumento();
};

var fnCrearCamposHidden = function (idTable, idFrom, parameterList, numeroCampos) {
    var arrayCampos = [];
    $("#" + idTable + " tbody tr th").each(function (index) {
        if (arrayCampos.length < numeroCampos) {
            arrayCampos.push($(this).attr("name"));
        }
    });

    $("#" + idTable + " tbody tr").each(
            function (index) {
                $(this).children("td").each(
                        function (index2) {
                            if (index2 < numeroCampos) {
                                $('#' + idFrom).append(
                                        '<input type="hidden" name="'
                                        + parameterList + '['
                                        + (index - 1) + '].'
                                        + arrayCampos[index2]
                                        + '" value="' + $(this).text()
                                        + '"/>');
                            }
                        });
            });
};

var fnCrearCamposHiddenNotTHDocumentos = function (idTable, idFrom,
        parameterList, arrayNombreCampos) {
    $("#" + idTable + " tbody tr").each(
            function (index) {
                $(this).children("td").each(
                        function (index2) {
                            var attr = $(this).attr('hidden');
                            if (typeof attr !== typeof undefined
                                    && attr !== false || index2 === 1) {
                                $('#' + idFrom).append(
                                        '<input type="hidden" name="'
                                        + parameterList + '['
                                        + (index - 1) + '].'
                                        + arrayNombreCampos[index2 - 1]
                                        + '" value="' + $(this).text()
                                        + '"/>');
                            }
                        });
            });
};

var fnCrearCamposHiddenNotTH = function (idTable, idFrom, parameterList,
        nombreCampo, posicionInformacion) {
    $("#" + idTable + " tbody tr").each(
            function (index) {
                $(this).children("td").each(
                        function (index2) {
                            if (index2 === posicionInformacion) {
                                $('#' + idFrom).append(
                                        '<input type="hidden" name="'
                                        + parameterList + '['
                                        + (index - 1) + '].'
                                        + nombreCampo + '" value="'
                                        + $(this).text() + '"/>');
                            }
                        });
            });
};

var contarCamposDocumentoNSS = function (idTable, idFrom, posicionInformacion) {
    var contador = 0;
    if ($("#" + idTable + " tbody tr").length > 1) {
        $("#" + idTable + " tbody tr")
                .each(
                        function (index) {
                            $(this)
                                    .children("td")
                                    .each(
                                            function (index2) {
                                                if (index2 === posicionInformacion
                                                        && $(this).text() === claveDocumentoNSS) {
                                                    contador++;
                                                }

                                            });
                        });
    }
    indexarListaDocumento();
    return contador;
};

var limpiarCamposAgregados = function (arrayCamposLimpiar) {
    jQuery.each(arrayCamposLimpiar, function (i, val) {
        $("#" + val).val("");
    });
};

var desactivarActivarSelectDocumentoNSS = function (claveDocumentoNSS) {
    var indiceNSS = ($('#listadoNSSInvolucradosGrid tr').length - 1);
    var documentosNSSActuales = contarCamposDocumentoNSS(
            "listadoDocumentosGrid", "datosHistoriaLaboralForm", claveDocumentoNSS);
    if (documentosNSSActuales >= indiceNSS) {
        $(
                "#registroDocumentoProbatorio option[value='"
                + claveDocumentoNSS + "']").attr("disabled", true);
    } else {
        $(
                "#registroDocumentoProbatorio option[value='"
                + claveDocumentoNSS + "']").removeAttr("disabled");
    }
};

function desactivarSelectDocumento() {
    var tipoDoctoSeleccionado = $("#registroDocumentoProbatorio :selected").parent().attr("value");
    console.log("Tipo documento seleccionado:" + tipoDoctoSeleccionado + " Clave docto NSS" + claveDocumentoNSS);
    if (tipoDoctoSeleccionado != claveDocumentoNSS) {
        $("#datosHistoriaLaboralForm optgroup[value='" + tipoDoctoSeleccionado + "'] option").attr("disabled", true);
    } else {
        var indiceNSS = ($('#listadoNSSInvolucradosGrid tr').length - 1);
        desactivarOptgroupSelectDocumento(claveDocumentoNSS, indiceNSS * 10);
    }
}
;

function activarSelectDocumento(tipoDocumento) {
    $("#datosHistoriaLaboralForm optgroup[value='" + tipoDocumento + "'] option").attr("disabled", false);
}

function desactivarSelectDocumentoAdjunto() {
    $('#listadoDocumentosGrid tr').each(
            function (index) {
                var tipoDoctoSeleccionado = $(this).children(' td:hidden:last').text();
                if (tipoDoctoSeleccionado != claveDocumentoNSS) {
                    $("#datosHistoriaLaboralForm optgroup[value='" + tipoDoctoSeleccionado + "'] option").attr("disabled", true);
                }
            });
    var indiceNSS = ($('#listadoNSSInvolucradosGrid tr').length - 1);
    desactivarOptgroupSelectDocumento(claveDocumentoNSS, indiceNSS * 10);
}
;

function desactivarOptgroupSelectDocumento(claveTipoDocumento, numMaxDocs) {
    var documentosActuales = contarDocsPorTipo(
            "listadoDocumentosGrid", claveTipoDocumento);
    if (documentosActuales >= numMaxDocs) {
        $("#registroDocumentoProbatorio optgroup[value='"
                + claveTipoDocumento + "']").attr("disabled", true);
    } else {
        $("#registroDocumentoProbatorio optgroup[value='"
                + claveTipoDocumento + "']").removeAttr("disabled");
    }
}
;

var contarDocsPorTipo = function (idTable, tipoDocumento) {
    var contador = 0;
    if ($("#" + idTable + " tbody tr").length > 1) {
        $("#" + idTable + " tbody tr").each(
                function (index) {
                    if ($(this).children(' td:hidden:last').text() == tipoDocumento) {
                        contador++;
                    }
                });
    }
    indexarListaDocumento();
    return contador;
};
