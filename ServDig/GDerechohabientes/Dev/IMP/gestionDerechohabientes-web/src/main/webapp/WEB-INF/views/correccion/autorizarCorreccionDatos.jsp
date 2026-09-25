<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>
<%@taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/correccion/autorizarTramite.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/gestionCtrlSelect.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/fileRead/fileRead.js" htmlEscape="true" />"></script>
<script type="text/javascript">

$(document).ready(
	function() {
		$("#registro input:text").each(
			function(index) {
				$(this).attr("disabled","disabled");
			}
		);
		
		$("#observaciones").attr("disabled","disabled");
	}		
);
</script>
<input type="hidden" id="validacion" value="${validacion}"/>
<div class="form-comment">
	<br><br>
	<h4 align="center">AUTORIZACI&Oacute;N DE CORRECCI&Oacute;N DE DATOS DE DERECHOHABIENTE</h4>
	<jsp:include page="/WEB-INF/views/prorrogas/grupoFamiliar.jsp"></jsp:include>
	<br>
	<div id="mensajeConfirmacion"></div>
	
		<!-- Apartado de datos del integrante -->
		<br>
	<input type="hidden" id="solicitudId" value="${solicitud.solicitudId}">
	<table style="width: 100%">
		<tr>	
			<td style="width: 50%">
		     <form>
				<fieldset style="width: 100%;height: 350px">
					<legend><strong><spring:message code="label.datosActuales" /></strong></legend>
					<table class="page_holder_no_height"  style="width:95%">
						<tr>
							<td ><spring:message code="label.nombre" /> : </td>
							<td>
								<input type="hidden" id="idUmfDom" value="${derechohabiente.medicoEnTurno.unidadMedicaFamiliar.idUMF}"/>
								<input type="text" id="nombreActual" name="nombreActual" value="${datosActuales.nombre}" style="width:95%" disabled="disabled" /> 
							</td>
						</tr>
						<tr>
							<td ><spring:message code="label.primerApe" /> : </td>
							<td>
								<input type="text" id="pApellidoActual" name="pApellidoActual" value="${datosActuales.primerApellido}" disabled="disabled"  style="width:95%" />
							</td>
						</tr>
						<tr>	
							<td ><spring:message code="label.segundoApe" /> : </td>
							<td>
								<input type="text" id="sApellidoActual" name="sApellidoActual" value="${datosActuales.segundoApellido}" disabled="disabled"  style="width:95%" />
							</td>	
						</tr>
						<tr>	
							<td ><spring:message code="label.fechaNac" /> : </td>
							<td>
								<input type="text" 
								value="<fmt:formatDate pattern="dd/MM/yyyy" value="${datosActuales.fechaNacimiento}"/>" disabled="disabled"   style="width:95%">
							</td>
						</tr>
						<tr>	
							<td ><spring:message code="label.lugarNac" /> : </td>
							<td colspan="3">
								<input type="text" id="fechaNacActual" name="fechaNacActual" value="${datosActuales.lugarNacimiento.nombre}"  disabled="disabled" style="width:95%"/> 
							</td>
						</tr>
						<tr>		
							<td ><spring:message code="label.sexo" /> : </td>
							<td>
								<input type="text" id="sexoActual" name="sexoActual" value="${datosActuales.sexo.descripcion}" disabled="disabled" style="width:95%"/> 
							</td>	
						</tr>
						<tr>	
							<td ><spring:message code="label.edoCivil" /> : </td>
							<td>
								<input type="text" value="${datosActuales.estadoCivil.descripcion}" disabled="disabled" style="width:95%"/> 
							</td>		
						</tr>
						<tr>	
							<td ><spring:message code="label.curp" /> : </td>
							<td>
								<input type="text" id="curpActual" name="curpActual" value="${datosActuales.curpCap}" style="width:95%" disabled="disabled" />
							</td>
						</tr>
						<tr>
							<td ><spring:message code="label.parentesco" /> : </td>
							<td>
								<input type="text" id="parentesco" name="parentesco" value="${datosActuales.parentesco.descripcion}" style="width:95%" disabled="disabled" />
							</td>
						</tr>
						</table>
						</fieldset>
						<fieldset id="mediosAnteriores" style="width: 95%;height: 220px">		
						<legend><strong><spring:message code="titulo.mediosContacto"/></strong></legend>			
						<table class="page_holder_no_height"   style="width:95%">	
							<tr>
								<td><spring:message code="label.correoElectronico" /> : </td>
								<td>
									<input type="text" value="${datosActuales.correoElectronico.correo}" disabled="disabled"  style="width:95%"/>							
								</td>
							</tr>
							<tr>
								<td><spring:message code="label.telefonoFijo" /> : </td>
								<td>
									<input type="text" value="${datosActuales.telefonoFijo.claveLada}" disabled="disabled"  style="width:95%"/>
								</td>
							</tr>
							<tr>
								<td><spring:message code="label.telefonoMovil" /> : </td>
								<td>
									<input type="text" value="${datosActuales.telefonoMovil.numero}" disabled="disabled"  style="width:95%"/>
								</td>
							</tr>
							<tr>
								<td><spring:message code="label.facebook" /> : </td>
								<td>
									<input type="text" value="${datosActuales.facebook.cuenta}" disabled="disabled"  style="width:95%"/>					
								</td>
							</tr>
							<tr>
								<td><spring:message code="label.twitter" /> : </td>
								<td>
									<input type="text" value="${datosActuales.twitter.cuenta}" disabled="disabled"  style="width:95%"/>					
								</td>
							</tr>			
						</table>
						</fieldset>
						<fieldset  style="width:95%;height: 650px">
						<legend><strong>Datos del domicilio anterior</strong></legend>
						<table id="domicilioAnterior" class="page_holder_no_height"  style="width:95%">
						<tr>
							<td ><spring:message code="label.asentamiento" /> : </td>
							<td>
								<input type="text" value="${datosActuales.domicilio.asentamiento.nombre}" disabled="disabled" style="width:95%"/>
							</td>
						</tr>
						<tr>
							<td>Codigo Postal:</td>
							<td>
								<input type="text" value="${datosActuales.domicilio.codigoPostal.codigoPostal}" style="width:95%" disabled="disabled" />
							</td>
						</tr>
						<tr>	
							<td ><spring:message code="label.tipoVialidad" /> : </td>
							<td>
								<input type="text" value="${datosActuales.domicilio.vialidadPrimaria.tipoVialidad.descripcion}" disabled="disabled"  style="width:95%"/>
							</td>
						</tr>
						<tr>	
							<td ><spring:message code="label.nombreV" /> : </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.vialidadPrimaria.nombre}"  disabled="disabled"  style="width:95%"/>
							</td>
						</tr>
						<tr>	
							<td ><spring:message code="label.numeroExt" /> : </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.numExterior1}" style="width:95%" disabled="disabled" />
							</td>
						</tr>
						<tr>	
							<td ><spring:message code="label.numeroLExt" /> : </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.numExteriorAlf}" style="width:95%" disabled="disabled" />
							</td>
						</tr>
						<tr>	
							<td ><spring:message code="label.numeroInt" /> : </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.numInterior}" style="width:95%" disabled="disabled" />
							</td>
						</tr>
						<tr>	
							<td ><spring:message code="label.numeroLInt" /> : </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.numInteriorAlf}" style="width:95%" disabled="disabled" />
							</td>
						</tr>
						<tr>	
							<td ><spring:message code="tramite.detalle.numLetraExtNoOficial" /> : </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.numExterior2}" style="width:95%" disabled="disabled" />
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
								<input  type="text"  value="${datosActuales.domicilio.vialidadReferenciaPrimaria.tipoVialidad.descripcion}" style="width:95%" disabled="disabled" />
							</td>
						</tr>
						<tr>
							<td ><spring:message code="label.nombreV" /> : </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.vialidadReferenciaPrimaria.nombre}" style="width:95%" disabled="disabled" />
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
								<input  type="text"  value="${datosActuales.domicilio.vialidadReferenciaSecundaria.tipoVialidad.descripcion}" style="width:95%" disabled="disabled" />
							</td>
						</tr>
						<tr>
							<td ><spring:message code="label.nombreV" /> : </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.vialidadReferenciaSecundaria.nombre}" style="width:95%" disabled="disabled" />
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
								<input  type="text"  value="${datosActuales.domicilio.vialidadReferenciaPosterior.tipoVialidad.descripcion}" style="width:95%" disabled="disabled" />
							</td>
						</tr>
						<tr>
							<td ><spring:message code="label.nombreV" /> : </td>
							<td>
								<input  type="text"  value="${datosActuales.domicilio.vialidadReferenciaPosterior.nombre}" style="width:95%" disabled="disabled" />
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
			<form:hidden path="idPersona" />
			<form:hidden path="tipoTramite.idTipoTramite" />
			<form:hidden path="domicilio.numExterior1" />
							<fieldset  style="width:95%;height: 350px">
								<legend><strong><spring:message code="label.datosRequierenCorreccion" /></strong></legend>
							<table id="registroD" class="page_holder_no_height" style="width:100%">
								<tr>
									<td ><spring:message code="label.nombre" /> : </td>
									<td>
										<form:input path="nombre" class='alfanumerico_espacios' maxlength="15" 
										 style="width:95%" /> 
									</td>
								</tr>
								<tr>
									<td ><spring:message code="label.primerApe" /> : </td>
									<td>
										<form:input path="primerApellido" class='alfanumerico_espacios' style="width:95%" />
										<span><form:errors path="primerApellido" cssStyle="color:red"/></span>
									</td>
								</tr>
								<tr>	
									<td ><spring:message code="label.segundoApe" /> : </td>
									<td>
										<form:input path="segundoApellido" class='alfanumerico_espacios' style="width:95%"/>
										<span><form:errors path="segundoApellido" cssStyle="color:red"/></span>
									</td>	
								</tr>
								<tr>	
									<td ><spring:message code="label.fechaNac" /> : </td>
									<td>
										<input type="text" id="fechaNacimiento" name="fechaNacimiento"  
										value="<fmt:formatDate pattern="dd/MM/yyyy" value="${derechohabiente.fechaNacimiento}"/>" style="width:95%"/>
										<span><form:errors path="fechaNacimiento" cssStyle="color:red"/></span>
									</td>
								</tr>
								<!-- Hasta aqui llego -->
								<tr>	
									<td ><spring:message code="label.lugarNac" /> : </td>
									<td >
										<form:input path="lugarNacimiento.nombre"  style="width:95%"/>
									</td>
								</tr>
								<tr>		
									<td ><spring:message code="label.sexo" /> : </td>
									<td>
										<form:input path="sexo.descripcion"  style="width:95%"/>
									</td>	
								</tr>
								<tr>	
									<td ><spring:message code="label.edoCivil" /> : </td>
									<td>
										<form:input path="estadoCivil.descripcion"  style="width:95%"/>
									</td>		
								</tr>
								<tr>	
									<td ><spring:message code="label.curp" /> : </td>
									<td>
										<form:input path="curpCap" maxlength="18"  style="width:95%"/>
										<span><form:errors path="curpCap" cssStyle="color:red"/></span>
									</td>
								</tr>
								<tr>	
									<td ><spring:message code="label.parentesco" /> : </td>
									<td>
										<form:hidden path="parentesco.idParentesco"  style="width:95%"/>
										<form:input path="parentesco.descripcion" style="width: 160px" />
									</td>
								</tr>
							</table>
							</fieldset>
							<fieldset id="mediosActuales"  style="width:95%;height: 220px">		
						<legend><strong><spring:message code="titulo.mediosContacto"/></strong></legend>			
						<table class="page_holder_no_height" style="width:95%">	
							<tr>
								<td><spring:message code="label.correoElectronico" /> : </td>
								<td>
									<form:hidden path="correoElectronico.clave"/>
									<form:input path="correoElectronico.correo"  style="width:95%"/>								
								</td>
							</tr>
							<tr>
								<td><spring:message code="label.telefonoFijo" /> : </td>
								<td>
									<form:hidden path="telefonoFijo.clave"/>
									<form:input class='entero_10' path="telefonoFijo.claveLada" style="width:95%"/>					
								</td>
							</tr>
							<tr>
								<td><spring:message code="label.telefonoMovil" /> : </td>
								<td>
									<form:hidden path="telefonoMovil.clave"/>
									<form:input class='entero_10' path="telefonoMovil.numero"  style="width:95%"/>					
								</td>
							</tr>
							<tr>
								<td><spring:message code="label.facebook" /> : </td>
								<td>
									<form:hidden path="facebook.clave"/>
									<form:input path="facebook.cuenta" style="width:95%"/>					
								</td>
							</tr>
							<tr>
								<td><spring:message code="label.twitter" /> : </td>
								<td>
									<form:hidden path="twitter.clave"/>
									<form:input path="twitter.cuenta" style="width:95%"/>					
								</td>
							</tr>			
						</table>
					</fieldset>	
							<fieldset  style="width:95%;height: 650px">
							<legend><strong>Datos del nuevo domicilio</strong></legend>
							<table id="domicilioNuevo" class="page_holder_no_height"  style="width:95%">
								<tr>
									<td ><spring:message code="label.asentamiento" /> : </td>
									<td>
										<form:hidden path="domicilio.asentamiento.clave"/>
										<form:input disabled="disabled" path="domicilio.asentamiento.nombre" style="width:95%"/>
									</td>
								</tr>
								<tr>
									<td><spring:message code="label.codigoPos" />:</td>
									<td>
										<form:input path="domicilio.codigoPostal.codigoPostal" style="width:95%"/>
									</td>
								</tr>
								<tr>	
									<td ><spring:message code="label.tipoVialidad" /> : </td>
									<td>
										<form:hidden path="domicilio.vialidadPrimaria.tipoVialidad.clave"/>
										<form:input path="domicilio.vialidadPrimaria.tipoVialidad.descripcion" style="width:95%"/>
									</td>
								</tr>
								<tr>	
									<td ><spring:message code="label.nombreV" /> : </td>
									<td>
										<form:hidden path="domicilio.vialidadPrimaria.clave"/>
										<form:input path="domicilio.vialidadPrimaria.nombre" style="width:95%"/>
									</td>
								</tr>
								<tr>
									<td><spring:message code="label.numeroExt" /><br></td>
									<td>
									<form:input path="domicilio.numExterior1" style="width:95%"/>
									</td>
								</tr>
								<tr>	
									<td ><spring:message code="label.numeroLExt" /> : </td>
									<td>
										<form:input path="domicilio.numExteriorAlf" style="width:95%"/>
									</td>
								</tr>
								<tr>	
									<td ><spring:message code="label.numeroInt" /> : </td>
									<td>
										<form:input path="domicilio.numInterior" style="width:95%"/>
									</td>
								</tr>
								<tr>	
									<td ><spring:message code="label.numeroLInt" /> : </td>
									<td>
										<form:input path="domicilio.numInteriorAlf" style="width:95%"/>
									</td>
								</tr>
								<tr>	
									<td ><spring:message code="tramite.detalle.numLetraExtNoOficial" /> : </td>
									<td>
										<form:input path="domicilio.numExterior2" style="width:95%"/>
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
										<form:input path="domicilio.vialidadReferenciaPrimaria.tipoVialidad.descripcion" style="width:95%"/>
									</td>
								</tr>
								<tr>
									<td ><spring:message code="label.nombreV" /> : </td>
									<td>
										<form:hidden path="domicilio.vialidadReferenciaPrimaria.clave"/>
										<form:input path="domicilio.vialidadReferenciaPrimaria.nombre" style="width:95%"/>
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
										<form:input path="domicilio.vialidadReferenciaSecundaria.tipoVialidad.descripcion" style="width:95%"/>
									</td>
								</tr>
								<tr>
									<td ><spring:message code="label.nombreV" /> : </td>
									<td>
										<form:hidden path="domicilio.vialidadReferenciaSecundaria.clave"/>
										<form:input path="domicilio.vialidadReferenciaSecundaria.nombre" style="width:95%"/>
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
										<form:input path="domicilio.vialidadReferenciaPosterior.tipoVialidad.descripcion" style="width:95%"/>
									</td>
								</tr>
								<tr>
									<td ><spring:message code="label.nombreV" /> : </td>
									<td>
										<form:hidden path="domicilio.vialidadReferenciaPosterior.clave"/>
										<form:input path="domicilio.vialidadReferenciaPosterior.nombre" style="width:95%"/>
									</td>
								</tr>
							</table>
						</fieldset>
			</form:form>				
		</td>
	</tr>
	</table>
	<fieldset>
			<fieldset>
				<spring:message code="label.observaciones"/> :  <textarea id="observaciones" name="observaciones" style="height: 40px; width: 770px;">${derechohabiente.observacion}</textarea>
			</fieldset>
	</fieldset>
	<br>
		<div id="docProbTramDiv"></div>
		<script type="text/javascript">
			$(document).ready(
				function() {
					initMuestraDocumentosTramite(${derechohabiente.tramiteId});
				}
			);
		</script>
	<br/><br/>
	<div id="ubicarDomi"></div>
	<div align="center">
		<form>
			<table>
			<tr>
				<td align="center">
				<div id="botones">
					<input id="aceptar" type="button" value = "Autorizar" class="mboton" />
					<input id="rechazar" type="button" value = "Rechazar" class="mboton" />
					<input id="regresar" type="button" value = "<spring:message code="button.regresar"/>" class="mboton" />
				</div>	
				</td>
			</tr>
			</table>
		</form>
	</div>
</div>