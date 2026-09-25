<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>			    
<table class="tablaverde2" width="100%">
	<tr>
		<td colspan="4" width="100%">
			<form:form method="post" id="formDerFiscalSITAB" modelAttribute="crtInvitacion" action="">				
				<form:hidden path="cveInvitacion" id="hidCveInvitacionSITAB"/>		
				
					<table class="tablaverde2" width="100%" >
	                	<tr class="impar">
							<td align="left" class="etiqueta2" width="20%" colspan="4">
								&nbsp;
							</td> 
						</tr>
	                 	<tr valign="top" class="par">
							<td align="center" colspan="4">
								&nbsp;
							</td>
							</tr>
	                    	<tr valign="top" class="par">	                    
		                   		<td align="left" width="20%" class="etiqueta2">
	    	               			<span class="required">*</span> Referencia :
	    	               		</td>
								<td align="left" width="30%" >
									<form:input path="txRfrpai" id="referenciaDerFiscalSITAB" size="40" maxlength="50" onkeyup="validaCampo('noCaracteresEspeciales','referenciaDerFiscalSITAB','formDerFiscalSITAB')"/>
									<div id="reqReferenciaDerFiscalSITAB">
	                            		<label class="etiquetaError">La referencia es requerida </label>
	                            	</div>
								</td>
								<td align="right" width="20%" class="etiqueta2">
									<label><span class="required">*</span>Fecha de la Derivaci&oacute;n :</label>
								</td>
								<td align="left" width="30%" >
									<form:input path="fechaPaiTx" readonly="true" size="12" id="fechaDerFiscalSITAB" name="fechaDerFiscalSITAB"  onchange="validaFechaDerFiscalSITAB()"/>
									<span class="boton_limpiar" onclick="limpiaFechaDerFiscalSITAB()" id="btnLimpiaFechaDerFiscalSITAB" >X</span>	                            	
	                            	<div id="errorfecDerFiscalSITAB">
	                            		<label class="etiquetaError">La fecha no puede ser menor a la fecha de emisi&oacute;n </label>
	                            	</div>
	                            	<div id="errorfecNotificaDerFiscalSITAB">
	                            		<label class="etiquetaError">La fecha no puede ser menor a la fecha de notificaci&oacute;n </label>
	                            	</div>
	                            	<div id="reqfecDerFiscalSITAB">
	                            		<label class="etiquetaError">La fecha de la derivaci&oacute;n es requerida </label>
	                            	</div>
								</td>
							<tr>
							<tr valign="top" class="par" class="etiqueta2">
	                            <td align="left" width="20%" class="etiqueta2" colspan="1">
									Funcionario : 
								</td>
	                            <td align="left" colspan="2" width="50%">
	                            	<form:label path="cveUsuario" id="funcionarioDerFiscalSITAB" size="50" readonly="true" />
	                            </td>
	                            <td align="left" width="30%">
							  	</td>
	                        </tr>			                                            		                       
	                        <tr valign="top" class="par"><td align="left" colspan="4">&nbsp;</td></tr>
	                        <tr valign="top" class="par">
	                        	<!--  >td align="center" colspan="2" class="etiqueta2">									
									<input type="button" value="Reabrir Folio" id="btnReaFolioDerFiscalSITABr" width="9px" height="9px" class="boton" onclick="reabrirFolioDerFiscalSITAB()" >
								</td-->
						    	<td align="center" colspan="4">									
									<input type="button" value="Confirmar" id="btnConfirmarDerFiscalSITABr" width="9px" height="9px" class="boton" onclick="validaGuardaderFiscalSITAB()" >
								</td>
							</tr>
							 <tr valign="top" class="par">
						    	<td align="center" colspan="4">
									&nbsp;
								</td>
							</tr>
							<tr class="impar">
								<td align="left" class="etiqueta2" width="20%" colspan="4">
								&nbsp;
								</td> 
							</tr>
		       		</table>																																								
								
			</form:form>
		</td>							    
	</tr>
</table>