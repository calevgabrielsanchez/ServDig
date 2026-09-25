<%@ include file="../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum"%>
<c:set var="tiempoEspera"><spring:message code="msg.time.wait" /></c:set>
<c:set var="tiempoIntervaloEspera"><spring:message code="msg.time.interval" /></c:set>

<script type="text/javascript" src=" <spring:url value="/static/resources/js/delta/home.js" htmlEscape="true" />"></script>
<script type="text/javascript" src=" <spring:url value="/static/resources/js/delta/atributosPersonaCtrl.js" htmlEscape="true" />"></script>
<script type="text/javascript" src=" <spring:url value="/static/resources/js/widget/widget.js" htmlEscape="true" />"></script>
<script type="text/javascript" src=" <spring:url value="/static/resources/js/portlet/portlet.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="/gestionSolicitud-web/static/resources/js/delta/firma-digital/FirmaDigital.js"></script>
<script type="text/javascript" src="/gestionIndividuo-consulta-web/static/resources/js/delta/personas/fisica/identificar/cambios-automaticos/identificar-cambios-automaticos.js"></script>
<script type="text/javascript" src="/gestionDomicilios-web/static/resources/js/delta/domicilios/Domicilio.js"></script>
<script type="text/javascript" src="/gestionDocumentoProbatorio-web/static/resources/js/wizard/CapturaDocumentosProbatoriosWizard.js"></script>
<script type="text/javascript" src="/gestionAsegurados-web-externo/static/resources/js/delta/wizard/busquedaPersona/busquedaPersonaCurpWizard.js"></script>
<script type="text/javascript" src="/wizard-web/static/resources/js/delta/wizard/ProcesandoSolicitudCmp.js"></script>
<script type="text/javascript" src="/portal-web/static/resources/js/delta/CometConector.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/general/solicitud/detalleSolicitud.js"  htmlEscape="true" />"></script>
<script type="text/javascript" src="/portalDerechohabiente-web/static/resources/js/delta/wizard/solicitudPension/solicitudPensionWizard.js"></script>

<c:set var="tipoPersonaFisica"><%=TipoPersonaEnum.FISICA.getId()%></c:set>

<jsp:include page="llenarAtributosPortalAsegurado.jsp"></jsp:include>

<script type="text/javascript">
	var tiempoEspera = ${tiempoEspera};	
	var tiempoIntervaloEspera = ${tiempoIntervaloEspera}; 
	var mostrarProcesando = true;
</script>

<script type="text/javaScript">
	var reporteCartilla = {
		mostrar : function() {
			var nssCifrado = AtributosPersonaCtrl.personaFirmada.nssCifrado;
			var urlFrame = '/gestionAsegurados-web-externo/cartilla/reporte?nss='
				+ nssCifrado;
	
			var div = $('#reimprimirCartilla');
	
			div.dialog({
				title : 'Reimpresi&oacute;n cartilla nacional de salud',
				closeOnEscape : false,
				autoOpen : false,
				width : 900,
				height : 900,
				modal : true,
				resizable : false,
				overlay : {
					opacity : 0.5,
					background : "black"
				},
				close : function(event, ui) {
					// Se destruye el dialogo
					$(this).html('');
					$(this).dialog('destroy').empty();
				}
			});
	
			div.dialog('open');
	
			div.html('<iframe id="reporteCartillaFrame" src="' + urlFrame
					+ '" width="100%" height="100%" frameborder="0"/>');
		}
	};
	
	$("#imprimirCartillagrupo").live('click', function() {
		reporteCartilla.mostrar();
	});

	$("#imprimirCartilla").live('click', function() {
		reporteCartilla.mostrar();
	});
</script>

<div id="homecontenido">
	<c:set var="idPersonaPrincipal" value="${idPersona}" scope="session"/>
	<c:set var="muestraDomicilio" value="false" scope="session"/>
	<c:set var="muestraMedios" value="true" scope="session"/>
	<div class="row">
		<div class="contenedor-widget col-md-4">
			<input id="hdnIdPersona" type="hidden" value="${idPersona}"/>
			<input id="hdnIdAsignacionNss" type="hidden" value="${idAsignacionNss}"/>
			<input id="hdnFechaInicioVigencia" type="hidden" value="<fmt:formatDate  pattern="dd/MM/yyyy"  value="${fechaInicioVigencia}"/>"/>
			<input id="hdnFechaFinVigencia" type="hidden" value="<fmt:formatDate  pattern="dd/MM/yyyy"  value="${fechaFinVigencia}"/>"/>
			<input id="hdnNss" type="hidden" value="${nss}"/>
			<input id="hdnIdEstadoDerechohabiente" type="hidden" value="${estadoDerechohabiente}"/>
			<input id="hdnIdParentesco" type="hidden" value="${idParentesco}"/> 

			<div class="widget"
				widget-url="/portalDerechohabiente-web/widget/adscripcionVigencia/${nss}/${idPersona}/${idAsignacionNss}"></div>
				
			<!--
			<div class="widget"
				widget-url="/portalDerechohabiente-web/widget/servicios/${idAsignacionNss}"></div>-->
			
			<!--  
			<div class="widget"
				widget-url="/portalDerechohabiente-web/widget/domicilio/integrante/${idAsignacionNss}/${nss}/${idPersona}"></div>
				-->		
			<div class="widget">
				<jsp:include page="../portal/nuevosTramitesWidget.jsp"></jsp:include>
			</div>
		</div>
  
		<div class="contenedor-portlet col-md-8">
			<div class="portlets">
			
				<div class="portlet"
					portlet-url="/portalDerechohabiente-web/portlet/detalle/integrante/${nss}/${idPersona}/${idAsignacionNss}/0"></div>
					
				<div class="portlet"
					portlet-url="/portalDerechohabiente-web/portlet/grupoFamiliar/${idAsignacionNss}/1"></div>
					
				<div class="portlet"
					portlet-url="/portalDerechohabiente-web/portlet/buscar/gruposFamiliares/${nss}/${idPersona}"></div>
					
				<div class="portlet"
					portlet-url="/portalDerechohabiente-web/portlet/datosPatron/${idAsignacionNss}/${nss}"></div>
					
				<div class="portlet"
					portlet-url="/portalDerechohabiente-web/portlet/solicitud/proceso/asegurado/${nss}"></div>
				<%-- 
				<div class="portlet"
					portlet-url="/portalDerechohabiente-web/portlet/pensiones"></div>
					--%>
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
<div id="dialogICACorreccionDatosWeb1"></div>
<div id="procesandoSolicitudComponent"></div>
<div id="wizardSolicitudPension"></div>
<div id="reimprimirCartilla"></div>

<!-- Elementos de soporte para el control de los Identificadores generales -->
<input type="hidden" id="portalContext" value="${portalContext}" />


<div id="waitingDivCommon" style="display: none;">
	<div style="text-align: center; vertical-align: middle;">
		<img alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
	</div>
</div>