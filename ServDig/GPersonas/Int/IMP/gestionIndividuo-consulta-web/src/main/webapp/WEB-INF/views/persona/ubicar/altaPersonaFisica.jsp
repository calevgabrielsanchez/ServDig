<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
	
<script>
	$(function(){
		$sexo = $("[id='sexo.idSexo']");
		$lugarNacimiento = $("[id='lugarNacimiento.clave']");
		
		$sexo.attr('disabled','disabled');
		$lugarNacimiento.attr('disabled','disabled');
	});
	
</script>
	
<div class="row" id="busquedaFormContainerPF">
	<div class="col-sm-12">
		<form:form modelAttribute="fisicaForm"
			role="form" id="altaPersonaFisica" method="post"
			action="${contextoOrigen}/ubicar/persona/buscar/fisica" style="margin-top: 10px;">
			<div class="row">
				<div class="col-sm-6">
					<div class="form-group">
						<form:label path="nombre">
							Nombre(s)
						</form:label>
						<form:input path="nombre" id="nombre" maxlength="50"
							cssClass="alfanumericoNSS form-control" style="text-transform: uppercase" readonly="true"/>
					</div>
					<div class="form-group">
						<form:label path="segundoApellido">
							Segundo Apellido
						</form:label>
						<form:input path="segundoApellido" id="segundoApellido"
							maxlength="50" cssClass="alfanumericoNSS form-control" style="text-transform: uppercase" readonly="true"/>
					</div>
					<div class="form-group">
						<form:label path="fechaNacimiento">
							Fecha de Nacimiento
						</form:label>
						<form:input path="fechaNacimiento" id="fechaNacimiento"
							maxlength="50" cssClass="form-control" readonly="true"/>
					</div>
					<c:if test="${encontradoFisica == 'REGISTRO'}">
						<div class="form-group">
							<form:label path="curp">
								CURP
							</form:label>
							<form:input path="curp" id="curp" maxlength="18"
								cssClass="alfanumericoEstricto form-control" style="text-transform: uppercase"  readonly="true"/>
						</div>
					</c:if>
				</div>
				<div class="col-sm-6">
					<div class="form-group">
						<form:label path="primerApellido">
							Primer Apellido
						</form:label>
						<form:input path="primerApellido" id="primerApellido"
							maxlength="50" cssClass="alfanumericoNSS form-control" style="text-transform: uppercase" readonly="true"/>
					</div>
					<div class="form-group">
						<form:label path="sexo.idSexo">
							Sexo
						</form:label>
						<combo:creaCombo idHtml="sexo.idSexo"
							idHtmlContenedor="altaPersonaFisica"
							entidad="mx.gob.imss.ctirss.delta.persistence.DicSexo"
							idHtmlValor="${fisica.sexo.idSexo}" mostrarSoloActivos="true"
							cssClassname="form-control" />							
					</div>
					<div class="form-group">
						<form:label path="lugarNacimiento.clave">
							Lugar de Nacimiento
						</form:label>
						<combo:creaCombo idHtml="lugarNacimiento.clave"
							idHtmlContenedor="altaPersonaFisica"
							entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado"
							idHtmlValor="${fisica.lugarNacimiento.clave}"
							mostrarSoloActivos="true" cssClassname="form-control" />
					</div>
				</div>
			</div>
						
			<c:if test="${encontradoFisica == 'REGISTRO'}">
				<div class="text-right m-t-lg">
					<button
						type="button"
						id="atrasPersonaFisicaRegistro"
						class="btn btn-default">REGRESAR</button>
					<button
						type="button"
						id="continuar"
						class="btn btn-primary">REGISTRAR</button>
				</div>
			</c:if>
			
			<c:if test="${encontradoFisica == 'REGISTRO_MANUAL'}">
				<div class="text-right m-t-lg">
					<button
						type="button"
						id="atrasPersonaFisicaRegistro"
						class="btn btn-default">REGRESAR</button>
					<button
						type="button"
						id="registroManual"
						class="btn btn-primary">REGISTRAR</button>
				</div>
			</c:if>
		</form:form>
	</div>
</div>