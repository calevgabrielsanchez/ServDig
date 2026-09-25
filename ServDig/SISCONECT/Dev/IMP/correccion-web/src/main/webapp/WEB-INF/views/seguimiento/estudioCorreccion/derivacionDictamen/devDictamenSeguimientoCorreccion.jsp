<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">	
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<br><br>
<table class="tablaverde2" width="100%">
	<tr>
		<td colspan="2" width="100%">
			<form:form id="devDictamenSeguimientoCorreccionForm" modelAttribute="derivDictamenTabVO" method="Post">
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
							<form:input path="fecFechaEmiOf" id="fecAutDicSegCorr" onchange="javascript:validaFechaDerivDictamen(this.value);" size="12" readonly="readonly" />
			                  <span class="boton_limpiar" onclick="javascript:limpiaFechaDerivacionDict()" id="spnFechaDerivarDict">X</span>
							  <div id="labelfecAutDicSegCorr"></div>
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
							<form:input path="numFolioOficio" id="numFolioOficio" size="30" maxlength="30" onkeyup="this.value=this.value.toUpperCase()" onkeypress="return validarAlfaNumerico(event);" />
							<div id="labelNumFolioOficio"></div>
						</td>
						<td align="center" colspan="2" width="65%" id="ejercicio">
							<form:select path="ejercicio" id="comboAnios">
								<option value="-1">--Por Favor Seleccione--</option>
							</form:select>
							<div id="labelEjercicio"></div>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<label>Funcionario que Registra: :</label>
						</td>
						<td align="left" colspan="3" width="80%">
							<input type="text" id="nombreFuncionario" size="80" readonly="readonly"/>
						</td>
					</tr>
					<tr class="par">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr class="par">
						<td align="center" colspan="4">
							<input type="button" value="Guardar" onclick="procesaFormularioDerivacionDict('validaCamposDerivacionDict()');" />
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
				<form:hidden path="cvePresentaCorr" id="cvePresentaCorr" />
				<form:hidden path="idTipoOficio" id="idTipoOficio" />
			</form:form>
		</td>
	</tr>
</table>

		
		