<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/personas/generalPersonas.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/limpiarFormulario.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.ui.datepicker-es.js" htmlEscape="true" />"></script>

<script type="text/javascript">
<!--
	$(document).ready(function(){

		$('#limpiar').click(function(){
			$('#registroAseguradoDatosBasicosForm').clearForm();
		});
		
		$('#buscar').click(function() {
 			$('#registroAseguradoDatosBasicosForm').submit();
		});
	});

//-->
</script>

<div class="page_holder_no_height">
	<div class="post_entry_wide no-border">
		<div class="form-comment">
		
			<c:set var="contextpath" value="<%=request.getContextPath()%>" />
			<form:form modelAttribute="fisica" id="registroAseguradoDatosBasicosForm" action="${contextpath}/asegurado/busqueda-unificada" method="post">
			
				<fieldset>
					<legend>
						<strong>&nbsp;Datos de Registro&nbsp;</strong>
					</legend>
					
					<form:label path="curp" cssClass="wide">CURP</form:label>
					<form:input path="curp" id="registroCurp" cssStyle="width: 300px" maxlength="18" />
					<form:errors path="curp" cssClass="error" />	
					<br /><br /><br />
	
					<form:label path="nombre" cssClass="wide">Nombre(s)</form:label>
					<form:input path="nombre" id="registroNombres" cssStyle="width: 300px" maxlength="50" />
					<form:errors path="nombre" cssClass="error" />
					<br /><br /><br />
					
					<form:label path="primerApellido" cssClass="wide">Primer Apellido</form:label>
					<form:input path="primerApellido" id="registroPrimerApellido" cssStyle="width: 300px" maxlength="50" />
					<form:errors path="primerApellido" cssClass="error" />
					<br /><br /><br />
	
					<form:label path="segundoApellido" cssClass="wide">Segundo Apellido</form:label>
					<form:input path="segundoApellido" id="registroSegundoApellido"	cssStyle="width: 300px" maxlength="50" />
					<br /><br /><br />
					
					<form:label path="sexo.idSexo" class="wide">Sexo</form:label>
					<combo:creaCombo idHtml="sexo.idSexo" idHtmlContenedor="registroAseguradoDatosBasicosForm" 
					entidad="mx.gob.imss.ctirss.delta.persistence.DicSexo" idHtmlValor="${fisica.sexo.idSexo}" 
					mostrarSoloActivos="true"/>
					<form:errors path="sexo.idSexo" cssClass="error" />
					<br /><br />
	
					<form:label path="fechaNacimiento" class="wide">Fecha de Nacimiento</form:label>
					<form:input path="fechaNacimiento" id="registroFechaNacimiento"	style="width: 70px" maxlength="10" />
					<form:errors path="fechaNacimiento" cssClass="error" />
					<br /><br />
					
					<form:label path="lugarNacimiento.clave" class="wide">Lugar de Nacimiento</form:label>
					<combo:creaCombo idHtml="lugarNacimiento.clave" idHtmlContenedor="registroAseguradoDatosBasicosForm" 
					entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado"  idHtmlValor="${fisica.lugarNacimiento.clave}" 
					mostrarSoloActivos="true"/>
					<form:errors path="lugarNacimiento.clave" cssClass="error" />
					<br /><br />
					
					<form:errors path="errorFormGeneral" cssClass="error" />
					
				</fieldset>
					
				<div style="text-align: right; float: right;">
					<input type="button" value="Buscar" id="buscar" class="mboton" />
					<input type="button" value="Limpiar" class="mboton" id="limpiar" />
					<input type="button" value="Regresar" id="regresar" class="mboton" />
				</div>
		
			</form:form>
	
		</div>
	</div>
</div>
