<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.domicilio.TipoBusquedaVialidadEnum"%>

<c:set var="busquedaXVi" value="<%=TipoBusquedaVialidadEnum.VIALIDAD.getCodigo()%>" />
<c:set var="busquedaXVNL" value="<%=TipoBusquedaVialidadEnum.VIALIDAD_NO_LOCALIZADA.getCodigo()%>" />
<c:set var="busquedaXCar" value="<%=TipoBusquedaVialidadEnum.CARRETERA.getCodigo()%>" />
<c:set var="busquedaXCam" value="<%=TipoBusquedaVialidadEnum.CAMINO.getCodigo()%>" />
<script>	
	var TIPO_BUSQUEDA_VIALIDAD = <%=TipoBusquedaVialidadEnum.VIALIDAD.getCodigo()%>;
	var TIPO_BUSQUEDA_VIALIDAD_NL = <%=TipoBusquedaVialidadEnum.VIALIDAD_NO_LOCALIZADA.getCodigo()%>;
	var TIPO_BUSQUEDA_CARRETERA = <%=TipoBusquedaVialidadEnum.CARRETERA.getCodigo()%>;
	var TIPO_BUSQUEDA_CAMINO = <%=TipoBusquedaVialidadEnum.CAMINO.getCodigo()%>;
</script>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/domicilios/domicilios.js" htmlEscape="true" />"></script>

<script>
	$(document).ready(function() {
		$('div#controles form#form').submit(function(){
			var oForm = $('form#formDomicilio').toObject();
			window.returnValue = oForm;
			window.close();
		});
	});
</script>

<input type="hidden" value="${tipoNuevaVialidad}" id="tipoNuevaVialidad">

<div class="row">	
	<div id="titulo" class="col-xs-12" style="width: 100%"> 
		<h2>Datos del domicilio geogr&aacute;fico nacional ubicado</h2>
		<h3 style="font-size: .9em; color: #666666">Paso 3 / 3</h3>
	</div>
</div>

