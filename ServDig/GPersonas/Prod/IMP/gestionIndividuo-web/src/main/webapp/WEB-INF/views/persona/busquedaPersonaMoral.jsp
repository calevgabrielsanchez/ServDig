<%@ include file="taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/personas/generalPersonas.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.ui.datepicker-es.js" htmlEscape="true" />"></script>

<div class="page_holder_no_height">
	<div class="post_entry_wide no-border">
		
		<div class="form-comment">
			<c:set var="contextpath" value="<%=request.getContextPath()%>" />

			<div class="div-1seccion">
	 			<form:form modelAttribute="moral" id="busquedaPersonaMoralForm" method="post" cssClass="formNotBlock">
	
					<div class="separadorseccion">B&uacute;squeda de Persona Moral</div>
					
					<fieldset>
						<legend>
							<strong>&nbsp;Filtro de B&uacute;squeda&nbsp;</strong>
						</legend>

						<form:label path="idPersona" cssClass="wide">Identificador</form:label>
						<form:input path="idPersona" id="busquedaIdentificador" cssStyle="width: 300px" maxlength="9"  cssClass="numerico" />
						<span id="idPersonaError" class="error hiddenElement">Formato de identificador inv&aacute;lido</span>
						<br /><br /><br />

						<form:label path="rfc" cssClass="wide">RFC</form:label>
						<form:input path="rfc" id="busquedaRfc" cssStyle="width: 300px" maxlength="12" cssClass="rfc_moral" />
						<span id="rfcError" class="error hiddenElement"></span>
						<br /><br /><br />
						
						<form:label path="nrp" cssClass="wide" id="busquedaNrpLabel">NRP</form:label>
						<form:input path="nrp" id="busquedaNrp" cssStyle="width: 300px" maxlength="13" cssClass="numerico" />
						<br /><br /><br />
						
						<form:label path="razonSocial" cssClass="wide">Raz&oacute;n Social</form:label>
						<form:input path="razonSocial" id="busquedaRazonSocial" cssStyle="width: 300px" maxlength="100" />
						<br /><br /><br />

						<form:label path="tipoSociedad.idTipoSociedad" class="wide">Tipo de Sociedad</form:label>
						<combo:creaCombo idHtml="tipoSociedad.idTipoSociedad" idHtmlContenedor="busquedaPersonaMoralForm" 
						entidad="mx.gob.imss.ctirss.delta.persistence.DicTipoSociedad" idHtmlValor="${moral.tipoSociedad.idTipoSociedad}" 
						mostrarSoloActivos="true"/>
						<br /><br />
	
						<form:label path="actaConstitutiva" cssClass="wide">Acta Constitutiva</form:label>
						<form:input path="actaConstitutiva" id="registroActaConstitutiva" cssStyle="width: 300px" maxlength="30" />
						<br /><br /><br />
							
						<form:label path="fechaCreacion" class="wide">Fecha de Creaci&oacute;n</form:label>
						<form:input path="fechaCreacion" id="busquedaFechaCreacion"	style="width: 70px" maxlength="10" />
						<span id="fechaCreacionError" class="error hiddenElement"></span>
						<span id="fechaCreacionErrorCliente" class="error hiddenElement">Formato de Fecha inv&aacute;lido</span>
						<br /><br />
									
						<form:label path="busqAprox" class="wide">Seleccione el Tipo de B&uacute;squeda</form:label>
						<form:radiobutton path="busqAprox" id="busquedaAproximada" style="width: 20px" label="Aproximada" value="true"/>
						<form:radiobutton path="busqAprox" id="busquedaExacta" style="width: 20px" label="Exacta" checked="checked" value="false"/>
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
			<table style="width: 100%" id="tablaResultadoMorales"></table>
		</div>

		<div style="float: right;">
			<input type="button" value="Tr&aacute;mite de Registro de Personas Morales" class="mboton" id="tramiteRegistroPersonasMorales"/>
		</div>
		
	</form>
</div>

<!-- En este div se presentara el cuadro de dialogo de jquery que antes era un showModalDialog de javascript -->
<div id="dgModalDatosComplementarios"></div>
<!--  -->
		

<!-- codigo javascript para gestionar el data teibol donde se presentara el resultado de la busqueda -->
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/personas/moral/busqueda/busquedaPersonaMoral.js" htmlEscape="true" />" ></script>