/*

 * JS para el Control de las funciones de soporte de Grupos.
 *
 */




 var fraccionCtrl = {
 
 
 

 	
 
 	buscarFracciones : function(){
 	
 		if(hasCapturaDatosCatalogo()){
 			oTable.fnDraw();
 		}else{
 		
 				 dgFaltaDatos.dialog("open");
 		}
 		
 	}, 
 	
 	/**
 	 * Metodo para ejecutar la consulta para el catalogo anterior
 	 */
 	buscarFraccionesByCatalogoAnterior: function(){
 		if(hasFIltroCapturadoAnterior()){
 		dtResultadosAnt.fnDraw();
 		}else{
 		 dgFaltaDatos.dialog("open");
 		}
 		
 	},
 	/**
 	 * Metodo para ejecutar la consulta para el catalogo actual
 	 */
 	buscarFraccionesByCatalogoActual : function(){
 		
 		
 		if(hasFIltroCapturadoActual()){
 		
 		dtResultadosActual.fnDraw();
 		}else{
 		dgFaltaDatos.dialog("open");
 		
 		}
 		
 	},
 	
 	/**
 	 * Metodo para ejecutar la consulta en el catalogo anterior
 	 */
 	buscarFraccionesInCatalogoAnterior: function(){
 		if(hasFIltroCapturadoEnAnterior()){
 		dtResultadosEnAnt.fnDraw();
 		}else{
 		 dgFaltaDatos.dialog("open");
 		}
 		
 	},
 	/**
	*Funcion para obtener la fraccion
	*/
	 getFraccion : function(pFraccion){
		 
		 /**
		  * Se debe de obtener la clave de la division
		  */
	
		 
		 var sSource = "/clasificador/fraccion/buscarFraccionPorClave";
		 
		 
		 $.getJSON(sSource, { desFraccion: pFraccion }, function(data) {
				
			 	$("#dgCveFraccion").text(data.desFraccion);
			 	$("#dgActividad").text(data.nomActividad);
			 	$("#dgDescripcion").text(data.desActividad);
			 	$("#dgPrima").text(data.numPrimaMedia);
			 	$("#dgClase").text(data.cveClase);
			 	
			 	/*Asignamos la fraccion al objeto de fraccion seleccionada*/
			 	oFraccionSeleccionada = data;
			});
		 
		 
		 dgFraccion.dialog("open");
	}
	
}