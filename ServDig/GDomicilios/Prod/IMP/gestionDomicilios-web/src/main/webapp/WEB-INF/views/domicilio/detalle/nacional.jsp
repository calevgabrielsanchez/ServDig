<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ include file="../../general/taglibs.jsp"%>



	



			
			
			
			
			

				<div class="cell" style=" float:left;  width:450px !important; height: 500px !important; ">
						
						
						
						<form:form  modelAttribute="domicilio" id="formDomicilio">
							
							<fieldset style="margin: 20px !important; height: 450px">
							<legend>
								<strong>Asentamiento localizado</strong>
							</legend>
							
							<!-- Campo del codigo postal -->							
							<fieldset class="fsInterno">
							<span id="errorNegocioLabel" class="error hiddenElement"></span>
								<div>
									<form:errors path="codigoPostal.codigoPostal" cssClass="error" />
								</div>
								<div>
								<form:label path="codigoPostal.codigoPostal">
										<spring:message code="label.codigoPostal" />
								</form:label> 
								<form:input type="text" path="codigoPostal.codigoPostal" maxlength="8" readonly="true" />
								</div>
							</fieldset>
							
							<!-- Entidad Federativa -->
							<fieldset class="fsInterno">
					
							<div>
								<form:label path="asentamiento.localidad.municipio.entidadFederativa.clave">
										<spring:message code="label.entidadFederativa" />
								</form:label>
								<form:input path="asentamiento.localidad.municipio.entidadFederativa.nombre" readonly="true"/>
							</div>
							</fieldset>
															
							<!-- Municipio -->
							<fieldset class="fsInterno">
								
								<div>
								<form:label path="asentamiento.localidad.municipio.nombre" >
									<spring:message code="label.municipio"/>
								</form:label>
								<form:input path="asentamiento.localidad.municipio.nombre" readonly="true"/>
								</div>
							</fieldset>	
							
							<!-- Localidad -->
							<fieldset class="fsInterno">
								
								<div>
								<form:label path="asentamiento.localidad.nombre" >
									<spring:message code="label.localidad"/>
								</form:label>
								<form:input path="asentamiento.localidad.nombre" readonly="true"/>
								</div>
							</fieldset>
							
							
							<!-- Asentamiento-->
							<fieldset class="fsInterno">
								
								<div>
								<form:label path="asentamiento.nombre" >
									<spring:message code="label.asentamiento"/>
								</form:label>
								<form:input path="asentamiento.nombre" readonly="true"/>
								</div>
							</fieldset>	
							
							
							<!-- Vialidad principal -->
							<fieldset class="fsInterno">
							<legend>
								<spring:message code="label.vialidadPrimaria" />
							</legend>
								<div>
									<form:errors path="vialidadPrimaria.nombre" cssClass="error" />
								</div>
								<div>
								<form:label path="vialidadPrimaria.nombre">
										<spring:message code="label.vialidad.nombre" />
								</form:label> 
								<form:input type="text" path="vialidadPrimaria.nombre" maxlength="255" size="50"/>
									
								</div>
								
								
								
								<div>
									<form:errors path="vialidadPrimaria.tipoVialidad.clave" cssClass="error" />
								</div>
								<div id="vialidadPrimaria">
								<form:label path="vialidadPrimaria.nombre">
										<spring:message code="label.vialidad.tipo" />
								</form:label> 
								
								<combo:creaCombo idHtml="vialidadPrimaria.tipoVialidad.clave"
 										idHtmlContenedor="vialidadPrimaria"
										entidad="mx.gob.imss.ctirss.delta.persistence.DgCatVialidad" 
										mostrarSoloActivos="false"/> 

								</div>
								
							</fieldset>
							
							
							<!-- Numero y Letra exterior principal -->							
							<fieldset class="fsInterno">
								<legend>
									<spring:message code="label.numeroLetraExterior" />
								</legend>
								
								<div>
									<form:errors path="numExterior1" cssClass="error" />
								</div>
								
								<div>
									<form:label path="numExterior1">
											<spring:message code="label.numero" />
									</form:label> 
									
									<form:input type="text" path="numExterior1" maxlength="5" />
									<span> -- </span> 
									<form:label path="numExterior1">
											<spring:message code="label.letra" />
									</form:label>
									<form:input type="text" path="numExteriorAlf" maxlength="35" />
								</div>
								
							</fieldset>
							
						<!-- Numero y Letra  interior-->							
							<fieldset class="fsInterno">
							
									<legend>
										<spring:message code="label.numeroLetraInterior" />
									</legend>
							
							
								<div>
									<form:errors path="numInterior" cssClass="error" />
								</div>
								
								<div>
									<form:label path="numInterior">
											<spring:message code="label.numero" />
									</form:label> 
									
									<form:input type="text" path="numInterior" maxlength="5" />
									<span> -- </span> 
									<form:label path="numInterior">
											<spring:message code="label.letra" />
									</form:label> 
									<form:input type="text" path="numInteriorAlf" maxlength="35" />
								</div>
							</fieldset>
							
							<!-- Datos complementarios, vialidades secundarias , etc -->
							
							<fieldset class="fsInterno">
								<legend>
									Datos complementarios
								</legend>
								<div>
						<p><span class="etiqueta"> Vialidad :</span> <span class="dato">${domicilio.vialidadPrimaria.nombre} </span></p>
						<p><span class="etiqueta"> Numero / Letra exterior :</span> <span class="dato">${domicilio.numExterior1} ,${domicilio.numExteriorAlf}, ${domicilio.numExterior2 }</span></p>
						<p><span class="etiqueta"> Numero / Letra interior :</span> <span class="dato">${domicilio.numInterior }, ${domicilio.numInteriorAlf } </span></p>
						
					</div>
							
							</fieldset>
							
							
								
							</fieldset>
						
					</form:form>
						
						</div>


