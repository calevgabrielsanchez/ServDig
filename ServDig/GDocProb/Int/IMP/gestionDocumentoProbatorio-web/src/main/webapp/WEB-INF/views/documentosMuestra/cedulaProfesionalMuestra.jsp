<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/fileRead/fileRead.js" htmlEscape="true" />"></script>

<script type="text/javascript">

	var context_path= "<%=request.getContextPath()%>";
</script>
<div class="form-comment">
<form id="formdocument" >
	<fieldset>
		<legend><strong>C&eacute;dula profesional</strong></legend>
		<center>
		<table>
			<tr >
				<td align="left">N&uacute;mero de c&eacute;dula profesional: </td>
				<td align="left">
					${fileReadVB.documentoProbatorio.cedula}
				</td>
			</tr>
			<tr >
				<td align="left">Fecha de expedici&oacute;n: </td>
				<td align="left">
					<fmt:formatDate pattern="dd/MM/yyyy" value="${fileReadVB.documentoProbatorio.fechaExpedicion}"/>
				</td>
			</tr>
			
			<tr>
				<td align="left">Nombre de la profesi&oacute;n: </td>
				<td align="left">
					${fileReadVB.documentoProbatorio.profesion}
				</td>
			</tr>
			<jsp:include page="muestraDocumentoDigitalizado.jsp"></jsp:include>
		</table>
		</center>
	</fieldset>
	
</form>


</div>		