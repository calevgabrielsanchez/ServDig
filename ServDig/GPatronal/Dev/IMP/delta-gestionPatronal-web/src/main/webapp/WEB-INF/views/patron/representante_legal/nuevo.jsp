<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div id="agregar"
	style="float: left; width: 100% !important; padding-top: 10px;"
	class="page_holder dialogo_holder">

	<h2 style="font-size: 1.0em !important;">Agregar Representante
		Legal</h2>

	<div class="derecha">
		<p>
			<span class="ui-icon ui-icon-info"
				style="float: left; margin-right: .3em;"></span> 
				<strong><a
				href="javascript:fnOpenBuscarPersonaFisica();">Cargar datos de Persona F&iacute;sica</a>
			</strong>
		</p>
		<p>
			<span class="ui-icon ui-icon-info"
				style="float: left; margin-right: .3em;"></span> 
				<strong><a
				href="javascript:fnOpenAgregarDatosContacto();">Agregar datos de Contacto</a>
			</strong>
		</p>
	</div>
	
	<div id="personaFisica"></div>

	<form:form modelAttribute="representanteLegal"
		id="representanteLegalFormNuevo" action="/representanteLegal/agregar">
		<span id="errorNegocioLabel" class=" hiddenElement error"></span>

		<fieldset style="margin: 20px !important;">

			<form:hidden path="cveIdPatronSujetoObligado" />
			<form:hidden path="indActAdmon" />
			<form:hidden path="indActDominio" />
			<form:hidden path="cveIdPersona" />
			<form:hidden id="personaFisicaCveIdPersona" path="cveIdPersona" />

			<legend>
				<strong><spring:message code="datos.basicos" /></strong>
			</legend>

			<fieldset class="fsInterno">
				<label style="width: 40%"><spring:message code="label.rfc" /></label>
				<form:input id="rfc" path="personaFisica.rfc" readonly="true" />
			</fieldset>

			<fieldset class="fsInterno">
				<label style="width: 40%"><spring:message
						code="rep.legal.curp" /></label>
				<form:input id="curp" path="personaFisica.curp" readonly="true" />
			</fieldset>

			<fieldset class="fsInterno">
				<label style="width: 40%"><spring:message
						code="label.primer.apellido" /></label>
				<form:input id="primerApellido" path="personaFisica.primerApellido"
					readonly="true" />
			</fieldset>

			<fieldset class="fsInterno">
				<label style="width: 40%"><spring:message
						code="label.segundo.apellido" /></label>
				<form:input id="segundoApellido"
					path="personaFisica.segundoApellido" readonly="true" />
			</fieldset>

			<fieldset class="fsInterno">
				<label style="width: 40%"><spring:message
						code="label.nombres" /></label>
				<form:input id="nombre" path="personaFisica.nombre" readonly="true" />
			</fieldset>

		</fieldset>
	


	<fieldset style="margin: 20px !important;">

		<fieldset class="fsInterno">
			<label style="width: 40%"><spring:message
					code="rep.legal.telefono.fijo.numero" /></label> 
				<form:input path="personaFisica.telefonoFijo.numero" />

		</fieldset>

		<fieldset class="fsInterno">
			<label style="width: 40%"><spring:message
					code="rep.legal.telefono.fijo.cve.lada" /></label> 
					<form:input path="personaFisica.telefonoFijo.claveLada" />
		</fieldset>

		<fieldset class="fsInterno">
			<label style="width: 40%"><spring:message
					code="rep.legal.telefono.fijo.ext" /></label> 
					<form:input path="personaFisica.telefonoFijo.extension" />
		</fieldset>

		<fieldset class="fsInterno">
			<label style="width: 40%"><spring:message
					code="rep.legal.telefono.movil.numero" /></label> 
					<form:input path="personaFisica.telefonoMovil.numero" />
		</fieldset>

		<fieldset class="fsInterno">
			<label style="width: 40%"><spring:message
					code="rep.legal.dir.correo" /></label> 
					<form:input path="personaFisica.correoElectronico.correo" />
		</fieldset>


	</fieldset>
	
	</form:form>
	
	<fieldset style="margin: 20px !important;">
	
	<fieldset class="fsInterno">
			<input type="checkbox" name="indActAdmon1" id="indActAdmon1" />Poder
			para Actos de administraci&oacute;n? <br /> <input type="checkbox"
				name="indActDominio1" id="indActDominio1" />Poder para Actos de
			dominio?
		</fieldset>

	</fieldset>
</div>

