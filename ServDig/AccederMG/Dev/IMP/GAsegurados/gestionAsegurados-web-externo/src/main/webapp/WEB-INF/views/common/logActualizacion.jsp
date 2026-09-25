<%@ include file="../general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="rutaActualiza" value="${contextpath}/vigencia/guardarActualizacion"/>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/login/login.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/firma-digital/FirmaDigital.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="/portal-web/static/resources/js/delta/portal.js" />

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
	var curpCapturado = "${fisica.curp}";
	console.log(curpCapturado);

	$(document).ready(function() {
		
		FirmaDigitalCtrl.init('firmaDigitalComponent', 'doctosRequeridosTramite');
		
		$("#finaliza-tramite").click(
			function() {
				FirmaDigitalCtrl.cerrar();
				$("#registroAseguradoDatosBasicosForm").submit();
			}		
		);
		
		$("#enviarForm").click(
				function() {
					console.log('entro al metodo');
					FirmaDigitalCtrl.setOnCloseCallback(
						function() {
							console.log('entro a la funcion');
							if (FirmaDigitalCtrl.datosSalida && FirmaDigitalCtrl.datosSalida != null
									&& FirmaDigitalCtrl.datosSalida.Resultado == 0) {
								
								if (FirmaDigitalCtrl.datosSalida.curp == curpCapturado) {
									var btnFinalizaTramite = document.getElementById('finaliza-tramite');
									btnFinalizaTramite.disabled = false;
									btnFinalizaTramite.className="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only";
									btnFinalizaTramite.removeAttribute('aria-disabled');
								} else {
									$("#erroresCaptura").html(
									"La CURP ingresada no corresponde a la CURP de su e-firma, favor de validar e intentar nuevamente.").show();
								}
								
								console.log('datoSalida: ', FirmaDigitalCtrl.datosSalida.Resultado);
								console.log('rutaController: ', registroAseguradoDatosBasicosForm);
								console.log('curpSalida: ', FirmaDigitalCtrl.datosSalida.curp);
								console.log('CURP capturado es: ',curpCapturado);
							} 
						}
					);
				}
			);
		

	});

	
</script>

<input type="hidden" id="curpCapturado" value="${fisica.curp}" />

<div class="alert alert-danger" style="display: none" id="divErrorCampos">
</div>
<c:if test="${not empty fisica.errorFormGeneral}">
	<div class="alert alert-danger">
		${fisica.errorFormGeneral}
	</div>
</c:if>


<div class="alert alert-danger" style="display: none" id="erroresCaptura">
</div>


<form:form modelAttribute="fisica" id="registroAseguradoDatosBasicosForm" action="${rutaController}" cssClass="form-horizontal" method="post" role="form">

		<div>
			<div class="form-group">
				<label for="curp" class="col-md-4 col-sm-5 col-xs-12 control-label">
					<spring:message code="label.curp"/><span class="required">*</span>:
				</label>
				<div class="col-md-4 col-sm-7 col-xs-12">
					<spring:message code="label.placeholder.curp" var="placeHolderCURP"/>
					<input id="registroCurp" class="form-control ns_" maxlength="18" value="${fisica.curp}" readonly= "readonly"/>
					<form:errors path="curp" cssClass="error" />
					<form:input path="curp" value="${fisica.curp}" type="hidden"/>
				</div>
			</div>
			

			<div class="form-group">
				<label for="correoElectronico.correo" class="col-md-4  col-sm-5 col-xs-12  control-label">
				<spring:message code="label.correo"/><span class="required">*</span>:</label>
				<div class="col-md-4 col-sm-7 col-xs-12">
					<spring:message code="label.placeholder.mail" var="placeHolderCorreo"/>
					<input id="correoInput" maxlength="100" style="width: 38ch; font-size: 16px" cssClass="form-control ns_" value="${fisica.correoElectronico.correo}" readonly= "readonly"/>
					<form:errors path="correoElectronico.correo" cssClass="error" />
					<form:input path="correoElectronico.correo" value="${fisica.correoElectronico.correo}" type="hidden"/>
				</div>
			</div>

			<!-- Se utiliza el atributo correoElectronicoFiscal como auxiliar para poder realizar la confirmación de correo electrónico -->
			<div class="form-group">
				<label for="correoElectronicoFiscal.correo" class="col-md-4  col-sm-5 col-xs-12 control-label">
					<spring:message code="label.confirmacionCorreo"/><span class="required">*</span>:</label>
				<div class="col-md-4 col-sm-7 col-xs-12">
					<spring:message code="label.placeholder.confirmaMail" var="placeHolderConfCorreo"/>
					<input id="correoConfirmacionInput" maxlength="100" style="width: 38ch; font-size: 16px" cssClass="form-control ns_" value="${fisica.correoElectronicoFiscal.correo}" readonly= "readonly"/>
					<form:errors path="correoElectronicoFiscal.correo" cssClass="error" />
					<form:input path="correoElectronicoFiscal.correo" value="${fisica.correoElectronicoFiscal.correo}" type="hidden"/>
					<div id="errorWrapperAux" style="display: none;">
						<span class="error hiddenElement" style="margin-left: 0px;" id="mailConfirmacionErrors"></span>
					</div>
				</div>
			</div>

		</div>
	<br>
	
	<!-- Controles -->
	<div class="row bottom-buffer">
		<div class="col-md-4 text-left" style="padding: 10px;">
			<span class="required">*</span><spring:message code="label.camposObligatorios" />
		</div>
		<div class="col-md-12 text-right" style="text-align: center">
			<button type="button" id="enviarForm" class="btn btn-primary">
				<spring:message code="label.tramite.con.efirma" />
			</button> 
			<button type="submit" id="btnFinalizarSineFirma"
					class="btn btn-primary" role="button"
					formaction="${contextpath}/vigencia/capturarDocumentacion"
					formmethod="POST">
					<spring:message code="label.tramite.sin.efirma" />
			</button>
			<button type="button" class="btn btn-primary" id="regresar" onclick="window.location.href = '${contextpath}/vigencia'">
				<spring:message code="label.btn.regresar" />
			</button>
		</div>
	</div>

	<!-- div para la forma de firma -->
<div id="firmaDigitalComponent"></div>
<div id="doctosRequeridosTramite"></div>

	
</form:form>