<!-- JSP Contenido del Widget de Persona Fisica. -->
<%@ include file="../../general/taglibs.jsp"%>
<c:set var="staticResourcesPath"
	value='<%=request.getSession().getServletContext()
					.getInitParameter("STATIC_RESOURCES_PATH")%>' />
<c:set var="staticLogoutPath" value='<%=request.getSession().getServletContext().getInitParameter("STATIC_LOGOUT_PATH")%>' />
<script type="text/javascript">
var infoDatosServicios = '<p>En esta secci&oacute;n se muestra los <span style="font-style: italic;">servicios activos</span></p>' +
	'<address>' +
	'</address>';
$('#idPopoverServicios').popover({
	animation : true,
	html: true,
	content : infoDatosServicios,
	trigger: 'hover',
	container : 'body'
});
</script>

	<div class="widget-section">
		<c:if test="${empty error }">
		
		
		<div style="float: right;">
			<a class="btn btn-sm icono-help" id="idPopoverServicios" data-toggle="popover" title="Servicios activos del asegurado"></a>
		</div>
			<address>
					<c:forEach items="${servicios}" var="servicio">
						<span><strong><spring:message code="label.serviciosDescripcion" /> : </strong> ${servicio.servicio} </span><br>
						<span><strong><spring:message code="label.serviciosDerechoPrestacion" /> : </strong> ${servicio.siNo} </span><br>
						<span><strong><spring:message code="label.serviciosRestricciones" /> : </strong> ${servicio.restricciones} </span><br><br>
					</c:forEach>
			</address>
		<div id="divNss" style="display: none;">
			<form id="asignacionNss" name="asignacionNss" method="post" action="/portal-web/portal/asegurado/ingresar">
				<input type="hidden" id="idPersona" name="idPersona" value="${asegurado.derechohabiente.idPersona}">
				<input type="hidden" id="idAsignacionNSS" name="idAsignacionNSS" value ="${asegurado.asignacionNSS.idAsignacionNSS}">
				<input type="hidden" id="nss" name="nss" value = "${asegurado.asignacionNSS.nssStr}">
			</form>
		</div>
		</c:if>
		<c:if test="${not empty error}">
			${error}
		</c:if>
	</div>
	
	