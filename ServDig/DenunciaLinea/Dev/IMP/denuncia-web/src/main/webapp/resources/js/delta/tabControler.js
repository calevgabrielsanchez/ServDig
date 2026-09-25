/**
 * indica el tab seleccionado
 */
var activeTab = '';

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
	
		$("ul.tabs li").removeClass("active");
		$(this).addClass("active");
		$(".tab_content").hide();
		
		activeTab = $(this).find("a").attr("href");
		FORMA_ACTUAL = activeTab.substring(1,activeTab.length)+"Form";
		setToFormSeccionActual();
		$(activeTab).fadeIn();
		alert(activeTab);
		return false;
	});
});
