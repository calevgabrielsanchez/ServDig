<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/fileRead/fileRead.js" htmlEscape="true" />"></script>

<script type="text/javascript">

	var context_path= "<%=request.getContextPath()%>";
</script>
<div class="form-comment">
<form id="formdocument" >
	<fieldset>
		<!-- <legend><strong><spring:message code="label.datosGrupo"/> </strong></legend> -->
		<legend><strong>Constancia de estudios</strong></legend>
		<center>
		<table>
			<tr >
				<td align="left">Nivel de estudios:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.detalleNivelEducativo.tipoNivelEducativo.desNivelEducativo}
				</td>
			</tr>
			<tr >
				<td align="left">Detalle del nivel de estudios:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.detalleNivelEducativo.nivelEducativo.desNivelEducativo}
				</td>
			</tr>
			<tr >
				<td align="left">Nombre de la instituci&oacute;n educativa:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.nombreEscuela}
				</td>
			</tr>
			
			<tr >
				<td align="left">Clave de la instituci&oacute;n educativa:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.claveEscuela}
				</td>
			</tr>
			
			<tr >
				<td align="left">N&uacute;mero de incorporaci&oacute;n a la SEP:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.noIncorporacion}
				</td>
			</tr>
			<tr >
				<td align="left">Grado escolar:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.gradoEscolar}
				</td>
			</tr>
			<tr >
				<td align="left">Fecha de expedici&oacute;n:</td>
				<td>
					<fmt:formatDate pattern="dd/MM/yyyy" value="${fileReadVB.documentoProbatorio.fechaExpedicion}"/>
				</td>
			</tr>
			<tr >
				<td align="left">Fecha de inicio del periodo escolar:</td>
				<td align="left">
					<fmt:formatDate pattern="dd/MM/yyyy" value="${fileReadVB.documentoProbatorio.fechaInicioPeriodo}"/>
				</td>
			</tr>
			
			<tr >
				<td align="left">Fecha de t&eacute;rmino del periodo escolar :</td>
				<td align="left">
					<fmt:formatDate pattern="dd/MM/yyyy" value="${fileReadVB.documentoProbatorio.fechaFinPeriodo}"/>
				</td>
			</tr>
				<jsp:include page="muestraDocumentoDigitalizado.jsp"></jsp:include>
		</table>
		</center>
	</fieldset>
	
</form>


</div>		