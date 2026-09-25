<%@ include file="/WEB-INF/views/general/taglibs.jsp" %>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<%@ include file="/WEB-INF/views/general/GuiaTramite/guiaTramiteImport.jsp" %>	

<jsp:include page="../common/llenaSexo.jsp"></jsp:include>
<jsp:include page="../common/llenaParentescos.jsp"></jsp:include>
<jsp:include page="../common/llenarRazonRegistro.jsp"></jsp:include>
<jsp:include page="../common/llenaEstadoCivil.jsp"></jsp:include>

<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="/gestionDomicilios-web/static/resources/js/delta/domicilios/DomicilioUmf.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>

<%--scripts necesarios para el nuevo componente de domicilio --%>
<script type="text/javascript" src="/gestionDomicilios-web/static/resources/js/delta/domicilios/recortado/DomicilioRecortadoCtrl.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/registroDerechohabiente/domicilioController.js" htmlEscape="true" />"></script>


<script type="text/javascript" src="/gestionAsegurados-web-externo/static/resources/js/delta/wizard/busquedaPersona/busquedaPersonaCurpWizard.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/common/combosParentescos.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/common/setDomicilioCommon.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/common/funcionesComunes.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/registroDerechohabiente/registroDerechohabiente.js" htmlEscape="true" />"></script>
<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<script>
		var contextPath = "<%=request.getContextPath()%>";
</script>

