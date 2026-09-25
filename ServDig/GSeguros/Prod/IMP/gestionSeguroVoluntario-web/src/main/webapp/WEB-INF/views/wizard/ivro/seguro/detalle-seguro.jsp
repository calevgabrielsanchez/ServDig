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
	#tblIvroSegurosIndivResume .label {
	    font-size: 10px;
	}
	
	#tblIvroSegurosIndivResume td.center {
		text-align: center;
	}
</style>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/persona/ivro/detalle/detalle-seguro.js" htmlEscape="true" />"></script>
<script type="text/javascript">
	$(document).ready(function() {
	 	idSeguro = '<c:out value="${seguro.cveIdSeguroIvro}"/>';
	 	enviarComprobanteYLineaCapturaRenvacion(idSeguro);
	});
</script>
<!-- <script type="text/javascript">

function enviaMultipago(){
	document.getElementById("envia_info_multipago").submit();
	}
</script> -->
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.EstadoPagoEnum"%>

<c:set var="today" value="<%=new java.util.Date()%>" />
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="codigoTrabajadorUrbano" value="<%=ModalidadEnum.TREINTAYCUATRO.getId()%>" />

<c:set var="estadoPagoPendiente" value="<%=EstadoPagoEnum.POR_PAGAR.getId()%>" />
<c:set var="estadoPagoActivo" value="<%=EstadoPagoEnum.PAGADO.getId()%>" />
<c:set var="estadoPagoVencido" value="<%=EstadoPagoEnum.VENCIDO.getId()%>" />

<c:set var="modalidad44Renovar"
	value="<%=ModalidadEnum.CUARENTAYCUATRO.getNumModalidad()%>" />
<c:set var="modalidad43Renovar"
	value="<%=ModalidadEnum.CUARENTAYTRES.getNumModalidad()%>" />
<c:set var="modalidad35Renovar"
	value="<%=ModalidadEnum.TREINTAYCINCO.getNumModalidad()%>" />

<c:set var="staticResourcesPath" value='<%=request.getSession().getServletContext().getInitParameter("STATIC_RESOURCES_PATH")%>' />
					

<script type="text/javascript">
<!--
	var domestico = '${domestico}'
//-->

</script>

<div class="contenedor col-sm-12">
	<div class="contenido">

		<div id="errorNegocio"></div>
		
		<c:set var="defaultLocale" value="${pageContext.request.locale}" />
		<fmt:setLocale value="es_MX" scope="session" />
		
		<c:if test="${
			(seguro.modalidad.numModalidad == 35 ||
			seguro.modalidad.numModalidad == 43 ||
			seguro.modalidad.numModalidad == 44) and (not esRenovacion and not esExtemporanea)
		}">
			<jsp:include page="../individual/pasosIndividual.jsp">
				<jsp:param name="paso" value="6" />
			</jsp:include>
		</c:if>
		<div class="separadorseccion">
			<h5>Datos del Seguro</h5>
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
				<strong>Tipo seguro:</strong>
			</div>
			<div class="col-xs-2">
				<c:if test="${domestico}">
        Dom&eacute;stico
      </c:if>
				<c:if test="${!domestico}">
        Individual
      </c:if>
			</div>
			<div class="col-xs-2">
				<strong>Modalidad:</strong>
			</div>
			<div class="col-xs-6">${seguro.modalidad.numModalidad}-
				${seguro.modalidad.descripcion}</div>
		</div>
		<br>
		<div class="row">
			<div class="col-xs-2">
				<strong>Fecha inicio vigencia:</strong>
			</div>
			<div class="col-xs-2">
				<fmt:formatDate pattern="dd/MM/yyyy" value="${seguro.fechaInicio}" />
			</div>
			<div class="col-xs-2">
				<strong>Fecha fin vigencia:</strong>
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

		<c:if test="${seguro.tramite != null}">

			<c:if test="${domestico}">
				<br>
				<div class="separadorseccion">
					<h5>Empleados</h5>
				</div>
				<div id="tblBeneficiarioWrapper"
					style="width: 100%; margin: 0 auto;">
					<table id="tblBeneficiarioResume" style="width: 100%;"
						class="table table-striped table-bordered" cellpadding="0"
						cellspacing="0" border="0">
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


			</c:if>
			<br>
			<div>
				<div class="alert alert-info">
					<strong>Recuerda imprimir tus l&iacute;neas de captura y realizar el pago correspondiente antes de la fecha de fin de vigencia de cada l&iacute;nea. Recuerda que el incumplimiento en el pago de tus l&iacute;neas de captura en tiempo y forma es motivo de cancelaci&oacute;n de tus servicios m&eacute;dicos.</strong>
				</div>
			</div>
			<div class="separadorseccion">
				<h5>Pagos</h5>
			</div>
			<div id="tblSegurosWrapper" style="width: 100%; margin: 0 auto;">
				<table id="tblIvroSegurosIndivResume" style="width: 100%;"
					class="table table-striped table-bordered" cellpadding="0"
					cellspacing="0" border="0">
					<thead>
						<tr>
							<th>Fecha inicio</th>
							<th>Fecha fin</th>
							<th>Monto</th>
							<th>Fecha limite pago</th>
							<th>Estatus</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
					  <c:forEach items="${seguro.compra.pagos}" var="pago">
                        <%-- <c:if test="${(not pago.conBeneficio) or (pago.conBeneficio and (pago.imprimible or pago.estadoPago.idEstadoPago == 2))}"> --%>      
							<tr>
								<td><fmt:formatDate pattern="dd/MM/yyyy"
										value="${pago.fechaInicioPeriodo}" /></td>
								<td><fmt:formatDate pattern="dd/MM/yyyy"
									value="${pago.fechaFinPeriodo}" /></td>
								<td><fmt:formatNumber value="${pago.monto}" type="currency" /></td>
								<td><fmt:formatDate pattern="dd/MM/yyyy"
										value="${pago.fechaLimitePago}" /></td>
								<td class="center">
									<c:choose>
										<c:when test="${pago.estadoPago.idEstadoPago eq estadoPagoPendiente}">
											<c:set var="cssClassEdoPago" value="label-warning" />
										</c:when>
										<c:when test="${pago.estadoPago.idEstadoPago eq estadoPagoActivo}">
											<c:set var="cssClassEdoPago" value="label-success" />
										</c:when>
										<c:when test="${pago.estadoPago.idEstadoPago eq estadoPagoVencido}">
											<c:set var="cssClassEdoPago" value="label-danger" />
										</c:when>
									</c:choose>
									<span class="label ${cssClassEdoPago}">${pago.estadoPago.descripcion}</span>
								</td>
								<td class="center">
									<c:if test="${seguro.tramite.renovacion}">
										<c:if
											test="${today.time lt (pago.fechaLimitePago.time + 86400000) and (pago.imprimible and (pago.estadoPago.idEstadoPago ne estadoPagoActivo and pago.estadoPago.idEstadoPago ne estadoPagoVencido))}">
										<a class="link print" onclick="imprimePago(${pago.idPago})" title="Imprimir">
											<i class="icon-printing fa-2x"></i>
										</a>
									</c:if>
									</c:if>
									<c:if test="${not seguro.tramite.renovacion}">
										<c:if
											test="${today.time lt (pago.fechaLimitePago.time + 86400000) and (pago.imprimible and (pago.estadoPago.idEstadoPago ne estadoPagoActivo and pago.estadoPago.idEstadoPago ne estadoPagoVencido))}">
											<a class="link print" onclick="imprimePago(${pago.idPago})" title="Imprimir">
											<i class="icon-printing fa-2x"></i>
											</a>
										</c:if>
									</c:if>
								</td>
							</tr>

                      </c:forEach>
					</tbody>
				</table>
			</div>

		</c:if>
		<div class="pie">
			<div style="width: 100%">
				<c:if test="${seguro.tramite != null}">
					<div style="float: left">
						<div class="btn-group dropup">
							<a href="#" class="btn btn-primary">Acciones </a> <a href="#"
								data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><span
								class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a id="verComprobante"
									onclick="imprSegPerIvro(${seguro.cveIdSeguroIvro})">Imprimir
										Comprobante</a></li>
								<!--<c:if
									test="${seguro.tramite.aplicaCuestionario != null and seguro.tramite.aplicaCuestionario}">
									<li><a id="verCuestionario"
										onclick="imprCuestionarioIvro(${seguro.cveIdSeguroIvro})">Imprimir
											Cuestionario</a></li>
								</c:if>-->
							</ul>
						</div>
					</div>
				</c:if>
			</div>
