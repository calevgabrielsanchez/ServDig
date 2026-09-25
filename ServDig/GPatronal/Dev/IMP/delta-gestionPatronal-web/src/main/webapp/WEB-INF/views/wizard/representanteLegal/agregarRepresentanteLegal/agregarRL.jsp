<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/wizard/agregarRepresentanteLegal/agregarRL.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/procesaErrores.js" htmlEscape="true" />"></script>
<script type="text/javascript" 
	src="<spring:url value="/static/resources/js/delta/afiliacion/common/commonMethods.js" htmlEscape="true" />"></script>	
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/wizard/agregarRepresentanteLegal/commonRL.js" htmlEscape="true" />"></script>
	
<c:set var="contextpath" value="<%=request.getContextPath()%>" />


<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12">

			<c:if test="${not empty errorFormGeneral}">
				<div class="container-fluid empty-state">
					<div class="row">
						<div class="alert alert-danger">${errorFormGeneral}</div>
					</div>
				</div>
			</c:if>


			<c:choose>
				<c:when test="${!procesado}">

					<!-- PANTALLA LOCALIZAR REPRESENTANTE LEGAL -->
					<div id="errorNegocio"></div>

					<div>
						<h3 align="center">Proporcione el RFC del Representante Legal</h3>

						<form:form modelAttribute="busquedaPersona" id="busquedaPersona" method="post" cssClass="form-horizontal m-t-lg"
							role="form" action="${contextpath}/wizard/alta/representeLegal/localizar">

							<input type="hidden" name="tipoPersona.idTipoPersona" id="tipoPersona.idTipoPersona" value="1" />
							<input type="hidden" name="tipoPoder.idTipoPoder" id="tipoPoder.idTipoPoder" />

							<div class="form-group">
								<label for="rfc" class="col-sm-3 control-label">
									<span class="required">*</span>
									RFC:
								</label>
								<div class="col-sm-6">
									<form:input path="rfc" maxlength="13" cssClass="form-control" />
									<span id="rfcError" class="error hiddenElement"></span>
								</div>
							</div>
							<div class="form-group">
								<label for="curp" class="col-sm-3 control-label">
									<span class="required">*</span>
									CURP:
								</label>
								<div class="col-sm-6">
									<form:input path="curp" maxlength="18" cssClass="form-control" />
									<span id="curpError" class="error hiddenElement"></span>
								</div>
							</div>
							<div class="form-group">
								<label for="tipoPoder" class="col-sm-3 control-label">
									<span class="required">*</span>
									Tipo de Poder:
								</label>
								<div class="col-sm-6">
									<combo:creaCombo idHtml="idTipoPoder" idHtmlContenedor="busquedaPersona"
										entidad="mx.gob.imss.ctirss.delta.persistence.DicTipoPoder" idHtmlValor="${persona.tipoPoder.idTipoPoder}"
										mostrarSoloActivos="true" cssClassname="form-control" />
									<span id="idTipoPoderError" class="error hiddenElement"></span>
								</div>
							</div>
					</div>
					</form:form>	
				</c:when>

				<c:otherwise>
					<!-- FIN PROCESAMIENTO  -->
					<c:if test="${empty errorFormGeneral}">
						<div class="alert alert-success">Alta de Representante Legal finalizada.</div>
		
						<input type="hidden" id="rlIdPersona" value="${repLegal.personaFisica.idPersona}" />
						<input type="hidden" id="rlRfc" value="${repLegal.personaFisica.rfc}" />
						<input type="hidden" id="rlNombre" value="${repLegal.personaFisica.nombre}" />
						<input type="hidden" id="rlAPaterno" value="${repLegal.personaFisica.primerApellido}" />
						<input type="hidden" id="rlAMaterno" value="${repLegal.personaFisica.segundoApellido}" />
						<input type="hidden" id="rlCurp" value="${repLegal.personaFisica.curp}" />
		
						<div class="col-md-4">
							<fieldset>
								<legend>Datos del Representante Legal</legend>
								<address>
									<span>
										<strong> RFC </strong>
									</span>
									<br>
									<span> ${repLegal.personaFisica.rfc} </span>
									<br>
									<span>
										<strong> CURP </strong>
									</span>
									<br>
									<span> ${repLegal.personaFisica.curp}</span>
									<br>
									<span>
										<strong> Nombre </strong>
									</span>
									<br>
									<span> ${repLegal.personaFisica.nombre}</span>
									<br>
									<span>
										<strong> Primer Apellido </strong>
									</span>
									<br>
									<span> ${repLegal.personaFisica.primerApellido}</span>
									<br>
									<span>
										<strong> Segundo Apellido </strong>
									</span>
									<br>
									<span> ${repLegal.personaFisica.segundoApellido}</span>
									<br>
		
								</address>
							</fieldset>
						</div>
					</c:if>
				</c:otherwise>
			</c:choose>
		
		</div>
	</div>

	<div class="pie col-sm-12">
		<div class="pie row">
			<div class="opciones col-sm-6"></div>
			<div class="controles col-sm-6 text-right">
				<c:choose>
					<c:when test="${!procesado}">
						<button id="cerrarWizard" class="btn btn-default">Cerrar</button>
						<button id="localizarRL" class="btn btn-primary">Aceptar</button>
					</c:when>
					<c:otherwise>
						<button id="concluirWizard" class="btn btn-default">Cerrar</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
	
</div>