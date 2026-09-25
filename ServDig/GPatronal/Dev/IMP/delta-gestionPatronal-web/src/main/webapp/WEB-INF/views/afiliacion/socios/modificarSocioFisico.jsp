<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div id="idDialogModificaSocioFisico" style="float: left; width: 90% !important; padding-top: 10px;" class="page_holder dialogo_holder">
	<form:form modelAttribute="socio" id="formModificaSocioFisico" action="">
		
		<form:hidden path="idPersona" id="msFisicoIdPersona"/>
		<form:hidden path="tipoSocio.idTipoPersona"/>
		<form:hidden path="idSocio"/>
		<form:hidden path="idPersonaMoralPatron"/>
		<form:hidden path="esNacional" id="msFisicoNacional"/>
		<form:hidden path="esDomicilioNacional" id="msFisicoDomicilioNacional"/>

			<table id="tablaNsFisicaDatosPersona" style="width: 100%;">
				<tr id="filaMSFisicoRFC">
					<td class="label_patrones" style="width: 140px !important;">
						<label>RFC :
							
						</label>
					</td>
					<td>
						<form:input path="rfc" readonly="true" style="border: 0px; width: 80%;" id="msFisicoRFC"/>
					</td>
					<div id="filaMSFisicoCURP">
						<td class="label_patrones">
							<label>CURP :
								
							</label>
						</td>
						<td>
							<form:input path="curp" readonly="true" style="border: 0px; width: 95%;" id="msFisicoCURP"/>
						</td>
					</div>
				</tr>
				<tr>
					<td class="label_patrones">
						<label>Primer apellido :
							
						</label>
					</td>
					<td>
						<form:input path="primerApellido" readonly="true" style="border: 0px; width: 80%;" id="msFisicoPrimerAp"/>
					</td>
					<td class="label_patrones">
						<label>Segundo apellido :
							
						</label>
					</td>
					<td>
						<form:input path="segundoApellido" readonly="true" style="border: 0px; width: 80%;" id="msFisicoSegundoAp"/>
					</td>
				</tr>
				<tr>
					<td class="label_patrones">
						<label>Nombre(s) :
							
						</label>
					</td>
					<td colspan="3">
						<form:input path="nombres" readonly="true" style="border: 0px; width: 80%;" id="msFisicoNombre"/>
					</td>
				</tr>					
			</table>
			<div id="divMSFisicoDomicilio">
				<table style="width:  80% !important;">
					<tr>
						<td class="label_patrones" style="width: 100px !important;">
							<spring:message code="label.domicilio.fiscal"/>
						</td>
						<td>
							<span>No se ha encontrado ning&uacute;n domicilio fiscal asociado a esta persona.</span>
						</td>
					</tr>
				</table>
				<!-- 
				<table id="tablaMSFisicoDatosDomicilio" style="width: 100%;">
					<tr>
						<td style="width: 30%;">
							<label>Calle :
								
							</label>
						</td	>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.calle" id="msFisicoCalle"/>
						</td>
					</tr>
					<tr>
						<td style="width: 30%;">
							<label>N&uacute;mero y/o letra exterior :
								
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.numExteriorAlf" id="msFiscicoNumExt"/>
						</td>
					</tr>
					<tr>
						<td style="width: 30%;">
							<label>N&uacute;mero y/o letra interior:</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.numInteriorAlf" id="msFiscioNumInt"/>
						</td>
					</tr>
					<tr>
						<td style="width: 30%;">
							<label>Referencia primaria :
								
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.vialidadReferenciaPrimaria.nombre" id="msFiscicoReferUno"/>
						</td>
					</tr>
					<tr>
						<td style="width: 30%;">
							<label>Referencia secundaria :
								
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.vialidadReferenciaSecundaria.nombre" id="msFiscicoReferDos"/>
						</td>
					</tr>
					<tr>
						<td style="width: 30%;">
							<label>Referencia posterior :
								
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.vialidadReferenciaPosterior.nombre" id="msFiscicoReferPost"/>
						</td>
					</tr>
					<tr>
						<td style="width: 30%;">
							<label>Colonia o poblaci&oacute;n:
								
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.asentamiento.nombre" id="msFiscicoColonia"/>
						</td>
					</tr>
					<tr>
						<td style="width: 30%;">
							<label>Localidad :
								
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.asentamiento.localidad.nombre" id="msFiscicoLocalidad"/>
						</td>
					</tr>
					<tr>
						<td style="width: 30%;">
							<label>Delegaci&oacute;n o municipio :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.asentamiento.localidad.municipio.nombre" id="msFiscicoDeleg"/>
						</td>
					</tr>
					<tr>
						<td style="width: 30%;">
							<label>Entidad federativa :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre" id="msFiscicoEntidad"/>
						</td>
					</tr>
					<tr>
						<td style="width: 30%;">
							<label>C&oacute;digo postal :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td style="width: 70%;">
							<form:input readonly="true" style="border: 0px; width: 80%;" path="domicilioFiscal.codigoPostal" id="msFiscicoCP"/>
						</td>
					</tr>					
				</table>
				 -->
			</div>
		</form:form>
			<table id="dtModificaContactosSocioFisico" style="width: 80%; border: none !important;">
				<thead>
				</thead>
				<tbody>
				</tbody>
			</table>
		
		<!-- div style="float: left; width: 100%;" align="right">
			<input type="button" class="mboton" id="btnGuardarModificaSocio" value="Guardar" style="font-size: .8em !important;" onclick="">
			<input type="button" class="mboton" id="btnCancelarModificaSocio" value="Cancelar" style="font-size: .8em !important;" onclick="">
		</div-->
	
</div>