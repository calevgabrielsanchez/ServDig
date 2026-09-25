<!-- Vista para la captura de los datos de la persona -->
<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="combo"
	uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script src="<spring:url value="/static/resources/js/delta/riss/concluirSolicitud.js" htmlEscape="true" />"></script>

<c:if test="${not empty HUBO_ERROR }">
	<input type="hidden" value="${HUBO_ERROR}" id="huboError" />
</c:if>

<c:if test="${not empty  EDO_SOLICITUD }">
	<input type="hidden" value="${EDO_SOLICITUD}" id="edoSolicitud" />
</c:if>

<c:if test="${not empty  MOTIVO_CANCELACION_SOLICITUD }">
	<input type="hidden" value="${MOTIVO_CANCELACION_SOLICITUD}"
		id="motivoCancelacionSolic" />
</c:if>

<div class="contenedor">
	<div>
		
		<input type="hidden" id="hdnFolioSolicitud" value="${folioSolicitud}" />
		<input type="hidden" id="hdnIdSolicitud" value="${idSolicitud}" />
		<input type="hidden" id="idSolicitudHashed" value="${idSolicitudHashed}" />
		<input type="hidden" id="idTramiteHashed" value="${idTramiteHashed}" />
		<input type="hidden" id="tipoDocumentoHashed" value="${tipoDocumentoHashed}" />
		<c:choose>
			<c:when test="${empty HUBO_ERROR }">

				<div id="procesandoDiv">				
					<div id="info-paso">
						<h3 style="font-size: 1.8em !important">Paso 4: Solicitud en Proceso.</h3>			
					</div>
					<div class="well">
						<p style="font-size: large; font-weight: bold; text-align: center;">La
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
						
				</div>

				<div id="successDiv" class="hiddenElement">
				
					<div id="info-paso">
						<h3 style="font-size: 1.8em !important">Paso 5: Solicitud Concluida.</h3>			
					</div>
					
					<div class="alert alert-success alert-block">
						La solicitud <strong><span id="folioSolicitudSuccess"></span></strong>
						fue concluida exitosamente.
					</div>
					
					<div class="alert alert-warning alert-block" id="notDownloadedWarning">
						<strong><i class="glyphicon glyphicon-warning-sign m-r-sm">
							</i>El comprobante no ha sido descargado.</strong>
					</div>

					<div id="errorDiv" class="hiddenElement">
						<div class="alert alert-danger alert-block">
							<span id="errorMsg"></span>
						</div>
					</div>

					<div class="row">
						<div class="col-md-6">
							<h3>Descargar comprobante del tr&aacute;mite</h3>

							<form action="" id="formComprobante" class="formNotBlock" method="get" alreadyDownloaded="false" target="_blank">
								<button type="button" id="descargarComp" class="btn btn-primary">DESCARGAR COMPROBANTE</button>

								<input type="hidden" name="idSolicitud" id="idSolicitud" />
								<input type="hidden" name="idTramite" id="idTramite" />
								<input type="hidden" name="tipoDocumento" id="tipoDocumento" />
							</form>
						</div>
						<div class="col-md-6">
							<div id="nvaSolicDiv" class="hiddenElement" style="float: right;">
								<form id="formNuevaSolicitud" class="formNotBlock" action="${contextpath}/alta/riss/iniciar" method="get">
									<button type="submit" class="btn btn-primary">NUEVA SOLICITUD</button>
								</form>
							</div>
						</div>
					</div>			
				</div>

				
			</c:when>
			<c:otherwise>
				<div id="info-paso">
					<h3 style="font-size: 1.8em !important">Paso 4: Solicitud en Proceso.</h3>			
				</div>	
				
				<div class="alert alert-danger alert-block">
					La solicitud ${folio} no pudo ser concluida debido a: <strong>${mensaje}</strong>
				</div>
				
				<div id="nvaSolicDiv" style="float: right;">
					<form id="formNuevaSolicitud" action="${contextpath}/alta/riss/iniciar" method="get">
						<button type="submit" class="btn btn-primary">NUEVA SOLICITUD</button>
					</form>
				</div>
				
			</c:otherwise>
		</c:choose>				
	</div>
</div>