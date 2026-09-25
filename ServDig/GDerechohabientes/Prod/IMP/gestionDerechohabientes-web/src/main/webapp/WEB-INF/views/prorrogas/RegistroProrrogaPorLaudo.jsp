<%@ include file="../general/taglibs.jsp"%>
<%@ include file="../general/llenaTipoTramite.jsp"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<%@ include
	file="/WEB-INF/views/general/fileUpload/FileUploadImport.jsp"%>
	
	<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/derechohabiente/js/prorroga/rechazarProrroga.js" htmlEscape="true" />"></script>


<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<!--  <script type="text/javascript"
	src="<spring:url value="/static/resources/derechohabiente/js/prorroga/validadorLaudo.js" htmlEscape="true" />"></script>-->

<script type="text/javascript"
	src="<spring:url value="/static/resources/derechohabiente/js/prorroga/prorrogaLaudo.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/derechohabiente/js/prorroga/resultado.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/fileRead/fileRead.js" htmlEscape="true" />"></script>

<script>
	
	var modo = "<%=session.getAttribute("modo")%>";

	$(document).ready(function() {
		if(modo == "registro")
			loadFileUpload(35);
	});
	
	var tipoTramite = 35;
	
	var idTramite = "<%= session.getAttribute("idTramite") %>"; 		
	var documentos = "<%=session.getAttribute("documentos") %>";
	var idPersona = "<%=session.getAttribute("idPersona") %>"; 	
</script>


<div class="form-comment"><br>
<div id="mensajeConfirmacion"></div>
<br>
<h4 align="center"><strong><spring:message code="titulo.prorrogaLaudo" /></strong></h4>


<form id="frmRegistroProrroga">

<jsp:include page="../prorrogas/grupoFamiliar.jsp"></jsp:include>
<jsp:include page="../derechohabientes/datosPatron.jsp"></jsp:include>

<spring:hasBindErrors name="*">
	<c:forEach items="${messages}" var="errorMessage">
		<li><c:out value="${errorMessage}" /> <br />
		</li>
	</c:forEach>
</spring:hasBindErrors> 

<input type="hidden" value="${idSolicitud}" id="idSolicitud" name="idSolicitud"/>
<input type="hidden" value="0" id="mostrarMensajeActas"/>

<fieldset id="componentes"><legend><spring:message
	code="label.prorrogaEstudios.titulo" /></legend>
<table>
	<tr>
		<td><spring:message code="label.tipoVigencia">
		</spring:message></td>
		<td>
			<c:if test="${modo == 'registro'}">
				<combo:creaCombo idHtml="idCaracter" idHtmlContenedor="frmRegistroProrroga" 
							entidad="mx.gob.imss.ctirss.delta.persistence.DicCaracter" 
							mostrarSoloActivos = "true" />
			</c:if>				
			<c:if test="${modo == 'validar'}">
				<input id="caracter" name="caracter" readonly="readonly" value="${prorroga.caracter.descripcion}"/>
			</c:if>
		</td>
	</tr>
	<tr>
			<td><spring:message code="label.fechaInicio"></spring:message>:</td>
			<td>
				<input type="text" id="fechaInicio" name="fechaInicio" readonly="readonly" value='<fmt:formatDate pattern="dd/MM/yyyy" value ="${prorroga.fechaInicioProrroga}"/>'/>
			</td>
	</tr>
	<tr id="fechfin">
			<td><spring:message code="label.fechaFin"></spring:message>:</td>
			<td>
				<input type="text" id="fechaFin" name="fechaFin" readonly="readonly"  value='<fmt:formatDate pattern="dd/MM/yyyy" value ="${prorroga.fechaFinProrroga}"/>'/>
			</td>
	</tr>
	<tr>
		<td><spring:message code="label.observaciones"></spring:message>:
		</td>
		<td><textarea id="observaciones" name="observaciones" rows=""
			cols="">${prorroga.tramite.observacion}</textarea></td>
	</tr>
</table>
</fieldset>

</form>

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
<div id="botones" align="center"><input type="button" id="aceptar"
	value='<spring:message code="boton.aceptar"/>' class="mboton" /> <input
	id="cancelar" type="button"
	value="<spring:message  code="boton.cancelar"/>" class="mboton" /></div>


<div id="botonesAut" align="center">
	<input type="button" id="aceptarAut" value='<spring:message code="boton.aceptar"/>' class="mboton" /> 
	<input 	id="cancelarAut" type="button" value="<spring:message  code="boton.cancelar"/>" class="mboton" />
	<input  id="regresarAut"  type="button"  value='<spring:message  code="button.regresar"/>'  class="mboton" />
</div>	

</form>
</div>
