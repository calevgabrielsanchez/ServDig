<%@ include file="../../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoTramiteEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<script type="text/javascript" src="<spring:url value="/static/resources/js/portlet/solicitudesPortlet.js" htmlEscape="true" />"></script>

<c:set var="delegacion" value="${solicitud.subdelegacion.delegacion.descripcion}" />
<c:set var="subdelegacion" value="${solicitud.subdelegacion.descripcion}" />
<c:set var="idEstadoAtendida" value="<%=EstadoSolicitudEnum.ATENDIDA.getCodigo()%>" />
<c:set var="idEstadoRechazada" value="<%=EstadoSolicitudEnum.RECHAZADA.getCodigo()%>" />
<c:set var="idEstadoCancelada" value="<%=EstadoSolicitudEnum.CANCELADA.getCodigo()%>" />

<style>
	.subtitulo {
		font-size: small;
		font-weight: bold;
		line-height: 1em;
		margin-bottom: 0.5em;
		margin-top: 0;
	}
	
	.linkFolio {
		cursor: pointer;
		color: #0088CC;
		font-family: "Open Sans","Helvetica Neue",Helvetica,Arial,sans-serif;
	}
	
	.desc-campo {
		font-weight: bold !important;
	}
	
	fieldset.seccionDocumentos {
		width: 100%;
	}
	
	fieldset.seccionDocumentos legend {
		font-size: small;
		font-weight: bold;
	}
</style>

<script>
	$(function() {
		var gridSolicitud = $('#tblTramitesSolicitud').dataTable({
			"sPaginationType" : "bootstrap",
			"bLengthChange" : false,
			"bFilter" : false,
			"bSort" : false
		});
		
		$( "#tabs" ).tabs();
	});
</script>

<c:if test="${ solicitud.estadoSolicitud.idEstadoSolicitud == idEstadoAtendida }">
	<script>
		$(document).ready(function() {
			submitForm();
		});
		
		function submitForm() {
			if($("#forma").length)
				$("#forma").submit();
			
			if($("#formaResultantes").length)
				$("#formaResultantes").submit();
			
			if($("#firmaIframe").length)
	 			document.getElementById("firmaIframe").style.display = "inline";
			
			if($("#firmaIframeResultantes").length)
	 			document.getElementById("firmaIframeResultantes").style.display = "inline";
	 	}
	</script>
</c:if>

