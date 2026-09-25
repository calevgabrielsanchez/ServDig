function generarSAV011Persona() {
	var direccion;
	
	var derechohabiente = $("input:radio[name=idCandidato]:checked").val();
	if(typeof (derechohabiente) ==  'undefined'){
		direccion = context_path + "/documentos/sav011/";
	}else{
		direccion = context_path + "/documentos/sav011Persona/"+derechohabiente;
	}
	
	var page= contextPath + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");

	
}
function generarSAV011Obst(idDerechohabiente) {
	
	var page= contextPath + "/resources/js/delta/viewPdf.html";
	var direccion = context_path + "/documentos/sav011Persona/"+idDerechohabiente;
	window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");

	
}

function generarSAV011() {
	//var page= contextPath + "/resources/js/delta/viewPdf.html";
	var direccion = context_path + "/documentos/sav011/";
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

			$decision.text('Ocurrio un error al generar el reporte');
			$decision.dialog('open');
			
		}
		
		
	});
	
	
}