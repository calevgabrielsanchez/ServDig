<%@ include file="../general/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/general/fileUpload/FileUploadImport.jsp" %>
<%@ include file="../general/llenaTipoTramite.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/derechohabiente/js/prorroga/rechazarProrroga.js" htmlEscape="true" />"></script>

<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/prorroga/prorrogaTemporal.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/derechohabiente/js/prorroga/resultado.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/fileRead/fileRead.js" htmlEscape="true" />"></script>

<script>

	var modo = "<%=session.getAttribute("modo")%>";
		$(document).ready(
		function() {
			if(modo == "registro")
				loadFileUpload(32);
		}	
	);
		var tipoTramite = 32;
		
		var idTramite = "<%= session.getAttribute("idTramite") %>"; 		
		var documentos = "<%=session.getAttribute("documentos") %>";
		var idPersona = "<%=session.getAttribute("idPersona") %>";
</script>


<div class="form-comment">
	<br>
	<div id="mensajeConfirmacion"></div>
	
	
	
	<h4 align="center"><strong><spring:message code="titulo.prorrogaPension" /></strong></h4>
	
<c:choose>
<c:when test="${empty errores}">
	
	<jsp:include page="../prorrogas/grupoFamiliar.jsp"></jsp:include>
	<jsp:include page="../derechohabientes/datosPatron.jsp"></jsp:include>
		
	<form:form commandName="datos" id="frmRechazo" name="frmRechazo" method="POST" action="#">
			<form:input path="rechazo.idSolicitud" type="hidden"  value="${idSolicitud}" />			
			<form:input path="rechazo.idPersona"   type="hidden"  value="${idPersona}" />			
			<form:input path="rechazo.idTipoTramite" type="hidden"  value="${tipoTramite}" />
			<form:input path="rechazo.idTramite"   type="hidden"  value="${idTramite}" />	
			<form:input path="rechazo.observaciones"   type="hidden" />	
			<form:input path="rechazo.idRazonRechazo"   type="hidden" />		
		</form:form>

	<form id="frmRegistroProrroga">
	
		
		
		
		<spring:hasBindErrors name="*">   
                    <c:forEach items="${messages}" var="errorMessage">   
                        <li>   
                            <c:out value="${errorMessage}" />   
                            <br />   
                        </li>   
                    </c:forEach>   
                </spring:hasBindErrors>

		<input type="hidden" value="${idSolicitud}" id="idSolicitud" name="idSolicitud"/>
		<input type="hidden" value="0" id="mostrarMensajeActas"/>
			
		<fieldset id="componentes" >
			<legend><spring:message code="label.prorrogaEstudios.titulo"/></legend>
			<table>
				<c:if test="${modo == 'validar'}">
					<tr>
							<td><spring:message code="label.fechaInicio"></spring:message>:</td>
							<td>
								<input type="text" id="fechaInicio" name="fechaInicio" disabled="disabled" value='<fmt:formatDate pattern="dd/MM/yyyy" value ="${prorroga.fechaInicioProrroga}"/>'/>
							</td>
					</tr>
				</c:if>	
				<tr>
					<td><spring:message code="label.observaciones"></spring:message> : </td>
					<td><textarea id="observaciones" name="observaciones" rows=""
						cols="">${prorroga.tramite.observacion}</textarea></td>	
				</tr>
			</table>
			</fieldset>
			
		</form>	
			
		<br>
		<c:if test="${modo == 'registro'}">
			<div id="cagarDocProbDiv">
				<jsp:include page="/WEB-INF/views/general/fileUpload/fileUpload.jsp"/>
			</div>	
			<div id="msgDocumentosProb" title ="<spring:message code="titulo.mensajeAviso"/>" style="display:none"> 
				<spring:message code="msgDocumentosProb"/>		
			</div>
		</c:if>	
		<div id="docProbTramDiv"></div>
		<br>
		<form>
		<div align="center" id="botones">	
				<input id="aceptar" type="button" value='<spring:message code="boton.aceptar"/>' class="mboton" />
				<input id="cancelar" type="button" value="<spring:message code="boton.cancelar"/>" class="mboton" />

		</div>
			
		<div id="botonesAut" align="center">
			<input type="button" id="aceptarAut" value='<spring:message code="boton.aceptar"/>' class="mboton" /> 
			<input 	id="cancelarAut" type="button" value="<spring:message  code="boton.cancelar"/>" class="mboton" />
			<input  id="regresarAut"  type="button"  value='<spring:message  code="button.regresar"/>'  class="mboton" />
		</div>
			
	</form>
		
</c:when>
<c:otherwise>
<br>
	<div class="ui-widget-content ui-corner-all">
		<div class="ui-state-error ui-corner-all" align="center">
			<div class="ui-icon ui-icon-alert"></div>
			<p class="ui-helper-reset ui-state-error-text"><spring:message code="${errores}" /></p>
			<h3><span style="font-size:.7em" class="ui-helper-reset ui-state-error-text">Detalle: ${error}</span></h3>
		</div>
		
	</div>
		<br>
		<div align="center" >
		<form>
			<table>
				<tr>
					<td>
						<input  id="regresar"  type="button"  value='<spring:message  code="button.regresar"/>'  class="mboton" />
					</td>	
				</tr>
			</table>
		</form>	
		</div>
	
</c:otherwise>
</c:choose>	
			
		
</div>
