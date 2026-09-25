<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/personas/moral/modificacion/manual/captura-modificacion-manual.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/personas/domicilio-mediosContacto-registro.js" htmlEscape="true" />"></script>

<div class="col-sm-12">
	<div class="contenedor">
		<c:set var="contextpath" value="<%=request.getContextPath()%>" />

		<form:form modelAttribute="mdmDatosEntrada" id="mdmPersonaMoralForm"
			action="${contextpath}/persona/moral/modificacion-manual/procesarCaptura">

			<form:hidden path="indCapturaDatosSAT" id="indCapturaDatosSAT" />
			<form:hidden path="indCapturaDatosComplementarios"
				id="indCapturaDatosComplementarios" />

			<form:hidden path="indCapturaRFC" id="indCapturaRFC" />
			<form:hidden path="indCapturaDomicilioFiscal"
				id="indCapturaDomicilioFiscal" />
			<form:hidden path="indCapturaMediosContactoFiscales"
				id="indCapturaMediosContactoFiscales" />
			<form:hidden path="indCapturaRazonSocial" id="indCapturaRazonSocial" />
			<form:hidden path="indCapturaFechaConstitucion"
				id="indCapturaFechaConstitucion" />
			<form:hidden path="indCapturaTipoSociedad"
				id="indCapturaTipoSociedad" />

			<form:hidden path="indCapturaActaConstitutiva"
				id="indCapturaActaConstitutiva" />
			<form:hidden path="indCapturaRegistroSindicato"
				id="indCapturaRegistroSindicato" />

			<form:hidden path="indAutorizacion" id="indAutorizacion" />

			<c:if test="${not empty mdmDatosEntrada.errorFormGeneral}">
				<form:hidden path="errorFormGeneral" />
				<div class="alert alert-danger">
					<button type="button" class="close" data-dismiss="alert">×</button>
					<strong>Error: </strong>${mdmDatosEntrada.errorFormGeneral}
				</div>
				<div class="text-right">
					<button type="button" class="btn btn-default" id="btnCancelar">Cerrar</button>
				</div>
			</c:if>

			<c:if test="${empty mdmDatosEntrada.errorFormGeneral}">
				<form:hidden path="personaMoral.cveMoral" id="cveMoral"/>

				<span id="errorNegocioLabel" class="error"></span>

				<div class="alert alert-info">
					<button type="button" class="close" data-dismiss="alert">×</button>
					Los datos marcados con un (*) son requeridos
				</div>

				<div id="tabs" style="width: 100%">
					<ul>
						<c:if test="${mdmDatosEntrada.indCapturaDatosSAT}">
							<li><a href='#datosSATDiv'>DATOS SAT</a></li>
						</c:if>
						<c:if test="${mdmDatosEntrada.indCapturaDatosComplementarios}">
							<li><a href='#datosComplementariosDiv'>DATOS
									COMPLEMENTARIOS</a></li>
						</c:if>
					</ul>

					<c:if test="${mdmDatosEntrada.indCapturaDatosSAT}">
						<div id="datosSATDiv">
							<c:if test="${mdmDatosEntrada.indCapturaRazonSocial}">
								<div class="form-group">
									<form:label path="personaMoral.razonSocial">
										<span class="required">*</span>Raz&oacute;n Social
									</form:label>
									<form:input path="personaMoral.razonSocial"
										id="registroRazonSocial" cssClass="form-control"
										maxlength="100" />
									<span id="razonSocialError" class="error"></span>
								</div>
							</c:if>

							<c:if test="${mdmDatosEntrada.indCapturaFechaConstitucion}">
								<div class="form-group">
									<form:label
										path="personaMoral.datosPersonaSAT.fechaConstitucion"
										cssStyle="width: 100%;">
										<span class="required">*</span>Fecha de Creaci&oacute;n
									</form:label>
									<form:input
										path="personaMoral.datosPersonaSAT.fechaConstitucion"
										id="registroFechaCreacionC"
										style="width: auto; margin-right: 5px;" maxlength="10"
										cssClass="form-control" />
									<span id="datosPersonaSAT.fechaConstitucionErrorCliente"
										class="error">Formato de Fecha
										inv&aacute;lido</span> 
									<span
										id="datosPersonaSAT.fechaConstitucionError"
										class="error"></span>
								</div>
							</c:if>

							<c:if test="${mdmDatosEntrada.indCapturaTipoSociedad}">
								<div class="form-group">
									<form:label path="personaMoral.tipoSociedad.idTipoSociedad">
										<span class="required">*</span>Tipo de Sociedad</form:label>
									<combo:creaCombo
										idHtml="personaMoral.tipoSociedad.idTipoSociedad"
										idHtmlContenedor="mdmPersonaMoralForm"
										entidad="mx.gob.imss.ctirss.delta.persistence.DicTipoSociedad"
										idHtmlValor="${mdmDatosEntrada.personaMoral.tipoSociedad.idTipoSociedad}" 
										mostrarSoloActivos="true"
										cssClassname="form-control"/>
									<span id="tipoSociedad.idTipoSociedadError"
										class="error"></span>
									<form:hidden
										path="personaMoral.tipoSociedad.descripcionAbreviada" />
								</div>
							</c:if>

							<c:if test="${mdmDatosEntrada.indCapturaRFC || mdmDatosEntrada.indCapturaDomicilioFiscal || mdmDatosEntrada.indCapturaMediosContactoFiscales}">
								<div class="form-group">
									<form:label path="personaMoral.rfc">
										<span class="required">*</span>RFC
									</form:label>
									<form:input path="personaMoral.rfc" id="registroRfc"
										maxlength="12" cssClass="rfc_moral form-control" />
									<span id="rfcError" class="error"></span>
								</div>
							</c:if>
							
							<c:if test="${mdmDatosEntrada.indCapturaDomicilioFiscal or mdmDatosEntrada.indCapturaMediosContactoFiscales}">
								<hr>
							</c:if>

							<c:if test="${mdmDatosEntrada.indCapturaDomicilioFiscal}">
								<div id="acordeonDomFiscal">
									<h3>
										<a href='#'><span class="required">*</span>DOMICILIO
											FISCAL&nbsp; <span id="domicilioFiscalError"
											class="error" style="float: right;"></span></a>
									</h3>
									<div id="domicilioDiv">

										<jsp:include page="../../../detalleDomicilioFiscal.jsp"></jsp:include>

										<div style="text-align: right; float: right;">
											<input type="button" value="Registrar Domicilio Fiscal"
												class="btn btn-primary" id="btnDomFiscal" />
										</div>
									</div>
								</div>
							</c:if>

							<c:if test="${mdmDatosEntrada.indCapturaMediosContactoFiscales}">
								<div id="acordeonMediosFiscales">
									<h3>
										<a href='#'>MEDIOS DE CONTACTO FISCALES</a>
									</h3>
									<div id="mediosContactoFiscalesDiv">
										<span id="errorNegocioLabel" class="error"></span>
										<div id="admonMediosContactoFiscales"></div>
									</div>
								</div>
							</c:if>
						</div>
					</c:if>

					<c:if test="${mdmDatosEntrada.indCapturaDatosComplementarios}">
						<div id="datosComplementariosDiv">
							<div class="filtros-busqueda" style="margin-bottom: 5px;">
								<c:if test="${mdmDatosEntrada.indCapturaActaConstitutiva}">
									<div id="acordeonEscrituraConstitutiva">
										<h3>
											<a href='#'><span class="required">*</span>ESCRITURA
												CONSTITUTIVA &nbsp; <span id="escrituraConstitutivaError"
												class="error" style="float: right;"></span> </a>
										</h3>
										<div id="escrituraConstitutivaDiv">
											<form:hidden
												path="personaMoral.escrituraConstitutiva.cveEscrituraConstitutiva" />

											<div class="form-group">
												<form:label path="personaMoral.escrituraConstitutiva.numEscritura">
													N&uacute;mero de Escritura
												</form:label>
												<form:input
													path="personaMoral.escrituraConstitutiva.numEscritura"
													id="numEscritura" cssClass="form-control" maxlength="50" />
												<span id="escrituraConstitutiva.numEscrituraError"
													class="error"></span>
											</div>

											<div class="form-group">
												<form:label path="personaMoral.escrituraConstitutiva.numNotaria">
													No. de Notar&iacute;a o Corredur&iacute;a
												</form:label>
												<form:input
													path="personaMoral.escrituraConstitutiva.numNotaria"
													id="numNotaria" cssClass="form-control" maxlength="50" />
												<span id="escrituraConstitutiva.numNotariaError"
													class="error"></span>
											</div>

											<div class="form-group">
												<form:label path="personaMoral.escrituraConstitutiva.lugarExpedicion.entidadFederativa.clave">
													Estado o Provincia
												</form:label>
												<combo:creaCombo
													idHtml="personaMoral.escrituraConstitutiva.lugarExpedicion.entidadFederativa.clave"
													idHtmlContenedor="escrituraConstitutivaDiv"
													entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado"
													idHtmlValor="${mdmDatosEntrada.personaMoral.escrituraConstitutiva.lugarExpedicion.entidadFederativa.clave}" 
													mostrarSoloActivos="true"
													cssClassname="form-control"/>
												<span
													id="escrituraConstitutiva.lugarExpedicion.entidadFederativa.claveError"
													class="error"></span>
											</div>

											<div class="form-group">
												<form:label path="personaMoral.escrituraConstitutiva.lugarExpedicion.clave">
													Municipio o Delegaci&oacute;n
												</form:label>
												<combo:creaCombo
													entidad="mx.gob.imss.ctirss.delta.persistence.DgCatMunicipio"
													idHtml="personaMoral.escrituraConstitutiva.lugarExpedicion.clave"
													entidadPadre="dgCatEstado.cveEnt"
													idHtmlPadre="personaMoral.escrituraConstitutiva.lugarExpedicion.entidadFederativa.clave"
													idHtmlContenedor="escrituraConstitutivaDiv"
													idHtmlValor="${mdmDatosEntrada.personaMoral.escrituraConstitutiva.lugarExpedicion.clave}" 
													mostrarSoloActivos="false"
													cssClassname="form-control"/>
												<span id="escrituraConstitutiva.lugarExpedicion.claveError"
													class="error"></span>
											</div>

											<div class="form-group">
												<form:label path="personaMoral.escrituraConstitutiva.fechaExpedicion"
													cssStyle="width: 100%;">
													Fecha de expedici&oacute;n
												</form:label>
												<form:input
													path="personaMoral.escrituraConstitutiva.fechaExpedicion"
													id="fechaExpedicion"
													style="width: auto; margin-right: 5px;" maxlength="10" 
													cssClass="form-control"/>
												<span id="fechaExpedicionErrorCliente"
													class="error"></span> <span
													id="escrituraConstitutiva.fechaExpedicionError"
													class="error"></span>
											</div>

											<div id="folioMercantilDiv">
												<div class="form-group">
													<form:label path="personaMoral.escrituraConstitutiva.folioMercantil">
														Folio mercantil
													</form:label>
													<form:input
														path="personaMoral.escrituraConstitutiva.folioMercantil"
														id="folioMercantil" cssClass="form-control"
														maxlength="50" />
													<span id="escrituraConstitutiva.folioMercantilError"
														class="error"></span>
												</div>
											</div>

											<div id="datosEscritura">
												<div class="form-group">
													<form:label path="personaMoral.escrituraConstitutiva.seccion">
														Secci&oacute;n
													</form:label>
													<form:input
														path="personaMoral.escrituraConstitutiva.seccion"
														id="seccionEscrituraConstitutiva" cssClass="form-control"
														maxlength="50" />
													<span id="escrituraConstitutiva.seccionError"
														class="error"></span>
												</div>

												<div class="form-group">
													<form:label path="personaMoral.escrituraConstitutiva.partida">
														Partida
													</form:label>
													<form:input
														path="personaMoral.escrituraConstitutiva.partida"
														id="partidaEscrituraConstitutiva" cssClass="form-control"
														maxlength="50" />
													<span id="escrituraConstitutiva.partidaError"
														class="error"></span>
												</div>

												<div class="form-group">
													<form:label path="personaMoral.escrituraConstitutiva.volumen">
														Volumen
													</form:label>
													<form:input
														path="personaMoral.escrituraConstitutiva.volumen"
														id="volumenEscrituraConstitutiva" cssClass="form-control"
														maxlength="50" />
													<span id="escrituraConstitutiva.volumenError"
														class="error"></span>
												</div>

												<div class="form-group">
													<form:label path="personaMoral.escrituraConstitutiva.foja">
														Foja
													</form:label>
													<form:input path="personaMoral.escrituraConstitutiva.foja"
														id="fojaEscrituraConstitutiva" cssClass="form-control"
														maxlength="50" />
													<span id="escrituraConstitutiva.fojaError"
														class="error"></span>
												</div>
											</div>
										</div>
									</div>
								</c:if>

								<c:if test="${mdmDatosEntrada.indCapturaRegistroSindicato}">
									<div id="acordeonRegistroSindicato">
										<h3>
											<a href='#'><span class="required">*</span>REGISTRO DE
												SINDICATO &nbsp; <span id="registroSindicatoError"
												class="error" style="float: right;"></span> </a>
										</h3>
										<div id="registroSindicatoDiv">
											<form:hidden
												path="personaMoral.registroSindicato.cveRegistroSindicato" />

											<div class="form-group">
												<form:label
													path="personaMoral.registroSindicato.numReferenciadocRegistro">N&uacute;mero de Referencia
												</form:label>
												<form:input
													path="personaMoral.registroSindicato.numReferenciadocRegistro"
													id="numReferenciaRegSindicato"
													maxlength="50" cssClass="numerico form-control" />
												<span id="registroSindicato.numReferenciadocRegistroError"
													class="error"></span>
											</div>

											<div class="form-group">
												<form:label path="personaMoral.registroSindicato.fechaRegistro"
													cssStyle="width: 100%;">
													Fecha del documento de registro
												</form:label>
												<form:input
													path="personaMoral.registroSindicato.fechaRegistro"
													id="fechaRegSindicato"
													style="width: auto; margin-right: 5px;" maxlength="10"
													cssClass="form-control" />
												<span id="fechaRegistroErrorCliente"
													class="error"></span> 
												<span id="registroSindicato.fechaRegistroError"
													class="error"></span>
											</div>

											<div class="form-group">
												<form:label path="personaMoral.registroSindicato.autoridadLaboral">	
													Autoridad laboral que otorg&oacute; el registro
												</form:label>
												<form:input
													path="personaMoral.registroSindicato.autoridadLaboral"
													id="autoridadRegSindicato" cssClass="form-control"
													maxlength="50" />
												<span id="registroSindicato.autoridadLaboralError"
													class="error"></span>
											</div>
										</div>
									</div>
								</c:if>
							</div>
						</div>
					</c:if>
				</div>

				<br />

				<!-- estos divs seran lo que contengan el cuadro de dialogo de jquery que vienen de los componentes externos para capturar datos complementarios -->
				<div id="domicilioRegistrar"></div>

				<!-- Para medios de contacto fiscales -->
				<div id="agregarMedioContactoFiscalDialog"></div>
				
				<div class="text-right">
					<button type="button" class="btn btn-default" id="btnCancelar">Cancelar</button>
					<button type="button" class="btn btn-primary" id="btnAceptar">Aceptar</button>
				</div>

			</c:if>

			
		</form:form>
	</div>
</div>
