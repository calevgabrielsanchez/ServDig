<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum"%>

<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/date.format.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/wizard/personaAutorizada/registro/contenido.js" htmlEscape="true" />"></script>

<style>
.upperCase {
	text-transform: uppercase;
}
</style>

<div class="contenedor col-sm-12">
	<input type="hidden" id="hdnRfcPersonaSesion" value="${rfcPersonaSesion}" />
	<div class="contenido row">
		<div class="col-sm-12">
			<c:choose>
				<c:when test="${empty error }">

					<script>
						var tipoSolicitud = <%=TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES.getValor()%>;
						
						if(parent.WizardRegistroPersonaAutorizadaCtrl.config.idOrigen == 2){
							var codigoTipoSolicitud = ${codigoTipoSolicitud};
							var descripcionTipoSolicitud = '${descripcionTipoSolicitud}';
							var arrayCodigoTipoTramite = ${codigoTipoTramite};
		
							var datosEntradaFirma = {
								fechaElectronica : '${datosFirmaElectronica.fechaElectronicaFormateada}',
								nombreCompleto : '${datosFirmaElectronica.nombreCompleto}',
								registroPatronal : '${datosFirmaElectronica.registroPatronal}',
								rfc : '${datosFirmaElectronica.rfc}',
								curp : '${datosFirmaElectronica.curp}'
							};
						}
					</script>

					<c:choose>
						<c:when test="${!solicitudCreada}">

							<div class="container-fluid">
								<div>
									<label class="alert alert-info" style="width: 100%;">Para poder realizar el registro de persona
										autorizada es necesario contar con la CURP y el RFC de la persona a registrar.</label>
								</div>

								<form:form modelAttribute="persona" id="formPersona"
									action="${contextPath}/wizard/tramite/personaAutorizada/registro/solicitud/crear">
									<div class="row">
										<div class="col-xs-6">
											<div class="form-group">
												<label for="numEscritura">
													<span class="required">*</span>
													CURP:
												</label>
												<form:input path="curp" maxlength="18" cssClass="alfanumericoEstricto form-control input-sm upperCase" />
												<form:errors path="curp" cssClass="error" />
											</div>
										</div>

										<div class="col-xs-6">
											<div class="form-group">
												<label for="rfc">
													<span class="required">*</span>
													RFC:
												</label>
												<form:input path="rfc" maxlength="13" cssClass="alfanumericoEstricto form-control input-sm upperCase" />
												<form:errors path="rfc" cssClass="error" />
											</div>
										</div>
									</div>
								</form:form>
							</div>

						</c:when>
						<c:otherwise>
							<input type="hidden" id="idSolicitud" value="${solicitud.solicitudId}" />
							<div class="alert alert-success">
								<c:choose>
									<c:when test="${!isRetomar}">
										Su solicitud ha iniciado correctamente y su n&uacute;mero de folio es <strong>${solicitud.noFolioSolicitud}</strong>
									</c:when>
									<c:otherwise>
										El folio de la solicitud que esta retomando es: <strong>${solicitud.noFolioSolicitud}</strong>
									</c:otherwise>
								</c:choose>
								<input type="hidden" id="contenidoFirmar" value="${contenidoFirmar}" />
								<input type="hidden" id="folioSolicitud" value="${solicitud.noFolioSolicitud}" />
							</div>

							<form:form modelAttribute="tramitePersonaAutorizada">
								<fieldset>
									<div id="personaAutorizar">
										<div class="separadorseccion">
											<span> Datos de la Persona Autorizada</span>
										</div>
									</div>
									<div align=class="row">
										<div class="col-xs-4">
											<div class="form-group">
												<label for="nombre">Nombre:</label>
												<form:input path="personaAutorizada.nombre" class="form-control input-sm" disabled="true" />
											</div>
										</div>
										<div class="col-xs-4">
											<div class="form-group">
												<label for="pApellido">Primer Apellido:</label>
												<form:input path="personaAutorizada.primerApellido" class="form-control input-sm" disabled="true" />
											</div>
										</div>
										<div class="col-xs-4">
											<div class="form-group">
												<label for="sApellido">Segundo Apellido:</label>
												<form:input path="personaAutorizada.segundoApellido" class="form-control input-sm" disabled="true" />
											</div>
										</div>
									</div>
									<div align=class="row">
										<div class="col-xs-4">
											<div class="form-group">
												<label for="fecNacimiento">Fecha de Nacimiento:</label>
												<form:input path="personaAutorizada.fechaNacimiento" class="form-control input-sm" disabled="true" />
											</div>
										</div>
										<div class="col-xs-4">
											<div class="form-group">
												<label for="curp">CURP:</label>
												<form:input path="personaAutorizada.curp" class="form-control input-sm" disabled="true" />
											</div>
										</div>
										<div class="col-xs-4">
											<div class="form-group">
												<label for="rfc">RFC:</label>
												<form:input path="personaAutorizada.rfc" class="form-control input-sm" disabled="true" />
											</div>
										</div>
									</div>
								</fieldset>
							</form:form>
							<fieldset>
								<div id="personaAutorizar">
									<div class="separadorseccion">
										<span> Patrones Asociados</span>
									</div>
								</div>
								<div id="sujetosObligados">
									<table id="tabla-patrones" style="width: 100%;" class="table table-striped" cellpadding="0" cellspacing="0"
										border="0">
										<thead>
											<tr>
												<th>Selecci&oacute;n</th>
												<th>Registro Patronal</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${sujetosEncontrados}" var="sujetoO">
												<tr>
													<td>
														<c:choose>
															<c:when test="${sujetoO.checked}">
																<input type="checkbox" id="rps" name="patronSO" value="${sujetoO.cveIdSujetoObligado}" checked="checked">
													</td>
													</c:when>
													<c:otherwise>
														<input type="checkbox" id="rps" name="patronSO" value="${sujetoO.cveIdSujetoObligado}">
														</td>
													</c:otherwise>
													</c:choose>
													<td>${sujetoO.numeroRegistroPatronal} ${sujetoO.modalidad.numModalidad} ${sujetoO.digVerificador}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</fieldset>


						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<div class="alert alert-danger">
						<button type="button" class="close" data-dismiss="alert">×</button>
						${error}
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	
	<div class="pie row">
		<div class="opciones col-sm-6">
			<c:if test="${empty error}">
				<c:if test="${solicitudCreada}">
					<div class="btn-group dropup">
						<a href="#" class="btn btn-primary">
							<spring:message code="label.menus.opciones" />
						</a>
						<a href="#" data-toggle="dropdown" class="btn btn-primary dropdown-toggle">
							<span class="caret"></span>
						</a>
						<ul class="dropdown-menu">
							<li>
								<a id="finalizarTramite">
									<i class="glyphicon glyphicon-ok"></i>
									Finalizar Tr&aacute;mite
								</a>
							</li>
							<li>
								<a id="guardarTramite">
									<i class="glyphicon glyphicon-download-alt"></i>
									Guardar Tr&aacute;mite
								</a>
							</li>
							<li>
								<a id="cancelarTramite">
									<i class="glyphicon glyphicon-trash"></i>
									Cancelar Tr&aacute;mite
								</a>
							</li>
						</ul>
					</div>
				</c:if>
			</c:if>
		</div>
		<div class="controles">
			<div class="pull-right">
				<button class="btn btn-default" id="cerrarWizard">CERRAR</button>
				<c:if test="${!solicitudCreada}">
					<button class="btn btn-primary" id="seleccionarPatrones">
						<i class="glyphicon glyphicon-step-forward"></i>
						SIGUIENTE
					</button>
				</c:if>
			</div>

		</div>

	</div>
</div>

<input type="hidden" id="folioSolicitud" value="${folioSolicitud}">

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
		¿Desea cancelar la solicitud pendiente con folio:
		<strong>${folioSolicitud}</strong>
		?
	</p>
</div>

<div id="dialog-confirm" title="Mensaje">
	<p>
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
		<label id="mensajeDialogo"></label>
	</p>
</div>
