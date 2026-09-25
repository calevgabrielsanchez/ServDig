<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">	
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<br><br>
<table class="tablaverde2" width="100%">
	<tr>
		<td colspan="2" width="100%">
			<form:form id="reactivacionSeguimientoCorreccionForm" modelAttribute="reactivacionTabVO" method="Post">
				<table class="tablaverde2" style="width: 100%">	
					<tr class="impar">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<label style="color: red;">*</label>
							<label>Fecha Solicitud Reactivaci&oacute;n: :</label>
						</td>
						<td align="left" width="30%" colspan="3">
							<form:input path="fechaSolReactiva" id="fechaSolReactiva" onchange="javascript:validaFechaSolReactiva();" size="12" readonly="true" />
	                          <span class="boton_limpiar" onclick="javascript:reactivacionLimpiaFechaSolReactiva();" id="spnfechaSolReactiva">X</span>
							  <div id="labelfechaSolReactiva"></div>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<label style="color: red;">*</label>
							<label>Fecha Env&iacute;o Solicitud Normativo :</label>
						</td>
						<td align="left" width="30%">
							<form:input path="fechaEnvioSol" id="fechaEnvioSol" onchange="javascript:validaFechaEnvioSol(this.value);" size="12" readonly="true" />
	                          <span class="boton_limpiar" onclick="javascript:reactivacionLimpiaFechaEnvioSol();" id="spnfechaEnvioSol">X</span>
							  <div id="labelfechaEnvioSol"></div>
						</td>
						<td align="left" class="etiqueta2" width="20%">
							<label style="color: red;">*</label>
							<label>N&uacute;mero de Oficio :</label>
						</td>
						<td align="left" width="30%">
							<form:input path="numOficioEnvio" id="numOficioEnvio" size="25" maxlength="25" onkeyup = "this.value=this.value.toUpperCase()" onkeypress="return validarAlfaNumericoReactivacion(event);" />
							<div id="labelNumOficioEnvio"></div>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<label style="color: red;">*</label>
							<label>Fecha Reactivaci&oacute;n: :</label>
						</td>
						<td align="left" width="30%">
							<form:input path="fechaReactiva" id="fechaReactiva" onchange="javascript:validaFechaReactiva(this.value);" size="12" readonly="true" />
	                          <span class="boton_limpiar" onclick="javascript:reactivacionLimpiaFechaReactivacion();" id="spnfechaReactiva">X</span>
							  <div id="labelfechaReactiva"></div>
						</td>
						<td align="left" class="etiqueta2" width="20%">
							<label style="color: red;">*</label>
							<label>N&uacute;mero de Oficio Reactivaci&oacute;n :</label>
						</td>
						<td align="left" width="30%">
							<form:input path="numOficioReactiva" id="numOficioReactiva" size="25" maxlength="25" onkeyup = "this.value=this.value.toUpperCase()" onkeypress="return validarAlfaNumericoReactivacion(event);" />
							<div id="labelNumOficioReactiva"></div>
						</td>
					</tr>
					
					
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<label>Funcionario :</label>
						</td>
						<td align="left" colspan="3" width="80%">
							<form:input path="nombreFuncionario" id="nombreFuncionario" size="70" readonly="true" />
						</td>
					</tr>
					<tr class="par">
						<td align="left" colspan="4">
						    <span class="etiqueta2"> Observaciones: <BR> </span>
	                        <textarea rows="4" cols="92" name="txObservaciones" id="txObservacionesReactivaSegCorr" onkeydown="if(this.value.length >= 200){ this.value=this.value.substring(0,200); }"></textarea>
						</td>
					</tr>
					<tr class="par">
						<td align="center" colspan="4">
							<input type="button" id="botonGuardar" value="Guardar" onclick="guardarDatosReactivacion();"/>
							<input type="button" id="botonReactivar"  value="Reactivar" onclick="reactivar();"/>
						</td>
					</tr>
					<tr class="par">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr class="impar">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
				</table>
				
				<form:hidden path="cveSolCorr" name="cveSolCorr" id="cveSolCorr" />
				<form:hidden path="cveRevDerivAFisca" name="cveRevDerivAFisca" id="cveRevDerivAFisca" />
				<input id="fechaDeriva"  type="hidden" />
				<input id="folioSolicitud"  type="hidden" />
				
			</form:form>
		</td>
	</tr>
</table>

		
		