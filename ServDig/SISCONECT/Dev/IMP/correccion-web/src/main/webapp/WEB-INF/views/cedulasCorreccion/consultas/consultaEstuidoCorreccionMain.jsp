<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/tabControler.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/cedulasConstruccion/consultaEstudioCorreccion.js"></script>

<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/resources/estilos/styleTabs.css">
<%-- <link rel="stylesheet" type="text/css"	href="<%=request.getContextPath()%>/resources/estilos/cedulasStyle.css"> --%>

<div id="cuerpo">

	<div class="separadorseccion">
			<span>
				Consulta del Estudio de Correcci&oacute;n
			</span>
		</div>

	<form:form modelAttribute="CEC" action="consultaEstudioCorreccion/consultar.do"
						method="post" id="cecForm" onsubmit="return validaFormaCEC()">
				<fieldset>
					<input type="hidden" id="periodoSeleccionado" name="periodoSeleccionado" value ="${CEC.periodo}"/>
					<table style="width: 900px" align="center" border="0">
						<tr valign="middle">
							<td align="center" width="900px">
	
								<table class="tablaverde2" style="width: 900px; border-collapse: separate; border-spacing:  5px 5px;">
									<tbody>
										<tr valign="top" class="impar">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="230px" colspan="1" class="etiqueta2">
												<label style="color: red;">* </label>
												<form:label id="folioCorreccionLabel" for="folioCorreccion" path="folioCorreccion"
													cssErrorClass="error"> Folio Correcci&oacute;n: </form:label>
											</td>
											<td align="left" width="780px" colspan="3">
											<form:input	path="folioCorreccion" value="" label="Folio Corrección" maxlength="20"  id="folioCorreccion"
												onblur="this.value=(this.value).toUpperCase();obtenerPeriodos();" 
												onkeyup="validaCampo('noCaracteresEspeciales','folioCorreccion','cecForm');"
											/>
											<label id="labelFolioCorreccion"></label>
											</td>
										</tr>
										
										<tr valign="top" class="par">
												<td align="left" width="100px" colspan="1" class="etiqueta2">
												<span class="etiquetaError">*</span><label>Ejercicio:</label>
												<td align="left" width="100px" colspan="1">
													<form:select id="periodo" path="periodo" onchange="validaPeriodo()">
														<option id="" value="0">--Por favor seleccione--</option>
													</form:select>
													<form:label id="periodoLabel" for="periodo" path="periodo" 
														cssErrorClass="error"> </form:label>
												</td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="100px" colspan="1" class="etiqueta2"><form:label
													id="registroPatronalLabel" for="registroPatronal" path="registroPatronal" 
													cssErrorClass="error"> Registro Patronal: </form:label>
											</td>
											<td align="left" width="780px" colspan="3"><form:input
													path="registroPatronal" value="" label="registroPatronal" maxlength="10" id="registroPatronal"
													onkeyup="validaCampo('noCaracteresEspeciales','periodo','cecForm');"
													/>
											</td>
										</tr>
										<tr valign="top" class="impar">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>			
									</tbody>
								</table>
								<br>
								<table>
									<tbody>
										<tr align="center">
											<td align="center" width="100px" colspan="2">
										  		<input type="submit" value="Buscar" class="btn btn-primary btn-sm"> 
										  	</td>
										</tr>	
										<tr valign="top" class="par">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>
									</tbody>
								</table>							
									
							</td>
						</tr>
					</table>
										
				</fieldset>
							    
			<form:hidden path="error"/>				    
	</form:form>	

	<div id="dgConsultaEC" 
		style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity =   70) !important;">
		<table style="width: 900px" align="center" border="0">
			<tr valign="middle">
				<td align="left" width="900px">

					<table width="100%" align="left" border="0">
						<tr>
							<td>
								<ul class="tabs">
									<li class="etiqueta4" id="cedulaA_MIFORMALI"><a href="#cedulaA_MIFORMA" id="cedulaA_MIFORMALink">Cédula A</a></li>
									<li class="etiqueta4" id="cedulaGLI"><a href="#cedulaG" id="cedulaGLink">Cédula G </a></li>
									<li class="etiqueta4" id="cedulaHLI"><a href="#cedulaH" id="cedulaHLink">Cédula H</a></li>
									<li class="etiqueta4" id="cedulaILI"><a href="#cedulaI" id="cedulaILink">Cédula I</a></li>
									<li class="etiqueta4" id="cedulaOLI"><a href="#cedulaO" id="cedulaOLink">Cédula O</a></li>
									<li class="etiqueta4" id="cedulaQLI"><a href="#cedulaQ" id="cedulaQLink">Cédula Q</a></li>
									<li class="etiqueta4" id="cedulaCOPLI"><a href="#COP" id="cedulaCOPLink">C.O.P</a></li>
									
									<li class="etiqueta4" id="cedulaRLI"><a href="#cedulaR" id="cedulaRLink">Cédula R </a></li>		
								</ul>
							</td>
						</tr>
						<tr>
							<td>
								<div id="cedulaA_MIFORMA" class="tab_content"  style="border:solid 1px;border-color:black; width:900px; height:400px; overflow: auto;">${CEC.tablaCedulaA}</div>
								<div id="cedulaG" class="tab_content"  style="border:solid 1px;border-color:black; width:900px; height:400px; overflow: auto;">${CEC.tablaCedulaG}</div>
								<div id="cedulaH" class="tab_content"  style="border:solid 1px;border-color:black; width:900px; height:400px; overflow: auto;">${CEC.tablaCedulaH}</div>
								<div id="cedulaI" class="tab_content"  style="border:solid 1px;border-color:black; width:900px; height:400px; overflow: auto;">${CEC.tablaCedulaI}</div>
								<div id="cedulaO" class="tab_content"  style="border:solid 1px;border-color:black; width:900px; height:400px; overflow: auto;">${CEC.tablaCedulaO}</div>
								<div id="cedulaQ" class="tab_content"  style="border:solid 1px;border-color:black; width:900px; height:400px; overflow: auto;">${CEC.tablaCedulaQ}</div>
								<div id="COP" class="tab_content"  style="border:solid 1px;border-color:black; width:900px; height:400px; overflow: auto;">${CEC.tablaCop}</div>
								<div id="cedulaR" class="tab_content"  style="border:solid 1px;border-color:black; width:900px; height:400px; overflow: auto;">${CEC.tablaCedulaR}</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
		<input type="hidden" name="accessTabs" id="accessTabs" value="cedulaI-cedulaO-cedulaQ-CedulaCOP-cedulaA_MIFORMA-cedulaR-cedulaG-cedulaH">
		<input type="hidden" name="forbidenTabs" id="forbidenTabs" value="">
	</div>
			
</div>

<script>reloadPeriodos();</script>
<script>alertError();</script>

