<%@ include file="/WEB-INF/views/general/taglibs.jsp" %>

<c:choose>
<c:when test="${empty errores}">
<script type="text/javascript">
	$(document).ready(
		function() {
			$('#aceptar').click(
				function() {
					$decision = $('<div></div');

					$decision.dialog({
						autoOpen : false,
						resizable : false,
						height : 140,
						title : '',
						modal : true
					}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

					$decision.text('Espere un momento por favor...');
					$decision.dialog('open');
					
					location.href = context_path + "/tramite/comprobanteInternet?idSolicitud=${solicitud.solicitudId}&titulo=${tituloComprobante}";
				});			
		}
	);
</script>
</c:when>
<c:otherwise>
	<script type="text/javascript">
	$(document).ready(
		function() {
			$('#regresar').click(
				function() {
					location.href = context_path + "/inicio/grupoFamiliar";
				});
		}
	);
</script>
</c:otherwise>
</c:choose>


<c:choose>
<c:when test="${empty errores}">
<div class="form-comment">
<form:form id="frmCita" name="frmCita" action="#">
	<br><br><br>
	<fieldset class="titulo">
		<table>
			<caption><b><spring:message code="titulo.cita"/></b></caption>
			<tr>
				<td colspan="2" align="center">
					<b><spring:message code="leyenda1.cita"/>
					<spring:message code="leyenda2.cita"/></b>
				</td>
			</tr>
			<tr>
				<td><br></td>
			</tr>
			<tr>
				<td>
					<spring:message code="label.umf.delegacion"/>
				</td>
				<td>
					<input type="text" id="delegacion" name="delegacion" disabled="disabled" 
					value="<c:out value="${solicitud.citaSolicitud.umf.subdelegacion.delegacion.descripcion}"/>">
				</td>
			</tr>
			<tr>
				<td>
					<spring:message code="label.umf.cita"/>
				</td>
				<td>
					<input type="text" id="umf" disabled="disabled" name="umf" value="<c:out value="${solicitud.citaSolicitud.umf.nombreCorto}"/>">
				</td>
			</tr>
			<tr>
				<td>
					<spring:message code="label.direccionumf.cita"/>
				</td>
				<td>
						<textarea id="direccion" name="direccion"  disabled="disabled" style="height: 50px; width: 250px;">${solicitud.citaSolicitud.umf.desDireccion}</textarea>
				</td>
			</tr>
			<tr>
				<td>
					<spring:message code="label.turno.cita"/>
				</td>
				<td>
					<input type="text" id="turno" name="turno" disabled="disabled" value="<c:out value="${solicitud.citaSolicitud.turno.descripcion}"/>" >
				</td>
			</tr>
			<tr>
				<td>
					<spring:message code="label.horario.cita"/>
				</td>
				<td>
					<input type="text" id="horaInicio" disabled="disabled" name="horaInicio" value="<c:out value="${solicitud.citaSolicitud.turno.horaInicioTurno}"/> - <c:out value="${solicitud.citaSolicitud.turno.horaFinTurno}"/>">					
				</td>
			</tr>
			<tr>
				<td>
					<spring:message code="label.fecha.cita"/>
				</td>
				<td>
					<input type="text" id="fecha" disabled="disabled" name="fecha" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${solicitud.fechaCita}"/>">				
				</td>
			</tr>
		</table>	
		<table>
			<tr>
				<td>
					<input type="button" id="aceptar" value="<spring:message code="button.aceptar"/>" class="mboton"/>
				
				</td>
			</tr>
		</table>		
		</fieldset>
</form:form>
</div>
</c:when>
<c:otherwise>
<div>
	<br>
	<div class="ui-widget-content ui-corner-all">
		<div class="ui-state-error ui-corner-all" align="center">
			<div class="ui-icon ui-icon-alert"></div>
			<p class="ui-helper-reset ui-state-error-text"><spring:message code="${errores}" /></p>
		</div>
	</div>
	<div>
		<form class="form-comment">
			<input type="button" id="regresar" value="<spring:message code="button.regresar"/>" class="mboton"/>
		</form>
	</div>
</div>
</c:otherwise>
</c:choose>
