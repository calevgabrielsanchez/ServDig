<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
	
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<script src="/correccion-web/resources/js/delta/limpiaFormularios.js" type="text/javascript"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>

<script type="text/javascript"src="<%=request.getContextPath()%>/resources/js/jquery/jquery.alerts.js"></script>
<link href="/correccion-web/resources/css/jquery.alerts.css" type="text/css" rel="stylesheet">


<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/estudioCorreccion/resumen/resumenSeguimiento.js"></script>
<link href="/correccion-web/resources/estilos/estilo.css" type="text/css" rel="stylesheet">
<link href="/correccion-web/resources/css/correcion.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" href="/correccion-web/resources/css/ui-lightness/jquery-ui-1.8.14.custom.css" type="text/css">
<link href="/correccion-web/resources/css/ui-lightness/jquery-ui-1.8.14.custom.css" type="text/css" rel="stylesheet">

<table class="tablaverde2" width="100%">
	<tr>
		<td colspan="2" width="100%">
			<form:form id="formResumenSeguimiento" name="formResumenSeguimiento" method="get" modelAttribute="resumenSeguimientoVO" action="/seguimiento/estudioCorreccion/seguimientoCorreccionResumen.do">
				<input type="hidden" id="idPresentaCoor">
					<fieldset>
						<table style="width: 900px" align="center" class="tablaverde2">
						<thead>
							<tr>
								<td id="titleDatosGenerales" align="left" colspan="6">1. Datos Generales</td>
							</tr>
						</thead>
						
						<tbody id="bodyDatosGenerales">
							<tr class="impar">
								<td align="left" colspan="6">&nbsp;</td>
							</tr>
							<tr class="par" valign="top">
								<td>
									<label id="lbdomicilioFiscal" for="domicilioFiscal" class="etiqueta2">
										&nbsp;&nbsp;1.1 Domicilio Fiscal:
									</label>
								</td>
								<td>
										
									<label id="domicilioFiscal"  class="etiqueta2">	
									</label>					
								</td>
							</tr>
							<tr class="par" valign="top">
								<td>
									<label id="lbdomicilioCentroTrabajo" for="domicilioCentroTrabajo" class="etiqueta2">
										&nbsp;&nbsp;1.2 Domicilio Centro Trabajo:
									</label>
								</td>
								<td>
									<label id="domicilioCentroTrabajo"  class="etiqueta2"></label>					
								</td>
							</tr>
							<tr class="par" valign="top">
								<td>
									<label id="lbdomicilioObra" for="domicilioObra" class="etiqueta2">
										&nbsp;&nbsp;1.3 Domicilio de la Obra:
									</label>
								</td>
								<td>
									<label id="domicilioObra"  class="etiqueta2"></label>							
								</td>
							</tr>
							<tr class="par" valign="top">
								<td colspan="6">									
									<table id="dtPatronesAsociados" style="width: 400px" >
									</table>									
								</td>
							</tr>
						</tbody>			
					</table>
					<table style="width: 900px" align="center" class="tablaverde2">
						<thead>
							<tr>
								<td id="titleAntecedentes" align="left" colspan="4">2. Antecedentes</td>
							</tr>
						</thead>
						<tbody id="bodyAntecedentes">
							<tr class="impar">
								<td align="left" colspan="4">&nbsp;</td>
							</tr>
							<tr class="par" valign="top">
								<td width="40%">
									<label id="lbOrigen" class="etiqueta2">
										2.1. Origen
									</label>
								</td>
								<td>
									<label id="lbOrigenVal" class="etiqueta2"></label>
								</td>
							</tr>
							<tr class="par" valign="top">
								<td width="40%">
									<label id="lbFolioPrograma" class="etiqueta2">
										&nbsp;&nbsp;2.1.1 Folio del Programa
									</label>
								</td>
								<td>
									<label id="lbFolioProgramaVal" class="etiqueta2"></label>
								</td>
							</tr>
							<tr class="par" valign="top">
								<td width="40%">
									<label id="lbFechaEmiOf" class="etiqueta2">
										&nbsp;&nbsp;2.1.2 Fecha Emisi&oacute;n del Oficio
									</label>
								</td>
								<td>
									<label id="lbFechaEmiOfVal" class="etiqueta2"></label>
								</td>
							</tr>
							<tr class="par" valign="top">
								<td width="40%">
									<label id="lbFechaNotOf" class="etiqueta2">
										&nbsp;&nbsp;2.1.3 Fecha Notificaci&oacute;n del Oficio
									</label>
								</td>
								<td>
									<label id="lbFechaNotOfVal" class="etiqueta2"></label>
								</td>
							</tr>
						</tbody>
					</table>
					<table style="width: 900px" align="center" class="tablaverde2">
						<thead>
							<tr>
								<td id="titleSolCorr" align="left" colspan="6">3. Solicitud de Correcci&oacute;n</td>
							</tr>
						</thead>
						<tbody id="bodySolCorr">
							<tr class="impar">
								<td align="left" colspan="6">&nbsp;</td>
							</tr>
							<tr class="par" valign="top">
								<td width="40%">
									<label id="lbFechaPreSolCorr" class="etiqueta2">
										&nbsp;&nbsp;3.1 Fecha de Presentaci&oacute;n de la Solicitud de Correcci&oacute;n
									</label>
								</td>
								<td>
									<label id="lbFechaPreSolCorrVal" class="etiqueta2"></label>
								</td>
							</tr>
							<tr class="par" valign="top">
								<td width="40%">
									<label id="lbFechaAutSolCorr" class="etiqueta2">
										&nbsp;&nbsp;3.2 Fecha de Autorizaci&oacute;n de la Solicitud de Correcci&oacute;n
									</label>
								</td>
								<td>
									<label id="lbFechaAutSolCorrVal" class="etiqueta2"></label>
								</td>
							</tr>
							<tr class="par" valign="top">
								<td width="40%">
									<label id="lbFechaSolProrr" class="etiqueta2">
										&nbsp;&nbsp;3.3 Fecha de Solicitud de Pr&oacute;rroga
									</label>
								</td>
								<td>
									<label id="lbFechaSolProrrVal" class="etiqueta2"></label>
								</td>
							</tr>
							<tr class="par" valign="top">
								<td width="40%">
									<label id="lbFechaAutoProrr" class="etiqueta2">
										&nbsp;&nbsp;3.4 Fecha de Autorizaci&oacute;n de la Pr&oacute;rroga
									</label>
								</td>
								<td>
									<label id="lbFechaAutoProrrVal" class="etiqueta2"></label>
								</td>
							</tr>
							<tr class="par" valign="top">
								<td width="40%">
									<label id="lbFechaRechProrr" class="etiqueta2">
										&nbsp;&nbsp;3.5 Fecha de Rechazo de la Pr&oacute;rroga
									</label>
								</td>
								<td>
									<label id="lbFechaRechProrrVal" class="etiqueta2"></label>
								</td>
							</tr>
							<tr class="par" valign="top">
								<td width="40%">
									<label id="lbFechaPresentaProrr" class="etiqueta2">
										&nbsp;&nbsp;3.6 Fecha de Presentaci&oacute;n
									</label>
								</td>
								<td>
									<label id="lbFechaPresentaVal" class="etiqueta2"></label>
								</td>
							</tr>
						<!-- 	<tr class="par" valign="top">
								<td width="40%">
									<label id="lbFechaAutoPresent" class="etiqueta2">
										&nbsp;&nbsp;3.7 Fecha de autorizaci&oacute;n de la presentaci&oacute;n
									</label>
								</td>
								<td>
									<label id="lbFechaAutoPresentVal" class="etiqueta2"></label>
								</td>
							</tr> -->
						</tbody>			
					</table>		
					<table style="width: 900px" align="center" class="tablaverde2">
						<thead>
							<tr>
								<td id="titlePressCorr" align="left" colspan="8">4. Presentaci&oacute;n de la Correcci&oacute;n</td>
							</tr>
						</thead>
						<tbody id="bodyPressCorr" >
						<tr class="par" valign="top">
								<td colspan="1">
									<label id="lbFechaAutSolCorr" class="etiqueta2">
										&nbsp;&nbsp;4.1 Fecha de Presentaci&oacute;n
									</label>
								</td>
								<td colspan="7">
									<label id="lbValFechaAutSolCorr" class="etiqueta2">
										&nbsp;&nbsp;
									</label>
								</td>
							</tr>
							<tr class="par" valign="top">
							<td colspan="8">
								<table style="width: 900px" align="center">
									<tr>
										<td >
											<a  id="lbDetalleCedulaA">C&eacute;dula A</a>
										</td>
										<td>
											<a  id="lbDetalleCedulaG">C&eacute;dula G</a>
										</td>
										<td>
											<a  id="lbDetalleCedulaH">C&eacute;dula H</a>
										</td>
										<td>
											<a  id="lbDetalleCedulaI">C&eacute;dula I</a>
										</td>
										<td>
											<a  id="lbDetalleCedulaO">C&eacute;dula O</a>
										</td>
										<td>
											<a  id="lbDetalleCedulaQ">C&eacute;dula Q</a>
										</td>
										<td>
											<a  id="lbDetalleCedulaCOP">C&eacute;dula C.O.P</a>
										</td>
										<td>
											<a  id="lbDetalleCedulaR">C&eacute;dula R</a>
										</td>
									</tr>
								</table>
							</td>
								
							</tr>
							<tr class="par" valign="top">
								<td colspan="8">
									<label id="lbTotalCOPPaga" class="etiqueta2">
										&nbsp;&nbsp;&nbsp;&nbsp;4.1.1 Total de COP Pagadas en la Correcci&oacute;n(es la suma de todo lo que ha capturado el patr&oacute;n)
									</label>
								</td>
							</tr>
							<tr class="par" valign="top">
								<td  width="20%">
								<label id="lbCOPConcepto" class="etiqueta2" align="right">
										Concepto		
										</label>						
								</td>
								<td width="16%" align="right">
									<label id="lbCOPSuertePrincipal" class="etiqueta2">
										Suerte Principal							
									</label>
								</td>
								<td width="16%" align="right">
									<label id="lbCOPActualizaciones" class="etiqueta2">
										Actualizaciones							
									</label>
								</td>
								<td width="16%" align="right">
									<label id="lbCOPRecargos" class="etiqueta2">
										Recargos						
									</label>
								</td>
								<td width="16%" align="right">
									<label id="lbCOPMultas" class="etiqueta2">
										Multas						
									</label>
								</td>
								<td width="16%" align="right">
									<label id="lbCOPTotal" class="etiqueta2">
										Total						
									</label>
								</td>
							</tr>
							<tr class="par" valign="top">
								<td  width="20%">
								<label id="lbCOPConcepto" class="etiqueta2">
										COP		
										</label>						
								</td>
								<td  align="right">
									<label id="lbCopSPVal" >
																	
									</label>
								</td>
								<td align="right">
									<label id="lbCopActVal" >				
									</label>
								</td>
								<td align="right">
									<label id="lbCopRecVal" >		
									</label>
								</td>
								<td align="right">
									<label id="lbCopMulVal" >		
									</label>
								</td>
								<td align="right">
									<label id="lbCopTotaVal">	
									</label>
								</td>
							</tr>
							<tr class="par" valign="top">
								<td  width="20%" >
								<label id="lbRcvConcepto" class="etiqueta2">
										RCV		
										</label>						
								</td>
								<td align="right">
									<label id="lbRcvSPVal" >																	
									</label>
								</td>
								<td align="right">
									<label id="lbRcvActVal" >				
									</label>
								</td>
								<td align="right">
									<label id="lbRcvRecVal" >		
									</label>
								</td>
								<td align="right">
									<label id="lbRcvMulVal" >		
									</label>
								</td>
								<td align="right">
									<label id="lbRcvTotaVal">	
									</label>
								</td>
							</tr>
							<tr class="par" valign="top">
								<td  width="20%">
								<label id="lbTotalConcepto" class="etiqueta2">
										Total		
										</label>						
								</td>
								<td align="right">
									<label id="lbTotalSPVal" >																	
									</label>
								</td>
								<td align="right">
									<label id="lbTotalActVal" >				
									</label>
								</td>
								<td align="right">
									<label id="lbTotalRecVal" >		
									</label>
								</td>
								<td align="right">
									<label id="lbTotalMulVal" >		
									</label>
								</td>
								<td align="right">
									<label id="lbTotalTotaVal">	
									</label>
								</td>
							</tr>
							<tr class="par" valign="top">
								<td  width="40%">
									<label id="lbFechaAutSolCorr" class="etiqueta2">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.1.2 N&uacute;mero de Trabajadores Regularizados
									</label>
								</td>
								<td colspan="5">
									<label id="lbnumTraRegu" class="etiqueta2">										
									</label>
								</td>
							</tr>
							<tr class="par" valign="top">
								<td  width="40%">
									<label id="lbRevision" class="etiqueta2">
										&nbsp;&nbsp;4.2 Revisi&oacute;n
									</label>
								</td>
								<td colspan="5">
									<label class="etiqueta2">										
									</label>
								</td>
							</tr>
							<tr class="par" valign="top">
								<td>
									<label id="lbFechaCedulaRev" class="etiqueta2">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.2.1 Fecha C&eacute;dula Revisi&oacute;n
									</label>
								</td>
								<td colspan="5">
									<label id="lbValFechaCedulaRev" class="etiqueta2">										
									</label>
								</td>
							</tr>
							<tr class="par" valign="top">
								<td>
									<label id="lbFechaAutCedVal" class="etiqueta2">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.2.2 Fecha de Autorizaci&oacute;n
									</label>
								</td>
								<td colspan="5">
									<label id="lbValFecaAutCedVal" class="etiqueta2">										
									</label>
								</td>
							</tr>
							<tr class="par" valign="top">
								<td>
									<label id="lbPorcRazo" class="etiqueta2">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.2.3 Porcentaje de Razonabilidad
									</label>
								</td>
								<td colspan="5" id="tablaPorce">
									<label id="lbValPorcRazo" class="etiqueta2">										
									</label>
								</td>
							</tr>
							<tr class="par" valign="top">
								<td>
									<label id="lbMetodoCalc" class="etiqueta2">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.2.4 M&eacute;todo de C&aacute;lculo
									</label>
								</td>
								<td colspan="5">
									<label id="lbValMetodoCalc" class="etiqueta2">		
									</label>
								</td>
							</tr>
							
							<tr class="par" valign="top">
								<td>
									<label id="lbOfReqDoc" class="etiqueta2">
										&nbsp;&nbsp;4.3 Oficio de Requerimiento de Documentaci&oacute;n
									</label>
								</td>
								<td colspan="5">
									<label id="lbValOfReqDoc" class="etiqueta2">	
																		
									</label>
								</td>
							</tr>
							
							<tr class="par" valign="top">
								<td>
									<label id="lbFechaEmisioOfReq" class="etiqueta2">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.3.1 Fecha de Emisi&oacute;n
									</label>
								</td>
								<td colspan="5">
									<label id="lbValFechaEmisioOfReq" class="etiqueta2">	
																	
									</label>
								</td>
							</tr>
							<tr class="par" valign="top">
								<td>
									<label id="lbFechaNotOfReq" class="etiqueta2">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.3.2 Fecha de Notificaci&oacute;n
									</label>
								</td>
								<td colspan="5">
									<label id="lbValFechaNotOfReq" class="etiqueta2">	
																	
									</label>
								</td>
							</tr>
							
							<tr class="par" valign="top">
								<td>
									<label id="lbFechaAtencOfReq" class="etiqueta2">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.3.3 Fecha de Atenci&oacute;n
									</label>
								</td>
								<td colspan="5">
									<label id="lbValFechaAtencOfReq" class="etiqueta2">	
																	
									</label>
								</td>
							</tr>
							
							<tr class="par" valign="top">
								<td>
									<label id="lbCedValida" class="etiqueta2">
										&nbsp;&nbsp;4.4 C&eacute;dula de Validaci&oacute;n
									</label>
								</td>
								<td colspan="5">
									<label  class="etiqueta2">	
																	
									</label>
								</td>
							</tr>
							
							<tr class="par" valign="top">
								<td>
									<label id="lbFechaElaboraCedVal" class="etiqueta2">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.4.1 Fecha de Elaboraci&oacute;n
									</label>
								</td>
								<td colspan="5">
									<label id="lbValFechaElaboraCedVal" class="etiqueta2">	
																	
									</label>
								</td>
							</tr>
							
							<tr class="par" valign="top">
								<td>
									<label id="lbFechaAutCedVal" class="etiqueta2">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.4.2 Fecha de Autorizaci&oacute;n
									</label>
								</td>
								<td colspan="5">
									<label id="lbValFechaAutCedVal" class="etiqueta2">	
																	
									</label>
								</td>
							</tr>
							
							<tr class="par" valign="top">
								<td>
									<label id="lbOfiRes" class="etiqueta2">
										&nbsp;&nbsp;4.5 Oficio de Resultados
									</label>
								</td>
								<td colspan="5">
									<label class="etiqueta2">	
																	
									</label>
								</td>
							</tr>
							
								<tr class="par" valign="top">
								<td>
									<label id="lbFechaEmisionOfRes" class="etiqueta2">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.5.1 Fecha Emisi&oacute;n
									</label>
								</td>
								<td colspan="5">
									<label id="lbValFechaEmisionOfRes" class="etiqueta2">	
																	
									</label>
								</td>
							</tr>
							
								<tr class="par" valign="top">
								<td>
									<label id="lbFechaNotiOfRes" class="etiqueta2">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.5.2 Fecha Notificaci&oacute;n
									</label>
								</td>
								<td colspan="5">
									<label id="lbValFechaNotiOfRes" class="etiqueta2">	
																	
									</label>
								</td>
							</tr>
								<tr class="par" valign="top">
								<td>
									<label id="lbFechaAtencion" class="etiqueta2">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.5.3 Fecha Atenci&oacute;n
									</label>
								</td>
								<td colspan="5">
									<label id="lbValFechaAtencion" class="etiqueta2">	
																	
									</label>
								</td>
							</tr>
							<tr class="par" valign="top">
								<td>
									<label id="lbNumTrabajOfRes" class="etiqueta2">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.5.4 N&uacute;mero de Trabajadores Regularizados
									</label>
								</td>
								<td colspan="5">
									<label id="lbValNumTrabajOfRes" class="etiqueta2">	
																	
									</label>
								</td>
							</tr>
							
							<tr class="par" valign="top">
								<td colspan="6">
								<label id="lbTotalCOPOfRes" class="etiqueta2">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.5.5 Total COP Determiadas y Pagadas con Motivo de Revisi&oacute;n (calculada de oficio de diferencias y de los pagos capturados)
									</label>
								</td>
							</tr>
							
							
							<tr >
								<td colspan="6">
									<table width="100%" class="tablaverde2">
																	<tr class="par" valign="top">
											<td  width="20%">
											<label id="lbCOPConceptoCedVal" class="etiqueta2">
													Concepto		
													</label>						
											</td>
											<td width="13%" align="right">
												<label id="lbCOPBaseCedVal" class="etiqueta2">
													Base						
												</label>
											</td>
											
											<td width="13%" align="right">
												<label id="lbCOPSuertePrincipalCedVal" class="etiqueta2">
													Suerte Principal							
												</label>
											</td>
											
											<td width="13%" align="right">
												<label id="lbCOPActualizacionesCedVal" class="etiqueta2">
													Actualizaciones							
												</label>
											</td>
											<td width="13%" align="right">
												<label id="lbCOPRecargosCedVal" class="etiqueta2">
													Recargos						
												</label>
											</td>
											
											<td width="13%" align="right">
												<label id="lbCOPMultasCedVal" class="etiqueta2">
													Multas						
												</label>
											</td>
											<td width="13%" align="right">
												<label id="lbCOPTotalCedVal" class="etiqueta2">
													Total						
												</label>
											</td>
										</tr>
										<tr class="par" valign="top">
											<td  width="20%">
											<label id="lbCOPConceptoCedVal" class="etiqueta2">
													COP		
													</label>						
											</td>
											<td align="right">
												<label id="lbCopBaseValCedVal" >
																				
												</label>
											</td>
											<td align="right">
												<label id="lbCopSPValCedVal" >
																				
												</label>
											</td>
											<td align="right">
												<label id="lbCopActValCedVal" >				
												</label>
											</td>
											<td align="right">
												<label id="lbCopRecValCedVal" >		
												</label>
											</td>
											<td align="right">
												<label id="lbCopMultaValCedVal" >		
												</label>
											</td>
											
											<td align="right">
												<label id="lbCopTotaValCedVal">	
												</label>
											</td>
										</tr>
										<tr class="par" valign="top">
											<td  width="20%" >
											<label id="lbRcvConceptoCedVal" class="etiqueta2">
													RCV		
													</label>						
											</td>
											<td align="right">
												<label id="lbRcvBaseValCedVal" >																	
												</label>
											</td>
											<td align="right">
												<label id="lbRcvSPValCedVal" >																	
												</label>
											</td>
											<td align="right">
												<label id="lbRcvActValCedVal" >				
												</label>
											</td>
											<td align="right">
												<label id="lbRcvRecValCedVal" >		
												</label>
											</td>
											<td align="right">
												<label id="lbRcvMultaValCedVal" >		
												</label>
											</td>
											<td align="right">
												<label id="lbRcvTotaValCedVal">	
												</label>
											</td>
										</tr>
										<tr class="par" valign="top">
											<td  width="20%">
											<label id="lbTotalConceptoCedVal" class="etiqueta2">
													Total		
													</label>						
											</td>
											<td align="right">
												<label id="lbTotalBaseValCedVal" >																	
												</label>
											</td>
											<td align="right">
												<label id="lbTotalSPValCedVal" >																	
												</label>
											</td>
											<td align="right">
												<label id="lbTotalActValCedVal" >				
												</label>
											</td>
											<td align="right">
												<label id="lbTotalRecValCedVal" >		
												</label>
											</td>
											<td align="right">
												<label id="lbTotalMultaValCedVal" >		
												</label>
											</td>
											<td align="right">
												<label id="lbTotalTotaValCedVal">	
												</label>
											</td>
										</tr>
									</table>	
								</td>								
							</tr>
							
							
							<tr class="par">
								<td colspan="7">&nbsp;&nbsp;</td>
							</tr>
							
							<tr class="par" valign="top">
								<td>
									<label id="lbOfConc" class="etiqueta2">
										&nbsp;&nbsp;4.6 Oficio de Conclusi&oacute;n
									</label>
								</td>
								<td colspan="5">
									<label id="lbValOfConc" class="etiqueta2">	
																	
									</label>
								</td>
							</tr>
							
								<tr class="par" valign="top">
								<td>
									<label id="lbFechaEmiOfConc" class="etiqueta2">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.6.1 Fecha de Emisi&oacute;n
									</label>
								</td>
								<td colspan="5">
									<label id="lbValFechaEmiOfConc" class="etiqueta2">	
																	
									</label>
								</td>
							</tr>
							
								<tr class="par" valign="top">
								<td>
									<label id="lbFechaNotiOfiConclu" class="etiqueta2">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4.6.2 Fecha de Notificaci&oacute;n
									</label>
								</td>
								<td colspan="5">
									<label id="lbValFechaNotiOfiConclu" class="etiqueta2">	
																	
									</label>
								</td>
							</tr>
						</tbody>
					</table>	
						
						
					<table style="width: 900px" align="center" class="tablaverde2">
						<thead>
							<tr>
								<td id="titleDerivaFizca" align="left"  colspan="2">5. Derivaci&oacute;n a Fiscalizaci&oacute;n</td>
							</tr>
						</thead>
						<tbody id="bodyDerivaFiza">
							<tr class="impar">
								<td align="left" colspan="2">&nbsp;</td>
							</tr>
							<tr class="par" valign="top">
								<td  width="40%">
									<label id="lbFecDerFisca" class="etiqueta2">
										&nbsp;&nbsp;&nbsp;&nbsp;5.1. Fecha de Derivaci&oacute;n
									</label>
								</td>
								<td width="60%">
									<label id="lbValFecDerFisca" class="etiqueta2"></label>
								</td>
							</tr>
								<tr class="par" valign="top">
								<td  width="40%">
									<label id="lbReferenciaFizca" class="etiqueta2">
										&nbsp;&nbsp;&nbsp;&nbsp;5.2 Referencia
									</label>
								</td>
								<td width="60%">
									<label id="lbValReferenciaFizca" class="etiqueta2"></label>
								</td>
							</tr>
						</tbody>
					</table>
						
							
					<table style="width: 900px" align="center" class="tablaverde2">
						<thead>
							<tr>
								<td id="titleDerivaSubde" align="left"  colspan="2">6. Derivaci&oacute;n a otra Subdelegaci&oacute;n</td>
							</tr>
						</thead>
						<tbody id="bodyDerivaSubde">
							<tr class="impar">
								<td align="left" colspan="2">&nbsp;</td>
							</tr>
							<tr class="par" valign="top">
								<td  width="40%">
									<label id="lbFecDerSubde" class="etiqueta2">
										&nbsp;&nbsp;&nbsp;&nbsp;6.1. Fecha de Derivaci&oacute;n
									</label>
								</td>
								<td width="60%">
									<label id="lbValFecDerSubde" class="etiqueta2"></label>
								</td>
							</tr>
							<tr class="par" valign="top">
								<td  width="40%">
									<label id="lbFolioDerSubde" class="etiqueta2">
										&nbsp;&nbsp;&nbsp;&nbsp;6.2. Folio del Oficio
									</label>
								</td>
								<td width="60%">
									<label id="lbValFolioDerSubde" class="etiqueta2"></label>
								</td>
							</tr>
							<tr class="par" valign="top">
								<td  width="40%">
									<label id="lbSubdeDestDerSub" class="etiqueta2">
										&nbsp;&nbsp;&nbsp;&nbsp;6.3. Subdelegaci&oacute;n Destino
									</label>
								</td>
								<td width="60%">
									<label id="lbValSubdeDestDerSub" class="etiqueta2"></label>
								</td>
							</tr>
						</tbody>
					</table>
						
						
					<table style="width: 900px" align="center" class="tablaverde2">
						<thead>
							<tr>
								<td id="titleDerivaDicta" align="left"  colspan="2">7. Derivaci&oacute;n a Dictamen</td>
							</tr>
						</thead>
						<tbody id="bodyDerivaDicta">
							<tr class="impar">
								<td align="left" colspan="2">&nbsp;</td>
							</tr>
							<tr class="par" valign="top">
								<td  width="40%">
									<label id="lbFecAutoDerivDicta" class="etiqueta2">
										&nbsp;&nbsp;&nbsp;&nbsp;7.1. Fecha de Autorizaci&oacute;n
									</label>
								</td>
								<td width="60%">
									<label id="lbValFecAutoDerivDicta" class="etiqueta2"></label>
								</td>
							</tr>
							<tr class="par" valign="top">
								<td  width="40%">
									<label id="lbFolioAvisoDerDicta" class="etiqueta2">
										&nbsp;&nbsp;&nbsp;&nbsp;7.2. Folio del Aviso
									</label>
								</td>
								<td width="60%">
									<label id="lbValFolioAvisoDerDicta" class="etiqueta2"></label>
								</td>
							</tr>
						</tbody>
					</table>
					
					
					<table style="width: 900px" align="center" class="tablaverde2">
						<thead>
							<tr>
								<td id="titleCancelacion" align="left"  colspan="2">8. Cancelaci&oacute;n</td>
							</tr>
						</thead>
						<tbody id="bodyCancelacion">
							<tr class="impar">
								<td align="left" colspan="2">&nbsp;</td>
							</tr>
							<tr class="par" valign="top">
								<td  width="40%">
									<label id="lbFecAutoCancela" class="etiqueta2">
										&nbsp;&nbsp;&nbsp;&nbsp;8.1. Fecha de Autorizaci&oacute;n de Cancelaci&oacute;n
									</label>
								</td>
								<td width="60%">
									<label id="lbValFecAutoCancela" class="etiqueta2"></label>
								</td>
							</tr>
							<tr class="par" valign="top">
								<td  width="40%">
									<label id="lbFolioVolaCancel" class="etiqueta2">
										&nbsp;&nbsp;&nbsp;&nbsp;8.2. Folio de Volante de la Cancelaci&oacute;n
									</label>
								</td>
								<td width="60%">
									<label id="lbValFolioVolaCancel" class="etiqueta2"></label>
								</td>
							</tr>
							<tr class="par" valign="top">
								<td  width="40%">
									<label id="lbMotivoCancel" class="etiqueta2">
										&nbsp;&nbsp;&nbsp;&nbsp;8.3. Motivo de la Cancelaci&oacute;n
									</label>
								</td>
								<td width="60%">
									<label id="lbValMotivoCancel" class="etiqueta2"></label>
								</td>
							</tr>
						</tbody>
					</table>
				
				
					
					<table style="width: 900px" align="center" class="tablaverde2">
						<thead>
							<tr>
								<td id="titleObserva" align="left"  colspan="2">9. Observaciones</td>
							</tr>
						</thead>
						<tbody id="bodyObserva">
								<tr class="par" valign="top">
								<td  width="40%">
									<label id="lbObserva" class="etiqueta2">
										&nbsp;&nbsp;&nbsp;&nbsp;9.1 Observaciones
									</label>
								</td>
								<td width="60%">
									<label id="lbValObserva" class="etiqueta2"></label>
								</td>
							</tr>						
						</tbody>
					</table>
			
				</fieldset>	
			</form:form>
		</td>
	</tr>
</table>

<div id="dialog-form" title="Detalle Cedula" class="tab_content"  style="border:solid 1px;border-color:black; width:900px; height:400px; overflow: auto;">
</div>

