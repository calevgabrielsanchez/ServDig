<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/personas/notificacion/DetalleNotificacionesCtrl.js" htmlEscape="true" />"></script>

<style type="text/css">
	body {
		background-color: transparent;
	}
	
	.alert {
	    background-color: #FCF8E3;
	    border: 1px solid #FBEED5;
	    border-radius: 4px 4px 4px 4px;
	    color: #C09853;
	    padding: 8px 35px 8px 14px;
	    text-shadow: 0 1px 0 rgba(255, 255, 255, 0.5);
	    text-align: center;
	}
	
	.alert a {
	    color: #0088CC;
	    text-decoration: none;
	    cursor: pointer;
	}
	
	
</style>

<script type="text/javascript">
	
	var objDetalleNotifCtrl;
	
	$(document).ready(function() {
	
		objDetalleNotifCtrl = DetalleNotificacionCtrl;
		
		$('#detalleNotif').click(function () {
			fnConsultaNotif();
		});
		
	});
		
 	function fnConsultaNotif() {
		
 		var isMoral = ${isMoral}
 		
 		objDetalleNotifCtrl.datosEntrada.idPersona = ${idPersona};
 		objDetalleNotifCtrl.datosEntrada.idModulo = ${idModulo};
 		
		objDetalleNotifCtrl.init("consultaNotifDialog");
		
		objDetalleNotifCtrl.setOnCloseCallback(function () {
			$('#contenidoMsgNotif').text("Cargando ....");
			
			parent.NotificacionesCtrl.consultarNotificaciones();
		});
		
		if (isMoral){
			objDetalleNotifCtrl.mostrarDetalleNotificacionesMoral();
		} else {
			objDetalleNotifCtrl.mostrarDetalleNotificaciones();
		}
	}
</script>

<div id="msgNotifDivRef"> 
	<div class="alert" style="width: 70%; margin: 0 auto;">
		<span id="contenidoMsgNotif">
			<c:choose>
				<c:when test="${numNotificaciones > 0 }">
					La persona <strong>${nombrePersona}</strong> cuenta con <strong>${numNotificaciones}
						Notificaciones</strong>. <a id="detalleNotif">Ver detalle....</a>
				</c:when>
				<c:otherwise>
					La persona no cuenta con Notificaciones.
				</c:otherwise>
			</c:choose>
		</span>
	</div>
</div>

<!-- Div para contruir el dialogo para el detalle de las notificaciones -->
<div id="consultaNotifDialog"></div>