<%-- 			<div class="opciones col-sm-6">
				<c:if test="${not empty jsonInfoPago}">
					<a class="btn btn-primary" onclick="enviaMultipago();">Pagar</a>
				</c:if>
			</div> --%>
			<div style="float: right">
				<c:choose>
					<c:when test="${ventanilla}">
						<c:if test="${not empty domestico and domestico}">
							<form:form id="__backMainVentanilla" method="get"
								action="${contextpath}/wizard/seguroDomestico/ventanilla/init/${idPersona}/${rfc}/${nssCifrado}">
							</form:form>
							<button class="btn btn-default"
								id="backWizardVentanillaDomestico">Regresar</button>
						</c:if>
                       <c:if test="${esRenovacion and not esExtemporanea and (seguro.modalidad.numModalidad == modalidad44Renovar or seguro.modalidad.numModalidad == modalidad43Renovar or seguro.modalidad.numModalidad == modalidad35Renovar)}">
                            <button class="btn btn-primary" id="btnRenovarIvro">Renovar</button>
						</c:if>
						<c:if test="${esExtemporanea and (seguro.modalidad.numModalidad == modalidad44Renovar or seguro.modalidad.numModalidad == modalidad43Renovar or seguro.modalidad.numModalidad == modalidad35Renovar)}">
							<button class="btn btn-primary" id="btnExtemporaneoIvro">Renovar</button>
						</c:if>
                        <c:if test="${(seguro.modalidad.numModalidad != modalidad44Renovar and seguro.modalidad.numModalidad != modalidad43Renovar and seguro.modalidad.numModalidad != modalidad35Renovar)}">
							<button class="btn btn-primary" id="btnRenovarInhabilitadoIvro">Renovar</button>
                        </c:if>
						<button class="btn btn-default" id="cerrarWizardVentanilla">Cerrar</button>
					</c:when>
					<c:otherwise>
						<button class="btn btn-default" id="cerrarWizard">Cerrar</button>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="controles"></div>
		</div>
	</div>
	<fmt:setLocale value="${defaultLocale}" scope="session" />
</div>
<%-- <form id="envia_info_multipago" action="http://11.254.13.100:7001/multipagos/inicioMultipago" method="POST"
	target="_blank">
	<input type="hidden" id="infoPagoJSON" name="infoPagoJSON" value='${jsonInfoPago}'/>
</form> --%>
