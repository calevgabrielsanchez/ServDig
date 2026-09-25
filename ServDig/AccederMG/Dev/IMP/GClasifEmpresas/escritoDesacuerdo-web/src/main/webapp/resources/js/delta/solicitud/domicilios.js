
$(document).ready(
		function() {
			$.blockUI();

			/*
			 * Configuramos la invocacion a la peticion de asentamientos por
			 * codigo postal al perder el foco.
			 */
			$("form#formCodigoPostal input#codigoPostal\\.codigoPostal").live('blur', function() {
						getAsentamientoPorCodigo(true);
					});

			$("select#asentamiento\\.clave").change(function() {
				var claveCombo = $("select#" + "asentamiento\\.clave").val();

			});

			$.unblockUI();
		});

/**
 * Obtiene los asentamientos por codigo postal
 * 
 */
function getAsentamientoPorCodigo(hideErrors) {

	var codigo = $("form#formCodigoPostal input#codigoPostal\\.codigoPostal")
			.val();
	var url;
	var otros_parametros;

	url = context_path + "/wizard/correccionDatosAsegurado/asentamiento/get/codigoPostal";
	otros_parametros = {
		'codigo' : codigo
	};

	$
			.ajax({
				type : "POST",
				url : url,
				dataType : 'json',
				data : otros_parametros,
				beforeSend : function() {
					$('form#formCodigoPostal img#cveAsentamientoImgCargando')
							.show();
				},
				success : function(data) {
					if (data.asentamientos.length > 0) {
						var claveEstado = data.asentamientos[0].localidad.municipio.entidadFederativa.nombre;
						var claveMunicipio = data.asentamientos[0].localidad.municipio.nombre;
						$('form#formCodigoPostal input#entidadFederativa\\.nombre').val(claveEstado);
						$('form#formCodigoPostal input#municipio\\.nombre').val(claveMunicipio);
					}
					setAsentamientos(data.asentamientos, "select#asentamiento\\.clave");
				},
				complete : function() {
					$('form#formCodigoPostal img#cveAsentamientoImgCargando').hide();
				},
				error : function(data) {
					fnProcesarErrores(data, 'form#formCodigoPostal');
					limpiarAsentamientos();
					var options = "<option value='-1'>--Por favor seleccione--</option>";
					$("form#formCodigoPostal select#localidad\\.clave").html(options);
				}
			});
}

function limpiarAsentamientos() {
	$('select#asentamiento\\.clave').html("");
	var optionSeleccione = "<option value='-1'>--Por favor seleccione--</option>";
	$("select#asentamiento\\.clave").append(optionSeleccione);
}

/**
 * 
 * @param asentamientos
 *            Lista de los asentamientos
 * @param select
 *            Id del select a actualizar
 */
function setAsentamientos(asentamientos, select) {

	var cveAsentAux = $('input#cveAsentamientoAux').val();

	// Se checa si el otro hidden tiene valor
	if (cveAsentAux == null || cveAsentAux == '' || cveAsentAux == '-1') {
		cveAsentAux = $('input#cveAsentamientoCPAux').val();
	}

	asentamientosUbicados = asentamientos;
	var options = "<option value='-1'>--Por favor seleccione--</option>";
	for (var i = 0; i < asentamientos.length; i++) {
		options += "<option value='" + asentamientos[i].clave + "'>"
				+ asentamientos[i].nombre + "</option>";
	}
	$("" + select).html(options);
}

$('#continuarCarpturarHistoriaLaboral').click(function() {
	$('#formCodigoPostal').submit();
});


