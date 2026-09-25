<%@ include file="../general/taglibs.jsp"%>


<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoAccionAfectacionEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.PropietarioMedioContactoEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.CodigoRolTemporal"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.Usuario"%>

<link rel="stylesheet" type="text/css" 	href='<spring:url value="/static/resources/estilos/imss/reset.css" htmlEscape="true" />' />
<link rel="stylesheet" type="text/css" 	href='<spring:url value="/static/resources/estilos/imss/style.css" htmlEscape="true" />' />

<script type="text/javascript" src="../../gestionMediosContacto-web/static/resources/js/delta/mediosContacto/cmpMedioContacto.js"></script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<script>
	var denominacionSocial=<%=TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL.getCodigo().intValue()%>;
	var datosContacto=<%=TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO.getCodigo().intValue()%>;
	var escrituraConstitutiva=<%=TipoTramiteEnum.ACTUALIZACION_ESCRITURA_CONSTITUTIVA.getCodigo().intValue()%>;
	var registroSindicato=<%=TipoTramiteEnum.ACTUALIZACION_REGISTRO_SINDICATO.getCodigo().intValue()%>;
	var socio=<%=TipoTramiteEnum.ACTUALIZACION_SOCIO.getCodigo().intValue()%>;
	var representanteLegal=<%=TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo().intValue()%>;
	var centroTrabajoNombreTramite = '<%=TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.name()%>';
	var existeSolicitudActiva = ${existeSolicitudActiva};
	var isOperadorIMSS=${isOperadosIMSS};
	var idSujetoObligado = '${sujetoObligado.cveIdSujetoObligado}' != "" ? '${sujetoObligado.cveIdSujetoObligado}' : null;
	idSolicitudActiva = '${idSolicitudDatosPatronales}' != '' ? '${idSolicitudDatosPatronales}' : 0;
	folioSolicitudDatosPatronales = '${folioSolicitudDatosPatronales}';
	var tipoSolicitudClasificacion = <%=TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION.getValor().intValue() %>;
	var tipoSolicitudDatosGenerales = <%=TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES.getValor().intValue() %>;
	var tpPropietarioRepLegal = <%=PropietarioMedioContactoEnum.REPRESENTANTE_LEGAL.getCodigo()%>;

	var vigenteActaConstitutivaActivo="${vigenteActaConstitutivaActivo}";
	var vigenteRegistroSindicatoActivo="${vigenteRegistroSindicatoActivo}";
	var contextPath="${contextpath}";
	var context="${contextpath}";
	var MOSTRAR_DETALLE_MODIFICACION_SRT=<%=TipoAccionAfectacionEnum.MOSTRAR_DETALLE_MODIFICACION_SRT.getValor().intValue()%>;
	var MOSTRAR_ACUSE_DE_MODIFICACION_SRT=<%=TipoAccionAfectacionEnum.MOSTRAR_ACUSE_DE_MODIFICACION_SRT.getValor().intValue()%>;
	var MOSTRAR_AVISO_DE_MODIFICACION_SRT=<%=TipoAccionAfectacionEnum.MOSTRAR_AVISO_DE_MODIFICACION_SRT.getValor().intValue()%>;
	var MOSTRAR_ACUSE_DATOS_PATRONALES=<%=TipoAccionAfectacionEnum.MOSTRAR_ACUSE_DATOS_PATRONALES.getValor().intValue()%>;
	var MOSTRAR_DETALLE_MODIFICACION_DATOS_PATRONALES=<%=TipoAccionAfectacionEnum.MOSTRAR_DETALLE_MODIFICACION_DATOS_PATRONALES.getValor().intValue()%>;
	var SOLICITAR_ASIGNACION=<%=TipoAccionAfectacionEnum.SOLICITAR_ASIGNACION.getValor().intValue()%>;
	var MOSTRAR_DETALLE_MODIFICACION_CENTRO_TRABAJO=<%=TipoAccionAfectacionEnum.MOSTRAR_DETALLE_MODIFICACION_CENTRO_TRABAJO.getValor().intValue()%>;
	var ACCESO_RESTRINGIDO = <%=TipoAccionAfectacionEnum.ACCESO_NO_AUTORIZADO.getValor().intValue()%>;
	var MOSTRAR_ACUSE_CENTRO_TRABAJO = <%=TipoAccionAfectacionEnum.MOSTRAR_ACUSE_CENTRO_TRABAJO.getValor().intValue()%>;
	
	var idTipoMedioContactoEmail=<%=TipoMedioContacto.TIPO_CORREO_ELECTRONICO%>;
	var idTipoMedioContactoTelefonoFijo=<%=TipoMedioContacto.TIPO_TELEFONO_FIJO%>;
	var idTipoMedioContactoTelefonoMovil=<%=TipoMedioContacto.TIPO_TELEFONO_MOVIL%>;
	var tipoPersonaFiscal = '${sujetoObligado.tipoPersonaFiscal}';
	
	var tpPropietario = null;
	var idPropietario = null;

	var PATRON_SUJETO_OBLIGADO=<%=CodigoRolTemporal.PATRON_SUJETO_OBLIGADO.getCodigo().longValue()%>;
	<c:if test="${bFisica}">
		tpPropietario = <%=PropietarioMedioContactoEnum.PERSONA_FISICA.getCodigo()%>;
		idPropietario = ${sujetoObligado.fisica.idPersona};
	</c:if>
	<c:if test="${!bFisica}">
		tpPropietario = <%=PropietarioMedioContactoEnum.PERSONA_MORAL.getCodigo()%>;
		idPropietario = ${sujetoObligado.moral.cveMoral};
	</c:if>
	
	var tpPropietarioSocioPersonaFisica;
	tpPropietarioSocioPersonaFisica = <%=PropietarioMedioContactoEnum.SOCIO.getCodigo()%>;
	var isRL = '${isRL}';
	var idSubdelegacionOperador = 	<%=((Usuario)session.getAttribute("usuario")).getCveIdSubdelegacion()%> != "" ? 
									<%=((Usuario)session.getAttribute("usuario")).getCveIdSubdelegacion()%> : 0;
