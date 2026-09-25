<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<html>

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/delta/denuncia/denunciaLinea.js"></script>	
		
<div id="datosPatron" class="form-comment" align="left">

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

<form:form commandName="denunciaDTO"  action="/denunciaLinea/datosPatron/guardarDatosPatron.do" method="post" id="datosPatronForm" name="datosPatronForm">     
    <form:input type="hidden" path="paso" id="hdIdPasoDenuncia" name="hdIdPasoDenuncia" value="2"/>
     <fieldset style="align:center"  style="width: 977px">	 
		  <table style="width: 100%;" class="tablaverde2">       
          <tr>
             <td align="center" width="100%" colspan="4">            
                   <legend><strong>Informaci&oacute;n que deber&aacute; proporcionar el denunciante, beneficiario o encargado de representarlo</strong></legend>
              </td>
          </tr>
           <tr> <td> <br/></td> </tr>
          <tr>
                <td align="center" width="100%" colspan="4">
                       <div class="menu_principal" style="height: 2em !important;">
                    <!--inicia menu principal-->
                    <div align="center">
                        <div class="centrado">
                            <ul style="height: 1em !important;">
                                <li><a> DATOS DEL PATRON (PASO 2 DE 3) </a>
                                </li>
                            </ul>
                        </div>
                        <!--fin centrado-->
                    </div>
                </div>
                 </td>
          </tr> 
           <tr> <td> <br/></td> </tr>
           <tr>
                <td align="center" colspan="4">Proporcionar la siguiente informaci&oacute;n: Obligatorio <span class="required">*</span></td>
           </tr>        
            <tr> <td> <br/></td> </tr>
           </table>
		
	  </fieldset>
	  
	  <fieldset style="width: 977px" class="titulo">
         <legend><strong>DEL PATR&Oacute;N SUJETO U OBLIGADO</strong></legend>
         <table style="width: 100%" align="left">
              <tr> <td> <br/></td> </tr>
              <tr> <td> ¿Usted ha denunciado a este patr&oacute;n con anterioridad? </td> 
                   <td> <form:input path="dltPatron.indPatrondenunciado" type="radio" name="denunciadoAntes" id="denunciadoAntes" value="0"/>No <form:input path="dltPatron.indPatrondenunciado" type="radio" name="denunciadoAntes" id="denunciadoAntes" value="1"/>S&iacute; </td> 
              </tr>
              <tr> <td> <br/></td> </tr>
              <tr> 
				<td><span class="required">* </span> Nombre o Raz&oacute;n Social del patr&oacute;n:</td>			
				<td>
				  <form:input path="dltPatron.desNomrazonsocial" id="desNomrazonsocial" name="desNomrazonsocial" size="40" maxlength="40" onkeyup="conMayusculas(this);"/>
				  <label id="razonSocial"></label>
				</td>			   
			  </tr>
			 
			  <tr> <td><span class="required">* </span> Domicilio del centro de trabajo:</td>			
				 <td>
				   <input type="text" name="domicilioTrabajo" id="domicilioTrabajo" size="40" maxlength="40" />
				    <label id="lblDomTrabajo"></label>
				 </td>				   
			  </tr>
              <tr> <td> <br/></td> </tr>
               <tr align="left">
                  <td> Nombre del representante legal del patr&oacute;n, en caso de conocerlo: </td>
               </tr>
               <tr> <td> <br/></td> </tr>
                <tr align="left">
                <td colspan="3">
                 <table><tr>
                  <td align="left">(Nombre, Apellido Paterno, Apellido Materno):
                      <form:input path="dltPatron.desNomreplegal" id="desNomreplegal" name="desNomreplegal" onkeyup="conMayusculas(this);" />                     
                  </td>
                  </tr>
                  </table>
                  </td>
              </tr>
              <tr> <td> <br/></td> </tr>
              <tr align="left">
                  <td colspan="1"><span class="required">* </span> Giro o Actividad del patr&oacute;n:
                  </td>
                  <td>
                       <select id="selGiroActividad" name="selGiroActividad">
							<option value="-1">--Seleccione Giro o Actividad--</option>
							<option value="1">Comercio</option>
					   </select>
					    <label id="lblGiro"></label>
                  </td>
              </tr> 
              <tr> <td> <br/></td> </tr>
              <tr align="left">
                  <td colspan="1"><span class="required">* </span> Sector:</td>
                  <td>
                       <select id="selSector" name="selSector">
							<option value="-1">--Seleccione Sector--</option>
							<option value="1"> Abarrotes</option>
					   </select>
                  </td>
              </tr> 
              <tr> <td> <br/></td> </tr>
              <tr> 							   
				<td> RFC del patr&oacute;n , en caso de conocerlo: </td>
				<td>  
				    <form:input path="dltPatron.desRfc" id="desRfc" name="desRfc" />
				</td>			   							   
			 </tr>
			 <tr> 							   
				<td> N&uacute;mero de Registro Patronal, en caso de conocerlo: </td>
				<td>  
				    <form:input path="dltPatron.cveRegpat" id="cveRegpat" name="cveRegpat" size="11" maxlength="11"  />
				</td>			   							   
			 </tr>
			 <tr> <td> <br/></td> </tr>
			  <tr> 							   
				<td><span class="required">* </span> N&uacute;mero aproximado de Trabajadores que laboran: </td>
				<td>
				    <form:input path="dltPatron.numTrabajadores" id="numTrabajadores" name="numTrabajadores" />
				     <label id="lblNumTrabajadores"></label>
				</td>			   							   
			 </tr>
			 <tr> <td> <br/></td> </tr>
			 <tr> 							   
				<td> Domicilio fiscal de la empresa o persona f&iacute;sica, en caso de conocerlo:  </td>
				<td>
					<form:input path="dltPatron.domicilioId" id="domicilioId" name="domicilioId" />
				</td>			   							   
			 </tr>
			 <tr> <td> <br/></td> </tr>
			  <tr> 							   
				<td> Tel&eacute;fono de la empresa o persona f&iacute;sica, en caso de conocerlo: </td>
				<td>
					<form:input path="dltPatron.numTelefono" id="numTelefono" name="numTelefono" />
				</td>			   							   
			 </tr>
			 <tr><td> Observaciones: (Anote aqu&iacute; las aclaraciones o manifestaciones que considere necesario comentar)</td></tr>
			 <tr> <td> <br/></td> </tr>
			 <tr><td colspan="2"><form:textarea path="dltDenuncia.desObservaciones" name="desObservaciones" id="desObservaciones"/> </td>
			 <tr> <td> <br/></td> </tr>
			 <tr> 							   
				<td>¿Usted recibe el total de su sueldo de un solo patr&oacute;n? </td>
				<td> <form:input path="patronPrincipal" type="radio" name="patronPrincipal" id="patronPrincipal" value="false" onclick="muestraAgregaPC();"/>No <form:input path="patronPrincipal" type="radio" name="patronPrincipal" id="patronPrincipalY" value="true"/>S&iacute;</td>
				<td>
					<button type="button" name="cargaPatCmpl" id="cargaPatCmpl" class="mboton" onclick="agregaPatron()">Agregar patr&oacute;n </button>
				</td>						   							  
			 </tr>
			 <tr> <td> <br/></td> </tr>
			 <tr align="left">
			   
			   <td colspan="4">			   			   
					<table name="tblPatronCmpl" id="tblPatronCmpl"> 					 
					   <th> &nbsp;&nbsp;Nombre o Raz&oacute;n social del patr&oacute;n complementario &nbsp;&nbsp;</th>
					   <th> &nbsp;&nbsp;Patr&oacute;n Principal &nbsp;&nbsp;</th>
					   <th> &nbsp;&nbsp; &nbsp;&nbsp;</th>
					   <th> &nbsp;&nbsp;&nbsp;&nbsp;</th>
					</table>
				</td>	
			 </tr>		           						        
			 </tr>
 		</table>               
       </fieldset>
	  
	   <fieldset style="align:center">
		<table align="left" >		  
		  <tr>
			 <td align="left" >			
					<table >
						<tbody align="left" >						 
							<tr align="center"width="900px">
							  <td> A CONTINUACIÓN USTED DEBE HACER CLICK EN UNA DE LAS SIGUIENTES OPCIONES. SI HACE CLICK EN SALIR SIN GUARDAR SUS DATOS, LA INFORMACIÓN CAPTURADA NO SERÁ REGISTRADA
							  </td>					    
							</tr>		
							<tr> <td> <br/></td>
							</tr>					
							<tr>
						     <td align="center" width="900px">	
						            <button type="button" onclick="abrirDialogoConfirmacion();" id="buttonSiguiente" class="mboton">Guardar</button>									    									
									<button type="button"  id="buttonSalirDatosTrabajador" class="mboton" onclick="abrirDialogoSalirSinGuardar();">Salir</button>
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
<div>
  <jsp:include page="guardarDenuncia.jsp" />
</div>
<div> 
 <jsp:include page="confirmacionSalirSinGuardar.jsp"/>
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
