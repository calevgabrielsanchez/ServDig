<%@ include file="../../../../../general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum"%>	
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.FraccionEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.DELTA_SESSION_VARIABLES"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/common/commonMethods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/date.format.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/movPat/wizard/modificacion/patron/altaPatronal/localizarSubdelegacion.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/movPat/wizard/modificacion/patron/clasificacion/modificacionSRT.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/movPat/wizard/modificacion/patron/clasificacion/contenido.js" htmlEscape="true" />"></script>

<%--

<script type="text/javascript" src="${mvn.url.imssdigitalClas}/resources/js/clasificador/clasificacion.js"></script>

 --%>

<script type="text/javascript" src="${staticResourcesPath}/js/clasificador/clasificacion.js"></script>

<c:set var="sujetoObligado" value="${sujetoObligado}" />
<c:set var="objClasificacion" value="${sujetoObligado.clasificacion}" />
<c:set var="fraccion" value="${objClasificacion.fraccion}" />
<c:set var="fisica" value="${sujetoObligado.fisica}" />
<c:set var="moral" value="${sujetoObligado.moral}" />
<c:set var="proceso" value="${sujetoTramite.proceso}" />
<c:set var="tramite" value="${idTramite}" />
<c:set var="codigo" value="${idTramite.codigo}" />
<c:set var="codigoCentroTrabajo" value="<%=TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo()%>"/>
<c:set var="sujetoTramite" value="${sujetoTramite}" />
<c:set var="idSolicitud" value="${idSolicitud}" />
<c:set var="typeLogin" value="<%=session.getAttribute(\"showFinalizarFD\")%>" />
<c:set var="idOrigenSolicitud" value="<%=session.getAttribute(DELTA_SESSION_VARIABLES.KEY_ORIGEN_SOLICITUD)%>" />
<c:set var="idOrigenInternet" value="<%=OrigenSolicitudEnum.INTERNET.getId()%>" />


<c:if test="${codigo==21}">
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/messages_es.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/movPat/wizard/modificacion/patron/clasificacion/calculoPrima.js" htmlEscape="true" />"></script>
</c:if>
<c:if test="${codigo==21 || codigo == 20 || codigo == 175 || codigo == 176}">
<script type="text/javascript" src="<spring:url value="/static/resources/js/componenteBusquedaRP/componenteBusquedaRP.js" htmlEscape="true" />"></script>
</c:if>

<script>
	var idOrigenSolicitud = ${idOrigenSolicitud},
	idOrigenVENTANILLA = <%=OrigenSolicitudEnum.VENTANILLA.getId()%>,
	idOrigenINTERNET = <%=OrigenSolicitudEnum.INTERNET.getId()%>,
	fraccionAgricultura = '<%=FraccionEnum.AGRICULTURA.getCodigo()%>',
	esOperador = ${esOperador},
	idSujetoObligado = '${sujetoObligado.cveIdSujetoObligado}',
	tipoPersonaFiscal = '${sujetoObligado.tipoPersonaFiscal}',
	context_path = '<%=request.getContextPath()%>',
	idDelegacionSeleccionada = '${sujetoTramite.clasificacion.sujetoObligado.subdelegacion.delegacion.clave}',
	idSubdelegacionSeleccionada='${sujetoTramite.clasificacion.sujetoObligado.subdelegacion.id}',
	idMunicipioIMSS='${sujetoObligado.municipioIMSS.idMunicipio}',
	telefonoPrincipalCompleto  = '${ctTelefonoFijo}',
	telefonoSecundarioCompleto = '${ctTelefonoFijo2}',
	origenVENTANILLA = '<%=OrigenSolicitudEnum.VENTANILLA.getId()%>',
	origenApp = '<%=request.getSession().getServletContext().getInitParameter("ORIGEN_APP")%>';
	
	
	<c:if test="${fisica != null}">
	var rfcSujetoObligado='${fisica.rfc}',
	idPersona = '${fisica.idPersona}',
	idPersonaFisica = '${fisica.cveFisica}',
	idTipoPersona = 1;
	</c:if>
	<c:if test="${moral != null}">
	var rfcSujetoObligado='${moral.rfc}',
	idPersona = '${moral.idPersona}',
	idTipoPersona = 2;
	</c:if>

	var idClasificacion='${objClasificacion.id}',
	idTramite = '${tramite}',
	idTipoTramiteCambioDispLey = <%=TipoTramiteEnum.DISPOSICION_DE_LEY.getCodigo()%>,
	idTipoTramiteAlta = <%=TipoTramiteEnum.ALTA_SRT.getCodigo()%>,
	codigo = <%=TipoTramiteEnum.COMPRA_DE_ACTIVOS.getCodigo()%>,
	mostrarBienes = ${codigo} == <%=TipoTramiteEnum.COMPRA_DE_ACTIVOS.getCodigo()%> || ${codigo} == <%=TipoTramiteEnum.COMODATO.getCodigo()%> || ${codigo} == <%=TipoTramiteEnum.ENAJENACION.getCodigo()%> || ${codigo} == <%=TipoTramiteEnum.ARRENDAMIENTO.getCodigo()%> || ${codigo} == <%=TipoTramiteEnum.FIDEICOMISO_TRASLATIVO.getCodigo()%>.
	mostrarCentroTrabajo = ${codigo} == <%=TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo()%>,
	mostrarDomicilioCentroTrabajo = ${codigo} == <%=TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO.getCodigo()%>,
	mostrarFusionSustitucion = ${codigo} == <%=TipoTramiteEnum.FUSION.getCodigo()%> || ${codigo} == <%=TipoTramiteEnum.SUSTITUCION_PATRONAL.getCodigo()%> || ${codigo} == <%=TipoTramiteEnum.SUSTITUCION_PATRONAL_SUBCONTRATACION.getCodigo()%> || ${codigo} == <%=TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO.getCodigo()%>,
	identificadorTramite = ${codigo};
	
	<c:if test="${idSolicitud != null}">
	var idSolicitud = ${idSolicitud};
	</c:if>
	<c:if test="${idSolicitud == null}">
	var idSolicitud = 0;
	</c:if>
	<c:if test="${not empty mensajeError}">
	var hasMensajeError = true;
	</c:if>
	<c:if test="${empty mensajeError}">
	var hasMensajeError = false;
	</c:if>

	var fechaPresentacion = "${sujetoTramite.clasificacion.fecPresentacion}",
	fechaEfecto = "${sujetoTramite.clasificacion.fecEfecto}",
	equipoTransporte = '${sujetoTramite.cuentaConTransporte}',
	registroPatronal = '${sujetoObligado.numeroRegistroPatronal}'+'${sujetoObligado.modalidad.numModalidad}'+'${sujetoObligado.digVerificador}',
	indReintento = ${indReintento},
	indRPCInvalido = ${indRPCInvalido},
	codigoTramite = <%=((TipoTramiteEnum) request.getAttribute("idTramite")).getCodigo()%>,
	sessionId = '<%=request.getSession().getId()%>',
	tipoContactoTelefonoFijo = <%=TipoMedioContacto.TIPO_TELEFONO_FIJO%>,
	tipoContactoCorreoElectronico = <%=TipoMedioContacto.TIPO_CORREO_ELECTRONICO%>,
	codigoTipoSolicitud = ${codigoTipoSolicitud},
	arrayCodigoTipoTramite = [codigoTramite],
	descripcionTipoSolicitud = '${descripcionTipoTramite}';//'${descripcionTipoSolicitud}';
	
	<c:if test="${sujetoTramite.clasificacion.indProductorCana != null}">
	var isProductorCana=${sujetoTramite.clasificacion.indProductorCana};
	</c:if>
	<c:if test="${sujetoTramite.clasificacion.indProductorCana == null}">
	var isProductorCana = 0;
	</c:if>
	
	var indPrimaSugerida = 0;
	<c:if test="${sujetoTramite.clasificacion.indPrimaSugerida != null}">
		indPrimaSugerida = "${sujetoTramite.clasificacion.indPrimaSugerida}";
	</c:if>	
	
	var indSolSimilares = "";
	<c:if test="${sujetoTramite.clasificacion.indSolSimilares != null}">
		indSolSimilares = "${sujetoTramite.clasificacion.indSolSimilares}";
	</c:if>	
	
	var datosEntradaFirma = {
		fechaElectronica : '${datosFirmaElectronica.fechaElectronicaFormateada}',
		nombreCompleto : '${datosFirmaElectronica.nombreCompleto}',
		registroPatronal : '${datosFirmaElectronica.registroPatronal}',
		rfc : '${datosFirmaElectronica.rfc}',
		curp : '${datosFirmaElectronica.curp}'
	};

	var origenInternet = ${origenInternet};
	
	if(origenInternet){
		var context =  context_path + '/movPat/internet/clasificacion/';
	} else {
		var context =  context_path + '/movPat/clasificacion/';
	}
		

	
</script>


<style>
	label {
		display: inline;
	}
	
	.table_form table {
		margin: 15px auto;
	}
	
	.table_form table tr td {
		padding: 5px 10px;
	}
	
	textarea {
		height: 100%;
	}
	
	.row_selected {
		background-color: #4D92DF !important;
		color: #EFFAEF !important;
	}
	
	.row_selected td {
		background-color: #4D92DF !important;
		color: #EFFAEF !important;
	}
	
	input,textarea,.uneditable-input {
		width: auto;
	}
	
	table {
		max-width: 100%;
		background-color: transparent;
		border-collapse: collapse;
		border-spacing: 0;
	}
	
	.table-word-wrap-fixed {
		word-wrap: break-word !important;
		table-layout: fixed;
	}
	
	table.tbl-domCentroTrabajo input {
		width: 100%;
	}
	
	.form-horizontal .form-group-sm {
		font-size: 14px;
		margin-bottom: 0px;
	}
	
	.form-group-sm .form-control {
	    line-height: 1.5;
	    padding: 5px 10px;
	}
	
	.form-group-sm input.form-control {
	    height: 30px;
	}
	
	.icono-help {
	    color: black;
	    font-family: FontAwesome;
	    font-size: 20px;
	    padding: 0 10px;
	    text-decoration: none;
	}
	
	.ui-datepicker-trigger {
	    width: 30px !important;
    	padding-left: 5px !important;
	}
	
</style>

<div class="contenedor col-sm-12" style="font-size: .75em">
	<input type="hidden" id="rfcPersona" value="${rfcPersona}" />

	<div class="contenido row">
		<div class="col-sm-12">
			<c:choose>
				<c:when test="${not empty mensajeError}">
					<div class="alert alert-danger">
						<button type="button" class="close" data-dismiss="alert" onclick="uid_call('imss.gestion.patronal.modificaciones.srt.btn_cerrar','clickin')">*</button>
						${mensajeError}
					</div>
				</c:when>
				<c:otherwise>
					<h3>${descripcionTipoTramite}</h3>
					<c:if test="${not empty folioSolicitud}">
						<input type="hidden" id="hdnFolioSolicitud" value="${folioSolicitud}" />
						<input type="hidden" id="contenidoFirmar" value="${contenidoFirmar}" />
						<div class="alert alert-info">
							<c:choose>
								<c:when test="${!isRetomar}">
								<spring:message code="label.solicitud.iniciada" arguments="${folioSolicitud}"/>
								</c:when>
								<c:otherwise>
								<spring:message code="label.solicitud.retomando" arguments="${folioSolicitud}"/>
								</c:otherwise>
							</c:choose>
						</div>
					</c:if>

					<div class="row" id="divClasificacion">
						
