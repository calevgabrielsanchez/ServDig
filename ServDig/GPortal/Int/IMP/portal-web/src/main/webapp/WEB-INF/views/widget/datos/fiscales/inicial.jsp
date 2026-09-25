<!-- JSP Inicial del Widget de Persona Fisica. -->
<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/widget/domiciliosFiscalesWidget.js" htmlEscape="true" />"></script>

<div class="contenedor" id="datosFiscales" 
	widget-url="/portal-web/widget/datos/fiscales/resumen/${persona.idPersona}/${persona.tipoPersona.idTipoPersona}"
	widget-name="datosFiscales"
	widget-id-principal="${idPersonaPrincipal}" widget-id-tercero="${idPersonaTercero}" >
	<div style="text-align: center; vertical-align: middle;">
		<img alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
	</div>
</div>