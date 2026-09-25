


function hideUmf(radio){
	  jQuery('#seccionUmf').hide();
}

function showUmf(radio){
	  jQuery('#seccionUmf').show();
}


function validaDatosPerfil(){
	var nomPerfil;
	var radioButPerfil = document.getElementsByName("perfil");
	
	var perfilOK = true;
	var formPerfil= document.getElementById("normativoCEForm");
	for (var i=0; i<radioButPerfil.length; i++) {
		if (radioButPerfil[i].checked == true) { 
			nomPerfil = radioButPerfil[i].value;
			}
	}
	
	if(combosComunesCtrl.delegacion == 0){
		alert("Es necesario seleccionar una Delegacion");
		perfilOK=false;
		return;
	}
	
	if(combosComunesCtrl.subdelegacion == 0){
		alert("Es necesario seleccionar una Subdelegacion");
		perfilOK=false;
		return;
	}
	
	if(nomPerfil == 1 && combosComunesCtrl.clinica ==0){
		alert("Para el perfil TRAMITADOR es necesario seleccionar una clinica");
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

