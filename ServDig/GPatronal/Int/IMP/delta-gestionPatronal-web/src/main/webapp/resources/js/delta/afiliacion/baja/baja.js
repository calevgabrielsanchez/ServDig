var gridRegistrosPatronales;
function inicializarGridDeRegistrosPatronales() {
	gridRegistrosPatronales = $('#gridRegistrosPatronales').dataTable({
		"bJQueryUI" : false,
		"bPaginate" : true,
		"bLengthChange" : false,
		"bServerSide" : false,
		"iDisplayLength" : 5,
		"sPaginationType" : "full_numbers",
		"bFilter" : true,
		"bSort" : false,
		"bInfo" : false,
		"bAutoWidth" : true,
		"aoColumns" : columnasRegistroPatronal,
		"sAjaxSource" : context_path + "/afiliacion/cargaRegistrosPatronales",
		"fnServerData" : cargarGridRegistrosPatronales
	});

	inicializaEstiloGrid($("#gridRegistrosPatronales tbody"),
			gridRegistrosPatronales);

}

function cargarGridRegistrosPatronales(sSource, aoData, fnCallback) {
	var rfc = $("#fisica\\.rfc").val();
	var wrapper = new Object();
	wrapper.oForm = new Object();
	if (rfc.lenght == 13) {
		wrapper.oForm.fisica=new Object();
		wrapper.oForm.tipoPersonaFiscal="FISICA";
		wrapper.oForm.fisica.rfc = $("#fisica\\.rfc").val();
	} else {
		wrapper.oForm.moral = new Object();
		wrapper.oForm.tipoPersonaFiscal="MORAL";
		wrapper.oForm.moral.rfc = $("#moral\\.rfc").val();
	}

	wrapper.aoData = aoData;
	$.postJSON(sSource, wrapper, function(data) {
		fnCallback(data);
	});
}


function actualizarListaRegistrosPatronales(){
	if(gridRegistrosPatronales == undefined)
		inicializarGridDeRegistrosPatronales();
	else
		gridRegistrosPatronales.fnDraw();
}

$(document).ready(function() {
	
});