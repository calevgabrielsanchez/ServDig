<%@ include file="../../general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>

<script>
	var denominacionSocial=<%=TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL.getCodigo().intValue()%>;
	var datosContacto=<%=TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO.getCodigo().intValue()%>;
	var escrituraConstitutiva=<%=TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA.getCodigo().intValue()%>;
	var registroSindicato=<%=TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO.getCodigo().intValue()%>;
</script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/tramiteDatosGenerales.js" htmlEscape="true" />"></script>

<c:set var="denominacionSocial" value="<%=TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL.getCodigo().intValue()%>" />
<c:set var="datosContacto" value="<%=TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO.getCodigo().intValue()%>" />
<c:set var="escrituraConstitutiva" value="<%=TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA.getCodigo().intValue()%>" />
<c:set var="registroSindicato" value="<%=TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO.getCodigo().intValue()%>" />
<div class="page_holder">
	<div class="contenedor" style="width: 100% !important">
		<div class="row">
			<div class="cell" >
				<div class="row" id="datoGeneralesPatron" style="width: 1000px;" >
					<form>
						<div class="izquierda">
							<input type="button" class="mboton" name="regresar"
							onclick="history.back();"
							value="<spring:message code="label.regresar" />"></input>
						</div>
					</form>
					<div class="cell form-comment" id="capturaDG"
						style="float: right; width: 600px !important; height: 100% !important;">
						<c:if test="${idTramite==denominacionSocial}">
							<jsp:include page="denominacion/denominacionSocial.jsp" />
						</c:if>
						<c:if test="${idTramite==datosContacto}">
							<jsp:include page="contacto/tramiteDatoContacto.jsp" />
						</c:if>
						<c:if test="${idTramite==escrituraConstitutiva}">
							<jsp:include page="acta/tramiteActaConstitutiva.jsp" />
						</c:if>
						<c:if test="${idTramite==registroSindicato}">
							<jsp:include page="sindicato/tramiteSindicato.jsp" />
						</c:if>
						<form>
						<div class="izquierda">
							<input type="button" class="mboton" name="cancelar" id="cancelar"
							onclick="abrirAdvertencia(${idTramite}, ${idSolicitud});" value="Cancelar"></input>
						</div>
					</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="cancelarSol" title="Advertencia!">
	<p><span class="ui-icon ui-icon-alert"
		style="float: left; margin: 0 7px 20px 0;"> </span>
		Est&aacute; seguro que quiere cancelar la solicicitud?
	</p>
</div>