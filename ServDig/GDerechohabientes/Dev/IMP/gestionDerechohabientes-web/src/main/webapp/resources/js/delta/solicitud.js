var sIdSolicitud =    1;
var sIdAccordion = "#solicitud"

	
	
var sIdProductos = "#productos";
var sIdMaterias = "#materias";
var sIdMaquinarias = "#maquinarias";
var sIdProcesos = "#procesos";
var sIdPersonal = "#personal";
var sIdTransporte ="#transportes";
var sIdActividades = "#actividades";


	/** Seccion de codigo a ejectuar cuando el DOM este listo **/
	$(function() {
		
		
		/*COnfiguracion de los botones de abrir y cerrar*/
		$('.ui-icon-plusthick').click( function (event) {
			var divSeccion = event.target.parentNode.parentNode.nextElementSibling;
			$(divSeccion).show("fast");
		});
		$('.ui-icon-closethick').click( function (event) {
			var divSeccion = event.target.parentNode.parentNode.nextElementSibling;
			$(divSeccion).hide("slow");
		});
		
		
		
		fnLoadProductos();
		
		fnLoadMaterias();
		
		fnLoadMaquinaria();
		
		fnLoadTransporte();
		
		fnLoadProcesos();
		
		fnLoadPersonal();
		
		fnLoadActividades();
		
	});



/**
 * Carga la pagina de productos
 * @returns
 */
var fnLoadProductos = function(){
	var urlProductos =  "productos"
	$.get(urlProductos , function(data){
		$(sIdProductos).html(data);
	})
}




var fnLoadMaterias = function(){
	
	var urlMaterias  =  "materiasprimas"
		$.get(urlMaterias , function(data){
			$(sIdMaterias).html(data);
		})
	
}

/**
 * 
 * @returns
 */
var fnLoadMaquinaria = function(){
	
	var urlMaquinarias =  "maquinariaEquipos"
		$.get(urlMaquinarias , function(data){
			$(sIdMaquinarias).html(data);
		})
	
}



/**
 * 
 * @returns
 */
var fnLoadTransporte = function(){
	
	var urlTransportes =  "transportes"
		$.get(urlTransportes , function(data){
			$(sIdTransporte).html(data);
		})
	
}

/**
 * 
 * @returns
 */
var fnLoadProcesos = function(){
	
	var urlProcesos =  "proceso"
		$.get(urlProcesos , function(data){
			$(sIdProcesos).html(data);
			
			fnLoadInitProceso();
			
		})
	
}

/**
 * 
 * @returns
 */
var fnLoadPersonal= function(){
	
	var urlPersonal =  "personal"
		$.get(urlPersonal , function(data){
			$(sIdPersonal).html(data);
		})
	
}


/**
 * 
 * @returns
 */
var fnLoadActividades= function(){
	
	var urlActividades =  "actividades"
		$.get(urlActividades , function(data){
			$(sIdActividades).html(data);
		})
	
}



/*
 * funciones para navegacion
 */
	function fnSolicitudGoBack(){
		$("#form-solicitud-back").submit();
	}
	
	function fnSolicitudSave(){
		$("#form-solicitud-guardar").submit();
	}

	function fnSolicitudGoAhead(){
		$("#form-solicitud-forward").submit();
	}

