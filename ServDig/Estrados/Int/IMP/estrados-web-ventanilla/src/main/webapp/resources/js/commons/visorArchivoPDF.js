/**
 * Metodo para generar el visor de archivos PDF�s.
 * @param cveDoctoAdjunto
 * @param cveNotificaciones
 */
function visorArchivoPDF(cveDoctoAdjunto, cveNotificaciones) {
	var urlImagen = getAppContextParaJS() + "/servlet/MostrarArchivoServlet";
	var documentosAdjuntosDTO = '{'
	  + '"cveDoctoAdjunto":"' + cveDoctoAdjunto + '",'
	  + '"notificacionesDTO":{"cveNotificaciones":"'+cveNotificaciones+'"}}';
	var jsonDocumentosAdjuntosDTO = jQuery.parseJSON(documentosAdjuntosDTO);
	$.blockUI();
	$.postJSON_Sync(getAppContextParaJS()+"/estrados/obtenerDocumentoAdjunto.do", jsonDocumentosAdjuntosDTO, function(data) {
	}).error(function(data) {
		
	}).complete(function(data) {
		window.open(urlImagen,"PresentacionCorrecion","menubar=1,resizable=1,width=500,height=500");
		$.unblockUI();
	});
}

function visorManualUsuario() {
	var urlImagen = getAppContextParaJS() + "/servlet/MostrarArchivoServlet";
	$.blockUI();
	$.postJSON_Sync(getAppContextParaJS()+"/estrados/recuperaManualUsuario.do", null, function(data) {
	}).error(function(data) {
		
	}).complete(function(data) {
		window.open(urlImagen,"PresentacionCorrecion","menubar=1,resizable=1,width=500,height=500");
		$.unblockUI();
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

function salirAplicacion() {
	$("#redireccionListado").submit();
}

function descargaManualUsuario(){
	visorManualUsuario();
}

function logOut(){
	document.location.href =getAppContextParaJS()+'/j_spring_security_logout';
}