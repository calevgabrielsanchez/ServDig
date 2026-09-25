<!-- JSP Inicial del Portlet de Patrones asociados a la persona. -->
<%@ include file="../../../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.PortalContextEnum"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/portlet/patronAsociadoPortlet.js" htmlEscape="true" />"></script>

<div class="contenedor" id="listaPatronesAsociados"
	portlet-url="/portal-web/portlet/patrones/asociados/persona/resumen/${persona.idPersona}/${persona.tipoPersona.idTipoPersona}"
	portlet-name="patronesAsociadosPersona"
	portlet-id-principal="${idPersonaPrincipal}"
	portlet-id-tercero="${idPersonaTercero}">

	<div class="encabezado"></div>
	<div class="cuerpo">

		<div class="titulo">
			<span> 
				<c:choose>
					<c:when test="${portalContext eq 1 }">
						<spring:message
							code="label.portlet.titulo.patrones.asociados.persona" />
					</c:when>
					<c:otherwise>
						<spring:message
							code="label.portlet.titulo.patrones.asociados.tercero" />
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
					code="label.portlet.descripcion.patrones.asociados.persona" />
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
					<a class="btn btn-primary btn-sm" id="accionesPatrones" href="#"><spring:message code="label.menus.opciones" /> </a> <a
						class="btn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown"
						href="#"><span class="caret"></span></a>
					<ul id="opcionesPortletPatrones" class="dropdown-menu pull-right">
						<!--<li class="removable"><a id="registroAltaPatronal">
							<spring:message code="label.button.inciarRegistroPatronal" />
						</a></li>
						<li class="removable"><a id="registroAltaPatronalMoral">Alta Patronal P.M.</a></li>
						<li><a id="recuperarPatron"> <spring:message code="label.button.recuperarRegistroPatronal" /></a></li>
					--></ul>
				</div>
			</div>
		</div>
		<div id="dialogoMensajes">
			<p><span id="textoMensaje"></span></p>
		</div>
	</div>
</div>

<script type="text/javascript">
var portalContext = $("#portalContext").val();
var mostrarOpcionAltaMoral = portalContext == <%=PortalContextEnum.EMPRESA.getId()%>
if (mostrarOpcionAltaMoral) {
	$("li.removable").remove();
}
</script>
