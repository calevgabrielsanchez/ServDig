$(document).ready(function () {
    document.charset = 'utf-8';
    var indiceDocumentoProbatorio = ($('#listadoDocumentosGrid tr').length - 1);
	var tipoDocumento;
	var idDocumentoPorTipo;
	var desDocumento;
	var registroDocumentoProbatorio
	
    edicionDeCombos();
    function editarPanel(bandera){
    	if (bandera) {
    		$("#registroDocumentoProbatorio").removeAttr("disabled");
       } else {
    	   $("#registroDocumentoProbatorio").attr("disabled",true);
    	   $('#documentoProbatorioBeneficiarioListError').attr("class",'error hiddenElement');
    	   limpiarDocumentosBeneficiario();
		$('#registroDocumentoProbatorio').val(-1);
       }
    }
    editarPanel(false);
    
    var agregarDocumento=function(registroDocumentoProbatorio,tipoDocumento,idDocumentoPorTipo,desDocumento){
        if (desDocumento == '-1') {
            mensageConfirmacion('Tipo de archivo no v\u00e1lido, seleccione un documento v\u00e1lido');
            return;
        }
       
        console.log("descripcion de documento "+registroDocumentoProbatorio);
        var ext = $("#fileData").val().split(
                "\\");
        var nombreArchivo = ext[ext.length - 1];
        nombreArchivo = (idDocumentoPorTipo + "_").concat(nombreArchivo);
        fnHideErrores("form#datosInteresadoForm"); //datosInteresadoForm datosHistoriaLaboralForm
        var ext = $("#fileData").val();
        var n = ext.split("\\");
        var nombreExtension = n[n.length - 1];
        n = nombreExtension.split(".");
        nombreExtension = n[n.length - 1];
        var urlDocumento = contextPath
                + "/wizard/correccionDatosAsegurado/informacionAdicional/adjuntarDocumentoBeneficiario";
        if ($("#fileData").val()!=""){
        if (nombreExtension == 'gif'
                || nombreExtension == 'tif'
                || nombreExtension == 'jpg'
                || nombreExtension == 'png'
                || nombreExtension == 'pdf') {
            nombreExtension = "";
            var cveIdDocumento=desDocumento;
            var valida_docto = {'idDocPorTipo': idDocumentoPorTipo, 'cveIdDocumento':cveIdDocumento,'desDocumento':unescape(registroDocumentoProbatorio),'tipoDocumento':tipoDocumento};
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
                    var idDocBoveda = JSON.parse(data.responseText)['idDocBoveda'];
                    var arrayCampos = [
                        ++indiceDocumentoProbatorio,
                        registroDocumentoProbatorio];
                    var arrayCamposHidden = [
                        nombreArchivo, desDocumento, idDocumentoPorTipo];
                    arrayCamposHidden.push(idDocBoveda);
                    agregaDocProBeneficiarioTabla(
                            arrayCampos,
                            arrayCamposHidden,
                            indiceDocumentoProbatorio,
                            'listadoDocumentosGrid', tipoDocumento,desDocumento);
                             var idTipoDato=$("#registroDocumentoProbatorio option[value='"+cveIdDocumento+"']").parent().attr("value");
                        selectObcDocumento(idTipoDato,true);
                     optgroupSelectDocumentoBeneficiario(tipoDocumento,true);
//                    selectDocumento(desDocumento,true);
                    $("#registroDocumentoProbatorio").val(-1);
                    limpiarCamposAgregados(["fileData"]);
                }
            });
            limpiarCamposAgregados(["fileData"]);
            $.unblockUI();
        } else {
        	$("#registroDocumentoProbatorio").val(-1);
        	limpiarCamposAgregados(["fileData"]);
            mensageConfirmacion('El archivo no cumple con el tipo de formato permitido [.pdf, .jpg, .jpeg, .png, .gif, .tif, u otro tipo de imagen con algoritmo de compresi\u00f3n]. No es posible adjuntar el archivo.');

        }
        }
    };
    
    $('#registroDocumentoProbatorio').change(function() {
    	var valor=$(this).val();
    	
    	if(desDocumento!=="-1"){
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
    				
    				console.log("change de filedata "+valor);
    				if(valor!=="" && valor !== undefined && valor !== null && desDocumento!=="-1"){
    					agregarDocumento(registroDocumentoProbatorio,tipoDocumento,idDocumentoPorTipo,desDocumento);
    				}
    			}); 
    		});
    		$("#fileData").click();
    	}
    });
    
    $('#isDocumentoBeneficiario').change(function() {
    	editarPanel($('#isDocumentoBeneficiario').is(":checked"));
    });
    
});

