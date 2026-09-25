<script>
	$(document).ready(function() {
		document.charset = 'utf-8';
	});
</script>
<div id="info-paso" style="margin-bottom: 50px;">
	<h3>
		<spring:message code="label.solicitud.datosHistoriaLaboral" />
	</h3>
	<hr class="red" style="margin-bottom: 20px;">

	<div class="row">
		<label for="nombrePatron"
			class="col-md-12 col-sm-5 col-xs-12 control-label"
			style="text-align: left;"> <spring:message
				code="label.solicitud.placeholder.LeyendaAyudaNRP" />
		</label>
	</div>

	<div class="form-group">
		<div class="row">
			<label for="nombrePatron"
				class="col-md-3 col-sm-5 col-xs-12 control-label"
				style="text-align: left;"> <spring:message
					code="label.solicitud.placeholder.nombrePatron" />: *
			</label>
			<div class="col-md-3 col-sm-7 col-xs-12">
				<spring:message code="label.solicitud.placeholder.nombrePatron"
					var="placeHolderNombrePatron" />
				<form:input path="historiaLaboralForm.nombrePatron"
					id="registroNombrePatron" cssClass="form-control" maxlength="250"
					placeholder="${placeHolderNombrePatron}" />
				<span id="nombrePatronError" class="error hiddenElement"></span>
			</div>
			<label for="entidadFederativa"
				class="col-md-3 col-sm-5 col-xs-12 control-label"
				style="text-align: left;"> <spring:message
					code="label.solicitud.placeholder.entidadFederativa" />: *
			</label>
			<div class="col-md-3 col-sm-7 col-xs-12">
				<spring:message code="label.solicitud.placeholder.entidadFederativa"
					var="placeHolderEntidadFederativa" />


				<combo:creaCombo idHtml="registroEntidadFederativa"
					idHtmlContenedor="informacionHistoriaLaboralForm"
					entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado"
					cssClassname="form-control" mostrarSoloActivos="true"
					mostrarSoloEntidades="true" />
				<span id="entidadFederativaError" class="error hiddenElement"></span>
			</div>
		</div>
	</div>

	<div class="form-group">
		<div class="row">
			<label for="fechaInscripcion"
				class="col-md-3 col-sm-5 col-xs-12 control-label"
				style="text-align: left;"> <spring:message
					code="label.solicitud.placeholder.fechaInscripcion" />: * <span
				id="ayudaFechaInscripcion" class="glyphicon glyphicon-question-sign"></span>
				<!--</a>-->
			</label>
			<div class="col-md-3 col-sm-7 col-xs-12">
				<span> <select id="diaFechaInscripcion"
					name="diaFechaInscripcion" class="form-control"></select> <select
					id="mesFechaInscripcion" name="mesFechaInscripcion"
					class="form-control"></select> <select id="anioFechaInscripcion"
					name="anioFechaInscripcion" class="form-control"></select>
				</span> <span id="fechaInscripcionError" class="error hiddenElement"></span>
			</div>

			<label for="fechaBaja"
				class="col-md-3 col-sm-5 col-xs-12 control-label"
				style="text-align: left;"> <spring:message
					code="label.solicitud.placeholder.fechaBaja" />: * <span
				id="ayudaFechaBaja" class="glyphicon glyphicon-question-sign"></span>
			</label>
			<div class="col-md-3 col-sm-7 col-xs-12">
				<span> <select id="diaFechaBaja" name="diaFechaBaja"
					class="form-control"></select> <select id="mesFechaBaja"
					name="mesFechaBaja" class="form-control"></select> <select
					id="anioFechaBaja" name="anioFechaBaja" class="form-control"></select>
				</span> <span id="fechaBajaError" class="error hiddenElement"></span>
			</div>
			<div class="row col-md-12">
				<div class="pull-right">
					<input id="idVigente" type="checkbox" name="idVigente" value="1">
					<spring:message code="label.solicitud.vigente.fecha" />
				</div>
			</div>

		</div>
	</div>

	<div class="form-group">
		<div class="row">
			<label for="numeroRegistroPatronal"
				class="col-md-3 col-sm-5 col-xs-12 control-label"
				style="text-align: left;"> <spring:message
					code="label.solicitud.placeholder.numeroRegistroPatronal" />: *
			</label>
			<div class="col-md-3 col-sm-7 col-xs-12">
				<spring:message
					code="label.solicitud.placeholder.numeroRegistroPatronal"
					var="placeHolderNumeroRegistroPatronal" />
				<form:input path="historiaLaboralForm.numeroRegistroPatronal"
					id="registroNumeroRegistroPatronal" cssClass="form-control"
					maxlength="11" placeholder="${placeHolderNumeroRegistroPatronal}" />
				<span id="numeroRegistroPatronalError" class="error hiddenElement"></span>
			</div>
			<label for="actividadEmpresa"
				class="col-md-3 col-sm-5 col-xs-12 control-label"
				style="text-align: left;"> <spring:message
					code="label.solicitud.placeholder.actividadEmpresa" />: *
			</label>
			<div class="col-md-3 col-sm-7 col-xs-12">
				<spring:message code="label.solicitud.placeholder.actividadEmpresa"
					var="placeHolderActividadEmpresa" />
				<form:input path="historiaLaboralForm.actividadEmpresa"
					id="registroActividadEmpresa" cssClass="form-control"
					maxlength="250" placeholder="${placeHolderActividadEmpresa}" />
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
				<form:input path="historiaLaboralForm.domicilioEmpresa"
					id="registroDomicilioEmpresa" cssClass="form-control"
					maxlength="250" placeholder="${placeHolderDomicilioEmpresa}" />
				<span id="domicilioEmpresaError" class="error hiddenElement"></span>
			</div>
			<div class="col-md-6 col-sm-7 col-xs-12 text-right">

				<button type="button" id="agregarHistoriaLaboral"
					class="btn btn-primary">
					<spring:message code="label.solicitud.agregar" />
				</button>
			</div>
		</div>
	</div>

	<div class="form-group">
		<div class="row">
			<span id="historiaLaboralFormError" class="error hiddenElement"></span>
			<div class="col-md-12 col-sm-7 col-xs-12">
				<table class="table table-hover table-bordered table-responsive"
					id='datosHistoriaLaboralGrid' name="datosHistoriaLaboralGrid"
					style="font-size: 18px !important; table-layout: fixed; width: 100%"">
					<tr>
						<th name="nombrePatron"><spring:message
								code="label.solicitud.placeholder.nombreRazonSocial" /></th>
						<th name="entidadFederativa" style="width: 175px"><spring:message
								code="label.solicitud.placeholder.entidadFederativa" /></th>
						<th name="fechaInscripcion"><spring:message
								code="label.solicitud.placeholder.fechaInscripcion" /></th>
						<th name="fechaBaja"><spring:message
								code="label.solicitud.placeholder.fechaBaja" /></th>
						<th name="numeroRegistroPatronal"><spring:message
								code="label.solicitud.placeholder.numeroRegistroPatronal" /></th>
						<th name="actividadEmpresa"><spring:message
								code="label.solicitud.placeholder.actividadEmpresa" /></th>
						<th name="domicilioEmpresa"><spring:message
								code="label.solicitud.placeholder.domicilioEmpresa" /></th>
						<th name="acciones" style="width: 100px"><spring:message
								code="label.solicitud.placeholder.acciones" /></th>
					</tr>
					<c:forEach var="historiaLaboral" varStatus="contador"
						items="${informacionHistoriaLaboral.historiaLaboralGrid}">
						<tr id="datosHistoriaLaboralGrid${contador.index +1}">
							<td style="word-wrap: break-word">${historiaLaboral.nombrePatron}</td>
							<td style="word-wrap: break-word">${historiaLaboral.entidadFederativa}</td>
							<td style="word-wrap: break-word">${historiaLaboral.fechaInscripcion}</td>
							<td style="word-wrap: break-word">${historiaLaboral.fechaBaja}</td>
							<td style="word-wrap: break-word">${historiaLaboral.numeroRegistroPatronal}</td>
							<td style="word-wrap: break-word">${historiaLaboral.actividadEmpresa}</td>
							<td style="word-wrap: break-word">${historiaLaboral.domicilioEmpresa}</td>
							<td><a href="#"
								onclick="return fnEliminarRow('datosHistoriaLaboralGrid${contador.index+1}');">Eliminar</a>
								<input id="idEntidadFederativa" type="hidden"
								name="idEntidadFederativa"
								value="${historiaLaboral.claveEntidad}"></td>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12 col-sm-7 col-xs-12">
				<form:errors path="historiaLaboralGrid" cssClass="error" />
			</div>
		</div>
	</div>
</div>