<%@ include file="../general/taglibs.jsp"%>
<%@taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<%@ include file="/WEB-INF/views/general/fileUpload/FileUploadImport.jsp" %>
<%@ include file="../general/llenaTipoTramite.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/derechohabiente/js/prorroga/rechazarProrroga.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/derechohabiente/js/prorroga/prorrogaPermanente.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/derechohabiente/js/prorroga/resultado.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/fileRead/fileRead.js" htmlEscape="true" />"></script>

<script>
	var modo = "<%=session.getAttribute("modo")%>";
	$(document).ready(
		function() {
			if(modo == "registro")
				loadFileUpload(31);
		}	
	);
		var tipoTramite = 31;
		
		var idTramite = "<%= session.getAttribute("idTramite") %>"; 		
		var documentos = "<%=session.getAttribute("documentos") %>";
		var idPersona = "<%=session.getAttribute("idPersona") %>";
</script>

<div class="form-comment"><br>
	<h4 align="center">
		<strong>
			<spring:message code="titulo.prorrogaFallecimiento" />
		</strong>
	</h4>
	<br>
	<form:form commandName="prorrogas" id="frmRegistroProrroga" name="frmRegistroProrroga" method="POST">
		<spring:hasBindErrors name="*">
			<c:forEach items="${messages}" var="errorMessage">
				<li><c:out value="${errorMessage}" /> <br />
				</li>
			</c:forEach>
		</spring:hasBindErrors> 
	
		<jsp:include page="../prorrogas/grupoFamiliar.jsp"></jsp:include>
		<jsp:include page="../derechohabientes/datosPatron.jsp"></jsp:include>
	
		<form:input path="idSolicitud" type="hidden" value="${idSolicitud}"/>
		<input type="hidden" value="0" id="mostrarMensajeActas"/>
		<fieldset id="componentes">
			<legend>
				<spring:message code="label.prorrogaEstudios.titulo" />
			</legend>
			<table>
				<tr id="fechaInicioDiv">
					<td><spring:message code="label.fechaDefuncion"></spring:message> :</td>
					<td>
						<input type="text" id="fechaInicio" name="fechaInicio" readonly="readonly" value='<fmt:formatDate pattern="dd/MM/yyyy" value ="${prorroga.fechaInicioProrroga}"/>'/>
					</td>					
				</tr>
				<tr>
					<td><spring:message code="label.observaciones"></spring:message> : </td>
					<td><textarea id="observaciones" name="observaciones" rows=""
						cols="">${prorroga.tramite.observacion}</textarea></td>			
				</tr>		
			</table>
		</fieldset>
	</form:form>
	<br>
	<c:if test="${modo == 'registro'}">
		<div id="cagarDocProbDiv"><jsp:include
			page="/WEB-INF/views/general/fileUpload/fileUpload.jsp" /></div>
		<div id="msgDocumentosProb"
			title="<spring:message code="titulo.mensajeAviso"/>"
			style="display: none"><spring:message code="msgDocumentosProb" />
		</div>
	</c:if>

	<div id="docProbTramDiv"></div>
	<br>

	<form>
		<div id="botones" align="center">
			<input type="button" id="aceptar" value='<spring:message code="boton.aceptar"/>' class="mboton" /> 
			<input id="cancelar" type="button" value="<spring:message  code="boton.cancelar"/>" class="mboton" />
		</div>
			
		<div id="botonesAut" align="center">
			<input type="button" id="aceptarAut" value='<spring:message code="boton.aceptar"/>' class="mboton" /> 
			<input 	id="cancelarAut" type="button" value="<spring:message  code="boton.cancelar"/>" class="mboton" />
			<input  id="regresarAut"  type="button"  value='<spring:message  code="button.regresar"/>'  class="mboton" />
		</div>
	</form>	
</div>
