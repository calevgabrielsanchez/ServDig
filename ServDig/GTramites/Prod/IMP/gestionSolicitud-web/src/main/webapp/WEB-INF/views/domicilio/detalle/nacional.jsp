
<%@ include file="../../general/taglibs.jsp"%>



	


			
				<div class="cell">
				<fieldset>
					<legend> Direcci&oacute;n:</legend>
					
					<div>
						<p><span class="etiqueta"> Vialidad :</span> <span class="dato">${domicilio.vialidadPrimaria.nombre} </span></p>
						<p><span class="etiqueta"> Numero / Letra exterior :</span> <span class="dato">${domicilio.numExterior1} ,${domicilio.numExteriorAlf}, ${domicilio.numExterior2 }</span></p>
						<p><span class="etiqueta"> Numero / Letra interior :</span> <span class="dato">${domicilio.numInterior }, ${domicilio.numInteriorAlf } </span></p>
						<p><span class="etiqueta"> Codigo postal :</span> <span class="dato">${domicilio.codigoPostal.codigoPostal } </span></p>
						<p><span class="etiqueta"> Localidad / Asentamiento :</span> <span class="dato">${domicilio.asentamiento.localidad.nombre} </span></p>
						<p><span class="etiqueta"> Municipio :</span> <span class="dato">${domicilio.asentamiento.localidad.municipio.nombre} </span></p>
						<p><span class="etiqueta"> Estado :</span> <span class="dato">${domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre} </span></p>
					</div>
				</fieldset>
			</div>
				
			
			
			
			
			

<!-- 				<fieldset style="margin: 20px !important;"> -->
<!-- 					<legend> -->
<!-- 						<strong>Datos del asentamiento</strong> -->
<!-- 					</legend> -->

<!-- 					Campo del codigo postal -->
<!-- 					<fieldset class="fsInterno"> -->
<!-- 						<span id="errorNegocioLabel" class="error hiddenElement"></span> -->
<!-- 						<div> -->
<%-- 							<form:errors path="codigoPostal.codigoPostal" cssClass="error" /> --%>
<!-- 						</div> -->
<!-- 						<div> -->
<%-- 							<form:label path="codigoPostal.codigoPostal"> --%>
<%-- 								<spring:message code="label.codigoPostal" /> --%>
<%-- 							</form:label> --%>
<%-- 							<form:input type="text" path="codigoPostal.codigoPostal" --%>
<%-- 								maxlength="8" readonly="true" cssClass="deshabilitado" /> --%>
<!-- 						</div> -->
<!-- 					</fieldset> -->

<!-- 					Entidad Federativa -->
<!-- 					<fieldset class="fsInterno"> -->

<!-- 						<div> -->
<%-- 							<form:label --%>
<%-- 								path="asentamiento.localidad.municipio.entidadFederativa.clave"> --%>
<%-- 								<spring:message code="label.entidadFederativa" /> --%>
<%-- 							</form:label> --%>
<%-- 							<form:input --%>
<%-- 								path="asentamiento.localidad.municipio.entidadFederativa.nombre" --%>
<%-- 								readonly="true" cssClass="deshabilitado" /> --%>
<%-- 							<form:input --%>
<%-- 								path="asentamiento.localidad.municipio.entidadFederativa.clave" --%>
<%-- 								readonly="true" cssClass="hidden" /> --%>
<!-- 						</div> -->
<!-- 					</fieldset> -->

<!-- 					Municipio -->
<!-- 					<fieldset class="fsInterno"> -->

<!-- 						<div> -->
<%-- 							<form:label path="asentamiento.localidad.municipio.nombre"> --%>
<%-- 								<spring:message code="label.municipio" /> --%>
<%-- 							</form:label> --%>
<%-- 							<form:input path="asentamiento.localidad.municipio.nombre" --%>
<%-- 								readonly="true" cssClass="deshabilitado" /> --%>
<%-- 							<form:input path="asentamiento.localidad.municipio.clave" --%>
<%-- 								readonly="true" cssClass="hidden" /> --%>
<!-- 						</div> -->
<!-- 					</fieldset> -->

