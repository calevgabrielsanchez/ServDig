<%@ include file="../../general/taglibs.jsp"%>

<style>
	#selectable .ui-selecting { background: #FECA40; }
	#selectable .ui-selected { background: #F39814; color: white; }
	#selectable { list-style-type: none; margin: 0; padding: 0; width: 100%; }
	#selectable li { margin: 3px; padding: 0.4em; font-size: 1.0em; height: 18px; }
	#tabs_wrapper {
	width: 422px;
	background: white;
}
.etabs { margin: 0; padding: 0; }
.tab { display: inline-block; zoom:1; *display:inline; background: #eee; border: solid 1px #0A6659; border-bottom: none; -moz-border-radius: 4px 4px 0 0; -webkit-border-radius: 4px 4px 0 0; }
.tab a { font-size: 14px; line-height: 2em; display: block; padding: 0 10px; outline: none; }
.tab a:hover { text-decoration: underline; }
.tab.active { background: #fff; padding-top: 6px; position: relative; top: 1px; border-color: #665; }
.tab a.active { font-weight: bold; }
.tab-container .panel-container { background: #fff; border: solid #666 1px; padding: 10px; -moz-border-radius: 0 4px 4px 4px; -webkit-border-radius: 0 4px 4px 4px; }
</style>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum"%>

<script>
	var idSujetoObligado = '${sujetoObligado.cveIdSujetoObligado}' != "" ? '${sujetoObligado.cveIdSujetoObligado}' : null;
	var denominacionSocial=<%=TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL.getCodigo().intValue()%>;
	var datosContacto=<%=TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO.getCodigo().intValue()%>;
	var escrituraConstitutiva=<%=TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA.getCodigo().intValue()%>;
	var registroSindicato=<%=TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO.getCodigo().intValue()%>;
	var socio=<%=TipoTramiteEnum.ACTUALIZACION_SOCIO.getCodigo().intValue()%>;
	var representanteLegal=<%=TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo().intValue()%>;	

</script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/detalleRPTabs.js" htmlEscape="true" />"></script>


<div id="tabs">
	<ul>
		<li><a href="#tabs-datGenerales">Raz&oacute;n/Denominaci&oacute;n Social</a></li>
		<li><a href="#tabs-repLegal">Representante Legal</a></li>
		<li><a href="#tabs-socios">Socios</a></li>
		<li><a href="#tabs-actConstitutiva">Acta Constitutiva</a></li>
		<li><a href="#tabs-regSindicato">Registro de Sindicato</a></li>
	</ul>
	<div id="tabs-datGenerales">
		<jsp:include page="../../afiliacion/datosGenerales.jsp" />
	</div>
																			
	<div id="tabs-repLegal">
		<p>Aquí va el jsp de representante legal.</p>
	</div>
	<div id="tabs-socios">
		<p>Aquí va el jsp de  Socios.</p>
	</div>
	<div id="tabs-actConstitutiva">
		<p>Aquí va el jsp de Acta constitutiva.</p>
	</div>
	<div id="tabs-regSindicato">
		<p>Aquí va el jsp de Registro de Sindicato.</p>
	</div>
</div>

				
<div id="tramites" class="demo" title="Tr&aacute;mites">
	<ol id="selectable">
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.ACTIVIDAD_ECONOMICA.name()%>">Cambio de actividad econ&oacute;mica</li>
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.DISPOSICION_DE_LEY.name()%>">Cambio por disposici&oacute;n de Ley, o del RACERF</li>
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.INCORPORACION_DE_ACTIVIDADES.name()%>">Incorporaci&oacute;n de actividades</li>
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.COMPRA_DE_ACTIVOS.name()%>">Compra de Activos</li>
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.COMODATO.name()%>">Comodato</li>
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.ENAJENACION.name()%>">Enajenaci&oacute;n</li>
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.ARRENDAMIENTO.name()%>">Arrendamiento</li>
		<li class="ui-widget-content" id="<%=TipoTramiteEnum.FIDEICOMISO_TRASLATIVO.name()%>">Fideicomiso traslativo</li>
	</ol>
</div>


<div id="tramitesClasificacionExistentes" class="demo" title="Tr&aacute;mites">
	<p style="float: left; margin: 10 10px 10px 10;">
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"> </span>
		Existe un trámite de modificación al SRT en curso espere a su conclusi&oacute;n para iniciar un nuevo tr&aacutemite.
	</p>
</div>

<div id="dgErrorSinSeleccion" title="Debe seleccionar un elemento">
	<p style="float: left; margin: 10 10px 10px 10;">
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"> </span>
		No se ha seleccionado ning&uacute;n registro para ejecutar esta acci&oacute;n.
	</p>
</div>

<div id="dgDialogoNoProcedeTramite" title="Tramite no procede">
	<p style="float: left; margin: 10 10px 10px 10;">
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"> </span>
		Actualmente existe un tr&aacute;mite del mismo tipo en curso, por favor espere a que el tr&aacute;mite sea conclu&iacute;do para iniciar uno nuevo.
	</p>
</div>