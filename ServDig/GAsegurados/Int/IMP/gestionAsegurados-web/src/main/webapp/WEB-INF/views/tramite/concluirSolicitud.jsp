<!-- Vista para la captura de los datos de la persona -->
<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script language="javascript">
	window.history.forward(1);
</script>

<script type="text/javascript"
	src="/portal-web/static/resources/js/delta/CometConector.js"></script>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/tramite/tramite-concluirSolicitud.js" htmlEscape="true" />"></script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<c:if test="${not empty HUBO_ERROR }">
	<input type="hidden" value="${HUBO_ERROR}" id="huboError" />
</c:if>

<c:if test="${not empty  EDO_SOLICITUD }">
	<input type="hidden" value="${EDO_SOLICITUD}" id="edoSolicitud" />
</c:if>

<c:if test="${not empty  MOTIVO_CANCELACION_SOLICITUD }">
	<input type="hidden" value="${MOTIVO_CANCELACION_SOLICITUD}" id="motivoCancelacionSolic" />
</c:if>

<div class="contenedor">
	<div>
		<div id="info-paso">
			<h3 style="font-size: 1.8em !important">
				<span id="pasoProceso">Paso 4: Solicitud en Proceso</span>
			</h3>
			
			<input type="hidden" name="hashDatosPersona" id="hashDatosPersona" value="${hashDatosPersona }" />
			<input type="hidden" id="hdnFolioSolicitud" value="${folioSolicitud}" /> 
			<input type="hidden" id="hdnIdSolicitud" value="${idSolicitud}" />
		</div>

		<!-- Forma de la consulta de personas por datos basicos. -->
		<div class="form-comment">
			<div class="contenedor">
				<c:choose>
					<c:when test="${empty HUBO_ERROR }">
						
						<c:if test="${not empty MSG_SOLIC_PROCESO }">
							<div class="alert alert-info alert-block">
								${MSG_SOLIC_PROCESO}
							</div>
						</c:if>
						
						<div id="procesandoDiv" class="well">
							<p style="font-size: large; font-weight: bold; text-align: center;">La
								solicitud ${folioSolicitud} est&aacute; en proceso
								...</p>

							<div style="text-align: center; vertical-align: middle;">
								<img alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
							</div>

							<p style="margin-top: 18px;">
								Esta p&aacute;gina se recargar&aacute; autom&aacute;ticamente en
								cuanto su solicitud haya sido procesada. Se recomienda <strong>NO</strong>
								cerrar esta ventana.
							</p>
						</div>

						<div id="successDiv" class="hiddenElement">
							<div class="alert alert-success alert-block">
								La solicitud <strong><span id="folioSolicitudSuccess"></span></strong>
								fue concluida exitosamente
							</div>
							
							<div class="alert alert-warning alert-block" id="notDownloadedWarning"
								style=" display: none;">
								<strong><i class="glyphicon glyphicon-warning-sign"
									style="margin-right: 8px;"></i>El comprobante no ha sido
									descargado</strong>
							</div>
							
							<table style="width: 100%; border: none ! important;">
								<tr>
									<td style="border: none; width: 50%;">
										<h3>Descargar comprobante del tr&aacute;mite</h3>
										<div class="textwidget">
											<p style="font-size: .9em;">Descarga el comprobante de la
												solicitud.</p>
										</div>
										<form action="" id="formComprobante" class="formNotBlock"
											method="get" alreadyDownloaded="false">
											<input type="hidden" name="si" id="si" />
											<button type="button" id="descargarComp"
												class="btn btn-primary">DESCARGAR COMPROBANTE</button>
										</form>
									</td>
									<c:choose>
										<c:when test="${not empty FROM_SIME}">
											<td style="border: none; width: 50%;">
												<h3>Continuar proceso SIME</h3>
												<p style="font-size: .9em;"></p>
												<button type="button" id="btnContinuarSIME"
													class="btn btn-primary">CONTINUAR</button>
											</td>
										</c:when>
										<c:otherwise>
											<td style="border: none; width: 50%;">
												<h3>Crear una nueva solicitud.</h3>
												<div class="textwidget">
													<p style="font-size: .9em;">Crear una nueva solicitud.</p>
												</div>
												<form id="formNuevaSolicitud" class="formNotBlock"
													action="${contextpath}/tramite/iniciar" method="get">
													<button type="submit" class="btn btn-primary">NUEVA
														SOLICITUD</button>
												</form>
											</td>
										</c:otherwise>
									</c:choose>
								</tr>
							</table>
						</div>

						<div id="errorDiv" class="hiddenElement">
							<div class="alert alert-danger alert-block"
								style="">
								<span id="errorMsg"></span>
							</div>
							
							<c:choose>
								<c:when test="${not empty FROM_SIME}">
									<td style="border: none; width: 50%;">
										<h3>Continuar proceso SIME</h3>
										<p style="font-size: .9em;"></p>
										<button type="button" id="btnContinuarSIME"
											class="btn btn-primary">CONTINUAR</button>
									</td>
								</c:when>
								<c:otherwise>
									<form id="formNuevaSolicitud"
										action="${contextpath}/tramite/iniciar" method="get">
										<button type="submit" class="btn btn-primary">NUEVA
											SOLICITUD</button>
									</form>
								</c:otherwise>
							</c:choose>
						</div>
					</c:when>
					<c:otherwise>
						<div class="alert alert-danger alert-block"
							style="">
							La solicitud ${folio} no pudo ser concluida debido a: <strong>${mensaje}</strong>
						</div>

						<c:choose>
							<c:when test="${not empty FROM_SIME}">
								<td style="border: none; width: 50%;">
									<h3>Continuar proceso SIME</h3>
									<p style="font-size: .9em;"></p>
									<button type="button" id="btnContinuarSIME"
										class="btn btn-primary">CONTINUAR</button>
								</td>
							</c:when>
							<c:otherwise>
								<form id="formNuevaSolicitud"
									action="${contextpath}/tramite/iniciar" method="get">
									<button type="submit" class="btn btn-primary">NUEVA
										SOLICITUD</button>
								</form>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
