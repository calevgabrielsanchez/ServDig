<%@ include file="../../taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<script>
	var context_path = '<%= request.getContextPath()%>';
</script>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/personas/generalPersonas.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/personas/fisica/busqueda/busquedaEmbebidaPersonaFisica.js" htmlEscape="true" />"></script>

<div class="page_holder ">
	<div class="contenedor" style="height: 830px !important; display: inline-block !important;">
		
		<div class="row" >
			<c:set var="contextpath" value="<%=request.getContextPath()%>" />

			<div class="cell " style="width: 50%">
				<div class="form-comment" style="padding-right: 20px;">
					
					<form:form modelAttribute="fisica" id="busquedaPersonaFisicaForm" method="post" action="/persona/fisica/busqueda/bdu/json">	<!-- Definicion nueva -->
						<div class="separadorseccion">B&uacute;squeda de Persona F&iacute;sica</div>
						<fieldset>
							<legend>
								<strong>&nbsp;Filtro de B&uacute;squeda&nbsp;</strong>
							</legend>

							<form:label path="curp" cssClass="wide">CURP</form:label>
							<form:input path="curp" id="busquedaCurp" cssStyle="width: 300px" maxlength="18" />
							<br /><br /><br />
							
							<form:label path="rfc" cssClass="wide">RFC</form:label>
							<form:input path="rfc" id="busquedaRfc" cssStyle="width: 300px" maxlength="13" />
							<br /><br /><br />
							
							<form:label path="nombre" cssClass="wide">Nombre(s)</form:label>
							<form:input path="nombre" id="busquedaNombres" cssStyle="width: 300px" maxlength="30" />
							<br /><br /><br />
							
							<form:label path="primerApellido" cssClass="wide">Primer Apellido</form:label>
							<form:input path="primerApellido" id="busquedaPrimerApellido" cssStyle="width: 300px" maxlength="30" />
							<br /><br /><br />
		
							<form:label path="segundoApellido" cssClass="wide">Segundo Apellido</form:label>
							<form:input path="segundoApellido" id="busquedaSegundoApellido"	cssStyle="width: 300px" maxlength="30" />
							<br /><br /><br />
							
							<form:label path="sexo.idSexo" class="wide">Sexo</form:label>
							<combo:creaCombo idHtml="sexo.idSexo" idHtmlContenedor="busquedaPersonaFisicaForm" 
							entidad="mx.gob.imss.ctirss.delta.persistence.DicSexo" 
							mostrarSoloActivos="true"/>
							<br /><br />
							
							<form:label path="fechaNacimiento" class="wide">Fecha de Nacimiento</form:label>
							<form:input path="fechaNacimiento" id="busquedaFechaNacimiento"	style="width: 70px" maxlength="10" />
							<br /><br />
							
							<form:label path="lugarNacimiento.clave" class="wide">Lugar de Nacimiento</form:label>
							<combo:creaCombo idHtml="lugarNacimiento.clave" idHtmlContenedor="busquedaPersonaFisicaForm" 
							entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado"  idHtmlValor="${personaFisica.lugarNacimiento.clave}" 
							mostrarSoloActivos="true"/>
							<br /><br />	
														
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
	<div id="dgPersonaFisicaDetalle" title="¿Desea confirmar la valid&eacute;z de estos datos?" >
		<div class="form-comment">
			<form id="formPersonaFisica">
				<fieldset>
					<legend>&nbsp;Datos de la Persona seleccionada&nbsp;</legend>
					
					<fieldset class="fsInternosinlineas">
						<label><b>RFC: </b></label>
						<input type="text" id="rfc_" readonly="readonly" />
					</fieldset>
					<fieldset class="fsInternosinlineas">
						<label><b>CURP: </b></label>
						<input type="text" id="curp_" readonly="readonly" />
					</fieldset>
					<fieldset class="fsInternosinlineas">
						<label><b>Nombre: </b></label>
						<input type="text" id="nombre_" readonly="readonly" />
					</fieldset>
					<fieldset class="fsInternosinlineas">
						<label><b>Primer Apellido: </b></label>
						<input type="text" id="primerApellido_" readonly="readonly" />
					</fieldset>
					<fieldset class="fsInternosinlineas">
						<label><b>Segundo Apellido: </b></label>
						<input type="text" id="segundoApellido_" readonly="readonly" />
					</fieldset>
					<fieldset class="fsInternosinlineas">
						<label><b>Sexo: </b></label>
						<input type="text" id="sexo_" readonly="readonly" />
					</fieldset>
					<fieldset class="fsInternosinlineas">
						<label><b>Fecha de Nacimiento: </b></label>
						<input type="text" id="fechaNacimiento_" readonly="readonly" />
					</fieldset>
					<fieldset class="fsInternosinlineas">
						<label><b>Lugar de Nacimiento: </b></label>
						<input type="text" id="lugarNacimiento_" readonly="readonly" />
					</fieldset>
										
				</fieldset>
			</form>
		</div>
	</div>		
	<!--  -->
	
</div>
