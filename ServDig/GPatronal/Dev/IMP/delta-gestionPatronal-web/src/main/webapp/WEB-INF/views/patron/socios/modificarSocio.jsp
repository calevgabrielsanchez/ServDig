<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>





<div>


	<div id="modificar" style="float: left; width: 100% !important; padding-top: 10px;" class="page_holder dialogo_holder">
						

		<h2 style="font-size: 1.0em !important;">Agregar Socios</h2>

		<form:form modelAttribute="socio" id="socioFormModificar"
			action="/socios/modificar">
			<form:hidden path="cveIdPatronSujetoObligado" />

			<span id="errorNegocioLabel" class=" hiddenElement error"></span>

			<div id = "seccionTipoSocio">
			<!-- 	private Boolean esPersonaFisica; -->
			<form:radiobutton path="esPersonaFisica" name="tipoSocioNuevo" checked/>
			Persona Física
			<form:radiobutton path="esPersonaFisica" name="tipoSocioNuevo"/>
			Persona Moral
			
			</div>
			<div id=divPersonaFisica>
			
			<!-- 			private String curp; -->
			<fieldset>
			<form:errors path="curp" cssClass="error" />
			<form:label path="curp">
				<strong class="etiqueta">CURP:</strong>
			</form:label>
			<span id="curpError" class=" hiddenElement error"></span>
			<form:input type="text" path="curp" maxlength="300" size="50"
				cssStyle="padding:2px;" class="dato" />
			</fieldset>
			<fieldset>
			<!-- 	private String primerApellido; -->
			<form:errors path="primerApellido" cssClass="error" />
			<form:label path="primerApellido">
				<strong class="etiqueta">Primer Apellido:</strong>
			</form:label>
			<span id="primerApellidoError" class=" hiddenElement error"></span>
			<form:input type="text" path="primerApellido" maxlength="300"
				size="50" cssStyle="padding:2px;" class="dato" />
			</fieldset>
			<fieldset>
			<!-- 	private String segundoApellido; -->
			<form:errors path="segundoApellido" cssClass="error" />
			<form:label path="segundoApellido">
				<strong class="etiqueta">Segundo Apellido:</strong>
			</form:label>
			<span id="segundoApellidoError" class=" hiddenElement error"></span>
			<form:input type="text" path="segundoApellido" maxlength="300"
				size="50" cssStyle="padding:2px;" class="dato" />

			</fieldset>
			<fieldset>
			<!-- 	private String nombres; -->
			<form:errors path="nombres" cssClass="error" />
			<form:label path="nombres">
				<strong class="etiqueta">Nombre(s):</strong>
			</form:label>
			<span id="nombresError" class=" hiddenElement error"></span>
			<form:input type="text" path="nombres" maxlength="300" size="50"
				cssStyle="padding:2px;" class="dato" />
			</fieldset>
			</div>
			
			
			<div id=divPersonaMoral style="display:none;" >
<!-- 			//	SOLO SOCIOS MORALES -->
	
<!-- 	private String denominacionRazonSocial; -->

			<fieldset>
			<form:errors path="denominacionRazonSocial" cssClass="error" />
			<form:label path="denominacionRazonSocial">
				<strong class="etiqueta">Denominación Razón Social:</strong>
			</form:label>
			<span id="denominacionRazonSocialError" class=" hiddenElement error"></span>
			<form:input type="text" path="nombres" maxlength="300" size="50"
				cssStyle="padding:2px;" class="dato" />
				
</fieldset>
			<fieldset>				
<!-- 	private String tipoSociedad; -->
			<form:errors path="tipoSociedad" cssClass="error" />
			<form:label path="tipoSociedad">
				<strong class="etiqueta">Tipo Sociedad:</strong>
			</form:label>
			<span id="tipoSociedadError" class=" hiddenElement error"></span>
			<form:input type="text" path="tipoSociedad" maxlength="300" size="50"
				cssStyle="padding:2px;" class="dato" />
				</fieldset>
			</div>

			<fieldset>
			<!-- 			private String rfc; -->
			<form:errors path="rfc" cssClass="error" />
			<form:label path="rfc">
				<strong class="etiqueta">RFC:</strong>
			</form:label>
			<span id="rfcError" class=" hiddenElement error"></span>
			<form:input type="text" path="rfc" maxlength="300" size="50"
				cssStyle="padding:2px;" class="dato" />
