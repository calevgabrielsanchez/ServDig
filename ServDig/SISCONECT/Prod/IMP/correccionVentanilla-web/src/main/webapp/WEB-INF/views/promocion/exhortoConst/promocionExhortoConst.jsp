<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<html lang="sp">

<style>
	label.error { float: none; font-size:smaller; color: red; padding-left: .5em; vertical-align: top; }
	label  {  float: none; }
</style>

 
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/promocion/promocionExCons.js"></script>
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
					<li><a> Promoci&oacute;n Exhorto de Construcci&oacute;n</a></li>
				</ul>
			</div>
		<!--fin centrado--> 
		</div>
	</div>
		    
	<div id="dgPromocionExhortoConst"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
		<div id="wrapperDialogPromocionExhortoConst" style="background-color: #f2fff2;">	
			<form action=""	method="post" id="promocionExhortoConstForm">
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
											<td align="left" colspan="4">
												<div id="labelError"></div>
											</td>
										</tr>									
										
										<tr valign="top" class="par">   											   	
											<td align="left" width="25%" class="etiqueta2">
												<span class="required">*</span> Fecha de Detecci&oacute;n de : 
											</td>
											<td align="left" width="25%">
												<input name="fechaEstimIncio" id="fechaEstimIncio" size="10" readonly="readonly" />
												<span class="required">*</span> <b> a </b>:  
												<input name="fechaEstTerm" id="fechaEstTerm" size="10" readonly="readonly" />
											</td>	
											<td align="left" width="25%">
												<div id="labelFechas"></div>
											</td>																						
										</tr>	
										<tr>
											<td align="left" width="25%" class="etiqueta2">
												<label>Folio de Detecci&oacute;n :</label> 
											</td>
											<td align="left" width="25%">
												<input name="nuFoliodeteccion" id="nuFoliodeteccion" size="20" maxlength="18" onkeyup = "this.value=this.value.toUpperCase()" onkeypress="return jsvalidarAlfaNumerico(event);"/>
											</td>
											<td align="left" width="25%" class="etiqueta2">
												<label>Registro Patronal :</label> 
											</td>
											<td align="left" width="25%">
												<input name="regPatron" id="regPatron" size="11" maxlength="10" onkeyup = "this.value=this.value.toUpperCase()" onkeypress="return jsvalidarAlfaNumerico(event);"/>
											</td>
										</tr>																																			
										<tr valign="top" class="par">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>
										<tr valign="top" class="par">
											<td align="center" width="80px" colspan="4">
												<a href="#" onclick="javascript:buscar();"><span class="boton">Buscar</span></a>
												&nbsp;&nbsp;&nbsp;&nbsp;
											<a onclick="javascript:limpiarAltaExConstruccion();" href="#">
												<span class="boton">Limpiar</span>
											</a>												
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
			</form>							    
	  	</div>
	</div>			
	
	<div id="promocionExhortoObrasDeteccion" style="display: none;" align="center" class="centrado">
		<div class="menu_principal" style="height: 2em !important;"> 
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
	</div>
	
			
	<div id="promocionExhortoDeteccion" style="overflow: auto; width:950px; height:300px; display: none;" align="center" class="centrado">
		<jsp:include page="registrosDisponiblesExhorto.jsp" />
	</div>
	
	<div id="btnVisualizar" style="display:none" align="center">
		<br>
		<a href="#" onclick="javascript:mostrar();"><span class="boton">Ver</span></a>
	</div>	
	
	<div id="promocionExhortoCons" align="center" class="centrado">
		<jsp:include page="datosObraRegistrada.jsp" />
	</div>
	
	<div id="confirmarExhortoConst" align="center" class="centrado" >
		<jsp:include page="confirmarRegistroObra.jsp" />
	</div>
	
	<div id="promoverExhortoConst" align="center" class="centrado" >
		<jsp:include page="promoverConstruccion.jsp" />
	</div>
	
	<div id="registroPromocion">
		<jsp:include page="../registroPromocion.jsp" />
	</div>				
						    	   
</div>	 