


/*
 * Seccion de codigo a ejecutar en cuanto el DOM envie la señar de que esta
 * listo para procesar de modificaciones al DOM
 */
$(document).ready(
		function() {
			
			$.ajax({
				  url: context_path + '/domicilio/nacional/detalle/100',
				  success: function(data) {
				    $('div#centrotrabajo').html(data);
				    
				  }
				});
			
			var url = context_path + '/domicilio/nacional/detalle/100';
			
			var idDomicilio = 100;
			
			$.getJSON( url , { 'idDomicilio' : idDomicilio} , function(data){
					alert("Domicilio" + data);
			});
			
			
		});




/*
 * Setting modal dialog properties
 */
function fnSetValues(){
	  var iHeight = '900px';
	  var iWidth = '1000';
	   var sFeatures="dialogwidth: " + iHeight + "px;" ;
	   return sFeatures;
	}



/**
 * Abre el dialogo para el manejo del modulo de personas.
 */
	function fnOpenRegistroPersona(){
		
	   var sFeatures=fnSetValues();
	   
	   var oSendData = new Object();
	  
	   var url = "/gestionIndividuos-web/persona/fisica/registro";
	   
	   var oReturn =  window.showModalDialog(url , oSendData, sFeatures);
			   
			   
		if(oReturn != null){
			if(oReturn.calle != null){
						   
				$("#domicilioLocalizado").text(oReturn.calle + ", " + oReturn.nombreLocalidad);
				
				
				
				
			}
		}		   
			   
	}
	
	
	
	/**
	 * Abre el dialogo para el manejo del modulo de domicilio
	 */
		function fnOpenDomicilio(){
			
		   var sFeatures=fnSetValues();
		   
		   var oSendData = new Object();
		  
		   var url = context_path +"/domicilio/registro/embebed";
		   
		   var oReturn =  window.showModalDialog(url , oSendData, sFeatures);
				   
				   
			if(oReturn != null){
				if(oReturn.calle != null){
					$("#domicilioLocalizado").text(oReturn.calle + ", " + oReturn.nombreLocalidad);
					
				}
			}		   
				   
		}	