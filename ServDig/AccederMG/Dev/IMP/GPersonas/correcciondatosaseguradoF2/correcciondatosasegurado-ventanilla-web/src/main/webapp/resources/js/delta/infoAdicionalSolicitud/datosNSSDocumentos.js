$(document).ready(function () {
    document.charset = 'utf-8';
    var indiceDocumentoProbatorio = ($('#listadoDocumentosGrid tr').length - 1);
	var tipoDocumento;
	var idDocumentoPorTipo;
	var desDocumento;
	var registroDocumentoProbatorio
    
    $('#nssAsociados').change(function (){
		
		$('#listNSSError').attr("class",'error hiddenElement');
		$('#NSSError').attr("class",'error hiddenElement');
		$('#fileDataDocNss').attr("class",'error hiddenElement');
		$('#listaDocumentosNssTemp').attr("class",'error hiddenElement');
	});
  
    $('#agregarNSSDocumentos').click(function () {
		
        
			
			
		if($('#nssAsociados').val() != -1){
        	agregarNss(indiceDocumentoProbatorio);
			}
			else{
				fnShowError('form#informacionAdicionalForm #NSSError','Debe seleccionar el NSS involucrado en la solicitud que será asociado al documento probatorio.','form#informacionAdicionalForm #registroNSS');		
				
           
       
			}
        
    });

    var agregarDocumentoProbatorios=function(registroDocumentoProbatorio,tipoDocumento,idDocumentoPorTipo,desDocumento){
        agregarDocumento(indiceDocumentoProbatorio,registroDocumentoProbatorio,tipoDocumento,idDocumentoPorTipo,desDocumento);
    };
	
	    
    function editarPanel(bandera){
    	if (bandera) {
      		 $("#nssAsociados").removeAttr("disabled");
   		 	$("#registroDocumProbNss").removeAttr("disabled");
   		 	$("#agregarNSSDocumentos").removeAttr("disabled");
   			$("#limpiarNSSDocumentos").removeAttr("disabled");
          } else {
       	   		$("#nssAsociados").attr("disabled",true);
     		 	$("#registroDocumProbNss").attr("disabled",true);
     		 	$("#agregarNSSDocumentos").attr("disabled",true);
     			$("#limpiarNSSDocumentos").attr("disabled",true);
				$('#listNSSError').attr("class",'error hiddenElement');
				$('#NSSError').attr("class",'error hiddenElement');
				$('#fileDataDocNss').attr("class",'error hiddenElement');
				$('#listaDocumentosNssTemp').attr("class",'error hiddenElement');
				
				
				  $.blockUI();
				limpiarDocumentosNSS();
				limpiarGridDocumentosNSS();
				$.unblockUI();
				
				$('#registroDocumProbAsegurado').val(-1);
				$('#nssAsociados').val(-1);
     			
          }
    }
    
    editarPanel(false);
   

    $('#registroDocumProbNss').change(function() {
    	 var valor=$(this).val();
    	  
    	
    	    
        if(valor!=="-1"){
			 tipoDocumento = $(
    	    "#registroDocumProbNss :selected").parent().attr("value");

    	 idDocumentoPorTipo = $(
    	    "#registroDocumProbNss :selected").attr("docportipo");

    	 desDocumento = $(
    	    "#registroDocumProbNss")
    	    .val();
		registroDocumentoProbatorio = $(
    	    "#registroDocumProbNss option:selected")
    	    .text();
			$('#registroDocumProbNss').val(-1);
            $(function() {   
              $('input[name=fileDataDocNss]').change(function() {
                    
                    // console.log("change de filedata "+valor);
					var fileDataValor=$(this).val();
                    if(fileDataValor!=="" && fileDataValor !== undefined && fileDataValor !== null && valor !=="-1"){
                        agregarDocumentoProbatorios(registroDocumentoProbatorio,tipoDocumento,idDocumentoPorTipo,desDocumento);
                    }
                }); 
           });
           $("#fileDataDocNss").click();
        }else{
            // console.log("nodebe hacer nada")
        }
    });
    
   $('#isDocumentoProbatorioNSS').change(function() {
    	editarPanel($('#isDocumentoProbatorioNSS').is(":checked"));
   });
   
   
   $('#limpiarNSSDocumentos').click(function () {
       var contador = 0;
       $('#NSSListError').html("");
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
                       url: contextPath + '/wizard/correccionDatosAsegurado/informacionAdicional/eliminarDocProbTempNss',
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
//                           if (error.status === 500) {
//                               mensageConfirmacion("Error al eliminar el Documento.");
//                           }
                       }

                   });
                   $.unblockUI();
               }
               
           });
	}
       if ($("#listadoDocumentosNSSGrid tbody tr").length <=1) {
           mensageConfirmacion("BP-5001 - Operaci\u00f3n realizada con \u00e9xito.");
       }else{
    	   mensageConfirmacion("Error al eliminar el Documento.");
       }
	indexarListaDocumento();
	return contador;
       
   });
   
});

