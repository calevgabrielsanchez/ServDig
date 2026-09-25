/*Dialogo de confirmacion para cerrar la sesion*/

$(function() {
	
	$( "#dialog-closesession" ).dialog({
			autoOpen:false,
			resizable: false,
			height:140,
			modal: true,
			buttons: {
				"Aceptar": function() {
					$("#form-logout").submit();
					$( this ).dialog( "close" );
				},
				"Cancelar": function() {
					$( this ).dialog( "close" );
				}
			}
		});

});

function logout(){
	
	$( "#dialog-closesession" ).dialog('open');
	
}

function iniciarSolicitud(){
	
	$("#form-solicitud").submit();
}