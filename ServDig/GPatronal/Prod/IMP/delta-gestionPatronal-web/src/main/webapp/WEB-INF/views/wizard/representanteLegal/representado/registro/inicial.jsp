<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/wizard/representanteLegal/representado/registro/inicial.js" htmlEscape="true" />"></script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="introduccion col-sm-4">

			<div class="titulo separadorseccion">
				<span> Registro de empresa a representar </span>
			</div>

			<div class="descripcion">
				<p>A trav&eacute;s de este tr&aacute;mite podr&aacute;s
					registrarte como representante legal de alguna empresa
				</p>
			</div>

			<div class="opciones">


			
					<c:if test="${empty error}">					
						<c:if test="${!existeSolProceso}">
							<c:choose>
								<c:when test="${!existeSolRegistrada}">
									<button class="btn btn-primary btn-block"
										role="button" aria-disabled="false" id="btnInciaTramite">
										<span class="ui-button-text"><spring:message code="label.boton.solicitud.iniciar" /></span>
									</button>
								</c:when>
								<c:otherwise>
									<c:if test="${solicitudMismoOrigen}">
										<button class="btn btn-primary btn-block"
											role="button" aria-disabled="false" id="btnRetomarTramite">
											<span class="ui-button-text"><spring:message code="label.boton.solicitud.retomar" /></span>
										</button>
									</c:if>
									<button class="btn btn-danger btn-block"
										role="button" aria-disabled="false" id="btnCancelarTramite">
										<span class="ui-button-text"><spring:message code="label.boton.solicitud.cancelar.solicitud" /></span>
									</button>
								</c:otherwise>
							</c:choose>
						</c:if>						
					</c:if>
					
					<button class="btn btn-default btn-block"
						role="button" aria-disabled="false" id="btnInicioCancelarTramite">
						<span class="ui-button-text"><spring:message code="label.boton.solicitud.cancelar" /></span>
					</button>
				
			</div>
		</div>

		<div class="instrucciones col-sm-8">
			<c:if test="${empty error}">			
				<c:if test="${!existeSolProceso}">
						<c:choose>
							<c:when test="${!existeSolRegistrada}">
								<!-- INICIAR SOLICITUD -->								
								<%@ include file="/WEB-INF/views/wizard/general/mensajeOpcionesIniciarSolicitud.jsp" %>								
							</c:when>
							<c:otherwise>
									<!-- SOLICITUD REGISTRADA -->
									<div class="alert alert-info">
										Ya cuentas con una solicitud para <strong>Registro de empresa representada</strong> en proceso.
										Folio de la solicitud: <strong>${folioSolicitud}</strong><br>
										Origen de la solicitud: <strong>${descripcionOrigenSolicitud}</strong><br>
									</div>
									<c:if test="${solicitudMismoOrigen}">
										<!-- SOLICITUD REGISTRADA (MISMO ORIGEN - mostrar pasos tramite) -->										
										<%@ include file="/WEB-INF/views/wizard/general/mensajeOpcionesRetomarSolicitud.jsp" %>
									</c:if>																
							</c:otherwise>
						</c:choose>
				</c:if>	
				<c:if test="${existeSolProceso}">						
						<!-- SOLICITUD EN PROCESO -->
						<div class="alert alert-warning">
							Existe un tr&aacute;mite de <strong>Registro de empresa representada</strong> en curso, espera a su
							conclusi&oacute;n para iniciar un nuevo tr&aacute;mite.<br>
							Folio de la solicitud: <strong>${folioSolicitud}</strong><br>
							Origen de la solicitud: <strong>${descripcionOrigenSolicitud}</strong><br>
						</div>
				</c:if>				
			</c:if>
			
			<c:if test="${not empty error }">
				<div class="alert alert-danger" id="divMsgErrores">${error}</div>
			</c:if>
			
		</div>
	</div>
	
	<div class="pie row">
		<div class="controles"></div>
	</div>
	
</div>

<form:form
	action="${contextpath}/wizard/tramite/representado/registro/iniciarTramite"
	modelAttribute="icaDatosEntrada" id="icaDatosEntradaForm" method="post">

</form:form>

<!-- Forma para invocar el retomar o cancelar una solicitud-->
<form:form action="" modelAttribute="solicitudForm" id="solicitudForm"
	method="post">
	<form:hidden path="solicitudId" id="idSolicitudPendiente" />
	<form:hidden path="noFolioSolicitud" id="noFolioSolicitud" />
</form:form>

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			&iquest;Deseas cancelar la solicitud pendiente con folio: <strong>${folioSolicitud}</strong>?
	</p>
</div>

<div id="dialog-confirm" title="Mensaje confirmaci&oacute;n">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			<label id="mensajeDialogo"></label>
	</p>
</div>