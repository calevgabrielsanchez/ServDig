<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link rel="stylesheet" type="text/css" 	href="<%=request.getContextPath()%>/resources/estilos/imss/style.css"  />
 <script type="text/javascript"
				src="<%=request.getContextPath()%>/resources/js/delta/jquery.formatCurrency-1.4.0.pack.js"></script>
<html>
    <script>
    </script>
<div id="datosTrabajo" class="form-comment" align="left">    
<input id="hdIdDatosTrabajo" name="hdIdDatosTrabajo" type="hidden" /> 
<br>
<br>
<table style="width: 100%;" class="tablaverde2" >      	  
		 	
			 <tr>
                <td align="center" width="100%" colspan="4">
                      <div class="menu_principal" style="height: 2em !important;">
                    <!--inicia menu principal-->
                    <div align="center">
                        <div class="centrado">
                            <ul style="height: 1em !important;">
                                <li><a>DATOS DEL TRABAJO QUE DESEMPEÑA Y PRESTACIONES (PASO 3 DE 3) </a>
                                </li>
                            </ul>
                        </div>
                        <!--fin centrado-->
                    </div>
                </div>
                 </td>
            </tr>
           <tr> <td> <br/></td> </tr>
           </table> 
		  
		<table style="width: 100%;align="left" >		  
		  	<tr>
		  		<td align="left" class="fontD">1. Indicar fecha de inicio y terminaci&oacute;n.<span class="required">*</span></td>
				<td align="left" class="fontD">
				  <table> 
				     <tr>
				          <td align="right" class="fontD">Fecha Inicio</td>
				          <td>
				          	<input class="caja" type="text" id="txtFechaInicioTrabajoIT"  onchange="javascript:validaFecFinTrabajo();" readonly="readonly" name="txtFechaInicioTrabajoIT" onchange="javascript:validaFecFinTrabajo();"   size="20"/>
			            
				          </td>
