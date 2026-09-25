<!-- JSP Inicial del Widget de Persona Fisica. -->
<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/widget/mediosCentroTrabajoWidget.js" htmlEscape="true" />"></script>

<div class="contenedor" id="mediosCentroTrabajo"
	widget-url="/portal-web/widget/medios/centroTrabajo/resumen/${sujetoObligado.numeroRegistroPatronal}"
	widget-name="mediosCentroTrabajoWidget"
	widget-id-principal="${idPersonaPrincipal}"
	widget-id-tercero="${idPersonaTercero}">

	<div class="encabezado"></div>
	<div class="cuerpo">

		<div class="titulo">
			<span><spring:message
					code="label.widget.titulo.medios.centroTrabajo" /></span>
		</div>

		<div class="descripcion" style="display: none;">
			<p>
				<spring:message code="label.widget.descripcion.medios.centroTrabajo" />
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
				<!--
		<div class="btn-group">
			<a class="btn btn-default btn-sm" href="#"><spring:message code="label.menus.opciones" /> </a>
			<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"><span class="caret"></span></a>
			<ul class="dropdown-menu">
				<li><a id="editarMediosCentroTrabajo">Editar</a></li>
			</ul>
		</div>
		-->
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
