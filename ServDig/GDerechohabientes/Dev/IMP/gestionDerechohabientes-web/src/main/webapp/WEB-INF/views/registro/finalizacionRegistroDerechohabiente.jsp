<%@ include file="/WEB-INF/views/general/taglibs.jsp" %>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>	
<%@ include file="/WEB-INF/views/general/GuiaTramite/guiaTramiteImport.jsp" %>
<%@ include file="/WEB-INF/views/common/llenarRazonRegistro.jsp" %>
<%@ include file="/WEB-INF/views/common/llenarTipoDocumentoProbatorio.jsp" %>
<%@ include file="/WEB-INF/views/common/llenaTipoTramite.jsp" %>

<script type="text/javascript" src="<spring:url value="/static/resources/common/combosUmfMedicoConsultorio.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/common/datosUmf.js" htmlEscape="true" />"></script>
<c:if test="${requiereDocs}">
	<%@ include file="/WEB-INF/views/general/fileUpload/FileUploadImport.jsp" %>
</c:if>

<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/registroDerechohabiente/datosAdscripcionDerechohabiente.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/fileRead/fileRead.js" htmlEscape="true" />"></script>


<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<script>
		var contextPath = "<%=request.getContextPath()%>";
</script>

<div class="form-comment">
	<br><br><br>
	<!-- Se inserta el cabecero para todos los tramites -->
	<jsp:include page="/WEB-INF/views/general/encabezadoGF.jsp"/>
	<input type="hidden" value="${usuarioObj.idUmf}" id="umfSeleccionada"/>
	<input type="hidden" value="" id="turnoSeleccionado"/>
	<input type="hidden" value="" id="consultorioSeleccionado"/>
	<input type="hidden" value="${doctosNoMostrar}" id="doctosNoMostrar"/>
	<input type="hidden" value="${documentosAQuitar}" id="idsDoctosNoMostrar"/>
	<input type="hidden" value="0" id="mostrarMensajeActas"/>
	
	<!-- Formulario para el registro de derechohabientes -->
	<form:form modelAttribute="registro" action="${contextpath}/tramite/registro/finalizaSolicitud" method="POST">
	<input id="requiereDocs" type="hidden" value="${requiereDocs?1:0}"/>
	<input id="idAsignacionNss" type="hidden" value="${registro.datosAsegurado.idAsignacionNSS}"/>
	<input id="tipoTramite" type="hidden" value="${registro.tipoTramite.idTipoTramite}"/>
	<input id="idTramite" type="hidden" value="${registro.tramiteId}"/>
	<form:hidden path="fisica.idPersona"/> 
	<input type="hidden" value="${solicitudActiva.solicitudId}" id="idSolicitud"/>
	
	
	
	<!-- Datos dentro del grupo familiar -->
	<fieldset id="datosRegistro">
		<legend><strong>Datos del registro</strong></legend>
		<table>
		<tr>
			<td>
				<label class="control-label" for="tipoTramite.descripcion" style="width: 140px !important;">&nbsp;Tipo de registro : </label>
			</td>
			<td>
				<form:hidden path="fisica.idPersona"/> 
				<form:hidden path="tipoTramite.idTipoTramite"/>
				<form:input path="tipoTramite.descripcion" disabled="disabled"/>
				<form:hidden path="domicilio.codigoPostal.codigoPostal"/>
				<form:hidden path="parentesco.idParentesco"/>
				<form:hidden path="fechaCambioMedico"/>
				<form:hidden path="indSeleccionMedico" value="0"/>
				<form:hidden path="razonRegistro.idRazonRegistro"/>
				<form:hidden path="indHijosProcreados"/>
			</td>
			<td>
				<label class="control-label" for="razonRegistro.idRazonRegistro" style="width: 140px !important;"> Razon registro : </label>
			</td>
			<td>
				<form:input path="razonRegistro.descripcion" disabled="disabled"/>
			</td>
		</tr>
	</table>
	</fieldset>
	
	<div class="ui-widget" id="divMensajeMediosContaco">
		<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
		<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span><span id="mensajeUmfIntegrante">
			A continuaci&oacute;n elija los datos de adscripci&oacute;n del integrante a registrar, en caso de que ya exista un integrante
			en la UMF actual, los datos de adscripci&oacute;n seran los mismos.
		</span>
		</p></div>
	</div>
	<fieldset id="datosAdscripcion">
		<legend><strong>Servicio m&eacute;dico</strong></legend>
		<table class="page_holder_no_height" width="90%">
						<tr >
							<td>
								<label class="control-label" for="medicoEnTurno.unidadMedicaFamiliar.idUMF" style="width: 140px !important;">
									<spring:message code="label.umf" /> <span class="required">*</span>:
								</label>
							</td>
							<td colspan="3">
							    <select id="medicoEnTurno.unidadMedicaFamiliar.idUMF" style="width: 275px;" disabled="disabled" name="medicoEnTurno.unidadMedicaFamiliar.idUMF" >
									<option value="-1">--POR FAVOR SELECCIONE--</option>
								</select>
								<span id="medicoEnTurno.unidadMedicaFamiliar.idUMFError" class="error hiddenElement"></span>	
							</td>
							
						</tr>
						<tr>
							<td>
								<label class="control-label" for="medicoEnTurno.unidadMedicaFamiliar.idUMF" style="width: 140px !important;">
									<spring:message code="label.umf.delegacion" /> :
								</label>
							</td>
							<td>
								<form:hidden path="medicoEnTurno.idMedicoContultorioTurno"/>
								<form:hidden path="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.id"/>
								<form:input path="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion" cssStyle="width: 260px" disabled="true"/>
							</td>
							<td>
								<label class="control-label" for="medicoEnTurno.unidadMedicaFamiliar.idUMF" style="width: 140px !important;">
									<spring:message code="label.umf.subdelegacion" />:
								</label>
							</td>
							<td>	
								<form:hidden path="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.id"/>
								<form:input path="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.descripcion" cssStyle="width: 260px" disabled="true"/>
							</td>
						</tr>
						<tr>
							<td>
								<label class="control-label" for="medicoEnTurno.turno.idTurno" style="width: 140px !important;">
									<spring:message code="label.umfTurno" /> <span class="required">*</span>:
								</label>
							</td>
							<td>
								<select id="medicoEnTurno.turno.idTurno" style="width: 275px;" name="medicoEnTurno.turno.idTurno">
									<option value="-1">--POR FAVOR SELECCIONE--</option>
								</select>
								<span id="medicoEnTurno.turno.idTurnoError" class="error hiddenElement"></span>
							</td>
							<td>
								<label class="control-label" for="medicoEnTurno.consultorio.idConsultorio" style="width: 140px !important;">
								<spring:message code="label.consultorio" /> <span class="required">*</span> :</label>
							</td>
							<td>
								<select id="medicoEnTurno.consultorio.idConsultorio" style="width: 275px;" name="medicoEnTurno.consultorio.idConsultorio">
									<option value="-1">--POR FAVOR SELECCIONE--</option>
								</select>
								<span id="medicoEnTurno.consultorio.idConsultorioError" class="error hiddenElement"></span>
							</td>
						</tr>
						<tr>
							<td>
								<label class="control-label" for="medicoEnTurno.unidadMedicaFamiliar.idUMF" style="width: 140px !important;">
								Matr&iacute;cula m&eacute;dico :
								</label>
							</td>
							<td>
								<form:hidden path="medicoEnTurno.medicoFamiliar.idMedicoFamiliar"/>
								<form:input path="medicoEnTurno.medicoFamiliar.noMatricula" cssStyle="width: 260px" disabled="true"/>
							</td>
							<td>
								<label class="control-label" for="medicoEnTurno.unidadMedicaFamiliar.idUMF" style="width: 140px !important;">
									Especialidad :
								</label></td>
							<td>
								<form:hidden path="medicoEnTurno.medicoEspecialidad.idMedicoEspacialidad"/>
								<form:input path="medicoEnTurno.medicoEspecialidad.descripcion" cssStyle="width: 260px" disabled="true"/>
							</td>
						</tr>
						<tr>
							<td>
								<label class="control-label" for="medicoEnTurno.unidadMedicaFamiliar.idUMF" style="width: 140px !important;">
								<spring:message code="label.medicoFamiliar" />:
								</label></td>
							<td colspan="4">
								<form:input path="medicoEnTurno.medicoFamiliar.nombre" cssStyle="width: 710px" disabled="true"/>
							</td>
						</tr>
						<tr>
							<td><label class="control-label" for="medicoEnTurno.unidadMedicaFamiliar.idUMF" style="width: 140px !important;">
							
							<spring:message code="label.observaciones" /> <span class="required">*</span>: </label></td>
							<td colspan="5" >
								<form:textarea path="observacion" cols="2" style="width: 710px; height: 60px"></form:textarea>
								<span id="observacionError" class="error hiddenElement"></span>
							</td>
						</tr>
						<tr>
							<td colspan="6"><br>Los campos marcados con <span class="required">*</span> son requeridos.</td>
					</tr>
					</table>
	</fieldset>
	</form:form> <!-- Fin del formulario de registro de derechohabientes -->
	
	<c:if test="${requiereDocs}">
		<div class="ui-widget" id="divMensajeDocumentos">
			<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
				<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span><span id="mensajeDocumentos">
					El tr&aacute;mite actual requiere la captura de documentos probatorios, no podr&aacute; finalizar el tramite hasta completar
					la documentaci&oacute;n.
				</span>
				</p>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/general/fileUpload/fileUpload.jsp"/>	
		<div id="msgDocumentosProb" title ="<spring:message code="titulo.mensajeAviso"/>" style="display:none"> 
			<spring:message code="msgDocumentosProb"/>		
		</div>
	</c:if>
	<div id="docProbTramDiv"></div>
		<br>
	<br><br>
	<form>
		<table style="width: 100%">
			<tr>				
				<td>
					<div style="text-align: center">													
					<input type="button" id="registrar" class="mboton" value="<spring:message code="button.registrar"/>"/>
					<c:if test="${registro.tramiteId ne null}">
					<input type="button" id="rechazarTramite" class="mboton" value="Cancelar tr&aacute;mite"/>	
					</c:if>				
					<input type="button" id="guia" name="guia" onclick="showGuiaTramite(44,1)" value="<spring:message code="button.guiaTramite"/>" class="mboton"/>	
					</div>	
				</td>			
			</tr>		
		</table>
	</form>
</div>