$(document).ready(
	function(){
		
		var fecha= getFecha();
		$( "#fechaInicio" ).datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			yearRange : '-112:+0'
		});
		$( "#fechaFin" ).datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			yearRange : '-112:+0'
		});
		$( "#fechaExpedicionCadena" ).datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			yearRange : '-112:+0'
		});
	
		
		/*$( "#fechaInicioPeriodo" ).val(fecha);
		$( "#fechaFinPeriodo" ).val(fecha);
		$( "#fechaExpedicion" ).val(fecha);*/
		var edad =diferenciaFechas($("#fechaNacimiento").val());
		$( "#edad" ).val(edad);
	}	
);


	function diferenciaFechas (fechaInicio) {  
	   var miFecha1 =  new Date();
	   miFecha1 =  new Date(miFecha1.getFullYear(),miFecha1.getMonth() + 1,miFecha1.getDate() );
	   var miFecha2 =  fecha( fechaInicio)  
	   var diferencia = miFecha1.getTime() - miFecha2.getTime()  
	   var dias = Math.floor(diferencia /(1000 * 60 * 60 * 24))  
	   var anios = Math.floor(dias / 365)  
	     
	   return anios  
	}  

	function fecha( cadena ) {  
	   var separador = "/"  
	   var fecha; 
	   if ( cadena.indexOf( separador ) != -1 ) {  
	        var posi1 = 0  
	        var posi2 = cadena.indexOf( separador, posi1 + 1 )  
	        var posi3 = cadena.indexOf( separador, posi2 + 1 )  
	        this.dia = cadena.substring( posi1, posi2 )  
	        this.mes = cadena.substring( posi2 + 1, posi3 )  
	        this.anio = cadena.substring( posi3 + 1, cadena.length )  
	   } else {  
	        this.dia = 0  
	        this.mes = 0  
	        this.anio = 0     
	   }
	   fecha = new Date(this.anio,this.mes,this.dia);
	  return fecha
   }  

	
	function getFecha() {  
		   var separador = "/"  
		   var fecha = new Date();
		   fechaString="";
		      
		   fecha = fecha.getDate()+"/"+(fecha.getMonth() + 1)+"/"+fecha.getFullYear();
		  return fecha
	   }  
	  