<!-- Vista para la captura de los datos de la persona -->
<%@ include file="../general/taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum"%>

<c:set var="tipoActaNacimiento"><%=DocumentoPorTipoEnum.ACTA_NACIMIENTO.getId()%></c:set>
<c:set var="tipoCartaNaturalizacion"><%=DocumentoPorTipoEnum.CARTA_NATURALIZACION.getId()%></c:set>
<c:set var="tipoDocumentoMigratorio"><%=DocumentoPorTipoEnum.DOCUMENTO_MIGRATORIO.getId()%></c:set>
<c:set var="tipoNumeroUnicoExtranjero"><%=DocumentoPorTipoEnum.NUMERO_UNICO_EXTRANJERO.getId()%></c:set>
<c:set var="tipoCertificadoNacionalidad"><%=DocumentoPorTipoEnum.CERTIFICADO_NACIONALIDAD_MEXICANA.getId()%></c:set>
<c:set var="tipoOficioSolicitanteRef"><%=DocumentoPorTipoEnum.OFICIO_SOLICITANTE_REFUGIADO.getId()%></c:set>
<c:set var="tipoFormaMigratoriaTurista"><%=DocumentoPorTipoEnum.FORMA_MIGRATORIA_TURISTA.getId()%></c:set>

<c:set var="asentamiento" value="${tramiteAsegurado.fisica.domicilios[0].asentamiento}" />

