<%@ include file="../general/taglibs.jsp"%>
<%--Script del componente de firma digital para inclustarlo en la misma pantalla --%>
<script type="text/javascript" src="/gestionSolicitud-web/static/resources/js/delta/firma-digital/FirmaDigitalPlugin.js"></script>
<%--script de control de la pantalla de login --%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/altaPatronUX/loginFirma.js" htmlEscape="true" />"></script>

<jsp:include page="altaMoral/encabezadoMoral.jsp"></jsp:include>

<div class="row">
	<div class="col-sm-12">
		<spring:message code="label.tramite.alta.moral.instrucciones.tenermano" />:<br>
		<ul>
			<li><spring:message code="label.requisitos.fiel"/></li>
			<li><spring:message code="label.requisitos.centroTrabajo"/></li>
			<li><spring:message code="label.requisitos.claveFraccion"/><a class="btn btn-xs icono-help" id="idPopoverAsentamientoCP" data-toggle="popover"></a></li>
		</ul>
	</div>
</div>
<div class="row" id="firmaElectronica" style="text-align:center">Espere mientras el componente de firma electr&oacute;nica es cargado...</div>
<div id="contenedorDoctos"></div>