<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum"%>
<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<script>
	var denominacionSocial=<%=TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL.getCodigo().intValue()%>;
	var datosContacto=<%=TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO.getCodigo().intValue()%>;
	var escrituraConstitutiva=<%=TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA.getCodigo().intValue()%>;
	var registroSindicato=<%=TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO.getCodigo().intValue()%>;
	var socio=<%=TipoTramiteEnum.ACTUALIZACION_SOCIO.getCodigo().intValue()%>;
	var representanteLegal=<%=TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo().intValue()%>;
	var actualizacionDatosPatronales=<%=TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES.getValor().intValue()%>;
	var modificacionSRT=<%=TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION.getValor().intValue()%>;
	var centroTrabajo=<%=TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO.getValor().intValue()%>;
	var altaPatronal=<%=TipoSolicitudEnum.ALTA_PATRONAL.getValor().intValue()%>;
	var estadoPresentarseVentailla=<%=EstadoSolicitudEnum.PRESENTARSE_EN_VENTANILLA.getCodigo()%>;
	var estadoProcesarEnBackOffice=<%=EstadoSolicitudEnum.PARA_PROCESAR_BACKOFFICE.getCodigo()%>;
	var context="${contextpath}";
	var context_path="${contextpath}";
	var esRepresentante=${isRL};
	var nombreTramiteCentroTrabajo='<%=TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.name()%>';
</script>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/consultar.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/common/commonMethods.js" htmlEscape="true" />"></script>

<link rel="stylesheet" type="text/css" 	href='<spring:url value="/static/resources/estilos/imss/menuLateralStyle.css" htmlEscape="true" />' />

<style>
	<!--Data table style-->
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

