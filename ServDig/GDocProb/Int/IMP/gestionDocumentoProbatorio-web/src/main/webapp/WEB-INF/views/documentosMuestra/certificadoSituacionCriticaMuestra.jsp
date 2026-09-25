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
		<legend><strong>Certificado de situaci&oacute;n cl&iacute;nica del derechohabiente por incapacidad f&iacute;sica o ps&iacute;quica</strong></legend>
		<center>
		<table>
			<tr >
				<td align="left">Enfermedad que padece (diagn&oacute;stico):</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.enfermedadPadecida}
				</td>
			</tr>
			
			<tr >
				<td align="left">Fecha probable de inicio :</td>
				<td align="left">
					<fmt:formatDate pattern="dd/MM/yyyy" value="${fileReadVB.documentoProbatorio.fechaProbableInicio}"/>
				</td>
			</tr>
			<tr >
				<td align="left">Fecha probable de t&eacute;rmino:</td>
				<td align="left">
					<fmt:formatDate pattern="dd/MM/yyyy" value="${fileReadVB.documentoProbatorio.fechaTerminoIncapacidad}"/>
				</td>
			</tr>
			
			<tr >
				<td><br></td>
				<td><br></td>
			</tr>
			
			<tr >
				<td align="left">Nombre del m&eacute;dico que certific&oacute;:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.medicoFamiliar.nombre} ${fileReadVB.documentoProbatorio.medicoFamiliar.primerApellido}
				</td>
			</tr>
			<tr >
				<td align="left">Matr&iacute;cula:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.medicoFamiliar.noMatricula}
				
				</td>
			</tr>
			
			<tr >
				<td align="left">Fecha de expedici&oacute;n:</td>
				<td>
					<fmt:formatDate pattern="dd/MM/yyyy" value="${fileReadVB.documentoProbatorio.fechaExpedicion}"/>
				</td>
			</tr>
				<jsp:include page="muestraDocumentoDigitalizado.jsp"></jsp:include>
		</table>
		</center>
	</fieldset>
	
</form>


</div>		