var ssoVwUsuarioDTO;

/**
 * Metodo para generar el visor de archivos PDF´s.
 * @param cveDoctoAdjunto
 * @param cveNotificaciones
 */
function visorArchivoPDF(cveDoctoAdjunto, cveNotificaciones) {
	var urlImagen = getAppContextParaJS() + "/servlet/MostrarArchivoServlet";
	var documentosAdjuntosDTO = '{'
	  + '"cveDoctoAdjunto":"' + cveDoctoAdjunto + '",'
	  + '"notificacionesDTO":{"cveNotificaciones":"'+cveNotificaciones+'"}}';
	var jsonDocumentosAdjuntosDTO = jQuery.parseJSON(documentosAdjuntosDTO);
	$.postJSON_Sync(getAppContextParaJS()+"/estrados/obtenerDocumentoAdjunto.do", jsonDocumentosAdjuntosDTO, function(data) {
	}).error(function(data) {
		
	}).complete(function(data) {
		window.open(urlImagen,"PresentacionCorrecion","menubar=1,resizable=1,width=500,height=500");
	});
}




function visualizaAcuse(tramite){
	
	var visorDialog = $('#visor').dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		title:"Visor de Acuse",
		width: 890,
		height: 400,
		buttons: {
			"Aceptar": function() {
				$(this).dialog("close"); 
				$('#firmaIframe').hide(); 
			}
		}
	});


	//$('form#formaAcuse #idTramite').val('{"tramite":"'+ 'a7cd107b-16c3-48c0-9c3b-1ae7e0d7326d'+'"}');	
	$('form#formaAcuse #idTramite').val('{"tramite":"'+ tramite+'"}');			
	$('#formaAcuse').submit();
	visorDialog.dialog('open');
	$('#firmaIframe').show();
}




function recuperaDatosHeader(){
	var resultado;
	$.postJSON_Sync(getAppContextParaJS()+"/estrados/consultHeader.do", null,function(data) {
		$("#usuario").html("<b>Usuario: </b>"+data.desUsrCURP);
		$("#fecha").html("<b>Fecha: </b>"+data.fechaSistema);
		
		if(data.desDelegacion!=null && data.desDelegacion!='null'){
			$("#delegacion").html("<b>Delegaci&oacute;n: </b>"+data.desDelegacion);
		}else{
			$("#delegacion").html("<b>Delegaci&oacute;n:</b>");
		}
		
		
		if(data.desSubdelegacion!=null && data.desSubdelegacion!="null"){
			$("#subdelegacion").html("<b>Subdelegaci&oacute;n: </b>"+data.desSubdelegacion);	
		}else{
			$("#subdelegacion").html("<b>Subdelegaci&oacute;n: </b>");	
		}
		
		if(data.desAreaNorma!=null && data.desAreaNorma!='null'){
			$("#areaNorma").html("<b>Area Normativa: </b>"+data.desAreaNorma);	
		}else{
			$("#areaNorma").html("<b>Area Normativa: </b>"+data.desAreaNorma);	
		}
		
		if(data.desDepartamento!=null && data.desDepartamento!="null"){
			$("#departamento").html("<b>Departamento: </b>"+data.desDepartamento);	
		}else{
			$("#departamento").html("<b>Departamento: </b>"+data.desDepartamento);	
		}
		ssoVwUsuarioDTO = data;
	}).error(function(data){
		
	}).complete(function(data){
		
	});	
	return resultado;
}