<%@ include file="../../../general/taglibs.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/domicilios/wizard/general/funcionesComunes.js" htmlEscape="true" />"></script>
	
<script type="text/javascript" >
	wizardGeneralDomicilios.idPersona = ${idPersona};
	wizardGeneralDomicilios.idTipoTramite = ${idTipoTramite};
</script>	

<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<c:set var="tipoTramiteCreado" value="${fn:toLowerCase(tipoTramiteCreado.descripcion)}"></c:set>
<c:set var="tipoTram" value="${fn:toUpperCase(fn:substring(tipoTramite, 0, 1))}${fn:toLowerCase(fn:substring(tipoTramite, 1,fn:length(tipoTramite)))}"></c:set>
<div class="contenedor col-sm-12">

	<div class="contenido row">

		<div class="introduccion col-sm-4">

			<div class="titulo separadorseccion">
				<span> ${tipoTram} </span>
			</div>

			<div class="descripcion">
				<c:choose>
					<c:when test="${tieneDomicilioParticular}">
						<p>Actualizaci&oacute;n de domicilio</p>
					</c:when>
					<c:otherwise>
						<p>Asignaci&oacute;n de domicilio</p>
					</c:otherwise>
				</c:choose>
			</div>

			<div class="opciones">


				<div style="max-width: 400px;">
					<c:if test="${empty error}">
					
						<c:if test="${!otroTipoSolicitud}">
							<c:if test="${!pendienteAutorizacion}">
								<c:choose>
									<c:when test="${!solicitudRegistrada}">
										<c:choose>
											<c:when test="${asignacionDomicilio}">
												
												<c:if test="${empty KEY_DOMICILIOS_NUEVO}">
	    											<button onclick="uid_call('imss.gestion.domicilios.general.btn_iniciarSolicitud','clickin');"
														class="btn btn-primary btn-block"
														role="button" aria-disabled="false" id="btnInciaTramite">
														<span class="ui-button-text"><spring:message code="label.boton.solicitud.iniciar" /></span>
													</button>
												</c:if>
												
												<c:if test="${!empty KEY_DOMICILIOS_NUEVO}">
													<%-- <button class="ui-button-primary btn-block ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
														role="button" aria-disabled="false" id="btnInciaTramiteCP">
														<span class="ui-button-text"><spring:message code="label.boton.solicitud.iniciar.asignacion.cp" /></span>
													</button>
													<button
														class="ui-button-primary btn-block ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
														role="button" aria-disabled="false"
														id="btnInciaTramiteSinCP">
														<span class="ui-button-text"><spring:message code="label.boton.solicitud.iniciar.asignacion.sincp" /></span>
													</button> --%>
													<button onclick="uid_call('imss.gestion.domicilios.general.btn_iniciarSolicitud','clickin');"
														class="btn btn-primary btn-block"
														role="button" aria-disabled="false" id="btnElegirTipoActualizacion">
														<span class="ui-button-text"><spring:message code="label.boton.solicitud.iniciar" /></span>
													</button>
												</c:if>
												
											</c:when>
											
											<c:when test="${actualizacionDomicilio}">
												<%-- <button
													class="ui-button-primary btn-block ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
													role="button" aria-disabled="false" id="btnInciaTramiteCP">
													<span class="ui-button-text"><spring:message code="label.boton.solicitud.iniciar.cp" /></span>
												</button>
												<button
													class="ui-button-primary btn-block ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
													role="button" aria-disabled="false"
													id="btnInciaTramiteSinCP">
													<span class="ui-button-text"><spring:message code="label.boton.solicitud.iniciar.sincp" /></span>
												</button> --%>
												<button	onclick="uid_call('imss.gestion.domicilios.general.btn_iniciarSolicitud','clickin');"
														class="btn btn-primary btn-block"
														role="button" aria-disabled="false" id="btnElegirTipoActualizacion">
														<span class="ui-button-text"><spring:message code="label.boton.solicitud.iniciar" /></span>
													</button>
											</c:when>
											
											
											<c:when test="${cambioClinica}">
												<c:if test="${empty KEY_DOMICILIOS_NUEVO}">
	    											<button	onclick="uid_call('imss.gestion.domicilios.general.btn_iniciarSolicitud','clickin');"
														class="btn btn-primary btn-block"
														role="button" aria-disabled="false" id="btnInciaTramite">
														<span class="ui-button-text"><spring:message code="label.boton.solicitud.iniciar" /></span>
													</button>
												</c:if>
												
												<c:if test="${!empty KEY_DOMICILIOS_NUEVO}">
													<%-- <button class="ui-button-primary btn-block ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
														role="button" aria-disabled="false" id="btnInciaTramiteCP">
														<span class="ui-button-text"><spring:message code="label.boton.solicitud.iniciar.cp" /></span>
													</button>
													<button
														class="ui-button-primary btn-block ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
														role="button" aria-disabled="false"
														id="btnInciaTramiteSinCP">
														<span class="ui-button-text"><spring:message code="label.boton.solicitud.iniciar.sincp" /></span>
													</button> --%>
													<button
														class="btn btn-primary btn-block" onclick="uid_call('imss.gestion.domicilios.general.btn_iniciarSolicitud','clickin');"
														role="button" aria-disabled="false" id="btnElegirTipoActualizacion">
														<span class="ui-button-text"><spring:message code="label.boton.solicitud.iniciar" /></span>
													</button>
												</c:if>
											</c:when>
											
											
											
										</c:choose>
									</c:when>
									
									<c:otherwise>
										<c:if test="${mismoTramite}">
											<c:if test="${mismoOrigen}">
										
												<button	onclick="uid_call('imss.gestion.domicilios.general.btn_retomarSolicitud','clickin');"
													class="btn btn-primary btn-block"
													role="button" aria-disabled="false" id="btnRetomarTramite">
													<span class="ui-button-text"><spring:message
															code="label.boton.solicitud.retomar" /></span>
												</button>
												<button	onclick="uid_call('imss.gestion.domicilios.general.btn_cancelar','clickin');"
													class="btn btn-danger btn-block"
													role="button" aria-disabled="false" id="btnCancelarTramite">
													<span class="ui-button-text"><spring:message
															code="label.boton.solicitud.cancelar.proceso" /></span>
												</button>
												
											</c:if>
										</c:if>
									</c:otherwise>
								</c:choose>
								
							</c:if><!-- pendienteAutorizacion -->
						</c:if> <!-- otroTipoSolicitud -->
					</c:if>
					<button	onclick="uid_call('imss.gestion.domicilios.general.btn_cancelar','clickin');"
						class="btn btn-default btn-block"
						role="button" aria-disabled="false" id="btnInicioCancelarTramite">
						<span class="ui-button-text"><spring:message
								code="label.boton.solicitud.cancelar" /></span>
					</button>
				</div>

			</div>
		</div>

		<div class="instrucciones col-sm-8" style="width: 65% !important;">
			<c:if test="${empty error}">
			
				<!-- No existe una solicitud de otro tipo activa  -->
				<c:if test="${!otroTipoSolicitud}">
				
					<!-- No existe una solicitud pendiente de autorización  -->
					<c:if test="${!pendienteAutorizacion}">
				
						<c:choose>
						
							<c:when test="${!solicitudRegistrada}">
								<%@ include file="mensajeOpcionesIniciarSolicitud.jsp"%>
							</c:when>
							
							<c:otherwise>
								
								<c:if test="${mismoTramite}">
	
									<c:if test="${mismoOrigen}">
										<div class="alert alert-info">
											Ya cuentas con una solicitud de <strong>${tipoTramiteCreado}</strong>
											en proceso.
										</div>
										<%@ include file="mensajeOpcionesRetomarSolicitud.jsp"%>
									</c:if>
		
									<c:if test="${!mismoOrigen }">
										<div class="alert alert-warning">
											El integrante al que se le quiere aplicar el tr&aacute;mite de
											<strong>${descripcionTipoSolicitud}</strong> ya cuenta con una
											solicitud del mismo tipo por <strong>ventanilla</strong>. Es
											necesario finalizar o cancelar la solicitud donde fue iniciada.
										</div>
									</c:if>
								
								</c:if>
								
								<c:if test="${!mismoTramite}">
									<div class="alert alert-warning">
										El integrante al que se le quiere aplicar el tr&aacute;mite de <strong>${tipoTram}</strong>
										ya cuenta con una solicitud de <strong>${tipoTramiteCreado}</strong>
										en proceso. Es necesario concluir la solicitud activa para poder
										aplicar otro tr&aacute;mite.
									</div>
								</c:if>
							</c:otherwise>
						</c:choose>
					</c:if>
					
					<c:if test="${pendienteAutorizacion}">
						<div class="alert alert-warning">
							El integrante al que se le quiere aplicar el tr&aacute;mite de <strong>${tipoTram}</strong>
							cuenta con una solicitud de <strong>${tipoTramiteCreado}</strong>
							que se esta procesando. Por favor espere su finalizaci&oacute;n.
						</div>
					</c:if>
					
				</c:if>
				<c:if test="${otroTipoSolicitud}">
					<div class="alert alert-warning">
						El integrante al que se le quiere aplicar el tr&aacute;mite de <strong>${tipoTram}</strong>
						ya cuenta con una solicitud de <strong>${tipoTramiteCreado}</strong>
						en proceso. Es necesario concluir la solicitud activa para poder
						aplicar otro tr&aacute;mite.
					</div>
				</c:if>
			
			</c:if>
			<c:if test="${not empty error }">
				<div class="alert alert-danger">
					<button type="button" class="close" data-dismiss="alert">×</button>
					<strong>Error: </strong>${error}
				</div>
			</c:if>
		</div>
	</div>
<div class="pie row">
	<div class="controles"></div>
</div>
</div>

<%@ include file="wizardPie.jsp"%>