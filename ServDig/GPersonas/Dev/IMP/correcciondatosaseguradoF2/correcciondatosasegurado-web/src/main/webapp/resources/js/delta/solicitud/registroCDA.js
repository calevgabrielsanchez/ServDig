$(document).ready(function() {
	
	$( "#fechaNacimiento" ).datepicker();
	$( "#fechaNacimiento" ).datepicker( "option", "dateFormat", 'dd/mm/yy' );
	
	
	
	
});


objDialog = $('#cancelarSolicitud')
.dialog(
		{
			autoOpen : false,
			resizable : false,
			height : 300,
			width : 600,
			modal : true,
			buttons : {
				"Aceptar" : function(data) {
					var url = context_path + '/atencionResponsable';
//							+ "/wizard/correccionDatosAsegurado/cancelarSolicitud";
					var form = $('form#consultaRenapoForm').attr(
							'action', url);
					form.attr('method', 'post');
					form.submit();
				},
				"Cancelar" : function(data) {
					$(this).dialog("close");
					return false;
				}
			}
		});
$('#cancelarSolicitudButton').click(function(e) {
e.preventDefault();
objDialog.dialog('open');
});objDialog = $('#cancelarSolicitud').dialog({
    autoOpen:false,
    resizable: false,
    height:200,
    width:400,
    modal: true,
    buttons: {
        "Aceptar": function(data) {
    		var url = context_path + '/atencionResponsable'; 
//    		+ "/wizard/correccionDatosAsegurado/cancelarSolicitud";
    		var form = $('form#consultaRenapoForm').attr('action', url);
    		form.attr('method', 'post');
    		form.submit();
        },
        "Cancelar": function(data) {
        	$( this ).dialog( "close" );
			return false;
        }
    }
});
$('#cancelarSolicitudButton').click(function(e) {
 e.preventDefault();
objDialog.dialog('open');
});

objDialog = $('#cancelarSolicitud').dialog({
    autoOpen:false,
    resizable: false,
    height:200,
    width:400,
    modal: true,
    buttons: {
        "Aceptar": function(data) {
    		var url = context_path + '/atencionResponsable'; 
//    		+ "/wizard/correccionDatosAsegurado/cancelarSolicitud";
    		var form = $('form#confirmarSolicitudForm').attr('action', url);
    		form.attr('method', 'post');
    		form.submit();
        },
        "Cancelar": function(data) {
        	$( this ).dialog( "close" );
			return false;
        }
    }
});
$('#cancelarSolicitudButton').click(function(e) {
 e.preventDefault();
objDialog.dialog('open');
});

$('#regresarPaginaAnterior').click(function(e) {
 e.preventDefault();
 window.history.back();
});

$(document)
.ready(
		function() {
			$('#descargarComp')
					.click(
							function(e) {
								e.preventDefault();

								var url = context_path
										+ '/wizard/correccionDatosAsegurado/descargarComprobante';

								$('form#folioTramiteForm').attr(
										'action', url);
								$('form#folioTramiteForm')
										.attr('alreadyDownloaded',
												'true');
								$('form#folioTramiteForm').submit();

							});
		});


