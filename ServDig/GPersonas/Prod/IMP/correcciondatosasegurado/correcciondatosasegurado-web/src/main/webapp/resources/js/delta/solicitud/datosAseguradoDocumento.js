$(document).ready(function () {
    document.charset = 'utf-8';
    var indiceDocumentoProbatorio = ($('#listadoDocumentosGrid tr').length - 1);
    edicionDeCombos();
    var agregarDocumento=function(){
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
        console.log("descripcion de documento "+registroDocumentoProbatorio);
        var ext = $("#fileData").val().split(
                "\\");
        var nombreArchivo = ext[ext.length - 1];
        nombreArchivo = (idDocumentoPorTipo + "_").concat(nombreArchivo);
        fnHideErrores("form#datosHistoriaLaboralForm");
        var ext = $("#fileData").val();
        var n = ext.split("\\");
        var nombreExtension = n[n.length - 1];
        n = nombreExtension.split(".");
        nombreExtension = n[n.length - 1];
        var urlDocumento = contextPath
                + "/wizard/correccionDatosAsegurado/documentosProbatorios/asegUploadify";
        if (nombreExtension == 'gif'
                || nombreExtension == 'tif'
                || nombreExtension == 'jpg'
                || nombreExtension == 'png'
                || nombreExtension == 'pdf') {
            nombreExtension = "";
            var valida_docto = {'idDocPorTipo': idDocumentoPorTipo, 'cveIdDocumento':$("#registroDocumentoProbatorio").val(),'desDocumento':unescape(registroDocumentoProbatorio),'tipoDocumento':tipoDocumento};
            $.blockUI();
            $.ajaxFileUpload({
                url: urlDocumento,
                secureuri: false,
                fileElementId: 'fileData',
                contentType : "charset=utf-8",
                data: valida_docto,
                mensajeSuccess: 'Documento adjuntado exitosamente <br>BP-5001 - Operaci\u00f3n realizada con \u00e9xito',
                mensajeError: 'Error al adjuntar Documento',
                error: function (data, status) {
                    $.unblockUI();
                },
                success: function (data, status) {
                    $.unblockUI();
                    var idDocBoveda = JSON.parse(data.responseText)['idDocBoveda'];
                    var arrayCampos = [
                        ++indiceDocumentoProbatorio,
                        registroDocumentoProbatorio];
                    var arrayCamposHidden = [
                        nombreArchivo, desDocumento, idDocumentoPorTipo];
                    arrayCamposHidden.push(idDocBoveda);
                    fnCrearRowHidden(
                            arrayCampos,
                            arrayCamposHidden,
                            indiceDocumentoProbatorio,
                            'listadoDocumentosGrid', tipoDocumento,$("#registroDocumentoProbatorio").val());
                    selectDocumento($("#registroDocumentoProbatorio").val(),true);
                    $("#registroDocumentoProbatorio").val(-1);
                    limpiarCamposAgregados(["fileData"]);

                }
            });
        } else {
            mensageConfirmacion('El archivo no cumple con el tipo de formato permitido [.pdf, .jpg, .png, .gif, .tif]. No es posible adjuntar el archivo.');

        }
    };

    $('#continuarDatosHistoriaLaboral').click(function () {
        document.charset = 'ISO-8859-1';
        console.log("continuarDatosHistoriaLaboral click");
        $.blockUI();
        $.ajax({
                url : contextPath
                        + '/wizard/correccionDatosAsegurado/documentosProbatorios/validarDocumentosAsegurado',
                type : 'post',
                async : false,
                dataType: 'json',
                contentType : "application/json; charset=utf-8",
                data : JSON.stringify({nss:null}),
                success : function(response) {
                        document.charset = 'ISO-8859-1';
                        $('#datosHistoriaLaboralForm').submit();					
                        $.unblockUI();
                },
                error : function(error) {
                    console.log(error);
                        fnProcesarErrores(error,"form#datosHistoriaLaboralForm");
                        $.unblockUI();
                }
        });
        $.unblockUI();
    });
    
    $('#limpiarNSSDocumentos').click(function () {
        document.charset = 'ISO-8859-1';
        var contador = 0;
	if ($("#listadoDocumentosGrid tbody tr").length > 1) {
            $("#listadoDocumentosGrid tbody tr").each(function(index) {
                var idRow=null;
                var cveIdDocumento=null;
                var idDocBoveda=null;
                var tr=$(this);
                $(this).children("td").each(function(index2) {
                    switch(index2){
                        case 0:
                            console.log("idRow "+$(this).text());
                            idRow=$(this).text();
                        break;
                        case 3:
                            console.log("cveIdDocumento "+$(this).text());
                            cveIdDocumento=$(this).text();
                        break;
                        case 5:
                            console.log("idDocBoveda "+$(this).text());
                            idDocBoveda=$(this).text();
                        break;
                    }
                });
                idRow = idRow - 1;
                if(cveIdDocumento!=null && idRow!=null && idDocBoveda!=null){
                $.blockUI();
                $.ajax({
                    url: contextPath + '/wizard/correccionDatosAsegurado/documentosProbatorios/eliminarDocumentoAsegurado',
                    type: 'post',
                    async: false,
                    dataType: 'json',
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify({cveIdDocumento: cveIdDocumento,idDocBoveda:idDocBoveda}),
                    success: function (response) {
                        console.log(response);
                        tr.remove();
                        selectDocumento(cveIdDocumento,false);
                    },
                    error: function (error) {
                        $.unblockUI();
                        console.log(error);
                        if (error.status === 500) {
                            mensageConfirmacion("EX-500 - Error al eliminar el Documento.");
                        }
                    }
                });
                $.unblockUI();
                }
                
            });
	}
        if ($("#listadoDocumentosGrid tbody tr").length <=1) {
            mensageConfirmacion("BP-5001 - Operaci\u00f3n realizada con \u00e9xito.");
        }
	indexarListaDocumento();
	return contador;
        
    });
    
    $('#registroDocumentoProbatorio').change(function() {
        if($(this).val()!=="-1"){
            $(function() {   
              $('input[name=fileData]').change(function() {
                    var valor=$(this).val();
                    console.log("change de filedata "+valor);
                    if(valor!==""){
                        agregarDocumento();
                    }
                }); 
           });
           $("#fileData").click();
        }else{
            console.log("nodebe hacer nada")
        }
    });
    
});


