<%@ include file="../general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="rutaActualiza" value="${contextpath}/vigencia/guardarActualizacion"/>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/login/login.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="/gestionSolicitud-web/static/resources/js/delta/firma-digital/FirmaDigital.js"></script>
<script type="text/javascript" src="/portal-web/static/resources/js/delta/portal.js"htmlEscape="true" />"></script>

<style>
	.form-horizontal .control-label {
    text-align: right;
	}
</style>

<script>
	var contextpath = "${contextpath}";
	var mostrarNSS = "${mostrarNSS}";
	var rutaController = "${rutaController}";
    function avisoPrivacidad() {
        window.open(contextpath+'/avisoPrivacidad','_blank');
    }
</script>

<script type="text/javascript">

	var rutaActualiza = "${rutaActualiza}";

	$(document).ready(function() {
		
		FirmaDigitalCtrl.init('firmaDigitalComponent', 'doctosRequeridosTramite');
		
		$("#regresarPantallaInicial").click(redireccionPaginaPrincipal);
		
		$("#enviarForm").click(
				function() {
					console.log('entro al metodo');
					FirmaDigitalCtrl.setOnCloseCallback(
						function() {
							console.log('entro a la funcion');
							if(FirmaDigitalCtrl.datosSalida && FirmaDigitalCtrl.datosSalida != null
									&& FirmaDigitalCtrl.datosSalida.Resultado == 0) {
									$("#registroAseguradoDatosBasicosForm").submit();
									console.log('datoSalida: ', FirmaDigitalCtrl.datosSalida.Resultado);
									console.log('rutaController: ', registroAseguradoDatosBasicosForm);
									console.log('curpSalida: ', FirmaDigitalCtrl.datosSalida.curp);
							} else {
								var mensajeError = "La validaci\u00f3n de la autenticaci\u00f3n no pudo ser realizada";
								if(FirmaDigitalCtrl.datosSalida.texto) {
								 mensajeError = "["+	FirmaDigitalCtrl.datosSalida.Resultado + "] " + FirmaDigitalCtrl.datosSalida.texto;
								}
								$('#dialog-mensajes').text(mensajeError);
								dialogMensajes.dialog('open');
							}
						}
					);
				}
			);
	});
	
	
</script>

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
				<label for="correoElectronico.correo" class="col-md-4  col-sm-5 col-xs-12  control-label">
				<spring:message code="label.correo"/><span class="required">*</span>:</label>
				<div class="col-md-4 col-sm-7 col-xs-12">
					<spring:message code="label.placeholder.mail" var="placeHolderCorreo"/>
					<form:input path="correoElectronico.correo" id="correoInput" maxlength="100" cssClass="form-control ns_" placeholder="${placeHolderCorreo}"/>
					<form:errors path="correoElectronico.correo" cssClass="error" />
				</div>
			</div>

			<!-- Se utiliza el atributo correoElectronicoFiscal como auxiliar para poder realizar la confirmación de correo electrónico -->
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

		</div>
	<br>
	<!-- Controles -->
	<div class="row">
		<div class="col-sm-3 text-left" style="padding: 10px;">
			<span id="labelCamposObligatoriosGeneral" class="required">*</span> <spring:message code="label.camposObligatorios"/>
		</div>
	</div> 
	<div class="row">
		<div class="col-sm-5 text-left" >
			<button type="button" id="enviarForm" class="btn btn-primary" style="width:450px">
				Finalizar tramite con e.firma
			</button>
		</div>
		 <div class="col-sm-5 text-right">
			<button type="button" id="btnFinalizarSineFirma" class="btn btn-primary" role="button" onclick="window.location.href = '${contextpath}/vigencia/capturarDocumentacion'">Finalizar tramite sin e.firma</button>
		</div>
	</div>
	
<!-- div para la forma de firma -->
<div id="firmaDigitalComponent"></div>
<div id="doctosRequeridosTramite"></div>
	
</form:form>