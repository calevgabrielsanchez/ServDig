<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>	
		    
		     <div id="cancelacionGenericoTab"  style="background-color: white !important;">
			    <div id="wrapperCancelacionGenericoTab" style="background-color: #f2fff2;">	
					<form:form method="post" id="cancelacionGenericoTabForm" modelAttribute="seguimientoGenericoVO" action="seguimiento/generico/cancelacionGenerica.do">
						<form:hidden path="cvePromocion" name="cvePromocion" id="cvePromocion"/>
						<form:hidden path="nombreFuncionarioRegistra" name="hidFuncionarioRegistra" id="hidFuncionarioRegistra"/>
						<input type="hidden" id="fechaEmisionOficioGenerico" >
						<input type="hidden" id="fechaNotificacionOficioGenerico" >
						
						<!-- <fieldset> -->
									<table class="tablaverde2" width="100%" >
									  <tbody>
									  	<tr class="impar">
										    <td align="left" colspan="4">&nbsp;</td>
										</tr>
										    <tr>
										    	<td align="left" width="25%" class="etiqueta2"><span class="required">*</span>Referencia de Cancelaci&oacute;n:																									
													</td>
												<td align="left" width="25%">
													<form:input path="cancelacionGenericoTabVO.referenciaCancelacion" name="referenciaCancelacionCGT" id="referenciaCancelacionCGT" onkeyup="validaCampo('noCaracteresEspeciales','referenciaCancelacionCGT','cancelacionGenericoTabForm')" size="30" maxlength="25" />
													<div id="labelReferenciaCancelacionCGT"></div>
												</td>
										   		<td align="left" width="15%" class="etiqueta2">Funcionario que Registra:																								
												</td>
												<td align="left" width="40%">
<%-- 													<form:input path="nombreFuncionarioRegistra" name="funcionarioRegistraCGT" id="funcionarioRegistraCGT" size="25" readonly="true" /> --%>
													<label id="labelFuncionarioRegistraCGT"></label>
												</td>
											</tr>										
									  	<tr valign="top" class="par">   	
										   		<td align="left" width="25%" class="etiqueta2"><span class="required">*</span>Fecha de Cancelaci&oacute;n																									
												</td>
												<td align="left" width="25%">
													<form:input path="cancelacionGenericoTabVO.fechaCancelacion" name="fechaCancelacionCGT" id="fechaCancelacionCGT" onChange="javascript:validaFechaCancelacionGenerico();" size="10" readonly="true" />
										    	<span class="boton_limpiar" onclick="javascript:limpiaFechaCancelacionGenerico()" id="spnFechaCancelacionCGT">X</span>
												<div id="labelFechaCancelacionCGT"></div>
												</td>
												<td align="left" width="15%" class="etiqueta2"><span class="required">*</span>Funcionario que Autoriza:	    		
											   </td>
												<td align="left" width="40%">
					  									<form:select path="cancelacionGenericoTabVO.cveFuncionarioAutoriza" id="funcionarioAutorizaCGT" name="funcionarioAutorizaCGT" >
					  										<form:option value="-1" label="--Seleccione Por favor--" />
					  									</form:select>
					  									<div id="labelFuncionarioAutorizaCGT"></div>
												</td>												
										</tr>		
										   <tr valign="top" class="par">
												<td align="left" width="25%" colspan="4"><span class="required">*</span><span class="etiqueta2">Motivo de Cancelaci&oacute;n: </span>
												   		<combo:creaCombo entidad="mx.gob.imss.ctirss.correccion.catalogos.model.CgcCatmotivocancelacion"
																			 idHtml="cancelacionGenericoTabVO.cveMotivoCancelacion"
																			 idHtmlContenedor="cancelacionGenericoTabForm"/> 
														<div id="labelCveMotivoCancelacionCGT"></div>					    		
												   </td>
												<td align="left" width="25%" class="etiqueta2"> &nbsp;
												</td>												 
										   </tr>
										   <tr valign="top" class="par">
												<td align="center" width="25%" colspan="4" >
													<input type="button" class="boton" onclick="javascript:procesaFormularioCancelacion('validaCamposCancelacionGen()');" value="Confirmar">																		    		
											   </td>
										   </tr>
										<tr class="impar">
										    <td align="left" colspan="4">&nbsp;</td>
										</tr>										   
										 </tbody>
									</table>
												
						<!-- </fieldset> -->
						<!-- Funcion generica extra para completar flujo de cancelacion -->
						<input  type="hidden"  id="functionAuxCancelacion"/>
						
					</form:form>							    
		     	</div>
			</div>