<%@ include file="../../general/taglibs.jsp"%>

<style>
	td.nrpRiss{
		text-align: center;
		font-weight: bold;
	}
	
	.upperCase {
		text-transform: uppercase;
	}
</style>

<script src="<spring:url value="/static/resources/js/delta/riss/confirmarDatos.js" htmlEscape="true" />"></script>
<script src="<spring:url value="/static/resources/js/delta/riss/resultadoValidacion.js" htmlEscape="true" />"></script>
		
<div class="contenedor">
	<div>
	
		<div id="info-paso">
			<h3 style="font-size: 1.8em !important">Paso 2: Informaci&oacute;n b&aacute;sica del beneficiario(s).</h3>			
		</div>
			
	<c:choose>	
		<c:when test="${not empty msgError}">			
			<div class="alert alert-danger alert-block">${msgError}</div>
				
			<div class="text-right" style="margin-top: 10px;">
				<c:choose>
					<c:when test="${not empty solicitudEnProceso && solicitudEnProceso eq true }">
						<c:choose>
							<c:when test="${not empty solicitudRegistrada && solicitudRegistrada eq true }">						
								<button type="button" id="btnCancelarSolic" class="btn btn-default">CANCELAR
									SOLICITUD</button>													
								<input type="hidden" id="idSolic" value="${idSolicitudRegistrada}" />
							</c:when>
							<c:otherwise>
								<form action="${contextpath}/altaPublica/riss/iniciar" method="get">
									<button type="submit" class="btn btn-primary">ACEPTAR</button>
								</form>
							</c:otherwise>	
						</c:choose>
					</c:when>
					<c:otherwise>
						<form action="${contextpath}/altaPublica/riss/iniciar" method="get">
							<button type="submit" class="btn btn-primary">ACEPTAR</button>
						</form>
					</c:otherwise>	
				</c:choose>
			</div>
		</c:when>
		<c:otherwise>
			<div class="alert alert-info alert-block">
				A continuaci&oacute;n se
				<c:choose>
					<c:when test="${not empty rissRfc && rissRfc eq true }">
						muestran los patrones que fueron encontrados relacionados 
						al RFC <strong>${datosEntradaRiss.rfc }</strong>.
					</c:when>
					<c:otherwise>
						muestra la informaci&oacute;n b&aacute;sica encontrada relacionada al  
						NSS <strong>${datosEntradaRiss.nss }</strong>. 
					</c:otherwise>
				</c:choose>
				Si los datos son correctos de clic en el bot&oacute;n
				&quot;CONTINUAR&quot; para comenzar las validaciones correspondientes.
			</div>
			<c:if test="${not empty rissNss && rissNss eq true && empty fisica.rfc }">
				<div class="alert alert-warning alert-block">
					La persona localizada no cuenta con RFC, por lo cual es necesario capturarlo.
				</div>
			</c:if>
			<c:if test="${not empty datosEntradaRiss.errorFormGeneral }">
				<div class="alert alert-danger alert-block">
					${datosEntradaRiss.errorFormGeneral }
				</div>
			</c:if>

			<fieldset>
				<legend>
					Informaci&oacute;n localizada
				</legend>
				<c:choose>
					<c:when test="${not empty rissRfc && rissRfc eq true }">
						<c:choose>
							<c:when test="${not empty msgRPSPendientes}">
								<div class="alert alert-success"><strong>${msgRPSPendientes}</strong></div>
							</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
					
						<form:form modelAttribute="datosEntradaRiss" id="validarRissForm"
							action="${datosEntradaRiss.accion}">
							<form:hidden path="rfc"/>
							
							<table id="tblPatrones" style="width: 100%;"
							class="table table-striped table-bordered" cellpadding="0"
							cellspacing="0" border="0">
								<thead>
									<tr>
										<th>NRP</th>
										<th>Nombre Comercial</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${datosEntradaRiss.patrones}" var="so" varStatus="status">
										<tr>
											<td>${so.numeroRegistroPatronal}${so.modalidad.numModalidad}${so.digVerificador}</td>
											<td>
												<c:choose>
													<c:when test="${empty so.nombreComercial}">
														<span style="font-style: italic; color: #D3D3D3;">SIN
															NOMBRE COMERCIAL</span>
													</c:when>
													<c:otherwise>
														${so.nombreComercial}
													</c:otherwise>
												</c:choose>
												<form:hidden path="patrones[${status.index}].cveIdSujetoObligado"/>
												<form:hidden path="patrones[${status.index}].numeroRegistroPatronal"/>
												<form:hidden path="patrones[${status.index}].modalidad.numModalidad"/>
												<form:hidden path="patrones[${status.index}].digVerificador"/>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</form:form>
					</c:when>
					<c:otherwise>					
						<form:form modelAttribute="datosEntradaRiss" id="validarRissForm"
							action="${datosEntradaRiss.accion}" class="form-horizontal">

							<div class="form-group">
								<label class="col-sm-3 control-label">Nombre(s)</label>
								<div class="col-sm-5">
									<input type="text" disabled="disabled" readonly="readonly"
										class="form-control"
										value="${fisica.nombre }" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">Primer Apellido</label>
								<div class="col-sm-5">
									<input type="text" disabled="disabled" readonly="readonly"
										class="form-control"
										value="${fisica.primerApellido }" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">Segundo Apellido</label>
								<div class="col-sm-5">
									<input type="text" disabled="disabled" readonly="readonly"
										class="form-control"
										value="${fisica.segundoApellido }" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">CURP</label>
								<div class="col-sm-5">
									<input type="text" disabled="disabled" readonly="readonly"
										class="form-control"
										value="${fisica.curp }" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">
									<c:if test="${empty fisica.rfc }">
										<span class="required">*</span>
									</c:if>
									RFC
								</label>
								<div class="col-sm-5">
									<c:choose>
										<c:when test="${empty fisica.rfc }">
											<form:input path="rfc" id="rfc" cssClass="alfanumericoEstricto upperCase form-control" maxlength="13" />
											<form:errors cssClass="error customError" path="rfc" />
												<input type="hidden" name="rfcObligatorio"
													id="rfcObligatorio" value="true" />
												<div id="controlError" ></div>
										</c:when>
										<c:otherwise>
											<input type="text" disabled="disabled" readonly="readonly"
												value="${fisica.rfc }" class="form-control" />
											<input type="hidden" name="rfc" id="rfcFisica" value="${fisica.rfc }"/>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">Fecha de nacimiento</label>
								<div class="col-sm-5">
									<input type="text" disabled="disabled" readonly="readonly"
										class="form-control"
										value="${fisica.fechaNacimientoFormateada }" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">Lugar de nacimiento</label>
								<div class="col-sm-5">
									<input type="text" disabled="disabled" readonly="readonly"
										class="form-control"
										value="${fisica.lugarNacimiento.nombre }" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">Sexo</label>
								<div class="col-sm-5">
									<input type="text" disabled="disabled" readonly="readonly"
										
										value="${fisica.sexo.descripcion }" />
								</div>
							</div>
							
							<form:hidden path="nss"/>
							<form:hidden path="accion"/>
							<form:hidden path="opcRISS"/>
							<input type="hidden" name="iPer" value="${fisica.idPersona }" />
							
						</form:form>
					</c:otherwise>
				</c:choose>
			</fieldset>

			<div style="float: right; margin-top: 10px;">
				<button type="button" id="bntCancelar" class="btn btn-default">REGRESAR</button>
				<c:choose>
					<c:when test="${not empty rissRfc && rissRfc eq true }">
						<button type="button" id="btnValidarPatron" class="btn btn-primary">CONTINUAR</button>
					</c:when>
					<c:otherwise>	
						<button type="button" id="btnValidarFisicas" class="btn btn-primary">CONTINUAR</button>
					</c:otherwise>
				</c:choose>				
			</div>
			
			<form id="formCancelar"
				action="${contextpath}/altaPublica/riss/iniciar" method="get">
			</form>
		</c:otherwise>
	</c:choose>	
	</div>
</div>

<div id="dialog-confirm-cancelar"
	title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> ¿Desea cancelar la
		solicitud registrada con folio: <strong>${folioSolicitudRegistrada}</strong>?
	</p>
</div>

<div id="dialog-confirm" title="Mensaje confirmaci&oacute;n">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> <label
			id="mensajeDialogo"></label>
	</p>
</div>

<div id="dialog-confirm-Error" title="Mensaje confirmaci&oacute;n">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> <label
			id="mensajeDialogoError"></label>
	</p>
</div>