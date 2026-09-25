<%@page import="mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoTramiteEnum"%>
<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum"%>
<%@page import="mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.CodigoRolTemporal"%>

<script>
	var context = '<%= request.getContextPath()%>';
	var idEstatusParaEdicionBackOffice = <%=EstadoSolicitudEnum.PARA_PROCESAR_BACKOFFICE.getCodigo()%>;
	var idEstatusParaEdicionVentanilla = <%=EstadoSolicitudEnum.PRESENTARSE_EN_VENTANILLA.getCodigo()%>;
	var idEstatusProcesadaEnVentanilla = <%=EstadoSolicitudEnum.PROCESADA_VENTANILLA.getCodigo()%>;
	var idEstatusProcesadaEnBackoffice = <%=EstadoSolicitudEnum.PROCESADA_BACKOFFICE.getCodigo()%>;
	var estadoSolicitud = ${solicitud.estadoSolicitud.idEstadoSolicitud};
	var isOperadorIMSS = ${isOperadorIMSS};
	var rolPatron="<%=CodigoRolTemporal.PATRON_SUJETO_OBLIGADO%>";
	var rolRepresentante="<%=CodigoRolTemporal.REPRESENTANTE_LEGAL%>";
	var esPatronFisico=${bFisica};
	var idEstatusRechazada=<%=EstadoSolicitudEnum.RECHAZADA.getCodigo()%>;
</script>
<!-- Se colocan debajo las inclusiones de script -->
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/common/commonMethods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/solicitud/detalleSolicitud.js" htmlEscape="true" />"></script>

<c:set var="delegacion" value="${solicitud.sujetoObligado.subdelegacion.delegacion.descripcion}"/>
<c:set var="subdelegacion" value="${solicitud.sujetoObligado.subdelegacion.descripcion}"/>
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="idEstadoAtendida" value="<%=EstadoSolicitudEnum.ATENDIDA.getCodigo()%>" />
<c:set var="idEstadoRechazada" value="<%=EstadoSolicitudEnum.RECHAZADA.getCodigo()%>" />
<c:set var="idEstadoCancelada" value="<%=EstadoSolicitudEnum.CANCELADA.getCodigo()%>" />

<c:if test="${bFisica}">
	<c:set var="rfc" value="${solicitud.sujetoObligado.fisica.rfc}"/>
</c:if>
<c:if test="${!bFisica}">
	<c:set var="rfc" value="${solicitud.sujetoObligado.moral.rfc}"/>
</c:if>

