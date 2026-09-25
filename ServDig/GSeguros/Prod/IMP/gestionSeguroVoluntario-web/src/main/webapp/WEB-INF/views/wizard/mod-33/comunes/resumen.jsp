<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/mod-33/comunes/resumen.js" htmlEscape="true" />">
</script>
<script type="text/javascript"
		src="<spring:url value="/static/resources/js/wizard/comunes/obtenerPais.js" htmlEscape="true" />">
</script>

<script type="text/javascript">
	// var codigoTipoSolicitud = ${codigoTipoSolicitud};
	var descripcionTipoSolicitud = '${descripcionTipoSolicitud}';
	//var arrayCodigoTipoTramite = ${codigoTipoTramite};
	var tipoOperacion = '${tipoOperacion}';
	var cancelable = true;
	// var ventanilla = ${esVentanilla};
	// var tieneSeguros = ${tieneSeguros};
	var datosEntradaFirma = {
		fechaElectronica : '${datosFirmaElectronica.fechaElectronicaFormateada}',
		nombreCompleto : '${datosFirmaElectronica.nombreCompleto}',
		registroPatronal : '${datosFirmaElectronica.registroPatronal}',
		rfc : '${datosFirmaElectronica.rfc}',
		curp : '${datosFirmaElectronica.curp}'
	};
	-->
</script>

<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<style>
.total {
	font-size: 25px;
}

table.table {
	font-size: initial !important;
}

</style>