<div class="page_holder_no_height">
	<div class="post_entry_wide no-border">
		<div id="info-paso">
			<h3 style="font-size: 1.8em !important">Paso 3: Captura de los datos complementarios de la persona (Solicitud pendiente)</h3>
			<div class="textwidget">
				<p style="font-size: .9em;">Capture los datos complementarios de la persona (domicilio, medios de contacto, documentos probatorios).</p>
			</div>
		</div>

		<!-- Forma de la consulta de personas por datos basicos. -->
		<div class="form-comment">
			<c:set var="contextpath" value="<%=request.getContextPath()%>" />

			<div id="acordeon" class="contenedor">
				<div class="row" style="width: 900px;">
					<div>
						<h2 style="font-size: .8em !important">Folio de la solicitud pendiente: <strong style="font-size: 1.2em">${folio}</strong></h2>
					</div>

					<form:form modelAttribute="tramiteAsegurado" id="paso3Form" action="${contextpath}/tramite/pendiente/concluir/">
						<form:hidden path="fisica.umf.idUMF" id="idUmfAsegurado"/>
						<form:hidden path="fisica.umf.noEconomico" id="noEconomicoUmfAsegurado"/>
						<form:hidden path="fisica.umf.subdelegacion.id" id="idSubdelegacionAsegurado"/>
						<form:hidden path="fisica.umf.subdelegacion.clave" id="cveSubdelegacionAsegurado"/>
						<form:hidden path="fisica.umf.subdelegacion.delegacion.id" id="idDelegacionAsegurado"/>
						<form:hidden path="fisica.umf.subdelegacion.delegacion.clave" id="cveDelegacionAsegurado"/>
						<form:hidden path="fisica.umf.subdelegacion.delegacion.ciz" id="cveCizAsegurado"/>
						<div class="cell" style="width: 60%;">
							<div id="datosBasicosDiv">
								<fieldset>
									<legend>
										<strong>&nbsp;Datos B&aacute;sicos de la Persona&nbsp;</strong>
									</legend>

									<form:label path="fisica.curp" cssClass="wide">CURP</form:label>
									<form:input path="fisica.curp" id="registroCurp" cssStyle="width: 300px" maxlength="18" />
									<span id="curpError" class="error hiddenElement"></span>
									<br /><br /><br />

									<form:label path="fisica.nombre" cssClass="wide">Nombre(s)</form:label>
									<form:input path="fisica.nombre" id="registroNombres" cssStyle="width: 300px" maxlength="50" />
									<br /><br /><br />

									<form:label path="fisica.primerApellido" cssClass="wide">Primer Apellido</form:label>
									<form:input path="fisica.primerApellido" id="registroPrimerApellido" cssStyle="width: 300px" maxlength="50" />
									<br /><br /><br />

									<form:label path="fisica.segundoApellido" cssClass="wide">Segundo Apellido</form:label>
									<form:input path="fisica.segundoApellido" id="registroSegundoApellido" cssStyle="width: 300px" maxlength="50" />
									<br /><br /><br />

									<form:label path="fisica.sexo.idSexo" cssClass="wide">Sexo</form:label>
									<combo:creaCombo 	idHtml				= "fisica.sexo.idSexo"
														idHtmlContenedor	= "paso3Form"
														entidad				= "mx.gob.imss.ctirss.delta.persistence.DicSexo"
														idHtmlValor			= "${tramiteAsegurado.fisica.sexo.idSexo}" 
														mostrarSoloActivos	= "true" />
									<span id="fisica.sexo.idSexoError" class="error hiddenElement"></span>
									<br /><br />
									<form:hidden path="fisica.sexo.descripcion"	id="sexo.descripcion" />

									<form:label path="fisica.fechaNacimiento" cssClass="wide">Fecha de Nacimiento</form:label>
									<form:input path="fisica.fechaNacimiento" id="registroFechaNacimientoC" style="width: 70px"	maxlength="10" />
									<br /><br /><br />

									<form:label path="fisica.lugarNacimiento.clave" cssClass="wide">Lugar de Nacimiento</form:label>
									<combo:creaCombo 	idHtml				= "fisica.lugarNacimiento.clave"
														idHtmlContenedor	= "paso3Form"
														entidad				= "mx.gob.imss.ctirss.delta.persistence.DgCatEstado"
														idHtmlValor			= "${tramiteAsegurado.fisica.lugarNacimiento.clave}" 
														mostrarSoloActivos	="true" />
									<span id="fisica.lugarNacimiento.claveError" class="error hiddenElement"></span>
									<br /><br />
									<form:hidden path="fisica.lugarNacimiento.nombre" id="lugarNacimiento.nombre" />

									<span id="errorFormGeneralError" class="error hiddenElement"></span>
								</fieldset>
								<br>
								<fieldset>
									<legend>
										<strong>&nbsp;Unidad M&eacute;dico Familiar&nbsp;</strong>
									</legend>
									<div id="umfContenedor" style="width: 100%;">
										<div style="text-align: center;">
											<img alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
										</div>
									</div>
									<form:errors path="fisica.umf.idUMF" cssClass="error" />
								</fieldset>
								<br>
							</div>

							<!-- Los datos de la serie solo deberan ser mostrados si el usuario es interno... -->
							
							<div id="datosSerie" >
								<fieldset>
									<legend> 
										<strong>&nbsp;Datos de la Serie &nbsp;</strong>
									</legend>

									<div>
										<form:label path="asignacionSerieNss.serie.tipoSerie.idTipoSerie" cssClass="wide">Tipo de Serie</form:label> 
										<form:select path="asignacionSerieNss.serie.tipoSerie.idTipoSerie"  >
											<form:option value="-1" label="--Por favor seleccione--"/>
											<form:options items="${listTipoSerie}" itemLabel="descripcion" itemValue="idTipoSerie"/>
										</form:select>
										<br />
										<form:errors path="asignacionSerieNss.serie.tipoSerie.idTipoSerie" cssClass="error" />
										<form:hidden path="asignacionSerieNss.serie.tipoSerie.descripcion" />
									</div>
									<br />
									
									<div id="selectSerie" class="hiddenElement">
										<form:label path="asignacionSerieNss.serie.anioRegistro" cssClass="wide">A&ntilde;o de Registro</form:label> 
										<form:select path="asignacionSerieNss.serie.anioRegistro" />
									</div>
									<br />
									<form:errors path="asignacionSerieNss.serie.anioRegistro" cssClass="error" />
								</fieldset>
							</div>
							
							<div style="text-align: right; float: right;">
								<input type="button" value="Cancelar Solicitud" id="cancelarSolicitud"	class="mboton" />
								<input type="button" value="Concluir" id="registrar" class="mboton" />
							</div>
						</div>

						<div class="cell" id="datosComplementarios" style="padding-left: 10px; height: 500px;">
							<div id="domicilio">
								<fieldset style="background: none repeat scroll 0 0 #F5F5F5; border: 1px solid #E5E5E5;">
									<legend>
										<strong>Datos del domicilio ubicado</strong>
									</legend>

									<div id="domicilioLocaliza"></div>

									<div id="datos">
										<label class="wide">C&oacute;digo postal</label>
										<input type="text" id="codigoPostal" style="width: 50px;" maxlength="5" value="${tramiteAsegurado.fisica.domicilios[0].codigoPostal.codigoPostal}" />
										<br /><br /><br />

										<label class="wide">Entidad federativa</label>
										<input type="text" id="entidadFederativa" style="width: 300px;" maxlength="50" value="${tramiteAsegurado.fisica.domicilios[0].asentamiento.localidad.municipio.entidadFederativa.nombre}" />
										<input type="hidden" id="entidadFederativaClave" />
										<br /><br /><br />

										<label class="wide">Municipio</label>
										<input type="text" id="municipio" style="width: 300px;" maxlength="50" value="${tramiteAsegurado.fisica.domicilios[0].asentamiento.localidad.municipio.nombre}" />
										<input type="hidden" id="municipioClave" />
										<br /><br /><br />

										<label class="wide">Localidad</label>
										<input type="text" id="localidad" style="width: 300px;" maxlength="50" value="${tramiteAsegurado.fisica.domicilios[0].asentamiento.localidad.nombre}" />
										<input type="hidden" id="localidadClave" />
										<br /><br /><br />

										<label class="wide">Asentamiento</label>
										<input type="text" id="asentamiento" style="width: 300px;" maxlength="50" value="${tramiteAsegurado.fisica.domicilios[0].asentamiento.nombre}" />
										<input type="hidden" id="asentamientoClave" />
										<br /><br /><br />

										<label class="wide">Vialidad primaria</label>
										<input type="text" id="vialidadPrimaria" style="width: 300px;" maxlength="50" value="${tramiteAsegurado.fisica.domicilios[0].vialidadPrimaria.nombre}" />
										<input type="hidden" id="vialidadPrimaria.clave" />
										<br /><br /><br />

										<label class="wide">N&uacute;mero exterior principal</label>
										<input type="text" id="numeroExteriorPrincipal" style="width: 50px;" maxlength="10" value="${tramiteAsegurado.fisica.domicilios[0].numExterior1}" />
										<br /><br /><br />

										<div style="display: none;">
											<label class="wide">N&uacute;mero exterior alfanum&eacute;rico</label>
											<input type="text" id="numeroExteriorAlfanumerico" style="width: 50px;" maxlength="10" value="${tramiteAsegurado.fisica.domicilios[0].numExteriorAlf}" />
											<br /><br /><br />

											<label class="wide">N&uacute;mero exterior secundario</label>
											<input type="text" id="numeroExteriorSecundario" style="width: 50px;" maxlength="10" value="${tramiteAsegurado.fisica.domicilios[0].numExterior2}" />
											<br /><br /><br />

											<label class="wide">N&uacute;mero interior</label>
											<input type="text" id="numeroInterior" style="width: 50px;" maxlength="10" value="${tramiteAsegurado.fisica.domicilios[0].numInterior}" />
											<br /><br /><br />

											<label class="wide">N&uacute;mero interior alfanum&eacute;rico</label>
											<input type="text" id="numeroInteriorAlfanumerico" style="width: 50px;" maxlength="10" value="${tramiteAsegurado.fisica.domicilios[0].numInteriorAlf}" />
											<br /><br /><br />

											<label class="wide">Vialidad referencia primaria</label>
											<input type="text" id="vialidadReferenciaPrimaria" style="width: 300px;" maxlength="50" value="${tramiteAsegurado.fisica.domicilios[0].vialidadReferenciaPrimaria.clave}" />
											<input type="hidden" id="vialidadReferenciaPrimaria.clave" />
											<br /><br /><br />

											<label class="wide">Vialidad referencia secundaria</label>
											<input type="text" id="vialidadReferenciaSecundaria" style="width: 300px;" maxlength="50" value="${tramiteAsegurado.fisica.domicilios[0].vialidadReferenciaSecundaria.clave}" />
											<input type="hidden" id="vialidadReferenciaSecundaria.clave" />
											<br /><br /><br />

											<label class="wide">Vialidad referencia posterior</label>
											<input type="text" id="vialidadReferenciaPosterior" style="width: 300px;" maxlength="50" value="${tramiteAsegurado.fisica.domicilios[0].vialidadReferenciaPosterior.clave}" />
											<input type="hidden" id="vialidadReferenciaPosterior.clave" />
											<br /><br /><br />

										</div>
									</div>

									<div style="text-align: right; float: right;">
										<input type="button" value="Ubicar domicilio" class="mboton" id="btnUbicarDomicilio" />
									</div>
								</fieldset>
							</div>
							<br />

							<div id="mediosContacto">
								<fieldset style="background: none repeat scroll 0 0 #F5F5F5; border: 1px solid #E5E5E5;">
									<legend>
										<strong> Medios de contacto</strong>
									</legend>

									<div id="mediosContactoCaptura"></div>
									<div id="mediosContactoDatos">
										<label class="wide"> N&uacute;mero Tel&eacute;fono
											Particular</label> <input type="text" id="numeroTelefonicoParticular"
											style="width: 200px;" maxlength="80"
											value="${tramiteAsegurado.fisica.telefonoFijo.numero}" />
										<br />
										<br />
										<br /> <label class="wide"> Clave Lada </label> <input
											type="text" id="claveLada" style="width: 100px;"
											maxlength="3"
											value="${tramiteAsegurado.fisica.telefonoFijo.claveLada}" />
										<br />
										<br />
										<br /> <label class="wide"> Extensi&oacute;n </label> <input
											type="text" id="extension" style="width: 100px;"
											maxlength="5"
											value="${tramiteAsegurado.fisica.telefonoFijo.extension}" />
										<br />
										<br />
										<br /> <label class="wide"> N&uacute;mero
											Tel&eacute;fono M&oacute;vil </label> <input type="text"
											id="numeroTelefonicoMovil" style="width: 200px;"
											maxlength="10"
											value="${tramiteAsegurado.fisica.telefonoMovil.numero}" />
										<br />
										<br />
										<br /> <label class="wide"> Correo Electr&oacute;nico
										</label> <input type="text" id="correoElectronico"
											style="width: 200px;" maxlength="10"
											value="${tramiteAsegurado.fisica.correoElectronico.correo}" />
										<br />
										<br />
										<br />

										<div style="text-align: right; float: right;">
											<input type="button" value="Registrar Medios" class="mboton"
												id="btnRegistrarMedios" />
										</div>

									</div>
								</fieldset>
							</div>
						</div>

						<c:forEach items="${tramiteAsegurado.fisica.documentosProbatorios}" var="documento" varStatus="index">
							<c:if test="${documento.documentoPorTipo.idDocumentoPorTipo eq tipoActaNacimiento}">
								<form:hidden path="fisica.actaNacimiento.documentoPorTipo.idDocumentoPorTipo" />
								<form:hidden path="fisica.actaNacimiento.anio" />
								<form:hidden path="fisica.actaNacimiento.noLibro" />
								<form:hidden path="fisica.actaNacimiento.noActa" />
								<form:hidden path="fisica.actaNacimiento.tomo" />
								<form:hidden path="fisica.actaNacimiento.noFoja" />
								<form:hidden path="fisica.actaNacimiento.crip" />

								<input id="fisica.actaNacimiento.noJuzgado" name="fisica.actaNacimiento.noJuzgado" value="0" type="hidden">
								<form:hidden path="fisica.actaNacimiento.municipio.entidadFederativa.clave" />
								<form:hidden path="fisica.actaNacimiento.municipio.clave" />
							</c:if>
							<c:if test="${documento.documentoPorTipo.idDocumentoPorTipo eq tipoCartaNaturalizacion}">
								<form:hidden path="fisica.cartaNaturalizacion.documentoPorTipo.idDocumentoPorTipo" />
								<form:hidden path="fisica.cartaNaturalizacion.numTipoDocumento" />
								<form:hidden path="fisica.cartaNaturalizacion.descripcionTipoDocumento" />
								<form:hidden path="fisica.cartaNaturalizacion.curp" />

								<form:hidden path="fisica.cartaNaturalizacion.numFolioExtranjero" />
								<form:hidden path="fisica.cartaNaturalizacion.anioRegistro" />
							</c:if>
							<c:if test="${documento.documentoPorTipo.idDocumentoPorTipo eq tipoDocumentoMigratorio}">
								<form:hidden path="fisica.documentoMigratorio.documentoPorTipo.idDocumentoPorTipo" />
								<form:hidden path="fisica.documentoMigratorio.numTipoDocumento" />
								<form:hidden path="fisica.documentoMigratorio.descripcionTipoDocumento" />
								<form:hidden path="fisica.documentoMigratorio.curp" />

								<form:hidden path="fisica.documentoMigratorio.numFolioExtranjero" />
							</c:if>
							<c:if test="${documento.documentoPorTipo.idDocumentoPorTipo eq tipoNumeroUnicoExtranjero}">
								<form:hidden path="fisica.numeroUnicoExtranjero.documentoPorTipo.idDocumentoPorTipo" />
								<form:hidden path="fisica.numeroUnicoExtranjero.numTipoDocumento" />
								<form:hidden path="fisica.numeroUnicoExtranjero.descripcionTipoDocumento" />
								<form:hidden path="fisica.numeroUnicoExtranjero.curp" />

								<form:hidden path="fisica.numeroUnicoExtranjero.numFolioExtranjero" />
							</c:if>
							<c:if test="${documento.documentoPorTipo.idDocumentoPorTipo eq tipoCertificadoNacionalidad}">
								<form:hidden path="fisica.certificadoNacionalidadMexicana.documentoPorTipo.idDocumentoPorTipo" />
								<form:hidden path="fisica.certificadoNacionalidadMexicana.numTipoDocumento" />
								<form:hidden path="fisica.certificadoNacionalidadMexicana.descripcionTipoDocumento" />
								<form:hidden path="fisica.certificadoNacionalidadMexicana.curp" />

								<form:hidden path="fisica.certificadoNacionalidadMexicana.numFolioExtranjero" />
								<form:hidden path="fisica.certificadoNacionalidadMexicana.anioRegistro" />
							</c:if>
							<c:if test="${documento.documentoPorTipo.idDocumentoPorTipo eq tipoOficioSolicitanteRef}">
								<form:hidden path="fisica.oficioSolicitanteRefugiado.documentoPorTipo.idDocumentoPorTipo" />
								<form:hidden path="fisica.oficioSolicitanteRefugiado.numTipoDocumento" />
								<form:hidden path="fisica.oficioSolicitanteRefugiado.descripcionTipoDocumento" />
								<form:hidden path="fisica.oficioSolicitanteRefugiado.curp" />

								<form:hidden path="fisica.oficioSolicitanteRefugiado.numFolioExtranjero" />
							</c:if>
							<c:if test="${documento.documentoPorTipo.idDocumentoPorTipo eq tipoFormaMigratoriaTurista}">
								<form:hidden path="fisica.formaMigratoriaTurista.documentoPorTipo.idDocumentoPorTipo" />
								<form:hidden path="fisica.formaMigratoriaTurista.numTipoDocumento" />
								<form:hidden path="fisica.formaMigratoriaTurista.descripcionTipoDocumento" />
								<form:hidden path="fisica.formaMigratoriaTurista.curp" />

								<form:hidden path="fisica.formaMigratoriaTurista.numFolioExtranjero" />
							</c:if>
						</c:forEach>
					</form:form>

				</div>
			</div>
			<!-- acordeon -->

		</div>
	</div>