var fnEliminarRow = function (idRow, cveIdDocumento, idDocBoveda) {
    var item = $('#' + idRow);
    idRow = $("#listadoDocumentosGrid tr").index(item);
    idRow = idRow - 1;
    $.blockUI();
    $.ajax({
        url: contextPath + '/wizard/correccionDatosAsegurado/documentosProbatorios/eliminarDocumentoAsegurado',
        type: 'post',
        async: true,
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({cveIdDocumento: cveIdDocumento,idDocBoveda:idDocBoveda}),
        success: function (response) {
            console.log(response);
            if (response.success) {
                $(item).remove();
                selectDocumento(cveIdDocumento,false);
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
            console.log(error);
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

var limpiarCamposAgregados = function (arrayCamposLimpiar) {
    jQuery.each(arrayCamposLimpiar, function (i, val) {
        $("#" + val).val("");
    });
};

var selectDocumento = function (claveDocumentoNSS, bandera) {
    console.log(bandera)
    if (bandera) {
        $("#registroDocumentoProbatorio option[value='"
            + claveDocumentoNSS + "']").attr("disabled", true);
    } else {
        $("#registroDocumentoProbatorio option[value='"
        + claveDocumentoNSS + "']").removeAttr("disabled");
    }
};

function edicionDeCombos(){
    $("#listadoDocumentosGrid tbody tr").each(
        function(index) {
                $(this).children("td").each(function(index2) {
                    if (index2 == 3) {
                            var idclave=$(this).text();
                            console.log(idclave);
                            selectDocumento(idclave, true);
                    }
        });
    });
}