<div class="col-sm-12">
	<c:choose>
		<c:when test="${not empty solicitud.errorFormGeneral }">
			<div class="alert alert-danger" id="divMsgErrores">
				${solicitud.errorFormGeneral}
			</div>
		</c:when>
		<c:otherwise>
			<table style="width: 100% !important; font-size: smaller;"
				class="table table-bordered" cellpadding="0" cellspacing="0"
				border="0">
				<tr>
					<td class="desc-campo"><spring:message
							code="label.numero.folio" />:</td>
					<td colspan="3">${solicitud.noFolioSolicitud}</td>
				</tr>
				<c:if test="${not empty solicitud.sujetoObligado.fisica || not empty solicitud.sujetoObligado.moral}">
					<tr>
						<c:if test="${solicitud.sujetoObligado.moral != null}">
							<td class="desc-campo"><spring:message
									code="label.tipo.persona" />:</td>
							<td><spring:message code="label.persona.moral" /></td>
						</c:if>
						<c:if test="${solicitud.sujetoObligado.fisica != null}">
							<td class="desc-campo"><spring:message
									code="label.tipo.persona" />:</td>
							<td><spring:message code="label.persona.fisica" /></td>
						</c:if>
						<c:if test="${solicitud.sujetoObligado.numeroRegistroPatronal !=null && solicitud.sujetoObligado.numeroRegistroPatronal != ''}">
							<td class="desc-campo"><spring:message code="label.nrp" />:</td>
							<td>${ solicitud.sujetoObligado.numeroRegistroPatronal }</td>
						</c:if>
						<c:if test="${solicitud.sujetoObligado.numeroRegistroPatronal ==null || solicitud.sujetoObligado.numeroRegistroPatronal == ''}">
							<td colspan="2"></td>
						</c:if>
					</tr>
				</c:if>
				<c:if test="${ solicitud.estadoSolicitud.idEstadoSolicitud == idEstadoAtendida }">
					<tr>
						<td class="desc-campo"><spring:message
								code="label.fecha.presentacion" />:</td>
						<td><fmt:formatDate value="${solicitud.fechaSolicitud}"
								pattern="dd/MM/yyyy HH:mm:ss" /></td>
						<td class="desc-campo"><spring:message
								code="label.fecha.conclusion" />:</td>
						<td><fmt:formatDate value="${solicitud.fechaActualizacion}"
								pattern="dd/MM/yyyy HH:mm:ss" /></td>
					</tr>
				</c:if>
				<c:if test="${ solicitud.estadoSolicitud.idEstadoSolicitud == idEstadoRechazada }">
					<c:choose>
						<c:when test="${not empty solicitud.razonCancelacion.descripcion}">
							<c:set var="showRazonCancelacion" value="true" />
							<c:set var="colspan" value="1" />
						</c:when>
						<c:otherwise>
							<c:set var="showRazonCancelacion" value="false" />
							<c:set var="colspan" value="3" />
						</c:otherwise>
					</c:choose>
					
					<tr>
						<td class="desc-campo"><spring:message
								code="label.solicitud.rechazada.por" />:</td>
						<td colspan="${colspan}">${solicitud.observacion}</td>
						
						<c:if test="${not empty showRazonCancelacion && showRazonCancelacion eq true }">
							<td class="desc-campo"><spring:message
									code="label.solicitud.razon.rechazo" />:</td>
							<td>${solicitud.razonCancelacion.descripcion}</td>
						</c:if>
					</tr>
				</c:if>
				<c:if test="${ solicitud.estadoSolicitud.idEstadoSolicitud == idEstadoRechazada }">
					<tr>
						<td class="desc-campo"><spring:message
								code="label.solicitud.fecha.rechazo" />:</td>
						<td colspan="3"><fmt:formatDate
								value="${solicitud.fechaActualizacion}"
								pattern="dd/MM/yyyy HH:mm:ss" /></td>
					</tr>
				</c:if>
				<c:if test="${ solicitud.estadoSolicitud.idEstadoSolicitud == idEstadoCancelada }">
					<tr>
						<td class="desc-campo"><spring:message
								code="label.solicitud.cancelada.por" />:</td>
						<td>${solicitud.observacion}</td>
						<td class="desc-campo"><spring:message
								code="label.solicitud.fecha.cancelacion" />:</td>
						<td><fmt:formatDate value="${solicitud.fechaActualizacion}"
								pattern="dd/MM/yyyy HH:mm:ss" /></td>
					</tr>
				</c:if>
				<c:if test="${not empty solicitud.subdelegacion }">
					<tr>
						<td class="desc-campo"><spring:message
								code="label.delegacion.atencion" />:</td>
						<td>[${solicitud.subdelegacion.delegacion.clave}] ${ delegacion}</td>
						<td class="desc-campo"><spring:message
								code="label.subdelegacion" />:</td>
						<td>[${solicitud.subdelegacion.clave}] ${ subdelegacion }</td>
					</tr>
				</c:if>
				<tr>
					<td class="desc-campo"><spring:message
							code="label.estatus.solicitud" />:</td>
					<td colspan="3">[${
						solicitud.estadoSolicitud.idEstadoSolicitud}] ${
						solicitud.estadoSolicitud.descripcion }</td>
				</tr>
				<tr>
					<td class="desc-campo"><spring:message
							code="label.usuario" />:</td>
					<td colspan="3">${solicitud.solicitante.usuario }</td>
				</tr>
				<tr>
					<td class="desc-campo"><spring:message
							code="label.origen.solicitud" />:</td>
					<td colspan="3">${solicitud.origenSolicitud.descripcion }</td>
				</tr>
			</table>
			<br>
			<span class="subtitulo">Tr&aacute;mites</span>
			<br>
			<table id="tblTramitesSolicitud"
				class="table table-striped table-bordered" cellpadding="0"
				cellspacing="0" border="0">
				<thead>
					<tr>
						<th>Tipo tr&aacute;mite</th>
						<th>Estado tr&aacute;mite</th>
						<th>Fecha presentaci&oacute;n</th>
						<c:if test="${ solicitud.estadoSolicitud.idEstadoSolicitud == idEstadoCancelada }">
							<th>Fecha cancelaci&oacute;n</th>
						</c:if>
						<c:if test="${ solicitud.estadoSolicitud.idEstadoSolicitud != idEstadoCancelada }">
							<th>Fecha conclusi&oacute;n</th>
						</c:if>
				</thead>
				<tbody>
					<c:forEach var="tramite" items="${solicitud.tramites}">
						<tr>
							<td>${tramite.tipoTramite.descripcion}</td>
							<td>${tramite.estadoTramite.descripcion}</td>
							<td>${tramite.fechaPresentacionParse}</td>
							<c:if test="${ solicitud.estadoSolicitud.idEstadoSolicitud == idEstadoCancelada or  solicitud.estadoSolicitud.idEstadoSolicitud == idEstadoAtendida}">
								<td><fmt:formatDate value="${solicitud.fechaActualizacion}"
										pattern="dd/MM/yyyy HH:mm:ss" /></td>
							</c:if>
							<c:if test="${ solicitud.estadoSolicitud.idEstadoSolicitud != idEstadoCancelada &&  solicitud.estadoSolicitud.idEstadoSolicitud != idEstadoAtendida}">
								<td></td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
			<c:if test="${ solicitud.estadoSolicitud.idEstadoSolicitud == idEstadoAtendida }">
				<c:if test="${solicitud.tipoSolicitud.idTipoSolicitud != 59}">
				<br>
				<br>
				<br>
				<br>
				<fieldset class="seccionDocumentos">
					
						<legend>Documentos</legend>
					<ul>
						<c:if
							test="${solicitud.tipoSolicitud.idTipoSolicitud != 17 
								&& solicitud.tipoSolicitud.idTipoSolicitud != 7
								&& solicitud.tipoSolicitud.idTipoSolicitud != 31 
								&& solicitud.tipoSolicitud.idTipoSolicitud != 32
								&& solicitud.tipoSolicitud.idTipoSolicitud != 23
								&& solicitud.tipoSolicitud.idTipoSolicitud != 67
								&& solicitud.origenSolicitud.idTipoSolicitud != 1 && solicitud.origenSolicitud.idTipoSolicitud != 6
								&& solicitud.origenSolicitud.idTipoSolicitud != 5}">
							<li>
								<a class="linkFolio" href="/firmaElectronicaWeb/chfecynAcuseApp/view?id=${solicitud.secuenciaDeNotaria}"
								target="_blank"> 
									Acuse de recibo electr&oacute;nico
								</a>
							</li>
						</c:if>
						
						<c:if test="${ solicitud.tipoSolicitud.idTipoSolicitud != 5
							&& solicitud.tipoSolicitud.idTipoSolicitud != 23 && solicitud.tipoSolicitud.idTipoSolicitud != 20}">
							
							<c:forEach var="tramite" items="${solicitud.tramites}">
								<c:forEach var="documentoPT" items="${tramite.documentoPorTipos}">
								<!-- No muestra el acuse de trámite en Cambio de Domicilio -->
									<c:if test="${documentoPT.idDocumentoPorTipo != 74 && tramite.tipoTramite.idTipoTramite !=2 }">
									<li>
										<a class="linkFolio"
										onclick="abrirDocumentoResultante('${solicitud.solicitudIdHashed}','${tramite.tramiteIdHashed}','${documentoPT.idDocumentoPorTipoHashed}')">
											${documentoPT.documento.desDocumento} 
										</a>
									</li>
									</c:if>	
								<!-- Acaba validación para no mostrar acuse en Cambio de domicilio -->
								</c:forEach>
							</c:forEach>
						</c:if>
						<c:if test="${ solicitud.tipoSolicitud.idTipoSolicitud == 20 }">						
							<li><a class="linkFolio" onclick="abrirDocumentoResultante('${solicitud.solicitudIdHashed}', 
								'${solicitud.tramites[0].tramiteIdHashed}','${solicitud.tramites[0].documentoPorTipos[0].idDocumentoPorTipoHashed}')">
								${solicitud.tramites[0].documentoPorTipos[0].documento.desDocumento} </a></li>
						</c:if>	
					</ul>
					<br>
						<form action="/firmaElectronicaWeb/widget/chfecyn/imss/buscaArchivos"
							class="formNotBlock" method="post" target="firmaIframe" id="forma">
							<input type="hidden" name="params" value="{&quot;tramite&quot;:&quot;${solicitud.secuenciaDeNotaria}&quot;}" />
						</form>
						<form action="/firmaElectronicaWeb/widget/chfecyn/imss/buscaSeguimiento"
							class="formNotBlock" method="post" target="firmaIframeResultantes" id="formaResultantes">
							<input type="hidden" name="params" value="{&quot;tramite&quot;:&quot;${solicitud.secuenciaDeNotaria}&quot;}" />
						</form>
						<div id="tabs">
							<ul>
								<li><a href="#tabs-1">Documentos Adjuntos</a></li>
								<li><a href="#tabs-2">Documentos Resultantes</a></li>
							</ul>
							<div id="tabs-1">
								<iframe id="firmaIframe" name="firmaIframe" height="500" style="display: none; width: 100%" frameborder="0"></iframe>
							</div>
							<div id="tabs-2">
								<iframe id="firmaIframeResultantes" name="firmaIframeResultantes" height="500" style="display: none; width: 100%" frameborder="0"></iframe>
							</div>
						</div>
				</fieldset>
			</c:if>
			<c:if test="${solicitud.tipoSolicitud.idTipoSolicitud == 59}">
						<div id="doctosBoveda">
							Cargando documentos, espera por favor...
						</div>
						<script type="text/javascript" src="/gestionDocumentoProbatorio-web/static/resources/js/boveda/boveda.js"></script>
						<c:forEach var="tramiteBoveda" items="${solicitud.tramites}">
						<script type="text/JavaScript">
							$(document).ready(function() {
								$("#doctosBoveda").boveda({tipoTramite: 155, idTramite: ${tramiteBoveda.tramiteId}, tipoComponente: 2,tipoDocumental: "D:RTT:escrito_desacuerdo"})
							}) 
						</script>
						</c:forEach>
					</c:if>
			</c:if>
			<form id="solicitudDocumentoForm" name="solicitudDocumentoForm" method="get" target="_blank" class="formNotBlock" action="${contextpath }/portlet/solicitudes/mostrarDocumentoResultante">
				<input type="hidden" name="idSolicitud" id="idSolicitud" /> 
				<input type="hidden" name="idTramite" id="idTramite" /> 
				<input type="hidden" name="tipoDocumento" id="tipoDocumento" />
			</form>
			<form id="cartaTerminos" name="cartaTerminos" method="POST"
				target="_blank" class="formNotBlock">
				<input type="hidden" name="selloDigital" id="selloDigital" value="${solicitud.selloDigital}"> 
				<input type="hidden" name="cadenaOriginal" id="cadenaOriginal" value="${solicitud.cadenaOriginal}">
			</form>	
		</c:otherwise>
	</c:choose>
</div>