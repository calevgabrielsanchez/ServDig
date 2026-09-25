<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<div id="divDetalleEnTramiteRepresentanteLegal">
	<div class="page_holder" style="width: 550px !important;">
		<div class="contenedor">
			<div class="row" >
				<div class="cell" >
					<div class="row" id="rowDetalleSujetoObligado" >
						<legend>
							<strong>Datos de persona</strong>
						</legend>
							
						<form>
							<input type="hidden" id="personaFisicaIdPersona"/>
						</form>
							
						<table style="width: 600px !important;">
							<tr>
								<td class="label_patrones" style="width: 150px !important">
									<label>RFC :</label>
								</td>
								<td>
									<label id="detalleEnTramiteRepLegalRFC"></label>
								</td>
							</tr>
							<tr>
								<td class="label_patrones">
									<label>CURP :</label>
								</td>
								<td>
									<label id="detalleEnTramiteRepLegalCURP"></label>
								</td>
							</tr>
							<tr>
								<td class="label_patrones">
									<label>Primer Apellido :</label>
								</td>
								<td>
									<label id="detalleEnTramiteRepLegalPrimerApellido"></label>
								</td>
							</tr>
							<tr>
								<td class="label_patrones">
									<label>Segundo Apellido :</label>
								</td>
								<td>
									<label id="detalleEnTramiteRepLegalSegundoApellido"></label>
								</td>
							</tr>
							<tr>
								<td class="label_patrones">
									<label>Nombre(s) :</label>
								</td>
								<td>
									<label id="detalleEnTramiteRepLegalNombre"></label>
								</td>
							</tr>
						</table>
						
						<br />
						
						<table style="width: 600px !important;">
							<tr>
								<td align="center" class="label_patrones" colspan="2">
									&iquest;Poder para Actos de administraci&oacute;n o dominio?: &nbsp;<input type="checkbox" name="indActAdmonDominioDetalleEnTramite" id="indActAdmonDominioDetalleEnTramite" />
									
								</td>
							</tr>
						</table>			
						
						<br />
							
						<table id="tbRepresentanteLegalDatosContactoDetalle" style="width: 100%; vertical-align: top;">
							<thead></thead>
							<tbody style="width: 100%;"></tbody>				
						</table>
						
							<table style="width: 100%; vertical-align: top;">
								<tr>
									<td style="border: none !important;" >
										<div class="opciones">
											<div id="btnGridRLDatosContactoDetalle" class="opcion">
												<form>
													<input type="button"
													onclick="fnOpenDialogNuevoDatoContactoDetalleRL();"
													class="mboton" value="Agregar"
													style="font-size: .8em !important;">
												
													<input type="button"
													onclick="fnOpenDialogEliminarDatoContactoDetalleRL();"
													class="mboton" value="Eliminar"
													style="font-size: .8em !important;">
													
													<input type="button"
													onclick="fnOpenDialogModificarDatoContactoDetalleRL();"
													class="mboton" value="Modificar"
													style="font-size: .8em !important;">
												</form>	
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="2" style="border: none !important;" align="center" >
										<div id="mensajeAfectacionRLDatosContactoDetalle">
										</div>
									</td>
								</tr>
							</table>
						
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


