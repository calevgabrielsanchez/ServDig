<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>

<c:set var="sujetoObligado" value="${sujetoObligado}"/>
<c:set var="objClasificacion" value="${sujetoObligado.clasificacion}"/>
<c:set var="fraccion" value="${objClasificacion.fraccion}"/>
<c:set var="fisica" value="${sujetoObligado.fisica}"/>
<c:set var="moral" value="${sujetoObligado.moral}"/>
<c:set var="proceso" value="${sujetoTramite.proceso}"/>
<c:set var="tramite" value ="${idTramite}"/>
<c:set var="codigo" value ="${idTramite.codigo}"/>
<c:set var="sujetoTramite" value="${sujetoTramite}"/>
<c:set var="idSolicitud" value="${idSolicitud}"/>
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="typeLogin" value="<%=session.getAttribute(\"showFinalizarFD\")%>" />

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/common/commonMethods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/resumenClasificacion.js" htmlEscape="true" />"></script>


<script>
	var context_path = '<%= request.getContextPath()%>';
	var sessionId = '<%= request.getSession().getId()%>';

	var host = "<%=request.getContextPath()%>";
	
	var idSujetoObligado = '${sujetoObligado.cveIdSujetoObligado}';
	
	var tipoPersonaFiscal = '${sujetoObligado.tipoPersonaFiscal}';
	<c:if test="${fisica != null}">
	var rfcSujetoObligado='${fisica.rfc}';
	</c:if>
	<c:if test="${moral != null}">
	var rfcSujetoObligado='${moral.rfc}';
	</c:if>
	var idClasificacion='${objClasificacion.id}';
	var idTramite = '${tramite}';
	var codigo = <%=TipoTramiteEnum.COMPRA_DE_ACTIVOS.getCodigo()%>;
	var mostrarBienes = ${codigo} == <%=TipoTramiteEnum.COMPRA_DE_ACTIVOS.getCodigo()%> || ${codigo} == <%=TipoTramiteEnum.COMODATO.getCodigo()%> || ${codigo} == <%=TipoTramiteEnum.ENAJENACION.getCodigo()%> || ${codigo} == <%=TipoTramiteEnum.ARRENDAMIENTO.getCodigo()%> || ${codigo} == <%=TipoTramiteEnum.FIDEICOMISO_TRASLATIVO.getCodigo()%>;
	<c:if test="${idSolicitud != null}">
	var idSolicitud = ${idSolicitud};
	</c:if>
	<c:if test="${idSolicitud == null}">
	var idSolicitud = 0;
	</c:if>
	var equipoTransporte = '${sujetoObligado.cuentaConTransporte}';
