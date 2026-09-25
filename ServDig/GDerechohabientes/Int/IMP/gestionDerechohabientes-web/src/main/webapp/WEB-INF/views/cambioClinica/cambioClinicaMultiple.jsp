<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>
<%@taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<%@ include file="/WEB-INF/views/general/fileUpload/FileUploadImport.jsp" %>
<script type="text/javascript" src="<spring:url value="/static/resources/common/combosUmfMedicoConsultorio.js" htmlEscape="true" />"></script>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/gestionCtrlSelect.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/correccion/cambioClinica.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/correccion/validacionCambioClinica.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/fileRead/fileRead.js" htmlEscape="true" />"></script>
<script type="text/javascript">
$(document).ready(
		function() {
			setValidacion("${validacion}");
				<c:if test="${documentacion == 0}">
					loadFileUpload("${derechohabiente.tipoTramite.idTipoTramite}",undefined,undefined,'${tipoDocsNoMostrar}');
				</c:if>
				<c:if test="${documentacion == 1}">
					initMuestraDocumentosTramite("${derechohabiente.tramiteId}");
				</c:if>
		}
	);

</script>

<input type="hidden" id="documentos" value="${documentacion}">
<input type="hidden" id="validacion" value="${validacion}"/>
<input type="hidden" id="esAsegurado" value="${esAsegurado}"/>
<input type="hidden" id="cambioClinica1" />

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
	<fieldset>
	<div id="mensajeConfirmacion"></div>
	<c:if test="${validacion != 0 }">
		<input type="hidden" value="${derechohabiente.medicoEnTurno.unidadMedicaFamiliar.idUMF}" id="idUMF"/>
		<input type="hidden" value="${derechohabiente.medicoEnTurno.turno.idTurno}" id="idTurno"/>
		<input type="hidden" value="${derechohabiente.medicoEnTurno.consultorio.idConsultorio}" id="idConsultorio"/>			
	</c:if>
	<input type="hidden" value="${datosActuales.medicoEnTurno.unidadMedicaFamiliar.idUMF}" id="idUMFAnt"/>
	<input type="hidden" value="${usuarioObj.idUmf}" id="idUMFUsuario"/>
	<input type="hidden" value="${usuarioObj.perfilUsuario.idPerfilUsuario}" id="idPerfilUsuario"/>
	<form:form id="correccionDatos" method="POST" commandName="derechohabiente">
		<form:hidden path="domicilio.clave" />
		<form:hidden path="nss" />
		<form:hidden path="tramiteId"/>
		<form:hidden path="tipoTramite.idTipoTramite" />
		<form:hidden path="medicoEnTurno.idMedicoContultorioTurno" />	
		<form:hidden path="idUmfOrigen"/>
		<form:hidden path="enUmfDestino" />
		<form:hidden path="parentesco.idParentesco"/>
		<div id="cand" style="display:none">
			<c:forEach items="${integrantes}" var="integrante">
				<input type="checkbox" checked="checked" id="candidatosCambioClinica" name="candidatosCambioClinica" value="${integrante.derechohabiente.idPersona}">
			</c:forEach>
		</div>
		<table style="width: 100%">
				<tr >
					<td style="width: 50%">
						<fieldset  style="height: 770px;width: 95%;" >
							<legend>
								<strong>Datos del domicilio actual de la persona</strong>
							</legend>
							<table class="page_holder_no_height" style="width:95%">
								<tr>
									<td><spring:message code="label.entidadF" /> :</td>
									<td><input type="text"
										value="${datosActuales.domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre}"
										style="width: 160px" disabled="disabled" /></td>
								</tr>
								<tr>
									<td><spring:message code="label.delegacion" /> :</td>
									<td><input type="text"
										value="${datosActuales.domicilio.asentamiento.localidad.municipio.nombre}"
										style="width: 160px" disabled="disabled" /></td>
								</tr>
								<tr>
									<td><spring:message code="label.localidad" /> :</td>
									<td><input type="text"
										value="${datosActuales.domicilio.asentamiento.localidad.nombre}"
										style="width: 160px" disabled="disabled" /></td>
								</tr>
								<tr>
									<td><spring:message code="label.codigoPos" /> :</td>
									<td><input type="text"
										value="${datosActuales.domicilio.codigoPostal.codigoPostal}"
										style="width: 160px" disabled="disabled" /></td>
								</tr>
								<tr>
									<td><spring:message code="label.asentamiento" /> :</td>
									<td><input type="text"
										value="${datosActuales.domicilio.asentamiento.nombre}"
										disabled="disabled" /></td>
								</tr>
								<tr>
									<td><spring:message code="label.tipoVialidad" /> :</td>
									<td><input type="text"
										value="${datosActuales.domicilio.vialidadPrimaria.tipoVialidad.descripcion}"
										disabled="disabled" /></td>
								</tr>
								<tr>
									<td><spring:message code="label.nombreV" /> :</td>
									<td><input type="text"
										value="${datosActuales.domicilio.vialidadPrimaria.nombre}"
										style="width: 250px" disabled="disabled" /></td>
								</tr>
								<tr>
									<td ><spring:message code="label.numeroExt" />
										:</td>
									<td><input type="text"
										value="${datosActuales.domicilio.numExterior1}"
											style="width: 160px" disabled="disabled" /></td>
								</tr>
								
								<tr>
									<td><spring:message code="label.numeroLExt" /> :</td>
									<td><input type="text"
										value="${datosActuales.domicilio.numExteriorAlf}"
										style="width: 160px" disabled="disabled" /></td>
								</tr>
								
								<tr>
									<td ><spring:message code="label.numeroInt" />:</td>
									<td><input type="text"
										value="${datosActuales.domicilio.numInterior}"
											style="width: 160px" disabled="disabled" /></td>
								</tr>
								<tr>
									<td><spring:message code="label.numeroLInt" /> :</td>
									<td><input type="text"
										value="${datosActuales.domicilio.numInteriorAlf}"
										style="width: 160px" disabled="disabled" /></td>
								</tr>
								<tr>	
									<td ><spring:message code="tramite.detalle.numLetraExtNoOficial" /> : </td>
									<td>
										<input  type="text"  value="${datosActuales.domicilio.numExterior2}" style="width: 160px" disabled="disabled" />
									</td>
								</tr>
								<tr>
									<td><spring:message code="label.ref1" /></td>
								</tr>
								<tr>
									<td><spring:message code="label.tipoVialidad" /> :</td>
									<td><input type="text"
										value="${datosActuales.domicilio.vialidadReferenciaPrimaria.tipoVialidad.descripcion}"
										disabled="disabled" /></td>
								</tr>
								<tr>
									<td><spring:message code="label.nombreV" /> :</td>
									<td colspan="3"><input type="text"
										value="${datosActuales.domicilio.vialidadReferenciaPrimaria.nombre}"
										style="width: 250px; font-size: 10px" disabled="disabled" />
									</td>
								</tr>
								<tr>
									<td><spring:message code="label.ref2" /></td>
								</tr>
								<tr>
									<td><spring:message code="label.tipoVialidad" /> :</td>
									<td><input type="text"
										value="${datosActuales.domicilio.vialidadReferenciaSecundaria.tipoVialidad.descripcion}"
										disabled="disabled" /></td>
								</tr>
								<tr>
									<td><spring:message code="label.nombreV" /> :</td>
									<td colspan="3"><input type="text"
										value="${datosActuales.domicilio.vialidadReferenciaSecundaria.nombre}"
										style="width: 250px; font-size: 10px" disabled="disabled" />
									</td>
								</tr>
								<tr>
									<td><spring:message code="label.ref3" /></td>
								</tr>
								<tr>
									<td><spring:message code="label.tipoVialidad" /> :</td>
									<td><input type="text"
										value="${datosActuales.domicilio.vialidadReferenciaPosterior.tipoVialidad.descripcion}"
										disabled="disabled" /></td>
								</tr>
								<tr>
									<td><spring:message code="label.nombreV" /> :</td>
									<td colspan="3"><input type="text"
										value="${datosActuales.domicilio.vialidadReferenciaPosterior.nombre}"
										style="width: 250px; font-size: 10px" disabled="disabled" />
									</td>
								</tr>
								<tr>
									<td colspan="2"></td>
								</tr>
							</table>


						</fieldset>
					</td>
					<td style="width: 50%">
						<form:hidden path="idPersona" />
						<input type="hidden" value="${resultado.tipoTramite.idTipoTramite}" id="idTipoTramite" name="idTipoTramite">
						<input type="hidden" value="${resultado.tramiteId}" id="tramiteId" name="tramiteId">
						<input type="hidden" value="${idDelegacionOrigen}" id="idDelegacion" name="idDelegacion">
						<fieldset id="domicilio"  style="height: 770px;width: 95%;" >
							<legend>
								<strong><spring:message code="titulo.datosDomicilio" />
								</strong>
							</legend>
							<table class="page_holder_no_height" style="width:95%">
								<tr>
									<td ><spring:message code="label.entidadF" />
										:</td>
									<td><form:hidden
											path="domicilio.asentamiento.localidad.municipio.entidadFederativa.clave" />
										<form:input
											path="domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre"
											style="width: 160px" />
									</td>
								</tr>
								<tr>
									<td ><spring:message code="label.delegacion" />
										:</td>
									<td><form:hidden
											path="domicilio.asentamiento.localidad.municipio.clave" /> <form:input
											path="domicilio.asentamiento.localidad.municipio.nombre"
											style="width: 160px" />
									</td>
								</tr>
								<tr>
									
								</tr>
								<tr>
									<td ><spring:message code="label.localidad" />
										:</td>
									<td><form:hidden
											path="domicilio.asentamiento.localidad.clave" /> <form:input
											path="domicilio.asentamiento.localidad.nombre"
											style="width: 160px" /></td>
								</tr>
								<tr>
									<td ><spring:message code="label.codigoPos" />
										:</td>
									<td><form:input path="domicilio.codigoPostal.codigoPostal"
											style="width: 160px" /></td>
								
								</tr>
								<tr>
									<td ><spring:message code="label.asentamiento" />
										:</td>
									<td colspan="3"><form:hidden
											path="domicilio.asentamiento.clave" /> <form:input
											path="domicilio.asentamiento.nombre" style="width: 250px" /></td>
								</tr>
								<tr>
									<td ><spring:message code="label.tipoVialidad" /> : </td>
									<td>
										<form:hidden path="domicilio.vialidadPrimaria.tipoVialidad.clave"/>
										<form:input path="domicilio.vialidadPrimaria.tipoVialidad.descripcion"/>
									</td>
								</tr>
								<tr>
									<form:hidden
											path="domicilio.vialidadPrimaria.clave" /> 
									<td ><spring:message code="label.nombreV" /> :
									</td>
									<td><form:input
											path="domicilio.vialidadPrimaria.nombre" style="width: 250px" />
									</td>
								</tr>
								<tr>
									<td ><spring:message code="label.numeroExt" />
										:</td>
									<td><form:input path="domicilio.numExterior1"
											style="width: 160px" /></td>
								</tr>
								<tr>
									<td ><spring:message code="label.numeroLExt" />
									:</td>
									<td><form:input path="domicilio.numExteriorAlf"
										style="width: 160px" /></td>
								</tr>
								<tr>
									<td ><spring:message code="label.numeroInt" />:</td>
									<td><form:input path="domicilio.numInterior"
											style="width: 160px" /></td>
								</tr>		
								<tr>
									<td ><spring:message code="label.numeroLInt" />
										:</td>
									<td><form:input path="domicilio.numInteriorAlf"
											style="width: 160px" /></td>
								</tr>
								<tr>	
									<td ><spring:message code="tramite.detalle.numLetraExtNoOficial" /> : </td>
									<td>
										<form:input path="domicilio.numExterior2" style="width: 160px" class='alfanumerico_espacios' 
										maxlength="15"/>
									</td>
								</tr>
								<tr>
									<td ><spring:message code="label.ref1" />
									</td>
								</tr>
								<tr>
									<td ><spring:message code="label.tipoVialidad" /> : </td>
									<td>
										<form:hidden path="domicilio.vialidadReferenciaPrimaria.tipoVialidad.clave"/>
										<form:input path="domicilio.vialidadReferenciaPrimaria.tipoVialidad.descripcion"/>
									</td>
								</tr>
								<tr>
									<form:hidden
											path="domicilio.vialidadReferenciaPrimaria.clave" />
									<td ><spring:message code="label.nombreV" /> :
									</td>
									<td><form:input
											path="domicilio.vialidadReferenciaPrimaria.nombre"
											style="width: 250px" /></td>
				
								</tr>
								<tr>
									<td><spring:message
											code="label.ref2" />
									</td>
								</tr>
								<tr>
									<td ><spring:message code="label.tipoVialidad" /> : </td>
									<td>
										<form:hidden path="domicilio.vialidadReferenciaSecundaria.tipoVialidad.clave"/>
										<form:input path="domicilio.vialidadReferenciaSecundaria.tipoVialidad.descripcion"/>
									</td>
								</tr>
								<tr>
									<form:hidden
											path="domicilio.vialidadReferenciaSecundaria.clave" />
									<td ><spring:message code="label.nombreV" /> :
									</td>
									<td><form:input
											path="domicilio.vialidadReferenciaSecundaria.nombre"
											style="width: 250px" /></td>
								</tr>
								<tr>
									<td><spring:message
											code="label.ref3" />
									</td>
								</tr>
								<tr>
									<td ><spring:message code="label.tipoVialidad" /> : </td>
									<td>
										<form:hidden path="domicilio.vialidadReferenciaPosterior.tipoVialidad.clave"/>
										<form:input path="domicilio.vialidadReferenciaPosterior.tipoVialidad.descripcion"/>
									</td>
								</tr>
								<tr>
									<form:hidden
											path="domicilio.vialidadReferenciaPosterior.clave" />	
									<td ><spring:message code="label.nombreV" /> :
									</td>
									<td><form:input
											path="domicilio.vialidadReferenciaPosterior.nombre"
											style="width: 250px" /></td>
											
									<form:hidden path="domicilio.calle"/>
									<form:hidden path="domicilio.tipoBusquedaVialidad"/>
							
									<!-- Atributos de domicilio carretera -->
									<form:hidden path="domicilio.domicilioCarretera.terminoGeneral.descripcion"/>
									<form:hidden path="domicilio.domicilioCarretera.terminoGeneral.clave"/>
									<form:hidden path="domicilio.domicilioCarretera.derechoTransito.descripcion"/>
									<form:hidden path="domicilio.domicilioCarretera.derechoTransito.clave"/>
									<form:hidden path="domicilio.domicilioCarretera.origen"/>
									<form:hidden path="domicilio.domicilioCarretera.destino"/>
									<form:hidden path="domicilio.domicilioCarretera.administracion.descripcion"/>
									<form:hidden path="domicilio.domicilioCarretera.administracion.clave"/>
									<form:hidden path="domicilio.domicilioCarretera.cadenamiento"/>
									<form:hidden path="domicilio.domicilioCarretera.codigoCarretera"/>
											
									<!-- Atrbutos de domicilio camino -->
									<form:hidden path="domicilio.domicilioCamino.terminoGeneral.descripcion"/>
									<form:hidden path="domicilio.domicilioCamino.terminoGeneral.clave"/>
									<form:hidden path="domicilio.domicilioCamino.margen.descripcion"/>
									<form:hidden path="domicilio.domicilioCamino.margen.clave"/>
									<form:hidden path="domicilio.domicilioCamino.origen"/>
									<form:hidden path="domicilio.domicilioCamino.destino"/>
									<form:hidden path="domicilio.domicilioCamino.cadenamiento"/>
								</tr>
								<tr>
									<td colspan="2" align="right">
										<br>
										<input type="button" id="ubicarUMF" value="Ubicar UMF" class="mboton" />
									</td>
								</tr>
							</table>
						</fieldset>
					</td>	
				</tr>
			</table>
			<br >
				<fieldset id="medicoTurno">
					<legend>
						<strong><spring:message code="titulo.datosUMF" />
							${derechohabiente.nombre} ${derechohabiente.primerApellido}
							${derechohabiente.segundoApellido}</strong>
					</legend>
					
					<table class="page_holder_no_height" width="90%">
						<tr >
							<td>
								<label class="control-label" for="medicoEnTurno.unidadMedicaFamiliar.idUMF" style="width: 140px !important;">
									<span class="required">*</span><spring:message code="label.umf" />:
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
									<span class="required">*</span><spring:message code="label.umfTurno" />:
								</label>
							</td>
							<td>
								<combo:creaCombo idHtml="medicoEnTurno.turno.idTurno"
									idHtmlContenedor="correccionDatos"
									entidad="mx.gob.imss.ctirss.delta.persistence.DicTurno" 
									idHtmlValor="${derechohabiente.medicoEnTurno.turno.idTurno}"
									mostrarSoloActivos = "true"/>
								<div id="errorTurno" ></div>	
							</td>
							<td>
								<label class="control-label" for="medicoEnTurno.consultorio.idConsultorio" style="width: 140px !important;">
								<span class="required">*</span><spring:message code="label.consultorio" />:</label>
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
							<spring:message code="label.observaciones" />: </label></td>
							<td colspan="5" >
								<form:textarea path="observacion" cols="2" style="width: 710px; height: 60px"></form:textarea>
							</td>
						</tr>
						<tr>
						<td><br><span class="required">*</span>&nbsp;Datos Requeridos</td>
						<td colspan="5"/>
					</tr>
					</table>
				</fieldset>
				<br>	
	</form:form>
	<fieldset>
		<br>
		<c:if test="${documentacion == 0}">
			<jsp:include page="/WEB-INF/views/general/fileUpload/fileUpload.jsp"/>	
			<div id="msgDocumentosProb" title ="<spring:message code="titulo.mensajeAviso"/>" style="display:none"> 
				<spring:message code="msgDocumentosProb"/>		
			</div>
		</c:if>
		<c:if test="${documentacion == 1}">
			<div id="docProbTramDiv"></div>
		</c:if>
	<br>
	</fieldset>
	</fieldset>
	<br/><br/>
	<div align="center" class="form-comment">
		<form>
			<table>
			<tr>
				<td align="center">
					
					<input id="aceptar" type="button" value = "<spring:message code="button.aceptar"/>" class="mboton" />
					<input type="button" id="regresarLista" value="Regresar" class="mboton" />
					<input id="aceptarValidacion" type="button" value="Aceptar" class="mboton" /> 
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