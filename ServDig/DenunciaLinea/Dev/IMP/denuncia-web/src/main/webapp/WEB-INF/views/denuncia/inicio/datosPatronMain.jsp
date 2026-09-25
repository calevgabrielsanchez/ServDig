<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
    "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

		 
<html>
<div id="domicilioUbicar3"></div>
<div id="domicilioUbicar4"></div>
<div id="domicilioUbicar5"></div>
<div id="datosPatron" class="form-comment" align="left">   
 <input id="hdIdPatronPrincipal" name="hdIdPatronPrincipal" type="hidden" />
 <br>
 <br>
 <table style="width: 100%;" class="tablaverde2">
	<tr>
       <td align="center" width="100%" colspan="2">
         <div class="menu_principal" style="height: 2em !important;">
                    <!--inicia menu principal-->
                    <div align="center">
                        <div class="centrado">
                            <ul style="height: 1em !important;">
                                <li><a> DATOS DEL PATR&Oacute;N (PASO 2 DE 3) </a>
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
<table style="width: 100%;" class="tablaverde2" align="center">
    <tr>
        <td width="24%"></td>
        <td width="25%"></td>
        <td width="25%"></td>
        <td width="26%"></td>
    </tr>
    <tr align="center"> 
       <td align="right" class="fontD" colspan="2"> ¿Has denunciado a este patr&oacute;n ante el IMSS con anterioridad? <span class="required">* </span></td> 
        <td align="center">
             <table>
              <tr>
                <td width="50px" class="fontD" ><input type="radio" name="rdDenunciadoAntesDP" id="rdDenunciadoAntesDP" value="1" />S&iacute;</td>
                <td width="50px" class="fontD" ><input type="radio" name="rdDenunciadoAntesDP" id="rdDenunciadoAntesDP" value="0" />No</td>
              </tr>
              </table> 
        </td>        
    </tr>
    <tr> <td> <br/></td> </tr>
    <tr>
       <td class="fontD" align="right" colspan="2"> Nombre o Raz&oacute;n Social del patr&oacute;n: <span class="required">* </span></td>
       <td align="center">
		 <input type="text" id="txtDesNomrazonsocialDP" name="txtDesNomrazonsocialDP" maxlength="100" style="text-transform: uppercase;" class="caja" size="60" maxlength="100"/>		 
	    </td>		    
	    <td></td>
	    <td></td>
    </tr>
    <tr>
       <td align="right" class="fontD" colspan="2">  N&uacute;mero de Registro Patronal, en caso de conocerlo: &nbsp;&nbsp;&nbsp;</td>
       <td align="center">
		 <input type="text" id="txtCveRegpatDP" name="txtCveRegpatDP" maxlength="11" style="text-transform: uppercase;" class="caja" size="60"/>
	    </td>
	   <td align="left" class="fontD" colspan="2">  &nbsp;&nbsp; Es importante contar con este dato para una &aacute;gil  &nbsp;&nbsp;localización del patr&oacute;n</td> 		    
    </tr>
    <tr> 
      <td align="right" colspan="2"></td>
      <td align="center" colspan="2"><label for="txtDesNomrazonsocialDP"  id="lbltxtDesNomrazonsocialDP"></label></td>
    </tr>
    <tr>
       <td align="right" class="fontD" colspan="2"> Domicilio del centro de trabajo: </td>
       <td align="center">
		  <textarea rows="3" cols="20" style="height: 40px; width: 280px" id="txtDomicilioTrabajoDP" name="txtDomicilioTrabajoDP"  class="caja" size="60" readonly="readonly" ></textarea>				 
	    </td>	
	    	 <td colspan="1"  align="center">
                     <input type="button" id="ubicar3" name="ubicar3"  class="mboton" value="Agregar Direcci&oacute;n" onkeypress="setPersona('4');" onclick="setPersona('4');"/>   
                  </td> 
                  <td>
                   	 <input type="button" id="quitar" name="quitar"  class="mboton" value="Quitar Direcci&oacute;n" onclick="borraDomicilio(4)"/>
                  </td>        
    </tr>
    <tr> 
      <td align="right" colspan="2"></td>
      <td colspan="2"> <label for="txtDomicilioTrabajoDP"  id="lbltxtDomicilioTrabajoDP"></label></td>
    </tr>
    
    <tr>
       <td align="right" class="fontD" colspan="2"> Nombre del representante legal del patr&oacute;n, en caso de conocerlo: &nbsp;&nbsp;&nbsp;</td>
       <td align="center">
		 <input type="text" id="txtDesNomreplegalDP" name="txtDesNomreplegalDP" maxlength="150" style="text-transform: uppercase;" class="caja" size="60"/>
	    </td>		    
    </tr>
       <tr> <td> <br/></td> </tr>
        <tr>
       <td align="right" class="fontD" colspan="2">  Sector:  <!--  <span class="required">* </span>--></td>
      <td align="center" >
		 <select  name="cbxSelSectordDP" id="cbxSelSectordDP" class="fontD" width="280px"  style="width: 280px" >
		     <option value="-1" >--Seleccione Sector--</option>			
		 </select>	
	    </td>		    
    </tr>
    
     <tr>
       <td align="right" class="fontD" colspan="2"> </td>
      <td align="center">
		 <label for="cbxSelSectordDP"  />		
     </td>		    
    </tr>
    
    
     <tr> <td> <br/></td> </tr>
    <tr>
       <td align="right" class="fontD" colspan="2">  Giro o Actividad del patr&oacute;n:  <!--  <span class="required">* </span>--> </td>
      <td align="center">
		 <select  name="cbxSelGiroActividadDP" id="cbxSelGiroActividadDP" class="fontD" width="280px" style="width: 280px" >
		     <option value="-1">--Seleccione Giro o Actividad--</option>			
		 </select>			 
	    </td>		    
    </tr>
   <tr>
       <td align="right" class="fontD" colspan="2"></td>
      <td align="center" colspan="2">
		 <label for="cbxSelGiroActividadDP"  />	
	    </td>		    
    </tr>
    
     <tr> <td> <br/></td> </tr>
    <tr>
       <td align="right" class="fontD" colspan="2">  RFC del patr&oacute;n , en caso de conocerlo: &nbsp;&nbsp;&nbsp;</td>
       <td align="center">
		 <input type="text" id="txtRfcPatronDP" name="txtRfcPatronDP" maxlength="13" style="text-transform: uppercase;" class="caja" size="60"/>
	    </td>		    
    </tr>
    <tr> 
      <td colspan="2">&nbsp;&nbsp;&nbsp;</td>
      <td colspan="2"><label for="txtRfcPatronDP"  id="lbltxtRfcPatronDP"></label></td>
    </tr>
   <tr> <td> <br/></td> </tr>
