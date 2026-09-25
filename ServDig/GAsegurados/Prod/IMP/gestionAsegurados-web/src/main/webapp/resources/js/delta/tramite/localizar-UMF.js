function obtenerUMFs() {
	
	var objAsentamiento = $('#asentamientoForUmfForm').serialize();
	
	var url = '/gestionDomicilios-web/widget/domicilio/utility/consulta/umf/asentamiento';
	
	$.post(url, objAsentamiento, function(data){
		$.getScript("/gestionDomicilios-web/static/resources/js/delta/domicilios/widget/UmfImssWidget.js",function(){
			$('#umfContenedor').html(data);

			// Se settea la función de callback para las UMF
			setFnCallback(fnSeleccionarUMF);
			
			// Se ejecuta la función de inicialización
			idUMFInicial = $('input#idUmfAsegurado').val();
			initComponenteUMFs();
		});
	}).error(function (data){
		$('#umfContenedor').html("<span>Existió un error al cargar las UMF's</span>");
	});
}

function obtenerUMFsByCodigoPostal(codigoPostal) {
	
	var url = '/gestionDomicilios-web/widget/domicilio/utility/umf/' + codigoPostal;
	
	$.get(url, function(data){
		$.getScript("/gestionDomicilios-web/static/resources/js/delta/domicilios/widget/UmfImssWidget.js",function(){
			$('#umfContenedor').html(data);
	
			// Se settea la función de callback para las UMF
			setFnCallback(fnSeleccionarUMF);
			
			// Se ejecuta la función de inicialización
			idUMFInicial = $('input#idUmfAsegurado').val();
			initComponenteUMFs();
		});
	}).error(function (data){
		$('#umfContenedor').html("<span>Existió un error al cargar las UMF's</span>");
	});
}

var fnSeleccionarUMF = function () {

	//idUMF|noEconomico|idDelegacion|cveDelegacion|idSubdelegacion|cveSubdelegacion|cveCiz;
	if(umfSeleccionada != null) {
		var umfArray = umfSeleccionada.split('|');
		
		$('#idUmfAsegurado').val(umfArray[0]);
		$('#noEconomicoUmfAsegurado').val(umfArray[1]);
		$('#idDelegacionAsegurado').val(umfArray[2]);
		$('#cveDelegacionAsegurado').val(umfArray[3]);
		$('#idSubdelegacionAsegurado').val(umfArray[4]);
		$('#cveSubdelegacionAsegurado').val(umfArray[5]);
		$('#cveCizAsegurado').val(umfArray[6]);
	}
	
};