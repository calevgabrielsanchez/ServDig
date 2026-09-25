<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/fisica/actualizacion-datos/contenido.js" htmlEscape="true" />"></script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="idTipoTramite" value="<%=TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES.getCodigo()%>" />	
<c:set var="origenINTERNET" value="<%=OrigenSolicitudEnum.INTERNET.getId()%>" />
<c:set var="origenVENTANILLA" value="<%=OrigenSolicitudEnum.VENTANILLA.getId()%>" />	
	
<style>
	.dataTables_wrapper table thead {
		display: none;
	}
		
	.contenedor .pie .opciones {
	    float: left;
	    width: 60%;
	}
	
	.contenedor .pie .controles {
	    float: right;
	    width: 40%;
	}
</style>

<script>
	var nuevosDatosPersonales;
	var idTipoTramite = ${idTipoTramite};
	
	if(parent.WizardActualizacionDatosCtrl.config.idOrigen == 2){
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
	}
	
</script>

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12">
		<form:form modelAttribute="icaDatosAux" id="forma" method="post">
			
			<c:if test="${not empty icaDatosAux.errorFormGeneral &&  fn:contains(icaDatosAux.errorFormGeneral , 'No existen diferencias')}">
				<div class="alert alert-info">
					<button type="button" class="close" data-dismiss="alert">×</button>
					${icaDatosAux.errorFormGeneral}
				</div>
			</c:if>

			<c:if test="${not empty icaDatosAux.errorFormGeneral &&  !fn:contains(icaDatosAux.errorFormGeneral , 'No existen diferencias')}">
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
				<input type="hidden" id="tramiteId" value="${tramiteId}"/>
				<input type="hidden" id="isDerechohabiente" value="${isDerechohabiente?1:0}"/>
				
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
					informaci&oacute;n registrada en el Instituto y las entidades
					externas.
				</div>

				<div id="acordeon">

					<h3>
						<a href='#'>Datos confirmados por RENAPO</a>
					</h3>
					<div id="datosConfirmadosRenapo">
						<c:if test="${not empty icaDatosAux.traza['ESTATUS_CURP']}">
							<div class="alert alert-info" id="estatusCURPDiv" style="font-size: small;">
								${icaDatosAux.traza['ESTATUS_CURP']}</div>
						</c:if>
						<table class="table" id="tabla-imss-renapo">
							<thead>
								<tr>
									<th><b>Datos confirmados por RENAPO</b></th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>
										<table cellpadding="0" cellspacing="0" class="tblICA table-striped">
											<tr>
												<td colspan="4" style="text-align: center;"><b>Datos b&aacute;sicos</b></td>
											</tr>
											<tr>
												<td style="width: 15%;">&nbsp;</td>
												<td style="text-align: center; width: 40%;"><b>Datos
														actuales</b></td>
												<td style="text-align: center; width: 40%;"><b>Datos
														entidad externa</b></td>
												<td style="width: 5%;">&nbsp;</td>
											</tr>
											<tr>
												<td style="font-weight: bold;">CURP:</td>
												<td>${icaDatosAux.personaFisicaIMSS.curp}</td>
												<td>${icaDatosAux.personaFisicaEE.curp}</td>
												<td><c:if test="${icaDatosAux.cambios['curp'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Nombre(s):</td>
												<td>${icaDatosAux.personaFisicaIMSS.nombre}</td>
												<td>${icaDatosAux.personaFisicaEE.nombre}</td>
												<td><c:if
														test="${icaDatosAux.cambios['nombre'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Primer apellido:</td>
												<td>${icaDatosAux.personaFisicaIMSS.primerApellido}</td>
												<td>${icaDatosAux.personaFisicaEE.primerApellido}</td>
												<td><c:if
														test="${icaDatosAux.cambios['primerApellido'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Segundo apellido:</td>
												<td>${icaDatosAux.personaFisicaIMSS.segundoApellido}</td>
												<td>${icaDatosAux.personaFisicaEE.segundoApellido}</td>
												<td><c:if
														test="${icaDatosAux.cambios['segundoApellido'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Sexo:</td>
												<td>${icaDatosAux.personaFisicaIMSS.sexo.descripcion}</td>
												<td>${icaDatosAux.personaFisicaEE.sexo.descripcion}</td>
												<td><c:if test="${icaDatosAux.cambios['sexo'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Fecha de nacimiento:</td>
												<td>${icaDatosAux.personaFisicaIMSS.fechaNacimientoFormateada}</td>
												<td>${icaDatosAux.personaFisicaEE.fechaNacimientoFormateada}</td>
												<td><c:if
														test="${icaDatosAux.cambios['fechaNacimiento'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Lugar de nacimiento:</td>
												<td>${icaDatosAux.personaFisicaIMSS.lugarNacimiento.nombre}</td>
												<td>${icaDatosAux.personaFisicaEE.lugarNacimiento.nombre}</td>
												<td><c:if
														test="${icaDatosAux.cambios['lugarNacimiento'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Nacionalidad:</td>
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
										<table cellpadding="0" cellspacing="0" class="tblICA table-striped">
											<c:choose>
												<c:when
													test="${not empty icaDatosAux.personaFisicaEE.actaNacimiento}">
													<tr>
														<td colspan="4" style="text-align: center;"><b>Acta
																de nacimiento</b></td>
													</tr>
													<tr>
														<td style="width: 15%;">&nbsp;</td>
														<td style="text-align: center; width: 40%;"><b>Datos
																actuales</b></td>
														<td style="text-align: center; width: 40%;"><b>Datos
																entidad externa</b></td>
														<td style="width: 5%;">&nbsp;</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">A&ntilde;o de registro:</td>
														<td>${icaDatosAux.personaFisicaIMSS.actaNacimiento.anio}</td>
														<td>${icaDatosAux.personaFisicaEE.actaNacimiento.anio}</td>
														<td><c:if
																test="${icaDatosAux.cambios['actaNacimiento.anio'].id ne 4}">
																<span class="label label-danger"
																	style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if></td>
													</tr>
													<tr>
														<td style="font-weight: bold;">No. acta:</td>
														<td>${icaDatosAux.personaFisicaIMSS.actaNacimiento.noActa}</td>
														<td>${icaDatosAux.personaFisicaEE.actaNacimiento.noActa}</td>
														<td><c:if
																test="${icaDatosAux.cambios['actaNacimiento.acta'].id ne 4}">
																<span class="label label-danger"
																	style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if></td>
													</tr>
													<tr>
														<td style="font-weight: bold;">No. Foja:</td>
														<td>${icaDatosAux.personaFisicaIMSS.actaNacimiento.noFoja}</td>
														<td>${icaDatosAux.personaFisicaEE.actaNacimiento.noFoja}</td>
														<td><c:if
																test="${icaDatosAux.cambios['actaNacimiento.foja'].id ne 4}">
																<span class="label label-danger"
																	style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if></td>
													</tr>
													<tr>
														<td style="font-weight: bold;">No. Libro:</td>
														<td>${icaDatosAux.personaFisicaIMSS.actaNacimiento.noLibro}</td>
														<td>${icaDatosAux.personaFisicaEE.actaNacimiento.noLibro}</td>
														<td><c:if
																test="${icaDatosAux.cambios['actaNacimiento.libro'].id ne 4}">
																<span class="label label-danger"
																	style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if></td>
													</tr>
													<tr>
														<td style="font-weight: bold;">Tomo:</td>
														<td>${icaDatosAux.personaFisicaIMSS.actaNacimiento.tomo}</td>
														<td>${icaDatosAux.personaFisicaEE.actaNacimiento.tomo}</td>
														<td><c:if
																test="${icaDatosAux.cambios['actaNacimiento.tomo'].id ne 4}">
																<span class="label label-danger"
																	style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if></td>
													</tr>
													<tr>
														<td style="font-weight: bold;">CRIP:</td>
														<td>${icaDatosAux.personaFisicaIMSS.actaNacimiento.crip}</td>
														<td>${icaDatosAux.personaFisicaEE.actaNacimiento.crip}</td>
														<td><c:if
																test="${icaDatosAux.cambios['actaNacimiento.crip'].id ne 4}">
																<span class="label label-danger"
																	style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if></td>
													</tr>
													<tr>
														<td style="font-weight: bold;">Entidad de registro:</td>
														<td>${icaDatosAux.personaFisicaIMSS.actaNacimiento.municipio.entidadFederativa.nombre}</td>
														<td>${icaDatosAux.personaFisicaEE.actaNacimiento.municipio.entidadFederativa.nombre}</td>
														<td><c:if
																test="${icaDatosAux.cambios['actaNacimiento.entidad'].id ne 4}">
																<span class="label label-danger"
																	style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if></td>
													</tr>
													<tr>
														<td style="font-weight: bold;">Municipio de registro:</td>
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
														<td colspan="4" style="text-align: center;"><b>Documento
																migratorio</b></td>
													</tr>
													<tr>
														<td style="width: 15%;">&nbsp;</td>
														<td style="text-align: center; width: 40%;"><b>Datos
																actuales</b></td>
														<td style="text-align: center; width: 40%;"><b>Datos
																entidad externa</b></td>
														<td style="width: 5%;">&nbsp;</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">N&uacute;mero del
															registro nacional de extranjeros:</td>
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
															expediente del documento migratorio:</td>
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
														<td colspan="4" style="text-align: center;"><b>Carta
																de naturalizaci&oacute;n</b></td>
													</tr>
													<tr>
														<td style="width: 15%;">&nbsp;</td>
														<td style="text-align: center; width: 40%;"><b>Datos
																actuales</b></td>
														<td style="text-align: center; width: 40%;"><b>Datos
																entidad externa</b></td>
														<td style="width: 5%;">&nbsp;</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">A&ntilde;o de registro:</td>
														<td>${icaDatosAux.personaFisicaIMSS.cartaNaturalizacion.anioRegistro}</td>
														<td>${icaDatosAux.personaFisicaEE.cartaNaturalizacion.anioRegistro}</td>
														<td><c:if
																test="${icaDatosAux.cambios['cartaNaturalizacion.anio'].id ne 4}">
																<span class="label label-danger"
																	style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if></td>
													</tr>
													<tr>
														<td style="font-weight: bold;">Folio de la carta:</td>
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
														<td colspan="4" style="text-align: center;"><b>N&uacute;mero
																&uacute;nico de extranjero</b></td>
													</tr>
													<tr>
														<td style="width: 15%;">&nbsp;</td>
														<td style="text-align: center; width: 40%;"><b>Datos
																actuales</b></td>
														<td style="text-align: center; width: 40%;"><b>Datos
																entidad externa</b></td>
														<td style="width: 5%;">&nbsp;</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">N&uacute;mero de folio:</td>
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
														<td colspan="4" style="text-align: center;"><b>Certificado
																de nacionalidad mexicana</b></td>
													</tr>
													<tr>
														<td style="width: 15%;">&nbsp;</td>
														<td style="text-align: center; width: 40%;"><b>Datos
																actuales</b></td>
														<td style="text-align: center; width: 40%;"><b>Datos
																entidad externa</b></td>
														<td style="width: 5%;">&nbsp;</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">A&ntilde;o de registro:</td>
														<td>${icaDatosAux.personaFisicaIMSS.certificadoNacionalidadMexicana.anioRegistro}</td>
														<td>${icaDatosAux.personaFisicaEE.certificadoNacionalidadMexicana.anioRegistro}</td>
														<td><c:if
																test="${icaDatosAux.cambios['certificadoNacionalidad.anio'].id ne 4}">
																<span class="label label-danger"
																	style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if></td>
													</tr>
													<tr>
														<td style="font-weight: bold;">Folio de la carta:</td>
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
														<td colspan="4" style="text-align: center;"><b>Oficio
																solicitante de refugiado</b></td>
													</tr>
													<tr>
														<td style="width: 15%;">&nbsp;</td>
														<td style="text-align: center; width: 40%;"><b>Datos
																actuales</b></td>
														<td style="text-align: center; width: 40%;"><b>Datos
																entidad externa</b></td>
														<td style="width: 5%;">&nbsp;</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">N&uacute;mero de folio:</td>
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
														<td colspan="4" style="text-align: center;"><b>Forma
																migratoria turista</b></td>
													</tr>
													<tr>
														<td style="width: 15%;">&nbsp;</td>
														<td style="text-align: center; width: 40%;"><b>Datos
																actuales</b></td>
														<td style="text-align: center; width: 40%;"><b>Datos
																entidad externa</b></td>
														<td style="width: 5%;">&nbsp;</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">N&uacute;mero de folio:</td>
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
														<td colspan="4" style="text-align: center;"><b>Sin
																documento probatorio</b></td>
													</tr>
												</c:otherwise>
											</c:choose>
										</table>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<c:if test="${icaDatosAux.indicadorConsultaSAT}">
					<h3>
						<a href='#'>Datos confirmados por SAT</a>
					</h3>
					<div id="datosConfirmadosSat">

						<table class="table" id="tabla-imss-sat">
							<thead>
								<tr>
									<th><b>Datos confirmados por SAT</b></th>
								</tr>
							</thead>

							<tbody>
								<tr>
									<td>
										<table cellpadding="0" cellspacing="0" class="tblICA table-striped">
											<tr>
												<td colspan="4" style="text-align: center;"><b>Datos
														b&aacute;sicos</b></td>
											</tr>
											<tr>
												<td style="width: 15%;">&nbsp;</td>
												<td style="text-align: center; width: 40%;"><b>Datos
														IMSS</b></td>
												<td style="text-align: center; width: 40%;"><b>Datos
														entidad externa</b></td>
												<td style="width: 5%;">&nbsp;</td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Nombre(s):</td>
												<td>${icaDatosAux.personaFisicaIMSS.nombre}</td>
												<td>${icaDatosAux.personaFisicaEE.nombre}</td>
												<td><c:if
														test="${icaDatosAux.cambios['nombre'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Primer apellido:</td>
												<td>${icaDatosAux.personaFisicaIMSS.primerApellido}</td>
												<td>${icaDatosAux.personaFisicaEE.primerApellido}</td>
												<td><c:if
														test="${icaDatosAux.cambios['primerApellido'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Segundo apellido:</td>
												<td>${icaDatosAux.personaFisicaIMSS.segundoApellido}</td>
												<td>${icaDatosAux.personaFisicaEE.segundoApellido}</td>
												<td><c:if
														test="${icaDatosAux.cambios['segundoApellido'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">RFC:</td>
												<td>${icaDatosAux.personaFisicaIMSS.rfc}</td>
												<td>${icaDatosAux.personaFisicaEE.rfc}</td>
												<td><c:if test="${icaDatosAux.cambios['rfc'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Situaci&oacute;n:</td>
												<td>${icaDatosAux.personaFisicaIMSS.situacionesSAT[0].descripcion}</td>
												<td>${icaDatosAux.personaFisicaEE.situacionesSAT[0].descripcion}</td>
												<td><c:if
														test="${icaDatosAux.cambios['situacionSAT'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Fecha de
													inicio de operaciones:</td>
												<td><fmt:formatDate value="${icaDatosAux.personaFisicaIMSS.datosPersonaSAT.fechaInicioOperaciones}" pattern="dd/MM/yyyy"/></td>
												<td><fmt:formatDate value="${icaDatosAux.personaFisicaEE.datosPersonaSAT.fechaInicioOperaciones}" pattern="dd/MM/yyyy"/></td>
												<td><c:if
														test="${icaDatosAux.cambios['fechaInicioOperaciones'].id ne 4}">
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
												<td colspan="4" style="text-align: center;"><b>Domicilio
														fiscal</b></td>
											</tr>
											<tr>
												<td style="width: 15%;">&nbsp;</td>
												<td style="text-align: center; width: 40%;"><b>Datos
														IMSS</b></td>
												<td style="text-align: center; width: 40%;"><b>Datos
														entidad externa</b></td>
												<td style="width: 5%;">&nbsp;</td>
											</tr>
											<tr>
												<td style="font-weight: bold;">C&oacute;digo postal:</td>
												<td>${icaDatosAux.personaFisicaIMSS.domicilioFiscal.codigoPostal.codigoPostal}</td>
												<td>${icaDatosAux.personaFisicaEE.domicilioFiscal.codigoPostal.codigoPostal}</td>
												<td><c:if
														test="${icaDatosAux.cambios['codigoPostal'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Calle:</td>
												<td>${icaDatosAux.personaFisicaIMSS.domicilioFiscal.calle}</td>
												<td>${icaDatosAux.personaFisicaEE.domicilioFiscal.calle}</td>
												<td><c:if
														test="${icaDatosAux.cambios['calle'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Colonia:</td>
												<td>${icaDatosAux.personaFisicaIMSS.domicilioFiscal.colonia}</td>
												<td>${icaDatosAux.personaFisicaEE.domicilioFiscal.colonia}</td>
												<td><c:if
														test="${icaDatosAux.cambios['colonia'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Estado:</td>
												<td>${icaDatosAux.personaFisicaIMSS.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre}</td>
												<td>${icaDatosAux.personaFisicaEE.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre}</td>
												<td><c:if
														test="${icaDatosAux.cambios['entidad'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Localidad:</td>
												<td>${icaDatosAux.personaFisicaIMSS.domicilioFiscal.asentamiento.localidad.nombre}</td>
												<td>${icaDatosAux.personaFisicaEE.domicilioFiscal.asentamiento.localidad.nombre}</td>
												<td><c:if
														test="${icaDatosAux.cambios['localidad'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Municipio o alcald&iacute;a:</td>
												<td>${icaDatosAux.personaFisicaIMSS.domicilioFiscal.asentamiento.localidad.municipio.nombre}</td>
												<td>${icaDatosAux.personaFisicaEE.domicilioFiscal.asentamiento.localidad.municipio.nombre}</td>
												<td><c:if
														test="${icaDatosAux.cambios['municipio'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Entre calle 1:</td>
												<td>${icaDatosAux.personaFisicaIMSS.domicilioFiscal.vialidadReferenciaPrimaria.nombre}</td>
												<td>${icaDatosAux.personaFisicaEE.domicilioFiscal.vialidadReferenciaPrimaria.nombre}</td>
												<td><c:if
														test="${icaDatosAux.cambios['entreCalle1'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Entre calle 2:</td>
												<td>${icaDatosAux.personaFisicaIMSS.domicilioFiscal.vialidadReferenciaSecundaria.nombre}</td>
												<td>${icaDatosAux.personaFisicaEE.domicilioFiscal.vialidadReferenciaSecundaria.nombre}</td>
												<td><c:if
														test="${icaDatosAux.cambios['entreCalle2'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Inmueble:</td>
												<td>${icaDatosAux.personaFisicaIMSS.domicilioFiscal.asentamiento.tipoAsentamiento.descripcion}</td>
												<td>${icaDatosAux.personaFisicaEE.domicilioFiscal.asentamiento.tipoAsentamiento.descripcion}</td>
												<td><c:if
														test="${icaDatosAux.cambios['inmueble'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Referencia:</td>
												<td>${icaDatosAux.personaFisicaIMSS.domicilioFiscal.descripcion}</td>
												<td>${icaDatosAux.personaFisicaEE.domicilioFiscal.descripcion}</td>
												<td><c:if
														test="${icaDatosAux.cambios['referencia'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Vialidad:</td>
												<td>${icaDatosAux.personaFisicaIMSS.domicilioFiscal.vialidadPrimaria.nombre}</td>
												<td>${icaDatosAux.personaFisicaEE.domicilioFiscal.vialidadPrimaria.nombre}</td>
												<td><c:if
														test="${icaDatosAux.cambios['vialidad'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">N&uacute;mero interior:</td>
												<td>${icaDatosAux.personaFisicaIMSS.domicilioFiscal.numInteriorAlf}</td>
												<td>${icaDatosAux.personaFisicaEE.domicilioFiscal.numInteriorAlf}</td>
												<td><c:if
														test="${icaDatosAux.cambios['numeInt'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">N&uacute;mero exterior:</td>
												<td>${icaDatosAux.personaFisicaIMSS.domicilioFiscal.numExteriorAlf}</td>
												<td>${icaDatosAux.personaFisicaEE.domicilioFiscal.numExteriorAlf}</td>
												<td><c:if
														test="${icaDatosAux.cambios['numeExt'].id ne 4}">
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
												<td colspan="4" style="text-align: center;"><b>Forma
														de contacto 1 (Tel&eacute;fono fijo)</b></td>
											</tr>
											<tr>
												<td style="width: 15%;">&nbsp;</td>
												<td style="text-align: center; width: 40%;"><b>Datos
														IMSS</b></td>
												<td style="text-align: center; width: 40%;"><b>Datos
														entidad externa</b></td>
												<td style="width: 5%;">&nbsp;</td>
											</tr>
											<tr>
												<td style="font-weight: bold;">N&uacute;mero:</td>
												<td>${icaDatosAux.personaFisicaIMSS.telefonoFijoFiscal.numero}</td>
												<td>${icaDatosAux.personaFisicaEE.telefonoFijoFiscal.numero}</td>
												<td><c:if
														test="${icaDatosAux.cambios['telefonoFijo.numero'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Clave lada:</td>
												<td>${icaDatosAux.personaFisicaIMSS.telefonoFijoFiscal.claveLada}</td>
												<td>${icaDatosAux.personaFisicaEE.telefonoFijoFiscal.claveLada}</td>
												<td><c:if
														test="${icaDatosAux.cambios['telefonoFijo.lada'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Extensi&oacute;n:</td>
												<td>${icaDatosAux.personaFisicaIMSS.telefonoFijoFiscal.extension}</td>
												<td>${icaDatosAux.personaFisicaEE.telefonoFijoFiscal.extension}</td>
												<td><c:if
														test="${icaDatosAux.cambios['telefonoFijo.extension'].id ne 4}">
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
												<td colspan="4" style="text-align: center;"><b>Forma
														de contacto 2 (Tel&eacute;fono m&oacute;vil)</b></td>
											</tr>
											<tr>
												<td style="width: 15%;">&nbsp;</td>
												<td style="text-align: center; width: 40%;"><b>Datos
														IMSS</b></td>
												<td style="text-align: center; width: 40%;"><b>Datos
														entidad externa</b></td>
												<td style="width: 5%;">&nbsp;</td>
											</tr>
											<tr>
												<td style="font-weight: bold;">N&uacute;mero</td>
												<td>${icaDatosAux.personaFisicaIMSS.telefonoMovilFiscal.numero}</td>
												<td>${icaDatosAux.personaFisicaEE.telefonoMovilFiscal.numero}</td>
												<td><c:if
														test="${icaDatosAux.cambios['telefonoMovil'].id ne 4}">
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
												<td colspan="4" style="text-align: center;"><b>Forma
														de contacto 3 (Correo electr&oacute;nico)</b></td>
											</tr>
											<tr>
												<td style="width: 15%;">&nbsp;</td>
												<td style="text-align: center; width: 40%;"><b>Datos
														IMSS</b></td>
												<td style="text-align: center; width: 40%;"><b>Datos
														entidad externa</b></td>
												<td style="width: 5%;">&nbsp;</td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Correo:</td>
												<td>${icaDatosAux.personaFisicaIMSS.correoElectronicoFiscal.correo}</td>
												<td>${icaDatosAux.personaFisicaEE.correoElectronicoFiscal.correo}</td>
												<td><c:if
														test="${icaDatosAux.cambios['correoElectronico'].id ne 4}">
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
				<c:if test="${isDerechohabiente}">
					<div class="separadorseccion">
						<span>
							Documentos probatorios
						</span>
					</div>
					<div class="alert alert-warning">
							<span class="required">*</span> Este tr&aacute;mite requiere la captura de documentos probatorios, de clic <a id="capturarDocumentos"><i class="glyphicon glyphicon-file"></i> AQUI </a> para proceder a la misma, no podr&aacute; 
							finalizar el tr&aacute;mite hasta completarla.<br><br>
					</div>
				</c:if>
				<!-- acordeon -->
			</c:if>
		</form:form>
		<form action="" method="post" id="soForm"></form>
		</div>
	</div>

	<div class="pie row">
		<div class="col-sm-12" style="text-align:right">
			<button class="btn btn-default" id="cerrarWizard">Cerrar</button>
			<c:if test="${empty icaDatosAux.errorFormGeneral}">
				<div class="btn-group dropup">
					<a href="#" class="btn btn-primary">Acciones</a> <a href="#"
						data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><span
						class="caret"></span></a>
					<ul class="dropdown-menu dropdown-menu-right">
						<c:if test="${origenApp eq origenINTERNET}">
							<li><a id="finalizarTramite"><i class="glyphicon glyphicon-ok"></i> Finalizar tr&aacute;mite</a></li>
						</c:if>
						<c:if test="${origenApp eq origenVENTANILLA}">
							<li><a id="finalizarTramiteVen"><i class="glyphicon glyphicon-ok"></i> Finalizar tr&aacute;mite</a></li>
						</c:if>
							<li><a id="guardarTramite"><i class="glyphicon glyphicon-download-alt"></i> Guardar tr&aacute;mite</a></li>
							<li><a id="cancelarTramite"><i class="glyphicon glyphicon-trash"></i> Cancelar tr&aacute;mite</a></li>
					</ul>
				</div>
			</c:if>
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