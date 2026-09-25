//Variable con los asentamientos
var asentamientosUbicados;


/*
 * Seccion de codigo a ejecutar en cuanto el DOM envie la señar de que esta
 * listo para procesar de modificaciones al DOM
 */
$(document).ready(
		function() {

			
			
				//Configuramos la invocacion a la peticion de asentamientos
				// por codigo postal al perder el foco.
				$("form#formCodigoPostal input#codigoPostal\\.codigoPostal").blur( function(){
					getAsentamientoPorCodigo();
				});

			
				
				//Configuramos la invocacion al cambio del valor del municipio
				$("form#formMunicipio select#localidad\\.municipio\\.clave").click(function(event){
					
					getAsentamientoPorMunicipio();
					
				});
				
				
				$("form#formCodigoPostal select#asentamiento\\.clave").change(function(event){
					setDatosLocalidad($('option', this).index($('option:selected', this)), 'form#formCodigoPostal' )
				});
			
				
				
				$("form#formMunicipio select#clave").change(function(event){
					setDatosLocalidad($('option', this).index($('option:selected', this)), 'form#formMunicipio' )
				});
				
				
				
				
				
				
				
			
		});





/**
 * 
 * @param indexOption
 * @param form
 */
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

function getAsentamientoPorMunicipio(){
	
	var cveEnt = $("form#formMunicipio select#localidad\\.municipio\\.entidadFederativa\\.clave").val();
	var cveMun = $("form#formMunicipio select#localidad\\.municipio\\.clave").val();
	
	
	
	if(  !$.isEmptyObject(cveEnt)  && !$.isEmptyObject( cveMun)){
		var url = context_path +"/domicilio/nacional/ubicar/asentamiento/get/municipio"
		
		$.getJSON( url , {'cveEnt' : cveEnt, 'cveMun':cveMun}, function(data){
			setAsentamientos(data.asentamientos, "form#formMunicipio select#clave");
		}).error(function(data){
			
		});
		
	}
	
	
}


/**
 * Obtiene los asentamientos por codigo postal
 */
function getAsentamientoPorCodigo(){
	
	fnHideErrores("form#formCodigoPostal");
	
	var codigo = $("form#formCodigoPostal input#codigoPostal\\.codigoPostal").val();
	var url = context_path +"/domicilio/nacional/ubicar/asentamiento/get/codigoPostal"
	
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

