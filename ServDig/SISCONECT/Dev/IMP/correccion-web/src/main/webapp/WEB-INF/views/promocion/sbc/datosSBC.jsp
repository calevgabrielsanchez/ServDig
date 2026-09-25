<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
 "http://www.w3.org/TR/html4/strict.dtd">
 

<div id="dgPromocionDatosSBC" style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity = 70) !important;">
	
	<div class="menu_principal" style="height: 2em !important;"> 
		<!--inicia menu principal-->
		<div align="center">
			<div class="centrado">
		    	<ul style="height: 1em !important;">
					<li><a> SEGUIMIENTO SBC</a></li>
				</ul>
			</div>
		<!--fin centrado--> 
		</div>
	</div>
	<div id="wrapperDialogDatosSBC" style="background-color: #f2fff2;">
	<form action=""  method="post" id="segimientoSBCForm">
		<input type="hidden" name="cvePromocion" id="cvePromocion">
		<input type="hidden" name="seguimiento" id="seguimientoSBC">
		<input type="hidden" name="regPatronalSbsHdn" id="regPatronalSbsHdn">
		
		
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
										<label id="labelRegistroPatronal"></label>
									</td>
									<td align="left" class="etiqueta2">
										<label>Nombre &oacute; Raz&oacute;n Social :</label>
									</td>
									<td align="left">
										<label id="labelRazonSocial"></label>
									</td>
								</tr>
								<thead>
									<tr>
										<td colspan="4" class="etiqueta2">Domicilio Geografico</td>
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
													<label>Codigo Postal :</label>
												</td>
												<td align="left" width="15%">
													<label id="labelCP"></label>
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
	<br>
	<!-- Seccion de Tabs (Pestañas) -->
	<table  style="width: 900px" align="left" >
		<tr	>
			<td colspan="4" align="left">
			
				<ul class="tabs">
					<li class="etiqueta2" id="seguimientoSBCTAB_SBCLI"><a href="#seguimientoSBCTAB_SBC" id="seguimientoSBCTAB_SBCLink">Seguimiento</a></li>
					<li class="etiqueta2" id="cancelacionGenericoTab_SBCLI"><a href="#cancelacionGenericoTab_SBC" id="cancelacionGenericoTab_SBCLink">Cancelar Promoci&oacute;n</a></li>
					<li class="etiqueta2" id="autAviDictamenGenericoTab_SBCLI"><a href="#autAviDictamenGenericoTab_SBC" id="autAviDictamenGenericoTab_SBCLink">Aut. Avi. Dict. </a></li>		
					<li class="etiqueta2" id="cierrePorCotizarRGenericoTab_SBCLI"><a href="#cierrePorCotizarRGenericoTab_SBC" id="cierrePorCotizarRGenericoTab_SBCLink">Cierre por Cotizar Razonablemente</a></li>	
					<li class="etiqueta2" id="regularizarObraGenericoTAB_SBCLI"><a href="#regularizarObraGenericoTAB_SBC" id="regularizarObraGenericoTAB_SBCLink">Regulariza</a></li>
					
				</ul>
		
			</td>
		</tr>
		<tr>
			<td>
				<div id="seguimientoSBCTAB_SBC" class="tab_content"><jsp:include page="/WEB-INF/views/promocion/sbc/seguimientoSBCTAB.jsp" /></div>
				<div id="cancelacionGenericoTab_SBC" class="tab_content"><jsp:include page="/WEB-INF/views/seguimiento/promocion/genericos/cancelacionGenericoTAB.jsp" /></div> 
				<div id="autAviDictamenGenericoTab_SBC" class="tab_content"><jsp:include page="/WEB-INF/views/seguimiento/promocion/genericos/autAvisoDictamenGenericoTAB.jsp" /></div>
				<div id="cierrePorCotizarRGenericoTab_SBC" class="tab_content" ><jsp:include page="/WEB-INF/views/seguimiento/promocion/genericos/cierrePorCotizarRGenericoTAB.jsp"  /></div>
				<div id="regularizarObraGenericoTAB_SBC" class="tab_content"><jsp:include page="/WEB-INF/views/seguimiento/promocion/regularizarObraGenericoTAB.jsp" /></div>
			</td>
		</tr>
	</table>	
	

	
</div>

		<%-- <div id="confirmarRP" style="display: none;">
			<jsp:include page="/WEB-INF/views/promocion/sbc/confirmarRegularizaObra.jsp" />
		</div> --%>
