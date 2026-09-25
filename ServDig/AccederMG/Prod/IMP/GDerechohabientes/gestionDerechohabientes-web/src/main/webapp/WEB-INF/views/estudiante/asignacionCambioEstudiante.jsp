<%@ include file="/WEB-INF/views/general/taglibs.jsp" %>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>


<script type="text/javascript" src="<spring:url value="/static/resources/common/combosUmfMedicoConsultorio.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/common/setDomicilioCommon.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="/gestionDomicilios-web/static/resources/js/delta/domicilios/DomicilioUmf.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/estudiantes/asignacionCambioEstudiantes.js" htmlEscape="true" />"></script>

<input type="hidden" value="${usuarioObj.usuarioFuncionario.unidadMedicaFamiliar.idUMF}" id="idUmfOrigen"/>

<div class="form-comment">
<h4 align="center">
	<c:if test="${cambioClinica == null || !cambioClinica}">
		ASIGNACI&Oacute;N DE DOMICILIO DE DERECHOHABIENTE
		<input type="hidden" value="0" id="cambioclinica"/>
	</c:if>
	<c:if test="${cambioClinica != null && cambioClinica}">
		CAMBIO DE CLINICA
		<input type="hidden" value="1" id="cambioclinica"/>
	</c:if>
</h4>

<div class="ui-widget" id="divMensajeMediosContaco">
		<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
		<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span> 
		Es necesario capturar el domicilio, presione el bot&oacute;n <strong>'Ubicar domicilio'</strong>. 
		<strong>Nota:</strong> el domicilio debe estar en la circunscripcion de esta Unidad M&eacute;dica Familiar.
	</div>
</div>

