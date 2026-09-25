function obtenerMunicipiosImss() {
	var paramCentroTrabajo = $('#asentamientoForMunicipioForm').serialize();
	
	var url = '/${mvn.web.app.rootDomicilios}/widget/domicilio/utility/consulta/municipioImss/centroTrabajo';
	
	$.post(url, paramCentroTrabajo, function(data){
		$('#municipioImssContenedor').html(data);

		// Se settea la función de callback para las UMF
		setFnCallback(fnSeleccionarMunicipioImss);
		
		// Se ejecuta la función de inicialización
		idMunicipioImssInicial = $('#municipioIMSS\\.idMunicipio').val();
		initComponenteMunicipiosImss();
	}).error(function (data){
		$('#municipioImssContenedor').html("<span>Existi&oacute; un error al cargar las Subdelegaciones</span>");
	});
}

var fnSeleccionarMunicipioImss = function () {
	var municipioImssArray = municipioSeleccionado.split('|');

	$('#municipioIMSS\\.idMunicipio').val(municipioImssArray[0]);
	$('#municipioIMSS\\.cvecMunicipioSINDO').val(municipioImssArray[1]);
	$('#municipioIMSS\\.descMunicipio').val(municipioImssArray[2]);
	
	$('#municipioIMSS\\.subdelegacion\\.delegacion\\.id').val(municipioImssArray[3]);
	$('#municipioIMSS\\.subdelegacion\\.delegacion\\.clave').val(municipioImssArray[4]);
	$('#municipioIMSS\\.subdelegacion\\.delegacion\\.descripcion').val(municipioImssArray[5]);
	$('#municipioIMSS\\.subdelegacion\\.delegacion\\.ciz').val(municipioImssArray[6]);

	$('#municipioIMSS\\.subdelegacion\\.id').val(municipioImssArray[7]);
	$('#municipioIMSS\\.subdelegacion\\.clave').val(municipioImssArray[8]);
	$('#municipioIMSS\\.subdelegacion\\.descripcion').val(municipioImssArray[9]);

};