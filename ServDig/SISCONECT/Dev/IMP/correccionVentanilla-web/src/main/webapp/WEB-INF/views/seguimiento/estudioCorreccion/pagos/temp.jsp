

<script>
	function lanzaVentanaPagos(){
		
		
		if(confirm("Este proceso puede tardar varios minutos, desea continuar?")){
			
			/*Lanzar la pantalla de Bloqueo*/
			
	/*		
		VALIDACION
		var accion = "seguimiento/ec/pagos.do?periodoInicial=1/01/2011"
			     +"&periodoFinal=9/06/2012&folioCorreccion=0702/CE/2012/0454"
			     +"&idPresentacion=128&indTipoPago=2&fechaMinDateCalendar=1/10/2012";
	*/
	
		//RECEPCION
		var accion = "seguimiento/ec/pagos.do?periodoInicial=1/12/2011"
			     +"&periodoFinal=1/03/2012&folioCorreccion=0119/CE/2012/0007"
			     +"&idPresentacion=1&indTipoPago=1&fechaMinDateCalendar=1/10/2012";
	
	
	
	/* 
	//PROMOCION
	var accion = "seguimiento/ec/pagos.do?periodoInicial=1/01/2011"
	     +"&periodoFinal=9/06/2012&cveRegulaPagos=11&indTipoPago=3&fechaMinDateCalendar=1/10/2012";

	*/
			var resultado = openWindowPagosSeguimientoFII(getAppContextParaJS(),accion);
			
			alert("Resultado="+resultado);
		}else{
			alert("Cancelo.");
		}
		
		
		
	}
</script>

<input type="button" onclick="lanzaVentanaPagos()" value="Test Ventana Pagos"/>