<%@ include file="../../../general/taglibs.jsp"%>

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
		src="<spring:url value="/static/resources/js/wizard/mod-40/comunes/detalle-pago.js" htmlEscape="true" />"></script>

<div class="contenedor col-sm-12 m-t-sm">
	<div id="errorNegocio"></div>
	<c:set var="defaultLocale" value="${pageContext.request.locale}" />
	<fmt:setLocale value="es_MX" scope="session" />

	<c:if test="${pagos != null}">


		<div id="pagos" class="m-t-xl">
			<div class="titulo">
				<span>Lista de Pagos</span>
				<hr class="red m-b-none">
			</div>

			<div id="tblSegurosWrapper" class="table-responsive">
				<table id="tblIvroSegurosIndivResume"
					   class="table table-striped table-bordered">
					<thead>
					<tr>
						<th>Fecha inicio</th>
						<th>Fecha fin</th>
						<th>Monto</th>
						<th>Fecha de aviso de pago</th>
                        <th>Estado del pago</th>
						<th>Folio</th>
						<th>Estado del seguro</th>
						<th>Salario diario</th>
						<th>Origen</th>
                        <th>Días</th>
					</tr>
					</thead>
					<tbody><c:forEach items="${pagos}" var="pago">
						<tr>
							<td><fmt:formatDate pattern="dd/MM/yyyy" value="${pago.fecInicioPeriodo}" /></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy" value="${pago.fecFinPeriodo}" /></td>
							<td><fmt:formatNumber value="${pago.monto}" type="currency" /></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy" value="${pago.fecAvisoPago}" /></td>
                            <td>${pago.desEstadoPago}</td>
                            <td>${pago.refFolio}</td>
                            <td>${pago.desEstadoSeguro}</td>
                            <td><fmt:formatNumber value="${pago.salarioDiario}" type="currency" /></td>
                            <td>${pago.origen}</td>
                            <td>${pago.numDias}</td>

						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</c:if>

	<div class="pie row">
		<div class="opciones col-sm-12 text-right">
            <button class="btn btn-default" id="cerrarWizard">Cerrar</button>
            <a id="regresar" class="btn btn-default"> <i
                    class="glyphicon glyphicon-step-backward"></i> Regresar
            </a>
		</div>
	</div>
	<fmt:setLocale value="${defaultLocale}" scope="session" />
</div>
