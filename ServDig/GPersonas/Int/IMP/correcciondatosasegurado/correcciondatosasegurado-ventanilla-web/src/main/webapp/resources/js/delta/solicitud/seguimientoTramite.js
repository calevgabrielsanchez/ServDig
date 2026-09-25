$(document).ready(function() {
	if(banderaContinuarTramite ==="true"){
		var mensaje;
		if(mismaSubdelegacion ==="true"){
			mensaje = "Tiene una solicitud de Correcci\u00f3n de Datos sin concluir \u00bfDesea continuar con la solicitud anterior o iniciar un nuevo registro?";
		}else{
			mensaje = "Se tiene un registro de Solicitud de Correcci\u00f3n de Datos sin concluir asociada a esta CURP. \u00bfDesea continuar con la solicitud anterior o iniciar un nuevo registro?";
		}
	$ventana = $('<div></div');
	$ventana.append(mensaje);
	$ventana.dialog({
		autoOpen : false,
		title : 'Mensaje',
		show : "blind",
		modal : true,
		height : 250,
		width : 450,
		draggable:false,
		closeOnEscape: false,
		open: function(event, ui) {
			$(".ui-dialog-titlebar-close", ui.dialog | ui).hide();
		},
		buttons : {
			"Cancelar" : function() {
				var url = context_path
				+ '/wizard/correccionDatosAsegurado/cancelarSolicitud';		   
				$('form#concluirSolicitudForm').attr('action', url);
				$('form#concluirSolicitudForm').attr('method', 'POST');
				$('form#concluirSolicitudForm').submit();
			},
			"Continuar" : function(data) {
				var url = context_path
				+ '/wizard/correccionDatosAsegurado/obtenerInformacionRenapo';		   
				$('form#concluirSolicitudForm').attr('action', url);
				$('form#concluirSolicitudForm').submit();
			}		
		}

	});

	$ventana.dialog('open');
	
	
	}
	
	if(banderaContinuarTramiteAtendido ==="true"){
		var mensaje = "Si usted desea, podr\u00E1 hacerlo al t\u00E9rmino de 40 d\u00EDas posteriores a la fecha de expedici\u00f3n de la Certificaci\u00f3n.";
		$ventana = $('<div></div');

		$ventana.append(mensaje);
		$ventana.dialog({
			autoOpen : false,
			title : 'Mensaje',
			show : "blind",
			modal : true,
			height : 200,
			width : 450,
			closeOnEscape: false,
			open: function(event, ui) {
				$(".ui-dialog-titlebar-close", ui.dialog | ui).hide();
			},
			buttons : {
				"Aceptar" : function() {
					$ventana.dialog('close');
				}	
			}

		});

		$ventana.dialog('open');	 
	}
	
	$("#descargarCertificacion").click(descargarCertificacion);
});



var descargarCertificacion = function(){
	$('#formSeguimiento').submit();
	$.unblockUI();
}