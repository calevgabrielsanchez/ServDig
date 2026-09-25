var dataDenuncias;

$(document).ready(function() {	
	bloquear();
	$.postJSON("../registro/consultarDenuncias.do", null,function(data) {
		dataDenuncias=data;
		generaTabla(data);
	}).error(function(data){
		validarSesionExpirada(data);
	}).complete(function(data){
		desbloquear();
	});
});



function bloquear(){
	$.blockUI({ message:  '<h1>Procesando...</h1>', css: {             
		border: 'none',             
		padding: '15px',                          
		opacity: .5             
	} });
}


function desbloquear(){
	$.unblockUI();
}