<div class="contenedor col-sm-12">
	<c:choose>
		<c:when test="${empty error}">
			<div class="contenido row">
				<div class="col-sm-12 form-horizontal">
					<c:set var="defaultLocale" value="${pageContext.request.locale}" />
					<fmt:setLocale value="es_MX" scope="session" />
					<div class="alert alert-success">
						Tu solicitud se ha creado exitosamente: <strong>${solicitud.numSolicitud}</strong>
					</div>
                    <input type="hidden" id="idSolicitud" value=${solicitud.idSolicitud} >

					<div id="datosSolicitante" class="m-b-lg">
						<div class="titulo">
						<c:if test="${enRenovacion and not extemporanea}">
							<span>Paso 3 de 4: Confirma tus datos</span>
						</c:if>
						<c:if test="${!enRenovacion or extemporanea}">
							<span>Paso 4 de 5: Confirma tus datos</span>
						</c:if>	
						</div>
						<div class="titulo">
							<span>Datos del solicitante</span>
							<hr class="red m-b-none">
						</div>
						<form class="form-horizontal" role="form">
							<div class="form-group">
								<label class="col-sm-2 control-label">Nombre</label>
								<div class="col-sm-5">
									<p class="form-control-static">${solicitante.nombreCompleto}</p>
								</div>

								<label class="col-sm-2 control-label">NSS</label>
								<div class="col-sm-3">
									<p class="form-control-static">${solicitante.nss}</p>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label">Correo(s)
									electr&oacute;nico(s)</label>
								<div class="col-sm-5">
									<c:forEach items="${solicitante.mediosContacto}"
										var="medioContacto">
										<c:if
											test="${medioContacto.tipoMedioContacto.idTipoMedioContacto == 1}">
											<p class="form-control-static">${medioContacto.desFormaContacto}</p>
										</c:if>
									</c:forEach>
								</div>

								<label class="col-sm-2 control-label">Fecha solicitud</label>
								<div class="col-sm-3">
									<p class="form-control-static">
										<fmt:formatDate value="${solicitud.fechaRegistro}"
											pattern="dd/MM/yyyy" />
									</p>
								</div>
							</div>
						</form>
					</div>

					<div id="domicilio" class="m-b-lg">
						<div class="titulo">
							<span>Domicilio</span>
							<hr class="red m-b-none">
						</div>

						<form class="form-horizontal" role="form">
							<div class="form-group">
								<label class="col-sm-2 control-label">C&oacute;digo
									Postal</label>
								<div class="col-sm-4">
									<p class="form-control-static">
										${tramiteSeguro.domicilioSeguro.codigoPostal}</p>
								</div>

								<label class="col-sm-2 control-label">Colonia</label>
								<div class="col-sm-4">
									<p class="form-control-static">
										${tramiteSeguro.domicilioSeguro.colonia}</p>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label">Delegaci&oacute;n
									o Municipio</label>
								<div class="col-sm-4">
									<p class="form-control-static">
										<c:if
											test="${tramiteSeguro.domicilioSeguro.localidad != null and tramiteSeguro.domicilioSeguro.localidad.municipio != null}">
											${tramiteSeguro.domicilioSeguro.localidad.municipio.nombre}
										</c:if>
									</p>
								</div>

								<label class="col-sm-2 control-label">Estado</label>
								<div class="col-sm-4">
									<p class="form-control-static">
										<c:if
											test="${tramiteSeguro.domicilioSeguro.localidad != null and tramiteSeguro.domicilioSeguro.localidad.municipio != null and tramiteSeguro.domicilioSeguro.localidad.municipio.entidadFederativa != null}">
											${tramiteSeguro.domicilioSeguro.localidad.municipio.entidadFederativa.nombre}
										</c:if>
									</p>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label">Calle</label>
								<div class="col-sm-4">
									<p class="form-control-static" style="word-wrap: break-word;">
										<c:if
											test="${tramiteSeguro.domicilioSeguro.vialidadPrimaria != null}">
											${tramiteSeguro.domicilioSeguro.vialidadPrimaria.nombre}
										</c:if>
									</p>
								</div>

								<label class="col-sm-2 control-label">N&uacute;mero Ext.
									e Int.</label>
								<div class="col-sm-4">
									<p class="form-control-static">
										${tramiteSeguro.domicilioSeguro.numExteriorAlf}
										<c:if
											test="${tramiteSeguro.domicilioSeguro.numExterior1 gt 0}">
											${tramiteSeguro.domicilioSeguro.numExterior1}
										</c:if>
										<span> </span> ${tramiteSeguro.domicilioSeguro.numInteriorAlf}
										<c:if test="${tramiteSeguro.domicilioSeguro.numInterior gt 0}">
											${tramiteSeguro.domicilioSeguro.numInterior}
										</c:if>
									</p>
								</div>
							</div>
						</form>
					</div>

					<div id="cobertura" class="m-b-lg">
						<div class="titulo">
							<span>Cobertura de los servicios m&eacute;dicos</span>
							<hr class="red m-b-none">
						</div>
						<form class="form-horizontal" role="form">
							<div class="form-group">
								<label class="col-sm-3 control-label">Inicio de la
									vigencia</label>
								<div class="col-sm-3">
									<p class="form-control-static">
										<fmt:formatDate
											value="${solicitud.tramite[0].cotizacion.detalle.fechaInicioCalculo.time}"
											pattern="dd/MM/yyyy" />
									</p>
								</div>

								<label class="col-sm-3 control-label">T&eacute;rmino de
									la vigencia</label>
								<div class="col-sm-3">
									<p class="form-control-static">
										<fmt:formatDate
											value="${solicitud.tramite[0].cotizacion.detalle.fechaFinCalculo.time}"
											pattern="dd/MM/yyyy" />
									</p>
								</div>
							</div>
						</form>
					</div>

					<div id="detalleCotizacion" class="m-b-lg">
						<div class="titulo">
							<span>Detalle de la cotizaci&oacute;n</span>
							<hr class="red m-b-none">
						</div>
						<div class="table-responsive">
							<table id="tblIntegrantes"
								class="table table-striped table-bordered table-word-wrap-fixed">
								<thead>
									<tr>
										<th style="width: 25%;">Nombre</th>
										<th style="width: 25%;">CURP</th>
										<th style="width: 20%;">NSS</th>
										<th style="width: 15%;">Parentesco</th>
										<th style="width: 15%;">Pago anual por persona</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach
										items="${solicitud.tramite[0].cotizacion.detalle.empleados}"
										var="integrante" varStatus="status">
										<tr>
											<td>${integrante.nombreTrabajador}</td>
											<td>${integrante.curp}</td>
											<td>${integrante.numeroSeguridadSocial}</td>
											<td>${integrante.parentesco.descripcion}</td>
											<td><fmt:formatNumber value="${integrante.cuotaTotal}"
													type="currency" /></td>
										</tr>
										<c:set var="costoTotal"
											value="${costoTotal + integrante.cuotaTotal}" scope="page" />
									</c:forEach>
								</tbody>
								<tfoot>
									<tr>
										<th colspan="4" class="text-right"
											style="padding-right: 20px;"><strong class="total">Total</strong>
										</th>
										<th id="costoTotal"><strong class="total"> <fmt:formatNumber
													value="${costoTotal}" type="currency" />
										</strong></th>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>

					<div class="alert alert-info" style="text-align: center;">
						<form:form id="aceptarTerminosCondiciones">
							<label> <input id="chkTCCuestionario" name="aceptarTC"
								type="checkbox" /> <span> Acepto los <a
									id="linkTCCuestionario" href="#">T&eacute;rminos y
										condiciones</a>
							</span>
							</label>
						</form:form>
					</div>

					<div class="alert alert-info">
						<p>
							<b>Acciones al concluir tu solicitud</b>
						</p>
						<c:choose>
							<c:when test="${esVentanilla}">
								<p>Una vez concluida la solicitud, le recordamos que tiene
									que iniciar nuevamente el tr&aacute;mite para poder ver la
									impresi&oacute;n de l&iacute;neas de captura, comprobante y
									cuestionario m&eacute;dico.</p>
								<p>Recuerda imprimir tus L&iacute;neas de Captura y realizar
									el pago correspondiente antes de la fecha de fin de vigencia de
									cada l&iacute;nea. Recuerda que el incumplimiento en el pago de
									tus l&iacute;neas de captura en tiempo y forma es motivo de
									cancelaci&oacute;n de tus servicios m&eacute;dicos.</p>
							</c:when>
							<c:otherwise>
								<p>Una vez concluida la solicitud de Incorporaci&oacute;n al
									Seguro de Salud para la Familia, es necesario que obtengas tus
									l&iacute;neas de captura, las cuales se encuentran disponibles
									al momento de finalizar el tr&aacute;mite, dar clic en acciones
									y seleccionar la opci&oacute;n &quot;Ver Detalle&quot; es
									importante realizar el pago correspondiente antes de la fecha
									de vencimiento.</p>
								<p>Con la finalidad de que el Instituto Mexicano del Seguro
									Social se mantenga en contacto con Usted, se le solicita tener
									registrada y actualizada una direcci&oacute;n de correo
									electr&oacute;nico.</p>
							</c:otherwise>
						</c:choose>
					</div>

					<input id="folioSolicitud" type="hidden"
						value="${solicitud.numSolicitud}" /> <input id="contenidoFirmar"
						type="hidden" value="${contenidoFirmar}" />

					<fmt:setLocale value="${defaultLocale}" scope="session" />
				</div>
			</div>
			<div class="pie row">
				<div class="opciones col-sm-5"></div>
				<div class="controles col-sm-7">
					<div class="row">
						<button id="siguientePaso" class="btn btn-primary">
							<i class="glyphicon glyphicon-ok"></i> Finalizar Solicitud
						</button>
						<button id="cancelarSolicitud" class="btn btn-default">
							<i class="glyphicon glyphicon-trash"></i> Cancelar Solicitud
						</button>
					</div>
				</div>
			</div>
			<div style="display: none;">
				<%@ include
					file="../../mod-33/comunes/terminosCondicionesCuestionario.jsp"%>
			</div>
		</c:when>
		<c:otherwise>
			<div class="contenido row">
				<div class="alert alert-danger">
					<span>${error}</span>
				</div>
			</div>
			<div class="pie row">
				<div class="controles col-sm-6">
					<div class="pull-right">
						<button id="salirSolicitud" class="btn btn-default">Salir</button>
					</div>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</div>

<div id="dialog-confirm-cancelar"
	title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> ¿Desea cancelar la
		solicitud pendiente con folio: <strong>${solicitud.numSolicitud}</strong>
		?
	</p>
</div>

<div id="dialog-confirm" title="Mensaje">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> <label
			id="mensajeDialogo"></label>
	</p>
</div>
