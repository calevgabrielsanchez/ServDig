<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div id="idDialogNuevoSocioMoral" style="float: left; width: 100% !important; padding-top: 10px;" class="page_holder dialogo_holder">
	<form:form modelAttribute="socio" id="formNuevoSocioPersonaMoral" action="">	
		
		<form:hidden path="idPersona" id="nsMoralIdPersona"/>
		<form:hidden path="tipoSocio.idTipoPersona" id="idTipoPersonaSocioPersonaMoral"/>
		<form:hidden path="esNacional" id="nsMoralNacional"/>
		<form:hidden path="esDomicilioNacional" id="nsMoralDomicilioNacional"/>
						
			<legend>
				<strong>Agregar socio p. moral nacional</strong>
			</legend>
			<table id="tablaNsFisicaDatosPersona" style="width: 100%; border: none !important;">
				<tr id="filaRFCMoral">
					<td class="label_patrones" style="width: 30%;">
						<div id="moralA1">
							<label>RFC :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</div>
					</td>
					<td class="label_patrones_data" style="width: 70%;">
						<div id="moralA2">
							<form:input path="" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsMoralRFC"/>
						</div>
					</td>
				</tr>
				<tr>
					<td class="label_patrones" style="width: 30%;">
						<label>Denominaci&oacute;n o Raz&oacute;n Social :
							<span class="indicador_campo_requerido">*</span>
						</label>
					</td>
					<td class="label_patrones_data" style="width: 70%;">
						<form:input path="" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsMoralRazonSocial"/>
					</td>
				</tr>
				<tr id="filaTipoSociedad">
					<td class="label_patrones"  style="width: 30%;">
						<div id="moralB1">
							<label>Tipo de sociedad :
								<span class="indicador_campo_requerido">*</span>
							</label>
						<div>
					</td>
					<td class="label_patrones_data" style="width: 70%;">
						<div id="moralB2">
							<form:input path="" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsMoralTipoSociedad"/>
						</div>
					</td>
				</tr>	
				<!-- 
				<tr id="filaBtnCargaDatos">
					<td colspan="2" align="center" style="border: none !important;">
						<div style="float: left; width: 100%;" id="divNSMoralCargarDatos">
							<input type="button" class="mboton" value="Cargar datos de Persona" style="font-size: .8em !important;" onclick="buscarPersonaMoralNS()">
						</div>
					</td>
				</tr>
				 -->
			</table>									
			
			<div id="divNSMoralDomicilio">
				<legend>
					<strong>Escritura constitutiva</strong>
				</legend>
				
				<table style="width: 100%;">
						<tr>
							<td class="label_patrones" style="width: 140px !important;">
								<label> <spring:message code="label.escritura.conts.num.esc" />:</label>
							</td>
							<td class="label_patrones_data" style="width: 280px !important;">
								<form:input path="escrituraConstitutiva.numEscritura"  id="nsMoralNumEscritura"/>
											
							</td>				
							<td class="label_patrones" style="width: 200px !important;">
								<label> <spring:message code="label.escritura.conts.notaria" />:</label>
							</td>
							<td class="label_patrones_data">
								<form:input path="escrituraConstitutiva.numNotaria"  id="nsMoralNumNotaria"/>
							</td>
						</tr>
						<tr>
							<td class="label_patrones">
									<label><spring:message code="label.entidad.federativa"/></label>
							</td>
							<td class="label_patrones_data">
								<form:input path="escrituraConstitutiva.lugarExpedicion.entidadFederativa.nombre"  id="nsMoralEntidadFed"/>
							</td>				
							<td class="label_patrones">
								<label ><spring:message code="label.escritura.conts.municipio"/>:</label>
							</td>
							<td class="label_patrones_data">
								<form:input path="escrituraConstitutiva.lugarExpedicion.nombre"  id="nsMoralMunicipio"/>
							</td>
						</tr>
						<tr>	
							<td style="border-width:0px 0 0 0px;" class="label_patrones">
								<label> <spring:message code="label.escritura.conts.fecha"/>:</label>
							</td>
							<td class="label_patrones_data">
								<form:input path="escrituraConstitutiva.fechaExpedicion"  id="nsMoralFecConstitucion"/>				
							</td>			
						</tr>
					</table>
			
				
				<table id="tablaNsFisicaDatosPersona" style="width: 100%; border: none !important;">
					<tr>
						<td class="label_patrones">
							<label>Folio mercantil :</label>
						</td>
						<td class="label_patrones_data">
							<form:input path="escrituraConstitutiva.folioMercantil" id="nsMoralIdActaConstitutiva" style="border: 0px; width: 80%;"  onkeydown="validarNumeros(event)" onkeyup="capturaFolioMercantil()"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones">
							<label>Secci&oacute;n :</label>
						</td>
						<td class="label_patrones_data">
							<form:input path="escrituraConstitutiva.seccion" id="nsMoralSeccion" onkeydown="validarNumeros(event)" style="border: 0px; width: 80%;"  onkeyup="deshabilitarFolioMercantil()"/>
						</td>
						<td class="label_patrones">
							<label>Partida :</label>
						</td>
						<td class="label_patrones_data">
							<form:input path="escrituraConstitutiva.partida" id="nsMoralPartia" onkeydown="validarNumeros(event)" style="border: 0px; width: 80%;"  onkeyup="deshabilitarFolioMercantil()"/>
						</td>
						<td class="label_patrones">
							<label>Volumen :</label>
						</td>
						<td class="label_patrones_data">
							<form:input path="escrituraConstitutiva.volumen" id="nsMoralVolumen" onkeydown="validarNumeros(event)" style="border: 0px; width: 80%;" onkeyup="deshabilitarFolioMercantil()"/>
						</td>
						<td class="label_patrones">
							<label>Foja :</label>
						</td>
						<td class="label_patrones_data">
							<form:input path="escrituraConstitutiva.foja" id="nsMoralFoja" onkeydown="validarNumeros(event)" style="border: 0px; width: 80%;"  onkeyup="deshabilitarFolioMercantil()"/>
						</td>
					</tr>	
				</table>
			
				<legend>
					<strong>Domicilio fiscal</strong>
				</legend>
				
				<table id="tablaNsMoralDatosDomicilio" style="width: 100%; border: none !important;">
					
					<tr>
						<td class="label_patrones" style="width: 150px !important;">
							<spring:message code="label.domicilio.fiscal"/>:
						</td>
						<td class="label_patrones_data">
						
							<form:label path="domicilioFiscal.vialidadPrimaria.nombre" id="nsMoralCalle" style="border: 0px; width: 80%;" readonly="true"/> 
							#<form:label path="domicilioFiscal.numExterior1" id="nsMoralNumExt" style="border: 0px; width: 80%;" readonly="true"/>
							<form:label path="domicilioFiscal.numExteriorAlf" id="nsMoralNumExtAlf" style="border: 0px; width: 80%;" readonly="true"/>, 
							<spring:message code="label.interior" /> <form:label path="domicilioFiscal.numInterior" id="nsMoralNumInt" style="border: 0px; width: 80%;" readonly="true"/>
							<form:label path="domicilioFiscal.numInteriorAlf" id="nsMoralNumIntAlf"style="border: 0px; width: 80%;" readonly="true"/>, 
							<spring:message code="label.colonia" /> <form:label path="domicilioFiscal.asentamiento.nombre" id="nsMoralReferColonia" style="border: 0px; width: 80%;" readonly="true"/>, 
							<form:label path="domicilioFiscal.asentamiento.localidad.municipio.nombre" id="nsMoralReferDeleg" style="border: 0px; width: 80%;" readonly="true"/>, 
							<form:label path="domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre" id="nsMoralReferEntidad" style="border: 0px; width: 80%;" readonly="true"/>, 
							<spring:message code="label.codigo.postal.abreviado" /> <form:label path="domicilioFiscal.codigoPostal.codigoPostal" id="nsMoralReferCP" style="border: 0px; width: 80%;" readonly="true"/>.
						</td>
					</tr>
					
					<!-- 
					<tr>
						<td class="label_patrones"  style="width: 30%;">
							<label>Calle :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsMoralCalle"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones"  style="width: 30%;">
							<label>N&uacute;mero y/o letra exterior :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsMoralNumExt"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones"  style="width: 30%;">
							<label>N&uacute;mero y/o letra interior:</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsMoralNumInt"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones"  style="width: 30%;">
							<label>Referencia primaria :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsMoralReferUno"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones"  style="width: 30%;">
							<label>Referencia secundaria :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsMoralReferDos"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones"  style="width: 30%;">
							<label>Referencia posterior :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsMoralReferPost"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones"  style="width: 30%;">
							<label>Colonia o poblaci&oacute;n:
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsMoralReferColonia"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones"  style="width: 30%;">
							<label>Localidad :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsMoralReferLocalidad"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones"  style="width: 30%;">
							<label>Delegaci&oacute;n o municipio :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsMoralReferDeleg"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones"  style="width: 30%;">
							<label>Entidad federativa :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsMoralReferEntidad"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones"  style="width: 30%;">
							<label>C&oacute;digo postal :
								<span class="indicador_campo_requerido">*</span>
							</label>
						</td>
						<td class="label_patrones_data" style="width: 70%;">
							<form:input path="" readonly="true" disabled="true" style="border: 0px; width: 80%;" id="nsMoralReferCP"/>
						</td>
					</tr>
					 -->					
				</table>			
			
			</div>
			
			</form:form>
			
			<table id="divMediosContactoNSPersonaMoral" style="width: 1000px">
				<thead>
				</thead>
				<tbody>
				</tbody>
			</table>
		
		<form:form>
			<table style="width: 100%; border: none !important;">
				<tr>
					<td align="center" style="border: none !important;">
						<input type="button" class="mboton" id="btnGuardarNuevoSocioMorak" value="Guardar" style="font-size: .8em !important;" onclick="agregarSocioMoralSesion()">
						<input type="button" class="mboton" id="btnCancelarNuevoSocioMoral" value="Cancelar" style="font-size: .8em !important;" onclick="canclelarAgregarSocioMoralSesion(); validaSocioExtranjero();">
					</td>
				</tr>
			</table>
		</form:form>
	
</div>
<div id="personaMoralNS">
</div>