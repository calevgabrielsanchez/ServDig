<%@ include file="../general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<script type="text/javascript">
	var context="${contextpath}";
</script>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/common/commonMethods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/portal/afiliacion/listaTramiteModificacionSRT.js" htmlEscape="true" />"></script>

<style>
	#selectable .ui-selecting { background: #FECA40; }
	#selectable .ui-selected { background: #F39814; color: white; }
	#selectable { list-style-type: none; margin: 0; padding: 0; width: 100%; }
	#selectable li { margin: 3px; padding: 0.4em; font-size: 1.0em; height: 18px; }
	
	a:active {
		outline: none;
	}
	a:focus {
		-moz-outline-style: none;
	}
</style>


<div id="listaTramitesClasificacion" title="Tr&aacute;mites" style="display: none;" >
	<ol id="selectable">
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.ACTIVIDAD_ECONOMICA.getCodigo()%>">Cambio de actividad econ&oacute;mica</li>
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.DISPOSICION_DE_LEY.getCodigo()%>">Cambio por disposici&oacute;n de Ley, o del RACERF</li>
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.INCORPORACION_DE_ACTIVIDADES.getCodigo()%>">Incorporaci&oacute;n de actividades</li>
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.COMPRA_DE_ACTIVOS.getCodigo()%>">Compra de Activos</li>
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.COMODATO.getCodigo()%>">Comodato</li>
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.ENAJENACION.getCodigo()%>">Enajenaci&oacute;n</li>
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.ARRENDAMIENTO.getCodigo()%>">Arrendamiento</li>
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.FIDEICOMISO_TRASLATIVO.getCodigo()%>">Fideicomiso traslativo</li>
	</ol>
</div>


<form:form modelAttribute="sujetoTramite" id="clasificacionInvokerForm">
	<form:hidden path="cveIdSujetoObligado"/>
	<form:hidden path="numeroRegistroPatronal"/>
	<form:hidden path="modalidad.numModalidad"/>
	<form:hidden path="digVerificador"/>
	<form:hidden path="tipoPersonaFiscal"/>
	<form:hidden path="fisica.idPersona"/>
	<form:hidden path="fisica.rfc"/>
	<form:hidden path="moral.idPersona"/>
	<form:hidden path="moral.rfc"/>
</form:form>

<div id="dialogoMensajes">
	<p><span id="textoMensaje"></span></p>
</div>