<!--      <tr>
       <td align="right" class="fontD" colspan="2">  N&uacute;mero de Registro Patronal, en caso de conocerlo: &nbsp;&nbsp;&nbsp;</td>
       <td colspan="2" align="left">
		 <input type="text" id="txtCveRegpatDP" name="txtCveRegpatDP" maxlength="11" style="text-transform: uppercase;" class="caja" size="60"/>
	    </td>		    
    </tr> -->
      <tr> <td> <br/></td> </tr>
    <tr>
       <td align="right" class="fontD" colspan="2"> N&uacute;mero aproximado de Trabajadores que laboran:</td>
       <td align="center">
		 <input type="text" id="txtNumTrabajadoresDP" name="txtNumTrabajadoresDP" maxlength="4" class="caja" size="60"/>		 
	    </td>		    
    </tr>
    <tr> <td> <br/></td> </tr>
     <tr> 
      <td colspan="2"></td>
      <td colspan="2"><label for="txtNumTrabajadoresDP"  ></label></td>
    </tr>
<!--     <tr>
       <td align="right" class="fontD" colspan="2"> Domicilio fiscal de la empresa o persona f&iacute;sica, en caso de conocerlo:  &nbsp;&nbsp;&nbsp;</td>
       <td colspan="1">
		 <textarea rows="3" cols="20" style="height: 40px; width: 280px" id="txtDomFiscalPtrIdDP" name="txtDomFiscalPtrIdDP" style="text-transform: uppercase;" class="caja" size="60" readonly="readonly" ></textarea>
	    </td>
	     <td colspan="1"  align="center">
                     <input type="button" id="ubicar4" name="ubicar4"  class="mboton" value="Agregar Direcci&oacute;n" onkeypress="setPersona('5');" onclick="setPersona('5');"/>   
         </td>   	
          <td>
                   	 <input type="button" id="quitar" name="quitar"  class="mboton" value="Quitar Direcci&oacute;n" onclick="borraDomicilio(5)"/>
                  </td>  	    
    </tr>  -->
    <tr> <td> <br/></td> </tr>
    <tr>
       <td align="right" class="fontD" colspan="2"> Tel&eacute;fono de la empresa o persona f&iacute;sica, en caso de conocerlo 12 N&uacute;meros incluyendo clave LADA:</td>
       <td align="center">
		 <input type="text" id="txtNumTelefonoPatronDP" name="txtNumTelefonoPatronDP" maxlength="22" class="caja" size="60"/>
		 <label for="txtNumTelefonoPatronDP"  ></label>
	    </td>		    
    </tr>
    <tr> <td> <br/></td> </tr>
    <tr> 
       <td align="right" class="fontD" colspan="2"> ¿Usted recibe el total de su sueldo de un solo patr&oacute;n? &nbsp;&nbsp;&nbsp; </td> 
       <td align="center">
           <table>
                        <tr>
                                <td width="50px" class="fontD" ><input type="radio" name="rdPatronPrincipalDP" id="rdPatronPrincipalDP" value="1" onclick="ocultaAgregaPC();"/>S&iacute;</td>
                                <td width="50px" class="fontD" ><input type="radio" name="rdPatronPrincipalDP" id="rdPatronPrincipalDP" value="0" onclick="muestraAgregaPC();" />No</td>                               
                        </tr>
          </table>                
       </td> 
    </tr>
    <tr> <td> <br/></td> </tr>
    <tr >			   
	   <td align="center" width="100%" colspan="4">			   			   
			 <div class="menu_principal" style="height: 2em !important;">
                    <!--inicia menu principal-->
                    <div align="center">
                        <div class="centrado">
                            <ul style="height: 1em !important;">
