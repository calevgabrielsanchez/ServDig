<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>
<%@taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/gestionCtrlSelect.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/correccion/validacionCircunscripcionForanea.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/correccion/circunscripcionForanea.js" htmlEscape="true" />"></script>
	
<%--scripts necesarios para el nuevo componente de domicilio --%>
<script type="text/javascript" src="/gestionDomicilios-web/static/resources/js/delta/domicilios/recortado/DomicilioRecortadoCtrl.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/common/setDomicilioCommon.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/domicilio/domicilioAutorizacionCircunscripcionInit.js" htmlEscape="true" />"></script>


<input type="hidden" value="${idUmfOrigen}" id="idUmfOr"/>
<input type="hidden" value="${idUmfUs}" id="idUmfUsuario"/>
<input type="hidden" value="${validacion}" id="validacion"/>
<input type="hidden" value = "${idDelegacionOrigen}" id="idDelegacionOrigen"/>
<input id="requiereDocs" type="hidden" value="${requiereDocs?1:0}"/>


<c:if test="${validacion == 0}">
	<%@ include file="/WEB-INF/views/general/fileUpload/FileUploadImport.jsp" %>
	<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/fileRead/fileRead.js" htmlEscape="true" />"></script>
	<script>
		$(document).ready(function() {
			loadFileUpload(${tipoTramite},undefined,undefined,'${tipoDocsNoMostrar}');
		});
	</script>
