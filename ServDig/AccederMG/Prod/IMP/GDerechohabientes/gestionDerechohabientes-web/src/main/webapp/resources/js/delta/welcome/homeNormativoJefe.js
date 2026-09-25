
function validaDatosPerfil(){
	var nomPerfil;
	var radioButPerfil = document.getElementsByName("nivelreporte");
	
	var perfilOK = true;
	var formPerfil= document.getElementById("normativoCEForm");
	for (var i=0; i<radioButPerfil.length; i++) {
		if (radioButPerfil[i].checked == true) { 
			nomPerfil = radioButPerfil[i].value;
			}
	}
	
	if(combosComunesCtrl.delegacion == 0 && nomPerfil == 2){
		alert("Es necesario seleccionar una Delegacion");
		perfilOK=false;
		return;
	}
	
	if(combosComunesCtrl.subdelegacion == 0 && nomPerfil == 2){
		alert("Es necesario seleccionar una Subdelegacion");
		perfilOK=false;
		return;
	}

	if(perfilOK){
		formPerfil.submit();
	}
	
}

$(document).ready(function(){
	//inicializamos los combos
	combosComunesCtrl.init();
	$('#subdelegado').click(function(){
		hideUmf(this);	
	});
	
	$('#tramitador').click(function(){
		showUmf(this);	
	})
	$('#cancelar').click(function(){
		combosComunesCtrl.limpiarCombos();
	});
	
	$('#aceptarVal').click(function(){
		validaDatosPerfil();
	});
	
	
});

