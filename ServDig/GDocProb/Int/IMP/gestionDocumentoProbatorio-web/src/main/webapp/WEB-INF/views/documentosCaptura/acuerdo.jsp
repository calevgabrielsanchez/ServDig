<%@ include file="../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/additional-methods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/acuerdo.js" htmlEscape="true" />"></script>
<div class="form-comment">
	<form id="formdocument">
		<fieldset>
			<table class="table table-striped table-bordered">
				<tr >
					<td align="left">
						<label class="control-label" for="fechaExpedicion">Fecha de expedici&oacute;n<span class="required">*</span>: </label>
					</td>
					<td align="left">
						<input type="text" class="form-control" id="fechaExpedicion" name="fechaExpedicionCadena" value="" readonly="readonly" onclick=" $( this ).datepicker();"/>
					</td>
				</tr>
				<tr >
					<td align="left">
						<label class="control-label" for="instanciaEmiteRes">Instancia que emite la resoluci&oacute;n<span class="required">*</span>:</label>
					</td>
					<td align="left">
						<input class="alfanumerico_espacios form-control" type="text" name="instanciaEmiteRes" value="" maxlength="50"/>
					</td>
				</tr>
				<tr >
					<td align="left">
						<label class="control-label" for="noAcuerdo"><spring:message code="${tituloComp}.num"/><span class="required">*</span>:</label>
					</td>
					<td align="left">
						<input type="text" class="alfanumerico form-control" name="noAcuerdo" value="" maxlength="8"/>
					</td>
				</tr>
							
			</table>
		</fieldset>
	</form>
</div>		