</fieldset>
			<fieldset>
			<!-- 	private String calle; -->
			<form:errors path="calle" cssClass="error" />
			<form:label path="calle">
				<strong class="etiqueta">Calle:</strong>
			</form:label>
			<span id="calleError" class=" hiddenElement error"></span>
			<form:input type="text" path="calle" maxlength="300" size="50"
				cssStyle="padding:2px;" class="dato" />
</fieldset>
			<fieldset>
			<!-- 	private String numeroLetraExterior; -->
			<form:errors path="numeroLetraExterior" cssClass="error" />
			<form:label path="numeroLetraExterior">
				<strong class="etiqueta">Número o Letra Exterior:</strong>
			</form:label>
			<span id="numeroLetraExteriorError" class=" hiddenElement error"></span>
			<form:input type="text" path="numeroLetraExterior" maxlength="300"
				size="50" cssStyle="padding:2px;" class="dato" />
</fieldset>
			<fieldset>
			<!-- 	private String numeroLetraInterior;	 -->
			<form:errors path="numeroLetraInterior" cssClass="error" />
			<form:label path="numeroLetraInterior">
				<strong class="etiqueta">Número o Letra Interior:</strong>
			</form:label>
			<span id="numeroLetraInteriorError" class=" hiddenElement error"></span>
			<form:input type="text" path="numeroLetraInterior" maxlength="300"
				size="50" cssStyle="padding:2px;" class="dato" />
</fieldset>
			<fieldset>
			<!-- 	private String codigoPostal; -->
			<form:errors path="codigoPostal" cssClass="error" />
			<form:label path="codigoPostal">
				<strong class="etiqueta">Código Postal:</strong>
			</form:label>
			<span id="codigoPostalError" class=" hiddenElement error"></span>
			<form:input type="text" path="codigoPostal" maxlength="300" size="50"
				cssStyle="padding:2px;" class="dato" />
</fieldset>
			<fieldset>
			<!-- 	private String telefonoFijo; -->
			<form:errors path="telefonoFijo" cssClass="error" />
			<form:label path="telefonoFijo">
				<strong class="etiqueta">Telefono Fijo:</strong>
			</form:label>
			<span id="telefonoFijoError" class=" hiddenElement error"></span>
			<form:input type="text" path="telefonoFijo" maxlength="300" size="50"
				cssStyle="padding:2px;" class="dato" />
</fieldset>
			<fieldset>
			<!-- 	private String extension; -->
			<form:errors path="extension" cssClass="error" />
			<form:label path="extension">
				<strong class="etiqueta">EXT:</strong>
			</form:label>
			<span id="extensionError" class=" hiddenElement error"></span>
			<form:input type="text" path="extension" maxlength="300" size="50"
				cssStyle="padding:2px;" class="dato" />
</fieldset>
			<fieldset>
			<!-- 	private String telefonoMovil; -->
			<form:errors path="telefonoMovil" cssClass="error" />
			<form:label path="telefonoMovil">
				<strong class="etiqueta">Telefono Movil:</strong>
			</form:label>
			<span id="telefonoMovilError" class=" hiddenElement error"></span>
			<form:input type="text" path="telefonoMovil" maxlength="300"
				size="50" cssStyle="padding:2px;" class="dato" />
</fieldset>
			<fieldset>
			<!-- 	private String email; -->
			<form:errors path="email" cssClass="error" />
			<form:label path="email">
				<strong class="etiqueta">Correo Electronico:</strong>
			</form:label>
			<span id="emailError" class=" hiddenElement error"></span>
			<form:input type="text" path="email" maxlength="300" size="50"
				cssStyle="padding:2px;" class="dato" />
</fieldset>
			<fieldset>
			<!-- 	private String emailAlterno; -->
			<form:errors path="emailAlterno" cssClass="error" />
			<form:label path="emailAlterno">
				<strong class="etiqueta">Correo Electronico Alterno:</strong>
			</form:label>
			<span id="emailAlternoError" class=" hiddenElement error"></span>
			<form:input type="text" path="emailAlterno" maxlength="300" size="50"
				cssStyle="padding:2px;" class="dato" />

