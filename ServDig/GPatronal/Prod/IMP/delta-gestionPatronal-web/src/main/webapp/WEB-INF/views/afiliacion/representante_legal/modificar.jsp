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
						<table style=" width: 800px; border: 1px !important;">								
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
									<input type="text" readonly="readonly" id="modificar.segundoApellido" maxlength="14" size="50" cssStyle="width:40%"/>
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
									<input type="text" readonly="readonly" id="modificar.nombre" maxlength="14" size="50" cssStyle="width:40%"/>
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
									<input type="text" readonly="readonly" id="modificar.curp" name="modificar.curp" maxlength="14" size="50" cssStyle="width:40%"/>
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
									<input type="text" readonly="readonly" id="modificar.rfc" maxlength="14" size="50" cssStyle="width:40%"/>
								</td>
							</tr>																				
						</table>	
				</td>
			</tr>
		</table>
		
		<br></br>
		
		<table style=" width: 800px; border: 1px !important;">
			<tr>
				<td align="center" class="label_patrones" colspan="2">
					&iquest;Poder para Actos de administraci&oacute;n o dominio?: &nbsp;<input type="checkbox" id="modificar.indActAdmonDominio"/>
				</td>
			</tr>
		</table>
		
		<br></br>
		
		<table style=" width: 800px; border: 1px !important;">
			<tr>
				<td style="border: none;">
					<div id="divMediosContactoRepLegalPersonaFisicaModificacion" align="center"></div>
				</td>
			</tr>
		</table>							
	</form>