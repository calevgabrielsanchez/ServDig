<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript">
	
	var isPreregistro = '${IS_PREREGISTRO}';

	$(function() {
		$('#cerrarWizard').click(function() {
			if (typeof isPreregistro !== 'undefined' && isPreregistro == 'true') {
				parent.WizardAsignacionNSSCtrl.cerrar();
			} else {
				parent.WizardAsignacionNSSCtrl.cerrar();
				// procesarSolicitud();
			}
		});

		function procesarSolicitud() {
			var idSolicitud = ${solicitud.solicitudId};
			var url = '/gestionAsegurados-web-externo/wizard/nss/finalizarOSBTmp/'
					+ idSolicitud;

			$.postJSON(url, null, function(data) {
				parent.WizardAsignacionNSSCtrl.cerrar();
			}).error(function(data) {
				alert('Error');
			});
		}
		
		/* 
		 * Se lanza la generación de comprobante en cuanto se cargue la página,
		 * sólo cuando no haya existido error
		 */
		if ($('#huboError').length == 0) {
			$('form#formComprobante').submit();
			
			$('#imprimirComprobante').click(function(){
				$('form#formComprobante').submit();	
			});
		}
	});	
</script>

<div class="contenedor">
	<div class="contenido" style="width: 100%;">
		
		<c:if test="${not empty HUBO_ERROR }">
			<input type="hidden" value="${HUBO_ERROR}" id="huboError" />
		</c:if>
		
		<c:choose>
			<c:when test="${not empty HUBO_ERROR }">
				<div class="alert alert-danger alert-block">
					<button type="button" class="close" data-dismiss="alert">×</button>
					<strong>Error: </strong>${solicitud.errorFormGeneral}
				</div>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${IS_PREREGISTRO}">
						<div style="margin: 0 auto;">
							<div class="alert alert-warning alert-block">
								La solicitud para la generaci&oacute;n de N&uacute;mero de Seguridad Social
								no pudo realizarse correctamente por la siguiente raz&oacute;n:
								<br><br>
								<div style="text-align: center;">
									<strong>${RAZON_PREREGISTRO}</strong>
								</div>
								<br>
								Por lo tanto, se gener&oacute; una solicitud de <strong>PRE-REGISTRO</strong> 
								con el siguiente folio <strong>${solicitud.noFolioSolicitud}</strong> y para
								poder obtener su N&uacute;mero de Seguridad Social es
								necesario que acuda a ventanilla con el folio de esta solicitud.
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="alert alert-success alert-block">
							Su solicitud ha sido procesada correctamente y se le ha asignado a dicha solicitud el folio: <strong>${solicitud.noFolioSolicitud}</strong>
						</div>
						<!-- 
						<p>Su solicitud ser&aacute; atendida a la brevedad y
							recibir&aacute; a trav&eacute;s del correo elect&oacute;nico que
							registr&oacute; su N&uacute;mero de Seguridad Social.</p>
						 -->
						 
						<p style="font-size: medium; text-align: center;">Se le ha creado el siguiente N&uacute;mero de Seguridad Social</p>
						<div class="alert alert-success nss-recuperado"
							style="width: 50%; margin: 0px auto; text-align: center;">
							<h4 style="font-size: 35px;">
								<strong>${NSS_ASIGNADO}</strong>
							</h4>
						</div>
						<br>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</div>

	<div class="pie">
		<div class="opciones">
			<button class="btn btn-primary" id="cerrarWizard">ACEPTAR</button>
			<c:if test="${empty HUBO_ERROR }">
				<button type="button" class="btn btn-primary" id="imprimirComprobante">IMPRIMIR COMPROBANTE</button>
			</c:if>
		</div>
		<div class="controles"></div>
	</div>
</div>

<form id="formComprobante" action="/gestionAsegurados-web/reporte/comprobante/interno/${solicitud.solicitudId }"
	target="_blank" class="formNotBlock">
</form>