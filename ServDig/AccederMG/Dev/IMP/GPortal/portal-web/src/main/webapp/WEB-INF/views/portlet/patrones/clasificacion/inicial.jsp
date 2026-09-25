<!-- JSP Inicial del Portlet de Clasificacion. -->
<%@ include file="../../../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum"%>

<input type="hidden" id="hdnTipoActualizacionClasif"
	value="<%=TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION.getValor() %>" />

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/portlet/patronClasificacionPortlet.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/portlet/indicadorRPC.js" htmlEscape="true" />"></script>
	
<input type="hidden" id="context_p" value="<%= request.getContextPath()%>"/>
<div class="contenedor" id="listaClasificacion"
	portlet-url="/portal-web/portlet/patrones/clasificacion/detalle/${sujetoObligado.numeroRegistroPatronal}"
	portlet-name="patronesClasificacion"
	portlet-id-principal="${idPersonaPrincipal}"
	portlet-id-tercero="${idPersonaTercero}">

	<div class="encabezado"></div>
	<div class="cuerpo">

		<div class="titulo">
			<span><spring:message
					code="label.portlet.titulo.patrones.clasificacion" /> </span>
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
					code="label.portlet.descripcion.patrones.clasificacion" />
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
				<ul id="opcionesClasificacion" class="dropdown-menu  pull-right">
					<!--<li><a id="modificarClasificacionPatron">Efectuar tr&aacute;mite de Modificaciones en el SRT</a></li>
				--></ul>
			</div>
		</div>
	</div>
</div>

<div id="dialogoMensajes">
	<p><span id="textoMensaje"></span></p>
</div>