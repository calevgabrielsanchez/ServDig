<%@ include file="../../../../general/taglibs.jsp"%>
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
			value="${contextpath}/wizard/tramite/registrar/domicilio/particular/porCodigoPostal"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="urlPorCP"
			value="${contextpath}/domicilio/nacional/ubicar/porCodigoPostal"></c:set>
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${not empty FROM_WIZARD}">
		<c:set var="urlPorMunicipio"
			value="${contextpath}/wizard/tramite/registrar/domicilio/particular/porMunicipio"></c:set>
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
	src="<spring:url value="/static/resources/js/delta/domicilios/domicilios.js" htmlEscape="true" />">
</script>
 <script>
$( document ).ready(function() {
	setTipoBusquedaDomicilio('cp');
});
</script>

<input type="hidden" value="${tipoBusquedaDomicilio}" id="tipoBusquedaDomicilio">
<input type="hidden" value="${tipoTramite}" id="tipoTramite">

<div class="row dom-grid">
	<div id="codigoPostal" style="display:none;" class="col-xs-8">
		<div>
			<form:form action="${urlPorCP}" method="POST"
				modelAttribute="domicilio" id="formCodigoPostal" role="form">
				<fieldset>
					<legend>
						<strong>C&oacute;digo Postal de su domicilio</strong>
					</legend>
					
					<input type="hidden" id="idUMF" value="${idUmf}" />
					<input type="hidden" id="idDelegacion"  value="${idDelegacion}"/>
					
					<!-- Campo del codigo postal -->
					<div class="form-group">
						<span id="errorNegocioLabel" class="error hiddenElement"></span>
						<div>
							<form:errors path="codigoPostal.codigoPostal" cssClass="error" />
							<span id="codigoPostalError" class="error hiddenElement"></span>
						</div>
						<div>
							<form:label path="codigoPostal.codigoPostal" cssClass="control-label"
								cssStyle="display:block;">
								<span class="required">*</span>&nbsp;<spring:message
									code="label.codigoPostal" />
							</form:label>
							<form:input type="text" path="codigoPostal.codigoPostal"
								maxlength="5" cssClass="numerico form-control"/>
						</div>
					</div>

					<!-- Campo de asentamiento -->
					<div class="form-group">
						<div style="float: right;">
							<a class="btn btn-default btn-xs icono-help" id="idPopoverAsentamientoCP"
								data-toggle="popover"> </a>
						</div>
						<div>
							<form:errors path="asentamiento.clave" cssClass="error" />
						</div>
						<div>
							<form:label path="asentamiento.clave" cssClass="control-label">
								<span class="required">*</span>&nbsp;<spring:message
									code="label.asentamiento" />
							</form:label>
							<select id="asentamiento.clave" name="asentamiento.clave" class="form-control"
								style="display: inline-block;">
								<option>--Por favor seleccione--</option>
							</select> <input type="hidden" id="cveAsentamientoCPAux"
								value="${domicilio.asentamiento.clave}" />
							<img id="cveAsentamientoImgCargando"
								class="cargando-combo" style="display: none;"
								src="${staticResourcesPath}/imagenes/loading.gif"
								alt="Cargando" />
						</div>
					</div>
					<div>
						<button type="submit" style="float: right; margin-top: 5px;"
							class="btn btn-secondary">
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