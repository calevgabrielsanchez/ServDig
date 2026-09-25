<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/personas/fisica/identificar/cambios-automaticos/resultado-consultar-comparar.js" htmlEscape="true" />"></script>

<style type="text/css">
	.dataTables_wrapper table thead {
		display: none;
	}
</style>

<div class="col-sm-12">
	<div>
		<c:set var="contextpath" value="<%=request.getContextPath()%>" />

		<c:choose>
	      <c:when test="${ocultarBotones !=null && ocultarBotones == true}">
	      	<c:set var="display" value="none" />
	      </c:when>
	
	      <c:otherwise>
	      	<c:set var="display" value="initial" />
	      </c:otherwise>
		</c:choose>

		<form:form modelAttribute="icaDatosRespuesta" id="forma"
			method="post"
			action="${contextpath}/persona/fisica/identificar/cambios-automaticos/integrarCambiosICA">
							
			<c:if test="${not empty icaDatosRespuesta.errorFormGeneral && 
							fn:contains(icaDatosRespuesta.errorFormGeneral , 'No existen diferencias')}">
				<div class="alert alert-danger">
					<button type="button" class="close" data-dismiss="alert">×</button>
					${icaDatosRespuesta.errorFormGeneral}
				</div>
			</c:if>

			<c:if test="${not empty icaDatosRespuesta.errorFormGeneral && 
							!fn:contains(icaDatosRespuesta.errorFormGeneral , 'No existen diferencias')}">
				<div class="alert alert-danger">
					<button type="button" class="close" data-dismiss="alert">×</button>
					<strong>Error: </strong>${icaDatosRespuesta.errorFormGeneral}
				</div>
			</c:if>

			<c:if test="${empty icaDatosRespuesta.errorFormGeneral}">
				<div class="alert alert-info">
					<button type="button" class="close" data-dismiss="alert">×</button>
					<strong>Resultado: </strong>Comparaci&oacute;n de consultas en
					IMSS y Entidades Externas
				</div>

				<div id="acordeon">
					<c:if test="${icaDatosRespuesta.indicadorConsultaRENAPO eq true }">
						<h3>
							<a href='#'>DATOS CONFIRMADOS POR RENAPO</a>
						</h3>
						<div id="datosConfirmadosRenapo">
							<c:if test="${not empty icaDatosRespuesta.traza['ESTATUS_CURP']}">
								<div class="alert alert-info" id="estatusCURPDiv">
									${icaDatosRespuesta.traza['ESTATUS_CURP']}
								</div>
							</c:if>
							<table class="table" id="tabla-imss-renapo">
								<thead>
									<tr>
										<th><b>DATOS CONFIRMADOS POR RENAPO</b></th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>
											<table cellpadding="0" cellspacing="0" class="tblICA table-striped">
												<tr>
													<td colspan="4" style="text-align: center;"><b>DATOS B&Aacute;SICOS</b></td>
												</tr>
												<tr>
													<td style="width: 15%;">&nbsp;</td>
													<td style="text-align: center; width: 40%;"><b>DATOS ACTUALES</b></td>
													<td style="text-align: center; width: 40%;"><b>DATOS ENTIDAD EXTERNA</b></td>
													<td style="width: 5%;">&nbsp;</td>
												</tr>
												<tr>
													<td style="font-weight: bold;">Nombre</td>
													<td>${icaDatosRespuesta.personaFisicaIMSS.nombre}</td>
													<td>${icaDatosRespuesta.personaFisicaEE.nombre}</td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['nombre'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if></td>
												</tr>
												<tr>
													<td style="font-weight: bold;">Primer Apellido</td>
													<td>${icaDatosRespuesta.personaFisicaIMSS.primerApellido}</td>
													<td>${icaDatosRespuesta.personaFisicaEE.primerApellido}</td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['primerApellido'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if></td>
												</tr>
												<tr>
													<td style="font-weight: bold;">Segundo Apellido</td>
													<td>${icaDatosRespuesta.personaFisicaIMSS.segundoApellido}</td>
													<td>${icaDatosRespuesta.personaFisicaEE.segundoApellido}</td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['segundoApellido'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if></td>
												</tr>
												<tr>
													<td style="font-weight: bold;">CURP</td>
													<td>${icaDatosRespuesta.personaFisicaIMSS.curp}</td>
													<td>${icaDatosRespuesta.personaFisicaEE.curp}</td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['curp'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if></td>
												</tr>
												<tr>
													<td style="font-weight: bold;">Sexo</td>
													<td>${icaDatosRespuesta.personaFisicaIMSS.sexo.descripcion}</td>
													<td>${icaDatosRespuesta.personaFisicaEE.sexo.descripcion}</td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['sexo'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if></td>
												</tr>
												<tr>
													<td style="font-weight: bold;">Fecha Nacimiento</td>
													<td>${icaDatosRespuesta.personaFisicaIMSS.fechaNacimientoFormateada}</td>
													<td>${icaDatosRespuesta.personaFisicaEE.fechaNacimientoFormateada}</td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['fechaNacimiento'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if></td>
												</tr>
												<tr>
													<td style="font-weight: bold;">Entidad de Nacimiento</td>
													<td>${icaDatosRespuesta.personaFisicaIMSS.lugarNacimiento.nombre}</td>
													<td>${icaDatosRespuesta.personaFisicaEE.lugarNacimiento.nombre}</td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['lugarNacimiento'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if></td>
												</tr>
												<tr>
													<td style="font-weight: bold;">Nacionalidad</td>
													<td>${icaDatosRespuesta.personaFisicaIMSS.pais.nacionalidad}</td>
													<td>${icaDatosRespuesta.personaFisicaEE.pais.nacionalidad}</td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['nacionalidad'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if></td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table cellpadding="0" cellspacing="0" class="tblICA table-striped">
												<c:choose>
													<c:when test="${not empty icaDatosRespuesta.personaFisicaEE.actaNacimiento}">
														<tr>
															<td colspan="4" style="text-align: center;"><b>ACTA DE NACIMIENTO</b></td>
														</tr>
														<tr>
															<td style="width: 15%;">&nbsp;</td>
															<td style="text-align: center; width: 40%;"><b>DATOS ACTUALES</b></td>
															<td style="text-align: center; width: 40%;"><b>DATOS ENTIDAD EXTERNA</b></td>
															<td style="width: 5%;">&nbsp;</td>
														</tr>
														<tr>
															<td style="font-weight: bold;">A&ntilde;o de Registro</td>
															<td>${icaDatosRespuesta.personaFisicaIMSS.actaNacimiento.anio}</td>
															<td>${icaDatosRespuesta.personaFisicaEE.actaNacimiento.anio}</td>
															<td><c:if
																	test="${icaDatosRespuesta.cambios['actaNacimiento.anio'].id ne 4}">
																	<span class="label label-danger"
																		style="font-size: xx-small;" title="CAMBIO">*</span>
																</c:if></td>
														</tr>
														<tr>
															<td style="font-weight: bold;">No. Acta</td>
															<td>${icaDatosRespuesta.personaFisicaIMSS.actaNacimiento.noActa}</td>
															<td>${icaDatosRespuesta.personaFisicaEE.actaNacimiento.noActa}</td>
															<td><c:if
																	test="${icaDatosRespuesta.cambios['actaNacimiento.acta'].id ne 4}">
																	<span class="label label-danger"
																		style="font-size: xx-small;" title="CAMBIO">*</span>
																</c:if></td>
														</tr>
														<tr>
															<td style="font-weight: bold;">No. Foja</td>
															<td>${icaDatosRespuesta.personaFisicaIMSS.actaNacimiento.noFoja}</td>
															<td>${icaDatosRespuesta.personaFisicaEE.actaNacimiento.noFoja}</td>
															<td><c:if
																	test="${icaDatosRespuesta.cambios['actaNacimiento.foja'].id ne 4}">
																	<span class="label label-danger"
																		style="font-size: xx-small;" title="CAMBIO">*</span>
																</c:if></td>
														</tr>
														<tr>
															<td style="font-weight: bold;">No. Libro</td>
															<td>${icaDatosRespuesta.personaFisicaIMSS.actaNacimiento.noLibro}</td>
															<td>${icaDatosRespuesta.personaFisicaEE.actaNacimiento.noLibro}</td>
															<td><c:if
																	test="${icaDatosRespuesta.cambios['actaNacimiento.libro'].id ne 4}">
																	<span class="label label-danger"
																		style="font-size: xx-small;" title="CAMBIO">*</span>
																</c:if></td>
														</tr>
														<tr>
															<td style="font-weight: bold;">Tomo</td>
															<td>${icaDatosRespuesta.personaFisicaIMSS.actaNacimiento.tomo}</td>
															<td>${icaDatosRespuesta.personaFisicaEE.actaNacimiento.tomo}</td>
															<td><c:if
																	test="${icaDatosRespuesta.cambios['actaNacimiento.tomo'].id ne 4}">
																	<span class="label label-danger"
																		style="font-size: xx-small;" title="CAMBIO">*</span>
																</c:if></td>
														</tr>
														<tr>
															<td style="font-weight: bold;">CRIP</td>
															<td>${icaDatosRespuesta.personaFisicaIMSS.actaNacimiento.crip}</td>
															<td>${icaDatosRespuesta.personaFisicaEE.actaNacimiento.crip}</td>
															<td><c:if
																	test="${icaDatosRespuesta.cambios['actaNacimiento.crip'].id ne 4}">
																	<span class="label label-danger"
																		style="font-size: xx-small;" title="CAMBIO">*</span>
																</c:if></td>
														</tr>
														<tr>
															<td style="font-weight: bold;">Entidad de Registro</td>
															<td>${icaDatosRespuesta.personaFisicaIMSS.actaNacimiento.municipio.entidadFederativa.nombre}</td>
															<td>${icaDatosRespuesta.personaFisicaEE.actaNacimiento.municipio.entidadFederativa.nombre}</td>
															<td><c:if
																	test="${icaDatosRespuesta.cambios['actaNacimiento.entidad'].id ne 4}">
																	<span class="label label-danger"
																		style="font-size: xx-small;" title="CAMBIO">*</span>
																</c:if></td>
														</tr>
														<tr>
															<td style="font-weight: bold;">Municipio de Registro</td>
															<td>${icaDatosRespuesta.personaFisicaIMSS.actaNacimiento.municipio.nombre}</td>
															<td>${icaDatosRespuesta.personaFisicaEE.actaNacimiento.municipio.nombre}</td>
															<td><c:if
																	test="${icaDatosRespuesta.cambios['actaNacimiento.municipio'].id ne 4}">
																	<span class="label label-danger"
																		style="font-size: xx-small;" title="CAMBIO">*</span>
																</c:if></td>
														</tr>
													</c:when>
													<c:when test="${not empty icaDatosRespuesta.personaFisicaEE.documentoMigratorio}">
														<tr>
															<td colspan="4" style="text-align: center;"><b>DOCUMENTO MIGRATORIO</b></td>
														</tr>
														<tr>
															<td style="width: 15%;">&nbsp;</td>
															<td style="text-align: center; width: 40%;"><b>DATOS ACTUALES</b></td>
															<td style="text-align: center; width: 40%;"><b>DATOS ENTIDAD EXTERNA</b></td>
															<td style="width: 5%;">&nbsp;</td>
														</tr>
														<tr>
															<td style="font-weight: bold;">N&uacute;mero del Registro Nacional de Extranjeros</td>
															<td>${icaDatosRespuesta.personaFisicaIMSS.documentoMigratorio.numFolioExtranjero}</td>
															<td>${icaDatosRespuesta.personaFisicaEE.documentoMigratorio.numFolioExtranjero}</td>
															<td><c:if
																	test="${icaDatosRespuesta.cambios['documentoMigratorio.numRegExtranjeros'].id ne 4}">
																	<span class="label label-danger"
																		style="font-size: xx-small;" title="CAMBIO">*</span>
																</c:if></td>
														</tr>
														<tr>
															<td style="font-weight: bold;">N&uacute;mero de Expediente del Documento Migratorio</td>
															<td>${icaDatosRespuesta.personaFisicaIMSS.documentoMigratorio.noActa}</td>
															<td>${icaDatosRespuesta.personaFisicaEE.documentoMigratorio.noActa}</td>
															<td><c:if
																	test="${icaDatosRespuesta.cambios['documentoMigratorio.numExpediente'].id ne 4}">
																	<span class="label label-danger"
																		style="font-size: xx-small;" title="CAMBIO">*</span>
																</c:if></td>
														</tr>
													</c:when>
													<c:when test="${not empty icaDatosRespuesta.personaFisicaEE.cartaNaturalizacion}">
														<tr>
															<td colspan="4" style="text-align: center;"><b>CARTA DE NATURALIZACI&Oacute;N</b></td>
														</tr>
														<tr>
															<td style="width: 15%;">&nbsp;</td>
															<td style="text-align: center; width: 40%;"><b>DATOS ACTUALES</b></td>
															<td style="text-align: center; width: 40%;"><b>DATOS ENTIDAD EXTERNA</b></td>
															<td style="width: 5%;">&nbsp;</td>
														</tr>
														<tr>
															<td style="font-weight: bold;">A&ntilde;o de Registro</td>
															<td>${icaDatosRespuesta.personaFisicaIMSS.cartaNaturalizacion.anioRegistro}</td>
															<td>${icaDatosRespuesta.personaFisicaEE.cartaNaturalizacion.anioRegistro}</td>
															<td><c:if
																	test="${icaDatosRespuesta.cambios['cartaNaturalizacion.anio'].id ne 4}">
																	<span class="label label-danger"
																		style="font-size: xx-small;" title="CAMBIO">*</span>
																</c:if></td>
														</tr>
														<tr>
															<td style="font-weight: bold;">Folio de la Carta</td>
															<td>${icaDatosRespuesta.personaFisicaIMSS.cartaNaturalizacion.numFolioExtranjero}</td>
															<td>${icaDatosRespuesta.personaFisicaEE.cartaNaturalizacion.numFolioExtranjero}</td>
															
															<td><c:if
																	test="${icaDatosRespuesta.cambios['cartaNaturalizacion.folio'].id ne 4}">
																	<span class="label label-danger"
																		style="font-size: xx-small;" title="CAMBIO">*</span>
																</c:if></td>
														</tr>
													</c:when>
													<c:when test="${not empty icaDatosRespuesta.personaFisicaEE.numeroUnicoExtranjero}">
														<tr>
															<td colspan="4" style="text-align: center;"><b>N&Uacute;MERO &Uacute;NICO DE EXTRANJERO</b></td>
														</tr>
														<tr>
															<td style="width: 15%;">&nbsp;</td>
															<td style="text-align: center; width: 40%;"><b>DATOS ACTUALES</b></td>
															<td style="text-align: center; width: 40%;"><b>DATOS ENTIDAD EXTERNA</b></td>
															<td style="width: 5%;">&nbsp;</td>
														</tr>
														<tr>
															<td style="font-weight: bold;">N&uacute;mero de folio</td>
															<td>${icaDatosRespuesta.personaFisicaIMSS.numeroUnicoExtranjero.numFolioExtranjero}</td>
															<td>${icaDatosRespuesta.personaFisicaEE.numeroUnicoExtranjero.numFolioExtranjero}</td>
															<td><c:if
																	test="${icaDatosRespuesta.cambios['numUnicoExtranjero.folio'].id ne 4}">
																	<span class="label label-danger"
																		style="font-size: xx-small;" title="CAMBIO">*</span>
																</c:if></td>
														</tr>
													</c:when>
													<c:when test="${not empty icaDatosRespuesta.personaFisicaEE.certificadoNacionalidadMexicana}">
														<tr>
															<td colspan="4" style="text-align: center;"><b>CERTIFICADO DE NACIONALIDAD MEXICANA</b></td>
														</tr>
														<tr>
															<td style="width: 15%;">&nbsp;</td>
															<td style="text-align: center; width: 40%;"><b>DATOS ACTUALES</b></td>
															<td style="text-align: center; width: 40%;"><b>DATOS ENTIDAD EXTERNA</b></td>
															<td style="width: 5%;">&nbsp;</td>
														</tr>
														<tr>
															<td style="font-weight: bold;">A&ntilde;o de Registro</td>
															<td>${icaDatosRespuesta.personaFisicaIMSS.certificadoNacionalidadMexicana.anioRegistro}</td>
															<td>${icaDatosRespuesta.personaFisicaEE.certificadoNacionalidadMexicana.anioRegistro}</td>
															<td><c:if
																	test="${icaDatosRespuesta.cambios['certificadoNacionalidad.anio'].id ne 4}">
																	<span class="label label-danger"
																		style="font-size: xx-small;" title="CAMBIO">*</span>
																</c:if></td>
														</tr>
														<tr>
															<td style="font-weight: bold;">Folio de la carta</td>
															<td>${icaDatosRespuesta.personaFisicaIMSS.certificadoNacionalidadMexicana.numFolioExtranjero}</td>
															<td>${icaDatosRespuesta.personaFisicaEE.certificadoNacionalidadMexicana.numFolioExtranjero}</td>
															<td><c:if
																	test="${icaDatosRespuesta.cambios['certificadoNacionalidad.folio'].id ne 4}">
																	<span class="label label-danger"
																		style="font-size: xx-small;" title="CAMBIO">*</span>
																</c:if></td>
														</tr>
													</c:when>
													<c:when test="${not empty icaDatosRespuesta.personaFisicaEE.oficioSolicitanteRefugiado}">
														<tr>
															<td colspan="4" style="text-align: center;"><b>OFICIO SOLICITANTE DE REFUGIADO</b></td>
														</tr>
														<tr>
															<td style="width: 15%;">&nbsp;</td>
															<td style="text-align: center; width: 40%;"><b>DATOS ACTUALES</b></td>
															<td style="text-align: center; width: 40%;"><b>DATOS ENTIDAD EXTERNA</b></td>
															<td style="width: 5%;">&nbsp;</td>
														</tr>
														<tr>
															<td style="font-weight: bold;">N&uacute;mero de Folio</td>
															<td>${icaDatosRespuesta.personaFisicaIMSS.oficioSolicitanteRefugiado.numFolioExtranjero}</td>
															<td>${icaDatosRespuesta.personaFisicaEE.oficioSolicitanteRefugiado.numFolioExtranjero}</td>
															<td><c:if
																	test="${icaDatosRespuesta.cambios['oficioRefugiado.folio'].id ne 4}">
																	<span class="label label-danger"
																		style="font-size: xx-small;" title="CAMBIO">*</span>
																</c:if></td>
														</tr>
													</c:when>
													<c:when test="${not empty icaDatosRespuesta.personaFisicaEE.formaMigratoriaTurista}">
														<tr>
															<td colspan="4" style="text-align: center;"><b>FORMA MIGRATORIA TURISTA</b></td>
														</tr>
														<tr>
															<td style="width: 15%;">&nbsp;</td>
															<td style="text-align: center; width: 40%;"><b>DATOS ACTUALES</b></td>
															<td style="text-align: center; width: 40%;"><b>DATOS ENTIDAD EXTERNA</b></td>
															<td style="width: 5%;">&nbsp;</td>
														</tr>
														<tr>
															<td style="font-weight: bold;">N&uacute;mero de Folio</td>
															<td>${icaDatosRespuesta.personaFisicaIMSS.formaMigratoriaTurista.numFolioExtranjero}</td>
															<td>${icaDatosRespuesta.personaFisicaEE.formaMigratoriaTurista.numFolioExtranjero}</td>
															<td><c:if
																	test="${icaDatosRespuesta.cambios['formaMigratoria.folio'].id ne 4}">
																	<span class="label label-danger"
																		style="font-size: xx-small;" title="CAMBIO">*</span>
																</c:if></td>
														</tr>
													</c:when>
													<c:otherwise>
														<tr>
															<td colspan="4" style="text-align: center;"><b>SIN DOCUMENTO PROBATORIO</b></td>
														</tr>
													</c:otherwise>
												</c:choose>
											</table>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</c:if>
					
					<c:if test="${icaDatosRespuesta.indicadorConsultaSAT eq true }">
						<h3>
							<a href='#'>DATOS CONFIRMADOS POR SAT</a>
						</h3>
						<div id="datosConfirmadosSat">

							<table class="table" id="tabla-imss-sat">
								<thead>
									<tr>
										<th><b>DATOS CONFIRMADOS POR SAT</b></th>
									</tr>
								</thead>

								<tbody>
									<tr>
										<td>
											<table cellpadding="0" cellspacing="0" class="tblICA table-striped">
												<tr>
													<td colspan="4" style="text-align: center;"><b>DATOS B&Aacute;SICOS</b></td>
												</tr>
												<tr>
													<td style="width: 15%;">&nbsp;</td>
													<td style="text-align: center; width: 40%;"><b>DATOS ACTUALES</b></td>
													<td style="text-align: center; width: 40%;"><b>DATOS ENTIDAD EXTERNA</b></td>
													<td style="width: 5%;">&nbsp;</td>
												</tr>
												<tr>
													<td style="font-weight: bold;">Nombre</td>
													<td>${icaDatosRespuesta.personaFisicaIMSS.nombre}</td>
													<td>${icaDatosRespuesta.personaFisicaEE.nombre}</td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['nombre'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if></td>
												</tr>
												<tr>
													<td style="font-weight: bold;">Primer Apellido</td>
													<td>${icaDatosRespuesta.personaFisicaIMSS.primerApellido}</td>
													<td>${icaDatosRespuesta.personaFisicaEE.primerApellido}</td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['primerApellido'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if></td>
												</tr>
												<tr>
													<td style="font-weight: bold;">Segundo Apellido</td>
													<td>${icaDatosRespuesta.personaFisicaIMSS.segundoApellido}</td>
													<td>${icaDatosRespuesta.personaFisicaEE.segundoApellido}</td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['segundoApellido'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if></td>
												</tr>
												<tr>
													<td style="font-weight: bold;">RFC</td>
													<td>${icaDatosRespuesta.personaFisicaIMSS.rfc}</td>
													<td>${icaDatosRespuesta.personaFisicaEE.rfc}</td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['rfc'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if></td>
												</tr>
												<tr>
													<td style="font-weight: bold;">Situaci&oacute;n</td>
													<td>${icaDatosRespuesta.personaFisicaIMSS.situacionesSAT[0].descripcion}</td>
													<td>${icaDatosRespuesta.personaFisicaEE.situacionesSAT[0].descripcion}</td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['situacionSAT'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if></td>
												</tr>
												<tr>
													<td style="font-weight: bold;">Fecha de
														Inicio de Operaciones</td>
													<td><fmt:formatDate value="${icaDatosRespuesta.personaFisicaIMSS.datosPersonaSAT.fechaInicioOperaciones}" pattern="dd/MM/yyyy"/></td>
													<td><fmt:formatDate value="${icaDatosRespuesta.personaFisicaEE.datosPersonaSAT.fechaInicioOperaciones}" pattern="dd/MM/yyyy"/></td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['fechaInicioOperaciones'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table cellpadding="0" cellspacing="0" class="tblICA table-striped">
												<tr>
													<td colspan="4" style="text-align: center;"><b>DOMICILIO FISCAL</b></td>
												</tr>
												<tr>
													<td style="width: 15%;">&nbsp;</td>
													<td style="text-align: center; width: 40%;"><b>DATOS ACTUALES</b></td>
													<td style="text-align: center; width: 40%;"><b>DATOS ENTIDAD EXTERNA</b></td>
													<td style="width: 5%;">&nbsp;</td>
												</tr>
												<tr>
													<td style="font-weight: bold;">C&oacute;digo Postal</td>
													<td>${icaDatosRespuesta.personaFisicaIMSS.domicilioFiscal.codigoPostal.codigoPostal}</td>
													<td>${icaDatosRespuesta.personaFisicaEE.domicilioFiscal.codigoPostal.codigoPostal}</td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['codigoPostal'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if></td>
												</tr>
												<tr>
													<td style="font-weight: bold;">Calle</td>
													<td>${icaDatosRespuesta.personaFisicaIMSS.domicilioFiscal.calle}</td>
													<td>${icaDatosRespuesta.personaFisicaEE.domicilioFiscal.calle}</td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['calle'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if></td>
												</tr>
												<tr>
													<td style="font-weight: bold;">Colonia</td>
													<td>${icaDatosRespuesta.personaFisicaIMSS.domicilioFiscal.colonia}</td>
													<td>${icaDatosRespuesta.personaFisicaEE.domicilioFiscal.colonia}</td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['colonia'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if></td>
												</tr>
												<tr>
													<td style="font-weight: bold;">Entidad Federativa</td>
													<td>${icaDatosRespuesta.personaFisicaIMSS.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre}</td>
													<td>${icaDatosRespuesta.personaFisicaEE.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre}</td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['entidad'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if></td>
												</tr>
												<tr>
													<td style="font-weight: bold;">Localidad</td>
													<td>${icaDatosRespuesta.personaFisicaIMSS.domicilioFiscal.asentamiento.localidad.nombre}</td>
													<td>${icaDatosRespuesta.personaFisicaEE.domicilioFiscal.asentamiento.localidad.nombre}</td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['localidad'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if></td>
												</tr>
												<tr>
													<td style="font-weight: bold;">Municipio</td>
													<td>${icaDatosRespuesta.personaFisicaIMSS.domicilioFiscal.asentamiento.localidad.municipio.nombre}</td>
													<td>${icaDatosRespuesta.personaFisicaEE.domicilioFiscal.asentamiento.localidad.municipio.nombre}</td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['municipio'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if></td>
												</tr>
												<tr>
													<td style="font-weight: bold;">Entre Calle 1</td>
													<td>${icaDatosRespuesta.personaFisicaIMSS.domicilioFiscal.vialidadReferenciaPrimaria.nombre}</td>
													<td>${icaDatosRespuesta.personaFisicaEE.domicilioFiscal.vialidadReferenciaPrimaria.nombre}</td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['entreCalle1'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if></td>
												</tr>
												<tr>
													<td style="font-weight: bold;">Entre Calle 2</td>
													<td>${icaDatosRespuesta.personaFisicaIMSS.domicilioFiscal.vialidadReferenciaSecundaria.nombre}</td>
													<td>${icaDatosRespuesta.personaFisicaEE.domicilioFiscal.vialidadReferenciaSecundaria.nombre}</td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['entreCalle2'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if></td>
												</tr>
												<tr>
													<td style="font-weight: bold;">Inmueble</td>
													<td>${icaDatosRespuesta.personaFisicaIMSS.domicilioFiscal.asentamiento.tipoAsentamiento.descripcion}</td>
													<td>${icaDatosRespuesta.personaFisicaEE.domicilioFiscal.asentamiento.tipoAsentamiento.descripcion}</td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['inmueble'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if></td>
												</tr>
												<tr>
													<td style="font-weight: bold;">Referencia</td>
													<td>${icaDatosRespuesta.personaFisicaIMSS.domicilioFiscal.descripcion}</td>
													<td>${icaDatosRespuesta.personaFisicaEE.domicilioFiscal.descripcion}</td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['referencia'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if></td>
												</tr>
												<tr>
													<td style="font-weight: bold;">Vialidad</td>
													<td>${icaDatosRespuesta.personaFisicaIMSS.domicilioFiscal.vialidadPrimaria.nombre}</td>
													<td>${icaDatosRespuesta.personaFisicaEE.domicilioFiscal.vialidadPrimaria.nombre}</td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['vialidad'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if></td>
												</tr>
												<tr>
													<td style="font-weight: bold;">N&uacute;mero Interior</td>
													<td>${icaDatosRespuesta.personaFisicaIMSS.domicilioFiscal.numInteriorAlf}</td>
													<td>${icaDatosRespuesta.personaFisicaEE.domicilioFiscal.numInteriorAlf}</td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['numeInt'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if></td>
												</tr>
												<tr>
													<td style="font-weight: bold;">N&uacute;mero Exterior</td>
													<td>${icaDatosRespuesta.personaFisicaIMSS.domicilioFiscal.numExteriorAlf}</td>
													<td>${icaDatosRespuesta.personaFisicaEE.domicilioFiscal.numExteriorAlf}</td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['numeExt'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if></td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table cellpadding="0" cellspacing="0" class="tblICA table-striped">
												<tr>
													<td colspan="4" style="text-align: center;"><b>FORMA DE CONTACTO 1 (TEL&Eacute;FONO FIJO)</b></td>
												</tr>
												<tr>
													<td style="width: 15%;">&nbsp;</td>
													<td style="text-align: center; width: 40%;"><b>DATOS ACTUALES</b></td>
													<td style="text-align: center; width: 40%;"><b>DATOS ENTIDAD EXTERNA</b></td>
													<td style="width: 5%;">&nbsp;</td>
												</tr>
												<tr>
													<td style="font-weight: bold;">N&uacute;mero</td>
													<td>${icaDatosRespuesta.personaFisicaIMSS.telefonoFijoFiscal.numero}</td>
													<td>${icaDatosRespuesta.personaFisicaEE.telefonoFijoFiscal.numero}</td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['telefonoFijo.numero'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if></td>
												</tr>
												<tr>
													<td style="font-weight: bold;">Clave lada</td>
													<td>${icaDatosRespuesta.personaFisicaIMSS.telefonoFijoFiscal.claveLada}</td>
													<td>${icaDatosRespuesta.personaFisicaEE.telefonoFijoFiscal.claveLada}</td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['telefonoFijo.lada'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if></td>
												</tr>
												<tr>
													<td style="font-weight: bold;">Extensi&oacute;n</td>
													<td>${icaDatosRespuesta.personaFisicaIMSS.telefonoFijoFiscal.extension}</td>
													<td>${icaDatosRespuesta.personaFisicaEE.telefonoFijoFiscal.extension}</td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['telefonoFijo.extension'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if></td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table cellpadding="0" cellspacing="0" class="tblICA table-striped">
												<tr>
													<td colspan="4" style="text-align: center;"><b>FORMA DE CONTACTO 2 (TEL&Eacute;FONO M&Oacute;VIL)</b></td>
												</tr>
												<tr>
													<td style="width: 15%;">&nbsp;</td>
													<td style="text-align: center; width: 40%;"><b>DATOS ACTUALES</b></td>
													<td style="text-align: center; width: 40%;"><b>DATOS ENTIDAD EXTERNA</b></td>
													<td style="width: 5%;">&nbsp;</td>
												</tr>
												<tr>
													<td style="font-weight: bold;">N&uacute;mero</td>
													<td>${icaDatosRespuesta.personaFisicaIMSS.telefonoMovilFiscal.numero}</td>
													<td>${icaDatosRespuesta.personaFisicaEE.telefonoMovilFiscal.numero}</td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['telefonoMovil'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if></td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table cellpadding="0" cellspacing="0" class="tblICA table-striped">
												<tr>
													<td colspan="4" style="text-align: center;"><b>FORMA DE CONTACTO 3 (CORREO ELECTR&Oacute;NICO)</b></td>
												</tr>
												<tr>
													<td style="width: 15%;">&nbsp;</td>
													<td style="text-align: center; width: 40%;"><b>DATOS ACTUALES</b></td>
													<td style="text-align: center; width: 40%;"><b>DATOS ENTIDAD EXTERNA</b></td>
													<td style="width: 5%;">&nbsp;</td>
												</tr>
												<tr>
													<td style="font-weight: bold;">Correo</td>
													<td>${icaDatosRespuesta.personaFisicaIMSS.correoElectronicoFiscal.correo}</td>
													<td>${icaDatosRespuesta.personaFisicaEE.correoElectronicoFiscal.correo}</td>
													<td><c:if
															test="${icaDatosRespuesta.cambios['correoElectronico'].id ne 4}">
															<span class="label label-danger"
																style="font-size: xx-small;" title="CAMBIO">*</span>
														</c:if></td>
												</tr>
											</table>
										</td>
									</tr>
								</tbody>
							</table>
							<br />
						</div>
					</c:if>
				</div>
				<!-- acordeon -->
			</c:if>
			<div class="text-right m-t-lg">
				<button type="button" style="display:${display}" class="btn btn-default" id="btnCancelar"> Cancelar</button>
				<button type="button" style="display:${display}" class="btn btn-primary" id="btnIntegrarCambios">Aceptar</button>
			</div>
		</form:form>

	</div>
</div>