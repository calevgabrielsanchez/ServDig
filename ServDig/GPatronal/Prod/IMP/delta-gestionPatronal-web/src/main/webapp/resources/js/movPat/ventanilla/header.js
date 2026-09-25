var oDialogoCerrarSesion;

$(document).ready(function() {
	
	var context = '/movPat-web-ventanilla';
	
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
				$.postJSON(context + "/movPat/acceso/logout",null,
					function() {
					$.blockUI();
					location.href = context + "/j_spring_security_logout";
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