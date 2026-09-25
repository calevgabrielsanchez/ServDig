$(document).ready(function () {
    document.charset = 'utf-8';
    var indiceDocumentoProbatorio = ($('#listadoDocumentosNSSGrid tr').length - 1);
    
    var registroDocumentoProbatorio;
	var tipoDocumento;
	var idDocumentoPorTipo;
	var desDocumento;
	var idRegistroSolicitud;
    
    $('#agregarNSSDocumentos').click(function () {
        var countador=0
        $('#listadoNSSInvolucradosGrid > tbody > tr').each(function() {
            countador=countador + 1;
          });

        if(countador<=10){
            agregarNss(indiceDocumentoProbatorio);
        }else{
            mensageConfirmacion("Se ha excedido el l\u00EDmite de registro de NSS,"+ 
                    "el l\u00EDmite m\u00E1ximo para asociar NSS a una solicitud por Portal Ciudadano es de 10.");
        }
    });

    
    var agregarDocumentoProbatorios=function(){
    	agregarDocumento(indiceDocumentoProbatorio,registroDocumentoProbatorio,tipoDocumento,idDocumentoPorTipo,desDocumento);
    };

    $('#continuarDatosHistoriaLaboral').click(function () {
    	fnHideErrores("form#datosNSSDocumentoForm");
    	document.charset = 'ISO-8859-1';
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
                    console.log(error);
                        fnProcesarErrores(error,"form#datosNSSDocumentoForm");
                        $.unblockUI();
                }
        });
        $.unblockUI();
    	
        

    });
    
    $('#limpiarNSSDocumentos').click(function () {
        
        var contador = 0;
		 $('#NSSError').html("");
       $('#registroNSS').css({"border": "1px solid #ccc" });
	if ($("#listadoDocumentosNSSGrid tbody tr").length > 1) {
            $("#listadoDocumentosNSSGrid tbody tr").each(function(index) {
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
                        url: contextPath + '/wizard/correccionDatosAsegurado/documentosProbatorios/eliminarDocumentoProbatorio',
                        type: 'post',
                        async: false,
                        dataType: 'json',
                        contentType: "application/json; charset=utf-8",
                        data: JSON.stringify({cveIdDocumento: cveIdDocumento,idDocBoveda:idDocBoveda}),
                        success: function (response) {
                            console.log(response);
                            tr.remove();
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
        if ($("#listadoDocumentosNSSGrid tbody tr").length <=1) {
            mensageConfirmacion("BP-5001 - Operaci\u00f3n realizada con \u00e9xito.");
            $("#registroNSS").val("");
        }
	indexarListaDocumento();
	validaNumeroDocumentosNss();
	return contador;
        
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
                    var fileDataValor=$(this).val();
                    console.log("change de filedata "+fileDataValor);
                    if(valor!=="" && valor !== undefined && valor !== null && fileDataValor !== '-1'){
                        agregarDocumentoProbatorios();
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
  //La tabla  listadoNSSInvolucradosGrid siempre tienen un <tr></tr> vacio
//    var todalRow=$('#listadoNSSInvolucradosGrid > tbody > tr').length; 
//    if(todalRow>1){
//    	indexarListaNss();
//    }
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
            console.log(response);
            if (response.success) {
                $(item).remove();
                indexarListaDocumento();
                $.unblockUI();
                validaNumeroDocumentosNss();
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
    $('#listadoDocumentosNSSGrid tr').each(function (index) {
        $(this).children(' td:first').html('<p style="font-size: 1.5em;">' + index + "</p>");
    });
}

var limpiarCamposAgregados = function (arrayCamposLimpiar) {
    jQuery.each(arrayCamposLimpiar, function (i, val) {
        $("#" + val).val("");
    });
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
        });
    }  
};

function agregarNss(indiceDocumentoProbatorio){
    var registroNSS = $("#registroNSS").val();
        fnHideErrores("form#datosNSSDocumentoForm");
        var propiedad = "font-size";
        var valor = $("#registroNSS").css(propiedad);
        fnHideErroresInput("form#datosNSSDocumentoForm");
        $("#registroNSS").css(propiedad, valor);
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
                var table = document.getElementById('listadoNSSInvolucradosGrid');
                var rowCount = table.rows.length;
                var arrayCampos = [
                    rowCount,
                    registroNSS];
                fnCrearRowNSSList(arrayCampos,
                        rowCount,
                        'listadoNSSInvolucradosGrid',registroNSS);
                limpiarCamposAgregados(["registroNSS"]);
                getOtherTable(registroNSS);
                $("#listadoDocumentosNSSGrid").empty();
                var trHtml = '<tbody><tr></tr></tbody>';
                $('#listadoDocumentosNSSGrid').append(trHtml);
                indiceDocumentoProbatorio = ($('#listadoDocumentosNSSGrid tr').length - 1);
                validaNumeroDocumentosNss();
                $.unblockUI();
            },
            error: function (error) {
                console.log(error);
                fnProcesarErrores(
                        error,
                        "form#datosNSSDocumentoForm");
                $.unblockUI();
            }
        });
        $.unblockUI();
};

function agregarDocumento(indiceDocumentoProbatorio,registroDocumentoProbatorio,tipoDocumento,idDocumentoPorTipo,desDocumento){
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
        if ($("#fileData").val()!==""){
        if (nombreExtension === 'gif'
                || nombreExtension === 'tif'
                || nombreExtension === 'jpg'
                || nombreExtension === 'jpeg'
                || nombreExtension === 'png'
                || nombreExtension === 'pdf') {
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
                    indiceDocumentoProbatorio = ($('#listadoDocumentosNSSGrid tr').length - 1);
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
                            
                    $("#registroDocumentoProbatorio").val(-1);
                    limpiarCamposAgregados(["fileData"]);
                    validaNumeroDocumentosNss();
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

function indexarListaNss() {
    
    $('#listadoNSSInvolucradosGrid > tbody > tr').each(function(index){
            var indicetotal=index;
                $(this).attr('id','listadoNSSInvolucradosGrid'+indicetotal);
                var contador= $(this).find('td').eq(0).text();
                console.log("contador " +contador);
                var nss= $(this).find('td').eq(1).text();
                console.log("nss " +nss);
                var element= $(this).find('td').eq(2);
                console.log("nss " +nss);
                if(nss!=="" && contador!==""){
                    $(this).find('td').eq(0).html('<p style="font-size: 1.5em;">'+indicetotal+'</p>');
                }
                element.children("a").attr('onclick','return fnEliminarRowNss(\''+nss
            +'\',\''+'listadoNSSInvolucradosGrid'+indicetotal+'\');');
    });
}


function validaNumeroDocumentosNss() {
	var indice = ($('#listadoDocumentosNSSGrid tr').length - 1);
	console.log("Lo documentos son : "+indice)
	if(indice<10){
		desactivarOptgroupSelectDocumento(11,false);
	}else{
		desactivarOptgroupSelectDocumento(11,true);
	}
    
}
