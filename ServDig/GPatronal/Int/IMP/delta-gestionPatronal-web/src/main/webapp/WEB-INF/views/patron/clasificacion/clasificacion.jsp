<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.FraccionEnum"%>

<c:set var="sujetoObligado" value="${sujetoObligado}"/>
<c:set var="objClasificacion" value="${sujetoObligado.clasificacion}"/>
<c:set var="fraccion" value="${objClasificacion.fraccion}"/>
<c:set var="fisica" value="${sujetoObligado.fisica}"/>
<c:set var="moral" value="${sujetoObligado.moral}"/>
<c:set var="proceso" value="${sujetoTramite.proceso}"/>
<c:set var="tramite" value ="${idTramite}"/>
<c:set var="codigo" value ="${idTramite.codigo}"/>
<c:set var="sujetoTramite" value="${sujetoTramite}"/>
<c:set var="idSolicitud" value="${idSolicitud}"/>
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="typeLogin" value="<%=session.getAttribute(\"showFinalizarFD\")%>" />

<script>
	var context_path = '<%= request.getContextPath()%>';
	var sessionId = '<%= request.getSession().getId()%>';
	var esOperador = ${esOperador};
	var host = "<%=request.getContextPath()%>";
	var fraccionAgricultura = '<%=FraccionEnum.AGRICULTURA.getCodigo()%>';
	
	var idSujetoObligado = '${sujetoObligado.cveIdSujetoObligado}';
	
	var tipoPersonaFiscal = '${sujetoObligado.tipoPersonaFiscal}';
	<c:if test="${fisica != null}">
	var rfcSujetoObligado='${fisica.rfc}';
	</c:if><c:if test="${moral != null}">
	var rfcSujetoObligado='${moral.rfc}';
	</c:if>
	var idClasificacion='${objClasificacion.id}';
	var idTramite = '${tramite}';
	var idTipoTramiteCambioDispLey = <%=TipoTramiteEnum.DISPOSICION_DE_LEY.getCodigo()%>;
	var codigo = <%=TipoTramiteEnum.COMPRA_DE_ACTIVOS.getCodigo()%>;
	var mostrarCentroTrabajo = ${codigo} == <%=TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo()%>;
	var mostrarBienes = ${codigo} == <%=TipoTramiteEnum.COMPRA_DE_ACTIVOS.getCodigo()%> || ${codigo} == <%=TipoTramiteEnum.COMODATO.getCodigo()%> || ${codigo} == <%=TipoTramiteEnum.ENAJENACION.getCodigo()%> || ${codigo} == <%=TipoTramiteEnum.ARRENDAMIENTO.getCodigo()%> || ${codigo} == <%=TipoTramiteEnum.FIDEICOMISO_TRASLATIVO.getCodigo()%>;
	var context = "<%=request.getContextPath()%>";
	if(context.indexOf("clasificacion") < 0){
		context += "/clasificacion/";
	}else{
		context += "/";
	}
	<c:if test="${idSolicitud != null}">
	var idSolicitud = ${idSolicitud};
	</c:if>
	<c:if test="${idSolicitud == null}">
	var idSolicitud = 0;
	</c:if>
	var fechaPresentacion = "${sujetoTramite.clasificacion.fecPresentacion}";
	var fechaEfecto = "${sujetoTramite.clasificacion.fecEfecto}";
	var equipoTransporte = '${sujetoTramite.cuentaConTransporte}';
	var registroPatronal = '${sujetoObligado.numeroRegistroPatronal}'+'${sujetoObligado.modalidad.numModalidad}'+'${sujetoObligado.digVerificador}';
	var indReintento = ${indReintento};
	var indRPCInvalido = ${indRPCInvalido};
	var codigoTramite = <%=((TipoTramiteEnum)request.getAttribute("idTramite")).getCodigo()%>
	var tipoContactoTelefonoFijo = <%=TipoMedioContacto.TIPO_TELEFONO_FIJO%>;
	var tipoContactoCorreoElectronico = <%=TipoMedioContacto.TIPO_CORREO_ELECTRONICO%>;
	<c:if test="${sujetoTramite.clasificacion.indProductorCana != null}">
		var isProductorCana=${sujetoTramite.clasificacion.indProductorCana};
	</c:if>
	<c:if test="${sujetoTramite.clasificacion.indProductorCana == null}">
		var isProductorCana = 0;
	</c:if>
	
</script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/clasificacion/clasificacion.js" htmlEscape="true" />"></script>

