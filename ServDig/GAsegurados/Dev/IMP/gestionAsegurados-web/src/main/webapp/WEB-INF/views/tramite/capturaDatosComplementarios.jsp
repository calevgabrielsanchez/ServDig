<!-- Vista para la captura de los datos de la persona -->
<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="asentamiento" value="${tramiteAsegurado.fisica.domicilios[0].asentamiento}" />

<c:if test="${not empty FROM_SIME}">
	<input type="hidden" id="fromSIME" value="true"/>
</c:if>

<div class="contenedor">
	<div id="info-paso">
		<h3 style="font-size: 1.8em !important">Paso 3: Captura de los
			datos complementarios de la persona</h3>
		<div class="textwidget">
			<p style="font-size: .9em;">Capture los datos complementarios de
				la persona (domicilio, medios de contacto).</p>
		</div>
	</div>

	<!-- Forma de la consulta de personas por datos basicos. -->
	<div>
		<div class="row">
			<form:form modelAttribute="tramiteAsegurado"
				id="registroPersonaFisicaForm"
				action="${contextpath}/tramite/concluir" cssClass="form-horizontal"
				role="form">

				<input type="hidden" name="hashDatosPersona"
					value="${hashDatosPersona }" />
				<form:hidden path="fisica.idPersona" id="idPersona" />
				<form:hidden path="fisica.umf.idUMF" id="idUmfAsegurado" />
				<form:hidden path="fisica.umf.noEconomico"
					id="noEconomicoUmfAsegurado" />
				<form:hidden path="fisica.umf.subdelegacion.id"
					id="idSubdelegacionAsegurado" />
				<form:hidden path="fisica.umf.subdelegacion.clave"
					id="cveSubdelegacionAsegurado" />
				<form:hidden path="fisica.umf.subdelegacion.delegacion.id"
					id="idDelegacionAsegurado" />
				<form:hidden path="fisica.umf.subdelegacion.delegacion.clave"
					id="cveDelegacionAsegurado" />
				<form:hidden path="fisica.umf.subdelegacion.delegacion.ciz"
					id="cveCizAsegurado" />

				<div class="col-md-7">
					<div id="datosBasicosDiv">
						<fieldset>
							<legend>
								Datos B&aacute;sicos de la Persona
							</legend>

							<div class="form-group">
								<label for="registroCurp" class="col-xs-4 control-label">
									CURP </label>
								<div class="col-xs-8">
									<form:input path="fisica.curp" id="registroCurp"
										cssClass="form-control" maxlength="18" />
									<span id="curpError" class="error hiddenElement"></span>
								</div>
							</div>

							<div class="form-group">
								<label for="registroNombres" class="col-xs-4 control-label">
									Nombre(s) </label>
								<div class="col-xs-8">
									<form:input path="fisica.nombre" id="registroNombres"
										cssClass="form-control" maxlength="50" />
								</div>
							</div>

							<div class="form-group">
								<label for="registroPrimerApellido"
									class="col-xs-4 control-label"> Primer Apellido </label>
								<div class="col-xs-8">
									<form:input path="fisica.primerApellido"
										id="registroPrimerApellido" cssClass="form-control"
										maxlength="50" />
								</div>
							</div>

							<div class="form-group">
								<label for="registroSegundoApellido"
									class="col-xs-4 control-label"> Segundo Apellido </label>
								<div class="col-xs-8">
									<form:input path="fisica.segundoApellido"
										id="registroSegundoApellido" cssClass="form-control"
										maxlength="50" />
								</div>
							</div>


							<div class="form-group">
								<label for="fisica.sexo.idSexo" class="col-xs-4 control-label">
									Sexo </label>
								<div class="col-xs-8">
									<combo:creaCombo idHtml="fisica.sexo.idSexo"
										idHtmlContenedor="registroPersonaFisicaForm"
										entidad="mx.gob.imss.ctirss.delta.persistence.DicSexo"
										idHtmlValor="${tramiteAsegurado.fisica.sexo.idSexo}"
										mostrarSoloActivos="true" cssClassname="form-control" />
									<span id="fisica.sexo.idSexoError" class="error hiddenElement"></span>
									<form:hidden path="fisica.sexo.descripcion"
										id="sexo.descripcion" />
								</div>
							</div>

							<div class="form-group">
								<label for="registroFechaNacimientoC"
									class="col-xs-4 control-label"> Fecha de Nacimiento </label>
								<div class="col-xs-8">
									<form:input path="fisica.fechaNacimiento"
										id="registroFechaNacimientoC" style="width: auto"
										cssClass="form-control" maxlength="10" />
								</div>
							</div>

							<div class="form-group">
								<label for="fisica.lugarNacimiento.clave"
									class="col-xs-4 control-label"> Lugar de Nacimiento </label>
								<div class="col-xs-8">
									<combo:creaCombo idHtml="fisica.lugarNacimiento.clave"
										idHtmlContenedor="registroPersonaFisicaForm"
										entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado"
										idHtmlValor="${tramiteAsegurado.fisica.lugarNacimiento.clave}"
										mostrarSoloActivos="true" cssClassname="form-control" />
									<span id="fisica.lugarNacimiento.claveError"
										class="error hiddenElement"></span>
									<form:hidden path="fisica.lugarNacimiento.nombre"
										id="lugarNacimiento.nombre" />
								</div>
							</div>
							<span id="errorFormGeneralError" class="error hiddenElement"></span>
						</fieldset>
						<fieldset>
							<legend>
								<span class="required">*</span>Unidad
									M&eacute;dico Familiar
								
							</legend>
							<div id="umfContenedor" style="width: 100%;">
								<div style="text-align: center;">
									<img alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
								</div>
							</div>
							<form:errors path="fisica.umf.idUMF" cssClass="error" />
						</fieldset>
					</div>

					<div id="datosSerie">
						<fieldset>
							<legend>
								Datos de la serie asignada
							</legend>

							<div class="form-group">
								<label for="registroFechaNacimientoC"
									class="col-xs-4 control-label">Tipo de Serie:</label>
								<div class="col-xs-8">
									<input type="text" readonly="readonly"
										name="asignacionSerieNss.serie.tipoSerie.descripcion"
										value="${tramiteAsegurado.asignacionSerieNss.serie.tipoSerie.descripcion}"
										class="form-control" />
									<form:hidden
										path="asignacionSerieNss.serie.tipoSerie.idTipoSerie" />
								</div>
							</div>
						</fieldset>
					</div>
				</div>

				<div class="col-md-5" id="datosComplementarios">
					<c:choose>
						<c:when test="${not empty FROM_SIME}">
							<fieldset
								style="background: none repeat scroll 0 0 #F5F5F5; border: 1px solid #E5E5E5;">
								<legend>
									<span class="required">*</span>C&oacute;digo
										Postal
								</legend>
								<input type="text" id="codigoPostalExtranjero"
									value="${EXTRANJERO_SIME.codigoPostal}" readonly="readonly" />
							</fieldset>
						</c:when>
						<c:otherwise>
							<div id="domicilio" class="well p-xs">
								<fieldset style="width: 100%;">
									<legend>
										<span class="required">*</span>Datos del
											domicilio ubicado
									</legend>

									<div id="domicilioLocaliza"></div>
									<div id="datos">
										<form:errors path="fisica.domicilios" cssClass="error" />
										<form:hidden path="fisica.domicilios[0].clave" />

										<c:choose>
											<c:when
												test="${empty tramiteAsegurado.fisica.domicilios[0].vialidadPrimaria.clave}">
												<c:set var="addressClass" value="hidden" />
												<div id="msgSinDomicilio" class="alert alert-warning">
													Usted no cuenta con domicilio particular
												</div>
											</c:when>
											<c:otherwise>
												<c:set var="addressClass" value="showElement" />
											</c:otherwise>
										</c:choose>

										<address id="addressParticular" class="${addressClass}">
											<spring:message code="label.calle.num" />
											: <span id="nombreVialidadPrimaria">
												${tramiteAsegurado.fisica.domicilios[0].vialidadPrimaria.nombre}
											</span> <span id="numExterior">
												${tramiteAsegurado.fisica.domicilios[0].numExterior1} </span> <span
												id="numExteriorAlfa">
												${tramiteAsegurado.fisica.domicilios[0].numExteriorAlf} </span>, <span
												id="numInterior">
												${tramiteAsegurado.fisica.domicilios[0].numInterior} </span> <span
												id="numInteriorAlfa">
												${tramiteAsegurado.fisica.domicilios[0].numInteriorAlf} </span><br>
											<spring:message code="label.colonia" />
											: <span id="nombreAsentamiento">
												${tramiteAsegurado.fisica.domicilios[0].asentamiento.nombre}
											</span><br>
											<spring:message code="label.municipio" />
											: <span id="nombreMunicipio">
												${tramiteAsegurado.fisica.domicilios[0].asentamiento.localidad.municipio.nombre}
											</span><br>
											<spring:message code="label.entidadFederativa" />
											: <span id="nombreEstado">
												${tramiteAsegurado.fisica.domicilios[0].asentamiento.localidad.municipio.entidadFederativa.nombre}
											</span><br> C.P. <span id="codigoPostal">
												${tramiteAsegurado.fisica.domicilios[0].asentamiento.codigoPostal.codigoPostal}
											</span><br>
										</address>
										<form:hidden path="fisica.domicilios[0].calle" id="calle" />
										<form:hidden
											path="fisica.domicilios[0].asentamiento.codigoPostal.codigoPostal"
											id="codigoPostal" />
										<form:hidden
											path="fisica.domicilios[0].asentamiento.localidad.municipio.entidadFederativa.nombre"
											id="entidadFederativa" />
										<form:hidden
											path="fisica.domicilios[0].asentamiento.localidad.municipio.entidadFederativa.clave"
											id="entidadFederativaClave" />
										<form:hidden
											path="fisica.domicilios[0].asentamiento.localidad.municipio.nombre"
											id="municipio" />
										<form:hidden
											path="fisica.domicilios[0].asentamiento.localidad.municipio.clave"
											id="municipioClave" />
										<form:hidden
											path="fisica.domicilios[0].asentamiento.localidad.nombre"
											id="localidad" />
										<form:hidden
											path="fisica.domicilios[0].asentamiento.localidad.clave"
											id="localidadClave" />
										<form:hidden path="fisica.domicilios[0].asentamiento.nombre"
											id="asentamiento" />
										<form:hidden path="fisica.domicilios[0].asentamiento.clave"
											id="asentamientoClave" />
										<form:hidden
											path="fisica.domicilios[0].vialidadPrimaria.nombre"
											id="vialidadPrimaria" />
										<form:hidden
											path="fisica.domicilios[0].vialidadPrimaria.clave"
											id="vialidadPrimaria.clave" />

										<!-- Atributos de domicilio carretera -->
										<form:hidden
											path="fisica.domicilios[0].domicilioCarretera.terminoGeneral.descripcion"
											id="domCar.ter.des" />
										<form:hidden
											path="fisica.domicilios[0].domicilioCarretera.terminoGeneral.clave"
											id="domCar.ter.cve" />
										<form:hidden
											path="fisica.domicilios[0].domicilioCarretera.derechoTransito.descripcion"
											id="domCar.der.des" />
										<form:hidden
											path="fisica.domicilios[0].domicilioCarretera.derechoTransito.clave"
											id="domCar.der.cve" />
										<form:hidden
											path="fisica.domicilios[0].domicilioCarretera.origen"
											id="domCar.or" />
										<form:hidden
											path="fisica.domicilios[0].domicilioCarretera.destino"
											id="domCar.des" />
										<form:hidden
											path="fisica.domicilios[0].domicilioCarretera.administracion.descripcion"
											id="domCar.adm.des" />
										<form:hidden
											path="fisica.domicilios[0].domicilioCarretera.administracion.clave"
											id="domCar.adm.cve" />
										<form:hidden
											path="fisica.domicilios[0].domicilioCarretera.cadenamiento"
											id="domCar.cad" />
										<form:hidden
											path="fisica.domicilios[0].domicilioCarretera.codigoCarretera"
											id="domCar.cod" />

										<!-- Atrbutos de domicilio camino -->
										<form:hidden
											path="fisica.domicilios[0].domicilioCamino.terminoGeneral.descripcion"
											id="domCam.ter.des" />
										<form:hidden
											path="fisica.domicilios[0].domicilioCamino.terminoGeneral.clave"
											id="domCam.ter.cve" />
										<form:hidden
											path="fisica.domicilios[0].domicilioCamino.margen.descripcion"
											id="domCam.mar.des" />
										<form:hidden
											path="fisica.domicilios[0].domicilioCamino.margen.clave"
											id="domCam.mar.cve" />
										<form:hidden
											path="fisica.domicilios[0].domicilioCamino.origen"
											id="domCam.or" />
										<form:hidden
											path="fisica.domicilios[0].domicilioCamino.destino"
											id="domCam.des" />
										<form:hidden
											path="fisica.domicilios[0].domicilioCamino.cadenamiento"
											id="domCam.cad" />

										<form:hidden path="fisica.domicilios[0].numExterior1"
											id="numeroExteriorPrincipal" />
										<form:hidden path="fisica.domicilios[0].numExteriorAlf"
											id="numeroExteriorAlfanumerico" />
										<form:hidden path="fisica.domicilios[0].numInterior"
											id="numeroInterior" />
										<form:hidden path="fisica.domicilios[0].numInteriorAlf"
											id="numeroInteriorAlfanumerico" />
										<form:hidden path="fisica.domicilios[0].numExterior2"
											id="numeroExteriorSecundario" />
										<form:hidden
											path="fisica.domicilios[0].vialidadReferenciaPrimaria.nombre"
											id="vialidadReferenciaPrimaria" />
										<form:hidden
											path="fisica.domicilios[0].vialidadReferenciaPrimaria.clave"
											id="vialidadReferenciaPrimaria.clave" />
										<form:hidden
											path="fisica.domicilios[0].vialidadReferenciaSecundaria.nombre"
											id="vialidadReferenciaSecundaria" />
										<form:hidden
											path="fisica.domicilios[0].vialidadReferenciaSecundaria.clave"
											id="vialidadReferenciaSecundaria.clave" />
										<form:hidden
											path="fisica.domicilios[0].vialidadReferenciaPosterior.nombre"
											id="vialidadReferenciaPosterior" />
										<form:hidden
											path="fisica.domicilios[0].vialidadReferenciaPosterior.clave"
											id="vialidadReferenciaPosterior.clave" />
										<form:hidden path="fisica.domicilios[0].descripcion"
											id="descripcion" />
										<form:hidden path="fisica.domicilios[0].latitud" id="latitud" />
										<form:hidden path="fisica.domicilios[0].longitud"
											id="longitud" />
									</div>

									<c:if test="${empty tramiteAsegurado.fisica.domicilios[0].clave }">
										<div class="row">
											<div class="col-sm-12 text-right">
												<button type="button" id="btnUbicarDomicilio"
													class="btn btn-default">UBICAR DOMICILIO</button>
											</div>
										</div>
									</c:if>
								</fieldset>
							</div>

							<div id="mediosContacto" class="well p-xs">
								<fieldset>
									<legend>
										Medios de contacto
									</legend>

									<div id="mediosContactoDatos">
										<div>
											<form:errors path="fisica.correoElectronico.correo"
												cssClass="error" id="cveCorreo" />
											<form:hidden path="fisica.correoElectronico.clave"
												id="cveCorreo" />
											<div class="form-group">
												<label for="correoElectronico"
													class="col-xs-12 control-label mail"
													style="text-align: left;"> Correo
													Electr&oacute;nico </label>
												<div class="col-xs-12">
													<form:input path="fisica.correoElectronico.correo"
														id="correoElectronico" cssClass="form-control"
														maxlength="50" />
												</div>
											</div>
								</fieldset>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</form:form>
		</div>
		<div class="row">
			<div class="col-md-7 col-sm-12 m-t-lg text-right">
				<c:choose>
					<c:when test="${empty FROM_SIME}">
						<button type="button" id="regresar" class="btn btn-default">REGRESAR</button>
					</c:when>
					<c:otherwise>
						<button type="button" id="ignoreCurrentRecord"
							class="btn btn-warning">PENDIENTE</button>
					</c:otherwise>
				</c:choose>
				<button type="button" id="registrar" class="btn btn-primary">CONCLUIR</button>
			</div>
		</div>
	</div>
