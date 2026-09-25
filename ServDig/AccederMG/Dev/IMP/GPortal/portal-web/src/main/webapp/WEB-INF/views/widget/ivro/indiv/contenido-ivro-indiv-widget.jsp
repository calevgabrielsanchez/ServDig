<!-- JSP Contenido del Widget de Persona Fisica. -->
<%@ include file="../../../general/taglibs.jsp"%>
<c:set var="staticResourcesPath"
	value='<%=request.getSession().getServletContext()
					.getInitParameter("STATIC_RESOURCES_PATH")%>' />
<c:set var="staticLogoutPath" value='<%=request.getSession().getServletContext().getInitParameter("STATIC_LOGOUT_PATH")%>' />

<!--<script type="text/javaScript">
	$(document).ready(function() {
		
			$.post("/portal-web/utility/menu/opciones/10/${persona.tipoPersona.idTipoPersona}",null,function(data) {
				$("#accionesWidgetIvroIndiv").html(data);				
			});
		
	});
</script>-->


<div class="widget-section">

	<div id="tblSegurosWrapper" style="width: 100%; margin: 0 auto;">
			<table id="tblIvroSegurosIndivResume" style="width: 100%;"
				class="table table-striped table-bordered" cellpadding="0"
				cellspacing="0" border="0">
				<thead>
					<tr>
						<th>Fecha Inicio</th>
						<th>Fecha Fin</th>
						<th>Estatus</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${seguros}" var="seg">
						<tr>							
							<td><fmt:formatDate pattern="dd/MM/yyyy" value="${seg.fechaInicio}"/></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy" value="${seg.fechaFin}"/></td>
							<td>${seg.estadoSeguro.descripcion}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		<input type="hidden" id="rfcPerson" value="${rfc}" />
		</div>
</div>

<br />
<div class="widget-section">

	<div class="opciones">

		<div class="btn-group">
			<a class="btn btn-default btn-sm" href="#"><spring:message
					code="label.menus.opciones" /> </a> <a
				class="btn btn-default btn-sm dropdown-toggle"
				data-toggle="dropdown" href="#"><span class="caret"></span></a>
			<ul class="dropdown-menu" id="accionesWidgetIvroIndiv">
				<c:if test="${compra}">
					<li><a id="editarDomicilio"> Comprar </a></li>
				</c:if>

			</ul>
		</div>

	</div>
</div>


