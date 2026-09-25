<!-- Vista para la captura de los datos de la persona -->
<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<div class="page_holder_no_height">
	<div class="post_entry_wide no-border">
		<div id="info-paso">
			<h3 style="font-size: 1.8em !important">Paso 3: Captura de los datos complementarios de la persona (Solicitud pendiente)</h3>
			<div class="textwidget">
				<p style="font-size: .9em;">Capture los datos complementarios de la persona (domicilio, medios de contacto, documentos probatorios).</p>
			</div>
		</div>

		<!-- Forma de la consulta de personas por datos basicos. -->
		<div class="form-comment">
			<c:set var="contextpath" value="<%=request.getContextPath()%>" />

			<div id="acordeon" class="contenedor">
				<div class="row" style="width: 900px;">
					<div>
						<h2 style="font-size: .8em !important">Folio de la solicitud pendiente: <strong style="font-size: 1.2em">${folio}</strong></h2>
					</div>
					
					<form:form modelAttribute="tramiteAsegurado" id="paso3Form" action="${contextpath}/tramite/pendiente/concluir/">
						<div class="cell" style="width: 60%;">

							<div id="datosBasicosDiv">
								<fieldset>
									<legend>
										<strong> Datos B&aacute;sicos de la Persona</strong>
									</legend>

									<form:label path="fisica.curp" cssClass="wide">CURP</form:label>
									<form:input path="fisica.curp" id="registroCurp" cssStyle="width: 300px" maxlength="18" />
									<span id="curpError" class="error hiddenElement"></span> <br />
									<br /><br />

									<form:label path="fisica.nombre" cssClass="wide">Nombre(s)</form:label>
									<form:input path="fisica.nombre" id="registroNombres" cssStyle="width: 300px" maxlength="50" />

									<br /><br /><br />
									<form:label path="fisica.primerApellido" cssClass="wide">Primer Apellido</form:label>
									<form:input path="fisica.primerApellido" id="registroPrimerApellido" cssStyle="width: 300px" maxlength="50" />
									<br /><br /><br />

									<form:label path="fisica.segundoApellido" cssClass="wide">Segundo Apellido</form:label>
									<form:input path="fisica.segundoApellido" id="registroSegundoApellido" cssStyle="width: 300px" maxlength="50" />
									<br /><br /><br />

									<form:label path="fisica.sexo.idSexo" cssClass="wide">Sexo</form:label>
									<combo:creaCombo 	idHtml				= "fisica.sexo.idSexo"
														idHtmlContenedor	= "paso3Form"
														entidad				= "mx.gob.imss.ctirss.delta.persistence.DicSexo"
														idHtmlValor			= "${tramiteAsegurado.fisica.sexo.idSexo}" 
														mostrarSoloActivos="true"/>
									<span id="fisica.sexo.idSexoError" class="error hiddenElement"></span>
									<br /><br />
									<form:hidden path="fisica.sexo.descripcion"	id="sexo.descripcion" />

									<form:label path="fisica.fechaNacimiento" cssClass="wide">Fecha de Nacimiento</form:label>
									<form:input path="fisica.fechaNacimiento" id="registroFechaNacimientoC" style="width: 70px"	maxlength="10" />
									<br /><br /><br />

									<form:label path="fisica.lugarNacimiento.clave" cssClass="wide">Lugar de Nacimiento</form:label>
									<combo:creaCombo 	idHtml				= "fisica.lugarNacimiento.clave"
														idHtmlContenedor	= "paso3Form"
														entidad				= "mx.gob.imss.ctirss.delta.persistence.DgCatEstado"
														idHtmlValor			= "${tramiteAsegurado.fisica.lugarNacimiento.clave}" 
														mostrarSoloActivos="true"/>
									<span id="fisica.lugarNacimiento.claveError" class="error hiddenElement"></span>
									<br /><br />
									<form:hidden path="fisica.lugarNacimiento.nombre" id="lugarNacimiento.nombre" />

									<span id="errorFormGeneralError" class="error hiddenElement"></span>
								</fieldset>
							</div>
							
							<!-- Los datos de la serie solo deberan ser mostrados si el usuario es interno... -->
