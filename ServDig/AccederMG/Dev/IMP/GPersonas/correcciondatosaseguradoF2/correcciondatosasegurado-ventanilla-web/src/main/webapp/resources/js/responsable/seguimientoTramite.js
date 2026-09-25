$(document).ready(function() {	
	if(banderaContinuarTramite ==="true"){
		var mensaje = "Tiene una solicitud de Correcci\u00f3n de Datos sin concluir \u00bfDesea continuar con la solicitud anterior o iniciar un nuevo registro?";
		$ventana = $('<div></div');

		$ventana.append(mensaje);
		$ventana.dialog({
			autoOpen : false,
			title : 'Mensaje',
			show : "blind",
			modal : true,
			height : 250,
			width : 450,
			closeOnEscape: false,
			open: function(event, ui) {
				$(".ui-dialog-titlebar-close", ui.dialog | ui).hide();
			},
			buttons : {
				"Cancelar" : function() {
					var url = context_path
//					+ '/wizard/correccionDatosAsegurado/cancelarSolicitud';
					+ '/wizard/correccionDatosAsegurado/';	
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
	
	$("#descargarCertificacion").click(descargarCertificacion);
});


var descargarCertificacion = function(){
	$('#formSeguimiento').submit();
	$.unblockUI();
};
