<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/seguroDomestico/comunes/cotizacionTrabajador.js" htmlEscape="true" />"></script>

<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<style>
	div.form-group label {
	    font-weight: inherit;
	}
</style>

<script type="text/javascript">
	tipoOperacion = '${tipoOperacion}';
	ventanilla = ${esVentanilla};
</script>

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12 form-horizontal">
			<c:set var="defaultLocale" value="${pageContext.request.locale}"/>
			<fmt:setLocale value="es_MX" scope="session"/>
			<div class="titulo separadorseccion">
				<span>Datos de la Incorporaci&oacute;n voluntaria de trabajador(es) dom&eacute;stico(s)</span>
			</div>
			<p>Información del seguro a contratar para el trabajador solicitado</p>
			<div class="form-group">
				<label class="col-sm-4" style="text-align: left;">
					<span><strong>Fecha Inicio Vigencia:</strong></span>
				    <span><fmt:formatDate value="${cotizacionEmpleado.detalle.fechaInicioCalculo.time}" pattern="dd/MM/yyyy"/></span>
	            	</label>
	            	<label class="col-sm-4" style="text-align: left;">
	                	<span><strong>Fecha Fin Vigencia:</strong></span>
	                	<c:choose>
		                	<c:when test="${recargos}">
		                		<span><fmt:formatDate value="${cotizacionEmpleado.detalle.empleados[0].periodos[0].finPeriodo.time}" pattern="dd/MM/yyyy"/></span>
		                	</c:when>
		                	<c:otherwise>
		                		<span><fmt:formatDate value="${cotizacionEmpleado.detalle.fechaFinCalculo.time}" pattern="dd/MM/yyyy"/></span>
		                	</c:otherwise>
	                	</c:choose>
					</label>
				<c:if test="${not recargos}">
					<div class="col-sm-2">
						<label >
							<span><strong>Costo:</strong></span>
						</label>
					</div>
					<div class="col-sm-1" style="text-align: right;">
					<c:choose>
						<c:when test="${recargos}">
							<span><strong><fmt:formatNumber value="${cotizacionEmpleado.detalle.empleados[0].periodos[0].total}" type="currency"/></strong></span>
						</c:when>
						<c:otherwise>
							<span><strong><fmt:formatNumber value="${cotizacionEmpleado.cuotaTotal}" type="currency"/></strong></span>
						</c:otherwise>
					</c:choose>
					</div>
	                   <div class="col-sm-1" style="text-align: right;"></div>
				</c:if>
			</div>
			<div class="separadorseccion">
				<span>Trabajador</span>
			</div>
			<div class="form-group">
				<div class="col-sm-12">
					<span><strong>Nombre: </strong></span>
					<span>${cotizacionEmpleado.detalle.empleados[0].nombreTrabajador}</span>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-12">
					<span><strong>Zona salarial: </strong></span>
					<span>${cotizacionEmpleado.detalle.zonaSalarial}</span>
				</div>
			</div>
	
			<c:if test="${recargos}">
				<div class="separadorseccion">
					<span>Periodos de aseguramiento</span>
				</div>
				<table id="amortizacionesSeguro" style="width:100%;" class="table table-striped table-bordered table-word-wrap-fixed">
					<thead>
						<tr>
							<th>Fecha inicio</th>
							<th>Fecha fin</th>
							<th>Recargos</th>
							<th>Monto</th>
						</tr>
					</thead>
					<tbody>
						<c:set var="total" value="0"/>
						<!-- c:forEach items="${cotizacionEmpleado.detalle.empleados[0].periodos}" var="periodo" -->
							<tr>
								<td><fmt:formatDate value="${cotizacionEmpleado.detalle.empleados[0].periodos[0].inicioPeriodo.time}" pattern="dd/MM/yyyy"/></td>
								<td><fmt:formatDate value="${cotizacionEmpleado.detalle.empleados[0].periodos[0].finPeriodo.time}" pattern="dd/MM/yyyy"/></td>
								<td><fmt:formatNumber value="${cotizacionEmpleado.detalle.empleados[0].periodos[0].cuotaRecargo}" type="currency"/></td>
								<td><fmt:formatNumber value="${cotizacionEmpleado.detalle.empleados[0].periodos[0].total}" type="currency"/></td>
							</tr>
							<c:set var="total" value="${total + cotizacionEmpleado.detalle.empleados[0].periodos[0].total}"/>
						<!--   /c:forEach -->
					</tbody>
				</table>
				<table style="width:100%;"
					cellpadding="0" cellspacing="0" border="0">
					<thead>
						<tr>
							<td style="width:50%; border:1px solid transparent; padding:8px"></td>
							<td style="width:25%; border:1px solid transparent; padding:8px; font-size:13px; font-weight:bold;">Costo total</td>
							<td style="width:25%; border:1px solid transparent; padding:8px; font-size:13px; font-weight:bold;"><fmt:formatNumber value="${total}" type="currency"/></td>
						</tr>
					</thead>
				</table>
			</c:if>
			<form:form id="nextStepForm"
				cssClass="form-horizontal"
				cssStyle="margin: 20px 0px"
				action="${contextPath}/wizard/seguroDomestico/${nextAction}">
				<input name="inputIndex" type="hidden" value="${inputIndex}"/>
			</form:form>
			<c:choose>
				<c:when test="${empty tipoOperacion}">
					<form:form id="cancelarCotizacion" action="${contextPath}/wizard/seguroDomestico/alta/listaTrabajadores">
					</form:form>
				</c:when>
				<c:otherwise>
					<form:form id="cancelarCotizacion" action="${contextPath}/wizard/seguroDomestico/renovacion/listaTrabajadores">
					</form:form>
				</c:otherwise>
			</c:choose>
			<fmt:setLocale value="${defaultLocale}" scope="session"/>
		</div>
	</div>
	<div class="pie row">
		<div class="opciones col-sm-6">
		</div>
		<div class="controles col-sm-6">
			<div class="pull-right">
				<button id="cancelarTramite" class="btn btn-default">Cancelar</button>
				<a id="siguientePaso" class="btn btn-primary"><i class="glyphicon glyphicon-step-forward"></i> Siguiente</a>
			</div>
		</div>
	</div>
</div>
