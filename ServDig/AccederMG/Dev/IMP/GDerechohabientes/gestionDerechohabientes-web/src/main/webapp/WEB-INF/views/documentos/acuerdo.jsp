<%@ include file="../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/acuerdo.js" htmlEscape="true" />"></script>
<div class="form-comment">
	<form id="formdocument">
		<fieldset>
			<legend><strong><spring:message code="${tituloComp}"/></strong></legend>
			<center>
			<table>
				<tr >
					<td align="left">Fecha de expedici&oacute;n: </td>
					<td align="left">
						<input type="text" id="fechaExpedicion" name="fechaExpedicionCadena" value="" style="width: 200px" readonly="readonly" onclick=" $( this ).datepicker();"/>
					</td>
				</tr>
				<tr >
					<td align="left">Instancia que emite la resoluci&oacute;n:</td>
					<td align="left">
						<input class="alfanumerico_espacios" type="text" name="instanciaEmiteRes" value="" style="width: 200px" maxlength="50"/>
					</td>
				</tr>
				<tr >
					<td align="left"><spring:message code="${tituloComp}.num"/></td>
					<td align="left">
						<input type="text" class="alfanumerico" name="noAcuerdo" value="" style="width: 200px" maxlength="8"/>
					</td>
				</tr>
							
			</table>
			</center>
		</fieldset>
	</form>
</div>		