var valida = {};
var camposDocumentosProbatorios = [
                                  
     'desDocumento',
     'nombre',
     'cveIdDocumento',
     'idDocumentoPorTipo',
     'tipoDocumento',
     'tipoPer',
     'idDocBoveda'  
];

$(document).ready(function() {  
                    document.charset = 'UTF-8';
                    $('#fileData, [for="fileData"]').prop('disabled', true);    
                    var indiceNSS = ($('#listadoNSSInvolucradosGrid tr').length - 1);   
                    var indiceDocumentoProbatorio = ($('#listadoDocumentosGrid tr').length - 1);
                    var nom="CDA_PI_";
                    $('#agregarNSS')
                            .click(
                                    function() {
                                        var registroNSS = $("#registroNSS")
                                                .val();
                                        fnHideErrores("form#datosHistoriaLaboralForm");
                                        var propiedad ="font-size";
                                        var valor = $("#registroNSS").css(propiedad);
                                        fnHideErroresInput("form#datosHistoriaLaboralForm");
                                        $("#registroNSS").css(propiedad,valor);
                                        $.blockUI();
                                        $
                                                .ajax({
                                                    url : contextPath
                                                            + '/wizard/correccionDatosAsegurado/validar/NSS/'
                                                            + ($('#listadoNSSInvolucradosGrid tr').length - 1),
                                                    type : 'post',
                                                    async : false,
                                                    dataType : 'json',
                                                    contentType : "application/json; charset=utf-8",
                                                    data : JSON.stringify({
                                                        nss : registroNSS
                                                    }),
                                                    success : function(response) {
                                                        var table = document.getElementById('listadoNSSInvolucradosGrid');
                                                        var rowCount = table.rows.length;
                                                        var arrayCampos = [
                                                                rowCount,
                                                                registroNSS ];
                                                                fnCrearRow(arrayCampos,
                                                                rowCount,
                                                                'listadoNSSInvolucradosGrid');
                                                        limpiarCamposAgregados([ "registroNSS" ]);
                                                        var indiceNSS = ($('#listadoNSSInvolucradosGrid tr').length - 1);
                                                        desactivarActivarSelectDocumentoNSS(claveDocumentoNSS);
                                                        $.unblockUI();
                                                    },
                                                    error : function(error) {
                                                        fnProcesarErrores(
                                                                error,
                                                                "form#datosHistoriaLaboralForm");
                                                        $.unblockUI();
                                                    }
                                                });
                                        $.unblockUI();
                                    });
                    $('#agregarDocumento')
                            .click(function() {
                                            if($("#registroDocumentoProbatorio").val() == '-1'){
                                            mensageConfirmacion('Tipo de archivo no v\u00e1lido, seleccione un documento v\u00e1lido');
                                            return;
                                        }
                                        //Validacion documentos
                                            var
                                                group = $("#registroDocumentoProbatorio option:selected").parent(),
                                                tipoPer = group.attr("tipo"),
                                                tipoDocNom = $("#registroDocumentoProbatorio option:selected").text();
                                                tipoDoc = group.attr("value");
                                                    if(valida[tipoPer] == undefined ){
                                                        valida[tipoPer] = {};
                                                    }
                                                    if(valida[tipoPer][tipoDoc] == undefined){
                                                        valida[tipoPer][tipoDoc] = 1;
                                                    }else{
                                                        valida[tipoPer][tipoDoc] ++;    
                                                    }
                                        var registroDocumentoProbatorio = $("#registroDocumentoProbatorio option:selected")
                                                .text();
                                        var tipoDocumento = $("#registroDocumentoProbatorio :selected").parent().attr("value");
                                        var idDocumentoPorTipo = $("#registroDocumentoProbatorio :selected").attr("docportipo");
                                        var tipoPersona = $("#registroDocumentoProbatorio :selected").attr("docpara");
                                        var desDocumento = $("#registroDocumentoProbatorio").val();
                                        var cveIdDocumento=$("#registroDocumentoProbatorio :selected").val();
                                        var ext = $("#fileData").val().split("\\");
                                        var nombreArchivo = ext[ext.length - 1];
                                        if(tipoPer == "Beneficiario"){
                                          nombreArchivo = (idDocumentoPorTipo+"_").concat(nombreArchivo);
                                          nombreArchivo = nom.concat(nombreArchivo);
                                        }else{
                                            nombreArchivo = (idDocumentoPorTipo+"_").concat(nombreArchivo);
                                        }
                                        var iniciales = nombreArchivo.substring(0, 7);
                                        if(tipoPer == "Asegurado" &&  iniciales === nom){
                                            nombreArchivo = nombreArchivo.substring(7)                                          
                                        }                                       
                                        var arrayCampos = [
                                                ++indiceDocumentoProbatorio,
                                                registroDocumentoProbatorio ];
                                        var arrayCamposHidden = [
                                                nombreArchivo, desDocumento,idDocumentoPorTipo,tipoDocumento,tipoPer];  
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
                                            $.blockUI();
                                        valida_docto = {'tipoPer' : tipoPer,
                                                        'idDocPorTipo':idDocumentoPorTipo};
                                            $
                                                    .ajaxFileUpload({
                                                        url : urlDocumento,
                                                        secureuri : false,
                                                        fileElementId : 'fileData',
                                                        data: valida_docto, 
                                                        mensajeSuccess:'Documento adjuntado exitosamente <br>BP-5001 - Operaci\u00f3n realizada con \u00e9xito',  
                                                        mensajeError:'Error al adjuntar Documento',
                                                        error : function(data, status) {
                                                            $.unblockUI();
                                                        },
                                                        success : function(data, status) {
                                                        	$.unblockUI();
                                                            var idDocBoveda = JSON.parse(data.responseText)['idDocBoveda'];
                                                            arrayCamposHidden.push(idDocBoveda);
                                                            fnCrearRowHiddenDoc(
                                                                arrayCampos,
                                                                    arrayCamposHidden,
                                                                    indiceDocumentoProbatorio,
                                                                'listadoDocumentosGrid',tipoDocumento,tipoPer,cveIdDocumento);
                                                            if (desDocumento === claveDocumentoNSS) {
                                                                //Agregar validacion aqui si se requiere verificar num de Doctos probatorios por NSS
                                                            } else {
                                                                $('#fileData').hide();

                                                                if(tipoDoc != "11"){
                                                                    $("#registroDocumentoProbatorio option:selected").attr("disabled","disabled");
                                                                }
                                                                if((tipoDoc=="1") || (tipoDoc == "11" && valida.Asegurado["11"] > 14) ){
                                                                    $("#registroDocumentoProbatorio option:selected").parent().find("option").attr("disabled","disabled");
                                                                }
                                                            }
                                                    
                                                            $("#registroDocumentoProbatorio").val(-1);
                                                            limpiarCamposAgregados([ "fileData" ]);  
                                                        }

                                                    });
                                        } else {
                                            mensageConfirmacion('El archivo no cumple con el tipo de formato permitido [.pdf, .jpg, .png, .gif, .tif]. No es posible adjuntar el archivo.');

                                        }                                       
                                    });

                    $('#continuarDatosHistoriaLaboral').click(                          
                    function() {
                            $('#idBeneficio').removeClass('error');
                            var tipoBeneficiario =  $('input[name=tipoBeneficiario]:checked').val();
                            var tipoSolicitante =  $('input[name=tipoSolicitante]:checked').val();
                            if(tipoSolicitante!=undefined && tipoSolicitante=='1' && tipoBeneficiario==undefined){
                                fnProcesarErrorSimple("idBeneficio","Campo requerido","form#datosHistoriaLaboralForm");
                            }else {
                                fnCrearCamposHiddenNotTH(
                                        'listadoNSSInvolucradosGrid',
                                        'datosHistoriaLaboralForm', 'NSSList',
                                        'NSS', 1);
                                fnCrearCamposHiddenNotTHDocumentos(
                                        'listadoDocumentosGrid',
                                        'listadoDocumentosGridB',
                                        'datosHistoriaLaboralForm',
                                        'documentoProbatorioList',
                                        camposDocumentosProbatorios);
                                document.charset = 'ISO-8859-1';                                                            
                                $('#datosHistoriaLaboralForm').submit();
                                limpiarCamposAgregados([ "registroNSS" ]);
                            }

                    });
                    $('#fileData').hide();
                    $('#beneficiario').hide();
                    $('#curpSolicitante').hide();
                    $('#registroDocumentoProbatorio').disableSelection();
                    if (personaSolicitante!=""){
                        if (personaSolicitante == 'REPRESENTANTE_LEGAL'){
                            $('#representante').attr('checked', true);
                            iniciarRepresentante();
                        } else if (personaSolicitante == 'ASEGURADO_PENSIONADO'){
                            $('#asegurado').attr('checked', true);
                            iniciarAsegurado();
                        } else if (personaBeneficiario != ""){
                            $('#def').attr('checked', true);
                            iniciarDefuncion();
                            if (personaBeneficiario == 'CONYUGUE'){
                                $('#conyugue').attr('checked', true);
                                iniciarConyugue();
                            } else if(personaBeneficiario == 'CONCUBINO'){
                                $('#concubino').attr('checked', true);
                                iniciarConcubinos();
                            } else if(personaBeneficiario == 'DESCENDIENTE'){
                                $('#descendiente').attr('checked', true);
                                iniciarDescendientes();
                            } else if(personaBeneficiario == 'PADRES'){
                                $('#padres').attr('checked', true);
                                iniciarPadres();
                            }
                        }
                    }
                    $('#beneficiario').removeClass('error');
                    $('#def').change(function(){
                        eliminarLista("listadoNSSInvolucradosGrid");
                        iniciarDefuncion();                     
                        eliminarListaDocs();
                        
                    });
                    $('#representante').change(function(){
                        eliminarLista("listadoNSSInvolucradosGrid");
                        iniciarRepresentante();                     
                        eliminarListaDocs();                        
                        limpiarCamposAgregados(["registroCurp"]);
                    });
                    $('#asegurado').change(function(){
                        eliminarLista("listadoNSSInvolucradosGrid");
                        iniciarAsegurado();
                        eliminarListaDocs();                        
                        limpiarCamposAgregados(["registroCurp"]);
                    });
                    $('#conyugue').change(function(){
                        eliminarLista("listadoNSSInvolucradosGrid");
                        iniciarConyugue();                      
                        eliminarListaDocs();
                        limpiarCamposAgregados(["registroCurp"]);
                    });
                    $('#padres').change(function(){
                        eliminarLista("listadoNSSInvolucradosGrid");
                        iniciarPadres();
                        eliminarListaDocs();
                        limpiarCamposAgregados(["registroCurp"]);
                    });
                    $('#concubino').change(function(){
                        eliminarLista("listadoNSSInvolucradosGrid");
                        iniciarConcubinos();
                        eliminarListaDocs();
                        limpiarCamposAgregados(["registroCurp"]);
                    });
                    $('#descendiente').change(function(){
                        eliminarLista("listadoNSSInvolucradosGrid");
                        iniciarDescendientes();                     
                        eliminarListaDocs();
                        limpiarCamposAgregados(["registroCurp"]);
                    });
                    $('#registroDocumentoProbatorio').change(function(){
                        var disableLabel = false;
                        if(this.value == -1){
                            disableLabel = true;
                        }
                        $('#fileData, [for="fileData"]').prop('disabled', disableLabel);
                        
                    });
                    recuperaDatos('TbDocs',['desDocumento',
                                            'nombre',
                                            'cveIdDocumento',
                                            'idDocumentoPorTipo',
                                            'tipoDocumento',
                                            'tipoPer',
                                            'idDocBoveda']);
                    
                });

