<%@ include file="../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/documentos/pasaporte.js" htmlEscape="true" />"></script>
<div class="form-comment">
<form id="formdocument">
	<fieldset>
		<!-- <legend><strong><spring:message code="label.datosGrupo"/> </strong></legend> -->
		<legend><strong>Pasaporte</strong></legend>
		<center>
		<table>
			<tr >
				<td align="left">N&uacute;mero de pasaporte:</td>
				<td align="left">
					<input class="entero_10"  type="text" name="noPasaporte" value="" style="width: 200px" maxlength="10"/>
				</td>
			</tr>
			<tr >
				<td align="left">Fecha de expedici&oacute;n:</td>
				<td align="left">
					<input type="text"  id="fechaExpedicion"  name="fechaExpedicion" value="" style="width: 200px" readonly="readonly" onclick=" $( this ).datepicker();"/>
				</td>
			</tr>
			
			<tr >
				<td align="left">Fecha de caducidad:</td>
				<td align="left">
					<input type="text"  id="fechaCaducidad"  name="fechaCaducidad" value="" style="width: 200px" readonly="readonly" onclick=" $( this ).datepicker();"/>
				</td>
			</tr>
			
		</table>
		</center>
	</fieldset>
	
</form>


</div>		