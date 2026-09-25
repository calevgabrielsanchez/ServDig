$(document).ready(function(){ 
		
	$.datepicker.setDefaults({
		onClose: function(){
			$(this).valid();
		}
	});
	
	
//	$("#fechaRegistroAlta").mask("99/99/9999");
//	$( "#fechaRegistroAlta" ).datepicker(
//	{
//		dateFormat : 'dd/mm/yy',
//		changeMonth : true,
//		changeYear : true,
//		maxDate: new Date(), 
//		yearRange : '-112:+0',
//	}		
//	);
	
	
    $("#aceptarVal").on("click", function () {
        if (validarCampos()) {
//        	$("#filtrosBusquedaForm").submit();
//        	creaDataTableActualizaCorreo()
        	creaDataTableActualizaCorreoFiltros()
            limpiarCampos();
        } else {
            alert("Al menos un campo debe tener contenido.");
        }
    });
    
    $("#limpiar").on("click", function () {
        	creaDataTableActualizaCorreo()
            limpiarCampos();
    })
    

			
});

function validarCampos() {
    var nss = $("#nss").val();
    var fecha = $("#fecha").val();
    var estado = $("#estado").val();
    var folio = $("#folio").val();

    return nss !== "" || fecha !== "" || estado !== "" || folio !== "";
}

function limpiarCampos() {
    $("#nss").val("");
    $("#fechaRegistroAlta").val("");
    $("#idEstado").val(""); 
    $("#folio").val("");
}


