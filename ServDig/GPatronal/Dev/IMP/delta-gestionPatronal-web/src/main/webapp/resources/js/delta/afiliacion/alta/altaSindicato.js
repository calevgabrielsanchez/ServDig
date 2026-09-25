function inicializaFechasSindicato(){
	inicializaFecha("#txtFechaRegistroSindicato", "+60D");
}

function attachSindicato(sujetoTramite){
	//nos aseguramos que solo se agregue la info de sindicato cuando
	//se seleccionó registro sindicato
	if(tipoActa!=2 && sujetoTramite.moral!=undefined)
		sujetoTramite.moral.registroSindicato = undefined;
}


$(document).ready(
		function(){
			inicializaFechasSindicato();
});