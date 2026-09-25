<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>
<%@taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<jsp:include page="../general/llenaParentescos.jsp"></jsp:include>
<jsp:include page="../general/llenaEstadoCivil.jsp"></jsp:include>


<script type="text/javascript" src="/gestionIndividuo-consulta-web/static/resources/js/delta/personas/fisica/identificar/cambios-automaticos/identificar-cambios-automaticos.js"></script>

<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="/${mvn.web.app.root}/static/resources/js/delta/validations.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/gestionCtrlSelect.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/correccion/datosPersonales/integracionICACorreccion.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/correccion/datosPersonales/correccionDerechohabiente.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/correccion/validacionCorreccionDerechohabiente.js?v=1.0" htmlEscape="true" />"></script>

<%--scripts necesarios para el nuevo componente de domicilio --%>
<script type="text/javascript" src="/gestionDomicilios-web/static/resources/js/delta/domicilios/recortado/DomicilioRecortadoCtrl.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/common/setDomicilioCommon.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/domicilio/domicilioCorreccionDatosInit.js" htmlEscape="true" />"></script>



<c:if test="${validacion == 1 && requiereDocs} }">

<%@ include file="/WEB-INF/views/general/fileUpload/FileUploadImport.jsp" %>

<script type="text/javascript">
	$(document).ready(function() {
		//Carga el componente
		funcionCallBackPinta = limpiarDocumentos;
		loadFileUpload('${derechohabiente.tipoTramite.idTipoTramite}', '${lnuevosDocsRequeridos}',undefined,'${tipoDocsNoMostrar}','${idsDocsNoMostrar}');		
	});			 
</script>
	
</c:if>
<style>
<!--
.containerRows {
	display: table;
	table-layout: fixed;
    border-spacing: 10px;
}

.contenedorPantalla {
	width: 100%;
	height: 100%; 
}
.Row
{
    display: table-row;
    height: 100%;
}
.Column
{
    display: table-cell;
}

.Column fieldset {
	 padding: 0 1.4 1.4em 1.4em;
	 height: 100%
}

div#tabla table {
	 text-align: left; 
	 width: 100%;
}


div#tabla table input {
	 text-align: left; 
	 width: 200px;
	 float: left;
}

.campo {
	width: 150px
}

