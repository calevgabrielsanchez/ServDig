<%@ include file="../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
	
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/cartillaMilitar.js" htmlEscape="true" />"></script>	

<div class="form-comment">
<form id="formdocument">
	<fieldset>
		<!-- <legend><strong><spring:message code="label.datosGrupo"/> </strong></legend> -->
		<legend><strong>Cartilla militar</strong></legend>
		<center>
		<table>
		<tr >
				<td align="left">Fecha de expedici&oacute;n: </td>
				<td align="left">
					<input type="text" id="fechaExpedicion" name="fechaExpedicionString" value="" style="width: 200px" readonly="readonly" onclick=" $( this ).datepicker();"/>
				</td>
		</tr>
	
		
			
			<tr >
				<td align="right">N&uacute;mero de matr&iacute;cula:</td>
				<td>
					<input class="entero_15"  type="text"  name = "noMatricula" value="" style="width: 200px" maxlength="16"/>
				</td>
			</tr>
			
			
		</table>
		</center>
	</fieldset>
	
</form>


</div>		