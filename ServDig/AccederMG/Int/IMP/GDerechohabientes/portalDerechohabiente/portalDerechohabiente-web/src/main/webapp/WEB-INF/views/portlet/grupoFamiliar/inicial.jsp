<!-- JSP Inicial del Portlet de Representados Legales. -->
<%@ include file="../../general/taglibs.jsp"%>

<jsp:include page="../../common/llenaTipoTramite.jsp"></jsp:include>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/portlet/grupoFamiliarPortlet.js" htmlEscape="true" />"></script>
<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<div class="contenedor" id="idListagoGrupoFamiliarPortlet"
	portlet-url="${contextPath}/portlet/grupoFamiliar/detalle/${nss.idAsignacionNSS}/${mostrarOpciones}"
	portlet-name="listadoGrupoFamiliarPortlet"
	portlet-id-principal="${idPersona}"
	portlet-id-tercero="${idPersonaTercero}">

	<div class="encabezado"></div>
	<div class="cuerpo">

		<div class="titulo">
			
			<span>
						<spring:message
					code="label.portlet.titulo.grupoFamiliar" />
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
				<spring:message code="label.portlet.descripcion.grupoFamiliar" />
			</p>
		</div>

		<div class="contenido" style="display: none;" already-loaded="false">
			<div style="text-align: center; vertical-align: middle;">
				<img alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
			</div>
		</div>
	</div>
	<br>

	<div class="estado"></div>

	<div class="pie">
		<div class="opciones">
		
			<c:if test="${mostrarOpciones eq 1}">
			<div class="btn-group">
				<a class="btn btn-primary btn-sm" href="#"><spring:message code="label.menus.opciones" /> </a> <a
					class="btn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown"
					href="#"><span class="caret"></span></a>
				<ul id="opcionesPortletGrupoFamiliar" class="dropdown-menu  pull-right">
					
				</ul>
			</div>
			</c:if>
		</div>
	</div>
</div>
<input type="hidden" id="mostrarOpciones" value="${mostrarOpciones}"/>
