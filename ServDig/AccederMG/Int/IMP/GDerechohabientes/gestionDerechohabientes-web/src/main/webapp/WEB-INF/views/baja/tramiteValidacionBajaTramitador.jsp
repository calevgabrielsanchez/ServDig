<%@ include file="../general/taglibs.jsp"%>

<c:choose>
<c:when test="${empty errores}">
<div>
	Continuando con la validaci&oacute;n del tr&aacute;mite de Baja ...
	<form>
		<input type="hidden" value="${tramite.persona.idPersona}" id="idPersona" name="idPersona" />
		<input type="hidden" value="${solicitud.solicitudId}" id="idSolicitudVal" name="idSolicitud" />
		<input type="hidden" value="${tramite.tramiteId}" id="idTramite" name="idTipoTramite" />
	</form>
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