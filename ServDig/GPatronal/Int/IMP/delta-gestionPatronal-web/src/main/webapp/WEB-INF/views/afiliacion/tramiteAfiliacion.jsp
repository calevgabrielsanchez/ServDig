<%@ include file="../general/taglibs.jsp"%>

<%@page import="mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.CodigoRolTemporal"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoAccionAfectacionEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.Usuario"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona"%>
<script type="text/javascript" src="../../gestionMediosContacto-web/static/resources/js/delta/mediosContacto/cmpMedioContacto.js"></script>
 
<script>
	var denominacionSocial=<%=TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL.getCodigo().intValue()%>;
	var datosContacto=<%=TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO.getCodigo().intValue()%>;
	var escrituraConstitutiva=<%=TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA.getCodigo().intValue()%>;
	var registroSindicato=<%=TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO.getCodigo().intValue()%>;
	var socio=<%=TipoTramiteEnum.ACTUALIZACION_SOCIO.getCodigo().intValue()%>;
	var representanteLegal=<%=TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo().intValue()%>;

	var isOperadorIMSS=${isOperadosIMSS};
	var idSujetoObligado = '${sujetoObligado.cveIdSujetoObligado}' != "" ? '${sujetoObligado.cveIdSujetoObligado}' : null;
	idSolicitudActiva = '${idSolicitud}' != '' ? '${idSolicitud}' : 0;
	
	var idSolicitud = '${idSolicitud}' != '' ? '${idSolicitud}' : 0;
	folioSolicitudDatosPatronales = '${folioSolicitud}';
	var tipoSolicitudClasificacion = <%=TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION.getValor().intValue() %>;
	var tipoSolicitudDatosGenerales = <%=TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES.getValor().intValue() %>;
	
	var vigenteActaConstitutivaActivo="${vigenteActaConstitutivaActivo}";
	var vigenteRegistroSindicatoActivo="${vigenteRegistroSindicatoActivo}";
	var contextPath="${contextpath}";
	
	var MOSTRAR_DETALLE_MODIFICACION_SRT=<%=TipoAccionAfectacionEnum.MOSTRAR_DETALLE_MODIFICACION_SRT.getValor().intValue()%>;
	var MOSTRAR_ACUSE_DE_MODIFICACION_SRT=<%=TipoAccionAfectacionEnum.MOSTRAR_ACUSE_DE_MODIFICACION_SRT.getValor().intValue()%>;
	var MOSTRAR_AVISO_DE_MODIFICACION_SRT=<%=TipoAccionAfectacionEnum.MOSTRAR_AVISO_DE_MODIFICACION_SRT.getValor().intValue()%>;
	var MOSTRAR_ACUSE_DATOS_PATRONALES=<%=TipoAccionAfectacionEnum.MOSTRAR_ACUSE_DATOS_PATRONALES.getValor().intValue()%>;
	var MOSTRAR_DETALLE_MODIFICACION_DATOS_PATRONALES=<%=TipoAccionAfectacionEnum.MOSTRAR_DETALLE_MODIFICACION_DATOS_PATRONALES.getValor().intValue()%>;
	var SOLICITAR_ASIGNACION=<%=TipoAccionAfectacionEnum.SOLICITAR_ASIGNACION.getValor().intValue()%>
	var MOSTRAR_DETALLE_MODIFICACION_CENTRO_TRABAJO=<%=TipoAccionAfectacionEnum.MOSTRAR_DETALLE_MODIFICACION_CENTRO_TRABAJO.getValor().intValue()%>
	
	var idTipoMedioContactoEmail=<%=TipoMedioContacto.TIPO_CORREO_ELECTRONICO%>
	var idTipoMedioContactoTelefonoFijo=<%=TipoMedioContacto.TIPO_TELEFONO_FIJO%>
	var idTipoMedioContactoTelefonoMovil=<%=TipoMedioContacto.TIPO_TELEFONO_MOVIL%>
	var esNuevaSolicitud=false;
	var isRL=<%=((Usuario)session.getAttribute("usuario")).getPerfilUsuario().getIdPerfilUsuario().equals(CodigoRolTemporal.REPRESENTANTE_LEGAL.getCodigo().longValue())%>
	var rolPatron="<%=CodigoRolTemporal.PATRON_SUJETO_OBLIGADO%>";
	var rolRepresentante="<%=CodigoRolTemporal.REPRESENTANTE_LEGAL%>";
	var esPatronFisico=${bFisica};
	var idTipoPersonaFisica=<%=TipoPersona.TIPO_PERSONA_FISICA%>;
	var idTipoPersonaMoral=<%=TipoPersona.TIPO_PERSONA_MORAL%>;
