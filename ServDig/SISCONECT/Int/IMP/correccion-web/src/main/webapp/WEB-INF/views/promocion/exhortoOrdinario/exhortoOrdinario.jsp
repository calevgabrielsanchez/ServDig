<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
	<style>
		label.error { float: none; font-size:smaller; color: red; padding-left: .5em; vertical-align: top; }
		label  {  float: none; }
	</style>

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/delta/promocion/promocionCritSeleccion.js"></script>
		
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/delta/promocion/promocionExhortoOrdinario.js"></script>
		
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
		
	<div id="cuerpo">
		<div id="dialog-mensaje" title="Mensaje de SISCONET">
			
		</div>
		<div id="dialog-confirm-promocion" title="Promoci&oacute;n Ordinario">
			<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>El archivo se subira pero antes tiene que verificar si el archivo no esta abierto ¿?</p>
		</div>
		<div class="menu_principal" style="height: 2em !important;">
			<div align="center">
				<div class="centrado">
					<ul style="height: 1em !important;">
						<li><a id="tituloArriba">Promoci&oacute;n Ordinario</a></li>
					</ul>
				</div>
			</div>
		</div>
		<form:form modelAttribute="promocionCargaModel" method="POST" action="/promocion/carga/cargarXls">
			<div id="headerDialog" style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity = 70) !important;">
			<input type="hidden" id="idContexto" value="<%=request.getContextPath()%>">
			<input type="hidden" id="rutaCriterioSeleccion" value="<%=request.getContextPath()%>/promocion/carga/buscarCriteriosSeleccion.do">
			<input type="hidden" id="idTipo" value="5">
				<div id="cargaCedula" style="background-color: #f2fff2;" >
					<fieldset>
						<table style="width: 900px" align="center">
							<tr valign="middle">
								<td align="center" width="900px">
									<table class="tablaverde2" style="width: 900px">
										<tbody>
											<tr valign="top" class="impar">
												<td align="center" colspan="4"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></td>
											</tr>
											<tr>
												<td width="25%" align="left" class="etiqueta2"><label style="color: red;">* </label><label for="origenLbl" id="origenLbl">Origen de Promoci&oacute;n: </label></td>
												<td width="25%" align="left">
													<form:select path="idOrigen">
														<form:option value="-1" label="<-----Seleccione un Origen----->"/>
														<form:options items="${promocionCargaModel.origenes}" itemValue="id" itemLabel="descripcion" />
													</form:select>
													<label id="labelIdOrigen"></label>
												</td>
												<td width="50%" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
											</tr>
											<tr>
												<td colspan="4">
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												</td>
											</tr>
											<tr>
												<td width="35%" align="left" class="etiqueta2"><label style="color: red;">* </label><label for="criterioSeleccionLbl" id="criterioSeleccionLbl">Criterio de Selecci&oacute;n: </label></td>
												<td width="65%" align="left" colspan="2">
													<form:select path="idCriterio">
														<form:option value="-1" label="<-Seleccione un Criterio de Selección->" />
													</form:select>
													<label id="labelCriterio"></label>
												</td>
												<td align="center">
													<a href="#" id="btnBuscarCriterios"><span class="boton">Buscar Criterios</span></a>
												</td>
											</tr>
											<tr>
												<td colspan="4">
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												</td>
											</tr>
										</tbody>
									</table>
									<div id="wrapperDataTableCriteriosSeleccion" style="overflow: auto; width:950px; height:300px;" align="center" class="centrado">
										<table id="tableCriteriosSeleccion"  style="width: 900px" align="center">
										</table>
									</div>
								</td>
								
							</tr>
						</table>
					</fieldset>
				</div>
			</div>
			
			<div id="botonesPromoverOrdinario" style="background-color: #f2fff2;">
				<table style="width: 900px">				
					<tbody>
					<tr>
						<td>
					<table style="width: 300px" align="center" >
						<tr valign="top" class="impar">
								<td align="center"> 
									<div id="botonValidarRPOrdinario" class="tablaverde2" style="display:none">
										<a onclick="javascript:validarRPatronalOrdinarioProm();" href="#">
										<span class="boton">Validar Registro Patronal</span>
										</a>
									</div>								
								</td>
								<td align="center" colspan="2">&nbsp;&nbsp;</td>
								<td >
									<div id="botonPromoverRPOrdinario" class="tablaverde2" style="display:none">
										<a onclick="javascript:muestraConfirmacionPromocion();" href="#">
											<span class="boton">Promover</span>
										</a>
									</div>
								</td>
								
						</tr>
					</table>
						</td>
					</tr>	
					</tbody>
				</table>
			</div>				
			
			<div id="criterioSeleccionadoDivID" style="background-color: #f2fff2;">
				<fieldset>
					<table style="width: 900px" align="center">
						<tr valign="middle">
							<td align="center" width="900px">
							
									<table class="tablaverde2" style="width: 900px">
									<tbody>
										<tr valign="top" class="impar">
												<td align="center" colspan="4"><b>Origen de Promoci&oacute;n</b></td>
										</tr>
										<tr>
											<td colspan="4">
									  			<div id="labelSBC"></div>
										  	</td>
									  	</tr>
										<tr>
											<td align="left" width="25%" class="etiqueta2">Criterio de Selecci&oacute;n: </td>
											<td align="left" width="25%"><label id="descCriterioseleccion"> </label> </td>
											<td align="center" width="50%" colspan="2">
										  		
										  	</td>
										</tr>
									</tbody>
								</table>							
							
								<table class="tablaverde2" style="width: 900px">
									<tbody>
										<tr valign="top" class="impar">
												<td align="center" colspan="4"><b>Datos del Patr&oacute;n</b></td>
										</tr>
										<tr>
											<td colspan="4">
									  			<div id="labelSBC1"></div>
										  	</td>
									  	</tr>
										<tr>
											<td align="left" width="25%" class="etiqueta2">Registro Patronal: </td>
											<td align="left" width="25%"><label for="resgistroSeleccionado" id="resgistroPatronalSeleccionadoID"></label></td>
											<td align="left" width="25%" class="etiqueta2">Nombre o Raz&oacute;n Social</td>
										  	<td align="left" width="25%"><label id="razonSocialSeleccionadoID"></label></td>
										  	
										</tr>
									</tbody>
								</table>							
							
								<table class="tablaverde2" style="width: 900px">
									<tbody>
										<tr valign="top" class="impar">
												<td align="center" colspan="4"><b>Informaci&oacute;n del Oficio de Promoci&oacute;n</b></td>
										</tr>
										<tr>
											<td colspan="4">
									  			<div id="labelSBC2"></div>
										  	</td>
									  	</tr>
									  	<tr>
									  		<td align="left" width="25%" class="etiqueta2">
                                            	<span class="required">*</span><label id="fechaPromocionLabel" for="fechaPromocion">Fecha Oficio de Promoci&oacute;n:</label>																								
											</td>
											<td align="left" width="25%">
												<input name="fechaPromocionID" id="fechaPromocionID" maxlength="10" readonly="readonly"/>
											</td>
									  		<td align="left" width="25%" class="etiqueta2">
                                            	<span class="required">*</span><label id="numOficionLabel" for="numOficioLabel">N&uacute;mero de Oficio de la Promoci&oacute;n: </label>																								
											</td>
											<td align="left" width="25%">
												<input name="numeroOficioID" id="numeroOficioID" maxlength="25" onkeyup="validaCampo('noCaracteresEspeciales','numeroOficioID', 'promocionCargaModel');"/>
											</td>
									  	</tr>
									  	<tr>
									  		<td align="left" width="25%" class="etiqueta2">
                                            	<label id="observacionesLabel" for="observaciones">Observaciones:</label>																								
											</td>
											<td align="left" width="75%" colspan="3">
												<textarea rows="3" cols="92" id="observacionesId" onkeyup="validaCampo('noCaracteresEspeciales','observacionesId', 'promocionCargaModel');"></textarea>
											</td>
									  	</tr>
									  	<tr>
											<td colspan="4">
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
										</tr>
									</tbody>
								</table>
								<table class="tablaverde2" style="width: 900px">
									<tbody>
										<tr valign="top" class="impar">
												<td align="center" colspan="4"><b>Domicilio Geogr&aacute;fico</b></td>
										</tr>
										<tr valign="top">
											<td align="left" colspan="2" class="etiqueta2">
												<span class="required">*</span>Calle: 
												<input name="domCalle" id="domCalle" size="50" onkeyup="validaCampo('noCaracteresEspeciales','domCalle')" readonly="readonly" /> 
												<label for="domCalle"></label>
											</td>
											<td align="left" colspan="2" class="etiqueta2">
												<span class="required">*</span>Colonia: 
												<input name="refColonia" id="refColonia" size="50" onkeyup="validaCampo('noCaracteresEspeciales','refColonia')" readonly="readonly" /> 
												<label for="refColonia"></label>
											</td>
										</tr>
										<tr valign="top">
											<td align="left" class="etiqueta2">
												<span class="required">*</span>N&uacute;mero Exterior:
											</td>
											<td align="left" class="etiqueta2">
												<input name="numNroext" id="numNroext" size="12" onkeyup="validaCampo('noCaracteresEspeciales','numNroext')" readonly="readonly" /> 
												<label for="numNroext"></label>
											</td>
											<td align="left" class="etiqueta2">N&uacute;mero Interior:
											</td>
											<td align="left" class="etiqueta2">
												<input name="numNroint" id="numNroint" size="12" onkeyup="validaCampo('noCaracteresEspeciales','numNroint')" readonly="readonly" />
												<label for="numNroint"></label>
											</td>
										</tr>
										<tr valign="top">
											<td align="left" class="etiqueta2">
												<span class="required">*</span>C&oacute;digo Postal:
											</td>
											<td align="left" class="etiqueta2">
												<input name="numCodigopostal" id="numCodigopostal" size="12" onkeyup="validaCampo('noCaracteresEspeciales','numCodigopostal');validaCampo('PermiteSoloNumeros','numCodigopostal')" readonly="readonly" />
												<label for="numCodigopostal"></label>
											</td>
											<td align="left" colspan="2" valign="bottom">
												<div id="domInegiButtons" align="center">
													<a href="#" onclick="mostarDomGeo('<%=request.getContextPath() %>','registro')"><span class="boton">Agregar / Modificar Domicilio</span></a>
												</div>
											</td>
										</tr>
																			  	<tr>
											<td colspan="4">
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
										</tr>
										<tr>
											<td align="center" colspan="4" valign="bottom">
												<a href="#" id="btnPromocionar"><span class="boton">Guardar Promoci&oacute;n</span></a>
											</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</table>
				</fieldset>
			</div>
		</form:form>
	</div>
</html>