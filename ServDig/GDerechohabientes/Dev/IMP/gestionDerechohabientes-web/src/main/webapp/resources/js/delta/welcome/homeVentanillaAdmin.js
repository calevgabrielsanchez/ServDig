
function seleccionaOpcion(){
	var nomOpcion;
	var radioButOpcion = document.getElementsByName("opcion");
	var opcionOK = true;
	var formOpcion= document.getElementById("ventanillaAdminForm");
	for (var i=0; i<radioButOpcion.length; i++) {
		if (radioButOpcion[i].checked == true) { 
			nomOpcion = radioButOpcion[i].value;
			}
	}
	
	if(nomOpcion == 1){
		window.location.href = context_path + "/welcome/uno/busqueda";
	}
	
	if(nomOpcion == 2){
		window.location.href = context_path + "/continuarSolicitudActualizacionCorreo";
	}
	
}

$(document).ready(function(){

	$('#aceptarVal').click(function(){
		seleccionaOpcion();
	});
	
	
});