<div class="page_holder" style="width: 950px ! important; margin: 0px 25px ! important; background: none repeat scroll 0% 0% transparent;">
	<div class="contenedor">
		<div class="row">
			<div class="cell" >
				<div class="row" id="divClasificacion">
					<br/>
					<div style="width:100%; text-align:center">
					<h2>
					<%
					Integer codigoTramite = ((TipoTramiteEnum)request.getAttribute("idTramite")).getCodigo();
					if(codigoTramite == TipoTramiteEnum.ACTIVIDAD_ECONOMICA.getCodigo()){ %>
						Cambio de actividad econ&oacute;mica
					<%}else if(codigoTramite == TipoTramiteEnum.DISPOSICION_DE_LEY.getCodigo()){ %>
							Cambio por disposici&oacute;n de Ley, o del RACERF
					<%}else if(codigoTramite == TipoTramiteEnum.INCORPORACION_DE_ACTIVIDADES.getCodigo()){ %>
							Incorporaci&oacute;n de actividades
					<%}else if(codigoTramite == TipoTramiteEnum.COMPRA_DE_ACTIVOS.getCodigo()){ %>
							Compra de Activos
					<%}else if(codigoTramite == TipoTramiteEnum.COMODATO.getCodigo()){ %>
							Comodato
					<%}else if(codigoTramite == TipoTramiteEnum.ENAJENACION.getCodigo()){ %>
							Enajenaci&oacute;n
					<%}else if(codigoTramite == TipoTramiteEnum.ARRENDAMIENTO.getCodigo()){ %>
							Arrendamiento
					<%}else if(codigoTramite == TipoTramiteEnum.FIDEICOMISO_TRASLATIVO.getCodigo()){ %>
							Fideicomiso traslativo
					<%}else if(codigoTramite == TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo()){ %>
							Centro de Trabajo
					<%}%>
					</h2>
					</div>
					<br/>
					<div id="seccionDatosGenerales">
						<legend class="separadorseccion" style="width:930px">
							<spring:message code="titulo.datos.generales.patron"/>
						</legend>
						<table width="100%" border="0" cellpadding="0" cellspacing="0" style="margin: 0px !important;">
							<tr>
								<td>
									<c:if test="${esOperador == true}">
										<span class="etiqueta"><spring:message code="label.fecha.presentacion"/>:</span>
									</c:if>
									<c:if test="${esOperador == false}">
										<span class="etiqueta"><spring:message code="label.fecha.captura"/>:</span>
									</c:if>
								</td>
								<td style="width:120px">
								<c:if test="${sujetoTramite.clasificacion.fecPresentacion != null}">
									<input type="text" id="fechaPresentacion" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${sujetoTramite.clasificacion.fecPresentacion}"/>" style="width: 100px;" disabled="disabled">
								</c:if>
								<c:if test="${sujetoTramite.clasificacion.fecPresentacion == null}">
									<input type="text" id="fechaPresentacion" value="" style="width: 100px;" disabled="disabled">
								</c:if>
								</td>
								<td colspan="3">
									<span class="etiqueta" style="float: right;"><spring:message code="label.fecha.surte.efecto"/>:</span>
								</td>
								<td style="width:180px">
									<input type="text" id="fechaEfecto" style="width: 70px;" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${sujetoTramite.clasificacion.fecEfecto}"/>" onkeypress="evaluar(this, event)" maxlength="10" onblur="evaluarOnblur(this, event)" onclick="showCalendar()"/><label id="fechaEfectoInvalidaMsg" style="color: red; display: none;">
											fecha inv&aacute;lida
										</label>
									<!--<div id="fechaEfectoInvalidaMsg" style="display: none; width: 60px;">
										<label style="color: red; width: 60px;">
											inv&aacute;
										</label>
									</div>-->
								</td>
							</tr>
							<tr>
								<td>
									<span class="etiqueta"><spring:message code="label.nrp"/>:</span>
								</td>
								<td>
									${sujetoObligado.numeroRegistroPatronal}${sujetoObligado.modalidad.numModalidad}${sujetoObligado.digVerificador}
									<input type="hidden" id="numeroRegistroPatronal" value="${sujetoObligado.numeroRegistroPatronal}"/>
									<input type="hidden" id="idModalidad" value="${sujetoObligado.modalidad.idModalidad}"/>
									<input type="hidden" id="numModalidad" value="${sujetoObligado.modalidad.numModalidad}"/>
									<input type="hidden" id="descripcionModalidad" value="${sujetoObligado.modalidad.descripcion}"/>
									<input type="hidden" id="digVerificador" value="${sujetoObligado.digVerificador}"/>
									<input type="hidden" id="idSubdelegacionOrigen" value="${sujetoObligado.subdelegacion.id}"/>
									<input type="hidden" id="claveSubdelegacionOrigen" value="${sujetoObligado.subdelegacion.clave}"/>
								</td>
								<c:if test="${fisica != null}">
								<td>
									<span class="etiqueta"  style="float: right;"><spring:message code="label.rfc"/>:</span>
								</td>
								<td>
									${fisica.rfc}
								</td>
								<td>
									<span class="etiqueta"><spring:message code="rep.legal.curp"/></span>
								</td>
								<td>
									${fisica.curp}
								</td>
								</c:if>
								<c:if test="${moral != null}">
								<td colspan="3">
									<span class="etiqueta" style="float: right;"><spring:message code="label.rfc"/>:</span>
								</td>
								<td>
									${moral.rfc}
								</td>
								</c:if>
							</tr>
							<tr>
								<td>
									<span class="etiqueta"><spring:message code="label.tipo.persona"/>:</span>
								</td>
								<td colspan="5">
									${sujetoObligado.tipoPersonaFiscal}
								</td>
							</tr>
							<tr>
								<td>
									<c:if test="${fisica != null}">
										<span class="etiqueta"><spring:message code="label.nombre"/>:</span>
									</c:if>
									<c:if test="${moral != null}">
										<span class="etiqueta"><spring:message code="label.razon.social"/>:</span>
									</c:if>
								</td>
								<td colspan="5">
									<c:if test="${fisica != null}">
										${fisica.nombre} ${fisica.primerApellido} ${fisica.segundoApellido}
									</c:if>
									<c:if test="${moral != null}">
										${moral.razonSocial}
									</c:if>
								</td>
							</tr>
							<tr>
								<td colspan="6" style="padding:0px 0px !important;">
								<table width="100%" cellpadding="0;" cellspacing="0" style="margin: 0px !important; border-right: 0px none; border-left: 0px none;">
									<tr class="fielsetgris">
										<td align="center"><span class="etiqueta"><spring:message code="label.rp.clave.fraccion"/></span></td>
										<td align="center"><span class="etiqueta"><spring:message code="label.rp.division"/></span></td>
										<td align="center"><span class="etiqueta"><spring:message code="label.rp.grupo"/></span></td>
										<td align="center"><span class="etiqueta"><spring:message code="label.rp.fraccion"/></span></td>
										<td align="center"><span class="etiqueta"><spring:message code="label.rp.clase"/></span></td>
										<td align="center"><span class="etiqueta"><spring:message code="label.rp.prima.srt"/></span></td>
									</tr>
									<tr> <!-- Cambio -->
										<td align="center">
											<input type="hidden" id="indRegPatClase" value="${objClasificacion.indRegPatClase}"/>
											<c:if test="${ idSolicitud!=null && idSolicitud > 0 }">
												<input type="hidden" id="indPrestaServicioPersonal" value="${sujetoTramite.clasificacion.indPrestaServicioPersonal}"/>
											</c:if>
											<c:if test="${ idSolicitud==null || (idSolicitud!=null && idSolicitud <= 0) }">
												<input type="hidden" id="indPrestaServicioPersonal" value="${objClasificacion.indPrestaServicioPersonal}"/>
											</c:if>
											<span class="dato" id="idFraccionAct">
												${fraccion.grupo.division.numDivision}${fraccion.grupo.numGrupo}${fraccion.numFraccion}
											</span>
										</td>
										<td align="center">
											<span class="dato" id="cvedivisionAct">
												${fraccion.grupo.division.descripcion}
											</span>
										</td>
										<td align="center">
											<span class="dato" id="cvegrupoAct">
												${fraccion.grupo.descripcion}
											</span>
										</td>
										<td align="center">
											<span class="dato" id="cvefraccionAct">
												${fraccion.descripcionDetallada}
											</span>
										</td>
										<td align="center">
											<span class="dato" id="cveclaseAct">
												${fraccion.clase.descripcion}
											</span>
										</td>
										<td align="center">
											<span class="dato" id="cveprimaAct">
												${objClasificacion.primaSRTActual}
											</span>
										</td>
									</tr>
								</table>
								</td>
							</tr>
							<tr>
								<td colspan="6" style="padding:0px 0px !important;">
									<div id="seccionCentroTrabajo">
										<legend class="separadorseccion" style="width: 930px">
											Cambio de domicilio
											<!--  spring:message code="titulo.datos.generales.patron"/ -->
										</legend>
										<div id="wrapperDomicilio" class="ui-widget" align="center" style="width: 75% !important;">
											<div class="ui-state-highlight ui-corner-all" style="margin-top: 12px; padding: 0 .5em;">
												<p>
													<i class="glyphicon glyphicon-info-sign"></i></span>
													<strong>
														Seleccione su nuevo domicilio:
													</strong> 
													<strong>
														<a href="javascript:fnOpenBuscarDomicilio();"> aqu&iacute;</a>
													</strong>
												</p>
											</div>
										</div>
										<table width="100%" cellpadding="0;" cellspacing="0" style="margin: 0px !important; border-right: 0px none; border-left: 0px none;">
											<tr>
												<td class="label_patrones" rowspan="2" style="width: 470px !important;">Calle</td>
												<td class="label_patrones" colspan="2" align="center" style="width: 230px !important;">Exterior</td>
												<td class="label_patrones" colspan="2" align="center">Interior</td>
											</tr>
											<tr>
												<td class="label_patrones" align="center" style="width: 115px !important">N&uacute;mero</td>
												<td class="label_patrones" align="center" >Letra</td>
												<td class="label_patrones" align="center" >N&uacute;mero</td>
												<td class="label_patrones" align="center" style="width: 115px !important">Letra</td>
											</tr>
											<tr>
												<td class="label_patrones_data">
													<input type="text" readonly="readonly" id="cntroTrabajo.vialidadPrimaria.nombre" value="${ sujetoTramite.cntroTrabajo.vialidadPrimaria.nombre }" maxlength="100" size="70" /> 
													<input type="hidden" id="cntroTrabajo.vialidadPrimaria.clave" value="${ sujetoTramite.cntroTrabajo.vialidadPrimaria.clave }" />
													<input type="hidden" id="cntroTrabajo.vialidadPrimaria.tipoVialidad.clave" value="${ sujetoTramite.cntroTrabajo.vialidadPrimaria.tipoVialidad.clave }"/>
												</td>
												<td>
													<input type="text" readonly="readonly" id="cntroTrabajo.numExterior1" maxlength="5" size="5" value="${ sujetoTramite.cntroTrabajo.numExterior1 }" />
												</td>
												<td>
													<input type="text"  readonly="readonly" id="cntroTrabajo.numExteriorAlf" maxlength="3" size="3" value="${ sujetoTramite.cntroTrabajo.numExteriorAlf }" />
												</td>
												<td>
													<input type="text" readonly="readonly" id="cntroTrabajo.numInterior" maxlength="3" size="3" value="${ sujetoTramite.cntroTrabajo.numInterior }" />
												</td>
												<td>
													<input type="text" readonly="readonly" id="cntroTrabajo.numInteriorAlf" maxlength="3" size="3" value="${ sujetoTramite.cntroTrabajo.numInteriorAlf }" />
												</td>
											</tr>
											<tr>
												<td class="label_patrones">
													Entre la calle
												</td>
												<td class="label_patrones" colspan="4">										
													y la calle
												</td>
											</tr>
											
											<tr>			
												<td class="label_patrones_data">
													<input readonly="readonly" id="cntroTrabajo.vialidadReferenciaPrimaria.nombre" maxlength="100" size="70" value="${ sujetoTramite.cntroTrabajo.vialidadReferenciaPrimaria.nombre }" /> 									
													<input type="hidden" id="cntroTrabajo.vialidadReferenciaPrimaria.clave" value="${ sujetoTramite.cntroTrabajo.vialidadReferenciaPrimaria.clave }"/>
													<input type="hidden" id="cntroTrabajo.vialidadReferenciaPrimaria.tipoVialidad.clave" value="${ sujetoTramite.cntroTrabajo.vialidadReferenciaPrimaria.tipoVialidad.clave}" />
												</td>
												<td class="label_patrones_data" colspan="4">
													<input type="text" readonly="readonly" id="cntroTrabajo.vialidadReferenciaSecundaria.nombre" maxlength="100" size="70" value="${ sujetoTramite.cntroTrabajo.vialidadReferenciaSecundaria.nombre }" /> 
													<input type="hidden" id="cntroTrabajo.vialidadReferenciaSecundaria.clave" value="${ sujetoTramite.cntroTrabajo.vialidadReferenciaSecundaria.clave }"/>
													<input type="hidden" id="cntroTrabajo.vialidadReferenciaSecundaria.tipoVialidad.clave"/>
													<input type="hidden" id="cntroTrabajo.vialidadReferenciaPosterior.nombre" maxlength="14" value="${ sujetoTramite.cntroTrabajo.vialidadReferenciaPosterior.nombre }"/> 
													<input type="hidden" id="cntroTrabajo.vialidadReferenciaPosterior.clave" value="${ sujetoTramite.cntroTrabajo.vialidadReferenciaPosterior.clave }"/>
													<input type="hidden" id="cntroTrabajo.vialidadReferenciaPosterior.tipoVialidad.clave" value="${ sujetoTramite.cntroTrabajo.vialidadReferenciaPosterior.tipoVialidad.clave }"/>
												</td>
											</tr>
											<tr>		
												<td class="label_patrones">												
													Colonia(Asentamiento)
												</td>
												<td class="label_patrones"colspan="4">												
													<div>Localidad</div>
												</td>			
												
											</tr>
											<tr>
												<td class="label_patrones_data">
													<input type="text" readonly="readonly" id="cntroTrabajo.asentamiento.nombre" maxlength="50" size="50" value="${ sujetoTramite.cntroTrabajo.asentamiento.nombre }" /> 
													<input type="hidden" id="cntroTrabajo.asentamiento.clave" value="${ sujetoTramite.cntroTrabajo.asentamiento.clave }"/>
												</td>			
												<td class="label_patrones_data" colspan="4">
													<input type="text" readonly="readonly" id="cntroTrabajo.asentamiento.localidad.nombre" value="${ sujetoTramite.cntroTrabajo.asentamiento.localidad.nombre }" maxlength="50" size="50"  /> 
													<input type="hidden" id="cntroTrabajo.asentamiento.localidad.clave" value="${ sujetoTramite.cntroTrabajo.asentamiento.localidad.clave }"/>
												</td>
											</tr>
											<tr>					
												<td class="label_patrones">										
													Municipio o delegaci&oacute;n
												</td>
												<td class="label_patrones" colspan="3">
													Entidad Federativa
												</td>
												<td class="label_patrones">C&oacute;digo Postal</td>
											</tr>
											<tr>
												<td class="label_patrones_data">
													<input type="text" readonly="readonly" id="cntroTrabajo.asentamiento.localidad.municipio.nombre" value="${ sujetoTramite.cntroTrabajo.asentamiento.localidad.municipio.nombre }" size="50"/> 
													<input type="hidden" id="cntroTrabajo.asentamiento.localidad.municipio.clave" value="${ sujetoTramite.cntroTrabajo.asentamiento.localidad.municipio.clave }"/>
												</td>
												<td class="label_patrones_data" colspan="3">
													<input type="text" readonly="readonly" id="cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa.nombre" maxlength="50" size="50" value="${ sujetoTramite.cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa.nombre }" /> 
													<input type="hidden" id="cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa.clave" value="${ sujetoTramite.cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa.clave }"/>
												</td>
												<td>
													<input type="text" readonly="readonly" id="cntroTrabajo.codigoPostal.codigoPostal" maxlength="5" size="8" value="${ sujetoTramite.cntroTrabajo.codigoPostal.codigoPostal }"/>
													<input type="hidden" id="cntroTrabajo.vialidadPrimaria.tipoVialidad.descripcion" value="${ sujetoTramite.cntroTrabajo.vialidadPrimaria.tipoVialidad.descripcion }" />	
												</td>
											</tr>
										</table>
										<table width="100%" cellpadding="0;" cellspacing="0" style="margin: 0px !important; border-right: 0px none; border-left: 0px none;">
											<tr align="center">
												<td colspan="3" align="center" class="label_patrones">Datos de contacto del centro de trabajo</td>
											</tr>
											<tr>					
												<td class="label_patrones" style="width: 200px">										
													Tel&eacute;fono Fijo 1
												</td>
												<td class="label_patrones" style="width: 200px">
													Tel&eacute;fono Fijo 2
												</td>
												<td class="label_patrones">
													Correo Electr&oacute;nico
												</td>
											</tr>
											<tr>
												<td>
													<input type="text" id="ctTelefonoFijo" value="${ ctTelefonoFijo }" maxlength="12"  size="15"
													onkeydown="validarNumeros(event)" onkeypress="validarNumeros(event)" onkeyup="validarNumeros(event)"/> 
												</td>
												<td>
													<input type="text" id="ctTelefonoFijo2" maxlength="12"  size="15" value="${ ctTelefonoFijo2 }" 
													onkeydown="validarNumeros(event)" onkeypress="validarNumeros(event)" onkeyup="validarNumeros(event)"/> 
												</td>
												<td>
													<input type="text" id="ctCorreoElectronico" maxlength="50" size="60" value="${ ctCorreoElectronico }" style="text-transform: none !important;" />
												</td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
						</table>
					</div>
					<br/>
					<div id="seccionClasificacion">
						<legend class="separadorseccion" style="width:930px">
							<spring:message code="label.aviso.seguro.riesgos"/>
						</legend>
						<table width="100%">
						<tr>
							<td width="30%">
								<span class="etiqueta">Especificar su giro o actividad: </span>
							</td>
							<td width="70%">
								<div style="display: table; padding: 30px; width: 100%;" id="tbProceso">
									<div style="display: table-row;" id="giro">
										<div style=" width: 100%; height: 150px;">
											<textarea style="width:97%;"
													  rows="7" 
													  id="giroClasificacion"  
													  onkeydown="validaSize(this, 300, event);" 
													  onkeyup="validaSize(this, 300, event);"
													  onblur="validaSize(this, 300, event);"><c:if test="${sujetoTramite.clasificacion != null}">${sujetoTramite.clasificacion.giro}</c:if></textarea>
										</div>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<span id="labelServicioDePersonal" class="etiqueta">Presta servicio de personal:</span>
							</td>
							<td>
								<form:radiobutton path="sujetoTramite.clasificacion.indPrestaServicioPersonal" onclick="mostrarMensajePSP();" value="1" label="SI" />
								<form:radiobutton path="sujetoTramite.clasificacion.indPrestaServicioPersonal" value="0" label="NO" />
							</td>
						</tr>
						</table>		
						<legend class="separadorseccion" style="width:930px">
							<spring:message code="label.aviso.ley.seguro"/>
						</legend>
						<span class="etiqueta" style="font-size: .75em !important; text-align:justify">
							<spring:message code="label.aviso.conformidad"/>:
						</span>
						<center>
							<div id="wrapperIntsAnterior" class="ui-widget" style="width: 75% !important;">
								<div class="ui-state-highlight ui-corner-all" style="margin-top: 12px; padding: 0 .5em;">
									<p>
										<i class="glyphicon glyphicon-info-sign"></i></span>
										<strong>
											Seleccione su nueva clasificaci&oacute;n:
										</strong> 
											Conozca el nuevo cat&aacute;logo de actividades econ&oacute;micas 
										<strong>
											<a href="javascript:seleccionarClasificacion();"> aqu&iacute;</a>
										</strong>
									</p>
								</div>
							</div>
						</center>
						<table width="100%" cellpadding="0;" cellspacing="0">
							<tr class="fielsetgris">
								<td align="center"><span id="cveFracc" class="etiqueta"><spring:message code="label.rp.clave.fraccion"/></span></td>
								<td align="center"><span id="division" class="etiqueta"><spring:message code="label.rp.division"/></span></td>
								<td align="center"><span id="gruposFr" class="etiqueta"><spring:message code="label.rp.grupo"/></span></td>
								<td align="center"><span id="strFracc" class="etiqueta"><spring:message code="label.rp.fraccion"/></span></td>
								<td align="center"><span id="claseFrc" class="etiqueta"><spring:message code="label.rp.clase"/></span></td>
								<td align="center"><span id="primaSTR" class="etiqueta"><spring:message code="label.rp.prima.srt"/></span></td>
							</tr>
							<tr>
								<td align="center">
									<span class="dato" id="claveDivisionCompleta">
										${sujetoTramite.clasificacion.fraccion.grupo.division.numDivision}${sujetoTramite.clasificacion.fraccion.grupo.numGrupo}${sujetoTramite.clasificacion.fraccion.numFraccion}
									</span>
									<span class="dato" id="claveDivision" style="visibility: hidden;">
										${sujetoTramite.clasificacion.fraccion.grupo.division.numDivision}
									</span>
									<span class="dato" id="claveGrupo" style="visibility: hidden;">
										${sujetoTramite.clasificacion.fraccion.grupo.numGrupo}
									</span>
									<span class="dato" id="claveFraccion" style="visibility: hidden;">
										${sujetoTramite.clasificacion.fraccion.numFraccion}
									</span>
									<input type="hidden" id="fraccion" value="${sujetoTramite.clasificacion.fraccion.id}"/>
								</td>
								<td align="center">
									<span class="dato" id="textDivison">
										${sujetoTramite.clasificacion.fraccion.grupo.division.descripcion}
									</span>
									<input type="hidden" id="divison" value="${sujetoTramite.clasificacion.fraccion.grupo.division.id}"/>
								</td>
								<td align="center">
									<span class="dato" id="textGrupo">
										${sujetoTramite.clasificacion.fraccion.grupo.descripcion}
									</span>
									<input type="hidden" id="grupo" value="${sujetoTramite.clasificacion.fraccion.grupo.id}"/>
								</td>
								<td align="center">
									<span class="dato" id="textFraccion">
										${sujetoTramite.clasificacion.fraccion.descripcionDetallada}
									</span>
								</td>
								<td align="center">
									<input type="hidden" id="claveClase" value="${sujetoTramite.clasificacion.fraccion.clase.clave}">
									<span class="dato" id="textClase">
										${sujetoTramite.clasificacion.fraccion.clase.descripcion}
									</span>
									<input type="hidden" id="clase"/>
								</td>
								<td align="center">
									<span class="dato" id="textPrimaAnt">
										${sujetoTramite.clasificacion.fraccion.primaSRT}
									</span>
									<input type="hidden" id="primaAnt"/>
								</td>
							</tr>
						</table>
					</div>
					<div id="seccionProductosMaeriales">
						<legend class="separadorseccion" style="width:930px">
							<spring:message code="label.datos.actividad.declara"/>
						</legend>
						<table width="100%">
							<tr>
								<td style="vertical-align: top; max-width: 400px;">
									<table id="gridProductosServicios" style="width: 100%; vertical-align: top;" cellpadding="0" cellspacing="0">
									<thead>
									</thead>
									<tbody style="width: 100%;">
									</tbody>
									<tfoot>
									<tr>
										<td></td>
										<td align="center">
											<div class="opciones"><form>
												<div class="opcion">
													<input type="button" onclick="dialogoEdicion('producto', 'Agregar');" class="mboton"
														value="Agregar" style="font-size: .8em !important;">
												</div>
												<div class="opcion">
													<input type="button" onclick="dialogoEdicion('producto', 'Modificar');" class="mboton"
														value="Modificar" style="font-size: .8em !important;">
												</div>
												<div class="opcion">
													<input type="button" onclick="dialogoEdicion('producto', 'Eliminar');" class="mboton"
														value="Eliminar" style="font-size: .8em !important;">
												</div>			

											</form></div>
										</td>
									</tr>
									</tfoot>
									</table>
								</td>
								<td style="vertical-align: top; max-width: 400px;">
									<table id="gridMaeriasMateriales" style="width: 100%; vertical-align: top;" cellpadding="0" cellspacing="0">
									<thead>
									</thead>
									<tbody style="width: 100%;">
									</tbody>
									<tfoot>
									<tr>
										<td></td>
										<td align="center">
											<div class="opciones"><form>
												<div class="opcion">
													<input type="button" onclick="dialogoEdicion('material', 'Agregar');" class="mboton"
														value="Agregar" style="font-size: .8em !important;">
												</div>
												<div class="opcion">
													<input type="button" onclick="dialogoEdicion('material', 'Modificar');" class="mboton"
														value="Modificar" style="font-size: .8em !important;">
												</div>
												<div class="opcion">
													<input type="button" onclick="dialogoEdicion('material', 'Eliminar');" class="mboton"
														value="Eliminar" style="font-size: .8em !important;">
												</div>			
											</form></div>
										</td>
									</tr>
									</tfoot>
									</table>
								</td>
							</tr>
						</table>
					</div>
					<div id="seccionMaquinariaEquipo">
						<legend class="separadorseccion" style="width:930px">
							<spring:message code="label.maquinaria.equipo"/>
						</legend>
						<table id="gridMaquinariaEquipo" style="vertical-align: top; width:100%; max-width: 930px;" cellpadding="0" cellspacing="0">
						<thead>
						</thead>
						<tbody style="width: 100%;">
						</tbody>
						<tfoot>
						<tr>
							<td></td>
							<td align="center" colspan="5">
								<div class="opciones"><form>
									<div class="opcion">
										<input type="button" onclick="dialogoEdicion('equipo', 'Agregar');" class="mboton"
											value="Agregar" style="font-size: .8em !important;">
									</div>
									<div class="opcion">
										<input type="button" onclick="dialogoEdicion('equipo', 'Modificar');" class="mboton"
											value="Modificar" style="font-size: .8em !important;">
									</div>
									<div class="opcion">
										<input type="button" onclick="dialogoEdicion('equipo', 'Eliminar');" class="mboton"
											value="Eliminar" style="font-size: .8em !important;">
									</div>			
								</form></div>
							</td>
						</tr>
						</tfoot>
						</table>
					</div>
					<div id="seccionTransporte">
						<legend class="separadorseccion" style="width:930px">
							<spring:message code="label.equipo.transporte"/>
						</legend>
						<table>
							<tr>
								<td>
									<span class="etiqueta">&iquest;Cuenta con equipo de transporte?</span>
								</td>
								<td>
									<form:radiobutton id="siCuetaConTransporte" path="sujetoTramite.cuentaConTransporte" value="1" label="SI" onchange="toggleCuentaConTransporte(1)"/>
									<form:radiobutton id="noCuetaConTransporte" path="sujetoTramite.cuentaConTransporte" value="0" label="NO" onchange="toggleCuentaConTransporte(0)" />
								</td>
							</tr>
						</table>
						<table id="gridTrasporte" style="vertical-align: top; width:100%; max-width: 930px;" cellpadding="0" cellspacing="0">
						<thead>
						</thead>
						<tbody style="width: 100%;">
						</tbody>
						<tfoot>
						<tr>
							<td></td>
							<td align="center" colspan="5">
								<div class="opciones"><form>
									<div class="opcion">
										<input id="agregarTransporte" type="button" onclick="dialogoEdicion('transporte', 'Agregar');" class="mboton"
											value="Agregar" style="font-size: .8em !important;">
									</div>
									<div class="opcion">
										<input id="modificarTransporte"type="button" onclick="dialogoEdicion('transporte', 'Modificar');" class="mboton"
											value="Modificar" style="font-size: .8em !important;">
									</div>
									<div class="opcion">
										<input id="eliminarTransporte"type="button" onclick="dialogoEdicion('transporte', 'Eliminar');" class="mboton"
											value="Eliminar" style="font-size: .8em !important;">
									</div>			
								</form></div>
							</td>
						</tr>
						</tfoot>
						</table>
					</div>
					<div id="seccionProcesos">
						<form id="formProcesos">
						<legend class="separadorseccion" style="width:930px">
							<spring:message code="label.proceso.trabajo"/>
						</legend>
						<table cellspacing="0" cellpadding="0" class="tablaverde2" style="width: 100%;" id="tbActividades">
							<tbody>
								<tr>
									<td>
										<input type="hidden" id="procesoClave" value="<c:if test='${proceso != null}'>${proceso.clave}</c:if>"/>
										<div style="display: table; padding: 30px; width: 100%;" id="tbProceso">
											<div style="display: table-row;" id="proceso-inicial">
												<h6 style="font-size: 1.0em !important;"><spring:message code="label.procesos.iniciales"/></h6>
												<div style=" width: 100%; height: 150px;">
													<textarea style="width:98%;" rows="7" id="procesoInicial" onkeypress="validaSize(this, 600, event)" onkeyup="validaSize(this, 600, event)"><c:if test="${proceso != null}">${proceso.desInicial}</c:if></textarea>
												</div>
											</div>
											<div style="display: table-row;" id="proceso-intermedio">
												<h6 style="font-size: 1.0em !important;"><spring:message code="label.procesos.intermedios"/></h6>
												<div style="width: 100%; height: 150px;">
													<textarea style="width:98%;" rows="7" id="procesoIntermedio"  onkeypress="validaSize(this, 600, event)" onkeyup="validaSize(this, 600, event)"><c:if test="${proceso != null}">${proceso.desIntermedio}</c:if></textarea>
												</div>
												
											</div>
											<div style="display: table-row;" id="proceso-final">
												<h6 style="font-size: 1.0em !important;"><spring:message code="label.procesos.finales"/></h6>
												<div style=" width: 100%;height: 150px;">
													<textarea style="width:98%;" rows="7" id="procesoFinal"  onkeypress="validaSize(this, 600, event)" onkeyup="validaSize(this, 600, event)"><c:if test="${proceso != null}">${proceso.desFinal}</c:if></textarea>
												</div>			
											</div>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
						</form>
					</div>
					<div id="seccionPersonal">
						<legend class="separadorseccion" style="width:930px">
							<spring:message code="label.personal"/>
						</legend>
						<table id="gridPersonal" style="vertical-align: top; width:100%; max-width: 930px;" cellpadding="0" cellspacing="0">
						<thead>
						</thead>
						<tbody style="width: 100%;">
						</tbody>
						<tfoot>
						<tr>
							<td></td>
							<td align="center" colspan="2">
								<div class="opciones"><form>
									<div class="opcion">
										<input type="button" onclick="dialogoEdicion('personal', 'Agregar');" class="mboton"
											value="Agregar" style="font-size: .8em !important;">
									</div>
									<div class="opcion">
										<input type="button" onclick="dialogoEdicion('personal', 'Modificar');" class="mboton"
											value="Modificar" style="font-size: .8em !important;">
									</div>
									<div class="opcion">
										<input type="button" onclick="dialogoEdicion('personal', 'Eliminar');" class="mboton"
											value="Eliminar" style="font-size: .8em !important;">
									</div>			
								</form></div>
							</td>
						</tr>
						</tfoot>
						</table>
					</div>
					<div id="seccionActividades">
						<legend class="separadorseccion" style="width:930px">
							<spring:message code="label.actividades.complementarias"/>
						</legend>
						<table width="100%">
								<tr>
									<td style="width: 50%;">
										<spring:message code="label.distribuidor.entrega"/>
										<br>
										<table width="100%" style="padding-left:55px;">
											<tbody><tr class="odd">
												<td class="dtJustifyClassColumn">
												<c:if test="${sujetoTramite.cuentaConTransporte == 0}">
													<input type="checkbox" id="indTransportePropioTemp" name="indTransportePropioTemp" disabled="disabled"><spring:message code="label.transporte.propio"/>
												</c:if>
												<c:if test="${sujetoTramite.cuentaConTransporte == 1}">
												<c:if test="${sujetoTramite.clasificacion.indTransportePropio == 1}">
													<input type="checkbox" id="indTransportePropioTemp" name="indTransportePropioTemp" checked="true"><spring:message code="label.transporte.propio"/>
												</c:if>
												<c:if test="${sujetoTramite.clasificacion.indTransportePropio != 1}">
													<input type="checkbox" id="indTransportePropioTemp" name="indTransportePropioTemp" ><spring:message code="label.transporte.propio"/>
												</c:if>
												</c:if>
												</td>
											</tr>
											<tr class="even">
												<td class="dtJustifyClassColumn">
												<c:if test="${sujetoTramite.cuentaConTransporte == 0}">
														<input type="checkbox" id="indTransporteAjenoTemp" name="indTransporteAjenoTemp" disabled="disabled"><spring:message code="label.transporte.ajeno"/>
												</c:if>
												<c:if test="${sujetoTramite.cuentaConTransporte == 1}">
													<c:if test="${sujetoTramite.clasificacion.indTransporteAjeno == 1}">
														<input type="checkbox" id="indTransporteAjenoTemp" name="indTransporteAjenoTemp" checked="true"><spring:message code="label.transporte.ajeno"/>
													</c:if>
													<c:if test="${sujetoTramite.clasificacion.indTransporteAjeno != 1}">
														<input type="checkbox" id="indTransporteAjenoTemp" name="indTransporteAjenoTemp"><spring:message code="label.transporte.ajeno"/>
													</c:if>
												</c:if>
												</td>
											</tr>
											<tr class="odd">
												<td class="dtJustifyClassColumn">
												<c:if test="${sujetoTramite.cuentaConTransporte == 0}">
													<input type="checkbox" id="indNoDistribuyeTemp" name="indNoDistribuyeTemp"  checked="true"><spring:message code="label.no.distribuye"/>
												</c:if>
												<c:if test="${sujetoTramite.cuentaConTransporte == 1}">
													<input type="checkbox" id="indNoDistribuyeTemp" name="indNoDistribuyeTemp"  disabled="disabled"><spring:message code="label.no.distribuye"/>
												</c:if>
												</td>
											</tr>
										</tbody></table>
									</td>
									<td valign="top" style="width: 50%;">
									<c:if test="${sujetoTramite.clasificacion.indServiciosATerceros == 1}">
										<input type="checkbox" id="indServiciosTercerosTemp" name="indServiciosTercerosTemp" checked="true"><spring:message code="label.servicios.terceros"/>
									</c:if>
									<c:if test="${sujetoTramite.clasificacion.indServiciosATerceros != 1}">
										<input type="checkbox" id="indServiciosTercerosTemp" name="indServiciosTercerosTemp"><spring:message code="label.servicios.terceros"/>
									</c:if>
									</td>
								</tr>
							</table>
					</div>
					<div id="seccionBienesInmuebles">
						<legend class="separadorseccion" style="width:930px">
							<spring:message code="label.bienes.inmuebles"/>
						</legend>
						<table id="gridBienes" style="vertical-align: top; width:100%; max-width: 930px;" cellpadding="0" cellspacing="0">
						<thead>
						</thead>
						<tbody style="width: 100%;">
						</tbody>
						<tfoot>
						<tr>
							<td></td>
							<td align="center" colspan="5">
								<div class="opciones"><form>
									<div class="opcion">
										<input type="button" onclick="dialogoEdicion('bienes', 'Agregar');" class="mboton"
											value="Agregar" style="font-size: .8em !important;">
									</div>
									<div class="opcion">
										<input type="button" onclick="dialogoEdicion('bienes', 'Modificar');" class="mboton"
											value="Modificar" style="font-size: .8em !important;">
									</div>
									<div class="opcion">
										<input type="button" onclick="dialogoEdicion('bienes', 'Eliminar');" class="mboton"
											value="Eliminar" style="font-size: .8em !important;">
									</div>			
								</form></div>
							</td>
						</tr>
						</tfoot>
						</table>
						<table cellspacing="0" cellpadding="0" class="tablaverde2" style="width: 100%;" id="tbActividades">
							<tbody>
								<tr>
									<td>
										<div style="display: table; padding: 30px; width: 100%;" id="tbProceso">
											<div style="display: table-row;" id="proceso-intermedio">
												<h6 style="font-size: 1.0em !important;"><spring:message code="label.bienes.uso"/></h6>
												<div style="width: 100%; height: 150px;">
													<textarea style="width:98%;" rows="7" id="afectacionBienes"  
													onkeypress="validaSize(this, 600, event)" onkeyup="validaSize(this, 600, event)">${sujetoTramite.desUsosBienes}</textarea>
												</div>
											</div>
											<div style="display: table-row;" id="proceso-inicial">
												<h6 style="font-size: 1.0em !important;"><spring:message code="label.bienes.afectacion"/></h6>
												<div style=" width: 100%; height: 150px;">
													<textarea style="width:98%;" rows="7" id="usoBienes" 
													onkeypress="validaSize(this, 600, event)" onkeyup="validaSize(this, 300, event)">${sujetoTramite.desAfectacion}</textarea>
												</div>
											</div>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<form>
						<table width="100%">
							<tr>
								<td align="center" width="25%">
									<input type="button" class="mboton" style="width:200px;" onclick="regresar()" value="Regresar">
								</td>
								<td align="center" width="25%">
									<input type="button" id="btnGuardar" class="mboton" style="width:200px;" 
										onclick="construirDialogoConfirmar('Guardar',guardarClasificacion);" value="Guardar">
								</td>
								<c:if test="${typeLogin=='1'}">
									<td align="center" width="25%">
										<input type="button" id="btnFinalizar" class="mboton" style="width:200px;" 
										onclick="prefinalizarClasificacion();" value="Finalizar">
										<!--  onclick="premostrarFirma();" value="Finalizar"> -->
										<!-- input type="button" id="btnFinalizar" class="mboton" style="width:200px;" 
										onclick="construirDialogoConfirmar('Finalizar',finalizarClasificacion);" value="Finalizar"> -->
								</td>
								</c:if>
								<c:if test="${typeLogin=='0'}">
									<td align="center" width="25%">
									<input type="button" id="btnFinalizar" class="mboton" style="width:200px;" 
										onclick="prefinalizarClasificacion();" value="Finalizar">
								</c:if>	
								<td align="center" width="25%">
									<input type="button" id="btnCancelar" class="mboton" style="width:200px;" 
										onclick="construirDialogoConfirmar('Cancelar',cancelarSolicitud);" value="Cancelar Solicitud">
								</td>
							</tr>
						</table>
					</form>
					<div id="dgErrorSinSeleccion" title="Debe seleccionar un elemento">
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