<!-- 					Localidad -->
<!-- 					<fieldset class="fsInterno"> -->

<!-- 						<div> -->
<%-- 							<form:label path="asentamiento.localidad.nombre"> --%>
<%-- 								<spring:message code="label.localidad" /> --%>
<%-- 							</form:label> --%>
<%-- 							<form:input path="asentamiento.localidad.nombre" readonly="true" --%>
<%-- 								cssClass="deshabilitado" /> --%>
<%-- 							<form:input path="asentamiento.localidad.clave" readonly="true" --%>
<%-- 								cssClass="hidden" /> --%>
<!-- 						</div> -->
<!-- 					</fieldset> -->


<!-- 					Asentamiento -->
<!-- 					<fieldset class="fsInterno"> -->

<!-- 						<div> -->
<%-- 							<form:label path="asentamiento.nombre"> --%>
<%-- 								<spring:message code="label.asentamiento" /> --%>
<%-- 							</form:label> --%>
<%-- 							<form:input path="asentamiento.nombre" readonly="true" --%>
<%-- 								cssClass="deshabilitado" /> --%>
<%-- 							<form:input path="asentamiento.clave" readonly="true" --%>
<%-- 								cssClass="hidden" /> --%>
<!-- 						</div> -->
<!-- 					</fieldset> -->

<!-- 				</fieldset> -->


<!-- 				<fieldset style="margin: 20px !important;"> -->
<!-- 					<legend> -->
<!-- 						<strong>Datos complementarios</strong> -->
<!-- 					</legend> -->


<!-- 					Vialidad Primaria -->
<!-- 					<fieldset class="fsInterno"> -->
<!-- 						<div> -->
<%-- 							<form:errors path="vialidadPrimaria.nombre" cssClass="error" /> --%>
<!-- 						</div> -->
<!-- 						<div> -->
<%-- 							<form:label path="vialidadPrimaria.nombre"> --%>
<%-- 								<spring:message code="label.vialidadPrimaria" /> --%>
<%-- 							</form:label> --%>
<%-- 							<form:input path="vialidadPrimaria.nombre" maxlength="100" --%>
<%-- 								size="50" readonly="true" cssClass="deshabilitado" /> --%>
<!-- 						</div> -->
<!-- 					</fieldset> -->
<!-- 					Numero exterior principal -->
<!-- 					<fieldset class="fsInterno"> -->
<!-- 						<div> -->
<%-- 							<form:errors path="numExterior1" cssClass="error" /> --%>
<!-- 						</div> -->
<!-- 						<div> -->
<%-- 							<form:label path="numExterior1"> --%>
<%-- 								<spring:message code="label.numExterior1" /> --%>
<%-- 							</form:label> --%>
<%-- 							<form:input path="numExterior1" maxlength="10" readonly="true" --%>
<%-- 								cssClass="deshabilitado" /> --%>
<!-- 						</div> -->
<!-- 					</fieldset> -->

<!-- 					Numero exterior alfanumerico  -->
<!-- 					<fieldset class="fsInterno"> -->
<!-- 						<div> -->
<%-- 							<form:errors path="numExteriorAlf" cssClass="error" /> --%>
<!-- 						</div> -->
<!-- 						<div> -->
<%-- 							<form:label path="numExteriorAlf"> --%>
<%-- 								<spring:message code="label.numExteriorAlf" /> --%>
<%-- 							</form:label> --%>
<%-- 							<form:input path="numExteriorAlf" maxlength="10" readonly="true" --%>
<%-- 								cssClass="deshabilitado" /> --%>
<!-- 						</div> -->
<!-- 					</fieldset> -->


<!-- 					Numero exterior secundario  -->
<!-- 					<fieldset class="fsInterno"> -->
<!-- 						<div> -->
<%-- 							<form:errors path="numExterior2" cssClass="error" /> --%>
<!-- 						</div> -->
<!-- 						<div> -->
<%-- 							<form:label path="numExterior2"> --%>
<%-- 								<spring:message code="label.numExterior2" /> --%>
<%-- 							</form:label> --%>
<%-- 							<form:input path="numExterior2" maxlength="10" readonly="true" --%>
<%-- 								cssClass="deshabilitado" /> --%>
<!-- 						</div> -->
<!-- 					</fieldset> -->