</fieldset>
			<fieldset>
			<!-- 	private Boolean esNacional; -->
			<form:input type="radio" path="esNacional" name="tipoDomicilioNuevo" cssStyle="padding:2px;" class="dato" value="true" checked/>
			<form:label path="esNacional">
				<strong class="etiqueta">Nacional:</strong>
			</form:label>
			<form:input type="radio" path="esNacional" name="tipoDomicilioNuevo" cssStyle="padding:2px;" class="dato" value="false"/>
			<form:label path="esNacional">
				<strong class="etiqueta">Extranjero:</strong>
			</form:label>
</fieldset>
			<div id="divDomicilioNacional" >
			<!-- // SOLO SOCIO NACIONAL	 -->
			<fieldset>
			<!-- 	private String coloniaPoblacion; -->
			<form:errors path="coloniaPoblacion" cssClass="error" />
			<form:label path="coloniaPoblacion">
				<strong class="etiqueta">Colonía o Población:</strong>
			</form:label>
			<span id="coloniaPoblacionError" class=" hiddenElement error"></span>
			<form:input type="text" path="coloniaPoblacion" maxlength="300"
				size="50" cssStyle="padding:2px;" class="dato" />
</fieldset>
			<fieldset>
			<!-- 	private String localidad; -->
			<form:errors path="localidad" cssClass="error" />
			<form:label path="localidad">
				<strong class="etiqueta">Localidad:</strong>
			</form:label>
			<span id="localidadError" class=" hiddenElement error"></span>
			<form:input type="text" path="localidad" maxlength="300" size="50"
				cssStyle="padding:2px;" class="dato" />
</fieldset>
			<fieldset>
			<!-- 	private String delegacionMunicipio; -->
			<form:errors path="delegacionMunicipio" cssClass="error" />
			<form:label path="delegacionMunicipio">
				<strong class="etiqueta">Delegación o Municipio:</strong>
			</form:label>
			<span id="delegacionMunicipioError" class=" hiddenElement error"></span>
			<form:input type="text" path="delegacionMunicipio" maxlength="300"
				size="50" cssStyle="padding:2px;" class="dato" />
</fieldset>
			<fieldset>
			<!-- 	private String entidadFederativa; -->
			<form:errors path="entidadFederativa" cssClass="error" />
			<form:label path="entidadFederativa">
				<strong class="etiqueta">Entidad Federativa:</strong>
			</form:label>
			<span id="entidadFederativaError" class=" hiddenElement error"></span>
			<form:input type="text" path="entidadFederativa" maxlength="300"
				size="50" cssStyle="padding:2px;" class="dato" />
				</fieldset>
			</div>

			<div id="divDomicilioExtranjero" style="display:none;" >
			<fieldset>
			<!-- //SOLO SOCIO EXTRANJERO	 -->
			<!-- 	private String localidadColonia; -->
			<form:errors path="localidadColonia" cssClass="error" />
			<form:label path="localidadColonia">
				<strong class="etiqueta">Localidad o Colonía:</strong>
			</form:label>
			<span id="localidadColoniaError" class=" hiddenElement error"></span>
			<form:input type="text" path="localidadColonia" maxlength="300"
				size="50" cssStyle="padding:2px;" class="dato" />
</fieldset>
			<fieldset>
			<!-- 	private String paisYCiudad; -->
			<form:errors path="paisYCiudad" cssClass="error" />
			<form:label path="paisYCiudad">
				<strong class="etiqueta">País y Ciudad:</strong>
			</form:label>
			<span id="paisYCiudadError" class=" hiddenElement error"></span>
			<form:input type="text" path="paisYCiudad" maxlength="300" size="50"
				cssStyle="padding:2px;" class="dato" />
</fieldset>
			<fieldset>
			<!-- 	private String estadoProvincia; -->
			<form:errors path="estadoProvincia" cssClass="error" />
			<form:label path="estadoProvincia">
				<strong class="etiqueta">Estado o Provincia:</strong>
			</form:label>
			<span id="estadoProvinciaError" class=" hiddenElement error"></span>
			<form:input type="text" path="estadoProvincia" maxlength="300"
				size="50" cssStyle="padding:2px;" class="dato" />
				</fieldset>
			</div>

		</form:form>
	</div>

</div>
