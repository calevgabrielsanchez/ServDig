<%@ include file="../../../../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/common/commonMethods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/modificacion/patron/altaPatronal/comprobacionICAMoral.js" htmlEscape="true" />"></script>

<script>
	<c:if test="${idSolicitud != null}">
	var idSolicitud = ${idSolicitud};
	</c:if>
	<c:if test="${idSolicitud == null}">
	var idSolicitud = 0;
	</c:if>
</script>



<style type="text/css">
	.dataTables_wrapper table thead {
		display: none;
	}
</style>

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12">
			<div class="form-comment">

				<c:set var="contextpath" value="<%=request.getContextPath()%>" />
				<form:form modelAttribute="icaDatosAux" id="forma" method="post"
					action="${contextpath}/persona/moral/identificar/cambios-automaticos/integrarCambiosICA">

					<c:if test="${not empty icaDatosAux.errorFormGeneral && 
							fn:contains(icaDatosAux.errorFormGeneral , 'No existen diferencias')}">
						<div class="alert alert-success">
							<button type="button" class="close" data-dismiss="alert">&#215</button>
							${icaDatosAux.errorFormGeneral}
						</div>

						<input type="hidden" id="idSolicitud" value="${idSolicitud}" />
						<input type="hidden" id="folioSolicitud" value="${folioSolicitud}" />
						<input type="hidden" id="rfcPersona" value="${rfcPersona}" />
						<input type="hidden" id="contenidoFirmar" value="${contenidoFirmar}" />

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
					
					<c:if test="${not empty icaDatosAux.errorFormGeneral && 
									!fn:contains(icaDatosAux.errorFormGeneral , 'No existen diferencias')}">
							<c:if test="${!sitCont}">
								<div class="alert alert-danger">
									<button type="button" class="close" data-dismiss="alert">&#215</button>
									<strong>Importante: </strong>
									${icaDatosAux.errorFormGeneral}
								</div>
								<div class="pie">
									<div class="opciones">
										<button class="btn btn-primary" id="cerrarWizard">Cerrar</button>
									</div>
									<div class="controles"></div>
								</div>
							</c:if>
							<c:if test="${sitCont}">
								<div class="alert alert-danger">
									<c:if test="${sitContCod == 0 || sitContCod == 4 || sitContCod == 5}">
										<strong>Error: </strong>${icaDatosAux.errorFormGeneral}<br><br>
									</c:if>
									<c:if test="${sitContCod == 1 || sitContCod == 2 || sitContCod == 3}">
										<strong>Aviso</strong>: Su solicitud de alta patronal no puede ser atendida por este medio. <br><br>
										Puede registrar sus datos, obtener una cita y acudir a la subdelegaci&oacute;n que se le indique para verificar su documentaci&oacute;n. <br>
										D&eacute; <a href="http://www.imss.gob.mx/patrones/prealta-patronal " target="_blank">clic aqu&iacute; para obtener una cita.</a><br><br>
									</c:if>
								</div>
								<div class="pie">
									<br><br><br><br><br><br><br><br><br><br><br><br>
									<center>
										<div class="opciones">
											<button class="btn btn-primary" id="cerrarWizard">Cerrar</button>
										</div>
										<div class="controles"></div>
									</center>
								</div>
							</c:if>
					</c:if>

					<c:if test="${empty icaDatosAux.errorFormGeneral}">
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

					<c:if test="${empty icaDatosAux.errorFormGeneral}">
						<div class="alert alert-info">
							<button type="button" class="close" data-dismiss="alert">&#215</button>
							A continuaci&oacute;n se detallan las diferencias entre la informaci&oacute;n registrada en el Instituto y las
							entidades externas.
						</div>

						<div id="acordeon">
							<h3>
								<a href='#'>DATOS CONFIRMADOS POR SAT</a>
							</h3>
							<div id="datosConfirmadosSat">

								<table class="table" id="tabla-imss-sat" style="font-size: xx-small;">
									<thead>
										<tr>
											<th>
												<b>DATOS CONFIRMADOS SAT</b>
											</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>
												<table cellpadding="0" cellspacing="0" class="tblICA table-striped">
													<tr>
														<td colspan="4" style="text-align: center;">
															<b>DATOS B&Aacute;SICOS</b>
														</td>
													</tr>
													<tr>
														<td style="width: 15%;">&nbsp;</td>
														<td style="text-align: center; width: 40%;">
															<b>DATOS ACTUALES</b>
														</td>
														<td style="text-align: center; width: 40%;">
															<b>DATOS ENTIDAD EXTERNA</b>
														</td>
														<td style="width: 5%;">&nbsp;</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">Nombre &oacute; Raz&oacute;n Social</td>
														<td>${icaDatosAux.personaMoralIMSS.razonSocial}</td>
														<td>${icaDatosAux.personaMoralEE.razonSocial}</td>
														<td>
															<c:if test="${icaDatosAux.cambios['nombreRazonSocial'].id ne 4}">
																<span class="label label-danger" style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if>
														</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">Fecha de Constituci&oacute;n</td>
														<td>${icaDatosAux.personaMoralIMSS.fechaCreacionFormateada}</td>
														<td>${icaDatosAux.personaMoralEE.fechaCreacionFormateada}</td>
														<td>
															<c:if test="${icaDatosAux.cambios['fechaConstitucion'].id ne 4}">
																<span class="label label-danger" style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if>
														</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">Tipo de Sociedad</td>
														<td>${icaDatosAux.personaMoralIMSS.tipoSociedad.descripcionAbreviada}</td>
														<td>${icaDatosAux.personaMoralEE.tipoSociedad.descripcionAbreviada}</td>
														<td>
															<c:if test="${icaDatosAux.cambios['tipoSociedad'].id ne 4}">
																<span class="label label-danger" style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if>
														</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">RFC</td>
														<td>${icaDatosAux.personaMoralIMSS.rfc}</td>
														<td>${icaDatosAux.personaMoralEE.rfc}</td>
														<td>
															<c:if test="${icaDatosAux.cambios['rfc'].id ne 4}">
																<span class="label label-danger" style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if>
														</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">Situaci&oacute;n</td>
														<td>${icaDatosAux.personaMoralIMSS.situacionesSAT[0].descripcion}</td>
														<td>${icaDatosAux.personaMoralEE.situacionesSAT[0].descripcion}</td>
														<td>
															<c:if test="${icaDatosAux.cambios['situacionSAT'].id ne 4}">
																<span class="label label-danger" style="font-size: xx-small;" title="CAMBIO">*</span>
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
														<td colspan="4" style="text-align: center;">
															<b>DOMICILIO FISCAL</b>
														</td>
													</tr>
													<tr>
														<td style="width: 15%;">&nbsp;</td>
														<td style="text-align: center; width: 40%;">
															<b>DATOS ACTUALES</b>
														</td>
														<td style="text-align: center; width: 40%;">
															<b>DATOS ENTIDAD EXTERNA</b>
														</td>
														<td style="width: 5%;">&nbsp;</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">C&oacute;digo Postal</td>
														<td>${icaDatosAux.personaMoralIMSS.domicilioFiscal.codigoPostal.codigoPostal}</td>
														<td>${icaDatosAux.personaMoralEE.domicilioFiscal.codigoPostal.codigoPostal}</td>
														<td>
															<c:if test="${icaDatosAux.cambios['codigoPostal'].id ne 4}">
																<span class="label label-danger" style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if>
														</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">Calle</td>
														<td>${icaDatosAux.personaMoralIMSS.domicilioFiscal.calle}</td>
														<td>${icaDatosAux.personaMoralEE.domicilioFiscal.calle}</td>
														<td>
															<c:if test="${icaDatosAux.cambios['calle'].id ne 4}">
																<span class="label label-danger" style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if>
														</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">Colonia</td>
														<td>${icaDatosAux.personaMoralIMSS.domicilioFiscal.colonia}</td>
														<td>${icaDatosAux.personaMoralEE.domicilioFiscal.colonia}</td>
														<td>
															<c:if test="${icaDatosAux.cambios['colonia'].id ne 4}">
																<span class="label label-danger" style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if>
														</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">Entidad Federativa</td>
														<td>${icaDatosAux.personaMoralIMSS.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre}</td>
														<td>${icaDatosAux.personaMoralEE.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre}</td>
														<td>
															<c:if test="${icaDatosAux.cambios['entidad'].id ne 4}">
																<span class="label label-danger" style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if>
														</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">Localidad</td>
														<td>${icaDatosAux.personaMoralIMSS.domicilioFiscal.asentamiento.localidad.nombre}</td>
														<td>${icaDatosAux.personaMoralEE.domicilioFiscal.asentamiento.localidad.nombre}</td>
														<td>
															<c:if test="${icaDatosAux.cambios['localidad'].id ne 4}">
																<span class="label label-danger" style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if>
														</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">Municipio</td>
														<td>${icaDatosAux.personaMoralIMSS.domicilioFiscal.asentamiento.localidad.municipio.nombre}</td>
														<td>${icaDatosAux.personaMoralEE.domicilioFiscal.asentamiento.localidad.municipio.nombre}</td>
														<td>
															<c:if test="${icaDatosAux.cambios['municipio'].id ne 4}">
																<span class="label label-danger" style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if>
														</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">Entre Calle 1</td>
														<td>${icaDatosAux.personaMoralIMSS.domicilioFiscal.vialidadReferenciaPrimaria.nombre}</td>
														<td>${icaDatosAux.personaMoralEE.domicilioFiscal.vialidadReferenciaPrimaria.nombre}</td>
														<td>
															<c:if test="${icaDatosAux.cambios['entreCalle1'].id ne 4}">
																<span class="label label-danger" style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if>
														</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">Entre Calle 2</td>
														<td>${icaDatosAux.personaMoralIMSS.domicilioFiscal.vialidadReferenciaSecundaria.nombre}</td>
														<td>${icaDatosAux.personaMoralEE.domicilioFiscal.vialidadReferenciaSecundaria.nombre}</td>
														<td>
															<c:if test="${icaDatosAux.cambios['entreCalle2'].id ne 4}">
																<span class="label label-danger" style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if>
														</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">Inmueble</td>
														<td>${icaDatosAux.personaMoralIMSS.domicilioFiscal.asentamiento.tipoAsentamiento.descripcion}</td>
														<td>${icaDatosAux.personaMoralEE.domicilioFiscal.asentamiento.tipoAsentamiento.descripcion}</td>
														<td>
															<c:if test="${icaDatosAux.cambios['inmueble'].id ne 4}">
																<span class="label label-danger" style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if>
														</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">Referencia</td>
														<td>${icaDatosAux.personaMoralIMSS.domicilioFiscal.descripcion}</td>
														<td>${icaDatosAux.personaMoralEE.domicilioFiscal.descripcion}</td>
														<td>
															<c:if test="${icaDatosAux.cambios['referencia'].id ne 4}">
																<span class="label label-danger" style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if>
														</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">Vialidad</td>
														<td>${icaDatosAux.personaMoralIMSS.domicilioFiscal.vialidadPrimaria.nombre}</td>
														<td>${icaDatosAux.personaMoralEE.domicilioFiscal.vialidadPrimaria.nombre}</td>
														<td>
															<c:if test="${icaDatosAux.cambios['vialidad'].id ne 4}">
																<span class="label label-danger" style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if>
														</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">N&uacute;mero Interior</td>
														<td>${icaDatosAux.personaMoralIMSS.domicilioFiscal.numInteriorAlf}</td>
														<td>${icaDatosAux.personaMoralEE.domicilioFiscal.numInteriorAlf}</td>
														<td>
															<c:if test="${icaDatosAux.cambios['numeInt'].id ne 4}">
																<span class="label label-danger" style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if>
														</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">N&uacute;mero Exterior</td>
														<td>${icaDatosAux.personaMoralIMSS.domicilioFiscal.numExteriorAlf}</td>
														<td>${icaDatosAux.personaMoralEE.domicilioFiscal.numExteriorAlf}</td>
														<td>
															<c:if test="${icaDatosAux.cambios['numeExt'].id ne 4}">
																<span class="label label-danger" style="font-size: xx-small;" title="CAMBIO">*</span>
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
														<td colspan="4" style="text-align: center;">
															<b>FORMA DE CONTACTO 1 (TEL&Eacute;FONO FIJO)</b>
														</td>
													</tr>
													<tr>
														<td style="width: 15%;">&nbsp;</td>
														<td style="text-align: center; width: 40%;">
															<b>DATOS ACTUALES</b>
														</td>
														<td style="text-align: center; width: 40%;">
															<b>DATOS ENTIDAD EXTERNA</b>
														</td>
														<td style="width: 5%;">&nbsp;</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">N&uacute;mero</td>
														<td>${icaDatosAux.personaMoralIMSS.telefonoFijoFiscal.numero}</td>
														<td>${icaDatosAux.personaMoralEE.telefonoFijoFiscal.numero}</td>
														<td>
															<c:if test="${icaDatosAux.cambios['telefonoFijo.numero'].id ne 4}">
																<span class="label label-danger" style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if>
														</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">Clave lada</td>
														<td>${icaDatosAux.personaMoralIMSS.telefonoFijoFiscal.claveLada}</td>
														<td>${icaDatosAux.personaMoralEE.telefonoFijoFiscal.claveLada}</td>
														<td>
															<c:if test="${icaDatosAux.cambios['telefonoFijo.lada'].id ne 4}">
																<span class="label label-danger" style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if>
														</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">Extensi&oacute;n</td>
														<td>${icaDatosAux.personaMoralIMSS.telefonoFijoFiscal.extension}</td>
														<td>${icaDatosAux.personaMoralEE.telefonoFijoFiscal.extension}</td>
														<td>
															<c:if test="${icaDatosAux.cambios['telefonoFijo.extension'].id ne 4}">
																<span class="label label-danger" style="font-size: xx-small;" title="CAMBIO">*</span>
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
														<td colspan="4" style="text-align: center;">
															<b>FORMA DE CONTACTO 2 (TEL&Eacute;FONO M&Oacute;VIL)</b>
														</td>
													</tr>
													<tr>
														<td style="width: 15%;">&nbsp;</td>
														<td style="text-align: center; width: 40%;">
															<b>DATOS ACTUALES</b>
														</td>
														<td style="text-align: center; width: 40%;">
															<b>DATOS ENTIDAD EXTERNA</b>
														</td>
														<td style="width: 5%;">&nbsp;</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">N&uacute;mero</td>
														<td>${icaDatosAux.personaMoralIMSS.telefonoMovilFiscal.numero}</td>
														<td>${icaDatosAux.personaMoralEE.telefonoMovilFiscal.numero}</td>
														<td>
															<c:if test="${icaDatosAux.cambios['telefonoMovil'].id ne 4}">
																<span class="label label-danger" style="font-size: xx-small;" title="CAMBIO">*</span>
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
														<td colspan="4" style="text-align: center;">
															<b>FORMA DE CONTACTO 3 (CORREO ELECTR&Oacute;NICO)</b>
														</td>
													</tr>
													<tr>
														<td style="width: 15%;">&nbsp;</td>
														<td style="text-align: center; width: 40%;">
															<b>DATOS ACTUALES</b>
														</td>
														<td style="text-align: center; width: 40%;">
															<b>DATOS ENTIDAD EXTERNA</b>
														</td>
														<td style="width: 5%;">&nbsp;</td>
													</tr>
													<tr>
														<td style="font-weight: bold;">Correo</td>
														<td>${icaDatosAux.personaMoralIMSS.correoElectronicoFiscal.correo}</td>
														<td>${icaDatosAux.personaMoralEE.correoElectronicoFiscal.correo}</td>
														<td>
															<c:if test="${icaDatosAux.cambios['correoElectronico'].id ne 4}">
																<span class="label label-danger" style="font-size: xx-small;" title="CAMBIO">*</span>
															</c:if>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
						<!-- acordeon -->
					</c:if>
				</form:form>

			</div>

		</div>
	</div>
	<div class="pie row">
		<div class="col-sm-12">
			<div class="pull-right">
			<c:if
				test="${empty icaDatosAux.errorFormGeneral or (not empty icaDatosAux.errorFormGeneral && 
								fn:contains(icaDatosAux.errorFormGeneral , 'No existen diferencias'))}">
								
				<a id="siguientePaso" class="btn btn-primary">
						<i class="glyphicon glyphicon-step-forward"></i>Siguiente
					</a>
				<div class="btn-group">
					<a href="#" class="btn btn-primary">
						<spring:message code="label.menus.opciones" />
					</a>
					<a href="#" data-toggle="dropdown" class="btn btn-primary dropdown-toggle">
						<span class="caret"></span>
					</a>
					<ul class="dropdown-menu pull-right">
						<li>
							<a id="guardarTramite">
								<i class="glyphicon glyphicon-download-alt"></i><spring:message code="wizard.button.guardarTramite"/>
							</a>
						</li>
						<li>
							<a id="guardarCerrarTramite">
								<i class="glyphicon glyphicon-download-alt"></i><spring:message code="wizard.button.guardarCerrar"/>
							</a>
						</li>
						<li>
							<a id="cancelarTramite">
								<i class="glyphicon glyphicon-trash"></i><spring:message code="wizard.button.cancelarTramite"/>
							</a>
						</li>
					</ul>
				</div>
			</c:if>
			</div>
		</div>
	</div>
</div>

<!-- Forma para invocar al servicio del Modificacion Manual de Datos -->
<form id="comprobacionIcaForm" method="post">
</form>

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			&#191;Desea cancelar la solicitud pendiente con folio: <strong>${folioSolicitud}</strong>?
	</p>
</div>

<div id="dialog-confirm" title="Mensaje">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			<label id="mensajeDialogo"></label>
	</p>
</div>

<div id="dialogoConfirmacion">
	<p>
		<span id="textoConfirmacion"></span>
	</p>
</div>

<div id="dialogoMensajes">
	<p><span id="textoMensaje"></span></p>
</div>
