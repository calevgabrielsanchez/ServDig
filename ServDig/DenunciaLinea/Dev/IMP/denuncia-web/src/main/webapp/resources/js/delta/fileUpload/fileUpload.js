//Ojo la ruta URL es relativa a donde se esta parado.
//con este se inocializa el componente cuando se tiene la solicitud
//inicializa el componente cuando no se tiene la solicitud para guardar usar doSaveDoc(solicitud)
var fileUploadFinish=false;
var doctosCargadosLenght=0;
var flashVerReq=6;

$(document).ready(function() {
	
	 
	/*
	var output = "You have Flash player " + playerVersion.major + "." + playerVersion.minor + "." + playerVersion.release + " installed"; 
	alert(output);
	*/

	try {
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
	'fileSizeLimit'	: '5000KB',
	'fileObjName'	:'file' ,
	'debug' 		: false,
	'buttonText' 	: 'Seleccionar Documento ...',
	'width' 		: 200,
	'onCancel' 		: 	function(file) {
							cancelUploadify();
						}
	});
});


function loadFileUpload(tipoTramite,idTramite){

		$.getJSON(contextPath + "/fileupload/init",
			{
				cveIdTipoTramite:tipoTramite,
				cveIdTramite:idTramite,
				
			},              
			function(data) {
				$('#allFileUploadDiv').show();
				$('#divFileUploadDocs').hide();
				$('#guardaDocumentosButton').hide();	
				$('#erroresFileUploadDiv').hide();
				$('#browseDocDiv').hide();
				$('#capturaDoc').hide();
				
				pintaOptions(data);
			
				//recarga el combo documento
				cargaDocumentosDelTipo();
				fileUploadFinish=false;
			}   
			
		); 
	
}

//guarda los documentos Fuera del cargador requiere idSolicitud y persona se utiliza con loadFileUpload(tramite)
function doSaveDocS() { 
	$.getJSON(contextPath + "/fileupload/saveDoc",
			{},              
			function(data) {
				//bloquear eliminar
				//mostrar mensage de datos guardados
				cargaFinalizada(data);
				//oculta el boton guardar
				$('#guardaDocumentosButton').hide();
				fileUploadFinish=true;
			}           	
	); 
}


 
//cargaun documento
function getLisDoc(){  
	//validar que seleccione
	if($('#idDocSelected').val()==-1){
		$('#errorFileUploadDiv').html("Deve seleccionar El tipo de Documento");
		$('#erroresFileUploadDiv').show();
	}else{
		$('#erroresFileUploadDiv').hide();
		$.getJSON(contextPath + "/fileupload/loadDoc",
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
			
					$('#file_upload').uploadifyClearQueue();
					//recarga el combo documento
					cargaDocumentosDelTipo();
		
				}
			
			
			}           	
		);  
	}
}   



function eliminaDoc(sel){  

	$.getJSON(contextPath + "/fileupload/eliminaDoc",
			{cveIdDocumentoPorTipo: sel},              
			function(data) {
				pintaOptions(data);  
				pintaListaCargados(data);
			}           	
	);  
}   

function cargaFinalizada(data){
	var lenght=doctosCargadosLenght+1;
	$('#thAccion').hide();
	for(var i=0;i<lenght;i++){
		$('#eliminaDoc'+i).hide();
	}
	
	$('#fileUploadMessages').html("<center>Los archivos se han guardado correctamente</center>");

}

function pintaOptions(data){

	var html = '';     
	var lisDoc=data.tipoDocumentoProbatorioList;
	var len = lisDoc.length;
	
	var i=0;
	html +='<option value="-1">--Seleccione--</option>';
	for(i=0;i<len;i++){
		
		html += '<option value="' + lisDoc[i].idTipoDocumentoProbatorio + '" >' + lisDoc[i].descripcion + '</option>';

	}

	if(len==0){
	
		$('#fieldSelDoc').hide();
		$('#guardaDocumentosButton').show();
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
	$.getJSON(contextPath + "/fileupload/cargaDocumentosDelTipo",
			{cveIdTipoDocProb:idTipoDocProb},              
			function(data) {
			
				pintaOptionsDocumenntos(data);
			
			}   
			
	); 
}


function pintaListaCargados(data){
	//var pinta=data.loadDoc.ditProductoSolicitud.nomArchivo;
	var pinta;
	var lProd=data.documenProbatorioCapturaList;
	var len = lProd.length;
	var cveDoc;
	var doctoPorTipo;
	pinta="<table width=100% id=listaDocCargadosTable> <tr align='left'><th align='left'>Tipo Documento</th><th align='left'>Documento</th><th align='left'>Tipo</th><th id=thAccion align='left'>Accion</th></tr>";
	for(var i=0;i<len;i++){
		doctoPorTipo=lProd[i].documentoProbatorio.documentoPorTipo;
		cveDoc=doctoPorTipo.idDocumentoPorTipo;
		pinta += "<tr>"+
		
				"<td>"+doctoPorTipo.tipoDocumentoProbatorio.descripcion+"</td>"+	
				"<td>"+doctoPorTipo.documento.desDocumento+"</td>"+
				"<td>"+lProd[i].documentoProbatorio.cifrado+"</td>"+
				'<td><button id="eliminaDoc' + i + '" onclick="eliminaDoc(' + cveDoc + ');" class="mboton"  type="button" >Eliminar</button></td>'+
				"<tr>"
			;
		doctosCargadosLenght=i;
	}
	
	
	pinta+="</table>"
	$('#listaDocCargados').html(pinta);

	
	if(len==0){
		$('#divFileUploadDocs').hide();
	}else{
		$('#divFileUploadDocs').show();
	}
}




















function cancelUploadify(){
	$.getJSON(contextPath + "/fileupload/cancelUploadify",
			{},              
			function(data) {
				
			}   
			
	); 
}
//llama a el formulario de captura del doc correspondiente
function capturaDocfileUpload(){
	
	var idDoc=$('#idDocSelected').val();
	var idTipoDoc=$('#idTipoDocSelected').val();
	if(idDoc==-1||idTipoDoc==-1){
		//error
		$('#errorFileUploadDiv').html("Deve seleccionar El tipo de Documento");
		$('#erroresFileUploadDiv').show();
	}else{
		//muestra
		capturaDocs($('#idDocSelected').val());
		$('#erroresFileUploadDiv').hide();
		//si el documento se capturo correctamente
	
	}
}



function showButtonCapturaCarga(){
	var idTipoDoc=$('#idTipoDocSelected').val();
	var idDocto=$('#idDocSelected').val();
	var rules;
	$.getJSON(contextPath + "/fileupload/infoDocSeleccionado",
			{cveTipoDoctoProb:idTipoDoc,cveDocto:idDocto},              
			function(data) {
				rules=data.doctoReqTramite
				if(rules.refDocumentoCaptura==1){
					$('#capturaDoc').show();
				}else{
					$('#capturaDoc').hide();
				}
				if(rules.refDocumentoObligatorio==1){
					$('#browseDocDiv').show();
				}else{
					$('#browseDocDiv').hide();
				}
			}   
	); 
}

function hideButtonCaptura(){
	$('#capturaDoc').hide();
}


