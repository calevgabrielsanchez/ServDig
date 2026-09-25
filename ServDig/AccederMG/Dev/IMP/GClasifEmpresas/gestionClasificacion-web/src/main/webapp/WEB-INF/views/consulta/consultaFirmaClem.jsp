<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ include file="../general/taglibs.jsp"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!--Empieza contenido-->

<%@page import="mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisEnum"%>
<%@page import="mx.gob.imss.ctirss.delta.model.clasificacion.CodigoRolClasificacion"%>
<%@page import="mx.gob.imss.ctirss.delta.gestion.clasificacion.web.utils.Constantes"%>
<%@page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal"%>

<script>

$(document).ready(function() {
	$( "#strPeriodoInicio" ).datepicker();
	$( "#strPeriodoInicio" ).datepicker( "option", "dateFormat", 'dd/mm/yy' );
	$( "#strPeriodoFin" ).datepicker();
	$( "#strPeriodoFin" ).datepicker( "option", "dateFormat", 'dd/mm/yy' );
	
});

</script>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/consulta/consultaFirmaClem.js" htmlEscape="true" />"></script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<!-- Obtenemos el rol del usuario firmado -->
<c:set var="perfil" value="${usuario.perfilUsuario}"/>
<c:set var="rolU" value="${perfil.idPerfilUsuario}"/>

<c:set var="rolDDD" value="<%=CodigoRolClasificacion.DELEGADO_DEL.getCodigo()%>"/>
<c:set var="rolSBS" value="<%=CodigoRolClasificacion.SUBDELEGADO_SUBDEL.getCodigo()%>"/>
<c:set var="rolJOS" value="<%=CodigoRolClasificacion.JEFE_OFICINA_COBROS_SUBDEL.getCodigo()%>"/>

<input type="hidden" id="rolDDD" name="rolDDD" value="${rolDDD}"/>
<input type="hidden" id="rolSBS" name="rolSBS" value="${rolSBS}"/>
<input type="hidden" id="rolJOS" name="rolJOS" value="${rolJOS}"/>

<c:set var="mensaje" value="${mensaje}"/>
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<input type="hidden" id="rolU" name="rolU" value="${rolU}"/>

<input type="hidden" id="subdelegacionUser" name="subdelegacionUser" value="${usuario.usuarioFuncionario.subdelegacion.id}"/>
<input type="hidden" id="delegacionUser" name="delegacionUser" value="${usuario.usuarioFuncionario.delegacion.id}"/>
<input type="hidden" id="curp"  value="${usuario.usuario}"/>

<div class="site_position_center">
    <div class="page_holder_no_height">
		<div id="mensaje">${mensaje}</div>
	</div>
</div>

<div class="site_position_center">
    <div class="page_holder_no_height">
		<c:forEach items="${messageContext.allMessages}" var="message">
		    <c:if test="${message.severity eq 'Info'}">
		      <div class="info-msg">${message.text}</div>
		    </c:if>
		    <c:if test="${message.severity eq 'Error'}">
		      <div class="error-msg">${message.text}</div>
		    </c:if>
		</c:forEach>
	</div>
</div>
	
<div class="page_holder_no_height">
	<div class="post_entry_wide no-border">

		<div class="form-comment">
			
			<form id="formFiltros">
				<fieldset>
					<legend>
						<strong><spring:message code="label.filtros.busqueda" /></strong>
					</legend>

					<fieldset class="fsInterno">
						<!-- elemento SPAN para mostrar el error del campo especifico. -->
						<span id="strPeriodoInicioError" class=" 
						hiddenElement error"></span>
						<span id="strPeriodoFinError" class=" hiddenElement error"></span>
						
						<label class="wide"><spring:message code="label.filtros.busqueda.periodo.determinado" />:</label> 
						<input name="strPeriodoInicio" id="strPeriodoInicio" style="width: 100px;  text-align: center" type="text" maxlength="10" title="dd/MM/aaaa"/> 
						<label class="wide" for="periodoFin" style="width: 20px"> a</label> 
						<input name="strPeriodoFin" id="strPeriodoFin" style="width: 100px;  text-align: center" type="text" maxlength="10" title="dd/MM/aaaa"/>
					</fieldset>

					<fieldset class="fsInterno">
						<label class="wide"><spring:message code="label.filtros.busqueda.tipo.plantilla" />:</label>
						
						<c:if test="${rolU == rolDDD}">
							<input id="tipoClemInscDel" class="submit_no_margin" name="tipoClem" type="radio" value="<%=Constantes.CLEM_INSCRIP_DEL%>" CHECKED/> 
							<label for="tipoClemInscDel"><spring:message code="label.filtros.busqueda.radio.insc" /></label> 
							<input id="tipoClemModDel" class="submit_no_margin" name="tipoClem" type="radio" value="<%=Constantes.CLEM_MOD_DEL%>"/> 
							<label for="tipoClemModDel"><spring:message code="label.filtros.busqueda.radio.mod" /></label> 
						</c:if>

						<c:if test="${rolU == rolSBS || rolU == rolJOS}">
							<input id="tipoClemInscSubDel" class="submit_no_margin" name="tipoClem" type="radio" value="<%=Constantes.CLEM_INSCRIP_SUBDEL%>" CHECKED/>							 
							<label for="tipoClemInscSubDel"><spring:message code="label.filtros.busqueda.radio.insc" /></label>
							<input id="tipoClemModSubDel" class="submit_no_margin" name="tipoClem" type="radio" value="<%=Constantes.CLEM_MOD_SUBDEL%>"/>							 
							<label for="tipoClemModSubDel"><spring:message code="label.filtros.busqueda.radio.mod" /></label>
						</c:if>
												
					</fieldset>

					<div style="text-align: right; float: right;">
						<input type="button" value="Buscar" class="mboton" style="width: 120px;" id="boton" />
					</div>
<!-- 					
					<div style="text-align: right; float: right;">
						<input type="button" value="Ver seleccionados" class="mboton" style="width: 120px;" id="verSeleccionados" />
					</div>
 -->					
				</fieldset>
				
			</form>
		</div>
		<div>
			<table style="width: 100%"  id="tableSolicitudesConcluidas">
				
			</table>
		</div>

		<div class="site_position_center">
		    <div class="page_holder_no_height">
				<div id="divBtn">
					<form:form id="formFirmarClem" modelAttribute="firmaClemDTO" method="post">
						<input type="button" class="mboton" style="width: 120px;" id="btnFirmar" value="Firmar" onclick="ingresarRFC()"/>
						<input name="cveSolClems" id="paramrfcV"  type="hidden"/>
						<input name="solicitudesFirma" id="solicitudesFirma"  type="hidden"/>
					</form:form>
				</div>
			</div>
		</div>

	<div id="tvesModal" class="modalContainer">
		<div class="modal-content">
			<span class="close">×</span>
			<span id="strRfcError" class=" hiddenElement error"></span>
			<legend><strong>Ingrese el RFC con el que se firmaran los documentos:</strong></legend>
			<label class="wide">RFC:</label> 
			<input  id="rfcV" style="width: 100px;  text-align: center" type="text" maxlength="13"/>
			<input type="button" class="mboton" style="width: 120px;" id="btnRFC" value="Aceptar" onclick="enviarAFirma()"/>
			
		</div>
	</div>
	
	<!--  	<div class="site_position_center">
		    <div class="page_holder_no_height">
				<div id="divBtn">
					<input type="button" class="mboton" style="width: 170px;" id="btnFirmarD" value="Procesar Datos Firma" onclick="procesarRespuestaFirmaDigital()"/>
				</div>
			</div>
		</div>  -->


	</div>
</div>