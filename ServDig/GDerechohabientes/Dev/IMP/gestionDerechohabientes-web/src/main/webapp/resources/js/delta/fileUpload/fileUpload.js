
var fileUploadFinish=false;
var doctosCargadosLenght=0;
var flashVerReq=6;
var guardarInterno=true;
var listDocReq;
var functionRetorno = null;
var funcionCallBackPinta = null;
$(document).ready(function() {
	
	 
	/*
	var output = "You have Flash player " + playerVersion.major + "." + playerVersion.minor + "." + playerVersion.release + " installed"; 
	alert(output);
	*/

	/*try {
		  var fo = new ActiveXObject('ShockwaveFlash.ShockwaveFlash');
		}catch(e){
		  if(navigator.mimeTypes ["application/x-shockwave-flash"] == undefined) {
		  	//mostrar alerta y mandar a la pagina de flash en ventana nueva
		  	if(confirm('Se Requiere Instalar Flash Player para que el cargador de archivos funcione Correctamente desea ir a la pagina de Flash')){
		  		//dirigirlo en un pop up
		  	}
		  }
		}
	var playerVersion = swfobject.getFlashPlayerVersion().major;
	if(playerVersion<=flashVerReq){
		alert("Se requiere Flash " +flashVerReq+ "o superior");
	}

	$('#file_upload').uploadify({
	'swf' 			: contextPath + '/resources/js/uploadify3/uploadify.swf',
	'uploader' 		: contextPath + '/fileupload/uploadify',
	'method'	 	: 'post',
	'cancelImg' 	: contextPath + '/resources/js/uploadify3/uploadify-cancel.png',
	'multi'			: false,
	'auto' 			: true,//true
	'fileTypeDesc' 	: 'Archivos de Imagen o PDF',
	'fileTypeExts' 	: '*.gif; *.jpg; *.png;*.pdf',
	'fileSizeLimit'	: '2048KB',
	'fileObjName'	:'file' ,
	'debug' 		: false,
	'buttonText' 	: 'Seleccionar Documento ...',
	'width' 		: 200,
	'onCancel' 		: 	function(file) {
							cancelUploadify();
						}
	});*/
});


function setFunctionFinalizadoCapturaDocumentos(funcionRetorno) {
	functionRetorno = funcionRetorno;
}

function loadFileUpload(tipoTramite, listaNuevosRequeridos, idTramite, tipoDocsNoMostrar, idDocsNoMostrar){
	
	if(idTramite==null){
		guardarInterno=false;
	}
	
	
	if(listaNuevosRequeridos == undefined){
		listaNuevosRequeridos = "";
		
	}	
	
	
	if(tipoDocsNoMostrar == undefined){
		tipoDocsNoMostrar = "";
		
	}
	
	
	if(idDocsNoMostrar == undefined){
		idDocsNoMostrar = "";
		
	}
	
	
		$.getJSON("/${mvn.web.app.root}/fileupload/inicio",
				{
				cveIdTipoTramite:tipoTramite,
				cveIdTramite:idTramite,
				nuevosDocsList:listaNuevosRequeridos,
				tipoDocsNoMostrar:tipoDocsNoMostrar,
				idDocsNoMostrar:idDocsNoMostrar
				},              
				function(data) {
					$('#allFileUploadDiv').show();
					$('#divFileUploadDocs').hide();
					$("#docProbTramDiv").hide();
					$('#guardaDocumentosButton').hide();	
					$('#erroresFileUploadDiv').hide();
					$('#browseDocDiv').hide();
					//$('#capturaDoc').hide();
					
					pintaOptions(data);

					//recarga el combo documento
					//cargaDocumentosDelTipo();
					//pintaListaCargados(data);
					//fileUploadFinish=false;
					//fileUploadFinish=!data.necesarios;
					
				}   

		).success(function() {  }) 
		.error(function(jqXHR, textStatus, errorThrown) {
			error(errorThrown+" - "+'No fue posible cargar la lista de documentos probatorios para este tr�mite');
		 }).complete(function() {  });
		 
}

//guarda los documentos pasandole el tramite
function doSaveDocSinTramite(idTramite) {
	if(fileUploadFinish){
		$.blockUI();
		$.ajax({
			async: false,
	    	url : '/${mvn.web.app.root}/fileupload/saveDocSinTramite',
	        type: 'post',
	        data : {
	        	'cveIdTramite' : idTramite
			},
	        dataType: 'json',
	        success: function (result) {
	        	$.unblockUI();
	        }
	    }).error(function(jqXHR, textStatus, errorThrown) {
			fileUploadFinish=false;
			error(errorThrown+" - "+'No fue posible guardar los documentos probatorios para este tr�mite');
		 });
		
		/*
		$.getJSON("/${mvn.web.app.root}/fileupload/saveDocSinTramite",
			{cveIdTramite:idTramite},              
			function(data) {
	
			}           	
		).success(function() {  }) 
		 .error(function(jqXHR, textStatus, errorThrown) {
			fileUploadFinish=false;
			error(errorThrown+" - "+'No fue posible guardar los documentos probatorios para este tr�mite');
		 }).complete(function() {  });; */
	}
}