<div id="dialogoGrids">
	<form id="formaDialogo"></form>
</div>
<div id="dialogoConfirmacion">
	<p><span id="textoConfirmacion"></span></p>
</div>
<div id="dialogoMensajes">
	<p><span id="textoMensaje"></span></p>
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
					<form id="formConcluirConFirma" action="${contextpath}/solicitud/1/firmarsolicitud">
						<input type="button" class="mboton" id="btnCmpFirmaDigital" value="Concluir con firma digital!" >
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
									<form id="formConcluirSinFirma" action="${contextpath}/solicitud/1/concluir/sinfirma">
									<input type="button" class="mboton" value="Concluir sin firma digital" onclick="finalizarClasificacion();">
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

<div id="firmaDigitalDlg"></div>
<div id="divBusquedaDomicilio"></div>
<div id="reporteFrame"> </div>
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<!-- 
<form id="formaAcuse" name="formaAcuse" action="${contextpath}/clasificacion/presentarAcuse" method="POST" ></form>
-->
<form id="formaAcuseAfiliacion" name="formaAcuseAfiliacion" action="${contextpath}/afiliacion/procesarInformacionAcuseAfiliacion?origen=COMPROBANTE" method="POST" ></form>
<form id="formaAcuse" name="formaAcuse" action="${contextpath}/clasificacion/presentarAcuse" method="POST" ></form>

