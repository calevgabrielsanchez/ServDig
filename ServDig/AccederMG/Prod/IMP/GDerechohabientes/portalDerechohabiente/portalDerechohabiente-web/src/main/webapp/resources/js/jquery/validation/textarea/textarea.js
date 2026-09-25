	
	/**
 * Limita el número de caracteres en el text area
 * 
 * @param idTextArea
 * @param opciones
 */
function asignartextAreaLimites(idTextArea,opciones){
	
	try{
		
	
		var $textArea = $("#"+idTextArea);
		
		if( $textArea.length > 0){
			
			// ----------------------------------------------------------------
			// En caso de que el text area no este alineada a la izquierda
			// ----------------------------------------------------------------
			var tieneEventos = false;
			
			var settings = $.extend({
				events:["keyup","change","paste","input"],
				maxCharacters:255,
				status:true,
				statusClass:"status",
				statusText:"caracteres restantes.",
				notificationClass:"notification",
				showAlert:false,
				alertText:"Ha excedido el número máximo de caracteres permitidos.",
				slider:false,
				styles: {float:"right", display:"inline"}
			},opciones);
			
			var eventos = $textArea.data("events");
			$.each( settings.events,function(k,v){
				if(eventos && eventos[v]){
					tieneEventos = true;
					return;
				}
			} );
			
			if( !tieneEventos )
				$textArea.maxlength(settings);
		
		}
		
	}catch(e){
		//console.log(e);
	}
	
}