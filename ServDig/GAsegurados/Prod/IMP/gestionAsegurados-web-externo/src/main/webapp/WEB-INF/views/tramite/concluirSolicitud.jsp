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

<div class="container">
	<div id="info-paso"  style="margin-bottom: 50px;">
		<h3>
			<span id="pasoProceso">Paso 4: Solicitud en proceso</span>
		</h3>
		<hr class="red" style="margin-bottom: 20px;">
		
		<input type="hidden" id="hDP" name="hDP" value="${hashDatosPersona }" />
		<input type="hidden" id="hdnFolioSolicitud" value="${folioSolicitud}" /> 
		<input type="hidden" id="hdnIdSolicitud" value="${idSolicitud}" />
	</div>

	<!-- Forma de la consulta de personas por datos basicos. -->
	<div class="form-comment">
		<div class="contenedor">
			<c:choose>
				<c:when test="${empty HUBO_ERROR }">

					<div id="procesandoDiv" class="well p-md">
						<p
							style="font-size:x-large; font-weight: bold; text-align: center;">La
							solicitud ${folioSolicitud} est&aacute; en proceso ...</p>

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
							La solicitud <strong><span id="folioSolicitudSuccess"></span></strong> fue concluida exitosamente
						</div>
						
						<div class="alert alert-warning alert-block" id="notDownloadedWarning"
								style="display: none;">
							<strong><i class="glyphicon glyphicon-warning-sign"
								style="margin-right: 8px;"></i>El comprobante no ha sido
								descargado</strong>
						</div>
						
						<div class="row">
							<div class="col-md-6">
								<div class="row">
									<div class="col-md-12">
									<h3>Descargar comprobante del tr&aacute;mite</h3>
									<div class="textwidget">
										<p style="font-size: .9em;">Imprima el comprobante de la
											solicitud.</p>
									</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-7">
									<form action="" id="formComprobante" class="formNotBlock"
										method="get" alreadyDownloaded="false">
										<input type="hidden" name="si" id="si" />
										<button type="button" id="descargarComp"
											class="btn btn-primary">Descargar comprobante</button>
									</form>
									</div>
									<div class="col-md-5">
									<form action="" id="formImprimirNSS" class="formNotBlock"
										method="post" target="_blank">
										<input type="hidden" name="si" id="si" />
										<button type="button" id="imprimirNSS"
											class="btn btn-primary">Imprimir NSS</button>
									</form>
								</div>
								
							</div>
							
						</div>
						<div class="col-md-6">
								<h3>Tr&aacute;mite concluido</h3>
								<div class="textwidget">
									<p style="font-size: .9em;">El tr&aacute;mite ha sido concluido.</p>
								</div>
								<button type="button" class="btn btn-primary btnFinTramite">Aceptar</button>
						</div>
					</div>
					
					<div id="errorDiv" class="hiddenElement">
						<div class="alert alert-danger alert-block">
							<span id="errorMsg"></span>
						</div>
						
						<div class="row">
							<div class="col-xs-12">
								<h3>Tr&aacute;mite concluido</h3>
								<div class="textwidget">
									<p style="font-size: .9em;">El tr&aacute;mite ha sido concluido.</p>
								</div>
								<button type="button" class="btn btn-primary btnFinTramite">Aceptar</button>
							</div>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="alert alert-danger alert-block">
						La solicitud ${folioSolicitud} no pudo ser concluida debido a: 
						<strong>${mensaje}</strong>
					</div>
					<div class="row">
							<div class="col-xs-12">
								<h3>Tr&aacute;mite concluido</h3>
								<div class="textwidget">
									<p style="font-size: .9em;">El tr&aacute;mite ha sido concluido.</p>
								</div>
								<button type="button" class="btn btn-primary btnFinTramite">Aceptar</button>
							</div>
						</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>

<form action="${contextpath}/tramite/iniciar" method="get"
	id="terminarTramiteForm" class="formNotBlock"></form>