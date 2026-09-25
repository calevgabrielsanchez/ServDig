<!-- JSP Inicial del Widget de Persona Fisica. -->
<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/portlet/solicitudesPortlet.js" htmlEscape="true" />"></script>

<div class="contenedor" id="listaSolicitudes"
	portlet-url="/portal-web/portlet/solicitudes/resumen/${persona.idPersona}/${persona.tipoPersona.idTipoPersona}"
	portlet-name="solicitudesPersona"
	portlet-id-principal="${idPersonaPrincipal}"
	portlet-id-tercero="${idPersonaTercero}">

	<div class="encabezado"></div>
	<div class="cuerpo">

		<div class="titulo">			
			<span class="title">
				<c:choose>
					<c:when test="${portalContext eq 1 }">
						<spring:message code="label.portlet.titulo.solicitudes.persona" />
					</c:when>
					<c:otherwise>
						<spring:message code="label.portlet.titulo.solicitudes.tercero" />
					</c:otherwise>
				</c:choose>
			</span>

			<div class="controles">
				<a class="widget-tool widget-refresh" style="display: none;"><i
					class="icono-refrescar"></i></a> <a
					class="widget-tool widget-move handle"><i class="icono-mover"></i></a>
				<a class="widget-tool widget-resize"> <i class="icono-abrir"></i>
				</a>
			</div>
		</div>

		<div class="descripcion" style="display: none;">
			<p>
				<spring:message code="label.portlet.descripcion.solicitudes" />
			</p>
		</div>

		<div class="contenido" style="display: none;" already-loaded="false">
			<div style="text-align: center; vertical-align: middle;">
				<img alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
			</div>
		</div>
	</div>

	<div class="estado"></div>

	<div class="pie"></div>
</div>