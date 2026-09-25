<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.domicilio.TipoBusquedaVialidadEnum"%>
<c:choose>
	<c:when test="${not empty idDelegacion}">
		<c:set var="urlRegreso"
			value="${contextpath}/domicilio/nacional/ubicar/delegacion?idDelegacion=${idDelegacion}" ></c:set>
	</c:when>
	<c:when test="${not empty FROM_WIZARD}">
		<c:set var="urlRegreso"value="${contextpath}/wizard/domicilio/regresar"></c:set>
	</c:when>
	<c:when test="${not empty idUmfUsuarioSession}">
		<c:set var="urlRegreso" value="${contextpath}/domicilio/nacional/ubicar/byUmf?idUmfUsuario=${idUmfUsuarioSession}&idUmfPersona=${idUmfPersonaSession}&tipoTramite=${idTipoTramiteSession}"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="urlRegreso" value="${contextpath}/domicilio/nacional/ubicar"></c:set>
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${not empty FROM_WIZARD}">
		<c:set var="urlUbicarDom" value=""></c:set>
	</c:when>
	<c:when test="${not empty FROM_ADMON_DOMICILIO}">
		<c:set var="urlUbicarDom"
			value="${contextpath}/domicilio/administrar/particular/confirmar-modificacion/${indexDomicilio}"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="urlUbicarDom"
			value="${contextpath}/domicilio/nacional/ubicar/complemento/guardar"></c:set>
	</c:otherwise>
</c:choose>

<script type="text/javascript"
	src="${staticResourcesPath}/js/bootstrap/bootstrap-typeahead.js"></script>



<script>	
	var TIPO_BUSQUEDA_VIALIDAD = <%=TipoBusquedaVialidadEnum.VIALIDAD.getCodigo()%>;
	var TIPO_BUSQUEDA_VIALIDAD_NL = <%=TipoBusquedaVialidadEnum.VIALIDAD_NO_LOCALIZADA.getCodigo()%>;
	var TIPO_BUSQUEDA_CARRETERA = <%=TipoBusquedaVialidadEnum.CARRETERA.getCodigo()%>;
	var TIPO_BUSQUEDA_CAMINO = <%=TipoBusquedaVialidadEnum.CAMINO.getCodigo()%>;
</script>

<style>
ul.typeahead.dropdown-menu {
	width: 350px;
}

input.autocompleteLbl {
	width: 100%;
}

input.autocompleteInput {
	width: 85%;
	text-transform: uppercase;
}

.nomVialidad {
	font-size: 13px;
}

.descTipoVialidad {
	font-size: 10px;
	color: #67666A;
}

.descElementos {
	font-size: 10px;
	color: #67666A;
	font-style: italic;
}

.typeahead.dropdown-menu li>a {
	white-space: normal;
}

.dropdown-menu .active>a .descTipoVialidad {
	color: #FFFFFF;
}

.dropdown-menu .active>a .descElementos {
	color: #FFFFFF;
}

ul.typeahead li {
	border-bottom: 1px solid #CCCCCC;
}

ul.typeahead li:last-child {
	border-bottom: none;
}

input.mayusculas {
	text-transform: uppercase;
}

form .control-label span {
	font-size: 12px;
}

span.input-group-addon {
	padding: 0px 10px !important;
}