var fnEliminarRow = function(idRow, tipoDocto) {
	
	var item = $('#' + idRow);
	idRow = $( "#listadoNSSInvolucradosGrid tr" ).index( item );
	idRow = idRow - 1;
	
	$.blockUI();
	$.ajax({
			url : contextPath
				+ '/wizard/correccionDatosAsegurado/actualizarLista',
			type : 'post',
			async : false,
			dataType : 'json',
			contentType : "application/json; charset=utf-8",
			data : JSON.stringify({ 'idRow': '' + idRow, 'view': 'datosHistoriaLaboral', 'type' : 'listadoNSSInvolucradosGrid' }),
			success : function(response) {
				$(item).remove();
			    if (tipoDocto != undefined){
			        activarSelectDocumento(tipoDocto);
			    } else {
			        desactivarActivarSelectDocumentoNSS(claveDocumentoNSS);
			    }
			    indexarListaDocumento();
			},
			error : function(error) {
				fnProcesarErrores(error,"form#datosHistoriaLaboralForm");
				$.unblockUI();
			}
		});
	$.unblockUI(); 
	return false;
};


var fnEliminarRowDoc = function(idRow,tipoDocto,tipoPer,cveIdDocumento,idDocBoveda) {
	
	var item = $('#' + idRow);
	if(item.selector.indexOf("listadoDocumentosGridB")<0){
		idRow = $( "#listadoDocumentosGrid tr" ).index( item ) - 1;
	} else {
		idRow =  ($( "#listadoDocumentosGridB tr" ).index( item ) + $('#listadoDocumentosGrid tr').length) - 2;
	}

    $.blockUI();
    $.ajax({
        
        url : contextPath + '/wizard/correccionDatosAsegurado/eliminarDocumentoBoveda',
        type : 'post',
        async : true,
        dataType : 'json',
        contentType : "application/json; charset=utf-8",
        data : JSON.stringify({'idDocBoveda' : idDocBoveda, 'idRow': '' + idRow, 'view': 'datosHistoriaLaboral', 'type' : 'listadoDocumentosGrid' }),     
        success : function(response) {
            if(response.success){
                if((tipoDocto == "2" && tipoPer=="Asegurado") || tipoDocto == "16" || tipoDocto == "17" || tipoDocto == "14"){
                    $("#registroDocumentoProbatorio option[value='"+cveIdDocumento+"']").removeAttr("disabled");
                }
                if(tipoDocto=="1" || (tipoDocto=="2" && tipoPer=="Beneficiario")){
                    $("#registroDocumentoProbatorio option[tipoDoc='"+tipoDocto+"'][docpara='"+tipoPer+"']").parent().find("option").removeAttr("disabled");
                }
                if(tipoDocto == "11"){
                    desactivarActivarSelectDocumentoNSS(tipoDocto);
                }
                $(item).remove();
                indexarListaDocumento();
                $.unblockUI();
                mensageConfirmacion(response.success);
            } else {
                $.unblockUI();
                mensageConfirmacion(response.error);
            }
            
         },
        error : function(error) {
            $.unblockUI();
            if(error.status === 500){
                mensageConfirmacion("EX-500 - Error al eliminar el Documento.");
            } 
        }      
    });
    return false;
};

