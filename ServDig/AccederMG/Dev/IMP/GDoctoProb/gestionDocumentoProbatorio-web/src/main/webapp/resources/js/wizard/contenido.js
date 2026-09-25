
$(document).ready(function() {
		
		var idTipoTramiteDocumentos = $("#hdnIdTipoTramite").val();
		loadFileUpload(idTipoTramiteDocumentos);
		
		//console.debug("El tipo de tramite es: %s",idTipoTramiteDocumentos);
		
		$("#cerrarWizardDocumentos").click(
			function() {
				advertenciaDocumentos();
			}
		);
		
		$('#aceptarDocumentos').click(function() {

			if(!fileUploadFinish) {
				errorNoSeleccionado();
			} else {
				guardarDocumentos();
			}
		});
		
		var isDocumentacionValida =	$("#formdocumentgeneral").validate({ 

			rules: { 
				idTramite: {
					required:true,
					min: 0
	
				}, 
				idDocumento: {
					required:true,
					min: 0
	
				}
			}, 
		errorLabelContainer: "#warning", 

 
		messages: { 
			idTramite: {min:LABEL_CAMPO_OBLIGATORIO}, 
			idDocumento: {min:LABEL_CAMPO_OBLIGATORIO}
		}
	}); 
	}
);



/**
 * Filtra los documentos requieridos para el tr&aacute;mite
 */
funcionCallBackPinta = function(data){

	var tipoDocumentosNoMostrados = null;
	
	// -------------------------------------------------------------
	// Para registro de recien nacidos no se muestran las actas
	// Para registro de hijos no se muestran las constancias
	// -------------------------------------------------------------
	if( $("#hdnIdTipoTramite").length > 0 ){
		if($("#hdnIdRazonRegistro").length > 0){
			if( (typeof(RAZON_REGISTRO_ENUM) !== 'undefined') && (typeof(TIPO_DOCUMENTO_PROBATORIO_ENUM) !== 'undefined')
					&& (typeof(TIPO_TRAMITE_ENUM) !== 'undefined')){
				
				var tipoTramite = $("#hdnIdTipoTramite").val(); 
				var razonRegistro = $("#hdnIdRazonRegistro").val();
				
				if( tipoTramite == TIPO_TRAMITE_ENUM.REGISTRO_HIJOS  ){
					if( razonRegistro == RAZON_REGISTRO_ENUM.RECIEN_NACIDO){
						tipoDocumentosNoMostrados = TIPO_DOCUMENTO_PROBATORIO_ENUM.ACTAS;
					}else{
						tipoDocumentosNoMostrados = TIPO_DOCUMENTO_PROBATORIO_ENUM.CONSTANCIAS;
					}
				}
			}
		}
		
	}
	
	

	// ------------------------
	// Probatorios
	// ------------------------
	var listaDocumentoProbatorio = [];
	var lisDoc = data.tipoDocumentoProbatorioList;
	var len = lisDoc.length;
	
	for(var i=0;i<len;i++){
		if( tipoDocumentosNoMostrados != lisDoc[i].idTipoDocumentoProbatorio )
			listaDocumentoProbatorio.push(lisDoc[i]);
	}
	
	data.tipoDocumentoProbatorioList = listaDocumentoProbatorio;
	
	
	
	// ------------------------
	// Requeridos
	// ------------------------
	var tipoDocumentoProbatorioReqList = [];
	lisDoc = data.tipoDocumentoProbatorioReqList;
	len = lisDoc.length;
	
	for(var i=0;i<len;i++){
		if( tipoDocumentosNoMostrados != lisDoc[i].idTipoDocumentoProbatorio )
			tipoDocumentoProbatorioReqList.push(lisDoc[i]);
	}
	
	data.tipoDocumentoProbatorioReqList = tipoDocumentoProbatorioReqList;
	
	
	
	listDocReq = tipoDocumentoProbatorioReqList;
	return data;
	
	
};


/**
 * Metodo para guardar los documentos, este documento se hace solo en el Xml del tramite que se indico al inciar el wizard
 */
function guardarDocumentos() {
	$.blockUI();
	$.postJSON('/gestionDocumentoProbatorio-web/fileupload/saveDoc', null,
		function() {
		
			//console.debug("Se han guardado correctamente los documentos en el XML");
			$.unblockUI();
			if(typeof parent.WizardCapturaDocumentosProbatoriosCtrl !== "undefined") {
				parent.WizardCapturaDocumentosProbatoriosCtrl.finalizarCaptura();
			} 
			
		}
	);
}

function errorNoSeleccionado() {
	$noSeleccionado = $('<div></div');

    $noSeleccionado.dialog({
        autoOpen : false,
        resizable : false,
        title : 'Error',
        modal : true,
        buttons : {
            "Aceptar" : function() {
                cierraDialogo($(this));
            }
        },
        open: function(event, ui) {
            $(this).closest('.ui-dialog').find('.ui-dialog-buttonset').css("text-align","center").css("width","100%");
        }
    }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

    $noSeleccionado.text('Debes capturar todos los documentos para continuar.');
    $noSeleccionado.dialog('open');
}

function advertenciaDocumentos() {
	$noSeleccionado = $('<div></div');

	$noSeleccionado.dialog({
		autoOpen : false,
		resizable : false,
		title : 'Mensaje del sistema',
		modal : true,
		buttons : {
			"Cancelar" : function() {
				cierraDialogo($(this));
			},
			"Aceptar" : function() {
				if(typeof parent.WizardCapturaDocumentosProbatoriosCtrl !== "undefined") {
					
					parent.$("body").trigger("eliminaDoc",{documenProbatorioCapturaList : []});
					
					parent.WizardCapturaDocumentosProbatoriosCtrl.limpiarIndicadores();
					parent.WizardCapturaDocumentosProbatoriosCtrl.cerrar();
					
				}
				
				//console.debug("Se cancela la captura y no se tomaran en cuenta nigun documento capturado");
				
				cierraDialogo($(this));
			}
			
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$noSeleccionado.html('Si cierra la captura de documentos, todos los datos capturados ser&aacute;n eliminados, si est&aacute; de acuerdo de clic en la opci&oacute;n Aceptar, de lo contrario de clic en Cancelar.');
	$noSeleccionado.dialog('open');
}

function cierraDialogo($dialogo){
	$dialogo.dialog('close');
    $dialogo.dialog('destroy');
    $dialogo.html('');
}

function marcarCamposConErroresYRequired(form,selectorError,selectorPadre) {
	var tieneError= false;
	$(form).find(""+selectorError).each(function() {
		var existeError = $(this).is(":visible");
		tieneError = tieneError || existeError;
		var cssSpan = existeError ? "red" : "black";
		
		var $padre = $(this).parent(""+selectorPadre);
		
		$padre.find(":text").each(function() {
			$('label[for="'+this.name+ '"]').find("span").css("color",cssSpan);
			$('label[for="'+this.id+ '"]').find("span").css("color",cssSpan);
		})
		
		$padre.find("select").each(function() {
			$('label[for="'+this.name+ '"]').find("span").css("color",cssSpan);
			$('label[for="'+this.id+ '"]').find("span").css("color",cssSpan);
		})
		
		$padre.find("textarea").each(function() {
			$('label[for="'+this.name+ '"]').find("span").css("color",cssSpan);
			$('label[for="'+this.id+ '"]').find("span").css("color",cssSpan);
		})
	});

	//pintarErrorGeneral(tieneError);
}