<div class="page_holder">
	<div class="contenedor" style="width: 100% !important">
		<div class="row">
			<div class="cell" >
				<div class="row" id="divSujetoObligado" style="width: 1000px;" >
					<br>
					<fieldset style="width: 900px;">
						<legend class="separadorseccion">
							<strong><spring:message code="titulo.solicitud" /></strong>
						</legend>
						<form:form 	id="solicitudDocumentoForm" 
										name="solicitudDocumentoForm" method="POST" action="${contextpath}/solicitud/actualizarEstatus" target="_blank">
							<c:if test="${ solicitud.documentoAcuse != null }">
								<button type="button" class="mboton" onclick="abrirDocumentoDeTramite(${solicitud.solicitudId},${solicitud.noFolioSolicitud},<%=TipoDocumentoTramiteEnum.ACUSE.getCodigo() %>)">
									<spring:message code="label.acuse.recibo"/>
								</button>
							</c:if>
							<c:if test="${ solicitud.documentoComprobante != null }">
								<button type="button" class="mboton" onclick="abrirDocumentoDeTramite(${solicitud.solicitudId},${solicitud.noFolioSolicitud},<%=TipoDocumentoTramiteEnum.COMPROBANTE.getCodigo() %>)">
									<spring:message code="label.comprobante.tramite"/>
								</button>
							</c:if>				
						</form:form>
						<form:form 	modelAttribute="solicitud" id="solicitudForm" 
										name="solicitudForm" action="${contextpath}/solicitud/actualizarEstatus">
							<form:hidden path="solicitudId"/>
							<form:hidden path="estadoSolicitud.idEstadoSolicitud"/>
							<form:hidden path="razonCancelacion.idRazonCancelacion"/>
							<table style="width: 100% !important; border: none !important;">
								<tr>
									<td style="width: 150px !important; border: none !important;">
										<spring:message code="label.numero.folio" />
									</td>
									<td style="border: none !important;">
										${solicitud.noFolioSolicitud}
									</td>
									<c:if test="${solicitud.sujetoObligado.moral != null}">
										<td style="width: 150px !important; border: none !important;">
											<spring:message code="label.tipo.persona" />
										</td>
										<td style="border: none !important;">
											<spring:message code="label.persona.moral"/>
										</td>
									</c:if>
									<c:if test="${solicitud.sujetoObligado.fisica != null}">
										<td style="width: 150px !important; border: none !important;">
											<spring:message code="label.tipo.persona" />
										</td>
										<td style="border: none !important;">
											<spring:message code="label.persona.fisica"/>
										</td>
									</c:if>
								</tr>
								<tr>
									<c:if test="${solicitud.sujetoObligado.moral != null}">
										<td style="width: 150px !important; border: none !important;">
											<spring:message code="label.razon.social" />
										</td>
										<td style="border: none !important;">
											${solicitud.sujetoObligado.moral.razonSocial}
										</td>
										<td style="width: 150px !important; border: none !important;">
											<spring:message code="label.tipo.sociedad" />
										</td>
										<td style="border: none !important;">
											${solicitud.sujetoObligado.moral.tipoSociedad.descripcion}
										</td>
									</c:if>
									<c:if test="${solicitud.sujetoObligado.fisica != null}">
										<td style="width: 150px !important; border: none !important;">
											<spring:message code="label.nombre" />
										</td>
										<td style="border: none !important;" colspan="3">
											${solicitud.sujetoObligado.fisica.nombre} ${solicitud.sujetoObligado.fisica.primerApellido} ${solicitud.sujetoObligado.fisica.segundoApellido}
										</td>
									</c:if>
								</tr>
								
								<tr>
									<td style="border: none !important;">
										<spring:message code="label.rfc" />
									</td>
									<td style="border: none !important;">
										<label>
											${ rfc }
										</label>
									</td>
									<c:if test="${solicitud.sujetoObligado.numeroRegistroPatronal !=null && solicitud.sujetoObligado.numeroRegistroPatronal != ''}">
										<td style="border: none !important;">
											<spring:message code="label.nrp" />
										</td>
										<td style="border: none !important;">
											${ solicitud.sujetoObligado.numeroRegistroPatronal }${ solicitud.sujetoObligado.modalidad.numModalidad }${ solicitud.sujetoObligado.digVerificador }
										</td>
									</c:if>
									<c:if test="${solicitud.sujetoObligado.numeroRegistroPatronal ==null || solicitud.sujetoObligado.numeroRegistroPatronal == ''}">
										<td colspan="2" style="border: none !important;">
										</td>
									</c:if>
								</tr>
								<tr>
									<c:if test="${ solicitud.estadoSolicitud.idEstadoSolicitud == idEstadoAtendida }">
										<td style="border: none !important;">
											<spring:message code="label.fecha.presentacion" />
										</td>
										<td style="border: none !important;">
												<fmt:formatDate value="${solicitud.fechaSolicitud}" pattern="dd/MM/yyyy HH:mm:ss"/>
										</td>
										<td style="border: none !important;">
											<spring:message code="label.fecha.conclusion" />
										</td>
										<td style="border: none !important;">
												<fmt:formatDate value="${solicitud.fechaActualizacion}" pattern="dd/MM/yyyy HH:mm:ss"/>
										</td>
									</c:if>
								</tr>
								<tr>
									<c:if test="${ solicitud.estadoSolicitud.idEstadoSolicitud == idEstadoRechazada }">
										<td style="border: none !important;">
											<spring:message code="label.solicitud.rechazada.por" />
										</td>
										<td style="border: none !important;">
												${solicitud.observacion}
										</td>
										<td style="border: none !important;">
											<spring:message code="label.solicitud.razon.rechazo" />
										</td>
										<td style="border: none !important;">
												${solicitud.razonCancelacion.descripcion}
										</td>
									</c:if>
								</tr>
								<tr>
									<c:if test="${ solicitud.estadoSolicitud.idEstadoSolicitud == idEstadoRechazada }">
										<td style="border: none !important;">
											<spring:message code="label.solicitud.fecha.rechazo" />
										</td>
										<td style="border: none !important;">
												<fmt:formatDate value="${solicitud.fechaActualizacion}" pattern="dd/MM/yyyy HH:mm:ss"/>
										</td>
									</c:if>
								</tr>
								<tr>
									<c:if test="${ solicitud.estadoSolicitud.idEstadoSolicitud == idEstadoCancelada }">
										<td style="border: none !important;">
											<spring:message code="label.solicitud.cancelada.por" />
										</td>
										<td style="border: none !important;">
												${solicitud.observacion}
										</td>
										<td style="border: none !important;">
											<spring:message code="label.solicitud.fecha.cancelacion" />
										</td>
										<td style="border: none !important;">
												<fmt:formatDate value="${solicitud.fechaActualizacion}" pattern="dd/MM/yyyy HH:mm:ss"/>
										</td>
									</c:if>
								</tr>
								<tr>
									<td style="border: none !important;">
										<spring:message code="label.tramites.asociados"/>
									</td>
									<td style="border: none !important;" colspan="3">
										<c:forEach var="tramite" items="${solicitud.tramites}">
											${tramite.tipoTramite.descripcion}/
										</c:forEach>
									</td>
								</tr>
								<tr>
									<td style="border: none !important;">
										<spring:message code="label.delegacion.atencion"/>
									</td>
									<td style="border: none !important;">
										[${solicitud.sujetoObligado.subdelegacion.delegacion.clave}] ${ delegacion }
									</td>
									<td style="border: none !important;">
										<spring:message code="label.subdelegacion" />
									</td>
									<td style="border: none !important;">
										[${solicitud.sujetoObligado.subdelegacion.clave}] ${ subdelegacion }
									</td>
								</tr>
								<tr>
									<td style="border: none !important;">
										<spring:message code="label.estatus.solicitud" />
									</td>
									<td style="border: none !important;" colspan="3">
										[${ solicitud.estadoSolicitud.idEstadoSolicitud}] ${ solicitud.estadoSolicitud.descripcion }
									</td>
								</tr>
								<tr>
									<td style="border: none !important;" colspan="4">				
										<div id="embebedPDF" >
											<!-- 
											<iframe contenteditable="false" type="application/pdf"  src="<spring:url value='/solicitud/mostrarDocumento?idSolicitud=${solicitud.solicitudId}&noFolio=${solicitud.noFolioSolicitud}&idEstadoSolicitud=${solicitud.estadoSolicitud.idEstadoSolicitud}' htmlEscape='true' />"  style="width:718px; height:700px;">"  
												<p><b>No hay ning&uacute;n documento disponible para mostrar</b></p>
											</iframe>
											 -->
										</div>
									</td>
								</tr>
								<tr>
									<td align="right" style="border: none !important;" colspan="4">
										<div id="divBotonesDetalleSolicitud">
											<c:if test="${bFisica}">
												<form:hidden path="sujetoObligado.fisica.rfc"/>
											</c:if>
											<c:if test="${!bFisica}">
												<form:hidden path="sujetoObligado.moral.rfc"/>
											</c:if>
																						
											<button type="button" onclick="validaDetalleSolicitudTramiteActivo(<%=EstadoSolicitudEnum.EDICION_VENTANILLA.getCodigo()%>)" class="mboton" name="btnEditar" id="btnEditar">
												<spring:message code="label.editar"/>
											</button>
											<button type="button" onclick="validaDetalleSolicitudTramiteActivo(<%=EstadoSolicitudEnum.EDICION_BACKOFFICE.getCodigo()%>)" class="mboton" name="btnEditar" id="btnEditarBackOffice">
												<spring:message code="label.editar.backoffice"/>
											</button>
											<button type="button" onclick="actualizarSolicitud(<%=EstadoSolicitudEnum.PRESENTARSE_EN_VENTANILLA.getCodigo()%>)" class="mboton" name="btnEnviarVentanilla" id="btnEnviarVentanilla">
												<spring:message code="label.enviar.ventanilla"/>
											</button>
											<button type="button" onclick="actualizarSolicitud(<%=EstadoSolicitudEnum.PROCESADA_BACKOFFICE.getCodigo()%>)" class="mboton" name="btnConcluirBackOffice" id="btnConcluirBackOffice" >
												<spring:message code="label.concluir.backoffice"/>
											</button>
											<button type="button" onclick="especificarSolicitante()" class="mboton" name="btnConcluirPresencialmente" id="btnConcluirPresencialmente">
												<spring:message code="label.concluir.presencialmente"/>
											</button>
											<button type="button" onclick="despliegaMensajeConfirmacion(<%=EstadoSolicitudEnum.RECHAZADA.getCodigo()%>)" class="mboton" name="btnRechazar" id="btnRechazar">
												<spring:message code="label.rechazar"/>
											</button>
											<button type="button" onclick="history.back()" class="mboton" name="regresar">
												<spring:message code="label.regresar"/>
											</button>
										</div>	
									</td>
								</tr>
							</table>
						</form:form>
					</fieldset>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="dialogoMensajes">
	<p><span id="textoMensaje"></span></p>
</div>
<jsp:include page="razonRechazo.jsp"/>

<form:form modelAttribute="sujetoObligado"  action="" id="formReporteModificacionPatronal" method="POST">	
</form:form>

<form:form modelAttribute="sujetoObligado"  action="" id="formReporteClasificacion" method="POST">	
</form:form>



<jsp:include page="../common/vistaSolicitante.jsp"/>