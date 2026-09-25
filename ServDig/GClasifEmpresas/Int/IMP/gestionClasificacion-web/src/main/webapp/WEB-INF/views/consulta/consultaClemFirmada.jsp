<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ include file="../general/taglibs.jsp"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!--Empieza contenido-->

<%@page import="mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisEnum"%>
<%@page import="mx.gob.imss.ctirss.delta.model.clasificacion.CodigoRolClasificacion"%>
<%@page import="mx.gob.imss.ctirss.delta.gestion.clasificacion.web.utils.Constantes"%>
<%@page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal"%>

<script>

$(document).ready(function() {
	$( "#strPeriodoInicio" ).datepicker();
	$( "#strPeriodoInicio" ).datepicker( "option", "dateFormat", 'dd/mm/yy' );
	$( "#strPeriodoFin" ).datepicker();
	$( "#strPeriodoFin" ).datepicker( "option", "dateFormat", 'dd/mm/yy' );

	$( "#strPerIniF" ).datepicker();
	$( "#strPerIniF" ).datepicker( "option", "dateFormat", 'dd/mm/yy' );
	$( "#strPerFinF" ).datepicker();
	$( "#strPerFinF" ).datepicker( "option", "dateFormat", 'dd/mm/yy' );

});

</script>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/consulta/consultaClemFirmada.js" htmlEscape="true" />"></script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<!-- Obtenemos el rol del usuario firmado -->
<c:set var="mensaje" value="${mensaje}"/>
<input type="hidden" id="subdelegacionUser" name="subdelegacionUser" value="${usuario.usuarioFuncionario.subdelegacion.id}"/>
<input type="hidden" id="delegacionUser" name="delegacionUser" value="${usuario.usuarioFuncionario.delegacion.id}"/>

<div class="site_position_center">
    <div class="page_holder_no_height">
		<div id="mensaje">${mensaje}</div>
	</div>
</div>
	
<div class="page_holder_no_height">
	<div class="post_entry_wide no-border">

		<div class="form-comment">
			
			<form id="formFiltros">
				<fieldset>
					<legend>
						<strong><spring:message code="label.filtros.busqueda" /></strong>
					</legend>

					<fieldset class="fsInterno">
						<!-- elemento SPAN para mostrar el error del campo especifico. -->
						<span id="strPeriodoInicioError" class=" hiddenElement error"></span>
						<span id="strPeriodoFinError" class=" hiddenElement error"></span>
						
						<label class="wide"><spring:message code="label.filtros.busqueda.periodo.determinado" />:</label> 
						<input name="strPeriodoInicio" id="strPeriodoInicio" style="width: 100px;  text-align: center" type="text" maxlength="10" title="dd/MM/aaaa"/> 
						<label class="wide" for="periodoFin" style="width: 20px"> a</label> 
						<input name="strPeriodoFin" id="strPeriodoFin" style="width: 100px;  text-align: center" type="text" maxlength="10" title="dd/MM/aaaa"/>
					</fieldset>

					<fieldset class="fsInterno">
						<!-- elemento SPAN para mostrar el error del campo especifico. -->
						<span id="strPerIniFError" class=" hiddenElement error"></span>
						<span id="strPerFinFError" class=" hiddenElement error"></span>
						
						<label class="wide"><spring:message code="label.filtros.busqueda.periodo.firma" />:</label> 
						<input name="strPerIniF" id="strPerIniF" style="width: 100px;  text-align: center" type="text" maxlength="10" title="dd/MM/aaaa"/> 
						<label class="wide" for="periodoFin" style="width: 20px"> a</label> 
						<input name="strPerFinF" id="strPerFinF" style="width: 100px;  text-align: center" type="text" maxlength="10" title="dd/MM/aaaa"/>
					</fieldset>

					<fieldset class="fsInterno">
						<span id="registroPatronalError" class=" hiddenElement error"></span>
						<label class="wide"><spring:message code="label.detalle.registro.patronal" />:</label> 
						<input name="registroPatronal" id="registroPatronal" onchange="this.value=this.value.toUpperCase();" style="width: 100px" type="text" maxlength="11" />
					</fieldset>


					<div style="text-align: right; float: right;">
						<input type="button" value="Buscar" class="mboton" style="width: 120px;" id="boton" />
					</div>
				</fieldset>
				
			</form>
		</div>
		<div>
			<table style="width: 100%"  id="tableSolicitudesConcluidas">
				
			</table>
		</div>

	</div>
</div>