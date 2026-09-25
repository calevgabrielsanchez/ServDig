<!-- JSP Inicial del Portlet de Representados Legales. -->
<%@ include file="../../general/taglibs.jsp"%>

<div class="contenedor" id="idPensionesPortlet"
	portlet-url="http://gestionpension-stage.imss.gob.mx/pensiones-web-externo/pensiones/infoPensiones.do"
	portlet-name="pensionesPortlet">

	<div class="encabezado"></div>
	<div class="cuerpo">

		<div class="titulo">
			
			<span>
				<spring:message code="label.portlet.titulo.pensiones" />
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
				<!--  spring:message code="label.portlet.descripcion.pensiones" /-->
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
			
		</div>
	</div>
</div>
