<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">	
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/promocion/saticb/seguimientoSaticbTAB.js"></script>
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/promocion/saticb/seguimientoPromocionSaticb.js"></script> --%>
	    
<div id="dgSeguimientoPromocionSaticB" title="Generar Nuevo Folio SATIC B"   style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
	<div id="wrapperDialogSeguimientoPromocionSaticB" style="text-align:right; background-color: #f2fff2;">	
		<form:form id="seguimientoSaticBForm" name="seguimientoSaticBForm" modelAttribute="seguimientoSaticbVO" action="">
			<input type="hidden" id="cvePromocion">
			<input type="hidden" id="rolGenericoSaticB">
			<form:hidden  path="cvePatron" id="cvePatronObraSaticb" />
			<table  style="width: 1100px" align="center" >
				<tr valign="middle">
					<td align="center" width="100%">
						<table class="tablaverde2" style="width: 1100px"  >
							<thead>
							<tr >
								<td colspan="7">Datos de Promoci&oacute;n</td>
							</tr>
							</thead>
							<tbody >
								<tr valign="top" class="impar">
									<td align="left" colspan="7">&nbsp;</td>
								</tr>
								<tr class="par">
							  		<td align="left" class="etiqueta2" width="17%" >
							  			<label>Criterio de Selecci&oacute;n : </label>
							  		</td>
							  		<td align="left" width="17%" >
							  			<label id="desCriterioSeleccion"> </label>
							  		</td>
							  		<td align="left" class="etiqueta2" width="17%">
							  			<label>Folio de la promoci&oacute;n : </label>
							  		</td>
							  		<td align="left" width="17%">
							  			<label id="numFolioPromocion"></label>
							  		</td>
							  		<td align="left" width="32%" colspan="3">
							  			&nbsp;
							  		</td>
							  	</tr>
							  	<tr class="par">
							  		<td align="left" class="etiqueta2" width="17%">
							  			<label>Fecha del Oficio de Promoci&oacute;n : </label>
							  		</td>
							  		<td align="left" width="17%" >
							  			<label id="fecOficioSaticb"></label>
							  		</td>
							  		<td align="left" class="etiqueta2" width="17%">
							  			<label>N&uacute;mero de Oficio : </label>
							  		</td>
							  		<td align="left" width="17%">
							  			<label id="numOficioPromocion"></label>
							  		</td>
							  		<td align="left" width="32%" colspan="3">
							  			&nbsp;
							  		</td>
							  	</tr>
				  				<thead>
				  				<tr>
									<td colspan="7">Datos del Patr&oacute;n Responsable de la Obra</td>
								</tr>
							  	</thead>	
							  	<tr valign="top" class="impar" >
								   	<td align="left" colspan="7" width="100%">&nbsp;</td>
								</tr>
								<tr class="par">
							  		<td align="left" class="etiqueta2" width="17%">
							  			<label>Registro Patronal : </label>
							  		</td>
							  		<td align="left" width="17%">
							  			<label id="registroPatronal"></label>
							  		</td>
							  		<td align="left" class="etiqueta2" width="17%">
							  			<label>Nombre &oacute; Raz&oacute;n Social : </label>
							  		</td>
							  		<td align="left" width="49%" colspan="4" >
							  			<label id="razonSocial"></label>
							  		</td>
							  		
							  	</tr>
							  	<tr class="par">
							  		<td align="left" class="etiqueta2" width="17%">
							  			<label>Calle : </label>
							  		</td>
							  		<td align="left" width="49%" colspan="3">
							  			<label id="calle"></label>
							  		</td>
							  	
							  		<td align="left" width="16%" class="etiqueta2" > 
							  			<label>Colonia : </label>
							  		</td>
							  		<td align="left" width="20%">
							  		<label id="colonia"></label>
							  		</td>
							  		<td align="left" width="16%">
							  		&nbsp;
							  		</td>
							  	</tr>
							  	<tr class="par">
							  		<td align="left" class="etiqueta2" width="17%">
							  			<label>N&uacute;mero Exterior : </label>
							  		</td>
							  		<td align="left" width="17%">
							  			<label id="numeroExterior"></label>
							  		</td>
							  		<td align="left" class="etiqueta2" width="17%">
							  			<label>N&uacute;mero Interior :</label>
							  		</td>
							  		<td align="left" width="17%">
							  			<label id="numeroInterior"></label>
							  		</td>
							  		<td align="left" class="etiqueta2" width="16%">
							  			<label>C&oacute;digo Postal : </label>
							  		</td>
							  		<td align="left" width="20%">
							  			<label id="codigoPostal"></label>
							  		</td>
							  		<td align="left" width="16%">
							  			<input type="button" value="Agregar/Modificar Domicilio" id="btnAgreModificDomSaticB" width="10px" height="12px" class="boton" onclick="javascript:cargaDomicilioSaticb();" >
							  		</td>
							  	</tr>
							  	<thead>
								<tr>
									<td colspan="7">Datos de la Obra</td>
								 </tr>
							  	</thead>	
							  	<tr valign="top" class="impar">
									<td align="left" colspan="7">&nbsp;</td>
								</tr>
								<tr class="par">
									<td align="left" class="etiqueta2" width="17%">
							  			<label>SATIC : </label>
							  		</td>
							  		<td align="left" width="17%">
							  			<label id="saticb"></label>
							  		</td>
							  		<td align="left" class="etiqueta2" width="17%">
							  			<label>Registro de Obra : </label>
							  		</td>
							  		<td align="left" width="17%">
							  			<label id="registroObra"></label>
							  		</td>
							  		<td align="left" width="16%" class="etiqueta2" >
							  			<label>C&oacute;digo Postal : </label>
							  		</td>
							  		<td align="left" width="16%" >
							  			<label id="codigoPostalObra"></label>
							  		</td>
							  		<td align="left" width="16%" >
							  			&nbsp;
							  		</td>
							  		
							  	</tr>
							  	<tr class="par">
							  		<td align="left" class="etiqueta2" width="17%">
							  			<label>Calle : </label>
							  		</td>
							  		<td align="left" width="83%" colspan="6">
							  			<label id="calleObra"></label>
							  		</td>
							  		
							  	</tr>
								<tr class="par">
									<td align="left" class="etiqueta2" width="17%">
										<label>Colonia : </label>
							  		</td>
							  		<td align="left" width="17%">
							  			<label id="coloniaObra"></label>
							  		</td>
							  		<td align="left" class="etiqueta2" width="17%">
										<label>Numero Exterior : </label>
							  		</td>
							  		<td align="left" width="17%">
							  			<label id="numeroExteriorObra"></label>
							  		</td>
							  		<td align="left" class="etiqueta2" width="16%">
										<label>Numero Interior : </label>
							  		</td>
							  		<td align="left" width="16%">
							  			<label id="numeroInteriorObra"></label>
							  		</td>
							  		<td align="left" width="16%">
							  			&nbsp;
							  		</td>
							  	</tr>	
							  		
							  	<tr valign="top" class="par">
									<td align="left" colspan="7">&nbsp;</td>
								</tr>
								<tr valign="top" class="impar">
							    	<td align="left" colspan="7">&nbsp;</td>
								</tr>
							</tbody>
						</table>									
					</td>
				</tr>
			</table>							
		</form:form>
		</div>	
		<!-- Seccion de Tabs (Pestañas) -->
		<!-- div id="dgTabsSaticb" style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity =   70) !important;" -->
		<table  style="width: 1100px" align="left" >
			<tr	>
				<td colspan="4" align="left">
				
						<ul class="tabs">
							<li class="etiqueta2" id="seguimientoSaticbTAB_saticbLI"><a href="#seguimientoSaticbTAB_saticb" id="seguimientoSaticbTAB_saticbLink">Seguimiento</a></li>
							<li class="etiqueta2" id="cancelacionGenericoTab_saticbLI" ><a href="#cancelacionGenericoTab_saticb" id="cancelacionGenericoTab_saticbLink">Cancelar Promoci&oacute;n</a></li>
							<li class="etiqueta4" id="derivarSubdelegacionGenericoTab_saticbLI"><a href="#derivarSubdelegacionGenericoTab_saticb" id="derivarSubdelegacionGenericoTab_saticbLink">Derivar Subdelegaci&oacute;n</a></li>
							<li class="etiqueta2" id="derivarFiscalizacionTAB_saticbLI"><a href="#derivarFiscalizacionTAB_saticb" id="derivarFiscalizacionTAB_saticbLink">Derivar Fiscalizaci&oacute;n</a></li>
							<li class="etiqueta2" id="autAviDictamenGenericoTab_saticbLI"><a href="#autAviDictamenGenericoTab_saticb" id="autAviDictamenGenericoTab_saticbLink" >Autorizaci&oacute;n Dictamen</a></li>
							<li class="etiqueta2" id="estatusObraTAB_saticbLI"><a href="#estatusObraTAB_saticb" id="estatusObraTAB_saticbLink">Estatus de Obra</a></li>
							<li class="etiqueta2" id="regularizarObraGenericoTAB_saticbLI"><a href="#regularizarObraGenericoTAB_saticb" id="regularizarObraGenericoTAB_saticbLink">Regularizar Obra</a></li>
							
						</ul>
				
					</td>
				</tr>
				<tr>
					<td>
							<div id="seguimientoSaticbTAB_saticb" class="tab_content" ><jsp:include page="seguimientoSaticbTAB.jsp" /></div>
							<div id="cancelacionGenericoTab_saticb" class="tab_content"><jsp:include page="/WEB-INF/views/seguimiento/promocion/genericos/cancelacionGenericoTAB.jsp" /></div>
							<div id="derivarSubdelegacionGenericoTab_saticb" class="tab_content" ><jsp:include page="/WEB-INF/views/seguimiento/promocion/genericos/derivarSubdelegacionGenericoTAB.jsp" /></div>							
							<div id="derivarFiscalizacionTAB_saticb" class="tab_content"><jsp:include page="/WEB-INF/views/seguimiento/promocion/derivarFiscalizacionGenericoTAB.jsp" /></div>
							<div id="autAviDictamenGenericoTab_saticb" class="tab_content"><jsp:include page="/WEB-INF/views/seguimiento/promocion/genericos/autAvisoDictamenGenericoTAB.jsp" /></div>
							<div id="estatusObraTAB_saticb" class="tab_content"><jsp:include page="/WEB-INF/views/seguimiento/promocion/genericos/estatusObraGenericoTab.jsp" /></div>
							<div id="regularizarObraGenericoTAB_saticb" class="tab_content"><jsp:include page="/WEB-INF/views/seguimiento/promocion/regularizarObraGenericoTAB.jsp" /></div>
				</td>
			</tr>
		</table>	
		
		
	</div>

		<div id="confirmarSATICB" style="display: none;">
		<jsp:include page="/WEB-INF/views/seguimiento/promocion/saticb/confirmarSaticBSeguimiento.jsp" />
		</div>
			
											
							  				