<form:form modelAttribute="tramiteCorreccion" action="/${mvn.web.app.root}/estudiantes/asignacion/domicilio/finalizar" method="POST" id="tramiteCorreccion">
<!-- Datos del domicilio -->
	<fieldset id="datosDomicilioDerechohabiente" style="width: 977px">
	<legend><strong>Domicilio del integrante</strong></legend>
	<table  class="page_holder_no_height" width="100%">
		<tr>
			<td>
				<label class="control-label" for="domicilio.vialidadPrimaria.nombre" style="width: 150px !important;">
					<span class="required">*</span>Calle&nbsp;:&nbsp;
				</label>
			</td>
			<td colspan="7">
				<form:hidden path="cvePersonaDomicilio"/>
				<form:hidden path="parentesco.idParentesco"/>
				<form:hidden path="paso"/>
				<form:hidden path="tipoTramite.idTipoTramite"/>
				<form:hidden path="tipoTramite.descripcion"/>
						
				<form:input path="domicilio.vialidadPrimaria.nombre" maxlength="100" cssStyle="width: 650px;"  disabled="true"/> 
				<span id="domicilio.vialidadPrimaria.nombreError" class="error hiddenElement"></span>
							
				<form:hidden path="domicilio.clave"/>
				<form:hidden path="domicilio.vialidadPrimaria.clave" />
				<form:hidden path="domicilio.vialidadPrimaria.tipoVialidad.clave" />
							
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
			</td>
		</tr>
		<tr>
			<td >
				<label class="control-label" for="domicilio.numExterior1" style="width: 150px;">
					<span class="required">*</span>N&uacute;mero Exterior&nbsp;:&nbsp;
				</label>
			</td>
			<td>
				<form:input path="domicilio.numExterior1" maxlength="5"  cssStyle="width: 50px;" disabled="true"/>
				<span id="domicilio.numExterior1Error" class="error hiddenElement"></span>
			</td>
			<td>
				<label class="control-label" for="domicilio.numExteriorAlf">
					Letra Exterior&nbsp;:&nbsp;
				</label>
			</td>
			<td>
				<form:input path="domicilio.numExteriorAlf" maxlength="35" cssStyle="width: 50px;" disabled="true"/>
				<span id="domicilio.numExteriorAlfError" class="error hiddenElement"></span>
			</td>
			<td>
				<label class="control-label" for="domicilio.numInterior">
					N&uacute;mero Interior&nbsp;:&nbsp;
				</label>
			</td>
			<td>
				<form:input path="domicilio.numInterior" maxlength="5" cssStyle="width: 50px;" disabled="true"/>
				<span id="domicilio.numInteriorError" class="error hiddenElement"></span>
			</td>
			<td>
				<label class="control-label" for="domicilio.numInteriorAlf">
					Letra Interior&nbsp;:&nbsp;
				</label>
			</td>
			<td>
				<form:input path="domicilio.numInteriorAlf" maxlength="35" cssStyle="width: 50px;" disabled="true"/>
				<span id="domicilio.numInteriorAlfError" class="error hiddenElement"></span>
			</td>	
		</tr>
		<tr>
			<td>
				<label class="control-label" for="domicilio.vialidadReferenciaPrimaria.nombre"  style="width: 150px;">
					Entre la calle&nbsp;:&nbsp;
				</label>
			</td>
			<td colspan="7">
				<form:input path="domicilio.vialidadReferenciaPrimaria.nombre" maxlength="100" cssStyle="width: 650px;" disabled="true"/> 
				<span id="domicilio.vialidadReferenciaPrimaria.nombreError" class="error hiddenElement"></span>
																
				<form:hidden path="domicilio.vialidadReferenciaPrimaria.clave" />
				<form:hidden path="domicilio.vialidadReferenciaPrimaria.tipoVialidad.clave" />
			</td>
		</tr>
		<tr>
			<td>	
				<label class="control-label"  for="domicilio.vialidadReferenciaSecundaria.nombre"  style="width: 150px">									
					y la calle&nbsp;:&nbsp;
				</label>
			</td>
			<td colspan="7">
				<form:input path="domicilio.vialidadReferenciaSecundaria.nombre" maxlength="100" cssStyle="width: 650px;" disabled="true"/> 
				<span id="domicilio.vialidadReferenciaSecundaria.nombreError" class="error hiddenElement"></span>
							
				<form:hidden path="domicilio.vialidadReferenciaSecundaria.clave" />
				<form:hidden path="domicilio.vialidadReferenciaSecundaria.tipoVialidad.clave" />
				<form:hidden path="domicilio.vialidadReferenciaPosterior.nombre" maxlength="14" /> 
				<form:hidden path="domicilio.vialidadReferenciaPosterior.clave" />
				<form:hidden path="domicilio.vialidadReferenciaPosterior.tipoVialidad.clave" />
			</td>
		</tr>
		<tr><td colspan="8">&nbsp;&nbsp;</td></tr>
		<tr>
			<td colspan="8" align="center" ><strong>Datos del asentamiento</strong></td>
		</tr>
		<tr><td colspan="8">&nbsp;&nbsp;</td></tr>
		<tr>		
			<td >		
				<label class="control-label" for="domicilio.asentamiento.nombre"  style="width: 150px">										
					<span class="required">*</span>Colonia(Asentamiento)&nbsp;:&nbsp;
				</label>
			</td>
			<td  colspan="3">
				<form:input path="domicilio.asentamiento.nombre" maxlength="50"  cssClass="form-control" disabled="true"/> 
				<span id="domicilio.asentamiento.nombreError" class="error hiddenElement"></span>			
				<form:hidden path="domicilio.asentamiento.clave" />
			</td>	
			<td>		
				<label class="control-label" for="domicilio.asentamiento.localidad.nombre">										
					<span class="required">*</span>Localidad&nbsp;:&nbsp;
				</label>
			</td>			
			<td  colspan="3">
				<form:input path="domicilio.asentamiento.localidad.nombre" maxlength="50"  cssClass="form-control" disabled="true"/> 
				<span id="domicilio.asentamiento.localidad.nombreError" class="error hiddenElement"></span>
				<form:hidden path="domicilio.asentamiento.localidad.clave" />
			</td>
		</tr>
		<tr>					
			<td >		
				<label class="control-label" for="domicilio.asentamiento.localidad.municipio.nombre"  style="width: 150px">								
					<span class="required">*</span>Municipio o delegaci&oacute;n&nbsp;:&nbsp;
				</label>
			</td>
			<td  colspan="3">
				<form:input path="domicilio.asentamiento.localidad.municipio.nombre"  cssClass="form-control" disabled="true"/> 
				<span id="domicilio.asentamiento.localidad.municipio.nombreError" class="error hiddenElement"></span>
				<form:hidden path="domicilio.asentamiento.localidad.municipio.clave" />
			</td>
			<td >
				<label class="control-label" for="domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre">
					<span class="required">*</span>Entidad Federativa&nbsp;:&nbsp;
				</label>
			</td>
			<td  colspan="3">
				<form:input path="domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre" maxlength="50" cssClass="form-control" disabled="true"/> 
				<span id="domicilio.asentamiento.localidad.municipio.entidadFederativa.nombreError" class="error hiddenElement"></span>
				<form:hidden path="domicilio.asentamiento.localidad.municipio.entidadFederativa.clave" />
			</td>
		</tr>
		<tr>
			<td>
				<label class="control-label" for="domicilio.codigoPostal.codigoPostal"  style="width: 150px">
					<span class="required">*</span>C&oacute;digo Postal&nbsp;:&nbsp;
				</label>
			</td>
			<td colspan="3">
				<form:input path="domicilio.codigoPostal.codigoPostal" maxlength="5"  cssClass="form-control" disabled="true"/>
				<span id="domicilio.codigoPostal.codigoPostalError" class="error hiddenElement"></span>
				<form:hidden path="domicilio.vialidadPrimaria.tipoVialidad.descripcion" />	
			</td>
			<td colspan="4">
				<input type="button" id="buscaDomicilio" name="buscaDomicilio" class="mboton" value="<spring:message code="button.domicilio"/>"/>
			</td>
		</tr>
		<tr>
			<td><br><span class="required">*</span>&nbsp;Datos Requeridos</td>
			<td colspan="7"/>
		</tr>
	</table>
	</fieldset>
	
	<div id="divDatosAdscripcion" style="display:none">
	<div class="ui-widget" id="divMensajeMediosContaco">
		<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
		<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span> 
			Es necesario que capture los datos de adscripcion del asegurado. Una vez que finalice presione el bot&oacute;n <strong>'Aceptar'</strong>.
		</div>
	</div>
	<fieldset id="datosAdscripcion">
		<legend><strong>Servicio m&eacute;dico</strong></legend>
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
									<span class="required">*</span><spring:message code="label.umfTurno" />:
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
								<span class="required">*</span><spring:message code="label.consultorio" />:</label>
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
						<td><br><span class="required">*</span>&nbsp;Datos Requeridos</td>
						<td colspan="5"/>
					</tr>
					</table>
	</fieldset>
	</div>
</form:form>

<div id="componentDomicilio"></div>
<div align="center">
<form>
<table>
	<tr>
		<td align="center">
		<div id="botones"><input id="aceptar" type="button"
			value="Aceptar" class="mboton" /> <input id="regresarGrupoFamiliar"
			type="button" value="<spring:message code="button.regresar"/>"
			class="mboton" /></div>
		</td>
	</tr>
</table>
</form>
</div>
</div>
