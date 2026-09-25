<%@ include file="/WEB-INF/views/general/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/general/impresionDocumentosImport.jsp" %>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/citaProgramada.js" htmlEscape="true" />"></script>

<head>
</head>
<div class="form-comment">
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<form:form commandName="cita" id="frmCita" name="frmCita" method="post">
	<br><br><br>
	<input type="hidden" id="integrante" name="integrante" value="<c:out value="${concubina_rio}"/>"></input>
	<input type="hidden" id="solicitud" name="solicitud" value="<c:out value="${cita.solicitudId}"/>"></input>
	<fieldset class="titulo" style="width: 890px">
		<table>
			<caption><b><spring:message code="titulo.cita"/></b></caption>
			<tr>
				<td colspan="2" align="center">
					<b><spring:message code="leyenda1.cita"/>
					<spring:message code="leyenda2.cita"/>:</b>
				</td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td>
					<spring:message code="label.umf.cita"/>:
				</td>
				<td>
					<input type="text" id="umf" name="umf" value="<c:out value="${cita.citaSolicitud.umf.nombreCorto}"/>">
				</td>
			</tr>
			<tr>
				<td>
					<spring:message code="label.fecha.cita"/>
				</td>
				<td>
					<input type="text" id="fecha" name="fecha" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${cita.fechaCita}"/>">				
				</td>
			</tr>
			<tr>
				<td>
					<spring:message code="label.turno.cita"/>:
				</td>
				<td>
					<input type="text" id="turno" name="turno" value="<c:out value="${cita.citaSolicitud.turno.descripcion}"/>" >
				</td>
			</tr>
			<tr>
				<td>
					<spring:message code="label.horario.cita"/>:
				</td>
				<td>
					<input type="text" id="horaInicio" name="horaInicio" value="<c:out value="${cita.citaSolicitud.turno.horaInicioTurno}"/> - <c:out value="${cita.citaSolicitud.turno.horaFinTurno}"/>">					
				</td>
			</tr>
			<tr>
				<td>
					<spring:message code="label.ventanilla.cita"/>
				</td>
				<td>
					<input type="text" id="ventanillaCita" name="ventanillaCita" value="">					
				</td>
			</tr>
		</table>	
		<table>
			<tr>
				<td>					
					<button type="button" class="mboton" onclick="imprimeComprobante(${cita.solicitudId});"><spring:message code="button.aceptar"/></button>
					</td>
			</tr>
		</table>
		<div id="bajaIntegrante" title ="<spring:message code="titulo.mensajeConfirmacion"/>" style="display:none"> 
			<spring:message code="leyenda.baja"/>	
			<spring:message code="label.baja"/>					
		</div>			
	</fieldset>
</form:form>
</div>