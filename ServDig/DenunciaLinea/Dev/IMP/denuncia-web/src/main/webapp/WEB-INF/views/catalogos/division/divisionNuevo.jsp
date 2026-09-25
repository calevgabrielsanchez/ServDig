<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="dgDivisionNuevo" title=" Agregar Elemento"
	style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity = 70) !important;">


	<div id="wrapperDialog" style="background-color: #f2fff2;">



		<form:form modelAttribute="division" action="/catalogo/division/agregar.do" method="post" id="divisionForm">
			<fieldset>
				<legend>Capture los datos de la nueva division.</legend>
				<p class="par">
					<form:label id="desDivisionLabel" for="desDivision" path="desDivision"
						cssErrorClass="error">Descripci&oacute;n</form:label>
					<br />
					<form:input path="desDivision" size="60" maxlength="50" />
					<form:errors path="desDivision" />
				</p>
				<p class="impar">
					<form:label id="numDivisionLabel" for="numDivision" path="numDivision"
						cssErrorClass="error">N�mero Division</form:label>
					<br />
					<form:input path="numDivision" />
					<form:errors path="numDivision" />
				</p>
				<p class="impar">
					<form:label id="fecRegistroAltaLabel" for="fecRegistroAlta" path="fecRegistroAlta"
						cssErrorClass="error">Fecha Registro Alta</form:label>
					<br />
					<form:input path="fecRegistroAlta" />
					<form:errors path="fecRegistroAlta" />
				</p>
				<p class="par">
					<form:label id="fecRegistroBajaLabel" for="fecRegistroBaja" path="fecRegistroBaja"
						cssErrorClass="error">Fecha Registro Baja</form:label>
					<br />
					<form:input path="fecRegistroBaja" />
					<form:errors path="fecRegistroBaja" />
				</p>
			</fieldset>
		</form:form>





	</div>
</div>