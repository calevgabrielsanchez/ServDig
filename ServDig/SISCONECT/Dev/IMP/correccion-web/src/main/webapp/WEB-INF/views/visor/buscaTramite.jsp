<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="sp">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/visor/visorTramites.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/uploadFile/upclick.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/uploadFile/fileUp.js"></script>



<div id="cuerpo">

	<div class="menu_principal" style="height: 2em !important;"> 
		<!--inicia menu principal-->
<div class="separadorseccion">
			<span>
				 Visor de tr&aacute;mites
			</span>
		</div>
	</div>

	<div>
		<form action="" method="post" id="visorForm">
			<fieldset>
					<table style="width: 900px" align="center">
						<tr valign="middle">
							<td align="center" width="900px">	
								<table class="tablaverde2" style="width: 900px" id="">
									<tbody>
										<tr valign="top" class="impar">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>
										<tr valign="top" class="par">
											<td align="center" valign="top">
												<label>N&uacute;mero de Folio: </label><input type="text" id="idFolio"/>
											</td>
										</tr>
										<tr width="100%" class="par">
											<td width=100%>
												<table align="center" style="border-collapse: separate; border-spacing:  5px 5px;">													<tr valign="top" class="par">
														<td align="left" valign="top">
															<label for="cveTramite1">Solicitud de correcci&oacute;n patronal</label>
														</td>
															<td>
															<input type="radio" value="1" name="cveTramite" id="cveTramite1" title="Solicitud de correcci&oacute;n patronal" />
														</td>
													</tr>
													<tr valign="top" class="par">
														<td align="left" valign="top">
															<label for="cveTramite2">Solicitud de prorroga</label>
														</td>
															<td>
															<input type="radio" value="2" name="cveTramite" id="cveTramite2" title="Solicitud de prorroga"/>
														</td>
													</tr>
													<tr valign="top" class="par">
														<td align="left" valign="top">
															<label for="cveTramite3">Presentaci&oacute;n de la correcci&oacute;n</label>
														</td>
															<td>
															<input type="radio" value="3" name="cveTramite" id="cveTramite3" title="Presentaci&oacute;n de la correcci&oacute;n"/>
														</td>
													</tr>
													<tr valign="top" class="par">
														<td align="right" valign="top">
															<input type="button" value="Visualiza Adjuntos" onclick="buscaTramite();" id="visualizadorAdjuntos" class="btn btn-primary btn-sm"/>
														</td>
														<td>
															<input type="button" value="Visualiza Acuse" onclick="buscaTramiteAcuse();" id="visualizadorEnviados" class="btn btn-primary btn-sm"/>
														</td>
														
														<td>
															<input type="button" value="Anexar Archivos" id="anexaDocumentos" class="btn btn-primary btn-sm"/>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</table>
				</fieldset>
		</form>
	</div>
	
	<div id="visor">
		<form action="/firmaElectronicaWeb/widget/chfecyn/imss/buscaArchivos" class = "formNotBlock" method="post" target="firmaIframe" id="forma">
	
	                <input type="hidden" name="params" id="idTramite"/>
		</form>
		
		<form action="/firmaElectronicaWeb/widget/chfecyn/imss/buscaSeguimiento" class = "formNotBlock" method="post" target="firmaIframe" id="formaAcuse">
	
	                <input type="hidden" name="params" id="idTramite"/>
		</form>
		
		
		<iframe id="firmaIframe" name="firmaIframe" height="500" width="850" style="display: none;"></iframe> 
	</div>

</div>