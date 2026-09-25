$(function(){
	
	//establecemos el date picker
	$('#fechaAcuerdo').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : new Date(),
		yearRange : '-112:+0'
	});
	
	//establecemos las reglas de validacion
	$("#tramite").validate({ 
		rules: {
			numeroAcuerdo: {
				required: true,
				maxlength: 20
			},
			fechaAcuerdo: {
				required: true
			},
			observacion: {
				required: true,
				maxlength: 500
			}
		}, 
		errorLabelContainer: "#warning", 
		messages: { 
			numeroAcuerdo:{required: "Obligatorio",maxlength: "Debe ser m&aacute;ximo de 20 caracteres(tomando en cuenta espacios)"},
			observacion:{required: "Obligatorio", maxlength: "Debe ser m&aacute;ximo de 500 caracteres(tomando en cuenta espacios)"},
			fechaAcuerdo:{required: "Obligatorio"}
		} 
	});
	
	$("#aceptar").on("click", finalizaTramite);
	
	$("#cancelarTramiteAdmin").on("click", cancelarSolicitud);
	
	asignartextAreaLimites("observacion",{maxCharacters: 500});
});

var finalizaTramite = function() {
	
	var mensaje = "&iquest;Est&aacute; seguro que desea realizar el registro del acuerdo?";
	
	var $tramite = $("#tramite");
	if($tramite.valid()) {
		var $dialogo = $('<div></div');
		$dialogo.html(mensaje);
		$dialogo.dialog({
			autoOpen : false,
			title: "Confirmaci\u00F3n requerida",
			show: "blind",
			hide: "explode",
			resizable: false,
			modal: true,
			width: 500,
			buttons: {
				"Si" : function() {
					$(this).dialog('close');
					
					if($tramite.valid()) {
						
						$tramite.on("submit",$.blockUI);
						$tramite.attr("action",context_path + "/tramite/registroAcuerdo/finalizar");
						$tramite.submit();
					}
				}, 
				"No" : function() {
					$tramite.attr("action","#");
					$(this).dialog('close');
					
				}
				
			}
		}
		).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
		$dialogo.dialog('open');
	}
}

var cancelarSolicitud = function() {
	var tramiteRealizado = $("#tituloTramite").html().toLowerCase();
	var mensaje = "&iquest;Est&aacute; seguro que desea cancelar el tramite? La informaci&oacute;n capturada se perder&aacute;";
	var $dialogo = $('<div></div');
	$dialogo.html(mensaje);
	$dialogo.dialog({
		autoOpen : false,
		title: "Cancelaci\u00F3n",
		show: "blind",
		hide: "explode",
		resizable: false,
		modal: true,
		width: 500,
		buttons: {
			"Si" : function() {
				$(this).dialog('close');
				$.blockUI();
				location.href = context_path +"/inicio/grupoFamiliar";
			}, 
			"No" : function() {
				$(this).dialog('close');
			}
			
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$dialogo.dialog('open');
}