<%-- 							<c:if test="${usuario.perfilUsuario.idPerfilUsuario eq 100 }" scope="session" var="usuarioPerfil"> --%>
								<div id="datosSerie" >
									<fieldset>
										<legend> 
											<strong>&nbsp;Datos de la Serie &nbsp;</strong>
										</legend>
										
										<div>
											<form:label path="serie.idSerie" cssClass="wide">Serie</form:label> 
											<form:select path="serie.idSerie"  >
												<form:option value="-1" label="--Por favor seleccione--"/>
												<form:options items="${series}" itemLabel="tipoSerie.descripcion" itemValue="idSerie"/>
											</form:select>
											<br />
											<form:errors path="serie.idSerie" cssClass="error" />
										</div>
										<br />
										
										<div id="selectSerie" class="hiddenElement">
											<form:label path="serie.anioNacimiento" cssClass="wide">Año de Registro</form:label> 
											<form:select path="serie.anioNacimiento" />
										</div>
										<br />
										<form:errors path="serie.anioNacimiento" cssClass="error" />
										
										<form:hidden path="serie.tipoSerie.idTipoSerie" id="tipoSerie.idTipoSerie" />
									</fieldset>
								</div>
<%-- 							</c:if> --%>
							
							<div style="text-align: right; float: right;">
								<input type="button" value="Cancelar Solicitud" id="cancelarSolicitud"	class="mboton" />
								<input type="button" value="Concluir" id="registrar" class="mboton" />
							</div>
						</div>

						<div class="cell" id="datosComplementarios"	style="padding-left: 10px; height: 500px;">

							<div id="domicilio">

								<fieldset
									style="background: none repeat scroll 0 0 #F5F5F5; border: 1px solid #E5E5E5;">
									<legend>
										<strong>Datos del domicilio ubicado</strong>
									</legend>
									<div id="domicilioLocaliza"></div>

									<div id="datos">

<%-- 										<form:label --%>
<%-- 											path="fisica.domicilios[0].codigoPostal.codigoPostal" --%>
<%-- 											cssClass="wide">C&oacute;digo postal</form:label> --%>
<%-- 										<form:input --%>
<%-- 											path="fisica.domicilios[0].codigoPostal.codigoPostal" --%>
<%-- 											id="codigoPostal" style="width: 50px;" maxlength="5" /> --%>
										
										<label class="wide">C&oacute;digo postal</label>
										<input type="text"  id="codigoPostal" style="width: 50px;" maxlength="5" value="${tramiteAsegurado.fisica.domicilios[0].codigoPostal}" />
																					
										<br /><br /><br />
	
				
<%-- 										<form:label --%>
<%-- 											path="fisica.domicilios[0].asentamiento.localidad.municipio.entidadFederativa.nombre" --%>
<%-- 											cssClass="wide">Entidad federativa</form:label> --%>
<%-- 										<form:input --%>
<%-- 											path="fisica.domicilios[0].asentamiento.localidad.municipio.entidadFederativa.nombre" --%>
<%-- 											id="entidadFederativa" style="width: 300px;" maxlength="50" /> --%>

<%-- 										<form:hidden --%>
<%-- 											path="fisica.domicilios[0].asentamiento.localidad.municipio.entidadFederativa.clave" --%>
<%-- 											id="entidadFederativaClave" /> --%>

										<label class="wide">Entidad federativa</label>
										<input type="text"  id="entidadFederativa" style="width: 300px;" maxlength="50" value="${tramiteAsegurado.fisica.domicilios[0].asentamiento.localidad.municipio.entidadFederativa.nombre}" />
										<input type="hidden"  id="entidadFederativaClave" />


										<br /><br /><br />

<%-- 										<form:label --%>
<%-- 											path="fisica.domicilios[0].asentamiento.localidad.municipio.nombre" --%>
<%-- 											cssClass="wide">Municipio</form:label> --%>
<%-- 										<form:input --%>
<%-- 											path="fisica.domicilios[0].asentamiento.localidad.municipio.nombre" --%>
<%-- 											id="municipio" style="width: 300px;" maxlength="50" /> --%>
<%-- 										<form:hidden --%>
<%-- 											path="fisica.domicilios[0].asentamiento.localidad.municipio.clave" --%>
<%-- 											id="municipioClave" /> --%>


											<label class="wide">Municipio</label>
											<input type="text"  id="municipio" style="width: 300px;" maxlength="50" value="${tramiteAsegurado.fisica.domicilios[0].asentamiento.localidad.municipio.nombre}" />
											<input type="hidden"  id="municipioClave" />
											
										<br /><br /><br />

