/*
 * JS para el Control de las funciones de soporte de Divisiones.
 *
 */




 var divisionCtrl = {
	
	
	
	
	cargarComboDivisiones: function() {
		var url = context_path + "/division/cargarActivas.do";
		$.getJSON(url , function (objData){
			 var options = '<option value="0" >--Por favor seleccione--</option>';
			for (var i = 0; i < objData.length; i++) {
		        options += '<option value="' + objData[i].cveDivision + '">' + objData[i].nomDivision + '</option>';
		      }
			 $("select#divisionSelect").html(options);
		});
	}
	
}