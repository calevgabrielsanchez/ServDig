<%@ include file="../general/taglibs.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/login/login.js" htmlEscape="true" />"></script>

<script>
	var contextpath = "${contextpath}";
	var rutaController = "${rutaController}";
    function avisoPrivacidad() {
        window.open(contextpath+'/avisoPrivacidad','_blank');
    }
</script>
<div class="alert alert-danger" style="display: none" id="divErrorCampos">
</div>
<c:if test="${not empty fisica.errorFormGeneral}">
	<div class="alert alert-danger">
		${fisica.errorFormGeneral}
	</div>
</c:if>
<form:form modelAttribute="fisica" id="registroCorrecionDatosCurpForm" action="${rutaController}" cssClass="form-horizontal" method="post" role="form">
	<form:errors cssClass="alert alert-danger" element="div"/>

		<div>
			<div class="form-group">
				<label for="curp" class="col-md-4 col-sm-5 col-xs-12 control-label" style="text-align: left;">
					<spring:message code="label.solicitud.curp"/><span class="required">*</span>:
				</label>
				<div class="col-md-4 col-sm-7 col-xs-12">
					<spring:message code="label.placeholder.curp" var="placeHolderCURP"/>
					<form:input path="curp" id="registroCurp" cssClass="form-control" maxlength="18" placeholder="${placeHolderCURP}"/>
					<form:errors path="curp" cssClass="error" />
				</div>
			</div>
			<div class="form-group">
				<label for="curp" class="col-md-4 col-sm-5 col-xs-12 control-label">
				</label>
				<div class="col-md-4 col-sm-7 col-xs-12">
					<spring:message code="label.consultaCurp"/>
				</div>
			</div>
			
			<div class="form-group">
				<label for="correoElectronico.correo" class="col-md-4  col-sm-5 col-xs-12  control-label" style="text-align: left;">
				<spring:message code="label.solicitud.correo"/><span class="required">*</span>:</label>
				<div class="col-md-4 col-sm-7 col-xs-12">
					<spring:message code="label.placeholder.mail" var="placeHolderCorreo"/>
					<form:input path="correoElectronico.correo" id="correoInput" maxlength="100" cssClass="form-control" placeholder="${placeHolderCorreo}"/>
					<form:errors path="correoElectronico.correo" cssClass="error" />
				</div>
			</div>

			<!-- Se utiliza el atributo correoElectronicoFiscal como auxiliar para poder realizar la confirmación de correo electrónico -->
			<div class="form-group">
				<label for="correoElectronicoFiscal.correo" class="col-md-4  col-sm-5 col-xs-12 control-label" style="text-align: left;">
					<spring:message code="label.solicitud.confirmacionCorreo"/><span class="required">*</span>:</label>
				<div class="col-md-4 col-sm-7 col-xs-12">
					<spring:message code="label.placeholder.confirmaMail" var="placeHolderConfCorreo"/>
					<form:input path="correoElectronicoFiscal.correo" id="correoConfirmacionInput" maxlength="100" cssClass="form-control" placeholder="${placeHolderConfCorreo}"
					/>
					<form:errors path="correoElectronicoFiscal.correo" cssClass="error" />
					<div id="errorWrapperAux" style="display: none;">
						<span class="error hiddenElement" style="margin-left: 0px;" id="mailConfirmacionErrors"></span>
					</div>
				</div>
			</div>

			<div class="form-group">
				<label for="captcha" class="col-md-4  col-sm-5 col-xs-12 control-label" style="font-weight: normal; text-align: left;">
					<spring:message code="label.solicitud.captcha"/><span class="required">*</span>: 
					<label style="font-size: small; font-style: italic;">(Usa mayúsculas y minúsculas)</label></label>
				<div class="col-md-4 col-sm-7 col-xs-12">
					<div class="row">
						<div class="col-sm-7">
							<img src="" alt="Imagen Captcha" id="captchaImg" /> 
						</div>
						<div class="col-sm-1">
							<img alt="Cambiar captcha" src="${staticResourcesPath}/imagenes/refresh.png" id="refreshCaptcha"  style="cursor: pointer;" />
						</div>
						<div class="col-sm-4">
							<input type="text" name="captcha" size="15" maxlength="10" class="form-control" style="width: 100%;" placeholder="<spring:message code="label.placeholder.captcha"/>"/>
						</div>
					</div>
						
					<input type="hidden" id="strTipoTramite" name="tramite" value="${tramite}"/>
					<form:errors path="errorFormGeneral" cssClass="error" cssStyle="display: block;" />
				</div>
			</div>
		</div>
	<br>
	<!-- Controles -->
	<div class="row">
		<div class="col-sm-3 text-left" style="padding: 10px;">
			<span id="labelCamposObligatoriosGeneral" class="required">*</span> <spring:message code="label.solicitud.camposObligatorios"/>
		</div>
		<div class="col-sm-5 text-right">
			<button type="button" id="continuar" class="btn btn-primary"><spring:message code="label.continuar"/></button>
		</div>
	</div>
</form:form>