<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ include file="../../layout/taglibs.jsp" %>
<style>
.ui-datepicker-trigger{
    top: 8px;
	float:left;
    position: relative;
}
</style>
<script>
$(function() {
	$("#busquedaFechaCreacion").datepicker({
		showOn: "button",
		buttonImage: context_path + "/static/resources/imagenes/calendar.gif",
		buttonImageOnly: true,
		changeMonth: true,
		changeYear: true
	});
});
</script>
<div class="container">
	<div class="hero-unit">
		<div class="form-comment" style="padding-right: 20px;">
		<c:set var="contextpath" value="<%=request.getContextPath()%>"/>
			<form:form modelAttribute="moral" id="forma" method="post" action="${contextpath}/persona/moral/ubicar/consultar">	
				<c:if test="${ warning == true }">
				<div class="row" >
					<div class="alert alert-danger">
						<h6> Nota: ${mensajeException}</h6>
						Por favor verifique que los datos proporcionados sean correctos.
					</div>
				</div>
				</c:if>
							
				<div class="alert">
	 			   <button type="button" class="close" data-dismiss="alert">x</button>
	    			<strong>Nota:</strong> Para un mejor funcionamiento del servicio todos los datos son requeridos.
	    		</div>
							
				<fieldset>
					<legend>
						<strong>&nbsp;Datos de la B&uacute;squeda&nbsp;</strong>
					</legend>
	
					<form:label path="rfc" cssClass="wide">RFC</form:label>
					<form:errors path="rfc" cssClass="error"></form:errors>
					<form:input path="rfc" id="busquedaRfc" cssStyle="width: 300px" maxlength="13"/>
					<br/><br/><br/>
												
					<form:label path="razonSocial" cssClass="wide">Raz&oacute;n Social</form:label>
					<form:errors path="razonSocial" cssClass="error"></form:errors>
					<form:input path="razonSocial" id="busquedaRazonSocial" cssStyle="width: 300px" maxlength="18"/>
					<br/><br/><br/>
					
					<form:label path="tipoSociedad.idTipoSociedad" cssClass="wide">Tipo Sociedad</form:label>
					<form:errors path="tipoSociedad.idTipoSociedad" cssClass="error"></form:errors>
					<combo:creaCombo idHtml="tipoSociedad.idTipoSociedad" 
									 idHtmlContenedor="forma" 
									 entidad="mx.gob.imss.ctirss.delta.persistence.DicTipoSociedad" 
									 idHtmlValor="${moral.tipoSociedad.idTipoSociedad}" 
									 mostrarSoloActivos="true"/>
					<br/><br/><br/>
					
					<form:label path="fechaCreacion" cssClass="wide">Fecha de Creaci&oacute;n</form:label>
					<form:errors path="fechaCreacion" cssClass="error"></form:errors>
					<form:input path="fechaCreacion" id="busquedaFechaCreacion" cssStyle="width: 300px" maxlength="18"/>
					<br/><br/><br/>
					
					<form:label path="actaConstitutiva" cssClass="wide">Acta Constitutiva</form:label>
					<form:errors path="actaConstitutiva" cssClass="error"></form:errors>
					<form:input path="actaConstitutiva" id="busquedaActaConstitutiva" cssStyle="width: 300px" maxlength="18"/>
					<br/><br/><br/>
				</fieldset>
		
				<div style="float: right;">
					<button type="submit" class="btn btn-secondary" id="buscar"> Buscar </button>
					<button type="button" class="btn btn-secondary" id="limpiar"> Limpiar </button>
				</div>	
				<br/>
							
				<span id="errorNegocioLabel" class="error hiddenElement"></span>
			</form:form>

			<form name="aoDataForm" id="aoDataForm">
				<input type="hidden" id="iDisplayStart" name="iDisplayStart" value="0"/>
				<input type="hidden" id="iDisplayLength" name="iDisplayLength" value="10"/>
			</form>
		</div>
	</div>
</div>
    