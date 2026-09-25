<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">	
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<br><br>
<table class="tablaverde2" width="100%">
	<tr>
		<td colspan="2" width="100%">
			<form id="devDictamenSeguimientoCorreccionForm"  method="Post">
				<table class="tablaverde2" style="width: 100%">	
					<tr class="impar">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<label style="color: red;">*</label>
							<label>Fecha de  Aut. del aviso de Dict :</label>
						</td>
						<td align="left" width="25%">
							<input path="fecFechaEmiOf" id="fecAutDicSegCorr"  size="12" readonly="readonly" />
			                  <span class="boton_limpiar"  id="spnFechaDerivarDict">X</span>
							  <div id="labelfecAutDicSegCorrOperacion"></div>
						</td>
						<td align="center" colspan="2" class="etiqueta2" width="65%">
							<label style="color: red;">*</label>
							<label>Ejercicio a Dictaminar :</label>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<label style="color: red;"> *</label>
							<label>N&uacute;mero de Aviso :</label>
						</td >
						<td align="left" width="25%">
							<input  id="numFolioOficioOperacion" size="30" maxlength="30" onkeyup="this.value=this.value.toUpperCase()" onkeypress="return validarAlfaNumerico(event);" />
							<div id="labelNumFolioOficio"></div>
						</td>
						<td align="center" colspan="2" width="65%" id="ejercicio">
							<select  id="comboAnios">
								<option value="-1">--Por Favor Seleccione--</option>
							</select>
							<div id="labelEjercicio"></div>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<label>Funcionario que Registra: :</label>
						</td>
						<td align="left" colspan="3" width="80%">
							<input type="text" id="nombreFuncionarioDictamen" size="80" readonly="readonly"/>
						</td>
					</tr>
					<tr class="par">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr class="par">
						<td align="center" colspan="4">
							<input type="button" value="Guardar" onclick="generaOperacion(4);" />
						</td>
					</tr>
					<tr class="par">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr class="impar">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
				</table>

			</form>
		</td>
	</tr>
</table>

		
		