<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<div id="divDetalleEnTramiteSocioFisico" style="float: left; width: 90% !important; padding-top: 10px;" class="page_holder dialogo_holder">
 	<div class="page_holder" style="width: 550px !important;">
		<div class="contenedor">
			<div class="row" >
				<div class="cell" >
					<div class="row" id="rowDetalleSocioFisicoModificar" >

						<legend>
							<strong>Datos de persona</strong>
						</legend>
						
						<form>
							<input type="hidden" id="idPersona"/>
							<input type="hidden" id="esNacional"/>
							<input type="hidden" id="esDomicilioNacional"/>
						</form>
						
						<table style="width: 100% !important;">
							<tr>
								<td class="label_patrones" style="width: 140px !important;">
									<label>RFC :</label>
								</td>
								<td style="width: 200px !important;">
									<label id="detalleEnTramiteSocioFisicoRFC"></label>
								</td>
								<td class="label_patrones" style="width: 140px !important;">
									<label>CURP :</label>
								</td>
								<td>
									<label id="detalleEnTramiteSocioFisicoCURP"></label>
								</td>
							</tr>
							<tr>
								<td class="label_patrones" >
									<label>Primer apellido :</label>
								</td>
								<td>
									<label id="detalleEnTramiteSocioFisicoPrimerApellido"></label>
								</td>
								<td class="label_patrones" >
									<label>Segundo apellido :</label>
								</td>
								<td>
									<label id="detalleEnTramiteSocioFisicoSegundoApellido"></label>
								</td>
							</tr>
							<tr>
								<td class="label_patrones">
									<label>Nombre(s) :</label>
								</td>
								<td colspan="3">
									<label id="detalleEnTramiteSocioFisicoNombres"></label>
								</td>
							</tr>
						</table>
						
						<div id="divDetalleEnTramiteSocioFisicoSinDomicilio" style="display: none;">
							<table style="width:100% !important;">
								<tr>
									<td class="label_patrones" style="width: 120px !important;">
										<spring:message code="label.domicilio.fiscal"/>
									</td>
									<td>
										<span  style="color: red;">
											No se ha encontrado ning&uacute;n domicilio fiscal asociado a esta persona.
										</span>
									</td>
								</tr>
							</table>
						</div>
						
						<div id="divDetalleEnTramiteSocioFisicoDomicilio" style="display: none;">
							<table style="width:100% !important;">
								<tr>
									<td class="label_patrones" style="width: 120px !important;">
										<spring:message code="label.domicilio.fiscal"/>
									</td>
									<td>
										<label id="lbFiscicaReferUno"></label> #<label id="detalleTramiteFiscicoNumExt"></label>, <spring:message code="label.interior" /> <label id="detalleTramiteFiscioNumInt"></label>, <spring:message code="label.colonia" /> <label id="detalleTramiteFiscicoColonia"></label>, <label id="detalleTramiteFiscicoDeleg"></label>, <label id="detalleTramiteFiscicoEntidad"></label>, <spring:message code="label.codigo.postal.abreviado" /> <label id="detalleTramiteFiscicoCP"></label>
									</td>
								</tr>
							</table>
						</div>
		
						<legend>
							<strong>Datos de Contacto</strong>
						</legend>
						
						<table id="tbSocioFisicoDatosContactoDetalle" style="width: 60% !important; vertical-align: top;">
							<thead></thead>
							<tbody style="width: 100%;"></tbody>				
						</table>
						
						<form>
							<table style="width: 60%; vertical-align: top; border: none !important;">
								<tr>
									<td style="border: none !important;" >
										<div class="opciones">
											<div id="btnGridSocioFisicoDatosContactoDetalle" class="opcion">
												
													<input type="button"
													onclick="fnOpenDialogNuevoDatoContactoDetalleSocio();"
													class="mboton" value="Agregar"
													style="font-size: .8em !important;">
												
													<input type="button"
													onclick="fnOpenDialogEliminarDatoContactoDetalleSocio();"
													class="mboton" value="Eliminar"
													style="font-size: .8em !important;">
													
													<input type="button"
													onclick="fnOpenDialogModificarDatoContactoDetalleSocio();"
													class="mboton" value="Modificar"
													style="font-size: .8em !important;">
													
											</div>
										</div>
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

