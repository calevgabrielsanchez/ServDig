<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">	
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
 <%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<br><br>
<table class="tablaverde2" width="100%">
	<tr>
		<td colspan="2" width="100%">
			<form:form id="devSubDelegacionSeguimientoCorreccionForm" modelAttribute="correccionSeguimientoGenericoVO" method="Post">
			  
				<table class="tablaverde2" style="width: 100%">	
					<thead>
						<tr>
							<td colspan="4" class="etiqueta2" align="center">Domicilio Fiscal</td>
						</tr>
					</thead>
					<tr class="impar">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2">
							<label>Calle :</label>
						</td>
						<td align="left">
							<input name="" id="calleDerivarSubdel" type="text" size="50" maxlength="50" readonly="readonly" />
							<div id="labelCalleDerivarSubdel"></div>
						</td>
						<td align="left" class="etiqueta2">
							<label>Colonia :</label>
						</td>
						<td align="left">
							<input name="" type="text" id="coloniaDerivarSubdel" size="50" maxlength="50" readonly="readonly" />
							<div id="labelColoniaDerivarSubdel"></div>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2">
							<label>N&uacute;mero Exterior :</label>
						</td>
						<td align="left">
							<input name="" type="text" id="numExtDerivarSubdel" size="10" maxlength="50" readonly="readonly" />
							<div id="labelNumExtDerivarSubdel"></div>
						</td>
						<td align="left" class="etiqueta2">
							<label>N&uacute;mero Interior :</label>
						</td>
						<td align="left">
							<input name="" type="text" id="numIntDerivarSubdel" size="10" maxlength="50" readonly="readonly" />
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2">
							<label>Municipio :</label>
						</td>
						<td align="left">
							<input name="" type="text" id="municipioDerivarSubdel" size="40" maxlength="50" readonly="readonly" />
							<div id="labelMunicipioDerivarSubdel"></div>
						</td>
						<td align="left" class="etiqueta2">
							<label>Estado :</label>
						</td>
						<td align="left">
							<input name="" type="text" id="estadoDerivarSubdel" size="40" maxlength="50" readonly="readonly" />
							<div id="labelEstadoDerivarSubdel"></div>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2">
							<label>Codigo Postal :</label>
						</td>
						<td align="left">
							<input name="" type="text" id="codigoPostalDerivarSubdel" size="10" maxlength="50" readonly="readonly"/>
							<div id="labelCodigoPostalDerivarSubdel"></div>
						</td>
						<td align="left" colspan="2">
							<a href="#" onclick="obtenerDomGeo()">
							  <span class="boton" id="botonAgregarDomicilio">Corregir/Modificar Domicilio</span>
						    </a>
					    </td>

					</tr>
					<tr class="impar">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<thead>
						<tr>
							<td colspan="4" class="etiqueta2" align="center">Datos de la Derivaci&oacute;n</td>
						</tr>
					</thead>
					<tr class="impar">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2">
							<label style="color: red;">*</label>
							<label>Fecha Derivaci&oacute;n :</label>
						</td>
						<td align="left">
							<form:input path="derivSubDelTabVO.fechaDerivacion" onchange="validaFechaDerivacionSubdel();" id="fechaDerivacion" size="12" readonly="true" />
			                  <span class="boton_limpiar" onclick="javascript:limpiaFechaDerivacionSubdel()" id="spnFechaDerivarSubdel">X</span>
							  <div id="labelFechaDerivarSubdel"></div>
						</td>
						<td align="left" class="etiqueta2">
							<label style="color: red;">*</label>
							<label>Delegaci&oacute;n Destino :</label>
						</td>
						<td align="left">
								<combo:creaCombo entidad="mx.gob.imss.ctirss.correccion.catalogos.model.SacDelegacion"
										idHtml="cveDelegacion"
										idHtmlContenedor="devSubDelegacionSeguimientoCorreccionForm"										
										idHtmlValor="${derivSubDelTabVO.cveNuevaDelegacion}"
										/>			
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2"><span class="required">*</span>
							<label>Folio del Oficio de Derivaci&oacute;n :</label>
						</td>
						<td align="left">
							<form:input path="derivSubDelTabVO.folioDerivacion" id="folioDerivacion" size="35" maxlength="25" onkeyup = "this.value=this.value.toUpperCase()" onkeypress="return validarAlfaNumericoDevSub(event);" />
							<div id="labelFolioDerivarSubdel"></div>
						</td>
						<td align="left" class="etiqueta2">
							<label style="color: red;">*</label>
							<label>Subdelegaci&oacute;n Destino :</label>
						</td>
						<td align="left">						
							<combo:creaCombo entidad="mx.gob.imss.ctirss.correccion.catalogos.model.SacSubdelegacion"
									idHtml="selectNvaSubDeleg" 
									idHtmlPadre="cveDelegacion@sacDelegacion.cvePk"
									entidadPadre="mx.gob.imss.ctirss.correccion.catalogos.model.SacDelegacion"	
									idHtmlContenedor="devSubDelegacionSeguimientoCorreccionForm"
									idHtmlValor="${derivSubDelTabVO.idNuevaSubdelegacion},${derivSubDelTabVO.cveNuevaDelegacion}"
							/>	
							 <div id="labelIdSubdelDestDerivarSubdel"></div>
						</td>
					</tr>
					<tr class="par">
						<td align="left" class="etiqueta2">
							<label>Funcionario que Registra :</label>
						</td>
						<td align="left" colspan="3">
							<input id="nombreFuncionario" size="40" readonly="readonly" />
						</td>
					</tr>
					<tr class="par">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr class="par">
						<td align="center" colspan="4">
							<input type="button" value="Guardar" onclick="procesaFormularioDerivacionSubSegCorr('validaCamposDerivacionSub()');">
						</td>
					</tr>
					<tr class="par">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
					<tr class="impar">
						<td align="left" colspan="4">&nbsp;</td>
					</tr>
				</table>
				
				<form:hidden path="derivSubDelTabVO.idNuevaSubdelegacion" name="idNuevaSubdelegacion" id="idNuevaSubdelegacion" />
				<form:hidden path="derivSubDelTabVO.cveSolicitud" name="cveSolicitud" id="cveSolicitud" />
				<form:hidden path="derivSubDelTabVO.idAnteriorSubdelegacion" id="idAnteriorSubdelegacion" />
			</form:form>
		</td>
	</tr>
</table>

		
		