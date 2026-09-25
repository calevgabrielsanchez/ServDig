<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>
<%@taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<!-- c:if test="${requiereDocumentacion}"-->
<%@ include file="/WEB-INF/views/general/fileUpload/FileUploadImport.jsp" %>
<!-- /c:if-->


<%--scripts necesarios para el nuevo componente de domicilio --%>
<script type="text/javascript" src="/gestionDomicilios-web/static/resources/js/delta/domicilios/recortado/DomicilioRecortadoCtrl.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/common/setDomicilioCommon.js" htmlEscape="true" />"></script>

<script type="text/javascript" src="<spring:url value="/static/resources/common/combosUmfMedicoConsultorio.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/cambioClinica/domicilioCambioClinica.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/cambioClinica/cambioClinica.js" htmlEscape="true" />"></script>
<!-- c:if test="${requiereDocumentacion}"-->
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/fileRead/fileRead.js" htmlEscape="true" />"></script>
<!--  /c:if-->
<script type="text/javascript">
$(document).ready(
		function() {
			setValidacion("${validacion}");
			
			<!--c:if test="${requiereDocumentacion}"-->
				<!--c:if test="${documentacion == 0}"-->
					loadFileUpload("${derechohabiente.tipoTramite.idTipoTramite}",undefined,undefined,'${tipoDocsNoMostrar}');
				<!--/c:if-->
				
		}
	);

</script>

<input type="hidden" id="requiereDocumentacion" value="${requiereDocumentacion?1:0}">
<input type="hidden" id="documentos" value="${documentacion}">
<input type="hidden" id="validacion" value="${validacion}"/>
<input type="hidden" id="esAsegurado" value="${esAsegurado}"/>
<input type="hidden" id="tieneAcuerdo" value="${tieneAcuerdo}"/>

<c:if test="${idSolicitud != null}">
	<input type="hidden" id="idSolicitud" value="${idSolicitud}"/>
</c:if>

<div class="form-comment">
	<br><br>
