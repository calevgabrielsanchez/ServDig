<%@ include file="../general/taglibs.jsp"%>
<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/tramite/tramite-capturaDatosBasicos.js" htmlEscape="true" />"></script>

<div class="contenedor">
	<c:choose>
		<c:when test="${not empty fisica.errorFormGeneral }">
			<div class="alert alert-danger">
				<strong>Error: </strong>${fisica.errorFormGeneral}
			</div>
		</c:when>
		<c:otherwise>

			<div id="info-paso" style="margin-bottom: 50px;">
				<h3>Paso 1: Consulta de personas
					f&iacute;sicas por CURP</h3>
				<hr class="red" style="margin-bottom: 20px;">

				<div class="alert alert-info" style="text-align: justify;">A
					continuaci&oacute;n es necesario que capture su CURP, la cual ser&aacute;
					utilizada para localizar o generar un nuevo N&uacute;mero de
					Seguridad Social, tambi&eacute;n es necesario un correo electr&oacute;nico
					para que le sea enviado el N&uacute;mero de Seguridad Social
					recuperado o generado.</div>
				
				<div class="alert alert-danger" style="display: none" id="divErrorCampos"></div>

			</div>

			<!-- Muesta los mensajes de error, este error es cuando encontro mas de un error -->
			<c:if test="${errorDatosExistentes != null }">
				<div style="width: 500px;" align="center">
					<div class="ui-widget">
						<div style="margin-top: 20px; padding: 0 .7em;"
							class="ui-state-highlight ui-corner-all">
							<p>
								<span style="float: left; margin-right: .3em;"
									class="ui-icon ui-icon-info"></span> ${mensaje}
							</p>
						</div>
					</div>
				</div>
			</c:if>

			<!-- Forma de la consulta de personas por datos basicos. -->
			<div class="row">
			<div class="col-md-8">
				<form:form modelAttribute="fisica"
					id="registroAseguradoDatosBasicosForm"
					action="${contextpath}/tramite/consultaDatosBasicos" method="post"
					cssClass="form-horizontal" role="form">

					<div class="form-group">
						<label for="curp" class="col-sm-6 control-label">
						CURP<span class="required">*</span>:</label>
						<div class="col-sm-6">
							<form:input path="curp" id="registroCurp"
								cssClass="form-control" maxlength="18" />
							<form:errors path="curp" cssClass="error" />
						</div>
					</div>
					
					<div class="form-group">
						<label for="correoInput" class="col-sm-6 control-label">
						Correo electr&oacute;nico<span class="required">*</span>: 
						</label>
						<div class="col-sm-6">
							<form:input path="correoElectronico.correo" id="correoInput"
								cssClass="form-control" maxlength="100" />
							<form:errors path="correoElectronico.correo"
								cssClass="error" />
						</div>
					</div>
					
					<!-- Se utiliza el atributo correoElectronicoFiscal como auxiliar para poder realizar
						la confirmaciÛn de correo electrÛnico -->
					<div class="form-group">
						<label for="correoConfirmacionInput" class="col-sm-6 control-label text-left">
						Confirme su correo electr&oacute;nico<span class="required">*</span>: 
						</label>
						<div class="col-sm-6">
							<form:input path="correoElectronicoFiscal.correo"
								id="correoConfirmacionInput" cssClass="form-control" 
								maxlength="100" />
							<form:errors path="correoElectronicoFiscal.correo"
								cssClass="error" />
							<div id="errorWrapperAux" style="display: none;">
								<span class="error hiddenElement" style="margin-left: 0px;"
									id="mailConfirmacionErrors"></span>
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<label for="captcha" class="col-sm-6 control-label text-left">
							Capture los caracteres de la imagen<span class="required">*</span>:
							<label style="font-size: xx-small; font-style: italic;">(Distingue
							entre may&uacute;sculas y min&uacute;sculas)</label>
						</label> 
						<div class="col-sm-6">
							<div class="row">
								<div class="col-sm-7">
									<img src="" alt="Imagen Captcha" id="captchaImg" /> 
								</div>
								<div class="col-sm-1">
									<img alt="Cambiar captcha"
									src="${staticResourcesPath}/imagenes/refresh.png"
									id="refreshCaptcha"  style="cursor: pointer;" />
								</div>
								<div class="col-sm-4">
									<input type="text" name="captcha" size="15" maxlength="10" class="form-control" style="width: 100%;" />
								</div>
							</div>
							
							<form:errors path="errorFormGeneral"
								cssClass="error" cssStyle="display: block;" />
						</div>
					</div>
					
					<!-- Controles -->
					<div class="row m-t-lg">
						<div class="col-xs-6 divLabelObligatorio">
							<span class="required"  id="labelCamposObligatoriosGeneral">*</span> Campos obligatorios
						</div>
						<div class="col-xs-6 text-right">
							<button type="button" id="limpiar" class="btn btn-default">Limpiar</button>
							<button type="submit" id="buscar" class="btn btn-primary">Buscar</button>
						</div>
					</div>
				</form:form>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</div>
