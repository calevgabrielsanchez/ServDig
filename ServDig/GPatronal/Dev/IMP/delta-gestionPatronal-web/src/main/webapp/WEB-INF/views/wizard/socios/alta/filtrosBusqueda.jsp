<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/wizard/socios/alta/filtrosBusqueda.js" htmlEscape="true" />"></script>

<style>
	.upperCase {
		text-transform: uppercase;
	}

</style>


	<div class="contenedor col-sm-12">
		<div class="contenido row">
			<div class="col-sm-12">

				<div class="alert alert-info">
					Seleccione el tipo de socio y capture la informaci&oacute;n
						que se solicita:
				</div>

				<div>
					<c:if test="${not empty error}">
						<div class="container-fluid empty-state">
							<div class="row">
								<div class="alert alert-danger">${error}</div>
							</div>
						</div>
					</c:if>

					<form:form modelAttribute="socio" id="socio" method="post"
						cssClass="form-horizontal" role="form"
						action="${contextpath}/wizard/tramite/socios/contenidoRegistro"
						cssStyle="width: 65%; margin: 25px auto 30px;">

						<form:hidden path="idPersonaMoralPatron" id="idPersonaMoralPatron" />
						<form:hidden path="rfcPersonaMoralPatron"
							id="rfcPersonaMoralPatron" />
						<input type="hidden" id="tipoSocio.idTipoPersona"
							value="${socio.tipoSocio.idTipoPersona}" />

						<!-- TIPO SOCIO -->
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-3 control-label"><span
								class="required">*</span>Tipo socio:</label>
							<div class="col-sm-9">
								<select name="tipoSocio.idTipoPersona"
									id="tipoSocio.idTipoPersona" class="form-control">
									<option value="">-- Por favor seleccione --</option>
									<c:choose>
										<c:when test="${socio.tipoSocio.idTipoPersona==1}">
											<option value="1" selected="selected">Fisica</option>
											<script>
												$(function() {
													$('div#seccionRfc').show();
													$('div#seccionCurp').show();
												});
											</script>
										</c:when>
										<c:otherwise>
											<option value="1">Fisica</option>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${socio.tipoSocio.idTipoPersona==2}">
											<option value="2" selected="selected">Moral</option>
											<script>
												$(function() {
													$('div#seccionRfc').show();
													$('div#seccionCurp').hide();
													$("#curp").val('');
												});
											</script>
										</c:when>
										<c:otherwise>
											<option value="2">Moral</option>
										</c:otherwise>
									</c:choose>
								</select> <span id="tipoSocio.idTipoPersonaError"
									class="error hiddenElement"></span>
							</div>

						</div>
						<!-- RFC -->
						<div class="form-group">
							<div id="seccionRfc" style="display: none;">
								<label for="rfc" class="col-sm-3 control-label"><span
									class="required">*</span>RFC:</label>
								<div class="col-sm-9">
									<form:input path="rfc" maxlength="13"
										cssClass="form-control alfanumericoSemiEstricto upperCase" />
									<span id="rfcError" class="error hiddenElement"></span>
								</div>
							</div>
						</div>
						<!-- CURP -->
						<div class="form-group">
							<div id="seccionCurp" style="display: none;">
								<label for="curp" class="col-sm-3 control-label"><span
									class="required">*</span>CURP:</label>
								<div class="col-sm-9">
									<form:input path="curp" maxlength="18"
										cssClass="form-control alfanumericoSemiEstricto upperCase" />
									<span id="curpError" class="error hiddenElement"></span>
								</div>
							</div>
						</div>

					</form:form>

				</div>

			</div>
		</div>

	<div class="pie row">
		<div class="opciones col-sm-6"></div>
		<div class="controles col-sm-6">
			<div class="pull-right">
				<button class="btn btn-default" id="cerrarWizard">Cerrar</button>
				<a id="siguienteBusqueda" class="btn btn-primary"> Buscar</a>
			</div>
		</div>
	</div>
</div>