</style>
<form:form action="${urlRegreso}" method="POST" id="formRegreso"
			modelAttribute="domicilio">
			<c:if test="${not empty FROM_CODIGO_POSTAL }">
				<!-- Para búsqueda por código postal -->
				<form:hidden path="codigoPostal.codigoPostal" />
				<form:hidden path="asentamiento.clave" />
				<form:hidden path="asentamiento.localidad.municipio.clave" />
				<form:hidden path="asentamiento.localidad.municipio.entidadFederativa.clave" />
				<form:hidden path="localidad.municipio.clave" />
				<form:hidden path="localidad.municipio.entidadFederativa.clave" />
			</c:if>
			<c:if test="${not empty FROM_MUNICIPIO }">
				<!-- Para búsqueda por municipio -->
				<form:hidden path="asentamiento.clave" />
				<form:hidden path="asentamiento.localidad.municipio.entidadFederativa.clave" />
				<form:hidden path="asentamiento.localidad.municipio.clave" />
				<form:hidden path="localidad.municipio.entidadFederativa.clave" />
				<form:hidden path="localidad.municipio.clave" />
			</c:if>
		</form:form>
		<form:form action="${urlUbicarDom}" method="POST"
			modelAttribute="domicilio" id="formComplemento">
<div id="asentamientoMapa" class="row">

	<div id="datosComplementarios" class="col-xs-6"> 
			<form:hidden path="estadoAdministracionDomicilio" />
			<form:hidden path="clave" />
			<form:hidden path="tipoDomicilio.clave" />
			<form:hidden path="dicTipoDomicilio.clave" />
			<form:hidden path="tipoBusquedaVialidad"/>
			
			<fieldset class="dom-container">
				<legend>
					<strong>Componentes principales del domicilio</strong>
				</legend>

				<div>
					<!-- Vialidad Primaria -->
					<fieldset class="fsInterno">
						<legend>
							
							<spring:message code="label.vialidadPrimaria" /><span class="required" id="vialidadPrimariaInputReq">*</span>:
						</legend>
						

						<div id="vialidadPrimaria">
							<label class="error" id="vialidadPrimariaError"
								style="display: none;"></label>
							<div class="row" id="divVialidadPrimaria">
								<div class="col-xs-8">
									<div class="input-group input-group-sm">
										<input type="text" class="autocompleteInput form-control dropdown-vialidad-primaria"
											id="vialidadPrimariaInput" data-provide="typeahead"
											autocomplete="off"
											value="${domicilio.vialidadPrimaria.nombre}"
											name="vialidadPrimaria.nombre" /> 
										<span class="input-group-addon">
											<img alt="Cargando" src="${staticResourcesPath}/imagenes/loading.gif"
												style="height: 21px; width: 21px; display: none;" />
										</span>
									</div>
								</div>
								<div class="col-xs-4" style="padding-left: 0px;">
									<input type="text" id="vialidadPrimariaLbl"
										readonly="readonly" class="autocompleteLbl input-sm"
										value="${domicilio.vialidadPrimaria.tipoVialidad.descripcion}" />
								</div>
								<form:hidden path="vialidadPrimaria.clave"
								id="vialidadPrimaria.clave.hidden" />
								<form:hidden path="vialidadPrimaria.tipoVialidad.descripcion"
									id="vialidadPrimaria.tipoVialidad.descripcion.hidden" />
							</div>
							<div>
								<form:errors path="vialidadPrimaria.clave" cssClass="error" spanRequired="vialidadPrimariaInputReq" campoRelacionado="vialidadPrimariaInput"/>
								<span id="vialidadPrimaria.claveError"
									class="error hiddenElement"></span>
							</div>
							
							
							<div id="vialidadNoEncontrada" style="display:none">
							<div class="row" id="tipoNuevo" align="center">
								<div>
									<span style="font-size: 1.0em;"> <strong>Selecciona el tipo de tu vialidad:</strong>
									</span>
								</div>
								<div align="center" id="opcionesNuevaVialidad">
									    <div class="btn-group" data-toggle="buttons-radio">
										    <button type="button" class="btn btn-primary" onclick="elegirNuevoTipoVialidad(<%=TipoBusquedaVialidadEnum.VIALIDAD_NO_LOCALIZADA.getCodigo()%>)" id="nuevoVialidad">Vialidad</button>
										    <button type="button" class="btn btn-primary" onclick="elegirNuevoTipoVialidad(<%=TipoBusquedaVialidadEnum.CARRETERA.getCodigo()%>)" id="nuevoCarretera">Carretera</button>
										    <button type="button" class="btn btn-primary" onclick="elegirNuevoTipoVialidad(<%=TipoBusquedaVialidadEnum.CAMINO.getCodigo()%>)" id="nuevoCamino">Camino</button>
									    </div>
								</div>
							</div>
							<br>
							
							<div id="divLocalidadHidden" style="display: none;">
								<div class="form-group">
									<div>
										<label class="control-label">
											<span>Localidad<span class="required">*</span>:</span>
										</label>
										<form:errors path="asentamiento.localidad.clave" cssClass="error" />
										<form:hidden path="asentamiento.localidad.nombre"/>
										<input type="hidden" value="${domicilio.asentamiento.localidad.clave}" id="cveLocalidad"> 
										<select id="asentamiento.localidad.clave" name="asentamiento.localidad.clave" class="form-control input-sm"></select>
									</div>
								</div>
							</div>
							
							<!-- Atributos de nueva vialidad -->
							<div id="divVialidadPrim" style="display:none">
								<div class="form-group">
									<div>
										<label class="control-label">
											<span>Tipo vialidad<span class="required">*</span>:</span>
										</label>
										<combo:creaCombo
											idHtml="vialidadPrimaria.tipoVialidad.clave"
											idHtmlContenedor="formComplemento"
											entidad="mx.gob.imss.ctirss.delta.persistence.DgCatVialidad"
											mostrarSoloActivos="false" 
											idHtmlValor="${domicilio.vialidadPrimaria.tipoVialidad.clave}"
											cssClassname="form-control input-sm" />
									</div>
								</div>
								<div class="form-group">
									<div>
										<label class="control-label">
											<span>Nombre<span class="required">*</span>:</span>
										</label>
										<form:input type="text" path="calle"
											size="25" cssClass="alfanumerico mayusculas form-control input-sm" maxlength="50" 
											oncopy="return false" oncut="return false" onpaste="return false"/>
									</div>
								</div>
							</div>
							
							<!-- Atributs de carretera -->
							<div id="divCarretera" style="display:none">
								<div class="form-group">
									<div>
										<label class="control-label">
											<span>Tipo carretera / derecho<span class="required">*</span>:</span>
										</label>
										<div style="width: 100%; display: table;">
											<div style="display: table-cell; width: 47%;">
												<form:hidden
													path="domicilioCarretera.terminoGeneral.descripcion" />
												<combo:creaCombo
													idHtml="domicilioCarretera.terminoGeneral.clave"
													idHtmlContenedor="divCarretera"
													entidad="mx.gob.imss.ctirss.delta.persistence.DgCatTermGen"
													mostrarSoloActivos="false"
													idHtmlValor="${domicilio.domicilioCarretera.terminoGeneral.clave}"
													cssClassname="form-control input-sm" />
											</div>
											<div style="display: table-cell; width: 4%; text-align: center;">												
												<span> <strong>/</strong></span>
											</div>
											<div style="display: table-cell; width: 47%;">
												<form:hidden
													path="domicilioCarretera.derechoTransito.descripcion" />
												<combo:creaCombo
													idHtml="domicilioCarretera.derechoTransito.clave"
													idHtmlContenedor="divCarretera"
													entidad="mx.gob.imss.ctirss.delta.persistence.DgCatDerechosTransito"
													mostrarSoloActivos="false"
													idHtmlValor="${domicilio.domicilioCarretera.derechoTransito.clave}" 
													cssClassname="form-control input-sm" />
											</div>
										</div>
									</div>
								</div>
								
								<div class="form-group">
									<div>
										<label class="control-label">
											<span>Origen / Destino<span class="required">*</span>:</span>
										</label>
										<div style="width: 100%; display: table;">
											<div style="display: table-cell; width: 47%;">
												<form:input path="domicilioCarretera.origen" size="20"
													cssClass="alfanumerico mayusculas form-control input-sm"
													maxlength="50" 
													oncopy="return false" oncut="return false" onpaste="return false"/>
											</div>
											<div style="display: table-cell; width: 4%; text-align: center;">												
												<span> <strong>/</strong></span>
											</div>
											<div style="display: table-cell; width: 47%;">
												<form:input type="text" path="domicilioCarretera.destino"
													size="20" cssClass="alfanumerico mayusculas form-control input-sm"
													maxlength="50" 
													oncopy="return false" oncut="return false" onpaste="return false"/>
											</div>
										</div>
									</div>
								</div>
								
								<div class="form-group">
									<div>
										<label class="control-label">
											<span>Administraci&oacute;n<span class="required">*</span>:</span>
										</label>
										<form:hidden path="domicilioCarretera.administracion.descripcion"/>
										<combo:creaCombo
											idHtml="domicilioCarretera.administracion.clave"
											idHtmlContenedor="divCarretera"
											entidad="mx.gob.imss.ctirss.delta.persistence.DgCatAdministracion"
											mostrarSoloActivos="false"
											idHtmlValor="${domicilio.domicilioCarretera.administracion.clave}"
											cssClassname="form-control input-sm" />
									</div>
								</div>
								
								<div class="form-group">
									<div>
										<label class="control-label">
											<span>N&uacute;mero de carretera / cadenamiento (Km)<span class="required">*</span>:</span>
										</label>
										<div style="width: 100%; display: table;">
											<div style="display: table-cell; width: 47%;">
												<form:input path="domicilioCarretera.codigoCarretera"
													size="20" cssClass="numerico form-control input-sm" maxlength="6" 
													oncopy="return false" oncut="return false" onpaste="return false"/>
											</div>
											<div style="display: table-cell; width: 4%; text-align: center;">												
												<span> <strong>/</strong></span>
											</div>
											<div style="display: table-cell; width: 47%;">
												<form:input type="text" path="domicilioCarretera.cadenamiento"
													cssClass="mayusculas alfanumerico form-control input-sm"
													size="20" maxlength="10" 
													oncopy="return false" oncut="return false" onpaste="return false"/>
											</div>
										</div>
									</div>
								</div>
							</div>
							
							<!-- Atributos de camino -->
							<div id="divCamino" style="display:none">
								<div class="form-group">
									<div>
										<label class="control-label">
											<span>Tipo / Margen<span class="required">*</span>:</span>
										</label>
										<div style="width: 100%; display: table;">
											<div style="display: table-cell; width: 47%;">
												<form:hidden path="domicilioCamino.terminoGeneral.descripcion" />
												<combo:creaCombo idHtml="domicilioCamino.terminoGeneral.clave"
													idHtmlContenedor="divCamino"
													entidad="mx.gob.imss.ctirss.delta.persistence.DgCatTermGen"
													mostrarSoloActivos="false"
													idHtmlValor="${domicilio.domicilioCamino.terminoGeneral.clave}" 
													cssClassname="form-control input-sm"/>
											</div>
											<div style="display: table-cell; width: 4%; text-align: center;">												
												<span> <strong>/</strong></span>
											</div>
											<div style="display: table-cell; width: 47%;">
												<form:hidden path="domicilioCamino.margen.descripcion" />
												<combo:creaCombo idHtml="domicilioCamino.margen.clave"
													idHtmlContenedor="divCamino"
													entidad="mx.gob.imss.ctirss.delta.persistence.DgCatMargen"
													mostrarSoloActivos="false"
													idHtmlValor="${domicilio.domicilioCamino.margen.clave}"
													cssClassname="form-control input-sm" />
											</div>
										</div>
									</div>
								</div>
							
							
								<div class="form-group">
									<div>
										<label class="control-label">
											<span>Origen / Destino<span class="required">*</span>:</span>
										</label>
										<div style="width: 100%; display: table;">
											<div style="display: table-cell; width: 47%;">
												<form:input path="domicilioCamino.origen" size="20"
													cssClass="mayusculas alfanumerico form-control input-sm"
													maxlength="50" 
													oncopy="return false" oncut="return false" onpaste="return false"/>
											</div>
											<div style="display: table-cell; width: 4%; text-align: center;">												
												<span><strong>/</strong></span>
											</div>
											<div style="display: table-cell; width: 47%;">
												<form:input path="domicilioCamino.destino" size="20"
													cssClass="mayusculas alfanumerico form-control input-sm" 
													maxlength="50" 
													oncopy="return false" oncut="return false" onpaste="return false"/>
											</div>
										</div>
									</div>
								</div>
								
								<div class="form-group">
									<div>
										<label class="control-label">
											<span>Cadenamiento (Km)<span class="required">*</span>:</span>
										</label>
										<form:input path="domicilioCamino.cadenamiento"
											cssClass="mayusculas alfanumerico form-control input-sm" maxlength="10" 
											oncopy="return false" oncut="return false" onpaste="return false"/>
									</div>
								</div>
							</div>
							<div style="float: right">
								<a id="regresarBusqueda" class="link">
								Regresar a la b&uacute;squeda por nombre
								</a>
							</div>
						</div>
					</fieldset>

					<!-- Numero y Letra exterior principal -->
					<fieldset class="fsInterno">
						<legend>
							
							<spring:message code="label.numeroLetraExterior" /><span class="required" id="numExterior1Req">*</span>:
							
						</legend>
						
						<div>
							<span style="font-size: 0.7em;"> <strong>Ej. 999
									/ Mz. 9 Lt. 8</strong>
							</span>
						</div>
						<div>
							<form:input type="text" path="numExterior1" maxlength="5"
								size="6" cssClass="numerico input-sm" cssStyle="width: auto;" 
								oncopy="return false" oncut="return false" onpaste="return false"/>
							<span> <strong>/</strong>
							</span>
							<form:input type="text" path="numExteriorAlf" maxlength="35"
								cssClass="alfanumerico input-sm" cssStyle="width: 35%;"
								oncopy="return false" oncut="return false" onpaste="return false"/>
							
							<div style="float:right" class="btn-group" data-toggle="buttons-checkbox">
								<button type="button" class="btn btn-primary btn-sm" id="sinNumero">Sin n&uacute;mero</button>
							</div>
						</div>
						<div>
							<form:errors path="numExterior1" cssClass="error"  spanRequired="numExterior1Req" campoRelacionado="numExterior1"/>
							<span id="numExterior1Error" class="error hiddenElement"></span>
						</div>
					</fieldset>

					<!-- Numero y Letra  interior-->
					<fieldset class="fsInterno">
						<legend>
							<spring:message code="label.numeroLetraInterior" />:
						</legend>

						<div>
							<form:errors path="numInterior" cssClass="error" />
						</div>
						<div>
							<span style="font-size: 0.7em;"> <strong>Ej. 54
									/ Depto. 45 Int.</strong>
							</span>
						</div>

						<div>
							<form:input type="text" path="numInterior" maxlength="5"
								size="6" cssClass="numerico input-sm" cssStyle="width: auto;" 
								oncopy="return false" oncut="return false" onpaste="return false"/>
							<span> <strong>/</strong>
							</span>
							<form:input type="text" path="numInteriorAlf" maxlength="35"
								cssClass="alfanumerico input-sm" 
								oncopy="return false" oncut="return false" onpaste="return false"/>
						</div>
					</fieldset>

					<!-- Numero exterior secundario  -->
					<fieldset class="fsInterno">
						<legend>
							<spring:message code="label.numExterior2" />:
						</legend>

						<div>
							<form:errors path="numExterior2" cssClass="error" />		
							<form:input type="text" path="numExterior2" maxlength="5"
								cssClass="numerico input-sm" 
								oncopy="return false" oncut="return false" onpaste="return false"/>
						</div>
					</fieldset>
					
					<div class="fielsetgris">
						<!-- Nombre del asentamiento -->
						<fieldset class="fsInterno disabled">
							<legend>
								<spring:message code="label.asentamiento" />:
							</legend>
							<div>
								<form:input path="asentamiento.nombre" readonly="true"
									cssClass="disabled input-sm" size="30" />
								<form:hidden path="asentamiento.clave" />
								<form:hidden path="asentamiento.tipoAsentamiento.descripcion" />
							</div>
						</fieldset>
	
						<!-- Campo del codigo postal -->
						<fieldset class="fsInterno disabled">
							<legend>
								<spring:message code="label.codigoPostal" />:
							</legend>
							<span id="errorNegocioLabel" class="error hiddenElement"></span>
							<div>
								<form:errors path="codigoPostal.codigoPostal" cssClass="error" />
							</div>
							<div>
								<form:input path="asentamiento.codigoPostal.codigoPostal"
									maxlength="8" readonly="true" cssClass="disabled input-sm" />
							</div>
						</fieldset>
	
	
						
						<%-- <fieldset class="fsInterno disabled">
	
							<div>
								<form:label path="asentamiento.localidad.nombre">
									<spring:message code="label.localidad" />
								</form:label>
								<form:input path="asentamiento.localidad.nombre" readonly="true"
									cssClass="disabled" size="30" />
							</div>
						</fieldset> --%>
						
						<!-- Municipio -->
						<fieldset class="fsInterno disabled">
							<legend>
								<spring:message code="label.municipio" />:
							</legend>
							<div>
								<form:input path="asentamiento.localidad.municipio.nombre"
									readonly="true" cssClass="disabled input-sm" />
								<form:hidden path="asentamiento.localidad.municipio.clave" />
								<input type="text" id="municipio.nombre" name="municipio.nombre"
									value="${domicilio.asentamiento.localidad.municipio.nombre}"
									style="display: none;" />
							</div>
						</fieldset>
	
						<!-- Entidad Federativa -->
						<fieldset class="fsInterno disabled">
							<legend>
								<spring:message code="label.entidadFederativa" />:
							</legend>
							<div>
								<form:input
									path="asentamiento.localidad.municipio.entidadFederativa.nombre"
									readonly="true" cssClass="disabled input-sm" size="30" />
								<form:hidden
									path="asentamiento.localidad.municipio.entidadFederativa.clave" />
	
								<input type="text" id="entidadFederativa.nombre"
									name="entidadFederativa.nombre"
									value="${domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre}"
									style="display: none;" />
							</div>
						</fieldset>
					</div>
				</div>
			</fieldset>
	</div>
	
	<div class="col-xs-6"> <%--
		<div style="width: 100%;" align="center">
			<div class="ui-widget">
				<div style="margin-top: 20px; padding: 0 .7em;"
					class="ui-state-highlight ui-corner-all">
					<p>
						<span
							style="font-size: 0.7em !important; float: left; margin-right: .3em;"
							class="ui-icon ui-icon-info"></span> <strong> Nota:</strong> El
						mapa muestra la ubicaci&oacute;n aproximada del domicilio
						localizado, este mapa es solo para fines y usos internos del
						Instituto Mexicano del Seguro Social
					</p>
				</div>
			</div>
		</div>
		</br>
		<div id="map_canvas" style="width: 100%; height: 900px"></div>
		 --%>
		 <!-- Datos complementarios del domicilio -->
			<fieldset class="dom-container" >
				<legend>
					<strong> Componentes secundarios del domicilio</strong>
				</legend>

				<div>
					<!-- Vialidad referencia primaria-->
					<fieldset class="fsInterno">
						<legend>
							<spring:message code="label.vialidadReferenciaPrimaria" />:
						</legend>

						<div>
							<form:errors path="vialidadReferenciaPrimaria.clave"
								cssClass="error" />
							<span id="vialidadReferenciaPrimaria.claveError"
								class="error hiddenElement"></span>
						</div>

						<div id="vialidadReferenciaPrimaria">
							<label class="error" id="vialidadReferenciaPrimariaError"
								style="display: none;"></label>
							<div class="row">
								
								<div class="col-xs-8">
									<div class="input-group input-group-sm">
										<input type="text" class="autocompleteInput form-control"
											id="vialidadReferenciaPrimariaInput" data-provide="typeahead"
											autocomplete="off"
											value="${domicilio.vialidadReferenciaPrimaria.nombre}"
											name="vialidadReferenciaPrimaria.nombre" /> 
										<span class="input-group-addon">
											<img alt="Cargando" src="${staticResourcesPath}/imagenes/loading.gif"
												style="height: 21px; width: 21px; display: none;" />
										</span>
									</div>
								</div>
								<div class="col-xs-4" style="padding-left: 0px;">
									<input type="text" id="vialidadReferenciaPrimariaLbl"
										readonly="readonly" class="autocompleteLbl input-sm"
										value="${domicilio.vialidadReferenciaPrimaria.tipoVialidad.descripcion}" />
								</div>
							</div>
							<form:hidden path="vialidadReferenciaPrimaria.clave"
								id="vialidadReferenciaPrimaria.clave.hidden" />
							<form:hidden
								path="vialidadReferenciaPrimaria.tipoVialidad.descripcion"
								id="vialidadReferenciaPrimaria.tipoVialidad.descripcion.hidden" />
							<form:hidden path="vialidadReferenciaPrimaria.tipoVialidad.clave"
								id="vialidadReferenciaPrimaria.tipoVialidad.clave.hidden" />
						</div>
					</fieldset>

					<!-- Vialidad referencia secundaria-->
					<fieldset class="fsInterno">
						<legend>
							<spring:message code="label.vialidadReferenciaSecundaria" />:
						</legend>
						<div>
							<form:errors path="vialidadReferenciaSecundaria.clave"
								cssClass="error" />
							<span id="vialidadReferenciaSecundaria.claveError"
								class="error hiddenElement"></span>
						</div>

						<div id="vialidadReferenciaSecundaria">
							<label class="error" id="vialidadReferenciaSecundariaError"
								style="display: none;"></label>
							<div class="row">
								<div class="col-xs-8">
									<div class="input-group input-group-sm">
										<input type="text" class="autocompleteInput form-control"
											id="vialidadReferenciaSecundariaInput"
											data-provide="typeahead" autocomplete="off"
											value="${domicilio.vialidadReferenciaSecundaria.nombre}"
											name="vialidadReferenciaSecundaria.nombre" /> 
										<span class="input-group-addon">
											<img alt="Cargando" src="${staticResourcesPath}/imagenes/loading.gif"
												style="height: 21px; width: 21px; display: none;" />
										</span>
									</div>
								</div>
								<div class="col-xs-4" style="padding-left: 0px;">
									<input type="text" id="vialidadReferenciaSecundariaLbl"
										readonly="readonly" class="autocompleteLbl input-sm"
										value="${domicilio.vialidadReferenciaSecundaria.tipoVialidad.descripcion}" />
								</div>
								
							</div>
							<form:hidden path="vialidadReferenciaSecundaria.clave"
								id="vialidadReferenciaSecundaria.clave.hidden" />
							<form:hidden
								path="vialidadReferenciaSecundaria.tipoVialidad.descripcion"
								id="vialidadReferenciaSecundaria.tipoVialidad.descripcion.hidden" />
							<form:hidden
								path="vialidadReferenciaSecundaria.tipoVialidad.clave"
								id="vialidadReferenciaSecundaria.tipoVialidad.clave.hidden" />
						</div>
					</fieldset>

					<!-- Vialidad referencia posterior-->
					<fieldset class="fsInterno">
						<legend>
							<spring:message code="label.vialidadReferenciaPosterior" />:
						</legend>

						<div>
							<form:errors path="vialidadReferenciaPosterior.clave"
								cssClass="error" />
							<span id="vialidadReferenciaPosterior.claveError"
								class="error hiddenElement"></span>
						</div>

						<div id="vialidadReferenciaPosterior">
							<label class="error" id="vialidadReferenciaPosteriorError"
								style="display: none;"></label>
							<div class="row">
								<div class="col-xs-8">
									<div class="input-group input-group-sm">
										<input type="text" class="autocompleteInput form-control"
											id="vialidadReferenciaPosteriorInput" data-provide="typeahead"
											autocomplete="off"
											value="${domicilio.vialidadReferenciaPosterior.nombre}"
											name="vialidadReferenciaPosterior.nombre" />
										<span class="input-group-addon"> 
											<img alt="Cargando" src="${staticResourcesPath}/imagenes/loading.gif"
												style="height: 21px; width: 21px; display: none;" />
										</span>
									</div>
								</div>
								<div class="col-xs-4" style="padding-left: 0px;">
									<input type="text" id="vialidadReferenciaPosteriorLbl"
										readonly="readonly" class="autocompleteLbl input-sm"
										value="${domicilio.vialidadReferenciaPosterior.tipoVialidad.descripcion}" />
								</div>
								
							</div>
							<form:hidden path="vialidadReferenciaPosterior.clave"
								id="vialidadReferenciaPosterior.clave.hidden" />
							<form:hidden
								path="vialidadReferenciaPosterior.tipoVialidad.descripcion"
								id="vialidadReferenciaPosterior.tipoVialidad.descripcion.hidden" />
							<form:hidden
								path="vialidadReferenciaPosterior.tipoVialidad.clave"
								id="vialidadReferenciaPosterior.tipoVialidad.clave.hidden" />
						</div>
					</fieldset>

					<!-- Descripcion del domicilio -->
					<fieldset class="fsInterno">
						<div>
							<form:errors path="descripcion" cssClass="error" />
						</div>
						<legend>
							<spring:message code="label.descripcion" />:
						</legend>
						<div>
							<form:textarea path="descripcion" maxlength="255"
								cssStyle="width: 100% !important; height: 120px !important; resize: vertical;"
								cols="10" cssClass="alfanumerico" />
						</div>
					</fieldset>
				</div>
			</fieldset>

			<!-- datos de la longitud y latitud -->
			<form:hidden path="latitud" />
			<form:hidden path="longitud" />
	</div>

	
</div>
	<div class="row">
		<c:choose>
			<c:when test="${not empty FROM_WIZARD}">

			</c:when>
			<c:when test="${not empty FROM_ADMON_DOMICILIO}">
				<fieldset class="fsInterno">
					<button type="button" style="margin-top: 5px;"
						class="btn btn-secondary" target="regresar" id="btnModificar">
						<spring:message code="label.btn.modificar" />
					</button>
				</fieldset>
			</c:when>
			<c:otherwise>
				<div class="col-md-6 text-left" style="padding: 15px"><span class="required"  id="labelCamposObligatoriosGeneral">*</span> Campos obligatorios</div>
				<div class="col-md-6 text-right">
						<button type="button" style="margin-top: 5px;"
							class="btn btn-default" target="regresar" id="regresar">
							<spring:message code="label.btn.regresar" />
						</button>
						<button type="submit" style="margin-top: 5px;"
							class="btn btn-primary" target="seguir" id="regresar">
							<spring:message code="label.btn.ubicar" />
						</button>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</form:form>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/domicilios/domicilios.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/domicilios/autocomplete.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/domicilios/domicilios-map.js" htmlEscape="true" />"></script>