<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/personas/notificacion/NotificacionesCtrl.js" htmlEscape="true" />"></script>

<script type="text/javascript">
	
	var objNotifCtrl;
	
	$(document).ready(function() {
		
		objNotifCtrl = NotificacionesCtrl;
		
		$('#limpiar').click(function() {
			$('#forma').clearForm();
		});

		$('#consultar').click(function(event) {
			
			objNotifCtrl.datosEntrada.idPersona = $('#busquedaIdPersona').val();
			objNotifCtrl.datosEntrada.idModulo = $('#busquedaIdModulo').val();
			objNotifCtrl.datosEntrada.isMoral = $('#indPersonaMoral').is(':checked');
			objNotifCtrl.datosEntrada.idContenedor = "numNotifDivTest";
			
			if ($('#indMostrarPantalla').is(':checked')){
				objNotifCtrl.consultarNotificaciones();
			} else {
				objNotifCtrl.consultarNotificacionesJSON();
				$('#resultJSON').val(objNotifCtrl.getDatosSalida());
			}
		});

	});
</script>

<div class="container">
	<div class="hero-unit">
		<div class="form-comment" style="padding-right: 20px;">

			<c:set var="contextpath" value="<%=request.getContextPath()%>" />

			<div class="alert">
				<button type="button" class="close" data-dismiss="alert">×</button>
				<strong>Instrucciones: </strong>Ingrese los datos para consultar las notificaciones
			</div>

			<span id="errorNegocioLabel" class="error hiddenElement"></span>

			<fieldset>

				<legend>
					<strong>&nbsp;Datos de la B&uacute;squeda&nbsp;</strong>
				</legend>

				<label class="wide">ID PERSONA</label>
				<input type="text" id="busquedaIdPersona" style="width: 300px" maxlength="18"/>
				
				<br /> <br /> <br />
				
				<label class="wide">MODULO</label>
				<select id="busquedaIdModulo">
					<c:forEach var="modulo" items="${modulos}">
						<option value="${modulo.key}">${modulo.value}</option>
					</c:forEach>
				</select>
				
				<br /> <br /> <br />
				
				<label class="wide">ES PERSONA MORAL?</label>
				<input type="checkbox" id="indPersonaMoral" />
				
				<br /> <br /> <br />
				
				<label class="wide">Mostrar pantalla?</label>
				<input type="checkbox" id="indMostrarPantalla" />
				
				<br /> <br /> <br />
				
				<textarea rows="20" cols="30" id="resultJSON"></textarea>
				
			</fieldset>
			<br />

			<div style="float: right;">
				<button type="button" class="btn btn-secondary" id="consultar">
					Consultar</button>
				<button type="button" class="btn btn-secondary" id="limpiar">
					Limpiar</button>
			</div>
			<br><br><br>
			
			<div id="numNotifDivTest"></div>
			
		</div>
	</div>
</div>