<%-- 										<form:label --%>
<%-- 											path="fisica.domicilios[0].asentamiento.localidad.nombre" --%>
<%-- 											cssClass="wide">Localidad</form:label> --%>
<%-- 										<form:input --%>
<%-- 											path="fisica.domicilios[0].asentamiento.localidad.nombre" --%>
<%-- 											id="localidad" style="width: 300px;" maxlength="50" /> --%>
<%-- 										<form:hidden --%>
<%-- 											path="fisica.domicilios[0].asentamiento.localidad.clave" --%>
<%-- 											id="localidadClave" />	 --%>


											<label class="wide">Localidad</label>
											<input type="text"  id="localidad" style="width: 300px;" maxlength="50" value="${tramiteAsegurado.fisica.domicilios[0].asentamiento.localidad.nombre}" />
											<input type="hidden"  id="localidadClave" />
											
										<br /><br /><br />

<%-- 										<form:label path="fisica.domicilios[0].asentamiento.nombre" --%>
<%-- 											cssClass="wide">Asentamiento</form:label> --%>
<%-- 										<form:input path="fisica.domicilios[0].asentamiento.nombre" --%>
<%-- 											id="asentamiento" style="width: 300px;" maxlength="50" /> --%>
<%-- 										<form:input path="fisica.domicilios[0].asentamiento.clave" --%>
<%-- 											id="asentamientoClave" /> --%>

											<label class="wide">Asentamiento</label>
											<input type="text"  id="asentamiento" style="width: 300px;" maxlength="50" value="${tramiteAsegurado.fisica.domicilios[0].asentamiento.nombre}" />
											<input type="hidden"  id="asentamientoClave" />
										
										<br /><br /><br />

<%-- 										<form:label --%>
<%-- 											path="fisica.domicilios[0].vialidadPrimaria.nombre" --%>
<%-- 											cssClass="wide">Vialidad primaria</form:label> --%>
<%-- 										<form:input --%>
<%-- 											path="fisica.domicilios[0].vialidadPrimaria.nombre" --%>
<%-- 											id="vialidadPrimaria" style="width: 300px;" maxlength="50" /> --%>
<%-- 										<form:hidden path="fisica.domicilios[0].vialidadPrimaria.clave" id="vialidadPrimaria.clave"/> --%>

											<label class="wide">Vialidad primaria</label>
											<input type="text"  id="vialidadPrimaria" style="width: 300px;" maxlength="50" value="${tramiteAsegurado.fisica.domicilios[0].vialidadPrimaria.nombre}" />
											<input type="hidden"  id="vialidadPrimaria.clave" />
										
										<br /><br /><br />

<%-- 										<form:label path="fisica.domicilios[0].numExterior1" --%>
<%-- 											cssClass="wide">N&uacute;mero exterior principal</form:label> --%>
<%-- 										<form:input path="fisica.domicilios[0].numExterior1" --%>
<%-- 											id="numeroExteriorPrincipal" style="width: 50px;" --%>
<%-- 											maxlength="10" /> --%>
											<label class="wide">N&uacute;mero exterior principal</label>
											<input type="text"  id="numeroExteriorPrincipal" style="width: 50px;" maxlength="10" value="${tramiteAsegurado.fisica.domicilios[0].numExterior1}" />
										
										<br /><br /><br />

										<div style="display: none;">
<%-- 											<form:label path="fisica.domicilios[0].numExteriorAlf" --%>
<%-- 												cssClass="wide">N&uacute;mero exterior alfanum&eacute;rico</form:label> --%>
<%-- 											<form:input path="fisica.domicilios[0].numExteriorAlf" --%>
<%-- 												id="numeroExteriorAlfanumerico" style="width: 50px;" --%>
<%-- 												maxlength="10" /> --%>
												
											<label class="wide">N&uacute;mero exterior alfanum&eacute;rico</label>
											<input type="text"  id="numeroExteriorAlfanumerico" style="width: 50px;" maxlength="10" value="${tramiteAsegurado.fisica.domicilios[0].numExteriorAlf}" />
											
											<br /><br /><br />

<%-- 											<form:label path="fisica.domicilios[0].numExterior2" --%>
<%-- 												cssClass="wide">N&uacute;mero exterior secundario</form:label> --%>
<%-- 											<form:input path="fisica.domicilios[0].numExterior2" --%>
<%-- 												id="numeroExteriorSecundario" style="width: 50px;" --%>
<%-- 												maxlength="10" /> --%>
												
											<label class="wide">N&uacute;mero exterior secundario</label>
											<input type="text"  id="numeroExteriorSecundario" style="width: 50px;" maxlength="10" value="${tramiteAsegurado.fisica.domicilios[0].numExterior2}" />
											
											<br /><br /><br />