function doSaveDocS() { 
	
	$.getJSON("/${mvn.web.app.root}/fileupload/saveDoc",
			{},              
			function(data) {
				cargaFinalizada();
				
				//oculta el boton guardar
				$('#guardaDocumentosButton').hide();
			
				fileUploadFinish=true;

			}           	
	).error(function(jqXHR, textStatus, errorThrown) {
			cargaFinalizada();
			//oculta el boton guardar
			$('#guardaDocumentosButton').hide();
			
			error(errorThrown+" - "+'No fue posible guardar los documentos probatorios para este tr�mite');
		 
	}); //agregar funcion de error 
}


 
//carga un documento
function getLisDoc(){  
	//validar que seleccione
	if($('#idDocSelected').val()==-1){
		$('#errorFileUploadDiv').html("Debe seleccionar el tipo de documento");
		$('#erroresFileUploadDiv').show();
	}else{
		$('#erroresFileUploadDiv').hide();
		$.getJSON("/${mvn.web.app.root}/fileupload/loadDoc",
			{
				cveTipoDoctoProb: $('#idTipoDocSelected').val(),
				cveDocto: $('#idDocSelected').val()
			},              
			function(data) {
				if(data.error!=null){
					$('#errorFileUploadDiv').html(data.error);
					$('#erroresFileUploadDiv').show();
				}else{
					pintaOptions(data);  
			
					pintaListaCargados(data);
			
					//$('#file_upload').uploadifyClearQueue();
					//recarga el combo documento
					cargaDocumentosDelTipo();
		
				}
			
			
			}           	
		).success(function() {  }) 
		.error(function(jqXHR, textStatus, errorThrown) {
			error(errorThrown+" - "+'No fue posible cargar el documento.');
		 }).complete(function() {  });  
	}
}   



function eliminaDoc(sel){  

	$.getJSON("/${mvn.web.app.root}/fileupload/eliminaDoc",
			{cveIdDocumentoPorTipo: sel},              
			function(data) {
				
				fileUploadFinish = false;
				pintaOptions(data);  
				pintaListaCargados(data);
				
			}           	
	).success(function() {  }) 
	.error(function(jqXHR, textStatus, errorThrown) {
		error(errorThrown+" - "+'No fue posible eliminar el documento.');
	 }).complete(function() {  });  
}   

function cargaFinalizada(){
	var lenght=doctosCargadosLenght+1;
	$('#thAccion').hide();
	for(var i=0;i<lenght;i++){
		$('#eliminaDoc'+i).hide();
	}

}

function pintaOptions(data){

	if($("#mostrarMensajeActas").length > 0) {
		var mostrarMensajeActasOpcionales = $("#mostrarMensajeActas").val() == 1;
		if(!mostrarMensajeActasOpcionales) {
			$("#mensajeActasOpcionales").hide();
		}
	}
	
	if( funcionCallBackPinta != null && $.isFunction(funcionCallBackPinta) ){
		data = funcionCallBackPinta(data);
	}
	
	var html = '';     
	var lisDoc = data.tipoDocumentoProbatorioList;
	listDocReq = data.tipoDocumentoProbatorioReqList;
	
	
	var len = lisDoc.length;
	var tamListDocReq = listDocReq.length;
	
	var i=0;
	html +='<option value="-1">--Seleccione--</option>';
	for(i=0;i<len;i++){
		html += '<option value="' + lisDoc[i].idTipoDocumentoProbatorio + '" >' + lisDoc[i].descripcion + '</option>';
	}
	
	//Si la lista de los doc requeridos es cero se puede continuar
	
	if(tamListDocReq == 0){
		fileUploadFinish=true;
	}
	if(len==0){
		
		if($("#divMensajeDocumentos").length >0) {
			$("#divMensajeDocumentos").hide();
		}
	
		$('#fieldSelDoc').hide();
		if(guardarInterno){
			$('#guardaDocumentosButton').show();
		}else{//en este punto ya se pueden guardar los documentos
			fileUploadFinish=true;
			//console.debug('La carga de documentos ha sido finalizada');
			if(jQuery.isFunction(functionRetorno)) {
    			functionRetorno();
    		}
		}
		$('#cargaDoc').hide();
		
	}else{
		$('#fieldSelDoc').show();
	
		$('#idTipoDocSelected').html(html);
		$('#guardaDocumentosButton').hide();
		$('#cargaDoc').show();
	}
	
	
}

