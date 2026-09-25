<!-- JSP Inicial del Widget de Persona Fisica. -->
<%@ include file="../../../general/taglibs.jsp"%>


<div class="contenedor">
	<div class="titulo">
		<span><spring:message
				code="label.portlet.titulo.patrones.riesgos.trabajo" /> </span>
		<div class="controles">
			<a class="widget-tool widget-refresh" style="display: none;"><i
				class="icono-refrescar"></i></a> <a
				class="widget-tool widget-move handle"><i class="icono-mover"></i></a>
			<a class="widget-tool widget-resize"> <i class="icono-abrir"></i>
			</a>
		</div>
	</div>
	<div class="opciones">
		<div class="btn-group">
			<a class="btn btn-primary btn-sm" href="#"><spring:message
					code="label.menus.opciones" /> </a> <a
				class="btn btn-primary btn-sm dropdown-toggle"
				data-toggle="dropdown" href="#"><span class="caret"></span></a>
			<ul class="dropdown-menu">
				<li><a href="#" id="PortletRiesgoTrabajo">Consultar Riesgos
						de Trabajo</a></li>
			</ul>
		</div>
	</div>
</div>
