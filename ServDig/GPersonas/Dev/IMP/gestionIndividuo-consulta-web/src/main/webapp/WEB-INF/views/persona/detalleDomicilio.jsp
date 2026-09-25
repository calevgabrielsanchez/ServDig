<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

						<fieldset>
						
							<legend>
								<strong style="font-size: .8em;">Componentes principales del domicilio.</strong>
							</legend>
						
						<div>
						
						
						
						<form:label path="personaFisica.domicilios[0].vialidadPrimaria.tipoVialidad.descripcion" cssClass="wide">Tipo Vialidad </form:label>
						<form:input path="personaFisica.domicilios[0].vialidadPrimaria.tipoVialidad.descripcion" id="tipoVialidadPrimaria" style="width: 300px;" maxlength="50" />
						<br/><br/><br/>
						
						<form:label path="personaFisica.domicilios[0].vialidadPrimaria.nombre" cssClass="wide">Vialidad Primaria</form:label>
						<form:input path="personaFisica.domicilios[0].vialidadPrimaria.nombre" id="vialidadPrimaria" style="width: 300px;" maxlength="50" />
						<br/><br/><br/>
						
						<form:label path="personaFisica.domicilios[0].numExterior1" cssClass="wide">N&uacute;mero / Letra Exterior </form:label>
						<div>
							<form:input path="personaFisica.domicilios[0].numExterior1" id="numeroExteriorPrincipal" style="width: 50px;" maxlength="10" />
							
							<form:input path="personaFisica.domicilios[0].numExteriorAlf" id="numeroExteriorAlfanumerico" style="width: 50px;" maxlength="10" size="30" />
						</div>
						<br/><br/><br/>							

						<form:label path="personaFisica.domicilios[0].numInterior" cssClass="wide">N&uacute;mero / Letra Interior</form:label>
						<div>
							<form:input path="personaFisica.domicilios[0].numInterior" id="numeroInterior" style="width: 50px;" maxlength="10" />
							
							<form:input path="personaFisica.domicilios[0].numInteriorAlf" id="numeroInteriorAlfanumerico" style="width: 50px;" maxlength="10" size="30"/>
						</div>
						<br/><br/><br/>	
																											
						

						<form:label path="personaFisica.domicilios[0].numExterior2" cssClass="wide">N&uacute;mero Exterior Secundario</form:label>
						<form:input path="personaFisica.domicilios[0].numExterior2" id="numeroExteriorSecundario" style="width: 50px;" maxlength="10"  />
						<br/><br/><br/>									

						
						<form:label path="personaFisica.domicilios[0].asentamiento.tipoAsentamiento.descripcion" cssClass="wide">Tipo Asentamiento</form:label>
						<form:input path="personaFisica.domicilios[0].asentamiento.tipoAsentamiento.descripcion" id="tipoAsentamiento" style="width: 300px;" maxlength="50" />
						<br/><br/><br/>
						<form:label path="personaFisica.domicilios[0].asentamiento.nombre" cssClass="wide">Asentamiento</form:label>
						<form:input path="personaFisica.domicilios[0].asentamiento.nombre" id="asentamiento" style="width: 300px;" maxlength="50" />
						<br/><br/><br/>	

						<form:label path="personaFisica.domicilios[0].codigoPostal.codigoPostal" cssClass="wide">C&oacute;digo Postal</form:label>
						<form:input path="personaFisica.domicilios[0].codigoPostal.codigoPostal" id="codigoPostal" style="width: 50px;" maxlength="5" />
						<br/><br/><br/>
						
						<form:label path="personaFisica.domicilios[0].asentamiento.localidad.nombre" cssClass="wide">Localidad</form:label>
						<form:input path="personaFisica.domicilios[0].asentamiento.localidad.nombre" id="localidad" style="width: 300px;" maxlength="50" />
						<br/><br/><br/>
						
						<form:label path="personaFisica.domicilios[0].asentamiento.localidad.municipio.nombre" cssClass="wide">Municipio</form:label>
						<form:input path="personaFisica.domicilios[0].asentamiento.localidad.municipio.nombre" id="municipio" style="width: 300px;" maxlength="50" />
						<br/><br/><br/>
						
						<form:label path="personaFisica.domicilios[0].asentamiento.localidad.municipio.entidadFederativa.nombre" cssClass="wide">Entidad Federativa</form:label>
						<form:input path="personaFisica.domicilios[0].asentamiento.localidad.municipio.entidadFederativa.nombre" id="entidadFederativa" style="width: 300px;" maxlength="50" />
						<br/><br/><br/>
						</div>
						</fieldset>					

						
					
												
													
						<fieldset>
						
							<legend><strong style="font-size: .8em;">Componentes secundarios del domicilio.</strong></legend>
						
						
						<form:label path="personaFisica.domicilios[0].vialidadReferenciaPrimaria.tipoVialidad.descripcion" cssClass="wide">Tipo Vialidad </form:label>
						<form:input path="personaFisica.domicilios[0].vialidadReferenciaPrimaria.tipoVialidad.descripcion" id="tipoVialidadReferenciaPrimaria" style="width: 300px;" maxlength="50" />
						<br/><br/><br/>
						<form:label path="personaFisica.domicilios[0].vialidadReferenciaPrimaria.nombre" cssClass="wide">Vialidad Referencia Primaria</form:label>
						<form:input path="personaFisica.domicilios[0].vialidadReferenciaPrimaria.nombre" id="vialidadReferenciaPrimaria" style="width: 300px;" maxlength="50" />
						<br/><br/><br/>	
						<form:label path="personaFisica.domicilios[0].vialidadReferenciaSecundaria.tipoVialidad.descripcion" cssClass="wide">Tipo Vialidad </form:label>
						<form:input path="personaFisica.domicilios[0].vialidadReferenciaSecundaria.tipoVialidad.descripcion" id="tipoVialidadReferenciaSecundaria" style="width: 300px;" maxlength="50" />
						<br/><br/><br/>
						<form:label path="personaFisica.domicilios[0].vialidadReferenciaSecundaria.nombre" cssClass="wide">Vialidad Referencia Secundaria</form:label>
						<form:input path="personaFisica.domicilios[0].vialidadReferenciaSecundaria.nombre" id="vialidadReferenciaSecundaria" style="width: 300px;" maxlength="50" />
						<br/><br/><br/>								
						<form:label path="personaFisica.domicilios[0].vialidadReferenciaPosterior.tipoVialidad.descripcion" cssClass="wide">Tipo Vialidad </form:label>
						<form:input path="personaFisica.domicilios[0].vialidadReferenciaPosterior.tipoVialidad.descripcion" id="tipoVialidadReferenciaPosterior" style="width: 300px;" maxlength="50" />
						<br/><br/><br/>
						<form:label path="personaFisica.domicilios[0].vialidadReferenciaPosterior.nombre" cssClass="wide">Vialidad Referencia Posterior</form:label>
						<form:input path="personaFisica.domicilios[0].vialidadReferenciaPosterior.nombre" id="vialidadReferenciaPosterior" style="width: 300px;" maxlength="50" />
						<br/><br/><br/>		
						
						
						<form:label path="personaFisica.domicilios[0].descripcion" cssClass="wide">Descripci&oacute;n de Ubicación</form:label>
						<form:textarea path="personaFisica.domicilios[0].descripcion" id="descripcion" cssStyle="width:360px !important; height: 120px !important; overflow:auto;" cols="10" maxlength="50" />
						<br/><br/><br/>
						
						</fieldset>						
						
						<!-- atributos de las vialidades -->
						<form:hidden path="personaFisica.domicilios[0].vialidadPrimaria.clave" id="vialidadPrimaria.clave"/>
						<form:hidden path="personaFisica.domicilios[0].vialidadReferenciaPrimaria.clave" id="vialidadReferenciaPrimaria.clave"/>
						<form:hidden path="personaFisica.domicilios[0].vialidadReferenciaSecundaria.clave" id="vialidadReferenciaSecundaria.clave"/>
						<form:hidden path="personaFisica.domicilios[0].vialidadReferenciaPosterior.clave" id="vialidadReferenciaPosterior.clave"/>
						
						