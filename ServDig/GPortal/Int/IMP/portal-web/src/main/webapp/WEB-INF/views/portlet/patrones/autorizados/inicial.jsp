<!-- JSP Inicial del Portlet de Patrones asociados a la persona. -->
<%@ include file="../../../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.PortalContextEnum"%>

<div class="contenedor" id="listaPatronesAutorizados"
	portlet-url="/portal-web/portlet/patrones/autorizados/persona/resumen/${persona.idPersona}/${persona.tipoPersona.idTipoPersona}"
	portlet-name="patronesAutorizadosPersona"
	portlet-id-principal="${idPersonaPrincipal}"
	portlet-id-tercero="${idPersonaTercero}">

	<div class="encabezado"></div>
	<div class="cuerpo">
		<div class="titulo">
			<span><spring:message
					code="label.portlet.titulo.patrones.asociados.autorizados" /> </span>

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
					code="label.portlet.descripcion.patrones.asociados.autorizados" />
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
			<!--<div class="btn-group">
				<a class="btn btn-primary btn-sm" href="#"><spring:message code="label.menus.opciones" /> </a> <a
					class="btn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown"
					href="#"><span class="caret"></span></a>
				<ul class="dropdown-menu">
					<li><a id="registroAltaPatronal">Alta Patronal</a></li>
				<li class="removable"><a id="registroAltaPatronalMoral">Alta Patronal P.M.</a></li>
			
				</ul>
			</div>-->
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
