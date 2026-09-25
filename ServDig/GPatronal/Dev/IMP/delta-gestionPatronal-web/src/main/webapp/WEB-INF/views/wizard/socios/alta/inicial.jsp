<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="origenINTERNET" value="<%=OrigenSolicitudEnum.INTERNET.getId()%>" />

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/wizard/socios/alta/inicial.js" htmlEscape="true" />"></script>

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="introduccion col-sm-4">
			<div class="titulo separadorseccion">
				<span><spring:message code="label.portlet.titulo.socios.registrarSocios" /></span>
			</div>
			<div class="descripcion">
				<p>En esta opci&oacute;n podr&aacute;s solicitar alta de socios.</p>
			</div>
			<div class="opciones">
			
				<div style="max-width: 400px;">
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
									<button class="btn btn-warning btn-block"
										role="button" aria-disabled="false" id="btnCancelarTramite">
										<span class="ui-button-text"><spring:message code="label.boton.solicitud.cancelar.solicitud" /></span>
									</button>
								</c:otherwise>
							</c:choose>
						</c:if>						
					</c:if>
					<button
						class="btn btn-default btn-block"
						role="button" aria-disabled="false" id="btnInicioCancelarTramite">
						<span class="ui-button-text"><spring:message code="label.boton.solicitud.cancelar" /></span>
					</button>
				</div>
				
				
			</div>
		</div>

		<div class="instrucciones col-sm-8">
			<h3>Instrucciones:</h3>
			<c:if test="${empty error}">
				<c:if test="${!existeSolProceso}">
						<c:choose>
							<c:when test="${!existeSolRegistrada}">
								<!-- INICIAR SOLICITUD -->								
								<ul>
									
									<li>
										
										<c:if test="${origenApp eq origenINTERNET}">
											<p>
												Para iniciar la solicitud se requiere cuentes con tu RFC y archivos de tu FIEL.
											</p>
										</c:if>
										<p>
											1.	Selecciona la opci&oacute;n iniciar solicitud y capture los datos solicitados.<br>
											2.	Los campos marcados con un asterisco (*) son datos obligatorios.<br>
										</p>
									</li>
								</ul>							
							</c:when>
							<c:otherwise>
									<!-- SOLICITUD REGISTRADA -->
									<div class="alert alert-info">
										Usted ya cuenta con una solicitud para <strong>ALTA DE SOCIO</strong> en proceso.<br>
										Folio de solicitud: <strong>${solicitudForm.noFolioSolicitud}</strong><br>
										Origen de solicitud: <strong>${descripcionOrigenSolicitud}</strong><br>
									</div>
									<c:if test="${solicitudMismoOrigen}">
										<!-- SOLICITUD REGISTRADA (MISMO ORIGEN - mostrar pasos tramite) -->										
										<ul>
											
											<li>
												
												<c:if test="${origenApp eq origenINTERNET}">
													<p>
														Para iniciar la solicitud se requiere cuentes con tu RFC y archivos de tu FIEL.
													</p>
												</c:if>
												<p>
													1.	Selecciona la opci&oacute;n retomar solicitud y capture los datos solicitados.<br>
													2.	Los campos marcados con un asterisco (*) son datos obligatorios.<br>
												</p>
											</li>
										</ul>
									</c:if>																
							</c:otherwise>
						</c:choose>
				</c:if>	
				<c:if test="${existeSolProceso}">						
						<!-- SOLICITUD EN PROCESO -->
						<div class="alert alert-warning">
							Existe un tr&aacute;mite de <strong>ALTA DE SOCIO</strong> en curso, espere a su
							conclusi&oacute;n para iniciar un nuevo tr&aacute;mite.<br>
							Folio de solicitud: <strong>${solicitudForm.noFolioSolicitud}</strong><br>
							Origen de solicitud: <strong>${descripcionOrigenSolicitud}</strong><br>
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
	<div id="pie" class="row"> </div>
</div>

<form:form modelAttribute="socio" id="socio" method="post" action="">
	<form:hidden path="rfc" id="rfc" />
	<form:hidden path="curp" id="curp" />
	<form:hidden path="idPersonaMoralPatron" id="idPersonaMoralPatron" />
	<form:hidden path="rfcPersonaMoralPatron" id="rfcPersonaMoralPatron" />
</form:form>

<!-- Forma para invocar el retomar o cancelar una solicitud -->
<form:form action="" modelAttribute="solicitudForm" id="solicitudForm"	method="post">
	<form:hidden path="solicitudId" id="idSolicitudPendiente" />
	<form:hidden path="noFolioSolicitud" id="noFolioSolicitud" />
</form:form>

		<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
	<p><span class="ui-icon ui-icon-alert"
		style="float: left; margin: 0 7px 20px 0;"></span> ¿Desea cancelar la
		solicitud pendiente con folio: <strong>${solicitudForm.noFolioSolicitud}</strong>?</p>
</div>
		
<div id="dialog-confirm-common" title="Mensaje confirmaci&oacute;n">
	<p><span class="ui-icon ui-icon-alert"
		style="float: left; margin: 0 7px 20px 0;"></span> <label
		id="mensajeDialogo"></label></p>
</div>