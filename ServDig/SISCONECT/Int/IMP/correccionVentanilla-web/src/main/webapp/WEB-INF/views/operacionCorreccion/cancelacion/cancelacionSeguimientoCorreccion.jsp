<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">	
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
 <%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<br><br>
<table class="tablaverde2" width="100%">
	<tr>
		<td colspan="2" width="100%">
			<form id="cancelacionSeguimientoCorreccionForm"  >
				<table class="tablaverde2" style="width: 100%">	
				   <tr class="impar">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<label style="color: red;">*</label>
							<label>Referencia de Cancelaci&oacute;n :</label>
						</td>
						<td align="left" width="30%">

							<input name="numFolioOficio" id="refCancelacion" size="30" maxlength="25" onkeyup = "this.value=this.value.toUpperCase()" onkeypress="return jsvalidarAlfaNumerico(event);">
							<label id="labelRefCancelacion"></label>
						</td>
						<td align="left" class="etiqueta2" width="20%">
							<label>Funcionario que Registra :</label>
						</td>
						<td align="left" width="30%">
							<label id="labelFuncionarioReg"></label>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<label style="color: red;">*</label>
							<label>Fecha de Cancelaci&oacute;n :</label>
						</td>
						<td align="left" width="30%">
							<input name="fecFechaEmiOf" id="fecCancelacionSegCorr" size="12" readonly="readonly"><span id="spnfecDeCancelacion"  class="boton_limpiar">X</span>
							<label id="labelFecCancelacionSegCorr"></label>
						</td>
						<td align="left" class="etiqueta2" width="20%">
							<label>Funcionario que Autoriza :</label>
						</td>
						<td align="left" width="30%">
							<label id="labelFuncionarioAut"></label>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<label style="color: red;">*</label>
							<label>Motivo de Cancelaci&oacute;n :</label>
						</td>
						<td align="left" width="80%" colspan="3">
							<select id="cboMotivos" name="idMotivoCancelacion">
								<option value="-1">--Por Favor Seleccione--</option>
							</select>
  							<label id="labelIdMotivocancelacion"></label>
						</td>
					</tr>
					<tr class="par">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr class="par">
						<td align="center" colspan="4">
							<input type="button" value="Guardar" id="btnGuardarCanelacion" onclick="generaOperacion(5);">
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

		
		