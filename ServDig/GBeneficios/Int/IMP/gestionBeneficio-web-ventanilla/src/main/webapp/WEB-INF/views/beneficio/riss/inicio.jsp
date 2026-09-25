<%@ include file="../../general/taglibs.jsp"%>

<script src="<spring:url value="/static/resources/js/delta/riss/inicio.js" htmlEscape="true" />"></script>

<style>
	h3.opc-RISS {
		margin-top: 0px;
	}
</style>

<div class="contenedor">
	<div>
		<div id="info-paso">
			<h3 style="font-size: 1.8em !important">Paso 1: Selección del tipo de beneficiario.</h3>
			<div class="textwidget">
				<p style="font-size: .9em;">Localice al patr&oacute;n o trabajador independiente al que desea asignar el 
				Beneficio, capturando el RFC o NSS respectivamente</p>
			</div>
		</div>
	
		<form:form modelAttribute="datosEntradaRiss"
			action="${contextpath}/alta/riss/verificar/datos"
			method="post" cssClass="form-horizontal">
			<div class="well p-md">
				<div class="row">
					<div class="col-xs-1">
						<form:radiobutton path="opcRISS" value="rissRfc"
							cssClass="opc-RISS" />
					</div>
					<div class="col-xs-5">
						<h3 class="opc-RISS">
							<spring:message code="label.titulo.riss.patron" />
						</h3>
						<div class="textwidget">
							<p>
								<spring:message code="label.desc.riss.patron" />
							</p>
						</div>
					</div>
					<div class="col-xs-6">
						<div id="rissByNrp" style="display: none;" class="form-group">
							<label class="col-sm-2 control-label">
								<span class="required">*</span>
								<spring:message code="label.riss.patron" />
							</label>
							<div class="col-sm-7">
								<form:input path="rfc" id="rfc"
									cssClass="alfanumericoEstricto upperCase form-control"
									maxlength="13" />
								<form:errors cssClass="error customError" path="rfc" />
							</div>
							<div class="col-sm-3 btnContainer">
								<button type="submit" class="btn btn-primary">INICIAR</button>
							</div>
						</div>
					</div>
				</div>	
				<hr>
				<div class="row">
					<div class="col-xs-1">
						<form:radiobutton path="opcRISS" value="rissNss"
							cssClass="opc-RISS" />
					</div>
					<div class="col-xs-5">
						<h3 class="opc-RISS">
							<spring:message code="label.titulo.riss.nss" />
						</h3>
						<div class="textwidget">
							<p style="font-size: .9em;">
								<spring:message code="label.titulo.riss.patrones" />
							</p>
							<p style="font-size: .9em;">
								<spring:message code="label.titulo.riss.trabajadores" />
							</p>
							<p style="font-size: .9em;">
								<spring:message code="label.desc.riss.nss" />
							</p>
						</div>
					</div>
					<div class="col-xs-6">
						<div id="rissByNss" style="display: none;" class="form-group">
							<label class="col-sm-2 control-label">
								<span class="required">*</span>
								<spring:message code="label.riss.nss" />
							</label>
							<div class="col-sm-7">
								<form:input path="nss" id="nss"
									cssClass="numericoSinPunto form-control" maxlength="11" />
								<form:errors cssClass="error customError" path="nss" />
							</div>
							<div class="col-sm-3 btnContainer">
								<button type="submit" class="btn btn-primary">INICIAR</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>