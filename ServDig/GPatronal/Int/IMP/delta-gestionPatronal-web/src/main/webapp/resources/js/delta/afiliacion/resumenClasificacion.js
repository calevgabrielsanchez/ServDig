var dialogoListaTramitesClasificacion;
var index=-1;
var listaTramitesClasificacion = "#listaTramitesClasificacion";
var indicadorTramiteClasifExistente;
$(function() {
	dialogoListaTramitesClasificacion =  $(listaTramitesClasificacion).dialog({
		autoOpen:false,
		resizable: false,
		height: 550,
		width: 400,
		modal: true,
		buttons: {'Continuar': validaSeleccionDeTramiteClasificacion}
	});
	
	
	dialogoTramiteClasifExistente = $("#tramitesClasificacionExistentes").dialog({
		autoOpen:false,
		resizable: false,
		height: 175,
		width: 400,
		modal: true,
		buttons: {'Aceptar': function(){ $( this ).dialog( "close" ); }}
	});
	
	$( "#selectable" ).selectable({
		selected: function(event, ui) {
			$(ui.selected).siblings().removeClass("ui-selected");
		},
		stop: function() {
			$( ".ui-selected", this ).each(function() {
				index = $( "#selectable li" ).index(this);
			});
		}
	});
	
	
});


function muestraOpciones(){
	validaTramiteExiste();
	if(indicadorTramiteClasifExistente=='true' || indicadorTramiteClasifExistente==true){
		dialogoTramiteClasifExistente.dialog('open');
	}else{
		dialogoListaTramitesClasificacion.dialog('open');
	}
}



function validaSeleccionDeTramiteClasificacion() {
	if(index != -1){
		$("#idSolicitud").val("");
		$("#idTramite").val($( "#selectable li" )[index].id);
		
		dialogoListaTramitesClasificacion.dialog('close');
		$.blockUI();
		if(context.indexOf("clasificacion") < 0){
			context += "/clasificacion/";
		}else{
			context += "/";
		}
		navegarTo('?idTramite='+ $( "#selectable li" )[index].id +'&idSolicitud=', 'clasificacionInvokerForm');
	}
}


function validaTramiteExiste(){
	
	var sSource = context_path +'/sujetoObligado/validaTramiteClasificacionExistente';	
	var request = $.ajax({
		url: sSource,
		async : false,
		type: "POST",
		data: idSujetoObligado ? JSON.stringify(idSujetoObligado) : null,
		dataType: "json",
        contentType: "application/json; charset=utf-8",
	});
	request.done(function(response){
		if(response.errors != undefined){
			alert( response.errors );
		}else{
			indicadorTramiteClasifExistente = response.existeTramiteClasificacion;
		}
	});
	
}
