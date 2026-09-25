<!-- JSP Inicial del Widget de Persona Fisica. -->
<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/widget/personaFisicaWidget.js" htmlEscape="true" />"></script>

<div class="contenedor" id="personaFisica"
	widget-name="personaFisicaDatosBasicos"
	<c:choose>
		<c:when test="${not empty readOnly }">
			widget-url="/portal-web/widget/persona/fisica/detalle/<c:out
				value="${fisica.idPersona}"></c:out>/read-only"
			widget-id-principal="${idPersonaPrincipal}" widget-id-tercero="${idPersonaTercero}" >
		</c:when>
		<c:otherwise>
			widget-url="/portal-web/widget/persona/fisica/detalle/<c:out
				value="${fisica.idPersona}"></c:out>"
			widget-id-principal="${idPersonaPrincipal}" widget-id-tercero="${idPersonaTercero}" >
		</c:otherwise>
	</c:choose>

	<div class="encabezado"></div>
	<div class="cuerpo">
		<div class="titulo">
			<c:choose>
				<c:when test="${idPersonaTercero eq '0'}">
					<span><spring:message
							code="label.widget.titulo.persona.portal" /></span>
				</c:when>
				<c:otherwise>
					<span><spring:message
							code="label.widget.titulo.persona.fisica" /></span>
				</c:otherwise>
			</c:choose>
		</div>

		<div class="descripcion">
			<p>
				<spring:message code="label.widget.descripcion.persona.fisica" />
			</p>
		</div>

		<div class="contenido" style="display: none;" already-loaded="false">
			<div style="text-align: center; vertical-align: middle;">
				<img alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
			</div>
		</div>


		<div class="estado"></div>

		<div class="pie">
			<div class="opciones">
				<c:if test="${ empty readOnly}">
					<div class="btn-group">
						<a class="btn btn-default btn-sm" href="#"><spring:message code="label.menus.opciones" /> </a> <a
							class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown"
							href="#"><span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a id="editar">Actualizar Datos</a></li>
						</ul>
					</div>
				</c:if>
			</div>

			<div class="controles">
				<a class="btn btn-sm widget-tool widget-refresh"
					style="display: none;"><i class="icon-refresh"></i></a> <a
					class="btn btn-sm widget-tool widget-resize"><i
					class="icon-plus"></i></a> <a
					class="btn btn-sm widget-tool widget-move"><i
					class="icon-move handle"></i></a>
			</div>
		</div>
	</div>
</div>
