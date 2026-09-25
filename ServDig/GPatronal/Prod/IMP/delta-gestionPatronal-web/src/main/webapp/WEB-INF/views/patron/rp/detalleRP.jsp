<%@ include file="../../general/taglibs.jsp"%>

<style>
	#selectable .ui-selecting { background: #FECA40; }
	#selectable .ui-selected { background: #F39814; color: white; }
	#selectable { list-style-type: none; margin: 0; padding: 0; width: 100%; }
	#selectable li { margin: 3px; padding: 0.4em; font-size: 1.0em; height: 18px; }
</style>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>
<script>
	var denominacionSocial=<%=TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL.getCodigo().intValue()%>;
	var datosContacto=<%=TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO.getCodigo().intValue()%>;
	var escrituraConstitutiva=<%=TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA.getCodigo().intValue()%>;
	var registroSindicato=<%=TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO.getCodigo().intValue()%>;
	var socio=<%=TipoTramiteEnum.ACTUALIZACION_SOCIO.getCodigo().intValue()%>;
	var representanteLegal=<%=TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo().intValue()%>;
	var idSujetoObligado = '${sujetoObligado.cveIdSujetoObligado}' != "" ? '${sujetoObligado.cveIdSujetoObligado}' : null;
</script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/detalleRP.js" htmlEscape="true" />"></script>

