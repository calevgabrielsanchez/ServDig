/**
 * indica el tab seleccionado
 */
var activeTab = '';

/**
 * Permite el acceso a todos los tabs
 * por defecto
 */
var ACCESS_TO_ALL ="ALL";

/**
 * Niega el acceso a todos los tabs
 * por defecto
 */
var ACCESS_TO_NONE ="NONE";
/**
 * Indica la forma seleccionada
 */
var FORMA_ACTUAL ='';

/**
 * Inicializa los tabs y funciones de cambio
 * de la pantalla de seguimiento.
 * 
 * Inicializa las variables:
 *    - activeTab : TAB (pestaña) en la que se encuentra posicionado el usuario.
 *    - FORMA_ACTUAL: forma que debe de ejecutar la acción al momento de procesar
 *                    una petición.
 * Todos los formularios deben de contar con un hidden 'seccionPeticion'
 * y este se llenará automáticamente indicando a JAVA que sección se 
 * está procesando.
 * 
 */
$(document).ready(function() {
	
	
	$(".tab_content").hide();
	$("ul.tabs li:first").addClass("active").show();
	$(".tab_content:first").show();
	$("ul.tabs li").click(function()
       {

		activeTab = $(this).find("a").attr("href");
		
		
		if(tabNavSecurity(activeTab)){
			
		
			$("ul.tabs li").removeClass("active");
			$(this).addClass("active");
			$(".tab_content").hide();
		
			
			var activeTabParts = activeTab.split("_");
			
			FORMA_ACTUAL = activeTabParts[0].substring(1,activeTabParts[0].length)+"Form";
			
			try{			
				setToFormSeccionActual();			
			}catch(error){}
			
			
			$(activeTab).fadeIn();
	
			return true;
		}
		
		return false;
		
	});
	
	
	initTabs();
	
});

/**
 * Permite agregar un valor de seguridad a los tabs
 * se debe de utilizar un hidden llamado accessTabs
 * en donde se deberá poner todos los tabs a los
 * cuales el usuario tiene acceso.
 * 
 * En caso de no utilizar esta propiedad no declarar
 * el hidden accessTabb
 * @param activeTab
 * @returns true si acceso, false cualquier otro caso
 */
function tabNavSecurity(activeTab){
	
	
	var currentTab = activeTab.substring(1,activeTab.length);
	var accessTabs;
	
	
	try{
		accessTabs = $("#accessTabs").val();
		
		
		
		if(accessTabs==undefined) accessTabs = ACCESS_TO_ALL
		else if(accessTabs=="") accessTabs = ACCESS_TO_ALL
		
	}catch(error){
		
		alert("ERROR accessTabs="+error);
	}
	
	
	
	if(accessTabs==ACCESS_TO_NONE) return false;
	else if(accessTabs==ACCESS_TO_ALL || accessTabs.search(currentTab)>=0) return true
	
	return false;
	
}

function initTabs(){
	var forbidenTabs;
	var tabArray;
	
	try{
		forbidenTabs = $("#forbidenTabs").val();
		
		if(forbidenTabs==undefined) forbidenTabs = ACCESS_TO_ALL
		else if(forbidenTabs=="") forbidenTabs = ACCESS_TO_ALL
		else{
			tabArray = forbidenTabs.split("-");
		}
		
	}catch(error){
		
		alert("ERROR forbidenTabs="+error);
	}
	
	try{
		
		if(forbidenTabs!=ACCESS_TO_ALL){
			for(i=0;i<tabArray.length;i++){
				
				$("#"+tabArray[i]+"LI").addClass("active2");
				$("#"+tabArray[i]+"Link").attr('disabled', true);
			}
		}
		
		
	}catch(error){
		
		alert("CATCH:"+error);
	}
	
}

function setTabHabilitado(tab){
	
	try{
		var accessTabs = $("#accessTabs").val();
		var forbidenTabs = $("#forbidenTabs").val();
		
		
		var arrayTab = forbidenTabs.split("-");
		
		forbidenTabs ="";
		
		for(var i = 0; i< arrayTab.length;i++){
		
			if(tab!=arrayTab[i]){
			
				forbidenTabs = forbidenTabs + arrayTab[i]+"-"; 
						
			}else{
			
				accessTabs = accessTabs + "-" + arrayTab[i];

				$("#"+arrayTab[i]+"LI").removeClass("active2");
				$("#"+arrayTab[i]+"LI").addClass("active");
				$("#"+arrayTab[i]+"Link").attr('disabled', false);
				
				$("#"+arrayTab[i]+"LI").removeClass("active");
				$(activeTab).addClass("active");
				
			}
		
		}
		
			
		$("#accessTabs").val(accessTabs);
		$("#forbidenTabs").val(forbidenTabs);
	}catch(error){
		alert("Uso de deshabilitación sin declarar hiddens accesTab/forbidenTabs");
	}
	
}


function setTabDesHabilitado(tab){
	
	try{
		var accessTabs = $("#accessTabs").val();
		
		var forbidenTabs = $("#forbidenTabs").val();
		
		var arrayTab = accessTabs.split("-");
		
		accessTabs ="";
		
		for(var i = 0; i< arrayTab.length;i++){
		
			if(tab!=arrayTab[i]){
			
				accessTabs = accessTabs + arrayTab[i]+"-"; 
						
			}else{
			
				forbidenTabs = forbidenTabs + "-" + arrayTab[i];
				$("#"+arrayTab[i]+"LI").removeClass("active");
				$("#"+arrayTab[i]+"LI").addClass("active2");
				$("#"+arrayTab[i]+"Link").attr('disabled', true);
						
			}
		}
		
		
		$("#accessTabs").val(accessTabs);
		$("#forbidenTabs").val(forbidenTabs);
	}catch(error){
		alert("Uso de deshabilitación sin declarar hiddens accesTab/forbidenTabs");
	}
	
}

function changeTab(tab){
	var tab = "#"+tab;
	
	if(tabNavSecurity(tab)){
		
						
		$("ul.tabs li").removeClass("active");
		$(tab).removeClass("active");
		
		$(tab+"LI").addClass("active");
		$(tab).addClass("active");
		$(".tab_content").hide();
		
		var activeTabParts = tab.split("_");
		
		FORMA_ACTUAL = activeTabParts[0].substring(1,activeTabParts[0].length)+"Form";
		
		try{			
			setToFormSeccionActual();			
		}catch(error){}
		
		
		$(tab).fadeIn();
		
		activeTab = tab;
		
		return true;
	}
	
	
	return false;
}