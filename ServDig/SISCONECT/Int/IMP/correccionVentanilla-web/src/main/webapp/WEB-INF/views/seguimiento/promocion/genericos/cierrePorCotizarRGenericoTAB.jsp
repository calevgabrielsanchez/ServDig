<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">	
<table class="tablaverde2" width="100%">
	<tr>
		<td colspan="2" width="100%">
			<form id="cierrePorCotizarRGenericoTabForm" method="Post">
				<input type="hidden" name="cvePromocion" id="cvePromocionCCRG">
				<input type="hidden" name="cveUsuario" id="cveUsuarioCCRG">
				<input type="hidden" id="functionAuxCCRG">
				<input type="hidden" id="fechaAtencionGenericaCCRG">
				<table class="tablaverde2" style="width: 100%">		
					<tr class="impar">
						<td align="left" colspan="2">&nbsp;</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="25%">
							<label style="color: red;">* </label>
							<label>Fecha Cierre por Cot. Raz. :</label>
						</td>
						<td align="left" width="75%">
							<input name="fechaRegularizacion" id="fecCotizRazCCRG" size="12" readonly="readonly">
							<label id="labelFecCotizarRaz"></label>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="25%">
							<label>Funcionario que registra :</label>
						</td>
						<td align="left" width="75%">
							<label id="labelFuncionarioReg"></label>
						</td>
					</tr>
					<tr class="par">
						<td align="center" colspan="2" >
							<input type="button" class="boton" value="Guardar" onclick="javaScript:jsGuardarCCRG();">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" class="boton" value="Limpiar" onclick="javaScript:jsLimpiarCCRG();">
						</td>
					</tr>
					<tr class="impar">
						<td align="left" colspan="2">&nbsp;</td>
					</tr>
				</table>
			</form>
		</td>
	</tr>
</table>

		
		