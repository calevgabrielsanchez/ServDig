<%@ include file="/WEB-INF/views/general/taglibs.jsp" %>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>	
<%@ include file="/WEB-INF/views/general/GuiaTramite/guiaTramiteImport.jsp" %>

<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/busqueda/BusquedaPersonaIntegrante.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/registroDerechohabiente.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/gestionCtrlSelect.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>

<script>
		var contextPath = "<%=request.getContextPath()%>";
</script>

<div class="form-comment">
<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<form:form commandName="datos"  id="frmRegDerechohabiente" name="frmRegDerechohabiente" action="/${mvn.web.app.root}/derechohabientes/registro/confirmacion" method="POST">
	<br><br><br>							
	<jsp:include page="/WEB-INF/views/general/encabezadoGF.jsp"/>
	
	<fieldset style="width: 890px">	
	<legend><strong>Datos del integrante dentro del grupo familiar</strong></legend>	
	<div id="datosDentroDelGrupo">
		<table class="page_holder_no_height" style="width: 860px">
		<tr>
			<th align="right">
				<div id="parentAseg" style="display: none"><spring:message code="label.calidad" />:</div>
				<div id="parentInteg" style="display: none"><spring:message code="label.parentesco" />:</div>
			</th>
			<td align="left">
				<combo:creaCombo
					idHtmlValor="${datos.tramiteRegistro.parentesco.idParentesco}"
					regEspecial="${datos.conAsegurado}"
					idHtml="tramiteRegistro.parentesco.idParentesco"
					idHtmlContenedor="frmRegDerechohabiente"
					entidad="mx.gob.imss.ctirss.delta.persistence.DicCalidadParentesco" 
					mostrarSoloActivos = "true" 
				/>
				<div id="msgParentesco" title="<spring:message code="titulo.mensajeAviso"/>" style="display: none">
					<spring:message code="msgParentesco" />
				</div>
			</td>
			
			<th id="conRN" style="display: none">
				<c:if test="${datos.tipoRegistro.idRazonRegistro == 2 }">
					<input type="checkbox" id="recienNacido" name="recienNacido" checked="checked">
				</c:if>
				<c:if test="${datos.tipoRegistro.idRazonRegistro != 2 }">
					<input type="checkbox" id="recienNacido" name="recienNacido">
				</c:if>
				
				 <spring:message code="label.rn" />
			</th>
			<td id="sinRN">&nbsp;</td>
			<td>&nbsp;</td>
			</tr>
			<tr id="unaRazonRegistro">
			<th align="right"><spring:message code="label.registro" />:</th>
			<td align="left">
				<combo:creaCombo
					idHtml="tramiteRegistro.razonRegistro.idRazonRegistro"
					regEspecial="${datos.conAsegurado}"
					idHtmlValor="${datos.tramiteRegistro.razonRegistro.idRazonRegistro}"
					actor="${elemento}" idHtmlContenedor="frmRegDerechohabiente"
					entidadPadre="mx.gob.imss.ctirss.delta.persistence.DicCalidadParentesco"
					idHtmlPadre="tramiteRegistro.parentesco.idParentesco"
					entidad="mx.gob.imss.ctirss.delta.persistence.DicRazonRegistro"
					mostrarSoloActivos = "true" 
				/>

			<div id="msgRazonRegistro" title="<spring:message code="titulo.mensajeAviso"/>"
				style="display: none"><spring:message code="msgRazonRegistro" />
			</div>
			</td>
			<td colspan="2"/>
		</tr>
		<tr>
			<td style="display: none">
				<combo:creaCombo
					idHtml="tipoRegistro.idRazonRegistro"
					idHtmlValor="${datos.tipoRegistro.idRazonRegistro}"
					regEspecial="${datos.conAsegurado}"
					idHtmlContenedor="frmRegDerechohabiente"
					entidad="mx.gob.imss.ctirss.delta.persistence.DicRazonRegistro2" 
					mostrarSoloActivos = "true" 
				/>
				<div id="msgTipoRegistro"
					title="<spring:message code="titulo.mensajeAviso"/>"
					style="display: none"><spring:message code="msgTipoRegistro" />
				</div>
			</td>
		</tr>
		<tr id="sinRazonRegistro">
			<th align="right">&nbsp;</th>
			<td align="left">&nbsp;
			<div id="msgRazonRegistro" title="<spring:message code="titulo.mensajeAviso"/>" style="display: none"><spring:message code="msgRazonRegistro" />
			</div>
			</td>
			<td colspan="2">
		</tr>
		</table>
	</div>
	</fieldset>
	<fieldset style="width: 890px">		
	<legend><strong><spring:message code="titulo.persona"/></strong></legend>
	<table class="page_holder_no_height" style="width: 860px">
		<tr>
			<td>
				<div id="personaFis"></div>
				<div id="domicilioLocaliza"></div>
				
				<input type="hidden" id="perfil" name="perfil" value="<c:out value="${datos.miUsuario.perfilUsuario.idPerfilUsuario}"/>"/>
				<form:hidden path="tramiteRegistro.tramiteId"/>
				<form:hidden path="tramiteRegistro.tipoTramite.idTipoTramite"/>
				<form:hidden path="tramiteRegistro.tipoTramite.descripcion"/>
				<form:hidden path="tramiteRegistro.estadoTramite.idEstadoTramitePersona"/>
				<input type="hidden" id="registrado" name="registrado" value="${registrado}"/> 
				<input type="hidden" id="vigencia" name="vigencia" value="${datos.vigencia}"/> 
				<input type="hidden" id="elemento" name="elemento" value="${datos.elemento}"/> 
				<input type="hidden" id="idSexoAseg" name="idSexoAseg" value="${datos.datosAsegurado.sexo.idSexo}"/>
				<input type="hidden" id="pApellidoAseg" name="pApellidoAseg" value="${datos.datosAsegurado.primerApellido}"/>
				<input type="hidden" id="conAsegurado" name="conAsegurado" value="${datos.conAsegurado}"/> 
				<input type="hidden" id="razonR" name="razonR" value="${razonR}"/> 
				<input type="hidden" id="mc" name="mc" value="${datos.mc}"/> 
				<input type="hidden" id="proceso" name="proceso" value="${datos.proceso}"/> 
				<input type="hidden" id="miParentesco" name="miParentesco" value="${datos.tramiteRegistro.parentesco.idParentesco}"/>
				<input type="hidden" id="miEstadoCivil" name="miEstadoCivil" value="${datos.tramiteRegistro.fisica.estadoCivil.idEstadoCivil}"/>
				<input type="hidden" id="imss" name="imss" value=" ${datos.patronImss}"/>
				<input type="hidden" id="razonRegistroTramite" value="${datos.tipoRegistro.idRazonRegistro}"/>
			</td>
		</tr>
	</table>
	
	
	<table class="page_holder_no_height" style="width: 860px">
		
	</table>
	<table class="page_holder_no_height" style="width: 860px;">
		<tbody>
		<tr>
			<td colspan="4"><br></td>
		</tr>
		<tr>
			<th align="right"><spring:message code="label.curp" />&nbsp;:&nbsp;</th>
			<td><form:input path="tramiteRegistro.fisica.curp"
				value="${registro.fisica.curp}" type="hidden" /> 
				
				<input
				class="alfanumerico_espacios" id="miCurp" name="miCurp"
				value="${datos.tramiteRegistro.fisica.curp}" style="width: 200px"
				maxlength="18" type="text"/>
				
				<div id="msgCurp"
					title="<spring:message code="titulo.mensajeAviso"/>"
					style="display: none"><spring:message code="msgCurp" /></div>
				<div id="msgDatosCurp"
					title="<spring:message code="titulo.mensajeAviso"/>"
					style="display: none"><spring:message code="msgDatosCurp" /></div>
			</td>
			<td colspan="2">
				<input type="button" class="mboton" id = "buscarXCurp" value="Buscar persona"/>
			</td>
		</tr>
		<tr>
			<td colspan="4"><br></td>
		</tr>
		<tr>
			<th align="right"><spring:message code="label.nombre" />&nbsp;:&nbsp;</th>
			<td>
				<form:input path="tramiteRegistro.fisica.idPersona"
				type="hidden" value="${registro.fisica.idPersona}" /> 
				
				<form:input
				maxlegth="200" path="tramiteRegistro.fisica.nombre"
				value="${registro.fisica.nombre}" type="hidden" /> 
				
				<input
				class="alfabetico_espacios" maxlength="200" tabindex="2"
				id="miNombre" name="miNombre"
				value="${datos.tramiteRegistro.fisica.nombre}" style="width: 200px"
				type="text"/>
			<div id="msgNombre"
				title="<spring:message code="titulo.mensajeAviso"/>"
				style="display: none"><spring:message code="msgNombre" /></div>
			</td>
			
			<th align="right"><spring:message code="label.lugarNac" />&nbsp;:&nbsp;</th>
			<td><form:input
				path="tramiteRegistro.fisica.lugarNacimiento.clave"
				value="${registro.fisica.lugarNacimiento.clave}" type="hidden" /> 
				
				<combo:creaCombo
				idHtmlValor="${datos.tramiteRegistro.fisica.lugarNacimiento.clave}"
				idHtml="lugarNacimiento" idHtmlContenedor="frmRegDerechohabiente"
				entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado" 
				mostrarSoloActivos = "true" />
			<div id="msgLugarNacimiento"
				title="<spring:message code="titulo.mensajeAviso"/>"
				style="display: none"><spring:message
				code="msgLugarNacimiento" /></div>
			</td>
		</tr>
		<tr>
			<th align="right"><spring:message code="label.primerApe" />&nbsp;:&nbsp;</th>
			<td><form:input path="tramiteRegistro.fisica.primerApellido"
				value="${registro.fisica.primerApellido}" type="hidden" /> 
				
				<input
				class="alfabetico_espacios" maxlength="200" 
				id="miPrimerApellido" name="miPrimerApellido"
				value="${datos.tramiteRegistro.fisica.primerApellido}"
				style="width: 200px" type="text" />
			<div id="msgPrimerApellido"
				title="<spring:message code="titulo.mensajeAviso"/>"
				style="display: none"><spring:message code="msgPrimerApellido" />
			</div>
			</td>
			
			<th align="right"><spring:message code="label.sexo" />&nbsp;:&nbsp;</th>
			<td><form:input path="tramiteRegistro.fisica.sexo.idSexo"
				type="hidden" /> <combo:creaCombo
				idHtmlValor="${datos.tramiteRegistro.fisica.sexo.idSexo}"
				idHtml="sexoR" idHtmlContenedor="frmRegDerechohabiente"
				entidad="mx.gob.imss.ctirss.delta.persistence.DicSexo" 
				mostrarSoloActivos = "true" />
			<div id="msgSexo"
				title="<spring:message code="titulo.mensajeAviso"/>"
				style="display: none"><spring:message code="msgSexo" /></div>
			<div id="msgMismoSexo"
				title="<spring:message code="titulo.mensajeAviso"/>"
				style="display: none"><spring:message code="msgMismoSexo" /></div>
			<div id="msgSexoPadres"
				title="<spring:message code="titulo.mensajeAviso"/>"
				style="display: none"><spring:message code="msgSexoPadres" />
			</div>
			</td>
			
		</tr>
		<tr>
			<th align="right"><spring:message code="label.segundoApe" />&nbsp;:&nbsp;</th>
			<td><form:input path="tramiteRegistro.fisica.segundoApellido"
				value="${registro.fisica.segundoApellido}" type="hidden" /> 
				
				<input
				class="alfabetico_espacios" maxlength="200" type="text"
				id="miSegundoApellido" name="miSegundoApellido"
				value="${datos.tramiteRegistro.fisica.segundoApellido}"
				style="width: 200px" />
			<div id="msgSegundoApellido"
				title="<spring:message code="titulo.mensajeAviso"/>"
				style="display: none"><spring:message
				code="msgSegundoApellido" /></div>
			</td>
			
			<th align="right"><spring:message code="label.edoCivil" />&nbsp;:&nbsp;</th>
			<td>
				
				<combo:creaCombo
				idHtml="tramiteRegistro.fisica.estadoCivil.idEstadoCivil"
				regEspecial="${datos.conAsegurado}"
				idHtmlValor="${datos.tramiteRegistro.fisica.estadoCivil.idEstadoCivil}"
				actor="${elemento}" idHtmlContenedor="frmRegDerechohabiente"
				entidadPadre="mx.gob.imss.ctirss.delta.persistence.DicCalidadParentesco"
				idHtmlPadre="tramiteRegistro.parentesco.idParentesco"
				entidad="mx.gob.imss.ctirss.delta.persistence.DicEstadoCivil" 
				mostrarSoloActivos = "true" />

			<div id="msgEstadoCivil"
				title="<spring:message code="titulo.mensajeConfirmacion"/>"
				style="display: none"><spring:message code="msgEstadoCivil" />
			</div>
			</td>
		</tr>
		<tr>
			<th align="right"><spring:message code="label.fechaNac" />&nbsp;:&nbsp;</th>
			<td><form:input
				path="tramiteRegistro.fisica.fechaNacimientoFormateada" tabindex="5"
				value="${registro.fisica.fechaNacimientoFormateada}" type="hidden" />
				
				<input id="miFechaNacimientoFormateada"
				name="miFechaNacimientoFormateada" tabindex="5"
				value="${datos.tramiteRegistro.fisica.fechaNacimientoFormateada}"
				style="width: 150px" 
				type="text"/>
				
				<form:hidden path="tramiteRegistro.fisica.mesRegistroNac" value="${datos.tramiteRegistro.fisica.mesRegistroNac}"/>
				<form:hidden path="tramiteRegistro.fisica.anioRegistroNac" value="${datos.tramiteRegistro.fisica.anioRegistroNac}"/>
				
				<div id="msgFechaNacimiento"
					title="<spring:message code="titulo.mensajeAviso"/>"
					style="display: none">
					<spring:message code="msgFechaNacimiento" />
				</div>
			</td>
			
			<td colspan="2"/>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td align="right"><input disabled="disabled" type="hidden"
				id="calificacion" name="calificacion" /></td>
		</tr>
		</tbody>
	</table>
	</fieldset>
	
	<!-- Inicia el fieldset de medios de contacto-->
	<fieldset style="width: 890px">
	
	<legend><strong><spring:message code="titulo.mediosContacto" /></strong></legend>
	
	<table class="page_holder_no_height">
		<tr>
			<th align="right"><spring:message code="label.telefonoFijo" />&nbsp;:&nbsp;</th>
			<td>
				<form:hidden path="tramiteRegistro.fisica.telefonoFijo.clave"/>
				
				<form:input path="tramiteRegistro.fisica.telefonoFijo.claveLada" style="width: 150px"
				value="${datos.tramiteRegistro.fisica.telefonoFijo.claveLada}" type="hidden" />
				
				<input maxlength="15" class='entero_10' id="miTelefonoFijo" name="miTelefonoFijo" style="width: 200px"
				value="${datos.tramiteRegistro.fisica.telefonoFijo.claveLada}" type="text"/>
			</td>
			
			<th  align="right"><spring:message code="label.telefonoMovil" />&nbsp;:&nbsp;</th>
			<td>
				<form:hidden
				path="tramiteRegistro.fisica.telefonoMovil.clave"/> 
				
				<form:input path="tramiteRegistro.fisica.telefonoMovil.numero"
				style="width: 150px" value="${datos.tramiteRegistro.fisica.telefonoMovil.numero}" type="hidden" /> 
				
				<input class='entero_10' id="miTelefonoMovil" name="miTelefonoMovil" style="width: 200px" maxlength="15"
				value="${datos.tramiteRegistro.fisica.telefonoMovil.numero}" type="text"/>
			</td>
				
		</tr>
		<tr>
			<th  align="right"><spring:message code="label.correoElectronico" />&nbsp;:&nbsp;</th>
			<td>
			
				<form:hidden	path="tramiteRegistro.fisica.correoElectronico.clave"/>
				<form:input path="tramiteRegistro.fisica.correoElectronico.correo" style="width: 150px"
				value="${datos.tramiteRegistro.fisica.correoElectronico.correo}" type="hidden" />
				<input id="miCorreoElectronico" name="miCorreoElectronico" style="width: 200px" maxlength="100" 
				value="${datos.tramiteRegistro.fisica.correoElectronico.correo}"	type="text"/>
				<div id="msgCorreo"	title="<spring:message code="titulo.mensajeAviso"/>" style="display: none"><spring:message code="msgCorreo" /></div>
			</td>
			
			<th align="right"><spring:message code="label.facebook" />&nbsp;:&nbsp;</th>
			<td>
				<form:hidden path="tramiteRegistro.fisica.facebook.clave"/> 
				
				<form:input path="tramiteRegistro.fisica.facebook.cuenta" style="width: 150px" 
				value="${datos.tramiteRegistro.fisica.facebook.cuenta}" type="hidden" /> 
				
				<input id="miFacebook" name="miFacebook" style="width: 200px" maxlength="50"
				value="${datos.tramiteRegistro.fisica.facebook.cuenta}" type="text"/>
			
			</td>
		</tr>
		<tr>
			<th align="right"><spring:message code="label.twitter" />&nbsp;:&nbsp;</th>
			<td>
				<form:hidden path="tramiteRegistro.fisica.twitter.clave"/> 
				
				<form:input path="tramiteRegistro.fisica.twitter.cuenta"
				style="width: 150px" value="${datos.tramiteRegistro.fisica.twitter.cuenta}"	type="hidden" /> 
				
				<input id="miTwitter" name="miTwitter" style="width: 200px" maxlength="50"
				value="${datos.tramiteRegistro.fisica.twitter.cuenta}" 	type="text"/>
			</td>
			
			<td colspan="2"/>
		</tr>
	</table>
	</fieldset>
	<div class="ui-widget" id="mensajeRegi" style="display:none;">
		<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
		<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span><span id="mensajeRegistro"></span>
		</p></div>
	</div>
	<div class="ui-widget" id="mensajeErrorDomicilio" style="display:none;">
		<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
		<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span><span id="mensajeErr"></span>
		</p></div>
	</div>
	<div id="domicilioGeo">
		<fieldset>			
			<legend><strong><spring:message code="titulo.datosDomicilio"/></strong></legend>			
			<table class="page_holder_no_height">
				<tr>									
					<th align="right" style="width: 150px"><spring:message code="label.asentamiento" />&nbsp;:&nbsp;</th>
					<td>
						<input type="hidden" id="cvePersonaDomicilio" name="cvePersonaDomicilio" value="${registro.cvePersonaDomicilio}">
						<form:input path="domicilio.asentamiento.nombre" style="width: 240px"			
						value="${registro.domicilio.asentamiento.nombre}"/>
						<form:input path="domicilio.asentamiento.clave" style="display:none" 					
						value="${registro.domicilio.asentamiento.clave}" />
					</td>
					
					<th align="right" style="width: 160px"><spring:message code="label.localidad" />&nbsp;:&nbsp;</th>
					<td>
						<form:input path="domicilio.asentamiento.localidad.nombre" style="width: 240px"
						value="${registro.domicilio.asentamiento.localidad.nombre}"/>
						<form:input path="domicilio.asentamiento.localidad.clave" style="display:none" 
						value="${registro.domicilio.asentamiento.localidad.clave}" />
					</td>
				</tr>
				
				<tr>					
					<th align="right" style="width: 150px"><spring:message code="label.delegacion" />&nbsp;:&nbsp;</th>
					<td>
						<form:input path="domicilio.asentamiento.localidad.municipio.nombre" style="width: 240px"
						value="${registro.domicilio.asentamiento.localidad.municipio.nombre}"/>
						<form:input path="domicilio.asentamiento.localidad.municipio.clave" style="display:none" 
						value="${registro.domicilio.asentamiento.localidad.municipio.clave}" />
					</td>
					<th align="right" style="width: 160px"><spring:message code="label.entidadF" />&nbsp;:&nbsp;</th>
					<td>
						<form:input path="domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre" style="width: 240px"
						value="${datos.domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre}"/>
						<form:input path="domicilio.asentamiento.localidad.municipio.entidadFederativa.clave" style="display:none"
						value="${registro.domicilio.asentamiento.localidad.municipio.entidadFederativa.clave}" />
					</td>
				</tr>
				
				<tr>	
					<th align="right" style="width: 160px"><spring:message code="label.numeroLExt" />&nbsp;:&nbsp;</th>
					<td>
						<input id="numeroExterior" type="text" style="width: 240px" 
						value="<c:out value="${datos.domicilio.numExterior1}  ${datos.domicilio.numExteriorAlf}"/>">
						<form:input path="domicilio.numExterior1" style="display:none"	 						
						value="${registro.domicilio.numExterior1}" />
						<form:input path="domicilio.numExteriorAlf" style="display:none"	 						
						value="${registro.domicilio.numExteriorAlf}" />						
					</td>										
					<th align="right" style="width: 150px"><spring:message code="label.numeroLInt" />&nbsp;:&nbsp;</th>
					<td>
						<input id="numeroInterior" type="text" style="width: 240px"
						value="<c:out value="${datos.domicilio.numInterior}   ${datos.domicilio.numInteriorAlf}"/>">
						<form:input path="domicilio.numInterior" style="display:none"							
							value="${registro.domicilio.numInterior}" />						
						<form:input path="domicilio.numInteriorAlf" style="display:none"							
							value="${registro.domicilio.numInteriorAlf}" />	
					</td>				
																				
				</tr>
				<tr>
					<th align="right" style="width: 160px"><spring:message code="label.numeroSecundario" />&nbsp;:&nbsp;</th>
					<td>	
						<input id="secundario" type="text" style="width: 240px"
						value="<c:out value="${datos.domicilio.numExterior2}"/>">				
						<form:input path="domicilio.numExterior2" style="display:none"						
							value="${registro.domicilio.numExterior2}" />												
					</td>	
					<th align="right"><spring:message code="label.codigoPos" />&nbsp;:&nbsp;</th>					
					<td><input id="codigoPostal" type="text" style="width: 240px" 
					value="<c:out value="${datos.domicilio.codigoPostal.codigoPostal}"/>" >
					<form:input path="domicilio.codigoPostal.codigoPostal" style="display:none"
						 value="${registro.domicilio.codigoPostal.codigoPostal}"/>
					</td>	
				</tr>
				<tr>
					<th align="right" style="width: 150px"><spring:message code="label.nombreV" />&nbsp;:&nbsp;</th>
					<td colspan="3">
						<input id="vialidadPrimaria" type="text" style="width:670px" 
						value="<c:out value="${datos.domicilio.vialidadPrimaria.tipoVialidad.descripcion} ${datos.domicilio.vialidadPrimaria.nombre}"/>">
						<form:input path="domicilio.vialidadPrimaria.clave" style="display:none" 						
						value="${registro.domicilio.vialidadPrimaria.clave}"/>
						<form:input path="domicilio.vialidadPrimaria.nombre" style="display:none"						
						value="${registro.domicilio.vialidadPrimaria.nombre}" />
						<form:input path="domicilio.vialidadPrimaria.tipoVialidad.clave" style="display:none" 						
						value="${registro.domicilio.vialidadPrimaria.tipoVialidad.clave}"/>
						<form:input path="domicilio.vialidadPrimaria.tipoVialidad.descripcion" style="display:none" 						
						value="${registro.domicilio.vialidadPrimaria.tipoVialidad.descripcion}"/>
					</td>
									
				</tr>
				<tr>
					<td colspan="6"><br></td>
				</tr>
				
				<tr>
					<th align="right"><spring:message code="label.ref1" />&nbsp;:&nbsp;</th>					
					<td colspan="5">
						<input id="referenciaPrimaria" type="text"  style="width: 670px" readonly="readonly"
						value="<c:out value="${datos.domicilio.vialidadReferenciaPrimaria.tipoVialidad.descripcion}  ${datos.domicilio.vialidadReferenciaPrimaria.nombre}"/>">			
						<form:input path="domicilio.vialidadReferenciaPrimaria.clave" style="display:none"
						value="${registro.domicilio.vialidadReferenciaPrimaria.clave}" />
						<form:input path="domicilio.vialidadReferenciaPrimaria.nombre" style="display:none"
						value="${registro.domicilio.vialidadReferenciaPrimaria.nombre}" />
						<form:input path="domicilio.vialidadReferenciaPrimaria.tipoVialidad.clave" style="display:none" 
						value="${registro.domicilio.vialidadReferenciaPrimaria.tipoVialidad.clave}" />
						<form:input path="domicilio.vialidadReferenciaPrimaria.tipoVialidad.descripcion" style="display: none"
						value="${registro.domicilio.vialidadReferenciaPrimaria.tipoVialidad.descripcion}" />												
					</td>
				</tr>
				<tr>
					<th align="right"><spring:message code="label.ref2" />&nbsp;:&nbsp;</th>					
					<td colspan="5">
						<input id="refSecundaria" type="text" style="width: 670px" readonly="readonly"
						value="<c:out value="${datos.domicilio.vialidadReferenciaSecundaria.tipoVialidad.descripcion}  ${datos.domicilio.vialidadReferenciaSecundaria.nombre}"/>">
						<form:input path="domicilio.vialidadReferenciaSecundaria.clave" style="display:none"						
						value="${registro.domicilio.vialidadReferenciaSecundaria.clave}" />
						<form:input path="domicilio.vialidadReferenciaSecundaria.nombre" style="display:none"						
						value="${registro.domicilio.vialidadReferenciaSecundaria.nombre}" />
						<form:input path="domicilio.vialidadReferenciaSecundaria.tipoVialidad.clave" style="display:none"						
						value="${registro.domicilio.vialidadReferenciaSecundaria.tipoVialidad.clave}" />
						<form:input path="domicilio.vialidadReferenciaSecundaria.tipoVialidad.descripcion"	style="display:none"				
						value="${registro.domicilio.vialidadReferenciaSecundaria.tipoVialidad.descripcion}" />												
				    </td>
				    
				    
					
				</tr>
				<tr>    
				    <th align="right"><spring:message code="label.ref3" />&nbsp;:&nbsp;</th>
				    <td colspan="5">
				    	<input id="refPosterior" type="text" style="width: 670px" readonly="readonly"
				    	value="<c:out value="${datos.domicilio.vialidadReferenciaPosterior.tipoVialidad.descripcion}  ${datos.domicilio.vialidadReferenciaPosterior.nombre}"/>">
				    	<form:input path="domicilio.vialidadReferenciaPosterior.clave"	style="display:none"				
						value="${registro.domicilio.vialidadReferenciaPosterior.clave}" />
				    	<form:input path="domicilio.vialidadReferenciaPosterior.nombre"	style="display:none"				
						value="${registro.domicilio.vialidadReferenciaPosterior.nombre}" />
				    	<form:input path="domicilio.vialidadReferenciaPosterior.tipoVialidad.clave"	style="display:none"					
						value="${registro.domicilio.vialidadReferenciaPosterior.tipoVialidad.clave}" />
				    	<form:input path="domicilio.vialidadReferenciaPosterior.tipoVialidad.descripcion" style="display:none"					
						value="${registro.domicilio.vialidadReferenciaPosterior.tipoVialidad.descripcion}" />		
						
						
						<form:hidden path="domicilio.calle"/>
					<form:hidden path="domicilio.tipoBusquedaVialidad"/>
							
					<!-- Atributos de domicilio carretera -->
					<form:hidden path="domicilio.domicilioCarretera.terminoGeneral.descripcion"
					value ="${registro.domicilio.domicilioCarretera.terminoGeneral.descripcion}"/>
					<form:hidden path="domicilio.domicilioCarretera.terminoGeneral.clave"
					value ="${registro.domicilio.domicilioCarretera.terminoGeneral.clave}"/>
					<form:hidden path="domicilio.domicilioCarretera.derechoTransito.descripcion"
					value ="${registro.domicilio.domicilioCarretera.derechoTransito.descripcion}"/>
					<form:hidden path="domicilio.domicilioCarretera.derechoTransito.clave"
					value ="${registro.domicilio.domicilioCarretera.derechoTransito.clave}"/>
					<form:hidden path="domicilio.domicilioCarretera.origen"
					value ="${registro.domicilio.domicilioCarretera.origen}"/>
					<form:hidden path="domicilio.domicilioCarretera.destino"
					value ="${registro.domicilio.domicilioCarretera.destino}"/>
					<form:hidden path="domicilio.domicilioCarretera.administracion.descripcion"
					value ="${registro.domicilio.domicilioCarretera.administracion.descripcion}"/>
					<form:hidden path="domicilio.domicilioCarretera.administracion.clave"
					value ="${registro.domicilio.domicilioCarretera.administracion.clave}"/>
					<form:hidden path="domicilio.domicilioCarretera.cadenamiento"
					value ="${registro.domicilio.domicilioCarretera.cadenamiento}"/>
					<form:hidden path="domicilio.domicilioCarretera.codigoCarretera"
					value ="${registro.domicilio.domicilioCarretera.codigoCarretera}"/>
							
					<!-- Atrbutos de domicilio camino -->
					<form:hidden path="domicilio.domicilioCamino.terminoGeneral.descripcion"
					value ="${registro.domicilio.domicilioCamino.terminoGeneral.descripcion}"/>
					<form:hidden path="domicilio.domicilioCamino.terminoGeneral.clave"
					value ="${registro.domicilio.domicilioCamino.terminoGeneral.clave}"/>
					<form:hidden path="domicilio.domicilioCamino.margen.descripcion"
					value ="${registro.domicilio.domicilioCamino.margen.descripcion}"/>
					<form:hidden path="domicilio.domicilioCamino.margen.clave"
					value ="${registro.domicilio.domicilioCamino.margen.clave}"/>
					<form:hidden path="domicilio.domicilioCamino.origen"
					value ="${registro.domicilio.domicilioCamino.origen}"/>
					<form:hidden path="domicilio.domicilioCamino.destino"
					value ="${registro.domicilio.domicilioCamino.destino}"/>
					<form:hidden path="domicilio.domicilioCamino.cadenamiento"
					value ="${registro.domicilio.domicilioCamino.cadenamiento}"/>										
					</td>
				</tr>				
				<tr>
					<td>
						<input type="button" onclick="iniciaConsultaDomicilio();" id="buscaDomicilio" name="buscaDomicilio" class="mboton" value="<spring:message code="button.domicilio"/>"/>
					</td>
				</tr>
				<tr>
					<td><br></td><td></td><td></td><td></td><td></td><td></td>
				</tr>
				
				
				<tr id="seleccionarDom" style="display:none;">
				<th align="right" style="width: 160px"><spring:message code="label.modDomicilio" />&nbsp;:&nbsp;</th>									
					<td>
							<input type="checkbox" id="modificarDom" name="modificarDom" > 
					</td>					
				</tr>
				
				
				
				
				
								
			</table>
			<div id="msgDomicilio" title ="<spring:message code="titulo.mensajeConfirmacion"/>" style="display:none"> 
					<spring:message code="msgDomicilio"/>	
			</div>
			<div id="msgDomicilioOblig" title ="<spring:message code="titulo.mensajeConfirmacion"/>" style="display:none"> 
					<spring:message code="msgDomicilioOblig"/>	
			</div>
			
			
		</fieldset>
	</div>														
	<fieldset style="align:ceter" style="width: 890px">
		<table style="width: 860px"  id="agregarMod">
			<tr>				
				<td align="center">											
					<input type="button" onclick="validar();" id="enviar" name="enviar" class="mboton" value="<spring:message code="button.aceptar"/>"/>
					<input type="button" onclick="cancelar();" id="regresar" name="regresar" class="mboton" value="<spring:message code="button.regresar"/>"/>									
					<!-- Parametros  Tramite, Rol -->
					<input style="display:none" type="button" id="guia" name="guia" onclick="preparaGuia();" value="<spring:message code="button.guiaTramite"/>" class="mboton"/>					
				</td>			
			</tr>		
		</table>
		<div id="msg15" title ="<spring:message code="titulo.mensajeConfirmacion"/>" style="display:none"> 
			<spring:message code="msg15"/>	
		</div>
		<div id="msg09" title ="<spring:message code="titulo.mensajeConfirmacion"/>" style="display:none"> 
			<spring:message code="msg09"/>		
		</div>
		<div id="msg13" title ="<spring:message code="titulo.mensajeConfirmacion"/>" style="display:none"> 
			<spring:message code="msg13"/>		
		</div>	
		<div id="msg14" title ="<spring:message code="titulo.mensajeConfirmacion"/>" style="display:none"> 
			<spring:message code="msg14"/>		
		</div>	
	</fieldset>								
