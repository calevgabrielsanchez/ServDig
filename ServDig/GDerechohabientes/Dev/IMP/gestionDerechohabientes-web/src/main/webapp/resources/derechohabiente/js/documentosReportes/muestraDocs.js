function showReporte(reporte){
	
	 
	var direccion="";
	switch(reporte){
		case 'SAV002':
			direccion=contextPath + "/reportesDocumentos/documentoSAV002?_="+Date.now()+"&idTramite="+10+"";
			break;
		case 'SAV005':
			direccion=contextPath + "/reportesDocumentos/documentoSAV005?_="+Date.now();
			break;
		case 'SAV007':
			direccion=contextPath + "/reportesDocumentos/documentoSAV007?_="+Date.now();
			break;
		case 'SAV010':
			direccion=contextPath + "/reportesDocumentos/documentoSAV010?_="+Date.now();
			break;	
		case 'SAV017':
			direccion=contextPath + "/reportesDocumentos/documentoSAV017?_="+Date.now();
			break;	
		case 'reporte4305A':
			direccion=contextPath + "/documentos/reporte4305A?_="+Date.now();
			break;	
		case 'comprobanteVD':
			direccion=contextPath + "/documentos/comprobanteVD?_="+Date.now();
			break;	
	}
	

	
	//var page=contextPath + "/resources/js/delta/viewPdf.html";
	//window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");
	
	$.fileDownload(direccion,{
		prepareCallback: function (url) {
			$.removeCookie("fileDownloadError",{path:'/'});
			$.removeCookie("fileDownload",{path:'/'});
			$.blockUI.defaults.baseZ = 10000;
			$.blockUI();
		}
	}).always(function(){
		
		$.unblockUI();
		$.blockUI.defaults.baseZ = 1000;
		$.removeCookie("fileDownload",{path:'/'});
		
		if(document.cookie.search("fileDownloadError") >= 0){
			
			$.removeCookie("fileDownloadError",{path:'/'});
			
			$decision = $('<div></div');
			$decision.dialog({
				autoOpen : false,
				resizable : false,
				width : 340,
				title : 'Error',
				modal : true,
				buttons : {
					"Aceptar" : function() {
						$(this).dialog('close');
						$(this).dialog('destroy');
						$(this).html('');
					}
				}
			}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

			$decision.text('Ocurri\u00F3 un error al generar el reporte');
			$decision.dialog('open');
			
		}
		
	});
	
}