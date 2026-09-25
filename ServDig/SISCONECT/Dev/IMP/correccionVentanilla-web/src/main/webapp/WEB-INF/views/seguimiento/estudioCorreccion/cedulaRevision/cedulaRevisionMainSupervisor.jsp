<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/estudioCorreccion/recepcion/recepcionSeguimiento.js"></script>
<script src="/correccion-web/resources/js/delta/limpiaFormularios.js" type="text/javascript"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/estudioCorreccion/cedulaRevision/cedulaRevision.js"></script>
<link href="/correccion-web/resources/estilos/estilo.css" type="text/css" rel="stylesheet">
<link href="/correccion-web/resources/css/correcion.css" type="text/css" rel="stylesheet">
<link href="/correccion-web/resources/css/ui-lightness/jquery-ui-1.8.14.custom.css" type="text/css" rel="stylesheet" >

<table class="tablaverde2" width="100%">
	<tr>
		<td colspan="2" width="100%">
			<form:form id="formCedRevSupvCorrSeguimiento" name="formCedRevSupvCorrSeguimiento" method="get" modelAttribute="recepcionSeguimientoVO" action="/seguimiento/estudioCorreccion/seguimiento.do">
				<input type="hidden" id="idContexto" value="<%=request.getContextPath()%>">
				<input type="hidden" id="idPresentaCoor">
						<fieldset>
							<table style="width: 900px" align="center" class="tablaverde2" border="0">
							<thead>
								<tr>
									<td align="center" colspan="6">Conceptos del Supervisor</td>
								</tr>
							</thead>
							<tbody>
								<tr class="impar">
									<td align="left" colspan="6">&nbsp;</td>
								</tr>
								<tr class="par" valign="top">
										<td colspan="1">
											<div id="wrapperCheckPresuntivo"><input type="checkbox" id="checkPresuntivo"  readonly="readonly" >&nbsp;&nbsp;<label id="lbFecha"  class="etiqueta2">Presuntivo</label></div>						
										</td>		
										<td align="left" colspan="5">&nbsp;</td>						
								</tr>
								
								
								<tr class="par" valign="top">
									<td colspan="1">
										<label id="lbFecha"  class="etiqueta2"> 
											Fecha de Aplicaci&oacute;n de C&eacute;dula:
										</label>						
									</td>
									<td colspan="5">
										<input  id="fechaAplicaCedula" readonly="readonly" />	
									</td>								
								</tr>
								<tr class="par" valign="top">
									<td>
										<label id="lbEjerCedRevAud"  class="etiqueta2"> 
											Ejercicio:
										</label>						
									</td>
									<td>
										<select id="numEjercicio">
											<option value="-1">--Por Favor Seleccione--</option>
										</select>
									</td>
									<td>
										<label id="lbRegPatrCedRevAud"  class="etiqueta2">
											Registro Patronal:
										</label>
									</td>
									<td colspan="2">
										<select id="regPatronal">
											<option value="-1">--Por Favor Seleccione--</option>
										</select>
									</td>
								</tr>		
								<tr class="impar">
									<td align="right" colspan="6">&nbsp;</td>
								</tr>
								<tr class="par" valign="top">
									<td colspan="2">						
									</td>
									
									<td>
										<label id="lbPatronCedRevAud"  class="etiqueta2">
											Patr&oacute;n:
										</label>
									</td>
									<td>
										<label id="lbIMSSCedRevAud"  class="etiqueta2">
											IMSS:
										</label>
									</td>
									<td>
										<label id="lbAuto"  class="etiqueta2">
											Autorizar:
										</label>
									</td>
								</tr>	
								<tr class="par" valign="top">
									<td colspan="2">
										<label id="lbBaseCotImssCedRevAud"  class="etiqueta2" > 
											Base de Cotizaci&oacute;n del IMSS:
										</label>						
									</td>	
									<td align="right" >
										<label class="etiqueta2" >$</label><label id="baseCotPatron"  class="etiqueta2">0</label>				
									</td>
									<td align="right">
										<label class="etiqueta2" >$</label><label id="baseCotImss"  class="etiqueta2">0</label>					
									</td>
									<td>	
										<input type="checkbox" id="autorizabaseCot" > 				
									</td>	
								</tr>	
								<tr class="par" valign="top">
									<td colspan="2">
										<label id="lbDifeCotImssCedRevAud"  class="etiqueta2" > 
											Diferencias en Base de Cotizaci&oacute;n Paga en la Correcci&oacute;n (1% Guarder&iacute;as)(A):
										</label>						
									</td>	
									<td align="right">
										<label class="etiqueta2" >$</label><label id="difeBaseCotiPatron"  class="etiqueta2">0</label>				
									</td>
									<td align="right">
										<label class="etiqueta2" >$</label><label id="difeBaseCotiIMSS"  class="etiqueta2">0</label>						
									</td>
									<td>	
										<input type="checkbox" id="autorizaDifeBaseCoti" > 				
									</td>		
								</tr>	
								<tr class="par" valign="top">
									<td colspan="2">
										<label id="lbTotalConcep"  class="etiqueta2" > 
											Total:
										</label>						
									</td>	
									<td align="right">
										<label class="etiqueta2" >$</label><label id="totalConceptoPatron"  class="etiqueta2">0</label>					
									</td>
									<td  align="right">
										<label class="etiqueta2" >$</label><label id="totalConceptoImss"  class="etiqueta2">0</label>				
									</td>	
									<td>
										&nbsp;&nbsp;
									</td>
								</tr>							
							</tbody>			
						</table>	
						
						<table style="width: 900px" align="center" class="tablaverde2" border="0">
							<thead>
								
							</thead>
							<tbody>	
							<tr>
								<td align="center" >
									<div>
										<table id="dtRubrosAB" style="width: 900px">
											<thead></thead>
											<tbody></tbody>
										</table>
									</div>
								</td>
								</tr>
							</tbody>
						</table>	
						
						 <table style="width: 900px" align="center" class="tablaverde2" border="0">	
						 	<thead>
								<tr  valign="top">
									<td colspan="2">
										<label id="lbFolCorr"  > 
											Concepto
										</label>						
									</td>	
									<td>
										<label id="lbFolCorr"   > 
											Total Importe Aclarado
										</label>	
									</td>
									<td>
										<label id="lbFolCorr"  > 
											Total Importe por Aclarar
										</label>	
									</td>
									<td>
										<label id="lbFolCorr"  > 
											Total Importes
										</label>	
									</td>
								</tr>
							</thead>					
							<tbody>	
								<tr class="par" valign="top">
									<td colspan="2">
										<label id="lbFolCorr" for="inRecFolioCorr" class="etiqueta2" > 
											Total
										</label>						
									</td>	
									<td align="right">
										<label class="etiqueta2" >$</label><label id="valTotImporteAclarado"  class="etiqueta2">0</label>	
									</td>
									<td align="right"> 
										<label class="etiqueta2" >$</label><label id="valTotImportePorAclarar"  class="etiqueta2">0</label>	
									</td>
									<td align="right">
										<label class="etiqueta2" >$</label><label id="valTotImporte"  class="etiqueta2">0</label>
									</td>
								</tr>
							
							</tbody>
						</table>
						
						
						<table style="width: 900px" align="center" class="tablaverde2" border="0">	
									
							<tbody>	
								<tr class="par" valign="top">
									<td>
										
										<label id="lbTotImportAcla" class="etiqueta2" > 
											Total Importe Por Aclarar (C)
										</label>						
									</td>	
									<td align="right">
										<label class="etiqueta2" >$</label>
										<label id="valTotImportAcla"  class="etiqueta2">0</label>
									</td>									
								</tr>
								<tr class="par" valign="top">
									<td>
										<label id="lbPorcRaz"  class="etiqueta2" > 
											Porcentaje de Razonabilidad: (C) entre (A)
										</label>						
									</td>	
									<td align="right">
										<label id="valPorcRaz"  class="etiqueta2">0</label>
									</td>									
								</tr>
								<tr class="par" valign="top">
									<td>
										<label id="lbDifDet"  class="etiqueta2" > 
											Las diferencias determinadas son:
										</label>						
									</td>	
									<td align="right">
										<label id="valDifDet"  class="etiqueta2">0</label>
									</td>									
								</tr>
								<tr class="par" valign="top">
									<td colspan="2"  >
										<label id="lbObserv"  class="etiqueta2" > 
											Observaciones:
										</label>						
									</td>	
																		
								</tr>
								<tr class="par" valign="top">
									<td colspan="2" >
										<textarea rows="4" cols="75" id="txAreaObservaSuperv"></textarea>
									</td>								
								</tr>
							</tbody>
						</table>
						
						
					
						
						
						<table style="width: 900px" align="center" class="tablaverde2"  border="0" >
							<thead>
								<tr>
									<td align="center" colspan="6">Consolidaci&oacute;n de Importes por Aclarar (Total RP's y Total Ejercicios)</td>
								</tr>
							</thead>
							<tbody>	
								<tr class="par" valign="top">
									<td>
										<table id="dtTotalBdDif" style="width: 900px">
												<thead></thead>
												<tbody></tbody>
										</table>
									</td>
								</tr>							
							</tbody>
						</table>
						
				<div id="btnGuardarValidacionSeguimiento" align="center" style="">
					
					<!-- <input type="button" value="Reporte" id="btnReporteRevision" width="9px" height="9px">&nbsp;&nbsp; -->
					<input type="button" value="Autorizar" id="btnAutorizaCedularRevision" width="9px" height="9px" >	&nbsp;&nbsp;
					<input type="button" value="Rechazar" id="btnRechazaCedularRevision" width="9px" height="9px"  >
				</div>
					
				</fieldset>		
			</form:form>
		</td>
	</tr>
</table>