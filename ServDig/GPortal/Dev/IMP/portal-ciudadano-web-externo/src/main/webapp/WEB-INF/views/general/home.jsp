<%@ include file="../general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="tipoTramiteModifDatosGrales" value="<%=TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES.getCodigo()%>" />

<script type="text/javascript">
			history.go(1);
</script>

<script type="text/javascript" src="${staticResourcesPath}/js/delta/home/home.js"></script>
<script type="text/javascript"
	 src="${staticResourcesPath}/js/delta/common/cartaTerminosCtrl.js"></script>

<script>
	var contextpath="${contextpath}";
	var tramiteModifDatosGrales = ${tipoTramiteModifDatosGrales};
</script>

<style>
	span.error-custom {
		float: none !important;
		vertical-align: super;
	}
	
	.filtros-busqueda .row {
		margin-bottom: 12px;
	}
	
	.filtros-busqueda .filtros .etiqueta {
		width: 25%;
	}
	
	input[type="text"] {
		margin-bottom: 0px;
	}
	
	.divLabelObligatorio {
		padding: 10px;
	}
	
	.campoObligatorio {
		border: 1px solid red;
	}
	
	.form-horizontal .control-label {
    text-align: right;
	}
</style>

<div class="contenedor">
	<c:choose>
		<c:when test="${not empty fisica.errorFormGeneral }">
			<div class="alert alert-danger">
				<strong>Error: </strong>${fisica.errorFormGeneral}
			</div>
		</c:when>
		<c:otherwise>
			<div id="info-paso" style="margin-top: 0px;">
				<h3>Ingresar tr&aacute;mites b&aacute;sicos en l&iacute;nea</h3>
				<hr class="red"  style="margin-bottom: 20px;">
			</div>
			
			<div class="alert alert-danger" style="display: none" id="divErrorCampos"></div>
			

			<!-- Muesta los mensajes de error, este error es cuando encontro mas de un error -->
			<c:if test="${errorDatosExistentes != null }">
				<div style="width: 500px;" align="center">
					<div class="ui-widget">
						<div style="margin-top: 20px; padding: 0 .7em;" class="ui-state-highlight ui-corner-all">
							<p>
								<span style="float: left; margin-right: .3em;" class="ui-icon ui-icon-info"></span>
								${mensaje}
							</p>
						</div>
					</div>
				</div>
			</c:if>

			<!-- Forma de la consulta de personas por datos basicos. -->
			<div class="row">
			<div class="col-md-8">
				<form:form modelAttribute="fisica" id="registroAseguradoDatosBasicosForm" action="${contextpath}/home/validar"
					cssClass="form-horizontal" method="post" role="form">
					<div class="form-group">
						<label for="curp" class="col-sm-6 control-label">
								CURP<span class="required">*</span>:
						</label>
						<div class="col-sm-6">
							<form:input path="curp" id="registroCurp" cssClass="form-control" maxlength="18" />
							<form:errors path="curp" cssClass="error error-custom" />
						</div>
					</div>

					<div class="form-group">
						<label for="correoElectronico.correo" class="col-sm-6 control-label">
							Correo electr&oacute;nico<span class="required">*</span>:
						</label>
						<div class="col-sm-6">
							<form:input path="correoElectronico.correo" id="correoInput" maxlength="100" cssClass="form-control" />
							<form:errors path="correoElectronico.correo" cssClass="error error-custom" />
						</div>
					</div>

					<!-- Se utiliza el atributo correoElectronicoFiscal como auxiliar para poder realizar la confirmaci�n de correo electr�nico -->
					<div class="form-group">
						<label for="correoElectronicoFiscal.correo" class="col-sm-6 control-label">
							Confirma tu correo electr&oacute;nico<span class="required">*</span>:
						</label>
						<div class="col-sm-6">
							<form:input path="correoElectronicoFiscal.correo" id="correoConfirmacionInput" maxlength="100" cssClass="form-control" />
							<form:errors path="correoElectronicoFiscal.correo" cssClass="error error-custom" />
							<div id="errorWrapperAux" style="display: none;">
								<span class="error hiddenElement" style="margin-left: 0px;" id="mailConfirmacionErrors"></span>
							</div>
						</div>
					</div>

					<div class="form-group">
						<label for="captcha" class="col-sm-6 control-label">
							Captura los caracteres de la imagen<span class="required">*</span>:
							<label style="font-size: xx-small; font-style: italic;">(Distingue entre may&uacute;sculas y
								min&uacute;sculas)</label>
						</label>
						<input type="hidden" id="terminos" name="terminos" value='false' />
								
						<div class="col-sm-6">
							<div class="row">
								<div class="col-sm-7">
									<img src="" alt="Imagen Captcha" id="captchaImg" /> 
								</div>
								<div class="col-sm-1">
									<img alt="Cambiar captcha" src="${staticResourcesPath}/imagenes/refresh.png"
											id="refreshCaptcha"  style="cursor: pointer;" />
								</div>
								<div class="col-sm-4">
									<input type="text" id="strCaptcha" name="captcha" size="15" maxlength="10" class="form-control"
										 style="width: 100%;"  />
								</div>
							</div>
									
							<form:errors path="errorFormGeneral" cssClass="error error-custom" cssStyle="display: block;" />
						</div>
					</div>
						
					<div class="row m-t-lg">
						<div class="col-xs-6 divLabelObligatorio">
							<span class="required" id="labelCamposObligatoriosGeneral">*</span> Campos obligatorios
						</div>
						<div class="col-xs-6 text-right">
							<button type="button" id="limpiar" class="btn btn-default">Limpiar</button>
						<button type="button" id="buscar" class="btn btn-primary">Buscar</button>
						</div>
					</div>
				</form:form>
			</div>
			</div>
		</c:otherwise>
	</c:choose>
</div>

<div id="divCartaTerminos"></div>
