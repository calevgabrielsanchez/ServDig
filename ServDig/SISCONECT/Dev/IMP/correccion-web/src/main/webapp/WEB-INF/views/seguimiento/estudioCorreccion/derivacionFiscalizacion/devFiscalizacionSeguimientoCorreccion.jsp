<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">	
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<br><br>
<table class="tablaverde2" width="100%">
	<tr>
		<td colspan="2" width="100%">
			<form:form id="devFiscalizacionSeguimientoCorreccionForm" modelAttribute="derivFiscalTabVO" method="Post">
				<table class="tablaverde2" style="width: 100%">	
					<tr class="impar">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<span class="required">*</span>
							<label>Folio de Oficio de Fiscalizaci&oacute;n :</label>
						</td>
						<td align="left" width="30%">
							<form:input path="numFolioOficio" id="numFolioOficio" size="25" maxlength="25" onkeyup="this.value=this.value.toUpperCase()" onkeypress="return validarAlfaNumerico(event);" />
							<div id="labelNumFolioOficio"></div>
						</td>
						<td align="left" class="etiqueta2" width="20%">
							<label style="color: red;">*</label>
							<label>Fecha de la Derivaci&oacute;n: :</label>
						</td>
						<td align="left" width="30%">
							<form:input path="fechaDeriva" id="fecDerivFisSegCorr" onchange="javascript:validaFechaDerivacionAFisc();" size="12" readonly="true" />
	                          <span class="boton_limpiar" onclick="javascript:limpiafecDerivFisSegCorr()" id="spnfecDerivFisSegCorr">X</span>
							  <div id="labelfecDerivFisSegCorr"></div>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2" width="20%">
							<label>Funcionario :</label>
						</td>
						<td align="left" colspan="3" width="80%">
							<input id="nombreFuncionario" size="70" readonly="readonly" />
						</td>
					</tr>
					<tr class="par">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr class="par">
						<td align="center" colspan="4">
							<input type="button" value="Guardar" onclick="procesaFormularioDerivacionfiscal('validaCamposDerivacionFiscal()');"/>
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
				<form:hidden path="cvePresentaCorr" name="cvePresentaCorr" id="cvePresentaCorr" />
			</form:form>
		</td>
	</tr>
</table>

		
		