$(document).ready(function () {
    document.charset = 'utf-8';
    var indiceDocumentoProbatorio = ($('#listadoDocumentosGridAsegurado tr').length - 1);
	var tipoDocumento;
	var idDocumentoPorTipo;
	var desDocumento;
	var registroDocumentoProbatorio
	
	
    edicionDeCombos();
    function editarPanel(bandera){
    	if (bandera) {
    		$("#registroDocumProbAsegurado").removeAttr("disabled");
       } else {

    	   $("#registroDocumProbAsegurado").attr("disabled",true);
    	   limpiarDocumentosAsegurado();
		$('#registroDocumProbAsegurado').val(-1);
		$('#documentoProbatorioAseguradoListError').attr("class",'error hiddenElement');
       }
    }
    editarPanel(false);
    
    var agregarDocumento=function(registroDocumentoProbatorio,tipoDocumento,idDocumentoPorTipo,desDocumento){
        if (desDocumento == '-1') {
            mensageConfirmacion('Tipo de archivo no v\u00e1lido, seleccione un documento v\u00e1lido');
            return;
        }
       
        console.log("descripcion de documento "+registroDocumentoProbatorio);
        var ext = $("#fileDataAsegurado").val().split(
                "\\");
        var nombreArchivo = ext[ext.length - 1];
        nombreArchivo = (idDocumentoPorTipo + "_").concat(nombreArchivo);
        fnHideErrores("form#informacionAdicionalForm");
        var ext = $("#fileDataAsegurado").val();
        var n = ext.split("\\");
        var nombreExtension = n[n.length - 1];
        n = nombreExtension.split(".");
        nombreExtension = n[n.length - 1];
        var urlDocumento = contextPath
                + "/wizard/correccionDatosAsegurado/informacionAdicional/adjuntarDocumentoAsegurado";
        if ($("#fileDataAsegurado").val()!=""){
        if (nombreExtension == 'gif'
                || nombreExtension == 'tif'
                || nombreExtension == 'jpg'
                || nombreExtension == 'png'
                || nombreExtension == 'pdf') {
            nombreExtension = "";
            var valida_docto = {'idDocPorTipo': idDocumentoPorTipo, 'cveIdDocumento':desDocumento,'desDocumento':unescape(registroDocumentoProbatorio),'tipoDocumento':tipoDocumento};
            $.blockUI();
            $.ajaxFileUpload({
                url: urlDocumento,
                secureuri: false,
                async:false,
                fileElementId: 'fileDataAsegurado',
                contentType : "charset=utf-8",
                data: valida_docto,
                mensajeSuccess: 'Documento adjuntado exitosamente',
                mensajeError: 'Error al adjuntar Documento',
                error: function (data, status) {
                    $.unblockUI();
                    $("#registroDocumProbAsegurado").val(-1);
                    limpiarCamposAgregados(["fileDataAsegurado"]);
                },
                success: function (data, status) {
                    $.unblockUI();
                    var idDocBoveda = JSON.parse(data.responseText)['idDocBoveda'];
                    console.log("PostUpload");
                    var arrayCampos = [
                        ++indiceDocumentoProbatorio,
                        registroDocumentoProbatorio];
                    var arrayCamposHidden = [
                        nombreArchivo, desDocumento, idDocumentoPorTipo];
                    arrayCamposHidden.push(idDocBoveda);
                    agregaDocProAsegTabla(
                            arrayCampos,
                            arrayCamposHidden,
                            indiceDocumentoProbatorio,
                            'listadoDocumentosGridAsegurado', tipoDocumento,desDocumento);
                    console.log("tipo de documento : "+tipoDocumento)
                    optgroupSelectDocumentoAsegurado(tipoDocumento,true);
//                    selectDocumento(desDocumento,true);
                    $("#registroDocumProbAsegurado").val(-1);
                    limpiarCamposAgregados(["fileDataAsegurado"]);

                }
            });
            limpiarCamposAgregados(["fileDataAsegurado"]);
            $.unblockUI();
        } else {
        	$("#registroDocumProbAsegurado").val(-1);
        	limpiarCamposAgregados(["fileDataAsegurado"]);
            mensageConfirmacion('El archivo no cumple con el tipo de formato permitido [.pdf, .jpg, .jpeg, .png, .gif, .tif, u otro tipo de imagen con algoritmo de compresi\u00F3n]. No es posible adjuntar el archivo.');
        }
        }
    };

    $('#registroDocumProbAsegurado').change(function() {
    	
    	var valor=$(this).val();
    	
    	
    	if(valor!=="-1"){
			 registroDocumentoProbatorio = $(
    			"#registroDocumProbAsegurado option:selected")
    			.text();
    	 tipoDocumento = $(
    	"#registroDocumProbAsegurado :selected").parent().attr("value");

    	 idDocumentoPorTipo = $(
    	"#registroDocumProbAsegurado :selected").attr("docportipo");

    	 desDocumento = $(
    	"#registroDocumProbAsegurado")
    	.val();
		$("#registroDocumProbAsegurado").val(-1);
			
			
    		$(function() {   
    			$('input[name=fileDataAsegurado]').change(function() {
    				
    				console.log("change de filedata "+valor);
    				if(valor!=="" && valor !== undefined && valor !== null && desDocumento !=="-1"){
    					agregarDocumento(registroDocumentoProbatorio,tipoDocumento,idDocumentoPorTipo,desDocumento);
    				}
    			}); 
    		});
    		$("#fileDataAsegurado").click();
    	}
    });
    
    
    $('#isDocumentoProbatorioAsegurado').change(function() {
    	editarPanel($('#isDocumentoProbatorioAsegurado').is(":checked"));
    });
});

