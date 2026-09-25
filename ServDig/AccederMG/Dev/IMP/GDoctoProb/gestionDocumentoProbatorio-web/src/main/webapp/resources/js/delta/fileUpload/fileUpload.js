
var fileUploadFinish=false;
var doctosCargadosLenght=0;
var flashVerReq=6;
var guardarInterno=true;
var listDocReq;
var funcionCallBackPinta = null;

function loadFileUpload(tipoTramite, listaNuevosRequeridos, idTramite){
	if(idTramite==null){
		guardarInterno=false;
	}
	
	
	if(listaNuevosRequeridos == undefined){
		listaNuevosRequeridos = "";
		
	}	
	
		$.blockUI();
		$.getJSON(context_path + "/fileupload/init",
				{
				cveIdTipoTramite:tipoTramite,
				cveIdTramite:idTramite,
				nuevosDocsList:listaNuevosRequeridos
				},              
				function(data) {
					$('#allFileUploadDiv').show();
					$('#divFileUploadDocs').hide();
					$('#guardaDocumentosButton').hide();	
					$('#erroresFileUploadDiv').hide();
					$('#browseDocDiv').hide();
					//$('#capturaDoc').hide();

					pintaOptions(data);	
					
					documentosObligatorios = data.tipoDocumentoProbatorioReqList.length;
					//console.debug("El numero de archivos obligatorios es %s", documentosObligatorios);
					if(typeof parent.WizardCapturaDocumentosProbatoriosCtrl !== "undefined") {
						parent.WizardCapturaDocumentosProbatoriosCtrl.setNumeroDocumentosRequeridos(documentosObligatorios);
					}
					
					$.unblockUI();
					
					//recarga el combo documento
					//cargaDocumentosDelTipo();
					//pintaListaCargados(data);
					//fileUploadFinish=false;
					//fileUploadFinish=!data.necesarios;
					
				}   

		).success(function() {  }) 
		.error(function(jqXHR, textStatus, errorThrown) {
			$.unblockUI();
			error(errorThrown+" - "+'No fue posible cargar la lista de documentos probatorios para este tr\u00E1mite');
		 }).complete(function() {  });
		 
}


//guarda los documentos pasandole el tramite
function doSaveDocSinTramite(idTramite) {
	$.blockUI();
	if(fileUploadFinish){
		$.getJSON(context_path + "/fileupload/saveDocSinTramite",
			{cveIdTramite:idTramite},              
			function(data) {
				$.unblockUI();
			}           	
		).success(function() {  }) 
		 .error(function(jqXHR, textStatus, errorThrown) {
			 $.unblockUI();
			fileUploadFinish=false;
			error(errorThrown+" - "+'No fue posible guardar los documentos probatorios para este tr\u00E1mite');
		 }).complete(function() {  });; 
	}
}


function doSaveDocS() { 
	$.blockUI();
	$.getJSON(context_path + "/fileupload/saveDoc",
			{},              
			function(data) {
				cargaFinalizada();
				
				//oculta el boton guardar
				$('#guardaDocumentosButton').hide();
			
				fileUploadFinish=true;
				$.unblockUI();
			}           	
	).error(function(jqXHR, textStatus, errorThrown) {
			cargaFinalizada();
			//oculta el boton guardar
			$('#guardaDocumentosButton').hide();
			$.unblockUI();
			error(errorThrown+" - "+'No fue posible guardar los documentos probatorios para este tr\u00E1mite');
		 
	}); //agregar funcion de error 
}


 
//carga un documento
function getLisDoc(){  
	//validar que seleccione
	if($('#idDocSelected').val()==-1){
		$('#erroresFileUploadDiv').html("Debe seleccionar el tipo de documento");
		$('#erroresFileUploadDiv').show();
	}else{
		$('#erroresFileUploadDiv').hide();
		$.blockUI();
		$.getJSON(context_path + "/fileupload/loadDoc",
			{
				cveTipoDoctoProb: $('#idTipoDocSelected').val(),
				cveDocto: $('#idDocSelected').val()
			},              
			function(data) {
				if(data.error!=null){
					$('#erroresFileUploadDiv').html(data.error);
					$('#erroresFileUploadDiv').show();
				}else{
					pintaOptions(data);  
			
					pintaListaCargados(data);
			
					//$('#file_upload').uploadifyClearQueue();
					//recarga el combo documento
					cargaDocumentosDelTipo();
		
				}
			
				$.unblockUI();
			}           	
		).success(function() {  }) 
		.error(function(jqXHR, textStatus, errorThrown) {
			error(errorThrown+" - "+'No fue posible cargar el documento.');
		 }).complete(function() {  });  
	}
}   