var fnEliminarRowNss = function (registroNSS,idRow) {
    var item = $('#' + idRow);
    idRow = $("#listadoNSSInvolucradosGrid tr").index(item);
    idRow = idRow - 1;

    $.blockUI();
    $.ajax({
        url: contextPath
                + '/wizard/correccionDatosAsegurado/informacionAdicional/eliminarNSS',
        type: 'post',
        async: false,
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({
                nss: registroNSS}),
        success: function (response) {
            $(item).remove();
            mensageConfirmacion("BP-5001 - Operaci\u00f3n realizada con \u00e9xito.");
            selectNSSAsociado(registroNSS, false);
            indexarListaNss();
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
        url: contextPath + '/wizard/correccionDatosAsegurado/informacionAdicional/eliminarDocProbTempNss',
        type: 'post',
        async: true,
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({cveIdDocumento: cveIdDocumento,idDocBoveda:idDocBoveda}),
        success: function (response) {
            // console.log(response);
            if (response.success) {
                $(item).remove();
//                selectDocumentoNSS(cveIdDocumento,false);
                indexarListaDocumento();
                $.unblockUI();
                mensageConfirmacion(response.success);
                var numeroNss = ($('#listadoDocumentosNSSGrid tr').length - 1);
                if(numeroNss<10){
                	desactivarOptgroupSelectDocumento("11",false);
                }
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
            "#registroDocumProbNss option[value='"
            + claveDocumentoNSS + "']").attr("disabled", true);
    } else {
        $("#registroDocumProbNss option[value='"
        + claveDocumentoNSS + "']").removeAttr("disabled");
    }
};

var selectNSSAsociado = function (nss, bandera) {
    // console.log(bandera)
    if (bandera) {
        $("#nssAsociados option[value='"
            + nss + "']").attr("disabled", true);
    } else {
        $("#nssAsociados option[value='"
        + nss + "']").removeAttr("disabled");
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

function agregarNss(indiceDocumentoProbatorio){
    var registroNSS = $("#nssAsociados").val();
        fnHideErrores("form#informacionAdicionalForm");
        var propiedad = "font-size";
        fnHideErroresInput("form#informacionAdicionalForm");
        $.blockUI();
        $.ajax({
            url: contextPath
                    + '/wizard/correccionDatosAsegurado/informacionAdicional/validarNss',
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
                selectNSSAsociado(registroNSS, true);
                desactivarOptgroupSelectDocumento("11",false);
                $("#registroDocumProbAsegurado").val("-1");
                $("#nssAsociados").val("-1");
                $.unblockUI();
            },
            error: function (error) {
                // console.log(error);
                fnProcesarErrores(
                        error,
                        "form#informacionAdicionalForm");
                $.unblockUI();
            }
        });
        $.unblockUI();
};

function agregarDocumento(indiceDocumentoProbatorio,registroDocumentoProbatorio,tipoDocumento,idDocumentoPorTipo,desDocumento){
    if (desDocumento == '-1') {
            mensageConfirmacion('Tipo de archivo no v\u00e1lido, seleccione un documento v\u00e1lido');
            return;
        }
       
        var ext = $("#fileDataDocNss").val().split(
                "\\");
        var nombreArchivo = ext[ext.length - 1];
        nombreArchivo = (idDocumentoPorTipo + "_").concat(nombreArchivo);
        fnHideErrores("form#informacionAdicionalForm");
        var ext = $("#fileDataDocNss").val();
        var n = ext.split("\\");
        var nombreExtension = n[n.length - 1];
        n = nombreExtension.split(".");
        nombreExtension = n[n.length - 1];
        var urlDocumento = contextPath
                + "/wizard/correccionDatosAsegurado/informacionAdicional/adjuntarDocumentoNss";
        if ($("#fileDataDocNss").val()!=""){
        if (nombreExtension == 'gif'
                || nombreExtension == 'tif'
                || nombreExtension == 'jpg'
                || nombreExtension == 'png'
                || nombreExtension == 'pdf') {
            nombreExtension = "";
            var valida_docto = {'idDocPorTipo': idDocumentoPorTipo, 'cveIdDocumento':desDocumento,'desDocumento':registroDocumentoProbatorio,'tipoDocumento':tipoDocumento};
            $.blockUI();
            $.ajaxFileUpload({
                url: urlDocumento,
                secureuri: false,
                async:false,
                fileElementId: 'fileDataDocNss',
                data: valida_docto,
                mensajeSuccess: 'Documento adjuntado exitosamente',
                mensajeError: 'Error al adjuntar Documento',
                error: function (data, status) {
                    $.unblockUI();
                    $("#registroDocumProbNss").val(-1);
                    limpiarCamposAgregados(["fileDataDocNss"]);
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
                    // console.log("***********  Documentos hasta ahora " + indiceDocumentoProbatorio+"  ************** ")
                     var numeroNss = ($('#listadoDocumentosNSSGrid tr').length - 1);
                    if(numeroNss==10){
                    	desactivarOptgroupSelectDocumento(tipoDocumento,true);
                    }
//                    selectDocumentoNSS(desDocumento,true);
                    limpiarCamposAgregados(["fileDataDocNss"]);

                }
            });
            limpiarCamposAgregados(["fileDataDocNss"]);
            $.unblockUI();
        } else {
        	$("#registroDocumProbNss").val(-1);
        	limpiarCamposAgregados(["fileDataDocNss"]);
            mensageConfirmacion('El archivo no cumple con el tipo de formato permitido [.pdf, .jpg, .jpeg, .png, .gif, .tif, u otro tipo de imagen con algoritmo de compresi\u00F3n]. No es posible adjuntar el archivo.');

        }
        }
};

function isNSSAsociado(indiceDocumentoProbatorio){
    return $("#nssAsociados").val() != '-1';
};

function indexarListaNss() {
    
    $('#listadoNSSInvolucradosGrid > tbody > tr').each(function(index){
            var indicetotal=index;
                $(this).attr('id','listadoNSSInvolucradosGrid'+indicetotal);
                var contador= $(this).find('td').eq(0).text();
                // console.log("contador " +contador);
                var nss= $(this).find('td').eq(1).text();
                // console.log("nss " +nss);
                var element= $(this).find('td').eq(2);
                // console.log("nss " +nss);
                if(nss!=="" && contador!==""){
                    $(this).find('td').eq(0).html('<p style="font-size: 1.5em;">'+indicetotal+'</p>');
                }
                element.children("a").attr('onclick','return fnEliminarRowNss(\''+nss
            +'\',\''+'listadoNSSInvolucradosGrid'+indicetotal+'\');');
    });
}

var selectObcDocumento = function (claveTipoDocumento, bandera) {
    if (bandera) {
             $("#optgroup"+ claveTipoDocumento).children().attr("disabled",true);
        } else {
            $("#optgroup"+ claveTipoDocumento).children().removeAttr("disabled");
        }
};


function desactivarOptgroupSelectDocumento(claveTipoDocumento, bandera) {
    if (bandera) {
        $("#registroDocumProbNss optgroup[value='"
                + claveTipoDocumento + "']").attr("disabled", true);
    } else {
        $("#registroDocumProbNss optgroup[value='"
                + claveTipoDocumento + "']").removeAttr("disabled");
    }
};

function limpiarDocumentosNSS(){
	
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
                
                   $.ajax({
                       url: contextPath + '/wizard/correccionDatosAsegurado/informacionAdicional/eliminarDocProbTempNss',
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
//                           if (error.status === 500) {
//                               mensageConfirmacion("Error al eliminar el Documento.");
//                           }
                       }

                   });
                   $.unblockUI();
               }
               
           });
	}
};

