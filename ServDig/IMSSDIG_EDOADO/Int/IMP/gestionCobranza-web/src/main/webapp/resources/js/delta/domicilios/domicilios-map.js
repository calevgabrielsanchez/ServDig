var map;
var geocoder;
var markersArray = [];
var infowindow;


var domicilio;
/*
 * Seccion de codigo a ejecutar en cuanto el DOM envie la señar de que esta
 * listo para procesar de modificaciones al DOM
 */
$(document).ready(
		function() {

			
			
			
			
			$('form#form').submit(function(){
				clearOverlays();
				buscarDireccion();
				return false;
			});
			
			
			
			$("form#formComplemento input#vialidadPrimaria\\.nombre").blur(function(){
				
				var address = $("form#formComplemento input#vialidadPrimaria\\.nombre").val() + getDireccionAsentamiento();
				//getDireccionMaps(address);
				
			});
			
			//Dispara la consulta de los asentamientos por codigo postal
			$("form#form input#codigoPostal").blur( function(){
				getAsentamientosXCodigoPostal();
			});
			
			//Dispara la localizacion del mapa de google del lugar capturado.
			$("form#form input#calle").blur( function(){
//				clearOverlays();
//				buscarDireccion();
			});
			
			
			
			$('form#resultado').submit(function(){
				var oForm = $this.serializeObject(true);
				window.returnValue = oForm;
				window.close();
				return false;
			});
			
			
//
//			$('form#formComplemento').submit(function(event){
//				return false;
//			});
			
			
			/* Inicializacion del Google Maps */
			
			geocoder = new google.maps.Geocoder();
			var myOptions = {
				zoom : 10,
				mapTypeId : google.maps.MapTypeId.ROADMAP
			};
			map = new google.maps.Map(document.getElementById("map_canvas"),
					myOptions);
			/*Ubicamos el mapa en la zona del cliente*/
			//localizacionZonaCliente();
			
			/*Configuramos el eventoclick*/
			google.maps.event.addListener(map, 'click', function(event) {
				localizaPunto(event.latLng);
			  });
			
			localizacionZonaCliente();
			
			getDireccionAsentamiento();
		});


function getDireccionAsentamiento(){
	
	var oForm = $('form#formAsentamiento').toObject();
	var domicilioQuery =  oForm.localidad.municipio.nombre+ ", " + oForm.localidad.municipio.entidadFederativa.nombre
	getDireccionMaps(domicilioQuery);
	return domicilioQuery;
}

function localizacionZonaCliente (){
	  // Try W3C Geolocation (Preferred)
	  if(navigator.geolocation) {
	    browserSupportFlag = true;
	    navigator.geolocation.getCurrentPosition(function(position) {
	      initialLocation = new google.maps.LatLng(position.coords.latitude,position.coords.longitude);
	      map.setCenter(initialLocation);
	    }, function() {
	      handleNoGeolocation(browserSupportFlag);
	    });
	    
	  // Try Google Gears Geolocation
	  } else if (google.gears) {
	    browserSupportFlag = true;
	    var geo = google.gears.factory.create('beta.geolocation');
	    geo.getCurrentPosition(function(position) {
	      initialLocation = new google.maps.LatLng(position.latitude,position.longitude);
	      map.setCenter(initialLocation);
	    }, function() {
	      handleNoGeoLocation(browserSupportFlag);
	    });
	    
	  // Browser doesn't support Geolocation
	  } else {
	    browserSupportFlag = false;
	    handleNoGeolocation(browserSupportFlag);
	  }
	  
	  function handleNoGeolocation(errorFlag) {
	    if (errorFlag == true) {
	      alert("Geolocation service failed.");
	      initialLocation = newyork;
	    } else {
	      alert("Your browser doesn't support geolocation. We've placed you in Siberia.");
	      initialLocation = siberia;
	    }
	    map.setCenter(initialLocation);
	  }
	  
}

