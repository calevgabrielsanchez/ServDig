<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<html lang="sp">

 
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/promocion/promocionSalarioBase.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/jquery.validator.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/KeyPressed.js"></script>



		
<div id="cuerpo">
			
	<div class="menu_principal" style="height: 2em !important;"> 
		<!--inicia menu principal-->
		<div align="center">
			<div class="centrado">
		    	<ul>
					<li><a> Promoci&oacute;n de Salario Base de Cotizaci&oacute;n</a></li>
				</ul>
			</div>
		<!--fin centrado--> 
		</div>
	</div>
			    
	<div id="dgPromocionSB" style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
		<div id="wrapperDialogPromocion" style="background-color: #f2fff2;">	
			<form  method="post" id="promocionSBForm">
				<fieldset>
					<table style="width: 900px" align="center">
						<tr valign="top">
							<td align="center" width="900px">	
								<table class="tablaverde2" style="width: 900px">
									<tbody>
										<tr valign="top" class="impar">
											<td align="left" colspan="3">&nbsp;</td>
										</tr>
										<tr valign="top" class="par">   												
											<td align="right" width="25%">
												 	<label style="color: red;">* </label>
											    	<label class="etiqueta2"> Origen de Promoci&oacute;n :</label>		
											</td>
											<td align="center" width="25%">
								  				<select id="selectOrigen" onchange="jsActualizaCriterios(this.value);">
								  					<option value="-1">--Por favor seleccione--</option>
								  				</select>
											</td>
										</tr>																			
										<tr align="center" class="par">
											<td align="center" colspan="3">
												&nbsp;
											</td>
										</tr>
										<tr valign="top" class="par">   												
											<td align="right" width="25%">
												 	<label style="color: red;">* </label>
											    	<label class="etiqueta2">Criterio de Selecci&oacute;n: </label>    		
											</td>
											<td align="center" width="25%">
								  				<select id="selectCriterios" onchange="jsLimpiarEtiqueta();">
								  					<option value="-1">--Por favor seleccione--</option>
								  				</select>
											</td>
											
										</tr>																			
										<tr align="center" class="par">
											<td align="center" colspan="3">
												<div id="labelcveSelector"></div>
											</td>
										</tr>
										<tr align="center" class="par">
											<td align="center" colspan="3">
												&nbsp;
											</td>
										</tr>
										<tr align="center" class="par">
											<td align="center" colspan="3">
												<a href="#" onclick="javascript:buscar();"><span class="boton">Buscar</span></a>
											</td>
										</tr>
										<tr valign="top" class="impar">
											<td align="left" colspan="3">&nbsp;</td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</table>										
				</fieldset>	
			</form>							    
	  	</div>
	</div>
	
	<div class="menu_principal" style="height: 2em !important;"> 
		<!--inicia menu principal-->
		<div align="center">
			<div class="centrado">
		    	<ul style="height: 1em !important;">
					<li><a>Promover</a></li>
				</ul>
			</div>
		<!--fin centrado--> 
		</div>
	</div>
	<%--<br>--%>
	
	       <div id="promocionSalarioBase"  style="overflow: auto; width:950px; height:300px;" align="center" class="centrado">   
	         <table id="dtDisponiblesSelector"  style="width: 930px" align="center">
		<thead>			
		</thead>
		<tbody align="center">
		</tbody>
	    </table>
	
          </div>
		
	       <br>	
	<div id="botonesPromoverSBC">
		<table style="width: 900px">				
			<tbody>
			<tr>
				<td>
			<table style="width: 300px" align="center" >
				<tr valign="top" class="impar" >
						<td align="center"> 
							<div id="botonValidarRPSBC" class="tablaverde2" style="display:none">
								<a onclick="javascript:validarRPatronalSBCProm();" href="#">
								<span class="boton">Validar Registro Patronal</span>
								</a>
							</div>	
						
						</td>
						<td align="center" colspan="2">&nbsp;&nbsp;</td>
						<td align="center">
							<div id="botonPromoverRPSBC" class="tablaverde2" style="display:none">
								<a onclick="javascript:mostrar();" href="#">
									<span class="boton">Promover</span>
								</a>
							</div>
						</td>
						
				</tr>
			</table>
				</td>
			</tr>	
			</tbody>
		</table>
	</div>
			
<!-- 	<div id="btnVisualizar" style="display:none" align="center">
		<a href="#" onclick="javascript:mostrar();"><span class="boton">Promover</span></a>
	</div>
 -->			    	   
	<div id="dgRegistroPromocionSB1"  style="display:none">
		<jsp:include page="registroPromocionSB.jsp" />
	</div>					    	   
</div>	    
	
