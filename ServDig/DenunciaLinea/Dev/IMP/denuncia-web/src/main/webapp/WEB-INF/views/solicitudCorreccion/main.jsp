<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<style>
	label.error { float: none; font-size:smaller; color: red; padding-left: .5em; vertical-align: top; }
	label  {  float: none; }
</style>

<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/delta/solicitud/correccion.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/jquery/jquery.blockUI.1.33.js"></script>

		<div id="cuerpo" class="">
			<form action="correcion/muestraReporte.do" method="post" id="correccionForm">
				<input type="hidden" id="cont" value="<%=request.getContextPath() %>">

				<div class="menu_principal" style="height: 2em !important;">
					<!--inicia menu principal-->
					<div align="center">
						<div class="centrado">
							<ul style="height: 1em !important;">
								<li><a> REGISTRO DE SOLICITUD DE CORRECI&Oacute;N</a>
								</li>
							</ul>
						</div>
						<!--fin centrado-->
					</div>
				</div>
				<br />


				<div id="dgsolicitudCorreccion" title="Solicitud Correccion"
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
														<span class="required">*</span><label>Registro	patronal a corregir: <label>
													</td>
													<td align="left">
														<input type="text" name="regPatronPrInput" id="regPatronPrInput" size="20" maxlength="10" onkeyup="validaCampo('noCaracteresEspeciales','regPatronPrInput','correccionForm');"/><label for="regPatronPrInput"></label>
													</td>
													<td align="center">
														<a href="#" id="btnBuscarPatron"><span class="boton">Buscar</span> </a>
													</td>
													
													<td align="left" class="etiqueta2">
														<label>Folio de correcci�n asignado: <label>
													</td>
													<td align="left" class="etiqueta2">
														<input type="text" name="folioInput" id="folioInput" size="50" readonly="readonly"/>
													</td>
												</tr>
												<tr valign="top" class="par">
													<td align="right" class="etiqueta2" colspan="3"></td>
													<td align="left" class="etiqueta2">
														<span class="required">*</span><label>Nombre o denominaci�n social:</label>
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
														<span class="required">*</span><label>Registro federal de contribuyentes:</label>
													</td>
													<td align="left">
														<input type="text" readonly="readonly" name="rfcRegPatInput" id="rfcRegPatInput" size="50" maxlength="13" onkeyup="validaCampo('noCaracteresEspeciales','rfcRegPatInput','correccionForm');"/><label for="rfcRegPatInput"></label>
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
														<label>Clave �nica de registro de poblacion:</label>
													</td>
													<td align="left">
														<input type="text" readonly="readonly" name="curpPatInput" id="curpPatInput" size="20" maxlength="18" onkeyup="validaCampo('noCaracteresEspeciales','curpPatInput','correccionForm');"/><label for="curpPatInput"></label>
													
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
				<div class="menu_principal" style="height: 2em !important;">
					<!--inicia menu principal-->
					<div align="center">
						<div class="centrado">
							<ul style="height: 1em !important;">
								<li><a> DOMICILIO FISCAL</a>
								</li>
							</ul>
						</div>
						<!--fin centrado-->
					</div>
				</div>
				<div id="dgsolicitudCorreccionDom" title="Domicilio Fiscal"
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
														<span class="required">*</span><label>Registro patronal del domicilio fiscal: </label>
													</td>
													<td align="left">
														<input type="text" name="regPatronInputDom" id="regPatronInputDom" size="20" maxlength="10" /><label for="regPatronInputDom"></label>
													</td>
													<td align="center">
														<a href="#" id="btnBuscarPatronDom"><span class="boton">Buscar</span> </a>
													</td>
												</tr>
												<tr>
													<td>
														<input type="text" id="calleRegPatInputDom" size="50" maxlength="50" readonly="readonly"/>
													</td>
													<td>
														<input id="numExteriorRegPatInputDom" size="10" maxlength="50" readonly="readonly"/>
													</td>
													<td>
														<input id="numInteriorRegPatInputDom" size="10" maxlength="50" readonly="readonly"/>
													</td>
													<td colspan="2">
														<input id="coloniaRegPatInputDom" size="50" maxlength="50" readonly="readonly"/>
													</td>
												</tr>
												<tr>
													<td class="etiqueta2">Calle</td>
													<td class="etiqueta2">N&uacute;mero exterior</td>
													<td class="etiqueta2">N&uacute;mero interior</td>
													<td colspan="2" class="etiqueta2">Colonia</td>
												</tr>

												<tr>
													<td>
														<input id="municipioRegPatInputDom" size="50" maxlength="50" readonly="readonly" />
													</td>
													<td colspan="2">
														<input id="localidadRegPatInputDom" size="30" maxlength="50" readonly="readonly" />
													</td>
													<td>
														<input id="entidadFederativaRegPatInputDom" size="30" maxlength="50" readonly="readonly" />
													</td>
													<td>
														<input id="codigoPostalRegPatInputDom" size="10" maxlength="50" readonly="readonly" />
													</td>
												</tr>
												<tr>
													<td class="etiqueta2">Municipio</td>
													<td colspan="2" class="etiqueta2">Localidad</td>
													<td class="etiqueta2">Entidad federativa</td>
													<td class="etiqueta2">C�digo postal</td>
												</tr>
												<tr>
													<td>
														<input type="text" name="telefonoRegPatInput" id="telefonoRegPatInput" size="20" maxlength="20" onkeyup="validaCampo('PermiteSoloNumeros','telefonoRegPatInput','correccionForm')"/><label for="telefonoRegPatInput"></label>
													</td>
													<td colspan="2">
														<input type="text" name="emailRegPatInput" id="emailRegPatInput" size="30" maxlength="30" /><label for="emailRegPatInput"></label>
													</td>
													<td colspan="2">
														<input type="text" id="subdelegacionRegPatInput" size="50" maxlength="50" readonly="readonly"/>
													</td>
												</tr>
												<tr>
													<td class="etiqueta2"><span class="required">*</span>Tel�fono</td>
													<td colspan="2" class="etiqueta2"><span class="required">*</span>Correo electr�nico</td>
													<td colspan="2" class="etiqueta2">Subdelegaci&oacute;n IMSS del domicilio fiscal</td>
												</tr>
												<tr>
													<td colspan="6" align="center">
														<a href="#" id="btnCorrigeDom"><span class="boton">Corregir Domicilio Geogr�fico</span> </a>													
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

				<div id="dgsolicitudCorreccionTipo" title="Tipo de Solicitud"
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
														<input type="checkbox" label="Registro de Obra" onchange="showDiv();" id="chkSolicitudObra"/> Construcci�n
													</td>
													<td align="center" width="100px" colspan="2" class="etiqueta2">
														<label id="numeroObraLabel" for="txPrima">N�mero de registro de obra: </label>
													</td>
													<td align="left" width="100px" colspan="4">
														<input id="numeroRegistroObra" disabled="disabled" size="30" maxlength="12" onkeyup="validaCampo('PermiteSoloNumeros','numeroRegistroObra','correccionForm')" readonly="readonly"/>
													</td>
													<td align="left" >
														<a href="#" id="btnBuscarObra" ><span class="boton">Validar obra de SATIC</span> </a>
													</td>
												</tr>
												<tr>
													<td align="left" width="100px" colspan="1"	class="etiqueta2" class="etiqueta2">
														<label id="tipoOrigenLabel"> Tipo correcci�n: </label>
													</td>
													<td align="left" width="100px" colspan="1" class="etiqueta2">
														<input type="radio"	name="tipoSol" value="ESPONTANEA" disabled="disabled">Espontanea
													</td>
													<td align="left" width="100px" colspan="2" class="etiqueta2">
														<input type="radio" name="tipoSol" value="PROMOCION" disabled="disabled"/>Promoci�n
													</td>
													<td align="left" width="100px" colspan="2" class="etiqueta2">
														<input type="radio" name="tipoSol" value="INVITACION" disabled="disabled"/>Invitaci�n
													</td>
													<td align="left" width="100px" colspan="2" class="etiqueta2">
														<label id="numeroFolioInvitacion" >Fecha de recepci�n de oficio: </label>
													</td>
													<td align="left" width="100px" colspan="2" class="etiqueta2">
														<input type="text" id="fechaFolio" readonly="readonly" />
													</td>
												</tr>
												<tr>
													<td align="left" class="etiqueta2">
														<label	path="patron">Ejercicio o periodo a regularizar:</label>
													</td>
													<td align="left">
														<input type="text" id="fechaInicial" size="15" readonly="readonly" />
													</td>
													<td align="left" class="etiqueta2">
														<label path="patron"> al: </label>
													</td>
													<td align="left">
														<input type="text" id="fechaFinal" size="15" readonly="readonly" />
													</td>
													<td align="left" class="etiqueta2">
														<label path="numeroTrabajadores"><span class="required">*</span>N�mero de trabajadores:</label>
													</td>
													<td align="left" colspan="4">
														<input type="text"	name="numeroTrabajadores" id="numeroTrabajadores" size="15" maxlength="3" onkeyup="validaCampo('PermiteSoloNumeros','numeroTrabajadores','correccionForm')"/><label for="numeroTrabajadores"></label>
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
								<li><a> DOMICILIO DE LA OBRA</a>
								</li>
							</ul>
						</div>
						<!--fin centrado-->
					</div>
				</div>
				<div id="dgsolicitudCorreccionObra" 
					title="Domicilio del registro de Obra"
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
													<td class="etiqueta2">N&uacute;mero exterior</td>
													<td class="etiqueta2">N&uacute;mero interior</td>
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
													<td class="etiqueta2">Entidad federativa</td>
													<td class="etiqueta2">C�digo postal</td>
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
								<li><a> DOMICILIO DEL CENTRO DE TRABAJO</a>
								</li>
							</ul>
						</div>
						<!--fin centrado-->
					</div>
				</div>
				<div id="dgsolicitudCorreccionCentro"
					title="Domicilio del centro de trabajo"
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
													<td class="etiqueta2">N&uacute;mero exterior</td>
													<td class="etiqueta2">N&uacute;mero interior</td>
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
													<td class="etiqueta2">Entidad federativa</td>
													<td class="etiqueta2">C�digo postal</td>
												</tr>
												<tr>
													<td colspan="5">
														<input type="text" name="actividadRegPatInput" id="actividadRegPatInput" size="100" maxlength="60" />
													</td>
												</tr>
												<tr>
													<td colspan="5" class="etiqueta2"><span class="required">*</span>Actividad</td><label for="actividadRegPatInput"></label>
												</tr>
												<tr>
													<td colspan="2">
														<input type="text" name="claseRegPatInput" id="claseRegPatInput" size="20" maxlength="5" onkeyup="validaCampo('PermiteSoloNumerosYPunto','claseRegPatInput','correccionForm')"/>
													</td>
													<td>
														<input type="text" name="fraccionRegPatInput" id="fraccionRegPatInput" size="20" maxlength="5" onkeyup="validaCampo('PermiteSoloNumerosYPunto','fraccionRegPatInput','correccionForm')"/>
													</td>
													<td colspan="2">
														<input type="text" name="primaRegPatInput" id="primaRegPatInput" size="20" maxlength="5" onkeyup="validaCampo('PermiteSoloNumerosYPunto','primaRegPatInput','correccionForm')"/>
													</td>
												</tr>
												<tr>
													<td colspan="2" class="etiqueta2"><span class="required">*</span>Clase</td><label for="claseRegPatInput"></label>
													<td class="etiqueta2"><span class="required">*</span>Fracci�n</td><label for="fraccionRegPatInput"></label>
													<td colspan="2" class="etiqueta2"><span class="required">*</span>Prima</td><label for="primaRegPatInput"></label>
												</tr>
												<tr>
													<td colspan="6" align="center">
														<a href="#" id="btnCorrigeCentro"><span class="boton">Corregir Domicilio Geogr�fico</span> </a>													
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
					title="Uso exclusivo del imss"
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
														<label path="txRepLegal"><span class="required">*</span>Nombre y firma del patr�n o representante legal:</label>
													</td>
													<td align="left">
														<input type="text" id="txRepLegalInput" name="txRepLegalInput" size="20" maxlength="100" /><label for="txRepLegalInput"></label>
													</td>
													<td align="left" class="etiqueta2">
														<label path="patron">Lugar:</label>
													</td>
													<td align="left">
														<input type="text" id="lugarPresentacion" size="20" maxlength="11" readonly="readonly" />
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
			</form>

			<br />
			<div class="menu_principal">
				<!--inicia menu principal-->
				<div align="center">
					<div class="centrado">
						<ul>
							<li><a> REGISTROS PATRONALES INSCRITOS</a>
							</li>
						</ul>
					</div>
					<!--fin centrado-->
				</div>
			</div>
			<div id="dgsolicitudCorreccionCentro"
				title="Domicilio del centro de trabajo"
				style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity =     70) !important;">
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
														<span class="required">*</span><label	path="patron">Registro patronal:</label>
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
				title="Domicilio del centro de trabajo"
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
													<a href="#" id="btnGuardar"><span class="boton">Presentar soicitud de correcci�n</span> </a>
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
		</div>	
	<div id="filtros">
		<jsp:include page="soliciudCorreccionPatInsMod.jsp" />
	</div>