<!-- SECCION DATOS GENERALES DEL PATRON -->						
						<div class="alert alert-danger" style="display: none" id="divErrorCampos"></div>
						
						<c:choose>
									<c:when test="${origenInternet}">
									<!-- SECION FECHA PARA INTENET -->
										<div id="seccionDatosGenerales" class="col-sm-12">											
											<div class="row">
												<div class="col-sm-12">
													<form class="form-horizontal">
														<div class="form-group form-group-sm">
															<label class="col-sm-3 control-label">
																<c:if test="${esOperador == true}">
																	<spring:message code="label.fecha.presentacion" />:
																</c:if>
																<c:if test="${esOperador == false}">
																	<spring:message code="label.fecha.captura" />:
																</c:if>
															</label>
															<div class="col-sm-2">
																<c:if test="${sujetoTramite.clasificacion.fecPresentacion != null}">
																	<input type="text" id="fechaPresentacion"
																		value="<fmt:formatDate pattern="dd/MM/yyyy" value="${sujetoTramite.clasificacion.fecPresentacion}"/>"
																		disabled="disabled" class="form-control ns_">
																</c:if>
																<c:if test="${sujetoTramite.clasificacion.fecPresentacion == null}">
																	<input type="text" id="fechaPresentacion" value="" disabled="disabled" class="form-control ns_">
																</c:if>
															</div>
															<input type="hidden" id="numeroRegistroPatronal" value="${sujetoObligado.numeroRegistroPatronal}" />
															<input type="hidden" id="idModalidad" value="${sujetoObligado.modalidad.idModalidad}" />
															<input type="hidden" id="numModalidad" value="${sujetoObligado.modalidad.numModalidad}" />
															<input type="hidden" id="descripcionModalidad" value="${sujetoObligado.modalidad.descripcion}" />
															<input type="hidden" id="digVerificador" value="${sujetoObligado.digVerificador}" />
															<input type="hidden" id="idSubdelegacionOrigen" value="${sujetoObligado.subdelegacion.id}" />
															<input type="hidden" id="claveSubdelegacionOrigen" value="${sujetoObligado.subdelegacion.clave}" />
															<input type="hidden" id="indProductorCana" name="indProductorCana"
																value="${sujetoObligado.clasificacion.indProductorCana}" />
															<input type="hidden" id="objClasPrimaSRTActual" value="${objClasificacion.primaSRTActual}" />	
															
																	<input type="hidden" id="indRegPatClase" value="${objClasificacion.indRegPatClase}" />
																	<c:if test="${ idSolicitud!=null && idSolicitud > 0 }">
																		<input type="hidden" id="indPrestaServicioPersonal"
																			value="${sujetoTramite.clasificacion.indPrestaServicioPersonal}" />
																	</c:if>
																	<c:if test="${ idSolicitud==null || (idSolicitud!=null && idSolicitud <= 0) }">
																		<input type="hidden" id="indPrestaServicioPersonal" value="${objClasificacion.indPrestaServicioPersonal}" />
																	</c:if>
																	<!-- <span class="dato" id="idFraccionAct">${fraccion.grupo.division.numDivision}${fraccion.grupo.numGrupo}${fraccion.numFraccion}</span>  -->
																	<input type="hidden" id="fraccionActual" value="${fraccion.grupo.division.numDivision}${fraccion.grupo.numGrupo}${fraccion.numFraccion}" />
																	<input type="hidden" id="cveClaseActual" value="${fraccion.clase.descripcion}" />
															<label class="col-sm-5 control-label">
																<spring:message code="label.fecha.surte.efecto" /><span class="required" id="fechaEfectoReq">*</span>:
															</label>
															<div class="col-sm-2">
																<input type="text" id="fechaEfecto" class="form-control input-sm ns_" readonly
																	value="<fmt:formatDate pattern="dd/MM/yyyy" value="${sujetoTramite.clasificacion.fecEfecto}"/>"
																	onkeypress="evaluar(this, event)" maxlength="10" onblur="evaluarOnblur(this, event)"
																	onclick="showCalendar()" />
																<label id="fechaEfectoInvalidaMsg" style="color: red; display: none;">fecha inv&aacute;lida </label>
																<span style="display:none;" class="error" id="fechaEfectoError"></span>
															</div>
														</div>
													</form>
												</div>
											</div>
										</div>								
							<!-- TERMINA FECHA PARA INTENET -->
									</c:when>
									<c:otherwise>
											<div id="seccionDatosGenerales" class="col-sm-12">
												<div class="separadorseccion">
													<span>
														<spring:message code="titulo.datos.generales.patron" />
													</span>
												</div>
												<div class="row">
													<div class="col-sm-12">
														<c:if test="${codigo != codigoCentroTrabajo}">
															<form class="form-horizontal">
																<div class="form-group form-group-sm">
																	<label class="col-sm-3 control-label">
																		<c:if test="${esOperador == true}">
																			<spring:message code="label.fecha.presentacion" />:
																		</c:if>
																		<c:if test="${esOperador == false}">
																			<spring:message code="label.fecha.captura" />:
																		</c:if>
																	</label>
																	<div class="col-sm-2">
																		<c:if test="${sujetoTramite.clasificacion.fecPresentacion != null}">
																			<input type="text" id="fechaPresentacion"
																				value="<fmt:formatDate pattern="dd/MM/yyyy" value="${sujetoTramite.clasificacion.fecPresentacion}"/>"
																				disabled="disabled" class="form-control ns_">
																		</c:if>
																		<c:if test="${sujetoTramite.clasificacion.fecPresentacion == null}">
																			<input type="text" id="fechaPresentacion" value="" disabled="disabled" class="form-control ns_">
																		</c:if>
																	</div>
					
																	<label class="col-sm-5 control-label">
																		<spring:message code="label.fecha.surte.efecto" /><span class="required" id="fechaEfectoReq">*</span>:
																	</label>
																	<div class="col-sm-2">
																		<input type="text" id="fechaEfecto" class="form-control input-sm ns_" readonly
																			value="<fmt:formatDate pattern="dd/MM/yyyy" value="${sujetoTramite.clasificacion.fecEfecto}"/>"
																			onkeypress="evaluar(this, event)" maxlength="10" onblur="evaluarOnblur(this, event)"
																			onclick="showCalendar()" />
																		<label id="fechaEfectoInvalidaMsg" style="color: red; display: none;">fecha inv&aacute;lida </label>
																		<span style="display:none;" class="error" id="fechaEfectoError"></span>
																	</div>
																</div>
																<div class="form-group form-group-sm">
																	<label class="col-sm-3 control-label">
																		<spring:message code="label.nrp" />:
																	</label>
																	<div class="col-sm-9">
																		<p class="form-control-static">
																			${sujetoObligado.numeroRegistroPatronal}${sujetoObligado.modalidad.numModalidad}${sujetoObligado.digVerificador}
																		</p>
																		<input type="hidden" id="numeroRegistroPatronal" value="${sujetoObligado.numeroRegistroPatronal}" />
																		<input type="hidden" id="idModalidad" value="${sujetoObligado.modalidad.idModalidad}" />
																		<input type="hidden" id="numModalidad" value="${sujetoObligado.modalidad.numModalidad}" />
																		<input type="hidden" id="descripcionModalidad" value="${sujetoObligado.modalidad.descripcion}" />
																		<input type="hidden" id="digVerificador" value="${sujetoObligado.digVerificador}" />
																		<input type="hidden" id="idSubdelegacionOrigen" value="${sujetoObligado.subdelegacion.id}" />
																		<input type="hidden" id="claveSubdelegacionOrigen" value="${sujetoObligado.subdelegacion.clave}" />
																		<input type="hidden" id="indProductorCana" name="indProductorCana"
																			value="${sujetoObligado.clasificacion.indProductorCana}" />
																	</div>
																</div>
																<div class="form-group form-group-sm">
																	<c:if test="${fisica != null}">
																		<label class="col-sm-3 control-label">
																			<spring:message code="label.rfc" />:
																		</label>
																		<div class="col-sm-3">
																			<p class="form-control-static">${fisica.rfc}</p>
																		</div>
					
																		<label class="col-sm-2 control-label">
																			<spring:message code="rep.legal.curp" />:
																		</label>
																		<div class="col-sm-4">
																			<p class="form-control-static">${fisica.curp}</p>
																		</div>
																	</c:if>
																	<c:if test="${moral != null}">
																		<label class="col-sm-3 control-label">
																			<spring:message code="label.rfc" />:
																		</label>
																		<div class="col-sm-9">
																			<p class="form-control-static">${moral.rfc}</p>
																		</div>
																	</c:if>
																</div>
																<div class="form-group form-group-sm">
																	<label class="col-sm-3 control-label">
																		<spring:message code="label.tipo.persona" />:
																	</label>
																	<div class="col-sm-9">
																		<p class="form-control-static">${sujetoObligado.tipoPersonaFiscal}</p>
																	</div>
																</div>
																<div class="form-group form-group-sm">
																	<c:if test="${fisica != null}">
																		<label class="col-sm-3 control-label">
																			<spring:message code="label.nombre" />:
																		</label>
																		<div class="col-sm-9">
																			<p class="form-control-static">${fisica.nombre} ${fisica.primerApellido} ${fisica.segundoApellido}</p>
																		</div>
																	</c:if>
																	<c:if test="${moral != null}">
																		<label class="col-sm-3 control-label">
																			<spring:message code="label.razon.social" />:
																		</label>
																		<div class="col-sm-9">
																			<p class="form-control-static">${moral.razonSocial}</p>
																		</div>
																	</c:if>
																</div>
															</form>
														</c:if>
					
														<c:if test="${codigo == codigoCentroTrabajo}">
															<form class="form-horizontal">
																<div class="form-group form-group-sm">
																	<label class="col-sm-3 control-label">
																		<c:if test="${esOperador == true}">
																			<spring:message code="label.fecha.presentacion" />:
																		</c:if>
																		<c:if test="${esOperador == false}">
																			<spring:message code="label.fecha.captura" />:
																		</c:if>
																	</label>
																	<div class="col-sm-2">
																		<c:if test="${sujetoTramite.clasificacion.fecPresentacion != null}">
																			<input type="text" id="fechaPresentacion"
																				value="<fmt:formatDate pattern="dd/MM/yyyy" value="${sujetoTramite.clasificacion.fecPresentacion}"/>"
																				class="form-control ns_" disabled="disabled">
																		</c:if>
																		<c:if test="${sujetoTramite.clasificacion.fecPresentacion == null}">
																			<input type="text" id="fechaPresentacion" value="" class="form-control ns_" disabled="disabled">
																		</c:if>
																	</div>
					
																	<label class="col-sm-5 control-label">
																		
																		<spring:message code="label.fecha.surte.efecto" /><span class="required" id="fechaEfectoReq">*</span>:
																	</label>
																	<div class="col-sm-2">
																		<input type="text" id="fechaEfecto" class="form-control ns_"
																			value="<fmt:formatDate pattern="dd/MM/yyyy" value="${sujetoTramite.clasificacion.fecEfecto}"/>"
																			onkeypress="evaluar(this, event)" maxlength="10" onblur="evaluarOnblur(this, event)"
																			onclick="showCalendar()" />
																		<label id="fechaEfectoInvalidaMsg" style="color: red; display: none;">fecha inv&aacute;lida </label>
																		<span style="display:none;" class="error" id="fechaEfectoError"></span>
																	</div>
																</div>
																<div class="form-group form-group-sm">
																	<label class="col-sm-3 control-label">
																		<spring:message code="label.nrp" />:
																	</label>
																	<div class="col-sm-9">
																		<p class="form-control-static">
																			${sujetoObligado.numeroRegistroPatronal}${sujetoObligado.modalidad.numModalidad}${sujetoObligado.digVerificador}
																		</p>
																		<input type="hidden" id="numeroRegistroPatronal" value="${sujetoObligado.numeroRegistroPatronal}" />
																		<input type="hidden" id="idModalidad" value="${sujetoObligado.modalidad.idModalidad}" />
																		<input type="hidden" id="numModalidad" value="${sujetoObligado.modalidad.numModalidad}" />
																		<input type="hidden" id="descripcionModalidad" value="${sujetoObligado.modalidad.descripcion}" />
																		<input type="hidden" id="digVerificador" value="${sujetoObligado.digVerificador}" />
																		<input type="hidden" id="idSubdelegacionOrigen" value="${sujetoObligado.subdelegacion.id}" />
																		<input type="hidden" id="claveSubdelegacionOrigen" value="${sujetoObligado.subdelegacion.clave}" />
																	</div>
																</div>
																<div class="form-group form-group-sm">
																	<c:if test="${fisica != null}">
																		<label class="col-sm-3 control-label">
																			<spring:message code="label.rfc" />:
																		</label>
																		<div class="col-sm-3">
																			<p class="form-control-static">${fisica.rfc}</p>
																		</div>
					
																		<label class="col-sm-2 control-label">
																			<spring:message code="rep.legal.curp" />:
																		</label>
																		<div class="col-sm-4">
																			<p class="form-control-static">${fisica.curp}</p>
																		</div>
																	</c:if>
																	<c:if test="${moral != null}">
																		<label class="col-sm-3 control-label">
																			<spring:message code="label.rfc" />:
																		</label>
																		<div class="col-sm-9">
																			<p class="form-control-static">${moral.rfc}</p>
																		</div>
																	</c:if>
																</div>
																<div class="form-group form-group-sm">
																	<label class="col-sm-3 control-label">
																		<spring:message code="label.tipo.persona" />:
																	</label>
																	<div class="col-sm-9">
																		<p class="form-control-static">${sujetoObligado.tipoPersonaFiscal}</p>
																	</div>
																</div>
																<div class="form-group form-group-sm">
																	<c:if test="${fisica != null}">
																		<label class="col-sm-3 control-label">
																			<spring:message code="label.nombre" />:
																		</label>
																		<div class="col-sm-9">
																			<p class="form-control-static">${fisica.nombre} ${fisica.primerApellido} ${fisica.segundoApellido}</p>
																		</div>
																	</c:if>
																	<c:if test="${moral != null}">
																		<label class="col-sm-3 control-label">
																			<spring:message code="label.razon.social" />:
																		</label>
																		<div class="col-sm-9">
																			<p class="form-control-static">${moral.razonSocial}</p>
																		</div>
																	</c:if>
																</div>
															</form>
														</c:if>
													</div>
												</div>
					
												<div class="row" style="margin-top: 15px;">
													<div class="col-sm-12">
														<table id="gridClasificacionActual" class="table table-striped table-bordered">
															<thead>
																<tr>
																	<td align="center">
																		<span class="etiqueta">
																			<spring:message code="label.rp.clave.fraccion" />
																		</span>
																	</td>
																	<td align="center">
																		<span class="etiqueta">
																			<spring:message code="label.rp.division" />
																		</span>
																	</td>
																	<td align="center">
																		<span class="etiqueta">
																			<spring:message code="label.rp.grupo" />
																		</span>
																	</td>
																	<td align="center">
																		<span class="etiqueta">
																			<spring:message code="label.rp.descripcion.fraccion" />
																		</span>
																	</td>
																	<td align="center">
																		<span class="etiqueta">
																			<spring:message code="label.rp.clase" />
																		</span>
																	</td>
																	<td align="center">
																		<span class="etiqueta">
																			<spring:message code="label.rp.prima.srt" />
																		</span>
																	</td>
																</tr>
															</thead>
															<tr>
																<!-- Cambio -->
																<td align="center">
																	<input type="hidden" id="indRegPatClase" value="${objClasificacion.indRegPatClase}" />
																	<c:if test="${ idSolicitud!=null && idSolicitud > 0 }">
																		<input type="hidden" id="indPrestaServicioPersonal"
																			value="${sujetoTramite.clasificacion.indPrestaServicioPersonal}" />
																	</c:if>
																	<c:if test="${ idSolicitud==null || (idSolicitud!=null && idSolicitud <= 0) }">
																		<input type="hidden" id="indPrestaServicioPersonal" value="${objClasificacion.indPrestaServicioPersonal}" />
																	</c:if>
																	<span class="dato" id="idFraccionAct">${fraccion.grupo.division.numDivision}${fraccion.grupo.numGrupo}${fraccion.numFraccion}</span>
																	<input type="hidden" id="fraccionActual" value="${fraccion.grupo.division.numDivision}${fraccion.grupo.numGrupo}${fraccion.numFraccion}" />
																	<input type="hidden" id="cveClaseActual" value="${fraccion.clase.descripcion}" />
																</td>
																<td align="center">
																	<span class="dato" id="cvedivisionAct">${fraccion.grupo.division.descripcion}</span>
																</td>
																<td align="center">
																	<span class="dato" id="cvegrupoAct">${fraccion.grupo.descripcion}</span>
																</td>
																<td align="center">
																	<span class="dato" id="cvefraccionAct">${fraccion.descripcionDetallada}</span>
																</td>
																<td align="center">
																	<span class="dato" id="cveclaseAct">${fraccion.clase.descripcion}</span>
																</td>
																<td align="center">
																	<span class="dato" id="cveprimaAct">${objClasificacion.primaSRTActual}</span>
																	<input type="hidden" id="objClasPrimaSRTActual" value="${objClasificacion.primaSRTActual}" />												
																</td>
															</tr>
														</table>
													</div>
												</div>
											</div>
									</c:otherwise>
						</c:choose>																				

