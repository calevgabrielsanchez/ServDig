<!-- JSP Inicial del Portlet de Clasificacion. -->
<%@ include file="../../general/taglibs.jsp"%>

<%-- <script type="text/javascript" src="<spring:url value="/static/resources/js/portlet/patronClasificacionPortlet.js" htmlEscape="true" />"></script> --%>

<div class="contenedor" id="listaClasificacion"
	portlet-url="/gestionCobranza-web/portlet/edoadeudo/graficas/${nrp}"
	portlet-name="estadoAdeudo">
	<div class="encabezado"></div>
	<div class="cuerpo">

		<div class="titulo">
			<span><spring:message
					code="label.portlet.titulo.edoadeudo.resumen" /> </span>

			<div class="controles">
				<a class="widget-tool widget-refresh" style="display: none;"><i
					class="icono-refrescar"></i></a> <a
					class="widget-tool widget-move handle"><i class="icono-mover"></i></a>
				<a class="widget-tool widget-resize"> <i class="icono-abrir"></i>
				</a>
			</div>
		</div>

		<input type="hidden" id="nrp" value="${nrp}" />
		<div class="descripcion" style="display: none;">
			<p>
				<spring:message code="label.portlet.descripcion.edoadeudo.resumen" />
			</p>
		</div>

		<div class="contenido" style=" height: 300px; display: none;" already-loaded="false">
			<div style="text-align: center; vertical-align: middle;">
				<img alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
			</div>
		</div>
	</div>

	<div class="estado"></div>

	<div class="pie"></div>
</div>