function setLocacionResultante( results){
	var gm_domicilio = results[0];
	
	var routeGmap = getValoresGeocoderResponse(route , gm_domicilio.address_components);
	if(routeGmap != null){
		$("form#form input#calle").val( routeGmap );
	}
	
	//$("form#form input#calle").val( getValoresGeocoderResponse(route , gm_domicilio.address_components));
	
	$("form#form input#numero").val( getValoresGeocoderResponse(street_number , gm_domicilio.address_components));
	$("form#form input#nombreAsentamiento").val(  getValoresGeocoderResponse(neighborhood , gm_domicilio.address_components));
	$("form#form input#nombreLocalidad").val( getValoresGeocoderResponse(sublocality , gm_domicilio.address_components));
	$("form#form input#nombreMunicipio").val( getValoresGeocoderResponse(locality , gm_domicilio.address_components));
	$("form#form input#nombreEntidadFederativa").val( getValoresGeocoderResponse(administrative_area_level_1 , gm_domicilio.address_components));
	$("form#form input#nombreLocalidad").val( domicilio.nombreLocalidad);
}


var street_number ="street_number";
var route = "route";
var neighborhood = "neighborhood";
var sublocality = "sublocality";
var locality ="locality";
var administrative_area_level_1 ="administrative_area_level_1";
var country ="country";


var street_address = "street_address";
var route = "route";



function getValoresGeocoderResponse(idElement, address_components){
	var value;
	for(var i = 0 ; i < address_components.length ; i++){
		var address = address_components[i];
		if(address.types[0] == idElement){
			value = address.long_name;
		}
		
	}
	return value;
	
}


function localizaPunto(location) {
	  
	  geocoder.geocode( { 'latLng': location}, function(results, status) {
	      if (status == google.maps.GeocoderStatus.OK) {
	    	  setMarcador(results);
	      } else {
	        alert("Geocode was not successful for the following reason: " + status);
	      }
	    });
	}


var domicilios;

function getAsentamientosXCodigoPostal() {
	
	
	
	
//	var geocoderDomicilio = $("form#formfiltros").serializeObject(true);
	
	var codigoPostal = $("form#form input#codigoPostal").val();
	
	
	var url = context_path +"/domicilio/registro/codigopostal/buscar"
	$.getJSON(url , {"codigoPostal":codigoPostal}, function(data){
			domicilios = data.domicilios;
			var options = "<option value=''> -- Por favor seleccione -- </option>";
			for(var i = 0 ; i < domicilios.length ; i++){
				options += "<option value='" + domicilios[i].claveAsentamiento + "'>" + domicilios[i].nombreAsentamiento + "</option>";
			}
		$("form#form select#nombreAsentamiento").html(options);
	});
	
	
	
	
	
  }


function buscarDireccion(){
	
	var idAsentamiento = $("form#form select#nombreAsentamiento").val();
	
	for(var i = 0 ; i < domicilios.length ; i++){
		if(domicilios[i].claveAsentamiento == idAsentamiento){
			domicilio = domicilios[i];
			break;
		}
	}
	var calle = $("#calle").val();
	
	var address = "" + calle +", " +domicilio.domicilioNormalizado;
	getDireccionMaps(address);
	
	
}

function getDireccionMaps(address){

    //Buscamos por la direccion completa.
    geocoder.geocode( { 'address': address}, function(results, status) {
      
    	if (status == google.maps.GeocoderStatus.OK) {
        //Si la respuesta es positiva
    		//Marcamos el punto
        setMarcador(results);
        
      } else {
        alert("Geocode was not successful for the following reason: " + status);
      }
    });
	
	
}

function setMarcador(results){
	clearOverlays();
	
	//setLocacionResultante(results);
	 var location = results[0].geometry.location;
	 
	map.setCenter(location);
	  map.setZoom(18);
	  
	  var marker = new google.maps.Marker({
      map: map, 
      position: location,
      title: results[0].formatted_address,
      animation: google.maps.Animation.DROP
	  });
	  markersArray.push(marker);
	
	  
	
}

//Removes the overlays from the map, but keeps them in the array
function clearOverlays() {
  if (markersArray) {
    for (i in markersArray) {
      markersArray[i].setMap(null);
    }
  }
}

