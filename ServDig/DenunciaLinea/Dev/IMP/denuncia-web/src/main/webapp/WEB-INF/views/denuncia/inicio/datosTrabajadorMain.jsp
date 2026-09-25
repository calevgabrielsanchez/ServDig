<%@page import="java.text.SimpleDateFormat"%>
<%@page import="mx.imss.ctirss.session.UserSession"%>
<%@page import="mx.imss.ctirss.session.ConstantesSession"%>
<%@page import="java.util.Date"%>
<%@page import="mx.imss.ctirss.model.DltUsuarioden"%>
<%@page import="mx.imss.ctirss.catalogos.model.DlcUsuario"%>
    <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
    "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/delta/fileUpload/upclick.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/delta/fileUpload/uploadFiles.js"></script>


<html>     
 <div id="domicilioUbicar"></div>
 <div id="domicilioUbicar1"></div>
 <div id="domicilioUbicar2"></div>    
<div id="datosTrabajador" class="form-comment" align="left">    
    <input id="hdIdTrabajador" name="hdIdTrabajador" type="hidden" />
    <input id="hdIdBeneficiario" name="hdIdBeneficiario" type="hidden" />
    <input id="hdIdRepLegal" name="hdIdRepLegal" type="hidden" />
    <input id="hdIdm1" name="hdIdm1" type="hidden" />
    <input id="hdIdm2" name="hdIdm2" type="hidden" />
    <input id="hdIdm3" name="hdIdm3" type="hidden" />
    <input id="hdIdm4" name="hdIdm4" type="hidden" />    
        
        <table style="width: 100%;" class="tablaverde2">       
          <tr>
                <td align="center" width="100%" colspan="6">
                       <div class="menu_principal" style="height: 2em !important;">
                    <!--inicia menu principal-->
                    <div align="center">
                        <div class="centrado">
                            <ul style="height: 1em !important;">
                                <li><a> DATOS DEL TRABAJADOR (PASO 1 DE 3) </a>
                                </li>
                            </ul>
                        </div>
                        <!--fin centrado-->
                    </div>
                </div>
                 </td>
          </tr> 
          <tr> <td> <br/></td> </tr>
           <tr> <td> <br/></td> </tr> 
           </table>
           <table width="100%">
            <tr align= "center">
                <td></td>
                <td class="etiqueta2" align="right">Folio de la denuncia:</td>
                <td align="left"> <input id="txtFolioDenunciaDT" name="txtFolioDenunciaDT" type="text" readonly="readonly" disabled="true" class="cajaBloquer" size="30" style="text-transform: uppercase;"/><label for="txtFolioDenunciaDT"></label> </td>
                <td align="right" class="etiqueta2"> Presenta la denuncia como: </td>
                <td align="left"> 
                    <select id="cmbCveTipoDenuncianteDT" name="cmbCveTipoDenuncianteDT" onchange="habilitaDenunciante();" class="etiqueta2" style="text-transform: uppercase;">
                       <option value="-1">--Seleccione--</option>
                       <option value="1">Trabajador</option>
                       <option value="2">Beneficiario</option>
                       <option value="3">Representante Legal</option>
                    </select>
                </td>
                <td><label for="cmbCveTipoDenuncianteDT"/></td>                              
            </tr>
            <tr> <td> <br/></td> </tr>
        </table>
        <table>
            <div class="menu_principal" style="height: 2em !important;">
                    <!--inicia menu principal-->
                    <div align="center">
                        <div class="centrado">
                            <ul style="height: 1em !important;">
                                <li><a> DATOS DEL TRABAJADOR </a>
                                </li>
                            </ul>
                        </div>
                        <!--fin centrado-->
                    </div>
                </div>
        </table>
        <table style="width: 100%;align="left" >
                <tr>
                    <td width="20%"></td>
                    <td width="16%"></td>
                    <td width="16%"></td>
                    <td width="16%"></td>
                    <td width="16%"></td>
                    <td width="16%"></td>
               </tr>
               <tr>
               
                  <td align="right" class="fontD" colspan="2" ><span class="required">* </span> Nombre completo y correcto: </td>
                  <td align="center"><input id="txtDesPaternoDT" name="txtDesPaternoDT" class="caja"  type="text"  style="text-transform: uppercase;"style="text-transform: uppercase;" size="30" maxlength="50"/><label for="txtDesPaternoDT"></td>
                  <td align="center"><input id="txtDesMaternoDT" name="txtDesMaternoDT" class="caja" type="text"  style="text-transform: uppercase;" size="30" maxlength="50"/><label for="txtDesMaternoDT"></td>
                  <td align="center"><input id="txtDesNombreDT" name="txtDesNombreDT" class="caja" type="text"   style="text-transform: uppercase;" size="30" maxlength="50"/><label for="txtDesNombreDT"></td>
                  <td></td>                  
  			  </tr>
               
               <tr>
                    <td></td>
                     <td></td>
                    <td align="center" class="fontD">Apellido Paterno</td>
                    <td align="center" class="fontD">Apellido Materno</td>
                    <td align="center" class="fontD">Nombre</td>
                     <td></td>
               </tr>
               <tr> <td> <br/></td> </tr>

               <tr>
                   <td align="right" class="fontD" colspan="2" ><span class="required">* </span>  Sexo:</td>
                    <td align="center">
                        <table>
               				<tr>
                            	<td align="left" class="fontD" width="100"><input type="radio" name="rdCveSexo" id="rdCveSexo" value="0" class="fontD" ">Hombre </input></td>
                            	<td align="right" class="fontD"><input type="radio" name="rdCveSexo" id="rdCveSexo" value="1" class="fontD" ">Mujer </input></td>
               				</tr>
                        </table>                        
                    </td>
                  <td></td>                  

               </tr>
               <tr> <td> <br/></td> </tr>
               