var desactivarActivarSelectDocumentoNSS = function(claveDocumentoNSS) {
    var indiceNSS = ($('#listadoNSSInvolucradosGrid tr').length - 1);
    var documentosNSSActuales = contarDocsPorTipo(
            "listadoDocumentosGrid", claveDocumentoNSS);
    if (documentosNSSActuales >= indiceNSS*15) {
        $(
                "#registroDocumentoProbatorio optgroup[value='"
                        + claveDocumentoNSS + "']").attr("disabled", true);
    } else {
        $(
                "#registroDocumentoProbatorio optgroup[value='"
                        + claveDocumentoNSS + "']").removeAttr("disabled");
    }
};

function indexarListaDocumento(){
    $('#listadoDocumentosGrid tr').each(
        function(index){
            $(this).children(' td:first').html('<p style="font-size: 1.5em;">'+index+"</p>");
    });
    $('#listadoDocumentosGridB tr').each(
        function(index){
            $(this).children(' td:first').html('<p style="font-size: 1.5em;">'+index+"</p>");
    });
}

var fnCrearRow = function(arrayCampos, rowCount, tableName) {
    var idTr = tableName + rowCount;
    var trHtml = '<tr id=\'' + idTr + '\'>';
    for (var i = 0; i < arrayCampos.length; i++) {
        trHtml += '<td width="5%" nowrap><p style="font-size: 1.5em;">' + arrayCampos[i] + '</p></td>';
    }
    trHtml += '<td align="right" width="90%" height="55"> <a href="#" onclick="return fnEliminarRow(\'' + idTr
            + '\');" ><p style="font-size: 1.5em;">Eliminar</p></a> </td></tr>';

    $('#' + tableName + ' tbody').append(trHtml);
};

