<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/seguroDomestico/comunes/resumen.js" htmlEscape="true" />"></script>
<script type="text/javascript"
		src="<spring:url value="/static/resources/js/wizard/comunes/obtenerPais.js" htmlEscape="true" />">
</script>
<script type="text/javascript">
	<!--
	var codigoTipoSolicitud = ${codigoTipoSolicitud};
	var descripcionTipoSolicitud = '${descripcionTipoSolicitud}';
	var arrayCodigoTipoTramite = ${codigoTipoTramite};
	tipoOperacion = '${tipoOperacion}';
	cancelable = true;
	ventanilla = ${esVentanilla};
	var tieneSeguros = ${tieneSeguros};
	var datosEntradaFirma = {
		fechaElectronica: '${datosFirmaElectronica.fechaElectronicaFormateada}',
		nombreCompleto : '${datosFirmaElectronica.nombreCompleto}',
		registroPatronal : '${datosFirmaElectronica.registroPatronal}',
		rfc : '${datosFirmaElectronica.rfc}',
		curp : '${datosFirmaElectronica.curp}'
	};
	-->
</script>


<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<c:set var="esBimestral" value="${recargos}" />

<style>
	.total {
	    border-top: 1px solid lightgray;
	    font-size: 25px;
	    font-weight: bolder;
	}
</style>

