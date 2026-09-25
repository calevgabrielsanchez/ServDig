<!-- JSP Inicial del Widget de Persona Fisica. -->
<%@ include file="../../general/taglibs.jsp"%>
<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<jsp:include page="../../common/llenaTipoTramite.jsp"></jsp:include>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/widget/adscripcionYVigencia/adscripcionVigenciaWidget.js" htmlEscape="true" />"></script>
	
<div class="contenedor" id="idAdscripcionVigenciaWidget"
	widget-name="adscripcionVigenciaWidget"
	widget-url="${contextPath}/widget/adscripcionVigencia/detalle/${asignacion.nss}/${asignacion.idPersona}/${asignacion.idAsignacionNSS}"
	widget-id-principal="${idPersonaPrincipal}"
	widget-id-tercero="${idPersonaTercero}">

	<div class="encabezado"></div>
	<div class="cuerpo">
		<div class="titulo">
			<div class="controles" style="display: inline-block;">
				<a class="widget-tool widget-resize"> <i class="icono-abrir"></i>
				</a>
			</div>
			<span><spring:message code="label.widget.titulo.adscripcion.vigencia" /></span>
		</div>

		<div class="descripcion" style="display: none;">
			<p>
				<spring:message code="label.widget.descripcion.adscripcion.vigencia" />
			</p>
		</div>

		<div class="contenido" style="display: none;" already-loaded="false"
			load-on-startup="false">
			<div style="text-align: center; vertical-align: middle;">
				<img alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
			</div>
		</div>

		<div class="estado"></div>

		<div class="pie">
			<div class="opciones">
				<c:if test="${empty readOnly }">
					<div class="btn-group">
						<a class="btn btn-default btn-sm" href="#"><spring:message
								code="label.menus.opciones" /> </a> <a class="btn btn-default btn-sm dropdown-toggle"
							data-toggle="dropdown" href="#"><span class="caret"></span></a>
						<ul class="dropdown-menu" id="accionesWidgetAdscripcionVigencia">
						</ul>
					</div>
				</c:if>
			</div>

			<div class="controles">
				<a class="widget-tool widget-refresh" style="display: none;"><i
					class="icono-refrescar"></i></a> <a
					class="widget-tool widget-move handle"><i class="icono-mover"></i></a>
			</div>
		</div>
	</div>
	
	<form id="formPortalDerechohabiente">
		<input type="hidden" id="idPersona" name="idPersona" value="">
		<input type="hidden" id="idAsignacionNSS" name="idAsignacionNSS" value ="">
		<input type="hidden" id="nss" name="nss" value = "">
		<input type="hidden" id="nombre" name="nombre" value = "">
		<input type="hidden" id="primerApellido" name="primerApellido" value = "">
		<input type="hidden" id="segundoApellido" name="segundoApellido" value = "">
		<input type="hidden" id="curp" name="curp" value = "">
	</form>
</div>