
<%@ include file="../general/taglibs.jsp"%>
<script type="text/javascript">
$( function(){
	$("input#NSS").blur(
		function(){
			var _url="/delta-gestionPatronal-web/movimientos/patronales/buscarNSS";	
			var data=$("input#NSS").val();
			$.ajax({
				url : _url,
				async : true,
				type : "POST",
				data : data ? JSON.stringify(data) : null,
				dataType : "json",
				contentType : "application/json; charset=utf-8", 
				success: function(respuesta){
					$("input#CURP").val(respuesta.curp)
					$("input#nombreAsegurado").val(respuesta.nombre);
					$("input#apellidoPaterno").val(respuesta.primerApellido);
					$("input#apellidoMaterno").val(respuesta.segundoApellido);
						
					
							
				},	
				
				error:function(respuesta){}							
			});
				
		}
	);

});
</script>
<h3>
	<center>Movimientos Afiliatorios</center>
</h3>
<h3>Lote de Movimientos</h3>

<table style="width: 100% !important; border: none !important;">

	<tr>
		<td class="wide"><lebel>Registro Patronal: </lebel></td>

		<td><input id="Registro Patronal" type="text" maxlength="50"
			value="" style="width: 160px" readonly="readonly"></td>

		<td><class="wide"> <lebel>CURP (Opcional) :</lebel></td>

		<td><input id="CURP" type="text" maxlength="50" value=""
			style="width: 200px"></td>
	</tr>

	<tr>
		<td><class="wide"> <lebel>Tipo Movimiento</lebel></td>

		<td><select style="width: 80px !important">
				<option>Tipo Movimiento</option>
		</select></td>

		<td><class="wide"> <lebel>UMF</lebel></td>

		<td><input id="UMF" type="text" maxlength="50" value=""
			style="width: 200px" readonly="readonly">
	</tr>

	<tr>
		<td><class="wide"> <label><left>Numero de
				Seguridad <br>
				Social</label></td>


		<td><input id="NSS" type="text" maxlength="50" value=""
			style="width: 160px"></td>

		<td><class="wide"> <lebel>Salario Diario
			Integrado</lebel></td>

		<td><input id="salarioDiario" type="text" maxlength="50" value=""
			style="width: 200px" readonly="readonly"></td>

	</tr>

	<tr>
		<td><class="wide"> <lebel>Digito Verificador</lebel></td>

		<td><input id="digitoVerificador" type="text" maxlength="50"
			value="" style="width: 50px" readonly="readonly"></td>

		<td><class="wide"> <lebel>Tipo de Trabajador</lebel></td>

		<td><input id="tipodeTrabajador" type="text" maxlength="50"
			value="" style="width: 200px" readonly="readonly"></td>

	</tr>

	<tr>
		<td><class="wide"> <lebel>Apellido Paterno</lebel></td>

		<td><input id="apellidoPaterno" type="text" maxlength="50"
			value="" style="width: 200px" readonly="readonly"></td>

		<td><class="wide"> <lebel>Tipo de Salario</lebel></td>

		<td><input id="tipoSalario" type="text" maxlength="50" value=""
			style="width: 200px" readonly="readonly"></td>

	</tr>

	<tr>
		<td><class="wide"> <lebel>Apellido Materno</lebel></td>

		<td><input id="apellidoMaterno" type="text" maxlength="50"
			value="" style="width: 200px" readonly="readonly"></td>

		<td><class="wide"> <lebel>Jornada Reducida</lebel></td>

		<td><input id="apellidoMaterno" type="text" maxlength="50"
			value="" style="width: 200px" readonly="readonly"></td>
	</tr>

	<tr>
		<td><class="wide"> <lebel>Nombre del Asegurado<lebel></td>

		<td><input id="nombreAsegurado" type="text" maxlength="50"
			value="" style="width: 200px" readonly="readonly"></td>

		<td><class="wide"> <lebel>Fecha de Movimiento</lebel></td>

		<td><select style="width: 50px !important">
				<option>dd</option>
		</select> <select style="width: 50px !important">
				<option>mm</option>
		</select> <select style="width: 70px !important">
				<option>yyyy</option>
		</select></td>
	</tr>

	<tr>
		<td><class="wide"> <lebel>Clave del Trabajador <br>
			(Opcional)</lebel></td>

		<td><input id="claveTrabajador" type="text" maxlength="50"
			value="" style="width: 160px" readonly="readonly"></td>

		<td><class="wide"> <lebel>Causa de Baja</lebel></td>

		<td><select style="width: 140px !important">
				<option>CausaBaja</option>
		</select><br></td>

	</tr>

	<tr>

		<td><input type="button" id="btnEliminarLote" class="mboton"
			style="width: 150px; height: 30px" onclick="altapatronalNC(); "
			value="Eliminar Lote">
		</td>

		<td><input type="button" id="btnAgregar" class="mboton"
			style="width: 100px; height: 30px" onclick="altapatronalNC(); "
			value="Agregar"> 
			
			<input type="button" id="btnEnviar"
			class="mboton" style="width: 100px; height: 30px"
			onclick="altapatronalNC(); " value="Enviar">
		</td>
		
		<td>
			<input type="button" id="btnPrueba"
			class="mboton" style="width: 100px; height: 30px"
			onclick="altapatronalNC(); " value="Prueba">
		
		<script>
			function altapatronalNC (URL){ 
						window.open("http://${mvn.url.imssdigital}/portal-web/portal/", "altapatronalNC" , "width=700,height=500,scrollbars=NO")
			}
		</script>
	
		</td>
		