var oDialogoCerrarSesion;

$(document).ready(function() {
	
	
	/*Inicializacion del dialogo
	 * de confirmacion de cerrar sesion*/
	oDialogoCerrarSesion = $(idDialogoCerrarSesion).dialog({
		autoOpen : false,
		resizable : false,
		height : 180,
		modal : true,
		buttons : {
			'Cancelar' : function() {
				$(this).dialog("close");
			},
			"Aceptar" : function(data) {
				$(this).dialog("close");
				$.blockUI();
				$.postJSON(context + "/movPat/internet/clean/acceso",null,
					function() {
					$.blockUI();
					location.href = context + "/movPat/internet/acceso";
				});
				
			}
		}
	});
	
	$('#hrefCerrarSesion').live( 'click' , function(){
		fnAbrirDialogoCerrarSesion();
	});
	
});

var fnAbrirDialogoCerrarSesion = function() {
	oDialogoCerrarSesion.dialog('open');
};