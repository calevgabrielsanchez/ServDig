<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.domicilio.TipoBusquedaVialidadEnum"%>
<script>	
	var TIPO_BUSQUEDA_VIALIDAD = <%=TipoBusquedaVialidadEnum.VIALIDAD.getCodigo()%>;
	var TIPO_BUSQUEDA_VIALIDAD_NL = <%=TipoBusquedaVialidadEnum.VIALIDAD_NO_LOCALIZADA.getCodigo()%>;
	var TIPO_BUSQUEDA_CARRETERA = <%=TipoBusquedaVialidadEnum.CARRETERA.getCodigo()%>;
	var TIPO_BUSQUEDA_CAMINO = <%=TipoBusquedaVialidadEnum.CAMINO.getCodigo()%>;
</script>
<c:choose>
	<c:when test="${not empty FROM_WIZARD}">
		<c:set var="urlPorCP"
			value="${contextpath}/wizard/domicilio/porCodigoPostal"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="urlPorCP"
			value="${contextpath}/domicilio/nacional/ubicar/porCodigoPostal"></c:set>
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${not empty FROM_WIZARD}">
		<c:set var="urlPorMunicipio"
			value="${contextpath}/wizard/domicilio/porMunicipio"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="urlPorMunicipio"
			value="${contextpath}/domicilio/nacional/ubicar/porMunicipio"></c:set>
	</c:otherwise>
</c:choose>

<style>
	div.dom-grid div.form-group {
	    border-bottom: 1px solid #D3D3D3;
	    padding-bottom: 16px;
	}
	
	div.dom-grid div.form-group input, 
	div.dom-grid div.form-group select {
		width: 80%;
	}
	
	div.dom-grid div.form-group label {
		display: block;
	}
	
</style>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/domicilios/domicilios.js" htmlEscape="true" />"></script>

