<!-- JSP Contenido del Widget de los Domiclios del Centro de Trabajo. -->
<%@ include file="../../../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum"%>

<c:set var="centroTrabajo" value="${sujetoObligado.cntroTrabajo}" />

<input type="hidden" id="hdnNumeroRegistroPatronal" value="${sujetoObligado.numeroRegistroPatronal}${sujetoObligado.modalidad.numModalidad}${sujetoObligado.digVerificador}"/>
<input type="hidden" id="hdnTipoActualizacionCentroTrab" value="<%=TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO.getValor() %>" />
<input type="hidden" id="hdnEsRPC" value="${sujetoObligado.clasificacion.indRegPatClase}" />
<input type="hidden" id="hdnTipoRegPatron" value="${sujetoObligado.idTipoRegPatron}" />

<c:choose>
	<c:when test="${centroTrabajo != null}">
		<address>
			<spring:message code="label.calle.num" />: ${centroTrabajo.vialidadPrimaria.nombre} ${centroTrabajo.numExterior1} ${centroTrabajo.numExteriorAlf} , ${centroTrabajo.numInterior} ${centroTrabajo.numInteriorAlf}<br>
			<spring:message code="label.colonia" />: ${centroTrabajo.asentamiento.nombre}<br>
			<spring:message code="label.municipio" />: ${centroTrabajo.asentamiento.localidad.municipio.nombre}<br>
			<spring:message code="label.entidad.federativa" />: ${centroTrabajo.asentamiento.localidad.municipio.entidadFederativa.nombre}<br>
			C.P. ${centroTrabajo.codigoPostal.codigoPostal}<br>
			
			<spring:message code="label.delegacion" />: ${sujetoObligado.subdelegacion.delegacion.descripcion}<br>
			<spring:message code="label.subdelegacion" />: ${sujetoObligado.subdelegacion.descripcion}<br>
			<c:if test="${ sujetoObligado.municipioIMSS != null }">
				<spring:message code="label.municipio.imss" />: ${sujetoObligado.municipioIMSS.descMunicipio} (${sujetoObligado.municipioIMSS.cvecMunicipioSINDO})<br>
			</c:if>
		</address>
	</c:when>
	<c:otherwise>
		<p><span class="no-data">No cuenta con domicilio.</span></p>
	</c:otherwise>
</c:choose>

<div id="dialogoMensajes">
	<p><span id="textoMensaje"></span></p>
</div>
