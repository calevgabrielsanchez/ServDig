var idPersona;

$(function(){
	
	idPersona = $('#idPersonaCommon').val();
	
	$('#cerrarSesionLink').live( 'click' , function(){
		fnAbrirDialogoCerrarSesion();
	});
	
	// peticion para obtener el detalle de la persona firmada
    var urlPersona = "/gestionIndividuo-consulta-web/servicios/internos/persona/fisica/" + idPersona;
	$.get( urlPersona , null, function(data){
		
		$("#resumenpersona").html(data);
	});

});
