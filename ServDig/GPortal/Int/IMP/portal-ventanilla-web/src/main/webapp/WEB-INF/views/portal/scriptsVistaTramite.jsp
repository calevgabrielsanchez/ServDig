<%@ include file="../general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>

<!-- Scripts comúnes -->
<script src="<spring:url value="/static/resources/js/delta/vistaTramite.js" htmlEscape="true" />"></script>
<script src="/gestionDomicilios-web-ventanilla/static/resources/js/delta/domicilios/Domicilio.js"></script>
<script src="/wizard-web/static/resources/js/delta/wizard/ProcesandoSolicitudCmp.js"></script>
<script src="<spring:url value="/static/resources/js/delta/BusquedaCtrl.js" htmlEscape="true" />"></script>
<script src="<spring:url value="/static/resources/js/delta/DetalleIdentidadCtrl.js" htmlEscape="true" />"></script>
<script src="<spring:url value="/static/resources/js/delta/DetalleSujetoCtrl.js" htmlEscape="true" />"></script>
<script src="/gestionIndividuo-consulta-web-ventanilla/static/resources/js/delta/personas/ubicar/UbicarPersonaWidget.js"></script>
<script src="/gestionDocumentoProbatorio-web/static/resources/js/wizard/CapturaDocumentosProbatoriosWizard.js"></script>
<script src="/portal-web/static/resources/js/wizard/general/solicitud/detalleSolicitud.js"></script>

<!-- Script del wizard del trámite principal, la URL viene de base de datos -->
<script src="${tramiteInfo.urlWizard}"></script>

<!-- Serie de validaciones para sólo cargar los scripts necesarios por trámite -->
<c:set var="ALTA_SRT" value="<%=TipoTramiteEnum.ALTA_SRT.getCodigo()%>" scope="page"/>
<c:set var="ALTA_SRT_PM" value="<%=TipoTramiteEnum.ALTA_SRT_PM.getCodigo()%>" scope="page"/>
<c:set var="ACTUALIZACION_SOCIO" value="<%=TipoTramiteEnum.ACTUALIZACION_SOCIO.getCodigo()%>" scope="page"/>
<c:set var="BAJA_SOCIO" value="<%=TipoTramiteEnum.BAJA_SOCIO.getCodigo()%>" scope="page"/>
<c:set var="ACTUALIZACION_REPRESENTANTE_LEGAL" value="<%=TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo()%>" scope="page"/>
<c:set var="BAJA_REPRESENTANTE_LEGAL" value="<%=TipoTramiteEnum.BAJA_REPRESENTANTE_LEGAL.getCodigo()%>" scope="page"/>
<c:set var="COMPRA_SEGURO_INDIVIDUAL" value="<%=TipoTramiteEnum.COMPRA_SEGURO_INDIVIDUAL.getCodigo()%>" scope="page"/>
<c:set var="COMPRA_SEGURO_DOMESTICO" value="<%=TipoTramiteEnum.COMPRA_SEGURO_DOMESTICO.getCodigo()%>" scope="page"/>

<c:choose>
	<c:when test="${tramite eq ALTA_SRT or tramite eq ALTA_SRT_PM}">
		<script src="/delta-gestionPatronal-web-ventanilla/static/resources/js/delta/wizard/commonRepresentantes/representantesLegalesCtrl.js"></script>
		<script src="/delta-gestionPatronal-web-ventanilla/static/resources/js/delta/wizard/agregarRepresentanteLegal/wizarAgregarRLCtrl.js"></script>
		<script src="/delta-gestionPatronal-web-ventanilla/static/resources/js/delta/wizard/socios/alta/wizardAltaSocios.js"></script>
		<script src="/gestionSeguroVoluntario-web-ventanilla/static/resources/js/wizard/seguroDomestico/comunes/wizardDetalleDomesticoCtrl.js"></script>
	</c:when>
	<c:when test="${tramite eq ACTUALIZACION_SOCIO or tramite eq BAJA_SOCIO}">
		<script src="/delta-gestionPatronal-web-ventanilla/static/resources/js/delta/wizard/commonRepresentantes/representantesLegalesCtrl.js"></script>
		<script src="/delta-gestionPatronal-web-ventanilla/static/resources/js/delta/wizard/agregarRepresentanteLegal/wizarAgregarRLCtrl.js"></script>
		<script src="/gestionSeguroVoluntario-web-ventanilla/static/resources/js/wizard/seguroDomestico/comunes/wizardDetalleDomesticoCtrl.js"></script>
	</c:when>
	<c:when test="${tramite eq ACTUALIZACION_REPRESENTANTE_LEGAL or tramite eq BAJA_REPRESENTANTE_LEGAL}">
		<script src="/delta-gestionPatronal-web-ventanilla/static/resources/js/delta/wizard/commonRepresentantes/representantesLegalesCtrl.js"></script>
		<script src="/delta-gestionPatronal-web-ventanilla/static/resources/js/delta/wizard/agregarRepresentanteLegal/wizarAgregarRLCtrl.js"></script>
		<script src="/gestionSeguroVoluntario-web-ventanilla/static/resources/js/wizard/seguroDomestico/comunes/wizardDetalleDomesticoCtrl.js"></script>
	</c:when>
	<c:when test="${tramite eq COMPRA_SEGURO_INDIVIDUAL or tramite eq COMPRA_SEGURO_DOMESTICO}">
		<script src="/delta-gestionPatronal-web-ventanilla/static/resources/js/delta/wizard/commonRepresentantes/representantesLegalesCtrl.js"></script>
		<script src="/gestionSeguroVoluntario-web-ventanilla/static/resources/js/wizard/persona/ivro/detalle/wizar-detalle-seguro.js"></script>
		<script src="/gestionSeguroVoluntario-web-ventanilla/static/resources/js/wizard/seguroDomestico/comunes/wizardDetalleDomesticoCtrl.js"></script>
		<script src="/gestionSeguroVoluntario-web-ventanilla/static/resources/js/wizard/seguroDomestico/renovacion/wizardControl.js"></script>
	</c:when>
</c:choose>


