<%@ include file="../general/taglibs.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/login/login.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/common/terminosCondicionesActualizaCURP.js" htmlEscape="true" />"></script>

<style>	
	.form-horizontal .control-label {
    text-align: right;
	}
</style>
<script>
	var contextpath = "${contextpath}";
	var mostrarNSS = "${mostrarNSS}";
	var rutaController = "${rutaController}";
	var terminos = "${terminosActualizaCURP}";
    function avisoPrivacidad() {
        window.open(contextpath+'/avisoPrivacidad','_blank');
    }
</script>
<div class="alert alert-danger" style="display: none" id="divErrorCampos"></div>
<form:form modelAttribute="fisica" id="registroAseguradoDatosBasicosForm"
	action="${rutaController}" cssClass="form-horizontal" method="post" role="form">

		<div>
			<div class="form-group">
				<label for="curp" class="col-md-4 col-sm-5 col-xs-12 control-label">
					CURP<span class="required">*</span>:
				</label>
				<div class="col-md-4 col-sm-7 col-xs-12">
					<form:input path="curp" id="registroCurp" cssClass="form-control ns_" maxlength="18" placeholder="Ingresa tu CURP"/>
					<form:errors path="curp" cssClass="error error-custom" />
				</div>
			</div>
			<div class="form-group">
				<label for="curp" class="col-md-4 col-sm-5 col-xs-12 control-label">
				</label>
				<div class="col-md-4 col-sm-7 col-xs-12">
					&iquest;No te sabes tu CURP? <a href="https://consultas.curp.gob.mx/CurpSP/gobmx/inicio.jsp" target="_blank">Cons&uacute;ltala aqu&iacute;.</a>
				</div>
			</div>
			
			<%-- <div class="form-group">
				<label for="nss" class="col-md-4 col-sm-5 col-xs-12 control-label">
					NSS<span class="required">*</span>:
				</label>
				<div class="col-md-4 col-sm-7 col-xs-12">
					<form:input path="nss" id="registroNSS" cssClass="form-control ns_" maxlength="11" placeholder="Ingresa tu NSS"/>
					<form:errors path="nss" cssClass="error error-custom" />
				</div>
			</div>  --%>
			
			<%-- <c:if test="${mostrarNSS}"> --%>
				<div class="form-group">
					<label for="nss" class="col-md-4 col-sm-5 col-xs-12 control-label">
						NSS<span class="required">*</span>:
					</label>
					<div class="col-md-4 col-sm-7 col-xs-12">
						<form:input path="nss" id="nss" cssClass="form-control ns_" maxlength="11" placeholder="Ingresa tu NSS"/>
						<form:errors path="nss" cssClass="error error-custom" />
					</div> 
				</div>
				<div class="form-group">
				<label for="curp" class="col-md-4 col-sm-5 col-xs-12 control-label">
				</label>
				<div class="col-md-4 col-sm-7 col-xs-12">
					&iquest;No te sabes tu NSS? <a href="/gestionAsegurados-web-externo/asignacionNSS" {0} target="_blank">Cons&uacute;ltalo aqu&iacute;.</a> 
				</div>
			</div>
			<%-- </c:if> --%>

			<div class="form-group">
				<spring:message var="labelcorreo" code="label.electronico"></spring:message>
				<label for="correoElectronico.correo" class="col-md-4 col-sm-5 col-xs-12  control-label">Correo
					electr&oacute;nico<span class="required">*</span>:</label>
				<div class="col-md-4 col-sm-7 col-xs-12">
					<form:input path="correoElectronico.correo" id="correoInput" 
						maxlength="100" cssClass="form-control ns_"
						placeholder="Ingresa tu correo ${labelcorreo} personal"/>
					<form:errors path="correoElectronico.correo" cssClass="error error-custom" />
				</div>
			</div>

			<!-- Se utiliza el atributo correoElectronicoFiscal como auxiliar para poder realizar la confirmación de correo electrónico -->
			<div class="form-group">
				<label for="correoElectronicoFiscal.correo" class="col-md-4 col-sm-5 col-xs-12 control-label">
					Confirma tu correo electr&oacute;nico<span class="required">*</span>:</label>
				<div class="col-md-4 col-sm-7 col-xs-12">
					<form:input path="correoElectronicoFiscal.correo" id="correoConfirmacionInput" 
						maxlength="100" cssClass="form-control ns_" 
						placeholder="Confirma tu correo ${labelcorreo} personal"/>
					<form:errors path="correoElectronicoFiscal.correo" cssClass="error error-custom" />
					<div id="errorWrapperAux" style="display: none;">
						<span class="error hiddenElement" style="margin-left: 0px;" id="mailConfirmacionErrors"></span>
					</div>
				</div>
			</div>
			
			<div class="form-group">
				<label for="captcha" class="col-md-4 col-sm-5 col-xs-12 control-label" style="font-weight: normal;">
					Escribe las letras de la imagen que se muestra<span class="required">*</span>: </label>
				<div class="col-md-4 col-sm-7 col-xs-12">
					<div class="row">
						<div class="col-sm-7">
							<img src="" alt="Imagen Captcha" id="captchaImg" />
						</div>
						<div class="col-sm-1">
							<img alt="Cambiar captcha" src="${staticResourcesPath}/imagenes/refresh.png" id="refreshCaptcha"  style="cursor: pointer;" />
						</div>
						<div class="col-sm-4">
							<input type="text" name="captcha" size="15" maxlength="10" class="form-control ns_" style="width: 100%;" placeholder="Captura"/>
						</div>
					</div>
						
					<input type="hidden" id="strTipoTramite" name="tramite" value="${tramite}"/>
					<form:errors path="errorFormGeneral" cssClass="error" cssStyle="display: block;" />
				</div>
			</div>
	
	
	<c:if test="${terminosActualizaCURP}">	
	<div class="contenido row" style="min-height: 80px;">
		<div class="col-sm-8">
			<div id="cuestionarioContainer"></div>
			<div class="alert alert-info" style="text-align:center;">
			
					<label>
						<input id="chkTCActualizaCURP" name="aceptarTC" type="checkbox" value ="acepto" required = "required"/>
						<span>He le&iacute;do y acepto los <a id="linkTCActualizacion" href="#">T&eacute;rminos y condiciones</a></span>
					</label>
					<input type ="hidden" id ="hiddenTerminos" name ="hiddenTerminos" value ="">
			</div>
			
			<div id="contendorTerminos"style="display: none;">
				 <%@ include file="../tramite/actualizacionCurp/terminosCondicionesActualizaCURP.jsp" %> 
				
			</div>
				
			<div class="form-group">
				<div class="col-md-4 col-sm-5 col-xs-12 hide-sm"></div>
				<div class="col-md-4 col-sm-7 col-xs-12">
					<form:errors path="nombre" cssClass="error error-custom" cssStyle="display: block;" />
				</div>
			</div>
		</div>
	</div>
	</c:if>
		</div>
	<br>
	<!-- Controles -->
	<div class="row">
		<div class="col-sm-3 text-left">
			<span id="labelCamposObligatoriosGeneral" class="required">*</span>Campos obligatorios.
		</div>
		<div class="col-sm-5 text-right">
			<button type="button" class="btn btn-link" onclick="avisoPrivacidad()">Aviso de privacidad</button>
			<button type="button" id="continuar" class="btn btn-primary">Continuar</button>
		</div>
	</div>
</form:form>