<div id="dgNuevoDatoContactoDetalleRL" style="" title="Agregar Representante Medio de Contacto">
	<div class="page_holder" style="width: 450px !important;">
		<div class="contenedor">
			<div class="row" >
				<div class="cell" >
					<div class="row" id="rowDetalleSujetoObligado" >
	
						<form id="ditTipoContactoForm">
							
								<table style="width: 440px;">
									<tr>
										<td colspan="2" style="border-bottom: none !important;">
											<div id="divErrorTipoFormaContacto" style="color: red !important;"></div>
										</td>
									</tr>
									<tr>
										<td width="40%" style="border-top: none !important;">
											Medio Contacto: 
										</td>
										<td style="border-left: none !important; border-right-width: 1px !important; border-top: none !important;">
											<combo:creaCombo idHtml="tipoContacto"
											idHtmlContenedor="ditTipoContactoForm"
											entidad="mx.gob.imss.ctirss.delta.persistence.DitTipoContacto"
											mostrarSoloActivos = "false" />

									</td>
									</tr>
									<tr>
										<td colspan="2" style="border-bottom: none !important; ">
											<div id="divErrorDesFormaContacto" style="color: red !important;"></div>
										</td>
									</tr>		
									<tr>
										<td style="border-top: none !important; border-right: none !important; border-bottom: 1px !important;">
											Descripci&oacute;n: 		
										</td>
										<td style="border-left: none !important; border-top: none !important; border-right: 1px !important; border-bottom: 1px !important;">
											<input type="text" size="30" id="nuevoDatoContactoDetalleRLDescripcion"
											style="text-transform: none !important;" 
											onfocus="ajustarTamanoDeCampoDescripcionPorTipo('#tipoContacto','#nuevoDatoContactoDetalleRLDescripcion')"
											onkeypress="return inhabilitarEnterKey(event)"/>
										</td>
									</tr>
								</table>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="dgModificarDatoContactoDetalleRL" title="Modificar Medio de Contacto">
	<div class="page_holder" style="width: 450px !important;">
		<div class="contenedor">
			<div class="row" >
				<div class="cell" >
					<div class="row" id="rowDetalleSujetoObligado" >
						<form id="ditTipoContactoFormModificar">
						
							<input type="hidden" id="idVistaModificar"/>
							<input type="hidden" id="errorFormGeneralModificar"/>
								<table style="width: 440px;">
									<tr style="border-right: none !important;">
										<td colspan="2" style="border-bottom: none !important;">
											<div id="divErrorTipoFormaContactoModificar" style="color: red !important;"></div>
										</td>
									</tr>
									<tr>
										<td width="40%" style="border-top: none !important;">
											<label>Medio Contacto: </label>
										</td>
										<td style="border-left: none !important; border-right-width: 1px !important; border-top: none !important;">
											<combo:creaCombo idHtml="tipoContactoModificar"
											idHtmlContenedor="ditTipoContactoFormModificar"
											entidad="mx.gob.imss.ctirss.delta.persistence.DitTipoContacto"
											mostrarSoloActivos = "false" />
									</td>
									</tr>
									<tr>
										<td colspan="2" style="border-bottom: none !important; ">
											<div id="divErrorDesFormaContactoModificar" style="color: red !important;"></div>
										</td>
									</tr>
									<tr>
										<td style="border-top: none !important; border-right: none !important; border-bottom: 1px !important;">
											<label>Descripci&oacute;n: </label>
										</td>
										<td style="border-left: none !important; border-top: none !important; border-right: 1px !important; border-bottom: 1px !important;">
											<input type="text" size="30" id="modificarDatoContactoDetalleRLDescripcion" 
											style="text-transform: none !important;"
											onfocus="ajustarTamanoDeCampoDescripcionPorTipo('#tipoContactoModificar','#modificarDatoContactoDetalleRLDescripcion')"
											onkeypress="return inhabilitarEnterKey(event)"/>
										</td>
									</tr>
								</table>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="divDetalleEnTramiteRepresentanteLegalEliminar">
	<div class="page_holder" style="width: 550px !important;">
		<div class="contenedor">
			<div class="row" >
				<div class="cell" >
					<div class="row" id="rowDetalleSujetoObligado" >

						<legend>
							<strong>Datos de persona</strong>
						</legend>
									
						<table style="width: 600px !important;">
							<tr>
								<td class="label_patrones" style="width: 150px !important;">
									<label>RFC :</label>
								</td>
								<td>
									<label id="detalleEnTramiteRepLegalRFCEliminar"></label>
								</td>
							</tr>
							<tr>
								<td class="label_patrones">
									<label>CURP :</label>
								</td>
								<td>
									<label id="detalleEnTramiteRepLegalCURPEliminar"></label>
								</td>
							</tr>
							<tr>
								<td class="label_patrones">
									<label>Primer Apellido :</label>
								</td>
								<td>
									<label id="detalleEnTramiteRepLegalPrimerApellidoEliminar"></label>
								</td>
							</tr>
							<tr>
								<td class="label_patrones">
									<label>Segundo Apellido :</label>
								</td>
								<td>
									<label id="detalleEnTramiteRepLegalSegundoApellidoEliminar"></label>
								</td>
							</tr>
							<tr>
								<td class="label_patrones">
									<label>Nombre(s) :</label>
								</td>
								<td>
									<label id="detalleEnTramiteRepLegalNombreEliminar"></label>
								</td>
							</tr>
						</table>
						
						<br />
						
						<table style="width: 600px !important;">
							<tr>
								<td align="center" class="label_patrones" colspan="2">
									&iquest;Poder para Actos de administraci&oacute;n o dominio?: &nbsp;<label id="indActAdmonDominioDetalleEliminar"></label>
								</td>
							</tr>
						</table>	
						
						<br />		

						<table id="tbRepresentanteLegalDatosContactoDetalleEliminar" style="width: 100%; vertical-align: top;">
							<thead></thead>
							<tbody style="width: 100%;"></tbody>				
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