<%-- 											<form:label path="fisica.domicilios[0].numInterior" --%>
<%-- 												cssClass="wide">N&uacute;mero interior</form:label> --%>
<%-- 											<form:input path="fisica.domicilios[0].numInterior" --%>
<%-- 												id="numeroInterior" style="width: 50px;" maxlength="10" /> --%>
											
											<label class="wide">N&uacute;mero interior</label>
											<input type="text"  id="numeroInterior" style="width: 50px;" maxlength="10" value="${tramiteAsegurado.fisica.domicilios[0].numInterior}" />
												
											<br /><br /><br />

<%-- 											<form:label path="fisica.domicilios[0].numInteriorAlf" --%>
<%-- 												cssClass="wide">N&uacute;mero interior alfanum&eacute;rico</form:label> --%>
<%-- 											<form:input path="fisica.domicilios[0].numInteriorAlf" --%>
<%-- 												id="numeroInteriorAlfanumerico" style="width: 50px;" --%>
<%-- 												maxlength="10" /> --%>
												
											<label class="wide">N&uacute;mero interior alfanum&eacute;rico</label>
											<input type="text"  id="numeroInteriorAlfanumerico" style="width: 50px;" maxlength="10" value="${tramiteAsegurado.fisica.domicilios[0].numInteriorAlf}" />	
												
											<br /><br /><br />

<%-- 											<form:label --%>
<%-- 												path="fisica.domicilios[0].vialidadReferenciaPrimaria.nombre" --%>
<%-- 												cssClass="wide">Vialidad referencia primaria</form:label> --%>
<%-- 											<form:input --%>
<%-- 												path="fisica.domicilios[0].vialidadReferenciaPrimaria.nombre" --%>
<%-- 												id="vialidadReferenciaPrimaria" style="width: 300px;" --%>
<%-- 												maxlength="50" /> --%>
<%-- 											<form:hidden path="fisica.domicilios[0].vialidadReferenciaPrimaria.clave" id="vialidadReferenciaPrimaria.clave"/> --%>
											
											<label class="wide">Vialidad referencia primaria</label>
											<input type="text" id="vialidadReferenciaPrimaria" style="width: 300px;" maxlength="50" value="${tramiteAsegurado.fisica.domicilios[0].vialidadReferenciaPrimaria.clave}" />
											<input type="hidden" id="vialidadReferenciaPrimaria.clave"/>
											
											<br /><br /><br />

<%-- 											<form:label --%>
<%-- 												path="fisica.domicilios[0].vialidadReferenciaSecundaria.nombre" --%>
<%-- 												cssClass="wide">Vialidad referencia secundaria</form:label> --%>
<%-- 											<form:input --%>
<%-- 												path="fisica.domicilios[0].vialidadReferenciaSecundaria.nombre" --%>
<%-- 												id="vialidadReferenciaSecundaria" style="width: 300px;" --%>
<%-- 												maxlength="50" /> --%>
<%-- 											<form:hidden path="fisica.domicilios[0].vialidadReferenciaSecundaria.clave" id="vialidadReferenciaSecundaria.clave"/> --%>
											
											<label class="wide">Vialidad referencia secundaria</label>
											<input type="text" id="vialidadReferenciaSecundaria" style="width: 300px;" maxlength="50" value="${tramiteAsegurado.fisica.domicilios[0].vialidadReferenciaSecundaria.clave}" />
											<input type="hidden" id="vialidadReferenciaSecundaria.clave"/>
											
											<br /><br /><br />

<%-- 											<form:label --%>
<%-- 												path="fisica.domicilios[0].vialidadReferenciaPosterior.nombre" --%>
<%-- 												cssClass="wide">Vialidad referencia posterior</form:label> --%>
<%-- 											<form:input --%>
<%-- 												path="fisica.domicilios[0].vialidadReferenciaPosterior.nombre" --%>
<%-- 												id="vialidadReferenciaPosterior" style="width: 300px;" --%>
<%-- 												maxlength="50" /> --%>
<%-- 											<form:hidden path="fisica.domicilios[0].vialidadReferenciaPosterior.clave" id="vialidadReferenciaPosterior.clave"/> --%>
											
											<label class="wide">Vialidad referencia posterior</label>
											<input type="text" id="vialidadReferenciaPosterior" style="width: 300px;" maxlength="50" value="${tramiteAsegurado.fisica.domicilios[0].vialidadReferenciaPosterior.clave}" />
											<input type="hidden" id="vialidadReferenciaPosterior.clave"/>
											
											<br /><br /><br />
											
										</div>

									</div>
									<div style="text-align: right; float: right;">
										<input type="button" value="Ubicar domicilio" class="mboton" id="btnUbicarDomicilio" />
									</div>
								</fieldset>


							</div>
							<br />
							
							<div id="mediosContacto">

								<fieldset
									style="background: none repeat scroll 0 0 #F5F5F5; border: 1px solid #E5E5E5;">
									<legend>
										<strong> Medios de contacto</strong>
									</legend>
									<div id="mediosContactoCaptura"></div>
									<div id="mediosContactoDatos">

