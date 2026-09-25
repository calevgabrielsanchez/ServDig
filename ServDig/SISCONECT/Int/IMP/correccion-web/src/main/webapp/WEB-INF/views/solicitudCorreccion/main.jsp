<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<%@page import="mx.gob.imss.ctirss.correccion.session.UserSession"%>
<style>
	label.error { float: none; font-size:smaller; color: red; padding-left: .5em; vertical-align: top; }
	label  {  float: none; }
</style>
<script type="text/javascript"	src="http://framework-gb.cdn.gob.mx/data/encuesta_v1.0/qa/encuestas.js"></script>
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
		
		<div class="separadorseccion">
			<span>
				Registro de Solicitud de Correcci&oacute;n
			</span>
		</div>
	  	
		<div id="dgsolicitudCorreccion">
			<div id="wrapperDialogSolCorr" >
				<fieldset>
					<table>
						<tr>
							<td>
								<table style="border-collapse: separate; border-spacing:  5px 5px;">
									<tbody>
										<tr>
											<td>
												<label>Registro	Patronal a Corregir<span class="required">*</span>: <label>
											</td>
											<%if (((UserSession)request.getSession().getAttribute("USR_SESSION")).getCveIdPatron()!=null &&  ((UserSession)request.getSession().getAttribute("USR_SESSION")).getCveIdPatron()!=0 ){ %>
											<td>
												<input type="text" readonly="readonly" name="regPatronPrInput" style="text-align: center;" id="regPatronPrInput" size="20" maxlength="10" onkeyup="validaCampo('noCaracteresEspeciales','regPatronPrInput','correccionForm');" value="<%=((UserSession)request.getSession().getAttribute("USR_SESSION")).getRegistroPatronal().substring(0, 10)%>"/><label for="regPatronPrInput"></label>
											</td>
											<%}else{ %>
											<td>
												<input type="text" name="regPatronPrInput" style="text-align: center;" id="regPatronPrInput" size="20" maxlength="10" onkeyup="validaCampo('noCaracteresEspeciales','regPatronPrInput','correccionForm');"/><label for="regPatronPrInput"></label>
											</td>	
											<%} %>
											<td>   
												<a href="#" id="btnBuscarPatron"><span class="btn btn-primary btn-sm">Buscar</span> </a>
											</td>
											
											<td>
												<label>Folio de Corrección Asignado: <label>
											</td>
											<td>
												<input type="text" name="folioInput" id="folioInput" style="text-align: center;" size="42" readonly="readonly"/>
											</td>
										</tr>
										<tr>
											<td colspan="3">
												<input type="radio"
												       name="unoVariosRp" 
												       id="unoVariosRp" 
												       value="1" 
												       onclick="divControl('hide','dgControladorVRP'); gestionaVariosRPs('RP');divControl('hide','domicilioCentroTrabajoDIV');"
												       checked="checked"><label> Un Registro Patronal &nbsp;&nbsp; </label>
												<input type="radio" 
													   name="unoVariosRp" 
													   id="unoVariosRp" 
													   onclick="divControl('show','dgControladorVRP'); gestionaVariosRPs('VRP');divControl('show','domicilioCentroTrabajoDIV');"
													   value="2"><label> Varios Registros Patronales </label>
											</td>
											<td>
												<label>Nombre o Denominación Social<span class="required">*</span>:</label>
											</td>
											<td>
												<input type="text" readonly="readonly" name="razonSocialRegPatInput" id="razonSocialRegPatInput" size="42" maxlength="80" onkeyup="validaCampo('noCaracteresEspeciales','razonSocialRegPatInput','correccionForm');"/><label for="razonSocialRegPatInput"></label>
											</td>
										</tr>

										<tr>
											<td colspan="2">
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												<label>RFC<span class="required">*</span>:</label>
											</td>
											<td>
												<input type="text"  name="rfcRegPatInput" id="rfcRegPatInput" size="42" maxlength="13" onkeyup="validaCampo('noCaracteresEspeciales','rfcRegPatInput','correccionForm');"/><label for="rfcRegPatInput"></label>
											</td>
										</tr>
										
										<tr>
										<td colspan="2">
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												<label>CURP:</label>
											</td>
											<td>
												<input type="text"  name="curpPatInput" id="curpPatInput" size="20" maxlength="18" onkeyup="validaCampo('noCaracteresEspeciales','curpPatInput','correccionForm');"/><label for="curpPatInput"></label>
											
										</tr>
										<tr>
											<td>
												<label	path="patron">Ejercicio o Periodo a Regularizar Del:</label>
											</td>
											<td>
												<input type="text" id="fechaInicial" size="15" readonly="readonly" onchange="document.getElementById('fechaFinal').value=''" />
											</td>
											<td>
												<label path="patron"> Al: </label>
											</td>
											<td>
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
		
		<div class="separadorseccion">
			<span>
				Domicilio Fiscal
			</span>
		</div>
		<div id="dgsolicitudCorreccionDom">
			<div id="wrapperDialogSolCorr2">
				<fieldset>
					<table>
						<tr>
							<td>
								<table style="border-collapse: separate; border-spacing:  5px 5px;">
									<tbody>
										<tr>
											<td align="center">
												<label>Registro Patronal del Domicilio Fiscal<span class="required">*</span>: </label>
											</td>
											<td align="left">
												<input type="text" name="regPatronInputDom" id="regPatronInputDom" style="text-align: center;" size="20" maxlength="10" /><label for="regPatronInputDom"></label>
											</td>
											<td align="center">
												<a href="#" id="btnBuscarPatronDom"><span class="btn btn-primary btn-sm">Buscar</span> </a>
											</td>
										</tr>
										<tr>
											<td>
												<input type="text" id="calleRegPatInputDom" style="text-align: center;" size="50" maxlength="50" readonly="readonly"/>
											</td>
											<td>
												<input type="text" id="numExteriorRegPatInputDom" style="text-align: center;"  size="10" maxlength="50" readonly="readonly"/>
											</td>
											<td>
												<input type="text" id="numInteriorRegPatInputDom" style="text-align: center;" size="10" maxlength="50" readonly="readonly"/>
											</td>
											<td colspan="2">
												<input type="text" id="coloniaRegPatInputDom" style="text-align: center;" size="42" maxlength="50" readonly="readonly"/>
											</td>
										</tr>
										<tr>
											<td><label>Calle</label></td>
											<td><label>N&uacute;mero Exterior</label></td>
											<td><label>N&uacute;mero Interior</label></td>
											<td colspan="2"><label>Colonia</label></td>
										</tr>

										<tr>
											<td>
												<input type="text" id="municipioRegPatInputDom" style="text-align: center;" size="50" maxlength="50" readonly="readonly" />
											</td>
											<td colspan="2">
												<input type="text" id="localidadRegPatInputDom" style="text-align: center;" size="30" maxlength="50" readonly="readonly" />
											</td>
											<td>
												<input type="text" id="entidadFederativaRegPatInputDom" style="text-align: center;" size="27" maxlength="50" readonly="readonly" />
											</td>
											<td>
												<input type="text" id="codigoPostalRegPatInputDom" style="text-align: center;" size="10" maxlength="50" readonly="readonly" />
											</td>
										</tr>
										<tr>
											<td><label>Municipio</label></td>
											<td colspan="2"><label>Localidad</label></td>
											<td><label>Entidad Federativa</label></td>
											<td><label>Código Postal</label></td>
										</tr>
										<tr>
											<td>
												<input type="text" name="telefonoRegPatInput" style="text-align: center;" id="telefonoRegPatInput" size="20" maxlength="10" onkeyup="validaCampo('PermiteSoloNumeros','telefonoRegPatInput','correccionForm')"/><label for="telefonoRegPatInput"></label>
											</td>
											<td colspan="2">
												<input type="text" name="emailRegPatInput"  style="text-align: center;" id="emailRegPatInput" size="30" maxlength="30" /><label for="emailRegPatInput"></label>
											</td>
											<td colspan="2">
												<input type="text" id="subdelegacionRegPatInput" style="text-align: center;" size="42" maxlength="50" readonly="readonly"/>
											</td>
										</tr>
										<tr>
											<td><label>Teléfono<span class="required">*</span></label></td>
											<td colspan="2"><label>Correo Electrónico<span class="required">*</span></label></td>
											<td colspan="2"><label>Subdelegaci&oacute;n IMSS del Domicilio Fiscal</label></td>
										</tr>
										<tr>
											<td colspan="6" align="center">
												<div id="divBtnCorrigeDom">
													<a href="#" id="btnCorrigeDom"><span class="btn btn-primary btn-sm">Corregir Domicilio Geográfico</span> </a>
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
		<div id="dgsolicitudCorreccionTipo">
			<div id="wrapperDialogSolCorr3">
				<fieldset>
					<table>
						<tr>
							<td>
								<table style="border-collapse: separate; border-spacing:  5px 5px;">
									<tbody>
										<tr>
											<td align="left" width="100px" colspan="2">
												<input type="checkbox" label="Registro de Obra" onchange="showDiv();validaSeleccionConstrucion();" id="chkSolicitudObra"/><label> Construcci&oacute;n</label>
											</td>
											<td align="center" width="180px" colspan="2">
												<label id="numeroObraLabel" for="txPrima">Número de Registro de Obra: </label>
											</td>
											<td align="left" width="100px" colspan="4">
												<input type="text" id="numeroRegistroObra" disabled="disabled" size="30" maxlength="12" 
														onkeyup="validaCampo('PermiteSoloNumeros','numeroRegistroObra','correccionForm')" 
														onblur="validaExistenciaObra(this)"
														readonly="readonly"/>
											</td>
											<td align="left" >
												<a href="#" id="btnBuscarObra" ><span class="btn btn-primary btn-sm">Validar Obra de SATIC</span> </a>
											</td>
										</tr>
										<tr>
											<td align="left" width="164px" colspan="1">
												<label id="tipoOrigenLabel"> Tipo Corrección: </label>
											</td>
											<td align="left" width="100px" colspan="1">
												<input type="radio"	name="tipoSol" value="ESPONTANEA" disabled="disabled"><label>Espontanea</label>
											</td>
											<td align="left" width="100px" colspan="2">
												<input type="radio" name="tipoSol" value="PROMOCION" disabled="disabled"/><label>Promoción</label>
											</td>
											<td align="left" width="100px" colspan="2">
												<input type="radio" name="tipoSol" value="INVITACION" disabled="disabled"/><label>Invitación</label>
											</td>
											<td align="left" width="181px" colspan="2">
												<label id="numeroFolioInvitacion" >Fecha de Recepción de Oficio: </label>
											</td>
											<td align="left" width="100px" colspan="2">
												<input type="text" id="fechaFolio" readonly="readonly" />
											</td>
										</tr>
										<tr>
										
											<td align="left">
												<label path="numeroTrabajadores">Número de Trabajadores<span class="required">*</span>:</label>
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

		<div class="separadorseccion" id="divHeadObra" style="display:none">
			<span>
				Domicilio de la Obra
			</span>
		</div>
		  	
		<div id="dgsolicitudCorreccionObra" style="display:none">
			<div id="wrapperDialogSolCorrObra">
				<fieldset>
					<table>
						<tr>
							<td>
								<table style="border-collapse: separate; border-spacing:  5px 5px;">
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
											<td><label>Calle</label></td>
											<td><label>N&uacute;mero Exterior</label></td>
											<td><label>N&uacute;mero Interior</label></td>
											<td colspan="2"><label>Colonia</label></td>
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
											<td><label>Municipio</label></td>
											<td colspan="2"><label>Localidad</label></td>
											<td><label>Entidad Federativa</label></td>
											<td><label>Código Postal</label></td>
										</tr>
										<tr>
											<td colspan="6" align="center">
												<div id="botonObra">
													<a href="#" id="btnCorrigeDomObra"><span class="btn btn-primary btn-sm">Corregir Domicilio Geográfico</span> </a>
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

		<div class="separadorseccion">
			<span>
				Domicilio del Centro de Trabajo
			</span>
		</div>
		  	
		<div id="dgsolicitudCorreccionCentro">
			<div id="wrapperDialogSolCorr4">
				<fieldset>
					<table>
						<tr>
							<td>
								<table style="border-collapse: separate; border-spacing:  5px 5px;">
									<tbody>
										<tr>
											<td>
												<input type="text" id="calleRegPatInput" size="50" maxlength="50" readonly="readonly"/>
											</td>
											<td>
												<input type="text" id="numExteriorRegPatInput" size="11" maxlength="50" readonly="readonly"/>
											</td>
											<td>
												<input type="text" id="numInteriorRegPatInput" size="10" maxlength="50" readonly="readonly"/>
											</td>
											<td colspan="2">
												<input type="text" id="coloniaRegPatInput" size="41" maxlength="50" readonly="readonly"/>
											</td>
										</tr>
										<tr>
											<td><label>Calle</label></td>
											<td><label>N&uacute;mero Exterior</label></td>
											<td><label>N&uacute;mero Interior</label></td>
											<td colspan="2"><label>Colonia</label></td>
										</tr>
										<tr>
											<td>
												<input type="text" id="municipioRegPatInput" size="50" maxlength="50" readonly="readonly" />
											</td>
											<td colspan="2">
												<input type="text" id="localidadRegPatInput" size="30" maxlength="50" readonly="readonly" />
											</td>
											<td>
												<input type="text" id="entidadFederativaRegPatInput" size="27" maxlength="50" readonly="readonly" />
											</td>
											<td>
												<input type="text" id="cpRegPatInput" size="10" maxlength="50" readonly="readonly" />
											</td>
										</tr>
										<tr>
											<td><label>Municipio</label></td>
											<td colspan="2"><label>Localidad</label></td>
											<td><label>Entidad Federativa</label></td>
											<td><label>Código Postal</label></td>
										</tr>
										<tr>
											<td colspan="5">
												<input type="text" name="actividadRegPatInput" id="actividadRegPatInput" size="100" maxlength="60"
													onkeyup="validaCampo('PermiteSoloLetrasYPunto','actividadRegPatInput','correccionForm')"
												 />
											</td>
										</tr>
										<tr>
											<td colspan="5"><label for="actividadRegPatInput">Actividad<span class="required">*</span></label></td>
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
											<td colspan="2"><label for="claseRegPatInput">Clase<span class="required">*</span></td></label>
											<td><label for="fraccionRegPatInput">Fracción<span class="required">*</span></label></td>
											<td colspan="2"><label for="primaRegPatInput">Prima<span class="required">*</span></label></td>
										</tr>
										<tr>
											<td colspan="6" align="center">
												<div id="domicilioCentroTrabajoDIV">
													<a href="#" id="btnCorrigeCentro"><span class="btn btn-primary btn-sm">Corregir Domicilio Geográfico</span> </a>
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
	
		<br/>
		<div id="dgsolicitudCorreccionUsoExclusivo">
			<div id="wrapperDialogSolCorr5" >
				<fieldset>
					<table>
						<tr>
							<td>
								<table style="border-collapse: separate; border-spacing:  5px 5px;">
									<tbody>
										<tr>
											<td align="left">
												<label path="txRepLegal">Nombre y Firma del Patrón o Representante Legal<span class="required">*</span>:</label>
											</td>
											<td align="left">
												<input type="text" id="txRepLegalInput" name="txRepLegalInput" size="20" maxlength="80" 
													onkeyup="validaCampo('PermiteSoloLetrasYPunto','txRepLegalInput','correccionForm')"/>
												<label for="txRepLegalInput"></label>
											</td>
											<td align="left">
												<label path="patron">Lugar:</label>
											</td>
											<td align="left">
												<input type="text" id="lugarPresentacion" size="20"  readonly="readonly" />
											</td>
											<td align="left">
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

	<div id="dgControladorVRP" style="display: none;">
		<br />

		<div class="separadorseccion">
			<span>
				Registros Patronales Inscritos
			</span>
		</div>
	
		<div id="wrapperDialogSolCorr6" >
			<fieldset>
				<form action="/solicitud/correcion/consultar.do" method="post" id="correccionForm2">
					<table>
						<tr>
							<td>
								<table style="border-collapse: separate; border-spacing:  5px 5px;">
									<tbody>
										<tr>
											<td width="115px">
												<label	path="patron">Registro Patronal<span class="required">*</span>:</label>
											</td>
											<td align="left">
												<input type="text" id="regPatronInsInput" size="20" maxlength="10" onkeyup="validaCampo('noCaracteresEspeciales','regPatronInsInput','correccionForm2')"/><label for="regPatronPrInput"/>
											</td>
											<td align="center">
												<a href="#" id="btnAddPatron"><span	class="btn btn-primary btn-sm">Agregar</span> </a>
											</td>
											<td align="center">
												<a href="#" id="btnDelPatron"><span	class="btn btn-primary btn-sm">Eliminar</span></a>
											</td>
											<td align="center">
												<a href="#" id="btnModPatron"><span	class="btn btn-primary btn-sm">Corregir Domicilio</span></a>
											</td>
											<td align="center">
												<a href="#" id="btnMod2Patron"><span	class="btn btn-primary btn-sm">Modificar</span></a>
											</td>
										</tr>

									</tbody>
								</table>
							</td>
						</tr>
					</table>
				</form>
				<table id="dtPatronesInscritos" style="width: 960px" class="table table-striped table-bordered">
					<thead>
					</thead>
					<tbody>
					</tbody>
				</table>
			</fieldset>
		</div>
	</div>
	<br />
	
	<div id="dgsolicitudCorreccionCentro">
		<div id="wrapperDialogSolCorr7">
			<fieldset>
				<table align="center">
					<tr>
						<td>
							<table style="border-collapse: separate; border-spacing:  5px 5px;">
								<tbody>
									<tr>
										<td align="center">
											<a href="#" id="btnGuardar"><span class="btn btn-primary btn-sm">Presentar solicitud de corrección</span> </a>
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