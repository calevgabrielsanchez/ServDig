<%@ include file="../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/compDom.js" htmlEscape="true" />"></script>

	
<div class="form-comment">
	<form id="formdocument">
		<fieldset>
			<legend><strong>${tituloComp}</strong></legend>
			<center>
			<table>
				<tr>
					<td align="right">N&uacute;mero de folio o comprobante:</td>
					<td>
						<input type="text"  id ="folio" name = "folio" class="entero_15" value="" style="width: 200px" maxlength="15"/>
					</td>
				</tr>
				<tr >
					<td align="left">Fecha de expedici&oacute;n: </td>
					<td align="left">
						<input type="text" id="fechaExpedicionString" name="fechaExpedicionString" value="" style="width: 200px" readonly="readonly"/>
					</td>
				</tr>
			</table>
			</center>
		</fieldset>
	</form>
</div>		