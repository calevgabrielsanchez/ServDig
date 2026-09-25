<!-- Vista para la captura de los datos de la persona -->
<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum"%>

<c:set var="tipoActaNacimiento"><%=DocumentoPorTipoEnum.ACTA_NACIMIENTO.getId()%></c:set>
<c:set var="tipoCartaNaturalizacion"><%=DocumentoPorTipoEnum.CARTA_NATURALIZACION.getId()%></c:set>
<c:set var="tipoDocumentoMigratorio"><%=DocumentoPorTipoEnum.DOCUMENTO_MIGRATORIO.getId()%></c:set>
<c:set var="tipoNumeroUnicoExtranjero"><%=DocumentoPorTipoEnum.NUMERO_UNICO_EXTRANJERO.getId()%></c:set>
<c:set var="tipoCertificadoNacionalidad"><%=DocumentoPorTipoEnum.CERTIFICADO_NACIONALIDAD_MEXICANA.getId()%></c:set>
<c:set var="tipoOficioSolicitanteRef"><%=DocumentoPorTipoEnum.OFICIO_SOLICITANTE_REFUGIADO.getId()%></c:set>
<c:set var="tipoFormaMigratoriaTurista"><%=DocumentoPorTipoEnum.FORMA_MIGRATORIA_TURISTA.getId()%></c:set>

