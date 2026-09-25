<!-- JSP Inicial del Widget de Persona Fisica. -->
<%@ include file="../../general/taglibs.jsp"%>




<script type="text/javascript"
	src="<spring:url value="/static/resources/js/widget/impresionCobranzaWidget.js" htmlEscape="true" />"></script>

<div class="contenedor" id="cobranza" widget-name="edoAdeudoCobranza"
	widget-url="/gestionCobranza-web/widget/cobranza/resumen/${nrp}"
	widget-id-principal="${idPersonaPrincipal}"
	widget-id-tercero="${idPersonaTercero}">

	<div class="encabezado"></div>

	<div class="cuerpo">
		<div class="titulo">
			<div class="controles" style="display: inline-block;">
				<a class="widget-tool widget-resize"> <i class="icono-abrir"></i>
				</a>
			</div>
			<span><spring:message
					code="label.widget.titulo.cobranza.adeudo" /> </span>
		</div>

		<div class="descripcion" style="display: none;">
			<p>
				<spring:message code="label.widget.descripcion.cobranza.adeudo" />
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
					<a class="btn btn-default btn-sm" href="#">Acciones</a> <a
						class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown"
						href="#"><span class="caret"></span></a>
					<ul class="dropdown-menu" id="opcionesWidgetEdoAdeudo">
					</ul>
				</div>
			</div>
			<div class="controles">
				<a class="widget-tool widget-refresh" style="display: none;"><i
					class="icono-refrescar"></i></a> <a
					class="widget-tool widget-move handle"><i class="icono-mover"></i></a>
			</div>
		</div>
	</div>
</div>