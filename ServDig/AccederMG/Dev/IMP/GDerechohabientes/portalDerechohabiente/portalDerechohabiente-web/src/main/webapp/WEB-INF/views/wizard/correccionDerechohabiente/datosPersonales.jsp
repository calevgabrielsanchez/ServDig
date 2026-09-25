<%@ include file="../../general/taglibs.jsp" %>
<!-- 
<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/fisica/actualizacion-datos/contenido.js" htmlEscape="true" />"></script>
 -->
<script type="text/javascript">
	var codigoTipoSolicitud = ${codigoTipoSolicitud};
	var descripcionTipoSolicitud = '${descripcionTipoSolicitud}';
	var arrayCodigoTipoTramite = ${codigoTipoTramite};

	var datosEntradaFirma = {
		fechaElectronica : '${datosFirmaElectronica.fechaElectronicaFormateada}',
		nombreCompleto : '${datosFirmaElectronica.nombreCompleto}',
		registroPatronal : '${datosFirmaElectronica.registroPatronal}',
		rfc : '${datosFirmaElectronica.rfc}',
		curp : '${datosFirmaElectronica.curp}'
	};
	
	var nuevosDatosPersonales;
</script>

<style type="text/css">
	.dataTables_wrapper table thead {
		display: none;
	}
</style>


