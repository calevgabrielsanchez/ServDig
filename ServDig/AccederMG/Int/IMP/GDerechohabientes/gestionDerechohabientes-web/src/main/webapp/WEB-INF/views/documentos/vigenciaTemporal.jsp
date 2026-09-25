<%@ include file="../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/vigenciaTemporal.js" htmlEscape="true" />"></script>


<div class="form-comment">
<form id="formdocument">
	<fieldset>
		<!-- <legend><strong><spring:message code="label.datosGrupo"/> </strong></legend> -->
		<legend><strong>Solicitud de pensi&oacute;n</strong></legend>
		<center>
		<table>
			<tr >
				<td align="left">Fecha de expedici&oacute;n: </td>
				<td align="left">
					<input type="text" id="fechaExpedicion" name="fechaExpedicionCadena" value="" style="width: 200px" readonly="readonly" onclick=" $( this ).datepicker();"/>
				</td>
			</tr>
			<tr >
				<td align="left">N&uacute;mero de folio:</td>
				<td  align="left">
					<input type="text" class="alfanumerico" id="noFolio" name="noFolio" value="" style="width: 200px" maxlength="8"/>
				</td>
			</tr>
						
		</table>
		</center>
	</fieldset>
	
</form>


</div>		