var dialogoConfirmar;
var dialogoConfirmarCancelar;

$(document).ready(function() {

	$('#btnFinalizarTramite').click(function() {
		finalizarTramite();
	});

	$('#btnInicioCancelarTramite').click(function(){
		cerrarWizard();
	});
	
	$('#btnInicioCerrarTramite').click(function(){
		cerrarWizard();
	});
	
	
});

function finalizarTramite() {
	$("#formFinalizarTramite").submit();
}


function cerrarWizard(){
	parent.registrosEventualesWizard.cerrar();
}