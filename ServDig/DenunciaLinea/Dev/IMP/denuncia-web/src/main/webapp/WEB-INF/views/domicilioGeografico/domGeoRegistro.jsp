<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
	
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/generales/controlCombos.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/domiciliosInegi/domiciliosInegi.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>

				     
		    <div id="dgDomGeoRegistro" title="Registro Obra" style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
			    <div id="wrapperDialogRegistro" style="background-color: #f2fff2;">	
					<form:form modelAttribute="dgDomicilioGeografico" action="domiciliosGeograficos/agregar.do"
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
												    		cssErrorClass="error">Ambito: </form:label>	    			    
										</td>
										  <td align="left" width="100px">
											<combo:creaCombo entidad="mx.gob.imss.ctirss.domiciliosInegi.model.DgCatAmbito"
											 idHtml="dgCatLocalidad.dgCatAmbito.ambito"
											 idHtmlContenedor="domGeoFormRegistro"
											 idHtmlValor="${dgDomicilioGeografico.dgCatLocalidad.dgCatAmbito.ambito }"/>		    
										</td>
							          <td width="14%">&nbsp;</td>
							          <td width="39%">&nbsp;</td>
							        </tr>
							        <tr class="par">
							        	<td align="left" width="100px">				   							   		
											<form:label for="dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt" id="cveEntLabel" path="dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt" 
												    		cssErrorClass="error">Estado: </form:label>	    			    
										</td>
										  <td align="left" width="100px">
											<combo:creaCombo entidad="mx.gob.imss.ctirss.domiciliosInegi.model.DgCatEstado"
											 idHtml="dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt"
											 idHtmlContenedor="domGeoFormRegistro"
											 idHtmlValor="${dgDomicilioGeografico.dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt}"
											 />		    
										</td>
							          <td width="14%">&nbsp;</td>
							          <td width="39%">&nbsp;</td>
							        </tr>
							        <tr class="par">
							        	<td align="left" width="100px">				   							   		
											<form:label for="dgCatLocalidad.dgCatMunicipio.id.cveMun" id="cveMunLabel" path="dgCatLocalidad.dgCatMunicipio.id.cveMun" 
												    		cssErrorClass="error">Municipio: [${dgDomicilioGeografico.dgCatLocalidad.dgCatMunicipio.id.cveMun}]</form:label>	    			    
										</td>
										  <td align="left" width="100px">
											<combo:creaCombo entidad="mx.gob.imss.ctirss.domiciliosInegi.model.DgCatMunicipio"
											 idHtml="dgCatLocalidad.dgCatMunicipio.id.cveMun"
											 idHtmlPadre="dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt@id.cveEnt"
											 entidadPadre="mx.gob.imss.ctirss.domiciliosInegi.model.DgCatEstado"											 			   
											 idHtmlContenedor="domGeoFormRegistro"
											 idHtmlValor="${dgDomicilioGeografico.dgCatLocalidad.dgCatMunicipio.id.cveMun},${dgDomicilioGeografico.dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt}"
											 />		    
										</td>
							          <td width="14%">&nbsp;</td>
							          <td width="39%">&nbsp;</td>
							        </tr>
							        <tr class="par">
							        	<td align="left" width="100px">				   							   		
											<form:label for="dgCatLocalidad.id.cveLoc" id="cveLocLabel" path="dgCatLocalidad.id.cveLoc" 
												    		cssErrorClass="error">Localidad:[${dgDomicilioGeografico.dgCatLocalidad.id.cveLoc},${dgDomicilioGeografico.dgCatLocalidad.dgCatMunicipio.id.cveMun},${dgDomicilioGeografico.dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt},${dgDomicilioGeografico.dgCatLocalidad.dgCatAmbito.ambito}] </form:label>	    			    
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
											 idHtmlValor="${dgDomicilioGeografico.dgCatLocalidad.id.cveLoc},${dgDomicilioGeografico.dgCatLocalidad.dgCatMunicipio.id.cveMun},${dgDomicilioGeografico.dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt},${dgDomicilioGeografico.dgCatLocalidad.dgCatAmbito.ambito}"
											 />		    
										</td>
							          <td width="14%">&nbsp;</td>
							          <td width="39%">&nbsp;</td>
							        </tr>
							        <tr class="par">
							         	<td align="left" width="100px">				   							   		
											<form:label for="dgAsentamiento.dgCatTipoAsen.cveTipoAsen" id="cveTipoAsenLabel" path="dgAsentamiento.dgCatTipoAsen.cveTipoAsen" 
												    		cssErrorClass="error">Tipo Asentamiento: </form:label>	    			    
										</td>
										  <td align="left" width="100px">
											<combo:creaCombo entidad="mx.gob.imss.ctirss.domiciliosInegi.model.DgCatTipoAsen"
											 idHtml="dgAsentamiento.dgCatTipoAsen.cveTipoAsen"
											 idHtmlContenedor="domGeoFormRegistro"
											 idHtmlValor="${dgDomicilioGeografico.dgAsentamiento.dgCatTipoAsen.cveTipoAsen}"/>		    
										</td>
							        	<td align="left" width="100px">				   							   		
											<form:label for="dgAsentamiento.id.cveAsen" id="cveAsenLabel" path="dgAsentamiento.id.cveAsen" 
												    		cssErrorClass="error">Asentamiento: [${dgDomicilioGeografico.dgAsentamiento.id.cveAsen},${dgDomicilioGeografico.dgAsentamiento.dgCatTipoAsen.cveTipoAsen},${dgDomicilioGeografico.dgCatLocalidad.id.cveLoc},${dgDomicilioGeografico.dgCatLocalidad.dgCatMunicipio.id.cveMun},${dgDomicilioGeografico.dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt}]</form:label>	    			    
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
											 idHtmlValor="${dgDomicilioGeografico.dgAsentamiento.id.cveAsen},${dgDomicilioGeografico.dgAsentamiento.dgCatTipoAsen.cveTipoAsen},${dgDomicilioGeografico.dgCatLocalidad.id.cveLoc},${dgDomicilioGeografico.dgCatLocalidad.dgCatMunicipio.id.cveMun},${dgDomicilioGeografico.dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt}"
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
												cssErrorClass="error"> C&oacute;digo Postal: </form:label>
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
											 idHtmlValor="${dgDomicilioGeografico.dgCodigosPostales.id.codigo},${dgDomicilioGeografico.dgAsentamiento.id.cveAsen},${dgDomicilioGeografico.dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt},${dgDomicilioGeografico.dgCatLocalidad.id.cveLoc},${dgDomicilioGeografico.dgCatLocalidad.dgCatMunicipio.id.cveMun}"
											 />		    
									 </td>
							          <td>&nbsp;</td>
							          <td>&nbsp;</td>
							        </tr>
							      <tr class="par">
							        	<td align="left" width="100px">				   							   		
											<form:label for="dgVialidadByCveViaPrin.dgCatVialidad.cveTipoVial" id="cveTipoVialLabel" path="dgVialidadByCveViaPrin.dgCatVialidad.cveTipoVial" 
												    		cssErrorClass="error">Tipo Vialidad: </form:label>	    			    
										</td>
										  <td align="left" width="100px">
											<combo:creaCombo entidad="mx.gob.imss.ctirss.domiciliosInegi.model.DgCatVialidad"
											 idHtml="dgVialidadByCveViaPrin.dgCatVialidad.cveTipoVial"
											 idHtmlContenedor="domGeoFormRegistro"
											 idHtmlValor="${dgDomicilioGeografico.dgVialidadByCveViaPrin.dgCatVialidad.cveTipoVial}"
											 />		    
										</td>
							          <td align="left" width="100px">				   							   		
											<form:label for="dgVialidadByCveViaPrin.cveVia" id="cveViaPrinLabel" path="dgVialidadByCveViaPrin.cveVia" 
												    		cssErrorClass="error">Vialidad Principal:[${dgDomicilioGeografico.dgVialidadByCveViaPrin.cveVia},${dgDomicilioGeografico.dgVialidadByCveViaPrin.dgCatVialidad.cveTipoVial},${dgDomicilioGeografico.dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt},${dgDomicilioGeografico.dgCatLocalidad.id.cveLoc},${dgDomicilioGeografico.dgCatLocalidad.dgCatMunicipio.id.cveMun},${dgDomicilioGeografico.dgCatLocalidad.dgCatAmbito.ambito}] </form:label>	    			    
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
											  idHtmlValor="${dgDomicilioGeografico.dgVialidadByCveViaPrin.cveVia},${dgDomicilioGeografico.dgVialidadByCveViaPrin.dgCatVialidad.cveTipoVial},${dgDomicilioGeografico.dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt},${dgDomicilioGeografico.dgCatLocalidad.id.cveLoc},${dgDomicilioGeografico.dgCatLocalidad.dgCatMunicipio.id.cveMun},${dgDomicilioGeografico.dgCatLocalidad.dgCatAmbito.ambito}"
											 />		    
										</td>
							        </tr>
							          
								     <tr class="par">
								       <td align="left" width="100px">				   							   		
											<form:label for="dgCatTipoDom.cveTipoDom" id="cveTipoDomLabel" path="dgCatTipoDom.cveTipoDom" 
												    		cssErrorClass="error">Tipo Domicilio: </form:label>	    			    
										</td>
										<td align="left" width="100px">
											<combo:creaCombo entidad="mx.gob.imss.ctirss.domiciliosInegi.model.DgCatTipoDom"
											 idHtml="dgCatTipoDom.cveTipoDom"
											 idHtmlContenedor="domGeoFormRegistro"
											 idHtmlValor="${dgDomicilioGeografico.dgCatTipoDom.cveTipoDom}"
											 />		    
										</td>
										<td align="left" width="100px" colspan="1"><form:label
												id="nomvialLabel" for="nomvial" path="nomvial"
												cssErrorClass="error"> Via Principal: </form:label>
									 </td>
									  <td align="left" width="100px" colspan="1">
											<form:input	path="nomvial" value="" label="Via Principal" />
									 </td>
								    </tr>
								     <tr class="par">
								        <td align="left" width="100px">				   							   		
											<form:label for="numextnum" id="numextnumLabel" path="numextnum" 
												    		cssErrorClass="error">N&uacute;mero Exterior: </form:label>	    			    
										</td>
										<td align="left" width="100px">
											<form:input	path="numextnum" value="" label="N&uacute;mero Exterior: " />		    
										</td>
										<td align="left" width="100px">				   							   		
											<form:label for="numextalf" id="numextalfLabel" path="numextalf" 
												    		cssErrorClass="error">N&uacute;mero/Letra Ext: </form:label>	    			    
										</td>
										<td align="left" width="100px">
											<form:input	path="numextalf" value="" label="N&uacute;mero/Letra Ext" />		    
										</td>
								         
								    </tr>
								     <tr class="par">
								        <td align="left" width="100px">				   							   		
											<form:label for="numintnum" id="numintnumLabel" path="numintnum" 
												    		cssErrorClass="error">N&uacute;mero Interior: </form:label>	    			    
										</td>
										<td align="left" width="100px">
											<form:input	path="numintnum" value="" label="N&uacute;mero Interior: " />		    
										</td>
										<td align="left" width="100px">				   							   		
											<form:label for="numintalf" id="numintalfLabel" path="numintalf" 
												    		cssErrorClass="error">N&uacute;mero/Letra Int: </form:label>	    			    
										</td>
										<td align="left" width="100px">
											<form:input	path="numintalf" value="" label="N&uacute;mero/Letra Int" />		    
										</td>
								         
								    </tr>
								      <tr class="par">
								        <td align="left" width="100px">				   							   		
											<form:label for="numextAnt" id="numextAntLabel" path="numextAnt" 
												    		cssErrorClass="error">N&uacute;mero Exterior Ant: </form:label>	    			    
										</td>
										<td align="left" width="100px">
											<form:input	path="numextAnt" value="" label="N&uacute;mero Exterior Ant: " />		    
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
												    		cssErrorClass="error">Tipo Vialidad Referencia 1: [${dgDomicilioGeografico.dgVialidadByCveViaRef1.dgCatVialidad.cveTipoVial}]</form:label>	    			    
										</td>
										<td align="left" width="100px">
											<combo:creaCombo entidad="mx.gob.imss.ctirss.domiciliosInegi.model.DgCatVialidad"
											 idHtml="dgVialidadByCveViaRef1.dgCatVialidad.cveTipoVial"
											 idHtmlContenedor="domGeoFormRegistro"
											 idHtmlValor="${dgDomicilioGeografico.dgVialidadByCveViaRef1.dgCatVialidad.cveTipoVial}"
											 />	    
										</td>
										<td align="left" width="100px">				   							   		
											<form:label for="dgVialidadByCveViaRef1.cveVia" id="cveViaRef1Label" path="dgVialidadByCveViaRef1.cveVia" 
												    		cssErrorClass="error">Vialidad Referencia 1:[${dgDomicilioGeografico.dgVialidadByCveViaRef1.cveVia},${dgDomicilioGeografico.dgVialidadByCveViaPrin.dgCatVialidad.cveTipoVial},${dgDomicilioGeografico.dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt},${dgDomicilioGeografico.dgCatLocalidad.id.cveLoc},${dgDomicilioGeografico.dgCatLocalidad.dgCatMunicipio.id.cveMun},${dgDomicilioGeografico.dgCatLocalidad.dgCatAmbito.ambito}] </form:label>	    			    
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
  										     idHtmlValor="${dgDomicilioGeografico.dgVialidadByCveViaRef1.cveVia},${dgDomicilioGeografico.dgVialidadByCveViaRef1.dgCatVialidad.cveTipoVial},${dgDomicilioGeografico.dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt},${dgDomicilioGeografico.dgCatLocalidad.id.cveLoc},${dgDomicilioGeografico.dgCatLocalidad.dgCatMunicipio.id.cveMun},${dgDomicilioGeografico.dgCatLocalidad.dgCatAmbito.ambito}"
											 />
												    
										</td>
							        </tr>			    
								  <tr class="par">
							          <td align="left" width="100px">				   							   		
											<form:label for="dgVialidadByCveViaRef2.dgCatVialidad.cveTipoVial" id="cveTipoVialRef2Label" path="dgVialidadByCveViaRef2.dgCatVialidad.cveTipoVial" 
												    		cssErrorClass="error">Tipo Vialidad Referencia 2: </form:label>	    			    
										</td>
										<td align="left" width="100px">
											<combo:creaCombo entidad="mx.gob.imss.ctirss.domiciliosInegi.model.DgCatVialidad"
											 idHtml="dgVialidadByCveViaRef2.dgCatVialidad.cveTipoVial"
											 idHtmlContenedor="domGeoFormRegistro"
											 idHtmlValor="${dgDomicilioGeografico.dgVialidadByCveViaRef2.dgCatVialidad.cveTipoVial}"
											 />	   	    
										</td>
										<td align="left" width="100px">				   							   		
											<form:label for="dgVialidadByCveViaRef2.cveVia" id="cveViaRef2Label" path="dgVialidadByCveViaRef2.cveVia" 
												    		cssErrorClass="error">Vialidad Referencia 2: ${dgDomicilioGeografico.dgVialidadByCveViaRef2.cveVia},${dgDomicilioGeografico.dgVialidadByCveViaPrin.dgCatVialidad.cveTipoVial},${dgDomicilioGeografico.dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt},${dgDomicilioGeografico.dgCatLocalidad.id.cveLoc},${dgDomicilioGeografico.dgCatLocalidad.dgCatMunicipio.id.cveMun},${dgDomicilioGeografico.dgCatLocalidad.dgCatAmbito.ambito}</form:label>	    			    
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
  										     idHtmlValor="${dgDomicilioGeografico.dgVialidadByCveViaRef2.cveVia},${dgDomicilioGeografico.dgVialidadByCveViaRef2.dgCatVialidad.cveTipoVial},${dgDomicilioGeografico.dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt},${dgDomicilioGeografico.dgCatLocalidad.id.cveLoc},${dgDomicilioGeografico.dgCatLocalidad.dgCatMunicipio.id.cveMun},${dgDomicilioGeografico.dgCatLocalidad.dgCatAmbito.ambito}"
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
											 idHtmlValor="${dgDomicilioGeografico.dgVialidadByCveViaRef3.dgCatVialidad.cveTipoVial}"
											 />	  
										</td>
										<td align="left" width="100px">				   							   		
											<form:label for="dgVialidadByCveViaRef3.cveVia" id="cveViaRef3Label" path="dgVialidadByCveViaRef3.cveVia" 
												    		cssErrorClass="error">Vialidad Referencia 3: [${dgDomicilioGeografico.dgVialidadByCveViaRef3.cveVia},${dgDomicilioGeografico.dgVialidadByCveViaPrin.dgCatVialidad.cveTipoVial},${dgDomicilioGeografico.dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt},${dgDomicilioGeografico.dgCatLocalidad.id.cveLoc},${dgDomicilioGeografico.dgCatLocalidad.dgCatMunicipio.id.cveMun},${dgDomicilioGeografico.dgCatLocalidad.dgCatAmbito.ambito}]</form:label>	    			    
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
  										     idHtmlValor="${dgDomicilioGeografico.dgVialidadByCveViaRef3.cveVia},${dgDomicilioGeografico.dgVialidadByCveViaRef3.dgCatVialidad.cveTipoVial},${dgDomicilioGeografico.dgCatLocalidad.dgCatMunicipio.dgCatEstado.cveEnt},${dgDomicilioGeografico.dgCatLocalidad.id.cveLoc},${dgDomicilioGeografico.dgCatLocalidad.dgCatMunicipio.id.cveMun},${dgDomicilioGeografico.dgCatLocalidad.dgCatAmbito.ambito}"
											 />
												    
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
			</div>
			