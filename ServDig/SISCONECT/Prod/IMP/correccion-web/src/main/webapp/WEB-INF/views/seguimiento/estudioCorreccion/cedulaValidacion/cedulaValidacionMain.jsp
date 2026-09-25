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
			<form:form id="formCedValidacionSeguimientoCorr" method="get" modelAttribute="cedulaValidacionVO" action="/seguimiento/estudioCorreccion/seguimiento.do">
<!-- 				<input type="hidden" id="cvePresentaCorrCedVal"> -->
					<form:hidden path="cvePresentaCorr" id="cvePresentaCorrCedVal"/>
					<form:hidden path="cveConsolidaImporte" id="cveConsolidaImporteCedValHdn"/>
					<form:hidden path="numFolio" id="numFolioCedValHdn"/>
						<fieldset>
							<table style="width: 900px" align="center" class="tablaverde2" border="0">
							<thead>
								<tr>
									<td align="center" colspan="6">Cedula de Validación</td>
								</tr>
							</thead>
							<tbody>
								<tr class="impar">
									<td align="left" colspan="6">&nbsp;</td>
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
									<td>
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
										&nbsp;
									</td>
									<td>
										&nbsp;
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
									<div  class="par"  id="divCedValidImporte" style="OVERFLOW: auto; WIDTH: 900px; HEIGHT:450px">
										<table id="dtConsolidacionImportes" style="width: 900px">
											<thead></thead>
											<tbody></tbody>
											<tfoot id="totalesImportesCedVal"> 
													<th  align="center"><label id="footcedValCV1"></label></th> 
													<th  align="right"><label id="footcedValCV2"></label></th>
													<th  align="right"><label id="footcedValCV3"></label></th>
													<th  align="right"><label id="footcedValCV4"></label></th>
													<th align="right"><label></label></th>
													<th  align="right"><label id="footcedValCV5"></label></th>
													<th  align="right"><label id="footcedValCV6"></label></th>
													<th align="right"><label></label></th>
													<th  align="right"><label id="footcedValCV7"></label></th>
													<th  align="right"><label id="footcedValCV8"></label></th>
													<th align="right"><label></label></th>
											</tfoot>
										</table>
									</div>
								</td>
								</tr>
							</tbody>
						</table>	
						
						<!--  <table style="width: 900px" align="center" class="tablaverde2" border="0">	
						 	<thead>
								<tr  valign="top">
									<td colspan="2">
										<label id="lbFolCorr"  > 
											Concepto
										</label>						
									</td>	
									<td>
										<label id="lbFolCorr"   > 
											Importe por Aclarar
										</label>	
									</td>
									<td>
										<label id="lbFolCorr"  > 
											Aclarado
										</label>	
									</td>
									<td>
										<label id="lbFolCorr"  > 
											Diferencia
										</label>	
									</td>
									<td>
										<label id="lbFolCorr"  > 
											Aclarado Oficio Resultados
										</label>	
									</td>
									<td>
										<label id="lbFolCorr"  > 
											Total a pagar
										</label>	
									</td>
									<td>
										<label id="lbFolCorr"  > 
											Total pagado
										</label>	
									</td>
									<td>
										<label id="lbFolCorr"  > 
											Diferencia
										</label>	
									</td>
									
								</tr>
							</thead>					
							<tbody>	
								<tr class="par" >
									<td colspan="2">
										<label id="lbFolCorr" for="inRecFolioCorr" class="etiqueta2" > 
											Total
										</label>						
									</td>	
									<td>
										<input value="0.0" id="valTotImportePorAclararCV" readonly="readonly"  class="inputMoney"/>
									</td>
									<td>
										<input value="0.0" id="valTotImporteAclaradoCV" readonly="readonly"  class="inputMoney"/>
									</td>
									<td>
										<input value="0.0" id="valTotDiferenciaCV" readonly="readonly"  class="inputMoney"/>
									</td>
									<td>
										<input value="0.0" id="valAclaradoOficiResCV" readonly="readonly"  class="inputMoney"/>
									</td>
									<td>
										<input value="0.0" id="valTotalAPagarCV" readonly="readonly"  class="inputMoney"/>
									</td>
									<td>
										<input value="0.0" id="valTotPagadoCV" readonly="readonly"  class="inputMoney"/>
									</td>	
									<td>
										<input value="0.0" id="valTotPagadoDifeCV" readonly="readonly"  class="inputMoney"/>
									</td>									
								</tr>
							
							</tbody>
						</table> -->
						
						<table style="width: 900px" align="center" class="tablaverde2"  border="0" >
							<thead>
								<tr>
									<td align="center" colspan="6">Consolidacion de importes pagados, aclarados y por aclarar (Total RP's y Total Ejercicios)</td>
								</tr>
							</thead>
							<tbody>	
								<tr class="par" valign="top">
									<td>
										<table id="dtConsolidadoValidacion" style="width: 900px">
												<thead></thead>
												<tbody></tbody>
												<tfoot id="totalesConsolidadoPagadosCedVal"> 
													<th id="footcedValT1" align="right"></th> 
													<th id="footcedValT2" align="right"></th>
													<th id="footcedValT3" align="right"></th>
													<th id="footcedValT4" align="right"></th>
													<th id="footcedValT5" align="right"></th>
													<th id="footcedValT6" align="right"></th>
													<th id="footcedValT7" align="right"></th>
													<th id="footcedValT8" align="right"></th>
												</tfoot>
										</table>
									</td>
								</tr>							
							</tbody>
						</table>

						<table style="width: 900px" align="center" class="tablaverde2"  border="0" >
							<tbody>	
							<tr>
								<td colspan="7">
									<jsp:include page="/WEB-INF/views/seguimiento/estudioCorreccion/cedulaValidacion/consolidacionImportesValidacion.jsp" />
								</td>
							</tr>
							</tbody>
						</table>
						
						
				<div id="divGuardarValidacionSeguimiento" align="center" >
					
					<!-- <input type="button" value="Consolidado" id="btnConsolidacion" onclick="inicializaCedulaValidacion()" width="9px" height="9px" class="boton" >&nbsp;&nbsp; -->
					<input type="button" value="Guardar" id="btnGuardarValidacion" width="9px" height="9px" class="boton" onclick="guardaCedulaValidacion()" >&nbsp;&nbsp;
					<input type="button" value="Autorizar" style="display:none" id="btnAutorizarCedVali" width="9px" height="9px" class="boton" onclick="autorizaCedulaValida()" >&nbsp;&nbsp;
					<input type="button"  value="Rechazar" style="display:none" id="btnRechazaCedVali" width="9px" height="9px" class="boton" onclick="rechazaCedulaValida()" >&nbsp;&nbsp;
					
					<input type="button" value="Finalizar" style="display:none"  id="btnFinalizaCedulaValidacion" width="9px" height="9px" class="boton"  onclick="finalizaCedulaValidacion()" >	
				</div>
					
				</fieldset>		
			</form:form>
		</td>
	</tr>
</table>