<!--  				          <td>Si continua trabajando para el patr&oacute;n dejar la fecha de terminaci&oacute;n en blanco.</td> -->
				          <td align="right" class="fontD">Fecha de terminaci&oacute;n</td>
				          <td>
			           		<input class="caja" type="text" readonly="readonly" id="txtFechaFinTrabajoIT" onchange="javascript:validaFecFinTrabajo();" name="txtFechaFinTrabajoIT"   size="20" />
			           		 
			           	  </td>
				     </tr>

				     <tr>
				     	<td align="right"></td>
				     	<td align="right"></td>
				     	<td align="left" class="fontD">Si continua trabajando para el patr&oacute;n dejar la fecha de terminaci&oacute;n en blanco.</td>
				     	
				     </tr>				     				       



				     
				     <tr>
				     	<td align="right"></td>
				     	<td align="right"><label for="txtFechaInicioTrabajoIT" ></label></td>
				     	<td align="right"></td>
				     	<td align="right"><label for="labelfechaFinTrabajo" id="labelfechaFinTrabajo" ></label></td>
				     </tr>				     				       
				  </table>
				</td>
            </tr>           
           
            <tr>
            <td class="fontD">2. Actividad que desempe&ntilde;a o desempe&ntilde;aba con el patr&oacute;n.<span class="required">* </span>&nbsp;&nbsp;</td>
			<td nowrap><input class="caja" type="text" id="txtDesLaboresDesempIT" name="txtDesLaboresDesempIT"  size="90" maxlength="100" style="text-transform: uppercase;"/>
			     <label for="txtDesLaboresDesempIT" ></label></td>
            </tr>
          
           <tr>
           		<td  class="fontD"> 3. ¿Cuenta con contrato?</td>
				<td  class="fontD">
				    <table>
					    <tr>
					       <td width="50"><input class="fontD" type="radio" name="rdContratoIT" id="rdContratoIT" value="1" onclick="showContrato(this.value)" />S&iacute;</td>
					       <td width="50"><input class="fontD" type="radio" name="rdContratoIT" id="rdContratoIT" value="0" onclick="showContrato(this.value)"/>No</td>
					       <td width="300" align="center">
					           <div id="datosNumContrato" align="center" style="display:none">
					               N&uacute;mero de Contrato:&nbsp;<input class="caja" type="text" name="txtContratoIT" id="txtContratoIT" size="20" maxlength="10" style="text-transform: uppercase;"/><label for="txtContratoIT"  />
					               </div>
					       </td>
					    </tr>
				    </table>
				  </td>
			</tr>
			 <tr> <td> <br/></td> </tr>
			<tr>
                  <td  class="fontD">4. Nombre de su jefe inmediato</td>
                  <td  align="left"><input class="caja" type="text" id="txtDesNomJefeInmediatoIT" name="txtDesNomJefeInmediatoIT"  size="90" maxlength="100"  style="text-transform: uppercase;"/><label for="txtDesNomJefeInmediatoIT" ></label> </td>
                                   
            </tr>
           
            <tr>
				<td align="left" class="fontD">5. Horario de labores.</td>
				 <td align="left"><input class="caja" id="txtDesHorariolaboresIT" name="txtDesHorariolaboresIT" type="text"  size="90"  maxlength="30" style="text-transform: uppercase;"/><label for="txtDesHorariolaboresIT" ></label></td>
										          
			 </tr>
			 	
			 <tr>
    			<td align="left" colspan="2"  class="fontD"> 6.- Sueldos o salarios y otras precepciones.</td>
			</tr>
			 <tr> <td colspan="2"> <br/></td> </tr>
            <tr>            	
            	<td align="right"  class="fontD"><span class="required">* </span>¿Cu&aacute;nto te pagan o te pagaban antes de que concluyera la relaci&oacute;n de trabajo? </td> 
            	<td align="left" nowrap><input class="caja"  style="text-align: right; padding-right:5px" type="text" id="txtImpSalarioPercibidoIT"  size="30" maxlength="10" name="txtImpSalarioPercibidoIT"/><label for="txtImpSalarioPercibidoIT" ></label></td>            
            </tr>
            
          
          
            <tr> 
             	<td align="right"  class="fontD"><span class="required"> *</span>¿Cada cu&aacute;ndo te pagan o te pagaban antes de que concluyera la relaci&oacute;n de trabajo?</td>             	
                <td nowrap>
                
                
                <table>
                <tr>
	        	   <td>                                 
	                    <select name="sltCvePeriodoPagoIT" id="sltCvePeriodoPagoIT" class="fontD" onchange="opcionOtroCP(this)"   class="etiqueta2"></select>                   							                    
	               </td>
	                <td align="center">
	                	<label id="txtDesEspecifiquePPITLabel">&nbsp;&nbsp;Especifique&nbsp;&nbsp;</label>
	                	<input class="caja" type="text" id="txtDesEspecifiquePPIT" name="txtDesEspecifiquePPIT" size="30" maxlength="50" style="text-transform: uppercase;"/>	                         	                    
					</td>
		        </tr>		        
		        <tr>
				   	<td align="right" class="fontD"><label for="sltCvePeriodoPagoIT"  /></td>
					<td><label for="txtDesEspecifiquePPIT" ></label></td>
			    </tr>
			</table>
			   </td>
		  </tr>	
			<tr>
            	<td align="right"  class="fontD">¿Cu&aacute;nto te pagan o te pagaban por vacaciones? </td>
            	<td align="left"><input class="caja" type="text" style="text-align: right; padding-right:5px" id="txtImpVacacionesIT"  size="30" maxlength="10"  name="txtImpVacacionesIT"/><label for="txtImpVacacionesIT" ></label></td>
            </tr>
		 <tr> <td colspan="2"> <br/></td> </tr>
			<tr>
				<td  align="right"  class="fontD">&nbsp;&nbsp;D&iacute;as de Vacaciones&nbsp;&nbsp;</td> 
				<td><input class="caja" type="text" id="txtNumDiasVacacionesIT" onclick="limpiaCampo(this)" name="txtNumDiasVacacionesIT" size="30" maxlength="3"/><label for="txtNumDiasVacacionesIT" ></label></td>
			</tr>
			
			<tr>
			    <td align="right"  class="fontD">Aguinaldo anual </td>  
			    <td>
                 <table> 
				  <tr>
				     
	                 <td><input class="caja"  style="text-align: right; padding-right:5px" type="text" id="txtImpAguinaldoIT" name="txtImpAguinaldoIT" size="30" maxlength="10"/><label for="txtImpAguinaldoIT" ></label></td>
					 <td align="right"  class="fontD">&nbsp;&nbsp;D&iacute;as aguinaldo&nbsp;&nbsp;</td>
 					 <td><input  onclick="limpiaCampo(this)"  class="caja" type="text" id="txtDiasAguinaldoIT" name="txtDiasAguinaldoIT" size="30"  maxlength="22"/><label for="txtDiasAguinaldoIT" ></label></td>
				   </tr>				    
				  </table>
				  </td>
			</tr> 
			
			<tr>
				<td align="right"  class="fontD">Gratificación u otros  (Ejemplo: bonos, propinas, est&iacute;mulos por asistencia y/o puntualidad, etc.) </td> 
				<td> <input class="caja" style="text-align: right; padding-right:5px" type="text" id="txtImpGratificcionIT" name="txtImpGratificcionIT" size="30" maxlength="10" /><label for="txtImpGratificcionIT" ></label></td>
            </tr>
            
			<tr>
				<td  align="right"  class="fontD">¿Te pagan o te pagaban comisiones mediaciones u otra remuneraci&oacute;n similar? (Especificar)&nbsp;&nbsp;</td> 
				<td><input class="caja" type="text" id="txtDesBaseComisionOtrosIT" name="txtDesBaseComisionOtrosIT" size="50" maxlength="50" style="text-transform: uppercase;"/><label for="txtDesBaseComisionOtrosIT" ></label></td>
			</tr>	
			
