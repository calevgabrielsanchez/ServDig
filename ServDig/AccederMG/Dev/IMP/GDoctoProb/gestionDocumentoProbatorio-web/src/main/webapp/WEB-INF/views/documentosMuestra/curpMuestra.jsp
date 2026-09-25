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
		<legend><strong>C.U.R.P.</strong></legend>
		<center>
		<table>
				
			<tr >
				<td align="left">C.U.R.P.:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.curp}
				</td>
			</tr>
			<tr >
				<td align="left">Entidad federativa:</td>
				<td align="left">
								
								${fileReadVB.documentoProbatorio.municipio.entidadFederativa.nombre}
				</td>
			</tr>
			<tr >
				<td align="left">Fecha inscripci&oacute;n:</td>
				<td align="left">
					<fmt:formatDate pattern="dd/MM/yyyy" value="${fileReadVB.documentoProbatorio.fechaInscripcion}"/>
				</td>
			</tr>
	
			
			<tr >
				<td align="left">N&uacute;mero de folio:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.refFolio}
				</td>
			</tr>
			
			<tr id="anioRegRow">
				<td align="left">A&ntilde;o de registro:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.anioRegistro}
				</td>
			</tr>
			
			<tr id="libroRow">
				<td align="left">N&uacute;mero de libro:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.noLibro}
				</td>
			</tr>
			
			<tr id="actaRow">
				<td align="left">N&uacute;mero de acta:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.noActa}
				</td>
			</tr>
			<tr id="tomoRow">
				<td align="left">N&uacute;mero de tomo:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.noTomo}
				</td>
			</tr>
			<tr id="cripRow">
				<td align="left">CRIP:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.crip}
				</td>
			</tr>
		
			<tr id="fojaRow">
				<td align="left">N&uacute;mero de foja:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.noFoja}
				</td>
			</tr>
			
			
			<tr >
				<td>
					<br> 
				</td>
				<td>
					<br>
				</td>
			</tr>
		
				<jsp:include page="muestraDocumentoDigitalizado.jsp"></jsp:include>
								
		</table>
		</center>
	</fieldset>
	
</form>


</div>		