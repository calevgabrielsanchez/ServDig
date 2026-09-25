<%@ include file="../general/taglibs.jsp"%>

<br><br><br>
<div class="form-comment" id="cuerpo">
<c:choose>
<c:when test="${empty errores}">
	<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/baja/autorizacionBaja.js" htmlEscape="true" />"></script>
	<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/fileRead/fileRead.js" htmlEscape="true" />"></script>
	
	<h4 align="center">AUTORIZACI&Oacute;N DE ${tramite.tipoTramite.descripcion}</h4>
	<%@ include file="/WEB-INF/views/prorrogas/grupoFamiliar.jsp" %>
	<fieldset>
		<legend><strong>Datos de la baja</strong></legend>
		<form id="validacion" name="validacion" action="#" method="POST">
		<input type="hidden" value="${tramite.persona.idPersona}" id="idPersona" name="idPersona">
		<input type="hidden" value="${solicitud.solicitudId}" id="idSolicitud" name="idSolicitud">
		<input type="hidden" value="${tramite.tipoTramite.idTipoTramite}" id="idTipoTramite" name="idTipoTramite">
		<input type="hidden" value="${tramite.tramiteId}" id="idTramite" name="idTramite">
		<table style="width: 100%">
			<tbody>
			<c:if test="${tramite.tipoTramite.idTipoTramite == 25}">
				<tr>
				<td align="right" style="width: 150px">
					<spring:message code="label.fechaDefuncion"/>:
				</td>
				<td align="left" colspan="5">
					<input type="text" id="fechaDefuncion" name="fechaDefuncion" disabled="disabled" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${tramite.fechaDefuncion}"/>" style="width: 140px" />
				</td>
				</tr>
				<tr>
			</c:if>
			<tr>
				<td align="right"><spring:message code="label.observaciones"/>: </td>
				<td colspan="5">
				<textarea id="observaciones" name="observaciones"  disabled="disabled" style="height: 40px; width: 800px;">${tramite.observacion}</textarea>
				</td>
			</tr>
			</tbody>
		</table>
		</form>
	</fieldset>
	<br>
		<div id="docProbTramDiv"></div>
		<script type="text/javascript">
			$(document).ready(
				function() {
					initMuestraDocumentosTramite(${tramite.tramiteId});
				}
			);
		</script>
	<br><br>
	<div align="center">
	<form>
		<table>
			<tr>
				<td align="center">
					<input id="autorizar" type="button" value="Aceptar" class="mboton"> 
					<input id="rechazar" type="button" value="Rechazar" class="mboton"/>
					<input id="regresar" type="button" value="Regresar" class="mboton"/>
				</td>
			</tr>
		</table>
	</form>
	</div>
</c:when>
<c:otherwise>
	<%@ include file="/WEB-INF/views/error/paginaExcepcion.jsp" %>
</c:otherwise>
</c:choose>
</div>