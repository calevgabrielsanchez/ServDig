<%@ include file="../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
	
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/additional-methods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/cartillaMilitar.js" htmlEscape="true" />"></script>	

<div class="form-comment">
<form id="formdocument">
	<fieldset>
		<table class="table table-striped table-bordered">
		<tr >
				<td align="left">
					<label class="control-label" for="fechaExpedicion">Fecha de expedici&oacute;n<span class="required">*</span>:</label>
				</td>
				<td align="left">
					<input type="text" class="form-control" id="fechaExpedicion" name="fechaExpedicionString" value="" readonly="readonly" onclick=" $( this ).datepicker();"/>
				</td>
		</tr>
	
		
			
			<tr >
				<td align="left">
					<label class="control-label" for="noMatricula">N&uacute;mero de matr&iacute;cula<span class="required">*</span>:</label>
				</td>
				<td>
					<input class="entero_15 form-control"  type="text"  name = "noMatricula" value="" maxlength="16"/>
				</td>
			</tr>
			
			
		</table>
	</fieldset>
	
</form>


</div>		