</form:form>				
</div>		
<div id="buscarPersonaPorCurp"></div>
<div id="mensajeError"></div>
<script>
$(document).ready(function(){	
	 $.ajaxSetup({async:false});
	 
	 var parentesco = $("#tramiteRegistro\\.parentesco\\.idParentesco").val();
	 var proc = $('#proceso').val();	
	 
	 if($("#miParentesco").val() != "-1" && $("#miParentesco").val() != ""){
		 $("#tramiteRegistro\\.parentesco\\.idParentesco").val($("#miParentesco").val());
	 }
	 	 	 	 
	 $("#tramiteRegistro\\.parentesco\\.idParentesco").change();
	 
	 if($("#tramiteRegistro\\.fisica\\.lugarNacimiento\\.clave").val() != "-1" && $("#tramiteRegistro\\.fisica\\.lugarNacimiento\\.clave").val() != ""){
		 $("#lugarNacimiento").val($("#tramiteRegistro\\.fisica\\.lugarNacimiento\\.clave").val());
	 }
	 
	 if($("#tramiteRegistro\\.fisica\\.sexo\\.idSexo").val() != "-1" && $("#tramiteRegistro\\.fisica\\.sexo\\.idSexo").val() != ""){
		 $("#sexoR").val($("#tramiteRegistro\\.fisica\\.sexo\\.idSexo").val());
	 }	 
	
	 if($("#miEstadoCivil").val() != "-1" && $("#miEstadoCivil").val() != ""){
		 $("#tramiteRegistro\\.fisica\\.estadoCivil\\.idEstadoCivil").val($("#miEstadoCivil").val());	 
	 } 
	 
	$('#miNombre').change(function(){$('#tramiteRegistro\\.fisica\\.nombre').val($('#miNombre').val());}); 
	$('#miPrimerApellido').change(function(){$('#tramiteRegistro\\.fisica\\.primerApellido').val($('#miPrimerApellido').val());}); 
	$('#miSegundoApellido').change(function(){$('#tramiteRegistro\\.fisica\\.segundoApellido').val($('#miSegundoApellido').val());}); 
	$('#miCurp').change(function(){$('#tramiteRegistro\\.fisica\\.curp').val($('#miCurp').val());}); 
	$('#miFechaNacimientoFormateada').change(function(){$('#tramiteRegistro\\.fisica\\.fechaNacimientoFormateada').val($('#miFechaNacimientoFormateada').val());});
	
	$('#miCorreoElectronico').change(function(){$('#tramiteRegistro\\.fisica\\.correoElectronico\\.correo').val($('#miCorreoElectronico').val());});
	$('#miTelefonoFijo').change(function(){$('#tramiteRegistro\\.fisica\\.telefonoFijo\\.claveLada').val($('#miTelefonoFijo').val());});
	$('#miTelefonoMovil').change(function(){$('#tramiteRegistro\\.fisica\\.telefonoMovil\\.numero').val($('#miTelefonoMovil').val());});
	$('#miFacebook').change(function(){$('#tramiteRegistro\\.fisica\\.facebook\\.cuenta').val($('#miFacebook').val());});
	$('#miTwitter').change(function(){$('#tramiteRegistro\\.fisica\\.twitter\\.cuenta').val($('#miTwitter').val());});

	$('#tipoRegistro\\.idRazonRegistro').change(function() {
		if($('#tipoRegistro\\.idRazonRegistro').val() == "2"){
			$('#recienNacido').attr("checked","checked");
		}else {
			$('#recienNacido').removeAttr("checked");			
		}
	});
	
	
	$("#tramiteRegistro\\.fisica\\.estadoCivil\\.idEstadoCivil").change(function(){
		$("#miEstadoCivil").val($("#tramiteRegistro\\.fisica\\.estadoCivil\\.idEstadoCivil").val());
	});
	
	$("#sexoR").change(function() {
		$("#tramiteRegistro\\.fisica\\.sexo\\.idSexo").val($("#sexoR").val());
		pareja();
		validaEdad($('#miFechaNacimientoFormateada').val());
	});
	
	$("#lugarNacimiento").change(function() {
		$("#tramiteRegistro\\.fisica\\.lugarNacimiento\\.clave").val($("#lugarNacimiento").val());
	});
	
	$("#recienNacido").change(function() {
		if($('#recienNacido').is(":checked")){
			limpiarDatosBasicos();
			
			$('#tipoRegistro\\.idRazonRegistro').val("2");
			validaEdad($('#tramiteRegistro\\.fisica\\.fechaNacimientoFormateada').val());
			
			habilitarDesabilitarCamposDatosBasicos(true);
			
			rn();
			
			$("#buscaDomicilio").show();
			$('#buscarXCurp').hide();
			$("#miCurp").attr("disabled","disabled");
		}else{
			limpiarDatosBasicos();
			
			if($('#miNombre').val() == 'RECIEN NACIDO' ){
				$('#miNombre').val('');
			}
			 habilitarDesabilitarCamposDatosBasicos(false);
			 $("#miCurp").removeAttr("disabled");
			$('#buscarXCurp').show();
			validaEdad($('#tramiteRegistro\\.fisica\\.fechaNacimientoFormateada').val());
		}
	});	
});
</script>