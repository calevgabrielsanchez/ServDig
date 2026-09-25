<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>


<div class="row" id="medios">

	<div class="cell"
		style="float: left; width: 33% !important; height: 500px !important;">

		<div>
			<h3>Telefono particular</h3>
			<p style="font-size: .9em;">Indique los datos de su telefono
				particular.</p>
		</div>
		<br />
		<div id="telefonoFijo">

			<%-- <form:form
				action="${contextpath}/medios/contacto/telefonoFijo/guardar"
				method="POST" modelAttribute="telefonoFijo"
				id="form"> --%>

				<fieldset style="margin: 20px !important;">
					<legend>
						<strong>Telefono particular</strong><span></span>
					</legend>

					<!-- Campo del numero -->
					<fieldset class="fsInterno">
						<span id="errorNegocioLabel" class="error hiddenElement"></span>
						<div>
							<form:errors path="telefonoFijo.numero" cssClass="error" />
						</div>
						<div>
							<span></span>
							<form:label path="telefonoFijo.numero">
								<spring:message code="label.telefono.numero" />

							</form:label>
							<form:input type="text" path="telefonoFijo.numero" maxlength="15" cssClass="numerico"/>
						</div>
					</fieldset>

					<!-- Campo de clave de larga distancia -->
					<fieldset class="fsInterno">
						<div>
							<form:errors path="telefonoFijo.claveLada" cssClass="error" />
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
						</div>
						<div>
							<form:label path="telefonoFijo.extension">
								<spring:message code="label.telefono.extension" />
							</form:label>

							<form:input type="text" path="telefonoFijo.extension" maxlength="6"  cssClass="numerico"/>
						</div>
					</fieldset>

					<!-- <fieldset class="fsInterno"> -->
					<%-- <input type="submit" value="<spring:message code="label.btn.guardar" />" class="mboton"  style="float: right;"/>	 --%>
					<!-- </fieldset> -->


				</fieldset>
			<%-- </form:form> --%>

		</div>

	</div>

	<div class="cell"
		style="float: right; width: 33% !important; height: 500px !important;">

		<div>
			<h3>Correo electronico</h3>
			<p style="font-size: .9em;">Indique los datos de cuenta de correo
				electronico.</p>
		</div>
		<br />
		<div id="correoElectronico">
			<%-- <form:form
				action="${contextpath}/medios/contacto/correoElectronico/guardar"
				modelAttribute="correoElectronico"
				method="POST" id="form"> --%>

				<fieldset style="margin: 20px !important;">

					<legend>
						<strong>Correo electronico</strong>
					</legend>

					<!-- Correo Electronico -->
					<fieldset class="fsInterno">
						<div>
							<form:errors path="correoElectronico.correo" cssClass="error" />
						</div>
						<div>
							<form:label path="correoElectronico.correo">
								<spring:message code="label.correoElectronico" />
							</form:label>

							<form:input type="text" path="correoElectronico.correo" maxlength="30" />

						</div>
					</fieldset>
				</fieldset>
			<%-- </form:form> --%>
		</div>
	</div>

	<div class="cell"
		style="float: right; width: 33% !important; height: 500px !important;">
		<div>
			<h3>Telefono movil</h3>
			<p style="font-size: .9em;">Indique los datos de su telefono
				movil.</p>
		</div>
		<br />
		<div id="telefonoMovil">
			<%-- <form:form
				action="${contextpath}/medios/contacto/telefonoMovil/guardar"
				modelAttribute="telefonoMovil" method="POST"
				id="form"> --%>

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
			<%-- </form:form> --%>
		</div>
	</div>
</div>