function optgroupSelectDocumentoBeneficiario(claveTipoDocumento, bandera) {
	console.log("optgroupSelectDocumentoBeneficiario "+bandera)
    if (bandera) {
    	
        $("#registroDocumentoProbatorio optgroup[value='"
                + claveTipoDocumento + "']").attr("disabled", true);
    } else {
        $("#registroDocumentoProbatorio optgroup[value='"
                + claveTipoDocumento + "']").removeAttr("disabled");
    }
};

var utilDisabled = function (stringid, bandera) {
    if (bandera) {
        $(stringid).attr("disabled", true);
    } else {
        $(stringid).removeAttr("disabled");
    }
};

var agregaDocProBeneficiarioTabla = function (arrayCampos, arrayCamposHidden, rowCount,
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
    trHtml += '<td align="right" width="90%" height="55"> <a href="#" onclick="return eliminarDocProbBenf(\'' + idTr
            + '\',' + cveIdDocumento + ',\'' + idDocBoveda + '\');" ><p style="font-size: 1.5em;">Eliminar</p></a> </td></tr>';

    $('#' + tableName + ' tbody').append(trHtml);
    indexarListaDocumentoBeneficiario();
};

var eliminarDocProbBenf = function (idRow, cveIdDocumento, idDocBoveda) {
    var item = $('#' + idRow);
    idRow = $("#listadoDocumentosGrid tr").index(item);
    idRow = idRow - 1;
    $.blockUI();
    $.ajax({
        url: contextPath + '/wizard/correccionDatosAsegurado/informacionAdicional/eliminarDocProbBeneficiario',
        type: 'post',
        async: true,
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({cveIdDocumento: cveIdDocumento,idDocBoveda:idDocBoveda}),
        success: function (response) {
            console.log(response);
            if (response.success) {
                $(item).remove();
                var idTipoDato=$("#registroDocumentoProbatorio option[value='"+cveIdDocumento+"']").parent().attr("value");
                console.log(idTipoDato);
//                selectDocumento(cveIdDocumento,false);
 				optgroupSelectDocumentoBeneficiario(idTipoDato,false);
                indexarListaDocumentoBeneficiario();
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

function indexarListaDocumentoBeneficiario() {
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
                            var idTipoDato=$("#registroDocumentoProbatorio option[value='"+idclave+"']").parent().attr("value");
                            selectObcDocumento(idTipoDato,true);
                    }
        });
    });
}

function setSelectDocumentos(documentos, select){
    var options = "<option value='-1'>--Por favor seleccione--</option>";
    var optId = 1;
    var names = [];
    var ids = [];
    for ( var o in documentos ) {
        var res = o.split("=");
        console.log(res);
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
                console.log(documentoObjeto);
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

var selectObcDocumento = function (claveTipoDocumento, bandera) {
    if (bandera) {
             $("#optgroup"+ claveTipoDocumento).children().attr("disabled",true);
        } else {
            $("#optgroup"+ claveTipoDocumento).children().removeAttr("disabled");
        }
};



function limpiarDocumentosBeneficiario(){
	 if ($("#listadoDocumentosGrid tbody tr").length >1) {
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
        url: contextPath + '/wizard/correccionDatosAsegurado/informacionAdicional/eliminarDocProbBeneficiario',
        type: 'post',
        async: true,
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({cveIdDocumento: cveIdDocumento,idDocBoveda:idDocBoveda}),
        success: function (response) {
            console.log(response);
            if (response.success) {
                tr.remove();
                var idTipoDato=$("#registroDocumentoProbatorio option[value='"+cveIdDocumento+"']").parent().attr("value");
                 optgroupSelectDocumentoBeneficiario(idTipoDato,false);

                indexarListaDocumentoBeneficiario();
               
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



