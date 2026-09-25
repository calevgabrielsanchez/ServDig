<!-- JSP Contenido del Widget de Persona Fisica. -->

<%@ include file="../../general/taglibs.jsp"%>


<!-- Javascripts -->
<script>
	var context_path = '<%= request.getContextPath()%>';
</script>

<script type="text/javascript" src="${staticResourcesPath}/delta/resources/js/jquery/blockUI/jquery.blockUI.js"></script>
<c:set var="staticResourcesPath" value='<%=request.getSession().getServletContext().getInitParameter("STATIC_RESOURCES_PATH")%>' />
<input type="hidden" id="registropatronal" value='<%=request.getSession().getAttribute("numeroRegistroPatronal").toString()%>'/>

<div class="encabezado"></div>

<div class="cuerpo">
	<div class="titulo">
		<div class="controles" style="display: inline-block;">
			<a class="widget-tool widget-resize"> <i class="icono-abrir"></i></a>
		</div>
		<span><spring:message code="label.widget.titulo.cobranza.adeudo" /></span>
	</div>
		
	<div class="descripcion" style="display: none;">
		<p>
			Correcci&oacute;n en l&iacute;nea
		</p>
	</div>

	<div class="contenido" style="display: none;" already-loaded="false" load-on-startup="false">
		<div style="text-align: center; vertical-align: middle;">
            <spring:message code="label.widget.descripcion.cobranza.adeudo" />
            <%=request.getSession().getAttribute("numeroRegistroPatronal").toString()%>
        </div>
	</div>
</div>
<div class="estado"></div>

<div class="pie">
	<div class="opciones">
		<div class="btn-group" id="opcionesCorreccion">
			<a class="btn btn-default btn-sm" href="#">Acciones</a> 
			<a class="btn btn-default btn-sm dropdown-toggle"data-toggle="dropdown" href="#"><span class="caret"></span></a>
			<ul class="dropdown-menu" id="accionesWidgetBeneficios">
				<li><a id="abreCorre">Ingresar a la correcci&oacute;n patronal</a></li>
			</ul>
		</div>
	</div>

	<div class="controles">
		<a class="widget-tool widget-refresh" style="display: none;">
			<i class="icono-refrescar"></i>
		</a> 
		<a class="widget-tool widget-move handle">
			<i class="icono-mover"></i>
		</a>
	</div>
</div>