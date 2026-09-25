<%@ include file="../../../../../../general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.FraccionEnum"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/date.format.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/portal/afiliacion/modificacionSRT.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/modificacion/patron/altaPatronal/clasificacion.js" htmlEscape="true" />"></script>

<c:set var="fisica" value="${sujetoObligado.fisica}"/>
<c:set var="moral" value="${sujetoObligado.moral}"/>
<c:set var="proceso" value="${sujetoTramite.proceso}"/>
<c:set var="esPatronRPC" value="${esPatronRPC}"/>
<c:set var="origenVENTANILLA" value="<%=OrigenSolicitudEnum.VENTANILLA.getId()%>" />

<script>
	var esOperador = ${esOperador};
	var idSujetoObligado = '${sujetoObligado.cveIdSujetoObligado}';
	var tipoPersonaFiscal = '${sujetoObligado.tipoPersonaFiscal}';
	var fraccionAgricultura = '<%=FraccionEnum.AGRICULTURA.getCodigo()%>';
	var esPatronRPC = ${esPatronRPC};

	var rfcSujetoObligado='';
	<c:if test="${fisica != null}">
		rfcSujetoObligado='${fisica.rfc}';
	</c:if><c:if test="${moral != null}">
		rfcSujetoObligado='${moral.rfc}';
	</c:if>

	var hasMensajeError = false;
	<c:if test="${not empty solicitudTramite.errorFormGeneral}">
		hasMensajeError = true;
	</c:if>

	var idClasificacion='${objClasificacion.id}';
	var equipoTransporte = '${sujetoTramite.cuentaConTransporte}';
	var mostrarCentroTrabajo = false;
	var mostrarDomicilioCentroTrabajo = false;
	var mostrarBienes = false;

	var idSolicitud = 0;
	<c:if test="${idSolicitud != null}">
		idSolicitud = ${idSolicitud};
	</c:if>

	var fechaPresentacion = "${sujetoTramite.clasificacion.fecPresentacion}";
	var fechaEfecto = "${sujetoTramite.clasificacion.fecEfecto}";
	var indReintento = ${indReintento};
	var indRPCInvalido = ${indRPCInvalido};
	var idTipoTramiteAlta = <%=TipoTramiteEnum.ALTA_SRT.getCodigo()%>;
	var identificadorTramite = <%=TipoTramiteEnum.ALTA_SRT.getCodigo()%>;
	var context = context_path + '/clasificacion/';
	var sessionId = '<%=request.getSession().getId()%>';

	var isProductorCana = 0;
	<c:if test="${sujetoTramite.clasificacion.indProductorCana != null}">
		isProductorCana = ${sujetoTramite.clasificacion.indProductorCana};
	</c:if>

	$(function(){
		$('div.site_position_center').css('width', '825px');
		$('div.site_position_center').css('margin', '0 auto');
	});

	var origenVENTANILLA = '<%=OrigenSolicitudEnum.VENTANILLA.getId()%>';
	var origenApp = '<%=request.getSession().getServletContext().getInitParameter("ORIGEN_APP")%>';
	var mostrarFusionSustitucion = false;

	var codigoTramite = idTipoTramiteAlta;

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

.contenedor .pie .opciones {
	float: left;
	width: 60%;
}

