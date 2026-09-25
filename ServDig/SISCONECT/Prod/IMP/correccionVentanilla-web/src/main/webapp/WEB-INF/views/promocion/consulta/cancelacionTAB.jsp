<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/promocion/cancelacionTab.js"></script>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
	<table class="tablaverde2">
		<tr>
			<td colspan="4">
			
			<form id="promocionCancelacionForm" >
				<input type="hidden" name="cvePromocion" id="cvePromocion"/>
				<input type="hidden" name="bandera" id="bandera" value="cancelacion"/>
	                <table class="tablaverde2" style="width: 800px" >
	                        <tr valign="top" class="par">
	                        	<td align="left" width="20%" class="etiqueta2">
	                        		<span class="required">*</span> Referencia de cancelaci&oacute;n :</td>
								<td align="left" width="20%">
									<input type="text" id="refCancelacion" name="nuVolanteCancela" onkeyup = "this.value=this.value.toUpperCase()" onkeyup="validaCampo('noCaracteresEspeciales','referenciaCancelacionCGT','cancelacionGenericoTabForm')" size="25" maxlength="25"/>
									<div id="labelRefCancelacion"></div>
								</td>
								<td align="left" width="200px" class="etiqueta2">
									<label>Funcionario que registra:</label>
								</td>
								<td align="left" width="100px">
									<input id="funcionarioReg"  readonly="readonly" size="40" name="funcionarioReg" disabled="disabled"/>																			
									<div id="labelFuncionarioReg"></div>
								</td>
							<tr>
							<tr valign="top" class="par">
	                            <td align="left" width="200px" class="etiqueta2" colspan="1">
									<span class="required">*</span>Fecha de cancelaci&oacute;n : 
								</td>
	                            <td align="left">
	                            	<input type="text" readonly="readonly" size="12" id="fecCancelacion" name="fecCancelacion"
	                            	onchange="javaScript:jsValidaFecCancelacion(this.value);">
	                            	<span class="boton_limpiar" onclick="limpiaFechaCancelacion()" id="spnFecCan">X</span>
	                            	<div id="labelFecCancelacion"></div>
	                            </td>
	                             <td align="left" width="200px" class="etiqueta2" colspan="1">
									<span class="required">*</span><label>Funcionario que autoriza :</label>
	                            </td>
	                            <td align="left">
									<!-- input type="text" readonly="readonly" id="funcionarioAut" size="40" disabled="disabled" -->	
									<select id="funcionarioAutorizaOrd" name="funcionarioAutorizaOrd">
										<option value="-1" label="--Seleccione Por favor--"></option>
									</select>
				  					<div id="labelFuncionarioAutorizaOrdinario"></div>		                        
							  	</td>
							  	
							  	
							  	
	                        </tr>		
	                        <tr valign="top" class="par">
	                        	<td align="left" width="200px" class="etiqueta2" colspan="1">
									<span class="required">*</span>Motivo de Cancelaci&oacute;n: 
	                           	</td>
	                            <td align="left" colspan="3">
									<combo:creaCombo entidad="mx.gob.imss.ctirss.correccion.catalogos.model.CgcCatmotivocancelacion"
													 idHtml="idMotivoCancelacion"
													 idHtmlContenedor="promocionCancelacionForm"
													 
													 />
								<div id="labelMotivoCancelacion"></div>				                        
							  	</td>
	                        </tr>                       		                       
	                        <tr valign="top" class="par"><td align="left" colspan="4">&nbsp;</td></tr>
	                        <tr valign="top" class="par">
						    	<td align="center" colspan="4">
									<div id="prueba45">
									<input type="button" value="Guardar" id="btnConfirmarCancel" width="10px" height="12px" class="boton" onclick="onClickBtnConfirmarCancel()">
									</div>
									
								</td>
							</tr>
							
		       		</table>
	       		</form>
            </td>
        </tr>
    </table>
