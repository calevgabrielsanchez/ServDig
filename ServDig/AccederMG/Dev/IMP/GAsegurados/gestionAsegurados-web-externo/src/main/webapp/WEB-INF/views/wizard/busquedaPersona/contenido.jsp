<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/procesaErrores.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/wizard/busquedaPersona/contenido.js" htmlEscape="true" />"></script>


<input type="hidden" id="origenPeticion" value="${origenPeticion}">
<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<div class="contenedor col-sm-12">
	<div class="contenido">
	
		<div class="alert alert-danger" id="divError" style="display: none">
			<strong>Error: </strong>
			<span id="mensajeError"></span>
		</div>

		<div id="personasWrapper">
			<div class="separadorseccion">
				<span> Datos de la persona </span>
			</div>
			<h5></>A continuaci&oacute;n proporciona la informaci&oacute;n de la persona a buscar:</h5>
			<div class="alert alert-danger" style="display: none" id="divErrorCampos"></div>
		
			<form:form modelAttribute="fisica" id="busquedaPersonaFisica" method="post" rol="form" cssClass="form-horizontal">
				<fieldset style="border: 1px solid #CCC; padding: 50px;">
				<div class="form-group">
					<label class="col-sm-4 col-md-offset-1  control-label" for="curp">
						CURP<span class="required" id="requiredCurp">*</span>:
					</label>
					<div class="col-sm-6">
						<form:input path="curp" cssClass="form-control" maxlength="18" />
						<span id="curpError" class="error hiddenElement"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 col-md-offset-1 control-label" for="nombre">
						Nombre(s)<span class="required" id="requiredNombre" style="display: none">*</span>:
					</label>
					<div class="col-sm-6">
						<form:input path="nombre" cssClass="form-control" maxlength="50" />
						<span id="nombreError" class="error hiddenElement"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 col-md-offset-1 control-label" for="primerApellido">
						Primer apellido<span class="required" id="requiredPrimerApellido" style="display: none">*</span>:
					</label>
					<div class="col-sm-6">
						<form:input path="primerApellido" cssClass="form-control" maxlength="50" />
						<span id="primerApellidoError" class="error hiddenElement"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 col-md-offset-1 control-label" for="segundoApellido">
						Segundo apellido<span class="required" id="requiredPrimerApellido" style="display: none">*</span>:
					</label>
					<div class="col-sm-6">
						<form:input path="segundoApellido" cssClass="form-control" maxlength="50" />
						<span id="segundoApellidoError" class="error hiddenElement"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 col-md-offset-1 control-label" for="sexo.idSexo">
						Sexo<span class="required" id="requiredSexo.idSexo" style="display: none">*</span>:
					</label>
					<div class="col-sm-6">
						<combo:creaCombo idHtml="sexo.idSexo" idHtmlContenedor="busquedaPersonaFisica"
							entidad="mx.gob.imss.ctirss.delta.persistence.DicSexo" idHtmlValor="${fisica.sexo.idSexo}"
							mostrarSoloActivos="true" cssClassname="form-control" />
						<span id="sexo.idSexoError" class="error hiddenElement"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 col-md-offset-1 control-label" for="fechaNacimiento">
						Fecha de nacimiento<span class="required" id="requiredFechaNacimiento" style="display: none">*</span>:
					</label>
					<div class="col-sm-6">
						<form:input path="fechaNacimiento" style="width: 100px" cssClass="form-control" maxlength="10" />
						<span id="fechaNacimientoError" class="error hiddenElement"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 col-md-offset-1 control-label" for="lugarNacimiento.clave">
						Lugar de nacimiento<span class="required" id="requiredLugarNacimiento.clave" style="display: none">*</span>:
					</label>
					<div class="col-sm-6">
						<combo:creaCombo idHtml="lugarNacimiento.clave" idHtmlContenedor="busquedaPersonaFisica"
							entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado" idHtmlValor="${fisica.lugarNacimiento.clave}"
							mostrarSoloActivos="true" cssClassname="form-control" />
						<span id="lugarNacimiento.claveError" class="error hiddenElement"></span>
					</div>
				</div>
				</fieldset>
				<div class="form-group m-t-lg">
					<div class="col-sm-3 text-left">
						<span class="required" id="labelCamposObligatoriosGeneral">*</span>Campos obligatorios
					</div>
					<div class="col-sm-9 text-right">
						<input type="button" value="Limpiar" class="btn btn-default" id="limpiar" />
						<input type="button" value="Aceptar" id="aceptar" class="btn btn-primary" style="display: none" />
						<input type="button" value="Habilitar consulta por Curp" id="habilitaCurp" class="btn btn-primary" style="display: none" />
						<input type="button" value="Buscar" id="buscar" class="btn btn-primary" />
					</div>
				</div>
    				
			</form:form>
			
			</div>

			<div id="listadoPersonas" style="display: none; width: 100%; overflow: auto;" class="m-b-xl">
				<div class="separadorseccion">
					<span> Personas Encontradas </span>
				</div>
				<div class="alert alert-warning" id="divWarningDefuncion" style="display: none">
					<span id="mensajeDivWarnigDefuncion"></span>
				</div>
				<div id="tablaPersonasEncontradas">
					<table id="personasFisicasFoundIMSSTable" style="width: 100%;" class="table table-striped table-bordered">
						<thead>
							<tr>
								<th>ID</th>
								<th>RFC</th>
								<th>CURP</th>
								<th>NSS</th>
								<th>Nombre(s)</th>
								<th>Primer apellido</th>
								<th>Segundo apellido</th>
								<th>Sexo</th>
								<th>Fecha nacimiento</th>
								<th>Estado nacimento</th>
								<th>Calificaci&oacute;n</th>
								<th>Selecci&oacute;n</th>
							</tr>
						</thead>
						<tbody>

						</tbody>
					</table>
				</div>
			</div>
	</div>
</div>