<div id="divDetalleEnTramiteSocioFisicoEliminar" style="display:none;">
	<div class="page_holder" style="width: 550px !important;">
		<div class="contenedor">
			<div class="row" >
				<div class="cell" >
					<div class="row" id="rowDetalleSocioFisicoModificar" >
						<legend>
							<strong>Datos de persona</strong>
						</legend>
						
						<table style="width: 100% !important;">
							<tr>
								<td class="label_patrones" style="width: 120px !important;">
									<label>RFC :</label>
								</td>
								<td>
									<label id="detalleEnTramiteSocioFisicoRFCEliminar"></label>
								</td>
								<td class="label_patrones" style="width: 120px !important;">
									<label>CURP :</label>
								</td>
								<td>
									<label id="detalleEnTramiteSocioFisicoCURPEliminar"></label>
								</td>
							</tr>
							<tr>
								<td class="label_patrones">
									<label>Primer apellido :</label>
								</td>
								<td>
									<label id="detalleEnTramiteSocioFisicoPrimerApellidoEliminar"></label>
								</td>
								<td class="label_patrones">
									<label>Segundo apellido :</label>
								</td>
								<td>
									<label id="detalleEnTramiteSocioFisicoSegundoApellidoEliminar"></label>
								</td>
							</tr>
							<tr>
								<td class="label_patrones">
									<label>Nombre(s) :</label>
								</td>
								<td>
									<label id="detalleEnTramiteSocioFisicoNombresEliminar"></label>
								</td>
							</tr>
						</table>
						
						<!--<div id="divDetalleEnTramiteSocioFisicoEliminarSinDomicilio" style="display: none;">
							<table style="width:100% !important;">
								<tr>
									<td class="label_patrones" style="width: 120px !important;">
										<spring:message code="label.domicilio.fiscal"/>
									</td>
									<td>
										<span  style="color: red;">
											No se ha encontrado ning&uacute;n domicilio fiscal asociado a esta persona.
										</span>
									</td>
								</tr>
							</table>
						</div>
						
						<div id="divDetalleEnTramiteSocioFisicoEliminarDomicilio" style="display: none;">
							<table style="width:100% !important;">
								<tr>
									<tr>
									<td class="label_patrones" style="width: 120px !important;">
										<spring:message code="label.domicilio.fiscal"/>
									</td>
									<td>
										<label id="lbFiscicaReferUno"></label> #<label id="detalleTramiteFiscicoNumExt"></label>, <spring:message code="label.interior" /> <label id="detalleTramiteFiscioNumInt"></label>, <spring:message code="label.colonia" /> <label id="detalleTramiteFiscicoColonia"></label>, <label id="detalleTramiteFiscicoDeleg"></label>, <label id="detalleTramiteFiscicoEntidad"></label>, <spring:message code="label.codigo.postal.abreviado" /> <label id="detalleTramiteFiscicoCP"></label>
									</td>
								</tr>
								</tr>
							</table>
						</div>
						
						<legend>
							<strong>Datos de Contacto</strong>
						</legend>
						
						<table id="tbSocioFisicoDatosContactoDetalleEliminar" style="width: 100%; vertical-align: top;">
							<thead></thead>
							<tbody style="width: 100%;"></tbody>				
						</table>-->
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="divDetalleEnTramiteSocioMoral"  style="display:none;">
	<div class="page_holder" style="width: 550px !important;">
		<div class="contenedor">
			<div class="row" >
				<div class="cell" >
					<div class="row" id="rowDetalleSocioModificar" >
						<legend id="detalleTramiteSocioMoralDPLegenda">
							<strong>Datos de persona</strong>
						</legend>
						
						<form>
							<input type="hidden" id="idPersona"/>
							<input type="hidden" id="esNacional"/>
							<input type="hidden" id="esDomicilioNacional"/>
						</form>
						
						<table style="width: 100% !important;">
							<tr>
								<td class="label_patrones" id="celdaTituloRFC" style="width: 120px !important;">
									<label>RFC :</label>
								</td>
								<td id="celdaRFCMoral">
									<label id="detalleEnTramiteSocioMoralRFC"></label>
								</td>
								<td class="label_patrones" style="width: 220px !important;">
									<label>Denominaci&oacute;n / Raz&oacute;n Social :</label>
								</td>
								<td>
									<label id="detalleEnTramiteSocioMoralDenomRazonSocial"></label>
								</td>
							</tr>
							<tr>
								<td class="label_patrones" id="celdaTituloTipoSociedad" style="width: 120px !important;">
									<label>Tipo de Sociedad :</label>
								</td>
								<td colspan="3" id="celdaTipoSociedad">
									<label id="detalleEnTramiteSocioMoralTipoSociedad"></label>
								</td>
							</tr>
						</table>
						<br>
						<legend id="detalleTramiteSocioMoralECLegenda">
							<strong>Escritura Constitutiva</strong>
						</legend>
						
						<table id="tblDetalleSocioMoralEC" style="width: 100%;">
							<tr>
								<td class="label_patrones" style="width: 140px !important;">
									<label> <spring:message code="label.escritura.conts.num.esc" />:</label>
								</td>
								<td class="label_patrones_data" style="width: 280px !important;">
									<label id="detalleEnTramiteSocioMoralNumEscritura"></label>
												
								</td>				
								<td class="label_patrones" style="width: 200px !important;">
									<label> <spring:message code="label.escritura.conts.notaria" />:</label>
								</td>
								<td class="label_patrones_data">
									<label id="detalleEnTramiteSocioMoralNumNotaria"></label>
								</td>
							</tr>
							<tr>
								<td class="label_patrones">
										<label><spring:message code="label.entidad.federativa"/></label>
								</td>
								<td class="label_patrones_data">
									<label id="detalleEnTramiteSocioMoralEntidadFed"></label>
								</td>				
								<td class="label_patrones">
									<label ><spring:message code="label.escritura.conts.municipio"/>:</label>
								</td>
								<td class="label_patrones_data">
									<label id="detalleEnTramiteSocioMoralMunicipio"></label>
								</td>
							</tr>
							<tr>	
								<td style="border-width:0px 0 0 0px;" class="label_patrones">
									<label> <spring:message code="label.escritura.conts.fecha"/>:</label>
								</td>
								<td class="label_patrones_data">
									<label id="detalleEnTramiteSocioMoralFecConstitucion"></label>				
								</td>			
							</tr>
						</table>
						
									
						<table style="width: 100%;" id="tblDetalleSocioMoralFM">
							<tr>
								<td class="label_patrones" style="width: 120px !important;">
									<label>Folio Mercantil :</label>
								</td>
								<td>
									<label id="detalleEnTramiteSocioMoralFolioMercantil"></label>
								</td>
							</tr>
						</table>
						<table style="width: 100%;" id="tblDetalleSocioMoralSPVF">
							<tr>
								<td class="label_patrones">
									<label>Secci&oacute;n :</label>
								</td>
								<td>
									<label id="detalleEnTramiteSocioMoralSeccion"></label>
								</td>
								<td class="label_patrones">
									<label>Partida :</label>
								</td>
								<td>
									<label id="detalleEnTramiteSocioMoralPartida"></label>
								</td>
								<td class="label_patrones">
									<label>Volumen :</label>
								</td>
								<td>
									<label id="detalleEnTramiteSocioMoralVolumen"></label>
								</td>
								<td class="label_patrones">
									<label>Foja :</label>
								</td>
								<td>
									<label id="detalleEnTramiteSocioMoralFoja"></label>
								</td>
							</tr>
						</table>
						
						<div id="divDetalleEnTramiteSocioMoralSinDomicilio" style="display: none;">
							<table style="width:100% !important;">
								<tr>
									<td class="label_patrones" style="width: 120px !important;">
										<spring:message code="label.domicilio.fiscal"/>
									</td>
									<td>
										<span  style="color: red;">
											No se ha encontrado ning&uacute;n domicilio fiscal asociado a esta persona.
										</span>
									</td>
								</tr>
							</table>
						</div>
						
						<div id="divDetalleEnTramiteSocioMoralDomicilio" style="display: none;">
							<table style="width:100% !important;">
								<tr>
									<td class="label_patrones" style="width: 120px !important;">
										<spring:message code="label.domicilio.fiscal"/>
									</td>
									<td>
										<label id="detalleTramiteMoralReferUno"></label> #<label id="detalleTramiteMoralNumExt"></label>, <spring:message code="label.interior" /> <label id="detalleTramiteMoralNumInt"></label>, <spring:message code="label.colonia" /> <label id="detalleTramiteMoralColonia"></label>, <label id="detalleTramiteMoralDeleg"></label>, <label id="detalleTramiteMoralEntidad"></label>, <spring:message code="label.codigo.postal.abreviado" /> <label id="detalleTramiteMoralCP"></label>
									</td>
								</tr>
							</table>
						</div>
						
						<div id="detalleSocioMoralDatosContacto">
							<legend id="detalleTramiteSocioMoralDCLegenda">
								<strong>Datos de Contacto</strong>
							</legend>
							
							
							<table id="tbSocioMoralDatosContactoDetalle" style="width: 100%; vertical-align: top;">
								<thead></thead>
								<tbody style="width: 100%;"></tbody>				
							</table>
						
							<form>
								<table style="width: 100%; vertical-align: top;">
									<tr>
										<td style="border: none !important;" >
											<div class="opciones">
												<div id="btnGridSocioMoralDatosContactoDetalle" class="opcion">
													
														<input type="button"
														onclick="fnOpenDialogNuevoDatoContactoDetalleSocio();"
														class="mboton" value="Agregar"
														style="font-size: .8em !important;">
													
														<input type="button"
														onclick="fnOpenDialogEliminarDatoContactoDetalleSocio();"
														class="mboton" value="Eliminar"
														style="font-size: .8em !important;">
														
														<input type="button"
														onclick="fnOpenDialogModificarDatoContactoDetalleSocio();"
														class="mboton" value="Modificar"
														style="font-size: .8em !important;">
														
												</div>
											</div>
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
</div>

