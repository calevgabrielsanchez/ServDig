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

	<link href="https://framework-gb.cdn.gob.mx/assets/styles/main.css" rel="stylesheet">	
		
	<script type="text/javascript" src="https://framework-gb.cdn.gob.mx/data/encuesta_v1.0/qa/encuestas.js"> </script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/delta/correccion/presentacion/presentacionCorreccion.js"></script>
	<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/delta/solicitud/FirmaDigital.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
	
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
		<div class="menu_principal" style="height: 2em !important;">
			<div align="center">
				<div class="centrado">
					<ul style="height: 1em !important;">
						<li><a>Presentaci&oacute;n de la Correcci&oacute;n</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div id="dialog-error" title="Mensaje de SISCONET">
			<p>
				<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>
				Cargado Applet para firma digital
			</p>
			
		</div>
		 
		<div id="headerCorreccionDialog"  style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity = 70) !important;">
		
		<div id="filtrosBusquedaDiv" style="background-color: #f2fff2;" title="Buscar Solicitudes de Corrección">
				<fieldset>
					<table style="width: 900px" align="center">
						<tr valign="middle">
							<td align="center" width="900px">
								<table class="tablaverde2" style="width: 900px">
									<tbody>
										<tr valign="top" class="impar">
											<td align="center" colspan="4"><b>Buscar Solicitudes de Corrección</b></td>
										</tr>
										<tr>
											<td width="25%" align="left" class="etiqueta2"><form:label path="folioSoicitudCorreccionFiltro">Folio de Corrección: </form:label></td>
											<td width="25%" align="left"><form:input onkeypress="mayusculasTextField(this)" path="folioSoicitudCorreccionFiltro"/></td>
											<td width="25%">&nbsp;</td>
											<td width="25%">&nbsp;</td>
										</tr>
										<tr><td colspan="4">&nbsp;</td></tr>
										<tr>
											<td align="center" width="100%" colspan="4">
										  	<a id="btnBuscaSolicitudes" href="#" onclick="javascript:buscarSolicitudesDeCorreccion();"><span class="boton">Buscar</span></a>
										  </td>
										</tr>
										<tr><td colspan="4">&nbsp;</td></tr>
									</tbody>
								</table>
								<div id="wrapperDataTableFoliosSolicitudCorreccion" style="overflow: auto; width:950px; height:300px;" align="center" class="centrado">
									<table id="tableFoliosSolicitudCorreccion"  style="width: 900px" align="center">
									</table>
								</div><br>
							</td>
						</tr>
					</table>
				</fieldset>
			</div>
		</div>
		<br><br>
		<div id="menuPresentacionCorreccionID" class="menu_principal" style="height: 2em !important;">
			<div align="center">
				<div class="centrado">
					<ul style="height: 1em !important;">
						<li><a>Presentaci&oacute;n de la Correcci&oacute;n</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div id="presentacionMainDialog"  style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity = 70) !important;">
			<div id="folioSolicitudSeleccionadoDivID" style="background-color: #f2fff2;">
				<fieldset>
					<table style="width: 900px" align="center">
						<tr valign="middle">
							<td align="center" width="900px">
								<table class="tablaverde2" style="width: 900px">
									<tbody>
										<tr>
											<td align="left" width="25%">Folio Corrección Seleccionado: </td>
											<td align="left" width="10%"><label for="folioSolicitudSeleccionado" id="folioSolicitudSeleccionadoLabelID"></label></td>
											<td align="center" width="35%">
										  		<a onclick="javascript:mostrarSolicitudesDeCorreccion();"><span class="boton">Seleccionar otro Folio de Corrección</span></a>
										  	</td>
											<td align="left" width="30%">&nbsp;</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</table>
				</fieldset>
			</div>
			<div id="wrapperDialog" style="background-color: #f2fff2;">
				<fieldset>
					<table style="width: 900px" align="center">
						<tr valign="middle">
							<td align="center" width="900px">
								<table class="tablaverde2" style="width: 900px">
									<tbody>
										<tr valign="top" class="impar">
											<td align="center" colspan="4"><b>Datos Registro Patronal a Corregir</b></td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="100px"><form:label
													for="folioCorreccion" id="folioCorreccionLabelID"
													path="folioCorreccion" cssErrorClass="error">Folio de Corrección: </form:label>
											</td>
											<td align="left" width="100px"><form:input
													path="folioCorreccion" id="folioCorreccionInputID"
													size="25" maxlength="25" readonly="true"/>
												<form:errors path="folioCorreccion" /></td>
											<td align="left" width="100px">
												<!-- Nombre Denominacion o razon social --> <form:label
													for="razonSocial" id="razonSocialLabelID"
													path="razonSocial" cssErrorClass="error">Nombre, denominación o razón social: </form:label>
											</td>
											<td align="left" width="100px"><form:input
													path="razonSocial" id="razonSocialID" size="50"
													maxlength="200" readonly="true"/>
												<form:errors path="razonSocial" /></td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="100px"><form:label
													for="numeroRegistroPatronal"
													id="numeroRegistroPatronalLabelID"
													path="numeroRegistroPatronal" cssErrorClass="error">Número de Registro Patronal: </form:label>
											</td>
											<td align="left" width="100px"><form:input
													path="numeroRegistroPatronal" readonly="true" 
													id="numeroRegistroPatronalInputID" size="12" maxlength="12"/>
												<form:errors path="numeroRegistroPatronal" /></td>
											<td align="left" width="100px"><form:label
													for="digitoVerificador" id="digitoVerificadorLabelID"
													path="digitoVerificador" cssErrorClass="error">Dígito Verificador: </form:label>
											</td>
											<td align="left" width="100px"><form:input
													path="digitoVerificador" id="digitoVerificadorInputID" readonly="true"
													size="1" maxlength="1"/>
												<form:errors path="digitoVerificador" /></td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="150px"><form:label for="curp"
													id="curpLabelID" path="curp" cssErrorClass="error">Clave única de registro de población: </form:label>
											</td>
											<td align="left" width="50px"><form:input path="curp"
													id="curpInputID" size="20" maxlength="21" readonly="true"/>
												<form:errors path="curp" /></td>
											<td align="left" width="150px"><form:label for="rfc"
													id="rfcLabelID" path="rfc" cssErrorClass="error">Registro Federal de Contribuyentes: </form:label>
											</td>
											<td align="left" width="50px"><form:input path="rfc"
													id="rfcInputID" size="14" maxlength="15" readonly="true"/>
												<form:errors path="rfc" /></td>
										</tr>
										
									</tbody>
								</table></td>
						</tr>
					</table>
				</fieldset>
			</div>
			
			<div id="domicilioFiscalDiv" style="background-color: #f2fff2;" title="Domicilio Fiscal">
				<fieldset>
					<table style="width: 900px" align="center">
						<tr valign="middle">
							<td align="center" width="900px">
								<table class="tablaverde2" style="width: 900px">
									<tbody>
										<tr valign="top" class="impar">
											<td align="center" colspan="4"><b>Domicilio fiscal</b></td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="20%">
												<form:label path="registroPatronalFiscal" id="registroPatronalFiscalLabelID" cssErrorClass="error">Registro Patronal: </form:label>
											</td>
											<td align="left" colspan="3">
												<form:input size="120" path="registroPatronalFiscal" id="registroPatronalFiscalInputID" readonly="true"/>
											</td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="20%">
												<form:label path="calle" id="calleLabelID" cssErrorClass="error">Calle: </form:label>
											</td>
											<td align="left" colspan="3">
												<form:input size="120" path="calle" id="calleInputID" readonly="true"/>
											</td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="25%">
												<form:label path="numExterior" id="numExteriorLabelID" cssErrorClass="error">Número Exterior: </form:label>
											</td>
											<td align="left" width="25%">
												<form:input size="10" path="numExterior" id="numExteriorInputID" readonly="true"/>
											</td>
											<td align="left" width="20%">
												<form:label path="numInterior" id="numInteriorLabelID" cssErrorClass="error">Número interior: </form:label>
											</td>
											<td align="left" width="30%">
												<form:input size="10" path="numInterior" id="numInteriorInputID" readonly="true"/>
											</td>
										</tr>
										<tr>
											<td align="left" width="20%">
												<form:label path="colonia" id="coloniaLabelID" cssErrorClass="error">Colonia: </form:label>
											</td>
											<td align="left" width="30%">
												<form:input size="50" path="colonia" id="coloniaInputID" readonly="true"/>
											</td>
											<td align="left" width="25%">
												<form:label path="municipio" id="municipioLabelID" cssErrorClass="error">Municipio / Delegación: </form:label>
											</td>
											<td align="left" width="25%">
												<form:input size="50" path="municipio" id="municipioInputID"  readonly="true"/>
											</td>
										</tr>
										<tr>
											<td align="left" width="20%">
												<form:label path="localidad" id="localidadLabelID" cssErrorClass="error">Localidad: </form:label>
											</td>
											<td align="left" width="30%">
												<form:input size="50" path="localidad" id="localidadInputID" readonly="true"/>
											</td>
											<td align="left" width="25%">
												<form:label path="entidadFederativa" id="entidadFederativaLabelID" cssErrorClass="error">Entidad Federativa: </form:label>
											</td>
											<td align="left" width="25%">
												<form:input size="50" path="entidadFederativa" id="entidadFederativaInputID" readonly="true"/>
											</td>
										</tr>
										<tr>
											<td align="left" width="20%">
												<form:label path="codigoPostal" id="codigoPostalLabelID" cssErrorClass="error">CódigoPostal: </form:label>
											</td>
											<td align="left" width="30%">
												<form:input size="6" path="codigoPostal" id="codigoPostalInputID" readonly="true"/>
											</td>
											<td align="left" width="25%">
												<form:label path="telefono" id="telefonoLabelID" cssErrorClass="error">Teléfono: </form:label>
											</td>
											<td align="left" width="25%">
												<form:input path="telefono" id="telefonoInputID" readonly="true"/>
											</td>
										</tr>
										<tr>
											<td align="left" width="20%">
												<form:label path="email" id="emailLabelID" cssErrorClass="error">Correo Electrónico: </form:label>
											</td>
											<td align="left" width="30%">
												<form:input path="email" size="50" id="emailInputID" readonly="true"/>
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
											<td align="center" colspan="4"><b>Domicilio Centro de Trabajo</b></td>
										</tr>
										
										<tr valign="top" class="par">
											<td align="left" width="20%">
												<form:label path="registroPatronalCentroTrabajo" id="registroPatronalCentroTrabajoLabelID" cssErrorClass="error">Registro Patronal: </form:label>
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
												<form:label path="numInteriorCentroTrabajo" id="numInteriorCentroTrabajoLabelID" cssErrorClass="error">Número interior: </form:label>
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
												<form:label path="municipioCentroTrabajo" id="municipioCentroTrabajoLabelID" cssErrorClass="error">Municipio / Delegación: </form:label>
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
												<form:label path="entidadFederativaCentroTrabajo" id="entidadFederativaCentroTrabajoLabelID" cssErrorClass="error">Entidad Federativa: </form:label>
											</td>
											<td align="left" width="25%">
												<form:input size="50" path="entidadFederativaCentroTrabajo" id="entidadFederativaCentroTrabajoInputID" readonly="true"/>
											</td>
										</tr>
										<tr>
											<td align="left" width="20%">
												<form:label path="codigoPostalCentroTrabajo" id="codigoPostalCentroTrabajoLabelID" cssErrorClass="error">CódigoPostal: </form:label>
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
												<form:label path="email" id="fraccionCentroTrabajoLabelID" cssErrorClass="error">Fracción: </form:label>
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
											<td align="center" colspan="4"><b>Domicilio de la Obra</b></td>
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
												<form:label path="numExteriorObra" id="numExteriorObraLabelID" cssErrorClass="error">Número Exterior: </form:label>
											</td>
											<td align="left" width="25%">
												<form:input size="10" path="numExteriorObra" id="numExteriorObraInputID" readonly="true"/>
											</td>
											<td align="left" width="20%">
												<form:label path="numInteriorObra" id="numInteriorObraLabelID" cssErrorClass="error">Número interior: </form:label>
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
												<form:label path="municipioObra" id="municipioObraLabelID" cssErrorClass="error">Municipio / Delegación: </form:label>
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
												<form:label path="entidadFederativaObra" id="entidadFederativaObraLabelID" cssErrorClass="error">Entidad Federativa: </form:label>
											</td>
											<td align="left" width="25%">
												<form:input size="50" path="entidadFederativaObra" id="entidadFederativaObraInputID" readonly="true"/>
											</td>
										</tr>
										<tr>
											<td align="left" width="20%">
												<form:label path="codigoPostalObra" id="codigoPostalObraLabelID" cssErrorClass="error">CódigoPostal: </form:label>
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
			
			<div id="FechasSolicitudCorreccion" style="background-color: #f2fff2;" title="Fechas de la Corrección">
				<fieldset>
					<table style="width: 900px" align="center">
						<tr valign="middle">
							<td align="center" width="900px">
								<table class="tablaverde2" style="width: 900px">
									<tbody>
										<tr valign="top" class="impar">
											<td align="center" colspan="8"><b>Fechas de la Corrección</b></td>
										</tr>
										<tr>
											<td align="left" width="16%">
												<form:radiobutton disabled="true" path="tipoDeCorreccion" value="1" label="Corrección Espontanea" id="radioEspontanea"/>
											</td>
											<td align="left" width="16%">
												<form:label id="fechaAutorizacionCorreccionEspontaneaLabelID" path="fechaAutorizacionCorreccionEspontanea" cssErrorClass="error">Fecha Autorización: </form:label>
											</td>
											<td align="left" width="16%">
												<form:input path="fechaAutorizacionCorreccionEspontanea" readonly="true"/>
												<form:errors path="fechaAutorizacionCorreccionEspontanea" />
											</td>
											<td align="left" width="16%">
												<form:radiobutton disabled="true" path="tipoDeCorreccion" value="2" label="Corrección por Invitación" id="radioInvitacion"/>
											</td>
											<td align="left" width="16%">
												<form:label id="fechaAceptacionInvitacionCorreccionLabelID" path="fechaAceptacionInvitacionCorreccion" cssErrorClass="error">Fecha Aceptación: </form:label>
											</td>
											<td align="left" width="16%">
												<form:input path="fechaAceptacionInvitacionCorreccion" readonly="true" />
												<form:errors path="fechaAceptacionInvitacionCorreccion" />
											</td>
										</tr>
										<tr>
											<td align="left" width="16%">
												<form:label path="fechaEjercicioInicial" id="fechaEjercicioInicialLabelID">Ejercicio / periodo regularizado: </form:label>
											</td>
											<td align="left" width="16%">
												<form:input path="fechaEjercicioInicial" readonly="true" />
												<form:errors path="fechaEjercicioInicial" />
											</td>
											<td align="center" width="16%">
												<form:label path="fechaEjercicioInicial" id="fechaEjercicioInicialLabelALID">AL</form:label>
											</td>
											<td align="left" width="16%">
												<form:input path="fechaEjercicioFinal" readonly="true"/>
												<form:errors path="fechaEjercicioFinal" />
											</td>
											<td align="left" width="16%">
												<form:label path="fechaProrroga" id="fechaProrrogaLabelID">Prórroga: </form:label>
											</td>
											<td align="left" width="16%">
												<form:input path="fechaProrroga" readonly="true"/>
												<form:errors path="fechaProrroga" />
											</td>
										</tr>
										<tr>
											<td align="left" colspan="2">
												<form:label path="numeroTrabajadores" id="numeroTrabajadoresLabelID" cssErrorClass="error">Número de trabajadores regularizados: </form:label>
											</td>
											<td align="left" colspan="4">
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
			<div id="firmasDiv" style="background-color: #f2fff2;" title="Para uso exclusivo del IMSS">
				<fieldset>
					<table style="width: 900px" align="center">
						<tr valign="middle">
							<td align="center" width="900px">
								<table class="tablaverde2" style="width: 900px">
									
									<tr>
										<td colspan="4" align="center">Observaciones</td>
										
									</tr>
									
									
									<!--  Comenntario de prueba-->
									
									<tr>
										<td colspan="4"><textarea  id="observacionesPresentacion"  rows="8" cols="110"></textarea></td>
									</tr>
									<tbody>
										<tr valign="top" class="impar">
											<td align="center" colspan="6"><b>Para uso exclusivo del IMSS</b></td>
										</tr>
									</tbody>
									<tr>
										<td colspan="4" align="left"><form:label path="labelManifiesto">MANIFIESTO  BAJO PROTESTA DE DECIR VERDAD QUE LA INFORMACIÓN Y DOCUMENTACIÓN PRESENTADA EN ESTA CORRECCIÓN ES CIERTA, DETERMINÁNDOSE CON ESTRICTO APEGO A LA LEY DEL SEGURO SOCIAL Y SUS REGLAMENTOS, LA  QUE SE PRESENTA ANTE EL IMSS, EN LOS TÉRMINOS DEL ARTÍCULO 180 DEL REGLAMENTO DE LA LEY DEL SEGURO SOCIAL EN MATERIA DE AFILIACIÓN, CLASIFICACIÓN DE EMPRESAS, RECAUDACIÓN Y FISCALIZACIÓN</form:label> </td>
									</tr>
									<tr></tr>
									<tr>
										<td colspan="4"><form:input path="nombreYFirmaPatron" onkeyup="validaCampo('PermiteSoloLetrasYPunto','nombreYFirmaPatron','presentacionCorreccionModel')"/></td>
									</tr>
									<tr>
										<td colspan="4" align="center">Nombre y firma del patrón o representante legal</td>
									</tr>
									<tr>
										<td colspan="2"><form:input path="lugar" onkeyup="validaCampo('PermiteSoloLetrasYPunto','lugar','presentacionCorreccionModel')"/></td>
										<td colspan="2"><form:input path="fechaFirma" readonly="true"/></td>
									</tr>
									<tr>
										<td colspan="2" align="center">Lugar</td>
										<td colspan="2" align="center">Dia Mes A&ntilde;o</td>
									</tr>
									<tr>
										<td align="center" width="100%" colspan="4">
										  	<a href="#abajo" onclick="javascript:registrarFirmado();"><span class="boton">Presentar Solicitud de Corrección</span></a>
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