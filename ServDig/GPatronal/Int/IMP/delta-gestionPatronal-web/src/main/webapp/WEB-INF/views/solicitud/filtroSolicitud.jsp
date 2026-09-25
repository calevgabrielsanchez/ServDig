<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/common/commonMethods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/common/validateDeltaForm.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/solicitud/filtroSolicitud.js" htmlEscape="true" />"></script>

<script>
	var context = '<%= request.getContextPath()%>';
</script>
<div class="page_holder">
	<div class="contenedor" style="width: 100% !important">
		<div class="row">
			<div class="cell" >
				<div class="row" id="divSujetoObligado" style="width: 1000px;" >
					<br>
					<fieldset style="width: 900px">
							<legend class="separadorseccion">
								<strong><spring:message code="label.consulta.solicitud" /></strong>
							</legend>
							
							<form:form 	modelAttribute="filtroSolicitud" id="filtroSolicitudForm" 
										name="filtroSolicitudForm" action="/solicitud/consultar">
								<form:hidden path="idSolicitud"/>
								<legend class="separadorseccion" style="width:880px">
									Proporcione el n&uacute;mero de folio que desea consultar.
								</legend>
								<table style="width: 100% !important; border: none !important;">
									<tr>
										<td style="width: 120px !important; border: none !important;">
											<spring:message code="label.numero.folio" />
										</td>
										<td style="width: 80px !important; border: none !important;" >
											<form:input path="folio" onkeydown="validarNumeros(event)" onkeyup="validarNumeros(event)" onkeypress="validarNumeros(event)" size="20"/>
										</td>
										<td colspan="4" align="left" style="border: none !important;">
											<button type="button" onclick="consultarSolicitudesPorFolio()" class="mboton" name="filtro">
												<spring:message code="label.buscar"/>
											</button>
											<button type="button" onclick="history.back()" class="mboton" name="regresar">
												<spring:message code="label.regresar"/>
											</button>
										</td>
									</tr>
								</table>
								<legend class="separadorseccion" style="width:880px">
									<spring:message code="msg.seleccionar.criterios"/>
								</legend>
								<table style="width: 100% !important; border: none !important;">
									<tr>
										<c:if test="${isOperador}">
											<td style="border: none !important;">
												<spring:message code="label.rfc" />
											</td>
											<td style="border: none !important;">
												<form:input path="rfc" size="13"/>
											</td>
										</c:if>
										<td style="border: none !important;">
											<spring:message code="label.nrp" />
										</td>
										<td style="border: none !important;">
											<form:input path="rp" size="15"/>
										</td>
										<c:if test="${!isOperador}">
											<td colspan="2" style="border: none !important;">
												<form:hidden path="rfc"/>
											</td>
										</c:if>
									</tr>
									<tr>
										<td style="border: none !important;" colspan="4">
											<div id="fechasError" style="color: red;"></div>
											<table>
												<tr class="fielsetgris">
													<td colspan="2" align="center">
														<spring:message code="label.fecha.presentacion" />
													</td>
													
													<td colspan="2" align="center">
														<spring:message code="label.fecha.conclusion" />
													</td>
												</tr>
												<tr>
													<td colspan="2" align="center" style="border-bottom: none !important">
														<div id="fechaPresentacionError" style="color: red;"></div>
													</td>
													<td colspan="2" align="center" style="border-bottom: none !important">
														<div id="fechaConclusionError" style="color: red;"></div>
													</td>
												</tr>
												<tr>
													<td style="border-right: none !important; border-top: none !important;">
														<spring:message code="label.fecha.inicio"/>
														<form:input path="fechaInicioPresentacion" size="8"/>
													</td>
													<td style="border: none !important;">
														<spring:message code="label.fecha.fin" />
														<form:input path="fechaFinPresentacion" size="8"/>
													</td>
													<td style="border-right: none !important; border-top: none !important;">
														<spring:message code="label.fecha.inicio" />
														<form:input path="fechaInicioConclusion" size="8"/>
													</td>
													<td style="border: none !important;">
														<spring:message code="label.fecha.fin" />
														<form:input path="fechaFinConclusion" size="8"/>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td style="border: none !important;">
											<spring:message code="label.tramite.asociado" />
										</td>
										<td style="border: none !important;" colspan="3">
											<form:select path="tramiteId">
												<form:option value="">--POR FAVOR SELECCIONE--</form:option>
												<form:options items="${filtroSolicitud.listaTipoTramite}" 
													itemValue="idTipoTramite" itemLabel="descripcion"/>
											</form:select>
											
										</td>
									</tr>
									<tr>
										<c:if test="${isOperador}">
											<td style="border: none !important;">
												<spring:message code="label.estatus.solicitud" />
											</td>
											<td style="border: none !important;">
												<combo:creaCombo idHtml="idEstadoSolicitud" idHtmlContenedor="filtroSolicitudForm" 
														entidad="mx.gob.imss.ctirss.delta.persistence.DicEstadoSolicitud" mostrarSoloActivos="true"/>
												
											</td>
										</c:if>
										<c:if test="${!isOperador}">
											<td style="border: none !important;">
												<spring:message code="label.estatus.solicitud" />
											</td>
											<td colspan="2" style="border: none !important;">
												<form:select path="idEstadoSolicitud">
													<form:option value="-1"> -POR FAVOR SELECCIONE- </form:option>
													<form:option value="<%=EstadoSolicitudEnum.ATENDIDA.getCodigo() %>">Atendida</form:option>
													<form:option value="<%=EstadoSolicitudEnum.CANCELADA.getCodigo() %>">Cancelada</form:option>
													<form:option value="<%=EstadoSolicitudEnum.RECHAZADA.getCodigo() %>">Rechazada</form:option>
												</form:select>
												
											</td>
										</c:if>
									</tr>
									<tr>
										<c:if test="${filtroSolicitud.idSubdelegacion == null}">
											
											<td style="border: none !important;">
												<spring:message code="label.municipio" />
											</td>
											<td style="border: none !important;">
												<combo:creaCombo idHtml="idDelegacion" idHtmlContenedor="filtroSolicitudForm" 
														entidad="mx.gob.imss.ctirss.delta.persistence.DicDelegacion" mostrarSoloActivos="true"/>
											</td>
											<td style="border: none !important;">
												<spring:message code="label.subdelegacion" />
											</td>
											<td style="border: none !important;">
												<combo:creaCombo 		
													entidad			="mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion" 
			                     					idHtml			="idSubdelegacion" 
			                     				  	entidadPadre	="dicDelegacion.cveIdDelegacion"
			                     				  	idHtmlPadre		="idDelegacion"
			                     				  	idHtmlContenedor="filtroSolicitudForm" 
			                     				  	mostrarSoloActivos="true"/> 
											</td>
										</c:if>
										<c:if test="${filtroSolicitud.idSubdelegacion != null}">
											<td style="border: none !important;">
												<form:hidden path="idSubdelegacion"/>
											</td>
										</c:if>
									</tr>
									<tr>
										<td colspan="4" align="right" style="border: none !important;">
											<button type="button" onclick="history.back()" class="mboton" name="regresar">
												<spring:message code="label.regresar"/>
											</button>
											<button type="button" onclick="consultarSolicitudesPorFiltros()" class="mboton" name="filtro">
												<spring:message code="label.buscar"/>
											</button>
										</td>
									</tr>
								</table>
							<br><br>
							
							<div id="divResultadosBusqueda" style="max-width: 900px;" align="right">
								<legend class="separadorseccion" align="left">
									<strong><spring:message code="titulo.resultados.busqueda" /></strong>
								</legend>
								<div id="divSolicitudesConsultadas" style="max-width: 900px; overflow: auto;">
									<table id="gridSolicitudesConsultadas" 
										style="width: 100%; vertical-align: top;">
										<thead>
										</thead>
										<tbody style="width: 100%;">
										</tbody>
									</table>
								</div>
								<button type="button" onclick="mostrarDetalleSolicitudSeleccionada()" class="mboton" name="filtro">
									<spring:message code="label.detalle.solicitud"/>
								</button>
							</div>
							</form:form>
						</fieldset>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="dgErrorSinSeleccion" title="Debe seleccionar un elemento">
	<p style="float: left; margin: 10 10px 10px 10;">
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"> </span>
		No se ha seleccionado ning&uacute;n registro para ejecutar esta acci&oacute;n.
	</p>
</div>
<div id="dialogoMensajes">
	<p><span id="textoMensaje"></span></p>
</div>