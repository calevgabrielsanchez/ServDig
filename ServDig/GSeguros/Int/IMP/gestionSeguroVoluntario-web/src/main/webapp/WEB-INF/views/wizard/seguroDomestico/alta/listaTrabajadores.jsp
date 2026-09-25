<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/seguroDomestico/alta/listaTrabajadores.js" htmlEscape="true" />"></script>

<c:set var="contextPath" value="<%=request.getContextPath()%>" />

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
<script type="text/javascript">
	<!--
	ventanilla = ${esVentanilla};
	var tieneSeguros = ${tieneSeguros};
	-->
</script>
<div class="contenedor col-sm-12">
	<div class="contenido row">
		<jsp:include page="../comunes/pasosDomestico.jsp">
			<jsp:param name="paso" value="3" />
		</jsp:include>
		<div class="col-sm-12">
			<c:set var="defaultLocale" value="${pageContext.request.locale}"/>
			<fmt:setLocale value="es_MX" scope="session"/>
			<c:if test="${not empty error}">
				<div class="alert alert-danger">
					<button type="button" class="close" data-dismiss="alert">&#215;</button>
					<span>${error}</span>
				</div>
			</c:if>
			<div class="titulo separadorseccion">
				<span>Trabajadores del seguro</span>
			</div>
			<c:choose>
				<c:when test="${fn:length(tramites) == 0}">
					<p>Agrega a los trabajadores que deseas asegurar</p>
				</c:when>
				<c:otherwise>
					<p>Lista de trabajadores a asegurar</p>
					<table id="tblTrabajadores" style="margin: 20px 0px; width:100%;" class="table table-striped table-bordered table-word-wrap-fixed"
						cellpadding="0" cellspacing="0" border="0">
						<thead>
							<tr>
								<th>NSS</th>
								<th>Nombre</th>
								<th>Salario diario</th>
								<c:choose>
									<c:when test="${recargos}">
										<th>Parcialidad a Pagar</th>
									</c:when>
				                	<c:otherwise>
				                		<th>Costo anual</th>
				                	</c:otherwise>
								</c:choose>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:set var="total" value="0"/>							
							<c:forEach items="${tramites}" var="tramite" varStatus="status">
								<c:choose>									
									<c:when test="${recargos}">
										<c:set var="costoEmpleado" value="${tramite.cotizacion.detalle.empleados[0].periodos[0].total}" />
										<c:set var="montoCuotaRecargo" value="${tramite.cotizacion.detalle.empleados[0].periodos[0].cuotaRecargo}"/>
									</c:when>
									<c:otherwise>
										<c:set var="costoEmpleado" value="${tramite.cotizacion.cuotaTotal}" />
										<c:set var="montoCuotaRecargo" value="0"/>
									</c:otherwise>
								</c:choose>	
								<c:set var="montoCuotaSinRecargo" value="${costoEmpleado - montoCuotaRecargo}" />
								<tr>
									<td>${tramite.beneficiarios[0].nss}</td>
									<td>${tramite.beneficiarios[0].nombre}</td>
									<td><fmt:formatNumber value="${tramite.cotizacion.detalle.empleados[0].salario}" type="currency"/></td>
									<td><fmt:formatNumber value="${costoEmpleado}" type="currency"/></td>
									<td>
										<div class="btn-group custom-btn-group">
											<button class="btn btn-default btn-xs">Acciones</button>
											<a class="btn btn-default btn-xs dropdown-toggle"
																data-toggle="dropdown" href="#"><span class="caret" ></span></a>
											<ul class="dropdown-menu pull-right">
												<li id="${status.index}" class="linkQuitar">
													<a href="#">Eliminar</a>
												<li>
											</ul>
										</div>
									</td>
								</tr>
								<c:choose>
									<c:when test="${recargos}">									
										<c:set var="total" value="${total + costoEmpleado}"/>
									</c:when>
									<c:otherwise>									
										<c:set var="total" value="${total + costoEmpleado}"/>
									</c:otherwise>
								</c:choose>
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
			<form:form id="agregarTrabajadorForm" action="${contextPath}/wizard/seguroDomestico/alta/agregarTrabajador">
			</form:form>
			<form:form id="quitarTrabajadorForm" action="${contextPath}/wizard/seguroDomestico/alta/quitarTrabajador">
				<input id="inputIndex" name="inputIndex" type="hidden"/>
			</form:form>
			<form:form id="nextStepForm" action="${contextPath}/wizard/seguroDomestico/comunes/resumen">
			</form:form>
			<fmt:setLocale value="${defaultLocale}" scope="session"/>
		</div>
	</div>
	<div class="pie row">
		<div class="opciones col-sm-6">
			<c:if test="${fn:length(tramites) < 25}">
				<div class="btn-group">
					<button type="button" class="btn btn-primary">Acciones</button>
					<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
						<span class="caret"></span>
					</button>
					<ul class="dropdown-menu">
						<li>
							<a id="agregarTrabajador" href="#">
								<i class="glyphicon glyphicon-plus-sign"></i>
								Agregar trabajador
							</a>
						</li>
					</ul>
				</div>
			</c:if>
		</div>
		<div class="controles col-sm-6">
			<div class="pull-right">
				<button id="cancelarSolicitudAlta" class="btn btn-default">
					<c:choose>
						<c:when test="${esVentanilla and tieneSeguros}">Regresar</c:when>
						<c:otherwise>Cerrar</c:otherwise>
					</c:choose>
				</button>
				<c:if test="${fn:length(tramites) > 0}">
					<a id="siguientePaso" class="btn btn-primary"><i class="glyphicon glyphicon-step-forward"></i> Siguiente</a>
				</c:if>
			</div>
		</div>
	</div>
</div>
