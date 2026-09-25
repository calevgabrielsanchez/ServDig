<%@ include file="../../general/taglibs.jsp" %>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/wizard/bajaDerechohabiente/inicial.js" htmlEscape="true" />"></script>
<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<c:set var="tipoSolicitudBaja" value="${fn:toUpperCase(fn:substring(descripcionTipoSolicitud, 0, 1))}${fn:toLowerCase(fn:substring(descripcionTipoSolicitud, 1,fn:length(descripcionTipoSolicitud)))}"></c:set>
<div class="contenedor col-sm-12">

	<div class="contenido row">

		<div class="introduccion col-sm-4">

			<div class="titulo separadorseccion">
				<span>${tipoSolicitudBaja}</span>
			</div>

			<div class="descripcion">
				<p><spring:message code="label.wizard.baja.descripcion"/></p>
			</div>

			<div class="opciones">


				<div style="max-width: 400px;">
					<c:if test="${empty error}">
						<c:if test="${!otroTipoTramite && mismoOrigen}">
							<c:choose>
								<c:when test="${!solicitudCreada}">
									<button class="btn btn-primary btn-block" role="button" aria-disabled="false" id="btnInciaTramite" onclick="uid_call('imss.derechohabientes.baja.inicio.btn_iniciarTramite','clickin')">
										<span class="ui-button-text"><spring:message code="label.boton.solicitud.iniciar" /></span>
									</button>
								</c:when>
								<c:otherwise>
									<button class="btn btn-primary btn-block" role="button" aria-disabled="false" id="btnRetomarTramite" onclick="uid_call('imss.derechohabientes.baja.inicio.btn_retomar','clickin')">
										<span class="ui-button-text"><spring:message code="label.boton.solicitud.retomar" /></span>
									</button>
									<button class="btn btn-danger btn-block"role="button" aria-disabled="false" id="btnCancelarTramite" onclick="uid_call('imss.derechohabientes.baja.inicio.btn_cancelar','clickin')">
										<span class="ui-button-text"><spring:message code="label.boton.solicitud.cancelar.proceso" /></span>
									</button>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:if>
					<button class="btn btn-default btn-block" role="button" aria-disabled="false" id="btnInicioCancelarTramite" onclick="uid_call('imss.derechohabientes.baja.inicio.btn_salir','clickin')">
						<span class="ui-button-text"><spring:message code="label.boton.solicitud.cancelar" /></span>
					</button>
				</div>

			</div>
		</div>

		<div class="instrucciones col-sm-8">
			<c:if test="${empty error}">
				<c:set var="tipoTramiteCre" value="${fn:toUpperCase(fn:substring(tipoTramiteCreado.descripcion, 0, 1))}${fn:toLowerCase(fn:substring(tipoTramiteCreado.descripcion, 1,fn:length(tipoTramiteCreado.descripcion)))}"></c:set>
									
				<c:if test="${!otroTipoTramite}">
					<c:choose>
						<c:when test="${!solicitudCreada}">
							
							<%@ include file="../general/mensajeOpcionesIniciarSolicitud.jsp" %>
						</c:when>
						<c:otherwise>
							<c:if test="${mismoOrigen}">
								<div class="alert alert-info">
									<spring:message code="label.mensaje.solicitud.enProceso" arguments="${tipoTramiteCre}"/>
								</div>
			
								<%@ include file="../general/mensajeOpcionesRetomarSolicitud.jsp" %>
							</c:if>
							<c:if test="${!mismoOrigen }">
								<div class="alert alert-info">
								<spring:message code="label.mensaje.tramite.existente.beneficiario" arguments="${tipoSolicitudBaja},'VENTANILLA'"/>
								</div>
							</c:if>
						</c:otherwise>
					</c:choose>
				</c:if>
				<c:if test="${otroTipoTramite}">
					<div class="alert alert-info">
						<spring:message code="label.mensaje.tramite.diferente.beneficiario" arguments="${tipoSolicitudBaja},${tipoTramiteCre}"/>
					</div>
				</c:if>
				
				<div class="alert alert-info" style="margin-top: 20px" id="avisoPrivacidad">
					<p><strong>Aviso de privacidad simplificado</strong></p>
					La recolecci&oacute;n de datos personales se lleva a cabo a trav&eacute;s de la p&aacute;gina 
					electr&oacute;nica <a href="${mvn.avisos.contexto}/portal-web/portal"
					target="_blank">${mvn.avisos.contexto}/portal-web/portal</a> cuyo administrador y responsable del tratamiento 
					es la Coordinaci&oacute;n de Clasificaci&oacute;n de Empresas y Vigencia de Derechos del Instituto Mexicano del Seguro Social. 
					Los datos personales que se recaban ser&aacute;n utilizados con la finalidad de llevar a cabo la
					<c:if test="${codigoTipoSolicitud == 27 }">
						Baja de esposa (o) como derechohabiente en el IMSS con Homoclave IMSS-02-066-C.
					</c:if>
					<c:if test="${codigoTipoSolicitud == 26 }">
						Baja de concubina (rio) como derechohabiente en el IMSS con Homoclave IMSS-02-066-F.
					</c:if>
					<c:if test="${codigoTipoSolicitud == 28 }">
						Baja de padre y/o madre como derechohabiente en el IMSS con Homoclave IMSS-02-066-I.
					</c:if>
					<c:if test="${codigoTipoSolicitud == 25 }">
						<c:set var="idParentesco" value="${integranteBajaSession.parentesco.idParentesco}"></c:set>
						<c:if test="${idParentesco == 2 }">
							Baja de hijo (a) como derechohabiente en el IMSS con Homoclave IMSS-02-066-L.
						</c:if>
						<c:if test="${idParentesco == 3 }">
							Baja de esposa (o) como derechohabiente en el IMSS con Homoclave IMSS-02-066-C.
						</c:if>
						<c:if test="${idParentesco == 4 }">
							Baja de concubina (rio) como derechohabiente en el IMSS con Homoclave IMSS-02-066-F.
						</c:if>
						<c:if test="${idParentesco == 1 }">
							Baja de padre y/o madre como derechohabiente en el IMSS con Homoclave IMSS-02-066-I.
						</c:if>
					</c:if>
	 				Si deseas conocer nuestro aviso de privacidad integral, lo podr&aacute;s consultar en el portal: 
					<a href="${mvn.avisos.contexto}/gestionAsegurados-web-externo/avisoBaja.jsp"
					target="_blank">${mvn.avisos.contexto}/gestionAsegurados-web-externo/avisoBaja.jsp</a>
				</div>
			</c:if>
			<c:if test="${not empty error }">
				<div class="alert alert-danger">
					<spring:message code="label.mensaje.error"/>: ${error}
				</div>
			</c:if>
		</div>
	</div>

	<div class="pie row">
		<div class="controles"></div>
	</div>
</div>

<!-- Forma para invocar la baja de derehohabiente -->
<form:form id="formIniciaTramite" method="post"
	action="${contextPath}/wizard/baja/iniciarTramite">
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