<%@ include file="../../general/taglibs.jsp"%>

<div id="divDetallesocioFisico" class="page_holder dialogo_holder" style="display:none;">
		
		<table id="tablaNsFisicaDatosPersona" style="width: 800px !important;">
			<tr id="filaNSRFC">
				<td class="label_patrones" style="width: 120px !important;">
					<label>RFC:</label>
				</td>
				<td style="width: 120px !important;">
					<label id="lbFisicaRFC"></label>
				</td>
				<div id="filaNSCURP">
					<td class="label_patrones" style="width: 120px !important;">
						<label>CURP :</label>
					</td>
					<td style="width: 100px !important;">
						<label id="lbFisicaCURP"></label>
					</td>
				</div>
			</tr>	
			<tr>
				<td class="label_patrones" >
					<label>Primer apellido:</label>
				</td>
				<td>
					<label id="lbFisicaPrimerAp"></label>
				</td>
				<td class="label_patrones" >
					<label>Segundo apellido:</label>
				</td>
				<td>
					<label id="lbFisicaSegundoAp"></label>
				</td>
			</tr>
			<tr>
				<td class="label_patrones" >
					<label>Nombres(s):</label>
				</td>
				<td colspan="3">
					<label id="lbFisicaNombre"></label>
				</td>
			</tr>
		</table>
		<!-- 
		<div id="divDetalleSocioFisicoSinDomicilio" style="display: none;">
			<table id="tablaDetalleSocioFisicaSinDatosDomicilio" style="width: 100%;">
				<tr>
					<td class="label_patrones" style="width: 120px !important;">
						<spring:message code="label.domicilio.fiscal"/>:
					</td>
					<td style="color: red;">
						<span>No se ha encontrado ning&uacute;n domicilio fiscal asociado a esta persona.</span>
					</td>
				</tr>
			</table>
		</div>
		 -->
		<div id="divDetalleSocioFisicoDomicilio" style="display: none;">
			<table id="tablaDetalleSocioFisicaDatosDomicilio" style="width: 800px !important;">
				<tr>
					<td class="label_patrones" style="width: 120px !important;">
						<spring:message code="label.domicilio.fiscal"/>:
					</td>
					<td>
						<label id="lbFisicaReferUno"></label> #<label id="lbFisicaNumExt"></label>, <spring:message code="label.interior" /> <label id="lbFisicaNumInt"></label>, <spring:message code="label.colonia" /> <label id="lbFisicaReferColonia"></label>, <label id="lbFisicaReferDeleg"></label>, <label id="lbFisicaReferEntidad"></label>, <spring:message code="label.codigo.postal.abreviado" /> <label id="lbFisicaReferCP"></label>
					</td>
				</tr>				
			</table>
		</div>
		
		<table style="width: 800px !important; border: none !important;">
			<tr>
				<td style="border: none !important;">
					<div id="divEncabezadoDetalleDatosContactoSocioFisico">
					<strong>Datos de Contacto</strong>
					</div>
				</td>
			</tr>
		</table>
		
		<div id="divDetalleSocioFisicoDatosContacto" style="width: 80%" align="center"></div>
		
</div>
				

				
<div id="divDetallesocioMoral" class="page_holder dialogo_holder" style="display:none;">
		
		<table id="tablaNsMoralDatosPersona" style="width: 100%;">
			<tr id="filaRFCMoral">
				<td class="label_patrones" style="width: 120px !important;">
					<label>RFC :</label>
				</td>
				<td>
					<label id="lbMoralRFC"></label>
				</td>
				<td class="label_patrones" style="width: 220px !important;">
					<label>Denominaci&oacute;n / Raz&oacute;n Social :</label>
				</td>
				<td>
					<label id="lbMoralDenomRazonSocial"></label>
				</td>
			</tr>
			<tr id="filaTipoSociedadMoral">
				<td class="label_patrones" style="width: 120px !important;">
					<label>Tipo Sociedad:</label>
				</td>
				<td colspan="3">
					<label id="lbMoralTipoSociedad"></label>
				</td>
			</tr>	
		</table>
		
		<div id="divDetalleEscrituraConstitutivaSocioMoral">
			
			<strong>Escritura Constitutiva</strong>
			
			<table style="width: 100%;">
				<tr>
					<td class="label_patrones" style="width: 170px !important;">
						<label> <spring:message code="label.escritura.conts.num.esc" />:</label>
					</td>
					<td class="label_patrones_data" style="width: 150px !important;">
						<label id="lbMoralNumEscritura"></label>
									
					</td>				
					<td class="label_patrones" style="width: 200px !important;">
						<label> <spring:message code="label.escritura.conts.notaria" />:</label>
					</td>
					<td class="label_patrones_data">
						<label id="lbMoralNumNotaria"></label>
					</td>
				</tr>
				<tr>
					<td class="label_patrones">
							<label><spring:message code="label.entidad.federativa"/></label>
					</td>
					<td class="label_patrones_data">
						<label id="lbMoralEntidadFed"></label>
					</td>				
					<td class="label_patrones">
						<label ><spring:message code="label.escritura.conts.municipio"/>:</label>
					</td>
					<td class="label_patrones_data">
						<label id="lbMoralMunicipio"></label>
					</td>
				</tr>
				<tr>	
					<td class="label_patrones">
						<label> <spring:message code="label.escritura.conts.fecha"/>:</label>
					</td>
					<td class="label_patrones_data">
						<label id="lbMoralFecConstitucion"></label>				
					</td>			
				</tr>
			</table>
			
			
			<table style="width: 100%;">
				<tr>
					<td class="label_patrones" style="width: 120px !important;">
						<label>Folio Mercantil :</label>
					</td>
					<td>
						<label id="lbMoralFolioMercantil"></label>
					</td>
				</tr>
			</table>
			<table style="width: 100%;">
				<tr>
					<td class="label_patrones" >
						<label>Secci&oacute;n :</label>
					</td>
					<td>
						<label id="lbMoralSeccion"></label>
					</td>
					<td class="label_patrones" >
						<label>Partida :</label>
					</td>
					<td>
						<label id="lbMoralPartida"></label>
					</td>
					<td class="label_patrones" >
						<label>Volumen :</label>
					</td>
					<td>
						<label id="lbMoralVolumen"></label>
					</td>
					<td class="label_patrones" >
						<label>Foja :</label>
					</td>
					<td>
						<label id="lbMoralFoja"></label>
					</td>
				</tr>
			</table>
		</div>
		<!-- 
		<div id="divDetalleSocioMoralSinDomicilio" style="display: none;">
			<table id="tablaDetalleSocioMoralSinDatosDomicilio" style="width: 100%;">
				<tr>
					<td class="label_patrones" style="width: 140px !important;">
						<spring:message code="label.domicilio.fiscal"/>
					</td>
					<td style="color: red;">
						<span>No se ha encontrado ning&uacute;n domicilio fiscal asociado a esta persona.</span>
					</td>
				</tr>
			</table>
		</div>
		 -->
		
		<div id="divDetalleSocioMoralDomicilio" style="display: none;">
			<table id="tablaDetalleSocioMoralDatosDomicilio" style="width: 100%;">
				<tr>
					<td class="label_patrones" style="width: 120px !important;">
						<spring:message code="label.domicilio.fiscal"/>:
					</td>
					<td>
						<label id="lbMoralReferUno"></label> #<label id="lbMoralNumExt"></label>, <spring:message code="label.interior" /> <label id="lbMoralNumInt"></label>, <spring:message code="label.colonia" /> <label id="lbMoralReferColonia"></label>, <label id="lbMoralReferDeleg"></label>, <label id="lbMoralReferEntidad"></label>, <spring:message code="label.codigo.postal.abreviado" /> <label id="lbMoralReferCP"></label>
					</td>
				</tr>		
			</table>
		</div>
		
		<strong>Datos de Contacto</strong>
		<div id="divDetalleSocioMoralDatosContacto" style="width: 80%" align="center"></div>
		
