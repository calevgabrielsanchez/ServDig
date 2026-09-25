<%@ include file="../../general/taglibs.jsp" %>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/wizard/prorrogaDerechohabiente/inicial.js" htmlEscape="true" />"></script>
<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<div class="contenedor col-sm-12">

	<div class="contenido row">

		<div class="introduccion col-sm-4">

			<div class="titulo separadorseccion">
				<span> ${descripcionTipoSolicitud} </span>
			</div>

			<div class="descripcion">
				<p>A trav&eacute;s de este tr&aacute;mite usted podr&aacute;
					otorgar prorrogas a sus beneficiarios.</p>
			</div>

			<div class="opciones">


				<div style="max-width: 400px;">
					<c:if test="${empty error}">
						<c:if test="${!otroTipoTramite}">
							<c:choose>
								<c:when test="${!solicitudCreada}">
									<button
										class="btn btn-primary btn-block"
										role="button" aria-disabled="false" id="btnInciaTramite">
										<span class="ui-button-text"><spring:message
									code="label.boton.solicitud.iniciar" /></span>
									</button>
								</c:when>
								<c:otherwise>
									<button
										class="btn btn-primary btn-block"
										role="button" aria-disabled="false" id="btnRetomarTramite">
										<span class="ui-button-text"><spring:message
									code="label.boton.solicitud.retomar" /></span>
									</button>
									<button
										class="btn btn-warning btn-block"
										role="button" aria-disabled="false" id="btnCancelarTramite">
										<span class="ui-button-text"><spring:message
									code="label.boton.solicitud.cancelar.proceso" /></span>
									</button>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:if>
					<button
						class="btn btn-default btn-block"
						role="button" aria-disabled="false" id="btnInicioCancelarTramite">
						<span class="ui-button-text"><spring:message
							code="label.boton.solicitud.cancelar" /></span>
					</button>
				</div>

			</div>
		</div>

		<div class="instrucciones col-sm-8">
			<c:if test="${empty error}">
				<c:if test="${!otroTipoTramite}">
					<c:choose>
						<c:when test="${!solicitudCreada}">
							
							<%@ include file="../general/mensajeOpcionesIniciarSolicitud.jsp" %>
						</c:when>
						<c:otherwise>
							<div class="alert alert-info">
								Usted ya cuenta con una solicitud de <strong>${tipoTramiteCreado.descripcion}</strong> en proceso.
							</div>
		
							<%@ include file="../general/mensajeOpcionesRetomarSolicitud.jsp" %>
						</c:otherwise>
					</c:choose>
				</c:if>
				<c:if test="${otroTipoTramite}">
					<div class="alert alert-info">
								El integrante al que se le quiere aplicar el tr&aacute;mite de <strong>${descripcionTipoSolicitud}</strong>
								ya cuenta con una solicitud de <strong>${tipoTramiteCreado.descripcion}</strong> en proceso. Es necesario
								concluir la solicitud activa para poder aplicar otro tr&aacute;mite.
					</div>
				</c:if>
			</c:if>
			<c:if test="${not empty error}">
				<div class="alert alert-danger">
					${error}
				</div>
			</c:if>
			
			<div class="alert alert-info" style="margin-top: 20px;">
				<p><strong>Aviso de privacidad simplificado</strong></p>
				<p>
				La recolecci&oacute;n de datos personales se lleva a cabo a trav&eacute;s de la p&aacute;gina 
				electr&oacute;nica <a href="${mvn.avisos.contexto}/portal-web/portal"
				target="_blank">${mvn.avisos.contexto}/portal-web/portal</a> cuyo administrador y responsable del tratamiento 
				es la Coordinaci&oacute;n de Clasificaci&oacute;n de Empresas y Vigencia de Derechos del Instituto Mexicano del Seguro Social. 
				Los datos personales que se recaban ser&aacute;n utilizados con la finalidad de llevar a cabo el tr&aacute;mite de
				Actualizaci&oacute;n de datos personales de hijo (a) derechohabiente en el IMSS con Homoclave IMSS-02-066-K.
				</p>
				
				<p>
 				Si deseas conocer nuestro aviso de privacidad integral, lo podr&aacute;s consultar en el portal: 
				<a href="${mvn.avisos.contexto}/gestionAsegurados-web-externo/avisoCorreccion.jsp"
				target="_blank">${mvn.avisos.contexto}/gestionAsegurados-web-externo/avisoCorreccion.jsp</a>
				</p>
			</div>
		</div>
	</div>

	<div class="pie row">
		<div class="controles"></div>
	</div>
</div>

<!-- Forma para invocar la baja de derehohabiente -->
<form:form id="formIniciaTramite" method="post"
	action="${contextPath}/wizard/prorroga/iniciarTramite">
</form:form>
<!--  -->

<!-- Forma para invocar el retomar o cancelar una solicitud -->
<form:form action="" modelAttribute="solicitudForm" id="solicitudForm"
	method="post">
	<form:hidden path="solicitudId" id="idSolicitudPendiente" />
	<form:hidden path="noFolioSolicitud" id="noFolioSolicitud" />
</form:form>
<!--  -->

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar"
	title="Confirmar cancelaci&oacute;n de solicitud">
<p><span class="ui-icon ui-icon-alert"
	style="float: left; margin: 0 7px 20px 0;"></span> ¿Desea cancelar la
solicitud pendiente con folio: <strong>${solicitudForm.noFolioSolicitud}</strong>?
</p>
</div>

<div id="dialog-confirm" title="Mensaje confirmaci&oacute;n">
<p><span class="ui-icon ui-icon-alert"
	style="float: left; margin: 0 7px 20px 0;"></span> <label
	id="mensajeDialogo"></label></p>
</div>
<!--  -->