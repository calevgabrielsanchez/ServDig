var objCtrl = parent.identificarCambiosAutomaticosPersonaFisicaCtrl;

$(document).ready(function(){
	/*
	 * se iniciliza el componente del acordeon con la opcion 'autoHeight: false'
	 * para que cada DIV colapsable tenga la altura de acuerdo a su contenido
	 */
 	$('#acordeon').accordion({
 		autoHeight: false,
 		collapsible: true,
 		change: function(event, ui){
 			setSizeWithinIframe(document);
 		}
	});

 	// se renderea la tabla de resultados del renapo como datateibol
 	var datateibolIMSSRENAPO = $('#tabla-imss-renapo').dataTable({
 		
 		// sScrollY: "20",
 		bInfo: false,
 		// bScrollCollapse: false,
		sPaginationType: null,
 		bJQueryUI: true,
 		bSort: false,
 		bFilter: false,
 	    bPaginate: false,
 	    bAutoWidth: true//,
 	    // iDisplayLength: 5

 	});
 	
 	// se renderea la tabla de resultados del sat como datateibol
 	var datateibolIMSSSAT = $('#tabla-imss-sat').dataTable({
 		
 		// sScrollY: "20",
 		bInfo: false,
 		// bScrollCollapse: false,
		sPaginationType: null,
 		bJQueryUI: true,
 		bSort: false,
 		bFilter: false,
 	    bPaginate: false,
 	    bAutoWidth: true//,
 	    // iDisplayLength: 5

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