<!--                                 <li><a> PATRONES SECUNDARIOS </a>
                                </li> -->
                                <li><a> SI RECIBES SUELDO DE DIFERENTES PATRONES EN EL MISMO TRABAJO INDICAR SU NOMBRE </a>
                                </li>                                
                            </ul>
                        </div>
                        <!--fin centrado-->
                    </div>
                </div>
		</td>	
	</tr>	
    <tr >
       <td colspan="4" >
          <table id="dtPatronesDenunciados"  >    
             <thead>
                   <tr>
                                <th colspan="1" rowspan="1" width="2%" align="center"></th>
                                <th style="text-align: center;" colspan="1" rowspan="1" width="10%" align="center">Raz&oacute;n Social</th>                                
                   </tr>                     
              </thead>
        </table>
     </td>    
    </tr>    
    <tr>
        <td colspan="4">
        <table>
            <tr>
                <td><button type="button" name="btnAgregaPatron" id="btnAgregaPatron" class="mboton" onclick="openDgAgregarPatronesSecundarios();" >Agregar </button></td>
                <td><button type="button" name="btnModificaPatron" id="btnModificaPatron" class="mboton" onclick="openDgModificarPatronesSecundarios();" >Modificar </button></td>
                <td><button type="button" name="btnEliminaPatron" id="btnEliminaPatron" class="mboton" onclick="eliminaPatronSecundario()" >Eliminar </button></td>
            </tr>
        </table>
        </td>
    </tr> 
     <tr> <td> <br/></td> </tr> 
	<tr>
     <td colspan="4" class="fontD"> Observaciones: (Anota aqu&iacute; las aclaraciones o manifestaciones que considere necesario comentar)</td>
    </tr>
    <tr><td colspan="4"> <div>
          <textarea id ="txtObsDP"  name="txtObsDP" rows="5" cols="90" style="text-transform: uppercase; height: 60px; width: 80%" maxlength="2500" onchange="conMayusculas(this);" style="text-transform: uppercase;"  class="cajita"></textarea></div>  
         </td>
    </tr>
   
   
</table>
<br>
 <br>
 <table style="width: 100%;" class="tablaverde2">
	<tr>
       <td align="center" width="100%" colspan="2">
         <div class="menu_principal" style="height: 2em !important;">
                    <!--inicia menu principal-->
                    <div align="center">
                        <div class="centrado">
                            <ul style="height: 1em !important;">
                                <li><a> DATOS DEL PATR&Oacute;N (PASO 2 DE 3) </a>
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

