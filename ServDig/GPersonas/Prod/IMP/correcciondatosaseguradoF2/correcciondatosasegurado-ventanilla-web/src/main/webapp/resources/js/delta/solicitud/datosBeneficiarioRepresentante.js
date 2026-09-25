$(document).ready(function () {
    document.charset = 'utf-8';
    var indiceDocumentoProbatorio = ($('#listadoDocumentosGrid tr').length - 1);
    edicionDeCombos();
    
    var registroDocumentoProbatorio;
	var tipoDocumento;
	var idDocumentoPorTipo;;
	var desDocumento;
    
    var agregarDocumento=function(registroDocumentoProbatorio,tipoDocumento,idDocumentoPorTipo,desDocumento){
        if (desDocumento == '-1') {
            mensageConfirmacion('Tipo de archivo no v\u00e1lido, seleccione un documento v\u00e1lido');
            return;
        }
       
        // console.log("descripcion de documento "+registroDocumentoProbatorio);
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
                + "/wizard/correccionDatosAsegurado/datosBeneficiarios/beneficiarioUploadify";
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
                    fnCrearRowHidden(
                            arrayCampos,
                            arrayCamposHidden,
                            indiceDocumentoProbatorio,
                            'listadoDocumentosGrid', tipoDocumento,desDocumento);
                             var idTipoDato=$("#registroDocumentoProbatorio option[value='"+cveIdDocumento+"']").parent().attr("value");
//                             optgroupSelectDocumentoBeneficiario(tipoDocumento,true);
                             desactivarOptgroupSelectDocumento(tipoDocumento,true);
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
            mensageConfirmacion('El archivo no cumple con el tipo de formato permitido [.pdf, .jpg, .jpeg, .png, .gif, .tif, u otro tipo de imagen con algoritmo de compresi\u00F3n]. No es posible adjuntar el archivo.');

        }
        }
    };

    $('#continuarDatosBeneficiario').click(function () {

	        document.charset = 'ISO-8859-1';
	        var tipoBeneficiario =  $('input[name=tipoBeneficiario]:checked').val();
	        $.blockUI();
	        $.ajax({
	                url : contextPath
	                        + '/wizard/correccionDatosAsegurado/datosBeneficiarios/validarDatosBeneficiario',
	                type : 'post',
	                async : false,
	                dataType: 'json',
	                contentType : "application/json; charset=utf-8",
	                data : JSON.stringify({tipoBeneficiario:tipoBeneficiario}),
	                success : function(response) {
	                        document.charset = 'ISO-8859-1';
	                        $('#datosInteresadoForm').submit();					
	                        $.unblockUI();
	                },
	                error : function(error) {
	                    // console.log(error);
	                        fnProcesarErrores(error,"form#datosInteresadoForm");
	                        $.unblockUI();
	                }
	        });
	        $.unblockUI();
    });
    
    $('#limpiarNSSDocumentos').click(function () {
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
                    url: contextPath + '/wizard/correccionDatosAsegurado/datosBeneficiarios/eliminarDocumentoBeneficiario',
                    type: 'post',
                    async: false,
                    dataType: 'json',
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify({cveIdDocumento: cveIdDocumento,idDocBoveda:idDocBoveda}),
                    success: function (response) {
                        // console.log(response);
                        tr.remove();
                        var idTipoDato=$("#registroDocumentoProbatorio option[value='"+cveIdDocumento+"']").parent().attr("value");
                        desactivarOptgroupSelectDocumento(idTipoDato,false);
//                        selectDocumento(cveIdDocumento,false);
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
    
    $('input[type="text"]#registroCurp').blur(function() {
		this.value = this.value.toUpperCase();
	});
    
    $('#validarCurp').click(function () {
    	$('#curpError').val("");
//        document.charset = 'ISO-8859-1';
        var registroCURP =  $('#registroCurp').val();
        fnHideErrores("form#datosInteresadoForm");
        $.blockUI();
        $.ajax({
                url : contextPath + '/wizard/correccionDatosAsegurado/datosBeneficiarios/buscarPersonaRenapo',
                type : 'post',
                async : false,
                dataType: 'json',
                contentType : "application/json; charset=utf-8",
                data : JSON.stringify({curp:registroCURP}),
                success : function(response) {
                    // console.log(response.personaRenapo);
                        if(response.personaRenapo!=null){
                            $('#registroCurp').val("");
                            $('#lblcurp').text(response.personaRenapo.curp);
                            $('#lblPrimerApellido').text(response.personaRenapo.primerApellido);
                            $('#lblSegundoApellido').text(response.personaRenapo.segundoApellido);
                            $('#lblnombre').text(response.personaRenapo.nombre);
                            $('#lblSexo').text(response.personaRenapo.sexo.descripcion);
                            
                            $('#lblFechNacimiento').text(response.personaRenapo.fechaNacimientoFormateada);
                            $('#lblLugarNacimiento').text(response.personaRenapo.lugarNacimiento === null ? "" : response.personaRenapo.lugarNacimiento.nombre);
                            $('#lblNacionalidad').text(response.personaRenapo.pais === null ? "" : response.personaRenapo.pais.nacionalidad);
                            $('#lblEntidadValue').text(response.personaRenapo.actaNacimiento.municipio.entidadFederativa.nombre === null ? "" : response.personaRenapo.actaNacimiento.municipio.entidadFederativa.nombre);
                            $('#lblMunicipioValue').text(response.personaRenapo.actaNacimiento.municipio.nombre === null ? "" : response.personaRenapo.actaNacimiento.municipio.nombre);
                            $('#lblAnioValue').text(response.personaRenapo.actaNacimiento.anio === null ? "" : response.personaRenapo.actaNacimiento.anio);
                            $('#lblTomoValue').text(response.personaRenapo.actaNacimiento.tomo === null ? "" : response.personaRenapo.actaNacimiento.tomo);
                            $('#lblNoActaValue').text(response.personaRenapo.actaNacimiento.noActa === null ? "" : response.personaRenapo.actaNacimiento.noActa);
                            $('#lblCripValue').text(response.personaRenapo.actaNacimiento.crip === null ? "" : response.personaRenapo.actaNacimiento.crip);
                            $('#lblNoLibroValue').text(response.personaRenapo.actaNacimiento.noLibro === null ? "" : response.personaRenapo.actaNacimiento.noLibro);
                            $('#lblNoFojaValue').text(response.personaRenapo.actaNacimiento.noFoja === null ? "" : response.personaRenapo.actaNacimiento.noFoja);
                            
                            if (response.personaRenapo.curpsHistoricas === null) {
                                $("#lblCurpHistorico").hide();
                            } else {
                                $("#lblCurpHistorico").show();
                            }                            
                            
                            $('#labelCurpHistoricoText').text(response.personaRenapo.curpsHistoricas ==null ? "":response.personaRenapo.curpsHistoricas);
                            var textCurp = $('#labelCurpHistoricoText').text();
                            $('#labelCurpHistoricoText').text(textCurp.replace(/\[/g,''));
                            $('#labelCurpHistoricoText').text(textCurp.replace(/\]/g, ' '));
                            $('#labelCurpHistoricoText').text(textCurp.replace(/\,/g, ' '));
                            $('#curpError').val("");
                            
                        }
                        else
                        	{
                        		$('#curpError').val("La CURP Proporcionada no fue localizada en RENAPO.<br/> Por favor verifique la información capturada.");
                        	}
                },
                error : function(error) {
                    // console.log(error);
                        fnProcesarErrores(error,"form#datosInteresadoForm");
                        $.unblockUI();
                }
        });
        $.unblockUI();
    });
    
    $('input[type=radio][name=tipoBeneficiario]').change(function() {
        var tipoBeneficiario =  $('input[name=tipoBeneficiario]:checked').val();
        obtenerDocumentos(false,"",tipoBeneficiario);
    });
    
    $('#registroDocumentoProbatorio').change(function() {
    	var valor=$(this).val();
    	if(valor!=="-1"){
    		registroDocumentoProbatorio = $("#registroDocumentoProbatorio option:selected").text();
			tipoDocumento = $("#registroDocumentoProbatorio :selected").parent().attr("value");
			idDocumentoPorTipo = $("#registroDocumentoProbatorio :selected").attr("docportipo");
			desDocumento = $("#registroDocumentoProbatorio").val();
			$("#registroDocumentoProbatorio").val(-1);
    		$(function() {   
    			$('input[name=fileData]').change(function() {
    				var fileValor=$(this).val();
    				if(valor!=="" && valor !== undefined && valor !== null && fileValor!=="-1"){
    					agregarDocumento(registroDocumentoProbatorio,tipoDocumento,idDocumentoPorTipo,desDocumento);
    				}
    			}); 
    		});
    		$("#fileData").click();
    	}
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
        url: contextPath + '/wizard/correccionDatosAsegurado/datosBeneficiarios/eliminarDocumentoBeneficiario',
        type: 'post',
        async: true,
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({cveIdDocumento: cveIdDocumento,idDocBoveda:idDocBoveda}),
        success: function (response) {
            // console.log(response);
            if (response.success) {
            	// console.log("Retorno correcto");
                $(item).remove();
                var idTipoDato=$("#registroDocumentoProbatorio option[value='"+cveIdDocumento+"']").parent().attr("value");
                desactivarOptgroupSelectDocumento(idTipoDato,false);
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

var selectDocumento = function (claveDocumentoNSS, bandera) {
    // console.log(bandera)
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
                            // console.log(idclave);
                            var idTipoDato=$("#registroDocumentoProbatorio option[value='"+idclave+"']").parent().attr("value");
                            desactivarOptgroupSelectDocumento(idTipoDato,true);
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



function obtenerDocumentos(def, solicitante,beneficiario){
    $.ajax({
        url : contextPath+'/wizard/correccionDatosAsegurado/datosBeneficiarios/obtenerDocsProbatorios',
        type : 'post',
        async : false,
        dataType : 'json',
        contentType : "application/json; charset=utf-8",
        data : JSON
                .stringify({
                    defuncion:def,
                    tipoBeneficiario:beneficiario,
                    tipoSolicitante:solicitante
                }),     
        success : function(response) {
            // console.log(response);
            var select = "#registroDocumentoProbatorio";
            setSelectDocumentos(response, select);
            edicionDeCombos();
//            $('#registroDocumentoProbatorio').enableSelection();
        },
        error : function(error) {
            fnProcesarErrores(
                    error,
                    "form#datosInteresadoForm");
        }
    });
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

