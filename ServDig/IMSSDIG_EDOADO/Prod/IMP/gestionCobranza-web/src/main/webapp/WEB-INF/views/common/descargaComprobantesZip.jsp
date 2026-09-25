<!-- JSP Contenido del Widget de Comprobante Fiscal. -->
<%@ include file="../general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<style>
.site_position_center {
	width: 100% !important;
}

ul#icons li {
	cursor: pointer;
	float: left;
	list-style: none outside none;
	margin: 2px;
	padding: 4px;
	position: relative;
}

.contenedor {
	height: auto;
}
</style>

<script>
	var cobContextpath = '${contextpath}';
	$(document)
			.ready(
					function() {
						var gridSolicitud = $('#tblSolicitudesResumen')
								.dataTable(
										{
											"sPaginationType" : "bootstrap-full",
											"oLanguage" : {
												"sZeroRecords" : "<center><strong style=\"font-size: small;\">Sin información que mostrar</strong></center>"
											},
											"bLengthChange" : false,
											"bFilter" : false,
											"bProcessing" : false,
											"iDeferLoading" : 0,
											"bSort" : false,
										});
						
						$('#generarButton').click(function(){
							
							if (($("input[name*='comprobantes']:checked").length)<=0) {
						        alert("Seleccione al menos un comprobante a descargar");
						    }else{
								var urlAction = cobContextpath
									+ '/edoadeudo/obtener/comprobanteFiscal/descargarFacturas';
								$.unblockUI();
								document.getElementById('comprobantes').action = urlAction;
								document.getElementById('comprobantes').submit();
							}
						});
					});


	function descargarFacturas() {
		var urlAction = cobContextpath
				+ '/edoadeudo/obtener/comprobanteFiscal/descargarFacturas';
		$.unblockUI();
		document.getElementById('comprobantes').action = urlAction;
		document.getElementById('comprobantes').submit();
	}
	
	
</script>

<c:choose>
	<c:when test="${empty errorFormGeneral}">
		<ul>
			<li>
				<p>A continuaci&oacute;n seleccione los comprobantes que desea
					descargar:</p>
			</li>
		</ul>
		<div id="tblComprobantesFiscalesWrapper"
			style="width: 100%; margin: 0 auto;">
			<form:form method="POST" commandname="comprobantes" 
				action="/edoadeudo/obtener/comprobanteFiscal/descargarFacturas">  
				
				<form:hidden path="periodo" value="${periodo}"/>
				<form:hidden path="rfc" value="${rfc}" />
				<form:hidden path="numeroRegistroPatronal" value="${numeroRegistroPatronal}" />
				
				<table id="tblSolicitudesResumen" style="width: 100%;"
					class="table table-striped table-bordered" cellpadding="0"
					cellspacing="0" border="0">
					<thead>
						<tr>
							<th>N&uacute;mero de Registro Patronal</th>
							<th>RFC</th>
							<th>Periodo</th>
							<th>Folio SUA</th>
							<th>Fecha de pago</th>
							<th>Importe</th>
							<th>Comprobante</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${listPagos}" var="pagoFiscal" varStatus="indice">
							<tr>
								<td>${pagoFiscal.numeroRegistroPatronal}</td>
								<td>${pagoFiscal.rfc}</td>
								<td>${pagoFiscal.periodo}</td>
								<td>${pagoFiscal.folioSua}</td>
								<td><fmt:formatDate value="${pagoFiscal.fechaPago}"
										pattern="dd/MM/yyyy HH:mm" />
								</td>
								<td><fmt:formatNumber type="number" maxFractionDigits="2"
										value="${pagoFiscal.total}" />
								</td>

								<td style="vertical-align: top; text-align: center;">
									<form:checkbox path="comprobantes" value="${pagoFiscal.folioSua}" cssClass="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"/>
									<!--
									<input type="checkbox" name="documento"
									value="${pagoFiscal.folioSua}" />
									--> 
									
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<table style="width: 100%;"
					class="table table-striped table-bordered" cellpadding="0"
					cellspacing="0" border="0">
					<tr>
							<td style="text-align: right;">
								<button type="button" id="generarButton" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">Generar Comprobantes</button>
							</td>
					</tr>
				</table>
			</form:form>
		</div>
	</c:when>
	<c:otherwise>
		<div class="container-fluid empty-state">
			<div class="row">
				<div class="alert alert-danger alert-danger-riss">
					<strong>Error: </strong> ${errorFormGeneral}
				</div>
			</div>
		</div>
	</c:otherwise>
</c:choose>