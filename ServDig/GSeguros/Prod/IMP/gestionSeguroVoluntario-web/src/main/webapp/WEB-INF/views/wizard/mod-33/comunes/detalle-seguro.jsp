<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>

<style>
a.print {
	color: inherit;
	text-decoration: none;
}

a.print:hover {
	color: black;
	text-decoration: none;
}

table.table {
	font-size: initial !important;
}
</style>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/mod-33/comunes/detalle-seguro.js" htmlEscape="true" />"></script>

<!-- <script type="text/javascript">

function enviaMultipago(){
	document.getElementById("envia_info_multipago").submit();
	}

</script> -->


<%@ page import="mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.EstadoPagoEnum"%>

<c:set var="today" value="<%=new java.util.Date()%>" />
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="codigoTrabajadorUrbano"
	value="<%=ModalidadEnum.TREINTAYCUATRO.getId()%>" />

<c:set var="estadoPagoPendiente"
	value="<%=EstadoPagoEnum.POR_PAGAR.getId()%>" />
<c:set var="estadoPagoActivo" value="<%=EstadoPagoEnum.PAGADO.getId()%>" />
<c:set var="estadoPagoVencido"
	value="<%=EstadoPagoEnum.VENCIDO.getId()%>" />

<div class="contenedor col-sm-12 m-t-sm">

	<c:set var="defaultLocale" value="${pageContext.request.locale}" />
	<fmt:setLocale value="es_MX" scope="session" />

	<div id="errorNegocio"></div>
	<div class="alert alert-success">
		<c:if test="${enRenovacion}">
                La solicitud de
		renovaci&oacute;n en el Seguro de Salud para la Familia ha sido
		exitosa.
            </c:if>
		<c:if test="${!enRenovacion}">
                La solicitud de
		inscripci&oacute;n en el Seguro de Salud para la Familia ha sido
		exitosa.
            </c:if>
	</div>
	<div id="datosSeguro">
		<div class="titulo">
			<c:if test="${enRenovacion and not extemporanea}">
				<span>Paso 4 de 4: Recibir resultado</span>
			</c:if>
			<c:if test="${!enRenovacion or extemporanea}">
				<span>Paso 5 de 5: Recibir resultado</span>
			</c:if>
		</div>
		<div class="titulo">
			<span>Datos del seguro</span>
			<hr class="red m-b-none">
		</div>
		<div class="row">
			<div class="col-xs-2">
				<strong>Contratante:</strong>
			</div>
			<div class="col-xs-8">${seguro.titular.nombre}</div>
			<div class="col-xs-2"></div>
		</div>
		<br>
		<div class="row">
			<div class="col-xs-2">
				<strong>Tipo Seguro:</strong>
			</div>
			<div class="col-xs-2">Individual</div>
			<div class="col-xs-2">
				<strong>Modalidad:</strong>
			</div>
			<div class="col-xs-6">${seguro.modalidad.numModalidad}-
				${seguro.modalidad.descripcion}</div>
		</div>
		<br>
		<div class="row">
			<div class="col-xs-2">
				<strong>Fecha de inicio de la vigencia:</strong>
			</div>
			<div class="col-xs-2">
				<fmt:formatDate pattern="dd/MM/yyyy" value="${seguro.fechaInicio}" />
			</div>
			<div class="col-xs-2">
				<strong>Fecha del fin de la vigencia:</strong>
			</div>
			<div class="col-xs-2">
				<fmt:formatDate pattern="dd/MM/yyyy" value="${seguro.fechaFin}" />
			</div>
			<div class="col-xs-1">
				<strong>Estado:</strong>
			</div>
			<div class="col-xs-3">
				<span class="${claseEstado}">${seguro.estadoSeguro.descripcion}</span>
			</div>
		</div>
	</div>

	<c:if test="${seguro.tramite != null}">
		<div id="integrantesGpoFamiliar" class="m-t-xl">
			<div class="titulo">
				<span>Integrantes de tu Grupo Familiar</span>
				<hr class="red m-b-none">
			</div>

			<div id="tblBeneficiarioWrapper" class="table-responsive">
				<table id="tblBeneficiarioResume"
					class="table table-striped table-bordered">
					<thead>
						<tr>
							<th>NSS</th>
							<th>Nombre</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${seguro.tramite.beneficiarios}"
							var="trabajador">
							<tr>
								<td>${trabajador.nss}</td>
								<td>${trabajador.nombre}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>

		<div id="recordatorio" class="m-t-md">
			<div class="alert alert-info">
				<c:if test="${enRenovacion}">
					<strong>Obt&eacute;n el comprobante de tr&aacute;mite y
						las l&iacute;neas de captura para realizar los pagos
						correspondientes. Utiliza la banca electr&oacute;nica o acude a la
						sucursal de: BANCOMER, BANAMEX, BANORTE, HSBC, SANTANDER,
						SCOTIABANK, BANBAJIO, AFIRME, INBURSA, BANSI. Una vez realizado el
						pago antes de la fecha l&iacute;mite, habr&aacute;s concluido el
						tr&aacute;mite de Renovaci&oacute;n en el Seguro de Salud para la
						Familia.</strong>
				</c:if>
				<c:if test="${!enRenovacion}">
					<strong>Obt&eacute;n el comprobante de tr&aacute;mite y
						las l&iacute;neas de captura para realizar los pagos
						correspondientes. Utiliza la banca electr&oacute;nica o acude a la
						sucursal de: BANCOMER, BANAMEX, BANORTE, HSBC, SANTANDER,
						SCOTIABANK, BANBAJIO, AFIRME, INBURSA, BANSI. Una vez realizado el
						pago antes de la fecha l&iacute;mite, habr&aacute;s concluido el
						tr&aacute;mite de Inscripci&oacute;n en el Seguro de Salud para la
						Familia.</strong>
				</c:if>

			</div>
		</div>

		<div id="pagos" class="m-t-xl">
			<div class="titulo">
				<span>Pagos</span>
				<hr class="red m-b-none">
			</div>
			<div id="tblSegurosWrapper" style="width: 100%; margin: 0 auto;">
				<table id="tblIvroSegurosIndivResume" style="width: 100%;"
					class="table table-striped table-bordered" cellpadding="0"
					cellspacing="0" border="0">
					<thead>
						<tr>
							<th>Fecha Inicio</th>
							<th>Fecha Fin</th>
							<th>Monto</th>
							<th>Fecha L&iacute;mite Pago</th>
							<th>Estatus</th>
							<c:if test="${imprimir}">
								<th></th>
							</c:if>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${seguro.compra.pagos}" var="pago">
							<tr>
								<td><fmt:formatDate pattern="dd/MM/yyyy"
										value="${pago.fechaInicioPeriodo}" /></td>
								<td><fmt:formatDate pattern="dd/MM/yyyy"
										value="${pago.fechaFinPeriodo}" /></td>
								<td><fmt:formatNumber type="currency" value="${pago.monto}" />
								</td>
								<td><fmt:formatDate pattern="dd/MM/yyyy"
										value="${pago.fechaLimitePago}" /></td>
								<td class="text-center"><c:choose>
										<c:when
											test="${pago.estadoPago.idEstadoPago eq estadoPagoPendiente}">
											<c:set var="cssClassEdoPago" value="label-warning" />
										</c:when>
										<c:when
											test="${pago.estadoPago.idEstadoPago eq estadoPagoActivo}">
											<c:set var="cssClassEdoPago" value="label-success" />
										</c:when>
										<c:when
											test="${pago.estadoPago.idEstadoPago eq estadoPagoVencido}">
											<c:set var="cssClassEdoPago" value="label-danger" />
										</c:when>
									</c:choose> <span class="label ${cssClassEdoPago}">${pago.estadoPago.descripcion}</span>
								</td>
								<c:if
									test="${today.time lt (pago.fechaLimitePago.time + 86400000) and (pago.imprimible or (pago.estadoPago.idEstadoPago ne estadoPagoActivo and pago.estadoPago.idEstadoPago ne estadoPagoVencido))}">
									<td class="text-center"><a class="link print"
										onclick="imprimePago(${pago.idPago})" title="Descargar"> <i
											class="glyphicon glyphicon-download-alt fa-2x"></i>
									</a></td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</c:if>

	<div class="alert alert-success alert-block m-t-lg">
		<strong>&#161;Felicidades&#33;, te invitamos a conocer <a
			href="http://checatemidetemuevete.gob.mx/" class="alert-link"
			target="_blank"> Ch&eacute;cate, M&iacute;dete, Mu&eacute;vete. </a>
		</strong>
	</div>

	<fmt:setLocale value="${defaultLocale}" scope="session" />
	<div class="pie row">
		<div class="opciones col-sm-6">

			<a id="verComprobante" class="btn btn-primary" data-id="${ID_CIFRADO}"
				onclick="imprSegPerIvro(this); uid_call('imss.gestion.seguro.voluntario.mod40.detalleSeguro.btn_comprobante', 'download');">
				Obtener Comprobante</a>
<%-- 			<c:if test="${not empty jsonInfoPago}">
				<a class="btn btn-primary" onclick="enviaMultipago();">Pagar</a>
			</c:if> --%>
		</div>
		<div class="controles col-sm-6 text-right">
			<button class="btn btn-default" id="cerrarWizard">Cerrar</button>
			<a id="regresar" class="btn btn-default"> <i
				class="glyphicon glyphicon-step-backward"></i> Regresar
			</a>
		</div>
	</div>
</div>


<form
	action="${contextpath}/wizard/seguroFamiliar/lista/${seguro.titular.idPersona}"
	id="regresarForm"></form>

<%-- <form id="envia_info_multipago" action="http://11.254.13.100:7001/multipagos/inicioMultipago" method="POST"
	target="_blank">
	<input type="hidden" id="infoPagoJSON" name="infoPagoJSON" value='${jsonInfoPago}'/>
</form> --%>

<iframe id="auxIframe" name="auxIframe" style="display: none"></iframe>