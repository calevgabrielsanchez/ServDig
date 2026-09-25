<%@ include file="../general/taglibs.jsp"%>



<div>
			<h3>Telefono movil</h3>
			<p style="font-size: .9em;">Indique los datos de su telefono
				movil.</p>
		</div>
		<br />
		<div id="telefonomovil">
			<form:form
				action="${contextpath}/medios/contacto/telefono/movil/guardar"
				modelAttribute="telefonoMovil" method="POST"
				id="form"> 

				<fieldset style="margin: 20px !important;">

					<legend>
						<strong>Telefono movil</strong>
					</legend>

					<!-- Campo del numero -->
					<fieldset class="fsInterno">
						<span id="errorNegocioLabel" class="error hiddenElement"></span>
						<div>
							<form:errors path="numero" cssClass="error" />
						</div>
						<div>
							<form:label path="numero">
								<spring:message code="label.telefono.numero" />
							</form:label>
							<form:input type="text" path="numero" maxlength="15" />
						</div>
					</fieldset>
				</fieldset>
			 </form:form> 
	
		</div>