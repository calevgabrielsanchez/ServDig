$(document).ready(function() { 

	var $dialogCapturaClem = $('<div></div>')
	.html('¿Est&aacute; seguro de generar la Clem?')
	.dialog({
		autoOpen: false,
		title: 'Guardar Clem',
		resizable: false,
		height:140,
		modal: true,
		autoOpen: false,
		buttons: {
			"Aceptar": function() {				
				$( this ).dialog( "close" );
				var idForm = "form#datosClemForm";
				$(idForm).submit();				
			},
			"Regresar": function() {
				$( this ).dialog( "close" );
				return false;
			}
		}
	});			    
 

	$('#generaClem').click(function() {
		$dialogCapturaClem.dialog('open');
		return false;
	});
	
	$('#btnRegresar').click(function() {
		var idForm = "#regresaForm";
		$(idForm).submit();
	});

	
});
		

function showSuplente(show){
	if(show){
		document.getElementById("divsuplente").style.visibility = "visible";
		document.getElementById("fieldsuplente").style.visibility = "visible";
		document.getElementById("fieldsuplente2").style.visibility = "visible";
	}else{
		document.getElementById("divsuplente").style.visibility = "hidden";
		document.getElementById("suplente").value ="";
		$('input:checkbox[name="checkFirma"]').attr('checked', false);
		document.getElementById("fieldsuplente").style.visibility = "hidden";
		document.getElementById("fieldsuplente2").style.visibility = "hidden";
	}
} 
