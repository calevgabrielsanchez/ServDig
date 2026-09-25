<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
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
					<c:if test="${fileReadVB.documentoProbatorio.medicoFamiliar != null}">
					${fileReadVB.documentoProbatorio.medicoFamiliar.nombre} ${fileReadVB.documentoProbatorio.medicoFamiliar.primerApellido}
					</c:if>
					<c:if test="${fileReadVB.documentoProbatorio.medicoFamiliar == null}">
					No Localizado
					</c:if>
				</td>
			</tr>
			<tr >
				<td align="left">Matr&iacute;cula :</td>
				<td align="left">
				<c:if test="${fileReadVB.documentoProbatorio.medicoFamiliar != null}">
					${fileReadVB.documentoProbatorio.medicoFamiliar.noMatricula}
				</c:if>
				<c:if test="${fileReadVB.documentoProbatorio.medicoFamiliar == null}">
					N/A
				</c:if>
				</td>
			</tr>
				<jsp:include page="muestraDocumentoDigitalizado.jsp"></jsp:include>
		</table>
		</center>
	</fieldset>
	
</form>


</div>		