<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>
<%@taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<jsp:include page="../general/llenaParentescos.jsp"></jsp:include>
<jsp:include page="../general/llenaEstadoCivil.jsp"></jsp:include>

<script type="text/javascript" src="<spring:url value="/static/resources/common/setDomicilioCommon.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/common/combosUmfMedicoConsultorio.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/common/datosUmf.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="/gestionDomicilios-web/static/resources/js/delta/domicilios/DomicilioUmf.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/correccion/asignacionDomicilioDerechohabiente.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>

<input type="hidden" id="patronImss" value="${!patronIMSS ?0:1}" />
<input type="hidden" id="validacion" value="${validacion}" />
<input type="hidden" id="saltarCargaDocumento" value="1" />
<input type="hidden" id="idUmfPersona" value="${idUmfPersona}" />
<input type="hidden" id="idUmfUsuario" value="${usuarioObj.usuarioFuncionario.unidadMedicaFamiliar.idUMF}" />
<input type="hidden" id="umfSeleccionada" value=""/>
<input type="hidden" id="turnoSeleccionado" value=""/>
<input type="hidden" id="consultorioSeleccionado" value=""/>
<input type="hidden" id="tieneConcubinaPadres" value= "${tieneConcubinaPadres}"/>
<input type="hidden" id="domicilioOtro" value= "${domicilioOtro}"/>
<div class="form-comment">
	<br> <br>
	<c:choose>
		<c:when test="${empty errores}">
			<h4 align="center">ASIGNACI&Oacute;N DE DOMICILIO DE DERECHOHABIENTE</h4>
			<jsp:include page="/WEB-INF/views/prorrogas/grupoFamiliar.jsp"></jsp:include>
			<br>
			<div id="mensajeConfirmacion"></div>

			<c:if test="${domicilioOtro eq 1}">
			<div class="ui-widget" id="alertPadres1">
				<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
					<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
						El asegurado / pensionado no cuenta con domicilio, se usar&aacute; el que tiene asociado la concubina
						o los padres, si desea modificarlo de clic en el bot&oacute;n "Ubicar nuevo Domicilio".
					</p>
				</div>
			</div>
			</c:if>
			<!-- Apartado de datos del integrante -->
			<br>
			<form:form id="registro" method="POST"
							commandName="derechohabiente">
							
			<table style="width: 100%">
				<tr>
					<td style="width: 50%">
							<fieldset style="width: 95%; height: 670px">
								<legend>
									<strong>Datos del domicilio anterior</strong>
								</legend>
								<table id="domicilioAnterior" class="page_holder_no_height"
									style="width: 95%">
									<tr>
										<td><spring:message code="label.asentamiento" /> :</td>
										<td><input type="text"
											value="${datosActuales.domicilio.asentamiento.nombre}"
											disabled="disabled" style="width: 87%" /></td>
									</tr>
									<tr>
										<td>Codigo Postal:</td>
										<td><input type="text"
											value="${datosActuales.domicilio.codigoPostal.codigoPostal}"
											style="width: 40%" disabled="disabled" /></td>
									</tr>
									<tr>
										<td><spring:message code="label.tipoVialidad" /> :</td>
										<td><input type="text"
											value="${datosActuales.domicilio.vialidadPrimaria.tipoVialidad.descripcion}"
											disabled="disabled" style="width: 87%" /></td>
									</tr>
									<tr>
										<td><spring:message code="label.nombreV" /> :</td>
										<td><input type="text"
											value="${datosActuales.domicilio.vialidadPrimaria.nombre}"
											disabled="disabled" style="width: 87%" /></td>
									</tr>
									<tr>
										<td><spring:message code="label.numeroExt" /> :</td>
										<td><input type="text"
											value="${datosActuales.domicilio.numExterior1}"
											style="width: 40%" disabled="disabled" /></td>
									</tr>
									<tr>
										<td><spring:message code="label.numeroLExt" /> :</td>
										<td><input type="text"
											value="${datosActuales.domicilio.numExteriorAlf}"
											style="width: 40%" disabled="disabled" /></td>
									</tr>
									<tr>
										<td><spring:message code="label.numeroInt" /> :</td>
										<td><input type="text"
											value="${datosActuales.domicilio.numInterior}"
											style="width: 40%" disabled="disabled" /></td>
									</tr>
									<tr>
										<td><spring:message code="label.numeroLInt" /> :</td>
										<td><input type="text"
											value="${datosActuales.domicilio.numInteriorAlf}"
											style="width: 40%" disabled="disabled" /></td>
									</tr>
									<tr>
										<td><spring:message
												code="tramite.detalle.numLetraExtNoOficial" /> :</td>
										<td><input type="text"
											value="${datosActuales.domicilio.numExterior2}"
											style="width: 40%" disabled="disabled" /></td>
									</tr>
									<tr>
										<td><strong><spring:message code="label.ref1" /></strong>
										</td>
									</tr>
									<tr>
										<td><spring:message code="label.tipoVialidad" /> :</td>
										<td><input type="text"
											value="${datosActuales.domicilio.vialidadReferenciaPrimaria.tipoVialidad.descripcion}"
											disabled="disabled" style="width: 87%" /></td>
									</tr>
									<tr>
										<td><spring:message code="label.nombreV" /> :</td>
										<td><input type="text"
											value="${datosActuales.domicilio.vialidadReferenciaPrimaria.nombre}"
											disabled="disabled" style="width: 87%" /></td>
									</tr>

									<tr>
										<td><strong><spring:message code="label.ref2" /></strong>
										</td>
									</tr>
									<tr>
										<td><spring:message code="label.tipoVialidad" /> :</td>
										<td><input type="text"
											value="${datosActuales.domicilio.vialidadReferenciaSecundaria.tipoVialidad.descripcion}"
											disabled="disabled" style="width: 87%" /></td>
									</tr>
									<tr>
										<td><spring:message code="label.nombreV" /> :</td>
										<td><input type="text"
											value="${datosActuales.domicilio.vialidadReferenciaSecundaria.nombre}"
											disabled="disabled" style="width: 87%" /></td>
									</tr>
									<tr>
										<td><strong><spring:message code="label.ref3" /></strong>
										</td>
									</tr>
									<tr>
										<td><spring:message code="label.tipoVialidad" /> :</td>
										<td><input type="text"
											value="${datosActuales.domicilio.vialidadReferenciaPosterior.tipoVialidad.descripcion}"
											disabled="disabled" style="width: 87%" /></td>
									</tr>
									<tr>
										<td><spring:message code="label.nombreV" /> :</td>
										<td><input type="text"
											value="${datosActuales.domicilio.vialidadReferenciaPosterior.nombre}"
											disabled="disabled" style="width: 87%" /></td>
									</tr>
									<tr>
										<td></td>
										<td></td>
									</tr>
								</table>
							</fieldset>
					</td>
					<td style="width: 50%;">
							<form:hidden
								path="domicilio.asentamiento.localidad.municipio.entidadFederativa.clave" />
							<form:hidden
								path="domicilio.asentamiento.localidad.municipio.clave" />
							<form:hidden path="domicilio.asentamiento.localidad.clave" />
							<form:hidden path="fechaCambioMedico"/>
							<form:hidden path="idAsignacionNss" />
							<form:hidden path="indSeleccionMedico"/>
							<form:hidden path="tipoTramite.idTipoTramite" />
							<form:hidden path="domicilio.clave" />
							<form:hidden path="idPersona" />
							<form:hidden path="parentesco.idParentesco" style="width: 45px" />


							<fieldset style="width: 95%; height: 670px">
								<legend>
									<strong>Datos del nuevo domicilio</strong>
								</legend>
								<table id="domicilioNuevo" class="page_holder_no_height"
									style="width: 95%">
									<tr>
										<td><spring:message code="label.asentamiento" /> :</td>
										<td><form:hidden path="domicilio.asentamiento.clave" />
											<form:input disabled="disabled" readonly="true"
												path="domicilio.asentamiento.nombre" style="width:87%" /></td>
									</tr>
									<tr>
										<td><spring:message code="label.codigoPos" />:</td>
										<td><form:input readonly="true"
												path="domicilio.codigoPostal.codigoPostal" style="width:40%" />
										</td>
									</tr>
									<tr>
										<td><spring:message code="label.tipoVialidad" /> :</td>
										<td><form:hidden
												path="domicilio.vialidadPrimaria.tipoVialidad.clave" /> <form:input readonly="true"
												path="domicilio.vialidadPrimaria.tipoVialidad.descripcion"
												style="width:87%" /></td>
									</tr>
									<tr>
										<td><spring:message code="label.nombreV" /> :</td>
										<td><form:hidden path="domicilio.vialidadPrimaria.clave" />
											<form:input path="domicilio.vialidadPrimaria.nombre" readonly="true"
												style="width:87%" /></td>
									</tr>
									<tr>
										<td><spring:message code="label.numeroExt" /><br></td>
										<td><form:input path="domicilio.numExterior1" readonly="true"
												style="width:40%" /></td>
									</tr>
									<tr>
										<td><spring:message code="label.numeroLExt" /> :</td>
										<td><form:input path="domicilio.numExteriorAlf" readonly="true"
												style="width:40%" /></td>
									</tr>
									<tr>
										<td><spring:message code="label.numeroInt" /> :</td>
										<td><form:input readonly="true" path="domicilio.numInterior"
												style="width:40%" /></td>
									</tr>
									<tr>
										<td><spring:message code="label.numeroLInt" /> :</td>
										<td><form:input readonly="true" path="domicilio.numInteriorAlf"
												style="width:40%" /></td>
									</tr>
									<tr>
										<td><spring:message
												code="tramite.detalle.numLetraExtNoOficial" /> :</td>
										<td><form:input  readonly="true" path="domicilio.numExterior2"
												style="width:40%" /></td>
									</tr>
									<tr>
										<td><strong><spring:message code="label.ref1" /></strong>
										</td>
									</tr>

									<tr>
										<td><spring:message code="label.tipoVialidad" /> :</td>
										<td><form:hidden
												path="domicilio.vialidadReferenciaPrimaria.tipoVialidad.clave" />
											<form:input readonly="true"
												path="domicilio.vialidadReferenciaPrimaria.tipoVialidad.descripcion"
												style="width:87%" /></td>
									</tr>
									<tr>
										<td><spring:message code="label.nombreV" /> :</td>
										<td><form:hidden
												path="domicilio.vialidadReferenciaPrimaria.clave" /> <form:input  readonly="true"
												path="domicilio.vialidadReferenciaPrimaria.nombre"
												style="width:87%" /></td>
									</tr>

									<tr>
										<td><strong><spring:message code="label.ref2" /></strong>
										</td>
									</tr>
									<tr>
										<td><spring:message code="label.tipoVialidad" /> :</td>
										<td><form:hidden
												path="domicilio.vialidadReferenciaSecundaria.tipoVialidad.clave" />
											<form:input readonly="true"
												path="domicilio.vialidadReferenciaSecundaria.tipoVialidad.descripcion"
												style="width:87%" /></td>
									</tr>
									<tr>
										<td><spring:message code="label.nombreV" /> :</td>
										<td><form:hidden
												path="domicilio.vialidadReferenciaSecundaria.clave" /> <form:input readonly="true"
												path="domicilio.vialidadReferenciaSecundaria.nombre"
												style="width:87%" /></td>
									</tr>
									<tr>
										<td><strong><spring:message code="label.ref3" /></strong>
										</td>
									</tr>

									<tr>
										<td><spring:message code="label.tipoVialidad" /> :</td>
										<td><form:hidden
												path="domicilio.vialidadReferenciaPosterior.tipoVialidad.clave" />
											<form:input readonly="true"
												path="domicilio.vialidadReferenciaPosterior.tipoVialidad.descripcion"
												style="width:87%" /></td>
									</tr>
									<tr>
										<td><spring:message code="label.nombreV" /> :</td>
										<td><form:hidden
												path="domicilio.vialidadReferenciaPosterior.clave" /> <form:input readonly="true"
												path="domicilio.vialidadReferenciaPosterior.nombre"
												style="width:87%" /></td>

										<form:hidden path="domicilio.calle" />
										<form:hidden path="domicilio.tipoBusquedaVialidad" />

										<!-- Atributos de domicilio carretera -->
										<form:hidden
											path="domicilio.domicilioCarretera.terminoGeneral.descripcion" />
										<form:hidden
											path="domicilio.domicilioCarretera.terminoGeneral.clave" />
										<form:hidden
											path="domicilio.domicilioCarretera.derechoTransito.descripcion" />
										<form:hidden
											path="domicilio.domicilioCarretera.derechoTransito.clave" />
										<form:hidden path="domicilio.domicilioCarretera.origen" />
										<form:hidden path="domicilio.domicilioCarretera.destino" />
										<form:hidden
											path="domicilio.domicilioCarretera.administracion.descripcion" />
										<form:hidden
											path="domicilio.domicilioCarretera.administracion.clave" />
										<form:hidden path="domicilio.domicilioCarretera.cadenamiento" />
										<form:hidden
											path="domicilio.domicilioCarretera.codigoCarretera" />

										<!-- Atrbutos de domicilio camino -->
										<form:hidden
											path="domicilio.domicilioCamino.terminoGeneral.descripcion" />
										<form:hidden
											path="domicilio.domicilioCamino.terminoGeneral.clave" />
										<form:hidden
											path="domicilio.domicilioCamino.margen.descripcion" />
										<form:hidden path="domicilio.domicilioCamino.margen.clave" />
										<form:hidden path="domicilio.domicilioCamino.origen" />
										<form:hidden path="domicilio.domicilioCamino.destino" />
										<form:hidden path="domicilio.domicilioCamino.cadenamiento" />

										<form:hidden path="calificacion.idCalificacion" />
									</tr>
									<tr>
										<td colspan="2" align="center"><br> <input
											id="ubicar" type="button" value="Ubicar nuevo domicilio"
											class="mboton" /></td>
									</tr>
								</table>
							</fieldset>
							
			</td>
				</tr>
			</table>
			
				
				<div class="ui-widget" id="alertPadres2" <c:if test="${domicilioOtro ne 1}"> style="display:none"</c:if>>
					<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
						<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
							<span id="mensajeUmfIntegrante">
								<c:if test="${domicilioOtro eq 1}">
								El asegurado / pensionado no cuenta con domicilio, se usar&aacute; el que tiene asociado la concubina
								o los padres, si desea modificarlo de clic en el bot&oacute;n "Ubicar nuevo Domicilio".
								</c:if>
							</span>
						</p>
					</div>
				</div>
			
			<div id="datosMedico" style="display: none;">
			<fieldset id="medicoTurno">
					<legend>
						<strong><spring:message code="titulo.datosUMF" />
							${derechohabiente.nombre} ${derechohabiente.primerApellido}
							${derechohabiente.segundoApellido}</strong>
					</legend>
					<table>
						<tr >
							<td><spring:message code="label.umf" />:</td>
							<td>
							    <select id="medicoEnTurno.unidadMedicaFamiliar.idUMF" name="medicoEnTurno.unidadMedicaFamiliar.idUMF" >
								</select>
								<div id="errorUmf" ></div>		
							</td>
							<td><spring:message code="label.umf.delegacion" /> :</td>
							<td>
								<input type="hidden" id="medicoEnTurno.idMedicoContultorioTurno" name="medicoEnTurno.idMedicoContultorioTurno" value="">
								<input type="hidden"
								name="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.id"
								id="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.id"
								value="" />	
								<input type="text" name="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion"
								id="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion" value="" disabled="disabled" style="width: 160px"></td>
							<td><spring:message code="label.umf.subdelegacion" />:</td>
							<td><input type="hidden" name="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.id"
								id="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.id" value=""> 
								
								<input type="text" name="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.descripcion"
								id="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.descripcion" disabled="disabled" value="" style="width: 160px"></td>
						</tr>
						<tr>
							<td colspan="6"><br>
							</td>
						</tr>
						<tr>
							<td><spring:message code="label.umfTurno" />:</td>
							<td>
							<select id="medicoEnTurno.turno.idTurno"
									name="medicoEnTurno.turno.idTurno">
										<option value="">-- Por favor seleccione --</option>
								</select>
								<div id="errorTurno" >
								</div>	
							</td>
							<td><spring:message code="label.consultorio" />:</td>
							<td>
								<select id="medicoEnTurno.consultorio.idConsultorio"
									name="medicoEnTurno.consultorio.idConsultorio">
										<option value="">-- Por favor seleccione --</option>
								</select>
								<div id="errorConsultorio" >
								</div></td>
							
							<td colspan="1"></td>
						</tr>
						<tr>
							<td colspan="6"><br>
							</td>
						</tr>
						<tr>
							<td>Matricula medico :</td>
							<td><input type="hidden"
								name="medicoEnTurno.medicoFamiliar.idMedicoFamiliar"
								id="medicoEnTurno.medicoFamiliar.idMedicoFamiliar" value="">
								<input type="text"
								name="medicoEnTurno.medicoFamiliar.noMatricula"
								id="medicoEnTurno.medicoFamiliar.noMatricula" value=""
								style="width: 160px" disabled="disabled"></td>
							<td><spring:message code="label.medicoFamiliar" />:</td>
							<td><input type="text"
								name="medicoEnTurno.medicoFamiliar.nombre"
								id="medicoEnTurno.medicoFamiliar.nombre" value=""
								style="width: 160px" disabled="disabled"></td>
							<td>Especialidad :</td>
							<td><input type="hidden"
								name="medicoEnTurno.medicoEspecialidad.idMedicoEspacialidad"
								id="medicoEnTurno.medicoEspecialidad.idMedicoEspacialidad"
								value=""> <input type="text"
								name="medicoEnTurno.medicoEspecialidad.descripcion"
								id="medicoEnTurno.medicoEspecialidad.descripcion" value=""
								style="width: 160px" disabled="disabled"></td>
						</tr>
						<tr>
							<td><spring:message code="label.observaciones" />:  </td>
							<td colspan="5" >
								<form:textarea path="observacion" cols="2" style="width: 453px; height: 45px"></form:textarea>
							</td>
						</tr>	
					</table>
				</fieldset>
			</div>
				
			</form:form>
			
			<br />
			<br />
			<div id="ubicarDomi"></div>
			<div align="center">
				<form>
					<table>
						<tr>
							<td align="center">
								<div id="botones">
									<input id="aceptar" type="button" value="Aceptar"
										class="mboton" /> 
										
										<input id="regresarGrupoFamiliar"
										type="button" value="<spring:message code="button.regresar"/>"
										class="mboton" />
								</div>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</c:when>
		<c:otherwise>
			<div class="ui-widget-content ui-corner-all">
				<div class="ui-state-error ui-corner-all" align="center">
					<div class="ui-icon ui-icon-alert"></div>
					<p class="ui-helper-reset ui-state-error-text">
						<spring:message code="${errores}" />
					</p>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</div>

<div id="dialogICA"></div>