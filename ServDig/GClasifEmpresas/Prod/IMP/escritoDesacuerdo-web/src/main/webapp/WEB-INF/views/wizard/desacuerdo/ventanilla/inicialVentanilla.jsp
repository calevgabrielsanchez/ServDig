<%@ include file="../../../general/taglibs.jsp" %>

<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/messages_es.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/desacuerdo/inicialVentanilla.js" htmlEscape="true" />"></script>

<div class="row">
	<div class="col-sm-12">
		<jsp:include page="pasosEscritoDesacuerdo.jsp">
			<jsp:param value="1" name="paso"/>
		</jsp:include>
		<c:if test="${not empty error}">
			<div class="alert alert-danger">${error}</div>
		</c:if>
		<div class="row">
			<div class="col-sm-7">
				<p style="text-align: justify;">
				Por favor captura el N&uacute;mero de Registro Patronal (NRP) al que le quieres registrar un escrito de desacuerdo,
				 una vez capturado da clic en el bot&oacute;n &quot;Iniciar tr&aacute;mite&quot;.
				</p>
				
				<form:form action="#" id="formBusquedaRP" cssClass="form form-horizontal" method="post" modelAttribute="patron">
					<div class="form-group">
						<label class="col-sm-6">N&uacute;mero de Registro Patronal*:</label>
						<div class="col-sm-6">
							<form:input path="nrp" cssClass="form-control" maxlength="10" />
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-6" style="padding-top:10px">
							* Campos obligatorios.
						</div>
						<div class="col-sm-6 text-right">
							<button type="button" class="btn btn-default" id="regresarEscrito">Regresar</button>
							<button type="button" class="btn btn-primary" id="iniciarEscrito">Iniciar tr&aacute;mite</button>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>