<%@ include file="../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/additional-methods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/cedulaProfesional.js" htmlEscape="true" />"></script>	
	

<div class="form-comment">
<form id="formdocument">
	<fieldset>
		<table class="table table-striped table-bordered">
			<tr >
				<td align="left">
					<label class="control-label" for="cedula">N&uacute;mero de c&eacute;dula profesional<span class="required">*</span>: </label>
				</td>
				<td align="left">
					<input type="text" class= "entero_10 form-control" id = "cedula" name="cedula" value="" maxlength="10"/>
				</td>
			</tr>
			<tr >
				<td align="left">
					<label class="control-label" for="fechaExpedicionString">Fecha de expedici&oacute;n<span class="required">*</span>: </label>
				</td>
				<td align="left">
					<input type="text" class="form-control" id="fechaExpedicionString" name="fechaExpedicionString" value="" readonly="readonly" />
				</td>
			</tr>
			
			<tr >
				<td align="left">
					<label class="control-label" for="profesion">Nombre de la profesi&oacute;n<span class="required">*</span>: </label>
				</td>
				<td align="left">
					<input type="text" id="profesion" class="alfanum form-control" name="profesion" value="" maxlength="50"/>
				</td>
			</tr>
			
		</table>
	</fieldset>
	
</form>


</div>		