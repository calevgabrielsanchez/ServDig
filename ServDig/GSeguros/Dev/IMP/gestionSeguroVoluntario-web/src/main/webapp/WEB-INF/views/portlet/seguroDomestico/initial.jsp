<!-- JSP Inicial del Portlet de Patrones asociados a la persona. -->
<%@ include file="../../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.PortalContextEnum"%>

 
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/portlet/seguroDomestico/portletSeguroDomestico.js" htmlEscape="true" />"></script>
 

<div class="contenedor" id="portletSeguroDomestico"
	portlet-url="/gestionSeguroVoluntario-web/portlet/seguroDomestico/content/${ivroSolicitante.idPersona}"
	portlet-name="portletSeguroDomestico"
	portlet-id-principal="${idPersonaPrincipal}"
	portlet-id-tercero="${idPersonaTercero}">

	<div class="encabezado"></div>
	<div class="cuerpo">

		<div class="titulo">
			<div class="controles">
				<a class="widget-tool widget-refresh" style="display: none;"><i
					class="icono-refrescar"></i></a> <a
					class="widget-tool widget-move handle"><i class="icono-mover"></i></a>
				<a class="widget-tool widget-resize"> <i class="icono-abrir"></i>
				</a>
			</div>

			<span> 
				<c:choose>
					<c:when test="${portalContext eq 1 }">
						<spring:message
							code="label.portlet.titulo.seguroDomestico.persona" />
					</c:when>
					<c:otherwise>
						<spring:message
							code="label.portlet.titulo.seguroDomestico.tercero" />
					</c:otherwise>
				</c:choose>
			</span>
		</div>

		<div class="descripcion" style="display: none;">
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
					<a class="btn btn-primary btn-sm" id="accionesPatrones" href="#"><spring:message code="label.menus.opciones" /> </a> <a
						class="btn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown"
						href="#"><span class="caret"></span></a>
					<ul id="opcionesPortletIVROSeguroDomestico" class="dropdown-menu pull-right">
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>