</script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/detalleRPTabs.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/common/commonMethods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/ventanaAcuse.js" htmlEscape="true" />"></script>

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


<!-- 
<script>
  $(function() {
        
    $( "#accordion" ).accordion({
    	collapsible: true,
    	heightStyle: "content",
    	icons: null
    });
  });
</script>
   -->



<div class="page_holder">
	<div class="contenedor" style="width: 100% !important">
		<div class="row">
			<div class="cell">
			
			
			
				<div class="row" id="rowDetalleSujetoObligado" style="width: 1000px;border-style: solid;" >
						<h2><spring:message code="title.informacion.general"/></h2>					
						<!--jsp que contiene los datos generales del patron sujeto obligado como encabezado-->
						<br>
						<!-- <div id="accordion">  
						  <h3>Datos Generaless</h3>
						  <div>-->
						  <legend class="separadorseccion" style="width: 100% !important;">
							Informaci&oacute;n Fiscal
						</legend>
												  
						<jsp:include page="datosGeneralesEncabezado.jsp"/>
						  		
						  		
						  		
						  		
						  <!-- </div>

						  <h3>Datos fiscales</h3>
						  <div> -->
						  		<br>
								<jsp:include page="datosFiscales.jsp"/>
						  <!-- </div>
						  
						  
						  <h3>Representante(s) Legal(es)</h3>
						  <div> -->
						  		<br>
						  		<b><spring:message code="label.representante.legal"/></b>
								<table id="tbRepresentantesLegales" style="width: 100%; vertical-align: top;">											
									<thead></thead>
									<tbody style="width: 100%;"></tbody>
									<tfoot></tfoot>
								</table>
								
								
								
						  <!-- </div> -->
						  
						  <c:if test="${!bFisica}">
						  <!-- <h3>Socios</h3>
						  <div> -->
						  		<br>
						  		<b><spring:message code="label.socios"/></b>
								<table id="tbSocio" style="width: 100%; vertical-align: top;">											
											<thead></thead>
											<tbody style="width: 100%;"></tbody>
											<tfoot></tfoot>
								</table>	
						  <!-- </div>  -->
						  </c:if>
						  
						  
						  <legend class="separadorseccion" style="width: 100% !important;">
								Medios de contacto empleados por el Instituto Mexicano del Seguro Social
						  </legend>
					  		<br>
					  		<div id="mediosContactoContenedor" style="width: 100% !important;"></div>
						  
						  
  						</div>
						
							
								
								<br><br>
								<div class="izquierda">
									<form:form id="formSupport" modelAttribute="sujetoObligado" action="${contextpath}/afiliacion/crearSolicitud">
										<form:hidden path="tipoPersonaFiscal"/>
										<form:hidden path="fisica.rfc"/>
										<form:hidden path="moral.rfc"/>
										<form:hidden path="fisica.idPersona"/>
										<form:hidden path="moral.idPersona"/>
										<input type="button" id="btnGenerarSolicitud" onclick="validaPermisosDeEjecucion(callbackEvaluacionPermisosGenerarSolicitud)" class="mboton" name="generarSolicitud"
											style="width:200px; "
											value="Generar Solicitud" />
										<input type="button" id="btnConsultaSolicitudes" class="mboton" name="consultaSolicitudes"
											onclick="validaPermisosDeEjecucion(callbackEvaluacionPermisosConsultarSolicitudes)" style="width:200px;"
											value="<spring:message code="label.consulta.solicitud" />">
									</form:form>
									<!-- 
									<input type="button" id="testBtn" onclick="testcdrs()" value="Prueba Portal">
									 -->
									 
									 <!-- 
									<input type="button" id="testClasifBtn" onclick="testmsrt()" value="Prueba Clasif Portal">
									 -->
								</div>
								
								<br>
								<br>
								<form:form modelAttribute="sujetoObligado" action="${contextpath}/sujetoObligado/detalleRP" id="registrosPatronalesForm">
									<form:hidden path="tipoPersonaFiscal"/>
									<br>
									<div id="legendaRegPatronales"><h2>Registros Patronales</h2></div>
									<fieldset style="!important;">
										<table  style="border: none; ">
											<tr>
												<td class="label_patrones" style="border: none;"><spring:message code="label.nrp"/></td>
												<td style="border: none;"><form:input path="numeroRegistroPatronal" maxlength="11" size="15"/></td>
												<td style="border: none;">
													<input type="button" id="btnEnviarDetalleRegistroPatronal" class="mboton" name="Consultar"
														onclick="validaRPSeleccionado()" style="width:200px;"
														value="Consultar" />
												</td>
											</tr>
										</table>
										<table style="width: 100% !important; border: none !important;">
											<tr>
												<td colspan="4" style="border: none !important;" >
													<div id="divRegistrosPatronales">
														<table id="gridRegistrosPatronales"
																style="width: 100%; vertical-align: top;">
															<thead>
															</thead>
															<tbody style="width: 100%;">
															</tbody>
														</table>
													</div>
												</td>
											</tr>
											<tr>
												<td width="40%" style="border: none !important;">
													<div id="divTramiteClasificacion">
														<table style="border: none;">
															<tr>
																<td style="border: none;">
																	<input type="button" id="btnModificarSRT" class="mboton" 
				onclick="validaPermisosDeEjecucion(callbackEvaluacionPermisosModificarSRT)" value="Modificar clasificaci&oacute;n en el SRT">
																
																</td>
																<td style="border: none;">
																	<input type="button" id="btnModificarCentroTrabajo" class="mboton"  
																	onclick="validaPermisosDeEjecucion(callbackEvaluacionPermisosModificarCentroTrabajo)" value="Modificar centro de trabajo">
																</td>
															</tr>
														</table>
													</div>											
												</td>
											</tr>
										</table>
									</fieldset>
								</form:form>
								<form:form modelAttribute="sujetoObligado" action="${contextpath}/afiliacion/mostrarTramites" id="solicitudForm">	
									<form:hidden path="tipoPersonaFiscal"/>
									<form:hidden path="fisica.rfc"/>
									<form:hidden path="moral.rfc"/>
									<form:hidden path="fisica.idPersona"/>
									<form:hidden path="moral.idPersona"/>
									
									<br>
									<h2>Solicitudes en Proceso</h2>
									<fieldset style="!important;">
										<div id="divSolicitudes">
											<table id="gridSolicitudesProceso"
													style="width: 100%; vertical-align: top;">
													<thead>
													</thead>
													<tbody style="width: 100%;">
													</tbody>
												</table>
										</div>
										<input type="button" id="btnMostrarDetalleTramite" class="mboton" name="Detalle"
												onclick="validaSeleccionSol()" style="width:200px;"
												value="Detalle" />
									</fieldset>
									
								</form:form>
							
								<form>
									<div class="izquierda"> 
										<c:if test="${usuario.perfilUsuario.idPerfilUsuario ne rolPatronSujetoObligado}">
										<input type="button" class="mboton" name="regresar"
										onclick="navegarABusquedaRFC()" id="btnRegresarConsultaRFC"
										value="<spring:message code="label.regresar" />"></input>
										</c:if>
									</div>
								</form>
					
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
	<form:hidden path="modalidad.numModalidad"/>
	<form:hidden path="digVerificador"/>
	<form:hidden path="fisica.rfc"/>
	<form:hidden path="moral.rfc"/>
