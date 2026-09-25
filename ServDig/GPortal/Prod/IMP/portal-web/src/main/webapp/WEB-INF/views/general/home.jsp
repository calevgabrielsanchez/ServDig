<%@ include file="../general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum"%>

<c:set var="tiempoEspera"><spring:message code="msg.time.wait" /></c:set>
<c:set var="tiempoIntervaloEspera"><spring:message code="msg.time.interval" /></c:set>
<c:set var="origenSolicitudInternet" value="<%=OrigenSolicitudEnum.INTERNET.getId()%>" />

<script type="text/javascript" src=" <spring:url value="/static/resources/js/delta/home.js" htmlEscape="true" />"></script>
<script type="text/javascript" src=" <spring:url value="/static/resources/js/delta/atributosPersonaCtrl.js" htmlEscape="true" />"></script>
<script type="text/javascript" src=" <spring:url value="/static/resources/js/widget/widget.js" htmlEscape="true" />"></script>
<script type="text/javascript" src=" <spring:url value="/static/resources/js/portlet/portlet.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="/gestionCobranza-web/static/resources/js/common/impresionReportesCobranza.js"></script>
<script type="text/javascript" src="/gestionSolicitud-web/static/resources/js/delta/firma-digital/FirmaDigital.js"></script>
<script type="text/javascript" src="/gestionDomicilios-web/static/resources/js/delta/domicilios/Domicilio.js"></script>
<script type="text/javascript" src="/wizard-web/static/resources/js/delta/wizard/ProcesandoSolicitudCmp.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/general/solicitud/detalleSolicitud.js"  htmlEscape="true" />"></script>
<script type="text/javascript" src="/portal-web/static/resources/js/delta/CometConector.js"></script>
<script type="text/javascript" src="/gestionDocumentoProbatorio-web/static/resources/js/wizard/CapturaDocumentosProbatoriosWizard.js"></script>
<!-- <script type="text/javascript" -->
<!-- 	src="/gestionSeguroVoluntario-web/static/resources/js/wizard/persona/ivro/detalle/wizar-detalle-seguro.js"></script> -->
<script type="text/javascript"
	src="/gestionSeguroVoluntario-web/static/resources/js/wizard/seguroDomestico/comunes/wizardDetalleDomesticoCtrl.js"></script>	

<script type="text/javascript" src=" <spring:url value="/static/resources/js/wizard/registroObra/registroObraWizard.js" htmlEscape="true" />"></script>

<jsp:include page="llenarAtributosPortalPersona.jsp"></jsp:include>

<script type="text/javascript">
	var tiempoEspera = ${tiempoEspera};	
	var tiempoIntervaloEspera = ${tiempoIntervaloEspera}; 
	var mostrarProcesando = true;
</script>

<div id="homecontenido">
	<c:set var="idPersonaPrincipal" value="${usuario.cveIdUsuario}" scope="session"/>
	<c:set var="idPersonaTercero" value="0" scope="session"/>
	<c:set var="muestraDomicilio" value="true" scope="session"/>
	<c:set var="muestraMedios" value="true" scope="session"/>
	<div class="row">
		<div class="contenedor-widget col-md-4">
			<input id="hdnIdPersona" type="hidden" value="${idPersona}"/>
			<input id="hdnIdPersonaRepresentada" type="hidden" value="${idPersona}"/>
			<input id="hdnRfcPersonaRep" type="hidden" value = "${rfcFisica}"/>
			<input id="hdnNombrePersonaRepresentada" type="hidden" value="${usuario.fisica.nombre} ${usuario.fisica.primerApellido} ${usuario.fisica.segundoApellido}"/>			
			<input id="hdnCurpPersonaRepresentada" type="hidden" value="${usuario.fisica.curp}"/>	
			<input id="hdnActualizaRfcUsuario" type="hidden" value="${actualizaRfcUsuario}"/>
			
			<%-- <h2>Portal Personal</h2>
			<p style="font-size: .9em;">
				<spring:message code="label.portal.informacion.solicitudes" />
			</p> --%>

			<div class="widget"
				widget-url="/portal-web/widget/persona/identidad/${idPersona}/1/${muestraDomicilio}/${muestraMedios}"></div>
						 
			<div class="widget"
				widget-url="/portalDerechohabiente-web/widget/vigencia/${idPersona}/1/0/0"></div>
		

			<div class="widget"
				widget-url="/portal-web/widget/general/fiscales/${idPersona}/1"></div>
				
			<div class="widget"
				widget-url="/gestionBeneficio-web/widget/beneficios/persona/${idPersona}"></div>
            
