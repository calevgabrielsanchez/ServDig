<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<%@page import="mx.imss.ctirss.catalogos.model.DlcTipoconcepto"%>
<%@page import="mx.imss.ctirss.catalogos.model.DlcTiposformapago"%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/delta/denuncia/infoTrabajo.js">
		src="<%=request.getContextPath()%>/resources/js/delta/denuncia/guardarInfoTrabajo.js">
	</script>

<input type="hidden" id="hdIdPasoDenuncia" name="hdIdPasoDenuncia" value="3"/>	
	
<div id="datosTrabajo" class="form-comment" align="left">
     <fieldset style="align:center"  style="width: 977px">
        <table style="width: 100%" align="center">		  
		  <tr align="center">
			 <td align="center"><div align="center">
      			<legend><strong>Denuncia del trabajador, beneficiario(s) o su representante legal, en contra de su patr&oacute;n <br/>por no afiliarlo al IMSS, 
				por afiliarlo con un salario inferior al pagado o afiliarlo con <br/>una fecha posterior a la que realmente ingreso a trabajar IMSS-TYS0024</strong></leyend></div>
		   </td>
		   </tr>
		  </table> 
     </fieldset>
     
     <fieldset style="align:center"  style="width: 977px">	 
		<table style="width: 100%">		  
		  <tr>
			 <td align="center">			
				<table>
					<tbody align="left" >						 
						<tr valign="top"><td align="center">
						        <legend><strong>Informaci&oacute;n que deber&aacute; proporcionar el denunciante, beneficiario o encargado de representarlo</strong></leyend>
						 	</td></tr>
						<tr valign="top"><td align="center"><h3 align="center"><strong>INFORMACI&Oacute;N DEL TRABAJO QUE DESEMPEÑA Y PRESTACIONES</strong></h3></td></tr>	
						<tr valign="top"><td align="center"><h3 align="center"><strong>PASO 3 de 3</strong></h3></td></tr>	
					    </tbody>
			      </table>
			  </td>
			</tr>
			<tr><td align="left">Proporcionar la siguiente informaci&oacute;n: Obligatorio <span class="required">* </span></td></tr>				
		</table>
	  </fieldset>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
 <form:form  modelAttribute="denunciaDTO"  action="/denunciaenlinea/denunciaLinea/datosTrabajo/guardarInfoTrabajo.do" method="POST" id="infoTrabajoForm" name="infoTrabajoForm">
	  <fieldset style="width: 977px" class="titulo" style="align:center">
         <legend><strong>DEL TRABAJADOR</strong></legend>
         <table style="width: 100%" align="left">
            <tr> <td> <br/></td> </tr>
            
            <tr>
            	<td>1.-Periodo en que trabajo para el patr&oacute;n.</td>
            </tr>
            <tr>
            	<td colspan="2" align="right">
            		<table>
            		<tr>
					<td>&nbsp;&nbsp;Fecha Inicio:</td>
            		<td><input type="text" id="fechaInicioTrabajo" name="fechaInicioTrabajo" value="" size="20px" maxlength="20px" readonly="readonly" onclick=" $(this).datepicker();" /></td>
            		<td>&nbsp;&nbsp;Fecha de terminaci&oacute;n:</td>
            		<td><input type="text" id="fechaFinTrabajo" name="fechaFinTrabajo" value="" size="20" maxlength="30" readonly="readonly" onclick=" $(this).datepicker();" /></td>
            	    </tr>  						 	
					</table>
					
				</td>  
            </tr>
            <tr> <td> <br/></td> </tr><br>
            <tr>
				<td id="etiquetaRequired"> 2.-Actividad que desempe&ntilde;a o desempe&ntilde;aba con el patr&oacute;n.</td>
				 <td><input path="dltInfotrabajo.desLaboresdesemp"  type="text" id="actividad" class="required" size="10" maxlength="100" /><span class="required">* </span></td>							          
			</tr>
			<tr> <td> <br/></td> </tr>
			<tr><td> 3. ¿Cuenta con contrato?</td></tr>
			<tr><td align="center"><input path="dltInfotrabajo.desNumcontrato" type="radio"	name="nss" value="No"/>No <input path="dltInfotrabajo.desNumcontrato" type="radio"	name="nss" value="Si"/>S&iacute;</td>
			</tr>
			<tr> <td> <br/></td> </tr>
			<tr align="left">
                  <td>4.Nombre de su jefe inmediato</td>
                  <td><input path="dltInfotrabajo.desNomjefeinmediato" type="text" id="nombrePatron"  maxlength="100"  /> </td>
            </tr>
      
              
              <tr> <td> <br/></td> </tr>
              <tr>
				<td align="left">5.Horario de labores.</td>
				 <td align="left"><input path="dltInfotrabajo.desHorariolabores" id="dltInfotrabajo.desHorariolabores" name="dltInfotrabajo.desHorariolabores" type="text"  maxlength="30" /></td>							          
			 </tr>
			 <tr> <td> <br/></td> </tr>
			 <tr align="left"  >
                  <td colspan="2"> 6.- Sueldo o salario que percibe o percibía, así como indicar si percibe o percibía otro tipo de remuneraciones a su trabajo.</td>
            </tr>
             <tr> <td> <br/></td> </tr>
            <tr>         
               <td colspan="2">
                 <table> 
                   <tr><td id="etiquetaRequired">Salario $&nbsp;</td> <td align="left">  <input path="dltInfotrabajo.impSalariopercibido" type="text" id="salario"  /><span class="required">* </span>  </td></tr>
                   <tr> <td> <br/></td> </tr>
                   <tr><td >Vacaciones $&nbsp;</td><td align="left"><input path="dltInfotrabajo.impVacaciones" type="text" id="vacaciones" /> </td></tr>
                    <tr> <td> <br/></td> </tr>
                   <tr>
                   <td id="etiquetaRequired">Periodo de pago de salarios: </td> 
                   <td>
                   <table> 
                  	 <tr>
                   		<td>
                         
                           <select path="dltFormapagoPP.dlcTiposformapago.cveFormapago" name="cveFormaPago" id="cveFormaPago" class="required" onchange="otroPeriodo()" >
						   		
							</select>
							<span class="required">* </span>
							&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					<td>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="periodoOpcional">Especifique</span>
					</td>
					<td>
						<input path="dltFormapagoPP.desEspecifique" type="text" class="periodoOpcional"  maxlength="50"/>
					</td>
					</tr>
					</table>
					</td>
					</tr>
					<tr> <td> <br/></td> </tr>													
				    <tr><td>Días de Vacaciones</td> <td> <input path="dltInfotrabajo.numDiasvacaciones" type="text" id="diasVacaciones" /> </td></tr>
				    <tr>
				    	<td>Aguinaldo anual $</td> 
				    	<td>
				    		<table>
				    			<tr> 
				    				<td>
				    					<input path="dltInfotrabajo.impAguinaldo" type="text" id="aguinaldoAnual" />
				    				</td>
				    				<td>
				    				 	Días aguinaldo
				    				</td>
				    				<td>
				    					<input type="text" id="diasAguinaldo" />
				    				</td>
				    			</tr>
				    		</table> 
				    	</td>
				    </tr>
					<tr><td>Gratificación u otros $</td> <td> <input type="text" id="aguinaldo" /> </td></tr>		
					<tr><td>Comisiones, mediaciones, otros (especifique)</td> <td><input path="dltInfotrabajo.impComisionOtros" type="text" id="pagoComisiones" /></td></tr>	
					<tr><td>Indicar cual es la base para su otorgamiento, (ejemplo % sobre ventas)</td> <td> <input path="dltInfotrabajo.desBaseComisionOtros" type="text" id="baseComisiones" /></td></tr>		    													
                 </table>
                </td>
            </tr>
            <tr> <td> <br/></td> </tr>
            <tr> 
				<td id="etiquetaRequired"> 7.-Indicar el tipo de comprobante de sueldos, salarios u honorarios si fuera el caso:</td>
            </tr>
           <!--  <tr> <td> <br/></td> </tr> -->
            <tr>
            	
            	<td>
            		 <table> 
                  	 <tr>
                   		<td>
                           <select path="dltFormapagoCP.dlcTiposformapago.cveFormapago" name="tipoComprobante" id="tipoComprobante" class="required" onchange="otroComprobante()">
								
							</select>
							<span class="required">* </span>
							&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					<td>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="comprobanteOpcional">Especifique</span>
					</td>
					<td>
						<input path="dltFormapagoCP.desEspecifique" type="text" id="otroTipoComprobante" class="comprobanteOpcional" maxlength="50"/>
					</td>
					</tr>
					<tr> <td> <br/></td> </tr>
					<tr> 
						<td colspan="3">Si Usted cuenta con alguno de los documentos se&ntilde;alados, por favor adjuntar el documento m&aacute;s reciente</td>
					
					</tr>
					<tr> <td> <br/></td> </tr>
					<tr>
					<!-- <td colspan="3"><a href="#" onclick="test()">Adjuntar Comprobante</a>  --> 
					 	<td colspan="3">Adjuntar Comprobante:<input path="dltFormapagoCP.desEspecifique" type="file" id="file" name="file"/>
					 <!-- 	<input id="uploadbutton" type="button" value="Upload"/>   --> 
						<a href="">Eliminar Comprobante</a></td>
					</tr>
					</table>	
                </td>
            </tr>
            <tr> <td> <br/></td> </tr>
            <tr><td colspan="2">8.- Forma de pago: </td></tr>
            <tr> <td> <br/></td> </tr>
			<tr><td>
			     <table id="formaPago">
			  	<tr><td ><input path="dltFormapagoFP.dlcTiposformapago.cveFormapago" type="checkbox"	name="efectivo" value="12" /> Efectivo</td></tr> 
					<tr><td><input path="dltFormapagoFP.dlcTiposformapago.cveFormapago" type="checkbox"	name="cheque" value="13" /> Cheque</td></tr> 
					<tr><td><input path="dltFormapagoFP.dlcTiposformapago.cveFormapago" type="checkbox"	name="depositoBancario" value="14" /> Dep&oacute;sito en cuenta bancaria</td> </tr>
					<tr><td><form:input path="dltFormapagoFP.dlcTiposformapago.cveFormapago" type="checkbox"	name="transferenciaBancaria" value="15" /> Transferencia bancaria </td> </tr>
					<tr><td><form:input path="dltFormapagoFP.dlcTiposformapago.cveFormapago" type="checkbox"	name="otros" value="16" /> Otros</td>  
						<td>Especifique <form:input path="dltFormapagoFP.desEspecifique" type="text" id="otrosEspecifica"  maxlength="50"/></td>										       									       										       								     							    
					 </tr> 
					  
					<tr> 
						<td colspan="2">Si Usted cuenta con alguno de los documentos se&ntilde;alados, podr&aacute; adjuntarlo la versi&oacute;n digital dando click en el siguiente bot&oacute;n.
						    <button type="button"  id="buttonAdjuntarComprobantePago" class="mboton" >Adjuntar</button>
						</td>
						
					</tr>   
					<tr> <td> <br/></td> </tr> 
					<tr> <td align="right"> Cuenta bancaria.pdf <img src="<%=request.getContextPath()%>/resources/images/delete-icon.png" width="16" height="16"></td>
					 </tr>									    
			      </table>
				</td>							     
			</tr>
			<tr> <td> <br/></td> </tr>
			<tr> 
			  <td colspan="3">9.- Sufri&oacute; alg&uacute;n riesgo de trabajo.</td></tr>
			<tr> <td> <br/></td> </tr>
			<tr align="left">
			   <td><div style="float: left;"><input type="radio"	name="riesgoNo" value="N"/>No &nbsp; <input type="radio" name="riesgoSi" value="S"/>S&iacute; &nbsp; </div></td>
			   <td><div style="float: left;">
			           <form:input path="dltInfotrabajo.fecFechariesgotrab" type="text" id="fechaRiesgoTrabajo" name="fechaRiesgoTrabajo" readonly="readonly" onclick=" $( this ).datepicker();" value="" size="40" maxlength="40" 
			                 onchange="jsValidaDiaHabil(fechaRiesgoTrabajo);"/></div></td>
			</tr>
			 <tr> <td> <br/></td> </tr>
			 <tr><td> 10.- Observaciones: (Anote aqu&iacute; las aclaraciones o manifestaciones que considere necesario comentar)</td></tr>
			 <tr> <td> <br/></td> </tr>		
			 <tr><td colspan="2"><form:textarea path="dltInfotrabajo.desObservaciones" rows="2" cols="300"/></td></tr>
			 <tr> <td> <br/></td> </tr>
			 <tr><td colspan="2"> Seleccione la Subdelegación en la cual se ratificará la presente denuncia:  <select id="selSubdelegacion" name="selSubdelegacion">
												<option>--Seleccione Subdelegacion--</option>
					   </select> </td></tr>
			 <tr><td>
                       
              </td></tr>
              <tr> <td> <br/></td> </tr>
              <tr><td colspan="2" align="left"> Para mayor referencia podrá consultar el directorio de Subdelegaciones en la siguiente liga:<br>
                    <a href="http://www.imss.gob.mx/directorio/Pages/Instalaciones.aspx" title="Subdelegaciones IMSS">http://www.imss.gob.mx/directorio/Pages/Instalaciones.aspx</a>
                   </td></tr>
         </table>
      </fieldset>
      
      <fieldset style="align:center"  style="width: 977px">
        <table style="width: 100%" align="center">		  
		  <tr align="center">
			 <td align="center">
      			Se hace de su conocimiento que una vez guardada la información, el IMSS cuenta con los datos personales que usted proporcionó <br/>en la presente solicitud, los cuales serán protegidos y confidenciales en términos de los artículos 22 de la Ley del Seguro <br/>Social; 14 y 15 de la Ley Federal de Transparencia y Acceso a la Información Pública Gubernamental, por lo que se le comunica <br/>que la falsedad de declaraciones,  y la falsificación de documentos, constituyen la comisión de Delitos castigados por la Ley, <br/>por lo que se recomienda tener plena certeza de los hechos que se relatan en la denuncia y de la autenticidad de los documentos <br/>que adjuntan en la misma.
		   </td>
		   </tr>
		  </table> 
     </fieldset>
    <fieldset style="align:center">
		<table align="left" >		  
		  <tr>
			 <td align="left" >			
					<table >
						<tbody align="center" >						 
							<tr align="center"width="900px">
							  <td> A CONTINUACIÓN USTED DEBE HACER CLICK EN UNA DE LAS SIGUIENTES OPCIONES. SI HACE CLICK EN SALIR SIN GUARDAR SUS DATOS, LA INFORMACIÓN CAPTURADA NO SERÁ REGISTRADA
							       
							  </td>					    
							</tr>	
							<tr> <td> <br/></td>
							</tr>						
							<tr>
						     <td align="center" width="900px">	
						            <!--  <button type="button" onclick="javascript:siguiente();" id="buttonSiguiente" class="mboton">Guardar</button> -->
						             
						          <!-- <button type="button"  onclick="submitea();" id="buttonSiguiente" class="mboton">GURADAR</button> -->
						              <button type="submit"  id="buttonSiguiente" class="mboton">GURADAR</button> 
						         <!--   <button type="button" onclick="abrirDialogoConfirmacion();" id="buttonSiguiente" class="mboton">Guardar</button>-->
						            <button type="button"  id="buttonSiguiente" class="mboton">IR AL PASO 1</button>
						            <button type="button"  id="buttonSiguiente" class="mboton">IR AL PASO 2</button>									    									
									<button type="button"  id="buttonSalirDatosTrabajador" class="mboton">ENVIAR DENUNCIA</button>
							</td>
		 				   </tr>							
						</tbody>
					</table>	
				</td>
			</tr>	
		</table>								
	 </fieldset>     
    </form:form>
</div>
