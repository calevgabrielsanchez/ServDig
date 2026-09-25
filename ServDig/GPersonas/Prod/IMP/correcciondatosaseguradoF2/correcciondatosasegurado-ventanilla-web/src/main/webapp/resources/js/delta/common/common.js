function mensageConfirmacion(mensaje) {
	if( mensaje !== undefined && mensaje !== "" ){
	$ventana = $('<div></div');

	$ventana.append(mensaje);
	$ventana.dialog({
		autoOpen : false,
		title : 'Mensaje',
		show : "blind",
		modal : true,
		height : 250,
		width : 450,
		buttons : {
			"Aceptar" : function() {
				cierraDialogo($(this));
			}
		}

	});

	$ventana.dialog('open');
  }
}

function cierraDialogo($dialogo) {
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
}

objDialog = $('#cancelarSolicitud').dialog({
	autoOpen : false,
	resizable : false,
	height : 300,
	width : 600,
	modal : true,
	buttons : {
		"Aceptar" : function(data) {
			$("#cancelarForm").submit();
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
});

$('#regresarPaginaAnterior').click(function(e) {
	 e.preventDefault();
	 window.location.href = contextPath+"/wizard/correccionDatosAsegurado/datosAdicionalesHistoriaLaboral";	 
	});

$(document).ready(function() {

	document.charset = 'UTF-8';
	
	$('#cancelarCurpIncorrectaButton').click(function(e) {
		 e.preventDefault();
		 var mensaje = "En caso de que los datos sean incorrectos, se deber\u00e1 solicitar la respectiva regularizaci\u00f3n ante RENAPO.";
		 $ventana = $('<div></div');

			$ventana.append(mensaje);
			$ventana.dialog({
				autoOpen : false,
				title : 'Mensaje',
				show : "blind",
				modal : true,
				height : 250,
				width : 450,
				buttons : {
					"Aceptar" : function() {
						 var url = context_path + '/atencionResponsable';
//					      + '/wizard/correccionDatosAsegurado/cancelarSolicitud';
		   
						 $('form#consultaRenapoForm').attr('action', url);
						 $('form#consultaRenapoForm').submit();
					}
				}

			});

			$ventana.dialog('open');	 
	}); 



		});

