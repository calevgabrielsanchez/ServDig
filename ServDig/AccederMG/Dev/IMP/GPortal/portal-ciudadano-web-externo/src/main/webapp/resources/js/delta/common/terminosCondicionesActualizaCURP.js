var dialogTCActualizaCURP;
$(document).ready(function() {
	
	dialogTCActualizaCURP= $('#divTCCuestionario').dialog({
			title: 'T\u00E9rminos y Condiciones',
			//appendTo: parent,
			resizable: false,
			height: 400,
			width: '90%',
			modal: true,
			autoOpen: false,
		    closeOnEscape: false,
		    position : {
				my : "top",
				at : "top",
				of : window,
				offset : "0 10"
			}
		 });
	
	
	$('#linkTCActualizacion').click(function() {
		
		dialogTCActualizaCURP.dialog('open');
	});
	
	
	$('#cerrarTCCuestionario').click(function() {
			
			dialogTCActualizaCURP.dialog('close');
	})
	
	$("#chkTCActualizaCURP").click(function() {
	
		if($("#chkTCActualizaCURP").is(':checked')){
			$("#hiddenTerminos").val('true');
		}else{
			$("#hiddenTerminos").val('');
		}
		
	});
	
});