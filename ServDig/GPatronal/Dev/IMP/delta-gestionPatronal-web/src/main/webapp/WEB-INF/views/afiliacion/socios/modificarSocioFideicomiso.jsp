<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<div id="idDialogModificaSocioFideicomiso" style="float: left; width: 100% !important; padding-top: 10px;" class="page_holder dialogo_holder">
	<fieldset>
	<form:form modelAttribute="socio" id="formModificaSocioFideicomiso" action="">
		<form:hidden path="idPersona" id="msFideicomisoIdPersona"/>
		<form:hidden path="tipoSocio.idTipoPersona"/>
		<form:hidden path="idSocio"/>
		<form:hidden path="idPersonaMoralPatron"/>
		<form:hidden path="esNacional" id="msFideicomisoNacional"/>
		<form:hidden path="esDomicilioNacional" id="msFideicomisoDomicilioNacional"/>
		<form:hidden path="esPersonaFisica"/>
		
			<legend>
				<strong>Modificar socio fideicomiso</strong>
			</legend>
			<table id="tablaMsFideicomisoDatosGenerales" style="width: 90%;">
				<tr>
					<td style="width: 30%;">
						<label>Nombre del fideicomiso :
							
						</label>
					</td>
					<td style="width: 70%;">
						<form:input readonly="true" style="border: 0px; width: 80%;" path="denominacionRazonSocial" id="msFideicomisoNombre"/>
					</td>
				</tr>
				<tr>
					<td style="width: 30%;">
						<label>RFC 	del fideicomiso :
							
						</label>
					</td>
					<td style="width: 70%;">
						<form:input readonly="true" style="border: 0px; width: 80%;" path="rfc" id="msFideicomisoRFC"/>
					</td>
				</tr>
			</table>									
			
			<div id="divMSFideicomisoContrato">
			<fieldset>
				<legend>
					<strong>Contrato</strong>
				</legend>
				<table id="tablaNsFisicaDatosPersona" style="width: 100%;">
					<tr>
						<td>
							<label>N&uacute;mero de instrumento de protocolizaci&oacute;n :
							</label>
						</td>
						<td>
							<form:input path="numeroInstrumetoProtocolizacion" id="msFidecomisoProtoolo" onkeydown="validarNumeros(event)"/>
						</td>
					</tr>
					<tr>
						<td>
							<label>Notar&iacute;a o corredur&iacute;a:</label>
						</td>
						<td>
							<form:input path="notariaCorreduria" id="msFideicomisoNotaria"/>
						</td>
					</tr>
					<tr>
						<td>
							<label>Estado:</label>
						</td>
						<td>
							<combo:creaCombo idHtml="estado.clave" idHtmlContenedor="formModificaSocioFideicomiso"
						entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado" idHtmlValor="estado.clave" 
						mostrarSoloActivos = "true"/>
						<!-- falta definir la carga del id del estado desde el controller-->
						</td>
					</tr>
					<tr>
						<td>
							<label>Fecha de expedici&oacute;n del contrato :</label>
						</td>												
						<td>
							<form:input path="" id="msFechaExpedicionContrato"/>
						</td>
					</tr>
				</table>
			</fieldset>
			<fieldset id="fieldModificarDomicilioFideicomio">
				<legend>
					<strong>Domicilio fiscal</strong>
				</legend>
				
				<table id="tablaMsMoralDatosDomicilio" style="width: 100%;">
					<tr>
						<td style="width: 30%;">
							<label>Calle :
								
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.calle" id="msFideicomisoCalle"/>
						</td>
					</tr>
					<tr>
						<td style="width: 30%;">
							<label>N&uacute;mero y/o letra exterior :
								
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.numExteriorAlf" id="msFideicomisoNumExt"/>
						</td>
					</tr>
					<tr>
						<td style="width: 30%;">
							<label>N&uacute;mero y/o letra interior:
								
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.numInteriorAlf" id="msFideicomisoNumInt"/>
						</td>
					</tr>
					<tr>
						<td style="width: 30%;">
							<label>Referencia primaria :
								
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.vialidadReferenciaPrimaria.nombre" id="msFideicomisoReferUno"/>
						</td>
					</tr>
					<tr>
						<td style="width: 30%;">
							<label>Referencia secundaria :
								
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.vialidadReferenciaSecundaria.nombre" id="msFideicomisoReferDos"/>
						</td>
					</tr>
					<tr>
						<td style="width: 30%;">
							<label>Referencia posterior :
								
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.vialidadReferenciaPosterior.nombre" id="msFideicomisoReferPost"/>
						</td> 
					</tr>
					<tr>
						<td style="width: 30%;">
							<label>Colonia o poblaci&oacute;n:
								
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.asentamiento.nombre" id="msFideicomisoReferColonia"/>
						</td>
					</tr>
					<tr>
						<td style="width: 30%;">
							<label>Localidad :
								
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.asentamiento.localidad.nombre" id="msFideicomisoReferLocalidad"/>
						</td>
					</tr>
					<tr>
						<td style="width: 30%;">
							<label>Delegaci&oacute;n o municipio :
								
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.asentamiento.localidad.municipio.nombre" id="msFideicomisoReferDeleg"/>
						</td>
					</tr>
					<tr>
						<td style="width: 30%;">
							<label>Entidad federativa :
								
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre" id="msFideicomisoReferEntidad"/>
						</td>
					</tr>
					<tr>
						<td style="width: 30%;">
							<label>C&oacute;digo postal :
								
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.codigoPostal" id="msFideicomisoReferCP"/>
						</td>
					</tr>					
				</table>			
			</fieldset>
			</div>
			
			</form:form>
			
		<table id="dtModificaContactosSocioFideicomiso" style="width: 1000px">
			<thead>
			</thead>
			<tbody>
			</tbody>
		</table>
	</fieldset>
</div>