<input type="hidden" value="${tipoBusquedaDomicilio}" id="tipoBusquedaDomicilio">
<div class="alert alert-danger" style="display: none" id="divErrorCampos"></div>
<div class="row dom-grid">
	<div class="col-xs-4"> 
		<h3>
		&iquest;Conoces tu C&oacute;digo Postal?
		</h3>
		<div class="btn-group">
		    <button type="button" class="btn btn-default" onclick="setTipoBusquedaDomicilio('cp')" id="busquedaCP">Si</button>
		    <button type="button" class="btn btn-primary" onclick="setTipoBusquedaDomicilio('mun')" id="busquedaMun">No</button>
	    </div>
		
	</div>
	<div id="municipio" style="display: none;" class="col-xs-8"> 
		<div>
			<form:form action="${urlPorMunicipio }" modelAttribute="asentamiento"
				method="POST" id="formMunicipio">

				<fieldset>
					<legend>
						<strong>Municipio de tu domicilio</strong>
					</legend>

					<!-- Entidad Federativa -->
					<div class="form-group">
						<div>
							<form:label path="localidad.municipio.entidadFederativa.clave" cssClass="control-label">
								<spring:message code="label.entidadFederativa" /><span class="required" id="localidad.municipio.entidadFederativa.claveReq">*</span>:
							</form:label>
							<combo:creaCombo
								idHtml="localidad.municipio.entidadFederativa.clave"
								idHtmlContenedor="formMunicipio"
								entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado"
								idHtmlValor="${asentamiento.localidad.municipio.entidadFederativa.clave }"
								mostrarSoloActivos="true"
								cssClassname="form-control" />
							<input type="hidden" id="cveEntAsenAux"
								value="${asentamiento.localidad.municipio.entidadFederativa.clave }" />
						</div>
						<div>
							<form:errors path="localidad.municipio.entidadFederativa.clave"
								cssClass="error"  spanRequired="localidad.municipio.entidadFederativa.claveReq" campoRelacionado="localidad.municipio.entidadFederativa.clave" />
						</div>
					</div>

					<!-- Municipio -->
					<div class="form-group">
						
						<div>
							<form:label path="localidad.municipio.clave" cssClass="control-label">
								<spring:message code="label.municipio" /><span class="required" id="localidad.municipio.claveReq">*</span>:
							</form:label>

							<combo:creaCombo
								entidad="mx.gob.imss.ctirss.delta.persistence.DgCatMunicipio"
								idHtml="localidad.municipio.clave"
								entidadPadre="dgCatEstado.cveEnt"
								idHtmlPadre="localidad.municipio.entidadFederativa.clave"
								idHtmlContenedor="formMunicipio"
								idHtmlValor="${asentamiento.localidad.municipio.clave }"
								idHtmlValorPadre="${asentamiento.localidad.municipio.entidadFederativa.clave }"
								mostrarSoloActivos="false"
								cssClassname="form-control" />
							<input type="hidden" id="cveMuniAsenAux"
								value="${asentamiento.localidad.municipio.clave }" />
						</div>
						<div>
							<form:errors path="localidad.municipio.clave" cssClass="error" 
							  spanRequired="localidad.municipio.claveReq" campoRelacionado="localidad.municipio.clave"/>
						</div>
					</div>

					<!-- Campo de asentamiento -->
					<div class="form-group">
						<div style="float: right;">
							
						</div>
						
						<div>
							<form:label path="clave" cssClass="control-label">
								<spring:message code="label.asentamiento" /><span class="required" id="claveReq">*</span>:<a class="btn btn-xs icono-help" id="idPopoverAsentamiento"
								data-toggle="popover"> </a>
							</form:label>
							<select id="clave" name="clave" class="form-control" style="display: inline-block;">
								<option value="-1">--Selecciona por favor--</option>
							</select> <input type="hidden" id="cveAsentamientoAux"
								value="${asentamiento.clave }" />
							<img id="cveAsentamientoImgCargando"
								class="cargando-combo" style="display: none;"
								src="${staticResourcesPath}/imagenes/loading.gif"
								alt="Cargando" />
						</div>
						<div>
							<form:errors path="clave" cssClass="error" spanRequired="claveReq" campoRelacionado="clave"/>
							<span class="error"> ${msg.error.asentamiento}</span>
						</div>
					</div>

					<!-- Campo de localidad 
					<fieldset class="fsInterno">
						<div style="float: right;">
							<a class="btn btn-default btn-xs icono-help" id="idPopoverLocalidad"
								data-toggle="popover"> </a>
						</div>
						<div>
							<form:errors path="localidad.clave" cssClass="error" />
						</div>
						<div>
							<form:label path="localidad.clave">
								<span class="required">*</span>&nbsp;<spring:message
									code="label.localidad" />
							</form:label>
							<select id="localidad.clave" name="localidad.clave">
								<option>--Por favor seleccione--</option>
							</select> <input type="hidden" id="cveLocalidadAux"
								value="${asentamiento.localidad.clave }" />
						</div>
					</fieldset>
					-->

					<fieldset class="fsInterno">
						<div style="float: left; padding: 15px 0px;"><span class="required" id="labelCamposObligatoriosGeneralSecundario">*</span>Campos obligatorios </div>
						<button type="submit" style="float: right; margin-top: 5px; margin-right: 115px;"
							class="btn btn-primary">
							<spring:message code="label.btn.ubicar" />
						</button>
					</fieldset>
				</fieldset>
			</form:form>
		</div>
	</div>
	<div id="codigoPostal" style="display:none;" class="col-xs-8"> 
		<div>
			<form:form action="${urlPorCP}" method="POST"
				modelAttribute="domicilio" id="formCodigoPostal" role="form">
				<fieldset>
					<legend>
						<strong>C&oacute;digo Postal de tu domicilio</strong>
					</legend>
					
					<input type="hidden" id="idUMF" value="${idUmf}" />
					<input type="hidden" id="idDelegacion"  value="${idDelegacion}"/>
					
					<!-- Campo del codigo postal -->
					<div class="form-group">
						<span id="errorNegocioLabel" class="error hiddenElement"></span>
						
						<div>
							<form:label path="codigoPostal.codigoPostal" cssClass="control-label"
								cssStyle="display:block;">
								<spring:message code="label.codigoPostal" /><span class="required" id="codigoPostal.codigoPostalReq">*</span>:
							</form:label>
							<form:input type="text" path="codigoPostal.codigoPostal"
								maxlength="5" cssClass="numerico form-control"/>
						</div>
						<div>
							<form:errors path="codigoPostal.codigoPostal" cssClass="error" spanRequired="codigoPostal.codigoPostalReq" campoRelacionado="codigoPostal.codigoPostal"/>
						</div>
					</div>

					<!-- Campo de asentamiento -->
					<div class="form-group">
						<div style="float: right;">
							
						</div>
						<div>
							<form:label path="asentamiento.clave" cssClass="control-label">
								<spring:message code="label.asentamiento" /><span class="required" id="asentamiento.claveReq">*</span>:<a class="btn btn-xs icono-help" id="idPopoverAsentamientoCP"
								data-toggle="popover"> </a>
							</form:label>
							<select id="asentamiento.clave" name="asentamiento.clave" class="form-control"
								style="display: inline-block;">
								<option>--Selecciona por favor--</option>
							</select> <input type="hidden" id="cveAsentamientoCPAux"
								value="${domicilio.asentamiento.clave}" />
							<img id="cveAsentamientoImgCargando"
								class="cargando-combo" style="display: none;"
								src="${staticResourcesPath}/imagenes/loading.gif"
								alt="Cargando" />
						</div>
						<div>
							<form:errors path="asentamiento.clave" cssClass="error"  spanRequired="asentamiento.claveReq" campoRelacionado="asentamiento.clave" />
						</div>
					</div>

					<!-- Campo de localidad 
					<fieldset class="fsInterno">
						<div style="float: right;">
							<a class="btn btn-default btn-xs icono-help" id="idPopoverLocalidadCp"
								data-toggle="popover"> </a>
						</div>
						<div>
							<form:errors path="localidad.clave" cssClass="error" />
						</div>
						<div>
							<form:label path="localidad.clave">
								<span class="required">*</span>&nbsp;<spring:message
									code="label.localidad" />
							</form:label>
							<select id="localidad.clave" name="localidad.clave">
								<option>--Por favor seleccione--</option>
							</select> <input type="hidden" id="cveLocalidadCPAux"
								value="${domicilio.asentamiento.localidad.clave}" />
						</div>
					</fieldset>
					-->

					<div>
						<div style="float: left; padding: 15px 0px;"><span class="required" id="labelCamposObligatoriosGeneral">*</span>Campos obligatorios</div>
						<button type="submit" style="float: right; margin-top: 5px; margin-right: 115px;"
							class="btn btn-primary">
							<spring:message code="label.btn.ubicar" />
						</button>
					</div>
				</fieldset>

				<!--  Dato hidden de la localidad -->
				<form:hidden path="asentamiento.localidad.clave" />
				<form:hidden path="asentamiento.localidad.municipio.clave" />
				<form:hidden
					path="asentamiento.localidad.municipio.entidadFederativa.clave" />
			</form:form>
		</div>
	</div>

</div>