<!-- TERMINA SECCION DATOS GENERALES DEL PATRON -->

<!-- SECCION CENTRO DE TRABAJO -->
					<c:if test="${origenInternet == false}">
						<div id="seccionCentroTrabajoCambioDomicilio" class="col-sm-12">
							<div class="separadorseccion">
								<span>Domicilio</span>
							</div>
							
							<div class="row">
								<div class="col-sm-12">
									<table width="100%" id="domicilioCentroTrabajoForm"
										class="table table-striped table-bordered tbl-domCentroTrabajo">
										<tr>
											<td class="label_patrones" style="width: 230px !important;"><spring:message
													code="label.codigo.postal" /><span class="required">*</span>:</td>
											<td class="label_patrones" colspan="2"><spring:message
													code="label.entidad.federativa" /><span class="required">*</span>:</td>
											<td class="label_patrones" colspan="2"><spring:message
													code="label.municipio" /><span class="required">*</span>:</td>
										</tr>
										<tr>
											<td class="label_patrones_data"><input type="text"
												readonly="readonly"
												id="cntroTrabajo.codigoPostal.codigoPostal"
												class="campoObligatorio ns_"
												value="${ sujetoTramite.cntroTrabajo.codigoPostal.codigoPostal }" />
												<input type="hidden"
												id="cntroTrabajo.vialidadPrimaria.tipoVialidad.descripcion"
												value="${ sujetoTramite.cntroTrabajo.vialidadPrimaria.tipoVialidad.descripcion }" />
												<span style="display: none" class="error"></span></td>
											<td class="label_patrones_data" colspan="2"><input
												type="text" readonly="readonly" class="campoObligatorio ns_"
												id="cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa.nombre"
												value="${ sujetoTramite.cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa.nombre }" />
												<input type="hidden"
												id="cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa.clave"
												value="${ sujetoTramite.cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa.clave }" />
												<span style="display: none" class="error"></span></td>
											<td class="label_patrones_data" colspan="2"><input
												type="text" class="campoObligatorio ns_" readonly="readonly"
												id="cntroTrabajo.asentamiento.localidad.municipio.nombre"
												value="${ sujetoTramite.cntroTrabajo.asentamiento.localidad.municipio.nombre }" />
												<input type="hidden"
												id="cntroTrabajo.asentamiento.localidad.municipio.clave"
												value="${ sujetoTramite.cntroTrabajo.asentamiento.localidad.municipio.clave }" />
												<span style="display: none" class="error"></span></td>
										</tr>
										<tr>
											<td class="label_patrones" colspan="2"><spring:message
													code="label.localidad" /><span class="required">*</span>:</td>
											<td class="label_patrones" colspan="3"><spring:message
													code="label.colonia" /><span class="required">*</span>:</td>
										</tr>
										<tr>
											<td class="label_patrones_data" colspan="2"><input
												type="text" class="campoObligatorio ns_" readonly="readonly"
												id="cntroTrabajo.asentamiento.localidad.nombre"
												value="${ sujetoTramite.cntroTrabajo.asentamiento.localidad.nombre }" />
												<input type="hidden"
												id="cntroTrabajo.asentamiento.localidad.clave"
												value="${ sujetoTramite.cntroTrabajo.asentamiento.localidad.clave }" />
												<span style="display: none" class="error"></span></td>
											<td class="label_patrones_data" colspan="3"><input
												type="text" class="campoObligatorio ns_" readonly="readonly"
												id="cntroTrabajo.asentamiento.nombre"
												value="${ sujetoTramite.cntroTrabajo.asentamiento.nombre }" />
												<input type="hidden" id="cntroTrabajo.asentamiento.clave"
												value="${ sujetoTramite.cntroTrabajo.asentamiento.clave }" />
												<span style="display: none" class="error"></span></td>
										</tr>
										<tr>
											<td class="label_patrones"><spring:message
													code="label.calle" /><span class="required">*</span>:</td>
											<td class="label_patrones"><spring:message
													code="label.domicilio.numero" /><span class="required">*</span>:</td>
											<td class="label_patrones"><spring:message
													code="label.domicilio.letra" />:</td>
											<td class="label_patrones"><spring:message
													code="label.domicilio.numeroInt" />:</td>
											<td class="label_patrones"><spring:message
													code="label.domicilio.letraInt" />:</td>
										</tr>
										<tr>
											<td class="label_patrones_data"><input type="text"
												class="campoObligatorio ns_" readonly="readonly"
												id="cntroTrabajo.vialidadPrimaria.nombre" maxlength="100"
												size="50"
												value="${ sujetoTramite.cntroTrabajo.vialidadPrimaria.nombre != null? sujetoTramite.cntroTrabajo.vialidadPrimaria.nombre : sujetoTramite.cntroTrabajo.descripcion}" />
												<span style="display: none" class="error"></span> <input
												type="hidden" id="cntroTrabajo.vialidadPrimaria.clave"
												value="${ sujetoTramite.cntroTrabajo.vialidadPrimaria.clave }" />
												<input type="hidden"
												id="cntroTrabajo.vialidadPrimaria.tipoVialidad.clave"
												value="${ sujetoTramite.cntroTrabajo.vialidadPrimaria.tipoVialidad.clave }" />

												<input type="hidden" id="cntroTrabajo.tipoBusquedaVialidad"
												value="${sujetoTramite.cntroTrabajo.tipoBusquedaVialidad}" />
												<input type="hidden" id="cntroTrabajo.calle"
												value="${sujetoTramite.cntroTrabajo.calle}" /> <!-- Atributos de domicilio carretera -->
												<input type="hidden"
												id="cntroTrabajo.domicilioCarretera.terminoGeneral.descripcion"
												value="${ sujetoTramite.cntroTrabajo.domicilioCarretera.terminoGeneral.descripcion }" />
												<input type="hidden"
												id="cntroTrabajo.domicilioCarretera.terminoGeneral.clave"
												value="${ sujetoTramite.cntroTrabajo.domicilioCarretera.terminoGeneral.clave }" />
												<input type="hidden"
												id="cntroTrabajo.domicilioCarretera.derechoTransito.descripcion"
												value="${ sujetoTramite.cntroTrabajo.domicilioCarretera.derechoTransito.descripcion }" />
												<input type="hidden"
												id="cntroTrabajo.domicilioCarretera.derechoTransito.clave"
												value="${ sujetoTramite.cntroTrabajo.domicilioCarretera.derechoTransito.clave }" />
												<input type="hidden"
												id="cntroTrabajo.domicilioCarretera.origen"
												value="${ sujetoTramite.cntroTrabajo.domicilioCarretera.origen }" />
												<input type="hidden"
												id="cntroTrabajo.domicilioCarretera.destino"
												value="${ sujetoTramite.cntroTrabajo.domicilioCarretera.destino }" />
												<input type="hidden"
												id="cntroTrabajo.domicilioCarretera.administracion.descripcion"
												value="${ sujetoTramite.cntroTrabajo.domicilioCarretera.administracion.descripcion }" />
												<input type="hidden"
												id="cntroTrabajo.domicilioCarretera.administracion.clave"
												value="${ sujetoTramite.cntroTrabajo.domicilioCarretera.administracion.clave }" />
												<input type="hidden"
												id="cntroTrabajo.domicilioCarretera.cadenamiento"
												value="${ sujetoTramite.cntroTrabajo.domicilioCarretera.cadenamiento }" />
												<input type="hidden"
												id="cntroTrabajo.domicilioCarretera.codigoCarretera"
												value="${ sujetoTramite.cntroTrabajo.domicilioCarretera.codigoCarretera }" />

												<!-- Atrbutos de domicilio camino --> <input type="hidden"
												id="cntroTrabajo.domicilioCamino.terminoGeneral.descripcion"
												value="${ sujetoTramite.cntroTrabajo.domicilioCamino.terminoGeneral.descripcion }" />
												<input type="hidden"
												id="cntroTrabajo.domicilioCamino.terminoGeneral.clave"
												value="${ sujetoTramite.cntroTrabajo.domicilioCamino.terminoGeneral.clave}" />
												<input type="hidden"
												id="cntroTrabajo.domicilioCamino.margen.descripcion"
												value="${ sujetoTramite.cntroTrabajo.domicilioCamino.margen.descripcion }" />
												<input type="hidden"
												id="cntroTrabajo.domicilioCamino.margen.clave"
												value="${ sujetoTramite.cntroTrabajo.domicilioCamino.margen.clave }" />
												<input type="hidden"
												id="cntroTrabajo.domicilioCamino.origen"
												value="${ sujetoTramite.cntroTrabajo.domicilioCamino.origen }" />
												<input type="hidden"
												id="cntroTrabajo.domicilioCamino.destino"
												value="${ sujetoTramite.cntroTrabajo.domicilioCamino.destino }" />
												<input type="hidden"
												id="cntroTrabajo.domicilioCamino.cadenamiento"
												value="${ sujetoTramite.cntroTrabajo.domicilioCamino.cadenamiento }" />
											</td>
											<td colspan="1"><input type="text"
												class="campoObligatorio ns_" readonly="readonly"
												id="cntroTrabajo.numExterior1"
												value="${ sujetoTramite.cntroTrabajo.numExterior1 }" /> <span
												style="display: none" class="error"></span></td>
											<td colspan="1"><input type="text" class="ns_"
												readonly="readonly" id="cntroTrabajo.numExteriorAlf"
												value="${ sujetoTramite.cntroTrabajo.numExteriorAlf }" /></td>
											<td class="label_patrones" colspan="1"><input
												type="text" class="ns_" readonly="readonly"
												id="cntroTrabajo.numInterior"
												value="${ sujetoTramite.cntroTrabajo.numInterior }" /></td>
											<td class="label_patrones" colspan="1"><input
												type="text" class="ns_" readonly="readonly"
												id="cntroTrabajo.numInteriorAlf"
												value="${ sujetoTramite.cntroTrabajo.numInteriorAlf }" /></td>
										</tr>
										<tr>
											<td class="label_patrones" colspan="2"><spring:message
													code="label.entre.calle" />:</td>
											<td class="label_patrones" colspan="3"><spring:message
													code="label.entre.calle2" />:</td>
										</tr>
										<tr>
											<td class="label_patrones_data" colspan="2"><input
												type="text" class="ns_" readonly="readonly"
												id="cntroTrabajo.vialidadReferenciaPrimaria.nombre"
												value="${ sujetoTramite.cntroTrabajo.vialidadReferenciaPrimaria.nombre }" />
												<input type="hidden"
												id="cntroTrabajo.vialidadReferenciaPrimaria.clave"
												value="${ sujetoTramite.cntroTrabajo.vialidadReferenciaPrimaria.clave }" />
												<input type="hidden"
												id="cntroTrabajo.vialidadReferenciaPrimaria.tipoVialidad.clave"
												value="${ sujetoTramite.cntroTrabajo.vialidadReferenciaPrimaria.tipoVialidad.clave}" />
											</td>
											<td class="label_patrones_data" colspan="3"><input
												type="text" class="ns_" readonly="readonly"
												id="cntroTrabajo.vialidadReferenciaSecundaria.nombre"
												value="${ sujetoTramite.cntroTrabajo.vialidadReferenciaSecundaria.nombre }" />
												<input type="hidden"
												id="cntroTrabajo.vialidadReferenciaSecundaria.clave"
												value="${ sujetoTramite.cntroTrabajo.vialidadReferenciaSecundaria.clave }" />
												<input type="hidden"
												id="cntroTrabajo.vialidadReferenciaSecundaria.tipoVialidad.clave" />
												<input type="hidden"
												id="cntroTrabajo.vialidadReferenciaPosterior.nombre"
												maxlength="14"
												value="${ sujetoTramite.cntroTrabajo.vialidadReferenciaPosterior.nombre }" />
												<input type="hidden"
												id="cntroTrabajo.vialidadReferenciaPosterior.clave"
												value="${ sujetoTramite.cntroTrabajo.vialidadReferenciaPosterior.clave }" />
												<input type="hidden"
												id="cntroTrabajo.vialidadReferenciaPosterior.tipoVialidad.clave"
												value="${ sujetoTramite.cntroTrabajo.vialidadReferenciaPosterior.tipoVialidad.clave }" />

											</td>
										</tr>
									</table>

								</div>
							</div>


						</div>
					</c:if>	
						<!-- TERMINA SECCION DATOS GENERALES DEL PATRON -->

