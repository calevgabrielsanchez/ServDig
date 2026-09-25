
<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>




<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/mediosContacto/control.js?" htmlEscape="true" />"></script>

<div id="mediosContacto" class="form-comment">
	
	<c:set var="contextpath" value="<%=request.getContextPath()    %>" />
	<form:form modelAttribute="medioContactoFormWrapper" method="POST" id="forma" action="${contextpath}/medios/contacto/guardarMedios">
	
		<div id="telefonoFijo">

				<fieldset style="margin: 20px !important;">
					<legend>
						<strong>Telefono particular</strong><span></span>
					</legend>

					<!-- Campo del numero -->
					<fieldset class="fsInterno">
						<span id="errorNegocioLabel" class="error hiddenElement"></span>
						<div>
							<form:errors path="telefonoFijo.numero" cssClass="error" />
							<span id="telefonoFijo.numero" class=" hiddenElement error"></span>
						</div>
						<div>
							<span></span>
							<form:label path="telefonoFijo.numero">
								<spring:message code="label.telefono.numero" />

							</form:label>
							<form:input type="text" path="telefonoFijo.numero"  maxlength="15" cssClass="numerico"/>
						</div>
					</fieldset>

					<!-- Campo de clave de larga distancia -->
					<fieldset class="fsInterno">
						<div>
							<form:errors path="telefonoFijo.claveLada" cssClass="error" />
							<span id="telefonoFijo.claveLada" class=" hiddenElement error"></span>
						</div>
						<div>
							<form:label path="telefonoFijo.claveLada">
								<spring:message code="label.telefono.claveLada" />
							</form:label>

							<form:input type="text" path="telefonoFijo.claveLada" maxlength="4" cssClass="numerico" />
						</div>
					</fieldset>

					<!-- Campo de extension -->
					<fieldset class="fsInterno">
						<div>
							<form:errors path="telefonoFijo.extension" cssClass="error" />
							<span id="telefonoFijo.extension" class=" hiddenElement error"></span>
						</div>
						<div>
							<form:label path="telefonoFijo.extension">
								<spring:message code="label.telefono.extension" />
							</form:label>

							<form:input type="text" path="telefonoFijo.extension" maxlength="4"  cssClass="numerico"/>
						</div>
					</fieldset>

				</fieldset>

		</div>
		
		<div id="correoElectronico">

				<fieldset style="margin: 20px !important;">

					<legend>
						<strong>Correo electronico</strong>
					</legend>

					<!-- Correo Electronico -->
					<fieldset class="fsInterno">
						<div>
							<form:errors path="correoElectronico.correo" cssClass="error" />
							<span id="correoElectronico.correoError" class=" hiddenElement error"></span>
						</div>
						<div>
							<form:label path="correoElectronico.correo">
								<spring:message code="label.correoElectronico" />
							</form:label>

							<form:input type="text" path="correoElectronico.correo" maxlength="30" />

						</div>
					</fieldset>
				</fieldset>
		</div>
		
		<div id="telefonoMovil">

				<fieldset style="margin: 20px !important;">

					<legend>
						<strong>Telefono movil</strong>
					</legend>

					<!-- Campo del numero -->
					<fieldset class="fsInterno">
						<span id="errorNegocioLabel" class="error hiddenElement"></span>
						<div>
							<form:errors path="telefonoMovil.numero" cssClass="error" />
						</div>
						<div>
							<form:label path="telefonoMovil.numero">
								<spring:message code="label.telefono.numero" />
							</form:label>
							<form:input type="text" path="telefonoMovil.numero" maxlength="15" cssClass="numerico"/>
						</div>
					</fieldset>
				</fieldset>
		</div>
		
		
		<div id="facebook">

				<fieldset style="margin: 20px !important;">

					<legend>
						<strong>Facebook</strong>
					</legend>

					<!-- Cuenta de Facebook -->
					<fieldset class="fsInterno">
						<div>
							<form:errors path="facebook.cuenta" cssClass="error" />
						</div>
						<div>
							<form:label path="facebook.cuenta">
								<spring:message code="label.facebook" />
							</form:label>

							<form:input type="text" path="facebook.cuenta" maxlength="30" />

						</div>
					</fieldset>
				</fieldset>
		</div>
		
		<div id="twitter">

				<fieldset style="margin: 20px !important;">

					<legend>
						<strong>Twitter</strong>
					</legend>

					<!-- Cuenta de Facebook -->
					<fieldset class="fsInterno">
						<div>
							<form:errors path="twitter.cuenta" cssClass="error" />
						</div>
						<div>
							<form:label path="twitter.cuenta">
								<spring:message code="label.twitter" />
							</form:label>

							<form:input type="text" path="twitter.cuenta" maxlength="30" />

						</div>
					</fieldset>
				</fieldset>
		</div>
	
		<div>
		
			<input type="submit" value="Guardar" class="mboton" />
		</div>
	
	</form:form>


</div>