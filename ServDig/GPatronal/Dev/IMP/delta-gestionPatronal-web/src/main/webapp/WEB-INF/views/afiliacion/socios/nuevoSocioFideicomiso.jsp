<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<div id="idDialogNuevoSocioFideicomiso" style="float: left; width: 100% !important; padding-top: 10px;" class="page_holder dialogo_holder">
	<form:form modelAttribute="socio" id="formNuevoSocioFideicomiso" action="">	
		
		<form:hidden path="idPersona" id="idPersonaFideicomiso"/>
		<form:hidden path="tipoSocio.idTipoPersona" id="idTipoPersonaSocioFideicomiso"/>
		<form:hidden path="esNacional" id="nsFideicomisoNacional"/>
		<form:hidden path="esDomicilioNacional" id="nsFideicomisoDomicilioNacional"/>
		
		
			<legend>
				<strong>Agregar socio p. fideicomiso</strong>
			</legend>
			<table id="tablaNsFideicomisoDatosGenerales" style="width: 100%; border: none !important;">
				<tr>
					<td class="label_patrones"  style="width: 30%;">
						<label>Nombre del fideicomiso :
							<span class="indicador_campo_requerido">*</span>
						</label>
					</td>
					<td class="label_patrones_data" style="width: 70%;">
						<form:input path="" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsFideicomisoNombre"/>
					</td>
				</tr>
				<tr>
					<td class="label_patrones"  style="width: 30%;">
						<label>RFC 	del fideicomiso :
							<span class="indicador_campo_requerido">*</span>
						</label>
					</td>
					<td class="label_patrones_data" style="width: 70%;">
						<form:input path="" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsFideicomisoRFC"/>
					</td>
				</tr>				
				<tr id="filaBtnCargaDatosFideicomiso">
					<td colspan="2" align="center" style="border: none !important;">
						<div style="float: left; width: 100%;" id="divNSFideicomisoCargarDatos">
							<input type="button" class="mboton" value="Cargar datos de Persona" style="font-size: .8em !important;" onclick="buscarPersonaFideicomisoNS()">
						</div>
					</td>
				</tr>
			</table>									
			
			<div id="divNSFideicomisoContrato">
				<legend>
					<strong>Contrato</strong>
				</legend>
				<table id="tablaNsFisicaDatosPersona" style="width: 100%; border: none !important;">
					<tr>
						<td class="label_patrones" >
							<label>N&uacute;mero de instrumento de protocolizaci&oacute;n :</label>
						</td>
						<td class="label_patrones_data">
							<form:input path="" id="nsFidecomisoProtoolo" onkeydown="validarNumeros(event)"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones" >
							<label>Notar&iacute;a o corredur&iacute;a:</label>
						</td>
						<td class="label_patrones_data">
							<form:input path="" id="nsFideicomisoNotaria"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones" >
							<label>Estado:</label>
						</td>
						<td class="label_patrones_data">
							<combo:creaCombo idHtml="estado.clave" idHtmlContenedor="formNuevoSocioFideicomiso"
						entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado" idHtmlValor="estado.clave" 
						mostrarSoloActivos = "true"/>
						<!-- falta definir la carga del id del estado desde el controller-->
						</td>
					</tr>
					<tr>
						<td class="label_patrones" >
							<label>Fecha de expedici&oacute;n del contrato :</label>
						</td>												
						<td class="label_patrones_data">
							<form:input path="" id="nsFideicmnioPartia"/>
						</td>
					</tr>
				</table>
				<legend>
					<strong>Domicilio fiscal</strong>
				</legend>
				
				<table id="tablaNsMoralDatosDomicilio" style="width: 100%; border: none !important;">
					<tr>
						<td class="label_patrones" style="width: 30%;">
							<label>Calle :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="domicilioFiscal.calle" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsFideicomisoCalle"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones" style="width: 30%;">
							<label>N&uacute;mero y/o letra exterior :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="domicilioFiscal.numExteriorAlf" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsFideicomisolNumExt"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones" style="width: 30%;">
							<label>N&uacute;mero y/o letra interior:
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="domicilioFiscal.numInteriorAlf" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsFideicomisoNumInt"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones" style="width: 30%;">
							<label>Referencia primaria :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="domicilioFiscal.vialidadReferenciaPrimaria.nombre" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsFideicomisoReferUno"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones" style="width: 30%;">
							<label>Referencia secundaria :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="domicilioFiscal.vialidadReferenciaSecundaria.nombre" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsFideicomisoReferDos"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones" style="width: 30%;">
							<label>Referencia posterior :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="domicilioFiscal.vialidadReferenciaPosterior.nombre" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsFideicomisoReferPost"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones" style="width: 30%;">
							<label>Colonia o poblaci&oacute;n:
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="domicilioFiscal.asentamiento.nombre" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsFideicomisoReferColonia"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones" style="width: 30%;">
							<label>Localidad :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="domicilioFiscal.asentamiento.localidad.nombre" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsFideicomisoReferLocalidad"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones" style="width: 30%;">
							<label>Delegaci&oacute;n o municipio :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="domicilioFiscal.asentamiento.localidad.municipio.nombre" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsFideicomisoReferDeleg"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones" style="width: 30%;">
							<label>Entidad federativa :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsFideicomisoReferEntidad"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones" style="width: 30%;">
							<label>C&oacute;digo postal :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="domicilioFiscal.codigoPostal" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsFideicomisoReferCP"/>
						</td>
					</tr>					
				</table>			
			</div>
			
			</form:form>
			
			<table id="divMediosContactoNSPersonaFideicomiso" style="width: 1000px">
				<thead>
				</thead>
				<tbody>
				</tbody>
			</table>
		
		<form:form>
			<table style="width: 100%; border: none !important;">
				<tr>
					<td align="center" style="border: none !important;">
						<input type="button" class="mboton" id="btnGuardarNuevoSocioFideiomiso" value="Guardar" style="font-size: .8em !important;" onclick="agregarSocioFideicomisoSesion()">
						<input type="button" class="mboton" id="btnCancelarNuevoSocioFideicomiso" value="Cancelar" style="font-size: .8em !important;" onclick="canclelarAgregarSocioFideicomisoSesion(); validaSocioExtranjero();">
					</td>
				</tr>
			</table>
		</form:form>
	
</div>
<div id="personaFideicomisoNS">
</div>