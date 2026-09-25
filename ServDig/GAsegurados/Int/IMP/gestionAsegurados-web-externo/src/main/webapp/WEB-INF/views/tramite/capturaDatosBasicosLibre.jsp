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

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<div class="contenedor">
	<c:choose>
		<c:when test="${not empty NSS_RECUPERADO }">
			<div class="alert alert-success nss-recuperado">Usted ya
				cuenta con N&uacute;mero de Seguridad Social, el cual le
				ser&aacute; enviado al correo electr&oacute;nico
				<strong>${tramiteAsegurado.fisica.correoElectronico.correo }</strong>, 
				que fue capturado en el paso anterior de este tr&aacute;mite.</div>
				<div style="text-align: left; float: left;">
					<br>
					<button type="button" id="btnImprimirNSSQR" class="btn btn-primary">Imprimir NSS</button>
					<button type="button" id="btnInicioTramite" class="btn btn-primary">Aceptar</button>
					<form id="formImprimeDocto" action="iniciar" method="post" target="_blank">
						<input type="hidden" value="${id_solicitud}" name="si" id="si"/>
 					</form>
				</div>
		</c:when>
		<c:when test="${not empty tramiteAsegurado.errorFormGeneral }">
			<div class="alert alert-danger">
				${tramiteAsegurado.errorFormGeneral}
				<c:if test="${not empty mostrarInstruccionesVentanilla }">
					<br><br>
					Para un mejor servicio al momento de acudir a ventanilla, debes tener a la mano los siguientes documentos:
					<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;a) <b>Acta de
						Nacimiento</b> de la persona a la que se le va a asignar el N&uacute;mero de Seguridad Social<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;b) <b>CURP</b>
					<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;c) <b>Identificaci&oacute;n
						Oficial</b> (Credencial para votar expedida por el Instituto Federal Electoral, Pasaporte vigente Mexicano o Extranjero, Cartilla del Servicio Militar Nacional, C&eacute;dula Profesional)
				</c:if>
			</div>
			<div style="text-align: left; float: left;">
				<br>
				<button type="button" id="btnInicioTramite" class="btn btn-primary">Aceptar</button>
			</div>
		</c:when>
		<c:otherwise>
			<div id="info-paso" style="margin-bottom: 50px;">
				<h3>Paso 2: Resumen de los datos de la persona</h3>
				<hr class="red" style="margin-bottom: 20px;">
			</div>
			
			<!-- Forma de la consulta de personas por datos basicos. -->
			<div>
				<div class="row">
					<div class="col-md-7 col-sm-12">
						<div class="alert alert-info"  style="text-align: center;">
							<strong><i class="glyphicon glyphicon-info-sign"
								style="margin-right: 8px;"></i>
								<c:choose>
									<c:when test="${not empty tramiteAsegurado.fisica.idPersona && tramiteAsegurado.fisica.idPersona ne 0}">
										La persona fue localizada en el IMSS
									</c:when>
									<c:otherwise>
										La persona fue localizada en el RENAPO
									</c:otherwise>
								</c:choose>
							</strong>
						</div>
					</div>
				</div>
				<div class="row">
					<form:form modelAttribute="tramiteAsegurado"
						id="registroPersonaFisicaForm"
						action="${contextpath}/tramite/crear"
						cssClass="form-horizontal" role="form">
						
						<input type="hidden" name="hDP" value="${hashDatosPersona }" />	
						<form:hidden path="fisica.personaCalificaciones[0].calificacion.idCalificacion" />

						<div class="col-md-7">
							<div id="datosBasicosDiv">
								<fieldset>
									<legend>
										Datos b&aacute;sicos de la persona
									</legend>
									
									<div class="form-group">
										<label for="registroCurp" class="col-xs-4 control-label">
											CURP<span class="required">*</span>:
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
											Nombre(s)<span class="required">*</span>:
										</label>
										<div class="col-xs-8">
											<form:input path="fisica.nombre" id="registroNombres"
												cssClass="form-control"
												maxlength="50" />
										</div>
									</div>
									
									<div class="form-group">
										<label for="registroPrimerApellido" class="col-xs-4 control-label">
											Primer apellido<span class="required">*</span>:
										</label>
										<div class="col-xs-8">
											<form:input path="fisica.primerApellido"
												id="registroPrimerApellido" 
												cssClass="form-control" maxlength="50" />
										</div>
									</div>
									
									<div class="form-group">
										<label for="registroSegundoApellido" class="col-xs-4 control-label">
											Segundo apellido:
										</label>
										<div class="col-xs-8">
											<form:input path="fisica.segundoApellido"
												id="registroSegundoApellido" 
												cssClass="form-control" maxlength="50" />
										</div>
									</div>
									
									<div class="form-group">
										<label for="fisica.sexo.idSexo" class="col-xs-4 control-label">
											Sexo<span class="required">*</span>:
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
											Fecha de nacimiento<span class="required">*</span>:
										</label>
										<div class="col-xs-8">
											<form:input path="fisica.fechaNacimiento"
												id="registroFechaNacimientoC" style="width: auto"
												cssClass="form-control" maxlength="10" />
										</div>
									</div>
									
									<div class="form-group">
										<label for="fisica.lugarNacimiento.clave" class="col-xs-4 control-label">
											Lugar de nacimiento<span class="required">*</span>:
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
									<span id="errorFormGeneralError" class="error hiddenElement"></span>
								</fieldset>
							</div>							
						</div>

						<div class="col-md-5" id="datosComplementarios">
							<div id="datosCurp" class="well p-xs">
								<fieldset style="width: 100%;">
									<legend>
										Datos de la c&eacute;dula (CURP)
									</legend>
									
									<div class="form-group">
										<label for="curpDocumento" class="col-xs-4 control-label">
											CURP<span class="required">*</span>:
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
						</div>
					</form:form>
				</div>
				<div class="row m-t-lg">
					<div class="col-sm-3">
						<div style="float: left; padding: 11px 0px;"><span class="required">*</span> Campos obligatorios</div>
					</div>
					<div class="col-md-4 col-sm-12 text-right">
						<button type="button" id="regresar" class="btn btn-default">Regresar</button>
						<button type="button" id="registrar" class="btn btn-primary">Continuar</button>
					</div>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</div>

<form id="formIniciarTramite" action="iniciar" method="get" ></form>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/tramite/tramite-capturaLibre.js" htmlEscape="true" />"></script>