var fnCrearRowHiddenDoc = function(arrayCampos, arrayCamposHidden, rowCount,
        tableName,tipoDocto,tipoPer,cveIdDocumento) {
    console.log(arrayCamposHidden[5]);
    var idDocBoveda = arrayCamposHidden[camposDocumentosProbatorios.indexOf('idDocBoveda')-1];
    if(tipoPer=="Beneficiario"){
        tableName=tableName.concat('B');
    }
    var idTr = tableName + rowCount;
    var trHtml = '<tr id=\'' + idTr + '\'>';
    
    for (var i = 0; i < arrayCampos.length; i++) {
        trHtml += '<td width="5%" nowrap><p style="font-size: 1.5em;">' + arrayCampos[i] + '</p></td>';
    }
    for (var i = 0; i < arrayCamposHidden.length; i++) {
        trHtml += '<td hidden="hidden">' + arrayCamposHidden[i] + '</td>';
    }
    trHtml += '<td align="right" width="90%" height="55"><a href="#" onclick=\'return fnEliminarRowDoc("' + idTr + '","'+tipoDocto+'","'+tipoPer+'",'+cveIdDocumento+',"'+idDocBoveda+'");\'><p style="font-size: 1.5em;">Eliminar</p></a> </td></tr>';
    $('#' + tableName + ' tbody').append(trHtml);
    indexarListaDocumento();
};


