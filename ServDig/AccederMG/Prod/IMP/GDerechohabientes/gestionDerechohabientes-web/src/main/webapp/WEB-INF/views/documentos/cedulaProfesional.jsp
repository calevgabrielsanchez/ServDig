<%@ include file="../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/cedulaProfesional.js" htmlEscape="true" />"></script>	
	

<div class="form-comment">
<form id="formdocument">
	<fieldset>
		<legend><strong>C&eacute;dula profesional</strong></legend>
		<center>
		<table>
			<tr >
				<td align="left">N&uacute;mero de c&eacute;dula profesional: </td>
				<td align="left">
					<input type="text" class= "entero_10" id = "cedula" name="cedula" value="" style="width: 250px" maxlength="10"/>
				</td>
			</tr>
			<tr >
				<td align="left">Fecha de expedici&oacute;n: </td>
				<td align="left">
					<input type="text" id="fechaExpedicionString" name="fechaExpedicionString" value="" style="width: 250px" readonly="readonly" />
				</td>
			</tr>
			
			<tr >
				<td align="left">Nombre de la profesi&oacute;n: </td>
				<td align="left">
					<input type="text" id="profesion" class="alfanum" name="profesion" value="" style="width: 250px" maxlength="50"/>
				</td>
			</tr>
			
		</table>
		</center>
	</fieldset>
	
</form>


</div>		