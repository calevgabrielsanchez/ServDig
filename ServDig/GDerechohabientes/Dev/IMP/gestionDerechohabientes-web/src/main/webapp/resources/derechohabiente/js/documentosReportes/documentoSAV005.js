$.getScript("/${mvn.web.app.root}/resources/js/jquery/jquery.fileDownload.js");
$.getScript("/${mvn.web.app.root}/resources/js/jquery/jquery.cookie.js");


/**
 * Mario Teran Blanco
 * IMSS (Instituto Mexicano del Seguro Social)
 * 20/04/2012
 * Script relacionado con las operaciones comunes de los tramites de baja
 */

$(document).ready(
	function() {
		
		//ponemos el estilo del grid a nuestra tabla
		$('#candidatos').dataTable( {
			
			bJQueryUI : true,
	        bFilter : false,
	        bInfo:true,
	        bSort: false,
	        "bPaginate": true,
	        "bAutoWidth" : true,
	        "iDeferLoading" : 0
	        });
		
		$('#aceptar').click(function() {
			
			
			var derechohabiente = $("input:radio[name=idCandidato]:checked").val();
			
			if(derechohabiente != undefined || derechohabiente != null){
				
				generarSAV005(derechohabiente);
			}	
			else
				errorNoSeleccionado();
		});

		
		
		$("#cancelar").click(
				function() {
					salir();
				}
		);
	}
);

function salir() {
	
	esperePorFavor();
	location.href = "" + context_path + "/inicio/grupoFamiliar";
}

function esperePorFavor() {
	$decision = $('<div></div');

	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 140,
		title : '',
		modal : true
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text('Espere un momento por favor...');
	$decision.dialog('open');
}

function errorNoSeleccionado() {
	$noSeleccionado = $('<div></div');

	$noSeleccionado.dialog({
		autoOpen : false,
		resizable : false,
		height : 140,
		title : '',
		modal : true,
		buttons : {
			"Aceptar" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$noSeleccionado.text('Debe seleccionar un derechohabiente');
	$noSeleccionado.dialog('open');
}

function generarSAV005(idDerechohabiente) {

	//var page= context_path + "/resources/js/delta/viewPdf.html";
	var direccion = context_path + "/reportesDocumentos/generaDocumentoSAV005/"+idDerechohabiente;
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



function cierraDialogo($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
}
