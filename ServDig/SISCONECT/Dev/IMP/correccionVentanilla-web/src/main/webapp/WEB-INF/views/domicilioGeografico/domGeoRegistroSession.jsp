<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
	
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<html lang="sp">

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/generales/controlCombos.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/domiciliosInegi/domiciliosInegi.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>

				     
		    <div id="dgDomGeoRegistro"  title="Registro Domicilio Geogr&aacute;fico" style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
			    <div id="wrapperDialogRegistro" style="background-color: #f2fff2;">	
					<form:form modelAttribute="objDG" action="sessionDomicilioGeografico.do"
						method="post" id="domGeoFormRegistro">
						
						
					<fieldset>
						    <form:hidden path="hastableKeyDG"/>
						    <form:hidden path="domicilioId"/>
						    
							<table width="900px"  border="0" cellspacing="1" cellpadding="1">
							  <tr>
							    <td>
							    	<table class="tablaverde2" style="width: 900px" >
								  	  <thead>
									  	<tr >
									  		<td colspan="4">Datos Generales</td>
									  	</tr>
									  </thead>									  
									  <tbody>
							   			  <tr valign="top" class="impar">
										    <td align="left" colspan="4">&nbsp;</td>
										  </tr>
							      
							        <tr class="par">
							        	<td align="left" width="100px">			   				  		
											<form:label for="dgCatLocalidad.dgCatAmbito.ambito" id="ambitoLabel" path="dgCatLocalidad.dgCatAmbito.ambito" 
												    		cssErrorClass="error"><span class="required">*</span>&Aacute;mbito: </form:label>	    			    
										</td>
										  <td align="left" width="100px">
											<combo:creaCombo entidad="mx.gob.imss.ctirss.domiciliosInegi.model.DgCatAmbito"
											 idHtml="dgCatLocalidad.dgCatAmbito.ambito"
											 idHtmlContenedor="domGeoFormRegistro"
											 idHtmlValor="${objDG.dgCatLocalidad.dgCatAmbito.ambito }"
											 onchange="getSendComboDescToHidden(this,'dgCatLocalidad.dgCatAmbito.nombre');"
											 />		    
										</td>
										
							          <td width="14%">&nbsp;</td>
							          <td width="39%">&nbsp;</td>
							        </tr>
							        <tr class="par">
							        	<td align="left" width="100px">				   							   		
											<form:label for="dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt" id="cveEntLabel" path="dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt" 
												    		cssErrorClass="error"><span class="required">*</span>Estado: </form:label>	    			    
										</td>
										  <td align="left" width="100px">
											<combo:creaCombo entidad="mx.gob.imss.ctirss.domiciliosInegi.model.DgCatEstado"
											 idHtml="dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt"
											 idHtmlContenedor="domGeoFormRegistro"
											 idHtmlValor="${objDG.dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt}"
											 onchange="getSendComboDescToHidden(this,'dgCatLocalidad.dgCatMunicipio.dgCatEstado.nomEnt')"
											 />		    
										</td>
										
							          <td width="14%">&nbsp;</td>
							          <td width="39%">&nbsp;</td>
							        </tr>
							        <tr class="par">
							        	<td align="left" width="100px">				   							   		
											<form:label for="dgCatLocalidad.dgCatMunicipio.id.cveMun" id="cveMunLabel" path="dgCatLocalidad.dgCatMunicipio.id.cveMun" 
												    		cssErrorClass="error"><span class="required">*</span>Municipio: </form:label>	    			    
										</td>
										  <td align="left" width="100px">
											<combo:creaCombo entidad="mx.gob.imss.ctirss.domiciliosInegi.model.DgCatMunicipio"
											 idHtml="dgCatLocalidad.dgCatMunicipio.id.cveMun"
											 idHtmlPadre="dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt@id.cveEnt"
											 entidadPadre="mx.gob.imss.ctirss.domiciliosInegi.model.DgCatEstado"											 			   
											 idHtmlContenedor="domGeoFormRegistro"
											 idHtmlValor="${objDG.dgCatLocalidad.dgCatMunicipio.id.cveMun},${objDG.dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt}"
											 onchange="getSendComboDescToHidden(this,'dgCatLocalidad.dgCatMunicipio.nomMun');
											 		   reinicaSeleccionCmb(['dgAsentamiento.dgCatTipoAsen.cveTipoAsen',
											 		   						'dgAsentamiento.id.cveAsen',
											 					            'dgCodigosPostales.id.codigo',
											 					            'dgVialidadByCveViaPrin.dgCatVialidad.cveTipoVial',
											 					            'dgVialidadByCveViaPrin.cveVia',
											 					            'dgVialidadByCveViaRef1.dgCatVialidad.cveTipoVial',
											 					            'dgVialidadByCveViaRef2.dgCatVialidad.cveTipoVial',
											 					            'dgVialidadByCveViaRef3.dgCatVialidad.cveTipoVial',
											 					            'dgVialidadByCveViaRef1.cveVia',
											 					            'dgVialidadByCveViaRef2.cveVia',
											 					            'dgVialidadByCveViaRef3.cveVia'
											 	      			         ]);"
											 />		    
										</td>
										
							
							          <td width="14%">&nbsp;</td>
							          <td width="39%">&nbsp;</td>
							        </tr>
							        <tr class="par">
							        	<td align="left" width="100px">				   							   		
											<form:label for="dgCatLocalidad.id.cveLoc" id="cveLocLabel" path="dgCatLocalidad.id.cveLoc" 
												    		cssErrorClass="error"><span class="required">*</span>Localidad:</form:label>	    			    
										</td>
										  <td align="left" width="100px">
											<combo:creaCombo entidad="mx.gob.imss.ctirss.domiciliosInegi.model.DgCatLocalidad"
											 idHtml="dgCatLocalidad.id.cveLoc"
											 idHtmlPadre="dgCatLocalidad.dgCatMunicipio.id.cveMun@id.cveMun,
											 			  dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt@id.cveEnt,
											 			  dgCatLocalidad.dgCatAmbito.ambito@dgCatAmbito.ambito"											 			  
											 entidadPadre="mx.gob.imss.ctirss.domiciliosInegi.model.DgCatMunicipio,
											 			   mx.gob.imss.ctirss.domiciliosInegi.model.DgCatEstado,
											 			   mx.gob.imss.ctirss.domiciliosInegi.model.DgCatAmbito"
											 idHtmlContenedor="domGeoFormRegistro"
											 idHtmlValor="${objDG.dgCatLocalidad.id.cveLoc},${objDG.dgCatLocalidad.dgCatMunicipio.id.cveMun},${objDG.dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt},${objDG.dgCatLocalidad.dgCatAmbito.ambito}"
											 onchange="getSendComboDescToHidden(this,'dgCatLocalidad.nomLoc');
											 			reinicaSeleccionCmb(['dgAsentamiento.dgCatTipoAsen.cveTipoAsen',
											 		   						'dgAsentamiento.id.cveAsen',
											 					            'dgCodigosPostales.id.codigo',
											 					            'dgVialidadByCveViaPrin.dgCatVialidad.cveTipoVial',
											 					            'dgVialidadByCveViaPrin.cveVia',
											 					            'dgVialidadByCveViaRef1.dgCatVialidad.cveTipoVial',
											 					            'dgVialidadByCveViaRef2.dgCatVialidad.cveTipoVial',
											 					            'dgVialidadByCveViaRef3.dgCatVialidad.cveTipoVial',
											 					            'dgVialidadByCveViaRef1.cveVia',
											 					            'dgVialidadByCveViaRef2.cveVia',
											 					            'dgVialidadByCveViaRef3.cveVia'
											 	      			         ]);"
											 		  
											 />		    
										</td>
							          <td width="14%">&nbsp;</td>
							          <td width="39%">&nbsp;</td>
							        </tr>
							        <tr class="par">
							         	<td align="left" width="100px">				   							   		
											<form:label for="dgAsentamiento.dgCatTipoAsen.cveTipoAsen" id="cveTipoAsenLabel" path="dgAsentamiento.dgCatTipoAsen.cveTipoAsen" 
												    		cssErrorClass="error"><span class="required">*</span>Tipo Asentamiento: </form:label>	    			    
										</td>
										  <td align="left" width="100px">
											<combo:creaCombo entidad="mx.gob.imss.ctirss.domiciliosInegi.model.DgCatTipoAsen"
											 idHtml="dgAsentamiento.dgCatTipoAsen.cveTipoAsen"
											 idHtmlContenedor="domGeoFormRegistro"
											 idHtmlValor="${objDG.dgAsentamiento.dgCatTipoAsen.cveTipoAsen}"
											 onchange="getSendComboDescToHidden(this,'dgAsentamiento.dgCatTipoAsen.nombre');
											 reinicaSeleccionCmb(['dgAsentamiento.id.cveAsen',
											 					  'dgCodigosPostales.id.codigo',
											 					  'dgVialidadByCveViaPrin.dgCatVialidad.cveTipoVial',
											 					  'dgVialidadByCveViaPrin.cveVia',
											 					            'dgVialidadByCveViaRef1.dgCatVialidad.cveTipoVial',
											 					            'dgVialidadByCveViaRef2.dgCatVialidad.cveTipoVial',
											 					            'dgVialidadByCveViaRef3.dgCatVialidad.cveTipoVial',
											 					            'dgVialidadByCveViaRef1.cveVia',
											 					            'dgVialidadByCveViaRef2.cveVia',
											 					            'dgVialidadByCveViaRef3.cveVia'
											 	      			         ]);"
											 
											 />		    
										</td>
							
							        	<td align="left" width="100px">				   							   		
											<form:label for="dgAsentamiento.id.cveAsen" id="cveAsenLabel" path="dgAsentamiento.id.cveAsen" 
												    		cssErrorClass="error"><span class="required">*</span>Asentamiento: </form:label>	    			    
										</td>
										  <td align="left" width="100px">
											<combo:creaCombo entidad="mx.gob.imss.ctirss.domiciliosInegi.model.DgAsentamiento"
											 idHtml="dgAsentamiento.id.cveAsen"
											 idHtmlPadre="dgAsentamiento.dgCatTipoAsen.cveTipoAsen@dgCatTipoAsen.cveTipoAsen,
											 			  dgCatLocalidad.id.cveLoc@id.cveLoc,
											 			  dgCatLocalidad.dgCatMunicipio.id.cveMun@id.cveMun,
											 			  dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt@id.cveEnt"
											 entidadPadre="mx.gob.imss.ctirss.domiciliosInegi.model.DgCatTipoAsen,
											 			   mx.gob.imss.ctirss.domiciliosInegi.model.DgCatLocalidad,
											 			   mx.gob.imss.ctirss.domiciliosInegi.model.DgCatMunicipio,
											 			   mx.gob.imss.ctirss.domiciliosInegi.model.DgCatEstado"
											 idHtmlContenedor="domGeoFormRegistro"
											 idHtmlValor="${objDG.dgAsentamiento.id.cveAsen},${objDG.dgAsentamiento.dgCatTipoAsen.cveTipoAsen},${objDG.dgCatLocalidad.id.cveLoc},${objDG.dgCatLocalidad.dgCatMunicipio.id.cveMun},${objDG.dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt}"
											 onchange="getSendComboDescToHidden(this,'dgAsentamiento.nomAsen');
											 			reinicaSeleccionCmb([
											 					  'dgCodigosPostales.id.codigo',
											 					  'dgVialidadByCveViaPrin.dgCatVialidad.cveTipoVial',
											 					  'dgVialidadByCveViaPrin.cveVia',
											 					            'dgVialidadByCveViaRef1.dgCatVialidad.cveTipoVial',
											 					            'dgVialidadByCveViaRef2.dgCatVialidad.cveTipoVial',
											 					            'dgVialidadByCveViaRef3.dgCatVialidad.cveTipoVial',
											 					            'dgVialidadByCveViaRef1.cveVia',
											 					            'dgVialidadByCveViaRef2.cveVia',
											 					            'dgVialidadByCveViaRef3.cveVia'
											 	      			         ]);"
											 />		    
										</td>
							        </tr>
							      </tbody>
							   </table>
						
							  <tr>
							    <td>
							    	<table class="tablaverde2" style="width: 900px" >
								  	  <thead>
									  	<tr >
									  		<td colspan="4">Datos Especificos Domicilio</td>
									  	</tr>
									  </thead>									  
									  <tbody>
							   			  <tr valign="top" class="impar">
										    <td align="left" colspan="4">&nbsp;</td>
										  </tr>
										  
										  <tr class="par">
							       	 <td align="left" width="100px" colspan="1"><form:label
												id="codigoLabel" for="dgCodigosPostales.id.codigo" path="dgCodigosPostales.id.codigo"
												cssErrorClass="error"><span class="required">*</span> C&oacute;digo Postal: </form:label>
									 </td>
									 
									  <td align="left" width="100px" colspan="1">
											
											<combo:creaCombo entidad="mx.gob.imss.ctirss.domiciliosInegi.model.DgCodigosPostales"
											 idHtml="dgCodigosPostales.id.codigo"
											 idHtmlPadre="dgAsentamiento.id.cveAsen@id.cveAsen,
											 			  dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt@id.cveEnt,
											 			  dgCatLocalidad.id.cveLoc@id.cveLoc,
											 			  dgCatLocalidad.dgCatMunicipio.id.cveMun@id.cveMun"
											 			  
											 entidadPadre="mx.gob.imss.ctirss.domiciliosInegi.model.DgAsentamiento,
											 			   mx.gob.imss.ctirss.domiciliosInegi.model.DgCatEstado,
											  			   mx.gob.imss.ctirss.domiciliosInegi.model.DgCatLocalidad,
											 			   mx.gob.imss.ctirss.domiciliosInegi.model.DgCatMunicipio"
											 			   
											 idHtmlContenedor="domGeoFormRegistro"
											 idHtmlValor="${objDG.dgCodigosPostales.id.codigo},${objDG.dgAsentamiento.id.cveAsen},${objDG.dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt},${objDG.dgCatLocalidad.id.cveLoc},${objDG.dgCatLocalidad.dgCatMunicipio.id.cveMun}"
											 />		    
									 </td>
							          <td>&nbsp;</td>
							          <td>&nbsp;</td>
							        </tr>
							      <tr class="par">
							        	<td align="left" width="100px">				   							   		
											<form:label for="dgVialidadByCveViaPrin.dgCatVialidad.cveTipoVial" id="cveTipoVialLabel" path="dgVialidadByCveViaPrin.dgCatVialidad.cveTipoVial" 
												    		cssErrorClass="error"><span class="required">*</span>Tipo Vialidad: </form:label>	    			    
										</td>
										  <td align="left" width="100px">
											<combo:creaCombo entidad="mx.gob.imss.ctirss.domiciliosInegi.model.DgCatVialidad"
											 idHtml="dgVialidadByCveViaPrin.dgCatVialidad.cveTipoVial"
											 idHtmlContenedor="domGeoFormRegistro"
											 idHtmlValor="${objDG.dgVialidadByCveViaPrin.dgCatVialidad.cveTipoVial}"
											 onchange="getSendComboDescToHidden(this,'dgVialidadByCveViaPrin.dgCatVialidad.descripcion')"
											 />		    
										</td>
							          <td align="left" width="100px">				   							   		
											<form:label for="dgVialidadByCveViaPrin.cveVia" id="cveViaPrinLabel" path="dgVialidadByCveViaPrin.cveVia" 
												    		cssErrorClass="error"><span class="required">*</span>Vialidad Principal:</form:label>	    			    
										</td>
										<td align="left" width="100px">
											<combo:creaCombo entidad="mx.gob.imss.ctirss.domiciliosInegi.model.DgVialidad"
											 idHtml="dgVialidadByCveViaPrin.cveVia"
											 idHtmlPadre="dgVialidadByCveViaPrin.dgCatVialidad.cveTipoVial@dgCatVialidad.cveTipoVial,
											 			  dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt@dgCatLocalidad.id.cveEnt,
											 			  dgCatLocalidad.id.cveLoc@dgCatLocalidad.id.cveLoc,
											 			  dgCatLocalidad.dgCatMunicipio.id.cveMun@dgCatLocalidad.id.cveMun,											 			  
											 			  dgCatLocalidad.dgCatAmbito.ambito@dgCatAmbito.ambito"
											 entidadPadre="mx.gob.imss.ctirss.domiciliosInegi.model.DgCatVialidad,
											 			   mx.gob.imss.ctirss.domiciliosInegi.model.DgCatEstado,
											 			   mx.gob.imss.ctirss.domiciliosInegi.model.DgCatLocalidad,
											 			   mx.gob.imss.ctirss.domiciliosInegi.model.DgCatMunicipio,
											 			   mx.gob.imss.ctirss.domiciliosInegi.model.DgCatAmbito"
											 idHtmlContenedor="domGeoFormRegistro"
											  idHtmlValor="${objDG.dgVialidadByCveViaPrin.cveVia},${objDG.dgVialidadByCveViaPrin.dgCatVialidad.cveTipoVial},${objDG.dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt},${objDG.dgCatLocalidad.id.cveLoc},${objDG.dgCatLocalidad.dgCatMunicipio.id.cveMun},${objDG.dgCatLocalidad.dgCatAmbito.ambito}"
											  onchange="getSendComboDescToHidden(this,'dgVialidadByCveViaPrin.nomVia');
											  			getSendComboDescToHidden(this,'nomvial')"
											 />		    
										</td>
							        </tr>
							
							
								     <tr class="par">
								       <td align="left" width="100px">				   							   		
											<form:label for="dgCatTipoDom.cveTipoDom" id="cveTipoDomLabel" path="dgCatTipoDom.cveTipoDom" 
												    		cssErrorClass="error"><span class="required">*</span>Tipo Domicilio: </form:label>	    			    
										</td>
										<td align="left" width="100px">
											<combo:creaCombo entidad="mx.gob.imss.ctirss.domiciliosInegi.model.DgCatTipoDom"
											 idHtml="dgCatTipoDom.cveTipoDom"
											 idHtmlContenedor="domGeoFormRegistro"
											 idHtmlValor="${objDG.dgCatTipoDom.cveTipoDom}"
											 />		    
										</td>
										<td align="left" width="100px" colspan="1"><form:label
												id="nomvialLabel" for="nomvial" path="nomvial"
												cssErrorClass="error"> Vía Principal: </form:label>
									 </td>
									  <td align="left" width="100px" colspan="1">
											<form:input	path="nomvial" id="nomvial" value="" label="Via Principal" maxlength="256"  size="50"  />
									 </td>
								    </tr>
								     <tr class="par">
								        <td align="left" width="100px">				   							   		
											<form:label for="numextnum" id="numextnumLabel" path="numextnum" 
												    		cssErrorClass="error"><span class="required">*</span>N&uacute;mero Exterior: </form:label>	    			    
										</td>
										<td align="left" width="100px">
											<form:input	path="numextnum" id="numextnum" value="" label="N&uacute;mero Exterior: " 
											onkeyup="validaCampo('PermiteSoloNumeros','numextnum','domGeoFormRegistro');"
											maxlength="5" size="8"
											
											/>		    
										</td>
										<td align="left" width="100px">				   							   		
											<form:label for="numextalf" id="numextalfLabel" path="numextalf" 
												    		cssErrorClass="error">N&uacute;mero/Letra Ext: </form:label>	    			    
										</td>
										<td align="left" width="100px">
											<form:input	path="numextalf" value="" label="N&uacute;mero/Letra Ext" maxlength="35" size="8" />		    
										</td>
								         
								    </tr>
								     <tr class="par">
								        <td align="left" width="100px">				   							   		
											<form:label for="numintnum" id="numintnumLabel" path="numintnum" 
												    		cssErrorClass="error">N&uacute;mero Interior: </form:label>	    			    
										</td>
										<td align="left" width="100px">
											<form:input	path="numintnum" id="numintnum" value="" label="N&uacute;mero Interior: " maxlength="5" size="8" 
											onkeyup="validaCampo('PermiteSoloNumeros','numintnum','domGeoFormRegistro');"
											/>		    
										</td>
										<td align="left" width="100px">				   							   		
											<form:label for="numintalf" id="numintalfLabel" path="numintalf" 
												    		cssErrorClass="error">N&uacute;mero/Letra Int: </form:label>	    			    
										</td>
										<td align="left" width="100px">
											<form:input	path="numintalf" value="" label="N&uacute;mero/Letra Int" maxlength="35" size="8" />		    
										</td>
								         
								    </tr>
								      <tr class="par">
								        <td align="left" width="100px">				   							   		
											<form:label for="numextAnt" id="numextAntLabel" path="numextAnt" 
												    		cssErrorClass="error">N&uacute;mero Exterior Ant: </form:label>	    			    
										</td>
										<td align="left" width="100px">
											<form:input	path="numextAnt" value="" label="N&uacute;mero Exterior Ant: " maxlength="35" size="8" />		    
										</td>
										 <td width="14%">&nbsp;</td>
							          	 <td width="39%">&nbsp;</td>
								         
								    </tr>
								  
								  </tbody>
								     </table>
						
							  <tr>
							    <td>
							    	<table class="tablaverde2" style="width: 900px" >
								  	  <thead>
									  	<tr >
									  		<td colspan="4">Datos De Referencia</td>
									  	</tr>
									  </thead>									  
									  <tbody>
							   			  <tr valign="top" class="impar">
										    <td align="left" colspan="4">&nbsp;</td>
										  </tr>
							       <tr class="par">
							          <td align="left" width="100px">				   							   		
											<form:label for="dgVialidadByCveViaRef1.dgCatVialidad.cveTipoVial" id="cveTipoVialRef1Label" path="dgVialidadByCveViaRef1.dgCatVialidad.cveTipoVial" 
												    		cssErrorClass="error"><span class="required">*</span>Tipo Vialidad Referencia 1: </form:label>	    			    
										</td>
										<td align="left" width="100px">
											<combo:creaCombo entidad="mx.gob.imss.ctirss.domiciliosInegi.model.DgCatVialidad"
											 idHtml="dgVialidadByCveViaRef1.dgCatVialidad.cveTipoVial"
											 idHtmlContenedor="domGeoFormRegistro"
											 idHtmlValor="${objDG.dgVialidadByCveViaRef1.dgCatVialidad.cveTipoVial}"
											 onchange="getSendComboDescToHidden(this,'dgVialidadByCveViaRef1.dgCatVialidad.descripcion')"
											 />	    
										</td>
										<td align="left" width="100px">				   							   		
											<form:label for="dgVialidadByCveViaRef1.cveVia" id="cveViaRef1Label" path="dgVialidadByCveViaRef1.cveVia" 
												    		cssErrorClass="error"><span class="required">*</span>Vialidad Referencia 1: </form:label>	    			    
										</td>
										<td align="left" width="100px">
											<combo:creaCombo entidad="mx.gob.imss.ctirss.domiciliosInegi.model.DgVialidad"
											 idHtml="dgVialidadByCveViaRef1.cveVia"
											 idHtmlPadre="dgVialidadByCveViaRef1.dgCatVialidad.cveTipoVial@dgCatVialidad.cveTipoVial,
											 			  dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt@dgCatLocalidad.id.cveEnt,
											 			  dgCatLocalidad.id.cveLoc@dgCatLocalidad.id.cveLoc,
											 			  dgCatLocalidad.dgCatMunicipio.id.cveMun@dgCatLocalidad.id.cveMun,											 			  
											 			  dgCatLocalidad.dgCatAmbito.ambito@dgCatAmbito.ambito"
											 entidadPadre="mx.gob.imss.ctirss.domiciliosInegi.model.DgCatVialidad,
											 			   mx.gob.imss.ctirss.domiciliosInegi.model.DgCatEstado,
											 			   mx.gob.imss.ctirss.domiciliosInegi.model.DgCatLocalidad,
											 			   mx.gob.imss.ctirss.domiciliosInegi.model.DgCatMunicipio,
											 			   mx.gob.imss.ctirss.domiciliosInegi.model.DgCatAmbito"
											 idHtmlContenedor="domGeoFormRegistro"
  										     idHtmlValor="${objDG.dgVialidadByCveViaRef1.cveVia},${objDG.dgVialidadByCveViaRef1.dgCatVialidad.cveTipoVial},${objDG.dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt},${objDG.dgCatLocalidad.id.cveLoc},${objDG.dgCatLocalidad.dgCatMunicipio.id.cveMun},${objDG.dgCatLocalidad.dgCatAmbito.ambito}"
  										     onchange="getSendComboDescToHidden(this,'dgVialidadByCveViaRef1.nomVia')"
											 />
												    
										</td>
							
							        </tr>			    
								  <tr class="par">
							          <td align="left" width="100px">				   							   		
											<form:label for="dgVialidadByCveViaRef2.dgCatVialidad.cveTipoVial" id="cveTipoVialRef2Label" path="dgVialidadByCveViaRef2.dgCatVialidad.cveTipoVial" 
												    		cssErrorClass="error"><span class="required">*</span>Tipo Vialidad Referencia 2: </form:label>	    			    
										</td>
										<td align="left" width="100px">
											<combo:creaCombo entidad="mx.gob.imss.ctirss.domiciliosInegi.model.DgCatVialidad"
											 idHtml="dgVialidadByCveViaRef2.dgCatVialidad.cveTipoVial"
											 idHtmlContenedor="domGeoFormRegistro"
											 idHtmlValor="${objDG.dgVialidadByCveViaRef2.dgCatVialidad.cveTipoVial}"
											 onchange="getSendComboDescToHidden(this,'dgVialidadByCveViaRef2.dgCatVialidad.descripcion')"
											 />	   	    
										</td>
										<td align="left" width="100px">				   							   		
											<form:label for="dgVialidadByCveViaRef2.cveVia" id="cveViaRef2Label" path="dgVialidadByCveViaRef2.cveVia" 
												    		cssErrorClass="error"><span class="required">*</span>Vialidad Referencia 2: </form:label>	    			    
										</td>
										<td align="left" width="100px">
											<combo:creaCombo entidad="mx.gob.imss.ctirss.domiciliosInegi.model.DgVialidad"
											 idHtml="dgVialidadByCveViaRef2.cveVia"
											 idHtmlPadre="dgVialidadByCveViaRef2.dgCatVialidad.cveTipoVial@dgCatVialidad.cveTipoVial,
											 			  dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt@dgCatLocalidad.id.cveEnt,
											 			  dgCatLocalidad.id.cveLoc@dgCatLocalidad.id.cveLoc,
											 			  dgCatLocalidad.dgCatMunicipio.id.cveMun@dgCatLocalidad.id.cveMun,											 			  
											 			  dgCatLocalidad.dgCatAmbito.ambito@dgCatAmbito.ambito"
											 entidadPadre="mx.gob.imss.ctirss.domiciliosInegi.model.DgCatVialidad,
											 			   mx.gob.imss.ctirss.domiciliosInegi.model.DgCatEstado,
											 			   mx.gob.imss.ctirss.domiciliosInegi.model.DgCatLocalidad,
											 			   mx.gob.imss.ctirss.domiciliosInegi.model.DgCatMunicipio,
											 			   mx.gob.imss.ctirss.domiciliosInegi.model.DgCatAmbito"
											 idHtmlContenedor="domGeoFormRegistro"
  										     idHtmlValor="${objDG.dgVialidadByCveViaRef2.cveVia},${objDG.dgVialidadByCveViaRef2.dgCatVialidad.cveTipoVial},${objDG.dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt},${objDG.dgCatLocalidad.id.cveLoc},${objDG.dgCatLocalidad.dgCatMunicipio.id.cveMun},${objDG.dgCatLocalidad.dgCatAmbito.ambito}"
  										     onchange="getSendComboDescToHidden(this,'dgVialidadByCveViaRef2.nomVia')"
											 />
												    
										</td>
							        </tr>	
							        
							          <tr class="par">
							          <td align="left" width="100px">				   							   		
											<form:label for="dgVialidadByCveViaRef3.dgCatVialidad.cveTipoVial" id="cveTipoVialRef1Label" path="dgVialidadByCveViaRef3.dgCatVialidad.cveTipoVial" 
												    		cssErrorClass="error">Tipo Vialidad Referencia3: </form:label>	    			    
										</td>
										<td align="left" width="100px">
											<combo:creaCombo entidad="mx.gob.imss.ctirss.domiciliosInegi.model.DgCatVialidad"
											 idHtml="dgVialidadByCveViaRef3.dgCatVialidad.cveTipoVial"
											 idHtmlContenedor="domGeoFormRegistro"
											 idHtmlValor="${objDG.dgVialidadByCveViaRef3.dgCatVialidad.cveTipoVial}"
											 onchange="getSendComboDescToHidden(this,'dgVialidadByCveViaRef3.dgCatVialidad.descripcion')"
											 />	  
										</td>
										<td align="left" width="100px">				   							   		
											<form:label for="dgVialidadByCveViaRef3.cveVia" id="cveViaRef3Label" path="dgVialidadByCveViaRef3.cveVia" 
												    		cssErrorClass="error">Vialidad Referencia 3: </form:label>	    			    
										</td>
										<td align="left" width="100px">
											<combo:creaCombo entidad="mx.gob.imss.ctirss.domiciliosInegi.model.DgVialidad"
											 idHtml="dgVialidadByCveViaRef3.cveVia"
											 idHtmlPadre="dgVialidadByCveViaRef3.dgCatVialidad.cveTipoVial@dgCatVialidad.cveTipoVial,
											 			  dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt@dgCatLocalidad.id.cveEnt,
											 			  dgCatLocalidad.id.cveLoc@dgCatLocalidad.id.cveLoc,
											 			  dgCatLocalidad.dgCatMunicipio.id.cveMun@dgCatLocalidad.id.cveMun,											 			  
											 			  dgCatLocalidad.dgCatAmbito.ambito@dgCatAmbito.ambito"
											 entidadPadre="mx.gob.imss.ctirss.domiciliosInegi.model.DgCatVialidad,
											 			   mx.gob.imss.ctirss.domiciliosInegi.model.DgCatEstado,
											 			   mx.gob.imss.ctirss.domiciliosInegi.model.DgCatLocalidad,
											 			   mx.gob.imss.ctirss.domiciliosInegi.model.DgCatMunicipio,
											 			   mx.gob.imss.ctirss.domiciliosInegi.model.DgCatAmbito"
											 idHtmlContenedor="domGeoFormRegistro"
  										     idHtmlValor="${objDG.dgVialidadByCveViaRef3.cveVia},${objDG.dgVialidadByCveViaRef3.dgCatVialidad.cveTipoVial},${objDG.dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt},${objDG.dgCatLocalidad.id.cveLoc},${objDG.dgCatLocalidad.dgCatMunicipio.id.cveMun},${objDG.dgCatLocalidad.dgCatAmbito.ambito}"
  										     onchange="getSendComboDescToHidden(this,'dgVialidadByCveViaRef3.nomVia')"
											 />
											
								    
										</td>
							        </tr>
							         <tr class="par">
							          <td align="left" width="100px">				   							   		
											<form:label for="descripc" id="descripc" path="descripc" 
												    		cssErrorClass="error">Descripción:</form:label>	    			    
										</td>
										<td align="left" width="100px" colspan="3">
											<form:textarea path="descripc" cols="100" rows="5"/>
										</td>
							        </tr>
							      
									</tbody>
							  </table>
							  	</td>
							  </tr>
							</table>
							
							
							
							
							<form:hidden path="dgCatLocalidad.dgCatAmbito.nombre"/>
							
							<form:hidden path="dgCatLocalidad.dgCatMunicipio.nomMun"/>
							<form:hidden path="dgCatLocalidad.nomLoc"/>
							<form:hidden path="dgAsentamiento.dgCatTipoAsen.nombre"/>
							<form:hidden path="dgAsentamiento.nomAsen"/>
							
							<form:hidden path="dgVialidadByCveViaPrin.dgCatVialidad.descripcion"/>							
							<form:hidden path="dgVialidadByCveViaPrin.nomVia"/>
							
							<form:hidden path="dgVialidadByCveViaRef1.dgCatVialidad.descripcion"/>
							<form:hidden path="dgVialidadByCveViaRef1.nomVia"/>
							
							<form:hidden path="dgVialidadByCveViaRef2.dgCatVialidad.descripcion"/>
							<form:hidden path="dgVialidadByCveViaRef2.nomVia"/>
							
							<form:hidden path="dgVialidadByCveViaRef3.dgCatVialidad.descripcion"/>
							<form:hidden path="dgVialidadByCveViaRef3.nomVia"/>
							
							<form:hidden path="dgCatLocalidad.dgCatMunicipio.dgCatEstado.nomEnt"/>	
						</fieldset>
						
						<input type="hidden" name="loadValue" id="loadValue" value="0">
					</form:form>							    
		    	</div>
			</div>
			
<script type="text/javascript">

$("#dgCatLocalidad\\.dgCatMunicipio\\.dgCatEstado\\.cveEnt").attr("disabled", ${objDG.bloquearEstado});
	
</script>				