<!--  			 <tr>
						<td  align="right"  class="fontD">Indicar cual es la base para su otorgamiento, (ejemplo % sobre ventas)</td> 
						<td> <input  class="caja" type="text" id="txtBaseOtorgamientoIT" name="txtBaseOtorgamientoIT" size="50" maxlength="50" style="text-transform: uppercase;"/><label for="txtBaseOtorgamientoIT" ></label></td>
			</tr> -->
			
			<tr> 
						<td colspan="2"  class="fontD"> 7. Indicar el tipo de comprobante de sueldos, salarios u honorarios si fuera el caso:</td>
           </tr>
           <tr> <td> <br/></td> </tr>
            <tr>
			    <td  class="fontD" align="right"><span class="required" > *</span>Comprobante de pago:&nbsp;&nbsp;</td> 
                <td align="left">
                  <table> 
				  	<tr>
	                <td class="fontD" nowrap>                         
                      <select  name="sltCveComprobantePagoIT" id="sltCveComprobantePagoIT" class="fontD" onchange="opcionOtroCP(this)" style="width:180px" >
							<option value="-1">--Por favor seleccione--</option>
						   	<option value="7">RECIBO DE NÓMINA</option>
					   		<option value="8">CHEQUE</option>
					   		<option value="9">RECIBO DE HONORARIOS</option>	
					     	<option value="10">NINGUNO</option>
					   		<option value="11">OTROS</option>
							</select>
					</td>		
					<td >
						<label id="txtDesEspecifiqueCPITLab" >&nbsp;&nbsp;Especifique&nbsp;&nbsp;</label>	<input class="caja" type="text" id="txtDesEspecifiqueCPIT" name="txtDesEspecifiqueCPIT" size="30" maxlength="30" style="text-transform: uppercase;"/>
					</td>					 							
				    
				    </tr>
				    <tr>
				    <td><label for="sltCveComprobantePagoIT"  /></td>					
				    <td></td>
				   </tr>				    
				   <tr><td colspan="2"><label for="txtDesEspecifiqueCPIT" ></label></td></tr>
				  </table>
				  </td>
			</tr> 
			
			<tr> 
				<td  colspan="1" class="fontD" align="center" >Si cuentas con alguno o algunos de los documentos se&ntilde;alados, adjuntar los documentos m&aacute;s recientes</td>
			      <td colspan="3"><table>
			      <tr><td>
                  <input type="text" class="caja" id="txtDesUploadComprobantePago" readonly="readonly"  disabled="true" name="txtDesUploadComprobantePago" maxlength="50" size="20" />
               </td>
               <td>
                   <input type="button" id="uploaderComprobantePago" class="mboton" value="Adjuntar"   />    
                </td>
                <td>
   					<input type="button" value="Ver" id="btnCargar11" name="btnCargar11" onclick="cargarDocPago(0,sltCveComprobantePagoIT);" class="mboton" >
   				</td>
                </tr>
			      
			      </table> </td>
			      
			</tr>
			
			<tr>
			  
			</tr>
			 <tr> <td> <br/></td> </tr>
			<tr>
				<td  class="fontD">8.- ¿C&oacute;mo te pagan o te pagaban tu sueldo? </td>
			</tr>
			 <tr> <td> <br/></td> </tr>
			<tr>
			     <td colspan="2">
			     <div style="float:center;">
			     <table border="1">
					          <tr>
						          <td  class="fontD"><input  type="checkbox" id="cbxEfectivoDT" name="cbxFormaPago" value="13" />&nbsp;&nbsp;Efectivo</td>						         
					        	 <td>
                            			<!-- <input type="text" class="caja" id="txtDesUploadFormaPagoEfec" disabled="true" name="txtDesUploadFormaPagoEfec" maxlength="50" size="20" /> -->
                           		 </td>
					        	 <td>
					        	  		<!-- <input type="button" id="uploaderFormaPagoEfec" class="mboton" value="Adjuntar"   />     -->
					        	 </td>	
					          </tr>
					          <tr> <td> <br/></td> </tr>
					           <tr>
					             <td   class="fontD"><input type="checkbox" id="cbxTransferenciaBancariaDT" name="cbxFormaPago" value="16" onclick="ocultaCamposAdjunto('cbxTransferenciaBancariaDT','txtDesUploadFormaPagoTranBanc','uploaderFormaPagoTranBanc','btnCargar16')" />&nbsp;&nbsp;Transferencia bancaria </td>					                    
					         	 <td>
                            			<input type="text" class="caja" id="txtDesUploadFormaPagoTranBanc" readonly="readonly"  disabled="true" name="txtDesUploadFormaPagoTranBanc" maxlength="50" size="20" />
                           		 </td>
					        	 <td>
					        	  		<input type="button" id="uploaderFormaPagoTranBanc" class="mboton" value="Adjuntar"   />    
					        	 </td>
					        	 <td>
                					<input type="button" value="Ver" id="btnCargar16" name="btnCargar16" onclick="cargarDocPago(16,null);" class="mboton" >
                				</td> 
					          </tr>
					          <tr> <td> <br/></td> </tr>
							 <tr>
							 	 <td  class="fontD"><input type="checkbox" id="cbxChequeDT"	name="cbxFormaPago" value="14" onclick="ocultaCamposAdjunto('cbxChequeDT','txtDesUploadFormaPagoCheque','uploaderFormaPagoCheque','btnCargar14')"/>&nbsp;&nbsp;Cheque</td>
							 	 <td>
                            			<input type="text" class="caja" id="txtDesUploadFormaPagoCheque" readonly="readonly"  disabled="true" name="txtDesUploadFormaPagoCheque" maxlength="50" size="20" />
                           		 </td>
					        	 <td>
					        	  		<input type="button" id="uploaderFormaPagoCheque" class="mboton" value="Adjuntar"   />    
					        	 </td>
							 	<td>
                					<input type="button" value="Ver" id="btnCargar14" name="btnCargar14" onclick="cargarDocPago(14,null);" class="mboton" >
                				</td> 
							 </tr>
							 <tr> <td> <br/></td> </tr>
							 <tr>
								 <td class="fontD"><input type="checkbox" id="cbxDepositoDT" name="cbxFormaPago" value="15" onclick="ocultaCamposAdjunto('cbxDepositoDT','txtDesUploadFormaPagoDepoCuenta','uploaderFormaPagoDepoCuenta','btnCargar15')"/>&nbsp;&nbsp;Dep&oacute;sito en cuenta bancaria</td>							   
								 <td>
                            			<input type="text" class="caja" id="txtDesUploadFormaPagoDepoCuenta" readonly="readonly"  disabled="true" name="txtDesUploadFormaPagoDepoCuenta" maxlength="50" size="20" />
                           		 </td>
					        	 <td>
					        	  		<input type="button" id="uploaderFormaPagoDepoCuenta" class="mboton" value="Adjuntar"   />    
					        	 </td>
					        	 <td>
                					<input type="button" value="Ver" id="btnCargar15" name="btnCargar15" onclick="cargarDocPago(15,null);" class="mboton" >
                				</td> 	
							
							</tr>
							 <tr> <td> <br/></td> </tr>
							 <tr>							 
							    <td  class="fontD"><input  type="checkbox" id="cbxOtrosDT"	name="cbxFormaPago" value="17"  onclick="verificaFP();ocultaCamposAdjunto('cbxOtrosDT','txtDesUploadFormaPagoOtro','uploaderFormaPagoOtro','btnCargar17')"/>&nbsp;&nbsp;* Otros</td>
							    <td>
                            			<input type="text" class="caja" id="txtDesUploadFormaPagoOtro" readonly="readonly"  disabled="true" name="txtDesUploadFormaPagoOtro" maxlength="50" size="20" />
                           		 </td>
					        	 <td>
					        	  		<input type="button" id="uploaderFormaPagoOtro" class="mboton" value="Adjuntar"   />    
					        	 </td>
					        	 <td>
                						<input type="button" value="Ver" id="btnCargar17" name="btnCargar17" onclick="cargarDocPago(17,null);" class="mboton" >
                				</td> 	 
							 </tr
							  <tr> <td> <br/></td> </tr>
							 <tr>
							 	<td  class="fontD" align="left">* Especifique: &nbsp;&nbsp;</td>
							    <td align="right"><input class="caja"  type="text" id="txtDesEspecifiqueFPIT" name="txtDesEspecifiqueFPIT" maxlength="50" size="30" /></td>
							 </tr>  		
					</table></div></td></tr>
			
			<tr>
	        	<td   class="fontD">9.- ¿Sufriste alg&uacute;n accidente en tu trabajo o en el trayecto hacia el mismo?</td>
		    </tr>
		    
		    <tr align="center">
				<td colspan="2">
					 <table>
						 <tr>
						 	<td align="left" class="fontD"><input type="radio" id="rdRiesgo" name="rdRiesgo" value="1" onclick="showRiesgo();"/>&nbsp;&nbsp;S&iacute;&nbsp;&nbsp;</td> 
							<td class="fontD"><input  type="radio" id="rdRiesgo" name="rdRiesgo" value="0" onclick="hideRiesgo();"/>&nbsp;&nbsp;No&nbsp;&nbsp;</td>
							<td  class="fontD" id="tdRiesgo" >&nbsp;&nbsp;¿En que fecha?&nbsp;&nbsp;</td>
							<td><input  class="caja" type="text" id="txtFecFechaRiesgoTrabIT" name="txtFecFechaRiesgoTrabIT" size="30" /><label for="txtFecFechaRiesgoTrabIT"  /></</td>
						</tr>
					</table>
				</td>
			</tr>
			
			<tr>
	           <td colspan="2"  class="fontD">10.- Observaciones: (Anote aqu&iacute; las aclaraciones o manifestaciones que considere necesario comentar)</td>
			</tr>
			
			<tr>
			   <td><div style="float: left;"><textarea  class="cajita" id="desObservacionesIT" name="desObservacionesIT"  onchange="conMayusculas(this);" rows="5" cols="90" style="text-transform: uppercase;height: 30; width: 90" ></textarea></div>
					</td>
			</tr>
			<tr>
			<td><br><br></td>
			</tr>
