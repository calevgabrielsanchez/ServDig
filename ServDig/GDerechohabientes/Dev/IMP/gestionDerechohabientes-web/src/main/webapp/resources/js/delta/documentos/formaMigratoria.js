
	$(document).ready(function() {  
		console.log('readyOK');
		$.datepicker.setDefaults({
			onClose: function(){
				$(this).valid();
			}
		});
		
		$( "#calidadMigratoria").click(
				function (){
					$("#calidadMigratoriaHidden").val($("#calidadMigratoria option:selected").html());
				console.log('valorcalidad',calidadMigratoriaHidden);
				}
			);
		
		$( "#paisOrigen").click(
				function (){
					$("#paisOrigenHidden").val($("#paisOrigen option:selected").html());
				console.log('pais',paisOrigenHidden);
				}
			);
		
		
		
				$("#fechaExpedicion").mask("99/99/9999");
				$( "#fechaExpedicion" ).datepicker(
				{
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true,
					maxDate: new Date(), 
					yearRange : '-112:+0',
					onSelect: function() {
						  $('#fechaVencimiento').datepicker('option', {minDate: $("#fechaExpedicion").datepicker('getDate')});
						}
				}		
				
				);
				
				$("#fechaVencimiento").mask("99/99/9999");
				$( "#fechaVencimiento" ).datepicker(
						{
							dateFormat : 'dd/mm/yy',
							changeMonth : true,
							changeYear : true,
							yearRange : '-0:+10'
						}		

				);
		
		
		validateForm.allowOnlyRegularExpression( $('.entero_20'),regularExpression.entero_20);
		validateForm.allowOnlyRegularExpression( $('.alfanum'),regularExpression.alfanumerico_espacios);
		
		var isDocumentacionValida =	$("#formdocument").validate({ 

			rules: { 

				numeroDoc: {
					required:true,
					maxlength: 20,
					number  : true
				}, 
				
				paisOrigen: {
					required:true,
					min: 0
				},
				
				fechaCaducidad: {
					required:true,
					validDate : true
				},	
				
				fechaExpedicion: {
					required:true,
					validDate : true,
					validDateToDay : true
				},
				
				calidadMigratoria: {
					required:true,
					min: 0
					
				}
							
			}, 
		errorLabelContainer: "#warning", 

 
		messages: { 

			numeroDoc: {required:"Obligatorio", maxlength:"Debe ser de 20 caracteres como m\u00e1ximo", number: "Debe ser n\u00famerico"},
			paisOrigen: {required:"Obligatorio",min:"Obligatorio"},
			fechaExpedicion: {required:"Obligatorio", validDate : "Fecha inv\u00e1lida"},
			fechaVencimiento: {required:"Obligatorio", validDate : "Fecha inv\u00e1lida", validDateToDay : "La fecha no puede ser mayor a hoy."},
			calidadMigratoria: {required:"Obligatorio",min:"Obligatorio"},
		} 	
			
		}); 
	}); 
		
	