<%@ include file="../../general/taglibs.jsp" %>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/wizard/domicilioClinicaDerechohabiente/inicial.js" htmlEscape="true" />"></script>
<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<div class="contenedor">

	<div class="contenido">

		<div class="introduccion">

			<div class="titulo">
				<span> ${descripcionTipoSolicitud} </span>
			</div>

			<div class="descripcion">
				<p>A trav&eacute;s de este tr&aacute;mite usted podr&aacute;
					dar de baja a sus beneficiarios.</p>
			</div>

			<div class="opciones">


				<div style="max-width: 400px;">
					<c:if test="${empty error}">
						<c:if test="${!otroTipoTramite && mismoOrigen}">
							<c:choose>
								<c:when test="${!solicitudCreada}">
									<button
										class="ui-button-primary btn-block ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
										role="button" aria-disabled="false" id="btnInciaTramite">
										<span class="ui-button-text"><spring:message
									code="label.boton.solicitud.iniciar" /></span>
									</button>
								</c:when>
								<c:otherwise>
									<button
										class="ui-button-primary btn-block ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
										role="button" aria-disabled="false" id="btnRetomarTramite">
										<span class="ui-button-text"><spring:message
									code="label.boton.solicitud.retomar" /></span>
									</button>
									<button
										class="btn-block ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
										role="button" aria-disabled="false" id="btnCancelarTramite">
										<span class="ui-button-text"><spring:message
									code="label.boton.solicitud.cancelar.proceso" /></span>
									</button>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:if>
					<button
						class="ui-button btn-block ui-widget ui-state-default ui-corner-all ui-button-text-only"
						role="button" aria-disabled="false" id="btnInicioCancelarTramite">
						<span class="ui-button-text"><spring:message
							code="label.boton.solicitud.cancelar" /></span>
					</button>
				</div>

			</div>
		</div>

		<div class="instrucciones" style="width: 65% !important;">
			<c:if test="${empty error}">
				<c:if test="${!otroTipoTramite}">
					<c:choose>
						<c:when test="${!solicitudCreada}">
							
							<%@ include file="../general/mensajeOpcionesIniciarSolicitud.jsp" %>
						</c:when>
						<c:otherwise>
							<c:if test="${mismoOrigen}">
								<div class="alert alert-warning">
									Usted ya cuenta con una solicitud de <strong>${tipoTramiteCreado.descripcion}</strong> en proceso.
								</div>
			
								<%@ include file="../general/mensajeOpcionesRetomarSolicitud.jsp" %>
							</c:if>
							<c:if test="${!mismoOrigen }">
								<div class="alert alert-warning">
								El integrante al que se le quiere aplicar el tr&aacute;mite de <strong>${descripcionTipoSolicitud}</strong>
								ya cuenta con una solicitud del mismo tipo por <strong>VENTANILLA</strong>. Es necesario finalizar o
								cancelar la solicitud donde fue iniciada.
								</div>
							</c:if>
						</c:otherwise>
					</c:choose>
				</c:if>
				<c:if test="${otroTipoTramite}">
					<div class="alert alert-warning">
								El integrante al que se le quiere aplicar el tr&aacute;mite de <strong>${descripcionTipoSolicitud}</strong>
								ya cuenta con una solicitud de <strong>${tipoTramiteCreado.descripcion}</strong> en proceso. Es necesario
								concluir la solicitud activa para poder aplicar otro tr&aacute;mite.
					</div>
				</c:if>
			</c:if>
			<c:if test="${not empty error }">
				<div class="alert alert-danger">
					Ocurri&oacute; el siguiente error: ${error}
				</div>
			</c:if>
		</div>
	</div>

	<div class="pie">
		<div class="controles"></div>
	</div>
</div>

<!-- Forma para invocar la baja de derehohabiente -->
<form:form id="formIniciaTramite" method="post"
	action="${contextPath}/wizard/domicilio/iniciarTramite">
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