function pintaOptionsDocumenntos(data){
	var html = '';     
	var len = data.length;	
	var i=0;
	html +='<option value="-1">--Seleccione--</option>';
	for(i=0;i<len;i++){
		
		html += '<option value="' + data[i].cveIdDocumento + '">' + data[i].desDocumento + '</option>';
	}
	$('#idDocSelected').html(html);
	
}

function cargaDocumentosDelTipo(){

	var idTipoDocProb=$('#idTipoDocSelected').val();
	$.getJSON("/${mvn.web.app.root}/fileupload/cargaDocumentosDelTipo",
			{cveIdTipoDocProb:idTipoDocProb},              
			function(data) {
			
				pintaOptionsDocumenntos(data);
			
			}   
			
	).success(function() {  }) 
	.error(function(jqXHR, textStatus, errorThrown) {
		error(errorThrown+" - "+'No fue posible ontener los documentos para esta categoria.');
	 }).complete(function() {  });
}


function pintaListaCargados(data){
	//var pinta=data.loadDoc.ditProductoSolicitud.nomArchivo;
	var pinta;
	var lProd=data.documenProbatorioCapturaList;
	var len = lProd.length;
	var cveDoc;
	var doctoPorTipo;
	var tipoArch;
	pinta="<table width=100% id=listaDocCargadosTable><thead> <tr align='left'><th align='left'>Tipo Documento</th><th align='left'>Documento</th><th id=thAccion align='left'>Acci\u00F3n</th></tr></thead><tbody>";
	for(var i=0;i<len;i++){
		doctoPorTipo=lProd[i].documentoProbatorio.documentoPorTipo;
		cveDoc=doctoPorTipo.idDocumentoPorTipo;
		if(lProd[i].documentoProbatorio.cifrado!=null){
			tipoArch=lProd[i].documentoProbatorio.cifrado;
		}else{
			tipoArch="Sin Digitalizaci\u00F3n";
		}
		pinta += "<tr>"+
		
				"<td>"+doctoPorTipo.tipoDocumentoProbatorio.descripcion+"</td>"+	
				"<td>"+doctoPorTipo.documento.desDocumento+"</td>"+
				'<td><button id="eliminaDoc' + i + '" onclick="eliminaDoc(' + cveDoc + ');" class="mboton"  type="button" >Eliminar</button></td>'+
				"<tr>"
			;
		doctosCargadosLenght=i;
	}
	
	
	pinta+="</tbody></table>"
	$('#listaDocCargados').html(pinta);
	
	if(len==0){
		$('#divFileUploadDocs').hide();
		$("#docProbTramDiv").hide();
	}else{
		$('#divFileUploadDocs').show();
		$("#docProbTramDiv").show();
		initMuestraDocumentosTramiteSession();
	}
}



function cancelUploadify(){
	$.getJSON("/${mvn.web.app.root}/fileupload/cancelUploadify",
			{},              
			function(data) {
				
			}   
			
	).success(function() {  }) 
	.error(function(jqXHR, textStatus, errorThrown) {
		error(errorThrown+" - "+'Ocurri� un error al cancelar la carga.');
	 }).complete(function() {  });; 
}
//llama a el formulario de captura del doc correspondiente
function capturaDocfileUpload(){
	
	var idDoc=$('#idDocSelected').val();
	var idTipoDoc=$('#idTipoDocSelected').val();
	if(idDoc==-1||idTipoDoc==-1){
		//error
		$('#errorFileUploadDiv').html("Debe seleccionar el tipo de documento");
		$('#erroresFileUploadDiv').show();
	}else{
		//muestra
		capturaDocs($('#idDocSelected').val(),$('#idTipoDocSelected option:selected').html());
		$('#erroresFileUploadDiv').hide();
		//si el documento se capturo correctamente
	
	}
}



function showButtonCapturaCarga(){
	var idTipoDoc=$('#idTipoDocSelected').val();
	var idDocto=$('#idDocSelected').val();
	var rules;
	$.getJSON("/${mvn.web.app.root}/fileupload/infoDocSeleccionado",
			{cveTipoDoctoProb:idTipoDoc,cveDocto:idDocto},              
			function(data) {
				$('#browseDocDiv').show();
			}   
	).success(function() {  }) 
	.error(function(jqXHR, textStatus, errorThrown) {
		error(errorThrown+" - "+'No fue posible el documento seleccionado.');
	 }).complete(function() {  }); 
}

function hideButtonCaptura(){
	$('#capturaDoc').hide();
}

function alertDialogError(){

	$decision = $('<div></div');
	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 140,
		title : 'Error',
		modal : true,
		buttons : {
		
			"Aceptar" : function() {
				$decision.dialog('close');
				$decision.dialog('destroy');
				$decision.html('');
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text('\u00BF Ocurri\u00F3 un error interno intentelo mas tarde');
	$decision.dialog('open');

}

