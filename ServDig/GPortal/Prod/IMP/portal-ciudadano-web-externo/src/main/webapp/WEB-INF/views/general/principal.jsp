<%@ include file="../general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.cdsss.delta.portal.utils.PortalCiudadanoEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="tipoPersonaFisica" value="<%=TipoPersonaEnum.FISICA.getId()%>" />
<!-- IDENTIFICADORES ACCESO TRAMITES CIUDADANO -->
<c:set var="idPortalGeneral" value="<%=PortalCiudadanoEnum.PORTAL_CIUDADANO_GENERAL.getId()%>" />
<c:set var="idPortalAltaPatronal" value="<%=PortalCiudadanoEnum.PORTAL_CIUDADANO_ALTA_PATRONAL.getId()%>" />
<c:set var="idPortalIvroIndividual" value="<%=PortalCiudadanoEnum.PORTAL_CIUDADANO_IVRO_INDIVIDUAL.getId()%>" />

<link rel="stylesheet" href="${staticResourcesPath}/estilos/imss/menu-grid.css" />

<script src="${staticResourcesPath}/js/delta/MenuCtrl.js"></script>

<script type="text/javascript" src="/wizard-web/static/resources/js/delta/wizard/ProcesandoSolicitudCmp.js"></script>
<script type="text/javascript"
	src="/gestionDocumentoProbatorio-web/static/resources/js/wizard/CapturaDocumentosProbatoriosWizard.js"></script>
<script type="text/javascript" src="/gestionDomicilios-web-ciudadano/static/resources/js/delta/domicilios/Domicilio.js"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/wizard/cambioClinica/WizardCambioClinicaGeneral.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/wizard/registroDerechohabiente/RegistroDerechohabienteWizard.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/solicitud/detalleSolicitud.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/solicitud/registroConcluido.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="/gestionSeguroVoluntario-web-ciudadano/static/resources/js/wizard/individual/WizardSeguroIvroIndivCtrl.js"></script>
<script type="text/javascript"
	src="/gestionSeguroVoluntario-web-ciudadano/static/resources/js/wizard/seguroDomestico/ventanilla/wizardControl.js"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/home/principal.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/common/cartaTerminosCtrl.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="/gestionIndividuo-consulta-web-ciudadano/static/resources/js/wizard/common/actualizaRfc/actualizaRfcCtrl.js"></script>
<script type="text/javascript"
	src="/gestionBeneficio-web-ciudadano/static/resources/js/delta/wizard/riss/solicitarRifWizard.js"></script>
<script type="text/javascript"
	src="/delta-gestionPatronal-web-ciudadano/static/resources/js/wizard/modificacion/patron/altaPatronal/altaPatronalWizard.js"></script>
<script type="text/javascript"
	src="/gestionSeguroVoluntario-web-ciudadano/static/resources/js/wizard/persona/ivro/detalle/wizar-detalle-seguro.js"></script>
<script type="text/javascript"
	src="/gestionSeguroVoluntario-web-ciudadano/static/resources/js/wizard/seguroDomestico/comunes/wizardDetalleDomesticoCtrl.js"></script>	
<script type="text/javascript" 
	src="/portalDerechohabiente-ciudadano/static/resources/js/delta/wizard/comprobanteVigencia/ComprobanteVigenciaWizard.js"></script>
<script type="text/javascript"
	src="/portalDerechohabiente-ciudadano/static/resources/js/delta/wizard/prorrogaDerechohabiente/ProrrogaDerechohabienteWizard.js"></script>
<script type="text/javascript"
	src="/portalDerechohabiente-ciudadano/static/resources/js/delta/wizard/bajaDerechohabiente/BajaDerechohabienteWizard.js"></script>
<script type="text/javascript"
	src="/gestionSeguroVoluntario-web-ciudadano/static/resources/js/wizard/seguroDomestico/renovacion/wizardControl.js"></script>
<script type="text/javascript"
	src="/gestionSeguroVoluntario-web-ciudadano/static/resources/js/wizard/mod-33/alta/wizardSeguroMod33Ctrl.js"></script>
<script type="text/javascript"
	src="/gestionSeguroVoluntario-web-ciudadano/static/resources/js/wizard/mod-40/alta/wizardSeguroMod40Ctrl.js"></script>

<!--[if lte IE 9]>
<style>
.dashboard {
  transform: none;
  opacity: 1;
  -webkit-transform: none;
  -ms-transform: none;
  -webkit-animation: none;
  animation: none;
}
</style>
<![endif]-->


<style>
	.ui-widget-overlay {
	    position: fixed;
	}
	
	.disabled {
		pointer-events: none;
		opacity: 0.4;
		cursor: default;
	}
	
	#sesion {
		background-color: transparent;
		background-image:
			url("http://desarrollo.imss.gob.mx/portal-ventanilla-web/static/resources/imagenes/logo_d.png");
		background-repeat: no-repeat;
		background-size: contain;
		border: medium none;
		color: black;
		display: inline-block;
		text-indent: 50px;
	}