</script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/common/commonMethods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/tramiteAfiliacion.js" htmlEscape="true" />"></script>


<div class="page_holder">
	<div class="contenedor" style="width: 100% !important">
		<div class="row">
			<div class="cell">
				<div class="row" id="rowDetalleSujetoObligado" style="width: 1000px;" >
					<c:set var="contextpath" value="<%=request.getContextPath()%>" />
					<jsp:include page="datosGeneralesEncabezado.jsp"/>
					<table style="display: none; width: 100%; border-width: 0px !important;"  id="infoFolio">
						<tr>
							<td class="label_patrones" style="text-align: right !important;">
								<label>
									<spring:message code="label.numero.folio" />:
								</label>
							</td>
							<td class="label_patrones" style="text-align: left !important;">
								<span id="noFolioActual"></span>
							</td>
						</tr>
					</table>
					<jsp:include page="detalleSujetoObligadoTabs.jsp"/>
					<div class="izquierda">
						<form:form id="formSupport" modelAttribute="sujetoObligado" action="${contextpath}/afiliacion/visualizarDetalleRFC ">
							<form:hidden path="fisica.idPersona"/>
							<form:hidden path="fisica.rfc"/>
							<form:hidden path="fisica.nombre"/>
							<form:hidden path="fisica.primerApellido"/>
							<form:hidden path="fisica.segundoApellido"/>
							<form:hidden path="moral.idPersona"/>
							<form:hidden path="moral.rfc"/>
							<form:hidden path="moral.razonSocial"/>
							<form:hidden path="tipoPersonaFiscal"/>
							<input type="button" id="btnEnviarSolicitud" class="mboton" name="FinalizarCapturaSolicitud"
								onclick="enviarSolicitud(validarEnvioSolicitud)" style="width:200px; "
								value="Finalizar Captura" />
							<input type="button" id="btnConcluirSolicitud" class="mboton" name="ConcluirSolicitud"
								onclick="concluirSolicitudDatosFiscales()" style="width:200px; "
								value="Concluir Solicitud" />
							<c:if test="${!isOperadosIMSS}">
								<input type="button" id="btnCancelarSolicitud" class="mboton" name="CancelarSolicitud"
									onclick="presentarSolicitudConfirmacionCancelacion()" style="width:200px; "
									value="Cancelar Solicitud" />
							</c:if>
							<c:if test="${isOperadosIMSS}">
								<input type="button" id="btnRechazarSolicitud" class="mboton" name="RechazarSolicitud"
									onclick="despliegaMensajeConfirmacionRechazo()" style="width:200px; "
									value="Rechazar Solicitud" />
							</c:if>
							<input type="submit" id="btnRegresarDetalleRFC" class="mboton" name="RegresarRFC"
							style="width:200px; "
							value="Ir al Detalle"/>
							<input type="button" id="btnBusquedaRFC" class="mboton" name="RegresarBusqueda"
								onclick="navegarABusquedaRFC()" style="width:200px; "
								value="Ir a B&uacute;squeda por RFC" />
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<form:form modelAttribute="sujetoObligado"  action="${contextpath}/afiliacion/mostrarDetalleTramite" id="detalleRPForm">
	<input type="hidden" id="idSolicitud" name="idSolicitud"/>
	<input type="hidden" id="idTramite" name="idTramite"/>
	<form:hidden path="cveIdSujetoObligado"/>
	<form:hidden path="tipoPersonaFiscal"/>
	<form:hidden path="fisica.rfc"/>
	<form:hidden path="moral.rfc"/>