<div class="contenedor">
	<div>
		<div id="info-paso">
			<c:choose>
				<c:when test="${empty tramiteAsegurado.fisica.nss}">
					<h3 style="font-size: 1.8em !important">Paso 2:
						Confirmaci&oacute;n de los datos de la Persona</h3>
					<div class="textwidget">
						<p style="font-size: .9em;">Confirme si los datos de la
							persona son los correctos</p>
					</div>
				</c:when>
				<c:otherwise>
					<h3 style="font-size: 1.8em !important">Paso 2: Resultado de
						la b&uacute;squeda</h3>
				</c:otherwise>
			</c:choose>
		</div>
			
		<div class="alert alert-info"  style="text-align: center;">
			<strong><i class="glyphicon glyphicon-info-sign"
				style="margin-right: 8px;"></i>
				<c:choose>
					<c:when test="${tramiteAsegurado.fisica.idPersona ne 0}">
						La persona fue localizada en el IMSS
						<c:if test="${not empty tramiteAsegurado.fisica.nss}">
							 con un NSS asignado
						</c:if>
					</c:when>
					<c:when test="${not empty tramiteAsegurado.fisica.estatusRenapo}">
						La persona fue localizada en el RENAPO
					</c:when>
					<c:otherwise>
						La persona se registrar&aacute; manualmente
					</c:otherwise>
				</c:choose>
			</strong>
		</div>

		<!-- Forma de la consulta de personas por datos basicos. -->
		<div class="contenedor">
			<c:set var="contextpath" value="<%=request.getContextPath()%>" />
			<div class="row">
				<form:form modelAttribute="tramiteAsegurado"
					id="registroPersonaFisicaForm"
					action="${contextpath}/tramite/crear"
					cssClass="form-horizontal" role="form">
					<input type="hidden" name="hashDatosPersona" id="hashDatosPersona" value="${hashDatosPersona }" />
					<input type="hidden" name="idSolicitud" id="idSolicitud" value="${idSolicitud }" />
					
					<div class="col-md-7">
						<div id="datosBasicosDiv">
							<fieldset>
								<legend>
									Datos B&aacute;sicos de la Persona
								</legend>
								<form:hidden path="fisica.idPersona" />
								
								<c:if test="${not empty tramiteAsegurado.fisica.nss}">
									<div class="alert alert-warning" id="notDownloadedWarning"
										style="text-align: center; display: none;">
										<strong><i class="glyphicon glyphicon-warning-sign"
											style="margin-right: 8px;"></i>El comprobante no ha sido
											descargado</strong>
									</div>
								</c:if>

									<div class="form-group">
										<label for="registroCurp" class="col-xs-4 control-label">
											CURP
										</label>
										<div class="col-xs-8">
											<form:input path="fisica.curp" id="registroCurp"
												cssClass="form-control"
												maxlength="18" />
											<span id="curpError" class="error hiddenElement"></span>
										</div>
									</div>

									<div class="form-group">
										<label for="registroNombres" class="col-xs-4 control-label">
											Nombre(s)
										</label>
										<div class="col-xs-8">
											<form:input path="fisica.nombre" id="registroNombres"
												cssClass="form-control"
												maxlength="50" />
										</div>
									</div>
									<div class="form-group">
										<label for="registroPrimerApellido" class="col-xs-4 control-label">
											Primer Apellido
										</label>
										<div class="col-xs-8">
											<form:input path="fisica.primerApellido"
												id="registroPrimerApellido" 
												cssClass="form-control" maxlength="50" />
										</div>
									</div>
									<div class="form-group">
										<label for="registroSegundoApellido" class="col-xs-4 control-label">
											Segundo Apellido
										</label>
										<div class="col-xs-8">
											<form:input path="fisica.segundoApellido"
												id="registroSegundoApellido" 
												cssClass="form-control" maxlength="50" />
										</div>
									</div>
									<div class="form-group">
										<label for="fisica.sexo.idSexo" class="col-xs-4 control-label">
											Sexo
										</label>
										<div class="col-xs-8">
											<combo:creaCombo idHtml="fisica.sexo.idSexo"
												idHtmlContenedor="registroPersonaFisicaForm"
												entidad="mx.gob.imss.ctirss.delta.persistence.DicSexo"
												idHtmlValor="${tramiteAsegurado.fisica.sexo.idSexo}"
												mostrarSoloActivos="true" cssClassname="form-control"/>
											<span id="fisica.sexo.idSexoError"
												class="error hiddenElement"></span>
											<form:hidden path="fisica.sexo.descripcion"
												id="sexo.descripcion" />
										</div>
									</div>
									<div class="form-group">
										<label for="registroFechaNacimientoC" class="col-xs-4 control-label">
											Fecha de Nacimiento
										</label>
										<div class="col-xs-8">
											<form:input path="fisica.fechaNacimiento"
												id="registroFechaNacimientoC" style="width: auto"
												cssClass="form-control" maxlength="10" />
										</div>
									</div>
									<div class="form-group">
										<label for="fisica.lugarNacimiento.clave" class="col-xs-4 control-label">
											Lugar de Nacimiento
										</label>
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
								<c:if test="${not empty tramiteAsegurado.fisica.nss}">
									
									<div class="form-group">
										<label for="fisica.nss" class="col-xs-4 control-label">
											NSS
										</label>
										<div class="col-xs-8">
											<form:input path="fisica.nss" cssClass="form-control"  />
									</div>
								</c:if>
								<span id="errorFormGeneralError" class="error hiddenElement"></span>
							</fieldset>
						</div>

						<c:if test="${empty tramiteAsegurado.fisica.nss}">
							<div id="datosSerie">
								<fieldset>
									<legend>
										Datos de la Serie
									</legend>
																		
									<div class="form-group">
										<label for="asignacionSerieNss.serie.tipoSerie.idTipoSerie"
											class="col-xs-4 control-label"> <span
											class="required">*</span>Tipo de Serie
										</label>
										<div class="col-xs-8">
											<form:select
												path="asignacionSerieNss.serie.tipoSerie.idTipoSerie"
												cssClass="form-control">
												<form:option value="-1" label="--Por favor seleccione--" />
												<form:options items="${listTipoSerie}"
													itemLabel="descripcion" itemValue="idTipoSerie" />
											</form:select>
											<form:hidden
												path="asignacionSerieNss.serie.tipoSerie.descripcion" />
											<form:errors
												path="asignacionSerieNss.serie.tipoSerie.idTipoSerie"
												cssClass="error" />
										</div>
									</div>
									
								</fieldset>
							</div>
						</c:if>
						
						<c:if test="${not empty FROM_SIME && not empty tramiteAsegurado.fisica.nss}">
							<fieldset>
								<legend>
									<span class="required">*</span>Unidad M&eacute;dico Familiar
								</legend>
								<div id="umfContenedor" style="width: 100%;">
									<div style="text-align: center;">
										<img alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
									</div>
								</div>
								<form:errors path="fisica.umf.idUMF" cssClass="error" />
								<span id="erroUMF" class="error" style="display: none;" ></span>
							</fieldset>
						</c:if>
					</div>
					
					<div class="col-md-5" id="datosComplementarios">
						<div id="datosCurp" class="well p-xs">
							<fieldset style="width: 100%;">
								<legend>
									Datos de la C&eacute;dula (CURP)
								</legend>
	
								<div class="form-group">
									<label for="curpDocumento" class="col-xs-4 control-label">
										CURP
									</label>
									<div class="col-xs-8">
										<input id="curpDocumento" type="text" maxlength="50"
											value="${tramiteAsegurado.fisica.curp}" disabled="disabled"
											class="form-control"/>
									</div>
								</div>
								<c:forEach
									items="${tramiteAsegurado.fisica.documentosProbatorios}"
									var="documento" varStatus="index">
									<c:if test="${documento.documentoPorTipo.idDocumentoPorTipo eq tipoActaNacimiento}">
										<fieldset class="docProbatorio">
											<legend>
												Acta de nacimiento
											</legend>
											<form:hidden path="fisica.actaNacimiento.documentoPorTipo.idDocumentoPorTipo" />
											<div class="form-group">
												<label for="" class="col-xs-4 control-label">
													A&ntilde;o registro
												</label>
												<div class="col-xs-8">
													<form:input path="fisica.actaNacimiento.anio"
														maxlength="50" cssClass="form-control" />
												</div>
											</div>
											<div class="form-group">
												<label for="" class="col-xs-4 control-label">
													No. Libro
												</label>
												<div class="col-xs-8">
													<form:input path="fisica.actaNacimiento.noLibro"
														cssClass="form-control" maxlength="50" />
												</div>
											</div>
											<div class="form-group">
												<label for="" class="col-xs-4 control-label">
													No. Acta
												</label>
												<div class="col-xs-8">
													<form:input path="fisica.actaNacimiento.noActa"
														cssClass="form-control" maxlength="50" />
												</div>
											</div>
											<div class="form-group">
												<label for="" class="col-xs-4 control-label">
													No. Tomo
												</label>
												<div class="col-xs-8">
													<form:input path="fisica.actaNacimiento.tomo"
														cssClass="form-control" maxlength="50" />
												</div>
											</div>
											<div class="form-group">
												<label for="" class="col-xs-4 control-label">
													No. Foja
												</label>
												<div class="col-xs-8">
													<form:input path="fisica.actaNacimiento.noFoja"
														cssClass="form-control" maxlength="50" />
												</div>
											</div>
											<div class="form-group">
												<label for="" class="col-xs-4 control-label">
													CRIP
												</label>
												<div class="col-xs-8">
													<form:input path="fisica.actaNacimiento.crip"
														cssClass="form-control" maxlength="50" />
												</div>
											</div>
	
											<input id="fisica.actaNacimiento.noJuzgado"
												name="fisica.actaNacimiento.noJuzgado" value="0"
												type="hidden">
											<form:hidden
												path="fisica.actaNacimiento.municipio.entidadFederativa.clave" />
											<form:hidden path="fisica.actaNacimiento.municipio.clave" />
										</fieldset>
									</c:if>
									<c:if test="${documento.documentoPorTipo.idDocumentoPorTipo eq tipoCartaNaturalizacion}">
										<fieldset class="docProbatorio">
											<legend>
												<c:out value="${documento.documentoPorTipo.documento.desDocumento }" />
											</legend>
	
											<form:hidden
												path="fisica.cartaNaturalizacion.documentoPorTipo.idDocumentoPorTipo" />
											<form:hidden
												path="fisica.cartaNaturalizacion.numTipoDocumento" />
											<form:hidden
												path="fisica.cartaNaturalizacion.descripcionTipoDocumento" />
											<form:hidden path="fisica.cartaNaturalizacion.curp" />
											
											<div class="form-group">
												<label for="fisica.cartaNaturalizacion.numFolioExtranjero" class="col-xs-4 control-label">Folio</label>
												<div class="col-xs-8">
													<form:input
														path="fisica.cartaNaturalizacion.numFolioExtranjero"
														cssClass="form-control" maxlength="50" />
												</div>
											</div>
	
											<div class="form-group">
												<label for="fisica.cartaNaturalizacion.anioRegistro" class="col-xs-4 control-label">A&ntilde;o registro</label>
												<div class="col-xs-8">
													<form:input path="fisica.cartaNaturalizacion.anioRegistro"
														cssClass="form-control" maxlength="50" />
												</div>
											</div>
										</fieldset>
									</c:if>
									<c:if
										test="${documento.documentoPorTipo.idDocumentoPorTipo eq tipoDocumentoMigratorio}">
										<fieldset>
											<legend>
												<c:out value="${documento.documentoPorTipo.documento.desDocumento }" />
											</legend>
	
											<form:hidden
												path="fisica.documentoMigratorio.documentoPorTipo.idDocumentoPorTipo" />
											<form:hidden
												path="fisica.documentoMigratorio.numTipoDocumento" />
											<form:hidden
												path="fisica.documentoMigratorio.descripcionTipoDocumento" />
											<form:hidden path="fisica.documentoMigratorio.curp" />
	
											<div class="form-group">
												<label for="fisica.documentoMigratorio.numFolioExtranjero" class="col-xs-4 control-label">Folio</label>
												<div class="col-xs-8">
													<form:input
														path="fisica.documentoMigratorio.numFolioExtranjero"
														cssClass="form-control" maxlength="50" />
												</div>
											</div>
										</fieldset>
									</c:if>
									<c:if
										test="${documento.documentoPorTipo.idDocumentoPorTipo eq tipoNumeroUnicoExtranjero}">
										<fieldset>
											<legend>
												<c:out value="${documento.documentoPorTipo.documento.desDocumento }" />
											</legend>
	
											<form:hidden
												path="fisica.numeroUnicoExtranjero.documentoPorTipo.idDocumentoPorTipo" />
											<form:hidden
												path="fisica.numeroUnicoExtranjero.numTipoDocumento" />
											<form:hidden
												path="fisica.numeroUnicoExtranjero.descripcionTipoDocumento" />
											<form:hidden path="fisica.numeroUnicoExtranjero.curp" />
	
											<div class="form-group">
												<label for="fisica.numeroUnicoExtranjero.numFolioExtranjero" class="col-xs-4 control-label">Folio</label>
												<div class="col-xs-8">
													<form:input
														path="fisica.numeroUnicoExtranjero.numFolioExtranjero"
														cssClass="form-control" maxlength="50" />
												</div>
											</div>
										</fieldset>
									</c:if>
									<c:if test="${documento.documentoPorTipo.idDocumentoPorTipo eq tipoCertificadoNacionalidad}">
										<fieldset class="docProbatorio">
											<legend>
												<c:out value="${documento.documentoPorTipo.documento.desDocumento }" />
											</legend>
	
											<form:hidden
												path="fisica.certificadoNacionalidadMexicana.documentoPorTipo.idDocumentoPorTipo" />
											<form:hidden
												path="fisica.certificadoNacionalidadMexicana.numTipoDocumento" />
											<form:hidden
												path="fisica.certificadoNacionalidadMexicana.descripcionTipoDocumento" />
											<form:hidden
												path="fisica.certificadoNacionalidadMexicana.curp" />
	
											<div class="form-group">
												<label for="fisica.certificadoNacionalidadMexicana.numFolioExtranjero" class="col-xs-4 control-label">Folio</label>
												<div class="col-xs-8">
													<form:input
														path="fisica.certificadoNacionalidadMexicana.numFolioExtranjero"
														cssClass="form-control" maxlength="50" />
												</div>
											</div>
	
											<div class="form-group">
												<label for="fisica.certificadoNacionalidadMexicana.anioRegistro" class="col-xs-4 control-label">A&ntilde;o registro</label>
												<div class="col-xs-8">
													<form:input
														path="fisica.certificadoNacionalidadMexicana.anioRegistro"
														cssClass="form-control" maxlength="50" />
												</div>
											</div>
										</fieldset>
									</c:if>
									<c:if test="${documento.documentoPorTipo.idDocumentoPorTipo eq tipoOficioSolicitanteRef}">
										<fieldset class="docProbatorio">
											<legend>
												<c:out value="${documento.documentoPorTipo.documento.desDocumento }" />
											</legend>
	
											<form:hidden
												path="fisica.oficioSolicitanteRefugiado.documentoPorTipo.idDocumentoPorTipo" />
											<form:hidden
												path="fisica.oficioSolicitanteRefugiado.numTipoDocumento" />
											<form:hidden
												path="fisica.oficioSolicitanteRefugiado.descripcionTipoDocumento" />
											<form:hidden path="fisica.oficioSolicitanteRefugiado.curp" />
	
											<div class="form-group">
												<label for="fisica.oficioSolicitanteRefugiado.numFolioExtranjero" class="col-xs-4 control-label">Folio</label>
												<div class="col-xs-8">
													<form:input
														path="fisica.oficioSolicitanteRefugiado.numFolioExtranjero"
														cssClass="form-control" maxlength="50" />
												</div>
											</div>
										</fieldset>
									</c:if>
									<c:if test="${documento.documentoPorTipo.idDocumentoPorTipo eq tipoFormaMigratoriaTurista}">
										<fieldset class="docProbatorio">
											<legend>
												<c:out value="${documento.documentoPorTipo.documento.desDocumento }" />
											</legend>
	
											<form:hidden
												path="fisica.formaMigratoriaTurista.documentoPorTipo.idDocumentoPorTipo" />
											<form:hidden
												path="fisica.formaMigratoriaTurista.numTipoDocumento" />
											<form:hidden
												path="fisica.formaMigratoriaTurista.descripcionTipoDocumento" />
											<form:hidden path="fisica.formaMigratoriaTurista.curp" />
	
											<div class="form-group">
												<label for="fisica.formaMigratoriaTurista.numFolioExtranjero" class="col-xs-4 control-label">Folio</label>
												<div class="col-xs-8">
													<form:input
														path="fisica.formaMigratoriaTurista.numFolioExtranjero"
														cssClass="form-control" maxlength="50" />
												</div>
											</div>
										</fieldset>
									</c:if>
								</c:forEach>
							</fieldset>
						</div>
						<c:if test="${not empty FROM_SIME && not empty tramiteAsegurado.fisica.nss}">
							<div class="well p-xs">
								<fieldset>
									<legend>
										C&oacute;digo Postal
									</legend>
									<input type="text" id="codigoPostalExtranjero"
										value="${EXTRANJERO_SIME.codigoPostal}" readonly="readonly" />
								</fieldset>
							</div>
						</c:if>
					</div>
				</form:form>
			</div>
			<div class="row">
				<div class="col-md-7 col-sm-12 text-right m-t-md">
					<c:choose>
						<c:when test="${empty FROM_SIME}">
							<button type="button" id="regresar" class="btn btn-default">REGRESAR</button>
						</c:when>
						<c:otherwise>
							<button type="button" id="ignoreCurrentRecord" class="btn btn-warning">PENDIENTE</button>
						</c:otherwise>
					</c:choose>

					<c:choose>
						<c:when test="${empty tramiteAsegurado.fisica.nss}">
							<button type="button" id="registrar" class="btn btn-primary">CONTINUAR</button>
						</c:when>
						<c:otherwise>
							<button type="button" id="btnDescargar" class="btn btn-primary">DESCARGAR COMPROBANTE</button>
							<c:if test="${not empty FROM_SIME}">
								<button type="button" id="nextRecordBtn" class="btn btn-primary">CONTINUAR</button>
							</c:if>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</div>
