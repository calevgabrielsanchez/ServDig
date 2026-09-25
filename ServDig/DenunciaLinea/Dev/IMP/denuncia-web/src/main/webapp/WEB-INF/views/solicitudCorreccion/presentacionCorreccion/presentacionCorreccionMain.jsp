<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>

	<style>
		label.error { float: none; font-size:smaller; color: red; padding-left: .5em; vertical-align: top; }
		label  {  float: none; }
	</style>

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/delta/correccion/presentacion/presentacionCorreccion.js"></script>
		
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
	
<form:form modelAttribute="presentacionCorreccionModel" action="/correccion/presentacion/presentar.do">
	<input type="hidden" id="idContexto" value="<%=request.getContextPath()%>">
	<form:hidden path="tipoDeCorreccionHidden" id="tipoDeCorreccionHidden"/>
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
		<div id="dialog-error" title="Mensaje de SICONET">
			<p>
				<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>
				Your files have downloaded successfully into the My Downloads folder.
			</p>
			<p>
				Currently using <b>36% of your storage space</b>.
			</p>
		</div>
		 
		<div id="headerCorreccionDialog" title="Delegaci�n / SubDelegaci�n" style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity = 70) !important;">
		<!--
			<div id="wrapperHeaderDialog" style="background-color: #f2fff2;">
				<fieldset>
					<table style="width: 900px" align="center">
						<tr valign="middle">
							<td align="center" width="900px">
								<table class="tablaverde2" style="width: 900px">
									<tbody>
										<tr valign="top" class="impar">
											<td align="center" colspan="4"><b>Delegaci�n / Subdelegaci�n</b></td>
										</tr>
										<tr>
											<td align="left" width="100px">
												<form:label for="delegacion" id="delegacionLabelID" path="delegacion" cssErrorClass="error">Delegaci�n: </form:label>
											</td>
											<td align="left" width="100px">
												<form:input path="delegacion" id="delegacionInputID" size="30" maxlength="100" readonly="true" />
												<form:errors path="delegacion" /></td>
											<td align="left" width="100px">
												<form:label for="subDelegacion" id="subDelegacionLabelID"
													path="subDelegacion" cssErrorClass="error">SubDelegaci�n: </form:label>
											</td>
											<td align="left" width="100px">
												<form:input path="subDelegacion" id="subDelegacionInputID" size="50" maxlength="200" readonly="true" />
												<form:errors path="subDelegacion" /></td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</table>
				</fieldset>
			</div>
			 -->
			<div id="filtrosBusquedaDiv" style="background-color: #f2fff2;" title="B�scar Solicitudes de Correcci�n">
				<fieldset>
					<table style="width: 900px" align="center">
						<tr valign="middle">
							<td align="center" width="900px">
								<table class="tablaverde2" style="width: 900px">
									<tbody>
										<tr valign="top" class="impar">
											<td align="center" colspan="4"><b>B�scar Solicitudes de Correcci�n</b></td>
										</tr>
										<tr>
											<td width="25%" align="left"><form:label path="folioSoicitudCorreccionFiltro">Folio de Correcci�n: </form:label></td>
											<td width="25%" align="left"><form:input path="folioSoicitudCorreccionFiltro"/></td>
											<td width="25%">&nbsp;</td>
											<td width="25%">&nbsp;</td>
										</tr>
										<tr><td colspan="4">&nbsp;</td></tr>
										<tr>
											<td align="center" width="100%" colspan="4">
										  	<a href="#" onclick="javascript:buscarSolicitudesDeCorreccion();"><span class="boton">Buscar</span></a>
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
		<div id="presentacionMainDialog" title="Presentaci�n de la Correcci&oacute;n" style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity = 70) !important;">
			<div id="folioSolicitudSeleccionadoDivID" style="background-color: #f2fff2;">
				<fieldset>
					<table style="width: 900px" align="center">
						<tr valign="middle">
							<td align="center" width="900px">
								<table class="tablaverde2" style="width: 900px">
									<tbody>
										<tr>
											<td align="left" width="25%">Folio Correccion Seleccionado: </td>
											<td align="left" width="10%"><label for="folioSolicitudSeleccionado" id="folioSolicitudSeleccionadoLabelID"></label></td>
											<td align="center" width="35%">
										  		<a onclick="javascript:mostrarSolicitudesDeCorreccion();"><span class="boton">Seleccionar otro Folio de Correcci�n</span></a>
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
											<td align="center" colspan="4"><b>Datos Registro Patronal</b></td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="100px"><form:label
													for="folioCorreccion" id="folioCorreccionLabelID"
													path="folioCorreccion" cssErrorClass="error">Folio de Correcci�n: </form:label>
											</td>
											<td align="left" width="100px"><form:input
													path="folioCorreccion" id="folioCorreccionInputID"
													size="40" maxlength="30" readonly="true"/>
												<form:errors path="folioCorreccion" /></td>
											<td align="left" width="100px">
												<!-- Nombre Denominacion o razon social --> <form:label
													for="razonSocial" id="razonSocialLabelID"
													path="razonSocial" cssErrorClass="error">Nombre, denomicacion o raz�n social: </form:label>
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
													path="numeroRegistroPatronal" cssErrorClass="error">Numero de Registro Patronal: </form:label>
											</td>
											<td align="left" width="100px"><form:input
													path="numeroRegistroPatronal" readonly="true" 
													id="numeroRegistroPatronalInputID" size="12" maxlength="12"/>
												<form:errors path="numeroRegistroPatronal" /></td>
											<td align="left" width="100px"><form:label
													for="digitoVerificador" id="digitoVerificadorLabelID"
													path="digitoVerificador" cssErrorClass="error">Digito Verificador: </form:label>
											</td>
											<td align="left" width="100px"><form:input
													path="digitoVerificador" id="digitoVerificadorInputID"
													size="1" maxlength="1"/>
												<form:errors path="digitoVerificador" /></td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="150px"><form:label for="curp"
													id="curpLabelID" path="curp" cssErrorClass="error">Clave unica de registro de poblaci�n: </form:label>
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
												<form:label path="calle" id="calleLabelID" cssErrorClass="error">Calle: </form:label>
											</td>
											<td align="left" colspan="3">
												<form:input size="120" path="calle" id="calleInputID" readonly="true"/>
											</td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="25%">
												<form:label path="numExterior" id="numExteriorLabelID" cssErrorClass="error">Numero Exterior: </form:label>
											</td>
											<td align="left" width="25%">
												<form:input size="10" path="numExterior" id="numExteriorInputID" readonly="true"/>
											</td>
											<td align="left" width="20%">
												<form:label path="numInterior" id="numInteriorLabelID" cssErrorClass="error">Numero interior: </form:label>
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
												<form:label path="municipio" id="municipioLabelID" cssErrorClass="error">Municipio / Delegaci�n: </form:label>
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
												<form:label path="codigoPostal" id="codigoPostalLabelID" cssErrorClass="error">CodigoPostal: </form:label>
											</td>
											<td align="left" width="30%">
												<form:input size="6" path="codigoPostal" id="codigoPostalInputID" readonly="true"/>
											</td>
											<td align="left" width="25%">
												<form:label path="telefono" id="telefonoLabelID" cssErrorClass="error">Telefono: </form:label>
											</td>
											<td align="left" width="25%">
												<form:input path="telefono" id="telefonoInputID" readonly="true"/>
											</td>
										</tr>
										<tr>
											<td align="left" width="20%">
												<form:label path="email" id="emailLabelID" cssErrorClass="error">Correo Electr�nico: </form:label>
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
			<div id="FechasSolicitudCorreccion" style="background-color: #f2fff2;" title="Fechas de la Correcci�n">
				<fieldset>
					<table style="width: 900px" align="center">
						<tr valign="middle">
							<td align="center" width="900px">
								<table class="tablaverde2" style="width: 900px">
									<tbody>
										<tr valign="top" class="impar">
											<td align="center" colspan="8"><b>Fechas de la Correcci�n</b></td>
										</tr>
										<tr>
											<td align="left" width="16%">
												<form:radiobutton disabled="true" path="tipoDeCorreccion" value="1" label="Correcci�n Espontanea" id="radioEspontanea"/>
											</td>
											<td align="left" width="16%">
												<form:label id="fechaAutorizacionCorreccionEspontaneaLabelID" path="fechaAutorizacionCorreccionEspontanea" cssErrorClass="error">Fecha Autorizaci�n: </form:label>
											</td>
											<td align="left" width="16%">
												<form:input path="fechaAutorizacionCorreccionEspontanea" readonly="true"/>
												<form:errors path="fechaAutorizacionCorreccionEspontanea" />
											</td>
											<td align="left" width="16%">
												<form:radiobutton disabled="true" path="tipoDeCorreccion" value="2" label="Correcci�n por Invitaci�n" id="radioInvitacion"/>
											</td>
											<td align="left" width="16%">
												<form:label id="fechaAceptacionInvitacionCorreccionLabelID" path="fechaAceptacionInvitacionCorreccion" cssErrorClass="error">Fecha Aceptaci�n: </form:label>
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
												<form:label path="fechaProrroga" id="fechaProrrogaLabelID">Prorroga: </form:label>
											</td>
											<td align="left" width="16%">
												<form:input path="fechaProrroga" readonly="true"/>
												<form:errors path="fechaProrroga" />
											</td>
										</tr>
										<tr>
											<td align="left" colspan="2">
												<form:label path="numeroTrabajadores" id="numeroTrabajadoresLabelID" cssErrorClass="error">N�mero de trabajadores regularizados: </form:label>
											</td>
											<td align="left" colspan="4">
												<form:input path="numeroTrabajadores" readonly="true"/>
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
			<div id="CopPagadasDiv" style="background-color: #f2fff2;" title="Total de C.O.P pagadas en la correcci�n">
				<fieldset>
					<table style="width: 900px" align="center">
						<tr valign="middle">
							<td align="center" width="900px">
								<table class="tablaverde2" style="width: 900px">
									<tbody>
										<tr>
											<td><a name="inicioCopPagadas"></a></td>
										</tr>
										<tr>
											<td align="center" width="100%" colspan="6">
										  	<a href="#inicioCopPagadas" onclick="javascript:mostrarAnexoSolicitudCorreccion();"><span class="boton">Mostrar Anexo Solicitud Correccion.</span></a>
										  </td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</table>
				</fieldset>
			</div>
			<div id="CopPagadasAnexoDiv" style="background-color: #f2fff2;" title="Abrir Anexo de Solicitud de Correccion">
			</div>
			<div id="documentacionQuePresentaDiv" style="background-color: #f2fff2;" title="Documentaci�n qu� presenta">
				<fieldset>
					<table style="width: 900px" align="center">
						<tr valign="middle">
							<td align="center" width="900px">
								<table class="tablaverde2" style="width: 900px">
									<tbody>
										<tr valign="top" class="impar">
											<td align="center" colspan="6"><b>Documentaci�n qu� presenta</b></td>
										</tr>
										<tr>
											<td align="center" width="16%">1.</td>
											<td align="left" colspan="4" width="64%">Comprobante de pago o del tramite del pago diferido o en parcialidades de las diferencias autodeterminadas</td>
											<td align="center" width="16%">
												<form:checkbox path="chkCombtPago" id="chkCombtPago"/>
											</td>
										</tr>
										<tr>
											<td align="center" width="16%">2.</td>
											<td align="left" colspan="4" width="64%">Comprobante de la presentaci�n de los avisos afiliatorios, derivados de la correci�n</td>
											<td align="center" width="16%">
												<form:checkbox path="chkCombtAvisosAfil" id="chkCombtAvisosAfil"/>
												<form:errors path="chkCombtAvisosAfil"/>
											</td>
										</tr>
										<tr>
											<td align="center" width="16%">3.</td>
											<td align="left" colspan="4" width="64%">Documentaci�n qu� sustenta la correcci�n</td>
											<td align="center" width="16%">
												<form:checkbox path="chkDocumentacionCorreccion" id="chkDocumentacionCorreccion"/>
												<form:errors path="chkDocumentacionCorreccion"/>
											</td>
										</tr>
										<tr>
											<td colspan="6" width="100%">Observaciones</td>
										</tr>
										<tr>
											<td colspan="6" align="center">
												<form:textarea path="observaciones" cols="110" rows="5"/>
												<form:errors path="observaciones" />
											</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</table>
				</fieldset>
			</div>
			<div id="firmasDiv" style="background-color: #f2fff2;" title="Para uso exlusivo del IMSS">
				<fieldset>
					<table style="width: 900px" align="center">
						<tr valign="middle">
							<td align="center" width="900px">
								<table class="tablaverde2" style="width: 900px">
									<tbody>
										<tr valign="top" class="impar">
											<td align="center" colspan="6"><b>Para uso exlusivo del IMSS</b></td>
										</tr>
									</tbody>
									<tr>
										<td colspan="4" rowspan="2" align="left"><form:label path="labelManifiesto">MANIFIESTO  BAJO PROTESTA DE DECIR VERDAD QUE LA INFORMACI�N Y DOCUMENTACI�N PRESENTADA EN ESTA CORRECCI�N ES CIERTA, DETERMINADOSE CON ESTRICTO APEGO A LA LEY DEL SEGURO SOCIAL Y SUS REGLAMENTOS, LA  QUE SE PRESENTA ANTE EL IMSS, EN LOS T�RMINOS DEL ART�CULO 180 DEL REGLAMENTO DE LA LEY DEL SEGURO SOCIAL EN MATERIA DE AFILIACI�N, CLASIFICACI�N DE EMPRESAS, RECAUDACI�N Y FISCALIZACI�N</form:label> </td>
										<td colspan="2" rowspan="6" valign="top" align="center">Para uso exlusivo del IMSS</td>
									</tr>
									<tr></tr>
									<tr>
										<td colspan="4"><form:input path="nombreYFirmaPatron"/></td>
									</tr>
									<tr>
										<td colspan="4" align="center">Nombre y firma del patron o representante legal</td>
									</tr>
									<tr>
										<td colspan="2"><form:input path="lugar"/></td>
										<td colspan="2"><form:input path="fechaFirma" readonly="true"/></td>
									</tr>
									<tr>
										<td colspan="2" align="center">Lugar</td>
										<td colspan="2" align="center">Dia Mes A&ntilde;o</td>
									</tr>
									<tr>
										<td align="center" width="100%" colspan="4">
										  	<a href="#abajo" onclick="javascript:abrirDialogoFirma();"><span class="boton">Presentar Solicutd de Correccion</span></a>
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
	<div id="registroPercepcion">
		<jsp:include page="../../firma/firma.jsp" />
	</div>
</html>