<div id="divDetalleEnTramiteSocioMoralEliminar"  style="display:none;">
	<div class="page_holder" style="width: 750px !important;">
		<div class="contenedor">
			<div class="row" >
				<div class="cell" >
					<div class="row" id="rowDetalleSocioModificar" >
						<legend>
							<strong>Datos de persona</strong>
						</legend>
						
						<table style="width: 100% !important;">
							<tr>
								<td class="label_patrones" style="width: 120px !important;">
									<label>RFC :</label>
								</td>
								<td>
									<label id="detalleEnTramiteSocioMoralRFCEliminar"></label>
								</td>
								<td class="label_patrones" style="width: 220px !important;">
									<label>Denominaci&oacute;n / Raz&oacute;n Social :</label>
								</td>
								<td>
									<label id="detalleEnTramiteSocioMoralDenomRazonSocialEliminar"></label>
								</td>
							</tr>
							<tr>
								<td class="label_patrones" style="width: 120px !important;">
									<label>Tipo de Sociedad :</label>
								</td>
								<td colspan="3">
									<label id="detalleEnTramiteSocioMoralTipoSociedadEliminar"></label>
								</td>
							</tr>
						</table>
						<br>
						<legend>
							<strong>Escritura Constitutiva</strong>
						</legend>
						
						<table style="width: 100%;">
							<tr>
								<td class="label_patrones" style="width: 170px !important;">
									<label> <spring:message code="label.escritura.conts.num.esc" />:</label>
								</td>
								<td class="label_patrones_data" style="width: 150px !important;">
									<label id="detalleEnTramiteSocioMoralNumEscrituraEliminar"></label>
												
								</td>				
								<td class="label_patrones" style="width: 200px !important;">
									<label> <spring:message code="label.escritura.conts.notaria" />:</label>
								</td>
								<td class="label_patrones_data">
									<label id="detalleEnTramiteSocioMoralNumNotariaEliminar"></label>
								</td>
							</tr>
							<tr>
								<td class="label_patrones">
										<label><spring:message code="label.entidad.federativa"/></label>
								</td>
								<td class="label_patrones_data">
									<label id="detalleEnTramiteSocioMoralEntidadFedEliminar"></label>
								</td>				
								<td class="label_patrones">
									<label ><spring:message code="label.escritura.conts.municipio"/>:</label>
								</td>
								<td class="label_patrones_data">
									<label id="detalleEnTramiteSocioMoralMunicipioEliminar"></label>
								</td>
							</tr>
							<tr>	
								<td class="label_patrones">
									<label> <spring:message code="label.escritura.conts.fecha"/>:</label>
								</td>
								<td class="label_patrones_data">
									<label id="detalleEnTramiteSocioMoralFecConstitucionEliminar"></label>				
								</td>			
							</tr>
						</table>
			
						
						<table style="width: 100%;">
							<tr>
								<td class="label_patrones" style="width: 120px !important;">
									<label>Folio Mercantil :</label>
								</td>
								<td>
									<label id="detalleEnTramiteSocioMoralFolioMercantilEliminar"></label>
								</td>
							</tr>
						</table>
						<table style="width: 100%;">
							<tr>
								<td class="label_patrones">
									<label>Secci&oacute;n :</label>
								</td>
								<td>
									<label id="detalleEnTramiteSocioMoralSeccionEliminar"></label>
								</td>
								<td class="label_patrones">
									<label>Partida :</label>
								</td>
								<td>
									<label id="detalleEnTramiteSocioMoralPartidaEliminar"></label>
								</td>
								<td class="label_patrones">
									<label>Volumen :</label>
								</td>
								<td>
									<label id="detalleEnTramiteSocioMoralVolumenEliminar"></label>
								</td>
								<td class="label_patrones">
									<label>Foja :</label>
								</td>
								<td>
									<label id="detalleEnTramiteSocioMoralFojaEliminar"></label>
								</td>
							</tr>
						</table>
						
						<!--<div id="divDetalleEnTramiteSocioMoralEliminarSinDomicilio" style="display: none;">
							<table style="width:100% !important;">
								<tr>
									<td class="label_patrones" style="width: 120px !important;">
										<spring:message code="label.domicilio.fiscal"/>
									</td>
									<td>
										<span  style="color: red;">
											No se ha encontrado ning&uacute;n domicilio fiscal asociado a esta persona.
										</span>
									</td>
								</tr>
							</table>
						</div>
						
						<div id="divDetalleEnTramiteSocioMoralEliminarDomicilio" style="display: none;">
							<table style="width:100% !important;">
								<tr>
									<td class="label_patrones" style="width: 120px !important;">
										<spring:message code="label.domicilio.fiscal"/>
									</td>
									<td>
										<label id="detalleTramiteEliminarMoralReferUno"></label> #<label id="detalleTramiteEliminarMoralNumExt"></label>, <spring:message code="label.interior" /> <label id="detalleTramiteEliminarMoralNumInt"></label>, <spring:message code="label.colonia" /> <label id="detalleTramiteEliminarMoralColonia"></label>, <label id="detalleTramiteEliminarMoralDeleg"></label>, <label id="detalleTramiteEliminarMoralEntidad"></label>, <spring:message code="label.codigo.postal.abreviado" /> <label id="detalleTramiteEliminarMoralCP"></label>
									</td>
								</tr>
							</table>
						</div>
						
						<legend>
							<strong>Datos de Contacto</strong>
						</legend>
						
						<table id="tbSocioMoralDatosContactoDetalleEliminar" style="width: 100%; vertical-align: top;">
							<thead></thead>
							<tbody style="width: 100%;"></tbody>				
						</table>
						-->
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="divDetalleEnTramiteSocioFideicomiso"  style="display:none;">
	<div class="page_holder" style="width: 550px !important;">
		<div class="contenedor">
			<div class="row" >
				<div class="cell" >
					<div class="row" id="rowDetalleSocioModificar" >
						<legend>
							<strong>Datos de persona</strong>
						</legend>
						
						<form>
							<input type="hidden" id="idPersona"/>
							<input type="hidden" id="esNacional"/>
							<input type="hidden" id="esDomicilioNacional"/>
						</form>
						
						<table style="width: 100% !important;">
							<tr>
								<td class="label_patrones">
									<label>Nombre del fideicomiso :</label>
								</td>
								<td>
									<label id="detalleEnTramiteSocioFideicomisoDenominacionRazonSocial"></label>
								</td>
							</tr>
							<tr>
								<td class="label_patrones">
									<label>RFC del fideicomiso:</label>
								</td>
								<td>
									<label id="detalleEnTramiteSocioFideicomisoRFC"></label>
								</td>
							</tr>
						</table>
						
						<div id="divDetalleEnTramiteContratoSocioFideicomiso">
						
							<strong>Secci&oacute;n contrato</strong>
							
							<table style="width: 100%;">
								<tr>
									<td class="label_patrones">
										<label>N&uacute;mero de instrumento de protocolizaci&oacute;n :</label>
									</td>
									<td>
										<label id="detalleEnTramiteSocioFideicomisoNumeroInstrumetoProtocolizacion"></label>
									</td>
									<td class="label_patrones">
										<label>Notar&iacute;a o corredur&iacute;a:</label>
									</td>
									<td>
										<label id="detalleEnTramiteSocioFideicomisoNotariaCorreduria"></label>
									</td>
								</tr>
								<tr>
									<td class="label_patrones">
										<label>Estado:</label>
									</td>
									<td>
										<label id="detalleEnTramiteSocioFideicomisoEstado"></label>
									</td>
									<td class="label_patrones">
										<label>Fecha de expedici&oacute;n del contrato:</label>
									</td>
									<td>
										<label id="detalleEnTramiteSocioFideicomisoFechaExpedicionContrato"></label>
									</td>
								</tr>
							</table>
						</div>
						
						<div id="divDetalleEnTramiteSocioFideicomisoSinDomicilio" style="display: none;">
							<table style="width:100% !important;">
								<tr>
									<td class="label_patrones" style="width: 120px !important;">
										<spring:message code="label.domicilio.fiscal"/>
									</td>
									<td>
										<span  style="color: red;">
											No se ha encontrado ning&uacute;n domicilio fiscal asociado a esta persona.
										</span>
									</td>
								</tr>
							</table>
						</div>
						
						<div id="divDetalleEnTramiteSocioFideicomisoDomicilio" style="display: none;">
							<table style="width:100% !important;">
								<tr>
									<td class="label_patrones" style="width: 120px !important;">
										<spring:message code="label.domicilio.fiscal"/>
									</td>
									<td>
										<label id="detalleTramiteFideicomisoReferUno"></label> #<label id="detalleTramiteFideicomisoNumExt"></label>, <spring:message code="label.interior" /> <label id="detalleTramiteFideicomisoNumInt"></label>, <spring:message code="label.colonia" /> <label id="detalleTramiteFideicomisoColonia"></label>, <label id="detalleTramiteFideicomisoDeleg"></label>, <label id="detalleTramiteFideicomisoEntidad"></label>, <spring:message code="label.codigo.postal.abreviado" /> <label id="detalleTramiteFideicomisoCP"></label>
									</td>
								</tr>
							</table>
						</div>
						
						<legend>
							<strong>Datos de Contacto</strong>
						</legend>
						
						<table id="tbSocioFideicomisoDatosContactoDetalle" style="width: 100%; vertical-align: top;">
							<thead></thead>
							<tbody style="width: 100%;"></tbody>				
						</table>
						
						<form>
							<table style="width: 100%; vertical-align: top;">
								<tr>
									<td style="border: none !important;" >
										<div class="opciones">
											<div id="btnGridSocioFideicomisoDatosContactoDetalle" class="opcion">
												
													<input type="button"
													onclick="fnOpenDialogNuevoDatoContactoDetalleSocio();"
													class="mboton" value="Agregar"
													style="font-size: .8em !important;">
												
													<input type="button"
													onclick="fnOpenDialogEliminarDatoContactoDetalleSocio();"
													class="mboton" value="Eliminar"
													style="font-size: .8em !important;">
													
													<input type="button"
													onclick="fnOpenDialogModificarDatoContactoDetalleSocio();"
													class="mboton" value="Modificar"
													style="font-size: .8em !important;">
													
											</div>
										</div>
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

