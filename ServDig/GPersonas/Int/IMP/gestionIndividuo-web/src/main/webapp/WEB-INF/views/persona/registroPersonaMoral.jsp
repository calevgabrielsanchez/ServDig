<%@ include file="taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/personas/generalPersonas.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/limpiarFormulario.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.ui.datepicker-es.js" htmlEscape="true" />"></script>

<script type="text/javascript">
	$(document).ready(function() {
	
		$('#limpiar1').click(function() {
			// Como los errores se muestran en spans, hay que ocultarlos manualmente al dar clic en el boton limpiar, porque al metodo clearForm()
			// levale madres y no los quita
			$('span').each(function (){
				if($(this).hasClass('error')){
					$(this).hide();
					
				}
			});			
			$('#registroPersonaMoralForm1').clearForm();
		});
		
		$('#limpiar2').click(function() {
			// Como los errores se muestran en spans, hay que ocultarlos manualmente al dar clic en el boton limpiar, porque al metodo clearForm()
			// levale madres y no los quita
			$('span').each(function (){
				if($(this).hasClass('error')){
					$(this).hide();
					
				}
			});
			$('#registroPersonaMoralForm2').clearForm();
		});
	
	});
</script>

<div class="page_holder_no_height">
	<div class="post_entry_wide no-border">

		<div class="form-comment">
			<c:set var="contextpath" value="<%=request.getContextPath()%>" />
		
			<form:form modelAttribute="moral" id="registroPersonaMoralForm1" action="${contextpath}/persona/moral/busqueda/sat" method="post">

				<fieldset>
					<legend>
						<strong>&nbsp;Alta por RFC&nbsp;</strong>
					</legend>
					<form:label path="rfcSat" cssClass="wide">RFC</form:label>
					<form:input path="rfcSat" id="busquedaRegistroRfc" cssStyle="width: 300px" maxlength="12" cssClass="rfc_moral" />
					<form:errors path="rfcSat" cssClass="error" />	
				</fieldset>
				
				<div style="float: right;">
					<input type="submit" value="Buscar" id="buscarSat" class="mboton" />
					<input type="button" value="Limpiar" class="mboton" id="limpiar1"/>
				</div>		
				<br /><br /><br />
				
			</form:form>	
			
			<form:form modelAttribute="moral" id="registroPersonaMoralForm2" action="${contextpath}/persona/moral/busqueda/imss" method="post">	
				<fieldset>
					<legend>
						<strong>&nbsp;Alta por Datos B&aacute;sicos&nbsp;</strong>
					</legend>
					
					<form:label path="rfc" cssClass="wide">RFC</form:label>
					<form:input path="rfc" id="registroRfc" cssStyle="width: 300px" maxlength="12" cssClass="rfc_moral" />
					<form:errors path="rfc" cssClass="error" />
					<br /><br /><br />							
					
					<form:label path="razonSocial" cssClass="wide">Raz&oacute;n Social</form:label>
					<form:input path="razonSocial" id="registroRazonSocial" cssStyle="width: 300px" maxlength="50" />
					<form:errors path="razonSocial" cssClass="error" />
					<br /><br /><br />
					
					<form:label path="tipoSociedad.idTipoSociedad" class="wide">Tipo de Sociedad</form:label>
					<combo:creaCombo idHtml="tipoSociedad.idTipoSociedad" idHtmlContenedor="registroPersonaMoralForm2" 
					entidad="mx.gob.imss.ctirss.delta.persistence.DicTipoSociedad" idHtmlValor="${moral.tipoSociedad.idTipoSociedad}" 
					mostrarSoloActivos="true"/>
					<form:errors path="tipoSociedad.idTipoSociedad" cssClass="error" />
					<br /><br />

					<form:label path="actaConstitutiva" cssClass="wide">Acta Constitutiva</form:label>
					<form:input path="actaConstitutiva" id="registroActaConstitutiva" cssStyle="width: 300px" maxlength="30" />
					<form:errors path="actaConstitutiva" cssClass="error" />
					<br /><br /><br />

					<form:label path="fechaCreacion" class="wide">Fecha de Creaci&oacute;n</form:label>
					<form:input path="fechaCreacion" id="registroFechaCreacion"	style="width: 70px" maxlength="10" />
					<form:errors path="fechaCreacion" cssClass="error" />
					
				</fieldset>
				
				<div style="text-align: right; float: right;">
					<input type="submit" value="Buscar" id="buscarImss" class="mboton" />
					<input type="button" value="Limpiar" class="mboton" id="limpiar2" />
				</div>
		
			</form:form>
		</div>
	</div>
</div>
