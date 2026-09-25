<!-- JSP Inicial del Widget de Persona Fisica. -->
<%@ include file="../../general/taglibs.jsp" %>




<script type="text/javascript"
	src="<spring:url value="/static/resources/js/widget/personaFisicaWidget.js" htmlEscape="true" />"></script>

<div class="contenedor" id="cobranza" widget-name="edoAdeudoCobranza" 
	widget-url="/gestionCobranza-visor-web/widget/cobranza/resumen" >
	
	<div style="text-align: center; vertical-align:middle;">
		<img alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
	</div>
</div>