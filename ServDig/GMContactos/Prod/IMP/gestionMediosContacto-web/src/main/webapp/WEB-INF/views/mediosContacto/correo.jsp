
<%@ include file="../general/taglibs.jsp"%>



<div>
			<h3>Correo electronico</h3>
			<p style="font-size: .9em;">Indique los datos de cuenta de correo
				electronico.</p>
		</div>
		<br />
		<div id="correo">
			 <form:form
				action="${contextpath}/medios/contacto/correo/electronico/guardar"
				modelAttribute="correoElectronico"
				method="POST" id="form">

				<fieldset style="margin: 20px !important;">

					<legend>
						<strong>Correo electronico</strong>
					</legend>

					<!-- Correo Electronico -->
					<fieldset class="fsInterno">
						<div>
							<form:errors path="correo" cssClass="error" />
						</div>
						<div>
							<form:label path="correo">
								<spring:message code="label.correoElectronico" />
							</form:label>

							<form:input type="text" path="correo" maxlength="30" />

						</div>
					</fieldset>
				</fieldset>
			</form:form> 
		</div>