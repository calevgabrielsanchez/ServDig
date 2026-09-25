<!-- JSP Inicial del Portlet de Clasificacion. -->
<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/portlet/socios/sociosPortlet.js" htmlEscape="true" />"></script>

<div class="contenedor" id="listaSocios"
	portlet-url="/delta-gestionPatronal-web/portlet/empresas/socios/detalle/${socio.idPersonaMoralPatron}"
	portlet-name="sociosPortlet"
	portlet-id-principal="${idPersonaPrincipal}"
	portlet-id-tercero="${idPersonaTercero}">

	<div class="encabezado"></div>
	<div class="cuerpo">
		
		<div class="titulo">
			<span><spring:message
					code="label.portlet.socios.titulo" /> </span>

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
				<spring:message code="label.portlet.descripcion.socios" />
			</p>
		</div>

		<div class="contenido" style="display: none;" already-loaded="false">
			<div style="text-align: center; vertical-align: middle;">
				<img alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
			</div>
			<br> <br>
		</div>
	</div>
	<div class="estado"></div>

	<div class="pie">
		<div class="opciones">
			<div class="btn-group">
				<a class="btn btn-primary btn-sm" href="#"><spring:message code="label.menus.opciones" /> </a> <a
					class="btn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown"
					href="#"><span class="caret"></span></a>
				<ul id="opcionesSociosPortlet" class="dropdown-menu  pull-right"></ul>
			</div>
		</div>
	</div>
	<div id="dialogoMensajes">
		<p><span id="textoMensaje"></span></p>
	</div>
</div>
