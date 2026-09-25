<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<html lang="sp">
<body>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/promocion/promocionSalarioBaseIndividual.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
			    
			<div id="cuerpo">	
	
			<div class="menu_principal" style="height: 2em !important;"> 
		      <!--inicia menu principal-->
		      <div align="center">
		        <div class="centrado">
		          <ul style="height: 1em !important;">
		            <li><a> Generar Nuevo Folio Promoci&oacute;n Salario Base de Cotizaci&oacute;n </a></li>
		          </ul>
		        </div>
		        <!--fin centrado--> 
		      </div>
		    </div>      
			    
		    <div id="dgSBCindividual"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
			    <div id="wrapperDialogPromocionSBCindividual" style="background-color: #f2fff2;">	
					<form action="" id="promocionSBCindividualForm">
						<input type="hidden" name="cveFkPatron" id="cveFkPatron">
						<input type="hidden" name="bandera" id="bandera">
						<table  style="width: 900px" align="center" >
							<tr valign="middle">
								<td align="center" width="900px">
									<table class="tablaverde2" style="width: 900px" >
										<thead>
											<tr>
												<td colspan="4" class="etiqueta2">Origen de Promoci&oacute;n</td>
											</tr>
										</thead>
										<tbody>
											<tr class="impar">
												<td colspan="4">&nbsp;</td>
											</tr>	
											<tr class="par" >
										  		<td align="left" width="25%" class="etiqueta2">
										  			<label style="color: red;">* </label>
												   	<label>Criterio de Selecci&oacute;n: </label>   		
												</td>	
												<td align="left" colspan="3" width="75%">
													<select id="selectCriterios" name="idCriterioSeleccion" onchange="$('form#promocionSBCindividualForm #labelcriterios').html('');">
														<option value="">--Por favor seleccione--</option>
													</select>
													<div id="labelcriterios"></div>
												</td>				  		
									  		</tr>
									  		<tr class="par">
									  		<td colspan="4">&nbsp;</td>
									  	</tr>	
								  		<thead>
										  	<tr >
										  		<td colspan="4" class="etiqueta2" width="100%">Informaci&oacute;n del Oficio de Promoci&oacute;n</td>
										  	</tr>
										</thead>
										<tr class="impar">
									  		<td colspan="4">&nbsp;</td>
									  	</tr>									  										  									  											  
										<tr class="par"> 
 											<td align="left" width="25%" class="etiqueta2">
 												<label style="color: red;">* </label>
											   	<label> Fecha Oficio de la Promoci&oacute;n: </label>    		
											</td>
											<td align="left" width="25%">
												<input type="text" size="10" id="fecOficio" readonly="readonly" name="fechaOficio">
												<div id="labelFecOficio"></div>
											</td>   
											<td align="left" width="25%" class="etiqueta2">
 												<label style="color: red;">* </label>
											   	<label> N&uacute;mero de Oficio de la Promoci&oacute;n: </label> 		
											</td>
											<td align="left" width="25%">
												<input type="text" size="27" maxlength="25" id="nuOficiopro" name="nuOficiopro"  onkeydown="$('form#promocionSBCindividualForm labelNumOficio').html('');">
												<div id="labelNumOficio"></div>
											</td>                																												
										 </tr>
										 <tr  class="par">   	
											<td align="left" width="25%" class="etiqueta2">
											   	<label> Observaciones: </label> 												  																							
											</td>
											<td align="left" width="75%" colspan="3" height="60">
												<textarea rows="3" cols="92" id="txObservaciones" name="txObservaciones" onchange="validaCampo('noCaracteresEspeciales','txObservaciones','promocionSBCindividualForm')" onkeyup="valTamTextArea(event,this,200)"></textarea>
											</td>																																					
										 </tr>
										 <tr class="par">
									  		<td colspan="4">&nbsp;</td>
									  	</tr>	
										 <thead>
										  	<tr >
										  		<td colspan="4" class="etiqueta2">Datos del Patr&oacute;n</td>
										  	</tr>
										</thead>
										<tr class="impar">
									  		<td colspan="4">&nbsp;</td>
									  	</tr>
									  	<tr class="par" >
									  		<td align="left" width="25%" class="etiqueta2">
									  			<label style="color: red;">* </label>
											   	<label>Registro Patronal: </label>   		
											</td>
                                          	<td align="left" width="25%" >
												<input type="text" id="registroPatronal" size="12" maxlength="10" name="regPatron" onchange="mayusculasTextField(this)"  onkeyup="mayusculasTextField(this)" onkeypress="return jsvalidarAlfaNumerico(event);">
												<a href="#" id="btnValidar">
													<span class="boton">Validar</span>
												</a>
											</td>		
											<td align="left" width="25%" class="etiqueta2">
											   	<label>Nombre o Raz&oacute;n Social: </label>   		
											</td>
                                          	<td align="left" width="25%" >
												<input type="text" id="razonSocial" size="50" readonly="readonly" name="razonSocial" onchange="$('form#promocionSBCindividualForm labelRegistroPatronal').html('');">
											</td>						  		
									  	</tr>
									  	<tr valign="top" class="par">
									  		<td colspan="2" align="left">
									  			<div id="labelRegistroPatronal"></div>
									  		</td>
									  		<td colspan="2" align="left">
									  			<div id="labelRazonSocial"></div>
									  		</td>
									  	</tr>
										</tbody>
									</table>
								</td>
							</tr>
						</table>
						<table  style="width: 900px" align="center" >
							 <tr valign="middle">
							  <td align="center" width="900px">
								<table class="tablaverde2" style="width: 900px">
								<thead>
								  	<tr >
								  		<td colspan="4" class="etiqueta2">Domicilio Geogr&aacute;fico</td>
								  	</tr>
								  </thead>
								<tbody>
									<tr class="impar">
								  		<td colspan="4">&nbsp;</td>
								  	</tr>	
									
									<tr valign="top">
										<td align="left" class="etiqueta2" width="25%">
											<span class="required">*</span>Calle: 
										</td>
										<td align="left" width="25%">
											<input type="hidden" name="municipio" id="municipio">
											<input name="domCalle" id="domCalle" size="50" onkeyup="validaCampo('noCaracteresEspeciales','domCalle')" readonly="readonly" /> 
											<div id="labeldomCalle"></div>
										</td>
										<td align="left" class="etiqueta2" width="25%">
											<span class="required">*</span>Colonia: 
										</td>
										<td align="left" width="25%">
											<input name="refColonia" id="refColonia" size="50" onkeyup="validaCampo('noCaracteresEspeciales','refColonia')" readonly="readonly" /> 
											<div id="labelColonia"></div>
										</td>
									</tr>
									<tr valign="top">
										<td align="left" class="etiqueta2" width="25%">
											<span class="required">*</span>N&uacute;mero Exterior:
										</td>
										<td align="left" width="25%">
											<input name="numNroext" id="numNroext" size="12" onkeyup="validaCampo('noCaracteresEspeciales','numNroext')" readonly="readonly" /> 
											<div id="labelNumExt"></div>
										</td>
										<td align="left" width="25%" class="etiqueta2">N&uacute;mero Interior: 
										</td>
										<td align="left" width="25%">
											<input name="numNroint" id="numNroint" size="12" onkeyup="validaCampo('noCaracteresEspeciales','numNroint')" readonly="readonly" />
											
										</td>
									</tr>
									<tr valign="top">
										<td align="left" class="etiqueta2" width="25%">
											<span class="required">*</span>C&oacute;digo Postal:
										</td>
										<td align="left" width="25%" >
											<input name="numCodigopostal" id="numCodigopostal" size="12" onkeyup="validaCampo('noCaracteresEspeciales','numCodigopostal');validaCampo('PermiteSoloNumeros','numCodigopostal')" readonly="readonly" />
											<div id="labelCP"></div>
										</td>
										<td align="left" colspan="2" valign="bottom" width="50%">
											<div id="domInegiButtons" align="center">
												<a href="#" onclick="mostarDomGeo('<%=request.getContextPath() %>','registro')"><span class="boton">Agregar Domicilio</span></a>
											</div>
										</td>
									</tr>
									<tr align="center"  colspan="4" valign="bottom">
										<td>&nbsp;&nbsp;</td>
									</tr>
									<tr>
										<td align="center" colspan="4">
											<a href="#" id="btnPromover">
												<span class="boton">Promover</span>
											</a>
										</td>
									</tr>
									<tr class="par">
								  		<td colspan="4">&nbsp;</td>
								  	</tr>	
									
								</tbody>
							</table>			
							  </td>
							  </tr>
						</table>													
					</form>											    
		    	</div>
			</div>
			
		</div>
</body>
</html>