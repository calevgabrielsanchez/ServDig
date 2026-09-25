<!-- JSP Inicial del Portlet de Consulta de  Persona Fisica. -->
<%@ include file="../../../general/taglibs.jsp" %>




<script type="text/javascript"
	src="<spring:url value="/static/resources/js/portlet/personaFisicaConsultaPortlet.js" htmlEscape="true" />"></script>




<div class="contenedor"  portlet-url="/delta-gestionPatronal-web/portlet/representantes/5/2"
		portlet-id-principal="${idPersonaPrincipal}" portlet-id-tercero="${idPersonaTercero}" >

	<div class="portlet-loading">
		<img  alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
	</div>
	
	
</div>
