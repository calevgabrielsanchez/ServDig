/*
 * JS para el Control de las funciones de soporte de Divisiones.
 *
 */




 var grupoCtrl = {
	
	
	
	
	cargarComboXDvision: function() {
		
		
		
		var selectDivision =$("select#divisionSelect").val(); 
		
		var url = "/clasificador/grupo/cargarGrupos";
		$.getJSON(url ,  { cveDivision: selectDivision } , function (objData){
			 var options = '<option value="0" >--Por favor seleccione--</option> ';
			for (var i = 0; i < objData.length; i++) {
		        options += '<option value="' + objData[i].id.cveGrupo + '">' + objData[i].nomGrupo + '</option>';
		      }
			 $("select#grupoSelect").html(options);
		});
	} , 
	
	resetCombo : function (){
		 var options = '<option value="0" >--Por favor seleccione--</option> ';
		$("select#grupoSelect").html(options);
			
		
	}
	
}