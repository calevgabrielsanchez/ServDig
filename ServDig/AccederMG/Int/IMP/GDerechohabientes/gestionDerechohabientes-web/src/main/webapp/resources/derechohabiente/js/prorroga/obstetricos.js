$(document).ready(function() {
	
	$("#fechaInicioCadena").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate: new Date(), 
		yearRange : '-112:+0'
	});
	$("#fechaFinCadena").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : '-112:+1'
	});
	$("#fechaExpedicionCadena").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate: new Date(), 
		yearRange : '-112:+0'
	});
	
	$("#cmbMedicos").change(function() {
		index = $(this)[0].selectedIndex;
		var url = "/${mvn.web.app.root}/prorroga/getMedico"
			var	medicoFamiliar = {
				'idMedicoFamiliar' : index
			};
			
			$.postJSON(url, medicoFamiliar, function(result) {
				
				$("#nombreMedico").val(result.modelo);
			});
	
			
	});
});