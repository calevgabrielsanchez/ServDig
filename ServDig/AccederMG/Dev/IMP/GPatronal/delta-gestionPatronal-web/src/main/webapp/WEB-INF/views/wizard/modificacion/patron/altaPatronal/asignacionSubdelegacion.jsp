<%@ include file="../../../../general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum"%>

<c:set var="origenINTERNET" value="<%=OrigenSolicitudEnum.INTERNET.getId()%>" />
<c:set var="origenCIUDADANO" value="<%=OrigenSolicitudEnum.PORTAL_CIUDADANO.getId()%>" />
<c:set var="origenVENTANILLA" value="<%=OrigenSolicitudEnum.VENTANILLA.getId()%>" />

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/common/commonMethods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/modificacion/patron/altaPatronal/localizarSubdelegacion.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/modificacion/patron/altaPatronal/asignacionSubdelegacion.js" htmlEscape="true" />"></script>

<script>
	var idOrigenPortalInternet = <%=OrigenSolicitudEnum.INTERNET.getId()%>;
	var idOrigenPortalVentanilla = <%=OrigenSolicitudEnum.VENTANILLA.getId()%>;

	var idSolicitud=0;
	<c:if test="${idSolicitud != null}">
		idSolicitud = ${idSolicitud};
		var tipoSolicitud = <%=TipoSolicitudEnum.ALTA_PATRONAL.getValor()%>;
	</c:if>
		
	var indPrestaServicioPersonal = '${sujetoTramite.clasificacion.indPrestaServicioPersonal}';
	var validaTipoPoder = false;
	
	if(parent.WizardAltaPatronalCtrl.config.idOrigen == idOrigenPortalInternet){	
		//Si es INTERNET.
		<c:if test="${not empty isAltaPorRL}">
		validaTipoPoder = ${isAltaPorRL};
		</c:if>
		var codigoTipoSolicitud = ${codigoTipoSolicitud};
		var arrayCodigoTipoTramite = ${codigoTipoTramite};
		var descripcionTipoSolicitud = '${descripcionTipoSolicitud}';
	
		var datosEntradaFirma = {
			fechaElectronica : '${datosFirmaElectronica.fechaElectronicaFormateada}',
			nombreCompleto : "${datosFirmaElectronica.nombreCompleto}",
			registroPatronal : '${datosFirmaElectronica.registroPatronal}',
			rfc : '${datosFirmaElectronica.rfc}',
			curp : '${datosFirmaElectronica.curp}'
		};
	}
				
</script>

<style>
	.table_form table {
		margin: 15px auto;
	}
	
	.table_form table tr td {
		padding: 5px 10px;
	}
	
	textarea {
		height: 100%;
	}
	
	input,textarea,.uneditable-input {
		width: auto;
		text-transform: uppercase;
	}
	
	div.opcion {
	    padding-top: 15px;
	}
	
	.form-horizontal .form-group-sm {
		font-size: 14px;
	}
	
	.form-group-sm .form-control {
	    line-height: 1.5;
	    padding: 5px 10px;
	}
	
	.form-group-sm input.form-control {
	    height: 30px;
	}
	
	#municipioImssContenedor table {
		margin: 0px;
	}
	
	#municipioImssContenedor table tr td address{
		margin: 0;
    	padding: 5px 0;
	}
</style>

