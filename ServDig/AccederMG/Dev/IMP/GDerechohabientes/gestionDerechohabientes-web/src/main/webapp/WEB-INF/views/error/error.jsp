<%@ include file="../general/taglibs.jsp"%>
<c:if test="${mostrarBoton != null && mostrarBoton}">
<script>
	$(document).ready(
		function() {
			$("#regresarGrupoFamiliarValidacion").click(
				function() {
					location.href = context_path +"/inicio/grupoFamiliar";
					$.blockUI();
				}		
			);
		}		
	);
</script>
</c:if>

<div class="form-comment">

<br>
<table>
	<tr>
		<td><br>
		</td>
	</tr>

	<tr>
		<td>
		<div class="page_holder">
		<div class="ui-widget-content ui-corner-all">
		<div class="ui-state-error ui-corner-all" align="center">
		<div class="ui-icon ui-icon-alert"></div>
		<p class="ui-helper-reset ui-state-error-text"><spring:message
			code="${exception}" /></p>
		</div>
		<div class="ui-corner-all" align="center">
		<h3>Ha ocurrido un error inesperado. ${error}</h3>
		</div>
		</div>


		</div>
		</td>
	</tr>
	<tr>
		<td><br>
		
		<div align="center">
			<form>
		<table>
			<tr>
				<td align="center">
				<c:if test="${mostrarBoton != null && mostrarBoton}">
				<div id="botones"><input id="regresarGrupoFamiliarValidacion" type="button" value = "<spring:message code="button.regresar"/>" class="mboton" /></div>
				</c:if>
				</td>
			</tr>
		</table>
		</form>
		
			</div>
		</td>
	</tr>
	<tr>
		<td><br>
		</td>
	</tr>
</table>

</div>