<!--             <div class="widget" -->
<%--                  widget-url="/gestionSeguroVoluntario-web/widget/ivro/individual/${idPersona}/${rfcFisica}"></div> --%>
                 
                 <!--  Widget de Pensiones -->
<!--                  <div class="widget" -->
<!--                  widget-url="/pensiones-web-externo/pensiones/infoPensiones.do"></div> -->
			
<!-- 			<div class="widget" -->
<%-- 				widget-url="/gestionSeguroVoluntario-web-externo/widget/altaSeguroVoluntario/${idPersona}"></div> --%>

			<div class="widget">
				<jsp:include page="../portal/nuevosTramitesWidget.jsp"></jsp:include>
			</div>
				
		</div>

		<div class="contenedor-portlet col-md-8">

			<div class="portlets">
					
				<div class="portlet"
					portlet-url="/portal-web/portlet/patrones/asociados/persona/${idPersona}/1"></div>
					
				<div class="portlet"
					portlet-url="/delta-gestionPatronal-web/portlet/representantes/${idPersonaFisica}/1"></div>
					
				<div class="portlet"
					portlet-url="/delta-gestionPatronal-web/portlet/representante/obtener/representdos/${idPersona}"></div>
					
				<div class="portlet"
					portlet-url="/portal-web/portlet/patrones/autorizados/persona/${idPersona}/1"></div>
								
				<div class="portlet"
					portlet-url="/portal-web/portlet/solicitudes/${idPersona}/1"></div>
					
				<!-- EJEMPLO -->
				<div class="portlet"
					portlet-url="/gestionSeguroVoluntario-web/portlet/seguroDomestico/${idPersona}"></div>
				<!-- EJEMPLO -->
					
					<!--  Portlet de Pensiones -->
<!-- 					<div class="portlet" -->
<!-- 					portlet-url="/pensiones-web-externo/pensiones/iniciar.do"></div> -->
					
			</div>
		</div>
	</div>
</div>

<!-- Divs de soporte para abrir los dialogos de las aplicaciones utilitarias -->
<div id="wizardDatosActualizacion"></div>
<div id="wizardRegistroRepresentado"></div>
<div id="wizardAltaPatronal"></div>
<div id="wizardRecuperacionPatron"></div>
<div id="firmaDigitalComponent"></div>
<div id="domiciliosComponent"></div>
<div id="procesandoSolicitudComponent"></div>
<div id="detalleSolicitudComponent"></div>
<div id="doctosRequeridosTramite"></div>
<div id="wizardModificacionClasificacion"></div>
<div id="wizardModificacionCentroTrabajo"></div>
<div id="wizardModificacionContactoCentroTrabajo"></div>
<div id="wizardImpresionReportesCobranza"></div>
<div id="wizardEstadoAdeudoDiv"></div>
<div id="wizardBeneficioRiss"></div>
<div id="reporteFrame"></div>
<div id="wizardRegistroDerechohabiente"></div>
<div id="divCapturaDocs"></div>
<div id="wizardObtencionComprobanteFiscal"></div>
<div id="wizardRegistroObra"></div>
<div id="wizardAltaSeguroVoluntario"></div>
<div id="datosFiscalesComponent"></div>
<div id="wizardRegistroMovimientos"></div>
<div id="wizardNMPSDivAsegurado"></div>
<div id="wizardNMPSDiv"></div>

<!-- Checar Ivro Modalidad para seguro personal -->
<div id="wizardModalidadIvroPersonalComponent"></div>
<div id="wizardDetalleSeguroComponent"></div>
<div id="wizardDetalleSeguroDomesticoComponent"></div>
<div id="wizardRiesgoTrabajoDiv"></div>

<!-- Elementos de soporte para el control de los Identificadores generales -->
<input type="hidden" id="portalContext" value="${portalContext}" />
<input type="hidden" id="idPersonaWidgetCtrl" value="${idPersona}"/>
<input type="hidden" id="curpPersonaCtrl" value="${curpPersona }"/>
<input type="hidden" id="idPersonaFisicaMoral" value="${idPersonaFisica}"/>
<input type="hidden" id="rfcPersonaCtrl" value = "${rfcFisica}"/>
<input type="hidden" id="idTipoPersonaEmpresa" value="1"/>
<input type="hidden" id="idOrigenSolicitud" value="${origenSolicitudInternet}"/>

<div id="waitingDivCommon" style="display: none;">
	<div style="text-align: center; vertical-align: middle;">
		<img alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
	</div>
</div>