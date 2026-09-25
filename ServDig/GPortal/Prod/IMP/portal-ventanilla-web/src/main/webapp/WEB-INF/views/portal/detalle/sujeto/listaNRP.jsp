<%@ include file="../../../general/taglibs.jsp"%>

<script>
	$(document).ready(function() {
		$('#tblPatronesAsociados').dataTable({
			"searching": true,
			"sPaginationType" : "bootstrap-full",
			"oLanguage" : {
				"sZeroRecords" : "<center><strong style=\"font-size: small;\">Sin información que mostrar</strong></center>"
			},
			"bLengthChange" : false,
			"bFilter" : true,
			"bProcessing" : false,
			"iDeferLoading" : 0,
			"bSort" : true
		});
	});
</script>

<c:choose>
	<c:when test="${empty errorListaNRP}">
		<c:if test="${totalNRP == 0}">
			<div class="empty-state well">
				<div class="imagen">
					<i class="glyphicon glyphicon-exclamation-sign"></i>
				</div>
				<div class="alert alert-info">No existen registros patronales asociados.</div>
			</div>
		</c:if>

		<c:if test="${totalNRP > 0}">
			<div id="patronesAsociadosWrapper" class="well">
				<table id="tblPatronesAsociados" class="table table-striped table-bordered" 
					cellpadding="0" cellspacing="0">
					<thead>
						<tr>
							<th>
								<center>Registro Patronal</center>
							</th>
							<th>
								<center>Nombre Comercial</center>
							</th>
							<th>
								<center>Fecha de Alta</center>
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${patronesAsociados}" var="patron" varStatus="indice">
							<tr>
								<td>${patron.numeroRegistroPatronal}${patron.modalidad.numModalidad}${patron.digVerificador}</td>
								<td>${patron.nombreComercial}</td>
								<td>
									<fmt:formatDate value="${patron.fechaAlta}" pattern="dd/MM/yyyy" />
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
	</c:when>
	<c:otherwise>
		<div class="alert alert-danger">${errorListaNRP}</div>
	</c:otherwise>
</c:choose>