<!-- 					Numero interior -->
<!-- 					<fieldset class="fsInterno"> -->
<!-- 						<div> -->
<%-- 							<form:errors path="numInterior" cssClass="error" /> --%>
<!-- 						</div> -->
<!-- 						<div> -->
<%-- 							<form:label path="numInterior"> --%>
<%-- 								<spring:message code="label.numInterior" /> --%>
<%-- 							</form:label> --%>
<%-- 							<form:input path="numInterior" maxlength="10" readonly="true" --%>
<%-- 								cssClass="deshabilitado" /> --%>
<!-- 						</div> -->
<!-- 					</fieldset> -->

<!-- 					Numero interior alfanumerico -->
<!-- 					<fieldset class="fsInterno"> -->
<!-- 						<div> -->
<%-- 							<form:errors path="numInteriorAlf" cssClass="error" /> --%>
<!-- 						</div> -->
<!-- 						<div> -->
<%-- 							<form:label path="numInteriorAlf"> --%>
<%-- 								<spring:message code="label.numInteriorAlf" /> --%>
<%-- 							</form:label> --%>
<%-- 							<form:input path="numInteriorAlf" maxlength="10" readonly="true" --%>
<%-- 								cssClass="deshabilitado" /> --%>
<!-- 						</div> -->
<!-- 					</fieldset> -->

<!-- 					Vialidad referencia primaria -->
<!-- 					<fieldset class="fsInterno"> -->
<!-- 						<div> -->
<%-- 							<form:errors path="vialidadReferenciaPrimaria.nombre" --%>
<%-- 								cssClass="error" /> --%>
<!-- 						</div> -->
<!-- 						<div> -->
<%-- 							<form:label path="vialidadReferenciaPrimaria.nombre"> --%>
<%-- 								<spring:message code="label.vialidadReferenciaPrimaria" /> --%>
<%-- 							</form:label> --%>
<%-- 							<form:input path="vialidadReferenciaPrimaria.nombre" --%>
<%-- 								maxlength="100" size="50" readonly="true" --%>
<%-- 								cssClass="deshabilitado" /> --%>
<!-- 						</div> -->
<!-- 					</fieldset> -->

<!-- 					Vialidad referencia secundaria -->
<!-- 					<fieldset class="fsInterno"> -->
<!-- 						<div> -->
<%-- 							<form:errors path="vialidadReferenciaSecundaria.nombre" --%>
<%-- 								cssClass="error" /> --%>
<!-- 						</div> -->
<!-- 						<div> -->
<%-- 							<form:label path="vialidadReferenciaSecundaria.nombre"> --%>
<%-- 								<spring:message code="label.vialidadReferenciaSecundaria" /> --%>
<%-- 							</form:label> --%>
<%-- 							<form:input path="vialidadReferenciaSecundaria.nombre" --%>
<%-- 								maxlength="100" size="50" readonly="true" --%>
<%-- 								cssClass="deshabilitado" /> --%>
<!-- 						</div> -->
<!-- 					</fieldset> -->

<!-- 					Vialidad referencia posterior -->
<!-- 					<fieldset class="fsInterno"> -->
<!-- 						<div> -->
<%-- 							<form:errors path="vialidadReferenciaPosterior.nombre" --%>
<%-- 								cssClass="error" /> --%>
<!-- 						</div> -->
<!-- 						<div> -->
<%-- 							<form:label path="vialidadReferenciaPosterior.nombre"> --%>
<%-- 								<spring:message code="label.vialidadReferenciaPosterior" /> --%>
<%-- 							</form:label> --%>
<%-- 							<form:input path="vialidadReferenciaPosterior.nombre" --%>
<%-- 								maxlength="100" size="50" readonly="true" --%>
<%-- 								cssClass="deshabilitado" /> --%>
<!-- 						</div> -->
<!-- 					</fieldset> -->



<!-- 				</fieldset> -->


