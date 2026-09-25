
<%@ include file="../general/taglibs.jsp"%>



<div >
	
	
	
		<div>
			<h3>Telefono particular</h3>
			<p style="font-size: .9em;">Indique los datos de su telefono
				particular.</p>
		</div>
		<br />
	
	
	<div id="telefonofijo">
	<form:form
				action="${contextpath}/medios/contacto/telefono/fijo/guardar"
				method="POST" modelAttribute="telefonoFijo"
				id="form">
	
		<fieldset style="margin: 20px !important;">
					<legend>
						<strong>Telefono particular</strong><span></span>
					</legend>

					<!-- Campo del numero -->
					<fieldset class="fsInterno">
						<span id="errorNegocioLabel" class="error hiddenElement"></span>
						<div>
							<form:errors path="numero" cssClass="error" />
						</div>
						<div>
							<span></span>
							<form:label path="numero">
								<spring:message code="label.telefono.numero" />

							</form:label>
							<form:input type="text" path="numero" maxlength="15" />
						</div>
					</fieldset>

					<!-- Campo de clave de larga distancia -->
					<fieldset class="fsInterno">
						<div>
							<form:errors path="claveLada" cssClass="error" />
						</div>
						<div>
							<form:label path="claveLada">
								<spring:message code="label.telefono.claveLada" />
							</form:label>

							<form:input type="text" path="claveLada" maxlength="6" />
						</div>
					</fieldset>

					<!-- Campo de extension -->
					<fieldset class="fsInterno">
						<div>
							<form:errors path="extension" cssClass="error" />
						</div>
						<div>
							<form:label path="extension">
								<spring:message code="label.telefono.extension" />
							</form:label>

							<form:input type="text" path="extension" maxlength="4" />
						</div>
					</fieldset>
					
					<fieldset class="fsInterno">
						<input type="submit" value="<spring:message code="label.btn.guardar" />" class="mboton"  style="float: right;"/>	
					</fieldset>
					
	</fieldset>
	</form:form>
	</div>

</div>