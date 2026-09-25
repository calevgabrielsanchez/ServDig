<%@ include file="../../general/taglibs.jsp"%>

<style>
div.datosBusqueda label {
	display: inline;
	vertical-align: text-bottom;
	margin-right: 10px;
	font-size: 14px;
	font-weight: bold;
}

.btnContainer {
	display: block;
	float: right;
}

.upperCase {
	text-transform: uppercase;
}

</style>

<script src="<spring:url value="/static/resources/js/delta/riss/inicio.js" htmlEscape="true" />"></script>

<div class="contenedor">
	<div>
		<div id="info-paso">
		
			<h3 style="font-size: 1.8em !important">Paso 1: Alta de beneficio RISS para registros patronales.</h3>
			<div class="textwidget">
				<p style="font-size: .9em;">Localice al patr&oacute;n al que desea asignar el Beneficio.</p>
			</div>
		
			<!-- FUNCIONALIDAD PARA OTORGAR BENEFICIO A PATRON Y/O ASEGURADO -->
			<!--  <h3 style="font-size: 1.8em !important">Paso 1: Selección del tipo de beneficiario.</h3>
			<div class="textwidget">
				<p style="font-size: .9em;">Localice al patr&oacute;n o trabajador independiente al que desea asignar el 
				Beneficio, capturando el RFC o NSS respectivamente</p>
			</div>-->
		</div>
	
		<form:form modelAttribute="datosEntradaRiss"
			action="${contextpath}/altaPublica/riss/verificar/datos"
			method="post" cssClass="form-horizontal">
			
			<div class="well" style="padding: 20px 30px; margin-top: 50px;">
			
				<div class="row">
					
					<form:hidden path="opcRISS" id="opcRISS" value="rissRfc" />
					
					<div class="col-md-6">
						<h3 class="opc-RISS" style="margin-top: 0px;">
							<spring:message code="label.titulo.riss.patron" />
						</h3>
						<div class="textwidget">
							<p>
								<spring:message code="label.desc.riss.patron" />
							</p>
						</div>
					</div>
					<div class="col-md-6">
						<div id="rissByNrp" class="form-group">
							<label class="col-sm-2 control-label">
								<span class="required">*</span>
								<spring:message code="label.riss.patron" />
							</label>
							<div class="col-sm-10">
								<form:input path="rfc" id="rfc"
									cssClass="alfanumericoEstricto upperCase form-control"
									maxlength="13" />
								<form:errors cssClass="error customError" path="rfc" />
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-10 col-sm-offset-2 text-right">
								<button type="submit" class="btn btn-primary">INICIAR</button>
							</div>
						</div>
					</div>
				</div>					
			
				<!-- FUNCIONALIDAD PARA OTORGAR BENEFICIO A PATRON Y/O ASEGURADO -->
				<!--  <div class="row" style="margin-bottom: 25px;">
					<div class="col-xs-1">
						<form:radiobutton path="opcRISS" value="rissRfc"
							cssClass="opc-RISS form-control" />
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
					<div class="col-xs-6" style="margin-top: 10px;">
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
								<button type="submit" class="btn btn-default">INICIAR</button>
							</div>
						</div>
					</div>
				</div>	
				
				<div class="row">
					<div class="col-xs-1">
						<form:radiobutton path="opcRISS" value="rissNss"
							cssClass="opc-RISS form-control" />
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
					<div class="col-xs-6" style="margin-top: 20px;">
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
								<button type="submit" class="btn btn-default">INICIAR</button>
							</div>
						</div>
					</div>
				</div>
				-->
			</div>				
		</form:form>
	</div>
</div>