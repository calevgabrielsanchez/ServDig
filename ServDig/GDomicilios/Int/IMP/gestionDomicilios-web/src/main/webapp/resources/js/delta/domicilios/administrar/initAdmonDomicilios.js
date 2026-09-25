var dialogoConfirmar;
var indiceDom;
	
$(document).ready(function() {
		
	$('#tblAdmonDomicilios').dataTable({
		"iDisplayLength": 4,
		"bLengthChange": false,
		"bSort": false,
		"bFilter": false,
		"bAutoWidth": false,
		"aoColumns": [{ "sWidth": "60%" },{ "sWidth": "20%" },{ "sWidth": "20%" }]
	});
	
	dialogoConfirmar = $( "#dialog-confirm" ).dialog({
		resizable: false,
		height:140,
		modal: true,
		autoOpen: false,
		buttons: {
			"ACEPTAR": function() {
				fnHideElement('div#domiciliosParticularesDiv #errorNegocioLabel');
		 		var action = $('#deleteDomForm').attr('action') + "/" + indiceDom;
		 		$('#deleteDomForm').attr('action', action);
		 		$( this ).dialog( "close" );
		 		$('#deleteDomForm').submit();
		 	},
		 	"CANCELAR": function() {
		 		$( this ).dialog( "close" );
		 	}
		 }
	 });
	
	$('.modificarDom').live('click', function(){
		modificarDomicilio();
	});
});

function modificarDomicilio(){
	parent.dialogoModificar.dialog('open');	
}

function eliminarDomicilio(index){
	indiceDom = index;
	dialogoConfirmar.dialog( "open" );
}

function deshacerEliminar(index){
	parent.fnDeshacerEliminarDomicilio(index);	
}