var sIdFormModificarActividades = '#actividadesFormModificar';

var oDialogEliminarActividades;
var sIdDialgoModificarActividades = "#dgModificarActividades";

/** Seccion de codigo a ejectuar cuando el DOM este listo **/
$(function() {

	if ($('#indTransportePropio').val() == 'true')
		$('#indTransportePropioTemp').attr('checked', true);
	else 
		$('#indTransportePropioTemp').attr('checked', false);
	
	if ($('#indTrasporteAjeno').val() == 'true')
		$('#indTrasporteAjenoTemp').attr('checked', true);
	else 
		$('#indTrasporteAjenoTemp').attr('checked', false);
	
	if ($('#indDistribucionEntrega').val() == 'true')
		$('#indDistribucionEntregaTemp').attr('checked', true);
	else 
		$('#indDistribucionEntregaTemp').attr('checked', false);
	
	if ($('#indServicioOtrasPersonas').val() == 'true')
		$('#indServicioOtrasPersonasTemp').attr('checked', true);
	else 
		$('#indServicioOtrasPersonasTemp').attr('checked', false);
	
	$(sIdFormModificarActividades).submit(function(){
		modificarActividades();
		return false;
	});
	
	/*Configuracion del dialogo de agregar nuevo elemento*/
	oDialogModificarActividades = 	$( sIdDialgoModificarActividades).dialog({
		autoOpen:false,
		resizable: false,
		modal: true,
		height:150,
		width:400,
		buttons: {
			"Aceptar": function() {
				$( this ).dialog( "close" );
			}
		}
	});
	
});

/*
 * funciones para navegacion
 */
function fnActividadesGoBack(){
    $("#form-actividades-back").submit();
}

function fnActividadesGoAhead(){
    $("#form-actividades-forward").submit();
}
	
function modificarActividades(){
	
	$('#indTransportePropio').val($('#indTransportePropioTemp').is(':checked'));
	$('#indTrasporteAjeno').val($('#indTrasporteAjenoTemp').is(':checked'));
	$('#indDistribucionEntrega').val($('#indDistribucionEntregaTemp').is(':checked'));
	$('#indServicioOtrasPersonas').val($('#indServicioOtrasPersonasTemp').is(':checked'));

	/*la invocacion a modificar el elemento*/
	var oForm = $(sIdFormModificarActividades).serializeObject(true);
	var sSource = 'actividades/modificar';

	$.postJSON(sSource, oForm, function(data) {
		
		
		
		
		$('#actividadesFormModificar  #cveIdClasificacion').val(data.oForm.cveIdClasificacion);
		$('#actividadesFormModificar #indTransportePropioTemp').val(data.oForm.indTransportePropio);
		$('#actividadesFormModificar #indTrasporteAjenoTemp').val(data.oForm.indTrasporteAjeno);
		$('#actividadesFormModificar #indDistribucionEntregaTemp').val(data.oForm.indDistribucionEntrega);
		$('#actividadesFormModificar #indServicioOtrasPersonasTemp').val(data.oForm.indServicioOtrasPersonas);

		
	});
}
