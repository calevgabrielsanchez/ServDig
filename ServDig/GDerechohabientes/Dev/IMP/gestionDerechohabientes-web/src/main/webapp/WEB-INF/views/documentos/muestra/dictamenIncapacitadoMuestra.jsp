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
		<legend><strong>Dictamen para beneficiario incapacitado</strong></legend>
		<center>
		<table>
			<tr >
				<td align="left">¿Existe estado de incapacidad?</td>
				<td align="left">
						<c:if test="${fileReadVB.documentoProbatorio.existeEstadoIncapacidad==0}">
							No
						</c:if>
						<c:if test="${fileReadVB.documentoProbatorio.existeEstadoIncapacidad==1}">
							Si
						</c:if>
					 
				</td>
			</tr>
			<tr>
				<td align="left">Grado de incapacidad:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.gradoIncapacidad}
				</td>
			</tr>
			<tr >
				<td align="left"">Enfermedad que padece (diagn&oacute;stico):</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.diagnosticoPadecimiento}
				</td>
			</tr>
			
			<tr >
				<td align="left">Fecha de inicio del estado incapacitante:</td>
				<td align="left">
					<fmt:formatDate pattern="dd/MM/yyyy" value="${fileReadVB.documentoProbatorio.fechaInicioEnfermedad}"/>
				</td>
			</tr>
			
			<tr >
				<td><br></td>
				<td><br></td>
			</tr>
			
			<tr >
				<td align="left">Nombre del m&eacute;dico que certific&oacute;:</td>
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
				<td align="left">Matr&iacute;cula:</td>
				<td align="left">
					<c:if test="${fileReadVB.documentoProbatorio.medicoFamiliar != null}">
					${fileReadVB.documentoProbatorio.medicoFamiliar.noMatricula}
					</c:if>
					<c:if test="${fileReadVB.documentoProbatorio.medicoFamiliar == null}">
					N/A
					</c:if>
				</td>
			</tr>
			
			<tr >
				<td><br></td>
				<td><br></td>
			</tr>
			
			
			<tr >
				<td align="left">Unidad de medic&iacute;na familiar:</td>
				<td align="left">
					
					${fileReadVB.documentoProbatorio.unidadMedicaFamiliar.nombreCorto}
					</td>
				
			</tr>
			<tr >
				<td align="left">Delegaci&oacute;n:</td>
				<td align="left">
					${fileReadVB.documentoProbatorio.delegacion.descripcion}
					</td>
				
			</tr>
			<tr >
				<td align="left">Fecha de expedici&oacute;n:</td>
				<td align="left">
					<fmt:formatDate pattern="dd/MM/yyyy" value="${fileReadVB.documentoProbatorio.fechaExpedicion}"/>
				</td>
			</tr>
				<jsp:include page="muestraDocumentoDigitalizado.jsp"></jsp:include>
		</table>
		</center>
	</fieldset>
	
</form>


</div>		