</style>

<script type="text/javascript">
	var tiempoEspera = '10';	
	var tiempoIntervaloEspera = '10'; 
	var mostrarProcesando = true;
	
	var tipoPersonaFisica = <%=TipoPersonaEnum.FISICA.getId()%>;
	var _idTramiteAltaPatronal = <%=TipoTramiteEnum.ALTA_SRT.getCodigo()%>;
	var _idTramiteRiss = <%=TipoTramiteEnum.ALTA_RIF.getCodigo()%>;
	var _idTramiteIvroDomestico = <%=TipoTramiteEnum.COMPRA_SEGURO_DOMESTICO.getCodigo()%>;
	var _idTramiteIvroIndividual = <%=TipoTramiteEnum.COMPRA_SEGURO_INDIVIDUAL.getCodigo()%>;
</script>

<!-- Contenido -->
<div>
	<div>

		<!-- Seccion de datos de la identidad -->
		<div class="page-header">
			<h2 style="margin: 0px;">
				Tr&aacute;mites b&aacute;sicos en l&iacute;nea.
			</h2>
		</div>
		
		<!-- Seccion timeline de tramites -->
		<div>
			<div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							Bienvenido:
							<c:out value="${sessionScope.ciudadano.nombreCompleto}" />
						</h3>
					</div>
					<div class="panel-body">
						<div>
							<h4>Selecciona un tr&aacute;mite</h4>
						</div>

						<!-- MENU PRINCIPAL DE PORTAL CIUDADANO -->
						<c:choose>							
							<c:when test="${idPortalCiudadano eq  idPortalAltaPatronal}">
								<jsp:include page="opcionAltaPatronal.jsp" />
							</c:when>
							<c:when test="${idPortalCiudadano eq  idPortalIvroIndividual}">
								<jsp:include page="opcionIvroIndividual.jsp" />
							</c:when>
							<c:otherwise>								
								<jsp:include page="opcionesMenuPrincipal.jsp" />
							</c:otherwise>
						</c:choose>

						<div id="menu-wrapper"></div>
					</div>
					</br>
					<div class="panel-footer">
						<div class="row">
							<div class="col-sm-3 "></div>
						</div>
					</div>
					<input type="hidden" id="cveIdPersona" name="cveIdPersona"
						value="<c:out value="${sessionScope.ciudadano.cveIdPersona}"/>" />
					<input type="hidden" id="cveIdAsingacionNSS" name="cveIdAsingacionNSS"
						value="<c:out value="${sessionScope.ciudadano.cveIdAsingacionNSS}"/>" />
					<input type="hidden" id="nss" name="nss" value="<c:out value="${sessionScope.ciudadano.strNss}"/>" />
					<input type="hidden" id="nssCifrado" name="nssCifrado" value="<c:out value="${sessionScope.ciudadano.nssCifrado}"/>" />
					<input type="hidden" id="curp" name="curp" value="<c:out value="${sessionScope.ciudadano.curp}"/>" />
					<input type="hidden" id="correo" name="correo" value="${correo}" />
					<input type="hidden" id="nombre" name="nombre" value="${sessionScope.ciudadano.nombreCompleto}" />
					<input type="hidden" id="rfc" name="rfc" value="${sessionScope.ciudadano.rfc}" />
					<input type="hidden" id="jsonCiudadano" name="jsonCiudadano" value="<c:out value="${jsonCiudadano}" />" />
				</div>
			</div>
		</div>
	</div>
</div>

<div id="divWizardRegistro"></div>
<div id="divComprobanteVigencia"></div>
<div id="divCapturaDocs"></div>
<div id="divMostrarDocumentos"></div>
<div id="domiciliosComponent"></div>
<div id="divWizardClinica"></div>
<div id="divWizardDomicilio"></div>
<div id="divWizardIvroIndividual"></div>
<div id="wizardAltaSeguroVoluntario"></div>
<div id="procesandoSolicitudComponent"></div>
<div id="divComponentCommon"></div>
<div id="wizardDatosActualizacion"></div>
<div id="detalleSolicitudComponent"></div>
<div id="divActualizacionRfcComponent"></div>
<div id="divWizardBeneficiosRiss"></div>
<div id="wizardAltaPatronal"></div>
<div id="wizardDetalleSeguroComponent"></div>
<div id="wizardDetalleSeguroDomesticoComponent"></div>
<div id="divWizardTramiteProrroga"></div>
<div id="divWizardBajaDerechohabiente"></div>
<div id="divWizardSeguroFamiliar"></div>
<div id="divWizardContinuacionVoluntaria"></div>
<div id="dialogoMensajes">
	<p><span id="textoMensaje"></span></p>
</div>

