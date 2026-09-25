<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&region=MX"></script> 
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/domicilios/domicilios.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/domicilios/domicilios-map.js" htmlEscape="true" />"></script>
		
<div class="page_holder">
	<div class="contenedor">
		
		<div >
			<div style=" display:inline; width:100%">
			
			
			<div class="row"  style="width:100%">
			
				<div id="titulo" class="row" style="width:100%">
					<h2> Ubicar domicilio geogr&aacute;fico nacional </h2>
					<h3 style="font-size: .9em;color: #666666"> Paso 2 / 2 </h3>
				</div>
				<div id="asentamientoMapa" class="row" style="width:100%">
					
					
						<div class="cell" style=" float:left;  width:450px !important; height: 500px !important; ">
						
						
						
						<form:form  modelAttribute="asentamiento" id="formAsentamiento">
							
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
								<form:label path="localidad.municipio.entidadFederativa.clave">
										<spring:message code="label.entidadFederativa" />
								</form:label>
								<form:input path="localidad.municipio.entidadFederativa.nombre" readonly="true"/>
							</div>
							</fieldset>
															
							<!-- Municipio -->
							<fieldset class="fsInterno">
								
								<div>
								<form:label path="localidad.municipio.nombre" >
									<spring:message code="label.municipio"/>
								</form:label>
								<form:input path="localidad.municipio.nombre" readonly="true"/>
								</div>
							</fieldset>	
							
							<!-- Localidad -->
							<fieldset class="fsInterno">
								
								<div>
								<form:label path="localidad.nombre" >
									<spring:message code="label.localidad"/>
								</form:label>
								<form:input path="localidad.nombre" readonly="true"/>
								</div>
							</fieldset>
							
							
							<!-- Asentamiento-->
							<fieldset class="fsInterno">
								
								<div>
								<form:label path="nombre" >
									<spring:message code="label.asentamiento"/>
								</form:label>
								<form:input path="nombre" readonly="true"/>
								</div>
							</fieldset>	
								
							</fieldset>
						
					</form:form>
						
						</div>
						
						<div class="cell" style=" float:right;  width:450px !important; height: 500px !important; ">
						
							<div id="map_canvas" style="width: 100%; height: 100%">
							
							</div>
						
						</div>
					
					</div><!-- Fin del div del asentamiento y mapa -->
				
				
				
			</div><!-- Fin del div contenedor -->
				
				
					<c:set var="contextpath" value="<%=request.getContextPath()    %>" />
					<div  id="datosComplementarios" class="row" style="width:100%" >
							
							<form:form action="${contextpath}/domicilio/nacional/ubicar/complemento/guardar" method="POST" modelAttribute="domicilio" id="formComplemento">

							
							<fieldset style="margin: 20px !important;">
							<legend>
								<strong>Datos complementarios de su domicilio</strong>
							</legend>
							
							<div class="row">
								<div class="cell" >
							<!-- Datos del asentamiento van ocultos. -->
								
								<!-- Campo del codigo postal -->							
								<form:hidden  path="codigoPostal.codigoPostal" maxlength="8" readonly="true" />
								<form:hidden  path="asentamiento.codigoPostal.codigoPostal" maxlength="8" readonly="true" />
							<!-- Entidad Federativa -->
								<form:hidden path="asentamiento.localidad.municipio.entidadFederativa.nombre" />
								<form:hidden path="asentamiento.localidad.municipio.entidadFederativa.clave" />
															
							<!-- Municipio -->
								<form:hidden path="asentamiento.localidad.municipio.nombre" readonly="true"/>
								<form:hidden path="asentamiento.localidad.municipio.clave" />
							
							<!-- Localidad -->
								<form:hidden path="asentamiento.localidad.nombre" readonly="true"/>
								<form:hidden path="asentamiento.localidad.clave" />
							
							<!-- Asentamiento-->
								<form:hidden path="asentamiento.nombre" readonly="true"/>
								<form:hidden path="asentamiento.clave" />
								
							
							
							
							<!-- Vialidad Primaria -->							
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
										mostrarSoloActivos = "false" /> 

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
							
								<!-- Numero exterior secundario  -->							
							<fieldset class="fsInterno">
							
							
								<div>
									<form:errors path="numExterior2" cssClass="error" />
								</div>
								
								<div>
								<form:label path="numExterior2">
										<spring:message code="label.numExterior2" />
								</form:label> 
								<form:input type="text" path="numExterior2" maxlength="35" />
								</div>
							</fieldset>
							
								
				



								<!-- Vialidad referencia primaria-->
								
								
								<fieldset class="fsInterno">
							<legend>
								<spring:message code="label.vialidadReferenciaPrimaria" />
							</legend>
								<div>
									<form:errors path="vialidadReferenciaPrimaria.nombre" cssClass="error" />
								</div>
								<div>
								<form:label path="vialidadReferenciaPrimaria.nombre">
										<spring:message code="label.vialidad.nombre" />
								</form:label> 
								<form:input type="text" path="vialidadReferenciaPrimaria.nombre" maxlength="255" size="50"/>
									
								</div>
								
								
								
								<div>
									<form:errors path="vialidadReferenciaPrimaria.tipoVialidad.clave" cssClass="error" />
								</div>
								<div id="vialidadReferenciaPrimaria">
								<form:label path="vialidadReferenciaPrimaria.nombre">
										<spring:message code="label.vialidad.tipo" />
								</form:label> 
								
								<combo:creaCombo idHtml="vialidadReferenciaPrimaria.tipoVialidad.clave"
 										idHtmlContenedor="vialidadReferenciaPrimaria"
 										entidad="mx.gob.imss.ctirss.delta.persistence.DgCatVialidad" 
 										mostrarSoloActivos = "false" />

								</div>
								
							</fieldset>
								
								
						
							
								<!-- Vialidad referencia secundaria-->	
								<fieldset class="fsInterno">
							<legend>
								<spring:message code="label.vialidadReferenciaSecundaria" />
							</legend>
								<div>
									<form:errors path="vialidadReferenciaSecundaria.nombre" cssClass="error" />
								</div>
								<div>
								<form:label path="vialidadReferenciaSecundaria.nombre">
										<spring:message code="label.vialidad.nombre" />
								</form:label> 
								<form:input type="text" path="vialidadReferenciaSecundaria.nombre" maxlength="255" size="50"/>
									
								</div>
								
								
								
								<div>
									<form:errors path="vialidadReferenciaSecundaria.tipoVialidad.clave" cssClass="error" />
								</div>
								<div id="vialidadReferenciaSecundaria">
								<form:label path="vialidadReferenciaSecundaria.nombre">
										<spring:message code="label.vialidad.tipo" />
								</form:label> 
								<combo:creaCombo idHtml="vialidadReferenciaSecundaria.tipoVialidad.clave"
 										idHtmlContenedor="vialidadReferenciaSecundaria" 
										entidad="mx.gob.imss.ctirss.delta.persistence.DgCatVialidad" 
										mostrarSoloActivos = "false" /> 
										
								</div>
								
								
								
							</fieldset>
													
							
							
								<!-- Vialidad referencia posterior-->	
								
									<fieldset class="fsInterno">
							<legend>
								<spring:message code="label.vialidadReferenciaPosterior" />
							</legend>
								<div>
									<form:errors path="vialidadReferenciaPosterior.nombre" cssClass="error" />
								</div>
								<div>
								<form:label path="vialidadReferenciaPosterior.nombre">
										<spring:message code="label.vialidad.nombre" />
								</form:label> 
								<form:input type="text" path="vialidadReferenciaPosterior.nombre" maxlength="255" size="50"/>
									
								</div>
								
								
								
								<div>
									<form:errors path="vialidadReferenciaPosterior.tipoVialidad.clave" cssClass="error" />
								</div>
								
								<div id="vialidadReferenciaPosterior">
								<form:label path="vialidadReferenciaPosterior.nombre">
										<spring:message code="label.vialidad.tipo" />
								</form:label> 
								<combo:creaCombo idHtml="vialidadReferenciaPosterior.tipoVialidad.clave"
										idHtmlContenedor="vialidadReferenciaPosterior"
										entidad="mx.gob.imss.ctirss.delta.persistence.DgCatVialidad" 
										mostrarSoloActivos = "false" />
								</div>
								
							</fieldset>
				</div>
							</div>
						
								
							
								
														
							
							
							
							<fieldset class="fsInterno">
								<input type="submit" value="<spring:message code="label.btn.ubicar" />" class="mboton"  style="float: right;"/>	
							</fieldset>
						</fieldset>
					</form:form>
					</div>
				
				
				
			</div>
		</div>
		
		
	</div>
</div>

		
		
		
		
		