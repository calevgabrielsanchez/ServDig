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
        tableName, tipoDocto, cveIdDocumento) {
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
            + '\',' + cveIdDocumento + ',\'' + idDocBoveda + '\');" ><p style="font-size: 1.5em;">Eliminar</p></a> </td></tr>';

    $('#' + tableName + ' tbody').append(trHtml);
    indexarListaDocumento();
};

var fnCrearRowHiddenNSS = function (arrayCampos, arrayCamposHidden, rowCount,
        tableName) {
    var idTr = tableName + rowCount;
    var trHtml = '<tr id=\'' + idTr + '\'>';
    for (var i = 0; i < arrayCampos.length; i++) {
        trHtml += '<td width="5%" nowrap><p style="font-size: 1.5em;">' + arrayCampos[i] + '</p></td>';
    }
    for (var i = 0; i < arrayCamposHidden.length; i++) {
        trHtml += '<td hidden="hidden">' + arrayCamposHidden[i] + '</td>';
    }
    trHtml += '</tr>';
    console.log(trHtml)
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

var fnCrearRowNSSList = function (arrayCampos, rowCount, tableName,registroNSS) {
    var idTr = tableName + rowCount;
    var trHtml = '<tr id=\'' + idTr + '\'>';
    for (var i = 0; i < arrayCampos.length; i++) {
        trHtml += '<td width="5%" nowrap><p style="font-size: 1.5em;">' + arrayCampos[i] + '</p></td>';
    }
    trHtml += '<td align="right" width="30%" height="55"> <a href="#" onclick="return fnEliminarRowNss(\''+registroNSS
            +'\',\''+idTr+'\');" ><p style="font-size: 1.5em;">Eliminar</p></a></td>';
    
    trHtml += '<td id="table-'+registroNSS+'" align="right" style="padding-top: 0px">'
            +'<div style="overflow-y: scroll; height: 100px;"><table class="table" id="listadoDocumentosGrid-'+registroNSS+'" ><tr></tr></table>'
            +'</div></td></tr>';
    $("#"+tableName).children('tbody').children('tr').last().after(trHtml);
};

