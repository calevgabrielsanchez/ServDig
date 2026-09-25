<%@ include file="../general/taglibs.jsp"%>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/portlet/solicitudesPortlet.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src=" <spring:url value="/static/resources/js/delta/home.js" htmlEscape="true" />"></script>

<style>
	.portlet {
		border: none;
		box-shadow: none;
	}
</style>

<div class="contenedor">
	<div class="contenedor-portlet">
		<div class="portlets">
			<div class="portlet"
				portlet-url="/gestionSolicitud-visor-web/portlet/solicitudes/${fromRegresar}"
				portlet-name="solicitudes"></div>
		</div>
	</div>
</div>