<div class="contenedor col-sm-12">
	<input type="hidden" id="rfcPersona" value="${rfcPersona}" />

	<div class="contenido row">
		<div class="col-sm-12">
			<c:if test="${not empty solicitudTramite.errorFormGeneral}">
				<div class="alert alert-danger">
					<button type="button" class="close" data-dismiss="alert">*—</button>
					<strong>Error: </strong>
					${solicitudTramite.errorFormGeneral}
				</div>
			</c:if>

			<c:if test="${not empty folioSolicitud}">
				<input type="hidden" id="hdnFolioSolicitud" value="${folioSolicitud}" />
				<input type="hidden" id="contenidoFirmar" value="${contenidoFirmar}" />
				<input type="hidden" id="hdnIdSolicitud" value="${idSolicitud}" />
				<div class="alert alert-success">
					<c:choose>
						<c:when test="${!isRetomar}">
							<spring:message code="label.solicitud.iniciada" arguments="${folioSolicitud}"></spring:message>
						</c:when>
						<c:otherwise>
							<spring:message code="label.solicitud.retomando" arguments="${folioSolicitud}"></spring:message>
						</c:otherwise>
					</c:choose>
				</div>
			</c:if>
			<div class="alert alert-danger" style="display: none" id="divErrorCampos"></div>
			<form action="" method="post" id="concluirTramiteForm">
				<input type="hidden" name="idSolicitud" id="idSolicitud" value="${idSolicitud}" />
			</form>

			<!-- Notificación de RISS -->
			<c:if test="${origenApp ne origenVENTANILLA and aplicaRissAltaPat != null and aplicaRissAltaPat}">
				<div class="alert alert-info">
					Eres
					<strong>sujeto</strong>
					a los beneficios establecidos al
					<strong>R&eacute;gimen de Incorporaci&oacute;n a la Seguridad Social (RISS)</strong>
					as&iacute; como a la adhesi&oacute;n al subsidio en materia de seguridad social establecidos en el decreto por el
					que se otorgan est&iacute;mulos para promover la incorporaci&oacute;n a la seguridad social.
				</div>
			</c:if>
			<c:if test="${origenApp eq origenVENTANILLA and aplicaRissAltaPat != null and aplicaRissAltaPat}">
				<div class="alert alert-info">
					La persona que realiza el tr&aacute;mite es
					<strong>sujeta</strong>
					a los beneficios establecidos al
					<strong>R&eacute;gimen de Incorporaci&oacute;n a la Seguridad Social (RISS)</strong>
					as&iacute; como a la adhesi&oacute;n al subsidio en materia de seguridad social establecidos en el Decreto por el
					que se otorgan est&iacute;mulos para promover la incorporaci&oacute;n a la seguridad social.
				</div>
			</c:if>

			<c:if test="${empty solicitudTramite.errorFormGeneral}">
				<form:form id="clasificacionForm" method="post" modelAttribute="sujetoTramite">
					<div id="seccionClasificacion" class="table_form">
						<div class="separadorseccion">
							<span>
								<spring:message code="titulo.nombre.comercial.manifestado" />
							</span>
						</div>
						
						<div class="form-horizontal">
							<c:if test="${sujetoTramite.clasificacion.indPrestaServicioPersonal eq 1}">
								<div class="form-group form-group-sm">
									<label class="col-sm-4 control-label" style="padding-top: 0px;">
										Indica el n&uacute;mero de centros de trabajo<span class="required" id="clasificacion.numCentrosTrabaReq">*</span>:
									</label>
									<div class="col-sm-8">
										<form:input path="clasificacion.numCentrosTraba" maxlength="4" size="50" onkeydown="validarNumeros(event)"
											onkeypress="validarNumeros(event)" onkeyup="validarNumeros(event)" cssClass="form-control ns_" />
										<span style="display:none;" class="error" id="clasificacion.numCentrosTrabaError"></span>
									</div>
								</div>
							</c:if>
	
							<div class="form-group form-group-sm">
								<label class="col-sm-4 control-label"> Nombre comercial: </label>
								<div class="col-sm-8">
									<form:input cssClass="nombreComercial form-control ns_" path="nombreComercial" maxlength="120" size="50"
										style='text-transform:none;' />
								</div>
							</div>
						</div>
						
						<div class="row m-t-lg m-b-sm">
							<div class="col-sm-12">
								Selecciona la Subdelegaci&oacute;n a la cual se asignar&aacute; el centro de trabajo<span class="required" id="municipioIMSS.subdelegacion.idReq">*</span>:
							</div>
						</div>
						
						<div class="row">
							<div class="col-sm-10 col-sm-offset-1">
								<form:hidden path="municipioIMSS.idMunicipio" />
								<form:hidden path="municipioIMSS.cvecMunicipioSINDO" />
								<form:hidden path="municipioIMSS.descMunicipio" />

								<form:hidden path="municipioIMSS.subdelegacion.id" />
								<form:hidden path="municipioIMSS.subdelegacion.clave" />
								<form:hidden path="municipioIMSS.subdelegacion.descripcion" />

								<form:hidden path="municipioIMSS.subdelegacion.delegacion.id" />
								<form:hidden path="municipioIMSS.subdelegacion.delegacion.clave" />
								<form:hidden path="municipioIMSS.subdelegacion.delegacion.descripcion" />
								<form:hidden path="municipioIMSS.subdelegacion.delegacion.ciz" />
								<div id="municipioImssContenedor" style="width: 100%;">
									<div style="text-align: center;">
										<img alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12">
								<span style="display:none;" class="error" id="municipioIMSS.subdelegacion.idError"></span>
							</div>
						</div>
						<table width="100%">
							<tr>
								<td colspan="2">
									<c:if test="${isAltaPorRL}">
										<c:if test="${origenApp eq origenINTERNET}">
											<div class="separadorseccion">
												<span> Tipo de poder<span class="required" id="tipoPoderAReq">*</span> </span>
											</div>
											Selecciona el tipo de poder que se te ha otorgado:							
											<table>
												<tr>
													<td>
														<form:checkbox path="tipoPoder" value="Dominio" onclick="enableOtroPoder('Dominio')"
															cssClass="uniqueCheckbox ns_" id="tipoPoderA"/>
													</td>
													<td>Dominio</td>

													<td>
														<form:checkbox path="tipoPoder" value="Administracion" onclick="enableOtroPoder('Administracion')"
															cssClass="uniqueCheckbox ns_"/>
													</td>
													<td>Administraci&oacute;n</td>

													<td>
														<form:checkbox path="tipoPoder" value="Especial" onclick="enableOtroPoder('Especial')"
															cssClass="uniqueCheckbox ns_" />
													</td>
													<td>Especial, para tr&aacute;mites ante el IMSS</td>
												</tr>
											</table>
										</c:if>
									</c:if>
								</td>
							</tr>
						</table>
						<div class="row">
							<div class="col-sm-12">
								<span style="display:none;" class="error" id="tipoPoderAError"></span>
							</div>
						</div>
						<form:hidden path="clasificacion.indRegPatClase" />
					</div>

					<c:if test="${isAltaPorRL}">
						<div id="personaAutorizada1">
							<div class="separadorseccion">
								<span> Persona autorizada 1 </span>
							</div>
							<div>
								<div class="row">
									<div class="col-xs-6">
										<div class="form-group">
											<label for="rfcPA1">
												<!--  span class="required">*</span>-->
												RFC:
											</label>
											<input type="text" class="alfanumericoEstricto form-control input-sm ns_" id="rfcPA1" maxlength="13"
												onblur="ejecutarConsulta(1)" value="${sujetoTramite.personasAutorizadas[0].fisica.rfc}" />
											<span class="error hiddenElement" id="rfcPA1Error"></span>
											<input type="hidden" id="idPersonaPA1" value="${sujetoTramite.personasAutorizadas[0].fisica.idPersona}" />
											<input type="hidden" id="cveFisicaPA1" value="${sujetoTramite.personasAutorizadas[0].fisica.cveFisica}" />
										</div>
									</div>
									<div class="col-xs-6">
										<div class="form-group">
											<label for="curpPA1">
												<!--  span class="required">*</span>-->
												CURP:
											</label>
											<input type="text" class="alfanumericoEstricto form-control input-sm ns_" id="curpPA1" maxlength="18"
												value="${sujetoTramite.personasAutorizadas[0].fisica.curp}" onblur="ejecutarConsulta(1)" />
											<span class="error hiddenElement" id="curpPA1Error"></span>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-xs-4">
										<div class="form-group">
											<label for="nombrePA1">Nombre:</label>
											<input class="alfanumerico form-control input-sm ns_" readonly="readonly" id="nombrePA1" maxlength="255"
												value="${sujetoTramite.personasAutorizadas[0].fisica.nombre}" />
										</div>
									</div>
									<div class="col-xs-4">
										<div class="form-group">
											<label for="apellidoPatPA1">Primer apellido:</label>
											<input class="alfanumerico form-control input-sm ns_" readonly="readonly" id="apellidoPatPA1" maxlength="255"
												value="${sujetoTramite.personasAutorizadas[0].fisica.primerApellido}" />
										</div>
									</div>
									<div class="col-xs-4">
										<div class="form-group">
											<label for="apellidoMatPA1">Segundo apellido:</label>
											<input class="textAfiliacion form-control input-sm ns_" readonly="readonly" id="apellidoMatPA1" maxlength="255"
												value="${sujetoTramite.personasAutorizadas[0].fisica.segundoApellido}" />
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-xs-4">
										<div class="form-group">
											<label for="numPA1">Tel&eacute;fono fijo:</label>
											<input class="numerico form-control ns_  input-sm" readonly="readonly" id="numPA1" maxlength="12"
												value="${sujetoTramite.personasAutorizadas[0].fisica.telefonoFijo.numero}" />
										</div>
									</div>
									<div class="col-xs-4">
										<div class="form-group">
											<label for="extPA1">Extensi&oacute;n:</label>
											<input class="numerico form-control ns_  input-sm" id="extPA1" maxlength="12" size="9"
												value="${sujetoTramite.personasAutorizadas[0].fisica.telefonoFijo.extension}" readonly="readonly" />
										</div>
									</div>
									<div class="col-xs-4">
										<div class="form-group">
											<label for="numMovPA1">Tel&eacute;fono m&oacute;vil:</label>
											<input class="numerico form-control ns_  input-sm" id="numMovPA1" maxlength="12" readonly="readonly"
												value="${sujetoTramite.personasAutorizadas[0].fisica.telefonoMovil.numero}" />
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-xs-4">
										<div class="form-group">
											<label for="correoPA1">Correo electr&oacute;nico:</label>
											<input id="correoPA1" maxlength="255" readonly="readonly" style="text-transform: none !important"
												class="correoElectronico form-control ns_  input-sm"
												value="${sujetoTramite.personasAutorizadas[0].fisica.correoElectronico.correo}" onblur="validarEmail('PA1')" />
										</div>
									</div>
									<div class="col-xs-4"></div>
									<div class="col-xs-4">
										<div class="opcion">
											<button type="button" class="btn btn-primary" onclick="limpiarForm(1)" name="1">Limpiar</button>
										</div>
									</div>
								</div>
								<!-- 				</form>-->
							</div>
						</div>
						<div id="personaAutorizada2">
							<div class="separadorseccion">
								<span> Persona autorizada 2 </span>
							</div>
							<div>
								<div class="row">
									<div class="col-xs-6">
										<div class="form-group">
											<label for="rfcPA2">
												<!--  span class="required">*</span>-->
												RFC:
											</label>
											<input type="text" class="alfanumericoEstricto form-control ns_  input-sm" id="rfcPA2" maxlength="13"
												onblur="ejecutarConsulta(2)" value="${sujetoTramite.personasAutorizadas[1].fisica.rfc}" />
											<span class="error hiddenElement" id="rfcPA2Error"></span>
											<input type="hidden" id="idPersonaPA2" value="${sujetoTramite.personasAutorizadas[1].fisica.idPersona}" />
											<input type="hidden" id="cveFisicaPA2" value="${sujetoTramite.personasAutorizadas[1].fisica.cveFisica}" />
										</div>
									</div>
									<div class="col-xs-6">
										<div class="form-group">
											<label for="curpPA2">
												<!--  span class="required">*</span>-->
												CURP:
											</label>
											<input type="text" class="alfanumericoEstricto form-control ns_  input-sm" id="curpPA2" maxlength="18"
												value="${sujetoTramite.personasAutorizadas[1].fisica.curp}" onblur="ejecutarConsulta(2)" />
											<span class="error hiddenElement" id="curpPA2Error"></span>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-xs-4">
										<div class="form-group">
											<label for="nombrePA2">Nombre:</label>
											<input class="alfanumerico form-control ns_  input-sm" readonly="readonly" id="nombrePA2" maxlength="255"
												value="${sujetoTramite.personasAutorizadas[1].fisica.nombre}" />
										</div>
									</div>
									<div class="col-xs-4">
										<div class="form-group">
											<label for="apellidoPatPA2">Primer apellido:</label>
											<input class="alfanumerico form-control ns_  input-sm" readonly="readonly" id="apellidoPatPA2" maxlength="255"
												value="${sujetoTramite.personasAutorizadas[1].fisica.primerApellido}" />
										</div>
									</div>
									<div class="col-xs-4">
										<div class="form-group">
											<label for="apellidoMatPA2">Segundo apellido:</label>
											<input class="textAfiliacion form-control ns_  input-sm" readonly="readonly" id="apellidoMatPA2" maxlength="255"
												value="${sujetoTramite.personasAutorizadas[1].fisica.segundoApellido}" />
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-xs-4">
										<div class="form-group">
											<label for="numPA2">Tel&eacute;fono fijo:</label>
											<input class="numerico form-control ns_  input-sm" readonly="readonly" id="numPA2" maxlength="12"
												value="${sujetoTramite.personasAutorizadas[1].fisica.telefonoFijo.numero}" />
										</div>
									</div>
									<div class="col-xs-4">
										<div class="form-group">
											<label for="extPA2">Extensi&oacute;n:</label>
											<input class="numerico form-control ns_  input-sm" id="extPA2" maxlength="12" size="9"
												value="${sujetoTramite.personasAutorizadas[1].fisica.telefonoFijo.extension}" readonly="readonly" />
										</div>
									</div>
									<div class="col-xs-4">
										<div class="form-group">
											<label for="numMovPA2">Tel&eacute;fono m&oacute;vil:</label>
											<input class="numerico form-control ns_  input-sm" id="numMovPA2" maxlength="12" readonly="readonly"
												value="${sujetoTramite.personasAutorizadas[1].fisica.telefonoMovil.numero}" />
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-xs-4">
										<div class="form-group">
											<label for="correoPA2">Correo electr&oacute;nico:</label>
											<input id="correoPA2" maxlength="255" readonly="readonly" style="text-transform: none !important"
												class="correoElectronico form-control ns_  input-sm"
												value="${sujetoTramite.personasAutorizadas[1].fisica.correoElectronico.correo}" onblur="validarEmail('PA2')" />
										</div>
									</div>
									<div class="col-xs-4"></div>
									<div class="col-xs-4">
										<div class="opcion">
											<button type="button" class="btn btn-primary" onclick="limpiarForm(2)" name="2">Limpiar</button>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div id="personaAutorizada3">
							<div class="separadorseccion">
								<span> Persona autorizada 3 </span>
							</div>
							<div>
								<div class="row">
									<div class="col-xs-6">
										<div class="form-group">
											<label for="rfcPA1">
												<!--  span class="required">*</span>-->
												RFC:
											</label>
											<input type="text" class="alfanumericoEstricto form-control ns_  input-sm" id="rfcPA3" maxlength="13"
												onblur="ejecutarConsulta(3)" value="${sujetoTramite.personasAutorizadas[2].fisica.rfc}" />
											<span class="error hiddenElement" id="rfcPA3Error"></span>
											<input type="hidden" id="idPersonaPA3" value="${sujetoTramite.personasAutorizadas[2].fisica.idPersona}" />
											<input type="hidden" id="cveFisicaPA3" value="${sujetoTramite.personasAutorizadas[2].fisica.cveFisica}" />
										</div>
									</div>
									<div class="col-xs-6">
										<div class="form-group">
											<label for="curpPA3">
												<!--  span class="required">*</span>-->
												CURP:
											</label>
											<input type="text" class="alfanumericoEstricto form-control ns_  input-sm" id="curpPA3" maxlength="18"
												value="${sujetoTramite.personasAutorizadas[2].fisica.curp}" onblur="ejecutarConsulta(3)" />
											<span class="error hiddenElement" id="curpPA3Error"></span>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-xs-4">
										<div class="form-group">
											<label for="nombrePA3">Nombre:</label>
											<input class="alfanumerico form-control ns_  input-sm" readonly="readonly" id="nombrePA3" maxlength="255"
												value="${sujetoTramite.personasAutorizadas[2].fisica.nombre}" />
										</div>
									</div>
									<div class="col-xs-4">
										<div class="form-group">
											<label for="apellidoPatPA3">Primer apellido:</label>
											<input class="alfanumerico form-control ns_  input-sm" readonly="readonly" id="apellidoPatPA3" maxlength="255"
												value="${sujetoTramite.personasAutorizadas[2].fisica.primerApellido}" />
										</div>
									</div>
									<div class="col-xs-4">
										<div class="form-group">
											<label for="apellidoMatPA3">Segundo apellido:</label>
											<input class="textAfiliacion form-control ns_  input-sm" readonly="readonly" id="apellidoMatPA3" maxlength="255"
												value="${sujetoTramite.personasAutorizadas[2].fisica.segundoApellido}" />
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-xs-4">
										<div class="form-group">
											<label for="numPA3">Tel&eacute;fono fijo:</label>
											<input class="numerico form-control ns_  input-sm" readonly="readonly" id="numPA3" maxlength="12"
												value="${sujetoTramite.personasAutorizadas[2].fisica.telefonoFijo.numero}" />
										</div>
									</div>
									<div class="col-xs-4">
										<div class="form-group">
											<label for="extPA3">Extensi&oacute;n:</label>
											<input class="numerico form-control ns_  input-sm" id="extPA3" maxlength="12" size="9"
												value="${sujetoTramite.personasAutorizadas[2].fisica.telefonoFijo.extension}" readonly="readonly" />
										</div>
									</div>
									<div class="col-xs-4">
										<div class="form-group">
											<label for="numMovPA3">Tel&eacute;fono m&oacute;vil:</label>
											<input class="numerico form-control ns_  input-sm" id="numMovPA3" maxlength="12" readonly="readonly"
												value="${sujetoTramite.personasAutorizadas[2].fisica.telefonoMovil.numero}" />
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-xs-4">
										<div class="form-group">
											<label for="correoPA3">Correo electr&oacute;nico:</label>
											<input id="correoPA3" maxlength="255" readonly="readonly" style="text-transform: none !important"
												class="correoElectronico form-control ns_  input-sm"
												value="${sujetoTramite.personasAutorizadas[2].fisica.correoElectronico.correo}" onblur="validarEmail('PA3')" />
										</div>
									</div>
									<div class="col-xs-4"></div>
									<div class="col-xs-4">
										<div class="opcion">
											<button type="button" class="btn btn-primary" onclick="limpiarForm(3)" name="3">Limpiar</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</c:if>

					<c:if test="${origenApp ne origenVENTANILLA}">
						<p class="alert alert-info">
							<b>Para concluir la solicitud, favor de dar clic en el bot&oacute;n de Acciones y seleccionar la
								opci&oacute;n - Finalizar tr&aacute;mite -.</b>
						</p>
					</c:if>
					<c:if test="${origenApp eq origenVENTANILLA}">
						</br>
						</br>
					</c:if>

				</form:form>
			</c:if>
			<!-- Form auxiliar ubicacion Centro Trabajo -->
			<form id="asentamientoForMunicipioForm">
					<c:choose>
						<c:when test="${sujetoTramite.clasificacion.indRegPatClase == 1}">
							<c:choose>
								<c:when test="${not empty sujetoTramite.fisica}">
									<input type="hidden" id="hdnCodigoPostal" name="codigoPostal.codigoPostal" 
										value="${sujetoTramite.fisica.domicilioFiscal.codigoPostal.codigoPostal}" />
								</c:when>
								<c:when test="${not empty sujetoTramite.moral}">
									<input type="hidden" id="hdnCodigoPostal" name="codigoPostal.codigoPostal" 
										value="${sujetoTramite.moral.domicilioFiscal.codigoPostal.codigoPostal}" />
								</c:when>
								<c:otherwise>
									<input type="hidden" id="hdnCodigoPostal" name="codigoPostal.codigoPostal" />
								</c:otherwise>
							</c:choose>								
						</c:when>
						<c:otherwise>
							<input type="hidden" id="hdnCveEnt" name="asentamiento.localidad.municipio.entidadFederativa.clave"
								value="${sujetoTramite.cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa.clave}" />
							<input type="hidden" id="hdnCveMun" name="asentamiento.localidad.municipio.clave"
								value="${sujetoTramite.cntroTrabajo.asentamiento.localidad.municipio.clave}" />
							<input type="hidden" id="hdnCodigoPostal" name="codigoPostal.codigoPostal"
								value="${sujetoTramite.cntroTrabajo.codigoPostal.codigoPostal}" />
						</c:otherwise>
					</c:choose>
			
			
				
			</form>
		</div>
	</div>

	<div class="pie row">
		<div class="col-sm-4">
			<div style="float: left; padding: 11px 0px;"><span class="required" id="labelCamposObligatoriosGeneral">*</span><spring:message code="label.camposObligatorios"/></div>
		</div>
		<div class="col-sm-8">
			<div class="pull-right">
			<c:if test="${empty solicitudTramite.errorFormGeneral}">
				<a id="pasoPrevio" class="btn btn-default" onclick="uid_call('imss.patrones.alta_patronal.subdelegacion.regresar','clickin');">
					<i class=" glyphicon glyphicon-step-backward"></i>
					Anterior
				</a>
				<c:if test="${origenApp eq origenVENTANILLA}">
					<a id="mostrarRLVentanilla" class="btn btn-primary">
						<i class="glyphicon glyphicon-step-forward"></i>
						Siguiente
					</a>
				</c:if>
					
				<div class="btn-group dropup">
					<a href="#" class="btn btn-primary">
						<spring:message code="label.menus.opciones" />
					</a>
					<a href="#" data-toggle="dropdown" class="btn btn-primary dropdown-toggle">
						<span class="caret"></span>
					</a>
					<ul class="dropdown-menu pull-right">
						<c:if test="${origenApp ne origenVENTANILLA}">
							<li>
								<a id="finalizarTramite" " onclick="uid_call('imss.patrones.alta_patronal.subdelegacion.link_finalizar','clickin');">
									<i class="glyphicon glyphicon-ok"></i>
									<spring:message code="wizard.button.finalizarTramite"></spring:message>
								</a>
							</li>
						</c:if>
						<li>
							<a id="guardarTramite"" onclick="uid_call('imss.patrones.alta_patronal.subdelegacion.link_guardar','clickin');">
								<i class="glyphicon glyphicon-download-alt"></i>
								<spring:message code="wizard.button.guardarTramite"/>
							</a>
						</li>
						<li>
							<a id="guardarCerrarTramite"" onclick="uid_call('imss.patrones.alta_patronal.subdelegacion.link_guardarCerrar','clickin');">
								<i class="glyphicon glyphicon-download-alt"></i>
								<spring:message code="wizard.button.guardarCerrar"/>
							</a>
						</li>
						<li>
							<a id="cancelarTramite"" onclick="uid_call('imss.patrones.alta_patronal.subdelegacion.link_cancelar','clickin');">
								<i class="glyphicon glyphicon-trash"></i>
								<spring:message code="wizard.button.cancelarTramite"/>
							</a>
						</li>
					</ul>
				</div>
			</c:if>
			</div>
		</div>

	</div>
</div>

<!-- Forma para invocar al servicio del ModificaciÃ³n Manual de Datos -->
<form id="comprobacionIcaForm" method="post"></form>

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
		¿Desea cancelar la solicitud pendiente con folio:
		<strong>${folioSolicitud}</strong>
		?
	</p>
</div>

<div id="dialog-confirm" title="Mensaje">
	<p>
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
		<label id="mensajeDialogo"></label>
	</p>
</div>

<div id="dialogoConfirmacion">
	<p><span id="textoConfirmacion"></span></p>
</div>

<div id="dialogoMensajes">
	<p><span id="textoMensaje"></span></p>
</div>

<div id="dialogoMsgError"></div>
<script language="JavaScript1.2" src="${staticResourcesPath}/js/comscore/Form.js"></script>