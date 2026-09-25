<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="dgDivisionModificar"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">


	<div id="wrapperDialogModif" style="background-color: #f2fff2;">

		<form:form modelAttribute="division" action="/catalogo/division/modificar.do"
			method="post" id="divisionFormModificar">
			<fieldset>
				<legend>Datos del cat&aacute;logo a modificar:</legend>
				<p class="impar">
					<form:label id="cveIdDivisionIniLabel" for="cveIdDivision" path="cveIdDivision"
						cssErrorClass="error">Clave Division</form:label>
					<br />
					<form:input path="cveIdDivision" readonly="true"/>
					<form:errors path="cveIdDivision" />
				</p>				
				<p class="par">
					<form:label id="desDivisionLabel" for="desDivision" path="desDivision"
						cssErrorClass="error">Descripci&oacute;n</form:label>
					<br />
					<form:input path="desDivision" size="60" maxlength="50" />
					<form:errors path="desDivision" />
				</p>
				<p class="impar">
					<form:label id="numDivisionLabel" for="numDivision" path="numDivision"
						cssErrorClass="error">Número Division</form:label>
					<br />
					<form:input path="numDivision" />
					<form:errors path="numDivision" />
				</p>
				<p class="impar">
					<form:label id="fecRegistroAltaLabel" for="fecRegistroAlta" path="fecRegistroAlta"
						cssErrorClass="error">Fecha Regstro Alta</form:label>
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