<!--               <tr>
                 <td align="right" class="fontD" colspan="2" ><span class="required">* </span> Fecha de Nacimiento: </td>
         				  <td align="center"><input id="txtFechaNacimientoDT" name="txtFechaNacimientoDT" class="caja" type="text"  style="text-transform: uppercase;" size="30" maxlength="50"/><label for="txtFechaNacimientoDT"></td>   
                  
                  <td></td>                  
  			  </tr>

               <tr>
               <td align="right" class="fontD" colspan="2" ><span class="required">* </span> Lugar de Nacimiento: </td>                 
           				  <td align="center"><input id="txtLugarNacimientoDT" name="txtLugarNacimientoDT" class="caja" type="text"   style="text-transform: uppercase;" size="30" maxlength="50"/><label for="txtLugarNacimientoDT"></td>
               </tr> -->



               <tr>
                   <td class="fontD" align="right" colspan="2">  ¿Cuenta con N&uacute;mero de Seguridad Social?</td>
                    <td align="center">
                        <table>
                        <tr>
                            <td align="left" class="fontD" width="50"><input type="radio" name="rdCveNssDT" id="rdCveNssDT" value="1" class="fontD" onclick="showNss();">Si</input></td>
                            <td align="right" class="fontD"><input type="radio" name="rdCveNssDT" id="rdCveNssDT" value="0" class="fontD" onclick="hideNss();">No </input></td>
                        </tr>
                        </table>                        
                    </td>
                    <td colspan="2">
                      <table id="tblNss" name="tblNss">
                        <tr>
                          <td align="center" class="fontD">N&uacute;mero de Seguridad Social:</td>
                    	  <td align="center"><input name="txtCveNssDT" id="txtCveNssDT" class="caja" maxlength="11" size="30"/><label for="txtCveNssDT"></label></td>
                        </tr>
                      </table>
                    </td>
                    <td></td>
               </tr>
            <tr> <td> <br/></td> </tr>
                <tr>
                  <td colspan="2" class="fontD" align="right">Clave &Uacute;nica de Registro de Poblaci&oacute;n (CURP): </td>                 
                  <td align="center" colspan="2"><input id="txtCveCurpDT" name="txtCveCurpDT" onchange="toUpperCase(this)" type="text" class="caja" maxlength="18" size="60" style="text-transform: uppercase;"/> <label for="txtCveCurpDT"></label></td>
                   <td></td>
                   <td></td>
              </tr>
           
               <tr>                             
                  <td class="fontD" colspan="2" align="right"> Registro Federal de Contribuyentes (RFC):
                  </td>
                  <td align="center" colspan="2">
                  <input id="txtCveRfcDT" onchange="toUpperCase(this)" class="caja" type="text" name="txtCveRfcDT" size="60" maxlength="13" style="text-transform: uppercase;"/><label for="txtCveRfcDT"></label>
                  
                  </td>
                   <td></td>
                   <td></td>
              </tr>
            
            <tr> 
                  <td class="fontD" colspan="2" align="right">Direcci&oacute;n:</td>
                  <td colspan="2" align="center"><textarea cols="20" rows="2" style="width: 270px;height: 40px;" class="caja" id="txtDomicilio" name="txtDomicilio" readonly="readonly"  ></textarea> <label for="txtDomicilio"  /></td>
                  <td colspan="1"  align="center">
                     <input type="button" id="ubicar" name="ubicar"  class="mboton" value="Agregar Direcci&oacute;n" onkeypress="setPersona('1');" onclick="setPersona('1');"/>   
                  </td>     
                   <td>
                   	 <input type="button" id="quitar" name="quitar"  class="mboton" value="Quitar Direcci&oacute;n" onclick="borraDomicilio(1)"/>  
                   </td>
                   
              </tr>    
               <tr> <td> <br/></td> </tr>
              <tr> 
                <td class="fontD" colspan="2" align="right">	 Correo Electr&oacute;nico (Si lo desea es posible indicar un correo eletr&oacute;nico diferente):</td>
                <td align="center" colspan="2" >
                <input type="text" class="cajaMin" id="txtDesEmailDT" name="txtDesEmailDT"  onkeypress="toLowerCase(this);" onkeydown="toLowerCase(this);" onchange="toLowerCase(this);"  maxlength="50" size="60"/ value="<%= ((UserSession)request.getSession().getAttribute(ConstantesSession.USR_SESSION)).getNomUsuarioSistema() %>"><label for="txtDesEmailDT"></label>
                </td>
                <td></td>
                <td></td>
              </tr>
           
              <tr>
                    <td class="fontD" colspan="2" align="right">Tel&eacute;fono 12 N&uacute;meros incluyendo clave LADA:</td>
                     <td align="center" colspan="2"><input type="text" class="caja" id="txtNumTelefonoDT" name="txtNumTelefonoDT" maxlength="20" size="60"/> <label for="txtNumTelefonoDT"></label></td>
                   <td colspan="2" class="fontD">(en caso de no contar con &eacute;ste, el de un familiar o vecino)
                   </td>
                   
              </tr>
           
               <tr> 
                  <td class="fontD" colspan="2" align="right">Tel&eacute;fono celular:</td>
                  <td align="center" colspan="2"><input type="text" class="caja" id="numCelular" name="numCelular" maxlength="20" size="60"/><label for="numCelular"></label>
                  </td>
                  <td></td>
                   <td></td>   
               </tr>
                <tr> <td> <br/></td> 
                </tr>
               <tr>
                <td colspan="2" class="fontD" align="right"><span class="required">* </span> Documento oficial con el que se identifica:</td>
                <td colspan="2" align="center">
                  <select  name="cmbCveTipodocumentoDT" id="cmbCveTipodocumentoDT" style="width: 280px;" class="fontD">
                  </select>
                   <label for="cmbCveTipodocumentoDT"></label>
                   <label id="cmbCveTipodocumentoDTLabel">
                </td>      
		       <td colspan="2" class="fontD" align="center">Podrá adjuntar archivo del tipo pdf, jpg, png, bmp y gif con un tamaño no mayor a 1 MB </td>
                <td colspan="2">
                 </td>         
             </tr>
               <tr> <td> <br/></td> </tr>
                                       <tr>
                            <td colspan="2" class="fontD" align="right" id="txtDesUploadLabel">
				                  Adjuntar el documento de identificaci&oacute;n en forma digital 
				            </td>
                       		<td align="center" colspan="2">
                            	 <input type="text" class="caja" id="txtDesUpload" disabled="true" readonly="readonly"  name="txtDesUpload" maxlength="50" size="40" />
                            </td>
                            <td class="fontD" colspan="2"> 
                               <input type="button" id="uploaderTrabajador" class="mboton" value="Adjuntar"   />                
                            </td>                            
                             <td>
                				<input type="button" value="Ver" id="btnCargar1" name="btnCargar1" onclick="cargarDocVista(1);" class="mboton" >
                			</td>  
                        </tr>
                        <tr>
                            <td><input id="txtRefDocDT" type="text" name="txtRefDocDT"maxlength="80" si /></td>                           
                        </tr>
               
                     
