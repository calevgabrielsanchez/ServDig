<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div id="idDialogModificaSocioMoral" style="float: left; width: 100% !important; padding-top: 10px;" class="page_holder dialogo_holder">
	<fieldset>
	<form:form modelAttribute="socio" id="formModificaSocioMoral" action="">
		<form:hidden path="idPersona" id="msMoralIdPersona"/>
		<form:hidden path="tipoSocio.idTipoPersona"/>
		<form:hidden path="idSocio"/>
		<form:hidden path="idPersonaMoralPatron"/>
		<form:hidden path="esNacional" id="msMoralNacional"/>
		<form:hidden path="esDomicilioNacional" id="msMoralDomicilioNacional"/>
		<form:hidden path="esPersonaFisica"/>
		
			<legend>
				<strong>Modificar socio  p. moral</strong>
			</legend>
			
			<fieldset>
				<legend>
					<strong>Datos de persona</strong>
				</legend>
				<table id="tablaMsMoralDatosPersona" style="width: 100%;">
					<tr id="filaMSRFCMoral">
						<td style="width: 30%;">
							<label>RFC :
								
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="rfc" id="msMoralRFC"/>
						</td>
					</tr>
					<tr>
						<td style="width: 30%;">
							<label>Denominaci&oacute;n / Raz&oacute;n Social:
								
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="nombreRazonSocial" id="msMoralDenominacionRazonSocial"/>
						</td>
					</tr>
					<tr id="filaTipoSociedad">
						<td style="width: 30%;">
							<label>Tipo de Sociedad :
								
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="tipoSociedad" id="msTipoSociedad"/>
						</td>
					</tr>
				</table>
			</fieldset>
			
			<div id="divMediosContactoModSocioMoral">
			
			</div>
			
			<div id="divMSMoralEscrituraConstitutiva">
				<fieldset>
					<legend>
						<strong>Escritura Constitutiva</strong>
					</legend>
					<table id="tablaMsMoralEscrituraConstitutiva" style="width: 100%;">
						<tr>
							<td>
								<label>Folio Mercantil :</label>
							</td>
							<td>
								<form:input path="escrituraConstitutiva.folioMercantil" disabled="false" id="msMoralFolioMercantil" onkeydown="validarNumeros(event)" onkeyup="capturaFolioMercantilMod()"/>
							</td>
						</tr>
						<tr>
							<td>
								<label>Secci&oacute;n :</label>
							</td>
							<td>
								<form:input path="escrituraConstitutiva.seccion" disabled="false" id="msMoralSeccion" onkeydown="validarNumeros(event)" onkeyup="deshabilitarFolioMercantilMod()"/>
							</td>
						</tr>
						<tr>
							<td>
								<label>Partida :</label>
							</td>
							<td>
								<form:input path="escrituraConstitutiva.partida" disabled="false" id="msMoralPartida" onkeydown="validarNumeros(event)" onkeyup="deshabilitarFolioMercantilMod()"/>
							</td> 
						</tr>
						<tr>
							<td>
								<label>Volumen :</label>
							</td>
							<td>
								<form:input path="escrituraConstitutiva.volumen" disabled="false" id="msMoralVolumen" onkeydown="validarNumeros(event)" onkeyup="deshabilitarFolioMercantilMod()"/>
							</td>
						</tr>
						<tr>
							<td>
								<label>Foja :</label>
							</td>
							<td>
								<form:input path="escrituraConstitutiva.foja" disabled="false" id="msMoralFoja" onkeydown="validarNumeros(event)" onkeyup="deshabilitarFolioMercantilMod()"/>
							</td>
						</tr>
					</table>
				</fieldset>
			</div>
			
			<div id="divMSMoralDomicilio">
				<table id="tablaMSMoralDatosDomicilio" style="width: 100%;">
					<tr>
						<td style="width: 30%;">
							<label>Calle :
								
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.calle" id="msMoralCalle"/>
						</td>
					</tr>
					<tr>
						<td style="width: 30%;">
							<label>N&uacute;mero y/o letra exterior :
								
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.numExteriorAlf" id="msMoralNumExt"/>
						</td>
					</tr>
					<tr>
						<td style="width: 30%;">
							<label>N&uacute;mero y/o letra interior:
																
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.numInteriorAlf" id="msMoralNumInt"/>
						</td>
					</tr>
					<tr>
						<td style="width: 30%;">
							<label>Referencia primaria :
								
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.vialidadReferenciaPrimaria.nombre" id="msMoralReferUno"/>
						</td>
					</tr>
					<tr>
						<td style="width: 30%;">
							<label>Referencia secundaria :
								
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.vialidadReferenciaSecundaria.nombre" id="msMoralReferDos"/>
						</td>
					</tr>
					<tr>
						<td style="width: 30%;">
							<label>Referencia posterior :
								
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.vialidadReferenciaPosterior.nombre" id="msMoralReferPost"/>
						</td>
					</tr>
					<tr>
						<td style="width: 30%;">
							<label>Colonia o poblaci&oacute;n:
								
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.asentamiento.nombre" id="msMoralColonia"/>
						</td>
					</tr>
					<tr>
						<td style="width: 30%;">
							<label>Localidad :
								
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.asentamiento.localidad.nombre" id="msMoralLocalidad"/>
						</td>
					</tr>
					<tr>
						<td style="width: 30%;">
							<label>Delegaci&oacute;n o municipio :
								
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.asentamiento.localidad.municipio.nombre" id="msMoralDeleg"/>
						</td>
					</tr>
					<tr>
						<td style="width: 30%;">
							<label>Entidad federativa :
								
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre" id="msMoralEntidad"/>
						</td>
					</tr>
					<tr>
						<td style="width: 30%;">
							<label>C&oacute;digo postal :
								
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.codigoPostal.codigoPostal" id="msMoralCP"/>
						</td>
					</tr>					
				</table>
			</div>
			</form:form>
			
				<table id="dtModificaContactosSocioMoral" style="width: 1000px">
					<thead>
					</thead>
					<tbody>
					</tbody>
				</table>
		</fieldset>

		<!--<div style="float: left; width: 100%;" align="right">
			<input type="button" class="mboton" id="btnGuardarModificaSocioMoral" value="Guardar" style="font-size: .8em !important;">
			<input type="button" class="mboton" id="btnCancelarModificaSocioMoral" value="Cancelar" style="font-size: .8em !important;">
		</div>-->
	
</div>