<%@ include file="../general/taglibs.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/login/login.js" htmlEscape="true" />"></script>

<style>
	.form-horizontal .control-label {
    text-align: right;
	}
</style>

<script>
	var contextpath = "${contextpath}";
	var mostrarNSS = "${mostrarNSS}";
	var rutaController = "${rutaController}";

    (function() {
        // Obtenemos el elemento por su ID
        var correoInput = document.getElementById("correoInput");
        
        // Verificamos que el elemento exista antes de a�adir el listener
        if (correoInput) {
        	correoInput.addEventListener("blur", validarCorreo);
            console.log("Listener configurado para 'miInput'.");
        } else {
            console.error("No se encontr� el elemento 'correoInput'.");
        }
    })();
	
    function avisoPrivacidad() {
        window.open(contextpath+'/avisoPrivacidad','_blank');
    }
    
    function validarCorreo() {
        const correo = document.getElementById("correoInput").value.trim();

        //AJUSTE_REGLA Validación básica de email
        const regexCorreo = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!regexCorreo.test(correo)) {
            alert("El correo no es valido.");
            return;
        }
        

      //AJUSTE_REGLA Si es Gmail, remover mas y guion
        const dominioGmail = "@gmail.com";
        if (correo.toLowerCase().endsWith(dominioGmail)) {
            const nombre = correo.substring(0, correo.indexOf("@"));

            if (nombre.includes("-") || nombre.includes("+")) {
				document.getElementById("correoInput").value = "";
                alert("Los correos Gmail no deben contener '-' ni '+'.");
                return;
            }	
        }
    }
</script>

<input type="hidden" id="requiereActualizacion" value="${requiereActualizacion}" />


<div class="alert alert-danger" style="display: none" id="divErrorCampos">
</div>
<c:if test="${not empty fisica.errorFormGeneral}">
	<div class="alert alert-danger">
		${fisica.errorFormGeneral}
	</div>
</c:if>
<div class="alert alert-danger" style="display:none" id="erroresCaptura">
</div>
<form:form modelAttribute="fisica" id="registroAseguradoDatosBasicosForm" action="${rutaController}" cssClass="form-horizontal" method="post" role="form">
	<input type="hidden" id="enviarCorreo" name="enviarCorreo" value="${enviarCorreo}"/>
		<div>
			<div class="form-group">
				<label for="curp" class="col-md-4 col-sm-5 col-xs-12 control-label">
					<spring:message code="label.curp"/><span class="required">*</span>:
				</label>
				<div class="col-md-4 col-sm-7 col-xs-12">
					<spring:message code="label.placeholder.curp" var="placeHolderCURP"/>
					<form:input path="curp" id="registroCurp" cssClass="form-control ns_" maxlength="18" placeholder="${placeHolderCURP}"/>
					<form:errors path="curp" cssClass="error" />
				</div>
			</div>
			<div class="form-group">
				<label for="curp" class="col-md-4 col-sm-5 col-xs-12 control-label">
				</label>
				<div class="col-md-4 col-sm-7 col-xs-12">
					<spring:message code="label.consultaCurp" argumentSeparator=";" arguments="${onclick}"/>
				</div>
			</div>
			
			<c:if test="${mostrarNSS}">
				<div class="form-group">
					<label for="nss" class="col-md-4 col-sm-5 col-xs-12 control-label">
						<spring:message code="label.nss"/><span class="required">*</span>:
					</label>
					<div class="col-md-4 col-sm-7 col-xs-12">
						<spring:message code="label.placeholder.nss" var="placeHolderNSS"/>
						<form:input path="nss" id="nss" cssClass="form-control ns_" maxlength="11" placeholder="${placeHolderNSS}"/>
						<form:errors path="nss" cssClass="error" />
					</div>
				</div>
				<div class="form-group">
				<label for="curp" class="col-md-4  col-sm-5 col-xs-12 control-label">
				</label>
				<div class="col-md-4 col-sm-7 col-xs-12">
					<spring:message code="label.consultaNSS" argumentSeparator=";" arguments="${onclickNSS}"/>
				</div>
			</div>
			</c:if>

			<div class="form-group">
				<label for="correoElectronico.correo" class="col-md-4  col-sm-5 col-xs-12  control-label">
				<spring:message code="label.correo"/><span class="required">*</span>:</label>
				<div class="col-md-4 col-sm-7 col-xs-12">
					<spring:message code="label.placeholder.mail" var="placeHolderCorreo"/>
					<form:input path="correoElectronico.correo" id="correoInput" maxlength="100" cssClass="form-control ns_" placeholder="${placeHolderCorreo}" onblur="validarCorreo()"/>
					<form:errors path="correoElectronico.correo" cssClass="error" />
				</div>
			</div>

			<!-- Se utiliza el atributo correoElectronicoFiscal como auxiliar para poder realizar la confirmaciï¿½n de correo electrï¿½nico -->
			<div class="form-group">
				<label for="correoElectronicoFiscal.correo" class="col-md-4  col-sm-5 col-xs-12 control-label">
					<spring:message code="label.confirmacionCorreo"/><span class="required">*</span>:</label>
				<div class="col-md-4 col-sm-7 col-xs-12">
					<spring:message code="label.placeholder.confirmaMail" var="placeHolderConfCorreo"/>
					<form:input path="correoElectronicoFiscal.correo" id="correoConfirmacionInput" maxlength="100" cssClass="form-control ns_" placeholder="${placeHolderConfCorreo}"
					/>
					<form:errors path="correoElectronicoFiscal.correo" cssClass="error" />
					<div id="errorWrapperAux" style="display: none;">
						<span class="error hiddenElement" style="margin-left: 0px;" id="mailConfirmacionErrors"></span>
					</div>
				</div>
			</div>

			<div class="form-group">
				<label for="captcha" class="col-md-4  col-sm-5 col-xs-12 control-label" style="font-weight: normal;  ">
					<spring:message code="label.captcha"/><span class="required">*</span>: 
					<label style="font-size: small;">(<spring:message code="label.tramite.mayus.minus"></spring:message>)</label>
				</label>
				<div class="col-md-4 col-sm-7 col-xs-12">
					<div class="row">
						<div class="col-sm-7" >
							<img src="" alt="Imagen Captcha" id="captchaImg"/>
						</div>
						<div class="col-sm-1">
							<img alt="Cambiar captcha" src="${staticResourcesPath}/imagenes/refresh.png" id="refreshCaptcha"  style="cursor: pointer;" />
						</div>
						<div class="col-sm-4">
							<input type="text" name="captcha" size="15" maxlength="10" class="form-control ns_" style="width: 100%;" placeholder="<spring:message code="label.placeholder.captcha"/>"/>
						</div>
					</div>
						
						
					<input type="hidden" id="strTipoTramite" name="tramite" value="${tramite}"/>
					<form:errors path="errorFormGeneral" cssClass="error" cssStyle="display: block;" />
				</div>
			</div>
		</div>
	<br>
	<!-- Controles -->

	<div class="row" id="botonesConsultaVigencia">
		<div class="col-sm-3 text-left" style="padding: 10px;">
			<span id="labelCamposObligatoriosGeneral" class="required">*</span> <spring:message code="label.camposObligatorios"/>
		</div>
		<div class="col-sm-5 text-right">
			<button type="button" id="continuar" ${clickContinuar} class="btn btn-primary"><spring:message code="label.continuar"/></button>
		</div>
	</div>
	
</form:form>