<!--            <tr> 
               <td class="fontD" colspan="2">N&uacute;mero del documento de identificaci&oacute;n:</td>   
               <td  align="center" colspan="2"><input class="caja" id="numDocumento" type="text" name="numDocumento"maxlength="25" size="60" /></td>      
               <td><label for="numDocumento"  /></td>
               <td></td>                
            </tr> -->
            <tr> <td> <br/></td> </tr>  
          </table>
<!--                      <table >
                        <tr>
                            <td width=255px>
				            <label id="txtDesUploadLabel" width=255px>
				                  Adjuntar el documento de identificaci&oacute;n en forma digital </label>
				            </td>
                       		<td>
                            	 <input type="text" class="caja" id="txtDesUpload" disabled="true" readonly="readonly"  name="txtDesUpload" maxlength="50" size="20" />
                            </td>
                            <td> 
                               <input type="button" id="uploaderTrabajador" class="mboton" value="Adjuntar"   />                
                            </td>                            
                             <td>
                				<input type="button" value="Ver" id="btnCargar1" name="btnCargar1" onclick="cargarDocVista(1);" class="mboton" >
                			</td>  
                        </tr>
                        <tr>
                            <td><input id="txtRefDocDT" type="text" name="txtRefDocDT"maxlength="80" si /></td>                           
                        </tr>
                    </table> -->     


        <table width="100%">
            <tr>
                <td align="center" width="100%">
                    <div class="menu_principal" style="height: 2em !important;">
                    <!--inicia menu principal-->
                    <div align="center">
                        <div class="centrado">
                            <ul style="height: 1em !important;">
                                <li><a> MOTIVO DE LA DENUNCIA </a>
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
        <table width="100%">
          <tr> <td> <br/></td> </tr>    
            <tr>
                <td align="center" width="100%" nowrap>
                    <legend id="msgMotivosDenuncia"><strong>Hechos o razones que dan motivo a la denuncia </strong></legend>
                </td>
            </tr>
              <tr> <td> <br/></td> </tr>    
        </table>
        <table style="width: 100%" align="center" border="1">
             <tr>
                <td colspan="6" class="etiqueta2" nowrap>
                
                     <label for="md" generated="true" class="error"></label>
                </td>
                 
             </tr>    
            <tr>
                <td colspan="2" class="fontD" nowrap>
                     <input type="checkbox" id="md1" name="md" value="1" class="etiqueta2" onclick="habilitaMotivosDenuncia(this);"> El patr&oacute;n no lo afili&oacute; al Seguro Social</input>
                     
                </td>
                
                <td class="fontD">
                        <span id="fechaval" name="fechaval" class="required"> </span>  Fecha de no afiliación Del 
                </td>                
                <td  align="center">
					 <input type="text" class="caja" id="md1fechaInicio"  readonly="true"  name="md1fechaInicio"  onchange="javascript:validaFechaFinDeNoAfiliacion();" value="" size="20" maxlength="10" disabled="disabled"   />
               		 
                </td>
                 <td  class="fontD"  align="center">
                    Al
                 </td>
                 <td align="center">
                    <input type="text" id="md1fechaFin" class="caja" name="md1fechaFin" readonly="true" onchange="javascript:validaFechaFinDeNoAfiliacion();" size="20" maxlength="10" disabled="disabled"/>
                 </td> 
             </tr>                   
             <tr> <td colspan="2"> </td><td colspan="2"><label for="md1fechaInicio"  /> </td>  <td colspan="2"> <div id="labelfechaFinNoAfil"></div></td></tr>
             <tr> <td colspan="6"> <br/></td> </tr>
              
              <tr>
                <td colspan="2" class="fontD">
                             <input type="checkbox" id="md2" name="md" value="2" onclick="habilitaMotivosDenuncia(this);"> El patr&oacute;n lo afili&oacute; con fecha posterior a la de ingreso</input>
                </td>
               
                <td class="fontD">
                     <span id="fechaval2" name="fechaval2" class="required"> </span>  Fecha de ingreso al trabajo
                </td>
                
                <td align="center">
                    <input type="text" class="caja" id="md2fechaInicio" onchange="javascript:validaFecFinAfiliacionPost();" readonly="true" name="md2fechaInicio" value="" size="20" maxlength="10" disabled="disabled"  />                  
                </td>
             <td class="fontD" align="center">
                        Fecha de afiliación
                 </td>
               <td align="center">
                    <input type="text" class="caja"  id="md2fechaFin"  readonly="true" name="md2fechaFin" onchange="javascript:validaFecFinAfiliacionPost();" size="20" maxlength="10"  disabled="disabled"/>
                     
                 </td>
             </tr>
			  <tr> <td colspan="2"> </td><td colspan="2"><label for="md2fechaInicio"  /> </td>  <td colspan="2"> <div id="labelfechaFinAfilPost"></div></div></td></tr>                   
              <tr> <td colspan="6"> <br/></td> </tr>
              <tr>
                <td colspan="2" class="fontD">
                             <input type="checkbox" class="etiqueta2" id="md3" name="md" value="3" onclick="habilitaMotivosDenuncia(this);"> El patr&oacute;n lo afili&oacute; con un salario inferior al real</input>
                </td>
               
                 <td class="fontD">
                      <span id="salarios" name="salarios" class="required"> </span>  Salario registrado ante el IMSS
                </td>
                
                <td align="center">
                    <input type="text" class="caja" style="text-align: right; padding-right:5px" id="md3ImporteImss" name="md3ImporteImss" value="" size="20" maxlength="10"  disabled="disabled" />
                     <label for="md3ImporteImss"  />
                </td>
               <td class="fontD" align="center">
                        Salario Real
                 </td>
                 <td align="center">
                    <input type="text"  style="text-align: right; padding-right:5px" class="caja" id="md3ImporteReal" name="md3ImporteReal" value="" size="20" maxlength="10" disabled="disabled"/>
                 </td> 
             </tr>      
                   <tr> <td colspan="6"> <br/></td> </tr> 
             <tr>
                 <td colspan="2" class="fontD">
                       <input type="checkbox" class="etiqueta2"  id="md4" name="md" value="4" onclick="habilitaMotivosDenuncia(this);"> El patr&oacute;n se niega a presentar el aviso de baja del trabajador, cuando ya no labora con &eacute;l </input>
                </td>
               
               <td class="fontD">
                  <span id="fechaval3" name="fechaval3" class="required"> </span>      Fecha en la que dej&oacute; de laborar con el patr&oacute;n
                </td>                
                <td align="center">
                    <input type="text" id="md4fechaInicio" class="caja" readonly="true" name="md4fechaInicio"  maxlength="10" disabled="disabled"  />
                    <label for="md4fechaInicio"  />
                </td>
                <td> &nbsp;
                </td>
                 <td>
                    &nbsp;
                 </td>  
             </tr>
                   
              <tr> <td colspan="6"> <br/></td> </tr>
             <tr>
                <td colspan="6" width="100%" class="fontD">Observaciones (Anote las aclaraciones o manifestaciones que considere necesario comentarse)</td>
             </tr>
             <tr>
                <td colspan="6" width="100%" align="center"><textarea class="cajita"  onchange="conMayusculas(this);" name="txtObs" maxlength="2500"  id="txtObs" rows="5" cols="90" style="text-transform: uppercase; height: 30px; width: 800px"></textarea> </td>
             </tr>        
              <tr> <td colspan="6"> <br/></td> </tr>
        </table>
        <div id="divDatosBeneficiario"> 
       <table width="100%">
            <tr>
                 <td width="100%"> <div class="menu_principal" style="height: 2em !important;">
                    <!--inicia menu principal-->
                    <div align="center">
                        <div class="centrado">
                            <ul style="height: 1em !important;">
                                <li><a> DATOS DEL BENEFICIARIO (cuando &eacute;ste es el denunciante) </a>
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
         <table style="width: 100%" align="center" name="tblDatosB" id="tblDatosB">
          <tr>
                    <td width="20%"></td>
                    <td width="16%"></td>
                    <td width="16%"></td>
                    <td width="16%"></td>
                    <td width="16%"></td>
                    <td width="16%"></td>
               </tr>
           <tr>
                  <td align="right"  class="fontD" colspan="2" ><span class="required">* </span> Nombre:</td>                                  
                  <td align="center"><input id="txtDesPaternoBDT" type="text" name="txtDesPaternoBDT" style="text-transform: uppercase;" maxlength="50" class="caja" size="30"/><label for="txtDesPaternoBDT"  /></td>
                  <td align="center"><input id="txtDesMaternoBDT" type="text" name="txtDesMaternoBDT" style="text-transform: uppercase;" maxlength="50" class="caja" size="30"/><label for="txtDesMaternoBDT"  /></td>
                  <td align="center"><input id="txtDesNombreBDT" type="text" name="txtDesNombreBDT" style="text-transform: uppercase;" maxlength="50" class="caja" size="30"/><label for="txtDesNombreBDT"  /></td>
                  <td></td>
               </tr>
               
               <tr>
                    <td></td>
                    <td></td>                    
                    <td align="center" class="fontD">Apellido Paterno</td>
                    <td align="center" class="fontD">Apellido Materno</td>
                    <td align="center" class="fontD">Nombre</td>
                    <td></td>
               </tr>
       
              <tr> <td> <br/></td> </tr>
               <tr> 
                  <td class="fontD" colspan="2" align="right">Direcci&oacute;n:</td>
                  <td colspan="2" align="center"><textarea class="caja" id="direccionBeneficiario" name="direccionBeneficiario" readonly="readonly" size="100" cols="20" rows="3" style="width: 270px;height: 40px;" ></textarea><label for="direccionBeneficiario"  /> </td>
                  <td colspan="1"  align="center">
                     <input type="button" id="ubicar1" name="ubicar1"  class="mboton" value="Agregar Direcci&oacute;n" onkeypress="setPersona('2');" onclick="setPersona('2');"/>   
                  </td>     
                   <td>
                      	 <input type="button" id="quitar1" name="quitar1"  class="mboton" value="Quitar Direcci&oacute;n" onclick="borraDomicilio(2)"/>  
                   </td>
                         
              </tr>   
             <tr> <td> <br/></td> </tr>
               <tr>
                <td colspan="2" class="fontD" align="right"><span class="required">* </span> Documento oficial con el que se identifica.</td>  
                <td colspan="2" align="center">
                  <select  name="cmbCveTipodocumentoBDT" id="cmbCveTipodocumentoBDT" class="fontD" style="width: 280px">
                    <option value="-1">--Seleccione Identificaci&oacute;n--</option>
                    <option value="1">Credencial de Elector</option>
                    <option value="2">Cartilla del Servicio Militar Nacional</option>
                    <option value="3">C&eacute;dula Profesional</option>
                    <option value="4">ADIMSS</option>
                    <option value="5">Documento que acredita la </option>
                  </select>
                  <label for="cmbCveTipodocumentoBDT"/>
                </td>          
				<td colspan="2" class="fontD" align="center">Podrá adjuntar archivo del tipo pdf, jpg, png, bmp y gif con un tamaño no mayor a 1 MB </td>

             </tr>
               <tr> <td> <br/></td> </tr>
                         <tr>
                            <td class="fontD" align="right" colspan="2" id="txtDesUploadBeneficiarioLabel">
				                                       Adjuntar el documento de identificaci&oacute;n en forma digital 
				            </td>
                            <td align="center" colspan="2">
                            	<input type="text" class="caja" id="txtDesUploadBeneficiario"  readonly="readonly"  disabled="true" name="txtDesUploadBeneficiario" maxlength="50" size="40" />
                            </td>
                            <td class="fontD" colspan="2"> 
                                <input type="button" id="uploaderBeneficiario" class="mboton" value="Adjuntar"   />                
                            </td>
                             <td>
                				<input type="button" value="Ver" id="btnCargar2" name="btnCargar2" onclick="carDocVista(2);" class="mboton" >
                			</td>  
