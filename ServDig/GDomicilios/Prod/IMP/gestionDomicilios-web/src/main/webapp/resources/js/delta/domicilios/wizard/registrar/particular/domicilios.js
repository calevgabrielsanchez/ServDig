//Variable con los asentamientos
var asentamientosUbicados;

var oFormQueryDomicilio;
/*
 * Seccion de codigo a ejecutar en cuanto el DOM envie la se�ar de que esta
 * listo para procesar de modificaciones al DOM
 */
$(document).ready(
		function() {

			//Configuramos la invocacion a la peticion de asentamientos
			// por codigo postal al perder el foco.
			$("form#formCodigoPostal input#codigoPostal\\.codigoPostal").live(
					'blur', function() {
						getAsentamientoPorCodigo();
					});

			//Configuramos la invocacion al cambio del valor del municipio
			$("form#formMunicipio select#localidad\\.municipio\\.clave")
					.change(function(event) {

						getAsentamientoPorMunicipio();

					});

			$("form#formCodigoPostal select#asentamiento\\.clave").change(
					function(event) {
						setDatosLocalidad($('option', this).index(
								$('option:selected', this)),
								'form#formCodigoPostal');
					});

			$("form#formMunicipio select#clave").change(
					function(event) {
						setDatosLocalidad($('option', this).index(
								$('option:selected', this)),
								'form#formMunicipio');
					});

			// Se vuelve disabled los datos que tengan la clase disabled
			$(".disabled").each(function(i) {
				$(this).attr("disabled", "true");
			});

			$("form").submit(function() {
				$(".disabled").each(function(i) {
					$(this).removeAttr("disabled");
				});
			});

			//Manejo del maxlength del textarea
			$('textarea[maxlength]').live('keyup blur', function() {
				// Store the maxlength and value of the field.
				var maxlength = $(this).attr('maxlength');
				var val = $(this).val();

				// Trim the field if it has content over the maxlength.
				if (val.length > maxlength) {
					$(this).val(val.slice(0, maxlength));
				}
			});

			/*
			 * 
			 */
			$("input#regresar").click(function(event) {

				$("form#formRegreso").submit();

			});

		});

/**
 * 
 * @param indexOption
 * @param form
 */
function setDatosLocalidad(indexOption, form) {

	//Obtenemos el asentamiento del indice del asentamiento
	// Se resta un elemento ya que el arreglo de asentamientos no tiene la opcion 0 del select
	var asentamientoLocalizado = asentamientosUbicados[indexOption - 1];

	var cveLoc = asentamientoLocalizado.localidad.clave;
	var cveMun = asentamientoLocalizado.localidad.municipio.clave;
	var cveEnt = asentamientoLocalizado.localidad.municipio.entidadFederativa.clave;

	var oForm = $(form);

	$('input:hidden#asentamiento\\.localidad\\.clave', oForm).val(cveLoc);
	$('input:hidden#asentamiento\\.localidad\\.municipio\\.clave', oForm).val(cveMun);
	$('input:hidden#asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave',oForm).val(cveEnt);
	$('input:hidden#localidad\\.clave', oForm).val(cveLoc);

}

function getAsentamientoPorMunicipio() {

	var cveEnt = $("form#formMunicipio select#localidad\\.municipio\\.entidadFederativa\\.clave").val();
	var cveMun = $("form#formMunicipio select#localidad\\.municipio\\.clave").val();

	if (cveMun != -1) {
		var url = context_path + "/domicilio/nacional/ubicar/asentamiento/get/municipio";

		$.getJSON( url,{'cveEnt' : cveEnt,'cveMun' : cveMun},function(data) {
			setAsentamientos(data.asentamientos,"form#formMunicipio select#clave");
		}).error(function(data) {});
	}
}

/**
 * Obtiene los asentamientos por codigo postal
 */
function getAsentamientoPorCodigo() {

	fnHideErrores("form#formCodigoPostal");

	var codigo = $("form#formCodigoPostal input#codigoPostal\\.codigoPostal").val();
	var url = context_path+ "/domicilio/nacional/ubicar/asentamiento/get/codigoPostal";

	$.getJSON(url, {'codigo' : codigo}, function(data) {
		setAsentamientos(data.asentamientos, "select#asentamiento\\.clave");
	}).error(function(data) {
		fnProcesarErrores(data, 'form#formCodigoPostal');
		limpiarAsentamientos();
		
	});

}

function limpiarAsentamientos(){
	$('select#asentamiento\\.clave').html("");
	var optionSeleccione="<option value=''>-- Seleccione --</option>";
	$("select#asentamiento\\.clave").append(optionSeleccione);
}


/**
 * 
 * @param asentamientos Lista de los asentamientos
 * @param select Id del select a actualizar
 */
function setAsentamientos(asentamientos, select) {
	asentamientosUbicados = asentamientos;
	var options = "<option value='-1'> -- Por favor seleccione -- </option>";
	for ( var i = 0; i < asentamientos.length; i++) {
		options += "<option value='" + asentamientos[i].clave + "'>"
				+ asentamientos[i].nombre + "</option>";
	}
	$("" + select).html(options);
}
