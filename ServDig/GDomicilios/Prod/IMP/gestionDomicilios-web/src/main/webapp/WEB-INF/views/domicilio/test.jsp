<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>


<script type="text/javascript"
	src="http://maps.googleapis.com/maps/api/js?key=AIzaSyD2rWkTQuL0tv6j3oQp61fjf49vbEEIQ10&sensor=true"></script>
	

	
	
	
<script>

/* Inicializacion del Google Maps */
var map;
var geocoder;
var markersArray = [];
var infowindow;

try{
	geocoder = new google.maps.Geocoder();
	var myOptions = {
		zoom : 10,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};
	map = new google.maps.Map(document.getElementById("map_canvas"),
			myOptions);
	
	
	
	
	localizacionZonaCliente();
	
	
}catch(error){
	
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



</script>
	
<div id="map_canvas" style="width: 100%; height: 100%">
							</div>