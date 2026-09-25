/**
 * Mario Teran Blanco
 *
 */

$(document).ready(
	function() {
		datosPatron();
	}
);

function datosPatron() {
	var url = context_path + "/derechohabiente/detalle/datos/patron";
	$.postJSON(url, {} ,function(result) {
		$('#modalidadPatron').val(result.modelo.modalidad.descripcion);
		$('#patronnrp').val(result.mensaje);
	});
}