</div>

<c:choose>
	<c:when test="${not empty FROM_SIME}">
		<form id="extranjeroSIMEIgnoreForm"
			action="${contextpath}/sime/ignorar/registro-en-proceso" method="post"></form>	
	</c:when>
	<c:otherwise>
		<!-- Forma auxiliar para consultar las UMF's -->
		<form id="asentamientoForUmfForm">
			<input type="hidden" name="clave" value="${asentamiento.clave}" id="cveAsentamientoAux" /> 
			<input type="hidden" name="nombre" value="${asentamiento.nombre}" id="nombreAsentamientoAux" /> 
			<input type="hidden" name="localidad.clave"	value="${asentamiento.localidad.clave}"	id="localidadCveAsentamientoAux" /> 
			<input type="hidden" name="localidad.municipio.clave" value="${asentamiento.localidad.municipio.clave}" id="municipioCveAsentamientoAux" /> 
			<input type="hidden" name="localidad.municipio.entidadFederativa.clave" value="${asentamiento.localidad.municipio.entidadFederativa.clave}" id="estadoCveAsentamientoAux" /> 
			<input type="hidden" name="codigoPostal.codigoPostal" value="${asentamiento.codigoPostal.codigoPostal}" id="cpAsentamientoAux" /> 
			<input type="hidden" name="tipoAsentamiento.clave" value="${asentamiento.tipoAsentamiento.clave}" id="tipoAsentamientoAux" />
		</form>
	</c:otherwise>
</c:choose>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/tramite/tramite-capturaComplementos.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/tramite/localizar-UMF.js" htmlEscape="true" />"></script>