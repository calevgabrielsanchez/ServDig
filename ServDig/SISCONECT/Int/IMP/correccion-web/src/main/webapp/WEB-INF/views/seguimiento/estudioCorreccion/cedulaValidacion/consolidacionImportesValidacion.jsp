	<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">	
	<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	
	
	<table style="width: 900px" align="center" class="tablaverde2">
		<thead>
			<tr>
				<td align="center" colspan="9">Consolidaci&oacute;n de importes por aclarar (Total RP´s y Total Ejercicios)</td>
			</tr>
		</thead>
		
		<tbody>
			<tr valign="top" class="impar">
				<td align="left" colspan="9">&nbsp;</td>
			</tr>
			<tr class="par">
				<td align="center" class="etiqueta2" width="80%" colspan="7">
				</td>
				<td align="center" class="etiqueta2" width="10%" >
					<label> COP </label>
				</td> 
				<td align="center" class="etiqueta2" width="10%" >
					<label> RCV </label>
				</td>  
			</tr>	
			<tr class="par">
				<td align="right" class="etiqueta2" width="10%" >
					<span class="required" id="spanReqTRabRegulaSegCorr_cedval" style="display:none;">*</span><label>% Avance</label>
				</td> 
				<td align="left"  width="5%">
					<form:input path="consolidaImporteVo.porcAvance" id="porcentajeAvanceSegCorr_cedval" size="5" maxlength="3" 
						onkeyup="validaCampo('PermiteSoloNumeros','porcentajeAvanceSegCorr_cedval','formCedValidacionSeguimientoCorr');validaPorcentajeConsolImpteCedVal('porcentajeAvanceSegCorr_cedval','labelPorcAvanceGenSegCorr_cedval')"/>
					<div id="labelPorcAvanceGenSegCorr_cedval" class="etiqueta2"></div>
				</td>
				<td align="left" class="etiqueta2" width="5%">
					&nbsp;&nbsp;
				</td>
				<td align="left" class="etiqueta2" width="15%">
					<label>Trab. Revisados</label>
				</td>
				<td align="left" width="15%" >
					<form:input path="consolidaImporteVo.trabRevisados" id="trabRevisadosSegCorr_cedval" maxlength="5" size="7" 
						onkeyup="validaRangoEntero(this,'0','9999');$('form#formCedValidacionSeguimientoCorr #labelTrabRevisadosSegCorr_cedval').html('');" 
						onblur="moneyMask(this,0);calcTotalTrabRegularizadoConsolImpteCedVal()"/> 
					<div id="labelTrabRevisadosSegCorr_cedval" class="etiqueta2"></div>
				</td>
				<td align="left" class="etiqueta2" width="2%">
					&nbsp;&nbsp;
				</td>		
				<td align="left" class="etiqueta2" width="10%">
					<label>Suerte Ppal Det.</label>
				</td>
				<td align="left"  width="14%">
					<form:input path="consolidaImporteVo.suertePpalDetCOP" id="suertePpalDetCopSegCorr_cedval" maxlength="13" size="18"
						onkeyup="validaCampo('PermiteSoloNumerosComaYPunto','suertePpalDetCopSegCorr_cedval','formCedValidacionSeguimientoCorr');validaRangoDouble(this,'0','999999999');$('form#formCedValidacionSeguimientoCorr #labelSuertePpalDetCopSegCorr_cedval').html('');$('form#formCedValidacionSeguimientoCorr #labelSuertePpalDetCopSegCorr_cedval').html('');" 
						onblur="moneyMask(this,2);validaMontoSuertePpalBaseDeterminadaCedVal()" 
					/>
					<div align="right" class="etiqueta2" id="labelSuertePpalDetCopSegCorr_cedval"></div>
				</td>
				<td align="left"  width="14%">			
					<form:input path="consolidaImporteVo.suertePpalDetRCV" id="suertePpalDetRcvSegCorr_cedval" maxlength="13" size="18"
						onkeyup="validaCampo('PermiteSoloNumerosComaYPunto','suertePpalDetRcvSegCorr_cedval','formCedValidacionSeguimientoCorr');validaRangoDouble(this,'0','999999999');$('form#formCedValidacionSeguimientoCorr #suertePpalDetRcvSegCorr_cedval').html('');$('form#formCedValidacionSeguimientoCorr #labelSuertePpalDetRcvSegCorr_cedval').html('');"
						onblur="moneyMask(this,2);validaMontoSuertePpalRCVBaseDeterminadaCedVal()"
					/>
					<div id="labelSuertePpalDetRcvSegCorr_cedval" class="etiqueta2"></div>
				</td>										
			</tr>
			<tr class="par">
				<td align="right" class="etiqueta2" width="10%">
					<span class="required" id="spanReqTRabRegulaSegCorr_cedval" style="display:none;">*</span><label>% Regularizado</label>
				</td> 
				<td align="left" width="10%" >
					<form:input path="consolidaImporteVo.porcRegularizado" id="porcentajeRegularizadoSegCorr_cedval" size="5" maxlength="3"
						onkeyup="validaCampo('PermiteSoloNumeros','porcentajeRegularizadoSegCorr_cedval','formCedValidacionSeguimientoCorr');validaPorcentajeConsolImpteCedVal('porcentajeRegularizadoSegCorr_cedval','labelPorcRegularizadoSegCor_cedval')" />
						<div id="labelPorcRegularizadoSegCor_cedval" class="etiqueta2"></div>
				</td>
				<td align="left" class="etiqueta2" width="5%">
					&nbsp;&nbsp;
				</td>
				<td  align="left" class="etiqueta2" width="15%">
					<label>Trab. Omisos (NSS Unicos) </label>
				</td>
				<td  align="left" width="15%">
					<form:input  id="trabOmisosUniSegCorr_cedval" path="consolidaImporteVo.trabOmisos" maxlength="5" size="7" 
						onblur="moneyMask(this,0);calcTotalTrabRegularizadoConsolImpteCedVal()" onkeyup="validaRangoEntero(this,'0','9999');$('form#formCedValidacionSeguimientoCorr #labelTrabOmisosUniSegCorr_cedval').html('');"/>
						<div id="labelTrabOmisosUniSegCorr_cedval" class="etiqueta2"></div>
				</td>
				<td align="left" class="etiqueta2" width="2%">
					&nbsp;&nbsp;
				</td>
				<td align="left" class="etiqueta2" width="10%">
					<label>Suerte Ppal Pent Pago</label>
				</td>
				<td align="left"  width="14%">
					<form:input path="consolidaImporteVo.suertePpalPentPagoCOP" id="suertePpalPenPagoCopSegCorr_cedval" readonly="true" disabled="disabled"/>
				</td>
				<td align="left" width="14%">
					<form:input path="consolidaImporteVo.suertePpalPentPagoRCV" id="suertePpalPenPagoRcvSegCorr_cedval" readonly="true" disabled="disabled"/>
				</td>		
			</tr>
			<tr class="par">
				<td align="left" class="etiqueta2" width="10%">
				</td>
				<td align="left" width="10%">
				</td> 
				<td align="left" class="etiqueta2" width="5%">
					&nbsp;&nbsp;
				</td>
				<td  align="left" class="etiqueta2" width="15%">
					<label>Trab. Subdeclarados (NSS unicos) </label>
				</td>
				<td  align="left"  width="15%">
					<form:input  id="trabSubdeclaUniSegCorr_cedval" path="consolidaImporteVo.trabSubdeclarados"  maxlength="5" size="7" 
						onblur="moneyMask(this,0);calcTotalTrabRegularizadoConsolImpteCedVal()" onkeyup="validaRangoEntero(this,'0','9999');$('form#formCedValidacionSeguimientoCorr #labelTrabSubdeclaUniSegCorr_cedval').html('');"	/>
						<div id="labelTrabSubdeclaUniSegCorr_cedval" class="etiqueta2"></div>
				</td>
				<td align="left" class="etiqueta2" width="2%">
					&nbsp;&nbsp;
				</td>
				<td align="left" class="etiqueta2" width="10%">
					<label >Suerte Principal </label>
				</td>
				<td align="left"  width="14%">
					<form:input path="consolidaImporteVo.suertePrincipalCOP" id="suertePrincipalCopSegCorr_cedval" disabled="disabled" readonly="true"/>
				</td>	
				<td align="left" width="14%">
					<form:input path="consolidaImporteVo.suertePrincipalRCV" id="suertePrincipalRcvSegCorr_cedval" readonly="true" disabled="disabled"/>
				</td>	
			</tr>
			<tr class="par">
				<td align="right" class="etiqueta2" width="10%">
					<label>N&uacute;m. de Parcialidades </label>
				</td>
				<td align="left"  width="10">
					<form:input path="consolidaImporteVo.numParcialidades" id="numeroParcialidadesSegCorr_cedval" size="4" maxlength="2" 
						onkeyup="validaCampo('PermiteSoloNumeros','numeroParcialidadesSegCorr_cedval','formCedValidacionSeguimientoCorr')" onblur="validaParcialidadesSegCorrCedVal()"/>
						<div id="labelNumParcialidiSegCorr_cedval" class="etiqueta2"></div>
				</td>
				<td align="left" class="etiqueta2" width="5%">
					&nbsp;&nbsp;
				</td> 
				<td  align="left" class="etiqueta2" width="15%">
					<label>Trab. Regularizados </label>
				</td>
				<td  align="left" width="15%">
					<form:input  id="trabRegularizadosRegObraSegCorr_cedval" path="consolidaImporteVo.trabRegularizados" readonly="true" disabled="disabled" 
						onchange="moneyMask(this,0)"/>
					<div id="labelTrabRegularizadosRegObraSegCorr_cedval"></div>
				</td>
				<td align="left" class="etiqueta2" width="2%">
					&nbsp;&nbsp;
				</td>
				<td align="left" class="etiqueta2" width="10%">
					<label>Actualizaci&oacute;n</label>
				</td>
				<td align="left" width="14%">
					<form:input path="consolidaImporteVo.actualizacionCOP" id="actualizacionCopSegCorr_cedval" disabled="disabled" readonly="true" />
				</td>
				<td align="left" width="14%">
					<form:input path="consolidaImporteVo.actualizacionRCV" id="actualizacionRcvSegCorr_cedval" readonly="true" disabled="disabled"/>
				</td>		
			</tr>
			<tr class="par">
				<td align="right" class="etiqueta2" width="20%" colspan="2">
					<form:checkbox path="comprobanteConvenio" id="cbxComprobConvenio_cedval" />
					<label>Comprobante de convenio</label>
				</td>
				<td align="left" class="etiqueta2" width="5%">
					&nbsp;&nbsp;
				</td> 
				<td  align="left" class="etiqueta2" width="15%">
					<label>Base determinada </label>
				</td>
				<td  align="left" width="15%">
					<form:input  id="baseDeterminadaSegCorr_cedval" path="consolidaImporteVo.baseDeterminada" maxlength="11" size="15" disabled="disabled" readonly="true"
						onkeyup="moneyMask(this,0);$('form#formCedValidacionSeguimientoCorr #labelBaseDeterminadaSegCorr_cedval').html('');" />
						<div id="labelBaseDeterminadaSegCorr_cedval"></div>
				</td>
				<td align="left" class="etiqueta2" width="2%">
					&nbsp;&nbsp;
				</td>
				<td align="left" class="etiqueta2" width="10%">
					<label>Recargos</label>
				</td>
				<td align="left"  width="14%">
					<form:input path="consolidaImporteVo.recargosCOP" id="recargosCopSegCorr_cedval" disabled="disabled" readonly="true"/>
				</td>
				<td align="left" width="14%">
					<form:input path="consolidaImporteVo.recargosRCV" id="recargosRcvSegCorr_cedval" readonly="true" disabled="disabled"/>
				</td>		
			</tr>
			<tr class="par">
				<td align="right" class="etiqueta2" width="10%">
					&nbsp;
				</td>
				<td align="left" width="10%">
				</td>
				<td align="left" class="etiqueta2" width="5%" >
					&nbsp;&nbsp;
				</td> 
				<td  align="left" class="etiqueta2" width="15%">
					&nbsp;
				</td>
				<td  align="left" class="etiqueta2" width="15%">
					&nbsp;
				</td>
				<td align="left" class="etiqueta2" width="2%">
					&nbsp;&nbsp;
				</td>
				<td align="left" class="etiqueta2" width="10%">
					<label>Total Pagado : </label>
				</td>
				<td align="left" width="14%">
					<form:input path="consolidaImporteVo.totalPagadoCOP" id="totalPagadoCopSegCorr_cedval" disabled="disabled" readonly="true"/>
				</td>
				<td align="left" width="14%">
					<form:input path="consolidaImporteVo.totalPagadoRCV" id="totalPagadoRcvSegCorr_cedval" readonly="true" disabled="disabled"/>
				</td>		
			</tr>
			<tr class="par">
				<td align="right" class="etiqueta2" width="15%" colspan="3">
					Fecha de captura:
				</td>
				<td  align="left" width="15%">
					<form:input path="fechaCapturaTxt" id="fecCaptura_cedval" size="12" readonly="true"/>
				</td>
				<td  align="left" class="etiqueta2" width="15%">
					&nbsp;
				</td>
				<td align="left" class="etiqueta2" width="2%">
					&nbsp;&nbsp;
				</td>
				<td align="left" class="etiqueta2" width="10%">
				</td>
				<td align="left" width="14%">
				</td>
				<td align="left" width="14%">
				</td>		
			</tr>
			<tr class="par">
				<td align="right" class="etiqueta2" width="15%" colspan="3">
				</td>
				<td  align="left"  width="25%" colspan="2">
					&nbsp;
				</td>
				<td align="left" class="etiqueta2" width="2%">
					&nbsp;&nbsp;
				</td>
				<td align="left" class="etiqueta2" width="10%">
				</td>
				<td align="left" width="14%">
				</td>
				<td align="left" width="14%">
				</td>		
			</tr>
			<tr valign="top" class="par">
				<td align="right" class="etiqueta2" width="10%">
				</td>
				<td align="left"  width="10%" >
				</td>
				<td align="left" width="5%" >&nbsp;</td>
				<td align="left" colspan="6">&nbsp;</td>
			</tr>
			<tr valign="top" class="par">
				<td align="left" width="10%" colspan="7" >&nbsp;</td>
				   	<!-- <td align="center" colspan="5" >
							<input type="button" value="Guardar" id="btnGuardarRegularizaObra" width="10px" height="12px" class="boton" onclick="procesaFormularioRegObraGenerico(validaCamposRegularizaObra())" >
						</td> -->
				<td align="center" colspan="3" >
					<input type="button" value="Datos de la Regularizaci&oacute;n" id="btnDatosRegularizacionSegCorr_cedval" width="9px" height="9px" class="boton" onclick="openDialogoPagosCedula_Valida()" >
				</td>
			</tr>
			<tr valign="top" class="par">
				<td align="left" colspan="9">&nbsp;</td>
			</tr>
		</tbody>
	</table>