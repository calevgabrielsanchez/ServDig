function loadFileRead(tramite){
	alert("llamada al metodo");
	$.getJSON(contextPath + "/fileread/listaDocSolicitud",
			{idTramite:tramite,
		
			},              
			function(data) {
				alert("regreso del servicio");
				$('#divFileReadAll').show();
				listaDocPrint(data);
			}   
	); 
}

function listaDocPrint(data){
	//pintar la tabla de la lista y sus botones
	
	var pinta;
	var lProd=data.documentoProbatoriosList;
	var len = lProd.length;
	var cveDoc;
	pinta="<table width=100%> <tr align='left'><th>Tipo Documento</th><th>Tipo</th><th>Accion</th></tr>";
	for(var i=0;i<len;i++){
		idocPro=lProd[i].idDocumentoProbatorio;
	
		pinta += "<tr>"+
				"<td>"+lProd[i].tipoDocumentoProbatorio.descripcion+"</td>"+
				"<td>pdf</td>"+
				
				'<td><button id="showDoc' + i + '" onclick="showDoc(' + idocPro + ');" >Imprimir</button></td>'+
				
				"<tr>"
			;
	}
	
	pinta+="</table>"
	
	if(len==0){
		pinta="No existen documentos probatorios para el tramite";
		
	}else{
		
	}
	$('#divListDoc').html(pinta);
}

function showDoc(idDocPro){
	var direccion=contextPath + "/fileread/muestraReporte?idDocumentoProbatorio="+idDocPro;
	var page=contextPath + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable: yes,dialogwidth: 1000,dialogheight:1000,scroll:on");	
}
