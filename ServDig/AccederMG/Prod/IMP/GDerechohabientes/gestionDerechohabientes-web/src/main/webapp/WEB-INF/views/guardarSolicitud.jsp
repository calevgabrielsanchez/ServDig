<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">

	function imprime_sav002() {
		location.href="/${mvn.web.app.root}/prototipo/documentos/9210-009-050-F_SAV002.pdf";
	}
</script>
<!--  <jsp:include page="/prototipo/pageUp.jsp">
	<jsp:param value="Guardar Coreccion de Derechohabiente" name="titulo" />
</jsp:include -->
<div class="form-comment">
<fieldset>
	<legend> <strong>Tu solicitud ha sido guardad acude a tu umf para finalizar el tramite</strong></legend>
	<fieldset>
		<legend> <strong>Comprobantes generados</strong></legend>
		
		<table align="center">
  			<tr>
    			<th align="left">Imprimir SAV002</th>
   			</tr>
  			<tr>
    			<td><input type="button" value="Imprimir" class="mboton" onclick="imprime_sav002();"/></td>
   			</tr>
		</table>
	</fieldset>
	
	<fieldset>
		<legend> <strong>Datos de la solicitud</strong></legend>
		<table align="center">
  			<tr>
    			<th>Folio Registro</th>
   			</tr>
  			<tr>
    			<td>
    				<input type="text" readonly="readonly" value="123348343243567" size="6">
    			</td>
   			</tr>
   			<tr>
    			<td>
    				<input type="button" value="Inicio" onclick="location.href='/${mvn.web.app.root}/prototipo/main.jsp'" size="10">
    			</td>
   			</tr>
		</table>
	</fieldset>
</fieldset>
</div>
	<!-- jsp:include page="/prototipo/pageDown.jsp"/ -->	