<%-- 										<form:label path="fisica.telefonoFijo.numero" cssClass="wide">N&uacute;mero Tel&eacute;fono Particular</form:label> --%>
<%-- 										<form:input path="fisica.telefonoFijo.numero" --%>
<%-- 											id="numeroTelefonicoParticular" style="width: 200px;" --%>
<%-- 											maxlength="8" /> --%>
											
										<label class="wide" > N&uacute;mero Tel&eacute;fono Particular</label>
										<input type="text" id="numeroTelefonicoParticular" style="width: 200px;" maxlength="80" value="${tramiteAsegurado.fisica.mediosContacto[0].numero}" />
											
										<br /><br /><br />

<%-- 										<form:label path="fisica.telefonoFijo.claveLada" --%>
<%-- 											cssClass="wide">Clave Lada</form:label> --%>
<%-- 										<form:input path="fisica.telefonoFijo.claveLada" --%>
<%-- 											id="claveLada" style="width: 100px;" maxlength="3" /> --%>
											
										
										<label class="wide" > Clave Lada </label>
										<input type="text" id="claveLada" style="width: 100px;" maxlength="3" value="${tramiteAsegurado.fisica.mediosContacto[0].claveLada}" />
										
										<br /><br /><br />
										
<%-- 										<form:label path="fisica.telefonoFijo.extension" --%>
<%-- 											cssClass="wide">Extensi&oacute;n</form:label> --%>
<%-- 										<form:input path="fisica.telefonoFijo.extension" --%>
<%-- 											id="extension" style="width: 100px;" maxlength="5" /> --%>
											
										<label class="wide" > Extensi&oacute;n </label>
										<input type="text" id="extension" style="width: 100px;" maxlength="5" value="${tramiteAsegurado.fisica.mediosContacto[0].extension}" />	
											
										<br /><br /><br />

<%-- 										<form:label path="fisica.telefonoMovil.numero" cssClass="wide">N&uacute;mero Tel&eacute;fono M&oacute;vil</form:label> --%>
<%-- 										<form:input path="fisica.telefonoMovil.numero" --%>
<%-- 											id="numeroTelefonicoMovil" style="width: 200px;" --%>
<%-- 											maxlength="10" /> --%>
											
										<label class="wide" > N&uacute;mero Tel&eacute;fono M&oacute;vil </label>
										<input type="text" id="numeroTelefonicoMovil" style="width: 200px;" maxlength="10" value="${tramiteAsegurado.fisica.mediosContacto[1].numero}" />		
											
										<br /><br /><br />

<%-- 										<form:label path="fisica.correoElectronico.correo" --%>
<%-- 											cssClass="wide">Correo Electr&oacute;nico</form:label> --%>
<%-- 										<form:input path="fisica.correoElectronico.correo" --%>
<%-- 											id="correoElectronico" style="width: 200px;" maxlength="50" /> --%>
											
										<label class="wide" > Correo Electr&oacute;nico </label>
										<input type="text" id="correoElectronico" style="width: 200px;" maxlength="10" value="${tramiteAsegurado.fisica.mediosContacto[2].correo}" />
										
										<br /><br /><br />

										<div style="text-align: right; float: right;">
											<input type="button" value="Registrar Medios" class="mboton" id="btnRegistrarMedios" />
										</div>

									</div>
								</fieldset>



							</div>

						</div>

					</form:form>

				</div>
			</div>
			<!-- acordeon -->




		</div>
	</div>
</div>

<!-- DIV de jquery para poder desplegar un cuadro de dialogo tipo confirm para la cancelacion de una solicitud pendiente -->
<div id="dgCancelarSolicitudPendiente" title="Cancelar solicitud pendiente" >
	<p>
		<span class="ui-icon ui-icon-alert"	style="float: left; margin: 0 7px 20px 0;"> </span>
		¿Est&aacute; UD. seguro de querer cancelar esta solicitud?
	</p>
	<br />
</div>		
<!--  -->
	
<script 
type="text/javascript" src="<spring:url value="/static/resources/js/delta/tramite/tramite-capturaComplementosConSerie.js" htmlEscape="true" />"></script>