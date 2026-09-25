<%@ include file="../../general/taglibs.jsp"%>
<style>#field { margin-left: .5em; float: left; }  	
	input.error { border: 1px solid red; }
	label.error {		
		padding-left: 16px;
		margin-left: .3em;
	}
</style>			
	<form id="modificarRepLegalForm">
		<br>
		<table>						
			<tr>
				<td>		
						<table style=" width: 900px; border: 1px !important;">								
							<tr>
								<td class="label_patrones" style="width: 150px !important; padding-top: 5px;
									padding-right: 10px;
									padding-bottom: 5px;
									padding-left: 10px;
									border-width: 1px;
									word-wrap: break-word;
									border: solid;
									border-color: #eee;">
									<label>Primer Apellido:</label>
								</td>
								<td style="border:  solid; border-color: #eee; border-width: 1px;">
									<input type="text" readonly="readonly" id="modificar.primerApellido" maxlength="14" cssStyle="width:40%"/>
								</td>
							</tr>
							<tr>
								<td class="label_patrones" style="width: 150px !important; padding-top: 5px;
											padding-right: 10px;
											padding-bottom: 5px;
											padding-left: 10px;
											border-width: 1px;
											word-wrap: break-word;
											border: solid;
											border-color: #eee;">
									<label  >Segundo Apellido:</label>
								</td>
								<td style="border:  solid; border-color: #eee; border-width: 1px;">
									<input type="text" readonly="readonly" id="modificar.segundoApellido" maxlength="14" cssStyle="width:40%"/>
								<td>
							<tr>
								<td class="label_patrones" style="width: 150px !important; padding-top: 5px;
											padding-right: 10px;
											padding-bottom: 5px;
											padding-left: 10px;
											border-width: 1px;
											word-wrap: break-word;
											border: solid;
											border-color: #eee;">
									<label  >Nombre(s):</label>
								</td>
								<td style="border:  solid; border-color: #eee; border-width: 1px;">
									<input type="text" readonly="readonly" id="modificar.nombre" maxlength="14" cssStyle="width:40%"/>
								</td>
							</tr>
							<tr>
								<td class="label_patrones" style="width: 150px !important; padding-top: 5px;
											padding-right: 10px;
											padding-bottom: 5px;
											padding-left: 10px;
											border-width: 1px;
											word-wrap: break-word;
											border: solid;
											border-color: #eee;">
									<label  >CURP:</label>
								</td>
								<td style="border:  solid; border-color: #eee; border-width: 1px;">
									<input type="text" readonly="readonly" id="modificar.curp" name="modificar.curp" maxlength="14" cssStyle="width:40%"/>
								</td>
							</tr>	
							<tr>
								<td class="label_patrones" style="width: 150px !important; padding-top: 5px;
											padding-right: 10px;
											padding-bottom: 5px;
											padding-left: 10px;
											border-top-width: 1px;
											border-right-width: 0px;
											border-bottom-width: 0px;
											border-left-width: 1px;
											word-wrap: break-word;
											border: solid;
											border-color: #eee;">
									<label  >RFC:</label>
								</td>
								<td style="border: solid; border-color: #eee; border-width: 1px;">
									<input type="text" readonly="readonly" id="modificar.rfc" maxlength="14" cssStyle="width:40%"/>
								</td>
							</tr>																				
						</table>	
				</td>
			</tr>
		</table>
		
		<br />
		
		<fieldset style="float: center;">
			<legend>
				<strong>Datos a modificar</strong>
			</legend>
			
			<table style=" width: 850px !important;">
				<tr>
					<td align="center" class="label_patrones" style=" width: 550px !important;">
						&iquest;Poder para Actos de administraci&oacute;n o dominio?: &nbsp;<input type="checkbox" id="modificar.indActAdmonDominio" style="height: 19px; width: 19px; "/>
					</td>
				</tr>
			
				<tr>
					<td align="center"  style="border: none;">
						<div id="divMediosContactoRepLegalPersonaFisicaModificacion" align="center"></div>
					</td>
				</tr>
			</table>
		
		</fieldset>				
	</form>