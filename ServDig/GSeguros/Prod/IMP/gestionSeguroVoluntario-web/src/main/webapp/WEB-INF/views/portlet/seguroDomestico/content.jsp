<!-- JSP Contenido del portlet Example -->
<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/portlet/seguroDomestico/portletSeguroDomestico.js" htmlEscape="true" />"></script>
<c:if test="${esVentanilla}">
	<script type="text/javascript"
		src="<spring:url value="/static/resources/js/wizard/seguroDomestico/comunes/common.js" htmlEscape="true" />"></script>
	<script type="text/javascript">
		<!--
		ventanilla = ${esVentanilla};
		-->
	</script>
</c:if>
<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<style type="text/css">
	.table-action {
		margin: 4px;
		padding: 3px 5px;
		border-radius: 2px;
		background-color: #4dacda;
		color: #ffffff;
		cursor: pointer;
	}
	
	.btn-group {
        display: flex;
	}
</style>

<c:if test="${not empty esVentanilla && esVentanilla}">
	<c:set var="ccsContainer" value="col-sm-12" />
</c:if>


<div class="${ccsContainer}">
    <jsp:include page="../../wizard/seguroDomestico/comunes/pasosDomestico.jsp">
        <jsp:param name="paso" value="5" />
    </jsp:include>
    <br/>
	<p>
		<spring:message code="label.portlet.descripcion.seguroDomestico" />
	</p>
	<table id="tablaSeguros" style="width: 100%;" class="table table-striped table-bordered table-word-wrap-fixed"
		cellpadding="0" cellspacing="0" border="0">
		<thead>
			<tr>
				<th>Nombre del asegurado</th>
				<th>Estado</th>
				<!-- Preguntar si es renovacion -->
				<th>Tipo de pago</th>
				<th>Periodo de aseguramiento</th>
				<%--TODO Habilitar renovacion
				<th>Periodo de renovaci&oacute;n</th>
				--%>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${segurosDomesticos.seguroIvro}" var="seguro">
				<tr>
					<td>${seguro.tramite.beneficiarios[0].nombreCompleto}</td>
					<td class="text-center">
						<c:choose>
							<c:when test="${seguro.estadoSeguro.idEstadoSeguro == 1}">
								<span class="label label-warning label-imss label-warning-imss">
							</c:when>
							<c:when test="${seguro.estadoSeguro.idEstadoSeguro == 3}">
								<span class="label label-danger label-imss label-danger-imss">
							</c:when>
							<c:when test="${seguro.estadoSeguro.idEstadoSeguro == 4}">
								<span class="label label-danger label-imss label-danger-imss">
							</c:when>
							<c:otherwise>
								<span class="label label-success label-imss label-success-imss">
							</c:otherwise>
						</c:choose>
						${seguro.estadoSeguro.descripcion}
						</span>
					</td>
					<!-- Preguntar si es renovacion -->
					<td class="text-center"><c:choose>
							<c:when test="${seguro.compra.formaPago == 1}">
								ANUAL
							</c:when>
							<c:when test="${seguro.compra.formaPago == 2}">
								BIMESTRAL
							</c:when>
							<c:otherwise>
															
							</c:otherwise>
						</c:choose>
					</td>
					<td><c:out value="${mapaFechaAseguamiento[seguro.cveIdSeguroIvro]}"/></td>
					<%-- TODO Habilitar renovacion
					<td><c:out value="${mapaFechaRenovacon[seguro.cveIdSeguroIvro]}"/></td>--%>
					<td>
						<div class="btn-group custom-btn-group">
							<button class="btn btn-primary btn-xs">Acciones</button>
							<a class="btn btn-primary btn-xs dropdown-toggle" data-toggle="dropdown" href="#">
								<span class="caret"></span>
							</a>
							<ul class="dropdown-menu pull-right">
								<li idSeguro="${seguro.cveIdSeguroIvro}" class="link-detalle">
									<a href="#">Ver detalle</a>
								</li>
								<c:if test="${seguro.enRenovacion}">
									<li idSeguro="${seguro.cveIdSeguroIvro}" nssTrabajador="${seguro.tramite.beneficiarios[0].nss}" class="link-renovacion">
										<a href="#">Renovar</a>
									</li>
								</c:if>
							</ul>
						</div>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<script type="text/javascript">
	<!--
	$('#tablaSeguros').dataTable({
		'bFilter': false,
		'bDestroy': true,
		'bLengthChange': false,
		'bAutoWidth': false,
		'bInfo': false,
		'sPaginationType': 'bootstrap',
		'aoColumnDefs': [{'sSortDataType': 'html', 'sType': 'html', 'aTargets': [0]},
		                 {'bSortable': false, 'aTargets': [2]}],
		'aoColumns' : [{'sWidth': '60%'},
	                   {'sWidth': '20%'},
	                   {'sWidth': '20%'}]
		}	
	);
	//-->
</script>
	<c:if test="${not empty esVentanilla && esVentanilla}">
		<br />
		<div style="display: block; clear: both;">
			<div style="float: left;">
				<div class="btn-group">
					<button type="button" class="btn btn-primary">Acciones</button>
					<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
						<span class="caret"></span>
					</button>
					<ul class="dropdown-menu">
						<li>
							<a id="btnComprarSeguroDomestico" href="#">Incorporaci&oacute;n Voluntaria de Trabajador Dom&eacute;stico</a>
						</li>
					</ul>
				</div>
			</div>
			<div style="float: right;">
				<button id="btnCancelarSolicitudVentanilla" class="btn btn-default">Cerrar</button>
			</div>
		</div>
		<form:form id="comprarSeguroDomesticoForm" method="get"
			action="${contextPath}/wizard/seguroDomestico/alta/init/${empleador.idPersona}/${empleador.rfc}/${empleador.nssCifrado}">
		</form:form>
		<form:form id="verDetalleSeguroDomesticoForm" method="post">
			<input type="hidden" name="idPersona" value="${empleador.idPersona}" />
			<input type="hidden" name="rfc" value="${empleador.rfc}" />
			<input type="hidden" name="nssCifrado" value="${empleador.nssCifrado}" />
		</form:form>
		<script type="text/javascript">
			$('#btnComprarSeguroDomestico').click(function(event) {
				event.preventDefault();
				ivroPortlet.mostrarAltaSeguroDomesticoVentanillaWizard(${empleador.idPersona}, '${empleador.rfc}', '${correoDomestico}');
			});
			$('#btnCancelarSolicitudVentanilla').click(function() {
				closeWizard();
			});
		</script>
	
	</c:if>

	<form:form id="detalle">
	</form:form>
</div>