</div>
				

				
				
				
				
				
				
<div id="divDetallesocioFideicomiso"   style="display:none;">
		
			<strong>Datos del fideicomiso</strong>
			<table id="tablaDetalleFideicomiso" style="width: 100%;">
				<tr>
					<td class="label_patrones" >
						<label>Nombre del fideicomiso :</label>
					</td>
					<td>
						<label id="lbFideicomisoNombre"></label>
					</td>
				</tr>
				<tr>
					<td class="label_patrones" >
						<label>RFC del fideicomiso:</label>
					</td>
					<td>
						<label id="lbFideicomisoRFC"></label>
					</td>
				</tr>
			</table>
		
		<div id="divDetalleContratoSocioFideicomiso">
			
			<strong>Secci&oacute;n contrato</strong>
			
			<table style="width: 100%;">
				<tr>
					<td class="label_patrones" >
						<label>N&uacute;mero de instrumento de protocolizaci&oacute;n :</label>
					</td>
					<td>
						<label id="lbFideicomisoNumInstProt"></label>
					</td>
					<td class="label_patrones" >
						<label>Notar&iacute;a / Corredur&iacute;a :</label>
					</td>
					<td>
						<label id="lbFideicomisoNotariaCorreduria"></label>
					</td>
				</tr>
				<tr>
					<td class="label_patrones" >
						<label>Estado :</label>
					</td>
					<td>
						<label id="lbFideicomisoEstado"></label>
					</td>
					<td class="label_patrones" >
						<label>Fecha de expedici&oacute;n del contrato :</label>
					</td>
					<td>
						<label id="lbFideicomisoFechaExpedicion"></label>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="divDetalleSocioFideicomisoSinDomicilio" style="display: none;">
			<table id="tablaDetalleSocioFideicomisoSinDatosDomicilio" style="width: 100%;">
				<tr>
					<td class="label_patrones" style="width: 140px !important;">
						<spring:message code="label.domicilio.fiscal"/>
					</td>
					<td style="color: red;">
						<span>No se ha encontrado ning&uacute;n domicilio fiscal asociado a esta persona.</span>
					</td>
				</tr>
			</table>
		</div>
		
		
		<div id="divDetalleSocioFideicomisoDomicilio" style="display: none;">
			<table id="tablaDetalleSocioFideicomisoDatosDomicilio" style="width: 100%;">
				<tr>
					<td class="label_patrones" style="width: 120px !important;">
						<spring:message code="label.domicilio.fiscal"/>:
					</td>
					<td>
						<label id="lbFideicomisoReferUno"></label> #<label id="lbFideicomisoNumExt"></label>, <spring:message code="label.interior" /> <label id="lbFideicomisoNumInt"></label>, <spring:message code="label.colonia" /> <label id="lbFideicomisoReferColonia"></label>, <label id="lbFideicomisoReferDeleg"></label>, <label id="lbFideicomisoReferEntidad"></label>, <spring:message code="label.codigo.postal.abreviado" /> <label id="lbFideicomisoReferCP"></label>
					</td>
				</tr>				
			</table>
		</div>
		
		<strong>Datos de Contacto</strong>
		<div id="divDetalleSocioFideicomisoDatosContacto" style="width: 80%" align="center"></div>
		
</div>