</div>

<!-- DIV de jquery para poder desplegar un cuadro de dialogo tipo confirm para la cancelacion de una solicitud pendiente -->
<div id="dgCancelarSolicitudPendiente" title="Cancelar solicitud pendiente" >
	<p>
		<span class="ui-icon ui-icon-alert"	style="float: left; margin: 0 7px 20px 0;"> </span>
		¿Est&aacute; UD. seguro de querer cancelar esta solicitud?
	</p>
	<br />
</div>		

<!-- Forma auxiliar para consultar las UMF's -->
<form id="asentamientoForUmfForm">
	<input type="hidden" name="clave" value="${asentamiento.clave}" id="cveAsentamientoAux"/>
	<input type="hidden" name="nombre" value="${asentamiento.nombre}" id="nombreAsentamientoAux"/>
	<input type="hidden" name="localidad.clave" value="${asentamiento.localidad.clave}" id="localidadCveAsentamientoAux"/>
	<input type="hidden" name="localidad.municipio.clave" value="${asentamiento.localidad.municipio.clave}" id="municipioCveAsentamientoAux"/>
	<input type="hidden" name="localidad.municipio.entidadFederativa.clave" value="${asentamiento.localidad.municipio.entidadFederativa.clave}" id="estadoCveAsentamientoAux"/>
	<input type="hidden" name="codigoPostal.codigoPostal" value="${asentamiento.codigoPostal.codigoPostal}" id="cpAsentamientoAux"/>
	<input type="hidden" name="tipoAsentamiento.clave" value="${asentamiento.tipoAsentamiento.clave}" id="tipoAsentamientoAux"/>
</form>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/tramite/tramite-capturaComplementosConSerie.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/tramite/localizar-UMF.js" htmlEscape="true" />"></script>