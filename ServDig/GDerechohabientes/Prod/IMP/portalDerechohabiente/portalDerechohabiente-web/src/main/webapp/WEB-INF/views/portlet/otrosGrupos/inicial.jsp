<!-- JSP Inicial del Portlet de Representados Legales. -->
<%@ include file="../../general/taglibs.jsp"%>

<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<div class="contenedor" id="idListaOtrosGruposFamiliares"
	portlet-url="${contextPath}/portlet/buscar/gruposFamiliares/detalle/${fisica.nss}/${fisica.idPersona}"
	portlet-name="listaOtrosGruposFamiliares"
	portlet-id-principal="${idPersona}"
	portlet-id-tercero="${idPersonaTercero}">

	<div class="encabezado"></div>
	<div class="cuerpo">

		<div class="titulo">
			<span>
				<spring:message code="label.portlet.titulo.otros.grupoFamiliar" />
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
				<spring:message code="label.portlet.descripcion.otros.grupoFamiliar" />
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
			<!--
			<div class="btn-group">
				<a class="btn btn-primary" href="#"><spring:message code="label.menus.opciones" /> </a> <a
					class="btn btn-primary dropdown-toggle" data-toggle="dropdown"
					href="#"><span class="caret"></span></a>
				<ul id="opcionesDatosPatrones" class="dropdown-menu  pull-right">
					
				</ul>
			</div>
			  -->
		</div>
	</div>
</div>
