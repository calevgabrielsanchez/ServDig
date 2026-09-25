<div class="form-group">
	<div class="row col-md-12 form-group">
		<label><spring:message
				code="label.docsProbatorios.tipoSolicitud" /> *</label>
	</div>
		<div class="row col-md-12">
			<c:choose>
				<c:when
					test="${informacionAdicional.tipoSolicitante=='BENEFICIARIO'}">
					<spring:message code="label.solicitud.beneficiario" />
				</c:when>
				<c:when
					test="${informacionAdicional.tipoSolicitante=='REPRESENTANTE_LEGAL'}">
					<spring:message code="label.solicitud.representante" />
				</c:when>
				<c:when
					test="${informacionAdicional.tipoSolicitante=='ASEGURADO'}">
					<spring:message code="label.solicitud.asegurado" />
				</c:when>
			</c:choose>

			<input id="tipoSolicitante"
				value="${informacionAdicional.tipoSolicitante}" type="hidden" />
			<input id="defuncion" value="${informacionAdicional.defuncion}"
				type="hidden" />

		</div>
		<div class="row">
			<form:errors path="tipoSolicitante" cssClass="error" />
		</div>
	
	<c:if test="${informacionAdicional.tipoSolicitante=='BENEFICIARIO'}">
		<div class="row col-md-12 form-group">
			<label><spring:message
					code="label.docsProbatorios.parentesco" /></label><span id="idBeneficio">*</span><span
				id="idBeneficioError" class="error hiddenElement"
				style="font-size: 12px !important;"></span>
			<div class=" row col-md-12">
				<label class="radio-inline"> <input id="conyugue"
					type="radio" name="tipoBeneficiario" value="CONYUGE"
					${informacionAdicional.tipoBeneficiario == 'CONYUGE' ? 'checked' : ''} disabled>
					<spring:message
						code="label.docsProbatorios.tipoBeneficiario.coyuge" />
				</label> <label class="radio-inline"> <input id="descendiente"
					type="radio" name="tipoBeneficiario" value="DESCENDIENTE"
					${informacionAdicional.tipoBeneficiario == 'DESCENDIENTE' ? 'checked' : ''} disabled>
					<spring:message
						code="label.docsProbatorios.tipoBeneficiario.descendiente" />
				</label>
			</div>
			<div class="row col-md-12">
				<label class="radio-inline"> <input id="padres" type="radio"
					name="tipoBeneficiario" value="PADRES"
					${informacionAdicional.tipoBeneficiario == 'PADRES' ? 'checked' : ''} disabled>
					<spring:message
						code="label.docsProbatorios.tipoBeneficiario.padres" />
				</label> <label class="radio-inline">
					<div class="col-md-12">
						<input id="concubino" type="radio" name="tipoBeneficiario"
							value="CONCUBINO"
							${informacionAdicional.tipoBeneficiario == 'CONCUBINO' ? 'checked' : ''} disabled>
						<spring:message
							code="label.docsProbatorios.tipoBeneficiario.concubino" />
					</div>
				</label>
			</div>
		</div>
		<div class="row">
			<span style="font-size: 18px" id="tipoBeneficiarioError"
				class="error hiddenElement"></span>
			<form:errors path="tipoBeneficiario" cssClass="error" />
		</div>
	</c:if>
	<c:if test="${informacionAdicional.tipoSolicitante=='BENEFICIARIO' 
		|| informacionAdicional.tipoSolicitante=='REPRESENTANTE_LEGAL'}">
		<div class="row col-md-12 form-group">
			<c:choose>
				<c:when
					test="${informacionAdicional.tipoSolicitante=='BENEFICIARIO'}">
					<label><spring:message
							code="label.solicitud.curpBeneficiario" /></label>
				</c:when>
				<c:when
					test="${informacionAdicional.tipoSolicitante=='REPRESENTANTE_LEGAL'}">
					<label><spring:message
							code="label.solicitud.curpRepresentante" /></label>
				</c:when>
			</c:choose>
		</div>

		<div class="row col-md-12 form-group">
			<div class="row col-md-4 col-sm-7 col-xs-12">
				<input id="registroCurp" type="text" cssClass="form-control"
					maxlength="18" name="tipoBeneficiario"
					value="${informacionAdicional.curp}" disabled> <span
					id="curpError" class="error hiddenElement"></span>
				<form:errors id="curpError" path="curp" cssClass="error" />
			</div>
		</div>
	</c:if>
	<div class="row col-md-12">
	<hr class="red" style="margin-bottom: 20px;">
	</div>
</div>