$(document).ready(function() {
});


//muestra el documento en ua pantalla emergente
function muestraDocumentoProbatorioModal(idDocumentoProbatorio,session) {
	
	var $muestraDocs = $('<div id="documentoProbatorioModalDiv"></div');
	$muestraDocs.dialog({
		autoOpen : false,
		title: 'Documento Probatorio',
		show: "blind",
		hide: "explode",
		resizable: false,
		modal: true,
		height: 500,
		width: 600,
		buttons: {
		
			"Cerrar": function() {
				
				$(this).dialog('close');
				$(this).dialog('destroy');
				$(this).html('');
			}
		}
	
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	$muestraDocs.html('Cargando Datos...');
	var ajaxSource = context_path + '/fileread/muestraDocumentoProbatorio';
	//si es por session
	if(session){
		ajaxSource = context_path + '/fileread/muestraDocumentoProbatorioSession';
	}
	
	var solicitud = {'idDocumentoProbatorio': idDocumentoProbatorio};
	$muestraDocs.load(ajaxSource,solicitud);
	$muestraDocs.dialog('open');

	
}




function showDoc(idDocPro){
	var direccion=context_path + "/fileread/muestraDigitalizacion?idDocumentoProbatorio="+idDocPro;
	var page=context_path + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable: yes,dialogwidth: 1000,dialogheight:1000,scroll:on");	
}



//Busca documentos en base de datos Init
function initMuestraDocumentosTramite(tramite){

	var ajaxSource = context_path + '/fileread/initMuestraDocumentosTramite';
	$('#docProbTramDiv').load(ajaxSource);
	muestraDocumentosTramite(tramite);
}
//Muestra los documentos en session Init
function initMuestraDocumentosTramiteSession(){
	var ajaxSource = context_path + '/fileread/initMuestraDocumentosTramite';
	$('#docProbTramDiv').load(ajaxSource);
	muestraDocumentosTramiteSession();
}


//Muestra Documentos session
function muestraDocumentosTramiteSession(){
	var session=true;
	var uri="/fileread/listaDocTramiteSession";

	$.getJSON(context_path + uri,
			{},              
			function(data) {
				$('#divFileReadAll').show();
				listaDocPrint(data,session);
			}   
	); 
}
//Muestra Documentos 
function muestraDocumentosTramite(idTramite){
	var session=false;
	var uri="/fileread/listaDocTramite";

	$.getJSON(context_path + uri,
			{'idTramite': idTramite},              
			function(data) {
				$('#divFileReadAll').show();
				listaDocPrint(data,session);
			}   
	); 
}


function listaDocPrint(data,session){
	//pintar la tabla de la lista y sus botones
	
	var pinta;
	var lProd=data;
	var len = lProd.length;
	var cveDoc;
	pinta="<table width='100%' id='docTramiteTable'> <thead><tr align='left'><th>Tipo Documento</th><th>Documento</th><th>Acci\u00F3n</th></tr></thead><tbody>";
	for(var i=0;i<len;i++){
		idocPro=lProd[i].idDocumentoProbatorio;
	
		pinta += "<tr align='left'>"+
				"<td>"+lProd[i].documentoPorTipo.tipoDocumentoProbatorio.descripcion+"</td>"+
				"<td>"+lProd[i].documentoPorTipo.documento.desDocumento+"</td>"+	
				'<td><button class="mboton"  type="button" id="showDoc' + i + '" onclick="muestraDocumentoProbatorioModal(' + idocPro + ','+ session +');" >Mostrar</button></td>'+
				
				"<tr>"
			;
	}
	
	pinta+="</tbody></table>"
	
	if(len==0){
		pinta="No existen documentos probatorios para el tr&aacute;mite";
		
	}else{
		
	}
	$('#divListDoc').html(pinta);
	//formatodataTable();
}

function formatodataTable(){
	$('#docTramiteTable').dataTable( {
		sScrollX: "100%",
		bJQueryUI : false,
        bFilter : false,
        bInfo:false,
        bSort: false,
        "bPaginate": false,
        "bAutoWidth" : true,
        "iDeferLoading" : 0
        });
}
