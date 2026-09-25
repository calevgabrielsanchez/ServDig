<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/seguroDomestico/comunes/agregarCentroTrabajo.js" htmlEscape="true" />"></script>

<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<script type="text/javascript">
	<!--
	ventanilla = ${esVentanilla};
	var tieneSeguros = ${tieneSeguros};
	//-->
</script>
<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12">
			<div class="titulo separadorseccion">
				Agregar domicilio
			</div>
			<div style="display: none;" id="validacion">
				<div class="alert alert-info">
					<span id="mensaje-validacion">
						El domicilio que proporcionaste se encuentra dentro de la misma circunscripci&oacute;n de un domicilio que se
						registr&oacute; previamente por lo tanto no es necesario registrar el nuevo domicilio, deber&aacute;s registrar
						a los trabajadores dom&eacute;sticos dentro del domicilio que se muestra a continuaci&oacute;n.
					</span>
					<br/>
					<span>
						Si est&aacute;s de acuerdo con lo anterior presione el bot&oacute;n siguiente.
					</span>
				</div>
				<br/>
				<div class="ui-state-default" style="padding:15px 25px;">
					<h6><span id="descripcionDomicilio"></span></h6>
				</div>
			</div>
			<div id="infoDomicilio">
				<div class="row">
					<div id="wrapperDomicilio" class="col-sm-8 col-sm-offset-2">
						<div class="alert alert-info">
							<i class="glyphicon glyphicon-exclamation-sign" style="margin-right: 20px;"></i>
							<span class="required">*</span>
							Selecciona tu nuevo domicilio
							<a href="javascript:fnOpenBuscarDomicilio();" class="alert-link"> aqu&iacute;</a>
						</div>
					</div>
				</div>
				<br/>
				<table width="100%" class="table table-striped table-bordered tbl-domCentroTrabajo">
					<tr>
						<td class="label_patrones" style="width: 330px !important;"><span class="required">*</span>Calle</td>
						<td class="label_patrones" align="center" colspan="1" style="width: 115px !important"><span class="required">*</span>N&uacute;mero Exterior</td>
						<td class="label_patrones" align="center" colspan="1" >Letra Exterior</td>
					</tr>
					<tr>
						<td class="label_patrones_data" rowspan="3"><input type="text"
							readonly="readonly"
							id="cntroTrabajo.vialidadPrimaria.nombre" maxlength="100"
							size="50"
							value="${ sujetoTramite.cntroTrabajo.vialidadPrimaria.nombre }" />
							<input type="hidden"
							id="cntroTrabajo.vialidadPrimaria.clave"
							value="${ sujetoTramite.cntroTrabajo.vialidadPrimaria.clave }" />
							<input type="hidden"
							id="cntroTrabajo.vialidadPrimaria.tipoVialidad.clave"
							value="${ sujetoTramite.cntroTrabajo.vialidadPrimaria.tipoVialidad.clave }" />
							
							<input type="hidden" 
								id="cntroTrabajo.tipoBusquedaVialidad" value="${sujetoTramite.cntroTrabajo.tipoBusquedaVialidad}"/>
								<input type="hidden" 
								id="cntroTrabajo.calle" value="${sujetoTramite.cntroTrabajo.calle}"/>
								
							<!-- Atributos de domicilio carretera -->
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
							
							<!-- Atrbutos de domicilio camino -->
							<input type="hidden"
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
						<td><input type="text" readonly="readonly"
							id="cntroTrabajo.numExterior1"
							value="${ sujetoTramite.cntroTrabajo.numExterior1 }" /></td>
						<td><input type="text" readonly="readonly"
							id="cntroTrabajo.numExteriorAlf"
							value="${ sujetoTramite.cntroTrabajo.numExteriorAlf }" />
						</td>
						
					</tr>
					<tr>
						<td class="label_patrones" colspan="1" align="center">N&uacute;mero Interior</td>
						<td class="label_patrones" colspan="1" align="center">Letra Interior</td>
					</tr>
					<tr>
						<td><input type="text" readonly="readonly"
							id="cntroTrabajo.numInterior"
							value="${ sujetoTramite.cntroTrabajo.numInterior }" /></td>
						<td><input type="text" readonly="readonly"
							id="cntroTrabajo.numInteriorAlf"
							value="${ sujetoTramite.cntroTrabajo.numInteriorAlf }" />
						</td>
					</tr>
					<tr>
						<td class="label_patrones">Entre la calle</td>
						<td class="label_patrones" colspan="2">y la calle</td>
					</tr>
	
					<tr>
						<td class="label_patrones_data"><input type="text"
							readonly="readonly"
							id="cntroTrabajo.vialidadReferenciaPrimaria.nombre"
							value="${ sujetoTramite.cntroTrabajo.vialidadReferenciaPrimaria.nombre }" />
							<input type="hidden"
							id="cntroTrabajo.vialidadReferenciaPrimaria.clave"
							value="${ sujetoTramite.cntroTrabajo.vialidadReferenciaPrimaria.clave }" />
							<input type="hidden"
							id="cntroTrabajo.vialidadReferenciaPrimaria.tipoVialidad.clave"
							value="${ sujetoTramite.cntroTrabajo.vialidadReferenciaPrimaria.tipoVialidad.clave}" />
						</td>
						<td class="label_patrones_data" colspan="2"><input
							type="text" readonly="readonly"
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
					<tr>
						<td class="label_patrones"><span class="required">*</span>Colonia(Asentamiento)</td>
						<td class="label_patrones" colspan="2">
							<div><span class="required">*</span>Localidad</div>
						</td>
	
					</tr>
					<tr>
						<td class="label_patrones_data"><input type="text"
							readonly="readonly" id="cntroTrabajo.asentamiento.nombre"
							value="${ sujetoTramite.cntroTrabajo.asentamiento.nombre }" />
							<input type="hidden" id="cntroTrabajo.asentamiento.clave"
							value="${ sujetoTramite.cntroTrabajo.asentamiento.clave }" />
						</td>
						<td class="label_patrones_data" colspan="2"><input
							type="text" readonly="readonly"
							id="cntroTrabajo.asentamiento.localidad.nombre"
							value="${ sujetoTramite.cntroTrabajo.asentamiento.localidad.nombre }" /> 
							<input type="hidden" id="cntroTrabajo.asentamiento.localidad.clave"
							value="${ sujetoTramite.cntroTrabajo.asentamiento.localidad.clave }" />
						</td>
					</tr>
					<tr>
						<td class="label_patrones"><span class="required">*</span>Municipio o
							delegaci&oacute;n</td>
						<td class="label_patrones" colspan="2"><span class="required">*</span>Entidad
							Federativa</td>
						
					</tr>
					<tr>
						<td class="label_patrones_data"><input type="text"
							readonly="readonly"
							id="cntroTrabajo.asentamiento.localidad.municipio.nombre"
							value="${ sujetoTramite.cntroTrabajo.asentamiento.localidad.municipio.nombre }" /> 
							<input type="hidden" id="cntroTrabajo.asentamiento.localidad.municipio.clave"
							value="${ sujetoTramite.cntroTrabajo.asentamiento.localidad.municipio.clave }" />
						</td>
						<td class="label_patrones_data" colspan="2"><input
							type="text" readonly="readonly"
							id="cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa.nombre"
							value="${ sujetoTramite.cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa.nombre }" />
							<input type="hidden"
							id="cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa.clave"
							value="${ sujetoTramite.cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa.clave }" />
						</td>
					</tr>
					<tr>
						<td class="label_patrones" colspan="3"><span class="required">*</span>C&oacute;digo Postal</td>
					</tr>
					<tr>
						<td colspan="3"><input type="text" readonly="readonly"
							id="cntroTrabajo.codigoPostal.codigoPostal"
							value="${ sujetoTramite.cntroTrabajo.codigoPostal.codigoPostal }" />
							<input type="hidden"
							id="cntroTrabajo.vialidadPrimaria.tipoVialidad.descripcion"
							value="${ sujetoTramite.cntroTrabajo.vialidadPrimaria.tipoVialidad.descripcion }" />
						</td>
					</tr>
					<!-- <tr>
						<td  colspan="5">
							<span class="required">*</span>Subdelegaci&oacute;n
						</td>
					</tr>
					<tr>
						<td colspan="5">
							<input type="hidden" id="municipioIMSS.idMunicipio" value="${sujetoTramite.municipioIMSS.idMunicipio}" />
							<input type="hidden" id="municipioIMSS.cvecMunicipioSINDO"  value="${sujetoTramite.municipioIMSS.cvecMunicipioSINDO}" />
							<input type="hidden" id="municipioIMSS.descMunicipio"  value="${sujetoTramite.municipioIMSS.descMunicipio}" />
	
							<input type="hidden" id="municipioIMSS.subdelegacion.id" value="${sujetoTramite.municipioIMSS.subdelegacion.id}" />
							<input type="hidden" id="municipioIMSS.subdelegacion.clave" value="${sujetoTramite.municipioIMSS.subdelegacion.clave}" />
							<input type="hidden" id="municipioIMSS.subdelegacion.descripcion" value="${sujetoTramite.municipioIMSS.subdelegacion.descripcion}" />
	
							<input type="hidden" id="municipioIMSS.subdelegacion.delegacion.id" value="${sujetoTramite.municipioIMSS.subdelegacion.delegacion.id}" />
							<input type="hidden" id="municipioIMSS.subdelegacion.delegacion.clave" value="${sujetoTramite.municipioIMSS.subdelegacion.delegacion.clave}" />
							<input type="hidden" id="municipioIMSS.subdelegacion.delegacion.descripcion" value="${sujetoTramite.municipioIMSS.subdelegacion.delegacion.descripcion}" />
							<input type="hidden" id="municipioIMSS.subdelegacion.delegacion.ciz" value="${sujetoTramite.municipioIMSS.subdelegacion.delegacion.ciz}" />
							<div id="municipioImssContenedor" style="width: 100%;">
								Sin Subdelegaciones que mostrar
							</div>
						</td>
					</tr>  -->
				</table>
			</div>
			<form:form id="nextStepForm"
				action="${contextPath}/wizard/seguroDomestico/comunes/solicitarTipoPago">
			</form:form>
			<form:form id="nextStepFormExisteCentroTrabajo"
				modelAttribute="registroPatronal"
				action="${contextPath}/wizard/seguroDomestico/comunes/seleccionarCentroTrabajo">
				<form:hidden id="inputIdCentroTrabajo" path="centrotrabajo.idDomicilio" />
				<form:hidden id="inputNumeroRegistroPatronal" path="numeroRegistroPatronal"/>
				<form:hidden id="inputIdModalidad" path="modalidad.idModalidad"/>
				<form:hidden id="inputNumModalidad" path="modalidad.numModalidad"/>
				<form:hidden id="inputDigitoVerificador" path="digitoVerificador"/>
				<form:hidden id="inputClaveAsentamiento" path="centrotrabajo.asentamiento.clave"/>
				<form:hidden id="inputClaveLocalidad" path="centrotrabajo.asentamiento.localidad.clave"/>
				<form:hidden id="inputClaveMunicipio" path="centrotrabajo.asentamiento.localidad.municipio.clave"/>
				<form:hidden id="inputClaveEntidadFederativa" path="centrotrabajo.asentamiento.localidad.municipio.entidadFederativa.clave"/>
			</form:form>
		</div>
	</div>
	<div class="pie row">
		<div class="opciones col-sm-6">
		</div>
		<div class="controles col-sm-6">
			<div class="pull-right">
				<button id="cancelarTramite" class="btn btn-default">
					<c:choose>
						<c:when test="${esVentanilla and tieneSeguros}">Regresar</c:when>
						<c:otherwise>Cerrar</c:otherwise>
					</c:choose>
				</button>
				<a id="siguientePaso" style="display: none;" class="btn btn-primary"><i class="glyphicon glyphicon-step-forward"></i> Siguiente</a>
			</div>
		</div>
	</div>
</div>
<div id="dialogoMensajes">
	<p><span id="textoMensaje"></span></p>
</div>
<div id="domiciliosComponent"></div>
