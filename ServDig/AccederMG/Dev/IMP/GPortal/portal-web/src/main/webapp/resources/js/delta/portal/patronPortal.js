/**
 * 
 */
var dialogMensaje;
$(document).ready(
	function() {
		
		var nrp = $('#idPersonaWidgetCtrl').val();
		
		var objCookieEmision = $.cookie('imssdigitalAskEmision_' + nrp);
		//Si no existe entonces preguntamos...
		
		var options = {
				'path': '/',
				'domain':'.imss.gob.mx'
		};
		
		dialogMensaje = $( "#mensajeRecibirMail" ).dialog({
			title:"Notificaci\u00F3n Electr\u00F3nica",
			resizable: false,
			modal: true,
			width: 250,
			autoOpen:false,
			buttons: {
				"Aceptar": function() {
					
					$.cookie('imssdigitalAskEmision_' + nrp, true, options);
					
					$( this ).dialog( "close" );
				},
				"Cancelar": function() {
					$.cookie('imssdigitalAskEmision_' + nrp, false, options);
					$( this ).dialog( "close" );
				}
			}
		});
		
		if(objCookieEmision == null){
			
			dialogMensaje.dialog('open');
			
		}
		
		
	}
);