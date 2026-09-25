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
	$(document).ready(function() {
		var gridSolicitud = $('#tblSolicitudesResumen').dataTable({
			"sPaginationType" : "bootstrap-full",
			"oLanguage": {
				"sZeroRecords": "<center><strong style=\"font-size: small;\">Sin información que mostrar</strong></center>"
			},
			"bLengthChange" : false,
			"bFilter" : false,
			"bProcessing" : false,
			"iDeferLoading" : 0,
			"bSort" : false,
		});
	});

	function descargarComprobante(index) {
		var urlAction = cobContextpath + '/edoadeudo/obtener/comprobanteFiscal/descargarComprobante';

		$.unblockUI();

		document.getElementById('descargarComprForm_' + index).action = urlAction;
		document.getElementById('descargarComprForm_' + index).submit();
	}
	
	function descargarFacturaElectronica(index) {
		var urlAction = cobContextpath + '/edoadeudo/obtener/comprobanteFiscal/descargarFacturaElectronica';
		$.unblockUI();
		document.getElementById('descargarComprForm_' + index).action = urlAction;
		document.getElementById('descargarComprForm_' + index).submit();
	}
	
</script>

<c:choose>
	<c:when test="${empty errorFormGeneral}">
		<ul>
			<li>
				<p>A continuaci&oacute;n seleccione  el comprobante que desea descargar:</p>
			</li>
		</ul>
		<div id="tblComprobantesFiscalesWrapper" style="width: 100%; margin: 0 auto;">
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
							<td><fmt:formatDate value="${pagoFiscal.fechaPago}" pattern="dd/MM/yyyy HH:mm" /></td>
							<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${pagoFiscal.total}" /></td>
							
							<td style="vertical-align: top; text-align: center;">
								<form action="${contextpath}/edoadeudo/obtener/comprobanteFiscal/descargarComprobante" 
										id="descargarComprForm_${indice.index}" target="_blank" method="post">
									<input type="hidden" name="numeroRegistroPatronal" value="${pagoFiscal.numeroRegistroPatronal}" />
									<input type="hidden" name="rfc" value="${pagoFiscal.rfc}" />
									<input type="hidden" name="folioSua" value="${pagoFiscal.folioSua}" />
									<input type="hidden" name="periodo" value="${pagoFiscal.periodo}" />
									<input type="hidden" name="importe" value="${pagoFiscal.total}" />														
									<ul id="icons" class="ui-widget">
										<li class="ui-state-default ui-corner-all" onclick="descargarComprobante(${indice.index})">
											<a style="text-decoration: none;">
												<span class="ui-icon ui-icon-disk" style="display: inline-block" title="DESCARGAR"></span>
											</a>
										</li>
									</ul>
									<ul id="icons" class="ui-widget">
										<li class="ui-state-default ui-corner-all" onclick="descargarFacturaElectronica(${indice.index})">
											<a style="text-decoration: none;">
												<span class="ui-icon ui-icon-document" style="display: inline-block" title="FACTURA" ></span>
											</a>
										</li>
									</ul>
								</form>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:when>
	<c:otherwise>
		<div class="alert alert-danger">
			<strong>Error: </strong> ${errorFormGeneral}
		</div>
	</c:otherwise>
</c:choose>

