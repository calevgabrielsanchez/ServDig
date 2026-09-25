<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.enums.TipoDomicilioEnum"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/personas/fisica/modificacion/manual/captura-modificacion-manual.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/personas/domicilio-mediosContacto-registro.js" htmlEscape="true" />"></script>

<div class="col-sm-12">
	<div class="contenedor">
		<c:set var="contextpath" value="<%=request.getContextPath()%>" />

		<c:if test="${mdmDatosEntrada.indCapturaDomicilioParticular}">
			<input type="hidden" id="tipoDomParticular"
				value="<%=TipoDomicilioEnum.PARTICULAR.getId()%>" />
			<input type="hidden" id="tipoDomNotificaciones"
				value="<%=TipoDomicilioEnum.RECIBIR_NOTIFICACIONES.getId()%>" />
		</c:if>

		<form:form modelAttribute="mdmDatosEntrada" id="mdmPersonaFisicaForm"
			action="${contextpath}/persona/fisica/modificacion-manual/procesarCaptura">

			<form:hidden path="indCapturaDatosRENAPO" id="indCapturaDatosRENAPO" />
			<form:hidden path="indCapturaDatosSAT" id="indCapturaDatosSAT" />
			<form:hidden path="indCapturaDatosComplementarios"
				id="indCapturaDatosComplementarios" />

			<form:hidden path="indCapturaNombre" id="indCapturaNombre" />
			<form:hidden path="indCapturaCURP" id="indCapturaCURP" />
			<form:hidden path="indCapturaSexo" id="indCapturaSexo" />
			<form:hidden path="indCapturaFechaNacimiento"
				id="indCapturaFechaNacimiento" />
			<form:hidden path="indCapturaLugarNacimiento"
				id="indCapturaLugarNacimiento" />
			<form:hidden path="indCapturaDocumentoProbatorio"
				id="indCapturaDocumentoProbatorio" />

			<form:hidden path="indCapturaRFC" id="indCapturaRFC" />
			<form:hidden path="indCapturaDomicilioFiscal"
				id="indCapturaDomicilioFiscal" />
			<form:hidden path="indCapturaMediosContactoFiscales"
				id="indCapturaMediosContactoFiscales" />

			<form:hidden path="indCapturaDomicilioParticular"
				id="indCapturaDomicilioParticular" />
			<form:hidden path="indCapturaMediosContactoParticular"
				id="indCapturaMediosContactoParticular" />

			<form:hidden path="indAutorizacion" id="indAutorizacion" />

			<c:if test="${not empty mdmDatosEntrada.errorFormGeneral}">
				<form:hidden path="errorFormGeneral" />
				<div class="alert alert-danger">
					<button type="button" class="close" data-dismiss="alert">×</button>
					<strong>Error: </strong>${mdmDatosEntrada.errorFormGeneral}
				</div>
				<div class="text-right m-t-lg">
					<button type="button" class="btn btn-default" id="btnCancelar">Cerrar</button>
				</div>
			</c:if>

			<c:if test="${empty mdmDatosEntrada.errorFormGeneral}">
				<form:hidden path="personaFisica.idPersona" id="idPersona" />
				<form:hidden path="personaFisica.cveFisica" id="cveFisica" />

				<span id="errorNegocioLabel" class="error hiddenElement"></span>

				<div class="alert alert-info">
					<button type="button" class="close" data-dismiss="alert">×</button>
					Los datos marcados con un (*) son requeridos
				</div>

				<div id="tabs" style="width: 100%">
					<ul>
						<c:if test="${mdmDatosEntrada.indCapturaDatosRENAPO}">
							<li><a href='#datosRENAPODiv'>DATOS RENAPO</a></li>
						</c:if>
						<c:if test="${mdmDatosEntrada.indCapturaDatosSAT}">
							<li><a href='#datosSATDiv'>DATOS SAT</a></li>
						</c:if>
						<c:if test="${mdmDatosEntrada.indCapturaDatosComplementarios}">
							<li><a href='#datosComplementariosDiv'>DATOS
									COMPLEMENTARIOS</a></li>
						</c:if>
					</ul>

					<c:if test="${mdmDatosEntrada.indCapturaDatosRENAPO}">
						<div id="datosRENAPODiv">
							<div id="datosBasicos">
								<c:if test="${mdmDatosEntrada.indCapturaNombre}">
									<div class="form-group">
										<form:label path="personaFisica.nombre">
											<span class="required">*</span>Nombre(s)
										</form:label>
										<form:input path="personaFisica.nombre" id="registroNombres" maxlength="50" cssClass="form-control" />
										<span id="nombreError" class="error hiddenElement"></span>
									</div>

									<div class="form-group">
										<form:label path="personaFisica.primerApellido">
											<span class="required">*</span>Primer Apellido
										</form:label>
										<form:input path="personaFisica.primerApellido" id="registroPrimerApellido" cssClass="form-control"
											maxlength="50" />
										<span id="primerApellidoError" class="error hiddenElement"></span>
									</div>

									<div class="form-group">
										<form:label path="personaFisica.segundoApellido">Segundo Apellido</form:label>
										<form:input path="personaFisica.segundoApellido" id="registroSegundoApellido" cssClass="form-control"
											maxlength="50" />
									</div>
								</c:if>

								<c:if test="${mdmDatosEntrada.indCapturaCURP}">
									<div class="form-group">
										<form:label path="personaFisica.curp">CURP</form:label>
										<form:input path="personaFisica.curp" id="registroCurp" cssClass="form-control" maxlength="18" />
										<span id="curpError" class="error hiddenElement"></span>
									</div>
								</c:if>

								<c:if test="${mdmDatosEntrada.indCapturaSexo}">
									<div class="form-group">
										<form:label path="personaFisica.sexo.idSexo">
											<span class="required">*</span>Sexo</form:label>
										<combo:creaCombo idHtml="personaFisica.sexo.idSexo" 
											idHtmlContenedor="mdmPersonaFisicaForm"
											entidad="mx.gob.imss.ctirss.delta.persistence.DicSexo"
											idHtmlValor="${mdmDatosEntrada.personaFisica.sexo.idSexo}" 
											mostrarSoloActivos="true" 
											cssClassname="form-control"/>
										<form:hidden path="personaFisica.sexo.descripcion" id="sexo.descripcion" />
										<span id="sexo.idSexoError" class="error hiddenElement"></span>
									</div>
								</c:if>

								<c:if test="${mdmDatosEntrada.indCapturaFechaNacimiento}">
									<div class="form-group">
										<form:label path="personaFisica.fechaNacimiento" cssStyle="width: 100%;">
											<span class="required">*</span>Fecha de Nacimiento
										</form:label>
										<form:input path="personaFisica.fechaNacimiento"
											id="registroFechaNacimientoC" style="width: auto; margin-right: 5px;"
											maxlength="10" cssClass="form-control" />
										<span id="fechaNacimientoError" class="error hiddenElement"></span>
										<span id="fechaNacimientoErrorCliente"
											class="error hiddenElement"></span>
									</div>
								</c:if>

								<c:if test="${mdmDatosEntrada.indCapturaLugarNacimiento}">
									<div class="form-group">
										<form:label path="personaFisica.pais.idPais">
											<span class="required">*</span>Nacionalidad</form:label>
										<combo:creaCombo idHtml="personaFisica.pais.idPais"
											idHtmlContenedor="mdmPersonaFisicaForm"
											entidad="mx.gob.imss.ctirss.delta.persistence.DicPai"
											idHtmlValor="${mdmDatosEntrada.personaFisica.pais.idPais}" 
											mostrarSoloActivos="true"
											cssClassname="form-control"/>
										<form:hidden path="personaFisica.pais.nacionalidad"
											id="personaFisica.pais.nacionalidad" />
										<span id="pais.idPaisError"
											class="error hiddenElement"></span>
									</div>
									<div class="form-group">
										<form:label path="personaFisica.lugarNacimiento.clave">
											<span class="required">*</span>Lugar de Nacimiento</form:label>
										<combo:creaCombo
											entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado"
											idHtml="personaFisica.lugarNacimiento.clave"
											entidadPadre="dicPai.cveIdPais"
											idHtmlPadre="personaFisica.pais.idPais"
											idHtmlContenedor="mdmPersonaFisicaForm"
											idHtmlValor="${mdmDatosEntrada.personaFisica.lugarNacimiento.clave}"
											idHtmlValorPadre="${mdmDatosEntrada.personaFisica.pais.idPais}" 
											mostrarSoloActivos="true"
											cssClassname="form-control"/>
										<form:hidden path="personaFisica.lugarNacimiento.nombre"
											id="lugarNacimiento.nombre" />
										<span id="lugarNacimiento.claveError"
											class="error hiddenElement"></span>
									</div>
								</c:if>
							</div>

							<c:if test="${mdmDatosEntrada.indCapturaDocumentoProbatorio}">
								<div id="acordeonDocProbatorios">
									<h3>
										<a href='#' class="actualiza_combo_hijo"><span class="required">*</span>DOCUMENTOS
											PROBATORIOS &nbsp; <span id="documentosProbatoriosError"
											class="error hiddenElement" style="float: right;"></span>
										</a>
									</h3>
									<div id="documentosProbatoriosDiv">
										<span id="errorNegocioLabel" class="error hiddenElement"></span>
										<iframe id="admonDocsProbatorios"
											src="/gestionDocumentoProbatorio-web/documentos/probatorios/administrar/${mdmDatosEntrada.personaFisica.idPersona}"
											style="width: 100%;" frameborder="0"></iframe>

										<div class="text-right" style="margin-top: -40px;">
											<button type="button" class="btn btn-primary"
												id="btnDocsProbatorios">Registrar Documento Probatorio</button>
										</div>
									</div>
								</div>
							</c:if>
						</div>
					</c:if>

					<c:if test="${mdmDatosEntrada.indCapturaDatosSAT}">
						<div id="datosSATDiv">
							<div class="filtros-busqueda" style="margin-bottom: 5px;">
								<c:if
									test="${mdmDatosEntrada.indCapturaRFC || mdmDatosEntrada.indCapturaDomicilioFiscal || mdmDatosEntrada.indCapturaMediosContactoFiscales}">
									<div class="form-group">
										<form:label path="personaFisica.rfc"><span class="required">*</span>RFC</form:label>
										<form:input path="personaFisica.rfc" id="registroRfc"
											cssClass="form-control" maxlength="13" />
										<span id="rfcError" class="error hiddenElement"></span>
									</div>
								</c:if>
							</div>

							<c:if test="${mdmDatosEntrada.indCapturaDomicilioFiscal}">
								<div id="acordeonDomFiscal">
									<h3>
										<a href='#'><span class="required">*</span>DOMICILIO FISCAL&nbsp; <span
											id="domicilioFiscalError" class="error hiddenElement"
											style="float: right;"></span>
										</a>
									</h3>
									<div id="domicilioDiv">

										<jsp:include page="../../../detalleDomicilioFiscal.jsp"></jsp:include>

										<div style="text-align: right; float: right;">
											<button type="button" class="btn btn-primary"
												id="btnDomFiscal">Registrar Domicilio Fiscal</button>
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
										<span id="errorNegocioLabel" class="error hiddenElement"></span>
										<div id="admonMediosContactoFiscales"></div>
									</div>
								</div>
							</c:if>
						</div>
					</c:if>

					<c:if test="${mdmDatosEntrada.indCapturaDatosComplementarios}">
						<div id="datosComplementariosDiv">
							<c:if test="${mdmDatosEntrada.indCapturaDomicilioParticular}">
								<div id="acordeonDomiciliosParticulares">
									<h3>
										<a href='#'><span class="required">*</span>DOMICILIO(S) PARTICULAR(ES)&nbsp; <span
											id="domiciliosError" class="error hiddenElement"
											style="float: right;"></span>
										</a>
									</h3>
									<div id="domiciliosParticularesDiv">
										<span id="errorNegocioLabel" class="error hiddenElement"></span>
										<iframe id="admonDomicilios"
											src="/gestionDomicilios-web/domicilio/administrar/particular/fisica/${mdmDatosEntrada.personaFisica.idPersona}"
											style="width: 100%;" frameborder="0"></iframe>

										<div style="text-align: right; float: right;">
											<span id="tipoDomicilio.claveError"
												class="error hiddenElement"></span><br> <br>
											<button type="button" class="btn btn-primary"
												id="btnAgregarDomicilioParticular">Registrar
												Domicilio Particular</button>
											<button type="button" class="btn btn-primary"
												id="btnAgregarDomicilioNotificaciones">Registrar
												Domicilio para Oír y Recibir Notificaciones</button>
											</input>
										</div>
										<br> <br> <br>
									</div>
								</div>
							</c:if>

							<c:if
								test="${mdmDatosEntrada.indCapturaMediosContactoParticular}">
								<div id="acordeonMediosParticulares">
									<h3>
										<a href='#'>MEDIOS DE CONTACTO PARTICULARES</a>
									</h3>
									<div id="mediosContactoDiv">
										<span id="errorNegocioLabel" class="error hiddenElement"></span>
										<div id="admonMediosContactoDiv"></div>
									</div>
								</div>
							</c:if>
						</div>
					</c:if>
				</div>

				<!-- Para documentos probatorios particulares -->
				<div id="agregarDocsProbatoriosDialog"></div>

				<!-- Para domicilio fiscal -->
				<div id="domicilioRegistrar"></div>

				<!-- Para medios de contacto fiscales -->
				<div id="mediosContactoRegistrar"></div>

				<!-- Para domicilios particulares -->
				<div id="agregarDomicilioDialog"></div>

				<!-- Para la modificación de un domicilio particular -->
				<div id="modificarDomicilioDialog">
					<iframe name="modificarDomicilioFrame" width="100%" height="100%"></iframe>
				</div>

				<div class="text-right m-t-lg">
					<button type="button" class="btn btn-default" id="btnCancelar">Cancelar</button>
					<button type="button" class="btn btn-primary" id="btnAceptar">Aceptar</button>
				</div>
			</c:if>

			
		</form:form>
	</div>
</div>