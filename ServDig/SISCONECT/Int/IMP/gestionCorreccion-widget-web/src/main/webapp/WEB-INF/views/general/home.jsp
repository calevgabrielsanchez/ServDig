<%@ include file="../general/taglibs.jsp"%>

<script type="text/javascript" src=" <spring:url value="/static/resources/js/delta/home.js" htmlEscape="true" />"></script>

<div id="homecontenido" class="contenedor">
	<div class="row">
		<div class="contenedor-widget cell">

			<h2><spring:message code="label.portal.titulo.visor.solicitudes" /></h2>

			<p style="font-size: .9em;">
				<spring:message code="label.portal.informacion.visor.solicitudes" />
			</p>
							
		</div>

		<div class="contenedor-portlet cell">

			<div class="portlets">

				<div class="portlet"
					portlet-url="/gestionCorreccion-widget-web/portlet/solicitudes"
					portlet-name="solicitudes"></div>
					
			</div>
		</div>
	</div>
</div>