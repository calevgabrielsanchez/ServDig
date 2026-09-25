<!-- JSP Inicial del Widget de Persona Fisica. -->
<%@ include file="../../general/taglibs.jsp"%>
<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<div class="contenedor" id="idDomicilioGrupoFamiliarWidget"
	widget-name="domicilioGrupoFamiliarWidget"
	widget-url="${contextPath}/widget/domicilio/integrante/detalle/${fisica.idAsignacionNSS}/${fisica.nss}/${fisica.idPersona}"
	widget-id-principal="${idPersonaPrincipal}"
	widget-id-tercero="${idPersonaTercero}">

	<div class="encabezado"></div>

	<div class="cuerpo">
		<div class="titulo">
			<div class="controles" style="display: inline-block;">
				<a class="widget-tool widget-resize"> <i class="icono-abrir"></i>
				</a>
			</div>
			<span><spring:message code="label.widget.titulo.domicilio.grupo.familiar" /></span>
		</div>

		<div class="descripcion" style="display: none;">
			<p>
				<spring:message code="label.widget.descripcion.domicilio.grupo.familiar" />
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
			<!-- 
					<div class="btn-group">
						<a class="btn" href="#"><spring:message
								code="label.menus.opciones" /> </a> <a class="btn dropdown-toggle"
							data-toggle="dropdown" href="#"><span class="caret"></span></a>
						<ul class="dropdown-menu" id="accionesWidgetDomicilioGrupoFamiliar">
						</ul>
					</div>
			  -->
			</div>

			<div class="controles">
				<a class="widget-tool widget-refresh" style="display: none;"><i
					class="icono-refrescar"></i></a> <a
					class="widget-tool widget-move handle"><i class="icono-mover"></i></a>
			</div>
		</div>
	</div>
</div>