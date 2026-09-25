<!-- JSP Inicial del Widget de Persona Fisica. -->
<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/portlet/dummy/portletDummy.js" htmlEscape="true" />"></script>

<div class="contenedor" id="portletDummy"
	portlet-url="/portalDerechohabiente-web/portlet/resumen/${idDummy}"
	portlet-name="portletDummy" portlet-id-principal="${idDummy}"
	portlet-id-tercero="${idDummy}">

	<div class="encabezado"></div>
	<div class="cuerpo">

		<div class="titulo">
			<div class="controles" style="display: inline-block; float: left;">
				<a class="widget-tool widget-resize"> <i class="icono-abrir"></i>
				</a>
			</div>

			<span class="title"> <spring:message
					code="label.portlet.titulo.dummy" />
			</span>

			<div class="controles">
				<a class="widget-tool widget-refresh" style="display: none;"><i
					class="icono-refrescar"></i></a> <a
					class="widget-tool widget-move handle"><i class="icono-mover"></i></a>
			</div>
		</div>

		<div class="descripcion" style="display: none;">
			<p>
				<spring:message code="label.portlet.descripcion.dummy" />
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
				<a class="btn btn-primary" id="accionesDummy" href="#"> <spring:message
						code="label.menus.opciones" />
				</a> <a class="btn btn-primary dropdown-toggle" data-toggle="dropdown"
					href="#"><span class="caret"></span></a>
				<ul id="opcionesPortletDummy" class="dropdown-menu pull-right">
					<li class="removable"><a id="abrirWizardDummyPortlet"> Editar
					</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>