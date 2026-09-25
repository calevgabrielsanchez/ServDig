<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div>


	<div id="modificar"
		style="float: left; width: 100%; padding-top: 10px;"
		class="page_holder dialogo_holder">
		<h2 style="font-size: 1.0em !important;">Modificar Representante
			Legal</h2>
		<p>
			<span class="info">Modifique los datos de Representante Legal:</span>
		</p>
		

		

		<form:form modelAttribute="representanteLegal"
			id="representanteLegalFormModificar">
			
			
			

			<span id="errorNegocioLabel" class=" hiddenElement error"></span>

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



			<form:hidden path="cveIdRepresentanteLegal" />
			<form:hidden path="indActAdmon" />
			<form:hidden path="indActDominio" />
			<form:hidden path="cveIdPersona" />
			<form:hidden path="cveIdPatronSujetoObligado" />
			<form:hidden path="fecRegistroActualizado" />
			<form:hidden path="fecRegistroBaja" />
			<form:hidden path="fecRegistroAlta" />
				
				
		
			



			<fieldset style="margin: 20px !important;">

				<fieldset class="fsInterno">
					<label style="width: 40%"><spring:message
							code="rep.legal.telefono.fijo.numero" /></label>
					<form:input id="telefonoFijoNumero"
						path="personaFisica.telefonoFijo.numero" />
				</fieldset>

				<fieldset class="fsInterno">
					<label style="width: 40%"><spring:message
							code="rep.legal.telefono.fijo.cve.lada" /></label>
					<form:input id="telefonoFijoClaveLada"
						path="personaFisica.telefonoFijo.claveLada" />
				</fieldset>

				<fieldset class="fsInterno">
					<label style="width: 40%"><spring:message
							code="rep.legal.telefono.fijo.ext" /></label>
					<form:input id="telefonoFijoExtension"
						path="personaFisica.telefonoFijo.extension" />
				</fieldset>

				<fieldset class="fsInterno">
					<label style="width: 40%"><spring:message
							code="rep.legal.telefono.movil.numero" /></label>
					<form:input id="telefonoMovilNumero"
						path="personaFisica.telefonoMovil.numero" />
				</fieldset>

				<fieldset class="fsInterno">
					<label style="width: 40%"><spring:message
							code="rep.legal.dir.correo" /></label>
					<form:input id="dirCorreo"
						path="personaFisica.correoElectronico.correo" />
				</fieldset>
			</fieldset>
		</form:form>


		<fieldset style="margin: 20px !important;">
			<fieldset class="fsInterno">
				<input type="checkbox" name="indActAdmon2" id="indActAdmon2" />Poder
				para Actos de administraci&oacute;n? <br /> <input type="checkbox"
					name="indActDominio2" id="indActDominio2" />Poder para Actos de
				dominio?
			</fieldset>
		</fieldset>
	</div>
</div>