<!-- SECCION CENTRO DE TRABAJO -->

					<div id="seccionCentroTrabajo"  class="col-sm-12">
							<div class="separadorseccion">
								<span>Cambio de domicilio</span>
							</div>
							<div class="row">
								<div id="wrapperDomicilio" class="col-sm-12">
									<div class="alert alert-info">
										Selecciona tu nuevo domicilio
										<a href="javascript:fnOpenBuscarDomicilio();" class="alert-link" onclick="uid_call('imss.gestion.patronal.modificaciones.srt.domicilio','clickin');"> aqu&iacute;</a><span class="required">*</span>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12">
									<table width="100%" id="domicilioCentroTrabajoForm" class="table table-striped table-bordered tbl-domCentroTrabajo" >	
					<tr>
						<td class="label_patrones" style="width: 230px !important;"><spring:message code="label.codigo.postal"/><span class="required">*</span>:</td>
						<td class="label_patrones" colspan="2"><spring:message code="label.entidad.federativa"/><span class="required">*</span>:</td>
						<td class="label_patrones" colspan="2"><spring:message code="label.municipio"/><span class="required">*</span>:</td>
					</tr>
					<tr>
						<td class="label_patrones_data">
							<input type="text" readonly="readonly" id="cntroTrabajo.codigoPostal.codigoPostal" class="campoObligatorio ns_" value="${ sujetoTramite.cntroTrabajo.codigoPostal.codigoPostal }" />
							<input type="hidden" id="cntroTrabajo.vialidadPrimaria.tipoVialidad.descripcion" value="${ sujetoTramite.cntroTrabajo.vialidadPrimaria.tipoVialidad.descripcion }" />	
							<span style="display: none" class="error"></span>
						</td>
						<td class="label_patrones_data" colspan="2">
							<input type="text" readonly="readonly" class="campoObligatorio ns_" id="cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa.nombre" value="${ sujetoTramite.cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa.nombre }" />
							<input type="hidden" id="cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa.clave" value="${ sujetoTramite.cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa.clave }" />
							<span style="display: none" class="error"></span>
						</td>
						<td class="label_patrones_data" colspan="2">
							<input type="text" class="campoObligatorio ns_" readonly="readonly" id="cntroTrabajo.asentamiento.localidad.municipio.nombre" value="${ sujetoTramite.cntroTrabajo.asentamiento.localidad.municipio.nombre }" />
							<input type="hidden" id="cntroTrabajo.asentamiento.localidad.municipio.clave" value="${ sujetoTramite.cntroTrabajo.asentamiento.localidad.municipio.clave }" />
							<span style="display: none" class="error"></span>
						</td>
					</tr>
					<tr>
						<td class="label_patrones" colspan="2"><spring:message code="label.localidad"/><span class="required">*</span>:</td>
						<td class="label_patrones" colspan="3"><spring:message code="label.colonia"/><span class="required">*</span>:</td>
					</tr>
					<tr>
						<td class="label_patrones_data" colspan="2">
							<input type="text" class="campoObligatorio ns_" readonly="readonly" id="cntroTrabajo.asentamiento.localidad.nombre" value="${ sujetoTramite.cntroTrabajo.asentamiento.localidad.nombre }" />
							<input type="hidden" id="cntroTrabajo.asentamiento.localidad.clave" value="${ sujetoTramite.cntroTrabajo.asentamiento.localidad.clave }" />
							<span style="display: none" class="error"></span>
						</td>
						<td class="label_patrones_data" colspan="3">
							<input type="text"  class="campoObligatorio ns_" readonly="readonly" id="cntroTrabajo.asentamiento.nombre" value="${ sujetoTramite.cntroTrabajo.asentamiento.nombre }" />
							<input type="hidden" id="cntroTrabajo.asentamiento.clave" value="${ sujetoTramite.cntroTrabajo.asentamiento.clave }" />
							<span style="display: none" class="error"></span>
						</td>			
					</tr>
					<tr>
						<td class="label_patrones"><spring:message code="label.calle"/><span class="required">*</span>:</td>
						<td class="label_patrones"><spring:message code="label.domicilio.numero"/><span class="required">*</span>:</td>
						<td class="label_patrones"><spring:message code="label.domicilio.letra"/>:</td>
						<td class="label_patrones"><spring:message code="label.domicilio.numeroInt"/>:</td>
						<td class="label_patrones"><spring:message code="label.domicilio.letraInt"/>:</td>				
					</tr>
					<tr>
						<td class="label_patrones_data">
							
							
							<input type="text" class="campoObligatorio ns_"  readonly="readonly" id="cntroTrabajo.vialidadPrimaria.nombre" maxlength="100" size="50"
													value="${ sujetoTramite.cntroTrabajo.vialidadPrimaria.nombre }" />
							<span style="display: none" class="error"></span>
							<input type="hidden" id="cntroTrabajo.vialidadPrimaria.clave"
													value="${ sujetoTramite.cntroTrabajo.vialidadPrimaria.clave }" />
												<input type="hidden" id="cntroTrabajo.vialidadPrimaria.tipoVialidad.clave"
													value="${ sujetoTramite.cntroTrabajo.vialidadPrimaria.tipoVialidad.clave }" />
		
												<input type="hidden" id="cntroTrabajo.tipoBusquedaVialidad"
													value="${sujetoTramite.cntroTrabajo.tipoBusquedaVialidad}" />
												<input type="hidden" id="cntroTrabajo.calle" value="${sujetoTramite.cntroTrabajo.calle}" />
		
												<!-- Atributos de domicilio carretera -->
												<input type="hidden" id="cntroTrabajo.domicilioCarretera.terminoGeneral.descripcion"
													value="${ sujetoTramite.cntroTrabajo.domicilioCarretera.terminoGeneral.descripcion }" />
												<input type="hidden" id="cntroTrabajo.domicilioCarretera.terminoGeneral.clave"
													value="${ sujetoTramite.cntroTrabajo.domicilioCarretera.terminoGeneral.clave }" />
												<input type="hidden" id="cntroTrabajo.domicilioCarretera.derechoTransito.descripcion"
													value="${ sujetoTramite.cntroTrabajo.domicilioCarretera.derechoTransito.descripcion }" />
												<input type="hidden" id="cntroTrabajo.domicilioCarretera.derechoTransito.clave"
													value="${ sujetoTramite.cntroTrabajo.domicilioCarretera.derechoTransito.clave }" />
												<input type="hidden" id="cntroTrabajo.domicilioCarretera.origen"
													value="${ sujetoTramite.cntroTrabajo.domicilioCarretera.origen }" />
												<input type="hidden" id="cntroTrabajo.domicilioCarretera.destino"
													value="${ sujetoTramite.cntroTrabajo.domicilioCarretera.destino }" />
												<input type="hidden" id="cntroTrabajo.domicilioCarretera.administracion.descripcion"
													value="${ sujetoTramite.cntroTrabajo.domicilioCarretera.administracion.descripcion }" />
												<input type="hidden" id="cntroTrabajo.domicilioCarretera.administracion.clave"
													value="${ sujetoTramite.cntroTrabajo.domicilioCarretera.administracion.clave }" />
												<input type="hidden" id="cntroTrabajo.domicilioCarretera.cadenamiento"
													value="${ sujetoTramite.cntroTrabajo.domicilioCarretera.cadenamiento }" />
												<input type="hidden" id="cntroTrabajo.domicilioCarretera.codigoCarretera"
													value="${ sujetoTramite.cntroTrabajo.domicilioCarretera.codigoCarretera }" />
		
												<!-- Atrbutos de domicilio camino -->
												<input type="hidden" id="cntroTrabajo.domicilioCamino.terminoGeneral.descripcion"
													value="${ sujetoTramite.cntroTrabajo.domicilioCamino.terminoGeneral.descripcion }" />
												<input type="hidden" id="cntroTrabajo.domicilioCamino.terminoGeneral.clave"
													value="${ sujetoTramite.cntroTrabajo.domicilioCamino.terminoGeneral.clave}" />
												<input type="hidden" id="cntroTrabajo.domicilioCamino.margen.descripcion"
													value="${ sujetoTramite.cntroTrabajo.domicilioCamino.margen.descripcion }" />
												<input type="hidden" id="cntroTrabajo.domicilioCamino.margen.clave"
													value="${ sujetoTramite.cntroTrabajo.domicilioCamino.margen.clave }" />
												<input type="hidden" id="cntroTrabajo.domicilioCamino.origen"
													value="${ sujetoTramite.cntroTrabajo.domicilioCamino.origen }" />
												<input type="hidden" id="cntroTrabajo.domicilioCamino.destino"
													value="${ sujetoTramite.cntroTrabajo.domicilioCamino.destino }" />
												<input type="hidden" id="cntroTrabajo.domicilioCamino.cadenamiento"
													value="${ sujetoTramite.cntroTrabajo.domicilioCamino.cadenamiento }" />
						</td>
						<td colspan="1">
							<input type="text" class="campoObligatorio ns_" readonly="readonly" id="cntroTrabajo.numExterior1"
													value="${ sujetoTramite.cntroTrabajo.numExterior1 }" />
							<span style="display: none" class="error"></span>
						</td>
						<td colspan="1">
							<input type="text" class="ns_" readonly="readonly" id="cntroTrabajo.numExteriorAlf"
													value="${ sujetoTramite.cntroTrabajo.numExteriorAlf }" />
						</td>
						<td class="label_patrones" colspan="1" >
							<input type="text" class="ns_" readonly="readonly" id="cntroTrabajo.numInterior"
													value="${ sujetoTramite.cntroTrabajo.numInterior }" />
						</td>
						<td class="label_patrones" colspan="1" >
							<input type="text" class="ns_" readonly="readonly" id="cntroTrabajo.numInteriorAlf"
													value="${ sujetoTramite.cntroTrabajo.numInteriorAlf }" />
						</td>	
					</tr>
					<tr>
						<td class="label_patrones" colspan="2"><spring:message code="label.entre.calle"/>:</td>
						<td class="label_patrones" colspan="3"><spring:message code="label.entre.calle2"/>:</td>
					</tr>
					<tr>
						<td class="label_patrones_data" colspan="2">
							<input type="text" class="ns_" readonly="readonly" id="cntroTrabajo.vialidadReferenciaPrimaria.nombre"
													value="${ sujetoTramite.cntroTrabajo.vialidadReferenciaPrimaria.nombre }" />
												<input type="hidden" id="cntroTrabajo.vialidadReferenciaPrimaria.clave"
													value="${ sujetoTramite.cntroTrabajo.vialidadReferenciaPrimaria.clave }" />
												<input type="hidden" id="cntroTrabajo.vialidadReferenciaPrimaria.tipoVialidad.clave"
													value="${ sujetoTramite.cntroTrabajo.vialidadReferenciaPrimaria.tipoVialidad.clave}" />
						</td>
						<td class="label_patrones_data" colspan="3">
							<input type="text" class="ns_" readonly="readonly" id="cntroTrabajo.vialidadReferenciaSecundaria.nombre"
													value="${ sujetoTramite.cntroTrabajo.vialidadReferenciaSecundaria.nombre }" />
												<input type="hidden" id="cntroTrabajo.vialidadReferenciaSecundaria.clave"
													value="${ sujetoTramite.cntroTrabajo.vialidadReferenciaSecundaria.clave }" />
												<input type="hidden" id="cntroTrabajo.vialidadReferenciaSecundaria.tipoVialidad.clave" />
												<input type="hidden" id="cntroTrabajo.vialidadReferenciaPosterior.nombre" maxlength="14"
													value="${ sujetoTramite.cntroTrabajo.vialidadReferenciaPosterior.nombre }" />
												<input type="hidden" id="cntroTrabajo.vialidadReferenciaPosterior.clave"
													value="${ sujetoTramite.cntroTrabajo.vialidadReferenciaPosterior.clave }" />
												<input type="hidden" id="cntroTrabajo.vialidadReferenciaPosterior.tipoVialidad.clave"
													value="${ sujetoTramite.cntroTrabajo.vialidadReferenciaPosterior.tipoVialidad.clave }" />

						</td>
					</tr>
					<tr>
											<td colspan="5">
												<input type="hidden" id="municipioIMSS.idMunicipio" value="${sujetoTramite.municipioIMSS.idMunicipio}" />
												<input type="hidden" id="municipioIMSS.cvecMunicipioSINDO"
													value="${sujetoTramite.municipioIMSS.cvecMunicipioSINDO}" />
												<input type="hidden" id="municipioIMSS.descMunicipio" value="${sujetoTramite.municipioIMSS.descMunicipio}" />
		
												<input type="hidden" id="municipioIMSS.subdelegacion.id"
													value="${sujetoTramite.municipioIMSS.subdelegacion.id}" />
												<input type="hidden" id="municipioIMSS.subdelegacion.clave"
													value="${sujetoTramite.municipioIMSS.subdelegacion.clave}" />
												<input type="hidden" id="municipioIMSS.subdelegacion.descripcion"
													value="${sujetoTramite.municipioIMSS.subdelegacion.descripcion}" />
		
												<input type="hidden" id="municipioIMSS.subdelegacion.delegacion.id"
													value="${sujetoTramite.municipioIMSS.subdelegacion.delegacion.id}" />
												<input type="hidden" id="municipioIMSS.subdelegacion.delegacion.clave"
													value="${sujetoTramite.municipioIMSS.subdelegacion.delegacion.clave}" />
												<input type="hidden" id="municipioIMSS.subdelegacion.delegacion.descripcion"
													value="${sujetoTramite.municipioIMSS.subdelegacion.delegacion.descripcion}" />
												<input type="hidden" id="municipioIMSS.subdelegacion.delegacion.ciz"
													value="${sujetoTramite.municipioIMSS.subdelegacion.delegacion.ciz}" />
												<div id="municipioImssContenedor" style="width: 100%;">Sin Subdelegaciones que mostrar</div>
												<!-- Form auxiliar ubicacion Centro Trabajo -->
												<form id="asentamientoForMunicipioForm">
													<input type="hidden" id="hdnCveEnt" name="asentamiento.localidad.municipio.entidadFederativa.clave"
														value="${sujetoTramite.cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa.clave}" />
													<input type="hidden" id="hdnCveMun" name="asentamiento.localidad.municipio.clave"
														value="${sujetoTramite.cntroTrabajo.asentamiento.localidad.municipio.clave}" />
													<input type="hidden" id="hdnCodigoPostal" name="codigoPostal.codigoPostal"
														value="${sujetoTramite.cntroTrabajo.codigoPostal.codigoPostal}" />
												</form>
											</td>
										</tr>
				</table>
							
								</div>
							</div>
							
							<div class="row">
								<div class="col-sm-12">
									<div class="separadorseccion">
										<span>Datos de contacto del centro de trabajo</span>
									</div>
									<table width="100%" class="table table-striped table-bordered">
										<tr>
											<td class="label_patrones" colspan="3" style="width: 200px" align="center">
												
												Tel&eacute;fono fijo (Principal)<span class="required" id="ctTelefonoFijoReq">*</span>:
											</td>
											<td class="label_patrones" style="width: 200px" align="center" colspan="3">Tel&eacute;fono fijo
												(Secundario):</td>
										</tr>
										<tr></tr>
										<tr>
											<td>Lada:</td>
											<td>Tel&eacute;fono:</td>
											<td>Extensi&oacute;n:</td>
											<td>Lada:</td>
											<td>Tel&eacute;fono:</td>
											<td>Extensi&oacute;n:</td>
										</tr>
										<tr>
											<td>
												<input class="ns_" type="text" id="ctLada" value="${ ctLada }" maxlength="3" size="4" onkeydown="validarNumeros(event)"
													onkeypress="validarNumeros(event)" onkeyup="validarNumeros(event)" />
											</td>
											<td>
												<input class="ns_" type="text" id="ctTelefonoFijo" value="${ ctTelefonoFijo }" maxlength="8" size="8"
													onkeydown="validarNumeros(event)" onkeypress="validarNumeros(event)" onkeyup="validarNumeros(event)" />
												<span style="display: none" class="error" id="ctTelefonoFijoError"></span>
											</td>
											<td>
												<input class="ns_" type="text" id="ctExtension" value="${ ctExtension }" maxlength="6" size="6"
													onkeydown="validarNumeros(event)" onkeypress="validarNumeros(event)" onkeyup="validarNumeros(event)" />
											</td>
											<td>
												<input class="ns_" type="text" id="ctLada2" value="${ ctLada2 }" maxlength="3" size="4" onkeydown="validarNumeros(event)"
													onkeypress="validarNumeros(event)" onkeyup="validarNumeros(event)" />
											</td>
											<td>
												<input class="ns_" type="text" id="ctTelefonoFijo2" value="${ ctTelefonoFijo2 }" maxlength="8" size="8"
													onkeydown="validarNumeros(event)" onkeypress="validarNumeros(event)" onkeyup="validarNumeros(event)" />
											</td>
											<td>
												<input class="ns_" type="text" id="ctExtension2" value="${ ctExtension2 }" maxlength="6" size="6"
													onkeydown="validarNumeros(event)" onkeypress="validarNumeros(event)" onkeyup="validarNumeros(event)" />
											</td>
										</tr>
										<tr>
											<td class="label_patrones" colspan="3" style="text-align: right !important;">
												
												Correo electr&oacute;nico<span class="required" id="ctCorreoElectronicoErrorReq">*</span>:
											</td>
											<td colspan="3">
												<input class="ns_" type="text" id="ctCorreoElectronico" maxlength="50" size="60" value="${ ctCorreoElectronico }"
													style="text-transform: none !important;" />
													<span style="display: none" class="error" id="ctCorreoElectronicoError"></span>
											</td>
										</tr>
									</table>
								</div>
							</div>
						</div>

