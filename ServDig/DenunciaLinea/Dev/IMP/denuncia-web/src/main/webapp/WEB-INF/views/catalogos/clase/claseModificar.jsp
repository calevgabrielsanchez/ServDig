<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="dgClaseModificar" title=" Modificar Elemento" style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">


	<div id="wrapperDialogModif" style="background-color: #f2fff2;">

		<form:form modelAttribute="clase" action="/catalogo/clase/modificar.do"
			method="post" id="claseFormModificar">
			<fieldset>
				<legend>Datos del cat&aacute;logo a modificar:</legend>
				<p class="impar">
					<form:label id="cveIdClaseIniLabel" for="cveIdClase" path="cveIdClase"
						cssErrorClass="error">Clave Clase</form:label>
					<br />
					<form:input path="cveIdClase" readonly="true"/>
					<form:errors path="cveIdClase" />
				</p>				
				<p class="par">
					<form:label id="desClaseLabel" for="desClase" path="desClase"
						cssErrorClass="error">Descripci&oacute;n</form:label>
					<br />
					<form:input path="desClase" size="60" maxlength="50" />
					<form:errors path="desClase" />
				</p>
				<p class="impar">
					<form:label id="fecIniLabel" for="fecIni" path="fecIni"
						cssErrorClass="error">Fecha de Inicio</form:label>
					<br />
					<form:input path="fecIni" />
					<form:errors path="fecIni" />
				</p>
				<p class="par">
					<form:label id="fecFinLabel" for="fecFin" path="fecFin"
						cssErrorClass="error">Fecha de Termino</form:label>
					<br />
					<form:input path="fecFin" />
					<form:errors path="fecFin" />
				</p>
				<p class="impar">
					<form:label id="indPrimaMediaLabel" for="indPrimaMedia" path="indPrimaMedia"
						cssErrorClass="error">Prima media</form:label>
					<br />
					<form:input path="indPrimaMedia" size="20" maxlength="10"/>
					<form:errors path="indPrimaMedia" />
				</p>
				<p class="par">
					<form:label id="numGradoRiesgoLabel" for="numGradoRiesgo" path="numGradoRiesgo"
						cssErrorClass="error">N&uacute;mero de riesgo</form:label>
					<br />
					<form:input path="numGradoRiesgo" size="10" maxlength="3"/>
					<form:errors path="numGradoRiesgo" />
				</p>
				<p class="impar">
					<form:label id="numPorcentajeLabel" for="numPorcentaje" path="numPorcentaje"
						cssErrorClass="error">N&uacute;mero de porcentaje (%)</form:label>
					<br />
					<form:input path="numPorcentaje" size="10" maxlength="10"/>
					<form:errors path="numPorcentaje" />
				</p>
			</fieldset>
		</form:form>

	</div>
</div>