<%@ include file="../general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="rutaActualiza" value="${contextpath}/vigencia/guardarActualizacion"/>

<c:set var="baseHost" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}/" />
<c:set var="semanasCotizadasUrl" value="${baseHost}semanascotizadas-web/" />


<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/login/login.js" htmlEscape="true" />"></script>
<!-- <script type="text/javascript" src="<spring:url value="/static/resources/js/delta/firma-digital/FirmaDigital.js" htmlEscape="true" />"></script>  -->
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
/* 	console.log(curpCapturado); */

	$(document).ready(function() {
		
		/*$("#finaliza-tramite").click(
			function() {
				// Enviar el formulario
	    	    $("#registroAseguradoDatosBasicosForm").submit();
				FirmaDigitalCtrl.cerrar();
				//$("#registroAseguradoDatosBasicosForm").submit();
			}		
		);*/
		
		/*$("#enviarForm").click(
				function() {
					
				    const captcha = $("#busqAprox").val().trim();
				    console.log('el captcha es ' , captcha);
				    if (!captcha) {
				        $("#erroresCaptura").html("Debes ingresar el texto del captcha.").show();
				        //return;
				    }else {

				    $("#erroresCaptura").hide();
				    
				    FirmaDigitalCtrl.init('firmaDigitalComponent', 'doctosRequeridosTramite');
 					console.log('entro al metodo'); 
					FirmaDigitalCtrl.setOnCloseCallback(
						function() {*/
/* 							console.log('entro a la funcion'); */
							/*if (FirmaDigitalCtrl.datosSalida && FirmaDigitalCtrl.datosSalida != null
									&& FirmaDigitalCtrl.datosSalida.Resultado == 0) {
								if (captcha){
									if (FirmaDigitalCtrl.datosSalida.curp == curpCapturado) {
										var btnFinalizaTramite = document.getElementById('finaliza-tramite');
										btnFinalizaTramite.disabled = false;
										btnFinalizaTramite.className="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only";
										btnFinalizaTramite.removeAttribute('aria-disabled');
									} else {
										$("#erroresCaptura").html(
										"Error en la validaci&oacute;n de la e-firma, favor de intentar nuevamente.").show();
									}
								}else {
									$("#erroresCaptura").html("Debes ingresar el texto del captcha.").show();
								}*/

								
/* 								console.log('datoSalida: ', FirmaDigitalCtrl.datosSalida.Resultado);
								console.log('rutaController: ', registroAseguradoDatosBasicosForm);
								console.log('curpSalida: ', FirmaDigitalCtrl.datosSalida.curp);
								console.log('CURP capturado es: ',curpCapturado); */
							
						/*}
					});
				}
			});*/
		
		/*$("#btnFinalizarSineFirma").click(function () {
			const captcha = $("#busqAprox").val().trim();

			if (!captcha) {
				$("#erroresCaptura").html("Debes ingresar el texto del captcha.").show();
				return;
			}

			$("#erroresCaptura").hide();

			$("#registroAseguradoDatosBasicosForm") 
				.attr("action", `${contextpath}/vigencia/capturarDocumentacion`)
				.attr("method", "POST")
				.submit();
		});*/

		

	});
	
    function redireccionar() {
        var origenExterno = document.getElementById('origenExterno').value;
        if (origenExterno === 'true') {
            window.location.href = '${semanasCotizadasUrl}';
        } else {
            window.location.href = '${contextpath}/vigencia';
        }
    }
    	
</script>

<input type="hidden" id="origenExterno" value="${origenExterno}" />
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
<%-- 					<input id="registroCurp" class="form-control ns_" maxlength="18" value="${fisica.curp}" readonly= "readonly"/>
 --%>					<form:errors path="curp" cssClass="error" />
					<form:input path="curp" value="${fisica.curp}" cssClass="form-control ns_" readonly= "true"/>
				</div>
			</div>
			

			<div class="form-group">
				<label for="correoElectronico.correo" class="col-md-4  col-sm-5 col-xs-12  control-label">
				<spring:message code="label.correo"/><span class="required">*</span>:</label>
				<div class="col-md-4 col-sm-7 col-xs-12">
					<spring:message code="label.placeholder.mail" var="placeHolderCorreo"/>
