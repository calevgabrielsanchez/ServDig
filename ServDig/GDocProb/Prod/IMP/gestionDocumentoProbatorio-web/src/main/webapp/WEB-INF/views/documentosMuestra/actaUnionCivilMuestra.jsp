<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/fileRead/fileRead.js" htmlEscape="true" />"></script>

<script type="text/javascript">

var context_path= "<%=request.getContextPath()%>";
</script>
<div class="form-comment">
<form id="formdocument" >
	<fieldset>
		<legend><strong>Acta de Uni&oacute;n Civil</strong></legend>
		<center>
		<table>
		
			<tr >
				<td align="left">Lugar de emisi&oacute;n: </td>
				<td align="left">
					${fileReadVB.documentoProbatorio.lugarEmision}
				</td>
			</tr>
			<tr >
				<td align="left">Fecha de emisi&oacute;n: </td>
				<td align="left">
					<fmt:formatDate pattern="dd/MM/yyyy" value="${fileReadVB.documentoProbatorio.fechaEmision}"/>
				</td>
			</tr>
			<tr >
				<td align="left">Autoridad Emisora:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.autoridadEmisora.desAutoridadEmisora}
				</td>
			</tr>
			<tr >
				<td align="left">Entidad:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.entidadFederativa.nombre}
				</td>
			</tr>
			<tr >
				<td align="left">N&uacute;mero de referencia: </td>
				<td align="left">
					${fileReadVB.documentoProbatorio.noReferencia}
				</td>
			</tr>
			<jsp:include page="muestraDocumentoDigitalizado.jsp"></jsp:include>
						
		</table>
		</center>
	</fieldset>
	
</form>


</div>	