<!-- TERMINA SECCION CENTRO DE TRABAJO -->

<!-- SECCION DE CLASIFICACION-->							

						<div id="seccionClasificacion" class="col-sm-12">
							<div class="row">
								<div class="col-sm-12">
									<div class="separadorseccion">
										<span>
											<spring:message code="label.aviso.seguro.riesgos" />
										</span>
									</div>
		
									<form class="form-horizontal">
										<div class="form-group form-group-sm">
											<label class="col-sm-3 control-label">
												
												Especifica tu giro o actividad<span class="required" id="giroClasificacionReq">*</span>: <a class="delta-tooltip icono-help btn btn-xs" data-toggle="tooltip" data-placement="right"
													title="Anotar el(los) objeto(s) principal(es) para el(los) cual(es) fue constituida la empresa o las actividades que desarrolla para llevar a cabo el prop&oacute;sito del negocio. El giro se define como el conjunto de actividades a las que se dedica una empresa o patr&oacute;n"></a>
											</label>
											<div class="col-sm-9">
												<textarea class="alfanumerico form-control" rows="7" id="giroClasificacion"
													onkeydown="validaSize(this, 300, event);" onkeyup="validaSize(this, 300, event);"
													onblur="validaSize(this, 300, event);"><c:if test="${sujetoTramite.clasificacion != null}">${sujetoTramite.clasificacion.giro}</c:if></textarea>
												<span style="display:none;" class="error" id="giroClasificacionError"></span>
											</div>
										</div>
										<div class="form-group form-group-sm" id="seccionPSP">
											<label class="col-sm-3 control-label">
												<span id="labelServicioDePersonal" class="etiqueta">Prestas servicios especializados<span class="required" id="clasificacion.indPrestaServicioPersonal1Req">*</span>:</span> 
											</label>
											<div class="col-sm-9">
												<div class="radio">
													<label class="radio-inline">
														<form:radiobutton path="sujetoTramite.clasificacion.indPrestaServicioPersonal" onclick="mostrarMensajePSP();" value="1" />S&iacute;
													</label>
													<label class="radio-inline">
														<form:radiobutton path="sujetoTramite.clasificacion.indPrestaServicioPersonal" value="0"  /> No
													</label>
												</div>
												<span class="error" id="clasificacion.indPrestaServicioPersonal1Error"></span>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
<!-- TERMINA SECCION DE CLASIFICACION-->
							
<!-- SECCION PARA CAPTURA DE PATRONES -->

						<div id="dgErrorSinSeleccion" title="Debe seleccionar un elemento">
							<p style="float: left; margin: 10 10px 10px 10;">
								<span class="ui-icon ui-icon-alert"
									style="float: left; margin: 0 7px 20px 0;"> </span> No se ha
								seleccionado ning&uacute;n registro para ejecutar esta
								acci&oacute;n.
							</p>
						</div>

						<c:if test="${codigo==21 || codigo == 20 || codigo == 175 || codigo == 176}">
						<div id="seccionCapturaPatrones" class="col-sm-12">
							
								<script type="text/javascript">
									$(function() {
										
										$("#primaSRTFusionSust").numeric({
											maxDigits: 8,
											maxDecimalPlaces: 5,
											maxPreDecimalPlaces: 3
										});
										
										$.postJSON(context_path + "/movPat/wizard/tramite/clasificacion/patronesFusionSust",null, function(data) {
											
											$("#contenedorComponenteBusquedaPatrones").busquedaRps({
												validarFusion: true,
												idSujetoObligado: idSujetoObligado,
												patronesNoElegibles: [registroPatronal],
												patronesDefault: data,
												funcionPatronEncontrado:quitarErrorCampoPatrones,
												clasePrincipal:'${fraccion.grupo.division.numDivision}${fraccion.grupo.numGrupo}${fraccion.numFraccion}',
												funcionPatronEliminado: quitarErrorCampoPatrones,
												onInit: quitarErrorCampoPatrones,
												rfcPatronTramite: rfcSujetoObligado,
												idMpioIMSSPatronTr: idMunicipioIMSS /*Enviamos el municipio del patron seleccionado en el tramite*/
											});
										})
										
									});
								</script>
		
								<div id="seccionEmpresaFusionada" class="col-sm-12">
									<div class="separadorseccion">
										<span>
											<c:if test="${codigo==175}">
												<spring:message code="tramite.sustitucion.titulo.patron" />
											</c:if>
											<c:if test="${codigo==20 || codigo==21}">
												<spring:message code="tramite.fusion.titulo.patron" />
											</c:if>
											<c:if test="${codigo==176}">
												<spring:message code="tramite.avisocambiodomicilio.titulo.patron" />
											</c:if>																				
											<span class="required" id="gridPatronesFusionados">*</span>
										</span>
									</div>
									<div id="contenedorComponenteBusquedaPatrones">
									</div>
								</div>									
						</div>
						</c:if>
						
						<c:if test="${codigo == 11 || codigo == 13 || codigo == 14 || codigo == 15 
						              || codigo == 16 || codigo == 17 || codigo == 18 
						              || codigo == 19 || codigo == 12}">						              
							<script type="text/javascript">
								$(function() {
									$("#primaSRTRestoTramites").numeric({
										maxDigits: 8,
										maxDecimalPlaces: 5,
										maxPreDecimalPlaces: 3
									});

								});
							</script>
						</c:if>
						
<!-- TERMINA SECCION PARA CAPTURA DE PATRONES -->

<!-- SECCION CLASIFICADOR -->				
							<div id="seccionClasificador" class="col-sm-12">
							<div class="row">
								<div class="col-sm-12">
									<div class="separadorseccion">
										<span>
											<spring:message code="label.aviso.ley.seguro" />
										</span>
									</div>
									<div class="etiqueta" style="text-align: justify; margin-bottom: 15px;">
										<spring:message code="label.aviso.conformidad" />:
									</div>
		
									<div class="row">
										<div id="wrapperIntsAnterior" class="col-sm-12">
											<div class="alert alert-info">												
												Selecciona tu clasificaci&oacute;n conforme al Cat&aacute;logo de Actividades para la Clasificaci&oacute;n de
													las Empresas en el Seguro de Riesgos de Trabajo
												<a href="javascript:seleccionarClasificacion();" id="clasificacionSelector" class="alert-link"
													onclick="uid_call('imss.gestion.patronal.modificaciones.srt.clasificacion','clickout');">
													aqu&iacute;</a><span class="required" id="gridClasificacionNuevaReq">*</span>.
											</div>
										</div>
									</div>
	
									<table id="gridClasificacionNueva" width="100%" cellpadding="0;" cellspacing="0"
										class="table table-striped table-bordered">
										<thead>
											<tr>
												<td align="center">
													<span id="cveFracc" class="etiqueta">
														<spring:message code="label.rp.clave.fraccion" />
													</span>
												</td>
												<td align="center">
													<span id="division" class="etiqueta">
														<spring:message code="label.rp.division" />
													</span>
												</td>
												<td align="center">
													<span id="gruposFr" class="etiqueta">
														<spring:message code="label.rp.grupo" />
													</span>
												</td>
												<td align="center">
													<span id="strFracc" class="etiqueta">
														<spring:message code="label.rp.descripcion.fraccion" />
													</span>
												</td>
												<td align="center">
													<span id="claseFrc" class="etiqueta">
														<spring:message code="label.rp.clase" />
													</span>
												</td>
												<td align="center">
													<span id="primaSTR" class="etiqueta">
														<spring:message code="label.rp.prima.srt" />
													</span>
												</td>
												</tr>
										</thead>
		
										<tr>
											<td align="center">
												<span class="dato" id="claveDivisionCompleta">
													${sujetoTramite.clasificacion.fraccion.grupo.division.numDivision}${sujetoTramite.clasificacion.fraccion.grupo.numGrupo}${sujetoTramite.clasificacion.fraccion.numFraccion}
												</span>
												<span class="dato" id="claveDivision" style="visibility: hidden;">${sujetoTramite.clasificacion.fraccion.grupo.division.numDivision}</span>
												<span class="dato" id="claveGrupo" style="visibility: hidden;">${sujetoTramite.clasificacion.fraccion.grupo.numGrupo}</span>
												<span class="dato" id="claveFraccion" style="visibility: hidden;">${sujetoTramite.clasificacion.fraccion.numFraccion}</span>
												<input type="hidden" id="fraccion" value="${sujetoTramite.clasificacion.fraccion.id}" />
												<input type="hidden" id="fraccionClasificador" value="${sujetoTramite.clasificacion.fraccion.grupo.division.numDivision}${sujetoTramite.clasificacion.fraccion.grupo.numGrupo}${sujetoTramite.clasificacion.fraccion.numFraccion}" />
												<input type="hidden" id="cveclaseClasificador" value="${sujetoTramite.clasificacion.fraccion.clase.descripcion}" />
											</td>
											<td align="center">
												<span class="dato" id="textDivison"> ${sujetoTramite.clasificacion.fraccion.grupo.division.descripcion}
												</span>
												<input type="hidden" id="divison" value="${sujetoTramite.clasificacion.fraccion.grupo.division.id}" />
											</td>
											<td align="center">
												<span class="dato" id="textGrupo"> ${sujetoTramite.clasificacion.fraccion.grupo.descripcion} </span>
												<input type="hidden" id="grupo" value="${sujetoTramite.clasificacion.fraccion.grupo.id}" />
											</td>
											<td align="center">
												<span class="dato" id="textFraccion"> ${sujetoTramite.clasificacion.fraccion.descripcionDetallada} </span>
											</td>
											<td align="center">
												<input type="hidden" id="claveClase" value="${sujetoTramite.clasificacion.fraccion.clase.clave}">
												<span class="dato" id="textClase"> ${sujetoTramite.clasificacion.fraccion.clase.descripcion} </span>
												<input type="hidden" id="clase" />
											</td>
											
											<td align="center">
												<span class="dato" id="textPrimaAnt">
													${sujetoTramite.clasificacion.fraccion.primaSRT} 
												</span>
												<input type="hidden" id="primaAnt" />
												<input type="hidden" id="primaClasificador" value="${sujetoTramite.clasificacion.fraccion.primaSRT}"/>
												<input type="hidden" id="cveclaseClasificador" value="${sujetoTramite.clasificacion.fraccion.clase.descripcion}" />
											</td>
										</tr>
									</table>
									<span class="error" id="gridClasificacionNuevaError"></span>
								</div>
							</div>
							</div>

<!-- TERMINA SECCION CLASIFICADOR -->
 
 <!-- SECCION PARA SELECCIONAR O CALCULAR LA PRIMA -->

							<div id="seccionPrimaSugerida" class="col-sm-12">
									<div class="row">
										<div id="wrapperIntsAnterior" class="col-sm-12">
											<span>&nbsp;</span>
										</div>
									</div>								
							
									<div class="row">
										<div id="wrapperIntsAnterior" class="col-sm-12">
											<div class="alert alert-info">												
												Calcula tu prima o selecciona tu clasificaci&oacute;n conforme al Cat&aacute;logo de Actividades para la Clasificaci&oacute;n de
													las Empresas en el Seguro de Riesgos de Trabajo
											</div>
										</div>
									</div>							
									<div class="row">
										<div id="wrapperIntsAnterior" class="col-sm-2">
											<input type="button" id="btnObtienePrima" name="btnObtienePrima"
												onclick="javascript:obtienePrimaFusSust();" class="btn btn-default btn-sm"
												value="Asignar prima sugerida">

										</div>
									</div>								
							</div>

