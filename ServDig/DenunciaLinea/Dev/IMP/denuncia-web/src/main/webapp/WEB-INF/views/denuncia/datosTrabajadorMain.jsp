	<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/delta/denuncia/denunciaLinea.js">
	</script> 
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/delta/denuncia/datosTrabajador.js">
	</script>
	
<script type="text/javascript" src="<spring:url value="/static/resources/js/uploadify3/swfobject.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/uploadify3/jquery.uploadify-3.1.min.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/fileUpload/fileUpload.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/generales/capturaDocs.js" htmlEscape="true" />"></script>
		
<input type="hidden" id="hdFechaServidor" name="hdFechaServidor"/>

	
		
<div id="datosTrabajador" class="form-comment" align="left">	
     <fieldset style="align:center"  style="width: 977px">
        <table style="width: 100%" align="center">		  
		  <tr align="center" >
			 <td align="center">
      			Denuncia del trabajador, beneficiario(s) o su representante legal, en contra de su patr&oacute;n <br/>por no afiliarlo al IMSS, 
				por afiliarlo con un salario inferior al pagado o afiliarlo con <br/>una fecha posterior a la que realmente ingres&oacute; a trabajar IMSS-TYS0024
		     </td>
		   </tr>
		  </table> 
     </fieldset>
     
 <c:set var="contextpath" value="<%=request.getContextPath()%>" />
 <form:form commandName="denunciaDTO"  action="/denunciaLinea/datosTrabajador/guardarDatosTrabajador.do" method="post" id="datosTrabajadorForm" name="datosTrabajadorForm">
     <form:input type="hidden" path="paso" id="hdIdPasoDenuncia" name="hdIdPasoDenuncia" value="1"/>
     <form:input type="hidden" path ="enviar" id="hdEnviar" name="hdEnviar" value="0"/>	
     <fieldset style="align:center"  style="width: 977px">     
        <table style="width: 100%">		  
		  <tr>
			 <td align="center">			
				<table>
					<tbody align="left" >						 
						<tr valign="top">
						  <td align="center" colspan="3">
						        <legend><strong>Informaci&oacute;n que deber&aacute; proporcionar el denunciante, beneficiario o encargado de representarlo</strong></leyend>
						 	</td></tr>
						<tr valign="top"><td align="center" colspan="3"><h3 align="center"><strong>DATOS DEL TRABAJADOR <fmt:message key='datosTrabajador.tituloPantalla'/></strong></h3></td></tr>	
						<tr valign="top"><td align="center" colspan="3"><h3 align="center"><strong>PASO 1 de 3</strong></h3></td></tr>	
					    </tbody>
			      </table>
			  </td>
			</tr>
			<tr><td align="left">Proporcionar la siguiente informaci&oacute;n: Obligatorio <span class="required">*</span></td></tr>		
			<tr align= "left">
				<td>Folio de la denuncia:
				     <form:input path="dltDenuncia.cveFoliodenuncia" disabled="true"/> 
				</td>
				<td align="left">
				Presenta la denuncia como: &nbsp;&nbsp; 
					<form:select path="dltPersonaT.dlcTipodenunciante.cveTipodenunciante" id="cveTipodenunciante" name="cveTipodenunciante" onchange="activaFS();">
					   <option value="-1">--Seleccione--</option>
					   <option value="1">Trabajador</option>
					   <option value="2">Beneficiario</option>
					   <option value="3">Representante Legal</option>
					</form:select>
             	</td>  							   
			</tr>
			 <tr> <td> <br/></td> </tr>		
			 <tr> <td> <form:errors path="*" cssStyle="color: red;"/> </td> </tr>
		</table>
     </fieldset>
         
     <fieldset style="width: 977px" class="titulo">
         <legend><strong>DATOS DEL TRABAJADOR</strong></legend>
         <table style="width: 100%" align="left">
               <tr align="left">
                  <td> <span class="required">* </span> Nombre completo y correcto: </td>
               </tr>
               <tr> <td> <br/></td> </tr>
               <tr align="left">
                <td colspan="3">
                 <table><tr>
                  <td align="left">Nombre:&nbsp;
                    <form:input path="dltPersonaT.desNombre" id="desNombre" name="desNombre" onkeyup="conMayusculas(this);" maxlength="50"/>                    
                    </td>                  
                  <td>Apellido Paterno:&nbsp;
                  <form:input path="dltPersonaT.desPaterno" id="desPaterno" name="desPaterno" onkeyup="conMayusculas(this);" maxlength="50"/></td>
                  <td>Apellido Materno: &nbsp; 
                  <form:input path="dltPersonaT.desMaterno" id="desMaterno" name="desMaterno" onkeyup="conMayusculas(this);" maxlength="50"/></td>
                  </tr>
                  <tr> <td> <br/></td> </tr>
                  <tr> <td> <form:errors path="dltPersonaT.desNombre" cssStyle="color: red;"/> </td> </tr>
                   
                  </table>
                  </td>
              </tr>
              
              <tr> <td> <br/></td> </tr>
              <tr>
				  <td> ¿Cuenta con N&uacute;mero de Seguro Social?:</td>
			  </tr>
			  <tr> <td> <br/></td> </tr>
			  <tr>
				  <td align="center">
				  <input type="radio" name="nss" id="nssN" value="N">No <input type="radio" name="nss" id="nssS" value="S">S&iacute;
				  </td>				  
				  <td align="left">No. Afiliaci&oacute;n al Seguro Social: &nbsp;
				  <form:input path="dltPersonaT.cveNss" id="cveNss" name="cveNss" maxlength="11" size="11"/></td>
			  </tr>
			  <tr> <td> <br/></td> </tr>
			  <tr>
				  <td colspan="3"><span class="required">* </span> Clave &Uacute;nica de Registro de Poblaci&oacute;n (CURP):
                  &nbsp;
                   <form:input path="dltPersonaT.cveCurp" id="cveCurp" name="cveCurp" maxlength="18" size="18"/>
                   <form:errors path="dltPersonaT.cveCurp" cssStyle="color: red;"/>
                   </td>
			  </tr>
			  <tr> <td> <br/></td> </tr>
			  <tr> 							   
				  <td> Registro Federal de Contribuyentes (RFC):
				  &nbsp;
				  <form:input path="dltPersonaT.cveRfc" id="cveRfc" name="cveRfc" size="13" maxlength="13"/>
				  <form:errors path="dltPersonaT.cveRfc" cssStyle="color: red;"/>
				  </td>
			  </tr>
			  <tr> <td> <br/></td> </tr> 
			  <tr> 
				  <td><span class="required">* </span> Direcci&oacute;n:&nbsp;
				  <input type="text" id="direccionTrabajador" name="direccionTrabajador" size="13" maxlength="13" />
				  
				  </td>			   
			  </tr>    
			  <tr> <td> <br/></td> </tr>
			  <tr> 
				<td><span class="required">* </span> Correo Electr&oacute;nico:
				&nbsp;
				<form:input path="dltPersonaT.desEmail" id="desEmail" name="desEmail" maxlength="50" />
				</td>
			  </tr>
			  <tr> <td> <br/></td> </tr>
			  <tr><td>Tel&eacute;fono de contacto:&nbsp;
			       <form:input path="dltPersonaT.numTelefono" id="numTelefono" name="numTelefono" maxlength="25" />
			       <div style="font-size: 10px;">(en caso de no contar con &eacute;ste, el de un familiar o vecino)</div>
			       </td>
			  </tr>
			  <tr> <td> <br/></td> </tr>
			   <tr> <td>Tel&eacute;fono celular:&nbsp;
			      <form:input path="dltPersonaT.numCelular" id="numCelular" name="numCelular" maxlength="25"/>
			      </td> </tr>
			 <tr> 
				<td colspan="2"><span class="required">* </span> Documento oficial con el que se identifica.</td>							   
			 </tr>
			 <tr> <td> <form:errors path="dltPersonaT.dlcTipodocumento.cveTipodocumento" cssStyle="color: red;"/><br/></td> </tr>
			 <tr>							  
				<td>
				  <form:select  name="cveTipodocumento" id="cveTipodocumento" path="dltPersonaT.dlcTipodocumento.cveTipodocumento">
				    <option value="-1">--Seleccione Identificaci&oacute;n--</option>
					<option value="1">Credencial de Elector</option>
					<option value="2">Cartilla del Servicio Militar Nacional</option>
					<option value="3">C&eacute;dula Profesional</option>
					<option value="4">ADIMSS</option>
					<option value="5">Documento que acredita la legal estancia del pa&iacute;s</option>
				  </form:select>
				</td>
				<td>
					 Adjuntar el documento de identificaci&oacute;n en forma digital 
					<button type="button"  id="buttonAdjuntarDatosTrabajador" class="mboton" title="Button" class="mboton" onclick="getLisDoc();" >Adjuntar</button>
					<div id="browseDocDiv">
				     <input id="file_upload" type="file" name="file" >
				   </div>
				</td>
			</tr>
			<tr> <td> <br/></td> </tr>		
			<tr><td>
				<div class="ui-widget-content ui-corner-all" id="erroresFileUploadDiv">
 			 		<br>
					<div class="ui-state-error ui-corner-all" align="center">
						<div class="ui-icon ui-icon-alert"></div>
						<br>
						<p class="ui-helper-reset ui-state-error-text" id="errorFileUploadDiv"></p>
						<br>
					</div>
				</div>
				</td>
			</tr>
			<tr> 
			   <td>N&uacute;mero del documento de identificaci&oacute;n:&nbsp;			
			   <form:input path="dltPersonaT.numDocumento" id="numDocumento" name="numDocumento"maxlength="25" />
			   <form:errors path="dltPersonaT.numDocumento" cssStyle="color: red;"/>
			   </td>				   
			</tr>
			<tr> <td> <br/></td> </tr>
		 </table>
		 </fieldset>
		 
		 <fieldset style="width: 977px" class="titulo">
         <legend><strong>MOTIVO DE LA DENUNCIA</strong></legend>
		 	 <table style="width: 100%" align="left">
			<tr align="center"> <td>Hechos o razones que dan motivo a la petici&oacute;n </td></tr>
			<tr> <td> <br/></td> </tr>
			<tr>
				<td colspan="2">
				     <table>
					    <tr>
						 <td colspan="4"> 
						    <input type="checkbox" id="md1" name="md" value="1"> El patr&oacute;n no lo afili&oacute; al Seguro Social</td>
						</tr>
						<tr>
						    <td  colspan="4" >Fecha de no afiliación &nbsp; Del: &nbsp; 
						 	<form:input type="text" path="valoresMotivoDenuncia.md1fechaInicio"  readonly="readonly" id="md1fechaInicio" name="md1fechaInicio" value="" size="10" maxlength="10" readonly="readonly" onclick=" $( this ).datepicker();"  /> 						 	
						 	&nbsp;al:&nbsp;
						 	<form:input type="text" path="valoresMotivoDenuncia.md1fechaFin" readonly="readonly" id="md1fechaFin" name="md1fechaFin" readonly="readonly" onclick=" $(this).datepicker();" value="" size="10" maxlength="1" /> </td></tr>
						<tr>
							<td colspan="4"> 
							<input type="checkbox" id="md2" name="md" value="2">El patr&oacute;n lo afili&oacute; con fecha posterior a la de ingreso </td>
						</tr>
						<tr>
						   <td >Fecha de ingreso al trabajo:</td>
						   <td><form:input path="valoresMotivoDenuncia.md2fechaInicio" readonly="readonly" type="text" id="md2fechaInicio" name="md2fechaInicio" readonly="readonly" onclick=" $( this ).datepicker();" value="" size="10" maxlength="10" /></td>
						   <td >Fecha de afiliaci&oacute;n </td>
						   <td><form:input path="valoresMotivoDenuncia.md2fechaFin" readonly="readonly" type="text" id="md2fechaInicio" name="md2fechaInicio" readonly="readonly" onclick=" $( this ).datepicker();" value="" size="10" maxlength="10" /></td>
						</tr>
						<tr>
							<td colspan="4"> <input type="checkbox" id="md3" name="md" value="3"> El patr&oacute;n lo afili&oacute; con un salario inferior al real </td>
						</tr>
						<tr>
						    <td>Salario registrado en el IMSS: </td>
						    <td><form:input type="text" path="valoresMotivoDenuncia.md3ImporteImss" id="md3ImporteImss" name="md3ImporteImss" size="12" maxlength="12" /></td>
						    <td>Salario real: </td>
						    <td><form:input type="text" path="valoresMotivoDenuncia.md3ImporteReal" id="md3ImporteReal" name="md3ImporteReal" size="12" maxlength="12" /></td>
						 </tr>
						<tr>
							<td colspan="4"><input type="checkbox" id="md4" name="md" value="4"> El patr&oacute;n se niega a presentar el aviso de baja del trabajador, cuando ya no labora con &eacute;l</td>				
						</tr>
						<tr><td >Fecha en la que dej&oacute; de laborar para &eacute;l:</td>
						    <td><form:input path="valoresMotivoDenuncia.md4fechaFin" type="text" readonly="readonly" id="md4fechaFin" name="md4fechaFin" readonly="readonly" onclick=" $( this ).datepicker();" value="" size="1" maxlength="10" /></td>
						    <td> &nbsp;&nbsp;<td>
						    <td> &nbsp;&nbsp;<td>
						</tr>
					 </table>
				 </td>	
			 </tr>		
			 <tr> <td> <br/></td> </tr>
			 <tr><td colspan="4"> Observaciones: <div  style="font-size:10px;">(Anote aqu&iacute; las aclaraciones o manifestaciones que considere necesario comentar)</div></td></tr>		
			 <tr><td colspan="4">
			     <form:textarea path="dltDenuncia.desObservaciones" rows="2" cols="200" id="desObservaciones" name="desObservaciones"></form:textarea>
			     </td>							           						        
			 </tr>									 
        </table>
    </fieldset>         
    
    <fieldset style="width: 977px" class="titulo" id="fsDatosBeneficiario">
         <legend><strong>DATOS DEL BENEFICIARIO (cuando &eacute;ste es el denunciante)</strong></legend>
         <table style="width: 100%" align="center" name="tblDatosB" id="tblDatosB">
         	<tr align="left">
                  <td> Nombre: </td>
               </tr>
               <tr align="left">
                <td colspan="3">
                 <table><tr>
                  <td align="left"><div><span class="required">*</span>Nombre:</div>
                  <form:input path="dltPersonaB.desNombre" id="nombreBeneficiario" name="nombreBeneficiario" onkeyup="conMayusculas(this);" maxlength="50"/>
                  <form:errors path="dltPersonaB.desNombre" cssStyle="color: red;"/>
                  </td>
                  <td><div>Apellido Paterno:</div> 
                  <form:input path="dltPersonaB.desPaterno" id="paternoBeneficiario" name="paternoBeneficiario" onkeyup="conMayusculas(this);" maxlength="50"/>
                  <form:errors path="dltPersonaB.desPaterno" cssStyle="color: red;"/>
                  </td>
                  <td><div>Apellido Materno: </div> 
                   <form:input path="dltPersonaB.desMaterno" id="maternoBeneficiario" name="maternoBeneficiario" onkeyup="conMayusculas(this);" maxlength="50"/>
                   <form:errors path="dltPersonaB.desMaterno" cssStyle="color: red;"/>
                   </td>
                  </tr>
                  <tr>
				  <td colspan="3"><span class="required">* </span> Clave &Uacute;nica de Registro de Poblaci&oacute;n (CURP):
                  &nbsp;
                   <form:input path="dltPersonaB.cveCurp" id="cveCurpB" name="cveCurpB" maxlength="18" size="18"/>
                   <form:errors path="dltPersonaB.cveCurp" cssStyle="color: red;"/>
                   </td>
			  </tr>
                  </table>
                  </td>
              </tr>
              <tr> 
				  <td>Direcci&oacute;n</td>
				  <td><input type="text" id="direccionBeneficiario" size="13" maxlength="13" /></td>			   
			  </tr>  
			  <tr> <td> <form:errors path="dltPersonaB.dlcTipodocumento.cveTipodocumento" cssStyle="color: red;"/><br/></td> </tr>
			  <tr>
			    <td><span class="required">* </span>Documento oficial con el que se identifica </td>
			    <td>
				  <form:select  name="beneficiarioCveTipodocumento" id="beneficiarioCveTipodocumento" path ="dltPersonaB.dlcTipodocumento.cveTipodocumento">
				    <option value="-1">--Seleccione Identificaci&oacute;n--</option>
					<option value="1">Credencial de Elector</option>
					<option value="2">Cartilla del Servicio Militar Nacional</option>
					<option value="3">C&eacute;dula Profesional</option>
					<option value="4">ADIMSS</option>
					<option value="5">Documento que acredita la legal estancia del pa&iacute;s</option>
				  </form:select>
				</td>
			    <td>Adjuntar documento</td>
			    <td>Eliminar documento</td>
			  </tr>
			  <tr><td>Número del documento de identificaci&oacute;n</td>
			      <td><form:input path="dltPersonaB.numDocumento" id="numDocumento" name="numDocumento" maxlength="25"/>
			          <form:errors path="dltPersonaB.numDocumento" cssStyle="color: red;"/>
			          </td></tr>
         </table>
    </fieldset>
    <fieldset style="width: 977px" class="titulo" id="fsDatosRepresentanteLegal">
         <legend><strong>DATOS DEL REPRESENTANTE LEGAL (cuando &eacute;ste es el denunciante)</strong></legend>  
         <table style="width: 100%" align="center" name="tblDatosRL" id="tblDatosRL">
         	<tr align="left">
                  <td>Nombre del Representante Legal: </td>
               </tr>
               <tr align="left">
                <td colspan="3">
                 <table><tr>
                  <td align="left"><div>Nombre:</div>
                  <form:input path="dltPersonaRL.desNombre" id="nombreRL" name="nombreRL" onkeyup="conMayusculas(this);" maxlength="50"/>
                  <form:errors path="dltPersonaRL.desNombre" cssStyle="color: red;"/>
                  </td>
                  <td><div>Apellido Paterno:</div> 
                  <form:input path="dltPersonaRL.desPaterno" id="paternoRL" name="paternoRL" onkeyup="conMayusculas(this);" maxlength="50"/>
                  <form:errors path="dltPersonaRL.desPaterno" cssStyle="color: red;"/>
                  </td>
                  <td><div>Apellido Materno: </div> 
                  <form:input path="dltPersonaRL.desMaterno" id="maternoRL" name="maternoRL" onkeyup="conMayusculas(this);" maxlength="50"/>
                   <form:errors path="dltPersonaRL.desMaterno" cssStyle="color: red;"/>
                  </td>
                  </tr>
                  <tr>
				  <td colspan="3"><span class="required">* </span> Clave &Uacute;nica de Registro de Poblaci&oacute;n (CURP):
                  &nbsp;
                   <form:input path="dltPersonaRL.cveCurp" id="cveCurpRL" name="cveCurpRL" maxlength="18" size="18"/>
                   <form:errors path="dltPersonaRL.cveCurp" cssStyle="color: red;"/>
                   </td>
			  </tr>
                  </table>
                  </td>
              </tr>
              <tr> 
				  <td>Direcci&oacute;n</td>
				  <td><input type="text" id="direccionRL" size="13" maxlength="13" /></td>			   
			  </tr>  
			   <tr> <td> <form:errors path="dltPersonaRL.dlcTipodocumento.cveTipodocumento" cssStyle="color: red;"/><br/></td> </tr>
			  <tr>
			    <td><span class="required">* </span> Documento oficial con el que se identifica </td>
			    <td> 
				  <form:select name="rlCveTipodocumento" id="rlCveTipodocumento" path="dltPersonaRL.dlcTipodocumento.cveTipodocumento">
				    <option value="-1">--Seleccione Identificaci&oacute;n--</option>
					<option value="1">Credencial de Elector</option>
					<option value="2">Cartilla del Servicio Militar Nacional</option>
					<option value="3">C&eacute;dula Profesional</option>
					<option value="4">ADIMSS</option>
					<option value="5">Documento que acredita la legal estancia del pa&iacute;s</option>
				  </form:select>
				</td>
			    <td>Adjuntar documento</td>
			    <td>Eliminar documento</td>
			  </tr>
			  <tr><td>Número del documento de identificación</td>
			       <td><form:input path="dltPersonaRL.numDocumento" id="numDocumento" name="numDocumento" maxlength="25"/>
			            <form:errors path="dltPersonaRL.numDocumento" cssStyle="color: red;"/>
			       </td></tr>
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
						            <button type="button"  id="buttonSiguiente" class="mboton" onclick="abrirDialogoConfirmacion();">Guardar</button>									    									
						            <button type="button"  id="buttonSiguiente" class="mboton" onclick="enviar();">Enviar</button>
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
</div>
