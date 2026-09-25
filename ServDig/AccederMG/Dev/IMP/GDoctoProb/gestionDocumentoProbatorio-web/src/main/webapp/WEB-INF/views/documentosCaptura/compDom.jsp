<%@ include file="../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/additional-methods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/compDom.js" htmlEscape="true" />"></script>

	
<div class="form-comment">
	<form id="formdocument">
		<fieldset>
			<table class="table table-striped table-bordered">
				<tr>
					<td>
						<label class="control-label" for="folio">N&uacute;mero de folio o comprobante<span class="required">*</span>:</label>
					</td>
					<td>
						<input type="text"  id ="folio" name = "folio" class="entero_15 form-control" value="" maxlength="15"/>
					</td>
				</tr>
				<tr >
					<td>
						<label class="control-label" for="fechaExpedicionString">Fecha de expedici&oacute;n<span class="required">*</span>:</label>
					</td>
					<td>
						<input type="text" id="fechaExpedicionString" class="form-control" name="fechaExpedicionString" value="" readonly="readonly"/>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>		