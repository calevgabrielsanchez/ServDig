<%@ include file="../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/matriculaConsular.js" htmlEscape="true" />"></script>	
	

<div class="form-comment">
<form id="formdocument">
	<fieldset>
		<legend><strong>Matr&iacute;cula consular</strong></legend>
		<center>
		<table>
			<tr >
				<td align="left">N&uacute;mero de documento: </td>
				<td align="left">
					<input type="text" class= "entero_20" id = "numeroDoc" name="numeroDoc" value="" style="width: 250px" maxlength="20"/>
				</td>
			</tr>
			<tr >
				<td align="left"">Autoridad que emite la matr&iacute;cula: </td>
				<td align="left">
					<input type="text" id="autoridadEmiteMat" class="alfanum" name="autoridadEmiteMat" value="" style="width: 250px" maxlength="50"/>  
				</td>
			</tr>
			<tr >
				<td align="left">Fecha de expedici&oacute;n: </td>
				<td align="left">
					<input type="text" id="fechaExpedicion" name="fechaExpedicion" value="" style="width: 250px" readonly="readonly" onclick=" $( this ).datepicker();"/>
				</td>
			</tr>
			<tr >
				<td align="left">Fecha de vencimiento: </td>
				<td align="left">
					<input type="text" id="fechaVencimiento" name="fechaVencimiento" value="" style="width: 250px" readonly="readonly" onclick=" $( this ).datepicker();"/>
				</td>
			</tr>
			<tr >
				<td align="left"">Calidad migratoria: </td>
				<td align="left">
					<input type="text" id="calidadMigratoria" class="alfanum" name="calidadMigratoria" value="" style="width: 250px" maxlength="50"/>  
				</td>
			</tr>
		</table>
		</center>
	</fieldset>
	
</form>


</div>		