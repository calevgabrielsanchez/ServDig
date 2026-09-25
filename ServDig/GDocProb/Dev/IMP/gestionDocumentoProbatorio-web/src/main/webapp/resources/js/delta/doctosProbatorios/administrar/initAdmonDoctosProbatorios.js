var dialogoConfirmar;
var indiceDoctoProbatorio;
	
$(document).ready(function() {
		
	$('#tblAdmonDoctosProbatorios').dataTable({
		"bSort": false,
		"bFilter": false,
		"bAutoWidth": false,
		"bLengthChange": false,
		"sPaginationType": "bootstrap",
		"aoColumns": [{ "sWidth": "60%" },{ "sWidth": "20%" },{ "sWidth": "20%" }]
		
	});
	
	dialogoConfirmar = $( "#dialog-confirm-DoctoProbatorio" ).dialog({
		resizable: false,
		height:'auto',
		modal: true,
		autoOpen: false,
		buttons: {
			"ACEPTAR": function() {
				fnHideElement('div#documentosProbatoriosDiv #errorNegocioLabel');
		 		var action = $('#deleteDoctoProbatorioForm').attr('action') + "/" + indiceDoctoProbatorio;
		 		$('#deleteDoctoProbatorioForm').attr('action', action);
		 		$( this ).dialog( "close" );
		 		$('#deleteDoctoProbatorioForm').submit();
		 	},
		 	"CANCELAR": function() {
		 		$( this ).dialog( "close" );
		 	}
		 }
	 });
	
});

function modificarDoctoProbatorio(index){
	parent.fnModificarDocProbatorio(index);	
}

function eliminarDoctoProbatorio(index){
	indiceDoctoProbatorio = index;
	dialogoConfirmar.dialog( "open" );
}

function deshacerEliminar(index){
	parent.fnDeshacerEliminarDocProbatorio(index);	
}