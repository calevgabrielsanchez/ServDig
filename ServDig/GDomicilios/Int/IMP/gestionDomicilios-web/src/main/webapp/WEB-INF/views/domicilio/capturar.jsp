<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>




 



<div class="page_holder">

<div class="contenedor">
	<div class="row">
		<!-- Seccion de la captura de datos del domicilio  -->
		<div class="cell" style="width: 550px;">
		
			
			
			<div class="row"  >
				<div class="row" >
					<h2> Capturar domicilio geogr&aacute;fico nacional </h2>
					<h3 style="font-size: .9em;color: #666666"> Capture los datos del domicilio. </h3>
				</div>
			</div>
		
			</br>
		
			<div class="row form-comment"  id="domicilio">
				<form id="formDomicilio">
					<span id="errorNegocioLabel" class="error hiddenElement"></span>
						
						
						
						
						
						<fieldset style="margin: 20px !important;">
							
							<legend>
								<strong>Datos del domicilio</strong>
							</legend>
							
							
							
							<!-- Entidad Federativa -->
							<fieldset class="fsInterno">
								<div>
									<span id="asentamiento.localidad.municipio.entidadFederativa.claveError" class="error hiddenElement"></span>
								</div>
								<div>
									<label  >
											<spring:message code="label.entidadFederativa" />
									</label>
									
									<combo:creaCombo idHtml="asentamiento.localidad.municipio.entidadFederativa.clave" 
									idHtmlContenedor="formDomicilio" 
									entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado"
									mostrarSoloActivos="true"/>
									
								</div>
							</fieldset>
															
							<!-- Municipio -->
							<fieldset class="fsInterno">
								<div>
									<span id="asentamiento.localidad.municipio.claveError" class="error hiddenElement"></span>
								</div>
								<div>
								<label >
									<spring:message code="label.municipio"/>
								</label>
								
								<combo:creaCombo 		entidad			="mx.gob.imss.ctirss.delta.persistence.DgCatMunicipio" 
		                     					idHtml			="asentamiento.localidad.municipio.clave" 
		                     				  	entidadPadre	="dgCatEstado.cveEnt"
		                     				  	idHtmlPadre		="asentamiento.localidad.municipio.entidadFederativa.clave"
		                     				  	idHtmlContenedor="formDomicilio"
		                     				  	mostrarSoloActivos="false"/>   
								</div>
							</fieldset>
							
							
							<!-- Campo de asentamiento -->
							<fieldset class="fsInterno">
								<div>
									<span id="asentamiento.clave" class="error hiddenElement"></span>
								</div>
								<div>
									<label  >
											<spring:message code="label.asentamiento" />
									</label>
									<select id="asentamiento.clave" name="clave"  >
									<option>-- Seleccione --</option>
									</select>
								</div>
							</fieldset>
							
							
							
							
							<!-- Campo del codigo postal -->							
							<fieldset class="fsInterno">
								<div>
									<span id="codigoPostal.codigoPostalError" class="error hiddenElement"></span>
								</div>
								<div>
									<label >
											<spring:message code="label.codigoPostal" />
									</label> 
									<input type="text" id="codigoPostal.codigoPostal" maxlength="8" />
								</div>
							</fieldset>
						
						
						
						
						<!-- Vialidad Primaria -->
									<fieldset class="fsInterno">
										<legend>
											<spring:message code="label.vialidadPrimaria" />
										</legend>
										<div>
											<span id="vialidadPrimaria.clave" class="error hiddenElement"></span>
										</div>
										<div>
											<select id="vialidadPrimaria.clave"
												name="vialidadPrimaria.clave" style="float: left; width: 300px !important;" >
												<option> Cargando...  </option>
											</select>
											<input type="hidden" id="vialidadPrimaria.clave.hidden"/>
										</div>
									</fieldset>
									<!-- Numero y Letra exterior principal -->
									<fieldset class="fsInterno">
										<legend>
											<spring:message code="label.numeroLetraExterior" />
										</legend>

										<div>
											<span id="numExterior1" class="error hiddenElement"></span>
										</div>
											<div>
												<span style="font-size: 0.7em;"> <strong>Ej. 999 / Mz. 9 Lt. 8</strong> </span>
											</div>
										<div>
											<input type="text" id="numExterior1" maxlength="5" size="3"  style="width: 100px !important;"/>
											<span> <strong>/</strong> </span>
											<input type="text" id="numExteriorAlf" maxlength="35" style="width: 100px !important;" />
										</div>

									</fieldset>

									<!-- Numero y Letra  interior-->
									<fieldset class="fsInterno">

										<legend>
											<spring:message code="label.numeroLetraInterior" />
										</legend>


										<div>
											<span id="numInterior" class="error hiddenElement"></span>
										</div>
										<div>
												<span style="font-size: 0.7em;"> <strong>Ej. 999 / Mz. 9 Lt. 8</strong> </span>
											</div>

										<div>

											<input type="text" id="numInterior" maxlength="5" size="3" />
											<span> <strong>/</strong> </span>
											<input type="text" id="numInteriorAlf" maxlength="35" />
										</div>
									</fieldset>

									<!-- Numero exterior secundario  -->
									<fieldset class="fsInterno">


										<div>
											<span id="numExterior2" class="error hiddenElement"></span>
										</div>

										<div>
											<div>
											<label >
												<spring:message code="label.numExterior2" />
											</label>
											</div>
											<input type="text" id="numExterior2" maxlength="35" />
										</div>
									</fieldset>






									<!-- Vialidad referencia primaria-->


									<fieldset class="fsInterno">
										<legend>
											<spring:message code="label.vialidadReferenciaPrimaria" />
										</legend>
										<div>
											<span id="vialidadReferenciaPrimaria.clave" class="error hiddenElement"></span>	
										</div>
										<div>

											<select id="vialidadReferenciaPrimaria.clave"
												name="vialidadReferenciaPrimaria.clave" 
												style="float: left; width: 300px !important;">
												<option> Cargando...  </option>
											</select>
											<input type="hidden"  id="vialidadReferenciaPrimaria.clave.hidden"/>
										</div>
									</fieldset>




									<!-- Vialidad referencia secundaria-->
									<fieldset class="fsInterno">
										<legend>
											<spring:message code="label.vialidadReferenciaSecundaria" />
										</legend>
										<div>
											<span id="vialidadReferenciaSecundaria.clave" class="error hiddenElement"></span>	
										</div>
										<div>
											<select id="vialidadReferenciaSecundaria.clave"
												name="vialidadReferenciaSecundaria.clave" style="float: left; width: 300px !important;">
												<option>Cargando...  </option>
											</select>
											<input type="hidden" id="vialidadReferenciaSecundaria.clave.hidden"/>
										</div>
									</fieldset>



									<!-- Vialidad referencia posterior-->

									<fieldset class="fsInterno">
										<legend>
											<spring:message code="label.vialidadReferenciaPosterior" />
										</legend>
										<div>
											<span id="vialidadReferenciaPosterior" class="error hiddenElement"></span>	
										</div>
										<div>
											<select id="vialidadReferenciaPosterior.clave"
												name="vialidadReferenciaPosterior.clave" style="float: left; width: 300px !important;">
												<option>Cargando...  </option>
											</select>
											
											<input type="hidden" id="vialidadReferenciaPosterior"/>
										</div>
									</fieldset>
						</fieldset>
					
				</form>
			</div>
		
		</div>
		<!-- Seccion del mapa del domicilio -->
		<div class="cell">
			<div id="map_canvas" style="width: 100%; height: 100%">
		</div>
	</div>
</div>
</div>
</div>


<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&region=MX"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/domicilios/captura.js" htmlEscape="true" />"></script>