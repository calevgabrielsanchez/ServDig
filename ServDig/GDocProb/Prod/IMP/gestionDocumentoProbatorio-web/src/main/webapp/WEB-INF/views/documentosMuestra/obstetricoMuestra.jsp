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
		<legend><strong>Obst&eacute;trico</strong></legend>
		<center>
		<table>
			<tr >
				<td align="left">Fecha de expedici&oacute;n:</td>
				<td>
					<fmt:formatDate pattern="dd/MM/yyyy" value="${fileReadVB.documentoProbatorio.fechaExpedicion}"/>
				</td>
			</tr>
			<tr >
				<td align="left">Fecha probable de concepci&oacute;n:</td>
				<td>
					<fmt:formatDate  value="${fileReadVB.documentoProbatorio.fechaProbableConcepcion}"/>
				</td>
			</tr>
			<tr >
				<td align="left">Fecha de parto:</td>
				<td>

					<fmt:formatDate  value="${fileReadVB.documentoProbatorio.fechaParto}"/>
				</td>
			</tr>
			
			<tr >
				<td align="left">Fecha de certificaci&oacute;n M&eacute;dico:</td>
				<td>
			
					<fmt:formatDate pattern="dd/MM/yyyy" value="${fileReadVB.documentoProbatorio.fechaCertificacionMedico}"/>	
				</td>
			</tr>
			
			<tr >
				<td><br> </td>
				<td><br> </td>
			</tr>
			
			<tr >
				<td align="left">Nombre del m&eacute;dico familiar que certific&oacute; :</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.medicoFamiliar.nombre} ${fileReadVB.documentoProbatorio.medicoFamiliar.primerApellido}
				</td>
			</tr>
			<tr >
				<td align="left">Matr&iacute;cula :</td>
				<td align="left">
				
				${fileReadVB.documentoProbatorio.medicoFamiliar.noMatricula}
				</td>
			</tr>
				<jsp:include page="muestraDocumentoDigitalizado.jsp"></jsp:include>
		</table>
		</center>
	</fieldset>
	
</form>


</div>		