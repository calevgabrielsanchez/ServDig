$(document).ready(function () {
    document.charset = 'utf-8';
    var indiceDocumentoProbatorio = ($('#listadoDocumentosGrid tr').length - 1);
	var tipoDocumento;
	var idDocumentoPorTipo;
	var desDocumento;
	var registroDocumentoProbatorio
    edicionDeCombos();

    var agregarDocumento=function(registroDocumentoProbatorio,idDocumentoPorTipo,desDocumento,tipoDocumento){
//    	var tipoDocumento=tipoDocumento;
//    	var idDocumentoPorTipo=idDocumentoPorTipo;
    	// console.log("idDocumentoPorTipo  "+idDocumentoPorTipo);
//    	var desDocumento=desDocumento;
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
        if ($("#fileData").val()!=""){
        if (nombreExtension == 'gif'
                || nombreExtension == 'tif'
                || nombreExtension == 'jpg'
                || nombreExtension == 'png'
                || nombreExtension == 'pdf') {
            var claveDocumento=desDocumento;
            nombreExtension = "";
            var valida_docto = {'idDocPorTipo': idDocumentoPorTipo, 'cveIdDocumento':claveDocumento,'desDocumento':unescape(registroDocumentoProbatorio),'tipoDocumento':tipoDocumento};
            $.blockUI();
            $.ajaxFileUpload({
                url: urlDocumento,
                secureuri: false,
                async:false,
                fileElementId: 'fileData',
                contentType : "charset=utf-8",
                data: valida_docto,
                mensajeSuccess: 'Documento adjuntado exitosamente',
                mensajeError: 'Error al adjuntar Documento',
                error: function (data, status) {
                    $.unblockUI();
                    $("#registroDocumentoProbatorio").val(-1);
                    limpiarCamposAgregados(["fileData"]);
                },
                success: function (data, status) {
                        $.unblockUI();
                        // console.log(data);
                        var idDocBoveda = JSON.parse(data.responseText)['idDocBoveda'];
                        // console.log("primero :" +idDocBoveda);
                        // console.log(idDocBoveda);
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
                                'listadoDocumentosGrid', tipoDocumento,desDocumento);
                        selectObcDocumento(tipoDocumento,claveDocumento,true);
                        $("#registroDocumentoProbatorio").val(-1);
                        limpiarCamposAgregados(["fileData"]);
                	
                }
            });
            limpiarCamposAgregados(["fileData"]);
            $.unblockUI();
        } else {
        	$("#registroDocumentoProbatorio").val(-1);
        	limpiarCamposAgregados(["fileData"]);
            mensageConfirmacion('El archivo no cumple con el tipo de formato permitido [.pdf, .jpg, .jpeg, .png, .gif, .tif, u otro tipo de imagen con algoritmo de compresi\u00F3n]. No es posible adjuntar el archivo.');
        }
        }
    };

    $('#continuarDatosHistoriaLaboral').click(function () {
        document.charset = 'ISO-8859-1';
        var tipoSolicitante =  $('input[name=tipoSolicitante]:checked').val();
        $.blockUI();
        $.ajax({
                url : contextPath
                        + '/wizard/correccionDatosAsegurado/documentosProbatorios/validarDocumentosAsegurado',
                type : 'post',
                async : false,
                dataType: 'json',
                contentType : "application/json; charset=utf-8",
                data : JSON.stringify({tipoSolicitante:tipoSolicitante, defuncion:$("#defuncion").is(':checked')}),
                success : function(response) {
                        document.charset = 'ISO-8859-1';
                        $('#datosHistoriaLaboralForm').submit();					
                        $.unblockUI();
                },
                error : function(error) {
                    // console.log(error);
                        fnProcesarErrores(error,"form#datosHistoriaLaboralForm");
                        $.unblockUI();
                }
        });
        $.unblockUI();
    });
    
    $('#limpiarNSSDocumentos').click(function () {
        var contador = 0;
        $('#NSSError').html("");
        $('#registroNSS').css({"border": "1px solid #ccc" });
	if ($("#listadoDocumentosGrid tbody tr").length > 1) {
            $("#listadoDocumentosGrid tbody tr").each(function(index) {
                var idRow=null;
                var cveIdDocumento=null;
                var idDocBoveda=null;
                var tr=$(this);
                $(this).children("td").each(function(index2) {
                    switch(index2){
                        case 0:
                            // console.log("idRow "+$(this).text());
                            idRow=$(this).text();
                        break;
                        case 3:
                            // console.log("cveIdDocumento "+$(this).text());
                            cveIdDocumento=$(this).text();
                        break;
                        case 5:
                            // console.log("idDocBoveda "+$(this).text());
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
                        // console.log(response);
                        tr.remove();
                        var idTipoDato=$("#registroDocumentoProbatorio option[value='"+cveIdDocumento+"']").parent().attr("value");
                        selectObcDocumento(idTipoDato,cveIdDocumento,false);
                    },
                    error: function (error) {
                        $.unblockUI();
                        // console.log(error);
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
    
    $('input[type=radio][name=tipoSolicitante]').change(function() {
        var tipoSolicitante =  $('input[name=tipoSolicitante]:checked').val();
        // console.log(tipoSolicitante);
        if(tipoSolicitante!=="ASEGURADO"){
            utilDisabled('#defuncion',false);
        }else{
            $("#defuncion").prop('checked', false);
            utilDisabled('#defuncion',true);
            var defuncion =  $("#defuncion").is(':checked');
            obtenerDocumentos(defuncion);
        }
    });
    
    $('#registroDocumentoProbatorio').change(function() {
    	var valor=$(this).val();
    	if(valor!=="-1"){
        	registroDocumentoProbatorio = $(
        			"#registroDocumentoProbatorio option:selected")
        			.text();
        	tipoDocumento = $(
        	"#registroDocumentoProbatorio :selected").parent().attr("value");

        	idDocumentoPorTipo = $(
        	"#registroDocumentoProbatorio :selected").attr("docportipo");

        	desDocumento = $(
        	"#registroDocumentoProbatorio")
        	.val();
        	$("#registroDocumentoProbatorio").val(-1);
        	// console.log("Chance registrodocumento");
        	// console.log(registroDocumentoProbatorio);
        	// console.log(idDocumentoPorTipo);
        	// console.log(desDocumento);
        	// console.log(tipoDocumento);
    		$(function() {   
    			$('input[name=fileData]').change(function() {
    				var valor2=$(this).val();
    				if(valor2!=="" && valor2 !== undefined && valor2 !== null && valor!=="-1"){
    					agregarDocumento(registroDocumentoProbatorio,idDocumentoPorTipo,desDocumento,tipoDocumento);
    				}
    			}); 
    		});
    		$("#fileData").click();
    	}
    });
    
    
    $('#defuncion').change(function() {
        var defuncion =  $("#defuncion").is(':checked');
        obtenerDocumentos(defuncion);
    });
    
});


var utilDisabled = function (stringid, bandera) {
    if (bandera) {
        $(stringid).attr("disabled", true);
    } else {
        $(stringid).removeAttr("disabled");
    }
};

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
            // console.log(response);
            if (response.success) {
                $(item).remove();
                var idTipoDato=$("#registroDocumentoProbatorio option[value='"+cveIdDocumento+"']").parent().attr("value");
                selectObcDocumento(idTipoDato,cveIdDocumento,false);
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
            // console.log(error);
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

var selectDocumento = function (claveDocumento, bandera) {
    // console.log(bandera)
    if (bandera) {
        $("#registroDocumentoProbatorio option[value='"
            + claveDocumento + "']").attr("disabled", true);
    } else {
        $("#registroDocumentoProbatorio option[value='"
        + claveDocumento + "']").removeAttr("disabled");
    }
};

var selectObcDocumento = function (claveTipoDocumento,claveDocumento, bandera) {
    // console.log((claveTipoDocumento!=2) +" claveTipoDocumento "+claveTipoDocumento +" defucion " + !$("#defuncion").is(':checked'));
//    && !$("#defuncion").is(':checked')
    if (claveTipoDocumento!=2 || !$("#defuncion").is(':checked')) {
        if (bandera) {
             $("#optgroup"+ claveTipoDocumento).children().attr("disabled",true);
        } else {
            $("#optgroup"+ claveTipoDocumento).children().removeAttr("disabled");
        }
    }else{
        selectDocumento(claveDocumento, bandera);
    }
};



function edicionDeCombos(){
    $("#listadoDocumentosGrid tbody tr").each(
        function(index) {
                $(this).children("td").each(function(index2) {
                    if (index2 == 3) {
                            var idclave=$(this).text();
                            // console.log(idclave);
                             var idTipoDato=$("#registroDocumentoProbatorio option[value='"+idclave+"']").parent().attr("value");
                            selectObcDocumento(idTipoDato,idclave, true);
                    }
        });
    });
}

function obtenerDocumentos(def){
    $.ajax({
        url : contextPath+'/wizard/correccionDatosAsegurado/documentosProbatorios/obtenerDocsProbatorios',
        type : 'post',
        async : false,
        dataType : 'json',
        contentType : "application/json; charset=utf-8",
        data : JSON
                .stringify({
                    defuncion:def
                }),     
        success : function(response) {
            // console.log(response);
            var select = "#registroDocumentoProbatorio";
            setSelectDocumentos(response, select);
            edicionDeCombos();
        },
        error : function(error) {
            fnProcesarErrores(
                    error,
                    "form#datosInteresadoForm");
        }
    });
}

function setSelectDocumentos(documentos, select){
    var options = "<option value='-1'>--Por favor seleccione--</option>";
    var optId = 1;
    var names = [];
    var ids = [];
    for ( var o in documentos ) {
        var res = o.split("=");
        // console.log(res);
        var otr=res[2].split("]");
        var desded=otr[0];
        names.push(desded);
        var idrep=res[1].split(",");
        ids.push(idrep[0]);
    }
    var e=0;
    $.each(documentos, function(arrayID,tipoDocumentoProbatorio) {
         var descripcion = names[e],
                idTipoDocumentoProbatorio = ids[e],
                lObligatorio = (idTipoDocumentoProbatorio != "16")? " (Obligatorio)" : "",
                docsPorTipo = tipoDocumentoProbatorio[0].tipoDocumento; 
                e++;
             options += "<optgroup id='optgroup"+idTipoDocumentoProbatorio+"' label='" + descripcion + lObligatorio + "' style='color: #101010; font-weight: 700;' tipo='"+ docsPorTipo +"' value='" + idTipoDocumentoProbatorio + "'>";
        $.each(tipoDocumentoProbatorio, function(documentoArrayId,documentoObjeto) {
                // console.log(documentoObjeto);
                var cveIdDocumento = documentoObjeto.cveIdDocumento,
                    idDocumentoPorTipo = documentoObjeto.idDocumentoPorTipo;
                    var desDocumento =documentoObjeto.desDocumento; 
                    var tipoDocumento = documentoObjeto.tipoDocumento;
                options += "<option value='"+ cveIdDocumento +"' docportipo='"+ idDocumentoPorTipo +"' tipoDoc='"+ tipoDocumento +"' docpara='"+""+"'>"+ desDocumento +"</option>";
                optId++;
         });
    });
    $(select).html(options);
}