function eliminaDoc(sel){  
	$.blockUI();
	$.getJSON(context_path + "/fileupload/eliminaDoc",
			{cveIdDocumentoPorTipo: sel},              
			function(data) {
				
				fileUploadFinish = false;
				pintaOptions(data);  
				pintaListaCargados(data);
				$.unblockUI();
				
				parent.$("body").trigger("eliminaDoc",data);
				
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

	
	if( funcionCallBackPinta != null && $.isFunction(funcionCallBackPinta) ){
		data = funcionCallBackPinta(data);
	}
	
	
	var html = '';     
	var lisDoc = data.tipoDocumentoProbatorioList;
	listDocReq = data.tipoDocumentoProbatorioReqList;
	
	var len = lisDoc.length;
	var tamListDocReq = listDocReq.length;
	
	var i=0;
	html +='<option value="-1">--Selecciona por favor--</option>';
	for(i=0;i<len;i++){
		html += '<option value="' + lisDoc[i].idTipoDocumentoProbatorio + '" >' + lisDoc[i].descripcion + '</option>';
	}
	
	//Si la lista de los doc requeridos es cero se puede continuar
	
	if(tamListDocReq == 0){
		fileUploadFinish=true;
	}
	if(len==0){
	
		$('#fieldSelDoc').hide();
		if(guardarInterno){
			$('#guardaDocumentosButton').show();
		}else{//en este punto ya se pueden guardar los documentos
			fileUploadFinish=true;
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
	html +='<option value="-1">--Selecciona por favor--</option>';
	for(i=0;i<len;i++){
		
		html += '<option value="' + data[i].cveIdDocumento + '">' + data[i].desDocumento + '</option>';
	}
	$('#idDocSelected').html(html);
	
}

function cargaDocumentosDelTipo(){

	var idTipoDocProb=$('#idTipoDocSelected').val();
	$.blockUI();
	$.getJSON(context_path + "/fileupload/cargaDocumentosDelTipo",
			{cveIdTipoDocProb:idTipoDocProb},              
			function(data) {
			
				pintaOptionsDocumenntos(data);
				
			$.unblockUI();
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
	pinta="<table width=\"100%\" id=\"listaDocCargadosTable\" class=\"table table-striped table-bordered\">" +
			"	<thead> " +
			"		<tr align='left'>" +
			"			<th align='left'>Tipo documento</th>" +
			"			<th align='left'>Documento</th>" +
			"			<th id='thAccion' align='left'>Acci\u00F3n</th>" +
			"		</tr>" +
			"	</thead>" +
			"	<tbody>";
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
				'<td><div class="pull-right"><button id="eliminaDoc' + i + '" onclick="eliminaDoc(' + cveDoc + ');" class="btn btn-default"  type="button" ><span class="glyphicon glyphicon-trash"></span> Eliminar</button></div></td>'+
				"</tr>"
			;
		doctosCargadosLenght=i;
	}
	
	
	pinta+="</tbody></table>";
	$('#listaDocCargados').html(pinta);
	parent.$("body").trigger("pintaListaCargados",data);
	
	if(len==0){
		$('#divFileUploadDocs').hide();
	}else{
		$('#divFileUploadDocs').show();
	}
	
}



function cancelUploadify(){
	$.getJSON(context_path + "fileupload/cancelUploadify",
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
	$("#formdocumentgeneral").valid();
	if(idDoc==-1||idTipoDoc==-1){
		//error
		$('#erroresFileUploadDiv').html("<strong>&iexcl;Error en el formulario!</strong> No has llenado todos los campos requeridos. Por favor verifica.");
		$('#erroresFileUploadDiv').show();
	}else{
		//muestra
		capturaDocs($('#idDocSelected').val(),$('#idTipoDocSelected option:selected').html() + " - " + $('#idDocSelected option:selected').html());
		$('#erroresFileUploadDiv').hide();
		//si el documento se capturo correctamente
	
	}
}



function showButtonCapturaCarga(){
	var idTipoDoc=$('#idTipoDocSelected').val();
	var idDocto=$('#idDocSelected').val();
	var rules;
	
	if(idDocto != "-1" && idDocto != "") {
		$("#erroresFileUploadDiv").hide();
	}
	
	$.getJSON(context_path + "/fileupload/infoDocSeleccionado",
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

