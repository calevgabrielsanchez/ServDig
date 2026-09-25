<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<html lang="sp">
<style>
	label.error { float: none; font-size:xx-small; color: red; padding-left: .5em; vertical-align: top; }
	label  {  float: none; }
</style>
 
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/promocion/promocionSaticB.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
<link rel="stylesheet" type="text/css" 	href="<%=request.getContextPath()%>/resources/estilos/styleTabs.css">

		
<div id="cuerpo">
			
	<div class="menu_principal" style="height: 2em !important;"> 
		<!--inicia menu principal-->
		<div align="center">
			<div class="centrado">
		    	<ul style="height: 1em !important;">
					<li><a> Promoci&oacute;n Satic-B</a></li>
				</ul>
			</div>
		<!--fin centrado--> 
		</div>
	</div>
		    
	<div id="dgPromocion" style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
		<div id="wrapperDialogPromocion" style="background-color: #f2fff2;">	
			<form:form modelAttribute="crtDeteccion" action="/catalogo/promocion/consultar.do"
						method="post" id="promocionSaticbForm">
				<input type="hidden" name="bandera" id="bandera" />
				<fieldset>
					<table style="width: 900px" align="center">
						<tr valign="middle">
							<td align="center" width="900px">
							<div id="labelError"></div>	
								<table class="tablaverde2" style="width: 900px">
									<tbody>
										<tr valign="top" class="impar">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>										
										
										<tr valign="top" class="par">   											   	
											<td align="left" width="75%" colspan="4" class="etiqueta2">
												<form:label id="fechaEstimIncioLabel" for="fechaEstimIncio" path="fechaEstimIncio"
												cssErrorClass="error"><span class="required">*</span> Fecha de t&eacute;rmino de la obra &nbsp;&nbsp;&nbsp; Del:</form:label>
												<form:input path="fechaEstimIncio" id="fechaEstimIncio" readonly="true" 
												onchange="jsValidarFecOficioPromo();jsValidaFechasLimite();"/>
												<form:errors path="fechaEstimIncio" />
												<form:label id="fechaEstTermLabel" for="fechaEstTerm" path="fechaEstTerm"
												cssErrorClass="error"><span class="required">*</span> al: </form:label>
												<form:input path="fechaEstTerm" id="fechaEstTerm" readonly="true" 
												onchange="jsValidarFecOficioPromo();jsValidaFechasLimite();"/>
												<form:errors path="fechaEstTerm"/>
												<label id="labelPeriodo"></label>	
											</td>																					
										</tr>																																				
										<tr valign="top" class="par">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>
										<tr valign="top" class="par">
											<td align="center" width="80px" colspan="4">
												<a href="#" onclick="javascript:buscar();"><span class="boton">Buscar</span></a>
											</td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>
										<tr valign="top" class="impar">
											<td align="left" colspan="4">&nbsp;</td>
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
	
	<div class="menu_principal" style="height: 2em !important;display:none;" id="divTitulo"> 
		<!--inicia menu principal-->
		<div align="center">
			<div class="centrado">
		    	<ul style="height: 1em !important;">
					<li><a>Registros Disponibles a Promover</a></li>
				</ul>
			</div>
		<!--fin centrado--> 
		</div>
	</div>
	<br>		
		
	<div id="promoverSATICB">
		<jsp:include page="promoverSATICB.jsp" />
	</div>		
	
	<div id="promocionSaticBObrasSatic" style="overflow: auto; width:950px; height:300px;display:none;" align="center">		
		<jsp:include page="registrosSaticB.jsp" />
	</div>
	    		
	<div id="buttonPromover" style="display:none" align="center">
		<br>
		<a href="#" onclick="javascript:mostrar();"><span class="boton">Ver Datos Completos</span></a>
	</div>
	
	<div id="registroPromocion">
		<jsp:include page="../registroPromocion.jsp" />
	</div>				
	
	<div id="datosSatic">
		<jsp:include page="datosSatic.jsp" />
	</div>		
	
				
						    	   
</div>	 