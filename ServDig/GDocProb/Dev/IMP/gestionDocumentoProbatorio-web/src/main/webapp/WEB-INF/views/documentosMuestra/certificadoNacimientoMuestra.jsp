<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/fileRead/fileRead.js" htmlEscape="true" />"></script>

<script type="text/javascript">

	var context_path= "<%=request.getContextPath()%>";
</script>
<div class="form-comment">
<form id="formdocument" >
<fieldset><!-- <legend><strong><spring:message code="label.datosGrupo"/> </strong></legend> -->
<legend><strong>Certificado de nacimiento</strong></legend>
<center>
<table>
	<tr>
		<td align="left">Folio:</td>
		<td align="left">
			${fileReadVB.documentoProbatorio.noFolio}
		</td>
	</tr>

	<tr>
		<td align="left">Fecha de alumbramiento:</td>
		<td align="left">
			<fmt:formatDate pattern="dd/MM/yyyy" value="${fileReadVB.documentoProbatorio.fechaAlumbramiento}"/>
		</td>
	</tr>

	<tr>
		<td><br>
		</td>
	</tr>


	<tr>
		<td><br>
		</td>
		<td><br>
		</td>
	</tr>
	<tr>
		<td align="left">Unidad de medicina familiar que certifica:</td>


		<td align="left">
			${fileReadVB.documentoProbatorio.unidadMedica.nombreCorto}
			</td>

	</tr>
	<tr >
				<td align="left">Fecha de expedici&oacute;n:</td>
				<td align="left">
					<fmt:formatDate pattern="dd/MM/yyyy"  value="${fileReadVB.documentoProbatorio.fechaExpedicion}"/>
				</td>
	</tr>
	<jsp:include page="muestraDocumentoDigitalizado.jsp"></jsp:include>
</table>
</center>
</fieldset>

</form>


</div>
