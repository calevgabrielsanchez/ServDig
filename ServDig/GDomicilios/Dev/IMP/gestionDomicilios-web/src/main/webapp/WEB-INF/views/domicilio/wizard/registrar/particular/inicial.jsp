<%@ include file="../../../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/domicilios/wizard/registrar/particular/inicial.js" htmlEscape="true" />"></script>

<div class="contenedor col-sm-12">

	<div class="contenido row">

		<div class="introduccion col-sm-4">

			<div class="titulo separadorseccion">
				<span>Registrar Domicilio Particular </span>
			</div>

			<div class="descripcion">
				<p>A trav&eacute;s de este tr&aacute;mite usted podr&aacute;
					registrar su Domicilio Particular.
				</p>
			</div>

			<div class="opciones">


				<div style="max-width: 400px;">
					<c:if test="${!existeSolProceso}">
						<c:choose>
							<c:when test="${!existeSolRegistrada}">
								<button
									class="btn btn-primary btn-block" onclick="uid_call('imss.gestion.domicilios.registrar.particular.btn_iniciarSolicitud','clickin');"
									role="button" aria-disabled="false" id="btnInciaTramite">
									<span class="ui-button-text">Iniciar Solicitud</span>
								</button>
							</c:when>
							<c:otherwise>
								<button
									class="btn btn-warning btn-block" onclick="uid_call('imss.gestion.domicilios.registrar.particular.btn_retomarSolicitud','clickin');"
									role="button" aria-disabled="false" id="btnRetomarTramite">
									<span class="ui-button-text">Retomar Solicitud</span>
								</button>
								<button
									class="btn btn-default btn-block" onclick="uid_call('imss.gestion.domicilios.registrar.particular.btn_cancelarSolicitud','clickin');"
									role="button" aria-disabled="false" id="btnCancelarTramite">
									<span class="ui-button-text">Cancelar Solicitud en
										Proceso</span>
								</button>
							</c:otherwise>
						</c:choose>
					</c:if>
					<button
						class="btn btn-default btn-block" onclick="uid_call('imss.gestion.domicilios.registrar.particular.btn_cancelar','clickout');"
						role="button" aria-disabled="false" id="btnInicioCancelarTramite">
						<span class="ui-button-text">Cancelar</span>
					</button>
				</div>

			</div>
		</div>

		<div class="instrucciones col-sm-8" style="width: 65% !important;">
			<h3>Instrucciones :</h3>

			<c:choose>
				<c:when test="${existeSolRegistrada}">
					<div class="alert alert-info">
						Usted ya cuenta con una solicitud para
						<strong>REGISTRO DE DOMICILIO PARTICULAR</strong> registrada.
					</div>

					<ul>
						<li>
							<p>Para iniciar la solicitud se requiere cuentes con tu  RFC y archivos de tu FIEL. </p>
						</li>
						<li>
							<p>1.	Selecciona la opci&oacute;n iniciar solicitud y captura los datos solicitados.<br>
							   2.	Los campos marcados con un asterisco (*) son datos obligatorios.</p>
						</li>
					</ul>
				</c:when>
				<c:when test="${existeSolProceso}">
					<div class="alert alert-warning">
						Usted ya cuenta con una solicitud para
						<strong>REGISTRO DE DOMICILIO PARTICULAR</strong> en proceso.
					</div>

					<ul>
						<li>
							<p>Para iniciar la solicitud se requiere cuentes con tu  RFC y archivos de tu FIEL. </p>
						</li>
						<li>
							<p>1.	Selecciona la opci&oacute;n iniciar solicitud y captura los datos solicitados.<br>
							   2.	Los campos marcados con un asterisco (*) son datos obligatorios.</p>
						</li>
					</ul>
				</c:when>
				<c:otherwise>
					<ul>
						<li>
							<p>Para iniciar la solicitud se requiere cuentes con tu  RFC y archivos de tu FIEL. </p>
						</li>
						<li>
							<p>1.	Selecciona la opci&oacute;n iniciar solicitud y captura los datos solicitados.<br>
							   2.	Los campos marcados con un asterisco (*) son datos obligatorios.</p>
						</li>
					</ul>
				</c:otherwise>
			</c:choose>

		</div>
	</div>

	<div class="pie row">
		<div class="controles"></div>
	</div>
</div>

<!-- Forma para invocar al servicio del Modificación Manual de Datos -->
<form:form modelAttribute="mdmDatosEntrada" id="mdmForm" method="post"
	action="${contextpath}/wizard/tramite/registrar/domicilio/particular/crear/solicitud">

	<form:hidden path="personaFisica.idPersona" id="busquedaIdPersona" />
	<form:hidden path="indCapturaDomicilioParticular"
		id="indCapturaDomicilioParticular" />
	<form:hidden path="indAsignacionDomicilio"
		id="indAsignacionDomicilio" />
	<form:hidden path="indActualizacionDomicilioDerechohabiente"
					id="indActualizacionDomicilioDerechohabiente" />
	<form:hidden path="indCambioClinica" id="indCambioClinica" />
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
<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			¿Desea cancelar la solicitud pendiente con folio: <strong>${solicitudForm.noFolioSolicitud}</strong>?
	</p>
</div>

<div id="dialog-confirm" title="Mensaje confirmaci&oacute;n">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			<label id="mensajeDialogo"></label>
	</p>
</div>
<!--  -->