<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/personas/moral/identificar/cambios-automaticos/resultado-consultar-comparar.js" htmlEscape="true" />"></script>

<style type="text/css">
	.dataTables_wrapper table thead {
		display: none;
	}
</style>

<div class="col-sm-12">
	<div>
		<c:set var="contextpath" value="<%=request.getContextPath()%>" />
		
		<form:form modelAttribute="icaDatosRespuesta" id="forma"
			method="post"
			action="${contextpath}/persona/moral/identificar/cambios-automaticos/integrarCambiosICA">

			<c:if test="${not empty icaDatosRespuesta.errorFormGeneral && 
							fn:contains(icaDatosRespuesta.errorFormGeneral , 'No existen diferencias')}">
				<div class="alert alert-success">
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
					<h3>
						<a href='#'>DATOS CONFIRMADOS POR SAT</a>
					</h3>
					<div id="datosConfirmadosSat">

						<table class="table" id="tabla-imss-sat"
							style="font-size: xx-small;">
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
												<td style="font-weight: bold;">Nombre &oacute; Raz&oacute;n Social</td>
												<td>${icaDatosRespuesta.personaMoralIMSS.razonSocial}</td>
												<td>${icaDatosRespuesta.personaMoralEE.razonSocial}</td>
												<td><c:if
														test="${icaDatosRespuesta.cambios['nombreRazonSocial'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Fecha de Constituci&oacute;n</td>
												<td>${icaDatosRespuesta.personaMoralIMSS.fechaCreacionFormateada}</td>
												<td>${icaDatosRespuesta.personaMoralEE.fechaCreacionFormateada}</td>
												<td><c:if
														test="${icaDatosRespuesta.cambios['fechaConstitucion'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Tipo de Sociedad</td>
												<td>${icaDatosRespuesta.personaMoralIMSS.tipoSociedad.descripcionAbreviada}</td>
												<td>${icaDatosRespuesta.personaMoralEE.tipoSociedad.descripcionAbreviada}</td>
												<td><c:if
														test="${icaDatosRespuesta.cambios['tipoSociedad'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">RFC</td>
												<td>${icaDatosRespuesta.personaMoralIMSS.rfc}</td>
												<td>${icaDatosRespuesta.personaMoralEE.rfc}</td>
												<td><c:if
														test="${icaDatosRespuesta.cambios['rfc'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Situaci&oacute;n</td>
												<td>${icaDatosRespuesta.personaMoralIMSS.situacionesSAT[0].descripcion}</td>
												<td>${icaDatosRespuesta.personaMoralEE.situacionesSAT[0].descripcion}</td>
												<td><c:if
														test="${icaDatosRespuesta.cambios['situacionSAT'].id ne 4}">
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
												<td>${icaDatosRespuesta.personaMoralIMSS.domicilioFiscal.codigoPostal.codigoPostal}</td>
												<td>${icaDatosRespuesta.personaMoralEE.domicilioFiscal.codigoPostal.codigoPostal}</td>
												<td><c:if
														test="${icaDatosRespuesta.cambios['codigoPostal'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Calle</td>
												<td>${icaDatosRespuesta.personaMoralIMSS.domicilioFiscal.calle}</td>
												<td>${icaDatosRespuesta.personaMoralEE.domicilioFiscal.calle}</td>
												<td><c:if
														test="${icaDatosRespuesta.cambios['calle'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Colonia</td>
												<td>${icaDatosRespuesta.personaMoralIMSS.domicilioFiscal.colonia}</td>
												<td>${icaDatosRespuesta.personaMoralEE.domicilioFiscal.colonia}</td>
												<td><c:if
														test="${icaDatosRespuesta.cambios['colonia'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Entidad Federativa</td>
												<td>${icaDatosRespuesta.personaMoralIMSS.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre}</td>
												<td>${icaDatosRespuesta.personaMoralEE.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre}</td>
												<td><c:if
														test="${icaDatosRespuesta.cambios['entidad'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Localidad</td>
												<td>${icaDatosRespuesta.personaMoralIMSS.domicilioFiscal.asentamiento.localidad.nombre}</td>
												<td>${icaDatosRespuesta.personaMoralEE.domicilioFiscal.asentamiento.localidad.nombre}</td>
												<td><c:if
														test="${icaDatosRespuesta.cambios['localidad'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Municipio</td>
												<td>${icaDatosRespuesta.personaMoralIMSS.domicilioFiscal.asentamiento.localidad.municipio.nombre}</td>
												<td>${icaDatosRespuesta.personaMoralEE.domicilioFiscal.asentamiento.localidad.municipio.nombre}</td>
												<td><c:if
														test="${icaDatosRespuesta.cambios['municipio'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Entre Calle 1</td>
												<td>${icaDatosRespuesta.personaMoralIMSS.domicilioFiscal.vialidadReferenciaPrimaria.nombre}</td>
												<td>${icaDatosRespuesta.personaMoralEE.domicilioFiscal.vialidadReferenciaPrimaria.nombre}</td>
												<td><c:if
														test="${icaDatosRespuesta.cambios['entreCalle1'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Entre Calle 2</td>
												<td>${icaDatosRespuesta.personaMoralIMSS.domicilioFiscal.vialidadReferenciaSecundaria.nombre}</td>
												<td>${icaDatosRespuesta.personaMoralEE.domicilioFiscal.vialidadReferenciaSecundaria.nombre}</td>
												<td><c:if
														test="${icaDatosRespuesta.cambios['entreCalle2'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Inmueble</td>
												<td>${icaDatosRespuesta.personaMoralIMSS.domicilioFiscal.asentamiento.tipoAsentamiento.descripcion}</td>
												<td>${icaDatosRespuesta.personaMoralEE.domicilioFiscal.asentamiento.tipoAsentamiento.descripcion}</td>
												<td><c:if
														test="${icaDatosRespuesta.cambios['inmueble'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Referencia</td>
												<td>${icaDatosRespuesta.personaMoralIMSS.domicilioFiscal.descripcion}</td>
												<td>${icaDatosRespuesta.personaMoralEE.domicilioFiscal.descripcion}</td>
												<td><c:if
														test="${icaDatosRespuesta.cambios['referencia'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Vialidad</td>
												<td>${icaDatosRespuesta.personaMoralIMSS.domicilioFiscal.vialidadPrimaria.nombre}</td>
												<td>${icaDatosRespuesta.personaMoralEE.domicilioFiscal.vialidadPrimaria.nombre}</td>
												<td><c:if
														test="${icaDatosRespuesta.cambios['vialidad'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">N&uacute;mero Interior</td>
												<td>${icaDatosRespuesta.personaMoralIMSS.domicilioFiscal.numInteriorAlf}</td>
												<td>${icaDatosRespuesta.personaMoralEE.domicilioFiscal.numInteriorAlf}</td>
												<td><c:if
														test="${icaDatosRespuesta.cambios['numeInt'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">N&uacute;mero Exterior</td>
												<td>${icaDatosRespuesta.personaMoralIMSS.domicilioFiscal.numExteriorAlf}</td>
												<td>${icaDatosRespuesta.personaMoralEE.domicilioFiscal.numExteriorAlf}</td>
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
												<td>${icaDatosRespuesta.personaMoralIMSS.telefonoFijoFiscal.numero}</td>
												<td>${icaDatosRespuesta.personaMoralEE.telefonoFijoFiscal.numero}</td>
												<td><c:if
														test="${icaDatosRespuesta.cambios['telefonoFijo.numero'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Clave lada</td>
												<td>${icaDatosRespuesta.personaMoralIMSS.telefonoFijoFiscal.claveLada}</td>
												<td>${icaDatosRespuesta.personaMoralEE.telefonoFijoFiscal.claveLada}</td>
												<td><c:if
														test="${icaDatosRespuesta.cambios['telefonoFijo.lada'].id ne 4}">
														<span class="label label-danger"
															style="font-size: xx-small;" title="CAMBIO">*</span>
													</c:if></td>
											</tr>
											<tr>
												<td style="font-weight: bold;">Extensi&oacute;n</td>
												<td>${icaDatosRespuesta.personaMoralIMSS.telefonoFijoFiscal.extension}</td>
												<td>${icaDatosRespuesta.personaMoralEE.telefonoFijoFiscal.extension}</td>
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
												<td>${icaDatosRespuesta.personaMoralIMSS.telefonoMovilFiscal.numero}</td>
												<td>${icaDatosRespuesta.personaMoralEE.telefonoMovilFiscal.numero}</td>
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
												<td>${icaDatosRespuesta.personaMoralIMSS.correoElectronicoFiscal.correo}</td>
												<td>${icaDatosRespuesta.personaMoralEE.correoElectronicoFiscal.correo}</td>
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
						<br /> <br />
					</div>
				</div>
				<!-- acordeon -->
			</c:if>
			<div class="text-right m-t-lg">
				<button type="button" class="btn btn-default" id="btnCancelar">
					Cancelar</button>
				<button type="button" class="btn btn-primary"
					id="btnIntegrarCambios">Aceptar</button>
			</div>
		</form:form>
	</div>
</div>