<div class="contenedor">

	<div class="contenido" style="width: 100%;">

		<form:form modelAttribute="icaDatosAux" id="forma" method="post">

			<c:if
				test="${not empty icaDatosAux.errorFormGeneral && 
								fn:contains(icaDatosAux.errorFormGeneral , 'No existen diferencias')}">
				<div class="alert alert-danger">
					<button type="button" class="close" data-dismiss="alert">×</button>
					${icaDatosAux.errorFormGeneral}
				</div>
			</c:if>

			<c:if
				test="${not empty icaDatosAux.errorFormGeneral && 
								!fn:contains(icaDatosAux.errorFormGeneral , 'No existen diferencias')}">
				<div class="alert alert-danger">
					<button type="button" class="close" data-dismiss="alert">×</button>
					<strong>Error: </strong>${icaDatosAux.errorFormGeneral}
				</div>
			</c:if>

			<c:if test="${empty icaDatosAux.errorFormGeneral}">
			
				<script type="text/javascript">
					nuevosDatosPersonales = {
							nombreRazonSocial : '${icaDatosAux.personaFisicaEE.nombre} ${icaDatosAux.personaFisicaEE.primerApellido} ${icaDatosAux.personaFisicaEE.segundoApellido}',
							rfc : '${icaDatosAux.personaFisicaEE.rfc}',
							curp : '${icaDatosAux.personaFisicaEE.curp}'
					};
				</script>
				<input type="hidden" id="idSolicitud" value="${idSolicitud}" />
				<input type="hidden" id="folioSolicitud" value="${folioSolicitud}" />
				<input type="hidden" id="rfcPersona" value="${rfcPersona}" />
				<input type="hidden" id="contenidoFirmar" value="${contenidoFirmar}" />
				
				<div class="alert alert-success">
					<c:choose>
						<c:when test="${!isRetomar}">
							Su solicitud ha sido iniciado correctamente y se le ha asignado a dicha solicitud el folio: <strong>${folioSolicitud}</strong>
						</c:when>
						<c:otherwise>
							El folio de la solicitud que esta retomando es: <strong>${folioSolicitud}</strong>
						</c:otherwise>
					</c:choose>
				</div>
				
				<div class="alert alert-info">
					<button type="button" class="close" data-dismiss="alert">×</button>
					A continuaci&oacute;n se detallan las diferencias entre la
					informaci&oacute;n registrada en el Instituto y las entidad externa RENAPO.
				</div>

					<h3>
						<a href='#'>DATOS CONFIRMADOS POR RENAPO</a>
					</h3>
					<div id="datosConfirmadosRenapo">
						<c:if test="${not empty icaDatosAux.traza['ESTATUS_CURP']}">
							<div class="alert" id="estatusCURPDiv" style="font-size: small;">
								${icaDatosAux.traza['ESTATUS_CURP']}</div>
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
										<table cellpadding="0" cellspacing="0" class="tblICA">
											<tr>
												<td colspan="4" style="text-align: center;"><b>DATOS
														B&Aacute;SICOS</b></td>
											</tr>
											<tr>
												<td style="width: 15%;">&nbsp;</td>
												<td style="text-align: center; width: 40%;"><b>DATOS
														ACTUALES</b></td>
												<td style="text-align: center; width: 40%;"><b>DATOS
														ENTIDAD EXTERNA</b></td>
												<td style="width: 5%;">&nbsp;</td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Nombre</td>
												<td>${icaDatosAux.personaFisicaIMSS.nombre}</td>
												<td>${icaDatosAux.personaFisicaEE.nombre}</td>
												<td><c:if
														test="${icaDatosAux.cambios['nombre'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Primer Apellido</td>
												<td>${icaDatosAux.personaFisicaIMSS.primerApellido}</td>
												<td>${icaDatosAux.personaFisicaEE.primerApellido}</td>
												<td><c:if
														test="${icaDatosAux.cambios['primerApellido'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Segundo Apellido</td>
												<td>${icaDatosAux.personaFisicaIMSS.segundoApellido}</td>
												<td>${icaDatosAux.personaFisicaEE.segundoApellido}</td>
												<td><c:if
														test="${icaDatosAux.cambios['segundoApellido'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">CURP</td>
												<td>${icaDatosAux.personaFisicaIMSS.curp}</td>
												<td>${icaDatosAux.personaFisicaEE.curp}</td>
												<td><c:if test="${icaDatosAux.cambios['curp'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Sexo</td>
												<td>${icaDatosAux.personaFisicaIMSS.sexo.descripcion}</td>
												<td>${icaDatosAux.personaFisicaEE.sexo.descripcion}</td>
												<td><c:if test="${icaDatosAux.cambios['sexo'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Fecha Nacimiento</td>
												<td>${icaDatosAux.personaFisicaIMSS.fechaNacimientoFormateada}</td>
												<td>${icaDatosAux.personaFisicaEE.fechaNacimientoFormateada}</td>
												<td><c:if
														test="${icaDatosAux.cambios['fechaNacimiento'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Entidad de Nacimiento</td>
												<td>${icaDatosAux.personaFisicaIMSS.lugarNacimiento.nombre}</td>
												<td>${icaDatosAux.personaFisicaEE.lugarNacimiento.nombre}</td>
												<td><c:if
														test="${icaDatosAux.cambios['lugarNacimiento'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Nacionalidad</td>
												<td>${icaDatosAux.personaFisicaIMSS.pais.nacionalidad}</td>
												<td>${icaDatosAux.personaFisicaEE.pais.nacionalidad}</td>
												<td><c:if
														test="${icaDatosAux.cambios['nacionalidad'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table cellpadding="0" cellspacing="0" class="tblICA">
											<c:choose>
												<c:when
													test="${not empty icaDatosAux.personaFisicaEE.actaNacimiento}">
													<tr>
														<td colspan="4" style="text-align: center;"><b>ACTA
																DE NACIMIENTO</b></td>
													</tr>
													<tr>
														<td style="width: 15%;">&nbsp;</td>
														<td style="text-align: center; width: 40%;"><b>DATOS
																ACTUALES</b></td>
														<td style="text-align: center; width: 40%;"><b>DATOS
																ENTIDAD EXTERNA</b></td>
														<td style="width: 5%;">&nbsp;</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">A&ntilde;o de Registro</td>
														<td>${icaDatosAux.personaFisicaIMSS.actaNacimiento.anio}</td>
														<td>${icaDatosAux.personaFisicaEE.actaNacimiento.anio}</td>
														<td><c:if
																test="${icaDatosAux.cambios['actaNacimiento.anio'].id ne 4}">
																<span class="label label-danger"
																	style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if></td>
													</tr>
													<tr>
														<td style="font-weight: bold;">No. Acta</td>
														<td>${icaDatosAux.personaFisicaIMSS.actaNacimiento.noActa}</td>
														<td>${icaDatosAux.personaFisicaEE.actaNacimiento.noActa}</td>
														<td><c:if
																test="${icaDatosAux.cambios['actaNacimiento.acta'].id ne 4}">
																<span class="label label-danger"
																	style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if></td>
													</tr>
													<tr>
														<td style="font-weight: bold;">No. Foja</td>
														<td>${icaDatosAux.personaFisicaIMSS.actaNacimiento.noFoja}</td>
														<td>${icaDatosAux.personaFisicaEE.actaNacimiento.noFoja}</td>
														<td><c:if
																test="${icaDatosAux.cambios['actaNacimiento.foja'].id ne 4}">
																<span class="label label-danger"
																	style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if></td>
													</tr>
													<tr>
														<td style="font-weight: bold;">No. Libro</td>
														<td>${icaDatosAux.personaFisicaIMSS.actaNacimiento.noLibro}</td>
														<td>${icaDatosAux.personaFisicaEE.actaNacimiento.noLibro}</td>
														<td><c:if
																test="${icaDatosAux.cambios['actaNacimiento.libro'].id ne 4}">
																<span class="label label-danger"
																	style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if></td>
													</tr>
													<tr>
														<td style="font-weight: bold;">Tomo</td>
														<td>${icaDatosAux.personaFisicaIMSS.actaNacimiento.tomo}</td>
														<td>${icaDatosAux.personaFisicaEE.actaNacimiento.tomo}</td>
														<td><c:if
																test="${icaDatosAux.cambios['actaNacimiento.tomo'].id ne 4}">
																<span class="label label-danger"
																	style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if></td>
													</tr>
													<tr>
														<td style="font-weight: bold;">CRIP</td>
														<td>${icaDatosAux.personaFisicaIMSS.actaNacimiento.crip}</td>
														<td>${icaDatosAux.personaFisicaEE.actaNacimiento.crip}</td>
														<td><c:if
																test="${icaDatosAux.cambios['actaNacimiento.crip'].id ne 4}">
																<span class="label label-danger"
																	style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if></td>
													</tr>
													<tr>
														<td style="font-weight: bold;">Entidad de Registro</td>
														<td>${icaDatosAux.personaFisicaIMSS.actaNacimiento.municipio.entidadFederativa.nombre}</td>
														<td>${icaDatosAux.personaFisicaEE.actaNacimiento.municipio.entidadFederativa.nombre}</td>
														<td><c:if
																test="${icaDatosAux.cambios['actaNacimiento.entidad'].id ne 4}">
																<span class="label label-danger"
																	style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if></td>
													</tr>
													<tr>
														<td style="font-weight: bold;">Municipio de Registro</td>
														<td>${icaDatosAux.personaFisicaIMSS.actaNacimiento.municipio.nombre}</td>
														<td>${icaDatosAux.personaFisicaEE.actaNacimiento.municipio.nombre}</td>
														<td><c:if
																test="${icaDatosAux.cambios['actaNacimiento.municipio'].id ne 4}">
																<span class="label label-danger"
																	style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if></td>
													</tr>
												</c:when>
												<c:when
													test="${not empty icaDatosAux.personaFisicaEE.documentoMigratorio}">
													<tr>
														<td colspan="4" style="text-align: center;"><b>DOCUMENTO
																MIGRATORIO</b></td>
													</tr>
													<tr>
														<td style="width: 15%;">&nbsp;</td>
														<td style="text-align: center; width: 40%;"><b>DATOS
																ACTUALES</b></td>
														<td style="text-align: center; width: 40%;"><b>DATOS
																ENTIDAD EXTERNA</b></td>
														<td style="width: 5%;">&nbsp;</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">N&uacute;mero del
															Registro Nacional de Extranjeros</td>
														<td>${icaDatosAux.personaFisicaIMSS.documentoMigratorio.numFolioExtranjero}</td>
														<td>${icaDatosAux.personaFisicaEE.documentoMigratorio.numFolioExtranjero}</td>
														<td><c:if
																test="${icaDatosAux.cambios['documentoMigratorio.numRegExtranjeros'].id ne 4}">
																<span class="label label-danger"
																	style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if></td>
													</tr>
													<tr>
														<td style="font-weight: bold;">N&uacute;mero de
															Expediente del Documento Migratorio</td>
														<td>${icaDatosAux.personaFisicaIMSS.documentoMigratorio.noActa}</td>
														<td>${icaDatosAux.personaFisicaEE.documentoMigratorio.noActa}</td>
														<td><c:if
																test="${icaDatosAux.cambios['documentoMigratorio.numExpediente'].id ne 4}">
																<span class="label label-danger"
																	style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if></td>
													</tr>
												</c:when>
												<c:when
													test="${not empty icaDatosAux.personaFisicaEE.cartaNaturalizacion}">
													<tr>
														<td colspan="4" style="text-align: center;"><b>CARTA
																DE NATURALIZACI&Oacute;N</b></td>
													</tr>
													<tr>
														<td style="width: 15%;">&nbsp;</td>
														<td style="text-align: center; width: 40%;"><b>DATOS
																ACTUALES</b></td>
														<td style="text-align: center; width: 40%;"><b>DATOS
																ENTIDAD EXTERNA</b></td>
														<td style="width: 5%;">&nbsp;</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">A&ntilde;o de Registro</td>
														<td>${icaDatosAux.personaFisicaIMSS.cartaNaturalizacion.anioRegistro}</td>
														<td>${icaDatosAux.personaFisicaEE.cartaNaturalizacion.anioRegistro}</td>
														<td><c:if
																test="${icaDatosAux.cambios['cartaNaturalizacion.anio'].id ne 4}">
																<span class="label label-danger"
																	style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if></td>
													</tr>
													<tr>
														<td style="font-weight: bold;">Folio de la Carta</td>
														<td>${icaDatosAux.personaFisicaIMSS.cartaNaturalizacion.numFolioExtranjero}</td>
														<td>${icaDatosAux.personaFisicaEE.cartaNaturalizacion.numFolioExtranjero}</td>

														<td><c:if
																test="${icaDatosAux.cambios['cartaNaturalizacion.folio'].id ne 4}">
																<span class="label label-danger"
																	style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if></td>
													</tr>
												</c:when>
												<c:when
													test="${not empty icaDatosAux.personaFisicaEE.numeroUnicoExtranjero}">
													<tr>
														<td colspan="4" style="text-align: center;"><b>N&Uacute;MERO
																&Uacute;NICO DE EXTRANJERO</b></td>
													</tr>
													<tr>
														<td style="width: 15%;">&nbsp;</td>
														<td style="text-align: center; width: 40%;"><b>DATOS
																ACTUALES</b></td>
														<td style="text-align: center; width: 40%;"><b>DATOS
																ENTIDAD EXTERNA</b></td>
														<td style="width: 5%;">&nbsp;</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">N&uacute;mero de folio</td>
														<td>${icaDatosAux.personaFisicaIMSS.numeroUnicoExtranjero.numFolioExtranjero}</td>
														<td>${icaDatosAux.personaFisicaEE.numeroUnicoExtranjero.numFolioExtranjero}</td>
														<td><c:if
																test="${icaDatosAux.cambios['numUnicoExtranjero.folio'].id ne 4}">
																<span class="label label-danger"
																	style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if></td>
													</tr>
												</c:when>
												<c:when
													test="${not empty icaDatosAux.personaFisicaEE.certificadoNacionalidadMexicana}">
													<tr>
														<td colspan="4" style="text-align: center;"><b>CERTIFICADO
																DE NACIONALIDAD MEXICANA</b></td>
													</tr>
													<tr>
														<td style="width: 15%;">&nbsp;</td>
														<td style="text-align: center; width: 40%;"><b>DATOS
																ACTUALES</b></td>
														<td style="text-align: center; width: 40%;"><b>DATOS
																ENTIDAD EXTERNA</b></td>
														<td style="width: 5%;">&nbsp;</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">A&ntilde;o de Registro</td>
														<td>${icaDatosAux.personaFisicaIMSS.certificadoNacionalidadMexicana.anioRegistro}</td>
														<td>${icaDatosAux.personaFisicaEE.certificadoNacionalidadMexicana.anioRegistro}</td>
														<td><c:if
																test="${icaDatosAux.cambios['certificadoNacionalidad.anio'].id ne 4}">
																<span class="label label-danger"
																	style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if></td>
													</tr>
													<tr>
														<td style="font-weight: bold;">Folio de la carta</td>
														<td>${icaDatosAux.personaFisicaIMSS.certificadoNacionalidadMexicana.numFolioExtranjero}</td>
														<td>${icaDatosAux.personaFisicaEE.certificadoNacionalidadMexicana.numFolioExtranjero}</td>
														<td><c:if
																test="${icaDatosAux.cambios['certificadoNacionalidad.folio'].id ne 4}">
																<span class="label label-danger"
																	style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if></td>
													</tr>
												</c:when>
												<c:when
													test="${not empty icaDatosAux.personaFisicaEE.oficioSolicitanteRefugiado}">
													<tr>
														<td colspan="4" style="text-align: center;"><b>OFICIO
																SOLICITANTE DE REFUGIADO</b></td>
													</tr>
													<tr>
														<td style="width: 15%;">&nbsp;</td>
														<td style="text-align: center; width: 40%;"><b>DATOS
																ACTUALES</b></td>
														<td style="text-align: center; width: 40%;"><b>DATOS
																ENTIDAD EXTERNA</b></td>
														<td style="width: 5%;">&nbsp;</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">N&uacute;mero de Folio</td>
														<td>${icaDatosAux.personaFisicaIMSS.oficioSolicitanteRefugiado.numFolioExtranjero}</td>
														<td>${icaDatosAux.personaFisicaEE.oficioSolicitanteRefugiado.numFolioExtranjero}</td>
														<td><c:if
																test="${icaDatosAux.cambios['oficioRefugiado.folio'].id ne 4}">
																<span class="label label-danger"
																	style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if></td>
													</tr>
												</c:when>
												<c:when
													test="${not empty icaDatosAux.personaFisicaEE.formaMigratoriaTurista}">
													<tr>
														<td colspan="4" style="text-align: center;"><b>FORMA
																MIGRATORIA TURISTA</b></td>
													</tr>
													<tr>
														<td style="width: 15%;">&nbsp;</td>
														<td style="text-align: center; width: 40%;"><b>DATOS
																ACTUALES</b></td>
														<td style="text-align: center; width: 40%;"><b>DATOS
																ENTIDAD EXTERNA</b></td>
														<td style="width: 5%;">&nbsp;</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">N&uacute;mero de Folio</td>
														<td>${icaDatosAux.personaFisicaIMSS.formaMigratoriaTurista.numFolioExtranjero}</td>
														<td>${icaDatosAux.personaFisicaEE.formaMigratoriaTurista.numFolioExtranjero}</td>
														<td><c:if
																test="${icaDatosAux.cambios['formaMigratoria.folio'].id ne 4}">
																<span class="label label-danger"
																	style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if></td>
													</tr>
												</c:when>
												<c:otherwise>
													<tr>
														<td colspan="4" style="text-align: center;"><b>SIN
																DOCUMENTO PROBATORIO</b></td>
													</tr>
												</c:otherwise>
											</c:choose>
										</table>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				<!-- acordeon -->
			</c:if>
			<br />
			<br />
		</form:form>

	</div>

	<div class="pie">
		<div class="opciones">
			<c:if test="${empty icaDatosAux.errorFormGeneral}">
				<div class="btn-group">
					<a href="#" class="btn btn-primary">Acciones</a> <a href="#"
						data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><span
						class="caret"></span></a>
					<ul class="dropdown-menu">
						<!-- <li><a id="finalizarTramite"><i class="glyphicon glyphicon-ok"></i> Finalizar Tr&aacute;mite</a></li> -->
						<li><a id="guardarTramite"><i class="glyphicon glyphicon-download-alt"></i> Guardar Tr&aacute;mite</a></li>
						<li><a id="guardarCerrarTramite"><i class="glyphicon glyphicon-remove"></i> Guardar y Cerrar Tr&aacute;mite</a></li>
						<li><a id="cancelarTramite"><i class="glyphicon glyphicon-trash"></i> Cancelar Tr&aacute;mite</a></li>
					</ul>
				</div>
			</c:if>
			<button class="btn btn-default" id="cerrarWizard">CERRAR</button>
		</div>
		<div class="controles">
			
		</div>

	</div>
</div>

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			¿Desea cancelar la solicitud pendiente con folio: <strong>${folioSolicitud}</strong>?
	</p>
</div>

<div id="dialog-confirm" title="Mensaje">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			<label id="mensajeDialogo"></label>
	</p>
</div>