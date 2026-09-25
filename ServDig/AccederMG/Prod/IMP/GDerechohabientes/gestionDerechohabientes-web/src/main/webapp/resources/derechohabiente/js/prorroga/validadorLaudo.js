 $(document).ready(function(){
  //inicio de funcion ready
	 $.datepicker.setDefaults({
			onClose: function(){
				$(this).valid();
			}
		});
	 
	 $("#fechaInicio").mask("99/99/9999");
	 $("#fechaInicio").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			yearRange : '-112:+0',
			onSelect: function() {
				  $('#fechaFin').datepicker('option', {minDate: $("#fechaInicio").datepicker('getDate')});
				}
		});
	 
	 $("#fechaFin").mask("99/99/9999");
	 $("#fechaFin").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			yearRange : '-0:+10'
		});
	 $("#fechaExpedicionCadena").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate: new Date(), 
			yearRange : '-112:+0'
		});
	 
	
		
	 
    $("#frmRegistroProrroga").validate({
    	rules:{ 
    		fechaInicio:{
				required:true,
				validDate : true
			},
			observaciones:{
				required:true
			},
			fechaFin:{
				required:true,
				validDate : true,
				fechaMayorQue : "#fechaInicio"
			},
			idCaracter:{
				required:true
			}
    						
			},
 		messages: { 
 			fechaInicio:{
				required:"Obligatorio",
				validDate : "Fecha inv\u00e1lida"
			},
			observaciones:{
				required:"Obligatorio"
			},
			fechaFin:{
				required:"Obligatorio",
				validDate : "Fecha inv\u00e1lida",
				fechaMayorQue : "La fecha de t\u00e9rmino debe ser mayor o igual que la fecha de inicio y/o mayor o igual al dia de hoy."
			},
			idCaracter:{
				required:"Obligatorio"
			}
			
 		}
    });
  
  //fin de funcion ready
  });