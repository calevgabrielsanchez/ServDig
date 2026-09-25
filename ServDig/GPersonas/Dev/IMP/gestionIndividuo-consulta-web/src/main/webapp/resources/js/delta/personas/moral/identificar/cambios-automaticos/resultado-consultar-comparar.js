var objCtrl = parent.identificarCambiosAutomaticosPersonaMoralCtrl;

$(document).ready(function(){
	// se iniciliza el componente del acordeon con la opcion 'autoHeight: false' para que cada DIV colapsable tenga la altura de acuerdo a su contenido
 	$('#acordeon').accordion({
 		autoHeight: false,
 		collapsible: true,
 		change: function(event, ui){
 			setSizeWithinIframe(document);
 		}
	});
 	
 	$('#btnIntegrarCambios').click(function(event){
 		fnIntegrarCambios();
	});
 	
 	$('#btnCancelar').click(function(event){
 		objCtrl.cerrar();
	});
 	
 	$('span.label').tooltip();

});


//Ir al servicio de ICA
var fnIntegrarCambios = function(){
    
	var url = $('#forma').attr('action');
	
	$.postJSON(url, null, function(data) {
		objCtrl.setDatosSalida(data);
		objCtrl.cerrar(true);
	}).error(function(data) {
		fnProcesarErrores(data, "form#forma");
	});	
	       
};