<%@ include file="/WEB-INF/views/general/taglibs.jsp" %>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>	
<%@ include file="/WEB-INF/views/general/GuiaTramite/guiaTramiteImport.jsp" %>
	
<script type="text/javascript"
src="<spring:url value="/static/resources/js/delta/confirmacionRegistroD.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/gestionCtrlSelect.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
	<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
		 
	<script>
		var contextPath = "<%=request.getContextPath()%>";
	</script> 


<div class="form-comment">
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<form:form commandName="datos" id="frmConfirmacion" name="frmConfirmacion" action="${contextpath}/derechohabientes/registro/cita" method="post">
	<br><br><br>
	<jsp:include page="/WEB-INF/views/general/encabezadoGF.jsp"/>
	<input name="usuario" id="usuario" type="hidden" value="${usuario}"/>
	<input type="hidden" value="${perfil}" id="perfil" name="perfil"/>
	<input type="hidden" value="${requisitos.aprobado}" id="requisito" name="requisito"/>
	<input type="hidden" id="razonR" name="razonR" value="<c:out value="${datos.tramiteRegistro.razonRegistro.idRazonRegistro}"/>">	
	<input type="hidden" id="idParentesco" name="idParentesco" value="<c:out value="${datos.tramiteRegistro.parentesco.idParentesco}"/>">
	
	<form:input path="resultado.doble" type="hidden" value="${resultado.doble}"/>
	<form:input path="resultado.idRazonResultado" type="hidden" value=""/>
	<form:input path="proceso" value="3" type="hidden"/>
				
	<div id="minimosDiv" style="display:none">
		<fieldset style="align:ceter"  style="width: 890px">
			<legend><strong>Requisitos M&iacute;nimos</strong></legend>
			<table class="page_holder_no_height" style="width: 860px">
				<tr align="center">
					<td>Resultado:</td>
					<td><div id="resultado"></div></td>
					<td>Motivo:</td>
					<td><div id="motivo"><b>${requisitos.motivo}</b></div></td>
				</tr>				
			</table>
		</fieldset>
	</div>								
	<fieldset style="align:ceter" style="width: 890px">
		<legend><b><spring:message code="titulo.confirmacionRegistro"/></b></legend>
		<table class="page_holder_no_height" style="width: 860px">
			<tr>
				<td>
					<div class="ui-widget" id="leyendaConf">
						<div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
							<p>
								<strong><spring:message code="leyenda.confirmacionRegistro"/></strong>
							</p>
						</div>
					</div>
				</td>
			</tr>
		</table>				
	</fieldset>	
	<div id="datosGenerales">
		<fieldset style="align:ceter" style="width: 890px">
		<legend><b><spring:message code="titulo.datosGenerales"/></b></legend>				
			<table class="page_holder_no_height" style="width: 860px">				
				<tr>
					<th align="right">
						<div id="parentAseg">
							<spring:message code="label.calidad"/>:
						</div>																																								
						<div id="parentInteg">
							<spring:message code="label.parentesco"/>:
						</div>	
					</th>
					<td>
						
						<input type="text" maxlength="10" id="datos.tramiteRegistro.parentesco.descripcion" name="datos.tramiteRegistro.parentesco.descripcion" readonly="readonly" value="<c:out value="${datos.tramiteRegistro.parentesco.descripcion}"/>">													
					</td>
					<td colspan="2"></td>												
				</tr>					
				<tr id="razonReg">
					<th align="right">
						<spring:message code="label.registro"/>:
					</th>
					<td>
						<input type="text"  maxlength="10"  readonly="readonly" value="<c:out value="${datos.tramiteRegistro.razonRegistro.descripcion}"/>">											
					</td>	
				</tr>
				<tr id="tipoReg">		
					<th align="right">
						<spring:message code="label.registro.tipo"/>:
					</th>
					<td>
						<input type="text"  maxlength="10"  readonly="readonly" value="<c:out value="${datos.tipoRegistro.descripcion}"/>">											
					</td>
				</tr>					
			</table>		
		</fieldset>
	</div>
	<div id="datosPersona">
		<fieldset style="align:ceter" style="width: 890px">
			<legend><b><spring:message code="titulo.datosPersona"/></b></legend>			
			<table class="page_holder_no_height" style="width: 860px;">						
				<tr>					
					<th align="right">
						<spring:message code="label.nombre"/>:
					</th>
					
					<td>
						<input type="text" maxlength="10" readonly="readonly" value="<c:out value="${datos.tramiteRegistro.fisica.nombre}"/>">							
					</td>
					<th align="right">
						<spring:message code="label.curp"/>:
					</th>
					<td>
						<input type="text" maxlength="10" readonly="readonly" value="<c:out value="${datos.tramiteRegistro.fisica.curp}"/>">							
					</td>															
				</tr>
				<tr>
					<th align="right">
						<spring:message code="label.primerApe"/>:
					</th>					
					<td>
						<input type="text" maxlength="10"  readonly="readonly" value="<c:out value="${datos.tramiteRegistro.fisica.primerApellido}"/>">							
					</td>
					<th align="right">
						<spring:message code="label.lugarNac"/>:
					</th>
					<td>
						<input type="text" maxlength="10"  readonly="readonly" value="<c:out value="${datos.tramiteRegistro.fisica.lugarNacimiento.nombre}"/>">													
					</td>																																		
				</tr>
				<tr>
					<th align="right">
						<spring:message code="label.segundoApe"/>:
					</th>
					<td>
						<input type="text" maxlength="10" readonly="readonly" value="<c:out value="${datos.tramiteRegistro.fisica.segundoApellido}"/>">							
					</td>
					<th align="right">
						<spring:message code="label.sexo"/>:
					</th>
					<td>
						<input type="text" maxlength="10" readonly="readonly" value="<c:out value="${datos.tramiteRegistro.fisica.sexo.descripcion}"/>">																			
					</td>	
																			
				</tr>
				<tr>
					<th align="right">
						<spring:message code="label.fechaNac"/>:
					</th>
					<td>
						<input type="text" id="fechaNacimiento" name="fechaNacimiento" maxlength="10" readonly="readonly" value="${datos.tramiteRegistro.fisica.fechaNacimientoFormateada}">																		
					</td>
					<th align="right">
						<spring:message code="label.edoCivil"/>:
					</th>
					<td>
						<input type="text" maxlength="10" readonly="readonly" value="<c:out value="${datos.tramiteRegistro.fisica.estadoCivil.descripcion}"/>">																		
					</td>					
				</tr>
			</table>			
		</fieldset>
	</div>
	<div id="mediosContacto">
		<fieldset style="align:ceter" style="width: 890px">
			<legend><b><spring:message code="titulo.mediosContacto"/></b></legend>		
			<table class="page_holder_no_height" style="width: 860px;">
				
				<tr>
					<th align="right">
						<spring:message code="label.correoElectronico"/>:
					</th>
					<td>
						<input type="text" maxlength="10" readonly="readonly" value="<c:out value="${datos.tramiteRegistro.fisica.correoElectronico.correo}"/>">																		
					</td>
				</tr>
				<tr>
					<th align="right">
						<spring:message code="label.telefonoFijo"/>:
					</th>
					<td>
						<input type="text" maxlength="10" readonly="readonly" value="<c:out value="${datos.tramiteRegistro.fisica.telefonoFijo.claveLada}"/>">																		
					</td>
				</tr>
				<tr>
					<th align="right">
						<spring:message code="label.telefonoMovil"/>:
					</th>
					<td>
						<input type="text" maxlength="10" readonly="readonly" value="<c:out value="${datos.tramiteRegistro.fisica.telefonoMovil.numero}"/>">																	
					</td>
				</tr>
				<tr>
					<th align="right">
						<spring:message code="label.facebook"/>:
					</th>
					<td>
						<input type="text" maxlength="10" readonly="readonly" value="<c:out value="${datos.tramiteRegistro.fisica.facebook.cuenta}"/>">																
					</td>
				</tr>
				<tr>
					<th align="right">
						<spring:message code="label.twitter"/>:
					</th>
					<td>
						<input type="text" maxlength="10" readonly="readonly" value="<c:out value="${datos.tramiteRegistro.fisica.twitter.cuenta}"/>">																
					</td>
				</tr>
			</table>							
		</fieldset>					
	</div>												
	<div id="domicilio">
		<fieldset style="align:ceter" style="width: 890px">			
			<legend><b><spring:message code="titulo.datosDomicilio"/></b></legend>			
			<table class="page_holder_no_height" style="width: 860px;">
				<tr>									
					<th align="right" style="width: 150px"><spring:message code="label.asentamiento" />: </th>
					<td>
						<input id="domicilio.asentamiento.nombre" style="width: 240px" readonly="readonly"				
						value="<c:out value="${datos.domicilio.asentamiento.nombre}"/>">
						<form:input path="domicilio.asentamiento.clave" style="display:none" 					
						value="${registro.domicilio.asentamiento.clave}" />
					</td>
					
					<th align="right" style="width: 160px"><spring:message code="label.localidad" />: </th>
					<td>
						<input id="domicilio.asentamiento.localidad.nombre" style="width: 240px" readonly="readonly"
						value="<c:out value="${datos.domicilio.asentamiento.localidad.nombre}"/>">
						<form:input path="domicilio.asentamiento.localidad.clave" style="display:none" 
						value="${registro.domicilio.asentamiento.localidad.clave}" />
					</td>
				</tr>
				
				<tr>					
					<th align="right" style="width: 150px"><spring:message code="label.delegacion" />:</th>
					<td>
						<input id="domicilio.asentamiento.localidad.municipio.nombre" style="width: 240px" readonly="readonly"
						value="<c:out value="${datos.domicilio.asentamiento.localidad.municipio.nombre}"/>">
						<form:input path="domicilio.asentamiento.localidad.municipio.clave" style="display:none" 
						value="${registro.domicilio.asentamiento.localidad.municipio.clave}" />
					</td>
					<th align="right" style="width: 160px"><spring:message code="label.entidadF" />:</th>
					<td>
						<input id="domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre" style="width: 240px" readonly="readonly"
						value="<c:out value="${datos.domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre}"/>">
						<form:input path="domicilio.asentamiento.localidad.municipio.entidadFederativa.clave" style="display:none"
						value="${registro.domicilio.asentamiento.localidad.municipio.entidadFederativa.clave}" />
					</td>
				</tr>
				<tr>
					<th align="right" style="width: 150px"><spring:message code="label.nombreV" />:</th>
					<td>
						<input id="vialidadPrimaria" type="text" style="width:240px" readonly="readonly" 
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
					<th align="right" style="width: 160px"><spring:message code="label.numeroLExt" />:</th>
					<td>
						<input id="numeroExterior" type="text" style="width: 130px" readonly="readonly"
						value="<c:out value="${datos.domicilio.numExterior1}  ${datos.domicilio.numExteriorAlf}"/>">
						<form:input path="domicilio.numExterior1" style="display:none"	 						
						value="${registro.domicilio.numExterior1}" />
						<form:input path="domicilio.numExteriorAlf" style="display:none"	 						
						value="${registro.domicilio.numExteriorAlf}" />						
					</td>					
				</tr>
				<tr>										
					<th align="right" style="width: 150px"><spring:message code="label.numeroLInt" />:</th>
					<td>
						<input id="numeroInterior" type="text" style="width: 130px" readonly="readonly"
						value="<c:out value="${datos.domicilio.numInterior}   ${datos.domicilio.numInteriorAlf}"/>">
						<form:input path="domicilio.numInterior" style="display:none"							
							value="${registro.domicilio.numInterior}" />						
						<form:input path="domicilio.numInteriorAlf" style="display:none"							
							value="${registro.domicilio.numInteriorAlf}" />	
					</td>	
					<th align="right" style="width: 160px"><spring:message code="label.numeroSecundario" />:</th>
					<td>	
						<input id="secundario" type="text" style="width: 130px" readonly="readonly"
						value="<c:out value="${datos.domicilio.numExterior2}"/>">						
						<form:input path="domicilio.numExterior2" style="display:none"						
							value="${registro.domicilio.numExterior2}" />												
					</td>									
				</tr>
				<tr>
					<th align="right"><spring:message code="label.codigoPos" />:</th>
					<td><input id="domicilio.codigoPostal.codigoPostal" style="width: 130px" readonly="readonly"
						 value="<c:out value="${datos.domicilio.codigoPostal.codigoPostal}"/>">
					</td>
				</tr>
				<tr>
					<td colspan="6"><br></td>
				</tr>
				
				<tr>
					<th align="right"><spring:message code="label.ref1" /></th>					
					<td colspan="5">
						<input id="referenciaPrimaria" type="text"  style="width: 470px" readonly="readonly"
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
					<th align="right"><spring:message code="label.ref2" /></th>					
					<td colspan="5">
						<input id="refSecundaria" type="text" style="width: 470px" readonly="readonly"
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
				    <th align="right"><spring:message code="label.ref3" /></th>
				    <td colspan="5">
				    	<input id="refPosterior" type="text" style="width: 470px" readonly="readonly"
				    	value="<c:out value="${datos.domicilio.vialidadReferenciaPosterior.tipoVialidad.descripcion}  ${datos.domicilio.vialidadReferenciaPosterior.nombre}"/>">
				    	<form:input path="domicilio.vialidadReferenciaPosterior.clave"	style="display:none"				
						value="${registro.domicilio.vialidadReferenciaPosterior.clave}" />
				    	<form:input path="domicilio.vialidadReferenciaPosterior.nombre"	style="display:none"				
						value="${registro.domicilio.vialidadReferenciaPosterior.nombre}" />
				    	<form:input path="domicilio.vialidadReferenciaPosterior.tipoVialidad.clave"	style="display:none"					
						value="${registro.domicilio.vialidadReferenciaPosterior.tipoVialidad.clave}" />
				    	<form:input path="domicilio.vialidadReferenciaPosterior.tipoVialidad.descripcion" style="display:none"					
						value="${registro.domicilio.vialidadReferenciaPosterior.tipoVialidad.descripcion}" />												
					</td>
				</tr>
				
				
				<tr>
					<td><br></td><td></td><td></td><td></td><td></td><td></td>
				</tr>
				
			</table>
		</fieldset>
	</div>				
	<fieldset style="align:ceter" style="width: 890px">
		<table style="width: 860px" class="titulo" id="agregarMod">
			<tr>				
				<td align="center">						
					<button onclick="termina();" type="button" class="mboton" id="aceptar"><spring:message code="button.aceptar"/></button>
					<button onclick="regresa();" type="button" class="mboton"><spring:message code="button.regresar"/></button>								
				</td>			
			</tr>		
		</table>
	</fieldset>		
	
	<div id="msg03" title ="<spring:message code="titulo.mensajeConfirmacion"/>" style="display:none"> 
		<spring:message code="msg03"/>		
	</div>	
	<div id="razonRechazo" title ="<spring:message code="titulo.mensajeConfirmacion"/>" style="display:none">
		<spring:message code="msg16"/>
		<table>
			<tr>
				<td>
					<spring:message code="label.cancela"/>:
				</td>
				<td>					
					<select id="idRazonResultado">						
						<option value="1">DOCUMENTOS PROBATORIOS INCOMPLETOS</option>
						<option value="2">DOCUMENTOS AP&Oacute;CRIFOS</option>
						<option value="3">IMPROCEDENCIA</option>
						<option value="4">CONVIVENCIA-DEPENDENCIA NO COMPROBADAS</option>
						<option value="5" selected="selected">SOLICITUD CANCELADA</option>
					</select>						
				</td>
			</tr>		
		</table>
	</div>				
</form:form>				
</div>				