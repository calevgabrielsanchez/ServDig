<%@ include file="../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/adimss.js" htmlEscape="true" />"></script>	
	

<div class="form-comment">
<form id="formdocument">
	<fieldset>
		<legend><strong>Credencial ADIMSS</strong></legend>
		<center>
		<table>
			<tr >
				<td align="left">Folio: </td>
				<td align="left">
					<input type="text" class= "entero_20" id = "folio" name="folio" value="" style="width: 250px" maxlength="20"/>
				</td>
			</tr>
			<tr >
				<td align="left">Fecha de expedici&oacute;n: </td>
				<td align="left">
					<input type="text" id="fechaExpedicion" name="fechaExpedicion" value="" style="width: 250px" readonly="readonly" />
				</td>
			</tr>
		</table>
		</center>
	</fieldset>
	
</form>


</div>		