var fnCrearCamposHidden = function(idTable, idFrom, parameterList, numeroCampos) {
    var arrayCampos = [];
    $("#" + idTable + " tbody tr th").each(function(index) {
        if (arrayCampos.length < numeroCampos) {
            arrayCampos.push($(this).attr("name"));
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
                            }
                        });
            });
};

var fnCrearCamposHiddenNotTHDocumentos= function(idTable, idTableb, idFrom, parameterList, arrayNombreCampos) {
    $("#" + idTable + " tbody tr ").each(
            function(index) {               
                $(this).children("td").each(
                        function(index2) {
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
        var contador=$("#" + idTable + " tbody tr").length -2;
        $("#" + idTableb + " tbody tr ").each(
            function(index) {                               
                $(this).children("td").each(
                        function(index2) {
                            var attr = $(this).attr('hidden');
                            if (typeof attr !== typeof undefined
                                    && attr !== false || index2 === 1) {
                                $('#' + idFrom).append(
                                        '<input type="hidden" name="'
                                                + parameterList + '['
                                                + (contador) + '].'
                                                + arrayNombreCampos[index2 - 1]
                                                + '" value="' + $(this).text()
                                                + '"/>');
                            }
                        });
                        contador++;
            });
            
};


var fnCrearCamposHiddenNotTH = function(idTable, idFrom, parameterList,
        nombreCampo, posicionInformacion) {
    $("#" + idTable + " tbody tr").each(
            function(index) {
                $(this).children("td").each(
                        function(index2) {
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

var contarDocsPorTipo = function(idTable, tipoDocumento) {
    var contador = 0;
    if ($("#" + idTable + " tbody tr").length > 1) {
        $("#" + idTable + " tbody tr").each(
            function(index) {
                if($(this).children(' td:hidden:last').text()== tipoDocumento){
                    contador++;
                }
            });
    }
    indexarListaDocumento();
    return contador;
};

var limpiarCamposAgregados = function(arrayCamposLimpiar) {
    jQuery.each(arrayCamposLimpiar, function(i, val) {
        $("#" + val).val("");
    });
};

function obtenerDocumentos(def, solicitante){
    $.ajax({
        url : contextPath+'/wizard/correccionDatosAsegurado/datosHistoriaLaboral/obtenerDocsProbatorios',
        type : 'post',
        async : false,
        dataType : 'json',
        contentType : "application/json; charset=utf-8",
        data : JSON
                .stringify({
                    defuncion:def,
                    tipoBeneficiario:solicitante
                }),     
        success : function(response) {
            var select = "#registroDocumentoProbatorio";
            setSelectDocumentos(response, select);
            desactivarActivarSelectDocumentoNSS(claveDocumentoNSS);
            $('#registroDocumentoProbatorio').enableSelection();
        },
        error : function(error) {
            fnProcesarErrores(
                    error,
                    "form#datosHistoriaLaboralForm");
        }
    });
}
    
function setSelectDocumentos(documentos, select){
    arregloTipoDocs = {};
    var options = "<option value='-1'>--Por favor seleccione--</option>";
    var optId = 1;
    for(var tipo in documentos){
        for(var tiposDocs in documentos[tipo]){
            var descripcion = (tiposDocs.split("=")[2].split("]")[0])+" "+tipo.toUpperCase(),
                idTipoDocumentoProbatorio = tiposDocs.split("=")[1].split(",")[0],
                lObligatorio = (idTipoDocumentoProbatorio != "16")? " (Obligatorio)" : "",
                docsPorTipo = documentos[tipo][tiposDocs]; 
             options += "<optgroup label='" + descripcion + lObligatorio + "' style='color: #101010; font-weight: 700;' tipo='"+ tipo +"' value='" + idTipoDocumentoProbatorio + "'>";
            for(var key in docsPorTipo){
                var cveIdDocumento = docsPorTipo[key].cveIdDocumento,
                    idDocumentoPorTipo = docsPorTipo[key].idDocumentoPorTipo,
                    desDocumento = docsPorTipo[key].desDocumento; 
                    tipoDocumento = docsPorTipo[key].tipoDocumento;
                options += "<option value='"+ cveIdDocumento +"' docportipo='"+ idDocumentoPorTipo +"' tipoDoc='"+ tipoDocumento +"' docpara='"+tipo+"'>"+ desDocumento +"</option>";
                optId++;
            }
            options += "</optgroup>";
        }
    }
    $(select).html(options);
}

function accionesTipoSolicitante(defuncion){
    if (defuncion){
        var tipoBeneficiario =  $('input[name=tipoBeneficiario]:checked').val();
        obtenerDocumentos(true, tipoBeneficiario);
    } else {
        $('#beneficiario').hide();
        obtenerDocumentos(false, 'REPRESENTANTE_LEGAL');
    }
}

function iniciarAsegurado(){
    $('#beneficiario').hide();
    $('#curpSolicitante').hide();
    $('#doctosBene').hide();
    $('input[name=tipoBeneficiario]').attr('checked',false);
    limpiarCamposAgregados(["registroCurp"]);
    obtenerDocumentos(true, '');
}

function iniciarRepresentante(){
    $('#curpSolicitante').show();
    $('#doctosBene').show();
    $('input[name=tipoBeneficiario]').attr('checked',false);
    accionesTipoSolicitante(false);
}

function iniciarDefuncion(){
    $('input[name=tipoBeneficiario]').attr('checked',false);
    $('#registroDocumentoProbatorio').disableSelection();
    $('#beneficiario').show();
    $('#curpSolicitante').show();
    $('#doctosBene').show();
}

function iniciarDescendientes(){
    accionesTipoSolicitante(true);
}

function iniciarConyugue(){
    accionesTipoSolicitante(true);
}

function iniciarPadres(){
    accionesTipoSolicitante(true);
}

function iniciarConcubinos(){
    accionesTipoSolicitante(true);
}

function eliminarLista(grid){
    fnHideErroresInput("form#datosHistoriaLaboralForm");
    limpiarCamposAgregados([ "registroNSS" ]);
    $('#idBeneficio').removeClass('error');
    $("#"+grid+" tbody tr").each(
        function(index) {
            if (index!=0){
                var idRow = $(this).attr('id');
                $('#' + idRow).remove();
            }
        }); 
    fnHideErrores("form#datosHistoriaLaboralForm");
}


function eliminarListaDocs(){
    $('#idBeneficio').removeClass('error');
    var idsBovedaDocs = [];
    $("#listadoDocumentosGrid tbody tr").each(
        function(index) {
            if (index!=0){
                $(this).children("td").each(
                        function(index2) {
                            if (index2 === (camposDocumentosProbatorios.indexOf("idDocBoveda") + 1)) {
                                idsBovedaDocs.push($(this).text());
                            }
                        });
                var idRow = $(this).attr('id');
                $('#' + idRow).remove();
            }         
        });
    $("#listadoDocumentosGridB tbody tr").each(
        function(index) {
            if (index!=0){
                $(this).children("td").each(
                        function(index2) {
                            if (index2 === camposDocumentosProbatorios.indexOf("idDocBoveda")) {
                                idsBovedaDocs.push($(this).text());
                            }
                        });
                var idRow = $(this).attr('id');
                $('#' + idRow).remove();
            }         
        });
    $.ajax({
        url : contextPath + '/wizard/correccionDatosAsegurado/eliminarListaDocsBoveda',
        type : 'post',
        async : true,
        dataType : 'json',
        contentType : "application/json; charset=utf-8",
        data : JSON.stringify({'idsBovedaDocs' : idsBovedaDocs}),
    });
    fnHideErrores("form#datosHistoriaLaboralForm");
}

function fnProcesarErrorSimple(campo, mensaje, contenedor){
      var form = $(contenedor); 
      campo = campo.replace(/\./g, "\\.");
      var filtroCampoError = contenedor +' #'+campo +'Error';
      var filtroInputError = contenedor +' #'+campo ;
      fnShowErrorSimple(filtroCampoError , mensaje , filtroInputError);
}

function fnShowErrorSimple(idCampoError , mensajeError,idInputError){
    $(idCampoError).removeClass(nClassHidden);
    $(idCampoError).addClass(nClassShow);
    $(idInputError).addClass("error");
    $(idCampoError).text(mensajeError);
}

function recuperaDatos(classTables,arrayNombreCampos){
    $("table."+classTables+" tbody tr.trDocs").each(function(){     
        var tmp = {};
        $(this).children("td").each(function(index2){
            var attr = $(this).attr('hidden');
            if (typeof attr !== typeof undefined && attr !== false || index2 === 1) {   
                var name = arrayNombreCampos[index2 - 1];
                var value = $(this).text();
                tmp[name] = value;
            }
        });
        if(valida[tmp["tipoPer"]] == undefined){
            valida[tmp.tipoPer] = {};
        }
        if(valida[tmp.tipoPer][tmp.tipoDocumento] == undefined){
            valida[tmp.tipoPer][tmp.tipoDocumento] = 1;
        }else{
            valida[tmp.tipoPer][tmp.tipoDocumento] ++;  
        }
        if(tmp.tipoDocumento=="1"){
            $("#registroDocumentoProbatorio option[tipoDoc='"+tmp.tipoDocumento+"'][docpara='"+tmp.tipoPer+"']").parent().find("option").attr("disabled","disabled");
        }
        if(tmp.tipoDocumento=="16" || tmp.tipoDocumento=="17" || tmp.tipoDocumento=="14" ){
            $("#registroDocumentoProbatorio option[tipoDoc='"+tmp.tipoDocumento+"']").parent().find("option").attr("disabled","disabled");
        }
        if(tmp.tipoDocumento=="2"){
            $("#registroDocumentoProbatorio option[value='"+tmp.cveIdDocumento+"'][docpara='"+tmp.tipoPer+"']").attr("disabled","disabled");
        }
        if(tmp.tipoDocumento == "11"){
            desactivarActivarSelectDocumentoNSS(tmp.tipoDocumento)
        }
    });
}
