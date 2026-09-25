/**
 * @author mario.teran
 */
//Variable con los asentamientos
var asentamientosUbicados;

$(document).ready(
		function() {
			
			//Configuramos la invocacion a la peticion de asentamientos
			// por codigo postal al perder el foco.
			$("form#formCodigoPostal input#codigoPostal\\.codigoPostal").live('blur', function(){
				getAsentamientoPorCodigo();
			});
			
			$("form#formCodigoPostal select#asentamiento\\.clave").change(function(event){
				setDatosLocalidad($('option', this).index($('option:selected', this)), 'form#formCodigoPostal' )
			});
		}
);

function setDatosLocalidad(indexOption , form){
	
	//Obtenemos el asentamiento del indice del asentamiento
	// Se resta un elemento ya que el arreglo de asentamientos no tiene la opcion 0 del select
	var asentamientoLocalizado = asentamientosUbicados[indexOption -1];
	
	var cveLoc = asentamientoLocalizado.localidad.clave;
	var cveMun = asentamientoLocalizado.localidad.municipio.clave;
	var cveEnt = asentamientoLocalizado.localidad.municipio.entidadFederativa.clave;
	
	var oForm = $(form);
	
	$('input:hidden#asentamiento\\.localidad\\.clave', oForm).val(cveLoc);
	$('input:hidden#asentamiento\\.localidad\\.municipio\\.clave', oForm).val(cveMun);
	$('input:hidden#asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave', oForm).val(cveEnt);
	$('input:hidden#localidad\\.clave', oForm).val(cveLoc);
	
	
}
/**
 * Obtiene los asentamientos por codigo postal
 */
function getAsentamientoPorCodigo(){
	
	fnHideErrores("form#formCodigoPostal");
	
	var codigo = $("form#formCodigoPostal input#codigoPostal\\.codigoPostal").val();
	var idDelegacion = $("#idDelegacion").val();
	var url = context_path +"/domicilio/nacional/ubicar/delegacion/asentamiento/get/codigoPostal"
	
	$.getJSON(url ,{'codigo': codigo}, function(data){
		setAsentamientos(data.asentamientos, "select#asentamiento\\.clave");
	}).error(function(data){
		fnProcesarErrores(data, 'form#formCodigoPostal')
	});
	
}

/**
 * 
 * @param asentamientos Lista de los asentamientos
 * @param select Id del select a actualizar
 */
function setAsentamientos(asentamientos, select){
	asentamientosUbicados = asentamientos;
	var options = "<option value=''> -- Por favor seleccione -- </option>";
	for(var i = 0 ; i < asentamientos.length ; i++){
		options += "<option value='" + asentamientos[i].clave + "'>" + asentamientos[i].nombre + "</option>";
	}
	$(""+select).html(options);
}