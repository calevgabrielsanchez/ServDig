<%@ page import="mx.gob.imss.ctirss.delta.model.enums.FormaPagoEnum" %>
<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>

<script type="text/javascript">
	$("#siguienteCotizacion").live('click', function(event) {
		event.preventDefault();
		$('#formCotIvro').submit();
	});

	$("#agregarDomicilio").live('click', function(event) {
		event.preventDefault();
		$('#otraUbicacionForm').submit();
	});

	$("#cerrarWizard, #cerrarWizardCot").live('click', function(event) {
		event.preventDefault();
		parent.WizardSeguroIvroIndivCtrl.cerrar();
	});
</script>
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="formaPagoBimestral" value="<%=FormaPagoEnum.BIMESTRAL.getId()%>" />
<c:set var="formaPagoAnual" value="<%=FormaPagoEnum.ANUAL.getId()%>" />

<div class="contenedor col-sm-12">
	<c:set var="defaultLocale" value="${pageContext.request.locale}" />
	<fmt:setLocale value="es_MX" scope="session" />

	<div class="contenido row" style="min-height: 400px;">
		<jsp:include page="pasosIndividual.jsp">
			<jsp:param name="paso" value="3" />
		</jsp:include>
		<div class="col-sm-12">

			<!-- Mensaje de Error RISS -->
            <c:if test="${not empty datosRiss.errorFormGeneral}">
                <div class="alert alert-danger">
                    <button type="button" class="close" data-dismiss="alert">×</button>

                    <c:choose>
                        <c:when test="${fn:contains(datosRiss.errorFormGeneral, 'Debido a cancelaci')}">
                            <strong>LO SENTIMOS, NO APLICA AL BENEFICIO DEL R&Eacute;GIMEN DE INCORPORACI&Oacute;N AL SEGURO SOCIAL (RISS), POR EL SIGUIENTE MOTIVO:</strong> Se ubica en los supuestos de terminaci&oacute;n del otorgamiento al subsidio, de conformidad con las disposiciones de car&aacute;cter general para la aplicaci&oacute;n del est&iacute;mulo fiscal al pago de las cuotas obrero patronales al Seguro Social.
                        </c:when>
                        <c:when test="${fn:contains(datosRiss.errorFormGeneral, 'No cumple requisitos RIF')}">
                            <strong>LO SENTIMOS, NO APLICA AL BENEFICIO DEL R&Eacute;GIMEN DE INCORPORACI&Oacute;N AL SEGURO SOCIAL (RISS), POR EL SIGUIENTE MOTIVO:</strong> El SAT informa que no pertenece al R&eacute;gimen de Incorporaci&oacute;n Fiscal (RIF).
                        </c:when>
                        <c:when test="${fn:contains(datosRiss.errorFormGeneral, 'Error de comunicaci')}">
                            <strong>LO SENTIMOS, POR EL MOMENTO NO ES POSIBLE VERIFICAR SI PERTENCE AL R&Eacute;GIMEN DE INCORPORACI&Oacute;N FISCAL (RIF) ANTE EL SAT, PARA GOZAR DEL BENEFICIO DEL R&Eacute;GIMEN </strong>
                        </c:when>
                        <c:when test="${!fn:contains(datosRiss.errorFormGeneral, 'Ya cuentas')}">
                            <strong>LO SENTIMOS, NO APLICA AL BENEFICIO DEL R&Eacute;GIMEN DE INCORPORACI&Oacute;N AL SEGURO SOCIAL (RISS), POR EL SIGUIENTE MOTIVO:</strong> ${datosRiss.errorFormGeneral}
                        </c:when>
                        <c:otherwise>${datosRiss.errorFormGeneral}</c:otherwise>
                    </c:choose>
                </div>
            </c:if>

			<c:if test="${not empty cotizacion.errorFormGeneral}">
				<div style="min-height: 200px;">
					<div class="alert alert-danger">${cotizacion.errorFormGeneral}</div>
				</div>
			</c:if>
			<c:if test="${empty cotizacion.errorFormGeneral}">

				<div id="divVigencias">
					<div class="titulo separadorseccion">
						<span> Cotizaci&oacute;n de Incorporaci&oacute;n Voluntaria
							al R&eacute;gimen Obligatorio</span>
					</div>

					<div class="row">
						<div class="col-xs-4">
							<strong>Fecha inicio vigencia:</strong>
						</div>
						<div class="col-xs-2">
							<fmt:formatDate pattern="dd/MM/yyyy"
								value="${cotizacion.detalle.fechaInicioCalculo.time}" />
						</div>
						<div class="col-xs-4">
							<strong>Fecha fin vigencia:</strong>
						</div>
						<div class="col-xs-2">
							<c:choose>
								<c:when test="${cotizacion.detalle.empleados[0].conBeneficio}">
									<fmt:formatDate pattern="dd/MM/yyyy"
										value="${cotizacion.detalle.empleados[0].periodos[0].finPeriodo.time}" />
								</c:when>
								<c:otherwise>
									<fmt:formatDate pattern="dd/MM/yyyy"
										value="${cotizacion.detalle.fechaFinCalculo.time}" />
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					<fmt:setLocale value="es_MX" />

					<br>
					<div class="separadorseccion">
						<span>Detalle de cotizaci&oacute;n de la
							Incorporaci&oacute;n Voluntaria al R&eacute;gimen Obligatorio</span>
					</div>
					<div class="row">
						<div class="col-xs-6">
							<strong>Zona salarial:</strong>
						</div>
						<div class="col-xs-6">${cotizacion.detalle.zonaSalarial}</div>
					</div>
					<div class="row">
						<div class="col-xs-6">
							<strong>Costo :</strong>
						</div>
						<div class="col-xs-6">
							<strong> <c:choose>
									<c:when test="${cotizacion.detalle.empleados[0].conBeneficio}">
										<fmt:formatNumber
											value="${cotizacion.detalle.empleados[0].periodos[0].total}"
											type="currency" />
									</c:when>
									<c:otherwise>
										<fmt:formatNumber value="${cotizacion.detalle.cuotaTotal}"
											type="currency" />
									</c:otherwise>
								</c:choose>

							</strong>
						</div>
						<div class="col-xs-6">
							<strong>Forma de pago:</strong>
						</div>
						<div class="col-xs-6">
							<c:choose>
								<c:when test="${formaPago eq formaPagoAnual}">
									Anual
								</c:when>
								<c:when test="${formaPago eq formaPagoBimestral}">
									Bimestral
								</c:when>
							</c:choose>
						</div>
				</div>


			<br>
					<div class="separadorseccion">
						<span>Datos del solicitante</span>
					</div>

					<c:forEach var="emp" items="${cotizacion.detalle.empleados}">
						<div class="row">
							<div class="col-xs-5">
								<strong>Nombre :</strong>
							</div>
							<div class="col-xs-6">${emp.nombreTrabajador}</div>
						</div>
						<div class="row">
							<div class="col-xs-5">
								<strong>N&uacute;mero de seguridad social :</strong>
							</div>
							<div class="col-xs-6">${emp.numeroSeguridadSocial}</div>
						</div>

						<div class="separadorseccion">
							<span>Domicilio</span>
						</div>
						<div class="row">
							<div class="col-xs-2">
								<strong>Calle :</strong>
							</div>
							<div class="col-xs-2">${domicilio.calle}</div>
							<div class="col-xs-2">
								<strong>N&uacute;m. Ext. :</strong>
							</div>
							<div class="col-xs-2">${domicilio.numExteriorAlf}
								${domicilio.numExterior1}</div>
							<div class="col-xs-2">
								<strong>N&uacute;m. Int. :</strong>
							</div>
							<div class="col-xs-2">${domicilio.numInteriorAlf}
								${domicilio.numInterior}</div>
						</div>
						<div class="row">
							<div class="col-xs-2">
								<strong>Colonia :</strong>
							</div>
							<div class="col-xs-2">${domicilio.colonia}</div>
							<div class="col-xs-2">
								<strong>Municipio :</strong>
							</div>
							<div class="col-xs-2">
								<c:if
									test="${domicilio.localidad != null and domicilio.localidad.municipio != null}">
                  ${domicilio.localidad.municipio.nombre}
                </c:if>
							</div>
							<div class="col-xs-2">
								<strong>C.P. :</strong>
							</div>
							<div class="col-xs-2">${domicilio.codigoPostal}</div>
						</div>

						<br>
						<div class="separadorseccion">
							<span>Medios de contacto</span>
						</div>
						<c:choose>
				<c:when  test="${not empty correoIVRO}">
				<div class="row">
					<div class="col-xs-4">
						<strong>Correo electr&oacute;nico:</strong>
					</div>
					<div class="col-xs-2"><span>${correoIVRO}</span></div>
				</div>
				</c:when>
						<c:otherwise>
							<c:forEach items="${personaMC.mediosContacto}"
								var="medioContacto">
								<div class="row">
									<div class="col-xs-4">
										<strong> <c:choose>
													<c:when test="${medioContacto.tipoMedioContacto.idTipoMedioContacto == 1}">
														Correo electr&oacute;nico:
													</c:when>
													<c:when test="${medioContacto.tipoMedioContacto.idTipoMedioContacto == 2}">
														Tel&eacute;fono fijo:
													</c:when>
													<c:when test="${medioContacto.tipoMedioContacto.idTipoMedioContacto == 3}">
														Tel&eacute;fono m&oacute;vil:
													</c:when>
													<c:when test="${medioContacto.tipoMedioContacto.idTipoMedioContacto == 4}">
														Facebook:
													</c:when>
													<c:when test="${medioContacto.tipoMedioContacto.idTipoMedioContacto == 5}">
														Twitter:
													</c:when>
												</c:choose>
										</strong>
									</div>
									<div class="col-xs-2">
										<span>${medioContacto.desFormaContacto}</span>
									</div>
								</div>
							</c:forEach>
						</c:otherwise>
					</c:choose>

						<br>
						<c:if test="${emp.conBeneficio}">
							<div class="alert alert-success">
								<span> <strong>Este seguro se cotiz&oacute; con
										beneficio RISS</strong>
								</span>
							</div>
						</c:if>
							<div class="separadorseccion">
								<span> Periodos de aseguramiento</span>
							</div>
							<table class="table table-striped table-bordered" cellpadding="0"
								cellspacing="0" border="0">
								<thead>
									<tr>
										<th>Fecha de inicio bimestre</th>
										<th>Fecha de fin bimestre</th>
										<th>Total</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="p" items="${emp.periodos}">
										<tr>
											<td><fmt:formatDate pattern="dd/MM/yyyy"
																value="${p.inicioPeriodo.time}" /></td>
											<td><fmt:formatDate pattern="dd/MM/yyyy"
																value="${p.finPeriodo.time}" /></td>
											<td style="text-align: right;"><strong> <fmt:formatNumber
													value="${p.total}" type="currency" />
											</strong></td>
										</tr>
									</c:forEach>
									<tr>
										<td colspan="2"></td>
										<td style="text-align: right;">
											<h5>
												<fmt:formatNumber value="${emp.cuotaTotal}"
													type="currency" />
											</h5>
										</td>
									</tr>
								</tbody>
							</table>

					</c:forEach>
				</div>
			</c:if>
		</div>
		<fmt:setLocale value="${defaultLocale}" scope="session" />
	</div>
	<form:form id="otraUbicacionForm" method="post"
		action="${contextpath}/wizard/individual/otraUbicacion"></form:form>
	<form:form id="formCotIvro" method="post"
		action="${contextpath}/wizard/individual/aplicaCuestionario"></form:form>
	<div class="pie row">
		<div class="opciones col-sm-6">
			<button id="agregarDomicilio" class="btn btn-primary">Modificar
				Domicilio</button>
		</div>
		<div class="controles col-sm-6">
			<div class="pull-right">
				<c:if test="${empty cotizacion.errorFormGeneral}">
					<button class="btn btn-default" id="cerrarWizardCot">Cerrar</button>
					<a id="siguienteCotizacion" class="btn btn-primary"> <i
						class="glyphicon glyphicon-step-forward"></i> Siguiente
					</a>
				</c:if>
				<c:if test="${not empty cotizacion.errorFormGeneral}">
					<button class="btn btn-default" id="cerrarWizard">Cerrar</button>
				</c:if>
			</div>
		</div>
	</div>
</div>
