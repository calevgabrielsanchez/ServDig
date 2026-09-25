<%@ include file="../../../../../layout/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/fisica/modificacion/medios/fiscales/inicial.js" htmlEscape="true" />"></script>

<div class="contenedor">

	<div class="contenido">

		<div class="introduccion">

			<div class="titulo">
				<span> Modificaci&oacute;n de Medios de Contacto Fiscales </span>
			</div>

			<div class="descripcion">
				<p>A trav&eacute;s de este tr&aacute;mite usted podr&aacute;
					modificar sus Medios de Contacto Fiscales.</p>
			</div>

			<div class="opciones">


				<div style="max-width: 400px;">
					<c:choose>
						<c:when test="${empty mdmDatosEntrada.errorFormGeneral }">
							<c:if test="${!existeSolProceso}">
								<c:choose>
									<c:when test="${!existeSolRegistrada}">
										<button
											class="ui-button-primary btn-block ui-button ui-widget ui-state-default  ui-button-text-only"
											role="button" aria-disabled="false" id="btnInciaTramite">
											<span class="ui-button-text">Iniciar Tr&aacute;mite</span>
										</button>
									</c:when>
									<c:otherwise>
										<button
											class="ui-button-primary btn-block ui-button ui-widget ui-state-default  ui-button-text-only"
											role="button" aria-disabled="false" id="btnRetomarTramite">
											<span class="ui-button-text">Retomar Tr&aacute;mite</span>
										</button>
										<button
											class="btn-block ui-button ui-widget ui-state-default  ui-button-text-only"
											role="button" aria-disabled="false" id="btnCancelarTramite">
											<span class="ui-button-text">Cancelar Tr&aacute;mite en
												Proceso</span>
										</button>
									</c:otherwise>
								</c:choose>
							</c:if>
						</c:when>
					</c:choose>

					<button
						class="ui-button btn-block ui-widget ui-state-default  ui-button-text-only"
						role="button" aria-disabled="false" id="btnInicioCancelarTramite">
						<span class="ui-button-text">Cancelar</span>
					</button>
				</div>

			</div>
		</div>

		<div class="instrucciones" style="width: 65% !important;">
			<c:choose>
				<c:when test="${empty mdmDatosEntrada.errorFormGeneral }">
					<h3>Instrucciones :</h3>

					<c:choose>
						<c:when test="${existeSolRegistrada}">
							<div class="alert">
								Usted ya cuenta con una solicitud para <strong>MODIFICACI&Oacute;N
									DE MEDIOS DE CONTACTO PARTICULARES</strong> registrada.
							</div>

							<ul>
								<li>
									<p>Si Ud. realizo una modificación recientemente de su
										información en el SAT o RENAPO</p>
								</li>
								<li>
									<p>Si Ud. realizo una modificación recientemente de su
										información en el SAT o RENAPO</p>
								</li>
							</ul>
						</c:when>
						<c:when test="${existeSolProceso}">
							<div class="alert">
								Usted ya cuenta con una solicitud para <strong>MODIFICACI&Oacute;N
									DE MEDIOS DE CONTACTO PARTICULARES</strong> en proceso.
							</div>

							<ul>
								<li>
									<p>Si Ud. realizo una modificación recientemente de su
										información en el SAT o RENAPO</p>
								</li>
								<li>
									<p>Si Ud. realizo una modificación recientemente de su
										información en el SAT o RENAPO</p>
								</li>
							</ul>
						</c:when>
						<c:otherwise>
							<ul>
								<li>
									<p>Si Ud. realizo una modificación recientemente de su
										información en el SAT o RENAPO</p>
								</li>
								<li>
									<p>Si Ud. realizo una modificación recientemente de su
										información en el SAT o RENAPO</p>
								</li>
							</ul>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<div class="alert">
						<strong>${mdmDatosEntrada.errorFormGeneral }</strong>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

	<div class="pie">
		<div class="controles"></div>
	</div>
</div>

<c:choose>
	<c:when test="${empty mdmDatosEntrada.errorFormGeneral }">
		<!-- Forma para invocar al servicio del Modificación Manual de Datos -->
		<form:form modelAttribute="mdmDatosEntrada" id="mdmForm" method="post"
			action="/gestionIndividuo-consulta-web/wizard/tramite/modificar/medios/fiscales/crear/solicitud">
		
			<form:hidden path="personaFisica.idPersona" id="busquedaIdPersona" />
			<form:hidden path="personaFisica.cveFisica" id="cveFisicaMediosFiscales" />
			<form:hidden path="indCapturaMediosContactoParticular"
				id="indCapturaMediosContactoParticular" />
		
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
			<p>
				<span class="ui-icon ui-icon-alert"
					style="float: left; margin: 0 7px 20px 0;"></span> ¿Desea cancelar la
				solicitud pendiente con folio: <strong>${solicitudForm.noFolioSolicitud}</strong>?
			</p>
		</div>
		
		<div id="dialog-confirm" title="Mensaje confirmaci&oacute;n">
			<p>
				<span class="ui-icon ui-icon-alert"
					style="float: left; margin: 0 7px 20px 0;"></span> <label
					id="mensajeDialogo"></label>
			</p>
		</div>
		<!--  -->
	</c:when>
</c:choose>