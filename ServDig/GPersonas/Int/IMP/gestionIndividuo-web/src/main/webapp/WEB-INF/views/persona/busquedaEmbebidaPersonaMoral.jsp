<%@ include file="taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<script>
	var context_path = '<%= request.getContextPath()%>';
</script>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/personas/moral/busqueda/busquedaEmbebidaPersonaMoral.js" htmlEscape="true" />"></script>

<div class="page_holder ">
	<div class="contenedor" style="height: 830px !important; display: inline-block !important;">
		
		<div class="row" >
			<c:set var="contextpath" value="<%=request.getContextPath()%>" />

			<div class="cell " style="width: 50%">
				<div class="form-comment" style="padding-right: 20px;">
					
					<form:form modelAttribute="moral" id="busquedaPersonaMoralForm" method="post" action="/persona/moral/busqueda/bdu/json">	<!-- Definicion nueva -->
						<div class="separadorseccion">B&uacute;squeda de Persona Moral</div>
						<fieldset>
							<legend>
								<strong>&nbsp;Filtro de B&uacute;squeda&nbsp;</strong>
							</legend>
	
							<form:label path="rfc" cssClass="wide">RFC:</form:label>
							<form:input path="rfc" id="busquedaRfc" cssStyle="width: 300px" maxlength="12" />
							<span id="rfcError" class="error hiddenElement"></span>
							<br /><br /><br />
							
							<form:label path="razonSocial" cssClass="wide">Raz&oacute;n Social:</form:label>
							<form:input path="razonSocial" id="busquedaRazonSocial" cssStyle="width: 300px" maxlength="100" />
							<br /><br /><br />
							
							<form:hidden path="tipoSociedad.idTipoSociedad" id="idTipoSociedad" />
						</fieldset>
	
						<div style="float: right;">
							<input type="button" value="Buscar" class="mboton" id="buscar" />
							<input type="button" value="Limpiar" class="mboton" id="limpiar"/>
						</div>	
						<br />
						
						<span id="errorNegocioLabel" class="error hiddenElement"></span>
					</form:form>
						
					<form name="aoDataForm" id="aoDataForm">
						<input type="hidden" id="iDisplayStart" name="iDisplayStart" value="0"/>
						<input type="hidden" id="iDisplayLength" name="iDisplayLength" value="10"/>
					</form>
					
				</div>
			</div>
			
			<div class="cell resultados" style="width: 50%; ">
				<h2 style="font-size: 1em !important;">Personas localizadas</h2>
				<!-- En este Div se presentara el HMTL con el resultado de la busqueda -->
				<div id="resultados"><!-- Aqui va el resultado de la busqueda --></div>
			</div>
			
		</div>
	</div>
	<br/>
	
	<!-- DIV de query para poder desplegar un cuadro de dialogo tipo confirm de los datos de persona -->
	<div id="dgPersonaMoralDetalle" title="¿Desea confirmar la valid&eacute;z de estos datos?" >
		<div class="form-comment">
			<form id="formPersonaMoral">
				<fieldset>
					<legend>&nbsp;Datos de la Persona seleccionada&nbsp;</legend>
					<fieldset class="fsInternosinlineas">
						<label><b>RFC: </b></label>
						<input type="text" id="rfc_" readonly="readonly" />
					</fieldset>
					<fieldset class="fsInternosinlineas">
						<label><b>Raz&oacute;n Social: </b></label>
						<input type="text" id="razonSocial_" readonly="readonly" />
					</fieldset>
					<fieldset class="fsInternosinlineas">
						<label><b>Acta Constitutiva: </b></label>
						<input type="text" id="actaConstitutiva_" readonly="readonly" />
					</fieldset>
					<fieldset class="fsInternosinlineas">
						<label><b>Fecha de Creaci&oacute;n: </b></label>
						<input type="text" id="fechaCreacion_" readonly="readonly" />
					</fieldset>
				</fieldset>
			</form>
		</div>
	</div>		
	<!--  -->
	
</div>
