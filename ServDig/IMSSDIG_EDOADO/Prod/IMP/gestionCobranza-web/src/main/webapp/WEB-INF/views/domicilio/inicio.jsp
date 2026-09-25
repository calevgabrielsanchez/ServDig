<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>


<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/domicilios/domicilios.js" htmlEscape="true" />"></script>
	
<div class="page_holder">
	<div class=" contenedor" >
		<div class="row" >
			<div class="cell">
			
			</br>
			
			<div class="row"  >
				<div class="row" >
					<h2> Ubicar domicilio geogr&aacute;fico nacional </h2>
					<h3 style="font-size: .9em;color: #666666"> Paso 1 / 2 </h3>
				</div>
			</div>
			</br>
				<div class="row" >
				
				
					<c:set var="contextpath" value="<%=request.getContextPath()    %>" />
					<div class="cell " id="codigoPostal" style=" float:left;  width:50% !important; height: 500px !important; ">
						
						<div>
						<h3> Por codigo postal</h3>
						<p style="font-size: .9em;">
							Si usted tiene y conoce su codigo postal puede ubicar su domicilio
							a traves del mismo.
						</p>
						</div>
						
						<div>
							
							<form:form action="${contextpath}/domicilio/nacional/ubicar/porCodigoPostal" method="POST" modelAttribute="domicilio" id="formCodigoPostal">
							
							<fieldset style="margin: 20px !important;">
							<legend>
								<strong>Codigo postal de su domicilio</strong>
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
								<form:input type="text" path="codigoPostal.codigoPostal" maxlength="8" />
								</div>
							</fieldset>
							
							
							
							
							<!-- Campo de asentamiento -->
							<fieldset class="fsInterno">
							<div>
								<form:errors path="asentamiento.clave" cssClass="error" />
							</div>
							<div>
								<form:label path="asentamiento.clave">
										<spring:message code="label.asentamiento" />
								</form:label>
								<select id="asentamiento.clave" name="asentamiento.clave"  >
								<option>-- Seleccione --</option>
								</select>
							</div>
							</fieldset>
							
							<fieldset class="fsInterno">
								<input type="submit" value="<spring:message code="label.btn.ubicar" />" class="mboton"  style="float: right;"/>	
							</fieldset>
							
							
						</fieldset>
						
						<!--  Dato hidden de la localidad -->
						<form:hidden path="asentamiento.localidad.clave"/>
						<form:hidden path="asentamiento.localidad.municipio.clave"/>
						<form:hidden path="asentamiento.localidad.municipio.entidadFederativa.clave"/>
						
					</form:form>
						
						</div>
						
						
					</div>
					
					<div class="cell" id="municipio" style="  float:right; width:50% !important; height: 500px !important; ">
						
						<div>
						<h3>Por municipio</h3>
						<p style="font-size: .9em;">Si usted no tiene o no conoce su codigo postal, podr&aacute; ubicar su domicilio a traves del municipio</p>
						</div>
						<div>
							<form:form action="${contextpath}/domicilio/nacional/ubicar/porMunicipio" modelAttribute="asentamiento" method="POST" id="formMunicipio">
							
							<fieldset style="margin: 20px !important;">
							
							<legend>
								<strong>Municipio de su domicilio</strong>
							</legend>
							
							<!-- Entidad Federativa -->
							<fieldset class="fsInterno">
							<div>
								<form:errors path="localidad.municipio.entidadFederativa.clave" cssClass="error" />
							</div>
							<div>
								<form:label path="localidad.municipio.entidadFederativa.clave">
										<spring:message code="label.entidadFederativa" />
								</form:label>
								<combo:creaCombo idHtml="localidad.municipio.entidadFederativa.clave" idHtmlContenedor="formMunicipio" 
								entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado"
								mostrarSoloActivos = "false" />
							</div>
							</fieldset>
															
							<!-- Municipio -->
							<fieldset class="fsInterno">
								<div>
								<form:errors path="localidad.municipio.clave" cssClass="error"/>
								</div>
								<div>
								<form:label path="localidad.municipio.clave" >
									<spring:message code="label.municipio"/>
								</form:label>
								
								<combo:creaCombo 		entidad			="mx.gob.imss.ctirss.delta.persistence.DgCatMunicipio" 
		                     					idHtml			="localidad.municipio.clave" 
		                     				  	entidadPadre	="dgCatEstado.cveEnt"
		                     				  	idHtmlPadre		="localidad.municipio.entidadFederativa.clave"
		                     				  	idHtmlContenedor="formMunicipio"
		                     				  	mostrarSoloActivos = "false" />   
								</div>
							</fieldset>
							
							
						
							
							
							<!-- Campo de asentamiento -->
							<fieldset class="fsInterno">
							<div>
								<form:errors path="clave" cssClass="error" />
							</div>
							<div>
								<form:label path="clave">
										<spring:message code="label.asentamiento" />
								</form:label>
								<select id="clave" name="clave"  >
								<option>-- Seleccione --</option>
								</select>
							</div>
							</fieldset>
						
							
							<fieldset class="fsInterno">
							<input type="submit" value="<spring:message code="label.btn.ubicar" />" class="mboton"  style="float: right;"/>							
							</fieldset>
						</fieldset>
						
						
						<form:hidden path="localidad.clave"/>
						
					</form:form>
						
						</div>
					
					
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

		