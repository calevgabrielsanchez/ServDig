<%@ include file="../../general/taglibs.jsp"%>


<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.PropietarioMedioContactoEnum"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/common/commonMethods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="../../gestionMediosContacto-web/static/resources/js/delta/mediosContacto/cmpMedioContacto.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/centro/trabajo/centroTrabajo.js" htmlEscape="true" />"></script>

<script type="text/javascript">
	var tramiteCentroTrabajoActivo = ${tramiteCentroTrabajoActivo}; 
	var idTramiteActivo=null;
	var	modInterfaz=1;
	var tpPropietario = null;
	var idPropietario = null;
	idSolicitudActiva = '${idSolicitudCT}' != '' ? '${idSolicitudCT}' : 0;
	tpPropietario = <%=PropietarioMedioContactoEnum.CENTRO_TRABAJO.getCodigo()%>;
	var tipoPersonaFiscal = '${sujetoObligado.tipoPersonaFiscal}';
	var context = "<%=request.getContextPath()%>";
</script>	

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<!-- 
<div class="page_holder">
	<div class="contenedor" style="width: 100% !important">
		<div class="row">
			<div class="cell">
				<div class="row" id="rowDetalleSujetoObligado" style="width: 1000px;" >
					<fieldset style="width: 100% !important; border: none !important;">
					 -->
						<h2><spring:message code="titulo.centro.trabajo" /></h2>			
						<table style="width: 100%; border: none">
							<tr> 		
								<td style="border: none !important;">
									<c:if test="${sujetoObligado.cntroTrabajo.clave != null}">						
											<form:hidden path="sujetoObligado.numeroRegistroPatronal"/>
											<form:hidden path="sujetoObligado.tipoPersonaFiscal"/>
											<form:hidden path="sujetoObligado.cveIdSujetoObligado"/>
										<h3><spring:message code="titulo.informacion.actual" /></h3>
										<div id="infoSolicitud" style=" display: none;" >
											<table style="width: 100%; border: none">						
												<tr>
												<td class="label_patrones" style="width: 150px !important;">
													<div>Folio:</div>
												</td>
												<td class="label_patrones_data">
													<label id="noFolioActual"><c:out value="${folioSolicitud}" /> </label>						
												</td>
												</tr>			
											</table>
										</div>
										<table style="width: 100%; border: none">						
											<tr>
												<td class="label_patrones" style="width: 150px !important;">
													<div>Nombre Comercial</div>
												</td>
												<td class="label_patrones_data">
													<c:out value="${sujetoObligado.nombreComercial}" /> 						
												</td>
											</tr>
											<tr>
												<td class="label_patrones" style="width: 150px !important;">
													<div><spring:message code="label.nrp"/></div>
												</td>
												<td class="label_patrones_data">
												${sujetoObligado.numeroRegistroPatronal}${sujetoObligado.modalidad.numModalidad}${sujetoObligado.digVerificador}
												</td>
											</tr>			
										</table>
										<table style="width: 100%; border: none">						
											<tr>
												<td class="label_patrones" >																												
													<div><spring:message code="label.calle" /></div>
												</td>			
												<td class="label_patrones_data" >														
													<c:out value="${sujetoObligado.cntroTrabajo.vialidadPrimaria.nombre}" /> 						
												</td>
												<td class="label_patrones" >																												
													<div>N&uacute;mero Exterior</div>
												</td>			
												<td class="label_patrones_data">																												
													<c:out value="${sujetoObligado.cntroTrabajo.numExterior1}"/> 							
												</td>																
												<td class="label_patrones">														
													<div>Letra Exterior</div>
												</td>			
												<td class="label_patrones_data">														
													<c:out value="${sujetoObligado.cntroTrabajo.numExteriorAlf}" /> 							
												</td>	
											</tr>
											<tr>
												<td class="label_patrones">															
													<div>N&uacute;mero Interior</div>
												</td>			
												<td class="label_patrones_data">														
													<c:out value="${sujetoObligado.cntroTrabajo.numInterior}" /> 							
												</td>						
												<td class="label_patrones">														
													<div>Letra Interior</div>
												</td>			
												<td class="label_patrones_data">														
													<c:out value="${sujetoObligado.cntroTrabajo.numInteriorAlf}" /> 							
												</td>
												<td class="label_patrones">														
													<div>Referencia Primaria</div>
												</td>			
												<td class="label_patrones_data">														
													<c:out value="${sujetoObligado.cntroTrabajo.vialidadReferenciaPrimaria.nombre}" /> 							
												</td>
											</tr>
											<tr>
												<td class="label_patrones">														
													<div>Referencia Secundaria</div>
												</td>			
												<td class="label_patrones_data">														
													<c:out value="${sujetoObligado.cntroTrabajo.vialidadReferenciaSecundaria.nombre}" /> 							
												</td>
												<td class="label_patrones">														
													<div>Referencia Posterior</div>
												</td>			
												<td class="label_patrones_data">														
													<c:out value="${sujetoObligado.cntroTrabajo.vialidadReferenciaPosterior.nombre}" /> 							
												</td>
												<td class="label_patrones" >														
													<div>Asentamiento</div>
												</td>			
												<td class="label_patrones_data" >														
													<c:out value="${sujetoObligado.cntroTrabajo.asentamiento.nombre}"/> 							
												</td>
											</tr>
											<tr>
												<td class="label_patrones" >														
													<div>Localidad</div>
												</td>			
												<td class="label_patrones_data" >														
													<c:out value="${sujetoObligado.cntroTrabajo.asentamiento.localidad.nombre}"/> 													
												</td>									
												<td class="label_patrones" >														
													<div>Municipio</div>
												</td>			
												<td class="label_patrones_data" >														
													<c:out value="${sujetoObligado.cntroTrabajo.asentamiento.localidad.municipio.nombre}"/>
												</td>										
												<td class="label_patrones" style="width: 140px !important; ">																												
													<div>Entidad Federativa</div>
												</td>			
												<td class="label_patrones_data" >														
													<c:out value="${sujetoObligado.cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa.nombre}"/> 							
												</td>
											</tr>
											<tr>							
												<td class="label_patrones" style="width: 140px !important;">
													<div>C&oacute;digo Postal</div>										
												</td>
												<td class="label_patrones_data" >
													<div><c:out value="${sujetoObligado.cntroTrabajo.codigoPostal.codigoPostal}"/>
													</div>
												</td>																
												<td class="label_patrones" >																												
													<div>Tipo de Vialidad</div>
												</td>			
												<td class="label_patrones_data" >														
													<c:out value="${sujetoObligado.cntroTrabajo.vialidadPrimaria.tipoVialidad.descripcion}"  /> 							
												</td>																		
											</tr>						
										</table>					
										</c:if>					
										<br><br>
										<div id="divTramiteCT"  style="display:none">
										<h3><spring:message code="titulo.tramite" /></h3>	
										<form:form id="centroTrabajoForm" modelAttribute="<%=TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.name()%>">
											<form:hidden path="numeroRegistroPatronal"/>
											<form:hidden path="tipoPersonaFiscal"/>
											<form:hidden path="cveIdSujetoObligado"/>		
											<c:out value ="${numeroRegistroPatronal}"/>
											<c:if test="${sujetoObligado.tipoPersonaFiscal == 'FISICA'}">
												<form:hidden path="fisica.idPersona"/>
												<form:hidden path="fisica.cveFisica"/>
												<form:hidden path="fisica.rfc"/>
												
												<form:hidden path="fisica.nombre"/>
												<form:hidden path="fisica.primerApellido"/>
												<form:hidden path="fisica.segundoApellido"/>
											</c:if>
											<c:if test="${sujetoObligado.tipoPersonaFiscal == 'MORAL'}">
												<form:hidden path="moral.idPersona"/>
												<form:hidden path="moral.rfc"/>
												
												<form:hidden path="moral.razonSocial"/>
												<form:hidden path="moral.tipoSociedad.idTipoSociedad"/>
												<form:hidden path="moral.tipoSociedad.descripcion"/>
											</c:if>
											<table style="width: 100%; border: none">						
											<tr>
											<td class="label_patrones" width=25% height=35>
												<div>Nombre Comercial</div>
											</td>
											<td class="label_patrones_data" width=75% height=35>
												<form:input  path="nombreComercial" maxlength="120" cssStyle="width:70%" /> 
											</td>
											</tr>			
											</table>
											<table style="width: 100%; border: none">
												<tr>
													<td class="label_patrones" width=100 height=35>																												
														<div>Calle</div>
													</td>			
													<td class="label_patrones_data" width=200>
														<div><form:input  readonly="true" path="cntroTrabajo.vialidadPrimaria.nombre" maxlength="14" cssStyle="width:90%" /> 
															 <form:hidden path="cntroTrabajo.vialidadPrimaria.clave"/>
															 <form:hidden path="cntroTrabajo.vialidadPrimaria.tipoVialidad.clave"/>
														</div>					
													</td>						
													<td class="label_patrones" width=100 height=35>
														<div>N&uacute;mero Exterior</div>
													</td>			
													<td class="label_patrones_data" width=200 height=35>
														<div><form:input  readonly="true" path="cntroTrabajo.numExterior1" maxlength="14" cssStyle="width:90%" /> 
														</div>					
													</td>																		
													<td class="label_patrones" width=100 height=35>												
														<div>Letra Exterior</div>
													</td>			
													<td class="label_patrones_data" width=200 height=35>
														<div><form:input  readonly="true" path="cntroTrabajo.numExteriorAlf" maxlength="14" cssStyle="width:90%" /> 
														</div>					
													</td>	
												</tr>
												<tr>
													<td class="label_patrones" width=100>											
														<div>N&uacute;mero Interior</div>
													</td>			
													<td class="label_patrones_data" width=200>
														<div><form:input  readonly="true" path="cntroTrabajo.numInterior" maxlength="14" cssStyle="width:90%" /> 
														</div>					
													</td>								
													<td class="label_patrones" width=100 height=35>												
														<div>Letra Interior</div>
													</td>			
													<td class="label_patrones_data" width=200 height=35>
														<div><form:input  readonly="true" path="cntroTrabajo.numInteriorAlf" maxlength="14" cssStyle="width:90%" /> 
														</div>					
													</td>
													<td class="label_patrones" width=100 height=35>
													<div>Referencia Primaria</div>
													</td>			
													<td class="label_patrones_data" width=200 height=35>
														<div><form:input readonly="true" path="cntroTrabajo.vialidadReferenciaPrimaria.nombre" maxlength="14" cssStyle="width:90%" /> 									
															 <form:hidden path="cntroTrabajo.vialidadReferenciaPrimaria.clave"/>
															 <form:hidden path="cntroTrabajo.vialidadReferenciaPrimaria.tipoVialidad.clave"/>
														</div>
													</td>
												</tr>
												<tr>
													<td class="label_patrones"  width=100 height=35>										
														<div>Referencia Secundaria</div>
													</td>
													<td class="label_patrones_data"  width=200 height=35>
														<div><form:input readonly="true" path="cntroTrabajo.vialidadReferenciaSecundaria.nombre" maxlength="14" cssStyle="width:90%" /> 
															 <form:hidden path="cntroTrabajo.vialidadReferenciaSecundaria.clave"/>
															 <form:hidden path="cntroTrabajo.vialidadReferenciaSecundaria.tipoVialidad.clave"/>
														</div>
													</td>		
													<td class="label_patrones"  width=100 height=35>										
														<div>Referencia Posterior</div>
													</td>
													<td class="label_patrones_data"  width=200 height=35>
														<div><form:input readonly="true" path="cntroTrabajo.vialidadReferenciaPosterior.nombre" maxlength="14" cssStyle="width:90%" /> 
															 <form:hidden path="cntroTrabajo.vialidadReferenciaPosterior.clave"/>
															 <form:hidden path="cntroTrabajo.vialidadReferenciaPosterior.tipoVialidad.clave"/>
														</div>
													</td>										
													<td class="label_patrones" width=100>												
														<div>Asentamiento</div>
													</td>			
													<td class="label_patrones_data" width=200>
														<div><form:input  readonly="true" path="cntroTrabajo.asentamiento.nombre" maxlength="14" cssStyle="width:90%" /> 
															 <form:hidden path="cntroTrabajo.asentamiento.clave"/>
														</div>					
													</td>
												</tr>
												<tr>
													<td class="label_patrones" width=100 height=35>												
														<div>Localidad</div>
													</td>			
													<td class="label_patrones_data" width=200 height=35>
														<div><form:input  readonly="true" path="cntroTrabajo.asentamiento.localidad.nombre" maxlength="14" cssStyle="width:90%" /> 
															 <form:hidden path="cntroTrabajo.asentamiento.localidad.clave"/>
														</div>					
													</td>							
													<td class="label_patrones" width=100>										
														<div>Municipio</div>
													</td>										
													<td class="label_patrones_data" width=200>
														<div><form:input  readonly="true" path="cntroTrabajo.asentamiento.localidad.municipio.nombre" cssStyle="width:90%" /> 
															 <form:hidden path="cntroTrabajo.asentamiento.localidad.municipio.clave"/>
														</div>					
													</td>
													<td class="label_patrones" width=100 height=35>
														<div>Entidad Federativa</div>
													</td>			
													<td class="label_patrones_data" width=200 height=35>
														<div><form:input  readonly="true" path="cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa.nombre" maxlength="14" cssStyle="width:90%" /> 
															 <form:hidden path="cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa.clave"/>
														</div>					
													</td>
												</tr>
												<tr>
													<td  class="label_patrones" width=100 height=35>
														<div>C&oacute;digo Postal</div>										
													</td>
													<td class="label_patrones_data" width=200 height=35>
														<div><form:input readonly="true" path="cntroTrabajo.codigoPostal.codigoPostal" maxlength="14" cssStyle="width:90%"/>
														</div>
													</td>																																					
													<td class="label_patrones" width=100 height=35>																										
														<div>Tipo de Vialidad</div>
													</td>			
													<td class="label_patrones_data" width=200 height=35>														
														<div><form:input  readonly="true" path="cntroTrabajo.vialidadPrimaria.tipoVialidad.descripcion" maxlength="14" cssStyle="width:90%" /> 
														</div>					
													</td>																			
												</tr>							
											</table>		
											<br>
											<br>
											<div id="errorFormMCT"></div>
											<center>
												<!--<input type="button" class="mboton" style="width:200px;" onclick="javascript:buscarCentroTrabajo();" id="buttonModificarTramite" value="Modificar Domicilio"/>	-->
												<!--<input type="button" class="mboton" style="width:200px;" onclick="javascript:cancelarBuscarCentroTrabajo();" id="buttonCancelarTramite" value="Cancelar"/>	-->
												<!--input type="button" id="btnGuardarCentroTrabajo" class="mboton" style="width:200px;" onclick="guardarCT()" value="Guardar"-->			
												<input type="button" id="btnCancelarCentroTrabajo" class="mboton" style="width:200px;" onclick="cleanFormCT()" value="Cancelar">			
											</center>
										</form:form>
										</div>
								<c:if test="${sujetoObligado.cntroTrabajo.clave == null}">
									<strong>
										<spring:message code="err.centro.trabajo.inexistente" />
									</strong>
								</c:if>					
								</td>
							</tr>
							<tr>
								<td style="border: none !important;">
									
										<form>
											<input type="button" class="mboton" onclick="javascript:fnOpenBuscarDomicilio();" id="buttonModificarCT" value="Modificar Centro de Trabajo"/>						
											<input type="button" class="mboton" onclick="javascript:fnModificarDatosContacto();" id="buttonModificarCD" value="Modificar Datos de Contacto"/>						
										</form>
									
								</td>
							</tr>
							<tr>
								<td style="border: none !important;">								
									<strong><spring:message code="titulo.datos.contacto" /></strong>
									<div id="mediosContactoContenedor">
									</div>	
									<div id="buttonsMediosContacto">
										<form>
											<input type="button" id="btnGuardarDatosContacto" class="mboton" style="width:200px;" onclick="guardarDC()" value="Guardar DatosContacto">
											<input type="button" id="btnCancelarDatosContacto" class="mboton" style="width:200px;" onclick="cancelarDC()" value="Cancelar">
										</form>
									</div>
								</td>
							</tr>
						</table>	
						<form>
							<input type="button" id="btnGuardarCentroTrabajo" class="mboton" style="width:160px;" onclick="guardarCT()" value="Guardar">
							<input type="button" class="mboton" onclick="javascript:validarSubdOrigenSubdDestino();" id="btnConcluirSol" value="Concluir Solicitud"/>
							<input type="button" class="mboton" onclick="javascript:fnCancelarSolicitud();" id="btnCancelarSol" value="Cancelar Solicitud"/>
							<c:if test="${isOperadorIMSS}">
								<input type="button" id="btnBusquedaRFC" class="mboton" name="RegresarBusqueda" 
								onclick="navegarABusquedaRFC()" style="width:200px; " value="Ir a B&uacute;squeda por RFC" />
							</c:if>
							<input type="button" id="btnRegresarDetalleRFC" class="mboton" name="RegresarRFC" onclick="navegarToDetalleRFC()"
								 value="Ir al Detalle de RFC"/>
						</form>
						
						
						<div style="display:none" id="dgUbicarDomicilio" title="Cambio de Domicilio">
							<p>
								<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span> 
								<strong><a href="javascript:fnOpenBuscarDomicilio();">Cargar datos de Domicilio</a></strong>						
							</p>		
							<jsp:include page="modificarCentroTrabajo.jsp"/>
							<div id="domicilioLocaliza"></div>
							<br>
							<div id="showErrorForm"/></div>
						</div>
						<!-- 			
					</fieldset>
				</div>
			</div>
		</div>
	</div>
</div> -->

<form:form modelAttribute="sujetoObligado"  action="" id="busquedaRFCForm">
</form:form>

<form:form id="detalleRFCForm" modelAttribute="sujetoObligado" action="${contextpath}/afiliacion/visualizarDetalleRFC">
	<form:hidden path="fisica.rfc"/>
	<form:hidden path="moral.rfc"/>
	<form:hidden path="tipoPersonaFiscal"/>
</form:form>

<div id="dialogoConfirmacion">
	<p><span id="textoConfirmacion"></span></p>	
</div> 

<div id="dialogoMensajes">
	<p><span id="textoMensaje"></span></p>
</div>