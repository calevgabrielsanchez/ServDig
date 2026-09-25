
function showCompSolRegistro(idSolicitud,titulo){
	var direccion=contextPath + "/documentos/comprobanteSolicitud?idSolicitud="+idSolicitud+"&titulo="+titulo;
	var page=contextPath + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");	
}

function showCompValRegistro(idTramite,titulo){
	var direccion=contextPath + "/documentos/documentoRegistro?idTramite="+idTramite+"&titulo="+titulo;
	var page=contextPath + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");	
}

function showCompRechazoSol(titulo,tipoTramite){
	var direccion=contextPath + "/documentos/rechazoSolicitud?titulo="+titulo+"&tipoTramite="+tipoTramite;
	var page=contextPath + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");	
}