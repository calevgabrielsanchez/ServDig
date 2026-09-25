<%@ include file="../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/additional-methods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/adimss.js" htmlEscape="true" />"></script>	
	

<div class="form-comment">
<form id="formdocument">
	<fieldset>
		<table class="table table-striped table-bordered">
			<tr >
				<td align="left">
					<label class="control-label" for="folio">Folio<span class="required">*</span>: </label>
				</td>
				<td align="left">
					<input type="text" class= "entero_20 form-control" id = "folio" name="folio" value="" maxlength="20"/>
				</td>
			</tr>
			<tr >
				<td align="left">
					<label class="control-label" for="fechaExpedicion">Fecha de expedici&oacute;n<span class="required">*</span>:</label>
				 </td>
				<td align="left">
					<input type="text" class="form-control" id="fechaExpedicion" name="fechaExpedicion" value="" readonly="readonly" />
				</td>
			</tr>
		</table>
	</fieldset>
	
</form>


</div>		