<c:choose>
<c:when test="${empty errores}">
	<c:if test="${validacion == 1}">
		<h4 align="center">VALIDACI&Oacute;N DE CAMBIO DE CLINICA UMF ORIGEN</h4>
	</c:if>
	<c:if test="${validacion == 2}">
		<h4 align="center">VALIDACI&Oacute;N DE CAMBIO DE CLINICA UMF DESTINO</h4>
	</c:if>
	<c:if test="${validacion == 0}">
		<h4 align="center">CAMBIO DE CLINICA</h4>
	</c:if>
	<jsp:include page="/WEB-INF/views/general/encabezadoMultiplesIntegrantes.jsp"></jsp:include>
	<br>
	<div id="mensajeConfirmacion"></div>
	<c:if test="${validacion != 0 }">
		<input type="hidden" value="${derechohabiente.medicoEnTurno.unidadMedicaFamiliar.idUMF}" id="idUMF"/>
		<input type="hidden" value="${derechohabiente.medicoEnTurno.turno.idTurno}" id="idTurno"/>
		<input type="hidden" value="${derechohabiente.medicoEnTurno.consultorio.idConsultorio}" id="idConsultorio"/>			
	</c:if>
	<input type="hidden" value="${datosActuales.medicoEnTurno.unidadMedicaFamiliar.idUMF}" id="idUMFAnt"/>
	<input type="hidden" value="${usuarioObj.idUmf}" id="idUMFUsuario"/>
	<input type="hidden" value="${usuarioObj.perfilUsuario.idPerfilUsuario}" id="idPerfilUsuario"/>
	
	<div class="ui-widget" id="divMensajesPersona">
			<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
			<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span><span id="mensajesDatosPersona">
				A continuaci&oacute;n se muestra el domicilio actual de la persona
			</span>
			</p></div>
		</div>
	<fieldset>
		<legend><strong>Domicilio actual del derechohabiente</strong></legend>
		<div id="domicilioAnteriorDiv"></div>
	</fieldset>
	<div class="ui-widget" id="divMensajesPersona">
			<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
			<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span><span id="mensajesDatosPersona">
				A continuaci&oacute;n capture el domicilio al que se mudara la persona, en caso de ser el mismo de clic en ACEPTAR para que 
				se muestren los datos de la UMF.
			</span>
			</p></div>
		</div>
	<fieldset>
		<legend><strong>Domicilio nuevo del derechohabiente</strong></legend>
		<div id="domicilioActualDiv"></div>
	</fieldset>

	<form:form id="correccionDatos" method="POST" commandName="derechohabiente">
		
		<div id="cand" style="display:none">
			<c:forEach items="${integrantes}" var="integrante">
				<input type="checkbox" checked="checked" id="candidatosCambioClinica" name="candidatosCambioClinica" value="${integrante.derechohabiente.idPersona}">
			</c:forEach>
		</div>
		
		<form:hidden path="idPersona" />
		<form:hidden path="nss" />
		<form:hidden path="tramiteId" />
		<form:hidden path="tipoTramite.idTipoTramite" />
		<form:hidden path="medicoEnTurno.idMedicoContultorioTurno" />	
		<form:hidden path="idUmfOrigen"/>
		<form:hidden path="enUmfDestino" />
		<form:hidden path="parentesco.idParentesco"/>
		
		<%--TODO verificar para que se usan --%>
		<input type="hidden" value="${resultado.tipoTramite.idTipoTramite}" id="idTipoTramite" name="idTipoTramite">
		<input type="hidden" value="${resultado.tramiteId}" id="tramiteId" name="tramiteId">
		<input type="hidden" value="${idDelegacionOrigen}" id="idDelegacion" name="idDelegacion">
			
		<%--Domicilio anterior --%>
		<form:hidden path="domicilioAnterior.clave"/>
		<!-- datos de la calle -->
		<form:hidden path="domicilioAnterior.calle"/>
		<form:hidden path="domicilioAnterior.vialidadPrimaria.nombre"/>
		<form:hidden path="domicilioAnterior.vialidadPrimaria.clave" />
		<form:hidden path="domicilioAnterior.vialidadPrimaria.tipoVialidad.clave" />
		<form:hidden path="domicilioAnterior.vialidadPrimaria.tipoVialidad.descripcion" />
		<!-- tipo de busqueda realizada -->
		<form:hidden path="domicilioAnterior.tipoBusquedaVialidad"/>
			
		<form:hidden path="domicilioAnterior.codigoPostal.codigoPostal"/>
		<!-- Datos del asentamiento -->
		<form:hidden path="domicilioAnterior.asentamiento.nombre"/>
		<form:hidden path="domicilioAnterior.asentamiento.clave" />
		<!-- Datos de la localidad -->
		<form:hidden path="domicilioAnterior.asentamiento.localidad.nombre"/>
		<form:hidden path="domicilioAnterior.asentamiento.localidad.clave" />
		<!-- Datos del municipio -->
		<form:hidden path="domicilioAnterior.asentamiento.localidad.municipio.nombre"/>
		<form:hidden path="domicilioAnterior.asentamiento.localidad.municipio.clave" />
		<!-- Datos de la entidad federativa -->
		<form:hidden path="domicilioAnterior.asentamiento.localidad.municipio.entidadFederativa.nombre"/>
		<form:hidden path="domicilioAnterior.asentamiento.localidad.municipio.entidadFederativa.clave" />
		
		<form:hidden path="domicilioAnterior.numExterior1"/>
		<form:hidden path="domicilioAnterior.numExteriorAlf" />
		<form:hidden path="domicilioAnterior.numInterior"/>
		<form:hidden path="domicilioAnterior.numInteriorAlf"/>
		
		<%--Domicilio nuevo --%>
		<form:hidden path="domicilio.clave"/>
			
		<!-- datos de la calle -->
		<form:hidden path="domicilio.calle"/>
		<form:hidden path="domicilio.vialidadPrimaria.nombre"/>
		<form:hidden path="domicilio.vialidadPrimaria.clave" />
		<form:hidden path="domicilio.vialidadPrimaria.tipoVialidad.clave" />
		<form:hidden path="domicilio.vialidadPrimaria.tipoVialidad.descripcion" />
		<!-- tipo de busqueda realizada -->
		<form:hidden path="domicilio.tipoBusquedaVialidad"/>
		
		<form:hidden path="domicilio.codigoPostal.codigoPostal"/>
		<!-- Datos del asentamiento -->
		<form:hidden path="domicilio.asentamiento.nombre"/>
		<form:hidden path="domicilio.asentamiento.clave" />
		<!-- Datos de la localidad -->
		<form:hidden path="domicilio.asentamiento.localidad.nombre"/>
		<form:hidden path="domicilio.asentamiento.localidad.clave" />
		<!-- Datos del municipio -->
		<form:hidden path="domicilio.asentamiento.localidad.municipio.nombre"/>
		<form:hidden path="domicilio.asentamiento.localidad.municipio.clave" />
		<!-- Datos de la entidad federativa -->
		<form:hidden path="domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre"/>
		<form:hidden path="domicilio.asentamiento.localidad.municipio.entidadFederativa.clave" />
		
		<form:hidden path="domicilio.numExterior1"/>
		<form:hidden path="domicilio.numExteriorAlf" />
		<form:hidden path="domicilio.numInterior"/>
		<form:hidden path="domicilio.numInteriorAlf"/>
		
		<br>
		
		<fieldset id="medicoTurno">
			<legend>
				<strong><spring:message code="titulo.datosUMF" />${derechohabiente.nombre} ${derechohabiente.primerApellido} ${derechohabiente.segundoApellido}</strong>
			</legend>
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
						<div id="errorUmf" ></div>		
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
						<div id="errorTurno" ></div>	
					</td>
					<td>
						<label class="control-label" for="medicoEnTurno.consultorio.idConsultorio" style="width: 140px !important;">
						<spring:message code="label.consultorio" /> <span class="required">*</span>:</label>
					</td>
					<td>
						<select id="medicoEnTurno.consultorio.idConsultorio" style="width: 275px;" name="medicoEnTurno.consultorio.idConsultorio">
							<option value="-1">--POR FAVOR SELECCIONE--</option>
						</select>
						<div id="errorConsultorio" ></div>
					</td>
				</tr>
				<tr>
					<td>
						<label class="control-label" for="medicoEnTurno.unidadMedicaFamiliar.idUMF" style="width: 140px !important;">
						Matricula medico :
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
						<div id="errorObservacion" ></div>
					</td>
				</tr>
				<tr>
					<td colspan="6"><br>Los campos marcados con <span class="required">*</span> con requeridos</td>
				</tr>
			</table>
		</fieldset>	
	</form:form>
	<br>
	<!-- c:if test="${requiereDocumentacion}"-->
		<%--Empieza la documentacion --%>
		<!-- c:if test="${documentacion == 0}"-->
			<jsp:include page="/WEB-INF/views/general/fileUpload/fileUpload.jsp"/>	
			<div id="msgDocumentosProb" title ="<spring:message code="titulo.mensajeAviso"/>" style="display:none"> 
				<spring:message code="msgDocumentosProb"/>		
			</div>
		<!-- /c:if-->
		<!-- c:if test="${documentacion == 1}"-->
			<div id="docProbTramDiv"></div>
		<!-- /c:if-->
	<!-- /c:if-->
	<br/><br/>
	<div align="center" class="form-comment">
		<form>
			<table>
			<tr>
				<td align="center">
					
					<input id="aceptar" type="button" value = "<spring:message code="button.aceptar"/>" class="mboton" />
					<input type="button" id="regresarLista" value="Regresar" class="mboton" />
					
					<input id="aceptarValidacion" type="button" value="Aceptar" class="mboton" style="display:none"/> 
					<input id="regresarGrupoFamiliar" type="button" value="<spring:message code="button.regresar"/>" class="mboton" />
					<input id="rechazarTramite" type="button" value="<spring:message code="button.cancelar"/>" class="mboton"/>
					<input type="button" id="regresar" value="Regresar"  class="mboton"/> 
				</td>
			</tr>
			</table>
		</form>
	</div>
</c:when>
<c:otherwise>
	<div class="ui-widget-content ui-corner-all">
		<div class="ui-state-error ui-corner-all" align="center">
			<div class="ui-icon ui-icon-alert"></div>
			<p class="ui-helper-reset ui-state-error-text"><spring:message code="${errores}" /></p>
		</div>
	</div>
</c:otherwise>
</c:choose>
<div id="domicilioUbicar">

</div>
</div>