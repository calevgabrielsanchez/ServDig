<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/fileRead/fileRead.js" htmlEscape="true" />"></script>

<script type="text/javascript">

var context_path= "<%=request.getContextPath()%>";
</script>
<div class="form-comment">
<form id="formdocument" >
	<fieldset>
		<legend><strong><spring:message code="${fileReadVB.tituloComp}"/></strong></legend>
		<center>
		<table>
		
			<tr >
				<td align="left">N&uacute;mero de acta:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.noActa}
				</td>
			</tr>
			
			<tr >
				<td align="left">N&uacute;mero de foja:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.noFoja}
				</td>
			</tr>
			
			<tr >
				<td align="left">N&uacute;mero de libro:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.noLibro}
				</td>
			</tr>
			
	
			<tr >
				<td><br></td>
				<td><br></td>
			</tr>
			<tr >
				<td align="left">Entidad federativa:</td>
				<td align="left">
								${fileReadVB.documentoProbatorio.municipio.entidadFederativa.nombre}
				</td>
			</tr>
			<tr >
				<td><br></td>
				<td><br></td>
			</tr>
			<tr >
				<td align="left">Municipio:</td>
				<td align="left">
		            ${fileReadVB.documentoProbatorio.municipio.nombre}
				</td>
			</tr>
			
			<tr >
				<td><br></td>
				<td><br></td>
			</tr>
			<tr >
				<td align="left">Fecha de <spring:message code="${fileReadVB.tituloComp}.fecha"/>:</td>
				<td align="left">
					<fmt:formatDate pattern="dd/MM/yyyy" value="${fileReadVB.documentoProbatorio.fechaSuceso}"/>
				</td>
			</tr>
			<tr >
				<td align="left">Fecha de expedici&oacute;n: </td>
				<td align="left">
					<fmt:formatDate pattern="dd/MM/yyyy" value="${fileReadVB.documentoProbatorio.fechaExpedicion}"/>
				</td>
			</tr>
			<tr >
				<td align="left">N&uacute;mero de juzgado:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.noJuzgado}
				</td>
			</tr>
			<jsp:include page="muestraDocumentoDigitalizado.jsp"></jsp:include>
						
		</table>
		</center>
	</fieldset>
	
</form>


</div>		