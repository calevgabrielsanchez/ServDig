<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/tramite/tramite-capturaDatosBasicos.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/tramite/tramite-personasEncontradas.js" htmlEscape="true" />"></script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<style>
input,textarea,.uneditable-input {
	text-transform: uppercase;
}

.table th {
    font-size: 10px;
}
</style>

<div class="contenedor">
	<div>
		<div id="info-paso">
			<h3 style="font-size: 1.8em !important">Paso 1: Consulta de
				personas f&iacute;sicas por datos b&aacute;sicos</h3>
			<div class="textwidget">
				<p style="font-size: .9em;">Localice a la persona f&iacute;sica
					que desea asignar el N&uacute;mero de Seguridad Social, capturando
					el CURP y/o los Datos B&aacute;sicos de la persona</p>
			</div>
		</div>

		<!-- Muesta los mensajes de error, este error es cuando encontro mas de un error -->
		<c:if test="${personaFisicaLst != null }">
			<!-- Solo cuando es usuario interno se presentara la lista de personas encontradas -->

			<div class="alert alert-info">
				<i class="glyphicon glyphicon-info-sign"></i>
				${mensaje}
				<a href="javascript:verRegistros();" class="alert-link"> Revisar registros </a>
			</div>

			<!-- Script para inicializar el dialogo de las personas encontradas -->
			<div id="dgPersonas"
				style="margin: 10px 0px !important; display: none;">

				<form:form modelAttribute="fisica"
					action="${contextpath}/tramite/complementar/persona" method="post"
					id="formaComplementarSeleccion">

					<form:hidden path="idPersona" id="hiddenIdPersona" />
					<form:hidden path="nss" id="hiddenNSSPersona" />

					<div title="Personas encontradas en el Instituto">
						<table id="personasFisicasFoundIMSSTable" style="width: 100%;"
							class="table table-striped table-bordered" cellpadding="0"
							cellspacing="0" border="0">
							<thead>
								<tr>
									<th>ID</th>
									<th>RFC</th>
									<th>CURP</th>
									<th>NSS</th>
									<th>Nombre(s)</th>
									<th>Primer Apellido</th>
									<th>Segundo Apellido</th>
									<th>Sexo</th>
									<th>Fecha de Nacimiento</th>
									<th>Año/Mes N.</th>
									<th>Entidad de Nacimento</th>
									<th>Calificaci&oacute;n</th>
									<th>Selecci&oacute;n</th>
								</tr>
							</thead>
							<c:forEach items="${personaFisicaLst}" var="personaFisica"
								varStatus="index">
								<tr>
									<td><c:out value="${personaFisica.idPersona}" /></td>
									<td><c:out value="${personaFisica.rfc}" /></td>
									<td><c:out value="${personaFisica.curp}" /></td>
									<td><c:out value="${personaFisica.nss}" /></td>
									<td><c:out value="${personaFisica.nombre}" /></td>
									<td><c:out value="${personaFisica.primerApellido}" /></td>
									<td><c:out value="${personaFisica.segundoApellido}" /></td>
									<td><c:out value="${personaFisica.sexo.descripcion}" /></td>
									<td><c:out value="${personaFisica.fechaNacimientoFormateada}"/></td>
									<td><c:out value="${personaFisica.anioRegistroNac}"/>/<c:out value="${personaFisica.mesRegistroNac}"/></td>
									<td><c:out value="${personaFisica.lugarNacimiento.nombre}" /></td>
									<td><c:out value="${personaFisica.subEstadosFormateados}" /></td>
									<td>
										<button type="button" idPersona="${personaFisica.idPersona}|${personaFisica.nss}"
											id="btnSeleccionarPersonaRegistrada" class="btn btn-default btn-sm">
											<c:choose>
												<c:when test="${not empty personaFisica.nss}">
												Recuperar
											</c:when>
												<c:otherwise>
												Seleccionar
											</c:otherwise>
											</c:choose>
										</button>
									</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</form:form>
				<div class="text-right m-t-lg">
					<button type="button" idPersona="0" id="btnRegistrarPersonaNueva"
						class="btn btn-primary">Nuevo</button>
				</div>
			</div>
		</c:if>

		<!-- Solo cuando es usuario externo ... -->
		<c:if test="${errorDatosExistentes != null }">
			<div style="width: 500px;" align="center">
				<div class="ui-widget">
					<div style="margin-top: 20px; padding: 0 .7em;"
						class="ui-state-highlight ui-corner-all">
						<p>
							<span style="float: left; margin-right: .3em;"
								class="ui-icon ui-icon-info"></span> ${mensaje}
						</p>
					</div>

				</div>
			</div>
		</c:if>

		<c:if test="${empty FROM_PREREGISTRO || !FROM_PREREGISTRO}">
			<!-- Forma de la consulta de personas por datos basicos. -->
			<div class="contenedor">

				<form:form modelAttribute="fisica"
					id="registroAseguradoDatosBasicosForm"
					action="${contextpath}/tramite/consultaDatosBasicos" method="post"
					cssClass="form-horizontal" role="form">

					<fieldset>
						<legend>
							Datos de la Persona F&iacute;sica localizada
						</legend>
						<!-- Seccion de errores -->
						<form:errors path="*" cssClass="error" />

						<div class="form-group">
							<form:label path="curp" cssClass="col-sm-3 control-label">
								<span class="required">*</span>CURP</form:label>
							<div class="col-sm-6">
								<form:input path="curp" id="registroCurp"
									maxlength="18" cssClass="alfanumericoEstricto form-control" />
							</div>
							<div class="col-sm-3">
								<form:errors path="curp" cssClass="error" />
							</div>
						</div>

						<div class="form-group">
							<form:label path="nombre" cssClass="col-sm-3 control-label">
								<span class="required">*</span>Nombre(s)</form:label>
							<div class="col-sm-6">
								<form:input path="nombre" id="registroNombres"
									maxlength="50" cssClass="alfanumericoNSS form-control" />
							</div>
							<div class="col-sm-3">
								<form:errors path="nombre" cssClass="error" />
							</div>
						</div>

						<div class="form-group">
							<form:label path="primerApellido" cssClass="col-sm-3 control-label">
								<span class="required">*</span>Primer Apellido</form:label>
							<div class="col-sm-6">
								<form:input path="primerApellido" id="registroPrimerApellido"
									maxlength="50" cssClass="alfanumericoNSS form-control" />
							</div>
							<div class="col-sm-3">
								<form:errors path="primerApellido" cssClass="error" />
							</div>
						</div>

						<div class="form-group">
							<form:label path="segundoApellido" cssClass="col-sm-3 control-label">Segundo Apellido</form:label>
							<div class="col-sm-6">
								<form:input path="segundoApellido" id="registroSegundoApellido"
									maxlength="50" cssClass="alfanumericoNSS form-control" />
							</div>
						</div>

						<div class="form-group">
							<form:label path="sexo.idSexo" cssClass="col-sm-3 control-label">
								<span class="required">*</span>Sexo</form:label>
							<div class="col-sm-6">
								<combo:creaCombo idHtml="sexo.idSexo"
									idHtmlContenedor="registroAseguradoDatosBasicosForm"
									entidad="mx.gob.imss.ctirss.delta.persistence.DicSexo"
									idHtmlValor="${fisica.sexo.idSexo}" 
									mostrarSoloActivos="true"
									cssClassname="form-control" />
							</div>
							<div class="col-sm-3">
								<form:errors path="sexo.idSexo" cssClass="error" />
							</div>
						</div>

						<div class="form-group">
							<form:label path="fechaNacimiento" cssClass="col-sm-3 control-label">
								<span class="required">*</span>Fecha de Nacimiento</form:label>
							<div class="col-sm-6">
								<form:input path="fechaNacimiento" id="registroFechaNacimiento"
									style="width: auto" maxlength="10" cssClass="form-control" />
							</div>
							<div class="col-sm-3">
								<form:errors path="fechaNacimiento" cssClass="error" />
							</div>
						</div>

						<div class="form-group">
							<form:label path="lugarNacimiento.clave" cssClass="col-sm-3 control-label">
								<span class="required">*</span>Lugar de Nacimiento</form:label>
							<div class="col-sm-6">
								<combo:creaCombo idHtml="lugarNacimiento.clave"
									idHtmlContenedor="registroAseguradoDatosBasicosForm"
									entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado"
									idHtmlValor="${fisica.lugarNacimiento.clave}" 
									mostrarSoloActivos="true"
									cssClassname="form-control" />
							</div>
							<div class="col-sm-3">
								<form:errors path="lugarNacimiento.clave" cssClass="error" />
							</div>
						</div>

						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10 text-right">
							<button type="button" id="limpiar" class="btn btn-default">LIMPIAR</button>
								<c:if test="${not empty FROM_SIME }">
									<button type="button" id="ignoreCurrentRecord"
										class="btn btn-warning">PENDIENTE</button>
								</c:if>
								<button type="button" id="buscar" class="btn btn-primary">BUSCAR</button>
							</div>
						</div>
					</fieldset>
				</form:form>

			</div>
		</c:if>
	</div>
</div>
<!-- Div del dialogo de localizar persona -->
<div id="dgLocalizarPersona"></div>

<c:if test="${not empty FROM_SIME}">
	<form id="extranjeroSIMEIgnoreForm"
		action="${contextpath}/sime/ignorar/registro-en-proceso" method="post"></form>
</c:if>