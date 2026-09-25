<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/fileRead/fileRead.js" htmlEscape="true" />"></script>

<script type="text/javascript">

var context_path= "<%=request.getContextPath()%>";
</script>
<div class="form-comment">
<form id="formdocument" >
	<fieldset>
		<legend><strong>Matr&iacute;cula consular</strong></legend>
		<center>
		<table>
		
			<tr >
				<td align="left">N&uacute;mero de documento:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.numeroDoc}
				</td>
			</tr>
			<tr >
				<td align="left">Autoridad que emite la matr&iacute;cula:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.autoridadEmiteMat}
				</td>
			</tr>
			<tr >
				<td align="left">Fecha de expedici&oacute;n: </td>
				<td align="left">
					<fmt:formatDate pattern="dd/MM/yyyy" value="${fileReadVB.documentoProbatorio.fechaExpedicion}"/>
				</td>
			</tr>
			<tr >
				<td align="left">Fecha de vencimiento: </td>
				<td align="left">
					<fmt:formatDate pattern="dd/MM/yyyy" value="${fileReadVB.documentoProbatorio.fechaVencimiento}"/>
				</td>
			</tr>
			<tr >
				<td align="left">Calidad migratoria:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.calidadMigratoria}
				</td>
			</tr>
			<jsp:include page="muestraDocumentoDigitalizado.jsp"></jsp:include>
						
		</table>
		</center>
	</fieldset>
	
</form>


</div>	