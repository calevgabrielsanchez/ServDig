
<h3>
	<spring:message code="label.solicitud.datosAdicionalesHistoriaLaboral" />
</h3>
<hr class="red" style="margin-bottom: 20px;">

<div class="form-group">
	<div class="row">
		<label for="numeroRegistroPatronal" class="col-md-3 col-sm-5 col-xs-12 control-label"
			style="text-align: left;"> <spring:message
				code="label.solicitud.placeholder.numeroRegistroPatronal" />: *
		</label>
		<div class="col-md-3 col-sm-7 col-xs-12">
			<spring:message
				code="label.solicitud.placeholder.numeroRegistroPatronal"
				var="placeHolderNumeroRegistroPatronal" />
			<form:input path="datosAdicionalesForm.numeroRegistroPatronal"
				id="registroNumeroRegistroPatronal" cssClass="form-control" maxlength="11" placeholder="${placeHolderNumeroRegistroPatronal}" />
			<span id="numeroRegistroPatronalError" class="error hiddenElement"></span>
		</div>
		<label for="actividadEmpresa"
			class="col-md-3 col-sm-5 col-xs-12 control-label"
			style="text-align: left;"> <spring:message
				code="label.solicitud.placeholder.actividadEmpresa" />: *
		</label>
		<div class="col-md-3 col-sm-7 col-xs-12">
			<spring:message code="label.solicitud.placeholder.actividadEmpresa" var="placeHolderActividadEmpresa" />
			<form:input path="datosAdicionalesForm.actividadEmpresa" id="registroActividadEmpresa" cssClass="form-control" maxlength="18"
				placeholder="${placeHolderActividadEmpresa}" />
			<span id="actividadEmpresaError" class="error hiddenElement"></span>
		</div>
	</div>
</div>

<div class="form-group">
	<div class="row">
		<label for="domicilioEmpresa"
			class="col-md-3 col-sm-5 col-xs-12 control-label"
			style="text-align: left;"> <spring:message
				code="label.solicitud.placeholder.domicilioEmpresa" />: *
		</label>
		<div class="col-md-3 col-sm-7 col-xs-12">
			<spring:message code="label.solicitud.placeholder.domicilioEmpresa"
				var="placeHolderDomicilioEmpresa" />
			<form:input path="datosAdicionalesForm.domicilioEmpresa"
				id="registroDomicilioEmpresa" cssClass="form-control" maxlength="18" placeholder="${placeHolderDomicilioEmpresa}" />
			<span id="domicilioEmpresaError" class="error hiddenElement"></span>
		</div>
		<div class="col-md-6 col-sm-7 col-xs-12 text-right">
			<button type="button" id="agregar" class="btn btn-primary">
				<spring:message code="label.solicitud.agregar" />
			</button>
		</div>
	</div>
</div>

<div class="form-group">
	<div class="row">
		<div class="col-md-12 col-sm-7 col-xs-12">
			<table class="table table-bordered"
				id='datosAdicionalesHistoriaLaboralGrid'>

				<tr>
					<th name="numeroRegistroPatronal"><spring:message
							code="label.solicitud.placeholder.numeroRegistroPatronal" /></th>
					<th name="actividadEmpresa"><spring:message
							code="label.solicitud.placeholder.actividadEmpresa" /></th>
					<th name="domicilioEmpresa"><spring:message
							code="label.solicitud.placeholder.domicilioEmpresa" /></th>
					<th name="acciones"><spring:message
							code="label.solicitud.placeholder.acciones" /></th>
				</tr>
				<c:forEach var="datosAdicionales" varStatus="contador"
					items="${informacionHistoriaLaboral.datosAdicionalesList}">
					<tr id="datosAdicionalesHistoriaLaboralGrid${contador.index +1}">
						<td>${datosAdicionales.numeroRegistroPatronal}</td>
						<td>${datosAdicionales.actividadEmpresa}</td>
						<td>${datosAdicionales.domicilioEmpresa}</td>
						<td><a href="#"
							onclick="return fnEliminarRow('datosAdicionalesHistoriaLaboralGrid${contador.index+1}');">Eliminar</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-md-12 col-sm-7 col-xs-12">
		<form:errors path="datosAdicionalesList" cssClass="error" />
	</div>
</div>
</div>