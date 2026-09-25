<%@ include file="../general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum"%>

<c:set var="tiempoEspera"><spring:message code="msg.time.wait" /></c:set>
<c:set var="tiempoIntervaloEspera"><spring:message code="msg.time.interval" /></c:set>
<c:set var="origenSolicitudInternet" value="<%=OrigenSolicitudEnum.INTERNET.getId()%>" />
<c:set var="tipoPersonaFisica"><%=TipoPersonaEnum.FISICA.getId()%></c:set>

<script type="text/javascript" src=" <spring:url value="/static/resources/js/delta/home.js" htmlEscape="true" />"></script>
<script type="text/javascript" src=" <spring:url value="/static/resources/js/delta/atributosPersonaCtrl.js" htmlEscape="true" />"></script>
<script type="text/javascript" src=" <spring:url value="/static/resources/js/widget/widget.js" htmlEscape="true" />"></script>
<script type="text/javascript" src=" <spring:url value="/static/resources/js/portlet/portlet.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="/gestionSolicitud-web/static/resources/js/delta/firma-digital/FirmaDigital.js"></script>
<script type="text/javascript" src=" <spring:url value="/static/resources/js/delta/portal/personaRepresentadaPortal.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="/gestionDomicilios-web/static/resources/js/delta/domicilios/Domicilio.js"></script>
<script type="text/javascript" src="/wizard-web/static/resources/js/delta/wizard/ProcesandoSolicitudCmp.js"></script>
<script type="text/javascript" src="/portal-web/static/resources/js/delta/CometConector.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/general/solicitud/detalleSolicitud.js"  htmlEscape="true" />"></script>
<script type="text/javascript" src="/gestionCobranza-web/static/resources/js/common/impresionReportesCobranza.js"></script>

<script type="text/javascript" src=" <spring:url value="/static/resources/js/wizard/registroObra/registroObraWizard.js" htmlEscape="true" />"></script>


<jsp:include page="llenarAtributosPortalPersonaRepresentada.jsp"></jsp:include>

<script type="text/javascript">
	var tiempoEspera = ${tiempoEspera};	
	var tiempoIntervaloEspera = ${tiempoIntervaloEspera}; 
</script>


<div id="homecontenido">
	<c:set var="idPersonaPrincipal" value="${idPersonaRepresentada}" scope="session"/>
	<c:set var="idPersonaTercero" value="${usuario.cveIdUsuario}" scope="session"/>
	<c:set var="muestraDomicilio" value="false" scope="session"/>
	<c:set var="muestraMedios" value="false" scope="session"/>
	<div class="row">
		<div class="contenedor-widget col-md-4">
			<input id="hdnIdPersona" type="hidden" value="${idPersona}"/>
			<input id="hdnIdPersonaRepresentada" type="hidden" value="${idPersonaRepresentada}"/>
			<input id="hdnRfcPersonaRep" type="hidden" value = "${rfc}"/>
			<input id="hdnNombrePersonaRepresentada" type="hidden" value='${nombre}'/>			
			<input id="hdnCurpPersonaRepresentada" type="hidden" value="${curpPersona}"/>	
			<%-- <h2>Portal de Empresa</h2>
			<p style="font-size: .9em;">
				<spring:message code="label.portal.informacion.solicitudes" />
			</p> --%>

			<div class="widget"
				widget-url="/portal-web/widget/persona/identidad/${idPersonaRepresentada}/${tipoPersona}/${muestraDomicilio}/${muestraMedios}"></div>

			<div class="widget"
				widget-url="/portal-web/widget/general/fiscales/${idPersonaRepresentada}/${tipoPersona}"></div>
				
			<div class="widget">
				<jsp:include page="../portal/nuevosTramitesWidget.jsp"></jsp:include>
			</div>
		</div>

		<div class="contenedor-portlet col-md-8">
			<div class="portlets">
				<div class="portlet"
					portlet-url="/delta-gestionPatronal-web/portlet/representantes/${idPersonaFM}/${tipoPersona}"></div>

				<div class="portlet"
					portlet-url="/portal-web/portlet/patrones/asociados/persona/${idPersonaRepresentada}/${tipoPersona}"></div>

				<div class="portlet"
					portlet-url="/delta-gestionPatronal-web/portlet/personaAutorizada/init/${idPersonaRepresentada}/${tipoPersona}/${rfc}"></div>

				<c:if test="${not empty idPersonaRepresentada && not empty rfc && tipoPersona eq '2' }">
					<div class="portlet"
						portlet-url="/delta-gestionPatronal-web/portlet/empresas/socios/init/${idPersonaRepresentada}"></div>
				</c:if>	
									
				<div class="portlet"
					portlet-url="/portal-web/portlet/solicitudes/${idPersonaRepresentada}/${tipoPersona}"></div>
					
			</div>
		</div>
	</div>
</div>

<!-- Divs de soporte para abrir los dialogos de las aplicaciones utilitarias -->
<div id="wizardDatosActualizacion"></div>
<div id="wizardRegistroPersonaAutorizada"></div>
<div id="wizardRegistroRepresentado"></div>
<div id="wizardRecuperacionPatron"></div>
<div id="firmaDigitalComponent"></div>
<div id="procesandoSolicitudComponent"></div>
<div id="doctosRequeridosTramite"></div>
<div id="detalleSolicitudComponent"></div>
<div id="wizardModificacionClasificacion"></div>
<div id="wizardModificacionCentroTrabajo"></div>
<div id="wizardImpresionReportesCobranza"></div>
<div id="wizardEstadoAdeudoDiv"></div>
<div id="domiciliosComponent"></div>
<div id="wizardAltaPatronal"></div>
<div id="wizardPortletSocios"></div>
<div id="datosFiscalesComponent"></div>
<div id="wizardRegistroMovimientos"></div>
<div id="wizardObtencionComprobanteFiscal"></div>
<div id="wizardRegistroObra"></div>
<div id="wizardRiesgoTrabajoDiv"></div>
<div id="wizardNMPSDiv"></div>

<!-- Elementos de soporte para el control de los Identificadores generales -->
<input type="hidden" id="portalContext" value="${portalContext}" />
<%-- <input type="hidden" id="idPersonaWidgetCtrl" value="${idPersona}"/> --%>
<input type="hidden" id="idPersonaWidgetCtrl" value="${idPersonaRepresentada}"/>
<input type="hidden" id="idTipoPersonaEmpresa" value="${tipoPersona}"/>
<input type="hidden" id="idPersonaFisicaMoral" value="${idPersonaFM}"/>
<input type="hidden" id="idOrigenSolicitud" value="${origenSolicitudInternet}"/>

<div id="waitingDivCommon" style="display: none;">
	<div style="text-align: center; vertical-align: middle;">
		<img alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
	</div>
</div>