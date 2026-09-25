<!-- JSP Inicial del Widget Dummy -->
<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/resources/js/delta/widget/dummy/widgetDummy.js" htmlEscape="true" />"></script>

<div class="contenedor" id="widgetDummy" 
	widget-url="/portal-ciudadano-web-externo/widget/dummy/detalle/${idDummy}"
	widget-name="widgetDummy"
	widget-id-principal="${idDummy}" widget-id-tercero="${idDummy}" >
	
	<div class="encabezado"></div>

	<div class="cuerpo">
		<div class="titulo">
			<div class="controles" style="display: inline-block;">
				<a class="widget-tool widget-resize"> <i class="icono-abrir"></i>
				</a>
			</div>
			<span><spring:message code="label.widget.titulo.dummy" /></span>
		</div>

		<div class="descripcion" style="display: none;">
			<p>
				<spring:message code="label.widget.descripcion.dummy" />
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
				<div class="btn-group">
					<a class="btn btn-sm btn-default" href="#"><spring:message
							code="label.menus.opciones" /> </a> <a
						class="btn btn-sm btn-default dropdown-toggle" data-toggle="dropdown"
						href="#"><span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li id="abrirWizardDummyWidget"><a href="#">Editar</a></li>
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