</form:form>

<form:form modelAttribute="sujetoObligado"  action="" id="formReporteModificacionPatronal">
	
</form:form>

<form:form modelAttribute="sujetoObligado"  action="" id="busquedaRFCForm">

</form:form>

<jsp:include page="../common/dialogosGenericos.jsp"/>

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
									<input type="button" id="btnConcluirConFirma" onclick="fnConcluirConFirma();" class="mboton" value="Concluir con firma digital">
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

<!-- seccion para detalle del rep legal -->
<div id="divDetalleRepLegal"   style="display:none;">
	<div class="page_holder" style="width: 550px !important;">
		<div class="contenedor">
			<div class="row" >
				<div class="cell" >
					<div class="row" id="rowDetalleSujetoObligado" >
							<legend>
								<strong>Datos de persona</strong>
							</legend>
							<table style="width: 900px !important;">
								<tr>
									<td class="label_patrones" style="width: 150px !important;">
										<label>RFC :</label>
									</td>
									<td>
										<label id="detalleRepLegalRFC"></label>
									</td>
								</tr>
								<tr>
									<td class="label_patrones">
										<label>CURP :</label>
									</td>
									<td>
										<label id="detalleRepLegalCURP"></label>
									</td>
								</tr>
								<tr>
									<td class="label_patrones">
										<label>Primer Apellido :</label>
									</td>
									<td>
										<label id="detalleRepLegalPrimerApellido"></label>
									</td>
								</tr>
								<tr>
									<td class="label_patrones">
										<label>Segundo Apellido :</label>
									</td>
									<td>
										<label id="detalleRepLegalSegundoApellido"></label>
									</td>
								</tr>
								<tr>
									<td class="label_patrones">
										<label>Nombre(s) :</label>
									</td>
									<td>
										<label id="detalleRepLegalNombre"></label>
									</td>
								</tr>
								<tr>
									<td align="center" class="label_patrones" colspan="2">
										&iquest;Poder para Actos de administraci&oacute;n o dominio?: &nbsp;<label id="indActAdmonDominioDetalle"></label>
									</td>
								</tr>
							</table>			
							<legend>
								<strong>Datos de Contacto</strong>
							</legend>
							
							<div id="divDetalleMediosContactoRepLegal"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