</div>

<c:if test="${not empty FROM_SIME}">
	<form id="extranjeroSIMERecuperadoForm"
		action="${contextpath}/sime/nss/asignado" method="post">
		<input type="hidden" id="fromSIME" value="true"/>
		<input type="hidden" name="llaveRegistro" value="${EXTRANJERO_SIME.llaveRegistro}" />
		<input type="hidden" name="nss" id="nss" value="${EXTRANJERO_SIME.nss}" />
		<input type="hidden" name="estadoRegistroAux" id="estadoRegistro" value="${EXTRANJERO_SIME.estadoRegistro.clave}"/>
		<input type="hidden" name="umf.idUMF" id="idUmfAsegurado" value="${EXTRANJERO_SIME.umf.idUMF}"/>
		<input type="hidden" name="umf.noEconomico" id="noEconomicoUmfAsegurado" value="${EXTRANJERO_SIME.umf.noEconomico}"/>
		<input type="hidden" name="umf.subdelegacion.id" id="idSubdelegacionAsegurado"value="${EXTRANJERO_SIME.umf.subdelegacion.id}" />
		<input type="hidden" name="umf.subdelegacion.clave" id="cveSubdelegacionAsegurado" value="${EXTRANJERO_SIME.umf.subdelegacion.clave}"/>
		<input type="hidden" name="umf.subdelegacion.delegacion.id" id="idDelegacionAsegurado" value="${EXTRANJERO_SIME.umf.subdelegacion.delegacion.id}"/>
		<input type="hidden" name="umf.subdelegacion.delegacion.clave" id="cveDelegacionAsegurado" value="${EXTRANJERO_SIME.umf.subdelegacion.delegacion.clave}"/>
		<input type="hidden" name="umf.subdelegacion.delegacion.ciz" id="cveCizAsegurado" value="${EXTRANJERO_SIME.umf.subdelegacion.delegacion.ciz}"/>
	</form>
</c:if>