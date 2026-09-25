
/**
 * 
 */


var forma;
var map;
var geocoder;
var markersArray = [];

$(document).ready(function(){
	
	forma = $("form#formDomicilio");
	
	//Configuramos la invocacion al cambio del valor del municipio
	$("select#asentamiento\\.localidad\\.municipio\\.clave" , forma).change(function(event){
		getAsentamientoPorMunicipio();
	});
	
	/*Invocamos la inicializacion de los mapas.*/
	initGoogleMaps();
	
	alert("funciona ya no guardo en cache los JS jojo jo jo");
})




/**
 * Funcion para inicializar el mapa de google maps.
 * 
 */
function initGoogleMaps(){
	
//	var _urlGoogleMaps = 'http://maps.google.com/maps/api/js?sensor=false&region=MX';
//	
//	$.getScript(_urlGoogleMaps)
//	.done(
//			function(script, textStatus) {
//				
//				
//			}).fail(
//			function(jqxhr, settings, exception) {
//				
//			});
	
	/* Inicializacion del Google Maps */
	try{
		geocoder = new google.maps.Geocoder();
		var myOptions = {
			zoom : 10,
			mapTypeId : google.maps.MapTypeId.ROADMAP,
			overviewMapControl : false
		};
		map = new google.maps.Map(document.getElementById("map_canvas"),
				myOptions);
	}catch(error){
		
	}
	
	
	
}


/**
 * Obtiene los asentamientos por la clave de municipio y entidad federativa
 */
function getAsentamientoPorMunicipio(){
	
	var cveEnt = $("select#asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave" , forma).val();
	var cveMun = $("select#asentamiento\\.localidad\\.municipio\\.clave", forma).val();
	
	
	
	if(  !$.isEmptyObject(cveEnt)  && !$.isEmptyObject( cveMun)){
		var url = context_path +"/domicilio/nacional/ubicar/asentamiento/get/municipio"
		
		$.getJSON( url , {'cveEnt' : cveEnt, 'cveMun':cveMun}, function(data){
			setAsentamientos(data.asentamientos, "select#asentamiento\\.clave");
		}).error(function(data){
			
		});
		
	}
}



/**
 * 
 * @param asentamientos Lista de los asentamientos
 * @param select Id del select a actualizar
 */
function setAsentamientos(asentamientos, select){
	
	var options = "<option value=''> -- Por favor seleccione -- </option>";
	for(var i = 0 ; i < asentamientos.length ; i++){
		options += "<option value='" + asentamientos[i].clave + "'>" + asentamientos[i].nombre + "</option>";
	}
	$(""+select , forma).html(options);
}