<!--  			<tr>
			<td class="fontD" align="right">Seleccione la Subdelegaci&oacute;n en la cual se ratificar&aacute; la presente denuncia<span class="required">* </span></td>
			<td> <select  name="sltSubdelegacionIT" id="sltSubdelegacionIT" class="fontD"  >
					<option value="-1">--------Por favor seleccione--------</option>
			</select><label for="sltSubdelegacionIT" ></label></td>
			</tr> -->
			<tr>
			<td><br><br></td>
			</tr>
			<tr><td colspan="2" align="left">Para mayor referencia podr&aacute; consultar el directorio de Subdelegaciones en la siguiente liga:<br><a  href="http://www.imss.gob.mx/directorio/Pages/Instalaciones.aspx" style="color: #0000FF" target="_blank">http://www.imss.gob.mx/directorio/Pages/Instalaciones.aspx</a></td></tr>
		    <tr>																																				
			<td><br><br></td>
			</tr>
		    
		    <tr style="text-align: justify;">
		    <td style="text-align: justify;" colspan="2">"Se hace de su conocimiento que una vez guardada la informaci&oacute;n, el IMSS cuenta con los datos personales que usted proporcion&oacute;, los cuales ser&aacute;n protegidos y confidenciales en t&eacute;rminos de los art&iacute;culos 22 de la Ley del Seguro Social; 14 y 15 de la Ley Federal de Transparencia y Acceso a la Informaci&oacute;n P&uacute;blica Gubernamental, por lo que se le comunica que la falsedad de las declaraciones, y la falsificaci&oacute;n de documentos, constituyen la comisi&oacute;n de Delitos castigados por la Ley; se recomienda tener plena certeza de los hechos que se relatan en la denuncia y la autenticidad de los documentos que se adjuntan en la misma".</td>
		    </tr>
		    
		 
		  </table>
		  <br>
		  <br>
		  <table style="width: 100%;" class="tablaverde2" >      	  
		 	
			 <tr>
                <td align="center" width="100%" colspan="4">
                      <div class="menu_principal" style="height: 2em !important;">
                    <!--inicia menu principal-->
                    <div align="center">
                        <div class="centrado">
                            <ul style="height: 1em !important;">
                                <li><a>DATOS DEL TRABAJO QUE DESEMPEÑA Y PRESTACIONES (PASO 3 DE 3) </a>
                                </li>
                            </ul>
                        </div>
                        <!--fin centrado-->
                    </div>
                </div>
                 </td>
            </tr>
           <tr> <td> <br/></td> </tr>
           </table> 
    </div>
