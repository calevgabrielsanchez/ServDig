<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>
<%@taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<jsp:include page="../../general/llenaParentescos.jsp"></jsp:include>
<jsp:include page="../../general/llenaEstadoCivil.jsp"></jsp:include>


<script type="text/javascript" src="/gestionIndividuo-consulta-web/static/resources/js/delta/personas/fisica/identificar/cambios-automaticos/identificar-cambios-automaticos.js"></script>

<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="/${mvn.web.app.root}/static/resources/js/delta/validations.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/gestionCtrlSelect.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/correccion/datosPersonales/integracionICACorreccion.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/correccion/datosPersonales/correccionDerechohabiente.js?v1.0" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/correccion/validacionCorreccionDerechohabiente.js?v=1.0" htmlEscape="true" />"></script>

<%--scripts necesarios para el nuevo componente de domicilio --%>
<script type="text/javascript" src="/gestionDomicilios-web/static/resources/js/delta/domicilios/recortado/DomicilioRecortadoCtrl.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/common/setDomicilioCommon.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/domicilio/domicilioCorreccionDatos.js" htmlEscape="true" />"></script>


<c:if test="${validacion == 1}">
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

.Row
{
    display: table-row;
    height: auto;
}
.Column
{
    display: table-cell;
}

.Column fieldset {
	 padding: 0 1.4 1.4em 1.4em;
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

-->
</style>
<div class="form-comment">
	<input type="hidden" id="asegurado" value="${asegurado}"/>
	<input type="hidden" id="idParentescoActual" value="${derechohabiente.parentesco.idParentesco}"/>
	<input type="hidden" id="patronImss" value="${patronIMSS?1:0}"/>
	<input type="hidden" id="curpValidada" value=""/>
	<input type="hidden" id="idSolicitud" value="${solicitud.solicitudId}"/>
	<input type="hidden" id="validacion" value="${validacion}"/>
	<input type="hidden" id="idSexoAseg" value="${miGrupoFamiliar.derechohabiente.sexo.idSexo}"/>
	
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
		<div id="tabla" class="containerRows">
			<div id="filaDatosPersonales" class="Row">
				<div id="datosPersonalesAnteriores" class= "Column">
					<fieldset>
					<legend><strong><spring:message code="label.datosActuales" /></strong></legend>
					<table class="page_holder_no_height">
						
						<tr>	
							<td class="campo"><spring:message code="label.curp" /> : </td>
							<td>
								<input type="text" id="curpActual" name="curpActual" value="${datosActuales.curpCap}" disabled="disabled" />
							</td>
						</tr>
						<tr>
							<td colspan="2"><br></td>
						</tr>
						<tr>
							<td ><spring:message code="label.nombre" /> : </td>
							<td>
								<input type="hidden" id="idUmfDom" value="${derechohabiente.medicoEnTurno.unidadMedicaFamiliar.idUMF}"/>
								<input type="text" id="nombreActual" name="nombreActual" value="${datosActuales.nombre}" disabled="disabled" /> 
							</td>
						</tr>
						<tr> 
							<td ><spring:message code="label.primerApe" /> : </td>
							<td>
								<input type="text" id="pApellidoActual" name="pApellidoActual" value="${datosActuales.primerApellido}" disabled="disabled"/>
							</td>
						</tr>
						<tr>	
							<td ><spring:message code="label.segundoApe" /> : </td>
							<td>
								<input type="text" id="sApellidoActual" name="sApellidoActual" value="${datosActuales.segundoApellido}" disabled="disabled"/>
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
								<input type="text" id="lugarNacActual" name="lugarNacActual" value="${datosActuales.lugarNacimiento.nombre}"  disabled="disabled"/> 
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
								<input type="text" id="parentesco" name="parentesco" value="${datosActuales.parentesco.descripcion}" disabled="disabled" />
							</td>
						</tr>
						<tr>		
							<td ><spring:message code="label.sexo" /> : </td>
							<td>
								<input type="hidden" id="idSexoActual" value="${datosActuales.sexo.idSexo}" />
								<input type="text" id="sexoActual" name="sexoActual" value="${datosActuales.sexo.descripcion}" disabled="disabled"/> 
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
										<input type="button" class="mboton" value="Identificar Cambios" id="llamarIca" style="display: none">
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
										disabled="true">
										<form:hidden path="mesRegistroNac"/>
										<form:hidden path="anioRegistroNac"/>
										<span><form:errors path="fechaNacimiento" cssStyle="color:red"/></span>
									</td>
									
								</tr>
								<!-- Hasta aqui llego -->
								<tr>	
									<td ><spring:message code="label.lugarNac" /> : </td>
									<td >
										<combo:creaCombo
											idHtml="lugarNacimiento.clave"
											idHtmlContenedor="registro"
											entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado" 
											idHtmlValor="${derechohabiente.lugarNacimiento.clave}" 
											mostrarSoloActivos = "true" 
										/>
										<div id="errorLugarNacimiento" class="error" style="text-align: right;"></div>
										<span><form:errors path="lugarNacimiento.clave" cssStyle="color:red"/></span>
									</td>
								</tr>
								
								<tr>	
									<td ><spring:message code="label.edoCivil" /> : </td>
									<td>
										<combo:creaCombo idHtml="estadoCivil.idEstadoCivil" 
											idHtmlContenedor="registro"
											entidad="mx.gob.imss.ctirss.delta.persistence.DicEstadoCivil" 
											idHtmlValor="${derechohabiente.estadoCivil.idEstadoCivil}" 
											mostrarSoloActivos = "true" 
										/>
										<div id="errorEstadoCivil" class="error" style="text-align: right;"></div>
										<span><form:errors path="sexo.idSexo" cssStyle="color:red"/></span>
									</td>		
								</tr>
								
								<tr>	
									<td ><spring:message code="label.parentesco" /> : </td>
									<td>
										<form:hidden path="idPersona" />
										<c:choose>
										<c:when test="${derechohabiente.parentesco.idParentesco != 5 && derechohabiente.parentesco.idParentesco != 6 && enBajaAdministrativa}">
											
											<combo:creaCombo  idHtmlValor="${derechohabiente.parentesco.idParentesco}" 
											regEspecial="7" idHtml="parentesco.idParentesco" idHtmlContenedor="registro" 
											entidad="mx.gob.imss.ctirss.delta.persistence.DicCalidadParentesco"
											mostrarSoloActivos = "true" />
											
											<div id="errorParentesco" class="error" style="text-align: right;"></div>
											
										</c:when>
										<c:otherwise>
											<form:hidden path="parentesco.idParentesco"/>
											<form:input path="parentesco.descripcion" disabled="true"/>
										</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr>		
									<td ><spring:message code="label.sexo" /> : </td>
									<td>
										<combo:creaCombo idHtml="sexo.idSexo"
											idHtmlContenedor="registro"
											entidad="mx.gob.imss.ctirss.delta.persistence.DicSexo" 
											idHtmlValor="${derechohabiente.sexo.idSexo}"
											mostrarSoloActivos = "true" 
										/>
										<div id="errorSexo" class="error" style="text-align: right;"></div>
										<span><form:errors path="sexo.idSexo" cssStyle="color:red"/></span>
									</td>	
								</tr>
							</table>
						</fieldset>
				</div>
			
			</div>
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
			
			
			<div id="filaDomicilios" class="Row">
			<fieldset>
				<legend><strong>Datos del domicilio anterior</strong></legend>
				<div class="Column" id="domicilioAnteriorDiv"></div>
			</fieldset>
			
			<fieldset>
				<legend><strong>Datos del nuevo domicilio</strong></legend>
				<div class="Column" id="domicilioAnteriorDiv"></div>
			</fieldset>
			
			<!-- 
				<div class="Column" id="divDomicilioAnterior">
					<fieldset>
						<legend><strong>Datos del domicilio anterior</strong></legend>
						<table id="domicilioAnterior" class="page_holder_no_height">
						<tr>
							<td class="campo"><spring:message code="label.asentamiento" /> : </td>
							<td>
								<input type="text" value="${datosActuales.domicilio.asentamiento.nombre}" disabled="disabled"/>
							</td>
						</tr>
						<tr>
							<td>Codigo Postal:</td>
							<td>
								<input type="text" value="${datosActuales.domicilio.codigoPostal.codigoPostal}" disabled="disabled" />
							</td>
						</tr>
						<tr>	
							<td > <spring:message code="label.tipoVialidad" /> :  </td>
							<td>
								<input type="text" value="${datosActuales.domicilio.vialidadPrimaria.tipoVialidad.descripcion}" disabled="disabled"/>
							</td>
						</tr>
						<tr>	
							<td > <spring:message code="label.nombreV" /> :  </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.vialidadPrimaria.nombre}"  disabled="disabled"/>
							</td>
						</tr>
						<tr>	
							<td > <spring:message code="label.numeroExt" /> :  </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.numExterior1}" disabled="disabled" />
							</td>
						</tr>
						<tr>	
							<td > <spring:message code="label.numeroLExt" /> :  </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.numExteriorAlf}" disabled="disabled" />
							</td>
						</tr>
						<tr>	
							<td > <spring:message code="label.numeroInt" /> :  </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.numInterior}" disabled="disabled" />
							</td>
						</tr>
						<tr>	
							<td > <spring:message code="label.numeroLInt" /> :  </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.numInteriorAlf}" disabled="disabled" />
							</td>
						</tr>
						<tr>	
							<td > <spring:message code="tramite.detalle.numLetraExtNoOficial" /> :  </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.numExterior2}" disabled="disabled" />
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<strong><spring:message code="label.ref1"/></strong>
							</td>
						</tr>
						<tr>	
							<td > <spring:message code="label.tipoVialidad" /> :  </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.vialidadReferenciaPrimaria.tipoVialidad.descripcion}" disabled="disabled"/>
							</td>
						</tr>
						<tr>
							<td >  <spring:message code="label.nombreV" /> :  </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.vialidadReferenciaPrimaria.nombre}" disabled="disabled"/>
							</td>	
						</tr>
						
						<tr>
							<td colspan="2">
								<strong><spring:message code="label.ref2"/></strong>
							</td>
						</tr>
						<tr>
							<td > <spring:message code="label.tipoVialidad" /> :  </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.vialidadReferenciaSecundaria.tipoVialidad.descripcion}" disabled="disabled"/>
							</td>
						</tr>
						<tr>
							<td > <spring:message code="label.nombreV" /> :  </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.vialidadReferenciaSecundaria.nombre}" disabled="disabled"/>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<strong><spring:message code="label.ref3"/></strong>
							</td>
						</tr>
						<tr>
							<td > <spring:message code="label.tipoVialidad" /> :  </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.vialidadReferenciaPosterior.tipoVialidad.descripcion}" disabled="disabled"/>
							</td>
						</tr>
						<tr>
							<td > <spring:message code="label.nombreV" /> :  </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.vialidadReferenciaPosterior.nombre}" disabled="disabled"/>
							</td>
						</tr>
						<tr>
							<td colspan="2"></td>
						</tr>
					</table>
					</fieldset>
				</div>
				<div class="Column" id="divDomicilioActual">
					<fieldset>
							<legend><strong>Datos del nuevo domicilio</strong></legend>
							<table id="domicilioNuevo" class="page_holder_no_height">
								<tr>
									<td class="campo"> <spring:message code="label.asentamiento" /> :  </td>
									<td>
										<form:hidden path="domicilio.asentamiento.clave"/>
										<form:input 
										path="domicilio.asentamiento.nombre" disabled="true"/>
									</td>
								</tr>
								<tr>
									<td> <spring:message code="label.codigoPos" />: </td>
									<td>
										<form:input path="domicilio.codigoPostal.codigoPostal" disabled="true"/>
									</td>
								</tr>
								<tr>	
									<td > <spring:message code="label.tipoVialidad" /> :  </td>
									<td>
										<form:hidden path="domicilio.vialidadPrimaria.tipoVialidad.clave"/>
										<form:input path="domicilio.vialidadPrimaria.tipoVialidad.descripcion" disabled="true"/>
									</td>
								</tr>
								<tr>	
									<td > <spring:message code="label.nombreV" /> :   </td>
									<td>
										<form:hidden path="domicilio.vialidadPrimaria.clave"/>
										<form:input path="domicilio.vialidadPrimaria.nombre" disabled="true"/>
									</td>
								</tr>
								<tr>
									<td> <spring:message code="label.numeroExt" /><br> </td>
									<td>
									<form:input path="domicilio.numExterior1" disabled="true"/>
									</td>
								</tr>
								<tr>	
									<td > <spring:message code="label.numeroLExt" /> :  </td>
									<td>
										<form:input path="domicilio.numExteriorAlf" disabled="true"/>
									</td>
								</tr>
								<tr>	
									<td > <spring:message code="label.numeroInt" /> :  </td>
									<td>
										<form:input path="domicilio.numInterior" disabled="true"/>
									</td>
								</tr>
								<tr>	
									<td > <spring:message code="label.numeroLInt" /> :  </td>
									<td> 
										<form:input path="domicilio.numInteriorAlf" disabled="true"/>
									</td>
								</tr>
								<tr>	
									<td > <spring:message code="tramite.detalle.numLetraExtNoOficial" /> :  </td>
									<td>
										<form:input path="domicilio.numExterior2" disabled="true"/>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<strong><spring:message code="label.ref1"/></strong>
									</td>
								</tr>
								
								<tr>	
									<td > <spring:message code="label.tipoVialidad" /> :  </td>
									<td>
										<form:hidden path="domicilio.vialidadReferenciaPrimaria.tipoVialidad.clave"/>
										<form:input path="domicilio.vialidadReferenciaPrimaria.tipoVialidad.descripcion" disabled="true"/>
									</td>
								</tr>
								<tr>
									<td > <spring:message code="label.nombreV" /> :  </td>
									<td>
										<form:hidden path="domicilio.vialidadReferenciaPrimaria.clave"/>
										<form:input path="domicilio.vialidadReferenciaPrimaria.nombre" disabled="true"/>
									</td>	
								</tr>
								
								<tr>
									<td colspan="2">
										<strong><spring:message code="label.ref2"/></strong>
									</td>
								</tr>
								<tr>
									<td > <spring:message code="label.tipoVialidad" /> :  </td>
									<td>
										<form:hidden path="domicilio.vialidadReferenciaSecundaria.tipoVialidad.clave"/>
										<form:input path="domicilio.vialidadReferenciaSecundaria.tipoVialidad.descripcion" disabled="true"/>
									</td>
								</tr>
								<tr>
									<td > <spring:message code="label.nombreV" /> :  </td>
									<td>
										<form:hidden path="domicilio.vialidadReferenciaSecundaria.clave"/>
										<form:input path="domicilio.vialidadReferenciaSecundaria.nombre" disabled="true"/>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<strong><spring:message code="label.ref3"/></strong>
									</td>
								</tr>
								
								<tr>
									<td > <spring:message code="label.tipoVialidad" /> :  </td>
									<td>
										<form:hidden path="domicilio.vialidadReferenciaPosterior.tipoVialidad.clave"/>
										<form:input path="domicilio.vialidadReferenciaPosterior.tipoVialidad.descripcion" disabled="true"/>
									</td>
								</tr>
								<tr>
									<td > <spring:message code="label.nombreV" /> :  </td>
									<td>
										<form:hidden path="domicilio.vialidadReferenciaPosterior.clave"/>
										<form:input path="domicilio.vialidadReferenciaPosterior.nombre" disabled="true"/>
									</td>
									
									<form:hidden path="domicilio.calle"/>
									<form:hidden path="domicilio.tipoBusquedaVialidad"/>
							
							
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
											
							
									<form:hidden path="domicilio.domicilioCamino.terminoGeneral.descripcion"/>
									<form:hidden path="domicilio.domicilioCamino.terminoGeneral.clave"/>
									<form:hidden path="domicilio.domicilioCamino.margen.descripcion"/>
									<form:hidden path="domicilio.domicilioCamino.margen.clave"/>
									<form:hidden path="domicilio.domicilioCamino.origen"/>
									<form:hidden path="domicilio.domicilioCamino.destino"/>
									<form:hidden path="domicilio.domicilioCamino.cadenamiento"/>
									
									<form:hidden path="calificacion.idCalificacion"/>
								</tr>
								<tr>
									<td colspan="2" align="center">
										<br>
										<button id="ubicar" class="mboton" >
											Corregir Domicilio
										</button>
									</td>
								</tr>
							</table>
						</fieldset>
				</div>-->
			</div>
			<div id="filaObservaciones" class="Row">
					<fieldset style="position:relative">
						<spring:message code="label.observaciones"/> :  <textarea id="observaciones" name="observaciones" style="height: 40px; width: 770px;">${tramite.observacion}</textarea>
					</fieldset>
			</div>
			<div id="filaDocumentos" class="Row">
				<jsp:include page="/WEB-INF/views/general/fileUpload/fileUpload.jsp"/>	
				<div id="msgDocumentosProb" title ="<spring:message code="titulo.mensajeAviso"/>" style="display:none"> 
					<spring:message code="msgDocumentosProb"/>		
				</div>
			</div>
		
		</div>
	</form:form>


</div>