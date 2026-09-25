<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ include file="../general/taglibs.jsp"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!--Empieza contenido-->

<%@page import="mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisEnum"%>
<%@page import="mx.gob.imss.ctirss.delta.model.clasificacion.CodigoRolClasificacion"%>
<%@page import="mx.gob.imss.ctirss.delta.gestion.clasificacion.web.utils.Constantes"%>
<%@page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal"%>

<script>

ROL_JEFE_DEPTO = <%=CodigoRolClasificacion.JEFE_DEPTO_DEL.getCodigo()%>,
ROL_JEFE_OFICINA = <%=CodigoRolClasificacion.JEFE_OFICINA_DEL.getCodigo()%>,
ROL_JEFE_VENT = <%=CodigoRolClasificacion.VENTANILLA_CLASIF_DEL.getCodigo()%>,
ROL_NORMAT = <%=CodigoRolClasificacion.NORMATIVO_DEL.getCodigo()%>,
ROL_NORM_CENTRAL = <%=CodigoRolClasificacion.NORMATIVO_CENTRAL.getCodigo()%>,
CANCELADO_GCE = <%=EstatusAnalisisEnum.CANCELADO_POR_NUEVO_TRAMITE_DE_GCE.getClave()%>,
CANCELADO_BAJA = <%=EstatusAnalisisEnum.CANCELADO_POR_BAJA_PATRONAL.getClave()%>
;


</script>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/consulta/analisisConsultaDictamen.js" htmlEscape="true" />"></script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="grupoTramite" value="<%=session.getAttribute(\"grupoTramite\")%>" />
<c:set var="tipoMovimientoGrupoTramite" value="<%=session.getAttribute(\"tipoMovimientoGrupoTramite\")%>" />

<c:set var="vieneDetalle" value="${vieneDetalle}"/>
<input type="hidden" id="regPatConservar" name="regPatConservar" value="${regPatConservar}"/>
<input type="hidden" id="idEjercicioConservar" name="idEjercicioConservar" value="${idEjercicioConservar}"/>
<input type="hidden" id="delegacionConservarH" name="delegacionConservarH" value="${delegacionConservar}"/>
<input type="hidden" id="subdelegacionConservarH" name="subdelegacionConservarH" value="${subdelegacionConservar}"/>

<!-- Obtenemos el rol del usuario firmado -->
<c:set var="rol" value="${usuario.perfilUsuario}"/>
<input type="hidden" id="rol" name="rol" value="${rol.idPerfilUsuario}"/>

<!-- Delegacion y subdelegacion del usuario -->
<input type="hidden" id="subdelegacionUser" name="subdelegacionUser" value="${usuario.usuarioFuncionario.subdelegacion.id}"/>
<input type="hidden" id="delegacionUser" name="delegacionUser" value="${usuario.usuarioFuncionario.delegacion.id}"/>


<div >
	<form:form method="POST" action="${contextpath}/solicitud/detalle" modelAttribute="dictamen" id="formDictamen">
		<form:hidden path="cveIdPatronDictamen"/>
		<form:hidden path="cveIdPatronSujetoObligado"/>
		<form:hidden path="idSolicitud"/>
		<form:hidden path="registroPatronal"/>
		<form:hidden path="idEjercicio"/>
		<form:hidden path="rfc"/>
	</form:form>
</div>

<div class="site_position_center">
    <div class="page_holder_no_height">
		<div id="mensaje"></div>
	</div>
</div>
	
<div class="page_holder_no_height">
	<div class="post_entry_wide no-border">
		<!--Aquí pega tu código-->
		<div class="form-comment">
			
			<form id="formFiltros">
				<fieldset>
					<legend>
						<strong><spring:message code="label.filtros.busqueda" /></strong>
					</legend>

					<fieldset class="fsInterno">
						<!-- elemento SPAN para mostrar el error del campo especifico. -->
						<span id="strPeriodoInicioError" class=" hiddenElement error"></span>
						
						<label class="wide">Periodo dictaminado:</label> 
						
						<select id="idEjercicio" name="idEjercicio">
							<c:forEach var="ejercicioDictamen" items="${lstEjercicios}">
								<c:if test="${ejercicioDictamen.idEjercicio==5}">
								<option value="${ejercicioDictamen.idEjercicio}" selected="selected">${ejercicioDictamen.descripcion}</option>
								</c:if>
								<c:if test="${ejercicioDictamen.idEjercicio!=5}">
								<option value="${ejercicioDictamen.idEjercicio}">${ejercicioDictamen.descripcion}</option>
								</c:if>
							</c:forEach>
						</select>

					</fieldset>
					
					<fieldset class="fsInterno">
						<span id="registroPatronalError" class=" hiddenElement error"></span>
						<label class="wide"><spring:message code="label.detalle.registro.patronal" />:</label> 
						<input name="registroPatronal" id="registroPatronal" onchange="this.value=this.value.toUpperCase();" style="width: 100px" type="text" maxlength="10" />
					</fieldset>
					
					<fieldset class="fsInterno">
						<label class="wide "><spring:message code="label.detalle.estatus" />:</label> 
						
						<select id="idStatus" name="idStatus">
							<option value=""><spring:message code="label.filtros.combos.todos" /></option>
								<c:forEach var="estatusAnalisis" items="${lstEstatusAnalisis}">
									<option value="${estatusAnalisis.cveIdEstatus}">${estatusAnalisis.desEstatus}</option>
								</c:forEach>  
						</select> 
																	  
					</fieldset>
					
					<!-- <fieldset class="fsInterno hiddenElement" id="delegacionHolder">-->
					<fieldset class="fsInterno hiddenElement" id="delegacionHolder">
						<span id="delegacion" class=" hiddenElement error"></span>
						<label class="wide"><spring:message code="label.filtros.busqueda.delegacion" />:</label> 
		
						<combo:creaCombo idHtml="idDelegacion" idHtmlContenedor="formFiltros" 
							entidad="mx.gob.imss.ctirss.delta.persistence.DicDelegacion" mostrarSoloActivos="true"/>
							
					</fieldset>
					
					<fieldset class="fsInterno hiddenElement" id="subdelegacionHolder">
						<label class="wide"><spring:message code="label.filtros.busqueda.subdelegacion" />:</label>
						<combo:creaCombo entidad="mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion" 
		                     idHtml="idSubDelegacion" 
		                     entidadPadre="dicDelegacion.cveIdDelegacion"
		                     idHtmlPadre="idDelegacion"
		                     idHtmlContenedor="formFiltros"
		                     mostrarSoloActivos="true"/>  
					</fieldset>
					
					
					<div style="text-align: right; float: right;">
						<c:if test="${menuDecoration != 2}">
						<input type="button" value="Buscar" class="mboton" style="width: 120px;" id="boton" />
						</c:if>
						<c:if test="${menuDecoration == 2}">
						<input type="button" value="Generar reporte" class="mboton" style="width: 160px;" id="boton" />
						</c:if>
					</div>
				</fieldset>
				
			</form>
		</div>
		<c:if test="${menuDecoration != 2}">
		<div>
			<table style="width: 100%"  id="tableSolicitudesConcluidas">
				
			</table>
		</div>
		</c:if>
		
	</div>
</div>
