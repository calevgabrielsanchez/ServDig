$(document).ready(
	function() {
		$("#validarInfo").click(mensajeConfirmacion);
		$("#cancelarTramite").click(mostrarMensajeCancelar);
		
		blockBackButton();
	}
);

var mensajeConfirmacion = function() {
	
	var curpAnterior = $("#fisicaAnterior\\.curp").val();
	var curpNueva = $("#fisicaNueva\\.curp").val();
	var mensaje = "<strong>Los datos del tr&aacute;mite son los siguientes: </strong><br><br>";
	mensaje += "<strong>CURP anterior:</strong> "+ curpAnterior + "<br>";
	mensaje += "<strong>CURP actual:</strong> "+ curpNueva + "<br><br>";
	mensaje += "Su informaci&oacute;n ser&aacute; actualizada. &iquest;Desea continuar?"
	
	var mensajeConfirmacion =  $( "#dialog-confirm" );
	mensajeConfirmacion.html(mensaje);
	mensajeConfirmacion.dialog({
		autoOpen : false,
		title: 'Confirmacion requerida',
		resizable: false,
		closeOnEscape: false,
		modal: true,
		heigth: 'auto',
		width: 'auto',
		buttons: {
			"Aceptar" : function() {
				$("#datosPersonaForm").attr("action","/portal-ciudadano-web-externo/asegurados/tramite/actualizacion/finalizar");
				$("#datosPersonaForm").submit();
	           $(this).dialog("close");
	        }, 
	        "Cancelar" : function() {
	        	$("#datosPersonaForm").attr("action","#");
	        	$(this).dialog("close");
	        }
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	mensajeConfirmacion.dialog('open');
}

function mostrarMensajeCancelar() {
	var consinNSS = $("#conSinNSS").val();
	var mensajeError =  $( "#dialog-error" );
	mensajeError.html("Toda la informaci&oacute;n capturada hasta el momento se perder&aacute;, &iquest;Est&aacute; seguro que desea cancelar?");
	mensajeError.dialog({
		autoOpen : false,
		resizable: false,
		closeOnEscape: false,
		modal: true,
		heigth: 'auto',
		width: 'auto',
		buttons: {
			"Si" : function() {
	          $(this).dialog("close");
	          $.blockUI();
	          
	          location.href = '/portal-ciudadano-web-externo/asegurados/tramite/actualizacion/'+consinNSS;
	        },
	        "No" : function() {
		          $(this).dialog("close");
		     }
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	mensajeError.dialog('open');
}