<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<%@ include file="/WEB-INF/views/general/fileUpload/FileUploadImport.jsp"%>
<%@ include file="../general/llenaTipoTramite.jsp"%>


<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/derechohabiente/js/prorroga/rechazarProrroga.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/derechohabiente/js/prorroga/registroProrrogas.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/derechohabiente/js/prorroga/resultado.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/fileRead/fileRead.js" htmlEscape="true" />"></script>



<div class="form-comment"><br>
	<c:set var="now" value="<%=new java.util.Date()%>" />
	<input id="fechaDeHoy" type="hidden" value='<fmt:formatDate pattern="dd/MM/yyyy" value ="${now}" />'/>
	<input type="hidden" value="0" id="mostrarMensajeActas"/>
	<br>
			
	<div id="tituloVigenciaPermanente" style="display:none"><h4 align="center"><strong><spring:message code="titulo.prorrogaFallecimiento" /></strong></h4></div>
	<div id="tituloAcuerdo" style="display:none"><h4 align="center"><strong><spring:message code="titulo.prorrogaAcuerdo" /></strong></h4></div>
	<div id="tituloObtetrico" style="display:none">
	<c:if test="${hijo.parentesco.idParentesco == 3}">
		<h4 align="center"><strong><spring:message code="titulo.prorrogaObstetricosBeneficiaria" /></strong></h4>
	</c:if>
	<c:if test="${hijo.parentesco.idParentesco == 4}">
		<h4 align="center"><strong><spring:message code="titulo.prorrogaObstetricosBeneficiariaConcubina" /></strong></h4>
	</c:if>

	<c:if test="${hijo.parentesco.idParentesco == 0}">
		<h4 align="center"><strong><spring:message code="titulo.prorrogaObstetricosBeneficiariaConcubina" /></strong></h4>
	</c:if>
	</div>
	<div id="tituloEnfermedad" style="display:none"><h4 align="center"><strong><spring:message code="titulo.prorrogaIncapacidad" /></strong></h4></div>
	<div id="tituloEstudios" style="display:none"><h4 align="center"><strong><spring:message code="titulo.prorrogaEstudios" /></strong></h4></div>
	<div id="tituloLaudo" style="display:none"><h4 align="center"><strong><spring:message code="titulo.prorrogaLaudo" /></strong></h4></div>
	<div id="tituloVigenciaTemporal" style="display:none"><h4 align="center"><strong><spring:message code="titulo.prorrogaPension" /></strong></h4></div>

	
		
	<c:choose>
	<c:when test="${empty errores}">			
		<jsp:include page="../prorrogas/grupoFamiliar.jsp"></jsp:include>
		<jsp:include page="../derechohabientes/datosPatron.jsp"></jsp:include>
		
		<div class="ui-widget" id="mensajeConfirmacion" style="display:none;">
		<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
		<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span><span id="mensajeConf"></span>
		</p></div>
		</div>
	
		<input type="hidden" id="modo" name="modo" value="<c:out value="${modo}"/>"></input>
		<input type="hidden" id="documentoP" name="documentoP" value="<c:out value="${documentoP}"/>"></input>
		<input id="tipoTramite" name="tipoTramite" type="hidden" value="<c:out value="${tipoTramite}"/>"></input>
		<input id="idTramite" name="idTramite" type="hidden"  value="<c:out value="${idTramite}"/>"></input>
		
		<form:form commandName="datos" id="frmRechazo" name="frmRechazo" method="POST" action="#">
			<form:input path="rechazo.idSolicitud" type="hidden"  value="${idSolicitud}" />			
			<form:input path="rechazo.idPersona"   type="hidden"  value="${idPersona}" />			
			<form:input path="rechazo.idTipoTramite" type="hidden"  value="${tipoTramite}" />
			<form:input path="rechazo.idTramite"   type="hidden"  value="${idTramite}" />	
			<form:input path="rechazo.observaciones"   type="hidden" />	
			<form:input path="rechazo.idRazonRechazo"   type="hidden" />		
		</form:form>
		
		<c:if test="${tipoTramite eq 29 or tipoTramite eq 34}">
		<div class="ui-widget" id="divMensajeProrroga">
		<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
		<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span><span id="spanMensajeProrroga">
			<c:if test="${tipoTramite eq 29}">
			Para el tr&aacute;mite de prorroga por estudios las fechas de inicio y fin ser&aacute;n las mismas que las fechas de inicio y
			fin de periodo escolar, por lo tanto, es necesario captura el documento probatorio.
			</c:if>
			<c:if test="${tipoTramite eq 34}">
			Para el tr&aacute;mite de prorroga por servicios obst&eacute;tricos es necesario capturar el documentos probatorio, ya que de el se 
			obtendran las fechas de inicio y fin de prorroga.
			</c:if>
		</span>
		</p></div>
		</div>
		</c:if>
		
		<form:form commandName="datos" id="frmRegistroProrroga" name="frmRegistroProrroga" method="POST" action="/${mvn.web.app.root}/prorroga/guardarProrrogas">				
			<fieldset style="width: 967px" class="titulo">
				<legend><STRONG><spring:message code="label.prorrogaEstudios.titulo" /></STRONG></legend>
				<table style="width: 100%">										 						
					<tr id='comboVigencia'>
						<td>
							<spring:message code="label.tipoVigencia">
							</spring:message></td>
						<td>
						<c:if test="${modo == 'registro'}">
							<form:input path="prorroga.caracter.idCaracter" type="hidden"/>
							<combo:creaCombo idHtml="idCaracter" idHtmlContenedor="frmRegistroProrroga" 
										entidad="mx.gob.imss.ctirss.delta.persistence.DicCaracter" 
										mostrarSoloActivos = "true" />
						</c:if>				
						<c:if test="${modo == 'validar'}">
							<input id="caracter" name="caracter" readonly="readonly" value="${prorroga.caracter.descripcion}"/>
						</c:if>
						</td>
					</tr>											
					<tr id="inicioP" style="display:none">						
						<td id="fInicio" style="display:none"><spring:message code="label.fechaInicio"></spring:message>:</td>
						<td id="defuncion" style="display:none"><spring:message code="label.fechaDefuncion"></spring:message>:</td>
						<td>
							<form:input path="prorroga.fechaInicioProrroga" type="hidden" value ="${prorroga.fechaInicioProrroga}" />
							<input id="miFechaInicio" name="miFechaInicio" type="text" readonly="readonly" 
							value='<fmt:formatDate pattern="dd/MM/yyyy" value ="${prorroga.fechaInicioProrroga}" />' style="width: 80px"/>
						</td>
					</tr>																							
					<tr id="fechfin">
						<td><spring:message code="label.fechaFin"></spring:message>:</td>
						<td>
							<form:input path="prorroga.fechaFinProrroga" type="hidden" value ="${prorroga.fechaFinProrroga}"/>
							<input id="miFechaFin" name="miFechaFin" type="text" readonly="readonly"  
							value='<fmt:formatDate pattern="dd/MM/yyyy" value ="${prorroga.fechaFinProrroga}"/>' style="width: 80px"/>
						</td>					
					</tr>	
					<tr id="permanente">
						<td><spring:message code="label.fechaFin"></spring:message>:</td>
						<td>PERMANENTE</td>	
					</tr>																							
					<tr>
						<td><spring:message code="label.observaciones"></spring:message> : </td>
						<td>
							<textarea id="observaciones" name="observaciones" rows="" cols="">${prorroga.tramite.observacion}</textarea>
							<form:textarea path="prorroga.tramite.observacion" rows="" cols="" value="${prorroga.tramite.observacion}"/>
						</td>			
					</tr>											
				</table>
			</fieldset>
		</form:form>	
		<br>
		<c:if test="${modo == 'registro'}">
			<div id="cagarDocProbDiv">
				<jsp:include page="/WEB-INF/views/general/fileUpload/fileUpload.jsp"/>
			</div>	
			<div id="msgDocumentosProb" title ="<spring:message code="titulo.mensajeAviso"/>" style="display:none"> 
				<spring:message code="msgDocumentosProb"/>		
			</div>
		</c:if>
		<br>	
		<div id="docProbTramDiv"></div>
			<br>
			<form>
				<div id="botones" align="center">
					<input id="aceptar"  type="button" value='<spring:message code="boton.aceptar"/>' class="mboton" /> 
					<input id="cancelar" type="button" value="<spring:message  code="boton.cancelar"/>" class="mboton" />
				</div>
				<div id="botonesAut" align="center">
					<input  id="aceptarAut"  type="button"  value='<spring:message  code="boton.aceptar"/>'  class="mboton" /> 
					<input 	id="cancelarAut" type="button"  value='<spring:message  code="boton.cancelar"/>' class="mboton" />
					<input  id="regresarAut" type="button"  value='<spring:message  code="button.regresar"/>'  class="mboton" />
					
				</div>
			</form>	
			<br>
			</c:when>
			<c:otherwise>
			<br>
			<br>
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