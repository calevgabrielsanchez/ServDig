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
				var tieneDomicilio = $("#dom"+derechohabiente).val() == 1;
				
				if(tieneDomicilio) {
					generarCartillaNacionalSalud(derechohabiente);
				} else {
					errorSinDomicilio();
				}
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
	$.blockUI();
	location.href = "" + context_path + "/inicio/grupoFamiliar";

}

function errorSinDomicilio() {
	mostrarAlertError('Para la impresi&oacute;n de la cartilla se requiere que el derechohabiente cuente con un domicilio, por favor realice el tr&aacute;mite de correcci&oacute;n de datos y asigne el domicilio.');
}

function errorNoSeleccionado() {
	mostrarAlertError('Debe seleccionar un derechohabiente');
}

function mostrarAlertError(mensaje){
	var mensajeError = '<div class="ui-widget">' +
	'<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">'+
	'<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>' +
	'<strong>'+mensaje+'</strong></p></div></div>';

	$razonRechazo = $('<div></div');
	$razonRechazo.html(mensajeError);
	$razonRechazo.dialog({
		autoOpen : false,
		title: 'Error',
		resizable: false,
		modal: true,
		width: 500,
		buttons: {
			"Cerrar": function() {
				cierraDialogo($(this));
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$razonRechazo.dialog('open');

}


function generarCartillaNacionalSalud(idDerechohabiente) {

	//var page= context_path + "/resources/js/delta/viewPdf.html";
	var direccion = context_path + "/reportesDocumentos/generarCartillaNacionalSalud/"+idDerechohabiente;
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



function cierraDialogo($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
}