</div>

<c:if test="${not empty tramiteAsegurado.fisica.nss}">
	<form action="" id="formComprobante" method="get" class="formNotBlock"
		alreadyDownloaded="false">
		<input type="hidden" name="si" id="si" />
	</form>
</c:if>

<form action="" class="formNotBlock" id="regresarForm"></form>

<c:if test="${not empty FROM_SIME}">
	<input type="hidden" id="fromSIME" value="true"/>
	
	<form id="extranjeroSIMERecuperadoForm"
		action="${contextpath}/sime/nss/recuperado" method="post">
		<input type="hidden" name="llaveRegistro" value="${EXTRANJERO_SIME.llaveRegistro}" />
		<input type="hidden" name="nss" value="${tramiteAsegurado.fisica.nss }" />
		<input type="hidden" name="umf.idUMF" id="idUmfAsegurado" />
		<input type="hidden" name="umf.noEconomico" id="noEconomicoUmfAsegurado" />
		<input type="hidden" name="umf.subdelegacion.id" id="idSubdelegacionAsegurado" />
		<input type="hidden" name="umf.subdelegacion.clave" id="cveSubdelegacionAsegurado" />
		<input type="hidden" name="umf.subdelegacion.delegacion.id" id="idDelegacionAsegurado" />
		<input type="hidden" name="umf.subdelegacion.delegacion.clave" id="cveDelegacionAsegurado" />
		<input type="hidden" name="umf.subdelegacion.delegacion.ciz" id="cveCizAsegurado" />
	</form>
	
	<form id="extranjeroSIMEIgnoreForm"
		action="${contextpath}/sime/ignorar/registro-en-proceso" method="post">
	</form>
</c:if>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/tramite/tramite-capturaLibre.js" htmlEscape="true" />"></script>

<c:if test="${not empty FROM_SIME}">
	<script type="text/javascript"
		src="<spring:url value="/static/resources/js/delta/tramite/localizar-UMF.js" htmlEscape="true" />"></script>
</c:if>