<!-- TERMINA SECCION PARA SELECCIONAR O CALCULAR LA PRIMA -->

<!-- SECCION PARA CAPTURAR PRIMA-->
							<div id="seccionPrima" class="col-sm-12">
								<div class="row">
									<div id="wrapperIntsAnterior" class="col-sm-12">
										<span>&nbsp;</span>
									</div>
								</div>								
							
								<c:if test="${codigo == 11 || codigo == 13 || codigo == 14 || codigo == 15 
								              || codigo == 16 || codigo == 17 || codigo == 18 
								              || codigo == 19 || codigo == 12 || codigo == 22}">								
									<div class="row">
										<div class="col-sm-12">
											<jsp:include page="prima.jsp" >
												<jsp:param value="${codigo}" name="codigo"/>
												<jsp:param value="${sujetoTramite.clasificacion.primaSRTSugerida}" name="prima"/>
											</jsp:include>
										</div>
									</div>																			
								</c:if>
							
								<c:if test="${codigo == 21 || codigo == 20 || codigo == 175 || codigo == 176}">
									<div class="row">
										<div class="col-sm-12">
										<jsp:include page="prima.jsp" >
											<jsp:param value="${codigo}" name="codigo"/>
												<jsp:param value="${objClasificacion.primaSRTActual}" name="primaActual"/>
												<jsp:param value="${sujetoTramite.clasificacion.primaSRTFusionSust}" name="prima"/>
											</jsp:include>
										</div>
									</div>
								</c:if>

							<input type="hidden" id="indPrimaSugerida"
								value="${sujetoTramite.clasificacion.indPrimaSugerida}" /> 
							<input type="hidden" id="indSolSimilares"
								value="${sujetoTramite.clasificacion.indSolSimilares}" />

						</div>

<!--TERMINA SECCION PARA CAPTURAR PRIMA -->


						<div id="seccionProductosMaeriales" class="col-sm-12">

							<div class="separadorseccion">
								<span>
									<spring:message code="label.datos.actividad.declara" /><span class="required" id="gridProductosServiciosReq">*</span>
								</span>
							</div>

							<table style="width:100%">
								<tr>
									<td style="vertical-align: top; max-width: 400px;">
										<table id="gridProductosServicios" style="width: 100%; vertical-align: top;" cellpadding="0" cellspacing="0"
											class="table table-striped table-bordered table-word-wrap-fixed">
											<thead></thead>
											<tbody style="width: 100%;"></tbody>

											<tfoot>
												<tr>
													<td></td>
													<td>
														<div class="opciones">
															<form>
																
																<input type="button" onclick="dialogoEdicion('producto', 'Modificar'); uid_call('imss.gestion.patronal.modificaciones.srt.btn_dialogoModificarProducto','clickin');" class="btn btn-default btn-sm"
																	value="Modificar" >
																<input type="button" onclick="dialogoEdicion('producto', 'Eliminar'); uid_call('imss.gestion.patronal.modificaciones.srt.btn_dialogoEliminarProducto','clickin');" class="btn btn-danger btn-sm"
																	value="Eliminar">
																<input type="button" id="btnAgregarProducto" onclick="dialogoEdicion('producto', 'Agregar'); uid_call('imss.gestion.patronal.modificaciones.srt.btn_dialogoAgregarProducto','clickin');"
																		class="btn btn-primary btn-sm" value="Agregar">
																
																<a class="btn btn-xs icono-help delta-tooltip" data-toggle="tooltip" data-placement="top" title="Captura los productos o servicios que ofreces u ofrecer&aacute;s y que caracterizan mejor a tu empresa o negocio."></a>
															</form>
														</div>
													</td>
												</tr>
											</tfoot>
										</table>
										<span class="" id="">* Campos obligatorios</span>
									</td>

									<td style="vertical-align: top; max-width: 400px;">
										<table id="gridMaeriasMateriales" style="width: 100%; vertical-align: top;" cellpadding="0" cellspacing="0"
											class="table table-striped table-bordered table-word-wrap-fixed">
											<thead></thead>
											<tbody style="width: 100%;"></tbody>

											<tfoot>
												<tr>
													<td></td>
													<td>
														<div class="opciones">
															<form>
																
																<input type="button" onclick="dialogoEdicion('material', 'Modificar'); uid_call('imss.gestion.patronal.modificaciones.srt.btn_dialogoModificarMaterial','clickin');" class="btn btn-default btn-sm"
																	value="Modificar">
																<input type="button" onclick="dialogoEdicion('material', 'Eliminar'); uid_call('imss.gestion.patronal.modificaciones.srt.btn_dialogoEliminarMaterial','clickin');" class="btn btn-danger btn-sm"
																	value="Eliminar">
																	<input type="button" id="btnAgregarMateriales" onclick="dialogoEdicion('material', 'Agregar'); uid_call('imss.gestion.patronal.modificaciones.srt.btn_dialogoAgregarMaterial','clickin');"
																		class="btn btn-primary btn-sm" value="Agregar">
																<a class="btn btn-xs icono-help  delta-tooltip" data-toggle="tooltip" data-placement="top" title="Captura una lista de los insumos o materias primas esenciales, que empleas o emplear&aacute;s para la elaboraci&oacute;n de productos o prestaci&oacute;n de servicios.