<div class="page_holder">
	<div class="contenedor" style="width: 100% !important">
		<div class="row" style="width: 1000px;">
			<c:if test="${!isRL}">
				<div class="cell" id="menuVertical" style="width: 200px;">
					<div class="row" id="divMenu">
						<div class="cell" id="celdaPadre">
							<br><br><br>
							<ul id="menuLateral">
								 <li><a href="#">B&uacute;squedas</a>
									 <ul>
									 	<li><a href="#" onclick="despliegaBusquedaRFC()">Consultar RFC</a></li>
									 	<li><a href="#" onclick="despliegaBusquedaRP()">Consultar Detalle de RP</a></li>
									 </ul>
								 </li>
								 <li><a href="#">Movimientos</a>
									 <ul>
										 <li><a href="#" onclick="despliegaCrearSolicitudAfiliacion()">Modificar Datos de Afiliaci&oacute;n</a></li>
									 	<li><a href="#" onclick="despliegaModificacionSRT()">Modificar Clasificaci&oacute;n de SRT</a></li>
									 	<li><a href="#" onclick="despliegaModificacionCentroTrabajo()">Modificar Centro de Trabajo</a></li>
									 </ul>
								 </li>
								 <li><a href="#">Solicitudes</a>
									 <ul>
										 <li><a href="#" onclick="despliegaBusquedaSolicitud()">B&uacute;squeda de Solicitudes</a></li>
										 <li><a href="#" onclick="invokarCUConsultaSolicitud()">Consulta Avanzada de Solicitudes</a></li>
									 </ul>
								 </li>
							 </ul> 
						</div>
					</div>
				</div>
			</c:if>
			<div class="cell" id="cuerpoPagina" style="width: 800px;">
				<div class="row" id="divSujetoObligado" >
					<div class="cell" id="captura">
						<br>
						<div class="row">
							<h2>DELTA GESTI&Oacute;N PATRONAL</h2>
						</div>
						
						<c:set var="contextpath" value="<%=request.getContextPath()%>" />
						<form:form modelAttribute="socio" action="${contextpath}/sujetoObligado/detalleSujetoObligado" id="patronForm">
						
							<input type="hidden" id="cveIdSujetoObligado"/>
							
							<div id="divInformacionRFC" style="display: none;">				
								<c:if test="${!isRL}">
									<fieldset style="margin: 20px !important;">
									<legend>
										<strong>
											<spring:message code="title.datos.patron" />
										</strong>
									</legend>	
									<form:errors path="*" cssClass="error" />
									<table >
										<tr>
											<td class="label_patrones">
												<label>
													<spring:message code="label.rfc" />
												</label>
											</td>
											<td >
												<form:input path="rfc" maxlength="13" />
											</td>
											<td >
												<input type="button" id="btnInvDetalleRFC" onclick="invocarDetalleRFC();" class="mboton" name="Consultar"
												value="<spring:message code="label.consultar.rfc" />"/>
											</td>
										</tr>
										<tr>
											<td colspan="3">
												<input type="button" id="btnCrearSolAfil" onclick="crearSolicitudAfiliacion();" class="mboton" name="Consultar"
													value="<spring:message code="label.modificar.afiliacion" />"/>
											</td>
										</tr>
									</table>
									</fieldset>				
								</c:if>
							</div>		
							<div id="gridRepresentados">
							
								<c:if test="${isRL}">
									<fieldset style="margin: 20px !important;">
									<legend>
										<strong>
											<spring:message code="title.datos.patron" />
										</strong>
									</legend>
									<form:hidden path="rfc" id="hrfc"/>
									
										
										<label>
											Buscar:
										</label>
										<input id="txtBuscar" name="txtBuscar" maxlength="50" />
									
										<table id="gridPersona" style="width: 100%; vertical-align: top;">
											<thead>
											</thead>
											<tbody style="width: 100%;">
											</tbody>
											<tfoot>
											</tfoot>
										</table>
										<div class="derecha">
											<input type="button" class="mboton" name="ConsultarRL"
												onclick="validaSeleccion()"
												value="<spring:message code="label.ver.detalle" />"/>
										</div>
									</fieldset>	
								</c:if>
							</div>
							
							<c:if test="${!isRL}">
								<div id="divRp" style="display: none;">		
									<fieldset style="margin: 20px !important;">
										<table>
											<tr>
												<td class="label_patrones">
													<legend>
														<strong>
															<spring:message code="label.nrp" />
														</strong>
													</legend>
												</td>
												<td>
													<input type="text" id="numRegistroPatronal" maxlength="11" />
												</td>
												<td>
													<input type="button" id="btnConsultarDetalleRp" onclick="navegarADetalleDeRP();" class="mboton" name="Consultar"
												value="Ver detalle"/>
												</td>
											</tr>
											<tr>
												<td colspan="3">
													<input type="button" id="btnCrearTramiteModSRT" style="width: 265px" onclick="crearTramiteClasificacion();" class="mboton" name="Consultar"
														value="Modificar clasificaci&oacute;n en el SRT"/>
													<input type="button" id="btnModificarCentroTrabajo" class="mboton" 
																		onclick="desplegarCentroTrabajo()" value="Modificar centro de trabajo">
												</td>
											</tr>
										</table>
										
									</fieldset>
									</div>
									<div id="divSolicitudes" style="display: none;">
										<fieldset style="margin: 20px !important;">
											<legend>
												<strong>
													<spring:message code="title.busqueda.solicitudes" />
												</strong>
											</legend>
											
											
											<table style="width: 100%;">
												<tr>
													<td  class="label_patrones" style="width: 250px !important;">
														Ingrese dato para filtrar solicitudes:
													</td>
													<td>
														<input id="txtBuscarSol" name="txtBuscarSol" size="55" maxlength="50"/>	
													</td>
													<td>
														<input type="button" onclick="invokarCUConsultaSolicitud();" class="mboton" style="width: 180px !important;" name="Consultar"
														value="<spring:message code="label.consulta.solicitud" />"/>	
													</td>
												</tr>
											</table>
											
											
											
											<table id="gridSolicitudesProceso"
													style="width: 100%; vertical-align: top;">
													<thead>
													</thead>
													<tbody style="width: 100%;">
													</tbody>
											</table>
											<div class="derecha">
												<input type="button" class="mboton" name="ConsultarSol"
													onclick="validaSeleccionSol()"
													value="<spring:message code="label.ver.detalle" />"/>
											</div>
											
										</fieldset>
									</div>
								</c:if>
								
						</form:form>
						
						<form action="" id="patronForm1" method="POST">
							<input type="hidden" id="numSolicitud" name="numSolicitud"/>
							<input type="hidden" id="idTramite" name="idTramite"/>
							<input type="hidden" id="idSolicitud" name="idSolicitud"/>
						</form>
						
						<form:form modelAttribute="sujetoObligado" action="${contextpath}/afiliacion/mostrarTramites" id="patronSolicitud" method="POST">
							<form:hidden path="fisica.rfc"/>
							<form:hidden path="tipoPersonaFiscal"/>
							<form:hidden path="moral.rfc"/>
						</form:form>
						
						<form:form action="${contextpath}/sujetoObligado/crearSolicitudAfiliacion" modelAttribute="sujetoObligado" id="solicitudAfiliacionForm" method="POST">
							<form:hidden path="fisica.rfc"/>
							<form:hidden path="tipoPersonaFiscal"/>
							<form:hidden path="moral.rfc"/>
						</form:form>
						
						<form:form action="${contextpath}/clasificacion" modelAttribute="sujetoObligado" id="solicitudClasificacionForm" method="POST">
							<form:hidden path="numeroRegistroPatronal"/>
						</form:form>
						
						<form:form modelAttribute="filtroSolicitud" action="${contextpath}/solicitud/mostrarDetalleSolicitud" id="detalleSolicitudForm" method="POST">
							<form:hidden path="idSolicitud"/>
						</form:form>
						
						<form:form modelAttribute="sujetoObligado"  id="registrosPatronalesForm" method="POST">
							<form:hidden path="numeroRegistroPatronal"/>
						</form:form>
						<div id="dgErrorSinSeleccion" style="display: none;" title="Debe seleccionar un elemento">
							<p style="float: left; margin: 10 10px 10px 10;">
								<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"> </span>
								No se ha seleccionado ning&uacute;n registro para ejecutar esta acci&oacute;n.
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<form:form modelAttribute="sujetoObligado" id="centroTrabajoInvokerForm" method="POST">
	<form:hidden path="cveIdSujetoObligado"/>
	<form:hidden path="numeroRegistroPatronal"/>
	<form:hidden path="tipoPersonaFiscal"/>
	<form:hidden path="fisica.idPersona"/>
	<form:hidden path="moral.idPersona"/>								
</form:form>


<div id="dialogoMensajes">
	<p><span id="textoMensaje"></span></p>
</div>

<div id="listaTramitesClasificacion" title="Tr&aacute;mites" style="display: none;">
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
