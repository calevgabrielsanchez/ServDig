<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/additional-methods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/actaPactoCivil.js" htmlEscape="true" />"></script>
<div class="form-comment">
<form id="formdocument">
	<fieldset>
		<table class="table table-striped table-bordered">
		
			<tr >
				<td align="left">
					<label class="control-label" for="noActa">N&uacute;mero de acta<span class="required">*</span>:</label>
				</td>
				<td align="left">
					<input type="text" name="noActa" class="entero_15 form-control" value="" maxlength="16"/>
				</td>
			</tr>
			
			<tr >
				<td align="left">
					<label class="control-label" for="noFoja">N&uacute;mero de foja<span class="required">*</span>:</label>
				</td>
				<td align="left">
					<input type="text" name="noFoja" class="entero_15 form-control" value="" maxlength="8"/>
				</td>
			</tr>
			
			<tr >
				<td align="left">
					<label class="control-label" for="noLibro">N&uacute;mero de libro<span class="required">*</span>:</label>
				</td>
				<td align="left">
					<input type="text"  name = "noLibro" class="entero_15 form-control" value="" maxlength="8"/>
				</td>
			</tr>
		

			<tr >
				<td align="left">
					<label class="control-label" for="fechaSuceso">Fecha de suceso<span class="required">*</span>:</label>
				</td>
				<td align="left">
					<input type="text" class="form-control" id="fechaSuceso" name="fechaSuceso" value="" readonly="readonly" onclick=" $( this ).datepicker();"/>
				</td>
			</tr>
		
			<tr >
				<td align="left">
					<label class="control-label" for="tomo"><span class="required">*</span>Tomo</label>
				</td>
				<td align="left">
					<input type="text" class="entero_15 form-control" name = "tomo" value="" maxlength="20"/>
				</td>
			</tr>

		</table>
	</fieldset>
	
</form>


</div>		