<div class="contenedor col-sm-12">
	<c:choose>
		<c:when test="${empty error}">
			<div class="contenido row">
				<c:if test="${tipoOperacion != 'RENOVACION' }">
				<jsp:include page="pasosDomestico.jsp">
					<jsp:param name="paso" value="4" />
				</jsp:include>
				</c:if>
				<div class="col-sm-12 form-horizontal">
					<c:set var="defaultLocale" value="${pageContext.request.locale}"/>
					<fmt:setLocale value="es_MX" scope="session"/>
					<div class="alert alert-success">
						La solicitud se ha creado exitosamente: <strong>${solicitud.numSolicitud}</strong>
					</div>
					<input type="hidden" id="idSolicitud" value=${solicitud.idSolicitud} >
					<div class="titulo separadorseccion">
						<span>Informaci&oacute;n del seguro</span>
					</div>
					<div class="form-group">
						<div class="col-sm-3">
							<span><strong>Fecha Inicio Vigencia: </strong></span>							
						</div>
	                   <div class="col-sm-2">              
	                     <span><fmt:formatDate value="${solicitud.tramite[0].cotizacion.detalle.fechaInicioCalculo.time}" pattern="dd/MM/yyyy"/> </span>
	                   </div>
	                   <div class="col-sm-3">
	                     <span><strong>Fecha Fin Vigencia: </strong></span>
	                   </div>
	                   <div class="col-sm-2">
							<c:choose>
								<c:when test="${esBimestral eq true}">
									<c:set var="finVigencia" value="${solicitud.tramite[0].cotizacion.detalle.empleados[0].periodos[0].finPeriodo.time}" />
								</c:when>
								<c:otherwise>
									<c:set var="finVigencia" value="${solicitud.tramite[0].cotizacion.detalle.fechaFinCalculo.time}" />
								</c:otherwise>
							</c:choose>
							<span><fmt:formatDate value="${finVigencia}" pattern="dd/MM/yyyy"/></span>
	                   </div>
	                   <div class="col-sm-2">
	                     
	                   </div>
					</div>
					<div class="form-group">
						<div class="col-sm-12">
							<span><strong>Zona salarial: </strong></span>
							<span>${solicitud.tramite[0].cotizacion.detalle.zonaSalarial}</span>
						</div>
					</div>
					
					<div class="separadorseccion">
						<span>Domicilio</span>
					</div>
					<div class="row">
						<div class="col-xs-2"><strong>Calle :</strong></div>
						<div class="col-xs-2">
						<c:if test="${domicilio.vialidadPrimaria != null}">
							${domicilio.vialidadPrimaria.nombre}
						</c:if>
						</div>
						<div class="col-xs-2"><strong>N&uacute;m. Ext. :</strong></div>
						<div class="col-xs-2">${domicilio.numExteriorAlf}
							<c:if test="${domicilio.numExterior1 gt 0}">
								${domicilio.numExterior1}
							</c:if>
						</div>
						<div class="col-xs-2"><strong>N&uacute;m. Int. :</strong></div>
						<div class="col-xs-2">${domicilio.numInteriorAlf}
							<c:if test="${domicilio.numInterior gt 0}">
								${domicilio.numInterior}
							</c:if>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-2"><strong>Colonia :</strong></div>
						<div class="col-xs-2">${domicilio.asentamiento.nombre}</div>
						<div class="col-xs-2"><strong>Municipio :</strong></div>
						<div class="col-xs-2">
						<c:if test="${domicilio.asentamiento.localidad != null and domicilio.asentamiento.localidad.municipio != null}">
							${domicilio.asentamiento.localidad.municipio.nombre}
						</c:if></div>
						<div class="col-xs-2"><strong>C.P. :</strong></div>
						<div class="col-xs-2">${domicilio.codigoPostal}</div>
					</div>
			
					<div class="separadorseccion">
						<span>Medios de contacto</span>
					</div>
					<c:forEach items="${personaMC.mediosContacto}" var="medioContacto">
						<div class="row">
							<div class="col-xs-4">
								<strong>
									<c:choose>
										<c:when test="${medioContacto.tipoMedioContacto.idTipoMedioContacto == 1}">
											Correo electr&oacute;nico:
										</c:when>
										<c:when test="${medioContacto.tipoMedioContacto.idTipoMedioContacto == 2}">
											Tel&eacute;fono fijo:
										</c:when>
										<c:when test="${medioContacto.tipoMedioContacto.idTipoMedioContacto == 3}">
											Tel&eacute;fono m&oacute;vil:
										</c:when>
										<c:when test="${medioContacto.tipoMedioContacto.idTipoMedioContacto == 4}">
											Facebook:
										</c:when>
										<c:when test="${medioContacto.tipoMedioContacto.idTipoMedioContacto == 5}">
											Twitter:
										</c:when>
									</c:choose>
								</strong>
							</div>
							<div class="col-xs-2"><span>${medioContacto.desFormaContacto}</span></div>
						</div>
					</c:forEach>

					<div class="separadorseccion">
						<span>Lista de trabajadores asociados a la solicitud</span>
					</div>
					<table id="tblTrabajadores" style="width:100%;" class="table table-striped table-bordered table-word-wrap-fixed"
						cellpadding="0" cellspacing="0" border="0">
						<thead>
							<tr>
								<th>NSS</th>
								<th>Nombre</th>
								<th>Salario diario integrado</th>								
								<th>
									<c:choose>
										<c:when test="${esBimestral eq true}">
											Parcialidad a Pagar
										</c:when>
										<c:otherwise>
											Costo anual
										</c:otherwise>
									</c:choose>
								</th>
							</tr>
						</thead>
						<tbody>
							<c:set var="total" value="0"/>
							<c:forEach items="${solicitud.tramite[0].cotizacion.detalle.empleados}" var="empleado" varStatus="status">
								<c:choose>
										<c:when test="${esBimestral eq true}">
											<c:set var="costoEmpleado" value="${empleado.periodos[0].total}" />
											<c:set var="montoCuotaRecargo" value="${empleado.periodos[0].cuotaRecargo}"/>
										</c:when>
										<c:otherwise>
											<c:set var="costoEmpleado" value="${empleado.cuotaTotal}" />
											<c:set var="montoCuotaRecargo" value="0"/>
										</c:otherwise>
								</c:choose>
								<c:set var="montoCuotaSinRecargo" value="${costoEmpleado - montoCuotaRecargo}" />
								<tr>
									<td>${empleado.numeroSeguridadSocial}</td>
									<td>${empleado.nombreTrabajador}</td>
									<td><fmt:formatNumber value="${empleado.salario}" type="currency"/></td>									
									<td><fmt:formatNumber value="${costoEmpleado}" type="currency"/></td>
								</tr>
								<c:set var="total" value="${total + costoEmpleado}"/>
							</c:forEach>
						</tbody>
					</table>
					<table style="width:100%;"
						cellpadding="0" cellspacing="0" border="0">
						<thead>
							<tr>
								<td style="width:60%; border:1px solid transparent; padding:8px"></td>
								<td style="width:20%; border:1px solid transparent; padding:8px;" class="total">Costo total</td>
								<td style="width:20%; border:1px solid transparent; padding:8px;" class="total"><fmt:formatNumber value="${total}" type="currency"/></td>
							</tr>
						</thead>
					</table>
					<div class="alert alert-info">
						<p><b>Acciones al concluir la solicitud</b></p>
						<c:choose>
							<c:when test="${esVentanilla}">
								<p>
									Una vez concluida la solicitud, te recordamos que tienes que iniciar nuevamente el tr&aacute;mite para poder
									ver la impresi&oacute;n de l&iacute;neas de captura, comprobante y cuestionario m&eacute;dico.
								</p>
								<p>
									Recuerda imprimir tus L&iacute;neas de Captura y realizar el pago correspondiente antes de la fecha de fin de vigencia de cada l&iacute;nea. Recuerda que el incumplimiento en el pago de tus l&iacute;neas de captura en tiempo y forma es motivo de cancelaci&oacute;n de tus servicios m&eacute;dicos.
								</p>
							</c:when>
							<c:otherwise>
								<p>
									Una vez concluida la solicitud de incorporaci&oacute;n, es necesario que imprimas las l&iacute;neas
									de captura, las cuales se encuentran disponibles en la secci&oacute;n "Incorporaci&oacute;n voluntaria 
									de trabajadores Dom&eacute;sticos"
									mediante la opci&oacute;n "Ver Detalle" y realizar el pago correspondiente antes de la
									fecha de vencimiento.
								</p>
								<p>
									En caso del llenado del cuestionario m&eacute;dico de tu trabajador dom&eacute;stico, este
									deber&aacute; ser impreso y presentado en la Unidad de Medicina Familiar previo a tu atenci&oacute;n.
								</p>
								<p>
									Con la finalidad de que el Instituto Mexicano del Seguro Social se mantenga en contacto contigo,
									te solicitamos tener registrada y actualizada una direcci&oacute;n de correo electr&oacute;nico.
								</p>
							</c:otherwise>
						</c:choose>
					</div>
					<input id="folioSolicitud" type="hidden" value="${solicitud.numSolicitud}"/>
					<input id="contenidoFirmar" type="hidden" value="${contenidoFirmar}"/>
					<%--form:form id="nextStepForm" action="${contextPath}/wizard/seguroDomestico/comunes/generarComprobantes" target="_blank">
					</form:form--%>
					<form:form id="nextStepForm" action="${contextPath}/wizard/seguroDomestico/comunes/final">
					</form:form>
					<fmt:setLocale value="${defaultLocale}" scope="session"/>
				</div>
			</div>
			<div class="pie row">
				<div class="opciones col-sm-6">
					<div class="btn-group dropup">
						<button type="button" class="btn btn-primary">Acciones</button>
						<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
						<%--<li><a id="cambiaDomicilio" href="#"><i class="glyphicon glyphicon-refresh"></i> Cambiar Domicilio</a></li>				--%>
	           			  <li><a id="siguientePaso" href="#"><i class="glyphicon glyphicon-ok"></i> Finalizar tr&aacute;mite</a></li>				
	                         <li><a id="cancelarSolicitud" href="#"><i class="glyphicon glyphicon-trash"></i> Cancelar tr&aacute;mite</a></li>							
						</ul>
					</div>
				</div>
				<div class="controles col-sm-6">
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="contenido row">
				<div class="alert alert-danger">
					<span>${error}</span>
				</div>
			</div>
			<div class="pie row">
				<div class="controles col-sm-6">
					<div class="pull-right">
						<button id="salirSolicitud" class="btn btn-default">Salir</button>
					</div>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</div>

<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			¿Deseas cancelar la solicitud pendiente con folio: <strong>${solicitud.numSolicitud}</strong>?
	</p>
</div>

<div id="dialog-confirm" title="Mensaje">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			<label id="mensajeDialogo"></label>
	</p>
</div>
