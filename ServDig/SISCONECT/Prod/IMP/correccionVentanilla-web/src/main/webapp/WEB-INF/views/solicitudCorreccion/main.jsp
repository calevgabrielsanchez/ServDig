<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<%@page import="mx.gob.imss.ctirss.correccion.session.UserSession"%>
<style>
	label.error { float: none; font-size:smaller; color: red; padding-left: .5em; vertical-align: top; }
	label  {  float: none; }
</style>

<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/delta/solicitud/FirmaDigital.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/delta/solicitud/correccion.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/jquery/jquery.blockUI.1.33.js"></script>


 <div id="contenedorFirma"></div>
<div id="dialog-error" title="Mensaje de SISCONET"></div>
  
		<div id="cuerpo" class="">
			<form action="correcion/muestraReporte.do" method="post" id="correccionForm">
				
				<input type="hidden" id="cont" value="<%=request.getContextPath() %>">
				
				<input type="hidden" id="cveIdUsuario" value="${crcSolicitud.usuarioFirmado.cveIdUsuario}">
				<input type="hidden" id="fechaCadenaOriginal" value="${crcSolicitud.fechaCadenaOriginal}">
				<input type="hidden" id="procedencia" value="${crcSolicitud.usuarioFirmado.procedencia}">
				<input type="hidden" id="idSubDelegacion" value="${crcSolicitud.usuarioFirmado.idSubDelegacion}">
				<input type="hidden" id="cveDelegacion" value="${crcSolicitud.usuarioFirmado.cveCodigoDelegacion}">
				<input type="hidden" id="cveSubDelegacion" value="${crcSolicitud.usuarioFirmado.cveCodigoSubDelegacion}">
				
								
				<div class="menu_principal" style="height: 2em !important;">
					<!--inicia menu principal-->
					<div align="center">
						<div class="centrado">
							<ul style="height: 1em !important;">
								<li><a> Registro de Solicitud de Correcci&oacute;n</a>
								</li>
							</ul>
						</div>
						<!--fin centrado-->
					</div>
				</div>
				<br />


				<div id="dgsolicitudCorreccion" 
					style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity =  70) !important;">
					<div id="wrapperDialogSolCorr" style="background-color: #f2fff2;">
						<fieldset>
							<table class="tablaverde2" style="width: 900px" align="center">
								<tr valign="middle">
									<td align="center" width="100%">
										<table style="width: 900px" border="0">
											<tbody>
												
												<tr valign="top" class="par">
												<td align="center" class="etiqueta2">
														<span class="required">*</span><label>Registro Patronal a Corregir: <label>
													</td>
													<%if (((UserSession)request.getSession().getAttribute("USR_SESSION")).getCveIdPatron()!=null &&  ((UserSession)request.getSession().getAttribute("USR_SESSION")).getCveIdPatron()!=0 ){ %>
													<td align="left">
														<input type="text" readonly="readonly" name="regPatronPrInput" style="text-align: center;" id="regPatronPrInput" size="20" maxlength="10" onkeyup="validaCampo('noCaracteresEspeciales','regPatronPrInput','correccionForm');" value="<%=((UserSession)request.getSession().getAttribute("USR_SESSION")).getRegistroPatronal().substring(0, 10)%>"/><label for="regPatronPrInput"></label>
													</td>
													<%}else{ %>
													<td align="left">
														<input type="text" name="regPatronPrInput" style="text-align: center;" id="regPatronPrInput" size="20" maxlength="10" onkeyup="validaCampo('noCaracteresEspeciales','regPatronPrInput','correccionForm');"/><label for="regPatronPrInput"></label>
													</td>	
													<%} %>
													<td align="center">   
														<a href="#" id="btnBuscarPatron"><span class="boton">Buscar</span> </a>
													</td>
													
													<td align="left" class="etiqueta2">
														<label>Folio de Corrección Asignado: <label>
													</td>
													<td align="left" class="etiqueta2">
														<input type="text" name="folioInput" id="folioInput" style="text-align: center;" size="50" readonly="readonly"/>
													</td>
												</tr>
												<tr valign="top" class="par">
													<td align="left" class="etiqueta2" colspan="3">
														<input type="radio"
														       name="unoVariosRp" 
														       id="unoVariosRp" 
														       value="1" 
														       onclick="divControl('hide','dgControladorVRP'); gestionaVariosRPs('RP');divControl('hide','domicilioCentroTrabajoDIV');"
														       checked="checked"> Un Registro Patronal &nbsp;&nbsp;
														<input type="radio" 
															   name="unoVariosRp" 
															   id="unoVariosRp" 
															   onclick="divControl('show','dgControladorVRP'); gestionaVariosRPs('VRP');divControl('show','domicilioCentroTrabajoDIV');"
															   value="2"> Varios Registros Patronales 
													</td>
													<td align="left" class="etiqueta2">
														<span class="required">*</span><label>Nombre o Denominación Social:</label>
													</td>
													<td align="left">
														<input type="text" readonly="readonly" name="razonSocialRegPatInput" id="razonSocialRegPatInput" size="50" maxlength="80" onkeyup="validaCampo('noCaracteresEspeciales','razonSocialRegPatInput','correccionForm');"/><label for="razonSocialRegPatInput"></label>
													</td>
												</tr>

												<tr valign="top" class="par">
													<td align="left" colspan="2" class="etiqueta2">
														&nbsp;
													</td>
													<td align="left">
														&nbsp;
													</td>
													<td align="left" class="etiqueta2">
														<span class="required">*</span><label>RFC:</label>
													</td>
													<td align="left">
														<input type="text"  name="rfcRegPatInput" id="rfcRegPatInput" size="50" maxlength="13" onkeyup="validaCampo('noCaracteresEspeciales','rfcRegPatInput','correccionForm');"/><label for="rfcRegPatInput"></label>
													</td>
												</tr>
												
												<tr valign="top" class="par">
												<td align="left" colspan="2" class="etiqueta2">
														&nbsp;
													</td>
													<td align="left">
														&nbsp;
													</td>
													<td align="left" class="etiqueta2">
														<label>CURP:</label>
													</td>
													<td align="left">
														<input type="text"  name="curpPatInput" id="curpPatInput" size="20" maxlength="18" onkeyup="validaCampo('noCaracteresEspeciales','curpPatInput','correccionForm');"/><label for="curpPatInput"></label>
													
												</tr>
												<tr>
													<td align="left" class="etiqueta2">
														<label	path="patron">Ejercicio o Periodo a Regularizar Del:</label>
													</td>
													<td align="left">
														<input type="text" id="fechaInicial" size="15" readonly="readonly" onchange="document.getElementById('fechaFinal').value=''" />
													</td>
													<td align="left" class="etiqueta2">
														<label path="patron"> Al: </label>
													</td>
													<td align="left">
														<input type="text" id="fechaFinal" size="15" readonly="readonly" onchange="validaAntecedentesCambioFecha()"/>
													</td>
												</tr>
												
											</tbody>
										</table>
									</td>
								</tr>
							</table>
							<input type="hidden" readonly="readonly" name="folioTemporalForma" id="folioTemporalForma" size="50"/>
						</fieldset>
					</div>
				</div>

				<br />
				<div class="menu_principal" style="height: 2em !important;">
					<!--inicia menu principal-->
					<div align="center">
						<div class="centrado">
							<ul style="height: 1em !important;">
								<li><a> Domicilio Fiscal</a>
								</li>
							</ul>
						</div>
						<!--fin centrado-->
					</div>
				</div>
				<div id="dgsolicitudCorreccionDom" 
					style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity =     70) !important;">
					<div id="wrapperDialogSolCorr2" style="background-color: #f2fff2;">
						<fieldset>
							<table class="tablaverde2" style="width: 900px" align="center">
								<tr valign="middle">
									<td align="center" width="900px">
										<table style="width: 900px">
											<tbody>
												<tr>
													<td align="center" class="etiqueta2">
														<span class="required">*</span><label>Registro Patronal del Domicilio Fiscal: </label>
													</td>
													<td align="left">
														<input type="text" name="regPatronInputDom" id="regPatronInputDom" style="text-align: center;" size="20" maxlength="10" /><label for="regPatronInputDom"></label>
													</td>
													<td align="center">
														<a href="#" id="btnBuscarPatronDom"><span class="boton">Buscar</span> </a>
													</td>
												</tr>
												<tr>
													<td>
														<input type="text" id="calleRegPatInputDom" style="text-align: center;" size="50" maxlength="50" readonly="readonly"/>
													</td>
													<td>
														<input id="numExteriorRegPatInputDom" style="text-align: center;"  size="10" maxlength="50" readonly="readonly"/>
													</td>
													<td>
														<input id="numInteriorRegPatInputDom" style="text-align: center;" size="10" maxlength="50" readonly="readonly"/>
													</td>
													<td colspan="2">
														<input id="coloniaRegPatInputDom" style="text-align: center;" size="50" maxlength="50" readonly="readonly"/>
													</td>
												</tr>
												<tr>
													<td class="etiqueta2">Calle</td>
													<td class="etiqueta2">N&uacute;mero Exterior</td>
													<td class="etiqueta2">N&uacute;mero Interior</td>
													<td colspan="2" class="etiqueta2">Colonia</td>
												</tr>

												<tr>
													<td>
														<input id="municipioRegPatInputDom" style="text-align: center;" size="50" maxlength="50" readonly="readonly" />
													</td>
													<td colspan="2">
														<input id="localidadRegPatInputDom" style="text-align: center;" size="30" maxlength="50" readonly="readonly" />
													</td>
													<td>
														<input id="entidadFederativaRegPatInputDom" style="text-align: center;" size="30" maxlength="50" readonly="readonly" />
													</td>
													<td>
														<input id="codigoPostalRegPatInputDom" style="text-align: center;" size="10" maxlength="50" readonly="readonly" />
													</td>
												</tr>
												<tr>
													<td class="etiqueta2">Municipio</td>
													<td colspan="2" class="etiqueta2">Localidad</td>
													<td class="etiqueta2">Entidad Federativa</td>
													<td class="etiqueta2">Código Postal</td>
												</tr>
												<tr>
													<td>
														<input type="text" name="telefonoRegPatInput" style="text-align: center;" id="telefonoRegPatInput" size="20" maxlength="10" onkeyup="validaCampo('PermiteSoloNumeros','telefonoRegPatInput','correccionForm')"/><label for="telefonoRegPatInput"></label>
													</td>
													<td colspan="2">
														<input type="text" name="emailRegPatInput"  style="text-align: center;" id="emailRegPatInput" size="30" maxlength="30" /><label for="emailRegPatInput"></label>
													</td>
													<td colspan="2">
														<input type="text" id="subdelegacionRegPatInput" style="text-align: center;" size="50" maxlength="50" readonly="readonly"/>
													</td>
												</tr>
												<tr>
													<td class="etiqueta2"><span class="required">*</span>Teléfono</td>
													<td colspan="2" class="etiqueta2"><span class="required">*</span>Correo Electrónico</td>
													<td colspan="2" class="etiqueta2">Subdelegaci&oacute;n IMSS del Domicilio Fiscal</td>
												</tr>
												<tr>
													<td colspan="6" align="center">
														<div id="divBtnCorrigeDom">
															<a href="#" id="btnCorrigeDom"><span class="boton">Corregir Domicilio Geográfico</span> </a>
														</div>													
													</td>
												</tr>
											</tbody>
										</table>
									</td>
								</tr>
							</table>
						</fieldset>
					</div>
				</div>

				<div id="dgsolicitudCorreccionTipo" 
					style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity =     70) !important;">
					<div id="wrapperDialogSolCorr3" style="background-color: #f2fff2;">
						<fieldset>
							<table class="tablaverde2" style="width: 900px" align="center" >
								<tr valign="middle">
									<td align="center" width="900px">
										<table style="width: 900px">
											<tbody>
												<tr>
													<td align="left" width="100px" colspan="2" class="etiqueta2">
														<input type="checkbox" label="Registro de Obra" onchange="showDiv();validaSeleccionConstrucion();" id="chkSolicitudObra"/> Construcción
													</td>
													<td align="center" width="100px" colspan="2" class="etiqueta2">
														<label id="numeroObraLabel" for="txPrima">Número de Registro de Obra: </label>
													</td>
													<td align="left" width="100px" colspan="4">
														<input id="numeroRegistroObra" disabled="disabled" size="30" maxlength="12" 
																onkeyup="validaCampo('PermiteSoloNumeros','numeroRegistroObra','correccionForm')" 
																onblur="validaExistenciaObra(this)"
																readonly="readonly"/>
													</td>
													<td align="left" >
														<a href="#" id="btnBuscarObra" ><span class="boton">Validar Obra de SATIC</span> </a>
													</td>
												</tr>
												<tr>
													<td align="left" width="100px" colspan="1"	class="etiqueta2" class="etiqueta2">
														<label id="tipoOrigenLabel"> Tipo Corrección: </label>
													</td>
													<td align="left" width="100px" colspan="1" class="etiqueta2">
														<input type="radio"	name="tipoSol" value="ESPONTANEA" disabled="disabled">Espontanea
													</td>
													<td align="left" width="100px" colspan="2" class="etiqueta2">
														<input type="radio" name="tipoSol" value="PROMOCION" disabled="disabled"/>Promoción
													</td>
													<td align="left" width="100px" colspan="2" class="etiqueta2">
														<input type="radio" name="tipoSol" value="INVITACION" disabled="disabled"/>Invitación
													</td>
													<td align="left" width="100px" colspan="2" class="etiqueta2">
														<label id="numeroFolioInvitacion" >Fecha de Recepción de Oficio: </label>
													</td>
													<td align="left" width="100px" colspan="2" class="etiqueta2">
														<input type="text" id="fechaFolio" readonly="readonly" />
													</td>
												</tr>
												<tr>
												
													<td align="left" class="etiqueta2">
														<label path="numeroTrabajadores"><span class="required">*</span>Número de Trabajadores:</label>
													</td>
													<td align="left" colspan="4">
														<input type="text"	name="numeroTrabajadores" id="numeroTrabajadores" size="15" maxlength="4" onkeyup="validaCampo('PermiteSoloNumeros','numeroTrabajadores','correccionForm')"/><label for="numeroTrabajadores"></label>
													</td>
												</tr>
											</tbody>
										</table>
									</td>
								</tr>
							</table>
						</fieldset>
					</div>
				</div>

				<br />
				<div class="menu_principal" id="divHeadObra" style="display:none">
					<!--inicia menu principal-->
					<div align="center">
						<div class="centrado">
							<ul>
								<li><a> Domicilio de la Obra</a>
								</li>
							</ul>
						</div>
						<!--fin centrado-->
					</div>
				</div>
				<div id="dgsolicitudCorreccionObra" 
					
					style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity =     70) !important;display:none">
					<div id="wrapperDialogSolCorrObra" style="background-color: #f2fff2;">
						<fieldset>
							<table class="tablaverde2" style="width: 900px" align="center">
								<tr valign="middle">
									<td align="center" width="900px">
										<table style="width: 900px" border="0">
											<tbody>
												<tr>
													<td>
														<input type="text" id="calleRegPatInputObra" size="50" maxlength="50" readonly="readonly"/>
													</td>
													<td>
														<input type="text" id="numExteriorRegPatInputObra" size="10" maxlength="50" readonly="readonly"/>
													</td>
													<td>
														<input type="text" id="numInteriorRegPatInputObra" size="10" maxlength="50" readonly="readonly"/>
													</td>
													<td colspan="2">
														<input type="text" id="coloniaRegPatInputObra" size="50" maxlength="50" readonly="readonly"/>
													</td>
												</tr>
												<tr>
													<td class="etiqueta2">Calle</td>
													<td class="etiqueta2">N&uacute;mero Exterior</td>
													<td class="etiqueta2">N&uacute;mero Interior</td>
													<td colspan="2" class="etiqueta2">Colonia</td>
												</tr>
												<tr>
													<td>
														<input type="text" id="municipioRegPatInputObra" size="50" maxlength="50" readonly="readonly" />
													</td>
													<td colspan="2">
														<input type="text" id="localidadRegPatInputObra" size="30" maxlength="50" readonly="readonly" />
													</td>
													<td>
														<input type="text" id="entidadFederativaRegPatInputObra" size="30" maxlength="50" readonly="readonly" />
													</td>
													<td>
														<input type="text" id="cpRegPatInputObra" size="10" maxlength="50" readonly="readonly" />
													</td>
												</tr>
												<tr>
													<td class="etiqueta2">Municipio</td>
													<td colspan="2" class="etiqueta2">Localidad</td>
													<td class="etiqueta2">Entidad Federativa</td>
													<td class="etiqueta2">Código Postal</td>
												</tr>
												<tr>
													<td colspan="6" align="center">
														<div id="botonObra">
															<a href="#" id="btnCorrigeDomObra"><span class="boton">Corregir Domicilio Geográfico</span> </a>
														</div>														
													</td>
												</tr>
											</tbody>
										</table>
									</td>
								</tr>
							</table>
						</fieldset>
					</div>
				</div>



				<br />
				<div class="menu_principal">
					<!--inicia menu principal-->
					<div align="center">
						<div class="centrado">
							<ul>
								<li><a> Domicilio del Centro de Trabajo</a>
								</li>
							</ul>
						</div>
						<!--fin centrado-->
					</div>
				</div>
				<div id="dgsolicitudCorreccionCentro"
					
					style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity =     70) !important;">
					<div id="wrapperDialogSolCorr4" style="background-color: #f2fff2;">
						<fieldset>
							<table class="tablaverde2" style="width: 900px" align="center">
								<tr valign="middle">
									<td align="center" width="900px">
										<table style="width: 900px" border="0">
											<tbody>
												<tr>
													<td>
														<input type="text" id="calleRegPatInput" size="50" maxlength="50" readonly="readonly"/>
													</td>
													<td>
														<input type="text" id="numExteriorRegPatInput" size="10" maxlength="50" readonly="readonly"/>
													</td>
													<td>
														<input type="text" id="numInteriorRegPatInput" size="10" maxlength="50" readonly="readonly"/>
													</td>
													<td colspan="2">
														<input type="text" id="coloniaRegPatInput" size="50" maxlength="50" readonly="readonly"/>
													</td>
												</tr>
												<tr>
													<td class="etiqueta2">Calle</td>
													<td class="etiqueta2">N&uacute;mero Exterior</td>
													<td class="etiqueta2">N&uacute;mero Interior</td>
													<td colspan="2" class="etiqueta2">Colonia</td>
												</tr>
												<tr>
													<td>
														<input type="text" id="municipioRegPatInput" size="50" maxlength="50" readonly="readonly" />
													</td>
													<td colspan="2">
														<input type="text" id="localidadRegPatInput" size="30" maxlength="50" readonly="readonly" />
													</td>
													<td>
														<input type="text" id="entidadFederativaRegPatInput" size="30" maxlength="50" readonly="readonly" />
													</td>
													<td>
														<input type="text" id="cpRegPatInput" size="10" maxlength="50" readonly="readonly" />
													</td>
												</tr>
												<tr>
													<td class="etiqueta2">Municipio</td>
													<td colspan="2" class="etiqueta2">Localidad</td>
													<td class="etiqueta2">Entidad Federativa</td>
													<td class="etiqueta2">Código Postal</td>
												</tr>
												<tr>
													<td colspan="5">
														<input type="text" name="actividadRegPatInput" id="actividadRegPatInput" size="100" maxlength="60"
															onkeyup="validaCampo('PermiteSoloLetrasYPunto','actividadRegPatInput','correccionForm')"
														 />
													</td>
												</tr>
												<tr>
													<td colspan="5" class="etiqueta2"><span class="required">*</span>Actividad</td><label for="actividadRegPatInput"></label>
												</tr>
												<tr>
													<td colspan="2">
														<input type="text" name="claseRegPatInput" id="claseRegPatInput" size="20" maxlength="2" onkeyup="validaCampo('PermiteSoloNumerosYPunto','claseRegPatInput','correccionForm')"/>
													</td>
													<td>
														<input type="text" name="fraccionRegPatInput" id="fraccionRegPatInput" size="20" maxlength="4" onkeyup="validaCampo('PermiteSoloNumerosYPunto','fraccionRegPatInput','correccionForm')"/>
													</td>
													<td colspan="2">
														<input type="text" name="primaRegPatInput" id="primaRegPatInput" size="20" maxlength="8" onkeyup="validaCampo('PermiteSoloNumerosYPunto','primaRegPatInput','correccionForm')"/>
													</td>
												</tr>
												<tr>
													<td colspan="2" class="etiqueta2"><span class="required">*</span>Clase</td><label for="claseRegPatInput"></label>
													<td class="etiqueta2"><span class="required">*</span>Fracción</td><label for="fraccionRegPatInput"></label>
													<td colspan="2" class="etiqueta2"><span class="required">*</span>Prima</td><label for="primaRegPatInput"></label>
												</tr>
												<tr>
													<td colspan="6" align="center">
														<div id="domicilioCentroTrabajoDIV">
															<a href="#" id="btnCorrigeCentro"><span class="boton">Corregir Domicilio Geográfico</span> </a>
														</div>													
													</td>
												</tr>
												
											</tbody>
										</table>
									</td>
								</tr>
							</table>
						</fieldset>
					</div>
				</div>

				<div id="dgsolicitudCorreccionUsoExclusivo"
					
					style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity =     70) !important;">
					<div id="wrapperDialogSolCorr5" style="background-color: #f2fff2;">
						<fieldset>
							<table class="tablaverde2" style="width: 770px" align="center">
								<tr valign="middle">
									<td align="center" width="900px">
										<table style="width: 900px">
											<tbody>
												<tr>
													<td align="left" class="etiqueta2">
														<label path="txRepLegal"><span class="required">*</span>Nombre y Firma del Patrón o Representante Legal:</label>
													</td>
													<td align="left">
														<input type="text" id="txRepLegalInput" name="txRepLegalInput" size="20" maxlength="80" 
															onkeyup="validaCampo('PermiteSoloLetrasYPunto','txRepLegalInput','correccionForm')"/>
														<label for="txRepLegalInput"></label>
													</td>
													<td align="left" class="etiqueta2">
														<label path="patron">Lugar:</label>
													</td>
													<td align="left">
														<input type="text" id="lugarPresentacion" size="20"  readonly="readonly" />
													</td>
													<td align="left" class="etiqueta2">
														<label path="patron">Fecha:</label>
													</td>
													<td align="left">
														<input type="text" id="fechaPresentacion" size="20" readonly="readonly" />
													</td>
												</tr>
											</tbody>
										</table>
									</td>
								</tr>
							</table>
						</fieldset>
					</div>
				</div>
				<input type="hidden" name="cveAuditorAsignado" id="cveAuditorAsignado" value="0"/>
				<input type="hidden" name="idTipoSolicitud" id="idTipoSolicitud" value="0"/>
				
				<input type="hidden" name="firmaElectronica" id="firmaElectronica" value=""/>
				<input type="hidden" name="cadenaOriginal" id="cadenaOriginal" value=""/>
				<input type="hidden" name="tipoCertificado" id="tipoCertificado" value=""/>
	
			</form>

			
			<div id="dgControladorVRP"
				
				style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity =     70) !important; display: none;">
				<br />
				<div class="menu_principal">
					<!--inicia menu principal-->
					<div align="center">
						<div class="centrado">
							<ul>
								<li><a> Registros Patronales Inscritos</a>
								</li>
							</ul>
						</div>
						<!--fin centrado-->
					</div>
				</div>
			
				<div id="wrapperDialogSolCorr6" style="background-color: #f2fff2;">
					<fieldset>
						<form action="/solicitud/correcion/consultar.do" method="post"
							id="correccionForm2">

							<table class="tablaverde2" style="width: 900px" align="center" border="0">
								<tr valign="middle">
									<td align="center" width="900px">
										<table style="width: 900px" border="0">
											<tbody>
												<tr>
													<td align="center" class="etiqueta2" width="100">
														<span class="required">*</span><label	path="patron">Registro Patronal:</label>
													</td>
													<td align="left">
														<input type="text" id="regPatronInsInput" size="20" maxlength="10" onkeyup="validaCampo('noCaracteresEspeciales','regPatronInsInput','correccionForm2')"/><label for="regPatronPrInput"/>
													</td>
													<td align="center">
														<a href="#" id="btnAddPatron"><span	class="boton">Agregar</span> </a>
													</td>
													<td align="center">
														<a href="#" id="btnDelPatron"><span	class="boton">Eliminar</span></a>
													</td>
													<td align="center">
														<a href="#" id="btnModPatron"><span	class="boton">Corregir Domicilio</span></a>
													</td>
													<td align="center">
														<a href="#" id="btnMod2Patron"><span	class="boton">Modificar</span></a>
													</td>
													<td align="center" width="30%"></td>
												</tr>

											</tbody>
										</table>
									</td>
								</tr>
							</table>
						</form>
						<table id="dtPatronesInscritos" style="width: 900px">
							<thead>
							</thead>
							<tbody>
							</tbody>
						</table>
					</fieldset>
				</div>
			</div>


			<br />
			<div id="dgsolicitudCorreccionCentro"
				
				style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity =     70) !important;">
				<div id="wrapperDialogSolCorr7" style="background-color: #f2fff2;">
					<fieldset>
						<table class="tablaverde2" style="width: 900px" align="center">
							<tr valign="middle">
								<td align="center" width="900px">
									<table style="width: 900px" border="0">
										<tbody>
											<tr>
												<td align="center">
													<a href="#" id="btnGuardar"><span class="boton">Presentar solicitud de corrección</span> </a>
												</td>
											</tr>

										</tbody>
									</table>
								</td>
							</tr>
						</table>
						
						<!-- <a href="#" id="btnFirmar" onclick="firmarDocumento()"><span class="boton">Firmar solicitud de corrección</span> </a> -->
				
				
				
						
					</fieldset>
				</div>
			</div>
		</div>	
	<div id="filtros">
		<jsp:include page="soliciudCorreccionPatInsMod.jsp" />
	</div>
	