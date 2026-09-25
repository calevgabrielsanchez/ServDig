<!-- JSP Contenido del Widget de Persona Fisica. -->
<%@ include file="../../general/taglibs.jsp"%>
<c:set var="staticResourcesPath"
	value='<%=request.getSession().getServletContext()
					.getInitParameter("STATIC_RESOURCES_PATH")%>' />
<c:set var="staticLogoutPath" value='<%=request.getSession().getServletContext().getInitParameter("STATIC_LOGOUT_PATH")%>' />
<script type="text/javascript">
var infoDomicilioFiscal = '<p>En esta secci&oacute;n se muestra el <span style="font-style: italic;">Domicilio del Grupo Familiar</span></p>';
$('#idPopoverDomicilio').popover({
	animation : true,
	html: true,
	title : "Domicilio Particular",
	content : infoDomicilioFiscal,
	trigger: 'hover',
	container : 'body'
});
</script>

	<div class="widget-section">
		<c:if test="${empty error }">
			<div style="float: right;">
				<a class="btn btn-sm icono-help" id="idPopoverDomicilio" data-toggle="popover" title="Domicilio del grupo familiar"></a>
			</div>
			<address>
						<strong> Vialidad </strong><br>
						${derechohabiente.domicilio.vialidadPrimaria.nombre}<br>
						<strong> N&uacute;mero interior, N&uacute;mero exterior</strong><br>
						${derechohabiente.domicilio.numExteriorAlf} ${derechohabiente.domicilio.numExterior1},
						${derechohabiente.domicilio.numInteriorAlf} ${derechohabiente.domicilio.numInterior}<br>
						<strong> Asentamiento </strong><br>
						${derechohabiente.domicilio.asentamiento.nombre}<br>
						<strong> Municipio </strong><br>
						${derechohabiente.domicilio.asentamiento.localidad.municipio.nombre}<br>
						<strong> Estado </strong><br>
						${derechohabiente.domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre}<br>
						<strong> C&oacute;digo postal </strong><br>
						C.P. ${derechohabiente.domicilio.asentamiento.codigoPostal.codigoPostal}<br>
			</address>
		</c:if>
		<c:if test="${not empty error}">
			${error}
		</c:if>
	</div>
	
	