</div>

<div id="dgDetalleSocio" style="display: none;">
	<jsp:include page="socios/detalleSocio.jsp"></jsp:include>
</div>

<jsp:include page="popUpDetalleRegistroPatronal.jsp"/>

<div id="listaTramitesClasificacion" title="Tr&aacute;mites" style="display: none;" >
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
		Existe un tr&aacute;mite de modificaci&oacute;n al SRT en curso espere a su conclusi&oacute;n para iniciar un nuevo tr&aacute;mite.
	</p>
</div>

<form:form modelAttribute="sujetoObligado" id="clasificacionInvokerForm">
	<form:hidden path="cveIdSujetoObligado"/>
	<form:hidden path="numeroRegistroPatronal"/>
	<form:hidden path="tipoPersonaFiscal"/>
</form:form>

<form:form modelAttribute="sujetoObligado" id="centroTrabajoInvokerForm">
	<form:hidden path="cveIdSujetoObligado"/>
	<form:hidden path="numeroRegistroPatronal"/>
	<form:hidden path="tipoPersonaFiscal"/>
	<form:hidden path="fisica.idPersona"/>
	<form:hidden path="moral.idPersona"/>								
</form:form>

<div id="dialogoMensajes">
	<p><span id="textoMensaje"></span></p>
</div>
<div id="cdrsContainer"></div>
<div id="msrtContainer"></div>
<form id="formaRegresoDetalle" name="formaRegresoDetalle" action="${contextpath}/clasificacion/mostrarAcuse" method="POST" target="_blank"></form>