<!--  		                    <td><input id="txtRefDocBDT" type="text" name="txtRefDocBDT"maxlength="80" /></td>-->                           
		                </tr>

                  <tr> <td> <br/></td> </tr>
         </table>
         </div>
         <div id="divDatosRepLegal">
    <table width="100%">
            <tr>
               <td width="100%"> <div class="menu_principal" style="height: 2em !important;" >
                    <!--inicia menu principal-->
                    <div align="center">
                        <div class="centrado">
                            <ul style="height: 1em !important;">
                                <li><a> DATOS DEL REPRESENTANTE LEGAL (cuando &eacute;ste es el denunciante) </a>
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
         <table style="width: 100%" align="center" name="tblDatosRL" id="tblDatosRL">
          <tr>
                    <td width="20%"></td>
                    <td width="16%"></td>
                    <td width="16%"></td>
                    <td width="16%"></td>
                    <td width="16%"></td>
                    <td width="16%"></td>
               </tr>
           <tr>
                  <td colspan="2" align="right"  class="fontD" ><span class="required">* </span> Nombre:</td>
                                   
                  <td align="center"><input id="txtDesPaternoRLDT" class="caja" type="text" name="txtDesPaternoRLDT" style="text-transform: uppercase;" maxlength="50" size="30"/><label for="txtDesPaternoRLDT"  /></td>
                  <td align="center"><input id="txtDesMaternoRLDT" class="caja" type="text" name="txtDesMaternoRLDT" style="text-transform: uppercase;" maxlength="50" size="30"/><label for="txtDesMaternoRLDT"  /></td>
                  <td align="center"><input  id="txtDesNombreRLDT" class="caja" type="text" name="txtDesNombreRLDT" style="text-transform: uppercase;" maxlength="50" size="30"/><label for="txtDesNombreRLDT"  /></td>
                  <td></td>
               </tr>
               
               <tr>
                    <td></td>
                    <td></td>
                    <td align="center"  class="fontD">Apellido Paterno</td>
                    <td align="center" class="fontD">Apellido Materno</td>
                    <td align="center"  class="fontD">Nombre</td>
                    <td></td>
               </tr>
         <tr> <td> <br/></td> </tr>
             <tr> 
                  <td class="fontD" colspan="2" align="right">Direcci&oacute;n:</td>
                  <td colspan="2" align="center"><textarea rows="3" cols="20" class="caja" id="direccionRepLegalDT" name="direccionRepLegalDT" readonly="readonly"  style="width: 270px;height: 40px;" ></textarea><label for="direccionRepLegalDT"  />  </td>
                  <td colspan="1"  align="center">
                     <input type="button" id="ubicar2" name="ubicar2"  class="mboton" value="Agregar Direcci&oacute;n" onkeypress="setPersona('3');" onclick="setPersona('3');"/>   
                  </td>     
                   <td>
                    <input type="button" id="quitar2" name="quitar1"  class="mboton" value="Quitar Direcci&oacute;n" onclick="borraDomicilio(3)"/>  
                   </td>       
              </tr>
             
               <tr> <td> <br/></td> </tr>
      
               <tr>
                <td colspan="2" class="fontD" align="right"><span class="required">* </span> Documento oficial con el que se identifica.</td>  
                <td colspan="2" align="center">
                  <select  name="cmbCveTipodocumentoRLDT" id="cmbCveTipodocumentoRLDT" class="fontD" style="width: 280px">
                    <option value="-1" class="fontD" >--Seleccione Identificaci&oacute;n--</option>
                    <option value="1" class="fontD">Credencial de Elector</option>
                    <option value="2" class="fontD">Cartilla del Servicio Militar Nacional</option>
                    <option value="3" class="fontD">C&eacute;dula Profesional</option>
                    <option value="4" class="fontD">ADIMSS</option>
                    <option value="5" class="fontD">Documento que acredita la </option>
                  </select>
                  <label for="cmbCveTipodocumentoRLDT"  />
                </td>
                	<td colspan="2" class="fontD" align="center">Podrá adjuntar archivo del tipo pdf, jpg, png, bmp y gif con un tamaño no mayor a 1 MB </td>
                <td colspan="2">
                 </td>         
             </tr>
               <tr> <td> <br/></td> </tr>
                       <tr>                
                
                            <td colspan="2" class="fontD" align="right" id="txtDesUploadRPLabel">
				              Adjuntar el documento de identificaci&oacute;n en forma digital 
				            </td>
                        	<td align="center" colspan="2">
                            	 <input type="text" class="caja" id="txtDesUploadRP" readonly="readonly"   disabled="true" name="txtDesUploadRP" maxlength="50" size="40" />
                            </td>
                            <td class="fontD" colspan="2">  
                              	 <input type="button" id="uploaderRepLegal" class="mboton" value="Adjuntar"   />                
                            </td>
                             <td>
                				<input type="button" value="Ver" id="btnCargar3" name="btnCargar3" onclick="cargarDocVista(3);" class="mboton" >
                			</td>
                        </tr>

                 </td>            
             </tr>
              <tr> <td> <br/></td> </tr>
          <!--    <tr><td class="fontD" colspan="2" align="right">Número del documento de identificaci&oacute;n</td>
                  <td align="center" colspan="2"><input  type="text"  class="caja"  id="txtNumDocumentoRLDT" name="txtNumDocumentoRLDT" maxlength="25" size="60"/><label for="txtNumDocumentoRLDT"  />
                     
                      </td>
                      <td></td>
                      <td></td>
                      </tr> -->
         </table>
    
       </div>  
            <table style="width: 100%;" class="tablaverde2">       
          <tr>
                <td align="center" width="100%" colspan="6">
                       <div class="menu_principal" style="height: 2em !important;">
                    <!--inicia menu principal-->
                    <div align="center">
                        <div class="centrado">
                            <ul style="height: 1em !important;">
                                <li><a> DATOS DEL TRABAJADOR (PASO 1 DE 3) </a>
                                </li>
                            </ul>
                        </div>
                        <!--fin centrado-->
                    </div>
                </div>
                 </td>
          </tr> 
          <tr> <td> <br/></td> </tr>
           <tr> <td> <br/></td> </tr> 
           </table>
</div>

