<%@ include file="../general/taglibs.jsp" %>

<script type="text/javascript">
	$(document).ready(function() {
		$('#tblSolicitudesPersonMoral').dataTable({
			bJQueryUI : true,
			bFilter : false,
			bInfo : true,
			bSort : false,
			bPaginate : true,
			iDeferLoading : 0,
			iDisplayLength : 5,
			bProcessing : true
		});
	});
</script>

<div class="page_holder" style="width: 300px; height: 500px; margin: 0px;">
	<div class="contenedor">
		<div class="form-comment">

			<table id="tblSolicitudesPersonMoral" style="width: 100%">
				<thead>
					<tr>
						<th>Solicitudes realizadas</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="solicitud" items="${listSolicitudes}" varStatus="indice">
						<tr>
							<td>
								<address>
									<strong>${solicitud.fechaSolicitud}</strong><br />
									${solicitud.tipoSolicitud.descripcion}<br />
									${solicitud.estadoSolicitud.descripcion}<br />
									${solicitud.noFolioSolicitud}
								</address>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>