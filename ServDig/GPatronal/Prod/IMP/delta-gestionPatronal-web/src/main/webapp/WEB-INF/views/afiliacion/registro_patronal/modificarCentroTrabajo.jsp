<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<fieldset style="margin: 20px !important;">
	<legend><strong>Nueva Ubicaci&oacute;n</strong></legend>
	<form id="modificarCentroTrabajoForm">
		<table>
			<tr>
				<td class="label_patrones" width=140 height=35>
					<div>Calle</div>
				</td>
				<td class="label_patrones_data" width=280 height=35>
					<div><input type="text" readonly="true" id="modificarForm.vialidadPrimaria" maxlength="14" cssStyle="width:90%" /> 
					</div>
				</td>
				<td class="label_patrones" width=140 height=35>
					<div>N&uacute;mero Exterior</div>
				</td>
				<td class="label_patrones_data" width=280 height=35>
					<div><input type="text" readonly="true" id="modificarForm.numeroExterior" maxlength="14" cssStyle="width:90%" /> 
					</div>
				</td>
				<td class="label_patrones" width=140 height=35>
					<div>Letra Exterior</div>
				</td>
				<td class="label_patrones_data" width=280 height=35>
					<div><input type="text" readonly="true" id="modificarForm.letraExterior" maxlength="14" cssStyle="width:90%" />
					</div>
				</td>
			</tr>
			<tr>
				<td class="label_patrones" width=140 height=35>
					<div>Numero Interior</div>
				</td>
				<td class="label_patrones_data" width=280 height=35>
					<div><input type="text" readonly="true" id="modificarForm.numeroInterior" maxlength="14" cssStyle="width:90%" />
					</div>
				</td>			
				<td class="label_patrones" width=140 height=35>
					<div>Letra Interior</div>
				</td>
				<td class="label_patrones_data" width=280 height=35>
					<div><input type="text" readonly="true" id="modificarForm.letraInterior" maxlength="14" cssStyle="width:90%" />
					</div>
				</td>
				<td class="label_patrones" width=140 height=35>
					<div>Referencia Primaria</div>
				</td>
				<td class="label_patrones_data" width=280 height=35>
					<div><input type="text" readonly="true" id="modificarForm.vialidadReferenciaPrimaria" maxlength="14" cssStyle="width:90%" /> 
					</div>
				</td>
			</tr>
			<tr>	
				<td class="label_patrones" width=140 height=35>
					<div>Referencia Secundaria</div>
				</td>
				<td class="label_patrones_data" width=280 height=35>
					<div><input type="text" readonly="true" id="modificarForm.vialidadReferenciaSecundaria" maxlength="14" cssStyle="width:90%" /> 
					</div>
				</td>		
				<td class="label_patrones" width=140 height=35>
					<div>Referencia Posterior</div>
				</td>
				<td class="label_patrones_data" width=280 height=35>
					<div><input type="text" readonly="true" id="modificarForm.vialidadReferenciaPosterior" maxlength="14" cssStyle="width:90%" /> 
					</div>
				</td>						
				<td class="label_patrones" width=140 height=35>
					<div>Asentamiento</div>
				</td>
				<td class="label_patrones_data" width=280 height=35>
					<div><input type="text" readonly="true" id="modificarForm.asentamiento" maxlength="14" cssStyle="width:90%" /> 
					</div>
				</td>
			</tr>
			<tr>
				<td class="label_patrones" width=140 height=35>
					<div>Localidad</div>
				</td>
				<td class="label_patrones_data" width=280 height=35>
					<div><input type="text" readonly="true" id="modificarForm.localidad" maxlength="14" cssStyle="width:90%" /> 
					</div>
				</td>			
				<td class="label_patrones" width=140 height=35>
					<div>Municipio</div>
				</td>
				<td class="label_patrones_data" width=280 height=35>
					<div><input type="text" readonly="true" id="modificarForm.municipio" maxlength="14" cssStyle="width:90%" /> 
					</div>
				</td>							
				<td class="label_patrones" width=140 height=35>
					<div>Entidad Federativa</div>
				</td>
				<td class="label_patrones_data" width=280 height=35>
					<div><input type="text" readonly="true" id="modificarForm.entidadFederativa" maxlength="14" cssStyle="width:90%" /> 
					</div>
				</td>
			</tr>
			<tr>
				<td class="label_patrones" width=140 height=35>
					<div>C&oacute;digo Postal</div>
				</td>
				<td class="label_patrones_data" width=280 height=35>
					<div><input type="text" readonly="true" id="modificarForm.codigoPostal" maxlength="14" cssStyle="width:90%"/>
					</div>
				</td>							
				<td class="label_patrones" width=140 height=35>
					<div>Tipo de Vialidad</div>
				</td>
				<td class="label_patrones_data" width=280 height=35>
					<div><input type="text" readonly="true" id="modificarForm.tipoVialidad" maxlength="14" cssStyle="width:90%" /> 
					</div>
				</td>			
			</tr>
		</table>
	</form>
</fieldset>