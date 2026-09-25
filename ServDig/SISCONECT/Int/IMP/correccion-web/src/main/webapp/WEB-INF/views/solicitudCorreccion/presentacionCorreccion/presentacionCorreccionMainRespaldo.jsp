<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
	<HEAD>
		<meta http-equiv="content-type" content="text/html; charset=ISO-8859-1"/>
	</HEAD>
	
	<style>
		label.error { float: none; font-size:smaller; color: red; padding-left: .5em; vertical-align: top; }
		label  {  float: none; }
	</style>

	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/correccion/presentacion/presentacionCorreccion.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/solicitud/FirmaDigital.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
	
	<form:form modelAttribute="presentacionCorreccionModel" action="/correccion/presentacion/presentar.do">
		<input type="hidden" id="idContexto" value="<%=request.getContextPath()%>">
		<div id="contenedorFirmaPresentacion"></div>
		<form:hidden path="tipoDeCorreccionHidden" id="tipoDeCorreccionHidden"/>
		<form:hidden path="cveSolicitudCorreccion" id="cveSolicitudCorreccion"/>
		
		<form:hidden path="fechaCadenaOriginal" id="fechaCadenaOriginal"/>
		<form:hidden path="procedencia" id="procedencia"/>
		<form:hidden path="idSubDelegacion" id="idSubDelegacion"/>
		<form:hidden path="cveDelegacion" id="cveDelegacion"/>
		<form:hidden path="cveSubDelegacion" id="cveSubDelegacion"/>
		<form:hidden path="firmaElectronica" id="firmaElectronica"/>
		<form:hidden path="cadenaOriginal" id="cadenaOriginal"/>
		<form:hidden path="idTipoDeSolicitud" id="idTipoDeSolicitud"/>
		<form:hidden path="cveNroRegObra" id="cveNroRegObra"/>
		
		<div id="cuerpo">
			<div class="separadorseccion">
				<span>
					Presentaci&oacute;n de la correcci&oacute;n
				</span>
			</div>
			<div id="dialog-error" title="Mensaje de SISCONET">
				<p>
					<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>
					Cargado applet para firma digital
				</p>
			</div>
		  
			<div id="headerCorreccionDialog">
				<div id="filtrosBusquedaDiv" title="Buscar Solicitudes de Corrección">
					<fieldset>
						<table>
							<tr>
								<td>
									<table style="border-collapse: separate; border-spacing:  5px 5px;">
										<tbody>
											<tr>
												<td width="160px">
													<form:label path="folioSoicitudCorreccionFiltro">Folio de correcci&oacute;n: </form:label>
												</td>
												<td width="342px">
													<form:input onkeypress="mayusculasTextField(this)" path="folioSoicitudCorreccionFiltro"/>
												</td>
												<td>
													<a id="btnBuscaSolicitudes" href="#" onclick="javascript:buscarSolicitudesDeCorreccion();">
													<span class="btn btn-primary btn-sm">Buscar</span></a>
												</td>
											</tr>
										</tbody>
									</table>
									<br>
									<div id="wrapperDataTableFoliosSolicitudCorreccion">
										<table id="tableFoliosSolicitudCorreccion" style="width: 960px" class="table table-striped table-bordered">
										</table>
									</div><br>
								</td>
							</tr>
						</table>
					</fieldset>
				</div>
			</div>
			<div id="presentacionMainDialog">
				<div id="folioSolicitudSeleccionadoDivID">
					<fieldset>
						<table>
							<tr>
								<td>
									<table style="border-collapse: separate; border-spacing:  5px 5px;">
										<tbody>
											<tr>
												<td width="210px">
													<label>Folio correcci&oacute;n seleccionado: </label>
												</td>
												<td width="160px">
													<label for="folioSolicitudSeleccionado" id="folioSolicitudSeleccionadoLabelID"></label>
												</td>
												<td>
											  		<a onclick="javascript:mostrarSolicitudesDeCorreccion();"><span class="btn btn-primary btn-sm">Seleccionar otro folio de correcci&oacute;n</span></a>
											  	</td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
						</table>
					</fieldset>
				</div>
				<div id="wrapperDialog">
					<fieldset>
						<table>
							<tr>
								<td>
									<table style="border-collapse: separate; border-spacing:  5px 5px;">
										<tbody>
											<tr>
												<td colspan="4">
													<div class="separadorseccion">
														<span>
															Datos registro patronal a corregir
														</span>
													</div>
												</td>
											</tr>
											<tr>
												<td>
													<form:label for="folioCorreccion" id="folioCorreccionLabelID" path="folioCorreccion" cssErrorClass="error">Folio de correcci&oacute;n: </form:label>
												</td>
												<td>
													<form:input path="folioCorreccion" id="folioCorreccionInputID" size="25" maxlength="25" readonly="true"/>
													<form:errors path="folioCorreccion" />
												</td>
												<td>
													<!-- Nombre Denominacion o razon social -->
													<form:label for="razonSocial" id="razonSocialLabelID" path="razonSocial" cssErrorClass="error">Nombre, denominaci&oacute;n o raz&oacute;n social: </form:label>
												</td>
												<td>
													<form:input path="razonSocial" id="razonSocialID" size="50" maxlength="200" readonly="true"/>
													<form:errors path="razonSocial" />
												</td>
											</tr>
											<tr>
												<td>
													<form:label for="numeroRegistroPatronal" id="numeroRegistroPatronalLabelID" path="numeroRegistroPatronal" cssErrorClass="error">N&uacute;mero de registro patronal: </form:label>
												</td>
												<td>
													<form:input path="numeroRegistroPatronal" readonly="true" id="numeroRegistroPatronalInputID" size="25" maxlength="12"/>
													<form:errors path="numeroRegistroPatronal" />
												</td>
												<td>
													<form:label for="digitoVerificador" id="digitoVerificadorLabelID" path="digitoVerificador" cssErrorClass="error">D&iacute;gito verificador: </form:label>
												</td>
												<td>
													<form:input path="digitoVerificador" id="digitoVerificadorInputID" readonly="true" size="50" maxlength="1"/>
													<form:errors path="digitoVerificador" />
												</td>
											</tr>
											<tr>
												<td>
													<form:label for="curp" id="curpLabelID" path="curp" cssErrorClass="error">Clave &Uacute;nica de Registro de Poblaci&oacute;n: </form:label>
												</td>
												<td>
													<form:input path="curp" id="curpInputID" size="25" maxlength="21" readonly="true"/>
													<form:errors path="curp" />
												</td>
												<td>
													<form:label for="rfc" id="rfcLabelID" path="rfc" cssErrorClass="error">Registro Federal de Contribuyentes: </form:label>
												</td>
												<td>
													<form:input path="rfc" id="rfcInputID" size="50" maxlength="15" readonly="true"/>
													<form:errors path="rfc" />
												</td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
						</table>
					</fieldset>
				</div>
			
				<div id="domicilioFiscalDiv" title="Domicilio Fiscal">
					<fieldset>
						<table>
							<tr>
								<td>
									<table style="border-collapse: separate; border-spacing:  5px 5px;">
										<tbody>
											<tr>
												<td colspan="4">
													<div class="separadorseccion">
														<span>
															Domicilio fiscal
														</span>
													</div>
												</td>
											</tr>
											<tr>
												<td>
													<form:label path="registroPatronalFiscal" id="registroPatronalFiscalLabelID" cssErrorClass="error">Registro patronal: </form:label>
												</td>
												<td colspan="3">
													<form:input size="123" path="registroPatronalFiscal" id="registroPatronalFiscalInputID" readonly="true"/>
												</td>
											</tr>
											<tr>
												<td>
													<form:label path="calle" id="calleLabelID" cssErrorClass="error">Calle: </form:label>
												</td>
												<td colspan="3">
													<form:input size="123" path="calle" id="calleInputID" readonly="true"/>
												</td>
											</tr>
											<tr>
												<td>
													<form:label path="numExterior" id="numExteriorLabelID" cssErrorClass="error">N&uacute;mero exterior: </form:label>
												</td>
												<td>
													<form:input size="50" path="numExterior" id="numExteriorInputID" readonly="true"/>
												</td>
												<td>
													<form:label path="numInterior" id="numInteriorLabelID" cssErrorClass="error">N&uacute;mero interior: </form:label>
												</td>
												<td>
													<form:input size="50" path="numInterior" id="numInteriorInputID" readonly="true"/>
												</td>
											</tr>
											<tr>
												<td>
													<form:label path="colonia" id="coloniaLabelID" cssErrorClass="error">Colonia: </form:label>
												</td>
												<td>
													<form:input size="50" path="colonia" id="coloniaInputID" readonly="true"/>
												</td>
												<td>
													<form:label path="municipio" id="municipioLabelID" cssErrorClass="error">Municipio / Delegaci&oacute;n: </form:label>
												</td>
												<td>
													<form:input size="50" path="municipio" id="municipioInputID"  readonly="true"/>
												</td>
											</tr>
											<tr>
												<td>
													<form:label path="localidad" id="localidadLabelID" cssErrorClass="error">Localidad: </form:label>
												</td>
												<td>
													<form:input size="50" path="localidad" id="localidadInputID" readonly="true"/>
												</td>
												<td>
													<form:label path="entidadFederativa" id="entidadFederativaLabelID" cssErrorClass="error">Entidad federativa: </form:label>
												</td>
												<td>
													<form:input size="50" path="entidadFederativa" id="entidadFederativaInputID" readonly="true"/>
												</td>
											</tr>
											<tr>
												<td>
													<form:label path="codigoPostal" id="codigoPostalLabelID" cssErrorClass="error">C&oacute;digo Postal: </form:label>
												</td>
												<td>
													<form:input size="50" path="codigoPostal" id="codigoPostalInputID" readonly="true"/>
												</td>
												<td>
													<form:label path="telefono" id="telefonoLabelID" cssErrorClass="error">Tel&eacute;fono: </form:label>
												</td>
												<td>
													<form:input size="50" path="telefono" id="telefonoInputID" readonly="true"/>
												</td>
											</tr>
											<tr>
												<td>
													<form:label path="email" id="emailLabelID" cssErrorClass="error">Correo electr&oacute;nico: </form:label>
												</td>
												<td colspan="3">
													<form:input path="email" size="123" id="emailInputID" readonly="true" />
												</td>										
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
						</table>
					</fieldset>
				</div>
				
				<div id="domicilioCentroTrabajoDiv" style="background-color: #f2fff2;" title="Domicilio Fiscal">
					<fieldset>
						<table style="width: 900px" align="center">
							<tr valign="middle">
								<td align="center" width="900px">
									<table class="tablaverde2" style="width: 900px">
										<tbody>
											<tr valign="top" class="impar">
												<td align="center" colspan="4"><b>Domicilio centro de trabajo</b></td>
											</tr>
											
											<tr valign="top" class="par">
												<td align="left" width="20%">
													<form:label path="registroPatronalCentroTrabajo" id="registroPatronalCentroTrabajoLabelID" cssErrorClass="error">Registro patronal: </form:label>
												</td>
												<td align="left" colspan="3">
													<form:input size="120" path="registroPatronalCentroTrabajo" id="registroPatronalCentroTrabajoInputID" readonly="true"/>
												</td>
											</tr>
											
											<tr valign="top" class="par">
												<td align="left" width="20%">
													<form:label path="calleCentroTrabajo" id="calleCentroTrabajoLabelID" cssErrorClass="error">Calle: </form:label>
												</td>
												<td align="left" colspan="3">
													<form:input size="120" path="calleCentroTrabajo" id="calleCentroTrabajoInputID" readonly="true"/>
												</td>
											</tr>
											
											<tr valign="top" class="par">
												<td align="left" width="25%">
													<form:label path="numExteriorCentroTrabajo" id="numExteriorCentroTrabajoLabelID" cssErrorClass="error">Número Exterior: </form:label>
												</td>
												<td align="left" width="25%">
													<form:input size="10" path="numExteriorCentroTrabajo" id="numExteriorCentroTrabajoInputID" readonly="true"/>
												</td>
												<td align="left" width="20%">
													<form:label path="numInteriorCentroTrabajo" id="numInteriorCentroTrabajoLabelID" cssErrorClass="error">N&uacute;mero interior: </form:label>
												</td>
												<td align="left" width="30%">
													<form:input size="10" path="numInteriorCentroTrabajo" id="numInteriorCentroTrabajoInputID" readonly="true"/>
												</td>
											</tr>
											<tr>
												<td align="left" width="20%">
													<form:label path="coloniaCentroTrabajo" id="coloniaCentroTrabajoLabelID" cssErrorClass="error">Colonia: </form:label>
												</td>
												<td align="left" width="30%">
													<form:input size="50" path="coloniaCentroTrabajo" id="coloniaCentroTrabajoInputID" readonly="true"/>
												</td>
												<td align="left" width="25%">
													<form:label path="municipioCentroTrabajo" id="municipioCentroTrabajoLabelID" cssErrorClass="error">Municipio / Delegaci&oacute;n: </form:label>
												</td>
												<td align="left" width="25%">
													<form:input size="50" path="municipioCentroTrabajo" id="municipioCentroTrabajoInputID"  readonly="true"/>
												</td>
											</tr>
											<tr>
												<td align="left" width="20%">
													<form:label path="localidadCentroTrabajo" id="localidadCentroTrabajoLabelID" cssErrorClass="error">Localidad: </form:label>
												</td>
												<td align="left" width="30%">
													<form:input size="50" path="localidadCentroTrabajo" id="localidadCentroTrabajoInputID" readonly="true"/>
												</td>
												<td align="left" width="25%">
													<form:label path="entidadFederativaCentroTrabajo" id="entidadFederativaCentroTrabajoLabelID" cssErrorClass="error">Entidad federativa: </form:label>
												</td>
												<td align="left" width="25%">
													<form:input size="50" path="entidadFederativaCentroTrabajo" id="entidadFederativaCentroTrabajoInputID" readonly="true"/>
												</td>
											</tr>
											<tr>
												<td align="left" width="20%">
													<form:label path="codigoPostalCentroTrabajo" id="codigoPostalCentroTrabajoLabelID" cssErrorClass="error">C&oacute;digo Postal: </form:label>
												</td>
												<td align="left" width="30%">
													<form:input size="6" path="codigoPostalCentroTrabajo" id="codigoPostalCentroTrabajoInputID" readonly="true"/>
												</td>
												<td align="left" width="25%">
													&nbsp;
												</td>
												<td align="left" width="25%">
													&nbsp;
												</td>
											</tr>
											<tr>
												<td align="left" width="20%" colspan="1">
													<form:label path="actividadCentroTrabajo" id="actividadCentroTrabajoLabelID" cssErrorClass="error">Actividad: </form:label>
												</td>
												<td align="left" width="30%" colspan="3">
													<form:input path="actividadCentroTrabajo" size="100" id="actividadCentroTrabajoInputID" readonly="true"/>
												</td>										
											</tr>
											<tr>
												<td align="left" width="20%">
													<form:label path="claseCentroTrabajo" id="claseCentroTrabajoLabelID" cssErrorClass="error">Clase: </form:label>
												</td>
												<td align="left" width="30%">
													<form:input path="claseCentroTrabajo" size="20" id="claseCentroTrabajoInputID" readonly="true"/>
												</td>										
												<td align="left" width="20%">
													<form:label path="email" id="fraccionCentroTrabajoLabelID" cssErrorClass="error">Fracci&oacute;n: </form:label>
												</td>
												<td align="left" width="30%">
													<form:input path="fraccionCentroTrabajo" size="20" id="fraccionCentroTrabajoInputID" readonly="true"/>
												</td>										
												
											</tr>
											<tr>
												<td align="left" width="20%">
													<form:label path="primaCentroTrabajo" id="primaCentroTrabajoLabelID" cssErrorClass="error">Prima: </form:label>
												</td>
												<td align="left" width="30%">
													<form:input path="primaCentroTrabajo" size="20" id="primaCentroTrabajoInputID" readonly="true"/>
												</td>										
												<td align="left" width="20%">
													&nbsp;
												</td>
												<td align="left" width="30%">
													&nbsp;
												</td>										
												
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
						</table>
					</fieldset>
				</div>
				<div id="domicilioObralDiv" style="background-color: #f2fff2;" title="Domicilio Obra">
					<fieldset>
						<table style="width: 900px" align="center">
							<tr valign="middle">
								<td align="center" width="900px">
									<table class="tablaverde2" style="width: 900px">
										<tbody>
											<tr valign="top" class="impar">
												<td align="center" colspan="4"><b>Domicilio de la obra</b></td>
											</tr>
											
											
											<tr valign="top" class="par">
												<td align="left" width="20%">
													<form:label path="calleObra" id="calleObraLabelID" cssErrorClass="error">Calle: </form:label>
												</td>
												<td align="left" colspan="3">
													<form:input size="120" path="calleObra" id="calleObraInputID" readonly="true"/>
												</td>
											</tr>
											
											<tr valign="top" class="par">
												<td align="left" width="25%">
													<form:label path="numExteriorObra" id="numExteriorObraLabelID" cssErrorClass="error">N&uacute;mero exterior: </form:label>
												</td>
												<td align="left" width="25%">
													<form:input size="10" path="numExteriorObra" id="numExteriorObraInputID" readonly="true"/>
												</td>
												<td align="left" width="20%">
													<form:label path="numInteriorObra" id="numInteriorObraLabelID" cssErrorClass="error">N&uacute;mero interior: </form:label>
												</td>
												<td align="left" width="30%">
													<form:input size="10" path="numInteriorObra" id="numInteriorObraInputID" readonly="true"/>
												</td>
											</tr>
											<tr>
												<td align="left" width="20%">
													<form:label path="coloniaObra" id="coloniaObraLabelID" cssErrorClass="error">Colonia: </form:label>
												</td>
												<td align="left" width="30%">
													<form:input size="50" path="coloniaObra" id="coloniaObraInputID" readonly="true"/>
												</td>
												<td align="left" width="25%">
													<form:label path="municipioObra" id="municipioObraLabelID" cssErrorClass="error">Municipio / Delegaci&oacute;n: </form:label>
												</td>
												<td align="left" width="25%">
													<form:input size="50" path="municipioObra" id="municipioObraInputID"  readonly="true"/>
												</td>
											</tr>
											<tr>
												<td align="left" width="20%">
													<form:label path="localidadObra" id="localidadObraLabelID" cssErrorClass="error">Localidad: </form:label>
												</td>
												<td align="left" width="30%">
													<form:input size="50" path="localidadObra" id="localidadObraInputID" readonly="true"/>
												</td>
												<td align="left" width="25%">
													<form:label path="entidadFederativaObra" id="entidadFederativaObraLabelID" cssErrorClass="error">Entidad federativa: </form:label>
												</td>
												<td align="left" width="25%">
													<form:input size="50" path="entidadFederativaObra" id="entidadFederativaObraInputID" readonly="true"/>
												</td>
											</tr>
											<tr>
												<td align="left" width="20%">
													<form:label path="codigoPostalObra" id="codigoPostalObraLabelID" cssErrorClass="error">C&oacute;digo Postal: </form:label>
												</td>
												<td align="left" width="30%">
													<form:input size="6" path="codigoPostalObra" id="codigoPostalObraInputID" readonly="true"/>
												</td>
												<td align="left" width="25%">
													&nbsp;
												</td>
												<td align="left" width="25%">
													&nbsp;
												</td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
						</table>
					</fieldset>
				</div>
			
				<div id="FechasSolicitudCorreccion" title="Fechas de la Corrección">
					<fieldset>
						<table>
							<tr>
								<td>
									<table style="border-collapse: separate; border-spacing:  5px 5px;">
										<tbody>
											<tr>
												<td colspan="6">
													<div class="separadorseccion">
														<span>
															Fechas de la correcci&oacute;n
														</span>
													</div>
												</td>
											</tr>
											<tr>
												<td>
													<form:radiobutton disabled="true" path="tipoDeCorreccion" value="1" label="Corrección Espontanea" id="radioEspontanea"/>
												</td>
												<td>
													<form:label id="fechaAutorizacionCorreccionEspontaneaLabelID" path="fechaAutorizacionCorreccionEspontanea" cssErrorClass="error">Fecha de autorizaci&oacute;n: </form:label>
												</td>
												<td>
													<form:input path="fechaAutorizacionCorreccionEspontanea" readonly="true"/>
													<form:errors path="fechaAutorizacionCorreccionEspontanea" />
												</td>
												<td>
													<form:radiobutton disabled="true" path="tipoDeCorreccion" value="2" label="Corrección por Invitación" id="radioInvitacion"/>
												</td>
												<td>
													<form:label id="fechaAceptacionInvitacionCorreccionLabelID" path="fechaAceptacionInvitacionCorreccion" cssErrorClass="error">Fecha de aceptaci&oacute;n: </form:label>
												</td>
												<td>
													<form:input path="fechaAceptacionInvitacionCorreccion" readonly="true" />
													<form:errors path="fechaAceptacionInvitacionCorreccion" />
												</td>
											</tr>
											<tr>
												<td>
													<form:label path="fechaEjercicioInicial" id="fechaEjercicioInicialLabelID">Ejercicio / periodo regularizado: </form:label>
												</td>
												<td>
													<form:input path="fechaEjercicioInicial" readonly="true" />
													<form:errors path="fechaEjercicioInicial" />
												</td>
												<td align="center">
													<form:label path="fechaEjercicioInicial" id="fechaEjercicioInicialLabelALID">AL</form:label>
												</td>
												<td>
													<form:input path="fechaEjercicioFinal" readonly="true"/>
													<form:errors path="fechaEjercicioFinal" />
												</td>
												<td>
													<form:label path="fechaProrroga" id="fechaProrrogaLabelID">Pr&oacute;rroga: </form:label>
												</td>
												<td>
													<form:input path="fechaProrroga" readonly="true"/>
													<form:errors path="fechaProrroga" />
												</td>
											</tr>
											<tr>
												<td colspan="2">
													<form:label path="numeroTrabajadores" id="numeroTrabajadoresLabelID" cssErrorClass="error">N&uacute;mero de trabajadores regularizados: </form:label>
												</td>
												<td>
													<form:input path="numeroTrabajadores" onkeyup="validaCampo('PermiteSoloNumeros','numeroTrabajadores','presentacionCorreccionModel')" readonly="true"/>
													<form:errors path="numeroTrabajadores"></form:errors>
												</td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
						</table>
					</fieldset>
				</div>
			
				<div id="CopPagadasAnexoDiv" style="background-color: #f2fff2;" title="Total de C.O.P pagadas en la corrección">
				</div>
				<div id="registrosPatronalesInscritos" style="border:solid 1px;border-color:black; width:900px; height:350;">
					<table id="dtPatronesInscritos" style="width: 900px">
								<thead>
								</thead>
								<tbody>
								</tbody>
							</table>
				</div>
				<div id="documentacionQuePresentaDiv" style="background-color: #f2fff2;" title="Documentación que presenta">
					<fieldset>
						<form:hidden path="chkCombtPago" id="chkCombtPago" value="false"/>
						<form:hidden path="chkCombtAvisosAfil" id="chkCombtAvisosAfil" value="false"/>
						<form:hidden path="chkDocumentacionCorreccion" id="chkDocumentacionCorreccion" value="false"/>
					</fieldset>
				</div>
				
				<div id="firmasDiv" title="Para uso exclusivo del IMSS">
					<fieldset>
						<table>
							<tr>
								<td>
									<table style="border-collapse: separate; border-spacing:  5px 5px;">
										<tr>
											<td colspan="6">
												<div class="separadorseccion">
													<span>
														Observaciones
													</span>
												</div>
											</td>
										</tr>
										<!--  Comenntario de prueba-->
										<tr>
											<td colspan="6">
												<textarea  id="observacionesPresentacion"  rows="8" cols="130"></textarea>
											</td>
										</tr>
										<tr>
											<td colspan="6">
												<div class="separadorseccion">
													<span>
														Para uso exclusivo del IMSS
													</span>
												</div>
											</td>
										</tr>
										<tr>
											<td colspan="6">
												<!--<form:label path="labelManifiesto" style="text-align:justify">
													MANIFIESTO  BAJO PROTESTA DE DECIR VERDAD QUE LA INFORMACIÓN Y DOCUMENTACIÓN PRESENTADA EN ESTA CORRECCIÓN ES CIERTA, DETERMINÁNDOSE CON 
													ESTRICTO APEGO A LA LEY DEL SEGURO SOCIAL Y SUS REGLAMENTOS, LA  QUE SE PRESENTA ANTE EL IMSS, EN LOS TÉRMINOS DEL ARTÍCULO 180 DEL REGLAMENTO DE 
													LA LEY DEL SEGURO SOCIAL EN MATERIA DE AFILIACIÓN, CLASIFICACIÓN DE EMPRESAS, RECAUDACIÓN Y FISCALIZACIÓN
												</form:label>
												-->
														<form:label path="labelManifiesto" style="text-align:justify">
												Manifiesto bajo protesta de decir verdad que la informaci&oacute;n y documentaci&oacute;n presentada en esta correcci&oacute;n es cierta,determin&aacute;ndose con
												estricto apego a la ley del seguro social y sus reglamentos, la que se presenta ante el IMSS, en los t&eacute;rminos del art&iacute;culo 180 del Reglamento 
												de la Ley del Seguro Social en Materia de Afiliaci&oacute;n, Clasificaci&oacute;n de Empresas, Recaudaci&oacute;n y Fiscalizaci&oacute;n.											</form:label>
												<br/><br/>
											</td>
										</tr>
										<tr>
											<td width="323px">
												<span class="required">*</span><label>Nombre y firma del patr&oacute;n o representante legal:</label>
											</td>
											<td>
												<form:input path="nombreYFirmaPatron" onkeyup="validaCampo('PermiteSoloLetrasYPunto','nombreYFirmaPatron','presentacionCorreccionModel')"/>
											</td>
											<td>
												<span class="required">*</span><label>Lugar:</label>
											</td>
											<td>
												<form:input path="lugar" onkeyup="validaCampo('PermiteSoloLetrasYPunto','lugar','presentacionCorreccionModel')"/>
											</td>
											<td>
												<label>Fecha:</label>
											</td>
											<td>
												<form:input path="fechaFirma" readonly="true"/>
											</td>
										</tr>
										<tr>
	  										<td align="right" colspan="5"></td>
											<td>
												<label>Dia/Mes/Año</label>
											</td>
										</tr>
										<tr>
											<td align="left">
											* Campos obligatorios
											</td>
											<td align="center" colspan="5">
											  	<a href="#abajo" onclick="javascript:registrarFirmado();"><span class="btn btn-primary btn-sm">Presentar solicitud de correcci&oacute;n</span></a>
											  	<a name="abajo"></a>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</fieldset>
				</div>
			</div>
		</div>
	</form:form>
</html>