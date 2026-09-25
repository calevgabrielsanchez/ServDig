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
		<legend><strong><spring:message code="${fileReadVB.tituloComp}"/></strong></legend>
		<center>
		<table>
			<tr >
				<td align="left">Fecha de expedici&oacute;n: </td>
				<td align="left">
					<fmt:formatDate pattern="dd/MM/yyyy" value="${fileReadVB.documentoProbatorio.fechaExpedicion}"/>				
				</td>
			</tr>
			<tr >
				<td align="left">Instancia que emite la resoluci&oacute;n:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.instanciaEmiteRes}
				</td>
			</tr>
			<tr >
				<td align="left"><spring:message code="${fileReadVB.tituloComp}.num"/></td>
				<td align="left">
					${fileReadVB.documentoProbatorio.noAcuerdo}
				</td>
			</tr>
			<jsp:include page="muestraDocumentoDigitalizado.jsp"></jsp:include>
						
		</table>
		</center>
	</fieldset>
	
</form>


</div>		