Las principales materias primas o materiales utilizados son bienes consumibles utilizados que generalmente pierden sus propiedades y caracter&iacute;sticas para transformarse y formar parte de otros bienes o productos finales.
Para el caso de servicios se denomina as&iacute; a los recursos de entrada al proceso cuyo flujo de salida es el servicio entregado al cliente."></a>
															</form>
														</div>
													</td>
												</tr>
											</tfoot>

										</table>
									</td>
								</tr>
							</table>
						</div>


						<div id="seccionMaquinariaEquipo" class="col-sm-12">
							<div class="separadorseccion">
								<span>
									
									<spring:message code="label.maquinaria.equipo" /><span class="required" id="gridMaquinariaEquipoReq">*</span> <a class="delta-tooltip icono-help btn btn-xs" data-toggle="tooltip"
														title="Ingresa una lista de las m&aacute;quinas, herramientas o equipos esenciales, que empleas o emplear&aacute;s para transformar los insumos o materias primas, en los productos o servicios, de tu empresa o negocio."></a>
													
								</span>
							</div>

							<table id="gridMaquinariaEquipo" style="vertical-align: top; width: 100%; max-width: 830px;" cellpadding="0"
								cellspacing="0" class="table table-striped table-bordered table-word-wrap-fixed">
								<thead></thead>
								<tbody style="width: 100%;"></tbody>
								<tfoot>
									<tr>
										<td></td>
										<td colspan="5">
											<div class="opciones">
												<form>
													
													<input type="button" onclick="dialogoEdicion('equipo', 'Modificar'); uid_call('imss.gestion.patronal.modificaciones.srt.btn_dialogoModificarEquipo','clickin');" class="btn btn-default btn-sm"
														value="Modificar">
													<input type="button" onclick="dialogoEdicion('equipo', 'Eliminar'); uid_call('imss.gestion.patronal.modificaciones.srt.btn_dialogoEliminarEquipo','clickin');" class="btn btn-danger btn-sm"
														value="Eliminar">
													<input type="button" id="btnAgregarMaquinariaEquipo" onclick="dialogoEdicion('equipo', 'Agregar'); uid_call('imss.gestion.patronal.modificaciones.srt.btn_dialogoAgregarEquipo','clickin');"
															class="btn btn-primary btn-sm" value="Agregar">
													
												</form>
											</div>
										</td>
									</tr>
								</tfoot>
							</table>
							<span class="" id="">* Campos obligatorios</span>
						</div>


						<div id="seccionTransporte" class="col-sm-12">
							<div class="separadorseccion">
								<span>
									<spring:message code="label.equipo.transporte" />
									<a class="delta-tooltip icono-help btn btn-xs" data-toggle="tooltip"
														title="Indica el nombre, n&uacute;mero de unidades, uso, combustible o energ&iacute;a y capacidad o potencia del equipo de transporte que empleas para el desarrollo de las actividades de tu negocio o empresa, ya sea &eacute;ste utilizado para el acopio, traslado, entrega, distribuci&oacute;n o venta de materias primas, materiales, productos, prestaci&oacute;n de  los servicios que ofreces, o para el transporte de personal.">
									</a>			
								</span>
							</div>
							<table>
									<tr>
										<td>
											<span class="etiqueta">&iquest;Cuentas con equipo de transporte?</span>
											<a class="delta-tooltip icono-help btn btn-xs" data-toggle="tooltip"
								title="Indica el nombre, n&uacute;mero de unidades, uso, combustible o energ&iacute;a y capacidad o potencia del equipo de transporte que empleas para el desarrollo de las actividades de tu negocio o empresa, ya sea &eacute;ste utilizado para el acopio, traslado, entrega, distribuci&oacute;n o venta de materias primas, materiales, productos, prestaci&oacute;n de  los servicios que ofreces, o para el transporte de personal.">
								</a>
										</td>
										<td style="padding-left:15px;">
											<form:radiobutton id="siCuetaConTransporte" path="sujetoTramite.cuentaConTransporte" value="1"
												onchange="toggleCuentaConTransporte(1)" /> S&iacute;
											<form:radiobutton id="noCuetaConTransporte" path="sujetoTramite.cuentaConTransporte" value="0"
												onchange="toggleCuentaConTransporte(0)" /> No
										</td>
									</tr>
								</table>

							<table id="gridTrasporte" style="vertical-align: top; width: 100%; max-width: 830px;" cellpadding="0"
								cellspacing="0" class="table table-striped table-bordered table-word-wrap-fixed">
								<thead></thead>
								<tbody style="width: 100%;"></tbody>
								<tfoot>
									<tr>
										<td></td>
										<td colspan="5">
											<div class="opciones">
												<form>
													
													<input id="modificarTransporte" type="button" onclick="dialogoEdicion('transporte', 'Modificar'); uid_call('imss.gestion.patronal.modificaciones.srt.btn_dialogoModificarTransporte','clickin');"
														class="btn btn-default btn-sm" value="Modificar">
													<input id="eliminarTransporte" type="button" onclick="dialogoEdicion('transporte', 'Eliminar'); uid_call('imss.gestion.patronal.modificaciones.srt.btn_dialogoEliminarTransporte','clickin');"
														class="btn btn-danger btn-sm" value="Eliminar">
													<input id="agregarTransporte" type="button" onclick="dialogoEdicion('transporte', 'Agregar'); uid_call('imss.gestion.patronal.modificaciones.srt.btn_dialogoAgregarTransporte','clickin');"
															class="btn btn-primary btn-sm" value="Agregar">
												</form>
											</div>
										</td>
									</tr>
								</tfoot>
							</table>
						</div>


						<div id="seccionProcesos" class="col-sm-12">
							<form id="formProcesos">
								<div class="separadorseccion">
									<span>
										<spring:message code="label.proceso.trabajo" />
									</span>
								</div>

								<table cellspacing="0" cellpadding="0" class="tablaverde2" style="width: 100%;" id="tbActividades">
									<tbody>
										<tr>
											<td>
												<input type="hidden" id="procesoClave" value="<c:if test='${proceso != null}'>${proceso.clave}</c:if>" />
												<div style="display: table; padding: 30px; width: 100%;" id="tbProceso">
													<div style="display: table-row;" id="proceso-inicial">
														<h6 style="font-size: 1.0em !important;">
															
															<label>
																
																<!-- de la actividad de su empresa o negocio, precisando los procesos iniciales, intermedios y finales. En su caso, describa los procesos que realiza para trasformar, fabricar o procesar materias primas o insumos, en los productos de su empresa o negocio, especifique los insumos y para qu&eacute; se utiliza la maquinaria, herramienta o equipo que se&#241;alaste. Trat&aacute;ndose de empresas prestadoras de servicios, deber&aacute; describir los procesos iniciales, intermedios y finales del o los  servicios que presta, especifique los insumos y para qu&eacute; se utiliza la maquinaria, herramienta o equipo que se&#241;alaste. -->
																<spring:message code="label.procesos.iniciales" /><span class="required" id="procesoInicialReq">*</span>: 
																<a class="delta-tooltip icono-help btn btn-xs" data-toggle="tooltip" title="Describe los procesos de trabajo "></a>
															</label>
															
														</h6>
														<div style="width: 100%; height: 150px;">
															<textarea class="textClasificacion" style="width: 98%;" rows="7" id="procesoInicial"
																onkeypress="validaSize(this, 600, event)" onkeyup="validaSize(this, 600, event)"
																onblur="validaSize(this, 600, event);"><c:if test="${proceso != null}">${proceso.desInicial}</c:if></textarea>
														</div>
														<span class="error" id="procesoInicialError"></span>
													</div>

													<div style="display: table-row;" id="proceso-intermedio">
														<h6 style="font-size: 1.0em !important;">
															
															<label>
																	<spring:message code="label.procesos.intermedios" /><span class="required" id="procesoIntermedioReq">*</span>: 
																<a class="delta-tooltip icono-help btn btn-xs" data-toggle="tooltip"
																title="Describe los procesos de trabajo de la actividad de tu empresa o negocio, precisando los procesos iniciales, intermedios y finales. En su caso, describe los procesos que realizas para trasformar, fabricar o procesar materias primas o insumos, en los productos de tu empresa o negocio, especifica los insumos y para qu&eacute; se utiliza la maquinaria, herramienta o equipo que se&#241;alaste. Trat&aacute;ndose de empresas prestadoras de servicios, deber&aacute;s describir los procesos iniciales, intermedios y finales del o los  servicios que prestas, especifica los insumos y para qu&eacute; se utiliza la maquinaria, herramienta o equipo que se&#241;alaste.">
																</a>
															</label>
															
														</h6>
														<div style="width: 100%; height: 150px;">
															<textarea class="textClasificacion" style="width: 98%;" rows="7" id="procesoIntermedio"
																onkeypress="validaSize(this, 600, event)" onkeyup="validaSize(this, 600, event)"
																onblur="validaSize(this, 600, event);"><c:if test="${proceso != null}">${proceso.desIntermedio}</c:if></textarea>
														</div>
														<span class="error" id="procesoIntermedioError"></span>
													</div>

													<div style="display: table-row;" id="proceso-final">
														<h6 style="font-size: 1.0em !important;">
															
															<label >
																	<spring:message code="label.procesos.finales" /><span class="required" id="procesoFinalReq">*</span>: 
																<a class="delta-tooltip icono-help btn btn-xs" data-toggle="tooltip"
																title="Describe los procesos de trabajo de la actividad de tu empresa o negocio, precisando los procesos iniciales, intermedios y finales. En su caso, describe los procesos que realizas para trasformar, fabricar o procesar materias primas o insumos, en los productos de tu empresa o negocio, especifica los insumos y para qu&eacute; se utiliza la maquinaria, herramienta o equipo que se&#241;alaste. Trat&aacute;ndose de empresas prestadoras de servicios, deber&aacute;s describir los procesos iniciales, intermedios y finales del o los  servicios que prestas, especifica los insumos y para qu&eacute; se utiliza la maquinaria, herramienta o equipo que se&#241;alaste.">
															</a>
															</label>
														</h6>
														<div style="width: 100%; height: 150px;">
															<textarea class="textClasificacion" style="width: 98%;" rows="7" id="procesoFinal"
																onkeypress="validaSize(this, 600, event)" onkeyup="validaSize(this, 600, event)"
																onblur="validaSize(this, 600, event);"><c:if test="${proceso != null}">${proceso.desFinal}</c:if></textarea>
														</div>
														<span class="error" id="procesoFinalError"></span>
													</div>
												</div>
											</td>
										</tr>
									</tbody>
								</table>
							</form>
						</div>


						<div id="seccionPersonal" class="col-sm-12">
							<div class="separadorseccion">
								<span>
									
									<spring:message code="label.personal" /><span class="required" id="gridPersonalReq">*</span>
									<a class="delta-tooltip icono-help btn btn-xs" data-toggle="tooltip"
														title="Indica el n&uacute;mero de trabajadores con que cuenta el patr&oacute;n por grupos de oficio u ocupaci&oacute;n para el desarrollo de su actividad, describiendo el trabajo que desarrollan, el cual contribuye a la  fabricaci&oacute;n o venta de productos o prestaci&oacute;n de servicios."></a>		
								</span>
							</div>
							<table id="gridPersonal" style="vertical-align: top; width: 100%; max-width: 830px;" cellpadding="0"
								cellspacing="0" class="table table-striped table-bordered">
								<thead></thead>
								<tbody style="width: 100%;"></tbody>
								<tfoot>
									<tr>
										<td></td>
										<td colspan="2">
											<div class="opciones">
												<form>
													
													<input type="button" onclick="dialogoEdicion('personal', 'Modificar'); uid_call('imss.gestion.patronal.modificaciones.srt.btn_dialogoModificarPersonal','clickin');" class="btn btn-default btn-sm"
														value="Modificar">
													<input type="button" onclick="dialogoEdicion('personal', 'Eliminar'); uid_call('imss.gestion.patronal.modificaciones.srt.btn_dialogoEliminarPersonal','clickin');" class="btn btn-danger btn-sm"
														value="Eliminar">
														<input type="button" id="btnAgregarPersonal" onclick="dialogoEdicion('personal', 'Agregar'); uid_call('imss.gestion.patronal.modificaciones.srt.btn_dialogoAgregarPersonal','clickin');"
															class="btn btn-primary btn-sm" value="Agregar">
													
												</form>
											</div>
										</td>
									</tr>
								</tfoot>
							</table>
							<span class="" id="">* Campos obligatorios</span>
						</div>

						
						<div id="seccionActividades" class="col-sm-12">
							<div class="separadorseccion">
								<spring:message code="label.actividades.complementarias" /> 
								<a class="delta-tooltip icono-help btn btn-xs" data-toggle="tooltip"
									title="Se consideran todas aquellas adicionales a la actividad principal que realiza una empresa o negocio para brindar a los clientes una mejor atenci&oacute;n o servicio, o como complemento a los productos o servicios que ofrece a los clientes."></a>
							</div>

							<table width="100%">
								<tr>
									<td style="width: 40%;">
										<spring:message code="label.distribuidor.entrega" />
										<br>
										<table width="100%" style="padding-left: 55px;">
											<tbody>
												<tr class="odd">
													<td class="dtJustifyClassColumn">
														<c:if test="${sujetoTramite.cuentaConTransporte == 0}">
															<input type="checkbox" id="indTransportePropioTemp" name="indTransportePropioTemp" disabled="disabled" class="ns_">
															<spring:message code="label.transporte.propio" />
														</c:if>
														<c:if test="${sujetoTramite.cuentaConTransporte == 1}">
															<c:if test="${sujetoTramite.clasificacion.indTransportePropio == 1}">
																<input type="checkbox" id="indTransportePropioTemp" name="indTransportePropioTemp" checked="true" class="ns_">
																<spring:message code="label.transporte.propio" />
															</c:if>
															<c:if test="${sujetoTramite.clasificacion.indTransportePropio != 1}">
																<input type="checkbox" id="indTransportePropioTemp" name="indTransportePropioTemp" class="ns_">
																<spring:message code="label.transporte.propio" />
															</c:if>
														</c:if>
													</td>
												</tr>

												<tr class="even">
													<td class="dtJustifyClassColumn">
														<c:if test="${sujetoTramite.cuentaConTransporte == 0}">
															<input type="checkbox" id="indTransporteAjenoTemp" name="indTransporteAjenoTemp" disabled="disabled" class="ns_">
															<spring:message code="label.transporte.ajeno" />
														</c:if>
														<c:if test="${sujetoTramite.cuentaConTransporte == 1}">
															<c:if test="${sujetoTramite.clasificacion.indTransporteAjeno == 1}">
																<input type="checkbox" id="indTransporteAjenoTemp" name="indTransporteAjenoTemp" checked="true" class="ns_">
																<spring:message code="label.transporte.ajeno" />
															</c:if>
															<c:if test="${sujetoTramite.clasificacion.indTransporteAjeno != 1}">
																<input type="checkbox" id="indTransporteAjenoTemp" name="indTransporteAjenoTemp" class="ns_">
																<spring:message code="label.transporte.ajeno" />
															</c:if>
														</c:if>
													</td>
												</tr>

												<tr class="odd">
													<td class="dtJustifyClassColumn">
														<c:if test="${sujetoTramite.cuentaConTransporte == 0}">
															<input type="checkbox" id="indNoDistribuyeTemp" name="indNoDistribuyeTemp" checked="true" class="ns_">
															<spring:message code="label.no.distribuye" />
														</c:if>
														<c:if test="${sujetoTramite.cuentaConTransporte == 1}">
															<input type="checkbox" id="indNoDistribuyeTemp" name="indNoDistribuyeTemp" disabled="disabled" class="ns_">
															<spring:message code="label.no.distribuye" />
														</c:if>
													</td>
												</tr>
											</tbody>
										</table>
									</td>
									<td valign="top" style="width: 60%;">

										<c:if test="${sujetoTramite.clasificacion.indServiciosATerceros == 1}">
												<input type="checkbox" id="indServiciosTercerosTemp" name="indServiciosTercerosTemp" checked="true" class="ns_">
													<spring:message code="label.servicios.terceros" />
											</c:if>
											<c:if test="${sujetoTramite.clasificacion.indServiciosATerceros != 1}">
												<input type="checkbox" id="indServiciosTercerosTemp" name="indServiciosTercerosTemp" class="ns_">
													<spring:message code="label.servicios.terceros" />
											</c:if>
										<a class="btn btn-xs icono-help delta-tooltip" data-toggle="tooltip"
											title="Selecciona este campo si cuentas con servicios de instalaci&oacute;n, reparaci&oacute;n o mantenimiento de los productos que elaboras o vendes."></a>
									</td>
								</tr>
							</table>
						</div>

						<div id="seccionBienesInmuebles" class="col-sm-12">
							<div class="separadorseccion">
								<span>
									<spring:message code="label.bienes.inmuebles" /><span class="required" id="gridBienesReq">*</span>
								</span>
							</div>
							<table id="gridBienes" style="vertical-align: top; width: 100%; max-width: 830px;" cellpadding="0"
								cellspacing="0" class="table table-striped table-bordered">
								<thead></thead>
								<tbody style="width: 100%;">
								</tbody>
								<tfoot>
									<tr>
										<td></td>
										<td colspan="5">
											<div class="opciones">
												<form>
													
													<input type="button" onclick="dialogoEdicion('bienes', 'Modificar'); uid_call('imss.gestion.patronal.modificaciones.srt.btn_dialogoModificarBienes','clickin');" class="btn btn-default btn-sm"
														value="Modificar">
													<input type="button" onclick="dialogoEdicion('bienes', 'Eliminar'); uid_call('imss.gestion.patronal.modificaciones.srt.btn_dialogoEliminarBienes','clickin');" class="btn btn-danger btn-sm"
														value="Eliminar">
													<span class="delta-tooltip" data-toggle="tooltip"
														title="Cantidad y descripci&oacute;n de los bienes. Describe el tipo de bien o bienes que originan la modificaci&oacute;n que presentas ante el Instituto e indica la cantidad de los mismos.">
														<input type="button" id="btnAgregarBienes" onclick="dialogoEdicion('bienes', 'Agregar'); uid_call('imss.gestion.patronal.modificaciones.srt.btn_dialogoAgregarBienes','clickin');"
															class="btn btn-primary btn-sm" value="Agregar">
													</span>
												</form>
											</div>
										</td>
									</tr>
								</tfoot>
							</table>
							<span class="error" id="gridBienesError"></span>
							<table cellspacing="0" cellpadding="0" class="tablaverde2" style="width: 100%;" id="tbActividades">
								<tbody>
									<tr>
										<td>
											<div style="display: table; padding: 30px; width: 100%;" id="tbProceso">
												<div style="display: table-row;" id="proceso-intermedio">
													<h6 style="font-size: 1.0em !important;">
														

														<span class="delta-tooltip" data-toggle="tooltip"
															title="Indica brevemente para que es o ser&aacute; utilizado el bien o bienes que originan la modificaci&oacute;n presentada ante el Instituto.">
															<label>
																<spring:message code="label.bienes.uso" /><span class="required" id="afectacionBienesReq">*</span>:
															</label>
														</span>
													</h6>
													<div style="width: 100%; height: 150px;">
														<textarea class="textClasificacion" style="width: 98%;" rows="7" id="afectacionBienes"
															onkeypress="validaSize(this, 600, event)" onkeyup="validaSize(this, 600, event)">${sujetoTramite.desUsosBienes}</textarea>
													</div>
													<span class="error" id="afectacionBienesError"></span>
												</div>

												<div style="display: table-row;" id="proceso-inicial">
													<h6 style="font-size: 1.0em !important;">
														
														<span
															title="Afectaci&oacute;n directa o indirecta al desarrollo de la actividad econ&oacute;mica. Describe brevemente, c&oacute;mo el bien o los bienes objeto de la compra, enajenaci&oacuten, arrendamiento, comodato o fideicomiso traslativo, afectan directa o indirectamente, en el desarrollo de las actividades de la empresa o negocio.">
															<label>
																<spring:message code="label.bienes.afectacion" /><span class="required" id="usoBienesReq">*</span>:
															</label>
														</span>
													</h6>
													<div style="width: 100%; height: 150px;">
														<textarea class="textClasificacion" style="width: 98%;" rows="7" id="usoBienes"
															onkeypress="validaSize(this, 600, event)" onkeyup="validaSize(this, 300, event)">${sujetoTramite.desAfectacion}</textarea>
													</div>
													<span class="error" id="usoBienesError"></span>
												</div>

											</div>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						
						<c:if test="${codigo == 175}">
							<div id="seccionDocAdjuntos" class="col-sm-12">
								<div class="separadorseccion">
									<span> <spring:message
											code="tramite.sustitucion.titulo.documentos" /> <span
										class="required" id="gridDocumentos">*</span>
									</span>
								</div>
								<div class="alert alert-info" style="text-align: left">
								<ul>
									  <li>NOTIFICACI&Oacute;N A LOS TRABAJADORES DE LA SUSTITUCI&Oacute;N PATRONAL, RECONOCIENDO SUS DERECHOS LABORALES</li>
									  <li>CONVENIO DE SERVICIOS M&Eacute;DICOS CON REVERSI&Oacute;N DE CUOTAS</li>
									  <li>AFIL01</li>
									  </ul>
									</div>
								<div id="contenedorComponenteDocAdjunto">
									<label class="control-label" for="updateFile-01"><spring:message
											code="etiqueta.archivoIMSSTable" />:</label> <input
										class="form-control dataText" id="updateFile-01" name="updateFile-01" type="file"
										size="7000000" multiple>
										<input type="hidden" id="claveTipoTramite" name="claveTipoTramite" value="175">
											<input type="hidden" id="folio" name="folio" value="${idSolicitud}">
											<input type="hidden" id="numDoc" name="numDoc" value="">

									<div class="form-group">
										<div class="col-md-offset-10 col-md-2"
											style="padding-top: 20px; padding-left: 7px;">
											<button id="btn_AdjuntarImssRelacion-01" type="button"
												class="btn btn-primary pull-right"
												onclick="adjuntarDocumento()">
												<spring:message code="etiqueta.buttonAdjuntar" />
											</button>
										</div>
									</div>
								</div>
							</div>
							
							<div id="seccionMostrarDocAdjunto" class="col-sm-12"
								style="padding-top: 20px;">
								  
							
								
							</div>
					
						</c:if>	
						
						<div id="seccionCocumentosRequeridos" class="col-sm-12">
							<div class="separadorseccion">
								<span>
									<spring:message code="label.seccion.documentos.requeridos" />
								</span>				
							</div>
						    <div id="divDocumentosRequeridos">
						        <c:choose>
									<c:when test="${not empty documentos}">
							
										<input type="hidden" value="${documentosRequeridos}" id="documentosRequeridos" />
										<input type="hidden" value="${documentos}" id="documentosHidden" />
							
										<table id="tablaDocumentos" style="width: 100%;" class="table table-striped table-bordered" cellpadding="0"
											cellspacing="0" border="0">
											<tbody>
												<c:forEach var="doctoReqTramite" items="${documentos}">
													<tr>
														<td><input type="checkbox" id="documentosRequeridosCheck${doctoReqTramite.documentoPorTipo.documento.cveIdDocumento}" name="documentosRequeridosCheck" class="ns_"></td>
														<td>${doctoReqTramite.documentoPorTipo.documento.desDocumento}
														<span class="delta-tooltip icono-help" data-toggle="tooltip" data-html="true" title="${doctoReqTramite.refDetalleDoctoRequerido}" ></span>
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:when>
									<c:otherwise>
										<span class="error-widget">Este tr&aacute;mite no requiere de documentos</span>
									</c:otherwise>
								</c:choose>
						    </div>
						</div>
						
										
					</div>

				</c:otherwise>
			</c:choose>
		</div>
		
	</div>

	<div class="pie row">
		<div class="col-sm-4">
			<div style="float: left; padding: 11px 0px;"><span class="required" id="labelCamposObligatoriosGeneral">*</span><spring:message code="label.camposObligatorios"/></div>
		</div>
		<div class="col-sm-8">
			<div class="pull-right">
			<c:if test="${empty mensajeError}">
				<div class="btn-group dropup">
					<a href="#" class="btn btn-primary" onclick="uid_call('imss.gestion.patronal.modificaciones.srt.opciones','clickin');">
						<spring:message code="label.menus.opciones" />
					</a>
					<a href="#" data-toggle="dropdown" class="btn btn-primary dropdown-toggle" onclick="uid_call('imss.gestion.patronal.modificaciones.srt.opciones','clickin');">
						<span class="caret"></span>
					</a>
					<ul class="dropdown-menu pull-right">
						<li>
							<a id="finalizarTramite" onclick="uid_call('imss.gestion.patronal.modificaciones.srt.btn_finalizarTramite','clickin');">
								<i class="glyphicon glyphicon-ok"></i>
								<c:choose>
									<c:when test="${origenInternet}">
										<spring:message code="wizard.button.internet.finalizarTramite"/>
									</c:when>
									<c:otherwise>
										<spring:message code="wizard.button.finalizarTramite"/>
									</c:otherwise>
								</c:choose>
							</a>
						</li>
						<li>
							<a id="guardarTramite" onclick="uid_call('imss.gestion.patronal.modificaciones.srt.btn_guardarTramite','clickin');">
								<i class="glyphicon glyphicon-download-alt"></i>
								<c:choose>
									<c:when test="${origenInternet}">
										<spring:message code="wizard.button.internet.guardarTramite"/>
									</c:when>
									<c:otherwise>
										<spring:message code="wizard.button.guardarTramite"/>
									</c:otherwise>
								</c:choose>
							</a>
						</li>
						<li>
							<a id="guardarCerrarTramite" onclick="uid_call('imss.gestion.patronal.modificaciones.srt.btn_guardarCerrarTramite','clickin');">
								<i class="glyphicon glyphicon-remove"></i>
								<c:choose>
									<c:when test="${origenInternet}">
										<spring:message code="wizard.button.internet.guardarCerrar"/>
									</c:when>
									<c:otherwise>
										<spring:message code="wizard.button.guardarCerrar"/>
									</c:otherwise>
								</c:choose>	
							</a>
						</li>
						<li>
							<a id="cancelarTramite" onclick="uid_call('imss.gestion.patronal.modificaciones.srt.btn_cancelarTramite','clickin');">
								<i class="glyphicon glyphicon-trash"></i>
								<c:choose>
									<c:when test="${origenInternet}">
										<spring:message code="wizard.button.internet.cancelarTramite"/>
									</c:when>
									<c:otherwise>
										<spring:message code="wizard.button.cancelarTramite"/>
									</c:otherwise>
								</c:choose>
							</a>
						</li>
					</ul>
				</div>
			</c:if>
			</div>
		</div>
	</div>