<form:form modelAttribute="domicilio" id="formDomicilio">
	<div id="domicilio" class="row">
		<div class="col-xs-6"> 
			<fieldset class="dom-container">
				<legend>
					<strong>Componentes principales del domicilio</strong>
				</legend>
				
				<!-- Campo del codigo postal -->
				<fieldset class="fsInterno">
					<span id="errorNegocioLabel" class="error hiddenElement"></span>
					<div>
						<form:errors path="codigoPostal.codigoPostal"
							cssClass="error" />
					</div>
					<legend>
						<spring:message code="label.codigoPostal" /><span class="required">*</span>:
					</legend>
					<div>
						<form:input type="text"
							path="asentamiento.codigoPostal.codigoPostal" maxlength="8"
							readonly="true" cssClass="deshabilitado input-sm" />
						<form:hidden path="codigoPostal.codigoPostal"
							value="${domicilio.asentamiento.codigoPostal.codigoPostal}" />
					</div>
				</fieldset>
				
				<!-- Entidad Federativa -->
				<fieldset class="fsInterno">
					<legend>
						<spring:message code="label.entidadFederativa" /><span class="required">*</span>:
					</legend>
					<div>
						<form:input
							path="asentamiento.localidad.municipio.entidadFederativa.nombre"
							readonly="true" cssClass="deshabilitado input-sm" />
						<form:input
							path="asentamiento.localidad.municipio.entidadFederativa.clave"
							readonly="true" cssClass="hidden" cssStyle="display: none !important;" />
					</div>
				</fieldset>
				
				<!-- Municipio -->
				<fieldset class="fsInterno">
					<legend>
						<spring:message code="label.municipio" /><span class="required">*</span>:
					</legend>
					<div>
						<form:input path="asentamiento.localidad.municipio.nombre"
							readonly="true" cssClass="deshabilitado input-sm" />
						<form:input path="asentamiento.localidad.municipio.clave"
							readonly="true" cssClass="hidden" cssStyle="display: none !important;" />
					</div>
				</fieldset>
				
				<!-- Localidad -->
				<form:input path="asentamiento.localidad.nombre" readonly="true" cssClass="hidden" cssStyle="display: none !important;" />
				<form:input path="asentamiento.localidad.clave" readonly="true" cssClass="hidden" cssStyle="display: none !important;" />
				<%-- <fieldset class="fsInterno">
					<div>
						<form:label path="asentamiento.localidad.nombre">
							<spring:message code="label.localidad" />
						</form:label>
						<form:input path="asentamiento.localidad.nombre"
							readonly="true" cssClass="deshabilitado input-sm" />
					</div>
				</fieldset> --%>
				
				<!-- Asentamiento-->
				<fieldset class="fsInterno">
					<legend>
						<spring:message code="label.asentamiento" /><span class="required">*</span>:
					</legend>
					<div>
						<form:input path="asentamiento.tipoAsentamiento.descripcion"
							readonly="true" cssClass="deshabilitado input-sm" style="width: 30%;" />
						-
						<form:input path="asentamiento.nombre" readonly="true"
							cssClass="deshabilitado input-sm" style="width: 50%;"/>
						<form:input path="asentamiento.clave" readonly="true"
							cssClass="hidden" cssStyle="display: none !important;" />
					</div>
				</fieldset>

				<!-- Vialidad Primaria -->
				<fieldset class="fsInterno" style="margin-top: 0px;">
					<div>
						<form:errors path="vialidadPrimaria.nombre" cssClass="error" />
					</div>
					<legend>
						<spring:message code="label.vialidadPrimaria" /><span class="required">*</span>:
					</legend>
					<div>
						<form:hidden path="tipoBusquedaVialidad"/>
						<c:choose>
							<c:when test="${empty domicilio.tipoBusquedaVialidad || domicilio.tipoBusquedaVialidad eq busquedaXVi}">
								<div>
									<form:input path="vialidadPrimaria.tipoVialidad.descripcion"
										maxlength="100" size="50" readonly="true"
										cssClass="deshabilitado input-sm" style="width: 30%;"/>
									-
									<form:input path="vialidadPrimaria.nombre" maxlength="100"
										size="50" readonly="true" cssClass="deshabilitado input-sm" style="width: 50%;"/>
									<form:hidden path="vialidadPrimaria.tipoVialidad.clave" />
									<form:hidden path="vialidadPrimaria.clave" />
								</div>
							</c:when>
							<c:when test="${domicilio.tipoBusquedaVialidad eq busquedaXVNL}">
								<div>
									<form:input path="vialidadPrimaria.tipoVialidad.descripcion"
										maxlength="100" size="50" readonly="true"
										cssClass="deshabilitado input-sm" style="width: 30%;"/>
									-
									<form:input type="text" path="calle" maxlength="100"
										size="50" readonly="true" cssClass="deshabilitado input-sm" style="width: 50%;"/>
									<form:hidden path="vialidadPrimaria.nombre"/>
									<form:hidden path="vialidadPrimaria.tipoVialidad.clave" />
									<form:hidden path="vialidadPrimaria.clave" />
								</div>
							</c:when>
							<c:when test="${domicilio.tipoBusquedaVialidad eq busquedaXCar}">
								<form:input type="text" path="calle" maxlength="100"
										size="100" readonly="true" cssClass="deshabilitado input-sm" style="width: 95%;"/>
								<div class="row" id="divCarretera" style="display:none">
										<form:hidden path="vialidadPrimaria.nombre"/>
										<form:hidden path="domicilioCarretera.terminoGeneral.clave"/>
										<form:hidden path="domicilioCarretera.derechoTransito.clave" />
										<form:hidden path="domicilioCarretera.terminoGeneral.descripcion"/>
										<form:hidden path="domicilioCarretera.derechoTransito.descripcion"/>
										<form:hidden path="domicilioCarretera.origen"/>
										<form:hidden path="domicilioCarretera.destino"/>
										<form:hidden path="domicilioCarretera.administracion.clave"/>
										<form:hidden path="domicilioCarretera.administracion.descripcion"/>
										<form:hidden path="domicilioCarretera.codigoCarretera"/>
										<form:hidden path="domicilioCarretera.cadenamiento" />
								</div>
							</c:when>
							<c:when test="${domicilio.tipoBusquedaVialidad eq busquedaXCam}">
								<form:input type="text" path="calle" maxlength="100"
										size="50" readonly="true" cssClass="deshabilitado input-sm"  style="width: 95%;"/>
								
								<div style="display: none;">
									<form:hidden path="vialidadPrimaria.nombre"/>
									<form:hidden path="domicilioCamino.terminoGeneral.clave"/>
									<form:hidden path="domicilioCamino.margen.clave" />
									<form:hidden path="domicilioCamino.terminoGeneral.descripcion"/>
									<form:hidden path="domicilioCamino.margen.descripcion"/>
									<form:hidden path="domicilioCamino.origen"/>
									<form:hidden path="domicilioCamino.destino"/>
									<form:hidden path="domicilioCamino.cadenamiento"/>
								</div>
							</c:when>
						</c:choose>
					</div>
				</fieldset>
				
				<!-- Numero y Letra exterior principal -->
				<fieldset class="fsInterno">
					<div>
						<form:errors path="numExterior1" cssClass="error" />
					</div>
					<legend>
						<spring:message code="label.numeroLetraExterior" /><span class="required">*</span>:
					</legend>
					<div>
						<form:input path="numExterior1" maxlength="10"
							readonly="true" cssClass="deshabilitado input-sm"  style="width: 40%;"/>
						<span> <strong>/</strong>
						</span>
						<form:input path="numExteriorAlf" maxlength="10"
							readonly="true" cssClass="deshabilitado input-sm" style="width: 40%;"/>
					</div>
				</fieldset>

				<!-- Numero y Letra  interior-->
				<fieldset class="fsInterno">
					<div>
						<form:errors path="numInterior" cssClass="error" />
					</div>
					<legend>
						<spring:message code="label.numeroLetraInterior" />:
					</legend>
					<div>
						<form:input path="numInterior" maxlength="10" readonly="true"
							cssClass="deshabilitado input-sm" style="width: 40%;"/>
						<span> <strong>/</strong>
						</span>
						<form:input path="numInteriorAlf" maxlength="10"
							readonly="true" cssClass="deshabilitado input-sm" style="width: 40%;"/>
					</div>
				</fieldset>

				<!-- Numero exterior secundario  -->
				<fieldset class="fsInterno">
					<div>
						<form:errors path="numExterior2" cssClass="error" />
					</div>
					<legend>
						<spring:message code="label.numExterior2" />:
					</legend>
					<div>
						<form:input path="numExterior2" maxlength="10" readonly="true"
							cssClass="deshabilitado input-sm" />
					</div>
				</fieldset>

				

				
			</fieldset>
		</div>
		<div class="col-xs-6"> 
			<fieldset class="dom-container">
				<legend>
					<strong>Componentes secundarios del domicilio</strong>
				</legend>

				<!-- Vialidad referencia primaria-->
				<fieldset class="fsInterno">
					<div>
						<form:errors path="vialidadReferenciaPrimaria.nombre"
							cssClass="error" />
					</div>
					<legend>
						<spring:message code="label.vialidadReferenciaPrimaria" />:
					</legend>
					<div>
						<form:input
							path="vialidadReferenciaPrimaria.tipoVialidad.descripcion"
							maxlength="100" size="50" readonly="true"
							cssClass="deshabilitado input-sm" style="width: 30%;"/>
						-
						<form:input path="vialidadReferenciaPrimaria.nombre"
							maxlength="100" size="50" readonly="true"
							cssClass="deshabilitado input-sm" style="width: 50%;"/>
						<form:hidden
							path="vialidadReferenciaPrimaria.tipoVialidad.clave" />
						<form:hidden path="vialidadReferenciaPrimaria.clave" />
					</div>
				</fieldset>

				<!-- Vialidad referencia secundaria-->
				<fieldset class="fsInterno">
					<div>
						<form:errors path="vialidadReferenciaSecundaria.nombre"
							cssClass="error" />
					</div>
					<legend>
						<spring:message code="label.vialidadReferenciaSecundaria" />:
					</legend>
					<div>
						<form:input
							path="vialidadReferenciaSecundaria.tipoVialidad.descripcion"
							maxlength="100" size="50" readonly="true"
							cssClass="deshabilitado input-sm" style="width: 30%;"/>
						-
						<form:input path="vialidadReferenciaSecundaria.nombre"
							maxlength="100" size="50" readonly="true"
							cssClass="deshabilitado input-sm" style="width:50%;"/>
						<form:hidden
							path="vialidadReferenciaSecundaria.tipoVialidad.clave" />
						<form:hidden path="vialidadReferenciaSecundaria.clave" />
					</div>
				</fieldset>

				<!-- Vialidad referencia posterior-->
				<fieldset class="fsInterno">
					<div>
						<form:errors path="vialidadReferenciaPosterior.nombre"
							cssClass="error" />
					</div>
					<legend>
						<spring:message code="label.vialidadReferenciaPosterior" />:
					</legend>
					<div>
						<form:input
							path="vialidadReferenciaPosterior.tipoVialidad.descripcion"
							maxlength="100" size="50" readonly="true"
							cssClass="deshabilitado input-sm" style="width: 30%;"/>
						-
						<form:input path="vialidadReferenciaPosterior.nombre"
							maxlength="100" size="50" readonly="true"
							cssClass="deshabilitado input-sm" style="width: 50%;"/>
						<form:hidden
							path="vialidadReferenciaPosterior.tipoVialidad.clave" />
						<form:hidden path="vialidadReferenciaPosterior.clave" />
					</div>
				</fieldset>
				<!-- Descripcion -->
				<fieldset class="fsInterno">
					<div>
						<form:errors path="descripcion" cssClass="error" />
					</div>
					<legend>
						<spring:message code="label.descripcion" />:
					</legend>
					<div>
						<form:textarea path="descripcion"
							 cssStyle="width:360px !important; height: 120px !important; resize: vertical;"
							cols="10" readonly="true" cssClass="deshabilitado input-sm" />
					</div>
				</fieldset>
			</fieldset>
		</div>
	</div>
	<div class="row" style="margin-top: 20px;">
		<div class="col-sm-4">
			<div style="float: left; padding: 15px 0px;"><span class="required">*</span> Campos obligatorios</div>
		</div>
		<div class="col-sm-8">
			<div class="pull-right">
				<button type="button" style="margin-top: 5px;"
					class="btn btn-default" destino="regresar"
					id="botonControlRegresar">
					Regresar
				</button>
				<button type="button" style="margin-top: 5px;"
					class="btn btn-primary" destino="terminar"
					id="botonControl">
					Aceptar
				</button>
			</div>
		</div>
	</div>

	<form:hidden path="latitud" />
	<form:hidden path="longitud" />
</form:form>