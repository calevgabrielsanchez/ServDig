<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
 "http://www.w3.org/TR/html4/strict.dtd">
<div id="dgPromocionDatosCostruccion" style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity = 70) !important;">
	
	<div class="menu_principal" style="height: 2em !important;"> 
		<!--inicia menu principal-->
		<div align="center">
			<div class="centrado">
		    	<ul style="height: 1em !important;">
					<li><a> SEGUIMIENTO CONSTRUCCI&Oacute;N</a></li>
				</ul>
			</div>
		<!--fin centrado--> 
		</div>
	</div>
	<div id="wrapperDialogDatosConstruccion" style="background-color: #f2fff2;">
	<form  method="post" id="segimientoConstruccionForm">
		<input type="hidden" name="cvePromocion" id="cvePromocion">
		<fieldset>
			<table  style="width: 900px" align="center">
		 		<tr valign="middle">		 
		 			<td align="center" width="900px">
						<table class="tablaverde2" style="width: 900px" >
							<thead>
								<tr>
									<td colspan="4" class="etiqueta2">Informaci&oacute;n del Oficio de Promoci&oacute;n</td>
								</tr>
							</thead>
							<tbody>	
								<tr class="impar">
									<td align="left" colspan="4">&nbsp;</td>
								</tr>
								<tr class="par">
									<td align="left" class="etiqueta2">
										<label>Criterio de Selecci&oacute;n :</label>
									</td>
									<td align="left">
										<label id="labelCriterioSeleccion"></label>
									</td>
									<td align="left" class="etiqueta2">
										<label>N&uacute;mero del folio de la Promoci&oacute;n :</label>
									</td>
									<td align="left">
										<label id="labelFolio"></label>
									</td>
								</tr>
								<tr class="par">
									<td align="left" class="etiqueta2">
										<label>Fecha del Oficio Promoci&oacute;n :</label>
									</td>
									<td align="left">
										<label id="labelFechaOficio"></label>
									</td>
									<td align="left" class="etiqueta2">
										<label>N&uacute;mero del Oficio de la Promoci&oacute;n :</label>
									</td>
									<td align="left">
										<label id="labelNumeroOficio"></label>
									</td>
								</tr>
								<thead>
									<tr>
										<td colspan="4" class="etiqueta2">Ubicaci&oacute;n de la Obra</td>
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
										<label id="labelCalle"></label>
									</td>
									<td align="left" class="etiqueta2">
										<label>Colonia :</label>
									</td>
									<td align="left">
										<label id="labelColonia"></label>
									</td>
								</tr>
								<tr class="par">
									<td colspan="4">
										<table style="width: 900px">
											<tr>
												<td align="left" class="etiqueta2" width="15%">
													<label>N&uacute;mero Exterior :</label>
												</td>
												<td align="left" width="15%">
													<label id="labelNumExt"></label>
												</td>
												<td align="left" class="etiqueta2" width="15%">
													<label>N&uacute;mero Interior :</label>
												</td>
												<td align="left" width="15%">
													<label id="labelNumInt"></label>
												</td>
												<td align="left" class="etiqueta2" width="15%">
													<label>C&oacute;digo Postal :</label>
												</td>
												<td align="left" width="15%">
													<label id="labelCP"></label>
												</td>
											</tr>
											
										</table>
									</td>									
								</tr>
								<thead>
									<tr>
										<td colspan="4" class="etiqueta2">Datos del Patr&oacute;n</td>
									</tr>
								</thead>
								<tr class="impar">
									<td align="left" colspan="4">&nbsp;</td>
								</tr>
								<tr class="par">
									<td align="left" class="etiqueta2">
										<label>Registro Patronal :</label>
									</td>
									<td align="left">
										<input type="text" id="regPatronalSegConstruccion" name="regPatron" size="11" maxlength="10" onkeyup="mayusculasTextField(this);">
										&nbsp;&nbsp;&nbsp;
										<input type="button" id="btnValidarRegPatronal" value="Validar" onclick="javaScript:jsValidaRegPatronal();" onchange="$('form#segimientoConstruccionForm #labelRPError').html('');">
										<span class="boton_limpiar" id="btnLimpiaRPEX" onclick="javaScript:jsLimpiaRPEX();">X</span>
										<label id="labelRPError"></label>
									</td>
									<td align="left" class="etiqueta2">
										<label>Nombre &oacute; Raz&oacute;n Social :</label>
									</td>
									<td align="left">
										<label id="labelRazonSocial"></label>
										<input type="hidden" id="regPatronalValidar">
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
	<br>
	<!-- Seccion de Tabs (Pestañas) -->
	<div style="width: auto; overflow: auto; ">
		<table  style="width: 1150px; overflow: auto;" align="left" >
			<tr	>
				<td colspan="4" align="left">			
					<ul class="tabs">
						<li class="etiqueta2" id="seguimientoTAB_ConstruccionLI"><a href="#seguimientoTAB_Construccion" id="seguimientoTAB_ConstruccionLink">Seguimiento</a></li>
						<li class="etiqueta2" id="cancelacionGenericoTab_ConstruccionLI"><a href="#cancelacionGenericoTab_Construccion" id="cancelacionGenericoTab_ConstruccionLink">Cancelar Promoci&oacute;n</a></li>
						<li class="etiqueta2" id="derivarSubdelegacionGenericoTab_ConstruccionLI"><a href="#derivarSubdelegacionGenericoTab_Construccion" id="derivarSubdelegacionGenericoTab_ConstruccionLink">Derivar a Otra Subdelegaci&oacute;n</a></li>
						<li class="etiqueta2" id="derivarFiscalizacionGenericoTab_ConstruccionLI"><a href="#derivarFiscalizacionGenericoTab_Construccion" id="derivarFiscalizacionGenericoTab_ConstruccionLink">Derivar a Fiscalizaci&oacute;n</a></li>
						<li class="etiqueta2" id="autAviDictamenGenericoTab_ConstruccionLI"><a href="#autAviDictamenGenericoTab_Construccion" id="autAviDictamenGenericoTab_ConstruccionLink">Autorizaci&oacute;n Dictamen</a></li>		
						<li class="etiqueta2" id="cierrePorCotizarRGenericoTab_ConstruccionLI"><a href="#cierrePorCotizarRGenericoTab_Construccion" id="cierrePorCotizarRGenericoTab_ConstruccionLink">Cotizar Razonablemente</a></li>	
						<li class="etiqueta2" id="regularizarObraGenericoTAB_ConstruccionLI"><a href="#regularizarObraGenericoTAB_Construccion" id="regularizarObraGenericoTAB_ConstruccionLink">Regularizar Obra</a></li>			
					</ul>		
				</td>
			</tr>
			<tr>
				<td>
					<div id="seguimientoTAB_Construccion" class="tab_content"><jsp:include page="/WEB-INF/views/seguimiento/promocion/construccion/seguimientoConstruccionTAB.jsp" /></div>
					<div id="cancelacionGenericoTab_Construccion" class="tab_content"><jsp:include page="/WEB-INF/views/seguimiento/promocion/genericos/cancelacionGenericoTAB.jsp" /></div> 
					<div id="derivarSubdelegacionGenericoTab_Construccion" class="tab_content"><jsp:include page="/WEB-INF/views/seguimiento/promocion/genericos/derivarSubdelegacionGenericoTAB.jsp" /></div>
					<div id="derivarFiscalizacionGenericoTab_Construccion" class="tab_content" ><jsp:include page="/WEB-INF/views/seguimiento/promocion/derivarFiscalizacionGenericoTAB.jsp" /></div>
					<div id="autAviDictamenGenericoTab_Construccion" class="tab_content"><jsp:include page="/WEB-INF/views/seguimiento/promocion/genericos/autAvisoDictamenGenericoTAB.jsp" /></div>
					<div id="cierrePorCotizarRGenericoTab_Construccion" class="tab_content" ><jsp:include page="/WEB-INF/views/seguimiento/promocion/genericos/cierrePorCotizarRGenericoTAB.jsp"  /></div>
					<div id="regularizarObraGenericoTAB_Construccion" class="tab_content"><jsp:include page="/WEB-INF/views/seguimiento/promocion/regularizarObraGenericoTAB.jsp" /></div>
				</td>
			</tr>
		</table>	
	</div>
	
</div>

		<div id="confirmarRP" style="display: none;">
			<jsp:include page="/WEB-INF/views/promocion/sbc/confirmarRegularizaObra.jsp" />
		</div>
		
		<div id="confirmarDiv" style="display: none;">
			<jsp:include page="/WEB-INF/views/seguimiento/promocion/saticb/confirmarSaticBSeguimiento.jsp" />
		</div>
