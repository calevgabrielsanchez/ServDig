<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div id="idDialogNuevoSocioFiscal" style="float: left; width: 100% !important; padding-top: 10px;" class="page_holder dialogo_holder">
	<form:form modelAttribute="socio" id="formNuevoSocioPersonaFisica" action="">
		<form:hidden path="idPersona" id="nsFisicaIdPersona"/>
		<form:hidden path="tipoSocio.idTipoPersona" id="idTipoPersonaSocioPersonaFisica"/>
		<form:hidden path="esNacional" id="nsFisicaNacional"/>
		<form:hidden path="esDomicilioNacional" id="nsFisicaDomicilioNacional"/>
		
			<legend>
				<strong>Agregar socio  p. f&iacute;sica nacional</strong>
			</legend>
			<table id="tablaNsFisicaDatosPersona" style="width: 100%; border: none !important;">
				<tr id="filaNSRFC">
					
						<td class="label_patrones" style="width: 30%;">
							<div id="a1">
							<label>RFC :
								<span class="indicador_campo_requerido">*</span>
							</label>
							</div>
						</td>
					
					
						<td class="label_patrones_data" style="width: 70%;">
							<div id="a2">
							<form:input path="rfc" disabled="true" style="border: 0px; width: 80%; border-right: none !important;" id="nsFisicaRFC"/>
							</div>
						</td>
					
				</tr>
				<tr id="filaNSCURP">
					<td class="label_patrones" style="width: 30%;">
						<div id="b1"> 
							<label>CURP :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</div>
					</td>
					<td class="label_patrones_data" style="width: 70%;">
						<div id="b2">
							<form:input path="curp" disabled="true" style="border: 0px; width: 80%; border-right: none !important;" id="nsFisicaCURP"/>
						</div>
					</td>
				</tr>
				<tr>
					<td class="label_patrones" style="width: 30%;">
						<label>Primer apellido :
							<span class="indicador_campo_requerido">*</span>
						</label>
					</td>
					<td class="label_patrones_data" style="width: 70%;">
						<form:input path="primerApellido" readonly="true" style="border: none !important; width: 80%; border-right: none !important;" id="nsFisicaPrimerAp"/>
					</td>
				</tr>
				<tr>
					<td class="label_patrones" style="width: 30%;">
						<label>Segundo apellido :
							<span class="indicador_campo_requerido">*</span>
						</label>
					</td>
					<td class="label_patrones_data" style="width: 70%;">
						<form:input path="segundoApellido" readonly="true" style="border: none !important; width: 80%; border-right: none !important;" id="nsFisicaSegundoAp"/>
					</td>
				</tr>
				<tr>
					<td class="label_patrones" style="width: 30%;">
						<label>Nombres(s) :
							<span class="indicador_campo_requerido">*</span>
						</label>
					</td>
					<td class="label_patrones_data" style="width: 70%; border: none !important; border-right: none !important;">
						<form:input path="nombres" readonly="true" style="border: none !important; width: 80%;" id="nsFisicaNombre"/>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center" style="border: none !important;">
						<div style="float: left; width: 100%; border: none !important;" id="divNSCargarDatos">
							<input type="button" class="mboton" value="Cargar datos de Persona" style="font-size: .8em !important;" onclick="buscarPersonaFisicaNS()">
						</div>
					</td>
				</tr>
			</table>
			
			
			<div id="divNSFisicaDomicilio">
				<table id="tablaNsFisicaDatosDomicilio" style="width: 100%; border: none !important;">
					
					<tr>
						<td class="label_patrones" style="width: 150px !important;">
							<spring:message code="label.domicilio.fiscal"/>:
						</td>
						<td class="label_patrones_data">
						
							<form:label path="domicilioFiscal.vialidadPrimaria.nombre" id="nsFisicaCalle" style="border: 0px; width: 80%;" readonly="true"/> 
							#<form:label path="domicilioFiscal.numExterior1" id="nsFisicaNumExt" style="border: 0px; width: 80%;" readonly="true"/>
							<form:label path="domicilioFiscal.numExteriorAlf" id="nsFisicaNumExtAlf" style="border: 0px; width: 80%;" readonly="true"/>, 
							<spring:message code="label.interior" /> <form:label path="domicilioFiscal.numInterior" id="nsFisicaNumInt" style="border: 0px; width: 80%;" readonly="true"/>
							<form:label path="domicilioFiscal.numInteriorAlf" id="nsFisicaNumIntAlf"style="border: 0px; width: 80%;" readonly="true"/>, 
							<spring:message code="label.colonia" /> <form:label path="domicilioFiscal.asentamiento.nombre" id="nsFisicaColonia" style="border: 0px; width: 80%;" readonly="true"/>, 
							<form:label path="domicilioFiscal.asentamiento.localidad.municipio.nombre" id="nsFisicaDeleg" style="border: 0px; width: 80%;" readonly="true"/>, 
							<form:label path="domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre" id="nsFisicaEntidad" style="border: 0px; width: 80%;" readonly="true"/>, 
							<spring:message code="label.codigo.postal.abreviado" /> <form:label path="domicilioFiscal.codigoPostal.codigoPostal" id="nsFisicaReferCP" style="border: 0px; width: 80%;" readonly="true"/>.
						</td>
					</tr>
					<!-- 
					<tr>
						<td class="label_patrones" style="width: 30%;">
							<label>Calle :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="domicilioFiscal.calle" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsFisicaCalle"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones"  style="width: 30%;">
							<label>N&uacute;mero y/o letra exterior :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="domicilioFiscal.numExteriorAlf" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsFiscicaNumExt"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones" style="width: 30%;">
							<label>N&uacute;mero y/o letra interior:</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="domicilioFiscal.numInteriorAlf" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsFiscicaNumInt"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones" style="width: 30%;">
							<label>Referencia primaria :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="domicilioFiscal.vialidadReferenciaPrimaria.nombre" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsFiscicaReferUno"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones" style="width: 30%;">
							<label>Referencia secundaria :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="domicilioFiscal.vialidadReferenciaSecundaria.nombre" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsFiscicaReferDos"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones" style="width: 30%;">
							<label>Referencia posterior :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="domicilioFiscal.vialidadReferenciaPosterior.nombre" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsFiscicaReferPost"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones" style="width: 30%;">
							<label>Colonia o poblaci&oacute;n:
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="domicilioFiscal.asentamiento.nombre" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsFiscicaColonia"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones" style="width: 30%;">
							<label>Localidad :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="domicilioFiscal.asentamiento.localidad.nombre" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsFiscicaLocalidad"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones" style="width: 30%;">
							<label>Delegaci&oacute;n o municipio :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="domicilioFiscal.asentamiento.localidad.municipio.nombre" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsFiscicaDeleg"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones" style="width: 30%;">
							<label>Entidad federativa :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsFiscicaEntidad"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones" style="width: 30%;">
							<label>C&oacute;digo postal :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="domicilioFiscal.codigoPostal" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsFiscicaCP"/>
						</td>
					</tr>
					 -->					
				</table>
			</div>
			
			</form:form>
			
			<div id="divMediosContactoNSPersonaFisica"></div>
					
			
		
		<form:form>
			<table style="width: 100%; border: none !important;">
				<tr>
					<td align="center" style="border: none !important;">
						<input type="button" class="mboton" id="btnGuardarNuevoSocio" value="Guardar" style="font-size: .8em !important;" onclick="agregarSocioSesion()">
						<input type="button" class="mboton" id="btnCancelarNuevoSocio" value="Cancelar" style="font-size: .8em !important;" onclick="canclelarAgregarSocioSesion(); validaSocioExtranjero();">
					</td>
				</tr>
			</table>
		</form:form>
		
</div>	
<div id="personaFisicaNS">
</div>