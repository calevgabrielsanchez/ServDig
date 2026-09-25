<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>

<script type="text/javascript">
var objCtrl;

$(document).ready(function() {
	var scriptURL = "/gestionIndividuo-consulta-web/static/resources/js/delta/personas/notificacion/ConsultarNotificacionCtrl.js" 
	$.getScript( scriptURL, function () {
		objCtrl = ConsultarNotificacionCtrl;
	});
});

function mostarDetalleNotif(idNotificacion) {
	objCtrl.datosEntrada.idNotificacion = idNotificacion;
	
	objCtrl.init("detalleNotificacionComponent");
	objCtrl.mostrarDetalleNotificacion();
}
</script>

<div class="page_holder" style="width: 300px; height: 500px; margin: 0px;">
	<div class="contenedor">
		<div class="form-comment">

			<table id="tblNotificaciones" style="width: 100%"
				class="table table-striped table-bordered" cellpadding="0"
				cellspacing="0" border="0">
				<tbody>
					<c:forEach var="notificacion" items="${notificaciones}"
						varStatus="indice">
						<tr>
							<td>
								<address class="resumen-text"
									style="float: left; width: 80%; font-size: x-small;">
									<strong>${notificacion.tramite.tipoTramite.descripcion
										}</strong><br> ${notificacion.moduloOrigen.descripcion }<br>
									${notificacion.tramite.fechaPresentacionParse }
								</address>
								<div style="float: right; display: inline;">
									<a class="btn btn-sm widget-tool" title="VER DETALLE"
										onclick="mostarDetalleNotif(${notificacion.idNotificacion})">
										<i class="glyphicon glyphicon-file"></i>
									</a>
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>

			</table>
		</div>
	</div>
</div>