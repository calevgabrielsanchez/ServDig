<!-- JSP Inicial del Widget de Persona Fisica. -->
<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/portlet/representanteLegal/representantes/representantesLegalesContenido.js" htmlEscape="true" />"></script>

<div class="contenedor" id="listaRepresentantesLegales"
	portlet-url="/delta-gestionPatronal-web/portlet/representantes/resumen/${persona.idPersona}/${persona.tipoPersona.idTipoPersona}"
	portlet-name="representantesLegalesPortlet"
	portlet-id-principal="${idPersonaPrincipal}"
	portlet-id-tercero="${idPersonaTercero}">

	<div class="encabezado"></div>
	<div class="cuerpo">

		<div class="titulo">			
			<span>
				<c:choose>
					<c:when test="${portalContext eq 1 }">
						<spring:message
					code="label.portlet.titulo.representantes.legales.persona" /> 
					</c:when>
					<c:otherwise>
						<spring:message
					code="label.portlet.titulo.representantes.legales.tercero" /> 
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
				<spring:message
					code="label.portlet.descripcion.representantes.legales" />
			</p>
		</div>

		<div class="contenido" style="display: none;" already-loaded="false">
			<div style="text-align: center; vertical-align: middle;">
				<img alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
			</div>
		</div>
	</div>

	<div class="estado"></div>

	<div class="pie">
		<div class="opciones">
			<div class="btn-group">
				<a class="btn btn-primary btn-sm" href="#"><spring:message code="label.menus.opciones" /> </a> <a
					class="btn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown"
					href="#"><span class="caret"></span></a>
				<ul id="opcionesRepresentante" class="dropdown-menu  pull-right">
					
				</ul>
			</div>
		</div>
	</div>
</div>