var agregaDocProAsegTabla = function (arrayCampos, arrayCamposHidden, rowCount,
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
    trHtml += '<td align="right" width="90%" height="55"> <a href="#" onclick="return eliminarDocProbAsegurado(\'' + idTr
            + '\',' + cveIdDocumento + ',\'' + idDocBoveda + '\');" ><p style="font-size: 1.5em;">Eliminar</p></a> </td></tr>';

    $('#' + tableName + ' tbody').append(trHtml);
    indexarListaDocumentoAsegurado();
};


var eliminarDocProbAsegurado = function (idRow, cveIdDocumento, idDocBoveda) {
    var item = $('#' + idRow);
    idRow = $("#listadoDocumentosGridAsegurado tr").index(item);
    idRow = idRow - 1;
    $.blockUI();
    $.ajax({
        url: contextPath + '/wizard/correccionDatosAsegurado/informacionAdicional/eliminarDocProbAsegurado',
        type: 'post',
        async: true,
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({cveIdDocumento: cveIdDocumento,idDocBoveda:idDocBoveda}),
        success: function (response) {
            console.log(response);
            if (response.success) {
                $(item).remove();
                var idTipoDato=$("#registroDocumProbAsegurado option[value='"+cveIdDocumento+"']").parent().attr("value");
                optgroupSelectDocumentoAsegurado(idTipoDato,false);
                indexarListaDocumentoAsegurado();
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

function indexarListaDocumentoAsegurado() {
    $('#listadoDocumentosGridAsegurado tr').each(
            function (index) {
                $(this).children(' td:first').html('<p style="font-size: 1.5em;">' + index + "</p>");
            });
}

var limpiarCamposAgregados = function (arrayCamposLimpiar) {
    jQuery.each(arrayCamposLimpiar, function (i, val) {
        $("#" + val).val("");
    });
};

var selectObcDocumento = function (claveTipoDocumento,claveDocumento, bandera) {
    console.log((claveTipoDocumento!=2) +" claveTipoDocumento "+claveTipoDocumento +" defucion " + !$("#defuncion").is(':checked'));
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
    $("#listadoDocumentosGridAsegurado tbody tr").each(
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

var selectDocumento = function (idclave, bandera) {
    console.log(bandera)
    if (bandera) {
        $(
            "#registroDocumProbAsegurado option[value='"
            + claveDocumentoNSS + "']").attr("disabled", true);
    } else {
        $("#registroDocumProbAsegurado option[value='"
        + claveDocumentoNSS + "']").removeAttr("disabled");
    }
};

function optgroupSelectDocumentoAsegurado(claveTipoDocumento, bandera) {
	console.log("desactivarOptgroupSelectDocumento "+bandera)
    if (bandera) {
    	
        $("#registroDocumProbAsegurado optgroup[value='"
                + claveTipoDocumento + "']").attr("disabled", true);
    } else {
        $("#registroDocumProbAsegurado optgroup[value='"
                + claveTipoDocumento + "']").removeAttr("disabled");
    }
};


function limpiarDocumentosAsegurado(){
	 if ($("#listadoDocumentosGridAsegurado tbody tr").length >1) {
			  $("#listadoDocumentosGridAsegurado tbody tr").each(function(index) {
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
        url: contextPath + '/wizard/correccionDatosAsegurado/informacionAdicional/eliminarDocProbAsegurado',
        type: 'post',
        async: true,
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({cveIdDocumento: cveIdDocumento,idDocBoveda:idDocBoveda}),
        success: function (response) {
            console.log(response);
            if (response.success) {
                tr.remove();
                var idTipoDato=$("#registroDocumProbAsegurado option[value='"+cveIdDocumento+"']").parent().attr("value");
                optgroupSelectDocumentoAsegurado(idTipoDato,false);
                indexarListaDocumentoAsegurado();
               
                //mensageConfirmacion(response.success);
            } else {
              
              //  mensageConfirmacion(response.error);
            }
        },
        error: function (error) {
          
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
		  
		
       
};
