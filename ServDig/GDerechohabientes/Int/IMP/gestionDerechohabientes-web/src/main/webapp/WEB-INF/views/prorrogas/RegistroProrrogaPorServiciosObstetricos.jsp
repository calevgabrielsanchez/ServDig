<%@ include file="../general/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/general/fileUpload/FileUploadImport.jsp" %>
<%@ include file="../general/llenaTipoTramite.jsp"%>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/derechohabiente/js/prorroga/rechazarProrroga.js" htmlEscape="true" />"></script>

<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/prorroga/prorrogaObstetricos.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/prorroga/generarSav011.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/derechohabiente/js/prorroga/resultado.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/fileRead/fileRead.js" htmlEscape="true" />"></script>

<script>
		
		var tipoTramite = 34;
		var modo = "<%=session.getAttribute("modo")%>";
		var idTramite = "<%= session.getAttribute("idTramite") %>"; 		
		var documentos = "<%=session.getAttribute("documentos") %>";
		var idPersona = "<%=session.getAttribute("idPersona") %>";
		
</script>

<div class="form-comment">


	<br>
	<div id="mensajeConfirmacion"></div>
	<br>
	
	<c:if test="${hijo.parentesco.idParentesco == 3}">
		<h4 align="center"><strong><spring:message code="titulo.prorrogaObstetricosBeneficiaria" /></strong></h4>
	</c:if>
	<c:if test="${hijo.parentesco.idParentesco == 4}">
		<h4 align="center"><strong><spring:message code="titulo.prorrogaObstetricosBeneficiariaConcubina" /></strong></h4>
	</c:if>
	
	<c:if test="${hijo.parentesco.idParentesco == 0}">
		<h4 align="center"><strong><spring:message code="titulo.prorrogaObstetricosBeneficiariaConcubina" /></strong></h4>
	</c:if>
		<c:if test="${hijo.parentesco.idParentesco == 7}">
		<h4 align="center"><strong><spring:message code="titulo.prorrogaObstetricosBeneficiariaPersonaUnionCivil" /></strong></h4>
	</c:if>
	
<c:choose>
<c:when test="${empty errores}">

	<script>
		var modo = "<%=session.getAttribute("modo")%>";
	
		$(document).ready(
		function() {
			if(modo == "registro")
				loadFileUpload(34);
		}	
	);
		var tipoTramite = 34;
		
		var idTramite = "<%= session.getAttribute("idTramite") %>"; 		
		var documentos = "<%=session.getAttribute("documentos") %>";
		var idPersona = "<%=session.getAttribute("idPersona") %>";
		
</script>
		
	<jsp:include page="../prorrogas/grupoFamiliar.jsp"></jsp:include>
	<jsp:include page="../derechohabientes/datosPatron.jsp"></jsp:include>
	
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
				
				<tr id="fInicio">
						<td><spring:message code="label.fechaInicio"></spring:message>:</td>
						<td>
							<input type="text" id="fechaInicio" name="fechaInicio" readonly ="readonly" value='<fmt:formatDate pattern="dd/MM/yyyy" value ="${prorroga.fechaInicioProrroga}"/>'/>
						</td>
				</tr>
				<tr id="fFin">
						<td><spring:message code="label.fechaFin"></spring:message>:</td>
						<td>
							<input type="text" id="fechaFin" name="fechaFin" disabled="disabled" value='<fmt:formatDate pattern="dd/MM/yyyy" value ="${prorroga.fechaFinProrroga}"/>'/>
						</td>
				</tr>
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
			<input id="cancelar" type="button" value='<spring:message code="boton.cancelar"/>' class="mboton" />
			<input  type="button" value="Imprimir SAV 011" class="mboton" onclick="generarSAV011Obst(${idPersona});"/>
		</div>	
	
		<div id="botonesAut" align="center">
			<input type="button" id="aceptarAut"     value='<spring:message code="boton.aceptar"/>' class="mboton" /> 
			<input 	id="cancelarAut" type="button"   value="<spring:message  code="boton.cancelar"/>" class="mboton" />
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