<div class="page_holder">
	<div class=" contenedor" style="width: 100% !important">
		<div class="row">
			<div class="cell" >
				<div class="row" id="regPatronal" style="width: 1000px;" >
					<c:set var="contextpath" value="<%=request.getContextPath()%>" />
					<form>
						<div class="izquierda">
							<input type="button" class="mboton" name="regresar"
							onclick="history.back();"
							value="<spring:message code="label.regresar" />"></input>
						</div>
					</form>
					<div class="cell form-comment" id="captura" style=" float:right; width:600px !important; height: 100% !important; ">
						<div class="row">
							<h2><spring:message code="title.informacion.general"/></h2>
						</div>
						<div class="row">
							<h3>Registro Patronal</h3>
							<p>A continuaci&oacute;n se presenta la informaci&oacute;n y tr&aacute;mites del registro patronal</p>
						</div>
						<c:set var="contextpath" value="<%=request.getContextPath()%>" />
						<form:form modelAttribute="sujetoObligado"  action="${contextpath}/sujetoObligado/datosGenerales" id="patronForm">
							<input type="hidden" id="idTramite" name="idTramite"/>
							<input type="hidden" id="idSolicitud" name="idSolicitud"/>
							<form:hidden path="cveIdSujetoObligado"/>
							<form:hidden path="tipoPersonaFiscal"/>
							<form:hidden path="cntroTrabajo.clave"/>
							<form:hidden path="domicilioFiscal.clave"/>
							<c:set var="contextpath" value="<%=request.getContextPath()%>" />
							<fieldset style="margin: 20px !important;">
								<legend>
									<strong><spring:message code="titulo.detalle.rp" /></strong>
								</legend>
								<fieldset class="fsInterno">
									<label style="width:25%">
										<spring:message code="label.registro.patronal" />
									</label>
									<form:input readonly="true" path="numeroRegistroPatronal" maxlength="14" cssStyle="width:70%"/>
								</fieldset>
								<c:if test="${bFisica}">
									<jsp:include page="sujetoObligadoPersonaFisica.jsp" />
								</c:if>
								<c:if test="${!bFisica}">
									<jsp:include page="sujetoObligadoPersonaMoral.jsp" />
								</c:if>
								<fieldset class="fsInterno">
									<label style="width:25%">
										<spring:message code="label.rp.division" />
									</label>
									<form:input readonly="true" path="clasificacion.fraccion.grupo.division.descripcion" maxlength="14" cssStyle="width:70%"/>
								</fieldset>
								<fieldset class="fsInterno">
									<label style="width:25%">
										<spring:message code="label.rp.grupo" />
									</label>
									<form:input readonly="true" path="clasificacion.fraccion.grupo.descripcion" maxlength="14" cssStyle="width:70%"/>
								</fieldset>
								<fieldset class="fsInterno">
									<label style="width:25%">
										<spring:message code="label.rp.fraccion" />
									</label>
									<form:input readonly="true" path="clasificacion.fraccion.descripcion" maxlength="14"cssStyle="width:70%"/>
								</fieldset>
								<fieldset class="fsInterno">
									<label style="width:25%">
										<spring:message code="label.rp.clase" />
									</label>
									<form:input readonly="true" path="clasificacion.fraccion.clase.descripcion" maxlength="14" cssStyle="width:70%"/>
								</fieldset>
								<fieldset class="fsInterno">
									<label style="width:25%">
										<spring:message code="label.rp.prima.srt" />
									</label>
									<form:input readonly="true" path="clasificacion.fraccion.primaSRT" maxlength="14" cssStyle="width:70%"/>
								</fieldset>
								<div>
									<div>
										<input type="button" class="mboton" name="Datos Generales"
											value="<spring:message code="label.datos.generales" />"
											 onclick="navegar('${contextpath}/sujetoObligado/datosGenerales')"></input>
									</div>
									<div>
										<input type="button" class="mboton" name="Representante legal"
											value="<spring:message code="label.representante.legal" />"
											onclick="navegar('${contextpath}/representanteLegal', '<%=TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo().intValue()%>')"></input>
									</div>
									<c:if test="${!bFisica}">
										<div>
											<input type="button" class="mboton" name="Socios"
												value="<spring:message code="label.socios" />"
												onclick="navegar('${contextpath}/socios', '<%=TipoTramiteEnum.ACTUALIZACION_SOCIO.getCodigo().intValue()%>')"></input>
										</div>
									</c:if>
									<div>
										<input type="button" class="mboton" name="tramites"
											value="<spring:message code="label.modificacion.srt" />"
											onclick="abrirTramites()"></input>
									</div>
									<div>
										<input type="button" class="mboton" name="CentroTrabajo"
											value="<spring:message code="label.centro.trabajo" />"
											onclick="navegar('${contextpath}/centroTrabajo')"></input>
									</div>
									<input type="hidden" id="numSolicitud" name="numSolicitud"/>
								</div>
								<div style="display: table-row !important;">
									<fieldset class="fsInterno" >
										<label>
											Buscar:
										</label>
										<input id="txtBuscar" name="txtBuscar" maxlength="50"/>
									</fieldset>
									<table id="gridSolicitudes" style="width: 100%; vertical-align: top;">
										<thead>
										</thead>
										<tbody style="width: 100%;">
										</tbody>
										<tfoot>
										</tfoot>
									</table>
									<div class="derecha">
										<input type="button" class="mboton" name="ConsultarRL"
											onclick="validaSeleccionSol()"
											value="<spring:message code="label.ver.detalle" />"/>
									</div>
								</div>
							</fieldset>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="tramites" class="demo" title="Tr&aacute;mites">
	<ol id="selectable">
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.CLASIFICACION_D.name()%>">Cambio de actividad econ&oacute;mica</li>
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.CLASIFICACION_E.name()%>">Cambio por disposici&oacute;n de Ley, o del RACERF</li>
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.CLASIFICACION_F.name()%>">Incorporaci&oacute;n de actividades</li>
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.CLASIFICACION_J.name()%>">Compra de Activos</li>
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.CLASIFICACION_K.name()%>">Comodato</li>
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.CLASIFICACION_L.name()%>">Enajenaci&oacute;n</li>
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.CLASIFICACION_M.name()%>">Arrendamiento</li>
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.CLASIFICACION_N.name()%>">Fideicomiso traslativo</li>
	</ol>
</div>


<div id="tramitesClasificacionExistentes" class="demo" title="Tr&aacute;mites">
	<p style="float: left; margin: 10 10px 10px 10;">
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"> </span>
		Existe un trámite de modificación al SRT en curso espere a su conclusi&oacute;n para iniciar un nuevo tr&aacutemite.
	</p>
</div>

<div id="dgErrorSinSeleccion" title="Debe seleccionar un elemento">
	<p style="float: left; margin: 10 10px 10px 10;">
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"> </span>
		No se ha seleccionado ning&uacute;n registro para ejecutar esta acci&oacute;n.
	</p>
</div>

<div id="dgDialogoNoProcedeTramite" title="Tramite no procede">
	<p style="float: left; margin: 10 10px 10px 10;">
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"> </span>
		Actualmente existe un tr&aacute;mite del mismo tipo en curso, por favor espere a que el tr&aacute;mite sea conclu&iacute;do para iniciar uno nuevo.
	</p>
</div>