</script>
					<div id="seccionDatosGenerales">
						<legend class="separadorseccion">
							<spring:message code="titulo.datos.generales.patron"/>
						</legend>
						<table style="margin: 0px !important;">
							<tr>
								<td>
									<span class="etiqueta"><spring:message code="label.nrp"/>:</span>
								</td>
								<td>
									${sujetoObligado.numeroRegistroPatronal}${ sujetoObligado.modalidad.numModalidad }${ sujetoObligado.digVerificador }
								</td>
								<c:if test="${fisica != null && moral ==null}">
								<td>
									<span class="etiqueta"><spring:message code="label.rfc"/>:</span>
								</td>
								<td>
									${fisica.rfc}
								</td>
								<td>
									<span class="etiqueta"><spring:message code="rep.legal.curp"/></span>
								</td>
								<td>
									${fisica.curp}
								</td>
								</c:if>
								<c:if test="${moral != null}">
								<td colspan="3">
									<span class="etiqueta"><spring:message code="label.rfc"/>:</span>
								</td>
								<td>
									${moral.rfc}
								</td>
								</c:if>
							</tr>
							<tr>
								<td>
									<span class="etiqueta"><spring:message code="label.tipo.persona"/>:</span>
								</td>
								<td colspan="5">
									${sujetoObligado.tipoPersonaFiscal}
								</td>
							</tr>
							<tr>
								<td>
									<c:if test="${fisica != null}">
										<span class="etiqueta"><spring:message code="label.nombre"/>:</span>
									</c:if>
									<c:if test="${moral != null}">
										<span class="etiqueta"><spring:message code="label.razon.social"/>:</span>
									</c:if>
								</td>
								<td colspan="5">
									<c:if test="${fisica != null}">
										${fisica.nombre} ${fisica.primerApellido} ${fisica.segundoApellido}
									</c:if>
									<c:if test="${moral != null}">
										${moral.razonSocial}
									</c:if>
								</td>
							</tr>
							<tr>
								<td colspan="6" style="padding:0px 0px !important;">
								<table style="margin: 0px !important; border-right: 0px none; border-left: 0px none;">
									<tr class="fielsetgris">
										<td><span class="etiqueta"><spring:message code="label.rp.clave.fraccion"/>:</span></td>
										<td><span class="etiqueta"><spring:message code="label.rp.division"/>:</span></td>
										<td><span class="etiqueta"><spring:message code="label.rp.grupo"/>:</span></td>
										<td><span class="etiqueta"><spring:message code="label.rp.fraccion"/>:</span></td>
										<td><span class="etiqueta"><spring:message code="label.rp.clase"/>:</span></td>
										<td><span class="etiqueta"><spring:message code="label.rp.prima.srt"/>:</span></td>
									</tr>
									<tr>
										<td align="center">
											<span class="dato" id="idFraccionAct">
												${fraccion.grupo.division.numDivision}${fraccion.grupo.numGrupo}${fraccion.numFraccion}
											</span>
										</td>
										<td align="center">
											<span class="dato" id="cvedivisionAct">
												${fraccion.grupo.division.descripcion}
											</span>
										</td>
										<td align="center">
											<span class="dato" id="cvegrupoAct">
												${fraccion.grupo.descripcion}
											</span>
										</td>
										<td align="center">
											<span class="dato" id="cvefraccionAct">
												${fraccion.descripcionDetallada}
											</span>
										</td>
										<td align="center">
											<span class="dato" id="cveclaseAct">
												${fraccion.clase.descripcion}
											</span>
										</td>
										<td align="center">
											<span class="dato" id="cveprimaAct">
												${fraccion.primaSRT}
											</span>
										</td>
									</tr>
								</table>
								</td>
							</tr>
						</table>
					</div>
					<br/>
<form:form modelAttribute="sujetoObligado" id="clasificacionInvokerForm">
	<form:hidden path="cveIdSujetoObligado"/>
	<form:hidden path="numeroRegistroPatronal"/>
	<form:hidden path="tipoPersonaFiscal"/>
	<div id="divTramiteClasificacion">
		<input type="button" id="btnModificarSRT" class="mboton" style="width:300px;" 
			onclick="muestraOpciones()" value="<spring:message code='label.modificacion.srt'/>">
	</div>
</form:form>

<!-- Elementos ocultaos -->
<div id="listaTramitesClasificacion" title="Tr&aacute;mites">
	<ol id="selectable">
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.ACTIVIDAD_ECONOMICA.name()%>">Cambio de actividad econ&oacute;mica</li>
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.DISPOSICION_DE_LEY.name()%>">Cambio por disposici&oacute;n de Ley, o del RACERF</li>
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.INCORPORACION_DE_ACTIVIDADES.name()%>">Incorporaci&oacute;n de actividades</li>
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.COMPRA_DE_ACTIVOS.name()%>">Compra de Activos</li>
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.COMODATO.name()%>">Comodato</li>
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.ENAJENACION.name()%>">Enajenaci&oacute;n</li>
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.ARRENDAMIENTO.name()%>">Arrendamiento</li>
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.FIDEICOMISO_TRASLATIVO.name()%>">Fideicomiso traslativo</li>
	</ol>
</div>

<div id="tramitesClasificacionExistentes" class="demo" title="Tr&aacute;mites">
	<p style="float: left; margin: 10 10px 10px 10;">
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"> </span>
		Existe un tr&aacute;mite de modificaci&oacute;n al SRT en curso espere a su conclusi&oacute;n para iniciar un nuevo tr&aacute;mite.
	</p>
</div>
