<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">

<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>


<html lang="sp">

 
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/catalogos/deteccion/deteccion.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
<script type="text/javascript"
				src="<%=request.getContextPath()%>/resources/js/delta/KeyPressed.js"></script>
<script type="text/javascript"
				src="<%=request.getContextPath()%>/resources/js/delta/jquery.formatCurrency-1.4.0.pack.js"></script>


		
		<div id="cuerpo">
			
			<div class="menu_principal" style="height: 2em !important;"> 
		      <!--inicia menu principal-->
		      <div align="center">
		        <div class="centrado">
		          <ul style="height: 1em !important;">
		            <li><a> Detecci&oacute;n</a></li>
		          </ul>
		        </div>
		        <!--fin centrado--> 
		      </div>
		    </div>
		    <br>
			<div class="menu_principal" style="height: 2em !important;"> 
		      <!--inicia menu principal-->
		      <div align="center">
		        <div class="centrado">
		          <ul style="height: 1em !important;">
		            <li><a> B&uacute;squeda de Obras Registradas</a></li>
		          </ul>
		        </div>
		        <!--fin centrado--> 
		      </div>
		    </div>
		    
		    <div id="dgDeteccionReporte" style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
		<div id="wrapperDialogReporte" style="background-color: #f2fff2;">	
			<form action="/catalogo/deteccion/consultar.do"
						method="post" id="deteccionFormReporte" >				
				<fieldset>
					<table style="width: 900px" align="center">
						<tr valign="middle">
							<td align="center" width="900px">
								<div id="labelError"></div>
								<table class="tablaverde2" style="width: 900px">
									<tbody>
										<tr valign="top" class="impar">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>
										<tr valign="top" class="par"> 
											<td align="left" width="180px" colspan="1" class="etiqueta2">
												<span class="required">*</span> Fecha de Detecci&oacute;n de:
											</td>  											   	
											<td align="left" width="300px" colspan="1" class="etiqueta2">												
												<input name="fechaIncial" id="fechaIncial" class="etiqueta2" size="12" readonly="readonly" onchange="validafechaSistema('deteccionFormReporte','fechaIncial','Fecha Inicial');jsValidaPeriodoInicial(this.value);validaFecha('deteccionFormReporte','fechaIncial','fechaFinal','Fecha Final','Fecha Inicial');limpiaCamposBusquedaDet(this);"/>	
												<span class="required">*</span> a: 
												<input name="fechaFinal" id="fechaFinal" class="etiqueta2" size="12" readonly="readonly" onchange="validafechaSistema('deteccionFormReporte','fechaFinal','Fecha Final');validaFecha('deteccionFormReporte','fechaIncial','fechaFinal','Fecha Final','Fecha Inicial');jsValidaPeriodoFinal(this.value);limpiaCamposBusquedaDet(this);"/>
												<div id="labelfechaIncial"></div>	
											</td>
											<td align="left" width="100px" colspan="1" class="etiqueta2"><span class="required">*</span> Estatus:</td>
											<td align="left" width="350px" colspan="1" class="etiqueta2">
												
												<select name="estatus" id="estatus" onchange="javaScript:cambiaCombo();limpiaCamposBusquedaDet(this);" class="etiqueta2">
													<option value="">-- Por favor seleccione --</option>
												</select>
												<div id="labelestatus"></div>
											</td>																					
										</tr>	
																																	
										<tr valign="top" class="par">										  								   												
											<td align="left" width="180px" class="etiqueta2">Folio de Detecci&oacute;n:</td>
										  	<td align="left" width="80px">
												<input type="text" name="nuFoliodeteccion" id="nuFoliodeteccion" class="etiqueta2" size="20" maxlength="18" onkeyup="validaCampo('noCaracteresEspeciales','nuFoliodeteccion','deteccionFormReporte');limpiaCamposBusquedaDet(this);"/>
												<label id="labelNumFolio" >  </label>	
											</td>															
										<tr valign="top" class="par">
										   		<td colspan="4">
										   			<div id="divDatosDet" style="width:200px;display:none">
										   				<table style="width: 100%">
										   					<tr valign="top" class="par">   	
															  	<td align="left" width="25%" class="etiqueta2">Fecha Estimada de Incio de la Obra																									
																</td>
																<td align="left" width="25%">
																	<input type="text" name="fechaEstimIncio" id="fechaEstimIncio" class="etiqueta2" onchange="validafechaSistema('deteccionFormReporte','fechaEstimIncio','Fecha Estimada de Inicio')"/>
																	<label for="fechaEstimIncio" >  </label>																	
																</td>
																<td align="left" width="25%" class="etiqueta2">Fecha Estimada de Termino de la obra:																									
																</td>
																<td align="left" width="25%">
																	<input type="text" name="fechaEstTerm" id="fechaEstTerm" class="etiqueta2" onchange="validaFecha('deteccionFormReporte','fechaEstimIncio','fechaEstTerm','Fecha estimada de termino','Fecha estimada de Inicio')"/>
																	<label for="fechaEstTerm" >  </label>																	
																</td>
															</tr>
															<tr valign="top" class="par">   	
																<td align="left" width="50%" class="etiqueta2" colspan="2"> Tipo de Obra:	    			    
															   	</td>															   	
															   	<td align="left" width="50%" class="etiqueta2" colspan="2"> Fase de la Obra:	    			    
															   	</td>															   	
															</tr>
															<tr valign="top" class="par">
																<td align="left" width="80px" colspan="2" class="etiqueta2">
															   		<combo:creaCombo entidad="mx.gob.imss.ctirss.correccion.catalogos.model.CrcTipoobra"
																			 idHtml="cvePkTipObra"
																			 idHtmlContenedor="deteccionFormReporte"/>
																	<label for="cvePkTipObra" >  </label>		    						    			    
															   	</td>
															   	<td align="left" width="80px" colspan="2" class="etiqueta2">
															   		<combo:creaCombo entidad="mx.gob.imss.ctirss.correccion.catalogos.model.CrcFaseconstruccion"
																			 idHtml="cvePkFaseConst"
																			 idHtmlContenedor="deteccionFormReporte"/>
																	<label for="cvePkTipObra" >  </label>	    						    			    
															   	</td>
															</tr>
														</table>
													</div>
												</td>
										</tr>																											
										<tr align="center">
										  <td align="center" width="100px" colspan="4">
										  	<a href="#" onclick="javascript:buscar();"><span class="boton">Buscar</span></a>
										  </td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>	
										<tr valign="top" class="impar">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>										
									</tbody>
								</table>
							</td>
						</tr>
					</table>					
				</fieldset>
			</form>	
			<form action="<%=request.getContextPath()%>/deteccion/alta.do" id="altaForm" method="get">
				<input type="hidden" id="alta">
			</form>							    
	  	</div>
	</div>
			
			<div id="deteccionReg">
				<jsp:include page="deteccionRegistroObra.jsp" />
			</div>
			
			<div id="deteccionRegLayout">
				<jsp:include page="deteccionLayout.jsp" />
			</div>
			
			
			<div id="deteccionCancelacion">
				<jsp:include page="CancelaDeteccion.jsp" />
			</div>
			
			<div id="deteccionData" style="overflow: auto; width:950px; height:300px;display:none;" align="center">
				<jsp:include page="deteccionData.jsp" />
			</div>
			
			<div id="actionButtons" style="display:none">
				<jsp:include page="deteccionAction.jsp" />
			</div>
			
			<div id="deteccionPromocion">
				<jsp:include page="elementosPantalla/confirmaPromocion.jsp" />
			</div>
						
			<br>	    	    
</div>	    
	
