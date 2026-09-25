<%@ include file="../general/taglibs.jsp"%>
<c:set var="tiempoEspera">
	<spring:message code="msg.time.wait" />
</c:set>
<c:set var="tiempoIntervaloEspera">
	<spring:message code="msg.time.interval" />
</c:set>

<script type="text/javascript"
	src=" <spring:url value="/static/resources/js/delta/portal/patronPortal.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src=" <spring:url value="/static/resources/js/delta/home.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src=" <spring:url value="/static/resources/js/delta/atributosPersonaCtrl.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src=" <spring:url value="/static/resources/js/widget/widget.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src=" <spring:url value="/static/resources/js/portlet/portlet.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="/gestionSolicitud-web/static/resources/js/delta/firma-digital/FirmaDigital.js"></script>
<script type="text/javascript"
	src="/gestionDomicilios-web/static/resources/js/delta/domicilios/Domicilio.js"></script>
<script type="text/javascript"
	src="/wizard-web/static/resources/js/delta/wizard/ProcesandoSolicitudCmp.js"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/general/solicitud/detalleSolicitud.js"  htmlEscape="true" />"></script>
<script type="text/javascript"
	src="/portal-web/static/resources/js/delta/CometConector.js"></script>
<script type="text/javascript"
	src=" <spring:url value="/static/resources/js/widget/correccionWidget.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="/portal-web/static/resources/js/widget/riesgoTrabajoWidget.js"></script>
<script type="text/javascript"
	src=" <spring:url value="/static/resources/js/wizard/convenios/wizardConveniosCtrl.js" htmlEscape="true" />"></script>
	

<jsp:include page="llenarAtributosPortalPatronal.jsp"></jsp:include>

<script type="text/javascript">
	var tiempoEspera = $
	{
		tiempoEspera
	};
	var tiempoIntervaloEspera = $
	{
		tiempoIntervaloEspera
	};
	
	/**uso de storage para convenios**/
	sessionStorage.setItem('tokenConvenios', '${tokenConvenios}');
</script>


<div id="homecontenido">
	<c:set var="idPersonaPrincipal" value="${numeroRegistroPatronal}"
		scope="session" />
	<c:set var="idPersonaTercero" value="${usuario.cveIdUsuario}"
		scope="session" />
	<div class="row">
		<div class="contenedor-widget col-md-4">
			<input id="hdnIdPersona" type="hidden" value="${idPersona}" />
			<input id="tokenConvenios" type="hidden" value="${tokenConvenios}" />
			<%-- <h2>Portal Patronal</h2>
			<p style="font-size: .9em;">
				<spring:message code="label.portal.informacion.solicitudes" />
			</p> --%>

			<div class="widget"
				widget-url="/portal-web/widget/general/centroTrabajo/${numeroRegistroPatronal}"></div>

			<div class="widget"
				widget-url="/gestionCobranza-web/widget/cobranza/${numeroRegistroPatronal}"></div>

			<div class="widget"
				widget-url="/gestionCorreccion-widget-web/widget/cobranza/resumen/${numeroRegistroPatronal}"></div>


			<c:if test="${not empty modalidadPatron && modalidadPatron eq '32' }">
				<div class="widget"
					widget-url="/portal-web/widget/asignacion/nss/estudiantes"></div>
			</c:if>

			<c:if
				test="${not empty numeroRegistroPatronal && tipoPersona eq '1' }">
				<div class="widget"
					widget-url="/gestionBeneficio-web/widget/beneficios/patsujoblig/${numeroRegistroPatronal}"></div>
			</c:if>

			<c:if test="${not empty numeroRegistroPatronal}">
				<div class="widget"
					widget-url="/gestionCobranza-web/widget/comprobanteFiscal/${numeroRegistroPatronal}/${rfc}"></div>
			</c:if>
			

			<div class="widget">
				<jsp:include page="../portal/nuevosTramitesWidget.jsp"></jsp:include>
			</div>


		</div>

		<div class="contenedor-portlet col-md-8">
			<div class="portlets">

				<div class="portlet"
					portlet-url="/portal-web/portlet/patrones/clasificacion/${numeroRegistroPatronal}"></div>

				<div class="portlet"
					portlet-url="/portal-web/portlet/solicitudes/registroPatronal/${numeroRegistroPatronal}"></div>

				<div class="portlet"
					portlet-url="/gestionCobranza-web/portlet/edoadeudo/${numeroRegistroPatronal}"></div>

				<div class="portlet"
					portlet-url="/gestionCorreccion-widget-web/widget/cobranza/tramites/${numeroRegistroPatronal}"></div>
			</div>
		</div>
	</div>
</div>

<!-- Elementos de soporte para el control de los Identificadores generales -->
<input type="hidden" id="portalContext" value="${portalContext}" />
<input type="hidden" id="idPersonaWidgetCtrl"
	value="${numeroRegistroPatronal}" />
<input type="hidden" id="nrpCtrl" value="${numeroRegistroPatronal}" />
<input type="hidden" id="modalidadPatron" value="${modalidadPatron}" />
<input type="hidden" id="idTipoRegPatron" value="${idTipoRegPatron}" />

<!-- Divs de soporte para abrir los dialogos de las aplicaciones utilitarias -->
<div id="wizardModificacionClasificacion"></div>
<div id="wizardModificacionCentroTrabajo"></div>
<div id="wizardModificacionContactoCentroTrabajo"></div>
<div id="wizardImpresionReportesCobranza"></div>
<div id="firmaDigitalComponent"></div>
<div id="domiciliosComponent"></div>
<div id="procesandoSolicitudComponent"></div>
<div id="asignacionNSSEstudiantesComponent"></div>
<div id="detalleSolicitudComponent"></div>
<div id="correccionWidget"></div>
<div id="doctosRequeridosTramite"></div>
<div id="wizardAltaPatronal"></div>
<div id="wizardObtencionComprobanteFiscal"></div>
<div id="wizardRiesgoTrabajoDiv"></div>
<div id="wizardNMPSDiv"></div>
<div id="wizardConveniosDiv"></div>
<!--
<div id="mensajeRecibirMail"
	title="Mensaje">
	<jsp:include page="mensajeRecivirCorreoPatron.jsp"></jsp:include>
</div>
-->

<div id="waitingDivCommon" style="display: none;">
	<div style="text-align: center; vertical-align: middle;">
		<img alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
	</div>
</div>