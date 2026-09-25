<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>


<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/domicilios/domicilios.js" htmlEscape="true" />"></script>
	





<script>

$(document).ready(
		function() {

$('div#controles form#form').submit(function(){
	var url = context_path + "/domicilio/nacional/ubicar";
	var objDomicilio =  window.showModalDialog(url , null, "dialogWidth:900px;dialogHeight:500px;status=yes,toolbar=no,menubar=no,location=no");
	alert(objDomicilio);
	return false;
});



		});




</script>

<div class="page_holder">
	<div class=" contenedor" style="width: 100% !important">
	<div class="row">

		<div class="cell" >
			
			<div class="row" id="domicilio" style="width: 1000px;" >
				<div class="cell form-comment" id="captura" style=" float:right;  width:100% !important; height: 100% !important; ">
				
					<div id="wrapperIntsAnterior" class="ui-widget"
											style="width: 450px !important;" align="center">
											<div class="ui-state-highlight ui-corner-all"
												style="margin-top: 20px; padding: 0 .5em;">
												<p>
													<span class="ui-icon ui-icon-info"
														style="float: left; margin-right: .3em;"></span> <strong>Para iniciar:</strong> capture su Codigo Postal, Asentamiento y Calle 
												</p>
											</div>
										</div>
				
					<form:form modelAttribute="domicilio" id="form">
						<fieldset style="margin: 20px !important;">
						
							<legend>
								<strong>Datos de su domicilio</strong>
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
								<form:input type="text" path="codigoPostal.codigoPostal" maxlength="8" readonly="true" cssClass="deshabilitado" />
								</div>
							</fieldset>
							
							<!-- Entidad Federativa -->
							<fieldset class="fsInterno">
					
							<div>
								<form:label path="asentamiento.localidad.municipio.entidadFederativa.clave">
										<spring:message code="label.entidadFederativa" />
								</form:label>
								<form:input path="asentamiento.localidad.municipio.entidadFederativa.nombre" readonly="true" cssClass="deshabilitado" />
							</div>
							</fieldset>
															
							<!-- Municipio -->
							<fieldset class="fsInterno">
								
								<div>
								<form:label path="asentamiento.localidad.municipio.nombre" >
									<spring:message code="label.municipio"/>
								</form:label>
								<form:input path="asentamiento.localidad.municipio.nombre" readonly="true" cssClass="deshabilitado" />
								</div>
							</fieldset>	
							
							<!-- Localidad -->
							<fieldset class="fsInterno">
								
								<div>
								<form:label path="asentamiento.localidad.nombre" >
									<spring:message code="label.localidad"/>
								</form:label>
								<form:input path="asentamiento.localidad.nombre" readonly="true" cssClass="deshabilitado"/>
								</div>
							</fieldset>
							
							
							<!-- Asentamiento-->
							<fieldset class="fsInterno">
								
								<div>
								<form:label path="asentamiento.nombre" >
									<spring:message code="label.asentamiento"/>
								</form:label>
								<form:input path="asentamiento.nombre" readonly="true" cssClass="deshabilitado"/>
								</div>
							</fieldset>
							
							
							<!-- Vialidad Primaria -->							
							<fieldset class="fsInterno">
								<div>
									<form:errors path="vialidadPrimaria.nombre" cssClass="error" />
								</div>
								<div>
								<form:label path="vialidadPrimaria.nombre">
										<spring:message code="label.vialidadPrimaria" />
								</form:label> 
								<form:input  path="vialidadPrimaria.nombre" maxlength="100" size="50" readonly="true" cssClass="deshabilitado"/>
								</div>
							</fieldset>
							<!-- Numero exterior principal -->							
							<fieldset class="fsInterno">
								<div>
									<form:errors path="numExterior1" cssClass="error" />
								</div>
								<div>
								<form:label path="numExterior1">
										<spring:message code="label.numExterior1" />
								</form:label> 
								<form:input  path="numExterior1" maxlength="10" readonly="true" cssClass="deshabilitado"/>
								</div>
							</fieldset>
							
								<!-- Numero exterior alfanumerico  -->							
							<fieldset class="fsInterno">
								<div>
									<form:errors path="numExteriorAlf" cssClass="error" />
								</div>
								<div>
								<form:label path="numExteriorAlf">
										<spring:message code="label.numExteriorAlf" />
								</form:label> 
								<form:input path="numExteriorAlf" maxlength="10" readonly="true" cssClass="deshabilitado"/>
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
								<form:input  path="numExterior2" maxlength="10" readonly="true" cssClass="deshabilitado"/>
								</div>
							</fieldset>
							
								<!-- Numero interior-->							
							<fieldset class="fsInterno">
								<div>
									<form:errors path="numInterior" cssClass="error" />
								</div>
								<div>
								<form:label path="numInterior">
										<spring:message code="label.numInterior" />
								</form:label> 
								<form:input path="numInterior" maxlength="10" readonly="true" cssClass="deshabilitado"/>
								</div>
							</fieldset>
							
								<!-- Numero interior alfanumerico-->							
							<fieldset class="fsInterno">
								<div>
									<form:errors path="numInteriorAlf" cssClass="error" />
								</div>
								<div>
								<form:label path="numInteriorAlf">
										<spring:message code="label.numInteriorAlf" />
								</form:label> 
								<form:input  path="numInteriorAlf" maxlength="10" readonly="true" cssClass="deshabilitado"/>
								</div>
							</fieldset>
							
								<!-- Vialidad referencia primaria-->							
							<fieldset class="fsInterno">
								<div>
									<form:errors path="vialidadReferenciaPrimaria.nombre" cssClass="error" />
								</div>
								<div>
								<form:label path="vialidadReferenciaPrimaria.nombre">
										<spring:message code="label.vialidadReferenciaPrimaria" />
								</form:label> 
								<form:input  path="vialidadReferenciaPrimaria.nombre" maxlength="100" size="50" readonly="true" cssClass="deshabilitado"/>
								</div>
							</fieldset>
							
								<!-- Vialidad referencia secundaria-->							
							<fieldset class="fsInterno">
								<div>
									<form:errors path="vialidadReferenciaSecundaria.nombre" cssClass="error" />
								</div>
								<div>
								<form:label path="vialidadReferenciaSecundaria.nombre">
										<spring:message code="label.vialidadReferenciaSecundaria" />
								</form:label> 
								<form:input path="vialidadReferenciaSecundaria.nombre" maxlength="100" size="50" readonly="true" cssClass="deshabilitado"/>
								</div>
							</fieldset>
							
								<!-- Vialidad referencia posterior-->							
							<fieldset class="fsInterno">
								<div>
									<form:errors path="vialidadReferenciaPosterior.nombre" cssClass="error" />
								</div>
								<div>
								<form:label path="vialidadReferenciaPosterior.nombre">
										<spring:message code="label.vialidadReferenciaPosterior" />
								</form:label> 
								<form:input  path="vialidadReferenciaPosterior.nombre" maxlength="100" size="50" readonly="true" cssClass="deshabilitado"/>
								</div>
							</fieldset>	
							
							
						</fieldset>
					</form:form>
					
				</div>
				
				
				<div id="controles">
					<form id="form">
						<input type="submit" value="Ubicar" class="mboton" />
					
					</form>
				
				</div>
				
			</div>
			
		

		</div>

		
		</div>

	</div>
	
	
</div>
<!-- 
style="float: left; width:500px !important; height: 700px !important; "
 style="width:500px !important; height: 700px !important; "-->

			