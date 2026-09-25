<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>
<%@taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<jsp:include page="../general/llenaParentescos.jsp"></jsp:include>
<jsp:include page="../general/llenaEstadoCivil.jsp"></jsp:include>

<script type="text/javascript" src="<spring:url value="/gestionIndividuo-consulta-web/static/resources/js/delta/personas/fisica/identificar/cambios-automaticos/identificar-cambios-automaticos.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/gestionCtrlSelect.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/correccion/datosPersonales/integracionICACorreccion.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/correccion/correccionDerechohabiente.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/correccion/validacionCorreccionDerechohabiente.js?v=1.0" htmlEscape="true" />"></script>


<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<c:if test="${validacion == 1}">
	<%@ include file="/WEB-INF/views/general/fileUpload/FileUploadImport.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
				//Carga el componente
				loadFileUpload('${derechohabiente.tipoTramite.idTipoTramite}', '${lnuevosDocsRequeridos}');
		});			 
	</script>
	
	
</c:if>
<script type="text/javascript">

$(document).ready(
		function() {
			existeAsegurado('${asegurado}','${derechohabiente.parentesco.idParentesco}');
			validateForm.allowOnlyRegularExpression( $('.alfanumerico_espacios'),regularExpression.alfanumerico_espacios);
			validateForm.allowOnlyRegularExpression( $('.alfanumerico'),regularExpression.alfanumerico);
			validateForm.allowOnlyRegularExpression( $('.entero_10'),regularExpression.entero_10);
			//deshabilitarCampos('${derechohabiente.parentesco.idParentesco}');
			
			//findParentescos(${derechohabiente.parentesco.idParentesco});
			setValidacion('${validacion}');
			
			
			$.validator.addMethod("alphanumeric", function(value, element) { 
		        return this.optional(element) || /^[a-z0-9\- ]+$/i.test(value); 
		    }, "Username must contain only letters, numbers, or dashes."); 
	 
			
			
			$("#registro").validate({
				 rules:{ 
			    		nombre:{
			    			required:true,
			    			maxlength:15
			    		},
			    		primerApellido:{
			       			required:true,
			    			maxlength:15
			    		},
			    		segundoApellido:{
			       			required:true,
			    			maxlength:15
			    		},
			    		fechaNacimiento: {
								required:true
						},
						numExteriorAlf:{
							required:true
						},
						curpCap: {
							//required: true,
							minlength: 18,
							maxlength: 18,
							curp: true
						}
						},
			 		messages: { 
			 			fechaNacimiento: {
							required:"Obligatorio"
							},
						nombre:{
								required:"Obligatorio",
								 maxlength:"Debe ser de 15 caracteres como m\u00e1ximo",
								 alphanumeric:"Debe ser alfan\u00famerico"
							},
						primerApellido:{
							required:"Obligatorio",
							 maxlength:"Debe ser de 15 caracteres como m\u00e1ximo",
							 alphanumeric:"Debe ser alfan\u00famerico"
						},
						segundoApellido:{
							required:"Obligatorio",
							 maxlength:"Debe ser de 15 caracteres como m\u00e1ximo",
							 alphanumeric:"Debe ser alfan\u00famerico"
						 },
						 numExteriorAlf:{
								required:"Obligatorio"
							}
						},
						curpCap: {
							minlength: "Debe ser de 18 caracteres",
							maxlength: "Debe ser de 18 caracteres",
							curp: "Formato incorrecto"
						}
			    });
			
		});
	
</script>
<c:if test="${patronIMSS}">
	<input type="hidden" id="patronImss" value="1"/>
</c:if>
<c:if test="${!patronIMSS}">
	<input type="hidden" id="patronImss" value="0"/>
</c:if>
<input type="hidden" id="curpValidada" value=""/>
<input type="hidden" id="idSolicitud" value="${solicitud.solicitudId}"/>
<input type="hidden" id="validacion" value="${validacion}"/>
<input type="hidden" id="idSexoAseg" value="${miGrupoFamiliar.derechohabiente.sexo.idSexo}"/>
<div class="form-comment">
	<br><br>
