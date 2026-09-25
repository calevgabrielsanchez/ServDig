<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="origenINTERNET" value="<%=OrigenSolicitudEnum.INTERNET.getId()%>" />

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/fisica/actualizacion-datos/inicial.js" htmlEscape="true" />"></script>

<div class="contenedor col-sm-12">

	<div class="contenido row">

		<div class="introduccion col-sm-4">

			<div class="titulo separadorseccion">
				<span><spring:message code="label.wizard.titulo.actualizacion.fisica" /></span>
			</div>

			<div class="descripcion">
				<p>
					&Eacute;ste tr&aacute;mite lo podr&aacute;s realizar cuando modifiques tu nombre,
				 	denominaci&oacute;n o raz&oacute;n social ante el SAT y RENAPO, actualizando 
				 	as&iacute; tus datos personales y fiscales.  
   				</p>
   				<p>
					El sistema obtiene tus datos con la finalidad de identificar si existe alguna actualizaci&oacute;n
					 para en su caso registrarlos en este Instituto.
				</p>
			</div>

			<div class="opciones">

				<div style="max-width: 400px;">
					
					<c:if test="${!existeSolProceso}">
						<c:choose>
							<c:when test="${!existeSolRegistrada}">
								<button
									class="btn btn-primary btn-block"
									role="button" aria-disabled="false" id="btnInciaTramite">
									<span class="ui-button-text">Iniciar solicitud</span>
								</button>
							</c:when>
							<c:otherwise>
								<c:if test="${tramiteIniciadoPorOtraPersona}">
									<c:if test="${solicitudMismoOrigen}">
										<button
											class="btn btn-primary btn-block"
											role="button" aria-disabled="false" id="btnRetomarTramite">
											<span class="ui-button-text">Retomar solicitud</span>
										</button>
									</c:if>									
									<button
										class="btn btn-warning btn-block"
										role="button" aria-disabled="false" id="btnCancelarTramite">
										<span class="ui-button-text">Cancelar solicitud</span>
									</button>
								</c:if>
							</c:otherwise>
						</c:choose>
					
					</c:if>
					<button
						class="btn btn-default btn-block"
						role="button" aria-disabled="false" id="btnInicioCancelarTramite">
						<span class="ui-button-text">Cancelar</span>
					</button>
				</div>

			</div>
		</div>

		<div class="instrucciones col-sm-8">
			<h3>Instrucciones :</h3>

			<c:choose>
				<c:when test="${existeSolRegistrada}">
					<c:if test="${tramiteIniciadoPorOtraPersona}">
						<div class="alert alert-info">
							Usted ya cuenta con una solicitud para <strong>ACTUALIZACI&Oacute;N DE DATOS</strong> registrada iniciada por otra persona. 
							Es necesario finalizar o cancelar esa solicitud para proceder con una nueva.<br>
							Folio de solicitud: <strong>${solicitudForm.noFolioSolicitud}</strong><br>							
						</div>
					</c:if>
					<c:if test="${!tramiteIniciadoPorOtraPersona}">
						<div class="alert alert-info">
							Usted ya cuenta con una solicitud para <strong>actualizaci&oacute;n de datos</strong> registrada.<br>
							Folio de solicitud: <strong>${solicitudForm.noFolioSolicitud}</strong><br>
							Origen de solicitud: <strong>${descripcionOrigenSolicitud}</strong><br>
						</div>
	
						<c:if test="${origenApp eq origenINTERNET}">
							<ul>
								<li>
									<p>Para iniciar la solicitud se requiere cuentes con tu  RFC y archivos de tu FIEL. </p>
								</li>
							</ul>
						</c:if>
												
						<ol>
							<li>Selecciona la opci&oacute;n iniciar solicitud y captura los datos solicitados.</li>
							<li>Los campos marcados con un asterisco (*) son datos obligatorios.</li>
						</ol>
					</c:if>
				</c:when>
				<c:when test="${existeSolProceso}">
					<div class="alert alert-info">
						Usted ya cuenta con una solicitud para <strong>actualizaci&oacute;n de datos</strong> en proceso.<br>
						Folio de solicitud: <strong>${solicitudForm.noFolioSolicitud}</strong><br>
						Origen de solicitud: <strong>${descripcionOrigenSolicitud}</strong><br>
					</div>

					<ul>
						<c:if test="${origenApp eq origenINTERNET}">
							<li>
								<p>Para iniciar la solicitud se requiere cuentes con tu  RFC y archivos de tu FIEL. </p>
							</li>
						</c:if>
					</ul>
					<ol>
						<li>Selecciona la opci&oacute;n iniciar solicitud y captura los datos solicitados.</li>
						<li>Los campos marcados con un asterisco (*) son datos obligatorios.</li>
					</ol>
				</c:when>
				<c:otherwise>
					<ul>
						<c:if test="${origenApp eq origenINTERNET}">
							<li>
								<p>Para iniciar la solicitud se requiere cuentes con tu RFC y archivos de tu FIEL.</p>
							</li>
						</c:if>
					</ul>
					<ol>
						<li>Selecciona la opci&oacute;n iniciar solicitud y captura los datos solicitados.</li>
						<li>Los campos marcados con un asterisco (*) son datos obligatorios.</li>
					</ol>

					<c:if test="${PEDIR_CURP || PEDIR_RFC }">
						<div class="well" style="background-color: white;">
							<div class="alert alert-info">
								<c:choose>
									<c:when test="${PEDIR_CURP && PEDIR_RFC }">
										Para poder realizar este tr&aacute;mte es necesario contar con su CURP y RFC.
									</c:when>
									<c:when test="${PEDIR_CURP && !PEDIR_RFC }">
										Para poder realizar este tr&aacute;mte es necesario contar con su CURP.
									</c:when>
									<c:when test="${!PEDIR_CURP && PEDIR_RFC }">
										Para poder realizar este tr&aacute;mte es necesario contar con su RFC.
									</c:when>
								</c:choose>
							</div>

							<form class="form-horizontal" id="formAuxDatosComp">
								<c:if test="${PEDIR_CURP}">
									<div class="form-group">
										<label class="col-sm-2 control-label" for="curpInputTmp">
											<span class="required">*</span>
											CURP:
										</label>
										<div class="col-sm-10">
											<input id="curpInputTmp" class="alfanumericoEstricto form-control" type="text" name="curpInputTmp"
												class="form-control" style="text-transform: uppercase;" maxlength="18" />
											<span id="curpInputTmpError" class="error hiddenElement"></span>
										</div>
									</div>
								</c:if>
								<c:if test="${PEDIR_RFC }">
									<div class="form-group">
										<label class="col-sm-2 control-label" for="rfcInputTmp">
											<span class="required">*</span>
											RFC:
										</label>
										<div class="col-sm-10">
											<input id="rfcInputTmp" class="alfanumericoEstricto form-control" type="text" name="rfcInputTmp"
												style="text-transform: uppercase;" maxlength="13" />
											<span id="rfcInputTmpError" class="error hiddenElement"></span>
										</div>
									</div>
								</c:if>
							</form>
						</div>
					</c:if>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

	<div class="pie row">
		<div class="controles"></div>
	</div>
</div>

<!-- Forma para invocar al servicio del ICA -->
<form:form
	action="${contextpath}/wizard/tramite/actualizar/datos/fisica/crear/solicitud"
	modelAttribute="icaDatosEntrada" id="icaDatosEntradaForm" method="post">

	<form:hidden path="personaFisica.idPersona" id="idPersona" />
	<form:hidden path="personaFisica.curp" id="curp" />
	<form:hidden path="personaFisica.rfc" id="rfc" />

	<form:hidden path="indicadorConsultaRENAPO" id="consultaRENAPO" />
	<form:hidden path="indicadorConsultaSAT" id="consultaSAT" />
	<form:hidden path="indicadorMostrarPantalla" id="mostrarPantalla" />

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