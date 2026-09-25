<%@ include file="taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/personas/generalPersonas.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.ui.datepicker-es.js" htmlEscape="true" />"></script>

<div class="page_holder_no_height">
	<div class="post_entry_wide no-border">
		
		<div class="form-comment">
			<c:set var="contextpath" value="<%=request.getContextPath()%>" />

			<div class="div-1seccion">
				<form:form modelAttribute="personaFisica" id="busquedaPersonaFisicaForm" method="post" cssClass="formNotBlock">
	
					<div class="separadorseccion">B&uacute;squeda de Persona F&iacute;sica</div>
					
					<fieldset>
						<legend>
							<strong>&nbsp;Filtro de B&uacute;squeda&nbsp;</strong>
						</legend>

						<form:label path="idPersona" cssClass="wide">Identificador</form:label>
						<form:input path="idPersona" id="busquedaIdentificador" cssStyle="width: 300px" maxlength="9" cssClass="numerico" />
						<span id="idPersonaError" class="error hiddenElement">Formato de identificador inv&aacute;lido</span>
						<br /><br /><br />
 						
						<form:label path="nss" cssClass="wide" id="busquedaNssLabel">NSS</form:label>
						<form:input path="nss" id="busquedaNss" cssStyle="width: 300px" maxlength="11" cssClass="numerico" />
						<span id="nssError" class="error hiddenElement">Formato de NSS inv&aacute;lido</span>
						<br /><br /><br />

						<form:label path="curp" cssClass="wide">CURP</form:label>
						<form:input path="curp" id="busquedaCurp" cssStyle="width: 300px" maxlength="18" />
						<span id="curpError" class="error hiddenElement"></span>
						<br /><br /><br />
						
						<form:label path="rfc" cssClass="wide">RFC</form:label>
						<form:input path="rfc" id="busquedaRfc" cssStyle="width: 300px" maxlength="13" />
						<span id="rfcError" class="error hiddenElement"></span>
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
						entidad="mx.gob.imss.ctirss.delta.persistence.DicSexo" mostrarSoloActivos="true"/>
						<br /><br />
						
						<form:label path="fechaNacimiento" class="wide">Fecha de Nacimiento</form:label>
						<form:input path="fechaNacimiento" id="busquedaFechaNacimiento"	style="width: 70px" maxlength="10" />
						<span id="fechaNacimientoError" class="error hiddenElement"></span>
						<span id="fechaNacimientoErrorCliente" class="error hiddenElement">Formato de Fecha inv&aacute;lido</span>
						<br /><br />
						
						<form:label path="lugarNacimiento.clave" class="wide">Lugar de Nacimiento</form:label>
						<combo:creaCombo idHtml="lugarNacimiento.clave" idHtmlContenedor="busquedaPersonaFisicaForm" 
						entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado"  idHtmlValor="${personaFisica.lugarNacimiento.clave}" mostrarSoloActivos="true"/>
						<br /><br />
	
						<form:label path="busqAprox" class="wide">Seleccione el Tipo de B&uacute;squeda</form:label>
						<form:radiobutton path="busqAprox" id="busquedaAproximada" style="width: 20px" label="Aproximada" value="true"/>
						<form:radiobutton path="busqAprox" id="busquedaExacta" style="width: 20px" label="Exacta" value="false" checked="checked" />

					</fieldset>

					<div style="float: right;">
						<input type="button" value="Buscar" class="mboton" id="buscar" />
						<input type="button" value="Limpiar" class="mboton" id="limpiar"/>
					</div>
	
					<br />
					<span id="errorNegocioLabel" class="error hiddenElement"></span>
	
				</form:form>
			</div>
		</div>
	</div>
	<br/>
	
	<!-- El datateibol debe estar dentro de una tag <form> para que los botones de cada renglon agarren el estilo de la clase "mboton" -->
	<form action="">
		<div>
			<table style="width: 100%" id="tablaResultadoIndividuos"></table>
		</div>

		<div style="float: right;">
			<input type="button" value="Tr&aacute;mite de Registro de Personas F&iacute;sicas" class="mboton" id="tramiteRegistroPersonasFisicas"/>
		</div>
		
	</form>
</div>

<!-- En este div se presentara el cuadro de dialogo de jquery que antes era un showModalDialog de javascript -->
<div id="dgModalDatosComplementarios"></div>
<!--  -->

<!-- codigo javascript para gestionar el data teibol donde se presentara el resultado de la busqueda -->
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/personas/fisica/busqueda/busquedaPersonaFisica.js" htmlEscape="true" />" ></script>