</form:form>

<form:form modelAttribute="sujetoObligado"  action="" id="busquedaRFCForm">

</form:form>


<form:form modelAttribute="sujetoObligado"  action="" id="formReporteModificacionPatronal">
	
</form:form>

<div id="dialogoConfirmacion">
	<p><span id="textoConfirmacion"></span></p>
</div>
<div id="dialogoMensajes">
	<p><span id="textoMensaje"></span></p>
</div>

<div id="dialogoMensajesTramitePrecargado" title="Notificaci&oacute;n">
	<div class="page_holder" style="width:100% !important; margin: 0 0 0 0;">
		<div class="row	">
			<div class="cell form-comment">
				<span id="textoMensajeTramitePrecargado">
					La informaci&oacute;n del tr&aacute;mite ha sido precargada en la parte superior.
				</span>
			</div>
		</div>
	</div>
</div>

<div id="dgErrorSinSeleccion" title="Debe seleccionar un elemento">
	<p style="float: left; margin: 10 10px 10px 10;">
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"> </span>
		No se ha seleccionado ning&uacute;n registro para ejecutar esta acci&oacute;n.
	</p>
</div>

<div id="dialogoConcluirSolicitudFirma" style="display:none;">
	<div class="page_holder" style="width:100% !important; margin: 0 0 0 0;">
		<div class="row	">
			<div class="cell form-comment">
				<span id="textoCS">					
					<div id="dialogoConcluirSolicitud" title="Concluir solicitud">
					<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
						Seleccione la forma en la cual desea concluir la solicitud
					</p>		
					<div class="contenedor">
						<div class="row">
							<div class="cell">			
								<h2 style="font-size: 14px;">Concluir con firma digital</h2>
								<p  style="font-size: .9em;">Se le solicitara los datos de su firma digital del IMSS para poder concluir el tr&aacute;mite, 
								al ingresar sus datos de su firma digital no sera necesario presentarse posteriormente ante el Instituto.</p>					
								<c:set var="firmarSolicitudAction" value="/afiliacion/iniciaProcesoFirmaDigital" />
								<form id="formConcluirConFirma" method="post">
									<input type="hidden" id="idSolicitudActiva" name="idSolicitudActiva"/>
									<input type="hidden" id="numRegPatronal" name="numRegPatronal"/>
									<input type="hidden" id="idSujetoObligado" name="idSujetoObligado"/>
									<input type="button" id="btnConcluirConFirma" class="mboton" value="Concluir con firma digital">
								</form>			
							</div>
						</div>
					<div class="row">		
						<div class="cell">
							<h2 style="font-size: 14px;">Concluir sin firma digital</h2>
							<p  style="font-size: .9em;"> Si Ud. no cuenta con su firma digital del IMSS, podra continuar su tr&aacute;mite 
							ante el Instituto imprimiendo su Acuse de presentaci&oacute;n que acontinuaci&oacute;n el sistema le genera, posteriormente
							deber&aacute; presentarse en el Instituto.
							</p>
							<form id="formConcluirSinFirma">
								<input type="button" class="mboton" value="Concluir sin firma digital" onclick="ejecutarEnvioDeSolicitudSinFirma();">
							</form>				
						</div>				
					</div>	
					</div>
					</div>					
				</span>
			</div>
		</div>
	</div>
</div>

<div id="firmaDigitalDialogo"></div>
<jsp:include page="../common/vistaSolicitante.jsp"/>
<jsp:include page="../solicitud/razonRechazo.jsp"/>
