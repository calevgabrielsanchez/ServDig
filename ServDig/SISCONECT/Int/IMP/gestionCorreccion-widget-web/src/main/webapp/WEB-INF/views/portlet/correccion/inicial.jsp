<!-- JSP Inicial del Widget de Persona Fisica. -->
<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="${staticResourcesPath}/delta/resources/js/widget/correccionWidget.js"></script>


<div class="contenedor" id="portletCorreccion"  portlet-url="/gestionCorreccion-widget-web/widget/cobranza/tramites/portlet/<%=request.getSession().getAttribute("registroPatronal") %>" 
        portlet-name="portletCorreccion"> 
 	<div class="encabezado"></div>
	<div class="cuerpo">

		<div class="titulo">
			<span>Correcciones patronales </span>
			<div class="controles">
				<a class="widget-tool widget-refresh" style="display: none;"><i class="icono-refrescar"></i></a> 
				<a class="widget-tool widget-move handle"><i class="icono-mover"></i></a>
				<a class="widget-tool widget-resize"> <i class="icono-abrir"></i></a>
			</div>
		</div>

		<div class="descripcion" style="display: none;">
			<p>Cuentas con el siguiente historial de movimientos</p>
		</div>

		<div class="contenido" style="height: 300px; display: none;" already-loaded="false">
			<div style="text-align: center; vertical-align: middle;">
				<img alt="" src="${staticResourcesPath}/delta/resources/imagenes/loading.gif" />
			</div>
		</div>
	</div>

	<div class="estado"></div>
	 <div class="pie"></div> 
</div>