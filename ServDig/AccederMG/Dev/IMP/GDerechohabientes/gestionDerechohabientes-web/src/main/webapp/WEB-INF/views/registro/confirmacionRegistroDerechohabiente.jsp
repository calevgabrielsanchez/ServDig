<%@ include file="/WEB-INF/views/general/taglibs.jsp" %>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>	

<jsp:include page="../common/llenaSexo.jsp"></jsp:include>
<jsp:include page="../common/llenaParentescos.jsp"></jsp:include>
<jsp:include page="../common/llenarRazonRegistro.jsp"></jsp:include>
<jsp:include page="../common/llenaEstadoCivil.jsp"></jsp:include>

<!-- <script type="text/javascript" src="<spring:url value="/static/resources/common/rechazoTramite.js" htmlEscape="true" />"></script> -->
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/registroDerechohabiente/confirmacionRegistro.js" htmlEscape="true" />"></script>
<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<script>
		var contextPath = "<%=request.getContextPath()%>";
</script>

<div class="form-comment">
	
	<br><br><br>							
	<jsp:include page="/WEB-INF/views/general/encabezadoGF.jsp"/>
	
	<!-- Variables necesarias para el registro -->
	<input type="hidden" value="${patronIMSS}" id="patronIMSS"/>
	
	<!-- Formulario para el registro de derechohabientes -->
	<form:form modelAttribute="registro" action="${contextpath}/tramite/registro/datosAdscripcion" method="POST" id="registro">
	<input type="hidden" value="${solicitudActiva.solicitudId}" id="idSolicitud"/>
	<form:hidden path="tramiteId"/>
	<form:hidden path="tipoTramite.idTipoTramite"/>
	<form:hidden path="cvePersonaDomicilio"/>
	<form:hidden path="parentesco.idParentesco"/>
	<form:hidden path="paso"/>
	
	<fieldset>
		<legend><strong>Resultado de las validaciones</strong></legend>
			<input type="hidden" value="${validaciones.correcto}" id="estadoValidaciones"/>
			<table class="page_holder_no_height" style="width: 100%">
				<tr>
					<td><strong>Resultado:</strong></td>
					<td>
					
						<c:if test="${validaciones.correcto}">
							<div class="ui-widget" style="width:90%">
								<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
									<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
										<strong>Aprobado</strong>
									</p>
								</div>
							</div>
						</c:if>
						<c:if test="${!validaciones.correcto}">
							<div class="ui-widget" style="width:90%">
								<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
								<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
								<strong>No aprobado</strong></p>
								</div>
							</div>
						</c:if>
					
					</td>
				</tr>
				<tr><td colspan="2"><br></td></tr>
				<tr>
					<td><strong>Motivo:</strong></td>
					<td>
						<div id="motivo" style="width:90%; height: auto" ><strong>${validaciones.mensaje}</strong></div>
					</td>
				</tr>				
			</table>
	</fieldset>
	<!-- Datos dentro del grupo familiar -->
	<fieldset id="datosDentroDelGrupo">
		<legend><strong>Datos del integrante dentro del grupo familiar</strong></legend>
		<table  class="page_holder_no_height" style="width: 100%">
		<tr>
			<td>
				<label class="control-label" for="parentesco.idParentesco" style="width: 160px !important;">
				Parentesco <span class="required">*</span>: </label>
			</td>
			<td>
			
				<form:input path="parentesco.descripcion" cssClass="alfabetico_espacios" disabled="true"/> 
			</td>
			<td>
				<c:if test="${registro.parentesco.idParentesco==2}">
					<label class="control-label" id="labelRecienNacido" style="width: 150px !important;">
					Recien nacido <span class="required">*</span>: </label>
				</c:if>
			</td>
			<td>
				<c:if test="${registro.parentesco.idParentesco==2}">
					<c:if test="${registro.razonRegistro.idRazonRegistro == 2 }">
						<input type="text" id="recienNacido" value="SI"  disabled="disabled"/>
					</c:if>
					<c:if test="${registro.razonRegistro.idRazonRegistro != 2 }">
						<input type="text" id="recienNacido" value="NO" disabled="disabled"/>
					</c:if>
				</c:if>
			</td>
		</tr>
		<tr>
			<td>
				<label class="control-label" for="fisica.estadoCivil.idEstadoCivil" style="width: 160px">
				Estado Civil <span class="required">*</span>: </label>
			</td>
			<td>
				<form:hidden path="fisica.estadoCivil.idEstadoCivil"/> 
				<form:input path="fisica.estadoCivil.descripcion" cssClass="alfabetico_espacios" disabled="true"/> 
			</td>
			<td>
				<label class="control-label" for="razonRegistro.idRazonRegistro" style="width: 150px !important;">
				Razon registro <span class="required">*</span>: </label>
			</td>
			<td>
				<form:hidden path="razonRegistro.idRazonRegistro"/> 
				<form:input path="razonRegistro.descripcion" cssClass="alfabetico_espacios" disabled="true"/> 
			</td>
		</tr>
	</table>
	</fieldset>
	<div class="ui-widget" id="divMensajesPersona">
		<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
		<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span><span id="mensajesDatosPersona">
			Confirme los datos de la persona en caso de ser incorrectos presione el bot&oacute;n <strong>Regresar</strong>.
		</span>
		</p></div>
	</div>
	<!-- Datos del integrante -->
	<fieldset id="datosPersonalesDerechohabiente">
		<legend><strong>Datos personales del integrante</strong></legend>
		<form:hidden path="fisica.idPersona"/> 
		
		<table class="page_holder_no_height"  style="width: 100%">
		<tr>
			<td >
				<label class="control-label" for="fisica.curp">
				<spring:message code="label.curp" />&nbsp;<span class="required">*</span>:&nbsp;</label>
			</td>
			<td>
				<form:input path="fisica.curp" cssClass="alfanumerico_espacios" disabled="true"/> 
			</td>
			<td colspan="2">
				
			</td>
		</tr>
		<tr>
			<td colspan="4"><br></td>
		</tr>
		<tr>
			<td>
				<label class="control-label" for="fisica.nombre" style="width: 150px">
				<spring:message code="label.nombre" />&nbsp;<span class="required">*</span>:&nbsp;
				</label>
			</td>
			<td>
				<form:input maxlegth="200" path="fisica.nombre" cssClass="alfabetico_espacios" disabled="true"/>
				<span id="fisica.nombreError" class="error hiddenElement"></span>
			</td>
			
			<td>
				<label class="control-label" for="fisica.lugarNacimiento.clave" style="width: 150px">
				<spring:message code="label.lugarNac" />&nbsp;<span class="required">*</span>:&nbsp;
				</label></td>
			<td>
				<form:hidden path="fisica.lugarNacimiento.clave"/> 
				<form:input path="fisica.lugarNacimiento.nombre" cssStyle="width: 150px" disabled="true"/>
				<span id="fisica.lugarNacimiento.claveError" class="error hiddenElement"></span>
			</td>
		</tr>
		<tr>
			<td>
				<label class="control-label" for="fisica.primerApellido" style="width: 150px">
				<spring:message code="label.primerApe" />&nbsp;<span class="required">*</span>:&nbsp;
				</label></td>
			<td>
				<form:input path="fisica.primerApellido" cssClass="alfabetico_espacios" disabled="true"/> 
				<span id="fisica.primerApellidoError" class="error hiddenElement"></span>
			</td>
			
			<td>
				<label class="control-label" for="fisica.sexo.idSexo" style="width: 150px">
				<spring:message code="label.sexo" />&nbsp;<span class="required">*</span>:&nbsp;
				</label></td>
			<td>
				<form:hidden path="fisica.sexo.idSexo"/> 
				<form:input path="fisica.sexo.descripcion" cssStyle="width: 150px" disabled="true"/>
				<span id="fisica.sexo.idSexoError" class="error hiddenElement"></span>
			</td>
			
		</tr>
		<tr>
			<td>
				<label class="control-label" for="fisica.segundoApellido" style="width: 150px">
				<spring:message code="label.segundoApe" />&nbsp;:&nbsp;
				</label>
			</td>
			<td>
				<form:input path="fisica.segundoApellido" cssClass="alfabetico_espacios" disabled="true"/> 
				<span id="fisica.segundoApellidoError" class="error hiddenElement"></span>
			</td>
			
			<td>
				<label class="control-label" for="fisica.fechaNacimiento">
					<spring:message code="label.fechaNac" />&nbsp;<span class="required">*</span>:&nbsp;
				</label>
			</td>
			<td>
				<form:input path="fisica.fechaNacimiento" cssStyle="width: 150px" disabled="true"/>
				<span id="fisica.fechaNacimientoError" class="error hiddenElement"></span>
				<form:hidden path="fisica.mesRegistroNac" value="${registro.fisica.mesRegistroNac}"/>
				<form:hidden path="fisica.anioRegistroNac" value="${registro.fisica.anioRegistroNac}"/>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td align="right"><input disabled="disabled" type="hidden"
				id="calificacion" name="calificacion" /></td>
		</tr>
		</table>
	</fieldset>
	
	<div class="ui-widget" id="divMensajeMediosContaco">
		<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
		<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span><span id="mensajesMediosContacto">
			Confirme los medios de contacto del nuevo integrante, en caso de ser incorrectos presione el bot&oacute;n <strong>Regresar</strong>.
		</span>
		</p></div>
	</div>
	<!-- Inicia el fieldset de medios de contacto-->
	<fieldset id="mediosContactoIntegrante">
	
	<legend><strong><spring:message code="titulo.mediosContacto" /></strong></legend>
	
	<table class="page_holder_no_height"  width="100%">
		<tr>
			<td>
				<label class="control-label" for="fisica.telefonoFijo.claveLada"  style="width: 150px"><spring:message code="label.telefonoFijo" />&nbsp;:&nbsp;
				</label>
			</td>
			<td>
				<form:hidden path="fisica.telefonoFijo.clave"/>
				
				<form:input path="fisica.telefonoFijo.claveLada" cssClass="entero_10" maxlength="10" disabled="true"/>
			</td>
			
			<td>
				<label class="control-label" for="fisica.correoElectronico.correo"  style="width: 150px"><spring:message code="label.correoElectronico" />&nbsp;:&nbsp;
				</label>
			</td>
			<td>
			
				<form:hidden path="fisica.correoElectronico.clave"/>
				<form:input path="fisica.correoElectronico.correo" maxlength="100"  disabled="true"/>
				
			</td>
		</tr>
	</table>
	</fieldset>
	<div class="ui-widget" id="divMensajeDomicilio">
		<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
		<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span><span id="mensajesDomicilio">
			Confirme el domicilio del nuevo integrante, en caso de ser incorrecto presione el bot&oacute;n <strong>Regresar</strong>.
		</span>
		</p></div>
	</div>
	<!-- Datos del domicilio -->
	<fieldset id="datosDomicilioDerechohabiente">
	<legend><strong>Domicilio del integrante</strong></legend>
	
	<table class="page_holder_no_height" style="width:95%">
	<tr>
		<td style="width:20%">
			<strong>
				C&oacute;digo Postal<span class="required labelObligatorio">*</span>: 
			</strong>
		</td>
		<td style="width:25%">
			<form:input path="domicilio.codigoPostal.codigoPostal"  maxlength="5"  cssClass="form-control" disabled="true" />
			<span id="domicilio.codigoPostal.codigoPostalError" class="error hiddenElement text-left"></span>
		</td>
		<td colspan="2"  style="width:50%"></td>
	</tr>
	<tr>
		<td>
			<strong>
				Estado: 
			</strong>
		</td>
		<td>
			<form:input path="domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre" cssStyle="width: 90%" cssClass="form-control" disabled="true"/>
			
			<form:hidden path="domicilio.asentamiento.localidad.clave"/>
			<form:hidden path="domicilio.asentamiento.localidad.municipio.entidadFederativa.clave"/>
			<span id="domicilio.asentamiento.localidad.municipio.entidadFederativa.nombreError" class="error hiddenElement"></span>
		</td>
		<td>
			<strong>
				Municipio o Delegaci&oacute;n: 
			</strong>
		</td>
		<td>
			<form:input path="domicilio.asentamiento.localidad.municipio.nombre" cssStyle="width: 90%" cssClass="form-control" disabled="true"/>
			<form:hidden path="domicilio.asentamiento.localidad.municipio.clave"/>
			<span id="domicilio.asentamiento.localidad.municipio.nombreError" class="error hiddenElement"></span>
		</td>
	</tr>
	<tr>
		<td>
			<strong>
				Colonia: <span class="required labelObligatorio">*</span>: 
			</strong>
		</td>
		<td colspan = "3">
			<form:input path="domicilio.asentamiento.nombre" cssStyle="width: 60%" cssClass="form-control" disabled="true"/>
			<form:hidden path="domicilio.asentamiento.clave"/>
		</td>
	</tr>
	<tr>
		<td>
			<strong>
				Calle<span class="required labelObligatorio" <c:if test="${bloquearCampos}">style="display:none"</c:if> >*</span>: 
			</strong>
		</td>
		<td colspan="3">
		
			<form:input path="domicilio.calle" cssStyle="width: 97%" cssClass="form-control" disabled="true"/>
			
			<form:hidden path="domicilio.vialidadPrimaria.nombre"/>
			<form:hidden path="domicilio.vialidadPrimaria.clave"/>
			<form:hidden path="domicilio.vialidadPrimaria.tipoVialidad.descripcion"/>
			<form:hidden path="domicilio.vialidadPrimaria.tipoVialidad.clave"/>
		</td>
	</tr>
	<tr>
		<td>
			<strong>
				N&uacute;mero exterior<span class="required labelObligatorio" <c:if test="${bloquearCampos}">style="display:none"</c:if> >*</span>: 
			</strong>
		</td>
		<td>
			<form:input path="domicilio.numExteriorAlf" cssStyle="width: 90%" cssClass="form-control" disabled="true"/>
		</td>
		<td>
			<strong>
				N&uacute;mero interior: 
			</strong>
		</td>
		<td>
			<form:input path="domicilio.numInteriorAlf" cssStyle="width: 90%" cssClass="form-control" disabled="true"/>
		</td>
	</tr>
	</table>
	</fieldset>
	<fieldset>
		<table style="width: 100%">
			<tr>				
				<td>		
					<div style="text-align: center;">			
					<c:if test="${validaciones.correcto}">						
						<input type="button" id="irAConfirmacion" class="mboton" value="<spring:message code="button.aceptar"/>"/>
					</c:if>
					<c:if test="${registro.tramiteId ne null }">
						<input type="button" id="rechazarTramite" class="mboton" value="Cancelar tr&aacute;mite"/>
					</c:if>
					<input type="button" id="regresaraInicioTramite" name="regresar" class="mboton" value="<spring:message code="button.regresar"/>"/>	
					</div>								
				</td>			
			</tr>		
		</table>
	</fieldset>		

	
	</form:form> <!-- Fin del formulario de registro de derechohabientes -->
</div>