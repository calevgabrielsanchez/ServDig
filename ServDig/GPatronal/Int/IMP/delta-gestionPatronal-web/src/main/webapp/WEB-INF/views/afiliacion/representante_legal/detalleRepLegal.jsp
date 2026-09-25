<%@ include file="../../general/taglibs.jsp"%>
<style>#field { margin-left: .5em; float: left; }  	
	input.error { border: 1px solid red; }
	label.error {		
		padding-left: 16px;
		margin-left: .3em;
	}
</style>			
	
	<form id="modificarRepLegalForm">
		<table boder=2>						
			<tr>
				<td width=400>					
					<fieldset>								
						<fieldset class="fsInterno">
							<label style="width:85%">Poder para: &nbsp; &nbsp;&nbsp; &nbsp;Actos de Administraci&oacute;n</label>											
							<input type="checkbox" id="modificar.incActAdm" value="1" cssStyle="width:5%"/>
						</fieldset>																															
						<fieldset class="fsInterno">
							<label style="width:35%">Primer Apellido:</label>											
							<input type="text" readonly="true" id="modificar.nombre" maxlength="14" cssStyle="width:40%"/>
						</fieldset>																															
						<fieldset class="fsInterno">
							<label style="width:35%">Nombre(s):</label>
							<input type="text" readonly="true" id="modificar.primerApellido" maxlength="14" cssStyle="width:40%"/>
						</fieldset>																															
						<fieldset class="fsInterno">
							<label style="width:35%">CURP:</label>
							<input type="text" readonly="false" id="modificar.curp" name="modificar.curp" maxlength="14" cssStyle="width:40%"/>
						</fieldset>												
						<fieldset class="fsInterno">
							<label style="width:35%">Tel&eacute;fono Fijo:</label>							
							<input type="text" readonly="true" id="modificar.primerApellido" maxlength="14" cssStyle="width:40%"/>
						</fieldset>
						<fieldset class="fsInterno">
							<label style="width:35%">Tel&eacute;fono m&oacute;vil con clave de larga distancia:</label>
							<input type="text" readonly="true" id="modificar.primerApellido" maxlength="14" cssStyle="width:40%"/>
						</fieldset>												
					</fieldset>											
				</td>
				<td width=400>										
					<fieldset>							
						<fieldset class="fsInterno">
							<label style="width:35%">Actos de Dominio</label>
							<input type="checkbox" id="modificar.incActDom" value="1" cssStyle="width:5%"/> 											
						</fieldset>
						<fieldset class="fsInterno">
							<label style="width:35%">Segundo Apellido:</label>
							<input type="text" readonly="true" id="modificar.segundoApellido" maxlength="14" cssStyle="width:40%"/>
						</fieldset>
						<fieldset class="fsInterno">
							<label style="width:35%">RFC:</label>
							<input type="text" readonly="true" id="modificar.rfc" maxlength="14" cssStyle="width:40%"/>
						</fieldset>
						<fieldset class="fsInterno">
							<label style="width:35%">Ext:</label>
							<input type="text" readonly="true" id="modificar.primerApellido" maxlength="14" cssStyle="width:40%"/>
						</fieldset>
						<fieldset class="fsInterno">
							<label style="width:35%">Direcci&oacute;n de Correo electr&oacute;nico!:</label>
							<input type="text"  id="modificar.email" class="required" maxlength="14" cssStyle="width:40%"/>
						</fieldset>
						<fieldset class="fsInterno">
							<label style="width:35%">Direcci&oacute;n de Correo electr&oacute;nico alterno:</label>
							<input type="text" readonly="true" id="modificar.primerApellido" maxlength="14" cssStyle="width:40%"/>
						</fieldset>																												
					</fieldset>					
				</td>
			</tr>
		</table>							
	</form>