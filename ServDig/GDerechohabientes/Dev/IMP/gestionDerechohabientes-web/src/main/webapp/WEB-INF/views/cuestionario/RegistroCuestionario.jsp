<%@ include file="../general/taglibs.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>

<br>
<div class="form-comment">
	<input type="hidden" id="idTramite" name="tramiteId" value="${registroDerechohabiente.tramiteId}" />
	<fieldset>
		<legend>
			<spring:message code="label.titulo.registroResultado" ></spring:message>
		</legend>
		<form id="frmRegistro" name="frmRegistro">
		<table>
				<tr>
					<td>
						<spring:message code="label.evaluacionCuestionario">:
						</spring:message>
					</td>
					<td valign="middle">
						<input class='entero_3' style="width:100px" maxlength="3" type ="text" id="evaluacionCuestionario" name="evaluacionCuestionario"  />
					</td>
			
				</tr>
				
			</table>	
		</form>
	</fieldset>
</div>
<script>
$(document).ready(function() {
	validateForm.allowOnlyRegularExpression( $('.entero_3'),regularExpression.entero_3);
	
	$("#frmRegistro").validate({
		
		rules:{ 
			'evaluacionCuestionario': {required:true, number: true, range:[1,100]}
		},
		messages: { 
			'evaluacionCuestionario': {required:"Obligatorio",number:"Valor invalido",range:"El valor no debe ser mayor a 100"}
		}
	});
	
});

</script>