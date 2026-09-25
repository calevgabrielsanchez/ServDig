<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">	
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<br><br>
<table class="tablaverde2" width="100%">
	<tr>
		<td colspan="2" width="100%">
			<form id="reactivacionSeguimientoCorreccionForm"  method="Post">
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
							<input path="fechaSolReactiva" id="fechaSolReactiva"  size="12" readonly="true" />
	                          <span class="boton_limpiar" onclick="javascript:reseteaFecha('fechaSolReactiva');" id="spnfechaSolReactiva">X</span>
							  <div id="labelfechaSolReactiva"></div>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<label style="color: red;">*</label>
							<label>Fecha Env&iacute;o Solicitud Normativo :</label>
						</td>
						<td align="left" width="30%">
							<input path="fechaEnvioSol" id="fechaEnvioSol"  size="12" readonly="true" />
	                          <span class="boton_limpiar" onclick="javascript:reseteaFecha('fechaEnvioSol');" id="spnfechaEnvioSol">X</span>
							  <div id="labelfechaEnvioSol"></div>
						</td>
						<td align="left" class="etiqueta2" width="20%">
							<label style="color: red;">*</label>
							<label>N&uacute;mero de Oficio :</label>
						</td>
						<td align="left" width="30%">
							<input path="numOficioEnvio" id="numOficioEnvio" size="25" maxlength="25" onkeyup = "this.value=this.value.toUpperCase()" onkeypress="return validarAlfaNumericoReactivacion(event);" />
							<div id="labelNumOficioEnvio"></div>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<label style="color: red;">*</label>
							<label>Fecha Reactivaci&oacute;n: :</label>
						</td>
						<td align="left" width="30%">
							<input path="fechaReactiva" id="fechaReactiva"  size="12" readonly="true" />
	                          <span class="boton_limpiar" onclick="javascript:reseteaFecha('fechaReactiva');" id="spnfechaReactiva">X</span>
							  <div id="labelfechaReactiva"></div>
						</td>
						<td align="left" class="etiqueta2" width="20%">
							<label style="color: red;">*</label>
							<label>N&uacute;mero de Oficio Reactivaci&oacute;n :</label>
						</td>
						<td align="left" width="30%">
							<input path="numOficioReactiva" id="numOficioReactiva" size="25" maxlength="25" onkeyup = "this.value=this.value.toUpperCase()" onkeypress="return validarAlfaNumericoReactivacion(event);" />
							<div id="labelNumOficioReactiva"></div>
						</td>
					</tr>
					
					
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<label>Funcionario :</label>
						</td>
						<td align="left" colspan="3" width="80%">
							<input path="nombreFuncionarioReactiva" id="nombreFuncionarioReactiva" size="70" readonly="true" />
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
<!-- 							<input type="button" id="botonGuardar" value="Guardar" onclick="generaOperacion(3);"/> -->
							<input type="button" id="botonReactivar"  value="Reactivar" onclick="generaOperacion(3);"/>
						</td>
					</tr>
					<tr class="par">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr class="impar">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
				</table>
				<input id="fechaDeriva"  type="hidden" />
				<input id="folioSolicitud"  type="hidden" />
				
			</form>
		</td>
	</tr>
</table>

		
		