<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/datosContacto.js" htmlEscape="true" />"></script>

<form:form modelAttribute="sujetoTramite" id="datosContactoForm">
	
	<fieldset style="margin: 20px !important;">
		<legend>
			<strong><spring:message code="titulo.datos.contacto" /></strong>
		</legend>
		<form:hidden path="cveIdSujetoObligado"/>
		<form:hidden path="tipoPersonaFiscal"/>
		<form:hidden path="numeroRegistroPatronal"/>
		<c:set var="sol" value="${idSolicitud}" />
		
		<c:if test="${bFisica}">
			<form:hidden path="fisica.idPersona" />
		</c:if>
		<c:if test="${!bFisica}">
			<form:hidden path="moral.idPersona" />
		</c:if>
		<div id="divActualizarDatosContacto">
			<fieldset class="fsInterno">
				<label style="width:40%">
					<spring:message code="label.telefono.fijo"/>
				</label>
				<c:if test="${bFisica}">
					<form:hidden path="fisica.telefonoFijo.clave"/>
					<span id="moral.telefonoFijo.numeroError" class="error hiddenElement"></span>
					<form:input id="telFijo" path="fisica.telefonoFijo.numero" maxlength="12" cssStyle="width:20%"/>
				</c:if>
				<c:if test="${!bFisica}">
					<form:hidden path="moral.telefonoFijo.clave"/>
					<span id="moral.telefonoFijo.numeroError" class="error hiddenElement"></span>
					<form:input id="telFijo" path="moral.telefonoFijo.numero" maxlength="12" cssStyle="width:20%"/>
				</c:if>
				<label style="width:15%">
					<spring:message code="label.extension"/>
				</label>
				<c:if test="${bFisica}">
					<span id="fisica.telefonoFijo.extensionError" class="error hiddenElement"></span>
					<form:input id="extencion" path="fisica.telefonoFijo.extension" maxlength="6" cssStyle="width:10%"/>
				</c:if>
				<c:if test="${!bFisica}">
					<span id="moral.telefonoFijo.extensionError" class="error hiddenElement"></span>
					<form:input id="extencion" path="moral.telefonoFijo.extension" maxlength="6" cssStyle="width:10%"/>
				</c:if>
			</fieldset>
			<fieldset class="fsInterno">
				<label style="width:40%">
					<spring:message code="label.telefono.movil"/>
				</label>
				<c:if test="${bFisica}">
					<form:hidden path="fisica.telefonoMovil.clave" cssStyle="width:50%"/>
					<span id="fisica.telefonoMovil.numeroError" class="error hiddenElement"></span>
					<form:input id="telMovil" path="fisica.telefonoMovil.numero" maxlength="13" cssStyle="width:50%"/>
				</c:if>
				<c:if test="${!bFisica}">
					<form:hidden path="moral.telefonoMovil.clave" cssStyle="width:50%"/>
					<span id="moral.telefonoMovil.numeroError" class="error hiddenElement"></span>
					<form:input id="telMovil" path="moral.telefonoMovil.numero" maxlength="13" cssStyle="width:50%"/>
				</c:if>
			</fieldset>
			<fieldset class="fsInterno">
				<label style="width:40%">
					<spring:message code="label.correo.electronico"/>
				</label>
				<c:if test="${bFisica}">
					<form:hidden path="fisica.correoElectronico.clave" cssStyle="width:50%"/>
					<span id="fisica.correoElectronico.correoError" class="error hiddenElement"></span>
					<form:input id="correo" path="fisica.correoElectronico.correo" maxlength="60" cssStyle="width:50%"/>
				</c:if>
				<c:if test="${!bFisica}">
					<form:hidden path="moral.correoElectronico.clave" cssStyle="width:50%"/>
					<span id="moral.correoElectronico.correoError" class="error hiddenElement"></span>
					<form:input id="correo" path="moral.correoElectronico.correo" maxlength="60" cssStyle="width:50%"/>
				</c:if>
			</fieldset>
			<c:if test="${sol=='vacio'}">
				<div class="derecha">
					<input type="button" onclick="actualizarDatosContacto('activar');"
							class="mboton" name="aDatosGenerales"
							value="<spring:message code="label.finalizar"/>"/>
				</div>
			</c:if>
			<c:if test="${sol!='vacio'}">
				<div class="derecha">
					<input type="button" onclick="actualizarDatosContacto('finalizar');"
							class="mboton" name="aDatosGenerales"
							value="<spring:message code="label.finalizar"/>"/>
				</div>
			</c:if>
		</div>
	</fieldset>
</form:form>
<div id="dgActualizarDatosContacto" title="<spring:message code="label.exito"/>">
	<p><span class="ui-icon ui-icon-alert"
		style="float: left; margin: 0 7px 20px 0;"> </span>
		<spring:message code="label.datos.contacto.actualizada" />
	</p>
</div>