.contenedor .pie .controles {
	float: right;
	width: 40%;
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

.form-horizontal .form-group-sm {
	font-size: 14px;
}

.form-group-sm .form-control {
	line-height: 1.5;
	padding: 5px 10px;
}

.form-group-sm input.form-control {
	height: 30px;
}

.form-horizontal .radio-inline {
	 padding-top: 2px;
}
</style>

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div id="formularioPatron" class="col-sm-12">

			<c:if test="${not empty solicitudTramite.errorFormGeneral}">
				<div class="alert alert-danger">
					<button type="button" class="close" data-dismiss="alert">×</button>
					<strong>Error: </strong>
					${solicitudTramite.errorFormGeneral}
				</div>
			</c:if>

			<c:if test="${not empty folioSolicitud}">
				<input type="hidden" id="hdnFolioSolicitud" value="${folioSolicitud}" />
				<div class="alert alert-success">
					<c:choose>
						<c:when test="${!isRetomar}">
							<spring:message code="label.solicitud.iniciada" arguments="${folioSolicitud}"></spring:message>
						</c:when>
						<c:otherwise>
							<spring:message code="label.solicitud.retomando" arguments="${folioSolicitud}"></spring:message>
						</c:otherwise>
					</c:choose>
				</div>
			</c:if>
			<div class="alert alert-danger" style="display: none" id="divErrorCampos"></div>
			<c:if test="${empty solicitudTramite.errorFormGeneral}">
				<div id="seccionDatosGenerales" class="row">
					<div class="col-sm-12">
						<div class="separadorseccion">
							<span>
								<spring:message code="titulo.datos.generales.patron" />
							</span>
						</div>
						<form class="form-horizontal">
							<c:if test="${origenApp eq origenVENTANILLA}">
								<div class="form-group form-group-sm">
									<div class="col-sm-offset-10 col-sm-2">
										<div class="checkbox">
											<label style="font-weight: 700;">

											<c:choose>
												<c:when test="${sujetoTramite.clasificacion.auditoria}">
														<input type="checkbox"  name="auditoria" id="auditoria" checked="true" class="ns_" />
														<spring:message code="label.auditoria" />
												</c:when>
												<c:otherwise>
													<input type="checkbox"  name="auditoria" id="auditoria"  class="ns_"/>
													<spring:message code="label.auditoria" />
												</c:otherwise>
											</c:choose>

											</label>
										</div>
									</div>
								</div>
							</c:if>
							<div class="form-group form-group-sm">
								<label class="col-sm-3 control-label">
									<c:if test="${esOperador == true}">
										<spring:message code="label.fecha.presentacion" />:
									</c:if>
									<c:if test="${esOperador == false}">

										<spring:message code="label.fecha.captura" /><span class="required" id="fechaPresentacionReq">*</span>:
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
									<span style="display:none;" class="error" id="fechaPresentacionError"></span>
								</div>

								<label class="col-sm-5 control-label" style="padding-top: 0px;">
									<spring:message code="label.fecha.surte.efecto" /><span class="required" id="fechaEfectoReq">*</span>:
								</label>
								<div class="col-sm-2">
									<!--  <input type="text" id="fechaEfecto" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${sujetoTramite.clasificacion.fecEfecto}"/>" style="width: 100px;" onkeypress="evaluar(this, event)" size="10" onblur="evaluarOnblur(this, event)" onclick="showCalendar()" />-->
									<input type="text" id="fechaEfecto"
										spanRequired="fechaEfectoReq";
										spanError="fechaEfectoError";
										value="<fmt:formatDate pattern="dd/MM/yyyy" value="${sujetoTramite.clasificacion.fecEfecto}"/>"
										class="form-control ns_" onkeypress="evaluar(this, event)" size="10"
										onblur="evaluarOnblur(this, event)" onclick="showCalendarVentanilla()" />
									<label id="fechaEfectoInvalidaMsg" style="color: red; display: none;">fecha inv&aacute;lida </label>
									<span style="display:none;" class="error" id="fechaEfectoError"></span>
								</div>
							</div>
						</form>
					</div>
				</div>

				<div id="seccionClasificacion" class="row">
					<div class="col-sm-12">
						<div class="separadorseccion">
							<span>
								<spring:message code="label.aviso.seguro.riesgos" />
							</span>
						</div>

						<form class="form-horizontal">
							<div class="form-group form-group-sm">
								<label class="col-sm-4 control-label">

									<span class="delta-tooltip" data-toggle="tooltip" data-placement="right"
										title="Anotar el(los) objeto(s) principal(es) para el(los) cual(es) fue constituida la empresa o las actividades que desarrolla para llevar a cabo el prop&oacute;sito del negocio. El giro se define como el conjunto de actividades a las que se dedica una empresa o patr&oacute;n">
										Especifica tu giro o actividad<span class="required" id="giroClasificacionReq">*</span>:
									</span>
								</label>
								<div class="col-sm-8">
									<textarea class="textClasificacion form-control ns_" style="width: 97%;" rows="7" id="giroClasificacion"
										onkeydown="validaSize(this, 300, event);" onkeyup="validaSize(this, 300, event);"
										onblur="validaSize(this, 300, event);"><c:if test="${sujetoTramite.clasificacion != null}">${sujetoTramite.clasificacion.giro}</c:if></textarea>
									<span style="display:none;" class="error" id="giroClasificacionError"></span>
								</div>
							</div>


							<div class="form-group form-group-sm" hidden="true">
								<label class="col-sm-4 control-label">

									Prestas servicios especializados<span class="required" id="clasificacion.indPrestaServicioPersonal1Req">*</span>:
								</label>
								<div class="col-sm-8">
									<div class="radio">
										<label class="radio-inline">
											<form:radiobutton path="sujetoTramite.clasificacion.indPrestaServicioPersonal"
												value="1" readonly="readonly" class="ns_"/>S&iacute;
										</label>

										<label class="radio-inline">
											<form:radiobutton path="sujetoTramite.clasificacion.indPrestaServicioPersonal" checked="checked"
												value="0" readonly="readonly" class="ns_"/>No
										</label>
									</div>
									<span class="error" id="clasificacion.indPrestaServicioPersonal1Error"></span>
								</div>
							</div>

							<div class="form-group form-group-sm">
								<label class="col-sm-4 control-label">
									Presta servicios especializados:
								</label>
								<div class="col-sm-8">
									<c:if test="${sujetoTramite.clasificacion.indPrestaServicioPersonal == 2}">
										<input type="checkbox" id="indPrestaServicioPersonalCheck" checked="true">
									</c:if>
									<c:if test="${sujetoTramite.clasificacion.indPrestaServicioPersonal != 2}">
										<input type="checkbox" id="indPrestaServicioPersonalCheck">
									</c:if>
								</div>
							</div>
						</form>

						<div class="separadorseccion">
							<span>
								<spring:message code="label.aviso.ley.seguro" />
							</span>
						</div>

						<strong style="font-size: 14px;">
							<spring:message code="label.aviso.conformidad" />
							:
						</strong>

						<div class="row m-t-sm">
							<div id="wrapperIntsAnterior" class="col-sm-10 col-sm-offset-1">
								<div class="alert alert-info">
									<i class="glyphicon glyphicon-exclamation-sign" style="margin-right: 20px;"></i>

										Selecciona tu clasificaci&oacute;n conforme al Cat&aacute;logo de Actividades para la Clasificaci&oacute;n de
										las Empresas en el Seguro de Riesgos de Trabajo
									<a href="javascript:clasificador2();" id="clasificacionSelector" class="alert-link"  onclick="uid_call('imss.patrones.alta_patronal.clasificacion.clasificador','clickout')">aqu&iacute;</a><span class="required" id="gridClasificacionNuevaReq">*</span>.
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
									<span class="dato" id="claveDivisionCompleta">${sujetoTramite.clasificacion.fraccion.grupo.division.numDivision}${sujetoTramite.clasificacion.fraccion.grupo.numGrupo}${sujetoTramite.clasificacion.fraccion.numFraccion}</span>
									<span class="dato" id="claveDivision" style="visibility: hidden;">${sujetoTramite.clasificacion.fraccion.grupo.division.numDivision}</span>
									<span class="dato" id="claveGrupo" style="visibility: hidden;">${sujetoTramite.clasificacion.fraccion.grupo.numGrupo}</span>
									<span class="dato" id="claveFraccion" style="visibility: hidden;">${sujetoTramite.clasificacion.fraccion.numFraccion}</span>
									<input type="hidden" id="fraccion" value="${sujetoTramite.clasificacion.fraccion.id}" />
									<input type="hidden" id="fraccionCompleta"
										value="${sujetoTramite.clasificacion.fraccion.grupo.division.numDivision}${sujetoTramite.clasificacion.fraccion.grupo.numGrupo}${sujetoTramite.clasificacion.fraccion.numFraccion}">
								</td>
								<td align="center">
									<span class="dato" id="textDivison">${sujetoTramite.clasificacion.fraccion.grupo.division.descripcion}</span>
									<input type="hidden" id="divison" value="${sujetoTramite.clasificacion.fraccion.grupo.division.id}" />
								</td>
								<td align="center">
									<span class="dato" id="textGrupo">${sujetoTramite.clasificacion.fraccion.grupo.descripcion}</span>
									<input type="hidden" id="grupo" value="${sujetoTramite.clasificacion.fraccion.grupo.id}" />
								</td>
								<td align="center">
									<span class="dato" id="textFraccion">${sujetoTramite.clasificacion.fraccion.descripcionDetallada}</span>
								</td>
								<td align="center">
									<input type="hidden" id="claveClase" value="${sujetoTramite.clasificacion.fraccion.clase.clave}">
									<span class="dato" id="textClase">${sujetoTramite.clasificacion.fraccion.clase.descripcion}</span>
									<input type="hidden" id="clase" />
								</td>
								<td align="center">
									<span class="dato" id="textPrimaAnt">${sujetoTramite.clasificacion.fraccion.primaSRT}</span>
									<input type="hidden" id="primaAnt" />
								</td>
							</tr>
						</table>
						<span class="error" id="gridClasificacionNuevaError"></span>
					</div>
				</div>


				<div id="seccionProductosMaeriales" class="row">
					<div class="col-sm-12">
						<div class="separadorseccion">
							<span>
								<spring:message code="label.datos.actividad.declara" /><span class="required" id="gridProductosServiciosReq">*</span>
							</span>
						</div>
						<div id="infoCaneros" style="display: none;">
							<c:if test="${origenApp eq origenVENTANILLA && moral != null}">
								<table>
									<tr>
										<td>Seleccione esta opción si es productor de ca&ntilde;a</td>
										<td>
											<input type="checkbox" id="indProductorCana" onchange="inhabilitarPSPRCP()" name="indProductorCana" class="ns_">
										</td>
									</tr>
								</table>
							</c:if>
						</div>

						<div class="row">
							<div class="col-sm-6">
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

														<input type="button" onclick="uid_call('imss.patrones.alta_patronal.clasificacion.productos.modificar','clickin');dialogoEdicion('producto', 'Modificar');" class="btn btn-default btn-sm"
															value="Modificar">
														<input type="button" onclick="uid_call('imss.patrones.alta_patronal.clasificacion.productos.eliminar','clickin');dialogoEdicion('producto', 'Eliminar');" class="btn btn-danger btn-sm"
															value="Eliminar">

															<span class="delta-tooltip" data-toggle="tooltip"
															title="Capture los productos o servicios que ofrece u ofrecer&aacute; y
															que caracterizan mejor a su empresa o negocio.">
															<input type="button" onclick="uid_call('imss.patrones.alta_patronal.clasificacion.productos.agregar','clickin');dialogoEdicion('producto', 'Agregar');" class="btn btn-primary btn-sm"
																value="Agregar" id="btnAgregarProducto">
														</span>
													</form>
												</div>
											</td>
										</tr>
									</tfoot>
								</table>
								<span class="error" id="gridProductosServiciosError"></span>
							</div>
							<div class="col-sm-6">
								<table id="gridMaeriasMateriales" style="width: 100%; vertical-align: top;" cellpadding="0" cellspacing="0"
									class="table table-striped table-bordered
									table-word-wrap-fixed">
									<thead></thead>
									<tbody style="width: 100%;"></tbody>

									<tfoot>
										<tr>
											<td></td>
											<td>
												<div class="opciones">
													<form>

														<input type="button" onclick="uid_call('imss.patrones.alta_patronal.clasificacion.materiales.modificar','clickin');dialogoEdicion('material', 'Modificar');" class="btn btn-default btn-sm"
															value="Modificar">
														<input type="button" onclick="uid_call('imss.patrones.alta_patronal.clasificacion.materiales.eliminar','clickin');dialogoEdicion('material', 'Eliminar');" class="btn btn-danger btn-sm"
															value="Eliminar">
														<span class="delta-tooltip" data-toggle="tooltip"
															title="Capture una lista de los insumos o materias primas esenciales, que emplea
															o emplear&aacute; para la
															elaboraci&oacute;n de productos o prestaci&oacute;n de servicios.
															Las principales materias primas o materiales utilizados son bienes consumibles
															utilizados que generalmente pierden sus propiedades y caracter&iacute;sticas para
															transformarse y formar parte de otros bienes o productos finales.
															Para el caso de servicios se denomina as&iacute; a los recursos de entrada al
															proceso cuyo flujo de salida es el servicio entregado al cliente.">
															<input type="button" onclick="uid_call('imss.patrones.alta_patronal.clasificacion.materiales.agregar','clickin');dialogoEdicion('material', 'Agregar');" class="btn btn-primary btn-sm"
																value="Agregar" id="btnAgregarMateriales">
														</span>
													</form>
												</div>
											</td>
										</tr>
									</tfoot>
								</table>
								<span class="error" id="gridMaeriasMaterialesError"></span>
							</div>
						</div>
					</div>
				</div>


				<div id="seccionMaquinariaEquipo" class="col-sm-12">
					<div class="row">
						<div class="separadorseccion">
							<span>

								<spring:message code="label.maquinaria.equipo" /><span class="required" id="gridMaquinariaEquipoReq">*</span>
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

												<input type="button" onclick="uid_call('imss.patrones.alta_patronal.clasificacion.maquinaria.modificar','clickin');dialogoEdicion('equipo', 'Modificar');" class="btn btn-default btn-sm"
													value="Modificar" >
												<input type="button" onclick="uid_call('imss.patrones.alta_patronal.clasificacion.maquinaria.eliminar','clickin');dialogoEdicion('equipo', 'Eliminar');" class="btn btn-danger btn-sm"
													value="Eliminar">
												<span class="delta-tooltip" data-toggle="tooltip"
													title="Ingrese una lista de las m&aacute;quinas, herramientas o equipos esenciales, que emplea o emplear&aacute; para transformar los insumos o materias primas, en los productos o servicios, de su empresa o negocio.">
													<input type="button" onclick="uid_call('imss.patrones.alta_patronal.clasificacion.maquinaria.agregar','clickin');dialogoEdicion('equipo', 'Agregar');" class="btn btn-primary btn-sm"
														value="Agregar" id="btnAgregarMaquinariaEquipo">
												</span>
											</form>
										</div>
									</td>
								</tr>
							</tfoot>
						</table>
						<span class="error" id="gridMaquinariaEquipoError"></span>
					</div>
				</div>

				<div id="seccionTransporte" class="col-sm-12">
					<div class="row">
						<div class="separadorseccion">
							<span>
								<spring:message code="label.equipo.transporte" />
							</span>
						</div>
						<div class="form-horizontal">
							<div class="form-group form-group-sm">
								<label class="control-label col-sm-4">&iquest;Cuentas con equipo de transporte?</label>

								<div class="col-sm-8">
									<div class="radio">
										<label class="radio-inline">
										<form:radiobutton id="siCuetaConTransporte" path="sujetoTramite.cuentaConTransporte" value="1"
											onchange="toggleCuentaConTransporte(1)" cssClass="ns_"/>S&iacute;
										</label>
										<label class="radio-inline">
										<form:radiobutton id="noCuetaConTransporte" path="sujetoTramite.cuentaConTransporte" value="0"
											onchange="toggleCuentaConTransporte(0)" cssClass="ns_"/>No
										</label>
									</div>
								</div>
							</div>
						</div>
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

												<input id="modificarTransporte" type="button" onclick="uid_call('imss.patrones.alta_patronal.clasificacion.transporte.modificar','clickin');dialogoEdicion('transporte', 'Modificar');"
													class="btn btn-default btn-sm" value="Modificar">
												<input id="eliminarTransporte" type="button" onclick="uid_call('imss.patrones.alta_patronal.clasificacion.transporte.eliminar','clickin');dialogoEdicion('transporte', 'Eliminar');"
													class="btn btn-danger btn-sm" value="Eliminar">
												<span class="delta-tooltip" data-toggle="tooltip"
													title="Indica el nombre, n&uacute;mero de unidades, uso, combustible o energ&iacute;a y capacidad o potencia del equipo de transporte que empleas para el desarrollo de las actividades de tu negocio o empresa, ya sea &eacute;ste utilizado para el acopio, traslado, entrega, distribuci&oacute;n o venta de materias primas, materiales, productos, prestaci&oacute;n de  los servicios que ofreces, o para el transporte de personal.">
													<input id="agregarTransporte" type="button" onclick="uid_call('imss.patrones.alta_patronal.clasificacion.transporte.agregar','clickin');dialogoEdicion('transporte', 'Agregar');"
														class="btn btn-primary btn-sm" value="Agregar">
												</span>
											</form>
										</div>
									</td>
								</tr>
							</tfoot>
						</table>
					</div>
				</div>


				<div id="seccionProcesos" class="col-sm-12">
					<div class="row">
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

														<span class="delta-tooltip" data-toggle="tooltip"
															title="Describe los procesos de trabajo de la actividad de tu empresa o negocio, precisando los procesos iniciales, intermedios y finales. En su caso, describe los procesos que realizas para trasformar, fabricar o procesar materias primas o insumos, en los productos de tu empresa o negocio, especifica los insumos y para qu&eacute; se utiliza la maquinaria, herramienta o equipo que se&#241;al&oacute;s. Trat&aacute;ndose de empresas prestadoras de servicios, deber&aacute;s describir los procesos iniciales, intermedios y finales del o los  servicios que prestas, especifique los insumos y para qu&eacute; se utiliza la maquinaria, herramienta o equipo que se&#241;alaste.">
															<label >
																<spring:message code="label.procesos.iniciales" /><span class="required" id="procesoInicialReq">*</span>:
															</label>
														</span>
													</h6>
													<div style="width: 100%; height: 150px;">
														<textarea class="textClasificacion ns_" style="width: 98%;" rows="7" id="procesoInicial"
															onkeypress="validaSize(this, 600, event)" onkeyup="validaSize(this, 600, event)"
															onblur="validaSize(this, 600, event);"><c:if test="${proceso != null}">${proceso.desInicial}</c:if></textarea>
													</div>
													<span class="error" id="procesoInicialError"></span>
												</div>

												<div style="display: table-row;" id="proceso-intermedio">
													<h6 style="font-size: 1.0em !important;">

														<span class="delta-tooltip" data-toggle="tooltip"
															title="Describe los procesos de trabajo de la actividad de tu empresa o negocio, precisando los procesos iniciales, intermedios y finales. En su caso, describe los procesos que realizas para trasformar, fabricar o procesar materias primas o insumos, en los productos de tu empresa o negocio, especifica los insumos y para qu&eacute; se utiliza la maquinaria, herramienta o equipo que se&#241;al&oacute;s. Trat&aacute;ndose de empresas prestadoras de servicios, deber&aacute;s describir los procesos iniciales, intermedios y finales del o los  servicios que prestas, especifique los insumos y para qu&eacute; se utiliza la maquinaria, herramienta o equipo que se&#241;alaste.">
															<label>
																<spring:message code="label.procesos.intermedios" /><span class="required" id="procesoIntermedioReq">*</span>:
															</label>
														</span>
													</h6>
													<div style="width: 100%; height: 150px;">
														<textarea class="textClasificacion ns_" style="width: 98%;" rows="7" id="procesoIntermedio"
															onkeypress="validaSize(this, 600, event)" onkeyup="validaSize(this, 600, event)"
															onblur="validaSize(this, 600, event);"><c:if test="${proceso != null}">${proceso.desIntermedio}</c:if></textarea>
													</div>
													<span class="error" id="procesoIntermedioError"></span>
												</div>

												<div style="display: table-row;" id="proceso-final">
													<h6 style="font-size: 1.0em !important;">

														<span class="delta-tooltip" data-toggle="tooltip"
															title="Describe los procesos de trabajo de la actividad de tu empresa o negocio, precisando los procesos iniciales, intermedios y finales. En su caso, describe los procesos que realizas para trasformar, fabricar o procesar materias primas o insumos, en los productos de tu empresa o negocio, especifica los insumos y para qu&eacute; se utiliza la maquinaria, herramienta o equipo que se&#241;al&oacute;s. Trat&aacute;ndose de empresas prestadoras de servicios, deber&aacute;s describir los procesos iniciales, intermedios y finales del o los  servicios que prestas, especifique los insumos y para qu&eacute; se utiliza la maquinaria, herramienta o equipo que se&#241;alaste.">
															<label>
																<spring:message code="label.procesos.finales" /><span class="required" id="procesoFinalReq">*</span>:
															</label>
														</span>
													</h6>
													<div style="width: 100%; height: 150px;">
														<textarea class="textClasificacion ns_" style="width: 98%;" rows="7" id="procesoFinal"
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
				</div>


				<div id="seccionPersonal" class="col-sm-12">
					<div class="row">
						<div class="separadorseccion">
							<span>

								<spring:message code="label.personal" /><span class="required" id="gridPersonalReq">*</span>
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

												<input type="button" onclick="uid_call('imss.patrones.alta_patronal.clasificacion.personal.modificar','clickin');dialogoEdicion('personal', 'Modificar');" class="btn btn-default btn-sm"
													value="Modificar">
												<input type="button" onclick="uid_call('imss.patrones.alta_patronal.clasificacion.personal.eliminar','clickin');dialogoEdicion('personal', 'Eliminar');" class="btn btn-danger btn-sm"
													value="Eliminar">
												<span class="delta-tooltip" data-toggle="tooltip"
													title="Indica el n&uacute;mero de trabajadores con que cuenta el patr&oacute;n por grupos de oficio u ocupaci&oacute;n para el desarrollo de su actividad, describiendo el trabajo que desarrollan, el cual contribuye a la  fabricaci&oacute;n o venta de productos o prestaci&oacute;n de servicios.">
													<input type="button" onclick="uid_call('imss.patrones.alta_patronal.clasificacion.personal.agregar','clickin');dialogoEdicion('personal', 'Agregar');" class="btn btn-primary btn-sm"
														value="Agregar" id="btnAgregarPersonal">
												</span>
											</form>
										</div>

									</td>
								</tr>
							</tfoot>
						</table>
						<span class="error" id="gridPersonalError"></span>
					</div>
				</div>


				<div id="seccionActividades" class="col-sm-12">
					<div class="row">
						<div class="separadorseccion">
							<span>
								<spring:message code="label.actividades.complementarias" />
								<a class='icono-help delta-tooltip btn btn-xs' data-toggle='tooltip' title="Se consideran todas aquellas adicionales a la actividad principal que realiza una empresa o negocio para brindar a los clientes una mejor atenci&oacute;n o servicio, o como complemento a los productos o servicios que ofrece a los clientes."></a>
							</span>
						</div>
						<table style="width:100%">
								<tr>
									<td style="width: 50%;">
										<spring:message code="label.distribuidor.entrega" />
										<br>
										<table style="width:100%;padding-left: 55px;">
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
																<input type="checkbox" id="indTransporteAjenoTemp" name="indTransporteAjenoTemp" checked="true" class="ns">
																<spring:message code="label.transporte.ajeno" />
															</c:if>
															<c:if test="${sujetoTramite.clasificacion.indTransporteAjeno != 1}">
																<input type="checkbox" id="indTransporteAjenoTemp" name="indTransporteAjenoTemp" class="ns">
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
									<td valign="top" style="width: 50%;">
											<c:if test="${sujetoTramite.clasificacion.indServiciosATerceros == 1}">
												<input type="checkbox" id="indServiciosTercerosTemp" name="indServiciosTercerosTemp" checked="true" class="ns_">
												<u>
													<spring:message code="label.servicios.terceros" />
													<a class='icono-help delta-tooltip btn btn-xs' data-toggle='tooltip' title="Seleccione este campo si cuenta con servicios de instalaci&oacute;n, reparaci&oacute;n o mantenimiento de los productos que elabora o vende."></a>

												</u>
											</c:if>
											<c:if test="${sujetoTramite.clasificacion.indServiciosATerceros != 1}">
												<input type="checkbox" id="indServiciosTercerosTemp" name="indServiciosTercerosTemp" class="ns_">
												<u>
													<spring:message code="label.servicios.terceros" />
													<a class='icono-help delta-tooltip btn btn-xs' data-toggle='tooltip' title="Seleccione este campo si cuenta con servicios de instalaci&oacute;n, reparaci&oacute;n o mantenimiento de los productos que elabora o vende."></a>
												</u>
											</c:if>
									</td>
								</tr>
							</table>
					</div>
				</div>


				<div id="seccionBienesInmuebles" class="col-sm-12">
					<div class="row">
						<div class="separadorseccion">
							<span>

								<spring:message code="label.bienes.inmuebles" /><span class="required">*</span>
							</span>
						</div>

						<table id="gridBienes" style="vertical-align: top; width: 100%; max-width: 830px;" cellpadding="0" cellspacing="0"
							class="table table-striped table-bordered">
							<thead></thead>
							<tbody style="width: 100%;">
							</tbody>
							<tfoot>
								<tr>
									<td></td>
									<td colspan="5">
										<div class="opciones">
											<form>

												<input type="button" onclick="uid_call('imss.patrones.alta_patronal.clasificacion.bienes.modificar','clickin');dialogoEdicion('bienes', 'Modificar');" class="btn btn-default btn-sm"
													value="Modificar">
												<input type="button" onclick="uid_call('imss.patrones.alta_patronal.clasificacion.bienes.eliminar','clickin');dialogoEdicion('bienes', 'Eliminar');" class="btn btn-danger btn-sm"
													value="Eliminar">
												<span class="delta-tooltip" data-toggle="tooltip"
													title="Cantidad y descripci&oacute;n de los bienes. Describa el tipo de bien o bienes que originan la modificaci&oacute;n que presenta ante el Instituto e indique la cantidad de los mismos.">
													<input type="button" onclick="uid_call('imss.patrones.alta_patronal.clasificacion.bienes.agregar','clickin');dialogoEdicion('bienes', 'Agregar');" class="btn btn-primary btn-sm"
														value="Agregar" id="btnAgregarBienes">
												</span>
											</form>
										</div>
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
												<h6 style="font-size: 1.0em !important;">

													<span class="delta-tooltip" data-toggle="tooltip"
														title="Indique brevemente para que es o ser&aacute; utilizado el bien o bienes que originan la modificaci&oacute;n presentada ante el Instituto.">
														<label>
															<spring:message code="label.bienes.uso" /><span class="required">*</span>:
														</label>
													</span>
												</h6>
												<div style="width: 100%; height: 150px;">
													<textarea class="textClasificacion ns_" style="width: 98%;" rows="7" id="afectacionBienes"
														onkeypress="validaSize(this, 600, event)" onkeyup="validaSize(this, 600, event)">${sujetoTramite.desUsosBienes}</textarea>
												</div>
											</div>

											<div style="display: table-row;" id="proceso-inicial">
												<h6 style="font-size: 1.0em !important;">

													<span class="delta-tooltip" data-toggle="tooltip"
														title="Afectaci&oacute;n directa o indirecta al desarrollo de la actividad econ&oacute;mica. Describa brevemente, c&oacute;mo el bien o los bienes objeto de la compra, enajenaci&oacuten, arrendamiento, comodato o fideicomiso traslativo, afectan directa o indirectamente, en el desarrollo de las actividades de la empresa o negocio.">
														<label>
															<spring:message code="label.bienes.afectacion" /><span class="required">*</span>:
														</label>
													</span>
												</h6>
												<div style="width: 100%; height: 150px;">
													<textarea class="textClasificacion ns_" style="width: 98%;" rows="7" id="usoBienes"
														onkeypress="validaSize(this, 600, event)" onkeyup="validaSize(this, 300, event)">${sujetoTramite.desAfectacion}</textarea>
												</div>
											</div>

										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>

				<div id="dgErrorSinSeleccion" title="Debe seleccionar un elemento">
					<p style="float: left; margin: 10 10px 10px 10;">
						<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"> </span>
						No se ha seleccionado ning&uacute;n registro para ejecutar esta acci&oacute;n.
					</p>
				</div>
			</c:if>
		</div>
	</div>


	<div class="pie row">
		<div class="col-sm-4">
			<div style="float: left; padding: 11px 0px;"><span class="required" id="labelCamposObligatoriosGeneral">*</span><spring:message code="label.camposObligatorios"/></div>
		</div>
		<div class="col-sm-8">
			<div class="pull-right">
			<c:if test="${empty solicitudTramite.errorFormGeneral}">
				<a id="previoPaso" class="btn btn-default" onclick="uid_call('imss.patrones.alta_patronal.clasificacion.regresar','clickin');">
						<i class="glyphicon glyphicon-step-backward"></i>
						Anterior
					</a>
					<a id="siguientePaso" class="btn btn-primary" onclick="uid_call('imss.patrones.alta_patronal.clasificacion.siguiente','clickin');">
						<i class="glyphicon glyphicon-step-forward"></i>
						Siguiente
					</a>
				<div class="btn-group dropup">
					<a href="#" class="btn btn-primary">
						<spring:message code="label.menus.opciones" />
					</a>
					<a href="#" data-toggle="dropdown" class="btn btn-primary dropdown-toggle">
						<span class="caret"></span>
					</a>
					<ul class="dropdown-menu pull-right">
						<li>
							<a id="guardarTramite" onclick="uid_call('imss.patrones.alta_patronal.clasificacion.link_guardar','clickin');">
								<i class="glyphicon glyphicon-download-alt"></i>
								<spring:message code="wizard.button.guardarTramite"/>
							</a>
						</li>
						<li>
							<a id="guardarCerrarTramite" onclick="uid_call('imss.patrones.alta_patronal.clasificacion.link_guardarCerrar','clickin');">
								<i class="glyphicon glyphicon-download-alt"></i>
								<spring:message code="wizard.button.guardarCerrar"/>
							</a>
						</li>
						<li>
							<a id="cancelarTramite" onclick="uid_call('imss.patrones.alta_patronal.clasificacion.link_cancelar','clickin');">
								<i class="glyphicon glyphicon-trash"></i>
								<spring:message code="wizard.button.cancelarTramite"/>
							</a>
						</li>
					</ul>
				</div>
			</c:if>
			</div>
		</div>
	</div>
</div>

<!-- Forma para invocar al servicio del Modificación Manual de Datos -->
<form id="datosClasificacionForm" method="post"></form>

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
		¿Desea cancelar la solicitud pendiente con folio:
		<strong>${folioSolicitud}</strong>
		?
	</p>
</div>

<div id="dialog-confirm" title="Mensaje">
	<p>
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
		<label id="mensajeDialogo"></label>
	</p>
</div>

<!-- Divs para dialogos de mensajes -->

<div id="dialogoGrids">
	<form id="formaDialogo"></form>
</div>

<div id="dialogoConfirmacion">
	<p>
		<span id="textoConfirmacion"></span>
	</p>
</div>

<div id="dialogoMensajes">
	<p><span id="textoMensaje"></span></p>
</div>

<script language="JavaScript1.2" src="${staticResourcesPath}/js/comscore/Form.js"></script>