</c:if>
<c:if test="${validacion == 1}">
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/fileRead/fileRead.js" htmlEscape="true" />"></script>
</c:if>
<div class="form-comment">
	<br>
	<br>
	<c:if test="${validacion == 1}">
	<h4 align="center">VALIDACI&Oacute;N DE AUTORIZACI&Oacute;N PARA RECIBIR SERVICIOS EN CIRCUNSCRIPCI&Oacute;N FOR&Aacute;NEA</h4>
	</c:if>
	<c:if test="${validacion == 0}">
		<h4 align="center">AUTORIZACI&Oacute;N PARA RECIBIR SERVICIOS EN CIRCUNSCRIPCI&Oacute;N FOR&Aacute;NEA</h4>
	</c:if>
	<c:choose>
		<c:when test="${empty errores}">
			<br>
			<jsp:include page="/WEB-INF/views/prorrogas/grupoFamiliar.jsp"></jsp:include>
			<br>
			<div id="mensajeConfirmacion"></div>
			<c:if test="${validacion == 1 }">
				<input type="hidden" value="${derechohabiente.medicoEnTurno.unidadMedicaFamiliar.idUMF}" id="idUMF"/>
				<input type="hidden" value="${derechohabiente.medicoEnTurno.turno.idTurno}" id="idTurno"/>
				<input type="hidden" value="${derechohabiente.medicoEnTurno.consultorio.idConsultorio}" id="idConsultorio"/>			
			</c:if>
			
			
		<div id="tabla" class="containerRows contenedorPantalla">
			<div id="separadorMediosDomicilio" class="Row divSeparador"></div>
			<div id="filaDomicilios" class="Row">
						 <fieldset>
				<legend><strong>Datos del domicilio origen</strong></legend>
				<div id="domicilioAnteriorDiv"></div>
			</fieldset>
			<BR>
			<fieldset>
				<legend><strong>Datos del domicilio destino</strong></legend>
				<div id="domicilioActualDiv"></div>
			</fieldset>
			
			</div>
		 </div> 
		
			
		<form:form id="correccionDatos" method="POST" commandName="derechohabiente">
		 <br> 
		 <table align="center">
				<tr>
									<td colspan="2" align="center">
										<br>
										<input id="ubicar" type="button" value="Seleccionar Clinica" class="mboton" />
									</td>
				</tr>
		 </table>
			<br>
		<form:hidden path="parentesco.idParentesco"/>
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
		
		<form:hidden path="idPersona"/>
						
		<input type="hidden" value="${resultado.tipoTramite.idTipoTramite}" id="idTipoTramite" name="idTipoTramite">
		<input type="hidden" value="${resultado.tramiteId}" id="idTramite" name="idTramite">
						
			<BR>
				
				<fieldset id="medicoEnTurno">
					<legend>
						<strong><spring:message code="titulo.datosUMF" />
							${derechohabiente.nombre} ${derechohabiente.primerApellido}
							${derechohabiente.segundoApellido}</strong>
					</legend>
					<table class="" >
						<tr >
							<td><spring:message code="label.umf" />:</td>
							<td>
							    <select id="medicoEnTurno.unidadMedicaFamiliar.idUMF"
									name="medicoEnTurno.unidadMedicaFamiliar.idUMF" >
								</select>
								<div id="errorUmf" >
									</div>		
							</td>
							<td><spring:message code="label.umf.delegacion" /> :</td>
							<td>
								<input type="hidden"
								name="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.id"
								id="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.id"
								value="${derechohabiente.medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.id}" />	
								<input type="text" name="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion"
								id="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion" value="" style="width: 160px"></td>
							<td><spring:message code="label.umf.subdelegacion" />:</td>
							<td><input type="hidden"
								name="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.id"
								id="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.id"
								value=""> 
								
								<input type="text" name="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.descripcion"
								id="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.descripcion" value="" style="width: 160px"></td>
						</tr>
						<tr>
							<td colspan="6"><br>
							</td>
						</tr>
						<tr>
							<td><spring:message code="label.umfTurno" />:</td>
							<td>
							<combo:creaCombo idHtml="medicoEnTurno.turno.idTurno"
									idHtmlContenedor="correccionDatos"
									entidad="mx.gob.imss.ctirss.delta.persistence.DicTurno" 
									mostrarSoloActivos = "true" />
								<div id="errorTurno" >
								</div>	
							</td>
							<td><spring:message code="label.consultorio" />:</td>
							<td>
								<select id="medicoEnTurno.consultorio.idConsultorio"
									name="medicoEnTurno.consultorio.idConsultorio">
										<option value="">--Seleccione umf y turno--</option>
								</select>
								<div id="errorConsultorio" >
								</div></td>
							
							<td colspan="1"></td>
						</tr>
						<tr>
							<td colspan="6"><br>
							</td>
						</tr>
						<tr>
							<td>Matricula medico :</td>
							<td>
							<form:hidden path="medicoEnTurno.idMedicoContultorioTurno"/>
							<input type="hidden"
								name="medicoEnTurno.medicoFamiliar.idMedicoFamiliar"
								id="medicoEnTurno.medicoFamiliar.idMedicoFamiliar" value="">
								<input type="text"
								name="medicoEnTurno.medicoFamiliar.noMatricula"
								id="medicoEnTurno.medicoFamiliar.noMatricula" value=""
								style="width: 160px"></td>
							<td><spring:message code="label.medicoFamiliar" />:</td>
							<td><input type="text"
								name="medicoEnTurno.medicoFamiliar.nombre"
								id="medicoEnTurno.medicoFamiliar.nombre" value=""
								style="width: 160px"></td>
							<td>Especialidad :</td>
							<td><input type="hidden"
								name="medicoEnTurno.medicoEspecialidad.idMedicoEspacialidad"
								id="medicoEnTurno.medicoEspecialidad.idMedicoEspacialidad"
								value=""> <input type="text"
								name="medicoEnTurno.medicoEspecialidad.descripcion"
								id="medicoEnTurno.medicoEspecialidad.descripcion" value=""
								style="width: 160px"></td>
						</tr>
						<tr>
							<td><spring:message code="label.observaciones" />:  </td>
							<td colspan="5" >
								<c:if test="${validacion == 0}">
									<form:textarea path="observacion" cols="2" style="width: 453px; height: 45px"></form:textarea>
								</c:if>
								<c:if test="${validacion == 1}">
									<form:textarea  path="observacion" readonly="true" cols="2" style="width: 453px; height: 45px"></form:textarea>
								</c:if>
							</td>
						</tr>	
					</table>
				</fieldset>
				<br>	
			</form:form>
			
			
			<form:form id="autCircunscripcionFor" method="POST">
				<input type="hidden" value="${solicitud.solicitudId}" id="solicitudId" name="solicitudId"/>
			</form:form>
			<c:if test="${validacion == 0}">
				<div id="cagarDocProbDiv">
				<jsp:include page="/WEB-INF/views/general/fileUpload/fileUpload.jsp"/>	
				<div id="msgDocumentosProb" title ="<spring:message code="titulo.mensajeAviso"/>" style="display:none"> 
					<spring:message code="msgDocumentosProb"/>		
				</div>
				</div>
				<div id="docProbTramDiv">
				</div>
				<br><br>
			</c:if>
			
			<c:if test="${validacion == 1}">
			<div id="docProbTramDiv"></div>
					<script type="text/javascript">
						$(document).ready(function() {
							initMuestraDocumentosTramite(${derechohabiente.tramiteId});
						});
					</script>
			</c:if>
			<!-- termina -->
			<br />
			<br />
			<div align="center">
				<form>
					<table>
						<tr>
							<td align="center">
								<input id="aceptar" type="button" value="<spring:message code="button.aceptar"/>" class="mboton" /> 
								
								<!--<input id="guiaTramite" type="button" value="Guia de Tramite" class="mboton"/> -->
								<input id="regresar" type="button" value="Regresar" class="mboton"/> 
								<input id="cancelar" type="button" value="<spring:message code="button.regresar"/>" class="mboton" />
								<!-- Botones de validacion -->
								<input id="aceptarValidacion" type="button" value="<spring:message code="button.aceptar"/>" class="mboton" /> 
								<input id="regresarGrupoFamiliar" type="button" value="<spring:message code="button.regresar"/>" class="mboton" />
								<input id="rechazarTramite" type="button" value="<spring:message code="button.rechazar"/>" class="mboton"/> 
								<input id="regresarValidacion" type="button" value="Regresar" class="mboton"/> 
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div id="domicilioUbicar"></div>
		</c:when>
		<c:otherwise>
			<div class="ui-widget-content ui-corner-all">
				<div class="ui-state-error ui-corner-all" align="center">
					<div class="ui-icon ui-icon-alert"></div>
					<p class="ui-helper-reset ui-state-error-text">
						<spring:message code="${errores}" />
					</p>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</div>