function limpiarGridDocumentosNSS(){
	
	if ($("#listadoNSSInvolucradosGrid  tbody tr").length > 1) {
           $("#listadoNSSInvolucradosGrid tbody tr").each(function(index) {
            
               var cveNss=null;
               var idDocBoveda=null;
			    
               
               $(this).children("td").each(function(index2) {
                   switch(index2){
                      
                       case 1:
                           console.log("cveNss "+$(this).text());
                           cveNss=$(this).text();
						  
                       break;
                       
                   }
               });
               
               if(cveNss!=null){
				   if ((/^([0-9])*$/).test(cveNss)){
					   
					eliminarRowNss(cveNss,'listadoNSSInvolucradosGrid1');
				   }
               }
               
           });
	}
};
  
function eliminarRowNss(registroNSS,idRow) {
	
    var item = $('#' + idRow);
    idRow = $("#listadoNSSInvolucradosGrid tr").index(item);
    idRow = idRow - 1;

   
    $.ajax({
        url: contextPath
                + '/wizard/correccionDatosAsegurado/informacionAdicional/eliminarNSS',
        type: 'post',
        async: false,
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({
                nss: registroNSS}),
        success: function (response) {
            $(item).remove();
          
            selectNSSAsociado(registroNSS, false);
            indexarListaNss();
        },
        error: function (error) {
            fnProcesarErrores(error, "form#datosNSSDocumentoForm");
            $.unblockUI();
        }
    });
};
