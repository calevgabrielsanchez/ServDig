<!-- JSP Inicial del Widget de Persona Fisica. -->
<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/widget/ivro-indiv-widget.js" htmlEscape="true" />"></script>

<c:choose>
	<c:when test="${empty readOnly }">
		<c:set var="widgetURL" value="/portal-web/widget/persona/ivro/indiv/detalle/${persona.idPersona}/${persona.tipoPersona.idTipoPersona}/${muestraDomicilio}/${rfc}" />
	</c:when>
	<c:otherwise>
		<c:set var="widgetURL" value="/portal-web/widget/persona/ivro/indiv/detalle/${persona.idPersona}/${persona.tipoPersona.idTipoPersona}/${muestraDomicilio}/${rfc}/read-only" />
	</c:otherwise>
</c:choose>

<div class="contenedor" id="idPersonaIvroIndivWidget"
	widget-name="personaIvroIndivWidget"
	widget-url="${widgetURL}"
	widget-id-principal="${idPersonaPrincipal}"
	widget-id-tercero="${idPersonaTercero}"
	is-readOnly="${readOnly }">

	<div class="encabezado"></div>

	<div class="cuerpo">
		<div class="titulo">
			<div class="controles" style="display: inline-block;">
				<a class="widget-tool widget-resize"> <i class="icono-abrir"></i>
				</a>
			</div>
			<c:choose>
				<c:when test="${idPersonaTercero eq '0'}">
					<span><spring:message
							code="label.widget.titulo.persona.ivro.indiv.portal" /></span>
				</c:when>
				<c:otherwise>
					<span><spring:message
							code="label.widget.titulo.persona.fisica" /></span>
				</c:otherwise>
			</c:choose>
		</div>

		<div class="descripcion" style="display: none;">
			<p>
				<spring:message code="label.widget.descripcion.persona.ivro.seg.indiv" />
			</p>
		</div>

		<div class="contenido" style="display: none;" already-loaded="false"
			load-on-startup="false">
			<div style="text-align: center; vertical-align: middle;">
				<img alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
			</div>
		</div>

		<div class="estado"></div>

		<div class="pie">
			

			<div class="controles">
				<a class="widget-tool widget-refresh" style="display: none;"><i
					class="icono-refrescar"></i></a> <a
					class="widget-tool widget-move handle"><i class="icono-mover"></i></a>
			</div>
		</div>
	</div>
</div>