<div id="divDetalleEnTramiteSocioFideicomisoEliminar"  style="display:none;">
	<div class="page_holder" style="width: 550px !important;">
		<div class="contenedor">
			<div class="row" >
				<div class="cell" >
					<div class="row" id="rowDetalleSocioModificar" >
						<legend>
							<strong>Datos de persona</strong>
						</legend>
						
						<table style="width: 100% !important;">
							<tr>
								<td class="label_patrones">
									<label>Nombre del fideicomiso :</label>
								</td>
								<td>
									<label id="detalleEnTramiteSocioFideicomisoDenominacionRazonSocialEliminar"></label>
								</td>
							</tr>
							<tr>
								<td class="label_patrones">
									<label>RFC del fideicomiso:</label>
								</td>
								<td>
									<label id="detalleEnTramiteSocioFideicomisoRFCEliminar"></label>
								</td>
							</tr>
						</table>
						
						<div id="divDetalleEnTramiteContratoSocioFideicomiso">
						
							<strong>Secci&oacute;n contrato</strong>
							
							<table style="width: 100%;">
								<tr>
									<td class="label_patrones">
										<label>N&uacute;mero de instrumento de protocolizaci&oacute;n :</label>
									</td>
									<td>
										<label id="detalleEnTramiteSocioFideicomisoNumeroInstrumetoProtocolizacionEliminar"></label>
									</td>
									<td class="label_patrones">
										<label>Notar&iacute;a o corredur&iacute;a:</label>
									</td>
									<td>
										<label id="detalleEnTramiteSocioFideicomisoNotariaCorreduriaEliminar"></label>
									</td>
								</tr>
								<tr>
									<td class="label_patrones">
										<label>Estado:</label>
									</td>
									<td>
										<label id="detalleEnTramiteSocioFideicomisoEstadoEliminar"></label>
									</td>
									<td class="label_patrones">
										<label>Fecha de expedici&oacute;n del contrato:</label>
									</td>
									<td>
										<label id="detalleEnTramiteSocioFideicomisoFechaExpedicionContratoEliminar"></label>
									</td>
								</tr>
							</table>
						</div>
						
					<!--
						<div id="divDetalleEnTramiteSocioFideicomisoEliminarSinDomicilio" style="display: none;">
							<table style="width:100% !important;">
								<tr>
									<td class="label_patrones" style="width: 120px !important;">
										<spring:message code="label.domicilio.fiscal"/>
									</td>
									<td>
										<span  style="color: red;">
											No se ha encontrado ning&uacute;n domicilio fiscal asociado a esta persona.
										</span>
									</td>
								</tr>
							</table>
						</div>
						
						<div id="divDetalleEnTramiteSocioFideicomisoEliminarDomicilio" style="display: none;">
							<table style="width:100% !important;">
								<tr>
									<td class="label_patrones" style="width: 120px !important;">
										<spring:message code="label.domicilio.fiscal"/>
									</td>
									<td>
										<label id="detalleTramiteEliminarFideicomisoReferUno"></label> #<label id="detalleTramiteEliminarFideicomisoNumExt"></label>, <spring:message code="label.interior" /> <label id="detalleTramiteEliminarFideicomisoNumInt"></label>, <spring:message code="label.colonia" /> <label id="detalleTramiteEliminarFideicomisoColonia"></label>, <label id="detalleTramiteEliminarFideicomisoDeleg"></label>, <label id="detalleTramiteEliminarFideicomisoEntidad"></label>, <spring:message code="label.codigo.postal.abreviado" /> <label id="detalleTramiteEliminarFideicomisoCP"></label>
									</td>
								</tr>
							</table>
						</div>
						
						<legend>
							<strong>Datos de Contacto</strong>
						</legend>
						
						<table id="tbSocioFideicomisoDatosContactoDetalleEliminar" style="width: 100%; vertical-align: top;">
							<thead></thead>
							<tbody style="width: 100%;"></tbody>				
						</table>
					-->
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="dgNuevoDatoContactoDetalleSocio" title="Agregar Medio de Contacto" style="display:none;">

	<div class="page_holder" style="width: 510px !important;">
		<div class="contenedor">
			<div class="row" >
				<div class="cell" >
					<div class="row" id="rowDetalleSocioModificar" >
						<form id="ditTipoContactoFormSocio">
							<fieldset style="width: 500px;">
								<table style="width: 500px; border: none !important; margin-left: auto !important;">
									<tr>
											<td colspan="2" style="border: none !important;">
												<div id="divErrorTipoContactoSocio" style="color: red !important;"></div>
											</td>
										</tr>
									<tr>
										<td style="width: 150px; border: none !important;">
											Medio Contacto: 
										</td>
										<td style="border: none !important;">
											<combo:creaCombo idHtml="tipoContactoSocio" idHtmlContenedor="ditTipoContactoFormSocio" 
															entidad="mx.gob.imss.ctirss.delta.persistence.DitTipoContacto"
															mostrarSoloActivos = "false"/>
										</td>
									</tr>		
								</table>
							</fieldset>
							<fieldset style="width: 500px;">
								<table style="width: 500px; border: none !important;">
									<tr>
										<td colspan="2" style="border: none !important; ">
											<div id="divErrorNuevoDatoContactoDetalleSocioDescripcion" style="color: red !important;"></div>
										</td>
									</tr>
									<tr>
										<td style="width: 150px; border: none !important;">
											Descripci&oacute;n: 		
										</td>
										<td style="margin-left: 20px; border: none !important;">
											<input type="text" size="25" 
											onfocus="ajustarTamanoDeCampoDescripcionPorTipo('#tipoContactoSocio','#nuevoDatoContactoDetalleSocioDescripcion'); asignaFuncionAEvento('onkeydown','modificarDatoContactoDetalleSocioDescripcion',validarNumeros(event));" 
											id="nuevoDatoContactoDetalleSocioDescripcion" 
											style="border: thin !important; border-color: gray !important; border-bottom-style: solid !important; border-left-style: solid !important; border-right-style: solid !important; border-top-style: solid !important; text-transform: none !important;"></input>
										</td>
									</tr>
								</table>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="dgModificarDatoContactoDetalleSocio" title="Modificar Medio de Contacto" style="display:none;">
	<div class="page_holder" style="width: 510px !important;">
		<div class="contenedor">
			<div class="row" >
				<div class="cell" >
					<div class="row" id="rowDetalleSocioModificar" >
						<form id="ditTipoContactoFormModificarSocio">
							<input type="hidden" id="idVistaModificarSocio"/>
							<input type="hidden" id="errorFormGeneralModificarSocio"/>
							<fieldset style="width: 500px;">	
								<table style="width: 500px; border: none !important;">
									<tr style="border: none !important;">
										<td colspan="2" style="border: none !important;">
											<div id="divErrorTipoContactoModificarSocio" style="color: red !important;"></div>
										</td>
									</tr>
									<tr>
										<td style="width: 150px; border: none !important;">
											<label>Medio Contacto: </label>
										</td>
										<td style="border: none !important;">
											<combo:creaCombo idHtml="tipoContactoModificarSocio" idHtmlContenedor="ditTipoContactoFormModificarSocio" 
															entidad="mx.gob.imss.ctirss.delta.persistence.DitTipoContacto"
															mostrarSoloActivos = "false"/>
											
										</td>
									</tr>
								</table>
							</fieldset>
							<fieldset style="width: 500px;">
								<table style="width: 500px; border: none !important;">	
									<tr>
										<td colspan="2" style="border: none !important; ">
											<div id="divErrorModificarDatoContactoDetalleSocioDescripcion" style="color: red !important;"></div>
										</td>
									</tr>
									<tr>
										<td style="width: 150px; border: none !important;">	
											<label>Descripci&oacute;n: </label>
										</td>
										<td style="border: none !important;">
											<input type="text" onfocus="ajustarTamanoDeCampoDescripcionPorTipo('#tipoContactoModificarSocio','#modificarDatoContactoDetalleSocioDescripcion'); asignaFuncionAEvento('onkeydown','modificarDatoContactoDetalleSocioDescripcion',validarNumeros(event));" size="25" 
											id="modificarDatoContactoDetalleSocioDescripcion" 
											style="border: thin !important; border-color: gray !important; border-bottom-style: solid !important; border-left-style: solid !important; border-right-style: solid !important; border-top-style: solid !important; text-transform: none !important;"></input>
										</td>
									</tr>
								</table>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


<div id="dgEliminarDatoContactoDetalleSocio" title="&iquest;Eliminar elemento?">		
			Este dato de contacto ser&aacute; eliminado, &iquest;Est&aacute; Ud. seguro?				
</div>