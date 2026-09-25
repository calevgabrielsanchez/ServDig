/**
 * JavaScript for detalleRPAfiliacion.jsp page assholes
 */
var idFiltroDatosContacto = "#gridDatosContacto_filter";
 
var columnasDatosContacto = [
                 {mDataProp: "tipoMedioContacto.descripcion", sTitle : "Tipo de dato de contacto"},
				 {mDataProp: "desFormaContacto", sTitle :"Descripcion"}
				];


$(document).ready(function() {	
	
	oTableDatosContacto = $('#gridDatosContacto').dataTable( {
		"bJQueryUI": false,
		"bPaginate": true,
		"bLengthChange": false,
		"iDisplayLength": 5,
		"sPaginationType": "full_numbers",
		"bFilter": true,
		"bSort": false,
		"bInfo": false,
		"bAutoWidth": true,
		"aoColumns" : columnasDatosContacto,
		"sAjaxSource": context_path + "/afiliacion/cargarDatosContacto",
		"fnServerData": cargarGridDatosContacto
	});
	
	$("#gridDatosContacto tbody").click( function(event) {
		$(oTableDatosContacto.fnSettings().aoData).each(function (){ 
			$(this.nTr).removeClass('row_selected'); 
		});
		if($(event.target.parentNode).hasClass('row_selected')){
			$(event.target.parentNode).removeClass('row_selected');
		}else{
			$(event.target.parentNode).addClass('row_selected');
			var obRowSelected = fnGetRowSelected(oTableDatosContacto);
			//$("#numSolicitud").val(obRowSelected.tramiteId);
			//$("#idSolicitud").val(obRowSelected.solicitudId);
		}
    });
	
	$("#gridDatosContacto tbody").hover(
			function(){
				$(this).css('cursor', 'pointer');
			}
		);
	
	$(idFiltroDatosContacto).dialog({
		autoOpen:false
	});
	
	/*$("#txtBuscar").keyup(function(){
		oTableDatosContacto.fnFilter($("#txtBuscar").val()); 
	});*/
	
	
});

/**
 * end Document ready definition
 */


function cargarGridDatosContacto(sSource, aoData, fnCallback) {
	var wrapper = new Object();
	wrapper.oForm = new Object();
	wrapper.oForm.moral = new Object();
	wrapper.oForm.fisica = new Object();
	
	wrapper.aoData = aoData;
	
	var bFisica = $("#bFisicaHidden").val();
	
	if (bFisica == 'true'){
		wrapper.oForm.fisica.idPersona=document.getElementById('idPersonaFisica').value;
	} else {
		wrapper.oForm.moral.idPersona=document.getElementById('idPersonaMoral').value;
	}
	
	$.postJSON(sSource, wrapper, function(data) {
		fnCallback(data);
	});
}


