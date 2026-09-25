<!-- JSP Inicial del Widget de Persona Fisica. -->
<%@ include file="../../../general/taglibs.jsp"%>
<%@ page
	import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum"%>

<input type="hidden" id="hdnTipoActualizacionCentroTrab"
	value="<%=TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO.getValor()%>" />

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/widget/domiciliosCentroTrabajoWidget.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/portlet/indicadorRPC.js" htmlEscape="true" />"></script>
	
<input type="hidden" id="context_p" value="<%= request.getContextPath()%>"/>
<div class="contenedor" id="idCentroTrabajoWidget"
	widget-name="centroTrabajoWidget"
	widget-url="/portal-web/widget/general/centroTrabajo/detalle/${patron.numeroRegistroPatronal}"
	widget-id-principal="${idPersonaPrincipal}"
	widget-id-tercero="${idPersonaTercero}">

	<div class="encabezado"></div>
	<div class="cuerpo">
		<div class="titulo">
			<div class="controles" style="display: inline-block;">
				<a class="widget-tool widget-resize">
					<i class="icono-abrir"></i>
				</a>
			</div>
			<span> <spring:message
					code="label.widget.titulo.patron.centroTrabajo" />
			</span>
		</div>
		<div class="descripcion" style="display: none;">
			<p>
				<spring:message code="label.widget.descripcion.patron.centroTrabajo" />
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
				<div class="btn-group">
					<a class="btn btn-default btn-sm" href="#"><spring:message code="label.menus.opciones" /></a> <a
						class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown"
						href="#"><span class="caret"></span></a>
					<ul class="dropdown-menu" id="accionesWidgetPatronGeneral">
					</ul>
				</div>
			</div>

			<div class="controles">
				<a class="widget-tool widget-refresh"
					style="display: none;"><i class="icono-refrescar"></i></a>
				<a class="widget-tool widget-move handle"><i class="icono-mover"></i></a>
			</div>
		</div>
	</div>

</div>

<div id="dialogoMensajes">
	<p><span id="textoMensaje"></span></p>
</div>
