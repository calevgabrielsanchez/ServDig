<%@ include file="../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum"%>
<c:set var="tiempoEspera"><spring:message code="msg.time.wait" /></c:set>
<c:set var="tiempoIntervaloEspera"><spring:message code="msg.time.interval" /></c:set>



<script type="text/javascript" src=" <spring:url value="/static/resources/js/delta/home.js" htmlEscape="true" />"></script>
<script type="text/javascript" src=" <spring:url value="/static/resources/js/delta/atributosPersonaCtrl.js" htmlEscape="true" />"></script>
<script type="text/javascript" src=" <spring:url value="/static/resources/js/widget/widget.js" htmlEscape="true" />"></script>
<script type="text/javascript" src=" <spring:url value="/static/resources/js/portlet/portlet.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="/gestionSolicitud-web/static/resources/js/delta/firma-digital/FirmaDigital.js"></script>
<script type="text/javascript" src="/gestionDomicilios-web/static/resources/js/delta/domicilios/Domicilio.js"></script>
<script type="text/javascript" src="/gestionDocumentoProbatorio-web/static/resources/js/wizard/CapturaDocumentosProbatoriosWizard.js"></script>
<script type="text/javascript" src="/gestionAsegurados-web-externo/static/resources/js/delta/wizard/busquedaPersona/busquedaPersonaCurpWizard.js"></script>
<script type="text/javascript" src="/wizard-web/static/resources/js/delta/wizard/ProcesandoSolicitudCmp.js"></script>
<script type="text/javascript" src="/portal-web/static/resources/js/delta/CometConector.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/general/solicitud/detalleSolicitud.js"  htmlEscape="true" />"></script>

<c:set var="tipoPersonaFisica"><%=TipoPersonaEnum.FISICA.getId()%></c:set>

<jsp:include page="llenarAtributosPortalDerechohabiente.jsp"></jsp:include>

<script type="text/javascript">
	var tiempoEspera = ${tiempoEspera};	
	var tiempoIntervaloEspera = ${tiempoIntervaloEspera}; 
	var mostrarProcesando = true;
</script>


<div id="homecontenido">
	<c:set var="idPersonaPrincipal" value="${asignacion.idPersona}" scope="session"/>
	<c:set var="muestraDomicilio" value="false" scope="session"/>
	<c:set var="muestraMedios" value="true" scope="session"/>
	<div class="row">
		<div class="contenedor-widget col-md-4">
			<input id="hdnIdPersona" type="hidden" value="${persona.idPersona}"/>
			<input id="hdnCurpPersonaDerechohabiente" type="hidden" value="${curpPersonaDerechohabiente}"/>
			<input id="hdnIdAsignacionNss" type="hidden" value="${asignacion.idAsignacionNSS}"/>
			<input id="hdnNss" type="hidden" value="${asignacion.nss}"/>
			<input id="hdnIdEstadoDerechohabiente" type="hidden" value="${estadoDerechohabiente}"/>
			<input id="hdnIdParentesco" type="hidden" value="${parentesco.idParentesco}"/> 

			<div class="widget"
				widget-url="/portalDerechohabiente-web/widget/adscripcionVigencia/${asignacion.nss}/${persona.idPersona}/${asignacion.idAsignacionNSS}"></div>
				
			<div class="widget"
				widget-url="/portalDerechohabiente-web/widget/domicilio/integrante/${asignacion.idAsignacionNSS}/${asignacion.nss}/${persona.idPersona}"></div>
				
			<div class="widget">
				<jsp:include page="../portal/nuevosTramitesWidget.jsp"></jsp:include>
			</div>
		</div>
  
		<div class="contenedor-portlet col-md-8">
			<div class="portlets">
			
					<div class="portlet"
					portlet-url="/portalDerechohabiente-web/portlet/detalle/integrante/${asignacion.nss}/${persona.idPersona}/${asignacion.idAsignacionNSS}/0"></div>
			
					<div class="portlet"
					portlet-url="/portalDerechohabiente-web/portlet/buscar/gruposFamiliares/${asignacion.nss}/${persona.idPersona}"></div>
					
					<div class="portlet"
					portlet-url="/portalDerechohabiente-web/portlet/solicitud/proceso/beneficiario/${asignacion.nss}/${persona.idPersona}"></div>
			</div>
		</div>
	</div>
</div>

<!-- Divs de soporte para abrir los dialogos de las aplicaciones utilitarias -->
<div id="listadoCandidatos"></div>
<div id="wizardBajaDerechohabiente"></div>
<div id="wizardProrrogaDerechohabiente"></div>
<div id="wizardCorreccionDerechohabiente"></div>
<div id="wizardRegistroDerechohabiente"></div>
<div id="divDetalleDerechohabiente"></div>
<div id="divCapturaDocs"></div>
<div id="buscarPersonaPorCurpDiv"></div>
<div id="firmaDigitalComponent"></div>
<div id="doctosRequeridosTramite"></div>
<div id="domiciliosComponent"></div>
<div id="detalleSolicitudComponent"></div>
<div id="wizardDatosActualizacion"></div>
<div id="procesandoSolicitudComponent"></div>


<!-- Elementos de soporte para el control de los Identificadores generales -->
<input type="hidden" id="portalContext" value="${portalContext}" />


<div id="waitingDivCommon" style="display: none;">
	<div style="text-align: center; vertical-align: middle;">
		<img alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
	</div>
</div>