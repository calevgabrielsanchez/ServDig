	<%@ include file="../general/taglibs.jsp"%>
	<script type="text/javascript">
		$(document).ready(
			function() {
				$("#cerrar").click(
					function() {
						esperePF();
						var direccion = ${usuarioObj.perfilUsuario.idPerfilUsuario} == 4 ? "/welcome/uno/busqueda" : "/inicio/grupoFamiliar";
						location.href = "" + context_path + "" + direccion;
					}	
				);
			}	
		);
		
		function esperePF() {
			$decision = $('<div></div');

			$decision.dialog({
				autoOpen : false,
				resizable : false,
				height : 140,
				title : '',
				modal : true
			}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

			$decision.text('Espere un momento por favor');
			$decision.dialog('open');
		}
	</script>
	
	<div class="ui-widget-content ui-corner-all">
		<div class="ui-state-error ui-corner-all" align="center">
			<div class="ui-icon ui-icon-alert"></div>
			<p class="ui-helper-reset ui-state-error-text"><spring:message code="${errores}"/></p>
		</div>
	</div>
	<br><br>
	<div align="center">
	<form>
		<table>
			<tr>
				<td align="center">
					<input id="cerrar" type="button" value="Regresar" class="mboton">
				</td>
			</tr>
		</table>
	</form>
	</div>