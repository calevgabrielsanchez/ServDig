<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/seguroDomestico/renovacion/listaTrabajadores.js" htmlEscape="true" />"></script>

<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<script type="text/javascript">
	tipoOperacion = '${tipoOperacion}';
	ventanilla = ${esVentanilla};
</script>

<style>
	.table-action {
		margin: 4px;
		padding: 3px 5px;
		border-radius: 2px;
		background-color: #4dacda;
		color: #ffffff;
		cursor: pointer;
	}

	.custom-btn-group > button {
		padding-left: 5px;
		padding-right: 5px;
	}
</style>

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12">
			<c:set var="defaultLocale" value="${pageContext.request.locale}"/>
			<fmt:setLocale value="es_MX" scope="session"/>
			<div class="titulo separadorseccion">
				<span>Trabajadores del seguro</span>
			</div>
			<c:choose>
				<c:when test="${fn:length(tramites) == 0}">
					<p>No hay trabajadores asociados a la solicitud</p>
				</c:when>
				<c:otherwise>
					<p>Lista de trabajadores asociados al seguro</p>
					<table id="tblTrabajadores" style="margin: 20px 0px; width:100%;" class="table table-striped table-bordered table-word-wrap-fixed"
						cellpadding="0" cellspacing="0" border="0">
						<thead>
							<tr>
								<th>NSS</th>
								<th>Nombre</th>
								<th>Salario diario</th>
								<th>Costo anual</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:set var="total" value="0"/>
							<c:forEach items="${tramites}" var="tramite" varStatus="status">
								<tr>
									<td>${tramite.beneficiarios[0].nss}</td>
									<td>${tramite.beneficiarios[0].nombre}</td>
									<td><fmt:formatNumber value="${tramite.cotizacion.detalle.empleados[0].salario}" type="currency"/></td>
									<td><fmt:formatNumber value="${tramite.cotizacion.cuotaTotal}" type="currency"/></td>
									<td>
										<div class="btn-group custom-btn-group">
											<button class="btn btn-default btn-xs">Acciones</button>
											<a class="btn btn-default btn-xs dropdown-toggle"
																data-toggle="dropdown" href="#"><span class="caret" ></span></a>
											<ul class="dropdown-menu pull-right">
												<li index="${status.index}" class="linkActualizarSalario">
													<a href="#">Actualizar Salario</a>
												<li>
											</ul>
										</div>
									</td>
								</tr>
								<c:set var="total" value="${total + tramite.cotizacion.cuotaTotal}"/>
							</c:forEach>
						</tbody>
					</table>
					<table style="width:100%;"
						cellpadding="0" cellspacing="0" border="0">
						<thead>
							<tr>
								<td style="width:55%; border:1px solid transparent; padding:8px"></td>
								<td style="width:15%; border:1px solid transparent; padding:8px; font-size:13px; font-weight:bold;">Costo total</td>
								<td style="width:15%; border:1px solid transparent; padding:8px; font-size:13px; font-weight:bold;"><fmt:formatNumber value="${total}" type="currency"/></td>
								<td style="width:15%; border:1px solid transparent; padding:8px"></td>
							</tr>
						</thead>
					</table>
				</c:otherwise>
			</c:choose>
			<form:form id="actualizarSalarioForm" action="${contextPath}/wizard/seguroDomestico/renovacion/actualizarSalario">
				<input id="inputIndex" name="inputIndex" type="hidden"/>
			</form:form>
			<form:form id="nextStepForm" action="${contextPath}/wizard/seguroDomestico/comunes/resumen">
			</form:form>
			<fmt:setLocale value="${defaultLocale}" scope="session"/>
		</div>
	</div>
	<div class="pie row">
		<div class="opciones col-sm-6">
		</div>
		<div class="controles col-sm-6">
			<div class="pull-right">
				<button id="cancelarSolicitudRenovacion" class="btn btn-default">
					<c:choose>
						<c:when test="${esVentanilla and not empty segurosDomesticos}">Regresar</c:when>
						<c:otherwise>Cerrar</c:otherwise>
					</c:choose>
				</button>
				<c:if test="${fn:length(tramites) > 0}">
					<a id="siguientePaso" class="btn btn-primary">
						<i class="glyphicon glyphicon-step-forward"></i>
						Siguiente
					</a>
				</c:if>
			</div>
		</div>
	</div>
</div>