<div class="form-comment">
	
	<br><br><br>							
	<jsp:include page="/WEB-INF/views/general/encabezadoGF.jsp"/>
	
	<!-- Variables necesarias para el registro -->
	<input type="hidden" value="${usuarioObj.idUmf}" id="idUmfUsuario"/>
	<input type="hidden" value="${aseguradoConDomicilio?1:0}" id="aseguradoConDomicilio"/>
	<input type="hidden" value="${patronIMSS}" id="patronIMSS"/>
	<input type="hidden" value="${AsignacionNSS.sexo.idSexo}" id="idSexoAsegurado"/>
	<input type="hidden" value="${AsignacionNSS.primerApellido}" id="apellidoAsegurado"/>
	<input type="hidden" value="${registro.varianteRegistro}" id="valorActualVariante"/>
	<!-- Formulario para el registro de derechohabientes -->
	<form:form modelAttribute="registro" action="${contextpath}/derechohabientes/registro/confirmacion" method="POST" id="registro">
	
	<input type="hidden" value="${registro.parentesco.idParentesco}" id="idParentescoSeleccionado"/>
	<input type="hidden" value="${registro.razonRegistro.idRazonRegistro}" id="idRazonregistroSeleccionada"/>
	<input type="hidden" value="${solicitudActiva.solicitudId}" id="idSolicitud"/>
	<form:hidden path="tramiteId"/>
	<form:hidden path="tipoTramite.idTipoTramite"/>
	
	<c:if test="${not empty validaciones && !validaciones.correcto}">
		<div class="ui-widget">
		<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
		<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span> <strong>${validaciones.mensaje}</strong></p>
		</div>
		</div>
	</c:if>
	
	<!-- Datos dentro del grupo familiar -->
	<fieldset  style="width: 977px">
		<legend><strong>Datos del integrante dentro del grupo familiar</strong></legend>
		<table  class="page_holder_no_height" width="100%">
		<tr>
			<td>
				<label class="control-label" for="parentesco.idParentesco" style="width: 160px !important;">
				Parentesco <span class="required">*</span>: </label>
			</td>
			<td>
				<input type="hidden" id="prueba">
				<select id="parentesco.idParentesco" name="parentesco.idParentesco">
					<option value="-1">--POR FAVOR SELECCIONE--</option>
				</select>
				<form:hidden path="parentesco.descripcion"/> 
				<span id="parentesco.idParentescoError" class="error hiddenElement"></span>
			</td>
			<td colspan="2">
				 <div id="radiosRecienNacido" style="display:none;">
				 	
				 	<strong>
				 	&nbsp;&nbsp;
					<span>
						<c:if test="${registro.varianteRegistro == 0}" >
					 		<input type="radio" value="0" name="varianteRegistro" id="ningunoCheck" checked="checked">
					 	</c:if>
					 	<c:if test="${registro.varianteRegistro != 0}" >
					 		<input type="radio" value="0" name="varianteRegistro" id="ningunoCheck">
					 	</c:if>
					 	Normal
				 	</span>
				 	&nbsp;&nbsp;&nbsp;
				 	<span id="spanRecienNacido">		 	
					 	<c:if test="${registro.varianteRegistro == 1}">
							<input type="radio" value="1" name="varianteRegistro" id="recienNacidoCheck" checked="checked">
						</c:if>
						<c:if test="${registro.varianteRegistro != 1}">
							<input type="radio" value="1" name="varianteRegistro"id="recienNacidoCheck">
						</c:if>
						Reci&eacute;n nacido
					</span>
					&nbsp;&nbsp;&nbsp;
					<span>
						<c:if test="${registro.varianteRegistro == 2}">
							<input type="radio" value="2" name="varianteRegistro" id="adopcionCheck" checked="checked">
						</c:if>
						<c:if test="${registro.varianteRegistro != 2}">
							<input type="radio" value="2" name="varianteRegistro" id="adopcionCheck">
						</c:if>
						Adopci&oacute;n
					</span>
					&nbsp;&nbsp;&nbsp;
					<span>
						<c:if test="${registro.varianteRegistro == 3}">
							<input type="radio" value="3" name="varianteRegistro" id="reconocimientoCheck" checked="checked">
						</c:if>
						<c:if test="${registro.varianteRegistro != 3}">
							<input type="radio" value="3" name="varianteRegistro" id="reconocimientoCheck">
						</c:if>
						Reconocimiento
					</span>
					
				 	</strong>
				</div>
				<!--  
				<div id="radiosRecienNacido" style="display:none;">
					<c:if test="${registro.razonRegistro.idRazonRegistro == 2 }">
						<input type="checkbox" id="recienNacidoCheck" checked="checked">
					</c:if>
					<c:if test="${registro.razonRegistro.idRazonRegistro != 2 }">
						<input type="checkbox" id="recienNacidoCheck">
					</c:if>
				</div>
				-->
				<div id="checkHijosProcreados" style="display:none;">
					<label class="control-label" id="labelHijosProcreados" style="display:none; width: 150px !important;">
					Existen hijos procreados ? <span class="required">*</span>: </label>
					<c:if test="${registro.indHijosProcreados == 1 }">
						<input type="checkbox" id="hijosProcreadosCheck" checked="checked">
					</c:if>
					<c:if test="${registro.indHijosProcreados == 0 }">
						<input type="checkbox" id="hijosProcreadosCheck">
					</c:if>
				</div>
				
				<form:hidden path="indHijosProcreados"/>
			</td>
		</tr>
		<tr>
			<td>
				<label class="control-label" for="fisica.estadoCivil.idEstadoCivil" style="width: 160px">
				Estado Civil <span class="required">*</span>: </label>
			</td>
			<td>
				<combo:creaCombo
					idHtmlValor="${registro.fisica.estadoCivil.idEstadoCivil}"
					idHtml="fisica.estadoCivil.idEstadoCivil"
					idHtmlContenedor="registro"
					entidad="mx.gob.imss.ctirss.delta.persistence.DicEstadoCivil"
					mostrarSoloActivos="true"
				/> 
				<form:hidden path="fisica.estadoCivil.descripcion"/> 
				<span id="fisica.estadoCivil.idEstadoCivilError" class="error hiddenElement"></span>
			</td>
			<td>
				<label class="control-label" for="razonRegistro.idRazonRegistro" style="width: 150px !important;">
				Razon registro <span class="required">*</span>: </label>
			</td>
			<td>
				<select id="razonRegistro.idRazonRegistro" name="razonRegistro.idRazonRegistro">
					<option value="-1">--POR FAVOR SELECCIONE--</option>
				</select>
				
				<form:hidden path="razonRegistro.descripcion"/> 
				<span id="razonRegistro.idRazonRegistroError" class="error hiddenElement"></span>
			</td>
		</tr>
		<tr>
			<td colspan="4">Los campos marcados con <span class="required">*</span> son requeridos.</td>
		</tr>
	</table>
	</fieldset>
	<c:if test="${empty validaciones or validaciones.correcto}">
		<div class="ui-widget" id="divMensajesPersona">
			<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
			<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span><span id="mensajesDatosPersona">
				Para ubicar a la persona de clic en el bot&oacute;n <strong>Buscar Persona</strong>.
			</span>
			</p></div>
		</div>
	</c:if>
	<c:if test="${not empty validaciones && !validaciones.correcto}">
		<div class="ui-widget">
		<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
		<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span> <strong>${validaciones.mensaje}</strong></p>
		</div>
		</div>
	</c:if>
	<!-- Datos del integrante -->
	<fieldset id="datosPersonalesDerechohabiente" style="width: 977px">
		<legend><strong>Datos personales del integrante</strong></legend>
		<form:hidden path="fisica.idPersona"/> 
		
		<table class="page_holder_no_height" width="100%">
		<tr>
			<td >
				<label class="control-label" for="fisica.curp" style="width: 150px">
				<spring:message code="label.curp" />&nbsp;<span id="curpRequiredLabel" class="required">*</span> :&nbsp;</label>
			</td>
			<td>
				<form:input path="fisica.curp" cssClass="alfanumerico_espacios"/> 
			</td>
			<td colspan="2">
				<c:if test="${empty validaciones or validaciones.correcto}">
					<input type="button" class="mboton" id = "localizaPersona" value="Buscar persona"/>
				</c:if>
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
				<form:input maxlegth="200" path="fisica.nombre" cssClass="alfabetico_espacios"/>
				<span id="fisica.nombreError" class="error hiddenElement"></span>
			</td>
			
			<td>
				<label class="control-label" for="fisica.lugarNacimiento.clave" style="width: 150px">
				<spring:message code="label.lugarNac" />&nbsp;<span class="required">*</span>:&nbsp;
				</label></td>
			<td>
				<combo:creaCombo
					idHtmlValor="${registro.fisica.lugarNacimiento.clave}"
					idHtml="fisica.lugarNacimiento.clave" idHtmlContenedor="registro"
					entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado" 
					mostrarSoloActivos = "true"
				/>
				<form:hidden path="fisica.lugarNacimiento.nombre"/>
				<span id="fisica.lugarNacimiento.claveError" class="error hiddenElement"></span>
			</td>
		</tr>
		<tr>
			<td>
				<label class="control-label" for="fisica.primerApellido" style="width: 150px">
				
				<spring:message code="label.primerApe" />&nbsp;:&nbsp;
				</label></td>
			<td>
				<form:input path="fisica.primerApellido" cssClass="alfabetico_espacios" maxlength="30"/> 
				<span id="fisica.primerApellidoError" class="error hiddenElement"></span>
			</td>
			
			<td>
				<label class="control-label" for="fisica.sexo.idSexo" style="width: 150px">
				
				<spring:message code="label.sexo" />&nbsp;<span class="required">*</span>:&nbsp;
				</label></td>
			<td>
				<combo:creaCombo
				idHtmlValor="${registro.fisica.sexo.idSexo}"
				idHtml="fisica.sexo.idSexo" idHtmlContenedor="registro"
				entidad="mx.gob.imss.ctirss.delta.persistence.DicSexo" 
				mostrarSoloActivos = "true" 
				/>
				<form:hidden path="fisica.sexo.descripcion"/>
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
				<form:input path="fisica.segundoApellido" cssClass="alfabetico_espacios" maxlength="30"/> 
				<span id="fisica.segundoApellidoError" class="error hiddenElement"></span>
			</td>
			
			<td>
				<label class="control-label" for="fisica.fechaNacimiento" style="width: 150px">
					
					<spring:message code="label.fechaNac" />&nbsp;<span class="required">*</span>:&nbsp;
				</label>
			</td>
			<td>
				<form:input path="fisica.fechaNacimiento" tabindex="5" cssStyle="width: 150px"/>
				<form:hidden path="conFechaNacimiento"/>
				<span id="fisica.fechaNacimientoError" class="error hiddenElement"></span>
				<form:hidden path="fisica.mesRegistroNac" value="${registro.fisica.mesRegistroNac}"/>
				<form:hidden path="fisica.anioRegistroNac" value="${registro.fisica.anioRegistroNac}"/>
			</td>
		</tr>
		<tr>
			<td colspan="4">Los campos marcados con <span class="required">*</span> son requeridos.</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>
				<form:hidden path="fisica.actaNacimiento.noActa"/>
				<form:hidden path="fisica.actaNacimiento.noFoja"/>
				<form:hidden path="fisica.actaNacimiento.noLibro"/>
				<form:hidden path="fisica.actaNacimiento.municipio.clave"/>
				<form:hidden path="fisica.actaNacimiento.municipio.nombre"/>
				<form:hidden path="fisica.actaNacimiento.municipio.entidadFederativa.clave"/>
				<form:hidden path="fisica.actaNacimiento.municipio.entidadFederativa.nombre"/>
				<form:hidden path="fisica.actaNacimiento.fechaSuceso"/>
				<form:hidden path="fisica.actaNacimiento.anio"/>
				<form:hidden path="fisica.actaNacimiento.tomo"/>
				<form:hidden path="fisica.actaNacimiento.crip"/>
				<form:hidden path="fisica.actaNacimiento.noJuzgado"/>
				<form:hidden path="fisica.actaNacimiento.documentoPorTipo.idDocumentoPorTipo"/>
				<form:hidden path="fisica.actaNacimiento.documentoPorTipo.documento.cveIdDocumento"/>
				<form:hidden path="fisica.actaNacimiento.documentoPorTipo.documento.desDocumento"/>
			</td>
			<td align="right"><input disabled="disabled" type="hidden" id="calificacion" name="calificacion" /></td>
		</tr>
		</table>
	</fieldset>
	
	<c:if test="${not empty validaciones && !validaciones.correcto}">
		<div class="ui-widget">
		<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
		<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span> <strong>${validaciones.mensaje}</strong></p>
		</div>
		</div>
	</c:if>
	<!-- Inicia el fieldset de medios de contacto-->
	<fieldset style="width: 977px">
	
	<legend><strong><spring:message code="titulo.mediosContacto" /></strong></legend>
	
	<table class="page_holder_no_height"  width="100%">
		<tr>
			<td>
				<label class="control-label" for="fisica.telefonoFijo.claveLada"  style="width: 150px"><spring:message code="label.telefonoFijo" />&nbsp;:&nbsp;
				</label>
			</td>
			<td>
				<form:hidden path="fisica.telefonoFijo.clave"/>
				
				<form:input path="fisica.telefonoFijo.claveLada" cssClass="entero_10" maxlength="15"/>
			</td>
			<td>
				<label class="control-label" for="fisica.correoElectronico.correo"  style="width: 150px"><spring:message code="label.correoElectronico" />&nbsp;:&nbsp;
				</label>
			</td>
			<td>
			
				<form:hidden path="fisica.correoElectronico.clave"/>
				<form:input path="fisica.correoElectronico.correo" maxlength="45" />
				<span id="fisica.correoElectronico.correoError" class="error hiddenElement"></span>
				
			</td>
		</tr>
	</table>
	</fieldset>
	<c:if test="${empty validaciones || validaciones.correcto}">
		<div class="ui-widget" id="divMensajeDomicilio">
			<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
			<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span><span id="mensajesDomicilio">
				Para modificar el domicilio es necesario elegir el parentesco y capturar los datos de la persona.
			</span>
			</p></div>
		</div>
	</c:if>
	
	<c:if test="${not empty validaciones && !validaciones.correcto}">
		<div class="ui-widget">
		<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
		<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span> <strong>${validaciones.mensaje}</strong></p>
		</div>
		</div>
	</c:if>
	
	<div style="display:none">
		<form:hidden path="cvePersonaDomicilio"/>
			<form:hidden path="parentesco.idParentesco"/>
			<form:hidden path="paso"/>
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
			
			<!-- Atributos de domicilio carretera -->
			<form:hidden path="domicilio.domicilioCarretera.terminoGeneral.descripcion" />
			<form:hidden path="domicilio.domicilioCarretera.terminoGeneral.clave" />
			<form:hidden path="domicilio.domicilioCarretera.derechoTransito.descripcion" />
			<form:hidden path="domicilio.domicilioCarretera.derechoTransito.clave" />
			<form:hidden path="domicilio.domicilioCarretera.origen" />
			<form:hidden path="domicilio.domicilioCarretera.destino" />
			<form:hidden path="domicilio.domicilioCarretera.administracion.descripcion" />
			<form:hidden path="domicilio.domicilioCarretera.administracion.clave" />
			<form:hidden path="domicilio.domicilioCarretera.cadenamiento" />
			<form:hidden path="domicilio.domicilioCarretera.codigoCarretera" />

			<!-- Atrbutos de domicilio camino -->
			<form:hidden path="domicilio.domicilioCamino.terminoGeneral.descripcion" />
			<form:hidden path="domicilio.domicilioCamino.terminoGeneral.clave" />
			<form:hidden path="domicilio.domicilioCamino.margen.descripcion" />
			<form:hidden path="domicilio.domicilioCamino.margen.clave" />
			<form:hidden path="domicilio.domicilioCamino.origen" />
			<form:hidden path="domicilio.domicilioCamino.destino" />
			<form:hidden path="domicilio.domicilioCamino.cadenamiento" />
			
			<form:hidden path="domicilio.vialidadReferenciaPrimaria.nombre"/>
			<form:hidden path="domicilio.vialidadReferenciaPrimaria.clave" />
			<form:hidden path="domicilio.vialidadReferenciaPrimaria.tipoVialidad.clave" />

			<form:hidden path="domicilio.vialidadReferenciaSecundaria.nombre"/>
			<form:hidden path="domicilio.vialidadReferenciaSecundaria.clave" />
			<form:hidden path="domicilio.vialidadReferenciaSecundaria.tipoVialidad.clave" />
			<form:hidden path="domicilio.vialidadReferenciaPosterior.nombre"/>
			<form:hidden path="domicilio.vialidadReferenciaPosterior.clave" />
			<form:hidden path="domicilio.vialidadReferenciaPosterior.tipoVialidad.clave" />
		</div>
	</form:form> <!-- Fin del formulario de registro de derechohabientes -->
	
	<%--Dejamos fuera el formulario de domicilio para que no se mande junto al otro --%>
	<!-- Datos del domicilio -->
	<fieldset id="datosDomicilioDerechohabiente" style="width: 977px">
		<legend><strong>Domicilio del integrante</strong></legend>
		<div id="contenedorDomicilioRecortado"></div>
	</fieldset>
	
	<form>
	<fieldset>
		<table style="width: 100%" id="agregarMod">
			<tr>				
				<td>
					<div style = "text-align: center">
					<c:if test="${empty validaciones or validaciones.correcto}">					
						<input type="button" id="irAConfirmacion" class="mboton" value="<spring:message code="button.aceptar"/>"/>
					</c:if>
					<c:if test="${registro.tramiteId ne null && (empty validaciones || validaciones.correcto)}">
						<input type="button" id="rechazarTramite" class="mboton" value="Cancelar tr&aacute;mite"/>
					</c:if>
					<input type="button" id="regresaraGrupo" name="regresar" class="mboton" value="<spring:message code="button.regresar"/>"/>
					<input type="button" id="guia" name="guia" class="mboton" value="<spring:message code="button.guiaTramite"/>" style="display: none"/>									
					</div>
				</td>			
			</tr>		
		</table>
	</fieldset>	
	</form>	

	
	

	<div id="divComponenteDomicilio"></div>
	<div id="divComponenteBusquedaPersona"></div>
</div>