.divSeparador {
	height: 10px
}
-->
</style>
<div class="form-comment">
	<input type="hidden" id="asegurado" value="${asegurado}"/>
	<input type="hidden" id="patronImss" value="${patronIMSS?1:0}"/>
	<input type="hidden" id="curpValidada" value=""/>
	<input type="hidden" id="idSolicitud" value="${solicitud.solicitudId}"/>
	<input type="hidden" id="validacion" value="${validacion}"/>
	<input type="hidden" id="idSexoAseg" value="${miGrupoFamiliar.derechohabiente.sexo.idSexo}"/>
	<input type="hidden" id="tieneAcuerdo" value="${hijo.indAcuerdo}"/>
	<input type="hidden" value="${usuarioObj.idUmf}" id="idUmfUsuario"/>
	
	<input id="requiereDocs" type="hidden" value="${requiereDocs?1:0}"/>
	
	<br><br><br>
	<c:if test="${validacion == 1}">
		<h4 align="center">VALIDACI&Oacute;N DE CORRECCI&Oacute;N DE DATOS DE DERECHOHABIENTE</h4>
	</c:if>
	<c:if test="${validacion == 0}">
		<h4 align="center">CORRECCI&Oacute;N DE DATOS DE DERECHOHABIENTE</h4>
	</c:if>
	<jsp:include page="/WEB-INF/views/prorrogas/grupoFamiliar.jsp"></jsp:include>
	<br>
	<div id="mensajeConfirmacion"></div>
	<div class="ui-widget" id="mensajeCurp" style="display:none;">
		<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
			<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span><span id="mensajeBusquedaPorCurp"></span></p>
		</div>
	</div>

	<form:form id="registro" method="POST" commandName="derechohabiente">	
			<form:hidden path="nss" />
			<form:hidden path="tramiteId" />
			<form:hidden path="tipoTramite.idTipoTramite" />
			<form:hidden path="idEstadoDerechohabiente" />
			<form:hidden path="parentescoActual.idParentesco" />
			<form:hidden path="indicadorRN"/>
			<form:hidden path="domicilio.clave" />
			<form:hidden path="indICATeniaActa"/>
		<div id="tabla" class="containerRows contenedorPantalla">
			<div id="filaDatosPersonales" class="Row">
				<div id="datosPersonalesAnteriores" class= "Column">
					<fieldset>
					<legend><strong><spring:message code="label.datosActuales" /></strong></legend>
					<table class="page_holder_no_height">
						
						<tr>	
							<td class="campo"><spring:message code="label.curp" /> : </td>
							<td>
								<input type="text" id="curpActual" value="${datosActuales.curpCap}" disabled="disabled" />
							</td>
						</tr>
						<tr>
							<td colspan="2"><br></td>
						</tr>
						<tr>
							<td ><spring:message code="label.nombre" /> : </td>
							<td>
								<input type="hidden" id="idUmfDom" value="${derechohabiente.medicoEnTurno.unidadMedicaFamiliar.idUMF}"/>
								<input type="text" id="nombreActual" value="${datosActuales.nombre}" disabled="disabled" /> 
							</td>
						</tr>
						<tr> 
							<td ><spring:message code="label.primerApe" /> : </td>
							<td>
								<input type="text" id="pApellidoActual"  value="${datosActuales.primerApellido}" disabled="disabled"/>
							</td>
						</tr>
						<tr>	
							<td ><spring:message code="label.segundoApe" /> : </td>
							<td>
								<input type="text" id="sApellidoActual" value="${datosActuales.segundoApellido}" disabled="disabled"/>
							</td>	
						</tr>
						<tr>	
							<td ><spring:message code="label.fechaNac" /> : </td>
							<td>
								<input type="text" id="fechaNacActual"
								value="<fmt:formatDate pattern="dd/MM/yyyy" value="${datosActuales.fechaNacimiento}"/>" disabled="disabled"/>
							</td>
						</tr>
						<tr>	
							<td ><spring:message code="label.lugarNac" /> : </td>
							<td colspan="3">
								<input type="hidden" id="idLugarNacActual" value="${datosActuales.lugarNacimiento.clave}" />
								<input type="text" id="lugarNacActual" value="${datosActuales.lugarNacimiento.nombre}"  disabled="disabled"/> 
							</td>
						</tr>
						<tr>	
							<td ><spring:message code="label.edoCivil" /> : </td>
							<td>
								<input type="hidden" id="idEdoCivilActual" value="${datosActuales.estadoCivil.idEstadoCivil}" />
								<input type="text" value="${datosActuales.estadoCivil.descripcion}" disabled="disabled"/> 
							</td>		
						</tr>
						<tr>
							<td ><spring:message code="label.parentesco" /> : </td>
							<td>
								<input type="hidden" id="idParentescoActual" value="${datosActuales.parentesco.idParentesco}" />
								<input type="text" id="parentesco" value="${datosActuales.parentesco.descripcion}" disabled="disabled" />
							</td>
						</tr>
						<tr>		
							<td ><spring:message code="label.sexo" /> : </td>
							<td>
								<input type="hidden" id="idSexoActual" value="${datosActuales.sexo.idSexo}" />
								<input type="text" id="sexoActual" value="${datosActuales.sexo.descripcion}" disabled="disabled"/> 
							</td>	
						</tr>
						</table>
					</fieldset>
				</div>
				<div id="datosPersonaesActuales"  class= "Column">
					<fieldset>
								<legend><strong>Nuevos Datos</strong></legend>
							<table id="registroD" class="page_holder_no_height">
								<tr>	
									<td class="campo"><spring:message code="label.curp" /> : </td>
									<td>
										<form:input path="curpCap" class="alfanumerico" maxlength="18"/>
										<span><form:errors path="curpCap" cssStyle="color:red"/></span>
									</td>
								</tr>
								<tr>
									<td/>
									<td align="left">
										<button type="button" class="mboton" id="llamarIca" style="display: none">
										Identificar Cambios
										</button>
									</td>
								</tr>	
								<tr>
									<td ><spring:message code="label.nombre" /> : </td>
									<td>
										<form:input path="nombre" class='alfanumerico_espacios' maxlength="15" disabled="true"/> 
									</td>
								</tr>
								<tr>
									<td ><spring:message code="label.primerApe" /> : </td>
									<td>
										<form:input path="primerApellido" class='alfanumerico_espacios' disabled="true"/>
										<span><form:errors path="primerApellido" cssStyle="color:red"/></span>
									</td>
								</tr>
								<tr>	
									<td ><spring:message code="label.segundoApe" /> : </td>
									<td>
										<form:input path="segundoApellido" class='alfanumerico_espacios' disabled="true"/>
										<span><form:errors path="segundoApellido" cssStyle="color:red"/></span>
									</td>	
								</tr>
								<tr>	
									<td ><spring:message code="label.fechaNac" /> : </td>
									<td>
										<input type="text" id="fechaNacimiento" name="fechaNacimiento"  
										value="<fmt:formatDate pattern="dd/MM/yyyy" value="${derechohabiente.fechaNacimiento}"/>"
										disabled="true" onkeydown="event.preventDefault()" >
										<form:hidden path="mesRegistroNac"/>
										<form:hidden path="anioRegistroNac"/>
										<span><form:errors path="fechaNacimiento" cssStyle="color:red"/></span>
									</td>
									
								</tr>
								<!-- Hasta aqui llego -->
								<tr>	
									<td ><spring:message code="label.lugarNac" /> : </td>
									<td >
										<c:if test="${validacion == 1}">
											<form:input path="lugarNacimiento.nombre" class='alfanumerico_espacios' disabled="true"/>
											<form:hidden path="lugarNacimiento.clave"/>
										</c:if>
										<c:if test="${validacion == 0}">
											<combo:creaCombo
												idHtml="lugarNacimiento.clave"
												idHtmlContenedor="registro"
												entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado" 
												idHtmlValor="${derechohabiente.lugarNacimiento.clave}" 
												mostrarSoloActivos = "true" 
											/>
											<form:hidden path="lugarNacimiento.nombre"/>
										</c:if>
										<div id="errorLugarNacimiento" class="error" style="text-align: right;"></div>
										<span><form:errors path="lugarNacimiento.clave" cssStyle="color:red"/></span>
									</td>
								</tr>
								
								<tr>	
									<td ><spring:message code="label.edoCivil" /> : </td>
									<td>
										<c:if test="${validacion == 1}">
											<form:input path="estadoCivil.descripcion" disabled="true"/>
											<form:hidden path="estadoCivil.idEstadoCivil"/>
										</c:if>
										<c:if test="${validacion == 0}">
											<combo:creaCombo idHtml="estadoCivil.idEstadoCivil" 
												idHtmlContenedor="registro"
												entidad="mx.gob.imss.ctirss.delta.persistence.DicEstadoCivil" 
												idHtmlValor="${derechohabiente.estadoCivil.idEstadoCivil}" 
												mostrarSoloActivos = "true" 
											/>
											<form:hidden path="estadoCivil.descripcion"/>
										</c:if>
										<div id="errorEstadoCivil" class="error" style="text-align: right;"></div>
										<span><form:errors path="sexo.idSexo" cssStyle="color:red"/></span>
									</td>		
								</tr>
								
								<tr>	
									<td ><spring:message code="label.parentesco" /> : </td>
									<td>
										<form:hidden path="idPersona" />
										<c:if test="${validacion == 1}">
											<form:input path="parentesco.descripcion" disabled="true"/>
											<form:hidden path="parentesco.idParentesco"/>
										</c:if>
										<c:if test="${validacion == 0}">
											<c:choose>
											<c:when test="${derechohabiente.parentesco.idParentesco != 5 && derechohabiente.parentesco.idParentesco != 6 && enBajaAdministrativa}">
												
												<combo:creaCombo  idHtmlValor="${derechohabiente.parentesco.idParentesco}" 
												regEspecial="7" idHtml="parentesco.idParentesco" idHtmlContenedor="registro" 
												entidad="mx.gob.imss.ctirss.delta.persistence.DicCalidadParentesco"
												mostrarSoloActivos = "true" />
												<form:hidden path="parentesco.descripcion"/>
												<div id="errorParentesco" class="error" style="text-align: right;"></div>
												
											</c:when>
											<c:otherwise>
												<form:hidden path="parentesco.idParentesco"/>
												<form:input path="parentesco.descripcion" disabled="true"/>
											</c:otherwise>
											</c:choose>
										</c:if>
									</td>
								</tr>
								<tr>		
									<td ><spring:message code="label.sexo" /> : </td>
									<td>
										<c:if test="${validacion == 1}">
											<form:input path="sexo.descripcion" disabled="true"/>
											<form:hidden path="sexo.idSexo"/>
										</c:if>
										<c:if test="${validacion == 0}">
											<combo:creaCombo idHtml="sexo.idSexo"
												idHtmlContenedor="registro"
												entidad="mx.gob.imss.ctirss.delta.persistence.DicSexo" 
												idHtmlValor="${derechohabiente.sexo.idSexo}"
												mostrarSoloActivos = "true" 
											/>
											<form:hidden path="sexo.descripcion"/>
										</c:if>
										<div id="errorSexo" class="error" style="text-align: right;"></div>
										<span><form:errors path="sexo.idSexo" cssStyle="color:red"/></span>
									</td>	
								</tr>
							</table>
						</fieldset>
				</div>
			</div>
			<div id="separadordatosmedios" class="Row divSeparador"></div>
			<div id="filaMediosDeContacto" class="Row">
				<div class="Column" id="mediosAnteriores">
					<fieldset>		
						<legend><strong><spring:message code="titulo.mediosContacto"/></strong></legend>			
						<table class="page_holder_no_height">	
							<tr>
								<td class="campo"><spring:message code="label.correoElectronico" /> : </td>
								<td>
									<input id="correoEActual" type="text" value="${datosActuales.correoElectronico.correo}" disabled="disabled"/>							
								</td>
							</tr>
							<tr>
								<td><spring:message code="label.telefonoFijo" /> : </td>
								<td>
									<input id="telefonoActual" type="text" value="${datosActuales.telefonoFijo.claveLada}" disabled="disabled"/>
								</td>
							</tr>
						</table>
						</fieldset>
				</div>
				<div class="Column" id="mediosActuales">
					<fieldset>		
						<legend><strong><spring:message code="titulo.mediosContacto"/></strong></legend>			
						<table class="page_holder_no_height">	
							<tr>
								<td class="campo"><spring:message code="label.correoElectronico" /> : </td>
								<td>
									<form:hidden path="correoElectronico.clave"/>
									<form:input path="correoElectronico.correo"/>								
								</td>
							</tr>
							
							<tr>
								<td><spring:message code="label.telefonoFijo" /> : </td>
								<td>
									<form:hidden path="telefonoFijo.clave"/>
									<form:input class='entero_10' path="telefonoFijo.claveLada"/>					
								</td>
							</tr>
						</table>
					</fieldset>	
				</div>
				
				</div>
			</div>
			
			
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
				
			</form:form>
			<div id="tabla" class="containerRows contenedorPantalla">
			<div id="separadorMediosDomicilio" class="Row divSeparador"></div>
			<div id="filaDomicilios" class="Row">
						 <fieldset>
				<legend><strong>Datos del domicilio anterior</strong></legend>
				<div id="domicilioAnteriorDiv"></div>
			</fieldset>
			
			<fieldset>
				<legend><strong>Datos del nuevo domicilio</strong></legend>
				<div id="domicilioActualDiv"></div>
			</fieldset>
			
			</div>
		 </div> 
		
		<div id="tablaB" class="containerRows contenedorPantalla">
		<c:if test="${validacion == 1}">
			<div id="filaObservaciones" class="Row">
				<fieldset style="position:relative" style="width: 100%">
					<legend><strong>Capture sus observaciones por favor</strong></legend>
					<table style="width: 100%;">
						<tr>
							<td style="width: 20%;"><spring:message code="label.observaciones"/> :  </td>
							<td><textarea id="observaciones" name="observaciones" style="height: 40px; width: 90%;">${tramite.observacion}</textarea></td>
						</tr>
					</table>
				</fieldset>
			</div>
			<c:if test="${requiereDocs}">
			
			<div id="filaDocumentos" class="Row">
				<jsp:include page="/WEB-INF/views/general/fileUpload/fileUpload.jsp"/>	
				<div id="msgDocumentosProb" title ="<spring:message code="titulo.mensajeAviso"/>" style="display:none"> 
					<spring:message code="msgDocumentosProb"/>		
				</div>
			</div>
			</c:if>
		</c:if>
		</div>
		
		<br/><br/>
	<div id="ubicarDomi"></div>
	<div align="center">
		<form>
			<table>
			<tr>
				<td align="center">
				<div id="botones">
					<input id="aceptar" type="button" value = "Aceptar" class="mboton" />
					<input id="regresarGrupoFamiliar" type="button" value = "<spring:message code="button.regresar"/>" class="mboton" />
					<input id="regresar" type="button" value = "<spring:message code="button.regresar"/>" class="mboton" />	
					<input id="aceptarValidacion" type="button" value = "Aceptar" class="mboton" />
					<input id="rechazarTramite" type="button" value="<spring:message code="button.cancelar"/>" class="mboton" />
					<input id="regresarGrupoFamiliarValidacion" type="button" value = "<spring:message code="button.regresar"/>" class="mboton" />
					<input id="regresarValidacion" type="button" value="<spring:message code="button.regresar"/>" class="mboton" />
				</div>	
				</td>
			</tr>
			</table>
			</form>
	</div>
</div>
<div id="dialogICA"></div>