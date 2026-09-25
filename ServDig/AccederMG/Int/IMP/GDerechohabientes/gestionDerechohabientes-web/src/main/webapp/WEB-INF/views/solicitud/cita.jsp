<%@ include file="/WEB-INF/views/general/taglibs.jsp" %>

<c:choose>
<c:when test="${empty errores}">
<div class="form-comment">
	<fieldset class="titulo">
		<table>
			<tr>
				<td colspan="2" align="center">
					<b><spring:message code="leyenda1.cita"/>
					<spring:message code="leyenda2.cita"/></b>
				</td>
			</tr>
			<tr>
				<td><br><input type="hidden" id="idSolicitud" value="${cita.solicitudId}"/></td>
				<td></td>
			</tr>
			<tr>
				<td>
					<spring:message code="label.umf.delegacion"/>
				</td>
				<td>
					<input type="text" id="delegacion" name="delegacion" readonly="readonly" value="<c:out value="${cita.citaSolicitud.umf.subdelegacion.delegacion.descripcion}"/>">
				</td>
			</tr>
			<tr>
				<td>
					<spring:message code="label.direccionumf.cita"/>
				</td>
				<td>
					<textarea id="direccion" name="direccion"  readonly="readonly" style="height: 50px; width: 250px;">${cita.citaSolicitud.umf.desDireccion}</textarea>
				</td>
			</tr>
			<tr>
				<td>
					<spring:message code="label.umf.cita"/>
				</td>
				<td>
					<input type="text" id="umf" name="umf" readonly="readonly" value="${cita.citaSolicitud.umf.nombreCorto}"/>
				</td>
			</tr>
			<tr>
				<td>
					<spring:message code="label.turno.cita"/>
				</td>
				<td>
					<input type="text" id="turno" name="turno" readonly="readonly" value="${cita.citaSolicitud.turno.descripcion}" />
				</td>
			</tr>
			<tr>
				<td>
					<spring:message code="label.horario.cita"/>
				</td>
				<td>
					<input type="text" id="horaInicio" name="horaInicio" readonly="readonly" value="${cita.citaSolicitud.turno.horaInicioTurno} - ${cita.citaSolicitud.turno.horaFinTurno}"/>					
				</td>
			</tr>
			<tr>
				<td>
					<spring:message code="label.fecha.cita"/>
				</td>
				<td>
					<input type="text" id="fecha" name="fecha" readonly="readonly" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${cita.fechaCita}"/>"/>				
				</td>
			</tr>
		</table>	
		</fieldset>
</div>
</c:when>
<c:otherwise>
	<div class="ui-widget-content ui-corner-all">
		<div class="ui-state-error ui-corner-all" align="center">
			<p class="ui-helper-reset ui-state-error-text">
				<div class="ui-icon ui-icon-alert"></div><spring:message code="${errores}"/>
			</p>
		</div>
	</div>
</c:otherwise>
</c:choose>	