<c:choose>
<c:when test="${empty errores}">

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
		<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span><span id="mensajeBusquedaPorCurp"></span>
		</p></div>
	</div>
	
		<!-- Apartado de datos del integrante -->
		<br>

	<table style="width: 100%" >
		<tr>	
			<td style="width: 50%">
		     <form>
				<fieldset style="width:95%;height: 370px">
					<legend><strong><spring:message code="label.datosActuales" /></strong></legend>
					<table class="page_holder_no_height" style="width:95%">
						
						<tr>	
							<td ><spring:message code="label.curp" /> : </td>
							<td>
								<input type="text" id="curpActual" name="curpActual" value="${datosActuales.curpCap}" style="width:87%" disabled="disabled" />
							</td>
						</tr>
						<tr>
							<td colspan="2"><br></td>
						</tr>
						<tr>
							<td ><spring:message code="label.nombre" /> : </td>
							<td>
								<input type="hidden" id="idUmfDom" value="${derechohabiente.medicoEnTurno.unidadMedicaFamiliar.idUMF}"/>
								<input type="text" id="nombreActual" name="nombreActual" value="${datosActuales.nombre}" style="width:87%" disabled="disabled" /> 
							</td>
						</tr>
						<tr> 
							<td ><spring:message code="label.primerApe" /> : </td>
							<td>
								<input type="text" id="pApellidoActual" name="pApellidoActual" value="${datosActuales.primerApellido}" disabled="disabled"  style="width:87%" />
							</td>
						</tr>
						<tr>	
							<td ><spring:message code="label.segundoApe" /> : </td>
							<td>
								<input type="text" id="sApellidoActual" name="sApellidoActual" value="${datosActuales.segundoApellido}" disabled="disabled"  style="width:87%" />
							</td>	
						</tr>
						<tr>	
							<td ><spring:message code="label.fechaNac" /> : </td>
							<td>
								<input type="text" id="fechaNacActual"
								value="<fmt:formatDate pattern="dd/MM/yyyy" value="${datosActuales.fechaNacimiento}"/>" disabled="disabled" style="width:87%"/>
							</td>
						</tr>
						<tr>	
							<td ><spring:message code="label.lugarNac" /> : </td>
							<td colspan="3">
								<input type="hidden" id="idLugarNacActual" value="${datosActuales.lugarNacimiento.clave}" />
								<input type="text" id="lugarNacActual" name="lugarNacActual" value="${datosActuales.lugarNacimiento.nombre}"  disabled="disabled" style="width:87%"/> 
							</td>
						</tr>
						<tr>	
							<td ><spring:message code="label.edoCivil" /> : </td>
							<td>
								<input type="hidden" id="idEdoCivilActual" value="${datosActuales.estadoCivil.idEstadoCivil}" />
								<input type="text" value="${datosActuales.estadoCivil.descripcion}" disabled="disabled" style="width:87%"/> 
							</td>		
						</tr>
						<tr>
							<td ><spring:message code="label.parentesco" /> : </td>
							<td>
								<input type="hidden" id="idParentescoActual" value="${datosActuales.parentesco.idParentesco}" />
								<input type="text" id="parentesco" name="parentesco" value="${datosActuales.parentesco.descripcion}" style="width:87%" disabled="disabled" />
							</td>
						</tr>
						<tr>		
							<td ><spring:message code="label.sexo" /> : </td>
							<td>
								<input type="hidden" id="idSexoActual" value="${datosActuales.sexo.idSexo}" />
								<input type="text" id="sexoActual" name="sexoActual" value="${datosActuales.sexo.descripcion}" disabled="disabled" style="width:87%"/> 
							</td>	
						</tr>
						</table>
						</fieldset>
						<fieldset id="mediosAnteriores" style="width:95%;height: 220px">		
						<legend><strong><spring:message code="titulo.mediosContacto"/></strong></legend>			
						<table class="page_holder_no_height" style="width:95%">	
							<tr>
								<td><spring:message code="label.correoElectronico" /> : </td>
								<td>
									<input id="correoEActual" type="text" value="${datosActuales.correoElectronico.correo}" disabled="disabled" style="width:87%"/>							
								</td>
							</tr>
							<tr>
								<td><spring:message code="label.telefonoFijo" /> : </td>
								<td>
									<input id="telefonoActual" type="text" value="${datosActuales.telefonoFijo.claveLada}" disabled="disabled" style="width:87%"/>
								</td>
							</tr>
							<tr>
								<td><spring:message code="label.telefonoMovil" /> : </td>
								<td>
									<input id="celularActual" type="text" value="${datosActuales.telefonoMovil.numero}" disabled="disabled" style="width:87%"/>
								</td>
							</tr>
							<tr>
								<td><spring:message code="label.facebook" /> : </td>
								<td>
									<input id="facebookActual" type="text" value="${datosActuales.facebook.cuenta}" disabled="disabled" style="width:87%"/>					
								</td>
							</tr>
							<tr>
								<td><spring:message code="label.twitter" /> : </td>
								<td>
									<input id="twitterActual" type="text" value="${datosActuales.twitter.cuenta}" disabled="disabled" style="width:87%"/>					
								</td>
							</tr>			
						</table>
						</fieldset>
						<fieldset style="width:95%;height: 670px">
						<legend><strong>Datos del domicilio anterior</strong></legend>
						<table id="domicilioAnterior" class="page_holder_no_height" style="width:95%">
						<tr>
							<td ><spring:message code="label.asentamiento" /> : </td>
							<td>
								<input type="text" value="${datosActuales.domicilio.asentamiento.nombre}" disabled="disabled" style="width:87%"/>
							</td>
						</tr>
						<tr>
							<td>Codigo Postal:</td>
							<td>
								<input type="text" value="${datosActuales.domicilio.codigoPostal.codigoPostal}" style="width:40%" disabled="disabled" />
							</td>
						</tr>
						<tr>	
							<td ><spring:message code="label.tipoVialidad" /> : </td>
							<td>
								<input type="text" value="${datosActuales.domicilio.vialidadPrimaria.tipoVialidad.descripcion}" disabled="disabled" style="width:87%"/>
							</td>
						</tr>
						<tr>	
							<td ><spring:message code="label.nombreV" /> : </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.vialidadPrimaria.nombre}"  disabled="disabled" style="width:87%"/>
							</td>
						</tr>
						<tr>	
							<td ><spring:message code="label.numeroExt" /> : </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.numExterior1}" style="width:40%" disabled="disabled" />
							</td>
						</tr>
						<tr>	
							<td ><spring:message code="label.numeroLExt" /> : </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.numExteriorAlf}" style="width:40%" disabled="disabled" />
							</td>
						</tr>
						<tr>	
							<td ><spring:message code="label.numeroInt" /> : </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.numInterior}" style="width:40%" disabled="disabled" />
							</td>
						</tr>
						<tr>	
							<td ><spring:message code="label.numeroLInt" /> : </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.numInteriorAlf}" style="width:40%" disabled="disabled" />
							</td>
						</tr>
						<tr>	
							<td ><spring:message code="tramite.detalle.numLetraExtNoOficial" /> : </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.numExterior2}" style="width:40%" disabled="disabled" />
							</td>
						</tr>
						<tr>
							<td>
								<strong><spring:message code="label.ref1"/></strong>
							</td>
						</tr>
						<tr>	
							<td ><spring:message code="label.tipoVialidad" /> : </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.vialidadReferenciaPrimaria.tipoVialidad.descripcion}" disabled="disabled" style="width:87%"/>
							</td>
						</tr>
						<tr>
							<td ><spring:message code="label.nombreV" /> : </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.vialidadReferenciaPrimaria.nombre}" disabled="disabled" style="width:87%"/>
							</td>	
						</tr>
						
						<tr>
							<td>
								<strong><spring:message code="label.ref2"/></strong>
							</td>
						</tr>
						<tr>
							<td ><spring:message code="label.tipoVialidad" /> : </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.vialidadReferenciaSecundaria.tipoVialidad.descripcion}" disabled="disabled" style="width:87%"/>
							</td>
						</tr>
						<tr>
							<td ><spring:message code="label.nombreV" /> : </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.vialidadReferenciaSecundaria.nombre}" disabled="disabled" style="width:87%"/>
							</td>
						</tr>
						<tr>
							<td>
								<strong><spring:message code="label.ref3"/></strong>
							</td>
						</tr>
						<tr>
							<td ><spring:message code="label.tipoVialidad" /> : </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.vialidadReferenciaPosterior.tipoVialidad.descripcion}" disabled="disabled" style="width:87%"/>
							</td>
						</tr>
						<tr>
							<td ><spring:message code="label.nombreV" /> : </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.vialidadReferenciaPosterior.nombre}" disabled="disabled" style="width:87%"/>
							</td>
						</tr>
						<tr>
							<td ></td>
							<td>
							</td>
						</tr>
					</table>
					</fieldset>
				</form>
		</td>
		<td style="width: 50%;">
			<form:form id="registro" method="POST" commandName="derechohabiente">	
			<form:hidden path="domicilio.asentamiento.localidad.municipio.entidadFederativa.clave"/>
			<form:hidden path="domicilio.asentamiento.localidad.municipio.clave"/>
			<form:hidden path="domicilio.asentamiento.localidad.clave"/>
			<form:hidden path="nss" />
			<form:hidden path="tramiteId" />
			<form:hidden path="tipoTramite.idTipoTramite" />
			<form:hidden path="domicilio.clave" />
							<fieldset style="width: 95%;height: 370px">
								<legend><strong>Nuevos Datos</strong></legend>
							<table id="registroD" class="page_holder_no_height" style="width:95%">
								<tr>	
									<td ><spring:message code="label.curp" /> : </td>
									<td>
										<form:input path="curpCap" class="alfanumerico" maxlength="18" style="width:87%" />
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
										<form:input path="nombre" class='alfanumerico_espacios' maxlength="15" 
										style="width:87%" /> 
									</td>
								</tr>
								<tr>
									<td ><spring:message code="label.primerApe" /> : </td>
									<td>
										<form:input path="primerApellido" class='alfanumerico_espacios' style="width:87%" />
										<span><form:errors path="primerApellido" cssStyle="color:red"/></span>
									</td>
								</tr>
								<tr>	
									<td ><spring:message code="label.segundoApe" /> : </td>
									<td>
										<form:input path="segundoApellido" class='alfanumerico_espacios' style="width:87%" />
										<span><form:errors path="segundoApellido" cssStyle="color:red"/></span>
									</td>	
								</tr>
								<tr>	
									<td ><spring:message code="label.fechaNac" /> : </td>
									<td>
										<input type="text" id="fechaNacimiento" name="fechaNacimiento"  
										value="<fmt:formatDate pattern="dd/MM/yyyy" value="${derechohabiente.fechaNacimiento}"/>" style="width:50%">
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
										<c:when test="${derechohabiente.parentesco.idParentesco != 5 && derechohabiente.parentesco.idParentesco != 6}">
											<combo:creaCombo 
											 idHtmlValor="${derechohabiente.parentesco.idParentesco}" regEspecial="7" 
											 idHtml="parentesco.idParentesco" idHtmlContenedor="registro" 
											 entidad="mx.gob.imss.ctirss.delta.persistence.DicCalidadParentesco"
											 mostrarSoloActivos = "true" />
				
											<!--<select id="parentesco.idParentesco" name="parentesco.idParentesco" style="width: 315px">
											</select>
											--><div id="errorParentesco" class="error" style="text-align: right;"></div>
										</c:when>
										<c:otherwise>
											<form:hidden path="parentesco.idParentesco" style="width: 45px" />
											<form:input path="parentesco.descripcion" style="width:87%" />
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
							<fieldset id="mediosActuales" style="width: 95%;height: 220px">		
						<legend><strong><spring:message code="titulo.mediosContacto"/></strong></legend>			
						<table class="page_holder_no_height" style="width:87%">	
							<tr>
								<td><spring:message code="label.correoElectronico" /> : </td>
								<td>
									<form:hidden path="correoElectronico.clave"/>
									<form:input path="correoElectronico.correo" style="width:87%"/>								
								</td>
							</tr>
							
							<tr>
								<td><spring:message code="label.telefonoFijo" /> : </td>
								<td>
									<form:hidden path="telefonoFijo.clave"/>
									<form:input class='entero_10' path="telefonoFijo.claveLada" style="width:87%"/>					
								</td>
							</tr>
							<tr>
								<td><spring:message code="label.telefonoMovil" /> : </td>
								<td>
									<form:hidden path="telefonoMovil.clave"/>
									<form:input class='entero_10' path="telefonoMovil.numero" style="width:87%"/>					
								</td>
							</tr>
							<tr>
								<td><spring:message code="label.facebook" /> : </td>
								<td>
									<form:hidden path="facebook.clave"/>
									<form:input path="facebook.cuenta" style="width:87%"/>					
								</td>
							</tr>
							<tr>
								<td><spring:message code="label.twitter" /> : </td>
								<td>
									<form:hidden path="twitter.clave"/>
									<form:input path="twitter.cuenta" style="width:87%"/>					
								</td>
							</tr>			
						</table>
					</fieldset>	
							<fieldset style="width: 95%;height: 670px">
							<legend><strong>Datos del nuevo domicilio</strong></legend>
							<table id="domicilioNuevo" class="page_holder_no_height" style="width:95%">
								<tr>
									<td ><spring:message code="label.asentamiento" /> : </td>
									<td>
										<form:hidden path="domicilio.asentamiento.clave"/>
										<form:input disabled="disabled" path="domicilio.asentamiento.nombre" style="width:87%"/>
									</td>
								</tr>
								<tr>
									<td><spring:message code="label.codigoPos" />:</td>
									<td>
										<form:input path="domicilio.codigoPostal.codigoPostal"  style="width:40%"/>
									</td>
								</tr>
								<tr>	
									<td ><spring:message code="label.tipoVialidad" /> : </td>
									<td>
										<form:hidden path="domicilio.vialidadPrimaria.tipoVialidad.clave"/>
										<form:input path="domicilio.vialidadPrimaria.tipoVialidad.descripcion" style="width:87%"/>
									</td>
								</tr>
								<tr>	
									<td ><spring:message code="label.nombreV" /> : </td>
									<td>
										<form:hidden path="domicilio.vialidadPrimaria.clave"/>
										<form:input path="domicilio.vialidadPrimaria.nombre" style="width:87%"/>
									</td>
								</tr>
								<tr>
									<td><spring:message code="label.numeroExt" /><br></td>
									<td>
									<form:input path="domicilio.numExterior1" style="width:40%"/>
									</td>
								</tr>
								<tr>	
									<td ><spring:message code="label.numeroLExt" /> : </td>
									<td>
										<form:input path="domicilio.numExteriorAlf" style="width:40%"/>
									</td>
								</tr>
								<tr>	
									<td ><spring:message code="label.numeroInt" /> : </td>
									<td>
										<form:input path="domicilio.numInterior" style="width:40%"/>
									</td>
								</tr>
								<tr>	
									<td ><spring:message code="label.numeroLInt" /> : </td>
									<td>
										<form:input path="domicilio.numInteriorAlf" style="width:40%"/>
									</td>
								</tr>
								<tr>	
									<td ><spring:message code="tramite.detalle.numLetraExtNoOficial" /> : </td>
									<td>
										<form:input path="domicilio.numExterior2" style="width:40%"/>
									</td>
								</tr>
								<tr>
									<td>
										<strong><spring:message code="label.ref1"/></strong>
									</td>
								</tr>
								
								<tr>	
									<td ><spring:message code="label.tipoVialidad" /> : </td>
									<td>
										<form:hidden path="domicilio.vialidadReferenciaPrimaria.tipoVialidad.clave"/>
										<form:input path="domicilio.vialidadReferenciaPrimaria.tipoVialidad.descripcion" style="width:87%"/>
									</td>
								</tr>
								<tr>
									<td ><spring:message code="label.nombreV" /> : </td>
									<td>
										<form:hidden path="domicilio.vialidadReferenciaPrimaria.clave"/>
										<form:input path="domicilio.vialidadReferenciaPrimaria.nombre" style="width:87%"/>
									</td>	
								</tr>
								
								<tr>
									<td>
										<strong><spring:message code="label.ref2"/></strong>
									</td>
								</tr>
								<tr>
									<td ><spring:message code="label.tipoVialidad" /> : </td>
									<td>
										<form:hidden path="domicilio.vialidadReferenciaSecundaria.tipoVialidad.clave"/>
										<form:input path="domicilio.vialidadReferenciaSecundaria.tipoVialidad.descripcion" style="width:87%"/>
									</td>
								</tr>
								<tr>
									<td ><spring:message code="label.nombreV" /> : </td>
									<td>
										<form:hidden path="domicilio.vialidadReferenciaSecundaria.clave"/>
										<form:input path="domicilio.vialidadReferenciaSecundaria.nombre" style="width:87%"/>
									</td>
								</tr>
								<tr>
									<td>
										<strong><spring:message code="label.ref3"/></strong>
									</td>
								</tr>
								
								<tr>
									<td ><spring:message code="label.tipoVialidad" /> : </td>
									<td>
										<form:hidden path="domicilio.vialidadReferenciaPosterior.tipoVialidad.clave"/>
										<form:input path="domicilio.vialidadReferenciaPosterior.tipoVialidad.descripcion" style="width:87%"/>
									</td>
								</tr>
								<tr>
									<td ><spring:message code="label.nombreV" /> : </td>
									<td>
										<form:hidden path="domicilio.vialidadReferenciaPosterior.clave"/>
										<form:input path="domicilio.vialidadReferenciaPosterior.nombre" style="width:87%"/>
									</td>
									
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
									
									<form:hidden path="calificacion.idCalificacion"/>
								</tr>
								<tr>
									<td colspan="2" align="center">
										<br>
										<input id="ubicar" type="button" value = "Corregir Domicilio" class="mboton" />
									</td>
								</tr>
							</table>
						</fieldset>
			</form:form>				
		</td>
	</tr>
	</table>
	<fieldset>
		<c:if test="${validacion == 1 }">
			<fieldset>
				<spring:message code="label.observaciones"/> :  <textarea id="observaciones" name="observaciones" style="height: 40px; width: 770px;">${tramite.observacion}</textarea>
			</fieldset>
		</c:if>
	</fieldset>
	<br>
	<c:if test="${validacion == 1 }">
		<jsp:include page="/WEB-INF/views/general/fileUpload/fileUpload.jsp"/>	
		<div id="msgDocumentosProb" title ="<spring:message code="titulo.mensajeAviso"/>" style="display:none"> 
			<spring:message code="msgDocumentosProb"/>		
		</div>
	</c:if>
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
					<input id="rechazarValidacion" type="button" value="<spring:message code="button.cancelar"/>" class="mboton" />
					<input id="regresarGrupoFamiliarValidacion" type="button" value = "<spring:message code="button.regresar"/>" class="mboton" />
					<input id="regresarValidacion" type="button" value="<spring:message code="button.regresar"/>" class="mboton" />
				</div>	
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
</div>

<div id="dialogICA"></div>