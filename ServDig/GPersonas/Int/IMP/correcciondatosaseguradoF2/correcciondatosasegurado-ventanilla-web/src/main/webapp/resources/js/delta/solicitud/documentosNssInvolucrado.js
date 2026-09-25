$(document).ready(function () {
    document.charset = 'utf-8';	
    var indiceDocumentoProbatorio = ($('#listadoDocumentosNSSGrid tr').length - 1);
    var registroDocumentoProbatorio;
    var tipoDocumento;
    var idDocumentoPorTipo;
    var desDocumento;
    
    function validaNumeroDocumentosNss() {
        var indice = ($('#listadoDocumentosNSSGrid tr').length - 1);
        console.log("Lo documentos son : " + indice)
        if (indice < 10) {
            desactivarOptgroupSelectDocumento(11, false);
        } else {
            desactivarOptgroupSelectDocumento(11, true);
        }

    }
    
    function desactivarOptgroupSelectDocumento(claveTipoDocumento, bandera) {
        if (bandera) {
            $("#registroDocumentoProbatorio optgroup[value='"
                    + claveTipoDocumento + "']").children().attr("disabled", true);
        } else {
            $("#registroDocumentoProbatorio optgroup[value='"
                    + claveTipoDocumento + "']").children().removeAttr("disabled");
        }
    };
	
    var agregarDocumento=function(registroDocumentoProbatorio,idDocumentoPorTipo,desDocumento,tipoDocumento){
        var ext = $("#fileData").val().split(
                "\\");
        var nombreArchivo = ext[ext.length - 1];
        nombreArchivo = (idDocumentoPorTipo + "_").concat(nombreArchivo);
        fnHideErrores("form#datosNSSDocumentoForm");
        var ext = $("#fileData").val();
        var n = ext.split("\\");
        var nombreExtension = n[n.length - 1];
        n = nombreExtension.split(".");
        nombreExtension = n[n.length - 1];
        var urlDocumento = contextPath
                + "/wizard/correccionDatosAsegurado/documentosProbatorios/uploadify";
        if ($("#fileData").val()!=""){
        if (nombreExtension == 'gif'
                || nombreExtension == 'tif'
                || nombreExtension == 'jpg'
                || nombreExtension == 'png'
                || nombreExtension == 'pdf'
                || nombreExtension == 'jpeg') {
            nombreExtension = "";
            var valida_docto = {'idDocPorTipo': idDocumentoPorTipo, 'cveIdDocumento':desDocumento,'desDocumento':registroDocumentoProbatorio,'tipoDocumento':tipoDocumento};
            $.blockUI();
            $.ajaxFileUpload({
                url: urlDocumento,
                secureuri: false,
                async:false,
                fileElementId: 'fileData',
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
                            'listadoDocumentosNSSGrid', tipoDocumento,desDocumento);
//                    selectDocumentoNSS(desDocumento,true);
                    $("#registroDocumentoProbatorio").val(-1);
                    limpiarCamposAgregados(["fileData"]);
                    //$('optgroup').children('option[value='+desDocumento+']').attr('disabled', true)
                    validaNumeroDocumentosNss();
                    
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
    }
    
    $('#agregarNSSDocumentos').click(function () {

        var registroNSS = $("#registroNSS").val();
        fnHideErrores("form#datosNSSDocumentoForm");
        var propiedad = "font-size";
        var valor = $("#registroNSS").css(propiedad);
        fnHideErroresInput("form#datosNSSDocumentoForm");
        $("#registroNSS").css(propiedad, valor);
        var tableObj = document.getElementById('listadoNSSInvolucradosGrid');
        var rowCount = tableObj.rows.length;

        if (rowCount === 21) {
            mensageConfirmacion('Se ha excedido el l\u00EDmite de registro de NSS, el l\u00EDmite m\u00E1ximo para asociar NSS a una solicitud por ventanilla es de 20.');
        } else {
            $.blockUI();
            $.ajax({
                url: contextPath
                        + '/wizard/correccionDatosAsegurado/documentosProbatorios/validar/nss/'
                        + ($('#listadoNSSInvolucradosGrid tr').length - 1),
                type: 'post',
                async: false,
                dataType: 'json',
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify({
                    nss: registroNSS

                }),
                success: function (response) {
                    var arrayCampos = [
                        rowCount,
                        registroNSS];
                    fnCrearRowNSSList(arrayCampos,
                            rowCount,
                            'listadoNSSInvolucradosGrid', registroNSS);
                    limpiarCamposAgregados(["registroNSS"]);
                    getOtherTable(registroNSS);
                    $("#listadoDocumentosNSSGrid").empty();
                    var trHtml = '<tbody><tr></tr></tbody>';
                    $('#listadoDocumentosNSSGrid').append(trHtml);
                    indiceDocumentoProbatorio = ($('#listadoDocumentosNSSGrid tr').length - 1);
                    mensageConfirmacion("BP-5001 - Operaci\u00f3n realizada con \u00e9xito.");
                    $.unblockUI();
                },
                error: function (error) {
                    // console.log(error);
                    fnProcesarErrores(
                            error,
                            "form#datosNSSDocumentoForm");
                    $.unblockUI();
                }
            });
            $.unblockUI();
        }
    });

    $('#continuarDatosHistoriaLaboral').click(function () {
    	document.charset = 'ISO-8859-1';
    	fnHideErrores("form#datosNSSDocumentoForm");
        $.blockUI();
        $.ajax({
                url : contextPath
                        + '/wizard/correccionDatosAsegurado/documentosProbatorios/validarDocumentosbyNSS',
                type : 'post',
                async : false,
                dataType: 'json',
                contentType : "application/json; charset=utf-8",
                data: JSON.stringify({
                	curp: ""
                }),
                success : function(response) {
                        document.charset = 'ISO-8859-1';
                        $('#datosNSSDocumentoForm').submit();				
                        $.unblockUI();
                },
                error : function(error) {
                    // console.log(error);
                        fnProcesarErrores(error,"form#datosNSSDocumentoForm");
                        $.unblockUI();
                }
        });
        $.unblockUI();
        

    });
    
    $('#limpiarNSSDocumentos').click(function () {
        var contador = 0;
	if ($("#listadoDocumentosNSSGrid tbody tr").length > 1) {
            $("#listadoDocumentosNSSGrid tbody tr").each(function(index) {
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
                        url: contextPath + '/wizard/correccionDatosAsegurado/documentosProbatorios/eliminarDocumentoProbatorio',
                        type: 'post',
                        async: false,
                        dataType: 'json',
                        contentType: "application/json; charset=utf-8",
                        data: JSON.stringify({cveIdDocumento: cveIdDocumento,idDocBoveda:idDocBoveda}),
                        success: function (response) {
                            // console.log(response);
                            tr.remove();
                            selectDocumentoNSS(cveIdDocumento,false);
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
        if ($("#listadoDocumentosNSSGrid tbody tr").length <=1) {
            mensageConfirmacion("BP-5001 - Operaci\u00f3n realizada con \u00e9xito.");
            $("#registroNSS").val("");
        }
	indexarListaDocumento();
	return contador;
        
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
    		$(function() {   
    			$('input[name=fileData]').change(function() {
    				var valor2 = $(this).val();
    				if(valor2!=="" && valor2 !== undefined && valor2 !== null && valor!=="-1"){
    					agregarDocumento(registroDocumentoProbatorio,idDocumentoPorTipo,desDocumento,tipoDocumento);
    				}
    			}); 
    		});
    		$("#fileData").click();
    	}
    });
});

var fnEliminarRowNss = function (registroNSS,idRow) {
    var item = $('#' + idRow);
    idRow = $("#listadoNSSInvolucradosGrid tr").index(item);
    idRow = idRow - 1;

    $.blockUI();
    $.ajax({
        url: contextPath
                + '/wizard/correccionDatosAsegurado/documentosProbatorios/eliminarNSS',
        type: 'post',
        async: false,
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({
                nss: registroNSS}),
        success: function (response) {
            $(item).remove();
            mensageConfirmacion("BP-5001 - Operaci\u00f3n realizada con \u00e9xito.");
					
			
        },
        error: function (error) {
            fnProcesarErrores(error, "form#datosNSSDocumentoForm");
            $.unblockUI();
        }
    });
    $.unblockUI();
    return false;
};

var fnEliminarRow = function (idRow, cveIdDocumento, idDocBoveda) {
    var item = $('#' + idRow);
    idRow = $("#listadoDocumentosNSSGrid tr").index(item);
    idRow = idRow - 1;
    $.blockUI();
    $.ajax({
        url: contextPath + '/wizard/correccionDatosAsegurado/documentosProbatorios/eliminarDocumentoProbatorio',
        type: 'post',
        async: true,
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({cveIdDocumento: cveIdDocumento,idDocBoveda:idDocBoveda}),
        success: function (response) {
            // console.log(response);
            if (response.success) {
                $(item).remove();
                //selectDocumentoNSS(cveIdDocumento,false);
                validaNumeroDocumentosNss_();
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

function validaNumeroDocumentosNss_() {
    var indice = ($('#listadoDocumentosNSSGrid tr').length - 1);
    console.log("Lo documentos son : " + indice)
    if (indice < 10) {
        desactivarOptgroupSelectDocumento(11, false);
    } else {
        desactivarOptgroupSelectDocumento(11, true);
    }

}

function indexarListaDocumento() {
    $('#listadoDocumentosNSSGrid tr').each(function (index) {
        $(this).children(' td:first').html('<p style="font-size: 1.5em;">' + index + "</p>");
    });
}

var limpiarCamposAgregados = function (arrayCamposLimpiar) {
    jQuery.each(arrayCamposLimpiar, function (i, val) {
        $("#" + val).val("");
    });
};

var selectDocumentoNSS = function (claveDocumentoNSS, bandera) {
    // console.log(bandera)
    if (bandera) {
        $(
            "#registroDocumentoProbatorio option[value='"
            + claveDocumentoNSS + "']").attr("disabled", true);
    } else {
        $("#registroDocumentoProbatorio option[value='"
        + claveDocumentoNSS + "']").removeAttr("disabled");
    }
};

function activarSelectDocumento(tipoDocumento) {
    $("#datosNSSDocumentoForm optgroup[value='" + tipoDocumento + "'] option").attr("disabled", false);
};

function desactivarOptgroupSelectDocumento(claveTipoDocumento, bandera) {
    if (bandera) {
        $("#registroDocumentoProbatorio optgroup[value='"
                + claveTipoDocumento + "']").children().attr("disabled", true);
    } else {
        $("#registroDocumentoProbatorio optgroup[value='"
                + claveTipoDocumento + "']").children().removeAttr("disabled");
    }
};


var getOtherTable = function (asociadoNSS) {
    if ($("#listadoDocumentosNSSGrid tbody tr").length > 0) {
        $("#listadoDocumentosNSSGrid tbody tr").each(function (index) {
            var arrayCampos = [];
            var arrayCamposHidden = [];
            var optionAdesbloquear;
            $(this).children("td").each(function (index2) {
                if(index2=='0' || index2=='1'){
                   arrayCampos.push($(this).text()); 
                }else if(index2!='6' || index2!='7'){
                   arrayCamposHidden.push($(this).text());
                   if(index2=='3'){
                        optionAdesbloquear=$(this).text();
                   }
                }
            });
            var indiceDocumentoProbatorio = ($('#listadoDocumentosNSSGrid tr').length - 1);
            fnCrearRowHiddenNSS(arrayCampos, arrayCamposHidden, "-"+indiceDocumentoProbatorio,
                "listadoDocumentosGrid-"+asociadoNSS);
            selectDocumentoNSS(optionAdesbloquear,false);
        });
    }  
};