<%-- 					<input id="correoInput" maxlength="100" cssClass="form-control ns_" value="${fisica.correoElectronico.correo}" readonly= "readonly"/>
 --%>					<form:errors path="correoElectronico.correo" cssClass="error" />
					<form:input path="correoElectronico.correo"  cssClass="form-control ns_" value="${fisica.correoElectronico.correo}" readonly= "true"/>
				</div>
			</div>

			<!-- Se utiliza el atributo correoElectronicoFiscal como auxiliar para poder realizar la confirmacion de correo electronico -->
			<div class="form-group">
				<label for="correoElectronicoFiscal.correo" class="col-md-4  col-sm-5 col-xs-12 control-label">
					<spring:message code="label.confirmacionCorreo"/><span class="required">*</span>:</label>
				<div class="col-md-4 col-sm-7 col-xs-12">
					<spring:message code="label.placeholder.confirmaMail" var="placeHolderConfCorreo"/>
<%-- 					<input id="correoConfirmacionInput" maxlength="100" cssClass="form-control ns_" value="${fisica.correoElectronicoFiscal.correo}" readonly= "readonly"/>
 --%>					<form:errors path="correoElectronicoFiscal.correo" cssClass="error" />
					<form:input path="correoElectronicoFiscal.correo" cssClass="form-control ns_" value="${fisica.correoElectronicoFiscal.correo}" readonly= "true"/>
					<div id="errorWrapperAux" style="display: none;">
						<span class="error hiddenElement" style="margin-left: 0px;" id="mailConfirmacionErrors"></span>
					</div>
				</div>
			</div>
			
			<div class="form-group">
				<label for="busqAprox" class="col-md-4  col-sm-5 col-xs-12 control-label" style="font-weight: normal;  ">
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
						<form:input path="busqAprox" size="15" maxlength="10" cssClass="form-control ns_" style="width: 100%;"/>
						</div>
					</div>
						
					<form:errors path="errorFormGeneral" cssClass="error" cssStyle="display: block;" />
				</div>
			</div>
			
			<form:input path="nssCifrado" type="hidden" id="nssCifrado"/>
			<%-- <form:input path="busqAprox" type="hidden" id="busqAprox" /> --%>
	</div>
	
	<!-- Controles -->
	<div class="row bottom-buffer">
		<div class="col-md-4 text-left" style="padding: 10px;">
			<span class="required">*</span><spring:message code="label.camposObligatorios" />
		</div>
		<br>

		<div class="form-group">
		    <div class="col-md-12">
		        <label class="control-label">
		        <spring:message code="label.informacion.efirma"/> 
		        <a href="https://lmxi.imss.gob.mx/mslmxi-acceso/llave/login"> https://lmxi.imss.gob.mx/mslmxi-acceso/llave/login</a>.</label>
		    </div>
		</div>
		<br>

		<div class="col-md-12 text-right" style="text-align: center">
			<!--  <button type="button" id="enviarForm" class="btn btn-primary">
				<spring:message code="label.tramite.con.efirma" />
			</button>
			-->
			<!-- <button type="button" id="btnFinalizarSineFirma"
				class="btn btn-primary" role="button">
				<spring:message code="label.tramite.sin.efirma" /> 
			</button> -->
			<button type="button" class="btn btn-primary" id="regresar"
				onclick="redireccionar()">
				<spring:message code="label.btn.regresar" />
			</button>
		</div>
	</div>

	<!-- div para la forma de firma -->
<!-- 
<div id="firmaDigitalComponent"></div>
<div id="doctosRequeridosTramite"></div>
-->
	
</form:form>