</div>

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar"
	title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			&iquest;Desea cancelar la solicitud pendiente con folio: <strong>${folioSolicitud}</strong>&#63;
			Est&aacute;s a punto de cancelar el tr&aacute;mite &#34;${descripcionTipoTramite}&#34;&#63; del RP &#34;<span id="rpMessage"></span>&#34;&#63;.
			&iquest;Est&aacute;s seguro de querer cancelarlo&#63; Se perder&aacute; toda la informaci&oacute;n y cancelar&aacute; el folio de cita y tr&aacute;mite	</p>
</div>

<div id="dialog-confirm" title="Mensaje confirmaci&oacute;n">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> <label
			id="mensajeDialogo"></label>
	</p>
</div>

<div id="firmaDigitalDlg"></div>

<!-- Divs para dialogos de mensajes -->
<div id="dialogoGrids">
	<form id="formaDialogo"></form>
</div>

<div id="dialogoConfirmacion">
	<p><span id="textoConfirmacion"></span></p>
</div>

<div id="dialogoConfirmacionCambioCita" style="display: none;">
	<p><span>&iquest;Est&aacute;s seguro de cambiar tu cita?</span></p>
	<p><span>Despu&eacute;s de confirmar imprime tu comprobante</span></p>
</div>

<div id="dialogoMensajes">
	<p><span id="textoMensaje"></span></p>
</div>

<div id="dialogoConcluirSolicitudFirma" title="Concluir solicitud" style="display: none;">
	<div class="page_holder" style="width: 100% !important;">
		<div class="row	">
			<div class="cell form-comment">
				<span id="textoCS">
					<div id="dialogoConcluirSolicitud" title="Concluir solicitud"
						style="margin-left: 45px; margin-right: 45px;">
						<!-- <p>
							<span class="ui-icon ui-icon-alert"
								style="float: left; margin: 0 7px 20px 0;"></span> Selecciona la
							forma en la cual deseas concluir la solicitud
						</p> -->
						<div class="contenedor">
							<div class="row">
							<c:if test="${empty mensajeError && origenInternet == false}">
								<div class="cell">
									<h2 style="font-size: 14px; ">Concluir tramite
										${descripcionTipoTramite}</h2>
									<p style="font-size: .9em; text-align: justify;">Esta usted a punto de concluir
										&eacute;l tr&aacute;mite: "${descripcionTipoTramite}", si
										est&aacute; seguro de que la informaci&oacute;n capturada es
										correcta, presione el bot&oacute;n "Descargar"  para obtener e imprimir los documentos resultantes del tr&aacute;mite, si desea
										revisar la informaci&oacute;n presione el bot&oacute;n
										"Revisar".</p>
									<div style="text-align: center;">
									<input id="movPatImprimirSTR" type="button"
										onclick="finalizarClasificacion(); uid_call('imss.gestion.patronal.modificaciones.srt.btn_dialogoModificarProducto','clickin');"
										class="btn btn-default btn-sm" value="${origenInternet ? 'Enviar' :'Descargar' }"> 
									<input id="movPatRevisarSTR"
										type="button"
										onclick="guardarClasificacionPrevioRevisar(); uid_call('imss.gestion.patronal.modificaciones.srt.btn_dialogoEliminarProducto','clickin');"
										class="btn btn-danger btn-sm" value="Revisar"> 
									<input id="movPatRegresar"
										type="button" 
										onclick="closeModalFinalizacion(); uid_call('imss.gestion.patronal.modificaciones.srt.btn_dialogoAgregarProducto','clickin');"
										class="btn btn-primary btn-sm" value="Regresar">
									</div>
								</div>
							</c:if>
							<c:if test="${empty mensajeError && origenInternet == true}">
								<div class="cell">
									<p style="font-size: .9em; text-align: justify;">
										Est&aacute; usted a punto de concluir el pre-registro y continuar con la solicitud de cita para el tr&aacute;mite: "${descripcionTipoTramite}", si
										est&aacute; seguro de que la informaci&oacute;n capturada es
										correcta, presione el bot&oacute;n "Enviar", si desea
										revisar la informaci&oacute;n presione el bot&oacute;n
										"Revisar".</p>
									<div style="text-align: center;">
									<input id="movPatEnviar" type="button"
										onclick="enviarInternet(); uid_call('imss.gestion.patronal.modificaciones.srt.btn_dialogoModificarProducto','clickin');"
										class="btn btn-primary btn-sm" value="Enviar"> 
									<!--<input id="movPatRevisar"
										type="button"
										onclick="closeModalFinalizacion(); uid_call('imss.gestion.patronal.modificaciones.srt.btn_dialogoEliminarProducto','clickin');"
										class="btn btn-danger btn-sm" value="Revisar"> -->
									<input id="movPatRegresar"
										type="button" 
										onclick="closeModalFinalizacion(); uid_call('imss.gestion.patronal.modificaciones.srt.btn_dialogoAgregarProducto','clickin');"
										class="btn btn-danger btn-sm" value="Revisar">
									</div>
								</div>
							</c:if>
							</div>
						</div>
					</div>
				</span>
			</div>
		</div>
	</div>
</div>

<div id="dialogoGenerarCita" title="Pre registro ${descripcionTipoTramite}" style="display: none;">
	<div class="page_holder" style="width: 100% !important;">
		<div class="row	">
			<div class="cell form-comment">
				<span id="textoCS">
					<div id="divGenerarCita" title="Pre registro ${descripcionTipoTramite}"
						style="margin-left: 45px; margin-right: 45px;">
						<div class="contenedor">
							<div class="row">
								<div class="cell">
									<div class="col-sm-12">
										<h3 style="font-size: 14px; ">Estimado patr&oacute;n, es indispensable que presente el poder notarial cuando acuda a la subdelegaci&oacute;n a concluir el tr&aacute;mite.</h3>
									</div>									
									<div class="col-sm-12"><h2 style="font-size: 14px; ">Has realizado el pre registro del tr&aacute;mite ${descripcionTipoTramite}:</h2></div>
									<div class="col-sm-12" style="text-align: left;">
										<span id="fechaGeneracionCita"> </span> <span>con folio: </span><b id="folioCita"> </b>
									</div>									
									<div class="col-sm-12">
										<b id="nombreCita"></b>
									</div>
									<div class="col-sm-12"><p>  </p></div>
									<div class="col-sm-12" style="text-align: left;"><span>Debes presentar la documentaci&oacute;n probatoria se&ntilde;alada en el comprobante de cita en original y copia con el fin de corroborar la informaci&oacute;n en la subdelegaci&oacute;n </span> <span id="subDelCita"></span> <span>,</span> <span>el d&iacute;a</span> <b id="diaCita"></b>.</div>
									<div class="col-sm-12"><p>  </p></div>
									<div class="col-sm-12" style="text-align: justify;">
									<span>Recuerda imprimir tu comprobante de cita para presentarlo en la subdelegaci&oacute;n y finalizar tu tr&aacute;mite, para ello oprime el bot&oacute;n 
										"IMPRIMIR COMPROBANTE".</span></div>
									<div class="col-sm-12"><p>  </p></div>
									<div class="col-sm-12" style="text-align: justify;">
										<span>Puedes asistir a las ventanillas del IMSS en su subdelegaci&oacute;n antes del d&iacute;a de la cita; pero tendr&aacute;s; que esperar turno para 
											ser atendido.</span></div>
									<div class="col-sm-12"><p>  </p></div>
									<div class="col-sm-12" style="text-align: justify;">
										<span>Si deseas reasignar tu cita en otro d&iacute;a por favor selecciona la fecha del calendario que se muestra. </span>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="cell">
								<div class="col-sm-12"><p>  </p></div>
								<input type="hidden" id="citaOriginal" />
								</div>
							</div>
							<div class="row">
								<div class="cell">
								<div class="col-sm-12"><p>  </p></div>
								</div>
							</div>
							<div id="divCambioCita" class="row">
								<div class="cell">
									<div class="col-sm-4"><span>Fecha de reasignaci&oacute;n de la cita:</span></div>
									<div class="col-sm-4" >
										<input type="text" id="fechaReasignacion" class="form-control " style="width: 100px;" readonly />
									</div>
									<div class="col-sm-4">
										<input id="cambiarFechaCita"
											type="button"
											onclick="cambiarFechaCita(); "
											class="btn btn-danger btn-sm" value="Cambiar fecha">
									</div>
								</div>
							</div>
							<div class="row">
								<div class="cell">
								<div class="col-sm-12"><p>  </p></div>
								</div>
							</div>
							<div class="row">
								<div class="cell">
									<div class="col-sm-4">
										<input id="imprimirComprobanteInternet"
											type="button"
											onclick="imprimirCita(); "
											class="btn btn-danger btn-sm" value="Imprimir comprobante">
									</div>
									<div class="col-sm-4">
										<input id="btnFinalizarInternet"
											type="button"
											onclick="cerrarWizardInternet();"
											class="btn btn-default btn-sm" value="Finalizar" disabled>
									</div>
								</div>
							</div>
						</div>
					</div>
				</span>
			</div>
		</div>
	</div>
</div>


<div id="pdfcontainer"></div>

<div id="domiciliosComponent"></div>
<div id="dialogoReporte" style="width='100%' height='100%'"><div id="reporteFrame" style="width='100%' height='100%'"></div></div>
<div id="dialogoReporteFinal" style="width='100%' height='100%'"><div id="reporteFinalFrame" style="width='100%' height='100%'"></div></div>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<form id="formaAcuse" name="formaAcuse" action="${contextpath}/movPat/clasificacion/presentarAcuse" method="POST"></form>

<form id="formaGeneraAviso" name="formaGeneraAviso" action="${contextpath}/movPat/clasificacion/mostrarAviso" method="POST" target="_blank"></form>
<form id="formaRegresoDetalle" name="formaRegresoDetalle" action="${contextpath}/movPat/clasificacion/mostrarAcuse" method="POST" target="_blank"></form>
<script language="JavaScript1.2" src="${staticResourcesPath}/js/comscore/Form.js"></script>