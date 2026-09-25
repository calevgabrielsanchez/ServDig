<%@ include file="../../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum"%>

<c:set var="OrigenSolicitudEnumVentanilla" value="<%=OrigenSolicitudEnum.VENTANILLA%>"/>
<c:set var="OrigenSolicitudEnumInternet" value="<%=OrigenSolicitudEnum.INTERNET%>"/>
<c:set var="OrigenSolicitudEnumPortalCiudadano" value="<%=OrigenSolicitudEnum.PORTAL_CIUDADANO%>"/>

<script type="text/javascript" src="<spring:url value="/resources/js/jquery/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/delta/wizard/cambioClinica/funcionesComunes.js" htmlEscape="true" />"></script>

<div class="contenedor col-sm-12">
	<span id="mensajeCancelarYContinuar" data-message="<spring:message code="CancelarSolicitudMessage" />" ></span>
	<span id="mensajeCerrarWizard" data-message="<spring:message code="CerrarWizardMessage" />" ></span>
	<div class="contenido row">

		<div class="introduccion col-sm-4">

			<div class="titulo separadorseccion">
				<span> Cambio de cl&iacute;nica </span>
			</div>

			<div class="descripcion">
				<p>A trav&eacute;s de este tr&aacute;mite usted podr&aacute;
					actualizar su domicilio y/o cl�nica
				</p>
			</div>

			<div class="opciones">


				<div style="max-width: 400px;">
					
					<button class="btn btn-default btn-block" id="btnInicioCancelarTramiteInternetClinica">
						<span class="ui-button-text"><spring:message code="label.boton.solicitud.cancelar" /></span>
					</button>
					
					<c:if test="${ (solicitud.origenSolicitud.idTipoSolicitud eq OrigenSolicitudEnumInternet.id) || (solicitud.origenSolicitud.idTipoSolicitud eq OrigenSolicitudEnumPortalCiudadano.id)}">
						<button class="btn btn-warning btn-block" id="btnIniciocancelarYContinuar">
							<span class="ui-button-text"><spring:message code="label.boton.solicitud.cancelar.proceso" /></span>
						</button>
					</c:if>
				</div>

			</div>
		</div>

		<div class="instrucciones col-sm-8" >
			<div class="alert alert-info">
				<button type="button" class="close" data-dismiss="alert">�</button>
				<strong>Importante: </strong>
				<c:if test="${solicitud.origenSolicitud.idTipoSolicitud eq OrigenSolicitudEnumVentanilla.id}">
					<spring:message code="SolicitudEnProcesoVentanillaException"  text="default text"/>
				</c:if>
				<c:if test="${(solicitud.origenSolicitud.idTipoSolicitud eq OrigenSolicitudEnumInternet.id) || (solicitud.origenSolicitud.idTipoSolicitud eq OrigenSolicitudEnumPortalCiudadano.id)}">
					<spring:message code="SolicitudEnProcesoInternetException" text="default text1" />
				</c:if>
			</div>
		</div>
			
	</div>

</div>

