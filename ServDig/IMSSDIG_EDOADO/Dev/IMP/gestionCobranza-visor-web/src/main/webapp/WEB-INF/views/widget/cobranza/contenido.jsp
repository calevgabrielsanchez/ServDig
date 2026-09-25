<!-- JSP Contenido del Widget de Persona Fisica. -->

<%@ include file="../../general/taglibs.jsp"%>
<c:set var="staticResourcesPath"
	value='<%=request.getSession().getServletContext()
					.getInitParameter("STATIC_RESOURCES_PATH")%>' />
<c:set var="staticLogoutPath" value='<%=request.getSession().getServletContext().getInitParameter("STATIC_LOGOUT_PATH")%>' />


<div class="encabezado"></div>

<div class="cuerpo">
	<div class="titulo">
		<span><spring:message code="label.widget.titulo.cobranza.adeudo" />
		</span>
	</div>

	<div class="descripcion">
		<p>
			<spring:message code="label.widget.descripcion.cobranza.adeudo" />
		</p>
	</div>

	<div class="contenido">
		

		<address>
			
			
			<i class="glyphicon glyphicon-tag" style="margin-right: 15px;"></i>   <span style="font-size:12px; ">&#36; 1,098,012.12</span> <br>
			<i class="glyphicon glyphicon-tags" style="margin-right: 15px;"></i>   <span style="font-size:12px; ">&#36; 798,012.12</span> <br>

		</address>
	</div>
</div>

<div class="estado"></div>

<div class="pie">
	<div class="opciones">
		<c:if test="${ empty readOnly}">
			<div class="btn-group">
				<a class="btn btn-primary" href="#">Acciones</a> <a
					class="btn btn-primary dropdown-toggle" data-toggle="dropdown"
					href="#"><span class="caret"></span></a>
				<ul class="dropdown-menu">
					<li><a id="editar">Reporte de Estado de Adeudo IMSS por Situaci&oacute;n</a></li>
					<li><a id="editar">Reporte de Estado de Adeudo RCV por Situaci&oacute;n</a></li>
					<li><a id="editar">Reporte de Estado de Adeudo IMSS por Motivo</a></li>
					<li><a id="editar">Reporte de Estado de Adeudo RCV por Motivo</a></li>
				</ul>
			</div>
		</c:if>
	</div>

	<div class="controles">
		<a class="btn btn-sm widget-tool widget-resize"><i
			class="icon-resize-small"></i></a> <a
			class="btn btn-sm widget-tool widget-move"><i
			class="icon-move handle"></i></a>
	</div>
</div>



